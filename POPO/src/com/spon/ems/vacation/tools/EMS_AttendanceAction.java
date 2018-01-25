package com.spon.ems.vacation.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.hr.forms.EHF010100M0F;
import com.spon.ems.util.overtime.EMS_OvertimeAttendanceHandle;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_Work_ScheduleForm;
import com.spon.utils.work_schedule.EMS_Work_Schedule_Table;

/**
 * EMS 考勤資料  元件
 * @author maybe
 * @version 1.0
 * @created 14-八月-2015
 */
public class EMS_AttendanceAction {
	
	private volatile static EMS_AttendanceAction attendance_tools;
	private static BA_TOOLS tools = BA_TOOLS.getInstance();
	
	private static String sta_comp_id = "";
	private static String sta_cur_day = "";
	private static String sta_user_id = "";
	private static String cur_employee_id = "";
	
	private static boolean isHoliday_FK;
	
	private static boolean today_FK;//如果是true  表示要產生當下時間，  false表示要產生前一天的考勤
	
	
	
	private boolean commit_flag = true;
	private boolean InAtt_flag = false;		//上班是否有正常刷卡資料
	private boolean EndAtt_flag = false;	//下班是否有正常刷卡資料
	private EMS_Work_ScheduleForm cur_emp_work_schedule = null;
	
	private static Map WorkSchedule ;
	
	public static EMS_AttendanceAction getInstance(String comp_id, String cur_day, String user_id) {
		today_FK=false;
		/*if (attendance_tools == null){
        	attendance_tools = new EMS_AttendanceAction(comp_id, cur_day, user_id);
        }else{
        	sta_comp_id = comp_id;
    		sta_cur_day = cur_day;
    		sta_user_id = user_id;
        }*/
		
		if(attendance_tools == null) {
            synchronized(EMS_AttendanceAction.class) {
            	attendance_tools = new EMS_AttendanceAction(comp_id, cur_day, user_id);
            }
        }else{
        	sta_comp_id = comp_id;
    		sta_cur_day = cur_day;
    		sta_user_id = user_id;
        }
        
        return attendance_tools;
	}
	
	private EMS_AttendanceAction(String comp_id, String cur_day, String user_id){
		EMS_AttendanceAction.sta_comp_id = comp_id;
		EMS_AttendanceAction.sta_cur_day = cur_day;
		EMS_AttendanceAction.sta_user_id = user_id;
	}
	
	public void finalize() throws Throwable {

	}
	
	public static String getSta_comp_id() {
		return sta_comp_id;
	}

	public static void setSta_comp_id(String staCompId) {
		sta_comp_id = staCompId;
	}

	public static String getSta_cur_day() {
		return sta_cur_day;
	}

	public static void setSta_cur_day(String staCurDay) {
		sta_cur_day = staCurDay;
	}

	public static String getSta_user_id() {
		return sta_user_id;
	}

	public static void setSta_user_id(String staUserId) {
		sta_user_id = staUserId;
	}

	public boolean isCommit_flag() {
		return commit_flag;
	}

	public void setCommit_flag(boolean commitFlag) {
		commit_flag = commitFlag;
	}
	
	public static String getCur_employee_id() {
		return cur_employee_id;
	}

	public static void setCur_employee_id(String curEmployeeId) {
		cur_employee_id = curEmployeeId;
	}
	
	
	public static Map getWorkSchedule() {
		return WorkSchedule;
	}

	public static void setWorkSchedule(Map workSchedule) {
		WorkSchedule = workSchedule;
	}

	public static boolean isToday_FK() {
		return today_FK;
	}

	public static void setToday_FK(boolean todayFK) {
		today_FK = todayFK;
	}

	/**
	 * 讓外部呼叫處理考勤資料的方法
	 * @param conn
	 * @param AdminFlag
	 * @param testID
	 * @return
	 */
	public Map execute_Attendance(  boolean AdminFlag, String testID){
		
		return this.process( AdminFlag, testID);
	}
	
	private Map process( boolean AdminFlag, String testID ){
		
		Map classMap = null;	//公司班別資料Map
		Connection conn2 = null;
		
		try {
			conn2 = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			//重置
			this.InAtt_flag = false;
			this.EndAtt_flag = false;
			
			//預先刪除考勤資料刪除, 避免離職人員的考勤資料未被刪除
			if(AdminFlag){
				//是管理者執行才處理
				if(!"".equals(testID) && testID != null){
					try{
						//有設定測試ID, 只需要刪除測試ID的考勤資料
						this.delAllAttData(conn2, testID, "");
					}catch(Exception e){
						e.printStackTrace();
						throw new Exception("刪除指定人員："+testID+" 考勤資料時，發生錯誤!!" + e.toString());
					}
				}else{
					try{
						//未設定測試ID, 刪除全部考勤資料
						this.delAllAttData(conn2, "", "");
					}catch(Exception e){
						e.printStackTrace();
						throw new Exception("刪除全部考勤資料時，發生錯誤!!" + e.toString());
					}
				}
			}
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			//建立HR處理元件
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			boolean ov_flag = false;	//判斷是否有下班後加班
			Map<String, String> ov_map = null;
			Map NotWorkDay= new HashMap<String, String>();
			
			//找出員工班別序號
			String sql = "" +
						 " SELECT EHF010100T0_01, EHF010100T8_04 FROM EHF010100T0 " +
						 " LEFT JOIN EHF010100T1 ON EHF010100T1_01 = EHF010100T0_01 " +
						 " LEFT JOIN EHF010100T8 ON EHF010100T8_03 = EHF010100T0_01 " +
						 " WHERE 1=1 " +
						 " AND EHF010100T1_02 IN ('1','3') AND EHF010100T1_04 = '0' "+	//員工在職才執行
						 " AND EHF010100T0.HR_CompanySysNo = '"+EMS_AttendanceAction.sta_comp_id+"' " ;
						 //" AND EHF010100T0_01='EMP201512070005'   " +
						 
						 
			if(!"".equals(testID) && testID != null){
				//有設定測試ID, 只需要產生測試ID的考勤資料
				sql += " AND EHF010100T0_01='"+testID+"'";
			}					
			
			sql += " ORDER BY EHF010100T0_01 ";
			
			Statement stmt = conn2.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				//設定當前考勤資料的員工工號
				EMS_AttendanceAction.cur_employee_id = rs.getString("EHF010100T0_01");
				
				//判斷員工必須要有感應卡資料才可產生考勤資料 
				if(!this.chkEmpCardExist(conn2, EMS_AttendanceAction.cur_employee_id, EMS_AttendanceAction.sta_comp_id, EMS_AttendanceAction.sta_cur_day)){
					//感應卡片不存在, 不處理此筆考勤資料產生
					continue;
				}
				
				NotWorkDay	= new HashMap<String, String>();
				classMap 	= new HashMap();
				
				/*
				if("EMP201607190015".equals(EMS_AttendanceAction.cur_employee_id)){
					System.out.println(EMS_AttendanceAction.cur_employee_id);
				}
				*/
				
				NotWorkDay = (Map) WorkSchedule.get(EMS_AttendanceAction.cur_employee_id);
				
				//假日Flag
				boolean isHoliday = true;
				int choose=0;
				
				if(NotWorkDay==null){
					//表示要上班,但   是使用預設班別
					isHoliday = true;
					choose=1;
				}else if(NotWorkDay.get(EMS_AttendanceAction.sta_cur_day)==null){
					//表示要上班,但   是使用預設班別
					isHoliday = true;
					choose=2;
				}else if(NotWorkDay.get(EMS_AttendanceAction.sta_cur_day).equals("-1")){
					//表示假日
					isHoliday = false;
					choose=3;
				}else{
					//表示要上班，且有指定班別
					isHoliday = true;
					choose=4;
				}
				
				EMS_AttendanceAction.isHoliday_FK = !isHoliday;
				
				switch(choose){
				
				case 0:
					//表示異常
					break;
					
				case 1:
					//表示要上班,但   是使用預設班別
					classMap = hr_tools.getEmpClassByNo( conn2, EMS_AttendanceAction.sta_comp_id, rs.getInt("EHF010100T8_04"));
					break;
				case 2:
					//表示要上班,但   是使用預設班別
					classMap = hr_tools.getEmpClassByNo( conn2, EMS_AttendanceAction.sta_comp_id, rs.getInt("EHF010100T8_04"));
					break;
				case 3:
					//表示放假
					classMap = hr_tools.getEmpClassByNo( conn2, EMS_AttendanceAction.sta_comp_id, rs.getInt("EHF010100T8_04"));
					break;
				case 4:
					//表示要上班,且有指定班別
					classMap = hr_tools.getEmpClassByNo( conn2, EMS_AttendanceAction.sta_comp_id, Integer.parseInt((String)NotWorkDay.get(EMS_AttendanceAction.sta_cur_day)));
					break;
					
				}
				//是否時薪班別
				if("1".equals((String)classMap.get("PAY_BY_HOUR"))){
					
					this.generateInAttDataHOUR(conn2, EMS_AttendanceAction.cur_employee_id, classMap, tools.hasNoonRecord(classMap));
					
					this.generateEndAttDataHOUR(conn2, EMS_AttendanceAction.cur_employee_id, classMap);
					
				}else{
								
				if(!classMap.isEmpty()){
					//取得班別下班時間
					Calendar class_out_cal = this.getCalendarByClass(conn2, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, EMS_AttendanceAction.sta_comp_id, 
												  classMap, 2);  //班別設定的下班時間(Calendar)
					
					ov_map = ems_ovatt_handle.findOvertimeData(
							 conn2,
							 EMS_AttendanceAction.sta_cur_day, 
							 EMS_AttendanceAction.cur_employee_id, 
							 this.CalendarToString(class_out_cal), 
							 EMS_AttendanceAction.sta_comp_id);
					
					ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
				}else{
					classMap.put("WORK_CLASS_NO",-9999);
					classMap.put("SIESTA_FLAG", false);  //是否記錄中午打卡
				}
				
				//檢核人員考勤資料
				//檢核程式
				//產生人員考勤資料
				//產生程式
				this.generateAttendanceData(
						conn2, 
						EMS_AttendanceAction.cur_employee_id, 
						(Integer)classMap.get("WORK_CLASS_NO"), 
						this.chkAttendance(
								conn2, 
								(Integer)classMap.get("WORK_CLASS_NO"), 
								EMS_AttendanceAction.cur_employee_id, 
								tools.hasNoonRecord(classMap), 
								ov_flag,								
								AdminFlag, 
								classMap,
								isHoliday), 
						classMap, 
						AdminFlag);
				}
				
			}
			
			rs.close();
			stmt.close();
			
			ems_ovatt_handle.close();
			hr_tools.close();
			
			conn2.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if (conn2 != null && !conn2.isClosed()) {
					conn2.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return null;
	}
	
	/**
	 * 產生考勤資料(依據考勤儉核結果做判斷)
	 * @param conn
	 * @param employee_id
	 * @param class_no
	 * @param TYPE
	 * @return
	 */
	private boolean generateAttendanceData( Connection conn, String employee_id, int class_no, int TYPE, Map classMap, boolean AdminFlag){
		
		try{
			
			if(AdminFlag){
				//刪除該日期所有考勤資料
				this.delAttData(conn, employee_id, true, 0 );
			}
			
			switch(TYPE){
				
			case 0:
				//--> 有上班,下班,上午下班,下午上班,加班上班,加班下班
				break;
				
			case 1:
				//--> 有上班,下班,上午下班,下午上班,加班上班
				//需處理加班下班的考勤資料
				if(tools.hasOverTime(classMap)){  //判斷班別是否有設定加班
					this.generateOverTimeOutData(conn, employee_id, classMap);
				}
				break;
				
			case 2:
				// TYPE == 2 , --> 有上班,上午下班,下午上班,下班
				//若該班別有設定加班,需處理加班的考勤資料
				if(tools.hasOverTime(classMap)){  //判斷班別是否有設定加班
					this.generateOverTimeData(conn, employee_id, classMap);  //產生加班考勤資料
				}
				break;
				
			case 3:
				// TYPE == 3 , --> 有上班,上午下班,下午上班
				//需處理下班考勤資料
				if(tools.hasNoonRecord(classMap)){  //判斷班別是否有設定記錄中午打卡
					this.generateOutAttDataWN(conn, employee_id, classMap);  //產生下班考勤資料(有中午記錄)
				}else{
					this.generateEndAttData(conn, employee_id, classMap);  //產生下班考勤資料(沒有中午記錄)
				}
				
				break;
				
			case 4:
				// TYPE == 4 , --> 有上班,上午下班
				//需處理下午上班考勤資料
				if(tools.hasNoonRecord(classMap)){  //判斷班別是否有設定記錄中午打卡
					this.generateSiestaOutAttData(conn, employee_id, classMap);  //產生下午上班考勤資料
				}
				break;
				
			case 5:
				// TYPE == 5 , --> 有上班
				//需處理上午下班考勤資料
				if(tools.hasNoonRecord(classMap)){  //判斷班別是否有設定記錄中午打卡
					this.generateSiestaInAttData(conn, employee_id, classMap);  //產生上午下班考勤資料
				}else{
					this.generateEndAttData(conn, employee_id, classMap);  //產生下班考勤資料(沒有中午記錄)
				}
				break;
				
			case 6:
				// TYPE == 6 , --> 皆沒有
				
				//if(this.isHoliday(conn, this.sta_cur_day, this.sta_comp_id)){
					//處理假日加班
					//this.generateHolidayOverTimeStartAttData( conn, employee_id, classMap, tools.hasNoonRecord(classMap) );  //產生考勤資料
				//}else{
					//需處理上班考勤資料
					this.generateAllAttData(conn, employee_id, classMap);  //產生考勤資料
				//}
				
				
				break;
				
			case 7:
				//TYPE == 7 , --> 處理不休假加班考勤資料
				//因為不休假加班跟一般的考勤與加班不一樣, 所以獨立特殊處理				
				//執行假日加班產生的判斷程式
				this.generateHaOverTimeData(conn, employee_id, classMap);				
				
				break;
				
			default:
				//未有符合的類型需要處理
					
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("依據考勤檢核結果做判斷，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return true;
	}
	
	

	/**
	 * 產生上班,下班考勤資料(沒有加班情況)
	 * @param conn
	 * @param employee_id
	 * @param class_no
	 */
	private void generateAllAttData( Connection conn, String employee_id, Map classMap){
		
		try{
			if(today_FK){			
				//只產生早上考勤
				this.generateInAttDataWN_today( conn, employee_id, classMap, tools.hasNoonRecord(classMap) );
			}else{
				//產生考勤資料(有考慮中午需要記錄打卡的情況)
				this.generateInAttDataWN( conn, employee_id, classMap, tools.hasNoonRecord(classMap) );
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 產生上班考勤資料
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 * @param siesta_flag
	 */
	private void generateInAttDataWN(Connection conn, String employee_id, Map classMap, boolean siesta_flag ){
		
		String sql = "";
		
		try{
			Calendar class_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
															EMS_AttendanceAction.sta_comp_id, classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
																   EMS_AttendanceAction.sta_comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
																	EMS_AttendanceAction.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
															 EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_in_cal));
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			
			//複製上班時間
			Calendar range_class_in_cal = (Calendar)class_in_cal.clone();
			
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_in_cal.add(Calendar.SECOND, 59);
			
			//是否有提前加班狀況發生
			boolean before_overtime_flag = false;
			Map<String, String> before_ov_map = null;
			//使用加班處理元件來取得提前加班的資訊
			before_ov_map = ems_ovatt_handle.findBeforeOvertimeData(
							conn,
						    EMS_AttendanceAction.sta_cur_day, employee_id, this.CalendarToString(class_in_cal), EMS_AttendanceAction.sta_comp_id);
			before_overtime_flag = Boolean.parseBoolean(before_ov_map.get("ADVANCE_OVERTIME_SWITCH"));
			
			Calendar before_class_in_cal = null;
			//列出門禁資料的第一筆當作上班考勤資料
			//上班可擷取時間
			int att_limit_start_hour = (Integer.parseInt(tools.getSysParam(conn, EMS_AttendanceAction.sta_comp_id, "ATT_LIMIT_START_HOUR")));
			
			//String door_type = tools.getSysParam(conn, this.sta_comp_id, "DOOR_IN");	//參數1
			
			//有提前加班情況
			if(before_overtime_flag){
				
				Calendar class_before_ov_in_cal = null;
				
				//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (提前加班)
				class_before_ov_in_cal = tools.datetimeToCalendar(before_ov_map.get("BEFORE_OVERTIME_START")); //加班單設定的提前加班開始時間(Calendar)
				
				before_class_in_cal = (Calendar) class_before_ov_in_cal.clone();
				
				//取得系統參數 資料擷取的開始時間
				//依據系統參數設定提前加班開始的可擷取的門禁資料時間
				//上班時間往前三個小時
				before_class_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_start_hour);
				
				//正常情況  -- 有提前加班
				sql += this.getAttSelectSQL("IN_TIME",
											"IN_CAL_DATE",
											"IN_CAL_TIME",
											" <= '"+this.CalendarToString(range_class_in_cal)+"' ",
									        " >= '"+this.CalendarToString(before_class_in_cal)+"' ",
									        employee_id,
									        EMS_AttendanceAction.sta_comp_id,
									        " ORDER BY EHF020404T0_06 ",
									        1,card_number,"");
				
			}else{
				
				before_class_in_cal = (Calendar) class_in_cal.clone();
				//取得系統參數 資料擷取的開始時間
				//依據系統參數設定上班開始的可擷取的門禁資料時間
				before_class_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_start_hour);
				
				//正常情況 -- 沒有提前加班
				sql += this.getAttSelectSQL("IN_TIME",
											"IN_CAL_DATE",
											"IN_CAL_TIME", 
											" <= '"+this.CalendarToString(range_class_in_cal)+"' ",
											" >= '"+this.CalendarToString(before_class_in_cal)+"' ",
											employee_id,
											EMS_AttendanceAction.sta_comp_id, 
											" ORDER BY EHF020404T0_06 ",
											1,card_number,"");
				
			}
			
			//有中午打卡記錄的情況
			if(siesta_flag){
				
				//異常情況
				  sql += " UNION ";
				  sql += this.getAttSelectSQL("IN_TIME",
											  "IN_CAL_DATE",
											  "IN_CAL_TIME",  
											  " > '"+this.CalendarToString(range_class_in_cal)+"' ",
											  " < '"+this.CalendarToString(class_siesta_in_cal)+"' ",
											  employee_id,
											  EMS_AttendanceAction.sta_comp_id,
											  " ORDER BY EHF020404T0_06 ",
											  1,card_number,"");
				
			}else{
				//未設定需要記錄中午打卡
				//異常情況
				  sql += " UNION ";
				  sql += this.getAttSelectSQL("IN_TIME",
						  					  "IN_CAL_DATE",
						  					  "IN_CAL_TIME",  
						  					  " > '"+this.CalendarToString(range_class_in_cal)+"' ",
						  					  " < '"+this.CalendarToString(class_out_cal)+"' ",
						  					  employee_id,
						  					  EMS_AttendanceAction.sta_comp_id,
						  					  " ORDER BY EHF020404T0_06 ",
						  					  1,card_number,"");
				
			}
			
			//因為EMS產品套裝功能調整 -> 上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			boolean flex_switch = false;
			int class_in_flex_sec = 0;	//上班彈性時間
			int flex_diff_sec = 0;	//上班遲到時間
			Map<String, String> abatt_map = null;
			
			//使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", EMS_AttendanceAction.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			flex_switch = Boolean.parseBoolean(abatt_map.get("DELAY_FLEX_FLAG"));  //上班彈性時間開關
			class_in_flex_sec = tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"));  //上班彈性時間
		
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//取得資料來源   "1->自動匯入 (預設)2->手動匯入"
				String EHF020404T0_10=rs.getString("EHF020404T0_10");
				
				//封裝門禁資料
				EMS_AttendanceForm attform = this.packAttData(rs);
				
				//依據班別上班時間判斷是否遲到
				//int in_sec = this.TimeToSecs(rs.getString("IN_TIME"));  //第一筆門禁打卡時間(以秒計)
				Calendar in_cal = this.datetimeToCalendar(rs.getString("IN_CAL_DATE"), rs.getString("IN_CAL_TIME"));  //第一筆門禁打卡時間(Calendar)
				
				//siesta_flag = true ==> 判斷所抓取的考勤資料是否已超過上午下班時間, 若已超過設定為上班未打卡
				//siesta_flag = false ==> 判斷所抓取的考勤資料是否已超過下班時間, 若已超過設定為上班未打卡
				if( siesta_flag==true?(this.chkTimeIsOK(conn, in_cal, classMap, 7)):(this.chkTimeIsOK(conn, in_cal, classMap, 2)) ){
					//表示抓取的資料已超過上午下班時間, 所以資料不能當作上班時間,使用上班未打卡處理
					
					//上班未打卡
					this.InsertUnATTData(conn, 1, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
					
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id, 
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "1", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
									 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal ) 
									   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id,4,"");
					
				}else{
					//表示抓取的資料沒有超過下班時間, 可以當作上班時間
					
					if(!flex_switch){
						//沒有設定上班彈性時間
						if( in_cal.compareTo(range_class_in_cal) <= 0 ){
							
							//EMS產品功能調整加班考勤的Mapping方式, 若有提前加班狀況發生
							//只有正常上班的狀況下才有加班情況, 因為上班遲到不可能有提前加班的刷卡記錄
							//在正常上班的狀況要額外判斷提前加班是否需要進行處理, 並產生提前加班的加班出勤記錄(EHF020802T0)
							//有提前加班情況
							if(before_overtime_flag){
								//結算提前加班
								ems_ovatt_handle.generateBeforeOvertimeAttendanceRecord(
								conn,
								employee_id, before_ov_map.get("OV_RECORD_UID"), 
								EMS_AttendanceAction.sta_cur_day, 
								before_ov_map.get("BEFORE_OVERTIME_START"), 
								this.CalendarToString(class_in_cal), 
								EMS_AttendanceAction.sta_comp_id);
							}
							
							//正常上班
							this.InsertATTData(conn, 1, false, 1, flex_switch, flex_diff_sec, classMap, attform);
							
							this.InAtt_flag = true;
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,1,"");
							
						}else if( in_cal.after(range_class_in_cal) && in_cal.before(class_siesta_in_cal) ){
							
							flex_diff_sec = this.CaculateLateSecs( 1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 1, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,2,attform.getEHF020404T0_06());
							
						}else if( !siesta_flag && in_cal.after(range_class_in_cal) && in_cal.before(class_out_cal) ){
							
							//未記錄中午打卡
							flex_diff_sec = this.CaculateLateSecs( 1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 1, true, 2, flex_switch, flex_diff_sec, classMap, attform);
								
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,2,attform.getEHF020404T0_06());
							
						}else{
							//上班未打卡
							this.InsertUnATTData(conn, 1, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "1", 
							//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
							siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
									 		 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
									 		   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,4,"");
							
						}
						
					}else{
						//有設定上班彈性時間
						Calendar flex_class_in_cal = (Calendar)class_in_cal.clone();
						flex_class_in_cal.add(Calendar.SECOND, class_in_flex_sec+59);  //為當前Calendar加上彈性的秒數
						
						if( in_cal.compareTo(flex_class_in_cal) <= 0 ){
							//EMS產品功能調整加班考勤的Mapping方式, 若有提前加班狀況發生
							//只有正常上班的狀況下才有加班情況, 因為上班遲到不可能有提前加班的刷卡記錄
							//在正常上班的狀況要額外判斷提前加班是否需要進行處理, 並產生提前加班的加班出勤記錄(EHF020802T0)
							//有提前加班情況
							if(before_overtime_flag){
								//結算提前加班
								ems_ovatt_handle.generateBeforeOvertimeAttendanceRecord(
								conn,
								employee_id, before_ov_map.get("OV_RECORD_UID"), 
								EMS_AttendanceAction.sta_cur_day, 
								before_ov_map.get("BEFORE_OVERTIME_START"), 
								this.CalendarToString(class_in_cal), 
								EMS_AttendanceAction.sta_comp_id);
							}
							
							flex_diff_sec = tools.MilliSecsToSecs(in_cal.getTimeInMillis() - class_in_cal.getTimeInMillis());  //彈性使用時間
							//正常上班
							this.InsertATTData(conn, 1, false, 1, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,1,"");
							
						}else if ( in_cal.after(flex_class_in_cal) && in_cal.before(class_siesta_in_cal) ){
							
							flex_diff_sec = this.CaculateLateSecs(1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 1, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,2,attform.getEHF020404T0_06());
							
						}else if( !siesta_flag && in_cal.after(flex_class_in_cal) && in_cal.before(class_out_cal) ){
							//未記錄中午打卡
							flex_diff_sec = this.CaculateLateSecs(1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 1, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,2,attform.getEHF020404T0_06());
							
						}else{
							
							//上班未打卡
							this.InsertUnATTData(conn, 1, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "1", 
							//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
							siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
											 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
											   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,4,"");
							
						}						
						
					}
					
				}
				
			}else{				
				//上班未打卡
				this.InsertUnATTData(conn, 1, 4, classMap, employee_id,"");
				
				//建立出勤記錄
				ems_abatt_handle.putAttendanceRecord(
				conn,
				employee_id,
				String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
				EMS_AttendanceAction.sta_cur_day, "1", 
				//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
				siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
						 	     :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
						 	       - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
				abatt_map, 
				EMS_AttendanceAction.sta_comp_id,4,"");
				
			}
			
			rs.close();
			stmt.close();
			
			if(siesta_flag){
				//呼叫產生上午下班考勤資料(沒有加班情況)
				this.generateSiestaInAttData(conn, employee_id, classMap);
			}else{
				//呼叫產生下班考勤資料(沒有加班情況)
				this.generateEndAttData( conn, employee_id, classMap);
			}
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(sql);
			try {
				throw new Exception("產生上班考勤資料，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	/**
	 * 產生上午下班考勤資料
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 */
	private void generateSiestaInAttData( Connection conn, String employee_id, Map classMap){
		
		Map userMap = null;
		String attKey = "";
		
		try{
			//取得上班考勤資訊
			userMap = this.getUserInAttendanceTime(conn, employee_id, 1, classMap);
			
			if( userMap != null ){
				attKey = (String) userMap.get("ATT_KEY");
			}
			
			Calendar class_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
										 EMS_AttendanceAction.sta_comp_id, classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
												EMS_AttendanceAction.sta_comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
												 EMS_AttendanceAction.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			Calendar range_class_siesta_in_cal = (Calendar)class_siesta_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_siesta_in_cal.add(Calendar.SECOND, 59); 
			
			Calendar range_class_in_cal = (Calendar)class_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_in_cal.add(Calendar.SECOND, 59); 
			
			Calendar range_class_siesta_out_cal = (Calendar)class_siesta_out_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_siesta_out_cal.add(Calendar.SECOND, 59);
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_siesta_in_cal));
			
			//列出門禁資料的最接近上午下班的資料當作上午下班考勤資料
			String sql = "" ;
			//考勤正常狀況
			sql += this.getAttSelectSQL("SIESTA_IN_TIME",
								        "SIESTA_IN_CAL_DATE",
								        "SIESTA_IN_CAL_TIME",
								        " >= '"+this.CalendarToString(class_siesta_in_cal)+"' ",
								        " < '"+this.CalendarToString(class_siesta_out_cal)+"' ",
								        employee_id,
								        EMS_AttendanceAction.sta_comp_id,
								        " ORDER BY EHF020404T0_06 ",
								        1,card_number,"");
			
			sql +=" UNION ";
			//考勤異常狀況
			sql += this.getAttSelectSQL("SIESTA_IN_TIME",
			         					"SIESTA_IN_CAL_DATE",
			         					"SIESTA_IN_CAL_TIME",
			         					" < '"+this.CalendarToString(class_siesta_in_cal)+"' ",
			         					" > '"+this.CalendarToString(range_class_in_cal)+"' ",
			         					employee_id,
			         					EMS_AttendanceAction.sta_comp_id,
			         					" ORDER BY EHF020404T0_06 DESC ",
			         					1,card_number,"");
			
			//因為EMS產品套裝功能調整 -> 上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			boolean flex_switch = false;
			//int class_in_flex_sec = 0;
			int flex_diff_sec = 0;
			Map<String, String> abatt_map = null;
			
			//使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", EMS_AttendanceAction.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			flex_switch = false;  //上午下班彈性時間開關
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//判斷取得的上午下班資料是否是 ==> '正常'的下午上班資料
				//若是, 則上午下班資料需執行一次rs.next() 取得異常資料, 避免造成下午上資料取得錯誤
				//若不是, 則使用當前資料
				
				//檢核門禁資料是否是上班考勤資料, 若是表示中午下班未打卡
				if( this.chkNoonDataConflict(conn, range_class_siesta_in_cal, range_class_siesta_out_cal, employee_id, rs,card_number) 
				    && this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs)){
					
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					
					//依據班別中午下班時間判斷是否早退
					Calendar out_cal = this.datetimeToCalendar(rs.getString("SIESTA_IN_CAL_DATE"),
							  									rs.getString("SIESTA_IN_CAL_TIME"));  //中午下班打卡時間(Calendar)
					if( out_cal.compareTo(class_siesta_in_cal) >= 0 ){
						
						//正常中午下班                                  
						this.InsertATTData(conn, 7, false, 1, flex_switch, flex_diff_sec, classMap, attform);
						
						//建立出勤記錄
						ems_abatt_handle.putAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "7", 
						flex_diff_sec, 
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id,1,"");
						
					}else if( out_cal.before(class_siesta_in_cal) && out_cal.after(class_in_cal) ){
						
						flex_diff_sec = this.CaculateLateSecs(2, out_cal, class_siesta_in_cal);  //中午下班早退時間
						//中午下班早退
						this.InsertATTData(conn, 7, true, 3, flex_switch, flex_diff_sec, classMap, attform);
						
						//建立出勤記錄
						ems_abatt_handle.putAttendanceRecord(
					    conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "7", 
						flex_diff_sec, 
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id,3,attform.getEHF020404T0_06());
						
					}
					
					
				}else{
					//上午下班未打卡
					this.InsertUnATTData(conn, 7, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
					
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "7", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal )),
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id,4,"");
					
				}
				
			}else{
				//上午下班未打卡
				this.InsertUnATTData(conn, 7, 4, classMap, employee_id,"");
				
				//建立出勤記錄
				ems_abatt_handle.putAttendanceRecord(
				conn,
				employee_id,
				String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
				EMS_AttendanceAction.sta_cur_day, "7", 
				//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
				(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal )),
				abatt_map, 
				EMS_AttendanceAction.sta_comp_id,4,"");
				
			}
			
			rs.close();
			stmt.close();
			
			//呼叫產生下午上班考勤資料(沒有加班情況)
			this.generateSiestaOutAttData(conn, employee_id, classMap);
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生上午下班考勤資料，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
	}
	
	/**
	 * 產生下午上班考勤資料
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 */
	private void generateSiestaOutAttData( Connection conn, String employee_id, Map classMap){
		
		Map userMap = null;
		String attKey = "";
		
		try{
			//取得上午下班考勤資訊
			userMap = this.getUserInAttendanceTime(conn, employee_id, 7, classMap);
			
			if( userMap != null ){
				attKey = (String) userMap.get("ATT_KEY");
			}
			
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
												EMS_AttendanceAction.sta_comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
												 EMS_AttendanceAction.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
										  EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)

			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)			
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
						
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			Calendar range_class_siesta_in_cal = (Calendar)class_siesta_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_siesta_in_cal.add(Calendar.SECOND, 59); 
			
			Calendar range_class_siesta_out_cal = (Calendar)class_siesta_out_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_siesta_out_cal.add(Calendar.SECOND, 59);
			
			//列出門禁資料的最接近下午上班的資料當作下午上班考勤資料
			String sql = "" ;
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_siesta_out_cal));
			
			//考勤正常狀況
			sql += this.getAttSelectSQL("SIESTA_OUT_TIME",
 										"SIESTA_OUT_CAL_DATE",
 										"SIESTA_OUT_CAL_TIME",
 										" <= '"+this.CalendarToString(range_class_siesta_out_cal)+"' ",
 										" > '"+this.CalendarToString(range_class_siesta_in_cal)+"' ",
 										employee_id,
 										EMS_AttendanceAction.sta_comp_id,
 										" ORDER BY EHF020404T0_06 DESC ",
 										1,card_number,"");
			
			sql +=" UNION ";
			//考勤異常狀況
			sql += this.getAttSelectSQL("SIESTA_OUT_TIME",
						 				"SIESTA_OUT_CAL_DATE",
						 				"SIESTA_OUT_CAL_TIME",
						 				" > '"+this.CalendarToString(range_class_siesta_out_cal)+"' ",
						 				" < '"+this.CalendarToString(class_out_cal)+"' ",
						 				employee_id,
						 				EMS_AttendanceAction.sta_comp_id,
						 				" ORDER BY EHF020404T0_06 ",
						 				1,card_number,"");
			
			//因為EMS產品套裝功能調整 -> 上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			boolean flex_switch = false;
			//int class_in_flex_sec = 0;
			int flex_diff_sec = 0;
			Map<String, String> abatt_map = null;
			
			//使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", EMS_AttendanceAction.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			flex_switch = false;  //下午上班彈性時間開關
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//檢核門禁資料是否是上午下班考勤資料, 若是表示下午上班未打卡
				if(this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs)){
					
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					
					//依據班別下午上班時間判斷是否遲到
					Calendar in_cal = this.datetimeToCalendar(rs.getString("SIESTA_OUT_CAL_DATE"),
															  rs.getString("SIESTA_OUT_CAL_TIME"));  //下午上班打卡時間(Calendar)
					
					if( in_cal.compareTo(range_class_siesta_out_cal) <= 0 ){
						
						//正常下午上班                                  
						this.InsertATTData(conn, 8, false, 1, flex_switch, flex_diff_sec, classMap, attform);
						
						//建立出勤記錄
						ems_abatt_handle.putAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "8", 
						flex_diff_sec, 
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id,1,"");
						
					}else if( in_cal.after(range_class_siesta_out_cal) && in_cal.before(class_out_cal) ){
						
						flex_diff_sec = this.CaculateLateSecs(1, in_cal, class_siesta_out_cal );  //下午上班遲到時間
						//下午上班遲到
						this.InsertATTData(conn, 8, true, 2, flex_switch, flex_diff_sec, classMap, attform);
						
						//建立出勤記錄
						ems_abatt_handle.putAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "8", 
						flex_diff_sec, 
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id,2,attform.getEHF020404T0_06());
						
					}
					
				}else{
					//下午上班未打卡
					this.InsertUnATTData(conn, 8, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
					
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "8", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal )),
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id,4,"");
					
				}
				
			}else{
				//下午上班未打卡
				this.InsertUnATTData(conn, 8, 4, classMap, employee_id,"");
				
				//建立出勤記錄
				ems_abatt_handle.putAttendanceRecord(
				conn,
				employee_id,
				String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
				EMS_AttendanceAction.sta_cur_day, "8", 
				//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
				(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal )),
				abatt_map, 
				EMS_AttendanceAction.sta_comp_id,4,"");
			}
			
			rs.close();
			stmt.close();
			
			//呼叫產生下班考勤資料(沒有加班情況)
			this.generateOutAttDataWN(conn, employee_id, classMap);			
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();			
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生下午上班考勤資料，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 產生下班考勤資料(有中午打卡記錄)
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 */
	private void generateOutAttDataWN( Connection conn, String employee_id, Map classMap){
		
		Map userMap = null;
		Map Gotowork = null;
		String attKey = "";
		
		try{
			//取得下午上班考勤資訊
			userMap = this.getUserInAttendanceTime(conn, employee_id, 8, classMap);
			//取得上班考勤資料
			Gotowork= this.getUserInAttendanceTime(conn, employee_id, 1, classMap);
			if( userMap != null ){
				attKey = (String) userMap.get("ATT_KEY");
			}
			
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
												 EMS_AttendanceAction.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
										  EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
			
			Calendar class_ov_in_cal = null;  //班別設定的加班上班時間(Calendar)
			Calendar class_ov_out_cal = null;  //班別設定的加班下班時間(Calendar)
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			
			//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (下班後加班)
			boolean ov_flag = false;
			Map<String, String> ov_map = null;
			
			ov_map = ems_ovatt_handle.findOvertimeData(
					 conn,
					 EMS_AttendanceAction.sta_cur_day, 
					 employee_id, 
					 this.CalendarToString(class_out_cal), 
					 EMS_AttendanceAction.sta_comp_id);
			
			ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
			if(ov_flag){
				class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
				class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
			}
			
			//取得系統參數 資料擷取的結束時間
			//依據系統參數設定下班的可擷取的門禁資料時間
			int att_limit_end_hour = (Integer.parseInt(tools.getSysParam(conn, EMS_AttendanceAction.sta_comp_id, "ATT_LIMIT_END_HOUR")));
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
			//延後下班在新的模式採用加班記錄做認定, 所以不用取得延後下班的設定資訊
			//直接以加班單認定做處理
			//boolean late_ov_flag = false;
			//直接以加班單認定做處理
			//late_ov_flag = false;
			
			Calendar late_class_out_cal = (Calendar) class_out_cal.clone();
			
			Calendar after_class_out_cal = (Calendar) late_class_out_cal.clone();
			//將延後下班時間的後四個小時作為可擷取的門禁資料
			after_class_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_end_hour);
			
			Calendar range_class_siesta_out_cal = (Calendar)class_siesta_out_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_siesta_out_cal.add(Calendar.SECOND, 59);
			
			Calendar range_class_out_cal = (Calendar)class_out_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_out_cal.add(Calendar.SECOND, 59);
			
			Calendar range_class_ov_in_cal = null;
			if( class_ov_in_cal != null ){
				range_class_ov_in_cal = (Calendar)class_ov_in_cal.clone();
				//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
				//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
				range_class_ov_in_cal.add(Calendar.SECOND, 59);
			}
			
			Calendar after_class_ov_out_cal = null;
			if( class_ov_out_cal != null ){
				after_class_ov_out_cal = (Calendar) class_ov_out_cal.clone();
				//將加班下班時間的後一個小時作為可擷取的門禁資料
				after_class_ov_out_cal.add(Calendar.HOUR_OF_DAY, 1);
			}
			
			//列出門禁資料的最接近下班的資料當作下班考勤資料
			String sql = "";
			
			//正常情況 - 未延後下班
			String sqlcond = "";
			String sqlordercond = "";
			String card_number_END_time = "";
			
			//判斷是否有加班
			if( ov_flag ){
				//有設定加班
				if(late_class_out_cal.compareTo(class_ov_in_cal) <= 0){
					//延後下班時間小於等於加班上班時間
					sqlcond += " < '"+this.CalendarToString(late_class_out_cal)+"' ";  //小於延後下班時間
					card_number_END_time+=this.CalendarToString(late_class_out_cal);
					sqlordercond = " ORDER BY EHF020404T0_06 ";
				}else if(late_class_out_cal.compareTo(class_ov_in_cal) > 0 ){
					//延後下班時間大於等於加班上班時間, 目前程式不允許此設定
					//記錄延後下班時間大於加班上班時間的錯誤
					System.out.println("系統不允許延後下班時間大於加班上班時間的設定, 請重新設定班別資料!!"+employee_id);
				}
			}else{
				//沒有設定加班
				sqlcond += " <= '"+this.CalendarToString(after_class_out_cal)+"' ";  //小於等於延後下班時間後四個小時
				card_number_END_time+=this.CalendarToString(after_class_out_cal);
				sqlordercond = " ORDER BY EHF020404T0_06 DESC ";
			}
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_out_cal));
			
			sql += this.getAttSelectSQL("OUT_TIME",
						"OUT_CAL_DATE",
						"OUT_CAL_TIME",
						" >= '"+this.CalendarToString(class_out_cal)+"' ",  //大於等於下班時間
						sqlcond,
						employee_id,
						EMS_AttendanceAction.sta_comp_id,
						sqlordercond,
						1,card_number,"");
			
			//---------------------------延後下班處理SQL--------------------------START--------------------------------
			//正常情況 - 延後下班
			  sql += " UNION ";
			  sql += " SELECT * FROM ( " +
			  		 " SELECT " +
			  		 " IFNULL(EHF020404T0_01, '') AS EHF020404T0_01, " +
			  		 " IFNULL(EHF020404T0_02, '') AS EHF020404T0_02, " +
			  		 " IFNULL(EHF020404T0_03, '') AS EHF020404T0_03, " +
			  		 " IFNULL(EHF020404T0_04, '') AS EHF020404T0_04, " +
			  		 " IFNULL(EHF020404T0_05, '') AS EHF020404T0_05, " +
			  		 " IFNULL(EHF020404T0_06, '') AS EHF020404T0_06, " +
			  		 " IFNULL(EHF020404T0_07, '') AS EHF020404T0_07, " +
			  		 " IFNULL(EHF020404T0_08, '') AS EHF020404T0_08, " +
			  		 " IFNULL(EHF020404T0_09, '') AS EHF020404T0_09, " +
			  		 " IFNULL(EHF020404T0_10, '') AS EHF020404T0_10, " +
			  		 " IFNULL((EHF020404T0_06), '') AS LIMIT_VALUE, ";
			  		 
			  		 /*
					 if( this.hasOverTime(classMap) ){
					  	//有設定加班
					  	sql += " IFNULL(MIN(EHF020404T0_06), '') AS LIMIT_VALUE, ";
					 }else{
					  	//沒有設定加班
					  	sql += " IFNULL(MAX(EHF020404T0_06), '') AS LIMIT_VALUE, ";
					 }
					 */
			  		 
					  sql += " IFNULL(DATE_FORMAT(EHF020404T0_06, '%H%i'), '') AS OUT_TIME, " +
							 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%Y/%m/%d'), '') AS OUT_CAL_DATE, " +
							 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%H:%i:%s'), '') AS OUT_CAL_TIME " +
							 " FROM EHF020404T0 " +
							 " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020404T0_03 AND EHF020403T1_06 = EHF020404T0_09 " +
							 " WHERE 1=1 " +
							 " AND EHF020404T0_06 IS NOT NULL ";
							 if( ov_flag ){
								//有設定加班
								 //判斷延後下班時間是否等於加班上班時間
								 if(late_class_out_cal.equals(class_ov_in_cal)){
									//檢查是否有實際加班資料
									if( this.chkRealOvertimeExist( conn, class_out_cal, after_class_ov_out_cal, employee_id, 
											EMS_AttendanceAction.sta_comp_id, 2 )){ 
										//有加班資料, 表示沒有延後下班
										sql += " AND EHF020404T0_06 >= '"+this.CalendarToString(late_class_out_cal)+"' " +  //大於等於延後下班時間
					 					   	   " AND EHF020404T0_06 <= '"+this.CalendarToString(class_ov_in_cal)+"' ";  //小於加班上班時間
									}else{
										//沒有加班資料, 表示有延後下班
										sql += " AND EHF020404T0_06 >= '"+this.CalendarToString(late_class_out_cal)+"' " +  //大於等於延後下班時間
						 					   " AND EHF020404T0_06 <= '"+this.CalendarToString(after_class_out_cal)+"' ";  //小於等於延後下班時間後四個小時
									}
								 }else{
									 sql += " AND EHF020404T0_06 >= '"+this.CalendarToString(late_class_out_cal)+"' " +  //大於等於延後下班時間
							 				" AND EHF020404T0_06 < '"+this.CalendarToString(class_ov_in_cal)+"' ";  //小於加班上班時間
								 }
							 }else{
								//沒有設定加班
								 sql += " AND EHF020404T0_06 >= '"+this.CalendarToString(late_class_out_cal)+"' " +  //大於等於延後下班時間
								 		" AND EHF020404T0_06 <= '"+this.CalendarToString(after_class_out_cal)+"' ";  //小於等於延後下班時間後四個小時
							 }
					  sql += " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
