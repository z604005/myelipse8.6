package com.spon.ems.salary.tools;

import org.apache.log4j.Logger;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.hr.forms.EHF010101M0F;
import com.spon.ems.salary.forms.EHF030107M0F;
import com.spon.ems.salary.forms.EHF030601M0F;
import com.spon.ems.salary.forms.EHF030601M2F;
import com.spon.ems.salary.forms.EHF030602M0F;
import com.spon.ems.salary.forms.EHF030602M2F;
import com.spon.ems.salary.forms.EHF030602M3F;
import com.spon.ems.salary.forms.EHF030603M0F;
import com.spon.ems.salary.forms.EHF030603M2F;
import com.spon.ems.salary.forms.EHF030603M6F;
import com.spon.ems.util.overtime.EMS_OvertimeAttendanceHandle;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_Work_ScheduleForm;
import com.spon.utils.work_schedule.EMS_Work_Schedule_TOOLS;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;


/**
 * EMS 津貼,加班,不休假加班計算共用元件
 * 
 * @version 1.0
 * @created 18-十月-2010 下午 06:00:47
 */
public class EMS_OATools {

	static Logger loger = Logger.getLogger(EMS_OATools.class.getName());
	
	private static EMS_OATools ems_oa_tools;
	private static BA_TOOLS tools = BA_TOOLS.getInstance();
	//private static BA_TOOLS ems_tools = BA_TOOLS.getInstance();
	
	private static EMS_AttendanceAction ems_att_tools = null;
	
	private static String sta_salyymm = "";
	private static String sta_costyymm = "";
	private static String sta_user_id = "";
	private static String sta_user_name = "";
	private static String sta_comp_id = "";
	
	private static String sta_date_type = "";
	private static String sta_start_date = "";
	private static String sta_end_date = "";
	
	private Map sta_compClassMap = null;
	private Map cur_classMap = null;
	private Map cur_emp_schedule = null;
	private String cur_dept_id = "";
	
	private float sta_min_pay_hour = 0;
	
	
	public static EMS_OATools getInstance() {
        /*if (ems_oa_tools == null)
        	ems_oa_tools = new EMS_OATools();
        */
        if(ems_oa_tools == null) {
            synchronized(EMS_OATools.class) {
            	ems_oa_tools = new EMS_OATools();
            }
        }
        return ems_oa_tools;
    }
    
	
	
	public static EMS_OATools getInstance(String salYYMM, String costYYMM, String user_id, String user_name, String comp_id) {
        /*if (ems_oa_tools == null){
        	ems_oa_tools = new EMS_OATools(salYYMM, costYYMM, user_id, user_name, comp_id);
        }else{
        	sta_salyymm = salYYMM;
        	sta_costyymm = costYYMM;
        	sta_user_id = user_id;
        	sta_user_name = user_name;
        	sta_comp_id = comp_id;
        }
        */
        if(ems_oa_tools == null) {
            synchronized(EMS_OATools.class) {
            	ems_oa_tools = new EMS_OATools(salYYMM, costYYMM, user_id, user_name, comp_id);
            }
        }else{
        	sta_salyymm = salYYMM;
        	sta_costyymm = costYYMM;
        	sta_user_id = user_id;
        	sta_user_name = user_name;
        	sta_comp_id = comp_id;
        }
        
        return ems_oa_tools;
    }
    
	
	
