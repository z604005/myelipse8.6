package com.spon.ems.abs.actions;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.db.EMS_DB_CONTROLLER;

/**
 * <Action>思邦科技查詢抽象類別

 */
public abstract class QueryAction extends BaseAction {
	
	protected final String checkId_config = "checkId";
	protected final String detail_type_config = "Detail_formType";
	protected final String detail_action_config = "Detail_formAction";
	protected final String INIT_FORWARD_CONFIG = "INIT_FORWARD_CONFIG";
	protected final String DEL_FORM_KEY_DELIMITER = ",";
	protected final String DEL_FORWARD_CONFIG = "DEL_FORWARD_CONFIG";
	protected final String FORWARD_QUERYFORM = "queryForm";
	protected final String FORWARD_INIT = "Init";
	protected final String FORWARD_REDIRECT = "Redirect";
	protected final String FORWARD_ASSIGN = "ASSIGN";
	protected final String ASSIGN_VALUE_CONFIG = "ASSIGN_VALUE_CONFIG";
	protected final String msg_query_config = "MSG_QUERY";
	
	public QueryAction() {
		
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {
		
		try{
			System.out.println("The QueryAction's finalize has been called!!");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	protected abstract void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request);
	
	/**
	 * Action的進入點
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		return this.init_base(mapping, form, request, response);
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
	protected ActionForward init_base(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		EMS_DB_CONTROLLER ems_db_c = null;
		
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
			ArrayList list = new ArrayList();
			
			//產生下拉選單
			generateSelectBox(ems_db_c.getConn(), form, request);
			
			//設定Initial的相關資料
			form = executeInitData(ems_db_c.getConn(), form, paramMap);
			
			//設定前端資訊
			request.setAttribute("Form1Datas", form);
			request.setAttribute("Form2Datas",list);
			
			//設定顯示模式
			super.setCreateMode(paramMap, request, form);
			super.setButtionDispalyMode(request, super.QueryButtion);
			super.setCollectionDispalyMode(request, super.ShowCollection);
			
			//設定TabsUtil
			super.setEMSCurrentTab(paramMap, request, response);
			
			//設定MSG
			super.setEMSViewMSG(paramMap, request);

		} catch (Exception e) {
	        // TODO Auto-generated catch block
			//記錄錯誤訊息
			this.handleException(e, paramMap);
	        e.printStackTrace();
	    }finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
	    
	    try{
			//設定QueryAction Init 後要Forward的Method
			if("queryForm".equals((String)paramMap.get(this.INIT_FORWARD_CONFIG))){
				return this.queryForm(mapping, form, request, response);
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return this.redirect(mapping, this.success_config);
	}
	
	protected abstract ActionForm executeInitData( Connection conn, ActionForm form, Map paramMap );
	
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
		return this.queryDatas(mapping, form, request, response);
	}
	
	/**
	 * 查詢資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		//錯誤訊息參數
		ActionErrors lc_errors = new ActionErrors();
		EMS_DB_CONTROLLER ems_db_c = null;
		
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
		
		try {
				
			try{
				//取得之前作業的錯誤訊息參數
				//避免因為前面的方法處理中已發生檢核錯誤 -> 最後執行 return queryDatas() 時
				//因 queryDatas() 不知已發生檢核錯誤, 執行了查詢動作造成使用者填寫的資料消失
				ActionErrors ex_lc_errors = null;
				if("error".equals((String)request.getAttribute("save_status"))){
					ex_lc_errors = (ActionErrors) this.getErrors(request);
				}else{
					ex_lc_errors = new ActionErrors();
				}
				
				if(ex_lc_errors.isEmpty()){
					//處理檢核程式
					request.setAttribute("action", "queryData");
					ValidateForm validate_form = (ValidateForm) form;
					lc_errors = validate_form.validate(mapping, request, ems_db_c.getConn());
				}else{
					lc_errors.add(ex_lc_errors);
				}
			}catch(Exception e){
				//記錄錯誤訊息
				this.handleException(e, paramMap);
				e.printStackTrace();
			}
			
			if (lc_errors.isEmpty()) {
				
				ArrayList list = new ArrayList();
				Map dataMap = new HashMap();
				
				try{
					dataMap = executeQueryData(ems_db_c.getConn(), form, paramMap);
				}catch(Exception e){
					//記錄錯誤訊息
					this.handleException(e, paramMap);
					e.printStackTrace();
				}
				
				//判斷查詢是否成功
				if( (Boolean)super.getMapDataWithCHK(dataMap, "CHK_FLAG", false) ){
					//設定前端顯示的Collection List
					request.setAttribute("Form2Datas", (ArrayList)super.getMapDataWithCHK(dataMap, "LIST", list) );
					request.setAttribute(this.msg_query_config,"查詢成功!!"+ "    " +
							""+(request.getAttribute(this.msg_query_config)==null?"":request.getAttribute(this.msg_query_config)));
				}else{
					//設定前端顯示的Collection List 為空清單
					request.setAttribute("Form2Datas",list);
					request.setAttribute(this.msg_query_config,"查無資料!!"+ "    " +
							""+(request.getAttribute(this.msg_query_config)==null?"":request.getAttribute(this.msg_query_config)));
				}
				
				//產生下拉選單
				generateSelectBox(ems_db_c.getConn(), form, request);
				
				//設定前端顯示參數
				super.setEditMode(paramMap, request, form);
				super.setButtionDispalyMode(request, super.QueryButtion);
				super.setCollectionDispalyMode(request, super.ShowCollection);
				
				//設定TabsUtil
				super.setEMSCurrentTab(paramMap, request, response);
				
				//設定MSG
				super.setEMSViewMSG(paramMap, request);
				
				//設定前端FormBean資料
				request.setAttribute("Form1Datas", form);
				
			} else {
				
				//產生下拉選單
				generateSelectBox(ems_db_c.getConn(), form, request);
				
				//處理錯誤資訊
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				
				//設定TabsUtil
				super.setEMSCurrentTab(paramMap, request, response);
				
				//設定MSG
				super.setEMSViewMSG(paramMap, request);
				
				//設定前端FormBean資料
				request.setAttribute("Form1Datas", form);
				request.setAttribute("Form2Datas",new ArrayList());
				
				return this.redirect(mapping, this.success_config);
			}
			
		} catch (Exception e) {
			this.handleException(e, paramMap);
			System.out.println(e);
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		return this.redirect(mapping, this.success_config);
	}
	
	protected abstract Map executeQueryData( Connection conn, ActionForm form, Map paramMap );
	
	/**
	 * 新增資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return this.redirect(mapping, "redirectADD");
	}
	
	/**
	 * 修改資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return editData(mapping, form, request, response);
	}
	
	/**
	 * 修改資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	protected ActionForward editData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			String arrid[] = request.getParameterValues(this.checkId_config);
			
			if ((arrid==null?false:!arrid[0].equals(""))){
				return this.redirect(mapping, "redirectEDIT");
			}else{
				request.setAttribute(this.msg_query_config,"請選取一筆要查看的明細資料!!");
				return queryDatas(mapping,form,request,response);
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}	
		
		return queryDatas(mapping,form,request,response);
	}
	
	/**
	 * 刪除表單資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return delData(mapping, form, request, response);
	}
	
	/**
	 * 刪除資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward delData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String arrid[] = {};
		EMS_DB_CONTROLLER ems_db_c = null;
		
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
				arrid = new String[1];
				arrid[0] = (String) request.getAttribute(this.checkId_config);
			}
			
			if ((arrid[0]==null?false:!arrid[0].equals(""))){
				//資料存在不做異常處理
				//處理多組DelFormKey -> 多組Key在String中要用 ","區隔!!
				arrid = arrid[0].split(this.DEL_FORM_KEY_DELIMITER);
			}else{
				request.setAttribute(this.msg_query_config, "請先選擇一筆要刪除的資料!!");
				return queryDatas(mapping, form, request, response);
			}
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		try {
			//資料檢核程式
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			//處理刪除檢核程式
			ValidateForm validate_form = (ValidateForm) form;
			lc_errors = validate_form.validate(mapping, request, ems_db_c.getConn());
			
			if (lc_errors.isEmpty()) {
					//執行實際刪除動作
					if(executeDelData(ems_db_c.getConn(), arrid, paramMap)){
						
						//更新資料庫
						ems_db_c.getConn().commit();
						
						request.setAttribute(this.msg_query_config, "刪除成功!!");
					}else{
						request.setAttribute(this.msg_query_config, "刪除失敗，選取的資料不存在!!");
					}
								
			} else {
				//處理錯誤資訊
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute(this.msg_query_config, request.getAttribute("ErrMSG"));
				
				return init(mapping, form, request, response);
			}
			
		} catch (Exception e) {
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		try{
			//設定QueryAction刪除後要Forward的Method
			if("queryForm".equals((String)paramMap.get(this.DEL_FORWARD_CONFIG))){
				return queryForm(mapping, form, request, response);
			}else if("Init".equals((String)paramMap.get(this.DEL_FORWARD_CONFIG))){
				return init(mapping, form, request, response);
			}else if("Redirect".equals((String)paramMap.get(this.DEL_FORWARD_CONFIG))){
				return this.redirect(mapping, form, request, response);
			}else if("ASSIGN".equals((String)paramMap.get(this.DEL_FORWARD_CONFIG))){
				//指定Forward位置, 透過 assign_value_config 參數
				this.redirect(mapping, (String)paramMap.get(this.ASSIGN_VALUE_CONFIG));
			}else{
				return init(mapping, form, request, response);
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		return init(mapping, form, request, response);
	}
	
	protected abstract boolean executeDelData( Connection conn, String key[], Map paramMap );
	
	/**
	 * 回前作業
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward redirect(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		return this.redirect(mapping, this.redirect_config);
	}
	
}