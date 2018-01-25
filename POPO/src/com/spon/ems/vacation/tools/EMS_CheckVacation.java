package com.spon.ems.vacation.tools;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.salary.forms.EHF030600M2F;
import com.spon.ems.vacation.forms.EHF020100M0F;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_GetEmsFlowInf;
import com.spon.utils.util.HR_TOOLS;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;


import org.apache.log4j.Logger;

import vtgroup.ems.common.vo.AuthorizedBean;


/**
 * EMS 請假區間檢核元件
 * 
 * @version 1.0
 * @created 18-十月-2010 下午 06:00:47
 */
public class EMS_CheckVacation {
	

	static Logger loger = Logger.getLogger(EMS_CheckVacation.class.getName());
	private List<Map<String,String>> vacation_time_list = new ArrayList<Map<String,String>>();	//EMS產品新增參數 20130909 by Hedwig
	private volatile static EMS_CheckVacation check_vacation_tools; 
	BA_TOOLS tools = BA_TOOLS.getInstance();
	
	public static EMS_CheckVacation getInstance() {
        /*if (check_vacation_tools == null)
        	check_vacation_tools = new EMS_CheckVacation();*/
        if(check_vacation_tools == null) {
            synchronized(EMS_CheckVacation.class) {
            	check_vacation_tools = new EMS_CheckVacation();
            }
        }
        return check_vacation_tools;
    }