//							 " AND EHF020404T0_04 = '"+this.sta_cur_day+"' " +  //計算考勤的日期
							 " AND EHF020404T0_09 = '"+EMS_AttendanceAction.sta_comp_id+"' " ;  //公司代碼
							 if( ov_flag ){
								 //有設定加班
								 sql += " ORDER BY EHF020404T0_06 " ;
							 }else{
								 //沒有設定加班
								 sql += " ORDER BY EHF020404T0_06 DESC " ;
							  }
					  sql += " LIMIT 1 " +
							 " ) tableA " +
							 " WHERE 1=1 " +
							 " AND tableA.EHF020404T0_06 <> '' ";
					  
			//異常情況 - 下班早退
			sql += " UNION ";
			sql += this.getAttSelectSQL("OUT_TIME",
										"OUT_CAL_DATE",
										"OUT_CAL_TIME",
										" < '"+this.CalendarToString(class_out_cal)+"' ",
										" > '"+this.CalendarToString(range_class_siesta_out_cal)+"' ",
										employee_id,
										EMS_AttendanceAction.sta_comp_id,
										" ORDER BY EHF020404T0_06 DESC ",
										1,card_number,"");
			
			//因為EMS產品套裝功能調整 -> 下班早退的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			boolean flex_switch = false;
			boolean late_time_switch = false;//是否補足遲到時數開關
			//int class_in_flex_sec = 0;
			int flex_diff_sec = 0;  //下班早退秒數
			//int class_out_flex_sec = 0;  //下班彈性時間
			Map<String, String> abatt_map = null;
			
			// 使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", EMS_AttendanceAction.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);//取得遲到早退曠職元件
			flex_switch = false; // 下班彈性時間開關
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//檢查晚上考勤資料是否有衝突
				boolean conflict_flag = this.chkNightDataConflict( conn, range_class_out_cal, range_class_ov_in_cal, after_class_ov_out_cal,
																   employee_id, rs, tools.hasOverTime(classMap),card_number );
				
				boolean samekey_flag = this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs);
				
				//檢核門禁資料是否是下午上班考勤資料, 若是表示下班未打卡
				if( conflict_flag && samekey_flag ){
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					
					//依據班別下班時間判斷是否早退
					Calendar out_cal = this.datetimeToCalendar(rs.getString("OUT_CAL_DATE"),
															  rs.getString("OUT_CAL_TIME"));  //下班打卡時間(Calendar)
					
					//因為EMS產品套裝功能調整 -> 下班早退的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
					int used_flex_sec = 0;  //取得使用者使用的上班彈性時間
					
					used_flex_sec = 0;
					late_time_switch = Boolean.parseBoolean(abatt_map.get("RECORD_COME_LATE_TIME"));;  //是否補足遲到時數開關
					
					if(late_time_switch){
						//要補足遲到數
						if(Gotowork!=null){
							int late_time=(Integer) Gotowork.get("FLEX_SEC");//取得當日上班彈性時間
							if(late_time>0&&late_time<=tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"))){
								//上班彈性時間>0&&上班彈性時間<=可遲到的秒數
								class_out_cal.add(Calendar.SECOND, late_time);
							}
							else if(late_time<=0){
								//無遲到
								class_out_cal.add(Calendar.SECOND, -(tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"))));
							}
							else if(late_time>tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"))){
								//上班彈性時間大於可遲到時間
								class_out_cal.add(Calendar.SECOND, -(tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"))));
							}
						}
					}
					else{
						//不補足時數
						//可使用早退時數，不管有無遲到
						//班別設定下班時間減"可早退時間"
						class_out_cal.add(Calendar.SECOND, -(tools.MinuteToSecs(abatt_map.get("LEAVE_EARLY_FLEX_MINUTE"))));
					}
					
					//判斷是否有使用上班彈性時間
					if( used_flex_sec > 0 && used_flex_sec <= tools.MinuteToSecs( (String) classMap.get("START_FLEXIBLE_TIME") ) ){
						//有使用上班彈性時間
						class_out_cal.add(Calendar.SECOND, used_flex_sec);  //為當前下班Calendar加上彈性的秒數
						
						if( out_cal.compareTo(class_out_cal) >= 0 ){
							
							//正常下班                                  
							this.InsertATTData(conn, 2, false, 1, flex_switch, flex_diff_sec, classMap, attform);
														
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,1,"");							
							
						}else if( out_cal.before(class_out_cal) && out_cal.after(class_siesta_out_cal) ){
							
							flex_diff_sec = this.CaculateLateSecs(2, out_cal, class_out_cal );  //下班早退時間
							//下班早退
							this.InsertATTData(conn, 2, true, 3, flex_switch, flex_diff_sec, classMap, attform);
														
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,3,attform.getEHF020404T0_06());							
							
						}
						
					}else{
						//未使用上班彈性時間
						if( out_cal.compareTo(class_out_cal) >= 0 ){
							
							//正常下班                                  
							this.InsertATTData(conn, 2, false, 1, flex_switch, flex_diff_sec, classMap, attform);
														
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,1,"");							
							
						}else if( out_cal.before(class_out_cal) && out_cal.after(class_siesta_out_cal) ){
							
							flex_diff_sec = this.CaculateLateSecs(2, out_cal, class_out_cal );  //下班早退時間
							//下班早退
							this.InsertATTData(conn, 2, true, 3, flex_switch, flex_diff_sec, classMap, attform);
														
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,3,attform.getEHF020404T0_06());							
							
						}
						
					}
					
					//有下班打卡資料, 不論正常或是異常,才可產生加班資料
					//加班考勤產生程式
					//----------------------------------------------
					//this.generateOverTimeData(conn, employee_id, classMap);
					
				}else{
					//判斷是否有設定加班
					if(ov_flag){
						if(!samekey_flag){
							conflict_flag = false;
						}
						//若有設定加班則需要考慮, 是否實際有加班但是下班未打卡, 避免因為有加班但是下班未打卡而沒有產生加班資料
						this.chkOvertimeNeed(conn, class_ov_in_cal, after_class_ov_out_cal, employee_id, classMap, conflict_flag,rs.getString("EHF020404T0_01"));
					}else{
						//若無設定加班則不需要特殊考慮
						//下班未打卡
						this.InsertUnATTData(conn, 2, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));						
						
						//建立出勤記錄
						ems_abatt_handle.putAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "2", 
						//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
						(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal )),
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id,4,"");						
						
					}
				}								
				
			}else{
				//判斷是否有設定加班
				if(ov_flag){
					//若有設定加班則需要考慮, 是否實際有加班但是下班未打卡, 避免因為有加班但是下班未打卡而沒有產生加班資料
					this.chkOvertimeNeed(conn, class_ov_in_cal, after_class_ov_out_cal, employee_id, classMap, false,"");
				}else{
					//若無設定加班則不需要特殊考慮
					//下班未打卡
					this.InsertUnATTData(conn, 2, 4, classMap, employee_id,"");
					
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "2", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal )),
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id,4,"");
					
				}				
			}
			
			rs.close();
			stmt.close();
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生下班考勤資料(有中午打卡記錄)，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 產生下班考勤資料(沒有加班情況)
	 * @param conn
	 * @param employee_id
	 * @param class_no
	 */
	private void generateEndAttData( Connection conn, String employee_id, Map classMap){
		
		Map userMap = null;
		String attKey = "";
		
		try{
			//增加判斷, 若加班設定中的'是否記錄下班與加班上班', 若設定為不記錄, 則需要跳過下班資料與加班上班資料產生的處理
			//但前提是需確定有實際加班的資料, 若沒有實際加班資料則是依原本流程繼續產生下班資料
			if(this.handleOVTRecordProcess(conn, classMap, employee_id, EMS_AttendanceAction.sta_comp_id )){
				//呼叫產生加班下班考勤資料
				this.generateOverTimeOutData(conn, employee_id, classMap);
				
				//中斷後續程式的執行
				return;
			}
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();															
			
			//取得上班考勤資訊
			userMap = this.getUserInAttendanceTime(conn, employee_id, 1, classMap);
			
			if( userMap != null ){
				attKey = (String) userMap.get("ATT_KEY");
			}
			
			Calendar class_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
												 		    EMS_AttendanceAction.sta_comp_id, classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
																   EMS_AttendanceAction.sta_comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
																    EMS_AttendanceAction.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
														     EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
			
			Calendar class_ov_in_cal = null;  //班別設定的加班上班時間(Calendar)
			Calendar class_ov_out_cal = null;  //班別設定的加班下班時間(Calendar)
			
			//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (下班後加班)
			boolean ov_flag = false;	//判斷班別是否有設定加班
			Map<String, String> ov_map = null;
			
			ov_map = ems_ovatt_handle.findOvertimeData(
					 conn,
					 EMS_AttendanceAction.sta_cur_day, 
					 employee_id, 
					 this.CalendarToString(class_out_cal), 
					 EMS_AttendanceAction.sta_comp_id);
			
			ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
			if(ov_flag){
				class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
				class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
			}
			
			//取得系統參數 資料擷取的結束時間
			//依據系統參數設定下班的可擷取的門禁資料時間
			int att_limit_end_hour = (Integer.parseInt(tools.getSysParam(conn, EMS_AttendanceAction.sta_comp_id, "ATT_LIMIT_END_HOUR")));
			
			//取得系統參數 資料擷取的結束時間
			//依據系統參數設定加班下班的可擷取的門禁資料時間
			int att_limit_ov_end_hour = (Integer.parseInt(tools.getSysParam(conn, EMS_AttendanceAction.sta_comp_id, "ATT_LIMIT_OV_END_HOUR")));
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
			//延後下班在新的模式採用加班記錄做認定, 所以不用取得延後下班的設定資訊
			//直接以加班單認定做處理
			//boolean late_ov_flag = false;
			
			Calendar late_class_out_cal = (Calendar) class_out_cal.clone();
			
			Calendar after_class_out_cal = (Calendar) late_class_out_cal.clone();
			//依據系統參數設定延後下班的可擷取的門禁資料時間
			after_class_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_end_hour);
			
			/*Calendar after_class_out_cal = (Calendar) late_class_out_cal.clone();
			//依據系統參數設定延後下班的可擷取的門禁資料時間
			after_class_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_end_hour);*/
			
			Calendar range_class_in_cal = (Calendar)class_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_in_cal.add(Calendar.SECOND, 59);
			
			Calendar range_class_out_cal = (Calendar)class_out_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_out_cal.add(Calendar.SECOND, 59);
			
			Calendar range_class_ov_in_cal = null;
			
			if( class_ov_in_cal != null ){
				range_class_ov_in_cal = (Calendar)class_ov_in_cal.clone();
				//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
				//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
				range_class_ov_in_cal.add(Calendar.SECOND, 59);
			}
			
			//班別設定的加班下班時間(Calendar)
			Calendar after_class_ov_out_cal = null;
			if( class_ov_out_cal != null ){
				after_class_ov_out_cal = (Calendar) class_ov_out_cal.clone();
				//將加班下班時間的後一個小時作為可擷取的門禁資料
				after_class_ov_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_ov_end_hour);
			}									
			
			//列出門禁資料的最後一筆當作下班考勤資料
			String sql = "";
			
			//String door_type = tools.getSysParam(conn, this.sta_comp_id, "DOOR_OUT");	//參數2
			
			//正常情況 - 未延後下班
			String sqlcond = "";
			String sqlordercond = "";
			
			//沒有設定加班
