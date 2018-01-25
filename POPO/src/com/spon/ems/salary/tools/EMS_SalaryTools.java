package com.spon.ems.salary.tools;

import org.apache.log4j.Logger;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.msg.EMS_MSGSYSTEM;
import com.spon.ems.salary.forms.EHF030102M0F;
import com.spon.ems.salary.forms.EHF030102M1F;
import com.spon.ems.salary.forms.EHF030200M0F;
import com.spon.ems.salary.forms.EHF030600M0F;
import com.spon.ems.salary.forms.EHF030600M1F;
import com.spon.ems.salary.forms.EHF030600M2F;
import com.spon.ems.util.overtime.EMS_OvertimeAttendanceHandle;

import com.spon.ems.vacation.forms.EHF020100M0F;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.ems.vacation.tools.EMS_CaculateVacationByCal;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_Work_ScheduleForm;
import com.spon.utils.work_schedule.EMS_Work_Schedule_Table;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
 


/**
 * EMS 薪資計算公用元件
 * 
 * @version 1.0
 * @created 18-十月-2010 下午 06:00:47
 */
public class EMS_SalaryTools {

	static Logger loger = Logger.getLogger(EMS_SalaryTools.class.getName());
	
	private static BA_TOOLS tools = BA_TOOLS.getInstance();
	private volatile static EMS_SalaryTools ems_salary_tools; 
	private static EMS_InsuranceTools ems_insurance_tools;
	private static EHF030600M0F ehf030600m0 = null;
	
	private Map vacation_type_map = null;
	private List<Map<String,String>> vacation_time_list = new ArrayList<Map<String,String>>();	//EMS產品新增參數 20130909 by Hedwig
	private String salyymm_first_day = "";
	private String salyymm_last_day = "";
	
	private static String sta_salary_no = "";
	private static String sta_salyymm = "";
	private static String sta_costyymm = "";
	private static String sta_user_employee_id;
	private static String sta_user_id = "";
	private static String sta_user_code = "";
	private static String sta_user_name = "";
	private static String sta_comp_id = "";
	
	
	//private int count = 0;//紀錄EHF020410T0_02  規則序號(遲到早退曠職)
	Map EHF020409T0 =new HashMap<String, String>();
	
	//EMS系統訊息元件
	protected static EMS_MSGSYSTEM ems_msg_tools = EMS_MSGSYSTEM.getInstance();
	
	
	public static EMS_SalaryTools getInstance() {
        /*if (ems_salary_tools == null){
        	ems_salary_tools = new EMS_SalaryTools();
        }*/
        
        if(ems_salary_tools == null) {
            synchronized(EMS_SalaryTools.class) {
            	ems_salary_tools = new EMS_SalaryTools();
            }
        }
        
        //建立保險計算元件
		ems_insurance_tools = EMS_InsuranceTools.getInstance();
        
        return ems_salary_tools;
    }
		
	public static EMS_SalaryTools getInstance(String salary_no,String salYYMM, String costYYMM, String user_employee_id, String user_id, String user_code, String user_name, String comp_id) {
        
		/*if (ems_salary_tools == null){
        	ems_salary_tools = new EMS_SalaryTools(salary_no, salYYMM, costYYMM, user_employee_id, user_id, user_code, user_name, comp_id);
        }else{
        	sta_salary_no = salary_no;
        	sta_salyymm = salYYMM;
        	sta_costyymm = costYYMM;
        	sta_user_id = user_id;
        	sta_user_name = user_name;
        	sta_comp_id = comp_id;
        }*/
		
		if(ems_salary_tools == null) {
            synchronized(EMS_SalaryTools.class) {
            	ems_salary_tools = new EMS_SalaryTools(salary_no, salYYMM, costYYMM, user_employee_id, user_id, user_code, user_name, comp_id);
            }
        }else{
        	sta_salary_no = salary_no;
        	sta_salyymm = salYYMM;
        	sta_costyymm = costYYMM;
        	sta_user_id = user_id;
        	sta_user_name = user_name;
        	sta_comp_id = comp_id;
        }
		
		//建立保險計算元件
		ems_insurance_tools = EMS_InsuranceTools.getInstance(salYYMM, costYYMM, user_id, user_name, comp_id);
		//ems_insurance_tools = new EMS_InsuranceTools(salYYMM, costYYMM, user_id, user_name, comp_id);
        
        return ems_salary_tools;
    }
	
	private EMS_SalaryTools(String salary_no, String salYYMM, String costYYMM, String user_employee_id, String user_id, String user_code, String user_name, String comp_id){
		
		EMS_SalaryTools.sta_salary_no = salary_no;
		EMS_SalaryTools.sta_salyymm = salYYMM;
		EMS_SalaryTools.sta_costyymm = costYYMM;
		EMS_SalaryTools.sta_user_employee_id = user_employee_id;
		EMS_SalaryTools.sta_user_id = user_id;
		EMS_SalaryTools.sta_user_code = user_code;
		EMS_SalaryTools.sta_user_name = user_name;
		EMS_SalaryTools.sta_comp_id = comp_id;
		
		//建立保險計算元件
		ems_insurance_tools = EMS_InsuranceTools.getInstance(salYYMM, costYYMM, user_id, user_name, comp_id);
		//ems_insurance_tools = new EMS_InsuranceTools(salYYMM, costYYMM, user_id, user_name, comp_id);
		
	}
	
	private EMS_SalaryTools(){
		//建立保險計算元件
		//ems_insurance_tools = EMS_InsuranceTools.getInstance();
	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * 薪資計算元件
	 * @param conn
	 * @param salary_no
	 * @param salYYMM
	 * @param costYYMM
	 * @param user_name
	 * @param comp_id
	 */
	public Map count(Connection conn, String salary_no, String salYYMM, String costYYMM, String user_name, String comp_id, String[] test_id ){
		
		ArrayList cntEmpList = new ArrayList();
		ArrayList ngEmpList = new ArrayList();
		Map msgMap = new HashMap();
		
		try{
			
			try{
				//判斷是否關帳
				//目前未實做關帳功能
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("判斷是否關帳，發生錯誤!!" + e.toString());
			}
			
			try{
				//判斷薪資計算是否發生跳月處理
				//若以後要做追溯功能，則薪資不可跳月計算，否則追溯發生錯誤
				//目前未實做追溯功能
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("檢查說明(整批薪資計算需檢查之前月份的薪資是否處理完畢，不允許跳月處理)時，發生錯誤!!" + e.toString());
			}
			
			try{
				//系統自動重算請假單的請假時數
				//因計薪前必須先將排班表調整完成才可計算, 在排班表正確的狀況下做一次請假單的請假時數計算處理
				//計算請假單的區間為薪資年月的1號至下個月的1號
				
				//判斷薪資計算是否要重新計算請假時數
				String ems_salary_count_compute_va_time = this.tools.getSysParam(conn, comp_id, "EMS_SALARY_COUNT_COMPUTE_VA_TIME");
				if("ENABLE".equals(ems_salary_count_compute_va_time)){
					//取得計算區間
					Calendar cal_salyymm = this.tools.covertStringToCalendar(salYYMM, "yyyy/MM");
					cal_salyymm.set(Calendar.DAY_OF_MONTH, 1);  //設定為這個月的1號
					String the_first_day_of_this_month = this.tools.covertDateToString(cal_salyymm.getTime(), "yyyy/MM/dd");
					cal_salyymm.add(Calendar.MONTH, 1);  //取得下個月的1號
					String the_first_day_of_next_month = this.tools.covertDateToString(cal_salyymm.getTime(), "yyyy/MM/dd");
					
					//建立請假時數計算元件
					//取得員工目前班別資訊
					AuthorizedBean user_authbean = new AuthorizedBean();
					user_authbean.setUserName(user_name);
					user_authbean.setUserId(EMS_SalaryTools.sta_user_id);
					user_authbean.setUserCode(EMS_SalaryTools.sta_user_code);
					user_authbean.setEmployeeID(EMS_SalaryTools.sta_user_employee_id);
					user_authbean.setCompId(comp_id);
					EMS_CaculateVacationByCal cacuva_tools = new EMS_CaculateVacationByCal();
					//重新計算請假時數
					int dataCount = cacuva_tools.computeVaTime(conn, user_authbean, "", "", 
															   the_first_day_of_this_month, 
															   the_first_day_of_next_month, 
															   true);
					
					//寫入薪資系統開始計算的訊息
					EMS_SalaryTools.ems_msg_tools.writeMSGForm( conn, "SALARY_MSG_0002", "SALARY_COMPUTE_VA_TIME", 
													 "EMS_SalaryTools().count() -> SALARY COMPUTE VA TIME , " +
													 Calendar.getInstance().getTime() + "薪資系統系統 --> 請假單時數重新計算成功!! 共計算了 "+dataCount+"筆請假單",
													 user_name, EMS_MSGSYSTEM.General, EMS_MSGSYSTEM.E_SYSTEM_MANGER, comp_id);
				}
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("檢查說明(整批薪資計算需重算請假單的請假時數)時，發生錯誤!!" + e.toString());
			}
			
			try{
				//建立員工的歷史薪資主檔,保險主檔資料 Use 入扣帳年月
				this.addHistory(conn, salary_no, salYYMM, costYYMM, comp_id, test_id);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("建立員工的歷史薪資主檔,保險主檔資料時，發生錯誤!!" + e.toString());
			}
			
			try{
				//取得該月可計薪的員工清單,以公司為一個計算單位
				//可計薪 ==> 當月的異常考勤資料已全部修正 且 當月有實際的考勤資料
				Map salaryEmpList = this.getCntSalaryList( conn, salary_no, costYYMM, salYYMM, comp_id, test_id );
				
				//建立記薪員工清單
				cntEmpList = (ArrayList) salaryEmpList.get("CNTLIST");
				//建立不記薪員工清單
				ngEmpList = (ArrayList) salaryEmpList.get("NGLIST");
				
				msgMap.put("NGLIST", ngEmpList);  //儲存不計薪員工清單
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("取得該月可計薪的員工清單，發生錯誤!!" + e.toString());
			}
			
			
			//目前EMS系統的勞工保險,全民健康保險,勞退基金的設定並不是複雜的計算，所以目前並不需要獨立的Table與保險費計算流程
			try{
				//計算勞保費  --> 產生勞保計費明細檔
				
				
				
				//計算健保費  --> 產生健保計費明細檔
				
				
				
				//計算勞退費  --> 產生勞退計費明細檔
				
				
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算相關保費，發生錯誤!!" + e.toString());
			}
			
			
			try{
				//執行薪資計算處理流程與產生薪資計算明細資料
				
				if(cntEmpList.size() > 0 || ngEmpList.size() > 0 ){
					//計薪清單不為空才進行計算
					msgMap = this.count( conn, salYYMM, costYYMM, salary_no, comp_id, user_name, "", cntEmpList, ngEmpList);
				}
				
				if (!msgMap.containsKey("ERRMSG") || "".equals(msgMap.get("ERRMSG")) ){ //計算成功
					conn.commit();
					msgMap.put("MSG", "計算成功!!");
				}else{  //計算失敗
					conn.rollback();	
					msgMap.put("MSG", "計算失敗!!  "+((String) msgMap.get("ERRMSG")));
				}
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("整批薪資計算，發生錯誤!!" + e.toString());
			}
			
		}catch(Exception e){
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println(e);
			e.printStackTrace();
		}
		
		return msgMap;
	}
	
	/**
	 * 薪資計算元件  薪資計算開始
	 * @param conn
	 * @param salYYMM
	 * @param costYYMM
	 * @param salary_no
	 * @param comp_id
	 * @param user_name
	 * @param sal_type
	 * @param cntEmpList
	 * @return
	 */
	private Map count( Connection conn, String salYYMM, String costYYMM, String salary_no, String comp_id, String user_name, String sal_type, ArrayList cntEmpList, ArrayList ngEmpList ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_CheckVacation check_vacation_tools = EMS_CheckVacation.getInstance();
		Map msgMap = new HashMap();
//		EHF030600M0F ehf030600m0 = null;
		int EHF030600T0_C = 0;  //處理總筆數
		int EHF030600T0_NGC = 0;  //未處理總筆數
		long EHF030600T0_AM = 0;  //處理總金額
		String EHF030600T0_U = "";  //薪資計算UID
		Map employee_Map = new HashMap();
		try{
			Statement stmt = null;
			
			/*
			//刪除薪資明細資料EHF030600T1中, 非本次可計薪人員的薪資明細資料, edit by joe 2012/01/10
			try{
				Iterator it = cntEmpList.iterator();
				int count = 0;
				StringBuffer del_list = new StringBuffer();
				String sql = "";
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				//判斷是否有計薪清單
				
				if(it.hasNext()){
					
					//取得計薪人員
					EHF030600M2F ehf030600m2 = (EHF030600M2F) it.next();
					del_list.append("(");
					del_list.append("'");
					del_list.append(ehf030600m2.getEHF030600T2_02());
					del_list.append("'");
					
					while(it.hasNext()){
						//取得計薪人員
						ehf030600m2 = (EHF030600M2F) it.next();
						del_list.append(",");
						del_list.append("'");
						del_list.append(ehf030600m2.getEHF030600T2_02());
						del_list.append("'");
					}
					del_list.append(")");
					sql = " DELETE FROM EHF030600T1 " +
					  " WHERE 1=1 " +
					  " AND EHF030600T1_01 = "+salary_no+" " +  //薪資計算序號
					  " AND EHF030600T1_02 NOT IN "+del_list.toString()+" " +  //可計薪員工清單
					  " AND EHF030600T1_M = '"+costYYMM+"' " +  //入扣帳年月
					  " AND EHF030600T1_M1 = '"+salYYMM+"' " +  //薪資年月
					  " AND EHF030600T1_SCP <= '02' " +  //薪資處理狀態
					  " AND EHF030600T1_08 = '"+comp_id+"' ";  //公司代碼
					stmt.execute(sql);
				}else{
					sql = " DELETE FROM EHF030600T1 " +
					  	  " WHERE 1=1 " +
					  	  " AND EHF030600T1_01 = "+salary_no+" " +  //薪資計算序號
					  	  " AND EHF030600T1_M = '"+costYYMM+"' " +  //入扣帳年月
					  	  " AND EHF030600T1_M1 = '"+salYYMM+"' " +  //薪資年月
					  	  " AND EHF030600T1_SCP <= '02' " +  //薪資處理狀態
					  	  " AND EHF030600T1_08 = '"+comp_id+"' ";  //公司代碼
					stmt.execute(sql);
				}
				
				
				stmt.close();
				
			}catch(Exception e){
				e.printStackTrace();
				msgMap.put("ERRMSG", "刪除非本次可計薪人員的薪資明細檔資料(EHF030600T1)時，發生錯誤!!" + e.toString());
			}
			*/
			
			//EMS計薪處理準備
			if(cntEmpList.size() > 0 || ngEmpList.size() > 0 ){
				
				try{
				//準備薪資計算控制檔
				ehf030600m0 = new EHF030600M0F();
				
				//取得薪資計算控制檔 by salary_no
				ehf030600m0 = this.getSalCntFile( conn, salary_no, comp_id, ehf030600m0 );
				
				if(!"".equals(ehf030600m0.getEHF030600T0_U())){
					EHF030600T0_U = ehf030600m0.getEHF030600T0_U();
				}else{
					EHF030600T0_U = "";
				}
					
				}catch(Exception e){
					e.printStackTrace();
					msgMap.put("ERRMSG", "準備薪資控制檔的資料時，發生錯誤!!" + e.toString());
					throw new Exception("準備薪資控制檔的資料時，發生錯誤!!" + e.toString());
				}
				
				
				EHF030600T0_C = 0;  //處理總筆數
				EHF030600T0_AM = 0;  //處理總金額
				
				try{
					//回寫薪資控制檔的UID
					stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					String sql = "";
					
					//處理計薪人員
					for(int i=0; i<cntEmpList.size(); i++){
						
						sql = "" +
						" UPDATE EHF030600T2 SET EHF030600T2_SCU = '"+EHF030600T0_U+"' " +
						" WHERE 1=1 " +
						" AND EHF030600T2_01 = "+salary_no+" " +  //薪資計算序號
						//" AND EHF030600T2_04 = '"+costYYMM+"' " +  //入扣帳年月
						//" AND EHF030600T2_M = '"+salYYMM+"' " +  //薪資年月
					    " AND EHF030600T2_23 = '"+comp_id+"' " +  //公司代碼
						" AND EHF030600T2_02 = '" + ((EHF030600M2F) cntEmpList.get(i)).getEHF030600T2_02() + "' " ;  //員工工號
						
						stmt.execute(sql);
					}
					
					//處理不計薪人員
					for(int i=0; i<ngEmpList.size(); i++){
						
						sql = "" +
						" UPDATE EHF030600T2 SET EHF030600T2_SCU = '"+EHF030600T0_U+"' " +
						" WHERE 1=1 " +
						" AND EHF030600T2_01 = "+salary_no+" " +  //薪資計算序號
						//" AND EHF030600T2_04 = '"+costYYMM+"' " +  //入扣帳年月
						//" AND EHF030600T2_M = '"+salYYMM+"' " +  //薪資年月
					    " AND EHF030600T2_23 = '"+comp_id+"' " +  //公司代碼
						" AND EHF030600T2_02 = '" + ((EHF030600M2F) ngEmpList.get(i)).getEHF030600T2_02() + "' " ;  //員工工號
						
						stmt.execute(sql);
					}
					
					stmt.close();
					
				}catch(Exception e){
					e.printStackTrace();
					msgMap.put("ERRMSG", "更改薪資歷史資料時，發生錯誤!!" + e.toString());
					throw new Exception("更改薪資歷史資料時，發生錯誤!!" + e.toString());
				}
				
				//建立系統假別資訊
				try{
					//將假別資訊建置到HashMap中
					this.vacation_type_map = check_vacation_tools.getVacationTypeInf(conn, comp_id);
					//傳入指定日期作為薪資年月的第一天, dateformat = 'yyy/MM/dd'
					this.salyymm_first_day = tools.convertADDateToStrng(BA_TOOLS.getFirstMonthDay(tools.covertStringToCalendar(salYYMM+"/01")));
					//傳入指定日期作為薪資年月的最後一天, dateformat = 'yyy/MM/dd'
					this.salyymm_last_day = tools.convertADDateToStrng(BA_TOOLS.getLastMonthDay(tools.covertStringToCalendar(salYYMM+"/01")));
					
				}catch(Exception e){
					e.printStackTrace();
					msgMap.put("ERRMSG", "準備系統假別資訊的資料時，發生錯誤!!" + e.toString());
					throw new Exception("準備系統假別資訊的資料時，發生錯誤!!" + e.toString());
				}
				
				//釋放資源
				System.gc();
				
				//薪資計算開始
				System.out.println("開始計算=" + tools.getTime() + " 總筆數:" + cntEmpList.size() + " 未處理總筆數:" + ngEmpList.size() );
				//寫入薪資系統開始計算的訊息
				EMS_SalaryTools.ems_msg_tools.writeMSGForm( conn, "SALARY_MSG_0001", "COUNT_SALARY_START", 
												 "EMS_SalaryTools().count() -> Count Salary Start , " +
												 Calendar.getInstance().getTime() + " 總筆數:" + cntEmpList.size() + " 未處理總筆數:" + ngEmpList.size(),
												 user_name, EMS_MSGSYSTEM.Important, EMS_MSGSYSTEM.E_SYSTEM_MANGER, comp_id);
				
				int saltemp = 0;  //薪資計算總金額
				
				//處理計薪人員
				for(int colCount=0; colCount < cntEmpList.size(); colCount++){
					
					//取得計薪人員
					EHF030600M2F ehf030600m2 = (EHF030600M2F) cntEmpList.get(colCount);
					
					//設定異動人員
					ehf030600m2.setUSER_UPDATE(user_name);
					
					try{
						//取得員工目前班別資訊
						AuthorizedBean user_authbean = new AuthorizedBean();
						user_authbean.setUserId(EMS_SalaryTools.sta_user_id);
						user_authbean.setCompId(comp_id);						
						
						//EMS_CheckVacation EMS_CheckVacation = new EMS_CheckVacation();
						//取得請假資訊
						Map leave = check_vacation_tools.countVacationTime(conn,EHF030600T0_U, salYYMM,costYYMM,ehf030600m2,this.vacation_type_map,comp_id,user_authbean);
						
						this.vacation_time_list = check_vacation_tools.getEMS_CheckVacation(); 
						
						//將所有人請假時數寫入資料庫(EHF020411T0)
						this.createleaveData(conn, this.vacation_time_list, comp_id);						
						
						//計算員工當月薪資, 非異動產生的薪資計算
						saltemp = this.cntSalDetail(conn, salary_no, EHF030600T0_U, "02", ehf030600m2, salYYMM, costYYMM, true, comp_id);
						
					}catch(Exception e){
						e.printStackTrace();
						msgMap.put("ERRMSG", "計算薪資時發生錯誤!! 員工工號:{" + ehf030600m2.getEHF030600T2_02() + "}。薪資年月:{" +ehf030600m2.getEHF030600T2_M()+ "}" + e.toString());
						throw new Exception("計算薪資時發生錯誤!! 員工工號:{" + ehf030600m2.getEHF030600T2_02() + "}。薪資年月:{" +ehf030600m2.getEHF030600T2_M()+ "}" + e.toString());
					}
					
					//if(saltemp != 0){
						EHF030600T0_AM = EHF030600T0_AM + saltemp;
						EHF030600T0_C++;
					//}
						
				}  //End For
				
				//處理不計薪人員
				for(int colCount=0; colCount < ngEmpList.size(); colCount++){
					
					//取得計薪人員
					EHF030600M2F ehf030600m2 = (EHF030600M2F) ngEmpList.get(colCount);
					
					//設定異動人員
					ehf030600m2.setUSER_UPDATE(user_name);
					
					try{
						
						//取得員工目前班別資訊
						AuthorizedBean user_authbean = new AuthorizedBean();
						user_authbean.setUserId(EMS_SalaryTools.sta_user_id);
						user_authbean.setCompId(comp_id);
						
						//取得請假資訊
						Map leave= check_vacation_tools.countVacationTime(conn,EHF030600T0_U, salYYMM,costYYMM,ehf030600m2,this.vacation_type_map,comp_id,user_authbean);
						
						if(leave!=null){//賴泓錡
							if(!"".equals(leave.get("EHF020411T0_05"))||!(leave.get("EHF020411T0_05")==null)){
								//計算每人每個月的請假總天數
								this.vacation_time_list.add(leave); 
							}
						}
						//將所有人請假時數寫入資料庫
						this.createleaveData(conn, this.vacation_time_list,comp_id);
																		
						//計算員工當月薪資, 非異動產生的薪資計算
						saltemp = this.cntSalDetail(conn, salary_no, EHF030600T0_U, "02", ehf030600m2, salYYMM, costYYMM, false, comp_id);
					
					}catch(Exception e){
						e.printStackTrace();
						msgMap.put("ERRMSG", "計算薪資時發生錯誤!! 員工工號:{" + ehf030600m2.getEHF030600T2_02() + "}。薪資年月:{" +ehf030600m2.getEHF030600T2_M()+ "}" + e.toString());
						throw new Exception("計算薪資時發生錯誤!! 員工工號:{" + ehf030600m2.getEHF030600T2_02() + "}。薪資年月:{" +ehf030600m2.getEHF030600T2_M()+ "}" + e.toString());
					}
					
					//if(saltemp != 0){
						EHF030600T0_NGC++;
					//}
					
						
				}  //End For
				
				//記錄處理總筆數
				ehf030600m0.setEHF030600T0_C(String.valueOf(EHF030600T0_C));
				//記錄處理總金額
				ehf030600m0.setEHF030600T0_AM(String.valueOf(EHF030600T0_AM));
				//記錄處理的異動人員
				ehf030600m0.setUSER_UPDATE(user_name);
				//釋放系統資源
				System.gc();
				
				
				//薪資計算處理完畢
				//修改薪資計算控制檔的 ==> 薪資處理狀態 = '02': 已完成薪資計算
				try{
					 
					this.chgSalCntStatus(conn, ehf030600m0, "02");
					 
					//修改EHF020900T0(考勤確認)狀態
					this.chgAttSalCntStatus(conn, ehf030600m0.getEHF030600T0_U(), "02", ehf030600m0.getUSER_UPDATE(), ehf030600m0.getEHF030600T0_14());
					
				}catch(Exception E){
					E.printStackTrace();
					msgMap.put("ERRMSG", "更改薪資控制檔的 ==> 薪資處理狀態  = '02' 時，發生錯誤!!" + E.toString());
					throw new Exception("更改薪資控制檔的 ==> 薪資處理狀態  = '02' 時，發生錯誤!!" + E.toString());
				}
				
				
			}else{
				msgMap.put("ERRMSG", "找不到可計算的員工清單!!");
			}
			
			//將員工清單寫入 Message Map
			msgMap.put("CNTLIST", cntEmpList);  //計薪員工清單
			msgMap.put("CNTCOUNT", EHF030600T0_C);  //計薪員工人數
			msgMap.put("NGLIST", ngEmpList);  //不計薪員工清單
			msgMap.put("NGCOUNT", EHF030600T0_NGC);  //不計薪員工人數
			
			System.out.println("薪資計算完畢=" + tools.getlongTime());
			//寫入薪資系統結束計算的訊息
			EMS_SalaryTools.ems_msg_tools.writeMSGForm( conn, "SALARY_MSG_0001", "COUNT_SALARY_END", 
					 "EMS_SalaryTools().count() -> Count Salary End , " +
					 Calendar.getInstance().getTime() + " 總筆數:" + cntEmpList.size() + " 未處理總筆數:" + ngEmpList.size() + 
					 " 薪資計算完畢=" + tools.getlongTime(),
					 user_name, EMS_MSGSYSTEM.Important, EMS_MSGSYSTEM.E_SYSTEM_MANGER, comp_id);
			
	
			
		}catch(Exception e){
			try {
				throw new Exception("薪資計算，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return msgMap;
	}
	
	/**
	 * 取的薪資計算控制檔的資料
	 * @param conn
	 * @param salary_no  薪資計算資料序號
	 * @param comp_id  公司代碼
	 * @param ehf030600m0  薪資計算控制檔的Form
	 * @return
	 */
	public EHF030600M0F getSalCntFile( Connection conn, String salary_no, String comp_id, EHF030600M0F ehf030600m0 ){
		
		
		try{
			
			String sql = "" +
			" SELECT * FROM EHF030600T0 " +
			" WHERE 1=1 " +
			" AND EHF030600T0_01 = "+salary_no+" " +  //薪資計算資料序號
			" AND EHF030600T0_14 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				ehf030600m0.setEHF030600T0_01(rs.getString("EHF030600T0_01"));  //薪資計算資料序號
				ehf030600m0.setEHF030600T0_U(rs.getString("EHF030600T0_U"));  //薪資計算UID
				ehf030600m0.setEHF030600T0_M(rs.getString("EHF030600T0_M"));  //入扣帳年月
				ehf030600m0.setEHF030600T0_02(rs.getString("EHF030600T0_02"));  //薪資年月
				ehf030600m0.setEHF030600T0_03(rs.getString("EHF030600T0_03"));  //公司組織代號
				ehf030600m0.setEHF030600T0_04(rs.getString("EHF030600T0_04"));  //發放類別
				ehf030600m0.setEHF030600T0_05(rs.getString("EHF030600T0_05"));  //天數計算類別
				ehf030600m0.setEHF030600T0_06(rs.getString("EHF030600T0_06"));  //天數
				ehf030600m0.setEHF030600T0_07(rs.getString("EHF030600T0_07"));  //發放方式
				ehf030600m0.setEHF030600T0_08(rs.getString("EHF030600T0_08"));  //依本俸百分比
				ehf030600m0.setEHF030600T0_09(rs.getString("EHF030600T0_09"));  //依固定金額
				ehf030600m0.setEHF030600T0_10(rs.getString("EHF030600T0_10"));  //本次發放金額
				ehf030600m0.setEHF030600T0_11(rs.getString("EHF030600T0_11"));  //是否已發放
				ehf030600m0.setEHF030600T0_12(rs.getString("EHF030600T0_12"));  //發放日期
				ehf030600m0.setEHF030600T0_13(rs.getString("EHF030600T0_13"));  //備註
				ehf030600m0.setEHF030600T0_14(rs.getString("EHF030600T0_14"));  //公司代碼
				ehf030600m0.setEHF030600T0_C(rs.getString("EHF030600T0_C"));  //處理總筆數
				ehf030600m0.setEHF030600T0_AM(rs.getString("EHF030600T0_AM"));  //處理總金額
				ehf030600m0.setEHF030600T0_SCP(rs.getString("EHF030600T0_SCP"));  //薪資處理狀態
				ehf030600m0.setEHF030600T0_AFK(rs.getString("EHF030600T0_AFK"));  //考勤薪資紀錄UID 20130909 by Hedwig
				ehf030600m0.setDATE_CREATE(rs.getString("DATE_CREATE"));  //資料建立日期
				ehf030600m0.setDATE_UPDATE(rs.getString("DATE_UPDATE"));  //資料最後異動日期
				ehf030600m0.setUSER_CREATE(rs.getString("USER_CREATE"));  //資料建立者
				ehf030600m0.setUSER_UPDATE(rs.getString("USER_UPDATE"));  //資料最後異動者
				ehf030600m0.setVERSION(rs.getInt("VERSION"));  //資料版本
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			try {
				throw new Exception("取得薪資計算控制檔時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return ehf030600m0;
	}
	
	/**
	 * 依據 入扣帳年月,薪資年月 取得薪資計算控制檔的資料
	 * @param conn
	 * @param costyymm
	 * @param salyymm
	 * @param comp_id
	 * @return
	 */
	public EHF030600M0F getSalCntFileByYYMM( Connection conn, String costyymm, String salyymm, String comp_id ){
		
		EHF030600M0F ehf030600m0 = null;
		
		try{
			
			ehf030600m0 = new EHF030600M0F();
			
			String sql = "" +
			" SELECT EHF030600T0_01 " +
			" FROM EHF030600T0 " +
			" WHERE 1=1 " +
			" AND EHF030600T0_M = '"+costyymm+"' " +  //入扣帳年月
			" AND EHF030600T0_02 = '"+salyymm+"' " +  //薪資年月
			" AND EHF030600T0_14 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				ehf030600m0 = this.getSalCntFile(conn, rs.getString("EHF030600T0_01"), comp_id, ehf030600m0);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			try {
				throw new Exception("取得薪資計算控制檔時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return ehf030600m0;
	}
	
	/**
	 * 取得員工薪資主檔資料
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public EHF030200M0F getEmpSalFile( Connection conn, String employee_id, String comp_id ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF030200M0F ehf030200m0 = null;
		
		try{
			
			ehf030200m0 = new EHF030200M0F();
			
			String sql = "" +
			" SELECT * " +
			" FROM EHF030200T0 " +
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01=EHF030200T0_01 AND EHF020403T0_04=EHF030200T0_13" +
			" WHERE 1=1 " +
			" AND EHF030200T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF030200T0_13 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
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
				//EMS產品新增參數 20130909 by Hedwig
				//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
				ehf030200m0.setEHF030200T0_17(rs.getString("EHF020403T0_05"));  //是否計算考勤
				ehf030200m0.setEHF030200T0_18(rs.getString("EHF030200T0_18"));  //員工加班費規則
				ehf030200m0.setEHF030200T0_19(rs.getInt("EHF030200T0_19"));  //全勤獎金金額
				ehf030200m0.setEHF030200T0_20(rs.getString("EHF030200T0_20"));  //全勤獎金扣費規則
				ehf030200m0.setEHF030200T0_21(rs.getString("EHF030200T0_21"));  //是否代扣福利金
				
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
		
		return ehf030200m0;
	}
	
	/**
	 * 取得員工的日薪或時薪( type=1 時薪, type=2 日薪 )
	 * @param conn
	 * @param type
	 * @param employee_id
	 * @param costyymm
	 * @param salyymm
	 * @param comp_id
	 * @return
	 */
	public int getEmpHourPay( Connection conn, int type, String employee_id, String costyymm, String salyymm, String comp_id ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		int pay = 0;
		
		try{
			int hour_pay = 0;
			float hour_pay_float = 0;
			int day_pay = 0;
			float day_pay_float = 0;
			
			//取得工作時數
			float work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
			
			//取得系統參數中, 日薪的計算方式
			String salary_day_pay_count_mode = tools.getSysParam(conn, comp_id, "SALARY_DAY_PAY_COUNT_MODE");
			//取得系統參數中, 時薪的計算方式
			String salary_hour_pay_count_mode = tools.getSysParam(conn, comp_id, "SALARY_HOUR_PAY_COUNT_MODE");
			
			//取得系統參數中, 以月薪計算時, 時薪的計算方式
			String salary_month_pay_mode = tools.getSysParam(conn, comp_id, "SALARY_BY_MONTH_PAY_MODE");
			
			//取得員工主檔設定
			EHF030200M0F ehf030200m0 = this.getEmpSalFile(conn, employee_id, comp_id);
			
			if("01".equals(ehf030200m0.getEHF030200T0_07())){
				//月薪
				//取得薪資年月的天數
				String last_day = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(salyymm+"/01")));
				Calendar last_cal = tools.covertStringToCalendar(last_day);
				int month_days = last_cal.get(Calendar.DAY_OF_MONTH);
				
				if("CALENDAR_DAY".equals(salary_month_pay_mode)){
					//日曆天
					//先計算日薪 = 月薪 / 日曆天
					//****月薪計算的日曆天 --> 要考慮 --> 是否為一個月以固定 30天計算  edit by joe 2012/07/18****
					day_pay_float = ehf030200m0.getEHF030200T0_04()/month_days;
					//修正數字
					day_pay = tools.fixNumByMode(day_pay_float, salary_day_pay_count_mode);
					
					//計算時薪 = 日薪 / 工作時數
					hour_pay_float = day_pay/work_hours;
					//修正數字
					hour_pay = tools.fixNumByMode(hour_pay_float, salary_hour_pay_count_mode);

				}else if("WORK_DAY".equals(salary_month_pay_mode)){
					//工作天
					
					//取得系統參數中, 以月薪計算且計算模式為工作天時, 工作天的取得方式
					String salary_month_work_day_mode = tools.getSysParam(conn, comp_id, "SALARY_BY_MONTH_WORK_DAY_MODE");
					
					if("BY_CALENDAR_DAY".equals(salary_month_work_day_mode)){
						//先計算日薪 = 月薪 / (日曆天 - 公休天)
						//****公休天的計算方式要考慮 --> 例行假日、國定假日 edit by joe 2012/07/18****
						//****月薪計算的日曆天 --> 要考慮 --> 是否為一個月以固定 30天計算  edit by joe 2012/07/18****
						day_pay_float = ehf030200m0.getEHF030200T0_04()/(month_days - this.getHaDaysInMonth(conn, salyymm, employee_id, comp_id));
					}else if("BY_FIXED_DAY".equals(salary_month_work_day_mode)){
						//取得系統參數中, 由USER設定的固定工作天
						float salary_work_day_user_set_fixed_day = Float.parseFloat(tools.getSysParam(conn, comp_id, "SALARY_WORK_DAY_USER_SET_FIXED_DAY"));
						//先計算日薪 = 月薪 / (USER設定的工作天)
						day_pay_float = ehf030200m0.getEHF030200T0_04()/salary_work_day_user_set_fixed_day;
					}else{
						//先計算日薪 = 月薪 / (日曆天 - 公休天)
						//****公休天的計算方式要考慮 --> 例行假日、國定假日 edit by joe 2012/07/18****
						//****月薪計算的日曆天 --> 要考慮 --> 是否為一個月以固定 30天計算  edit by joe 2012/07/18****
						day_pay_float = ehf030200m0.getEHF030200T0_04()/(month_days - this.getHaDaysInMonth(conn, salyymm, employee_id, comp_id));
					}
					//修正數字
					day_pay = tools.fixNumByMode(day_pay_float, salary_day_pay_count_mode);
					
					//計算時薪 = 日薪 / 工作時數
					hour_pay_float = day_pay/work_hours;
					//修正數字
					hour_pay = tools.fixNumByMode(hour_pay_float, salary_hour_pay_count_mode);

				}
			}else if("02".equals(ehf030200m0.getEHF030200T0_07())){
				//日薪
				//計算時薪 = 日薪 / 工作時數
				hour_pay_float = ehf030200m0.getEHF030200T0_04()/work_hours;
				//修正數字
				hour_pay = tools.fixNumByMode(hour_pay_float, salary_hour_pay_count_mode);
	
				//計算日薪 = 日薪
				day_pay_float = ehf030200m0.getEHF030200T0_04();
				//修正數字
				day_pay = tools.fixNumByMode(day_pay_float, salary_day_pay_count_mode);

			}else if("03".equals(ehf030200m0.getEHF030200T0_07())){
				//時薪
				hour_pay_float = ehf030200m0.getEHF030200T0_04();
				//修正數字
				hour_pay = tools.fixNumByMode(hour_pay_float, salary_hour_pay_count_mode);
			}
			
			switch(type){
			
			//取得時薪
			case 1:
				pay = hour_pay;
				break;
			
			//取得日薪
			case 2:
				pay = day_pay;
				break;						
			
			}
			
		}catch(Exception e){
			try {
				throw new Exception("取得員工：{" + employee_id + "}的日薪,時薪，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return pay;
	}
	
	/**
	 * 取得員工的日薪
	 * @param conn
	 * @param employee_id
	 * @param costyymm
	 * @param salyymm
	 * @param comp_id
	 * @return
	 */
	public int getEmpDayPay( Connection conn, String employee_id, String costyymm, String salyymm, String comp_id ){
		
		int day_pay = 0;
		
		try{
			//取得員工的日薪
			day_pay = this.getEmpHourPay(conn, 2, employee_id, costyymm, salyymm, comp_id);
			
		}catch(Exception e){
			try {
				throw new Exception("取得員工的日薪，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return day_pay;
	}
	
	/**
	 * 取得員工的時薪
	 * @param conn
	 * @param employee_id
	 * @param costyymm
	 * @param salyymm
	 * @param comp_id
	 * @return
	 */
	public int getEmpHourPay( Connection conn, String employee_id, String costyymm, String salyymm, String comp_id ){
		
		int hour_pay = 0;
		
		try{
			//取得員工的時薪
			hour_pay = this.getEmpHourPay(conn, 1, employee_id, costyymm, salyymm, comp_id);
			
		}catch(Exception e){
			try {
				throw new Exception("取得員工的時薪，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return hour_pay;
	}
	
	/**
	 * 更新薪資記錄控制檔 --> 總筆數,總金額,薪資處理狀態
	 * @param conn
	 * @param ehf030600m0
	 * @param status
	 */
	protected void chgSalCntStatus( Connection conn, EHF030600M0F ehf030600m0, String status ){
		
		try{
			
			//更新薪資計算控制檔資料
			String sql = "" +
			" UPDATE EHF030600T0 SET " +
			" EHF030600T0_C=?, EHF030600T0_AM=?, EHF030600T0_SCP=?, EHF030600T0_AFK=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030600T0_01 = '"+ehf030600m0.getEHF030600T0_01()+"' " +  //薪資計算資料序號
			" AND EHF030600T0_U = '"+ehf030600m0.getEHF030600T0_U()+"' " +  //薪資計算UID
			" AND EHF030600T0_14 = '"+ehf030600m0.getEHF030600T0_14()+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030600m0.getEHF030600T0_C());  //處理總筆數
			indexid++;
			p6stmt.setString(indexid, ehf030600m0.getEHF030600T0_AM());  //處理總金額
			indexid++;
			p6stmt.setString(indexid, status);  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, this.chk_number(conn,ehf030600m0.getEHF030600T0_U(),ehf030600m0.getEHF030600T0_14()));  //薪資處理UID
			indexid++;
			p6stmt.setString(indexid, ehf030600m0.getUSER_UPDATE());  //修改人員
			indexid++;
			
			//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改薪資計算控制檔狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	

	/**
	 * 當薪資計算時
	 * 更改EHF020900T0(考勤確認)狀態
	 * @param compId 
	 * @param userName 
	 * @param status 
	 * @param eHF030600T0U 
	 * @param conn 
	 * @param request
	 * @param conn
	 * @param costYYMM 
	 * @param salYYMM 
	 * @param Form 
	 */
	private void chgAttSalCntStatus(Connection conn, String eHF030600T0U, String status, String userName, String compId) {
		// TODO Auto-generated method stub
		BaseFunction base_tools = new BaseFunction(compId);
		String tmp_status = "";
		
			if("-1".equals(status)){
				tmp_status = "01";
			}else{
				tmp_status = status;
			}
		try{

			String sql = ""
				+ " UPDATE ehf020900t0 SET"
				+ " EHF020900T0_03=? ,USER_UPDATE=?,DATE_UPDATE=NOW(),VERSION=VERSION+1" 
				+ " WHERE 1=1 " 
				+ " AND EHF020900T0_04 = '"+compId+"'" 
				+ " AND EHF020900T0_03FK = '"+eHF030600T0U+"'";
			// 執行SQL
			PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, tmp_status); //EHF020900T0_03
			indexid++;
			p6stmt.setString(indexid, userName); //USER_UPDATE
			indexid++;		
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			base_tools.commit();
			base_tools.close();
		} catch (Exception e) {
			System.out.println(" 修改EHF020900T0錯誤!! ");
			e.printStackTrace();
		}
	}	
	
	/**
	 * 修改薪資控制檔的薪資處理狀態
	 * @param conn
	 * @param EHF030600T0_U
	 * @param status
	 * @param user_name
	 * @param comp_id
	 */
	public void chgSalCntStatus( Connection conn, String EHF030600T0_U, String status, String user_name, String comp_id ){
		
		String tmp_status = "";
		
		try{
			
			if("-1".equals(status)){
				tmp_status = "01";
			}else{
				tmp_status = status;
			}
			
			if("-1".equals(status)){
				//回復薪資計算控制檔資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030600T0 SET " +
				" EHF030600T0_SCP=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030600T0_U = '"+EHF030600T0_U+"' " +  //薪資計算UID
				" AND EHF030600T0_14 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}else{
				//更新薪資計算控制檔資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030600T0 SET " +
				" EHF030600T0_SCP=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030600T0_U = '"+EHF030600T0_U+"' " +  //薪資計算UID
				" AND EHF030600T0_14 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改薪資計算控制檔的薪資處理狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 修改薪資計明細資料的薪資處理狀態
	 * @param conn
	 * @param EHF030600T1_SCU
	 * @param status
	 * @param user_name
	 * @param comp_id
	 */
	public void chgSalCntDetailStatus( Connection conn, String EHF030600T1_SCU, String status, String user_name, String comp_id ){
		
		String tmp_status = "";
		
		try{
			
			if("-1".equals(status)){
				tmp_status = "01";
			}else{
				tmp_status = status;
			}
			
			if("-1".equals(status)){
				//回復薪資計算明細資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030600T1 SET " +
				" EHF030600T1_SCP=?, EHF030600T1_SCU=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030600T1_SCU = '"+EHF030600T1_SCU+"' " +  //薪資計算UID
				" AND EHF030600T1_08 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, "");  //薪資計算UID
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}else{
				//更新薪資計算明細資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030600T1 SET " +
				" EHF030600T1_SCP=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030600T1_SCU = '"+EHF030600T1_SCU+"' " +  //薪資計算UID
				" AND EHF030600T1_08 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改薪資計算控制檔的薪資處理狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 取得薪資計算狀態
	 * @param conn
	 * @param salYYMM
	 * @param comp_id
	 * @return
	 */
	public String getSalaryCntStatus( Connection conn, String salYYMM, String comp_id ){
		
		String salary_status = "01";
		
		try{
			//表頭資料查詢
			String sql = "" +
			" SELECT EHF030600T0_SCP AS SALARY_STATUS " +
			" FROM EHF030600T0 " +
			" WHERE 1=1 " +
			" AND EHF030600T0_02 = '"+salYYMM+"' " +  //薪資年月
			" AND EHF030600T0_04 = '01' " +  //發放類別 - 薪資
			" AND EHF030600T0_14 = '"+comp_id+"' " ;  //公司代碼

			sql += " ORDER BY EHF030600T0_01 DESC ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				salary_status = rs.getString("SALARY_STATUS");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得 "+salYYMM+" 薪資計算狀態時發生錯誤!!");
			e.printStackTrace();
		}
		
		return salary_status;
	}
	
	/**
	 * 建立員工的歷史薪資主檔,保險主檔資料 Use 入扣帳年月
	 * @param conn
	 * @param salary_no  薪資計算序號
	 * @param salYYMM  薪資年月
	 * @param costYYMM  入扣帳年月
	 * @param comp_id  公司代碼
	 * @param emp_id  員工工號
	 */
	public void addHistory(Connection conn, String salary_no, String salYYMM, String costYYMM, String comp_id, String[] emp_id ){
		
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		
		try{
			
			if(emp_id == null){
				Exception EX = new Exception("員工工號=NULL，無法產生資料");
				throw EX;			
			}else{
				
				Statement stmt = null;
				Statement stmt1 =null;	
				ResultSet rs = null;
				
				//先刪除舊資料
				//刪除 EHF030600T2
				String sql = "" +
				" DELETE FROM EHF030600T2 " +
				" WHERE 1=1 " +
				" AND EHF030600T2_01 = "+salary_no+" " +  //薪資計算序號
				//" AND EHF030600T2_M = '"+salYYMM+"' " +  //薪資年月
				//" AND EHF030600T2_04 = '"+costYYMM+"' " +  //入扣帳年月
				" AND EHF030600T2_23 = '"+comp_id+"' ";  //公司代碼
				
				sql = sql + " "+this.getTestIdSQL("EHF030600T2_02", emp_id) + " ";
				
				stmt1 = conn.createStatement();
				stmt1.execute(sql);
				stmt1.close();
				
				//刪除EHF030600T3
				sql = "" +
				" DELETE FROM EHF030600T3 " +
				" WHERE 1=1 " +
				" AND EHF030600T3_01 = "+salary_no+" " +  //薪資計算序號
				//" AND EHF030600T3_M1 = '"+salYYMM+"' " +  //薪資年月
				//" AND EHF030600T3_M = '"+costYYMM+"' " +  //入扣帳年月
				" AND EHF030600T3_07 = '"+comp_id+"' ";  //公司代碼
				
				sql = sql + " "+this.getTestIdSQL("EHF030600T3_02", emp_id) + " ";
				
//				if (!"".equals(emp_id) && emp_id != null ){
//					sql += " AND EHF030600T3_02 = '" +emp_id+ "' ";  //員工工號
//				}
				
				stmt1 = conn.createStatement();
				stmt1.execute(sql);
				stmt1.close();
				
//				//刪除目前在此單位人員的該月份歷史資料(預防有人調區處)
				sql = " SELECT EHF030200T0_01 " +
					  " FROM EHF030200T0 " +
					  " WHERE 1=1 " +
					  " AND EHF030200T0_13 = '" +comp_id+ "' ";  //公司代碼
				
				sql = sql + " "+this.getTestIdSQL("EHF030200T0_01", emp_id)+ " ";
				
//				if (!"".equals(emp_id) && emp_id != null ){
//					sql += " AND EHF030200T0_01 = '" +emp_id+ "' ";  //員工工號
//				}
				
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				
				while (rs.next()){
					
					//刪除 EHF030600T2
					stmt1= conn.createStatement();
					
					sql = "" +
					" DELETE FROM EHF030600T2 " +
					" WHERE 1=1 " +
					" AND EHF030600T2_01 = "+salary_no+" " +  //薪資計算序號
					//" AND EHF030600T2_M = '"+salYYMM+"' " +  //薪資年月
					//" AND EHF030600T2_04 = '"+costYYMM+"' " +  //入扣帳年月
					" AND EHF030600T2_23 = '"+comp_id+"' " +  //公司代碼
					" AND EHF030600T2_02 = '"+rs.getString("EHF030200T0_01")+"'  ";  //員工工號
					
					stmt1.execute(sql);
					stmt1.close();
					
					//刪除EHF030600T3
					stmt1= conn.createStatement();
					
					sql = "" +
					" DELETE FROM EHF030600T3 " +
					" WHERE 1=1 " +
					" AND EHF030600T3_01 = "+salary_no+" " +  //薪資計算序號
					//" AND EHF030600T3_M1 = '"+salYYMM+"' " +  //薪資年月
					//" AND EHF030600T3_M = '"+costYYMM+"' " +  //入扣帳年月
					" AND EHF030600T3_07 = '"+comp_id+"' " +  //公司代碼
					" AND EHF030600T3_02 = '"+rs.getString("EHF030200T0_01")+"'  ";  //員工工號
					
					stmt1.execute(sql);
					stmt1.close();
				}
				
				rs.close();
				stmt.close(); 
				
				//勞工保險參數
				//取得普通事故保險費率
				float accident_insure_rate = (Float.parseFloat(ems_tools.getSysParam(conn, comp_id, "LABOR_ACCIDENT_INSURANCE_RATE"))/100);
				//取得就業保險費率
				float employment_insure_rate = (Float.parseFloat(ems_tools.getSysParam(conn, comp_id, "LABOR_EMPLOYMENT_INSURANCE_RATE"))/100);
				//取得單位的勞保職災費率%數
				float job_disaster_rate  = Float.parseFloat(ems_tools.getSysParam(conn, comp_id, "LABOR_JOB_DISASTER_RATE"));
				
				//全民健康保險參數
				//舊的全民健康保險費率
				float health_old_insure_rate = (Float.parseFloat(ems_tools.getSysParam(conn, comp_id, "HEALTH_OLD_INSURANCE_RATE"))/100);
				//現在的全民健康保險費率
				float health_now_insure_rate = (Float.parseFloat(ems_tools.getSysParam(conn, comp_id, "HEALTH_NOW_INSURANCE_RATE"))/100);
				//全民健康保險的平均眷屬人數
				float health_avg_dependents = (Float.parseFloat(ems_tools.getSysParam(conn, comp_id, "HEALTH_AVG_DEPENDENTS")));
				
				//Copy 資料 to EHF030600T2
				sql  =  " INSERT INTO EHF030600T2( EHF030600T2_01, EHF030600T2_02, EHF030600T2_03, " +
						" EHF030600T2_04, EHF030600T2_M, EHF030600T2_05,  EHF030600T2_06, EHF030600T2_07, " +
						" EHF030600T2_08, EHF030600T2_09, EHF030600T2_10, EHF030600T2_11, EHF030600T2_12, " +
						" EHF030600T2_INS_TYPE, " +  //員工保險類別
						" EHF030600T2_13, EHF030600T2_13_VERSION, EHF030600T2_14, EHF030600T2_15, EHF030600T2_16, " +  //勞保
						" EHF030600T2_17, EHF030600T2_17_VERSION, EHF030600T2_18, EHF030600T2_19, EHF030600T2_20, " +  //健保
						" EHF030600T2_21, EHF030600T2_22, EHF030600T2_23, " +
						" EHF030600T2_24, EHF030600T2_24_TYPE, EHF030600T2_25, EHF030600T2_26, " +  //代扣所得相關欄位
						" EHF030600T2_27, EHF030600T2_28, EHF030600T2_29, EHF030600T2_30, EHF030600T2_31, " +  //勞退相關欄位
						" EHF030600T2_32, EHF030600T2_33, EHF030600T2_34, EHF030600T2_35, EHF030600T2_36, " +  //保險參數相關欄位
						" EHF030600T2_SCU, " +
						" DATE_CREATE, DATE_UPDATE, USER_CREATE, USER_UPDATE, VERSION ) " +
						
						
						" select "+salary_no+", EHF030200T0_01, EHF030200T0_02, " +
						" '"+costYYMM+"', '"+salYYMM+"', EHF030200T0_03, EHF030200T0_04, EHF030200T0_05, " +
						" EHF030200T0_06, EHF030200T0_06_AC, EHF030200T0_07, EHF030200T0_10, '', " +
						" EHF030300T0_03, " +  //員工保險類別
						" EHF030300T0_07, EHF030300T0_07_VERSION,	EHF030104T1_03,	EHF030104T1_04,	EHF030103T1_05," +  //勞保  修改BY賴泓錡 20160204
						" EHF030300T0_05, EHF030300T0_05_VERSION,	EHF030103T1_03,	EHF030103T1_04, EHF030104T1_05," +  //建保  修改BY賴泓錡 20160204
						
						
						" EHF030300T0_11, EHF030300T0_10, '"+comp_id+"', " +
						" EHF030200T0_14, EHF030200T0_14_TYPE, EHF030200T0_15, EHF030200T0_16, " +  //代扣所得相關欄位
						" 0, EHF030300T0_15, 0, EHF030300T0_16, "+job_disaster_rate+", " +  //勞退相關欄位
						" "+accident_insure_rate+", "+employment_insure_rate+", "+health_old_insure_rate+", "+health_now_insure_rate+", "+health_avg_dependents+", " +  //保險參數相關欄位
						" '', " +
						" NOW(), NOW(), 'EMSADMIN', 'EMSADMIN', '1' " +
			            " FROM EHF030200T0 " +
			            " LEFT JOIN EHF030300T0 ON EHF030300T0_01 = EHF030200T0_01 AND EHF030300T0_14 = EHF030200T0_13 " +
			            " LEFT JOIN EHF030901T0 ON EHF030901T0_02 = EHF030200T0_10 AND EHF030901T0_06 = EHF030200T0_13 " +
			            //勞保  修改BY賴泓錡 20160204
			            " LEFT JOIN EHF030104T0 ON EHF030104T0_01 = EHF030300T0_07_VERSION AND EHF030104T0_05 = EHF030200T0_13 " + 
			            " LEFT JOIN EHF030104T1 ON EHF030104T1_01 = EHF030104T0_01 AND EHF030104T1_02 = EHF030300T0_07 AND EHF030104T1_08 = EHF030200T0_13 " +
			            
			            //建保  修改BY賴泓錡 20160204
			            " LEFT JOIN EHF030103T0 ON EHF030103T0_01 = EHF030300T0_05_VERSION AND EHF030103T0_05 = EHF030200T0_13 " + 
			            " LEFT JOIN EHF030103T1 ON EHF030103T1_01 = EHF030103T0_01 AND EHF030103T1_02 = EHF030300T0_05 AND EHF030103T1_08 = EHF030200T0_13 " +
			            " WHERE 1=1 " +
			            " AND EHF030300T0_01 IS NOT NULL " +  //若保險主檔未建立, 則不建置該員工歷史資料, 不會列入計薪範圍  edit by joe 2012/02/02
			            " AND EHF030200T0_13 = '"+comp_id+"' ";  //公司代碼
				
				sql = sql + " "+this.getTestIdSQL("EHF030200T0_01", emp_id) + " ";
				
//				if (!"".equals(emp_id) && emp_id != null ){
//					sql += " AND EHF030200T0_01 = '" +emp_id+ "' ";  //員工工號
//				}
				
				stmt1 = conn.createStatement();
				stmt1.execute(sql);
				stmt1.close();
				
				
				//Copy 薪資項目資料 to EHF030600T3
				sql = "" +
				" INSERT INTO EHF030600T3( EHF030600T3_01, EHF030600T3_M, EHF030600T3_M1, " +
				" EHF030600T3_02, EHF030600T3_03, EHF030600T3_04, EHF030600T3_05, EHF030600T3_06, " +
				" EHF030600T3_07, EHF030600T3_SCU, " +
				" DATE_CREATE, DATE_UPDATE, USER_CREATE, USER_UPDATE, VERSION ) " +
				" SELECT "+salary_no+", '"+costYYMM+"', '"+salYYMM+"', " +
				" EHF030200T1_01, EHF030102T0_04, EHF030102T0_05, EHF030101T0_02, EHF030102T0_06, " +
				" '"+comp_id+"', '', " +
				" NOW(), NOW(), 'EMSADMIN', 'EMSADMIN', '1' " +
				" FROM EHF030200T1 " +
				" LEFT JOIN EHF030102T0 ON EHF030102T0_01 = EHF030200T1_02 AND EHF030102T0_08 = EHF030200T1_04 " +
				" LEFT JOIN EHF030101T0 ON EHF030101T0_01 = EHF030102T0_03 AND EHF030101T0_05 = EHF030200T1_04 " +
				" WHERE 1=1 " +
				" AND EHF030200T1_04 = '"+comp_id+"' " +//公司代碼
				" AND EHF030200T1_06 = '1' "; 
				
				sql = sql + " "+this.getTestIdSQL("EHF030200T1_01", emp_id) + " ";
				
//				if (!"".equals(emp_id) && emp_id != null ){
//					sql += " AND EHF030200T1_01 = '" +emp_id+ "' ";  //員工工號
//				}
				
				stmt1 = conn.createStatement();
				stmt1.execute(sql);
				stmt1.close();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("產生薪資歷史資料發生錯誤"+e.getMessage());
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try {
				throw e;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 取得薪資年月可計薪的員工清單
	 * @param conn
	 * @param salYYMM  薪資年月
	 * @param comp_id  公司代碼
	 * @param emp_id  員工工號
	 * @return
	 */
	private Map getCntSalaryList( Connection conn, String salary_no, String costYYMM, String salYYMM, String comp_id, String[] emp_id ){
		
		Map return_Map = new HashMap();
		ArrayList cntList = new ArrayList();
		ArrayList ng_cntList = new ArrayList();
		Map empInfMap = new HashMap();
		//傳入薪資年月(SalYYMM), 取得薪資年月的第一天, dateformat = 'yyyy/MM/dd'
		String start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(salYYMM+"/01")));
		//傳入薪資年月(SalYYMM), 取得薪資年月的最後一天, dateformat = 'yyyy/MM/dd'
		String end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(salYYMM+"/01")));

		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			EHF030600M2F ehf030600m2 = null;
			
			String sql = "" +
			" SELECT EHF030200T0_01, EHF030200T0_02, " +
			" CASE EHF020403T0_05 WHEN '1' THEN true ELSE false END AS EHF020403T0_05 " +
			" FROM EHF030200T0 " +
			" LEFT JOIN EHF030300T0 ON EHF030300T0_01 = EHF030200T0_01 AND EHF030300T0_14 = EHF030200T0_13 " +
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF030200T0_01 AND EHF020403T0_04 = EHF030200T0_13" +
			" WHERE 1=1 " +
			//需要新增判斷, 員工在該薪資年月離職, 若薪資主檔設定為:未啟用, 還是必須要計算離職當月的薪資 add idea by joe 2013/02/04
			//
			" AND EHF030200T0_08 = '1'  " +  //已啟用
			" AND EHF030300T0_01 IS NOT NULL " +  //若保險主檔未建立, 則不建置該員工歷史資料, 不會列入計薪範圍  edit by joe 2012/02/02
			" AND EHF030200T0_13 = '"+comp_id+"' ";  //公司代碼
			
			sql = sql + " "+this.getTestIdSQL("EHF030200T0_01", emp_id) + " ";
			
//			if (!"".equals(emp_id) && emp_id != null ){
//				sql += " AND EHF030200T0_01 = '" +emp_id+ "' ";  //員工工號
//			}
			
			rs = stmt.executeQuery(sql);
			
			//增加判斷該員工考勤資料是否要納入薪資是否計算的依據 2012/07/06
			while(rs.next()){

				//產生人員資料ForBean
				ehf030600m2 = new EHF030600M2F();
				ehf030600m2 =(EHF030600M2F)tools.getFormBean(conn,
					" SELECT * FROM EHF030600T2 " +
					" WHERE 1=1 " +
					" AND EHF030600T2_01 = "+salary_no+" " +  //薪資計算序號
					" AND EHF030600T2_02 = '"+rs.getString("EHF030200T0_01")+"' " +  //員工工號
					" AND EHF030600T2_03 = '"+rs.getString("EHF030200T0_02")+"'  " +  //部門代號
					" AND EHF030600T2_04 = '"+costYYMM+"' " +  //入扣帳年月
					" AND EHF030600T2_M = '"+salYYMM+"' " +  //薪資年月
					" AND EHF030600T2_23 = '"+comp_id+"' " +  //公司代碼
					"", ehf030600m2);		
				
				//新增判斷，該員工若是離職，則不列入薪資計算範圍。20140917  新增By Alvin
				if(this.getEmployee_idList(rs.getString("EHF030200T0_01"), start_date, end_date, empInfMap)){
					
					//判斷  1.考勤異常資料是否已完全修正  2.當月是否有實際考勤資料
					if((this.chkAttFIX(conn, salYYMM, rs.getString("EHF030200T0_01"), comp_id) 
							&& this.chkAttExist(conn, salYYMM, rs.getString("EHF030200T0_01"), comp_id)
							&& rs.getBoolean("EHF020403T0_05"))){
						//需要計薪的人員 且 考勤設定為需要計算 edit by joe 2012/07/06
						cntList.add(ehf030600m2);
					
					}else if(!rs.getBoolean("EHF020403T0_05")){
						//考勤設定為不需要計算 edit by joe 2012/07/06
						cntList.add(ehf030600m2);
					
					}else{
						if(this.chack(conn,costYYMM,comp_id)){//考勤未修正完畢人員，如考勤確認，也要列入計算清單內。
							cntList.add(ehf030600m2);
						}else{				
							//不需要計薪的人員
							ng_cntList.add(ehf030600m2);
							//該人員考勤異常資料未完全修正
							System.out.println("員工："+rs.getString("EHF030200T0_01")+", 薪資年月："+salYYMM+" 考勤異常資料未完全修正, 該員工系統不列入計薪清單");
						}
					}
				}else{
					//該員工已經離職了
				}
				
			}
			
			rs.close();
			stmt.close();
			
			ArrayList delCntEmpList = new ArrayList();
			delCntEmpList.addAll(cntList);  //需要計薪的人員
			delCntEmpList.addAll(ng_cntList);  //不需要計薪的人員
			//刪除本月不需處理人員的薪資明細資料
			this.delSalData(conn, salary_no, costYYMM, salYYMM, comp_id, delCntEmpList);
			
			//寫入RETURN MAP 資訊
			return_Map.put("CNTLIST", cntList);
			return_Map.put("NGLIST", ng_cntList);
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("取得計薪員工清單，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return return_Map;
	}
	
	
	
	private int delSalData( Connection conn, String salary_no, String costYYMM, String salYYMM, String comp_id,
							ArrayList delCntEmpList ){
		
		int del_count = 0;
		
		//刪除薪資明細資料EHF030600T1中, 非本次可計薪人員的薪資明細資料, edit by joe 2012/01/10
		try{
			Iterator it = delCntEmpList.iterator();
			StringBuffer del_list = new StringBuffer();
			String sql = "";
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			//判斷是否有計薪清單
			
			if(it.hasNext()){
				
				//取得計薪人員
				EHF030600M2F ehf030600m2 = (EHF030600M2F) it.next();
				del_list.append("(");
				del_list.append("'");
				del_list.append(ehf030600m2.getEHF030600T2_02());
				del_list.append("'");
				
				while(it.hasNext()){
					//取得計薪人員
					ehf030600m2 = (EHF030600M2F) it.next();
					del_list.append(",");
					del_list.append("'");
					del_list.append(ehf030600m2.getEHF030600T2_02());
					del_list.append("'");
				}
				del_list.append(")");
				sql = " DELETE FROM EHF030600T1 " +
				  " WHERE 1=1 " +
				  " AND EHF030600T1_01 = "+salary_no+" " +  //薪資計算序號
				  " AND EHF030600T1_02 NOT IN "+del_list.toString()+" " +  //可計薪員工清單
				  " AND EHF030600T1_M = '"+costYYMM+"' " +  //入扣帳年月
				  " AND EHF030600T1_M1 = '"+salYYMM+"' " +  //薪資年月
				  " AND EHF030600T1_SCP <= '02' " +  //薪資處理狀態
				  " AND EHF030600T1_08 = '"+comp_id+"' ";  //公司代碼
				del_count = stmt.executeUpdate(sql);
			}
			
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("刪除非本次可計薪人員的薪資明細檔資料(EHF030600T1)時，發生錯誤!!" + e.toString());
		}
		
		return del_count;
	}
	
	/**
	 * 取得測試ID所需要的SQL
	 * @param table_column
	 * @param emp_id
	 * @return
	 */
	private String getTestIdSQL( String table_column, String[] emp_id ){
		
		String test_sql = "";
		
		try{
			
			//產生測試所需的SQL
			if (!"".equals(emp_id) && emp_id != null && emp_id.length > 0 ){
				test_sql = " AND ( ";
				for(int i=0; i<emp_id.length; i++){
					if(i==0){
						test_sql += " "+table_column+" = '" +emp_id[i]+ "' ";  //員工工號
					}else{
						test_sql += " OR "+table_column+" = '" +emp_id[i]+ "' ";  //員工工號
					}
				}
				test_sql += " ) ";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return test_sql;
	}
	
	/**
	 * 檢核員工考勤異常資料是否皆已修正
	 * @param conn
	 * @param salYYMM  薪資年月
	 * @param emp_id  員工工號
	 * @param comp_id  公司代碼
	 * @return
	 */
	private boolean chkAttFIX( Connection conn, String salYYMM, String emp_id, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			String sql = "" +
			" SELECT EHF020401T0_05 " +
			" FROM EHF020401T0 " +
			" LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+emp_id+"' " +  //員工工號
			" AND EHF020401T0_09 = true " +  //是否異常  --> 異常
			" AND EHF020401T0_FIX = false " +  //是否已修正  --> 未修正
			" AND EHF020401T0_05 BETWEEN '"+salYYMM+"/01' AND '"+salYYMM+"/31' " +  //打卡日期
			" AND EHF020401T0_08 IN ('1','2','7','8') " +  //打卡狀態代碼
			" AND EHF020401T0_11 = '"+comp_id+"' ";  //公司代碼
			
			sql += " UNION ";
			
			sql += "" +
			" SELECT EHF020406T0_04 " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+emp_id+"' " +  //員工工號
			" AND EHF020406T0_05_FLG = true " +  //是否異常  --> 異常
			" AND EHF020406T0_FIX = false " +  //是否已修正  --> 未修正
			" AND EHF020406T0_04 BETWEEN '"+salYYMM+"/01' AND '"+salYYMM+"/31' " +  //未打卡日期
			" AND EHF020406T0_05 IN ('1','2','7','8') " +  //打卡狀態代碼
			" AND EHF020406T0_07 = '"+comp_id+"' ";  //公司代碼
			
			rs = stmt.executeQuery(sql);
			
			if(!rs.next()){
				
				chk_flag = true;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
			try {
				throw new Exception("檢核員工考勤異常資料是否已修正，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return chk_flag;
	}
	
	/**
	 * 檢核員工當月實際考勤資料是否存在
	 * @param conn
	 * @param salYYMM
	 * @param emp_id
	 * @param comp_id
	 * @return
	 */
	private boolean chkAttExist( Connection conn, String salYYMM, String emp_id, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			String sql = "" +
			" SELECT EHF020401T0_05 " +
			" FROM EHF020401T0 " +
			" LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+emp_id+"' " +  //員工工號
			" AND EHF020401T0_05 BETWEEN '"+salYYMM+"/01' AND '"+salYYMM+"/31' " +  //打卡日期
			" AND EHF020401T0_08 IN ('1','2','7','8') " +  //打卡狀態代碼
			" AND EHF020401T0_11 = '"+comp_id+"' " +  //公司代碼
			" LIMIT 1 ";  
			
			sql += " UNION ";
			
			sql += "" +
			" SELECT EHF020406T0_04 " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+emp_id+"' " +  //員工工號
			" AND EHF020406T0_04 BETWEEN '"+salYYMM+"/01' AND '"+salYYMM+"/31' " +  //未打卡日期
			" AND EHF020406T0_05 IN ('1','2','7','8') " +  //打卡狀態代碼
			" AND EHF020406T0_07 = '"+comp_id+"' " +  //公司代碼
			" LIMIT 1 ";  
			
			rs = stmt.executeQuery(sql);
			
			//判斷至少要有一筆資料
			if(rs.next()){
				chk_flag = true;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
			try {
				throw new Exception("檢核員工當月實際考勤資料是否存在，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return chk_flag;
	}
	
	/**
	 * 處理員工薪資計算明細
	 * @param conn
	 * @param salary_no
	 * @param EHF030600T0_U
	 * @param status
	 * @param ehf030600m2
	 * @param salYYMM
	 * @param costYYMM
	 * @param comp_id
	 * @return
	 */
	private int cntSalDetail( Connection conn, String salary_no, String EHF030600T0_U, String status, EHF030600M2F ehf030600m2, 
						      String salYYMM, String costYYMM, boolean ng_flag, String comp_id ){
		
		int intRevSal = 0;
		String personUID = ""; //個人薪資計算明細UID
		String sql = "";
		boolean cnt_flag = false;
		
		try{
			
			if(!"02".equals(status)){
				
				return 0;
			}
			
			try{
				sql = "" +
				" SELECT EHF030600T1_01, EHF030600T1_U, EHF030600T1_SCU, EHF030600T1_SCP " +
				" FROM EHF030600T1 " +
				" WHERE 1=1 " +
				" AND EHF030600T1_01 = "+salary_no+" " +  //薪資計算序號
				" AND EHF030600T1_02 = '"+ehf030600m2.getEHF030600T2_02()+"' " +  //員工工號
				//" AND EHF030600T1_M = '"+costYYMM+"' " +  //入扣帳年月
				//" AND EHF030600T1_M1 = '"+salYYMM+"' " +  //薪資年月
				" AND EHF030600T1_SCP >= '03' " +  //薪資處理狀態
				" AND EHF030600T1_08 = '"+comp_id+"' ";  //公司代碼
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(!rs.next()){
					cnt_flag = true;
				}
				
				rs.close();
				stmt.close();
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("取得該員工是否已有薪資計算明細且薪資處理狀態 >= '已確認' 的資料時，發生錯誤!!" + e.toString());
			}
			
			
			if(cnt_flag){
				//計算薪資
				//取得薪資計算明細資料
				sql = "" +
				" SELECT * " +
				" FROM EHF030600T1 " +
				" WHERE 1=1 " +
				" AND EHF030600T1_01 = "+salary_no+" " +  //薪資計算序號
				" AND EHF030600T1_02 = '"+ehf030600m2.getEHF030600T2_02()+"' " +  //員工工號
				//" AND EHF030600T1_M = '"+costYYMM+"' " +  //入扣帳年月
				//" AND EHF030600T1_M1 = '"+salYYMM+"' " +  //薪資年月
				" AND EHF030600T1_SCP < '03' " +  //薪資處理狀態
				" AND EHF030600T1_08 = '"+comp_id+"' ";  //公司代碼
 				
				//找出尚未處理完畢的薪資計算UID
				EHF030600M1F ehf030600m1 = new EHF030600M1F();
				//getFormBean
				ehf030600m1 = (EHF030600M1F) tools.getFormBean(conn, sql, ehf030600m1, ehf030600m2.getUSER_UPDATE());
				
				if("".equals(ehf030600m1.getEHF030600T1_U()) || ehf030600m1.getEHF030600T1_U() == null){
					
					ehf030600m1.setEHF030600T1_01(salary_no);  //薪資計算資料序號
					//使用 CodeRules 做UID取得的動作
					personUID =  tools.createEMSUID(conn, "EO", "EHF030600T1", "EHF030600T1_01", comp_id);
					ehf030600m1.setEHF030600T1_U(personUID);  //薪資計算明細UID
					ehf030600m1.setEHF030600T1_M(costYYMM);  //入扣帳年月
					ehf030600m1.setEHF030600T1_M1(salYYMM);  //薪資年月
					ehf030600m1.setEHF030600T1_02(ehf030600m2.getEHF030600T2_02());  //員工工號
					ehf030600m1.setEHF030600T1_03(ehf030600m2.getEHF030600T2_03());  //部門代號
					ehf030600m1.setEHF030600T1_04_DESC("");  //本薪計算記錄
					ehf030600m1.setEHF030600T1_041_DESC("");  //請假扣薪記錄
					ehf030600m1.setEHF030600T1_TYPE("01");  //薪資處理種類  --> '01' = 薪資
					ehf030600m1.setEHF030600T1_DS("01");  //資料來源 --> '01' = EMS系統
					ehf030600m1.setEHF030600T1_08(comp_id);  //公司代碼
					ehf030600m1.setEHF030600T1_SCU("");  //薪資計算UID
					ehf030600m1.setEHF030600T1_SCP("01");  //薪資處理狀態 --> '01' = 處理中
					ehf030600m1.setUSER_CREATE(ehf030600m2.getUSER_UPDATE());  //資料建立者
					ehf030600m1.setUSER_UPDATE(ehf030600m2.getUSER_UPDATE());  //資料異動者
					ehf030600m1.setVERSION(1);  //版本
					
					//新增薪資計算明細資料
					try {	
						this.createSalData(conn, ehf030600m1);
						
					}catch(Exception E){
						throw new Exception("計算薪資，新增薪資計算明細資料時，發生錯誤!!"+ E.toString());
					}
					
				}else{
					//已有計算過的資料, 取出薪資計算明細UID
					personUID = ehf030600m1.getEHF030600T1_U();  //薪資計算明細UID
				}
				
				//判斷是否為不非計薪人員
				if(ng_flag){
					//正式開始執行薪資計算流程
					try{
						if("01".equals(ehf030600m0.getEHF030600T0_04())){
							//薪資計算
							ehf030600m1 = this.cntRealSal(conn, salary_no, EHF030600T0_U, ehf030600m2, ehf030600m1, comp_id);
						}else if("02".equals(ehf030600m0.getEHF030600T0_04()) || "03".equals(ehf030600m0.getEHF030600T0_04())){
							//獎金,臨時計算
							ehf030600m1 = this.cntRealBonus(conn, EHF030600T0_U, ehf030600m2, ehf030600m1, comp_id);
						}else{
							//未設定當作薪資計算
							ehf030600m1 = this.cntRealSal(conn, salary_no, EHF030600T0_U, ehf030600m2, ehf030600m1, comp_id);
						}
						
					}catch(Exception e){
						e.printStackTrace();
						throw new Exception("cntRealSal()計算薪資時，發生錯誤!!"+ e.toString());
					}
				}
				
				//儲存薪資計算明細檔
				try{
					this.updateSalData(conn, ehf030600m1);
					
				}catch(Exception e){
					e.printStackTrace();
					throw new Exception("計算薪資，儲存薪資計算明細檔時，發生錯誤!!"+ e.toString());
				}
				
				//更新 EHF030600T2 的資料
				try{
					this.chgEHF030600T2_SCU(conn, salary_no, EHF030600T0_U, ehf030600m1.getEHF030600T1_02(), ehf030600m1.getUSER_UPDATE(), comp_id);
					
				}catch(Exception e){
					e.printStackTrace();
					throw new Exception("計算薪資，更新 EHF030600T2 的資料時，發生錯誤!!"+ e.toString());
				}
				
				
				//回傳實發金額
				intRevSal = ehf030600m1.getEHF030600T1_07(); 
				
				
			}else{
				//已有確認的薪資計算明細資料的處理流程
				
			}
				
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("計算薪資 cntSalDetail()，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		return intRevSal;
	}
	
	/**
	 * 新增薪資計算明細資料
	 * @param conn
	 * @param ehf030600m1
	 */
	public void createSalData( Connection conn, EHF030600M1F ehf030600m1 ){
		
		String sql = "";
		
		try{
			
			sql = "" +
			" INSERT INTO EHF030600T1 ( EHF030600T1_01, EHF030600T1_U, EHF030600T1_M, EHF030600T1_M1, EHF030600T1_02, EHF030600T1_03, " +
			" EHF030600T1_04, EHF030600T1_04_DESC, EHF030600T1_04_O, EHF030600T1_041, EHF030600T1_05, EHF030600T1_05_D, EHF030600T1_06, " +
			" EHF030600T1_071, EHF030600T1_072, EHF030600T1_07, EHF030600T1_08, EHF030600T1_09, " +
			" EHF030600T1_10, EHF030600T1_11, EHF030600T1_12, EHF030600T1_13, EHF030600T1_13_VERSION, EHF030600T1_14, EHF030600T1_15, " +
			" EHF030600T1_16, EHF030600T1_17, EHF030600T1_17_VERSION, EHF030600T1_18, EHF030600T1_19, " +
			" EHF030600T1_DS, EHF030600T1_SCU, EHF030600T1_SCP, EHF030600T1_TYPE, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_01());  //薪資計算資料序號
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_U());  //薪資計算明細UID
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_M());  //入扣帳年月
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_M1());  //薪資年月
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_02());  //員工工號
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_03());  //部門代號
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_04());  //本薪金額
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_04_DESC());  //本薪計算記錄
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_04_O());  //新資項目總金額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_041());  //請假扣薪金額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_05());  //其他費用 補款金額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_05_D());  //其他費用 扣款金額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_06());  //新資所得扣繳金額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_071());  //應發金額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_072());  //應扣金額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_07());  //實發金額
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_08());  //公司代碼
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_09());  //津貼費用
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_10());  //加班費
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_11());  //差旅費
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_12());  //不休假加班費
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_13());  //健保費
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_13_NUMBER());  
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_14());  //健保費公司補助額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_15());  //勞保費
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_16());  //勞保費公司補助額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_17());  //勞退費
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_17_NUMBER());  
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_18());  //勞退費公司補助額
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_19());  //福利金
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_DS());  //資料來源
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_SCU());  //新資計算UID
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_SCP());  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_TYPE());  //薪資處理種類
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getUSER_CREATE());  //建立人員
			indexid++;
			p6stmt.setString(indexid, ehf030600m1.getUSER_UPDATE());  //修改人員
			indexid++;
			p6stmt.setInt(indexid, ehf030600m1.getVERSION());  //版本
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();	
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("createSalData() 新增薪資計算明細資料時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 更新薪資計算明細資料
	 * @param conn
	 * @param ehf030600m1
	 */
	public void updateSalData( Connection conn, EHF030600M1F ehf030600m1 ){
		
		String sql = "";
		
		try{
			
			if("01".equals(ehf030600m1.getEHF030600T1_SCP()) || "02".equals(ehf030600m1.getEHF030600T1_SCP())){
			
				sql = "" +
				" UPDATE EHF030600T1 SET " +
				" EHF030600T1_M=?, 	EHF030600T1_M1=?, EHF030600T1_02=?, EHF030600T1_03=?, " +
				" EHF030600T1_04=?, EHF030600T1_04_DESC=?, EHF030600T1_04_O=?, EHF030600T1_041=?, EHF030600T1_041_DESC=?," +
				" EHF030600T1_05=?, EHF030600T1_05_D=?, EHF030600T1_06=?, EHF030600T1_071=?, EHF030600T1_072=?, EHF030600T1_07=?, " +
				" EHF030600T1_08=?, EHF030600T1_09=?, EHF030600T1_10=?, EHF030600T1_10_DAY=?, EHF030600T1_11=?, EHF030600T1_12=?, " +
				" EHF030600T1_13=?, EHF030600T1_13_VERSION=?, EHF030600T1_14=?, EHF030600T1_15=?, EHF030600T1_16=?, EHF030600T1_17=?,EHF030600T1_17_VERSION=?,	EHF030600T1_18=?, " +
				" EHF030600T1_19=?,	EHF030600T1_20=?, EHF030600T1_21=?, EHF030600T1_22=?, EHF030600T1_23=?,	EHF030600T1_24=?," +
				" EHF030600T1_25=?, EHF030600T1_26=?, EHF030600T1_27=?, EHF030600T1_28=?, EHF030600T1_29=?, EHF030600T1_30=?," +
				" EHF030600T1_DS=?, EHF030600T1_SCU=?, EHF030600T1_SCP=?, EHF030600T1_TYPE=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030600T1_U = '"+ehf030600m1.getEHF030600T1_U()+"' ";  //薪資計算明細UID

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
			
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_M());  //入扣帳年月
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_M1());  //薪資年月
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_02());  //員工工號
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_03());  //部門代號
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_04());  //本薪金額
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_04_DESC());  //本薪計算記錄
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_04_O());  //新資項目總金額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_041());  //請假扣薪金額
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_041_DESC());  //請假扣薪記錄
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_05());  //其他費用 補款金額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_05_D());  //其他費用 扣款金額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_06());  //新資所得扣繳金額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_071());  //應發金額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_072());  //應扣金額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_07());  //實發金額
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_08());  //公司代碼
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_09());  //津貼費用
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_10());  //加班費
				indexid++;
				p6stmt.setFloat(indexid, ehf030600m1.getEHF030600T1_10_DAY());  //加班時數
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_11());  //差旅費
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_12());  //不休假加班費
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_13());  //健保費
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_13_NUMBER());  
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_14());  //健保費公司補助額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_15());  //勞保費
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_16());  //勞保費公司補助額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_17());  //勞退費
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_17_NUMBER());  
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_18());  //勞退費公司補助額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_19());  //福利金
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_20());  //全勤獎金金額
				indexid++;
				p6stmt.setFloat(indexid, ehf030600m1.getEHF030600T1_21());  //在職天數
				indexid++;
				p6stmt.setFloat(indexid, ehf030600m1.getEHF030600T1_22());  //出勤天數
				indexid++;
				p6stmt.setFloat(indexid, ehf030600m1.getEHF030600T1_23());  //公休天數
				indexid++;
				
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_24());  //遲到分鐘/次數
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_25());  //遲到扣薪金額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_26());  //早退分鐘/次數
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_27());  //早退扣薪金額
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_28());  //曠職分鐘/次數
				indexid++;
				p6stmt.setInt(indexid, ehf030600m1.getEHF030600T1_29());  //曠職扣薪金額
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_30());  //遲到早退曠職扣薪金額
				indexid++;
				
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_DS());  //資料來源
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_SCU());  //新資計算UID
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_SCP());  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_TYPE());  //薪資處理種類
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getUSER_UPDATE());  //修改人員
				indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();	
			
			}else{
				
				sql = "" +
				" UPDATE EHF030600T1 SET " +
				" EHF030600T1_SCP=?,  " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" ADN EHF030600T1_U = '"+ehf030600m1.getEHF030600T1_U()+"' ";  //薪資計算明細UID

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,
						pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, ehf030600m1.getEHF030600T1_SCP());  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, ehf030600m1.getUSER_UPDATE());  //修改人員
				indexid++;
				
				//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();	
				
			}
			
			//更新資料庫
			conn.commit();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("createSalData() 新增薪資計算明細資料時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 更新 EHF030600T2_SCU
	 * @param conn
	 * @param EHF030600T0_U
	 * @param salary_no
	 * @param emp_id
	 * @param USER_UPDATE
	 * @param comp_id
	 */
	public void chgEHF030600T2_SCU( Connection conn, String salary_no, String EHF030600T0_U, String emp_id,
								    String USER_UPDATE, String comp_id ){
		
		try{
			String sql = "";
		        
		    sql = " UPDATE EHF030600T2 " +
		       	  " SET EHF030600T2_SCU=?, USER_UPDATE=?, DATE_UPDATE=NOW(), VERSION=VERSION+1 ";
		    sql += " WHERE 1=1 " +
		       	   " AND EHF030600T2_01 = '" +salary_no+ "' " +  //薪資計算資料序號
		       	   " AND EHF030600T2_02 = '"+emp_id+"' " +  //員工工號
		       	   " AND EHF030600T2_23 = '"+comp_id+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			 int indexid = 1;
			
			p6stmt.setString(indexid, EHF030600T0_U);  //EHF030600T2_SCU  薪資計算UID
			indexid++;
			p6stmt.setString(indexid, USER_UPDATE);  //USER_UPDATE  最後異動人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();	
			
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改 EHF030600T2 的薪資處理狀態與必要欄位時發生錯誤,員工工號:{" +emp_id+ "} " + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
	}
	
	/**
	 * 實際的員工薪資計算流程
	 * @param conn
	 * @param salary_no
	 * @param EHF030600T0_U
	 * @param ehf030600m2
	 * @param ehf030600m1
	 * @param comp_id
	 * @return
	 */
	private EHF030600M1F cntRealSal( Connection conn, String salary_no, String EHF030600T0_U, 
									 EHF030600M2F ehf030600m2, EHF030600M1F ehf030600m1, String comp_id  ){
		
		String status = "2"; //表示要計算
		HR_TOOLS hr_tools = new HR_TOOLS();
		try{
			
			ehf030600m1.setUSER_UPDATE(ehf030600m2.getUSER_UPDATE());  //最後異動人員
			
			//取得EMS-Flow員工基本資料Map
			Map empInfMap = hr_tools.getEmpInfMapByEmpId(EMS_SalaryTools.sta_user_id, ehf030600m1.getEHF030600T1_02(), comp_id);
			
			//取得員工薪資主檔資料
			EHF030200M0F ehf030200m0 = this.getEmpSalFile(conn, ehf030600m1.getEHF030600T1_02(), comp_id);
			
			//判斷薪資是否有破月情況
			Map breakMonthMap = this.chkBreakMonth(empInfMap);
			
			//乾淨的基本薪資
			int clean_basic_salary = 0;
			
			
			Map basic_map = this.getBasicSalary( conn, breakMonthMap, ehf030200m0 );

			
			//以下稍作修改，因戴養菌元的薪資計算方式為(出勤天數+假日次數+不扣日數+增減工作天數+遲到早退)*日薪單價。

			//計算本薪金額
			try{
				//計算當月員工實際的基本薪資金額 
				//若員工 15日到職 基本薪資只計算  15日 至 當月月底 所應領的基本薪資
				//若員工 15日離職 基本薪資只計算  當月 1 日 至 15 日 所應領的基本薪資
				//取得本薪金額
				//Map basic_map = this.getBasicSalary( conn, breakMonthMap, ehf030200m0 );//   2013/9/17註解
				
				//float work_day		= Float.valueOf( (String)basic_map.get("work_day"));//出勤天數
				//float holiday		= Float.valueOf( (String)basic_map.get("holiday"));//假日次數
				//float NO_deduct		= Float.valueOf( (String)basic_map.get("NO_deduct"));//不扣日數
				
				
				ehf030600m1.setEHF030600T1_04((Integer)basic_map.get("BASIC_MONEY"));//發放日薪(金額)
				//本薪計算記錄
				ehf030600m1.setEHF030600T1_04_DESC((String)basic_map.get("BASIC_DESC"));
				//設定乾淨的基本薪資
				clean_basic_salary = (Integer)basic_map.get("CLEAN_BASIC_MONEY");
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算本薪金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
									"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			
			//EMS產品新增遲到早退曠職計算 20130911 by Hedwig  BY賴泓錡
			try{
				Map AttDeduct = this.getAttDeductionInf(conn,ehf030600m1.getEHF030600T1_M1(),ehf030600m1.getEHF030600T1_02(),comp_id);
				ehf030600m1.setEHF030600T1_24(Integer.valueOf((String)AttDeduct.get("EHF030600T1_24")));
				ehf030600m1.setEHF030600T1_25(Integer.valueOf((String)AttDeduct.get("EHF030600T1_25")));
				ehf030600m1.setEHF030600T1_26(Integer.valueOf((String)AttDeduct.get("EHF030600T1_26")));
				ehf030600m1.setEHF030600T1_27(Integer.valueOf((String)AttDeduct.get("EHF030600T1_27")));
				ehf030600m1.setEHF030600T1_28(Integer.valueOf((String)AttDeduct.get("EHF030600T1_28")));
				ehf030600m1.setEHF030600T1_29(Integer.valueOf((String)AttDeduct.get("EHF030600T1_29")));
				ehf030600m1.setEHF030600T1_30((String)AttDeduct.get("EHF030600T1_30"));
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算遲到早退曠職發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			

			//計算薪資項目總金額
			try{
				//依據 EHF030600T3 新資歷史新資項目取得新資項目資料
				//計算新資項目總金額(含薪資項目計算公式的計算)
				//計算完畢後, 回寫  EHF030600T3_06_R 實際金額, EHF030600T3_NOTE 薪資計算公式異動註解
				//取得薪資項目總金額
				ehf030600m1.setEHF030600T1_04_O(this.getAllSalaryItemMoney( conn, ehf030600m1.getEHF030600T1_02(), EHF030600T0_U ));
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算薪資項目總金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//EMS產品新增全勤獎金計算 20130911 by Hedwig
			try{
				Map AttBonusMap = this.getAttBonusMap(conn,ehf030600m1.getEHF030600T1_M1(),ehf030200m0,ehf030600m1,comp_id);

				ehf030600m1.setEHF030600T1_20((Integer.valueOf((String)AttBonusMap.get("MONEY"))));
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算全勤獎金發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算請假扣薪金額
			try{
				//依據當月考勤異常修正後的資料取得相關對應的表單
				//依據表單類型,時數計算扣薪金額
				Map vamap = this.getVaDeductionMoney( conn, ehf030600m1.getEHF030600T1_02());
				ehf030600m1.setEHF030600T1_041( (Integer)vamap.get("MONEY") );
				ehf030600m1.setEHF030600T1_041_DESC( (String)vamap.get("HOURS") );
				
				//取得非假假別要扣除本薪的金額
				ehf030600m1.setEHF030600T1_04(ehf030600m1.getEHF030600T1_04() - (Integer)vamap.get("NOT_VA_MONEY") );
				//記錄非假假別扣除的時數
				ehf030600m1.setEHF030600T1_04_DESC(ehf030600m1.getEHF030600T1_04_DESC() +","+(String)vamap.get("NOT_VA_HOURS") );
								
//				ehf030600m1.setEHF030600T1_041( 0);
//				ehf030600m1.setEHF030600T1_041_DESC("0");
//				
//				//取得非假假別要扣除本薪的金額
//				ehf030600m1.setEHF030600T1_04(0);
//				//記錄非假假別扣除的時數
//				ehf030600m1.setEHF030600T1_04_DESC("0");
								
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算請假扣薪金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算津貼總金額
			try{
				//依據當月產生的津貼明細資料 --> 進行加總計算津貼總金額
				ehf030600m1.setEHF030600T1_09(this.sumAllowanceMoney(conn, EHF030600T0_U, ehf030600m1.getEHF030600T1_02()));
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算津貼總金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
								
			//計算加班費總金額
			try{
				//依據當月產生的加班費明細資料 --> 進行加總加班費總金額
				ehf030600m1.setEHF030600T1_10(new Float(this.sumOvertimeMoney(conn, EHF030600T0_U, ehf030600m1.getEHF030600T1_02(),"01")).intValue());
				ehf030600m1.setEHF030600T1_10_DAY(this.sumOvertimeMoney(conn, EHF030600T0_U, ehf030600m1.getEHF030600T1_02(),"02"));
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算加班費總金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算不休假加班費總金額
			try{
				//依據當月產生的不休假加班費明細資料 --> 進行加總不休假加班費總金額
				ehf030600m1.setEHF030600T1_12(this.sumHaOvertimeMoney(conn, EHF030600T0_U, ehf030600m1.getEHF030600T1_02()));
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算不休假加班費總金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算差旅費總金額
			try{
				//依據當月產生的差旅費明細資料 --> 進行加總差旅費總金額
				ehf030600m1.setEHF030600T1_11(0);
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算不休假加班費總金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算其他費用
			try{
				//計算當月的其他費用 "補款" 總金額
				ehf030600m1.setEHF030600T1_05(this.sumOtherFee(conn, ehf030600m1.getEHF030600T1_02(), "01", EHF030600T0_U));
				
				//計算當月的其他費用 "扣款" 總金額
				ehf030600m1.setEHF030600T1_05_D(this.sumOtherFee(conn, ehf030600m1.getEHF030600T1_02(), "02", EHF030600T0_U));
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算其他費用總金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算勞保費用
			try{
				//計算勞保費用
				ehf030600m1.setEHF030600T1_15(this.getLaborFee( conn, ehf030600m2, breakMonthMap));
				//計算勞保費公司補助額
				ehf030600m1.setEHF030600T1_16(this.getLaborCompSubsidy( conn, ehf030600m2, breakMonthMap));
				ehf030600m1.setEHF030600T1_17_NUMBER(ehf030600m2.getEHF030600T2_13_VERSION());//建保使用版本
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算勞保費用發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算健保費
			try{
				//計算健保費用
				ehf030600m1.setEHF030600T1_13(this.getHealthFee( conn, ehf030600m2, breakMonthMap));
				//計算健保費公司補助額
				ehf030600m1.setEHF030600T1_14(this.getHealthCompSubsidy( conn, ehf030600m2, breakMonthMap));
				ehf030600m1.setEHF030600T1_13_NUMBER(ehf030600m2.getEHF030600T2_17_VERSION());//建保使用版本
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算健保費用發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算勞退費
			try{
				// 2012/01/04 目前勞退計算先直接使用保險主檔填入勞退金額, 之後導入人事完整系統時再改為用%數計算的方式, 目前已預留欄位. edit by joe
				//計算勞退費用 - 員工自提
				ehf030600m1.setEHF030600T1_17(ehf030600m2.getEHF030600T2_28());
				
				//計算勞退費用 - 公司提撥
				ehf030600m1.setEHF030600T1_18(ehf030600m2.getEHF030600T2_30());
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算勞退費用發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//計算福利金
			try{
				if(tools.StringToBoolean(ehf030200m0.getEHF030200T0_21())){
					//計算福利金
					ehf030600m1.setEHF030600T1_19(this.getWelFare(conn, clean_basic_salary, ehf030600m1.getEHF030600T1_04(), comp_id));
				}else
					ehf030600m1.setEHF030600T1_19(0);
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算福利金發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
						
			//a.	將在職天數寫入EHF030600T1_21 = basci_map.get(“SCHEDUL_DAYS”)。
			//b.	將出勤天數寫入EHF030600T1_22 = basci_map.get(“ONWORK_DAYS”)。
			//c.	將公休天數寫入EHF030600T1_23 = this.getSchedulDays。

			try{
				String count_start_date = "";  //當月薪資計算起始日
				String count_end_date = "";  //當月薪資計算結束日
				//判斷是否薪資有破月情況產生
				if((Boolean)breakMonthMap.get("BREAK_MONTH_FLAG")){
					//破月
					//破月情況
					//取得當月薪資計算起始日
					if("".equals((String)breakMonthMap.get("COUNT_START_DATE"))){
						count_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
					}else{
						count_start_date = (String)breakMonthMap.get("COUNT_START_DATE");
					}
					
					//取得當月薪資計算結束日
					if("".equals((String)breakMonthMap.get("COUNT_END_DATE"))){
						count_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
					}else{
						count_end_date = (String)breakMonthMap.get("COUNT_END_DATE");
					}
				}else{
					//未破月
					//取得當月薪資計算起始日,結束日
					count_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
					count_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				}
				
				
				ehf030600m1.setEHF030600T1_21( Float.parseFloat(basic_map.get("ONWORK_DAYS")+""));	//在職天數
				ehf030600m1.setEHF030600T1_22( Float.parseFloat(basic_map.get("SCHEDUL_DAYS")+""));	//出勤天數
				
				//float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
				//ehf030600m1.setEHF030600T1_23((this.getSchedulDays(conn, this.sta_user_employee_id, count_start_date, count_end_date,1, comp_id) / Day_work_hours));
				
				ehf030600m1.setEHF030600T1_23((this.getHaDaysInMonth(conn, EMS_SalaryTools.sta_salyymm, ehf030600m1.getEHF030600T1_02(), comp_id)));	//公休天數
				
			
			
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算遲到早退曠職發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}

			//薪資計算明細資料的 -->  應發金額,  應扣金額,  實發金額
			//應發金額
			ehf030600m1.setEHF030600T1_071(
			ehf030600m1.getEHF030600T1_04() +  //本薪金額
			ehf030600m1.getEHF030600T1_04_O() +  //薪資項目總金額
			ehf030600m1.getEHF030600T1_05() +  //其他費用補款金額
			ehf030600m1.getEHF030600T1_09() +  //津貼費用
			ehf030600m1.getEHF030600T1_10() +  //加班費
			ehf030600m1.getEHF030600T1_11() +  //差旅費
			ehf030600m1.getEHF030600T1_12() +//不休假加班費
			ehf030600m1.getEHF030600T1_20()//全勤獎金 
			
			);
			
			//薪資所得代扣
			try{
				//計算當月的薪資所得代扣額
				ehf030600m1.setEHF030600T1_06(this.getSalaryAgencyDeduction(conn, ehf030600m1.getEHF030600T1_02(), ehf030200m0,
																			ehf030600m1.getEHF030600T1_071(), comp_id));
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算薪資所得代扣發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			//應扣金額
			ehf030600m1.setEHF030600T1_072(
			ehf030600m1.getEHF030600T1_041() +  //請假扣薪金額
			ehf030600m1.getEHF030600T1_05_D() +  //其他費用扣款金額
			ehf030600m1.getEHF030600T1_06() +  //薪資所得代扣金額
			ehf030600m1.getEHF030600T1_13() +  //健保費
			ehf030600m1.getEHF030600T1_15() +  //勞保費
			ehf030600m1.getEHF030600T1_17() +  //勞退費
			ehf030600m1.getEHF030600T1_19()  //福利金
			);
			
			//實發金額 = 應發金額 - 應扣金額
			//實發金額
			ehf030600m1.setEHF030600T1_07(ehf030600m1.getEHF030600T1_071() - ehf030600m1.getEHF030600T1_072());
			
			//薪資計算明細資料的相關設定值
			ehf030600m1.setEHF030600T1_SCP("02");  //薪資處理狀態
			ehf030600m1.setEHF030600T1_SCU(EHF030600T0_U);  //薪資計算UID
			hr_tools.close();
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("執行 cntRealSal() 時發生錯誤,員工工號:{" +ehf030600m2.getEHF030600T2_02()+ "} " + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
		return ehf030600m1;
	}


	

	/**
	 * 取得基本薪資金額
	 * @param conn
	 * @param ehf030200m0
	 * @return
	 */
	private Map getBasicSalary( Connection conn, Map breakMonthMap, EHF030200M0F ehf030200m0 ){
		
		Map basci_map = new HashMap();
		int basic_salary = 0;
		int clean_basic_salary = 0;
		String basic_desc = "";
		
		
		try{
			String count_start_date = "";  //當月薪資計算起始日
			String count_end_date = "";  //當月薪資計算結束日
			int count_days = 0;  //當月計薪天數
			float holidays = 0;  //當月公休假日天數
			int day_pay = 0;  //員工日薪
			
			
			//EMS產品新增參數 20130909 by Hedwig
			float schedule_days = 0;	//排班表出勤天數
			
			//判斷是否薪資有破月情況產生
//			Map breakMonthMap = this.chkBreakMonth(empInfMap);
			if((Boolean)breakMonthMap.get("BREAK_MONTH_FLAG")){
				//破月
				//破月情況
				//取得當月薪資計算起始日
				if("".equals((String)breakMonthMap.get("COUNT_START_DATE"))){
					count_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				}else{
					count_start_date = (String)breakMonthMap.get("COUNT_START_DATE");
				}
				
				//取得當月薪資計算結束日
				if("".equals((String)breakMonthMap.get("COUNT_END_DATE"))){
					count_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				}else{
					count_end_date = (String)breakMonthMap.get("COUNT_END_DATE");
				}
			}else{
				//未破月
				//取得當月薪資計算起始日,結束日
				count_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				count_end_date   = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
			}
			
			//不論月薪或是日薪的計算, 在有破月的情況下都先以'日薪'計算當月基本薪資
			//但是月薪必須含公休假日, 而日薪不含
			//****破月天數需要處理, 必須依據破月薪資起始日、結束日, 計算實際破月天數, edit by joe 2012/07/17****
			
			/*
			if("01".equals(ehf030200m0.getEHF030200T0_07())){
				//月薪
				count_days = this.getCountSalaryDays( conn, ehf030200m0.getEHF030200T0_01(), true,  this.sta_comp_id );
			}else if("02".equals(ehf030200m0.getEHF030200T0_07())){
				//日薪
				count_days = this.getCountSalaryDays( conn, ehf030200m0.getEHF030200T0_01(), false,  this.sta_comp_id );
			}
			*/
			//取得員工在職天數
			breakMonthMap.put("JOB_DAY", this.getSalaryDay(count_start_date, count_end_date));
			
			//取得員工薪資年月的計薪天數
			count_days = this.getCountSalaryDays( conn, ehf030200m0.getEHF030200T0_01(), ehf030200m0.getEHF030200T0_07(), count_start_date, count_end_date, this.sta_comp_id );

			//日薪 --> 計薪天數計算方式，ACTUAL_JOB_DAY:在職天數, ACTUAL_JOB_DAY_WITH_OUT_ROUTINE_HOLIDAY:在職天數(不含例假日),ACTUAL_WORK_DAY
			String salary_by_day_salary_day_get_mode = tools.getSysParam(conn,  this.sta_comp_id, "SALARY_BY_DAY_SALARY_DAY_GET_MODE");
			//薪資計算的日薪取得方式，HALF_UP:四捨五入, FLOOR:無條件捨去, CEIL:無條件進位
			String SALARY_DAY_PAY_COUNT_MODE = tools.getSysParam(conn,  this.sta_comp_id, "SALARY_DAY_PAY_COUNT_MODE");
			
			if("03".equals(ehf030200m0.getEHF030200T0_07())){
				//取得員工的時薪
				day_pay = this.getEmpHourPay(conn, ehf030200m0.getEHF030200T0_01(), this.sta_costyymm, this.sta_salyymm, this.sta_comp_id);
			}else{
				//取得員工的日薪, 不論月薪或日薪
				day_pay = this.getEmpDayPay(conn, ehf030200m0.getEHF030200T0_01(), this.sta_costyymm, this.sta_salyymm, this.sta_comp_id);
			}
		
			//Map<String, Float> getAll_day = new HashMap<String, Float>();
	
			//取得，出勤天數，公休天數，不扣日數，工作日數
			//this.getemployee_idAll_day(conn,count_start_date,count_end_date,getAll_day,ehf030200m0.getEHF030200T0_01());
			
			
			
			//float work_day		= (float) getAll_day.get("work_day");//出勤天數
			//float holiday		= (float) getAll_day.get("holiday");//假日次數
			//float NO_deduct		= (float) getAll_day.get("NO_deduct");//不扣日數

			
//			//處理基本薪資的計算
			if((Boolean)breakMonthMap.get("BREAK_MONTH_FLAG")){
				//破月
				//不論月薪或是日薪的計算, 在有破月的情況下都先以'日薪'計算當月基本薪資
				if("01".equals(ehf030200m0.getEHF030200T0_07())){
					//月薪
					schedule_days = count_days;
					basic_salary = (day_pay * count_days);
					basic_desc = "本薪以月薪計算且產生破月計算, 工作天:"+count_days+"天, ";
				}else if("02".equals(ehf030200m0.getEHF030200T0_07())){
					//日薪
					if("SCHEDUL_DAY".equals(salary_by_day_salary_day_get_mode)){
						schedule_days = count_days;
					}
					/*if("SCHEDUL_DAY".equals(salary_by_day_salary_day_get_mode)){
						//count_days取得為時數  須將時數轉換為天數
						
						float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "WORK_HOURS"));
						String   day =  String.valueOf((int)(count_days / Day_work_hours));//取得天
						String hours =  String.valueOf((int)(count_days % Day_work_hours));//取得小時
						schedule_days=count_days / Day_work_hours;
						
						basic_salary = tools.fixNumByMode(day_pay * schedule_days, SALARY_DAY_PAY_COUNT_MODE);
						basic_desc = "本薪以日薪計算且產生破月計算, 工作天:"+day+"天 +"+hours+"小時 ";
					}else
					{*/
						basic_salary = (day_pay * count_days);
						basic_desc = "本薪以日薪計算且產生破月計算, 工作天:"+count_days+"天, ";
						
					//}
				}else if("03".equals(ehf030200m0.getEHF030200T0_07())){
					//時薪
					schedule_days = count_days;
					basic_salary = (day_pay * count_days);
					basic_desc = "本薪以時薪計算且產生破月計算, 時數:"+count_days+"小時, ";
				}
			}else{
				//未破月
				if("01".equals(ehf030200m0.getEHF030200T0_07())){
					//月薪
					schedule_days = count_days;
					basic_salary = ehf030200m0.getEHF030200T0_04();
					basic_desc = "本薪以月薪計算, 當月天數:"+count_days+"天, ";
				}else if("02".equals(ehf030200m0.getEHF030200T0_07())){
					//日薪
					if("SCHEDUL_DAY".equals(salary_by_day_salary_day_get_mode)){
						schedule_days = count_days;
					}
					/*if("SCHEDUL_DAY".equals(salary_by_day_salary_day_get_mode)){
						float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "WORK_HOURS"));
						String   day = String.format("%02d", (int)(count_days / Day_work_hours));//取得天
						String hours = String.format("%02d", (int)(count_days % Day_work_hours));//取得小時
						schedule_days=count_days / Day_work_hours;
						
						basic_salary = tools.fixNumByMode(day_pay * schedule_days, SALARY_DAY_PAY_COUNT_MODE);
						basic_desc = "本薪以日薪計算且產生未破月計算, 工作天:"+day+"天 +"+hours+"小時 ";
					}else
					{*/
						basic_salary = (day_pay * count_days);
						basic_desc = "本薪以日薪計算且產生未破月計算, 工作天:"+count_days+"天, ";
						
					//}
				}else if("03".equals(ehf030200m0.getEHF030200T0_07())){
					//時薪
					schedule_days = count_days;
					basic_salary = (day_pay * count_days);
					basic_desc = "本薪以時薪計算且產生未破月計算, 時數:"+count_days+"小時, ";
				}
			}
			
			

			//設定乾淨的基本薪資
			clean_basic_salary = basic_salary;//+overTimt_money;//需額外加上休假時的費用(非加班費)
			
			//basic_desc="本次發放日薪為："+day_pay;
			//basic_salary= day_pay;
			
			
			
			//判斷以日薪計薪時, 公休假日是否額外給一天基本薪資
			//日薪才需要做判斷
			String giveOneDayPayInHa = tools.getSysParam(conn, ehf030200m0.getEHF030200T0_13(), "SALARY_BY_DAY_GIVE_DAY_PAY_IN_HOLIDAY");
//			if("02".equals(ehf030200m0.getEHF030200T0_07())){
//				//日薪
////				BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
//				if("YES".equals(giveOneDayPayInHa)){
//					//日薪計算時, 公休假日額外給一天基本薪資
//					//取得公休假日天數
//					//****公休天的計算方式要考慮 --> 例行假日、國定假日 edit by joe 2012/07/18****
//					holidays = this.getHaDaysInMonth(conn, this.sta_salyymm, ehf030200m0.getEHF030200T0_01(), this.sta_comp_id);
//					//basic_salary += (day_pay * holidays);
//					basic_desc += "公休天:"+holidays+"天, ";
//				}else{
//					//不給基本薪資
//				}
//			}
			
			
			//========================計算排班表排定休假時的金額(根據排班表休假時，是休何種休假類別，再依計薪方式計算)========================================
//			//日薪人員才需要計算該筆費用
//			int overTimt_money=0;
//		
//			//取得薪資年用
//			String day=this.sta_salyymm;
//			String  Salary_day[]= day.split("/");
//			
//			//所有加班紀錄(確實有加班紀錄與加班考勤)
//			Map OverTime=this.getovertime(conn, ehf030200m0.getEHF030200T0_01());
//			
//			
//			String sql=
//				" SELECT *" +
//				" FROM " +
//				" EHF010105T0" +//查詢排班表
//				" LEFT JOIN" +
//				" EHF010105T1 ON EHF010105T0_01 = EHF010105T1_01" +
//				" AND EHF010105T0_06 = EHF010105T1_06 " +
//				" WHERE 1 = 1" +
//				" AND EHF010105T0_03='"+Salary_day[0]+"'" +
//				" AND EHF010105T0_04='"+Salary_day[1]+"'" +
//				" AND EHF010105T0_02='"+ehf030200m0.getEHF030200T0_01()+"'" +
//				" AND EHF010105T0_06='"+this.sta_comp_id+"'" +
//				" AND EHF010105T1_03=1";
//			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			ResultSet rs = stmt.executeQuery(sql);
//
//			while(rs.next()){
//				//列出排班表為"休假"的日期
//				if(OverTime.get((String)rs.getString("EHF010105T1_02"))==null){
//					//沒有加班紀錄，才需要計算排班表排定休假時的金額
//					if("01".equals(ehf030200m0.getEHF030200T0_07())){
//						//月薪人員
//						if("1".equals((String)rs.getString("EHF010105T1_07"))){
//							//全薪
//							//因月薪人員，本來放假就會給薪，所以不做扣錢動作
//						}else if("2".equals((String)rs.getString("EHF010105T1_07"))){
//							//半薪
//							//扣半天錢
//							overTimt_money-=tools.fixNumByMode(day_pay / 2, SALARY_DAY_PAY_COUNT_MODE);
//						}else if("3".equals((String)rs.getString("EHF010105T1_07"))){
//							//無薪
//							//扣一天錢
//							overTimt_money-=day_pay;
//						}
//					}else if("02".equals(ehf030200m0.getEHF030200T0_07())){
//						//日薪人員
//						if("1".equals((String)rs.getString("EHF010105T1_07"))){
//							//全薪
//							//加一天薪水
//							overTimt_money+=day_pay;
//						}else if("2".equals((String)rs.getString("EHF010105T1_07"))){
//							//半薪
//							//加半天薪水
//							overTimt_money+=tools.fixNumByMode(day_pay / 2, SALARY_DAY_PAY_COUNT_MODE);
//						}else if("3".equals((String)rs.getString("EHF010105T1_07"))){
//							//無薪
//							//日薪人員，有工作才有錢領，所以無薪不不做計算
//						}
//					}						
//				}else{
//					//已有加班紀錄的，則不需要紀錄該筆費用
//				}
//			}
//			rs.close();
//			stmt.close();
		//========================計算排班表排定休假時的金額(根據排班表休假時，是休何種休假類別，再依計薪方式計算)========================================
		
			
//		basic_salary += overTimt_money;
//			
//			
//
//			
//			//處理不休假加班的計算資料型態為'MERGE'時,需將不休假加班費的當日本薪併入本薪金額中
//			String ha_ov_type = tools.getSysParam(conn, this.sta_comp_id, "HALIDAY_OVERTIME_TYPE");
//			
//			//判斷不休假加班的計算資料型態
//			if("MERGE".equals(ha_ov_type)){
//				//取得工作時數
//				float work_hours = Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "WORK_HOURS"));
//				//不休假加班計算資料合併至津貼,加班費,本薪金額
//				Map ha_merge_map = this.getHaOvertimeDataForMerge(conn, ehf030200m0.getEHF030200T0_01(), 
//																  this.sta_salyymm, this.sta_costyymm, this.sta_comp_id);
//				//加上不休假加班的本薪
//				basic_salary +=  (Integer)ha_merge_map.get("MONEY");
//				basic_desc += "假日加班:"+Math.round((((Float)ha_merge_map.get("HOURS"))/work_hours)*100)/100.0+"天, ";
//			}
			
			//針對薪資發放類別進行特殊處理
			float salary_rate = (Float.parseFloat(ehf030600m0.getEHF030600T0_08()))/(100);
			String salary_rate_desc = ehf030600m0.getEHF030600T0_08();
			if("01".equals(ehf030600m0.getEHF030600T0_04())){
				//薪資計算
				basic_salary = Math.round(basic_salary * salary_rate);
				basic_desc += " (發放本薪的"+salary_rate_desc+"% )";
			}else if("02".equals(ehf030600m0.getEHF030600T0_04()) || "03".equals(ehf030600m0.getEHF030600T0_04())){
				//獎金,臨時計算
				//判斷日薪或月薪
				if("01".equals(ehf030200m0.getEHF030200T0_07())){
					//月薪
					basic_salary = Math.round((ehf030200m0.getEHF030200T0_04()) * (salary_rate));
					basic_desc = " 發放月薪("+ehf030200m0.getEHF030200T0_04()+"元)的"+salary_rate_desc+"% ";
				}else if("02".equals(ehf030200m0.getEHF030200T0_07())){
					//日薪, 日薪的本薪以30天計算
					if("YES".equals(giveOneDayPayInHa)){
						//公休假日給薪
						basic_salary = Math.round((day_pay * 30) * (salary_rate));
						basic_desc = "本薪以日薪("+day_pay+"元)(30天)計算, 發放"+salary_rate_desc+"% ";
					}else{
						//公休假日不給薪
						int work_days = Integer.parseInt((tools.getSysParam(conn, this.sta_comp_id, "WORK_HOURS")));
						basic_salary = Math.round((day_pay * work_days) * (salary_rate));
						basic_desc = "本薪以日薪("+day_pay+"元)("+work_days+"天)計算, 發放"+salary_rate_desc+"% ";
					}
				}
			}else{
				//未設定當作薪資計算
				basic_salary = Math.round(basic_salary * salary_rate);
				basic_desc += " (發放本薪的"+salary_rate_desc+"% )";
			}			
			//設定 return Map
			basci_map.put("CLEAN_BASIC_MONEY", clean_basic_salary);
			basci_map.put("BASIC_MONEY", basic_salary);
			basci_map.put("BASIC_DESC", basic_desc);
			basci_map.put("SCHEDUL_DAYS", schedule_days);//排班表出勤天數
			basci_map.put("ONWORK_DAYS", breakMonthMap.get("JOB_DAY"));//員工在職天數

			//basci_map.put("work_day", 	String.valueOf((float) getAll_day.get("work_day")));//員工在職天數
			//basci_map.put("holiday", 	String.valueOf((float) getAll_day.get("holiday")));//員工在職天數
			//basci_map.put("NO_deduct", 	String.valueOf((float) getAll_day.get("NO_deduct")));//員工在職天數
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("取得員工基本薪資 getBasicSalary() 時發生錯誤,員工工號:{" +ehf030200m0.getEHF030200T0_01()+ "} " + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return basci_map;
	}
	
	/**
	 * 取得，出勤天數，公休天數，不扣日數，工作日數
	 * @param conn 
	 * @param countStartDate 		起始日期
	 * @param countEndDate			結束日期
	 * @param getAllDay     		回傳的Map
	 * @param ehf030200t0_01	 	員工代號(內碼)
	 */
	private void getemployee_idAll_day(Connection conn, String countStartDate,String countEndDate, Map getAllDay, String ehf030200t0_01) {
		// TODO Auto-generated method stub
		float work_day=0;//工作天
		float work_day_change=0;//工作天(有預排換班的)
		float holiday_1=0;//行事曆設定的大禮拜天數
		float holiday_2=0;//行事曆設定的小禮拜天數
		
		float NO_deduct=0;//不扣日數
		
		
		try {
		//1.先取得有預排換班的資料(根據員工代碼)

			String sql="select EHF010105T1.EHF010105T1_02" +
				"			from EHF010105T0 " +
				"			LEFT JOIN EHF010105T1 on EHF010105T0_01=EHF010105T1_01" +
				"			where 1=1" +
				"			and EHF010105T1_02 BETWEEN '"+countStartDate+"' and '"+countEndDate+"' "+//找當月的
				"           and EHF010105T1_03='0'" +//上班天
				"		    and EHF010105T0_02='"+ehf030200t0_01+"'" +
				"			and EHF010105T1_01 is not null";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				
				getAllDay.put(rs.getString("EHF010105T1_02"), 0);
				work_day_change++;
			}
			rs.close();
			stmt.close();
			
			//2.找當月行事曆大禮拜的天數
			sql=" SELECT DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS EHF000500T0_05" +
				" FROM EHF000500T0" +
				" WHERE 1=1 " +
				" AND DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') BETWEEN '"+countStartDate+"' and '"+countEndDate+"' " +//找當月的
				" AND EHF000500T0_12=1 " +//找大禮拜(EHF000500T0_12=1)
				" ORDER by EHF000500T0_05";

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
			
				if((getAllDay.get(rs.getString("EHF000500T0_05")))==null){
					getAllDay.put(rs.getString("EHF000500T0_05"), 1);
					holiday_1++;
				}
			}
			rs.close();
			stmt.close();
			//3.找當月行事曆小禮拜的天數
			sql=" SELECT DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS EHF000500T0_05" +
			" FROM EHF000500T0" +
			" WHERE 1=1 " +
			" AND DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') BETWEEN '"+countStartDate+"' and '"+countEndDate+"' " +//找當月的
			" AND EHF000500T0_12=2 " +//找小禮拜(EHF000500T0_12=1)
			" ORDER by EHF000500T0_05";

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
		
				if(getAllDay.get(rs.getString("EHF000500T0_05"))==null){
					getAllDay.put(rs.getString("EHF000500T0_05"), 2);
					holiday_2++;
				}
			}
			rs.close();
			stmt.close();
			//4.取得當月在職的工作天數(包含了工作天、假日天)
			String [] StartDate =countStartDate.split("/");
			String [] EndDate =countEndDate.split("/");
			
			int month_day = Integer.valueOf(EndDate[2])-Integer.valueOf(StartDate[2])+1;
			
			
			//工作天數=當月總在職天數(根據到職、離職)-換班天數-大禮拜天數-小禮拜天數
			work_day=month_day-work_day_change-holiday_1-holiday_2;
			
			
			work_day=work_day+work_day_change;//真正有工作的天數
			
			//5.取得當月有請假的總次數
			sql=    " select EHF020411T0_04,EHF020411T0_05,EHF020411T0_06" +
					" from EHF020411T0" +
					" where 1=1 " +
					" and EHF020411T0_03='"+ehf030200t0_01+"'" +
					" and EHF020411T0_M1 ='"+this.sta_salyymm+"'";

			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			while(rs.next()){
				
				work_day= (work_day-Float.valueOf(rs.getString("EHF020411T0_05")));
				
				if("01".equals(rs.getString("EHF020411T0_06"))){
					//全薪
					//假日日數必須扣除
					holiday_1=(holiday_1-Float.valueOf(rs.getString("EHF020411T0_05")));
					if(holiday_1<0){
						holiday_1=0;
					}
					//全薪不做扣除，必須在不扣日數加回來
					NO_deduct=NO_deduct+1;
				}else if("02".equals(rs.getString("EHF020411T0_06"))){
					//半薪
					//戴養菌園沒有半薪，因此暫時不撰寫
				}else if("03".equals(rs.getString("EHF020411T0_06"))){
					//無薪
					//假日日數必須扣除
					holiday_1= (holiday_1-Float.valueOf(rs.getString("EHF020411T0_05")));
					if(holiday_1<0){
						holiday_1=0;
					}	
				}
			}
			rs.close();
			stmt.close();

			getAllDay.put("work_day", work_day);//出勤天數
			getAllDay.put("holiday", holiday_1);//假日次數
			getAllDay.put("NO_deduct", NO_deduct);//不扣日數
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	/**
	 * 判斷破月狀況
	 * @param empInfMap
	 * @return
	 */
	protected Map chkBreakMonth( Map empInfMap ){
		
		Map breakMonthMap = new HashMap();
		
		try{
			boolean chk_flag = false;
			String return_type = "";
			Calendar count_start_cal = null;
			String count_start_date = "";
			Calendar count_end_cal = null;
			String count_end_date = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			SimpleDateFormat sdf_d = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat sdf_day = new SimpleDateFormat("dd");
			
			//取得員工到職日
			Calendar emp_in_cal = Calendar.getInstance();
			Date emp_in = (Date)empInfMap.get("OFFICE_DATE");
			if( emp_in != null){
				emp_in_cal.setTime(emp_in);
			}else{
				emp_in_cal = null;
			}
			String emp_in_date = "";
			boolean emp_in_date_is_first_day = false;
			//String emp_in_date_day = "";
			if(emp_in_cal != null){
				emp_in_date = sdf.format(emp_in_cal.getTime());
				
				//取得到職當月 -->該月份的第一天
				//ems_tools.getFirstMonthDay(ems_tools.covertStringToCalendar(emp_in_date+"/01"));
				//ems_tools.getLastMonthDay(ems_tools.covertStringToCalendar(this.sta_salyymm+"/01"));
				//emp_in_date_day = sdf_day.format(emp_in_cal.getTime());
				if(sdf_day.format(emp_in_cal.getTime()).equals(sdf_day.format(tools.getFirstMonthDay(tools.covertStringToCalendar(emp_in_date+"/01"))))){
					emp_in_date_is_first_day = true;
				}
				
			}
			
			//取得員工復職日
			Calendar emp_re_in_cal = Calendar.getInstance();
			Date emp_re_in = (Date)empInfMap.get("REAPOINT_DATE");
			if( emp_re_in != null ){
				emp_re_in_cal.setTime(emp_re_in);
			}else{
				emp_re_in_cal = null;
			}
			String emp_re_in_date = "";
			boolean emp_re_in_date_is_first_day = false;
			//String emp_re_in_date_day = "";
			if(emp_re_in_cal != null){
				emp_re_in_date = sdf.format(emp_re_in_cal.getTime());
				
				//取得復職當月 -->該月份的第一天
				//emp_re_in_date_day = sdf_day.format(emp_re_in_cal.getTime());
				if(sdf_day.format(emp_re_in_cal.getTime()).equals(sdf_day.format(tools.getFirstMonthDay(tools.covertStringToCalendar(emp_re_in_date+"/01"))))){
					emp_re_in_date_is_first_day = true;
				}
			}
			
			//取得員工離職日
			Calendar emp_out_cal = Calendar.getInstance();
			Date emp_out = (Date)empInfMap.get("OUT_DATE");
			if( emp_out != null ){
				emp_out_cal.setTime(emp_out);
			}else{
				emp_out_cal = null;
			}
			String emp_out_date = "";
			boolean emp_out_date_is_last_day = false;
			String emp_out_date_day = "";
			if(emp_out_cal != null){
				emp_out_date = sdf.format(emp_out_cal.getTime());
				emp_out_date_day = sdf_day.format(emp_out_cal.getTime());
				
				//取得離職當月 -->該月份的最後一天
				//emp_re_in_date_day = sdf_day.format(emp_re_in_cal.getTime());
				if(sdf_day.format(emp_out_cal.getTime()).equals(sdf_day.format(tools.getLastMonthDay(tools.covertStringToCalendar(emp_out_date+"/01"))))){
					emp_out_date_is_last_day = true;
				}
			}
			
			
			//判斷破月情況
			//判斷離職狀況
			if(emp_out_date.equals(this.sta_salyymm) && !emp_out_date_is_last_day){
				//當月離職
				//判斷到職狀況
				if(emp_in_date.equals(this.sta_salyymm) && !emp_in_date_is_first_day){
					//當月到職
					//計算到職日至離職日的天數
					chk_flag = true;
					return_type = "04";  //破月, 當月到職,當月離職
					count_start_cal = emp_in_cal;
					//count_start_date = emp_in_date;
					count_end_cal = emp_out_cal;
					//count_end_date = emp_out_date;
				}else if(emp_re_in_date.equals(this.sta_salyymm) && !emp_re_in_date_is_first_day){
					//當月復職
					//計算復職日至離職日的天數
					chk_flag = true;
					return_type = "05";  //破月, 當月復職, 當月離職
					count_start_cal = emp_re_in_cal;
					//count_start_date = emp_re_in_date;
					count_end_cal = emp_out_cal;
					//count_end_date = emp_out_date;
				}else{
					//計算當月一日至離職日的天數
					chk_flag = true;
					return_type = "03";  //破月, 當月離職
					count_end_cal = emp_out_cal;
					//count_end_date = emp_out_date;
				}
					
			}else{
				//當月未離職
				//判斷到職狀況
				if(emp_in_date.equals(this.sta_salyymm) && !emp_in_date_is_first_day){
					//當月到職
					//計算到職日至當月最後一日的天數
					chk_flag = true;
					return_type = "01";
					count_start_cal = emp_in_cal;
					//count_start_date = emp_in_date;
				}else if(emp_re_in_date.equals(this.sta_salyymm) && !emp_re_in_date_is_first_day){
					//當月復職
					//計算復職日至當月最後一日的天數
					chk_flag = true;
					return_type = "02";
					count_start_cal = emp_re_in_cal;
					//count_start_date = emp_re_in_date;
				}else{
					//未破月
					chk_flag = false;
					return_type = "06";
				}
			}
			
			//轉換到職日期
			if(count_start_cal != null){
				count_start_date = tools.convertADDateToStrng(count_start_cal.getTime());
			}
			
			//轉換離職日期
			if(count_end_cal != null){
				count_end_date = tools.convertADDateToStrng(count_end_cal.getTime());
			}
			
			breakMonthMap.put("BREAK_MONTH_FLAG", chk_flag);
			breakMonthMap.put("BREAK_MONTH_TYPE", return_type);
			breakMonthMap.put("COUNT_START_CAL", count_start_cal);
			breakMonthMap.put("COUNT_END_CAL", count_end_cal);
			breakMonthMap.put("COUNT_START_DATE", count_start_date);
			breakMonthMap.put("COUNT_END_DATE", count_end_date);
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("判斷員工薪資是否破月 chkBreakMonth() 時發生錯誤,員工工號:{" +(String)empInfMap.get("EMPLOYEE_ID")+ "} " + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return breakMonthMap;
	}
	
	/**
	 * 取得實際計薪天數
	 * @param conn
	 * @param employee_id
	 * @param withHoliday_flag
	 * @return
	 */
	protected int getCountSalaryDays( Connection conn, String employee_id,String salary_count_mode, String count_start_date, String count_end_date, String comp_id ){
		
		int cur_days = 0;
		
		try{
			//****破月天數需要處理, 必須依據破月薪資起始日、結束日, 計算實際破月天數, edit by joe 2012/07/17****
			
			//取得系統參數中, 月薪、日薪計薪天數的計算方式
			String salary_by_month_salary_day_get_mode = tools.getSysParam(conn, comp_id, "SALARY_BY_MONTH_SALARY_DAY_GET_MODE");
			String salary_by_day_salary_day_get_mode = tools.getSysParam(conn, comp_id, "SALARY_BY_DAY_SALARY_DAY_GET_MODE");
			String salary_by_hour_salary_day_get_mode = tools.getSysParam(conn, comp_id, "SALARY_BY_HOUR_SALARY_DAY_GET_MODE");
			
			//計算當月實際計薪天數(含破月狀況)
			int salary_day_get_mode = 0;
			
			//判斷計算模式
			if("01".equals(salary_count_mode)){
				//月薪
				if("ACTUAL_JOB_DAY".equals(salary_by_month_salary_day_get_mode)){
					//模式一: 實際在職天數(含例行假日、國定假日)
					salary_day_get_mode = 1;
				}else if("ACTUAL_JOB_DAY_WITHOUT_ROUTINE_HOLIDAY".equals(salary_by_month_salary_day_get_mode)){
					//模式二: 實際在職工作天數(含國定假日)
					salary_day_get_mode = 2;
				}else if("ACTUAL_WORK_DAY".equals(salary_by_month_salary_day_get_mode)){
					//模式三: 實際工作天數(不含例行假日、國定假日)
					salary_day_get_mode = 3;
				}else if("FIXED_THIRTY_DAY".equals(salary_by_month_salary_day_get_mode)){
					//模式四: 固定30天減去實際未在職天數
					salary_day_get_mode = 4;
				}else if("FIXED__DAY".equals(salary_by_month_salary_day_get_mode)){
					//模式五: 月薪 --> 固定天數減去實際未在職天數
					salary_day_get_mode = 5;
				}
			}else if("02".equals(salary_count_mode)){
				//日薪
				if("ACTUAL_JOB_DAY".equals(salary_by_day_salary_day_get_mode)){
					//模式一: 實際在職天數(含例行假日、國定假日)
					salary_day_get_mode = 1;
				}else if("ACTUAL_JOB_DAY_WITHOUT_ROUTINE_HOLIDAY".equals(salary_by_day_salary_day_get_mode)){
					//模式二: 實際在職工作天數(含國定假日)
					salary_day_get_mode = 2;
				}else if("ACTUAL_WORK_DAY".equals(salary_by_day_salary_day_get_mode)){
					//模式三: 實際工作天數(不含例行假日、國定假日)
					salary_day_get_mode = 3;
				}else if("FIXED_THIRTY_DAY".equals(salary_by_day_salary_day_get_mode)){
					//模式四: 固定30天減去實際未在職天數
					salary_day_get_mode = 4;
				}else if("FIXED__DAY".equals(salary_by_day_salary_day_get_mode)){
					//模式六: 日薪 --> 固定天數減去實際未在職天數
					salary_day_get_mode = 6;
				}else if("SCHEDUL_DAY".equals(salary_by_day_salary_day_get_mode)){//EMS產品新增參數 20130909 by Hedwig
					//模式七: 日薪 --> 依照排班表
					salary_day_get_mode = 7;
				}
			}else if("03".equals(salary_count_mode)){
				//時薪
				if("ACTUAL_ATT_DAY".equals(salary_by_hour_salary_day_get_mode)){
					//模式三: 實際工作時數(實際有打上下班卡的工作時數)
					salary_day_get_mode = 8;
				}
			}
			
			//處理計薪天數
			cur_days = this.handleCountSalaryDays(conn,employee_id, count_start_date, count_end_date, salary_day_get_mode, comp_id);
			
			/*
			String sql = "" +
			" SELECT COUNT(*) AS CUR_DAYS FROM ( " +
			" SELECT EHF020401T0_05 AS ATT_DATE " +
			" FROM EHF020401T0 " +  //考勤資料
			" LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020401T0_08 = '1' " +  //上班
			" AND DATE_FORMAT(EHF020401T0_05, '%Y/%m') = '"+this.sta_salyymm+"' " +  //薪資年月
			" AND EHF020401T0_11 = '"+this.sta_comp_id+"' " +  //公司代碼
			" UNION " +
			" SELECT EHF020406T0_04 AS ATT_DATE " +
			" FROM EHF020406T0 " +  //未打卡資料
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020406T0_05 = '1' " +  //上班
			" AND DATE_FORMAT(EHF020406T0_04, '%Y/%m') = '"+this.sta_salyymm+"' " +  //薪資年月
			" AND EHF020406T0_07 = '"+this.sta_comp_id+"' " +
			" ) tableA " +
			" WHERE 1=1 ";
			
			if(!withHoliday_flag){
				//不含公休假日
				//取得系統參數中, 公休日的計算方式
				String salary_holiday_get_mode = ems_tools.getSysParam(conn, comp_id, "SALARY_HOLIDAY_GET_MODE");
				
				if("BY_EMS_WORK_SCHEDULE".equals(salary_holiday_get_mode)){
					
				}else{
					sql += "" +
					" AND ATT_DATE NOT IN ( " +
					" SELECT " + 
					" DATE_FORMAT(EHF010200T0_05, '%Y/%m/%d') AS EHF010200T0_05 " +
					" FROM EHF010200T0 " +
					" WHERE 1=1 " +
					" AND EHF010200T0_03 = '"+(Integer.parseInt((this.sta_salyymm.split("/"))[0])-1911)+"' " +  //年度
					" AND DATE_FORMAT(EHF010200T0_05, '%Y/%m') = '"+this.sta_salyymm+"' " +  //薪資年月
					" AND EHF010200T0_11 = '"+this.sta_comp_id+"' " +  //公司代碼
					" UNION " +
					" SELECT " +
					" DATE_FORMAT(EHF010200T0_06, '%Y/%m/%d') AS EHF010200T0_06 " +
					" FROM EHF010200T0 " +
					" WHERE 1=1 " +
					" AND EHF010200T0_03 = '"+(Integer.parseInt((this.sta_salyymm.split("/"))[0])-1911)+"' " +  //年度
					" AND DATE_FORMAT(EHF010200T0_06, '%Y/%m') = '"+this.sta_salyymm+"' " +  //薪資年月
					" AND EHF010200T0_11 = '"+this.sta_comp_id+"' " +  //公司代碼
					" " +
					" ) ";
				}
			}
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				cur_days = rs.getInt("CUR_DAYS");
			}
			
			rs.close();
			stmt.close();
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return cur_days;
	}
	
	/**
	 * 處理當月計薪天數計算
	 * @param conn
	 * @param count_start_date
	 * @param count_end_date
	 * @param salary_day_get_mode
	 * @param comp_id
	 * @return
	 */
	protected int handleCountSalaryDays(Connection conn,String employee_id, String count_start_date, String count_end_date, int salary_day_get_mode, String comp_id){
		
		int salary_day = 0;
		
		//String salary_day="";
		
		try{
			//處理各種模式的計算
			switch(salary_day_get_mode){
			
			case 1:
				//模式一: 實際在職天數(含例行假日、國定假日)
				//取得計薪區間的實際在職天數
				salary_day = this.getSalaryDay(count_start_date, count_end_date);
				
				//salary_day = String.valueOf(this.getSalaryDay(count_start_date, count_end_date));
				break;
				
			case 2:
				//模式二: 實際在職工作天數(含國定假日)
				//取得計薪區間的實際在職天數
				//取得計薪區間的例行假日天數
				//只是取在職天數，並不需要考慮力行假日是否包含在請假日期間的問題。 20140623 by Hedwig
				salary_day = this.getSalaryDay(count_start_date, count_end_date) 
							 - this.getSalaryHoliday(conn, "02", count_start_date, count_end_date, comp_id);  //例行假日天數
				
				
				//salary_day = String.valueOf(this.getSalaryDay(count_start_date, count_end_date)- this.getSalaryHoliday(conn, "02", count_start_date, count_end_date, comp_id));
				break;
			
			case 3:
				//模式三: 實際工作天數(不含例行假日、國定假日)
				//取得計薪區間的實際在職天數
				//取得計薪區間的例行假日天數
				//取得計薪區間的國定假日天數
				salary_day = this.getSalaryDay(count_start_date, count_end_date) 
				 - this.getSalaryHoliday(conn, "02", count_start_date, count_end_date, comp_id)  //例行假日天數
				 - this.getSalaryHoliday(conn, "01", count_start_date, count_end_date, comp_id);  //國定假日天數
				
				
				//salary_day = String.valueOf(this.getSalaryDay(count_start_date, count_end_date) 
				 //- this.getSalaryHoliday(conn, "02", count_start_date, count_end_date, comp_id)  
				 //- this.getSalaryHoliday(conn, "01", count_start_date, count_end_date, comp_id));
				
				break;
				
			case 4:
				//模式四: 固定30天減去實際未在職天數
				//取得當月未在職天數
				salary_day = 30 - this.getNotSalaryDay(count_start_date, count_end_date);  //未在職天數
				
				
				//salary_day = String.valueOf(30 - this.getNotSalaryDay(count_start_date, count_end_date));
				
				break;
				
			case 5:
				//模式五: 月薪 --> 固定天數減去實際未在職天數
				//取得月薪固定天數
				//取得當月未在職天數
				
				//取得系統參數中, 月薪 --> 固定天數
				int salary_by_month_salary_day_fixed_day = Integer.parseInt(tools.getSysParam(conn, comp_id, "SALARY_BY_MONTH_SALARY_DAY_FIXED_DAY"));
				salary_day = salary_by_month_salary_day_fixed_day  - this.getNotSalaryDay(count_start_date, count_end_date);  //未在職天數 
				
				
				
				//salary_day = String.valueOf(salary_by_month_salary_day_fixed_day  - this.getNotSalaryDay(count_start_date, count_end_date));
				
				break;
				
			case 6:
				//模式六: 日薪 --> 固定天數減去實際未在職天數
				//取得固日薪定天數
				//取得當月未在職天數
				
				//取得系統參數中, 日薪 --> 固定天數
				int salary_by_day_salary_day_fixed_day = Integer.parseInt(tools.getSysParam(conn, comp_id, "SALARY_BY_DAY_SALARY_DAY_FIXED_DAY"));
				salary_day = salary_by_day_salary_day_fixed_day 
								- this.getNotSalaryDay(count_start_date, count_end_date);  //未在職天數 
				
				
				//salary_day = String.valueOf(salary_by_day_salary_day_fixed_day - this.getNotSalaryDay(count_start_date, count_end_date));

				break;
			
			case 7:
				//預留排班表整合功能
				//由排班表查詢員工 --> 1.在職天數 2.工作天數 3.日曆天數 4.公休日天數
				
				//float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
				
				//得到小時
				//float Total_Hours = 0;
				//Total_Hours+=this.getSchedulDays(conn, employee_id, count_start_date, count_end_date,0, comp_id)
				//	-this.getnoWorkDays(conn, employee_id, count_start_date, count_end_date, comp_id);
				
				//salary_day = (int)Total_Hours;
				
				salary_day = this.getSalaryDay(count_start_date, count_end_date)
					+ this.getSchedulWorkDays(conn, employee_id, count_start_date, count_end_date, 0, comp_id)//排班表的上班 (有換班才有排班表)
					- this.getCalendarByOddOrDouble(conn, "02", count_start_date, count_end_date, comp_id)	//大禮拜(休假)
					- this.getSchedulWorkDays(conn, employee_id, count_start_date, count_end_date, 1, comp_id);//排班表的假日
				
				break;
				
			case 8:
				
				List ATT_Day = this.getActualATTDay(conn, employee_id, count_start_date, count_end_date, comp_id);
				
				Iterator it = ATT_Day.iterator();
				Map tempMap = null;
				Map break_datetime_map = null;
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					
					//處理加班休息時間資訊
					break_datetime_map = this.composeBreakDate(conn, (String)tempMap.get("EHF020410T0_03"), (String)tempMap.get("EHF020410T0_CLASS"), comp_id);
					
					salary_day += this.getSalaryHour(conn, 
													this.getActualHour(conn, employee_id, (String)tempMap.get("EHF020410T0_03"), comp_id, "1"), 
													this.getActualHour(conn, employee_id, (String)tempMap.get("EHF020410T0_03"), comp_id, "2"),
													Float.parseFloat((String)break_datetime_map.get("BREAL_TIME")),
													comp_id);
				}
				
				break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return salary_day;
	}
	
	/**
	 * 取得員工曠職並且尚未修正的時數
	 * @param conn
	 * @param employee_id
	 * @param count_start_date
	 * @param count_end_date
	 * @param comp_id
	 * @return
	 */
	private float getnoWorkDays(Connection conn, String employee_id,String count_start_date, String count_end_date, String comp_id) {
		// TODO Auto-generated method stub
		
		int time=0;
		float time_count=0;
		String Day="";
		try{
			
			String SQL= "SELECT * FROM EHF020410T0" +
					" WHERE   1 = 1" +
					" AND EHF020410T0_01 = '"+employee_id+"'" +
					" AND EHF020410T0_03 BETWEEN '"+count_start_date+"'" + "AND '"+count_end_date+"'"  +
					" AND EHF020410T0_05 = '1'" +
					" AND EHF020410T0_06 = '03'" +
					" AND EHF020410T0_FIX = '0'"+ 
					" AND EHF020410T0_08 = '"+comp_id+"'" +
				    " ORDER BY EHF020410T0_03";
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(SQL);
			while(rs.next()){
				if(!Day.equals(rs.getString("EHF020410T0_03"))){
					Day=rs.getString("EHF020410T0_03");
					time+=rs.getInt("EHF020410T0_07");
				}
				
			}
			if(time!=0)
			time_count=Float.parseFloat(time/60/60+"");
			rs.close();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		//回傳小時
		return time_count;
	}

	/**
	 * 計算員工這個月請假的總時數。
	 * @param compId 
	 * @param i 
	 * @param countEndDate 
	 * @param countStartDate 
	 * @param employeeId 
	 * @param conn 
	 * @return
	 */
	private float getVacationDays(Connection conn, String employeeId,  int i, String compId) {
		// TODO Auto-generated method stub
		Map data= new HashMap();
		data.put("EHF020411T0_M1", this.sta_salyymm);
		data.put("EHF020411T0_03", employeeId);
		data.put("EHF020411T0_08", compId);
		
		float time=Float.parseFloat(this.chackEHF020411T0(conn, data, compId, 2));
		
		//回傳小時
		return time;
	}

	

	/**
	 * 取得計薪區間的實際在職天數
	 * @param count_start_date
	 * @param count_end_date
	 * @return
	 */
	protected int getSalaryDay(String count_start_date, String count_end_date){
		
		int salary_day = 0;
		
		try{
			salary_day = tools.caculateDiffCalDays(count_start_date, count_end_date);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return salary_day;
	}
	
	/**
	 * 取得實際有打上下班卡的工作日期
	 * @param conn
	 * @param employee_id
	 * @param count_start_date
	 * @param count_end_date
	 * @param comp_id
	 * @return
	 */
	private List getActualATTDay(Connection conn, String employee_id,
			String count_start_date, String count_end_date, String comp_id) throws Exception {
		// TODO Auto-generated method stub
		
		String sql = "";
		//int salary_day = 0;
		List dataList = new ArrayList(); 
		BaseFunction base_tools= new BaseFunction(comp_id);
		
		try{
			/*sql = 
				" SELECT COUNT(*) AS DAYS " +
				" FROM " +
				" (" +
				" SELECT EHF020410T0_01, EHF020410T0_03 FROM " +
				" EHF020410T0 " +
				" WHERE 1=1 " +
				" AND EHF020410T0_01 = '"+employee_id+"'" +
				" AND EHF020410T0_03 BETWEEN '"+count_start_date+"'" + "AND '"+count_end_date+"'"  +
				" AND EHF020410T0_05 = '0'" +
				" AND EHF020410T0_08 = '"+comp_id+"'" +
				" GROUP BY EHF020410T0_03" +
				" HAVING count(*)>1 " +
				" ) A" +
				" WHERE EHF020410T0_01 = '"+employee_id+"'" +
				" AND EHF020410T0_03 BETWEEN '"+count_start_date+"'" + "AND '"+count_end_date+"'"  ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				salary_day = rs.getInt("DAYS");
			}*/
			
			sql = 
				" SELECT EHF020410T0_01, EHF020410T0_03, EHF020410T0_CLASS FROM " +
				" EHF020410T0 " +
				" WHERE 1=1 " +
				" AND EHF020410T0_01 = '"+employee_id+"'" +
				" AND EHF020410T0_03 BETWEEN '"+count_start_date+"'" + "AND '"+count_end_date+"'"  +
				" AND EHF020410T0_05 = '0'" +
				" AND EHF020410T0_08 = '"+comp_id+"'" +
				" GROUP BY EHF020410T0_03" +
				" HAVING count(*)>1 " ;
			
			dataList = base_tools.queryList(sql);
			
			base_tools.close();
			/*
			rs.close();
			stmt.close();
			*/
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("取得實際有打上下班卡的工作日期，發生錯誤!!" + e.toString());
		}
		
		return dataList;
	}
	
	/**
	 * 取得指定考勤日期、指定打卡狀態的打卡日期時間
	 * @param conn
	 * @param employee_id
	 * @param ATT_Day
	 * @param comp_id
	 * @param Card_Type
	 * @return Calendar
	 */
	private Calendar getActualHour(Connection conn, String employee_id, String ATT_Day,
			String comp_id, String Door_Type) throws Exception {
		// TODO Auto-generated method stub
		
		Calendar cal = null;
		String sql = "";
		
		try{							
			sql = 
				" SELECT EHF020401T0_01, EHF020401T0_02_EMP, EHF020401T0_05, EHF020401T0_06, EHF020401T0_07 " +
				" FROM EHF020401T0 " +
				" WHERE 1=1 " +
				" AND EHF020401T0_02_EMP = '"+employee_id+"'" +
				" AND EHF020401T0_05 = '"+ATT_Day+"'"  +
				" AND EHF020401T0_08 = '"+Door_Type+"'" +
				" AND EHF020401T0_09 = '0'" +
				" AND EHF020401T0_11 = '"+comp_id+"'" ;
				
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			if(rs.next()){
				cal = tools.covertStringToCalendar(rs.getString("EHF020401T0_05")+" "+rs.getString("EHF020401T0_06"), "yyyy/MM/dd HH:mm:ss");
			}
			
			rs.close();
			stmt.close();
									
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("取得指定考勤日期、指定打卡狀態的打卡日期時間，發生錯誤!!" + e.toString());
		}
		
		return cal;
	}
	
	/**
	 * 時薪休息時間資訊
	 * @param conn
	 * @param employee_id
	 * @param class_no
	 * @param comp_id
	 * @return
	 * @throws Exception
	 */
	private Map composeBreakDate(Connection conn, String ATT_Day,
			String class_no, String comp_id) throws Exception {
		// TODO Auto-generated method stub
		
		String sql = "";
		int break_in = 0;
		int break_out = 0;
		int break_in_sec = 0;
		int break_out_sec = 0;
		int break_sec = 0;
		float break_hours = 0;
		Map return_map = new HashMap();
		
		try{
			sql =
				" SELECT EHF000400T0_01, EHF000400T0_03, EHF000400T0_04, EHF000400T0_07, EHF000400T0_08 " +
				" FROM EHF000400T0 " +
				" WHERE 1=1 " +
				" AND EHF000400T0_01 = '"+class_no+"'" +
				" AND EHF000400T0_11 = '"+comp_id+"'" ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				break_in = Integer.parseInt(rs.getString("EHF000400T0_07"));
				break_out = Integer.parseInt(rs.getString("EHF000400T0_08"));
				
				//型態格式器
				DecimalFormat formatter = new DecimalFormat("0000");
				
				//處理內扣時數
				break_in_sec = tools.TimeToSecs(formatter.format(break_in));
				break_out_sec = tools.TimeToSecs(formatter.format(break_out));
				break_sec = break_out_sec - break_in_sec;
				break_hours = (((float) break_sec) / 60 / 60);
				
				return_map.put("BREAL_TIME", break_hours + ""); //內扣時數
			}
			
			rs.close();
			stmt.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("時薪休息時間資訊，發生錯誤!!" + e.toString());
		}
		
		return return_map;
	}
	
	/**
	 * 計算兩個Calendar之間的時數, 並使用最低一小時進行修正, 不足最低的部分則捨去
	 * @param conn
	 * @param begin_cal
	 * @param end_cal
	 * @param break_time
	 * @param comp_id 
	 * @return
	 */
	private int getSalaryHour(Connection conn, Calendar begin_cal, Calendar end_cal, float break_time, String comp_id) throws Exception {
		// TODO Auto-generated method stub
		
		int salary_day = 0;
		float hour = 0;
		
		try{
			//計算兩個Calendar之間的時數
			hour = tools.caculateDiffHour(begin_cal, end_cal);
			
			salary_day = (int) (Math.floor(hour) - break_time);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("計算兩個Calendar之間的時數，發生錯誤!!" + e.toString());
		}
		
		return salary_day;
	}
	
	/**
	 * 取得計薪區間的實際未在職天數
	 * @param count_start_date
	 * @param count_end_date
	 * @return
	 */
	protected int getNotSalaryDay(String count_start_date, String count_end_date){
		
		int not_salary_day = 0;
		
		try{
			//取得當月在職天數
			int salary_day = this.getSalaryDay(count_start_date, count_end_date);
			
			//取得當月最大天數
			Calendar tmp_cal = Calendar.getInstance();
			tmp_cal.setTime(tools.getLastMonthDay(tools.covertStringToCalendar(count_start_date+"/01")));
			int month_day = tmp_cal.get(Calendar.DAY_OF_MONTH);
			
			//計算當月未在職天數 = 當月最大天數 - 當月在職天數
			not_salary_day = month_day - salary_day; 
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return not_salary_day;
	}
	
	/**
	 * 取得計薪區間的假日天數
	 * @param conn
	 * @param holiday_mode 01 = 國定假日, 02 = 例行假日, 03 = 所有假日
	 * @param count_start_date
	 * @param count_end_date
	 * @param comp_id
	 * @return
	 */
	protected int getSalaryHoliday(Connection conn, String holiday_mode,
									String count_start_date, String count_end_date, String comp_id){
		
		int holiday = 0;
		
		try{
			//由EMS CALENDAR進行計算
			String sql = "" +
			" SELECT COUNT(*) AS DAYS FROM ( " +
			" SELECT HA_DAY FROM ( " + 
			" SELECT DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS HA_DAY " +
			" FROM EHF000500T0 " +
			" WHERE 1=1 " +
			" AND EHF000500T0_05 BETWEEN ? AND ? " +  //日期區間
			" AND EHF000500T0_11 = ? " +  //公司代碼
			" UNION " +
			" SELECT " +
			" DATE_FORMAT(EHF000500T0_06, '%Y/%m/%d') AS HA_DAY " +
			" FROM EHF000500T0 " +
			" WHERE 1=1 " +
			" AND EHF000500T0_06 BETWEEN ? AND ? " +  //日期區間
			" AND EHF000500T0_11 = ? " +  //公司代碼
			" ) tableDay " +
			" WHERE 1=1 ";
			//holiday_mode -->  01 = 國定假日, 02 = 例行假日, 03 = 所有假日
			if("01".equals(holiday_mode)){
				sql += " AND DAYOFWEEK(HA_DAY) NOT IN (1,7) ";  //國定假日  1=星期天,7=星期六
			}else if("02".equals(holiday_mode)){
				sql += " AND DAYOFWEEK(HA_DAY) IN (1,7) ";  //例行假日(週六、日)
			}
			sql += " ) tableA " +
				   " WHERE 1=1 ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, count_start_date);  //日期區間 - 開始
			indexid++;
			p6stmt.setString(indexid, count_end_date);  //日期區間 - 結束
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, count_start_date);  //日期區間 - 開始
			indexid++;
			p6stmt.setString(indexid, count_end_date);  //日期區間 - 結束
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				holiday = rs.getInt("DAYS");
			}	
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return holiday;
	}
	
	/**
	 * 取得行事曆的大禮拜或小禮拜天數
	 * @param conn
	 * @param holiday_mode 01 = 小禮拜, 02 = 大禮拜
	 * @param count_start_date
	 * @param count_end_date
	 * @param comp_id
	 * @return
	 */
	protected int getCalendarByOddOrDouble(Connection conn, String holiday_mode, 
			String count_start_date, String count_end_date, String comp_id){
		
		int holiday = 0;
		
		try{
			String sql = "" +
			" SELECT COUNT(*) AS DAYS FROM ( " +
			" SELECT HA_DAY FROM ( " + 
			" SELECT DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS HA_DAY, EHF000500T0_12 " +
			" FROM EHF000500T0 " +
			" WHERE 1=1 " +
			" AND EHF000500T0_05 BETWEEN ? AND ? " +  //日期區間
			" AND EHF000500T0_11 = ? " +  //公司代碼
			" UNION " +
			" SELECT " +
			" DATE_FORMAT(EHF000500T0_06, '%Y/%m/%d') AS HA_DAY, EHF000500T0_12 " +
			" FROM EHF000500T0 " +
			" WHERE 1=1 " +
			" AND EHF000500T0_06 BETWEEN ? AND ? " +  //日期區間
			" AND EHF000500T0_11 = ? " +  //公司代碼
			" ) tableDay " +
			" WHERE 1=1 ";
			//holiday_mode -->  01 = 小禮拜, 02 = 大禮拜
			if("01".equals(holiday_mode)){
				sql += " AND EHF000500T0_12 = 2 ";  //找小禮拜
			}else if("02".equals(holiday_mode)){
				sql += " AND EHF000500T0_12 = 1 ";  //找大禮拜
			}
			sql += " ) tableA " +
				   " WHERE 1=1 ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, count_start_date);  //日期區間 - 開始
			indexid++;
			p6stmt.setString(indexid, count_end_date);  //日期區間 - 結束
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, count_start_date);  //日期區間 - 開始
			indexid++;
			p6stmt.setString(indexid, count_end_date);  //日期區間 - 結束
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				holiday = rs.getInt("DAYS");
			}	
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return holiday;
	}
	
	/**
	 * 依照排班表 取得員工上班應到天數或休假天數
	 * @param conn
	 * @param emp_id 			員工工號
	 * @param count_start_date	 開始日期
	 * @param count_end_date   	結束日期
	 * @param type  			0.應到天數  1.休假天數
	 * @param comp_id  			公司代碼
	 * @return
	 */
	protected int getSchedulWorkDays(Connection conn, String emp_id, String count_start_date, String count_end_date, int type, String comp_id){
		
		BaseFunction base_tools= new BaseFunction(comp_id);
		String sql = "";
		List dataList = new ArrayList();
		int day_count = 0;
		
		try{
			sql += " SELECT IFNULL(count(EHF010105T1_01),0) AS EHF010105T1_01"
				+ " FROM EHF010105T0 LEFT JOIN EHF010105T1"
				+ " ON EHF010105T0_01 = EHF010105T1_01 "
				+ " AND EHF010105T0_06 = EHF010105T1_06" 
				+ " WHERE 1 = 1"
				+ " AND EHF010105T0_02 = '" + emp_id + "'"
				+ " AND EHF010105T0_06 = '" + comp_id + "'"
				+ " AND EHF010105T1_02 BETWEEN '" + count_start_date+ "' AND '" + count_end_date + "'"
				+ " AND EHF010105T1_03 = '" + type + "'";	
			
				dataList = base_tools.queryList(sql);
				
				base_tools.close();
				
			if(dataList.size()>0){
				
				Map datamap = (Map)dataList.get(0);
			
				day_count = Integer.parseInt(datamap.get("EHF010105T1_01")+"");//取得天數
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return day_count;
	}
	
	/**
	 * 取得薪資年月的公休假日天數
	 * @param conn
	 * @param salyymm
	 * @param comp_id
	 * @return
	 */
	protected float getHaDaysInMonth( Connection conn, String salyymm, String employee_id, String comp_id ){
		
		
		float holiday = 0;
		
		try{
			//取得系統參數中, 公休日的計算方式
			String salary_holiday_get_mode = tools.getSysParam(conn, comp_id, "SALARY_HOLIDAY_GET_MODE");
			
			if("BY_USER_SET_FIXED_DAY".equals(salary_holiday_get_mode)){
				//由系統參數設定固定公休日天數
				holiday = Integer.parseInt(tools.getSysParam(conn, comp_id, "SALARY_HOLIDAY_USER_SET_FIXED_DAY"));
				
			}else if("BY_JAVA_CALENDAR".equals(salary_holiday_get_mode)){
				//由JAVA CALENDAR進行計算
				
			}else if("BY_EMS_CALENDAR".equals(salary_holiday_get_mode)){
				//由EMS CALENDAR進行計算
				
				String count_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				String count_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				holiday = this.getSalaryHoliday(conn, "", count_start_date, count_end_date, comp_id);
				
				/*
				String sql = "" +
				" SELECT COUNT(*) AS DAYS FROM ( " +
				" SELECT " + 
				" DATE_FORMAT(EHF010200T0_05, '%Y/%m/%d') AS EHF010200T0_05 " +
				" FROM EHF010200T0 " +
				" WHERE 1=1 " +
				" AND EHF010200T0_03 = '"+(Integer.parseInt((salyymm.split("/"))[0])-1911)+"' " +  //年度
				" AND DATE_FORMAT(EHF010200T0_05, '%Y/%m') = '"+salyymm+"' " +  //薪資年月
				" AND EHF010200T0_11 = '"+comp_id+"' " +  //公司代碼
				" UNION " +
				" SELECT " +
				" DATE_FORMAT(EHF010200T0_06, '%Y/%m/%d') AS EHF010200T0_06 " +
				" FROM EHF010200T0 " +
				" WHERE 1=1 " +
				" AND EHF010200T0_03 = '"+(Integer.parseInt((salyymm.split("/"))[0])-1911)+"' " +  //年度
				" AND DATE_FORMAT(EHF010200T0_06, '%Y/%m') = '"+salyymm+"' " +  //薪資年月
				" AND EHF010200T0_11 = '"+comp_id+"' " +  //公司代碼
				" ORDER BY EHF010200T0_05 " +
				" ) tableA " +
				" WHERE 1=1 ";
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					holiday = rs.getInt("DAYS");
				}	
				
				rs.close();
				stmt.close();
				*/
				
			}else if("BY_EMS_WORK_SCHEDULE".equals(salary_holiday_get_mode)){
				//依據 EMS 排班表進行計算
				/*
				//建立年月資訊
				Calendar cur_cal = Calendar.getInstance();
				int year = -1;
				int month = -1;
				String str_year = "";
				String str_month = "";
				
				//依據日期產生 cur_date && year4 && month
				try{
					//建立當前年月的Calendar
					cur_cal = tools.covertStringToCalendar(salyymm, "yyyy/MM");
					//當前年度(西元年)
					str_year = tools.covertDateToString(cur_cal.getTime(), "yyyy");
					year = Integer.parseInt(str_year);
					//當前月份
					str_month = tools.covertDateToString(cur_cal.getTime(), "MM");
					month = Integer.parseInt(str_month);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				//依據薪資年月與員工工號取得員工排班表, 並且取得該薪資年月的公休天數
				EMS_Work_Schedule_Table ems_work_schedule_table = new EMS_Work_Schedule_Table(this.sta_user_name, this.sta_comp_id);
				holiday = ems_work_schedule_table.getOneMonthHolidayInSchedule(conn, employee_id, year, month);
				*/
				String count_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				String count_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.sta_salyymm+"/01")));
				
				holiday = this.getCalendarByOddOrDouble(conn, "02", count_start_date, count_end_date, comp_id)	//大禮拜(休假)
						+ this.getSchedulWorkDays(conn, employee_id, count_start_date, count_end_date, 1, comp_id);//排班表的假日
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return holiday;
	}
	
	/**
	 * 依照排班表 取得員工上班應到天數  或休假天數
	 * @param conn
	 * @param emp_id 			員工工號
	 * @param count_start_date	 開始日期
	 * @param count_end_date   	結束日期
	 * @param type  			0.應到天數  1.休假天數
	 * @param comp_id  			公司代碼
	 * @return
	 */
	private float getSchedulDays(Connection conn, String emp_id,String count_start_date, String count_end_date,int type, String comp_id) {
		// TODO Auto-generated method stub
		BaseFunction base_tools= new BaseFunction(comp_id);
		float day=0;
		float day_count=0;
		String sql="";
		List dataList= new ArrayList();
		float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
		try {
			sql += " SELECT IFNULL(count(EHF010105T1_01),0) AS EHF010105T1_01"
				+ " FROM EHF010105T0 LEFT JOIN EHF010105T1"
				+ " ON EHF010105T0_01 = EHF010105T1_01 "
				+ " AND EHF010105T0_06 = EHF010105T1_06" 
				+ " WHERE 1 = 1"
				+ " AND EHF010105T0_02 = '" + emp_id + "'"
				+ " AND EHF010105T0_06 = '" + comp_id + "'"
				+ " AND EHF010105T1_02 BETWEEN '" + count_start_date+ "' AND '" + count_end_date + "'"
				+ " AND EHF010105T1_03 = '" + type + "'";	
			
				dataList = base_tools.queryList(sql);
			if(dataList.size()>0){
				Map datamap=(Map)dataList.get(0);
			
				day_count=Float.parseFloat(datamap.get("EHF010105T1_01")+"");//取得天數
		
				day+=day_count*Day_work_hours;//取得總工作小時
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		base_tools.close();
		//回傳小時
		return day;
	}
	/**
	 * 取得薪資項目總金額
	 * @param conn
	 * @param employee_id
	 * @param EHF030600T0_U
	 * @return
	 */
	private int getAllSalaryItemMoney( Connection conn, String employee_id, String EHF030600T0_U ){
		
		int salary_item_money = 0;
		
		try{
			//取得員工薪資項目清單
			ArrayList salaryItemList = this.getSalaryItemList( conn, employee_id, this.sta_comp_id );
			
			//如果清單有資料才進行薪資項目金額的計算
			if(salaryItemList.size() > 0){
				//清單不為空
				Iterator it = salaryItemList.iterator();
				
				while(it.hasNext()){
					
					EHF030102M0F ehf030102m0 = (EHF030102M0F) it.next();
					
					if(!"".equals(ehf030102m0.getEHF030102T0_09()) && ehf030102m0.getEHF030102T0_09() != null ){
						//有設定薪資項目計算公式
						//需要進行公式處理
						salary_item_money += this.caculateSalItemByFormula( conn, this.sta_salary_no, this.sta_salyymm, employee_id, ehf030102m0, 
																		    this.sta_user_name, this.sta_comp_id );
					}else{
						//未設定薪資項目計算公式
						//沒需要進行公式處理
						salary_item_money += Integer.parseInt(ehf030102m0.getEHF030102T0_06());  //薪資項目金額
					}
					
					//因複製歷史\薪資基本資料時, 已經對薪資項目的資料進行複製處理, 在這裡就不需要新增薪資項目的資料
					//寫入薪資項目資料
//					this.insertSalaryItemData(conn, this.sta_salary_no, this.sta_costyymm, this.sta_salyymm,
//											  employee_id, ehf030102m0, EHF030600T0_U, this.sta_user_name, this.sta_comp_id);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return salary_item_money;
	}
	
	/**
	 * 取得員工的薪資項目清單
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public ArrayList getSalaryItemList( Connection conn, String employee_id, String comp_id ){
		
		ArrayList item_list = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT EHF030200T1_01, EHF030102T0_01, EHF030102T0_02, EHF030101T0_02, EHF030102T0_03, EHF030102T0_04, " +
			" EHF030102T0_05, EHF030102T0_06, EHF030102T0_07, EHF030102T0_08, EHF030102T0_09 " +
			" FROM EHF030200T1 " +
			" LEFT JOIN EHF030102T0 ON EHF030102T0_01 = EHF030200T1_02 AND EHF030102T0_08 = EHF030200T1_04 " +
			" LEFT JOIN EHF030101T0 ON EHF030101T0_01 = EHF030102T0_03 AND EHF030101T0_05 = EHF030200T1_04 " +
			" WHERE 1=1 " +
			" AND EHF030200T1_06 = '1'" +
			" AND EHF030200T1_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF030200T1_04 = '"+comp_id+"' ";  //公司代碼
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				EHF030102M0F ehf030102m0 = new EHF030102M0F();
				ehf030102m0.setEHF030101T0_02(rs.getString("EHF030101T0_02"));  //薪資項目名稱
				ehf030102m0.setEHF030102T0_01(rs.getString("EHF030102T0_01"));  //薪資項目金額序號
				ehf030102m0.setEHF030102T0_02(rs.getString("EHF030102T0_02"));  //部門代號
				ehf030102m0.setEHF030102T0_03(rs.getString("EHF030102T0_03"));  //薪資項目序號
				ehf030102m0.setEHF030102T0_04(rs.getString("EHF030102T0_04"));  //是否納入所得
				ehf030102m0.setEHF030102T0_05(rs.getString("EHF030102T0_05"));  //類別
				ehf030102m0.setEHF030102T0_06(rs.getString("EHF030102T0_06"));  //金額
				ehf030102m0.setEHF030102T0_07(rs.getString("EHF030102T0_07"));  //備註
				ehf030102m0.setEHF030102T0_08(rs.getString("EHF030102T0_08"));  //公司代碼
				ehf030102m0.setEHF030102T0_09(rs.getString("EHF030102T0_09"));  //薪資項目計算公式序號
				
				item_list.add(ehf030102m0);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return item_list;
	}
	
	/**
	 * 新增已計算的薪資項目資料
	 * @param conn
	 * @param salary_no
	 * @param costyymm
	 * @param salyymm
	 * @param ehf030102m0
	 * @param EHF030600T0_U
	 * @param user_name
	 * @param comp_id
	 */
	protected void insertSalaryItemData( Connection conn, String salary_no, String costyymm, String salyymm,
										 String employee_id, EHF030102M0F ehf030102m0,
										 String EHF030600T0_U, String user_name, String comp_id ){
		
		try{
			//新增已計算的薪資項目資料
			String sql = "" +
			" INSERT INTO EHF030600t3 ( EHF030600T3_01, EHF030600T3_M, EHF030600T3_M1, EHF030600T3_02, " +
			" EHF030600T3_03, EHF030600T3_04, EHF030600T3_05, EHF030600T3_06, EHF030600T3_07, EHF030600T3_SCU, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, salary_no);  //薪資計算資料序號
			indexid++;
			p6stmt.setString(indexid, costyymm);  //入扣帳年月
			indexid++;
			p6stmt.setString(indexid, salyymm);  //薪資年月
			indexid++;
			p6stmt.setString(indexid, employee_id);  //員工工號
			indexid++;
			p6stmt.setString(indexid, ehf030102m0.getEHF030102T0_04());  //是否納入所得
			indexid++;
			p6stmt.setString(indexid, ehf030102m0.getEHF030102T0_05());  //類別
			indexid++;
			p6stmt.setString(indexid, ehf030102m0.getEHF030101T0_02());  //薪資項目名稱
			indexid++;
			p6stmt.setInt(indexid, Integer.parseInt(ehf030102m0.getEHF030102T0_06()));  //金額
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, EHF030600T0_U);  //薪資計算UID
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
		}
		
	}	
	
	/**
	 * 更新修改後的薪資項目金額
	 * @param conn
	 * @param salary_no
	 * @param employee_id
	 * @param salary_item_name
	 * @param salary_item_money
	 * @param user_name
	 * @param comp_id
	 */
	protected void updateSalaryItemData( Connection conn, String salary_no, String employee_id, String salary_item_name, int salary_item_money,
										 String user_name, String comp_id ){

		try{
			//新增已計算的薪資項目資料
			String sql = "" +
			" UPDATE EHF030600t3 " +
			" SET EHF030600T3_06=?, USER_UPDATE=?, DATE_UPDATE=NOW()" +
			" WHERE 1=1 " +
			" AND EHF030600T3_01 = "+salary_no+" " +  //薪資計算序號
			" AND EHF030600T3_02 = '"+employee_id+"' " +  //員工工號
			" AND EHF030600T3_05 = '"+salary_item_name+"' " +  //薪資項目名稱
			" AND EHF030600T3_07 = '"+comp_id+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;

			p6stmt.setInt(indexid, salary_item_money);  //金額
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 依據薪資項目計算公式計算薪資項目金額
	 * @param conn
	 * @param salyymm
	 * @param employee_id
	 * @param ehf030102m0
	 * @param comp_id
	 * @return
	 */
	protected int caculateSalItemByFormula( Connection conn, String salary_no, String salyymm, String employee_id,
											EHF030102M0F ehf030102m0, String user_name, String comp_id ){
		
		int sal_item_money = 0;
		
		try{
			//取得薪資項次計算公式資料
			EHF030102M1F ehf030102m1 = this.getSalaryItemFormula(conn, Integer.parseInt(ehf030102m0.getEHF030102T0_09()), comp_id);
			
			int original_money = Integer.parseInt(ehf030102m0.getEHF030102T0_06());
			String type = "";  //條件
			int frequency = 0;  //次數
			
			//進行薪資項目計算公式處理
			if("01".equals(ehf030102m1.getEHF030102T1_03())){
				//依遲到條件
				//判斷執行條件
				if("01".equals(ehf030102m1.getEHF030102T1_04())){
					//每次
					type = "01";
					frequency = 1;
				}else if("02".equals(ehf030102m1.getEHF030102T1_04())){
					//每週
					type = "02";
					frequency = Integer.parseInt(ehf030102m1.getEHF030102T1_04_FREQ());
				}else if("03".equals(ehf030102m1.getEHF030102T1_04())){
					//每月
					type = "03";
					frequency = Integer.parseInt(ehf030102m1.getEHF030102T1_04_FREQ());
				}
				
				//進行遲到條件的判斷
				sal_item_money = this.judgeSalItemByLateCond( conn, original_money, ehf030102m1, 
															  type, frequency, salyymm, employee_id, comp_id);
				
			}else if("02".equals(ehf030102m1.getEHF030102T1_03())){
				//依請假條件
				//判斷執行條件
				if("01".equals(ehf030102m1.getEHF030102T1_04())){
					//每次
					type = "01";
					frequency = 1;
				}else if("02".equals(ehf030102m1.getEHF030102T1_04())){
					//每週
					type = "02";
					frequency = Integer.parseInt(ehf030102m1.getEHF030102T1_04_FREQ());
				}else if("03".equals(ehf030102m1.getEHF030102T1_04())){
					//每月
					type = "03";
					frequency = Integer.parseInt(ehf030102m1.getEHF030102T1_04_FREQ());
				}
				
				//進行請假條件的判斷
				sal_item_money = this.judgeSalItemByVaCond( conn, original_money, ehf030102m1, 
						  									type, frequency, salyymm, employee_id, comp_id );

			}else{
				//特殊條件處理
				
			}
			
			if( sal_item_money < 0 ){
				sal_item_money = 0;
			}
			
			//更新薪資項目的金額
			this.updateSalaryItemData(conn, salary_no, employee_id, ehf030102m0.getEHF030101T0_02(), sal_item_money, user_name, comp_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sal_item_money;
	}
	
	/**
	 * 依據遲到條件判斷薪資項目金額
	 * @param conn
	 * @param original_money
	 * @param ehf030102m1
	 * @param type
	 * @param frequency
	 * @param salyymm
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected int judgeSalItemByLateCond( Connection conn, int original_money, EHF030102M1F ehf030102m1, 
										  String type, int frequency, String salyymm, String employee_id, String comp_id ){
		
		int salary_item_money = 0;
		
		try{
			int times = 0;
			
			String sql = "" +
			" SELECT COUNT(*) AS COUNTNUM, CUR_DATE, WEEKNUM, MONTHNUM FROM ( " +
			" SELECT EHF020401T0_05 AS CUR_DATE, WEEK(EHF020401T0_05) AS WEEKNUM, MONTH(EHF020401T0_05) AS MONTHNUM " +
			" FROM EHF020401T0 " +
			" LEFT JOIN EHF020403T1 ON EHF020403T1_02 = EHF020401T0_02 AND EHF020403T1_06 = EHF020401T0_11 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			" AND DATE_FORMAT(EHF020401T0_05, '%Y/%m') = '"+salyymm+"' " +  //薪資年月
			" AND EHF020401T0_08 = '1' " +  //上班
			" AND EHF020401T0_09 = true " +  //異常
			" AND EHF020401T0_11 = '"+comp_id+"' " +  //公司代碼
			" UNION " +
			" SELECT EHF020406T0_04 AS CUR_DATE, WEEK(EHF020406T0_04) AS WEEKNUM, MONTH(EHF020406T0_04) AS MONTHNUM " +
			" FROM EHF020406T0 " +
			" WHERE 1=1 " +
			" AND EHF020406T0_01 = '"+employee_id+"' " +  //員工工號
			" AND DATE_FORMAT(EHF020406T0_04, '%Y/%m') = '"+salyymm+"' " +  //薪資年月
			" AND EHF020406T0_05 = '1' " +  //上班
			" AND EHF020406T0_05_FLG = true " +  //異常
			" AND EHF020406T0_07 = '"+comp_id+"' " +  //公司代碼
			" ) tableA " +
			" WHERE 1=1 " +
			" AND DATE_FORMAT(CUR_DATE, '%Y/%m/%d') NOT IN " +
			" ( " +
			" SELECT " + 
			" DATE_FORMAT(EHF010200T0_05, '%Y/%m/%d') AS EHF010200T0_05 " +
			" FROM EHF010200T0 " +
			" WHERE 1=1 " +
			" AND EHF010200T0_03 = '"+(Integer.parseInt((salyymm.split("/"))[0])-1911)+"' " +  //年度
			" AND EHF010200T0_11 = '"+comp_id+"' " +  //公司代碼
			" UNION " + 
			" SELECT " + 
			" DATE_FORMAT(EHF010200T0_06, '%Y/%m/%d') AS EHF010200T0_06 " +
			" FROM EHF010200T0 " +
			" WHERE 1=1 " +
			" AND EHF010200T0_03 = '"+(Integer.parseInt((salyymm.split("/"))[0])-1911)+"' " +  //年度
			" AND EHF010200T0_11 = '"+comp_id+"' " +  //公司代碼
			" ) " ;
			//判斷執行條件類型
			if("01".equals(type) || "03".equals(type)){
				//每次 or 每月
				sql += " GROUP BY MONTHNUM ";
				
			}else if("02".equals(type)){
				//每週
				sql += " GROUP BY WEEKNUM ";
				
			}
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			
			//處理執行條件
			if("01".equals(type)){
				//每次
				if(rs.next()){
					times = rs.getInt("COUNTNUM");  //執行次數
				}
			}else if("02".equals(type)){
				//每週
				while(rs.next()){
					//大於等於設定的執行次數
					if(rs.getInt("COUNTNUM") >= frequency ){
						times += 1;  //執行次數
					}
				}
			}else if("03".equals(type)){
				//每月
				if(rs.next()){
					//大於等於設定的執行次數
					if(rs.getInt("COUNTNUM") >= frequency ){
						times = 1;  //執行次數
					}
				}
			}
			
			rs.close();
			stmt.close();
			
			//取得公式計算後的金額
			salary_item_money = this.getSalItemMoneybyFormula(original_money, ehf030102m1, times);
			
			if( salary_item_money < 0 ){
				salary_item_money = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return salary_item_money;
	}
	
	/**
	 * 依據請假資料判斷薪資項目金額
	 * @param conn
	 * @param original_money
	 * @param ehf030102m1
	 * @param type
	 * @param frequency
	 * @param salyymm
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	protected int judgeSalItemByVaCond( Connection conn, int original_money, EHF030102M1F ehf030102m1, 
										  String type, int frequency, String salyymm, String employee_id, String comp_id ){
		
		int salary_item_money = 0;
		
		try{
			int times = 0;
			
			String sql = "" +
			" SELECT COUNT(*) AS COUNTNUM, WEEKNUM, MONTHNUM FROM ( " +
			" SELECT DISTINCT FORM_KEY, VA_DATE, WEEKNUM, MONTHNUM FROM ( " +
			" SELECT " + 
			" EHF020200T0_01 AS FORM_KEY, EHF020200T0_14, " +
			" DATE_FORMAT(EHF020200T0_08, '%Y/%m/%d') AS FILL_DATE, " +
			" DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS VA_DATE, " +
			" WEEK(EHF020200T0_09) AS WEEKNUM, " +
			" MONTH(EHF020200T0_09) AS MONTHNUM " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND DATE_FORMAT(EHF020200T0_09, '%Y/%m') = '"+salyymm+"' ";  //薪資年月
			if(!"".equals(ehf030102m1.getEHF030102T1_03_TYPE()) && ehf030102m1.getEHF030102T1_03_TYPE() != null ){
				sql += " AND EHF020200T0_14 = '"+ehf030102m1.getEHF030102T1_03_TYPE()+"' ";  //假別
			}
			sql += "" +
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
			" UNION " +
			" SELECT " + 
			" EHF020200T0_01 AS FORM_KEY, EHF020200T0_14, " +
			" DATE_FORMAT(EHF020200T0_08, '%Y/%m/%d') AS FILL_DATE, " +
			" DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') AS VA_DATE, " +
			" WEEK(EHF020200T0_10) AS WEEKNUM, " +
			" MONTH(EHF020200T0_10) AS MONTHNUM " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND DATE_FORMAT(EHF020200T0_10, '%Y/%m') = '"+salyymm+"' ";  //薪資年月
			if(!"".equals(ehf030102m1.getEHF030102T1_03_TYPE()) && ehf030102m1.getEHF030102T1_03_TYPE() != null ){
				sql += " AND EHF020200T0_14 = '"+ehf030102m1.getEHF030102T1_03_TYPE()+"' ";  //假別
			}
			sql += "" +
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
			" ) tableA " +
			" WHERE 1=1 ";
			//判斷執行條件類型
			if("01".equals(type) || "03".equals(type)){
				//每次 or 每月
				sql += " GROUP BY MONTHNUM, FORM_KEY ";
				
			}else if("02".equals(type)){
				//每週
				sql += " GROUP BY WEEKNUM, FORM_KEY ";
				
			}

			sql += "" +
			" ) tableB " +
			" WHERE 1=1 ";
			//判斷執行條件類型
			if("01".equals(type) || "03".equals(type)){
				//每次 or 每月
				sql += " GROUP BY MONTHNUM ";
				
			}else if("02".equals(type)){
				//每週
				sql += " GROUP BY WEEKNUM ";
				
			}
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			
			//處理執行條件
			if("01".equals(type)){
				//每次
				if(rs.next()){
					times = rs.getInt("COUNTNUM");  //執行次數
				}
			}else if("02".equals(type)){
				//每週
				while(rs.next()){
					//大於等於設定的執行次數
					if(rs.getInt("COUNTNUM") >= frequency ){
						times += 1;  //執行次數
					}
				}
			}else if("03".equals(type)){
				//每月
				if(rs.next()){
					//大於等於設定的執行次數
					if(rs.getInt("COUNTNUM") >= frequency ){
						times = 1;  //執行次數
					}
				}
			}
			
			rs.close();
			stmt.close();
			
			//取得公式計算後的金額
			salary_item_money = this.getSalItemMoneybyFormula(original_money, ehf030102m1, times);
			
			if( salary_item_money < 0 ){
				salary_item_money = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return salary_item_money;
	}
	
	
	
	/**
	 * 取得依據公式換算後的薪資項目金額
	 * @param original_money
	 * @param ehf030102m1
	 * @return
	 */
	protected int getSalItemMoneybyFormula( int original_money, EHF030102M1F ehf030102m1, int times ){
		
		int return_money = 0;
		
		try{
			//判斷金額加減類型
			if("01".equals(ehf030102m1.getEHF030102T1_06())){
				//依比例
				float rate = Float.parseFloat(ehf030102m1.getEHF030102T1_07());
				if( rate > 1 ){
					rate = rate - 1;
				}else if( rate < 1){
					rate = rate - 1;
				}else if( rate == 1 ){
					rate = -1;
				}
				
				int dis_money = Math.round(original_money * rate);  //四捨五入
				return_money = original_money + ( dis_money * times );  
			}else if("02".equals(ehf030102m1.getEHF030102T1_06())){
				//依固定金額
				return_money = original_money + (Integer.parseInt(ehf030102m1.getEHF030102T1_08()) * times);
			}else{
				//依金額加減特殊條件Key
			}
			
			if( return_money < 0 ){
				return_money = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_money;
	}
	
	/**
	 * 取得薪資項目計算公式的資料
	 * @param conn
	 * @param ehf030102m0
	 * @return
	 */
	public EHF030102M1F getSalaryItemFormula( Connection conn, int formula_no, String comp_id ){
		
		EHF030102M1F ehf030102m1 = null;
		
		try{
			String sql = "" +
			" SELECT * FROM EHF030102T1 " +
			" WHERE 1=1 " +
			" AND EHF030102T1_01 = "+formula_no+" " +  //薪資項目計算公式序號
			" AND EHF030102T1_11 = '"+comp_id+"' ";  //公司代碼
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				ehf030102m1 = new EHF030102M1F();
				ehf030102m1.setEHF030102T1_01(rs.getString("EHF030102T1_01"));  //薪資項目計算公式資料序號
				ehf030102m1.setEHF030102T1_02(rs.getString("EHF030102T1_02"));  //薪資項目計算公式名稱
				ehf030102m1.setEHF030102T1_03(rs.getString("EHF030102T1_03"));  //公式類型
				ehf030102m1.setEHF030102T1_03_TYPE(rs.getString("EHF030102T1_03_TYPE"));  //公式類型內容的類別
				ehf030102m1.setEHF030102T1_04(rs.getString("EHF030102T1_04"));  //執行條件
				ehf030102m1.setEHF030102T1_04_FREQ(rs.getString("EHF030102T1_04_FREQ"));  //執行條件次數
				ehf030102m1.setEHF030102T1_05(rs.getString("EHF030102T1_05"));  //公式特殊條件Key
				ehf030102m1.setEHF030102T1_06(rs.getString("EHF030102T1_06"));  //金額加減類型
				ehf030102m1.setEHF030102T1_07(rs.getString("EHF030102T1_07"));  //金額加減比例
				ehf030102m1.setEHF030102T1_08(rs.getString("EHF030102T1_08"));  //金額加減固定金額
				ehf030102m1.setEHF030102T1_09(rs.getString("EHF030102T1_09"));  //金額加減特殊條件Key
				ehf030102m1.setEHF030102T1_10(rs.getString("EHF030102T1_10"));  //備註
				ehf030102m1.setEHF030102T1_11(rs.getString("EHF030102T1_11"));  //公司代碼
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ehf030102m1;
	}
	
	/**
	 * 取得請假扣薪金額
	 * @param conn
	 * @param employee_id
	 * @return
	 */
	private Map getVaDeductionMoney( Connection conn, String employee_id ){
		
		Map va_map = new HashMap();
		
		try{
			//取得員工的時薪
			int hour_pay = this.getEmpHourPay(conn, 1, employee_id, this.sta_costyymm, this.sta_salyymm, this.sta_comp_id);
			//取得員工的日薪
			int day_pay = this.getEmpDayPay(conn, employee_id, this.sta_costyymm, this.sta_salyymm, this.sta_comp_id);
			//取得工作時數
			float work_hours = Float.parseFloat(tools.getSysParam(conn, this.sta_comp_id, "WORK_HOURS"));
			
			//取得請假扣薪狀態
			String VaDeductionType = tools.getSysParam(conn, this.sta_comp_id, "LABOR_STANDARDS");
			
			//取得請假扣薪, 時薪的計算方式
			String vacation_count_mode = tools.getSysParam(conn, this.sta_comp_id, "VACATION_COUNT_MODE");
			
			//取得不算請假扣薪, 但要扣本薪金額的假別型態 edit by joe 2012/01/09
			Map not_vacation_type = tools.stringArrayToMap(tools.getSysParam(conn, this.sta_comp_id, "NOT_VACATION_TYPE").trim().split(","));
			
			/*
			//判斷勞基法啟用狀態
			if("ENABLE".equals(VaDeductionType)){
				//啟用勞基法
				//計算扣薪金額
				va_map = this.VaDeductionWithLABOR( conn, employee_id, vacation_count_mode, work_hours, day_pay, hour_pay, 
													not_vacation_type, this.sta_salyymm, this.sta_comp_id );
			}else if("DISABLE".equals(VaDeductionType)){
				//未啟用勞基法
				//計算扣薪金額
				va_map = this.VaDeductionWithoutLABOR( conn, employee_id, vacation_count_mode, work_hours, day_pay, hour_pay, 
													   not_vacation_type, this.sta_salyymm, this.sta_comp_id );
			}else{
				//預設 - 啟用勞基法
				//計算扣薪金額
				va_map = this.VaDeductionWithLABOR( conn, employee_id, vacation_count_mode, work_hours, day_pay, hour_pay, 
													not_vacation_type, this.sta_salyymm, this.sta_comp_id );
			}
			*/
			
			//請假扣薪金額的計算改由 '依據系統假別資訊做處理' !! , edit by joe 2012/05/08
			va_map = this.VaDeductionCaculate(conn, employee_id, vacation_count_mode, 
										      work_hours, day_pay, hour_pay, not_vacation_type, 
										      this.sta_salyymm, this.sta_comp_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return va_map;
	}
	
	/**
	 * 依據勞基法計算請假扣薪金額
	 * @param conn
	 * @param employee_id
	 * @param hour_pay
	 * @param salyymm
	 * @param comp_id
	 * @return
	 */
	protected Map VaDeductionWithLABOR( Connection conn, String employee_id, String vacation_count_mode, 
										float work_hours, int day_pay, int hour_pay, Map not_vacation_type,
									    String salyymm, String comp_id ){
		
		Map vamap = new HashMap();
		int va_deduction_money = 0;
		float float_va_deduction_money = 0;
		float float_va_deduction_hour = 0;
		float va_hours = 0;
		int not_va_deduction_money = 0;
		float not_float_va_deduction_money = 0;
		float not_float_va_deduction_hour = 0;
		float not_va_hours = 0;
		
		
		try{
			String sql = "" +
			" SELECT VA_TYPE, SUM(HOURS) AS HOURS FROM ( " +
			" SELECT EHF020200T0_14 AS VA_TYPE, EHF020200T0_13, " +
			" ((SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) * "+work_hours+" ) + " +
			" (SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5))) AS HOURS " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND DATE_FORMAT(EHF020200T0_10, '%Y/%m') = '"+salyymm+"' " +  //薪資年月, 不能跨月填假單
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
			" ) tableA " +
			" WHERE 1=1 " +
			" GROUP BY VA_TYPE ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				if("A01".equals(rs.getString("VA_TYPE"))){
					//病假 - 半薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") * 0.5 );
					float_va_deduction_hour += ( rs.getFloat("HOURS") * 0.5 );
					va_hours += rs.getFloat("HOURS");
				}else if("A02".equals(rs.getString("VA_TYPE"))){
					//事假 - 無薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
					float_va_deduction_hour += ( rs.getFloat("HOURS") );
					va_hours += rs.getFloat("HOURS");
				}else if("A03".equals(rs.getString("VA_TYPE"))){
					//特休 - 全薪
					
				}else if("A04".equals(rs.getString("VA_TYPE"))){
					//公假 - 全薪
					
				}else if("A05".equals(rs.getString("VA_TYPE"))){
					//婚假 - 全薪
					
				}else if("A06".equals(rs.getString("VA_TYPE"))){
					//產假 - 全薪
					
				}else if ("A07".equals(rs.getString("VA_TYPE"))){
					//喪假 - 全薪
					
				}else{
					//先處理非假假別的計算
					if(not_vacation_type.containsValue(rs.getString("VA_TYPE"))){
						//表示此次處理的假別是 "非假假別"
						not_float_va_deduction_hour += ( rs.getFloat("HOURS") );
						not_va_hours += rs.getFloat("HOURS");
					}else{
						//預設 - 無薪
						//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
						float_va_deduction_hour += ( rs.getFloat("HOURS") );
						va_hours += rs.getFloat("HOURS");
					}
				}	
			}
			
			rs.close();
			stmt.close();
			
			//計算請假有扣薪的總時數並計算扣薪金額
			float_va_deduction_money = this.countRealVaDeductionMoney(float_va_deduction_hour, work_hours, day_pay, hour_pay);
			
			//計算非假假別要算扣除本薪的金額
			not_float_va_deduction_money = this.countRealVaDeductionMoney(not_float_va_deduction_hour, work_hours, day_pay, hour_pay);
			
			//處理請假扣薪金額進位
			if( float_va_deduction_money < 1 ){
				va_deduction_money = 0;
			}else{
				va_deduction_money = tools.fixNumByMode(float_va_deduction_money, vacation_count_mode);
			}
			
			//處理非假扣除本薪金額進位
			if( not_float_va_deduction_money < 1 ){
				not_va_deduction_money = 0;
			}else{
				not_va_deduction_money = tools.fixNumByMode(not_float_va_deduction_money, vacation_count_mode);
			}
			
			vamap.put("MONEY", va_deduction_money);
			vamap.put("HOURS", "請假時數:"+va_hours+"小時");
			vamap.put("NOT_VA_MONEY", not_va_deduction_money);
			vamap.put("NOT_VA_HOURS", "非假扣除本薪的時數:"+not_va_hours+"小時");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return vamap;
	}
	
	/**
	 * 不依據勞基法計算請假扣薪金額
	 * @param conn
	 * @param employee_id
	 * @param hour_pay
	 * @param salyymm
	 * @param comp_id
	 * @return
	 */
	protected Map VaDeductionWithoutLABOR( Connection conn, String employee_id, String vacation_count_mode,
										   float work_hours, int day_pay, int hour_pay, Map not_vacation_type,
										   String salyymm, String comp_id ){
		
		Map vamap = new HashMap();
		int va_deduction_money = 0;
		float float_va_deduction_money = 0;
		float float_va_deduction_hour = 0;
		float va_hours = 0;
		int not_va_deduction_money = 0;
		float not_float_va_deduction_money = 0;
		float not_float_va_deduction_hour = 0;
		float not_va_hours = 0;
		
		try{
			String sql = "" +
			" SELECT VA_TYPE, SUM(HOURS) AS HOURS FROM ( " +
			" SELECT EHF020200T0_14 AS VA_TYPE, EHF020200T0_13, " +
			" ((SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) * "+work_hours+" ) + " +
			" (SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5))) AS HOURS " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND DATE_FORMAT(EHF020200T0_10, '%Y/%m') = '"+salyymm+"' " +  //薪資年月
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
			" ) tableA " +
			" WHERE 1=1 " +
			" GROUP BY VA_TYPE ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				if("A01".equals(rs.getString("VA_TYPE"))){
					//病假 - 無薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
					float_va_deduction_hour += ( rs.getFloat("HOURS") );
					va_hours += rs.getFloat("HOURS");
				}else if("A02".equals(rs.getString("VA_TYPE"))){
					//事假 - 無薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
					float_va_deduction_hour += ( rs.getFloat("HOURS") );
					va_hours += rs.getFloat("HOURS");
				}else if("A03".equals(rs.getString("VA_TYPE"))){
					//特休 - 無薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
					float_va_deduction_hour += ( rs.getFloat("HOURS") );
					va_hours += rs.getFloat("HOURS");
				}else if("A04".equals(rs.getString("VA_TYPE"))){
					//公假 - 無薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
					float_va_deduction_hour += ( rs.getFloat("HOURS") );
					va_hours += rs.getFloat("HOURS");
				}else if("A05".equals(rs.getString("VA_TYPE"))){
					//婚假 - 無薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
					float_va_deduction_hour += ( rs.getFloat("HOURS") );
					va_hours += rs.getFloat("HOURS");
				}else if("A06".equals(rs.getString("VA_TYPE"))){
					//產假 - 無薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
					float_va_deduction_hour += ( rs.getFloat("HOURS") );
					va_hours += rs.getFloat("HOURS");
				}else if ("A07".equals(rs.getString("VA_TYPE"))){
					//喪假 - 無薪
					//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
					float_va_deduction_hour += ( rs.getFloat("HOURS") );
					va_hours += rs.getFloat("HOURS");
				}else{
					//先處理非假假別的計算
					if(not_vacation_type.containsValue(rs.getString("VA_TYPE"))){
						//表示此次處理的假別是 "非假假別"
						not_float_va_deduction_hour += ( rs.getFloat("HOURS") );
						not_va_hours += rs.getFloat("HOURS");
					}else{
						//預設 - 無薪
						//float_va_deduction_money += (hour_pay * rs.getFloat("HOURS") );
						float_va_deduction_hour += ( rs.getFloat("HOURS") );
						va_hours += rs.getFloat("HOURS");
					}
				}	
			}
			
			rs.close();
			stmt.close();
			
			//計算請假有扣薪的總時數並計算扣薪金額
			float_va_deduction_money = this.countRealVaDeductionMoney(float_va_deduction_hour, work_hours, day_pay, hour_pay);
			
			//計算非假假別要算扣除本薪的金額
			not_float_va_deduction_money = this.countRealVaDeductionMoney(not_float_va_deduction_hour, work_hours, day_pay, hour_pay);
			
			//處理請假扣薪金額進位
			if( float_va_deduction_money < 0 ){
				va_deduction_money = 0;
			}else{
				va_deduction_money = tools.fixNumByMode(float_va_deduction_money, vacation_count_mode);
			}
			
			//處理非假扣除本薪金額進位
			if( not_float_va_deduction_money < 1 ){
				not_va_deduction_money = 0;
			}else{
				not_va_deduction_money = tools.fixNumByMode(not_float_va_deduction_money, vacation_count_mode);
			}
			
			vamap.put("MONEY", va_deduction_money);
			vamap.put("HOURS", "請假時數:"+va_hours+"小時");
			vamap.put("NOT_VA_MONEY", not_va_deduction_money);
			vamap.put("NOT_VA_HOURS", "非假扣除本薪的時數:"+not_va_hours+"小時");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return vamap;	
	}
	
	/**
	 * 處理請假扣薪金額計算
	 * @param conn
	 * @param employee_id
	 * @param vacation_count_mode
	 * @param work_hours
	 * @param day_pay
	 * @param hour_pay
	 * @param not_vacation_type
	 * @param salyymm
	 * @param comp_id
	 * @return
	 */
	protected Map VaDeductionCaculate( Connection conn, String employee_id, String vacation_count_mode, 
									   float work_hours, int day_pay, int hour_pay, Map not_vacation_type,
									   String salyymm, String comp_id ){

		Map vamap = new HashMap();
		int va_deduction_money = 0;
		int total_va_deduction_money = 0;
		float float_va_deduction_money = 0;
		float float_va_deduction_hour = 0;
		boolean show_va_deduction_hour = false;
		float va_hours = 0;
		float total_va_hours = 0;
		int not_va_deduction_money = 0;
		int total_not_va_deduction_money = 0;
		float not_float_va_deduction_money = 0;
		float not_float_va_deduction_hour = 0;
		float not_va_hours = 0;
		float total_not_va_hours = 0;
		

		try{
			//處理請假單納入本月計薪的例外狀況 edit by joe 2012/08/30
			boolean handle_except = false;
			Calendar cal_salyymm = tools.covertStringToCalendar(salyymm, "yyyy/MM");
			cal_salyymm.add(Calendar.MONTH, 1);  //取得下個月
			cal_salyymm.set(Calendar.DAY_OF_MONTH, 1);  //設定為下個月的1號
			String the_first_day_of_next_month = tools.covertDateToString(cal_salyymm.getTime(), "yyyy/MM/dd");
			cal_salyymm.add(Calendar.DAY_OF_MONTH, -1);  //設定為這個月的最後一日
			String the_last_day_of_this_month = tools.covertDateToString(cal_salyymm.getTime(), "yyyy/MM/dd");
			
			//建立員工排班表元件
			EMS_Work_Schedule_Table ems_work_schedule = new EMS_Work_Schedule_Table("EMS系統", comp_id);
			
			EMS_Work_ScheduleForm cur_emp_work_schedule = ems_work_schedule.getOneDayInSchedule(conn, employee_id, the_last_day_of_this_month);
			Map classMap = null;
			Map tempClassMap = null;
			
			//取得員工目前班別資訊
			AuthorizedBean user_authbean = new AuthorizedBean();
			
			user_authbean.setUserId(this.sta_user_id);
			user_authbean.setEmployeeID(employee_id);
			user_authbean.setCompId(comp_id);
			HR_TOOLS hr_tools = new HR_TOOLS();
			tempClassMap =  hr_tools.getEmpClass(conn, user_authbean);
			hr_tools.close();
			if(cur_emp_work_schedule.getSchedule_no() != -1){
				classMap = hr_tools.getEmpClassByNo(conn, comp_id, cur_emp_work_schedule.getClass_no());
				//避免 class_no 不正確, 若以 class_no 找不到 classMap 則以員工當前班別作為 classMap
				if(classMap.isEmpty()){
					classMap = tempClassMap;
				}
			}else{
				classMap = tempClassMap;
			}
			//判斷班別下班是否有跨午夜
			if(tools.hasCrossMidnight(tempClassMap, 2)){
				//如果有跨午夜則需要處理例外
				handle_except = true;
			}else{
				//如果沒有跨午夜則需要處理例外
				handle_except = false;
			}
			
			//取得下班時間的秒數
			int work_end_time =	tools.TimeToSecs((String)classMap.get("WORK_END_TIME")); 
			
			//先做例外處理
			String sql_except = "" +
			" SELECT VA_TYPE, VA_NAME, SUM(HOURS) AS HOURS FROM ( " +
			" SELECT EHF020200T0_14 AS VA_TYPE, " +  //假別代號
			" EHF020100T0_04 AS VA_NAME, " +  //假別名稱
			" EHF020200T0_13, " +
			" ((SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) * "+work_hours+" ) + " +
			" (SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5))) AS HOURS " +
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020200T0_09 = '"+the_last_day_of_this_month+"' " +  //請假(起)
			" AND EHF020200T0_10 = '"+the_last_day_of_this_month+"' " +  //請假(迄)
			" AND ((SUBSTRING(EHF020200T0_12, 1, 2) * 60) + SUBSTRING(EHF020200T0_12, 3, 2)) <= "+work_end_time+" " +  //判斷請假的時間要小於等於下班時間
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
			" ) tableA " +
			" WHERE 1=1 " +
			" GROUP BY VA_TYPE ";

			
			String sql = "" +
			" SELECT VA_TYPE, VA_NAME, SUM(HOURS) AS HOURS FROM ( " +
			" SELECT EHF020200T0_14 AS VA_TYPE, " +  //假別代號
			" EHF020100T0_04 AS VA_NAME, " +  //假別名稱
			" EHF020200T0_13, " +
			" ((SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) * "+work_hours+" ) + " +
			" (SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5))) AS HOURS " +
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			
			//請假單納入該月薪資計算的判定程式, 要針對班別是跨午夜的狀況做特別處理
			//Ex: 2012/04/30 是該月最後一天, 
			//若某員工班別是 17:00 - 01:00, 則該天正常上下班時間應為 2012/04/30 17:00 - 2012/05/01 01:00
			//若該員工有一張請假單是 2012/05/01 00:00 - 2012/05/01 01:00, 1hr,
			//則此請假單也必須納入本月薪資做計算, edit by joe 2012/05/08
			//如果請假起迄日為同一天, 則只要起日或器日落在該月份即納入薪資做計算
			" AND ( ((EHF020200T0_09 = EHF020200T0_10) AND ( DATE_FORMAT(EHF020200T0_09, '%Y/%m') = '"+salyymm+"' " +
			"      OR DATE_FORMAT(EHF020200T0_10, '%Y/%m') = '"+salyymm+"' )) " +
			//如果請假起迄日為不同天, 則只要起日落在該月份即納入薪資做計算
			"     OR ( (EHF020200T0_09 <> EHF020200T0_10) AND (DATE_FORMAT(EHF020200T0_09, '%Y/%m') = '"+salyymm+"') ) ) " +
			" AND EHF020200T0_16 = '0006' " +  //表單狀態 = 完成
			" AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
			" ) tableA " +
			" WHERE 1=1 " +
			" GROUP BY VA_TYPE ";
			
			//判斷是否使用例外處理SQL語句
			if(handle_except){
				sql = sql + " UNION " + sql_except;
			}
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			EHF020100M0F ehf020100m0 = null;
			StringBuffer vacation_information = new StringBuffer();
			
			while(rs.next()){
				
				//設定預設值
				not_float_va_deduction_hour = 0;
				not_va_hours = 0;
				float_va_deduction_hour = 0;
				va_hours = 0;
				
				//先處理非假假別的計算
				if(not_vacation_type.containsValue(rs.getString("VA_TYPE"))){
					//表示此次處理的假別是 "非假假別"
					not_float_va_deduction_hour += ( rs.getFloat("HOURS") );
					not_va_hours += rs.getFloat("HOURS");
					
					//修改請假扣薪內部計算邏輯, 將各假別的的扣薪金額獨立算出後再加總, 以便在個人薪資條顯示每個假別的扣薪金額
					//edit by joe 2012/10/08
					//計算非假假別要算扣除本薪的金額
					not_float_va_deduction_money = this.countRealVaDeductionMoney(not_float_va_deduction_hour, work_hours, day_pay, hour_pay);
					
					//處理非假扣除本薪金額進位
					if( not_float_va_deduction_money < 1 ){
						not_va_deduction_money = 0;
					}else{
						not_va_deduction_money = tools.fixNumByMode(not_float_va_deduction_money, vacation_count_mode);
					}
					
					//加總金額
					total_not_va_hours += not_va_hours;
					total_not_va_deduction_money += not_va_deduction_money;
					
				}else{
					//預設 - 使用系統假別資訊做判定處理
					if(this.vacation_type_map.containsKey(rs.getString("VA_TYPE"))){
						//有此一假別資訊
						ehf020100m0 = (EHF020100M0F)this.vacation_type_map.get(rs.getString("VA_TYPE"));
						if("01".equals(ehf020100m0.getEHF020100T0_03_TYPE())){
							//全薪
							//float_va_deduction_hour += ( rs.getFloat("HOURS") );
							va_hours += rs.getFloat("HOURS");
						}else if("02".equals(ehf020100m0.getEHF020100T0_03_TYPE())){
							//半薪
							float_va_deduction_hour += ( rs.getFloat("HOURS") * 0.5 );
							va_hours += rs.getFloat("HOURS");
						}else if("03".equals(ehf020100m0.getEHF020100T0_03_TYPE())){
							//無薪
							float_va_deduction_hour += ( rs.getFloat("HOURS") );
							va_hours += rs.getFloat("HOURS");
						}else if("04".equals(ehf020100m0.getEHF020100T0_03_TYPE())){
							//依比例設定
							float_va_deduction_hour += ( rs.getFloat("HOURS") * (Float.parseFloat(ehf020100m0.getEHF020100T0_03_VAL())) );
							va_hours += rs.getFloat("HOURS");
						}
						
						//修改請假扣薪內部計算邏輯, 將各假別的的扣薪金額獨立算出後再加總, 以便在個人薪資條顯示每個假別的扣薪金額
						//edit by joe 2012/10/08
						//計算請假有扣薪的總時數並計算扣薪金額
						float_va_deduction_money = this.countRealVaDeductionMoney(float_va_deduction_hour, work_hours, day_pay, hour_pay);
						
						//處理請假扣薪金額進位
						if( float_va_deduction_money < 1 ){
							va_deduction_money = 0;
						}else{
							va_deduction_money = tools.fixNumByMode(float_va_deduction_money, vacation_count_mode);
						}
						
						//加總金額
						total_va_hours += va_hours;
						total_va_deduction_money += va_deduction_money;
						
						//記錄請假資訊 -- edit by joe 2012/07/23
						vacation_information.append(rs.getString("VA_NAME"));
						vacation_information.append("(");
						vacation_information.append((((int)(rs.getFloat("HOURS")/work_hours))));  //Days
						vacation_information.append("天 ");
						vacation_information.append((rs.getFloat("HOURS")%work_hours));  //Hours
						vacation_information.append("小時");
						vacation_information.append(")");
						//若為扣薪假才顯示該假別的扣薪金額 edit by joe 2012/10/08
						if(float_va_deduction_hour > 0){
							vacation_information.append("(");
							vacation_information.append(va_deduction_money);
							vacation_information.append("元");
							vacation_information.append(")");
						}
						if(!rs.isLast()){
							vacation_information.append(",");
						}
						show_va_deduction_hour = true;
						
					}
				}
			}
			
			rs.close();
			stmt.close();
			
			vamap.put("MONEY", total_va_deduction_money);
			//vamap.put("HOURS", "請假時數:"+total_va_hours+"小時");
			//判斷是否有請假時數
			if(show_va_deduction_hour){
				vamap.put("HOURS", "請假:"+vacation_information.toString());  //請假資訊
			}else{
				vamap.put("HOURS", "");  //請假資訊
			}
			vamap.put("NOT_VA_MONEY", total_not_va_deduction_money);
			vamap.put("NOT_VA_HOURS", "非假扣除本薪的時數:"+total_not_va_hours+"小時");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return vamap;
	}
		
	
	/**
	 * 計算實際的請假扣薪金額
	 * @param float_va_deduction_hour
	 * @param work_hours
	 * @param day_pay
	 * @param hour_pay
	 * @return
	 */
	private float countRealVaDeductionMoney( float float_va_deduction_hour, float work_hours, int day_pay, int hour_pay ){
		
		float va_de_money = 0;
		
		try{
			int de_day = 0;
			float de_hour = 0;
			
			//實際 -> 計算請假有扣薪的總時數並計算扣薪金額
			if( float_va_deduction_hour > 0 ){
				//將實際請假時數轉換為天數, 要足數不足以時數處理
				de_day = (int)(float_va_deduction_hour / work_hours);
				//取得實際請假時數不足天數的時數
				de_hour = (float_va_deduction_hour % work_hours);
				
				//計算實際請假扣薪金額
				va_de_money = ( de_day * day_pay ) + ( de_hour * hour_pay );
				
			}else{
				//請假時數 <= 0, 表示沒請假不需要扣薪, 扣薪金額 = 0
				va_de_money = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return va_de_money;
	}
	
	/**
	 * 取得津貼金額
	 * @param conn
	 * @param EHF030600T0_U
	 * @param employee_id
	 * @return
	 */
	private int sumAllowanceMoney( Connection conn, String EHF030600T0_U, String employee_id ){
		
		int allowance_money = 0;
		
		try{
			String sql = "" +
			" SELECT EHF030601T0_U AS ALLOWANCE_UID, SUM(EHF030601T1_04+EHF030601T1_05) AS ALLOWANCE_MONEY " +
			" FROM EHF030601T1 " +
			" LEFT JOIN EHF030601T0 ON EHF030601T0_U = EHF030601T1_U AND EHF030601T0_07 = EHF030601T1_07 " +
			" WHERE 1=1 " +
			" AND EHF030601T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF030601T0_M = '"+this.sta_costyymm+"' " +  //入扣帳年月
			" AND EHF030601T0_M1 = '"+this.sta_salyymm+"' " +  //薪資年月
			" AND EHF030601T0_SCP IN ('01','02') " +  //薪資處理狀態
			" AND EHF030601T1_07 = '"+this.sta_comp_id+"' " +  //公司代碼
			" GROUP BY EHF030601T0_U ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				allowance_money += rs.getInt("ALLOWANCE_MONEY");
				//更改津貼計算薪資處理狀態
				this.chgAllownceStatus( conn, rs.getString("ALLOWANCE_UID"), "02", 
									    EHF030600T0_U, this.sta_user_name, this.sta_comp_id);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		return allowance_money;
	}
	
	/**
	 * 修改津貼資料狀態
	 * @param conn
	 * @param EHF030601T0_U
	 * @param status
	 * @param EHF030601T0_SCU
	 * @param user_name
	 * @param comp_id
	 */
	protected void chgAllownceStatus( Connection conn, String EHF030601T0_U, String status, String EHF030601T0_SCU, 
									  String user_name, String comp_id ){
		
		try{
			
			//更新津貼資料狀態
			String sql = "" +
			" UPDATE EHF030601T0 SET " +
			" EHF030601T0_SCP=?, EHF030601T0_SCU=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030601T0_U = '"+EHF030601T0_U+"' " +  //津貼明細UID
			" AND EHF030601T0_07 = '"+comp_id+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, status);  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, EHF030601T0_SCU);  //薪資計算UID
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改津貼資料狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 修改津貼資料的薪資處理狀態
	 * @param conn
	 * @param EHF030601T0_SCU
	 * @param status
	 * @param user_name
	 * @param comp_id
	 */
	public void chgAllownceStatus( Connection conn, String EHF030601T0_SCU, String status, String user_name, String comp_id ){
		
		String tmp_status = "";
		
		try{
			if("-1".equals(status)){
				tmp_status = "01";
			}else{
				tmp_status = status;
			}
			
			
			if("-1".equals(tmp_status)){
				//回復津貼資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030601T0 SET " +
				" EHF030601T0_SCP=?, EHF030601T0_SCU=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030601T0_SCU = '"+EHF030601T0_SCU+"' " +  //薪資計算UID
				" AND EHF030601T0_07 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, "");  //薪資計算UID
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}else{
				//更新津貼資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030601T0 SET " +
				" EHF030601T0_SCP=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030601T0_SCU = '"+EHF030601T0_SCU+"' " +  //薪資計算UID
				" AND EHF030601T0_07 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改津貼資料的薪資處理狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 取得加班費金額
	 * @param conn
	 * @param EHF030600T0_U
	 * @param employee_id
	 * @return
	 */
	private float sumOvertimeMoney( Connection conn, String EHF030600T0_U, String employee_id ,String type){
		
		float overtime_money = 0;
		
		try{
			String sql = "" +
			" SELECT EHF030602T0_U AS OVERTIME_UID, " ;
			if(type.equals("01")){//計算總金額
				sql+="SUM(EHF030602T1_04) AS OVERTIME_MONEY ";
				
			}
			else if(type.equals("02")){//計算總時數
				sql+="SUM(EHF030602T1_03) AS OVERTIME_MONEY ";
			}

			//"SUM(EHF030602T1_04) AS OVERTIME_MONEY " +
			sql+=" FROM EHF030602T1 " +
			" LEFT JOIN EHF030602T0 ON EHF030602T0_U = EHF030602T1_U AND EHF030602T0_07 = EHF030602T1_07 " +
			" WHERE 1=1 " +
			" AND EHF030602T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF030602T0_M = '"+this.sta_costyymm+"' " +  //入扣帳年月
			" AND EHF030602T0_M1 = '"+this.sta_salyymm+"' " +  //薪資年月
			" AND EHF030602T0_SCP IN ('01','02') " +  //薪資處理狀態
			" AND EHF030602T1_07 = '"+this.sta_comp_id+"' " +  //公司代碼
			" GROUP BY EHF030602T0_U ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				overtime_money += rs.getInt("OVERTIME_MONEY");
				//更改加班費計算薪資處理狀態
				this.chgOvertimeStatus( conn, rs.getString("OVERTIME_UID"), "02", 
									    EHF030600T0_U, this.sta_user_name, this.sta_comp_id);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		return overtime_money;
	}
	
	/**
	 * 修改加班費資料狀態
	 * @param conn
	 * @param EHF030601T0_U
	 * @param status
	 * @param EHF030601T0_SCU
	 * @param user_name
	 * @param comp_id
	 */
	protected void chgOvertimeStatus( Connection conn, String EHF030602T0_U, String status, String EHF030602T0_SCU, String user_name, String comp_id ){
		
		try{
			
			//更新加班費資料狀態
			String sql = "" +
			" UPDATE EHF030602T0 SET " +
			" EHF030602T0_SCP=?, EHF030602T0_SCU=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030602T0_U = '"+EHF030602T0_U+"' " +  //加班費明細UID
			" AND EHF030602T0_07 = '"+comp_id+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, status);  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, EHF030602T0_SCU);  //薪資計算UID
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改加班費資料狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
	}
	
	/**
	 * 修改加班費的薪資處理狀態
	 * @param conn
	 * @param EHF030602T0_SCU
	 * @param status
	 * @param user_name
	 * @param comp_id
	 */
	public void chgOvertimeStatus( Connection conn, String EHF030602T0_SCU, String status, String user_name, String comp_id ){
		
		String tmp_status = "";
		
		try{
			
			if("-1".equals(status)){
				tmp_status = "01";
			}else{
				tmp_status = status;
			}
			
			if("-1".equals(status)){
				//回復加班費資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030602T0 SET " +
				" EHF030602T0_SCP=?, EHF030602T0_SCU=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030602T0_SCU = '"+EHF030602T0_SCU+"' " +  //薪資計算UID
				" AND EHF030602T0_07 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, "");  //薪資計算UID
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}else{
				//更新加班費資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030602T0 SET " +
				" EHF030602T0_SCP=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030602T0_SCU = '"+EHF030602T0_SCU+"' " +  //薪資計算UID
				" AND EHF030602T0_07 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改加班費資料的薪資處理狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
	}
	
	protected Map getHaOvertimeDataForMerge( Connection conn, String employee_id, String salYYMM, String costYYMM, String comp_id ){
		
		Map return_map = new HashMap();
		
		try{
			
			String sql = "" +
			 " SELECT COUNT(*) AS COUNTNUM, SUM(EHF030603T1_03) AS HOURS, SUM(EHF030603T1_04) AS MONEY " +
			 " FROM EHF030603T0 " +
			 " LEFT JOIN EHF030603T1 ON EHF030603T1_U = EHF030603T0_U AND EHF030603T1_07 = EHF030603T0_07 " +
			 " WHERE 1=1 " +
			 " AND EHF030603T0_01 = '"+employee_id+"' " +  //員工工號
			 " AND EHF030603T0_M = '"+costYYMM+"' " +  //入扣帳年月
			 " AND EHF030603T0_M1 = '"+salYYMM+"' " +  //薪資年月
			 " AND EHF030603T0_07 = '"+comp_id+"' ";  //公司代碼

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//不休假加班筆數
				return_map.put("COUNT", rs.getInt("COUNTNUM"));
				//不休假加班時數
				return_map.put("HOURS", rs.getFloat("HOURS"));
				//不休假加班本薪金額
				return_map.put("MONEY", rs.getInt("MONEY"));
			}else{
				//不休假加班筆數
				return_map.put("COUNT", 0);
				//不休假加班時數
				return_map.put("HOURS", (float)0 );
				//不休假加班本薪金額
				return_map.put("MONEY", 0);
			}
			
			rs.close();
			stmt.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 取得不休假加班費金額
	 * @param conn
	 * @param EHF030600T0_U
	 * @param employee_id
	 * @return
	 */
	private int sumHaOvertimeMoney( Connection conn, String EHF030600T0_U, String employee_id ){
		
		int ha_overtime_money = 0;
		
		try{
			String sql = "" +
			" SELECT EHF030603T0_U AS HA_OVERTIME_UID, SUM(EHF030603T1_04) AS HA_OVERTIME_MONEY " +
			" FROM EHF030603T1 " +
			" LEFT JOIN EHF030603T0 ON EHF030603T0_U = EHF030603T1_U AND EHF030603T0_07 = EHF030603T1_07 " +
			" WHERE 1=1 " +
			" AND EHF030603T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF030603T0_M = '"+this.sta_costyymm+"' " +  //入扣帳年月
			" AND EHF030603T0_M1 = '"+this.sta_salyymm+"' " +  //薪資年月
			" AND EHF030603T0_SCP IN ('01','02') " +  //薪資處理狀態
			" AND EHF030603T1_07 = '"+this.sta_comp_id+"' " +  //公司代碼
			" GROUP BY EHF030603T0_U ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			//if(employee_id.equals("023")){
			//	int i=0;
			//}
			
			
			while(rs.next()){
				ha_overtime_money += rs.getInt("HA_OVERTIME_MONEY");
				//更改不休假加班費計算薪資處理狀態
				this.chgHaOvertimeStatus( conn, rs.getString("HA_OVERTIME_UID"), "02", 
									    EHF030600T0_U, this.sta_user_name, this.sta_comp_id);
			}
			
			rs.close();
			stmt.close();
			
			//判斷是否需要回傳
			//處理不休假加班的計算資料型態為'MERGE'時,需將不休假加班費的當日本薪併入本薪金額中
			String ha_ov_type = tools.getSysParam(conn, this.sta_comp_id, "HALIDAY_OVERTIME_TYPE");
			
			//判斷不休假加班的計算資料型態
			if("MERGE".equals(ha_ov_type)){
				//不休假加班計算資料合併至津貼,加班費,本薪金額
				ha_overtime_money = 0;
			}
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		return ha_overtime_money;
	}
	
	/**
	 * 修改不休假加班費資料狀態
	 * @param conn
	 * @param EHF030601T0_U
	 * @param status
	 * @param EHF030601T0_SCU
	 * @param user_name
	 * @param comp_id
	 */
	protected void chgHaOvertimeStatus( Connection conn, String EHF030603T0_U, String status, String EHF030603T0_SCU, String user_name, String comp_id ){
		
		try{
			
			//更新不休假加班費資料狀態
			String sql = "" +
			" UPDATE EHF030603T0 SET " +
			" EHF030603T0_SCP=?, EHF030603T0_SCU=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030603T0_U = '"+EHF030603T0_U+"' " +  //不休假加班費明細UID
			" AND EHF030603T0_07 = '"+comp_id+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, status);  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, EHF030603T0_SCU);  //薪資計算UID
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改不休假加班費資料狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 修改不休假加班費的薪資處理狀態
	 * @param conn
	 * @param EHF030603T0_SCU
	 * @param status
	 * @param user_name
	 * @param comp_id
	 */
	public void chgHaOvertimeStatus( Connection conn, String EHF030603T0_SCU, String status,String user_name, String comp_id ){
		
		String tmp_status = "";
		
		try{
			
			if("-1".equals(status)){
				tmp_status = "01";
			}else{
				tmp_status = status;
			}
			
			if("-1".equals(status)){
				//回復不休假加班費資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030603T0 SET " +
				" EHF030603T0_SCP=?, EHF030603T0_SCU=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030603T0_SCU = '"+EHF030603T0_SCU+"' " +  //薪資計算UID
				" AND EHF030603T0_07 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, "");  //薪資計算UID
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}else{
				//更新不休假加班費資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030603T0 SET " +
				" EHF030603T0_SCP=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030603T0_SCU = '"+EHF030603T0_SCU+"' " +  //薪資計算UID
				" AND EHF030603T0_07 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改不休假加班費資料的薪資處理狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 依據放扣款類別(fee_type)取得其他費用金額
	 * @param conn
	 * @param employee_id
	 * @param fee_type
	 * @param EHF030600T0_U
	 * @return
	 */
	private int sumOtherFee( Connection conn, String employee_id, String fee_type, String EHF030600T0_U ){
		
		int other_fee = 0;
		
		try{
			String sql = "" +
			" SELECT EHF030500T0_U AS OTHERFEE_UID, " +
			" CASE EHF030500T0_06 WHEN '01' THEN EHF030500T0_09 WHEN '02' THEN EHF030500T0_11 END AS FEE_MONEY " +
			" FROM EHF030500T0 " +
			" WHERE 1=1 " +
			" AND EHF030500T0_02 = '"+employee_id+"' " +  //員工工號
			" AND EHF030500T0_05 = '"+this.sta_salyymm+"' " +  //薪資年月
			//" AND EHF030500T0_07 = '"+ehf030600m0.getEHF030600T0_04()+"' " +  //發放類別
			" AND EHF030500T0_06 = '"+fee_type+"' " +  //放扣款類別
			" AND EHF030500T0_SCP IN ('01','02') " +  //薪資處理狀態
			" AND EHF030500T0_15 = '"+this.sta_comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				other_fee += rs.getInt("FEE_MONEY");  //其他費用
				//更改其他費用計算薪資處理狀態
				this.chgOtherFeeStatus( conn, rs.getString("OTHERFEE_UID"), "02", 
									    EHF030600T0_U, this.sta_user_name, this.sta_comp_id);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return other_fee;
	}
	
	/**
	 * 修改其他費用資料狀態
	 * @param conn
	 * @param EHF030500T0_U
	 * @param status
	 * @param EHF030500T0_SCU
	 * @param user_name
	 * @param comp_id
	 */
	protected void chgOtherFeeStatus( Connection conn, String EHF030500T0_U, String status, String EHF030500T0_SCU, String user_name, String comp_id ){
		
		try{
			
			//更新其他費用資料狀態
			String sql = "" +
			" UPDATE EHF030500T0 SET " +
			" EHF030500T0_SCP=?, EHF030500T0_SCU=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030500T0_U = '"+EHF030500T0_U+"' " +  //其他費用UID
			" AND EHF030500T0_15 = '"+comp_id+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, status);  //薪資處理狀態
			indexid++;
			p6stmt.setString(indexid, EHF030500T0_SCU);  //薪資計算UID
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改其他費用資料狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 修改其他費用的薪資處理狀態
	 * @param conn
	 * @param EHF030500T0_SCU
	 * @param status
	 * @param user_name
	 * @param comp_id
	 */
	public void chgOtherFeeStatus( Connection conn, String EHF030500T0_SCU, String status, String user_name, String comp_id ){
		
		String tmp_status = "";
		
		try{
			
			if("-1".equals(status)){
				tmp_status = "01";
			}else{
				tmp_status = status;
			}
			
			if("-1".equals(status)){
				//回復其他費用資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030500T0 SET " +
				" EHF030500T0_SCP=?, EHF030500T0_SCU=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030500T0_SCU = '"+EHF030500T0_SCU+"' " +  //薪資計算UID
				" AND EHF030500T0_15 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, "");  //薪資計算UID
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}else{
				//更新其他費用資料的薪資處理狀態
				String sql = "" +
				" UPDATE EHF030500T0 SET " +
				" EHF030500T0_SCP=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030500T0_SCU = '"+EHF030500T0_SCU+"' " +  //薪資計算UID
				" AND EHF030500T0_15 = '"+comp_id+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, tmp_status);  //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, user_name);  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("修改其他費用資料的薪資處理狀態時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 取得員工薪資所得代扣金額
	 * @param conn
	 * @param employee_id
	 * @param ehf030200m0
	 * @param pay  應發金額
	 * @param comp_id
	 * @return
	 */
	protected int getSalaryAgencyDeduction( Connection conn, String employee_id, EHF030200M0F ehf030200m0, int pay, String comp_id ){
		
		int agency_deduction = 0;
		
		try{
			String sql = "" +
			" SELECT EHF030105T0_04 AS SALARY, EHF030105T0_05 AS AGENCY_DEDUCTION " +
			" FROM EHF030105T1 " +
			" LEFT JOIN EHF030105T0 ON EHF030105T0_01 = EHF030105T1_01 AND EHF030105T0_07 = EHF030105T1_05 " +
			" WHERE 1=1 " +
			" AND EHF030105T1_02 = '"+employee_id+"' " +  //員工工號
			" AND EHF030105T0_02 = '"+(Integer.parseInt((this.sta_salyymm.split("/"))[0])-1911)+"' " +  //年度
			" AND EHF030105T1_05 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				agency_deduction = rs.getInt("AGENCY_DEDUCTION");
			}
			rs.close();
			stmt.close();
			
			//處理薪資主檔的所得稅代扣
			if("true".equals(ehf030200m0.getEHF030200T0_14())){
				//表示有設定代扣
				int ad2 = 0;  //代扣金額
				if("01".equals(ehf030200m0.getEHF030200T0_14_TYPE())){
					//使用代扣率
					String agency_deduction_mode = tools.getSysParam(conn, comp_id, "AGENCY_DEDUCTION_MODE");  //取得進位模式
					ad2 = tools.fixNumByMode((pay * (ehf030200m0.getEHF030200T0_15()/100)), agency_deduction_mode);
				}else if("02".equals(ehf030200m0.getEHF030200T0_14_TYPE())){
					//使用代扣金額
					ad2 = ehf030200m0.getEHF030200T0_16();
				}
				
				//加上原本的代扣金額
				agency_deduction = agency_deduction + ad2;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return agency_deduction;
	}
	
	/**
	 * 計算勞保費自付額
	 * @param ehf030600m2
	 * @return
	 */
	protected int getLaborFee( Connection conn, EHF030600M2F ehf030600m2, Map breakMonthMap ){
		
		int labor_fee = 0;
		
		try{
			//計算勞保費用
			//判斷是否破月, 若破月該月則先不計算勞保費用
			//破月照常計算勞保費
			
			//計算勞保投保天數
			int insured_day = 0;
			if(!(Boolean)breakMonthMap.get("BREAK_MONTH_FLAG")){
				//若沒有破月則當作整月投保 => 投保30天
				insured_day = 30;
			}else{
				insured_day = (Integer)breakMonthMap.get("JOB_DAY");
			}
			
			if(true){
				//未破月 --> 在職天數應算 30 天
				//取得勞保自付額
				//labor_fee = ehf030600m2.getEHF030600T2_15();
				//使用保險計算元件來進行員工勞保費用計算
				labor_fee = this.ems_insurance_tools.countEmpLaborFee(conn, 
																	  ehf030600m2.getEHF030600T2_02(), 
																	  ehf030600m2.getEHF030600T2_INS_TYPE(),
														              ehf030600m2.getEHF030600T2_14(), 
														              insured_day);
				
			}else{
				//破月
				labor_fee = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("取得勞保費用自付額時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return labor_fee;
	}
	
	/**
	 * 計算勞保公司補助額
	 * @param ehf030600m2
	 * @return
	 */
	protected int getLaborCompSubsidy( Connection conn, EHF030600M2F ehf030600m2, Map breakMonthMap ){
		
		int labor_comp_subsidy = 0;
		
		try{
			//計算勞保公司補助額
			//判斷是否破月, 若破月該月則先不計算勞保費用
			//破月照常計算勞保費
			
			//計算勞保投保天數
			int insured_day = 0;
			if(!(Boolean)breakMonthMap.get("BREAK_MONTH_FLAG")){
				//若沒有破月則當作整月投保 => 投保30天
				insured_day = 30;
			}else{
				insured_day = (Integer)breakMonthMap.get("JOB_DAY");
			}
			
			if(true){
				//未破月 --> 在職天數應算 30 天
				//取得勞保公司補助額
				//labor_comp_subsidy = ehf030600m2.getEHF030600T2_16();
				
				//勞保公司補助額需多計算職災費用, 會依據不同行業別會有不同的職災費率,
				//將職災費率作為系統參數, 職災費用 = 勞保投保額 * 職災費率 edit by joe 2012/01/04
				//labor_comp_subsidy += this.ems_tools.fixNumByMode( ehf030600m2.getEHF030600T2_14() * ehf030600m2.getEHF030600T2_31(), "HALF_UP");
				
				//使用保險計算元件來進行投保單位勞保費用計算
				labor_comp_subsidy = this.ems_insurance_tools.countCompLaborFee(conn, 
																				ehf030600m2.getEHF030600T2_02(), 
																	            ehf030600m2.getEHF030600T2_INS_TYPE(),
														   					    ehf030600m2.getEHF030600T2_14(), 
														   					    insured_day);
				
			}else{
				//破月
				labor_comp_subsidy = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("取得勞保費用公司補助額時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return labor_comp_subsidy;
	}
	
	/**
	 * 計算健保費自付額
	 * @param ehf030600m2
	 * @return
	 */
	protected int getHealthFee( Connection conn, EHF030600M2F ehf030600m2, Map breakMonthMap ){
		
		int health_fee = 0;
		
		try{
			//計算健保費用
			//判斷是否破月, 若破月該月則先不計算健保費用
			//破月照常計算健保費
			//!(Boolean)breakMonthMap.get("BREAK_MONTH_FLAG")
			if(true){
				//未破月
				//取得健保自付額
				//health_fee = (ehf030600m2.getEHF030600T2_19() * ehf030600m2.getEHF030600T2_21() ) - (ehf030600m2.getEHF030600T2_22());
				
				//使用保險計算元件來進行員工健保費用計算
				health_fee = this.ems_insurance_tools.countEmpHealthFee( conn, ehf030600m2.getEHF030600T2_02(), 
																			   ehf030600m2.getEHF030600T2_INS_TYPE(),
																		       ehf030600m2.getEHF030600T2_18(), 
																		       (ehf030600m2.getEHF030600T2_21() - 1), 
																		       ehf030600m2.getEHF030600T2_22());
				
			}else{
				//破月
				health_fee = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("取得健保費用自付額時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return health_fee;
	}
	
	/**
	 * 計算健保公司補助額
	 * @param ehf030600m2
	 * @return
	 */
	protected int getHealthCompSubsidy( Connection conn, EHF030600M2F ehf030600m2, Map breakMonthMap ){
		
		int health_comp_subsidy = 0;
		
		try{
			//計算健保公司補助額
			//判斷是否破月, 若破月該月則先不計算健保費用
			//破月照常計算健保費
			//!(Boolean)breakMonthMap.get("BREAK_MONTH_FLAG")
			if(true){
				//未破月
				//取得健保公司補助額
				//health_comp_subsidy = (ehf030600m2.getEHF030600T2_20());
				
				//使用保險計算元件來進行投保單位健保費用計算
				health_comp_subsidy = this.ems_insurance_tools.countCompHealthFee( conn, ehf030600m2.getEHF030600T2_02(), ehf030600m2.getEHF030600T2_INS_TYPE(),
															 					   ehf030600m2.getEHF030600T2_14() );
				
			}else{
				//破月
				health_comp_subsidy = 0;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("取得健保費用公司補助額時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return health_comp_subsidy;
	}
	
	/**
	 * 取得福利金
	 * @param conn
	 * @param basic_salary
	 * @param comp_id
	 * @return
	 */
	protected int getWelFare( Connection conn, int clean_basic_salary, int basic_salary, String comp_id ){
		
//		BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		int welfare_fee = 0;
		
		try{
			//取得福利金的計算型態
			String welfare_type = tools.getSysParam(conn, comp_id, "WELFARE_TYPE");
			//取得福利金的固定金額或比率
			float welfare_num = (Float.parseFloat(tools.getSysParam(conn, comp_id, "WELFARE_NUM")));
			
			if("RATE".equals(welfare_type)){
				//以基本薪資比率取得福利金
				welfare_fee = Math.round(clean_basic_salary * welfare_num);  //四捨五入
			}else if("FIXED".equals(welfare_type)){
				//以固定金額取得福利金
				welfare_fee = Math.round(welfare_num);  //四捨五入
			}else{
				//預設為比率
				welfare_fee = Math.round(clean_basic_salary * welfare_num);  //四捨五入
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return welfare_fee;
	}
	
	/**
	 * 修改薪資計算相關資料的薪資處理狀態
	 * @param conn
	 * @param EHF030600T0_U
	 * @param status
	 * @param user_name
	 * @param comp_id
	 */
	public void chgAllSalaryStatus( Connection conn, String EHF030600T0_U, String status, String user_name, String comp_id ){
		
		try{
			//更改津貼資料的薪資處理狀態
			this.chgAllownceStatus(conn, EHF030600T0_U, status, user_name, comp_id);
			
			//更改加班費資料的薪資處理狀態
			this.chgOvertimeStatus(conn, EHF030600T0_U, status, user_name, comp_id);
			
			//更改不休假加班費的薪資處理狀態
			this.chgHaOvertimeStatus(conn, EHF030600T0_U, status, user_name, comp_id);
			
			//更改其他費用的薪資處理狀態
			this.chgOtherFeeStatus(conn, EHF030600T0_U, status, user_name, comp_id);
			
			//更改薪資計算明細資料的薪資處理狀態
			this.chgSalCntDetailStatus(conn, EHF030600T0_U, status, user_name, comp_id);
			
			//更改薪資控制檔的薪資處理狀態
			this.chgSalCntStatus(conn, EHF030600T0_U, status, user_name, comp_id);
			
			//修改EHF020900T0(考勤確認)狀態
			this.chgAttSalCntStatus(conn, EHF030600T0_U, status, user_name, comp_id);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private EHF030600M1F cntRealBonus( Connection conn, String EHF030600T0_U,EHF030600M2F ehf030600m2, EHF030600M1F ehf030600m1, String comp_id  ){
		
		String status = "2"; //表示要計算
		HR_TOOLS hr_tools = new HR_TOOLS();
		try{
			
			ehf030600m1.setUSER_UPDATE(ehf030600m2.getUSER_UPDATE());  //最後異動人員
			
			//取得EMS-Flow員工基本資料Map
			Map empInfMap = hr_tools.getEmpInfMapByEmpId(this.sta_user_id, ehf030600m1.getEHF030600T1_02(), comp_id);
			
			//取得員工薪資主檔資料
			EHF030200M0F ehf030200m0 = this.getEmpSalFile(conn, ehf030600m1.getEHF030600T1_02(), comp_id);
			
			//判斷薪資是否有破月情況
			Map breakMonthMap = this.chkBreakMonth(empInfMap);
			
			//計算本薪金額
			try{
				//計算當月員工實際的基本薪資金額 
				//若員工 15日到職 基本薪資只計算  15日 至 當月月底 所應領的基本薪資
				//若員工 15日離職 基本薪資只計算  當月 1 日 至 15 日 所應領的基本薪資
				//取得本薪金額
				Map basic_map = this.getBasicSalary( conn, breakMonthMap, ehf030200m0 );
				ehf030600m1.setEHF030600T1_04((Integer)basic_map.get("BASIC_MONEY"));
				//本薪計算記錄
				ehf030600m1.setEHF030600T1_04_DESC((String)basic_map.get("BASIC_DESC"));
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算本薪金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
									"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			ehf030600m1.setEHF030600T1_04_O(0);  //薪資項目
			ehf030600m1.setEHF030600T1_041(0);  //請假扣薪
			ehf030600m1.setEHF030600T1_09(0);  //津貼
			ehf030600m1.setEHF030600T1_10(0);  //加班費
			ehf030600m1.setEHF030600T1_12(0);  //不休假加班費
			ehf030600m1.setEHF030600T1_11(0);  //差旅費
			
			//計算其他費用
			try{
				//計算當月的其他費用 "補款" 總金額
				ehf030600m1.setEHF030600T1_05(this.sumOtherFee(conn, ehf030600m1.getEHF030600T1_02(), "01", EHF030600T0_U));
				
				//計算當月的其他費用 "扣款" 總金額
				ehf030600m1.setEHF030600T1_05_D(this.sumOtherFee(conn, ehf030600m1.getEHF030600T1_02(), "02", EHF030600T0_U));
				
			}catch(Exception e){
				e.printStackTrace();
				throw new Exception("計算其他費用總金額發生錯誤!!員工工號:{" + ehf030600m1.getEHF030600T1_02() + "}。 " +
						"薪資年月[" + ehf030600m1.getEHF030600T1_M1() + "]"+ e.toString());
			}
			
			ehf030600m1.setEHF030600T1_06(0);  //薪資所得代扣
			ehf030600m1.setEHF030600T1_15(0);  //勞保費
			ehf030600m1.setEHF030600T1_16(0);  //勞保公司補助額
			ehf030600m1.setEHF030600T1_13(0);  //健保費
			ehf030600m1.setEHF030600T1_14(0);  //健保公司補助額
			ehf030600m1.setEHF030600T1_17(0);  //勞退費
			ehf030600m1.setEHF030600T1_18(0);  //勞退公司提撥6%
			ehf030600m1.setEHF030600T1_19(0);  //福利金
			
			
			//薪資計算明細資料的 -->  應發金額,  應扣金額,  實發金額
			//應發金額
			ehf030600m1.setEHF030600T1_071(
			ehf030600m1.getEHF030600T1_04() +  //本薪金額
			ehf030600m1.getEHF030600T1_04_O() +  //薪資項目總金額
			ehf030600m1.getEHF030600T1_05() +  //其他費用補款金額
			ehf030600m1.getEHF030600T1_09() +  //津貼費用
			ehf030600m1.getEHF030600T1_10() +  //加班費
			ehf030600m1.getEHF030600T1_11() +  //差旅費
			ehf030600m1.getEHF030600T1_12()  //不休假加班費
			);
			
			//應扣金額
			ehf030600m1.setEHF030600T1_072(
			ehf030600m1.getEHF030600T1_041() +  //請假扣薪金額
			ehf030600m1.getEHF030600T1_05_D() +  //其他費用扣款金額
			ehf030600m1.getEHF030600T1_06() +  //薪資所得扣款金額
			ehf030600m1.getEHF030600T1_13() +  //健保費
			ehf030600m1.getEHF030600T1_15() +  //勞保費
			ehf030600m1.getEHF030600T1_17() +  //勞退費
			ehf030600m1.getEHF030600T1_19()  //福利金
			);
			
			//實發金額 = 應發金額 - 應扣金額
			//實發金額
			ehf030600m1.setEHF030600T1_07(ehf030600m1.getEHF030600T1_071() - ehf030600m1.getEHF030600T1_072());
			
			//薪資計算明細資料的相關設定值
			ehf030600m1.setEHF030600T1_SCP("02");  //薪資處理狀態
			ehf030600m1.setEHF030600T1_SCU(EHF030600T0_U);  //薪資計算UID
			hr_tools.close();
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("執行 cntRealBonus() 時發生錯誤,員工工號:{" +ehf030600m2.getEHF030600T2_02()+ "} " + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}		
		
		return ehf030600m1;
	}
	
	
	
	/**
	 * 新增假別時數EHF020411T0
	 * 紀錄時間、請假單、假別設定的資料彙總到EHF020411T0
	 * @param conn
	 * @param vacationTimeList
	 * @param compId 
	 */
	public void createleaveData( Connection conn, List<Map<String, String>> vacationTimeList, String compId ){
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		
		try{
			
			sql = "" +
			" INSERT INTO EHF020411T0 ( EHF020411T0_01, EHF020411T0_M, EHF020411T0_M1, EHF020411T0_02,EHF020411T0_03, " +
			" EHF020411T0_04, EHF020411T0_05, EHF020411T0_06, EHF020411T0_07,EHF020411T0_08, EHF020411T0_SFK," +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?, NOW(), NOW() ) "  ;


			for(int i =0 ;i<vacationTimeList.size();i++){
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				String UID = tools.createEMSUID(conn, "VT", "EHF020411T0", "EHF020411T0_01", compId);
				Map Data = vacationTimeList.get(i);
				
				//找尋資料庫是否有相同資料
				if(!  ("true".equals(this.chackEHF020411T0(conn,Data,compId,1)))  ){
					//沒有相同資料  新增資料
					p6stmt.setString(indexid, UID); // EHF020411T0_01 請假彙總資料序號
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_M")); // 入扣帳年月	EHF020411T0_M
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_M1")); // 薪資年月	EHF020411T0_M1
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_02")); // 部門組織(代號)	EHF020411T0_02
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_03")); // 員工工號	EHF020411T0_03
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_04")); // 假別代碼	EHF020411T0_04
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_05")); // 請假總天數(請假天數/時數)	EHF020411T0_05
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_06")); // 假別薪資設定	EHF020411T0_06
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_07")); // 假別薪資設定比例 EHF020411T0_07
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_08")); // 公司代碼	EHF020411T0_08
					indexid++;
					p6stmt.setString(indexid, (String) Data.get("EHF020411T0_SFK")); // 薪資計算UID	EHF020411T0_SFK
					indexid++;
					p6stmt.setString(indexid, "SYSTEM"); // 建立人員	USER_CREATE
					indexid++;
					p6stmt.setString(indexid, "SYSTEM"); // 修改人員	USER_UPDATE
					indexid++;
					p6stmt.setInt(indexid, 1); // 版本
					indexid++;

					p6stmt.executeUpdate();
					pstmt.close();
					p6stmt.close();
					conn.commit();
				}
			
			}
			//System.out.println("sql ==>"+1)	
		}catch(Exception e){
			e.printStackTrace();
			try {
				throw new Exception("createleaveData() 新增請假時數彙總資料時，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}


	/**
	 * 	* 找尋EHF020411T0是否有相同資料
	 * @param conn
	 * @param data
	 * @param compId
	 * @param type  1.尋找有無資料  回傳false OR true   2.回傳找到的時數
	 * @return
	 */
	private String chackEHF020411T0(Connection conn,Map data, String compId,int type) {
		String chk="false";
		float vacation_hours = 0;
		// TODO Auto-generated method stub
		try{
			
		String SQL= "SELECT * FROM EHF020411T0" +
				" WHERE   1 = 1" +
				" AND EHF020411T0_M1 = '"+(String) data.get("EHF020411T0_M1")+"'" +
				" AND EHF020411T0_03 = '"+(String) data.get("EHF020411T0_03")+"'" ;
				if(type==1){
					SQL+=" AND EHF020411T0_04 = '"+(String) data.get("EHF020411T0_04")+"'" ;
				}
				SQL+=" AND EHF020411T0_08 = '"+(String) data.get("EHF020411T0_08")+"'" ;
			
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(SQL);
		switch(type){
		case 1:
			if(rs.next()){
				this.UPDATE_EHF020411T0(conn, data,compId);
				chk="true";	
			}
			else
				chk="false";

			break;
		case 2:
			while(rs.next()){

				vacation_hours += Float.parseFloat((String)rs.getString("EHF020411T0_05"));  //總共請假時數
			}
			//回傳小時
			chk=String.valueOf(vacation_hours);
			break;
		
		}
		rs.close();
		stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return chk;
	}
	
	/**
	 * UPDATE_EHF020411T0
	 * @param conn
	 * @param data
	 * @param compId
	 */
	private void UPDATE_EHF020411T0(Connection conn, Map data, String comp_id) {
		// TODO Auto-generated method stub
		BaseFunction base_tools = new BaseFunction(comp_id);
		try{

			String sql = ""
				+ " UPDATE EHF020411T0 SET"
				+ " EHF020411T0_05=? ,DATE_UPDATE=NOW(),VERSION=VERSION+1" 
				+ " WHERE 1=1 " 
				+" AND EHF020411T0_M1 = '"+(String) data.get("EHF020411T0_M1")+"'" +
				" AND EHF020411T0_03 = '"+(String) data.get("EHF020411T0_03")+"'" +
				" AND EHF020411T0_04 = '"+(String) data.get("EHF020411T0_04")+"'" +
				" AND EHF020411T0_08 = '"+(String) data.get("EHF020411T0_08")+"'" ;
			
			// 執行SQL
			PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			p6stmt.setString(indexid,  (String)data.get("EHF020411T0_05")); //EHF020900T0_03
			indexid++;

			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			base_tools.commit();
			base_tools.close();
		} catch (Exception e) {
			System.out.println("更新EHF020411T0錯誤!! ");
			e.printStackTrace();
		}
	}	
	
	
	/**
	 * 取得加班費金額與時數
	 * 20131007新增  BY 賴泓錡
	 * @param conn
	 * @param ehf030600t1M1  薪資年月
	 * @param ehf030200m0
	 * @param ehf030600t1_02員工工號
	 * @param compId 公司代碼
	 * @return
	 */
	private Map getOverTimeMa(Connection conn, String ehf030600t1M1,EHF030200M0F ehf030200m0, EHF030600M1F ehf030600m1, String comp_id) {
		// TODO Auto-generated method stub
		Map OverTimeMap =new HashMap();
		String month_start_date = "";
		String month_end_date = "";
		float nmoney_float=0;
		float hmoney_float=0;
		int hmoney_int =0;
		int nmoney_int =0;
		float time=0;
		try {
		
			//取得加班費計算時的進位模式
			String overtime_pay_mode = tools.getSysParam(conn, comp_id, "OVERTIME_PAY_MODE");
			
			// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
			month_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(ehf030600t1M1+ "/01")));
		
			// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
			month_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(ehf030600t1M1+ "/01")));

			EMS_OvertimeAttendanceHandle ems_oah = new EMS_OvertimeAttendanceHandle();  //員工加班資訊元件
			List<Map<String, String>> printlist = ems_oah.queryOvertimeCollectHours( conn,
					ehf030600m1.getEHF030600T1_03(), ehf030600m1.getEHF030600T1_02(),//部門代號，員工工號
					month_start_date, month_end_date , //考勤異常第一天 ，考勤異常最後一天
					"01",comp_id);//01=換錢
		
		if(printlist.size()!=0){
			Map TimeMap=printlist.get(0);
			//取得員工的時薪
			int hour_pay = this.getEmpHourPay(conn, 1, (String) TimeMap.get("員工工號"), this.sta_costyymm, this.sta_salyymm, this.sta_comp_id);
		
			if ("啟用".equals((String) TimeMap.get("是否啟用") ) ) {
				if ((String) TimeMap.get("前段加班時數") != null&& (String) TimeMap.get("後段加班時數") != null) {
					nmoney_float = Float.valueOf((String) TimeMap.get("前段加班時數")) * Float.parseFloat((String) TimeMap.get("前段費率"))* hour_pay
						  + Float.valueOf((String) TimeMap.get("後段加班時數")) * Float.parseFloat((String) TimeMap.get("後段費率"))* hour_pay;
					
					time=Float.parseFloat((String)TimeMap.get("前段加班時數"))+Float.parseFloat((String)TimeMap.get("後段加班時數"));
					nmoney_int=tools.fixNumByMode(nmoney_float, overtime_pay_mode);
				}
				
			}
			else{
				System.out.println("加班費代號"+(String) TimeMap.get("加班費代號")+"未啟用  請確認!!");
			}
			
			if ((String) TimeMap.get("假日加班時數") != null) {
				hmoney_float=Integer.valueOf((String) TimeMap.get("假日加班時數")) * Float.parseFloat((String) TimeMap.get("假日加班費率"))*hour_pay;
				
				hmoney_int=tools.fixNumByMode(hmoney_float, overtime_pay_mode);
			}
		}
		OverTimeMap.put("NMONEY", 	nmoney_int);
		OverTimeMap.put("NTIME", 	time+"");
		OverTimeMap.put("HMONEY", 	hmoney_int);
		
		
		
		
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return OverTimeMap;
	}

	/**
	 * 計算全勤獎金總扣除金額
	 * @param conn
	 * @param salYYMM
	 * @param ehf030200m0
	 * @param ehf030600m1
	 * @param comp_id
	 * @return
	 */
	private Map getAttBonusMap(Connection conn, String salYYMM,EHF030200M0F ehf030200m0, EHF030600M1F ehf030600m1, String comp_id) {
		// TODO Auto-generated method stub
		Map return_MAP = new LinkedHashMap<String, String>();
		int late_money=0;//遲到早退扣除金額
		int lack_money=0;//曠職扣除金額
		int Ask_for_leave_money=0;//請假扣除金額
		
		int total_Net_Money=0;//總扣除金額
		int money=ehf030200m0.getEHF030200T0_19();//系統設定的全勤獎金金額
		
		//一星期的開始參數設定
		String monthstart = tools.getSysParam(conn, comp_id, "MONTHSTART");
		// 取得全勤獎金設定明細
		Map EHF030108T0=this.getEHF030108T0(conn,ehf030200m0.getEHF030200T0_20(),comp_id);
		if(!EHF030108T0.isEmpty()){
		// 取得計算日期區間    
		Map AttDateMap=this.getAttDateMap(ehf030600m1.getEHF030600T1_M1(),(String)EHF030108T0.get("EHF030108T0_04"),(String)EHF030108T0.get("EHF030108T0_08"),monthstart);
		String startdate="";
		String enddate="";
		
		
		if(((String) EHF030108T0.get("EHF030108T0_12")).equals("1")){	
			if (((String) EHF030108T0.get("EHF030108T0_04")).equals("05")){// 遲到早退頻率單位   天   新增於20111112   BY賴泓錡
				// 星期處理變數
				String month_start_date = "";
				String month_end_date = "";
				Calendar month_start_cal = null;
				Calendar month_end_cal = null;
				try {
					// 處理星期資料
					// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
					month_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(ehf030600m1.getEHF030600T1_M1()+ "/01")));
					// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
					month_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(ehf030600m1.getEHF030600T1_M1()+ "/01")));
					
					month_start_cal = tools.covertStringToCalendar(month_start_date); // 月份開始日期Calendar
					month_end_cal = tools.covertStringToCalendar(month_end_date); // 月份結束日期Calendar

					boolean chk_run_flag = true;
					
					while (chk_run_flag) {

						// 判斷是否已到月份最後一天
						if (month_start_cal.equals(month_end_cal)) {
							chk_run_flag = false;
						}
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						String nowdate = ""+sdf.format( month_start_cal.getTime());
					
						late_money += this.sumAttMoney(conn, nowdate, nowdate,EHF030108T0, ehf030600m1.getEHF030600T1_02(), comp_id,1);

						// 月份往下走一天
						month_start_cal.add(Calendar.DAY_OF_MONTH, 1);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			
			}else if (((String) EHF030108T0.get("EHF030108T0_04")).equals("01")||// 遲到早退頻率單位 周
					((String) EHF030108T0.get("EHF030108T0_04")).equals("02")||// 遲到早退頻率單位  '02' = 兩週(第一週日數=第五週日數時第一週併入第二週)
					((String) EHF030108T0.get("EHF030108T0_04")).equals("03")){ // 遲到早退頻率單位   '03' = 兩週(第一週日數=第五週日數時第五週併入第四週), 
				
				List Range = new LinkedList();
				
				for (int i = 1; i <= AttDateMap.size(); i++) {
					// i變數表示第幾周
					if(!((List) AttDateMap.get(String.valueOf(i))).isEmpty()){
						Range = (List) AttDateMap.get(String.valueOf(i));
						startdate = (String) Range.get(0);
					}
					
					if (Range.size() == 1) {
						// 增加判斷 週數只有一周時 結束時間則為開始時間
						enddate = (String) Range.get(0);
					} else {
						// 否則結束時間為當周最後一天
						enddate = (String) Range.get(Range.size() - 1);
					}
					late_money+=this.sumAttMoney(conn, startdate, enddate, EHF030108T0, ehf030600m1.getEHF030600T1_02(), comp_id, 1);
				}
			} else if (((String) EHF030108T0.get("EHF030108T0_04")).equals("04")) {// '04' = 月
				List Range_01 = (List) AttDateMap.get(String.valueOf(1));
				startdate = (String) Range_01.get(0);
				List Range_02 = (List) AttDateMap.get(String.valueOf(AttDateMap.size()));
				enddate = (String) Range_02.get(Range_02.size() - 1);

				late_money += this.sumAttMoney(conn, startdate, enddate,EHF030108T0, ehf030600m1.getEHF030600T1_02(), comp_id,1);
			}
		//以上為遲到早退扣除金額
//====================================================================================================================================
		AttDateMap=this.getAttDateMap(ehf030600m1.getEHF030600T1_M1(),"",(String)EHF030108T0.get("EHF030108T0_08"),monthstart);
			
		//以下為曠職扣除金額
		if (((String) EHF030108T0.get("EHF030108T0_08")).equals("01")||// 遲到早退頻率單位 周
				((String) EHF030108T0.get("EHF030108T0_08")).equals("02")||// 遲到早退頻率單位  '02' = 兩週(第一週日數=第五週日數時第一週併入第二週)
				((String) EHF030108T0.get("EHF030108T0_08")).equals("03")) // 遲到早退頻率單位   '03' = 兩週(第一週日數=第五週日數時第五週併入第四週)
			{
				List Range = new LinkedList();
				
				for (int i = 1; i <= AttDateMap.size(); i++) {
					// i變數表示第幾周
					if(!((List) AttDateMap.get(String.valueOf(i))).isEmpty()){
						Range = (List) AttDateMap.get(String.valueOf(i));
						startdate = (String) Range.get(0);
					}
					
					if (Range.size() == 1) {
						// 增加判斷 週數只有一周時 結束時間則為開始時間
						enddate = (String) Range.get(0);
					} else {
						// 否則結束時間為當周最後一天
						enddate = (String) Range.get(Range.size() - 1);
					}
					lack_money+=this.sumAttMoney(conn, startdate, enddate, EHF030108T0, ehf030600m1.getEHF030600T1_02(), comp_id, 2);
				}
			}else if (((String) EHF030108T0.get("EHF030108T0_08")).equals("04")) {//'04' = 月
				List Range_01 = (List) AttDateMap.get(String.valueOf(1));
				startdate = (String) Range_01.get(0);
				List Range_02 = (List) AttDateMap.get(String.valueOf(AttDateMap.size()));
				enddate = (String) Range_02.get(Range_02.size() - 1);	
				
				lack_money+=this.sumAttMoney(conn, startdate, enddate, EHF030108T0, ehf030600m1.getEHF030600T1_02(), comp_id, 2);	
			}else if (((String) EHF030108T0.get("EHF030108T0_08")).equals("05")){// 遲到早退頻率單位 天  新增於20111112  BY賴泓錡 
				// 星期處理變數
				String month_start_date = "";
				String month_end_date = "";
				Calendar month_start_cal = null;
				Calendar month_end_cal = null;
				try {
					// 處理星期資料
					// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
					month_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(ehf030600m1.getEHF030600T1_M1()+ "/01")));
					// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
					month_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(ehf030600m1.getEHF030600T1_M1()+ "/01")));

					month_start_cal = tools.covertStringToCalendar(month_start_date); // 月份開始日期Calendar
					month_end_cal = tools.covertStringToCalendar(month_end_date); // 月份結束日期Calendar

					boolean chk_run_flag = true;

					while (chk_run_flag) {

						// 判斷是否已到月份最後一天
						if (month_start_cal.equals(month_end_cal)) {
							chk_run_flag = false;
						}
						 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				         String nowdate = ""+sdf.format( month_start_cal.getTime());
						
				         lack_money += this.sumAttMoney(conn, nowdate, nowdate,EHF030108T0, ehf030600m1.getEHF030600T1_02(), comp_id,2);

						// 月份往下走一天
						month_start_cal.add(Calendar.DAY_OF_MONTH, 1);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		
		
		
		//以上為曠職扣除金額
//====================================================================================================================================
		//以下為請假扣除金額
		
		List EHF030108T1=this.getEHF030108T1(conn,ehf030200m0.getEHF030200T0_20(),comp_id);
		
			if (EHF030108T1.size() != 0) {
				for (int size = 0; size < EHF030108T1.size(); size++) {
					Map Ask_for_leave = (Map) EHF030108T1.get(size);
					if(Ask_for_leave.get("EHF030108T1_03")!=null){
						if (((String) Ask_for_leave.get("EHF030108T1_03")).equals("01")	|| // 遲到早退頻率單位 周
								((String) Ask_for_leave.get("EHF030108T1_03")).equals("02") || // 遲到早退頻率單位 '02' = 兩週(第一週日數=第五週日數時第一週併入第二週)
								((String) Ask_for_leave.get("EHF030108T1_03")).equals("03")) { // 遲到早退頻率單位 '03' = 兩週(第一週日數=第五週日數時第五週併入第四週),
							// 取得計算日期區間 
							AttDateMap=this.getAttDateMap(ehf030600m1.getEHF030600T1_M1(),(String)Ask_for_leave.get("EHF030108T1_03"),"",monthstart);
							
							List Range = new LinkedList();
							
							for (int i = 1; i <= AttDateMap.size(); i++) {
								// i變數表示第幾周
								if(!((List) AttDateMap.get(String.valueOf(i))).isEmpty()){
									Range = (List) AttDateMap.get(String.valueOf(i));
									startdate = (String) Range.get(0);
								}

								if (Range.size() == 1) {
									// 增加判斷 週數只有一周時 結束時間則為開始時間
									enddate = (String) Range.get(0);
								} else {
									// 否則結束時間為當周最後一天
									enddate = (String) Range.get(Range.size() - 1);
								}
								Ask_for_leave_money += this.sumAttMoney(conn,startdate, enddate, Ask_for_leave,ehf030600m1.getEHF030600T1_02(), comp_id,3);
							}
						} else if (((String) Ask_for_leave.get("EHF030108T1_03"))	.equals("04")) {// '04' = 月
							// 取得計算日期區間 
							AttDateMap=this.getAttDateMap(ehf030600m1.getEHF030600T1_M1(),(String)Ask_for_leave.get("EHF030108T1_03"),"",monthstart);
							
							List Range_01 = (List) AttDateMap.get(String.valueOf(1));
							startdate = (String) Range_01.get(0);
							List Range_02 = (List) AttDateMap.get(String.valueOf(AttDateMap.size()));
							enddate = (String) Range_02.get(Range_02.size() - 1);

							Ask_for_leave_money += this.sumAttMoney(conn,startdate, enddate, Ask_for_leave, ehf030600m1.getEHF030600T1_02(), comp_id,3);
						} else if (((String) Ask_for_leave.get("EHF030108T1_03"))	.equals("05")) {// 遲到早退頻率單位 天 新增於20111112 BY賴泓錡
							// 星期處理變數
							String month_start_date = "";
							String month_end_date = "";
							Calendar month_start_cal = null;
							Calendar month_end_cal = null;
							try {
								// 處理星期資料
								// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
								month_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(ehf030600m1.getEHF030600T1_M1()+ "/01")));
								// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
								month_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(ehf030600m1.getEHF030600T1_M1()+ "/01")));
								
								month_start_cal = tools.covertStringToCalendar(month_start_date); // 月份開始日期Calendar
								month_end_cal = tools.covertStringToCalendar(month_end_date); // 月份結束日期Calendar

								boolean chk_run_flag = true;

								while (chk_run_flag) {

									// 判斷是否已到月份最後一天
									if (month_start_cal.equals(month_end_cal)) {
										chk_run_flag = false;
									}
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
									String nowdate = ""	+ sdf.format(month_start_cal.getTime());

									Ask_for_leave_money += this.sumAttMoney(conn, nowdate, nowdate, Ask_for_leave, ehf030600m1.getEHF030600T1_02(), comp_id, 3);

									// 月份往下走一天
									month_start_cal.add(Calendar.DAY_OF_MONTH, 1);
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		//以上為請假扣除金額
//====================================================================================================================================
		
		
			total_Net_Money=late_money+lack_money+Ask_for_leave_money;
			int Surplus_Money=money-total_Net_Money;//剩餘金額為   系統設定全勤獎金金額-總扣除金額
		
		
			if(Surplus_Money<=0)
				return_MAP.put("MONEY", 0+"");
			else
				return_MAP.put("MONEY", Surplus_Money+"");
			}else{
				//未啟用全勤獎金扣費
				return_MAP.put("MONEY", 0+"");
				}
		}else{
			//未啟用全勤獎金扣費
			return_MAP.put("MONEY", 0+"");
			}

		return return_MAP;
	}

	private int sumAttMoney(Connection conn, String startdate,String enddate, Map ehf030108T0, String emp_id, String comp_id, int type) {

		float second=0;
		int money=0;
		String sql="";
		
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			switch (type){
			
			
			case 1:
				String EHF030108T0_04 = (String) ehf030108T0.get("EHF030108T0_04");// 遲到早退頻率單位
				String EHF030108T0_05 = (String) ehf030108T0.get("EHF030108T0_05");// 遲到早退扣費基準
				String EHF030108T0_06 = (String) ehf030108T0.get("EHF030108T0_06");// 遲到早退扣費基準單位
				int EHF030108T0_07 = Integer.valueOf((String) ehf030108T0.get("EHF030108T0_07"));// 遲到早退扣費金額

					sql = "SELECT * FROM EHF020410T0 WHERE 1 = 1 "
							+ " AND EHF020410T0_01 = '" + emp_id + "'"
							+ " AND EHF020410T0_08 = '" + comp_id + "'"
							+ " AND EHF020410T0_06 IN ('01' , '02')"
							+ " AND EHF020410T0_FIX = '0'"//取得未修正的資料
							+ " AND (EHF020410T0_03 BETWEEN '" + startdate	+ "' AND '" + enddate + "')";
					// 依每次分鐘條件 則必須多一個條件
					if (EHF030108T0_04.equals("01")) {
						// 遲到早退扣費基準(EX:  設定10分鐘  轉換為10*60  為600秒)
						second = Float.parseFloat((new BigDecimal(EHF030108T0_05).multiply(new BigDecimal("60"))).toString());
						sql += " AND EHF020410T0_07  >= '" + second + "'";
					}

					// 執行SQL
					//Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					 rs = stmt.executeQuery(sql);

					if (EHF030108T0_06.equals("01")) {// '01'=依每次分鐘條件
						if (rs.next()) {
							// 如果有資料 表示此周有遲到一次
							money += EHF030108T0_07;
						}
						rs.close();
						stmt.close();
					} else if (EHF030108T0_06.equals("02")) { // '02'=依累計分鐘條件

						while (rs.next()) {
							// 累加遲到 早退的秒數
							second += Float.parseFloat(rs.getString("EHF020410T0_07"));
						}
						if (second >= Float.parseFloat((new BigDecimal((String) ehf030108T0.get("EHF030108T0_05")).multiply(new BigDecimal("60"))).toString())) {
							// 當累加的秒數大於設定的(分鐘*秒數)
							money += EHF030108T0_07;
						}
						rs.close();
						stmt.close();
					} else if (EHF030108T0_06.equals("03")) { // '03'=依次數條件
						int count = 0;
						while (rs.next()) {
							// 如果有資料 表示此周有遲到一次
							count += 1;
						}
						if (count >= Float.parseFloat((String) ehf030108T0.get("EHF030108T0_05"))) {
							money += EHF030108T0_07;
						}
						rs.close();
						stmt.close();
					}
					break;
			case 2:
				String EHF030108T0_08 = (String) ehf030108T0.get("EHF030108T0_08");// 曠職頻率單位
				String EHF030108T0_09 = (String) ehf030108T0.get("EHF030108T0_09");// 曠職扣費基準
				String EHF030108T0_10 = (String) ehf030108T0.get("EHF030108T0_10");// 曠職扣費基準單位
				int EHF030108T0_11 = Integer.valueOf((String) ehf030108T0.get("EHF030108T0_11"));// 遲到早退扣費金額

					sql = "SELECT floor((SUM(EHF020410T0_07)) / " +
							" ( SELECT COUNT(EHF020410T0_01) FROM   " +
							" EHF020410T0 " +
							" WHERE 1 = 1" 
							+ " AND EHF020410T0_01 = '" + emp_id + "'"
							+ " AND EHF020410T0_08 = '" + comp_id + "'"
							+ " AND EHF020410T0_06 IN ('03') " 
							+ " AND (EHF020410T0_03 BETWEEN '" + startdate	+ "' " 
							+ " AND '" + enddate + "') GROUP BY EHF020410T0_01)) AS EHF020410T0_07 FROM EHF020410T0 WHERE 1 = 1 "
							+ " AND EHF020410T0_01 = '" + emp_id + "'"
							+ " AND EHF020410T0_08 = '" + comp_id + "'"
							+ " AND EHF020410T0_06 IN ('03')"
							+ " AND EHF020410T0_FIX = '0'"//取得未修正的資料
							+ " AND (EHF020410T0_03 BETWEEN '" + startdate	+ "' AND '" + enddate + "')";
					// 依每次小時條件 則必須多一個條件
					if (EHF030108T0_10.equals("01")) {
						second = Float.parseFloat((new BigDecimal(EHF030108T0_09).multiply(new BigDecimal("60")).multiply(new BigDecimal("60"))).toString());// 遲到早退分鐘數
						sql += " AND EHF020410T0_07  >= '" + second*60 + "'";
					}

					// 執行SQL
					//Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					 rs = stmt.executeQuery(sql);

					if (EHF030108T0_10.equals("01")) {// '01'=依每次分鐘條件
						if (rs.next()) {
							if(rs.getString("EHF020410T0_07")!=null){
								money += EHF030108T0_11;// 如果有資料 表示此周有遲到一次
							}
						}
						rs.close();
						stmt.close();
					} else if (EHF030108T0_10.equals("02")) { // '02'=依累計分鐘條件

						while (rs.next()) {
							// 累加遲到 早退的秒數
							if(rs.getString("EHF020410T0_07")!=null){
								second += Float.parseFloat(rs.getString("EHF020410T0_07"));
							}
						}
						rs.close();
						stmt.close();
						if (second >= Float.parseFloat((new BigDecimal((String) ehf030108T0.get("EHF030108T0_09")).multiply(new BigDecimal("60"))).toString())) {
							// 當累加的秒數大於設定的(分鐘*秒數)
							money += EHF030108T0_11;
						}

						rs.close();
						stmt.close();
					} else if (EHF030108T0_10.equals("03")) { // '03'=依次數條件
						int count = 0;
						while (rs.next()) {
							if(rs.getString("EHF020410T0_07")!=null){
								// 如果有資料 表示此周有遲到一次
								count += 1;
							}
						}
						if (count >= Float.parseFloat((String) ehf030108T0.get("EHF030108T0_09"))) {
							money += EHF030108T0_11;
						}
						rs.close();
						stmt.close();
					}
					rs.close();
					stmt.close();
					break;
					
			case 3:
				String EHF030108T1_03 = (String) ehf030108T0.get("EHF030108T1_03");// 請假頻率單位
				String EHF030108T1_04 = (String) ehf030108T0.get("EHF030108T1_04");// 請假扣費基準
				String EHF030108T1_05 = (String) ehf030108T0.get("EHF030108T1_05");// 請假扣費基準單位
				int EHF030108T1_06 = Integer.valueOf((String) ehf030108T0.get("EHF030108T1_06"));// 請假扣費金額

					sql = "SELECT * FROM EHF020200T0 WHERE 1 = 1 "
						+ " AND EHF020200T0_03 = '" + emp_id + "'"//員工工號
						+ " AND EHF020200T0_18 = '" + comp_id + "'"//公司代碼
						+ " AND EHF020200T0_14 ='"+(String) ehf030108T0.get("EHF030108T1_02")+"'"//請假類別
						+ " AND EHF020200T0_16 = '0006'"//表單狀態(完成)
						+ " AND ((EHF020200T0_09 BETWEEN '" + startdate	+ "' AND '" + enddate + "')" 
						+ " OR (EHF020200T0_10 BETWEEN '" + startdate	+ "' AND '" + enddate + "'))";
					// 依每次小時條件 則必須多一個條件
					//if (EHF030108T1_03.equals("01")) {
					//	second = Float.parseFloat((new BigDecimal(EHF030108T1_04).multiply(new BigDecimal("60")).multiply(new BigDecimal("60"))).toString());// 遲到早退分鐘數
					//	sql += " AND EHF020200T0_13  >= '" + second*60 + "'";
					//}

					// 執行SQL
					//Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					 rs = stmt.executeQuery(sql);

					if (EHF030108T1_05.equals("01")) {// '01'=依每次時數條件
						second = Float.parseFloat(EHF030108T1_04);// 可請假時數
						int count = 0;
						while (rs.next()) {
							float work_hour = Float.parseFloat(tools.getSysParam(conn,comp_id,"WORK_HOURS"));
							float hour=0;
							String time[]=((String)rs.getString("EHF020200T0_13")).split("/");
							
							hour=(Integer.valueOf(time[0])*work_hour)+Float.valueOf(time[1]);//取得請假時數
							if(hour>second){
								count+=1;
								//money += EHF030108T1_06;
							}
							
						}
						if(count>1){
							// 如果有資料 表示此周有遲到一次
							money += EHF030108T1_06;
						}
						rs.close();
						stmt.close();
							
					} else if (EHF030108T1_05.equals("02")) { // '02'=依累計時數條件

						while (rs.next()) {
							float work_hour = Float.parseFloat(tools.getSysParam(conn,comp_id,"WORK_HOURS"));
							float hour=0;//取得請假總分鐘數
							String time[]=((String)rs.getString("EHF020200T0_13")).split("/");
							
							hour=(Integer.valueOf(time[0])*work_hour)+Float.valueOf(time[1]);
							
							// 累加遲到 早退的秒數
							second += hour;
						}
						if (second >= Float.parseFloat(EHF030108T1_04)) {
							// 當累加的秒數大於設定的(分鐘*秒數)
							money += EHF030108T1_06;
						}
						rs.close();
						stmt.close();

					} else if (EHF030108T1_05.equals("03")) { // '03'=依次數條件
						int count = 0;
						while (rs.next()) {
							// 如果有資料 表示此周有遲到一次
							count += 1;
						}
						if (count >= Float.parseFloat(EHF030108T1_04)) {
							money += EHF030108T1_06;
						}
						rs.close();
						stmt.close();
					}
					rs.close();
					stmt.close();
					
					break;
			}
			if(rs!=null){
				rs.close();
				stmt.close();
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		
		return money;
	}
	/**
	 * 取得計算日期區間    20131007新增  BY 賴泓錡
	 * @param getEHF030600T1_M1 薪資年月
	 * @param EHF030108T0_04 遲到早退頻率單位
	 * @param EHF030108T0_08 曠職頻率單位
	 * @param monthstart
	 * @return
	 */
	private Map getAttDateMap(String EHF030600T1_M1,String EHF030108T0_04, String EHF030108T0_08, String monthstart) {
		// TODO Auto-generated method stub
		
		Map return_MAP = new LinkedHashMap<String, String>();
		
		List ONE = new LinkedList<String>();
		List TWO = new LinkedList<String>();
		List Three = new LinkedList<String>();
		List Four = new LinkedList<String>();
		List Fifth= new LinkedList<String>();
		List Sixth= new LinkedList<String>();

		int SATURDAY=0;
		
		if(monthstart.equals("STARTMONDAY")){//禮拜一為一星期的開始
			SATURDAY=Calendar.SUNDAY;
		}else if(monthstart.equals("STARTSUNDAY")){//禮拜天為一個星期的開始
			SATURDAY=Calendar.SATURDAY;
		}
		
		//取得計算日期區間
		Map getWeek=this.getWEEK(EHF030600T1_M1,SATURDAY);
		
		List First_week=(List) getWeek.get(1);//第一周所有日期
		List last_week=(List) getWeek.get(getWeek.size());//最後一周所有日期
		
		
		if (EHF030108T0_04.equals("01")||EHF030108T0_08.equals("01")) {//遲到早退頻率單位 周
			
			switch(getWeek.size()){
			case 1:
				return_MAP.put("ERROR", "YES");
				break;
			case 2:
				return_MAP.put("ERROR", "YES");
				break;
			case 3:
				return_MAP.put("ERROR", "YES");
				break;
			case 4://當月有四周
				ONE = 	(List) getWeek.get(1);// 第一周
				TWO = 	(List) getWeek.get(2);// 第二周
				Three = (List) getWeek.get(3);// 第三周
				Four = 	(List) getWeek.get(4);// 第四周
				break;
			case 5://當月有五周
				ONE = 	(List) getWeek.get(1);// 第一周
				TWO = 	(List) getWeek.get(2);// 第二周
				Three = (List) getWeek.get(3);// 第三周
				Four = 	(List) getWeek.get(4);// 第四周
				Fifth= 	(List) getWeek.get(5);// 第五周
				
				break;
			case 6://當月有六周
				ONE = 	(List) getWeek.get(1);// 第一周
				TWO = 	(List) getWeek.get(2);// 第二周
				Three = (List) getWeek.get(3);// 第三周
				Four = 	(List) getWeek.get(4);// 第四周
				Fifth= 	(List) getWeek.get(5);// 第五周
				Sixth= 	(List) getWeek.get(6);// 第六周
				break;
			}

			
		} else if (EHF030108T0_04.equals("02")||EHF030108T0_08.equals("02")) {//兩週(第一週日數=第五週日數時第一週併入第二週)
			
			switch(getWeek.size()){
			case 1:
				return_MAP.put("ERROR", "YES");
				break;
			case 2:
				return_MAP.put("ERROR", "YES");
				break;
			case 3:
				return_MAP.put("ERROR", "YES");
				break;
			case 4://當月有四周
				ONE = 	(List) getWeek.get(1);// 第一周
				TWO = 	(List) getWeek.get(2);// 第二周
				Three = (List) getWeek.get(3);// 第三周
				Four = 	(List) getWeek.get(4);// 第四周
				break;
			case 5://當月有五周
				
				
				if (First_week.size() == last_week.size()) {// 當第一周的天數等於最後一周的天數時

					List Second_week = (List) getWeek.get(2);// 第二周所有日期
					for (int i = 0; i < Second_week.size(); i++) {
						First_week.add(Second_week.get(i));
					}
					ONE = First_week;// 第一周與第二周合併
					TWO = 	(List) getWeek.get(3);// 第二周
					Three = (List) getWeek.get(4);// 第三周
					Four = 	(List) getWeek.get(5);// 第四周

				}else if(First_week.size()>last_week.size()){//當第一周的天數大於最後一周
					//合併第五周到第四周
					List Third_week=(List) getWeek.get(4);//第四周所有日期
					List Fifth_week=(List) getWeek.get(5);//第五周所有日期
					
					ONE = (List) getWeek.get(1);// 第一周
					TWO = (List) getWeek.get(2);// 第二周
					Three = (List) getWeek.get(3);// 第三周
					
					for (int i = 0; i < Fifth_week.size(); i++) {
						Third_week.add(Fifth_week.get(i));
					}

					Four = Third_week;//合併第五周到第四周
					
				}else if(First_week.size()<last_week.size()){//當第一周的天數小於最後一周
					
					List Second_week = (List) getWeek.get(2);// 第二周所有日期
					
					for (int i = 0; i < Second_week.size(); i++) {
						First_week.add(Second_week.get(i));
					}
					
					ONE = First_week;// 第一周與第二周合併
					TWO = (List) getWeek.get(3);// 第二周
					Three = (List) getWeek.get(4);// 第三周
					Four = (List) getWeek.get(5);// 第四周
				}
				break;
			case 6://當月有六周
				
				List Second_week = (List) getWeek.get(2);// 第二周所有日期
				List Third_week  = (List) getWeek.get(4);// 第四周所有日期
				List Fifth_week  = (List) getWeek.get(5);// 第五周所有日期
				for (int i = 0; i < Second_week.size(); i++) {
					First_week.add(Second_week.get(i));
				}
				ONE = First_week;// 第一周與第二周合併
				TWO = (List) getWeek.get(3);// 第二周
				Three = (List) getWeek.get(4);// 第三周
				
				for (int i = 0; i < Fifth_week.size(); i++) {
					Third_week.add(Fifth_week.get(i));
				}

				Four = Third_week;//合併第五周到第四周
				break;
			}

		}else if (EHF030108T0_04.equals("03")||EHF030108T0_08.equals("03")) {//兩週(第一週日數=第五週日數時第五週併入第四週)
			switch(getWeek.size()){
			case 1:
				return_MAP.put("ERROR", "YES");
				break;
			case 2:
				return_MAP.put("ERROR", "YES");
				break;
			case 3:
				return_MAP.put("ERROR", "YES");
				break;
			case 4://當月有四周
				return_MAP=getWeek;
				break;
			case 5://當月有五周
				
				
				if (First_week.size() == last_week.size()) {// 當第一周的天數等於最後一周的天數時
					//合併第五周到第四周
					List Third_week=(List) getWeek.get(4);//第四周所有日期
					List Fifth_week=(List) getWeek.get(5);//第五周所有日期
					
					ONE = (List) getWeek.get(1);// 第一周
					TWO = (List) getWeek.get(2);// 第二周
					Three = (List) getWeek.get(3);// 第三周
					
					for (int i = 0; i < Fifth_week.size(); i++) {
						Third_week.add(Fifth_week.get(i));
					}

					Four = Third_week;//合併第五周到第四周
					
					

				}else if(First_week.size()>last_week.size()){//當第一周的天數大於最後一周
					//合併第五周到第四周
					List Third_week=(List) getWeek.get(4);//第四周所有日期
					List Fifth_week=(List) getWeek.get(5);//第五周所有日期
					
					ONE = (List) getWeek.get(1);// 第一周
					TWO = (List) getWeek.get(2);// 第二周
					Three = (List) getWeek.get(3);// 第三周
					
					for (int i = 0; i < Fifth_week.size(); i++) {
						Third_week.add(Fifth_week.get(i));
					}

					Four = Third_week;//合併第五周到第四周
					
				}else if(First_week.size()<last_week.size()){//當第一周的天數小於最後一周
					
					List Second_week = (List) getWeek.get(2);// 第二周所有日期
					for (int i = 0; i < Second_week.size(); i++) {
						First_week.add(Second_week.get(i));
					}
					ONE = First_week;// 第一周與第二周合併
					TWO = 	(List) getWeek.get(3);// 第二周
					Three = (List) getWeek.get(4);// 第三周
					Four = 	(List) getWeek.get(5);// 第四周
				}
				break;
			case 6://當月有六周
				
				List Second_week = (List) getWeek.get(2);// 第二周所有日期
				List Third_week  = (List) getWeek.get(4);// 第四周所有日期
				List Fifth_week  = (List) getWeek.get(5);// 第五周所有日期
				for (int i = 0; i < Second_week.size(); i++) {
					First_week.add(Second_week.get(i));
				}
				
				ONE = First_week;// 第一周與第二周合併
				TWO = (List) getWeek.get(3);// 第二周
				Three = (List) getWeek.get(4);// 第三周
				
				for (int i = 0; i < Fifth_week.size(); i++) {
					Third_week.add(Fifth_week.get(i));
				}

				Four = Third_week;//合併第五周到第四周
				break;
			}

		} else if (EHF030108T0_04.equals("04")||EHF030108T0_08.equals("04")) {//遲到早退頻率單位 月
			switch(getWeek.size()){
			case 1:
				return_MAP.put("ERROR", "YES");
				break;
			case 2:
				return_MAP.put("ERROR", "YES");
				break;
			case 3:
				return_MAP.put("ERROR", "YES");
				break;
			case 4://當月有四周
				ONE = 	(List) getWeek.get(1);// 第一周
				TWO = 	(List) getWeek.get(2);// 第二周
				Three = (List) getWeek.get(3);// 第三周
				Four = 	(List) getWeek.get(4);// 第四周
				break;
			case 5://當月有五周
				ONE = 	(List) getWeek.get(1);// 第一周
				TWO = 	(List) getWeek.get(2);// 第二周
				Three = (List) getWeek.get(3);// 第三周
				Four = 	(List) getWeek.get(4);// 第四周
				Fifth= 	(List) getWeek.get(5);// 第五周
				
				break;
			case 6://當月有六周
				ONE = 	(List) getWeek.get(1);// 第一周
				TWO = 	(List) getWeek.get(2);// 第二周
				Three = (List) getWeek.get(3);// 第三周
				Four = 	(List) getWeek.get(4);// 第四周
				Fifth= 	(List) getWeek.get(5);// 第五周
				Sixth= 	(List) getWeek.get(6);// 第六周
				break;
			}

		}
		if (EHF030108T0_04.equals("01") || EHF030108T0_04.equals("04")||EHF030108T0_08.equals("01")||EHF030108T0_08.equals("04")) {
			if (getWeek.size() == 6) {
				return_MAP.put("1", ONE);
				return_MAP.put("2", TWO);
				return_MAP.put("3", Three);
				return_MAP.put("4", Four);
				return_MAP.put("5", Fifth);
				return_MAP.put("6", Sixth);
			} else if (getWeek.size() == 5) {
				return_MAP.put("1", ONE);
				return_MAP.put("2", TWO);
				return_MAP.put("3", Three);
				return_MAP.put("4", Four);
				return_MAP.put("5", Fifth);
			} else if (getWeek.size() == 4) {
				return_MAP.put("1", ONE);
				return_MAP.put("2", TWO);
				return_MAP.put("3", Three);
				return_MAP.put("4", Four);
			}
		}else{
			return_MAP.put("1", ONE);
			return_MAP.put("2", TWO);
			return_MAP.put("3", Three);
			return_MAP.put("4", Four);
		}
		
		
		
		
		return return_MAP;
	}

	/**
	 * 取得全勤獎金設定明細。 20131007新增  BY 賴泓錡
	 * @param conn
	 * @param EHF030200T0_20
	 * @param comp_id
	 * @return
	 */
	private Map getEHF030108T0(Connection conn, String EHF030200T0_20, String comp_id) {
		// TODO Auto-generated method stub
		
		Map EHF030108T0 =new HashMap<String, String>();
		List EHF030108T1 = new LinkedList<String>();
		try {
		String sql =
			" SELECT   * " +
			" FROM EHF030108T0" +
			//" LEFT JOIN    " +
			//" EHF030108T1 ON EHF030108T0_01 = EHF030108T1_01" +
			//" AND EHF030108T0_13=EHF030108T1_07" +
			" WHERE 1 = 1" +
			" AND EHF030108T0_02='"+EHF030200T0_20+"'"+
			" AND EHF030108T0_13='"+comp_id+"'";
		
			// 執行SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {

				// 將全勤獎金資料放入return_map中
				EHF030108T0.put("EHF030108T0_02", 	rs.getString("EHF030108T0_02"));// 全勤獎金扣費規則名稱
				EHF030108T0.put("EHF030108T0_03", 	rs.getString("EHF030108T0_03"));// 遲到早退頻率單位
				EHF030108T0.put("EHF030108T0_04", 	rs.getString("EHF030108T0_04"));// 遲到早退扣費基準
				EHF030108T0.put("EHF030108T0_05", 	rs.getString("EHF030108T0_05"));// 遲到早退扣費基準單位													
				EHF030108T0.put("EHF030108T0_06", 	rs.getString("EHF030108T0_06"));// 遲到早退扣費金額
				EHF030108T0.put("EHF030108T0_07", 	rs.getString("EHF030108T0_07"));// 遲到早退扣費金額
				EHF030108T0.put("EHF030108T0_08", 	rs.getString("EHF030108T0_08"));// 曠職頻率單位
				EHF030108T0.put("EHF030108T0_09", 	rs.getString("EHF030108T0_09"));// 曠職扣費基準
				EHF030108T0.put("EHF030108T0_10", 	rs.getString("EHF030108T0_10"));// 曠職扣費基準單位
				EHF030108T0.put("EHF030108T0_11", 	rs.getString("EHF030108T0_11"));// 曠職扣費金額
				EHF030108T0.put("EHF030108T0_12", 	rs.getString("EHF030108T0_12"));// 是否啟用
				EHF030108T0.put("EHF030108T0_13", 	rs.getString("EHF030108T0_13"));// 公司代碼
			} 
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		
		return EHF030108T0;
	}
	
	
	/**
	 * 取得全勤獎金設定明細(請假明細)。 20131007新增  BY 賴泓錡
	 * @param conn
	 * @param EHF030200T0_20
	 * @param comp_id
	 * @return
	 */
	private List getEHF030108T1(Connection conn, String EHF030200T0_20, String comp_id) {
		// TODO Auto-generated method stub
		
		List EHF030108T1 = new LinkedList<String>();
		try {
			String sql =
				" SELECT   EHF030108T1_02,EHF030108T1_03,EHF030108T1_04,EHF030108T1_05,EHF030108T1_06 " +
				" FROM EHF030108T0" +
				" LEFT JOIN    " +
				" EHF030108T1 ON EHF030108T0_01 = EHF030108T1_01" +
				" AND EHF030108T0_13=EHF030108T1_07" +
				" WHERE 1 = 1" +
				" AND EHF030108T0_02='"+EHF030200T0_20+"'"+
				" AND EHF030108T0_13='"+comp_id+"'";
		
			// 執行SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Map MAP = new LinkedHashMap<String, String>();//
					
					MAP.put("EHF030108T1_02", rs.getString("EHF030108T1_02"));
					MAP.put("EHF030108T1_03", rs.getString("EHF030108T1_03"));
					MAP.put("EHF030108T1_04", rs.getString("EHF030108T1_04"));
					MAP.put("EHF030108T1_05", rs.getString("EHF030108T1_05"));
					MAP.put("EHF030108T1_06", rs.getString("EHF030108T1_06"));	
					EHF030108T1.add(MAP);
			} 
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		
		return EHF030108T1;
	}
	
	
	
	/**
	 * 取得計算日期區間。
	 * @param eHF030600T1M1 薪資年月
	 * @param saturday 系統參數指定的每周第一天為     星期一  或是  星期天
	 */
	private Map getWEEK(String eHF030600T1M1, int saturday) {
		
		Map return_MAP = new LinkedHashMap<String, String>();//存取有幾周  
		List EHF030108T0 = new LinkedList<String>();//存取當周每一天的日期
		
		//List<Map<String, String>> return_list = new LinkedList<Map<String, String>>();
		String dateString = eHF030600T1M1+"/01";

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date;
		try {
			date = sdf.parse(dateString);

			Calendar c_now = Calendar.getInstance();
			Calendar c_begin = Calendar.getInstance();
			Calendar c_end = Calendar.getInstance();

			DateFormatSymbols dfs = new DateFormatSymbols();
			String[] weeks = dfs.getWeekdays();

			c_now.setTime(date);
			int year = c_now.get(Calendar.YEAR);
			int month = c_now.get(Calendar.MONTH) + 1;
			int[] days = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
			if (year % 4 == 0) {
				days[2] = 29;// 大年
			}
			c_begin.set(year, month - 1, 1);//Calendar的月从0-11，所以2月是1.
			c_end.set(year, month - 1, days[month]);

			int count = 1;
			c_end.add(Calendar.DAY_OF_YEAR, 1);
			while (c_begin.before(c_end)) {
				//System.out.println("" + count + ","+ sdf.format(c_begin.getTime()) + ","+ weeks[c_begin.get(Calendar.DAY_OF_WEEK)]);
				EHF030108T0.add(sdf.format(c_begin.getTime()));

				if (c_begin.get(Calendar.DAY_OF_WEEK) == saturday) {// 
					return_MAP.put(count,EHF030108T0);
					EHF030108T0 = new LinkedList<String>();
					count++;
				}
				c_begin.add(Calendar.DAY_OF_YEAR, 1);
			}
			//當日期走到最後一周時 
			if (EHF030108T0.size() != 0) {
				return_MAP.put(count, EHF030108T0);
				count++;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return return_MAP;
	}
	
	/**
	 *  取得員工的考勤資訊。
	 * @param conn
	 * @param salYYMM 薪資年月
	 * @param ehf030600t1_02 員工工號
	 * @param comp_id
	 * @return
	 */
	private Map getAttDeductionInf(Connection conn, String salYYMM,String ehf030600t1_02, String comp_id) {
		// TODO Auto-generated method stub
		Map total_data_MAP = new LinkedHashMap<String, String>();
		List ONE = new LinkedList<String>();//遲到
		List TWO = new LinkedList<String>();//早退
		List Three = new LinkedList<String>();//曠職
		try {
			String sql = "SELECT * FROM EHF020410T0 WHERE 1 = 1 "
					+ " AND EHF020410T0_01 = '" + ehf030600t1_02 + "'"
					+ " AND EHF020410T0_08 = '" + comp_id + "'"
					+ " AND EHF020410T0_06 IN ('01' , '02', '03')"
					+ " AND EHF020410T0_FIX = '0'"
					+ " AND DATE_FORMAT(EHF020410T0_03, '%Y/%m') = '" + salYYMM	+ "'";
			// 執行SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				if("01".equals(rs.getString("EHF020410T0_06"))){
					//遲到
					Map data_MAP = new LinkedHashMap<String, String>();
					data_MAP.put("EHF020410T0_01"	, (String)rs.getString("EHF020410T0_01"));
					data_MAP.put("EHF020410T0_02"	, (String)rs.getString("EHF020410T0_02"));
					data_MAP.put("EHF020410T0_03"	, (String)rs.getString("EHF020410T0_03"));
					data_MAP.put("EHF020410T0_04"	, (String)rs.getString("EHF020410T0_04"));
					data_MAP.put("EHF020410T0_05"	, (String)rs.getString("EHF020410T0_05"));
					data_MAP.put("EHF020410T0_06"	, (String)rs.getString("EHF020410T0_06"));
					data_MAP.put("EHF020410T0_07"	, (String)rs.getString("EHF020410T0_07"));
					data_MAP.put("EHF020410T0_08"	, (String)rs.getString("EHF020410T0_08"));
					data_MAP.put("EHF020410T0_FIX"	, (String)rs.getString("EHF020410T0_FIX"));
					ONE.add(data_MAP);
					
				}else if("02".equals(rs.getString("EHF020410T0_06"))){
					//早退
					Map data_MAP = new LinkedHashMap<String, String>();
					data_MAP.put("EHF020410T0_01"	, (String)rs.getString("EHF020410T0_01"));
					data_MAP.put("EHF020410T0_02"	, (String)rs.getString("EHF020410T0_02"));
					data_MAP.put("EHF020410T0_03"	, (String)rs.getString("EHF020410T0_03"));
					data_MAP.put("EHF020410T0_04"	, (String)rs.getString("EHF020410T0_04"));
					data_MAP.put("EHF020410T0_05"	, (String)rs.getString("EHF020410T0_05"));
					data_MAP.put("EHF020410T0_06"	, (String)rs.getString("EHF020410T0_06"));
					data_MAP.put("EHF020410T0_07"	, (String)rs.getString("EHF020410T0_07"));
					data_MAP.put("EHF020410T0_08"	, (String)rs.getString("EHF020410T0_08"));
					data_MAP.put("EHF020410T0_FIX"	, (String)rs.getString("EHF020410T0_FIX"));
					TWO.add(data_MAP);
					
				}else if("03".equals(rs.getString("EHF020410T0_06"))){
					//曠職
					Map data_MAP = new LinkedHashMap<String, String>();
					data_MAP.put("EHF020410T0_01"	, (String)rs.getString("EHF020410T0_01"));
					data_MAP.put("EHF020410T0_02"	, (String)rs.getString("EHF020410T0_02"));
					data_MAP.put("EHF020410T0_03"	, (String)rs.getString("EHF020410T0_03"));
					data_MAP.put("EHF020410T0_04"	, (String)rs.getString("EHF020410T0_04"));
					data_MAP.put("EHF020410T0_05"	, (String)rs.getString("EHF020410T0_05"));
					data_MAP.put("EHF020410T0_06"	, (String)rs.getString("EHF020410T0_06"));
					data_MAP.put("EHF020410T0_07"	, (String)rs.getString("EHF020410T0_07"));
					data_MAP.put("EHF020410T0_08"	, (String)rs.getString("EHF020410T0_08"));
					data_MAP.put("EHF020410T0_FIX"	, (String)rs.getString("EHF020410T0_FIX"));
					Three.add(data_MAP);
					
				}	
			}
			rs.close();
			stmt.close();
		
			Map Later		=this.getAttLaterInf(conn,ONE,comp_id);			//計算員工遲到分鐘/次數與扣薪金額
			Map Early		=this.getAttEarlyInf(conn,TWO,comp_id);			//計算員工早退分鐘/次數與扣薪金額
			Map Absenteeism	=this.getAttAbsenteeismInf(conn,Three,comp_id);	//計算員工曠職分鐘/次數與扣薪金額
			
			//EHF030600T1_24	INT		遲到分鐘/次數
			//EHF030600T1_25	INT		遲到扣薪金額
			//EHF030600T1_26	INT		早退分鐘/次數
			//EHF030600T1_27	INT		早退扣薪金額
			//EHF030600T1_28	INT		曠職分鐘/次數
			//EHF030600T1_29	INT		曠職扣薪金額
			//total_data_MAP.put("EHF030600T1_24", Integer.valueOf((String)Later.get("total_Minute"))+Integer.valueOf((String)Later.get("total_count"))+"");//遲到
			if("Y".equals((String)Later.get("time"))){
				total_data_MAP.put("EHF030600T1_24", Integer.valueOf((String)Later.get("total_Minute"))+"");//遲到
			}else if("Y".equals((String)Later.get("freq"))){
				total_data_MAP.put("EHF030600T1_24", Integer.valueOf((String)Later.get("total_count"))+"");//遲到
			}
			total_data_MAP.put("EHF030600T1_25", (String)Later.get("total_money")+"");//遲到
			
			//total_data_MAP.put("EHF030600T1_26", Integer.valueOf((String)Early.get("total_Minute"))+Integer.valueOf((String)Early.get("total_count"))+"");//早退
			if("Y".equals((String)Early.get("time"))){
				total_data_MAP.put("EHF030600T1_26", Integer.valueOf((String)Early.get("total_Minute"))+"");//遲到
			}else if("Y".equals((String)Early.get("freq"))){
				total_data_MAP.put("EHF030600T1_26", Integer.valueOf((String)Early.get("total_count"))+"");//遲到
			}
			total_data_MAP.put("EHF030600T1_27", (String)Early.get("total_money")+"");//早退
			
			//total_data_MAP.put("EHF030600T1_28", Integer.valueOf((String)Absenteeism.get("total_Minute"))+Integer.valueOf((String)Absenteeism.get("total_count"))+"");//曠職
			if("Y".equals((String)Absenteeism.get("time"))){
				total_data_MAP.put("EHF030600T1_28", Integer.valueOf((String)Absenteeism.get("total_Minute"))+"");//遲到
			}else if("Y".equals((String)Absenteeism.get("freq"))){
				total_data_MAP.put("EHF030600T1_28", Integer.valueOf((String)Absenteeism.get("total_count"))+"");//遲到
			}
			total_data_MAP.put("EHF030600T1_29", (String)Absenteeism.get("total_money")+"");//曠職
			
			total_data_MAP.put("EHF030600T1_30", "遲到扣："+(String)Later.get("total_money")+", "+"早退扣："+(String)Early.get("total_money")+", "+"曠職扣："+(String)Absenteeism.get("total_money"));
			
			
			
			
			

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return total_data_MAP;
	}

	/**
	 * 計算員工遲到分鐘/次數與扣薪金額。
	 * @param oNE
	 * @param comp_id 
	 */
	private Map getAttLaterInf(Connection conn, List oNE, String comp_id) {
		// TODO Auto-generated method stub
		Map return_map = new HashMap<String, String>();
		int total_count = 0; // 遲到總次數
		int total_Minute = 0; // 遲到總分鐘
		int total_money = 0; // 遲到總金額
		int hour_pay=0;
		String type_time = "";
		String type_freq = "";
		//String EHF020410T0_02 = "3";// 暫存 規則序號(遲到早退曠職)
		//Map eHF020409T0 = this.getEHF020409T0(conn, EHF020410T0_02, comp_id);
		Map eHF020409T0 = this.getEHF020409T0(conn, comp_id);

		if ("01".equals(eHF020409T0.get("EHF020409T0_04"))) {// '01'=依每次分鐘條件
			
			type_time = "Y";
			
			for (int i = 0; i < oNE.size(); i++) {
				Map EHF020410T0 = (Map) oNE.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);
				
				// 將設定的可遲到分鐘 轉換為秒數
				int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_05")).multiply(new BigDecimal("60"))).toString()));
				
				if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
					// 當遲到時間小於等於設定時間 不算遲到  跳過此筆資料
					continue;
				}
				int number=0;
				if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_06"))){
					number=0;
				}else{
				// (遲到秒數/60)/系統設定的 遲到分鐘  得到次數
				 number = (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"))/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_06"));
				}
				//遲到扣薪類型
				if ("01".equals(eHF020409T0.get("EHF020409T0_07"))) {// '01'=依時薪比例
					total_count += 1;
					total_Minute += (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"));
					//total_money += number * hour_pay * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_08"));
					total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_08")) ,"HALF_UP")    ;
				} else if ("02".equals(eHF020409T0.get("EHF020409T0_07"))) {// '02'=依固定金額
					total_count += 1;
					total_Minute += (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"));
					total_money += number* Integer.valueOf((String) eHF020409T0.get("EHF020409T0_09"));
				}
			}
		} else if ("02".equals(eHF020409T0.get("EHF020409T0_04"))) {// '02'=依累計分鐘條件
			
			type_time = "Y";
			int total_time = 0;// 共遲到幾秒

			for (int i = 0; i < oNE.size(); i++) {
				Map EHF020410T0 = (Map) oNE.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);

				/*if (EHF020410T0_02.equals((String) EHF020410T0.get("EHF020410T0_02"))) {
					EHF020410T0_02 = (String) EHF020410T0.get("EHF020410T0_02");
					eHF020409T0 = this.getEHF020409T0(conn, (String) EHF020410T0.get("EHF020410T0_02"), comp_id);
				}*/
				// 將設定的可遲到分鐘 轉換為秒數
				int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_05")).multiply(new BigDecimal("60"))).toString()));

				if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
					// 當遲到時間小於等於設定時間  不算遲到  跳過此筆資料 
					continue;
				}
				//遲到總秒數
				total_time += Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07"));
			}
			int number=0;
			if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_06"))){
				number=0;
			}else{
			// (遲到秒數/60)/系統設定的 遲到分鐘  得到次數
			 number = (Integer.valueOf(total_time) / Integer.valueOf("60"))/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_06"));
			}

			//遲到扣薪類型
			if ("01".equals(eHF020409T0.get("EHF020409T0_07"))) {// '01'=依時薪比例
				total_count += 1;
				total_Minute += total_time/60;
				//total_money += number * hour_pay * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_08"));
				total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_08")) ,"HALF_UP")    ;
			} else if ("02".equals(eHF020409T0.get("EHF020409T0_07"))) {// '02'=依固定金額
				total_count += 1;
				total_Minute += number/60;
				total_money += number * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_09"));
			}
		} else if ("03".equals(eHF020409T0.get("EHF020409T0_04"))) {// '03'=依次數條件
			
			type_freq = "Y";
			int total_time = 0;// 共遲到幾秒
			int count = 0;// 共遲到幾秒

			for (int i = 0; i < oNE.size(); i++) {
				Map EHF020410T0 = (Map) oNE.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);

				/*if (EHF020410T0_02.equals((String) EHF020410T0.get("EHF020410T0_02"))) {
					EHF020410T0_02 = (String) EHF020410T0.get("EHF020410T0_02");
					eHF020409T0 = this.getEHF020409T0(conn, (String) EHF020410T0.get("EHF020410T0_02"), comp_id);
				}*/
				// 將設定的可遲到分鐘 轉換為秒數
				int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_05")).multiply(new BigDecimal("60"))).toString()));

				if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
					// 當遲到時間小於等於設定時間  不算遲到  跳過此筆資料 
					continue;
				}
				//共遲到幾秒
				total_time += Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07"));
				count+=1;
			}
			int number=0;
			if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_06"))){
				number=0;
			}else{
			// (遲到秒數/60)/系統設定的 遲到分鐘  得到次數
			 number = Integer.valueOf(count)/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_06"));
			}

			//遲到扣薪類型
			if ("01".equals(eHF020409T0.get("EHF020409T0_07"))) {// '01'=依時薪比例
				//total_count += 1;
				total_count = count;
				total_Minute += total_time/60;
				//total_money += number * hour_pay * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_08"));
				total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_08")) ,"HALF_UP")    ;
			} else if ("02".equals(eHF020409T0.get("EHF020409T0_07"))) {// '02'=依固定金額
				//total_count += 1;
				total_count = count;
				total_Minute += total_time/60;
				total_money += number * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_09"));
			}
		}
		return_map.put("time", 	type_time);
		return_map.put("freq", 	type_freq);
		return_map.put("total_count", 	total_count+"");
		return_map.put("total_Minute", 	total_Minute+"");
		return_map.put("total_money", 	total_money+"");
		return return_map;
	}
	
	
	
	
	
	/**
	 * 計算員工早退分鐘/次數與扣薪金額。
	 * @param two
	 * @param comp_id 
	 */
	private Map getAttEarlyInf(Connection conn, List TWO, String comp_id) {
		// TODO Auto-generated method stub
		Map return_map = new HashMap<String, String>();
		int total_count = 0; // 早退總次數
		int total_Minute = 0; // 早退總分鐘
		int total_money = 0; // 早退總金額
		int hour_pay=0;
		String type_time = "";
		String type_freq = "";
		//String EHF020410T0_02 = "3";// 暫存 規則序號(遲到早退曠職)
		//Map eHF020409T0 = this.getEHF020409T0(conn, EHF020410T0_02, comp_id);
		Map eHF020409T0 = this.getEHF020409T0(conn, comp_id);

		if ("01".equals(eHF020409T0.get("EHF020409T0_10"))) {// '01'=依每次分鐘條件
			
			type_time = "Y";
			
			for (int i = 0; i < TWO.size(); i++) {
				Map EHF020410T0 = (Map) TWO.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);

				/*if (EHF020410T0_02.equals((String) EHF020410T0.get("EHF020410T0_02"))) {
					EHF020410T0_02 = (String) EHF020410T0.get("EHF020410T0_02");
					eHF020409T0 = this.getEHF020409T0(conn, (String) EHF020410T0.get("EHF020410T0_02"), comp_id);
				}*/
				// 將設定的可早退分鐘 轉換為秒數
				int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_11")).multiply(new BigDecimal("60"))).toString()));
				
				if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
					// 當早退時間小於等於設定時間 不算遲到  跳過此筆資料
					continue;
				}
				int number=0;
				if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_12"))){
					number=0;
				}else{
				// (早退秒數/60)/系統設定的 早退分鐘  得到次數
				 number = (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"))
				            / Integer.valueOf((String) eHF020409T0.get("EHF020409T0_12"));
				}
				//遲到扣薪類型
				if ("01".equals(eHF020409T0.get("EHF020409T0_13"))) {// '01'=依時薪比例
					total_count += 1;
					total_Minute += (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"));
					//total_money += number * hour_pay * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_14"));
					total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_14")) ,"HALF_UP");
				} else if ("02".equals(eHF020409T0.get("EHF020409T0_13"))) {// '02'=依固定金額
					total_count += 1;
					total_Minute += (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"));
					total_money += number* Integer.valueOf((String) eHF020409T0.get("EHF020409T0_15"));
				}
			}
		} else if ("02".equals(eHF020409T0.get("EHF020409T0_10"))) {// '02'=依累計分鐘條件
			
			type_time = "Y";
			int total_time = 0;// 共遲到幾秒

			for (int i = 0; i < TWO.size(); i++) {
				Map EHF020410T0 = (Map) TWO.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);

				/*if (EHF020410T0_02.equals((String) EHF020410T0.get("EHF020410T0_02"))) {
					EHF020410T0_02 = (String) EHF020410T0.get("EHF020410T0_02");
					eHF020409T0 = this.getEHF020409T0(conn, (String) EHF020410T0.get("EHF020410T0_02"), comp_id);
				}*/
				// 將設定的可遲到分鐘 轉換為秒數
				int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_11")).multiply(new BigDecimal("60"))).toString()));

				if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
					// 當遲到時間小於等於設定時間  不算遲到  跳過此筆資料 
					continue;
				}
				//遲到總秒數
				total_time += Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07"));

			}
			int number=0;
			if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_12"))){
				number=0;
			}else{
			// (早退秒數/60)/系統設定的 早退分鐘  得到次數
			 number = (Integer.valueOf(total_time) / Integer.valueOf("60"))/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_12"));
			}
			//int number = (Integer.valueOf(total_time) / Integer.valueOf("60"))/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_12"));
			
			//遲到扣薪類型
			if ("01".equals(eHF020409T0.get("EHF020409T0_13"))) {// '01'=依時薪比例
				total_count += 1;
				total_Minute += total_time/60;
				//total_money += number * hour_pay * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_14"));
				total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_14")) ,"HALF_UP");
			} else if ("02".equals(eHF020409T0.get("EHF020409T0_13"))) {// '02'=依固定金額
				total_count += 1;
				total_Minute += number/60;
				total_money += number * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_15"));
			}
		} else if ("03".equals(eHF020409T0.get("EHF020409T0_10"))) {// '03'=依次數條件
			
			type_freq = "Y";
			int total_time = 0;// 共遲到幾秒
			int count = 0;// 共遲到幾秒

			for (int i = 0; i < TWO.size(); i++) {
				Map EHF020410T0 = (Map) TWO.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);

				/*if (EHF020410T0_02.equals((String) EHF020410T0.get("EHF020410T0_02"))) {
					EHF020410T0_02 = (String) EHF020410T0.get("EHF020410T0_02");
					eHF020409T0 = this.getEHF020409T0(conn, (String) EHF020410T0.get("EHF020410T0_02"), comp_id);
				}*/
				// 將設定的可遲到分鐘 轉換為秒數
				int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_11")).multiply(new BigDecimal("60"))).toString()));

				if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
					// 當遲到時間小於等於設定時間  不算遲到  跳過此筆資料 
					continue;
				}
				//共遲到幾秒
				total_time += Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07"));
				count+=1;
			}
			//int number = Integer.valueOf(count)/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_12"));
			int number=0;
			if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_12"))){
				number=0;
			}else{
			// (早退秒數/60)/系統設定的 早退分鐘  得到次數
			 number =  Integer.valueOf(count)/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_12"));
			}
			//遲到扣薪類型
			if ("01".equals(eHF020409T0.get("EHF020409T0_13"))) {// '01'=依時薪比例
				//total_count += 1;
				total_count = count;
				total_Minute += total_time/60;
				//total_money += number * hour_pay * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_14"));
				total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_14")) ,"HALF_UP");
			} else if ("02".equals(eHF020409T0.get("EHF020409T0_13"))) {// '02'=依固定金額
				//total_count += 1;
				total_count = count;
				total_Minute += total_time/60;
				total_money += number * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_15"));
			}
		}
		return_map.put("time", 	type_time);
		return_map.put("freq", 	type_freq);
		return_map.put("total_count", 	total_count+"");
		return_map.put("total_Minute", 	total_Minute+"");
		return_map.put("total_money", 	total_money+"");
		return return_map;
	}
		
	

	
	
	/**
	 * 計算員工曠職分鐘/次數與扣薪金額。
	 * @param two
	 * @param comp_id 
	 */
	private Map getAttAbsenteeismInf(Connection conn, List THREE, String comp_id) {
		// TODO Auto-generated method stub
		Map return_map = new HashMap<String, String>();
		int total_count = 0; // 遲到總次數
		int total_Minute = 0; // 遲到總分鐘
		int total_money = 0; // 遲到總金額
		int hour_pay=0;
		String type_time = "";
		String type_freq = "";
		//String EHF020410T0_02 = "3";// 暫存 規則序號(遲到早退曠職)
		//Map eHF020409T0 = this.getEHF020409T0(conn, EHF020410T0_02, comp_id);
		Map eHF020409T0 = this.getEHF020409T0(conn, comp_id);

		if ("01".equals(eHF020409T0.get("EHF020409T0_16"))) {// '01'=依每次分鐘條件
			
			type_time = "Y";
			
			for (int i = 0; i < THREE.size(); i++) {
				Map EHF020410T0 = (Map) THREE.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);

				/*if (EHF020410T0_02.equals((String) EHF020410T0.get("EHF020410T0_02"))) {
					EHF020410T0_02 = (String) EHF020410T0.get("EHF020410T0_02");
					eHF020409T0 = this.getEHF020409T0(conn, (String) EHF020410T0.get("EHF020410T0_02"), comp_id);
				}*/
																
				// 將設定的可遲到分鐘 轉換為秒數
				//int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_17")).multiply(new BigDecimal("60"))).toString()));
				
				//if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
				//	// 當遲到時間小於等於設定時間 不算遲到  跳過此筆資料
				//	continue;
				//}
								
				int number=0;
				if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"))){
					number=0;
				}else{
				// (早退秒數/60)/系統設定的 早退分鐘  得到次數
				 number =  (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"))
		            / Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"));
				}
				// (遲到秒數/60)/系統設定的 遲到分鐘  得到次數
				//int number = (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"))
				//            / Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"));
				
				//遲到扣薪類型
				if ("01".equals(eHF020409T0.get("EHF020409T0_19"))) {// '01'=依時薪比例
					total_count += 1;
					total_Minute += (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"));
					//total_money += number * hour_pay * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_20"));
					total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_20")) ,"HALF_UP");
				} else if ("02".equals(eHF020409T0.get("EHF020409T0_19"))) {// '02'=依固定金額
					total_count += 1;
					total_Minute += (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) / Integer.valueOf("60"));
					total_money += number* Integer.valueOf((String) eHF020409T0.get("EHF020409T0_21"));
				}
			}
		} else if ("02".equals(eHF020409T0.get("EHF020409T0_16"))) {// '02'=依累計分鐘條件
			
			type_time = "Y";
			int total_time = 0;// 共遲到幾秒

			for (int i = 0; i < THREE.size(); i++) {
				Map EHF020410T0 = (Map) THREE.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);

				/*if (EHF020410T0_02.equals((String) EHF020410T0.get("EHF020410T0_02"))) {
					EHF020410T0_02 = (String) EHF020410T0.get("EHF020410T0_02");
					eHF020409T0 = this.getEHF020409T0(conn, (String) EHF020410T0.get("EHF020410T0_02"), comp_id);
				}*/
								
				// 將設定的可遲到分鐘 轉換為秒數
				//int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_17")).multiply(new BigDecimal("60"))).toString()));

				//if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
					// 當遲到時間小於等於設定時間  不算遲到  跳過此筆資料 
				//	continue;
				//}
																
				//遲到總秒數
				total_time += Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07"));

			}
			//int number = (Integer.valueOf(total_time) / Integer.valueOf("60"))/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"));
			int number=0;
			if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"))){
				number=0;
			}else{
			// (早退秒數/60)/系統設定的 早退分鐘  得到次數
			 number =  (Integer.valueOf(total_time) / Integer.valueOf("60"))/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"));
			}
			//遲到扣薪類型
			if ("01".equals(eHF020409T0.get("EHF020409T0_19"))) {// '01'=依時薪比例
				//total_count += 1;
				total_Minute += total_time/60;
				//total_money += number * hour_pay * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_20"));
				total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_20")) ,"HALF_UP");
			} else if ("02".equals(eHF020409T0.get("EHF020409T0_19"))) {// '02'=依固定金額
				//total_count += 1;
				total_Minute += number/60;
				total_money += number * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_21"));
			}
		} else if ("03".equals(eHF020409T0.get("EHF020409T0_16"))) {// '03'=依次數條件
			
			type_freq = "Y";
			int total_time = 0;// 共遲到幾秒
			int count = 0;// 共遲到幾秒

			for (int i = 0; i < THREE.size(); i++) {
				Map EHF020410T0 = (Map) THREE.get(i);
				// 取得員工的時薪
				hour_pay = this.getEmpHourPay(conn, 1, (String) EHF020410T0.get("EHF020410T0_01"), this.sta_costyymm,this.sta_salyymm, this.sta_comp_id);

				/*if (EHF020410T0_02.equals((String) EHF020410T0.get("EHF020410T0_02"))) {
					EHF020410T0_02 = (String) EHF020410T0.get("EHF020410T0_02");
					eHF020409T0 = this.getEHF020409T0(conn, (String) EHF020410T0.get("EHF020410T0_02"), comp_id);
				}*/
				// 將設定的可遲到分鐘 轉換為秒數
				//int Setting_time = Integer.valueOf(((new BigDecimal((String) eHF020409T0.get("EHF020409T0_17")).multiply(new BigDecimal("60"))).toString()));

				//if (Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07")) <= Setting_time) {
				//	// 當遲到時間小於等於設定時間  不算遲到  跳過此筆資料 
				//	continue;
				//}

				//共遲到幾秒
				total_time += Integer.valueOf((String) EHF020410T0.get("EHF020410T0_07"));
				count+=1;
			}
			//int number = Integer.valueOf(count)/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"));
			int number=0;
			if(0==Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"))){
				number=0;
			}else{
			// (早退秒數/60)/系統設定的 早退分鐘  得到次數
			 number =   Integer.valueOf(count)/ Integer.valueOf((String) eHF020409T0.get("EHF020409T0_18"));
			}
			//遲到扣薪類型
			if ("01".equals(eHF020409T0.get("EHF020409T0_19"))) {// '01'=依時薪比例
				//total_count += 1;
				total_count = count;
				total_Minute += total_time/60;
				total_money += tools.fixNumByMode(number * hour_pay * Float.valueOf((String) eHF020409T0.get("EHF020409T0_20")) ,"HALF_UP")    ;
			} else if ("02".equals(eHF020409T0.get("EHF020409T0_19"))) {// '02'=依固定金額
				//total_count += 1;
				total_count = count;
				total_Minute += total_time/60;
				total_money += number * Integer.valueOf((String) eHF020409T0.get("EHF020409T0_21"));
			}
		}
		return_map.put("time", 	type_time);
		return_map.put("freq", 	type_freq);
		return_map.put("total_count", 	total_count+"");
		return_map.put("total_Minute", 	total_Minute+"");
		return_map.put("total_money", 	total_money+"");
		return return_map;
	}
		
	
	
	
	/**
	 * 取得全勤獎金設定明細。 20131007新增  BY 賴泓錡
	 * @param conn
	 * @param EHF030200T0_20
	 * @param comp_id
	 * @return
	 */
	//private Map getEHF020409T0(Connection conn, String EHF020410T0_02, String comp_id) {
	private Map getEHF020409T0(Connection conn, String comp_id) {
		// TODO Auto-generated method stub
		
		EHF020409T0 =new HashMap<String, String>();
		try {
		String sql =
			" SELECT   * " +
			" FROM EHF020409T0" +
			" WHERE 1 = 1" +
			//" AND EHF020409T0_01='"+EHF020410T0_02+"'"+
			" AND EHF020409T0_23='"+comp_id+"'";
		
			// 執行SQL
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			if (rs.next()) {

				// 將全勤獎金資料放入return_map中
				EHF020409T0.put("EHF020409T0_01", 	rs.getString("EHF020409T0_01"));// 遲到早退曠職資料序號
				EHF020409T0.put("EHF020409T0_03", 	rs.getString("EHF020409T0_03"));// 是否啟用
				EHF020409T0.put("EHF020409T0_04", 	rs.getString("EHF020409T0_04"));// 遲到判斷條件類型
				EHF020409T0.put("EHF020409T0_05", 	rs.getString("EHF020409T0_05"));// 遲到分鐘/次數
				EHF020409T0.put("EHF020409T0_06", 	rs.getString("EHF020409T0_06"));// 遲到扣薪單位(分鐘/次數)
				EHF020409T0.put("EHF020409T0_07", 	rs.getString("EHF020409T0_07"));// 遲到扣薪類型										
				EHF020409T0.put("EHF020409T0_08", 	rs.getString("EHF020409T0_08"));// 遲到扣薪倍數
				EHF020409T0.put("EHF020409T0_09", 	rs.getString("EHF020409T0_09"));// 遲到扣薪金額
				EHF020409T0.put("EHF020409T0_10", 	rs.getString("EHF020409T0_10"));// 早退判斷條件類型
				EHF020409T0.put("EHF020409T0_11", 	rs.getString("EHF020409T0_11"));// 早退分鐘/次數
				EHF020409T0.put("EHF020409T0_12", 	rs.getString("EHF020409T0_12"));// 早退扣薪單位(分鐘/次數)
				EHF020409T0.put("EHF020409T0_13", 	rs.getString("EHF020409T0_13"));// 早退扣薪類型
				EHF020409T0.put("EHF020409T0_14", 	rs.getString("EHF020409T0_14"));// 早退扣薪倍數
				EHF020409T0.put("EHF020409T0_15", 	rs.getString("EHF020409T0_15"));// 早退扣薪金額
				EHF020409T0.put("EHF020409T0_16", 	rs.getString("EHF020409T0_16"));// 曠職判斷條件類型
				EHF020409T0.put("EHF020409T0_17", 	rs.getString("EHF020409T0_17"));// 曠職判斷時數條件
				EHF020409T0.put("EHF020409T0_18", 	rs.getString("EHF020409T0_18"));// 曠職扣薪單位(小時/次數)
				EHF020409T0.put("EHF020409T0_19", 	rs.getString("EHF020409T0_19"));// 曠職扣薪類型
				EHF020409T0.put("EHF020409T0_20", 	rs.getString("EHF020409T0_20"));// 曠職扣薪倍數
				EHF020409T0.put("EHF020409T0_21", 	rs.getString("EHF020409T0_21"));// 曠職扣薪金額
				EHF020409T0.put("EHF020409T0_22", 	rs.getString("EHF020409T0_22"));// 備註
				EHF020409T0.put("EHF020409T0_23", 	rs.getString("EHF020409T0_23"));// 公司代碼
				EHF020409T0.put("EHF020409T0_24", 	rs.getString("EHF020409T0_24"));// 是否記錄下班與加班上班刷卡
				EHF020409T0.put("EHF020409T0_25", 	rs.getString("EHF020409T0_25"));// 是否補足遲到時數
			} else {
				EHF020409T0.put("EHF020409T0_03", "0");// 是否啟用
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		
		
		return EHF020409T0;
	}
	/**
	 * 檢查考勤是否已經確認
	 * @param conn
	 * @param costYYMM
	 * @param compId
	 * @return
	 */
	private boolean chack(Connection conn, String costYYMM, String compId) {
		// TODO Auto-generated method stub
		boolean check=false;
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02 " +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			" AND EHF020900T0_M2 = '"+costYYMM+"'"+	//薪資年月
			" AND EHF020900T0_04 = '"+compId+"'"; //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//有考勤資料
				if(Integer.valueOf((String)rs.getString("EHF020900T0_02"))==2||Integer.valueOf((String)rs.getString("EHF020900T0_02"))==3){
					check=true;
				}	
			}
			rs.close();
			stmt.close();
		
		}catch(Exception e){
			System.out.println(" EHF030600M0F.validate() "+e);
			e.printStackTrace();
		}
		return check;
	}
	
	
	private String chk_number(Connection conn, String EHF030600T0_U, String comp_id) {
		// TODO Auto-generated method stub
		
		String number="";
		try{
			String sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2 " +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			" AND EHF020900T0_03FK = '"+EHF030600T0_U+"'"+	//考勤年月
			" AND EHF020900T0_04 = '"+comp_id+"'"; //公司代碼
		
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				number+=rs.getString("EHF020900T0_01");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return number;
		
		
	}
	
	/**
	 * 搜尋員工該薪資年月所有的加班日期(確實有加班單與加班考勤)
	 * @param conn
	 * @param EHF030600T0_U
	 * @param comp_id
	 * @return
	 */
	private Map getovertime( Connection conn, String employee_id ){
		
		Map overtime = new HashMap();
		
		try{
			String sql = "" +
			" SELECT EHF030603T0_U AS HA_OVERTIME_UID, EHF030603T1_04 AS HA_OVERTIME_MONEY ,EHF030603T1_DATE" +
			" FROM EHF030603T1 " +
			" LEFT JOIN EHF030603T0 ON EHF030603T0_U = EHF030603T1_U AND EHF030603T0_07 = EHF030603T1_07 " +
			" WHERE 1=1 " +
			" AND EHF030603T0_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF030603T0_M = '"+this.sta_costyymm+"' " +  //入扣帳年月
			" AND EHF030603T0_M1 = '"+this.sta_salyymm+"' " +  //薪資年月
			" AND EHF030603T0_SCP IN ('01','02') " +  //薪資處理狀態
			" AND EHF030603T1_07 = '"+this.sta_comp_id+"' " +  //公司代碼
			"";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				
				overtime.put((String)rs.getString("EHF030603T1_DATE"), (String)rs.getString("HA_OVERTIME_MONEY"));
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e ){
			e.printStackTrace();
		}
		
		return overtime;	
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
	 * 過濾離職員工(依照薪資年月判定是否需要計算薪資)20140917  新增By Alvin
	 * @param EMPLOYEE_ID
	 * @param start_date
	 * @param end_date
	 * @param empInfMap
	 * @return
	 */
	protected boolean getEmployee_idList0(Connection conn,String EMPLOYEE_ID, String salYYMM ){
		Map tempMap= new HashMap();
		
		boolean chk_run_flag = true;//時間開關
		boolean chk_Show_flag = false;//是否列入員工清單
		
		try{
			String sql = "" +
			" SELECT EHF010100T0_27 , EHF010100T0_28 " +//到職日、離職日
			" FROM EHF010100T0 " +
			" WHERE 1=1 " +
			" AND EHF010100T0_01 = '"+EMPLOYEE_ID+"' " +  //員工工號
			" AND HR_CompanySysNo = '"+this.sta_comp_id+"' " +  //公司代碼
			"";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()){
				
				
				String start =rs.getString("EHF010100T0_27").replace("/", "");
				
				String end   =rs.getString("EHF010100T0_28").replace("/", "");
				
				
				
				
				//離職判斷	
				if((rs.getString("EHF010100T0_28").substring(0,6)).equals(salYYMM)){
					//如果是當月離職，則要計算薪資(破月)
					chk_Show_flag= true;
				}else if(Integer.valueOf(start)<Integer.valueOf(salYYMM)){
					
					
					
				}
				
					
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e ){
			e.printStackTrace();
		}

		return chk_Show_flag;	
	}
	
	
	
	
	/**
	 * 過濾離職員工(依照薪資年月判定是否需要計算薪資)20140917  新增By Alvin
	 * @param EMPLOYEE_ID
	 * @param start_date
	 * @param end_date
	 * @param empInfMap
	 * @return
	 */
	protected boolean getEmployee_idList(String EMPLOYEE_ID, String start_date , String end_date ,Map empInfMap){
		
		
		HR_TOOLS hr_tools = new HR_TOOLS();
		Map tempMap= new HashMap();
		Calendar start_cal = tools.covertStringToCalendar(start_date);  
		Calendar end_cal = tools.covertStringToCalendar(end_date);  
		boolean chk_run_flag = true;//時間開關
		boolean chk_Show_flag = false;//是否列入員工清單
		
		//取得EMS-Flow員工基本資料Map
		try {
			empInfMap = hr_tools.getEmpInfMapByEmpId(this.sta_user_id,EMPLOYEE_ID, this.sta_comp_id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
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
				chk_Show_flag=true;
				continue;	
			}
			//日期加一天
			start_cal.add(Calendar.DAY_OF_MONTH, 1);
		}
			
		
		hr_tools.close();
		return chk_Show_flag;	
	}
	/**
	 * 判斷該員工是否需要產生排班表資料(依據是否在職進行判斷)20140917  新增By Alvin
	 * @param user  取得EMS-Flow員工基本資料Map
	 * @param determine_date
	 * @return
	 */
	protected boolean determineGenerateSchedulingData( Map user, String determine_date ){
		//ADD by joe 2012/10/09
		boolean chk_flag = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date trialTime=null;
		try{
			Calendar determine_cal = tools.covertStringToCalendar(determine_date);
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
	
	
	
}


