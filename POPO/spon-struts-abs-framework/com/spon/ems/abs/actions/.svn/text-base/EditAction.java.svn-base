package com.spon.ems.abs.actions;


import java.sql.Connection;
import java.sql.SQLException;
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
 * <Action>思邦科技編輯抽象類別

 */
public abstract class EditAction extends QueryAction {
	
	protected final String ADD_FORWARD_CONFIG = "ADD_FORWARD_CONFIG";
	protected final String FORWARD_EDITDATAFORM = "EditDataForm";
	protected final String msg_edit_config = "MSG_EDIT";
	//protected static final String[] key_id = {};
	protected final String KEY_ID = "KEY_ID";
	//protected static final boolean do_detail_query = false;
	protected final String DO_DETAIL_QUERY = "DO_DETAIL_QUERY";
	//protected static final String do_detail_type = "";
	protected final String DO_DETAIL_TYPE = "DO_DETAIL_TYPE";
	
	public EditAction() {
		
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {
		
		try{
			System.out.println("The EditAction's finalize has been called!!");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 新增資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return addData(mapping, form, request, response);
	}
	
	/**
	 * 新增資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward addData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
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
		
		try{
			//處理檢核程式
			request.setAttribute("action", "addData");
			ValidateForm validate_form = (ValidateForm) form;
			lc_errors = validate_form.validate(mapping, request, ems_db_c.getConn());
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		try {
			//設定預設值 - queryForm
			paramMap.put(this.ADD_FORWARD_CONFIG, this.FORWARD_QUERYFORM);
			
			if (lc_errors.isEmpty()) {
					
				//執行新增資料
				if(executeAddData(ems_db_c.getConn(), form, paramMap)){
					//儲存成功
					ems_db_c.getConn().commit();
					
					//處理顯示訊息
					request.setAttribute(this.msg_edit_config, "新增成功");
				}else{
					//處理顯示訊息
					request.setAttribute(this.msg_edit_config, "新增失敗!");
				}
				
				//設定前端顯示參數
				super.setButtionDispalyMode(request, super.EditButtion);
				super.setCollectionDispalyMode(request, super.ShowCollection);

			} else {
				//處理錯誤資訊
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute(this.msg_edit_config, request.getAttribute("ErrMSG"));
				
				//產生下拉選單
				generateSelectBox(ems_db_c.getConn(), form, request);
				
				//設定前端FormBean資料
				request.setAttribute("Form1Datas", form);
				
				return this.redirect(mapping, this.success_config);
			}
			
			//清除新增欄位的內容
			this.cleanAddField(form);
			
		} catch (Exception e) {
			//處理顯示訊息
			request.setAttribute(this.msg_edit_config, "新增失敗!");
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
			try {
				ems_db_c.getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		try{
			//設定EditAction新增後要Forward的Method
			if("queryForm".equals((String)paramMap.get(this.ADD_FORWARD_CONFIG))){
				return queryForm(mapping, form, request, response);
			}else if("EditDataForm".equals((String)paramMap.get(this.ADD_FORWARD_CONFIG))){
				return editDataForm(mapping, form, request, response);
			}else if("Init".equals((String)paramMap.get(this.ADD_FORWARD_CONFIG))){
				return init(mapping, form, request, response);
			}else if("ASSIGN".equals((String)paramMap.get(this.ADD_FORWARD_CONFIG))){
				//指定Forward位置, 透過 assign_value_config 參數
				this.redirect(mapping, (String)paramMap.get(this.ASSIGN_VALUE_CONFIG));
			}else{
				return queryForm(mapping, form, request, response);
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}

		return queryForm(mapping, form, request, response);
	}
	
	protected abstract boolean executeAddData( Connection conn, ActionForm form, Map paramMap );
	
	protected abstract void cleanAddField( ActionForm form );
	
	/**
	 * 新增明細資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDetailDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return addDetailData(mapping, form, request, response);
	}
	
	/**
	 * 新增資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward addDetailData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
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
		
		try{
			//處理檢核程式
			request.setAttribute("action", "addDetailData");
			ValidateForm validate_form = (ValidateForm) form;
			lc_errors = validate_form.validate(mapping, request, ems_db_c.getConn());
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		try {
			if (lc_errors.isEmpty()) {
					
				//執行新增明細資料
				if(executeAddDetailData(ems_db_c.getConn(), form, paramMap)){
					//儲存成功
					ems_db_c.getConn().commit();
					
					//處理顯示訊息
					request.setAttribute(this.msg_edit_config, "新增明細成功");
				}else{
					//處理顯示訊息
					request.setAttribute(this.msg_edit_config, "新增明細失敗!");
				}
				
				//設定前端顯示參數
				super.setButtionDispalyMode(request, super.EditButtion);
				super.setCollectionDispalyMode(request, super.ShowCollection);

			} else {
				//處理錯誤資訊
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute(this.msg_edit_config, request.getAttribute("ErrMSG"));
				
				//產生下拉選單
				generateSelectBox(ems_db_c.getConn(), form, request);
				
				//設定前端FormBean資料
				request.setAttribute("Form1Datas", form);
				
				//return this.redirect(mapping, this.success_config);
				return queryForm(mapping, form, request, response);
			}
			
			//清除新增明細欄位的內容
			this.cleanAddDetailField(form);
			
		} catch (Exception e) {
			//處理顯示訊息
			request.setAttribute(this.msg_edit_config, "新增明細失敗!");
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
			try {
				ems_db_c.getConn().rollback();
			} catch (SQLException e1) {
				//記錄錯誤訊息
				this.handleException(e1, paramMap);
				e1.printStackTrace();
			}

		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}

		return queryForm(mapping, form, request, response);
	}
	
	protected boolean executeAddDetailData( Connection conn, ActionForm form, Map paramMap ){
		return false;
	}
	
	protected void cleanAddDetailField(ActionForm form){
		
		try{
			//Do Nothing!!
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
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
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
    	try{
    		//取得待查詢資料的KEY_ID
			String arrid[] = request.getParameterValues(this.checkId_config);
			
			if ((arrid==null?false:!arrid[0].equals(""))){
				//this.key_id = arrid;
				paramMap.put(this.KEY_ID, arrid);
			}else{
				generateKeyId(form, paramMap);
			}
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		EMS_DB_CONTROLLER ems_db_c = null;
		
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
    	
		try {
			//錯誤訊息參數
			ActionErrors lc_errors = new ActionErrors();
			try{
				//取得之前作業的錯誤訊息參數
				//避免因為前面的方法處理中已發生檢核錯誤 -> 最後執行 return queryDatas() 時
				//因 queryDatas() 不知已發生檢核錯誤, 執行了查詢動作造成使用者填寫的資料消失
				ActionErrors ex_lc_errors = null;
				if(!"true".equals((String)request.getAttribute("handle_detail"))){
					if("error".equals((String)request.getAttribute("save_status"))){
						ex_lc_errors = (ActionErrors) this.getErrors(request);
					}else{
						ex_lc_errors = new ActionErrors();
					}
				}else{
					ex_lc_errors = new ActionErrors();
				}
				
				if(ex_lc_errors.isEmpty()){
					//處理檢核程式
					request.setAttribute("action", "queryEditData");
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
				
				Map dataMap = new HashMap();
				
				//產生條件查詢結果
				try{
					//dataMap = executeQueryData(form);
					dataMap = executeQueryEditData(ems_db_c.getConn(), (String[])paramMap.get(this.KEY_ID), form, paramMap );
				}catch(Exception e){
					//記錄錯誤訊息
					this.handleException(e, paramMap);
					e.printStackTrace();
				}
				
				//產生下拉選單
				generateSelectBox(ems_db_c.getConn(), form, request);
				
				//判斷查詢是否成功
				if( (Boolean)super.getMapDataWithCHK(dataMap, "CHK_FLAG", false) ){
					//設定前端顯示的Form
					request.setAttribute("Form1Datas", (ActionForm)super.getMapDataWithCHK(dataMap, "FORM", form) );
					request.setAttribute(this.msg_query_config,"查詢成功!!"+ "    " +
							""+(request.getAttribute(this.msg_query_config)==null?"":request.getAttribute(this.msg_query_config)));
				}else{
					//設定前端顯示的Form
					request.setAttribute("Form1Datas",form);
					request.setAttribute(this.msg_query_config,"查無資料!!"+ "    " +
							""+(request.getAttribute(this.msg_query_config)==null?"":request.getAttribute(this.msg_query_config)));
					//回到查詢頁面
					return super.queryForm(mapping, form, request, response);
				}
				
				//設定前端顯示參數
				super.setEditMode(paramMap, request, form);
				super.setButtionDispalyMode(request, super.QueryButtion);
				super.setCollectionDispalyMode(request, super.ShowCollection);
				
				//設定TabsUtil
				super.setEMSCurrentTab(paramMap, request, response);
				
				//設定MSG
				super.setEMSViewMSG(paramMap, request);
				
				//設定前端FormBean資料
//				request.setAttribute("Form1Datas", form);
				
			} else {
				
				//產生下拉選單
				generateSelectBox(ems_db_c.getConn(), form, request);
				
				//處理錯誤資訊
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				
				//設定前端顯示參數
				super.setEditMode(paramMap, request, form);
				super.setButtionDispalyMode(request, super.QueryButtion);
				super.setCollectionDispalyMode(request, super.ShowCollection);
				
				//設定前端FormBean資料
				request.setAttribute("Form1Datas", form);
				
				//設定TabsUtil
				super.setEMSCurrentTab(paramMap, request, response);
				
				//設定MSG
				super.setEMSViewMSG(paramMap, request);
				
				return this.redirect(mapping, this.success_config);
			}
			
		} catch (Exception e) {
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
	
	/**
	 * 修改資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	protected ActionForward editData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
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
			Map dataMap = new HashMap();
			
			//產生表單查詢結果
			try{
				//取得前端資料的主鍵
				String[] arrid = request.getParameterValues(this.checkId_config);
				
				if ((arrid==null?false:!arrid[0].equals(""))){
					//Edit Data Key Exist
					dataMap = executeQueryEditData(ems_db_c.getConn(), arrid, form, paramMap);
					//控制顯示在 EDIT 頁面
				}else{
					dataMap = executeQueryEditData(ems_db_c.getConn(), null, form, paramMap);
				}
				
				//設定ActionForm
				form = (ActionForm)super.getMapDataWithCHK(dataMap, "FORM", form);
				
			}catch(Exception e){
				//記錄錯誤訊息
				this.handleException(e, paramMap);
				e.printStackTrace();
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
		
		return super.queryForm(mapping, form, request, response);
	}
	
	/**
	 * 取得資料的KEY_ID
	 * @param form
	 * @param paramMap
	 */
	protected abstract void generateKeyId( ActionForm form, Map paramMap );
	
	protected abstract Map executeQueryEditData( Connection conn, String[] key, ActionForm form, Map paramMap );
	
	/**
	 * 儲存資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return this.saveData(mapping, form, request, response);
	}
	
	/**
	 * 儲存資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
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
			//錯誤訊息參數
			ActionErrors lc_errors = new ActionErrors();
			try{
				//處理檢核程式
				request.setAttribute("action", "saveData");
				ValidateForm validate_form = (ValidateForm) form;
				lc_errors = validate_form.validate(mapping, request, ems_db_c.getConn());
				
			}catch(Exception e){
				//記錄錯誤訊息
				this.handleException(e, paramMap);
				e.printStackTrace();
			}
			
			if (lc_errors.isEmpty()) {
				
				//執行儲存資料
				if(executeSaveData(ems_db_c.getConn(), form, paramMap)){
					//儲存成功
					ems_db_c.getConn().commit();
					
					//處理顯示訊息
					request.setAttribute(this.msg_edit_config, "儲存成功!!");
				}else{
					//處理顯示訊息
					request.setAttribute(this.msg_edit_config, "儲存失敗!!");
				}
				
				//設定前端FormBean資料
				request.setAttribute("Form1Datas", form);
				
				//設定前端顯示參數
				super.setButtionDispalyMode(request, super.EditButtion);
				super.setCollectionDispalyMode(request, super.ShowCollection);
		
			} else {
				//處理錯誤資訊
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute(this.msg_edit_config, request.getAttribute("ErrMSG"));
				
				//產生下拉選單
				generateSelectBox(ems_db_c.getConn(), form, request);
				
				//設定前端FormBean資料
				request.setAttribute("Form1Datas", form);
				
				//return this.redirect(mapping, this.success_config);
				return queryDatas(mapping, form, request, response);
			}
			
		} catch (Exception e) {
			//處理顯示訊息
			request.setAttribute(this.msg_edit_config,"儲存錯誤!!請重新操作!!");
			
			//產生下拉選單
			generateSelectBox(ems_db_c.getConn(), form, request);
			
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
			
			return queryDatas(mapping, form, request, response);
			
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		return queryDatas(mapping, form, request, response);
	}
	
	protected abstract boolean executeSaveData(Connection conn, ActionForm form, Map paramMap );
	
	/**
	 * 刪除表單資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		
		try{
			request.setAttribute(this.checkId_config, this.getDelFormKey(form, paramMap));
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.redirect(mapping, "redirectDELETE");
	}
	
	protected abstract String getDelFormKey( ActionForm form, Map paramMap );
	
	/**
	 * 刪除明細資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delDetailForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return delDetailData(mapping, form, request, response);
	}
	
	/**
	 * 刪除明細資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward delDetailData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String arrid[] = {};
		Map handleMap = null;
		String handle_type = "";
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
			//取得前端資料的主鍵
			arrid = request.getParameterValues(this.checkId_config);
			
			//處理刪除明細的Key
			handleMap = this.handleDelDetailKey(arrid, form, paramMap);
			
			if((Boolean)super.getMapDataWithCHK(handleMap, "CHK_FLAG", false)){
				//資料存在不做異常處理
				handle_type = "1";
			}else if ((arrid==null?false:!arrid[0].equals(""))){
				//資料存在不做異常處理
				handle_type = "2";
			}else{
				//處理顯示訊息
				request.setAttribute(this.msg_edit_config, "請先選擇一筆要刪除的明細資料!!");
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
			
			if (lc_errors.isEmpty()) {
					//執行實際刪除動作
					if("1".equals(handle_type) && executeDelDetailData(ems_db_c.getConn(), form, paramMap ) ){
						
						//更新資料庫
						ems_db_c.getConn().commit();
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "刪除明細成功!!");
					}else if("2".equals(handle_type) && executeDelDetailData(ems_db_c.getConn(), arrid, paramMap ) ){
						
						//更新資料庫
						ems_db_c.getConn().commit();
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "刪除明細成功!!");
					}else{
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "刪除明細失敗，選取的明細資料不存在!!");
					}
								
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				return queryDatas(mapping, form, request, response);
			}
			
			//清除新增明細欄位的內容
			this.cleanAddDetailField(form);
			
		} catch (Exception e) {
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
		
		return queryDatas(mapping, form, request, response);
	}
	
	/**
	 * 處理刪除明細的Key
	 * @param key
	 * @param form
	 * @return
	 */
	protected Map handleDelDetailKey(String key[], ActionForm form, Map paramMap ){
		
		Map return_map = new HashMap();
		
		try{
			return_map.put("CHK_FLAG", false);
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	protected boolean executeDelDetailData(Connection conn, ActionForm form, Map paramMap ){
		return false;
	}
	protected boolean executeDelDetailData(Connection conn, String key[], Map paramMap ){
		return false;
	}
	
	/**
	 * 處理明細
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward handleDetailDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		return this.handleDetailData(mapping, form, request, response);
	}
	
	/**
	 * 處理明細
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward handleDetailData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		String type = "";
		String action = "";
		
		try{
			//取得明細資料型態 & 執行功能
			type = request.getParameter(this.detail_type_config);
			action = request.getParameter(this.detail_action_config);
			
			//判斷是否需要執行 --> handleDetailData() 的相關功能
			if(!(!"".equals(type) && type != null && !"".equals(action) && action != null)){
				return queryForm(mapping, form, request, response);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
		
		try{
			//處理檢核程式
			request.setAttribute("action", action+type);
			ValidateForm validate_form = (ValidateForm) form;
			lc_errors = validate_form.validate(mapping, request, ems_db_c.getConn());
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		try {
			if (lc_errors.isEmpty()) {
				
				paramMap.put(this.DO_DETAIL_QUERY, new Boolean(false));
				
				if("add".equals(action)){
					//執行新增明細資料
					if(executeAddDetailData(ems_db_c.getConn(), type, form, paramMap)){
						//儲存成功
						ems_db_c.getConn().commit();
						
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "新增明細成功");
					}else{
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "新增明細失敗!");
					}
				}else if("save".equals(action)){
					//執行更新明細資料
					if(executeSaveDetailData(ems_db_c.getConn(), type, form, paramMap)){
						//更新成功
						ems_db_c.getConn().commit();
						
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "更新明細成功");
					}else{
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "更新明細失敗!");
					}
				}else if("delete".equals(action)){
					//執行實際刪除動作
					if(executeDelDetailData(ems_db_c.getConn(), type, form, paramMap) ){
						
						//更新資料庫
						ems_db_c.getConn().commit();
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "刪除明細成功!!");
					}else{
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "刪除明細失敗，選取的明細資料不存在!!");
					}
				}else if("swapup".equals(action)){
					//執行實際將明細往上移一筆動作
					if(executeSwapupDetailData(ems_db_c.getConn(), type, form, paramMap) ){
						
						//更新資料庫
						ems_db_c.getConn().commit();
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "明細往上移一筆成功!!");
					}else{
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "明細往上移一筆失敗");
					}
				}else if("swapdown".equals(action)){
					//執行實際將明細往下移一筆動作
					if(executeSwapdownDetailData(ems_db_c.getConn(), type, form, paramMap) ){
						
						//更新資料庫
						ems_db_c.getConn().commit();
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "明細往下移一筆成功!!");
					}else{
						//處理顯示訊息
						request.setAttribute(this.msg_edit_config, "明細往下移一筆失敗");
					}
				}
				
				//明細查詢動作
				if("query".equals(action) || (Boolean)paramMap.get(this.DO_DETAIL_QUERY)){
					
					//判斷是否有強制的明細查詢設定
					String do_detail_type = (String)paramMap.get(this.DO_DETAIL_TYPE);
					String local_type = "";
					if(!"".equals(do_detail_type) && do_detail_type != null){
						local_type = do_detail_type;
						//清空強制查詢參數
						do_detail_type = "";
					}else{
						local_type = type;
					}
					
					//執行明細查詢動作
					if(executeQueryDetailData(ems_db_c.getConn(), local_type, form, paramMap)){
						//明細資料查詢成功
					}else{
						//明細資料查詢失敗
					}
				}
				
				//設定前端顯示參數
				//super.setButtionDispalyMode(request, BaseAction.EditButtion);
				//super.setCollectionDispalyMode(request, BaseAction.ShowCollection);

			} else {
				//處理錯誤資訊
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute(this.msg_edit_config, request.getAttribute("ErrMSG"));
				
				//設定Detail的處理參數
				request.setAttribute("handle_detail", "true");
				
				//產生下拉選單
				generateSelectBox(ems_db_c.getConn(), form, request);
				
				//設定前端FormBean資料
				request.setAttribute("Form1Datas", form);
				
				return queryForm(mapping, form, request, response);
			}
			
			//清除新增明細欄位的內容
			this.cleanAddDetailField(form);
			
		} catch (Exception e) {
			//處理顯示訊息
			if("add".equals(action)){
				request.setAttribute(this.msg_edit_config, "新增明細失敗!");
			}else if("save".equals(action)){
				request.setAttribute(this.msg_edit_config, "更新明細失敗!");
			}else if("delete".equals(action)){
				request.setAttribute(this.msg_edit_config, "刪除明細失敗!");
			}
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
			try {
				ems_db_c.getConn().rollback();
			} catch (SQLException e1) {
				//記錄錯誤訊息
				this.handleException(e1, paramMap);
				e1.printStackTrace();
			}

		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}

		return queryForm(mapping, form, request, response);
	}
	
	//Interface --> handleDetailFunction Use... edit by joe 2012/07/26
	public boolean executeAddDetailData(Connection conn, String type, ActionForm form, Map paramMap ){
		return false;
	}
	public boolean executeSaveDetailData(Connection conn, String type, ActionForm form, Map paramMap ){
		return false;
	}
	public boolean executeDelDetailData(Connection conn, String type, ActionForm form, Map paramMap ){
		return false;
	}
	public boolean executeQueryDetailData(Connection conn, String type, ActionForm form, Map paramMap ){
		return false;
	}
	public boolean executeSwapupDetailData(Connection conn, String type, ActionForm form, Map paramMap ){
		return false;
	}
	public boolean executeSwapdownDetailData(Connection conn, String type, ActionForm form, Map paramMap ){
		return false;
	}
	
	/**
	 * 改變表單類型
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward changeForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return this.changeFormType(mapping, form, request, response);
	}
	
	/**
	 * 改變表單類型
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	private ActionForward changeFormType(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
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
			//設定 FormType 資料
			if(!setFormTypeData(ems_db_c.getConn(), form ,paramMap)){
				return queryForm(mapping, form, request, response);
			}
			
			//產生下拉選單
			generateSelectBox(ems_db_c.getConn(), form, request );
			
			request.setAttribute("Form1Datas", form);
			
		} catch (Exception e2) {
			//記錄錯誤訊息
			this.handleException(e2, paramMap);
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
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
	
	/**
	 * 設定 FormType 資料
	 * @param form
	 */
	protected boolean setFormTypeData(Connection conn, ActionForm form, Map paramMap){	
		try{
			//設定Struts-Layout顯示模式
			paramMap.put(super.EMS_STRUTSLAYOUT_MODE, super.StrutsLayoutCreateMode);
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
}