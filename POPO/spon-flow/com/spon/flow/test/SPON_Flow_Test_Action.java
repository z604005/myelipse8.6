package com.spon.flow.test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.flow.tools.SPON_ParticularFlowForPWSUP_Tools;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.struts.form.BA_REPORTF;

/**
 * <Action> FLOW測試工具

 */
public class SPON_Flow_Test_Action extends QueryAction {

	public SPON_Flow_Test_Action() {

	}
	
	/**
	 * 呈核
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward submit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		BA_REPORTF Form = (BA_REPORTF) form;
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			//組成 condMap
			Map condMap = new HashMap();
			condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
			
			//執行FLOW submit
			spon_flow_tools.submit("PW_SUP_FLOW_0001", 
								   Form.getDATA01(), 
								   condMap,  //條件資料Map
								   userform.getSC0030_01(), 
								   userform.getSC0030_14());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.init(mapping, Form, request, response);
	}
	
	/**
	 * 核准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		BA_REPORTF Form = (BA_REPORTF) form;
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			//組成 condMap
			Map condMap = new HashMap();
			condMap.put("SUPPLY_USER_ID", Form.getDATA02());
			
			//執行FLOW approve
			spon_flow_tools.approve("PW_SUP_FLOW_0001", 
									Form.getDATA01(), 
									condMap,  //條件資料Map 
								    Form.getDATA03(),  //簽核意見
								    userform.getSC0030_01(), 
								    userform.getSC0030_14());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.init(mapping, Form, request, response);
	}
	
	/**
	 * 駁回
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		BA_REPORTF Form = (BA_REPORTF) form;
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			//組成 condMap
			Map condMap = new HashMap();
			condMap.put("SUPPLY_USER_ID", Form.getDATA02());
			
			//執行FLOW reject
			spon_flow_tools.reject("PW_SUP_FLOW_0001", 
								   Form.getDATA01(), 
								   condMap,  //條件資料Map
								   Form.getDATA03(),  //簽核意見
								   userform.getSC0030_01(), 
								   userform.getSC0030_14());
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.init(mapping, Form, request, response);
	}
	
	/**
	 * 取得表單簽核歷程清單
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getFlowLogList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		BA_REPORTF Form = (BA_REPORTF) form;
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			//取得FLOW LOG List
			List log_list = spon_flow_tools.getFlowSignLogList("PW_SUP_FLOW_0001", 
					   										   Form.getDATA01(), 
					   										   userform.getSC0030_14());
			
			request.setAttribute("show_log_list", "true");  //顯示簽核歷程
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", log_list);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.redirect(mapping, this.success_config);
	}
	
	/**
	 * 取得人員待簽核單據
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getUserCurrentSignList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		BA_REPORTF Form = (BA_REPORTF) form;
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			//取得人員待簽核表單 List
			List current_user_sign_list = spon_flow_tools.getUserCurrentSignList("PW_SUP_FLOW_0001", 
																				 userform.getSC0030_01(), 
																				 userform.getSC0030_14());
			
			request.setAttribute("show_user_current_sign_list", "true");  //顯示人員待簽核表單
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", current_user_sign_list);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.redirect(mapping, this.success_config);
	}
	
	/**
	 * 取得表單當前狀態資訊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getCurrentFlowFormStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		BA_REPORTF Form = (BA_REPORTF) form;
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			//取得表單當前狀態資訊 List
			List current_form_status_list = new ArrayList();
			Map current_form_status_map = spon_flow_tools.getCurrentFlowFormStatus("PW_SUP_FLOW_0001", 
																				   Form.getDATA01(), 
																				   userform.getSC0030_14());
			if(!current_form_status_map.isEmpty()){
				current_form_status_list.add(current_form_status_map);
			}
			
			request.setAttribute("show_current_form_status_list", "true");  //顯示表單當前狀態資訊
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", current_form_status_list);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.redirect(mapping, this.success_config);
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		BA_REPORTF Form = (BA_REPORTF) form;
		Form = new BA_REPORTF();
		
		try{

		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	
}