	private EMS_CheckVacation(){

	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * 檢核代理人一天只能代理一次，若代理多天假期，代理期間亦不可代理其他假單
	 * @param errors
	 * @param request
	 * @param conn
	 * @param start_date
	 * @param start_time
	 * @param end_date
	 * @param end_time
	 */
	public void checkAgent(ActionErrors errors,HttpServletRequest request,Connection conn,
			               String agent, String vacation_id,String start_date,String end_date,String msg){
		
		try{
			boolean agent_flag = true;
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT EHF020200T0_01,EHF020200T0_07" +
				   " FROM EHF020200T0" +
				   " WHERE 1=1" +
				   " AND EHF020200T0_07 = '"+agent+"' " +
				   " AND EHF020200T0_16 <> '0009' " +
				   " AND '"+start_date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10" +
				   " AND '"+end_date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10"+
				   " AND '"+vacation_id +"' != EHF020200T0_01";
			
//			System.out.println("Agent:"+sql);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				agent_flag = false;
			}

//				msg=msg.equals("")?"代理人無法代理":msg;
				  if(!agent_flag)
			        {
					  errors.add("EHF020200T0_07_SHOW",new ActionMessage(msg));
			        }

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 檢核請假期間是否有例假日問題
	 * @param errors
	 * @param request
	 * @param conn
	 * @param start_date
	 * @param start_time
	 * @param end_date
	 * @param end_time
	 */
	public void vacation_holiday_validate(ActionErrors errors, HttpServletRequest request, Connection conn, String start_date, String start_time,
				String end_date, String end_time ){
		
		try{
			String now_year = tools.getDateADYear(start_date, false);
			boolean tmp_flag = true;
			
			Statement stmt = conn.createStatement();
			String sql = "";
			
			//先檢查請假(起)
			sql += " SELECT EHF010200T0_03, DATE_FORMAT(EHF010200T0_05, '%Y/%m/%d') AS EHF010200T0_05, " +
				   " DATE_FORMAT(EHF010200T0_06, '%Y/%m/%d') AS EHF010200T0_06, EHF010200T0_07, EHF010200T0_08 " +
				   " FROM EHF010200T0 " +
				   " WHERE 1=1 " +
				   " AND EHF010200T0_03 = '"+now_year+"' " +
				   " AND '"+start_date+"' BETWEEN EHF010200T0_05 AND EHF010200T0_06 " +
				   " AND EHF010200T0_11 = '"+request.getAttribute("compid")+"' " ;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				if(!this.checkPass(errors, request, start_date, start_time, rs.getString("EHF010200T0_05"), rs.getString("EHF010200T0_07").replaceAll(":", ""),
						rs.getString("EHF010200T0_06"),  rs.getString("EHF010200T0_08").replaceAll(":", ""))){
					tmp_flag = false;
					break;
				}
			}
			
			if(tmp_flag){
				//再檢查請假(迄)
				sql = " SELECT EHF010200T0_03, DATE_FORMAT(EHF010200T0_05, '%Y/%m/%d') AS EHF010200T0_05, " +
					  " DATE_FORMAT(EHF010200T0_06, '%Y/%m/%d') AS EHF010200T0_06, EHF010200T0_07, EHF010200T0_08 " +
					  " FROM EHF010200T0 " +
					  " WHERE 1=1 " +
					  " AND EHF010200T0_03 = '"+now_year+"' " +
					  " AND '"+end_date+"' BETWEEN EHF010200T0_05 AND EHF010200T0_06 " +
					  " AND EHF010200T0_11 = '"+request.getAttribute("compid")+"' " ;
		
				rs = stmt.executeQuery(sql);
		
				while(rs.next()){
			
					if(!this.checkPass(errors, request, end_date, end_time, rs.getString("EHF010200T0_05"), rs.getString("EHF010200T0_07").replaceAll(":", ""),
							rs.getString("EHF010200T0_06"),  rs.getString("EHF010200T0_08").replaceAll(":", ""))){
						break;
					}
				}
			}
			rs.close();
			stmt.close();
	
		}catch (SQLException e) {
			System.out.println("EMS_CheckVacation.vacation_validate()" + e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 判斷傳入的日期與時間是否在例假日內  return boolean,  true ==> 檢核通過, false ==> 檢核失敗
	 * @param errors
	 * @param request
	 * @param date
	 * @param time
	 * @param ho_start_date
	 * @param ho_start_time
	 * @param ho_end_date
	 * @param ho_end_time
	 * @return
	 */
	private boolean checkPass(ActionErrors errors, HttpServletRequest request, String date, String time, String ho_start_date, String ho_start_time,
					String ho_end_date, String ho_end_time ){
		
		boolean flag = true;
		try{			
			int itime = tools.TimeToSecs(time); //需檢核日期的時間
			int iho_start_time = tools.TimeToSecs(ho_start_time);  //例假日(起)時間
			int iho_end_time = tools.TimeToSecs(ho_end_time);  //例假日(迄)時間
			
			if(date.equals(ho_start_date) && !date.equals(ho_end_date)){
				if(itime <= iho_start_time){
					//檢核通過
					flag = true;
				}else{
					//檢核失敗
					errors.add("EHF020200T0_09",new ActionMessage(""));
					errors.add("EHF020200T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							" "+ho_start_date+" "+ho_start_time.substring(0, 2)+"點"+ho_start_time.substring(2, 4)+"分  ~ " + 
							" "+ho_end_date+" "+ho_end_time.substring(0, 2)+"點"+ho_end_time.substring(2, 4)+"分  為例假日，請調整請假區間");
					flag = false;
				}
			}else if(date.equals(ho_end_date) && !date.equals(ho_start_date) ){
				if(itime >= iho_end_time){
					//檢核通過
					flag = true;
				}else{
					//檢核失敗
					errors.add("EHF020200T0_09",new ActionMessage(""));
					errors.add("EHF020200T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							" "+ho_start_date+" "+ho_start_time.substring(0, 2)+"點"+ho_start_time.substring(2, 4)+"分  ~ " + 
							" "+ho_end_date+" "+ho_end_time.substring(0, 2)+"點"+ho_end_time.substring(2, 4)+"分  為例假日，請調整請假區間");
					flag = false;
				}
			}else if(date.equals(ho_end_date) && date.equals(ho_start_date) ){
				if(itime <= iho_start_time || itime >= iho_end_time){
					//檢核通過
					flag = true;
				}else{
					//檢核失敗
					errors.add("EHF020200T0_09",new ActionMessage(""));
					errors.add("EHF020200T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							" "+ho_start_date+" "+ho_start_time.substring(0, 2)+"點"+ho_start_time.substring(2, 4)+"分  ~ " + 
							" "+ho_end_date+" "+ho_end_time.substring(0, 2)+"點"+ho_end_time.substring(2, 4)+"分  為例假日，請調整請假區間");
					flag = false;
				}
			}else{
				//檢核失敗
				errors.add("EHF020200T0_09",new ActionMessage(""));
				errors.add("EHF020200T0_10",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						" "+ho_start_date+" "+ho_start_time.substring(0, 2)+"點"+ho_start_time.substring(2, 4)+"分  ~ " + 
						" "+ho_end_date+" "+ho_end_time.substring(0, 2)+"點"+ho_end_time.substring(2, 4)+"分  為例假日，請調整請假區間");
				flag = false;
			}
			
		}catch(Exception e){
			System.out.println("EMS_CheckVacation.checkPass()" + e);
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 檢核請假日期與時間是否符合邏輯  return void
	 * @param errors
	 * @param request
	 * @param conn
	 * @param start_date
	 * @param start_time
	 * @param end_date
	 * @param end_time
	 */
	public void checkVacationDateTime(ActionErrors errors, HttpServletRequest request, Connection conn, String employee_id,
									  String start_date, String start_time,
									  String end_date, String end_time, String form_type ){
		
		EMS_GetEmsFlowInf emsflowinf_tools = EMS_GetEmsFlowInf.getInstance();
		
		try{
			//設定 authbean
			AuthorizedBean authbean = emsflowinf_tools.getAuthbean(request);
			
			//判斷請假區間檢核模式
			String ems_vacation_validate_mode = this.tools.getSysParam(conn, authbean.getCompId(), "EMS_VACATION_VALIDATE_MODE");
			if("BY_EMP_CLASS".equals(ems_vacation_validate_mode)){
				//檢核請假日期與時間是否符合邏輯  return void
				this.validateVacationDateTime(errors, request, conn, employee_id, start_date, start_time, end_date, end_time, form_type);
			}else if("BY_VACATION_FORM_DATA".equals(ems_vacation_validate_mode)){
				//不需檢核
			}else{
				//不需檢核
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 檢核請假日期與時間是否符合邏輯  return void
	 * @param errors
	 * @param request
	 * @param conn
	 * @param employee_id
	 * @param start_date
	 * @param start_time
	 * @param end_date
	 * @param end_time
	 * @param form_type
	 */
	public void validateVacationDateTime(ActionErrors errors, HttpServletRequest request, Connection conn, String employee_id,
			  String start_date, String start_time,
			  String end_date, String end_time, String form_type ){
		
		EMS_GetEmsFlowInf emsflowinf_tools = EMS_GetEmsFlowInf.getInstance();
		
		try{
			Calendar start_cal = tools.covertStringToCalendar(start_date);
			Calendar end_cal = tools.covertStringToCalendar(end_date);
			int istart_time = tools.TimeToSecs(start_time);
			int iend_time = tools.TimeToSecs(end_time);			
			//設定 authbean
			AuthorizedBean authbean = emsflowinf_tools.getAuthbean(request);
			String temp_emp = authbean.getEmployeeID();
			authbean.setEmployeeID(employee_id);
			//取得班別資訊
			HR_TOOLS hr_tools = new HR_TOOLS();
			Map empclass = hr_tools.getEmpClass(conn, emsflowinf_tools.getAuthbean(request));
			hr_tools.close();
			
			authbean.setEmployeeID(temp_emp);
			
			int work_start_time = tools.TimeToSecs((String)empclass.get("WORK_START_TIME"));  //上班時間
			int work_end_time = tools.TimeToSecs((String)empclass.get("WORK_END_TIME"));  //下班時間
			
			//判斷是否跨午夜十二時
			if(istart_time < work_start_time){
				istart_time = istart_time + (24*60*60);
			}
			if(iend_time < work_start_time){
				iend_time = iend_time + (24*60*60);
			}
			if(work_end_time < work_start_time){
				work_end_time = work_end_time + (24*60*60);
			}
			
			int flag;
			flag = start_cal.compareTo(end_cal);
			if(flag <= 0){

				if(flag == 0){
					
					if(!this.checkTimeRange(istart_time, work_start_time, work_end_time) || !this.checkTimeRange(iend_time, work_start_time, work_end_time)){
						//檢核失敗
						if("1".equals(form_type)){
							errors.add("EHF020200T0_09",new ActionMessage(""));
							errors.add("EHF020200T0_10",new ActionMessage(""));
							request.setAttribute("ErrMSG",
									(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
									"請假起始時間與請假結束時間必須在上下班時間內!!請再次確認!!");
						}else if("2".equals(form_type)){
							errors.add("EHF020300T0_09",new ActionMessage(""));
							errors.add("EHF020300T0_10",new ActionMessage(""));
							request.setAttribute("ErrMSG",
									(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
									"出差起始時間與出差結束時間必須在上下班時間內!!請再次確認!!");
						}
					}else{
						if(istart_time <= iend_time){
							//檢核通過
						}else{
							//檢核失敗
							if("1".equals(form_type)){
								errors.add("EHF020200T0_09",new ActionMessage(""));
								errors.add("EHF020200T0_10",new ActionMessage(""));
								request.setAttribute("ErrMSG",
										(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
										"請假日期為當天，請假起始時間必須大於等於請假結束時間!!請再次確認!!");
							}else if("2".equals(form_type)){
								errors.add("EHF020300T0_09",new ActionMessage(""));
								errors.add("EHF020300T0_10",new ActionMessage(""));
								request.setAttribute("ErrMSG",
										(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
										"出差日期為當天，出差起始時間必須大於等於出差結束時間!!請再次確認!!");
							}
						}
					}
				}else if (flag < 0){
					if(!this.checkTimeRange(istart_time, work_start_time, work_end_time) || !this.checkTimeRange(iend_time, work_start_time, work_end_time)){
						//檢核失敗
						if("1".equals(form_type)){
							errors.add("EHF020200T0_09",new ActionMessage(""));
							errors.add("EHF020200T0_10",new ActionMessage(""));
							request.setAttribute("ErrMSG",
									(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
									"請假起始時間與請假結束時間必須在上下班時間內!!請再次確認!!");
						}else if("2".equals(form_type)){
							errors.add("EHF020300T0_09",new ActionMessage(""));
							errors.add("EHF020300T0_10",new ActionMessage(""));
							request.setAttribute("ErrMSG",
									(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
									"出差起始時間與出差結束時間必須在上下班時間內!!請再次確認!!");
						}
					}
				}
				
			}else{
				//檢核失敗
				if("1".equals(form_type)){
					errors.add("EHF020200T0_09",new ActionMessage(""));
					errors.add("EHF020200T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"請假起始日期必須大於等於請假結束日期!!請再次確認!!");
				}else if("2".equals(form_type)){
					errors.add("EHF020300T0_09",new ActionMessage(""));
					errors.add("EHF020300T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"出差起始日期必須大於等於出差結束日期!!請再次確認!!");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 檢核傳入時間是否在工作區間內   在工作區間內，檢核通過 return true ,  不在工作區間內，檢核失敗 return false ,   return boolean
	 * @param time
	 * @param work_start_time
	 * @param work_end_time
	 * @return
	 */
	private boolean checkTimeRange(int time, int work_start_time, int work_end_time ){
		
		boolean flag = true;
		try{
			if(time < work_start_time || time > work_end_time){
				//檢核失敗
				flag = false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 檢核單據是否跨年度或跨月份申請
	 * @param errors
	 * @param request
	 * @param conn
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 */
	public void checkVacationYEARMONTH(ActionErrors errors, HttpServletRequest request, String start_date, String end_date){
		
		try{
			Calendar start_cal = tools.covertStringToCalendar(start_date);
			Calendar end_cal = tools.covertStringToCalendar(end_date);
			
			//檢核是否有跨年度申請
			if(!(start_cal.get(Calendar.YEAR) == end_cal.get(Calendar.YEAR))){
				//當年度不相等, 表示跨年度申請
				//但若是跨年度的第一天可允許, 因考慮到跨午夜班別的問題
				if(!(end_cal.get(Calendar.MONTH) == Calendar.JANUARY && end_cal.get(Calendar.DAY_OF_MONTH) == 1)){
					errors.add("EHF020200T0_09",new ActionMessage(""));
					errors.add("EHF020200T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"本單據不允許跨年度申請!!請再次確認!!");
				}
			}else{
				//檢核是否有跨月份申請
				if(!(start_cal.get(Calendar.MONTH) == end_cal.get(Calendar.MONTH))){
					//當月份不相等, 表示跨月份申請
					//但若是跨月份的第一天可允許, 因考慮到跨午夜班別的問題
					start_cal.add(Calendar.MONTH, 1);
					if(!(start_cal.get(Calendar.MONTH) != end_cal.get(Calendar.MONTH) && end_cal.get(Calendar.DAY_OF_MONTH) == 1)){
						errors.add("EHF020200T0_09",new ActionMessage(""));
						errors.add("EHF020200T0_10",new ActionMessage(""));
						request.setAttribute("ErrMSG",
								(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
								"本單據不允許跨月份申請!!請再次確認!!");
					}
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 判斷是否有請假單衝突
	 * @param errors
	 * @param request
	 * @param conn
	 * @param employee_id
	 * @param start_date
	 * @param start_time
	 * @param end_date
	 * @param end_time
	 * @param comp_id
	 */
	public void checkConflictVacationForm( ActionErrors errors, HttpServletRequest request,
										   Connection conn, String employee_id, 
			   							   String start_date, String start_time, 
			   							   String end_date, String end_time ){
		
		try{
			//判斷是否有請假單衝突產生
			Map conflict_map = this.checkConflictVacationForm(conn, employee_id, 
							   start_date, start_time, end_date, end_time, (String)request.getAttribute("compid"));
			
			if((Boolean)conflict_map.get("CHK_FLAG")){
				//表示有請假單衝突發生
				errors.add("EHF020200T0_09",new ActionMessage(""));
				errors.add("EHF020200T0_11_HH",new ActionMessage(""));
				errors.add("EHF020200T0_11_MM",new ActionMessage(""));
				errors.add("EHF020200T0_10",new ActionMessage(""));
				errors.add("EHF020200T0_12_HH",new ActionMessage(""));
				errors.add("EHF020200T0_12_MM",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"日期區間與請假單(單號:"+(String)conflict_map.get("FORM_KEY")+")衝突!!, 請再次確認!!");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 執行請假單衝突檢核
	 * @param conn
	 * @param employee_id
	 * @param start_date
	 * @param start_time
	 * @param end_date
	 * @param end_time
	 * @param comp_id
	 * @return
	 */
	public Map checkConflictVacationForm( Connection conn, String employee_id, 
										   String start_date, String start_time, 
										   String end_date, String end_time, String comp_id ){
		
		Map return_map = new HashMap();
		
		try{
			String sql = "" +
			" SELECT * " +
			" FROM ( " +
			" SELECT " +
			" EHF020200T0_01, EHF020200T0_09, EHF020200T0_11, EHF020200T0_10, EHF020200T0_12, " +
			" CAST(CONCAT(EHF020200T0_09,' ',INSERT(EHF020200T0_11,3,0,':'),':00') AS DATETIME) AS START_DATE, " +
			" CAST(CONCAT(EHF020200T0_10,' ',INSERT(EHF020200T0_12,3,0,':'),':00') AS DATETIME) AS END_DATE, " +
			" CAST(CONCAT('"+start_date+"',' ',INSERT('"+start_time+"',3,0,':'),':00') AS DATETIME) AS NOW_START_DATE, " +
			" CAST(CONCAT('"+end_date+"',' ',INSERT('"+end_time+"',3,0,':'),':00') AS DATETIME) AS NOW_END_DATE " +
			" FROM ( " +
			" SELECT * FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_03 = '"+employee_id+"' " +  //員工工號
			" AND EHF020200T0_16 = '0006' " +  //表單狀態  = 完成
			" AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
			
			//同一天可能會有兩張以上的請假單,所以必須依據實際的請假日期時間進去區間判斷, 避免一天只能有一張請假單的狀況發生, edit by joe 2012/09/04
			" AND ( '"+start_date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10 " +  //請假區間判斷
			"      OR '"+end_date+"' BETWEEN EHF020200T0_09 AND EHF020200T0_10 " +
			"      OR EHF020200T0_09 BETWEEN '"+start_date+"' AND '"+end_date+"' " +
			"      OR EHF020200T0_10 BETWEEN '"+start_date+"' AND '"+end_date+"' ) " +
			" ) tableA " +
			" WHERE 1=1 " +
			" ) tableB " +
			" WHERE 1=1 " +
			" AND ( NOW_START_DATE BETWEEN START_DATE AND END_DATE " +
			"      OR NOW_END_DATE BETWEEN START_DATE AND END_DATE " +
			"      OR START_DATE BETWEEN NOW_START_DATE AND NOW_END_DATE " +
			"      OR END_DATE BETWEEN NOW_START_DATE AND NOW_END_DATE ) ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//有找到跟本次衝突的請假單
				return_map.put("CHK_FLAG", true);
				return_map.put("FORM_KEY", rs.getString("EHF020200T0_01"));  //請假單單號
			}else{
				return_map.put("CHK_FLAG", false);
				return_map.put("FORM_KEY", "");  //請假單單號
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 檢核勞基法規則(病假,事假,特休)
	 * @param errors
	 * @param request
	 * @param conn
	 * @param authbean
	 * @param employee_id
	 * @param chiyear
	 * @param apply_date
	 * @param vacation_type
	 * @param vacation_time
	 */
	public void checkLaborLaw(ActionErrors errors, HttpServletRequest request, Connection conn, AuthorizedBean authbean, String employee_id,
				String chiyear, String start_date, String end_date, String vacation_type, String vacation_time ){
		
		try{
			boolean error_flag = false;
			float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
			
			String[] time = vacation_time.split("/");
			
			int day = Integer.parseInt(time[0]);  //天數
			float hours = Float.parseFloat(time[1]);  //時數
			
			float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
			
			//取得員工年度休假時數資訊
			String sql = " SELECT EHF010300T0_07, EHF010300T0_08, EHF020100T0_04 " +
						 " FROM EHF010300T0 " +
						 " LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF010300T0_06 AND EHF020100T0_08 = EHF010300T0_12 " +
						 " WHERE 1=1 " +
						 " AND EHF010300T0_02 = ? " + //年度(yy)
						 " AND EHF010300T0_05 = ? " + //員工工號
						 " AND EHF010300T0_06 = ? " + //假別代號
						 " AND ( ( ? BETWEEN EHF010300T0_09 AND EHF010300T0_10 ) AND ( ? BETWEEN EHF010300T0_09 AND EHF010300T0_10 ) ) " +  //申請日期是否在區間內
						 " AND EHF010300T0_12 = ? ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			
			p6stmt.setString(1, chiyear);
			p6stmt.setString(2, employee_id);
			p6stmt.setString(3, vacation_type);
			p6stmt.setString(4, start_date);
			p6stmt.setString(5, end_date);
			p6stmt.setString(6, authbean.getCompId());
			
			//System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());
			
			ResultSet rs = pstmt.executeQuery();
			
			if(	rs.next())
			{
				if( totalhours <= rs.getFloat("EHF010300T0_08")){
					//該假別剩餘時數大於請假時數，可以請假，檢核通過
					error_flag = false;
				}else{
					//該假別剩餘時數不足，檢核失敗
					error_flag = true;
				}
				
			}else{
				error_flag = true;
			}
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
			//處理檢核未通過訊息
			if(error_flag){
				//該假別剩餘時數不足，檢核失敗
				errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
				errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						rs.getString("EHF020100T0_04")+" 剩餘"+ rs.getFloat("EHF010300T0_08")+"小時, 時數不足!!請再次確認!!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 請假單呈核(簽核)時進行檢核
	 * @param errors
	 * @param request
	 * @param conn
	 * @param EHF020200T0_01
	 * @param comp_id
	 */
	/*
	public void checkVacation(ActionErrors errors, HttpServletRequest request, Connection conn, 
			 				  String EHF020200T0_01, String comp_id){
		
		try{
			
			String sql = "" +
			 " SELECT * , EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
			 " FROM EHF020200T0 " +
			 " WHERE 1=1 " +
			 " AND EHF020200T0_01 = ? " +
			 " AND EHF020200T0_18 = ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);

			p6stmt.setString(1, EHF020200T0_01);  //請假單單號
			p6stmt.setString(2, comp_id);  //公司代碼

			System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());

			ResultSet rs = pstmt.executeQuery();

			//檢核勞基法相關規定(病假,事假,特休,補休 其餘假由人資經辦判定)  -- 呈核時檢核
			if(rs.next()){
				
				if("A03".equals(rs.getString("EHF020200T0_14")) || "A08".equals(rs.getString("EHF020200T0_14"))  ){
					checkva_tools.checkLaborLaw(lc_errors, request, conn, getLoginUser(request), rs.getString("EHF020200T0_03"),
					rs.getString("CHIYEAR"), rs.getString("EHF020200T0_09"), rs.getString("EHF020200T0_10"), rs.getString("EHF020200T0_14"), 
					rs.getString("EHF020200T0_13"));
				}
				
				this.checkVacationHours(errors, request, conn, rs.getString("EHF020200T0_03"), 
				     rs.getString("CHIYEAR"), rs.getString("EHF020200T0_09"), rs.getString("EHF020200T0_10"), rs.getString("EHF020200T0_14"),
					 rs.getString("EHF020200T0_13"), comp_id);
			}

			rs.close();
			pstmt.close();
			p6stmt.close();
					
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	*/
	
	/**
	 * 請假單呈核(簽核)時進行檢核
	 * @param errors
	 * @param request
	 * @param conn
	 * @param EHF020200T0_01
	 * @param comp_id
	 */
	public void checkVacation( ActionErrors errors, HttpServletRequest request, 
							   Connection conn, String EHF020200T0_01, String comp_id){
		
		try{			
			//檢核請假單資料
			Map error_map = this.checkVacationData(conn, EHF020200T0_01, comp_id);
			
			//處理請假單錯誤訊息
			this.handleErrorMSG(errors, request, conn, (Integer)error_map.get("ERROR_TYPE"), (String)error_map.get("VACATION_TYPE"), comp_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 請假單呈核(簽核)時進行檢核
	 * @param conn
	 * @param EHF020200T0_01
	 * @param comp_id
	 * @return
	 */
	public String checkVacation(Connection conn, String EHF020200T0_01, String comp_id){
		
		String MSG = "";
		
		try{			
			//檢核請假單資料
			Map error_map = this.checkVacationData(conn, EHF020200T0_01, comp_id);
			
			//處理請假單錯誤訊息
			MSG = this.generateErrorMSG(conn, (Integer)error_map.get("ERROR_TYPE"), (String)error_map.get("VACATION_TYPE"), comp_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return MSG;
	}
	
	/**
	 * 檢查請假單資料
	 * @param conn
	 * @param EHF020200T0_01
	 * @param comp_id
	 * @return
	 */
	public Map checkVacationData( Connection conn, String EHF020200T0_01, String comp_id){
		
		Map return_Map = new HashMap();
		
		try{
			
			String sql = "" +
			 " SELECT * , EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
			 " FROM EHF020200T0 " +
			 " WHERE 1=1 " +
			 " AND EHF020200T0_01 = ? " +
			 " AND EHF020200T0_18 = ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);

			p6stmt.setString(1, EHF020200T0_01);  //請假單單號
			p6stmt.setString(2, comp_id);  //公司代碼

//			System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());

			ResultSet rs = pstmt.executeQuery();

			//檢核勞基法相關規定(病假,事假,特休,補休 其餘假由人資經辦判定)  -- 呈核時檢核
			int error_type = 0;
			
			if(rs.next()){
				error_type = this.checkVacationHours( conn, rs.getString("EHF020200T0_03"), 
							 rs.getString("CHIYEAR"), rs.getString("EHF020200T0_09"), rs.getString("EHF020200T0_10"), rs.getString("EHF020200T0_14"),
							 rs.getString("EHF020200T0_13"), comp_id);
				
				return_Map.put("ERROR_TYPE", error_type);
				return_Map.put("VACATION_TYPE", rs.getString("EHF020200T0_14"));
			}
			
			rs.close();
			pstmt.close();
			p6stmt.close();
					
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_Map;
	}
	
	/**
	 * 依據假別設定檢核請假單時數相關資訊
	 * @param errors
	 * @param request
	 * @param conn
	 * @param employee_id
	 * @param chiyear
	 * @param start_date
	 * @param end_date
	 * @param vacation_type
	 * @param vacation_time
	 * @param comp_id
	 */
	/*
	public void checkVacationHours(ActionErrors errors, HttpServletRequest request, Connection conn, String employee_id,
			String chiyear, String start_date, String end_date, String vacation_type, String vacation_time, String comp_id ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			boolean error_flag = false;
			int error_type = 0;
			float Day_work_hours = Float.parseFloat(ems_tools.getSysParam(conn, comp_id, "WORK_HOURS"));
			String[] time = vacation_time.split("/");
			int day = Integer.parseInt(time[0]);  //天數
			float hours = Float.parseFloat(time[1]);  //時數
			float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
			float tmp_hours = 0;
			
			//取得系統假別設定資訊
			EHF020100M0F ehf020100m0 = this.getVacationTypeInf(conn, vacation_type, comp_id);
			
			if( ehf020100m0 != null ){
				
				//判斷是否檢核單次最低請假時數
				if(tools.StringToBoolean(ehf020100m0.getEHF020100T0_06_FLG()) && !error_flag){
					//若本次請假總時數小於單次最低請假時數則表示有問題
					if(Float.parseFloat(ehf020100m0.getEHF020100T0_06()) > totalhours){
						error_type = 1;
						error_flag = true;
					}
				}
				
				//判斷是否檢核年度請假 總時數
				if(tools.StringToBoolean(ehf020100m0.getEHF020100T0_05_FLG()) && !error_flag){
					tmp_hours = this.getVacationHoursInFormByVacationType(conn, employee_id, chiyear, vacation_type, comp_id);
					//若以前此一假別的請假單總時數加上本次請假總時數大於年度請假總時數則表示有問題
					if((tmp_hours + totalhours)
						> Float.parseFloat(ehf020100m0.getEHF020100T0_05()) ){
						error_type = 2;
						error_flag = true;
					}
				}
				
				//判斷是否檢核年度假別剩餘時數
				if(tools.StringToBoolean(ehf020100m0.getEHF020100T0_09()) && !error_flag){
					tmp_hours = this.getVacationHoursByVacationType(conn, employee_id, chiyear, start_date, end_date, vacation_type, comp_id);
					//若年度假別剩餘時數小於本次請假總時數則表示有問題
					if(tmp_hours < totalhours){
						error_type = 3;
						error_flag = true;
					}
				}
				
				//處理檢核未通過訊息
				if(error_flag){
					
					switch(error_type){
						
						case 1:
							//該假別時數已超過年度假別請假總時數
							errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
							errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
							errors.add("EHF020200T0_14",new ActionMessage(""));
							request.setAttribute("ErrMSG",
									(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
									"該假別的單次最低請假時數:"+ehf020100m0.getEHF020100T0_06()+"小時, 請再次確認!!");
						break;
						
						case 2:
							//該假別時數已超過年度假別請假總時數
							errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
							errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
							errors.add("EHF020200T0_14",new ActionMessage(""));
							request.setAttribute("ErrMSG",
									(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
									"該假別請假時數已超過年度請假總時數:"+ehf020100m0.getEHF020100T0_05()+"小時, 請再次確認!!");
						break;
						
						case 3:
							//該假別剩餘時數不足，檢核失敗
							errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
							errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
							errors.add("EHF020200T0_14",new ActionMessage(""));
							request.setAttribute("ErrMSG",
									(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
									"假別剩餘"+tmp_hours+"小時, 時數不足!!請再次確認!!");
						break;
						
					}
				}
				
			}else{
				//該假別設定資訊不存在，檢核失敗
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"假別設定資訊不存在!!請再次確認!!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	*/
	
	/**
	 * 檢核請假時數是否有問題
	 * @param conn
	 * @param employee_id
	 * @param chiyear
	 * @param start_date
	 * @param end_date
	 * @param vacation_type
	 * @param vacation_time
	 * @param comp_id
	 * @return
	 */
	public int checkVacationHours( Connection conn, String employee_id, String chiyear, String start_date, String end_date,
								    String vacation_type, String vacation_time, String comp_id ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		int error_type = 0;
		
		try{
			boolean error_flag = false;
			float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
			String[] time = vacation_time.split("/");
			int day = Integer.parseInt(time[0]);  //天數
			float hours = Float.parseFloat(time[1]);  //時數
			float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
			float tmp_hours = 0;
			
			//取得系統假別設定資訊
			EHF020100M0F ehf020100m0 = this.getVacationTypeInf(conn, vacation_type, comp_id);
			
			if( ehf020100m0 != null ){
				String EHF020100T0_06_hourss=(Float.parseFloat(ehf020100m0.getEHF020100T0_06_day()) * Day_work_hours) + Float.parseFloat(ehf020100m0.getEHF020100T0_06_day())+"";
				
				//判斷是否檢核單次最低請假時數
				if(tools.StringToBoolean(ehf020100m0.getEHF020100T0_06_FLG()) && !error_flag){
					//若本次請假總時數小於單次最低請假時數則表示有問題
					if(Float.parseFloat(EHF020100T0_06_hourss) > totalhours){
						error_type = 1;
						error_flag = true;
					}
				}
				String work_hourss=(Float.parseFloat(ehf020100m0.getEHF020100T0_05_day()) * Day_work_hours) + Float.parseFloat(ehf020100m0.getEHF020100T0_05_day())+"";
				//判斷是否檢核年度請假 總時數
				if(tools.StringToBoolean(ehf020100m0.getEHF020100T0_05_FLG()) && !error_flag){
					tmp_hours = this.getVacationHoursInFormByVacationType(conn, employee_id, chiyear, vacation_type, comp_id);
					//若以前此一假別的請假單總時數加上本次請假總時數大於年度請假總時數則表示有問題
					if((tmp_hours + totalhours)	> Float.parseFloat(work_hourss) ){
						error_type = 2;
						error_flag = true;
					}
				}
				/*
				//判斷是否檢核年度假別剩餘時數
				if(tools.StringToBoolean(ehf020100m0.getEHF020100T0_09()) && !error_flag){
					tmp_hours = this.getVacationHoursByVacationType(conn, employee_id, chiyear, start_date, end_date, vacation_type, comp_id);
					//若年度假別剩餘時數小於本次請假總時數則表示有問題
					if(tmp_hours < totalhours){
						error_type = 3;
						error_flag = true;
					}
				}*/
				//判斷是否檢核年度假別剩餘時數
				if(!tools.StringToBoolean(ehf020100m0.getEHF020100T0_10()) && !error_flag){
					tmp_hours = this.getVacationHoursByVacationType(conn, employee_id, chiyear, start_date, end_date, vacation_type, comp_id);
					//若年度假別剩餘時數小於本次請假總時數則表示有問題
					if(tmp_hours < totalhours){
						error_type = 3;
						error_flag = true;
					}
				}
				
			}else{
				//該假別設定資訊不存在，檢核失敗
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return error_type;
	}
	
	/**
	 * 處理檢核錯誤訊息
	 * @param errors
	 * @param request
	 * @param conn
	 * @param error_type
	 * @param vacation_type
	 * @param comp_id
	 */
	private void handleErrorMSG(ActionErrors errors, HttpServletRequest request, 
						  	   Connection conn, int error_type, String vacation_type, String comp_id ){
		
		try{
			switch(error_type){
			
			case 1:
				//該假別時數已超過年度假別請假總時數
				errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
				errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						this.generateErrorMSG(conn, error_type, vacation_type, comp_id));
			break;
			
			case 2:
				//該假別時數已超過年度假別請假總時數
				errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
				errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						this.generateErrorMSG(conn, error_type, vacation_type, comp_id));
			break;
			
			case 3:
				//該假別剩餘時數不足，檢核失敗
				errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
				errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						this.generateErrorMSG(conn, error_type, vacation_type, comp_id));
			break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 產生檢核錯誤訊息
	 * @param conn
	 * @param error_type
	 * @param vacation_type
	 * @param comp_id
	 * @return
	 */
	private String generateErrorMSG(Connection conn, int error_type, String vacation_type, String comp_id ){
		
		String ERROR_MSG = "";
		float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
		try{
			//取得系統假別設定資訊
			EHF020100M0F ehf020100m0 = this.getVacationTypeInf(conn, vacation_type, comp_id);
			String work_hourss=(Float.parseFloat(ehf020100m0.getEHF020100T0_05_day()) * Day_work_hours) + Float.parseFloat(ehf020100m0.getEHF020100T0_05_day())+"";
			String work_hourss_06=(Float.parseFloat(ehf020100m0.getEHF020100T0_06_day()) * Day_work_hours) + Float.parseFloat(ehf020100m0.getEHF020100T0_06_day())+"";
			
			if( ehf020100m0 != null ){
				switch(error_type){
				
				case 1:
					//該假別時數已超過年度假別請假總時數
					ERROR_MSG = "該假別的單次最低請假時數:"+work_hourss_06+"小時, 請再次確認!!";
				break;
				
				case 2:
					//該假別時數已超過年度假別請假總時數
					ERROR_MSG = "該假別請假時數已超過年度請假總時數:"+work_hourss+"小時, 請再次確認!!";
				break;
				
				case 3:
					//該假別剩餘時數不足，檢核失敗
					ERROR_MSG = "假別剩餘時數不足!!請再次確認!!";
				break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ERROR_MSG;
	}
	
	/**
	 * 依據假別取得員工年度休假時數
	 * @param conn
	 * @param employee_id
	 * @param chiyear
	 * @param start_date
	 * @param end_date
	 * @param vacation_type
	 * @param comp_id
	 * @return
	 */
	public float getVacationHoursByVacationType( Connection conn, String employee_id, String chiyear, 
										   		 String start_date, String end_date, String vacation_type, String comp_id ){
		
		float vacation_hours = 0;
		
		try{
			//取得員工年度休假時數資訊
			String sql = " SELECT " +
						 " SUM(EHF010300T0_07) AS EHF010300T0_07, " +
						 " SUM(EHF010300T0_08) AS EHF010300T0_08, " +
					 	 " EHF020100T0_04 " +
						 " FROM EHF010300T0 " +
						 " LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF010300T0_06 AND EHF020100T0_08 = EHF010300T0_12 " +
						 " WHERE 1=1 " +
						 " AND EHF010300T0_02 = ? " + //年度(yy)
						 " AND EHF010300T0_05 = ? " + //員工工號
						 " AND EHF010300T0_06 = ? " + //假別代號
						 " AND ( ( ? BETWEEN EHF010300T0_09 AND EHF010300T0_10 ) " +
						 " 		 AND ( ? BETWEEN EHF010300T0_09 AND EHF010300T0_10 ) ) " +  //申請日期是否在區間內
						 " AND EHF010300T0_12 = ? ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			
			p6stmt.setString(1, chiyear);
			p6stmt.setString(2, employee_id);
			p6stmt.setString(3, vacation_type);
			p6stmt.setString(4, start_date);
			p6stmt.setString(5, end_date);
			p6stmt.setString(6, comp_id);
			
			//System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				vacation_hours = rs.getFloat("EHF010300T0_08");  //假別剩餘時數
			}
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return vacation_hours;
	}
	
	/**
	 * 取得全部的假別資訊
	 * @param conn
	 * @param comp_id
	 * @return
	 */
	public Map getVacationTypeInf( Connection conn, String comp_id ){
		
		Map vacation_type_map = new HashMap();
		
		try{
			String sql = "" +
			" SELECT * FROM EHF020100T0 " +
			" WHERE 1=1 " +
			" AND EHF020100T0_02 = ? " +  //公司代碼
			" AND EHF020100T0_08 = ? ";  //公司代碼
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			
			ResultSet rs = pstmt.executeQuery();
			EHF020100M0F ehf020100m0 = null;
			
			while(rs.next()){
				ehf020100m0 = this.getVacationTypeInf(conn, rs.getString("EHF020100T0_03"), comp_id);
				vacation_type_map.put(rs.getString("EHF020100T0_03"), ehf020100m0);
			}
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return vacation_type_map;
	}
	
	/**
	 * 取得系統假別設定資訊
	 * @param conn
	 * @param vacation_type
	 * @param comp_id
	 * @return
	 */
	public EHF020100M0F getVacationTypeInf( Connection conn, String vacation_type, String comp_id ){
		
		EHF020100M0F ehf020100m0 = new EHF020100M0F();
		float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
		try{
			String sql = "" +
			" SELECT * FROM EHF020100T0 " +
			" WHERE 1=1 " +
			" AND EHF020100T0_03 = ? " +  //假別代號
			" AND EHF020100T0_02 = ? " +  //公司代碼
			" AND EHF020100T0_08 = ? ";  //公司代碼
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, vacation_type);  //假別代號
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				ehf020100m0.setEHF020100T0_01(rs.getInt("EHF020100T0_01"));//假別序號
				ehf020100m0.setEHF020100T0_02(rs.getString("EHF020100T0_02"));//部門組織(代號)
				ehf020100m0.setEHF020100T0_03(rs.getString("EHF020100T0_03"));//假別代號
				ehf020100m0.setEHF020100T0_03_TYPE(rs.getString("EHF020100T0_03_TYPE"));//假別薪資設定
				ehf020100m0.setEHF020100T0_03_VAL(rs.getString("EHF020100T0_03_VAL"));//假別薪資設定比例
				ehf020100m0.setEHF020100T0_04(rs.getString("EHF020100T0_04"));//假別名稱
				ehf020100m0.setEHF020100T0_05_FLG(rs.getString("EHF020100T0_05_FLG"));//設定是否檢核年度請假總時數
				
				if(rs.getBoolean("EHF020100T0_05_FLG"))
				{
					//處理剩餘時數
					ehf020100m0.setEHF020100T0_05_day(((int)(rs.getFloat("EHF020100T0_05")/Day_work_hours))+"");
					ehf020100m0.setEHF020100T0_05_hour((rs.getFloat("EHF020100T0_05")%Day_work_hours)+"");
				}else
				{
					//處理剩餘時數
					ehf020100m0.setEHF020100T0_05_day(0+"");
					ehf020100m0.setEHF020100T0_05_hour(0+"");
				}
				//ehf020100m0.setEHF020100T0_05(rs.getString("EHF020100T0_05"));
				ehf020100m0.setEHF020100T0_06_FLG(rs.getString("EHF020100T0_06_FLG"));//設定是否檢核單次最低請假時數
				if(rs.getBoolean("EHF020100T0_06_FLG"))
				{
					//處理剩餘時數
					ehf020100m0.setEHF020100T0_06_day(((int)(rs.getFloat("EHF020100T0_06")/Day_work_hours))+"");
					ehf020100m0.setEHF020100T0_06_hour((rs.getFloat("EHF020100T0_06")%Day_work_hours)+"");
				}else
				{
					//處理剩餘時數
					ehf020100m0.setEHF020100T0_06_day(0+"");
					ehf020100m0.setEHF020100T0_06_hour(0+"");
				}
				
				//ehf020100m0.setEHF020100T0_06(rs.getString("EHF020100T0_06"));//單次最低請假時數
				ehf020100m0.setEHF020100T0_07(rs.getString("EHF020100T0_07"));//備註
				ehf020100m0.setEHF020100T0_08(rs.getString("EHF020100T0_08"));//公司代碼
				ehf020100m0.setEHF020100T0_09(rs.getString("EHF020100T0_09"));//設定是否檢核年度假別剩餘時數
				//於EMS1.1時在公司假別設定多加三個參數。 20140604 by Hedwig
				ehf020100m0.setEHF020100T0_10(rs.getString("EHF020100T0_10"));//是否為預設假別
				ehf020100m0.setEHF020100T0_11(rs.getString("EHF020100T0_11"));//請假時數是否包含假日
				ehf020100m0.setEHF020100T0_12(rs.getString("EHF020100T0_12"));//限定連續休假
				
			}else{
				ehf020100m0 = null;
			}
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ehf020100m0;
	}
	
	/**
	 * 依據假別取得員工請假單年度總時數
	 * @param conn
	 * @param employee_id
	 * @param chiyear
	 * @param start_date
	 * @param end_date
	 * @param vacation_type
	 * @param comp_id
	 * @return
	 */
	public float getVacationHoursInFormByVacationType( Connection conn, String employee_id, String chiyear, 
										   		 	   String vacation_type, String comp_id ){
		
		float vacation_hours = 0;
		
		try{
			float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
			String[] time = null;
			int day = 0;  //天數
			float hours = 0;  //時數
			
			//依據假別取得員工請假單年度總時數資訊
			String sql = " SELECT EHF020200T0_13 " +
						 " FROM EHF020200T0 " +
						 " WHERE 1=1 " +
						 " AND EHF020200T0_03 = ? " + //申請人員工工號
						 " AND EHF020200T0_14 = ? " + //假別代號
						 " AND EHF020200T0_16 = ? " + //表單狀態
						 " AND (EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911) = ? " +  //請假日期(起)
						 " AND (EXTRACT( YEAR FROM EHF020200T0_10 ) - 1911) = ? " +  //請假日期(迄)
						 " AND EHF020200T0_18 = ? ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, employee_id);  //申請人員工工號
			indexid++;
			p6stmt.setString(indexid, vacation_type);  //假別代號
			indexid++;
			p6stmt.setString(indexid, "0006");  //表單狀態
			indexid++;
			p6stmt.setString(indexid, chiyear);  //請假日期(起)
			indexid++;
			p6stmt.setString(indexid, chiyear);  //請假日期(迄)
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			
			//System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				time = rs.getString("EHF020200T0_13").split("/");
				day = Integer.parseInt(time[0]);  //天數
				hours = Float.parseFloat(time[1]);  //時數
				
				vacation_hours += hours + ( day * Day_work_hours);  //總共請假時數
			}
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return vacation_hours;
	}

	/**
	 * 依據假別取得員工請假單年度總時數
	 * @param conn
	 * @param employee_id  (員工工號)
	 * @param chiyear (薪資日期)
	 * @param vacation_type (假別代號)
	 * @param comp_id (公司代碼)
	 * @param user_authbean (假別資訊)
	 * @return
	 */
	private float getVacationHours( Connection conn, String employee_id, String chiyear,String vacation_type, String comp_id ,AuthorizedBean user_authbean){
		
		float vacation_hours = 0;
		Map tempClassMap = null;
		float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
		String[] time = null;
		int day = 0;  //天數
		float hours = 0;  //時數
		try {
			Calendar cal_salyymm = this.tools.covertStringToCalendar(chiyear, "yyyy/MM");
			cal_salyymm.add(Calendar.MONTH, 1);  //取得下個月
			cal_salyymm.set(Calendar.DAY_OF_MONTH, 1);  //設定為下個月的1號
			//String the_first_day_of_next_month = this.tools.covertDateToString(cal_salyymm.getTime(), "yyyy/MM/dd");
			cal_salyymm.add(Calendar.DAY_OF_MONTH, -1);  //設定為這個月的最後一日
			String the_last_day_of_this_month = this.tools.covertDateToString(cal_salyymm.getTime(), "yyyy/MM/dd");
			
			boolean handle_except = false;
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			user_authbean.setEmployeeID(employee_id);
			tempClassMap = hr_tools.getEmpClass(conn, user_authbean);
			
			// 判斷班別下班是否有跨午夜
			if (hr_tools.hasCrossMidnight(tempClassMap, 2)) {
				// 如果有跨午夜則需要處理例外
				handle_except = true;
			} else {
				// 如果沒有跨午夜則需要處理例外
				handle_except = false;
			}
			hr_tools.close();
			
			//此段SQL所搜尋到的 為 某員工  XXXX年XX月 的所有假單(沒有跨夜)
			//依據假別取得員工請假單年度總時數資訊
			String sql = " SELECT EHF020200T0_13 " +
						 " FROM EHF020200T0 " +
						 " WHERE 1=1 " +
						 " AND EHF020200T0_03 = '"+employee_id+ "'" + //申請人員工工號
						 " AND EHF020200T0_14 = '"+vacation_type+ "'" + //假別代號
						 " AND EHF020200T0_16 = '0006'" + //表單狀態
						 " AND EHF020200T0_18 = '"+comp_id+ "'"+  //公司代碼
			
			//請假單納入該月薪資計算的判定程式, 要針對班別是跨午夜的狀況做特別處理
			//Ex: 2012/04/30 是該月最後一天, 
			//若某員工班別是 17:00 - 01:00, 則該天正常上下班時間應為 2012/04/30 17:00 - 2012/05/01 01:00
			//若該員工有一張請假單是 2012/05/01 00:00 - 2012/05/01 01:00, 1hr,
			//則此請假單也必須納入本月薪資做計算, edit by joe 2012/05/08
			//如果請假起迄日為同一天, 則只要起日或器日落在該月份即納入薪資做計算
			" AND ( ((EHF020200T0_09 = EHF020200T0_10) AND ( DATE_FORMAT(EHF020200T0_09, '%Y/%m') = '"+chiyear+"' " +
			" OR DATE_FORMAT(EHF020200T0_10, '%Y/%m') = '"+chiyear+"' )) " +
			//如果請假起迄日為不同天, 則只要起日落在該月份即納入薪資做計算
			" OR ( (EHF020200T0_09 <> EHF020200T0_10) AND (DATE_FORMAT(EHF020200T0_09, '%Y/%m') = '"+chiyear+"') ) ) " ;

			
			//例外處理(當有跨夜時)
			String sql_except = "" +
			" SELECT EHF020200T0_13 " +
			 " FROM EHF020200T0 " +
			 " WHERE 1=1 " +
			 " AND EHF020200T0_03 = '"+employee_id+ "'" + //申請人員工工號
			 " AND EHF020200T0_14 = '"+vacation_type+ "'" + //假別代號
			 " AND EHF020200T0_16 = '0006'" + //表單狀態
			 " AND EHF020200T0_18 = '"+comp_id+ "'"+  //公司代碼
			 " AND EHF020200T0_09 = '"+the_last_day_of_this_month+"' " +  //請假(起)
			 " AND EHF020200T0_10 = '"+the_last_day_of_this_month+"' " ;  //請假(迄)
			
			if(handle_except){
				sql = sql + " UNION " + sql_except;
			}
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next()){
				time = rs.getString("EHF020200T0_13").split("/");
				day = Integer.parseInt(time[0]);  //天數
				hours = Float.parseFloat(time[1]);  //時數
				
				vacation_hours += hours + ( day * Day_work_hours);  //總共請假時數
			}
			rs.close();
			stmt.close();	
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw new Exception("依據假別取得員工請假單年度總時數，發生錯誤!!" + e.toString());
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return vacation_hours;
	}
	
	
	/**
	 * 請假彙總資料
	 * @param conn
	 * @param eHF030600T0U 		(計薪UID)
	 * @param salYYMM			(薪資年月)
	 * @param costYYMM			(入扣帳年月)
	 * @param ehf030600m2		(計薪人員)
	 * @param vacationTypeMap  	(假別資訊)
	 * @param compId
	 * @param userAuthbean
	 * @return
	 */
	public Map countVacationTime(Connection conn, String eHF030600T0U,String salYYMM, String costYYMM, EHF030600M2F ehf030600m2,Map vacationTypeMap, String compId, AuthorizedBean userAuthbean) {
		// TODO Auto-generated method stub
		Map<String,String> dataMap = null;
		
		EHF020100M0F ehf020100m0 = null;
		float vacation_hours = 0;
		//float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, compId, "WORK_HOURS"));
		//int temp_day = 0;
		//String diffhours = "";
		try{
			for (Object key : vacationTypeMap.keySet()) {
				//String leave = (String) vacationTypeMap.get(key);// 假別代號
				String Mapkey = (String)key;
				vacation_hours = this.getVacationHours(conn, ehf030600m2.getEHF030600T2_02(), salYYMM, Mapkey, compId, userAuthbean);
				if (vacation_hours != 0) {
					ehf020100m0 = (EHF020100M0F) vacationTypeMap.get(key);
					dataMap = new LinkedHashMap<String, String>();
					dataMap.put("EHF020411T0_M", costYYMM); //入扣帳年月
					dataMap.put("EHF020411T0_M1", salYYMM); //薪資年月
					dataMap.put("EHF020411T0_02", ehf030600m2.getEHF030600T2_03()); //部門組織(代號)
					dataMap.put("EHF020411T0_03", ehf030600m2.getEHF030600T2_02()); //員工工號
					dataMap.put("EHF020411T0_04", Mapkey); //假別代碼
					dataMap.put("EHF020411T0_05", ""+vacation_hours); //請假天數(請假天數/時數)
					dataMap.put("EHF020411T0_06", ehf020100m0.getEHF020100T0_03_TYPE()); //假別薪資設定
					dataMap.put("EHF020411T0_07", ehf020100m0.getEHF020100T0_03_VAL()); //假別薪資設定比例
					dataMap.put("EHF020411T0_08", compId); //公司代碼
					dataMap.put("EHF020411T0_SFK", eHF030600T0U); //薪資計算UID
					if(!"".equals(dataMap.get("EHF020411T0_05"))||!(dataMap.get("EHF020411T0_05")==null)){
						//計算每人每個月的請假總天數
						this.vacation_time_list.add(dataMap); 
					}
				}
				
				
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		return dataMap;
	}
	
	/**
	 * 判斷日期是否有連續。  20140604 by Hedwig
	 * @param errors
	 * @param request
	 * @param conn
	 * @param EHF020200T0_01	申請請假單UID
	 * @param EHF020200T0_09	申請請假日期(起)
	 * @param EHF020200T0_14	申請假別(假別代號)
	 * @param employee_id
	 */
	public void checkContinuousData( ActionErrors errors, HttpServletRequest request,
										   Connection conn, String EHF020200T0_01, 
			   							   String EHF020200T0_09,String EHF020200T0_10, String EHF020200T0_14, String employee_id){
		
		try{
			//判斷日期是否有連續
			Map conflict_map = this.checkContinuousDataForm(conn, EHF020200T0_01, EHF020200T0_09, EHF020200T0_10 ,EHF020200T0_14, employee_id, (String)request.getAttribute("compid"));
			
			//取得系統假別設定資訊
			EHF020100M0F ehf020100m0 = this.getVacationTypeInf(conn, EHF020200T0_14, (String)request.getAttribute("compid"));
			
			if((Boolean)conflict_map.get("CHK_FLAG")){
				//表示日期沒有連續
				errors.add("EHF020200T0_09",new ActionMessage(""));
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						ehf020100m0.getEHF020100T0_04()+"無法分開請，或是申請日期有誤，重新選擇日期!!");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 執行請假單日期連續檢核。 20140604 by Hedwig
	 * @param conn
	 * @param EHF020200T0_01	申請請假單UID
	 * @param EHF020200T0_09	申請請假日期(起)
	 * @param EHF020200T0_14	申請假別(假別代號)
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public Map checkContinuousDataForm( Connection conn, String EHF020200T0_01, String EHF020200T0_09, String EHF020200T0_10, String EHF020200T0_14, String employee_id, String comp_id ){
		
		Map return_map = new HashMap();
		EHF020200T0_09 = EHF020200T0_09.replaceAll("/", "");
		EHF020200T0_10 = EHF020200T0_10.replaceAll("/", "");
		try{
			String sql = "SELECT DATE_FORMAT(a.EHF020200T0_10,'%Y%m%d') AS EHF020200T0_10, " +
					" DATE_FORMAT(DATE_ADD(a.EHF020200T0_10,INTERVAL 1 DAY),'%Y%m%d') AS EHF020200T0_10ADD, " +
//					" DATE_FORMAT(DATE_SUB(a.EHF020200T0_10,INTERVAL 1 DAY),'%Y%m%d') AS EHF020200T0_10SUB, " +
					" d.EHF020100T0_12 " +
					" FROM EHF020200T0 a " +
					" LEFT JOIN EHF010300T1 b ON b.EHF010300T1_01 = a.EHF020200T0_01 AND b.EHF010300T1_04 = a.EHF020200T0_18 "+
					" LEFT JOIN EHF010300T0 c ON c.EHF010300T0_01 = b.EHF010300T1_02 AND c.EHF010300T0_12 = a.EHF020200T0_18 " +
					" LEFT JOIN EHF020100T0 d ON a.EHF020200T0_14 = d.EHF020100T0_03 AND a.EHF020200T0_18 = d.EHF020100T0_08 " +
					" WHERE 1=1 " +
					" AND a.EHF020200T0_05 = '"+employee_id+"' " +
					" AND a.EHF020200T0_14 = '" +EHF020200T0_14+"' " +
					" AND a.EHF020200T0_18 = '"+comp_id+"' " +
					" AND a.EHF020200T0_01 <> '"+EHF020200T0_01+"' " +
					" AND "+EHF020200T0_09+" BETWEEN c.EHF010300T0_09 AND c.EHF010300T0_10 "+
					" AND "+EHF020200T0_10+" BETWEEN c.EHF010300T0_09 AND c.EHF010300T0_10 "+
					" ORDER BY a.EHF020200T0_10 DESC " ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				EHF020200T0_09 = EHF020200T0_09.replaceAll("/", "");
				int BeforeApplydateADD = rs.getInt("EHF020200T0_10ADD");
				int BeforeApplydate = rs.getInt("EHF020200T0_10");
				//此員工年度休假設定先前已經有申請過。
				if(rs.getBoolean("EHF020100T0_12")){
					
					if(Integer.valueOf(EHF020200T0_09) > BeforeApplydateADD ){
						//申請日期大於先前申請日期+1。
						return_map.put("CHK_FLAG", true);
					}else if(Integer.valueOf(EHF020200T0_09) == BeforeApplydateADD || Integer.valueOf(EHF020200T0_09) == BeforeApplydate ){
						//申請日期等於先前申請日期+1 OR 申請日期等於先前申請日期。
						return_map.put("CHK_FLAG", false);
					}else if(Integer.valueOf(EHF020200T0_09) < BeforeApplydate ){
						//申請日期小於先前申請日期。
						return_map.put("CHK_FLAG", true);
					}
				}else{
					//假別設定為請假時數是可分開申請的。
					return_map.put("CHK_FLAG", false);
				}
			}else{
				//此員工年度休假設定先前沒有有申請過。
				return_map.put("CHK_FLAG", false);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			return_map.put("CHK_FLAG", false);
		}
		
		return return_map;
	}
	
	/**
	 * 檢核員工是否可以申請假別。  20140604 by Hedwig
	 * @param errors
	 * @param request
	 * @param conn
	 * @param employee_id	申請人員工工號
	 * @param EHF020200T0_09	申請請假日期(起)
	 * @param EHF020200T0_14	申請假別(假別代號)
	 */
	public void checkIsAskForLeave( ActionErrors errors, HttpServletRequest request,
										   Connection conn, String employee_id, 
			   							   String EHF020200T0_09, String EHF020200T0_14){
		
		try{
			//判斷員工是否可以申請假別
			Map conflict_map = this.checkIsAskForLeaveForm(conn, employee_id, EHF020200T0_09 = EHF020200T0_09.replaceAll("/", ""), EHF020200T0_14, (String)request.getAttribute("compid"));
			
			if((Boolean)conflict_map.get("CHK_FLAG")){
				//表示員工不可以申請假別
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						"您無法申請"+conflict_map.get("LeaveName")+"，請您詢問相關人員!!");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 檢核請假單的假別是否可以申請。 20140604 by Hedwig
	 * @param conn
	 * @param employee_id	申請人員工工號
	 * @param EHF020200T0_09	申請請假日期(起)
	 * @param EHF020200T0_14	申請假別(假別代號)
	 * @param comp_id	公司代碼
	 * @return return_map
	 */
	public Map checkIsAskForLeaveForm( Connection conn, String employee_id, String EHF020200T0_09, String EHF020200T0_14, String comp_id){
		
		Map return_map = new HashMap();
		boolean isPass = true;
		
		try{
			String sql = "SELECT 'EHF010300T0' AS FORM, ISNULL(EHF010300T0_03) AS isPass, '' AS LeaveName " +
							" FROM EHF010300T0 " +
							" WHERE 1=1 " +
							" AND EHF010300T0_06 = '"+EHF020200T0_14+"' " +
							" AND EHF010300T0_05 = '"+employee_id+"' " +
							" AND "+EHF020200T0_09+" BETWEEN DATE_FORMAT(EHF010300T0_09,'%Y%m%d') AND DATE_FORMAT(EHF010300T0_10,'%Y%m%d') " +
							" AND EHF010300T0_08 <> 0 " +
							" AND EHF010300T0_12 = '" +comp_id+"'"+
							" UNION " +
							" SELECT 'EHF020100T0' AS FORM, EHF020100T0_10 AS isPass, EHF020100T0_04 AS LeaveName " +
							" FROM EHF020100T0 " +
							" WHERE 1=1 " +
							" AND EHF020100T0_03 = '"+EHF020200T0_14+"' " +
							" AND EHF020100T0_08 = '"+comp_id+"'" ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next() && isPass){
				if("EHF010300T0".equals(rs.getString("FORM")) && !rs.getBoolean("isPass")){
					//此員工擁有此假別的設定，並且在申請的日期期間是有時數也可以使用的。
					return_map.put("CHK_FLAG", false);
					isPass = false;
				}else if("EHF020100T0".equals(rs.getString("FORM")) && rs.getBoolean("isPass")){
					//在假別設定中，此假別為預設假別，並不需要在員工年度休假設定新增資料。
					return_map.put("CHK_FLAG", false);
					isPass = false;
				}else{
					//此假別非預設假別，並且此員工也未設定次假別的員工年度休假資料，因此不允許申請此假別的請假單。
					return_map.put("CHK_FLAG", true);
					return_map.put("LeaveName", rs.getString("LeaveName"));
				}
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		
		return return_map;
	}
	
	
	public List<Map<String, String>> getEMS_CheckVacation( ){
		
		return this.vacation_time_list;
	}
		
}
