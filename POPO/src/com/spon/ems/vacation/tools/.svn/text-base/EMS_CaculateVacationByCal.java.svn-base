package com.spon.ems.vacation.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.vacation.forms.EHF020100M0F;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;
import com.spon.utils.work_schedule.EMS_Work_ScheduleForm;
import com.spon.utils.work_schedule.EMS_Work_Schedule_Table;

import org.apache.log4j.Logger;

import vtgroup.ems.common.vo.AuthorizedBean;


/**
 * EMS 請假單天數與時數計算元件 By Java Calendar
 * 
 * @version 1.0
 * @created 18-十月-2010 下午 06:00:47
 */
public class EMS_CaculateVacationByCal {
	

	static Logger loger = Logger.getLogger(EMS_CaculateVacationByCal.class.getName());
	
	//定義區間參數
	public static final int IN = 1;
	public static final int BREAK = 2;
	public static final int OUT = 3;
	public static final int ERROR = -1;
	
	private final String START_TRACE_FLAG = "START_TRACE_FLAG";
	private final String SAME_DAY_FLAG = "SAME_DAY_FLAG";
	private final String DIFF_DAY = "DIFF_DAY";
	
	private EMS_Work_Schedule_Table ems_work_schedule = null;
	private static BA_TOOLS tools = BA_TOOLS.getInstance();
	private static EMS_AttendanceAction ems_att_tools = EMS_AttendanceAction.getInstance("", "", ""); 
	
	private Calendar sta_cur_cal = null;
	private String cur_employee_id = "";
	private Map cur_classMap = null;
	//private boolean start_trace_flag = false;
	//private boolean same_day_flag = true;
	//private int diff_day = 0;
	private String real_start_date = "";
	private String real_end_date = "";
	private Calendar real_start_class_in_cal = null;
	private Calendar real_start_class_siesta_in_cal = null;
	private Calendar real_start_class_siesta_out_cal = null;
	private Calendar real_start_class_out_cal = null;
	private Calendar real_end_class_in_cal = null;
	private Calendar real_end_class_siesta_in_cal = null;
	private Calendar real_end_class_siesta_out_cal = null;
	private Calendar real_end_class_out_cal = null;

