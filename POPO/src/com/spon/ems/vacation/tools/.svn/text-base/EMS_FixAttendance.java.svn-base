package com.spon.ems.vacation.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;

import org.apache.log4j.Logger;

import vtgroup.ems.common.vo.AuthorizedBean;


/**
 * EMS 考勤異常資料  修正元件
 * 
 * @version 1.0
 * @created 18-十月-2010 下午 06:00:47
 */
public class EMS_FixAttendance{
	

	static Logger loger = Logger.getLogger(EMS_FixAttendance.class.getName());
	
	
	//private static EMS_FixAttendance attendance_tools;
	private volatile static EMS_FixAttendance fix_attendance_tools;
	private static String start_date = "";
	private static String end_date = "";
	
	
	public static EMS_FixAttendance getInstance() {
		
        /*if (attendance_tools == null){
        	attendance_tools = new EMS_FixAttendance();
        }*/
        if(fix_attendance_tools == null) {
            synchronized(EMS_AttendanceAction.class) {
            	fix_attendance_tools = new EMS_FixAttendance();
            }
        }
        
        return fix_attendance_tools;
    }

	private EMS_FixAttendance(){

	}

	public void finalize() throws Throwable {

	}

	public static String getStart_date() {
		return start_date;
	}

	public static void setStart_date(String startDate) {
		start_date = startDate;
	}

	public static String getEnd_date() {
		return end_date;
	}

	public static void setEnd_date(String endDate) {
		end_date = endDate;
	}

