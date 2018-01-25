package com.spon.utils.work_schedule;

/**
 * <Form>EMS 排班表使用的Form
 * @version 1.0
 * @created 25-四月-2011 下午 02:26:36
 */
public class EMS_Work_ScheduleForm {
	
	public EMS_Work_ScheduleForm(){

	}
	
	private int schedule_no;
	private String employee_id;
	private int year;
	private int month;
	private int day;
	private String date;
	private String date_desc;
	private boolean holiday_flag;
	private int class_no;
	private String comment;
	
	private String Vacation;//休假類別
	
	
	private float FULL_WORK_HOURS;
	private float REAL_WORK_HOURS;
	
	private boolean in_abnormal_flag;
	private String in_abnormal_desc;
	private boolean out_abnormal_flag;
	private String out_abnormal_desc;
	private String va_inf;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;


	public void finalize() throws Throwable {
		super.finalize();
	}


	public int getSchedule_no() {
		return schedule_no;
	}


	public void setSchedule_no(int scheduleNo) {
		schedule_no = scheduleNo;
	}


	public String getEmployee_id() {
		return employee_id;
	}


	public void setEmployee_id(String employeeId) {
		employee_id = employeeId;
	}


	public int getYear() {
		return year;
	}


	public void setYear(int year) {
		this.year = year;
	}


	public int getMonth() {
		return month;
	}


	public void setMonth(int month) {
		this.month = month;
	}


	public String getDate() {
		return date;
	}


	public void setDate(String date) {
		this.date = date;
	}


	public boolean isHoliday_flag() {
		return holiday_flag;
	}


	public void setHoliday_flag(boolean holidayFlag) {
		holiday_flag = holidayFlag;
	}


	public int getClass_no() {
		return class_no;
	}


	public void setClass_no(int classNo) {
		class_no = classNo;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public String getUSER_CREATE() {
		return USER_CREATE;
	}


	public void setUSER_CREATE(String uSERCREATE) {
		USER_CREATE = uSERCREATE;
	}


	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}


	public void setUSER_UPDATE(String uSERUPDATE) {
		USER_UPDATE = uSERUPDATE;
	}


	public int getVERSION() {
		return VERSION;
	}


	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}


	public String getDATE_CREATE() {
		return DATE_CREATE;
	}


	public void setDATE_CREATE(String dATECREATE) {
		DATE_CREATE = dATECREATE;
	}


	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}


	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}


	public float getFULL_WORK_HOURS() {
		return FULL_WORK_HOURS;
	}


	public void setFULL_WORK_HOURS(float fULLWORKHOURS) {
		FULL_WORK_HOURS = fULLWORKHOURS;
	}


	public float getREAL_WORK_HOURS() {
		return REAL_WORK_HOURS;
	}


	public void setREAL_WORK_HOURS(float rEALWORKHOURS) {
		REAL_WORK_HOURS = rEALWORKHOURS;
	}


	public int getDay() {
		return day;
	}


	public void setDay(int day) {
		this.day = day;
	}


	public boolean isIn_abnormal_flag() {
		return in_abnormal_flag;
	}


	public void setIn_abnormal_flag(boolean inAbnormalFlag) {
		in_abnormal_flag = inAbnormalFlag;
	}


	public String getIn_abnormal_desc() {
		return in_abnormal_desc;
	}


	public void setIn_abnormal_desc(String inAbnormalDesc) {
		in_abnormal_desc = inAbnormalDesc;
	}


	public boolean isOut_abnormal_flag() {
		return out_abnormal_flag;
	}


	public void setOut_abnormal_flag(boolean outAbnormalFlag) {
		out_abnormal_flag = outAbnormalFlag;
	}


	public String getOut_abnormal_desc() {
		return out_abnormal_desc;
	}


	public void setOut_abnormal_desc(String outAbnormalDesc) {
		out_abnormal_desc = outAbnormalDesc;
	}


	public String getVa_inf() {
		return va_inf;
	}


	public void setVa_inf(String vaInf) {
		va_inf = vaInf;
	}


	public String getDate_desc() {
		return date_desc;
	}


	public void setDate_desc(String dateDesc) {
		date_desc = dateDesc;
	}


	public String getVacation() {
		return Vacation;
	}


	public void setVacation(String vacation) {
		Vacation = vacation;
	}

	
	
}
