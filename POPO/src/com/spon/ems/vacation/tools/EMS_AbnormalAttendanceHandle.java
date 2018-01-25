package com.spon.ems.vacation.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.hr.forms.EHF010100M0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.utils.util.BA_TOOLS;

/**
 * EMS 出勤記錄處理元件
 * 
 * @version 1.0
 */
public class EMS_AbnormalAttendanceHandle implements BaseSystem {
	
	
	private BaseFunction base_tools;
	//private Connection outside_conn = null;
	
	public EMS_AbnormalAttendanceHandle(){
		
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
	 * 取得遲到、早退、曠職設定檔
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> getAbnormalAttendanceSetting(Connection conn, Map<String, String> paramMap){
		
		Map<String, String> return_map = new HashMap<String, String>();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF020409T0_01 AS RULE_NO, " +
			" CASE WHEN EHF020409T0_05 > 0 THEN 'true' ELSE 'false' END AS DELAY_FLEX_FLAG, " +
			" EHF020409T0_05 AS DELEY_FLEX_MINUTE, " +
			" CASE WHEN EHF020409T0_11 > 0 THEN 'true' ELSE 'false' END AS LEAVE_EARLY_FLEX_FLAG, " +
			" EHF020409T0_11 AS LEAVE_EARLY_FLEX_MINUTE, " +
			" CASE WHEN EHF020409T0_17 > 0 THEN 'true' ELSE 'false' END AS ABSENTEEISM_COND_FLAG, " +
			" EHF020409T0_17 AS ABSENTEEISM_COND_HOUR, " +
			" CASE WHEN EHF020409T0_24 = true THEN 'true' ELSE 'false' END AS RECORD_FULL_OVERTIME_ATT_DATA, " +
			" CASE WHEN EHF020409T0_25 = true THEN 'true' ELSE 'false' END AS RECORD_COME_LATE_TIME, " +
			" DATE_FORMAT(EHF020409T0.DATE_UPDATE, '%Y/%m/%d %H:%i:%s') AS DATE_UPDATE " +
			" FROM EHF020409T0 " +
			" WHERE 1=1 " +
			" AND EHF020409T0_23 = '"+paramMap.get("COMP_ID")+"' " +  //公司代碼
			" LIMIT 1 " ;
			
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
				return_map.put("RULE_NO", rs.getString("RULE_NO"));
				return_map.put("DELAY_FLEX_FLAG", rs.getString("DELAY_FLEX_FLAG"));
				return_map.put("DELEY_FLEX_MINUTE", rs.getString("DELEY_FLEX_MINUTE"));
				return_map.put("LEAVE_EARLY_FLEX_FLAG", rs.getString("LEAVE_EARLY_FLEX_FLAG"));
				return_map.put("LEAVE_EARLY_FLEX_MINUTE", rs.getString("LEAVE_EARLY_FLEX_MINUTE"));
				return_map.put("ABSENTEEISM_COND_FLAG", rs.getString("ABSENTEEISM_COND_FLAG"));
				return_map.put("ABSENTEEISM_COND_HOUR", rs.getString("ABSENTEEISM_COND_HOUR"));
				return_map.put("RECORD_FULL_OVERTIME_ATT_DATA", rs.getString("RECORD_FULL_OVERTIME_ATT_DATA"));  //是否記錄下班與加班上班刷卡
				return_map.put("RECORD_COME_LATE_TIME", rs.getString("RECORD_COME_LATE_TIME"));  //是否補足遲到時數
				return_map.put("ABSENTEEISM_COND_HOUR_IN_SEC", ((int) ((Float.parseFloat(return_map.get("ABSENTEEISM_COND_HOUR")))*60*60))+"");
				
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
	 * 產生出勤記錄
	 * @param conn
	 * @param employee_id
	 * @param class_no
	 * @param att_date
	 * @param att_type
	 * @param abnormal_sec
	 * @param settingMap
	 * @param comp_id
	 * @param error_no
	 * @param EHF020410T0_09   	紀錄刷卡的日期
	 * @param EHF020410T0_10	紀錄刷卡的時間	
	 * @return
	 */
	public Map<String, String> putAttendanceRecord(
							   Connection conn,
							   String employee_id, String class_no,
							   String att_date, String att_type, int abnormal_sec, 
			 				   Map<String, String> settingMap, String comp_id,int error_no
			 				   ,String EHF020410T0_09
			 				   ){
		
		Map<String, String> return_map = new HashMap<String, String>();
		String error_status = "";
		
		try{
			//建立paramMap
			Map<String, String> paramMap = new HashMap<String, String>();
			
			//取得曠職條件時數
			int absenteeism_sec = 0;
			if(settingMap.containsKey("ABSENTEEISM_COND_HOUR")){
				absenteeism_sec = (int) ((Float.parseFloat(settingMap.get("ABSENTEEISM_COND_HOUR")))*60*60);
			}else{
				absenteeism_sec = 0;
			}
			//00'-=正常, '01'=遲到, '02'=早退, '03'=曠職, '04'=未打卡
			//判斷異常秒數是否已超過曠職限制
			if(abnormal_sec > absenteeism_sec){
				//戴養菌特殊規則
				/*if(error_no==2||error_no==3){
					//員工曠職
					paramMap.put("EHF020410T0_01", employee_id);  //員工工號
					paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO"));  //規則序號
					paramMap.put("EHF020410T0_03", att_date);  //考勤日期
					paramMap.put("EHF020410T0_04", att_type);  //刷卡類型
					paramMap.put("EHF020410T0_05", "true");  //是否異常
					paramMap.put("EHF020410T0_06", "03");  //出勤異常類型 - 曠職
					paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec));  //出勤異常秒數
					paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
					paramMap.put("EHF020410T0_08", comp_id);  //公司代碼
					paramMap.put("COMP_ID", comp_id);  //公司代碼
					paramMap.put("EHF020410T0_09", EHF020410T0_09);  //紀錄刷卡的日期時間
					error_status = "曠職";
					
				}else if(error_no==4){
					//員工曠職
					paramMap.put("EHF020410T0_01", employee_id);  //員工工號
					paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO"));  //規則序號
					paramMap.put("EHF020410T0_03", att_date);  //考勤日期
					paramMap.put("EHF020410T0_04", att_type);  //刷卡類型
					paramMap.put("EHF020410T0_05", "true");  //是否異常
					paramMap.put("EHF020410T0_06", "04");  //出勤異常類型 - 未打卡
					paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec));  //出勤異常秒數
					paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
					paramMap.put("EHF020410T0_08", comp_id);  //公司代碼
					paramMap.put("COMP_ID", comp_id);  //公司代碼
					paramMap.put("EHF020410T0_09", EHF020410T0_09);  //紀錄刷卡的日期時間
					error_status = "未打卡";
					
				}*/
				//員工曠職
				paramMap.put("EHF020410T0_01", employee_id);  //員工工號
				paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO"));  //規則序號
				paramMap.put("EHF020410T0_03", att_date);  //考勤日期
				paramMap.put("EHF020410T0_04", att_type);  //刷卡類型
				paramMap.put("EHF020410T0_05", "true");  //是否異常
				paramMap.put("EHF020410T0_06", "03");  //出勤異常類型 - 曠職
				paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec));  //出勤異常秒數
				paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
				paramMap.put("EHF020410T0_08", comp_id);  //公司代碼
				paramMap.put("COMP_ID", comp_id);  //公司代碼
				paramMap.put("EHF020410T0_09", EHF020410T0_09);  //紀錄刷卡的日期時間
				error_status = "曠職";
				
			}else{
				//員工出勤異常(遲到 or 早退)
				if("1".equals(att_type) || "8".equals(att_type)){
					//刷卡類型 = 上班 --> 處理遲到邏輯
					
					//取得遲到條件分鐘
					int delay_sec = 0;
					if(settingMap.containsKey("DELEY_FLEX_MINUTE")){
						delay_sec = ((Integer.parseInt(settingMap.get("DELEY_FLEX_MINUTE")))*60);
					}else{
						delay_sec = 0;
					}
					
					if(abnormal_sec <= delay_sec){
						//表示員工刷卡時間在遲到的允許範圍內
						//員工出勤正常
						paramMap.put("EHF020410T0_01", employee_id);  //員工工號
						paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO"));  //規則序號
						paramMap.put("EHF020410T0_03", att_date);  //考勤日期
						paramMap.put("EHF020410T0_04", att_type);  //刷卡類型
						paramMap.put("EHF020410T0_05", "false");  //是否異常
						paramMap.put("EHF020410T0_06", "00");  //出勤異常類型 - 正常
						paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec));  //出勤異常秒數
						paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
						paramMap.put("EHF020410T0_08", comp_id);  //公司代碼
						paramMap.put("COMP_ID", comp_id);  //公司代碼
						paramMap.put("EHF020410T0_09", EHF020410T0_09);  //紀錄刷卡的日期時間
						error_status = "正常";

					}else{
						//表示員工刷卡時間超過遲到允許的範圍
						//員工出勤遲到
						paramMap.put("EHF020410T0_01", employee_id);  //員工工號
						paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO"));  //規則序號
						paramMap.put("EHF020410T0_03", att_date);  //考勤日期
						paramMap.put("EHF020410T0_04", att_type);  //刷卡類型
						paramMap.put("EHF020410T0_05", "true");  //是否異常
						paramMap.put("EHF020410T0_06", "01");  //出勤異常類型 - 遲到
						paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec));  //出勤異常秒數
						paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
						paramMap.put("EHF020410T0_08", comp_id);  //公司代碼
						paramMap.put("COMP_ID", comp_id);  //公司代碼
						paramMap.put("EHF020410T0_09", EHF020410T0_09);  //紀錄刷卡的日期時間
						error_status = "遲到";
						
					}
					
				}else if("2".equals(att_type) || "7".equals(att_type)){
					//刷卡類型 = 下班 --> 處理早退邏輯
					
					//取得早退條件分鐘
					int leave_early_sec = 0;
					if(settingMap.containsKey("LEAVE_EARLY_FLEX_MINUTE")){
						leave_early_sec = ((Integer.parseInt(settingMap.get("LEAVE_EARLY_FLEX_MINUTE")))*60);
					}else{
						leave_early_sec = 0;
					}
					
					if(abnormal_sec <= leave_early_sec){
						//表示員工刷卡時間在早退的允許範圍內
						//員工出勤正常
						paramMap.put("EHF020410T0_01", employee_id);  //員工工號
						paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO"));  //規則序號
						paramMap.put("EHF020410T0_03", att_date);  //考勤日期
						paramMap.put("EHF020410T0_04", att_type);  //刷卡類型
						paramMap.put("EHF020410T0_05", "false");  //是否異常
						paramMap.put("EHF020410T0_06", "00");  //出勤異常類型 - 正常
						paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec));  //出勤異常秒數
						paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
						paramMap.put("EHF020410T0_08", comp_id);  //公司代碼
						paramMap.put("COMP_ID", comp_id);  //公司代碼
						paramMap.put("EHF020410T0_09", EHF020410T0_09);  //紀錄刷卡的日期時間
						error_status = "正常";
						
					}else{
						//表示員工刷卡時間超過早退允許的範圍
						//員工出勤遲到
						paramMap.put("EHF020410T0_01", employee_id);  //員工工號
						paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO"));  //規則序號
						paramMap.put("EHF020410T0_03", att_date);  //考勤日期
						paramMap.put("EHF020410T0_04", att_type);  //刷卡類型
						paramMap.put("EHF020410T0_05", "true");  //是否異常
						paramMap.put("EHF020410T0_06", "02");  //出勤異常類型 - 早退
						paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec));  //出勤異常秒數
						paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
						paramMap.put("EHF020410T0_08", comp_id);  //公司代碼
						paramMap.put("COMP_ID", comp_id);  //公司代碼
						paramMap.put("EHF020410T0_09", EHF020410T0_09);  //紀錄刷卡的日期時間
						error_status = "早退";

					}
					
				}
				
			}
			
			try{
				//建立出勤記錄
				return_map = this.insertAttendanceRecord(conn, paramMap);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("建立 "+employee_id+" 的"+this.getAttStatus(Integer.parseInt(att_type))+error_status+"出勤記錄時，發生錯誤!!" + e.toString());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 產生加班出勤記錄
	 * @param employee_id
	 * @param att_date
	 * @param att_type
	 * @param abnormal_sec
	 * @param settingMap
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> putOvertimeAttendanceRecord(
							   Connection conn,
							   String employee_id, String class_no,
							   String att_date, String att_type, int abnormal_sec,
							   Map<String, String> settingMap, 
							   String comp_id) {

		Map<String, String> return_map = new HashMap<String, String>();

		try {
			// 建立paramMap
			Map<String, String> paramMap = new HashMap<String, String>();
			
			//專門處理加班的出勤記錄
			// 判斷異常秒數是否是加班未打卡的狀態
			if (abnormal_sec  == -1 ) {
				// 員工加班未打卡
				paramMap.put("EHF020410T0_01", employee_id); // 員工工號
				paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO")); // 規則序號
				paramMap.put("EHF020410T0_03", att_date); // 考勤日期
				paramMap.put("EHF020410T0_04", att_type); // 刷卡類型
				paramMap.put("EHF020410T0_05", "true"); // 是否異常
				paramMap.put("EHF020410T0_06", "04"); // 出勤異常類型 - 未打卡
				paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec)); // 出勤異常秒數
				paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
				paramMap.put("EHF020410T0_08", comp_id); // 公司代碼
				paramMap.put("COMP_ID", comp_id); // 公司代碼

			} else {
				// 員工加班有打卡
				// 表示員工刷卡時間在加班的允許範圍內
				// 員工出勤正常
				paramMap.put("EHF020410T0_01", employee_id); // 員工工號
				paramMap.put("EHF020410T0_02", settingMap.get("RULE_NO")); // 規則序號
				paramMap.put("EHF020410T0_03", att_date); // 考勤日期
				paramMap.put("EHF020410T0_04", att_type); // 刷卡類型
				paramMap.put("EHF020410T0_05", "false"); // 是否異常
				paramMap.put("EHF020410T0_06", "00"); // 出勤異常類型 - 正常
				paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec)); // 出勤異常秒數
				paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
				paramMap.put("EHF020410T0_08", comp_id); // 公司代碼
				paramMap.put("COMP_ID", comp_id); // 公司代碼

			}

			// 建立加班出勤記錄
			return_map = this.insertAttendanceRecord(conn, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	/**
	 * 修正出勤記錄
	 * @param employee_id
	 * @param att_date
	 * @param att_type
	 * @param fix_ds_type
	 * @param fix_id
	 * @param fix_ps
	 * @param user_name
	 * @param user_id
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> putFixAttendanceRecord(
							   Connection conn,
							   String employee_id,
							   String att_date, String att_type,
							   String fix_ds_type, String fix_id, String fix_ps,
							   String user_name, String user_id, String comp_id) {

		Map<String, String> return_map = new HashMap<String, String>();

		try {
			// 建立paramMap
			Map<String, String> paramMap = new HashMap<String, String>();
			
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

			//處理出勤資料修正
			//查詢是否為加班出勤(加班出勤資料要額外考慮重新處理加班時數結算的功能)
			if( !"5".equals(att_type) && !"6".equals(att_type) ){
				//不是加班出勤
				
				//建立出勤修正資料
				paramMap.put("EHF020410T0_FIX", "true"); //是否已修正
				paramMap.put("EHF020410T0_FDS", fix_ds_type); //修正來源
				paramMap.put("EHF020410T0_FID", fix_id); //修正的表單UID或是人員EMPLOYEE_ID
				paramMap.put("EHF020410T0_PS", fix_ps); //修正備註
				paramMap.put("USER_NAME", user_name); //資料修改者
				
				paramMap.put("EHF020410T0_01", employee_id); // 員工工號
				paramMap.put("EHF020410T0_03", att_date); // 考勤日期
				paramMap.put("EHF020410T0_04", att_type); // 刷卡類型
				paramMap.put("EHF020410T0_08", comp_id); // 公司代碼
				paramMap.put("COMP_ID", comp_id); // 公司代碼
				
				//建立加班出勤記錄
				return_map = this.fixAttendanceRecord(conn, paramMap);
				
			}else{
				//是加班出勤
				
				//建立出勤修正資料
				paramMap.put("EHF020410T0_FIX", "true"); //是否已修正
				paramMap.put("EHF020410T0_FDS", fix_ds_type); //修正來源
				paramMap.put("EHF020410T0_FID", fix_id); //修正的表單UID或是人員EMPLOYEE_ID
				paramMap.put("EHF020410T0_PS", fix_ps); //修正備註
				paramMap.put("USER_NAME", user_name); //資料修改者
				
				paramMap.put("EHF020410T0_01", employee_id); // 員工工號
				paramMap.put("EHF020410T0_03", att_date); // 考勤日期
				paramMap.put("EHF020410T0_04", att_type); // 刷卡類型
				paramMap.put("EHF020410T0_08", comp_id); // 公司代碼
				paramMap.put("COMP_ID", comp_id); // 公司代碼
				
				//建立加班出勤記錄
				return_map = this.fixAttendanceRecord(conn, paramMap);
				
				//加班考勤結算
				//使用考勤元件重新產生員工當日考勤資料
				EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(comp_id, att_date, user_id );
				
				//重新結算加班出勤資料
				att.reDoGenerateOvertimeAttendaceRecord(conn, employee_id);
				
			}		

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	/**
	 * 回復修正出勤記錄
	 * @param employee_id
	 * @param att_date
	 * @param att_type
	 * @param user_name
	 * @param user_id
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> recoveryFixAttendanceRecord(
							   Connection conn,
							   String employee_id,
							   String att_date, String att_type, 
							   String user_name, String user_id,
							   String comp_id) {

		Map<String, String> return_map = new HashMap<String, String>();

		try {
			// 建立paramMap
			Map<String, String> paramMap = new HashMap<String, String>();
			
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

			// 處理出勤資料修正
			// 查詢是否為加班出勤(加班出勤資料要額外考慮重新處理加班時數結算的功能)
			if (!"5".equals(att_type) && !"6".equals(att_type)) {
				// 不是加班出勤

				// 建立出勤修正資料
				paramMap.put("EHF020410T0_FIX", "false"); // 是否已修正
				paramMap.put("EHF020410T0_FDS", ""); // 修正來源
				paramMap.put("EHF020410T0_FID", ""); // 修正的表單UID或是人員EMPLOYEE_ID
				paramMap.put("EHF020410T0_PS", ""); // 修正備註
				paramMap.put("USER_NAME", user_name); // 資料修改者

				paramMap.put("EHF020410T0_01", employee_id); // 員工工號
				paramMap.put("EHF020410T0_03", att_date); // 考勤日期
				paramMap.put("EHF020410T0_04", att_type); // 刷卡類型
				paramMap.put("EHF020410T0_08", comp_id); // 公司代碼
				paramMap.put("COMP_ID", comp_id); // 公司代碼

				// 建立加班出勤記錄
				return_map = this.fixAttendanceRecord(conn, paramMap);

			} else {
				// 是加班出勤

				// 建立出勤修正資料
				paramMap.put("EHF020410T0_FIX", "false"); // 是否已修正
				paramMap.put("EHF020410T0_FDS", ""); // 修正來源
				paramMap.put("EHF020410T0_FID", ""); // 修正的表單UID或是人員EMPLOYEE_ID
				paramMap.put("EHF020410T0_PS", ""); // 修正備註
				paramMap.put("USER_NAME", user_name); // 資料修改者

				paramMap.put("EHF020410T0_01", employee_id); // 員工工號
				paramMap.put("EHF020410T0_03", att_date); // 考勤日期
				paramMap.put("EHF020410T0_04", att_type); // 刷卡類型
				paramMap.put("EHF020410T0_08", comp_id); // 公司代碼
				paramMap.put("COMP_ID", comp_id); // 公司代碼

				// 建立加班出勤記錄
				return_map = this.fixAttendanceRecord(conn, paramMap);

				// 加班考勤結算
				// 使用考勤元件重新產生員工當日考勤資料
				EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(comp_id, att_date, user_id);

				// 重新結算加班出勤資料
				att.reDoGenerateOvertimeAttendaceRecord(conn, employee_id);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	/**
	 * 修正出勤資料
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> fixAttendanceRecord(Connection conn, Map<String, String> paramMap){
		
		Map<String, String> return_map = new HashMap<String, String>();
		
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF020410T0 SET " +
			" " +
			" EHF020410T0_FIX=?, EHF020410T0_FDS=?, EHF020410T0_FID=?, " +
			" EHF020410T0_PS=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF020410T0_01 = ? " +
			" AND EHF020410T0_03 = ? " +
			" AND EHF020410T0_04 = ? " +
			" AND EHF020410T0_08 = ? ";
			
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
			p6stmt.setBoolean(indexid, Boolean.parseBoolean(paramMap.get("EHF020410T0_FIX")));  //是否已修正
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_FDS"));  //修正來源
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_FID"));  //修正的表單UID或是人員EMPLOYEE_ID
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_PS"));  //修正備註
			indexid++;
			p6stmt.setString(indexid, paramMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_01"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_03"));  //考勤日期
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_04"));  //刷卡類型
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_08"));  //公司代碼
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
			
			/*
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EMS_AbnormalAttendanceHandle().fixAttendanceRecord()", show_sql, 
										paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
			
		}catch(Exception e){
			
			/*
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EMS_AbnormalAttendanceHandle().fixAttendanceRecord()", show_sql+", "+e.getMessage(), 
										   paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										   paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
			
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 新增出勤記錄
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> insertAttendanceRecord(Connection conn, Map<String, String> paramMap){
		
		Map<String, String> return_map = new HashMap<String, String>();
		
		String sql = "";
		String show_sql = "";
		String user_create = "SYSTEM";
		
		try{
			//Add
			sql = "" +
			" INSERT INTO EHF020410T0 " +
			" ( " +
			" EHF020410T0_01, EHF020410T0_02, " +
			" EHF020410T0_03, EHF020410T0_04, EHF020410T0_05, EHF020410T0_06, " +
			" EHF020410T0_07, " +
			" EHF020410T0_08, " +
			" EHF020410T0_FIX, EHF020410T0_FDS, EHF020410T0_FID, EHF020410T0_PS, " +
			" EHF020410T0_CLASS, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ,EHF020410T0_09" +
			" ) " +
			" VALUES ( " +
			" ?, ?, " +
			" ?, ?, ?, ?, " +
			" ?, " +
			" ?, " +
			" ?, ?, ?, ?, " +
			" ?, " +
			" ?, ?, 1, NOW(), NOW() " +
			" ,?" +
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
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,	pstmt, null, sql);
			int indexid = 1;
			//20131028 新增 BY賴泓錡
			//取得薪資主檔
			EHF010100M0F ehf010100m0 = this.getEmpSalFile(conn, paramMap.get("EHF020410T0_01"),paramMap.get("COMP_ID"));
			
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_01"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_02"));  //規則序號(遲到早退曠職序號)
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_03"));  //考勤日期
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_04"));  //刷卡類型
			indexid++;
			p6stmt.setBoolean(indexid, Boolean.parseBoolean(paramMap.get("EHF020410T0_05")));  //是否異常
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_06"));  //出勤異常類型
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_07"));  //出勤異常秒數(遲到早退曠職)
			indexid++;
			p6stmt.setString(indexid, paramMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			//20131028 新增 BY賴泓錡
			//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
			if ("0".equals(ehf010100m0.getEHF020403T0_05())) {
				p6stmt.setBoolean(indexid, true);  //是否已修正 -> initial = false
				indexid++;
				p6stmt.setString(indexid, "不計算考勤人員");  //修正來源
				indexid++;
			}else{
				p6stmt.setBoolean(indexid, false);  //是否已修正 -> initial = false
				indexid++;
				p6stmt.setString(indexid, "");  //修正來源
				indexid++;
			}
			p6stmt.setString(indexid, "");  //修正的表單UID或人員EMP_ID
			indexid++;
			p6stmt.setString(indexid, "");  //修正備註
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020410T0_CLASS"));  //班別代號
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
			
			p6stmt.setString(indexid, "".equals(paramMap.get("EHF020410T0_09"))?null:paramMap.get("EHF020410T0_09"));  //紀錄刷卡的日期
			indexid++;

			//System.out.println("建立出勤紀錄："+p6stmt.getQueryFromPreparedStatement());
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
			this.base_tools.writeADDMSG("EMS_AbnormalAttendanceHandle().insertAttendanceRecord()", show_sql, 
										paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
		}catch(Exception e){
			
			/*
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EMS_AbnormalAttendanceHandle().insertAttendanceRecord()", show_sql+", "+e.getMessage(), 
										   paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										   paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
			
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 移除出勤記錄
	 * @param employee_id
	 * @param att_date
	 * @param att_type
	 * @param comp_id
	 * @return
	 */
	public Map<String, String> removeAttendanceRecord(
							   Connection conn,
							   String employee_id,
							   String att_date, String att_type,
							   String comp_id) {

		Map<String, String> return_map = new HashMap<String, String>();

		try {
			// 建立paramMap
			Map<String, String> paramMap = new HashMap<String, String>();

			//移除員工出勤記錄
			if(!"".equals(employee_id) && employee_id != null){
				paramMap.put("EHF020410T0_01", employee_id); // 員工工號
			}
			
			paramMap.put("EHF020410T0_03", att_date); // 考勤日期
			
			if(!"".equals(att_type) && att_type != null){
				paramMap.put("EHF020410T0_04", att_type); // 刷卡類型
			}
			
			paramMap.put("EHF020410T0_08", comp_id); // 公司代碼
			paramMap.put("COMP_ID", comp_id); // 公司代碼

			//移除加班出勤記錄
			this.deleteAttendanceRecord(conn, paramMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	/**
	 * 刪除出勤記錄
	 * @param paramMap
	 */
	public void deleteAttendanceRecord(Connection conn, Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			List<String> sql_list = new ArrayList<String>();

			//DELETE EHF020410T0
			sql = "" +
			" DELETE FROM EHF020410T0 " +
			" WHERE 1=1 " +
			" AND EHF020410T0_03 = '"+paramMap.get("EHF020410T0_03")+"' ";  //考勤日期
			
			
			if(paramMap.containsKey("EHF020410T0_01")){
				sql += "" +
				" AND EHF020410T0_01 = '"+paramMap.get("EHF020410T0_01")+"' ";  //員工工號
			}
			
			
			if(paramMap.containsKey("EHF020410T0_04")){
				sql += "" +
				" AND EHF020410T0_04 = '"+paramMap.get("EHF020410T0_04")+"' ";  //刷卡類型
			}
			
			sql += "" +
			" AND EHF020410T0_08 = '"+paramMap.get("COMP_ID")+"' ";  //公司代碼
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
			this.base_tools.writeDELETEMSG("EMS_AbnormalAttendanceHandle().deleteAttendanceRecord()", sql+" total delete "+dataCount+"rows data", 
										   paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										   paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			*/
			
		}catch(Exception e){
			
			/*
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EMS_AbnormalAttendanceHandle().deleteAttendanceRecord()", sql+", "+e.getMessage(), 
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
	 * 取得員工薪資主檔資料
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public EHF010100M0F getEmpSalFile( Connection conn, String employee_id, String comp_id ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF010100M0F ehf010100m0 = null;
		
		try{
			
			ehf010100m0 = new EHF010100M0F();
			
			String sql = "" +			
			" SELECT * " +
			" FROM EHF020403T0 " +
			" WHERE 1=1 " +
			" AND EHF020403T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020403T0_04 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				/*
				ehf030200m0.setEHF030200T0_01(rs.getString("EHF030200T0_01"));  //員工工號
				ehf030200m0.setEHF030200T0_02(rs.getString("EHF030200T0_02"));  //部門代號
				ehf030200m0.setEHF030200T0_03(rs.getString("EHF030200T0_03"));  //員工類別
				ehf030200m0.setEHF030200T0_04(rs.getInt("EHF030200T0_04"));  //基本薪資(本俸)
				ehf030200m0.setEHF030200T0_05(rs.getString("EHF030200T0_05"));  //薪資發放方式
				ehf030200m0.setEHF030200T0_06(rs.getString("EHF030200T0_06"));  //銀行代號
				ehf030200m0.setEHF030200T0_06_AC(rs.getString("EHF030200T0_06_AC"));  //匯款帳號
				ehf030200m0.setEHF030200T0_07(rs.getString("EHF030200T0_07"));  //薪資計算方式
				ehf030200m0.setEHF030200T0_08(rs.getString("EHF030200T0_08"));  //是否啟用
				ehf030200m0.setEHF030200T0_09(rs.getInt("EHF030200T0_09"));  //應發薪資
				ehf030200m0.setEHF030200T0_10(rs.getString("EHF030200T0_10"));  //各類所得代號
				ehf030200m0.setEHF030200T0_11(rs.getInt("EHF030200T0_11"));  //各類所得
				ehf030200m0.setEHF030200T0_12(rs.getString("EHF030200T0_12"));  //備註
				ehf030200m0.setEHF030200T0_13(rs.getString("EHF030200T0_13"));  //公司代碼
				ehf030200m0.setEHF030200T0_14(tools.BooleanToString(rs.getBoolean("EHF030200T0_14"), "1"));  //是否代扣所得稅
				ehf030200m0.setEHF030200T0_14_TYPE(rs.getString("EHF030200T0_14_TYPE"));  //代扣類型
				ehf030200m0.setEHF030200T0_15(rs.getFloat("EHF030200T0_15"));  //代扣率
				ehf030200m0.setEHF030200T0_16(rs.getInt("EHF030200T0_16"));  //代扣金額
				*/
				//EMS產品新增參數 20130909 by Hedwig
				//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
				ehf010100m0.setEHF020403T0_01(rs.getString("EHF020403T0_01"));  //員工工號
				ehf010100m0.setEHF020403T0_02(rs.getString("EHF020403T0_02"));  //部門代號	
				ehf010100m0.setEHF030200T0_17(rs.getString("EHF020403T0_05"));  //是否計算考勤
				/*
				ehf030200m0.setEHF030200T0_18(rs.getString("EHF030200T0_18"));  //員工加班費規則
				ehf030200m0.setEHF030200T0_19(rs.getInt("EHF030200T0_19"));  //全勤獎金金額
				ehf030200m0.setEHF030200T0_20(rs.getString("EHF030200T0_20"));  //全勤獎金扣費規則
				ehf030200m0.setEHF030200T0_21(rs.getString("EHF030200T0_21"));  //是否代扣福利金
				*/
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			try {
				throw new Exception("取得員工薪資主檔時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return ehf010100m0;
	}
	
	/**
	 * 取得出勤記錄刷卡類型中文名稱
	 * @param status_no
	 * @return
	 */
	public String getAttStatus(int status_no){
		
		String status_name = "";
		try{
			switch(status_no){
				
			case 1:
				status_name = "上班";
				break;
			case 2:
				status_name = "下班";
				break;
			case 3:
				status_name = "外出";
				break;
			case 4:
				status_name = "返回";
				break;
			case 5:
				status_name = "加班上班";
				break;
			case 6:
				status_name = "加班下班";
				break;
			case 7:
				status_name = "上午下班";
				break;
			case 8:
				status_name = "下午上班";
				break;
			default:
				status_name = "未知";
			}	
			
		}catch(Exception e){
			e.printStackTrace();
		}
				
		return status_name;
	}
	
	/**
	 * 產生時薪出勤記錄
	 * @param conn
	 * @param employee_id
	 * @param class_no
	 * @param att_date
	 * @param att_type
	 * @param abnormal_sec
	 * @param settingMap
	 * @param comp_id
	 * @param error_no
	 * @param EHF020410T0_09   	紀錄刷卡的日期
	 * @param EHF020410T0_10	紀錄刷卡的時間	
	 * @return
	 */
	public Map<String, String> putAttendanceRecordByHour(
							   Connection conn,
							   String employee_id, String class_no,
							   String att_date, String att_type, int abnormal_sec, 
			 				   Map<String, String> settingMap, String comp_id,int error_no
			 				   ,String EHF020410T0_09
			 				   ){
		
		Map<String, String> return_map = new HashMap<String, String>();
		String error_status = "";
		
		try{
			//建立paramMap
			Map<String, String> paramMap = new HashMap<String, String>();
						
			//00'-=正常, '01'=遲到, '02'=早退, '03'=曠職, '04'=未打卡
			//員工出勤正常
			paramMap.put("EHF020410T0_01", employee_id);  //員工工號
			paramMap.put("EHF020410T0_02", "0");  //規則序號
			paramMap.put("EHF020410T0_03", att_date);  //考勤日期
			paramMap.put("EHF020410T0_04", att_type);  //刷卡類型
			paramMap.put("EHF020410T0_05", "false");  //是否異常
			paramMap.put("EHF020410T0_06", "00");  //出勤異常類型 - 正常
			paramMap.put("EHF020410T0_07", String.valueOf(abnormal_sec));  //出勤異常秒數
			paramMap.put("EHF020410T0_CLASS", class_no);  //班別代號
			paramMap.put("EHF020410T0_08", comp_id);  //公司代碼
			paramMap.put("COMP_ID", comp_id);  //公司代碼
			paramMap.put("EHF020410T0_09", EHF020410T0_09);  //紀錄刷卡的日期時間
			error_status = "正常";
			
			try{
				//建立出勤記錄
				return_map = this.insertAttendanceRecord(conn, paramMap);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("建立 "+employee_id+" 的"+this.getAttStatus(Integer.parseInt(att_type))+error_status+"出勤記錄時，發生錯誤!!" + e.toString());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
}
