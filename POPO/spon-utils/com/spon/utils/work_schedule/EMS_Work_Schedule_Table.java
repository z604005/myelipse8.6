package com.spon.utils.work_schedule;

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

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.utils.util.BA_TOOLS;

/**
 * EMS 排班表元件
 * 
 * @version 1.0
 * @created 10-四月-2006 下午 09:16:47
 */
public class EMS_Work_Schedule_Table {
	
	BA_TOOLS tools = BA_TOOLS.getInstance();
	//private static EMS_Work_Schedule_Table ems_workschedule_table_tools;
	//private static String sta_user_id;
	private static String sta_user_name;
	private static String sta_comp_id;
	
	/*
	public static EMS_Work_Schedule_Table getInstance(String user_name, String comp_id) {
        if (ems_workschedule_table_tools == null){
        	ems_workschedule_table_tools = new EMS_Work_Schedule_Table(user_name, comp_id);
        }else{
        	sta_user_name = user_name;
    		sta_comp_id = comp_id;
        }
         return ems_workschedule_table_tools;
    }
    */
	
	public EMS_Work_Schedule_Table(String user_name, String comp_id){
		EMS_Work_Schedule_Table.sta_user_name = user_name;
		EMS_Work_Schedule_Table.sta_comp_id = comp_id;
	}
	
	/*
	public EMS_Work_Schedule_Table(String user_id, String user_name, String comp_id){
		this.sta_user_id = user_id;
		this.sta_user_name = user_name;
		this.sta_comp_id = comp_id;
	}
	*/

	public void finalize() throws Throwable {

	}
	