//			 sqlcond = " <= '"+this.CalendarToString(after_class_out_cal)+"' ";  //小於等於後一天上班時間
//			 sqlordercond = " ORDER BY EHF020404T0_06 DESC ";
			//判斷是否有加班
			if( ov_flag ){
				//有設定加班
				if(late_class_out_cal.compareTo(class_ov_in_cal) <= 0){
					//延後下班時間小於等於加班上班時間
					sqlcond = " < '"+this.CalendarToString(late_class_out_cal)+"' ";  //小於延後下班時間
					sqlordercond = " ORDER BY EHF020404T0_06 ";
				}else if(late_class_out_cal.compareTo(class_ov_in_cal) > 0 ){
					//延後下班時間大於等於加班上班時間, 目前程式不允許此設定
					//記錄延後下班時間大於加班上班時間的錯誤
					sqlcond = " < '0' AND 1=0 ";  //小於延後下班時間
					sqlordercond = " ORDER BY EHF020404T0_06 ";
					System.out.println("系統不允許延後下班時間大於加班上班時間的設定, 請重新設定班別資料!!"+employee_id);
				}
			 }else{
				//沒有設定加班
				 sqlcond = " <= '"+this.CalendarToString(after_class_out_cal)+"' ";  //小於等於延後下班時間後四個小時
				 sqlordercond = " ORDER BY EHF020404T0_06 DESC ";
			 }
												
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_out_cal));
			
			sql += this.getAttSelectSQL("OUT_TIME",
										"OUT_CAL_DATE",
										"OUT_CAL_TIME",
										" >= '"+this.CalendarToString(class_out_cal)+"' ",  //大於等於下班時間
										sqlcond,
										employee_id,
										EMS_AttendanceAction.sta_comp_id,
										sqlordercond,
										1,card_number,"");
			
			//正常情況 - 延後下班
			sql += " UNION ";
			sql += " SELECT * FROM ( " +
	  		 " SELECT " +
	  		 " IFNULL(EHF020404T0_01, '') AS EHF020404T0_01, " +
	  		 " IFNULL(EHF020404T0_02, '') AS EHF020404T0_02, " +
	  		 " IFNULL(EHF020404T0_03, '') AS EHF020404T0_03, " +
	  		 " IFNULL(EHF020404T0_04, '') AS EHF020404T0_04, " +
	  		 " IFNULL(EHF020404T0_05, '') AS EHF020404T0_05, " +
	  		 " IFNULL(EHF020404T0_06, '') AS EHF020404T0_06, " +
	  		 " IFNULL(EHF020404T0_07, '') AS EHF020404T0_07, " +
	  		 " IFNULL(EHF020404T0_08, '') AS EHF020404T0_08, " +
	  		 " IFNULL(EHF020404T0_09, '') AS EHF020404T0_09, " +
	  		 " IFNULL(EHF020404T0_10, '') AS EHF020404T0_10, " +
			 " IFNULL((EHF020404T0_06), '') AS LIMIT_VALUE, ";
			 /*
			 if( this.hasOverTime(classMap) ){
			  	//有設定加班
			  	sql += " IFNULL(MIN(EHF020404T0_06), '') AS LIMIT_VALUE, ";
			 }else{
			  	//沒有設定加班
			  	sql += " IFNULL(MAX(EHF020404T0_06), '') AS LIMIT_VALUE, ";
			 }
			 */
			  sql += " IFNULL(DATE_FORMAT(EHF020404T0_06, '%H%i'), '') AS OUT_TIME, " +
					 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%Y/%m/%d'), '') AS OUT_CAL_DATE, " +
					 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%H:%i:%s'), '') AS OUT_CAL_TIME " +
					 " FROM EHF020404T0 " +
					 " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020404T0_03 AND EHF020403T1_06 = EHF020404T0_09 " +
					 " WHERE 1=1 " +
					 " AND EHF020404T0_06 IS NOT NULL ";
			 if( ov_flag ){ 
				//判斷延後下班時間是否等於加班上班時間
				 if(late_class_out_cal.equals(class_ov_in_cal)){
					 //檢查是否有實際加班資料
					 if( this.chkRealOvertimeExist( conn, range_class_ov_in_cal, after_class_ov_out_cal, employee_id, EMS_AttendanceAction.sta_comp_id, 1 )){ 
						//有加班資料, 表示沒有延後下班
						sql += " AND EHF020404T0_06 >= '"+this.CalendarToString(late_class_out_cal)+"' " +  //大於等於延後下班時間
		 				   	   " AND EHF020404T0_06 <= '"+this.CalendarToString(class_ov_in_cal)+"' ";  //小於加班上班時間
					 }else{
						//沒有加班資料, 表示有延後下班
						sql += " AND EHF020404T0_06 >= '"+this.CalendarToString(late_class_out_cal)+"' " +  //大於等於延後下班時間
			 				   " AND EHF020404T0_06 <= '"+this.CalendarToString(after_class_out_cal)+"' ";  //小於等於延後下班時間後四個小時
					 }
				 }else{
					 sql += " AND EHF020404T0_06 >= '"+this.CalendarToString(late_class_out_cal)+"' " +  //大於等於延後下班時間
			 				" AND EHF020404T0_06 < '"+this.CalendarToString(class_ov_in_cal)+"' ";  //小於加班上班時間
				 }
			 }else{
				 //沒有設定加班
				 sql += " AND EHF020404T0_06 >= '"+this.CalendarToString(late_class_out_cal)+"' " +  //大於等於延後下班時間
						" AND EHF020404T0_06 <= '"+this.CalendarToString(after_class_out_cal)+"' ";  //小於等於延後下班時間後四個小時
			}
			  sql += " AND EHF020404T0_03 IN ("+card_number+")" +  //員工工號