	private EMS_OATools(){

	}
	
	
	private EMS_OATools(String salYYMM, String costYYMM, String user_id, String user_name, String comp_id){
		try{
			EMS_OATools.sta_salyymm = salYYMM;
			EMS_OATools.sta_costyymm = costYYMM;
			EMS_OATools.sta_user_id = user_id;
			EMS_OATools.sta_user_name = user_name;
			EMS_OATools.sta_comp_id = comp_id;
			
			//建立考勤產生元件
			ems_att_tools = EMS_AttendanceAction.getInstance(comp_id, "", user_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getSta_date_type() {
		return sta_date_type;
	}

	public static void setSta_date_type(String staDateType) {
		sta_date_type = staDateType;
	}

	public void finalize() throws Throwable {

	}
	
	public static String getSta_start_date() {
		return sta_start_date;
	}

	public static void setSta_start_date(String staStartDate) {
		sta_start_date = staStartDate;
	}

	public static String getSta_end_date() {
		return sta_end_date;
	}

	public static void setSta_end_date(String staEndDate) {
		sta_end_date = staEndDate;
	}

	/**
	 * 新增津貼資料 - EHF030601T0
	 * @param conn
	 * @param ehf030601m0
	 * @param user_name
	 * @param comp_id
	 * @return
	 */
	public String addAllowanceData( Connection conn, EHF030601M0F ehf030601m0, String user_name, String comp_id){
		
		String EHF030601T0_U = "";
		
		try{
			
			//新增津貼資料
			String sql = "" +
			" INSERT INTO EHF030601t0 ( EHF030601T0_U, EHF030601T0_01, EHF030601T0_02, EHF030601T0_M, " +
			" EHF030601T0_M1, EHF030601T0_DS, EHF030601T0_03, EHF030601T0_SCP, EHF030601T0_07, EHF030601T0_SCU, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			//使用 CodeRules 做單號取得的動作
			//EHF030601T0_U = EMS_GetCodeRules.getInstance().getCodeRule("EHF030601", comp_id);
			EHF030601T0_U = tools.createEMSUID(conn, "ESA", "EHF030601T0", "EHF030601T0_U", comp_id);
			ehf030601m0.setEHF030601T0_U(EHF030601T0_U);
			
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_U());  //津貼明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_01());  //員工工號
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_02());  //部門代號
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_M());  //入扣帳年月
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_M1());  //薪資年月
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_DS()==null?"":ehf030601m0.getEHF030601T0_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_03()==null?"":ehf030601m0.getEHF030601T0_03());  //備註
			indexid++;
			p6stmt.setString(indexid, "01");  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, "");  //薪資計算UID
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增津貼資料執行 EMS_OATools.addAllowanceData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return EHF030601T0_U;
	}
	
	/**
	 * 新增津貼細項資料 - EHF030601T1
	 * @param conn
	 * @param ehf030601m0
	 * @param user_name
	 * @param comp_id
	 */
	public void addAllowanceDetailData( Connection conn, EHF030601M0F ehf030601m0, String user_name, String comp_id){
		
		try{
			
			//新增津貼細項資料
			String sql = "" +
			" INSERT INTO EHF030601t1 ( EHF030601T1_U, EHF030601T1_01, EHF030601T1_02, EHF030601T1_03, " +
			" EHF030601T1_TAX, " +
			" EHF030601T1_04, EHF030601T1_05, EHF030601T1_DS, EHF030601T1_06, EHF030601T1_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_U());  //津貼明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_01());  //給津貼日期
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_02());  //津貼資料序號
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_03());  //津貼名稱
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_TAX());  //所得稅別
			indexid++;
			p6stmt.setInt(indexid, ehf030601m0.getEHF030601T1_04());  //津貼金額
			indexid++;
			p6stmt.setInt(indexid, ehf030601m0.getEHF030601T1_05());  //加成金額
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_DS()==null?"":ehf030601m0.getEHF030601T1_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_06()==null?"":ehf030601m0.getEHF030601T1_06());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增津貼細項資料執行 EMS_OATools.addAllowanceDetailData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 新增異常津貼細項資料
	 * @param conn
	 * @param ehf030601m2
	 * @param user_name
	 * @param comp_id
	 */
	public void addAbnormalAllowanceData( Connection conn, EHF030601M2F ehf030601m2, String user_name, String comp_id){
		
		try{
			
			//新增異常津貼細項資料
			String sql = "" +
			" INSERT INTO EHF030601t2 ( EHF030601T2_U, EHF030601T2_01, EHF030601T2_02, EHF030601T2_03, " +
			" EHF030601T2_04, " +
			" EHF030601T2_DS, EHF030601T2_05, EHF030601T2_06, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030601m2.getEHF030601T2_U());  //津貼明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030601m2.getEHF030601T2_01());  //給津貼日期
			indexid++;
			p6stmt.setString(indexid, ehf030601m2.getEHF030601T2_02());  //津貼資料序號
			indexid++;
			p6stmt.setString(indexid, ehf030601m2.getEHF030601T2_03());  //津貼名稱
			indexid++;
			p6stmt.setString(indexid, ehf030601m2.getEHF030601T2_04());  //異常津貼說明
			indexid++;
			p6stmt.setString(indexid, ehf030601m2.getEHF030601T2_DS()==null?"":ehf030601m2.getEHF030601T2_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030601m2.getEHF030601T2_05()==null?"":ehf030601m2.getEHF030601T2_05());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增異常津貼細項資料執行 EMS_OATools.addAllowanceDetailData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 查詢員工津貼資料
	 * @param conn
	 * @param employee_id
	 * @return
	 *//*
	public String queryAllowanceKeyByPerson( Connection conn, String employee_id ){
		
		String EHF030601T0_U = "";
		
		try{
			
			//津貼明細資料查詢
			String sql = "" +
			" SELECT EHF030601T0_U " +
			" FROM EHF030601T0 " +
			" WHERE 1=1 " +
			" AND EHF030601T0_M = '"+this.sta_costyymm+"' " +  //入扣帳年月
			" AND EHF030601T0_M1 = '"+this.sta_salyymm+"' " +  //薪資年月
			" AND EHF030601T0_07 = '"+this.sta_comp_id+"' " ;  //公司代碼
			
			if(!"".equals(employee_id) && employee_id != null){
				sql += " AND EHF030601T0_01 = '"+employee_id+"' ";  //員工工號
			}

			sql += " ORDER BY EHF030601T0_U DESC ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				EHF030601T0_U = rs.getString("EHF030601T0_U");  //津貼明細UID
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF030601T0_U;
	}
	
	*//**
	 * 查詢 Collection List
	 * @param conn
	 * @param EHF030601T1_U
	 * @param comp_id
	 * @return
	 *//*
	public Map queryListAllowanceDatas(Connection conn, String EHF030601T1_U, String comp_id){
		
		ArrayList list = new ArrayList();
		Map dataMap = new HashMap();
		int money_sum = 0;
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//津貼明細細項資料查詢
			String sql = "" +
			" SELECT EHF030601T1.*, " +
			" (EHF030601T1_04 + EHF030601T1_05) AS SUM, " +
			" CASE EHF030601T1_DS WHEN '01' THEN 'EMS系統' WHEN '02' THEN '人工' END AS DATASOURCE " +
			" FROM EHF030601T1 " +
			" WHERE 1=1 " +
			" AND EHF030601T1_U = '"+EHF030601T1_U+"' " +  //津貼明細UID
			" AND EHF030601T1_07 = '"+comp_id+"' " ;  //公司代碼

			sql += " ORDER BY EHF030601T1_01 ";

			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				EHF030601M0F bean = new EHF030601M0F();
				bean.setEHF030601T1_U(rs.getString("EHF030601T1_U"));  //津貼明細UID
				bean.setEHF030601T1_01(rs.getString("EHF030601T1_01"));  //給津貼日期
				bean.setEHF030601T1_02(rs.getString("EHF030601T1_02"));  //津貼資料序號
				bean.setEHF030601T1_03(rs.getString("EHF030601T1_03"));  //津貼名稱
				bean.setEHF030601T1_04(rs.getInt("EHF030601T1_04"));  //津貼金額
				bean.setEHF030601T1_05(rs.getInt("EHF030601T1_05"));  //加成金額
				bean.setEHF030601T1_DS(rs.getString("DATASOURCE"));  //資料來源
				bean.setEHF030601T1_06(rs.getString("EHF030601T1_06"));  //備註
				bean.setEHF030601T1_07 (rs.getString("SUM"));  //金額小計
				
				money_sum += rs.getInt("SUM");  //津貼總金額
				
				list.add(bean);
			}
			
			rs.close();
			stmt.close();
			
			dataMap.put("list", list);
			dataMap.put("MONEY_SUM", money_sum);
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return dataMap;
	}
	
	*//**
	 * 刪除津貼資料
	 * @param conn
	 * @param employee_id
	 */
	public void delAllowanceData( Connection conn, String[] employee_id ){
		
		try{
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = "";
			
			//刪除津貼資料, 若 employee_id == "", 則刪除該入扣帳年月,薪資年月所有津貼資料
			sql = " SELECT EHF030601T0_U " +
				  " FROM EHF030601T0 " +
				  " WHERE 1=1 " +
				  " AND EHF030601T0_M = '"+EMS_OATools.sta_costyymm+"' " + //入扣帳年月
				  " AND EHF030601T0_M1 = '"+EMS_OATools.sta_salyymm+"' " +  //薪資年月
				  " AND EHF030601T0_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼	
			
			sql += tools.getTestIdSQL("EHF030601T0_01", employee_id);
			
//			if(!"".equals(employee_id) && employee_id != null ){
//				sql += " AND EHF030601T0_01 = '"+employee_id+"'  ";  //員工工號
//			}
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//刪除 EHF030601T2 異常津貼資料
				/*sql = "" +
				" DELETE FROM EHF030601T2 " +
				" WHERE 1=1 " +
				" AND EHF030601T2_U = '"+rs.getString("EHF030601T0_U")+"' " +  //津貼明細UID
				" AND EHF030601T2_06 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);*/
				
				//刪除 EHF030601T1 津貼細項資料
				sql = "" +
				" DELETE FROM EHF030601T1 " +
				" WHERE 1=1 " +
				" AND EHF030601T1_U = '"+rs.getString("EHF030601T0_U")+"' " +  //津貼明細UID
				" AND EHF030601T1_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030601T0 津貼資料
				sql = "" +
				" DELETE FROM EHF030601T0 " +
				" WHERE 1=1 " +
				" AND EHF030601T0_U = '"+rs.getString("EHF030601T0_U")+"' " +  //津貼明細UID
				" AND EHF030601T0_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				conn.commit();
			}
				
			rs.close();
			stmt.close();
			stmt_del.close();
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 新增加班費資料 - EHF030602T0
	 * @param conn
	 * @param EHF030602m0
	 * @param user_name
	 * @param comp_id
	 * @return
	 */
	public String addOvertimeData( Connection conn, EHF030602M0F EHF030602m0, String user_name, String comp_id){
		
		String EHF030602T0_U = "";
		
		try{
			
			//新增加班費資料
			String sql = "" +
			" INSERT INTO EHF030602t0 ( EHF030602T0_U, EHF030602T0_01, EHF030602T0_02, EHF030602T0_M, " +
			" EHF030602T0_M1, EHF030602T0_DS, EHF030602T0_03, EHF030602T0_SCP, EHF030602T0_07, EHF030602T0_SCU, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			//使用 CodeRules 做單號取得的動作
			//EHF030602T0_U = EMS_GetCodeRules.getInstance().getCodeRule("EHF030602", comp_id);
			EHF030602T0_U = tools.createEMSUID(conn, "ESV", "EHF030602T0", "EHF030602T0_U", comp_id);
			EHF030602m0.setEHF030602T0_U(EHF030602T0_U);
			
			p6stmt.setString(indexid, EHF030602m0.getEHF030602T0_U());  //加班費明細UID
			indexid++;
			p6stmt.setString(indexid, EHF030602m0.getEHF030602T0_01());  //員工工號
			indexid++;
			p6stmt.setString(indexid, EHF030602m0.getEHF030602T0_02());  //部門代號
			indexid++;
			p6stmt.setString(indexid, EHF030602m0.getEHF030602T0_M());  //入扣帳年月
			indexid++;
			p6stmt.setString(indexid, EHF030602m0.getEHF030602T0_M1());  //薪資年月
			indexid++;
			p6stmt.setString(indexid, EHF030602m0.getEHF030602T0_DS()==null?"":EHF030602m0.getEHF030602T0_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, EHF030602m0.getEHF030602T0_03()==null?"":EHF030602m0.getEHF030602T0_03());  //備註
			indexid++;
			p6stmt.setString(indexid, "01");  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, "");  //薪資計算UID
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增加班費資料執行 EMS_OATools.addOvertimeData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return EHF030602T0_U;
	}
	
	/**
	 * 新增加班費細項資料 - EHF030602T1
	 * @param conn
	 * @param EHF030602m0
	 * @param user_name
	 * @param comp_id
	 */
	public void addOvertimeDetailData( Connection conn, EHF030602M0F ehf030602m0, String user_name, String comp_id){
		try{
			
			//新增加班費細項資料
			String sql = "" +
			" INSERT INTO EHF030602t1 ( EHF030602T1_U, EHF030602T1_DATE, EHF030602T1_TYPE, EHF030602T1_01, EHF030602T1_02, EHF030602T1_03, " +
			" EHF030602T1_04, EHF030602T1_05, EHF030602T1_DS, EHF030602T1_06, EHF030602T1_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030602m0.getEHF030602T0_U());  //加班費明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030602m0.getEHF030602T1_DATE());  //加班日期
			indexid++;
			p6stmt.setString(indexid, ehf030602m0.getEHF030602T1_TYPE());  //加班類型
			indexid++;
			p6stmt.setString(indexid, ehf030602m0.getEHF030602T1_01());  //加班日期時間(起)
			indexid++;
			p6stmt.setString(indexid, ehf030602m0.getEHF030602T1_02());  //加班日期時間(迄)
			indexid++;
			p6stmt.setFloat(indexid, ehf030602m0.getEHF030602T1_03());  //加班時數
			indexid++;
			p6stmt.setInt(indexid, ehf030602m0.getEHF030602T1_04());  //加班費金額
			indexid++;
			p6stmt.setString(indexid, ehf030602m0.getEHF030602T1_05()==null?"":ehf030602m0.getEHF030602T1_05());  //加班費計算明細
			indexid++;
			p6stmt.setString(indexid, ehf030602m0.getEHF030602T1_DS()==null?"":ehf030602m0.getEHF030602T1_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030602m0.getEHF030602T1_06()==null?"":ehf030602m0.getEHF030602T1_06());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增加班費細項資料執行 EMS_OATools.addOvertimeDetailData()時，發生錯誤!!"+ e.toString());	
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 新增異常加班費細項資料 - EHF030602T2
	 * @param conn
	 * @param ehf030602m2
	 * @param user_name
	 * @param comp_id
	 */
	public void addAbnormalOvertimeData( Connection conn, EHF030602M2F ehf030602m2, String user_name, String comp_id){
		
		try{
			
			//新增異常加班費細項資料
			String sql = "" +
			" INSERT INTO EHF030602t2 ( EHF030602T2_U, EHF030602T2_DATE, EHF030602T2_TYPE, EHF030602T2_01, EHF030602T2_02, " +
			" EHF030602T2_DS, EHF030602T2_03, EHF030602T2_04, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030602m2.getEHF030602T2_U());  //加班費明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030602m2.getEHF030602T2_DATE());  //加班日期
			indexid++;
			p6stmt.setString(indexid, ehf030602m2.getEHF030602T2_TYPE());  //加班類型
			indexid++;
			p6stmt.setFloat(indexid, ehf030602m2.getEHF030602T2_01());  //異常加班扣除時數
			indexid++;
			p6stmt.setString(indexid, ehf030602m2.getEHF030602T2_02());  //異常加班費說明
			indexid++;
			p6stmt.setString(indexid, ehf030602m2.getEHF030602T2_DS()==null?"":ehf030602m2.getEHF030602T2_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030602m2.getEHF030602T2_03()==null?"":ehf030602m2.getEHF030602T2_03());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增異常加班費細項資料執行 EMS_OATools.addAbnormalOvertimeData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 新增加班費計算明細資料 - EHF030602T3
	 * @param conn
	 * @param ehf030602m3
	 * @param user_name
	 * @param comp_id
	 */
	public void addOvertimeCountDetailData( Connection conn, EHF030602M3F ehf030602m3, String user_name, String comp_id){
		
		try{
			
			//新增加班費計算明細資料
			String sql = "" +
			" INSERT INTO EHF030602t3 ( EHF030602T3_U, EHF030602T3_01, EHF030602T3_02, " +
			" EHF030602T3_03, EHF030602T3_04, EHF030602T3_05, " +
			" EHF030602T3_DS, EHF030602T3_06, EHF030602T3_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_U());  //加班費明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_01());  //加班日期
			indexid++;
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_02());  //加班類型
			indexid++;
			p6stmt.setInt(indexid, ehf030602m3.getEHF030602T3_03());  //時薪
			indexid++;
			p6stmt.setFloat(indexid, ehf030602m3.getEHF030602T3_04());  //時薪倍數
			indexid++;
			p6stmt.setFloat(indexid, ehf030602m3.getEHF030602T3_05());  //加班時數
			indexid++;
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_DS()==null?"":ehf030602m3.getEHF030602T3_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_06()==null?"":ehf030602m3.getEHF030602T3_06());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增加班費計算明細資料執行 EMS_OATools.addOvertimeCountDetailData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 刪除加班費資料
	 * @param conn
	 * @param employee_id
	 */
	public void delOvertimeData( Connection conn, String[] employee_id ){
		
		try{
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = "";
			
			//刪除加班費資料, 若 employee_id == "", 則刪除該入扣帳年月,薪資年月所有加班費資料
			sql = " SELECT EHF030602T0_U " +
				  " FROM EHF030602T0 " +
				  " WHERE 1=1 " +
				  " AND EHF030602T0_M = '"+EMS_OATools.sta_costyymm+"' " + //入扣帳年月
				  " AND EHF030602T0_M1 = '"+EMS_OATools.sta_salyymm+"' " +  //薪資年月
				  " AND EHF030602T0_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼	
			
			sql += tools.getTestIdSQL("EHF030602T0_01", employee_id);
			
//			if(!"".equals(employee_id) && employee_id != null ){
//				sql += " AND EHF030602T0_01 = '"+employee_id+"'  ";  //員工工號
//			}
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//刪除 EHF030602T3 加班費計算明細資料
				sql = "" +
				" DELETE FROM EHF030602T3 " +
				" WHERE 1=1 " +
				" AND EHF030602T3_U = '"+rs.getString("EHF030602T0_U")+"' " +  //加班費明細UID
				" AND EHF030602T3_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030602T2 異常加班費資料
				sql = "" +
				" DELETE FROM EHF030602T2 " +
				" WHERE 1=1 " +
				" AND EHF030602T2_U = '"+rs.getString("EHF030602T0_U")+"' " +  //加班費明細UID
				" AND EHF030602T2_04 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030602T1 加班費細項資料
				sql = "" +
				" DELETE FROM EHF030602T1 " +
				" WHERE 1=1 " +
				" AND EHF030602T1_U = '"+rs.getString("EHF030602T0_U")+"' " +  //加班費明細UID
				" AND EHF030602T1_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030602T0 加班費資料
				sql = "" +
				" DELETE FROM EHF030602T0 " +
				" WHERE 1=1 " +
				" AND EHF030602T0_U = '"+rs.getString("EHF030602T0_U")+"' " +  //加班費明細UID
				" AND EHF030602T0_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				conn.commit();
			}
				
			rs.close();
			stmt.close();
			stmt_del.close();
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 新增不休假加班費資料 - EHF030603T0
	 * @param conn
	 * @param EHF030603m0
	 * @param user_name
	 * @param comp_id
	 * @return
	 */
	public String addHaOvertimeData( Connection conn, EHF030603M0F EHF030603m0, String user_name, String comp_id){
		
		String EHF030603T0_U = "";
		
		try{
			
			//新增不休假加班費資料
			String sql = "" +
			" INSERT INTO EHF030603t0 ( EHF030603T0_U, EHF030603T0_01, EHF030603T0_02, EHF030603T0_M, " +
			" EHF030603T0_M1, EHF030603T0_DS, EHF030603T0_03, EHF030603T0_SCP, EHF030603T0_07, EHF030603T0_SCU, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			//使用 CodeRules 做單號取得的動作
			//EHF030603T0_U = EMS_GetCodeRules.getInstance().getCodeRule("EHF030603", comp_id);
			EHF030603T0_U = tools.createEMSUID(conn, "ESH", "EHF030603T0", "EHF030603T0_U", comp_id);
			EHF030603m0.setEHF030603T0_U(EHF030603T0_U);
			
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T0_U());  //不休假加班費明細UID
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T0_01());  //員工工號
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T0_02());  //部門代號
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T0_M());  //入扣帳年月
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T0_M1());  //薪資年月
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T0_DS()==null?"":EHF030603m0.getEHF030603T0_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T0_03()==null?"":EHF030603m0.getEHF030603T0_03());  //備註
			indexid++;
			p6stmt.setString(indexid, "01");  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, "");  //薪資計算UID
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增不休假加班費資料執行 EMS_OATools.addOvertimeData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return EHF030603T0_U;
	}
	
	/**
	 * 新增不休假加班費細項資料 - EHF030603T1
	 * @param conn
	 * @param EHF030603m0
	 * @param user_name
	 * @param comp_id
	 */
	public void addHaOvertimeDetailData( Connection conn, EHF030603M0F EHF030603m0, String user_name, String comp_id){
		
		try{
			
			//新增不休假加班費細項資料
			String sql = "" +
			" INSERT INTO EHF030603t1 ( EHF030603T1_U, EHF030603T1_DATE, EHF030603T1_01, EHF030603T1_02, EHF030603T1_03, " +
			" EHF030603T1_04, EHF030603T1_05, EHF030603T1_DS, EHF030603T1_06, EHF030603T1_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T0_U());  //不休假加班費明細UID
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T1_DATE());  //不休假加班日期
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T1_01());  //加班日期時間(起)
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T1_02());  //加班日期時間(迄)
			indexid++;
			p6stmt.setFloat(indexid, EHF030603m0.getEHF030603T1_03());  //加班時數
			indexid++;
			p6stmt.setInt(indexid, EHF030603m0.getEHF030603T1_04());  //加班費金額
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T1_05()==null?"":EHF030603m0.getEHF030603T1_05());  //不休假加班費計算明細
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T1_DS()==null?"":EHF030603m0.getEHF030603T1_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, EHF030603m0.getEHF030603T1_06()==null?"":EHF030603m0.getEHF030603T1_06());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增不休假加班費細項資料執行 EMS_OATools.addOvertimeDetailData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 新增異常不休假加班資料 - EHF030603T2
	 * @param conn
	 * @param EHF030603m2
	 * @param user_name
	 * @param comp_id
	 */
	public void addAbnormalHaOvertimeData( Connection conn, EHF030603M2F EHF030603m2, String user_name, String comp_id){
		
		try{
			
			//新增不休假加班費細項資料
			String sql = "" +
			" INSERT INTO EHF030603t2 ( EHF030603T2_U, EHF030603T2_DATE, EHF030603T2_01, EHF030603T2_02, " +
			" EHF030603T2_DS, EHF030603T2_03, EHF030603T2_04, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, EHF030603m2.getEHF030603T2_U());  //不休假加班費明細UID
			indexid++;
			p6stmt.setString(indexid, EHF030603m2.getEHF030603T2_DATE());  //不休假加班日期
			indexid++;
			p6stmt.setFloat(indexid, EHF030603m2.getEHF030603T2_01());  //異常不休假加班扣除時數
			indexid++;
			p6stmt.setString(indexid, EHF030603m2.getEHF030603T2_02());  //異常不休假加班說明
			indexid++;
			p6stmt.setString(indexid, EHF030603m2.getEHF030603T2_DS()==null?"":EHF030603m2.getEHF030603T2_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, EHF030603m2.getEHF030603T2_03()==null?"":EHF030603m2.getEHF030603T2_03());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增異常不休假加班費細項資料執行 EMS_OATools.addAbnormalHaOvertimeData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 新增異常不休假津貼資料
	 * @param conn
	 * @param EHF030601m2
	 * @param user_name
	 * @param comp_id
	 *//*
	public void addAbnormalHaAllowanceData( Connection conn, EHF030601M2F EHF030601m2, String user_name, String comp_id){
		
		try{
			
			//新增不休假加班費細項資料
			String sql = "" +
			" INSERT INTO EHF030603t3 ( EHF030603T3_U, EHF030603T3_DATE, EHF030603T3_01, EHF030603T3_02, " +
			" EHF030603T3_03, EHF030603T3_DS, EHF030603T3_04, EHF030603T3_05, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, EHF030601m2.getEHF030601T2_U());  //不休假加班費明細UID
			indexid++;
			p6stmt.setString(indexid, EHF030601m2.getEHF030601T2_01());  //不休假加班日期
			indexid++;
			p6stmt.setString(indexid, EHF030601m2.getEHF030601T2_02());  //津貼資料序號
			indexid++;
			p6stmt.setString(indexid, EHF030601m2.getEHF030601T2_03());  //津貼名稱
			indexid++;
			p6stmt.setString(indexid, EHF030601m2.getEHF030601T2_04());  //異常不休假津貼說明
			indexid++;
			p6stmt.setString(indexid, EHF030601m2.getEHF030601T2_DS()==null?"":EHF030601m2.getEHF030601T2_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, EHF030601m2.getEHF030601T2_05()==null?"":EHF030601m2.getEHF030601T2_05());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增異常不休假津貼資料執行 EMS_OATools.addAbnormalHaAllowanceData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	*//**
	 * 新增異常不休假加班的加班費資料
	 * @param conn
	 * @param EHF030602m2
	 * @param user_name
	 * @param comp_id
	 *//*
	public void addAbnormalHaOvOvertimeData( Connection conn, EHF030602M2F EHF030602m2, String user_name, String comp_id){
		
		try{
			
			//新增不休假加班費細項資料
			String sql = "" +
			" INSERT INTO EHF030603t4 ( EHF030603T4_U, EHF030603T4_DATE, EHF030603T4_OVTYPE, EHF030603T4_01, " +
			" EHF030603T4_02, EHF030603T4_DS, EHF030603T4_03, EHF030603T4_04, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, EHF030602m2.getEHF030602T2_U());  //不休假加班費明細UID
			indexid++;
			p6stmt.setString(indexid, EHF030602m2.getEHF030602T2_DATE());  //不休假加班日期
			indexid++;
			p6stmt.setString(indexid, EHF030602m2.getEHF030602T2_TYPE());  //加班費類型
			indexid++;
			p6stmt.setFloat(indexid, EHF030602m2.getEHF030602T2_01());  //異常不休假的加班扣除時數
			indexid++;
			p6stmt.setString(indexid, EHF030602m2.getEHF030602T2_02());  //異常不休假的加班費說明
			indexid++;
			p6stmt.setString(indexid, EHF030602m2.getEHF030602T2_DS()==null?"":EHF030602m2.getEHF030602T2_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, EHF030602m2.getEHF030602T2_03()==null?"":EHF030602m2.getEHF030602T2_03());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增異常不休假的加班費資料執行 EMS_OATools.addAbnormalHaOvOvertimeData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	*//**
	 * 新增不休假加班津貼細項資料
	 * @param conn
	 * @param ehf030601m0
	 * @param user_name
	 * @param comp_id
	 *//*
	public void addHaAllowanceDetailData( Connection conn, EHF030601M0F ehf030601m0, String user_name, String comp_id){
		
		try{
			
			//新增不休假加班津貼細項資料
			String sql = "" +
			" INSERT INTO EHF030603t5 ( EHF030603T5_U, EHF030603T5_01, EHF030603T5_02, " +
			" EHF030603T5_03, EHF030603T5_TAX, " +
			" EHF030603T5_04, EHF030603T5_05, EHF030603T5_DS, EHF030603T5_06, EHF030603T5_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T0_U());  //不休假加班明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_01());  //給津貼日期
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_02());  //津貼資料序號
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_03());  //津貼名稱
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_TAX());  //所得稅別
			indexid++;
			p6stmt.setInt(indexid, ehf030601m0.getEHF030601T1_04());  //津貼金額
			indexid++;
			p6stmt.setInt(indexid, ehf030601m0.getEHF030601T1_05());  //加成金額
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_DS()==null?"":ehf030601m0.getEHF030601T1_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030601m0.getEHF030601T1_06()==null?"":ehf030601m0.getEHF030601T1_06());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增不休假加班津貼明細資料執行 EMS_OATools.addHaAllowanceDetailData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	*//**
	 * 新增加班費計算明細資料
	 * @param conn
	 * @param ehf030602m3
	 * @param user_name
	 * @param comp_id
	 *//*
	public void addHaOvertimeOVCountDetailData( Connection conn, EHF030602M3F ehf030602m3, String user_name, String comp_id){
		
		try{
			
			//新增加班費計算明細資料
			String sql = "" +
			" INSERT INTO EHF030603t7 ( EHF030603T7_U, EHF030603T7_01, EHF030603T7_02, " +
			" EHF030603T7_03, EHF030603T7_04, EHF030603T7_05, " +
			" EHF030603T7_DS, EHF030603T7_06, EHF030603T7_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_U());  //不休假加班費明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_01());  //加班日期
			indexid++;
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_02());  //加班類型
			indexid++;
			p6stmt.setInt(indexid, ehf030602m3.getEHF030602T3_03());  //時薪
			indexid++;
			p6stmt.setFloat(indexid, ehf030602m3.getEHF030602T3_04());  //時薪倍數
			indexid++;
			p6stmt.setFloat(indexid, ehf030602m3.getEHF030602T3_05());  //加班時數
			indexid++;
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_DS()==null?"":ehf030602m3.getEHF030602T3_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030602m3.getEHF030602T3_06()==null?"":ehf030602m3.getEHF030602T3_06());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增不休假加班費計算明細資料執行 EMS_OATools.addHaOvertimeOVCountDetailData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	*//**
	 * 新增不休假計算明細資料
	 * @param conn
	 * @param ehf030603m6
	 * @param user_name
	 * @param comp_id
	 */
	public void addHaOvertimeCountDetailData( Connection conn, EHF030603M6F ehf030603m6, String user_name, String comp_id){
		
		try{
			
			//新增不休假計算明細資料
			String sql = "" +
			" INSERT INTO EHF030603t6 ( EHF030603T6_U, EHF030603T6_01, EHF030603T6_02, " +
			" EHF030603T6_03, EHF030603T6_04, EHF030603T6_05, " +
			" EHF030603T6_DS, EHF030603T6_06, EHF030603T6_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030603m6.getEHF030603T6_U());  //不休假加班費明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030603m6.getEHF030603T6_01());  //加班日期
			indexid++;
			p6stmt.setString(indexid, ehf030603m6.getEHF030603T6_02());  //給薪類型
			indexid++;
			p6stmt.setInt(indexid, ehf030603m6.getEHF030603T6_03());  //時薪
			indexid++;
			p6stmt.setFloat(indexid, ehf030603m6.getEHF030603T6_04());  //時薪倍數
			indexid++;
			p6stmt.setFloat(indexid, ehf030603m6.getEHF030603T6_05());  //加班時數
			indexid++;
			p6stmt.setString(indexid, ehf030603m6.getEHF030603T6_DS()==null?"":ehf030603m6.getEHF030603T6_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030603m6.getEHF030603T6_06()==null?"":ehf030603m6.getEHF030603T6_06());  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("新增不休假計算明細資料執行 EMS_OATools.addHaOvertimeCountDetailData()時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 刪除不休假加班費
	 * @param conn
	 * @param employee_id
	 */
	public void delHaOvertimeData( Connection conn, String[] employee_id ){
		
		try{
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stmt_del = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = "";
			
			//刪除不休假加班費資料, 若 employee_id == "", 則刪除該入扣帳年月,薪資年月所有不休假加班費資料
			sql = " SELECT EHF030603T0_U " +
				  " FROM EHF030603T0 " +
				  " WHERE 1=1 " +
				  " AND EHF030603T0_M = '"+EMS_OATools.sta_costyymm+"' " + //入扣帳年月
				  " AND EHF030603T0_M1 = '"+EMS_OATools.sta_salyymm+"' " +  //薪資年月
				  " AND EHF030603T0_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼	
			
			sql += tools.getTestIdSQL("EHF030603T0_01", employee_id);
			
//			if(!"".equals(employee_id) && employee_id != null ){
//				sql += " AND EHF030603T0_01 = '"+employee_id+"'  ";  //員工工號
//			}
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//刪除 EHF030603T7 不休假加班計算明細資料
				/*sql = "" +
				" DELETE FROM EHF030603T7 " +
				" WHERE 1=1 " +
				" AND EHF030603T7_U = '"+rs.getString("EHF030603T0_U")+"' " +  //不休假加班費明細UID
				" AND EHF030603T7_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);*/
				
				//刪除 EHF030603T6 不休假計算明細資料
				sql = "" +
				" DELETE FROM EHF030603T6 " +
				" WHERE 1=1 " +
				" AND EHF030603T6_U = '"+rs.getString("EHF030603T0_U")+"' " +  //不休假加班費明細UID
				" AND EHF030603T6_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030603T5 不休假加班津貼明細資料
				/*sql = "" +
				" DELETE FROM EHF030603T5 " +
				" WHERE 1=1 " +
				" AND EHF030603T5_U = '"+rs.getString("EHF030603T0_U")+"' " +  //不休假加班費明細UID
				" AND EHF030603T5_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030603T4 異常不休假的加班資料
				sql = "" +
				" DELETE FROM EHF030603T4 " +
				" WHERE 1=1 " +
				" AND EHF030603T4_U = '"+rs.getString("EHF030603T0_U")+"' " +  //不休假加班費明細UID
				" AND EHF030603T4_04 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030603T3 異常不休假津貼資料
				sql = "" +
				" DELETE FROM EHF030603T3 " +
				" WHERE 1=1 " +
				" AND EHF030603T3_U = '"+rs.getString("EHF030603T0_U")+"' " +  //不休假加班費明細UID
				" AND EHF030603T3_05 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);*/
				
				//刪除 EHF030603T2 異常不休假加班資料
				sql = "" +
				" DELETE FROM EHF030603T2 " +
				" WHERE 1=1 " +
				" AND EHF030603T2_U = '"+rs.getString("EHF030603T0_U")+"' " +  //不休假加班費明細UID
				" AND EHF030603T2_04 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030603T1 不休假加班細項資料
				sql = "" +
				" DELETE FROM EHF030603T1 " +
				" WHERE 1=1 " +
				" AND EHF030603T1_U = '"+rs.getString("EHF030603T0_U")+"' " +  //不休假加班費明細UID
				" AND EHF030603T1_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				//刪除 EHF030601T0 不休假加班資料
				sql = "" +
				" DELETE FROM EHF030603T0 " +
				" WHERE 1=1 " +
				" AND EHF030603T0_U = '"+rs.getString("EHF030603T0_U")+"' " +  //不休假加班費明細UID
				" AND EHF030603T0_07 = '"+EMS_OATools.sta_comp_id+"' ";  //公司代碼
				stmt_del.execute(sql);
				
				conn.commit();
			}
				
			rs.close();
			stmt.close();
			stmt_del.close();
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 建立公司班別詳細資訊
	 * @param conn
	 * @return
	 *//*
	
	protected Map createCompClassMap( Connection conn ){
		
		Map compClassMap = new HashMap();
		Map tempMap = null;
		Map classMap = null;
		List allowancelist = null;
		List overtimelist = null;
		List haovertimelist = null;
		
		try{
			String sql = "" +
			" SELECT EHF010100T0_01 FROM EHF010100T0" +
			" WHERE 1=1 " +
			" AND EHF010100T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//建立tempMap
				tempMap = new HashMap();
				
				//取得班別資訊
				classMap = ems_tools.getEmpClassByNo( conn, this.sta_comp_id, rs.getInt("EHF010100T0_01"));
				
				//取得班別設定的津貼詳細資料
				//allowancelist = ems_tools.getClassAllowanceData(conn, (Integer)classMap.get("WORK_CLASS_NO"), this.sta_comp_id);
				
				//取得班別設定的加班詳細資料
				overtimelist = ems_tools.getClassOvertimeData(conn, (Integer)classMap.get("WORK_CLASS_NO"), this.sta_comp_id);
				
				//取得班別設定的不休假加班詳細資料
				//haovertimelist = ems_tools.getClassHaOvertimeData(conn, (Integer)classMap.get("WORK_CLASS_NO"), this.sta_comp_id);
				
				//建立公司班別詳細資訊
				tempMap.put("CLASS", classMap);
				tempMap.put("ALLOWANCE", allowancelist);
				tempMap.put("OVERTIME", overtimelist);
				tempMap.put("HAOVERTIME", overtimelist);//不休假加班與平日加班所需資料一樣(差別在於有假日加乘)
				//Put into compClassMap
				compClassMap.put( (Integer)classMap.get("WORK_CLASS_NO"), tempMap);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return compClassMap;
	}
	
	
	*//**
	 * 給外部呼叫的OA計算方法
	 * @param conn
	 * @param type
	 */
	public void executeCount(Connection conn, int type, boolean AdminFlag, String[] testID ){
		this.count(conn, type, AdminFlag, testID );
	}
	
	/**
	 * 計算 津貼資料, 加班資料, 不休假加班費資料
	 * @param conn
	 * @param type
	 */
	protected void count(Connection conn, int type, boolean AdminFlag, String[] testID ){
		
		List processList = new ArrayList();
		
		//Map classMap = null;
		boolean chk_Show_flag= false;
		
		Map empInfMap= new HashMap();
		Map tmp_emp_map = new HashMap();
		
		//傳入薪資年月(SalYYMM), 取得薪資年月的第一天, dateformat = 'yyyy/MM/dd'
		String start_date ="";
		//傳入薪資年月(SalYYMM), 取得薪資年月的最後一天, dateformat = 'yyyy/MM/dd'
		String end_date = "";
		
		try{
			
			//if AdminFlag == true, 刪除所有該月津貼資料
			//if AdminFlag == false, 無動作
			if(AdminFlag){
				//處理資料刪除
				switch(type){
				
					//刪除津貼資料
				case 1:
					this.delAllowanceData(conn, testID);
					break;
					
					//刪除加班費資料
				case 2:
					this.delOvertimeData(conn, testID);
					break;
					
					//刪除不休假加班費資料
				case 3:
					this.delHaOvertimeData(conn, testID);
					break;
					
				default:
					
				}
			}
			
			//建立公司班別詳細資料清單
			this.sta_compClassMap = EMS_Work_Schedule_TOOLS.getInstance().createCompClassMap(conn, true, EMS_OATools.sta_comp_id);
			
			//找出員工班別序號
			String sql = "" +
			 			 " SELECT EHF010100T0_01 AS USER_CODE, EHF010100T0_05 AS EMPLOYEE_NAME, EHF010100T8_04 AS WORK_CLASS_NO, EHF010100T6_02 AS DEPT_ID " +
			 			 " FROM EHF010100T0 " +
			 			 " LEFT JOIN EHF010100T1 ON EHF010100T1_01 = EHF010100T0_01 " +
			 			 " LEFT JOIN EHF010100T8 ON EHF010100T8_03 = EHF010100T0_01 " +
			 			 " LEFT JOIN EHF010100T6 ON EHF010100T6_01 = EHF010100T0_01 " +
			 			 " WHERE 1=1 " +
			 			 " AND EHF010100T1_02 IN ('1','3') AND EHF010100T1_04 = '0' "+	//員工在職才執行
			 			 " AND EHF010100T0.HR_CompanySysNo = '"+EMS_OATools.sta_comp_id+"' " ;

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			while(rs.next()){
				
				//取得班別資訊
				this.cur_classMap = hr_tools.getEmpClassByNo( conn, EMS_OATools.sta_comp_id, rs.getInt("WORK_CLASS_NO"));
				this.cur_dept_id = rs.getString("DEPT_ID");
				
				tmp_emp_map = new HashMap();
				
				//取得待計算的人員清單
				tmp_emp_map.put("USER_CODE", rs.getString("USER_CODE"));		//員工系統代碼
				tmp_emp_map.put("EMPLOYEE_ID", rs.getString("USER_CODE"));		//員工系統代碼
				tmp_emp_map.put("EMPLOYEE_NAME", rs.getString("EMPLOYEE_NAME"));//姓名
				tmp_emp_map.put("WORK_CLASS_NO", rs.getString("WORK_CLASS_NO"));//班別序號
				tmp_emp_map.put("DEPT_ID", rs.getString("DEPT_ID"));			//部門系統代碼
				
				processList.add(tmp_emp_map);
				
			}
			
			rs.close();
			stmt.close();
			
			hr_tools.close();
			
			//檢查人員清單中的員工主檔是否啟用
			processList = this.chkProcessList( conn, processList, EMS_OATools.sta_comp_id );
			
			//傳入薪資年月(SalYYMM), 取得薪資年月的第一天, dateformat = 'yyyy/MM/dd'
			start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
			//傳入薪資年月(SalYYMM), 取得薪資年月的最後一天, dateformat = 'yyyy/MM/dd'
			end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
			
			if( processList.size() > 0 ){
				//處理
				switch(type){
				
				//計算津貼資料
				case 1:
					this.countAllowance( conn, processList ,start_date,end_date);
					break;
					
					//計算加班費資料
				case 2:
					this.countOvertime( conn, processList ,start_date,end_date);
					break;
					
					//計算不休假加班費資料
				case 3:
					this.countHaOvertime( conn, processList ,start_date,end_date);
					break;
					
				default:
					
				}
			}
			
			/*
			//建立公司班別詳細資料清單
			//this.sta_compClassMap = EMS_Work_Schedule_TOOLS.getInstance().createCompClassMap(conn, true, this.sta_comp_id);

//******************************************* 1. 先處理部門預設班別人員 **********************************************
			
			//找出公司目前的部門預設班別
			String sql = "" +
						 " SELECT * FROM EHF010100T0" +
						 " WHERE 1=1 " +
						 " AND EHF010100T0_09 = '1'" +  //預設班別
						 " AND EHF010100T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
			
				//取得班別資訊
				this.cur_classMap = tools.getEmpClassByNo( conn, this.sta_comp_id, rs.getInt("EHF010100T0_01"));
				this.cur_dept_id = rs.getString("EHF010100T0_02");
			
				//取得待計算的人員清單
				processList = ems_att_tools.CalAttPList(conn, this.sta_user_id, this.cur_dept_id, this.sta_comp_id, testID);
				
				//檢查人員清單中的員工主檔是否啟用
				processList = this.chkProcessList( conn, processList, this.sta_comp_id );
				
				
				
				
				//傳入薪資年月(SalYYMM), 取得薪資年月的第一天, dateformat = 'yyyy/MM/dd'
				start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				//傳入薪資年月(SalYYMM), 取得薪資年月的最後一天, dateformat = 'yyyy/MM/dd'
				end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				
				this.getEmployee_idList(processList, start_date, end_date, empInfMap);
				if( processList.size() > 0 ){
					//處理
					switch(type){
					
					//計算津貼資料
					case 1:
						this.countAllowance( conn, processList ,start_date,end_date);
						break;
						
						//計算加班費資料
					case 2:
						//this.countOvertime( conn, processList ,start_date,end_date);
						break;
						
						//計算不休假加班費資料
					case 3:
						//this.countHaOvertime( conn, processList ,start_date,end_date);
						break;
						
					default:
						
					}
				}
			}
			
			rs.close();

//******************************************* 1. 先處理部門預設班別人員 -- 處理結束 **************************************		
			
			
			
			
//******************************************* 2. 處理各部門非預設班別人員 ***********************************************
			processList = null;
			
			sql = "" +
			 " SELECT * FROM EHF010100T0" +
			 " WHERE 1=1 " +
			 " AND EHF010100T0_09 = '0'" +
			 " AND EHF010100T0_11 = '"+this.sta_comp_id+"' ";
			
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//清空人員清單內容
				processList = new ArrayList();
				
				//取得班別資訊
				this.cur_classMap = ems_tools.getEmpClassByNo( conn, this.sta_comp_id, rs.getInt("EHF010100T0_01"));
				//classMap = ems_tools.getEmpClassByNo( conn, this.sta_comp_id, rs.getInt("EHF010100T0_01"));
				
				String subsql = "" +
				" SELECT EHF010100T1_02, EHF010100T1_03 " +
				" FROM EHF010100T1 " +
				" WHERE 1=1 " ;
				
				subsql += ems_tools.getTestIdSQL("EHF010100T1_03", testID);  //測試人員
				
				subsql += "" +
				" AND EHF010100T1_04 = "+rs.getInt("EHF010100T0_01")+" " +  //班別序號
				" AND EHF010100T1_06 = '"+this.sta_comp_id+"' ";  //公司代碼
				
				Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs2 = stmt2.executeQuery(subsql);
				
				while(rs2.next()){
					
					Map tempMap = new HashMap();
					tempMap.put("EMPLOYEE_ID", rs2.getString("EHF010100T1_03"));
					processList.add(tempMap);			
				}
				
				rs2.close();
				stmt2.close();
				
				//檢查人員清單中的員工主檔是否啟用
				processList = this.chkProcessList( conn, processList, this.sta_comp_id );
				
				
				empInfMap= new HashMap();
				
 				this.getEmployee_idList(processList, start_date, end_date, empInfMap);
				
				
				
				if( processList.size() > 0 ){
					//處理
					switch(type){
					
					//計算津貼資料
					case 1:
						this.countAllowance( conn, processList ,start_date,end_date);
						break;
						
						//計算加班費資料
					case 2:
						this.countOvertime( conn, processList ,start_date,end_date);
						break;
						
						//計算不休假加班費資料
					case 3:
						this.countHaOvertime( conn, processList ,start_date,end_date);
						break;
						
					default:
						
					}
				}
			}
			
			rs.close();
			stmt.close();

//******************************************* 2. 處理各部門非預設班別人員 -- 處理結束 **************************************
			*/
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("計算津貼,加班費,不休假加班費 EMS_OATools.count() TYPE:"+type+" 時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 判斷是否為假日
	 * @param conn
	 * @param date
	 * @param attMap
	 * @param comp_id
	 * @return
	 */
	public boolean isHoliday(Connection conn, String date, Map attMap, String comp_id){
		
		boolean chk_flag = false;
		
		try{
			if(this.cur_emp_schedule != null && this.cur_emp_schedule.size() > 0 && this.cur_emp_schedule.containsKey(date) ){
				chk_flag = ((EMS_Work_ScheduleForm)this.cur_emp_schedule.get(date)).isHoliday_flag();
			/*}else if(attMap != null && attMap.size() > 0 && ((Integer)attMap.get("班別序號")) != -1 ){
				chk_flag = (Boolean)attMap.get("是否為休假日");
			}else{
				//原始判斷是否為休假日為抓取行事曆，一律使用排班表
				//chk_flag = ems_tools.isHoliday(conn, date, comp_id);
				//若是排班表未產生，改為背端顯示訊息
				System.out.println(date+"排班表未產生，請先產生排班表。  ");
			}*/
			}else{
				//chk_flag = tools.isHoliday(conn, date, comp_id);
				chk_flag = tools.isHoliday(conn, (String)attMap.get("員工工號"), date, "", comp_id);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 選擇計算津貼的日期類別
	 * @param conn
	 * @param classMap
	 * @param processList
	 * @param endDate 
	 * @param startDate 
	 * @return
	 */
	protected Map countAllowance( Connection conn, List processList, String start_date, String end_date ){
		
		Map msgMap = new HashMap();
		
		try{
			
			//指定月份
			if("01".equals(EMS_OATools.sta_date_type)){
				msgMap = this.countAllowanceByEmp(conn, processList, start_date, end_date );
			//指定日期
			}else if("02".equals(EMS_OATools.sta_date_type)){
				msgMap = this.countAllowanceByEmp(conn, processList, EMS_OATools.sta_start_date, EMS_OATools.sta_start_date );
			//指定日期區間
			}else if("03".equals(EMS_OATools.sta_date_type)){	
				msgMap = this.countAllowanceByEmp(conn, processList, EMS_OATools.sta_start_date, EMS_OATools.sta_end_date );
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("選擇津貼日期類別  EMS_OATools.countAllowance() 時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return msgMap;
	}
	
	/**
	 * 計算員工津貼資料
	 * @param conn
	 * @param classMap
	 * @param allowancelist
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	protected Map countAllowanceByEmp(Connection conn, List processList, String start_date, String end_date ){
		
		Map msgMap = new HashMap();
		Map tempMap = null;
		Map empattdata = null;
		EHF030601M0F ehf030601m0 = null;
		Map attTempMap = null;
		int tmp_class_no = -1;
		
		EHF010101M0F ehf010101m0 = null;
		String cur_employee_id = "";
		int day_pay = 0;
		
		
		Map classMap = null;//排班表資料
		Map ChangeClass = new HashMap();//預排換班資料
		

		try{
			Calendar start_cal = tools.covertStringToCalendar(start_date);  //津貼計算開始日期
			Calendar end_cal = tools.covertStringToCalendar(end_date);  //津貼計算結束日期
			Calendar cur_start_cal = null;
			Calendar cur_end_cal = null;
			boolean chk_run_flag = true;
			String cur_date = "";
									
			//人員清單
			Iterator it = processList.iterator();
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				//建立計算日期區間
				cur_start_cal = (Calendar)start_cal.clone();
				cur_end_cal = (Calendar)end_cal.clone();
				
				//記錄員工工號
				cur_employee_id = (String) tempMap.get("EMPLOYEE_ID");																								
				
				//取得員工的考勤資料 Map && 排班班表資料
				empattdata = this.getEmpDayAttDataMap(conn, cur_employee_id,
													  start_date, end_date, false, false, EMS_OATools.sta_comp_id);
				
				//產生津貼表頭資料
				ehf030601m0 = this.generateAllowanceHeadData(  cur_employee_id, 
															   this.cur_dept_id,
															   EMS_OATools.sta_salyymm, EMS_OATools.sta_costyymm, 
															   EMS_OATools.sta_user_name, EMS_OATools.sta_comp_id );
				
				//取得員工的'實際日薪'
				day_pay = this.getEmpDayPay(conn, cur_employee_id, EMS_OATools.sta_comp_id);
				
				//計算迴圈建立
				chk_run_flag = true;
				while(chk_run_flag){
					if(cur_start_cal.equals(cur_end_cal)){
						chk_run_flag = false;
					}
					cur_date = this.CalendarToString(cur_start_cal);  //當前計算津貼的考勤日期
					//顯示計算資訊
					//System.out.print("目前正在計算員工:"+cur_employee_id+" 的 "+cur_date+" 津貼");
					
					
					
					EMS_AttendanceAction.setSta_cur_day(cur_date);
					//attTempMap = (Map) empattdata.get(cur_date);  //取得考勤日期的實際班別資訊
					//work_schedule = (EMS_Work_ScheduleForm) this.cur_emp_schedule.get(cur_date);  //取得員工當天的排班資訊
					
					//確認該員工有無預排換班(取得員工排班表)
					ChangeClass= new HashMap();
					ChangeClass = ems_att_tools.getEmpSchedule(conn, cur_employee_id, cur_date, ChangeClass);
					
					if(ChangeClass.isEmpty()){					
						//取得班別資訊，依員工班別資料(EHF010100T8)
						classMap = this.cur_classMap;
						
					}else{
						//取得班別資訊，依員工排班表(EHF010105T1)
						classMap = (Map)((Map)this.sta_compClassMap.get((Integer) ChangeClass.get("NEW_CLASS_NO"))).get("CLASS");
					}
					
					//判斷是否為假日
					/*if(work_schedule != null && work_schedule.isHoliday_flag() && attTempMap == null ){
						//為假日
						//取得當日班別 classMap
						tmp_class_no = work_schedule.getClass_no();
					}else if(attTempMap != null){
						//取得當日班別 classMap
						tmp_class_no = (Integer)attTempMap.get("班別序號");
					}else{
						tmp_class_no = -1;
					}
					
					if(tmp_class_no != -1){
						classMap = (Map)((Map)this.sta_compClassMap.get(tmp_class_no)).get("CLASS");
					}else{
						//若取不到排班表中的班別設定則使用員工的當前班別設定做計算
						classMap = this.cur_classMap;
						tmp_class_no = (Integer) classMap.get("WORK_CLASS_NO");
					}
					
					//班別需計算的津貼清單
					//Iterator it_allowance = ((List)((Map)this.sta_compClassMap.get(tmp_class_no)).get("ALLOWANCE")).iterator();
					if(cur_employee_id.equals("201")){
						int sdfgs=0;
					}
					*/		
					List allowancelist = this.getPersonalAllowanceData(conn, cur_employee_id, EMS_OATools.sta_comp_id);
					Iterator it_allowance =allowancelist.iterator();
					while(it_allowance.hasNext()){
						
						ehf010101m0 = (EHF010101M0F) it_allowance.next();
						
						if("".equals(ehf010101m0.getClass_no())){
							ehf030601m0 = this.countAllowanceData( conn, classMap,cur_date, cur_date, ehf030601m0, ehf010101m0, empattdata, true, day_pay );
						}else if(String.valueOf(classMap.get("WORK_CLASS_NO")).equals(ehf010101m0.getClass_no())){
							//津貼有設定班別時，要判斷是否與當似班別相同  相同才產生津貼
							//計算津貼資料
							ehf030601m0 = this.countAllowanceData( conn, classMap,cur_date, cur_date, ehf030601m0, ehf010101m0, empattdata, true, day_pay );
						}
						
					}
					
					cur_start_cal.add(Calendar.DAY_OF_MONTH, 1);
				}
				
				//判斷是否有津貼細項資料, 有細項資料才進行資料庫 Insert
				if(ehf030601m0.getEHF030601C().size() > 0){
					//將產生完成的員工津貼資料新增到資料庫中
					this.addAllowance(conn, ehf030601m0);
				}
			}
				
			//員工津貼資料計算完成
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("計算員工:"+cur_employee_id+" 津貼資料  EMS_OATools.countAllowanceByEmp() 時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return msgMap;
	}
	
	/**
	 * 計算津貼資料
	 * @param conn
	 * @param classMap
	 * @param start_date
	 * @param end_date
	 * @param ehf030601m0
	 * @param ehf010101m0
	 * @param empattdata
	 * @return
	 */
	protected EHF030601M0F countAllowanceData( Connection conn, Map classMap, String start_date, String end_date, EHF030601M0F ehf030601m0,
									  EHF010101M0F ehf010101m0, Map empattdata, boolean enable_ha, int day_pay ){
		
		Map msgMap = new HashMap();
		
		try{
			
			//判斷津貼是否有啟用
			if(tools.StringToBoolean(ehf010101m0.getEHF010101T0_05())){
				//有啟用才計算津貼資料
				
				Calendar start_cal = tools.covertStringToCalendar(start_date);  //津貼計算開始日期
				Calendar end_cal = tools.covertStringToCalendar(end_date);  //津貼計算結束日期
				
				boolean chk_run_flag = true;
				
				while(chk_run_flag){
					
					if(start_cal.equals(end_cal)){
						chk_run_flag = false;
					}
					
					boolean allowance_flag = false;  //給津貼flag
					boolean allowance_add_flag = false;  //給津貼加成flag
					
					boolean count_flag = true;  //計算津貼flag
					String cur_date = this.CalendarToString(start_cal);  //當前計算津貼的考勤日期
					Map tempMap = (Map) empattdata.get(cur_date);  //考勤資料Map
					
					//判斷是否取得考勤資料Map
					if( tempMap == null ){
						//若未取得結束此次迴圈
						//加一天
						start_cal.add(Calendar.DAY_OF_MONTH, 1);
						
						continue;
					}
					
					//判斷考勤是否有異常, 若異常不則不給津貼
					//if(考勤異常 == false) or if(考勤異常==true && 已修正 == true) 才進行津貼計算, 否則表示異常不給津貼
					//判斷是否是假日, 若是假日不使用津貼計算, 等到不休假加班計算一起處理
					boolean ha_flag = false;
					if(enable_ha){
						ha_flag = this.isHoliday(conn, cur_date, tempMap, ehf030601m0.getEHF030601T0_07());
					}else{
						ha_flag = false;
					}
					if(ha_flag){	
						//假日先不給津貼, 等到不休假加班計算一併處理
						count_flag = false;
						
						//判斷是否有記錄中午打卡記錄
					}else if(tools.hasNoonRecord(classMap)){
						
						//增加判斷加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄則只需要判斷下班資料與加班下班資料二擇一有資料
						//即可判定是否需要計算津貼費用
						//Use 
						//	!( ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)tempMap.get("員工工號"), ehf030601m0.getEHF030601T0_07()) 
						//  && !"".equals((String)tempMap.get("加班下班刷卡日期時間")) )
						
						if(	( (Boolean)tempMap.get("上班是否異常") 
							  && !((Boolean)tempMap.get("上班是否已修正") && "02".equals((String)tempMap.get("上班修正來源")) ) )
							|| ( (Boolean)tempMap.get("上午下班是否異常")
								 && !((Boolean)tempMap.get("上午下班是否已修正") && "02".equals((String)tempMap.get("上午下班修正來源")) )  )
							|| ( (Boolean)tempMap.get("下午上班是否異常")
							     && !((Boolean)tempMap.get("下午上班是否已修正") && "02".equals((String)tempMap.get("下午上班修正來源")) ) )
						    || ( (Boolean)tempMap.get("下班是否異常") 
						    	 && !((Boolean)tempMap.get("下班是否已修正") && "02".equals((String)tempMap.get("下班修正來源")) ) 
						    	 && !( ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)tempMap.get("員工工號"), 
						    			 							     ehf030601m0.getEHF030601T0_07() ) 
										   && !"".equals((String)tempMap.get("加班下班刷卡日期時間")) ) ) ){
							//有異常不給津貼
							count_flag = false;
						}
					}else{
						
						//增加判斷加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄則只需要判斷下班資料與加班下班資料二擇一有資料
						//即可判定是否需要計算津貼費用
						//Use 
						//	!( ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)tempMap.get("員工工號"), ehf030601m0.getEHF030601T0_07()) 
						//  && !"".equals((String)tempMap.get("加班下班刷卡日期時間")) )
						
						if( ( (Boolean)tempMap.get("上班是否異常") 
							  && !((Boolean)tempMap.get("上班是否已修正") && "02".equals((String)tempMap.get("上班修正來源")) ) )
							|| ( (Boolean)tempMap.get("下班是否異常") 
							  && !((Boolean)tempMap.get("下班是否已修正") && "02".equals((String)tempMap.get("下班修正來源")) )
							  && !( ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)tempMap.get("員工工號"), 
									  								  ehf030601m0.getEHF030601T0_07() ) 
								   && !"".equals((String)tempMap.get("加班下班刷卡日期時間")) ) ) ){
								//有異常不給津貼
								count_flag = false;
						}
					}
					
					//判斷是否執行津貼計算
					if(count_flag){
						//判斷是否有津貼條件
						if(tools.StringToBoolean(ehf010101m0.getEHF010101T0_07())){
							//有津貼條件需執行津貼條件判斷
							if("01".equals(ehf010101m0.getEHF010101T0_08())){
								//依時間條件
								Calendar cal = this.getAllowanceLimitTime(cur_date, ehf010101m0.getEHF010101T0_09(), classMap);
								//判斷是否給津貼金額
								if(this.giveAllowanceMoneyByTime(conn, ehf030601m0, cal, tempMap, classMap, ehf010101m0, "01" )){
									// if true == 給津貼金額
									allowance_flag = true;
								}else{
									allowance_flag = false;
								}
							}else if("02".equals(ehf010101m0.getEHF010101T0_08())){
								//依時數條件
								if(this.geiveAllowanceMoneyByHour( conn, ehf030601m0, Float.parseFloat(ehf010101m0.getEHF010101T0_10()), tempMap, classMap, ehf010101m0, "01" )){
									//if true == 給津貼金額
									allowance_flag = true;
								}else{
									allowance_flag = false;
								}
							}
						}else{
							//沒有津貼條件, 直接給津貼
							allowance_flag = true;
						}
						
						//處理津貼加成的程式
						//判斷是否有津貼加成
						if(tools.StringToBoolean(ehf010101m0.getEHF010101T0_12()) && allowance_flag ){
							//有津貼加成
							//判斷是否有津貼加成條件
							if(tools.StringToBoolean(ehf010101m0.getEHF010101T0_17())){
								//有津貼加成條件執行津貼加成條件判斷
								if("01".equals(ehf010101m0.getEHF010101T0_18())){
									//依時間條件
									Calendar cal = this.getAllowanceLimitTime(cur_date, ehf010101m0.getEHF010101T0_19(), classMap);
									//判斷是否給津貼加成金額
									if(this.giveAllowanceMoneyByTime(conn, ehf030601m0, cal, tempMap, classMap, ehf010101m0, "02" )){
										// if true == 給津貼加成金額
										allowance_add_flag = true;
									}else{
										allowance_add_flag = false;
									}
								}else if("02".equals(ehf010101m0.getEHF010101T0_18())){
									//依時數條件
									if(this.geiveAllowanceMoneyByHour( conn, ehf030601m0, Float.parseFloat(ehf010101m0.getEHF010101T0_20()),
																       tempMap, classMap, ehf010101m0, "02" )){
										//if true == 給津貼加成金額
										allowance_add_flag = true;
									}else{
										allowance_add_flag = false;
									}
								}
							}else{
								//沒有津貼加成條件, 直接給津貼加成
								allowance_add_flag = true;
							}
						}else{
							//沒有津貼加成
							allowance_add_flag = false;
						}
						
						//計算津貼金額與津貼加成金額,並產生津貼細項資料
						//
						ehf030601m0 = this.generateAllowanceData(ehf030601m0, cur_date, ehf010101m0, allowance_flag, allowance_add_flag, day_pay);
						
					}else{
						//寫入異常不給津貼的資訊
						if(ha_flag){
							//System.out.println("計算津貼資料:"+ehf010101m0.getEHF010101T0_01()+"/"+ehf010101m0.getEHF010101T0_02()+" "+
							 //  "員工:"+ehf030601m0.getEHF030601T0_01()+", "+cur_date+" 為假日, " +
							 //  "假日先不給津貼, 由不休假加班計算時一併處理!!" );
						}else{
							//System.out.println("計算津貼資料:"+ehf010101m0.getEHF010101T0_01()+"/"+ehf010101m0.getEHF010101T0_02()+" "+
							//   "員工:"+ehf030601m0.getEHF030601T0_01()+", "+cur_date+" 考勤資料異常, " +
							//   "不給津貼, 請由人資經辦手動進行修正!!" );
							
							if( !"".equals((String)tempMap.get("考勤日期")) && (String)tempMap.get("考勤日期") != null ){
								//產生津貼異常資訊
								ehf030601m0 = this.generateAbnormalAllowanceData( ehf030601m0, cur_date, ehf010101m0, 
											  cur_date+" 考勤資料異常, " +
											  ehf010101m0.getEHF010101T0_02()+" 不發放, " +
											  "" );
							}
						}
					}
										
					start_cal.add(Calendar.DAY_OF_MONTH, 1);
				}

			}else{
				System.out.println("津貼資料:"+ehf010101m0.getEHF010101T0_01()+"/"+ehf010101m0.getEHF010101T0_02()+" 未啟用!!");
				msgMap.put("MAIN_MSG", "津貼資料:"+ehf010101m0.getEHF010101T0_01()+"/"+ehf010101m0.getEHF010101T0_02()+" 未啟用!!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ehf030601m0;
	}
	
	/**
	 * 取得津貼限制條件的限制時間(Calendar)
	 * @param date
	 * @param limit_time
	 * @param classMap
	 * @return
	 */
	private Calendar getAllowanceLimitTime(String date, String limit_time, Map classMap){
		
		Calendar cal = null;
		
		try{
			int work_start_time = tools.TimeToSecs((String)classMap.get("WORK_START_TIME"));  //上班時間 in Secs
			int siesta_start_time = tools.TimeToSecs((String)classMap.get("SIESTA_START_TIME"));  //午休時間(起) in Secs
			int siesta_end_time = tools.TimeToSecs((String)classMap.get("SIESTA_END_TIME"));  //午休時間(迄) in Secs
			int work_end_time = tools.TimeToSecs((String)classMap.get("WORK_END_TIME"));  //下班時間 in Secs
			
			int limit_time_sec = tools.TimeToSecs(limit_time);  //限制時間 in Secs
			
			String limit_datetime = limit_time.substring(0, 2)+":"+limit_time.substring(2, 4)+":00";
			
			//判斷上午下班是否跨午夜十二點
			if(tools.hasCrossMidnight(classMap, 3)){
				cal = this.compareLimitTime(date, limit_datetime, limit_time_sec, work_start_time);
			//判斷下午上班是否跨午夜十二點
			}else if(tools.hasCrossMidnight(classMap, 4)){
				cal = this.compareLimitTime(date, limit_datetime, limit_time_sec, siesta_start_time);
			//判斷下班是否跨午夜十二點
			}else if(tools.hasCrossMidnight(classMap, 2)){
				cal = this.compareLimitTime(date, limit_datetime, limit_time_sec, siesta_end_time);
			}else{
				//判斷是否有加班情況
				if(tools.hasOverTime(classMap)){
					//有加班
					int ovt_start_tome = tools.TimeToSecs((String)classMap.get("OVT_START_TIME"));  //加班上班時間 in Secs
					//判斷加班上班是否跨午夜十二點
					if(tools.hasCrossMidnight(classMap, 5)){
						cal = this.compareLimitTime(date, limit_datetime, limit_time_sec, work_end_time);
					}else if(tools.hasCrossMidnight(classMap, 6)){
						cal = this.compareLimitTime(date, limit_datetime, limit_time_sec, ovt_start_tome);
					}else{
						//津貼限制時間不可超過加班下班時間
						//沒有跨午夜十二點
						cal = ems_att_tools.datetimeToCalendar(date, limit_datetime);
					}
				}else{
					//沒有加班
					//津貼限制時間不可超過下班時間
					//沒有跨午夜十二點
					cal = ems_att_tools.datetimeToCalendar(date, limit_datetime);
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	/**
	 * 比較限制時間是否跨午夜十二點,並轉換成Calendar回傳
	 * @param date
	 * @param limit_datetime
	 * @param limit_time
	 * @param compare_time
	 * @return
	 */
	private Calendar compareLimitTime(String date, String limit_datetime, int limit_time, int compare_time){
		
		Calendar cal = null;
		
		try{
			if(limit_time < compare_time){
				//有跨午夜十二點
				cal = ems_att_tools.datetimeToCalendar(date, limit_datetime);
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}else{
				//沒有跨午夜十二點
				cal = ems_att_tools.datetimeToCalendar(date, limit_datetime);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	/**
	 * 依據時間條件判斷是否給津貼金額, 津貼加成金額
	 * @param cal
	 * @param attMap
	 * @param classMap
	 * @return
	 */
	protected boolean giveAllowanceMoneyByTime( Connection conn,
												EHF030601M0F ehf030601m0, Calendar cal, Map attMap, Map classMap, EHF010101M0F ehf010101m0,
												String use_type ){
		
		
		boolean chk_flag = false;
		
		try{
			
			Calendar class_siesta_in_cal=null;//實際上午下班刷卡日期時間
			Calendar class_siesta_out_cal=null;//實際下午上班刷卡日期時間
			
			//Calendar class_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 1);  //班別設定的上班時間(Calendar)
			//Calendar class_siesta_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
			//										     (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
			//Calendar class_siesta_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
			//										      (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			//Calendar class_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
			//									   (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)		
			//Calendar late_class_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
			//										    (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 10);  //班別設定的延後下班時間
			//Calendar class_ov_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"),
			//										 (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 5);  //班別設定的加班上班時間(Calendar)
			//Calendar class_ov_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
			//										  (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 6);  //班別設定的加班下班時間(Calendar)
			//取得實際加班下班時間
			Calendar real_ov_in_cal = this.datetimeToCalendar((String) attMap.get("加班上班刷卡日期時間"));
			//取得實際加班下班時間
			Calendar real_ov_out_cal = this.datetimeToCalendar((String) attMap.get("加班下班刷卡日期時間"));
			//取得實際下班時間
			Calendar real_out_cal = this.datetimeToCalendar((String) attMap.get("下班刷卡日期時間"));

			//是否紀錄中午打卡
			if(tools.hasNoonRecord(classMap)){
				 class_siesta_in_cal 	= this.datetimeToCalendar((String) attMap.get("上午下班刷卡日期時間"));
				 class_siesta_out_cal 	= this.datetimeToCalendar((String) attMap.get("下午上班刷卡日期時間"));
				
			}
			
			//至少由上午下班開始比較起, 跟上班時間比沒意義, 表示津貼時間設定錯誤
			//判斷是否有記錄中午打卡
			if(tools.hasNoonRecord(classMap)){
				if( class_siesta_in_cal.compareTo(cal) >= 0 ){//實際上午下班刷卡時間   超過    設定時間
					//給津貼
					chk_flag = true;
				}else if( class_siesta_out_cal.compareTo(cal) >= 0 ){//實際下午上班刷卡時間   超過   設定時間
					//給津貼
					chk_flag = true;
				}else if( real_out_cal.compareTo(cal) >= 0 ){//實際下班刷卡時間   超過    設定時間
					//給津貼
					chk_flag = true;
				}
				/*else if( late_class_out_cal != null ){//增加若加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄也需進行處理
					
					//增加判斷加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄則只需要判斷下班資料與加班下班資料二擇一有資料
					//若只有加班下班資料則以加班下班資料作為實際下班時間
					if( real_out_cal != null && real_out_cal.compareTo(late_class_out_cal) >= 0 && late_class_out_cal.compareTo(cal) >= 0 ){
						//給津貼
						chk_flag = true;
					}else{
						//不給津貼
						chk_flag = false;
					}
				}*/
				else if (!tools.StringToBoolean(ehf010101m0.getEHF010101T0_24())) {// 是否勾選 "參考加班單"
					// 沒勾選 "參考加班單"
					if (real_out_cal != null && real_out_cal.compareTo(cal) >= 0) {// 下班刷卡日期時間 超過 設定時間
						// 給津貼
						chk_flag = true;
					} else {
						// 不給津貼
						chk_flag = false;
					}
				}
				else {
					// 不給津貼
					chk_flag = false;
				}
			}else{
				//中午上下班不打卡   直接處理下班
				//是否勾選   "參考加班單"
				if(!tools.StringToBoolean(ehf010101m0.getEHF010101T0_24())){
					//沒勾選 "參考加班單"
					if (real_out_cal != null && real_out_cal.compareTo(cal) >= 0) {//下班刷卡日期時間   超過    設定時間
						// 給津貼
						chk_flag = true;
					} else {
						// 不給津貼
						chk_flag = false;
					}
				}
			}
			
			//判斷是否有設定加班 並且需要參考加班單
			//若是正常班別區間已經判斷給津貼, 則不需要再執行加班的判斷
			if( tools.StringToBoolean(ehf010101m0.getEHF010101T0_24())   &&   real_ov_out_cal != null    &&    !chk_flag ){
				
				//此處需改以實際的加班上班與加班下班資料進行判斷, 否則會出現誤給的情況, 因為加班即使時數不足也不應該算是異常
				//先不修改, 此處的處理要加班設定中的 '是否記錄下班與加班上班'設定為紀錄才會進入此處處理,
				//此情況下若是有異常還是交給人工進行判斷, 以臨時補扣款進行處理
				if(( ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)attMap.get("員工工號"), ehf030601m0.getEHF030601T0_07())
						&& !"".equals((String)attMap.get("加班下班刷卡日期時間")) )){
					 if( real_ov_out_cal.compareTo(cal) >= 0 ){//使用實際加班下班資料做比較
						//給津貼
						chk_flag = true;
					}else{
						//不給津貼
						chk_flag = false;
					}
					return chk_flag;
				}
				
				//判斷加班考勤資料是否異常, 異常則不給津貼
				if( ( (Boolean)attMap.get("加班上班是否異常") && !(Boolean)attMap.get("加班上班是否已修正") )
					|| ( !(Boolean)attMap.get("加班上班是否異常") && "".equals((String)attMap.get("加班上班刷卡日期時間")) )
					|| ( (Boolean)attMap.get("加班下班是否異常") && !(Boolean)attMap.get("加班下班是否已修正") )
					|| ( !(Boolean)attMap.get("加班下班是否異常") && "".equals((String)attMap.get("加班下班刷卡日期時間")) ) ){
					//寫入異常不給津貼的資訊
					if("01".equals(use_type)){
						//System.out.println("計算津貼資料:"+ehf010101m0.getEHF010101T0_01()+"/"+ehf010101m0.getEHF010101T0_02()+" "+ "員工:"+attMap.get("員工工號")+", "+attMap.get("考勤日期")+" 加班考勤資料異常, 不給津貼, 請由人資手動進行修正!!");
						if(!"".equals((String)attMap.get("加班上班考勤類型")) || !"".equals((String)attMap.get("加班下班考勤類型"))){
							//產生津貼異常資訊
							ehf030601m0 = this.generateAbnormalAllowanceData( ehf030601m0, (String)attMap.get("考勤日期"), ehf010101m0, 
										  (String)attMap.get("考勤日期")+" 加班考勤資料異常, " +
										  ehf010101m0.getEHF010101T0_02()+"("+ehf010101m0.getEHF010101T0_06()+"元) 不發放, " +
										  "" );
						}
						
					}else if("02".equals(use_type)){
						//System.out.println("計算津貼資料:"+ehf010101m0.getEHF010101T0_01()+"/"+ehf010101m0.getEHF010101T0_02()+" "+ "員工:"+attMap.get("員工工號")+", "+attMap.get("考勤日期")+" 加班考勤資料異常, 不給津貼加成, 請由人資手動進行修正!!");
						if(!"".equals((String)attMap.get("加班上班考勤類型")) || !"".equals((String)attMap.get("加班下班考勤類型"))){
							//產生津貼異常資訊
							ehf030601m0 = this.generateAbnormalAllowanceData( ehf030601m0, (String)attMap.get("考勤日期"), ehf010101m0, 
										  (String)attMap.get("考勤日期")+" 加班考勤資料異常, " +
										  ehf010101m0.getEHF010101T0_02()+"(津貼加成) 不發放, " +
										  "" );
						}
					}
					//異常, 不給津貼
					chk_flag = false;
					
				}else{
					if(!ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)attMap.get("員工工號"), ehf030601m0.getEHF030601T0_07())) {
						//要記錄下班與加班上班刷卡時間
						if( real_ov_in_cal!=null&&real_ov_in_cal.compareTo(cal) >= 0 ){//實際加班上班時間超過設定時間
							//給津貼
							chk_flag = true;
						}else if( real_ov_out_cal!=null && real_ov_out_cal.compareTo(cal) >= 0 ){//實際加班下班時間超過設定時間
							//給津貼
							chk_flag = true;
						}else{
							//不給津貼
							chk_flag = false;
						}
					}else if( real_ov_out_cal!=null && real_ov_out_cal.compareTo(cal) >= 0 ){//實際加班下班時間超過設定時間
						//給津貼
						chk_flag = true;
					}else{
						//不給津貼
						chk_flag = false;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 依據時數條件判斷是否給津貼金額, 津貼加成金額    (從上班時間開始計算)
	 * @param hour
	 * @param attMap
	 * @param classMap
	 * @return
	 */
	protected boolean geiveAllowanceMoneyByHour( Connection conn,
												 EHF030601M0F ehf030601m0, float hour, Map attMap, Map classMap, EHF010101M0F ehf010101m0,
												 String use_type ){
		
		boolean chk_flag = false;
		//BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			
			Calendar class_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
											      (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
														 (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
														  (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
												   (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
			Calendar late_class_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
														(String)attMap.get("員工工號"), this.sta_comp_id, classMap, 10);  //班別設定的延後下班時間
			//取得實際加班下班時間
			Calendar real_ov_in_cal = this.datetimeToCalendar((String) attMap.get("加班上班刷卡日期時間"));
			//取得實際加班下班時間
			Calendar real_ov_out_cal = this.datetimeToCalendar((String) attMap.get("加班下班刷卡日期時間"));
			//取得實際下班時間
			Calendar real_out_cal = this.datetimeToCalendar((String) attMap.get("下班刷卡日期時間"));
			
			long hourinmillis = (long) (hour * 60 * 60 * 1000);  //轉換為微秒
			
			long beforenooninmillis = class_siesta_in_cal.getTimeInMillis() - class_in_cal.getTimeInMillis();  //上午上班時數 in Millisecond
			long afternoooninmillis = class_out_cal.getTimeInMillis() - class_siesta_out_cal.getTimeInMillis();  //下午上班時數in Millisecond
			long noonmillis = class_siesta_out_cal.getTimeInMillis() - class_siesta_in_cal.getTimeInMillis();  //中午休息時數in Millisecond
			long alldayinmillis = class_out_cal.getTimeInMillis() - class_in_cal.getTimeInMillis();  //整天上班時數in Millisecond
			long lateinmillis = 0;  //延後下班時數in Millisecond
			long overtimeinmillis = 0;  //加班時數in Millisecond
			
			//班別有設定延後下班才執行
			if( late_class_out_cal != null ){
				if( real_out_cal != null && real_out_cal.compareTo(late_class_out_cal) >= 0 ){
					//延後下班成立
					lateinmillis = late_class_out_cal.getTimeInMillis() - class_out_cal.getTimeInMillis();
				}else{
					//延後下班未成立
					lateinmillis = 0;
				}
			}
			
			//增加判斷加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄則需要判斷是否存在加班下班的資料
			//若有加班下班的資料表示有加班, 則需依據實際加班下班時間, 計算出加班時數(需扣除內扣時數)
			//下面的判斷需多增加一個有加班時數的判斷(前提是設定不記錄下班與加班上班)
			
			//至少由上午下班開始比較起, 跟上班時間比沒意義, 表示津貼時間設定錯誤
			//判斷是否有記錄中午打卡
			if(tools.hasNoonRecord(classMap)){
				if( beforenooninmillis >= hourinmillis ){//上班到中午下班這段時間>=設定時間
					//給津貼
					chk_flag = true;
				//else if( ( beforenooninmillis + afternoooninmillis + lateinmillis ) >= hourinmillis ){
				}else if( ( beforenooninmillis + afternoooninmillis + (real_out_cal.getTimeInMillis() - class_out_cal.getTimeInMillis()) ) 
						>= hourinmillis ){//上班到中午下班  +中午下班到下班時間 + (實際下班時間-班別設定下班時間) >=設定時間	
					//給津貼
					chk_flag = true;
				}else{
					//不給津貼
					chk_flag = false;
				}
			}else{
				//沒有中午打卡記錄, 只跟下班考勤比較
				//if( ( alldayinmillis - noonmillis + lateinmillis ) >= hourinmillis ){
				if(!tools.StringToBoolean(ehf010101m0.getEHF010101T0_24())&&//不參考加班紀錄 
						( alldayinmillis - noonmillis + (real_out_cal.getTimeInMillis() - class_out_cal.getTimeInMillis())
						//整天上班時間-中午休息時間 + (實際下班時間-班別設定下班時間) >=設定時間	
								>= hourinmillis )){
					//給津貼
					chk_flag = true;
				}else{
					//不給津貼
					chk_flag = false;
				}
			}
			
			//判斷是否有設定加班
			//若是正常班別區間已經判斷給津貼, 則不需要再執行加班的判斷
			if(tools.StringToBoolean(ehf010101m0.getEHF010101T0_24())&& real_ov_in_cal!=null && !chk_flag ){
				
				//有加班
				Calendar class_ov_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
												 		 (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 5);  //班別設定的加班上班時間(Calendar)
				Calendar class_ov_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
														  (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 6);  //班別設定的加班下班時間(Calendar)
				long overtimeinmilli = (long) (Float.parseFloat(this.getovertimeinmillis(conn,(String) attMap.get("考勤日期"),(String)attMap.get("員工工號")))
						* 60 * 60 * 1000);//依據考勤  工號  搜尋加班單  的內扣時數
				//此處需改以實際的加班上班與加班下班資料進行判斷, 否則會出現誤給的情況, 因為加班即使時數不足也不應該算是異常
				//先不修改, 此處的處理要加班設定中的 '是否記錄下班與加班上班'設定為紀錄才會進入此處處理,
				//此情況下若是有異常還是交給人工進行判斷, 以臨時補扣款進行處理
				if(( ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)attMap.get("員工工號"), ehf030601m0.getEHF030601T0_07()) 
						 && !"".equals((String)attMap.get("加班下班刷卡日期時間")) )){
						
					
					

					//使用實際加班下班資料做比較
						overtimeinmillis = real_ov_out_cal.getTimeInMillis() - class_ov_in_cal.getTimeInMillis();//實際加班時數(沒扣除內扣時數)
						if( ( alldayinmillis - noonmillis + overtimeinmillis -overtimeinmilli) >= hourinmillis ){
							//給津貼
							chk_flag = true;
						}else{
							//不給津貼
							chk_flag = false;
						}
						
						return chk_flag;
				}
				
				//增加處理, 要額外扣除內扣時數的數值
				overtimeinmillis = real_ov_out_cal.getTimeInMillis() - class_ov_in_cal.getTimeInMillis();//實際加班時數(沒扣除內扣時數)
				
				//判斷加班考勤資料是否異常, 異常則不給津貼
				if( ( (Boolean)attMap.get("加班上班是否異常") && !(Boolean)attMap.get("加班上班是否已修正") ) 
					|| ( !(Boolean)attMap.get("加班上班是否異常") && "".equals((String)attMap.get("加班上班刷卡日期時間")) )
					|| ( (Boolean)attMap.get("加班下班是否異常") && !(Boolean)attMap.get("加班下班是否已修正") )
					|| ( !(Boolean)attMap.get("加班下班是否異常") && "".equals((String)attMap.get("加班下班刷卡日期時間")) ) ){
					//寫入異常不給津貼的資訊
					//
					if("01".equals(use_type)){
						//System.out.println("計算津貼資料:"+ehf010101m0.getEHF010101T0_01()+"/"+ehf010101m0.getEHF010101T0_02()+" "+
						//				   "員工:"+attMap.get("員工工號")+", "+attMap.get("考勤日期")+" 加班考勤資料異常, 不給津貼, 請由人資手動進行修正!!" 
						//				   );
						if(!"".equals((String)attMap.get("加班上班考勤類型")) || !"".equals((String)attMap.get("加班下班考勤類型"))){
							//產生津貼異常資訊
							ehf030601m0 = this.generateAbnormalAllowanceData( ehf030601m0, (String)attMap.get("考勤日期"), ehf010101m0, 
										  (String)attMap.get("考勤日期")+" 加班考勤資料異常, " +
										  ehf010101m0.getEHF010101T0_02()+"("+ehf010101m0.getEHF010101T0_06()+"元) 不發放, " +
										  "" );
						}
						
					}else if("02".equals(use_type)){
						//System.out.println("計算津貼資料:"+ehf010101m0.getEHF010101T0_01()+"/"+ehf010101m0.getEHF010101T0_02()+" "+
						//"員工:"+attMap.get("員工工號")+", "+attMap.get("考勤日期")+" 加班考勤資料異常, 不給津貼加成, 請由人資手動進行修正!!" 
						//);
						
						if(!"".equals((String)attMap.get("加班上班考勤類型")) || !"".equals((String)attMap.get("加班下班考勤類型"))){
						//產生津貼異常資訊
							ehf030601m0 = this.generateAbnormalAllowanceData( ehf030601m0, (String)attMap.get("考勤日期"), ehf010101m0, 
										  (String)attMap.get("考勤日期")+" 加班考勤資料異常, " +
										  ehf010101m0.getEHF010101T0_02()+"(津貼加成) 不發放, " +
										  "" );
						}
						
					}
					
					//異常, 不給津貼
					chk_flag = false;
					
				}else{
					if( ( alldayinmillis - noonmillis + overtimeinmillis -overtimeinmilli) >= hourinmillis ){
						//給津貼
						chk_flag = true;
					}else{
						//不給津貼
						chk_flag = false;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	private String getovertimeinmillis(Connection conn, String date, String employee_id) {
		// TODO Auto-generated method stub
		 float i=0;
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = "";

			sql = " SELECT EHF020802T0_07_DE AS 內扣時數 " 
				+ " FROM EHF020802T0 "
				+ " WHERE 1=1 " 
				+ " AND EHF020802T0_02 = '"  + date + "' " // 入扣帳年月
				+ " AND EHF020802T0_04= '" + employee_id + "' " // 薪資年月
				+ " AND EHF020802T0_08 = '" + this.sta_comp_id + "' "; // 公司代碼
			rs = stmt.executeQuery(sql);

			while (rs.next()) {

				i+=rs.getFloat("內扣時數");
				
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(i);
	}

	/**
	 * 新增津貼資料,津貼細項資料,異常津貼細項資料到資料庫
	 * @param conn
	 * @param ehf030601m0
	 * @return
	 */
	public String addAllowance( Connection conn, EHF030601M0F ehf030601m0 ){
		
		String EHF030601T0_U = "";
		
		try{
			if(ehf030601m0 != null){
				//新增津貼表頭資料
				EHF030601T0_U = this.addAllowanceData(conn, ehf030601m0, ehf030601m0.getUSER_CREATE(), ehf030601m0.getEHF030601T0_07());
				
				List list = ehf030601m0.getEHF030601C();
				
				//list.size() > 0 表示有資料, 才新增津貼明細資料
				Iterator it = list.iterator();
				
				while(it.hasNext()){
					
					EHF030601M0F bean = (EHF030601M0F) it.next();
					bean.setEHF030601T0_U(EHF030601T0_U);  //津貼明細UID
					//新增津貼細項資料
					this.addAllowanceDetailData(conn, bean, ehf030601m0.getUSER_CREATE(), bean.getEHF030601T1_07());
				}
				
				//新增異常資料
				/*if(ehf030601m0.getEHF030601C2().size() > 0){
					//有異常資料才新增
					List abnormallist = ehf030601m0.getEHF030601C2();
					
					//list.size() > 0 表示有資料, 才新增異常津貼明細資料
					Iterator abit = abnormallist.iterator();
					
					while(abit.hasNext()){
						
						EHF030601M2F bean2 = (EHF030601M2F) abit.next();
						bean2.setEHF030601T2_U(EHF030601T0_U);  //津貼明細UID
						//新增異常津貼資料
						this.addAbnormalAllowanceData(conn, bean2, ehf030601m0.getUSER_CREATE(), bean2.getEHF030601T2_06());
					}
					
				}*/
				
			}
			
		}catch(Exception e){
			System.out.println("新增津貼資料,津貼細項資料與異常津貼資料到資料庫時,發生錯誤");
			e.printStackTrace();
		}
		
		return EHF030601T0_U;
	}
	
	/**
	 * 判斷與計算津貼金額,津貼加成金額
	 * @param ehf030601m0
	 * @param cur_date
	 * @param ehf010101m0
	 * @param allowance_flag
	 * @param allowance_add_flag
	 * @return
	 */
	protected EHF030601M0F generateAllowanceData( EHF030601M0F ehf030601m0, String cur_date, EHF010101M0F ehf010101m0, 
												  boolean allowance_flag, boolean allowance_add_flag, int day_pay ){
		
		try{
			//判斷是否給津貼資料
			if(allowance_flag){
				//給津貼, 有給津貼才會有津貼加成
				//判斷是否給津貼加成
				int allowance_money = 0;
				//處理津貼金額
				//判斷是否依據一日基本薪給津貼
				if(tools.StringToBoolean(ehf010101m0.getEHF010101T0_06_FLG())){
					//依據一日基本薪給津貼: 津貼金額  = 一日基本薪 x (一日基本薪加成比率)
					allowance_money = Math.round(day_pay * Float.parseFloat(ehf010101m0.getEHF010101T0_06_RATE()));
				}else{
					//依據津貼金額給津貼
					allowance_money = Integer.parseInt(ehf010101m0.getEHF010101T0_06());
				}
				if(allowance_add_flag){
					//有給津貼加成
					int allowance_add_money = 0;
					if("01".equals(ehf010101m0.getEHF010101T0_13())){  //依比例
						//計算津貼加成金額 公式: 津貼金額 x (津貼加成率 - 1) = 津貼加成金額(小數點四捨五入)
						//allowance_add_money = Math.round(Integer.parseInt(ehf010101m0.getEHF010101T0_06()) * (Float.parseFloat(ehf010101m0.getEHF010101T0_14()) - 1));
						allowance_add_money = Math.round(allowance_money * (Float.parseFloat(ehf010101m0.getEHF010101T0_14()) - 1));
					}else if("02".equals(ehf010101m0.getEHF010101T0_13())){  //依固定金額
						//計算津貼加成金額 公式: 津貼加成金額 = 津貼加成金額
						allowance_add_money = Integer.parseInt(ehf010101m0.getEHF010101T0_15());
					}
					//產生津貼細項資料
					ehf030601m0 = this.generateAllowanceDetailData(ehf030601m0, cur_date, Integer.parseInt(ehf010101m0.getEHF010101T0_01()),
					ehf010101m0.getEHF010101T0_02(), ehf010101m0.getEHF010101T0_03(), allowance_money, allowance_add_money );
				}else{
					//沒有給津貼加成
					//產生津貼細項資料
					ehf030601m0 = this.generateAllowanceDetailData(ehf030601m0, cur_date, Integer.parseInt(ehf010101m0.getEHF010101T0_01()),
					ehf010101m0.getEHF010101T0_02(), ehf010101m0.getEHF010101T0_03(), allowance_money, 0);
				}
			}else{
				//沒有給津貼, 就不會有津貼加成
			}
			
		}catch(Exception e){
			System.out.println("產生津貼資料的Form(EHF030601M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030601m0;
	}

	/**
	 * 產生津貼表頭資料Form
	 * @param employee_id
	 * @param dept_id
	 * @param salYYMM
	 * @param costYYMM
	 * @param user_name
	 * @param comp_id
	 * @return
	 */
	protected EHF030601M0F generateAllowanceHeadData( String employee_id, String dept_id,
						String salYYMM, String costYYMM, String user_name, String comp_id ){
		
		EHF030601M0F Form = null;
		List list = new ArrayList();
		List list2 = new ArrayList();
		
		try{
			Form = new EHF030601M0F();
			
			Form.setEHF030601T0_U("");  //津貼明細UID
			Form.setEHF030601T0_01(employee_id);  //員工工號
			Form.setEHF030601T0_02(dept_id);  //部門代號
			Form.setEHF030601T0_M(costYYMM);  //入扣帳年月
			Form.setEHF030601T0_M1(salYYMM);  //薪資年月
			Form.setEHF030601T0_DS("01");  //資料來源 - EMS系統
			Form.setEHF030601T0_03("");  //備註
			Form.setEHF030601T0_SCP("01");  //薪資處理狀態
			Form.setEHF030601T0_07(comp_id);  //公司代碼
			Form.setEHF030601T0_SCU("");  //薪資計算明細UID
			Form.setUSER_CREATE(user_name);  //建立人員
			Form.setUSER_UPDATE(user_name); //修改人員
			
			Form.setEHF030601C(list);  //建立空的LIST
			Form.setEHF030601C2(list2);  //建立空的LIST
			
			
		}catch(Exception e){
			System.out.println("產生津貼資料的Form(EHF030601M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return Form;
	}
	
	/**
	 * 產生津貼細項資料的bean
	 * @param ehf030601m0
	 * @param allowance_date
	 * @param allowance_no
	 * @param allowance_name
	 * @param allowance_tax_type
	 * @param allowance_money
	 * @param allowance_add_money
	 * @return
	 */
	protected EHF030601M0F generateAllowanceDetailData( EHF030601M0F ehf030601m0, String allowance_date, int allowance_no, String allowance_name, 
						   String allowance_tax_type, int allowance_money, int allowance_add_money ){

		EHF030601M0F bean = null;
		List list = null;

		try{
			list = ehf030601m0.getEHF030601C();
			bean = new EHF030601M0F();
			
			bean.setEHF030601T1_U("");  //津貼明細UID
			bean.setEHF030601T1_01(allowance_date);  //給津貼日期
			bean.setEHF030601T1_02(allowance_no+"");  //津貼資料序號
			bean.setEHF030601T1_03(allowance_name);  //津貼名稱
			bean.setEHF030601T1_TAX(allowance_tax_type);  //所得稅類別
			bean.setEHF030601T1_04(allowance_money);  //津貼金額
			bean.setEHF030601T1_05(allowance_add_money);  //津貼加成金額
			bean.setEHF030601T1_DS("01");  //資料來源 - EMS系統
			bean.setEHF030601T1_06("");  //備註
			bean.setEHF030601T1_07(ehf030601m0.getEHF030601T0_07());  //公司代碼
			
			list.add(bean);
			
			ehf030601m0.setEHF030601C(list);
			
		}catch(Exception e){
			System.out.println("產生津貼細項資料的bean(EHF030601M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030601m0;
	}
	
	/**
	 * 產生異常津貼細項資料
	 * @param ehf030601m0
	 * @param allowance_date
	 * @param ehf010101m0
	 * @param abnormal_desc
	 * @return
	 */
	protected EHF030601M0F generateAbnormalAllowanceData( EHF030601M0F ehf030601m0, String allowance_date, EHF010101M0F ehf010101m0,
														  String abnormal_desc ){

		EHF030601M2F bean = null;
		List list = null;

		try{
			list = ehf030601m0.getEHF030601C2();
			bean = new EHF030601M2F();
			
			bean.setEHF030601T2_U("");  //津貼明細UID
			bean.setEHF030601T2_01(allowance_date);  //給津貼日期
			bean.setEHF030601T2_02(ehf010101m0.getEHF010101T0_01());  //津貼資料序號
			bean.setEHF030601T2_03(ehf010101m0.getEHF010101T0_02());  //津貼名稱
			bean.setEHF030601T2_04(abnormal_desc);  //異常津貼說明
			bean.setEHF030601T2_DS("01");  //資料來源 - EMS系統
			bean.setEHF030601T2_05("");  //備註
			bean.setEHF030601T2_06(ehf030601m0.getEHF030601T0_07());  //公司代碼
			
			list.add(bean);
			
			ehf030601m0.setEHF030601C2(list);
			
		}catch(Exception e){
			System.out.println("產生異常津貼細項資料的bean(EHF030601M2F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030601m0;
	}
	
	/**
	 * 選擇計算加班費的日期類別
	 * @param conn
	 * @param classMap
	 * @param processList
	 * @return
	 */
	protected Map countOvertime( Connection conn, List processList , String start_date, String end_date){
		
		Map msgMap = new HashMap();
		
		try{
			
			//指定月份
			if("01".equals(EMS_OATools.sta_date_type)){
				msgMap = this.countOvertimeByEmp(conn, processList, start_date, end_date );
			//指定日期
			}else if("02".equals(EMS_OATools.sta_date_type)){
				msgMap = this.countOvertimeByEmp(conn, processList, EMS_OATools.sta_start_date, EMS_OATools.sta_start_date);
			//指定日期區間
			}else if("03".equals(EMS_OATools.sta_date_type)){
				msgMap = this.countOvertimeByEmp(conn, processList, EMS_OATools.sta_start_date, EMS_OATools.sta_end_date);		
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("選擇加班費日期類別  EMS_OATools.countOvertime() 時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return msgMap;
	}
	
	/**
	 * 計算員工加班費資料
	 * @param conn
	 * @param classMap
	 * @param overtimelist
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	protected Map countOvertimeByEmp(Connection conn, List processList, String start_date, String end_date ){
		
		Map msgMap = new HashMap();
		Map tempMap = null;
		String cur_employee_id = "";
		Map empattdata = null;
		EHF030602M0F ehf030602m0 = null;
		int hour_pay = 0;
		Map attTempMap = null;
		EMS_Work_ScheduleForm work_schedule = null;
		int tmp_class_no = -1;
		Map classMap = null;
		EHF030107M0F ehf030107m0 = null;
		
		try{
			Calendar start_cal = tools.covertStringToCalendar(start_date);  //加班費計算開始日期
			Calendar end_cal = tools.covertStringToCalendar(end_date);  //加班費計算結束日期
			Calendar cur_start_cal = null;
			Calendar cur_end_cal = null;
			boolean chk_run_flag = true;
			String cur_date = "";
			Map ChangeClass = new HashMap();
			
			//建立排班表元件
			//EMS_Work_Schedule_Table ems_work_schedule_tools = new EMS_Work_Schedule_Table(this.sta_user_name, this.sta_comp_id);
			
			//設定最低計薪時數
			this.sta_min_pay_hour = Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "MIN_PAY_HOUR"));
			
			//人員清單
			Iterator it = processList.iterator();
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				//建立計算日期區間
				cur_start_cal = (Calendar)start_cal.clone();
				cur_end_cal = (Calendar)end_cal.clone();
				
				//記錄員工工號
				cur_employee_id = (String) tempMap.get("EMPLOYEE_ID");
				
				//取得員工排班表資料Map In 全域變數
				//this.cur_emp_schedule = ems_work_schedule_tools.getDateRangeInScheduleMap(conn, cur_employee_id, start_date, end_date);
				
				//取得員工的考勤資料 Map && 排班班表資料
				empattdata = this.getEmpDayAttDataMap(conn, cur_employee_id,
													  start_date, end_date, false, false, this.sta_comp_id);
				//產生加班費表頭資料
				ehf030602m0 = this.generateOvertimeHeadData( cur_employee_id, this.cur_dept_id,
										   					 this.sta_salyymm, this.sta_costyymm, this.sta_user_name, this.sta_comp_id);
				
				//取得員工的'實際時薪
				hour_pay = this.getEmpHourPay(conn, ehf030602m0.getEHF030602T0_01(), ehf030602m0.getEHF030602T0_07() );
				
				//計算迴圈建立
				chk_run_flag = true;
				while(chk_run_flag){
					if(cur_start_cal.equals(cur_end_cal)){
						chk_run_flag = false;
					}
					cur_date = this.CalendarToString(cur_start_cal);  //當前計算津貼的考勤日期
					
					//確認該員工有無預排換班(取得員工排班表)
					ChangeClass= new HashMap();
					ChangeClass = ems_att_tools.getEmpSchedule(conn, cur_employee_id, cur_date, ChangeClass);
					
					if(ChangeClass.isEmpty()){					
						//取得班別資訊，依員工班別資料(EHF010100T8)
						classMap = this.cur_classMap;
						
					}else{
						//取得班別資訊，依員工排班表(EHF010105T1)
						classMap = (Map)((Map)this.sta_compClassMap.get((Integer) ChangeClass.get("NEW_CLASS_NO"))).get("CLASS");
					}
					/*
					//顯示計算資訊
					//System.out.println("目前正在計算員工:"+cur_employee_id+" 的 "+cur_date+" 加班費");
					if(cur_employee_id.equals("010")){
						
						int i=0;
					}
					
					attTempMap = (Map) empattdata.get(cur_date);  //取得考勤日期的實際班別資訊			
					work_schedule = (EMS_Work_ScheduleForm) this.cur_emp_schedule.get(cur_date);  //取得員工當天的排班資訊
					
					//判斷是否為假日
					if(work_schedule != null && work_schedule.isHoliday_flag() && attTempMap == null ){
						//為假日
						//取得當日班別 classMap
						tmp_class_no = work_schedule.getClass_no();
					}else if(attTempMap != null){
						//取得當日班別 classMap
						tmp_class_no = (Integer)attTempMap.get("班別序號");
					}else{
						tmp_class_no = -1;
					}
					
					if(tmp_class_no != -1){
						classMap = (Map)((Map)this.sta_compClassMap.get(tmp_class_no)).get("CLASS");
					}else{
						//若取不到排班表中的班別設定則使用員工的當前班別設定做計算
						classMap = this.cur_classMap;
						tmp_class_no = (Integer) classMap.get("WORK_CLASS_NO");
					}
					*/

					//是否需要計算加班費
					if(this.checkCountOvertimeData(conn, cur_date, cur_employee_id, this.sta_comp_id)){

						//需計算的加班清單    20140730 修改原有的找尋加班費規則方法  By Alvin
						Iterator it_overtime = this.getemployeeOvertimeData(conn, cur_employee_id, this.sta_comp_id).iterator();
						
						while(it_overtime.hasNext()){
							
							ehf030107m0 = (EHF030107M0F) it_overtime.next();
							//計算加班費資料
							ehf030602m0 = this.countOvertimeData(conn, classMap, 
																 cur_date, cur_date, ehf030602m0, ehf030107m0, empattdata, hour_pay, true );
						}
						
						cur_start_cal.add(Calendar.DAY_OF_MONTH, 1);
					
					}else{
						cur_start_cal.add(Calendar.DAY_OF_MONTH, 1);
					}
				}
				
				//判斷是否有加班費細項資料, 有細項資料才進行資料庫 Insert
				if(ehf030602m0.getEHF030602C().size() > 0){
					//將產生完成的員工加班費資料新增到資料庫中
					this.addOvertime(conn, ehf030602m0);
				}
			}
				
			//員工加班費資料計算完成
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("計算員工:"+cur_employee_id+" 加班費資料  EMS_OATools.countOvertimeByEmp() 時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return msgMap;
	}
	
	/**
	 * 是否需要計算加班費
	 * @param conn
	 * @param cur_date
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	private boolean checkCountOvertimeData(Connection conn, String cur_date,
			String employee_id, String comp_id) {
		// TODO Auto-generated method stub
		
		boolean flag = true;
		
		try {
			String sql = "" +
			" SELECT " +
			" EHF020802T0_04 AS 考勤日期, " +
			" DATE_FORMAT(EHF020802T0_05, '%Y/%m/%d %H:%i:%s') AS START_TIME, " +
			" DATE_FORMAT(EHF020802T0_06, '%Y/%m/%d %H:%i:%s') AS END_TIME, " +
			" EHF020802T0_07 AS 加班時數, " +
			" EHF020802T0_07_DE AS 內扣時數, " +
			" EHF020802T0_09 AS 處理方法 " +
			" FROM EHF020802T0 " +
			" WHERE 1=1 " +
			" AND EHF020802T0_02  = '"+employee_id+"'" +  //員工工號
			" AND EHF020802T0_04  = '"+cur_date+"' " +  //考勤日期
			" AND EHF020802T0_08  = '"+comp_id+"' " ;  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
		
			if(rs.next()){
				if("01".equals(rs.getString("處理方法"))){	//換錢
					flag = true;
				}else if("02".equals(rs.getString("處理方法"))){	//換休
					flag = false;
				}else{
					flag = true;
				}
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception E){
			E.printStackTrace();
		}
		
		return flag;
	}

	/**
	 * 實際計算與判斷加班費用
	 * @param conn
	 * @param classMap
	 * @param start_date
	 * @param end_date
	 * @param ehf030602m0
	 * @param ehf010102m0
	 * @param empattdata
	 * @return
	 */
	protected EHF030602M0F countOvertimeData( Connection conn, Map classMap, String start_date, String end_date, EHF030602M0F ehf030602m0,
			EHF030107M0F ehf030107m0, Map empattdata, int hour_pay, boolean enable_ha ){
		
		//BA_TOOLS tools = BA_TOOLS.getInstance();
		Map msgMap = new HashMap();
		
		try{
			
			//判斷加班是否有啟用
			if(tools.StringToBoolean(ehf030107m0.getEHF030107T0_09())){
				//有啟用才計算加班費資料
				
				Calendar start_cal = tools.covertStringToCalendar(start_date);  //加班計算開始日期
				Calendar end_cal = tools.covertStringToCalendar(end_date);  //加班計算結束日期
				
				boolean chk_run_flag = true;
				
				while(chk_run_flag){
					
					if(start_cal.equals(end_cal)){
						chk_run_flag = false;
					}
					
					Map before_overtime_map = new HashMap();  //提前加班時數Map
					Map late_overtime_map = new HashMap();  //延後下班時數Map
					Map overtime_map = new HashMap();  //加班時數Map
					
					boolean count_flag = true;  //計算加班費flag
					String cur_date = this.CalendarToString(start_cal);  //當前計算加班費的考勤日期
					Map tempMap = (Map) empattdata.get(cur_date);  //考勤資料Map
					
					//列印考勤資料Map
					//System.out.println("日期:"+cur_date+" 考勤資料Map:"+tempMap);
					
					//判斷是否取得考勤資料Map
					if( tempMap == null ){
						//若未取得結束此次迴圈
						//加一天
						start_cal.add(Calendar.DAY_OF_MONTH, 1);
						
						continue;
					}
					
					//判斷考勤是否有異常, 若異常還是給加班費, 但是依據實際加班時數給加班費
					//if(考勤異常狀態 != 未打卡 ) 才進行加班費計算, 否則表示異常不給加班費
					//判斷是否是假日, 若是假日不使用加班費計算, 等到不休假加班計算一起處理
					boolean ha_flag = false;
					if(enable_ha){
						ha_flag = this.isHoliday(conn, cur_date, tempMap, ehf030602m0.getEHF030602T0_07());  //假日flag
					}else{
						ha_flag = false;
					}							
					
					if(ha_flag){	
						//假日先不給加班費, 等到不休假加班計算一併處理
						count_flag = false;
						
					}else{
						
						//之後的加班上班與加班下班紀錄不在會有異常的狀況, 只需要判斷加班上班與加班下班不是未打卡資料即可進行加班費處理
						if( ( (Boolean)tempMap.get("加班上班是否異常") && !(Boolean)tempMap.get("加班上班是否已修正") )
							|| ( (Boolean)tempMap.get("加班下班是否異常") && !(Boolean)tempMap.get("加班下班是否已修正") ) ){
							//判斷異常是否為 '未打卡'
							//增加判斷加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄則只需要判斷加班下班的資料即可判定是否需要計算加班費	
							if( ( "".equals((String)tempMap.get("加班上班刷卡日期時間")) 
									  && !ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)tempMap.get("員工工號"),ehf030602m0.getEHF030602T0_07() ) ) 
									|| "".equals((String)tempMap.get("加班下班刷卡日期時間")) ){
									//異常狀態 == '未打卡', 則不給加班費
									count_flag = false;
								}
						}
					}
					
					try{
						//處理提前加班的程式
						//判斷是否有提前加班且當天不得為假日

						if( !ha_flag){
							//計算提前加班時數
							before_overtime_map = this.countBeforeOvertimeHour(conn, ehf030602m0, ehf030107m0, tempMap, classMap);
						}else{
							//沒有提前加班, 設定提前加班時數 = 0
							before_overtime_map.put("HOUR", (float)0);
						}
					
					}catch(Exception e){
						System.out.println("處理員工:"+ehf030602m0.getEHF030602T0_01()+"提前加班的加班費時，發生錯誤!!");
						e.printStackTrace();
					}
					//已經無此設定
					/*try{
						//處理延後下班的程式
						//判斷是否有延後下班且當天不得為假日
						if(ems_tools.hasLateOverTime(classMap) && !ha_flag){
//							//計算延後下班時數
							late_overtime_map = this.countLateOvertimeHour(conn, ehf030602m0, ehf010102m0, tempMap, classMap);
							
						}else{
							//沒有延後下班, 設定延後下班時數 = 0
							late_overtime_map.put("HOUR", (float)0);
//							late_overtime_hour = 0;
						}
					
					}catch(Exception e){
						System.out.println("處理員工:"+ehf030602m0.getEHF030602T0_01()+"延後下班的加班費時，發生錯誤!!");
						e.printStackTrace();
					}*/
					
					
					//判斷是否執行加班費計算
					if(count_flag){
						
						//處理加班的程式
						//判斷是否有加班
						if(tools.StringToBoolean(ehf030107m0.getEHF030107T0_09())){
							//有設定加班
							
							//計算加班時數
							overtime_map = this.countOvertimeHour( conn, ehf030602m0, ehf030107m0, tempMap, classMap, false );
							
						}else{
							//沒有加班, 設定加班時數 = 0
							overtime_map.put("HOUR", (float)0);
//							overtime_hour = 0;
						}
						
					}else{
						//沒有加班, 設定加班時數 = 0
						overtime_map.put("HOUR", (float)0);
//						overtime_hour = 0;
						
						//寫入異常不給加班費的資訊
						if(ha_flag){
							/*System.out.println("計算加班費資料:"+ehf030107m0.getEHF030107T0_02()+"/"+ehf030107m0.getEHF030107T0_03()+" "+
							   "員工:"+ehf030602m0.getEHF030602T0_01()+", "+cur_date+" 為假日, " +
							   "假日先不給加班費, 由不休假加班計算時一併處理!!" );*/
						}else{
							/*System.out.println("計算加班費資料:"+ehf030107m0.getEHF030107T0_02()+"/"+ehf030107m0.getEHF030107T0_03()+" "+
							   "員工:"+ehf030602m0.getEHF030602T0_01()+", "+cur_date+" 加班考勤資料異常, " +
							   "不給加班費, 請由人資手動進行修正!!" );*/
							
							if(!"".equals((String)tempMap.get("加班上班考勤類型")) || !"".equals((String)tempMap.get("加班下班考勤類型"))){
								//產生加班費異常資訊
								ehf030602m0 = this.generateAbnormalOvertimeData( ehf030602m0, cur_date, (float)0, "02",
											  cur_date+" 加班考勤資料異常, " +
											  ehf030107m0.getEHF030107T0_03()+" "+
											  "不發放加班費, " +
											  "" );
							}
							
						}
					}
					
					//計算加班費金額,並產生加班費細項資料
					//提前加班時數, 延後下班時數, 加班時數以及各加班設定的加班費加成率區間計算加班費用
					ehf030602m0 = this.generateOvertimeData(conn, ehf030602m0, cur_date,
							ehf030107m0, before_overtime_map, late_overtime_map, overtime_map, hour_pay,
															false);
					
					//加一天
					start_cal.add(Calendar.DAY_OF_MONTH, 1);
				}

			}else{
				//System.out.println("加班資料:"+ehf010102m0.getEHF010102T0_01()+"/"+ehf010102m0.getEHF010102T0_02()+" 未啟用!!");
				//msgMap.put("MAIN_MSG", "加班資料:"+ehf010102m0.getEHF010102T0_01()+"/"+ehf010102m0.getEHF010102T0_02()+" 未啟用!!");
			}
			
		}catch(Exception e){
			System.out.println("處理員工:"+ehf030602m0.getEHF030602T0_01()+"加班費時，發生錯誤!!");
			e.printStackTrace();
		}
		
		return ehf030602m0;
	}
	
	/**
	 * 取得提前加班的時數
	 * @param cal
	 * @param attMap
	 * @param classMap
	 * @return
	 */
	protected Map countBeforeOvertimeHour(Connection conn, EHF030602M0F ehf030602m0, EHF030107M0F ehf030107m0, Map attMap, Map classMap ){
		
		Map before_overtime_Map = new HashMap();
		float hour = 0;
		
		try{
			
			Calendar class_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
												  (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 1);  //班別設定的上班時間(Calendar)
//			Calendar class_siesta_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 7);  //班別設定的上午下班時間(Calendar)
//			Calendar class_siesta_out_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 8);  //班別設定的下午上班時間(Calendar)
//			Calendar class_out_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 2);  //班別設定的下班時間(Calendar)		
//			Calendar class_ov_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 5);  //班別設定的加班上班時間(Calendar)
//			Calendar class_ov_out_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 6);  //班別設定的加班下班時間(Calendar)
			
			//Calendar class_before_ov_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
			//												(String)attMap.get("員工工號"), this.sta_comp_id, classMap, 9);  //班別設定的提前加班開始時間(Calendar)
			
			Calendar cur_cal = this.datetimeToCalendar((String) attMap.get("上班刷卡日期時間"));  //實際上班時間(Calendar)
			
			
			Map GetBeforeOvertimeHour=this.getOvertimeHour(conn,class_in_cal, (String) attMap.get("考勤日期"), (String)attMap.get("員工工號"),this.sta_comp_id,"<=");
			

			//判斷考勤資料是否異常
			if(GetBeforeOvertimeHour.size()==0 || cur_cal == null ){
				hour = 0;
				before_overtime_Map.put("HOUR", hour);
				before_overtime_Map.put("START_TIME", "");
				before_overtime_Map.put("END_TIME", "");
				
				return before_overtime_Map;
			}
			//總加班時數(含內扣)
			hour=Float.valueOf((String)GetBeforeOvertimeHour.get("hour"));
			
			//取得班別的提前加班內扣時數
			float deducted_hour = Float.parseFloat((String)GetBeforeOvertimeHour.get("deducted_hour"));
			/*
			Calendar range_class_before_ov_in_cal = (Calendar)class_before_ov_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_before_ov_in_cal.add(Calendar.SECOND, 59); 
			
			//提前加班設定的區間總時數
			//float total_bov_hour = this.caculateDiffHourWithFIX(class_before_ov_in_cal, class_in_cal);
			
			//判斷提前加班的情況
			if( cur_cal.compareTo(range_class_before_ov_in_cal) <=0 ){
				//提前加班 - 正常
				//計算 提前加班時間  至 班別上班時間 中間的時數
				hour = this.caculateDiffHourWithFIX(class_before_ov_in_cal, class_in_cal);
				
			}else if( cur_cal.compareTo(class_in_cal) < 0 && cur_cal.compareTo(range_class_before_ov_in_cal) > 0 ){
				//提前加班 - 異常
				//計算 實際上班時間  至 班別上班時間 中間的時數
				hour = this.caculateDiffHourWithFIX(cur_cal, class_in_cal);
			}		
			*/
			
			//提前加班時數 = 時數 - 內扣時數
			hour = hour - deducted_hour;
			
			//時數不可為負的, 若為負的, 時數 = 0
			if(hour < 0){
				hour = 0;
			}
			
			//處理異常資訊的寫入
			float ab_hour = (hour - deducted_hour);
			if( ab_hour > 0 && hour > 0 ){
				//有異常, 若 hour = 0 表示沒有提前加班, 不需記錄異常
				//進行異常資訊處理
				//產生加班費異常資訊
				ehf030602m0 = this.generateAbnormalOvertimeData( ehf030602m0, (String) attMap.get("考勤日期"), ab_hour, "01",
							  (String) attMap.get("考勤日期")+" 提前加班考勤資料異常:上班("+(String) attMap.get("上班刷卡日期時間")+"), " +
							  ehf030107m0.getEHF030107T0_03()+" "+
						      "扣除異常時數:"+ab_hour+"小時, 發放"+hour+"小時提前加班費, " +
							  "" );
				
			}
			
			before_overtime_Map.put("HOUR", hour);
			before_overtime_Map.put("START_TIME", ems_att_tools.CalendarToString(cur_cal));
			before_overtime_Map.put("END_TIME", ems_att_tools.CalendarToString(class_in_cal));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return before_overtime_Map;
	}

	/**
	 * 取得延後下班的時數
	 * @param attMap
	 * @param classMap
	 * @return
	 *//*
	protected Map countLateOvertimeHour(Connection conn, EHF030602M0F ehf030602m0, EHF010102M0F ehf010102m0, Map attMap, Map classMap ){
		
		Map late_overtime_Map = new HashMap();
		float hour = 0;
		
		try{
			
//			Calendar class_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 1);  //班別設定的上班時間(Calendar)
//			Calendar class_siesta_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 7);  //班別設定的上午下班時間(Calendar)
//			Calendar class_siesta_out_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
												   (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)		
//			Calendar class_ov_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 5);  //班別設定的加班上班時間(Calendar)
//			Calendar class_ov_out_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 6);  //班別設定的加班下班時間(Calendar)
			
			//取得延後下班時數
			float late_hour = Float.parseFloat((String)classMap.get("LATETIME_HOUR"));
			int late_sec = (int) (late_hour * 60 * 60);
			if( late_sec < 0){
				late_sec = 0;
			}
			Calendar class_late_out_cal = (Calendar) class_out_cal.clone();   
			class_late_out_cal.add(Calendar.SECOND, late_sec);  //班別設定的延後下班結束時間(Calendar)
			
			Calendar cur_cal = this.datetimeToCalendar((String) attMap.get("下班刷卡日期時間"));  //實際下班時間(Calendar)
			
			//判斷考勤資料是否異常
			if( cur_cal == null ){
				hour = 0;
				late_overtime_Map.put("HOUR", hour);
				late_overtime_Map.put("START_TIME", "");
				late_overtime_Map.put("END_TIME", "");
				
				return late_overtime_Map;
			}
			
			//延後下班設定的區間總時數
			float total_aov_hour = this.caculateDiffHourWithFIX(class_out_cal, class_late_out_cal);
			
			//判斷延後下班的情況
			if( cur_cal.compareTo(class_late_out_cal) >= 0 ){
				//延後下班 - 正常
				//計算 班別下班時間  至 延後下班時間 中間的時數
				hour = this.caculateDiffHourWithFIX(class_out_cal, class_late_out_cal);
				
			}else if( cur_cal.compareTo(class_out_cal) > 0 && cur_cal.compareTo(class_late_out_cal) < 0 ){
				//延後下班 - 異常
				//計算 班別下班時間  至 實際下班時間 中間的時數
				hour = this.caculateDiffHourWithFIX(class_out_cal, cur_cal);
			}
			
			//時數不可為負的, 若為負的, 時數 = 0
			if(hour < 0){
				hour = 0;
			}
			
			//處理異常資訊的寫入
			float ab_hour = total_aov_hour - hour;
			if( ab_hour > 0 && hour > 0 ){
				//有異常, 若 hour = 0 表示沒有延後下班, 不需記錄異常
				//進行異常資訊處理
				//產生加班費異常資訊
				ehf030602m0 = this.generateAbnormalOvertimeData( ehf030602m0, (String) attMap.get("考勤日期"), ab_hour, "03",
							  (String) attMap.get("考勤日期")+" 延後下班考勤資料異常:下班("+(String) attMap.get("下班刷卡日期時間")+"), " +
							  ehf010102m0.getEHF010102T0_02()+" "+
						      "扣除異常時數:"+ab_hour+"小時, 發放"+hour+"小時延後下班費用, " +
							  "" );
				
			}
			
			late_overtime_Map.put("HOUR", hour);
			late_overtime_Map.put("START_TIME", ems_att_tools.CalendarToString(class_out_cal));
			late_overtime_Map.put("END_TIME", ems_att_tools.CalendarToString(cur_cal));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return late_overtime_Map;
	}
	
	*//**
	 * 取得加班的限制時間(Calendar)
	 * @param date
	 * @param limit_time
	 * @param classMap
	 * @return
	 *//*
	private Calendar getOvertimeLimitTime(String date, String limit_time, Map classMap){
		
		Calendar cal = null;
		
		try{
//			int work_start_time = this.TimeToSecs((String)classMap.get("WORK_START_TIME"));  //上班時間 in Secs
//			int siesta_start_time = this.TimeToSecs((String)classMap.get("SIESTA_START_TIME"));  //午休時間(起) in Secs
//			int siesta_end_time = this.TimeToSecs((String)classMap.get("SIESTA_END_TIME"));  //午休時間(迄) in Secs
			int work_end_time = ems_tools.TimeToSecs((String)classMap.get("WORK_END_TIME"));  //下班時間 in Secs
			
			int ovt_start_tome = ems_tools.TimeToSecs((String)classMap.get("OVT_START_TIME"));  //加班上班時間 in Secs
			
			int limit_time_sec = ems_tools.TimeToSecs(limit_time);  //限制時間 in Secs
			
			String limit_datetime = limit_time.substring(0, 2)+":"+limit_time.substring(2, 4)+":00";
			
			//計算加班的限制時間(	Calendar)
			//有加班
			//判斷加班上班是否跨午夜十二點
			if(ems_tools.hasCrossMidnight(classMap, 5)){
				cal = this.compareLimitTime(date, limit_datetime, limit_time_sec, work_end_time);
			}else if(ems_tools.hasCrossMidnight(classMap, 6)){
				cal = this.compareLimitTime(date, limit_datetime, limit_time_sec, ovt_start_tome);
			}else{
				//沒有跨午夜十二點
				cal = ems_att_tools.datetimeToCalendar(date, limit_datetime);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	*//**
	 * 依據加班限制條件判斷, 計算加班時數
	 * @param cal
	 * @param attMap
	 * @param classMap
	 * @return
	 */
	protected Map countOvertimeHour(Connection conn,
									EHF030602M0F ehf030602m0, EHF030107M0F ehf030107m0, Map attMap, Map classMap, boolean limit_cond_flag ){
		
		Map overtime_Map = new HashMap();
		float hour = 0;  //加班總時數
		
		try{
			
//			Calendar class_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 1);  //班別設定的上班時間(Calendar)
//			Calendar class_siesta_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 7);  //班別設定的上午下班時間(Calendar)
//			Calendar class_siesta_out_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = ems_att_tools.getCalendarByClass(conn,(String) attMap.get("考勤日期"), (String)attMap.get("員工工號"),
														this.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)		
			Calendar class_ov_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"),
													 (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 5);  //班別設定的加班上班時間(Calendar)
			
			Calendar cur_in_cal = this.datetimeToCalendar((String) attMap.get("加班上班刷卡日期時間"));  //實際加班上班時間(Calendar)
			Calendar cur_out_cal = this.datetimeToCalendar((String) attMap.get("加班下班刷卡日期時間"));  //實際加班下班時間(Calendar)
			
			//取得正常加班考勤紀錄
			Map GetOvertimeHour = this.getOvertimeHour(conn,class_out_cal, (String) attMap.get("考勤日期"), (String)attMap.get("員工工號"),this.sta_comp_id,">=");
			
			
			
			
			//增加判斷加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄則只需要判斷加班下班的資料即可判定是否需要計算加班費
			boolean chk_flag = false;
			if(cur_in_cal == null){
				chk_flag = ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)attMap.get("員工工號"), 
														     ehf030602m0.getEHF030602T0_07() );
				//如果有設定不記錄下班與加班上班, 則依據班別設定中的加班上班時間直接設定為加班上班時間
				if(chk_flag){
					cur_in_cal = (Calendar) class_ov_in_cal.clone();
				}
			}

			//若實際加班上班,加班下班取不到考勤資料的刷卡時間, 表示加班考勤資料異常且無法計算加班區間時數, 直接回傳家班時數 = 0.
			if( cur_in_cal == null || cur_out_cal == null || GetOvertimeHour.size()==0){
				//寫入異常資訊
				hour = 0;
				overtime_Map.put("HOUR", hour);
				overtime_Map.put("START_TIME", "" );
				overtime_Map.put("END_TIME", "" );
				
				return overtime_Map;
			}
			
			Calendar class_ov_out_cal = null;
			
			class_ov_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
					(String)attMap.get("員工工號"), this.sta_comp_id, classMap, 6);  //班別設定的加班下班時間(Calendar)
			
			Calendar range_class_ov_in_cal = (Calendar)class_ov_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_ov_in_cal.add(Calendar.SECOND, 59); 
			/*
			Calendar class_ov_out_cal = null;
			if(limit_cond_flag){
				//有加班限制條件, 以限制條件產生的班別加班下班時間為主
				class_ov_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
												 (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 6);  //班別設定的加班下班時間(Calendar)
			}else{
				//沒有加班限制條件, 實際加班下班考勤資料為主
				class_ov_out_cal = (Calendar) cur_out_cal.clone();
			}
		
			Calendar range_class_ov_in_cal = (Calendar)class_ov_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_ov_in_cal.add(Calendar.SECOND, 59); 
			
			//加班設定的區間總時數
			float total_ov_hour = this.caculateDiffHourWithFIX( class_ov_in_cal, class_ov_out_cal);
			
			//判斷加班的情況
			if( cur_in_cal.compareTo(range_class_ov_in_cal) <= 0 ){
				//加班上班 - 正常
				if(cur_out_cal.compareTo(class_ov_out_cal) >= 0 ){
					//加班下班 - 正常
					//計算 班別加班上班時間 至 班別加班下班時間  中間的時數
					hour = this.caculateDiffHourWithFIX( class_ov_in_cal, class_ov_out_cal);
					
				}else if( cur_out_cal.compareTo(class_ov_out_cal) < 0 ){
					//加班下班 - 異常
					//計算 班別加班上班時間 至 實際加班下班時間  中間的時數
					hour = this.caculateDiffHourWithFIX( class_ov_in_cal, cur_out_cal);
					
				}
			}else if( cur_in_cal.compareTo(range_class_ov_in_cal) > 0 ){
				//加班上班 - 異常
				if( cur_out_cal.compareTo(class_ov_out_cal) >= 0 ){
					//加班下班 - 正常
					//計算 實際加班上班時間 至 班別加班下班時間  中間的時數
					hour = this.caculateDiffHourWithFIX( cur_in_cal, class_ov_out_cal);
					
				}else if( cur_out_cal.compareTo(class_ov_out_cal) < 0 ){
					//加班下班 - 異常
					//計算 實際加班上班時間 至 實際加班下班時間  中間的時數
					hour = this.caculateDiffHourWithFIX( cur_in_cal, cur_out_cal);
					
				}
			}
			*/
			
			//總加班時數(含內扣)
			hour = Float.valueOf((String)GetOvertimeHour.get("hour"));
			
			//取得班別的提前加班內扣時數
			float deducted_hour = Float.parseFloat((String)GetOvertimeHour.get("deducted_hour"));
			hour = hour - deducted_hour;
			
			//加班設定的區間總時數(加班單)
			float total_ov_hour = this.caculateDiffHourWithFIX( class_ov_in_cal, class_ov_out_cal) - deducted_hour;
			//實際加班
			float c_hour = 0;
			
			//判斷加班的情況
			if( cur_in_cal.compareTo(range_class_ov_in_cal) <= 0 ){
				//加班上班 - 正常
				if(cur_out_cal.compareTo(class_ov_out_cal) >= 0 ){
					//加班下班 - 正常
					//計算 加班單加班上班時間 至 加班單加班下班時間  中間的時數 扣除休息時間
					c_hour = this.caculateDiffHourWithFIX( class_ov_in_cal, class_ov_out_cal) - deducted_hour;
					
				}else if( cur_out_cal.compareTo(class_ov_out_cal) < 0 ){
					//加班下班 - 異常
					//計算 加班單加班上班時間 至 實際加班下班時間  中間的時數 扣除休息時間
					c_hour = this.caculateDiffHourWithFIX( class_ov_in_cal, cur_out_cal) - deducted_hour;
					
				}
			}else if( cur_in_cal.compareTo(range_class_ov_in_cal) > 0 ){
				//加班上班 - 異常
				if( cur_out_cal.compareTo(class_ov_out_cal) >= 0 ){
					//加班下班 - 正常
					//計算 實際加班上班時間 至 加班單加班下班時間  中間的時數 扣除休息時間
					c_hour = this.caculateDiffHourWithFIX( cur_in_cal, class_ov_out_cal) - deducted_hour;
					
				}else if( cur_out_cal.compareTo(class_ov_out_cal) < 0 ){
					//加班下班 - 異常
					//計算 實際加班上班時間 至 實際加班下班時間  中間的時數 扣除休息時間
					c_hour = this.caculateDiffHourWithFIX( cur_in_cal, cur_out_cal) - deducted_hour;
					
				}
			}
			
			//進行加班設定中, 加班內扣時數的處理!!
			//取得加班內扣時數並進行加班內扣時數處理
			//hour = hour - (Float.parseFloat(ehf010102m0.getEHF010102T0_14()));
			
			//時數不可為負的, 若為負的, 時數 = 0
			if(hour < 0){
				hour = 0;
			}
			
			//處理異常資訊的寫入
			float ab_hour = total_ov_hour - c_hour;
			if( ab_hour > 0 && hour > 0 ){
				//有異常, 若 hour = 0 表示沒有加班, 不需記錄異常
				//進行異常資訊處理
				//產生加班費異常資訊
				ehf030602m0 = this.generateAbnormalOvertimeData( ehf030602m0, (String) attMap.get("考勤日期"), ab_hour, "02",
							  (String) attMap.get("考勤日期")+" " +
							  "加班考勤資料異常:加班("+(String) attMap.get("加班上班刷卡日期時間")+" ~ "+(String) attMap.get("加班下班刷卡日期時間")+"), " +
							  ehf030107m0.getEHF030107T0_03()+" "+
						      "扣除異常時數:"+ab_hour+"小時, 發放"+hour+"小時加班費, " +
							  "" );
				
			}
			
			overtime_Map.put("HOUR", hour);
			overtime_Map.put("START_TIME", chk_flag==true?(ems_att_tools.CalendarToString(cur_in_cal)):(String) attMap.get("加班上班刷卡日期時間") );
			overtime_Map.put("END_TIME", (String) attMap.get("加班下班刷卡日期時間") );
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return overtime_Map;
	}
	
	/**
	 * 判斷與計算加班費用
	 * @param conn
	 * @param ehf030602m0
	 * @param cur_date
	 * @param overtime_start
	 * @param overtime_end
	 * @param ehf010102m0
	 * @param befor_overtime_hour
	 * @param late_overtime_hour
	 * @param overtime_hour
	 * @return
	 */
	protected EHF030602M0F generateOvertimeData( Connection conn, EHF030602M0F ehf030602m0, String cur_date,
							EHF030107M0F ehf030107m0, Map befor_overtime_map, Map late_overtime_map, Map overtime_map, int hour_pay,
						   boolean overwrite_rate_flag){
		
		int aov_cost = 0;
		int lov_cost = 0;
		int ov_cost = 0;
		String aov_cost_str = "";
		String lov_cost_str = "";
		String ov_cost_str = "";
		List countlist = null;
		
		
//		(String)tempMap.get("加班上班刷卡日期時間"), (String)tempMap.get("加班下班刷卡日期時間")

		try{
			String overtime_pay_mode = tools.getSysParam(conn, ehf030602m0.getEHF030602T0_07(), "OVERTIME_PAY_MODE");
			
			//設定各種加班時數
			float befor_overtime_hour = (Float)befor_overtime_map.get("HOUR");  //提前加班時數
			//float late_overtime_hour = (Float)late_overtime_map.get("HOUR");  //延後下班時數
			float overtime_hour = (Float)overtime_map.get("HOUR");  //加班時數
			
			
			//準備加班的相關參數
			float before_hour_one = Float.parseFloat(ehf030107m0.getEHF030107T0_04());//2
			float before_rate_one = Float.parseFloat(ehf030107m0.getEHF030107T0_05());//1.33
			float before_hour_two = Float.parseFloat(ehf030107m0.getEHF030107T0_06());//2
			float before_rate_two = Float.parseFloat(ehf030107m0.getEHF030107T0_07());//1.66
			
			
			if(befor_overtime_hour+overtime_hour<=before_hour_one){
				//不超過設定的時數 (before_hour_one)
				
				if(befor_overtime_hour>0){
					//有提前加班
					//==========================================紀錄提前加班開始=======================================================
					aov_cost = tools.fixNumByMode((hour_pay * befor_overtime_hour * before_rate_one), overtime_pay_mode);
					aov_cost_str = " "+overtime_pay_mode+"("+hour_pay+"X"+befor_overtime_hour+"X"+before_rate_one+") " +" ) ";
				
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																   hour_pay, before_rate_one, before_hour_one, "01");
					//產生加班費細項資料
					ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date, 
															 (String)befor_overtime_map.get("START_TIME"),
															 (String)befor_overtime_map.get("END_TIME"),
															 befor_overtime_hour, aov_cost, aov_cost_str, "01");  //提前加班
				}
				//==========================================紀錄提前加班結束=======================================================	
				
				//==========================================紀錄加班開始=======================================================
				if(overtime_hour>0){
					//有加班
					aov_cost = tools.fixNumByMode((hour_pay * overtime_hour * before_rate_one ),overtime_pay_mode);
					aov_cost_str = " "+overtime_pay_mode+"("+hour_pay+"X"+overtime_hour+"X"+before_rate_one+")";
					
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
					   											hour_pay, before_rate_one, before_hour_one, "02");
					//產生加班費細項資料
					ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date,
													  	  (String)overtime_map.get("START_TIME"),
													  	  (String)overtime_map.get("END_TIME"),
													  	  overtime_hour, aov_cost, aov_cost_str, "02");  //加班
				}
				//==========================================紀錄加班結束=======================================================
			}else{
				//加班總時數超過設定數字
				if(befor_overtime_hour>=before_hour_one){
					//大於提前加班時數二
					aov_cost = tools.fixNumByMode((
							   tools.fixNumByMode((hour_pay * before_hour_one * before_rate_one), overtime_pay_mode) +
							   tools.fixNumByMode((hour_pay * (befor_overtime_hour - before_rate_one) * before_rate_two ),overtime_pay_mode)
								), overtime_pay_mode);
				
					aov_cost_str = " "+overtime_pay_mode+"( " +
							 	   " "+overtime_pay_mode+"("+hour_pay+"X"+before_hour_one+"X"+before_rate_one+") + " +
							       " "+overtime_pay_mode+"("+hour_pay+"X("+befor_overtime_hour+" - "+before_rate_one+")X"+before_rate_two+") " +
							       " ) ";
					
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																	   hour_pay, before_rate_one, before_hour_one,
																	   "01");
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																	   hour_pay, before_rate_two, (befor_overtime_hour - before_rate_one),
																	   "01");
				
					if(overtime_hour>0){
						//有加班
						aov_cost = tools.fixNumByMode((hour_pay * overtime_hour * before_rate_two ),overtime_pay_mode);
						aov_cost_str = " "+overtime_pay_mode+"("+hour_pay+"X"+overtime_hour+"X"+before_rate_two+")";
						
						//產生加班費計算明細
						ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
						   											hour_pay, before_rate_two, before_hour_one, "02");
						//產生加班費細項資料
						ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date,
														  	  (String)overtime_map.get("START_TIME"),
														  	  (String)overtime_map.get("END_TIME"),
														  	  overtime_hour, aov_cost, aov_cost_str, "02");  //加班
					}
				
				
				}else if(befor_overtime_hour<before_hour_one  &&   overtime_hour>=before_hour_one){
					//提前加班小於設定數字   &&   加班大於設定數字
					if(befor_overtime_hour>0){
					//==========================================紀錄提前加班開始=======================================================
					aov_cost = tools.fixNumByMode((hour_pay * befor_overtime_hour * before_rate_one), overtime_pay_mode);
					aov_cost_str = " "+overtime_pay_mode+"("+hour_pay+"X"+befor_overtime_hour+"X"+before_rate_one+") " +" ) ";
				
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																   hour_pay, before_rate_one, before_hour_one, "01");
					//產生加班費細項資料
					ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date, 
															 (String)befor_overtime_map.get("START_TIME"),
															 (String)befor_overtime_map.get("END_TIME"),
															 befor_overtime_hour, aov_cost, aov_cost_str, "01");  //提前加班
					//==========================================紀錄提前加班結束=======================================================
					}
					float time_1=before_hour_one-befor_overtime_hour;//先計算出提前加班與設定的數字 "相差"多少
					
					//大於加班時數一
					ov_cost = tools.fixNumByMode((
							  tools.fixNumByMode((hour_pay * time_1 * before_rate_one), overtime_pay_mode) +
							  tools.fixNumByMode((hour_pay * (overtime_hour-time_1) * before_rate_two ), overtime_pay_mode)
								), overtime_pay_mode);
				
					ov_cost_str = " "+overtime_pay_mode+"( " +
					  " "+overtime_pay_mode+"("+hour_pay+"X"+time_1+"X"+before_rate_one+") + " +
					  " "+overtime_pay_mode+"("+hour_pay+"X("+overtime_hour+"-"+time_1+")X"+before_rate_two+") " +
					  " ) ";
				
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																   hour_pay, before_rate_one, overtime_hour,
																   "02");
					//產生加班費細項資料
					ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date,
															  	  (String)overtime_map.get("START_TIME"),
															  	  (String)overtime_map.get("END_TIME"),
															  	  overtime_hour, ov_cost, ov_cost_str, "02");  //加班
	
				}else if(befor_overtime_hour<before_hour_one  &&   overtime_hour<before_hour_one){
					if(befor_overtime_hour>0){
					//==========================================紀錄提前加班開始=======================================================
					aov_cost = tools.fixNumByMode((hour_pay * befor_overtime_hour * before_rate_one), overtime_pay_mode);
					aov_cost_str = " "+overtime_pay_mode+"("+hour_pay+"X"+befor_overtime_hour+"X"+before_rate_one+") " +" ) ";
				
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																   hour_pay, before_rate_one, before_hour_one, "01");
					//產生加班費細項資料
					ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date, 
															 (String)befor_overtime_map.get("START_TIME"),
															 (String)befor_overtime_map.get("END_TIME"),
															 befor_overtime_hour, aov_cost, aov_cost_str, "01");  //提前加班
					}
					float time_1=before_hour_one-befor_overtime_hour;//先計算出提前加班與設定的數字 "相差"多少
					//大於加班時數一
					ov_cost = tools.fixNumByMode((
							  tools.fixNumByMode((hour_pay * time_1 * before_rate_one), overtime_pay_mode) +
							  tools.fixNumByMode((hour_pay * (overtime_hour-time_1) * before_rate_two ), overtime_pay_mode)
								), overtime_pay_mode);
				
					ov_cost_str = " "+overtime_pay_mode+"( " +
					  " "+overtime_pay_mode+"("+hour_pay+"X"+time_1+"X"+before_rate_one+") + " +
					  " "+overtime_pay_mode+"("+hour_pay+"X("+overtime_hour+"-"+time_1+")X"+before_rate_two+") " +
					  " ) ";
				
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																   hour_pay, before_rate_one, overtime_hour,
																   "02");
					//產生加班費細項資料
					ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date,
															  	  (String)overtime_map.get("START_TIME"),
															  	  (String)overtime_map.get("END_TIME"),
															  	  overtime_hour, ov_cost, ov_cost_str, "02");  //加班
					
					
				}else{
					System.out.println(befor_overtime_hour);
					System.out.println(overtime_hour);
					
				}
				
				
				
				
				
			}
			/*
			//判斷是否覆寫加班費率
			if(overwrite_rate_flag){

			}else{
				//不覆寫加班費率
				try{
					countlist = new ArrayList();
				
					//計算提前加班的加班費用
					if( befor_overtime_hour > 0 ){
						//提前加班時數大於0, 才計算提前加班的加班費用
						
						//準備提前加班的相關參數
						 before_hour_one = Float.parseFloat(ehf030107m0.getEHF030107T0_04());//2
						 before_rate_one = Float.parseFloat(ehf030107m0.getEHF030107T0_05());//1.33
						 before_hour_two = Float.parseFloat(ehf030107m0.getEHF030107T0_06());//2
						 before_rate_two = Float.parseFloat(ehf030107m0.getEHF030107T0_07());//1.66
						//float before_hour_three = Float.parseFloat(ehf010102m0.getEHF010102T0_25());
						//float before_rate_three = Float.parseFloat(ehf010102m0.getEHF010102T0_26());
						if( befor_overtime_hour >  before_hour_two){
							//大於提前加班時數二
							aov_cost = tools.fixNumByMode((
									   tools.fixNumByMode((hour_pay * before_hour_one * before_rate_one), overtime_pay_mode) +
									   tools.fixNumByMode((hour_pay * (befor_overtime_hour - before_hour_two) * before_rate_two ),overtime_pay_mode)
										), overtime_pay_mode);
						
							aov_cost_str = " "+overtime_pay_mode+"( " +
									 	   " "+overtime_pay_mode+"("+hour_pay+"X"+before_hour_one+"X"+before_rate_one+") + " +
									       " "+overtime_pay_mode+"("+hour_pay+"X("+befor_overtime_hour+" - "+before_hour_two+")X"+before_rate_two+") " +
									       " ) ";
							
							//產生加班費計算明細
							ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																			   hour_pay, before_rate_one, before_hour_one,
																			   "01");
							ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																			   hour_pay, before_rate_two, (befor_overtime_hour - before_hour_two),
																			   "01");
						
						}else if( befor_overtime_hour <= before_hour_one ){
							//大於提前加班時數一
							aov_cost = tools.fixNumByMode((hour_pay * befor_overtime_hour * before_rate_one), overtime_pay_mode);
						
							aov_cost_str = " "+overtime_pay_mode+"( ("+hour_pay+"X"+befor_overtime_hour+"X"+before_rate_one+") ) ";
							
							//產生加班費計算明細
							ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																			   hour_pay, before_rate_one, befor_overtime_hour,
																			   "01");
							
						}else{
							aov_cost = 0;
						
						}
					
						//產生加班費細項資料
						ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date, 
																	 (String)befor_overtime_map.get("START_TIME"),
																	 (String)befor_overtime_map.get("END_TIME"),
																	 befor_overtime_hour, aov_cost, aov_cost_str, "01");  //提前加班
					
					}
				
				}catch(Exception e){
					e.printStackTrace();
				}

				try{
					//計算加班的加班費用
					if( overtime_hour > 0 ){
						//加班時數大於0, 才計算加班的加班費用
						
						//準備加班的相關參數
						float hour_one = Float.parseFloat(ehf030107m0.getEHF030107T0_04());//2
						float rate_one = Float.parseFloat(ehf030107m0.getEHF030107T0_05());//1.33
						float hour_two = Float.parseFloat(ehf030107m0.getEHF030107T0_06());//2
						float rate_two = Float.parseFloat(ehf030107m0.getEHF030107T0_07());//1.66
						//float hour_three = Float.parseFloat(ehf010102m0.getEHF010102T0_27());
						//float rate_three = Float.parseFloat(ehf010102m0.getEHF010102T0_28());
						
						if( befor_overtime_hour >  hour_two){
							//當提前加班時數  超過設定的小時
							ov_cost = tools.fixNumByMode((
									  //ems_tools.fixNumByMode((hour_pay * hour_one * rate_one), overtime_pay_mode) +
									  tools.fixNumByMode((hour_pay * overtime_hour * rate_two ), overtime_pay_mode)
										), overtime_pay_mode);
						
							ov_cost_str = " "+overtime_pay_mode+"( " +
										  //" "+overtime_pay_mode+"("+hour_pay+"X"+hour_one+"X"+rate_one+") + " +
										  " "+overtime_pay_mode+"("+hour_pay+"X("+overtime_hour+")X"+rate_two+") " +
										  " ) ";
							
							//產生加班費計算明細
							ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																			   hour_pay, rate_one, hour_one,
																			   "02");
							ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																			   hour_pay, rate_two, (overtime_hour - hour_two),
																			   "02");
						
						}else if( befor_overtime_hour <= hour_one ){
							float time_1=hour_one-befor_overtime_hour;//先計算出提前加班與設定的數字 "相差"多少
							if(time_1>=0  &&  time_1!=hour_one){
								
								//大於加班時數一
								ov_cost = tools.fixNumByMode((
										  tools.fixNumByMode((hour_pay * time_1 * rate_one), overtime_pay_mode) +
										  tools.fixNumByMode((hour_pay * (overtime_hour-time_1) * rate_two ), overtime_pay_mode)
											), overtime_pay_mode);
							
								ov_cost_str = " "+overtime_pay_mode+"( " +
								  " "+overtime_pay_mode+"("+hour_pay+"X"+time_1+"X"+rate_one+") + " +
								  " "+overtime_pay_mode+"("+hour_pay+"X("+overtime_hour+"-"+time_1+")X"+rate_two+") " +
								  " ) ";
							
							//產生加班費計算明細
							ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																			   hour_pay, rate_one, overtime_hour,
																			   "02");
							}else{
								//大於加班時數一
								ov_cost = tools.fixNumByMode((
										  tools.fixNumByMode((hour_pay * hour_one * rate_one), overtime_pay_mode) +
										  tools.fixNumByMode((hour_pay * (overtime_hour-hour_one) * rate_two ), overtime_pay_mode)
											), overtime_pay_mode);
							
								ov_cost_str = " "+overtime_pay_mode+"( " +
								  " "+overtime_pay_mode+"("+hour_pay+"X"+hour_one+"X"+rate_one+") + " +
								  " "+overtime_pay_mode+"("+hour_pay+"X("+overtime_hour+"-"+hour_one+")X"+rate_two+") " +
								  " ) ";
							
							//產生加班費計算明細
							ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																			   hour_pay, rate_one, overtime_hour,
																			   "02");
							}
						}else{
							ov_cost = 0;
							
						}
					
						//產生加班費細項資料
						ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date,
																  	  (String)overtime_map.get("START_TIME"),
																  	  (String)overtime_map.get("END_TIME"),
																  	  overtime_hour, ov_cost, ov_cost_str, "02");  //加班
				}
				
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			*/
		}catch(Exception e){
			System.out.println("產生加班費資料的Form(EHF030602M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030602m0;
	}
	
	protected int generateOvertimeDataByType( EHF030602M0F ehf030602m0, String cur_date, Map overtime_map,
											  float overtime_hour, int hour_pay, float ov_hour_one, float ov_rate_one, 
											  float ov_hour_two, float ov_rate_two, float ov_hour_three, float ov_rate_three, 
											  String overtime_pay_mode, String overtime_type ){
		int level = 1;  //級距
		
		try{
			int cost = 0;
			String cost_str = "";
			
			//判斷是否需要計算加班費用
			if( overtime_hour > 0 ){
				//加班時數大於0, 才計算加班費用
				
				if(overtime_hour > ov_hour_three){
					//第三級距
					level = 3;
					
					//大於加班時數三
					cost = tools.fixNumByMode((
							   tools.fixNumByMode((hour_pay * ov_hour_one * ov_rate_one), overtime_pay_mode) +
							   tools.fixNumByMode((hour_pay * (ov_hour_three - ov_hour_two) * ov_rate_two ), overtime_pay_mode) +
							   tools.fixNumByMode((hour_pay * (overtime_hour - ov_hour_three) * ov_rate_three ), overtime_pay_mode)
								), overtime_pay_mode);
					
					cost_str = " "+overtime_pay_mode+"( " +
								   " "+overtime_pay_mode+"("+hour_pay+"X"+ov_hour_one+"X"+ov_rate_one+") + " +
							   	   " "+overtime_pay_mode+"("+hour_pay+"X("+ov_hour_three+" - "+ov_hour_two+")X"+ov_rate_two+") + " +
							   	   " "+overtime_pay_mode+"("+hour_pay+"X("+overtime_hour+" - "+ov_hour_three+")X"+ov_rate_three+") " +
							   	   " ) ";
					
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																	   hour_pay, ov_rate_one, ov_hour_one,
																	   overtime_type);
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																	   hour_pay, ov_rate_two, (ov_hour_three - ov_hour_two),
																	   overtime_type);
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																	   hour_pay, ov_rate_three, (overtime_hour - ov_hour_three),
																	   overtime_type);
		
				}else if( overtime_hour >  ov_hour_two){
					//第二級距
					level = 2;
					
					//大於加班時數二
					cost = tools.fixNumByMode((
							   tools.fixNumByMode((hour_pay * ov_hour_one * ov_rate_one), overtime_pay_mode) +
							   tools.fixNumByMode((hour_pay * (overtime_hour - ov_hour_two) * ov_rate_two ),overtime_pay_mode)
								), overtime_pay_mode);
				
					cost_str = " "+overtime_pay_mode+"( " +
							 	   " "+overtime_pay_mode+"("+hour_pay+"X"+ov_hour_one+"X"+ov_rate_one+") + " +
							       " "+overtime_pay_mode+"("+hour_pay+"X("+overtime_hour+" - "+ov_hour_two+")X"+ov_rate_two+") " +
							       " ) ";
					
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																	   hour_pay, ov_rate_one, ov_hour_one,
																	   overtime_type);
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																	   hour_pay, ov_rate_two, (overtime_hour - ov_hour_two),
																	   overtime_type);
				
				}else if( overtime_hour <= ov_hour_one ){
					//第一級距
					level = 1;
					
					//大於加班時數一
					cost = tools.fixNumByMode((hour_pay * overtime_hour * ov_rate_one), overtime_pay_mode);
				
					cost_str = " "+overtime_pay_mode+"( ("+hour_pay+"X"+overtime_hour+"X"+ov_rate_one+") ) ";
					
					//產生加班費計算明細
					ehf030602m0 = this.generateOvertimeCountDetailData(ehf030602m0, cur_date, 
																	   hour_pay, ov_rate_one, overtime_hour,
																	   overtime_type);
					
				}else{
					cost = 0;
				
				}
			
				//產生加班費細項資料
				ehf030602m0 = this.generateOvertimeDetailData(ehf030602m0, cur_date, 
															 (String)overtime_map.get("START_TIME"),
															 (String)overtime_map.get("END_TIME"),
															 overtime_hour, cost, cost_str, overtime_type);  //加班
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return level;
	}
	
	/**
	 * 新增加班費資料,加班費細項資料到資料庫
	 * @param conn
	 * @param ehf030602m0
	 * @return
	 */
	public String addOvertime( Connection conn, EHF030602M0F ehf030602m0 ){
		
		String EHF030602T0_U = "";
		
		try{
			if(ehf030602m0 != null){ 
				//新增加班費表頭資料
				EHF030602T0_U = this.addOvertimeData(conn, ehf030602m0, ehf030602m0.getUSER_CREATE(), ehf030602m0.getEHF030602T0_07());
				
				List list = ehf030602m0.getEHF030602C();
				
				//list.size() > 0 表示有資料, 才新增加班費明細資料
				Iterator it = list.iterator();
				
				while(it.hasNext()){
					
					EHF030602M0F bean = (EHF030602M0F) it.next();
					bean.setEHF030602T0_U(EHF030602T0_U);  //加班費明細UID
					//新增加班費細項資料
					this.addOvertimeDetailData(conn, bean, ehf030602m0.getUSER_CREATE(), bean.getEHF030602T1_07());
				}
				
				//處理異常加班費資訊
				if(ehf030602m0.getEHF030602C2().size() > 0){
					//有異常加班費資料才進行異常處理
					List abnormallist = ehf030602m0.getEHF030602C2();
					
					//list.size() > 0 表示有資料, 才新增異常加班費資料
					Iterator abit = abnormallist.iterator();
					
					while(abit.hasNext()){
						
						EHF030602M2F bean2 = (EHF030602M2F) abit.next();
						bean2.setEHF030602T2_U(EHF030602T0_U);  //加班費明細UID
						//新增異常津貼資料
						this.addAbnormalOvertimeData(conn, bean2, ehf030602m0.getUSER_CREATE(), bean2.getEHF030602T2_04());
					}
				}
				
				//處理加班費計算明細
				if(ehf030602m0.getEHF030602C3().size() > 0){
					//有加班費計算明明細資料才進行處理
					List countlist = ehf030602m0.getEHF030602C3();
					
					//list.size() > 0 表示有資料, 才新增加班費計算明細資料
					Iterator coit = countlist.iterator();
					
					while(coit.hasNext()){
						
						EHF030602M3F bean3 = (EHF030602M3F) coit.next();
						bean3.setEHF030602T3_U(EHF030602T0_U);  //加班費明細UID
						//新增加班費計算明細資料
						this.addOvertimeCountDetailData(conn, bean3, ehf030602m0.getUSER_CREATE(), bean3.getEHF030602T3_07());
					}
				}
				
			}
			
		}catch(Exception e){
			System.out.println("新增加班費資料與加班費細項資料到資料庫時,發生錯誤");
			e.printStackTrace();
		}
		
		return EHF030602T0_U;
	}
	
	/**
	 * 產生加班費表頭資料Form
	 * @param employee_id
	 * @param dept_id
	 * @param salYYMM
	 * @param costYYMM
	 * @param user_name
	 * @param comp_id
	 * @return
	 */
	protected EHF030602M0F generateOvertimeHeadData( String employee_id, String dept_id,
						String salYYMM, String costYYMM, String user_name, String comp_id ){
		
		EHF030602M0F Form = null;
		List list = new ArrayList();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		
		try{
			Form = new EHF030602M0F();
			
			Form.setEHF030602T0_U("");  //加班費明細UID
			Form.setEHF030602T0_01(employee_id);  //員工工號
			Form.setEHF030602T0_02(dept_id);  //部門代號
			Form.setEHF030602T0_M(costYYMM);  //入扣帳年月
			Form.setEHF030602T0_M1(salYYMM);  //薪資年月
			Form.setEHF030602T0_DS("01");  //資料來源
			Form.setEHF030602T0_03("");  //備註
			Form.setEHF030602T0_SCP("01");  //薪資處理狀態
			Form.setEHF030602T0_07(comp_id);  //公司代碼
			Form.setEHF030602T0_SCU("");  //薪資計算明細UID
			Form.setUSER_CREATE(user_name);  //建立人員
			Form.setUSER_UPDATE(user_name); //修改人員
			
			Form.setEHF030602C(list);  //建立空的LIST
			Form.setEHF030602C2(list2);  //建立空的LIST
			Form.setEHF030602C3(list3);  //建立空的LIST
			
		}catch(Exception e){
			System.out.println("產生加班費資料的Form(EHF030602M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return Form;
	}
	
	/**
	 * 產生加班費細項資料Form
	 * @param ehf030602m0
	 * @param overtime_date
	 * @param overtime_start
	 * @param overtime_end
	 * @param overtime_hour
	 * @param overtime_money
	 * @param overtime_count_detail
	 * @param overtime_type_desc
	 * @return
	 */
	protected EHF030602M0F generateOvertimeDetailData( EHF030602M0F ehf030602m0, String overtime_date, String overtime_start, String overtime_end, 
						   float overtime_hour, int overtime_money, String overtime_count_detail, String overtime_type ){

		EHF030602M0F bean = null;
		List list = null;

		try{
			list = ehf030602m0.getEHF030602C();
			bean = new EHF030602M0F();
			
			bean.setEHF030602T1_U("");  //加班費明細UID
			bean.setEHF030602T1_DATE(overtime_date);  //加班日期(考勤日期)
			bean.setEHF030602T1_TYPE(overtime_type);  //加班類型
			bean.setEHF030602T1_01(overtime_start);  //加班日期時間(起)
			bean.setEHF030602T1_02(overtime_end);  //加班日期時間(迄)
			bean.setEHF030602T1_03(overtime_hour);  //加班時數
			bean.setEHF030602T1_04(overtime_money);  //加班費金額
			bean.setEHF030602T1_05(overtime_count_detail);  //加班費計算明細
			bean.setEHF030602T1_DS("01");  //資料來源
			bean.setEHF030602T1_06("");  //備註
			bean.setEHF030602T1_07(ehf030602m0.getEHF030602T0_07());  //公司代碼
			
			list.add(bean);
			
			ehf030602m0.setEHF030602C(list);
			
		}catch(Exception e){
			System.out.println("產生加班費細項資料的bean(EHF030602M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030602m0;
	}
	
	/**
	 * 產生異常加班費細項資料Form
	 * @param ehf030602m0
	 * @param overtime_date
	 * @param abnormal_overtime_hour
	 * @param overtime_type
	 * @param abnormal_desc
	 * @return
	 */
	protected EHF030602M0F generateAbnormalOvertimeData( EHF030602M0F ehf030602m0, String overtime_date, 
						   float abnormal_overtime_hour, String overtime_type, String abnormal_desc ){

		EHF030602M2F bean = null;
		List list = null;

		try{
			list = ehf030602m0.getEHF030602C2();
			bean = new EHF030602M2F();
			
			bean.setEHF030602T2_U("");  //加班費明細UID
			bean.setEHF030602T2_DATE(overtime_date);  //加班日期(考勤日期)
			bean.setEHF030602T2_TYPE(overtime_type);  //加班類型
			bean.setEHF030602T2_01(abnormal_overtime_hour);  //異常加班扣除時數
			bean.setEHF030602T2_02(abnormal_desc);  //異常加班費說明
			bean.setEHF030602T2_DS("01");  //資料來源
			bean.setEHF030602T2_03("");  //備註
			bean.setEHF030602T2_04(ehf030602m0.getEHF030602T0_07());  //公司代碼
			
			list.add(bean);
			
			ehf030602m0.setEHF030602C2(list);
			
		}catch(Exception e){
			System.out.println("產生異常加班費細項資料的bean(EHF030602M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030602m0;
	}
	
	/**
	 * 產生加班費計算明細
	 * @param ehf030602m0
	 * @param overtime_date
	 * @param hour_pay
	 * @param rate
	 * @param hours
	 * @param overtime_type
	 * @return
	 */
	protected EHF030602M0F generateOvertimeCountDetailData( EHF030602M0F ehf030602m0, String overtime_date, int hour_pay, float rate, 
															float hours, String overtime_type ){

		EHF030602M3F bean = null;
		List list = null;

		try{
			//加班時數大於0, 才需要記錄
			if( hours > 0){
				list = ehf030602m0.getEHF030602C3();
				bean = new EHF030602M3F();
			
				bean.setEHF030602T3_U("");  //加班費明細UID
				bean.setEHF030602T3_01(overtime_date);  //加班日期(考勤日期)
				bean.setEHF030602T3_02(overtime_type);  //加班類型
				bean.setEHF030602T3_03(hour_pay);  //時薪
				bean.setEHF030602T3_04(rate);  //時薪倍數
				bean.setEHF030602T3_05(hours);  //加班時數
				bean.setEHF030602T3_DS("01");  //資料來源
				bean.setEHF030602T3_06("");  //備註
				bean.setEHF030602T3_07(ehf030602m0.getEHF030602T0_07());  //公司代碼
				
				list.add(bean);
					
				ehf030602m0.setEHF030602C3(list);
			}
			
		}catch(Exception e){
			System.out.println("產生加班費細項資料的bean(EHF030602M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030602m0;
	}
	
	/**
	 * 選擇計算不休假加班費的日期類別
	 * @param conn
	 * @param classMap
	 * @param processList
	 * @return
	 */
	protected Map countHaOvertime( Connection conn, List processList , String start_date, String end_date){
		
		Map msgMap = new HashMap();
		
		try{
			//指定月份
			if("01".equals(this.sta_date_type)){
				msgMap = this.countHaOvertimeByEmp(conn, processList, start_date, end_date);
			//指定日期
			}else if("02".equals(this.sta_date_type)){
				msgMap = this.countHaOvertimeByEmp(conn, processList, this.sta_start_date, this.sta_start_date);
			//指定日期區間
			}else if("03".equals(this.sta_date_type)){
				msgMap = this.countHaOvertimeByEmp(conn, processList, this.sta_start_date, this.sta_end_date);		
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("選擇不休假加班費日期類別  EMS_OATools.countHaOvertime() 時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return msgMap;
	}
	
	/**
	 * 計算員工的不休假加班費資料
	 * @param conn
	 * @param classMap
	 * @param haovertimelist
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	protected Map countHaOvertimeByEmp(Connection conn, List processList, String start_date, String end_date ){
		
		Map msgMap = new HashMap();
		Map tempMap = null;
		String cur_employee_id = "";
		Map empattdata = null;
		EHF030603M0F ehf030603m0 = null;
		int hour_pay = 0;
		int day_pay = 0;
		Map attTempMap = null;
		EMS_Work_ScheduleForm work_schedule = null;
		int tmp_class_no = -1;
		Map classMap = null;
		EHF030107M0F ehf030107m0 = null;
		
		try{
			Calendar start_cal = tools.covertStringToCalendar(start_date);  //津貼計算開始日期
			Calendar end_cal = tools.covertStringToCalendar(end_date);  //津貼計算結束日期
			Calendar cur_start_cal = null;
			Calendar cur_end_cal = null;
			boolean chk_run_flag = true;
			String cur_date = "";
			Map ChangeClass = new HashMap();
			
			//建立排班表元件
			//EMS_Work_Schedule_Table ems_work_schedule_tools = new EMS_Work_Schedule_Table(this.sta_user_name, this.sta_comp_id);
			
			//設定最低計薪時數
			this.sta_min_pay_hour = Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "MIN_PAY_HOUR"));
			
			//人員清單
			Iterator it = processList.iterator();
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				//建立計算日期區間
				cur_start_cal = (Calendar)start_cal.clone();
				cur_end_cal = (Calendar)end_cal.clone();
				
				//記錄員工工號
				cur_employee_id = (String) tempMap.get("EMPLOYEE_ID");
				
				//取得員工排班表資料Map In 全域變數
				//this.cur_emp_schedule = ems_work_schedule_tools.getDateRangeInScheduleMap(conn, cur_employee_id, start_date, end_date);
				
				//取得員工的考勤資料 Map && 排班班表資料
				empattdata = this.getEmpDayAttDataMap(conn, cur_employee_id, start_date, end_date, false, true, this.sta_comp_id);
								
				//產生不休假加班費表頭資料
				ehf030603m0 = this.generateHaOvertimeHeadData( cur_employee_id, this.cur_dept_id,
										   					   this.sta_salyymm, this.sta_costyymm, this.sta_user_name, this.sta_comp_id);
				
				//取得員工的'實際時薪'
				hour_pay = this.getEmpHourPay(conn, ehf030603m0.getEHF030603T0_01(), ehf030603m0.getEHF030603T0_07() );
				//取得員工的'實際日薪'
				day_pay = this.getEmpDayPay(conn, ehf030603m0.getEHF030603T0_01(), ehf030603m0.getEHF030603T0_07());
				
				//計算迴圈建立
				chk_run_flag = true;
				while(chk_run_flag){
					if(cur_start_cal.equals(cur_end_cal)){
						chk_run_flag = false;
					}
					
					cur_date = this.CalendarToString(cur_start_cal);  //當前計算津貼的考勤日期
					
					//顯示計算資訊
					//System.out.println("目前正在計算員工:"+cur_employee_id+" 的 "+cur_date+" 不休假加班費");
					
					//取得員工的考勤資料 Map && 排班班表資料
					//empattdata = this.getEmpDayAttDataMap(conn, cur_employee_id,cur_date, cur_date, false, true, this.sta_comp_id);
					/*					
					attTempMap = (Map) empattdata.get(cur_date);  //取得考勤日期的實際班別資訊					
					work_schedule = (EMS_Work_ScheduleForm) this.cur_emp_schedule.get(cur_date);  //取得員工當天的排班資訊
					
					//判斷是否為假日
					if(work_schedule != null && work_schedule.isHoliday_flag() && attTempMap == null ){
						//為假日
						//取得當日班別 classMap
						tmp_class_no = work_schedule.getClass_no();
					}else if(attTempMap != null){
						//取得當日班別 classMap
						tmp_class_no = (Integer)attTempMap.get("班別序號");
					}else{
						tmp_class_no = -1;
					}
					
					if(tmp_class_no != -1){
						classMap = (Map)((Map)this.sta_compClassMap.get(tmp_class_no)).get("CLASS");
					}else{
						//若取不到排班表中的班別設定則使用員工的當前班別設定做計算
						classMap = this.cur_classMap;
						tmp_class_no = (Integer) classMap.get("WORK_CLASS_NO");
					}
					*/
					//確認該員工有無預排換班(取得員工排班表)
					ChangeClass= new HashMap();
					ChangeClass = ems_att_tools.getEmpSchedule(conn, cur_employee_id, cur_date, ChangeClass);
					
					if(ChangeClass.isEmpty()){					
						//取得班別資訊，依員工班別資料(EHF010100T8)
						classMap = this.cur_classMap;
						
					}else{
						//取得班別資訊，依員工排班表(EHF010105T1)
						classMap = (Map)((Map)this.sta_compClassMap.get((Integer) ChangeClass.get("NEW_CLASS_NO"))).get("CLASS");
					}
					
					//取得班別設定的津貼詳細資料
					List allowancelist = this.getPersonalAllowanceData(conn, cur_employee_id, this.sta_comp_id);
					
					//取得員工使用的加班費規則
					List overtimelist = this.getemployeeOvertimeData(conn, cur_employee_id, this.sta_comp_id);
										
					//取得員工使用的加班費規則
					List it_haovertimelist = this.getemployeeOvertimeData(conn, cur_employee_id, this.sta_comp_id);
					//需計算的不休假加班費規則
					Iterator it_haovertime = it_haovertimelist.iterator();
										
					//取得班別設定的加班詳細資料
					//List overtimelist = ((List)((Map)this.sta_compClassMap.get(tmp_class_no)).get("OVERTIME"));
					
					//班別需計算的不休假加班清單
					//Iterator it_haovertime = ((List)((Map)this.sta_compClassMap.get(tmp_class_no)).get("HAOVERTIME")).iterator()
					
					//是否需要計算加班費
					if(this.checkCountOvertimeData(conn, cur_date, cur_employee_id, this.sta_comp_id)){
					
						while(it_haovertime.hasNext()){
							
							ehf030107m0 = (EHF030107M0F) it_haovertime.next();
							//計算不休假加班費資料
							ehf030603m0 = this.countHaOvertimeData( conn, classMap, cur_date, cur_date, ehf030603m0, ehf030107m0, empattdata,
																    allowancelist, overtimelist, hour_pay, day_pay);
						}
						
						cur_start_cal.add(Calendar.DAY_OF_MONTH, 1);
					
					}else{
						
						cur_start_cal.add(Calendar.DAY_OF_MONTH, 1);
						
					}
				}
				
				//判斷是否有不休假加班費細項資料, 有細項資料才進行資料庫 Insert
				if(ehf030603m0.getEHF030603C().size() > 0){
					//將產生完成的員工不休假加班費資料新增到資料庫中
					this.addHaOvertime(conn, ehf030603m0);
				}
			}
				
			//員工不休假加班費資料計算完成
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("計算員工:"+cur_employee_id+" 不休假加班費資料  EMS_OATools.countHaOvertimeByEmp() 時，發生錯誤!!"+ e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return msgMap;
	}
	
	/**
	 * 實際計算與判斷不休假加班費用
	 * @param conn
	 * @param classMap
	 * @param start_date
	 * @param end_date
	 * @param ehf030603m0
	 * @param ehf010103m0
	 * @param empattdata
	 * @return
	 */
	protected EHF030603M0F countHaOvertimeData( Connection conn, Map classMap, String start_date, String end_date, EHF030603M0F ehf030603m0,
						EHF030107M0F ehf030107m0, Map empattdata, List allowancelist, List overtimelist,int hour_pay, int day_pay ){
		
		Map msgMap = new HashMap();
		
		try{
			
			//判斷不休假加班是否有啟用
			if(tools.StringToBoolean(ehf030107m0.getEHF030107T0_09())){
				//有啟用才計算不休假加班費資料
				
				Calendar start_cal = tools.covertStringToCalendar(start_date);  //不休假加班計算開始日期
				Calendar end_cal = tools.covertStringToCalendar(end_date);  //不休假加班計算結束日期
				
				boolean chk_run_flag = true;
				
				while(chk_run_flag){
					
					if(start_cal.equals(end_cal)){
						chk_run_flag = false;
					}
					
					float ha_overtime_hour = 0;  //不休假加班時數
					EHF030601M0F ehf030601m0 = null;  //津貼資料
					EHF030602M0F ehf030602m0 = null;  //加班資料
					int type = 0;  //不休假加班的計薪型態
					
					boolean count_flag = true;  //計算不休假加班費flag
					String cur_date = this.CalendarToString(start_cal);  //當前計算不休假加班費的考勤日期
					Map tempMap = (Map) empattdata.get(cur_date);  //考勤資料Map
					
					//列印考勤資料Map
					//System.out.println("日期:"+cur_date+" 考勤資料Map:"+tempMap);
					
					//判斷是否取得考勤資料Map
					if( tempMap == null ){
						//若未取得結束此次迴圈
						//加一天
						start_cal.add(Calendar.DAY_OF_MONTH, 1);
						
						continue;
					}
					
					//判斷考勤是否有異常, 若異常還是給不休假加班費, 但是依據實際加班時數給不休假加班費
					//if(考勤異常狀態 != 未打卡 ) 才進行不休假加班費計算, 否則表示異常不給不休假加班費
					//判斷是否是假日, 若是假日才使用不休假加班計算
					boolean un_ha_flag = true;
					un_ha_flag = !this.isHoliday(conn, cur_date, tempMap, ehf030603m0.getEHF030603T0_07());
					
					if(un_ha_flag){	
						//假日才計算不休假加班費, 非假日不使用不休假加班費計算
						count_flag = false;
						
					//判斷是否有記錄中午打卡記錄
					}else if(tools.hasNoonRecord(classMap)){  
						if(	( (Boolean)tempMap.get("上班是否異常") && !(Boolean)tempMap.get("上班是否已修正") )
								|| ( (Boolean)tempMap.get("上午下班是否異常") && !(Boolean)tempMap.get("上午下班是否已修正") )
								|| ( (Boolean)tempMap.get("下午上班是否異常") && !(Boolean)tempMap.get("下午上班是否已修正") )
							    || ( (Boolean)tempMap.get("下班是否異常") && !(Boolean)tempMap.get("下班是否已修正") ) ){
							//判斷異常是否為 '未打卡'
							if( "".equals((String)tempMap.get("上班刷卡日期時間")) || "".equals((String)tempMap.get("上午下班刷卡日期時間")) 
								|| "".equals((String)tempMap.get("下午上班刷卡日期時間")) || "".equals((String)tempMap.get("下班刷卡日期時間")) ){
								//異常狀態 == '未打卡', 則不給不休假加班費
								count_flag = false;
							}
						}
					}else{
						
						//之後的加班上班與加班下班紀錄不在會有異常的狀況, 只需要判斷加班上班與加班下班不是未打卡資料即可進行加班費處理
						if( ( (Boolean)tempMap.get("上班是否異常") && !(Boolean)tempMap.get("上班是否已修正") )
							|| ( (Boolean)tempMap.get("下班是否異常") && !(Boolean)tempMap.get("下班是否已修正") ) ){
							
							//增加判斷加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄則只需要判斷下班資料與加班下班資料二擇一存在
							//即可判定是否需要計算不休假加班費
							
							
							//判斷異常是否為 '未打卡'
							if( "".equals((String)tempMap.get("上班刷卡日期時間")) 
								|| ( "".equals((String)tempMap.get("下班刷卡日期時間"))
									 && !( ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)tempMap.get("員工工號"), this.sta_comp_id ) 
										   && !"".equals((String)tempMap.get("加班下班刷卡日期時間")) ) ) ){
								//異常狀態 == '未打卡', 則不給不休假加班費
								count_flag = false;
							}
						}
					}
					
					//判斷是否執行不休假加班費計算
					if(count_flag){
						
						//處理不休假加班的程式
						//判斷是否有不休假加班
						if(true){//tools.StringToBoolean(ehf010103m0.getEHF010103T0_05())){
							//有設定不休假加班
							//判斷是否依正常上班日計薪
							if(true){//tools.StringToBoolean(ehf010103m0.getEHF010103T0_06())){
								//依正常上班日計薪
								type = 1;
								
								//計算正常上班區間的時數
								ha_overtime_hour = this.countHaOvertimeHour( conn, ehf030603m0, ehf030107m0, tempMap, classMap );
								
								//計算假日的津貼資料
								//ehf030601m0 = this.countHaAllowanceData(conn, empattdata, tempMap, classMap, allowancelist, day_pay);
								ehf030601m0 = null;
								//計算加班上班區間的時數與不休假加班金額(使用既有的加班計算)
								ehf030602m0 = this.countNoHaOvertimeData(conn, empattdata, tempMap, classMap, overtimelist, hour_pay);
								
								
							}else{
							
								//依不休假加班設定規則計薪
								type = 2;
								
								//計算正常上班區間的時數
								ha_overtime_hour = this.countHaOvertimeHour( conn, ehf030603m0, ehf030107m0, tempMap, classMap );
								
								//產生加班費表頭資料
								ehf030602m0 = this.generateOvertimeHeadData( (String)tempMap.get("員工工號"), (String)classMap.get("EHF010100T0_02"),
											  this.sta_salyymm, this.sta_costyymm, this.sta_user_name, this.sta_comp_id);
								//不休假加班限制暫時保留不用, 因為加班與不休假加班設定有繼承關係, 所以使用加班設定中的限制條件當做不休假加班的限制條件
								//計算加班時數
								ha_overtime_hour += this.countNoHaOvertimeHour(conn, ehf030602m0, tempMap, classMap, overtimelist);
								
							}		
						}else{
							//沒有不休假加班
							type = 0;
							//沒有不休假加班, 設定不休假加班時數 = 0
							ha_overtime_hour = 0;
							//沒有不休假加班, 沒有津貼資料(ehf030601m0)
							ehf030601m0 = null;
							////沒有不休假加班, 沒有加班資料(ehf030602m0)
							ehf030602m0 = null;
						}
						
						//計算不休假加班費金額,並產生不休假加班費細項資料
						//1.依正常日計算：津貼資料, 加班資料, 正常區間時數  計算不休假加班費用。
						//2.依規則計算：正常區間時數, 加班時數  計算不休假加班費用。
						ehf030603m0 = this.generateHaOvertimeData(conn, ehf030603m0, cur_date, tempMap,
								ehf030107m0, type, ha_overtime_hour, ehf030601m0, ehf030602m0, hour_pay, day_pay );
											
					}else{
						//寫入異常不給不休假加班費的資訊
						if(un_ha_flag){
							//System.out.println("計算不休假加班費資料:"+ehf030107m0.getEHF030107T0_02()+"/"+ehf030107m0.getEHF030107T0_03()+" "+
							//   "員工:"+ehf030603m0.getEHF030603T0_01()+", "+cur_date+" 不是假日, " +
							 //  "假日才計算不休假加班費, 非假日不使用不休假加班費計算!!" );
						}else{
							//System.out.println("計算不休假加班費資料:"+ehf030107m0.getEHF030107T0_02()+"/"+ehf030107m0.getEHF030107T0_03()+" "+
							//   "員工:"+ehf030603m0.getEHF030603T0_01()+", "+cur_date+" 不休假加班考勤資料異常, " +
							//   "不給不休假加班費, 請由人資手動進行修正!!" );
							
							if( !"".equals((String)tempMap.get("考勤日期")) && (String)tempMap.get("考勤日期") != null ){
								//產生不休假加班費異常資訊
								ehf030603m0 = this.generateAbnormalHaOvertimeData( ehf030603m0, cur_date, (float)0,
											  cur_date+" 不休假加班考勤資料異常, " +
											  ehf030107m0.getEHF030107T0_03()+" "+
											  "不發放不休假加班費, " +
										  	  "" );
							}
							
						}
					}
					
					start_cal.add(Calendar.DAY_OF_MONTH, 1);
				}

			}else{
				System.out.println("不休假加班資料:"+ehf030107m0.getEHF030107T0_02()+"/"+ehf030107m0.getEHF030107T0_03()+" 未啟用!!");
				msgMap.put("MAIN_MSG", "不休假加班資料:"+ehf030107m0.getEHF030107T0_02()+"/"+ehf030107m0.getEHF030107T0_03()+" 未啟用!!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ehf030603m0;
	}
	
	/**
	 * 計算不休假加班正常區間的時數(含扣除中午休息時間的相關判斷)
	 * @param attMap
	 * @param classMap
	 * @param limit_cond_flag
	 * @return
	 */
	protected float countHaOvertimeHour(Connection conn,EHF030603M0F ehf030603m0, EHF030107M0F ehf030107m0, Map attMap, Map classMap ){
		
		float hour = 0;
		
		try{
			
			Calendar class_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
											      (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 1);  //班別設定的上班時間(Calendar)
//			Calendar class_siesta_in_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
//														 (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
//			Calendar class_siesta_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
//														  (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
//			Calendar class_out_cal = ems_att_tools.getCalendarByClass(conn, (String) attMap.get("考勤日期"), 
//												   (String)attMap.get("員工工號"), this.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)		
//			Calendar class_ov_in_cal = this.getCalendarByClass( (String) attMap.get("考勤日期"), classMap, 5);  //班別設定的加班上班時間(Calendar)
			
			Calendar cur_in_cal = this.datetimeToCalendar((String) attMap.get("上班刷卡日期時間"));  //實際上班時間(Calendar)
			Calendar cur_siesta_in_cal = this.datetimeToCalendar((String) attMap.get("上午下班刷卡日期時間"));  //實際上午下班時間(Calendar)
			Calendar cur_siesta_out_cal = this.datetimeToCalendar((String) attMap.get("下午上班刷卡日期時間"));  //實際下午上班時間(Calendar)
			Calendar cur_out_cal = this.datetimeToCalendar((String) attMap.get("下班刷卡日期時間"));  //實際下班時間(Calendar)
			
			//取得假日加班刷卡紀錄
			Map GetBeforeOvertimeHour=this.getOvertimeHour(conn,class_in_cal, (String) attMap.get("考勤日期"), (String)attMap.get("員工工號"),this.sta_comp_id,">=");	
			
			if(GetBeforeOvertimeHour.size()==0 || cur_in_cal == null ){
				hour = 0;			
				return hour;
			}
			
			//總加班時數(含內扣)
			hour = Float.valueOf((String)GetBeforeOvertimeHour.get("hour"));
			
			//取得班別的提前加班內扣時數
			float deducted_hour = Float.parseFloat((String)GetBeforeOvertimeHour.get("deducted_hour"));
			hour = hour-deducted_hour;
			
			//依據加班單找加班記錄
			Map OvertimeFormRecording = this.getOvertimeFormRecording(conn, class_in_cal, (String)attMap.get("考勤日期"), (String)attMap.get("員工工號"), 
					this.sta_comp_id, ">=");
			
			Calendar class_ov_in_cal = this.datetimeToCalendar((String)OvertimeFormRecording.get("加班開始時間"));//加班單的加班開始時間
			Calendar class_ov_out_cal = this.datetimeToCalendar((String)OvertimeFormRecording.get("加班結束時間"));//加班單的加班結束時間
			Calendar class_siesta_ov_in_cal = this.datetimeToCalendar((String)OvertimeFormRecording.get("加班休息開始時間"));//加班單的休息開始時間
			Calendar class_siesta_ov_out_cal = this.datetimeToCalendar((String)OvertimeFormRecording.get("加班休息結束時間"));//加班單的休息結束時間
			
			Calendar range_class_ov_in_cal = (Calendar)class_ov_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_ov_in_cal.add(Calendar.SECOND, 59);
			
			Calendar range_class_siesta_ov_out_cal = (Calendar)class_siesta_ov_out_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_siesta_ov_out_cal.add(Calendar.SECOND, 59);
			
			float siesta_hour = tools.caculateDiffHour(class_siesta_ov_in_cal, class_siesta_ov_out_cal);
			
			if( siesta_hour < 0 ){
				siesta_hour = 0;
			}
			
			//判斷是否有記錄中午打卡記錄
			boolean noon_flag = tools.hasNoonRecord(classMap);
			
			//不休假加班設定的正常區間總時間(加班單上的時數)
			float total_hov_hour = 0;
			//實際發生的加班時數
			float c_hour = 0;
			
			//if(noon_flag){
				
			//}else{
				
				total_hov_hour = this.caculateDiffHourWithFIX( class_ov_in_cal, class_ov_out_cal) - siesta_hour;
				
				//沒有中午打卡記錄
				//判斷上班情況
				if( cur_in_cal.compareTo(range_class_ov_in_cal) <= 0 ){
					//上班 - 正常
					if(cur_out_cal.compareTo(class_ov_out_cal) >= 0 ){
						//下班 - 正常
						//計算 加班單上班時間 至 加班單下班時間  中間的時數 扣除休息時間
						c_hour = this.caculateDiffHourWithFIX( class_ov_in_cal, class_ov_out_cal) - siesta_hour;
					}else if( cur_out_cal.compareTo(class_ov_out_cal) < 0 ){
						//下班 - 異常
						//表示下班前就已下班
						//計算 加班單上班時間 至 實際下班時間  中間的時數 扣除休息時間
						c_hour = this.caculateDiffHourWithFIX( class_ov_in_cal, cur_out_cal)- siesta_hour;
						
					}
				}else if( cur_in_cal.compareTo(range_class_ov_in_cal) > 0 ){
					//上班 - 異常
					if( cur_out_cal.compareTo(class_ov_out_cal) >= 0 ){
						//下班 - 正常
						//表示下班前才上班
						//計算 實際上班時間 至 加班單下班時間  中間的時數 扣除休息時間
						c_hour = this.caculateDiffHourWithFIX( cur_in_cal, class_ov_out_cal)- siesta_hour;
					}else if( cur_out_cal.compareTo(class_ov_out_cal) < 0 ){
						//下班 - 異常
						//表示下班前就已下班
						//計算 實際上班時間 至 實際下班時間  中間的時數 扣除休息時間
						c_hour = this.caculateDiffHourWithFIX( cur_in_cal, cur_out_cal)- siesta_hour;
					}
				}
				
			//}
			/*
			float siesta_hour = tools.caculateDiffHour(class_siesta_in_cal, class_siesta_out_cal);
			
			if( siesta_hour < 0 ){
				siesta_hour = 0;
			}
			
			//增加判斷加班設定中的 '是否記錄下班與加班上班',  若是設定不記錄且存在加班下班的刷卡資料即可判斷有加班, 則以班別下班時間做為下班刷卡時間
			//若否則以正常方式計算
			boolean chk_flag = false;
			if(cur_out_cal == null){
				chk_flag = ems_att_tools.chkOVTRecordProcess(conn, classMap, (String)attMap.get("員工工號"), ehf030603m0.getEHF030603T0_07() );
				//如果有設定不記錄下班與加班上班, 則依據班別設定中的下班時間直接設定為下班刷卡時間
				if(chk_flag){
					cur_out_cal = (Calendar) class_out_cal.clone();
				}
			}
			
			Calendar range_class_in_cal = (Calendar)class_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_in_cal.add(Calendar.SECOND, 59); 
			
			Calendar range_class_siesta_out_cal = (Calendar)class_siesta_out_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_siesta_out_cal.add(Calendar.SECOND, 59);
			
			//不休假加班設定的正常區間總時間
			float total_hov_hour = 0; 
			
			//判斷是否有記錄中午打卡記錄
			//boolean noon_flag = tools.hasNoonRecord(classMap);
			if(noon_flag){
				//有中午打卡記錄
				float hour1 = 0;
				float hour2 = 0;
				total_hov_hour = this.caculateDiffHourWithFIX( class_in_cal, class_siesta_in_cal) 
								 + this.caculateDiffHourWithFIX( class_siesta_out_cal, class_out_cal);
				
				//計算上午的區間時數 hour1
				if( cur_in_cal.compareTo(range_class_in_cal) <= 0 ){
					//上班 - 正常
					if( cur_siesta_in_cal.compareTo(class_siesta_in_cal) >= 0 ){
						//上午下班 - 正常
						//計算 班別上班時間 至 班別上午下班時間  中間的時數
						hour1 = this.caculateDiffHourWithFIX( class_in_cal, class_siesta_in_cal);
						
					}else if( cur_siesta_in_cal.compareTo(class_siesta_in_cal) < 0 ){
						//上午下班 - 異常
						//計算 班別上班時間 至 實際上午下班時間  中間的時數
						hour1 = this.caculateDiffHourWithFIX( class_in_cal, cur_siesta_in_cal);

					}
				}else if( cur_in_cal.compareTo(range_class_in_cal) > 0 ){
					//上班 - 異常
					if( cur_siesta_in_cal.compareTo(class_siesta_in_cal) >= 0 ){
						//上午下班 - 正常
						//計算 實際上班時間 至 班別上午下班時間  中間的時數
						hour1 = this.caculateDiffHourWithFIX( cur_in_cal, class_siesta_in_cal);
						
					}else if( cur_siesta_in_cal.compareTo(class_siesta_in_cal) < 0 ){
						//上午下班 - 異常
						//計算 實際上班時間 至 實際上午下班時間  中間的時數
						hour1 = this.caculateDiffHourWithFIX( cur_in_cal, cur_siesta_in_cal);

					}
				}
				
				//計算下午的區間時數 hour2
				if( cur_siesta_out_cal.compareTo(range_class_siesta_out_cal) <= 0 ){
					//下午上班 - 正常
					if( cur_out_cal.compareTo(class_out_cal) >= 0 ){
						//下班 - 正常
						//計算 班別下午上班時間 至 班別下班時間  中間的時數
						hour2 = this.caculateDiffHourWithFIX( class_siesta_out_cal, class_out_cal);
						
					}else if( cur_out_cal.compareTo(class_out_cal) < 0 ){
						//下班 - 異常
						//計算 班別下午上班時間 至 實際下班時間  中間的時數
						hour2 = this.caculateDiffHourWithFIX( class_siesta_out_cal, cur_out_cal);

					}
				}else if( cur_siesta_out_cal.compareTo(range_class_siesta_out_cal) > 0 ){
					//下午上班 - 異常
					if( cur_out_cal.compareTo(class_out_cal) >= 0 ){
						//下班 - 正常
						//計算 實際下午上班時間 至 班別下班時間  中間的時數
						hour2 = this.caculateDiffHourWithFIX( cur_siesta_out_cal, class_out_cal);
						
					}else if( cur_out_cal.compareTo(class_out_cal) < 0 ){
						//下班 - 異常
						//計算 實際下午上班時間 至 實際下班時間  中間的時數
						hour2 = this.caculateDiffHourWithFIX( cur_siesta_out_cal, cur_out_cal);

					}
				}
				
				//實際時數 = 上午區間時數 + 下午區間時數
				hour = hour1 + hour2;
				
			}else{
				
				total_hov_hour = this.caculateDiffHourWithFIX( class_in_cal, class_out_cal) - siesta_hour;
				
				//沒有中午打卡記錄
				//判斷上班情況
				if( cur_in_cal.compareTo(range_class_in_cal) <= 0 ){
					//上班 - 正常
					if(cur_out_cal.compareTo(class_out_cal) >= 0 ){
						//下班 - 正常
						//計算 班別上班時間 至 班別下班時間  中間的時數 扣除中午休息時間
						hour = this.caculateDiffHourWithFIX( class_in_cal, class_out_cal) - siesta_hour;
						
					}else if( cur_out_cal.compareTo(class_out_cal) < 0 ){
						//下班 - 異常
						if( cur_out_cal.compareTo(class_siesta_in_cal) <= 0 ){
							//表示中午休息前就已下班
							//計算 班別上班時間 至 實際下班時間 中間的時數 不用扣除中午休息時間
							hour = this.caculateDiffHourWithFIX( class_in_cal, cur_out_cal);
						}else if( cur_out_cal.compareTo(range_class_siesta_out_cal) <= 0 ){
							//表示下午上班前就已下班
							//計算 班別上班時間 至 班別中午下班時間 中間的時數 不用扣除中午休息時間
							hour = this.caculateDiffHourWithFIX( class_in_cal, class_siesta_in_cal);
						}else if( cur_out_cal.compareTo(range_class_siesta_out_cal) > 0 ){
							//表示下班前就已下班
							//計算 班別上班時間 至 實際下班時間  中間的時數 扣除中午休息時間
							hour = this.caculateDiffHourWithFIX( class_in_cal, cur_out_cal)- siesta_hour;
						}
					}
				}else if( cur_in_cal.compareTo(range_class_in_cal) > 0 ){
					//上班 - 異常
					if( cur_out_cal.compareTo(class_out_cal) >= 0 ){
						//下班 - 正常
						if( cur_in_cal.compareTo(class_siesta_in_cal) <= 0 ){
							//表示中午休息前才上班
							//計算 實際上班時間 至 班別下班時間 中間的時數 扣除中午休息時間
							hour = this.caculateDiffHourWithFIX( cur_in_cal, class_out_cal) - siesta_hour;
						}else if( cur_in_cal.compareTo(range_class_siesta_out_cal) <= 0 ){
							//表示下午上班前才上班
							//計算 下午上班時間 至 班別下班時間 中間的時數 不用扣除中午休息時間
							hour = this.caculateDiffHourWithFIX( class_siesta_out_cal, class_out_cal);
						}else if( cur_in_cal.compareTo(range_class_siesta_out_cal) > 0 ){
							//表示下班前才上班
							//計算 實際上班時間 至 班別下班時間  中間的時數 不用扣除中午休息時間
							hour = this.caculateDiffHourWithFIX( cur_in_cal, class_out_cal);
						}
					}else if( cur_out_cal.compareTo(class_out_cal) < 0 ){
						//下班 - 異常
						if( cur_in_cal.compareTo(class_siesta_in_cal) <= 0 ){
							//表示中午休息前才上班
							if( cur_out_cal.compareTo(class_siesta_in_cal) <= 0 ){
								//表示中午休息前就已下班
								//計算 實際上班時間 至 實際下班時間 中間的時數 不用扣除中午休息時間
								hour = this.caculateDiffHourWithFIX( cur_in_cal, cur_out_cal);
							}else if( cur_out_cal.compareTo(range_class_siesta_out_cal) <= 0 ){
								//表示下午上班前就已下班
								//計算 實際上班時間 至 班別中午下班時間 中間的時數 不用扣除中午休息時間
								hour = this.caculateDiffHourWithFIX( cur_in_cal, class_siesta_in_cal);
							}else if( cur_out_cal.compareTo(range_class_siesta_out_cal) > 0 ){
								//表示下班前就已下班
								//計算 實際上班時間 至 實際下班時間  中間的時數 扣除中午休息時間
								hour = this.caculateDiffHourWithFIX( cur_in_cal, cur_out_cal)- siesta_hour;
							}
						}else if( cur_in_cal.compareTo(range_class_siesta_out_cal) <= 0 ){
							//表示下午上班前才上班
							if( cur_out_cal.compareTo(range_class_siesta_out_cal) <= 0 ){
								//表示下午上班前就已下班
								//上班時間 = 0
								hour = 0;
							}else if( cur_out_cal.compareTo(range_class_siesta_out_cal) > 0 ){
								//表示下班前就已下班
								//計算 班別下午上班時間 至 實際下班時間  中間的時數 不用扣除中午休息時間
								hour = this.caculateDiffHourWithFIX( class_siesta_out_cal, cur_out_cal);
							}
						}else if( cur_in_cal.compareTo(range_class_siesta_out_cal) > 0 ){
							//表示下班前才上班
							if( cur_out_cal.compareTo(range_class_siesta_out_cal) > 0 ){
								//表示下班前就已下班
								//計算 實際上班時間 至 實際下班時間  中間的時數 不用扣除中午休息時間
								hour = this.caculateDiffHourWithFIX( cur_in_cal, cur_out_cal);
							}
						}
					}
				}
			}
			*/
			//時數不可為負的, 若為負的, 時數 = 0
			if( hour < 0 ){
				hour = 0;
			}
			
			//處理異常資訊的寫入
			float ab_hour = total_hov_hour - c_hour;
			if( ab_hour > 0 && hour > 0 ){
				//有異常, 若 hour = 0 表示沒有不休假加班, 不需記錄異常
				//進行異常資訊處理
				//產生不休假加班費異常資訊
				if(noon_flag){
					ehf030603m0 = this.generateAbnormalHaOvertimeData( ehf030603m0, (String) attMap.get("考勤日期"), ab_hour,
							  	  (String) attMap.get("考勤日期")+" " +
							  	  "考勤資料異常:上午("+(String)attMap.get("上班刷卡日期時間")+"~"+(String) attMap.get("上午下班刷卡日期時間")+") " +
							  	  "下午("+(String) attMap.get("下午上班刷卡日期時間")+"~"+(String) attMap.get("下班刷卡日期時間")+"), " +
							  	ehf030107m0.getEHF030107T0_03()+" "+
							  	  "扣除異常時數:"+ab_hour+"小時, 發放"+hour+"小時不休假加班費, " +
							  	  "" );
				}else{
					ehf030603m0 = this.generateAbnormalHaOvertimeData( ehf030603m0, (String) attMap.get("考勤日期"), ab_hour,
						  	  (String) attMap.get("考勤日期")+" " +
						  	  "考勤資料異常:"+(String)attMap.get("上班刷卡日期時間")+"~"+(String) attMap.get("下班刷卡日期時間")+"), " +
						  	ehf030107m0.getEHF030107T0_03()+" "+
						  	  "扣除異常時數:"+ab_hour+"小時, 發放"+hour+"小時不休假加班費, " +
						  	  "" );
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return hour;
	}
	
	/**
	 * 計算不休假加班的津貼資料
	 * @param conn
	 * @param start_date
	 * @param end_date
	 * @param attMap
	 * @param classMap
	 * @return
	 *//*
	protected EHF030601M0F countHaAllowanceData( Connection conn, Map empattdata, Map attMap, Map classMap, List allowancelist, int day_pay ){
		
		EHF030601M0F ehf030601m0 = null;
		
		try{			
			if( allowancelist.size() > 0 ){
			
				//班別需計算的津貼清單
				Iterator it = allowancelist.iterator();
				
				//產生津貼表頭資料
				ehf030601m0 = this.generateAllowanceHeadData( (String)attMap.get("員工工號"), (String)classMap.get("EHF010100T0_02"),
									   	   this.sta_salyymm, this.sta_costyymm, this.sta_user_name, this.sta_comp_id);
			
				while(it.hasNext()){
				
					EHF010101M0F ehf010101m0 = (EHF010101M0F) it.next();
					
					//計算津貼資料
					ehf030601m0 = this.countAllowanceData(conn, classMap, (String) attMap.get("考勤日期"), (String) attMap.get("考勤日期"),
														  ehf030601m0, ehf010101m0, empattdata, false,
														  day_pay );
					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ehf030601m0;
	}
	
	*//**
	 * 計算不休假加班的加班費資料
	 * @param conn
	 * @param cur_date
	 * @param attMap
	 * @param classMap
	 * @return
	 */
	protected EHF030602M0F countNoHaOvertimeData( Connection conn, Map empattdata, Map attMap, Map classMap, List overtimelist, int hour_pay ){
		
		EHF030602M0F ehf030602m0 = null;
		
		try{
			
			//取得班別設定的加班詳細資料
			//List overtimelist = this.getClassOvertimeData(conn, classMap.get("WORK_CLASS_NO"), this.sta_comp_id);
			
			//有取到加班設定資料才計算
			if( overtimelist.size() > 0 ){
				//班別需計算的加班清單
				Iterator it = overtimelist.iterator();
				
				//產生加班費表頭資料
				ehf030602m0 = this.generateOvertimeHeadData( (String)attMap.get("員工工號"), (String)classMap.get("EHF010100T0_02"),
							  this.sta_salyymm, this.sta_costyymm, this.sta_user_name, this.sta_comp_id);
			
				while(it.hasNext()){
					
					EHF030107M0F ehf030107m0 = (EHF030107M0F) it.next();
					
					//計算加班費資料
					ehf030602m0 = this.countOvertimeData(conn, classMap, (String) attMap.get("考勤日期"), (String) attMap.get("考勤日期"),
														 ehf030602m0, ehf030107m0, empattdata, hour_pay, false );
					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ehf030602m0;
	}
	
	/**
	 * 計算不休假加班的加班時數
	 * @param conn
	 * @param attMap
	 * @param classMap
	 * @return
	 */
	protected float countNoHaOvertimeHour( Connection conn, EHF030602M0F ehf030602m0, Map attMap, Map classMap, List overtimelist ){
		
		//BA_TOOLS tools = BA_TOOLS.getInstance();
		Map overtime_map = null;
		float overtime_hour = 0;
		
		try{
			
			//取得班別設定的加班詳細資料
//			List overtimelist = this.getClassOvertimeData(conn, (Integer)classMap.get("WORK_CLASS_NO"), this.sta_comp_id);
			
			//有取到加班設定資料才計算
			if( overtimelist.size() > 0 ){
				//班別需計算的加班清單
				Iterator it = overtimelist.iterator();
				
				while(it.hasNext()){
					
					EHF030107M0F ehf030107m0 = (EHF030107M0F) it.next();
					//計算加班時數
					overtime_map = this.countOvertimeHour(conn, ehf030602m0, ehf030107m0, attMap, classMap, 
														  false );
				}
				
				//設定加班時數
				overtime_hour = (Float)overtime_map.get("HOUR");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return overtime_hour;
	}
	
	/**
	 * 判斷與計算 不休假加班費用
	 * @param conn
	 * @param ehf030603m0
	 * @param cur_date
	 * @param ha_overtime_start
	 * @param ha_overtime_end
	 * @param ehf010103m0
	 * @param type
	 * @param ha_overtime_hour
	 * @param ehf030601m0
	 * @param ehf030602m0
	 * @return
	 */
	protected EHF030603M0F generateHaOvertimeData( Connection conn, EHF030603M0F ehf030603m0, String cur_date, Map attMap,
			EHF030107M0F ehf030107m0, int type, float ha_overtime_hour, EHF030601M0F ehf030601m0, EHF030602M0F ehf030602m0,
		    int hour_pay, int day_pay ){
		
		int ha_ov_cost = 0;
		int normol_csot = 0;
		String ha_ov_cost_str = "";
		String overtime_type = "";
		float law_ha_overtime_hour = 0;//休息日加班認定時數
		
		try{
			//取得每天的工作時數
			float work_hours = Float.parseFloat(tools.getSysParam(conn, ehf030603m0.getEHF030603T0_07(), "WORK_HOURS"));
			//取得不休假加班的計算資料型態
			String ha_ov_type = tools.getSysParam(conn, ehf030603m0.getEHF030603T0_07(), "HALIDAY_OVERTIME_TYPE");
			
			//判斷不休假加班的計算資料型態
			if( type == 1 ){
				if("INDEPENDENT".equals(ha_ov_type)){
					//不休假加班計算資料獨立產生
					type = 1;
				}else if("MERGE".equals(ha_ov_type)){
					//不休假加班計算資料合併至津貼,加班費,本薪金額
					type = 3;
				}else{
					//預設為獨立產生
					type = 1;
				}
			}
//====================================一例一休 BY maybe 20170417=================================================
			// 產生不休假加班的時間區間
			String ha_overtime_start = (String) attMap.get("上班刷卡日期時間");
			String ha_overtime_end = "";
			if (!"".equals((String) attMap.get("加班下班刷卡日期時間"))&& (String) attMap.get("加班下班刷卡日期時間") != null) {
				ha_overtime_end = (String) attMap.get("加班下班刷卡日期時間");
			} else {
				ha_overtime_end = (String) attMap.get("下班刷卡日期時間");
			}
			
			//取得加班日期中加班單的加班類別
			overtime_type = this.getOvertimeFormType(conn, cur_date, (String)attMap.get("員工工號"), ehf030603m0.getEHF030603T0_07());
			
			if("00".equals(overtime_type)){
				//依照平日(星期一~五)加班費計算，但是依照程式邏輯，平日加班費計算不在此，所以不做動作
				//依照舊勞基法
				//計算正常區間金額
				/*normol_csot = 0;
				// 判斷是否正常區間時數為整天的時數, 表示未遲到,早退或請假
				if (ha_overtime_hour == work_hours) {
					// 整天直接給日薪
					normol_csot = day_pay;
				} else {
					// 非整天, 用時薪計算當天薪資
					normol_csot = Math.round(ha_overtime_hour * hour_pay);
				}
				// 產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float) 1, ha_overtime_hour, "99");
				
				Map additionalMap = this.countHaAdditionalMoney(ehf030603m0,cur_date, ehf030107m0, normol_csot, ha_overtime_hour, overtime_type);
				ha_ov_cost += (Integer) additionalMap.get("HA_ADDITIONAL_MONEY");
				ha_ov_cost_str += (String) additionalMap.get("HA_ADDITIONAL_STR");
				
				// 產生不休假加班費細項資料
				ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end, ha_overtime_hour, ha_ov_cost, ha_ov_cost_str, "", "依正常日計薪");
				*/
			}else if("01".equals(overtime_type)){
				//例假日，直接給日薪，外加補休一天
				normol_csot = day_pay;
				ha_ov_cost += normol_csot;				
				ha_ov_cost_str += "正常區間時數："+ha_overtime_hour+"小時, 正常區間金額："+normol_csot+"元, ";
				
				// 產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float) 1, ha_overtime_hour, "99");
				
				Map additionalMap = this.countHaAdditionalMoney(ehf030603m0, cur_date, ehf030107m0, normol_csot, ha_overtime_hour, overtime_type);
				
				ha_ov_cost += (Integer) additionalMap.get("HA_ADDITIONAL_MONEY");
				ha_ov_cost_str += (String) additionalMap.get("HA_ADDITIONAL_STR");
				
				// 產生不休假加班費細項資料
				ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end, ha_overtime_hour, ha_ov_cost, ha_ov_cost_str, "", "依正常日計薪");
				/*
				String chiyear = tools.getDateADYear(cur_date, false);
				
				EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
				//檢查有無個人補休
				boolean chkflag = setVaSql.chkVacationData(conn, chiyear, (String)attMap.get("員工工號"), ehf030603m0.getEHF030603T0_07());
				
				//處理員工休假資訊回寫
				if(chkflag){
					
					setVaSql.handleOverTimeVacation(conn, chiyear, (String)attMap.get("員工工號"), "A13", false, 
							 cur_date, cur_date, Float.parseFloat("8"), ehf030603m0.getEHF030603T0_07());
				
				//新增員工休假資訊，補休一天
				}else{
					
					String use_start_date = tools.getDateADYear(cur_date, true)+"/01/01";
					String use_end_date = "9999/12/31";
					
					AuthorizedBean user_authbean = new AuthorizedBean();
					user_authbean.setUserName(this.sta_user_name);					
					user_authbean.setEmployeeID((String)attMap.get("員工工號"));
					user_authbean.setCompId(this.sta_comp_id);
					
					setVaSql.addEHF010300T0(conn, chiyear, (String)attMap.get("員工工號"), "A13", use_start_date, use_end_date, Float.parseFloat("8"), user_authbean);
					
				}
				*/
			}else if("02".equals(overtime_type)){
				//休息日，帶時薪去計算				
				//先確定加班時數，以4小時為一個基本的認定範圍
				if(ha_overtime_hour>0 && ha_overtime_hour<=4){
					law_ha_overtime_hour = 4;
				}else if(ha_overtime_hour>4 && ha_overtime_hour<=8){
					law_ha_overtime_hour = 8;
				}else if(ha_overtime_hour>8 && ha_overtime_hour<=12){
					law_ha_overtime_hour = 12;
				}
				
				String overtime_pay_mode = tools.getSysParam(conn, ehf030602m0.getEHF030602T0_07(), "OVERTIME_PAY_MODE");
				//準備加班的相關參數
				float before_rate_one = Float.parseFloat(ehf030107m0.getEHF030107T0_05());//1.33				
				float before_rate_two = Float.parseFloat(ehf030107m0.getEHF030107T0_07());//1.66
				
				switch((int)law_ha_overtime_hour){
				
				case 4:					
					//前兩個小時每小時的加班費乘以1/3，第三個小時~第八個小時每小時的加班費乘以2/3
					normol_csot = tools.fixNumByMode((
					  	  	  	  tools.fixNumByMode((hour_pay * 2 * before_rate_one), overtime_pay_mode) +
					  	  	  	  tools.fixNumByMode((hour_pay * (law_ha_overtime_hour-2) * before_rate_two ), overtime_pay_mode)
						  	      ), overtime_pay_mode);
					
					ha_ov_cost += normol_csot;
					//ha_ov_cost_str += "正常區間時數："+law_ha_overtime_hour+"小時, 正常區間金額："+normol_csot+"元, ";
					ha_ov_cost_str = " 四捨五入( ("+hour_pay+"X"+2+"X"+before_rate_one+") + " +
					" ("+hour_pay+"X("+law_ha_overtime_hour+" - "+2+")X"+before_rate_two+"), ";
					
					//產生不休假計算明細資料
					ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float)0, law_ha_overtime_hour, "99");
					
					// 產生不休假加班費細項資料
					ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end, ha_overtime_hour, ha_ov_cost, ha_ov_cost_str, "", "依正常日計薪");
					
					break;
					
				case 8:	
					//前兩個小時每小時的加班費乘以1/3，第三個小時~第八個小時每小時的加班費乘以2/3
					normol_csot = tools.fixNumByMode((
						  	  	  tools.fixNumByMode((hour_pay * 2 * before_rate_one), overtime_pay_mode) +
						  	  	  tools.fixNumByMode((hour_pay * (law_ha_overtime_hour-2) * before_rate_two ), overtime_pay_mode)
							  	  ), overtime_pay_mode);
					
					ha_ov_cost += normol_csot;
					//ha_ov_cost_str += "正常區間時數："+law_ha_overtime_hour+"小時, 正常區間金額："+normol_csot+"元, ";
					ha_ov_cost_str = " 四捨五入( ("+hour_pay+"X"+2+"X"+before_rate_one+") + " +
					" ("+hour_pay+"X("+law_ha_overtime_hour+" - "+2+")X"+before_rate_two+"), ";
					
					//產生不休假計算明細資料
					ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float)0, law_ha_overtime_hour, "99");
					
					// 產生不休假加班費細項資料
					ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end, ha_overtime_hour, ha_ov_cost, ha_ov_cost_str, "", "依正常日計薪");
					
					break;
					
				case 12:
					
					normol_csot = tools.fixNumByMode((
				  	  	  	  	  tools.fixNumByMode((hour_pay * 2 * before_rate_one), overtime_pay_mode) +
				  	  	  	  	  tools.fixNumByMode((hour_pay * (law_ha_overtime_hour-2) * before_rate_two ), overtime_pay_mode)
					  	  	  	  ), overtime_pay_mode);
			
					ha_ov_cost += normol_csot;
					//ha_ov_cost_str += "正常區間時數："+law_ha_overtime_hour+"小時, 正常區間金額："+normol_csot+"元, ";
					ha_ov_cost_str = " 四捨五入( ("+hour_pay+"X"+2+"X"+before_rate_one+") + " +
					" ("+hour_pay+"X("+law_ha_overtime_hour+" - "+2+")X"+before_rate_two+"), ";
					
					//產生不休假計算明細資料
					ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float)0, law_ha_overtime_hour, "99");
				
					//9-12小時除了原來的加班費之外，要再加給4小時的本薪
					Map additionalMap = this.countHaAdditionalMoney(ehf030603m0, cur_date, ehf030107m0, hour_pay, law_ha_overtime_hour, overtime_type);
					
					ha_ov_cost += (Integer) additionalMap.get("HA_ADDITIONAL_MONEY");
					ha_ov_cost_str += (String) additionalMap.get("HA_ADDITIONAL_STR");
					
					// 產生不休假加班費細項資料
					ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end, ha_overtime_hour, ha_ov_cost, ha_ov_cost_str, "", "依正常日計薪");
					
					break;
				
				}
				
			}else if("03".equals(overtime_type)){
				//國定， 直接給日薪
				normol_csot = day_pay;
				ha_ov_cost += normol_csot;
				ha_ov_cost_str += "正常區間時數："+ha_overtime_hour+"小時, 正常區間金額："+normol_csot+"元, ";
				
				// 產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float) 1, ha_overtime_hour, "99");
				
				Map additionalMap = this.countHaAdditionalMoney(ehf030603m0, cur_date, ehf030107m0, normol_csot, ha_overtime_hour, overtime_type);
				
				ha_ov_cost += (Integer) additionalMap.get("HA_ADDITIONAL_MONEY");
				ha_ov_cost_str += (String) additionalMap.get("HA_ADDITIONAL_STR");
				
				// 產生不休假加班費細項資料
				ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end, ha_overtime_hour, ha_ov_cost, ha_ov_cost_str, "", "依正常日計薪");
				
			}			
//====================================一例一休 BY maybe 20170417=================================================			
//====================================產品改寫 BY 賴泓錡  20131202=================================================
			// 產生不休假加班的時間區間
			/*String ha_overtime_start = (String) attMap.get("上班刷卡日期時間");
			String ha_overtime_end = "";
			if (!"".equals((String) attMap.get("加班下班刷卡日期時間"))&& (String) attMap.get("加班下班刷卡日期時間") != null) {
				ha_overtime_end = (String) attMap.get("加班下班刷卡日期時間");
			} else {
				ha_overtime_end = (String) attMap.get("下班刷卡日期時間");
			}
						
			// 計算正常區間金額
			normol_csot = 0;
			// 判斷是否正常區間時數為整天的時數, 表示未遲到,早退或請假
			if (ha_overtime_hour == work_hours) {
				// 整天直接給日薪
				normol_csot = day_pay;
			} else {
				// 非整天, 用時薪計算當天薪資
				normol_csot = Math.round(ha_overtime_hour * hour_pay);
			}
			// 產生不休假計算明細資料
			ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float) 1, ha_overtime_hour, "99");
			
			Map additionalMap = this.countHaAdditionalMoney(ehf030603m0,cur_date, ehf030107m0, normol_csot);
			ha_ov_cost += (Integer) additionalMap.get("HA_ADDITIONAL_MONEY");
			ha_ov_cost_str += (String) additionalMap.get("HA_ADDITIONAL_STR");
			
			// 產生不休假加班費細項資料
			ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end, ha_overtime_hour, ha_ov_cost, ha_ov_cost_str, "", "依正常日計薪");*/
//====================================產品改寫 BY 賴泓錡  20131202=================================================
			/*
			//產生不休假加班的時間區間
			String ha_overtime_start = (String)attMap.get("上班刷卡日期時間");
			String ha_overtime_end = "";
			if(!"".equals((String)attMap.get("加班下班刷卡日期時間")) && (String)attMap.get("加班下班刷卡日期時間") != null){
				ha_overtime_end = (String)attMap.get("加班下班刷卡日期時間");
			}else{
				ha_overtime_end = (String)attMap.get("下班刷卡日期時間");
			}
			
			//---------------------------
			
			switch(type){
			
			case 1:
				
				//計算津貼的金額
				ha_ov_cost = this.countHaAllowanceMoney( ehf030603m0, ehf030601m0);
				ha_ov_cost_str += "津貼總金額："+ha_ov_cost+"元, ";
				
				//計算加班費金額
				Map overtimeMap = this.countHaOvertimeMoney( ehf030603m0, ehf030602m0);
				ha_ov_cost += (Integer)overtimeMap.get("OVERTIME_MONEY");
				ha_ov_cost_str += "加班區間時數："+(Float)overtimeMap.get("OVERTIME_HOUR")+"小時, " +
								  "加班區間金額："+(Integer)overtimeMap.get("OVERTIME_MONEY")+"元, ";
				
				//計算正常區間金額
				normol_csot = 0;
				//判斷是否正常區間時數為整天的時數, 表示未遲到,早退或請假
				if(ha_overtime_hour == work_hours){
					//整天直接給日薪
					normol_csot = day_pay;
				}else{
					//非整天, 用時薪計算當天薪資
					normol_csot = Math.round(ha_overtime_hour * hour_pay);
				}
				ha_ov_cost += normol_csot;
				ha_ov_cost_str += "正常區間時數："+ha_overtime_hour+"小時, 正常區間金額："+normol_csot+"元, ";
				
				//產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float)1, ha_overtime_hour, "99");
				
				//判斷是否有額外給薪金額
				if(tools.StringToBoolean(ehf010103m0.getEHF010103T0_17())){
//					int day_pay = this.getEmpDayPay(conn, ehf030603m0.getEHF030603T0_01(), ehf030603m0.getEHF030603T0_07());
					Map additionalMap = this.countHaAdditionalMoney(ehf030603m0, cur_date, ehf010103m0, day_pay);
					ha_ov_cost += (Integer)additionalMap.get("HA_ADDITIONAL_MONEY");
					ha_ov_cost_str += (String)additionalMap.get("HA_ADDITIONAL_STR");
				}
				
				//產生不休假加班費細項資料
				ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end,
							  ha_overtime_hour+((Float)overtimeMap.get("OVERTIME_HOUR")==null?0:(Float)overtimeMap.get("OVERTIME_HOUR")),
							  ha_ov_cost, ha_ov_cost_str, (String)overtimeMap.get("OVERTIME_MONEY_STR"), "依正常日計薪");
				
				break;
			
			case 2:
			
				float hour_one = Float.parseFloat(ehf010103m0.getEHF010103T0_07());
				float rate_one = Float.parseFloat(ehf010103m0.getEHF010103T0_08());
				float hour_two = Float.parseFloat(ehf010103m0.getEHF010103T0_09());
				float rate_two = Float.parseFloat(ehf010103m0.getEHF010103T0_10());
				float hour_three = Float.parseFloat(ehf010103m0.getEHF010103T0_11());
				float rate_three = Float.parseFloat(ehf010103m0.getEHF010103T0_12());
				
				//計算不休假加班的加班費用
				if( ha_overtime_hour > 0 ){
					//不休假加班時數大於0, 才計算不休假加班的不休假加班費用
					if(ha_overtime_hour > hour_three){
						//大於不休假加班時數三
						ha_ov_cost = Math.round((hour_pay * hour_one * rate_one) +
						(hour_pay * (hour_three - hour_two) * rate_two ) +
						(hour_pay * (ha_overtime_hour - hour_three) * rate_three ));
						
						ha_ov_cost_str = " 四捨五入( ("+hour_pay+"X"+hour_one+"X"+rate_one+") + " +
						" ("+hour_pay+"X("+hour_three+" - "+hour_two+")X"+rate_two+") + " +
						" ("+hour_pay+"X("+ha_overtime_hour+" - "+hour_three+")X"+rate_three+") ),  ";
						
						//產生不休假計算明細資料
						ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, rate_one, hour_one, "99");
						ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, rate_two, (hour_three - hour_two), "99");
						ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, rate_three, (ha_overtime_hour - hour_three), "99");
						
					}else if( ha_overtime_hour >  hour_two){
						//大於不休假加班時數二
						ha_ov_cost = Math.round((hour_pay * hour_one * rate_one) +
						(hour_pay * (ha_overtime_hour - hour_two) * rate_two ));
						
						ha_ov_cost_str = " 四捨五入( ("+hour_pay+"X"+hour_one+"X"+rate_one+") + " +
						" ("+hour_pay+"X("+ha_overtime_hour+" - "+hour_two+")X"+rate_two+") ),  ";
						
						//產生不休假計算明細資料
						ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, rate_one, hour_one, "99");
						ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, rate_two, (ha_overtime_hour - hour_two), "99");
						
					}else if( ha_overtime_hour <= hour_one ){
						//大於不休假加班時數一
						ha_ov_cost = Math.round((hour_pay * ha_overtime_hour * rate_one));
						
						ha_ov_cost_str = " 四捨五入( ("+hour_pay+"X"+ha_overtime_hour+"X"+rate_one+") ),  ";
						
						//產生不休假計算明細資料
						ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, rate_one, ha_overtime_hour, "99");
						
					}else{
						ha_ov_cost = 0;	
					}
					
					//判斷是否有額外給薪金額
					if(tools.StringToBoolean(ehf010103m0.getEHF010103T0_17())){
//						int day_pay = this.getEmpDayPay(conn, ehf030603m0.getEHF030603T0_01(), ehf030603m0.getEHF030603T0_07());
						Map additionalMap = this.countHaAdditionalMoney(ehf030603m0, cur_date, ehf010103m0, day_pay);
						ha_ov_cost += (Integer)additionalMap.get("HA_ADDITIONAL_MONEY");
						ha_ov_cost_str += (String)additionalMap.get("HA_ADDITIONAL_STR");
					}
				
					//產生不休假加班費細項資料
					ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end,
								  ha_overtime_hour, ha_ov_cost, ha_ov_cost_str, "", "依不休假加班設定規則計薪");
				}
				
				break;
				
			case 3:
				
				//產生津貼資料
				this.addAllowanceForHaOvertime(conn, ehf030601m0, this.sta_user_name, this.sta_comp_id);

				//產生加班費資料
				this.addOvertimeForHaOvertime(conn, ehf030602m0, this.sta_user_name, this.sta_comp_id);

				//計算正常區間金額
				normol_csot = 0;
				//判斷是否正常區間時數為整天的時數, 表示未遲到,早退或請假
				if(ha_overtime_hour == work_hours){
					//整天直接給日薪
					normol_csot = day_pay;
				}else{
					//非整天, 用時薪計算當天薪資
					normol_csot = Math.round(ha_overtime_hour * hour_pay);
				}
				ha_ov_cost += normol_csot;
				ha_ov_cost_str += "正常區間時數："+ha_overtime_hour+"小時, 正常區間金額："+normol_csot+"元, ";
				
				//產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, hour_pay, (float)1, ha_overtime_hour, "99");
				
				//判斷是否有額外給薪金額
				if(tools.StringToBoolean(ehf010103m0.getEHF010103T0_17())){
//					int day_pay = this.getEmpDayPay(conn, ehf030603m0.getEHF030603T0_01(), ehf030603m0.getEHF030603T0_07());
					Map additionalMap = this.countHaAdditionalMoney(ehf030603m0, cur_date, ehf010103m0, day_pay);
					ha_ov_cost += (Integer)additionalMap.get("HA_ADDITIONAL_MONEY");
					ha_ov_cost_str += (String)additionalMap.get("HA_ADDITIONAL_STR");
				}
				
				//產生不休假加班費細項資料
				ehf030603m0 = this.generateHaOvertimeDetailData(ehf030603m0, cur_date, ha_overtime_start, ha_overtime_end,
							  ha_overtime_hour,
							  ha_ov_cost, ha_ov_cost_str, "", "依正常日計薪");
				
				
				 break;
						
			}
			*/
			
		}catch(Exception e){
			System.out.println("產生不休假加班費資料的Form(EHF030603M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030603m0;
	}
	
	/**
	 * 取得加班單的加班類別
	 * @param conn
	 * @param cur_date
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	private String getOvertimeFormType(Connection conn, String cur_date, String employee_id, String comp_id) {
		// TODO Auto-generated method stub
		
		String overtime_type = "00";
		
		try{
			String sql = "" +
			" SELECT EHF020800T1_01, EHF020800T1_04, EHF020800T1_13 " +
			" FROM EHF020800T1 " +
			" LEFT JOIN EHF020800T0 ON EHF020800T0_01 = EHF020800T1_01 AND EHF020800T0_10 = EHF020800T1_10 " +
			" WHERE 1=1 " +
			" AND EHF020800T1_04 = '"+employee_id+"' " +  //員工工號
			" AND DATE_FORMAT(EHF020800T1_06, '%Y/%m/%d') = '"+cur_date+"' " +  //加班開始時間
			" AND EHF020800T0_09 = '03' " +
			" AND EHF020800T1_10 = '"+comp_id+"' ";  //公司代碼

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				overtime_type = rs.getString("EHF020800T1_13")==null?"":rs.getString("EHF020800T1_13");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return overtime_type;
	}



	/**
	 * 計算不休假加班的津貼金額
	 * @param ehf030601m0
	 * @return
	 *//*
	protected int countHaAllowanceMoney( EHF030603M0F ehf030603m0, EHF030601M0F ehf030601m0){
		
		int allowance_money = 0;
		
		try{
			List allowancelist = ehf030601m0.getEHF030601C();
			
			//需有津貼資料筆數才進行計算
			if(allowancelist.size() > 0){
				//取得津貼細項資料List
				List retuen_list = ehf030603m0.getEHF030603C5();
				Iterator it = allowancelist.iterator();
				
				while(it.hasNext()){
					
					EHF030601M0F bean = (EHF030601M0F) it.next();
					//津貼總金額  = 津貼金額 + 加成金額
					allowance_money += bean.getEHF030601T1_04() + bean.getEHF030601T1_05();
					retuen_list.add(bean);
				}
				
				//填入津貼細項資料List
				ehf030603m0.setEHF030603C5(retuen_list);
			}
			
			List abnormallist = ehf030601m0.getEHF030601C2();
			
			//需有異常津貼資料筆數才進行處理
			if(abnormallist.size() > 0){
				//取得異常津貼資料List
				List retuen_list = ehf030603m0.getEHF030603C3();
				Iterator abit = abnormallist.iterator();
				
				while(abit.hasNext()){
					EHF030601M2F bean2 = (EHF030601M2F) abit.next();
					retuen_list.add(bean2);
				}
				
				//填入異常津貼資料List
				ehf030603m0.setEHF030603C3(retuen_list);
			}
			
		}catch(Exception e){
			System.out.println("計算不休假加班費的津貼總金額時,發生錯誤");
			e.printStackTrace();
		}
		
		return allowance_money;
	}
	
	*//**
	 * 計算不休假加班的加班費用
	 * @param ehf030602m0
	 * @return
	 *//*
	protected Map countHaOvertimeMoney( EHF030603M0F ehf030603m0, EHF030602M0F ehf030602m0){
		
		Map overtimeMap = new HashMap();
		float overtime_hour = 0;
		int overtime_money = 0;
		String overtime_money_str = "";
		
		try{
			List overtimelist = ehf030602m0.getEHF030602C();
			
			//需有加班資料筆數才進行計算
			if(overtimelist.size() > 0){
				
				List retuen_list = ehf030603m0.getEHF030603C7();
				Iterator it = overtimelist.iterator();
				
				while(it.hasNext()){
					
					EHF030602M0F bean = (EHF030602M0F) it.next();
					//加班時數
					overtime_hour += bean.getEHF030602T1_03();
					//加班費金額
					overtime_money += bean.getEHF030602T1_04();
					//加班計算明細
					overtime_money_str += bean.getEHF030602T1_05()+", ";
					retuen_list.add(bean);
				}
				
				//填入加班費計算明細資料
				ehf030603m0.setEHF030603C7(retuen_list);
			}
			
			List abnormallist = ehf030602m0.getEHF030602C2();
			
			//需有異常加班費資料筆數才進行處理
			if(abnormallist.size() > 0){
				
				List retuen_list = ehf030603m0.getEHF030603C4();
				Iterator abit = abnormallist.iterator();
				
				while(abit.hasNext()){
					EHF030602M2F bean2 = (EHF030602M2F) abit.next();
					retuen_list.add(bean2);
				}
				
				//填入異常加班費資料
				ehf030603m0.setEHF030603C4(retuen_list);
			}
			
			overtimeMap.put("OVERTIME_HOUR", overtime_hour);
			overtimeMap.put("OVERTIME_MONEY", overtime_money);
			overtimeMap.put("OVERTIME_MONEY_STR", overtime_money_str);
			
		}catch(Exception e){
			System.out.println("計算不休假加班費的加班總金額時,發生錯誤");
			e.printStackTrace();
		}
		
		return overtimeMap;
	}
	
	*//**
	 * 計算不休假加班的額外給薪金額
	 * @param ehf010103m0
	 * @param day_pay
	 * @return
	 */
	protected Map countHaAdditionalMoney( EHF030603M0F ehf030603m0, String cur_date, EHF030107M0F ehf030107m0, int day_pay, 
			float ha_overtime_hour, String overtime_type ){
		
		//BA_TOOLS tools = BA_TOOLS.getInstance();
		Map haadditionalMap = new HashMap();
		int ha_additional_money = 0;
		String ha_additional_str = "";
		
		try{
//====================================一例一休 BY maybe 20170417=================================================
			if("00".equals(overtime_type)){
				/*
				ha_additional_money = Math.round((day_pay * Float.parseFloat(ehf030107m0.getEHF030107T0_08())));
				ha_additional_str = " 額外給一日基本薪 "+ Float.parseFloat(ehf030107m0.getEHF030107T0_08()) + "倍："+ ha_additional_money + "元 ";
				
				// 產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0,cur_date, day_pay, Float.parseFloat(ehf030107m0.getEHF030107T0_08()), ha_overtime_hour, "01");
				*/
			}else if("01".equals(overtime_type)){
				//例假日
				ha_additional_money = Math.round((day_pay * Float.parseFloat(ehf030107m0.getEHF030107T0_11())));
				ha_additional_str = " 額外給一日基本薪 "+ Float.parseFloat(ehf030107m0.getEHF030107T0_11()) + "倍："+ ha_additional_money + "元 ";
				
				// 產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0,cur_date, day_pay, Float.parseFloat(ehf030107m0.getEHF030107T0_11()), ha_overtime_hour, "01");
				
			}else if("02".equals(overtime_type)){
				//休息日
				ha_additional_money = Math.round((day_pay * 4));
				ha_additional_str = " 額外給四小時基本薪："+ ha_additional_money + "元 ";
				
				// 產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0,cur_date, day_pay, (float) 0, ha_overtime_hour, "01");
				
			}else if("03".equals(overtime_type)){
				//國定假日
				ha_additional_money = Math.round((day_pay * Float.parseFloat(ehf030107m0.getEHF030107T0_08())));
				ha_additional_str = " 額外給一日基本薪 "+ Float.parseFloat(ehf030107m0.getEHF030107T0_08()) + "倍："+ ha_additional_money + "元 ";
				
				// 產生不休假計算明細資料
				ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0,cur_date, day_pay, Float.parseFloat(ehf030107m0.getEHF030107T0_08()), ha_overtime_hour, "01");
				
			}
//====================================一例一休 BY maybe 20170417=================================================
//====================================產品改寫 BY 賴泓錡  20131202=================================================
			//ha_additional_money = Math.round((day_pay * Float.parseFloat(ehf030107m0.getEHF030107T0_08())));
			//ha_additional_str = " 額外給一日基本薪 "+ Float.parseFloat(ehf030107m0.getEHF030107T0_08()) + "倍："+ ha_additional_money + "元 ";

			// 產生不休假計算明細資料
			//ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0,cur_date, day_pay, Float.parseFloat(ehf030107m0.getEHF030107T0_08()), (float) 1, "01");
//====================================產品改寫 BY 賴泓錡  20131202=================================================
			/*
				// 判斷是否不休假加班額外給薪
			if (tools.StringToBoolean(ehf010103m0.getEHF010103T0_17())) {
				// 額外給薪
				// 判斷不休假加班額外給薪類型
				if ("01".equals(ehf010103m0.getEHF010103T0_18())) {
					// 額外給一日基本薪倍數
					ha_additional_money = Math.round((day_pay * Float.parseFloat(ehf010103m0.getEHF010103T0_19())));
					ha_additional_str = " 額外給一日基本薪 "+ Float.parseFloat(ehf010103m0.getEHF010103T0_19())	+ "倍：" + ha_additional_money + "元 ";

					// 產生不休假計算明細資料
					ehf030603m0 = this.generateHaOvertimeCountDetailData( ehf030603m0, cur_date, day_pay,	Float.parseFloat(ehf010103m0.getEHF010103T0_19()),(float) 1, "01");

				} else if ("02".equals(ehf010103m0.getEHF010103T0_18())) {
					// 額外給固定金額
					ha_additional_money = Integer.parseInt(ehf010103m0.getEHF010103T0_20());
					ha_additional_str = " 額外給固定金額：" + ha_additional_money 	+ "元 ";

					// 產生不休假計算明細資料
					ehf030603m0 = this.generateHaOvertimeCountDetailData(ehf030603m0, cur_date, ha_additional_money, (float) 1, (float) 1, "02");

				}
			} else {
				// 不額外給薪
				ha_additional_money = 0;
				ha_additional_str = "";
			}
			*/
			haadditionalMap.put("HA_ADDITIONAL_MONEY", ha_additional_money);
			haadditionalMap.put("HA_ADDITIONAL_STR", ha_additional_str);
			
		}catch(Exception e){
			System.out.println("計算不休假加班的額外給薪金額時,發生錯誤");
			e.printStackTrace();
		}
		
		return haadditionalMap;
	}
	
	/**
	 * 產生津貼資料 (當不休假加班資料型態設定為MERGE時)
	 * @param conn
	 * @param ehf030601m0
	 * @param user_name
	 * @param comp_id
	 *//*
	protected void addAllowanceForHaOvertime( Connection conn, EHF030601M0F ehf030601m0, String user_name, String comp_id ){
		
		try{
			//津貼資料UID
			String EHF030601T0_U = "";
			if( ehf030601m0 != null && (ehf030601m0.getEHF030601C().size() > 0 || ehf030601m0.getEHF030601C2().size() > 0)){
				//取得要歸入的津貼資料UID
				EHF030601T0_U = this.getAllowanceUID( conn, ehf030601m0.getEHF030601T0_M1(), ehf030601m0.getEHF030601T0_M(),
													  ehf030601m0.getEHF030601T0_01(), ehf030601m0.getEHF030601T0_07());
			}
			
			//需有津貼資料筆數才進行計算
			if( ehf030601m0 != null && ehf030601m0.getEHF030601C().size() > 0){
				//有津貼資料才新增
				List allowancelist = ehf030601m0.getEHF030601C();
				Iterator it = allowancelist.iterator();
				
				while(it.hasNext()){
					
					EHF030601M0F bean = (EHF030601M0F) it.next();
					//設定津貼資料UID
					bean.setEHF030601T0_U(EHF030601T0_U);
					//設定假日的備註
					bean.setEHF030601T1_06("假日");
					//新增津貼資料
					this.addAllowanceDetailData(conn, bean, user_name, comp_id);
					
				}
			}
			
			//新增異常資料
			if( ehf030601m0 != null && ehf030601m0.getEHF030601C2().size() > 0){
				//有異常資料才新增
				List abnormallist = ehf030601m0.getEHF030601C2();
				
				//list.size() > 0 表示有資料, 才新增異常津貼明細資料
				Iterator abit = abnormallist.iterator();
				
				while(abit.hasNext()){
					
					EHF030601M2F bean2 = (EHF030601M2F) abit.next();
					bean2.setEHF030601T2_U(EHF030601T0_U);  //津貼明細UID
					//新增異常津貼資料
					this.addAbnormalAllowanceData(conn, bean2, ehf030601m0.getUSER_CREATE(), bean2.getEHF030601T2_06());
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	*//**
	 * 取得津貼資料UID
	 * @param conn
	 * @param salYYMM
	 * @param costYYMM
	 * @param employee_id
	 * @param comp_id
	 * @return
	 *//*
	public String getAllowanceUID( Connection conn, String salYYMM, String costYYMM, String employee_id, String comp_id ){
		
		String EHF030601T0_U = "";
		
		try{
			
			String sql = "" +
			 " SELECT EHF030601T0_U " +
			 " FROM EHF030601T0" +
			 " WHERE 1=1 " +
			 " AND EHF030601T0_01 = '"+employee_id+"' " +  //員工工號
			 " AND EHF030601T0_M = '"+costYYMM+"' " +  //入扣帳年月
			 " AND EHF030601T0_M1 = '"+salYYMM+"' " +  //薪資年月
			 " AND EHF030601T0_07 = '"+comp_id+"' ";  //公司代碼

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				EHF030601T0_U = rs.getString("EHF030601T0_U");
			}else{
				//如果本月尚未位產生津貼UID
				//使用 CodeRules 做津貼單號取得的動作
				EHF030601T0_U = EMS_GetCodeRules.getInstance().getCodeRule("EHF030601", comp_id);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF030601T0_U;
	}
	
	*//**
	 * 產生加班費資料 (當不休假加班資料型態設定為MERGE時)
	 * @param conn
	 * @param ehf030602m0
	 * @param user_name
	 * @param comp_id
	 *//*
	protected void addOvertimeForHaOvertime( Connection conn, EHF030602M0F ehf030602m0, String user_name, String comp_id ){
		
		try{
			//加班資料UID
			String EHF030602T0_U = "";
			if( ehf030602m0 != null && (ehf030602m0.getEHF030602C().size() > 0 || ehf030602m0.getEHF030602C2().size() > 0)){
				//取得要歸入的加班資料UID
				EHF030602T0_U = this.getOvertimeUID( conn, ehf030602m0.getEHF030602T0_M1(), ehf030602m0.getEHF030602T0_M(),
													 ehf030602m0.getEHF030602T0_01(), ehf030602m0.getEHF030602T0_07());
			}
			
			
			//需有加班資料筆數才進行計算
			if(ehf030602m0 != null && ehf030602m0.getEHF030602C().size() > 0){
				//有加班費資料才進行處理
				List overtimelist = ehf030602m0.getEHF030602C();
				Iterator it = overtimelist.iterator();
				
				while(it.hasNext()){
					
					EHF030602M0F bean = (EHF030602M0F) it.next();
					//設定加班資料UID
					bean.setEHF030602T0_U(EHF030602T0_U);
					//設定假日的備註
					bean.setEHF030602T1_06("假日");
					//新增加班資料
					this.addOvertimeDetailData(conn, bean, user_name, comp_id);
					
				}
			}
			
			//處理異常加班費資訊
			if(ehf030602m0 != null && ehf030602m0.getEHF030602C2().size() > 0){
				//有異常加班費資料才進行異常處理
				List abnormallist = ehf030602m0.getEHF030602C2();
				
				//list.size() > 0 表示有資料, 才新增異常加班費資料
				Iterator abit = abnormallist.iterator();
				
				while(abit.hasNext()){
					
					EHF030602M2F bean2 = (EHF030602M2F) abit.next();
					bean2.setEHF030602T2_U(EHF030602T0_U);  //加班費明細UID
					//新增異常津貼資料
					this.addAbnormalOvertimeData(conn, bean2, ehf030602m0.getUSER_CREATE(), bean2.getEHF030602T2_04());
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	*//**
	 * 取得加班費資料UID
	 * @param conn
	 * @param salYYMM
	 * @param costYYMM
	 * @param employee_id
	 * @param comp_id
	 * @return
	 *//*
	public String getOvertimeUID( Connection conn, String salYYMM, String costYYMM, String employee_id, String comp_id ){
		
		String EHF030602T0_U = "";
		
		try{
			
			String sql = "" +
			 " SELECT EHF030602T0_U " +
			 " FROM EHF030602T0" +
			 " WHERE 1=1 " +
			 " AND EHF030602T0_01 = '"+employee_id+"' " +  //員工工號
			 " AND EHF030602T0_M = '"+costYYMM+"' " +  //入扣帳年月
			 " AND EHF030602T0_M1 = '"+salYYMM+"' " +  //薪資年月
			 " AND EHF030602T0_07 = '"+comp_id+"' ";  //公司代碼

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				EHF030602T0_U = rs.getString("EHF030602T0_U");
			}else{
				//如果本月尚未產生加班費UID
				//使用 CodeRules 做加班費單號取得的動作
				EHF030602T0_U = EMS_GetCodeRules.getInstance().getCodeRule("EHF030602", comp_id);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF030602T0_U;
	}
	
	
	*//**
	 * 新增不休假加班費資料, 不休假加班費細項資料, 異常不休假加班費資料到資料庫
	 * @param conn
	 * @param ehf030603m0
	 * @return
	 */
	public String addHaOvertime( Connection conn, EHF030603M0F ehf030603m0 ){
		
		String EHF030603T0_U = "";
		
		try{
			if(ehf030603m0 != null){ 
				//新增不休假加班費表頭資料
				EHF030603T0_U = this.addHaOvertimeData(conn, ehf030603m0, ehf030603m0.getUSER_CREATE(), ehf030603m0.getEHF030603T0_07());
				
				List list = ehf030603m0.getEHF030603C();
				
				//list.size() > 0 表示有資料, 才新增不休假加班費明細資料
				Iterator it = list.iterator();
				
				while(it.hasNext()){
					
					EHF030603M0F bean = (EHF030603M0F) it.next();
					bean.setEHF030603T0_U(EHF030603T0_U);  //不休假加班費明細UID
					//新增不休假加班費細項資料
					this.addHaOvertimeDetailData(conn, bean, ehf030603m0.getUSER_CREATE(), bean.getEHF030603T1_07());
				}
				
				List abnormallist = null;
				//處理異常不休假加班資訊
				if(ehf030603m0.getEHF030603C2().size() > 0){
					//有異常不休假加班費資料才進行異常處理
					abnormallist = ehf030603m0.getEHF030603C2();
					Iterator abit = abnormallist.iterator();
					
					while(abit.hasNext()){
						
						EHF030603M2F bean2 = (EHF030603M2F) abit.next();
						bean2.setEHF030603T2_U(EHF030603T0_U);  //不休假加班費明細UID
						//新增異常不休假加班費資料
						this.addAbnormalHaOvertimeData(conn, bean2, ehf030603m0.getUSER_CREATE(), bean2.getEHF030603T2_04());
					}
				}
				/*
				//處理異常不休假津貼資訊
				if(ehf030603m0.getEHF030603C3().size() > 0){
					//有異常不休假加班費資料才進行異常處理
					abnormallist = ehf030603m0.getEHF030603C3();
					Iterator abit = abnormallist.iterator();
					
					while(abit.hasNext()){
						
						EHF030601M2F bean2 = (EHF030601M2F) abit.next();
						bean2.setEHF030601T2_U(EHF030603T0_U);  //不休假加班費明細UID
						//新增異常不休假津貼資料
						this.addAbnormalHaAllowanceData(conn, bean2, ehf030603m0.getUSER_CREATE(), bean2.getEHF030601T2_06());
					}
				}
				
				//處理異常不休假的加班費資訊
				if(ehf030603m0.getEHF030603C4().size() > 0){
					//有異常不休假加班費資料才進行異常處理
					abnormallist = ehf030603m0.getEHF030603C4();
					Iterator abit = abnormallist.iterator();
					
					while(abit.hasNext()){
						
						EHF030602M2F bean2 = (EHF030602M2F) abit.next();
						bean2.setEHF030602T2_U(EHF030603T0_U);  //不休假加班費明細UID
						//新增異常不休假的加班費資料
						this.addAbnormalHaOvOvertimeData(conn, bean2, ehf030603m0.getUSER_CREATE(), bean2.getEHF030602T2_04());
					}
				}
				
				List countlist = null;
				//處理不休假加班津貼明細資訊
				if(ehf030603m0.getEHF030603C5().size() > 0){
					//有不休假加班津貼明細資料才進行處理
					countlist = ehf030603m0.getEHF030603C5();
					Iterator coit = countlist.iterator();
					
					while(coit.hasNext()){
						
						EHF030601M0F bean2 = (EHF030601M0F) coit.next();
						bean2.setEHF030601T0_U(EHF030603T0_U);  //不休假加班費明細UID
						//新增不休假加班津貼明細資料
						this.addHaAllowanceDetailData(conn, bean2, ehf030603m0.getUSER_CREATE(), bean2.getEHF030601T1_07());
					}
				}
				
				//處理不休假加班計算明細資訊
				if(ehf030603m0.getEHF030603C7().size() > 0){
					//有不休假加班計算明細資料才進行處理
					countlist = ehf030603m0.getEHF030603C7();
					Iterator coit = countlist.iterator();
					
					while(coit.hasNext()){
						
						EHF030602M3F bean2 = (EHF030602M3F) coit.next();
						bean2.setEHF030602T3_U(EHF030603T0_U);  //不休假加班費明細UID
						//新增不休假加班計算明細資料
						this.addHaOvertimeOVCountDetailData(conn, bean2, ehf030603m0.getUSER_CREATE(), bean2.getEHF030602T3_07());
					}
				}
				*/
				List countlist = null;
				//處理不休假計算明細資訊
				if(ehf030603m0.getEHF030603C6().size() > 0){
					//有不休假計算明細資料才進行處理
					countlist = ehf030603m0.getEHF030603C6();
					Iterator coit = countlist.iterator();
					
					while(coit.hasNext()){
						
						EHF030603M6F bean2 = (EHF030603M6F) coit.next();
						bean2.setEHF030603T6_U(EHF030603T0_U);  //不休假加班費明細UID
						//新增不休假計算明細資料
						this.addHaOvertimeCountDetailData(conn, bean2, ehf030603m0.getUSER_CREATE(), bean2.getEHF030603T6_07());
					}
				}

			}
			
		}catch(Exception e){
			System.out.println("新增不休假加班費資料與不休假加班費細項資料,異常不休假加班費資料到資料庫時,發生錯誤");
			e.printStackTrace();
		}
		
		return EHF030603T0_U;
	}
	
	/**
	 * 產生不休假加班費表頭資料(Form)
	 * @param employee_id
	 * @param dept_id
	 * @param salYYMM
	 * @param costYYMM
	 * @param user_name
	 * @param comp_id
	 * @return
	 */
	protected EHF030603M0F generateHaOvertimeHeadData( String employee_id, String dept_id,
						String salYYMM, String costYYMM, String user_name, String comp_id ){
		
		EHF030603M0F Form = null;
		List list = new ArrayList();
		List list2 = new ArrayList();
		List list3 = new ArrayList();
		List list4 = new ArrayList();
		List list5 = new ArrayList();
		List list6 = new ArrayList();
		List list7 = new ArrayList();
		
		try{
			Form = new EHF030603M0F();
			
			Form.setEHF030603T0_U("");  //不休假加班費明細UID
			Form.setEHF030603T0_01(employee_id);  //員工工號
			Form.setEHF030603T0_02(dept_id);  //部門代號
			Form.setEHF030603T0_M(costYYMM);  //入扣帳年月
			Form.setEHF030603T0_M1(salYYMM);  //薪資年月
			Form.setEHF030603T0_DS("02");  //資料來源
			Form.setEHF030603T0_03("");  //備註
			Form.setEHF030603T0_SCP("01");  //薪資處理狀態
			Form.setEHF030603T0_07(comp_id);  //公司代碼
			Form.setEHF030603T0_SCU("");  //薪資計算明細UID
			Form.setUSER_CREATE(user_name);  //建立人員
			Form.setUSER_UPDATE(user_name); //修改人員
			
			Form.setEHF030603C(list);  //建立空的LIST
			Form.setEHF030603C2(list2);  //建立空的LIST
			Form.setEHF030603C3(list3);  //建立空的LIST
			Form.setEHF030603C4(list4);  //建立空的LIST
			Form.setEHF030603C5(list5);  //建立空的LIST
			Form.setEHF030603C6(list6);  //建立空的LIST
			Form.setEHF030603C7(list7);  //建立空的LIST
			
		}catch(Exception e){
			System.out.println("產生不休假加班費資料的Form(EHF030603M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return Form;
	}
	
	/**
	 * 產生不休假加班細項資料(Form)
	 * @param ehf030603m0
	 * @param overtime_date
	 * @param overtime_start
	 * @param overtime_end
	 * @param overtime_hour
	 * @param overtime_money
	 * @param overtime_count_detail
	 * @param overtime_type_desc
	 * @return
	 */
	protected EHF030603M0F generateHaOvertimeDetailData( EHF030603M0F ehf030603m0, String ha_overtime_date, String ha_overtime_start,
			  String ha_overtime_end, float ha_overtime_hour, int ha_overtime_money, String ha_overtime_count_detail, String overtime_count_detail,
			  String ha_overtime_type_desc ){

		EHF030603M0F bean = null;
		List list = null;

		try{
			list = ehf030603m0.getEHF030603C();
			bean = new EHF030603M0F();
			
			bean.setEHF030603T1_U("");  //不休假加班費明細UID
			bean.setEHF030603T1_DATE(ha_overtime_date);  //不休假加班日期(考勤日期)
			bean.setEHF030603T1_01(ha_overtime_start);  //不休假加班日期時間(起)
			bean.setEHF030603T1_02(ha_overtime_end);  //不休假加班日期時間(迄)
			bean.setEHF030603T1_03(ha_overtime_hour);  //不休假加班時數
			bean.setEHF030603T1_04(ha_overtime_money);  //不休假加班費金額
			bean.setEHF030603T1_05(ha_overtime_count_detail);  //不休假加班費計算明細
			bean.setEHF030603T1_05_OV(overtime_count_detail);  //不休假加班的加班計算明細
			bean.setEHF030603T1_DS("01");  //資料來源
			bean.setEHF030603T1_06(ha_overtime_type_desc);  //備註
			bean.setEHF030603T1_07(ehf030603m0.getEHF030603T0_07());  //公司代碼
			
			list.add(bean);
			
			ehf030603m0.setEHF030603C(list);
			
		}catch(Exception e){
			System.out.println("產生不休假加班費細項資料的bean(EHF030603M0F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030603m0;
	}
	
	/**
	 * 產生異常不休假加班費細項資料(Form)
	 * @param ehf030603m0
	 * @param ha_overtime_date
	 * @param abnormal_ha_overtime_hour
	 * @param abnormal_desc
	 * @return
	 */
	protected EHF030603M0F generateAbnormalHaOvertimeData( EHF030603M0F ehf030603m0, String ha_overtime_date,
			  			   float abnormal_ha_overtime_hour, String abnormal_desc ){

		EHF030603M2F bean = null;
		List list = null;

		try{
			list = ehf030603m0.getEHF030603C2();
			bean = new EHF030603M2F();
			
			bean.setEHF030603T2_U("");  //不休假加班費明細UID
			bean.setEHF030603T2_DATE(ha_overtime_date);  //不休假加班日期(考勤日期)
			bean.setEHF030603T2_01(abnormal_ha_overtime_hour);  //異常不休假加班扣除時數
			bean.setEHF030603T2_02(abnormal_desc);  //異常不休假加班說明
			bean.setEHF030603T2_DS("01");  //資料來源
			bean.setEHF030603T2_03("");  //備註
			bean.setEHF030603T2_04(ehf030603m0.getEHF030603T0_07());  //公司代碼
			
			list.add(bean);
			
			ehf030603m0.setEHF030603C2(list);
			
		}catch(Exception e){
			System.out.println("產生異常不休假加班費細項資料的bean(EHF030603M2F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030603m0;
	}
	
	/**
	 * 產生不休假計算明細資料
	 * @param ehf030603m0
	 * @param ha_overtime_date
	 * @param pay
	 * @param rate
	 * @param hours
	 * @param ha_overtime_type
	 * @return
	 */
	protected EHF030603M0F generateHaOvertimeCountDetailData( EHF030603M0F ehf030603m0, String ha_overtime_date, int pay, float rate, 
															float hours, String ha_overtime_type ){

		EHF030603M6F bean = null;
		List list = null;

		try{
			//加班時數大於0, 才需要記錄
			if( hours > 0){
				list = ehf030603m0.getEHF030603C6();
				bean = new EHF030603M6F();
			
				bean.setEHF030603T6_U("");  //加班費明細UID
				bean.setEHF030603T6_01(ha_overtime_date);  //加班日期(考勤日期)
				bean.setEHF030603T6_02(ha_overtime_type);  //給薪類型
				bean.setEHF030603T6_03(pay);  //時薪
				bean.setEHF030603T6_04(rate);  //時薪倍數
				bean.setEHF030603T6_05(hours);  //加班時數
				bean.setEHF030603T6_DS("01");  //資料來源
				bean.setEHF030603T6_06("");  //備註
				bean.setEHF030603T6_07(ehf030603m0.getEHF030603T0_07());  //公司代碼
				
				list.add(bean);
					
				ehf030603m0.setEHF030603C6(list);
			}
			
		}catch(Exception e){
			System.out.println("產生不休假計算明細資料的bean(EHF030603M6F)時,發生錯誤");
			e.printStackTrace();
		}
		
		return ehf030603m0;
	}
	
	/**
	 * 依據員工,日期區間
	 * 取得詳細考勤資料  Map
	 * @param conn
	 * @param classMap
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @param error_flag
	 * @param ha_flag
	 * @param comp_id
	 * @return
	 */
	public Map getEmpDayAttDataMap( Connection conn, String employee_id, String start_date, String end_date,
			  boolean error_flag, boolean ha_flag, String comp_id  ){
		
		Map dayattmap = new HashMap();
		
		try{
			if (!ha_flag) {
				// 取得考勤資料
				List dayattlist = this.getEmpDayAttData(conn, employee_id,	start_date, end_date, error_flag, ha_flag, comp_id);

				Iterator it = dayattlist.iterator();

				while (it.hasNext()) {
					Map tempMap = (Map) it.next();
					dayattmap.put(tempMap.get("考勤日期"), tempMap);
				}
			} else {
				if (ha_flag) {
					// 取得考勤資料
					List dayattlist = this.getHaEmpDayAttData(conn, employee_id,start_date, end_date, error_flag, ha_flag, comp_id);

					Iterator it = dayattlist.iterator();

					while (it.hasNext()) {
						Map tempMap = (Map) it.next();
						dayattmap.put(tempMap.get("考勤日期"), tempMap);
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dayattmap;
	}
	
	/**
	 * 依據員工,日期區間
	 * 取得詳細考勤資料  List
	 * @param conn
	 * @param classMap
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @param error_flag
	 * @param ha_flag
	 * @param comp_id
	 * @return
	 */
	public List getEmpDayAttData( Connection conn, String employee_id, String start_date, String end_date,
								  boolean error_flag, boolean ha_flag, String comp_id  ){
		
		List dayattlist = new ArrayList();
		
		try{
			//取得員工排班表資料
			
			String sql = "" +
			" SELECT " +
			" tableA.部門代號 AS 部門代號, tableA.員工工號 AS 員工工號, IFNULL(tableA.考勤日期, '') AS 考勤日期, " + 
			" IFNULL(tableA.修正來源, '') AS 上班修正來源, IFNULL(tableA.考勤類型, '') AS 上班考勤類型, " +
			" IFNULL(tableA.狀態說明, '') AS 上班刷卡狀態, tableA.刷卡時間 AS 上班刷卡時間, " +
			" IFNULL(tableA.刷卡日期時間, '') AS 上班刷卡日期時間, IFNULL(tableA.是否異常, true) AS 上班是否異常, IFNULL(tableA.是否已修正, false) AS 上班是否已修正, " +
			" IFNULL(tableB.修正來源, '') AS 上午下班修正來源, IFNULL(tableB.考勤類型, '') AS 上午下班考勤類型, " +
			" IFNULL(tableB.狀態說明, '') AS 上午下班刷卡狀態, tableB.刷卡時間 AS 上午下班刷卡時間, " +
			" IFNULL(tableB.刷卡日期時間, '') AS 上午下班刷卡日期時間, IFNULL(tableB.是否異常, true) AS 上午下班是否異常, IFNULL(tableB.是否已修正, false) AS 上午下班是否已修正, " +
			" IFNULL(tableC.修正來源, '') AS 下午上班修正來源, IFNULL(tableC.考勤類型, '') AS 下午上班考勤類型, " +
			" IFNULL(tableC.狀態說明, '') AS 下午上班刷卡狀態, tableC.刷卡時間 AS 下午上班刷卡時間, " +
			" IFNULL(tableC.刷卡日期時間, '') AS 下午上班刷卡日期時間, IFNULL(tableC.是否異常, true) AS 下午上班是否異常, IFNULL(tableC.是否已修正, false) AS 下午上班是否已修正, " +
			" IFNULL(tableD.修正來源, '') AS 下班修正來源, IFNULL(tableD.考勤類型, '') AS 下班考勤類型, " +
			" IFNULL(tableD.狀態說明, '') AS 下班刷卡狀態, tableD.刷卡時間 AS 下班刷卡時間, " +
			" IFNULL(tableD.刷卡日期時間, '') AS 下班刷卡日期時間, IFNULL(tableD.是否異常, true) AS 下班是否異常, IFNULL(tableD.是否已修正, false) AS 下班是否已修正, " +
			" IFNULL(tableE.修正來源, '') AS 加班上班修正來源, IFNULL(tableE.考勤類型, '') AS 加班上班考勤類型, " +
			" IFNULL(tableE.狀態說明, '') AS 加班上班刷卡狀態, tableE.刷卡時間 AS 加班上班刷卡時間, " +
			" IFNULL(tableE.刷卡日期時間, '') AS 加班上班刷卡日期時間, IFNULL(tableE.是否異常, true) AS 加班上班是否異常, IFNULL(tableE.是否已修正, false) AS 加班上班是否已修正, " +
			" IFNULL(tableF.修正來源, '') AS 加班下班修正來源, IFNULL(tableF.考勤類型, '') AS 加班下班考勤類型, " +
			" IFNULL(tableF.狀態說明, '') AS 加班下班刷卡狀態, tableF.刷卡時間 AS 加班下班刷卡時間, " +
			" IFNULL(tableF.刷卡日期時間, '') AS 加班下班刷卡日期時間, IFNULL(tableF.是否異常, true) AS 加班下班是否異常, IFNULL(tableF.是否已修正, false) AS 加班下班是否已修正, " +
			" CONCAT('上班', tableA.狀態說明, ',', '上午下班', tableB.狀態說明, ',', '下午上班', tableC.狀態說明, ',', " +
			" '下班', tableD.狀態說明, ',', '加班上班', tableE.狀態說明, ',', '加班下班', tableF.狀態說明 ) AS 備註 " +
			//" IFNULL(schedule.HOLIDAY_FLAG, false) AS 是否為休假日, " +
			//" IFNULL(schedule.CLASS_NO, -1) AS 班別序號 " +
			" FROM ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "1", comp_id)+" " +  //上班
			" ) tableA " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "7", comp_id)+" " +  //上午下班
			" ) tableB ON tableA.員工工號 = tableB.員工工號 AND tableA.考勤日期 = tableB.考勤日期 " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "8", comp_id)+" " +  //下午上班
			" ) tableC ON tableA.員工工號 = tableC.員工工號 AND tableA.考勤日期 = tableC.考勤日期 " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "2", comp_id)+" " +  //下班
			" ) tableD ON tableA.員工工號 = tableD.員工工號 AND tableA.考勤日期 = tableD.考勤日期 " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "5", comp_id)+" " +  //加班上班
			" ) tableE ON tableA.員工工號 = tableE.員工工號 AND tableA.考勤日期 = tableE.考勤日期 " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "6", comp_id)+" " +  //加班下班
			" ) tableF ON tableA.員工工號 = tableF.員工工號 AND tableA.考勤日期 = tableF.考勤日期 " +
			/*
			" LEFT JOIN " +
			" ( " +
			" SELECT EHF010105T0_02 AS EMP_ID, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') AS SCHEDULE_DATE, " +
			" IFNULL(EHF010105T1_03, false) AS HOLIDAY_FLAG, " +
			" IFNULL(EHF010105T1_04, -1) AS CLASS_NO, " +
			" IFNULL(EHF010100T0_NFLG, false) AS NOON_FLAG, " +
			" IFNULL(EHF010102T0_04, false) AS OKEY_SW, " +
			" IFNULL(EHF010102T0_12, false) AS OSW " +
			" FROM EHF010105T1 " +
			" LEFT JOIN EHF010105T0 ON EHF010105T0_01 = EHF010105T1_01 " +
			" LEFT JOIN EHF010100T0 ON EHF010100T0_01 = EHF010105T1_04 " +
			" LEFT JOIN EHF010102T0 ON EHF010102T0_01 = EHF010100T0_12 " +
			" WHERE 1=1 " +
			" AND EHF010105T0_02 = '"+employee_id+"' " +  //員工工號
			" AND EHF010105T1_02 BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //排班日期
			" AND EHF010105T1_06 = '"+comp_id+"' " +  //公司代碼  
			" ) schedule ON tableA.員工工號 = schedule.EMP_ID AND tableA.考勤日期 = schedule.SCHEDULE_DATE " +
			*/
			" WHERE 1=1 " ;
			//異常flag
			if(error_flag){
				sql += " AND ( tableA.是否異常 = true " +
					   " OR ( schedule.NOON_FLAG AND (tableB.是否異常 = true OR tableC.是否異常 = true) ) " ;
//					   if(ems_tools.hasNoonRecord(classMap)){
//						   sql += " OR tableB.是否異常 = true OR tableC.是否異常 = true ";
//					   }
				sql += " OR tableD.是否異常 = true " +
					   " OR ( (schedule.OKEY_SW AND schedule.OSW) AND ( tableE.是否異常  = true OR tableF.是否異常  = true) ) " ;
//					   if(ems_tools.hasOverTime(classMap)){
//						   sql += " OR tableE.是否異常  = true OR tableF.是否異常  = true ";
//					   }
				sql += " ) ";  
			}
			//假日 flag
			if(ha_flag){
				
				
			}
			sql += " ORDER BY tableA.部門代號, tableA.員工工號, tableA.考勤日期 ";
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			//System.out.println(sql);
			while(rs.next()){
				
				Map tempMap = new HashMap();
				tempMap.put("部門代號", rs.getString("部門代號"));
				tempMap.put("員工工號", rs.getString("員工工號"));
				tempMap.put("考勤日期", rs.getString("考勤日期"));
				tempMap.put("上班考勤類型", rs.getString("上班考勤類型"));
				tempMap.put("上班是否異常", rs.getBoolean("上班是否異常"));
				tempMap.put("上班是否已修正", rs.getBoolean("上班是否已修正"));
				tempMap.put("上班刷卡日期時間", rs.getString("上班刷卡日期時間"));
				tempMap.put("上班刷卡狀態", rs.getString("上班刷卡狀態"));
				tempMap.put("上班修正來源", rs.getString("上班修正來源"));
				tempMap.put("上午下班考勤類型", rs.getString("上午下班考勤類型"));
				tempMap.put("上午下班是否異常", rs.getBoolean("上午下班是否異常"));
				tempMap.put("上午下班是否已修正", rs.getBoolean("上午下班是否已修正"));
				tempMap.put("上午下班刷卡日期時間", rs.getString("上午下班刷卡日期時間"));
				tempMap.put("上午下班刷卡狀態", rs.getString("上午下班刷卡狀態"));
				tempMap.put("上午下班修正來源", rs.getString("上午下班修正來源"));
				tempMap.put("下午上班考勤類型", rs.getString("下午上班考勤類型"));
				tempMap.put("下午上班是否異常", rs.getBoolean("下午上班是否異常"));
				tempMap.put("下午上班是否已修正", rs.getBoolean("下午上班是否已修正"));
				tempMap.put("下午上班刷卡日期時間", rs.getString("下午上班刷卡日期時間"));
				tempMap.put("下午上班刷卡狀態", rs.getString("下午上班刷卡狀態"));
				tempMap.put("下午上班修正來源", rs.getString("下午上班修正來源"));
				tempMap.put("下班考勤類型", rs.getString("下班考勤類型"));
				tempMap.put("下班是否異常", rs.getBoolean("下班是否異常"));
				tempMap.put("下班是否已修正", rs.getBoolean("下班是否已修正"));
				tempMap.put("下班刷卡日期時間", rs.getString("下班刷卡日期時間"));
				tempMap.put("下班刷卡狀態", rs.getString("下班刷卡狀態"));
				tempMap.put("下班修正來源", rs.getString("下班修正來源"));
				tempMap.put("加班上班考勤類型", rs.getString("加班上班考勤類型"));
				tempMap.put("加班上班是否異常", rs.getBoolean("加班上班是否異常"));
				tempMap.put("加班上班是否已修正", rs.getBoolean("加班上班是否已修正"));
				tempMap.put("加班上班刷卡日期時間", rs.getString("加班上班刷卡日期時間"));
				tempMap.put("加班上班刷卡狀態", rs.getString("加班上班刷卡狀態"));
				tempMap.put("加班上班修正來源", rs.getString("加班上班修正來源"));
				tempMap.put("加班下班考勤類型", rs.getString("加班下班考勤類型"));
				tempMap.put("加班下班是否異常", rs.getBoolean("加班下班是否異常"));
				tempMap.put("加班下班是否已修正", rs.getBoolean("加班下班是否已修正"));
				tempMap.put("加班下班刷卡日期時間", rs.getString("加班下班刷卡日期時間"));
				tempMap.put("加班下班刷卡狀態", rs.getString("加班下班刷卡狀態"));
				tempMap.put("加班下班修正來源", rs.getString("加班下班修正來源"));
				//tempMap.put("是否為休假日", rs.getBoolean("是否為休假日"));
				//tempMap.put("班別序號", rs.getInt("班別序號"));
				
				dayattlist.add(tempMap);
			}
			rs.close();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dayattlist;
	}
	
	/**
	 * 依據員工,日期區間
	 * 取得詳細考勤資料  List
	 * @param conn
	 * @param classMap
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @param error_flag
	 * @param ha_flag
	 * @param comp_id
	 * @return
	 */
	public List getHaEmpDayAttData( Connection conn, String employee_id, String start_date, String end_date,  boolean error_flag, boolean ha_flag, String comp_id  ){
		
		List dayattlist = new ArrayList();
		
		try{
			//取得員工排班表資料
			
			String sql = "" +
			" SELECT " +
			" tableA.部門代號 AS 部門代號, tableA.員工工號 AS 員工工號, IFNULL(tableA.考勤日期, '') AS 考勤日期, " + 
			" IFNULL(tableA.修正來源, '') AS 上班修正來源, IFNULL(tableA.考勤類型, '') AS 上班考勤類型, " +
			" IFNULL(tableA.狀態說明, '') AS 上班刷卡狀態, tableA.刷卡時間 AS 上班刷卡時間, " +
			" IFNULL(tableA.刷卡日期時間, '') AS 上班刷卡日期時間, IFNULL(tableA.是否異常, true) AS 上班是否異常, IFNULL(tableA.是否已修正, false) AS 上班是否已修正, " +
			" IFNULL(tableB.修正來源, '') AS 上午下班修正來源, IFNULL(tableB.考勤類型, '') AS 上午下班考勤類型, " +
			" IFNULL(tableB.狀態說明, '') AS 上午下班刷卡狀態, tableB.刷卡時間 AS 上午下班刷卡時間, " +
			" IFNULL(tableB.刷卡日期時間, '') AS 上午下班刷卡日期時間, IFNULL(tableB.是否異常, true) AS 上午下班是否異常, IFNULL(tableB.是否已修正, false) AS 上午下班是否已修正, " +
			" IFNULL(tableC.修正來源, '') AS 下午上班修正來源, IFNULL(tableC.考勤類型, '') AS 下午上班考勤類型, " +
			" IFNULL(tableC.狀態說明, '') AS 下午上班刷卡狀態, tableC.刷卡時間 AS 下午上班刷卡時間, " +
			" IFNULL(tableC.刷卡日期時間, '') AS 下午上班刷卡日期時間, IFNULL(tableC.是否異常, true) AS 下午上班是否異常, IFNULL(tableC.是否已修正, false) AS 下午上班是否已修正, " +
			" IFNULL(tableD.修正來源, '') AS 下班修正來源, IFNULL(tableD.考勤類型, '') AS 下班考勤類型, " +
			" IFNULL(tableD.狀態說明, '') AS 下班刷卡狀態, tableD.刷卡時間 AS 下班刷卡時間, " +
			" IFNULL(tableD.刷卡日期時間, '') AS 下班刷卡日期時間, IFNULL(tableD.是否異常, true) AS 下班是否異常, IFNULL(tableD.是否已修正, false) AS 下班是否已修正, " +
			" IFNULL(tableE.修正來源, '') AS 加班上班修正來源, IFNULL(tableE.考勤類型, '') AS 加班上班考勤類型, " +
			" IFNULL(tableE.狀態說明, '') AS 加班上班刷卡狀態, tableE.刷卡時間 AS 加班上班刷卡時間, " +
			" IFNULL(tableE.刷卡日期時間, '') AS 加班上班刷卡日期時間, IFNULL(tableE.是否異常, true) AS 加班上班是否異常, IFNULL(tableE.是否已修正, false) AS 加班上班是否已修正, " +
			" IFNULL(tableF.修正來源, '') AS 加班下班修正來源, IFNULL(tableF.考勤類型, '') AS 加班下班考勤類型, " +
			" IFNULL(tableF.狀態說明, '') AS 加班下班刷卡狀態, tableF.刷卡時間 AS 加班下班刷卡時間, " +
			" IFNULL(tableF.刷卡日期時間, '') AS 加班下班刷卡日期時間, IFNULL(tableF.是否異常, true) AS 加班下班是否異常, IFNULL(tableF.是否已修正, false) AS 加班下班是否已修正, " +
			" CONCAT('上班', tableA.狀態說明, ',', '上午下班', tableB.狀態說明, ',', '下午上班', tableC.狀態說明, ',', " +
			" '下班', tableD.狀態說明, ',', '加班上班', tableE.狀態說明, ',', '加班下班', tableF.狀態說明 ) AS 備註 " +
			//" IFNULL(schedule.HOLIDAY_FLAG, false) AS 是否為休假日, " +
			//" IFNULL(schedule.CLASS_NO, -1) AS 班別序號 " +
			" FROM ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "5", comp_id)+" " +  //上班
			" ) tableA " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "7", comp_id)+" " +  //上午下班
			" ) tableB ON tableA.員工工號 = tableB.員工工號 AND tableA.考勤日期 = tableB.考勤日期 " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "8", comp_id)+" " +  //下午上班
			" ) tableC ON tableA.員工工號 = tableC.員工工號 AND tableA.考勤日期 = tableC.考勤日期 " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "6", comp_id)+" " +  //下班
			" ) tableD ON tableA.員工工號 = tableD.員工工號 AND tableA.考勤日期 = tableD.考勤日期 " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "5", comp_id)+" " +  //加班上班
			" ) tableE ON tableA.員工工號 = tableE.員工工號 AND tableA.考勤日期 = tableE.考勤日期 " +
			" LEFT JOIN " + 
			" ( " +
			" "+this.getDayAttSQL(employee_id, start_date, end_date, "6", comp_id)+" " +  //加班下班
			" ) tableF ON tableA.員工工號 = tableF.員工工號 AND tableA.考勤日期 = tableF.考勤日期 " +
			/*
			" LEFT JOIN " +
			" ( " +
			" SELECT EHF010105T0_02 AS EMP_ID, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') AS SCHEDULE_DATE, " +
			" IFNULL(EHF010105T1_03, false) AS HOLIDAY_FLAG, " +
			" IFNULL(EHF010105T1_04, -1) AS CLASS_NO, " +
			" IFNULL(EHF010100T0_NFLG, false) AS NOON_FLAG, " +
			" IFNULL(EHF010102T0_04, false) AS OKEY_SW, " +
			" IFNULL(EHF010102T0_12, false) AS OSW " +
			" FROM EHF010105T1 " +
			" LEFT JOIN EHF010105T0 ON EHF010105T0_01 = EHF010105T1_01 " +
			" LEFT JOIN EHF010100T0 ON EHF010100T0_01 = EHF010105T1_04 " +
			" LEFT JOIN EHF010102T0 ON EHF010102T0_01 = EHF010100T0_12 " +
			" WHERE 1=1 " +
			" AND EHF010105T0_02 = '"+employee_id+"' " +  //員工工號
			" AND EHF010105T1_02 BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //排班日期
			" AND EHF010105T1_06 = '"+comp_id+"' " +  //公司代碼  
			" ) schedule ON tableA.員工工號 = schedule.EMP_ID AND tableA.考勤日期 = schedule.SCHEDULE_DATE " +
			*/
			" WHERE 1=1 " ;
			//異常flag
			if(error_flag){
				sql += " AND ( tableA.是否異常 = true " +
					   " OR ( schedule.NOON_FLAG AND (tableB.是否異常 = true OR tableC.是否異常 = true) ) " ;
//					   if(ems_tools.hasNoonRecord(classMap)){
//						   sql += " OR tableB.是否異常 = true OR tableC.是否異常 = true ";
//					   }
				sql += " OR tableD.是否異常 = true " +
					   " OR ( (schedule.OKEY_SW AND schedule.OSW) AND ( tableE.是否異常  = true OR tableF.是否異常  = true) ) " ;
//					   if(ems_tools.hasOverTime(classMap)){
//						   sql += " OR tableE.是否異常  = true OR tableF.是否異常  = true ";
//					   }
				sql += " ) ";  
			}
			//假日 flag
			if(ha_flag){
				
				
			}
			sql += " ORDER BY tableA.部門代號, tableA.員工工號, tableA.考勤日期 ";
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			//System.out.println(sql);
			while(rs.next()){
				
				Map tempMap = new HashMap();
				tempMap.put("部門代號", rs.getString("部門代號"));
				tempMap.put("員工工號", rs.getString("員工工號"));
				tempMap.put("考勤日期", rs.getString("考勤日期"));
				tempMap.put("上班考勤類型", rs.getString("上班考勤類型"));
				tempMap.put("上班是否異常", rs.getBoolean("上班是否異常"));
				tempMap.put("上班是否已修正", rs.getBoolean("上班是否已修正"));
				tempMap.put("上班刷卡日期時間", rs.getString("上班刷卡日期時間"));
				tempMap.put("上班刷卡狀態", rs.getString("上班刷卡狀態"));
				tempMap.put("上班修正來源", rs.getString("上班修正來源"));
				tempMap.put("上午下班考勤類型", rs.getString("上午下班考勤類型"));
				tempMap.put("上午下班是否異常", rs.getBoolean("上午下班是否異常"));
				tempMap.put("上午下班是否已修正", rs.getBoolean("上午下班是否已修正"));
				tempMap.put("上午下班刷卡日期時間", rs.getString("上午下班刷卡日期時間"));
				tempMap.put("上午下班刷卡狀態", rs.getString("上午下班刷卡狀態"));
				tempMap.put("上午下班修正來源", rs.getString("上午下班修正來源"));
				tempMap.put("下午上班考勤類型", rs.getString("下午上班考勤類型"));
				tempMap.put("下午上班是否異常", rs.getBoolean("下午上班是否異常"));
				tempMap.put("下午上班是否已修正", rs.getBoolean("下午上班是否已修正"));
				tempMap.put("下午上班刷卡日期時間", rs.getString("下午上班刷卡日期時間"));
				tempMap.put("下午上班刷卡狀態", rs.getString("下午上班刷卡狀態"));
				tempMap.put("下午上班修正來源", rs.getString("下午上班修正來源"));
				tempMap.put("下班考勤類型", rs.getString("下班考勤類型"));
				tempMap.put("下班是否異常", rs.getBoolean("下班是否異常"));
				tempMap.put("下班是否已修正", rs.getBoolean("下班是否已修正"));
				tempMap.put("下班刷卡日期時間", rs.getString("下班刷卡日期時間"));
				tempMap.put("下班刷卡狀態", rs.getString("下班刷卡狀態"));
				tempMap.put("下班修正來源", rs.getString("下班修正來源"));
				tempMap.put("加班上班考勤類型", rs.getString("加班上班考勤類型"));
				tempMap.put("加班上班是否異常", rs.getBoolean("加班上班是否異常"));
				tempMap.put("加班上班是否已修正", rs.getBoolean("加班上班是否已修正"));
				tempMap.put("加班上班刷卡日期時間", rs.getString("加班上班刷卡日期時間"));
				tempMap.put("加班上班刷卡狀態", rs.getString("加班上班刷卡狀態"));
				tempMap.put("加班上班修正來源", rs.getString("加班上班修正來源"));
				tempMap.put("加班下班考勤類型", rs.getString("加班下班考勤類型"));
				tempMap.put("加班下班是否異常", rs.getBoolean("加班下班是否異常"));
				tempMap.put("加班下班是否已修正", rs.getBoolean("加班下班是否已修正"));
				tempMap.put("加班下班刷卡日期時間", rs.getString("加班下班刷卡日期時間"));
				tempMap.put("加班下班刷卡狀態", rs.getString("加班下班刷卡狀態"));
				tempMap.put("加班下班修正來源", rs.getString("加班下班修正來源"));
				//tempMap.put("是否為休假日", rs.getBoolean("是否為休假日"));
				//tempMap.put("班別序號", rs.getInt("班別序號"));
				
				dayattlist.add(tempMap);
			}
			rs.close();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dayattlist;
	}
	/**
	 * 取得考勤資料SQL語法
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @param card_status
	 * @param comp_id
	 * @return
	 */
	protected String getDayAttSQL( String employee_id, String start_date, String end_date, String card_status, String comp_id ){
		
		String sql = "";
		
		try{
			sql += "" +
			" SELECT C.EHF010100T6_02 AS 部門代號, B.EHF020403T1_01 AS 員工工號, " +
			" A.EHF020401T0_05 AS 考勤日期, " +
			" DATE_FORMAT(EHF020401T0_07, '%H點%i分') AS 刷卡時間, " +
			" DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d %H:%i:%s') AS 刷卡日期時間, " +
			" A.EHF020401T0_08 AS 刷卡狀態, A.EHF020401T0_09_DESC AS 狀態說明, " +
			" A.EHF020401T0_09 AS 是否異常, A.EHF020401T0_FIX AS 是否已修正, " +
			" A.EHF020401T0_FDS AS 修正來源, " +
			" '考勤資料' AS 考勤類型 " +
			" FROM EHF020401T0 A " +
			" LEFT JOIN EHF020403T1 B ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +//感應卡明細
			//" LEFT JOIN EHF010100T0 C ON EHF010100T0_01 = EHF020401T0_04 AND EHF010100T0_11 = EHF020401T0_11 " +//班別資料
			" LEFT JOIN EHF020403T0 D ON EHF020403T0_01 = EHF020403T1_01 AND EHF020403T0_04 = EHF020401T0_11 " +//感應卡設定資料
			" LEFT JOIN EHF010100T6 C ON EHF010100T6_01 = EHF020401T0_02_EMP AND HR_CompanySysNo = EHF020401T0_11 " +//職務現況明細
			" WHERE 1=1 " +
			" AND EHF020401T0_08 = '"+card_status+"' " +  //打卡狀態
			" AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_05 BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //考勤日期區間  
			" AND EHF020401T0_11 = '"+comp_id+"' " +  //公司代碼
			" UNION " + 
			" SELECT BB.EHF010100T6_02 AS 部門代號, AA.EHF020406T0_01 AS 員工工號, " +
			" AA.EHF020406T0_04 AS 考勤日期, " +
			" '' AS 刷卡時間, " +
			" DATE_FORMAT(EHF020406T0_04_DTE, '%Y/%m/%d %H:%i:%s') AS 刷卡日期時間, " +
//			" '' AS 刷卡日期時間, " +
			" AA.EHF020406T0_05 AS 刷卡狀態, " +
			" CASE AA.EHF020406T0_05 WHEN 1 THEN AA.EHF020406T0_06 " +
			"                        WHEN 2 THEN AA.EHF020406T0_06 END  AS 狀態說明, " +
			" AA.EHF020406T0_05_FLG AS 是否異常, AA.EHF020406T0_FIX AS 是否已修正, " +
			" AA.EHF020406T0_FDS AS 修正來源, " +
			" '未打卡資料' AS 考勤類型 " +
			" FROM EHF020406T0 AA " +
			//" LEFT JOIN EHF010100T0 BB ON EHF010100T0_01 = EHF020406T0_03 AND EHF010100T0_11 = EHF020406T0_07 " +
			" LEFT JOIN EHF020403T0 CC ON EHF020403T0_01 = EHF020406T0_01 AND EHF020403T0_04 = EHF020406T0_07 " +
			" LEFT JOIN EHF010100T6 BB ON EHF010100T6_01 = EHF020406T0_01 AND HR_CompanySysNo = EHF020406T0_07 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_05 = '"+card_status+"' " + //打卡狀態
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_04 BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //考勤日期區間 
			" AND EHF020406T0_07 = '"+comp_id+"' " ;  //公司代碼
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sql;
	}
	
	/**
	 * 轉換Calendar to String  format:yyyy/MM/dd
	 */
	protected String CalendarToString( Calendar cal ){
		
		String return_date = ""; 
		
		try{
			return_date = tools.covertDateToString(cal.getTime(), "yyyy/MM/dd");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_date;
	}
	
	/**
	 * 轉換 String to Calendar format:yyyy/MM/dd HH:mm:ss
	 * @param datetime
	 * @return
	 */
	protected Calendar datetimeToCalendar( String datetime ){
		
		Calendar cal = null; 
		
		try{
			cal = tools.covertStringToCalendar( datetime, "yyyy/MM/dd HH:mm:ss");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	/**
	 * 計算兩個Calendar之間的時數, 並使用最低計薪時數進行修正, 不足最低計薪時數的部分則捨去
	 * @param begin_cal
	 * @param end_cal
	 * @return
	 */
	protected float caculateDiffHourWithFIX(Calendar begin_cal, Calendar end_cal){
		
		float hour = 0;
		float min_pay_hour = 0;
		int times = 0;
		
		try{
			//計算兩個Calendar之間的時數
			hour = tools.caculateDiffHour(begin_cal, end_cal);
			
			//取得最低付薪時數
			min_pay_hour = this.sta_min_pay_hour;
			
			//修正時數, 只取最低付薪時數的倍數
			times = (int) (hour/min_pay_hour);
			
			//計算時數
			hour = min_pay_hour * times;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return hour;
	}
	
	/**
	 *  取得員工的時薪
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected int getEmpHourPay(Connection conn, String employee_id, String comp_id){
		
		EMS_SalaryTools ems_sal_tools = EMS_SalaryTools.getInstance();
		int hour_pay = 0;
		
		try{
			hour_pay = ems_sal_tools.getEmpHourPay(conn, 1, employee_id, this.sta_costyymm, this.sta_salyymm, comp_id);
			
		}catch(Exception e){
			System.out.println("取得員工的時薪  EMS_OATools.getEmpHourPay()時,發生錯誤");
			e.printStackTrace();
		}
		
		return hour_pay;
	}
	
	/**
	 * 取得員工的日薪
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected int getEmpDayPay(Connection conn, String employee_id, String comp_id){
		
		EMS_SalaryTools ems_sal_tools = EMS_SalaryTools.getInstance();
		int day_pay = 0;
		
		try{
			day_pay = ems_sal_tools.getEmpDayPay(conn, employee_id, this.sta_costyymm, this.sta_salyymm, comp_id);
			
		}catch(Exception e){
			System.out.println("取得員工的日薪  EMS_OATools.getEmpDayPay()時,發生錯誤");
			e.printStackTrace();
		}
		
		return day_pay;
	}
	
	/**
	 * 檢查人員清單
	 * @param conn
	 * @param processlist
	 * @param comp_id
	 * @return
	 */
	protected List chkProcessList( Connection conn, List processlist, String comp_id ){
		
		try{
			Iterator it = processlist.iterator();
			
			while(it.hasNext()){
				Map tempMap = (Map) it.next();
				
				//檢查人員薪資主檔是否啟用
				if(!this.chkSalaryEnabled( conn,  (String) tempMap.get("EMPLOYEE_ID"), comp_id )){
					it.remove();
				}
			}
			
		}catch(Exception e){
			System.out.println("檢查人員清單 EMS_OATools.chkProcessList()時,發生錯誤");
			e.printStackTrace();
		}
		
		return processlist;
	}
	
	/**
	 * 檢查員工薪資主檔是否啟用
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected boolean chkSalaryEnabled( Connection conn, String employee_id, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			String sql = "" +
			" SELECT EHF030200T0_01, EHF030200T0_02 " +
			" FROM EHF030200T0 " +
			" WHERE 1=1 " +
			" AND EHF030200T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF030200T0_08 = '1'  " +  //已啟用
			" AND EHF030200T0_13 = '"+comp_id+"' ";  //公司代碼
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//啟用
				chk_flag = true;
			}else{
				//未啟用
				chk_flag = false;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			System.out.println("檢查人員薪資主檔是否啟用 EMS_OATools.chkSalaryEnabled()時,發生錯誤");
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 依據員工工號 考勤日期  找出當天是否有加班紀錄
	 * @param conn
	 * @param class_in_cal 
	 * @param date
	 * @param employee_id
	 * @param comp_id
	 * @param type  <=  為提前加班   >=為加班
	 * @return
	 */
	private Map getOvertimeHour(Connection conn,  Calendar class_in_cal, String date, String employee_id, String comp_id,String type) {
		//薪資計算明細資料查詢
		Map return_Map = new HashMap();
		try {
			String sql = "" +
			" SELECT " +
			" EHF020802T0_04 AS 考勤日期, " +
			" DATE_FORMAT(EHF020802T0_05, '%Y/%m/%d %H:%i:%s') AS START_TIME, " +
			" DATE_FORMAT(EHF020802T0_06, '%Y/%m/%d %H:%i:%s') AS END_TIME, " +
			" EHF020802T0_07 AS 加班時數, " +
			" EHF020802T0_07_DE AS 內扣時數 " +
			" FROM EHF020802T0 " +
			" WHERE 1=1 " +
			" AND EHF020802T0_02  = '"+employee_id+"'" +  //員工工號
			" AND EHF020802T0_04  = '"+date+"' " +  //考勤日期
			" AND EHF020802T0_08  = '"+comp_id+"' " +  //公司代碼
			" AND EHF020802T0_06 "+type+"'"+tools.covertDateToString(class_in_cal.getTime(),"yyyy/MM/dd HH:mm:ss")+"'";
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
		
			if(rs.next()){
				return_Map.put("加班開始時間", rs.getString("START_TIME"));
				return_Map.put("加班結束時間", rs.getString("END_TIME"));
				return_Map.put("deducted_hour", rs.getString("內扣時數"));
				return_Map.put("hour", rs.getString("加班時數"));
			}
			rs.close();
			stmt.close();
		}catch(Exception E){
			E.printStackTrace();
		}
		
		
		// TODO Auto-generated method stub
		return return_Map;
	}
	
	/**
//	 * 依據員工工號、考勤日期，找出加班記錄
	 * @param conn
	 * @param class_in_cal
	 * @param date
	 * @param employee_id
	 * @param comp_id
	 * @param type
	 * @return
	 */
	private Map getOvertimeFormRecording(Connection conn, Calendar class_in_cal, String date, String employee_id, String comp_id, String type) {
		
		Map return_Map = new HashMap();
		
		try {
			String sql = "" +
			" SELECT " +
			" EHF020801T0_04 AS 考勤日期, " +
			" DATE_FORMAT(EHF020801T0_05, '%Y/%m/%d %H:%i:%s') AS START_TIME, " +
			" DATE_FORMAT(EHF020801T0_06, '%Y/%m/%d %H:%i:%s') AS END_TIME, " +
			" DATE_FORMAT(EHF020801T0_05_BRK, '%Y/%m/%d %H:%i:%s') AS BRK_START_TIME, " +
			" DATE_FORMAT(EHF020801T0_06_BRK, '%Y/%m/%d %H:%i:%s') AS BRK_END_TIME, " +
			" EHF020801T0_07 AS 加班時數, " +
			" EHF020801T0_07_DE AS 內扣時數 " +
			" FROM EHF020801T0 " +
			" WHERE 1=1 " +
			" AND EHF020801T0_02  = '"+employee_id+"'" +  //員工工號
			" AND EHF020801T0_04  = '"+date+"' " +  //考勤日期
			" AND EHF020801T0_08  = '"+comp_id+"' " +  //公司代碼
			" AND EHF020801T0_06 "+type+"'"+tools.covertDateToString(class_in_cal.getTime(),"yyyy/MM/dd HH:mm:ss")+"'";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
		
			if(rs.next()){
				return_Map.put("加班開始時間", rs.getString("START_TIME"));
				return_Map.put("加班結束時間", rs.getString("END_TIME"));
				return_Map.put("加班休息開始時間", rs.getString("BRK_START_TIME"));
				return_Map.put("加班休息結束時間", rs.getString("BRK_END_TIME"));
				return_Map.put("deducted_hour", rs.getString("內扣時數"));
				return_Map.put("ov_hour", rs.getString("加班時數"));
			}
			rs.close();
			stmt.close();
			
		}catch(Exception E){
			E.printStackTrace();
		}
						
		return return_Map;
	}
	
	/**
	 * 過濾離職員工(依照薪資年月判定是否需要計算薪資)20140917  新增By Alvin
	 * @param userList 員工總清單
	 * @param determine_date 薪資年月
	 *//*
	protected void getEmployee_idList( List userList, String start_date , String end_date ,Map empInfMap){
		
		Map tempMap= new HashMap();	
		Calendar start_cal = ems_tools.covertStringToCalendar(start_date);  //津貼計算開始日期
		Calendar end_cal = ems_tools.covertStringToCalendar(end_date);  //津貼計算結束日期
		boolean chk_run_flag = true;//時間開關
		boolean chk_Show_flag = false;//是否列入員工清單
		
		
		Iterator it = userList.iterator();
		
		while(it.hasNext()){
			tempMap = (Map) it.next();
			//取得EMS-Flow員工基本資料Map
			empInfMap = ems_tools.getEmpInfMapByEmpId(this.sta_user_id,(String) tempMap.get("EMPLOYEE_ID"), this.sta_comp_id);			
			//計算迴圈建立
			chk_run_flag = true;
			while(chk_run_flag){
				if(start_cal.equals(end_cal)){
					chk_run_flag = false;
				}
				String cur_date = this.CalendarToString(start_cal);  //當前計算津貼的考勤日期
				if(this.determineGenerateSchedulingData(empInfMap, cur_date)){
					chk_Show_flag=true;
				}
				
				if(chk_Show_flag){
					//只要有一天回傳為true，就要列入清單。
					chk_run_flag = false;
					continue;	
				}
				//日期加一天
				start_cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			if(chk_Show_flag){
				//只要有一天回傳為true，就要列入清單。
				continue;	
			}else{
				it.remove();
			}
		}
		
	}
	
	*//**
	 * 判斷該員工是否需要計算薪資(依據是否在職進行判斷)20140917  新增By Alvin
	 * @param user
	 * @param determine_date
	 * @return
	 *//*
	protected boolean determineGenerateSchedulingData( Map user, String determine_date ){
		//ADD by Alvin  20140917
		boolean chk_flag = false;
		
		try{
			Calendar determine_cal = ems_tools.covertStringToCalendar(determine_date);
			Calendar office_date_cal = null;  //到職日期
			Calendar reapoint_date_cal = null;  //復職日期
			Calendar stop_beg_date_cal = null;  //留職停薪起日
			Calendar stop_end_date_cal = null;  //留職停薪迄日
			Calendar out_date_cal = null;  //離職日期
			
			Calendar att_start_cal = null;  //考勤計算起始日期
			Calendar att_end_cal = null;  //考勤計算結束日期

			//到職日期
			if(user.get("OFFICE_DATE") != null){
				office_date_cal = Calendar.getInstance();
				office_date_cal.setTime((Date)user.get("OFFICE_DATE"));
				att_start_cal = (Calendar)office_date_cal.clone();
			}
			//復職日期
			if(user.get("REAPOINT_DATE") != null){
				reapoint_date_cal = Calendar.getInstance();
				reapoint_date_cal.setTime((Date)user.get("REAPOINT_DATE"));
				att_start_cal = (Calendar)reapoint_date_cal.clone();
			}
			//留職停薪起日
			if(user.get("STOP_BEG_DATE") != null){
				stop_beg_date_cal = Calendar.getInstance();
				stop_beg_date_cal.setTime((Date)user.get("STOP_BEG_DATE"));
				att_end_cal = (Calendar)stop_beg_date_cal.clone();
			}
			//留職停薪迄日
			if(user.get("STOP_END_DATE") != null){
				stop_end_date_cal = Calendar.getInstance();
				stop_end_date_cal.setTime((Date)user.get("STOP_END_DATE"));
				att_start_cal = (Calendar)stop_end_date_cal.clone();
			}
			//離職日期
			if(user.get("OUT_DATE") != null){
				out_date_cal = Calendar.getInstance();
				out_date_cal.setTime((Date)user.get("OUT_DATE"));
				att_end_cal = (Calendar)out_date_cal.clone();
			}
			
			//判斷要產生考勤資料的日期是否要區間內
			if(att_start_cal != null && determine_cal.compareTo(att_start_cal) >= 0){
				if(att_end_cal == null){
					//需要產生考勤資料
					chk_flag = true;
				}else if(determine_cal.compareTo(att_end_cal) < 0){
					//需要產生考勤資料
					chk_flag = true;
				}
			}else{
				//未填寫到職日期不計算考勤
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
*/
	/**
	 * 取得個人的津貼資料
	 * @param conn
	 * @param class_no
	 * @param comp_id
	 * @return
	 */
	public List getPersonalAllowanceData(Connection conn, String class_no, String comp_id ){
		
		List allowancelist = new ArrayList();
		
		try{
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT " +
			" EHF030200T1_01 AS ALLOWANCE_NO, IFNULL(EHF030200T1_05,'') AS class_no , A . *" +
			" FROM EHF030200T1 " +
			" LEFT JOIN EHF010101T0 A ON EHF030200T1_02 = EHF010101T0_01 AND EHF030200T1_04 = EHF010101T0_23" +
			" WHERE 1=1 " +
			" AND EHF030200T1_06 = '2'" +  //員工工號  修改於 20131106 BY 賴泓錡
			" AND EHF030200T1_01 = '"+class_no+"' " +  //員工工號  修改於 20131106 BY 賴泓錡
			" AND EHF030200T1_04 = '"+comp_id +"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				EHF010101M0F bean = new EHF010101M0F();
				bean.setEHF010101T0_01(rs.getString("EHF010101T0_01"));  //津貼資料序號
				bean.setEHF010101T0_02(rs.getString("EHF010101T0_02"));  //津貼名稱
				bean.setEHF010101T0_03(rs.getString("EHF010101T0_03"));  //所得稅別
				bean.setEHF010101T0_04(rs.getString("EHF010101T0_04"));  //津貼類別
				bean.setEHF010101T0_05(rs.getString("EHF010101T0_05"));  //是否啟用
				bean.setEHF010101T0_06(rs.getString("EHF010101T0_06"));  //津貼金額
				bean.setEHF010101T0_06_FLG(rs.getString("EHF010101T0_06_FLG"));  //是否依據一日基本薪給津貼
				bean.setEHF010101T0_06_RATE(rs.getString("EHF010101T0_06_RATE"));  //一日基本薪加成比率
				bean.setEHF010101T0_07(rs.getString("EHF010101T0_07"));  //是否有津貼條件
				bean.setEHF010101T0_08(rs.getString("EHF010101T0_08"));  //津貼條件類型
				bean.setEHF010101T0_09(rs.getString("EHF010101T0_09"));  //津貼條件時間
				bean.setEHF010101T0_10(rs.getString("EHF010101T0_10"));  //津貼條件時數
				bean.setEHF010101T0_11(rs.getString("EHF010101T0_11"));  //津貼特殊條件Key
				bean.setEHF010101T0_12(rs.getString("EHF010101T0_12"));  //是否有津貼加成
				bean.setEHF010101T0_13(rs.getString("EHF010101T0_13"));  //津貼加成類型
				bean.setEHF010101T0_14(rs.getString("EHF010101T0_14"));  //津貼加成比例
				bean.setEHF010101T0_15(rs.getString("EHF010101T0_15"));  //津貼加成固定金額
				bean.setEHF010101T0_16(rs.getString("EHF010101T0_16"));  //津貼加成職等條件
				bean.setEHF010101T0_17(rs.getString("EHF010101T0_17"));  //是否有津貼加成條件
				bean.setEHF010101T0_18(rs.getString("EHF010101T0_18"));  //津貼加成條件類型
				bean.setEHF010101T0_19(rs.getString("EHF010101T0_19"));  //津貼加成條件時間
				bean.setEHF010101T0_20(rs.getString("EHF010101T0_20"));  //津貼加成條件時數
				bean.setEHF010101T0_21(rs.getString("EHF010101T0_21"));  //津貼加成特殊條件Key
				bean.setEHF010101T0_22(rs.getString("EHF010101T0_22"));  //備註
				bean.setEHF010101T0_24(rs.getString("EHF010101T0_24"));  //是否參考加班單
				bean.setClass_no(rs.getString("class_no"));  //使用班別----->可以為空值
				allowancelist.add(bean);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("取得班別津貼資料發生錯誤!! "+e);
		}
		
		return allowancelist;
	}
	
	/**
	 * 取得員工的加班費規則資料
	 * @param conn
	 * @param class_no
	 * @param comp_id
	 * @return
	 */
	public List getemployeeOvertimeData(Connection conn, String employeeId, String comp_id ){
		//取得員工使用的加班費規則
		List overtimelist = new ArrayList();
		try{
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT " +
			" EHF030107T0.*" +
			" FROM EHF030200T0 " +
			" LEFT JOIN EHF030107T0 ON EHF030200T0_18 = EHF030107T0_02 AND EHF030200T0_13 = EHF030107T0_10 " +
			" WHERE 1=1 " +
			" AND EHF030200T0_01  = '"+employeeId+"'" +  //班別序號
			" AND EHF030200T0_18 <> '' " +	//員工加班費規則(代號)
			" AND EHF030200T0_13  = '"+comp_id+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){

				EHF030107M0F bean = new EHF030107M0F();
				bean.setEHF030107T0_01(rs.getString("EHF030107T0_01"));  //加班費規則序號
				bean.setEHF030107T0_02(rs.getString("EHF030107T0_02"));  //加班費規則代號
				bean.setEHF030107T0_03(rs.getString("EHF030107T0_03"));  //加班費規則名稱
				bean.setEHF030107T0_04(rs.getString("EHF030107T0_04"));  //加班(時數一)
				bean.setEHF030107T0_05(rs.getString("EHF030107T0_05"));  //加班(加成率一)
				bean.setEHF030107T0_06(rs.getString("EHF030107T0_06"));  //加班(時數二)
				bean.setEHF030107T0_07(rs.getString("EHF030107T0_07"));  //加班(加成率二)
				bean.setEHF030107T0_08(rs.getString("EHF030107T0_08"));  //假日加班加成率
				bean.setEHF030107T0_11(rs.getString("EHF030107T0_11"));  //例假日加班加成率
				bean.setEHF030107T0_09(rs.getString("EHF030107T0_09"));  //是否啟用
			
				overtimelist.add(bean);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("取得班別加班資料發生錯誤!! "+e);
		}
		
		return overtimelist;
	}
}