	/**
	 * 取得考勤異常資料清單
	 * @param conn
	 * @param ehf020401m0
	 * @param authbean
	 * @return
	 */
	public ArrayList getAttAbnormalList(Connection conn, EHF020401M0F ehf020401m0, AuthorizedBean authbean, boolean system_use_flag,
									    boolean overtime_data_flag, String receipt_no, String card_status){
		
		ArrayList list = new ArrayList();
		EMS_ACCESS ems_access = EMS_ACCESS.getInstance();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			//String ems_version = this.getSysParam(conn, authbean.getCompId(), "EMS_VERSION");
			
			//取得FLOW_NO,使用請假單資料
			//String flow_no = this.getSysParam(conn, authbean.getCompId(), "Vacation_FLOW_NO");
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			//取得公司名稱
			Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
			//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			//職務
			Map titleMap = hr_tools.getTitleNameMap(authbean.getCompId());
			
			String sql = "";
			String sqla = "";
			
			sql = "" +
			" SELECT EHF020401T0_05, EHF020401T0_08, ";
			
			//處理考勤異常說明
			//if("1.6".equals(ems_version)){
				sql += "" +
				" CASE D.EHF020410T0_06 " +
				" WHEN '00' THEN '正常' " +
				" WHEN '01' THEN '遲到' " +
				" WHEN '02' THEN '早退' " +
				" WHEN '03' THEN '曠職' " +
				" WHEN '04' THEN '未打卡' " +
				" END AS ERROR_DESC, ";
			//}else{
			//	sql += " EHF020401T0_09_DESC AS ERROR_DESC, ";
			//}
			
			sql+= "" +
			" B.EHF020403T1_01 AS EHF020403T1_01, " +
			" A.EHF020403T0_02 AS EHF020403T0_02, A.EHF020403T0_01 AS EHF020403T0_01, A.EHF020403T0_05 AS EHF020403T0_05, " +	
			" DATE_FORMAT( STR_TO_DATE( EHF020401T0_05 , '%Y/%m/%d' ), '%Y年%m月%d日' ) AS CARD_DATE, " +
			" DATE_FORMAT( STR_TO_DATE( EHF020401T0_05 , '%Y/%m/%d' ), '%m/%d' ) AS DATE2, " +
//			" DATE_FORMAT(EHF020401T0_07, '%Y年%m月%d日') AS CARD_DATE, " +
			" DATE_FORMAT(EHF020401T0_07, '%k點%i分') AS CARD_DATETIME, " +
			" DATE_FORMAT(EHF020401T0_07, '%H:%i') AS CARD_TIME, " +
			" CONCAT(DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d %H:%i'), ':00') AS EHF020401T0_07, " +  //以分計算, 取消以秒計算, edit by joe 2012/10/08
			" C.EHF000400T0_04 AS EHF000400T0_04, C.EHF000400T0_03 AS EHF000400T0_03, " +
			" EHF020401T0_11, " +
			" CASE EHF020401T0_FIX WHEN false THEN '未修正' WHEN true THEN '已修正' END AS EHF020401T0_FIX, " +
			" CONCAT(IFNULL(EHF020401T0_FDS, ''), '  ',IFNULL(EHF020401T0_FID, '')) AS EHF020401T0_FDS, " +
			" '01' AS TYPE, EHF020401T0_04 AS CLASS_NO, " +
			" DATE_FORMAT( STR_TO_DATE( EHF020401T0_05 , '%Y/%m/%d' ), '%Y/%m/%d' ) AS DATE1, " +
//			" DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d') AS DATE1, " +
			" EHF020401T0_01 AS UID, CONCAT(EHF020401T0_01, '@01') AS SUID, " +
			" EHF020401T0_PS AS PS " +
			" FROM EHF020401T0 " +
			" LEFT JOIN EHF020403T1 B ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
			" LEFT JOIN EHF020403T0 A ON EHF020403T0_01 = EHF020403T1_01 AND EHF020403T0_04 = EHF020401T0_11 " +
			" LEFT JOIN EHF000400T0 C ON EHF000400T0_01 = EHF020401T0_04 AND EHF000400T0_11 = EHF020401T0_11 ";
			
			//if("1.6".equals(ems_version)){
				//處理1.6版本的考勤異常說明資料表
				sql += "" +
				" LEFT JOIN EHF020410T0 D ON EHF020410T0_01 = EHF020401T0_02_EMP AND EHF020410T0_03 = EHF020401T0_05  " +
				" AND EHF020410T0_04 = EHF020401T0_08 AND EHF020410T0_08 = EHF020401T0_11 " +
				" AND EHF020410T0_05 = EHF020401T0_09 " +
				" LEFT JOIN EHF030200T0 E ON EHF020401T0_02_EMP = EHF030200T0_01" +//計算考勤人員  20131101 BY賴泓錡
				" AND EHF020401T0_11 = EHF030200T0_13";
			//}
			
			sql += "" +
			" WHERE 1=1 " +
			" AND EHF020401T0_09 = true " +  //異常
			" AND EHF020403T0_05 = '1'  " +  //計算考勤人員  20131101 BY賴泓錡
			" AND EHF020401T0_11 = '"+authbean.getCompId()+"' " ;  //公司代碼
			
			sqla = "" +
			" SELECT EHF020406T0_04, EHF020406T0_05, ";
			
			//處理考勤異常說明
			//if("1.6".equals(ems_version)){
				sqla += "" +
				" CASE UD.EHF020410T0_06 " +
				" WHEN '00' THEN '正常' " +
				" WHEN '01' THEN '遲到' " +
				" WHEN '02' THEN '早退' " +
				" WHEN '03' THEN '曠職' " +
				" WHEN '04' THEN '未打卡' " +
				" END AS ERROR_DESC, ";
			//}else{
			//	sqla += "" +
			//	" EHF020406T0_06 AS ERROR_DESC, ";
			//}
			
			sqla += "" +
			" UB.EHF020403T1_01 AS EHF020403T1_01, " +
			" UA.EHF020403T0_02 AS EHF020403T0_02, UA.EHF020403T0_01 AS EHF020403T0_01, UA.EHF020403T0_05 AS EHF020403T0_05 ," +
			" DATE_FORMAT( STR_TO_DATE( EHF020406T0_04 , '%Y/%m/%d' ), '%Y年%m月%d日' ) AS CARD_DATE, " +
			" DATE_FORMAT( STR_TO_DATE( EHF020406T0_04 , '%Y/%m/%d' ), '%m/%d' ) AS DATE2, " +
			" '' AS CARD_DATETIME," +
			" '' AS CARD_TIME, " +
//			" EHF020406T0_04, " +
			" CONCAT(DATE_FORMAT(EHF020406T0_04_DTE, '%Y/%m/%d %H:%i'), ':00') AS EHF020401T0_07, " +
			" UC.EHF000400T0_04 AS EHF000400T0_04, UC.EHF000400T0_03 AS EHF000400T0_03, " +
			" EHF020406T0_07, " +
			" CASE EHF020406T0_FIX WHEN false THEN '未修正' WHEN true THEN '已修正' END AS EHF020406T0_FIX, " +
			" CONCAT(IFNULL(EHF020406T0_FDS, ''), '  ',IFNULL(EHF020406T0_FID, '')) AS EHF020406T0_FDS, " +
			" '02' AS TYPE, EHF020406T0_03 AS CLASS_NO, " +
			" EHF020406T0_04 AS DATE1, " +
			" CONCAT(EHF020406T0_01,',',EHF020406T0_04,',',EHF020406T0_05) AS UID, " +
			" CONCAT(EHF020406T0_01,',',EHF020406T0_04,',',EHF020406T0_05, '@02') AS SUID, " +
			" EHF020406T0_PS AS PS " +
			" FROM EHF020406T0 " +
			" LEFT JOIN EHF020403T1 UB ON EHF020403T1_01 = EHF020406T0_01 AND EHF020403T1_06 = EHF020406T0_07 " +
			" LEFT JOIN EHF020403T0 UA ON EHF020403T0_01 = EHF020406T0_01 AND EHF020403T0_04 = EHF020406T0_07 " +
			" LEFT JOIN EHF000400T0 UC ON EHF000400T0_01 = EHF020406T0_03 AND EHF000400T0_11 = EHF020406T0_07 ";
			
			//if("1.6".equals(ems_version)){
				//處理1.6版本的考勤異常說明資料表
				sqla += "" +
				" LEFT JOIN EHF020410T0 UD ON EHF020410T0_01 = EHF020406T0_01 AND EHF020410T0_03 = EHF020406T0_04 " +
				" AND EHF020410T0_04 = EHF020406T0_05 AND EHF020410T0_08 = EHF020406T0_07 " +
				" AND EHF020410T0_05 = EHF020406T0_05_FLG " +
				" LEFT JOIN EHF030200T0 E ON EHF020406T0_01 = EHF030200T0_01 " +//計算考勤人員  20131101 BY賴泓錡
				" AND EHF020406T0_07 = EHF030200T0_13";
			//}
			
			sqla += "" +
			" WHERE 1=1 " +
			" AND EHF020406T0_05_FLG = true " +  //異常
			" AND EHF020403T0_05 = '1'  " +  //計算考勤人員  20131101 BY賴泓錡
			" AND EHF020406T0_07 = '"+authbean.getCompId()+"' " ;  //公司代碼
			
			//判斷當前登入者的身份，主管或是人事經辦
			//if( system_use_flag || this.checkEmsRoleEmp(this.emscontext, authbean, "", "2", "0001", flow_no, "") || "1.6".equals(ems_version) ){
			if( system_use_flag || ems_access.checkEmsRoleEmp(conn, authbean, "100") || ems_access.checkEmsRoleEmp(conn, authbean, "110") 
					|| ems_access.checkEmsRoleEmp(conn, authbean, "111") ){
				//人事經辦、會計、老闆
				
				sql += "" +
				" AND EHF020403T1_01 IS NOT NULL " ;
				
				sqla += "" +
				" AND EHF020403T1_01 IS NOT NULL " ;
					
				
				
				if (!ehf020401m0.getEHF020403T0_02().equals("") && !ehf020401m0.getEHF020403T0_01().equals("")){  //員工
					
					sql += " AND EHF020403T0_02 = '"+ehf020401m0.getEHF020403T0_02()+"' " +  //部門代號
						   " AND EHF020403T1_01 = '"+ehf020401m0.getEHF020403T0_01()+"' ";  //員工工號
					
					sqla += " AND EHF020403T0_02 = '"+ehf020401m0.getEHF020403T0_02()+"' " +  //部門代號
					   		" AND EHF020403T1_01 = '"+ehf020401m0.getEHF020403T0_01()+"' ";  //員工工號
					
				}else if(!ehf020401m0.getEHF020403T0_02().equals("")){  //部門組織
					sql += " AND EHF020403T0_02 = '"+ehf020401m0.getEHF020403T0_02()+"' ";  //部門代號
					sqla += " AND EHF020403T0_02 = '"+ehf020401m0.getEHF020403T0_02()+"' ";  //部門代號
				}
				

				
			}else if(ems_access.checkManager(conn, authbean) && ems_access.checkEmsRoleEmp(conn, authbean, "002")){
				
				//主管
				
				sql += "" +
				" AND EHF020403T1_01 IS NOT NULL " ;
//				" AND EHF020403T1_01 = '"+getLoginUser(request).getEmployeeID()+"' " +  //員工工號
				
				sqla += "" +
				" AND EHF020403T1_01 IS NOT NULL " ;
				
				if (!ehf020401m0.getEHF020403T0_02().equals("") && !ehf020401m0.getEHF020403T0_01().equals("")){  //員工
					
					sql += " AND EHF020403T0_02 = '"+ehf020401m0.getEHF020403T0_02()+"' " +  //部門代號
						   " AND EHF020403T1_01 = '"+ehf020401m0.getEHF020403T0_01()+"' ";  //員工工號
					
					sqla += " AND EHF020403T0_02 = '"+ehf020401m0.getEHF020403T0_02()+"' " +  //部門代號
					   		" AND EHF020403T1_01 = '"+ehf020401m0.getEHF020403T0_01()+"' ";  //員工工號
					
				}else if(!ehf020401m0.getEHF020403T0_02().equals("")){  //部門組織
					sql += " AND EHF020403T0_02 = '"+ehf020401m0.getEHF020403T0_02()+"' ";  //部門代號
					sqla += " AND EHF020403T0_02 = '"+ehf020401m0.getEHF020403T0_02()+"' ";  //部門代號
				}else if(ehf020401m0.getEHF020403T0_02().equals("") && ehf020401m0.getEHF020403T0_01().equals("")){
					
					//當部門與員工條件為空白時，取出主管轄下人員的資料
					/*sql += " AND ( 1=0 " ;
					sqla += " AND ( 1=0 " ;
					
					List bossdeplist = this.getEmsBossAllUnderDep(this.emscontext, authbean.getUserId(), authbean.getEmployeeID(),
									   "", authbean.getUserCode(), authbean.getCompId());
					Iterator it = bossdeplist.iterator();
					
					while(it.hasNext()){
						Map tempMap = (Map) it.next();
						sql += " OR EHF020403T0_02 = '"+tempMap.get("DEPT_ID")+"' ";
						sqla += " OR EHF020403T0_02 = '"+tempMap.get("DEPT_ID")+"' ";
					}
					
					sql += " ) ";
					sqla += " ) ";*/
					
				}
				
				
			}else {
				//一般員工
			
				sql += "" +
				" AND EHF020403T1_01 = '"+authbean.getEmployeeID()+"' " ;  //員工工號
				
				sqla += "" +
				" AND EHF020403T1_01 = '"+authbean.getEmployeeID()+"' " ;  //員工工號

			}
			
			if (!"".equals(ehf020401m0.getEHF020401T0_05()) && ehf020401m0.getEHF020401T0_05() != null ) {  //考勤異常年月
				sql += " AND EHF020401T0_05 BETWEEN '"+ehf020401m0.getEHF020401T0_05()+"/01"+"' AND  '"+ehf020401m0.getEHF020401T0_05()+"/31"+"'  ";
				sqla += " AND EHF020406T0_04 BETWEEN '"+ehf020401m0.getEHF020401T0_05()+"/01"+"' AND  '"+ehf020401m0.getEHF020401T0_05()+"/31"+"'  ";
			}else if(!"".equals(this.start_date) && this.start_date != null && !"".equals(this.end_date) && this.end_date != null){  //考勤日期區間
				sql += " AND EHF020401T0_05 BETWEEN '"+this.start_date+"' AND  '"+this.end_date+"'  ";
				sqla += " AND EHF020406T0_04 BETWEEN '"+this.start_date+"' AND  '"+this.end_date+"'  ";
			}else if(!"".equals(this.start_date) && this.start_date != null){
				sql += " AND EHF020401T0_05 >= '"+this.start_date+"' ";
				sqla += " AND EHF020406T0_04 >= '"+this.start_date+"' ";
			}else if(!"".equals(this.end_date) && this.end_date != null){
				sql += " AND EHF020401T0_05 <= '"+this.end_date+"' ";
				sqla += " AND EHF020406T0_04 <= '"+this.end_date+"' ";
			}
			
			if(!"".equals(ehf020401m0.getEHF020401T0_FIX()) && ehf020401m0.getEHF020401T0_FIX() != null ){  //是否修正
				sql += "  AND EHF020401T0_FIX = '"+ehf020401m0.getEHF020401T0_FIX()+"' ";
				sqla += " AND EHF020406T0_FIX = '"+ehf020401m0.getEHF020401T0_FIX()+"' ";
			}
			
			//判斷是否顯示加班考勤
			if(!overtime_data_flag){
				sql += " AND EHF020401T0_08 NOT IN ('5','6') ";
				sqla += " AND EHF020406T0_05 NOT IN ('5','6') ";
			}
			if(!"".equals(receipt_no) && receipt_no != null){
				sql  += " AND EHF020401T0_PS LIKE '%"+receipt_no+"%'";
				sqla += " AND EHF020406T0_PS LIKE '%"+receipt_no+"%' ";
			}
			
			if(!"".equals(card_status) && card_status != null){
				sql += " AND EHF020401T0_08 = '"+card_status+"' ";	//刷卡狀態
				sqla += " AND EHF020406T0_05 = '"+card_status+"' ";
			}
			
			sql = sql + " UNION " + sqla;
			sql += " ORDER BY EHF020403T0_02, EHF020403T1_01, EHF020401T0_05, EHF020401T0_07 ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				EHF020401M0F bean = new EHF020401M0F();
				bean.setEHF020401T0_UID(rs.getString("UID"));  //UID
				bean.setEHF020401T0_SUID(rs.getString("SUID"));  //SUID
				bean.setEHF020401T0_TYPE(rs.getString("TYPE"));  //表單別
				bean.setEHF020401T0_05(rs.getString("CARD_DATE"));  //考勤日期
//				bean.setEHF020401T0_05_DATE();  //打卡日期
				bean.setEHF020401T0_DATE(rs.getString("DATE1")); //考勤日期 yyyy/mm/dd
				bean.setEHF020403T0_01(rs.getString("EHF020403T0_01"));
				//System.out.println(rs.getString("EHF020403T1_01"));
				bean.setEHF020403T0_02((String) ((Map)empMap.get(rs.getString("EHF020403T1_01"))).get("SHOW_DESC") 
									   +" / "+ 
									   (String) ((Map)empMap.get(rs.getString("EHF020403T1_01"))).get("EMPLOYEE_NAME") );  //員工姓名
				//System.out.println(rs.getString("EHF020403T1_01") +" / "+ (String) ((Map)empMap.get(rs.getString("EHF020403T1_01"))).get("EMPLOYEE_NAME"));
				bean.setEHF020403T0_03((String) ((Map)depMap.get(rs.getString("EHF020403T0_02"))).get("SHOW_DESC"));  //部門代號
				bean.setEHF020401T0_04(rs.getString("EHF000400T0_03")+" / "+ rs.getString("EHF000400T0_04"));  //班別名稱
				bean.setEHF020401T0_08(rs.getString("EHF020401T0_08"));  //打卡狀態代碼
				bean.setEHF020401T0_06(rs.getString("CARD_DATETIME"));  //打卡日期時間
				bean.setEHF020401T0_05_DATE(rs.getString("CARD_TIME"));  //打卡時間
				
				//bean.setEHF020401T0_09_DESC(rs.getString("EHF020401T0_09_DESC"));  //異常說明
				bean.setEHF020401T0_09_DESC(rs.getString("ERROR_DESC"));  //異常說明
				
				bean.setEHF020401T0_10(rs.getString("DATE2"));  //考勤日期 mm/dd
				bean.setEHF020401T0_FIX(rs.getString("EHF020401T0_FIX"));  //是否已修正
				bean.setEHF020401T0_PS(rs.getString("PS"));  //修正備註
				
				/*
				//TYPE == '02' 表示是未打卡單
				if( "02".equals(rs.getString("TYPE")) && "1".equals(rs.getString("EHF020401T0_08")) ){  //上班未打卡
					//取得班別資訊
					classMap = this.getEmpClassByNo( conn, authbean.getCompId(), rs.getInt("CLASS_NO"));
					
					bean.setEHF020401T0_07(rs.getString("EHF020401T0_07")+" "+
					((String)classMap.get("WORK_START_TIME")).substring(0, 1)+":"+
					((String)classMap.get("WORK_START_TIME")).substring(2, 3)+":00");  //打卡日期時間
				}else if( "02".equals(rs.getString("TYPE")) && "2".equals(rs.getString("EHF020401T0_08")) ){  //下班未打卡
					//取得班別資訊
					classMap = this.getEmpClassByNo( conn, authbean.getCompId(), rs.getInt("CLASS_NO"));
					
					bean.setEHF020401T0_07(rs.getString("EHF020401T0_07")+" "+
							((String)classMap.get("WORK_END_TIME")).substring(0, 1)+":"+
							((String)classMap.get("WORK_END_TIME")).substring(2, 3)+":00");  //打卡日期時間
				}else{
					
					bean.setEHF020401T0_07(rs.getString("EHF020401T0_07"));  //打卡日期時間
				}
				*/
				
				bean.setEHF020401T0_07(rs.getString("EHF020401T0_07"));  //打卡日期時間&未打卡日期時間
				bean.setEHF020401T0_08_STATUS( (String) tools.getCardStatus(Integer.parseInt(rs.getString("EHF020401T0_08"))) );  //打卡狀態
				bean.setEHF020401T0_11(authbean.getCompId());  //公司代碼

				list.add(bean);
			}
			
