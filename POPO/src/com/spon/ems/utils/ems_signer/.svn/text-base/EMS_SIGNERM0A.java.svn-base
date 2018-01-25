package com.spon.ems.utils.ems_signer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
import com.spon.ems.util.overtime.EMS_Overtime_Record_Control;
import com.spon.ems.vacation.models.EHF020200;
import com.spon.ems.vacation.models.EHF020800;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.flow.tools.SPON_ParticularFlowForPWSUP_Tools;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.EMS_FLOW;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>待簽核單據作業

 */
public class EMS_SIGNERM0A extends NewDispatchAction {
	
	private BA_TOOLS tools;
	private EMS_ACCESS ems_access;
	private EMS_FLOW ems_flow;
	
	public EMS_SIGNERM0A() {
		tools = BA_TOOLS.getInstance();
		ems_access = EMS_ACCESS.getInstance();
		ems_flow = EMS_FLOW.getInstance();
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
	private void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request) {
		
		//產生--待簽核單據-- 下拉選單
		try{
			List listDATA02 = new ArrayList();
			
			listDATA02 = this.getFormTypeList(conn, getLoginUser(request).getCompId());
			
			request.setAttribute("listDATA02", listDATA02);
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	
	/**
	 * 取得表單類型下拉選單
	 * @param conn
	 * @param comp_id
	 * @return
	 */
	public List getFormTypeList(Connection conn, String comp_id){
		
		List listDATA02 = new ArrayList();
		
		try{
			BA_ALLKINDForm bform = null;
			/*
			if(!"".equals(this.getFlowNoByType(conn, "EHF020200M0", comp_id))){
				bform = new BA_ALLKINDForm();
				bform.setItem_id("EHF020200M0");
				bform.setItem_value("請假單待簽核單據");
				listDATA02.add(bform);
			}
			if(!"".equals(this.getFlowNoByType(conn, "EHF020301M0", comp_id))){
				bform = new BA_ALLKINDForm();
				bform.setItem_id("EHF020301M0");
				bform.setItem_value("出差單待簽核單據");
				listDATA02.add(bform);
			}
			if(!"".equals(this.getFlowNoByType(conn, "EHF020600M0", comp_id))){
				bform = new BA_ALLKINDForm();
				bform.setItem_id("EHF020600M0");
				bform.setItem_value("銷假單待簽核單據");
				listDATA02.add(bform);
			}
			if(!"".equals(this.getFlowNoByType(conn, "EHF020701M0", comp_id))){
				bform = new BA_ALLKINDForm();
				bform.setItem_id("EHF020701M0");
				bform.setItem_value("加班單待簽核單據");
				listDATA02.add(bform);
			}
			*/
			bform = new BA_ALLKINDForm();
			bform.setItem_id("EHF020200M0");
			bform.setItem_value("請假單待簽核單據");
			listDATA02.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("EHF020800M0");
			bform.setItem_value("加班單待簽核單據");
			listDATA02.add(bform);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return listDATA02;
	}
	
	/**
	 * 取得表單的FLOW_NO, 依據表單型態(ex:請假單,出差單...)
	 * @param conn
	 * @param receipt_type
	 * @param comp_id
	 * @return
	 */
	/*public String getFlowNoByType(Connection conn, String receipt_type, String comp_id ){
		
		String flow_no = "";
		
		try{
			
			if("EHF020200M0".equals(receipt_type)){
				flow_no = tools.getSysParam(conn, comp_id, "NT_Vacation_FLOW_NO");
			}
			else if("EHF020701M0".equals(receipt_type)){
				flow_no = tools.getSysParam(conn, comp_id, "NT_Overtime_FLOW_NO");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_no;
	}*/
	
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
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
			ArrayList list = new ArrayList();
			
			generateSelectBox(conn, Form, request);
			
			Form.setDATA02("EHF020200M0");
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("collection", "show");
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    } finally {
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
	
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		//EHF020900M0A ehf020900m0 = new EHF020900M0A();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			String receipt_type = Form.getDATA02();
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, getLoginUser(request).getCompId(), "EMERGENCY_FLOW_FLAG");

			if (lc_errors.isEmpty() && !"".equals(receipt_type) && receipt_type != null ) {
				
				ArrayList list = new ArrayList();		
				AuthorizedBean authbean = getLoginUser(request);
				String FormINF = "";
				List listDATA02 = (List) request.getAttribute("listDATA02");
				
				Map dataMap = this.executeQueryData(conn, authbean, receipt_type, listDATA02);
				
				FormINF = (String)dataMap.get("FORM_INF");
				list = (ArrayList) ((List)dataMap.get("DATALIST"));
				
				//判斷當前登入者身份
				ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
				
				request.setAttribute("receipt_type",receipt_type);  //待簽核表單的型態 
				
				request.setAttribute("Form2Datas",list);  //主管待簽核單據List
				
				//設定表單待簽核資訊列
				request.setAttribute("FormINF", FormINF);
				
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("collection", "show");
				/*
				if(ems_access.checkManager(conn, authbean)){
					//表示不是主管
					request.setAttribute("MSG", "目前沒有任何表單需要進行簽核!!");
				}
				*/
				if(list.isEmpty()){
					request.setAttribute("MSG", "目前沒有任何表單需要進行簽核!!");
				}
				
			} else {
				
				//AuthorizedBean authbean = getLoginUser(request);
				
				//判斷當前登入者身份
				ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
				/*
				if(ems_access.checkManager(conn, authbean)){
					//表示不是主管
					request.setAttribute("MSG", "目前沒有任何表單需要進行簽核!!");
				}
				*/
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
			if("Y".equals(emergency_flow)){
				request.setAttribute("emergency_flow", "Y");
			}else{
				request.setAttribute("emergency_flow", "N");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
	}
	
	/**
	 * 實際執行查詢線上簽核資料
	 * @param conn
	 * @param authbean
	 * @param receipt_type
	 * @param listDATA02
	 * @return
	 */
	public Map executeQueryData(Connection conn, AuthorizedBean authbean, String receipt_type, List listDATA02 ){
		
		Map dataMap = new HashMap();
		
		try{
			ArrayList datalist = new ArrayList();
			String FormINF = "";
			
			//取得FLOW_NO
			//String flow_no = this.getFlowNoByType( conn, receipt_type, authbean.getCompId() );
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, authbean.getCompId(), "EMERGENCY_FLOW_FLAG");
			
			String HR 		= tools.getSysParam(conn, authbean.getCompId(),"HR_IDENTITY");		//預設人資		100
			String SYSTEM 	= tools.getSysParam(conn, authbean.getCompId(),"SYSTEM_IDENTITY");	//系統管理者	000
			String BOSS 	= tools.getSysParam(conn, authbean.getCompId(),"BOSS_IDENTITY");	//老闆		111
			
			if("Y".equals(emergency_flow)){
				if(ems_access.checkEmsRoleEmp(conn, authbean, HR) 
						|| ems_access.checkEmsRoleEmp(conn, authbean, SYSTEM)						
						|| ems_access.checkEmsRoleEmp(conn, authbean, BOSS)){
					
					//產生待簽核單據的資訊列
					FormINF = this.getFormINF(conn, authbean, listDATA02);
					
					//取得人資待簽核清單
					List underList = this.getEmsEmergencySignList(authbean.getCompId(), authbean.getUserId(), receipt_type);
					
					Iterator it = underList.iterator();
					
					//處理各種表單的待簽核清單list
					datalist = this.choiseReceiptType(conn, authbean, it, receipt_type);
					
				}
			}else{
			
				/*if("1".equals(authbean.getManagerFlag()) || ems_tools.checkEmsRoleEmp(getEmscontext(), authbean, "", "2", "0001", flow_no, "")
						|| ems_tools.checkEmsRoleEmp(emscontext, authbean, "", "2", "0009", flow_no, "")
						|| ems_tools.checkEmsRoleEmp(emscontext, authbean, "", "2", "0004", flow_no, "")
						|| ems_tools.checkEmsRoleEmp(emscontext, authbean, "", "2", "0008", flow_no, "") ){  //主管身分
				
					//產生待簽核單據的資訊列
					FormINF = this.getFormINF(conn, authbean, listDATA02);
				
					//取得主管待簽核清單
					List underList = ems_tools.getEmsBossUnderList(getEmscontext(), authbean.getUserId(), "",
									 "", flow_no, "2", "", "N", authbean.getUserCode());
				
					Iterator it = underList.iterator();
				
					//處理各種表單的待簽核清單list
					datalist = this.choiseReceiptType(conn, authbean, it, receipt_type);
				
				}*/
				//產生待簽核單據的資訊列
				FormINF = this.getFormINF(conn, authbean, listDATA02);
				
				//取得待簽核清單
				List underList = this.getEmsSignList(authbean.getCompId(), authbean.getUserId(), receipt_type);
				
				Iterator it = underList.iterator();
				
				//處理各種表單的待簽核清單list
				datalist = this.choiseReceiptType(conn, authbean, it, receipt_type);
			
			}
			
			//建立資料Map
			dataMap.put("FORM_INF", FormINF);
			dataMap.put("DATALIST", datalist);			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
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
		return queryDatas(mapping, form, request, response);
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
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		String arrid[] = request.getParameterValues("checkId");
		String arrid2[] = request.getParameterValues("checkId_boss");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		return mapping.findForward("redirectEDIT");
    	}else if((arrid2==null?false:!arrid2[0].equals(""))){
    		return mapping.findForward("redirectEDIT");
    	}else{
    		request.setAttribute("MSG","請選取要查看的明細資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
		
	}
	
	/**
	 * 簽核動作 -- 緩衝區
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward signForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		String flow_action = request.getParameter("FLOW_ACTION");
		
		try{
			switch (Integer.parseInt(flow_action)){
				
				case 2:
					return approve(mapping, form, request, response, flow_action);
				
				case 3:
					return reject(mapping, form, request, response, flow_action);
												
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 核准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param flow_action 
	 * @return
	 */
	public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String flow_action ) {
		
		Connection conn = null;
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId_boss");
		
    	if ((arrid==null?false:arrid.length > 0)){
    		formKey = arrid ;
		}else{
    		request.setAttribute("MSG","請選取要查看的明細資料!!");
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
		
		try{
			ActionErrors lc_errors = new ActionErrors();
			String receipt_type = Form.getDATA02();
			
			if (lc_errors.isEmpty() && !"".equals(receipt_type) && receipt_type != null ) {
				
				int Count = 0;
				int NGCount = 0;							
				
				//建立FLOW元件
				SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
				
				//取得當前使用者資訊
				SC003F userform = super.userform(request);
				
				//批次簽核
				for(int i=0; i<formKey.length; i++){
					
					if("EHF020200M0".equals(receipt_type)){	//請假單
						
						//依據單號取得申請人部門組織(代號)
						Map UserMap = this.getVacationFormUserData(conn, formKey[i], comp_id);
						
						HR_TOOLS hr_tools = new HR_TOOLS();
						
						Map empMap = hr_tools.getEmpNameMap(comp_id);
						
						hr_tools.close();
						
						flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", (String)UserMap.get("EMPLOYEE_ID"), (String)UserMap.get("DEP_ID"), comp_id);
						
						//取得當前待簽核LOG
						Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, formKey[i], comp_id);
							
						this.updateFormStatus(spon_flow_tools, request, formKey[i], current_sign_map, flow_no, "approve", "EHF020200M0");
							
						//組成 condMap                                                                                                                                                                                                                                                                                                    
						Map condMap = new HashMap();
							
						//執行FLOW approve
						spon_flow_tools.approve(flow_no, 
												formKey[i], 
												condMap,  //條件資料Map 
												Form.getDATA01(),  //簽核意見
											    userform.getSC0030_01(), 
											    userform.getSC0030_14());
						
					}else if("EHF020800M0".equals(receipt_type)){	//加班單
						
						//依據單號取得申請人部門組織(代號)
						Map UserMap = this.getOvertimeFormUserData(conn, formKey[i], comp_id);
						
						flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", (String)UserMap.get("EMPLOYEE_ID"), (String)UserMap.get("DEP_ID"), comp_id);
						
						//取得當前待簽核LOG
						Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, formKey[i], comp_id);
							
						this.updateFormStatus(spon_flow_tools, request, formKey[i], current_sign_map, flow_no, "approve", "EHF020800M0");
							
						//組成 condMap                                                                                                                                                                                                                                                                                                    
						Map condMap = new HashMap();
							
						//執行FLOW approve
						spon_flow_tools.approve(flow_no, 
												formKey[i], 
												condMap,  //條件資料Map 
												Form.getDATA01(),  //簽核意見
											    userform.getSC0030_01(), 
											    userform.getSC0030_14());
						
					}
					
				}
				
				//取得簽核訊息參數
				String shownsg = tools.getSysParam(conn, comp_id, "SHOW_FLOW_SIGN_MSG");
				if("Y".equals(shownsg)){
					//顯示完整簽核訊息
					request.setAttribute("MSG", Count+" 件 "+this.getFlowActionMSG(conn, flow_action, true, comp_id)+" , "+
												NGCount+" 件 "+this.getFlowActionMSG(conn, flow_action, false, comp_id));
				}else{
					//不顯示完成簽核訊息
					request.setAttribute("MSG", "共處理 "+(Count+NGCount)+" 件");
				}

				//清除簽核意見
				Form.setDATA01("");

				//設定前端顯示資訊
				request.setAttribute("Form1Datas", Form);
				
			}else {
				generateSelectBox(conn, Form, request);
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", this.getFlowActionMSG(conn, flow_action, false, comp_id));
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准失敗!!");
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
	 * 駁回
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, String flow_action ) {
		
		Connection conn = null;
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId_boss");
		
    	if ((arrid==null?false:arrid.length > 0)){
    		formKey = arrid ;
		}else{
    		request.setAttribute("MSG","請選取要查看的明細資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			ActionErrors lc_errors = new ActionErrors();
			String receipt_type = Form.getDATA02();
			
			if (lc_errors.isEmpty() && !"".equals(receipt_type) && receipt_type != null ) {
				
				int Count = 0;
				int NGCount = 0;
				
				//建立FLOW元件
				SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
				
				//取得當前使用者資訊
				SC003F userform = super.userform(request);
				
				//批次簽核
				for(int i=0; i<formKey.length; i++){
					
					if("EHF020200M0".equals(receipt_type)){	//請假單
						
						//依據單號取得申請人部門組織(代號)
						Map UserMap = this.getVacationFormUserData(conn, formKey[i], comp_id);
						
						HR_TOOLS hr_tools = new HR_TOOLS();
						
						Map empMap = hr_tools.getEmpNameMap(comp_id);
						
						hr_tools.close();
						
						flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", (String)UserMap.get("EMPLOYEE_ID"), (String)UserMap.get("DEP_ID"), comp_id);
						
						//組成 condMap
						Map condMap = new HashMap();
						//condMap.put("SUPPLY_USER_ID", Form.getDATA02());
						
						//執行FLOW reject
						spon_flow_tools.reject(flow_no, 
											   formKey[i], 
											   condMap,  //條件資料Map
											   Form.getDATA01(),  //簽核意見
											   userform.getSC0030_01(), 
											   userform.getSC0030_14());
						
						//取得當前待簽核LOG
						Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, formKey[i], comp_id);
						
						this.updateFormStatus(spon_flow_tools, request, formKey[i], current_sign_map, flow_no, "reject", "EHF020200M0");
						
					}else if("EHF020800M0".equals(receipt_type)){	//加班單
						
						//依據單號取得申請人部門組織(代號)
						Map UserMap = this.getOvertimeFormUserData(conn, formKey[i], comp_id);
						
						flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", (String)UserMap.get("EMPLOYEE_ID"), (String)UserMap.get("DEP_ID"), comp_id);
						
						//組成 condMap
						Map condMap = new HashMap();
						
						//執行FLOW reject
						spon_flow_tools.reject(flow_no, 
											   formKey[i], 
											   condMap,  //條件資料Map
											   Form.getDATA01(),  //簽核意見
											   userform.getSC0030_01(), 
											   userform.getSC0030_14());
						
						//取得當前待簽核LOG
						Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, formKey[i], comp_id);
						
						this.updateFormStatus(spon_flow_tools, request, formKey[i], current_sign_map, flow_no, "reject", "EHF020800M0");
						
					}
					
				}
				
				//取得簽核訊息參數
				String shownsg = tools.getSysParam(conn, comp_id, "SHOW_FLOW_SIGN_MSG");
				if("Y".equals(shownsg)){
					//顯示完整簽核訊息
					request.setAttribute("MSG", Count+" 件 "+this.getFlowActionMSG(conn, flow_action, true, comp_id)+" , "+
												NGCount+" 件 "+this.getFlowActionMSG(conn, flow_action, false, comp_id));
				}else{
					//不顯示完成簽核訊息
					request.setAttribute("MSG", "共處理 "+(Count+NGCount)+" 件");
				}

				//清除簽核意見
				Form.setDATA01("");

				//設定前端顯示資訊
				request.setAttribute("Form1Datas", Form);
				
			}else {
				generateSelectBox(conn, Form, request);
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", this.getFlowActionMSG(conn, flow_action, false, comp_id));
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
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
	 * 更新表單狀態
	 * @param spon_flow_tools
	 * @param request
	 * @param form_no
	 * @param current_sign_map
	 * @param action
	 * @param action 
	 */
	private void updateFormStatus(SPON_ParticularFlowForPWSUP_Tools spon_flow_tools, HttpServletRequest request,
			String form_no, Map current_sign_map, String flow_no, String action, String receipt_type) {
		// TODO Auto-generated method stub
		
		String comp_id = getLoginUser(request).getCompId();
		String user_name = getLoginUser(request).getUserName();
		
		try{
			if("approve".equals(action)){
				
				if("EHF020200M0".equals(receipt_type)){
					
					boolean next_flow_station_flag = spon_flow_tools.isNextFlowStation(current_sign_map, comp_id);
					
					EHF020200 ehf020200 = new EHF020200();
					
					if(next_flow_station_flag){
						//有下一站
						//建立資料Map
						Map queryCondMap = new HashMap();					
						queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
						queryCondMap.put("EHF020200T0_16", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
						queryCondMap.put("EHF020200T0_16_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
						
						if(ehf020200.submitFlowDatas(queryCondMap, comp_id, user_name)){
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准成功!!");
						}else{
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准失敗!!");
						}
						
					}else{
						//無下一站
						//建立資料Map
						Map queryCondMap = new HashMap();					
						queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
						queryCondMap.put("EHF020200T0_16", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
						queryCondMap.put("EHF020200T0_16_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
						
						if(ehf020200.submitFlowDatas(queryCondMap, comp_id, user_name)){						
							//若是執行完成的動作，要回寫請假時數到年度休假資料中												
							EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
							setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());						
							
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成成功!!");
						}else{
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成失敗!!");
						}
						
					}
					
					ehf020200.close();
					
				}else if("EHF020800M0".equals(receipt_type)){
					
					boolean next_flow_station_flag = spon_flow_tools.isNextFlowStation(current_sign_map, comp_id);
					
					EHF020800 ehf020800 = new EHF020800();
					
					if(next_flow_station_flag){
						//有下一站
						//建立資料Map
						Map queryCondMap = new HashMap();					
						queryCondMap.put("EHF020800T0_01", form_no);  //表單編號
						queryCondMap.put("EHF020800T0_09", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
						queryCondMap.put("EHF020800T0_09_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
						
						if(ehf020800.submitFlowDatas(queryCondMap, comp_id, user_name)){
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准成功!!");
						}else{
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准失敗!!");
						}
						
					}else{
						//無下一站
						//建立資料Map
						Map queryCondMap = new HashMap();					
						queryCondMap.put("EHF020800T0_01", form_no);  //表單編號
						queryCondMap.put("EHF020800T0_09", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
						queryCondMap.put("EHF020800T0_09_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
						queryCondMap.put("COMP_ID", comp_id);
						
						if(ehf020800.submitFlowDatas(queryCondMap, comp_id, user_name)){
							
							this.updataOvertime(queryCondMap, getLoginUser(request), ehf020800);						
							
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成成功!!");
						}else{
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成失敗!!");
						}
						
					}
					
					ehf020800.close();
					
				}
				
			}else if("reject".equals(action)){
				
				if("EHF020200M0".equals(receipt_type)){
					
					EHF020200 ehf020200 = new EHF020200();
					
					//建立資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
					queryCondMap.put("EHF020200T0_16", "0005");	//FLOW狀態號碼
					queryCondMap.put("EHF020200T0_16_DESC", "駁回"); //FLOW狀態名稱
					
					if(ehf020200.submitFlowDatas(queryCondMap, comp_id, user_name)){
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
					}
					
					ehf020200.close();
					
				}else if("EHF020800M0".equals(receipt_type)){
					
					EHF020800 ehf020800 = new EHF020800();
					
					//建立資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF020800T0_01", form_no);  //表單編號
					queryCondMap.put("EHF020800T0_09", "08");	//FLOW狀態號碼
					queryCondMap.put("EHF020800T0_09_DESC", "駁回"); //FLOW狀態名稱
					
					if(ehf020800.submitFlowDatas(queryCondMap, comp_id, user_name)){
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
					}
					
					ehf020800.close();
					
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * 重產加班考勤與換休寫入
	 * @param queryCondMap
	 * @param authbean
	 * @param ehf020800
	 */
	private void updataOvertime(Map queryCondMap, AuthorizedBean authbean,
			EHF020800 ehf020800) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			//取得加班記錄 use 加班單
			overtime_list = ehf020800.queryOVList(queryCondMap);
			
			try {
				Map<String, String> Map = null;
				Map<String, String> return_map = null;

				Iterator<Map<String, String>> it = overtime_list.iterator();

				while (it.hasNext()) {

					Map = it.next();
					//轉換Map Data
					Map = ems_ov_ctrl.mappingOVMapData(Map);
					//重產考勤
					ehf020800.Again_Produce_att(conn, Map.get("EHF020801T0_04"), Map.get("EHF020801T0_02"), authbean.getCompId(), authbean.getUserId());
					//針對加班處理方式處理
					if("02".equals(Map.get("EHF020801T0_09"))){//換休->換補休
						//換休當年度可以使用到隔年6月底
						EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						setVaSql.updataOverTimeVacation("0006", Map, authbean);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			ems_ov_ctrl.close();
			// 更新資料庫
			conn.commit();
			
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
		
	}
	
	/**
	 * 待簽核執行 Flow Action
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward signForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		String flow_no = "";
		String flow_action = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//取得FLOW_NO
			flow_no = this.getFlowNoByType(conn, Form.getDATA02(), getLoginUser(request).getCompId() );
			//取得FLOW_ACTION
			flow_action = request.getParameter("FLOW_ACTION");
			
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
		
		return submitDatasModule(mapping, form, request, response, flow_no, flow_action );
	}*/
	
	/**
	 * EMS 表單 模組 --> 待簽核  --> 執行 FLOW_ACTION (除了呈核之外的動作)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @param flow_no
	 * @param flow_action
	 * @return
	 */
	/*public ActionForward submitDatasModule(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response,
										   String flow_no, String flow_action) {
		
		Connection conn = null;
		Connection conn3 = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		BaseFunction base_tools =  new BaseFunction();
		EHF020600M0F bean = new EHF020600M0F();
		EHF020600 ehf020600 = new EHF020600();
		
		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId_boss");
		
    	if ((arrid==null?false:arrid.length > 0)){
    		formKey = arrid ;
		}else{
    		request.setAttribute("MSG","請選取要查看的明細資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
    	
    	try {
			conn = tools.getConnection("EMSDS01");
			conn3 = tools.getConnection("EMSDS01");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, getLoginUser(request).getCompId(), "WORK_HOURS"));  //一個工作天的時數

		try {
			ActionErrors lc_errors = new ActionErrors();
			String receipt_type = Form.getDATA02();
			AuthorizedBean authbean = getLoginUser(request);
			String comp_id = authbean.getCompId();  //公司代碼

			if (lc_errors.isEmpty() && !"".equals(receipt_type) && receipt_type != null ) {
				
				boolean error_flag = false;
				int Count = 0;
				int NGCount = 0;
				
				//依據表單型態取得簽核的元件
				//flow_tools = this.getEmsFlowClassByType(receipt_type);
				
				//簽核執行錯誤訊息
				StringBuffer ERROR_MSG = new StringBuffer();
				Map msg_map = null;
				String error_msg = "";
				
				boolean approve = false;
				
				//批次簽核
				for(int i=0; i<formKey.length; i++){
					
					if("EHF020200M0".equals(receipt_type)){	//請假單
						approve = true;
					}else if("EHF020301M0".equals(receipt_type)){	//出差單
						approve = true;
					}else if("EHF020600M0".equals(receipt_type)){	//銷假單
						approve = true;
					}else if("EHF020701M0".equals(receipt_type)){	//加班單
						
						error_msg = "尚有核定時數需填寫!無法使用批次功能。";
						ERROR_MSG.append(error_msg);  //錯誤訊息
						ERROR_MSG.append("<BR>");  //換行																
						
						request.setAttribute("ERROR_MSG", ERROR_MSG.toString());
						
					}
					
					if(approve){
						if(flow_tools.flowSubmitModule(getEmscontext(), conn, authbean, formKey[i], Form.getDATA01(), flow_no, flow_action)){
							Count++;
							//人事註記且為最後一站
							this.checkIdentityByHR(conn, request, formKey[i], receipt_type);
							
							if("yes".equals(request.getAttribute("hr_manager"))){
							
								if("EHF020600M0".equals(receipt_type)){
								
									String sql_select = " SELECT EHF020601T0_13, EHF020601T0_14 FROM EHF020601T0 WHERE EHF020601T0_01 = '"+formKey[i]+"' ";
								
									Map dataMap = base_tools.query(sql_select);
								
									if("01".equals((String) dataMap.get("EHF020601T0_13"))){
									
										bean.setEHF020601T0_14_VA((String) dataMap.get("EHF020601T0_14"));
									
										ehf020600.invalidEHF020200T0(bean, "SPFORM0019", "0014", getLoginUser(request).getCompId(), getLoginUser(request).getUserName());
										
										//還原剩餘時數
										ehf020600.saveFormStatus(conn3, bean, "SPFORM0019", getLoginUser(request).getCompId(), Day_work_hours);
									
										conn3.commit();
									
									}else if("02".equals((String) dataMap.get("EHF020601T0_13"))){
									
										bean.setEHF020601T0_14_TR((String) dataMap.get("EHF020601T0_14"));
									
										ehf020600.invalidEHF020300T0(bean, "SPFORM0020", "0014", getLoginUser(request).getCompId(), getLoginUser(request).getUserName());
									
									}
								
									ehf020600.close();
									base_tools.close();
								
								}else if("EHF020701M0".equals(receipt_type)){
									
								}
							
							}
							
						}else{
							NGCount++;
							//建立Flow簽核執行失敗訊息
							msg_map = flow_tools.getFlowSignMSG(conn, authbean, formKey[i], Form.getDATA01(), flow_no, flow_action);
							if(msg_map.containsKey("MSG") && !"".equals((String)msg_map.get("MSG"))){
								ERROR_MSG.append((String)msg_map.get("MSG"));  //錯誤訊息
								ERROR_MSG.append("<BR>");  //換行
							}
						}
					}else{
						NGCount++;
					}
					
				}
				
				//處理Flow執行錯誤訊息顯示
				if(ERROR_MSG.length() > 0){
					request.setAttribute("ERROR_MSG", ERROR_MSG.toString());
				}
				
				//取得簽核訊息參數
				String shownsg = tools.getSysParam(conn, comp_id, "SHOW_FLOW_SIGN_MSG");
				if("Y".equals(shownsg)){
					//顯示完整簽核訊息
					request.setAttribute("MSG", Count+" 件 "+this.getFlowActionMSG(conn, flow_action, true, comp_id)+" , "+
												NGCount+" 件 "+this.getFlowActionMSG(conn, flow_action, false, comp_id));
				}else{
					//不顯示完成簽核訊息
					request.setAttribute("MSG", "共處理 "+(Count+NGCount)+" 件");
				}
				
				//清除簽核意見
				Form.setDATA01("");
				
				//設定前端顯示資訊
				request.setAttribute("Form1Datas", Form);
				
			} else {
				
				generateSelectBox(conn, Form, request);
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", this.getFlowActionMSG(conn, flow_action, false, comp_id));
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			System.out.println(e);
			base_tools.rollback();
			base_tools.close();
			ehf020600.close();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				if (conn3 != null && !conn3.isClosed()) {
					conn3.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryDatas(mapping, form, request, response);
	}*/
	
	/**
	 * EMS 表單簽核
	 * @param conn
	 * @param authbean
	 * @param receipt_type
	 * @param formKey
	 * @param flow_no
	 * @param flow_action
	 * @param comment
	 * @return
	 */
	/*public Map submitDatasModule(Connection conn, AuthorizedBean authbean, String receipt_type,
								 String[] formKey, String flow_no, String flow_action, String comment){
		
		Map dataMap = new HashMap();
		boolean chk_flag = false;
		
		try{
			int Count = 0;
			int NGCount = 0;
			
			//依據表單型態取得簽核的元件
			//flow_tools = this.getEmsFlowClassByType(receipt_type);
			
			//簽核執行錯誤訊息
			StringBuffer ERROR_MSG = new StringBuffer();
			Map msg_map = null;
			
			//批次簽核
			for(int i=0; i<formKey.length; i++){
				if(flow_tools.flowSubmitModule(getEmscontext(), conn, authbean, formKey[i], comment, flow_no, flow_action)){
					Count++;
				}else{
					NGCount++;
					//建立Flow簽核執行失敗訊息
					msg_map = flow_tools.getFlowSignMSG(conn, authbean, formKey[i], comment, flow_no, flow_action);
					if(msg_map.containsKey("MSG") && !"".equals((String)msg_map.get("MSG"))){
						ERROR_MSG.append((String)msg_map.get("MSG"));  //錯誤訊息
						if(!(i >= formKey.length)){
							ERROR_MSG.append(",");  //換行
						}
					}
				}
				
			}
			
			chk_flag = true;
			
			//建立資料Map
			dataMap.put("CHK_FLAG", chk_flag);
			dataMap.put("COUNT", Count);
			dataMap.put("NGCOUNT", NGCount);
			dataMap.put("ERROR_MSG", ERROR_MSG.toString());
			
		}catch(Exception e){
			chk_flag = false;
			//建立資料Map
			dataMap.put("CHK_FLAG", chk_flag);
			e.printStackTrace();
		}
		
		return dataMap;
	}*/
	
	/**
	 * 取的待簽核的表單資訊列
	 * @param conn
	 * @param authbean
	 * @param form_type
	 * @param comp_id
	 * @return
	 */
	private String getFormINF( Connection conn,  AuthorizedBean authbean, List form_type ){
		
		List underList = null;
		ArrayList list = null;
		String FormINF = "";
		String flow_no = "";
		
		try{
			//待簽核的表單類型
			Iterator it = form_type.iterator();
			
			while(it.hasNext()){
				
				BA_ALLKINDForm bean = (BA_ALLKINDForm) it.next();
				
				//取得FLOW_NO
				//flow_no = this.getFlowNoByType( conn, bean.getItem_id(), authbean.getCompId() );
				
				//是否啟用緊急流程
				String emergency_flow = tools.getSysParam(conn, authbean.getCompId(), "EMERGENCY_FLOW_FLAG");
				
				if("Y".equals(emergency_flow)){
					
					//取得主管待簽核清單
					underList = this.getEmsEmergencySignList(authbean.getCompId(), authbean.getUserId(), bean.getItem_id());
					
				}else{
				
					//取得主管待簽核清單
					/*underList = ems_tools.getEmsBossUnderList(getEmscontext(), authbean.getUserId(), "",
								 						  	  "", flow_no, "2", "", "N", authbean.getUserCode());*/
					//取得待簽核清單
					underList = this.getEmsSignList(authbean.getCompId(), authbean.getUserId(), bean.getItem_id());
				
				}
				
				//Flow待簽核單號
				Iterator it_bean = underList.iterator();
				list = this.choiseReceiptType(conn, authbean, it_bean, bean.getItem_id());
				
				if(list.size() > 0){
					FormINF += bean.getItem_value()+" 共 "+list.size()+" 張    <br>";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return FormINF;
	}
	
	/**
	 * 取得各種單據的待簽核清單數量
	 * @param conn
	 * @param authbean
	 * @param form_type
	 * @return
	 */
	/*public Map getFormCount( Connection conn,  AuthorizedBean authbean, List form_type ){
		
		List underList = null;
		ArrayList list = null;
		String FormINF = "";
		String flow_no = "";
		Map dataMap = new HashMap();
		boolean chk_flag = false;
		
		try{
			//待簽核的表單類型
			Iterator it = form_type.iterator();
			
			while(it.hasNext()){
				
				BA_ALLKINDForm bean = (BA_ALLKINDForm) it.next();
				
				//取得FLOW_NO
				flow_no = this.getFlowNoByType( conn, bean.getItem_id(), authbean.getCompId() );
				
				
				//取得主管待簽核清單
				underList = ems_tools.getEmsBossUnderList(getEmscontext(), authbean.getUserId(), "",
								 						  "", flow_no, "2", "", "N", authbean.getUserCode());
				
				//Flow待簽核單號
				Iterator it_bean = underList.iterator();
				list = this.choiseReceiptType(conn, authbean, it_bean, bean.getItem_id());
				
				if(list.size() > 0){
					dataMap.put(bean.getItem_id(), list.size());
				}else{
					dataMap.put(bean.getItem_id(), (int)0);
				}
			}
			
			chk_flag = true;
			//建立Map訊息
			dataMap.put("CHK_FLAG", chk_flag);
			
		}catch(Exception e){
			chk_flag = false;
			//建立Map訊息
			dataMap.put("CHK_FLAG", chk_flag);
			e.printStackTrace();
		}
		
		return dataMap;
	}*/
	
	/**
	 * 取得各種表單的待簽核清單
	 * @param it
	 * @param receipt_type
	 * @return
	 */
	private ArrayList choiseReceiptType(Connection conn, AuthorizedBean authbean, Iterator it, String receipt_type){
		
		ArrayList list = new ArrayList();
		
		try{
			
			if("EHF020200M0".equals(receipt_type)){  
				list = (ArrayList) this.getEHF020200M0List(conn, authbean, it);  //請假單
			}
			else if("EHF020800M0".equals(receipt_type)){
				list = (ArrayList) this.getEHF020800M0List(conn, authbean, it);  //加班單
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	private ArrayList getEHF020800M0List(Connection conn,
			AuthorizedBean authbean, Iterator it) {
		// TODO Auto-generated method stub
		
		ArrayList list = new ArrayList();
		
		try{
			
			if(it.hasNext()){
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				
				hr_tools.close();	
				
				String boss_sql = "" +
				" SELECT *, " +
				" DATE_FORMAT(EHF020800T0_06, '%Y年%c月%e日') AS EHF020800T0_06_YMD, " + //加班日期
				" EHF010100T0_02," + //填單人員工工號
				" EHF010100T0_05," + //填單人中文姓名
				" EHF000200T0_02, " + //加班部門代號
				" EHF000200T0_03, " + //加班部門中文名稱
				" EHF020800T0_10 " + //公司代碼
				
				" FROM EHF020800T0 " +
				" LEFT JOIN EHF010100T0 ON EHF020800T0_03=EHF010100T0_01 AND EHF020800T0_10=EHF010100T0.HR_CompanySysNo" +
				" LEFT JOIN EHF000200T0 ON EHF020800T0_04=EHF000200T0_01 AND EHF020800T0_10=EHF000200T0.HR_CompanySysNo" +
				" WHERE 1=1 " +
				" AND ( ";
				
				int TempCount = 0;
				while(it.hasNext()){
					Map tempMap = (Map) it.next();
					if(TempCount == 0){
						boss_sql += " EHF020800T0_01 = '"+tempMap.get("FORM_NO")+"' ";
					}else{
						boss_sql += " OR EHF020800T0_01 = '"+tempMap.get("FORM_NO")+"' ";
					}
					TempCount++;
				}
				
				boss_sql += " ) AND EHF020800T0_10 = '"+authbean.getCompId()+"' " +
				" ORDER BY EHF020800T0_06 DESC ";
				Statement stmt_boss = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs_boss = stmt_boss.executeQuery(boss_sql);
				
				Map queryCondMap = new HashMap();
				List EHF020800T1_LIST = new ArrayList();
				EHF020800 ehf020800 = new EHF020800();
				while(rs_boss.next()){
					
					EMS_VIEWDATAF bean1 = new EMS_VIEWDATAF();
					bean1.setDATA01(rs_boss.getString("EHF020800T0_06_YMD"));  //加班日期
					bean1.setDATA02(rs_boss.getString("EHF010100T0_02")+" "+rs_boss.getString("EHF010100T0_05"));  //填單人工號+姓名
					bean1.setDATA03(rs_boss.getString("EHF000200T0_02")+" "+rs_boss.getString("EHF000200T0_03"));  //加班部門代號+名稱
					bean1.setDATA04(rs_boss.getString("EHF020800T0_09_DESC"));  //處理狀態
					bean1.setDATA05(rs_boss.getString("EHF020800T0_01"));  //加班單編號
					bean1.setDATA10(rs_boss.getString("EHF020800T0_10"));  //公司代碼
					bean1.setDATA20("true");

					queryCondMap.put("EHF020800T0_01",bean1.getDATA05());
					queryCondMap.put("COMP_ID", bean1.getDATA10());
					EHF020800T1_LIST=ehf020800.queryEHF020800T1List(queryCondMap);
					bean1.setDATALIST(EHF020800T1_LIST);
					list.add(bean1);	
					
					
				}				
				ehf020800.close();
				rs_boss.close();
				stmt_boss.close();


				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * 取得銷假差待簽核清單
	 * @param conn
	 * @param authbean
	 * @param it
	 * @return
	 */
	private ArrayList getEHF020600M0List(Connection conn,
			AuthorizedBean authbean, Iterator it) {
		// TODO Auto-generated method stub
		
		ArrayList list = new ArrayList();
		
		try{
			
			if(it.hasNext()){
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				
				hr_tools.close();
				
				String boss_sql = "" +
				" SELECT *, " +
				" DATE_FORMAT(EHF020601T0_09, '%Y年%c月%e日') AS START_DATE, " +
				" DATE_FORMAT(EHF020601T0_10, '%Y年%c月%e日') AS END_DATE, " +
				" DATE_FORMAT(EHF020601T0_08, '%Y年%c月%e日') AS CREATE_DATE, " +
				" (CASE EHF020601T0_13 WHEN 01 THEN '請假單' WHEN 02 THEN '出差單' ELSE '' END) AS EHF020601T0_13_TXT, " +
				" (CASE EHF020601T0_13 WHEN 01 THEN EHF020200T0_13 WHEN 02 THEN EHF020300T0_13 ELSE '' END) AS dayhouse " +
				" FROM EHF020601T0 " +
				" LEFT JOIN EHF020200T0 ON EHF020200T0_01 = EHF020601T0_14 AND EHF020200T0_18 = EHF020601T0_18 " +
				" LEFT JOIN EHF020300T0 ON EHF020300T0_01 = EHF020601T0_14 AND EHF020300T0_21 = EHF020601T0_18 " +
				" WHERE 1=1 " +
				" AND ( ";
				
				int TempCount = 0;
				while(it.hasNext()){
					Map tempMap = (Map) it.next();
					if(TempCount == 0){
						boss_sql += " EHF020601T0_01 = '"+tempMap.get("FORM_NO")+"' ";
					}else{
						boss_sql += " OR EHF020601T0_01 = '"+tempMap.get("FORM_NO")+"' ";
					}
					TempCount++;
				}
				
				boss_sql += " ) AND EHF020601T0_18 = '"+authbean.getCompId()+"' " +
				" ORDER BY EHF020601T0_01 DESC ";
				
				Statement stmt_boss = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs_boss = stmt_boss.executeQuery(boss_sql);
				
				while(rs_boss.next()){
					
					EMS_VIEWDATAF bean = new EMS_VIEWDATAF();
					bean.setDATA01(rs_boss.getString("EHF020601T0_01"));  //銷假差單號
					bean.setDATA02(rs_boss.getString("CREATE_DATE"));  //申請日期
					bean.setDATA03((String) ((Map)empMap.get(rs_boss.getString("EHF020601T0_03"))).get("EMPLOYEE_NAME"));  //申請人
					bean.setDATA04(rs_boss.getString("EHF020601T0_13_TXT"));  //單別
					bean.setDATA09(rs_boss.getString("EHF020601T0_15"));  //事由
					bean.setDATA05(rs_boss.getString("START_DATE")+" "+rs_boss.getString("EHF020601T0_11").substring(0, 2)+"點 " +
					rs_boss.getString("EHF020601T0_11").substring(2, 4)+"分"+ " ~ " +rs_boss.getString("END_DATE")+" " +
					rs_boss.getString("EHF020601T0_12").substring(0, 2)+"點 "+rs_boss.getString("EHF020601T0_12").substring(2, 4)+"分 ");  //日期區間&時間區間
//					bean.setDATA21(rs_boss.getString("EHF020200T0_11")+ " ~ " +rs_boss.getString("EHF020200T0_12"));  //時間區間
					String[] dayhour = rs_boss.getString("EHF020601T0_19").split("/");
					bean.setDATA07(dayhour[0]+" 天 "+dayhour[1]+" 時");  //請假時數(天/小時)
					bean.setDATA08(rs_boss.getString("EHF020601T0_16_DESC"));  //表單狀態
					
					bean.setDATA20("true");  //flow_sign_flag
					
					list.add(bean);
				}
				
				rs_boss.close();
				stmt_boss.close();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}

	/**
	 * 取得請假單待簽核清單
	 * @param conn
	 * @param authbean
	 * @param it
	 * @return
	 */
	private ArrayList getEHF020200M0List(Connection conn, AuthorizedBean authbean, Iterator it){
		
		ArrayList list = new ArrayList();
		
		try{
			
			if(it.hasNext()){
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				
				hr_tools.close();
				
				String boss_sql = "" +
				" SELECT *, " +
				" DATE_FORMAT(EHF020200T0_09, '%Y年%c月%e日') AS START_DATE, " +
				" DATE_FORMAT(EHF020200T0_10, '%Y年%c月%e日') AS END_DATE, " +
				//" B.EHF020100T0_04 AS EHF020100T0_04 " +
				" EHF020100T0_04 AS 假別 " +
				" FROM EHF020200T0 " +
				" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 " +
				" WHERE 1=1 " +
				" AND ( ";
				
				int TempCount = 0;
				while(it.hasNext()){
					Map tempMap = (Map) it.next();
					if(TempCount == 0){
						boss_sql += " EHF020200T0_01 = '"+tempMap.get("FORM_NO")+"' ";
					}else{
						boss_sql += " OR EHF020200T0_01 = '"+tempMap.get("FORM_NO")+"' ";
					}
					TempCount++;
				}
				
				boss_sql += " ) AND EHF020200T0_18 = '"+authbean.getCompId()+"' " +
				" ORDER BY EHF020200T0_01 DESC ";
				
				Statement stmt_boss = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs_boss = stmt_boss.executeQuery(boss_sql);
				
				while(rs_boss.next()){
					
					EMS_VIEWDATAF bean = new EMS_VIEWDATAF();
					bean.setDATA01(rs_boss.getString("EHF020200T0_01"));  //請假單單號
					bean.setDATA02(rs_boss.getString("EHF020200T0_08"));  //申請日期
					bean.setDATA03((String) ((Map)empMap.get(rs_boss.getString("EHF020200T0_03"))).get("EMPLOYEE_NAME"));  //申請人
					
					if((Map)empMap.get(rs_boss.getString("EHF020200T0_07"))!=null){//有指定代理人
						bean.setDATA06((String) ((Map)empMap.get(rs_boss.getString("EHF020200T0_07"))).get("EMPLOYEE_NAME"));  //代理人
					}else{
						bean.setDATA06("");  //代理人
					}
					
					bean.setDATA04(rs_boss.getString("假別"));  //假別
					bean.setDATA09(rs_boss.getString("EHF020200T0_15"));  //事由
					bean.setDATA05(rs_boss.getString("START_DATE")+" "+rs_boss.getString("EHF020200T0_11").substring(0, 2)+"點 " +
					rs_boss.getString("EHF020200T0_11").substring(2, 4)+"分"+ " ~ " +rs_boss.getString("END_DATE")+" " +
					rs_boss.getString("EHF020200T0_12").substring(0, 2)+"點 "+rs_boss.getString("EHF020200T0_12").substring(2, 4)+"分 ");  //日期區間&時間區間
//					bean.setDATA21(rs_boss.getString("EHF020200T0_11")+ " ~ " +rs_boss.getString("EHF020200T0_12"));  //時間區間
					String[] dayhour = rs_boss.getString("EHF020200T0_13").split("/");
					bean.setDATA07(dayhour[0]+" 天 "+dayhour[1]+" 時");  //請假時數(天/小時)
					bean.setDATA08(rs_boss.getString("EHF020200T0_16_DESC"));  //表單狀態
					
					bean.setDATA20("true");  //flow_sign_flag
					
					list.add(bean);
				}
				
				rs_boss.close();
				stmt_boss.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 取得出差單待簽核清單
	 * @param conn
	 * @param authbean
	 * @param it
	 * @return
	 */
	private List getEHF020301M0List(Connection conn, AuthorizedBean authbean, Iterator it){
		
		ArrayList list = new ArrayList();
		
		try{
			
			if(it.hasNext()){
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				
				hr_tools.close();
				
				String boss_sql = "" +
				" SELECT *, " +
				" DATE_FORMAT(EHF020300T0_09, '%Y年%c月%e日') AS START_DATE, " +  //出差起始日期
				" DATE_FORMAT(EHF020300T0_10, '%Y年%c月%e日') AS END_DATE, " +  //出差結束日期
				" REPLACE(EHF020300T0_11, ':', '') AS START_TIME, " +  //出差起始時間
				" REPLACE(EHF020300T0_12, ':', '') AS END_TIME " +  //出差結束時間
				" FROM EHF020300T0 " +
				" WHERE 1=1 " +
				" AND ( ";
				
				int TempCount = 0;
				while(it.hasNext()){
					Map tempMap = (Map) it.next();
					if(TempCount == 0){
						boss_sql += " EHF020300T0_01 = '"+tempMap.get("FORM_NO")+"' ";  //出差單單號
					}else{
						boss_sql += " OR EHF020300T0_01 = '"+tempMap.get("FORM_NO")+"' ";  //出差單單號
					}
					TempCount++;
				}
				
				boss_sql += " ) AND EHF020300T0_21 = '"+authbean.getCompId()+"' " +  //公司代碼
				" ORDER BY EHF020300T0_01 DESC ";
				
				Statement stmt_boss =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				ResultSet rs_boss = stmt_boss.executeQuery(boss_sql);
				
				while(rs_boss.next()){
					
					EMS_VIEWDATAF bean = new EMS_VIEWDATAF();
					bean.setDATA01(rs_boss.getString("EHF020300T0_01"));  //出差單單號
					bean.setDATA02(rs_boss.getString("EHF020300T0_08"));  //申請日期
					bean.setDATA03((String) ((Map)empMap.get(rs_boss.getString("EHF020300T0_03"))).get("EMPLOYEE_NAME"));  //申請人
					bean.setDATA04(rs_boss.getString("EHF020300T0_15"));  //地點
					bean.setDATA05(rs_boss.getString("EHF020300T0_18"));  //事由
					bean.setDATA06(rs_boss.getString("START_DATE")+" "+rs_boss.getString("START_TIME").substring(0, 2)+"點 " +
					rs_boss.getString("START_TIME").substring(2, 4)+"分"+ " ~ " +rs_boss.getString("END_DATE")+" " +
					rs_boss.getString("END_TIME").substring(0, 2)+"點 "+rs_boss.getString("END_TIME").substring(2, 4)+"分 ");  //日期區間&時間區間
//					bean.setDATA07(rs_boss.getString("EHF020200T0_11")+ " ~ " +rs_boss.getString("EHF020200T0_12"));  //時間區間
					String[] dayhour = rs_boss.getString("EHF020300T0_13").split("/");
					bean.setDATA08(dayhour[0]+" 天 "+dayhour[1]+" 時");  //出差時數(天/小時)
					bean.setDATA09(rs_boss.getString("EHF020300T0_20_DESC"));  //表單狀態
					
					bean.setDATA20("true");  //flow_sign_flag
					
					list.add(bean);
				}
				
				rs_boss.close();
				stmt_boss.close();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**
	 * 取得表單的 EMS FLOW CLASS 依據表單型態(ex:請假單,出差單...)
	 * @param conn
	 * @param authbean
	 * @param receipt_type
	 * @return
	 */
	/*public EMS_FlowInterface getEmsFlowClassByType( String receipt_type ){
		
		EMS_FlowInterface ems_class = null;
		
		try{
			
			if("EHF020200M0".equals(receipt_type)){
				ems_class = (NTUPES_EBFlow) NTUPES_EBFlow.getInstance();
			}else if("EHF020301M0".equals(receipt_type)){
				ems_class = (NT_TravelFlow) NT_TravelFlow.getInstance();
			}else if("EHF020600M0".equals(receipt_type)){
				ems_class = (NT_CancelFlow) NT_CancelFlow.getInstance();
			}else if("EHF020701M0".equals(receipt_type)){
				ems_class = (NT_OvertimeFlow) NT_OvertimeFlow.getInstance();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ems_class;
	}*/
	
	public List getEmsEmergencySignList(String comp_id, String employee_id, String receipt_type){
		
		BaseFunction base_tools =  new BaseFunction();
		String sql = "";
		List emsFlowLog = null;
		
		try{
			if("EHF020200M0".equals(receipt_type)){	//請假單
				sql = " SELECT EHF020200T0_01 AS FORM_NO " +
						" FROM EHF020200T0 " +
						" WHERE 1=1 " +
						" AND EHF020200T0_18 = '" + comp_id + "' " +	//公司別
						" AND EHF020200T0_16 = '0002' ";	//表單狀態 = 呈核
			}
			//加班單尚無代理人欄位
			else if("EHF020800M0".equals(receipt_type)){	//加班單
				sql = " SELECT EHF020800T0_01 AS FORM_NO " +
						" FROM EHF020800T0 " +
						" WHERE 1=1 " +
						" AND EHF020800T0_10 = '" + comp_id + "' " +	//公司別
						" AND EHF020800T0_09 = '06' ";	//表單狀態 = 呈核
			}
			
			emsFlowLog = base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
			base_tools.close();
		}
		
		base_tools.close();
		
		return emsFlowLog;
		
	}
	
	/**
	 * 取得待簽核清單
	 * @param comp_id
	 * @param user_id
	 * @param receipt_type
	 * @return
	 */
	public List getEmsSignList(String comp_id, String user_id, String receipt_type){
		
		BaseFunction base_tools =  new BaseFunction();
		String sql = "";
		//List emsFlowLog = null;
		List emsFlowLog = new ArrayList();
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			if("EHF020200M0".equals(receipt_type)){	//請假單
				
				//emsFlowLog = spon_flow_tools.getUserCurrentSignList("HC_VACATION_FLOW", user_id, comp_id);
				
				List tempA = spon_flow_tools.getUserCurrentSignList("HC_VACATION_FLOW", user_id, comp_id);
				
				List tempB = spon_flow_tools.getUserCurrentSignList("HC_M_VACATION_FLOW", user_id, comp_id);
				
				List tempC = spon_flow_tools.getUserCurrentSignList("HC_M1_VACATION_FLOW", user_id, comp_id);
				
				List tempD = spon_flow_tools.getUserCurrentSignList("HC_M2_VACATION_FLOW", user_id, comp_id);
				
				if(!tempA.isEmpty()){
					emsFlowLog.addAll(tempA);
				}
				if(!tempB.isEmpty()){
					emsFlowLog.addAll(tempB);
				}
				if(!tempC.isEmpty()){
					emsFlowLog.addAll(tempC);
				}
				if(!tempD.isEmpty()){
					emsFlowLog.addAll(tempD);
				}
				
				Iterator it = emsFlowLog.iterator();
				Map tempMap = null;
				
				while(it.hasNext()){
					tempMap = (Map) it.next();
					//避免待簽核LOG是駁回或抽單後產生的
					if((Integer)tempMap.get("FLOW_SITE_SN") == 1){
						it.remove();
					}
					
				}
																
			}else if("EHF020800M0".equals(receipt_type)){	//加班單
				
				//emsFlowLog = spon_flow_tools.getUserCurrentSignList("HC_OVERTIME_FLOW", user_id, comp_id);
				
				List tempA = spon_flow_tools.getUserCurrentSignList("HC_OVERTIME_FLOW", user_id, comp_id);
				
				List tempB = spon_flow_tools.getUserCurrentSignList("HC_M_OVERTIME_FLOW", user_id, comp_id);
				
				List tempC = spon_flow_tools.getUserCurrentSignList("HC_M1_OVERTIME_FLOW", user_id, comp_id);
				
				List tempD = spon_flow_tools.getUserCurrentSignList("HC_M2_OVERTIME_FLOW", user_id, comp_id);
				
				if(!tempA.isEmpty()){
					emsFlowLog.addAll(tempA);
				}
				if(!tempB.isEmpty()){
					emsFlowLog.addAll(tempB);
				}
				if(!tempC.isEmpty()){
					emsFlowLog.addAll(tempC);
				}
				if(!tempD.isEmpty()){
					emsFlowLog.addAll(tempD);
				}
				
				Iterator it = emsFlowLog.iterator();
				Map tempMap = null;
				
				while(it.hasNext()){
					tempMap = (Map) it.next();
					//避免待簽核LOG是駁回或抽單後產生的
					if((Integer)tempMap.get("FLOW_SITE_SN") == 1){
						it.remove();
					}
					
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
			base_tools.close();
		}
		
		base_tools.close();
		
		return emsFlowLog;
		
	}
	
	/**
	 * //從緊急流程
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward EmergencySignForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		String flow_no = "";
		String emergency_flow_action = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//取得FLOW_NO
			//flow_no = this.getFlowNoByType(conn, Form.getDATA02(), getLoginUser(request).getCompId() );
			//取得FLOW_ACTION
			emergency_flow_action = request.getParameter("emergency_flow_action");
			
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
		
		return submitDatasModuleEmergency(mapping, form, request, response, flow_no, emergency_flow_action );
	}
	
	public ActionForward submitDatasModuleEmergency(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response,
			   String flow_no, String flow_action) {

		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		BaseFunction base_tools =  new BaseFunction();

		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId_boss");
		String comment = request.getParameter("comment");

		if ((arrid==null?false:arrid.length > 0)){
			formKey = arrid ;
		}else{
			request.setAttribute("MSG","請選取要查看的明細資料!!");
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

		try {
			ActionErrors lc_errors = new ActionErrors();
			String receipt_type = Form.getDATA02();
			AuthorizedBean authbean = getLoginUser(request);
			String comp_id = authbean.getCompId();  //公司代碼
			String agent_sign_table = "";	//簽核table
			String agent_sign_form_status = "";	//簽核表單狀態
			String agent_sign_form_status_desc = "";	//簽核表單狀態說明
			String agent_sign_form_status_code = ""; //flow_action
			String agent_sign_form_status_code_desc = ""; //flow_action說明
			String agent_sign_form_recieptNo = "";	//簽核表單單號
			String agent_sign_compId = "";	//簽核公司代碼
			
			float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, getLoginUser(request).getCompId(), "WORK_HOURS"));  //一個工作天的時數

			if (lc_errors.isEmpty() && !"".equals(receipt_type) && receipt_type != null ) {

				boolean error_flag = false;
				int Count = 0;
				int NGCount = 0;

				//依據表單型態取得簽核的元件
				//flow_tools = this.getEmsFlowClassByType(receipt_type);

				//簽核執行錯誤訊息
				StringBuffer ERROR_MSG = new StringBuffer();
				//Map msg_map = null;
				String error_msg = "";
				
				EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				boolean approve = false;

				//批次簽核
				for(int i=0; i<formKey.length; i++){

					if("EHF020200M0".equals(receipt_type)){	//請假單
						agent_sign_table = "EHF020200T0";
						agent_sign_form_status = "EHF020200T0_16";
						agent_sign_form_status_desc = "EHF020200T0_16_DESC";
						agent_sign_form_recieptNo = "EHF020200T0_01";
						agent_sign_compId = "EHF020200T0_18";
						approve = true;
					}
					//加班單尚無代理人欄位
					else if("EHF020800M0".equals(receipt_type)){	//加班單
						agent_sign_table = "EHF020800T0";
						agent_sign_form_status = "EHF020800T0_09";
						agent_sign_form_status_desc = "EHF020800T0_09_DESC";
						agent_sign_form_recieptNo = "EHF020800T0_01";
						agent_sign_compId = "EHF020800T0_10";
						approve = true;
					}
					
					if(approve && !"EHF020800M0".equals(receipt_type)){
						
						String sql = "";
						boolean agent_approve = false;

						if("0001".equals(flow_action)){	//呈核
							agent_sign_form_status_code = "0002";
							agent_sign_form_status_code_desc = "呈核";
						}else if("0002".equals(flow_action)){	//核准
							agent_sign_form_status_code = "0006";
							agent_sign_form_status_code_desc = "完成";	//因為人資核准就算完成了，所以狀態直接設定為完成
							agent_approve = true;
						}else if("0003".equals(flow_action)){	//駁回
							agent_sign_form_status_code = "0005";
							agent_sign_form_status_code_desc = "駁回";
						}else if("0013".equals(flow_action)){	//抽單
							agent_sign_form_status_code = "0008";
							agent_sign_form_status_code_desc = "抽單";
						}

						sql = " UPDATE " + agent_sign_table +
								" SET " + agent_sign_form_status + "='" + agent_sign_form_status_code + "'," +
								" " + agent_sign_form_status_desc + "='" + agent_sign_form_status_code_desc + "' " +
								" WHERE " + agent_sign_form_recieptNo + "='" + formKey[i] + "' " +
								" AND " + agent_sign_compId + "='" + getLoginUser(request).getCompId() + "'";

						if(base_tools.executeSQL(sql) > 0){
							Count++;

							base_tools.commit();
							
							//建立查詢資料Map
							Map queryCondMap = new HashMap();

							if(agent_approve){
								//若代理人核准，則以申請人名義"呈核"此單至ESA-Flow1
								/*if(flow_tools.flowSubmit(getEmscontext(), conn, authbean, formKey[i], comment)){
									//Count++;
								}else{
									Count--;
									NGCount++;
									//建立Flow簽核執行失敗訊息
									msg_map = flow_tools.getFlowSignMSG(conn, authbean, formKey[i], Form.getDATA01(), flow_no, flow_action);
									if(msg_map.containsKey("MSG") && !"".equals((String)msg_map.get("MSG"))){
										ERROR_MSG.append((String)msg_map.get("MSG"));  //錯誤訊息
										ERROR_MSG.append("<BR>");  //換行
									}
								}*/																										
								if("EHF020200M0".equals(receipt_type)){
									
									if("0006".equals(agent_sign_form_status_code)){
										/*
										sql = "" +
										" SELECT *, EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
										" FROM EHF020200T0 " +
										" WHERE 1=1 " +
										" AND EHF020200T0_01 = '"+formKey[i]+"' " +
										" AND EHF020200T0_18 = '"+authbean.getCompId()+"' ";
										ResultSet rs = stmt.executeQuery(sql);
										
										if(rs.next()){
											
											String[] time = rs.getString("EHF020200T0_13").split("/");
											
											int day = Integer.parseInt(time[0]);  //天數
											float hours = Float.parseFloat(time[1]);  //時數
											
											float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
											
											String chiyear = rs.getString("CHIYEAR");  //年度(yy)
											String employee_id = rs.getString("EHF020200T0_03");  //員工工號
											String va_type = rs.getString("EHF020200T0_14");  //假別代號
											String start_date = rs.getString("EHF020200T0_09");  //請假開始日期
											String end_date = rs.getString("EHF020200T0_10");  //請假結束日期
																																								
											setVaSql.NThandleVacationData(conn, chiyear, employee_id, va_type, true, 
																    	  start_date, end_date, totalhours, authbean.getCompId());
																																	
											//更新資料庫
											conn.commit();
											
										}			
										rs.close();
										*/
										//建立查詢資料Map
										queryCondMap = new HashMap();					
										queryCondMap.put("EHF020200T0_01", formKey[i]);  //表單編號
										
										setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());
										
									}
								}
							}
						}else{
							NGCount++;
						}
					}else{
						
						if("EHF020200M0".equals(receipt_type)){	//請假單						
							
						}
						/*else if("EHF020701M0".equals(receipt_type)){	//加班單
							
							error_msg = "尚有核定時數需填寫!無法使用批次功能。";
							ERROR_MSG.append(error_msg);  //錯誤訊息
							ERROR_MSG.append("<BR>");  //換行																
							
							request.setAttribute("ERROR_MSG", ERROR_MSG.toString());
							
							NGCount++;
							
						}*/
					}
					if(approve && "EHF020800M0".equals(receipt_type)){
						
							String sql = "";
							boolean agent_approve = false;

							if("0001".equals(flow_action)){	//呈核
								agent_sign_form_status_code = "06";
								agent_sign_form_status_code_desc = "呈核";
							}else if("0002".equals(flow_action)){	//核准
								agent_sign_form_status_code = "03";
								agent_sign_form_status_code_desc = "已確認";	//因為人資核准就算完成了，所以狀態直接設定為完成
								agent_approve = true;
							}else if("0003".equals(flow_action)){	//駁回
								agent_sign_form_status_code = "08";
								agent_sign_form_status_code_desc = "駁回";
							}/*else if("0013".equals(flow_action)){	//抽單
								agent_sign_form_status_code = "0008";
								agent_sign_form_status_code_desc = "抽單";
							}*/

						sql = " UPDATE " + agent_sign_table +
								" SET " + agent_sign_form_status + "='" + agent_sign_form_status_code + "'," +
								" " + agent_sign_form_status_desc + "='" + agent_sign_form_status_code_desc + "' " +
								" WHERE " + agent_sign_form_recieptNo + "='" + formKey[i] + "' " +
								" AND " + agent_sign_compId + "='" + getLoginUser(request).getCompId() + "'";

							if(base_tools.executeSQL(sql) > 0){
								Count++;

								base_tools.commit();
							
								//建立查詢資料Map
								Map queryCondMap = new HashMap();

								if(agent_approve){
									//若代理人核准，則以申請人名義"呈核"此單至ESA-Flow1
									/*if(flow_tools.flowSubmit(getEmscontext(), conn, authbean, formKey[i], comment)){
										//Count++;
									}else{
										Count--;
										NGCount++;
										//建立Flow簽核執行失敗訊息
										msg_map = flow_tools.getFlowSignMSG(conn, authbean, formKey[i], Form.getDATA01(), flow_no, flow_action);
										if(msg_map.containsKey("MSG") && !"".equals((String)msg_map.get("MSG"))){
											ERROR_MSG.append((String)msg_map.get("MSG"));  //錯誤訊息
											ERROR_MSG.append("<BR>");  //換行
										}
									}*/																										
									if("EHF020800M0".equals(receipt_type)){
									
										if("03".equals(agent_sign_form_status_code)){
											/*
											sql = "" +
											" SELECT *, EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
											" FROM EHF020200T0 " +
											" WHERE 1=1 " +
											" AND EHF020200T0_01 = '"+formKey[i]+"' " +
											" AND EHF020200T0_18 = '"+authbean.getCompId()+"' ";
											ResultSet rs = stmt.executeQuery(sql);
										
											if(rs.next()){
											
												String[] time = rs.getString("EHF020200T0_13").split("/");
											
												int day = Integer.parseInt(time[0]);  //天數
												float hours = Float.parseFloat(time[1]);  //時數
											
												float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
											
												String chiyear = rs.getString("CHIYEAR");  //年度(yy)
												String employee_id = rs.getString("EHF020200T0_03");  //員工工號
												String va_type = rs.getString("EHF020200T0_14");  //假別代號
												String start_date = rs.getString("EHF020200T0_09");  //請假開始日期
												String end_date = rs.getString("EHF020200T0_10");  //請假結束日期
																																								
												setVaSql.NThandleVacationData(conn, chiyear, employee_id, va_type, true, 
																    	  	start_date, end_date, totalhours, authbean.getCompId());
																																	
												//更新資料庫
												conn.commit();
											
											}			
											rs.close();
											 */
											//建立查詢資料Map
											queryCondMap = new HashMap();			
											Map signMap = new HashMap();	
											
											String sql_ds="";
											sql_ds =" SELECT EHF020800T0_01,EHF020800T0_12 FROM EHF020800T0  "+
													" WHERE EHF020800T0_01 ='"+formKey[i]+"'"+
													" AND EHF020800T0_10='"+getLoginUser(request).getCompId()+"'";
											
											List sign_list = base_tools.queryList(sql_ds);
											
											Iterator it = sign_list.iterator();
											
											while(it.hasNext()){
												
												Map columnMap = (Map) it.next();
												
												signMap.put("EHF020800T0_12", columnMap.get("EHF020800T0_12"));
												
											}
											
											queryCondMap.put("EHF020800T0_01", formKey[i]);  //表單編號											
											queryCondMap.put("EHF020800T0_12", signMap.get("EHF020800T0_12"));  //資料來源
											queryCondMap.put("COMP_ID", getLoginUser(request).getCompId());  //資料來源	
											//setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());
											EHF020800 ehf020800 = new EHF020800();
											ehf020800.confirm(queryCondMap);
											
											//建立加班記錄List
											List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
											//建立加班記錄連動元件
											EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
											//取得加班記錄 use 加班單
											overtime_list = ehf020800.queryOVList(queryCondMap);
											
											try {
												Map<String, String> Map = null;
												Map<String, String> return_map = null;

												Iterator<Map<String, String>> it1 = overtime_list.iterator();

												while (it1.hasNext()) {

													Map = it1.next();
													//轉換Map Data
													Map = ems_ov_ctrl.mappingOVMapData(Map);
													//重產考勤
													ehf020800.Again_Produce_att(conn,Map.get("EHF020801T0_04"),Map.get("EHF020801T0_02"),authbean.getCompId(),authbean.getUserId());
													//針對加班處理方式處理
													if("02".equals(Map.get("EHF020801T0_09"))){//換休->換補休
														//換休當年度可以使用到隔年6月底
//														EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
														setVaSql.updataOverTimeVacation("0006", Map, authbean);
													}
												}

											} catch (Exception e) {
												e.printStackTrace();
											}

											//關閉EHF020800 元件
											ehf020800.close();
											ems_ov_ctrl.close();
											// 更新資料庫
											conn.commit();
											
											
											//處理顯示訊息
											request.setAttribute("MSG_EDIT", "確認成功!!");
										}
									}
								}
							}else{
								NGCount++;
							}
						}
					
				}
				
				stmt.close();

				//處理Flow執行錯誤訊息顯示
				if(ERROR_MSG.length() > 0){
					request.setAttribute("ERROR_MSG", ERROR_MSG.toString());
				}

				//取得簽核訊息參數
				String shownsg = tools.getSysParam(conn, comp_id, "SHOW_FLOW_SIGN_MSG");
				if("Y".equals(shownsg)){
					//顯示完整簽核訊息
					request.setAttribute("MSG", Count+" 件 "+this.getFlowActionMSG(conn, flow_action, true, comp_id)+" , "+
												NGCount+" 件 "+this.getFlowActionMSG(conn, flow_action, false, comp_id));
				}else{
					//不顯示完成簽核訊息
					request.setAttribute("MSG", "共處理 "+(Count+NGCount)+" 件");
				}

				//清除簽核意見
				Form.setDATA01("");

				//設定前端顯示資訊
				request.setAttribute("Form1Datas", Form);

			} else {

				generateSelectBox(conn, Form, request);
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", this.getFlowActionMSG(conn, flow_action, false, comp_id));
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}

		} catch (Exception e) {
			System.out.println(e);
			base_tools.rollback();
			base_tools.close();
		} finally {
			try {
				base_tools.close();
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return queryDatas(mapping, form, request, response);
	}
	
	/*public void checkIdentityByHR(Connection conn, HttpServletRequest request, String formKey, String receipt_type){
		
		AuthorizedBean authbean = null;
		
		try{
			authbean = getLoginUser(request);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//取得FLOW_NO
		String flow_no = this.getFlowNoByType( conn, receipt_type, authbean.getCompId() );
		
		String receipt_no = formKey;
		
		//取得表單當前簽核者資訊
		List emsFlowLog2 = ems_tools.getEmsFlowLogList(getEmscontext(), authbean.getUserId(), "", "", flow_no, "2", "",
				receipt_no, authbean.getUserCode(), "2");
		Iterator its = emsFlowLog2.iterator();
		int idfinal = 0;
		while(its.hasNext()){
			Map flowlog = (Map)its.next();
			int flowid = (Integer)flowlog.get("SIGN_ID");
			if(idfinal < flowid){
				idfinal = flowid;
			}
		}
		
		try{
			//10.人事註記
			if(ems_tools.checkEmsRoleEmp(emscontext, authbean, "", "2", "0001", flow_no, "") && (idfinal == 10||idfinal == 9)){
				request.setAttribute("hr_manager", "yes");
				System.out.println("人事註記");
			}else{
				request.setAttribute("hr_manager", "no");
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}*/
	
	/*private String getSQL(HttpServletRequest request, Map dataMap,
			String type) {
		// TODO Auto-generated method stub
		
		type = (String) dataMap.get("EHF020701T0_01");
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" EHF020801T0_01 AS 加班記錄UID, " +
			" EHF020801T0_03 AS 加班單UID, " +
			" EHF020801T0_02 AS 員工工號, " +
			" EHF020403T0_02 AS 部門, " +
			" EHF020801T0_04 AS 考勤日期, " +
			" DATE_FORMAT(EHF020801T0_05, '%Y/%m/%d %H:%i:%s') AS 加班開始時間," +
			" DATE_FORMAT(EHF020801T0_06, '%Y/%m/%d %H:%i:%s') AS 加班結束時間," +
			" DATE_FORMAT(EHF020701T0.EHF020701T0_08, '%Y/%m/%d') AS 補休開始時間," +
			" DATE_FORMAT(EHF020701T0.EHF020701T0_19, '%Y/%m/%d') AS 補休結束時間," +
			" EHF020801T0_07 AS 加班總時數" +
			" FROM EHF020801T0" +
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020801T0_02 AND EHF020403T0_04 = EHF020801T0_08" +
			" LEFT JOIN EHF020701T0 ON EHF020701T0_01 = '"+(String) dataMap.get("EHF020701T0_01")+"' AND EHF020701T0_22 = '"+ getLoginUser(request).getCompId()+"' " +
			" WHERE 1=1 " +
			" AND EHF020801T0_08='"+ getLoginUser(request).getCompId()+"' "+
			" AND EHF020801T0_09='0' " +
			" AND EHF020801T0_02='"+(String) dataMap.get("EHF020701T0_03")+"' " +
			" AND EHF020801T0_03 IN('"+type+"') ";
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sql;
	}*/
	
	/**
	 * 取得EMS-Flow API flow 流程執行完畢後顯示的MSG內容
	 * @param flow_action
	 * @param isOK
	 * @return
	 */
	public String getFlowActionMSG( Connection conn, String flow_action , boolean isOK, String comp_id ){
		
		String MSG = "";
		
		try{
			String shownsg = tools.getSysParam(conn, comp_id, "SHOW_FLOW_SIGN_MSG");
			
			if("Y".equals(shownsg)){
				//顯示簽核訊息
				if("0001".equals(flow_action)){
					if(isOK){
						MSG = "呈核成功!!";
						return MSG;
					}else{
						MSG = "呈核失敗!!";
						return MSG;
					}
				}else if ("0002".equals(flow_action)){
					if(isOK){
						MSG = "核准成功!!";
						return MSG;
					}else{
						MSG = "核准失敗!!";
						return MSG;
					}
				}else if ("0003".equals(flow_action)){
					if(isOK){
						MSG = "駁回成功!!";
						return MSG;
					}else{
						MSG = "駁回失敗!!";
						return MSG;
					}
				}else if (("0013").equals(flow_action)){
					if(isOK){
						MSG = "抽單成功!!";
						return MSG;
					}else{
						MSG = "抽單失敗!!";
						return MSG;
					}
				}else if (("0014").equals(flow_action)){
					if(isOK){
						MSG = "作廢成功!!";
						return MSG;
					}else{
						MSG = "作廢失敗!!";
						return MSG;
					}
				}
			}else{
				//不顯示簽核訊息
				MSG = "";
				return MSG;
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return MSG;
	}
	
	/**
	 * 依據單號取得申請人部門組織、員工工號(代號)
	 * @param conn
	 * @param formKey
	 * @param comp_id
	 * @return
	 */
	private Map getVacationFormUserData(Connection conn, String formKey, String comp_id) {
		// TODO Auto-generated method stub
		
		Map dataMap = new HashMap();
		
		try{
			String sql = "" +
			" SELECT EHF020200T0_02, EHF020200T0_03, EHF020200T0_04 " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+formKey+"' " +
			" AND EHF020200T0_18 = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				dataMap.put("FLOW_NO", rs.getString("EHF020200T0_02"));
				dataMap.put("EMPLOYEE_ID", rs.getString("EHF020200T0_03"));
				dataMap.put("DEP_ID", rs.getString("EHF020200T0_04"));				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	/**
	 * 依據單號取得申請人部門組織、員工工號(代號)
	 * @param conn
	 * @param formKey
	 * @param comp_id
	 * @return
	 */
	private Map getOvertimeFormUserData(Connection conn, String formKey, String comp_id) {
		// TODO Auto-generated method stub
		
		Map dataMap = new HashMap();
		
		try{
			String sql = "" +
			" SELECT EHF020800T0_02, EHF020800T0_03, EHF020800T0_04 " +
			" FROM EHF020800T0 " +
			" WHERE 1=1 " +
			" AND EHF020800T0_01 = '"+formKey+"' " +
			" AND EHF020800T0_10 = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				dataMap.put("FLOW_NO", rs.getString("EHF020800T0_02"));
				dataMap.put("EMPLOYEE_ID", rs.getString("EHF020800T0_03"));
				dataMap.put("DEP_ID", rs.getString("EHF020800T0_04"));				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}

}
