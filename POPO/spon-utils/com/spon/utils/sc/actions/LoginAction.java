package com.spon.utils.sc.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.utils.sc.forms.LoginForm;
import com.spon.utils.util.BA_TOOLS;


/**
 * 登入系統
 * @version 1.0
 * @created 07-四月-2006 下午 02:43:37
 */
public class LoginAction extends NewDispatchAction {

	public LoginAction(){

	}
	/**
	 * 程式一開始進來時，不做任何查詢
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		LoginForm login = (LoginForm)form;
		if (login.getUserid() != null & login.getPassword() != null){
			return login(mapping, form, request, response);
		}else{
			return mapping.findForward("success");
		}
	}
	
	public void finalize() throws Throwable {
		super.finalize();
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
		LoginForm login = (LoginForm)form;
		Connection conn = null;
		String forward = "";
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		conn = null;
		
		try {
			
			conn = tools.getConnection("SPOS");			
			
			ActionErrors lc_errors = login.validate(mapping, request, conn);
			if (lc_errors.isEmpty()){
				if (login.getPassword().length()< 8 ){
					forward = "pass_chang";
				}else{
					//BA_TOOLS ba = new BA_TOOLS().getInstance();
					forward = "pass";
				}
				//產生登入記錄
				this.createLoginLog(conn, userform(request).getSC0030_01(), request.getRemoteAddr(), userform(request).getSC0030_14());
				/*
				Statement stmt = conn.createStatement();
				stmt.executeUpdate("INSERT INTO login_log (`user`, `ip`, `date`, `comp`)" +
						" VALUES ('"+userform(request).getSC0030_01()+"', '"+request.getRemoteAddr()+"', NOW(), '"+userform(request).getSC0030_14()+"')");
				conn.commit();
				*/
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				forward = "success";
			}
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("LoginAction.login():" + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}

		return mapping.findForward(forward);
	}
	
	/**
	 * 產生登入記錄
	 * @param conn
	 * @param login_id
	 * @param ip
	 * @param comp_id
	 */
	protected void createLoginLog(Connection conn, String login_id, String ip, String comp_id){
		
		try{
			String sql = "";
			sql = " INSERT INTO login_log " +
				  " (`user`, `ip`, `date`, `comp`) " +
				  " VALUES " +
				  " ( ?, ?, NOW(), ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, login_id);  //登入帳號
			indexid++;
			p6stmt.setString(indexid, ip);  //IP
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			
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
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward loginScreen(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		try{
			//清除使用者登入 Login FormBean
			request.getSession().removeAttribute("userform");
			request.getSession().removeAttribute("auth");
			request.getSession().invalidate();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward("success");
	}
	
	public ActionForward menustatus(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
			//System.out.println(request.getParameter("mode"));
		request.getSession().setAttribute("MENUHIDESTATUS",request.getParameter("mode"));
		
		return null;
	}
	
	

}