	/**
	 * 取得排班表序號
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @return
	 */
	public int getScheduleNo( Connection conn, String employee_id, int year, int month ){
		
		int schedult_no = -1;
		
		try{
			String sql = "" +
			" SELECT EHF010105T0_01 AS SCHEDULE_NO " +
			" FROM EHF010105T0 " +
			" WHERE 1=1 " +
			" AND EHF010105T0_02 = '"+employee_id+"' " +  //員工工號
			" AND EHF010105T0_03 = "+year+" " +  //年度
			" AND EHF010105T0_04 = "+month+" " +  //月份
			" AND EHF010105T0_06 = '"+EMS_Work_Schedule_Table.sta_comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//排班表存在, 取得排班表序號
				schedult_no = rs.getInt("SCHEDULE_NO");
			}else{
				//建立排班表
				schedult_no = this.createSchedule(conn, employee_id, year, month, "");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return schedult_no;
	}
	
	/**
	 * 建立排班表
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @param comment
	 * @return
	 */
	public int createSchedule( Connection conn, String employee_id, int year, int month,
							   String comment ){
		
		try{
			//新增排班表年月資料
			String sql = "" +
			" INSERT INTO ehf010105t0 " +
			" (EHF010105T0_02, EHF010105T0_03, EHF010105T0_04, EHF010105T0_05, " +
			" EHF010105T0_06, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ?, 1, NOW(), NOW()) ";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, employee_id);  //員工工號
			indexid++;
			p6stmt.setInt(indexid, year);  //排班表年度(西元年)
			indexid++;
			p6stmt.setInt(indexid, month);  //排班表月份
			indexid++;
			p6stmt.setString(indexid, comment);  //備註
			indexid++;
			p6stmt.setString(indexid, EMS_Work_Schedule_Table.sta_comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, EMS_Work_Schedule_Table.sta_user_name);  //建立人員
			indexid++;
			p6stmt.setString(indexid, EMS_Work_Schedule_Table.sta_user_name);  //修改人員
			indexid++;

			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.getScheduleNo(conn, employee_id, year, month);
	}
	
	/**
	 * 增加排班表日期資料
	 * @param conn
	 * @param schedule_no
	 * @param schedule_date
	 * @param holiday_flag
	 * @param class_no
	 * @param comment
	 * @param comp_id
	 * @return
	 */
	public boolean addSchedule( Connection conn, int schedule_no, String schedule_date, boolean holiday_flag,
								int class_no, String comment, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			//檢核排班表序號
			if( schedule_no < 1 ){
				return false;
			}
			
			//檢核欲新增的班表資料是否已存在
			if(!this.chkExistSchedule(conn, schedule_no, schedule_date)){
				//新增排班表日期資料
				String sql = "" +
				" INSERT INTO ehf010105t1 " +
				" (EHF010105T1_01, EHF010105T1_02, EHF010105T1_03, EHF010105T1_04, " +
				" EHF010105T1_05, EHF010105T1_06, EHF010105T1_07," +
				" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
				" VALUES ( ?, ?, ?, ?, ?, ?,?, ?, ?, 1, NOW(), NOW()) ";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,
						pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setInt(indexid, schedule_no);  //員工排班表序號
				indexid++;
				p6stmt.setString(indexid, schedule_date);  //排班表日期
				indexid++;
				p6stmt.setBoolean(indexid, holiday_flag);  //是否為休假日
				indexid++;
				if(holiday_flag){
					//是休假日時，班別為-1 2014/06/13  By賴泓錡
					p6stmt.setInt(indexid, -1);  //班別序號
				}else{
					p6stmt.setInt(indexid, class_no);  //班別序號
				}
				indexid++;

				p6stmt.setString(indexid, comment);  //備註
				indexid++;
				p6stmt.setString(indexid, comp_id);  //公司代碼
				indexid++;
				
				if(holiday_flag){
					//是休假日時，班別為-1 2014/06/13  By賴泓錡
					p6stmt.setString(indexid, this.HolidayCategory(conn, schedule_date, comp_id));  //休假類別
				}else{
					p6stmt.setString(indexid, "");  //休假類別
				}
				indexid++;
				p6stmt.setString(indexid, EMS_Work_Schedule_Table.sta_user_name);  //建立人員
				indexid++;
				p6stmt.setString(indexid, EMS_Work_Schedule_Table.sta_user_name);  //修改人員
				indexid++;
				
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();			
				
				chk_flag = true;
			}
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 增加預設排班表日期資料
	 * @param conn
	 * @param schedule_no
	 * @param schedule_date
	 * @param holiday_flag
	 * @param class_no
	 * @param comment
	 * @param comp_id
	 * @return
	 */
	public boolean Default_addSchedule( Connection conn, int schedule_no, String schedule_date, boolean holiday_flag,int class_no, String comment, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			//檢核排班表序號
			if( schedule_no < 1 ){
				return false;
			}

				//新增排班表日期資料
				String sql = "" +
				" INSERT INTO ehf010105t2 " +
				" (EHF010105T2_01, EHF010105T2_02, EHF010105T2_03, EHF010105T2_04, " +
				" EHF010105T2_05, EHF010105T2_06, EHF010105T2_07," +
				" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
				" VALUES ( ?, ?, ?, ?, ?, ?,?, ?, ?, 1, NOW(), NOW()) ";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,
						pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setInt(indexid, schedule_no);  //員工排班表序號
				indexid++;
				p6stmt.setString(indexid, schedule_date);  //排班表日期
				indexid++;
				p6stmt.setBoolean(indexid, holiday_flag);  //是否為休假日
				indexid++;
				if(holiday_flag){
					//是休假日時，班別為-1 2014/06/13  By賴泓錡
					p6stmt.setInt(indexid, -1);  //班別序號
				}else{
					p6stmt.setInt(indexid, class_no);  //班別序號
				}
				indexid++;

				p6stmt.setString(indexid, comment);  //備註
				indexid++;
				p6stmt.setString(indexid, comp_id);  //公司代碼
				indexid++;
				
				if(holiday_flag){
					//是休假日時，班別為-1 2014/06/13  By賴泓錡
					p6stmt.setString(indexid, this.HolidayCategory(conn, schedule_date, comp_id));  //休假類別
				}else{
					p6stmt.setString(indexid, "");  //休假類別
				}
				indexid++;
				p6stmt.setString(indexid, EMS_Work_Schedule_Table.sta_user_name);  //建立人員
				indexid++;
				p6stmt.setString(indexid, EMS_Work_Schedule_Table.sta_user_name);  //修改人員
				indexid++;
				
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();			
				
				
				chk_flag = true;

		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 檢核當天是否為休假日，如是，則回傳休假類別
	 * @param conn
	 * @param date 西元年月日
	 * @param comp_id
	 * @return
	 */
	public String HolidayCategory(Connection conn, String date, String comp_id){
		
		String chk_flag = "";
		
		try{
			
			String sql = "" +
			" SELECT EHF000500T0_01 ,EHF000500T0_04" +
			" FROM EHF000500T0 " +
			" WHERE 1=1 " +
			" AND EHF000500T0_03 = '"+(Integer.parseInt((date.split("/"))[0])-1911)+"' " +  //年度
			" AND '"+date+"' BETWEEN EHF000500T0_05 AND EHF000500T0_06 " +  //休假日期區間
			" AND EHF000500T0_11 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				chk_flag = (String)rs.getString("EHF000500T0_04");
			}else{
				chk_flag="false";
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	/**
	 * 檢核排班表日期資料是否已存在
	 * @param conn
	 * @param schedule_no
	 * @param schedule_date
	 * @return
	 */
	public boolean chkExistSchedule( Connection conn, int schedule_no, String schedule_date ){
		
		boolean chk_flag = true;
		
		try{
			String sql = "" +
			" SELECT EHF010105T1_01 AS SCHEDULE_NO, " +
			" EHF010105T1_02 AS SCHEDULE_DATE " +
			" FROM EHF010105T1 " +
			" WHERE 1=1 " +
			" AND EHF010105T1_01 = "+schedule_no+" " +  //排班表序號
			" AND EHF010105T1_02 = '"+schedule_date+"' ";  //排班表日期
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				chk_flag = true;
			}else{
				chk_flag = false;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 修改排班表日期資料
	 * @param conn
	 * @param schedule_no
	 * @param schedule_date
	 * @param holiday_flag
	 * @param class_no
	 * @param comment
	 * @param comp_id
	 * @return
	 */
	public boolean editSchedule( Connection conn, int schedule_no, String schedule_date, boolean holiday_flag,
								 int class_no, String comment, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			//檢核排班表序號
			if( schedule_no < 1 ){
				return false;
			}
			
			//判斷排班表日期資料是否存在
			if(!this.chkExistSchedule(conn, schedule_no, schedule_date)){
//				//資料不存在, 改為Create排班表日期資料
				return this.addSchedule(conn, schedule_no, schedule_date, holiday_flag, class_no, comment, comp_id);
			}
			
			//修改排班表日期資料
			String sql = "" +
			" UPDATE ehf010105t1 " +
			" SET " +
			" EHF010105T1_03 = ?, EHF010105T1_04 = ?, " +
			" EHF010105T1_05 = ?, EHF010105T1_07 = ?, " +
			" USER_UPDATE = ?, DATE_UPDATE = NOW() " +
			" WHERE 1=1 " +
			" AND EHF010105T1_01 = ? " +
			" AND EHF010105T1_02 = ? " +
			" AND EHF010105T1_06 = ? ";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setBoolean(indexid, holiday_flag);  //是否為休假日
			indexid++;
			
			
			if(holiday_flag){
				p6stmt.setInt(indexid, -1);  //班別序號
			}else{
				p6stmt.setInt(indexid, class_no);  //班別序號
			}
			indexid++;

			p6stmt.setString(indexid, comment);  //備註
			indexid++;
			
			if(holiday_flag){
				
				//是休假日時，班別為-1 2014/06/13  By賴泓錡
				String HolidayCategory=this.HolidayCategory(conn, schedule_date, comp_id);
				
				if("false".equals(HolidayCategory)){
					p6stmt.setString(indexid,String.valueOf(Math.abs(class_no)));  //休假類別
				}else{
					p6stmt.setString(indexid, this.HolidayCategory(conn, schedule_date, comp_id));  //休假類別
				}
				
				
			}else{
				p6stmt.setString(indexid, "");  //休假類別
			}
			
			indexid++;
			p6stmt.setString(indexid, EMS_Work_Schedule_Table.sta_user_name);  //修改人員
			indexid++;
			p6stmt.setInt(indexid, schedule_no);  //員工排班表序號
			indexid++;
			p6stmt.setString(indexid, schedule_date);  //排班表日期
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			
			
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();			
			
			chk_flag = true;
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 刪除排班表日期資料
	 * @param conn
	 * @param schedule_no
	 * @param schedule_date
	 * @param holiday_flag
	 * @param class_no
	 * @param comment
	 * @param comp_id
	 * @return
	 */
	public boolean delSchedule( Connection conn, int schedule_no, String schedule_date, boolean holiday_flag,
								 int class_no, String comment, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			//檢核排班表序號
			if( schedule_no < 1 ){
				return false;
			}
			
			//刪除排班表日期資料
			String sql = "" +
			" DELETE FROM ehf010105t1 " +
			" WHERE 1=1 " +
			" AND EHF010105T1_01 = ? " +
			" AND EHF010105T1_02 = ? " +
			" AND EHF010105T1_06 = ? ";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setInt(indexid, schedule_no);  //員工排班表序號
			indexid++;
			p6stmt.setString(indexid, schedule_date);  //排班表日期
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;

			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();			
			
			chk_flag = true;
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 取得員工指定日期的排班資料
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	public EMS_Work_ScheduleForm getOneDayInSchedule( Connection conn, String employee_id, String date ){
		
		int schedule_no = -1;
		EMS_Work_ScheduleForm work_schedule = new EMS_Work_ScheduleForm();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		int year = -1;
		int month = -1;
		
		try{
			//取出日期的 年度與月份 In Integer
			Calendar cal_date = tools.covertStringToCalendar(date);
			year = Integer.parseInt(tools.covertDateToString(cal_date.getTime(), "yyyy"));  //年度
			month = Integer.parseInt(tools.covertDateToString(cal_date.getTime(), "MM"));  //月份
			
			//設定預設序號
			work_schedule.setSchedule_no(schedule_no);
			
			//取得排班表序號
			schedule_no = this.getScheduleNo(conn, employee_id, year, month);
			
			//檢核排班表序號
			if( schedule_no < 1 ){
				return work_schedule;
			}
			
			//取得公司班別詳細資訊
			Map compClassMap = EMS_Work_Schedule_TOOLS.getInstance().createCompClassMap(conn, true, EMS_Work_Schedule_Table.sta_comp_id);
			Map classMap = null;
			
			String sql = "" +
			" SELECT EHF010105T0_01 AS SCHEDULE_NO, " +
			" EHF010105T0_02, EHF010105T0_03, EHF010105T0_04, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') AS EHF010105T1_02, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y年%m月%d日') AS EHF010105T1_02_DESC, " +
			" EHF010105T1_03, EHF010105T1_04, EHF010105T1_05, " +
			" IFNULL(tableA.IN_ABNORMAL_FLAG,'') AS 上班異常記號, IFNULL(tableA.IN_ABNORMAL_DESC,'') AS 上班異常說明, " +
			" IFNULL(tableB.OUT_ABNORMAL_FLAG,'') AS 下班異常記號, IFNULL(tableB.OUT_ABNORMAL_DESC,'') AS 下班異常說明, " +
			" IFNULL(tableC.VA_INF,'') AS 假別資訊, " +
			" EHF010105T1.USER_CREATE AS U_CREATE, EHF010105T1.USER_UPDATE AS U_UPDATE, " +
			" EHF010105T1.VERSION AS VER, EHF010105T1.DATE_CREATE AS D_CREATE, " +
			" EHF010105T1.DATE_UPDATE AS D_UPDATE " +
			" FROM EHF010105T0 " +
			" LEFT JOIN EHF010105T1 ON EHF010105T1_01 = EHF010105T0_01 AND DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') = '"+date+"' " +
			//上班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020401T0_05 AS ATT_DATE, " +
			" EHF020401T0_09 AS IN_ABNORMAL_FLAG, " +
			" EHF020401T0_09_DESC AS IN_ABNORMAL_DESC, " +
			" EHF020401T0_11 AS COMP_ID " +
			" FROM EHF020401T0 " +
			" WHERE 1=1 " +
			" AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '1' " +  //上班
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS IN_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS IN_ABNORMAL_DESC, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '1' " +  //上班
			" )tableA ON tableA.ATT_DATE  = DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') " +
			" AND tableA.COMP_ID = EHF010105T0_06 " +
			//下班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020401T0_05 AS ATT_DATE, " +
			" EHF020401T0_09 AS OUT_ABNORMAL_FLAG, " +
			" EHF020401T0_09_DESC AS OUT_ABNORMAL_DESC, " +
			" EHF020401T0_11 AS COMP_ID " +
			" FROM EHF020401T0 " +
			" WHERE 1=1 " +
			" AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '2' " +  //下班
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS OUT_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS OUT_ABNORMAL_DESC, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '2' " +  //下班
			" )tableB ON tableB.ATT_DATE  = DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') " +
			" AND tableB.COMP_ID = EHF010105T0_06 " +
			//假別資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" GROUP_CONCAT(TIME_INF) AS VA_INF " +
			" FROM " +
			" ( " +
			" SELECT " +
			" CASE SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT(EHF020100T0_04,'(',(SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT(EHF020100T0_04,'(',SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1), '天', " +
			" SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5), '時)') " +
			" END AS TIME_INF " +
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 " +
			" AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020200T0_18 = '"+EMS_Work_Schedule_Table.sta_comp_id+"' " +  //公司代碼
			" AND '"+date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10 " +  //日期
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" CASE SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT('出差(',(SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT('出差(',SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1), '天', " +
			" SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5), '時)') " +
			" END AS TIME_INF " +
			" FROM EHF020300T0 " +
			" WHERE 1=1 " +
			" AND EHF020300T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020300T0_21 = '"+EMS_Work_Schedule_Table.sta_comp_id+"' " +  //公司代碼
			" AND '"+date+"' BETWEEN EHF020300T0_09 AND EHF020300T0_10 " +  //日期
			" AND EHF020300T0_20 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '公務' AS TIME_INF " +
			" FROM EHF020600T0 " +
			" WHERE 1=1 " +
			" AND EHF020600T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020600T0_18 = '"+EMS_Work_Schedule_Table.sta_comp_id+"' " +  //公司代碼
			" AND EHF020600T0_09 = '"+date+"' " +  //日期
			" AND EHF020600T0_17 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '未打卡單' AS TIME_INF " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020700T0_15 = '"+EMS_Work_Schedule_Table.sta_comp_id+"' " +  //公司代碼
			" AND EHF020700T0_08 = '"+date+"' " +  //日期
			" AND EHF020700T0_14 = '0006' " +  //表單狀態 = 完成
			" )tableC1 " +
			" WHERE 1=1 " +
			" )tableC ON 1=1 " +
			" WHERE 1=1 " +
			" AND EHF010105T0_01 = "+schedule_no+" " +  //排班表序號
			" AND EHF010105T1_02 IS NOT NULL ";  //排班表日期不可為NULL  
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//排班表日期資料存在
				work_schedule.setSchedule_no(rs.getInt("SCHEDULE_NO"));
				work_schedule.setEmployee_id(rs.getString("EHF010105T0_02"));
				work_schedule.setYear(rs.getInt("EHF010105T0_03"));
				work_schedule.setMonth(rs.getInt("EHF010105T0_04"));
				work_schedule.setDate(rs.getString("EHF010105T1_02"));
				work_schedule.setDate_desc(rs.getString("EHF010105T1_02_DESC"));
				work_schedule.setHoliday_flag(rs.getBoolean("EHF010105T1_03"));
				work_schedule.setClass_no(rs.getInt("EHF010105T1_04"));
				
				//處理班別資訊
				if(compClassMap.containsKey(work_schedule.getClass_no())){
					classMap = (Map)((Map)compClassMap.get(work_schedule.getClass_no())).get("CLASS");
				
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((Float)classMap.get("FULL_WORK_HOURS"));
					work_schedule.setREAL_WORK_HOURS((Float)classMap.get("REAL_WORK_HOURS"));
				}else{
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((float)0);
					work_schedule.setREAL_WORK_HOURS((float)0);
				}
				
				//設定上班考勤、下班考勤、假別資訊
				work_schedule.setIn_abnormal_flag(rs.getBoolean("上班異常記號"));
				work_schedule.setIn_abnormal_desc(rs.getString("上班異常說明"));
				work_schedule.setOut_abnormal_flag(rs.getBoolean("下班異常記號"));
				work_schedule.setOut_abnormal_desc(rs.getString("下班異常說明"));
				work_schedule.setVa_inf(rs.getString("假別資訊"));
				
				work_schedule.setComment(rs.getString("EHF010105T1_05"));
				work_schedule.setUSER_CREATE(rs.getString("U_CREATE"));
				work_schedule.setUSER_UPDATE(rs.getString("U_UPDATE"));
				work_schedule.setVERSION(rs.getInt("VER"));
				work_schedule.setDATE_CREATE(rs.getString("D_CREATE"));
				work_schedule.setDATE_UPDATE(rs.getString("D_UPDATE"));
				
			}else{
				return work_schedule;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return work_schedule;
	}
	
	/**
	 * 取得全公司某一日期的排班表資料
	 * @param conn
	 * @param date
	 * @return
	 */
	/*public List<EMS_Work_ScheduleForm> getOneDayInScheduleForComp( Connection conn, String date ){
		
		List<EMS_Work_ScheduleForm> schedule_list = new ArrayList<EMS_Work_ScheduleForm>();
		EMS_Work_ScheduleForm work_schedule = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		int year = -1;
		int month = -1;
		
		try{
			//取出日期的 年度與月份 In Integer
			Calendar cal_date = tools.covertStringToCalendar(date);
			year = Integer.parseInt(tools.covertDateToString(cal_date.getTime(), "yyyy"));  //年度
			month = Integer.parseInt(tools.covertDateToString(cal_date.getTime(), "MM"));  //月份
			
			//取得公司班別詳細資訊
			Map compClassMap = EMS_Work_Schedule_TOOLS.getInstance().createCompClassMap(conn, true, this.sta_comp_id);
			Map classMap = null;
			
			String sql = "" +
			" SELECT EHF010105T0_01 AS SCHEDULE_NO, " +
			" EHF010105T0_02, EHF010105T0_03, EHF010105T0_04, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') AS EHF010105T1_02, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y年%m月%d日') AS EHF010105T1_02_DESC, " +
			" EHF010105T1_03, EHF010105T1_04, EHF010105T1_05, " +
			" IFNULL(tableA.IN_ABNORMAL_FLAG,'') AS 上班異常記號, IFNULL(tableA.IN_ABNORMAL_DESC,'') AS 上班異常說明, " +
			" IFNULL(tableB.OUT_ABNORMAL_FLAG,'') AS 下班異常記號, IFNULL(tableB.OUT_ABNORMAL_DESC,'') AS 下班異常說明, " +
			" IFNULL(tableC.VA_INF,'') AS 假別資訊, " +
			" EHF010105T1.USER_CREATE AS U_CREATE, EHF010105T1.USER_UPDATE AS U_UPDATE, " +
			" EHF010105T1.VERSION AS VER, EHF010105T1.DATE_CREATE AS D_CREATE, " +
			" EHF010105T1.DATE_UPDATE AS D_UPDATE " +
			" FROM EHF010105T0 " +
			" LEFT JOIN EHF010105T1 ON EHF010105T1_01 = EHF010105T0_01 AND DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') = ? " +  //排班表日期
			//上班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020401T0_05 AS ATT_DATE, " +
			" EHF020401T0_09 AS IN_ABNORMAL_FLAG, " +
			" EHF020401T0_09_DESC AS IN_ABNORMAL_DESC, " +
			" EHF020401T0_02_EMP AS EMP_ID, " +
			" EHF020401T0_11 AS COMP_ID " +
			" FROM EHF020401T0 " +
			" WHERE 1=1 " +
			//" AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '1' " +  //上班
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS IN_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS IN_ABNORMAL_DESC, " +
			" EHF020406T0_01 AS EMP_ID, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			//" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '1' " +  //上班
			" )tableA ON tableA.ATT_DATE  = DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') " +
			" AND tableA.EMP_ID = EHF010105T0_02 " +
			" AND tableA.COMP_ID = EHF010105T0_06 " +
			//下班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020401T0_05 AS ATT_DATE, " +
			" EHF020401T0_09 AS OUT_ABNORMAL_FLAG, " +
			" EHF020401T0_09_DESC AS OUT_ABNORMAL_DESC, " +
			" EHF020401T0_02_EMP AS EMP_ID, " +
			" EHF020401T0_11 AS COMP_ID " +
			" FROM EHF020401T0 " +
			" WHERE 1=1 " +
			//" AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '2' " +  //下班
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS OUT_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS OUT_ABNORMAL_DESC, " +
			" EHF020406T0_01 AS EMP_ID, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			//" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '2' " +  //下班
			" )tableB ON tableB.ATT_DATE  = DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') " +
			" AND tableB.EMP_ID = EHF010105T0_02 " +
			" AND tableB.COMP_ID = EHF010105T0_06 " +
			//假別資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" GROUP_CONCAT(TIME_INF) AS VA_INF, " +
			" EMP_ID " +
			" FROM " +
			" ( " +
			" SELECT " +
			" CASE SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT(EHF020100T0_04,'(',(SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT(EHF020100T0_04,'(',SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1), '天', " +
			" SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5), '時)') " +
			" END AS TIME_INF, " +
			" EHF020200T0_03 AS EMP_ID " +
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 " +
			" AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " +
			//" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020200T0_18 = '"+this.sta_comp_id+"' " +  //公司代碼
			" AND '"+date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10 " +  //日期
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" CASE SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT('出差(',(SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT('出差(',SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1), '天', " +
			" SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5), '時)') " +
			" END AS TIME_INF, " +
			" EHF020300T0_03 AS EMP_ID " +
			" FROM EHF020300T0 " +
			" WHERE 1=1 " +
			//" AND EHF020300T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020300T0_21 = '"+this.sta_comp_id+"' " +  //公司代碼
			" AND '"+date+"' BETWEEN EHF020300T0_09 AND EHF020300T0_10 " +  //日期
			" AND EHF020300T0_20 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '公務' AS TIME_INF, " +
			" EHF020600T0_03 AS EMP_ID " +
			" FROM EHF020600T0 " +
			" WHERE 1=1 " +
			//" AND EHF020600T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020600T0_18 = '"+this.sta_comp_id+"' " +  //公司代碼
			" AND EHF020600T0_09 = '"+date+"' " +  //日期
			" AND EHF020600T0_17 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '未打卡單' AS TIME_INF, " +
			" EHF020700T0_03 AS EMP_ID " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			//" AND EHF020700T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020700T0_15 = '"+this.sta_comp_id+"' " +  //公司代碼
			" AND EHF020700T0_08 = '"+date+"' " +  //日期
			" AND EHF020700T0_14 = '0006' " +  //表單狀態 = 完成
			" )tableC1 " +
			" WHERE 1=1 " +
			" )tableC ON tableC.EMP_ID = EHF010105T0_02 " +
			" WHERE 1=1 " +
			" AND EHF010105T0_03 = ? " +  //排班表年度(西元年)
			" AND EHF010105T0_04 = ? " +  //排班表月份
			" AND EHF010105T0_06 = ? " +  //公司代碼
			" AND EHF010105T1_02 IS NOT NULL ";  //排班表日期不可為NULL  
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, date);  //排班表日期
			indexid++;
			p6stmt.setInt(indexid, year);  //排班表年度(西元年)
			indexid++;
			p6stmt.setInt(indexid, month);  //排班表月份
			indexid++;
			p6stmt.setString(indexid, this.sta_comp_id);  //公司代碼
			indexid++;
			
			//System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());
			ResultSet rs = pstmt.executeQuery();

			while(rs.next()){
				
				work_schedule = new EMS_Work_ScheduleForm();
				
				//排班表日期資料存在
				work_schedule.setSchedule_no(rs.getInt("SCHEDULE_NO"));
				work_schedule.setEmployee_id(rs.getString("EHF010105T0_02"));
				work_schedule.setYear(rs.getInt("EHF010105T0_03"));
				work_schedule.setMonth(rs.getInt("EHF010105T0_04"));
				work_schedule.setDate(rs.getString("EHF010105T1_02"));
				work_schedule.setDate_desc(rs.getString("EHF010105T1_02_DESC"));
				work_schedule.setHoliday_flag(rs.getBoolean("EHF010105T1_03"));
				work_schedule.setClass_no(rs.getInt("EHF010105T1_04"));
				
				//處理班別資訊
				if(compClassMap.containsKey(work_schedule.getClass_no())){
					classMap = (Map)((Map)compClassMap.get(work_schedule.getClass_no())).get("CLASS");
				
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((Float)classMap.get("FULL_WORK_HOURS"));
					work_schedule.setREAL_WORK_HOURS((Float)classMap.get("REAL_WORK_HOURS"));
				}else{
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((float)0);
					work_schedule.setREAL_WORK_HOURS((float)0);
				}
				
				work_schedule.setComment(rs.getString("EHF010105T1_05"));
				work_schedule.setUSER_CREATE(rs.getString("U_CREATE"));
				work_schedule.setUSER_UPDATE(rs.getString("U_UPDATE"));
				work_schedule.setVERSION(rs.getInt("VER"));
				work_schedule.setDATE_CREATE(rs.getString("D_CREATE"));
				work_schedule.setDATE_UPDATE(rs.getString("D_UPDATE"));
				
				//加入排班表清單
				schedule_list.add(work_schedule);
			}
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return schedule_list;
	}*/
	
	/**
	 * 取得員工日期區間內的排班資料
	 * @param conn
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	/*public List<EMS_Work_ScheduleForm> getDateRangeInSchedule( Connection conn, String employee_id, String start_date, String end_date ){
		
		List<EMS_Work_ScheduleForm> schedule_list = new ArrayList<EMS_Work_ScheduleForm>();
		EMS_Work_ScheduleForm work_schedule = null;
		
		try{
			//取得公司班別詳細資訊
			Map compClassMap = EMS_Work_Schedule_TOOLS.getInstance().createCompClassMap(conn, true, this.sta_comp_id);
			Map classMap = null;
			//新EMS
			String sql = "" +
			" SELECT EHF010105T0_01 AS SCHEDULE_NO, " +
			" EHF010105T0_02, EHF010105T0_03, EHF010105T0_04, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') AS EHF010105T1_02, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y年%m月%d日') AS EHF010105T1_02_DESC, " +
			" EHF010105T1_03, EHF010105T1_04, EHF010105T1_05, " +
			" tableA.IN_ABNORMAL_FLAG AS 上班異常記號, " +
//			" IFNULL(tableA.IN_ABNORMAL_FLAG,'') AS 上班異常記號," +
			" IFNULL(tableA.IN_ABNORMAL_DESC,'') AS 上班異常說明, " +
			" tableB.OUT_ABNORMAL_FLAG AS 下班異常記號, " +			
//			" IFNULL(tableB.OUT_ABNORMAL_FLAG,'') AS 下班異常記號," +
			" IFNULL(tableB.OUT_ABNORMAL_DESC,'') AS 下班異常說明, " +
			" IFNULL(tableC.VA_INF,'') AS 假別資訊, " +
			" EHF010105T1.USER_CREATE AS U_CREATE, EHF010105T1.USER_UPDATE AS U_UPDATE, " +
			" EHF010105T1.VERSION AS VER, EHF010105T1.DATE_CREATE AS D_CREATE, " +
			" EHF010105T1.DATE_UPDATE AS D_UPDATE " +
			" FROM EHF010105T1 " +
			" LEFT JOIN EHF010105T0 ON EHF010105T0_01 = EHF010105T1_01 " +
			//上班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020410T0_03 AS ATT_DATE, " +
			" EHF020410T0_05 AS IN_ABNORMAL_FLAG, " +
			" EHF020410T0_06 AS IN_ABNORMAL_DESC, " +
			" EHF020410T0_08 AS COMP_ID " +
			" FROM EHF020410T0 " +
			" WHERE 1=1 " +
			" AND EHF020410T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020410T0_04 = '1' " +  //上班
			/*
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS IN_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS IN_ABNORMAL_DESC, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '1' " +  //上班
			*/
			/*" )tableA ON tableA.ATT_DATE  = DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') " +
			" AND tableA.COMP_ID = EHF010105T0_06 " +
			//下班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020410T0_03 AS ATT_DATE, " +
			" EHF020410T0_05 AS OUT_ABNORMAL_FLAG, " +
			" EHF020410T0_06 AS OUT_ABNORMAL_DESC, " +
			" EHF020410T0_08 AS COMP_ID " +
			" FROM EHF020410T0 " +
			" WHERE 1=1 " +
			" AND EHF020410T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020410T0_04 = '2' " +  //下班
			/*
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS OUT_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS OUT_ABNORMAL_DESC, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '2' " +  //下班
			*/
			/*" )tableB ON tableB.ATT_DATE  = DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') " +
			" AND tableB.COMP_ID = EHF010105T0_06 " +
			//假別資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" GROUP_CONCAT(TIME_INF) AS VA_INF, " +
			" DATE_FORMAT(START_DATE, '%Y/%m/%d') AS START_DATE, " +
			" DATE_FORMAT(END_DATE, '%Y/%m/%d') AS END_DATE " +
			" FROM " +
			" ( " +
			" SELECT " +
			" CASE SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT(EHF020100T0_04,'(',(SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT(EHF020100T0_04,'(',SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1), '天', " +
			" SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5), '時)') " +
			" END AS TIME_INF, " +
			" EHF020200T0_09 AS START_DATE, " +
			" EHF020200T0_10 AS END_DATE " +
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 " +
			" AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020200T0_18 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND '"+date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10 " +  //日期
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" CASE SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT('出差(',(SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT('出差(',SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1), '天', " +
			" SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5), '時)') " +
			" END AS TIME_INF, " +
			" EHF020300T0_09 AS START_DATE, " +
			" EHF020300T0_10 AS END_DATE " +
			" FROM EHF020300T0 " +
			" WHERE 1=1 " +
			" AND EHF020300T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020300T0_21 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND '"+date+"' BETWEEN EHF020300T0_09 AND EHF020300T0_10 " +  //日期
			" AND EHF020300T0_20 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '公務' AS TIME_INF, " +
			" EHF020600T0_09 AS START_DATE, " +
			" EHF020600T0_09 AS END_DATE " +
			" FROM EHF020600T0 " +
			" WHERE 1=1 " +
			" AND EHF020600T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020600T0_18 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND EHF020600T0_09 = '"+date+"' " +  //日期
			" AND EHF020600T0_17 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '未打卡單' AS TIME_INF, " +
			" EHF020700T0_08 AS START_DATE, " +
			" EHF020700T0_08 AS END_DATE " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020700T0_15 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND EHF020700T0_08 = '"+date+"' " +  //日期
			" AND EHF020700T0_14 = '0006' " +  //表單狀態 = 完成
			" )tableC1 " +
			" WHERE 1=1 " +
			" GROUP BY tableC1.START_DATE, tableC1.END_DATE " +
			" )tableC ON DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') BETWEEN tableC.START_DATE AND tableC.END_DATE  " +
			" WHERE 1=1 " +
			" AND DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //排班表日期區間
			" AND EHF010105T0_02 = '"+employee_id+"' " +  //員工工號
			" AND EHF010105T1_06 = '"+this.sta_comp_id+"' " +  //公司代碼
			" AND EHF010105T1_02 IS NOT NULL ";  //排班表日期不可為NULL
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//排班表資料存在
				work_schedule = new EMS_Work_ScheduleForm();
				work_schedule.setSchedule_no(rs.getInt("SCHEDULE_NO"));
				work_schedule.setEmployee_id(rs.getString("EHF010105T0_02"));
				work_schedule.setYear(rs.getInt("EHF010105T0_03"));
				work_schedule.setMonth(rs.getInt("EHF010105T0_04"));
				work_schedule.setDate(rs.getString("EHF010105T1_02"));
				work_schedule.setDate_desc(rs.getString("EHF010105T1_02_DESC"));
				work_schedule.setHoliday_flag(rs.getBoolean("EHF010105T1_03"));
				work_schedule.setClass_no(rs.getInt("EHF010105T1_04"));
				
				//處理班別資訊
				if(compClassMap.containsKey(work_schedule.getClass_no())){
					classMap = (Map)((Map)compClassMap.get(work_schedule.getClass_no())).get("CLASS");
				
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((Float)classMap.get("FULL_WORK_HOURS"));
					work_schedule.setREAL_WORK_HOURS((Float)classMap.get("REAL_WORK_HOURS"));
				}else{
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((float)0);
					work_schedule.setREAL_WORK_HOURS((float)0);
				}
				
				//設定上班考勤、下班考勤、假別資訊
				work_schedule.setIn_abnormal_flag(rs.getBoolean("上班異常記號"));
				work_schedule.setIn_abnormal_desc(rs.getString("上班異常說明"));
				work_schedule.setOut_abnormal_flag(rs.getBoolean("下班異常記號"));
				work_schedule.setOut_abnormal_desc(rs.getString("下班異常說明"));
				work_schedule.setVa_inf(rs.getString("假別資訊"));
				
				work_schedule.setComment(rs.getString("EHF010105T1_05"));
				work_schedule.setUSER_CREATE(rs.getString("U_CREATE"));
				work_schedule.setUSER_UPDATE(rs.getString("U_UPDATE"));
				work_schedule.setVERSION(rs.getInt("VER"));
				work_schedule.setDATE_CREATE(rs.getString("D_CREATE"));
				work_schedule.setDATE_UPDATE(rs.getString("D_UPDATE"));
				schedule_list.add(work_schedule);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return schedule_list;
	}*/
	
	/**
	 * 取得員工日期區間內的排班資料 In Map
	 * @param conn
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	/*public Map<String,EMS_Work_ScheduleForm> getDateRangeInScheduleMap( Connection conn, String employee_id, String start_date, String end_date ){
		
		Map<String,EMS_Work_ScheduleForm> schedule_map = new HashMap<String, EMS_Work_ScheduleForm>();
		
		try{
			EMS_Work_ScheduleForm work_schedule = null;
			List<EMS_Work_ScheduleForm> schedule_list = this.getDateRangeInSchedule(conn, employee_id, start_date, end_date);
			
			Iterator<EMS_Work_ScheduleForm> it = schedule_list.iterator();
			
			while(it.hasNext()){
				work_schedule = (EMS_Work_ScheduleForm) it.next();
				schedule_map.put( work_schedule.getDate(), work_schedule);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return schedule_map;
	}*/
	
	/**
	 * 取得員工指定月份的排班資料
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @return
	 */
	/*public List<EMS_Work_ScheduleForm> getOneMonthInSchedule( Connection conn, String employee_id, int year, int month ){
		
		int schedule_no = -1;
		List<EMS_Work_ScheduleForm> schedule_list = new ArrayList<EMS_Work_ScheduleForm>();
		EMS_Work_ScheduleForm work_schedule = null;
		try{		
			//取得排班表序號
			schedule_no = this.getScheduleNo(conn, employee_id, year, month);
			
			//檢核排班表序號
			if( schedule_no < 1 ){
				return schedule_list;
			}
			
			//取得公司班別詳細資訊
			Map compClassMap = EMS_Work_Schedule_TOOLS.getInstance().createCompClassMap(conn, true, this.sta_comp_id);
			Map classMap = null;
			
			String sql = "" +
			" SELECT EHF010105T0_01 AS SCHEDULE_NO, " +
			" EHF010105T0_02, EHF010105T0_03, EHF010105T0_04, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') AS EHF010105T1_02, " +
			" DATE_FORMAT(EHF010105T1_02, '%Y年%m月%d日') AS EHF010105T1_02_DESC, " +
			" DATE_FORMAT(EHF010105T1_02, '%e') AS DAY, " +
			" EHF010105T1_03, EHF010105T1_04, EHF010105T1_05,EHF010105T1_07, " +
			" IFNULL(tableA.IN_ABNORMAL_FLAG,'') AS 上班異常記號, IFNULL(tableA.IN_ABNORMAL_DESC,'') AS 上班異常說明, " +
			" IFNULL(tableB.OUT_ABNORMAL_FLAG,'') AS 下班異常記號, IFNULL(tableB.OUT_ABNORMAL_DESC,'') AS 下班異常說明, " +
			" IFNULL(tableC.VA_INF,'') AS 假別資訊, " +
			" EHF010105T1.USER_CREATE AS U_CREATE, EHF010105T1.USER_UPDATE AS U_UPDATE, " +
			" EHF010105T1.VERSION AS VER, EHF010105T1.DATE_CREATE AS D_CREATE, " +
			" EHF010105T1.DATE_UPDATE AS D_UPDATE " +
			" FROM EHF010105T0 " +
			" LEFT JOIN EHF010105T1 ON EHF010105T1_01 = EHF010105T0_01 " +
			//上班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020401T0_05 AS ATT_DATE, " +
			" EHF020401T0_09 AS IN_ABNORMAL_FLAG, " +
			" EHF020401T0_09_DESC AS IN_ABNORMAL_DESC, " +
			" EHF020401T0_11 AS COMP_ID " +
			" FROM EHF020401T0 " +
			" WHERE 1=1 " +
			" AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '1' " +  //上班
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS IN_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS IN_ABNORMAL_DESC, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '1' " +  //上班
			" )tableA ON tableA.ATT_DATE  = DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') " +
			" AND tableA.COMP_ID = EHF010105T0_06 " +
			//下班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020401T0_05 AS ATT_DATE, " +
			" EHF020401T0_09 AS OUT_ABNORMAL_FLAG, " +
			" EHF020401T0_09_DESC AS OUT_ABNORMAL_DESC, " +
			" EHF020401T0_11 AS COMP_ID " +
			" FROM EHF020401T0 " +
			" WHERE 1=1 " +
			" AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '2' " +  //下班
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS OUT_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS OUT_ABNORMAL_DESC, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '2' " +  //下班
			" )tableB ON tableB.ATT_DATE  = DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') " +
			" AND tableB.COMP_ID = EHF010105T0_06 " +
			//假別資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" GROUP_CONCAT(TIME_INF) AS VA_INF, " +
			" DATE_FORMAT(START_DATE, '%Y/%m/%d') AS START_DATE, " +
			" DATE_FORMAT(END_DATE, '%Y/%m/%d') AS END_DATE " +
			" FROM " +
			" ( " +
			" SELECT " +
			" CASE SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT(EHF020100T0_04,'(',(SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT(EHF020100T0_04,'(',SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1), '天', " +
			" SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5), '時)') " +
			" END AS TIME_INF, " +
			" EHF020200T0_09 AS START_DATE, " +
			" EHF020200T0_10 AS END_DATE " +
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 " +
			" AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020200T0_18 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND '"+date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10 " +  //日期
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" CASE SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT('出差(',(SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT('出差(',SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1), '天', " +
			" SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5), '時)') " +
			" END AS TIME_INF, " +
			" EHF020300T0_09 AS START_DATE, " +
			" EHF020300T0_10 AS END_DATE " +
			" FROM EHF020300T0 " +
			" WHERE 1=1 " +
			" AND EHF020300T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020300T0_21 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND '"+date+"' BETWEEN EHF020300T0_09 AND EHF020300T0_10 " +  //日期
			" AND EHF020300T0_20 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '公務' AS TIME_INF, " +
			" EHF020600T0_09 AS START_DATE, " +
			" EHF020600T0_09 AS END_DATE " +
			" FROM EHF020600T0 " +
			" WHERE 1=1 " +
			" AND EHF020600T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020600T0_18 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND EHF020600T0_09 = '"+date+"' " +  //日期
			" AND EHF020600T0_17 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '未打卡單' AS TIME_INF, " +
			" EHF020700T0_08 AS START_DATE, " +
			" EHF020700T0_08 AS END_DATE " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020700T0_15 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND EHF020700T0_08 = '"+date+"' " +  //日期
			" AND EHF020700T0_14 = '0006' " +  //表單狀態 = 完成
			" )tableC1 " +
			" WHERE 1=1 " +
			" GROUP BY tableC1.START_DATE, tableC1.END_DATE " +
			" )tableC ON DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') BETWEEN tableC.START_DATE AND tableC.END_DATE  " +
			" WHERE 1=1 " +
			" AND EHF010105T0_01 = "+schedule_no+" " +  //排班表序號
			" AND EHF010105T1_02 IS NOT NULL ";  //排班表日期不可為NULL
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//排班表資料存在
				work_schedule = new EMS_Work_ScheduleForm();
				work_schedule.setSchedule_no(rs.getInt("SCHEDULE_NO"));
				work_schedule.setEmployee_id(rs.getString("EHF010105T0_02"));
				work_schedule.setYear(rs.getInt("EHF010105T0_03"));
				work_schedule.setMonth(rs.getInt("EHF010105T0_04"));
				work_schedule.setDay(rs.getInt("DAY"));
				work_schedule.setDate(rs.getString("EHF010105T1_02"));
				work_schedule.setDate_desc(rs.getString("EHF010105T1_02_DESC"));
				work_schedule.setHoliday_flag(rs.getBoolean("EHF010105T1_03"));
				
				work_schedule.setVacation(rs.getString("EHF010105T1_07"));
				work_schedule.setClass_no(rs.getInt("EHF010105T1_04"));
				
				//處理班別資訊
				if(compClassMap.containsKey(work_schedule.getClass_no())){
					classMap = (Map)((Map)compClassMap.get(work_schedule.getClass_no())).get("CLASS");
				
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((Float)classMap.get("FULL_WORK_HOURS"));
					work_schedule.setREAL_WORK_HOURS((Float)classMap.get("REAL_WORK_HOURS"));
				}else{
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((float)0);
					work_schedule.setREAL_WORK_HOURS((float)0);
				}
				
				//設定上班考勤、下班考勤、假別資訊
				work_schedule.setIn_abnormal_flag(rs.getBoolean("上班異常記號"));
				work_schedule.setIn_abnormal_desc(rs.getString("上班異常說明"));
				work_schedule.setOut_abnormal_flag(rs.getBoolean("下班異常記號"));
				work_schedule.setOut_abnormal_desc(rs.getString("下班異常說明"));
				work_schedule.setVa_inf(rs.getString("假別資訊"));
				
				work_schedule.setComment(rs.getString("EHF010105T1_05"));
				work_schedule.setUSER_CREATE(rs.getString("U_CREATE"));
				work_schedule.setUSER_UPDATE(rs.getString("U_UPDATE"));
				work_schedule.setVERSION(rs.getInt("VER"));
				work_schedule.setDATE_CREATE(rs.getString("D_CREATE"));
				work_schedule.setDATE_UPDATE(rs.getString("D_UPDATE"));
				schedule_list.add(work_schedule);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return schedule_list;
	}*/
	/**
	 * 取得員工指定月份的排班資料
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @return
	 */
	/*public List<EMS_Work_ScheduleForm> get_Default_OneMonthInSchedule( Connection conn, String employee_id, int year, int month ){
		
		int schedule_no = -1;
		List<EMS_Work_ScheduleForm> schedule_list = new ArrayList<EMS_Work_ScheduleForm>();
		EMS_Work_ScheduleForm work_schedule = null;
		try{		
			//取得排班表序號
			schedule_no = this.getScheduleNo(conn, employee_id, year, month);
			
			//檢核排班表序號
			if( schedule_no < 1 ){
				return schedule_list;
			}
			
			//取得公司班別詳細資訊
			Map compClassMap = EMS_Work_Schedule_TOOLS.getInstance().createCompClassMap(conn, true, this.sta_comp_id);
			Map classMap = null;
			
			String sql = "" +
			" SELECT EHF010105T0_01 AS SCHEDULE_NO, " +
			" EHF010105T0_02, EHF010105T0_03, EHF010105T0_04, " +
			" DATE_FORMAT(EHF010105T2_02, '%Y/%m/%d') AS EHF010105T2_02, " +
			" DATE_FORMAT(EHF010105T2_02, '%Y年%m月%d日') AS EHF010105T2_02_DESC, " +
			" DATE_FORMAT(EHF010105T2_02, '%e') AS DAY, " +
			" EHF010105T2_03, EHF010105T2_04, EHF010105T2_05, EHF010105T2_07," +
			" IFNULL(tableA.IN_ABNORMAL_FLAG,'') AS 上班異常記號, IFNULL(tableA.IN_ABNORMAL_DESC,'') AS 上班異常說明, " +
			" IFNULL(tableB.OUT_ABNORMAL_FLAG,'') AS 下班異常記號, IFNULL(tableB.OUT_ABNORMAL_DESC,'') AS 下班異常說明, " +
			" IFNULL(tableC.VA_INF,'') AS 假別資訊, " +
			" EHF010105T2.USER_CREATE AS U_CREATE, EHF010105T2.USER_UPDATE AS U_UPDATE, " +
			" EHF010105T2.VERSION AS VER, EHF010105T2.DATE_CREATE AS D_CREATE, " +
			" EHF010105T2.DATE_UPDATE AS D_UPDATE " +
			" FROM EHF010105T0 " +
			" LEFT JOIN EHF010105T2 ON EHF010105T2_01 = EHF010105T0_01 " +
			//上班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020401T0_05 AS ATT_DATE, " +
			" EHF020401T0_09 AS IN_ABNORMAL_FLAG, " +
			" EHF020401T0_09_DESC AS IN_ABNORMAL_DESC, " +
			" EHF020401T0_11 AS COMP_ID " +
			" FROM EHF020401T0 " +
			" WHERE 1=1 " +
			" AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '1' " +  //上班
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS IN_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS IN_ABNORMAL_DESC, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '1' " +  //上班
			" )tableA ON tableA.ATT_DATE  = DATE_FORMAT(EHF010105T2_02, '%Y/%m/%d') " +
			" AND tableA.COMP_ID = EHF010105T0_06 " +
			//下班打卡資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" EHF020401T0_05 AS ATT_DATE, " +
			" EHF020401T0_09 AS OUT_ABNORMAL_FLAG, " +
			" EHF020401T0_09_DESC AS OUT_ABNORMAL_DESC, " +
			" EHF020401T0_11 AS COMP_ID " +
			" FROM EHF020401T0 " +
			" WHERE 1=1 " +
			" AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '2' " +  //下班
			" UNION " +
			" SELECT " +
			" EHF020406T0_04 AS ATT_DATE, " +
			" EHF020406T0_05_FLG AS OUT_ABNORMAL_FLAG, " +
			" EHF020406T0_06 AS OUT_ABNORMAL_DESC, " +
			" EHF020406T0_07 AS COMP_ID " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '2' " +  //下班
			" )tableB ON tableB.ATT_DATE  = DATE_FORMAT(EHF010105T2_02, '%Y/%m/%d') " +
			" AND tableB.COMP_ID = EHF010105T0_06 " +
			//假別資訊
			" LEFT JOIN " +
			" ( " +
			" SELECT " +
			" GROUP_CONCAT(TIME_INF) AS VA_INF, " +
			" DATE_FORMAT(START_DATE, '%Y/%m/%d') AS START_DATE, " +
			" DATE_FORMAT(END_DATE, '%Y/%m/%d') AS END_DATE " +
			" FROM " +
			" ( " +
			" SELECT " +
			" CASE SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT(EHF020100T0_04,'(',(SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT(EHF020100T0_04,'(',SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1), '天', " +
			" SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5), '時)') " +
			" END AS TIME_INF, " +
			" EHF020200T0_09 AS START_DATE, " +
			" EHF020200T0_10 AS END_DATE " +
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 " +
			" AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020200T0_18 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND '"+date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10 " +  //日期
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" CASE SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1) " +
			" WHEN '0' " +
			" THEN " +
			" CONCAT('出差(',(SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5)),'時)') " +
			" ELSE " +
			" CONCAT('出差(',SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1), '天', " +
			" SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5), '時)') " +
			" END AS TIME_INF, " +
			" EHF020300T0_09 AS START_DATE, " +
			" EHF020300T0_10 AS END_DATE " +
			" FROM EHF020300T0 " +
			" WHERE 1=1 " +
			" AND EHF020300T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020300T0_21 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND '"+date+"' BETWEEN EHF020300T0_09 AND EHF020300T0_10 " +  //日期
			" AND EHF020300T0_20 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '公務' AS TIME_INF, " +
			" EHF020600T0_09 AS START_DATE, " +
			" EHF020600T0_09 AS END_DATE " +
			" FROM EHF020600T0 " +
			" WHERE 1=1 " +
			" AND EHF020600T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020600T0_18 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND EHF020600T0_09 = '"+date+"' " +  //日期
			" AND EHF020600T0_17 = '0006' " +  //表單狀態 = 完成
			" UNION " +
			" SELECT " +
			" '未打卡單' AS TIME_INF, " +
			" EHF020700T0_08 AS START_DATE, " +
			" EHF020700T0_08 AS END_DATE " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020700T0_15 = '"+this.sta_comp_id+"' " +  //公司代碼
			//" AND EHF020700T0_08 = '"+date+"' " +  //日期
			" AND EHF020700T0_14 = '0006' " +  //表單狀態 = 完成
			" )tableC1 " +
			" WHERE 1=1 " +
			" GROUP BY tableC1.START_DATE, tableC1.END_DATE " +
			" )tableC ON DATE_FORMAT(EHF010105T2_02, '%Y/%m/%d') BETWEEN tableC.START_DATE AND tableC.END_DATE  " +
			" WHERE 1=1 " +
			" AND EHF010105T0_01 = "+schedule_no+" " +  //排班表序號
			" AND EHF010105T2_02 IS NOT NULL ";  //排班表日期不可為NULL
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//排班表資料存在
				work_schedule = new EMS_Work_ScheduleForm();
				work_schedule.setSchedule_no(rs.getInt("SCHEDULE_NO"));
				work_schedule.setEmployee_id(rs.getString("EHF010105T0_02"));
				work_schedule.setYear(rs.getInt("EHF010105T0_03"));
				work_schedule.setMonth(rs.getInt("EHF010105T0_04"));
				work_schedule.setDay(rs.getInt("DAY"));
				work_schedule.setDate(rs.getString("EHF010105T2_02"));
				work_schedule.setDate_desc(rs.getString("EHF010105T2_02_DESC"));
				work_schedule.setHoliday_flag(rs.getBoolean("EHF010105T2_03"));
				work_schedule.setClass_no(rs.getInt("EHF010105T2_04"));
				work_schedule.setVacation(rs.getString("EHF010105T2_07"));
				//處理班別資訊
				if(compClassMap.containsKey(work_schedule.getClass_no())){
					classMap = (Map)((Map)compClassMap.get(work_schedule.getClass_no())).get("CLASS");
				
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((Float)classMap.get("FULL_WORK_HOURS"));
					work_schedule.setREAL_WORK_HOURS((Float)classMap.get("REAL_WORK_HOURS"));
				}else{
					//設定工作時數
					work_schedule.setFULL_WORK_HOURS((float)0);
					work_schedule.setREAL_WORK_HOURS((float)0);
				}
				
				//設定上班考勤、下班考勤、假別資訊
				work_schedule.setIn_abnormal_flag(rs.getBoolean("上班異常記號"));
				work_schedule.setIn_abnormal_desc(rs.getString("上班異常說明"));
				work_schedule.setOut_abnormal_flag(rs.getBoolean("下班異常記號"));
				work_schedule.setOut_abnormal_desc(rs.getString("下班異常說明"));
				work_schedule.setVa_inf(rs.getString("假別資訊"));
				
				work_schedule.setComment(rs.getString("EHF010105T2_05"));
				work_schedule.setUSER_CREATE(rs.getString("U_CREATE"));
				work_schedule.setUSER_UPDATE(rs.getString("U_UPDATE"));
				work_schedule.setVERSION(rs.getInt("VER"));
				work_schedule.setDATE_CREATE(rs.getString("D_CREATE"));
				work_schedule.setDATE_UPDATE(rs.getString("D_UPDATE"));
				schedule_list.add(work_schedule);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return schedule_list;
	}*/
	
	/**
	 * 取得員工指定月份的排班公休日數
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @return
	 */
	public int getOneMonthHolidayInSchedule( Connection conn, String employee_id, int year, int month ){
		
		int schedule_no = -1;
		int holiday = 0;
		
		try{		
			//取得排班表序號
			schedule_no = this.getScheduleNo(conn, employee_id, year, month);
			
			//檢核排班表序號
			if( schedule_no < 1 ){
				holiday = 0;
				return holiday;
			}
			//EMS修改:計算公休日時還要考慮是否有假別是有計算到假日。
			//UNION的前段是要取得此員工不需要計算休假日為公休日的天數。
			//UNION的後段是取得此員工所有休假日。
			//將前後端相加後，就是真正的休假日。 20140612 by Hedwig
//			String sql = "" +
//			" SELECT COUNT(*) AS HOLIDAY FROM ( " +
//			" SELECT EHF010105T1_01 AS SCHEDULE_NO, " +
//			" EHF010105T1_02, EHF010105T1_03 " +
//			" FROM EHF010105T1 " +
//			" WHERE 1=1 " +
//			" AND EHF010105T1_01 = "+schedule_no+" " +  //排班表序號
//			" AND EHF010105T1_03 = true " +  //是否為休假日
//			" ) tableA " +
//			" WHERE 1=1 ";
			String sql = "" +
			" SELECT COUNT(*)*-1 AS HOLIDAY  FROM( " +
			" SELECT EHF010105T1_01 AS SCHEDULE_NO, " +
			" EHF010105T1_02, EHF010105T1_03 " +
			" FROM EHF010105T1 " +
			" LEFT JOIN EHF010105T0 ON EHF010105T0_01 = EHF010105T1_01 "+
			" LEFT JOIN EHF020200T0 ON EHF020200T0_03 = EHF010105T0_02 "+
			" LEFT JOIN EHF020100T0 ON EHF020200T0_14 = EHF020100T0_03" +
			" WHERE 1=1 " +
			" AND EHF010105T1_01 = "+schedule_no+" " +  //排班表序號
			" AND EHF010105T1_03 = true " + //是否為休假日
			" AND replace(EHF010105T1_02,'/','') BETWEEN DATE_FORMAT(EHF020200T0_09,'%Y%m%d') AND DATE_FORMAT(EHF020200T0_10,'%Y%m%d')" +
			"  AND EHF020100T0_11 = true " +  
			" ) tableA " +
			" WHERE 1=1 " +
			" UNION "+
			" SELECT COUNT(*) AS HOLIDAY  FROM( " +
			" SELECT EHF010105T1_01 AS SCHEDULE_NO, " +
			" EHF010105T1_02, EHF010105T1_03 " +
			" FROM EHF010105T1 " +
			" WHERE 1=1 " +
			" AND EHF010105T1_01 = "+schedule_no+" " +  //排班表序號
			" AND EHF010105T1_03 = true " + //是否為休假日
			" ) tableB " +
			" WHERE 1=1 " ;
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
//			if(rs.next()){
//				holiday = rs.getInt("HOLIDAY");
//			}
			while(rs.next()){
				holiday += rs.getInt("HOLIDAY");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return holiday;
	}
	
	/**
	 * 取得員工指定月份的排班資料 In Map
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @return
	 */
	/*public Map getOneMonthInScheduleMap( Connection conn, String employee_id, int year, int month ){
		
		Map schedule_map = new HashMap();
		int schedule_no = -1;
		
		try{
			EMS_Work_ScheduleForm work_schedule = null;
			List<EMS_Work_ScheduleForm> schedule_list = this.getOneMonthInSchedule(conn, employee_id, year, month);
			
			Iterator<EMS_Work_ScheduleForm> it = schedule_list.iterator();
			
			float full_hours = 0;
			float real_hours = 0;
			
			while(it.hasNext()){
				work_schedule = (EMS_Work_ScheduleForm) it.next();
				
				schedule_no = work_schedule.getSchedule_no();  //取得排班表序號
				
				//寫入員工每日排班表資訊
				schedule_map.put( work_schedule.getDay(), work_schedule);
				schedule_map.put( work_schedule.getDate(), work_schedule);
				
				//計算員工時數
				full_hours += work_schedule.getFULL_WORK_HOURS();
				real_hours += work_schedule.getREAL_WORK_HOURS();
				
			}
			
			//寫入員工時數
			schedule_map.put("FULL_WORK_HOURS", full_hours); 
			schedule_map.put("REAL_WORK_HOURS", real_hours);
			
			//寫入員工排班表資訊
			schedule_map.put("SCHEDULE_SN", schedule_no);
			schedule_map.put("EMPLOYEE_ID", employee_id);
			schedule_map.put("YEAR", year);
			schedule_map.put("MONTH", month);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return schedule_map;
	}*/
	
	
	/**
	 * 取得預設員工指定月份的排班資料 In Map
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @return
	 */
	/*public Map get_Default_OneMonthInScheduleMap( Connection conn, String employee_id, int year, int month ){
		
		Map schedule_map = new HashMap();
		int schedule_no = -1;
		
		try{
			EMS_Work_ScheduleForm work_schedule = null;
			List<EMS_Work_ScheduleForm> schedule_list = this.get_Default_OneMonthInSchedule(conn, employee_id, year, month);
			
			Iterator<EMS_Work_ScheduleForm> it = schedule_list.iterator();
			
			float full_hours = 0;
			float real_hours = 0;
			
			while(it.hasNext()){
				work_schedule = (EMS_Work_ScheduleForm) it.next();
				
				schedule_no = work_schedule.getSchedule_no();  //取得排班表序號
				
				//寫入員工每日排班表資訊
				schedule_map.put( work_schedule.getDay(), work_schedule);
				schedule_map.put( work_schedule.getDate(), work_schedule);
				
				//計算員工時數
				full_hours += work_schedule.getFULL_WORK_HOURS();
				real_hours += work_schedule.getREAL_WORK_HOURS();
				
			}
			
			//寫入員工時數
			schedule_map.put("FULL_WORK_HOURS", full_hours); 
			schedule_map.put("REAL_WORK_HOURS", real_hours);
			
			//寫入員工排班表資訊
			schedule_map.put("SCHEDULE_SN", schedule_no);
			schedule_map.put("EMPLOYEE_ID", employee_id);
			schedule_map.put("YEAR", year);
			schedule_map.put("MONTH", month);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return schedule_map;
	}*/
	
	/**
	 * 取得部門指定月份的排班資料 In Map
	 * @param conn
	 * @param dept_id
	 * @param year
	 * @param month
	 * @param user_id
	 * @return
	 */
	/*public Map getDeptOneMonthInScheduleMap( Connection conn, String dept_id, int year, int month, String user_id ){
		
		Map dept_schedule_map = new HashMap();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			//取得部門員工清單
			List emplist = tools.getSameDepEmpList(user_id, dept_id, this.sta_comp_id,"");
			Iterator it = emplist.iterator();
			Map tempMap = null;
			Map schedule = null;
			float full_dept_hours = 0;
			float real_dept_hours = 0;
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				//取得員工排班表(One Month)
				schedule = this.getOneMonthInScheduleMap(conn, (String) tempMap.get("EMPLOYEE_ID"), year, month);
				
				//寫入員工排班表資訊
				dept_schedule_map.put((String)schedule.get("EMPLOYEE_ID"), schedule);
				
				//計算部門時數
				full_dept_hours += (Float)schedule.get("FULL_WORK_HOURS");
				real_dept_hours += (Float)schedule.get("REAL_WORK_HOURS");
				
			}
			
			//寫入部門時數
			dept_schedule_map.put("FULL_WORK_HOURS", full_dept_hours);
			dept_schedule_map.put("REAL_WORK_HOURS", real_dept_hours);
			
			//寫入部門排班表資訊
			dept_schedule_map.put("DEPT_ID", dept_id);
			dept_schedule_map.put("YEAR", year);
			dept_schedule_map.put("MONTH", month);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dept_schedule_map;
	}*/
	
	/**
	 * 修改指定日期的員工排班表
	 * @param conn
	 * @param employee_id
	 * @param year
	 * @param month
	 * @param schedule_date
	 * @param holiday_flag
	 * @param class_no
	 * @param comment
	 * @return
	 */
	public boolean editEmpSchedule( Connection conn, String employee_id, int year, int month, String schedule_date,
								    boolean holiday_flag, int class_no, String comment ){
		
		boolean chk_flag = false;
		
		try{
			//取得指定員工年月的排班表
			int schedule_no = this.getScheduleNo(conn, employee_id, year, month);
			
			//修改指定日期的員工排班表
			chk_flag = this.editSchedule(conn, schedule_no, 
										 schedule_date, holiday_flag, class_no, comment, EMS_Work_Schedule_Table.sta_comp_id);
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 修改公司排班表指定日期的假日設定
	 * @param conn
	 * @param schedule_date
	 * @param holiday_flag
	 * @return
	 */
	/*public Map<String,Integer> editCompScheduleHolidayForOneDay( Connection conn, String schedule_date, boolean holiday_flag ){
		
		Map<String,Integer> msg_map = new HashMap<String,Integer>();
		int OK = 0;
		int NG = 0;
		//取得員工主要歸屬部門
		Map empdepMap =new HashMap();
		
		try{
			boolean chk_flag = false;
			
			//取得指定日期的公司排班表
			List<EMS_Work_ScheduleForm> schedule_list = this.getOneDayInScheduleForComp(conn, schedule_date);
			
			Iterator<EMS_Work_ScheduleForm> it = schedule_list.iterator();
			EMS_Work_ScheduleForm work_schedule = null;
			
			while(it.hasNext()){
				
				work_schedule = it.next();
				
				empdepMap = tools.getEmpBelongMainDepInf(work_schedule.getEmployee_id(),(String) work_schedule.getEmployee_id(), this.sta_comp_id,"");
				Map EHF010100T0_17 =new HashMap();
					
					
				this.getEHF010100T0_17(conn,(String)empdepMap.get("DEPT_ID"),EHF010100T0_17);
				
				//修改指定日期的員工排班表
				chk_flag = this.editSchedule(conn, 
											 work_schedule.getSchedule_no(), 
											 work_schedule.getDate(), 						 
											 //holiday_flag,
											 tools.isHoliday(conn, schedule_date, this.sta_comp_id,(String)EHF010100T0_17.get("EHF010100T0_17")),
											 Integer.valueOf((String)EHF010100T0_17.get("EHF010100T0_01")), 
											 work_schedule.getComment(), 
											 this.sta_comp_id);
				
				if(chk_flag){
					OK++;
				}else{
					NG++;
				}
				
			}
			
			//建立訊息Map
			msg_map.put("OK", OK);
			msg_map.put("NG", NG);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return msg_map;
	}*/
	
	private Map getEHF010100T0_17(Connection conn,String DEPT_ID ,Map map) {
		// TODO Auto-generated method stub
		
		
		try{
		//找出公司目前的部門預設班別
		String sql = "" +
					 " SELECT * FROM EHF010100T0" +
					 " WHERE 1=1 " +
					 " AND EHF010100T0_09 = '1'" +
					 " AND EHF010100T0_11 = '"+EMS_Work_Schedule_Table.sta_comp_id+"' "+
					 " AND EHF010100T0_02 = '"+DEPT_ID+"' " ;
		
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			
			map.put("EHF010100T0_17", rs.getString("EHF010100T0_17"));
			map.put("EHF010100T0_01", rs.getString("EHF010100T0_01"));
		}
		
		rs.close();
		stmt.close();
		
		
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 依據日期區間修改公司排班表假日設定
	 * @param conn
	 * @param start_date
	 * @param end_date
	 * @param holiday_flag
	 */
	/*public void chgCompScheduleHolidayByDateRange(Connection conn, String start_date, String end_date, boolean holiday_flag,
											      String user_id){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			//取得系統當前日期時間
			Calendar cur_system_cal = Calendar.getInstance();
			//設定系統時間
			cur_system_cal.set(Calendar.HOUR_OF_DAY, 8);
			cur_system_cal.set(Calendar.MINUTE, 0);
			cur_system_cal.set(Calendar.SECOND, 0);
			cur_system_cal.set(Calendar.MILLISECOND, 0);
			
			//建立考勤產生元件
			EMS_AttendanceAction att_act_tools = null; 
			
			Calendar start_cal = tools.covertStringToCalendar(start_date);  //計算開始日期
			Calendar end_cal = tools.covertStringToCalendar(end_date);  //計算結束日期
			String cur_date = "";
			
			//計算迴圈建立
			boolean chk_run_flag = true;
			while(chk_run_flag){
				if(start_cal.equals(end_cal)){
					chk_run_flag = false;
				}
				cur_date = tools.convertADDateToStrng(start_cal.getTime());  //當前排班表日期
				
				//修改排班表假日設定
				this.editCompScheduleHolidayForOneDay(conn, cur_date, holiday_flag);
				
				//連動考勤資料重新產生, edit by joe 2013/01/23
				//需要判斷日期是否在當前系統日期之前, 若是在當前系統日期之前才可以執行"連動考勤"
				//判斷是否在當前系統日期之前
				if( start_cal.compareTo(cur_system_cal) < 0 ){
					//在系統日期之前
					//需要連動考勤資料
					//使用指定日期變更考勤
					att_act_tools = EMS_AttendanceAction.getInstance(this.sta_comp_id, cur_date, user_id );
					//產生考勤資料
					att_act_tools.execute_Attendance(conn, true, "");
					
				}else{
					//在系統日期之後
					//不需要連動考勤資料
				}
				
				start_cal.add(Calendar.DAY_OF_MONTH, 1);
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}*/
	
	
}
