package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>重新產生考勤
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020405M2A extends NewDispatchAction {
	protected final BA_TOOLS tools = BA_TOOLS.getInstance();
	public EHF020405M2A() {

	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	private void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request) {
		//產生--日期類別-- 選單
		try{
			List listDATA05 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("月份");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("指定日期");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("指定日期區間");
			listDATA05.add(bform);
			request.setAttribute("listDATA05", listDATA05);
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Action的進入點
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String at_type = "";
		
		try{
			ArrayList list = new ArrayList();
			
			//設定初始考勤日期

			
			//產生下拉選單
			this.generateSelectBox(conn, Form, request);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("collection", "");
			
			request.setAttribute("Form1Datas", Form);
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
	    		if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			}catch(Exception e){
				
			}
	    }
		
		return mapping.findForward("success");
	}
	
	
	/**
	 * 產生考勤資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward generateData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		String error_month = "";
		String ALL_error_month = "";
		//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("action", "addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);

		try {
			
			try{
				//建立檢核元件
				BA_Vaildate ve = BA_Vaildate.getInstance();
				ve.isEmpty(lc_errors, Form.getDATA05(), "DATA05", "不可空白");
				
			}catch(Exception e){
				request.setAttribute("MSG", "檢核程式執行失敗!!");
				e.printStackTrace();
			}									
						
			if (lc_errors.isEmpty()) {
				
				String date = Form.getDATA05();  //考勤日期
				AuthorizedBean authbean = getLoginUser(request);
				BaseFunction base_tools = new BaseFunction(authbean.getCompId());
				Calendar start_cal = null;
				Calendar end_cal = null;
				
				//取得公司名稱
				//Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
				//員工Map
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				//部門Map
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
				//職務
				//Map titleMap = hr_tools.getTitleNameMap(authbean.getCompId());
																
				try{
					//2013/10/16 更新邏輯   BY賴泓錡
//=============================================================================================================================================	
					//指定日期
					if("02".equals(Form.getDATA07())){
						
						EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(authbean.getCompId(), date, authbean.getUserId() );
						
						String day[]=date.split("/");
						String sql = "" +
						" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
						" FROM EHF020900T0 " +
						" WHERE 1=1 " +
						" AND EHF020900T0_M2 = '"+day[0]+"/"+day[1]+"'"+	//考勤年月
						" AND EHF020900T0_04 = '"+authbean.getCompId()+"'" +//公司代碼
						" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

						List dataList = base_tools.queryList(sql);
						if (dataList.size() > 0) {
							Map data=(Map)dataList.get(0);
								
							if("02".equals((String)data.get("EHF020900T0_02"))||"03".equals((String)data.get("EHF020900T0_02"))){
								request.setAttribute("MSG", "考勤已確認，不可重新產生考勤!!");
								base_tools.close();
								this.cleanAddField(Form);
								return init(mapping, form, request, response);
							}
						}
													
						if(!"".equals(Form.getDATA01()) && Form.getDATA01() != null && !"".equals(Form.getDATA03()) && Form.getDATA03() != null){
							//將使用"門禁輸入作業"新增的門禁資料修改狀態為未確認
							this.SELECTSQL(conn, authbean.getCompId(), Form.getDATA01(), Form.getDATA03(), Form.getDATA05());
																					
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), date, "", WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
														
							//產生員工考勤資料
							att.execute_Attendance( true, Form.getDATA03());  //DATA03 = 員工工號
							
						}else if(!"".equals(Form.getDATA01()) && Form.getDATA01() != null){//指定部門
							//刪除門禁資料
							this.SELECTSQL(conn, authbean.getCompId(), Form.getDATA01(), "", Form.getDATA05());
							//產生部門考勤資料
							List deptemployeelist = this.getSameDepEmpList(conn,
																		   Form.getDATA01(),  	//DATA01 = 組織單位
																		   date,				//重產日期(找尋)	
																		   authbean.getCompId());//20140919  修改  BY Alvin
																					
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), date, "", WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
																					
							Map tempmap = null;
							
							//利用部門清單中的員工工號產生考勤資料
							/*for(int i = 0; i < deptemployeelist.size(); i++){
								tempmap = (Map) deptemployeelist.get(i);
								att.execute_Attendance(true, (String) tempmap.get("EMPLOYEE_ID"));
							}*/
							//利用部門清單中的員工工號產生考勤資料
							Iterator it = deptemployeelist.iterator();
							
							while(it.hasNext()){
								tempmap = (Map) it.next();
								att.execute_Attendance(true, (String) tempmap.get("EHF010100T6_01"));
							}
							
						}else{//沒指定
							//將使用"門禁輸入作業"新增的門禁資料修改狀態為未確認
							this.SELECTSQL(conn, authbean.getCompId(), "", "", Form.getDATA05());
																					
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
														
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), date, "", WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
																												
							//產生公司考勤資料
							att.execute_Attendance(true, "");
						}
