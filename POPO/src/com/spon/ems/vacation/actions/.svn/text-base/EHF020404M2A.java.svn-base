package com.spon.ems.vacation.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import vtgroup.ems.common.vo.AuthorizedBean;
import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.ems.fileimport.IMP_EHF020200;
import com.spon.ems.fileimport.IMP_EHF020404;
//import com.spon.ems.hr.forms.EHF010100M2F;
//import com.spon.ems.hr.forms.EHF010300M1F;
//import com.spon.ems.salary.forms.EHF030600M1F;
//import com.spon.ems.util.work_schedule.EMS_Work_ScheduleForm;
//import com.spon.ems.util.work_schedule.EMS_Work_Schedule_Table;
import com.spon.ems.vacation.forms.EHF020404M0F;
import com.spon.ems.vacation.forms.EX020404R0F;
import com.spon.ems.vacation.models.EHF020404;
import com.spon.ems.vacation.tools.EMS_AbnormalAttendanceHandle;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.ems.vacation.tools.EMS_AttendanceForm;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
//import com.spon.utils.util.BA_EMS_TOOLS;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;
import com.sun.org.apache.commons.beanutils.BeanUtils;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 門禁資料輸入
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020404M2A extends EditAction{
	private BA_TOOLS ems_tool ;
	private BA_TOOLS tool;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public EHF020404M2A()
	{
		ems_tool = BA_TOOLS.getInstance();;
		tool = BA_TOOLS.getInstance();
	}


	/**
	 * 清除欄位
	 */
	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		// 處理查詢後資料殘留問題
		EHF020404M0F Form = (EHF020404M0F)(form);
		Form.setEHF020404T0_03(""); // 感應卡
		Form.setEHF020404T0_dep(""); // 申請人部門代碼
		Form.setEHF020404T0_emp(""); // 申請人員工名稱
		Form.setEHF020404T0_06("");// 申請人部門名稱
		Form.setEHF020404T0_emp_TXT("");
		Form.setEHF020404T0_dep_TXT("");
		
		Form.setEHF020404T0_emp_UID("");
		Form.setEHF020404T0_dep_UID("");
		Form.setEHF020404T0_11("");
		Form.setEHF020404T0_05_MM("");
		Form.setEHF020404T0_05_HH("");
		Form.setEHF020404T0_13("");
		Form.setEHF020404T0_14("");
		Form.setEHF020404T0_15("");
		
	}

	/**
	 * 新增資料
	 */
	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,Map paramMap) {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		boolean chk_flag = false;
		String comp_id = (String) paramMap.get(super.COMP_ID);
		EHF020404M0F Form = (EHF020404M0F)(form);
		
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			//搜尋所有 輸入的門禁資料
			String sql = this.SELECTSQL(comp_id,(Form.getEHF020404T0_06()).replaceAll("/", "").trim()+ (Form.getEHF020404T0_05_HH()).trim()+ (Form.getEHF020404T0_05_MM()).trim()+ "00"+ (Form.getEHF020404T0_03()).trim(),"");
			rs = stmt.executeQuery(sql);
			//判斷是否有相同資料
			if (rs.next()) {
				chk_flag = false;
				request.setAttribute("ADDERROR", "資料重複!!");
			} else {
				// 建立EHF030604元件
				EHF020404 ehf020404 = new EHF020404(comp_id);
				// 建立新增資料Map
				Map dataMap = new HashMap();
				Calendar rightNow = Calendar.getInstance();
				String EHF020404T0_01 = (Form.getEHF020404T0_06()).replaceAll("/", "").trim()+ (Form.getEHF020404T0_05_HH()).trim()+ (Form.getEHF020404T0_05_MM()).trim()+ "00"+ (Form.getEHF020404T0_03()).trim();
				String EHF020404T0_02 = (Form.getEHF020404T0_06()).replaceAll("/", "").trim()+ (Form.getEHF020404T0_05_HH()).trim()+ (Form.getEHF020404T0_05_MM()).trim()+ "00"+ (Form.getEHF020404T0_03()).trim();
				String EHF020404T0_03 = Form.getEHF020404T0_03();
				String EHF020404T0_04 = Form.getEHF020404T0_06();
				String EHF020404T0_05 = (Form.getEHF020404T0_05_HH()).trim()+ ":" + (Form.getEHF020404T0_05_MM()).trim() + ":00";
				String EHF020404T0_06 = (Form.getEHF020404T0_06()).replaceAll("/", "-").trim()+ " "+ (Form.getEHF020404T0_05_HH()).trim()+ ":"+ (Form.getEHF020404T0_05_MM()).trim() + ":00";
				String EHF020404T0_07 = "";
				String EHF020404T0_08 = "";
				String EHF020404T0_09 = comp_id;
				String EHF020404T0_10 = "2";
				String EHF020404T0_11 = Form.getEHF020404T0_11();
				//String EHF020404T0_AFK = "0";
				//String EHF020404T0_SFK = "0";

				dataMap.put("EHF020404T0_01", EHF020404T0_01);
				dataMap.put("EHF020404T0_02", EHF020404T0_02);
				dataMap.put("EHF020404T0_03", EHF020404T0_03);
				dataMap.put("EHF020404T0_04", EHF020404T0_04);
				dataMap.put("EHF020404T0_05", EHF020404T0_05);
				dataMap.put("EHF020404T0_06", EHF020404T0_06);
				dataMap.put("EHF020404T0_07", EHF020404T0_07);
				dataMap.put("EHF020404T0_08", EHF020404T0_08);
				dataMap.put("EHF020404T0_09", EHF020404T0_09);
				dataMap.put("EHF020404T0_10", EHF020404T0_10);
				dataMap.put("EHF020404T0_11", EHF020404T0_11);
				//dataMap.put("EHF020404T0_AFK", EHF020404T0_AFK);
				//dataMap.put("EHF020404T0_SFK", EHF020404T0_SFK);

				ehf020404.addData(dataMap);
				// 新增完成
				chk_flag = true;
				//導向查詢畫面
				paramMap.put(ADD_FORWARD_CONFIG, "queryForm");
			}
			if(rs!=null)
			rs.close(); 
			stmt.close();
		} catch (Exception e) {
			// 記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return chk_flag;
	}
	/**
	 * 查詢資料
	 */
	@Override
	protected Map executeQueryEditData(Connection conn, String[] key,ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		boolean chk_flag = false;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		EHF020404M0F Form = (EHF020404M0F)form;
		Map return_map = new HashMap();
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		//公司代碼
		String comp_id = (String)paramMap.get(super.COMP_ID);
		//登入者代碼
		String user_id = (String)paramMap.get(super.USER_ID);
		String user_code = (String)paramMap.get(super.USER_CODE);
		//取得員工資訊
		//Map empMap = ems_tools.getEmpNameMap(getEmscontext(), user_id, comp_id);
		//取得部門資訊
		//Map depMap = ems_tools.getDepNameMap(getEmscontext(), user_id, Integer.valueOf(user_code), comp_id);

		
		//在職員工清單
		//Map WorkEmp=ems_tools.getWorkEmpNameMap(user_id, comp_id);
		
		//離職員工清單
		//Map DepartureWorkEmp=ems_tools.getDepartureWorkEmpNameMap(user_id, comp_id);
		
		//留職停薪員工清單
		//Map SuspensionWithoutPayEmp=ems_tools.getSuspensionWithoutPayEmpNameMap(user_id, comp_id);	
		
		
		//全部都不選，則表示查詢全部
		if(Form.getEHF020404T0_13()==null&&Form.getEHF020404T0_14()==null&&Form.getEHF020404T0_15()==null){
			Form.setEHF020404T0_13("0");
			Form.setEHF020404T0_14("0");
			Form.setEHF020404T0_15("0");
		}
		
		try{
	
			EHF020404 phf020404 = new EHF020404(comp_id);
			Map queryCondMap = new HashMap();
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put(super.COMP_ID, comp_id);
			queryCondMap.putAll(paramMap);
			
			//存放正職員工清單
			//queryCondMap.put("WorkEmp", WorkEmp);
			//存放離職員工清單
			//queryCondMap.put("DepartureWorkEmp", DepartureWorkEmp);
			//存放留職停薪員工清單
			//queryCondMap.put("SuspensionWithoutPayEmp", SuspensionWithoutPayEmp);
			
			
			
			
			
			List phf020404_queryList = phf020404.queryData(queryCondMap);
			if(phf020404_queryList.size()!=0){
				request.setAttribute("NOTNULL", "yes");
			}
			Form.setEHF020404T0_LIST(phf020404_queryList);
			chk_flag = true;
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			phf020404.close();

		}catch(Exception e){
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		if("2".equals(Form.getEHF020404T0_FLAG())){//確認狀態   2013/10/03 賴泓錡
			request.setAttribute("Show", "YES");
		}else if("1".equals(Form.getEHF020404T0_FLAG())||null==Form.getEHF020404T0_FLAG()){
			request.setAttribute("Show", "NO");
		}
		request.setAttribute("QUERYDATA", "YES");
		this.cleanAddField(form);
		return return_map;
	}

	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form,Map paramMap) {
		// TODO Auto-generated method stub		
		return false;
	}
	
	@Override
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
	}

	@Override
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		EHF020404M0F Form =(EHF020404M0F) form;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String key = "";
		String[] id = request.getParameterValues("checkId");			
		try {
			key = id[0];	
			
		} catch (Exception e) {
			// 記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return key;
	}
	//刪除資料
	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立phf020404元件
			EHF020404 ehf020404 = new EHF020404(comp_id);
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			
			dataMap.put("EHF020404T0_01", (key[0]));
			
			//執行刪除功能
			ehf020404.delData(dataMap);
								
			//關閉元件
			ehf020404.close();
			
			//控制刪除後的顯示畫面
			paramMap.put(super.DEL_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			conn.commit();
			
			chk_flag=true;
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,Map paramMap) {
		// TODO Auto-generated method stub
		EHF020404M0F Form = (EHF020404M0F) form;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.setAttribute("Show", "YES");
		
		request.setAttribute("QUERYDATA", "YES");
		
		
		return Form;
	}
	
	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,Map paramMap) {
		// TODO Auto-generated method stub
		EHF020404M0F Form = (EHF020404M0F) form;
		Map return_map = new HashMap();
		
		try{
			//Return資料
			return_map.put("CHK_FLAG", true);
			return_map.put("FORM", form);
		
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request) {
		// TODO Auto-generated method stub
		EHF020404M0F Form =  (EHF020404M0F)form;
		
		//產生時的下拉選單
		List listEHF020401T0_07_HH=new ArrayList();
		DecimalFormat df=new DecimalFormat("00");
		for (int i = 0; i < 24; i++) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listEHF020401T0_07_HH.add(bform);
		}
		request.setAttribute("listEHF020404M0_05_HH",listEHF020401T0_07_HH);
		
		//產生分的下拉選單
		List listEHF020401T0_07_MM=new ArrayList();
		
		for (int i = 0; i < 60; i++) {//20131028 修改為0~29  BY賴泓錡
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listEHF020401T0_07_MM.add(bform);
		}
		request.setAttribute("listEHF020404M0_05_MM",listEHF020401T0_07_MM);
		
		//取得員工感應卡資料
		if(Form.getEHF020404T0_emp()!=null){
			request.setAttribute("listEHF020404T0_02",this.getEmpCardList(conn, Form.getEHF020404T0_emp_UID(), getLoginUser(request).getCompId()));
		}else
			request.setAttribute("listEHF020404T0_02",this.getEmpCardList(conn, "", getLoginUser(request).getCompId()));
		
		List listEHF020404T0_FK=new ArrayList();
		
		BA_ALLKINDForm bform = new BA_ALLKINDForm();
		bform.setItem_id("1");
		bform.setItem_value("未確認");
		listEHF020404T0_FK.add(bform);
		bform = new BA_ALLKINDForm();
		bform.setItem_id("2");
		bform.setItem_value("已確認");
		listEHF020404T0_FK.add(bform);
		
		
		request.setAttribute("listEHF020404T0_FLAG",listEHF020404T0_FK);
	}

	/**
	 * 刪除門禁資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward del(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return delData_forEHF020404(mapping, form, request, response);
	}
	
	/**
	 * 實際執行刪除門禁資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward delData_forEHF020404(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		AuthorizedBean authbean = getLoginUser(request);
		String arrid[] = {};
		EHF020404M0F Form =  (EHF020404M0F)form;
		HR_TOOLS hr_tools = new HR_TOOLS();
		EMS_DB_CONTROLLER ems_db_c = null;
		String user_code = getLoginUser(request).getUserCode();
		String user_id = getLoginUser(request).getUserId();
		String comp_id = getLoginUser(request).getCompId();
		String employee="";
		String cur_day="";
		int EHF020404T0_10=0;
		//int EHF020404T0_AFK=0;
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			//設定預設值 - queryForm
			paramMap.put(this.DEL_FORWARD_CONFIG, this.FORWARD_QUERYFORM);
			
			//取得前端資料的主鍵
			arrid = request.getParameterValues(this.checkId_config);
			
			if(arrid == null){
				//arrid = new String[1];
				//arrid[0] = (String) request.getAttribute(this.checkId_config);//如果前端是沒有選擇的，就會抓getDelFormKey所存的數值
				request.setAttribute(this.msg_query_config, "請先選擇一筆要刪除的資料!!");
				return queryForm(mapping, form, request, response);
			}
			if(arrid.length>=2){
				request.setAttribute(this.msg_query_config, "請勿選擇多筆要刪除的資料!!");
				return queryForm(mapping, form, request, response);
			}
			else if ((arrid==null?false:!arrid[0].equals(""))){
				//資料存在不做異常處理
				//處理多組DelFormKey -> 多組Key在String中要用 ","區隔!!
				arrid = arrid[0].split(this.DEL_FORM_KEY_DELIMITER);
			}else{
				request.setAttribute(this.msg_query_config, "請先選擇一筆要刪除的資料!!");
				return queryForm(mapping, form, request, response);
			}
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		try {
			//20131018 新增判斷  如果是為確認要刪除  則不用經過從產考勤  BY賴泓錡
			if ("1".equals(Form.getEHF020404T0_FLAG())) {
				if (executeDelData(ems_db_c.getConn(), arrid, paramMap)) {
					request.setAttribute(this.msg_query_config, "刪除成功!!");
					return init(mapping, form, request, response);
				}
			}
			//在職員工清單
			//Map WorkEmp=ems_tools.getWorkEmpNameMap(user_id, comp_id);
			
			//離職員工清單
			//Map DepartureWorkEmp=ems_tools.getDepartureWorkEmpNameMap(user_id, comp_id);
			
			//留職停薪員工清單
			//Map SuspensionWithoutPayEmp=ems_tools.getSuspensionWithoutPayEmpNameMap(user_id, comp_id);	
			
			// 取得部門資訊
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

			// 取得員工資訊
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			
			
			//建立PHF030203元件
			EHF020404 phf020404 = new EHF020404(comp_id);
			Map dataMap = new HashMap();
			
			dataMap.put("EHF020404T0_01", (arrid[0]));
			dataMap.put("COMP_ID", comp_id);
			dataMap.put("USER_ID", user_id);
			dataMap.put("USER_CODE", user_code);
			dataMap.put("EHF020404T0_FLAG", Form.getEHF020404T0_FLAG());

			//全部都不選，則表示查詢全部
			if(Form.getEHF020404T0_13()==null&&Form.getEHF020404T0_14()==null&&Form.getEHF020404T0_15()==null){
				dataMap.put("EHF020404T0_13","0");
				dataMap.put("EHF020404T0_14","0");
				dataMap.put("EHF020404T0_15","0");
			}
			//存放正職員工清單
			//dataMap.put("WorkEmp", WorkEmp);
			//存放離職員工清單
			//dataMap.put("DepartureWorkEmp", DepartureWorkEmp);
			//存放留職停薪員工清單
			//dataMap.put("SuspensionWithoutPayEmp", SuspensionWithoutPayEmp);
			
			List queryDataList=phf020404.queryData(dataMap);
			
			Map map = (Map)queryDataList.get(0);
			employee=(String) map.get("EHF020403T0_01");//員工工號
			cur_day=(String) map.get("EHF020404T0_11");//考勤日期
			EHF020404T0_10= Integer.valueOf((String) map.get("EHF020404T0_10"));//資料來源
			//EHF020404T0_AFK=Integer.valueOf((String) map.get("EHF020404T0_AFK"));//考勤處理狀態

			ActionErrors lc_errors = new ActionErrors();
			if(EHF020404T0_10==1){
				lc_errors.add("EHF020404T0_10", new ActionMessage(""));
				request.setAttribute("ErrMSG", "此門禁資料為原始資料，無法刪除!! ");
			}if(this.chk_att_Status(ems_db_c.getConn(), cur_day, authbean.getCompId())){
				lc_errors.add("EHF020404T0_10", new ActionMessage(""));
				request.setAttribute("ErrMSG", "考勤已確認，無法刪除!! ");
			}

			if (lc_errors.isEmpty()) {
				// 執行實際刪除動作
				if (executeDelData(ems_db_c.getConn(), arrid, paramMap)) {
					
					//建立考勤元件
					EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(authbean.getCompId(), cur_day, authbean.getUserId());
					
					Map WorkSchedule= new HashMap();
					
					//產生預排換班處理元件
					EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
					
					ems_wsc_tools.sortoutList("",employee,empMap,depMap);
					
					//取得排班表與行事曆
					ems_wsc_tools.getVacations(ems_db_c.getConn(), empMap, depMap, authbean.getCompId(), cur_day,"", WorkSchedule);
					
					att.setWorkSchedule(WorkSchedule);

					//重產該筆刪除的考勤
					att.execute_Attendance( true, employee);

					// 更新資料庫
					ems_db_c.getConn().commit();
					
					request.setAttribute(this.msg_query_config, "刪除成功!!");
				} else {
					request.setAttribute(this.msg_query_config,"刪除失敗，選取的資料不存在!!");
				}
				
			} else {
				//處理錯誤資訊
				//saveErrors(request, lc_errors);
				//request.setAttribute("save_status", "error");
				request.setAttribute(this.msg_query_config, request.getAttribute("ErrMSG"));
				
				return queryForm(mapping, form, request, response);
			}
			
		} catch (Exception e) {
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
				hr_tools.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);
	}
	
	/**
	 * 前端點選確認修改門禁資料~並產生考勤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward change_EHF020404T0(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return change_Clock_in(mapping, form, request, response);	
	}
	/**
	 * 實際修改門禁資料~並產生考勤
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward change_Clock_in(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		AuthorizedBean authbean = getLoginUser(request);
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		
		Connection conn = null;
		
		
		int yes_1 = 0;//已修改(考勤正常、遲到、早退、曠職)
		int yes_2 = 0;//已修改(考勤未打卡)
		
		int no_time = 0;//因時間不正確  未修改
		int no_att=0;//新考勤已確認  未修改
		int no_change=0;//不改變考勤
		
		List show_ngEmpList = new ArrayList();
		
		//Map empMap = ems_tools.getEmpNameMap(getEmscontext(), authbean.getUserId(), authbean.getCompId());
		//Map depMap = ems_tools.getDepNameMap(getEmscontext(), authbean.getUserId(), Integer.parseInt(authbean.getUserCode()), authbean.getCompId());		
		try {
			// 取得部門資訊
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

			// 取得員工資訊
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			
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
	    	Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    	ResultSet rs = null;
	    	String comp_id = getLoginUser(request).getCompId();
	    	String user_id = getLoginUser(request).getUserId();

	    	//新增判斷  取得要確認考勤的 筆數     BY 賴泓錡
			String key = "";
			String[] id = request.getParameterValues("checkId");
			
			if(id==null){
				request.setAttribute("MSG","請選擇要確認的資料!!");
				return queryForm(mapping, form, request, response);
			}
			
			
			for (int i = 0; i < id.length; i++) {
				key += "'" + id[i] + "'";
				if (i != id.length - 1) {
					key += ",";
				}
			}
			EHF020404M0F bean=null;
			try {
				// 搜尋所有 輸入的門禁資料
				String sql = this.SELECTSQL(comp_id, key, "");
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					//取得當前系統日期
					Calendar cur_system_cal = Calendar.getInstance();//系統當前時間
					String cur_date = ems_tools.convertADDateToStrng(cur_system_cal.getTime());
					
					Calendar Att_time = ems_tools.covertStringToCalendar(rs.getString("考勤日期")+" 00:00:00","yyyy/MM/dd HH:mm:ss");
					
					
					
					bean = new EHF020404M0F();
					
					
					if(!this.chk_att_Status(conn, rs.getString("考勤日期"), authbean.getCompId())){
						//考勤未確認才可進入修改考勤  20140212 新增判斷條件 BY賴泓錡
						
						if(cur_date.equals(rs.getString("考勤日期"))){
							//不可確認與當前時間為同一天的考勤資料，因當天考勤隔天才會產生
							no_time++;
							
							bean.setEHF020404T0_emp(rs.getString("員工工號")+" "+(String) ((Map)empMap.get(rs.getString("員工工號"))).get("EMPLOYEE_NAME"));  //部門/員工
							bean.setEHF020404T0_dep(rs.getString("部門代號")+" "+ (String) ((Map)depMap.get(rs.getString("部門代號"))).get("SHOW_DESC"));
							bean.setEHF020404T0_06(rs.getString("打卡日期時間"));
							bean.setEHF020404T0_FLAG("不可確認與當前時間為同一天的考勤資料。");
							
							show_ngEmpList.add(bean);
							
							
						}else if( Att_time.compareTo(cur_system_cal) < 0 ){
							//在系統日期之前
							
							// 將門禁資料處理狀態 更新為已確認 BY 賴泓錡
							this.changeEHF020404T0_FLAG(conn, comp_id, rs.getString("刷卡資料序號"));
							
							// 建立考勤原件
							EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(authbean.getCompId(), rs.getString("考勤日期"), authbean.getUserId());
							
							
							Map WorkSchedule= new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							ems_wsc_tools.sortoutList("",rs.getString("員工工號"),empMap,depMap);
							
							//取得排班表與行事曆
							ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), rs.getString("考勤日期"),"", WorkSchedule);
							
							att.setWorkSchedule(WorkSchedule);
							
							
							
							// 依照目前員工工號 重新產出考勤
							att.execute_Attendance( true, rs.getString("員工工號"));
							// 判斷資料是否在考勤TABLE
							if (this.SELECT_EHF020401T0_SQL(conn, comp_id, rs.getString("刷卡資料序號"))) {
								yes_1++;	
							} else {
								// 是否在未打卡單找到此筆紀錄
								if (this.getSQL(rs, conn, user_id,rs.getString("刷卡資料序號"))){
									yes_2++;
									//this.changeEHF020404T0_FLAG(conn,comp_id, rs.getString("刷卡資料序號"));
								} else {
									no_change++;
									//this.changeEHF020404T0_FLAG(conn,comp_id, rs.getString("刷卡資料序號"));
								}
							}
						}else{
							//在系統日期之後
							no_time++;
							bean.setEHF020404T0_emp(rs.getString("員工工號")+" "+(String) ((Map)empMap.get(rs.getString("員工工號"))).get("EMPLOYEE_NAME"));  //部門/員工
							bean.setEHF020404T0_dep(rs.getString("部門代號")+" "+ (String) ((Map)depMap.get(rs.getString("部門代號"))).get("SHOW_DESC"));
							bean.setEHF020404T0_06(rs.getString("打卡日期時間"));
							bean.setEHF020404T0_FLAG("不可確認在系統日期之後的考勤資料。");
							
							show_ngEmpList.add(bean);
							
						}
					}else{
						//考勤已經確認
						no_att++;
						bean.setEHF020404T0_emp(rs.getString("員工工號")+" "+(String) ((Map)empMap.get(rs.getString("員工工號"))).get("EMPLOYEE_NAME"));  //部門/員工
						bean.setEHF020404T0_dep(rs.getString("部門代號")+" "+ (String) ((Map)depMap.get(rs.getString("部門代號"))).get("SHOW_DESC"));
						bean.setEHF020404T0_06(rs.getString("打卡日期時間"));
						bean.setEHF020404T0_FLAG("考勤已經確認。");
						
						show_ngEmpList.add(bean);
						
					}
				}
				
				
			} catch (Exception e) {
				// 記錄錯誤訊息
				e.printStackTrace();
			}
		
			String MSG="";
		
			
			int ALLyes=yes_1+no_change;
			if(ALLyes>0)
			MSG+="成功確認門禁資料(考勤正常):"+ALLyes+"筆；<br/>";
		
			if(yes_2>0)
			MSG+="成功確認門禁資料(考勤未打卡):"+yes_2+"筆；<br/>";
			
			
			int ALLno=no_time+no_att;
			if(ALLno>0)
			MSG+="未成功確認門禁資料:"+ALLno+"筆";

		
			if(rs!=null)
				rs.close();
			stmt.close();
		
			hr_tools.close();
			if(show_ngEmpList.size()>0){
				request.setAttribute("Form5Datas",show_ngEmpList);
				request.setAttribute("ng_emp_list","open");
			}
		
		
			request.setAttribute("MSG",MSG);
		}catch(Exception e){
			//request.setAttribute("MSG","刪除失敗");
			request.setAttribute("MSG","修改失敗!!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return init(mapping, form, request, response);
	}
	
	/**
	 * 查詢是否有未打卡資料
	 * @param rs
	 * @param conn
	 * @param userId 
	 * @return
	 */
	private boolean getSQL(ResultSet rs, Connection conn, String userId,String EHF020404T0_01){
		
		boolean flag=false;
		try{
			Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_01 = null;

			//找尋未打卡狀態
			String SQL = " Select " 
				+ " EHF020406T0_01 AS 員工工號,"
				+ " EHF020406T0_03 AS 班別序號,"
				+ " EHF020406T0_04 AS 未打卡考勤日期,"
				+ " EHF020406T0_04_DTE AS 未打卡日期時間,"
				+ " EHF020406T0_05 AS 未打卡狀態" 
				+ " FROM EHF020406T0"
				+ " WHERE 1 = 1" 
				+ " AND EHF020406T0_01='"+ rs.getString("員工工號") + "'"
				+ " AND DATE_FORMAT(EHF020406T0_04, '%Y/%m/%d') = '"+ rs.getString("考勤日期") + "'" 
				+ " AND EHF020406T0_07 = '"+ rs.getString("公司代碼") + "'" 
				+ " AND EHF020406T0_FK = '"+ EHF020404T0_01 + "'";
			
			SQL+="ORDER BY EHF020406T0_05";
			rs_01 = stmt_01.executeQuery(SQL);
			if (rs_01.next()) {
				flag = true;
			} else {
				flag = false;
			}
			rs_01.close();
			stmt_01.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flag;
	}


	/**
	 * 搜尋輸入會匯入的門禁資料
	 * @param compId
	 * @param EHF020404T0_01
	 * @param employee 
	 * @return
	 */
	private String SELECTSQL(String compId, String EHF020404T0_01, String employee){
		String sql = "" +
		" SELECT  " +
		" EHF020404T0_01 AS 刷卡資料序號," +
		" EHF020404T0_02 AS 門禁系統號," +
		" EHF020404T0_03 AS 感應卡號," +
		" EHF020404T0_04 AS 打卡日期," +
		" EHF020404T0_05 AS 打卡時間," +
		" DATE_FORMAT(EHF020404T0_06, '%Y/%m/%d %H:%i:%s') AS 打卡日期時間," +
		" IFNULL(EHF020404T0_07, '') AS 門禁機器代碼," +
		" IFNULL(EHF020404T0_08, '') AS 動作代碼," +
		" EHF020404T0_09 AS 公司代碼," +
		" EHF020403T0_02 AS 部門代號," +
		" EHF020403T0_01 AS 員工工號," +
		" IFNULL(DATE_FORMAT(EHF020404T0_11, '%Y/%m/%d'),'') AS 考勤日期," +
		" A.班別"+ 
		" FROM EHF020404T0 " +
		" LEFT JOIN "+
	    " EHF020403T1 ON EHF020404T0_03 = EHF020403T1_02"+
	    " AND EHF020404T0_09 = EHF020403T1_06"+
	    " LEFT JOIN "+
	    " EHF020403T0 ON EHF020403T1_01 = EHF020403T0_01"+
	    " AND EHF020403T1_06 = EHF020403T0_04"+
	    " LEFT JOIN (" +
	    " Select " +
	    " EHF010105T1_04 AS 班別," +
	    " EHF010105T0_02 AS 員工工號," +
	    " EHF010105T1_02 AS 排班表日期," +
	    " EHF010105T0_06 AS 公司代碼" +
	    " FROM EHF010105T0" +
	    " LEFT JOIN EHF020403T1 ON EHF010105T0_02 = EHF020403T1_01" +
	    " LEFT JOIN EHF010105T1 ON EHF010105T0_01 = EHF010105T1_01" +
	    " WHERE" +
	    "  1 = 1 " +
	    " ) A ON A.員工工號 = EHF020403T0_01" +
	    " AND A.排班表日期 = DATE_FORMAT(EHF020404T0_11, '%Y/%m/%d')" +
	    " AND A.公司代碼 = EHF020404T0_09 "+
	    " WHERE   1 = 1" +
	    " AND EHF020404T0_FLAG = '1'" +
	    " AND EHF020404T0_09 = '"+compId+"'" +
	    " AND EHF020404T0_10 = '2'";
		if(!"".equals(EHF020404T0_01)){
			sql+=" AND EHF020404T0_01 IN("+EHF020404T0_01+")";
	    }
		if(!"".equals(employee)){
			sql+=" AND EHF020404T0_01 ='"+employee+"'";
	    }
		
		return sql;
	}
	/**
	 * 搜尋資料是否在考勤TABLE
	 * @param string 
	 * @param compId 
	 * @param conn 
	 * @param compId
	 * @param EHF020401T0_01
	 * @return
	 */
	private boolean SELECT_EHF020401T0_SQL(Connection conn, String compId,String EHF020401T0_01) {
		boolean SELECT_switch = false;
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = "" 
				+ " SELECT  " + " *" 
				+ " FROM EHF020401T0 "
				+ " WHERE   1 = 1" 
				+ " AND EHF020401T0_11 = '" + compId+ "'" 
				+ " AND EHF020401T0_01 = '" + EHF020401T0_01 + "'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				SELECT_switch=true;
			}
			
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SELECT_switch;
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
			attform.setEHF020404T0_01(rs.getString("刷卡資料序號"));
			attform.setEHF020404T0_02(rs.getString("門禁系統號"));
			attform.setEHF020404T0_03(rs.getString("感應卡號"));
			attform.setEHF020404T0_04(rs.getString("打卡日期"));
			attform.setEHF020404T0_05(rs.getString("打卡時間"));
			attform.setEHF020404T0_06(rs.getString("打卡日期時間"));
			attform.setEHF020404T0_07(rs.getString("門禁機器代碼"));
			attform.setEHF020404T0_08(rs.getString("動作代碼"));
			attform.setEHF020404T0_09(rs.getString("公司代碼"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return attform;
	}
	
	/**
	 * 取得員工感應卡資料清單
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public List getEmpCardList( Connection conn, String employee_id, String comp_id ){
		
		List return_list = new ArrayList();
		
		try{
			
			if("".equals(employee_id)){
				return return_list;
			}
			//選出正在使用的感應卡號
			String sql = "" +
			" SELECT " +
			" EHF020403T1_02 AS item_id, " +
			" EHF020403T1_02 AS item_value " +
			" FROM EHF020403T1 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020403T1_06 = '"+comp_id+"' " + //公司代碼
			" AND EHF020403T1_04_END >= NOW()";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();

			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("item_id"));
				bform.setItem_value(rs.getString("item_value"));
				return_list.add(bform);
			}
			
			//選出過期的感應卡號
			String sql1 = "" +
			" SELECT " +
			" EHF020403T1_02 AS item_id, " +
			" EHF020403T1_02 AS item_value " +
			" FROM EHF020403T1 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+employee_id+"' " +  //員工工號
			" AND EHF020403T1_06 = '"+comp_id+"' " + //公司代碼
			" AND EHF020403T1_04_END <= NOW()";
			
			Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs1 = stmt1.executeQuery(sql1);
			
			BA_ALLKINDForm bform1 = new BA_ALLKINDForm();

			
			while(rs1.next()){
				bform1 = new BA_ALLKINDForm();
				bform1.setItem_id(rs1.getString("item_id"));
				bform1.setItem_value(rs1.getString("item_value"));
				return_list.add(bform1);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_list;
	}
	
	
	/**
	 * 將門禁資料處理狀態   更新為已確認   BY 賴泓錡
	 * @param conn
	 * @param compId
	 * @param key
	 */
	private void changeEHF020404T0_FLAG(Connection conn, String compId, String key) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF020404T0 SET " +
			" EHF020404T0_FLAG = 2, " +
			"  VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF020404T0_01 IN('"+key+"') " +  
			" AND EHF020404T0_10 = '2' " + 
			" AND EHF020404T0_09 = ? ";  
			
			//執行SQL
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;

			p6stmt.setString(indexid, compId);  //公司代碼
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
					
			//更新資料庫
			conn.commit();

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public ActionForward add(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020404M0F Form = (EHF020404M0F) form;
		String comp_id = getLoginUser(request).getCompId();
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
			if (lc_errors.isEmpty()) {
					//新增表單資訊
				// 建立EHF030604元件
				EHF020404 ehf020404 = new EHF020404(comp_id);
				// 建立新增資料Map
				Map dataMap = new HashMap();
				Calendar rightNow = Calendar.getInstance();
				String EHF020404T0_01 = (Form.getEHF020404T0_06()).replaceAll("/", "").trim()+ (Form.getEHF020404T0_05_HH()).trim()+ (Form.getEHF020404T0_05_MM()).trim()+ "00"+ (Form.getEHF020404T0_03()).trim();
				String EHF020404T0_02 = (Form.getEHF020404T0_06()).replaceAll("/", "").trim()+ (Form.getEHF020404T0_05_HH()).trim()+ (Form.getEHF020404T0_05_MM()).trim()+ "00"+ (Form.getEHF020404T0_03()).trim();
				String EHF020404T0_03 = Form.getEHF020404T0_03();
				String EHF020404T0_04 = Form.getEHF020404T0_06();
				String EHF020404T0_05 = (Form.getEHF020404T0_05_HH()).trim()+ ":" + (Form.getEHF020404T0_05_MM()).trim() + ":00";
				String EHF020404T0_06 = (Form.getEHF020404T0_06()).replaceAll("/", "-").trim()+ " "+ (Form.getEHF020404T0_05_HH()).trim()+ ":"+ (Form.getEHF020404T0_05_MM()).trim() + ":00";
				String EHF020404T0_07 = "";
				String EHF020404T0_08 = "";
				String EHF020404T0_09 = comp_id;
				String EHF020404T0_10 = "2";
				String EHF020404T0_11 = Form.getEHF020404T0_11();
				//String EHF020404T0_AFK = "0";
				//String EHF020404T0_SFK = "0";

				dataMap.put("EHF020404T0_01", EHF020404T0_01);
				dataMap.put("EHF020404T0_02", EHF020404T0_02);
				dataMap.put("EHF020404T0_03", EHF020404T0_03);
				dataMap.put("EHF020404T0_04", EHF020404T0_04);
				dataMap.put("EHF020404T0_05", EHF020404T0_05);
				dataMap.put("EHF020404T0_06", EHF020404T0_06);
				dataMap.put("EHF020404T0_07", EHF020404T0_07);
				dataMap.put("EHF020404T0_08", EHF020404T0_08);
				dataMap.put("EHF020404T0_09", EHF020404T0_09);
				dataMap.put("EHF020404T0_10", EHF020404T0_10);
				dataMap.put("EHF020404T0_11", EHF020404T0_11);
				//dataMap.put("EHF020404T0_AFK", EHF020404T0_AFK);
				//dataMap.put("EHF020404T0_SFK", EHF020404T0_SFK);

				ehf020404.addData(dataMap);

				
				request.setAttribute("MSG", "新增成功");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				// 清掉畫面上的新增
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas" , Form);
				if (request.getAttribute("Form1Datas") != null) {
					form = (EHF020404M0F) request.getAttribute("Form1Datas");
				}
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				request.setAttribute("button", "save");
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF020404M1A.addData() " + e);
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
			} catch (Exception e) {
			}
		}

		return queryForm(mapping,form,request,response);
	}
	
	/**
	 * 檢查考勤日否已確認
	 * @param conn
	 * @param receipt_no
	 * @param CompId
	 * @return
	 */
	private boolean chk_att_Status(Connection conn, String time, String CompId) {
		// TODO Auto-generated method stub
		boolean error_flag = false;
		try{
		
		String error_year="";
    	String sql = "" +
		" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
		" FROM EHF020900T0 "+
		" WHERE 1=1 " +
		" AND EHF020900T0_M2 BETWEEN DATE_FORMAT('"+time+"', '%Y/%m') AND DATE_FORMAT('"+time+"', '%Y/%m')"+	//考勤年月
		" AND EHF020900T0_04 = '"+CompId+"'" +//公司代碼
		" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

    	Statement stmt_NEW = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs_NEW = stmt_NEW.executeQuery(sql);
		
		while(rs_NEW.next()){
			error_flag = true;			
		}
		rs_NEW.close();
		stmt_NEW.close();
		}catch(Exception e){
		e.printStackTrace();
	}
		return error_flag;
	}
	
	public ActionForward redirect(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) {
		this.cleanAddField(form);
		return init(mapping,form,request,response);
	}
	/**
	 * 資料匯入後的頁面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward init_imp(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			
			conn = tools.getConnection("SPOS");
			EHF020404M0F Form = (EHF020404M0F) form;
			ArrayList list = new ArrayList();
			
			//產生下拉選單
			generateSelectBox(conn, Form, request);
	
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "imp");
			//request.setAttribute("collection", "show");
			
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
	
	public ActionForward init_add(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
			Connection conn = null;
			DataSource ds=null;
			BA_TOOLS tools = BA_TOOLS.getInstance();
			try{
				
				conn = tools.getConnection("SPOS");
				EHF020404M0F Form = (EHF020404M0F) form;
				ArrayList list = new ArrayList();
				generateSelectBox(conn, Form, request);
		
				
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("Form2Datas",list);
				FormUtils.setFormDisplayMode(request, form, "create");
				request.setAttribute("button", "save");
				//request.setAttribute("collection", "show");
				
	//			FormUtils.setFormDisplayMode(request, form, "create");
			} catch (Exception e2) {
		        // TODO Auto-generated catch block
		        e2.printStackTrace();
		    }finally {
		    	try {
					conn.close();
				}catch(Exception e){
					
				}
		    }
			return mapping.findForward("success");
		}
	/**
	 * 門禁資料匯入作業 EHF020404M2A
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public ActionForward impEHF020404(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF020404M0F Form = (EHF020404M0F)form;
		EHF020404M0F bForm = new EHF020404M0F();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		Connection conn = null;
		try{
			conn = tools.getConnection("SPOS");
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			//建立登入者資料
			AuthorizedBean authbean = getLoginUser(request);
			HR_TOOLS hr_tools = new HR_TOOLS();

			//建立卡鐘匯入元件
			IMP_EHF020404 imp_ehf020404 = new IMP_EHF020404( authbean);
	
			//取得匯入檔案
			FormFile importfile = Form.getUPLOADFILE();

			// 取得部門資訊
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

			// 取得員工資訊
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());

			//取得回傳訊息Map
			Map msgMap = imp_ehf020404.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(),
												  importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());
			
			//正確資料匯入
			List list = new ArrayList();
			//資料庫重複不匯入清單
			List ng_list = new ArrayList();
			//同份文件重複匯入
			List db_list = new ArrayList();
			//資料不正確清單
			List error_list = new ArrayList();
			//資料是否空白
			List empty_list = new ArrayList();
			//未設定感應卡資料
			List carderror_list = new ArrayList();
			
			List datalist = new ArrayList();
			
			datalist = (List) msgMap.get("DATALIST");
			
			Iterator it = datalist.iterator();
			
			Map tempMap = new HashMap();
			EHF020404M0F bean =  new EHF020404M0F();
			
			
			//正確的匯入資料
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				bForm = new EHF020404M0F();
				bForm.setEHF020404T0_03((String)tempMap.get("DATE_00"));//感應卡號
				bForm.setEHF020404T0_11((String)tempMap.get("DATE_01")+"/"+(String)tempMap.get("DATE_02")+"/"+(String)tempMap.get("DATE_03"));//考勤日期
				bForm.setEHF020404T0_06((String)tempMap.get("DATE_04")+"/"+(String)tempMap.get("DATE_05")+"/"+(String)tempMap.get("DATE_06"));//打卡日期時間
				
				list.add(bForm);
			}
			
			tempMap.clear();

			
			//判斷是否有與資料庫重複的資料清單
			if(msgMap.containsKey("NGDATALIST")){
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("NGDATALIST");				
				it = datalist.iterator();
				while(it.hasNext()){
					tempMap = (Map) it.next();
					bForm = new EHF020404M0F();
					bForm.setEHF020404T0_03((String)tempMap.get("DATE_00"));//感應卡號
					bForm.setEHF020404T0_11((String)tempMap.get("DATE_01")+"/"+(String)tempMap.get("DATE_02")+"/"+(String)tempMap.get("DATE_03"));//考勤日期
					bForm.setEHF020404T0_06((String)tempMap.get("DATE_04")+"/"+(String)tempMap.get("DATE_05")+"/"+(String)tempMap.get("DATE_06"));//打卡日期時間
					bForm.setEHF020404T0_10("資料重複!!請檢查!");//錯誤訊息
					ng_list.add(bForm);
				}
				tempMap.clear();
			}
			
			//判斷是否有日期錯誤的資料清單
			if(msgMap.containsKey("ERRORDATALIST")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("ERRORDATALIST");				
				it = datalist.iterator();
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bForm = new EHF020404M0F();
					bForm.setEHF020404T0_03((String)tempMap.get("DATE_00"));//感應卡號
					bForm.setEHF020404T0_11((String)tempMap.get("DATE_01")+"/"+(String)tempMap.get("DATE_02")+"/"+(String)tempMap.get("DATE_03"));//考勤日期
					bForm.setEHF020404T0_06((String)tempMap.get("DATE_04")+"/"+(String)tempMap.get("DATE_05")+"/"+(String)tempMap.get("DATE_06"));//打卡日期時間
					bForm.setEHF020404T0_10("匯入資料日期有錯誤!!請檢查!");//錯誤訊息
					ng_list.add(bForm);
				}
				tempMap.clear();
			}
			
			//判斷是否有空白欄位的資料清單
			if(msgMap.containsKey("EMPTYDATALIST")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("EMPTYDATALIST");				
				it = datalist.iterator();
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bForm = new EHF020404M0F();
					bForm.setEHF020404T0_03((String)tempMap.get("DATE_00"));//感應卡號
					bForm.setEHF020404T0_11((String)tempMap.get("DATE_01")+"/"+(String)tempMap.get("DATE_02")+"/"+(String)tempMap.get("DATE_03"));//考勤日期
					bForm.setEHF020404T0_06((String)tempMap.get("DATE_04")+"/"+(String)tempMap.get("DATE_05")+"/"+(String)tempMap.get("DATE_06"));//打卡日期時間
					bForm.setEHF020404T0_10("欄位有空白!!請檢查!");//錯誤訊息

					ng_list.add(bForm);
				}
				tempMap.clear();
			}
			
			//判斷是否有感應卡未設定的資料清單
			if(msgMap.containsKey("CARDERRORDATALIST")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("CARDERRORDATALIST");				
				it = datalist.iterator();
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bForm = new EHF020404M0F();
					bForm.setEHF020404T0_03((String)tempMap.get("DATE_00"));//感應卡號
					bForm.setEHF020404T0_11((String)tempMap.get("DATE_01")+"/"+(String)tempMap.get("DATE_02")+"/"+(String)tempMap.get("DATE_03"));//考勤日期
					bForm.setEHF020404T0_06((String)tempMap.get("DATE_04")+"/"+(String)tempMap.get("DATE_05")+"/"+(String)tempMap.get("DATE_06"));//打卡日期時間
					bForm.setEHF020404T0_10("感應卡未設定!!請檢查!");//錯誤訊息
					ng_list.add(bForm);
				}
				tempMap.clear();
			}
			
			//判斷是否有匯入資料重複的資料清單
			if(msgMap.containsKey("DBDATALIST")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("DBDATALIST");				
				it = datalist.iterator();
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bForm = new EHF020404M0F();
					bForm.setEHF020404T0_03((String)tempMap.get("DATE_00"));//感應卡號
					bForm.setEHF020404T0_11((String)tempMap.get("DATE_01")+"/"+(String)tempMap.get("DATE_02")+"/"+(String)tempMap.get("DATE_03"));//考勤日期
					bForm.setEHF020404T0_06((String)tempMap.get("DATE_04")+"/"+(String)tempMap.get("DATE_05")+"/"+(String)tempMap.get("DATE_06"));//打卡日期時間
					bForm.setEHF020404T0_10("匯入資料內容重複!!請檢查!");//錯誤訊息
					ng_list.add(bForm);
				}
				
				tempMap.clear();
			}

			
			//設定前端顯示訊息
			//request.setAttribute("NGMSG", "共有 "+(Integer)msgMap.get("CARDERRORCOUNT")+" 筆資料未設定!!");
			if(ng_list.size()>0){
				request.setAttribute("Form4Datas",ng_list);
				request.setAttribute("ng_collection", "show");
			}
			
			if(list.size()>0){
				//設定前端顯示訊息
				request.setAttribute("Form3Datas",list);
				request.setAttribute("correct_collection", "show");
			}
			
			hr_tools.close();
			
			//設定前端顯示資訊
			//request.setAttribute("Form2Datas", bForm);
			request.setAttribute("button", "imp");
			FormUtils.setFormDisplayMode(request, form, "create");
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(conn != null && !conn.isClosed()){
					conn.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return mapping.findForward("redirectIMP");
	}
	/**
	 * 下載空白範例匯入檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print_example(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020404M0F Form = (EHF020404M0F)form;
		Connection conn = null;
		
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
    	
		try {
			//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			
    		HR_TOOLS hr_tools = new HR_TOOLS();
    		
    		//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			
			hr_tools.close();
			
			int DataCount = 0;
			
			String sql = "" +
			 			 " SELECT EHF010100T0_01, EHF010100T0_02, EHF010100T0_05, EHF010100T6_02, EHF020403T1_02 FROM EHF010100T0 " +
			 			 " LEFT JOIN EHF010100T1 ON EHF010100T1_01 = EHF010100T0_01 " +
			 			 " LEFT JOIN EHF010100T6 ON EHF010100T6_01 = EHF010100T0_01 " +
			 			 " LEFT JOIN EHF020403T1 ON EHF020403T1_01 = EHF010100T0_01 " +
			 			 " WHERE 1=1 " +
			 			 " AND EHF010100T1_02 IN ('1','3') AND EHF010100T1_04 = '0' "+	//員工在職才執行
			 			 " AND EHF010100T0.HR_CompanySysNo = '"+authbean.getCompId()+"' " +
			 			 " ORDER BY EHF010100T6_02, EHF010100T0_02 ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			
			//產生Excel元件
			EX020404R0F ef = new EX020404R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getEmployeeID(), request, count);
			
			while(rs.next()){
				if(DataCount==0){
					
				}
				ef.next();
				
				if(count > 1){
					if(DataCount==0){
						for (int i = 0; i < count-1; i++) {
							ef.CopyCellFormat(6 + i , "A", 5, "A");// 複製格式
							ef.CopyCellFormat(6 + i , "B", 5, "B");// 複製格式
							ef.CopyCellFormat(6 + i , "C", 5, "C");// 複製格式
							ef.CopyCellFormat(6 + i , "D", 5, "D");// 複製格式
							ef.CopyCellFormat(6 + i , "E", 5, "E");// 複製格式
							ef.CopyCellFormat(6 + i , "F", 5, "F");// 複製格式
							ef.CopyCellFormat(6 + i , "G", 5, "G");// 複製格式
							ef.CopyCellFormat(6 + i , "H", 5, "H");// 複製格式
							ef.CopyCellFormat(6 + i , "I", 5, "I");// 複製格式
							ef.CopyCellFormat(6 + i , "J", 5, "J");// 複製格式
						}
					}
				}
				
				ef.setDetail(1,"A", rs.getString("EHF020403T1_02")==null?"":rs.getString("EHF020403T1_02"),false);//感應卡號
				ef.setDetail(1,"K", rs.getString("EHF010100T0_05")==null?"":rs.getString("EHF010100T0_05"),false);
				
				DataCount++;
				
			}
			
			ef.EndDocument();
			
			rs.close();
			stmt.close();
			
			String FileName="";
			String name = "門禁資料匯入.xls";
			
			if(DataCount>0){
				//存入檔案
				FileName = ef.write();
				request.setAttribute("MSG","列印完成!!");
				//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
				
			}else{				
				request.setAttribute("ERR_MSG","沒有資料可列印!!");	
			}
		
		} catch (Exception E) {
			E.printStackTrace();
			request.setAttribute("MSG", "列印失敗!!");
		} finally {
		// ef.write();
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		return this.init(mapping, form, request, response);
	}
	
	
	
	
	
}				
