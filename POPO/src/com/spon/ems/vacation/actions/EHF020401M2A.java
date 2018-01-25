package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.ems.vacation.forms.EX020503R5F;
import com.spon.ems.vacation.forms.EX020507R0F;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.ems.vacation.tools.EMS_FixAttendance;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;


import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>考勤異常處理
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020401M2A extends NewDispatchAction {
	
	private static BA_TOOLS tools = BA_TOOLS.getInstance();
	private EMS_ACCESS ems_access;
	public EHF020401M2A() {
		ems_access = EMS_ACCESS.getInstance();
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
		
		EHF020401M0F Form = (EHF020401M0F) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		//產生 "是否修正" 的下拉選單
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0");
			bform.setItem_value("未修正");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("已修正");
			datas.add(bform);
			request.setAttribute("listEHF020401T0_FIX", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生 "顯示加班考勤" 的下拉選單
		try{
			List listOVERTIME_DATA_FLAG = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("0");
			bform.setItem_value("不顯示");
			listOVERTIME_DATA_FLAG.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("顯示");
			listOVERTIME_DATA_FLAG.add(bform);
			request.setAttribute("listOVERTIME_DATA_FLAG", listOVERTIME_DATA_FLAG);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生舊班別
		try{
			List EHF020401T0_04_list = new ArrayList();
			if(!"".equals(Form.getEHF020401T0_02_EMP()) && Form.getEHF020401T0_02_EMP() != null 
			   && !"".equals(Form.getEHF020401T0_10()) && Form.getEHF020401T0_10() != null ){
				EHF020401T0_04_list = tools.getSelectOptionByTable(conn, "EHF010100T0_01", 
																   "CONCAT('(',EHF010100T0_03,')',EHF010100T0_04)", "EHF010100T0",
															   	   "EHF010100T0_11", getLoginUser(request).getCompId(),
															   	   " AND EHF010100T0_02 = '"+Form.getEHF020401T0_10()+"' " +  //部門
															   	   " ORDER BY EHF010100T0_03 ","01");
			}
			request.setAttribute("EHF020401T0_04_list", EHF020401T0_04_list);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生--時-- 下拉選單
		try{
			List list_EHF020401T0_07_HH = new ArrayList();
			DecimalFormat df = new DecimalFormat("00");
			for (int i = 0; i < 24; i++) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				list_EHF020401T0_07_HH.add(bform);
			}
			request.setAttribute("list_EHF020401T0_07_HH",list_EHF020401T0_07_HH);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生--分-- 下拉選單
		try{
			List list_EHF020401T0_07_MM = new ArrayList();
			DecimalFormat df = new DecimalFormat("00");
			for (int i = 0; i < 60; i++) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				list_EHF020401T0_07_MM.add(bform);
			}
			request.setAttribute("list_EHF020401T0_07_MM", list_EHF020401T0_07_MM);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生 "打卡狀態" 的下拉選單
		try{
		List list_EHF020401T0_08_STATUS = new ArrayList();
		BA_ALLKINDForm bform = new BA_ALLKINDForm();
		bform.setItem_id("2");
		bform.setItem_value("下班");
		list_EHF020401T0_08_STATUS.add(bform);
		bform = new BA_ALLKINDForm();
		bform.setItem_id("6");
		bform.setItem_value("加班下班");
		list_EHF020401T0_08_STATUS.add(bform);
		request.setAttribute("list_EHF020401T0_08_STATUS", list_EHF020401T0_08_STATUS);
		}catch(Exception e) {
			System.out.println(e);
		}
		//產生假別項目
		try {
			List datas = new ArrayList();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = " SELECT EHF020100T0_03 , EHF020100T0_04 FROM EHF020100T0 " +
					     " WHERE 1=1 " +
					     " AND EHF020100T0_08 = '"+getLoginUser(request).getCompId()+"' ";
			
			ResultSet rs=stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			datas.add(bform);
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("EHF020100T0_03"));
				bform.setItem_value(rs.getString("EHF020100T0_04"));
				datas.add(bform);
			}
			
			rs.close();
			stmt.close();
			
			request.setAttribute("EHF010300T0_06_list", datas);
							
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		EHF020401M0F Form = new EHF020401M0F();
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
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
//			at_type = ems_tools.getSysParam(conn, getLoginUser(request).getCompId(), "ATTENDANCE_TYPE");
			
			generateSelectBox(conn, Form, request);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			
			request.setAttribute("collection", "show");
			request.setAttribute("button", "query");
			request.setAttribute("Form2Datas",list);
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
	 * 與魏先生的門禁機器整合的出勤查詢作業
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;
		EMS_ACCESS ems_access = EMS_ACCESS.getInstance();
		
		ArrayList list = new ArrayList();
		//ArrayList DepartureWorkEmplist = new ArrayList();//離職員工清單
		//ArrayList SuspensionWithoutPayEmplist = new ArrayList();//留職停薪員工清單
		
		
		String comp_id = getLoginUser(request).getCompId();
		String user_id = getLoginUser(request).getUserId();
		String user_code = getLoginUser(request).getUserCode();
		
		
		BaseFunction base_tools = new BaseFunction(getLoginUser(request).getCompId());
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		//傳入年月的第一天, dateformat = 'yyyy/MM/dd'
		String start_date ="";
		//傳入年月的最後一天, dateformat = 'yyyy/MM/dd'
		String end_date = "";
		
		Map empInfMap= new HashMap();
		
		try {
			
			//全部都不選，則表示查詢全部
			if(Form.getEHF020401T0_13()==null&&Form.getEHF020401T0_14()==null&&Form.getEHF020401T0_15()==null){
				Form.setEHF020401T0_13("0");
				Form.setEHF020401T0_14("0");
				Form.setEHF020401T0_15("0");
			}


			//在職員工清單
			//Map WorkEmp=ems_tools.getWorkEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
			
			//離職員工清單
			//Map DepartureWorkEmp=ems_tools.getDepartureWorkEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
			
			//留職停薪員工清單
			//Map SuspensionWithoutPayEmp=ems_tools.getSuspensionWithoutPayEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());	

			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			
			//進行資料檢核
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			
			//建立考勤元件
			EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(comp_id, "", user_id );
			
			
			if (lc_errors.isEmpty()) {
				
				//是否顯示加班考勤資料flag
				boolean overtime_data_flag = tools.StringToBoolean(Form.getOVERTIME_DATA_FLAG());

				//取得考勤異常資料清單
				list = fix_att_tools.getAttAbnormalList(conn, Form, getLoginUser(request), false, overtime_data_flag, "", "" );
				/*
				HR_TOOLS hr_tools = new HR_TOOLS();
				String tmp_emp_id ="";
				//將取得的清單，依照員工狀態分類，依序為在職，離職，留職停薪。
				
				Iterator it = list.iterator();
				while(it.hasNext()){
					EHF020401M0F	list_form = (EHF020401M0F) it.next();

					boolean chk_flag = false;//在職判斷
					boolean SuspensionWithoutPay= false;//留職停薪判斷
					boolean DepartureWork= false;//離職停薪判斷
					
					//取得EMS-Flow員工基本資料Map
					empInfMap = hr_tools.getEmpInfMapByEmpId(user_id,list_form.getEHF020403T0_01(), comp_id);			
					
					//傳入薪資年月(SalYYMM), 取得薪資年月的第一天, dateformat = 'yyyy/MM/dd'
					start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(Form.getEHF020401T0_05()+"/01")));
					//傳入薪資年月(SalYYMM), 取得薪資年月的最後一天, dateformat = 'yyyy/MM/dd'
					end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(Form.getEHF020401T0_05()+"/01")));
					
					
					//判斷查詢到的異常考勤是否為離職日之後的，如果是，則必須要刪除。
					if(!this.determineGenerateSchedulingData(empInfMap, list_form.getEHF020401T0_DATE())){
						//設定考勤日期
						att.setSta_cur_day( list_form.getEHF020401T0_DATE() );
						//刪除員工考勤資料
						
						att.delAllAttDataForm(conn, list_form.getEHF020403T0_01());  // 員工工號
						
						it.remove();
						continue;
					}

					//1.先處理留職停薪人員
					if("0".equals(Form.getEHF020401T0_15())||"3".equals(Form.getEHF020401T0_15())){
						Iterator departure_emp_it = SuspensionWithoutPayEmp.keySet().iterator();
						while (departure_emp_it.hasNext()) {
							tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
							if (tmp_emp_id.equals(list_form.getEHF020403T0_01())) {
								SuspensionWithoutPay = true;
								break;
							} else {
								SuspensionWithoutPay = false;
							}
						}
					}
				
					//2.處理離職員工
					if("0".equals(Form.getEHF020401T0_14())||"2".equals(Form.getEHF020401T0_14())){
						Iterator departure_emp_it = DepartureWorkEmp.keySet().iterator();
						while (departure_emp_it.hasNext()) {
							tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
							if (tmp_emp_id.equals(list_form.getEHF020403T0_01())) {
								DepartureWork = true;
								break;
							} else {
								DepartureWork = false;
							}
						}
					}
				
					//3.處理在職員工
					if("0".equals(Form.getEHF020401T0_13())||"1".equals(Form.getEHF020401T0_13())){
						Iterator departure_emp_it = WorkEmp.keySet().iterator();
						while (departure_emp_it.hasNext()) {
							tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
							if (tmp_emp_id.equals(list_form.getEHF020403T0_01())) {
								chk_flag = true;
								break;
							} else {
								chk_flag = false;
							}
						}
					}
					
					
					if(chk_flag){
						//處理填單人資料Mapping
						list_form.setEHF020403T0_02(list_form.getEHF020403T0_02()+"");

					}else if(DepartureWork){
						//處理填單人資料Mapping
						list_form.setEHF020403T0_02(list_form.getEHF020403T0_02()+"(離職)");
						DepartureWorkEmplist.add(list_form);
						it.remove();
					}else if(SuspensionWithoutPay){
						//處理填單人資料Mapping
						list_form.setEHF020403T0_02(list_form.getEHF020403T0_02()+"(留職停薪)");
						SuspensionWithoutPayEmplist.add(list_form);
						it.remove();
					}else{
						it.remove();
					}	
				}
				list.addAll(DepartureWorkEmplist);
				list.addAll(SuspensionWithoutPayEmplist);
				*/
				if(list.size() > 0){
					
					request.setAttribute("MSG",(request.getAttribute("MSG")==null?"查詢成功!!":request.getAttribute("MSG"))+
					"    "+(request.getAttribute("IMSG")==null?"":request.getAttribute("IMSG")));
				}else{
					
					request.setAttribute("MSG",(request.getAttribute("MSG")==null?"查無資料!!":request.getAttribute("MSG"))+
					"    "+(request.getAttribute("IMSG")==null?"":request.getAttribute("IMSG")));
				}

				//判斷身分別
				ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
				
				Form.setEHF020401T0_PS("");  //清空修正備註欄位
				
				request.setAttribute("Form2Datas",list);
				
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				
				request.setAttribute("Form1Datas", Form);
				
				String sql = "" +
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
				" FROM EHF020900T0 " +
				" WHERE 1=1 " +
				" AND EHF020900T0_M2 = '"+Form.getEHF020401T0_05()+"'"+	//考勤年月
				" AND EHF020900T0_04 = '"+getLoginUser(request).getCompId()+"'"; //公司代碼

				List dataList = base_tools.queryList(sql);
				
				//增加判斷  判斷是否有考勤確認資料  控制前端顯示按鈕
				if (dataList.size() <= 0) {
					request.setAttribute("buttonSHOW", "YES");
					request.setAttribute("SHOW", "YES");
				}else{
					Map datamap=(Map)dataList.get(0);
					if("01".equals(datamap.get("EHF020900T0_02"))){
						request.setAttribute("buttonSHOW", "YES");//顯示自動修正、確認考勤、強制修正
						request.setAttribute("SHOW", "YES");
					}else if("02".equals(datamap.get("EHF020900T0_02"))){
						request.setAttribute("buttonSHOW", "NO");//顯示回復考勤
						request.setAttribute("SHOW", "NO");
					}else if("03".equals(datamap.get("EHF020900T0_02"))){
						request.setAttribute("MSG", Form.getEHF020401T0_05()+"已換休過，不可回復考勤");
						request.setAttribute("SHOW", "NO");
					}else{
						request.setAttribute("buttonSHOW", "YES");//顯示自動修正、確認考勤、強制修正
						request.setAttribute("SHOW", "YES");
					}
				}	
				
				
				Form.setEHF020403T0_02("");
				Form.setEHF000200T0_02("");	
				Form.setEHF000200T0_03("");	
				
				Form.setEHF020403T0_01("");	
				Form.setEHF010100T0_02("");	
				Form.setEHF010100T0_05("");	
				
				Form.setEHF020401T0_13("");
				Form.setEHF020401T0_14("");
				Form.setEHF020401T0_15("");
				
				
			} else {
				
				//判斷身分別
				ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				
				generateSelectBox(conn, Form, request);
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form2Datas",list);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				Form.setEHF020403T0_01("");
				//Form.setEHF020403T0_01_TXT("");
				Form.setEHF020403T0_02("");
				//Form.setEHF020403T0_02_TXT("");	
				
				Form.setEHF020401T0_13("");
				Form.setEHF020401T0_14("");
				Form.setEHF020401T0_15("");
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("Form2Datas",list);
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("MSG", "查詢失敗!");
			System.out.println("EHF020401M2A.addData() " + e);
			System.out.println(e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				base_tools.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return mapping.findForward("success");
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
//		BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		
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
			
//			at_type = ems_tools.getSysParam(conn, getLoginUser(request).getCompId(), "ATTENDANCE_TYPE");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*
		 if("0001".equals(at_type)){
				//與魏先生的卡鐘機器整合的出勤查詢作業
				return queryDatas(mapping, form, request, response);
				
			}else if("0002".equals(at_type)){
				//與魏先生的門禁機器整合的出勤查詢作業
				return queryDatas2(mapping, form, request, response);
			}else{
				
				return queryDatas(mapping, form, request, response);
			}
		*/
		 
		 return queryDatas(mapping, form, request, response);
	}

	
	/**
	 * 畫面導向個人出勤查詢作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward redirect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("redirect");
	}
	
	/**
	 * 系統自動修正考勤異常資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward autoFixAttData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
//		BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;
		EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		ArrayList list = new ArrayList();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			request.setAttribute("action", "autoFixAttData");
			//檢核考勤異常年月是否有問題
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			//Login user bean
			AuthorizedBean authbean = getLoginUser(request);
			
			if (lc_errors.isEmpty() ) {
				
				//產生考勤元件
				EMS_AttendanceAction ems_att_tools = EMS_AttendanceAction.getInstance( authbean.getCompId(), "", authbean.getUserId() );
				
				//暫存查詢條件
				String tmp_FIX = Form.getEHF020401T0_FIX();
				
				int Count = 0;
				int NGCount = 0;
				
				Form.setEHF020401T0_FIX("0");  //設定"是否已修正"的條件  ---> 未修正
				
				//是否顯示加班考勤資料flag
				boolean overtime_data_flag = tools.StringToBoolean(Form.getOVERTIME_DATA_FLAG());
				
				list = fix_att_tools.getAttAbnormalList(conn, Form, getLoginUser(request), false, overtime_data_flag, "", "" );
				
				Iterator it = list.iterator();
				
				//系統自動修正考勤異常資料
				while(it.hasNext()){
					
					EHF020401M0F ehf020401m0 = (EHF020401M0F) it.next();
					
					if(fix_att_tools.autoFixAttAbnormal(conn, ems_att_tools, ehf020401m0, authbean.getUserId())){
						//有修正
						Count++;
					}else{
						//未修正
						NGCount++;
					}
					
				}
				
				if(Count > 0){
					//commit to 資料庫
					conn.commit();
				}
				
				request.setAttribute("IMSG", "EMS系統共自動修正 "+Count+"筆資料" );
				
				//恢復原本的查詢條件
				Form.setEHF020401T0_FIX(tmp_FIX);

			} else {
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				return queryDatas(mapping, form, request, response);
//				return mapping.findForward("success");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 return queryDatas(mapping, form, request, response);
	}
	
	/**
	 * 人資批次強制修正考勤異常資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward fixAttData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
//		BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;
		EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		
		
		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId");

		if ((arrid==null?false:arrid.length > 0)){
    		formKey = arrid ;
		}else{
    		request.setAttribute("MSG","請至少選取一筆考勤異常資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		ActionErrors lc_errors = new ActionErrors();
		
		//檢核修正備註不可為空白
		try{
			BA_Vaildate ve = BA_Vaildate.getInstance();
			ve.isEmpty(lc_errors, Form.getEHF020401T0_PS(), "EHF020401T0_PS", "不可空白");
			
			if("".equals(Form.getEHF020401T0_PS()) || Form.getEHF020401T0_PS() == null ){
				request.setAttribute("MSG", "修正備註不可空白!!" );
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			
			if (lc_errors.isEmpty() ) {
				
				AuthorizedBean authbean = getLoginUser(request);
				
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				//取得公司名稱
				Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
				//員工Map
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				//部門Map
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
				
				int Count = 0;
				int NGCount = 0;
				
				
				//批次強制修正
				for(int i=0; i<formKey.length; i++){
					
					String[] UID = formKey[i].split("@");
					Form.setEHF020401T0_UID(UID[0]);  //UID
					Form.setEHF020401T0_TYPE(UID[1]);  //表單別
					Form.setEHF020401T0_11(authbean.getCompId());  //公司代碼
					Form.setEHF020401T0_SUID(formKey[i]);  //SUID
					
					if(fix_att_tools.fixAttAbnormal(conn, Form, (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DESC") 
							
							, authbean.getUserName(), authbean.getUserId())){
						Count++;
					}else{
						NGCount++;
					}
					
				}
				
				if(Count > 0){
					//commit to 資料庫
					conn.commit();
				}
				
				request.setAttribute("IMSG", "共修正 "+Count+"筆資料" );
				hr_tools.close();
			} else {
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				return queryDatas(mapping, form, request, response);
//				return mapping.findForward("success");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 return queryDatas(mapping, form, request, response);
	}
	
	/**
	 * 人資批次還原修正考勤異常資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward recoveryFixAttData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
//		BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;
		EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		
		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId");

		if ((arrid==null?false:arrid.length > 0)){
    		formKey = arrid ;
		}else{
    		request.setAttribute("MSG","請至少選取一筆要還原的考勤異常資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ActionErrors lc_errors = new ActionErrors();
		
		try{
			
			if (lc_errors.isEmpty() ) {
				
				AuthorizedBean authbean = getLoginUser(request);
				
				int Count = 0;
				int NGCount = 0;
				
				
				//批次還原修正
				for(int i=0; i<formKey.length; i++){
					
					String[] UID = formKey[i].split("@");
					Form.setEHF020401T0_UID(UID[0]);  //UID
					Form.setEHF020401T0_TYPE(UID[1]);  //表單別
					Form.setEHF020401T0_11(authbean.getCompId());  //公司代碼
					Form.setEHF020401T0_SUID(formKey[i]);  //SUID
					
					if(fix_att_tools.recoveryFixAttAbnormal(conn, Form, authbean.getUserName(), authbean.getUserId())){
						Count++;
					}else{
						NGCount++;
					}
					
				}
				
				if(Count > 0){
					//commit to 資料庫
					conn.commit();
				}
				
				request.setAttribute("IMSG", "共還原 "+Count+"筆資料" );

			} else {

				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				return queryDatas(mapping, form, request, response);
//				return mapping.findForward("success");
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 return queryDatas(mapping, form, request, response);
	}
	
	
	/**
	 * 調班功能
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward transferClass(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;
		EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		
		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId");

		if ((arrid==null?false:arrid.length > 0)){
    		formKey = arrid ;
		}else{
    		request.setAttribute("MSG","請選取一筆考勤異常資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ActionErrors
		ActionErrors lc_errors = new ActionErrors();
		
		try{
			
			if (lc_errors.isEmpty() ) {
				
				ArrayList list = new ArrayList();
				
				AuthorizedBean authbean = getLoginUser(request);
				Map ATT_MAP = null;
				//調班處理 - 先取出原始資料的考勤資訊
				ATT_MAP = fix_att_tools.getAttDateBySUID(conn, formKey[0], authbean.getCompId(), authbean.getUserName());
				
				if(ATT_MAP.size() > 0){
					list = fix_att_tools.getAttDatasByAttDate( conn, (String)ATT_MAP.get("EMP_ID"), (String)ATT_MAP.get("ATT_DATE"), 
														   	   "", authbean.getUserId(), authbean.getCompId());
					
					//設定相關考勤資訊
					Form.setEHF020401T0_02_EMP((String)ATT_MAP.get("EMP_ID"));
					Form.setEHF020401T0_05_DATE((String)ATT_MAP.get("ATT_DATE"));
					Form.setEHF020401T0_04((String)ATT_MAP.get("CLASS_NO"));  //舊班別
				}
				
				request.setAttribute("open_chg_class","true");
				request.setAttribute("chg_class","yes");
				request.setAttribute("Form3Datas",list);
				
			} else {
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				return queryDatas(mapping, form, request, response);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 return changeSelectBox(mapping, form, request, response);
	}*/
	
	/**
	 * 產生班別的下拉選單
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward changeSelectBox(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;

		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			AuthorizedBean authbean = getLoginUser(request);
			Map depMap = null;
			if(!"".equals(Form.getEHF020401T0_02_EMP()) && Form.getEHF020401T0_02_EMP() != null ){
				//取得員工的目前班別設定
				AuthorizedBean emp_authbean = new AuthorizedBean();
				emp_authbean.setUserId(authbean.getUserId());
				emp_authbean.setEmployeeID(Form.getEHF020401T0_02_EMP());
				emp_authbean.setCompId(authbean.getCompId());
				
				//取得員工主要歸屬部門
				depMap = ems_tools.getEmpBelongMainDepInf(emp_authbean.getUserId(), emp_authbean.getEmployeeID(), emp_authbean.getCompId(), "");
				//設定部門
				Form.setEHF020401T0_10((String)depMap.get("DEPT_ID"));
				
				/*
				Map empClassMap = ems_tools.getEmpClass(conn, emp_authbean);
				if(!empClassMap.isEmpty()){
					Form.setEHF020401T0_04((Integer)empClassMap.get("WORK_CLASS_NO")+"");  //舊班別
				}
				*/
			/*}
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	    return queryDatas(mapping, form, request, response);
	}*/
	
	/**
	 * 執行調班(不更新資料庫)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward transfer( ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response ) {
		
		return this.transfer(mapping, form, request, response, false);
	}*/
	
	/**
	 * 執行調班(更新資料庫)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward finishTransfer( ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response ) {
		
		return this.transfer(mapping, form, request, response, true);
	}*/
	
	/**
	 * 執行調班
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param commit_flag
	 * @return
	 */
	/*private ActionForward transfer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response,
								   boolean commit_flag ) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;
		EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ActionErrors
		ActionErrors lc_errors = new ActionErrors();
		
		try{
			
			if (lc_errors.isEmpty() ) {
								
				AuthorizedBean authbean = getLoginUser(request);
				
				//執行調班功能
				Map transClassInf = fix_att_tools.transferClass( conn, Form.getEHF020401T0_FID(), Form.getEHF020401T0_02_EMP(), 
																 Form.getEHF020401T0_05_DATE(), 
																 authbean.getUserId(), authbean.getUserName(), authbean.getCompId(),
																 commit_flag );
				
				/*
				if(!"".equals(Form.getEHF020401T0_02_EMP()) && Form.getEHF020401T0_02_EMP() != null && !"".equals(Form.getEHF020401T0_05_DATE()) 
				   && Form.getEHF020401T0_05_DATE() != null && !"".equals(Form.getEHF020401T0_FID()) && Form.getEHF020401T0_FID() != null ){
					
					//取得調班前的考勤資料
					list = fix_att_tools.getAttDatasByAttDate( conn, Form.getEHF020401T0_02_EMP(), Form.getEHF020401T0_05_DATE(), 
														   	   authbean.getUserId(), authbean.getCompId());
					//建立考勤產生元件
					EMS_AttendanceAction att_act_tools = EMS_AttendanceAction.getInstance( authbean.getCompId(), Form.getEHF020401T0_05_DATE(),
																						   authbean.getUserId() );
					//轉換後的考勤資料產生
					att_act_tools.execute_Tmp_Attendance(conn, Integer.parseInt(Form.getEHF020401T0_FID()), Form.getEHF020401T0_02_EMP(), true);
					
					//取得調班後的考勤資料
					list2 = fix_att_tools.getAttDatasByAttDate( conn, Form.getEHF020401T0_02_EMP(), Form.getEHF020401T0_05_DATE(), 
															    authbean.getUserId(), authbean.getCompId());
					
				}
				*/
				
				/*if(commit_flag){
					request.setAttribute("open_chg_class","false");
				}else{
					request.setAttribute("open_chg_class","true");
				}
				request.setAttribute("chg_class","yes");
				request.setAttribute("after_chg_class","yes");
				request.setAttribute("Form3Datas",(ArrayList)transClassInf.get("BEFORE_LIST"));
				request.setAttribute("Form4Datas",(ArrayList)transClassInf.get("AFTER_LIST"));
				
				if(commit_flag){
					//更新資料庫
					conn.commit();
					System.out.println("TranferClass DATABASE is commit!!");
				}else{
					//還原資料庫SQL處理
					conn.rollback();
				}
				
			} else {
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				return queryDatas(mapping, form, request, response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 return changeSelectBox(mapping, form, request, response);
	}*/
	
	/**
	 * 查詢欲修改的考勤資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward editClass(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;
		EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		
		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId");

		if ((arrid==null?false:arrid.length > 0)){
    		formKey = arrid ;
		}else{
    		request.setAttribute("MSG","請選取一筆考勤異常資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ActionErrors
		ActionErrors lc_errors = new ActionErrors();
		
		try{
			
			if (lc_errors.isEmpty() ) {
				
				boolean chk_flag = false;
				ArrayList list = new ArrayList();
				
				AuthorizedBean authbean = getLoginUser(request);
				Map ATT_MAP = null;
				//考勤資料修改處理 - 先取出原始資料的考勤資訊
				ATT_MAP = fix_att_tools.getAttDateBySUID(conn, formKey[0], authbean.getCompId(), authbean.getUserName());
				
				if(ATT_MAP.size() > 0){
					
					list = fix_att_tools.getAttDatasByAttDate( conn, (String)ATT_MAP.get("EMP_ID"), (String)ATT_MAP.get("ATT_DATE"), 
															   (String)ATT_MAP.get("CARD_STATUS"), authbean.getUserId(), authbean.getCompId());
					//取得考勤資料或未打卡資料
					EHF020401M0F ehf020401m0 = (EHF020401M0F) list.get(0);
					
					//檢核是否開啟前端打卡狀態的選取功能
					chk_flag = this.chkOpenCardStatusSelect( conn, null, Integer.parseInt(ehf020401m0.getEHF020403T1_03()), 
												  			 (String)ATT_MAP.get("CARD_STATUS"), 
												  			 (String)ATT_MAP.get("ATT_DATE"), (String)ATT_MAP.get("EMP_ID"),
												  			 authbean.getUserId(), authbean.getCompId());
					
					//設定相關考勤資訊
					Form.setEHF020401T0_02_EMP((String)ATT_MAP.get("EMP_ID"));  //員工工號
					Form.setEHF020401T0_02(ehf020401m0.getEHF020401T0_02_EMP());  //員工姓名
					Form.setEHF020401T0_05_DATE((String)ATT_MAP.get("ATT_DATE"));  //考勤日期
					Form.setEHF020401T0_04(ehf020401m0.getEHF020401T0_04());  //班別
					Form.setEHF020401T0_08((String)ATT_MAP.get("CARD_STATUS"));  //狀態代碼
					
					//Form.setEHF020401T0_08_STATUS(chk_flag==true?(String)ATT_MAP.get("CARD_STATUS"):ehf020401m0.getEHF020401T0_08());  //狀態
					Form.setEHF020401T0_08_STATUS((String)ATT_MAP.get("CARD_STATUS"));  //狀態
					
					//設定考勤打卡時間,  若是未打卡資料則使用班別對應打卡狀態的設定時間
					Form.setEHF020401T0_07(ehf020401m0.getEHF020401T0_07());  //打卡日期時間
					Form.setEHF020401T0_07_HH(ehf020401m0.getEHF020401T0_07_HH());  //打卡時
					Form.setEHF020401T0_07_MM(ehf020401m0.getEHF020401T0_07_MM());  //打卡分
					Form.setEHF020401T0_09_DESC(ehf020401m0.getEHF020401T0_09_DESC());  //異常說明
					
				}
				
				//控制前端狀態開關
				if(chk_flag){
					request.setAttribute("open_card_status_select","true");
				}else{
					request.setAttribute("open_card_status_select","false");
				}
				request.setAttribute("open_edit_class","true");
				request.setAttribute("edit_class","yes");
				
			} else {
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				return queryDatas(mapping, form, request, response);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 return queryDatas(mapping, form, request, response);
	}*/
	
	/**
	 * 外部執行修改考勤資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward editAttForm( ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response ) {
		
		return this.editAtt(mapping, form, request, response);
	}*/
	
	/**
	 * 實際執行修改考勤資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*private ActionForward editAtt( ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020401M0F Form = (EHF020401M0F) form;
		EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//ActionErrors
		ActionErrors lc_errors = new ActionErrors();
		
		try{
			
			if (lc_errors.isEmpty() ) {
				boolean chk_flag = false;
				ArrayList list = new ArrayList();
				AuthorizedBean authbean = getLoginUser(request);
				
				//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
				String ems_version = tools.getSysParam(conn, authbean.getCompId(), "EMS_VERSION");
				
				//產生考勤元件
				EMS_AttendanceAction ems_att_tools = EMS_AttendanceAction.getInstance( authbean.getCompId(), Form.getEHF020401T0_05_DATE(),
																					   authbean.getUserId());
				
				list = fix_att_tools.getAttDatasByAttDate( conn, Form.getEHF020401T0_02_EMP(), Form.getEHF020401T0_05_DATE(), 
						   								   Form.getEHF020401T0_08(), authbean.getUserId(), authbean.getCompId());
				
				//取得考勤資料或未打卡資料
				EHF020401M0F ehf020401m0 = (EHF020401M0F) list.get(0);
				
				//檢核是否開啟前端打卡狀態的選取功能
				chk_flag = this.chkOpenCardStatusSelect( conn, ems_att_tools, Integer.parseInt(ehf020401m0.getEHF020403T1_03()), 
														 Form.getEHF020401T0_08(), 
														 Form.getEHF020401T0_05_DATE(), Form.getEHF020401T0_02_EMP(),
											  			 authbean.getUserId(), authbean.getCompId());
				
				//準備考勤資料的Form
				EHF020401M3F ehf020401m3 = new EHF020401M3F();
				ehf020401m3.setEHF020403T0_01(Form.getEHF020401T0_02_EMP());  //員工工號
				ehf020401m3.setEHF020401T0_TYPE(ehf020401m0.getEHF020401T0_TYPE());  //資料類型
				ehf020401m3.setEHF020401T0_02(ehf020401m0.getEHF020401T0_02());  //感應卡號
				ehf020401m3.setEHF020401T0_07(Form.getEHF020401T0_07());  //打卡日期時間
				ehf020401m3.setEHF020401T0_07_HH(Form.getEHF020401T0_07_HH());  //打卡時
				ehf020401m3.setEHF020401T0_07_MM(Form.getEHF020401T0_07_MM());  //打卡分
				ehf020401m3.setEHF020401T0_08(Form.getEHF020401T0_08());  //打卡狀態代碼
				ehf020401m3.setEHF020401T0_08_STATUS(Form.getEHF020401T0_08_STATUS());  //打卡狀態
				ehf020401m3.setUSER_CREATE(authbean.getUserName());  //修改者姓名
				
				//修改考勤資料
				ems_att_tools.EditAttendanceDataByUser( conn, ehf020401m3, Integer.parseInt(ehf020401m0.getEHF020403T1_03()), 
														authbean.getCompId(), true);
				
				String cur_card_status = "";
				if("1.6".equals(ems_version)){
					cur_card_status = ehf020401m3.getEHF020401T0_08();
				}else{
					cur_card_status = (chk_flag==true?Form.getEHF020401T0_08_STATUS():ehf020401m0.getEHF020401T0_08());
				}
				
				//取得修改後的資料並且顯示於前端的欄位中
				list = fix_att_tools.getAttDatasByAttDate( conn, Form.getEHF020401T0_02_EMP(), Form.getEHF020401T0_05_DATE(),
														   //chk_flag==true?Form.getEHF020401T0_08_STATUS():Form.getEHF020401T0_08(),
														   cur_card_status,
														   authbean.getUserId(), authbean.getCompId());
				
				//判斷是否有資料可於前端頁面顯示, 避免發生取不到List內容的例外
				if( list.size() > 0 ){
					//取得考勤資料或未打卡資料
					ehf020401m0 = (EHF020401M0F) list.get(0);
					
					//設定相關考勤資訊
					Form.setEHF020401T0_02_EMP(Form.getEHF020401T0_02_EMP());  //員工工號
					Form.setEHF020401T0_02(ehf020401m0.getEHF020401T0_02_EMP());  //員工姓名
					Form.setEHF020401T0_05_DATE(Form.getEHF020401T0_05_DATE());  //考勤日期
					Form.setEHF020401T0_04(ehf020401m0.getEHF020401T0_04());  //班別
					
					//Form.setEHF020401T0_08(Form.getEHF020401T0_08());  //狀態代碼
					Form.setEHF020401T0_08(cur_card_status);  //狀態代碼

					//Form.setEHF020401T0_08_STATUS(chk_flag==true?Form.getEHF020401T0_08_STATUS():ehf020401m0.getEHF020401T0_08());  //狀態
					Form.setEHF020401T0_08_STATUS(cur_card_status);  //狀態
					
					//設定考勤打卡時間,  若是未打卡資料則使用班別對應打卡狀態的設定時間
					Form.setEHF020401T0_07(ehf020401m0.getEHF020401T0_07());  //打卡日期時間
					Form.setEHF020401T0_07_HH(ehf020401m0.getEHF020401T0_07_HH());  //打卡時
					Form.setEHF020401T0_07_MM(ehf020401m0.getEHF020401T0_07_MM());  //打卡分
					Form.setEHF020401T0_09_DESC(ehf020401m0.getEHF020401T0_09_DESC());  //異常說明
					
					//控制前端狀態開關
					if(chk_flag){
						request.setAttribute("open_card_status_select","true");
					}else{
						request.setAttribute("open_card_status_select","false");
					}
					request.setAttribute("open_edit_class","true");
					request.setAttribute("edit_class","yes");
				}
				
				if(true){
					//更新資料庫
					conn.commit();
					System.out.println("editClass DATABASE is commit!!");
				}else{
					//還原資料庫SQL處理
					conn.rollback();
				}
				
			} else {
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				return queryDatas(mapping, form, request, response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		 return queryDatas(mapping, form, request, response);
	}*/
	
	/**
	 * 檢核是否開啟打卡狀態的選取功能
	 * @param conn
	 * @param ems_att_tools
	 * @param class_no
	 * @param card_status
	 * @param employee_id
	 * @param user_id
	 * @param comp_id
	 * @return
	 */
	/*private boolean chkOpenCardStatusSelect( Connection conn, EMS_AttendanceAction ems_att_tools, int class_no, String card_status,
										     String att_date, String employee_id, String user_id, String comp_id ){
		
		boolean chk_flag = false;
		try{
			if(ems_att_tools == null){
				//產生考勤元件
				ems_att_tools = EMS_AttendanceAction.getInstance( comp_id, att_date, user_id);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			BA_TOOLS tools = BA_TOOLS.getInstance();
			
			//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
			String ems_version = tools.getSysParam(conn, comp_id, "EMS_VERSION");
			
			//下班的考勤資料&未打卡資料, 需額外判斷班別是有加班設定, 若有設定加班則要判斷加班資料是否記錄"下班與加班上班",
			//若是不記錄則要判斷修改的"打卡日期時間"是否符合加班下班的時間條件, 如果有符合則要將狀態更改為 '加班下班' 進行後續處理
			//且原始的打卡狀態 = 下班(2)
			
			//chkOVTRecordProcess() -> 考勤日期有加班設定 且 設定不記錄加班上下班 --> true, 其他狀態 --> false
			if( "2".equals(card_status) && ems_att_tools.chkOVTRecordProcess( conn, class_no, employee_id, comp_id) ){
				//有符合下班打卡日期時間有可能觸發加班處理狀況
				if("1.6".equals(ems_version)){
					chk_flag = false;
				}else{
					chk_flag = true;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}*/
	

	/**
	 * 確認考勤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		Connection conn = null;
		EHF020401M0F Form =(EHF020401M0F)form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String comp_id = getLoginUser(request).getCompId();
		BaseFunction base_tools = new BaseFunction(comp_id);
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			//Query
			String sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2 " +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			" AND EHF020900T0_M2 = '"+Form.getEHF020401T0_05()+"'"+	//考勤年月
			" AND EHF020900T0_04 = '"+comp_id+"'"; //公司代碼

			List dataList = base_tools.queryList(sql);
			if (dataList.size() <= 0) {
				sql = ""
						+ " INSERT INTO ehf020900t0 ("
						+ " EHF020900T0_U,  EHF020900T0_M,  EHF020900T0_M1,   EHF020900T0_M2, "
						+ " EHF020900T0_02, EHF020900T0_03, EHF020900T0_03FK, EHF020900T0_04,"
						+ " USER_CREATE, USER_UPDATE, VERSION,DATE_CREATE, DATE_UPDATE) "
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1,NOW(), NOW()) ";

				String UID = tools.createEMSUID(conn, "ATT", "EHF020900T0", "EHF020900T0_U", comp_id);
				// 執行SQL
				PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, UID); //考勤薪資記錄UID
				indexid++;
				p6stmt.setString(indexid, ""); //入扣帳年月
				indexid++;
				p6stmt.setString(indexid, ""); //薪資年月
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020401T0_05()); //考勤年月
				indexid++;
				p6stmt.setString(indexid, "02"); //考勤處理狀態
				indexid++;
				p6stmt.setString(indexid, "00"); //薪資處理狀態
				indexid++;
				p6stmt.setString(indexid, ""); //薪資處理UID
				indexid++;
				p6stmt.setString(indexid, comp_id); //
				indexid++;
				p6stmt.setString(indexid, "SYSTEM"); // 建立人員
				indexid++;
				p6stmt.setString(indexid, "SYSTEM"); // 修改人員
				indexid++;
				//System.out.println("sql ==>"+p6stmt.getQueryFromPreparedStatement());
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				//將考勤資料寫入歷史資料庫內。 20140713 by Hedwig
				//EMS_HistoryAction history = new EMS_HistoryAction();
				//先刪除之前有寫入歷史區的資料  20150427 by Alvin
				//history.insertHrHistory(base_tools.getConn(), getLoginUser(request).getUserId(), getLoginUser(request).getUserId(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), false, (OrganizationApi) getEmscontext().getBean("esaOrgOrganizationApi"), (EmployeeApi)getEmscontext().getBean("esaOrgEmployeeApi"), getLoginUser(request).getCompId());
				//history.insertAttHistory(base_tools.getConn(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), false, getLoginUser(request).getCompId());
				//base_tools.commit();
				//將考勤資料寫入歷史資料庫內。 20140713 by Hedwig
				//history.insertHrHistory(base_tools.getConn(), getLoginUser(request).getUserId(), getLoginUser(request).getUserId(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), true, (OrganizationApi) getEmscontext().getBean("esaOrgOrganizationApi"), (EmployeeApi)getEmscontext().getBean("esaOrgEmployeeApi"), getLoginUser(request).getCompId());
				//history.insertAttHistory(base_tools.getConn(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), true, getLoginUser(request).getCompId());
				base_tools.commit();
				request.setAttribute("MSG", "確認考勤成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				base_tools.close();
			}
			else{

				sql = ""
						+ " UPDATE ehf020900t0 SET"
						+ " EHF020900T0_02=?  " 
						+ " WHERE 1=1 " 
						+ " AND EHF020900T0_04 = '"+comp_id+"'" 
						+ " AND EHF020900T0_M2 = '"+Form.getEHF020401T0_05()+"'";

				// 執行SQL
				PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, "02"); //
				indexid++;
				//System.out.println("sql ==>"+p6stmt.getQueryFromPreparedStatement());
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();
				//將考勤資料寫入歷史資料庫內。 20140713 by Hedwig
				//EMS_HistoryAction history = new EMS_HistoryAction();
				//先刪除之前有寫入歷史區的資料  20150427 by Alvin
				//history.insertHrHistory(base_tools.getConn(), getLoginUser(request).getUserId(), getLoginUser(request).getUserId(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), false, (OrganizationApi) getEmscontext().getBean("esaOrgOrganizationApi"), (EmployeeApi)getEmscontext().getBean("esaOrgEmployeeApi"), getLoginUser(request).getCompId());
				//history.insertAttHistory(base_tools.getConn(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), false, getLoginUser(request).getCompId());
				base_tools.commit();
				//將考勤資料寫入歷史資料庫內。 20140713 by Hedwig
				//history.insertHrHistory(base_tools.getConn(), getLoginUser(request).getUserId(), getLoginUser(request).getUserId(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), true, (OrganizationApi) getEmscontext().getBean("esaOrgOrganizationApi"), (EmployeeApi)getEmscontext().getBean("esaOrgEmployeeApi"), getLoginUser(request).getCompId());
				//history.insertAttHistory(base_tools.getConn(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), true, getLoginUser(request).getCompId());
				base_tools.commit();
				request.setAttribute("MSG", "確認考勤成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "");
				request.setAttribute("show", "yes");
				request.setAttribute("collection", "");
				base_tools.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		 return queryForm(mapping, form, request, response);
	}	
	
	/**
	 * 回覆考勤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward recoveryConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		Connection conn = null;
		EHF020401M0F Form =(EHF020401M0F)form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String comp_id = getLoginUser(request).getCompId();
		BaseFunction base_tools = new BaseFunction(comp_id);
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{

			//Query
			String sql = "" +
			" SELECT  * " +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			" AND EHF020900T0_M2 = '"+Form.getEHF020401T0_05()+"'"+	//考勤年月
			" AND EHF020900T0_04 = '"+comp_id+"'"; //公司代碼

			List dataList = base_tools.queryList(sql);
			
			Map List=(Map)dataList.get(0);
			if (Integer.valueOf((String)List.get("EHF020900T0_03"))<2) {

				// 新增門禁刷卡資料
				sql = ""
						+ " UPDATE ehf020900t0 SET"
						+ " EHF020900T0_02=?  " 
						+ " WHERE 1=1 " 
						+ " AND EHF020900T0_04 = '"+comp_id+"'" 
						+ " AND EHF020900T0_M2 = '"+Form.getEHF020401T0_05()+"'";

				// 執行SQL
				PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, "01"); //
				indexid++;
				//System.out.println("sql ==>"+p6stmt.getQueryFromPreparedStatement());
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				//將考勤資料寫入歷史資料庫內。 20140713 by Hedwig
				//EMS_HistoryAction history = new EMS_HistoryAction();
				//history.insertHrHistory(base_tools.getConn(), getLoginUser(request).getUserId(), getLoginUser(request).getUserId(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), false, (OrganizationApi) getEmscontext().getBean("esaOrgOrganizationApi"), (EmployeeApi)getEmscontext().getBean("esaOrgEmployeeApi"), getLoginUser(request).getCompId());
				//history.insertAttHistory(base_tools.getConn(), Integer.parseInt(Form.getEHF020401T0_05().replace("/", "")), false, getLoginUser(request).getCompId());
				base_tools.commit();
				request.setAttribute("MSG", "考勤回復成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "");
				request.setAttribute("show", "yes");
				request.setAttribute("collection", "");
				base_tools.close();
			} else {
				
				
				switch(Integer.valueOf((String)List.get("EHF020900T0_03"))){
				
				case 2: 
					request.setAttribute("MSG", "該月薪資已計算、回覆失敗!!");
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("button", "");
					request.setAttribute("show", "yes");
					base_tools.close();
	                break; 
	            case 3: 
	            	request.setAttribute("MSG", "該月薪資已確認、回覆失敗!!");
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("button", "");
					request.setAttribute("show", "yes");
					base_tools.close();
	                break; 
	            case 4: 
	            	request.setAttribute("MSG", "該月薪資已出帳、回覆失敗!!");
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("button", "");
					request.setAttribute("show", "yes");
					base_tools.close();
	                break; 
	            default: 
	                System.out.println("回覆考勤失敗"); 
	                break; 
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		 return queryForm(mapping, form, request, response);
	}
	
	
	
	
	/**
	 * 將加班時數轉換為休假時數
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward confirmOverTime(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		String sql = "" ;
		String month_start_date = "";
		String month_end_date = "";
		EHF020401M0F Form =(EHF020401M0F)form;
		String[] EHF020401T0_05 = Form.getEHF020401T0_05().split("/");  //將"考勤異常年月" 分割字串  [0]年   [1]月
		
		try {
			conn = tools.getConnection("SPOS");
			// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
			month_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(Form.getEHF020401T0_05()+ "/01")));
			
			// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
			month_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(Form.getEHF020401T0_05()+ "/01")));

			//01.先將員工年度計畫休假資料中   EHF010300T0_13=01(自動)先刪除
			this.DELETE(conn, EHF020401T0_05[0], request);
			
			
			EMS_OvertimeAttendanceHandle ems_oah = new EMS_OvertimeAttendanceHandle();  //員工加班資訊元件
			List<Map<String, String>> printlist = ems_oah.queryOvertimeCollectHours( conn,"", "", month_start_date, month_end_date ,//部門代號，員工工號 ，考勤異常第一天 ，考勤異常最後一天
												  	"02",getLoginUser(request).getCompId());//02=換休	 		

			try{
				Iterator<Map<String, String>> it = printlist.iterator();
				Map<String, String> printMap = null;
				
				while(it.hasNext()){
					
					printMap = it.next();
	
					//取得一天工作時數
					float work_hours = Float.parseFloat(tools.getSysParam(conn, getLoginUser(request).getCompId(), "WORK_HOURS"));
					float dayhours = 0;
					
					//新增員工年度休假資料
					String INSERT_sql = "" +
					" INSERT INTO ehf010300t0 ( EHF010300T0_02 ,EHF010300T0_03 ,EHF010300T0_04 ,EHF010300T0_05 ,EHF010300T0_06 " +
					" ,EHF010300T0_07 ,EHF010300T0_08, EHF010300T0_09, EHF010300T0_10, EHF010300T0_11, EHF010300T0_12 ,EHF010300T0_13,EHF010300T0_FK" +
					" ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE  ) " +
					" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) " ;

					PreparedStatement pstmt = conn.prepareStatement(INSERT_sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, INSERT_sql);
					int indexid = 1;
					p6stmt.setString(indexid,Integer.parseInt(EHF020401T0_05[0])-1911+"");
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getEmployeeID()); //員工工號
					indexid++;
					p6stmt.setString(indexid, printMap.get("部門代號"));
					indexid++;
					p6stmt.setString(indexid, printMap.get("員工工號"));
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010300T0_06());  //假別代號
					indexid++;
					
					p6stmt.setString(indexid, printMap.get("總加班時數")+"");  //休假時數
					indexid++;
					p6stmt.setString(indexid, printMap.get("總加班時數")+"");  //剩餘時數
					indexid++;
					
					p6stmt.setString(indexid, Form.getEHF010300T0_09());
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010300T0_10());
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010300T0_11());
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());
					indexid++;
					p6stmt.setString(indexid, "01");
					indexid++;
					p6stmt.setString(indexid, "");
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());
					indexid++;
						
					p6stmt.executeUpdate();
					pstmt.close();
					p6stmt.close();
					//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
					conn.commit();
					generateSelectBox(conn, Form, request);
					request.setAttribute("MSG", "新增員工年度休假成功!!");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return init(mapping, form, request, response);
	}*/
	
	/**
	 * 刪除員工年度休假
	 * @param conn
	 * @param EHF020401T0_05_year
	 * @param request
	 */
	public void DELETE(Connection conn,String EHF020401T0_05_year,HttpServletRequest request ){
		
		String sql="";
		try {
		Statement stmt = conn.createStatement();
		sql = " DELETE FROM EHF010300T0 WHERE 1=1 " +
		" AND EHF010300T0_02 = '"+EHF020401T0_05_year+"' " +//年度
		" AND EHF010300T0_03 = '01' " +//EHF010300T0_13=01(程式自動的換休)
		" AND EHF010300T0_12 = '"+getLoginUser(request).getCompId()+"' ";//公司代碼
		stmt.execute(sql);
		conn.commit();
		
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} 

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
	protected boolean getEmployee_idList(String EMPLOYEE_ID, String start_date , String end_date ,Map empInfMap,String sta_user_id,String sta_comp_id){
		Map tempMap= new HashMap();
		Calendar start_cal = tools.covertStringToCalendar(start_date);  //津貼計算開始日期
		Calendar end_cal = tools.covertStringToCalendar(end_date);  //津貼計算結束日期
		boolean chk_run_flag = true;//時間開關
		boolean chk_Show_flag = false;//是否列入員工清單
		
		//取得EMS-Flow員工基本資料Map
		//empInfMap = ems_tools.getEmpInfMapByEmpId(sta_user_id,EMPLOYEE_ID, sta_comp_id);
			
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

	
	/**
	 * 列印查詢結果 (全部)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward print2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//HR_TOOLS hr_tools = HR_TOOLS.getInstance();
		
//		this.getSelectOption(request);
		EHF020401M0F Form = (EHF020401M0F) form;
		request.setAttribute("Form1Datas",Form);
		Connection conn = null;
		ArrayList list = new ArrayList();
		ActionErrors lc_errors = new ActionErrors();
		
		int count=0;
		int number=1;
		Map empInfMap= new HashMap();	
		/*
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?true:arrid[0].equals(""))){
    		lc_errors.add("",new ActionMessage("請選擇要列印的文件"));
    		request.setAttribute("MSG","請選取要列印的資料!!");
    	}
    	*/
		
    	//建立資料庫連線
    	if (conn == null) {
    		try {
    			conn = tools.getConnection("SPOS");
    		} catch (SQLException e2) {
    			e2.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		}
    	BA_Vaildate ve = BA_Vaildate.getInstance();
		

		if (!lc_errors.isEmpty()) {
			saveErrors(request, lc_errors);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			this.generateSelectBox(conn, Form, request);
			
			return mapping.findForward("success");
		}

		
		try {
			//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			
    		HR_TOOLS hr_tools = new HR_TOOLS();

			//取得公司名稱
			Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
			//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			//職務
			Map titleMap = hr_tools.getTitleNameMap(authbean.getCompId());
			
			hr_tools.close();
    		
    		
    		
    		
			String path = "";
			EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
			
			path = getServlet().getServletContext().getRealPath("");
			
			this.generateSelectBox(conn, Form, request);

//			產生Excel元件
			EX020503R5F ef = new EX020503R5F(conn,path,getLoginUser(request).getEmployeeID(),request);
			
			//是否顯示加班考勤資料flag
			boolean overtime_data_flag = tools.StringToBoolean(Form.getOVERTIME_DATA_FLAG());

			//取得考勤異常資料清單
			list = fix_att_tools.getAttAbnormalList(conn, Form, getLoginUser(request), false, overtime_data_flag, "", "" );
			
			
			try{
				int DataCount = 0;
				Iterator it = list.iterator();
				while(it.hasNext()) {
					
					EHF020401M0F bean = (EHF020401M0F) it.next();
					if(DataCount==0){
						
//						if("".equals(Form.getDATA13())&&"".equals(Form.getDATA14())){
//							ef.setHeadValue(0,3,"A","日期區間:所有時間",false,"");
//						}else if("".equals(Form.getDATA13())){
//							ef.setHeadValue(0,3,"A","日期:"+Form.getDATA14(),false,"");
//						}else if("".equals(Form.getDATA14())){
//							ef.setHeadValue(0,3,"A","日期:"+Form.getDATA13(),false,"");
//						}else{
//							ef.setHeadValue(0,3,"A","日期區間:"+Form.getDATA13()+" ~ "+Form.getDATA14(),false,"");
//						}
						
						//ef.setHeadValue(1,4,"A","部門代號:"+(depMap.containsKey(Form.getDATA11())==true? Form.getDATA11()+"/"+(String) ((Map)depMap.get(Form.getDATA11())).get("SHOW_DESC"):""),false,"");
						
						ef.setHeadValue(4,4,"H","列印日期:"+tools.ymdTostring(tools.getSysDate())+"",false,"");							
						ef.setHeadValue(5,1,"A",(String)compMap.get("COMP_NAME_C"),false,""); //列印公司抬頭
						
						ef.setHeadValue(6,2,"A","員工刷卡異常記錄表",false,""); //報表名稱
						
					}
					ef.setHeadValue(7,3,"H","頁        次:"+(ef.getNowPageNum()+1)+"",false,"");
					
					ef.next();

					ef.setDetail(1,"A", bean.getEHF020403T0_03(),false);  //部門
					ef.setDetail(1,"B", bean.getEHF020403T0_02(),false);  //員工姓名
					ef.setDetail(1,"C", bean.getEHF020401T0_10(),false);  //日期
					ef.setDetail(1,"D", bean.getEHF020401T0_08_STATUS(),false);  //考勤類別
					ef.setDetail(1,"E", bean.getEHF020401T0_05_DATE(),false);  //打卡時間
					ef.setDetail(1,"F", bean.getEHF020401T0_09_DESC(),false);  //打卡狀態
					ef.setDetail(1,"G", bean.getEHF020401T0_FIX()+","+bean.getEHF020401T0_PS(),false);  //修正內容
					ef.setDetail(1,"H", "",false);  //備註	
					
					DataCount++;
				}
				
				
				
				if(DataCount>0){
					//String cur_date = tool.ymdTostring(tools.getSysDate());
//					以下方法，在列印時，可以出現遮罩   Alvin
					//傳入前端需要的檔名
					String name ="";
						
					name="員工刷卡異常記錄表.xls";
				
					
					String FileName="";
					if(DataCount>0){
						//String cur_date = tool.ymdTostring(tools.getSysDate());
						//存入檔案
						FileName=ef.write();
						request.setAttribute("MSG","列印完成!!");
						//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
						request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
						return queryDatas(mapping, form, request, response);
					
					}
				
				}
				 return queryDatas(mapping, form, request, response);
				
				
				
			}catch(Exception E){
				E.printStackTrace();
				request.setAttribute("Form1Datas", form);
				request.setAttribute("Form2Datas",list);
				request.setAttribute("MSG","列印失敗!!");
			}finally{
//				ef.write();
			}
			
		}catch (Exception E) {
			E.printStackTrace();
//			this.getSelectOption(request);
		    request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return queryDatas(mapping, form, request, response);
	}
	
}