			rs.close();
			stmt.close();
			
			hr_tools.close();
			
		}catch(Exception e){
			System.out.println("取得考勤異常資料清單時，發生錯誤："+e);
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 系統自動修正考勤異常資料
	 * @param conn
	 * @param ehf020401m0
	 * @return
	 */
	public boolean autoFixAttAbnormal(Connection conn, EMS_AttendanceAction ems_att_tools, EHF020401M0F ehf020401m0, String user_id ){
		
		boolean chk_flag = false;
		Map chg_map = null;
		
		try{
			//檢核異常考勤資料
			chg_map = this.chkForRestConflict(conn, ems_att_tools, ehf020401m0.getEHF020401T0_TYPE(), ehf020401m0.getEHF020401T0_UID(),
											   ehf020401m0.getEHF020401T0_11());
			
			boolean chg_flag = (Boolean) chg_map.get("CHK_FLAG");
			String card_status = (String) chg_map.get("ATT_TYPE");
			
			//請假單
			String sql = "" +
			" SELECT EHF020200T0_01, " +
			" CONCAT( DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020200T0_11, 1, 2), ':', " +
			" SUBSTRING(EHF020200T0_11, 3, 2), ':00' ) AS START_DATE, " +
			" CONCAT( DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020200T0_12, 1, 2), ':', " +
			" SUBSTRING(EHF020200T0_12, 3, 2), ':00' ) AS END_DATE, " +
			" '01' AS TYPE, '請假單' AS TYPE_DESC " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+ehf020401m0.getEHF020403T0_01()+"' ";  //員工工號
			if( chg_flag && "1".equals(card_status)){
				//上班遲到
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020200T0_11, 1, 2), ':', SUBSTRING(EHF020200T0_11, 3, 2), ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_END_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_END_MINUTE")+"', ':00' ) ) " +
				" AND ( CONCAT( DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020200T0_12, 1, 2), ':', SUBSTRING(EHF020200T0_12, 3, 2), ':00' ) >=  " +
				" CONCAT( DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_START_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_START_MINUTE")+"', ':00' ) ) ";
			}else if( chg_flag && "2".equals(card_status) ){
				//下班早退
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_START_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_START_MINUTE")+"', ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020200T0_12, 1, 2), ':', SUBSTRING(EHF020200T0_12, 3, 2), ':00' ) ) " +
				" AND ( CONCAT( DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020200T0_11, 1, 2), ':', SUBSTRING(EHF020200T0_11, 3, 2), ':00' ) <=  " +
				" CONCAT( DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_END_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_END_MINUTE")+"', ':00' ) ) ";
			}else{
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020200T0_11, 1, 2), ':', SUBSTRING(EHF020200T0_11, 3, 2), ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020200T0_12, 1, 2), ':', SUBSTRING(EHF020200T0_12, 3, 2), ':00' ) ) ";
			}
			sql += "" +
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = '0006' 完成
			" AND EHF020200T0_18 = '"+ehf020401m0.getEHF020401T0_11()+"' "; //公司代碼
			
			sql += " UNION ";
			
			//出差單
			sql += "" +
			" SELECT EHF020300T0_01, " +
			" CONCAT( DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020300T0_11, 1, 2), ':', " +
			" SUBSTRING(EHF020300T0_11, 3, 2), ':00' ) AS START_DATE, " +
			" CONCAT( DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020300T0_12, 1, 2), ':', " +
			" SUBSTRING(EHF020300T0_12, 3, 2), ':00' ) AS END_DATE, " +
			" '02' AS TYPE, '出差單' AS TYPE_DESC " +
			" FROM EHF020300T0 " +
			" WHERE 1=1 " +
			" AND EHF020300T0_03 = '"+ehf020401m0.getEHF020403T0_01()+"' ";  //員工工號
			if( chg_flag && "1".equals(card_status)){
				//上班遲到
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020300T0_11, 1, 2), ':', SUBSTRING(EHF020300T0_11, 3, 2), ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_END_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_END_MINUTE")+"', ':00' ) ) " +
				" AND ( CONCAT( DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020300T0_12, 1, 2), ':', SUBSTRING(EHF020300T0_12, 3, 2), ':00' ) >=  " +
				" CONCAT( DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_START_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_START_MINUTE")+"', ':00' ) ) ";
			}else if( chg_flag && "2".equals(card_status) ){
				//下班早退
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_START_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_START_MINUTE")+"', ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020300T0_12, 1, 2), ':', SUBSTRING(EHF020300T0_12, 3, 2), ':00' ) ) " +
				" AND ( CONCAT( DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020300T0_11, 1, 2), ':', SUBSTRING(EHF020300T0_11, 3, 2), ':00' ) <=  " +
				" CONCAT( DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_END_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_END_MINUTE")+"', ':00' ) ) ";
			}else{
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020300T0_11, 1, 2), ':', SUBSTRING(EHF020300T0_11, 3, 2), ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020300T0_12, 1, 2), ':', SUBSTRING(EHF020300T0_12, 3, 2), ':00' ) ) ";
			}
			/*
			sql+= ""+
			" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
			" CONCAT( DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020300T0_11, 1, 2), ':', SUBSTRING(EHF020300T0_11, 3, 2), ':00' ) " +
			" AND " +
			" CONCAT( DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020300T0_12, 1, 2), ':', SUBSTRING(EHF020300T0_12, 3, 2), ':00' ) ) ";
			*/
			sql += "" +
			" AND EHF020300T0_20 = '0006' " + //表單狀態 = '0006' 完成
			" AND EHF020300T0_21 = '"+ehf020401m0.getEHF020401T0_11()+"' ";  //公司代碼
			
			sql += " UNION ";
			
			//公務單
			sql += "" +
			" SELECT EHF020600T0_01, " +
			" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020600T0_10, 1, 2), ':', " +
			" SUBSTRING(EHF020600T0_10, 3, 2), ':00' ) AS START_DATE, " +
			" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020600T0_11, 1, 2), ':', " +
			" SUBSTRING(EHF020600T0_11, 3, 2), ':00' ) AS END_DATE, " +
			" '03' AS TYPE, '公務單' AS TYPE_DESC " +
			" FROM EHF020600T0 " +
			" WHERE 1=1 " +
			" AND EHF020600T0_03 = '"+ehf020401m0.getEHF020403T0_01()+"' ";  //員工工號
			if( chg_flag && "1".equals(card_status)){
				//上班遲到
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020600T0_10, 1, 2), ':', SUBSTRING(EHF020600T0_10, 3, 2), ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_END_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_END_MINUTE")+"', ':00' ) ) " +
				" AND ( CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020600T0_11, 1, 2), ':', SUBSTRING(EHF020600T0_11, 3, 2), ':00' ) >=  " +
				" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_START_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_START_MINUTE")+"', ':00' ) ) ";
			}else if( chg_flag && "2".equals(card_status) ){
				//下班早退
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_START_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_START_MINUTE")+"', ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020600T0_11, 1, 2), ':', SUBSTRING(EHF020600T0_11, 3, 2), ':00' ) ) " +
				" AND ( CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020600T0_10, 1, 2), ':', SUBSTRING(EHF020600T0_10, 3, 2), ':00' ) <=  " +
				" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" '"+(String)chg_map.get("NOON_REST_END_HOUR")+"', ':', '"+(String)chg_map.get("NOON_REST_END_MINUTE")+"', ':00' ) ) ";
			}else{
				sql += "" +
				" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
				" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020600T0_10, 1, 2), ':', SUBSTRING(EHF020600T0_10, 3, 2), ':00' ) " +
				" AND " +
				" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', " +
				" SUBSTRING(EHF020600T0_11, 1, 2), ':', SUBSTRING(EHF020600T0_11, 3, 2), ':00' ) ) ";
			}
			/*
			sql += "" +
			" AND ( '"+ehf020401m0.getEHF020401T0_07()+"' BETWEEN " +
			" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020600T0_10, 1, 2), ':', SUBSTRING(EHF020600T0_10, 3, 2), ':00' ) " +
			" AND " +
			" CONCAT( DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d' ), ' ', SUBSTRING(EHF020600T0_11, 1, 2), ':', SUBSTRING(EHF020600T0_11, 3, 2), ':00' ) ) ";
			*/
			sql += "" +
			" AND EHF020600T0_17 = '0006' " +  //表單狀態 = '0006' 完成
			" AND EHF020600T0_18 = '"+ehf020401m0.getEHF020401T0_11()+"' ";  //公司代碼
			
			sql += " UNION ";
			
			//未打卡單
			sql += "" +
			" SELECT EHF020700T0_01, " +
			" EHF020700T0_08 AS START_DATE, " +
			" EHF020700T0_08 AS END_DATE, " +
			" '04' AS TYPE, '未打卡單' AS TYPE_DESC " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_03 = '"+ehf020401m0.getEHF020403T0_01()+"' " +  //員工工號
			" AND EHF020700T0_08 = '"+ehf020401m0.getEHF020401T0_DATE()+"' " +
			" AND ( (CASE EHF020700T0_09 WHEN '01' THEN 1 WHEN '02' THEN 2 END) = "+ehf020401m0.getEHF020401T0_08()+" " +
			"      OR ( EHF020700T0_09 = '03' AND ( "+ehf020401m0.getEHF020401T0_08()+" = 1 OR "+ehf020401m0.getEHF020401T0_08()+" = 2 ) ) ) " +
			" AND EHF020700T0_14 = '0006' " +  //表單狀態 = '0006' 完成
			" AND EHF020700T0_15 = '"+ehf020401m0.getEHF020401T0_11()+"' ";  //公司代碼
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//有找到可修正考勤異常資料的表單
				this.fixAttAbnormalData(conn, ehf020401m0, "01", rs.getString("EHF020200T0_01"),
						rs.getString("TYPE")+":"+"由EMS系統修正→"+rs.getString("TYPE_DESC")+":"+rs.getString("EHF020200T0_01"),//新增TYPE  以便以後判斷是什麼單子20131017新增   BY賴泓錡
						"EMS系統", user_id);
				
				chk_flag = true;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			System.out.println("自動修正考勤異常資料時，發生錯誤："+e);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 手動修正考勤異常資料
	 * @param conn
	 * @param ehf020401m0
	 * @param emp_id
	 * @param emp_name
	 * @return
	 */
	public boolean fixAttAbnormal(Connection conn, EHF020401M0F ehf020401m0, String emp_id, String emp_name, String user_id ){
		
		boolean chk_flag = false;
		
		try{
			this.fixAttAbnormalData(conn, ehf020401m0, "02", emp_id, "由人資經辦:"+emp_id+"/"+emp_name+" 修正, 原因:"+ehf020401m0.getEHF020401T0_PS(),
								    emp_name, user_id);
			
			chk_flag = true;
			
		}catch(Exception e){
			chk_flag = false;
			System.out.println("強制修正考勤異常資料時，發生錯誤："+e);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 手動還原考勤異常資料
	 * @param conn
	 * @param ehf020401m0
	 * @param emp_id
	 * @param emp_name
	 * @return
	 */
	public boolean recoveryFixAttAbnormal(Connection conn, EHF020401M0F ehf020401m0, String emp_name, String user_id ){
		
		boolean chk_flag = false;
		
		try{
			this.recoveryFixAttAbnormalData(conn, ehf020401m0, emp_name, user_id );
			
			chk_flag = true;
			
		}catch(Exception e){
			chk_flag = false;
			System.out.println("強制修正考勤異常資料時，發生錯誤："+e);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 更新修正的考勤或未打卡資料
	 * @param conn
	 * @param ehf020401m0
	 * @param ds_type
	 * @param fid
	 * @param ps
	 * @param user_name
	 */
	private void fixAttAbnormalData(Connection conn, EHF020401M0F ehf020401m0, String ds_type, String fid, 
									String ps, String user_name, String user_id ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			String sql = "";
			
			PreparedStatement pstmt = null;
			P6PreparedStatement p6stmt = null;
			
			if("01".equals(ehf020401m0.getEHF020401T0_TYPE())){
				//EHF020401T0考勤資料修正	
				
				sql = "" +
				" UPDATE EHF020401T0 " +
				" SET EHF020401T0_FIX=?, EHF020401T0_FDS=?, EHF020401T0_FID=?, " +
				" EHF020401T0_PS=?, " +
				" USER_UPDATE=?, VERSION = VERSION+1, DATE_UPDATE = NOW() " +
				" WHERE 1=1 " +
				" AND EHF020401T0_01 = ? " +  //出勤系統號
				" AND EHF020401T0_11 = ? ";  //公司代碼
				
				pstmt = conn.prepareStatement(sql);
				p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setBoolean(indexid, true);  //是否已修正
				indexid++;
				p6stmt.setString(indexid, ds_type);  //修正來源
				indexid++;
				p6stmt.setString(indexid, fid);  //修正的表單UID或人資經辦EMPID
				indexid++;
				p6stmt.setString(indexid, ps);  //修正備註
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				p6stmt.setString(indexid, ehf020401m0.getEHF020401T0_UID());  //出勤系統號
				indexid++;
				p6stmt.setString(indexid, ehf020401m0.getEHF020401T0_11());  //公司代碼
				indexid++;
				
			}else if("02".equals(ehf020401m0.getEHF020401T0_TYPE())){
				//EHF020406T0未打卡資料修正
				
				String[] UIDs = ehf020401m0.getEHF020401T0_UID().split(",");  //未打卡資料有三個Key
				
				sql = "" +
				" UPDATE EHF020406T0 " +
				" SET EHF020406T0_FIX=?, EHF020406T0_FDS=?, EHF020406T0_FID=?, " +
				" EHF020406T0_PS=?, " +
				" USER_UPDATE=?, VERSION = VERSION+1, DATE_UPDATE = NOW() " +
				" WHERE 1=1 " +
				" AND EHF020406T0_01 = ? " +  //員工工號
				" AND EHF020406T0_04 = ? " +  //未打卡日期(考勤日期)
				" AND EHF020406T0_05 = ? " +  //未打卡狀態
				" AND EHF020406T0_07 = ? ";  //公司代碼
				
				pstmt = conn.prepareStatement(sql);
				p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setBoolean(indexid, true);  //是否已修正
				indexid++;
				p6stmt.setString(indexid, ds_type);  //修正來源
				indexid++;
				p6stmt.setString(indexid, fid);  //修正的表單UID或人資經辦EMPID
				indexid++;
				p6stmt.setString(indexid, ps);  //修正備註
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				p6stmt.setString(indexid, UIDs[0]);  //員工工號
				indexid++;
				p6stmt.setString(indexid, UIDs[1]);  //未打卡日期(考勤日期)
				indexid++;
				p6stmt.setString(indexid, UIDs[2]);  //未打卡狀態
				indexid++;
				p6stmt.setString(indexid, ehf020401m0.getEHF020401T0_11());  //公司代碼
				indexid++;
				
			}
			
//			System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			
			//同步連動修正出勤資料(EHF020410T0)
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			//String ems_version = tools.getSysParam(conn, ehf020401m0.getEHF020401T0_11(), "EMS_VERSION");
			
			//if("1.6".equals(ems_version)){
				//建立 遲到早退曠職元件
				EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
				//ems_abatt_handle.setOutside_conn(conn);  //設定外部Connection
				
				//取得考勤資訊
				Map att_map = this.getAttDateBySUID(conn, 
							  ehf020401m0.getEHF020401T0_SUID(), 
							  ehf020401m0.getEHF020401T0_11(), 
							  user_name);
				
				//同步修正出勤資料
				ems_abatt_handle.putFixAttendanceRecord(
								 conn,
								 (String)att_map.get("EMP_ID"), 
								 (String)att_map.get("ATT_DATE"), (String) att_map.get("CARD_STATUS"), 
								 ds_type, fid, ps, 
								 user_name, user_id, 
								 ehf020401m0.getEHF020401T0_11());
				
				//關閉遲到早退曠職元件
				ems_abatt_handle.close();
				
			//}
			
			
			
		}catch(Exception e){
			System.out.println("更新考勤異常資料時，發生錯誤："+e);
			e.printStackTrace();
		}
		
		
	}
		
	/**
	 * 更新還原的考勤或未打卡資料
	 * @param conn
	 * @param ehf020401m0
	 * @param user_name
	 */
	private void recoveryFixAttAbnormalData(Connection conn, EHF020401M0F ehf020401m0, String user_name, String user_id ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			String sql = "";

			PreparedStatement pstmt = null;
			P6PreparedStatement p6stmt = null;

			if("01".equals(ehf020401m0.getEHF020401T0_TYPE())){
				//EHF020401T0考勤資料還原	
				sql = "" +
				" UPDATE EHF020401T0 " +
				" SET EHF020401T0_FIX=?, EHF020401T0_FDS=?, EHF020401T0_FID=?, " +
				" EHF020401T0_PS=?, " +
				" USER_UPDATE=?, VERSION = VERSION+1, DATE_UPDATE = NOW() " +
				" WHERE 1=1 " +
				" AND EHF020401T0_01 = ? " +  //出勤系統號
				" AND EHF020401T0_11 = ? ";  //公司代碼
				
				pstmt = conn.prepareStatement(sql);
				p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;

				p6stmt.setBoolean(indexid, false);  //是否已修正
				indexid++;
				p6stmt.setString(indexid, "");  //修正來源
				indexid++;
				p6stmt.setString(indexid, "");  //修正的表單UID或人資經辦EMPID
				indexid++;
				p6stmt.setString(indexid, "");  //修正備註
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				p6stmt.setString(indexid, ehf020401m0.getEHF020401T0_UID());  //出勤系統號
				indexid++;
				p6stmt.setString(indexid, ehf020401m0.getEHF020401T0_11());  //公司代碼
				indexid++;
				
			}else if("02".equals(ehf020401m0.getEHF020401T0_TYPE())){
				//EHF020406T0未打卡資料還原
				
				String[] UIDs = ehf020401m0.getEHF020401T0_UID().split(",");  //未打卡資料有三個Key
				
				sql = "" +
				" UPDATE EHF020406T0 " +
				" SET EHF020406T0_FIX=?, EHF020406T0_FDS=?, EHF020406T0_FID=?, " +
				" EHF020406T0_PS=?, " +
				" USER_UPDATE=?, VERSION = VERSION+1, DATE_UPDATE = NOW() " +
				" WHERE 1=1 " +
				" AND EHF020406T0_01 = ? " +  //員工工號
				" AND EHF020406T0_04 = ? " +  //未打卡日期(考勤日期)
				" AND EHF020406T0_05 = ? " +  //未打卡狀態
				" AND EHF020406T0_07 = ? ";  //公司代碼
				
				pstmt = conn.prepareStatement(sql);
				p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setBoolean(indexid, false);  //是否已修正
				indexid++;
				p6stmt.setString(indexid, "");  //修正來源
				indexid++;
				p6stmt.setString(indexid, "");  //修正的表單UID或人資經辦EMPID
				indexid++;
				p6stmt.setString(indexid, "");  //修正備註
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				p6stmt.setString(indexid, UIDs[0]);  //員工工號
				indexid++;
				p6stmt.setString(indexid, UIDs[1]);  //未打卡日期(考勤日期)
				indexid++;
				p6stmt.setString(indexid, UIDs[2]);  //未打卡狀態
				indexid++;
				p6stmt.setString(indexid, ehf020401m0.getEHF020401T0_11());  //公司代碼
				indexid++;
				
			}

			System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());

			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			//同步連動還原修正出勤資料(EHF020410T0)
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			//String ems_version = tools.getSysParam(conn, ehf020401m0.getEHF020401T0_11(), "EMS_VERSION");
			
			//if("1.6".equals(ems_version)){
				//建立 遲到早退曠職元件
				EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
				//ems_abatt_handle.setOutside_conn(conn);  //設定外部Connection
				
				//取得考勤資訊
				Map att_map = this.getAttDateBySUID(conn, 
							  ehf020401m0.getEHF020401T0_SUID(), 
							  ehf020401m0.getEHF020401T0_11(), 
							  user_name);
				
				//同步還原修正出勤資料
				ems_abatt_handle.recoveryFixAttendanceRecord(
								 conn,
								 (String)att_map.get("EMP_ID"), 
								 (String)att_map.get("ATT_DATE"), (String) att_map.get("CARD_STATUS"), 
								 user_name, user_id, 
								 ehf020401m0.getEHF020401T0_11());
				
				//關閉遲到早退曠職元件
				ems_abatt_handle.close();
				
			//}
			
		}catch(Exception e){
			System.out.println("更新考勤異常資料時，發生錯誤："+e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 依據SUID取得考勤日期
	 * @param conn
	 * @param SUID
	 * @param comp_id
	 * @param user_name
	 * @return
	 */
	public Map getAttDateBySUID(Connection conn, String SUID, String comp_id, String user_name ){
		
		Map Att_Map = new HashMap();
		
		try{
			String sql = "";
			//解析SUID
			String[] UID = SUID.split("@");
			
			if("01".equals(UID[1])){
				sql = "" +
				" SELECT EHF020401T0_05 AS ATT_DATE," +
				" EHF020403T1_01 AS EMP_ID, " +
				" EHF020401T0_02 AS CARD_NO, " +
				" EHF020401T0_08 AS CARD_STATUS, " +
				" EHF020401T0_04 AS CLASS_NO " +
				" FROM EHF020401T0 " +
				" LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
				" WHERE 1=1 " +
				" AND EHF020401T0_01 = '"+UID[0]+"' " +  //出勤系統號
				" AND EHF020401T0_11 = '"+comp_id+"' ";  //公司代碼
			}else if("02".equals(UID[1])){
				
				String[] UIDs = UID[0].split(",");  //未打卡資料有三個Key
				
				sql = "" +
				" SELECT EHF020406T0_04 AS ATT_DATE," +
				" EHF020406T0_01 AS EMP_ID, " +
				" '' AS CARD_NO, " +
				" EHF020406T0_05 AS CARD_STATUS, " +
				" EHF020406T0_03 AS CLASS_NO " +
				" FROM EHF020406T0 " +
				" WHERE 1=1 " +
				" AND EHF020406T0_01 = '"+UIDs[0]+"' " +  //員工工號
				" AND EHF020406T0_04 = '"+UIDs[1]+"' " +  //未打卡日期(考勤日期)
				" AND EHF020406T0_05 = '"+UIDs[2]+"' " +  //未打卡狀態
				" AND EHF020406T0_07 = '"+comp_id+"' ";  //公司代碼
			}
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//取得考勤資訊
				Att_Map.put("ATT_DATE", rs.getString("ATT_DATE"));
				Att_Map.put("EMP_ID", rs.getString("EMP_ID"));
				Att_Map.put("CARD_NO", rs.getString("CARD_NO"));
				Att_Map.put("CARD_STATUS", rs.getString("CARD_STATUS"));
				Att_Map.put("CLASS_NO", rs.getString("CLASS_NO"));
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return Att_Map;
	}
	
	/**
	 * 依據考勤日期取得考勤資料
	 * @param conn
	 * @param emloyee_id
	 * @param att_date
	 * @param card_status
	 * @param user_id
	 * @param comp_id
	 * @return
	 */
	public ArrayList getAttDatasByAttDate( Connection conn, String employee_id, String att_date, String card_status, 
										   String user_id, String comp_id ){
		
		ArrayList list = new ArrayList();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			String sql = "";
			sql = this.getAttDatasSQL(conn, employee_id, att_date, card_status, comp_id);  //取得考勤SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			//取得員工Map
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
			
			while(rs.next()){
				EHF020401M0F bean = new EHF020401M0F();
				bean.setEHF020401T0_TYPE(rs.getString("FORM_TYPE"));  //表單類型
				bean.setEHF020401T0_05(att_date);  //考勤日期
				bean.setEHF020401T0_05_DATE(rs.getString("CARD_DATE"));  //日期
				bean.setEHF020401T0_02_EMP( rs.getString("EHF020403T1_01") +" / "+
										    (String) ((Map)empMap.get(rs.getString("EHF020403T1_01"))).get("EMPLOYEE_NAME") );  //員工姓名
				bean.setEHF020401T0_02(rs.getString("CARD_NO"));  //感應卡號
				bean.setEHF020403T1_03(rs.getString("CLASS_NO"));  //班別序號
				bean.setEHF020401T0_04( rs.getString("EHF010100T0_03")+" / "+ rs.getString("EHF010100T0_04"));  //班別名稱
				bean.setEHF020401T0_08( (String) tools.getCardStatus(Integer.parseInt(rs.getString("EHF020401T0_08"))) );  //打卡狀態
				bean.setEHF020401T0_08_STATUS(rs.getString("EHF020401T0_08"));  //打卡狀態代碼
				bean.setEHF020401T0_06(rs.getString("CARD_TIME"));  //打卡時間
				bean.setEHF020401T0_07(rs.getString("DATETIME"));  //打卡日期
				bean.setEHF020401T0_07_HH(rs.getString("HOUR"));  //打卡時
				bean.setEHF020401T0_07_MM(rs.getString("MINUTE"));  //打卡分
				bean.setEHF020401T0_09(rs.getString("EHF020401T0_09"));  //是否異常
				
				//bean.setEHF020401T0_09_DESC(rs.getString("EHF020401T0_09_DESC"));  //異常說明
				bean.setEHF020401T0_09_DESC(rs.getString("ERROR_DESC"));  //異常說明
				
				list.add(bean);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 依據考勤日期與員工工號取得考勤資料
	 * @param employee_id
	 * @param att_date
	 * @param card_status
	 * @param comp_id
	 * @return
	 */
	protected String getAttDatasSQL( Connection conn, String emloyee_id, String att_date, String card_status, String comp_id ){
		
		String sql = "";
		String sqla = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			String ems_version = tools.getSysParam(conn, comp_id, "EMS_VERSION");
			
			sql = "" +
			" SELECT EHF020401T0_05, " +
			" DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d %H:%i:%s') AS EHF020401T0_07, " +
			" EHF020401T0_08, EHF020401T0_09, ";
			
			//處理考勤異常說明
			if("1.6".equals(ems_version)){
				sql += "" +
				" CASE D.EHF020410T0_06 " +
				" WHEN '00' THEN '正常' " +
				" WHEN '01' THEN '遲到' " +
				" WHEN '02' THEN '早退' " +
				" WHEN '03' THEN '曠職' " +
				" WHEN '04' THEN '未打卡' " +
				" END AS ERROR_DESC, ";
			}else{
				sql += " EHF020401T0_09_DESC AS ERROR_DESC, ";
			}
			
			sql += "" +
			"B.EHF020403T1_01 AS EHF020403T1_01, " +
			" A.EHF020403T0_02 AS EHF020403T0_02, " +
			" DATE_FORMAT(EHF020401T0_07, '%Y年%m月%d日') AS CARD_DATE, " +
			" DATE_FORMAT(EHF020401T0_07, '%k點%i分') AS CARD_TIME, " +
			" DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d') AS DATETIME, " +
			" DATE_FORMAT(EHF020401T0_07, '%H') AS HOUR, " +
			" DATE_FORMAT(EHF020401T0_07, '%i') AS MINUTE, " +
			" C.EHF010100T0_04 AS EHF010100T0_04, C.EHF010100T0_03 AS EHF010100T0_03, " +
			" EHF020401T0_04 AS CLASS_NO, EHF020401T0_02 AS CARD_NO, '01' AS FORM_TYPE, " +
			" EHF020401T0_11 " +
			" FROM EHF020401T0 " +
			" LEFT JOIN EHF020403T1 B ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
			" LEFT JOIN EHF020403T0 A ON EHF020403T0_01 = EHF020403T1_01 AND EHF020403T0_04 = EHF020401T0_11 " +
			" LEFT JOIN EHF010100T0 C ON EHF010100T0_01 = EHF020401T0_04 AND EHF010100T0_11 = EHF020401T0_11 ";
			
			if("1.6".equals(ems_version)){
				//處理1.6版本的考勤異常說明資料表
				sql += "" +
				" LEFT JOIN EHF020410T0 D ON EHF020410T0_01 = EHF020401T0_02_EMP AND EHF020410T0_03 = EHF020401T0_05 " +
				" AND EHF020410T0_04 = EHF020401T0_08 AND EHF020410T0_08 = EHF020401T0_11 ";
				//" AND EHF020410T0_05 = EHF020401T0_09 ";
			}
			
			sql += "" +
			" WHERE 1=1 " +
			" AND B.EHF020403T1_01 IS NOT NULL " +
			" AND EHF020401T0_11 = '"+comp_id+"' " ;  //公司代碼
			
			if(!"".equals(emloyee_id) && emloyee_id != null ){
				sql += " AND EHF020403T1_01 = '"+emloyee_id+"' ";  //員工工號
			}
			
			if(!"".equals(att_date) && att_date != null ){
				sql += " AND EHF020401T0_05 = '"+att_date+"' ";  //考勤日期
			}
			
			if(!"".equals(card_status) && card_status != null ){
				sql += " AND EHF020401T0_08 = '"+card_status+"' ";  //狀態
			}
			
			sqla = "" +
			" SELECT EHF020406T0_04, " +
			" DATE_FORMAT(EHF020406T0_04_DTE, '%Y/%m/%d %H:%i:%s') AS EHF020401T0_07, " +
			" EHF020406T0_05, EHF020406T0_05_FLG, ";
			
			//處理考勤異常說明
			if("1.6".equals(ems_version)){
				sqla += "" +
				" CASE UD.EHF020410T0_06 " +
				" WHEN '00' THEN '正常' " +
				" WHEN '01' THEN '遲到' " +
				" WHEN '02' THEN '早退' " +
				" WHEN '03' THEN '曠職' " +
				" WHEN '04' THEN '未打卡' " +
				" END AS ERROR_DESC, ";
			}else{
				sqla += "" +
				" EHF020406T0_06 AS ERROR_DESC, ";
			}
			
			sqla += "" +
			"UB.EHF020403T1_01 AS EHF020403T1_01, " +
			" UA.EHF020403T0_02 AS EHF020403T0_02, " +
			" DATE_FORMAT( STR_TO_DATE( EHF020406T0_04 , '%Y/%m/%d' ), '%Y年%m月%d日' ) AS CARD_DATE, " +
			" '' AS CARD_TIME, " +
			" DATE_FORMAT(EHF020406T0_04_DTE, '%Y/%m/%d') AS DATETIME, " +
			" DATE_FORMAT(EHF020406T0_04_DTE, '%H') AS HOUR, " +
			" DATE_FORMAT(EHF020406T0_04_DTE, '%i') AS MINUTE, " +
			" UC.EHF010100T0_04 AS EHF010100T0_04, UC.EHF010100T0_03 AS EHF010100T0_03, " +
			" EHF020406T0_03 AS CLASS_NO, UB.EHF020403T1_02 AS CARD_NO, '02' AS FORM_TYPE, " +
			" EHF020406T0_07 " +
			" FROM EHF020406T0 " +
			" LEFT JOIN EHF020403T1 UB ON EHF020403T1_01 = EHF020406T0_01 AND EHF020403T1_06 = EHF020406T0_07 " +
			" LEFT JOIN EHF020403T0 UA ON EHF020403T0_01 = EHF020406T0_01 AND EHF020403T0_04 = EHF020406T0_07 " +
			" LEFT JOIN EHF010100T0 UC ON EHF010100T0_01 = EHF020406T0_03 AND EHF010100T0_11 = EHF020406T0_07 ";
			
			if("1.6".equals(ems_version)){
				//處理1.6版本的考勤異常說明資料表
				sqla += "" +
				" LEFT JOIN EHF020410T0 UD ON EHF020410T0_01 = EHF020406T0_01 AND EHF020410T0_03 = EHF020406T0_04 " +
				" AND EHF020410T0_04 = EHF020406T0_05 AND EHF020410T0_08 = EHF020406T0_07 ";
				//" AND EHF020410T0_05 = EHF020406T0_05_FLG ";
			}
			
			sqla += "" +
			" WHERE 1=1 " +
			" AND UB.EHF020403T1_01 IS NOT NULL " +
			" AND EHF020406T0_07 = '"+comp_id+"' " ;  //公司代碼
			
			if(!"".equals(emloyee_id) && emloyee_id != null ){
				sqla += " AND EHF020406T0_01 = '"+emloyee_id+"' ";  //員工工號
			}
			
			if(!"".equals(att_date) && att_date != null ){
				sqla += " AND EHF020406T0_04 = '"+att_date+"' ";  //考勤日期
			}
			
			if(!"".equals(card_status) && card_status != null ){
				sqla += " AND EHF020406T0_05 = '"+card_status+"' ";  //狀態
			}
			
			sql = "" +
			" SELECT " +
			" * " +
			" FROM " +
			" ( " +
			"" + sql + " UNION " + sqla +
			" ) tableA " +
			" WHERE 1=1 ";
			
			//GROUP BY 員工工號
			sql += " GROUP BY EHF020403T1_01, EHF020401T0_05, EHF020401T0_08  ";
			//Order by 部門代號, 員工工號, 考勤日期, 打卡日期時間
			sql += " ORDER BY EHF020403T0_02, EHF020403T1_01 , EHF020401T0_05, EHF020401T0_07 ";

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sql;
	}
	
	/**
	 * 執行調班功能
	 * @param conn
	 * @param class_no
	 * @param employee_id
	 * @param att_date
	 * @param user_id
	 * @param comp_id
	 * @return
	 */
	public Map transferClass( Connection conn, String class_no, String employee_id, String att_date, 
							  String user_id, String user_name, String comp_id,
							  boolean commit_flag ){
		
		ArrayList list = new ArrayList();
		ArrayList list2 = new ArrayList();
		//EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		Map transClassInf = new HashMap();
		
		try{
			if(!"".equals(employee_id) && employee_id != null && !"".equals(att_date) 
					   && att_date != null && !"".equals(class_no) && class_no != null ){
				//取得調班前的考勤資料
				//list = fix_att_tools.getAttDatasByAttDate( conn, employee_id, att_date, "", user_id, comp_id);
				list = this.getAttDatasByAttDate( conn, employee_id, att_date, "", user_id, comp_id);
				
				//實際執行調班處理
				this.changeClass( conn, Integer.parseInt(class_no), employee_id, att_date, 
								  false, true, user_id, user_name, comp_id, commit_flag);
				
				//取得調班後的考勤資料
				//list2 = fix_att_tools.getAttDatasByAttDate( conn, employee_id, att_date, "", user_id, comp_id);
				list2 = this.getAttDatasByAttDate( conn, employee_id, att_date, "", user_id, comp_id);
			}
			
			transClassInf.put("BEFORE_LIST", list);
			transClassInf.put("AFTER_LIST", list2);
			
		}catch(Exception e){
			System.out.println("執行調班功能時, 發生錯誤!!");
			e.printStackTrace();
		}
		
		return transClassInf;
	}
	
	/**
	 * 實際執行排班表變更與考勤資料轉換
	 * @param conn
	 * @param class_no
	 * @param employee_id
	 * @param att_date
	 * @param holiday_flag
	 * @param change_att_flag
	 * @param user_id
	 * @param user_name
	 * @param comp_id
	 * @param commit_flag
	 */
	public void changeClass( Connection conn, int class_no, String employee_id, String att_date,
							 boolean holiday_flag, boolean change_att_flag,
			  				 String user_id, String user_name, String comp_id,
			  				 boolean commit_flag ){
		/*
		try{
			//處理排班表資料
			try{
				//因為是調班, 所以必須先修改原本的員工排班表資料 edit by joe 2012/04/02
				//建立員工排班表元件
				EMS_Work_Schedule_Table ems_work_schedule = new EMS_Work_Schedule_Table(user_name, comp_id);
				//建立修改排班表的相關參數
				Calendar att_cal = this.covertStringToCalendar(att_date);
				//修改調班日期的員工排班表資料
				ems_work_schedule.editEmpSchedule(conn, employee_id, 
												  Integer.parseInt(this.covertDateToString(att_cal.getTime(), "yyyy")), 
												  Integer.parseInt(this.covertDateToString(att_cal.getTime(), "MM")), 
												  att_date, holiday_flag, class_no, "調班!!");
			}catch(Exception e){
				System.out.println("執行調班功能!!, 更改員工排班表資料時,發生錯誤!!");
				e.printStackTrace();
			}
			
			//判斷是否處理考勤轉換
			if(change_att_flag){
				//建立考勤產生元件
				EMS_AttendanceAction att_act_tools = EMS_AttendanceAction.getInstance( comp_id, att_date, user_id );
				//設定考勤產生元件的 commit_flag
				att_act_tools.setCommit_flag(commit_flag);
				//轉換後的考勤資料產生
				att_act_tools.execute_Tmp_Attendance(conn, class_no, employee_id, true);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
	}
	
	/**
	 * 檢查考勤異常資料的打卡時間是否在中午休息的區間內
	 * @param conn
	 * @param type
	 * @param uid
	 * @param comp_id
	 * @return
	 */
	protected Map chkForRestConflict( Connection conn, EMS_AttendanceAction ems_att_tools, String type, String uid, String comp_id ){
		
		Map chk_map = new HashMap();
		boolean chk_flag = false;
		String sql = "";
		
		try{
			if(!"02".equals(type)){
				//需檢查不是 '未打卡資料' 才可執行中午休息的處理
				sql = "" +
				" SELECT " +
				" EHF020401T0_04 AS CLASS_NO, " +
				" EHF020401T0_02_EMP AS EMPLOYEE_ID, " +
				" EHF000400T0_07 AS NOON_REST_START, SUBSTRING(EHF000400T0_07, 1, 2) AS NOON_REST_START_HOUR, " +
				" SUBSTRING(EHF000400T0_07, 3, 2) AS NOON_REST_START_MINUTE, " +
				" EHF000400T0_08 AS NOON_REST_END, SUBSTRING(EHF000400T0_08, 1, 2) AS NOON_REST_END_HOUR, " +
				" SUBSTRING(EHF000400T0_08, 3, 2) AS NOON_REST_END_MINUTE, " +
				" EHF020401T0_08 AS ATT_TYPE, EHF020401T0_05 AS ATT_DATE, " +
				" DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d') AS ATT_DATETIME_DATE, " +
				" DATE_FORMAT(EHF020401T0_07, '%H:%i:%s') AS ATT_DATETIME_TIME " +
				" FROM EHF020401T0 " +
				" LEFT JOIN EHF000400T0 ON EHF000400T0_01 = EHF020401T0_04 AND EHF000400T0_11 = EHF020401T0_11 " +
				" WHERE 1=1 " +
				" AND EHF020401T0_01 = '"+uid+"' " +  //出勤系統號
				" AND EHF020401T0_11 = '"+comp_id+"' ";  //公司代碼
				//" AND ? BETWEEN EHF010100T0_07 AND EHF010100T0_08 ";  //檢查打卡時間是否在中午休息時間之內
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				HR_TOOLS hr_tools  = new HR_TOOLS();
				
				if(rs.next()){
					
					//設定考勤日期資料
					ems_att_tools.setSta_cur_day(rs.getString("ATT_DATE"));
					
					//取得班別資料
					Map classMap = hr_tools.getEmpClassByNo(conn, comp_id, rs.getInt("CLASS_NO"));
					
					Calendar class_siesta_in_cal = ems_att_tools.getCalendarByClass(conn, rs.getString("ATT_DATE"), 
																 rs.getString("EMPLOYEE_ID"), comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
					Calendar class_siesta_out_cal = ems_att_tools.getCalendarByClass(conn, rs.getString("ATT_DATE"),
																  rs.getString("EMPLOYEE_ID"), comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
					
					//轉換考勤打卡日期時間為 Calendar 格式
					Calendar cur_cal = ems_att_tools.datetimeToCalendar(rs.getString("ATT_DATETIME_DATE"), rs.getString("ATT_DATETIME_TIME"));
					
					if( cur_cal.compareTo(class_siesta_out_cal) <= 0 && cur_cal.compareTo(class_siesta_in_cal) >= 0 ){
						
						chk_flag = true;
						//設定MAP值
						chk_map.put("CLASS_NO", rs.getInt("CLASS_NO"));
						chk_map.put("ATT_TYPE", rs.getString("ATT_TYPE"));
						chk_map.put("NOON_REST_START_HOUR", rs.getString("NOON_REST_START_HOUR"));
						chk_map.put("NOON_REST_START_MINUTE", rs.getString("NOON_REST_START_MINUTE"));
						chk_map.put("NOON_REST_END_HOUR", rs.getString("NOON_REST_END_HOUR"));
						chk_map.put("NOON_REST_END_MINUTE", rs.getString("NOON_REST_END_MINUTE"));
						
					}
					
				}
				
				rs.close();
				stmt.close();
				hr_tools.close();
			}
			
			//設定MAP值
			chk_map.put("CHK_FLAG", chk_flag);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_map;
	}
	
	/**
	 * 發送考勤異常訊息 by 考勤日期
	 * @param conn
	 * @param att_date
	 * @param user_id
	 * @param comp_id
	 */
	/*public void sendAbnormalMessage( Connection conn, String att_date, String user_id, String comp_id, String test_id ){
		
		EMS_MailSystem mail_system = null;
		//EMS_MailMessage ems_mailmessage_tools = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			ArrayList att_list = new ArrayList();
			Iterator att_it = null;
			EHF020401M0F bean = null;
			boolean abnormal_flag = false;
			
			//建立 Mail System 元件
			mail_system = new EMS_MailSystem(comp_id);
			//建立MailMessage元件
			//ems_mailmessage_tools = new EMS_MailMessage(comp_id);
			
			//取得公司名稱
			Map compMap = (Map)this.getCompNameMap(user_id).get(comp_id);
			
			//取得所有員工資料
			Map work_emp = this.getWorkEmpNameMap(user_id, comp_id);
			//取得員工工號 Use work_emp -> KeySet
			Set employee_id_set = work_emp.keySet();
			String employee_id = "";  //員工工號
			String emp_email = "";
			
			//處理測試資料
			if(!"".equals(test_id) && test_id != null){
				String[] test_key_set = (test_id.split(","));
				if(test_key_set != null && test_key_set.length > 0 ){
					//處理測試員工工號清單
					Map temp_employee_id = new HashMap();
					//建立測試員工工號清單
					for(int i=0; i< test_key_set.length; i++){
						temp_employee_id.put((String)test_key_set[i], (String)test_key_set[i]);
					}
					employee_id_set = temp_employee_id.keySet();
				}
			}
			
			//產生MailMessage相關資訊
			Iterator it = employee_id_set.iterator();
			while(it.hasNext()){
				//取得員工工號
				employee_id = (String) it.next();
				abnormal_flag = false;
				
				//判斷員工是否需要計算考勤, 若不計算則不需要發送考勤異常Mail
				if(!this.chkEmpRecordAttData(conn, employee_id, comp_id)){
					//結束此次迴圈
					continue;
				}
				
				//取得考勤資料清單
				att_list = this.getAttDatasByAttDate(conn, employee_id, att_date, "", user_id, comp_id);
				
				//檢查考勤資料是否有異常
				att_it = att_list.iterator();
				while(att_it.hasNext()){
					bean = (EHF020401M0F)att_it.next();
					
					if(tools.StringToBoolean(bean.getEHF020401T0_09())){
						//表示考勤資料有異常
						abnormal_flag = true;
						break;
					}
				}
				
				//判斷是否有異常考勤資料, 若有則發送考勤資料異常訊息
				if(abnormal_flag){
					
					
					//建立 MailMessage
					ems_mailmessage_tools.createMailMessage("ATT_MSG_0090", EMS_MailMessage.Message, false, 0, null, "");
					//取得員工E-Mail, 並設定收件人資訊
					ems_mailmessage_tools.addAddressee(EMS_MailMessage.SENDTO, 
													   (String)this.getEmpDefaultContactData(user_id, employee_id, comp_id).get("EMAIL"));
					//設定 Mail Body 資訊
					ems_mailmessage_tools.addMailBody((String)compMap.get("COMP_NAME_C")+" "+att_date+" 考勤異常通知!!",
													  EMS_MailMessage.HTML, 
												      this.buildAbnormalMessageMailBody(att_list));
					//換下一筆MailMessage
					ems_mailmessage_tools.next();
					
					
					//取得員工E-mail
					emp_email = (String)this.getEmpDefaultContactData(user_id, employee_id, comp_id).get("EMAIL");
					//判斷E-mail是否存在
					if(!"".equals(emp_email) && emp_email != null){
						//使用 Mail System 直接執行發送
						mail_system.createMail("EMS_Service@spontech.com.tw", 
											   emp_email,
											   null, null, 
											   (String)compMap.get("COMP_NAME_C")+" "+att_date+" 考勤異常通知!!",
											   "text/html",
											   this.buildAbnormalMessageMailBody(att_list),
											   null);
					}
				}
			}
			
			//SEND E-mail
			mail_system.send();  //寄出E-mail
			
			//關閉 Mail System 元件
			mail_system.close();
			//關閉 MailMessage 元件
			//ems_mailmessage_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				
				if (ems_mailmessage_tools != null && !ems_mailmessage_tools.isClosed()) {
					ems_mailmessage_tools.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}*/
	
	/**
	 * 建立考勤異常訊息的MailMessage - MailBody
	 * @param att_list
	 * @return
	 */
	/*public String buildAbnormalMessageMailBody( ArrayList att_list ){
		
		StringBuffer body = new StringBuffer();
		
		try{
			//考勤資料清單
			Iterator it = att_list.iterator();
			EHF020401M0F bean = null;
			
			//建立MailBody
			//Start HTML
			body.append("<html>");
			body.append("<body>");
			body.append("親愛的使用者您好:<br>您的考勤異常資料如下表所示:<br>");
			body.append("<table border=\"2\" frame=\"box\" >");
			body.append("<caption>考勤異常資料</caption>");  //Table標題
			
			//建立考勤異常清單標頭
			body.append("<tr><td>考勤日期</td><td>員工姓名</td><td>班別</td><td>打卡狀態</td><td>打卡時間</td><td>異常說明</td></tr>");
			
			//建立考勤異常清單資料
			while(it.hasNext()){
				//取得考勤資料
				bean = (EHF020401M0F)it.next();
				body.append("<tr>");  //Start
				body.append("<td>");
				body.append(bean.getEHF020401T0_05());  //考勤日期
				body.append("</td>");
				body.append("<td>");
				body.append(bean.getEHF020401T0_02_EMP());  //員工姓名
				body.append("</td>");
				body.append("<td>");
				body.append(bean.getEHF020401T0_04());  //班別
				body.append("</td>");
				body.append("<td>");
				body.append(bean.getEHF020401T0_08());  //打卡狀態
				body.append("</td>");
				body.append("<td>");
				if("01".equals(bean.getEHF020401T0_TYPE())){
					//考勤資料
					body.append(bean.getEHF020401T0_05_DATE());  //打卡日期
					body.append(" ");
					body.append(bean.getEHF020401T0_06());  //打卡時間
				}else{
					//未打卡資料
					body.append("");
				}
				body.append("</td>");
				body.append("<td>");
				body.append(bean.getEHF020401T0_09_DESC());  //異常說明
				body.append("</td>");
				body.append("</tr>");  //End
			}
			
			//End HTML
			body.append("</table>");
			body.append("<br>若有任何問題, 請連絡相關處理人員!!<br>感謝!!<br><br>此信件由EMS系統自動發送!!請勿回復!!<br>");
			body.append("</body>");
			body.append("</html>");			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return body.toString();
	}*/
	
	/**
	 * 檢查員工是否需要記錄考勤資料
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public boolean chkEmpRecordAttData(Connection conn, String employee_id, String comp_id){
		
		boolean chk_flag = true;
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF020403T0_05 " +  //是否計算考勤
			" FROM EHF030200T0 " +
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01=EHF030200T0_01 AND EHF020403T0_04=EHF030200T0_13" +
			" WHERE 1=1 " +
			" AND EHF030200T0_08 = '1' " +  //薪資主檔啟用
			" AND EHF030200T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF030200T0_13 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				if(rs.getBoolean("EHF030200T0_17")){
					//計算考勤
					chk_flag = true;
				}else{
					//不計算考勤
					chk_flag = false;
				}
			}else{
				//找不到資料=不計算考勤
				chk_flag = false;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
}