//=============================================================================================================================================	
					//指定月份				
					}else if("01".equals(Form.getDATA07())){ 
												
						String day[]=date.split("/");
						String sql = "" +
						" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
						" FROM EHF020900T0 " +
						" WHERE 1=1 " +
						" AND EHF020900T0_M2 = '"+day[0]+"/"+day[1]+"'"+	//考勤年月
						" AND EHF020900T0_04 = '"+authbean.getCompId()+"'" +//公司代碼
						" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

						List dataList = base_tools.queryList(sql);
						if (dataList.size() > 0) {
							Map data=(Map)dataList.get(0);
							
							if("02".equals((String)data.get("EHF020900T0_02"))||"03".equals((String)data.get("EHF020900T0_02"))){
								request.setAttribute("MSG", "考勤已確認，不可重新產生考勤!!");
								this.cleanAddField(Form);
								base_tools.close();
								return init(mapping, form, request, response);
							}
						}
					
						///String e_date = Form.getDATA06();  //日期迄
						//傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
						String start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(date)));
						//傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
						String end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(date)));
												
						start_cal = tools.covertStringToCalendar(start_date);  //考勤計算開始日期
						end_cal = tools.covertStringToCalendar(end_date);  //考勤計算結束日期
						
						boolean chk_run_flag = true;
						
						EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(authbean.getCompId(), "", authbean.getUserId() );

						if(!"".equals(Form.getDATA01()) && Form.getDATA01() != null && !"".equals(Form.getDATA03()) && Form.getDATA03() != null){
																												
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), day[0]+"/"+day[1], "", WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
																																									
							//指定個人    指定某月份    從產考勤							
							while(chk_run_flag){
								
								if(start_cal.equals(end_cal)){
									chk_run_flag = false;
								}
								
								//產生考勤日期
								String cur_date = tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前計算的考勤日期
								
								//設定考勤日期
								EMS_AttendanceAction.setSta_cur_day( cur_date );
								
								//將使用"門禁輸入作業"新增的門禁資料修改狀態為未確認
								this.SELECTSQL(conn, authbean.getCompId(), Form.getDATA01(), Form.getDATA03(), cur_date);
																								
								//產生員工考勤資料
								att.execute_Attendance( true, Form.getDATA03());  //DATA03 = 員工工號
								
								start_cal.add(Calendar.DAY_OF_MONTH, 1);
							}
							
						}else if(!"".equals(Form.getDATA01()) && Form.getDATA01() != null){
							//指定部門    指定某月份    從產考勤							
														
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), day[0]+"/"+day[1], "", WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
																												
							while(chk_run_flag){
								
								if(start_cal.equals(end_cal)){
									chk_run_flag = false;
								}
								
								//產生考勤日期
								String cur_date = tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前計算的考勤日期
								
								//設定考勤日期
								EMS_AttendanceAction.setSta_cur_day( cur_date );
								
								//將使用"門禁輸入作業"新增的門禁資料修改狀態為未確認
								this.SELECTSQL(conn, authbean.getCompId(), Form.getDATA01(), "", cur_date);

								//產生部門考勤資料
								List deptemployeelist = this.getSameDepEmpList(conn,
																			   Form.getDATA01(),  //DATA01 = 組織單位
																			   cur_date,
																			   authbean.getCompId());//20140919  修改  BY Alvin
								Map tempmap = null;
								
								/*if (deptemployeelist.size() != 0) {
									// 利用部門清單中的員工工號產生考勤資料
									for (int i = 0; i < deptemployeelist.size(); i++) {
										tempmap = (Map) deptemployeelist.get(i);
										att.execute_Attendance( true,(String) tempmap.get("EMPLOYEE_ID"));
									}
								}*/
								// 利用部門清單中的員工工號產生考勤資料
								Iterator it = deptemployeelist.iterator();
								
								while(it.hasNext()){
									tempmap = (Map) it.next();
									att.execute_Attendance(true, (String) tempmap.get("EHF010100T6_01"));
								}
								start_cal.add(Calendar.DAY_OF_MONTH, 1);
							}
						}else{
							
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), day[0]+"/"+day[1], "", WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
														
							//指定全公司   指定某月份    從產考勤
							while(chk_run_flag){
								
								if(start_cal.equals(end_cal)){
									chk_run_flag = false;
								}
								
								//產生考勤日期
								String cur_date = tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前計算的考勤日期
								
								//設定考勤日期
								EMS_AttendanceAction.setSta_cur_day( cur_date );
								
								//將使用"門禁輸入作業"新增的門禁資料修改狀態為未確認
								this.SELECTSQL(conn, authbean.getCompId(), "", "", cur_date);								
								
								//產生公司考勤資料
								att.execute_Attendance(true, "");
		
								start_cal.add(Calendar.DAY_OF_MONTH, 1);
							}

						}	
					}					
