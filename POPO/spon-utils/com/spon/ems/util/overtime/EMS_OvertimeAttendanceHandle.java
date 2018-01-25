package com.spon.ems.util.overtime;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.utils.util.BA_TOOLS;

/**
 * EMS 加班出勤記錄處理元件
 * 
 * @version 1.0
 */
public class EMS_OvertimeAttendanceHandle implements BaseSystem {
	
	
	private BaseFunction base_tools;
	//private Connection outside_conn = null;
	
	public EMS_OvertimeAttendanceHandle(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void finalize() throws Throwable {

	}
	
	/**
	 * 設定外部Connection
	 * @param outsideConn
	 */
	/*
	public void setOutside_conn(Connection outsideConn) {
		outside_conn = outsideConn;
	}
	*/


	/**
	 * 查詢加班記錄(加班單)
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> queryOvertimeRecord(
							   Connection conn,
							   String employee_id, String att_date,
							   String cond_overtime_start, String cond_overtime_end, 
							   String comp_id){
		
		Map<String, String> return_map = new HashMap<String, String>();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF020801T0_01 AS OV_RECORD_UID, " +
			" EHF020801T0_02 AS EMMLOYEE_ID, " +
			" EHF020801T0_03 AS OV_FORM_UID, " +
			" EHF020801T0_04 AS OV_DATE, " +
			" EHF020801T0_09 AS EHF020801T0_09, " +//加班時數處理方式     20131007新增   BY 賴泓錡
			" DATE_FORMAT(EHF020801T0_05, '%Y/%m/%d %H:%i:%s') AS OVERTIME_START, " +
			" DATE_FORMAT(EHF020801T0_06, '%Y/%m/%d %H:%i:%s') AS OVERTIME_END, " +
			" EHF020801T0_07 AS OVERTIME_HOURS, " +
			" DATE_FORMAT(EHF020801T0_05_BRK, '%Y/%m/%d %H:%i:%s') AS OVERTIME_BREAK_START, " +
			" DATE_FORMAT(EHF020801T0_06_BRK, '%Y/%m/%d %H:%i:%s') AS OVERTIME_BREAK_END, " +
			" EHF020801T0_07_DE AS OVERTIME_DE_HOURS, " +
			" NOON_LIMITED" +//是否限定中午加班
			" FROM EHF020801T0 " +
			" LEFT JOIN ehf020800t1 ON ehf020801t0.EHF020801T0_03 = ehf020800t1.EHF020800T1_02" +
			" WHERE 1=1 " +
			" AND EHF020801T0_02 = '"+employee_id+"' " +  //員工工號
			" AND EHF020801T0_08 = '"+comp_id+"' "+  //公司代碼
			" AND DATE_FORMAT(EHF020801T0_04, '%Y/%m/%d') = '"+att_date+"'";
			//處理條件的參數替換
			cond_overtime_start = cond_overtime_start.replaceAll("OV_START_DATETIME", "EHF020801T0_05");//LAI賴泓錡
			cond_overtime_end = cond_overtime_end.replaceAll("OV_END_DATETIME", "EHF020801T0_06");
			
			//判斷加班記錄的日期時間區間條件
			sql += "" +
			" AND "+cond_overtime_start+" " +  //加班起始時間(OV_START_DATETIME)
			" AND "+cond_overtime_end+" ";  //加班結束時間(OV_END_DATETIME)
			
			//" AND EHF020801T0_05 "+cond_overtime_start+" " +  //加班起始時間
			//" AND EHF020801T0_06 "+cond_overtime_end+" ";  //加班結束時間
			
			/*
			//調整Connection來源
			Connection local_conn = null;
			
			if(this.outside_conn != null && !this.outside_conn.isClosed()){
				//使用外部Connection
				local_conn = this.outside_conn;
			}else{
				local_conn = this.base_tools.getConn();
			}
			*/
			
			//執行SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				return_map.put("CHK_FLAG", "true");
				if(!"on".equals(rs.getString("NOON_LIMITED"))){
					//將資料放入return_map中
					return_map.put("OV_RECORD_UID", rs.getString("OV_RECORD_UID"));
					return_map.put("EMMLOYEE_ID", rs.getString("EMMLOYEE_ID"));
					return_map.put("OV_FORM_UID", rs.getString("OV_FORM_UID"));
					return_map.put("OV_DATE", rs.getString("OV_DATE"));
					return_map.put("OVERTIME_START", rs.getString("OVERTIME_START"));
					return_map.put("OVERTIME_END", rs.getString("OVERTIME_END"));
					return_map.put("OVERTIME_HOURS", rs.getString("OVERTIME_HOURS"));
					return_map.put("OVERTIME_BREAK_START", rs.getString("OVERTIME_BREAK_START"));
					return_map.put("OVERTIME_BREAK_END", rs.getString("OVERTIME_BREAK_END"));
					return_map.put("OVERTIME_DE_HOURS", rs.getString("OVERTIME_DE_HOURS"));
					return_map.put("EHF020801T0_09", rs.getString("EHF020801T0_09"));
				}else{
					return_map.put("CHK_FLAG", "false");
				}
			}else{
				return_map.put("CHK_FLAG", "false");
			}
			
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 查詢加班記錄(use UID as Key)
	 * @param ov_record_uid
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> queryOvertimeRecordByUID(
							   Connection conn,
							   String ov_record_uid, String comp_id){

		Map<String, String> return_map = new HashMap<String, String>();

		try{
			String sql = "" +
			" SELECT " +
			" EHF020801T0_01 AS OV_RECORD_UID, " +
			" EHF020801T0_02 AS EMMLOYEE_ID, " +
			" EHF020801T0_03 AS OV_FORM_UID, " +
			" EHF020801T0_04 AS OV_DATE, " +
			" EHF020801T0_09 AS EHF020801T0_09, " +//加班時數處理方式     20131007新增   BY 賴泓錡
			" EHF020801T0_10 AS EHF020801T0_10, " +//中午加班時數
			" DATE_FORMAT(EHF020801T0_05, '%Y/%m/%d %H:%i:%s') AS OVERTIME_START, " +
			" DATE_FORMAT(EHF020801T0_06, '%Y/%m/%d %H:%i:%s') AS OVERTIME_END, " +
			" EHF020801T0_07 AS OVERTIME_HOURS, " +
			" DATE_FORMAT(EHF020801T0_05_BRK, '%Y/%m/%d %H:%i:%s') AS OVERTIME_BREAK_START, " +
			" DATE_FORMAT(EHF020801T0_06_BRK, '%Y/%m/%d %H:%i:%s') AS OVERTIME_BREAK_END, " +
			" EHF020801T0_07_DE AS OVERTIME_DE_HOURS " +
			" FROM EHF020801T0 " +
			" WHERE 1=1 " +
			" AND EHF020801T0_01 = '"+ov_record_uid+"' " +  //加班記錄UID
			" AND EHF020801T0_08 = '"+comp_id+"' ";  //公司代碼
			
			/*
			//調整Connection來源
			Connection local_conn = null;
			if(this.outside_conn != null && !this.outside_conn.isClosed()){
				//使用外部Connection
				local_conn = this.outside_conn;
			}else{
				local_conn = this.base_tools.getConn();
			}
			*/
			
			//執行SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				return_map.put("CHK_FLAG", "true");
				
				//將資料放入return_map中
				return_map.put("OV_RECORD_UID", rs.getString("OV_RECORD_UID"));
				return_map.put("EMMLOYEE_ID", rs.getString("EMMLOYEE_ID"));
				return_map.put("OV_FORM_UID", rs.getString("OV_FORM_UID"));
				return_map.put("OV_DATE", rs.getString("OV_DATE"));
				return_map.put("OVERTIME_START", rs.getString("OVERTIME_START"));
				return_map.put("OVERTIME_END", rs.getString("OVERTIME_END"));
				return_map.put("OVERTIME_HOURS", rs.getString("OVERTIME_HOURS"));
				return_map.put("OVERTIME_BREAK_START", rs.getString("OVERTIME_BREAK_START"));
				return_map.put("OVERTIME_BREAK_END", rs.getString("OVERTIME_BREAK_END"));
				return_map.put("OVERTIME_DE_HOURS", rs.getString("OVERTIME_DE_HOURS"));
				return_map.put("EHF020801T0_09", rs.getString("EHF020801T0_09")); //加班時數處理方式     20131007新增   BY 賴泓錡
				return_map.put("EHF020801T0_10", rs.getString("EHF020801T0_10")); //加班時數處理方式     20131007新增   BY 賴泓錡
			}else{
				return_map.put("CHK_FLAG", "false");
			}
			
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 查詢員工加班時數資訊
	 * @param dept_id
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @param comp_id
	 * @return
	 */
	public List<Map<String, String>> queryOvertimeCollectHours(
							   Connection conn,
							   String dept_id, String employee_id,
							   String start_date, String end_date,
							   String comp_id) {
		
		
		List<Map<String, String>> return_list = new ArrayList<Map<String, String>>();
		Map<String, String> return_map = new HashMap<String, String>();

		try {
			String sql = "" +
			" SELECT " +
			" tableA.員工工號, tableA.部門代號, " +
			" SUM(tableA.前兩小時加班時數) AS 前兩小時加班時數, " +
			" SUM(tableA.兩小時後加班時數) AS 兩小時後加班時數, " +
			" SUM(tableA.假日加班時數) AS 假日加班時數, " +
			" SUM(tableA.總加班時數) AS 總加班時數 " +
			" FROM " +
			" ( " +
			" SELECT " +
			" EHF020802T0_02 AS 員工工號, " +
			" EHF020403T0_02 AS 部門代號, " +
			" EHF020802T0_04 AS 考勤日期 " +
			" CASE EHF010105T1_03 " +
			" WHEN false THEN " +
			"  CASE (SUM(EHF020802T0_07) > 2) " +
			"  WHEN true THEN 2 " +
			"  ELSE SUM(EHF020802T0_07) " +
			"  END " +
			" END AS 前兩小時加班時數, " +
			" CASE EHF010105T1_03 " +
			" WHEN false THEN " +
			"  CASE (SUM(EHF020802T0_07) > 2) " +
			"  WHEN true THEN (SUM(EHF020802T0_07) - 2) " +
			"  ELSE 0 " +
			"  END " +
			" END AS 兩小時後加班時數, " +
			" CASE EHF010105T1_03 " +
			" WHEN true THEN (SUM(EHF020802T0_07)) " +
			" ELSE 0 " +
			" END AS 假日加班時數, " +
			" SUM(EHF020802T0_07) AS 總加班時數 " +
			" FROM EHF020802T0 " +
			" LEFT JOIN EHF010105T0 ON EHF010105T0_02 = EHF020802T0_02 " +
			" AND EHF010105T0_06 = EHF020802T0_08 " +
			" AND EHF010105T0_03 = DATE_FORMAT(EHF020802T0_04, '%Y') " +
			" AND EHF010105T0_04 = DATE_FORMAT(EHF020802T0_04, '%m') " +
			" LEFT JOIN EHF010105T1 ON EHF010105T1_01 = EHF010105T0_01 " +
			" AND DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') = EHF020802T0_04 " +
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020802T0_02 " +
			" AND EHF020403T0_04 = EHF020802T0_08 " +
			" WHERE 1=1 " +
			" AND EHF020802T0_08 = '"+comp_id+"' ";  //公司代碼
			
			if (!"".equals(employee_id) && employee_id != null 
				&& !"".equals(dept_id) && dept_id != null ){  //員工
				sql += " AND EHF020403T0_02 = '"+dept_id+"' " +  //部門代號
					   " AND EHF020802T0_02 = '"+employee_id+"' ";  //員工工號
			}else if(!"".equals(dept_id) && dept_id != null){  //部門組織
				sql += " AND EHF020403T0_02 = '"+dept_id+"' ";  //部門代號
			}
			
			//日期區間  -- 使用實際日期做判斷
			if(!"".equals(start_date) && start_date != null && !"".equals(end_date) && end_date !=null ){
				sql += " AND " +
					   " ( " +
					   " EHF020802T0_04 BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //考勤日期
					   " ) " ;
			}else if(!"".equals(start_date) && start_date != null){
				sql += " AND " +
					   " ( " +
					   " EHF020802T0_04 >= '"+start_date+"' " +  //考勤日期
					   " ) ";
			}else if(!"".equals(end_date) && end_date != null){
				sql += " AND " +
					   " ( " +
					   " EHF020802T0_04 <= '"+end_date+"' " +  //考勤日期
					   " ) ";
			}

			sql += "" +
			" GROUP BY EHF020802T0_04, EHF020802T0_02 " +
			" ) tableA " +
			" WHERE 1=1 " +
			" GROUP BY tableA.員工工號 " +
			"";
			
			/*
			// 調整Connection來源
			Connection local_conn = null;
			if (this.outside_conn != null && !this.outside_conn.isClosed()) {
				// 使用外部Connection
				local_conn = this.outside_conn;
			} else {
				local_conn = this.base_tools.getConn();
			}
			*/

			// 執行SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				
				// 將資料放入return_map中
				return_map = new HashMap<String, String>();
				return_map.put("員工工號", rs.getString("員工工號"));
				return_map.put("部門代號", rs.getString("部門代號"));
				return_map.put("前兩小時加班時數", rs.getString("前兩小時加班時數"));
				return_map.put("兩小時後加班時數", rs.getString("兩小時後加班時數"));
				return_map.put("假日加班時數", rs.getString("假日加班時數"));
				return_map.put("總加班時數", rs.getString("總加班時數"));
				//put return map to return list
				return_list.add(return_map);
			}

			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_list;
	}
	
	/**
	 * 結算加班出勤記錄
	 * @param employee_id
	 * @param ov_record_uid
	 * @param att_date
	 * @param class_out_datetime
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> settlementOvertimeAttendanceRecord(
							   Connection conn,
							   String employee_id, String ov_record_uid, 
							   String att_date, String class_out_datetime,
							   String comp_id) {

		Map<String, String> return_map = new HashMap<String, String>();

		try {
			//刪除之前的加班出勤資料 By ov_record_uid
			this.deleteOvertimeAttendanceRecord(conn, ov_record_uid, comp_id);
			
			String sql = "" +
						 " SELECT " +
						 " EHF020401T0_02_EMP AS EMPLOYEE_ID, EHF020401T0_05 AS ATT_DATE, " +
						 " EHF020401T0_08 AS ATT_TYPE, " +
						 " DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d %H:%i:%s') AS ATT_DATETIME, " +
						 " '01' AS DATA_TYPE " +
						 " FROM EHF020401T0 " +
						 " WHERE 1=1 " +
						 " AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
						 " AND EHF020401T0_05 = '"+att_date+"' " +  //考勤日期
						 " AND EHF020401T0_08 = '5' " +  //打卡狀態 - 加班上班
						 " AND EHF020401T0_11 = '"+comp_id+"' " +  //公司代碼
						 " UNION " +
						 " SELECT " +
						 " EHF020406T0_01 AS EMPLOYEE_ID, EHF020406T0_04 AS ATT_DATE, " +
						 " EHF020406T0_05 AS ATT_TYPE, " +
						 " DATE_FORMAT(EHF020406T0_04_DTE, '%Y/%m/%d %H:%i:%s') AS ATT_DATETIME, " +
						 " CASE EHF020406T0_FIX WHEN true THEN '01' ELSE '02' END AS DATA_TYPE " +
						 " FROM EHF020406T0 " +
						 " WHERE 1=1 " +
						 " AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
						 " AND EHF020406T0_04 = '"+att_date+"' " +  //考勤日期
						 " AND EHF020406T0_05 = '5' " +  //未打卡狀態 - 加班上班
						 " AND EHF020406T0_07 = '"+comp_id+"' " +  //公司代碼
						 "";
			
			/*
			// 調整Connection來源
			Connection local_conn = null;
			if (this.outside_conn != null && !this.outside_conn.isClosed()) {
				// 使用外部Connection
				local_conn = this.outside_conn;
			} else {
				local_conn = this.base_tools.getConn();
			}
			*/

			// 執行SQL
			Statement stmt = conn.createStatement(
							 ResultSet.TYPE_SCROLL_SENSITIVE,
							 ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			boolean find_ov_out_att_flag = false;  //是否查詢加班下班考勤資料
			String ov_in_datetime = "";  //加班上班打卡日期時間
			String ov_out_datetime = "";  //加班下班打卡日期時間
			
			if (rs.next()) {
				//有找到該考勤日期的加班上班考勤資料
				
				//判斷找到的加班上班考勤資料是否為未打卡資料
				if("01".equals(rs.getString("DATA_TYPE"))){
					//EHF020401T0 打卡資料
					
					//查詢加班下班考勤資料
					find_ov_out_att_flag = true;
					//寫入加班上班打卡日期時間
					ov_in_datetime = rs.getString("ATT_DATETIME");
					
				}else if("02".equals(rs.getString("DATA_TYPE"))){
					//EHF020406T0 未打卡資料
					//加班考勤其中一筆為未打卡考勤  --> 不產生加班出勤資料
					find_ov_out_att_flag = false;
					return_map.put("CHK_FLAG", "false");
				}
			} else {
				//未找到該考勤日期的加班上班考勤資料 --> 不產生加班出勤資料
				find_ov_out_att_flag = false;
				return_map.put("CHK_FLAG", "false");
			}

			rs.close();
			stmt.close();
			
			//判斷是否查詢加班下班考勤資料
			if(find_ov_out_att_flag){
				
				sql = "" +
				" SELECT " +
				" EHF020401T0_02_EMP AS EMPLOYEE_ID, EHF020401T0_05 AS ATT_DATE, " +
				" EHF020401T0_08 AS ATT_TYPE, " +
				" DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d %H:%i:%s') AS ATT_DATETIME, " +
				" '01' AS DATA_TYPE " +
				" FROM EHF020401T0 " +
				" WHERE 1=1 " +
			    " AND EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
				" AND EHF020401T0_05 = '"+att_date+"' " +  //考勤日期
				" AND EHF020401T0_08 = '6' " +  //打卡狀態 - 加班下班
				" AND EHF020401T0_11 = '"+comp_id+"' " +  //公司代碼
				" UNION " +
				" SELECT " +
				" EHF020406T0_01 AS EMPLOYEE_ID, EHF020406T0_04 AS ATT_DATE, " +
				" EHF020406T0_05 AS ATT_TYPE, " +
				" DATE_FORMAT(EHF020406T0_04_DTE, '%Y/%m/%d %H:%i:%s') AS ATT_DATETIME, " +
				" CASE EHF020406T0_FIX WHEN true THEN '01' ELSE '02' END  AS DATA_TYPE " +
				" FROM EHF020406T0 " +
				" WHERE 1=1 " +
				" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
				" AND EHF020406T0_04 = '"+att_date+"' " +  //考勤日期
				" AND EHF020406T0_05 = '6' " +  //未打卡狀態 - 加班下班
				" AND EHF020406T0_07 = '"+comp_id+"' " +  //公司代碼
				"";
				
				// 執行SQL
				stmt = conn.createStatement(
					   ResultSet.TYPE_SCROLL_SENSITIVE,
					   ResultSet.CONCUR_READ_ONLY);
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					//有找到該考勤日期的加班下班考勤資料
					
					//判斷找到的加班下班考勤資料是否為未打卡資料
					if("01".equals(rs.getString("DATA_TYPE"))){
						//EHF020401T0 打卡資料
						
						//寫入加班下班打卡日期時間
						ov_out_datetime = rs.getString("ATT_DATETIME");
						
						//加班上班與加班下班皆有加班考勤資料 且 皆不是未打卡資料
						//需要產生 --> 加班出勤記錄
						return_map = this.generateOvertimeAttendanceRecord(
									 conn,
									 employee_id, 
									 ov_record_uid, 
									 att_date, ov_in_datetime, ov_out_datetime, 
									 class_out_datetime, 
									 comp_id);
						
					}else if("02".equals(rs.getString("DATA_TYPE"))){
						//EHF020406T0 未打卡資料
						//加班考勤其中一筆為未打卡考勤  --> 不產生加班出勤資料
						return_map.put("CHK_FLAG", "false");
					}
					
				}else{
					//未找到該考勤日期的加班上班考勤資料 --> 不產生加班出勤資料
					return_map.put("CHK_FLAG", "false");
				}
				
				rs.close();
				stmt.close();
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	/**
	 * 取得提前加班的加班記錄資訊
	 * @param att_date 考勤日期
	 * @param employee_id  員工工號
	 * @param class_in_datetime  班別的上班開始時間
	 * @param comp_id  公司代碼
	 * @return
	 */
	public Map<String, String> findBeforeOvertimeData(Connection conn,
												      String att_date, String employee_id,
													  String class_in_datetime,
													  String comp_id){
		
		Map<String, String> return_map = new HashMap<String, String>();
		
		try{
			//取得員工考勤當日的班別資料的上班時間 = calss_in_datetime
			
			//查詢加班記錄
			Map<String, String> ov_data_map = this.queryOvertimeRecord(
			conn,
			employee_id,att_date,
			" OV_START_DATETIME < " + "'"+class_in_datetime+"'",  //提前加班的加班記錄開始時間必須大於班別的上班時間
			" OV_END_DATETIME = " + "'"+class_in_datetime+"'",  //提前加班的加班記錄結束時間必須等於班別的上班時間
			comp_id);
			
			//判斷提前加班的加班記錄是否存在
			if(!"false".equals(ov_data_map.get("CHK_FLAG"))){
				//表示加班記錄存在
				//要建立提前加班的回傳資料Map
				return_map.put("ADVANCE_OVERTIME_SWITCH", "true");  //是否有提前加班 - 是
				return_map.put("OV_RECORD_UID", ov_data_map.get("OV_RECORD_UID"));  //加班記錄UID
				return_map.put("EMMLOYEE_ID", ov_data_map.get("EMMLOYEE_ID"));  //員工工號
				return_map.put("BEFORE_OVERTIME_START", ov_data_map.get("OVERTIME_START"));  //提前加班 - 加班開始時間
				return_map.put("BEFORE_OVERTIME_END", ov_data_map.get("OVERTIME_END"));  //提前加班 - 加班結束時間
				return_map.put("BEFORE_OVERTIME_BREAK_START", ov_data_map.get("OVERTIME_BREAK_START"));  //提前加班 - 加班休息開始時間
				return_map.put("BEFORE_OVERTIME_BREAK_END", ov_data_map.get("OVERTIME_BREAK_END"));  //提前加班 - 加班休息結束時間
				
			}else{
				//表示加班記錄 不存在
				//要建立提前加班的回傳資料Map
				return_map.put("ADVANCE_OVERTIME_SWITCH", "false");  //是否有提前加班 - 否
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}	
	
	/**
	 * 產生並判定提前加班記錄
	 * @param employee_id
	 * @param ov_record_uid
	 * @param ov_att_date
	 * @param ov_in_datetime
	 * @param class_in_datetime
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> generateBeforeOvertimeAttendanceRecord(
							   Connection conn,
			   				   String employee_id,
			   				   String ov_record_uid, 
			   				   String ov_att_date, 
			   				   String ov_in_datetime,
			   				   String class_in_datetime,
			   				   String comp_id){

		Map<String, String> return_map = new HashMap<String, String>();

		try{
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Calendar class_in_cal = tools.covertStringToCalendar(class_in_datetime, "yyyy/MM/dd HH:mm:ss");  //考勤日期員工班別的上班時間
			Calendar ov_in_cal = tools.covertStringToCalendar(ov_in_datetime, "yyyy/MM/dd HH:mm:ss");  //上班考勤時間
			
			//刪除之前的加班出勤資料 By ov_record_uid
			this.deleteOvertimeAttendanceRecord(conn, ov_record_uid, comp_id);
			
			//取的加班記錄資料
			Map<String, String> ov_record_map = this.queryOvertimeRecordByUID(conn, ov_record_uid, comp_id);
			
			Calendar ov_record_in_cal = tools.covertStringToCalendar(
			ov_record_map.get("OVERTIME_START"), "yyyy/MM/dd HH:mm:ss");  //提前加班記錄的加班開始時間
			
			Calendar ov_record_break_in_cal = tools.covertStringToCalendar(
			ov_record_map.get("OVERTIME_BREAK_START"), "yyyy/MM/dd HH:mm:ss");  //提前加班記錄的加班休息開始時間
			
			Calendar ov_record_break_out_cal = tools.covertStringToCalendar(
			ov_record_map.get("OVERTIME_BREAK_END"), "yyyy/MM/dd HH:mm:ss");  //提前加班記錄的加班休息結束時間
			
			//加班內扣時數
			float ov_de_hours = Float.parseFloat(ov_record_map.get("OVERTIME_DE_HOURS"));
			
			String ov_choose=ov_record_map.get("EHF020801T0_09");  //加班時數處理方式     20131007新增   BY 賴泓錡
			
			String EHF020801T0_10=ov_record_map.get("EHF020801T0_10");  //中午休息時間
			
			
			if(ov_in_cal.compareTo(ov_record_in_cal) <= 0){
				//Type A:判斷上班考勤是否符合提前加班(加班記錄)中設定的加班開始時間
				//if 符合 則已提前加班記錄的加班開始時間做為 => 加班出勤記錄(EHF020802T0)的加班開始時間
				//       以該考勤日期員工班別的上班時間作為 => 加班出勤記錄(EHF020802T0)的加班結束時間
				
				//判斷加班是否有休息時間區間
				if(ov_record_break_in_cal.compareTo(ov_record_break_out_cal) == 0){
					//產生加班出勤記錄
					return_map =  this.putOvertimeAttendanceRecord(
								  conn,
								  employee_id, ov_record_uid, 
								  ov_att_date, 
								  ov_record_map.get("OVERTIME_START"), 
								  class_in_datetime,ov_choose,
								  (float)0,
								  comp_id,EHF020801T0_10);
				}else{
					//產生加班出勤記錄
					return_map =  this.putOvertimeAttendanceRecord(
								  conn,
								  employee_id, ov_record_uid, 
								  ov_att_date, 
								  ov_record_map.get("OVERTIME_START"), 
								  class_in_datetime,ov_choose,
								  ov_de_hours,
								  comp_id,EHF020801T0_10);
				}
				
				/*
				//產生加班出勤記錄
				return_map =  this.putOvertimeAttendanceRecord(
							  employee_id, ov_record_uid, 
							  ov_att_date, 
							  ov_record_map.get("OVERTIME_START"), 
							  class_in_datetime,
							  ov_de_hours,
							  comp_id);
				*/
				
				return_map.put("CHK_FLAG", "true");
				
			}else if(ov_in_cal.compareTo(ov_record_in_cal) > 0 
					 && ov_in_cal.compareTo(class_in_cal) < 0){
				//Type B:判斷上班考勤若是 大於提前加班記錄的加班開始時間 且 小於 考勤日期員工班別的上班時間
				// 則 以上班考勤時間做為 => 加班出勤記錄(EHF020802T0)的加班開始時間
				//   以考勤日期員工班別的上班時間做為 => 加班出勤記錄(EHF020802T0)的加班結束時間
				
				//判斷加班是否有休息時間區間
				if(ov_record_break_in_cal.compareTo(ov_record_break_out_cal) == 0){
					//產生加班出勤記錄
					return_map =  this.putOvertimeAttendanceRecord(
								  conn,
								  employee_id, ov_record_uid, 
								  ov_att_date, 
								  ov_in_datetime, 
								  class_in_datetime,ov_choose,
								  (float)0,
								  comp_id,EHF020801T0_10);
				}else{
					//判斷提前加班上班時間是否大於加班休息開始時間
					if(ov_in_cal.compareTo(ov_record_break_in_cal) < 0){
						//表示 提前加班上班時間 在 加班休息開始時間 之前
						//要扣除完整的加班內扣時數
						//產生加班出勤記錄
						return_map =  this.putOvertimeAttendanceRecord(
									  conn,
									  employee_id, ov_record_uid, 
									  ov_att_date, 
									  ov_in_datetime, 
									  class_in_datetime,ov_choose,
									  ov_de_hours,
									  comp_id,EHF020801T0_10);
						
					}else if(ov_in_cal.compareTo(ov_record_break_in_cal) >= 0 
							 && ov_in_cal.compareTo(ov_record_break_out_cal) < 0 ){
						//表示 提前加班上班時間 在 加班休息區間內
						//產生加班出勤記錄
						return_map =  this.putOvertimeAttendanceRecord(
									  conn,
									  employee_id, ov_record_uid, 
									  ov_att_date, 
									  ov_record_map.get("OVERTIME_BREAK_END"), 
									  class_in_datetime,ov_choose,
									  (float)0,
									  comp_id,EHF020801T0_10);
						
					}else{
						//表示 提前加班上班時間 在 加班休息區間之後
						//產生加班出勤記錄
						return_map =  this.putOvertimeAttendanceRecord(
									  conn,
									  employee_id, ov_record_uid, 
									  ov_att_date, 
									  ov_in_datetime, 
									  class_in_datetime,ov_choose,
									  (float)0,
									  comp_id,EHF020801T0_10);
						
					}
				}
				
				/*
				//產生加班出勤記錄
				return_map =  this.putOvertimeAttendanceRecord(
							  employee_id, ov_record_uid, 
							  ov_att_date, 
							  ov_in_datetime, 
							  class_in_datetime, 
							  comp_id);
				*/
				
				return_map.put("CHK_FLAG", "true");
				
			}else{
				//Type C:判斷考勤上班若是 大於等於 考勤日期員工班別的上班時間
				// 則 表示此筆提前加班記錄不成立 => 員工未依加班單所設定的加班區間進行打卡
				// => 員工未加班, 不產生加班出勤記錄(EHF020802T0)
				
				//Do Nothing!!
				return_map.put("CHK_FLAG", "false");
				
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 取得下班後加班的加班記錄資訊
	 * @param att_date
	 * @param employee_id
	 * @param class_out_datetime
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> findOvertimeData(
							   Connection conn,
							   String att_date,
							   String employee_id, 
							   String class_out_datetime, 
							   String comp_id) {

		Map<String, String> return_map = new HashMap<String, String>();

		try {
			// 取得員工考勤當日的班別資料的下班時間 = calss_out_datetime
			
			//處理班別下班時間資訊
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Calendar class_end_cal = tools.covertStringToCalendar(class_out_datetime, "yyyy/MM/dd HH:mm:ss");  //班別下班時間
			class_end_cal.add(Calendar.HOUR_OF_DAY, 2);
			String class_out_datetime_with_range = tools.covertDateToString(class_end_cal.getTime(), "yyyy/MM/dd HH:mm:ss");  //班別下班時間+2小時
			
			// 查詢加班記錄
			Map<String, String> ov_data_map = this.queryOvertimeRecord(
												   conn,
												   employee_id, att_date,
												   " OV_START_DATETIME < " + 
												   "'"+class_out_datetime_with_range+"'", //下班後加班的加班記錄開始時間必須小於等於(班別的下班時間+2小時以內)
												   " OV_END_DATETIME >= " + 
												   "'"+class_out_datetime+"'", // 提前加班的加班記錄結束時間必須等於班別的上班時間
												   comp_id);

			// 判斷下班後加班的加班記錄是否存在
			if (!"false".equals(ov_data_map.get("CHK_FLAG"))) {
				// 表示加班記錄存在
				// 要建立提前加班的回傳資料Map
				return_map.put("OVERTIME_SWITCH", "true"); // 是否有下班後加班 - 是
				
				return_map.put("OV_RECORD_UID", ov_data_map.get("OV_RECORD_UID")); // 加班記錄UID
				return_map.put("EMMLOYEE_ID", ov_data_map.get("EMMLOYEE_ID")); // 員工工號
				return_map.put("OVERTIME_START", ov_data_map.get("OVERTIME_START")); //下班後加班 - 加班開始時間
				return_map.put("OVERTIME_END", ov_data_map.get("OVERTIME_END")); //下班後加班 - 加班結束時間
				return_map.put("OVERTIME_BREAK_START", ov_data_map.get("OVERTIME_BREAK_START"));  //下班後加班 - 加班休息開始時間
				return_map.put("OVERTIME_BREAK_END", ov_data_map.get("OVERTIME_BREAK_END"));  //下班後加班 - 加班休息結束時間
				return_map.put("EHF020801T0_09", ov_data_map.get("EHF020801T0_09")); //加班時數處理方式     20131007新增   BY 賴泓錡

			} else {
				// 表示加班記錄 不存在
				// 要建立下班後加班的回傳資料Map
				return_map.put("OVERTIME_SWITCH", "false"); // 是否有下班後加班 - 否
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	/**
	 * 產生並判定下班後加班記錄
	 * @param employee_id
	 * @param ov_record_uid
	 * @param ov_att_date
	 * @param ov_in_datetime
	 * @param ov_out_datetime
	 * @param class_out_datetime
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> generateOvertimeAttendanceRecord(
							   Connection conn,
							   String employee_id, String ov_record_uid, 
							   String ov_att_date,
							   String ov_in_datetime, String ov_out_datetime, 
							   String class_out_datetime, 
							   String comp_id) {

		Map<String, String> return_map = new HashMap<String, String>();

		try {
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Calendar class_out_cal = tools.covertStringToCalendar(
					 class_out_datetime, "yyyy/MM/dd HH:mm:ss"); // 考勤日期員工班別的下班時間
			Calendar ov_in_cal = tools.covertStringToCalendar(
					 ov_in_datetime, "yyyy/MM/dd HH:mm:ss"); // 加班上班考勤時間
			Calendar ov_out_cal = tools.covertStringToCalendar(
					 ov_out_datetime, "yyyy/MM/dd HH:mm:ss"); // 加班下班考勤時間

			// 取得加班記錄資料
			Map<String, String> ov_record_map = this.queryOvertimeRecordByUID(
								conn,
								ov_record_uid, comp_id);
			Calendar ov_record_in_cal = tools.covertStringToCalendar(
					 ov_record_map.get("OVERTIME_START"), "yyyy/MM/dd HH:mm:ss"); //下班後加班記錄的加班開始時間
			Calendar ov_record_out_cal = tools.covertStringToCalendar(
					 ov_record_map.get("OVERTIME_END"), "yyyy/MM/dd HH:mm:ss"); //下班後加班記錄的加班結束時間
			
			Calendar ov_record_break_in_cal = tools.covertStringToCalendar(
					 ov_record_map.get("OVERTIME_BREAK_START"), "yyyy/MM/dd HH:mm:ss");  //下班後加班記錄的加班休息開始時間
					
			Calendar ov_record_break_out_cal = tools.covertStringToCalendar(
					 ov_record_map.get("OVERTIME_BREAK_END"), "yyyy/MM/dd HH:mm:ss");  //下班後加班記錄的加班休息結束時間
			
			String ov_choose=ov_record_map.get("EHF020801T0_09");  //加班時數處理方式     20131007新增   BY 賴泓錡	
			
			String EHF020801T0_10=ov_record_map.get("EHF020801T0_10");  //中午休息時間
			
			
			//加班內扣時數
			float ov_de_hours = Float.parseFloat(ov_record_map.get("OVERTIME_DE_HOURS"));
			
			//判斷加班上班
			if(ov_in_cal.compareTo(ov_record_in_cal) <= 0 && ov_in_cal.compareTo(class_out_cal) >= 0){
				//加班上班打卡時間在加班上班時間之前 且 加班上班打卡時間在班別下班時間之後
				
				//判斷加班下班
				if(ov_out_cal.compareTo(ov_record_out_cal) >= 0){
					//Type A:加班上班與加班下班考勤符合 下班後加班記錄設定的加班開始時間與加班結束時間
					// if 符合 則以加班記錄的加班開始時間做為 => 加班出勤記錄(EHF020802T0)的加班開始時間
					//          以加班記錄的加班結束時間做為 => 加班出勤記錄(EHF020802T0)的加班結束時間
					
					//判斷加班是否有休息時間區間
					if(ov_record_break_in_cal.compareTo(ov_record_break_out_cal) == 0){
						// 產生加班出勤記錄
						return_map = this.putOvertimeAttendanceRecord(
									 conn,
									 employee_id,
									 ov_record_uid, ov_att_date, 
									 ov_record_map.get("OVERTIME_START"), 
									 ov_record_map.get("OVERTIME_END"),
									 ov_choose,
									 (float)0,
									 comp_id,EHF020801T0_10);
					}else{
						// 產生加班出勤記錄
						return_map = this.putOvertimeAttendanceRecord(
									 conn,
									 employee_id,
									 ov_record_uid, ov_att_date, 
									 ov_record_map.get("OVERTIME_START"), 
									 ov_record_map.get("OVERTIME_END"),
									 ov_choose,
									 ov_de_hours,
									 comp_id,EHF020801T0_10);
					}
					
					/*
					// 產生加班出勤記錄
					return_map = this.putOvertimeAttendanceRecord(
								 employee_id,
								 ov_record_uid, ov_att_date, 
								 ov_record_map.get("OVERTIME_START"), 
								 ov_record_map.get("OVERTIME_END"),
								 comp_id);
	`				*/
					
					return_map.put("CHK_FLAG", "true");
					
				}else if(ov_out_cal.compareTo(ov_record_out_cal) < 0){
					//Type B:加班上班考勤符合加班記錄
					//       加班下班考勤小於加班記錄結束時間 且 大於加班記錄開始時間
					//       則 以加班記錄的加班開始時間做為 => 加班出勤記錄(EHF020802T0)的加班開始時間
					//         以加班下班考勤時間做為 => 加班出勤記錄(EHF020802T0)的加班結束時間
					
					//判斷加班是否有休息時間區間
					if(ov_record_break_in_cal.compareTo(ov_record_break_out_cal) == 0){
						// 產生加班出勤記錄
						return_map = this.putOvertimeAttendanceRecord(
									 conn,
									 employee_id,
									 ov_record_uid, ov_att_date, 
									 ov_record_map.get("OVERTIME_START"), 
									 ov_out_datetime,
									 ov_choose,
									 (float)0,
									 comp_id,EHF020801T0_10);
					}else{
						if(ov_out_cal.compareTo(ov_record_break_in_cal) < 0){
							//表示 加班下班打卡時間 在 加班休息開始時間 之前
							//不用扣除加班內扣時間
							// 產生加班出勤記錄
							return_map = this.putOvertimeAttendanceRecord(
										 conn,
										 employee_id,
										 ov_record_uid, ov_att_date, 
										 ov_record_map.get("OVERTIME_START"), 
										 ov_out_datetime,
										 ov_choose,
										 (float)0,
										 comp_id,EHF020801T0_10);
							
						}else if(ov_out_cal.compareTo(ov_record_break_in_cal) >= 0 && ov_out_cal.compareTo(ov_record_break_out_cal) < 0){
							//表示加班下班打卡時間 在 加班休息區間之內
							//不用扣除加班內扣時間
							// 產生加班出勤記錄
							return_map = this.putOvertimeAttendanceRecord(
										 conn,
										 employee_id,
										 ov_record_uid, ov_att_date, 
										 ov_record_map.get("OVERTIME_START"), 
										 ov_record_map.get("OVERTIME_BREAK_START"),
										 ov_choose,
										 (float)0,
										 comp_id,EHF020801T0_10);
							
						}else if(ov_out_cal.compareTo(ov_record_break_out_cal) >= 0){
							//表示加班下班打卡時間 在加班休息結束時間之後
							//扣除完整加班內扣時間
							// 產生加班出勤記錄
							return_map = this.putOvertimeAttendanceRecord(
										 conn,
										 employee_id,
										 ov_record_uid, ov_att_date, 
										 ov_record_map.get("OVERTIME_START"), 
										 ov_out_datetime,
										 ov_choose,
										 ov_de_hours,
										 comp_id,EHF020801T0_10);
							
						}
					}
					
					/*
					// 產生加班出勤記錄
					return_map = this.putOvertimeAttendanceRecord(
								 employee_id,
								 ov_record_uid, ov_att_date, 
								 ov_record_map.get("OVERTIME_START"), 
								 ov_out_datetime,
								 ov_de_hours,
								 comp_id);
					*/
					
					return_map.put("CHK_FLAG", "true");
				}
				
			}else if(ov_in_cal.compareTo(ov_record_in_cal) > 0 && ov_in_cal.compareTo(class_out_cal) >= 0){
				//加班上班打卡時間 在 加班上班時間之後 且 加班上班打卡時間 在 班別下班時間之後
				
				//判斷加班下班
				if(ov_out_cal.compareTo(ov_record_out_cal) >= 0){
					//Type C:加班上班考勤大於加班記錄開始時間 且 小於加班記錄結束時間
					//       加班下班考勤符合加班記錄
					//       則 以加班上班考勤時間做為 => 加班出勤記錄(EHF020802T0)的加班開始時間
					//         以加班記錄的加班結束時間做為 => 加班出勤記錄(EHF020802T0)的加班結束時間
					
					//判斷加班是否有休息時間區間
					if(ov_record_break_in_cal.compareTo(ov_record_break_out_cal) == 0){
						
						// 產生加班出勤記錄
						return_map = this.putOvertimeAttendanceRecord(
									 conn,
									 employee_id,
									 ov_record_uid, ov_att_date, 
									 ov_in_datetime, 
									 ov_record_map.get("OVERTIME_END"),
									 ov_choose,
									 (float)0,
									 comp_id,EHF020801T0_10);
						
					}else{
						
						if(ov_in_cal.compareTo(ov_record_break_in_cal) < 0){
							//表示加班上班時間在 加班休息開始時間 之前
							//要扣除完整加班內扣時數
							// 產生加班出勤記錄
							return_map = this.putOvertimeAttendanceRecord(
										 conn,
										 employee_id,
										 ov_record_uid, ov_att_date, 
										 ov_in_datetime, 
										 ov_record_map.get("OVERTIME_END"),
										 ov_choose,
										 ov_de_hours,
										 comp_id,EHF020801T0_10);
							
						}else if(ov_in_cal.compareTo(ov_record_break_in_cal) >= 0 && ov_in_cal.compareTo(ov_record_break_out_cal) < 0){
							//表示加班上班時間在 加班休息區間之內
							//不用扣除加班內扣時數
							// 產生加班出勤記錄
							return_map = this.putOvertimeAttendanceRecord(
										 conn,
										 employee_id,
										 ov_record_uid, ov_att_date, 
										 ov_record_map.get("OVERTIME_BREAK_START"), 
										 ov_record_map.get("OVERTIME_END"),
										 ov_choose,
										 (float)0,
										 comp_id,EHF020801T0_10);
							
						}else if(ov_in_cal.compareTo(ov_record_break_out_cal) >= 0){
							//表示加班上班時間在 加班休息結束時間之後
							//不用扣除加班內扣時數
							// 產生加班出勤記錄
							return_map = this.putOvertimeAttendanceRecord(
										 conn,
										 employee_id,
										 ov_record_uid, ov_att_date, 
										 ov_in_datetime, 
										 ov_record_map.get("OVERTIME_END"),
										 ov_choose,
										 (float)0,
										 comp_id,EHF020801T0_10);
						}
						
					}
					
					/*
					// 產生加班出勤記錄
					return_map = this.putOvertimeAttendanceRecord(
								 employee_id,
								 ov_record_uid, ov_att_date, 
								 ov_in_datetime, 
								 ov_record_map.get("OVERTIME_END"),
								 comp_id);
					*/
					
					return_map.put("CHK_FLAG", "true");
					
				}else if(ov_out_cal.compareTo(ov_record_out_cal) < 0){
					//Type D:加班上班考勤大於加班記錄開始時間 且 小於加班記錄結束時間
					//       加班下班考勤小於加班記錄結束時間 且 大於加班記錄開始時間
					//	       則 以加班上班考勤時間做為 => 加班出勤記錄(EHF020802T0)的加班開始時間
					//	             以加班下班考勤時間做為 => 加班出勤記錄(EHF020802T0)的加班結束時間
					
					//判斷加班是否有休息時間區間
					if(ov_record_break_in_cal.compareTo(ov_record_break_out_cal) == 0){
						
						// 產生加班出勤記錄
						return_map = this.putOvertimeAttendanceRecord(
									 conn,
									 employee_id,
									 ov_record_uid, ov_att_date, 
									 ov_in_datetime, 
									 ov_out_datetime,
									 ov_choose,
									 (float)0,
									 comp_id,EHF020801T0_10);
						
						return_map.put("CHK_FLAG", "true");
						
					}else{
						
						if(ov_in_cal.compareTo(ov_record_break_in_cal) < 0 ){
							//表示加班上班打卡時間 在 加班休息開始時間 之前
						
							if(ov_out_cal.compareTo(ov_record_break_in_cal) < 0){
								//表示 加班下班打卡時間 在 加班休息開始時間 之前
								//不用扣除加班內扣時數
								// 產生加班出勤記錄
								return_map = this.putOvertimeAttendanceRecord(
											 conn,
											 employee_id,
											 ov_record_uid, ov_att_date, 
											 ov_in_datetime, 
											 ov_out_datetime,
											 ov_choose,
											 (float)0,
											 comp_id,EHF020801T0_10);
								
								return_map.put("CHK_FLAG", "true");
								
							}else if(ov_out_cal.compareTo(ov_record_break_in_cal) >= 0 
									 && ov_out_cal.compareTo(ov_record_break_out_cal) < 0){
								//表示 加班下班打卡時間 在 加班休息區間之內
								//不用扣除加班內扣時數
								// 產生加班出勤記錄
								return_map = this.putOvertimeAttendanceRecord(
											 conn,
											 employee_id,
											 ov_record_uid, ov_att_date, 
											 ov_in_datetime, 
											 ov_record_map.get("OVERTIME_BREAK_START"),
											 ov_choose,
											 (float)0,
											 comp_id,EHF020801T0_10);
								
								return_map.put("CHK_FLAG", "true");
								
							}else if(ov_out_cal.compareTo(ov_record_break_out_cal) >= 0){
								//表示 加班下班打卡時間 在 加班休息結束時間之後
								//扣除完整加班時數
								// 產生加班出勤記錄
								return_map = this.putOvertimeAttendanceRecord(
											 conn,
											 employee_id,
											 ov_record_uid, ov_att_date, 
											 ov_in_datetime, 
											 ov_out_datetime,
											 ov_choose,
											 ov_de_hours,
											 comp_id,EHF020801T0_10);
								
								return_map.put("CHK_FLAG", "true");
								
							}
						}else if(ov_in_cal.compareTo(ov_record_break_in_cal) >= 0 
								 && ov_in_cal.compareTo(ov_record_break_out_cal) < 0){
							//表示 加班上班時間 在 加班休息區間 之內
							
							if(ov_out_cal.compareTo(ov_record_break_in_cal) > 0
							   && ov_out_cal.compareTo(ov_record_break_out_cal) <= 0 ){
								//表示加班下班打卡時間 在 加班休息區間之內
								//等於沒有加班
								//不用扣除加班內扣時數
								
								return_map.put("CHK_FLAG", "false");
								
							}else if(ov_out_cal.compareTo(ov_record_break_out_cal) > 0){
								//表示加班下班打卡時間 在 加班休息結束時間之後
								//不用扣除加班內扣時數
								// 產生加班出勤記錄
								return_map = this.putOvertimeAttendanceRecord(
											 conn,
											 employee_id,
											 ov_record_uid, ov_att_date, 
											 ov_record_map.get("OVERTIME_BREAK_END"), 
											 ov_out_datetime,
											 ov_choose,
											 (float)0,
											 comp_id,EHF020801T0_10);
								
								return_map.put("CHK_FLAG", "true");
								
							}
						}else if(ov_in_cal.compareTo(ov_record_break_out_cal) > 0){
							//表示 加班上班打卡時間 在 加班休息結束時間之後
							
							if(ov_out_cal.compareTo(ov_record_break_out_cal) > 0){
								//表示加班下班打卡時間 在加班休息結束時間之後
								//不用扣除加班內扣時數
								// 產生加班出勤記錄
								return_map = this.putOvertimeAttendanceRecord(
											 conn,
											 employee_id,
											 ov_record_uid, ov_att_date, 
											 ov_in_datetime, 
											 ov_out_datetime,
											 ov_choose,
											 (float)0,
											 comp_id,EHF020801T0_10);
								
								return_map.put("CHK_FLAG", "true");
								
							}
							
						}
						
					}
					
					/*
					// 產生加班出勤記錄
					return_map = this.putOvertimeAttendanceRecord(
								 employee_id,
								 ov_record_uid, ov_att_date, 
								 ov_in_datetime, 
								 ov_out_datetime,
								 comp_id);
					
					
					return_map.put("CHK_FLAG", "true");
					*/
				}
				
			}else{
				//加班上班考勤資料有問題
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	/**
	 * 取得假日加班的加班記錄資訊
	 * @param att_date
	 * @param employee_id
	 * @param class_out_datetime
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> findHaOvertimeData(
							   Connection conn,
							   String att_date, String employee_id, String comp_id) {

		Map<String, String> return_map = new HashMap<String, String>();

		try{
			//查詢假日加班記錄
			Map<String, String> ov_data_map = this.queryOvertimeRecord(
			conn,
			employee_id, att_date,
			" DATE_FORMAT(EHF020801T0_05, '%Y/%m/%d') = " + 
			" '"+ att_date + "' ",  //假日加班記錄需要符合考勤日期
			" DATE_FORMAT(EHF020801T0_06, '%Y/%m/%d') = " + 
			" '"+ att_date + "' ",  //假日加班記錄需要符合考勤日期
			comp_id);

			// 判斷假日加班的加班記錄是否存在
			if (!"false".equals(ov_data_map.get("CHK_FLAG"))) {
				// 表示加班記錄存在
				// 要建立假日加班的回傳資料Map
				return_map.put("HA_OVERTIME_SWITCH", "true"); // 是否有假日加班 - 是

				return_map.put("OV_RECORD_UID", ov_data_map.get("OV_RECORD_UID"));  // 加班記錄UID
				return_map.put("EMMLOYEE_ID", ov_data_map.get("EMMLOYEE_ID"));  //員工工號
				return_map.put("HA_OVERTIME_START", ov_data_map.get("OVERTIME_START"));  //假日加班 - 加班開始時間
				return_map.put("HA_OVERTIME_END", ov_data_map.get("OVERTIME_END"));  //假日加班 - 加班結束時間
				return_map.put("HA_OVERTIME_BREAK_START", ov_data_map.get("OVERTIME_BREAK_START"));  //假日加班 - 加班休息開始時間
				return_map.put("HA_OVERTIME_BREAK_END", ov_data_map.get("OVERTIME_BREAK_END"));  //假日加班 - 加班休息結束時間
				return_map.put("EHF020801T0_09", ov_data_map.get("EHF020801T0_09")); //加班時數處理方式     20131007新增   BY 賴泓錡
			} else {
				// 表示加班記錄 不存在
				// 要建立假日加班的回傳資料Map
				return_map.put("HA_OVERTIME_SWITCH", "false"); // 是否有假日加班 - 否
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	

	/**
	 * 產生加班出勤記錄
	 * @param conn
	 * @param employee_id
	 * @param ov_record_uid
	 * @param ov_att_date
	 * @param ov_start_datetime
	 * @param ov_end_datetime
	 * @param ov_choose  //加班時數處理方式     20131007新增   BY 賴泓錡
	 * @param ov_de_hours
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> putOvertimeAttendanceRecord(
							   Connection conn,
			   				   String employee_id,
			   				   String ov_record_uid, String ov_att_date,
			   				   String ov_start_datetime, String ov_end_datetime,String ov_choose,
			   				   float ov_de_hours,
			   				   String comp_id ,String EHF020802T0_10){

		Map<String, String> return_map = new HashMap<String, String>();

		try{
			//建立paramMap
			Map<String, String> paramMap = new HashMap<String, String>();
			
			//取得實際加班資料
			paramMap.put("EHF020802T0_02", employee_id);  //員工工號
			paramMap.put("EHF020802T0_03", ov_record_uid);  //加班記錄UID(Mapping用)
			paramMap.put("EHF020802T0_04", ov_att_date);  //考勤日期(加班日期)
			paramMap.put("EHF020802T0_05", ov_start_datetime);  //實際加班起始時間
			paramMap.put("EHF020802T0_06", ov_end_datetime);  //實際加班結束時間
			paramMap.put("EHF020802T0_09", ov_choose); //加班時數處理方式     20131007新增   BY 賴泓錡
			
			paramMap.put("EHF020802T0_10", EHF020802T0_10); //中午休息時間
			//利用實際加班開始時間與結束時間計算加班時數
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Calendar ov_start_cal = tools.covertStringToCalendar(ov_start_datetime, "yyyy/MM/dd HH:mm:ss");  //實際加班開始時間
			Calendar ov_end_cal = tools.covertStringToCalendar(ov_end_datetime, "yyyy/MM/dd HH:mm:ss");  //實際加班結束時間
			
			/*
			long ov_start_time_millis = ov_start_cal.getTimeInMillis();
			long ov_end_time_millis = ov_end_cal.getTimeInMillis();
			
			//實際加班區間 = ov_end_time_millis - ov_start_time_millis
			float ov_hours = ((float)(ov_end_time_millis - ov_start_time_millis)/(1000*60*60));  //計算實際加班時數
			
			//****實際加班時數必須是加班基本時數的倍數****
			*/
			
			//計算實際加班時數
			float ov_hours = this.caculateDiffHourWithFIX(conn, ov_start_cal, ov_end_cal, comp_id);
			
			paramMap.put("EHF020802T0_07", ov_hours+"");  //實際加班時數
			paramMap.put("EHF020802T0_07_DE", ov_de_hours+"");  //實際加班內扣時數
			
			paramMap.put("EHF020802T0_08", comp_id);  //公司代碼
			paramMap.put("COMP_ID", comp_id);  //公司代碼


			//建立加班出勤記錄
			return_map =  this.insertOvertimeAttendanceRecord(conn, paramMap);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 計算兩個Calendar之間的時數, 並使用最低計薪時數進行修正, 不足最低計薪時數的部分則捨去
	 * @param begin_cal
	 * @param end_cal
	 * @param comp_id
	 * @return
	 */
	protected float caculateDiffHourWithFIX( Connection conn, Calendar begin_cal, Calendar end_cal, String comp_id){
		
		float hour = 0;
		float min_pay_hour = 0;
		int times = 0;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		//Connection local_conn = null;  //資料庫連線
		
		//處理資料庫連線
		try{
			/*
			//調整Connection來源
			if(this.outside_conn != null && !this.outside_conn.isClosed()){
				//使用外部Connection
				local_conn = this.outside_conn;
			}else{
				local_conn = this.base_tools.getConn();
			}
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			
			//計算兩個Calendar之間的時數
			hour = tools.caculateDiffHour(begin_cal, end_cal);
			
			//取得最低付薪時數
			min_pay_hour = Float.parseFloat(tools.getSysParam(conn, comp_id, "MIN_PAY_HOUR"));
			
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
	 * 新增加班出勤記錄
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> insertOvertimeAttendanceRecord(Connection conn, Map<String, String> paramMap){
		
		Map<String, String> return_map = new HashMap<String, String>();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		String sql = "";
		String show_sql = "";
		String user_create = "SYSTEM";
		
		try{
			//Add
			sql = "" +
			" INSERT INTO EHF020802T0 " +
			" ( " +
			" EHF020802T0_01, EHF020802T0_02, EHF020802T0_03, " +
			" EHF020802T0_04, " +
			" EHF020802T0_05, EHF020802T0_06, " +
			" EHF020802T0_07, " +
			" EHF020802T0_07_DE, " +
			" EHF020802T0_08, " +
			" EHF020802T0_09, " +//加班時數處理方式     20131007新增   BY 賴泓錡
			" EHF020802T0_10, " +//中午加班時數
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, ?, " +
			" ?, " +
			" ?, ?, " +
			" ?, " +
			" ?, " +
			" ?, " +
			" ?, " +
			" ?, " +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			/*
			//調整Connection來源
			Connection local_conn = null;
			if(this.outside_conn != null && !this.outside_conn.isClosed()){
				//使用外部Connection
				local_conn = this.outside_conn;
			}else{
				local_conn = this.base_tools.getConn();
			}
			*/
			
			//執行SQL
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			String EHF020802T0_01 = "";  //加班出勤記錄UID
			//使用 CodeRules 做單號取得的動作
			//EHF020802T0_01 = EMS_GetCodeRules.getInstance().getCodeRule("EHF020802T0", paramMap.get("COMP_ID"));
			EHF020802T0_01 = tools.createEMSUID(conn, "OT", "EHF020802T0", "EHF020802T0_01", paramMap.get("COMP_ID"));
			
			p6stmt.setString(indexid, EHF020802T0_01);  //加班出勤記錄UID
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020802T0_02"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020802T0_03"));  //加班記錄UID(Mapping用)
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020802T0_04"));  //考勤日期(加班日期)
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020802T0_05"));  //實際加班起始時間
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020802T0_06"));  //實際加班結束時間
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020802T0_07"));  //實際加班時數
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020802T0_07_DE"));  //實際加班內扣時數
			indexid++;
			p6stmt.setString(indexid, paramMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020802T0_09"));  //加班時數處理方式     20131007新增   BY 賴泓錡
			indexid++;
			
			p6stmt.setFloat(indexid, Float.valueOf(paramMap.get("EHF020802T0_10")));  //加班時數處理方式     20131007新增   BY 賴泓錡
			indexid++;
			
			
			if( !"".equals(paramMap.get("USER_NAME")) && paramMap.get("USER_NAME") != null ){
				user_create = paramMap.get("USER_NAME");
			}else{
				user_create = "SYSTEM";
			}
			
			p6stmt.setString(indexid, user_create);  //資料建立者
			indexid++;
			p6stmt.setString(indexid, user_create);  //資料修改者
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
			/*
			//未使用外部Connection才可更新資料庫
			if(this.outside_conn == null){
				//更新資料庫
				this.base_tools.commit();
			}
			*/
			conn.commit();
			/*
			//記錄新增訊息
			this.base_tools.writeADDMSG("EMS_OvertimeAttendanceHandle().insertOvertimeAttendanceRecord()", show_sql, 
										paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
			
		}catch(Exception e){
			
			/*
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EMS_OvertimeAttendanceHandle().insertOvertimeAttendanceRecord()", show_sql+", "+e.getMessage(), 
										   paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										   paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
			
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 移除加班出勤記錄
	 * @param employee_id
	 * @param ov_att_date
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> removeOvertimeAttendanceRecord(
							   Connection conn,
			   				   String employee_id, String ov_att_date, 
			   				   String comp_id){

		Map<String, String> return_map = new HashMap<String, String>();

		try{
			//建立paramMap
			Map<String, String> paramMap = new HashMap<String, String>();

			//取得實際加班資料
			
			if(!"".equals(employee_id) && employee_id != null){
				paramMap.put("EHF020802T0_02", employee_id);  //員工工號
			}
			
			paramMap.put("EHF020802T0_04", ov_att_date);  //考勤日期(加班日期)
			paramMap.put("EHF020802T0_08", comp_id);  //公司代碼
			paramMap.put("COMP_ID", comp_id);  //公司代碼

			//刪除加班出勤記錄
			this.deleteOvertimeAttendanceRecord(conn, paramMap);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 刪除加班出勤記錄
	 * @param ov_record_uid
	 * @param comp_id
	 */
	public void deleteOvertimeAttendanceRecord(Connection conn, String ov_record_uid, String comp_id) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			List<String> sql_list = new ArrayList<String>();

			//DELETE EHF020802T0
			sql = "" +
			" DELETE FROM EHF020802T0 " +
			" WHERE 1=1 " +
			" AND EHF020802T0_03 = '"+ov_record_uid+"' " +  //加班記錄UID
			" AND EHF020802T0_08 = '"+comp_id+"' ";  //公司代碼
			sql_list.add(sql);
			
			/*
			//調整Connection來源
			Connection local_conn = null;
			if(this.outside_conn != null && !this.outside_conn.isClosed()){
				//使用外部Connection
				local_conn = this.outside_conn;
			}else{
				local_conn = this.base_tools.getConn();
			}
			*/
			
			//執行刪除
			int[] dataCount_array = this.base_tools.executeBatchSQL(conn, sql_list);
			int dataCount = 0;
			int mainDataCount = 0;
			for(int i=0; i<dataCount_array.length; i++){
				dataCount += dataCount_array[i];
				mainDataCount = dataCount_array[i];
			}
			
			/*
			//未使用外部Connection才可更新資料庫
			if(this.outside_conn == null){
				//更新資料庫
				this.base_tools.commit();
			}
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 刪除加班出勤記錄
	 * @param paramMap
	 */
	public void deleteOvertimeAttendanceRecord(Connection conn, Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			List<String> sql_list = new ArrayList<String>();

			//DELETE EHF020802T0
			sql = "" +
			" DELETE FROM EHF020802T0 " +
			" WHERE 1=1 ";
			
			if(paramMap.containsKey("EHF020802T0_02")){
				sql += "" +
				" AND EHF020802T0_02 = '"+paramMap.get("EHF020802T0_02")+"' ";  //員工工號
			}
			
			
			sql += "" +
			" AND EHF020802T0_04 = '"+paramMap.get("EHF020802T0_04")+"' " +  //考勤日期(加班日期)
			" AND EHF020802T0_08 = '"+paramMap.get("COMP_ID")+"' ";  //公司代碼
			sql_list.add(sql);
			
			/*
			//調整Connection來源
			Connection local_conn = null;
			if(this.outside_conn != null && !this.outside_conn.isClosed()){
				//使用外部Connection
				local_conn = this.outside_conn;
			}else{
				local_conn = this.base_tools.getConn();
			}
			*/
			
			//執行刪除
			int[] dataCount_array = this.base_tools.executeBatchSQL(conn, sql_list);
			int dataCount = 0;
			int mainDataCount = 0;
			for(int i=0; i<dataCount_array.length; i++){
				dataCount += dataCount_array[i];
				mainDataCount = dataCount_array[i];
			}
			
			paramMap.put("MAIN_DATA_COUNT", mainDataCount+"");
			
			/*
			//未使用外部Connection才可更新資料庫
			if(this.outside_conn == null){
				//更新資料庫
				this.base_tools.commit();
			}
			*/
			
			/*
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EMS_OvertimeAttendanceHandle().deleteOvertimeAttendanceRecord()", sql+" total delete "+dataCount+"rows data", 
										   paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										   paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
			
		}catch(Exception e){
			
			/*
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EMS_OvertimeAttendanceHandle().deleteOvertimeAttendanceRecord()", sql+", "+e.getMessage(), 
											  paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
											  paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
			
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return this.base_tools.isClosed();
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.base_tools.close();
	}
	
	
	/**
	 * 利用加班費規則裡的前、後加班小時  查詢員工加班時數資訊
	 * @param conn
	 * @param dept_id
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @param befort_time
	 * @param after_time
	 * @param choose_type
	 * @param comp_id
	 * @return   20131007新增   BY 賴泓錡
	 */
	
	public List<Map<String, String>> queryOvertimeCollectHours(Connection conn,	  String dept_id, String employee_id, String start_date, String end_date,  String choose_type,String comp_id) {
		
		List<Map<String, String>> return_list = new ArrayList<Map<String, String>>();
		Map<String, String> return_map = new HashMap<String, String>();
		try {
			String sql = "" +
			" SELECT " +
			" tableA.員工工號, tableA.部門代號, " +
			" SUM(tableA.前段加班時數) AS 前段加班時數, " +
			" SUM(tableA.後段加班時數) AS 後段加班時數, " +
			" SUM(tableA.假日加班時數) AS 假日加班時數, " +
			" SUM(tableA.總加班時數) AS 總加班時數 ," +
			
			" tableA.EHF020802T0_09  AS EHF020802T0_09, " +//加班時數處理方式     20131007新增   BY 賴泓錡
			" tableA.加班費代號 AS 加班費代號,"+//加班費規則代號    20131007新增   BY 賴泓錡
			" tableA.前段費率  AS 前段費率, " +//前段費率     20131007新增   BY 賴泓錡
			" tableA.後段費率  AS 後段費率 ," +//後段費率    20131007新增   BY 賴泓錡
			" tableA.假日加班費率 AS 假日加班費率,"+//20131007新增   BY 賴泓錡
		    " tableA.是否啟用 AS 是否啟用"+//20131007新增   BY 賴泓錡
			
		    " FROM " +
			" ( " +
			" SELECT " +
			" EHF020802T0_02 AS 員工工號, " +
			" EHF020403T0_02 AS 部門代號, " +
			" EHF020802T0_04 AS 考勤日期, " +
			
			" EHF020802T0_09 AS EHF020802T0_09, " +//加班時數處理方式     20131007新增   BY 賴泓錡
			" tableB.EHF030200T0_02 AS 加班費代號," +//加班費規則代號     20131007新增   BY 賴泓錡
			" tableB.EHF030107T0_05 AS 前段費率, "+//前段費率     20131007新增   BY 賴泓錡
			" tableB.EHF030107T0_07 AS 後段費率, "+//後段費率    20131007新增   BY 賴泓錡
			" tableB.EHF030107T0_08 AS 假日加班費率, "+//假日加班費率    20131007新增   BY 賴泓錡
            " CASE tableB.EHF030107T0_09"+
            "   WHEN '1' THEN '啟用' "+
            "   WHEN '0' THEN '不啟用' "+
            " END AS 是否啟用,"+//是否啟用    20131007新增   BY 賴泓錡

			" CASE EHF010105T1_03 " +
			" WHEN false THEN " +
			"  CASE (SUM(EHF020802T0_07-EHF020802T0_07_DE) > tableB.EHF030107T0_04) " +//依據加班費規則的前N小時加班  20131007新增   BY 賴泓錡
			"  WHEN true THEN tableB.EHF030107T0_04 " +
			"  ELSE SUM(EHF020802T0_07-EHF020802T0_07_DE) " +
			"  END " +
			" END AS 前段加班時數, " +
			" CASE EHF010105T1_03 " +
			" WHEN false THEN " +
			"  CASE (SUM(EHF020802T0_07-EHF020802T0_07_DE) > tableB.EHF030107T0_06) " +		//依據加班費規則的後N小時加班  20131007新增   BY 賴泓錡
			"  WHEN true THEN (SUM(EHF020802T0_07-EHF020802T0_07_DE) - tableB.EHF030107T0_06) " +
			"  ELSE 0 " +
			"  END " +
			" END AS 後段加班時數, " +
			" CASE EHF010105T1_03 " +
			" WHEN true THEN (SUM(EHF020802T0_07-EHF020802T0_07_DE)) " +
			" ELSE 0 " +
			" END AS 假日加班時數, " +
			" SUM(EHF020802T0_07-EHF020802T0_07_DE) AS 總加班時數 " +
			" FROM EHF020802T0 " +
			" LEFT JOIN EHF010105T0 ON EHF010105T0_02 = EHF020802T0_02 " +
			" AND EHF010105T0_06 = EHF020802T0_08 " +
			" AND EHF010105T0_03 = DATE_FORMAT(EHF020802T0_04, '%Y') " +
			" AND EHF010105T0_04 = DATE_FORMAT(EHF020802T0_04, '%m') " +
			" LEFT JOIN EHF010105T1 ON EHF010105T1_01 = EHF010105T0_01 " +
			" AND DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') = EHF020802T0_04 " +
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020802T0_02 " +
			" AND EHF020403T0_04 = EHF020802T0_08 " +
			
			//新增判斷   可以依照加班費規則設定的   前加班時段  與後加班時段  20131007新增   BY 賴泓錡
			" LEFT JOIN (SELECT " +
			" EHF030200T0_01, " +
			" EHF030200T0_02, " +
			" EHF030107T0_04, " +
			" EHF030107T0_05, " +
			" EHF030107T0_06, " +
			" EHF030107T0_07, " +
			" EHF030107T0_08, " +
			" EHF030107T0_09, " +
			" EHF030200T0_13" +
			" FROM" +
			" EHF030200T0 LEFT JOIN EHF030107T0 ON EHF030107T0_02 = EHF030200T0_18" +
			" WHERE 1 = 1 ) tableB ON tableB.EHF030200T0_01 = EHF020802T0_02 AND  tableB.EHF030200T0_13=EHF020802T0_08" +
			

			" WHERE 1=1 " +
			" AND EHF020802T0_08 = '"+comp_id+"' ";  //公司代碼
			
			if (!"".equals(employee_id) && employee_id != null 
				&& !"".equals(dept_id) && dept_id != null ){  //員工
				sql += " AND EHF020403T0_02 = '"+dept_id+"' " +  //部門代號
					   " AND EHF020802T0_02 = '"+employee_id+"' ";  //員工工號
			}else if(!"".equals(dept_id) && dept_id != null){  //部門組織
				sql += " AND EHF020403T0_02 = '"+dept_id+"' ";  //部門代號
			}
			
			//日期區間  -- 使用實際日期做判斷
			if(!"".equals(start_date) && start_date != null && !"".equals(end_date) && end_date !=null ){
				sql += " AND " +
					   " ( " +
					   " EHF020802T0_04 BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //考勤日期
					   " ) " ;
			}else if(!"".equals(start_date) && start_date != null){
				sql += " AND " +
					   " ( " +
					   " EHF020802T0_04 >= '"+start_date+"' " +  //考勤日期
					   " ) ";
			}else if(!"".equals(end_date) && end_date != null){
				sql += " AND " +
					   " ( " +
					   " EHF020802T0_04 <= '"+end_date+"' " +  //考勤日期
					   " ) ";
			}
			
			//加班時數處理方式     20131007新增   BY 賴泓錡
			if(!"".equals(choose_type)){
				sql +="AND EHF020802T0_09 ='"+choose_type+"' ";	
			}
			
			
			
			sql += "" +
			" GROUP BY EHF020802T0_04, EHF020802T0_02 " +
			" ) tableA " +
			" WHERE 1=1 " +
			" GROUP BY tableA.員工工號,tableA.EHF020802T0_09 " +//
			"";
			/*
			// 調整Connection來源
			Connection local_conn = null;
			if (this.outside_conn != null && !this.outside_conn.isClosed()) {
				// 使用外部Connection
				local_conn = this.outside_conn;
			} else {
				local_conn = this.base_tools.getConn();
			}
			*/

			// 執行SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()) {
				
				// 將資料放入return_map中
				return_map = new HashMap<String, String>();
				return_map.put("員工工號", rs.getString("員工工號"));
				return_map.put("部門代號", rs.getString("部門代號"));
				return_map.put("前段加班時數", rs.getString("前段加班時數"));//    20131007修改   BY 賴泓錡
				return_map.put("後段加班時數", rs.getString("後段加班時數"));//    20131007修改   BY 賴泓錡
				return_map.put("假日加班時數", rs.getString("假日加班時數"));
				return_map.put("總加班時數", rs.getString("總加班時數"));
				return_map.put("EHF020802T0_09", rs.getString("EHF020802T0_09"));//加班時數處理方式     20131007新增   BY 賴泓錡
				return_map.put("前段費率", rs.getString("前段費率"));//前段費率     20131007新增   BY 賴泓錡
				return_map.put("後段費率", rs.getString("後段費率"));//後段費率     20131007新增   BY 賴泓錡
				
				return_map.put("假日加班費率", rs.getString("假日加班費率"));//後段費率     20131007新增   BY 賴泓錡
				return_map.put("是否啟用", rs.getString("是否啟用"));//後段費率     20131007新增   BY 賴泓錡
				
				
				//put return map to return list
				return_list.add(return_map);
			}

			rs.close();
			stmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_list;
		}	
}
