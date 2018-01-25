package com.spon.utils.sc.mobile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.actions.BaseAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.utils.sc.forms.LoginForm;

/**
 * Mobile User Function 處理
 * @version 1.0
 * @create 2012/09/26
 */
public class MobileUserFunction extends BaseAction {

	public MobileUserFunction(){

	}
	
	/**
	 * Mobile Function init
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, 
							  HttpServletRequest request, HttpServletResponse response) {
		//進入Mobile Function 頁面
		return mapping.findForward("success");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		
		LoginForm login_form = (LoginForm)form;
		EMS_DB_CONTROLLER ems_db_c = null;
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
		
		try {
			//參數
			boolean success_flag = false;
			String message = "";
			String forward = "";
			
			//取得 struts forward
			forward = "success";

			//建立 return JSON Object
			JSONObject resultJSON = new JSONObject();
			resultJSON.accumulate("success", success_flag);  //是否成功
			resultJSON.accumulate("message", message);  //顯示訊息
			resultJSON.accumulate("forward", mapping.findForward(forward).getPath());  //程式導向路徑
			
			//將資料送前端頁面
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(resultJSON.toString());
			response.getWriter().flush();
			response.getWriter().close();
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("MobileLoginAction.login():" + e);
		} finally {
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		//return mapping.findForward(forward);
		return null;
	}

	

}