//=====================================================================================================	
					//指定日期區間
					else if("03".equals(Form.getDATA07())){

						if("".equals(Form.getDATA05())||"".equals(Form.getDATA06())){

							request.setAttribute("MSG", "日期條件輸入錯誤，請從新輸入");
							this.cleanAddField(Form);
							return init(mapping, form, request, response);
							
						}

						String e_date = Form.getDATA06();  //日期迄
						start_cal = tools.covertStringToCalendar(date);  //考勤計算開始日期
						end_cal = tools.covertStringToCalendar(e_date);  //考勤計算結束日期
						
						boolean chk_run_flag = true;
						
						EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(authbean.getCompId(), "", authbean.getUserId() );

						if(!"".equals(Form.getDATA01()) && Form.getDATA01() != null && !"".equals(Form.getDATA03()) && Form.getDATA03() != null){
														
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
														
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), Form.getDATA05(),Form.getDATA06(), WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);							
							
							//指定個人    指定某日期區間    從產考勤							
							while(chk_run_flag){
								
								if(start_cal.equals(end_cal)){
									chk_run_flag = false;
								}

								//產生考勤日期
								String cur_date = tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前計算的考勤日期
								
								String day[]=cur_date.split("/");
								String sql = "" +
								" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
								" FROM EHF020900T0 " +
								" WHERE 1=1 " +
								" AND EHF020900T0_M2 = '"+day[0]+"/"+day[1]+"'"+	//考勤年月
								" AND EHF020900T0_04 = '"+authbean.getCompId()+"'" +//公司代碼
								" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

								List dataList = base_tools.queryList(sql);
								if (dataList.size() > 0) {
									Map data = (Map)dataList.get(0);
									
									if("02".equals((String)data.get("EHF020900T0_02"))||"03".equals((String)data.get("EHF020900T0_02"))){
										start_cal.add(Calendar.DAY_OF_MONTH, 1);
										if ("".equals(error_month)){
											error_month=day[0] + "/" + day[1];
											ALL_error_month+=day[0] + "/" + day[1]+"，";
										}
										continue;
									}
								}

								//設定考勤日期
								EMS_AttendanceAction.setSta_cur_day( cur_date );
								
								//將使用"門禁輸入作業"新增的門禁資料修改狀態為未確認
								this.SELECTSQL(conn, authbean.getCompId(), Form.getDATA01(), Form.getDATA03(), cur_date);
								
								//產生員工考勤資料
								att.execute_Attendance(true, Form.getDATA03());  //DATA03 = 員工工號
								
								start_cal.add(Calendar.DAY_OF_MONTH, 1);
							}
							
						}else if(!"".equals(Form.getDATA01()) && Form.getDATA01() != null){
							//指定部門    指定某日期區間    從產考勤	
													
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), Form.getDATA05(),Form.getDATA06(), WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
														
							while(chk_run_flag){
								
								if(start_cal.equals(end_cal)){
									chk_run_flag = false;
								}
								
								//產生考勤日期
								String cur_date = tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前計算的考勤日期
								
								String day[]=cur_date.split("/");
								String sql = "" +
								" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
								" FROM EHF020900T0 " +
								" WHERE 1=1 " +
								" AND EHF020900T0_M2 = '"+day[0]+"/"+day[1]+"'"+	//考勤年月
								" AND EHF020900T0_04 = '"+authbean.getCompId()+"'" +//公司代碼
								" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

								List dataList = base_tools.queryList(sql);
								if (dataList.size() > 0) {
									Map data=(Map)dataList.get(0);
									
									if("02".equals((String)data.get("EHF020900T0_02"))||"03".equals((String)data.get("EHF020900T0_02"))){
										start_cal.add(Calendar.DAY_OF_MONTH, 1);
										if ("".equals(error_month)){
											error_month=day[0] + "/" + day[1];
											ALL_error_month+=day[0] + "/" + day[1]+ "，";
										}
										continue;
									}
								}
								//設定考勤日期
								EMS_AttendanceAction.setSta_cur_day( cur_date );
								
								//將使用"門禁輸入作業"新增的門禁資料修改狀態為未確認
								this.SELECTSQL(conn, authbean.getCompId(), Form.getDATA01(), "", cur_date);

								//產生部門考勤資料
								List deptemployeelist = this.getSameDepEmpList(conn,Form.getDATA01(),  //DATA01 = 組織單位
										cur_date,authbean.getCompId());//20140919  修改  BY Alvin
								Map tempmap = null;
								
								/*if (deptemployeelist.size() != 0) {
									// 利用部門清單中的員工工號產生考勤資料
									for (int i = 0; i < deptemployeelist.size(); i++) {
										tempmap = (Map) deptemployeelist.get(i);
										att.execute_Attendance(true,(String) tempmap.get("EMPLOYEE_ID"));
									}
								}*/
								// 利用部門清單中的員工工號產生考勤資料
								Iterator it = deptemployeelist.iterator();
								
								while(it.hasNext()){
									tempmap = (Map) it.next();
									att.execute_Attendance(true, (String) tempmap.get("EHF010100T6_01"));
								}
								start_cal.add(Calendar.DAY_OF_MONTH, 1);
							}
						}else{
							//指定全公司   指定某日期區間    從產考勤
																					
							Map WorkSchedule = new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							//整理員工名單
							ems_wsc_tools.sortoutList(Form.getDATA01(),Form.getDATA03(),empMap,depMap);
							
							try{
								//取得排班表與行事曆
								ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), Form.getDATA05(),Form.getDATA06(), WorkSchedule);
							}catch(Exception e){
								e.printStackTrace();
								throw new Exception("取得排班表與行事曆時，發生錯誤!!" + e.toString());
							}							
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
							
							while(chk_run_flag){
								
								if(start_cal.equals(end_cal)){
									chk_run_flag = false;
								}
								
								//產生考勤日期
								String cur_date = tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前計算的考勤日期
								
								String day[]=cur_date.split("/");
								String sql = "" +
								" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
								" FROM EHF020900T0 " +
								" WHERE 1=1 " +
								" AND EHF020900T0_M2 = '"+day[0]+"/"+day[1]+"'"+	//考勤年月
								" AND EHF020900T0_04 = '"+authbean.getCompId()+"'" +//公司代碼
								" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

								List dataList = base_tools.queryList(sql);
								if (dataList.size() > 0) {
									Map data=(Map)dataList.get(0);
									
									if("02".equals((String)data.get("EHF020900T0_02"))||"03".equals((String)data.get("EHF020900T0_02"))){
										start_cal.add(Calendar.DAY_OF_MONTH, 1);
										if ("".equals(error_month)){
											error_month=day[0] + "/" + day[1];
											ALL_error_month+=day[0] + "/" + day[1]+ "，";
										}
										continue;
									}
								}
								//設定考勤日期
								EMS_AttendanceAction.setSta_cur_day( cur_date );
								
								//將使用"門禁輸入作業"新增的門禁資料修改狀態為未確認
								this.SELECTSQL(conn, authbean.getCompId(), "", "", cur_date);
								
								//產生公司考勤資料
								att.execute_Attendance(true, "");
		
								start_cal.add(Calendar.DAY_OF_MONTH, 1);
							}

						}	
					}
					
					conn.commit();
						
				}catch(Exception e){
					request.setAttribute("MSG", "產生考勤程式執行失敗!!");
					e.printStackTrace();
				}

				if(!"".equals(ALL_error_month)){
					request.setAttribute("MSG", "考勤資料產生成功("+ALL_error_month+"考勤已確認，不可重新產生考勤)");
				}else{
					request.setAttribute("MSG", "考勤資料產生成功");
				}
				request.setAttribute("button", "query");
				request.setAttribute("collection", "");
				base_tools.close();
				// 清掉畫面上的新增
				this.cleanAddField(Form);
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas" , Form);
				if (request.getAttribute("Form1Datas") != null) {
					form = (EMS_VIEWDATAF) request.getAttribute("Form1Datas");
				}
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				//return init(mapping, form, request, response);
				// 清掉畫面上的新增
				this.cleanAddField(Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "考勤資料產生失敗!");
			System.out.println("EHF020405M2A.generateData() " + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				hr_tools.close();
			} catch (Exception e) {
			}
		}

		return init(mapping, form, request, response);
	}
	
	

	/**
	 * 根據傳述的部門資料，找員工基本資料裡面(EHF010100T6)的所有這部門的人
	 * @param conn 
	 * @param data01
	 * @param compId
	 * @param string 
	 * @return
	 */
	private List getSameDepEmpList(Connection conn, String Dep, String date,String compId ) {
		// TODO Auto-generated method stub
		
		List list = new ArrayList();
		BaseFunction base_tools = new BaseFunction(compId);
		
		 try{
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = null;
				String sql = "" +
				" SELECT *		  FROM ehf010100t6" +
				"		 WHERE 1 = 1 " +
				" and ehf010100t6_02='" +Dep+"'" +
				" AND HR_CompanySysNo='" +compId+"'" +
				" AND '"+date+"' BETWEEN ehf010100t6_03 AND EHF010100T6_04";
				/*rs = stmt.executeQuery(sql);
				while (rs.next()) {
					list.add(rs.getString("EHF010100T6_01"));
				}
				rs.close();
				stmt.close();*/
				list = base_tools.queryList(sql);
				base_tools.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		return list;
	}

	/**
	 * 產生考勤資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward generateDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return generateData(mapping, form, request, response);
	}
	
	
	/**
	 * 刪除  由使用者輸入的門禁資料
	 * @param conn
	 * @param compId
	 * @param EHF020403T0_02
	 * @param employee
	 * @param day
	 */
	private void SELECTSQL(Connection conn,String compId, String EHF020403T0_02, String employee ,String day){
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = "" +
			" SELECT  " +
			" EHF020403T0_02 AS 部門代號," +
			" EHF020403T1_01 AS 員工工號," +
			" EHF020403T1_02 AS 感應卡號," +
			" DATE_FORMAT(EHF020404T0_11, '%Y/%m/%d') AS 考勤日期" +
			" FROM EHF020403T1 " +
			" LEFT JOIN "+
			" EHF020403T0 ON EHF020403T1_01 = EHF020403T0_01"+
			" AND EHF020403T1_06 = EHF020403T0_04"+
			" LEFT JOIN "+
			" EHF020404T0 ON EHF020403T1_02 = EHF020404T0_03"+
			" AND EHF020403T1_06 = EHF020404T0_09"+
			" WHERE   1 = 1" +
			" AND EHF020403T0_04 = '"+compId+"'" +
			" AND EHF020404T0_10='2'";
		
		
			if (!"".equals(EHF020403T0_02)) {
				sql += "AND EHF020403T0_02='" + EHF020403T0_02 + "'";// 部門代號
			}
			if (!"".equals(employee)) {
				sql += " AND EHF020403T1_01 = '" + employee + "'";// 員工工號
			}
			if (!"".equals(day)) {
				sql += " AND DATE_FORMAT(EHF020404T0_11, '%Y/%m/%d')= '" + day+ "'";// 考勤日期
			}
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				this.DELETE_Data(rs,compId);
			}
			rs.close();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 刪除門禁資料
	 * @param compId 
	 * @param employee
	 * @param cur_day
	 * @param pattern
	 * @param comp_id
	 */
	private void DELETE_Data(ResultSet rs, String compId){
		Statement stmt;
		Connection conn01 = null;

		try {
			conn01 = tools.getConnection("SPOS");
			stmt = conn01.createStatement();
			String sql = "UPDATE  EHF020404T0 SET EHF020404T0_FLAG=1" 
				+ " WHERE 1=1 "
				+ " AND EHF020404T0_03 = '" + rs.getString("感應卡號") + "'"  // 感應卡號
				+ " AND EHF020404T0_10 = '" + 2 + "'" // 資料來源為使用"門禁資料輸入作業"新增的資料
				+ " AND EHF020404T0_11 = '" + rs.getString("考勤日期") + "'"  // 考勤日期
				+ " AND EHF020404T0_09 = '" + compId + "'";// 公司代碼

			stmt.execute(sql);
			
			stmt.close();
			conn01.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				if (conn01 != null && !conn01.isClosed()) {
					conn01.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * 清除
	 * @param form
	 * @throws Exception
	 */
	private void cleanAddField( ActionForm form) throws Exception {
		
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		
		// 清掉畫面上的新增明細欄位
		Form.setDATA01("");
		Form.setDATA02("");
		Form.setDATA03("");
		Form.setDATA04("");
		Form.setDATA05("");
		Form.setDATA06("");
		Form.setDATA07("");		
	}
	
	
	
	
	
	
}