//					 " AND EHF020404T0_04 = '"+this.sta_cur_day+"' " +  //計算考勤的日期
					 " AND EHF020404T0_09 = '"+EMS_AttendanceAction.sta_comp_id+"' " ;  //公司代碼
					 if( ov_flag ){
					  	//有設定加班
						sql += " ORDER BY EHF020404T0_06 " ;
					 }else{
						//沒有設定加班
						sql += " ORDER BY EHF020404T0_06 DESC " ;
					 }
			  sql += "" +		 
					 " LIMIT 1 " +
					 " ) tableA " +
					 " WHERE 1=1 " +
					 " AND tableA.EHF020404T0_06 <> '' ";
									
			//異常情況 - 下班早退
			sql += " UNION ";
			sql += this.getAttSelectSQL("OUT_TIME",
										"OUT_CAL_DATE",
										"OUT_CAL_TIME",
										" < '"+this.CalendarToString(class_out_cal)+"' ",
										" > '"+this.CalendarToString(range_class_in_cal)+"' ",
										employee_id,
										EMS_AttendanceAction.sta_comp_id,
										" ORDER BY EHF020404T0_06 DESC ",
										1,card_number,"");
			
			// 因為EMS產品套裝功能調整 -> 下班早退的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			boolean flex_switch = false;
			boolean late_time_switch = false;//是否補足遲到時數開關
			//int class_in_flex_sec = 0;
			int flex_diff_sec = 0; // 下班早退秒數
			//int class_out_flex_sec = 0; // 下班彈性時間
			Map<String, String> abatt_map = null;
			
			// 使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", EMS_AttendanceAction.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			flex_switch = false; // 下班彈性時間開關
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			if(rs.next()){
				
				//檢查晚上考勤資料是否衝突
				boolean conflict_flag = this.chkNightDataConflict( conn, range_class_out_cal, range_class_ov_in_cal, after_class_ov_out_cal,
						   										   employee_id, rs, tools.hasOverTime(classMap) ,card_number);
				boolean samekey_flag = this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs);

				//檢核門禁資料是否是上班考勤資料, 若是表示下班未打卡
				if( conflict_flag && samekey_flag){
					
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					//依據班別下班時間判斷是否早退
					Calendar out_cal = this.datetimeToCalendar(rs.getString("OUT_CAL_DATE"),
							  								   rs.getString("OUT_CAL_TIME"));  //下班打卡時間(Calendar)
					
					//因為EMS產品套裝功能調整 -> 下班早退的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
					int used_flex_sec = 0;  //取得使用者使用的上班彈性時間
					
					//使用彈性時間不需要做回補的動作
					used_flex_sec = 0;
					late_time_switch = Boolean.parseBoolean(abatt_map.get("RECORD_COME_LATE_TIME"));;  //是否補足遲到時數開關
					
					if(late_time_switch){
						//要補足遲到數
						if(userMap!=null){
							int late_time=(Integer) userMap.get("FLEX_SEC");//取得當日上班彈性時間
							if(late_time>0&&late_time<=tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"))){
								//上班彈性時間>0&&上班彈性時間<=可遲到的秒數
								class_out_cal.add(Calendar.SECOND, late_time);
							}else if(late_time<=0){
								class_out_cal.add(Calendar.SECOND, -(tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"))));
							}else if(late_time>tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"))){
								//上班彈性時間大於可遲到時間
								class_out_cal.add(Calendar.SECOND, -(tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"))));
							}
						}
					}
					else{
						//不補足時數
						//可使用早退時數，不管有無遲到
						//班別設定下班時間減"可早退時間"
						class_out_cal.add(Calendar.SECOND, -(tools.MinuteToSecs(abatt_map.get("LEAVE_EARLY_FLEX_MINUTE"))));
					}
					
					//判斷是否有使用上班彈性時間
					if( used_flex_sec > 0 && used_flex_sec <= tools.MinuteToSecs( (String) classMap.get("START_FLEXIBLE_TIME") ) ){
						//有使用上班彈性時間
						class_out_cal.add(Calendar.SECOND, used_flex_sec);  //為當前下班Calendar加上彈性的秒數
						//目前下班彈性時間使用秒數做判斷~
						if( out_cal.compareTo(class_out_cal) >= 0 ){
							
							//正常下班                                  
							this.InsertATTData(conn, 2, false, 1, flex_switch, flex_diff_sec, classMap, attform);
														
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,1,"");							
							
						}else if( out_cal.before(class_out_cal) && out_cal.after(class_in_cal) ){
						
							flex_diff_sec = this.CaculateLateSecs(2, out_cal, class_out_cal );  //下班早退時間
							//下班早退
							this.InsertATTData(conn, 2, true, 3, flex_switch, flex_diff_sec, classMap, attform);
														
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,3,attform.getEHF020404T0_06());							
							
						}
						
					}else{
						//未使用上班彈性時間
						if( out_cal.compareTo(class_out_cal) >= 0 ){
							
							//正常下班                                  
							this.InsertATTData(conn, 2, false, 1, flex_switch, flex_diff_sec, classMap, attform);
							
							this.EndAtt_flag = true;
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,1,"");
							
						}else if( out_cal.before(class_out_cal) && out_cal.after(class_in_cal) ){
							
							flex_diff_sec = this.CaculateLateSecs(2, out_cal, class_out_cal );  //下班早退時間
							//下班早退
							this.InsertATTData(conn, 2, true, 3, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,3,attform.getEHF020404T0_06());
							
						}
					}
					
					//有下班打卡資料, 不論正常或是異常,才可產生加班資料
					//加班考勤產生程式
					//----------------------------------------------
					this.generateOverTimeData(conn, employee_id, classMap);
					
				}else{
					
					//判斷是否有設定加班
					if(ov_flag){
						if(!samekey_flag){
							conflict_flag = false;
						}
						//若有設定加班則需要考慮, 是否實際有加班但是下班未打卡, 避免因為有加班但是下班未打卡而沒有產生加班資料
						this.chkOvertimeNeed(conn, class_ov_in_cal, after_class_ov_out_cal, employee_id, classMap, conflict_flag ,rs.getString("EHF020404T0_01"));
					}else{
						//若無設定加班則不需要特殊考慮
						//下班未打卡
						this.InsertUnATTData(conn, 2, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
						
						//建立出勤記錄
						ems_abatt_handle.putAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "2", 
						//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
						(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
						 - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id,4,"");
						
					}
					
				}
					
			}else{
				//判斷是否有設定加班
				if(ov_flag){
					//若有設定加班則需要考慮, 是否實際有加班但是下班未打卡, 避免因為有加班但是下班未打卡而沒有產生加班資料
					this.chkOvertimeNeed(conn, class_ov_in_cal, after_class_ov_out_cal, employee_id, classMap, false ,"");
				}else{
					//若無設定加班則不需要特殊考慮
					//下班未打卡
					this.InsertUnATTData(conn, 2, 4, classMap, employee_id,"");
					
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "2", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
					 - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id,4,"");
					
				}

			}
			rs.close();
			stmt.close();

			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生下班考勤資料，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 產生加班考勤資料(平日)
	 * @param employee_id
	 * @param class_no
	 */
	public void generateOverTimeData( Connection conn, String employee_id, Map classMap){
		
		try{
			Calendar class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
										  EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			
			boolean ov_flag = false;
			Map<String, String> ov_map = null;
			
			ov_map = ems_ovatt_handle.findOvertimeData(
					 conn,
					 EMS_AttendanceAction.sta_cur_day, 
					 employee_id, 
					 this.CalendarToString(class_out_cal), 
					 EMS_AttendanceAction.sta_comp_id);
			
			ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
			
			//判斷班別是否有設定加班 && 有實際的加班門禁資料
			if( ov_flag && this.chkRealOvertimeExist(conn, classMap, employee_id, EMS_AttendanceAction.sta_comp_id) ){
				//有設定加班才執行 --> 加班考勤產生程式
				this.generateOverTimeInData(conn, employee_id, classMap, false);
			}
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
			//有設定加班才執行 --> 加班考勤產生程式
			//this.generateOverTimeInData(conn, employee_id, classMap, false);			
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
		
	/**
	 * 產生假日加班考勤資料
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 */
	public void generateHaOverTimeData( Connection conn, String employee_id, Map classMap){
		
		try{
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			
			boolean ha_ov_flag = false;
			Map<String, String> ha_ov_map = null;
			
			ha_ov_map = ems_ovatt_handle.findHaOvertimeData(
					conn,
				 	EMS_AttendanceAction.sta_cur_day, 
				 	employee_id,
				 	EMS_AttendanceAction.sta_comp_id);
		
			ha_ov_flag = Boolean.parseBoolean(ha_ov_map.get("HA_OVERTIME_SWITCH"));
			
			//判斷是否有設定假日加班 && 有實際的假日加班門禁資料
			if( ha_ov_flag && this.chkRealHaOvertimeExist(conn, classMap, employee_id, EMS_AttendanceAction.sta_comp_id) ){
				//有設定假日加班才執行 --> 加班考勤產生程式
				this.generateOverTimeInData(conn, employee_id, classMap, false);
			}
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 產生加班上班考勤資料(平日)
	 * @param employee_id
	 * @param class_no
	 */
	private void generateOverTimeInData( Connection conn, String employee_id, Map classMap, boolean break_flag ){
		
		Map userMap = null;
		Map Gotowork = null;
		String attKey = "";
		
		try{
			//取得上班考勤資料
			Gotowork= this.getUserInAttendanceTime(conn, employee_id, 1, classMap);
			//取得下班考勤資訊
			userMap = this.getUserInAttendanceTime(conn, employee_id, 2, classMap);
			
			if( userMap != null ){
				attKey = (String) userMap.get("ATT_KEY");
			}
			
			Calendar class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
															 EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
			
			Calendar class_ov_in_cal = null;  //班別設定的加班上班時間(Calendar)
			Calendar class_ov_out_cal = null;  //班別設定的加班下班時間(Calendar)
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			
			boolean ov_flag = false;	//判斷班別是否有設定加班
			Map<String, String> ov_map = null;
			
			//取得考勤日期是否為假日資訊
			boolean ha_flag = this.isHoliday(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.sta_comp_id, 
					(String)classMap.get("WORK_CLASS_HOLIDAY"));
			
			//下班後加班與假日加班的功能同時處理
			//判斷是否為假日
			if(ha_flag){
				//處理假日加班
				ov_map = ems_ovatt_handle.findHaOvertimeData(
						 conn,
					 	 EMS_AttendanceAction.sta_cur_day, 
					 	 employee_id,
					 	 EMS_AttendanceAction.sta_comp_id);
			
				ov_flag = Boolean.parseBoolean(ov_map.get("HA_OVERTIME_SWITCH"));
				if(ov_flag){
					class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("HA_OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
					class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("HA_OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
				}
			}else{
				//處理下班後加班
				ov_map = ems_ovatt_handle.findOvertimeData(
						 conn,
						 EMS_AttendanceAction.sta_cur_day, 
						 employee_id, 
						 this.CalendarToString(class_out_cal), 
						 EMS_AttendanceAction.sta_comp_id);
				
				ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
				if(ov_flag){
					class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
					class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
				}
			}
			
			//取得系統參數 資料擷取的結束時間
			//依據系統參數設定加班下班的可擷取的門禁資料時間
			int att_limit_ov_end_hour = (Integer.parseInt(tools.getSysParam(conn, EMS_AttendanceAction.sta_comp_id, "ATT_LIMIT_OV_END_HOUR")));
			
			Calendar before_class_ov_in_cal = null;
			Calendar range_class_out_cal = null;
			
			if(ha_flag){
				if(ov_flag){
					before_class_ov_in_cal = (Calendar) class_ov_in_cal.clone();
					//將加班上班時間的前一個小時作為可擷取的門禁資料
					before_class_ov_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_ov_end_hour);
					
					//做為假日加班擷取資料的邊界
					range_class_out_cal = (Calendar)before_class_ov_in_cal.clone();
					
				}else{
					range_class_out_cal = (Calendar)class_out_cal.clone();
					//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
					//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
					range_class_out_cal.add(Calendar.SECOND, 59);
				}
			}else{
				range_class_out_cal = (Calendar)class_out_cal.clone();
				//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
				//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
				range_class_out_cal.add(Calendar.SECOND, 59);
			}
			
			Calendar range_class_ov_in_cal = (Calendar)class_ov_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_ov_in_cal.add(Calendar.SECOND, 59);
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_ov_in_cal));
			
			//列出門禁資料最接近加班上班時間的當作加班上班考勤資料
			String sql = "";
			//正常情況
			sql += this.getAttSelectSQL("OV_IN_TIME",
					"OV_IN_CAL_DATE",
					"OV_IN_CAL_TIME",
					" > '"+this.CalendarToString(range_class_out_cal)+"' ",
					" <= '"+this.CalendarToString(range_class_ov_in_cal)+"' ",
					employee_id,
					EMS_AttendanceAction.sta_comp_id,
					" ORDER BY EHF020404T0_06 DESC ",
					1,card_number,"");
			
			//判斷加班是否有休息
			if(break_flag){
				//異常情況 - 加班上班遲到
				  sql += " UNION ";
				  sql += this.getAttSelectSQL("OV_IN_TIME",
											  "OV_IN_CAL_DATE",
											  "OV_IN_CAL_TIME",
											  " > '"+this.CalendarToString(range_class_ov_in_cal)+"' ",
											  " = EHF020404T0_06 ",
											  employee_id,
											  EMS_AttendanceAction.sta_comp_id,
											  " ORDER BY EHF020404T0_06 ",
											  1,card_number,"");
			}else{
				  //異常情況 - 加班上班遲到
				  sql += " UNION ";
				  sql += this.getAttSelectSQL("OV_IN_TIME",
						  					  "OV_IN_CAL_DATE",
						  					  "OV_IN_CAL_TIME",
						  					  " > '"+this.CalendarToString(range_class_ov_in_cal)+"' ",
						  					  " < '"+this.CalendarToString(class_ov_out_cal)+"' ",
						  					  employee_id,
						  					  EMS_AttendanceAction.sta_comp_id,
						  					  " ORDER BY EHF020404T0_06 ",
						  					  1,card_number,"");
			
			}
			
			// 因為EMS產品套裝功能調整 -> 加班上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			boolean flex_switch = false;
			int flex_diff_sec = 0; // 加班上班遲到秒數
			Map<String, String> abatt_map = null;
			
			// 使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", EMS_AttendanceAction.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			flex_switch = false; // 加班上班彈性時間開關
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//檢核門禁資料是否是下班考勤資料, 若是表示加班上班未打卡
				if(this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs)){
					
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					
					//依據班別加班上班時間判斷是否遲到
					Calendar ov_in_cal = this.datetimeToCalendar(rs.getString("OV_IN_CAL_DATE"),
															     rs.getString("OV_IN_CAL_TIME"));  //加班上班打卡時間(Calendar)
					
					//沒有加班上班彈性時間設定
					//boolean flex_switch = false;
					//int flex_diff_sec = 0;
					
					if( ov_in_cal.compareTo(range_class_ov_in_cal) <= 0 ){
						
						//正常加班上班                        
						this.InsertATTData(conn, 5, false, 1, flex_switch, flex_diff_sec, classMap, attform);
												
						//建立出勤記錄
						ems_abatt_handle.putOvertimeAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "5", 
						flex_diff_sec, 
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id);						
						
					}else if( ov_in_cal.after(range_class_ov_in_cal) && ov_in_cal.before(class_ov_out_cal) ){
						
						//加班上班 遲到狀況 
						//需要判斷當前讀取到的加班上班刷卡資料比較接近哪一個時間 ==> 加班上班時間 或是 加班下班時間
						//1. 若是比較接近加班上班時間則當作 加班上班遲到的刷卡時間
						//2. 若是比較接近加班下班時間則當作 加班下班的刷卡時間, 此時加班上班刷卡時間要改為未打卡
						//承2. 要額外判斷 此筆刷卡時間 至 加班下班可擷取時間 沒有另一筆刷卡資料, 才可執行, 否則會造成加班資料錯亂
						
						//刷卡與加班上班差距的秒數 in 絕對值
						int ov_in_diff_sec = this.CaculateLateSecs(0, ov_in_cal, class_ov_in_cal );
						//刷卡與加班下班差距的秒數 in 絕對值
						int ov_out_diff_sec = this.CaculateLateSecs(0, ov_in_cal, class_ov_out_cal );
						
						if(ov_out_diff_sec >= ov_in_diff_sec){
							//表示該筆刷卡資料比較接近加班上班時間							
							flex_diff_sec = this.CaculateLateSecs(1, ov_in_cal, class_ov_in_cal );  //加班上班遲到時間
							//加班上班遲到
							//取消加班上班遲到的異常訊息, 因不符合實際加班考勤用途
							//this.InsertATTData(conn, 5, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							this.InsertATTData(conn, 5, false, 1, flex_switch, flex_diff_sec, classMap, attform);
							
							//加班打卡的異常考勤除了未打卡的狀況, 其餘狀況都不可以算是異常考勤, 不需要顯示異常訊息
							//加班出勤不符合加班記錄所設定之時間區間也不能算是異常出勤							
							//建立出勤記錄
							ems_abatt_handle.putOvertimeAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "5", 
							flex_diff_sec, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id);						
							
						}else{
							//表示該筆刷卡資料比較接近加班下班時間
							//判斷該筆刷卡時間至加班下班時間沒有額外的刷卡資料
							Calendar chk_ov_in_cal = (Calendar) ov_in_cal.clone();
							//為當前Calendar加上01秒, 避免抓取到同一筆刷卡資料
							chk_ov_in_cal.add(Calendar.SECOND, 1);
							
							//加班下班時間
							Calendar after_class_ov_out_cal = (Calendar) class_ov_out_cal.clone();
							
							//將加班下班時間的後一個小時作為可擷取的門禁資料
							after_class_ov_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_ov_end_hour);
							
							if(this.chkRealOvertimeExist(conn, chk_ov_in_cal, after_class_ov_out_cal, employee_id, EMS_AttendanceAction.sta_comp_id, 1)){
								//表示有區間內有抓到刷卡資料, 則此筆刷卡資料必須作為加班上班刷卡								
								flex_diff_sec = this.CaculateLateSecs(1, ov_in_cal, class_ov_in_cal );  //加班上班遲到時間
								//加班上班遲到
								//取消加班上班遲到的異常訊息, 因不符合實際加班考勤用途
								//this.InsertATTData(conn, 5, true, 2, flex_switch, flex_diff_sec, classMap, attform);
								this.InsertATTData(conn, 5, false, 1, flex_switch, flex_diff_sec, classMap, attform);
								
								//加班打卡的異常考勤除了未打卡的狀況, 其餘狀況都不可以算是異常考勤, 不需要顯示異常訊息
								//加班出勤不符合加班記錄所設定之時間區間也不能算是異常出勤								
								//建立出勤記錄
								ems_abatt_handle.putOvertimeAttendanceRecord(
								conn,
								employee_id,
								String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
								EMS_AttendanceAction.sta_cur_day, "5", 
								flex_diff_sec, 
								abatt_map, 
								EMS_AttendanceAction.sta_comp_id);								
								
							}else{
								//表示有區間內沒有抓到刷卡資料, 則此筆刷卡資料作為加班下班刷卡
								//加班上班未打卡
								this.InsertUnATTData(conn, 5, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
								
								//加班出勤若有未打卡則要記錄異常 -> 因為會造成無法計算實際加班時數								
								//建立出勤記錄
								ems_abatt_handle.putOvertimeAttendanceRecord(
								conn,
								employee_id,
								String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
								EMS_AttendanceAction.sta_cur_day, "5", 
								-1, 
								abatt_map, 
								EMS_AttendanceAction.sta_comp_id);								
								
							}							
							
						}											
						
					}
					
				}else{
					//加班上班未打卡
					this.InsertUnATTData(conn, 5, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
					
					//加班出勤若有未打卡則要記錄異常 -> 因為會造成無法計算實際加班時數					
					//建立出勤記錄
					ems_abatt_handle.putOvertimeAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "5", 
					-1, 
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id);					
					
				}
				
			}else{
				//加班上班未打卡
				this.InsertUnATTData(conn, 5, 4, classMap, employee_id,"");
				
				//加班出勤若有未打卡則要記錄異常 -> 因為會造成無法計算實際加班時數				
				//建立出勤記錄
				ems_abatt_handle.putOvertimeAttendanceRecord(
				conn,
				employee_id, 
				String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
				EMS_AttendanceAction.sta_cur_day, "5", 
				-1, 
				abatt_map, 
				EMS_AttendanceAction.sta_comp_id);				
				
			}
			
			rs.close();
			stmt.close();
			
			
			if(break_flag){
				//呼叫產生加班休息(起)考勤資料
				 //目前未實作加班休息, 先留程式結構
				
			}else{
				//呼叫產生加班下班考勤資料
				this.generateOverTimeOutData(conn, employee_id, classMap);
			}
			
			//關閉遲到早退曠職元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();			
			
			/*Calendar class_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
															classMap, 1);  //班別設定的上班時間(Calendar)
			
			Calendar after_class_in_cal = (Calendar) class_out_cal.clone();
			
			int att_limit_start_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_END_HOUR")));
			//取當前日期的後一天上班時間
			after_class_in_cal.add(Calendar.HOUR_OF_DAY, att_limit_start_hour);
			
			Calendar range_class_out_cal = null;
			
			range_class_out_cal = (Calendar)class_out_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_out_cal.add(Calendar.SECOND, 59);
			
			//先取得可用的感應卡號
			String card_number=this.card_number(conn,employee_id,this.CalendarToString(class_out_cal));
			
			String door_type = tools.getSysParam(conn, this.sta_comp_id, "OverTime_DOOR_IN");	//參數3
			
			//列出門禁資料最接近加班上班時間的當作加班上班考勤資料
			String sql = "";
			//正常情況
			sql += this.getAttSelectSQL("OV_IN_TIME",
										"OV_IN_CAL_DATE",
										"OV_IN_CAL_TIME",
										" > '"+this.CalendarToString(range_class_out_cal)+"' ",
										" < '"+this.CalendarToString(after_class_in_cal)+"' ",
										employee_id,
										this.sta_comp_id,
										" ORDER BY EHF020404T0_06 ",	//由小往大
										1,card_number,door_type);
			
			//判斷加班是否有休息
			if(break_flag){
				//異常情況 - 加班上班遲到
				
			}else{
				//異常情況 - 加班上班遲到
				
			}
			
			// 因為EMS產品套裝功能調整 -> 加班上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			// 使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			boolean flex_switch = false;
			int flex_diff_sec = 0; // 加班上班遲到秒數
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//檢核門禁資料是否是下班考勤資料, 若是表示加班上班未打卡
				if(this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs)){
					
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					
					//正常加班上班(沒有遲到問題)                        
					this.InsertATTData(conn, 5, false, 1, flex_switch, flex_diff_sec, classMap, attform);
					
				}else{
					
					//加班上班未打卡
					this.InsertUnATTData(conn, 5, 4, classMap, employee_id);
					
				}
				
			}else{
				//加班上班未打卡
				this.InsertUnATTData(conn, 5, 4, classMap, employee_id);
				
			}
			
			rs.close();
			stmt.close();
			
			if(break_flag){
				//呼叫產生加班休息(起)考勤資料
				 //目前未實作加班休息, 先留程式結構
				
			}else{
				//呼叫產生加班下班考勤資料
				this.generateOverTimeOutData(conn, employee_id, classMap);
			}*/
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生加班上班考勤資料(平日)，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 產生加班下班考勤資料(平日)
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 */
	private void generateOverTimeOutData( Connection conn, String employee_id, Map classMap ){
		
		Map userMap = null;
		Map Gotowork = null;
		String attKey = "";
		
		try{
			//取得上班考勤資料
			Gotowork= this.getUserInAttendanceTime(conn, employee_id, 1, classMap);	
			//取得加班上班考勤資訊
			userMap = this.getUserInAttendanceTime(conn, employee_id, 5, classMap);
			
			if( userMap != null ){
				attKey = (String) userMap.get("ATT_KEY");
			}
			
			Calendar class_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
															EMS_AttendanceAction.sta_comp_id, classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
																   EMS_AttendanceAction.sta_comp_id, classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
																    EMS_AttendanceAction.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
															 EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
			
			Calendar class_ov_in_cal = null;  //班別設定的加班上班時間(Calendar)
			Calendar class_ov_out_cal = null;  //班別設定的加班下班時間(Calendar)
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)			
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (下班後加班)
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			
			boolean ov_flag = false;
			Map<String, String> ov_map = null;
			
			//取得考勤日期是否為假日資訊
			boolean ha_flag = this.isHoliday(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.sta_comp_id, 
					(String)classMap.get("WORK_CLASS_HOLIDAY"));
			
			//判斷是否為假日
			if(ha_flag){
				//處理假日加班
				ov_map = ems_ovatt_handle.findHaOvertimeData(
						 conn,
					 	 EMS_AttendanceAction.sta_cur_day, 
					 	 employee_id,
					 	 EMS_AttendanceAction.sta_comp_id);
			
				ov_flag = Boolean.parseBoolean(ov_map.get("HA_OVERTIME_SWITCH"));
				if(ov_flag){
					class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("HA_OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
					class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("HA_OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
					
					
				}
			}else{
				//處理下班後加班
				ov_map = ems_ovatt_handle.findOvertimeData(
						 conn,
						 EMS_AttendanceAction.sta_cur_day, 
						 employee_id, 
						 this.CalendarToString(class_out_cal), 
						 EMS_AttendanceAction.sta_comp_id);
				
				ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
				if(ov_flag){
					class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
					class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
				}
			}
			
			//判斷中午上下班是否要打卡
			boolean siesta_flag = tools.hasNoonRecord(classMap);
			
			//取得系統參數 資料擷取的結束時間
			//依據系統參數設定加班下班的可擷取的門禁資料時間
			int att_limit_ov_end_hour = (Integer.parseInt(tools.getSysParam(conn, EMS_AttendanceAction.sta_comp_id, "ATT_LIMIT_OV_END_HOUR")));
			
			Calendar before_class_ov_in_cal = (Calendar) class_ov_in_cal.clone();
			//將加班上班時間的前一個小時作為可擷取的門禁資料
			before_class_ov_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_ov_end_hour);
			
			Calendar after_class_ov_out_cal = (Calendar) class_ov_out_cal.clone();
			//將加班下班時間的後一個小時作為可擷取的門禁資料
			after_class_ov_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_ov_end_hour);
			
			Calendar range_class_ov_in_cal = (Calendar)class_ov_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_ov_in_cal.add(Calendar.SECOND, 59);
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_ov_in_cal));
			
			//列出門禁資料最接近加班下班時間的當作加班下班考勤資料
			String sql = "";
			//正常情況
			sql += this.getAttSelectSQL("OV_OUT_TIME",
										"OV_OUT_CAL_DATE",
										"OV_OUT_CAL_TIME",
										" >= '"+this.CalendarToString(class_ov_out_cal)+"' ",
										" <= '"+this.CalendarToString(after_class_ov_out_cal)+"' ",
										employee_id,
										EMS_AttendanceAction.sta_comp_id,
										" ORDER BY EHF020404T0_06 DESC ",
										1,card_number,"");
			
			//異常情況 - 加班下班早退
			sql += " UNION ";
			sql += this.getAttSelectSQL("OV_OUT_TIME",
										"OV_OUT_CAL_DATE",
										"OV_OUT_CAL_TIME",
										" < '"+this.CalendarToString(class_ov_out_cal)+"' ",
										" > '"+this.CalendarToString(range_class_ov_in_cal)+"' ",
										employee_id,
										EMS_AttendanceAction.sta_comp_id,
										" ORDER BY EHF020404T0_06 DESC ",
										1,card_number,"");
			 
			// 因為EMS產品套裝功能調整 -> 加班下班早退的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			boolean flex_switch = false;
			int flex_diff_sec = 0; //加班下班早退秒數
			Map<String, String> abatt_map = null;
			boolean ovt_record_flag = false; 
			 
			// 使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", EMS_AttendanceAction.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			flex_switch = false; // 加班下班彈性時間開關
			
			//if -> true 要記錄, if -> false 不要記錄, 對面回補程式 ==> 要記錄 ==> 不要回補,  不要記錄 ==> 要回補
			ovt_record_flag = !(Boolean.parseBoolean(abatt_map.get("RECORD_FULL_OVERTIME_ATT_DATA")));  //是否記錄下班與加班上班 
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//檢核門禁資料是否是加班上班考勤資料, 若是表示加班下班未打卡
				if(this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs)){
					
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					
					//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
					//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
					
					//依據班別加班下班時間判斷是否早退
					Calendar ov_out_cal = this.datetimeToCalendar(rs.getString("OV_OUT_CAL_DATE"),
															      rs.getString("OV_OUT_CAL_TIME"));  //加班下班打卡時間(Calendar)
					
					if( ov_out_cal.compareTo(class_ov_out_cal) >= 0 ){
						
						//正常加班下班                                  
						this.InsertATTData(conn, 6, false, 1, flex_switch, flex_diff_sec, classMap, attform);
						
						//建立出勤記錄
						ems_abatt_handle.putOvertimeAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "6", 
						flex_diff_sec, 
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id);
						
						if(ovt_record_flag  && !ha_flag){
							//不要記錄加班上下班才需要回補資料  && 非假日上班才需要回補資料
							//回補下班考勤與出勤資料
							EMS_AttendanceForm tmp_attform = null;
							
							//建立正常下班門禁資料
							//封裝門禁資料
							tmp_attform = new EMS_AttendanceForm();
							
							
							//class_out_cal.add(Calendar.SECOND, (int)(Math.random()*120));
							
							tmp_attform.setEHF020404T0_02(tools.covertDateToString(class_out_cal.getTime(), "yyyyMMddHHmmss")+
														  rs.getString("EHF020404T0_03"));  //門禁系統號
							tmp_attform.setEHF020404T0_04(tools.covertDateToString(class_out_cal.getTime(), "yyyy/MM/dd"));  //打卡日期
							tmp_attform.setEHF020404T0_05(tools.covertDateToString(class_out_cal.getTime(), "HH:mm:ss"));  //打卡時間			
							tmp_attform.setEHF020404T0_06(this.CalendarToString(class_out_cal));  //打卡日期時間(datetime)
							tmp_attform.setEHF020404T0_03(rs.getString("EHF020404T0_03"));  //感應卡號
							tmp_attform.setEHF020404T0_07(rs.getString("EHF020404T0_07"));  //門禁機器代碼
							
							//正常下班                                  
							this.InsertATTData(conn, 2, false, 1, false, 0, classMap, tmp_attform);
							//正常下班出勤							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							0, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,1,"");							
							
							//回補加班上班考勤與出勤資料
							
							//建立正常家班上班門禁資料
							//封裝門禁資料
							tmp_attform = new EMS_AttendanceForm();
							tmp_attform.setEHF020404T0_02(tools.covertDateToString(class_ov_in_cal.getTime(), "yyyyMMddHHmmss")+
														  rs.getString("EHF020404T0_03"));  //門禁系統號
							tmp_attform.setEHF020404T0_04(tools.covertDateToString(class_ov_in_cal.getTime(), "yyyy/MM/dd"));  //打卡日期
							tmp_attform.setEHF020404T0_05(tools.covertDateToString(class_ov_in_cal.getTime(), "HH:mm:ss"));  //打卡時間			
							tmp_attform.setEHF020404T0_06(this.CalendarToString(class_ov_in_cal));  //打卡日期時間(datetime)
							tmp_attform.setEHF020404T0_03(rs.getString("EHF020404T0_03"));  //感應卡號
							tmp_attform.setEHF020404T0_07(rs.getString("EHF020404T0_07"));  //門禁機器代碼
							
							//正常加班上班                        
							this.InsertATTData(conn, 5, false, 1, false, 0, classMap, tmp_attform);
							//正常加班上班出勤							
							//建立出勤記錄
							ems_abatt_handle.putOvertimeAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "5", 
							0, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id);
							
						}
						
					}else if( ov_out_cal.before(class_ov_out_cal) && ov_out_cal.after(class_ov_in_cal) ){
						
						//加班打卡的異常考勤除了未打卡的狀況, 其餘狀況都不可以算是異常考勤, 不需要顯示異常訊息
						
						flex_diff_sec = this.CaculateLateSecs(2, ov_out_cal, class_ov_out_cal );  //加班下班早退時間
						//加班下班早退
						//取消加班下班早退的異常訊息, 因不符合實際加班考勤用途
						//this.InsertATTData(conn, 6, true, 3, flex_switch, flex_diff_sec, classMap, attform);
						this.InsertATTData(conn, 6, false, 1, flex_switch, flex_diff_sec, classMap, attform);
						
						//加班出勤不符合加班記錄所設定之時間區間也不能算是異常出勤
						//建立出勤記錄
						ems_abatt_handle.putOvertimeAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "6", 
						flex_diff_sec, 
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id);
						
						if(ovt_record_flag && !ha_flag){							
							
							//不要記錄加班上下班才需要回補資料  &&  非假日上班才需要回補資料 
							//回補下班考勤與出勤資料
							EMS_AttendanceForm tmp_attform = null;
							
							//建立正常下班門禁資料
							//封裝門禁資料
							tmp_attform = new EMS_AttendanceForm();
							tmp_attform.setEHF020404T0_02(tools.covertDateToString(class_out_cal.getTime(), "yyyyMMddHHmmss")+
														  rs.getString("EHF020404T0_03"));  //門禁系統號
							tmp_attform.setEHF020404T0_04(tools.covertDateToString(class_out_cal.getTime(), "yyyy/MM/dd"));  //打卡日期
							tmp_attform.setEHF020404T0_05(tools.covertDateToString(class_out_cal.getTime(), "HH:mm:ss"));  //打卡時間			
							tmp_attform.setEHF020404T0_06(this.CalendarToString(class_out_cal));  //打卡日期時間(datetime)
							tmp_attform.setEHF020404T0_03(rs.getString("EHF020404T0_03"));  //感應卡號
							tmp_attform.setEHF020404T0_07(rs.getString("EHF020404T0_07"));  //門禁機器代碼
							
							//正常下班                                  
							this.InsertATTData(conn, 2, false, 1, false, 0, classMap, tmp_attform);
							//正常下班出勤							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "2", 
							0, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id,1,"");							
							
							//回補加班上班考勤與出勤資料
							
							//建立正常家班上班門禁資料
							//封裝門禁資料
							tmp_attform = new EMS_AttendanceForm();
							tmp_attform.setEHF020404T0_02(tools.covertDateToString(class_ov_in_cal.getTime(), "yyyyMMddHHmmss")+
														  rs.getString("EHF020404T0_03"));  //門禁系統號
							tmp_attform.setEHF020404T0_04(tools.covertDateToString(class_ov_in_cal.getTime(), "yyyy/MM/dd"));  //打卡日期
							tmp_attform.setEHF020404T0_05(tools.covertDateToString(class_ov_in_cal.getTime(), "HH:mm:ss"));  //打卡時間			
							tmp_attform.setEHF020404T0_06(this.CalendarToString(class_ov_in_cal));  //打卡日期時間(datetime)
							tmp_attform.setEHF020404T0_03(rs.getString("EHF020404T0_03"));  //感應卡號
							tmp_attform.setEHF020404T0_07(rs.getString("EHF020404T0_07"));  //門禁機器代碼
							
							//正常加班上班                        
							this.InsertATTData(conn, 5, false, 1, false, 0, classMap, tmp_attform);
							//正常加班上班出勤							
							//建立出勤記錄
							ems_abatt_handle.putOvertimeAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							EMS_AttendanceAction.sta_cur_day, "5", 
							0, 
							abatt_map, 
							EMS_AttendanceAction.sta_comp_id);
							
						}
						
					}
					
				}else{
					
					if(ovt_record_flag && !ha_flag){
						//要記錄加班上下班時, 若出現加班下班未打卡則必須要回溯未打卡至下班考勤資料  &&  非假日上班才需要回補資料
						//因為未打卡無法判斷是否有實際加班狀況產生 -> 改為產生下班未打卡考勤資料
						
						//下班未打卡
						this.InsertUnATTData(conn, 2, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
						
						//因為禾益佳只要有加班時，正常下班時間(源於：班別設定的下班時間+random)與加班上班時間(源於：加班單上班時間)是假的，所以重新取得班別設定的下班時間
						/*class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
									 EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
						*/						
						//建立出勤記錄
						ems_abatt_handle.putAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "2", 
						//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
						siesta_flag==true?(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal ))
							 	     	 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
							 	     	   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id,4,"");
						
						
					}else{
						//加班下班未打卡
						this.InsertUnATTData(conn, 6, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
						//加班出勤若有未打卡則要記錄異常 -> 因為會造成無法計算實際加班時數
						
						//建立出勤記錄
						ems_abatt_handle.putOvertimeAttendanceRecord(
						conn,
						employee_id,
						String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
						EMS_AttendanceAction.sta_cur_day, "6", 
						-1, 
						abatt_map, 
						EMS_AttendanceAction.sta_comp_id);
						
					}
					
				}
				
			}else{
				
				if(ovt_record_flag && !ha_flag){
					//要記錄加班上下班時, 若出現加班下班未打卡則必須要回溯未打卡至下班考勤資料  &&  非假日上班才需要回補資料
					//因為未打卡無法判斷是否有實際加班狀況產生 -> 改為產生下班未打卡考勤資料
					
					//下班未打卡
					this.InsertUnATTData(conn, 2, 4, classMap, employee_id,"");
					
					//因為禾益佳只要有加班時，正常下班時間(源於：班別設定的下班時間+random)與加班上班時間(源於：加班單上班時間)是假的，所以重新取得班別設定的下班時間
					/*class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
								 EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
					*/					
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "2", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					siesta_flag==true?(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal ))
						 	     	 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
						 	     	   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id,4,"");					
					
				}else{
					//加班下班未打卡
					this.InsertUnATTData(conn, 6, 4, classMap, employee_id,"");
					//加班出勤若有未打卡則要記錄異常 -> 因為會造成無法計算實際加班時數					
					//建立出勤記錄
					ems_abatt_handle.putOvertimeAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "6", 
					-1, 
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id);
					
				}
				
			}
			
			rs.close();
			stmt.close();
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)			
			//進行加班出勤的結算處理, 抓取前段程式所產生的 加班上班考勤與加班下班考勤進行結算處理
			
			//1. 假設 加班上班考勤 或是 加班下班考勤, 若有任一筆是未打卡, 則加班出勤結算為:加班 0小時
			//   不需要產生加班出勤記錄資料(EHF020802T0) = 沒有加班且不會計算加班費用
			
			//2. 假設 加班上班考勤 或是 加班下班考勤 皆有資料時, 則依據實際加班考勤結算加班出勤資料
			//   結算加班上班至加班下班的時數(要以加班基本時數作為單位進行結算,
			//   if 基本時數是 0.5小時, 則結算的加班時數必須要是 0.5小時的倍數)
			//   需要產生加班出勤記錄資料(EHF020802T0) = 有加班且會計算加班費用
			
			//依據以上邏輯結算加班出勤資料
			//判斷是否為假日
			if(ha_flag){
				//假日加班的結算
				//若是假日加班必須用加班上班時間作為結算邊界
				Map<String, String> ov_record_map = 
					ems_ovatt_handle.settlementOvertimeAttendanceRecord(
					conn,
					employee_id, 
					ov_map.get("OV_RECORD_UID"), 
					EMS_AttendanceAction.sta_cur_day, 
					this.CalendarToString(before_class_ov_in_cal), 
					EMS_AttendanceAction.sta_comp_id);
			}else{
				//因為禾益佳只要有加班時，正常下班時間(源於：班別設定的下班時間+random)與加班上班時間(源於：加班單上班時間)是假的，所以重新取得班別設定的下班時間
				/*class_out_cal = this.getCalendarByClass(conn, EMS_AttendanceAction.sta_cur_day, EMS_AttendanceAction.cur_employee_id, 
							 EMS_AttendanceAction.sta_comp_id, classMap, 2);  //班別設定的下班時間(Calendar)
				*/
				//下班後加班的結算
				//若是下班後加班必須用班別下班時間作為結算邊界
				Map<String, String> ov_record_map = 
					ems_ovatt_handle.settlementOvertimeAttendanceRecord(
					conn,
					employee_id, 
					ov_map.get("OV_RECORD_UID"), 
					EMS_AttendanceAction.sta_cur_day, 
					this.CalendarToString(class_out_cal), 
					EMS_AttendanceAction.sta_comp_id);
			}
			
			//關閉遲到早退曠職元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();												
			
			/*Calendar after_class_in_cal = (Calendar) class_out_cal.clone();
			
			
			int att_limit_start_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_END_HOUR")));
			
			
			//取當前日期的後一天上班時間
			after_class_in_cal.add(Calendar.HOUR_OF_DAY, att_limit_start_hour);
			
			Calendar class_ov_in_cal = null;  //班別設定的加班上班時間(Calendar)
			Calendar class_ov_out_cal = null;  //班別設定的加班下班時間(Calendar)
			
			boolean ov_flag = false;	//判斷班別是否有設定加班
			//取得考勤日期是否為假日資訊
			boolean ha_flag = this.isHoliday(conn, this.sta_cur_day, this.sta_comp_id);
			
			//先取得可用的感應卡號
			String card_number=this.card_number(conn,employee_id,this.CalendarToString(after_class_in_cal));
			
			String door_type = tools.getSysParam(conn, this.sta_comp_id, "OverTime_DOOR_OUT");	//參數4			
			
			//列出門禁資料最接近加班下班時間的當作加班下班考勤資料
			String sql = "";
			//正常情況
			sql += this.getAttSelectSQL("OV_OUT_TIME",
										"OV_OUT_CAL_DATE",
										"OV_OUT_CAL_TIME",
										" > '"+this.CalendarToString(class_out_cal)+"' ",
										" < '"+this.CalendarToString(after_class_in_cal)+"' ",
										employee_id,
										this.sta_comp_id,
										" ORDER BY EHF020404T0_06 DESC ",
										1,card_number,door_type);
			
			boolean flex_switch = false;
			int flex_diff_sec = 0; //加班下班早退秒數
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//檢核門禁資料是否是加班上班考勤資料, 若是表示加班下班未打卡
				if(this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs)){
					
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					
					//依據班別加班下班時間判斷是否早退
					Calendar ov_out_cal = this.datetimeToCalendar(rs.getString("OV_OUT_CAL_DATE"),
															      rs.getString("OV_OUT_CAL_TIME"));  //加班下班打卡時間(Calendar)
					
					//正常加班下班                                  
					this.InsertATTData(conn, 6, false, 1, flex_switch, flex_diff_sec, classMap, attform);
					
				}else{
					
					//加班下班未打卡
					this.InsertUnATTData(conn, 6, 4, classMap, employee_id);
					
				}
				
			}else{
				
				//加班下班未打卡
				this.InsertUnATTData(conn, 6, 4, classMap, employee_id);
				
			}
			
			rs.close();
			stmt.close();*/
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生加班下班考勤資料(平日)，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	

	/**
	 * 產生提前加班上班考勤資料(平日)
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 * @param siesta_flag
	 */
	private void generateBeforeOvertimeInAttData(Connection conn, String employee_id, Map classMap, boolean siesta_flag ){
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			Calendar class_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
															classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
						   										   classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, 
																	this.cur_employee_id, this.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
					 										 classMap, 2);  //班別設定的下班時間(Calendar)

			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_in_cal));

			Calendar range_class_in_cal = (Calendar)class_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_in_cal.add(Calendar.SECOND, 59);
			
			int att_limit_start_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_START_HOUR")));
			
			//有提前加班情況
			Calendar class_before_ov_in_cal = null;
			
			class_before_ov_in_cal = (Calendar) class_in_cal.clone();
			//取得系統參數 資料擷取的開始時間
			//依據系統參數設定提前加班開始的可擷取的門禁資料時間
			class_before_ov_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_start_hour);
			
			String door_type = tools.getSysParam(conn, this.sta_comp_id, "OverTime_DOOR_IN");	//參數3   
															
			//正常情況  -- 有提前加班
			sql += this.getAttSelectSQL("IN_TIME",
										"IN_CAL_DATE",
										"IN_CAL_TIME",
										" <= '"+this.CalendarToString(range_class_in_cal)+"' ",//上班時間
								        " >= '"+this.CalendarToString(class_before_ov_in_cal)+"' ",//可抓取時間
								        employee_id,
								        this.sta_comp_id,
								        " ORDER BY EHF020404T0_06 ",
								        1,card_number,door_type);
			
			//因為EMS產品套裝功能調整 -> 上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			boolean flex_switch = false;
			int class_in_flex_sec = 0;
			int flex_diff_sec = 0;
			Map<String, String> abatt_map = null;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//封裝門禁資料
				EMS_AttendanceForm attform = this.packAttData(rs);
				
				//依據班別上班時間判斷是否遲到
				//int in_sec = this.TimeToSecs(rs.getString("IN_TIME"));  //第一筆門禁打卡時間(以秒計)
				Calendar in_cal = this.datetimeToCalendar(rs.getString("IN_CAL_DATE"), rs.getString("IN_CAL_TIME"));  //第一筆門禁打卡時間(Calendar)
				
				//siesta_flag = true ==> 判斷所抓取的考勤資料是否已超過上午下班時間, 若已超過設定為上班未打卡
				//siesta_flag = false ==> 判斷所抓取的考勤資料是否已超過下班時間, 若已超過設定為上班未打卡
				if( siesta_flag==true?(this.chkTimeIsOK(conn, in_cal, classMap, 7)):(this.chkTimeIsOK(conn, in_cal, classMap, 2)) ){
					//表示抓取的資料已超過上午下班時間, 所以資料不能當作上班時間,使用上班未打卡處理
					
					//上班未打卡
					/*this.InsertUnATTData(conn, 1, 4, classMap, employee_id);*/
					
					//建立出勤記錄
					/*ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id, 
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					this.sta_cur_day, "1", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
									 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal ) 
									   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
					abatt_map, 
					this.sta_comp_id);*/
					
				}else{
					//表示抓取的資料沒有超過下班時間, 可以當作上班時間
					
					if(!flex_switch){
						//沒有設定上班彈性時間
						if( in_cal.compareTo(range_class_in_cal) <= 0 ){
							
							//正常上班
							this.InsertATTData(conn, 5, false, 1, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							/*ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							this.sta_comp_id);*/
							
						}else if( in_cal.after(range_class_in_cal) && in_cal.before(class_siesta_in_cal) ){
							
							flex_diff_sec = this.CaculateLateSecs( 1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 5, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							/*ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							this.sta_comp_id);*/
							
						}else if( !siesta_flag && in_cal.after(range_class_in_cal) && in_cal.before(class_out_cal) ){
							
							//未記錄中午打卡
							flex_diff_sec = this.CaculateLateSecs( 1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 5, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							
						}else{
							
							//上班未打卡
							/*this.InsertUnATTData(conn, 1, 4, classMap, employee_id);*/
							
						}
						
					}else{
						//有設定上班彈性時間
						
					}
					
				}
				
			}else{
				
				//上班未打卡
				/*this.InsertUnATTData(conn, 1, 4, classMap, employee_id);*/
				
			}
			
			rs.close();
			stmt.close();
			//呼叫產生提前加班下班考勤資料
			this.generateBeforeOvertimeEndAttData( conn, employee_id, classMap);
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(sql);
		}
		
	}
	
	/**
	 * 產生提前加班下班考勤資料(平日)
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 */
	private void generateBeforeOvertimeEndAttData( Connection conn, String employee_id, Map classMap){
		// TODO Auto-generated method stub
		
		boolean siesta_flag = false;	//是否中午刷卡
		String sql = "";
		
		try{
			Calendar class_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
															classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
							   									   classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, 
																	this.cur_employee_id, this.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
						 									 classMap, 2);  //班別設定的下班時間(Calendar)

			//先取得可用的感應卡號
			String card_number = this.card_number(conn, employee_id, this.CalendarToString(class_in_cal));
			
			Calendar range_class_in_cal = (Calendar)class_in_cal.clone();
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_in_cal.add(Calendar.SECOND, 59);
			
			int att_limit_start_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_START_HOUR")));
			
			//有提前加班情況
			Calendar class_before_ov_in_cal = null;
			
			class_before_ov_in_cal = (Calendar) class_in_cal.clone();
			//取得系統參數 資料擷取的開始時間
			//依據系統參數設定提前加班開始的可擷取的門禁資料時間
			class_before_ov_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_start_hour);
			
			String door_type = tools.getSysParam(conn, this.sta_comp_id, "OverTime_DOOR_OUT");	//參數4
															
			//正常情況  -- 有提前加班
			sql += this.getAttSelectSQL("IN_TIME",
										"IN_CAL_DATE",
										"IN_CAL_TIME",
										" <= '"+this.CalendarToString(range_class_in_cal)+"' ",
								        " >= '"+this.CalendarToString(class_before_ov_in_cal)+"' ",
								        employee_id,
								        this.sta_comp_id,
								        " ORDER BY EHF020404T0_06 ",
								        1,card_number,door_type);
			
			//因為EMS產品套裝功能調整 -> 上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			boolean flex_switch = false;
			int class_in_flex_sec = 0;
			int flex_diff_sec = 0;
			Map<String, String> abatt_map = null;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//封裝門禁資料
				EMS_AttendanceForm attform = this.packAttData(rs);
								
				//正常加班下班                                  
				this.InsertATTData(conn, 6, false, 1, flex_switch, flex_diff_sec, classMap, attform);
				
			}else{
				
				//上班未打卡
				/*this.InsertUnATTData(conn, 1, 4, classMap, employee_id);*/
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(sql);
		}
		
	}

	
	/**
	 * 產生加班上班考勤資料(假日)
	 * @param conn
	 * @param employeeId
	 * @param classMap
	 * @param siesta_flag 
	 */
	private void generateHolidayOverTimeStartAttData(Connection conn,String employee_id, Map classMap, boolean siesta_flag) {
		// TODO Auto-generated method stub
		String sql = "";
		
		try{
			Calendar class_in_cal = this.getCalendarByClass			(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
																			classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass	(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
																   			classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass	(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
																			classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass		(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
															 				classMap, 2);  //班別設定的下班時間(Calendar)
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_in_cal));
			
			//建立 遲到早退曠職元件
			//EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			
			//上班可擷取時間
			int att_limit_start_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_START_HOUR")));
			
			//上班時間往前三個小時
			class_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_start_hour);
			
			//下班可擷取時間
			int att_limit_end_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_END_HOUR")));
			
			//下班時間往前三個小時
			class_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_end_hour);
			


			String door_type = tools.getSysParam(conn, this.sta_comp_id, "OverTime_DOOR_IN");	//參數1
				
			
			//正常假日加班情況 
			sql += this.getAttSelectSQL("IN_TIME",
										"IN_CAL_DATE",
										"IN_CAL_TIME", 
										" <= '"+this.CalendarToString(class_out_cal)+"' ",
										" >= '"+this.CalendarToString(class_in_cal)+"' ",								
										employee_id,
										this.sta_comp_id, 
										" ORDER BY EHF020404T0_06 ",
										1,card_number,door_type);	//上班打卡
			
			//有中午打卡記錄的情況
//			if(siesta_flag){
//				
//				//異常情況
//				sql += " UNION ";
//				sql += this.getAttSelectSQL("IN_TIME",
//											  "IN_CAL_DATE",
//											  "IN_CAL_TIME",  
//											  " > '"+this.CalendarToString(range_class_in_cal)+"' ",
//											  " < '"+this.CalendarToString(class_siesta_in_cal)+"' ",
//											  employee_id,
//											  this.sta_comp_id,
//											  " ORDER BY EHF020404T0_06 ",
//											  1,card_number,"");
//				
//			}else{
//				
//				//未設定需要記錄中午打卡
//				//異常情況
//				sql += " UNION ";
//				sql += this.getAttSelectSQL("IN_TIME",
//						  					  "IN_CAL_DATE",
//						  					  "IN_CAL_TIME",  
//						  					  " > '"+this.CalendarToString(range_class_in_cal)+"' ",
//						  					  " < '"+this.CalendarToString(class_out_cal)+"' ",
//						  					  employee_id,
//						  					  this.sta_comp_id,
//						  					  " ORDER BY EHF020404T0_06 ",
//						  					  1,card_number,"");
//				
//			}
			
			//因為EMS產品套裝功能調整 -> 上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			boolean flex_switch = false;
			int class_in_flex_sec = 0;
			int flex_diff_sec = 0;
			Map<String, String> abatt_map = null;
			
				/*if("1.6".equals(ems_version)){
					//使用遲到早退曠職元件處理
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("COMP_ID", this.sta_comp_id);
					abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
					flex_switch = Boolean.parseBoolean(abatt_map.get("DELAY_FLEX_FLAG"));  //上班彈性時間開關
					class_in_flex_sec = ems_tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"));  //上班彈性時間
				}else{
					flex_switch = (Boolean) classMap.get("START_FLEXIBLE_SWITCH");  //上班彈性時間開關
					class_in_flex_sec = ems_tools.MinuteToSecs( (String) classMap.get("START_FLEXIBLE_TIME") );  //上班彈性時間
				}*/
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//封裝門禁資料
				EMS_AttendanceForm attform = this.packAttData(rs);
				
				//正常加班上班(假日)
				this.InsertATTData(conn, 5, false, 1, flex_switch, flex_diff_sec, classMap, attform);

			}
			
			rs.close();
			stmt.close();
			
			if(siesta_flag){
				//呼叫產生上午下班考勤資料(沒有加班情況)
				//this.generateSiestaInAttData(conn, employee_id, classMap);
			}else{
				//呼叫產生下班考勤資料(沒有加班情況)
				this.generateHolidayOverTimeEndAttData( conn, employee_id, classMap);
			}
			
			//關閉遲到早退曠職處理元件
			//ems_abatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(sql);
		}	
		
	}
	
	/**
	 * 產生加班下班考勤資料(假日)
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 * @param siesta_flag
	 */
	private void generateHolidayOverTimeEndAttData(Connection conn,String employee_id, Map classMap) {
		// TODO Auto-generated method stub
		String sql = "";
		
		try{
			Calendar class_in_cal = this.getCalendarByClass			(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
																			classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass	(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
																   			classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass	(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
																			classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass		(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
															 				classMap, 2);  //班別設定的下班時間(Calendar)
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_in_cal));
			
			//建立 遲到早退曠職元件
			//EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			
			//上班可擷取時間
			int att_limit_start_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_START_HOUR")));
			
			//上班時間往前三個小時
			class_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_start_hour);
			
			//下班可擷取時間
			int att_limit_end_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_END_HOUR")));
			
			//下班時間往前三個小時
			class_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_end_hour);
			


			String door_type = tools.getSysParam(conn, this.sta_comp_id, "OverTime_DOOR_OUT");	//參數1
				
			
			//正常假日加班情況 
			sql += this.getAttSelectSQL("IN_TIME",
										"IN_CAL_DATE",
										"IN_CAL_TIME", 
										" <= '"+this.CalendarToString(class_out_cal)+"' ",
										" >= '"+this.CalendarToString(class_in_cal)+"' ",								
										employee_id,
										this.sta_comp_id, 
										" ORDER BY EHF020404T0_06 ",
										1,card_number,door_type);	//上班打卡
			
			//有中午打卡記錄的情況
//			if(siesta_flag){
//				
//				//異常情況
//				sql += " UNION ";
//				sql += this.getAttSelectSQL("IN_TIME",
//											  "IN_CAL_DATE",
//											  "IN_CAL_TIME",  
//											  " > '"+this.CalendarToString(range_class_in_cal)+"' ",
//											  " < '"+this.CalendarToString(class_siesta_in_cal)+"' ",
//											  employee_id,
//											  this.sta_comp_id,
//											  " ORDER BY EHF020404T0_06 ",
//											  1,card_number,"");
//				
//			}else{
//				
//				//未設定需要記錄中午打卡
//				//異常情況
//				sql += " UNION ";
//				sql += this.getAttSelectSQL("IN_TIME",
//						  					  "IN_CAL_DATE",
//						  					  "IN_CAL_TIME",  
//						  					  " > '"+this.CalendarToString(range_class_in_cal)+"' ",
//						  					  " < '"+this.CalendarToString(class_out_cal)+"' ",
//						  					  employee_id,
//						  					  this.sta_comp_id,
//						  					  " ORDER BY EHF020404T0_06 ",
//						  					  1,card_number,"");
//				
//			}
			
			//因為EMS產品套裝功能調整 -> 上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			boolean flex_switch = false;
			int class_in_flex_sec = 0;
			int flex_diff_sec = 0;
			Map<String, String> abatt_map = null;
			
				/*if("1.6".equals(ems_version)){
					//使用遲到早退曠職元件處理
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("COMP_ID", this.sta_comp_id);
					abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
					flex_switch = Boolean.parseBoolean(abatt_map.get("DELAY_FLEX_FLAG"));  //上班彈性時間開關
					class_in_flex_sec = ems_tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"));  //上班彈性時間
				}else{
					flex_switch = (Boolean) classMap.get("START_FLEXIBLE_SWITCH");  //上班彈性時間開關
					class_in_flex_sec = ems_tools.MinuteToSecs( (String) classMap.get("START_FLEXIBLE_TIME") );  //上班彈性時間
				}*/
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//封裝門禁資料
				EMS_AttendanceForm attform = this.packAttData(rs);
				
				//正常加班上班(假日)
				this.InsertATTData(conn, 6, false, 1, flex_switch, flex_diff_sec, classMap, attform);

			}
			
			rs.close();
			stmt.close();
			
			
			//呼叫產生下班考勤資料(沒有加班情況)
			//this.generateEndAttData( conn, employee_id, classMap);
			
			
			//關閉遲到早退曠職處理元件
			//ems_abatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(sql);
		}	
		
	}
	
	/**
	 * 產生考勤判斷用的門禁資料查詢SQL(提供外部元件呼叫)
	 * @param conn
	 * @param employee_id
	 * @param card_no
	 */
	public String getAttSelectSQLForm( String colname1, String colname2, String colname3, 
			  					       String timecond1, String timecond2, String employee_id, 
			  					       String comp_id, String others, int limit_value ,String card_number, String door_type ){
		
		String sql = "";
		
		try{			
			sql = getAttSelectSQL( colname1, colname2, colname3, 
					  			   timecond1, timecond2, employee_id, 
					  			   comp_id, others, limit_value , card_number, door_type );
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sql;
	}
	
	/**
	 * 產生考勤判斷用的門禁資料查詢SQL
	 * @param colname1
	 * @param colname2
	 * @param colname3
	 * @param timecond1
	 * @param timecond2
	 * @param employee_id
	 * @param comp_id
	 * @param others
	 * @param limit_value
	 * @return
	 */
	protected String getAttSelectSQL( String colname1, String colname2, String colname3, 
									  String timecond1, String timecond2, String employee_id, 
									  String comp_id, String others, int limit_value ,String card_number, String door_type ){
		
		String sql = "";
		
		try{
			
			sql += " SELECT * FROM ( " +
	  		 " SELECT " +
	  		 " IFNULL(EHF020404T0_01, '') AS EHF020404T0_01, " +
	  		 " IFNULL(EHF020404T0_02, '') AS EHF020404T0_02, " +
	  		 " IFNULL(EHF020404T0_03, '') AS EHF020404T0_03, " +
	  		 " IFNULL(EHF020404T0_04, '') AS EHF020404T0_04, " +//打卡日期
	  		 " IFNULL(EHF020404T0_05, '') AS EHF020404T0_05, " +//打卡時間
	  		 " IFNULL(EHF020404T0_06, '') AS EHF020404T0_06, " +//打卡日期時間
	  		 " IFNULL(EHF020404T0_07, '') AS EHF020404T0_07, " +
	  		 " IFNULL(EHF020404T0_08, '') AS EHF020404T0_08, " +
	  		 " IFNULL(EHF020404T0_09, '') AS EHF020404T0_09, " +
	  		 " IFNULL(EHF020404T0_10, '') AS EHF020404T0_10, " +//資料來源
	  		 " IFNULL((EHF020404T0_06), '') AS LIMIT_VALUE, " +
			 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%H%i'), '') AS "+colname1+", " +  //打卡日期時間
			 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%Y/%m/%d'), '') AS "+colname2+", " +
			 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%H:%i:%s'), '') AS "+colname3+" " +
			 " FROM EHF020404T0 " +
			 " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020404T0_03 AND EHF020403T1_06 = EHF020404T0_09 " +
			 " WHERE 1=1 " +
			 " AND EHF020404T0_06 IS NOT NULL " +  //打卡日期時間
			 " AND EHF020404T0_06 "+timecond1+" " ;
			 if(!"".equals(timecond2)){
				 sql += " AND EHF020404T0_06 "+timecond2+" " ;
			 }			 
			 sql += " AND IF(EHF020404T0_10 = 1, IF(EHF020404T0_FLAG = 1, 1, 0), IF(EHF020404T0_FLAG = 2, IF(EHF020404T0_FLAG = 2, 1, 0), 0)) = 1" +
			
			// " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			 //因應臨時卡機制, 必須修改門禁資料查詢SQL, 加入員工感應卡的使用期間作為額外的判斷條件, 且必須納入臨時卡資料作為感應卡資料之一
			 //門禁資料打卡日期時間必須是在員工感應卡有效期間內才算是有效的打卡日期時間資料  edit by joe 2012/07/25 -- (目前尚未實作)
			 " AND EHF020404T0_03 IN ("+card_number+") " +  //感應卡號  新增於20140113 BY 賴泓錡
//			 " AND EHF020404T0_04 = '"+this.sta_cur_day+"' " +  //計算考勤的日期
			 " AND EHF020404T0_09 = '"+comp_id+"' " ;  //公司代碼
			 if(!"".equals(door_type)){
				 sql += " AND EHF020404T0_08 = '"+door_type+"' ";	//動作代碼
			 }
			 sql += " "+others+" " +
			 " LIMIT "+limit_value+" " +
			 " ) tableA " +
			 " WHERE 1=1 " +
			 " AND tableA.EHF020404T0_06 <> '' " ;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sql;
	}
	
	/**
	 * 新增考勤資料
	 * @param conn
	 * @param card_status
	 * @param error_switch
	 * @param error_no
	 * @param flex_switch
	 * @param flex_diff_sec  若有異常此欄位為"早退","遲到"時間  若無異常此欄位為"彈性使用時間"
	 * @param classMap
	 * @param attform
	 */
	private void InsertATTData(Connection conn, int card_status, boolean error_switch, int error_no, boolean flex_switch, int flex_diff_sec,
							   Map classMap, EMS_AttendanceForm attform ){
		
		String user_create = "SYSTEM";
		//20131028 新增 BY賴泓錡
		//是否記錄考勤
		EHF010100M0F ehf010100m0 = this.getEmpSalFile(conn, this.cur_employee_id, this.sta_comp_id);
		try{
			
			//新增考勤資料 EHF020401T0
			String sql = "" +
			" INSERT INTO EHF020401t0 (EHF020401T0_01, EHF020401T0_02, EHF020401T0_02_EMP, " +
			" EHF020401T0_03, EHF020401T0_04, EHF020401T0_05, EHF020401T0_05_DATE, " +
			" EHF020401T0_06, EHF020401T0_07, EHF020401T0_08, EHF020401T0_08_FLE, " +
			" EHF020401T0_09, EHF020401T0_09_DESC, EHF020401T0_10, " +
			" EHF020401T0_11, EHF020401T0_FIX,EHF020401T0_FDS,USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?, 1, NOW() ) ";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;

			p6stmt.setString(indexid, attform.getEHF020404T0_02());  //出勤系統號
			indexid++;
			p6stmt.setString(indexid, attform.getEHF020404T0_03());  //感應卡號
			indexid++;
			p6stmt.setString(indexid, this.cur_employee_id );  //員工工號
			indexid++;
			p6stmt.setString(indexid, "");  //卡別
			indexid++;
			p6stmt.setInt(indexid, (Integer) classMap.get("WORK_CLASS_NO"));  //班別序號
			indexid++;
			p6stmt.setString(indexid, this.sta_cur_day);  //考勤日期
			indexid++;
			p6stmt.setString(indexid, attform.getEHF020404T0_04());  //打卡日期
			indexid++;
			p6stmt.setString(indexid, attform.getEHF020404T0_05());  //打卡時間
			indexid++;
			p6stmt.setString(indexid, attform.getEHF020404T0_06());  //打卡日期時間
			indexid++;
			p6stmt.setString(indexid, card_status+"");  //打卡狀態
			indexid++;
			
			//彈性已使用時間 or 遲到時間 or 早退時間, 有使用彈性時間或有異常時皆需記錄
			if(flex_switch || error_switch){
				p6stmt.setString(indexid, flex_diff_sec+"");  
			}else{
				p6stmt.setString(indexid, "0");
			}
			indexid++;

			p6stmt.setBoolean(indexid, error_switch);  //是否異常
			indexid++;
			p6stmt.setString(indexid, tools.getCardErrorStatus(error_no));  //異常說明
			indexid++;
			p6stmt.setString(indexid, attform.getEHF020404T0_07());  //刷卡機器代號
			indexid++;
			p6stmt.setString(indexid, this.sta_comp_id);  //公司代碼
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
			if( !"".equals(attform.getUSER_CREATE()) && attform.getUSER_CREATE() != null ){
				user_create = attform.getUSER_CREATE();
			}else{
				user_create = "SYSTEM";
			}
			
			p6stmt.setString(indexid, user_create);  //建立人員
			indexid++;
			p6stmt.setString(indexid, user_create);  //修改人員
			indexid++;
			
			
			//列印SQL語法
			//System.out.println("新增考勤資料SQL語法 -->"+p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();			
			
			//資料庫 COMMIT
			if(this.commit_flag){
				conn.commit();
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * 新增未卡打記錄資料
	 * @param conn
	 * @param card_status
	 * @param error_switch
	 * @param error_no
	 * @param classMap
	 * @param attform
	 */
	private void InsertUnATTData(Connection conn, int card_status, int error_no, Map classMap, String employee_id ,String EHF020404T0_01){
		
		try{
			
			//判斷是否已有打卡記錄
			if(!this.chkUnATTDataExist(conn, employee_id, card_status )){ 
				//20131028 新增 BY賴泓錡
				//取得薪資主檔
				EHF010100M0F ehf010100m0 = this.getEmpSalFile(conn, employee_id, this.sta_comp_id);
				
				//沒有未打卡記錄才可新增
				//新增考勤資料 EHF020406T0
				String sql = "" +
				" INSERT INTO ehf020406t0 (EHF020406T0_01, EHF020406T0_02, EHF020406T0_03, " +
				" EHF020406T0_04, EHF020406T0_04_DTE, EHF020406T0_05, EHF020406T0_06, " +
				" EHF020406T0_07, EHF020406T0_FIX, EHF020406T0_FDS,EHF020406T0_FK, USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " +
				" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, NOW() ) ";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;

				p6stmt.setString(indexid, employee_id);  //員工工號
				indexid++;
				p6stmt.setString(indexid, "");  //卡別
				indexid++;
				p6stmt.setInt(indexid, (Integer) classMap.get("WORK_CLASS_NO"));  //班別序號
				indexid++;
				p6stmt.setString(indexid, this.sta_cur_day);  //未打卡日期(考勤日期)
				indexid++;
				p6stmt.setString(indexid, this.CalendarToString(this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
																	 classMap, card_status)));  //未打卡日期時間(作為異常判斷用)
				indexid++;
				p6stmt.setString(indexid, card_status+"");  //打卡狀態
				indexid++;
				p6stmt.setString(indexid, tools.getCardErrorStatus(error_no));  //未打卡說明
				indexid++;
				
				p6stmt.setString(indexid, this.sta_comp_id);  //公司代碼
				indexid++;
				
				//20131028 新增 寫入"是否計算考勤" BY 賴泓錡
				//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
				if ("0".equals(ehf010100m0.getEHF020403T0_05())) {
					p6stmt.setBoolean(indexid, true); // 是否已修正
					indexid++;
					p6stmt.setString(indexid, "不計算考勤人員");  //EHF020406T0_FDS 修正來源
					indexid++;
				} else {
					p6stmt.setBoolean(indexid, false); // 是否已修正
					indexid++;
					p6stmt.setString(indexid, "");  //EHF020406T0_FDS 修正來源
					indexid++;
				}
				
				p6stmt.setString(indexid, EHF020404T0_01);  //公司代碼
				indexid++;
				
				
				p6stmt.setString(indexid, "SYSTEM");  //建立人員
				indexid++;
				p6stmt.setString(indexid, "SYSTEM");  //修改人員
				indexid++;
				
				
				//列印SQL語法
				//System.out.println("新增未打卡資料SQL語法 -->"+p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();			
				
				//資料庫 COMMIT
				if(this.commit_flag){
					conn.commit();
				}
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * 回寫上下班考勤為異常	
	 * @param conn
	 * @param error_switch
	 * @param error_no
	 * @param classMap
	 */
	private void UpdateATTData(Connection conn, boolean error_switch, int error_no, Map classMap){
		
		String sql ="";
		String show_sql = "";
		
		try{
			sql = "" +
			" UPDATE EHF020401T0 SET " +
			" EHF020401T0_09=?, EHF020401T0_09_DESC=?, USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF020401T0_02_EMP=? " +
			" AND EHF020401T0_08 IN ('1','2') " +
			" AND EHF020401T0_11=? " +
			" AND EHF020401T0_05=? ";
			
			//執行SQL
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setBoolean(indexid, error_switch);	////是否異常
			indexid++;
			p6stmt.setString(indexid, tools.getCardErrorStatus(error_no));  //異常說明
			indexid++;
			p6stmt.setString(indexid, "SYSTEM");  //修改人員
			indexid++;
			
			p6stmt.setString(indexid, this.cur_employee_id);  //員工工號
			indexid++;
			p6stmt.setString(indexid, this.sta_comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, this.sta_cur_day);  //考勤日期
			indexid++;
			
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
			//資料庫 COMMIT
			if(this.commit_flag){
				conn.commit();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 依據班別資料,cal_type 產生所需要的Calendar
	 * @param date
	 * @param classMap
	 * @param cal_type
	 * @return
	 */
	public Calendar getCalendarByClass(Connection conn, String date, String employee_id, String comp_id,
								       Map classMap, int cal_type){
		
		Calendar cal = null;
		
		try{
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)			
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (下班後加班)			
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			boolean ov_flag = false;
			Map<String, String> ov_map = null;
			
			//取得考勤日期是否為假日資訊
			boolean ha_flag = this.isHoliday(conn, date, comp_id, (String)classMap.get("WORK_CLASS_HOLIDAY"));
			
			//加班處理元件參數
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = null;
			
			switch(cal_type){
			
			//產生上班時間的Calendar
			case 1:
				cal = this.datetimeToCalendar(date, ((String) classMap.get("WORK_START_TIME_CAL")));  //班別設定的上班時間
				break;
				
			//產生下班時間的Calendar
			case 2:
				//判斷班別是否有跨午夜十二點
				if(hr_tools.hasCrossMidnight(classMap, 2) || hr_tools.hasCrossMidnight(classMap, 3) 
				   || hr_tools.hasCrossMidnight(classMap, 4) ){
					cal = this.datetimeToCalendar(date, ((String) classMap.get("WORK_END_TIME_CAL")));  //班別設定的下班時間
					//因為該班別有跨午夜十二點,所以下班時間要加一天
					cal.add(Calendar.DAY_OF_MONTH, 1);
				}else{
					cal = this.datetimeToCalendar(date, ((String) classMap.get("WORK_END_TIME_CAL")));  //班別設定的下班時間
				}
				break;
				
			//產生上午下班時間的Calendar
			case 7:
				//判斷班別是否有跨午夜十二點
				if(hr_tools.hasCrossMidnight(classMap, 3)){
					cal = this.datetimeToCalendar(date, ((String) classMap.get("SIESTA_START_TIME_CAL")));  //班別設定的上午下班時間
					//因為該班別有跨午夜十二點,所以下班時間要加一天
					cal.add(Calendar.DAY_OF_MONTH, 1);
				}else{
					cal = this.datetimeToCalendar(date, ((String) classMap.get("SIESTA_START_TIME_CAL")));  //班別設定的上午下班時間
				}
				break;
			
			//產生下午上班時間的Calendar
			case 8:
				//判斷班別是否有跨午夜十二點
				if(hr_tools.hasCrossMidnight(classMap, 3) || hr_tools.hasCrossMidnight(classMap, 4) ){
					cal = this.datetimeToCalendar(date, ((String) classMap.get("SIESTA_END_TIME_CAL")));  //班別設定的下午上班時間
					//因為該班別有跨午夜十二點,所以下班時間要加一天
					cal.add(Calendar.DAY_OF_MONTH, 1);
				}else{
					cal = this.datetimeToCalendar(date, ((String) classMap.get("SIESTA_END_TIME_CAL")));  //班別設定的下午上班時間
				}
				break;
				
			//產生加班上班時間的Calendar
			case 5:
				
				//建立加班處理元件
				ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
				//ems_ovatt_handle.setOutside_conn(conn);  //設定外部Connection
				
				//下班後加班與假日加班的功能同時處理									
				Calendar class_ov_in_cal = null;  //班別設定的加班上班時間(Calendar)
				//Calendar class_ov_out_cal = null;  //班別設定的加班下班時間(Calendar)
					
				//判斷是否為假日
				if(ha_flag){
					//處理假日加班
					ov_map = ems_ovatt_handle.findHaOvertimeData(
							 conn,
							 date, 
							 employee_id,
							 comp_id);
					
					ov_flag = Boolean.parseBoolean(ov_map.get("HA_OVERTIME_SWITCH"));
						
					if(ov_flag){
						class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("HA_OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
						//class_ov_out_cal = this.datetimeToCalendar(ov_map.get("HA_OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
					}
						
				}else{
					//處理下班後加班
					ov_map = ems_ovatt_handle.findOvertimeData(
							 conn,
							 date, 
							 employee_id, 
							 this.CalendarToString(this.getCalendarByClass(conn, date, employee_id, comp_id, classMap, 2)), 
							 comp_id);
						
					ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
						
					if(ov_flag){
						class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
						//class_ov_out_cal = this.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
					}
				}
					
					
					
				//有設定加班才可以產生Calendar
				if(ov_flag){
					
					cal = (Calendar) class_ov_in_cal.clone();  //班別設定的加班上班時間
					
				}else{
					cal = null;
				}									
				
				//關閉加班處理元件
				ems_ovatt_handle.close();
				
				break;
				
			//產生加班下班時間的Calendar	
			case 6:
				
				//建立加班處理元件
				ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
				//ems_ovatt_handle.setOutside_conn(conn);  //設定外部Connection
				
				//下班後加班與假日加班的功能同時處理									
				//Calendar class_ov_in_cal = null;  //班別設定的加班上班時間(Calendar)
				Calendar class_ov_out_cal = null;  //班別設定的加班下班時間(Calendar)
					
				//判斷是否為假日
				if(ha_flag){
					//處理假日加班
					ov_map = ems_ovatt_handle.findHaOvertimeData(
							 conn,
							 date, 
							 employee_id,
							 comp_id);
					
					ov_flag = Boolean.parseBoolean(ov_map.get("HA_OVERTIME_SWITCH"));
						
					if(ov_flag){
						//class_ov_in_cal = this.datetimeToCalendar(ov_map.get("HA_OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
						class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("HA_OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
					}
						
				}else{
					//處理下班後加班
					ov_map = ems_ovatt_handle.findOvertimeData(
							 conn,
							 date, 
							 employee_id, 
							 this.CalendarToString(this.getCalendarByClass(conn, date, employee_id, comp_id, classMap, 2)), 
							 comp_id);
						
					ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
						
					if(ov_flag){
						//class_ov_in_cal = this.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
						class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
					}
						
				}
					
					
				//有設定加班才可以產生Calendar
				if(ov_flag){
					
					cal = (Calendar) class_ov_out_cal.clone();  //班別設定的加班下班時間					
					
				}else{
					cal = null;
				}
					
								
				
				//關閉加班處理元件
				ems_ovatt_handle.close();
				
				break;
				
			//產生提前加班開始時間的Calendar(不考慮 )
			case 9:
				//有設定提前加班才可以產生Calendar
				/*if(ems_tools.hasBeforeOverTime(classMap)){
					//判斷班別是否有跨午夜十二點
					if(ems_tools.hasCrossMidnight(classMap, 7)){
						cal = this.datetimeToCalendar(date, ((String) classMap.get("ADVANCE_OVERTIME_START_TIME_CAL")));  //班別設定的提前加班時間
						//因為該班別有跨午夜十二點,所以提前加班時間要減一天
						cal.add(Calendar.DAY_OF_MONTH, -1);
					}else{
						cal = this.datetimeToCalendar(date, ((String) classMap.get("ADVANCE_OVERTIME_START_TIME_CAL")));  //班別設定的提前加班時間
					}
				}else{
					cal = null;
				}*/
				break;
				
			//產生延後下班時間的Calendar
			case 10:
				//有設定延後下班才可以產生Calendar
				/*if(ems_tools.hasLateOverTime(classMap)){
					//先產生下班時間的Calendar
					cal = this.getCalendarByClass(conn, date, employee_id, comp_id, classMap, 2);
					//產生延後下班的Calendar
					int late_minute = (int)(Float.parseFloat((String)classMap.get("LATETIME_HOUR")) * 60);  //延後下班時數
					//將下班時間加上延後下班時數作為延後下班時間的Calendar
					cal.add(Calendar.MINUTE, late_minute);
				}else{
					cal = null;
				}*/
				break;
			
			}
			
			hr_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	/**
	 * 先取得可用的感應卡號(提供外部元件呼叫)
	 * @param conn
	 * @param employee_id
	 * @param card_no
	 */
	public String getCard_Number(Connection conn, String employee_id, String Cal ){
		
		String card_number = "";
		
		try{			
			card_number = card_number(conn, employee_id, Cal);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return card_number;
	}
	
	/**
	 * 取得可用的感應卡號
	 * @param conn
	 * @param employeeId
	 * @param Cal
	 * @return
	 */
	private String card_number(Connection conn, String employeeId,String Cal ) {
		// TODO Auto-generated method stub
		String card_number="";
		try{
		
		String sql="SELECT * " +
				" FROM EHF020403T1" +
				" WHERE 1 = 1" +
				" AND EHF020403T1_01='"+employeeId +"'"+
				" AND '"+Cal+"' BETWEEN DATE_FORMAT(EHF020403T1_04_START, '%Y/%m/%d %H%i%s') AND DATE_FORMAT(EHF020403T1_04_END, '%Y/%m/%d %H%i%s')" ;
				//" AND '"+beforeClassInCal+"' BETWEEN DATE_FORMAT(EHF020403T1_04_START, '%Y/%m/%d %H%i%s') AND DATE_FORMAT(EHF020403T1_04_END, '%Y/%m/%d %H%i%s')";
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		while(rs.next()){
			card_number+="'"+rs.getString("EHF020403T1_02")+"'";
			if(rs.next()){
				card_number+=",";
				rs.previous();
			}
		}
		
		rs.close();
		stmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				throw new Exception("取得可用的感應卡號，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return card_number;
	}
	
	/**
	 * 轉換 Calendar to String
	 * @param cal
	 * @param time
	 * @return
	 */
	public String CalendarToString( Calendar cal ){
		
		String return_date = ""; 
		
		try{
			return_date = tools.covertDateToString(cal.getTime(), "yyyy/MM/dd HH:mm:ss");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_date;
	}
	
	/**
	 * 轉換  Datetime to Calendar
	 * @param date
	 * @param time
	 * @return
	 */
	public Calendar datetimeToCalendar( String date, String time ){
		
		Calendar cal = null; 
		
		try{
			cal = tools.covertStringToCalendar(date+" "+time, "yyyy/MM/dd HH:mm:ss");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cal;
	}
	
	/**
	 * 判斷是否為假日
	 * @param conn
	 * @param date
	 * @param comp_id
	 * @return
	 */
	public boolean isHoliday(Connection conn, String date, String comp_id, String EHF000400T0_17){
		
		return this.isHoliday_FK;
	}
	
	/**
	 * 刪除考勤資料
	 * @param conn
	 * @param employee_id
	 */
	protected void delAttData(Connection conn, String employee_id, boolean full_del_flag, int del_type){
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
						 " SELECT EHF020403T1_02 " +
						 " FROM EHF020403T1" +
						 " WHERE 1=1" +
						 " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
						 " AND EHF020403T1_06 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				String card_no = rs.getString("EHF020403T1_02");
			
				if(full_del_flag){
					//刪除所有考勤資料
					this.delAllAttData(conn, employee_id, card_no);
				}else{
					//依據傳入的 del_type 刪除資料
					/*this.delAttDataByType(conn, employee_id, card_no, del_type);*/
				}
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("刪除考勤資料delAttData()時，發生錯誤："+e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 檢核人員考勤資料
	 * @param conn
	 * @param class_no
	 * @param employee_id
	 * @param siesta_flag  
	 * @param ov_flag
	 * @param ho_flag
	 * @param classMap 
	 * @param isHoliday 如果是-1  表示就是假日
	 * @return
	 */
	private int chkAttendance(Connection conn, int class_no, String employee_id, 
						      boolean siesta_flag, boolean ov_flag, boolean AdminFlag, 
						      Map classMap, boolean isHoliday){
		
		//考勤資料 FLAG
		boolean in_flag = false;  //上班
		boolean out_flag = false; //下班
		boolean siesta_in_flag = false;  //上午下班
		boolean siesta_out_flag = false; //下午上班
		boolean in_over_flag = false;  //加班上班
		boolean out_over_flag = false; //加班下班
		
		try{			
			//若是Admin測試 Set flag = true 強制重新產生
			if(AdminFlag){
				//判斷今天是否為假日, ho_flag 用來設定是否處理不休假加班 -->  true:處理    false:不處理
				if(!isHoliday){
					//return 7  --> 進行不休假加班處理
					return 7;
				}else{
					return 6;
				}
			}
			//只產生早上考勤時，不用管下班考勤資料(避免return 3)
			if(today_FK){
				return 6;
			}
			
			//判斷今天是否為假日, ho_flag 用來設定是否處理不休假加班 -->  true:處理    false:不處理
//			if(tools.isHoliday(conn, this.sta_cur_day, this.sta_comp_id, (String)classMap.get("WORK_CLASS_HOLIDAY")) && ho_flag){
//				//return 7  --> 進行不休假加班處理
//				return 7;
//			}
			
			//找出人員當日已產生的考勤資料

			//上班考勤資料
			String sql = "" +
						 " SELECT EHF020401T0_01 " +
						 " FROM EHF020401T0 " +
						 " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
						 " WHERE 1=1 " +
//						 " AND EHF020401T0_04 = "+class_no+" " +  //班別序號
						 " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //考勤日期
						 " AND EHF020401T0_08 = '1' " +  //打卡狀態 - 上班
						 " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
						 " AND EHF020401T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){			
				in_flag = true;
			}else{
				//檢核是否已有上班未打卡記錄
				if(this.chkUnATTDataExist(conn, employee_id, 1)){
					in_flag = true;
				}else{
					in_flag = false;
				}
			}
			
			rs.close();
			
			//班別有設定記錄中午打卡記錄才檢查上午下班與下午上班考勤資料
			if(siesta_flag){
				//上午下班考勤資料
				sql = "" +
					  " SELECT EHF020401T0_01 " +
					  " FROM EHF020401T0 " +
					  " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
					  " WHERE 1=1 " +
//					  " AND EHF020401T0_04 = "+class_no+" " +  //班別序號
					  " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //考勤日期
					  " AND EHF020401T0_08 = '7' " +  //打卡狀態 - 上午下班
					  " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
					  " AND EHF020401T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
				
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){			
					siesta_in_flag = true;
				}else{
					//檢核是否已有上午下班未打卡記錄
					if(this.chkUnATTDataExist(conn, employee_id, 7)){
						siesta_in_flag = true;
					}else{
						siesta_in_flag = false;
					}
				}
				
				rs.close();
				
				//下午上班考勤資料
				sql = "" +
					  " SELECT EHF020401T0_01 " +
					  " FROM EHF020401T0 " +
					  " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
					  " WHERE 1=1 " +
//					  " AND EHF020401T0_04 = "+class_no+" " +  //班別序號
					  " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //考勤日期
					  " AND EHF020401T0_08 = '8' " +  //打卡狀態 - 下午上班
					  " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
					  " AND EHF020401T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
				
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){			
					siesta_out_flag = true;
				}else{
					//檢核是否已有上午下班未打卡記錄
					if(this.chkUnATTDataExist(conn, employee_id, 8)){
						siesta_out_flag = true;
					}else{
						siesta_out_flag = false;
					}
				}
				
				rs.close();
			}
			
			
			//下班考勤資料
			sql = "" +
				  " SELECT EHF020401T0_01 " +
				  " FROM EHF020401T0 " +
				  " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
				  " WHERE 1=1 " +
//				  " AND EHF020401T0_04 = "+class_no+" " +  //班別序號
				  " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //考勤日期
				  " AND EHF020401T0_08 = '2' " +  //打卡狀態 - 下班
				  " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
				  " AND EHF020401T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				out_flag = true;
			}else{
				//檢核是否已有下班未打卡記錄
				if(this.chkUnATTDataExist(conn, employee_id, 2)){
					out_flag = true;
				}else{
					out_flag = false;
				}
			}
			
			rs.close();
			
			//班別有設定加班才檢查加班上班與加班下班的考勤資料
			if(ov_flag){
				
				//加班上班考勤資料
				sql = "" +
				  	  " SELECT EHF020401T0_01 " +
				  	  " FROM EHF020401T0 " +
				  	  " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
				  	  " WHERE 1=1 " +
//				  	  " AND EHF020401T0_04 = "+class_no+" " +  //班別序號
				  	  " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //考勤日期
				  	  " AND EHF020401T0_08 = '5' " +  //打卡狀態 - 加班上班
				  	  " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
				  	  " AND EHF020401T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
				
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					in_over_flag = true;
				}else{
					//檢核是否已有加班上班未打卡記錄
					if(this.chkUnATTDataExist(conn, employee_id, 5)){
						in_over_flag = true;
					}else{
						in_over_flag = false;
					}	
				}
				
				rs.close();
				
				
				//加班下班考勤資料
				sql = "" +
				  	  " SELECT EHF020401T0_01 " +
				  	  " FROM EHF020401T0 " +
				  	  " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
				  	  " WHERE 1=1 " +
//				  	  " AND EHF020401T0_04 = "+class_no+" " +  //班別序號
				  	  " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //考勤日期
				  	  " AND EHF020401T0_08 = '6' " +  //打卡狀態 - 加班下班
				  	  " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
				  	  " AND EHF020401T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
				
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					out_over_flag = true;
				}else{
					//檢核是否已有加班下班未打卡記錄
					if(this.chkUnATTDataExist(conn, employee_id, 6)){
						out_over_flag = true;
					}else{
						out_over_flag = false;
					}
				}
				
				rs.close();
			}
			
			stmt.close();
			
			
			//判斷當天考勤資料缺少的狀況
			if(in_flag){
				//有上班資料
				//判斷是否記錄中午打卡
				if(siesta_flag){
					//有記錄中午打卡
					if(siesta_in_flag){
						//有上午下班資料
						if(siesta_out_flag){
							//有下午上班資料
							if(out_flag){
								//有下班資料
								
							}else{
								//沒有下班資料
								return 3;
							}
						}else{
							//沒有下午上班資料
							return 4;
						}
					}else{
						//沒有上午下班資料
						return 5;
					}
				}else{
					//沒有記錄中午打卡
					if(out_flag){
						//有下班資料
						
					}else{
						//沒有下班資料
						return 3;
					}
					
				}
				
				//判斷是否有設定加班
				if(ov_flag){
					//有設定加班
					if(in_over_flag){
						//有加班上班資料
						if(out_over_flag){
							//有加班下班資料
						}else{
							//沒有加班下班資料
							return 1;
						}
					}else{
						//沒有加班上班資料
						return 2;
					}
				}else{
					//沒有設定加班
				}
				
			}else{
				//沒有上班資料
				//皆沒有
				return 6;
				
			}
			
			
			/*
			if( in_flag && siesta_in_flag && siesta_out_flag && out_flag && in_over_flag && out_over_flag ){
				//有上班,上午下班,下午上班,下班,加班上班,加班下班
				return 0;
			}else if( in_flag && siesta_in_flag && siesta_out_flag && out_flag && in_over_flag && !out_over_flag ){
				//有上班,上午下班,下午上班,下班,加班上班
				return 1;
			}else if( in_flag && siesta_in_flag && siesta_out_flag && out_flag && !in_over_flag && !out_over_flag ){
				//有上班,上午下班,下午上班,下班
				return 2;
			}else if( in_flag && siesta_in_flag && siesta_out_flag && !out_flag && !in_over_flag && !out_over_flag ){
				//有上班,上午下班,下午上班
				return 3;
			}else if( in_flag && siesta_in_flag && !siesta_out_flag && !out_flag  && !in_over_flag && !out_over_flag ){
				//有上班,上午下班
				return 4;
			}else if( in_flag && !siesta_in_flag && !siesta_out_flag && !out_flag && !in_over_flag && !out_over_flag ){
				//有上班
				return 5;
			}else if( !in_flag && !siesta_in_flag && !siesta_out_flag && !out_flag && !in_over_flag && !out_over_flag ){
				//皆沒有
				return 6;
			}else{
				//未知
				return 6;
			}
			*/
				
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * 檢查是否已有未打卡記錄資料
	 * @param conn
	 * @param employee_id
	 * @param card_status
	 * @return
	 */
	private boolean chkUnATTDataExist(Connection conn, String employee_id, int card_status ){
		
		boolean chk_flag = false;
		
		try{
			
			String sql = "" +
			 " SELECT EHF020406T0_01, EHF020406T0_04, EHF020406T0_05 " +
			 " FROM EHF020406T0 " +
			 " WHERE 1=1 " +
			 " AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			 " AND EHF020406T0_04 = '"+this.sta_cur_day+"' " +  //未打卡日期(考勤日期)
			 " AND EHF020406T0_05 = '"+card_status+"' " +  //未打卡狀態
			 " AND EHF020406T0_07 = '"+this.sta_comp_id+"' " +  //公司代碼
			 " ORDER BY EHF020406T0_01 LIMIT 1 ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//已有未打卡記錄
				chk_flag = true;
			}else{
				
				//沒有未打卡記錄
				chk_flag = false;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 取得預排換班資料
	 * @param conn
	 * @param employee_id
	 * @param sta_cur_day
	 * @param ChangeClass
	 * @return
	 */
	private Map getChangeClassNo(Connection conn, String employee_id,
			String sta_cur_day, Map ChangeClass) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			sql = "" +
				  " SELECT EHF020104T0_02, EHF020104T0_04, EHF020104T1_03, EHF020104T1_04, EHF020104T1_05 " +
				  " FROM EHF020104T0 " +
				  " LEFT JOIN EHF020104T1 ON EHF020104T1_01 = EHF020104T0_01 AND EHF020104T1_06 = EHF020104T0_06 " +
				  " WHERE 1=1 " +
				  " AND EHF020104T0_02 = '"+sta_cur_day+"' " +
				  " AND EHF020104T1_03 = '"+employee_id+"' ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				ChangeClass.put("Change_DAY", rs.getString("EHF020104T0_02"));	//換班日期
				ChangeClass.put("OLD_CLASS_NO", rs.getInt("EHF020104T1_04"));	//舊班別序號
				ChangeClass.put("NEW_CLASS_NO", rs.getInt("EHF020104T1_05"));	//新班別序號
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ChangeClass;
	}
	
	/**
	 * 取得員工排班表
	 * @param conn
	 * @param employee_id
	 * @param sta_cur_day
	 * @param ChangeClass
	 * @return
	 */
	public Map getEmpSchedule(Connection conn, String employee_id,
			String sta_cur_day, Map ChangeClass) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			sql = "" +
				  " SELECT EHF010105T0_01, EHF010105T0_02, EHF010105T1_02, EHF010105T1_03, EHF010105T1_04, EHF010105T1_07 " +
				  " FROM EHF010105T0 " +
				  " LEFT JOIN EHF010105T1 ON EHF010105T1_01 = EHF010105T0_01 AND EHF010105T1_06 = EHF010105T0_06 " +
				  " WHERE 1=1 " +
				  " AND EHF010105T1_02 = '"+sta_cur_day+"' " +
				  " AND EHF010105T0_02 = '"+employee_id+"' ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				ChangeClass.put("Change_DAY", rs.getString("EHF010105T1_02"));	//換班日期
				ChangeClass.put("NEW_CLASS_NO", rs.getInt("EHF010105T1_04"));	//新班別序號
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ChangeClass;
	}

	/**
	 * 刪除所有考勤資料
	 * @param conn
	 * @param employee_id
	 * @param card_no
	 */
	private void delAllAttData(Connection conn, String employee_id, String card_no ){
		
		try{
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "";
			//刪除所有考勤資料
			sql = "" +
				  " DELETE FROM EHF020401T0 " +
				  " WHERE 1=1 ";
			
			//判斷 員工工號 與 門禁卡號
			if(!"".equals(employee_id) && employee_id != null && !"".equals(card_no) && card_no != null){
				sql += "" +
				       " AND ( " +
				       " EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
				       " OR EHF020401T0_02 = '"+card_no+"' ) ";  //感應卡號
			}else if(!"".equals(employee_id) && employee_id != null){
				sql += "" +
			       " AND ( " +
			       " EHF020401T0_02_EMP = '"+employee_id+"' ) ";  //員工工號
			}else if(!"".equals(card_no) && card_no != null){
				sql += "" +
			       " AND ( " +
			       " EHF020401T0_02 = '"+card_no+"' ) ";  //感應卡號
			}    
			sql += "" +
				   " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //考勤日期
				   " AND EHF020401T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			stmt.execute(sql);
			
			//刪除所有未打卡資料
			sql = "" +
			 	  " DELETE FROM EHF020406T0 " +
			 	  " WHERE 1=1 ";
			//判斷員工工號是否存在
			if(!"".equals(employee_id) && employee_id != null){ 	  
				sql += " AND EHF020406T0_01 = '"+employee_id+"' ";  //員工工號
			}
			sql += "" +
				   " AND EHF020406T0_04 = '"+this.sta_cur_day+"' " +  //未打卡日期
			 	   " AND EHF020406T0_07 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			stmt.execute(sql);
			
			stmt.close();
			/*
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			String ems_version = ems_tools.getSysParam(conn, this.sta_comp_id, "EMS_VERSION");
			
			//同步連動刪除 1. EHF020410T0 出勤記錄資料 2. EHF020802T0 加班出勤記錄
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			//ems_abatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			//ems_ovatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			if("1.6".equals(ems_version)){
				//移除出勤資料
				ems_abatt_handle.removeAttendanceRecord(
								 conn,
							     employee_id, 
							     this.sta_cur_day, 
							     "", 
							     this.sta_comp_id);
				
				//移除加班出勤資料
				ems_ovatt_handle.removeOvertimeAttendanceRecord(
								 conn,
							 	 employee_id, 
							 	 this.sta_cur_day, 
							 	 this.sta_comp_id);
			}*/
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
				//移除出勤資料
				ems_abatt_handle.removeAttendanceRecord(
								 conn,
							     employee_id, 
							     this.sta_cur_day, 
							     "", 
							     this.sta_comp_id);
				
				//移除加班出勤資料
				ems_ovatt_handle.removeOvertimeAttendanceRecord(
								 conn,
							 	 employee_id, 
							 	 this.sta_cur_day, 
							 	 this.sta_comp_id);
			stmt.close();
			
			
			//資料庫 COMMIT
			if(this.commit_flag){
				conn.commit();
			}
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			System.out.println("刪除所有考勤資料 delAllAttData()時，發生錯誤："+e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 刪除指定條件為日期、員工、感應卡號或打卡狀態的考勤資料
	 * @param conn
	 * @param employee_id
	 * @param card_no
	 */
	private void delAttDataByConditions(Connection conn, String employee_id, String card_no, String att_type ){
		
		try{
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "";
			//刪除考勤資料
			sql = "" +
				  " DELETE FROM EHF020401T0 " +
				  " WHERE 1=1 ";
			
			//判斷 員工工號 與 門禁卡號
			if(!"".equals(employee_id) && employee_id != null && !"".equals(card_no) && card_no != null){
				sql += "" +
				       " AND ( " +
				       " EHF020401T0_02_EMP = '"+employee_id+"' " +  //員工工號
				       " OR EHF020401T0_02 = '"+card_no+"' ) ";  //感應卡號
			}else if(!"".equals(employee_id) && employee_id != null){
				sql += "" +
			       " AND ( " +
			       " EHF020401T0_02_EMP = '"+employee_id+"' ) ";  //員工工號
			}else if(!"".equals(card_no) && card_no != null){
				sql += "" +
			       " AND ( " +
			       " EHF020401T0_02 = '"+card_no+"' ) ";  //感應卡號
			}
			//打卡狀態
			if(!"".equals(att_type) && att_type != null){
				sql += "" +
				   " AND ( " +
			       " EHF020401T0_08 = '"+att_type+"' ) ";  //打卡狀態
			}
			sql += "" +
				   " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //考勤日期
				   " AND EHF020401T0_11 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			stmt.execute(sql);
			
			//刪除未打卡資料
			sql = "" +
			 	  " DELETE FROM EHF020406T0 " +
			 	  " WHERE 1=1 ";
			//判斷員工工號是否存在
			if(!"".equals(employee_id) && employee_id != null){ 	  
				sql += " AND EHF020406T0_01 = '"+employee_id+"' ";  //員工工號
			}
			//打卡狀態
			if(!"".equals(att_type) && att_type != null){
				sql += "" +
				   " AND ( " +
			       " EHF020406T0_05 = '"+att_type+"' ) ";  //未打卡狀態
			}
			sql += "" +
				   " AND EHF020406T0_04 = '"+this.sta_cur_day+"' " +  //未打卡日期
			 	   " AND EHF020406T0_07 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			stmt.execute(sql);
			
			stmt.close();
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
				//移除出勤資料
				ems_abatt_handle.removeAttendanceRecord(
								 conn,
							     employee_id, 
							     this.sta_cur_day, 
							     att_type, 
							     this.sta_comp_id);
				
				//移除加班出勤資料
				ems_ovatt_handle.removeOvertimeAttendanceRecord(
								 conn,
							 	 employee_id, 
							 	 this.sta_cur_day, 
							 	 this.sta_comp_id);
				
				
				
			stmt.close();
			
			
			//資料庫 COMMIT
			if(this.commit_flag){
				conn.commit();
			}
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			System.out.println("刪除所有考勤資料 delAttData()時，發生錯誤："+e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 檢查感應卡片是否存在
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	private boolean chkEmpCardExist(Connection conn, String employee_id, String comp_id ,String sta_cur_day){
		
		boolean chk_flag = false;
		
		try{
			
			String sql = "" +
			" SELECT " +
			" EHF020403T1_01, EHF020403T1_02, " +
			" EHF020403T1_03, " +
			" EHF020403T1_04 " +
			" FROM EHF020403T1 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020403T1_06 = '"+comp_id+"' "+  //公司代碼
			" AND '"+sta_cur_day+"' BETWEEN DATE_FORMAT(EHF020403T1_04_START, '%Y/%m/%d') AND DATE_FORMAT(EHF020403T1_04_END, '%Y/%m/%d')";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			
			//判斷員工感應卡片設定是否存在
			if(rs.next()){
				chk_flag = true;  //感應卡片存在
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 封裝門禁資料
	 * @param rs
	 * @return
	 */
	protected EMS_AttendanceForm packAttData(ResultSet rs){
		
		EMS_AttendanceForm attform = null;
		
		try{
			//封裝門禁資料
			attform = new EMS_AttendanceForm();
			attform.setEHF020404T0_01(rs.getString("EHF020404T0_01"));  //刷卡資料序號
			attform.setEHF020404T0_02(rs.getString("EHF020404T0_02"));  //門禁系統號
			attform.setEHF020404T0_03(rs.getString("EHF020404T0_03"));  //感應卡號
			attform.setEHF020404T0_04(rs.getString("EHF020404T0_04"));  //打卡日期
			attform.setEHF020404T0_05(rs.getString("EHF020404T0_05"));  //打卡時間			
			attform.setEHF020404T0_06(rs.getString("EHF020404T0_06"));  //打卡日期時間(datetime)
			attform.setEHF020404T0_07(rs.getString("EHF020404T0_07"));  //門禁機器代碼
			attform.setEHF020404T0_08(rs.getString("EHF020404T0_08"));  //動作代碼
			attform.setEHF020404T0_09(rs.getString("EHF020404T0_09"));  //公司代碼
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return attform;
	}
	
	/**
	 * 檢核時間是否正常可產考勤資料
	 * @param cal
	 * @param classMap
	 * @param cal_type
	 * @return
	 */
	protected boolean chkTimeIsOK(Connection conn, Calendar cal, Map classMap, int cal_type){
		
		boolean chk_flag = false;
		Calendar com_cal = null;
		
		try{
			switch(cal_type){
			
			//判斷是否已超過上午下班時間
			case 7:
				com_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
							   classMap, cal_type);  //班別設定的上午下班時間(Calendar)
				
				if(cal.before(com_cal)){
					//表示未超過中午下班時間
					chk_flag = false;
				}else{
					//表示超過中午下班時間
					chk_flag = true;
				}
				break;
			
			//判斷是否已超過下午上班時間
			case 8:
				com_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
							   classMap, cal_type);  //班別設定的下午上班時間(Calendar)
				
				if(cal.before(com_cal)){
					//表示未超過下午上班時間
					chk_flag = false;
				}else{
					//表示超過下午上班時間
					chk_flag = true;
				}
				break;
			
			//判斷是否已超過下班時間
			case 2:
				com_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
							   classMap, cal_type);  //班別設定的下班時間(Calendar)
				
				if(cal.before(com_cal)){
					//表示未超過下班時間
					chk_flag = false;
				}else{
					//表示超過下班時間
					chk_flag = true;
				}
				break;
			
			}
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
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
				
				ehf010100m0.setEHF020403T0_01(rs.getString("EHF020403T0_01"));  //員工工號
				ehf010100m0.setEHF020403T0_02(rs.getString("EHF020403T0_02"));  //部門代號				
				ehf010100m0.setEHF020403T0_05(rs.getString("EHF020403T0_05"));  //是否計算考勤				
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			try {
				throw new Exception("取得感應卡設定資料時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return ehf010100m0;
	}
	
	/**
	 * 計算遲到或早退的秒數
	 * @param TYPE  0:日期差值秒數的絕對值  1:遲到,  2:早退
	 * @param user_sec
	 * @param classMap
	 * @return
	 */
	private int CaculateLateSecs(int TYPE, Calendar user_cal, Calendar com_cal ){
		
		int diff_sec = 0;
		
		try{
			
			switch(TYPE){
			
			//取兩Calendar之間的差值秒數 in 絕對值
			case 0:
					diff_sec = (int)((user_cal.getTimeInMillis() - com_cal.getTimeInMillis())/1000);
					diff_sec = Math.abs(diff_sec);
					break;
			//遲到
			case 1:
					diff_sec = (int)((user_cal.getTimeInMillis() - com_cal.getTimeInMillis())/1000);
					break;
			//早退
			case 2:
				diff_sec = (int)((com_cal.getTimeInMillis() - user_cal.getTimeInMillis())/1000);
					break;
					
			default:
				
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return diff_sec;
	}
	
	/**
	 * 檢查若考勤資料與前一狀態相同
	 * 則尋找是否有下一筆資料(rs.next)
	 * 若有 -> 使用該筆資料作為考勤資料 true
	 * 若無 -> 產生未打卡資料  false
	 * @param attkey
	 * @param com_attkey
	 * @param rs
	 * @return
	 */
	protected boolean compareAttKeyIsSame( String attkey, String com_attkey, ResultSet rs ){
		
		boolean chk_flag = false;
		
		try{
			
			if(!attkey.equals(com_attkey)){
				chk_flag = true;
			}else{
				if(rs.next()){
					chk_flag = true;
				}else{
					chk_flag = false;
				}
				rs.previous();
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 取得使用者的考勤時間
	 * @param conn
	 * @param employee_id
	 * @param class_no
	 * @return
	 */
	private Map getUserInAttendanceTime( Connection conn, String employee_id, int type, Map classMap){
		
		Map user_Map = new HashMap();
		
		try{
			if(type == 0){
				return null;
			}
			
			String sql = "" +
			 " SELECT EHF020401T0_01, " +
			 " EHF020401T0_08, EHF020401T0_08_FLE, " +
			 " DATE_FORMAT(EHF020401T0_07, '%Y/%m/%d %H:%i:%s') AS CARD_DATETIME " +
			 " FROM EHF020401T0 " +
			 " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
			 " WHERE 1=1 " +
			 " AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			 " AND EHF020401T0_05 = '"+this.sta_cur_day+"' " +  //計算考勤的日期
			 " AND EHF020401T0_08 = '"+type+"' " +  //打卡狀態 
			 " AND EHF020401T0_11 = '"+this.sta_comp_id+"' " +  //公司代碼
			 " ORDER BY EHF020401T0_07 LIMIT 1 ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				user_Map.put("ATT_KEY", rs.getString("EHF020401T0_01"));  //出勤系統碼
				user_Map.put("ATT_TYPE", rs.getString("EHF020401T0_08"));  //打卡狀態
				user_Map.put("FLEX_SEC", rs.getInt("EHF020401T0_08_FLE"));  //計算上班使用的彈性時間
				user_Map.put("DATETIME", rs.getString("CARD_DATETIME"));  //刷卡時間

			}else{
				user_Map = this.getUserInAttendanceTime(conn,
														employee_id,
														this.getLastAttendanceType(type, classMap),
														classMap);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return user_Map;
	}
	
	protected int getLastAttendanceType( int cur_type, Map classMap ){
		
		int last_type = 0;
		
		try{
			switch(cur_type){
			
			//上班
			case 1:
				//上班沒有上一個考勤狀態
				last_type = 0;
			break;
			
			
			//下班
			case 2:
				//判斷是否有中午打卡
				if(tools.hasNoonRecord(classMap)){
					//有記錄中午打卡
					//下午上班
					last_type = 8;
				}else{
					//沒有記錄中午打卡
					//上班
					last_type = 1;
				}
			break;
			
			//上午下班
			case 7:
				//上班
				last_type = 1;
			break;
			
			//下午上班
			case 8:
				//上午下班
				last_type = 7;
			break;
				
			//加班上班
			case 5:
				//下班
				last_type = 2;
			break;
			
			//加班下班
			case 6:
				//加班上班
				last_type = 5;
			break;	
			
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return last_type;
	}
	
	/**
	 * 刪除所有考勤資料(提供外部元件呼叫)
	 * @param conn
	 * @param employee_id
	 * @param card_no
	 */
	public void delAllAttDataForm(String employee_id ){
		try{
			Connection conn2 = null;
			//建立資料庫連線
			try {
				conn2 = tools.getConnection("SPOS");
			} catch (SQLException e2) {
				e2.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			delAllAttData(conn2, employee_id, "");
			
			conn2.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 刪除指定考勤資料(提供外部元件呼叫)
	 * @param conn
	 * @param employee_id
	 * @param card_no
	 */
	public void delAttDataForm(Connection conn, String employee_id, String card_no, String att_type ){
		try{
			delAttDataByConditions(conn, employee_id, card_no, att_type);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 重新結算員工加班出勤資料
	 * @param conn
	 * @param employee_id
	 */
	public void reDoGenerateOvertimeAttendaceRecord(Connection conn, String employee_id){
		
		try{
			//取得員工考勤日期班別資訊
			Map tempClassMap = null;
			//建立員工排班表元件
			EMS_Work_Schedule_Table ems_work_schedule = new EMS_Work_Schedule_Table("EMS系統", this.sta_comp_id);
			
			//取得並設定員工排班表資料
			this.cur_emp_work_schedule = ems_work_schedule.getOneDayInSchedule(conn, employee_id, this.sta_cur_day);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//依據排班表設定員工指定日期的班別資料
			if(this.cur_emp_work_schedule.getSchedule_no() != -1){
				tempClassMap = hr_tools.getEmpClassByNo( conn, this.sta_comp_id, this.cur_emp_work_schedule.getClass_no());
				//避免 class_no 不正確, 若以 class_no 找不到 classMap 則以員工當前班別作為 classMap
				if(tempClassMap.isEmpty()){
					AuthorizedBean authbean = new AuthorizedBean();
					authbean.setUserId(this.sta_user_id);
					authbean.setEmployeeID(employee_id);
					authbean.setCompId(this.sta_comp_id);
					tempClassMap = hr_tools.getEmpClass(conn, authbean);
				}
			}else{
				//以員工當前班別作為 classMap
				AuthorizedBean authbean = new AuthorizedBean();
				authbean.setUserId(this.sta_user_id);
				authbean.setEmployeeID(employee_id);
				authbean.setCompId(this.sta_comp_id);
				tempClassMap = hr_tools.getEmpClass(conn, authbean);
			}
			
			hr_tools.close();
			
			Calendar class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
										  tempClassMap, 2);  //班別設定的下班時間(Calendar)
			
			Calendar class_ov_in_cal = null;  //班別設定的加班上班時間(Calendar)
			Calendar class_ov_out_cal = null;  //班別設定的加班下班時間(Calendar)
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			//ems_ovatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			boolean ov_flag = false;
			Map<String, String> ov_map = null;
			
			//取得考勤日期是否為假日資訊
			boolean ha_flag = this.isHoliday(conn, this.sta_cur_day, this.sta_comp_id, (String)tempClassMap.get("WORK_CLASS_HOLIDAY"));
			
			//判斷是否為假日
			if(ha_flag){
				//處理假日加班
				ov_map = ems_ovatt_handle.findHaOvertimeData(
						 conn,
					 	 this.sta_cur_day, 
					 	 employee_id,
					 	 this.sta_comp_id);
			
				ov_flag = Boolean.parseBoolean(ov_map.get("HA_OVERTIME_SWITCH"));
				if(ov_flag){
					class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("HA_OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
					class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("HA_OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
				}
			}else{
				//處理下班後加班
				ov_map = ems_ovatt_handle.findOvertimeData(
						 conn,
						 this.sta_cur_day, 
						 employee_id, 
						 this.CalendarToString(class_out_cal), 
						 this.sta_comp_id);
				
				ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
				if(ov_flag){
					class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
					class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
				}
			}
			
			/*
			ov_map = ems_ovatt_handle.findOvertimeData(
									  this.sta_cur_day, 
									  employee_id, 
									  this.CalendarToString(class_out_cal), 
									  this.sta_comp_id);

			ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
			*/
			
			//有加班資料才進行處理
			if (ov_flag) {
				//class_ov_in_cal = this.datetimeToCalendar(ov_map.get("OVERTIME_START")); // 加班單設定的加班開始時間(Calendar)
				//class_ov_out_cal = this.datetimeToCalendar(ov_map.get("OVERTIME_END")); // 加班單設定的加班結束時間(Calendar)
				
				//判斷是否為假日
				if(ha_flag){
					
					//取得系統參數 資料擷取的結束時間
					//依據系統參數設定加班下班的可擷取的門禁資料時間
					int att_limit_ov_end_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_OV_END_HOUR")));
					
					Calendar before_class_ov_in_cal = (Calendar) class_ov_in_cal.clone();
					//將加班上班時間的前一個小時作為可擷取的門禁資料
					before_class_ov_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_ov_end_hour);
					
					//假日加班的結算
					//若是假日加班必須用加班上班時間作為結算邊界
					Map<String, String> ov_record_map = 
						ems_ovatt_handle.settlementOvertimeAttendanceRecord(
						conn,
						employee_id, 
						ov_map.get("OV_RECORD_UID"), 
						this.sta_cur_day, 
						this.CalendarToString(before_class_ov_in_cal), 
						this.sta_comp_id);
				}else{
					//下班後加班的結算
					//若是下班後加班必須用班別下班時間作為結算邊界
					Map<String, String> ov_record_map = 
						ems_ovatt_handle.settlementOvertimeAttendanceRecord(
						conn,
						employee_id, 
						ov_map.get("OV_RECORD_UID"), 
						this.sta_cur_day, 
						this.CalendarToString(class_out_cal), 
						this.sta_comp_id);
				}
				
				/*
				//結算加班出勤資料
				Map<String, String> ov_record_map = 
				ems_ovatt_handle.settlementOvertimeAttendanceRecord(
								 employee_id, 
								 ov_map.get("OV_RECORD_UID"), 
								 this.sta_cur_day, 
								 this.CalendarToString(class_out_cal), 
								 this.sta_comp_id);
				*/
				
			}
			
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 檢查中午考勤資料是否衝突
	 * @param conn
	 * @param range_class_siesta_in_cal
	 * @param range_class_siesta_out_cal
	 * @param employee_id
	 * @param com_rs
	 * @param card_number 
	 * @return
	 */
	protected boolean chkNoonDataConflict( Connection conn, Calendar range_class_siesta_in_cal, Calendar range_class_siesta_out_cal, 
										   String employee_id, ResultSet com_rs, String card_number ){
		
		boolean chk_flag = false;
		
		try{
			
			
			
			 //查詢下午上班考勤正常狀況
			 String sql = "" +
			 this.getAttSelectSQL("SIESTA_OUT_TIME",
 								  "SIESTA_OUT_CAL_DATE",
 								  "SIESTA_OUT_CAL_TIME",
 								  " <= '"+this.CalendarToString(range_class_siesta_out_cal)+"' ",
 								  " > '"+this.CalendarToString(range_class_siesta_in_cal)+"' ",
 								  employee_id,
 								  this.sta_comp_id,
 								  " ORDER BY EHF020404T0_06 DESC ",
 								  1,card_number,"");
			 
			 Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 ResultSet rs = stmt.executeQuery(sql);
			
			 if(rs.next()){
				 //有取得下午上班考勤正常狀況
				 //判斷下午上班考勤正常資料是否等於上午下班考勤正常資料
				 if((rs.getString("EHF020404T0_02")).equals(com_rs.getString("EHF020404T0_02"))){
					 //中午資料發生衝突
					 //以最接近的時間做判斷
					 Calendar cal = this.datetimeToCalendar(rs.getString("SIESTA_OUT_CAL_DATE"),
							  		    rs.getString("SIESTA_OUT_CAL_TIME"));  //上午下班與下午上班打卡時間(Calendar)
					 
					 if(tools.getDiffMilliWithABS(cal, range_class_siesta_out_cal) - tools.getDiffMilliWithABS(cal, range_class_siesta_in_cal) > 0){
						 //表示時間較接近上午下班時間
						 //表示此衝突的考勤資料, 需做為上午下班的正常考勤資料
						 chk_flag = true;
					 }else{
						 //表示時間較接近下午上班時間
						 //表示此衝突的考勤資料, 需做為下午上班的正常考勤資料
						 
						 //判斷上午下班的考勤資料是否有別筆正常資料或異常資料
						 //若有則取其為上午下班考勤資料
						 //若沒有則表示 未打卡
						 if(com_rs.next()){
							 chk_flag = true;  //表示產生考勤
						 }else{
							 chk_flag = false;  //表示產生未打卡
						 } 
						 com_rs.previous();
					 }
				 }else{
					 //未發生衝突
					 //可以產考勤資料
					 chk_flag = true;
				 }
				 
			 }else{
				 //沒有取得下午上班考勤正常狀況
				 //表示下午上班異常則可直接產上午下班資料
				 chk_flag = true;
			 }
			 
			 rs.close();
			 stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 判斷是否有存在的假日加班門禁資料
	 * @param conn
	 * @param classMap
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected boolean chkRealHaOvertimeExist( Connection conn, Map classMap, String employee_id, String comp_id ){
		
		boolean chk_flag = false;

		try{
			//班別設定的加班上班時間(Calendar)
			Calendar class_ov_in_cal = null;  
			//班別設定的加班下班時間(Calendar)
			Calendar class_ov_out_cal = null;
			
			//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (下班後加班)
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			
			boolean ha_ov_flag = false;
			Map<String, String> ha_ov_map = null;
			
			ha_ov_map = ems_ovatt_handle.findHaOvertimeData(
				    conn,
				 	this.sta_cur_day, 
				 	employee_id,
				 	this.sta_comp_id);
		
			ha_ov_flag = Boolean.parseBoolean(ha_ov_map.get("HA_OVERTIME_SWITCH"));
			if(ha_ov_flag){
				class_ov_in_cal = tools.datetimeToCalendar(ha_ov_map.get("HA_OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
				class_ov_out_cal = tools.datetimeToCalendar(ha_ov_map.get("HA_OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
				
				//班別上班時間(處理用)
				Calendar before_class_ov_in_cal = (Calendar) class_ov_in_cal.clone();
				//班別下班時間(處理用)
				Calendar after_class_ov_out_cal = (Calendar) class_ov_out_cal.clone();
				
				//取得系統參數 資料擷取的結束時間
				//依據系統參數設定加班下班的可擷取的門禁資料時間
				int att_limit_ov_end_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_OV_END_HOUR")));
				
				//將加班上班時間的前一個小時作為可擷取的門禁資料
				before_class_ov_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_ov_end_hour);
				//將加班下班時間的後一個小時作為可擷取的門禁資料
				after_class_ov_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_ov_end_hour);
				
				//檢查是否有加班門禁資料
				chk_flag = this.chkRealOvertimeExist( conn, 
													  before_class_ov_in_cal,
													  after_class_ov_out_cal,
										   			  employee_id,
										   			  comp_id,
										   			  1);
			}else{
				chk_flag = false;
			}
			
			
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 判斷是否有存在的假日全場加班門禁資料(提供外部元件呼叫)
	 * @param conn
	 * @param employee_id
	 * @param card_no
	 */
	public boolean chkRealHaOverallOvertimeExistForm(Connection conn, Calendar before_class_ov_in_cal, Calendar after_class_ov_out_cal, 
			String employee_id, String comp_id, int limit_value ){
		
		boolean chk_flag = false;
		
		try{
			chk_flag = this.chkRealOvertimeExist( conn, before_class_ov_in_cal, after_class_ov_out_cal, employee_id, comp_id, limit_value );
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	
	
	/**
	 * 判斷加班資料是否存在
	 * @param conn
	 * @param classMap
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected boolean chkRealOvertimeExist( Connection conn, Map classMap, String employee_id, String comp_id ){
		
		boolean chk_flag = false;

		try{
			//取得下班考勤資訊
			Map userMap = this.getUserInAttendanceTime(conn, employee_id, 2, classMap);
			
			//下班考勤參數
			Calendar class_out_cal = null;
			
			//檢查 userMap 是否有取得的考勤資料
			if( (userMap == null || !userMap.containsKey("DATETIME")) ){
				//若不存在考勤資料, 則不需要產生加班考勤資料
				//chk_flag = false;
				//return chk_flag;
				
				//若是抓不到下班考勤資料則使用班別下班時間作為擷取邊界
				class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
									 classMap, 2);  //班別設定的下班時間(Calendar)
				
			}else{
				//判斷下班考勤的ATT_TYPE ==> if != '2' --> 表示下班未打卡
				if(userMap.containsKey("ATT_TYPE") && "2".equals(userMap.containsKey("ATT_TYPE"))){
					//使用取得的下班考勤資料作為擷取邊界
					class_out_cal = tools.covertStringToCalendar((String)userMap.get("DATETIME"), "yyyy/MM/dd HH:mm:ss");
				}else{
					//使用班別下班時間作為擷取邊界
					class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
										 classMap, 2);  //班別設定的下班時間(Calendar)
				}
				
			}
			
			
			//設定實際下班考勤資料的Calendar
			//Calendar class_out_cal = ems_tools.covertStringToCalendar((String)userMap.get("DATETIME"), "yyyy/MM/dd HH:mm:ss");
			
			//為當前下班考勤加上一秒, 避免時間區間邊界重疊的問題
			class_out_cal.add(Calendar.SECOND, 1);
			
			//班別設定的加班上班時間(Calendar)
			Calendar class_ov_in_cal = null; 
			//班別設定的加班下班時間(Calendar)
			Calendar class_ov_out_cal = null;  
			
			//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (下班後加班)
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			//ems_ovatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			boolean ov_flag = false;
			Map<String, String> ov_map = null;
							
			ov_map = ems_ovatt_handle.findOvertimeData(
					 conn,
					 this.sta_cur_day, 
					 employee_id, 
					 this.CalendarToString(class_out_cal), 
					 this.sta_comp_id);
				
			ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
			if(ov_flag){
				class_ov_in_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
				class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
				
				//班別下班時間(處理用)
				Calendar after_class_ov_out_cal = (Calendar) class_ov_out_cal.clone();
				
				//取得系統參數 資料擷取的結束時間
				//依據系統參數設定加班下班的可擷取的門禁資料時間
				int att_limit_ov_end_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_OV_END_HOUR")));
				
				//將加班下班時間的後一個小時作為可擷取的門禁資料
				after_class_ov_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_ov_end_hour);
				
				//檢查是否有加班門禁資料
				chk_flag = this.chkRealOvertimeExist( conn, 
														class_ov_in_cal,
										   			  after_class_ov_out_cal,
										   			  employee_id,
										   			  comp_id,
										   			  1);
				
				
			}		else{
				chk_flag = false;
			}
			
			
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			chk_flag = false;
			System.out.println(this.sta_cur_day);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 判斷全場加班資料是否存在(提供外部元件呼叫)
	 * @param conn
	 * @param employee_id
	 * @param card_no
	 */
	public boolean chkRealOverallOvertimeExistForm(Connection conn, Calendar class_ov_in_cal, Calendar after_class_ov_out_cal, 
			String employee_id, String comp_id, int limit_value ){
		
		boolean chk_flag = false;
		
		try{
			chk_flag = this.chkRealOvertimeExist( conn, class_ov_in_cal, after_class_ov_out_cal, employee_id, comp_id, limit_value );
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	
	
	/**
	 * 判斷加班資料是否存在並回傳找到幾筆資料
	 * @param conn
	 * @param start_cal
	 * @param end_cal
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected Map chkRealOvertimeExist( Connection conn, Calendar start_cal, Calendar end_cal, String employee_id, String comp_id ){
		
		Map returnMap = new HashMap();
		
		try{
			int rows = 0;
			String attkey = "";
			String sql = "";
			
			//取得加班上班後判斷是否有加班的緩衝參數 (以分鐘計算)
			int att_de_ov_buff_minute = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_DE_OV_BUFF_MINUTE")));
			//加班上班時間開始後, 過半小時之後若還有打卡資料表示有做加班下班(早退或正常皆算)的動作, 才表示有加班
			//避免與延後下班衝突, 造成資料錯誤!!
			Calendar range_start_cal = (Calendar) start_cal.clone();
			range_start_cal.add(Calendar.MINUTE, att_de_ov_buff_minute);
			
			/*
			sql += this.getAttSelectSQL("COL1", "COL2", "COL3",
										" >= '"+this.CalendarToString(start_cal)+"' ",
										" <= '"+this.CalendarToString(end_cal)+"' ",
										employee_id,
										comp_id,
										"",
										20);
			*/
			//先取得可用的感應卡號
			String card_number=this.card_number(conn,employee_id,this.CalendarToString(start_cal));
			
			sql += this.getAttSelectSQL("COL1", "COL2", "COL3",
					" >= '"+this.CalendarToString(range_start_cal)+"' ",
					" <= '"+this.CalendarToString(end_cal)+"' ",
					employee_id,
					comp_id,
					"",
					20,card_number,"");
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			rs.last();
			rows = rs.getRow();
			
			if( rows == 1 ){
				rs.beforeFirst();
				rs.next();
				attkey = rs.getString("EHF020404T0_02");
			}
			

			returnMap.put("RETURN_VALUE", rows);
			returnMap.put("ATTKEY", attkey);

			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return returnMap;
	}
	
	/**
	 * 判斷是否有存在的加班門禁資料
	 * @param conn
	 * @param start_cal
	 * @param end_cal
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected boolean chkRealOvertimeExist( Connection conn, Calendar start_cal, Calendar end_cal, String employee_id, String comp_id, int limit_value ){
		
		boolean chk_flag = false;
		
		try{
			
			String sql = "";
			
			//取得加班上班後判斷是否有加班的緩衝參數 (以分鐘計算)
			int att_de_ov_buff_minute = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_DE_OV_BUFF_MINUTE")));
			//加班上班時間開始後, 過半小時之後若還有打卡資料表示有做加班下班(早退或正常皆算)的動作, 才表示有加班
			//避免與延後下班衝突, 造成資料錯誤!!
			Calendar range_start_cal = (Calendar) start_cal.clone();
			range_start_cal.add(Calendar.MINUTE, att_de_ov_buff_minute);
			
			/*
			sql += this.getAttSelectSQL("COL1", "COL2", "COL3",
										" >= '"+this.CalendarToString(start_cal)+"' ",
										" <= '"+this.CalendarToString(end_cal)+"' ",
										employee_id,
										comp_id,
										"",
										20);
			*/
			
			//先取得可用的感應卡號
			String card_number=this.card_number(conn,employee_id,this.CalendarToString(start_cal));
			
			
			sql += this.getAttSelectSQL("COL1", "COL2", "COL3",
					" >= '"+this.CalendarToString(range_start_cal)+"' ",
					" <= '"+this.CalendarToString(end_cal)+"' ",
					employee_id,
					comp_id,
					"",
					20,card_number,"");
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			rs.last();
			
			if(rs.getRow() >= limit_value){
				chk_flag = true;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 檢查晚上考勤資料是否衝突
	 * @param conn
	 * @param range_class_out_cal
	 * @param range_class_ov_in_cal
	 * @param employee_id
	 * @param com_rs
	 * @return
	 */
	protected boolean chkNightDataConflict( Connection conn, Calendar range_class_out_cal, 
										    Calendar range_class_ov_in_cal, Calendar after_class_ov_out_cal,
										    String employee_id, ResultSet com_rs, boolean overtime_flag ,String card_number){
		
		boolean chk_flag = false;
		
		try{
			
			//判斷是否有設定加班
			if(!overtime_flag){
				//若沒有設定加班則不需要考慮衝突問題
				chk_flag = true;
				return chk_flag;
			}
			
			String sql = "";
			//查詢加班上班正常情況
			sql += this.getAttSelectSQL("OV_IN_TIME",
										"OV_IN_CAL_DATE",
										"OV_IN_CAL_TIME",
										" > '"+this.CalendarToString(range_class_out_cal)+"' ",
										" <= '"+this.CalendarToString(range_class_ov_in_cal)+"' ",
										employee_id,
										this.sta_comp_id,
										" ORDER BY EHF020404T0_06 DESC ",
										1,card_number,"");
			 
			 Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 ResultSet rs = stmt.executeQuery(sql);
			
			 if(rs.next()){
				 //有取得加班上班考勤正常狀況
				 //判斷下班考勤正常資料是否等於加班上班考勤正常資料
				 if((rs.getString("EHF020404T0_02")).equals(com_rs.getString("EHF020404T0_02"))){
					 //晚上資料發生衝突
					 //以最接近的時間做判斷
					 Calendar cal = this.datetimeToCalendar(rs.getString("OV_IN_CAL_DATE"),
							  		    rs.getString("OV_IN_CAL_TIME"));  //下班與加班上班打卡時間(Calendar)
					 
					 if(tools.getDiffMilliWithABS(cal, range_class_ov_in_cal) - tools.getDiffMilliWithABS(cal, range_class_out_cal) > 0){
						 //表示時間較接近下班時間
						 //表示此衝突的考勤資料, 需做為下班的正常考勤資料
						 chk_flag = true;
					 }else{
						 //表示時間較接近加班上班時間
						 //表示此衝突的考勤資料, 需做為加班上班的正常考勤資料
						 
						 //先判斷是否有實際加班資料 real_overtime_data
						 //判斷下班的考勤資料是否有別筆正常資料或異常資料
						 //若有則取其為下班考勤資料
						 //若沒有則表示 未打卡
						 if(this.chkRealOvertimeExistInConflict( conn, rs.getString("EHF020404T0_02"), range_class_out_cal,
								 								 after_class_ov_out_cal, employee_id, this.sta_comp_id, 1,card_number)){
							 if(com_rs.next()){
								 chk_flag = true;  //表示產生考勤
							 }else{
								 com_rs.previous();  //往上移一筆資料
								 chk_flag = false;  //表示產生未打卡
							 }
						 }else{
							 chk_flag = true;  //表示產生考勤
						 }
					 }
				 }else{
					 //未發生衝突
					 //可以產生考勤資料
					 chk_flag = true;
				 }
				 
			 }else{
				 //沒有取得加班上班考勤正常狀況
				 //表示加班上班異常則可直接產下班資料
				 chk_flag = true;
			 }
			 
			 rs.close();
			 stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 判斷晚上資料衝突時, 檢查是否有實際加班資料
	 * @param conn
	 * @param attkey
	 * @param start_cal
	 * @param end_cal
	 * @param employee_id
	 * @param comp_id
	 * @param limit_value
	 * @return
	 */
	protected boolean chkRealOvertimeExistInConflict( Connection conn, String attkey, Calendar start_cal, Calendar end_cal,
													  String employee_id, String comp_id, int limit_value ,String card_number){
		
		boolean chk_flag = false;
		
		try{
			int totalrow = 0;
			String sql = "";
			
			sql += this.getAttSelectSQL("COL1", "COL2", "COL3",
										" >= '"+this.CalendarToString(start_cal)+"' ",
										" <= '"+this.CalendarToString(end_cal)+"' ",
										employee_id,
										comp_id,
										"",
										20,card_number,"");
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			rs.last();
			totalrow = rs.getRow();
			rs.beforeFirst();
			
			while(rs.next()){
				//判斷是否是當前的衝突考勤資料
				if(attkey.equals(rs.getString("EHF020404T0_02"))){
					//若是當前衝突資料, 則扣除該筆資料
					totalrow = totalrow - 1;
				}
			}
			
			//修正過後的資料筆數若是大於等於 limit_value則表示實際加班資料存在
			if( totalrow >= limit_value ){
				chk_flag = true;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 對下班未打卡的情況做額外的判斷
	 * 檢查是否有下面的情況
	 * 			 1. 延後下班
	 * 			 2. 加班
	 * @param conn
	 * @param class_ov_in_cal
	 * @param after_class_ov_out_cal
	 * @param employee_id
	 * @param classMap
	 */
	protected void chkOvertimeNeed( Connection conn, Calendar class_ov_in_cal, Calendar after_class_ov_out_cal, String employee_id,
									Map classMap, boolean conflict_flag ,String EHF020404T0_01){
		
		try{
			Calendar class_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
										 classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
											    classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
												 classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
										  classMap, 2);  //班別設定的下班時間(Calendar)
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			//ems_abatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			//ems_ovatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			//延後下班在新的模式採用加班記錄做認定, 所以不用取得延後下班的設定資訊
			//直接以加班單認定做處理
			boolean late_ov_flag = false;			
			//直接以加班單認定做處理
			late_ov_flag = false;				
			
			
			Map<String, String> abatt_map = null;			
			// 使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", this.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			
			
			//判斷中午上下班是否要打卡
			boolean siesta_flag = tools.hasNoonRecord(classMap);
			
			//判斷若加班上班時間後有資料, 表示有加班上班
			Map chkMap = this.chkRealOvertimeExist(conn, class_ov_in_cal, after_class_ov_out_cal, employee_id, this.sta_comp_id);
			
			if( ((Integer)chkMap.get("RETURN_VALUE")) == 1 ){
				//判斷是否有設定延後下班  && 是否有衝突產生
				if(late_ov_flag && conflict_flag ){
					//表示延後下班, 且超過了加班上班時間才打卡, 取此筆資料作為延後下班的考勤資料
					//因為延後下班所以沒有加班, 執行加班考勤產生程式
					//新增延後下班考勤資料( PS:班別設定中, 延後下班時數必須超過上班彈性時數, 避免發生程式邏輯判斷例外 )
					//取得該筆考勤資料
					EMS_AttendanceForm attform = this.getAttDataByAttKey(conn, (String)chkMap.get("ATTKEY"), this.sta_comp_id);
					//產生正常下班資料(延後下班)
					this.InsertATTData(conn, 2, false, 1, false, 0, classMap, attform);
										
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					this.sta_cur_day, "2", 
					0, 
					abatt_map, 
					this.sta_comp_id,1,"");					
					
				}else{
					//表示存在加班資料
					//新增下班未打卡資料並執行加班考勤產生程式
					//下班未打卡
					this.InsertUnATTData(conn, 2, 4, classMap, employee_id,EHF020404T0_01);
										
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					this.sta_cur_day, "2", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					siesta_flag==true?(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal ))
								 	 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
								 	   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
					abatt_map, 
					this.sta_comp_id,4,"");
										
				}
			}else if( ((Integer)chkMap.get("RETURN_VALUE")) >= 2 ){
				//表示存在加班資料
				//新增下班未打卡資料並執行加班考勤產生程式
				//下班未打卡
				this.InsertUnATTData(conn, 2, 4, classMap, employee_id,EHF020404T0_01);
								
				//建立出勤記錄
				ems_abatt_handle.putAttendanceRecord(
				conn,
				employee_id,
				String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
				this.sta_cur_day, "2", 
				//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
				siesta_flag==true?(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal ))
					 	     	 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
					 	     	   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
				abatt_map, 
				this.sta_comp_id,4,"");				
				
			}else{
				//表示沒有存在加班資料
				//新增下班未打卡資料並執行加班考勤產生程式
				//下班未打卡
				this.InsertUnATTData(conn, 2, 4, classMap, employee_id,EHF020404T0_01);
								
				//建立出勤記錄
				ems_abatt_handle.putAttendanceRecord(
				conn,
				employee_id,
				String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
				this.sta_cur_day, "2", 
				//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
				siesta_flag==true?(this.CaculateLateSecs( 0, class_siesta_out_cal, class_out_cal ))
					 	     	 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
					 	     	   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
				abatt_map, 
				this.sta_comp_id,4,"");
							
			}
			
			//只要班別有設定加班資料, 不論正常或是異常,都必須產生加班資料
			//加班考勤產生程式
			//--------------------------------------------------------
			this.generateOverTimeData(conn, employee_id, classMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 依據門禁系統號取得門禁資料
	 * @param conn
	 * @param attkey
	 * @param comp_id
	 * @return
	 */
	protected EMS_AttendanceForm getAttDataByAttKey( Connection conn, String attkey, String comp_id ){
		
		EMS_AttendanceForm attform = null;
		
		try{
			 String sql =
			 " SELECT * FROM ( " +
	  		 " SELECT " +
	  		 " IFNULL(EHF020404T0_01, '') AS EHF020404T0_01, " +
	  		 " IFNULL(EHF020404T0_02, '') AS EHF020404T0_02, " +
	  		 " IFNULL(EHF020404T0_03, '') AS EHF020404T0_03, " +
	  		 " IFNULL(EHF020404T0_04, '') AS EHF020404T0_04, " +
	  		 " IFNULL(EHF020404T0_05, '') AS EHF020404T0_05, " +
	  		 " IFNULL(EHF020404T0_06, '') AS EHF020404T0_06, " +
	  		 " IFNULL(EHF020404T0_07, '') AS EHF020404T0_07, " +
	  		 " IFNULL(EHF020404T0_08, '') AS EHF020404T0_08, " +
	  		 " IFNULL(EHF020404T0_09, '') AS EHF020404T0_09 " +
			 " FROM EHF020404T0 " +
			 " WHERE 1=1 " +
			 " AND EHF020404T0_02 = '"+attkey+"' " +  //門禁系統號碼
			 " AND EHF020404T0_09 = '"+comp_id+"' " +  //公司代碼
			 " ) tableA " +
			 " WHERE 1=1 " ;
			
			 Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			 ResultSet rs = stmt.executeQuery(sql);
			 
			 if(rs.next()){
				 attform = this.packAttData(rs);
			 }
			 
			 rs.close();
			 stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return attform;
	}
	
	/**
	 * 處理加班設定的是否記錄下班與加班上班刷卡資料問題
	 * @param conn
	 * @param classMap
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected boolean handleOVTRecordProcess( Connection conn, Map classMap, String employee_id, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			Calendar class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
										  classMap, 2);  //班別設定的下班時間(Calendar)
			//為當前下班考勤加上一秒, 避免時間區間邊界重疊的問題
			class_out_cal.add(Calendar.SECOND, 1);
			
			//因為EMS產品套裝功能調整 -> 加班改抓取加班記錄(EHF020801T0)
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			//加班開始時間與加班結束時間由加班記錄(EHF020801T0)提供
			
			//班別設定的加班上班時間(Calendar)
			Calendar class_ov_in_cal = null; 
			//班別設定的加班下班時間(Calendar)
			Calendar class_ov_out_cal = null;  
			
			//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (下班後加班)
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			String ems_version = tools.getSysParam(conn, this.sta_comp_id, "EMS_VERSION");
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			//ems_abatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			//ems_ovatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			boolean ov_flag = false;
			boolean ov_record_flag = false;
			Map<String, String> ov_map = null;
			Map<String, String> abatt_map = null;
			
			
				
				//處理加班資料
				ov_map = ems_ovatt_handle.findOvertimeData(
						 conn,
						 this.sta_cur_day, 
						 employee_id, 
						 this.CalendarToString(class_out_cal), 
						 this.sta_comp_id);
				
				ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
				
				if(ov_flag){
					class_ov_in_cal  = tools.datetimeToCalendar(ov_map.get("OVERTIME_START")); //加班單設定的加班開始時間(Calendar)
					class_ov_out_cal = tools.datetimeToCalendar(ov_map.get("OVERTIME_END")); //加班單設定的加班結束時間(Calendar)
				}
				
				//處理考勤設定
				//使用遲到早退曠職元件處理
				Map<String, String> paramMap = new HashMap<String, String>();
				paramMap.put("COMP_ID", this.sta_comp_id);
				abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
				ov_record_flag = Boolean.parseBoolean(abatt_map.get("RECORD_FULL_OVERTIME_ATT_DATA"));  //是否記錄下班與加班上班
				
			
			
			//先檢查班別是否有設定加班
			if(ov_flag){
				//檢查 加班設定中的 '是否記錄下班與加班上班的刷卡資料' 
				if(!ov_record_flag){
					//若設定為 ==> 不記錄
					//此時需要檢核當天的考勤資料, 是否有實際加班的刷卡資料, 
					//因有加班才有不記錄的問題, 若沒加班直接執行下班考勤資料產生即可
					
					//班別設定的加班下班時間(Calendar)
					Calendar after_class_ov_in_cal = (Calendar) class_ov_in_cal.clone();
					
					//班別設定的加班下班時間(Calendar)
					Calendar after_class_ov_out_cal = (Calendar) class_ov_out_cal.clone();
					
					//取得系統參數 資料擷取的結束時間
					//依據系統參數設定加班下班的可擷取的門禁資料時間
					int att_limit_ov_end_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_OV_END_HOUR")));
					
					//將加班下班時間的後一個小時作為可擷取的門禁資料
					after_class_ov_out_cal.add(Calendar.HOUR_OF_DAY, att_limit_ov_end_hour);
					
					//檢覈實際加班資料是否存在
					if(this.chkRealOvertimeExist(conn, after_class_ov_in_cal, after_class_ov_out_cal, employee_id, comp_id, 1)){
						//有實際的加班資料
						chk_flag = true;
					}else{
						//沒有實際的加班資料
						chk_flag = false;
					}
					
				}else{
					//若設定為 ==> 要記錄, 則直接依據正常程序執行
					chk_flag = false;
				}
			}else{
				//未設定加班, 則不需考慮是否記錄下班與加班上班的刷資料問題
				chk_flag = false;
			}
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 產生上班考勤資料(不產生下班考勤，只為了給使用者看當天早上狀況)
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 * @param siesta_flag
	 */
	private void generateInAttDataWN_today(Connection conn, String employee_id, Map classMap, boolean siesta_flag ){
		
		String sql = "";
		
		try{
			Calendar class_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
															classMap, 1);  //班別設定的上班時間(Calendar)
			Calendar class_siesta_in_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
																   classMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, 
											this.cur_employee_id, this.sta_comp_id, classMap, 8);  //班別設定的下午上班時間(Calendar)
			Calendar class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id, 
															 classMap, 2);  //班別設定的下班時間(Calendar)
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(class_in_cal));
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			
			//複製上班時間
			Calendar range_class_in_cal = (Calendar)class_in_cal.clone();
			
			//為當前Calendar加上59秒, 避免發生一到設定的遲到整點就算遲到
			//(EX:設定8點40分上班, 實際刷卡資料 8點41分算遲到, 避免出現 8點40分遲到的問題
			range_class_in_cal.add(Calendar.SECOND, 59);
			
			//是否有提前加班狀況發生
			boolean before_overtime_flag = false;
			Map<String, String> before_ov_map = null;
			//使用加班處理元件來取得提前加班的資訊
			before_ov_map = ems_ovatt_handle.findBeforeOvertimeData(
							conn,
						    this.sta_cur_day, employee_id, this.CalendarToString(class_in_cal), this.sta_comp_id);
			before_overtime_flag = Boolean.parseBoolean(before_ov_map.get("ADVANCE_OVERTIME_SWITCH"));
			
			Calendar before_class_in_cal = null;
			//列出門禁資料的第一筆當作上班考勤資料
			//上班可擷取時間
			int att_limit_start_hour = (Integer.parseInt(tools.getSysParam(conn, this.sta_comp_id, "ATT_LIMIT_START_HOUR")));
			
			//String door_type = tools.getSysParam(conn, this.sta_comp_id, "DOOR_IN");	//參數1
			
			//有提前加班情況
			if(before_overtime_flag){
				
				Calendar class_before_ov_in_cal = null;
				
				//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (提前加班)
				class_before_ov_in_cal = tools.datetimeToCalendar(before_ov_map.get("BEFORE_OVERTIME_START")); //加班單設定的提前加班開始時間(Calendar)
				
				before_class_in_cal = (Calendar) class_before_ov_in_cal.clone();
				
				//取得系統參數 資料擷取的開始時間
				//依據系統參數設定提前加班開始的可擷取的門禁資料時間
				//上班時間往前三個小時
				before_class_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_start_hour);
				
				//正常情況  -- 有提前加班
				sql += this.getAttSelectSQL("IN_TIME",
											"IN_CAL_DATE",
											"IN_CAL_TIME",
											" <= '"+this.CalendarToString(range_class_in_cal)+"' ",
									        " >= '"+this.CalendarToString(before_class_in_cal)+"' ",
									        employee_id,
									        this.sta_comp_id,
									        " ORDER BY EHF020404T0_06 ",
									        1,card_number,"");
				
			}else{
				
				before_class_in_cal = (Calendar) class_in_cal.clone();
				//取得系統參數 資料擷取的開始時間
				//依據系統參數設定上班開始的可擷取的門禁資料時間
				before_class_in_cal.add(Calendar.HOUR_OF_DAY, -att_limit_start_hour);
				
				//正常情況 -- 沒有提前加班
				sql += this.getAttSelectSQL("IN_TIME",
											"IN_CAL_DATE",
											"IN_CAL_TIME", 
											" <= '"+this.CalendarToString(range_class_in_cal)+"' ",
											" >= '"+this.CalendarToString(before_class_in_cal)+"' ",
											employee_id,
											this.sta_comp_id, 
											" ORDER BY EHF020404T0_06 ",
											1,card_number,"");
				
			}
			
			//有中午打卡記錄的情況
			if(siesta_flag){
				
				//異常情況
				  sql += " UNION ";
				  sql += this.getAttSelectSQL("IN_TIME",
											  "IN_CAL_DATE",
											  "IN_CAL_TIME",  
											  " > '"+this.CalendarToString(range_class_in_cal)+"' ",
											  " < '"+this.CalendarToString(class_siesta_in_cal)+"' ",
											  employee_id,
											  this.sta_comp_id,
											  " ORDER BY EHF020404T0_06 ",
											  1,card_number,"");
				
			}else{
				//未設定需要記錄中午打卡
				//異常情況
				  sql += " UNION ";
				  sql += this.getAttSelectSQL("IN_TIME",
						  					  "IN_CAL_DATE",
						  					  "IN_CAL_TIME",  
						  					  " > '"+this.CalendarToString(range_class_in_cal)+"' ",
						  					  " < '"+this.CalendarToString(class_out_cal)+"' ",
						  					  employee_id,
						  					  this.sta_comp_id,
						  					  " ORDER BY EHF020404T0_06 ",
						  					  1,card_number,"");
				
			}
			
			//因為EMS產品套裝功能調整 -> 上班遲到的彈性時間改抓取遲到早退曠職設定(EHF020409T0)
			boolean flex_switch = false;
			int class_in_flex_sec = 0;
			int flex_diff_sec = 0;
			Map<String, String> abatt_map = null;
			
			//使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", this.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			flex_switch = Boolean.parseBoolean(abatt_map.get("DELAY_FLEX_FLAG"));  //上班彈性時間開關
			class_in_flex_sec = tools.MinuteToSecs(abatt_map.get("DELEY_FLEX_MINUTE"));  //上班彈性時間
		
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//取得資料來源   "1->自動匯入 (預設)2->手動匯入"
				String EHF020404T0_10=rs.getString("EHF020404T0_10");
				
				//封裝門禁資料
				EMS_AttendanceForm attform = this.packAttData(rs);
				
				//依據班別上班時間判斷是否遲到
				//int in_sec = this.TimeToSecs(rs.getString("IN_TIME"));  //第一筆門禁打卡時間(以秒計)
				Calendar in_cal = this.datetimeToCalendar(rs.getString("IN_CAL_DATE"), rs.getString("IN_CAL_TIME"));  //第一筆門禁打卡時間(Calendar)
				
				//siesta_flag = true ==> 判斷所抓取的考勤資料是否已超過上午下班時間, 若已超過設定為上班未打卡
				//siesta_flag = false ==> 判斷所抓取的考勤資料是否已超過下班時間, 若已超過設定為上班未打卡
				if( siesta_flag==true?(this.chkTimeIsOK(conn, in_cal, classMap, 7)):(this.chkTimeIsOK(conn, in_cal, classMap, 2)) ){
					//表示抓取的資料已超過上午下班時間, 所以資料不能當作上班時間,使用上班未打卡處理
					
					//上班未打卡
					this.InsertUnATTData(conn, 1, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
					
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecord(
					conn,
					employee_id, 
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					this.sta_cur_day, "1", 
					//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
					siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
									 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal ) 
									   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
					abatt_map, 
					this.sta_comp_id,4,"");
					
				}else{
					//表示抓取的資料沒有超過下班時間, 可以當作上班時間
					
					if(!flex_switch){
						//沒有設定上班彈性時間
						if( in_cal.compareTo(range_class_in_cal) <= 0 ){
							
							//EMS產品功能調整加班考勤的Mapping方式, 若有提前加班狀況發生
							//只有正常上班的狀況下才有加班情況, 因為上班遲到不可能有提前加班的刷卡記錄
							//在正常上班的狀況要額外判斷提前加班是否需要進行處理, 並產生提前加班的加班出勤記錄(EHF020802T0)
							//有提前加班情況
							if(before_overtime_flag){
								//結算提前加班
								ems_ovatt_handle.generateBeforeOvertimeAttendanceRecord(
								conn,
								employee_id, before_ov_map.get("OV_RECORD_UID"), 
								this.sta_cur_day, 
								before_ov_map.get("BEFORE_OVERTIME_START"), 
								this.CalendarToString(class_in_cal), 
								this.sta_comp_id);
							}
							
							//正常上班
							this.InsertATTData(conn, 1, false, 1, flex_switch, flex_diff_sec, classMap, attform);
							
							this.InAtt_flag = true;
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							this.sta_comp_id,1,"");
							
						}else if( in_cal.after(range_class_in_cal) && in_cal.before(class_siesta_in_cal) ){
							
							flex_diff_sec = this.CaculateLateSecs( 1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 1, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							this.sta_comp_id,2,attform.getEHF020404T0_06());
							
						}else if( !siesta_flag && in_cal.after(range_class_in_cal) && in_cal.before(class_out_cal) ){
							
							//未記錄中午打卡
							flex_diff_sec = this.CaculateLateSecs( 1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 1, true, 2, flex_switch, flex_diff_sec, classMap, attform);
								
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							this.sta_comp_id,2,attform.getEHF020404T0_06());
							
						}else{
							//上班未打卡
							this.InsertUnATTData(conn, 1, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
							siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
									 		 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
									 		   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
							abatt_map, 
							this.sta_comp_id,4,"");
							
						}
						
					}else{
						//有設定上班彈性時間
						Calendar flex_class_in_cal = (Calendar)class_in_cal.clone();
						flex_class_in_cal.add(Calendar.SECOND, class_in_flex_sec+59);  //為當前Calendar加上彈性的秒數
						
						if( in_cal.compareTo(flex_class_in_cal) <= 0 ){
							//EMS產品功能調整加班考勤的Mapping方式, 若有提前加班狀況發生
							//只有正常上班的狀況下才有加班情況, 因為上班遲到不可能有提前加班的刷卡記錄
							//在正常上班的狀況要額外判斷提前加班是否需要進行處理, 並產生提前加班的加班出勤記錄(EHF020802T0)
							//有提前加班情況
							if(before_overtime_flag){
								//結算提前加班
								ems_ovatt_handle.generateBeforeOvertimeAttendanceRecord(
								conn,
								employee_id, before_ov_map.get("OV_RECORD_UID"), 
								this.sta_cur_day, 
								before_ov_map.get("BEFORE_OVERTIME_START"), 
								this.CalendarToString(class_in_cal), 
								this.sta_comp_id);
							}
							
							flex_diff_sec = tools.MilliSecsToSecs(in_cal.getTimeInMillis() - class_in_cal.getTimeInMillis());  //彈性使用時間
							//正常上班
							this.InsertATTData(conn, 1, false, 1, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							this.sta_comp_id,1,"");
							
						}else if ( in_cal.after(flex_class_in_cal) && in_cal.before(class_siesta_in_cal) ){
							
							flex_diff_sec = this.CaculateLateSecs(1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 1, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							this.sta_comp_id,2,attform.getEHF020404T0_06());
							
						}else if( !siesta_flag && in_cal.after(flex_class_in_cal) && in_cal.before(class_out_cal) ){
							//未記錄中午打卡
							flex_diff_sec = this.CaculateLateSecs(1, in_cal, class_in_cal );  //上班遲到時間
							//上班遲到
							this.InsertATTData(conn, 1, true, 2, flex_switch, flex_diff_sec, classMap, attform);
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							flex_diff_sec, 
							abatt_map, 
							this.sta_comp_id,2,attform.getEHF020404T0_06());
							
						}else{
							
							//上班未打卡
							this.InsertUnATTData(conn, 1, 4, classMap, employee_id,rs.getString("EHF020404T0_01"));
							
							//建立出勤記錄
							ems_abatt_handle.putAttendanceRecord(
							conn,
							employee_id,
							String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
							this.sta_cur_day, "1", 
							//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
							siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
											 :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
											   - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
							abatt_map, 
							this.sta_comp_id,4,"");
							
						}						
						
					}
					
				}
				
			}else{				
				//上班未打卡
				this.InsertUnATTData(conn, 1, 4, classMap, employee_id,"");
				
				//建立出勤記錄
				ems_abatt_handle.putAttendanceRecord(
				conn,
				employee_id,
				String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
				this.sta_cur_day, "1", 
				//Integer.parseInt(abatt_map.get("ABSENTEEISM_COND_HOUR_IN_SEC"))+1,
				siesta_flag==true?(this.CaculateLateSecs( 0, class_in_cal, class_siesta_in_cal ))
						 	     :(this.CaculateLateSecs( 0, class_in_cal, class_out_cal )
						 	       - this.CaculateLateSecs( 0, class_siesta_in_cal, class_siesta_out_cal )),
				abatt_map, 
				this.sta_comp_id,4,"");
				
			}
			
			rs.close();
			stmt.close();
			
			
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(sql);
		}
		
	}
	
	/**
	 * 產生時薪考勤判斷用的門禁資料查詢SQL
	 * @param colname1
	 * @param colname2
	 * @param colname3
	 * @param timecond1
	 * @param timecond2
	 * @param employee_id
	 * @param comp_id
	 * @param others
	 * @param limit_value
	 * @return
	 */
	protected String getAttSelectSQLForHour( String colname1, String colname2, String colname3, 
									  		 String cur_day, String employee_id, 
									  		 String comp_id, String others, int limit_value ,String card_number, String door_type ){
		
		String sql = "";
		
		try{
			
			sql += " SELECT * FROM ( " +
	  		 " SELECT " +
	  		 " IFNULL(EHF020404T0_01, '') AS EHF020404T0_01, " +
	  		 " IFNULL(EHF020404T0_02, '') AS EHF020404T0_02, " +
	  		 " IFNULL(EHF020404T0_03, '') AS EHF020404T0_03, " +
	  		 " IFNULL(EHF020404T0_04, '') AS EHF020404T0_04, " +//打卡日期
	  		 " IFNULL(EHF020404T0_05, '') AS EHF020404T0_05, " +//打卡時間
	  		 " IFNULL(EHF020404T0_06, '') AS EHF020404T0_06, " +//打卡日期時間
	  		 " IFNULL(EHF020404T0_07, '') AS EHF020404T0_07, " +
	  		 " IFNULL(EHF020404T0_08, '') AS EHF020404T0_08, " +
	  		 " IFNULL(EHF020404T0_09, '') AS EHF020404T0_09, " +
	  		 " IFNULL(EHF020404T0_10, '') AS EHF020404T0_10, " +//資料來源
	  		 " IFNULL((EHF020404T0_06), '') AS LIMIT_VALUE, " +
			 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%H%i'), '') AS "+colname1+", " +  //打卡日期時間
			 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%Y/%m/%d'), '') AS "+colname2+", " +
			 " IFNULL(DATE_FORMAT(EHF020404T0_06, '%H:%i:%s'), '') AS "+colname3+" " +
			 " FROM EHF020404T0 " +
			 " LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020404T0_03 AND EHF020403T1_06 = EHF020404T0_09 " +
			 " WHERE 1=1 " +
			 " AND EHF020404T0_06 IS NOT NULL " ;  //打卡日期時間
			 //" AND EHF020404T0_06 "+timecond1+" " ;
			 //if(!"".equals(timecond2)){
			 //	 sql += " AND EHF020404T0_06 "+timecond2+" " ;
			 //}			 
			 sql += " AND IF(EHF020404T0_10 = 1, IF(EHF020404T0_FLAG = 1, 1, 0), IF(EHF020404T0_FLAG = 2, IF(EHF020404T0_FLAG = 2, 1, 0), 0)) = 1" +
			
			 //" AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			 //因應臨時卡機制, 必須修改門禁資料查詢SQL, 加入員工感應卡的使用期間作為額外的判斷條件, 且必須納入臨時卡資料作為感應卡資料之一
			 //門禁資料打卡日期時間必須是在員工感應卡有效期間內才算是有效的打卡日期時間資料  edit by joe 2012/07/25 -- (目前尚未實作)
			 " AND EHF020404T0_03 IN ("+card_number+") " +  //感應卡號  新增於20140113 BY 賴泓錡
			 " AND EHF020404T0_04 = '"+cur_day+"' " +  //計算考勤的日期
			 " AND EHF020404T0_09 = '"+comp_id+"' " ;  //公司代碼
			 if(!"".equals(door_type)){
				 sql += " AND EHF020404T0_08 = '"+door_type+"' ";	//動作代碼
			 }
			 sql += " "+others+" " +
			 " LIMIT "+limit_value+" " +
			 " ) tableA " +
			 " WHERE 1=1 " +
			 " AND tableA.EHF020404T0_06 <> '' " ;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sql;
	}
	
	/**
	 * 產生時薪上班考勤資料
	 * @param conn
	 * @param employee_id
	 * @param classMap
	 * @param siesta_flag
	 */
	private void generateInAttDataHOUR(Connection conn, String employee_id, Map classMap, boolean siesta_flag ){
		
		String sql = "";
		
		try{
			//取得系統當前日期時間
			Calendar cur_system_cal = Calendar.getInstance();
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(cur_system_cal));
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			sql += this.getAttSelectSQLForHour("IN_TIME",
											   "IN_CAL_DATE",
											   "IN_CAL_TIME", 
											   EMS_AttendanceAction.sta_cur_day,
											   employee_id,
											   EMS_AttendanceAction.sta_comp_id, 
											   " ORDER BY EHF020404T0_06 ASC ",
											   1,card_number,"");
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			Map<String, String> abatt_map = null;
			
			//使用遲到早退曠職元件處理
			//abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			//時薪不用管遲到早退曠職設定
			boolean flex_switch = false;	//上班彈性時間開關
			int flex_diff_sec = 0;	//上班遲到時間
			
			if(rs.next()){
				
				//封裝門禁資料
				EMS_AttendanceForm attform = this.packAttData(rs);
				
				//正常上班
				this.InsertATTData(conn, 1, false, 1, flex_switch, flex_diff_sec, classMap, attform);
				
				//建立出勤記錄
				ems_abatt_handle.putAttendanceRecordByHour(
				conn,
				employee_id,
				String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
				EMS_AttendanceAction.sta_cur_day, "1", 
				flex_diff_sec, 
				abatt_map, 
				EMS_AttendanceAction.sta_comp_id,1,"");
				
			}
			
			rs.close();
			stmt.close();
			
			ems_abatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生時薪上班考勤資料，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 產生時薪下班考勤資料
	 * @param conn
	 * @param employee_id
	 * @param class_no
	 */
	private void generateEndAttDataHOUR( Connection conn, String employee_id, Map classMap){
		
		Map userMap = null;
		String attKey = "";
		
		try{
			//取得上班考勤資訊
			userMap = this.getUserInAttendanceTime(conn, employee_id, 1, classMap);
			
			if( userMap != null ){
				attKey = (String) userMap.get("ATT_KEY");
			}
			
			//取得系統當前日期時間
			Calendar cur_system_cal = Calendar.getInstance();
			
			//先取得可用的感應卡號
			String card_number = this.card_number(conn,employee_id,this.CalendarToString(cur_system_cal));
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			
			//列出門禁資料的最後一筆當作下班考勤資料
			String sql = "";
			
			sql += this.getAttSelectSQLForHour("OUT_TIME",
											   "OUT_CAL_DATE",
											   "OUT_CAL_TIME",
											   EMS_AttendanceAction.sta_cur_day,									   
											   employee_id,
											   EMS_AttendanceAction.sta_comp_id,
											   " ORDER BY EHF020404T0_06 DESC ",
											   1,card_number,"");
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			Map<String, String> abatt_map = null;
			
			int flex_diff_sec = 0; // 下班早退秒數
			boolean flex_switch = false; // 下班彈性時間開關
			
			if(rs.next()){
				
				//boolean samekey_flag = this.compareAttKeyIsSame(attKey, rs.getString("EHF020404T0_02"), rs);
				
				//檢核門禁資料是否是上班考勤資料, 若是表示下班未打卡
				//if(samekey_flag){
					
					//封裝門禁資料
					EMS_AttendanceForm attform = this.packAttData(rs);
					
					//正常下班                                  
					this.InsertATTData(conn, 2, false, 1, flex_switch, flex_diff_sec, classMap, attform);
												
					//建立出勤記錄
					ems_abatt_handle.putAttendanceRecordByHour(
					conn,
					employee_id,
					String.valueOf((Integer)classMap.get("WORK_CLASS_NO")),
					EMS_AttendanceAction.sta_cur_day, "2", 
					flex_diff_sec, 
					abatt_map, 
					EMS_AttendanceAction.sta_comp_id,1,"");
					
				//}else{
					//無法建立未打卡出勤記錄
					
				//}
				
			}
			
			rs.close();
			stmt.close();
			
			ems_abatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生時薪下班考勤資料，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 檢核是否記錄下班與加班上班的資料(且檢核是否有考勤日期的加班設定)
	 * @param conn
	 * @param classMap
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public boolean chkOVTRecordProcess(Connection conn, Map classMap, String employee_id, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			Calendar class_out_cal = this.getCalendarByClass(conn, this.sta_cur_day, this.cur_employee_id, this.sta_comp_id,
									      classMap, 2);  //班別設定的下班時間(Calendar)
			//為當前下班考勤加上一秒, 避免時間區間邊界重疊的問題
			class_out_cal.add(Calendar.SECOND, 1);

			//使用新的加班記錄控制元件 -> 取得加班記錄(EHF020801T0) (下班後加班)
			
			//建立 遲到早退曠職元件
			EMS_AbnormalAttendanceHandle ems_abatt_handle = new EMS_AbnormalAttendanceHandle();
			//ems_abatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			//建立加班處理元件
			EMS_OvertimeAttendanceHandle ems_ovatt_handle = new EMS_OvertimeAttendanceHandle();
			//ems_ovatt_handle.setOutside_conn(conn);  //設定外部Connection
			
			boolean ov_flag = false;
			boolean ov_record_flag = false;
			Map<String, String> ov_map = null;
			Map<String, String> abatt_map = null;
										
			//處理加班資料
			ov_map = ems_ovatt_handle.findOvertimeData(
					 conn,
					 this.sta_cur_day, 
					 employee_id, 
					 this.CalendarToString(class_out_cal), 
					 this.sta_comp_id);
				
			ov_flag = Boolean.parseBoolean(ov_map.get("OVERTIME_SWITCH"));
				
			//處理考勤設定
			//使用遲到早退曠職元件處理
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("COMP_ID", this.sta_comp_id);
			abatt_map = ems_abatt_handle.getAbnormalAttendanceSetting(conn, paramMap);
			ov_record_flag = Boolean.parseBoolean(abatt_map.get("RECORD_FULL_OVERTIME_ATT_DATA"));  //是否記錄下班與加班上班				
			
			//先檢查班別是否有設定加班
			if(ov_flag){
				//檢查 加班設定中的 '是否記錄下班與加班上班的刷卡資料' 
				if(!ov_record_flag){
					//若設定為 ==> 不記錄
					//此時需要檢核當天的考勤資料, 是否有實際加班的刷卡資料, 
					//因有加班才有不記錄的問題, 若沒加班直接執行下班考勤資料產生即可
					chk_flag = true;
					
				}else{
					//若設定為 ==> 要記錄, 則直接依據正常程序執行
					chk_flag = false;
				}
			}else{
				//未設定加班, 則不需考慮是否記錄下班與加班上班的刷資料問題
				chk_flag = false;
			}
			
			//關閉遲到早退曠職處理元件
			ems_abatt_handle.close();
			
			//關閉加班處理元件
			ems_ovatt_handle.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
}