	public EMS_CaculateVacationByCal(){

	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * 計算實際請假區間天數與時數(已扣除例假日天數與時數)  return Map
	 * @param emscontext
	 * @param conn
	 * @param authbean
	 * @param leave	20140614 add by Hedwig
	 * @param start_date
	 * @param end_date
	 * @param start_time
	 * @param end_time
	 * @return
	 */
//	public Map caculateRealVa( Connection conn, AuthorizedBean authbean, String start_date, String end_date,
//			  				   String start_time, String end_time ){
	public Map caculateRealVa( Connection conn, AuthorizedBean authbean,String leave, String start_date, String end_date,
			   String start_time, String end_time ){
		
		Map result = new HashMap();
		
		try{
			float Day_work_hours = Float.parseFloat(this.tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));  //工作時數
			
			//建立請假Calendar
			Calendar start_cal = this.tools.covertStringToCalendar(start_date+" "+start_time, "yyyy/MM/dd HHmm");  //請假起
			Calendar end_cal = this.tools.covertStringToCalendar(end_date+" "+end_time, "yyyy/MM/dd HHmm");  //請假迄
			
			//建立員工排班表元件
			this.ems_work_schedule = new EMS_Work_Schedule_Table("EMS系統", authbean.getCompId());
			
			//設定當前日期
			this.sta_cur_cal = this.tools.covertStringToCalendar(start_date);;
			//設定當前員工工號
			this.cur_employee_id = authbean.getEmployeeID();
			//設定當前班別
			HR_TOOLS hr_tools = new HR_TOOLS();
			this.cur_classMap = hr_tools.getEmpClass(conn, authbean);
			hr_tools.close();
			
			//判斷請假區間檢核模式
			String ems_vacation_validate_mode = this.tools.getSysParam(conn, authbean.getCompId(), "EMS_VACATION_VALIDATE_MODE");
			if("BY_EMP_CLASS".equals(ems_vacation_validate_mode)){
				//依據員工實際班別資料進行請假區間檢核判斷
				//在檢核請假時間時，多加一個參數來檢查假別是否計算假日的檢核。 20140614 by Hedwig
//				result = this.caculateRealVaByEmpClass(conn, authbean.getEmployeeID(), start_date, end_date, start_cal, end_cal, 
//													   Day_work_hours, authbean.getCompId() );
				result = this.caculateRealVaByEmpClass(conn, authbean.getEmployeeID(), start_date, end_date, start_cal, end_cal, 
						   Day_work_hours, leave, authbean.getCompId() );
			}else if("BY_VACATION_FORM_DATA".equals(ems_vacation_validate_mode)){
				//依據員工實際班別資料進行請假區間檢核判斷
				//在檢核請假時間時，多加一個參數來檢查假別是否計算假日的檢核。 20140614 by Hedwig
//				result = this.caculateRealVaByEmpClass(conn, authbean.getEmployeeID(), start_date, end_date, start_cal, end_cal, 
//													   Day_work_hours, authbean.getCompId() );
				result = this.caculateRealVaByEmpClass(conn, authbean.getEmployeeID(), start_date, end_date, start_cal, end_cal, 
						   Day_work_hours, leave, authbean.getCompId() );
			}else{
				//依據員工實際班別資料進行請假區間檢核判斷
				//在檢核請假時間時，多加一個參數來檢查假別是否計算假日的檢核。 20140614 by Hedwig
//				result = this.caculateRealVaByEmpClass(conn, authbean.getEmployeeID(), start_date, end_date, start_cal, end_cal, 
//													   Day_work_hours, authbean.getCompId() );
				result = this.caculateRealVaByEmpClass(conn, authbean.getEmployeeID(), start_date, end_date, start_cal, end_cal, 
						   Day_work_hours, leave, authbean.getCompId() );
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return result;
	}
	
	/**
	 * 依據員工實際班別資料進行請假區間檢核判斷
	 * @param conn
	 * @param authbean
	 * @param start_date    請假起
	 * @param end_date		請假迄
	 * @param start_cal		請假起(Calendar)
	 * @param end_cal		請假迄(Calendar)
	 * @param Day_work_hours
	 * @param leave 假別代碼	20140614 add by Hedwig 
	 * @return
	 */
//	private Map caculateRealVaByEmpClass(Connection conn, String employee_id, String start_date, String end_date,
//			 Calendar start_cal, Calendar end_cal, float Day_work_hours, String comp_id){
	private Map caculateRealVaByEmpClass(Connection conn, String employee_id, String start_date, String end_date,
			 Calendar start_cal, Calendar end_cal, float Day_work_hours, String leave, String comp_id){

		Map result = new HashMap();

		try {
			String diffdays = "0";
			String diffhours = "0";
			long diff_msec = 0;
			double diff_day=0;//用來儲存請假起訖同一天的天數

			// 設定Condition 預設值
			Map conMap = new HashMap();
			conMap.put(this.START_TRACE_FLAG, false);
			conMap.put(this.SAME_DAY_FLAG, true);
			conMap.put(this.DIFF_DAY, (int) 0);

			// 判斷請假起的區間
			int start_status = this.determineStartDateRange(conn, employee_id,start_cal, conMap, comp_id);

			// 判斷請假迄的區間
			int end_status = this.determineEndDateRange(conn, employee_id,end_cal, conMap, comp_id);

			// 判斷是否有異常區間
			if (start_status == this.ERROR || end_status == this.ERROR) {
				
				if(start_date.equals(end_date)){
					//請假起訖時間為同一天
					if (start_status == this.ERROR && end_status == this.ERROR) {
						result.put("DAYS", "1"); // 天數
						result.put("HOURS", "0"); // 時數
						result.put("ERROR_TIME_START", "請假時間(起、迄)，於正常上下班時間外。"); 
					}else if(start_status == this.ERROR){
						//請假起在上班時間之前
						if(this.getTime(start_cal,end_cal)>=Float.parseFloat(tools.getSysParam(conn,comp_id,"WORK_HOURS"))){
							//超過一天工作天數  以一天計算
							result.put("DAYS", "1"); // 天數
							result.put("HOURS", "0"); // 時數
							result.put("ERROR_TIME_START", "請假時間(起)，於正常上班時間前。"); 
						}else{
							//不足一天以小時計算
							result.put("DAYS", "0"); // 天數
							result.put("HOURS", String.valueOf(this.getTime(start_cal,end_cal))); // 時數
							result.put("ERROR_TIME_START", "請假時間(起)，於正常上班時間前。"); 
						}
					}else if(end_status == this.ERROR){
						if(this.getTime(start_cal,end_cal)>=Float.parseFloat(tools.getSysParam(conn,comp_id,"WORK_HOURS"))){
							//超過一天工作天數  以一天計算
							result.put("DAYS", "1"); // 天數
							result.put("HOURS", "0"); // 時數
							result.put("ERROR_TIME_END", "請假時間(迄)，於正常下班時間後。"); 
						}else{
							//不足一天以小時計算
							result.put("DAYS", "0"); // 天數
							result.put("HOURS", String.valueOf(this.getTime(start_cal,end_cal))); // 時數
							result.put("ERROR_TIME_END", "請假時間(迄)，於正常下班時間後。"); 
						}
					}
				}else{
					//請假起訖時間為不同一天
					if (start_status == this.ERROR && end_status == this.ERROR) {
						long between_days=(end_cal.getTimeInMillis() - start_cal.getTimeInMillis())/(1000*3600*24);//兩個時間相差秒數    
						result.put("DAYS", String.valueOf(between_days+1)); // 天數
						result.put("HOURS", "0"); // 時數
						result.put("ERROR_TIME_START", "請假時間(起、迄)，於正常上下班時間外。"); 
						
					}else if(start_status == this.ERROR){
						//請假起在上班時間之前
						Map getTime=this.getAbnormalTime(conn, employee_id,start_cal,end_cal, conMap, comp_id,Float.parseFloat(tools.getSysParam(conn,comp_id,"WORK_HOURS")));
						result.put("DAYS", getTime.get("DAY")); // 天數
						result.put("HOURS", getTime.get("HOUR")); // 時數
						result.put("ERROR_TIME_START", "請假時間(起)，於正常上班時間前。"); 
						
					}else if(end_status == this.ERROR){
						Map getTime=this.getAbnormalTime(conn, employee_id,start_cal,end_cal, conMap, comp_id,Float.parseFloat(tools.getSysParam(conn,comp_id,"WORK_HOURS")));
						result.put("DAYS", getTime.get("DAY")); // 天數
						result.put("HOURS", getTime.get("HOUR")); // 時數
						result.put("ERROR_TIME_END", "請假時間(迄)，於正常下班時間後。"); 
					}
				}
				return result;
			}

			// 判斷請假起與請假迄是否為同一天
			if ((Boolean) conMap.get(this.SAME_DAY_FLAG)) { // this.same_day_flag
			// 同一天
				if (start_status == this.IN) {
					if (end_status == this.IN) {
						// 同時在IN區間
						diff_msec = end_cal.getTimeInMillis()- start_cal.getTimeInMillis();
					} else if (end_status == this.BREAK) {
						//
						diff_msec = this.real_start_class_siesta_in_cal.getTimeInMillis() -start_cal.getTimeInMillis();
					} else if (end_status == this.OUT) {
						//
						//禾益佳特殊規則，如果是請假請同一天，要判斷請假開始與結束~是否等於班別設定的上下班時間，如果都一樣就要減半個小時。
						/*if(this.real_start_class_in_cal.compareTo(start_cal)==0&&this.real_start_class_out_cal.compareTo(end_cal)==0){
							end_cal.add(Calendar.SECOND,-1800);
						}else if(this.real_start_class_out_cal.compareTo(end_cal)==0){
							end_cal.add(Calendar.SECOND,-1800);
						}*/
						
						diff_msec = (this.real_start_class_siesta_in_cal.getTimeInMillis() - start_cal.getTimeInMillis())+ (end_cal.getTimeInMillis() - this.real_start_class_siesta_out_cal.getTimeInMillis());
						
					} else {
						diff_msec = 0;
					}
				} else if (start_status == this.BREAK) {
					if (end_status == this.BREAK) {
						diff_msec = 0;
					} else if (end_status == this.OUT) {
						//禾益佳特殊規則，如果是請假請同一天，要判斷請假開始與結束~是否等於班別設定的上下班時間，如果都一樣就要減半個小時。
						/*if(this.real_start_class_out_cal.compareTo(end_cal)==0){
							end_cal.add(Calendar.SECOND,-1800);
						}*/
						
						
						diff_msec = end_cal.getTimeInMillis() - this.real_start_class_siesta_out_cal.getTimeInMillis();
					} else {
						diff_msec = 0;
					}
				} else if (start_status == this.OUT) {
					if (end_status == this.OUT) {
						
						
						/*if(this.real_start_class_siesta_out_cal.compareTo(start_cal)==0&&this.real_start_class_out_cal.compareTo(end_cal)==0){
							end_cal.add(Calendar.SECOND,-1800);
						}*/
						
						
						// 同時在OUT區間
						diff_msec = end_cal.getTimeInMillis()- start_cal.getTimeInMillis();
					} else {
						diff_msec = 0;
					}
				} else {
					diff_msec = 0;
				}
			} else {
				// 先計算請假起
				long start_diff_time = 0;
				if (start_status == this.IN) {
					/*if(this.real_start_class_in_cal.compareTo(start_cal)==0){
						this.real_start_class_out_cal.add(Calendar.SECOND,-1800);
					}*/
					
					start_diff_time = (this.real_start_class_siesta_in_cal.getTimeInMillis() - start_cal.getTimeInMillis()) + (this.real_start_class_out_cal.getTimeInMillis() - this.real_start_class_siesta_out_cal.getTimeInMillis());
				} else if (start_status == this.BREAK) {
					//禾益佳特殊規則，如果是請假請同一天，要判斷請假開始與結束~是否等於班別設定的上下班時間，如果都一樣就要減半個小時。
					//this.real_start_class_out_cal.add(Calendar.SECOND,-1800);
					
					start_diff_time = this.real_start_class_out_cal.getTimeInMillis()- this.real_start_class_siesta_out_cal.getTimeInMillis();
					//禾益佳特殊規則，如果是請假請同一天，要判斷請假開始與結束~是否等於班別設定的上下班時間，如果都一樣就要減半個小時。
					//this.real_start_class_out_cal.add(Calendar.SECOND,1800);
					
					
				} else if (start_status == this.OUT) {
					start_diff_time = this.real_start_class_out_cal
							.getTimeInMillis()
							- start_cal.getTimeInMillis();
				} else {
					start_diff_time = 0;
				}

				// 在計算請假迄
				long end_diff_time = 0;
				if (end_status == this.IN) {
					end_diff_time = end_cal.getTimeInMillis() - this.real_end_class_in_cal.getTimeInMillis();
				} else if (end_status == this.BREAK) {
					end_diff_time = this.real_end_class_siesta_in_cal.getTimeInMillis()	- this.real_end_class_in_cal.getTimeInMillis();
				} else if (end_status == this.OUT) {
					
					//禾益佳特殊規則，如果是請假請同一天，要判斷請假開始與結束~是否等於班別設定的上下班時間，如果都一樣就要減半個小時。
					/*if(this.real_end_class_out_cal.compareTo(end_cal)==0){
						end_cal.add(Calendar.SECOND,-1800);
					}*/
					
					end_diff_time = (this.real_end_class_siesta_in_cal.getTimeInMillis() - this.real_end_class_in_cal.getTimeInMillis())+ (end_cal.getTimeInMillis() - this.real_end_class_siesta_out_cal.getTimeInMillis());
				} else {
					end_diff_time = 0;
				}

				diff_msec = start_diff_time + end_diff_time;
			}

			// 建立請假區間結果
			// int diffVaDay = this.diff_day;
			int diffVaDay = (Integer) conMap.get(this.DIFF_DAY);
			float diffVaHours = ((float) (diff_msec / 1000)) / 60 / 60;

			// 計算假日區間結果。 Hedwig
			Map diffHoresult = null;
			if ((Boolean) conMap.get(this.SAME_DAY_FLAG)) { // this.same_day_flag
				//在檢核請假時間時，多加一個參數來檢查假別是否計算假日的檢核。 20140614 by Hedwig
//				diffHoresult = this.caculateDiffHo(conn, start_date, start_date, comp_id);
				diffHoresult = this.caculateDiffHo(conn, start_date, start_date, leave, employee_id, comp_id);
			} else {
			//在檢核請假時間時，多加一個參數來檢查假別是否計算假日的檢核。 20140614 by Hedwig
//				diffHoresult = this.caculateDiffHo(conn, start_date, end_date, comp_id);
				diffHoresult = this.caculateDiffHo(conn, start_date, end_date, leave, employee_id, comp_id);
			}
			int diffHoDay = Integer.parseInt((String) diffHoresult.get("DAYS"));
			float diffHoHours = Float.parseFloat((String) diffHoresult.get("HOURS"));
			//多加一個值來判斷請假天數是否有計算休假。如果為true就有計算休假，如果為false就沒有計算休假。 20140603 by Hedwig
			boolean DayOfWeek = Boolean.valueOf((String) diffHoresult.get("DayOfWeek"));

			// 處理時間單位換算
			/*float totalHours = ((diffVaDay * Day_work_hours) + diffVaHours) - ((diffHoDay * Day_work_hours) + diffHoHours);

			if(start_date.equals(end_date)&&(this.ems_work_schedule.getOneDayInSchedule(conn, employee_id, start_date).isHoliday_flag())){
				//如果起訖時間為同一天，又是休假，就把扣除的假日時間加回來。
				totalHours=totalHours+((diffHoDay * Day_work_hours) + diffHoHours);
			}
			*/
			float totalHours = 0;
			if(DayOfWeek){
				totalHours = ((diffVaDay * Day_work_hours) + diffVaHours);
			}else{
				totalHours = ((diffVaDay * Day_work_hours) + diffVaHours) - ((diffHoDay * Day_work_hours) + diffHoHours );
			}
			// 判斷大於0才處理
			if (totalHours > 0) {
				float temp_diffhours = totalHours;
				int temp_day = 0;
				if (temp_diffhours >= Day_work_hours) {
					temp_day = (int) (temp_diffhours / Day_work_hours);
					diffhours = String.valueOf(temp_diffhours % Day_work_hours);
				} else {
					diffhours = String.valueOf(temp_diffhours);
				}
				diffdays = String.valueOf(temp_day);
			} else {
				diffdays = "0";
				diffhours = "0";
			}

			// 建立結果Map
			result.put("DAYS", diffdays); // 天數
			result.put("HOURS", diffhours); // 時數

	
			
			
			//取得並設定員工排班表資料
			if((this.ems_work_schedule.getOneDayInSchedule(conn, employee_id, start_date).isHoliday_flag())&&(this.ems_work_schedule.getOneDayInSchedule(conn, employee_id, end_date).isHoliday_flag())){
				result.put("ERROR_START", "請假時間(起、迄)為休假日"); // 時數
			}
			else if((this.ems_work_schedule.getOneDayInSchedule(conn, employee_id, start_date).isHoliday_flag())){
				result.put("ERROR_START", "請假時間(起)為休假日"); // 時數
			}
			else if((this.ems_work_schedule.getOneDayInSchedule(conn, employee_id, end_date).isHoliday_flag())){
				result.put("ERROR_END", "請假時間(迄)為休假日"); //
			//假如假別設定必須要包含休假的天數，也需要顯示"請假時間內有休假日"的訊息。 by Hedwig 
//			}else if(((diffHoDay * Day_work_hours) + diffHoHours)>0){
			}else if(((diffHoDay * Day_work_hours) + diffHoHours)>0 || DayOfWeek){
//				result.put("ERROR_END", "請假時間內有休假日"); // 
			}
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	
	/**
	 * 判斷請假起始日期區間
	 * @param conn
	 * @param start_cal
	 * @param comp_id
	 * @return
	 */
	private int determineStartDateRange( Connection conn, String employee_id, Calendar start_cal, Map conMap, String comp_id ){
		
		int status = -1;  //判斷狀態
		
		try{
			//設定初始參數
			String cur_day = this.tools.covertDateToString(this.sta_cur_cal.getTime(), "yyyy/MM/dd");  //當前日期
			this.real_start_date = cur_day;

			//依據排班表設定員工指定日期的班別資料
			Map tempClassMap = this.getEmpClass(conn, cur_day, comp_id);

			Calendar class_in_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 1);  //班別設定的上班時間(Calendar)
			this.real_start_class_in_cal = (Calendar) class_in_cal.clone();
			Calendar class_siesta_in_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 7);  //班別設定的上午下班時間(Calendar)
			this.real_start_class_siesta_in_cal = (Calendar) class_siesta_in_cal.clone();
			Calendar class_siesta_out_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 8);  //班別設定的下午上班時間(Calendar)
			this.real_start_class_siesta_out_cal = (Calendar) class_siesta_out_cal.clone();
			Calendar class_out_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 2);  //班別設定的下班時間(Calendar)
			this.real_start_class_out_cal = (Calendar) class_out_cal.clone();
			
			//判斷請假起
			if(start_cal.compareTo(class_in_cal) < 0){
				//小於當前日期上班時間, 往前追溯一天
				this.sta_cur_cal.add(Calendar.DAY_OF_MONTH, -1);  //往前追溯一天
				if(!(Boolean)conMap.get(this.START_TRACE_FLAG)){  //!this.start_trace_flag
					//this.start_trace_flag = true;  //將請假起的追溯Flag設定為 true
					conMap.put(this.START_TRACE_FLAG, true);
					status = this.determineStartDateRange(conn, employee_id, start_cal, conMap, comp_id);
				}else{
					//異常, 無法判斷區間
					status = this.ERROR;
				}
			}else if(start_cal.compareTo(class_out_cal) > 0){
				//大於當前日期下班時間, 往後追溯一天
				this.sta_cur_cal.add(Calendar.DAY_OF_MONTH, 1);  //往後追溯一天
				if(!(Boolean)conMap.get(this.START_TRACE_FLAG)){  //!this.start_trace_flag
					//this.start_trace_flag = true;  //將請假起的追溯Flag設定為 true
					conMap.put(this.START_TRACE_FLAG, true);
					status = this.determineStartDateRange(conn, employee_id, start_cal, conMap, comp_id);
				}else{
					//異常, 無法判斷區間
					status = this.ERROR;
				}
			}else{
				if(start_cal.compareTo(class_in_cal) >= 0 && start_cal.compareTo(class_siesta_in_cal) < 0){
					//大於等於上班時間 且 小於中間休息起
					status = this.IN ;
				}else if(start_cal.compareTo(class_siesta_in_cal) >= 0 && start_cal.compareTo(class_siesta_out_cal) < 0){
					//大於等於中間休息起 且 小於中間休息迄
					status = this.BREAK;
				}else if(start_cal.compareTo(class_siesta_out_cal) >= 0 && start_cal.compareTo(class_out_cal) <= 0){
					//大於等於中間休息迄 且 小於等於下班
					status = this.OUT;
				}else{
					//異常, 無法判斷區間
					status = this.ERROR;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return status;
	}
	
	/**
	 * 判斷請假結束日期區間
	 * @param conn
	 * @param end_cal
	 * @param comp_id
	 * @return
	 */
	private int determineEndDateRange( Connection conn, String employee_id, Calendar end_cal, Map conMap, String comp_id ){
		
		int status = -1;  //判斷狀態
		
		try{
			//設定初始參數
			String cur_day = this.tools.covertDateToString(this.sta_cur_cal.getTime(), "yyyy/MM/dd");  //當前日期
			this.real_end_date = cur_day;

			//依據排班表設定員工指定日期的班別資料
			Map tempClassMap = this.getEmpClass(conn, cur_day, comp_id);
			
			Calendar class_in_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 1);  //班別設定的上班時間(Calendar)
			this.real_end_class_in_cal = (Calendar) class_in_cal.clone();
			Calendar class_siesta_in_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 7);  //班別設定的上午下班時間(Calendar)
			this.real_end_class_siesta_in_cal = (Calendar) class_siesta_in_cal.clone();
			Calendar class_siesta_out_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 8);  //班別設定的下午上班時間(Calendar)
			this.real_end_class_siesta_out_cal = (Calendar) class_siesta_out_cal.clone();
			Calendar class_out_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 2);  //班別設定的下班時間(Calendar)
			this.real_end_class_out_cal = (Calendar) class_out_cal.clone();
			
			//判斷請假迄
			if(end_cal.compareTo(class_in_cal) < 0){
				//小於當前日期上班時間, 依據目前設定的邏輯判斷不會有小於上班時間的狀況發生
				//異常, 無法判斷區間
				status = this.ERROR;
			}else if(end_cal.compareTo(class_out_cal) > 0){
				//大於當前日期下班時間, 往後追溯一天
				this.sta_cur_cal.add(Calendar.DAY_OF_MONTH, 1);  //往後追溯一天
				//判斷請假起與請假迄是否為同一天
				if((Boolean)conMap.get(this.SAME_DAY_FLAG)){ //this.same_day_flag
					//this.same_day_flag = false;
					conMap.put(this.SAME_DAY_FLAG, false);
				}else{
					//this.diff_day++;  //將請假起與請假迄相差的天數加一
					conMap.put(this.DIFF_DAY, ((Integer)conMap.get(this.DIFF_DAY))+1);
				}
				status = this.determineEndDateRange(conn, employee_id, end_cal, conMap, comp_id);
			}else{
				if(end_cal.compareTo(class_out_cal) <= 0 && end_cal.compareTo(class_siesta_out_cal) > 0){
					//小於等於下班時間 且 大於中間休息迄
					status = this.OUT ;
				}else if(end_cal.compareTo(class_siesta_out_cal) <= 0 && end_cal.compareTo(class_siesta_in_cal) > 0){
					//小於等於中間休息迄 且 大於中間休息起
					status = this.BREAK;
				}else if(end_cal.compareTo(class_siesta_in_cal) <= 0 && end_cal.compareTo(class_in_cal) >= 0){
					//小於等於中間休息起 且 大於等於上班
					status = this.IN;
				}else{
					//異常, 無法判斷區間
					status = this.ERROR;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	/**
	 * start_status == this.ERROR && end_status == this.ERROR的時候
	 * 取得請假(起)到班別設定上班時間   之間的時數
	 * 取得班別設定下班時間到請假(迄) 之間的時數
	 * @param conn
	 * @param employee_id
	 * @param end_cal
	 * @param conMap
	 * @param comp_id
	 * @param Day_work_hours 
	 * @return
	 */
	private Map getAbnormalTime( Connection conn, String employee_id,Calendar start_cal,Calendar end_cal, Map conMap, String comp_id, float Day_work_hours ){
		int day=0;
		int count=0;
		long hour=0;
		Map return_MAP = new HashMap();
		try{
			boolean chk_run_flag = true;

			while (chk_run_flag) {
				//設定初始參數
				String cur_day = this.tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前日期

				//依據排班表設定員工指定日期的班別資料
				Map tempClassMap = this.getEmpClass(conn, cur_day, comp_id);
				
				Calendar class_in_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 1);  //班別設定的上班時間(Calendar)
				Calendar class_siesta_in_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 7);  //班別設定的上午下班時間(Calendar)
				Calendar class_siesta_out_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 8);  //班別設定的下午上班時間(Calendar)
				Calendar class_out_cal = this.ems_att_tools.getCalendarByClass(conn, cur_day, employee_id, comp_id, tempClassMap, 2);  //班別設定的下班時間(Calendar)
				
				String start_cal_DAY = this.tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前日期
				String end_cal__DAY  = this.tools.covertDateToString(end_cal.getTime(), "yyyy/MM/dd");  //當前日期
				
				if (start_cal_DAY.equals(end_cal__DAY)) {
					chk_run_flag = false;
				}

				if (!chk_run_flag) {
					// 時間走道 請假時間(迄)那一天
					long between_days=((class_siesta_in_cal.getTimeInMillis() - class_in_cal.getTimeInMillis()) + (end_cal.getTimeInMillis() - class_siesta_out_cal.getTimeInMillis()))/(1000*3600);//兩個時間相差秒數    
					if(between_days>=Day_work_hours){
						day+=1;
						
					}else{
						long totalHours=((class_siesta_in_cal.getTimeInMillis() - class_in_cal.getTimeInMillis()) + (end_cal.getTimeInMillis() - class_siesta_out_cal.getTimeInMillis()));//兩個時間相差秒數
						long nd = 1000*24*60*60;//一天的毫秒數
						long nh = 1000*60*60;//一小時的毫秒數
						hour = totalHours%nd/nh;//計算差多少小時
						
					}
				}else{
					if (count == 0) {
						long between_days = ((class_siesta_in_cal.getTimeInMillis() - start_cal.getTimeInMillis()) + (class_out_cal.getTimeInMillis() - class_siesta_out_cal.getTimeInMillis()))/ (1000 * 3600);// 兩個時間相差秒數
						if (between_days >= Day_work_hours) {
							day += 1;
						}else{
							long totalHours=((class_siesta_in_cal.getTimeInMillis() - start_cal.getTimeInMillis()) + (class_out_cal.getTimeInMillis() - class_siesta_out_cal.getTimeInMillis()));//兩個時間相差秒數
							long nd = 1000*24*60*60;//一天的毫秒數
							long nh = 1000*60*60;//一小時的毫秒數
							hour = totalHours%nd/nh;//計算差多少小時
							
						}
						
						count++;
					} else {
						day += 1;
					}
				}
				
				
				
				// 月份往下走一天
				start_cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			
			
			return_MAP.put("DAY", String.valueOf(day));
			return_MAP.put("HOUR", String.valueOf(hour));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_MAP;
	}
	
	
	
	
	/**
	 * 取得員工班別資料
	 * @param conn
	 * @param cur_day
	 * @param comp_id
	 * @return
	 */
	private Map getEmpClass(Connection conn, String cur_day, String comp_id){
		
		Map classMap = null;
		Map WorkSchedule = new HashMap();
		Map NotWorkDay = new HashMap();
		
		try{
			//取得並設定員工排班表資料
			/*EMS_Work_ScheduleForm cur_emp_work_schedule = this.ems_work_schedule.getOneDayInSchedule(conn, this.cur_employee_id, cur_day);
			
			//依據排班表設定員工指定日期的班別資料
			//if(cur_emp_work_schedule.getSchedule_no() != -1){
			if(cur_emp_work_schedule.getSchedule_no() != -1&&cur_emp_work_schedule.getClass_no() != -1){//修改於20131114 BY賴泓錡
				HR_TOOLS hr_tools = new HR_TOOLS();
				classMap = hr_tools.getEmpClassByNo( conn, comp_id, cur_emp_work_schedule.getClass_no());
				hr_tools.close();
			}else{
				classMap = this.cur_classMap;
			}*/
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			//員工Map
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			//部門Map
			Map depMap = hr_tools.getDepNameMap(comp_id);
			
			hr_tools.close();
			
			//產生預排換班處理元件
			EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
			
			//取得排班表與行事曆
			ems_wsc_tools.getVacations(conn, empMap, depMap, comp_id, cur_day, "", WorkSchedule);
			
			NotWorkDay = (Map) WorkSchedule.get(this.cur_employee_id);
			
			int  choose=0;
			
			if(NotWorkDay==null){
				//表示要上班,但   是使用預設班別				
				choose=1;
			}else if(NotWorkDay.get(cur_day)==null){
				//表示要上班,但   是使用預設班別				
				choose=2;
			}else if(NotWorkDay.get(cur_day).equals("-1")){
				//表示假日				
				choose=3;
			}else{
				//表示要上班，且有指定班別				
				choose=4;
			}
			
			switch(choose){
			
			case 0:
				//表示異常
				break;
				
			case 1:
				//表示要上班,但   是使用預設班別
				classMap = this.cur_classMap;
				break;
			case 2:
				//表示要上班,但   是使用預設班別
				classMap = this.cur_classMap;
				break;
			case 3:
				//表示放假
				classMap = this.cur_classMap;
				break;
			case 4:
				//表示要上班,且有指定班別
				classMap = hr_tools.getEmpClassByNo( conn, comp_id, Integer.parseInt((String)NotWorkDay.get(cur_day)));
				break;
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return classMap;
	}
	
	/**
	 * 取得員工班別資料
	 * @param conn
	 * @param startCal
	 * @param comp_id
	 * @return
	 */
	private int getEmpClass_Number(Connection conn, Calendar startCal, String comp_id){
		
		int i=0;
		String cur_day = this.tools.covertDateToString(startCal.getTime(), "yyyy/MM/dd");  //當前日期
		try{
			//取得並設定員工排班表資料
			EMS_Work_ScheduleForm cur_emp_work_schedule = this.ems_work_schedule.getOneDayInSchedule(conn, this.cur_employee_id, cur_day);
			i=cur_emp_work_schedule.getClass_no();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return i;
	}
	
	
	/**
	 * 計算例假日區間的天數與時數  return Map(改撈取排班表) 20111114 修改  BY 賴泓錡
	 * @param conn
	 * @param start_date
	 * @param end_date
	 * @param leave 假別代碼 20140614 add by Hedwig
	 * @param employee_id 員工代碼 20140614 add by Hedwig
	 * @param comp_id
	 * @return
	 */
//	private Map caculateDiffHo( Connection conn, String start_date, String end_date, String comp_id ){
	private Map caculateDiffHo( Connection conn, String start_date, String end_date,String leave, String employee_id, String comp_id ){
		
		Map diffVaresult = new HashMap();
		//如果次假別需要計算休假日，則此參數為true，相反則為false。 20140614 by Hedwig
		boolean DayOfWeek = false;
		try{/*
			String now_start_year = this.tools.getDateADYear(start_date, false);  //找出請假(起)年度
			String now_end_year = this.tools.getDateADYear(end_date, false);  //找出請假(迄)年度
			
			Statement stmt = conn.createStatement();
			String sql = "";
			
			//目前EMS系統行事曆不可設定多天
			sql += " SELECT a.*,b.EHF010105T1_02 From(" +
				   " SELECT EHF000500T0_03, DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS EHF000500T0_05, " +
				   " DATE_FORMAT(EHF000500T0_06, '%Y/%m/%d') AS EHF000500T0_06, EHF000500T0_07, EHF000500T0_08, EHF020100T0_11 " +
				   " FROM EHF000500T0 " +
				   " LEFT JOIN EHF020100T0 ON EHF020100T0_03 = '"+leave+"' AND EHF020100T0_08 = EHF000500T0_11 "+
				   " WHERE 1=1 " +
				   " AND ( EHF000500T0_03 = '"+now_start_year+"' OR EHF000500T0_03 = '"+now_end_year+"' ) " +
				   " AND ( EHF000500T0_05 BETWEEN '"+start_date+"' AND '"+end_date+"' AND EHF000500T0_06 BETWEEN '"+start_date+"' AND '"+end_date+"' ) " +
				   " AND EHF000500T0_11 = '"+comp_id+"' " +
				   " ) a LEFT JOIN ehf010105t1 b  ON a.EHF000500T0_05!=b.EHF010105T1_02" +
				   " where b.EHF010105T1_02 is not null" ;
			
			//改抓排班表
//			sql += " SELECT EHF010105T1_01, DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') AS EHF010105T1_02 " +
//				   " FROM EHF010105T1 " +
//				   " WHERE 1=1 " +
//				   " AND  EHF010105T1_02 BETWEEN '"+start_date+"' AND '"+end_date+"' " +
//				   " AND EHF010105T1_03='1'" +
//				   " AND EHF010105T1_06 = '"+comp_id+"' " ;
			//1.依照請假區間取得排班表時，需限定申請人的自己的員工工號。
			//20140603 by Hedwig
//			sql += " SELECT EHF010105T1_01, DATE_FORMAT(EHF010105T1_02, '%Y/%m/%d') AS EHF010105T1_02, " +
//			   " EHF020100T0_11 " +
//			   " FROM EHF010105T1 " +
//			   " LEFT JOIN EHF010105T0 ON EHF010105T0_01 = EHF010105T1_01 AND EHF010105T1_06 = EHF010105T0_06 " +
//			   " LEFT JOIN EHF020100T0 ON EHF020100T0_03 = '" +leave+"' AND EHF020100T0_08 = EHF010105T1_06 "+
//			   " WHERE 1=1 " +
//			   " AND EHF010105T1_02 BETWEEN '"+start_date+"' AND '"+end_date+"' " +
//			   " AND EHF010105T1_03='1'" +
//			   " AND EHF010105T0_02 = '" +employee_id+"'"+
//			   " AND EHF010105T1_06 = '"+comp_id+"' " ;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			int days = 0;
			float hours = 0;
			//多加判斷公司假別設定裡"是否計算假日"的設定來計算請假天數。 20140603 by Hedwig
//			while(rs.next()){
//				days++;
//			}
			while(rs.next()){
				//判斷"是否計算假日"。
				if(!rs.getBoolean("EHF020100T0_11")){
					//不需要將假日算進請假天數裡。
					days++;
				}else{
					DayOfWeek = true;
				}
			}
			rs.close();
			stmt.close();
			diffVaresult.put("DAYS", String.valueOf(days)) ;
			diffVaresult.put("HOURS", String.valueOf(hours));
			diffVaresult.put("DayOfWeek", String.valueOf(DayOfWeek));
			*/
			EMS_CheckVacation checkva_tools = (EMS_CheckVacation) EMS_CheckVacation.getInstance();
			//取得系統假別設定資訊
			EHF020100M0F ehf020100m0 = checkva_tools.getVacationTypeInf(conn, leave, comp_id);
			
			Map WorkSchedule = new HashMap();
			Map NotWorkDay = new HashMap();
			
			//產生預排換班處理元件
			EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
			
			//取得排班表與行事曆
			ems_wsc_tools.getVacationsByEMP(conn, employee_id, comp_id, start_date, end_date, WorkSchedule);
			
			Calendar start_cal = tools.covertStringToCalendar(start_date);  //考勤計算開始日期
			Calendar end_cal = tools.covertStringToCalendar(end_date);  //考勤計算結束日期
			
			boolean chk_run_flag = true;
			int days = 0;
			float hours = 0;
			NotWorkDay = (Map) WorkSchedule.get(employee_id);
			
			while(chk_run_flag){
				
				if(start_cal.equals(end_cal)){
					chk_run_flag = false;
				}
				
				if(NotWorkDay==null){
					//表示要上班,但   是使用預設班別					
				}else if(NotWorkDay.get(tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd"))==null){
					//表示要上班,但   是使用預設班別					
				}else if(NotWorkDay.get(tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd")).equals("-1")){
					//表示假日
					if(ehf020100m0 != null){
						//判斷"是否計算假日"。
						if(!tools.StringToBoolean(ehf020100m0.getEHF020100T0_11())){
							//不需要將假日算進請假天數裡。
							days++;
						}else{
							DayOfWeek = true;
						}
					}else{
						days++;
					}
				}else{
					//表示要上班，且有指定班別					
				}
				
				start_cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			diffVaresult.put("DAYS", String.valueOf(days)) ;
			diffVaresult.put("HOURS", String.valueOf(hours));
			diffVaresult.put("DayOfWeek", String.valueOf(DayOfWeek));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return diffVaresult;
	}
	
	/**
	 * 重新計算請假時數
	 * @param conn
	 * @param authbean
	 * @param employee_id
	 * @param dept_id
	 * @param start_date
	 * @param end_date
	 * @param salary_count_flag
	 * @return
	 */
	public int computeVaTime(Connection conn, AuthorizedBean authbean, String employee_id, String dept_id, 
							 String start_date, String end_date,
							 boolean salary_count_flag) {
		
		BA_TOOLS tools =  BA_TOOLS.getInstance();
		
		EMS_ACCESS ems_access = EMS_ACCESS.getInstance();
		
		
		String HR = tools.getSysParam(conn, authbean.getCompId(),"HR_IDENTITY");	//預設人資100
		String AC = tools.getSysParam(conn, authbean.getCompId(),"AC_IDENTITY");		//會計		110
		String BOSS = tools.getSysParam(conn, authbean.getCompId(),"BOSS_IDENTITY");	//老闆		111
		
		
		int dataCount = 0;
		
		try {			
			String flow_no = tools.getSysParam(conn, authbean.getCompId(), "Vacation_FLOW_NO");
			//String form_no = ems_tools.getSysParam(conn, cur_authbean.getCompId(), "Vacation_FORM_NO");

				String sql= "";
				//判斷是否為人諮群組(人資角色)
				if(salary_count_flag || ems_access.checkEmsRoleEmp(conn, authbean, HR) || ems_access.checkEmsRoleEmp(conn, authbean, AC) 
						|| ems_access.checkEmsRoleEmp(conn, authbean, BOSS)){
					//人資角色、會計、老闆
					sql = "" +
					" SELECT * ,  " +
					" DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS C_START_DATE, DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') AS C_END_DATE,  " +
					" DATE_FORMAT(EHF020200T0_09, '%Y年%c月%e日') AS START_DATE, DATE_FORMAT(EHF020200T0_10, '%Y年%c月%e日') AS END_DATE, " +
					" B.EHF020100T0_04 AS EHF020100T0_04 FROM EHF020200T0 " +
					" LEFT JOIN EHF020100T0 B ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 " +
					" WHERE 1=1 " +
					" AND EHF020200T0_18 = '"+authbean.getCompId()+"' ";
					
					if (!dept_id.equals("") && !employee_id.equals("")){  //員工
						sql += " AND EHF020200T0_04 = '"+dept_id+"' " +  //部門代號
							   " AND EHF020200T0_03 = '"+employee_id+"' ";  //員工工號
					}else if(!dept_id.equals("")){  //部門組織
						sql += " AND EHF020200T0_04 = '"+dept_id+"' ";  //部門代號
					}
				}else{
					//一般員工
					sql = "" +
					" SELECT * , " +
					" DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS C_START_DATE, DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') AS C_END_DATE,  " +
					" DATE_FORMAT(EHF020200T0_09, '%Y年%c月%e日') AS START_DATE, DATE_FORMAT(EHF020200T0_10, '%Y年%c月%e日') AS END_DATE, " +
					" B.EHF020100T0_04 AS EHF020100T0_04 FROM EHF020200T0 " +
					" LEFT JOIN EHF020100T0 B ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 " +
					" WHERE 1=1 " +
					" AND EHF020200T0_03 = '"+authbean.getEmployeeID()+"' " +  //一般員工只帶入自己的工號
					" AND EHF020200T0_18 = '"+authbean.getCompId()+"' ";
					
				}
				
				if(!"".equals(start_date) && start_date != null && !"".equals(end_date) && end_date !=null ){
					sql += " AND ( " +
					   	   "  DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //請假日期(起)
					   	   "  OR " +
					   	   "  DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') BETWEEN '"+start_date+"' AND '"+end_date+"' " +  //請假日期(迄)
					   	   " ) " ; 
				}else if(!"".equals(start_date) && start_date != null){
					sql += " AND ( " +
					   	   " DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') >= '"+start_date+"' " +
					   	   " OR " +
					   	   " DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') >= '"+start_date+"' " +
					   	   " ) ";
				}else if(!"".equals(end_date) && end_date != null){
					sql += " AND ( " +
					   	   " DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') <= '"+end_date+"' " +
					   	   " OR " +
					   	   " DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') <= '"+end_date+"' " +
					   	   " ) ";
				}
				
				sql += " ORDER BY EHF020200T0_04 , EHF020200T0_03 , EHF020200T0_01 DESC ";
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				//請假單使用的User AuthBean
				AuthorizedBean user_authbean = null;
				Map result = null;
				
				//更新請假單的SQL操作
				//更新資料
				String update_sql = "" +
				" UPDATE ehf020200t0 " +
				" SET EHF020200T0_13=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE EHF020200T0_01 = ? " +
				" AND EHF020200T0_18 = ? ";

				PreparedStatement pstmt = conn.prepareStatement(update_sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, update_sql);
				int indexid = 1;
				
				while(rs.next()){
					indexid = 1;
					
					//設定 authbean
					user_authbean = new AuthorizedBean();
					user_authbean.setEmployeeID(rs.getString("EHF020200T0_03"));  //當前申請人員工工號
					user_authbean.setCompId(authbean.getCompId());
					user_authbean.setUserId(authbean.getUserId());
					user_authbean.setUserCode(authbean.getUserCode());
					//在檢核請假時間時，多加一個參數來檢查假別是否計算假日的檢核。 20140614 by Hedwig
//					result = this.caculateRealVa( conn, user_authbean, rs.getString("C_START_DATE"), rs.getString("C_END_DATE"),
//							 rs.getString("EHF020200T0_11"), rs.getString("EHF020200T0_12"));
					result = this.caculateRealVa( conn, user_authbean, rs.getString("EHF020200T0_14"), rs.getString("C_START_DATE"), rs.getString("C_END_DATE"),
							 rs.getString("EHF020200T0_11"), rs.getString("EHF020200T0_12"));
					p6stmt.setString(indexid, ((String)result.get("DAYS"))+"/"+((String)result.get("HOURS")));  //請假天數&時數
					indexid++;
					p6stmt.setString(indexid, authbean.getUserName());  //修改人員
					indexid++;
					p6stmt.setString(indexid, rs.getString("EHF020200T0_01"));  //請假單單號
					indexid++;
					p6stmt.setString(indexid, authbean.getCompId());  //公司代碼
					indexid++;
					
					//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
					
					p6stmt.addBatch();
					dataCount++;
				}
				
				p6stmt.executeBatch();
				pstmt.close();
				p6stmt.close();
				
				//更新資料庫
				conn.commit();
				
				rs.close();
				stmt.close();
				
		} catch (Exception e) {
			System.out.println(e);
		} 

		return dataCount;
	}
	
	/**
	 * 當請假起迄為同一天時   算出時數
	 * @param start_cal
	 * @param end_cal
	 * @return
	 */
	private float getTime(Calendar start_cal, Calendar end_cal) {
		// TODO Auto-generated method stub
		
		long totalHours=end_cal.getTimeInMillis() - start_cal.getTimeInMillis();//兩個時間相差秒數
		long nd = 1000*24*60*60;//一天的毫秒數
		long nh = 1000*60*60;//一小時的毫秒數
		long hour = totalHours%nd/nh;//計算差多少小時

		return hour;
	}
		
}
