package com.spon.utils.sc.mobile;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.utils.sc.actions.LoginAction;
import com.spon.utils.sc.actions.SC003A;
import com.spon.utils.sc.forms.LoginForm;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.util.BA_TOOLS;

/**
 * Mobile 登入系統
 * @version 1.0
 * @create 2012/09/26
 */
@SuppressWarnings("serial")
public class MobileLoginAction extends LoginAction {

	public MobileLoginAction(){

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
		String forward = "";
		
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
			
			
			//取得Mobile登入資訊
			//設定login formbean
			login_form.setCompid(request.getParameter("comp_id"));  //公司代碼
			login_form.setUserid(request.getParameter("user_id"));  //帳號
			login_form.setPassword(request.getParameter("password"));  //密碼
			
			//驗證使用者
			ActionErrors lc_errors = new ActionErrors();
			//檢核欄位值不可空白
			login_form.isEmpty(lc_errors, login_form.getCompid(), "compid");
			login_form.isEmpty(lc_errors, login_form.getUserid(), "userid");
			login_form.isEmpty(lc_errors, login_form.getPassword(), "password");
			
			if (lc_errors.isEmpty()){
				try {
					String sql = "";
					sql = " select SC0030.*, EHF010100T0_01 from SC0030 " +
						  " LEFT JOIN EHF010100T0 ON EHF010100T0_I = SC0030_03 AND HR_CompanySysNo = SC0030_14 " +
						  " where 1=1 " +
						  " and SC0030_01 = ? " +
						  " and SC0030_14 = ? ";
					PreparedStatement pstmt = ems_db_c.getConn().prepareStatement(sql);
					int indexid = 1;
					pstmt.setString(indexid, login_form.getUserid());  //登入帳號
					indexid++;
					pstmt.setString(indexid, login_form.getCompid());  //公司代碼
					indexid++;
					
					//執行SQL
					ResultSet rs = pstmt.executeQuery();
					
					if (rs.next()) {
						//判斷帳號是否啟用
						if ("Y".equals(rs.getString("SC0030_05"))) {
							//判斷登入錯誤次數
							SC003A sc003a = new SC003A();
							if (sc003a.getLoginFail(ems_db_c.getConn(), login_form, request) <= 3) {
								// 密碼如果相等
								if (BA_TOOLS.chkTextInMD5(login_form.getPassword(), rs.getString("SC0030_02"))) {
									SC003F userform = new SC003F();
									//這一段是用來判斷使用者是否使用KEY登入
									//如果SC0030_13有值的話，代表該使用者需要用到KEY才可以登入
									//chacha是前端Submit過來的資料，須與SC0030_13做比對
									if(true){
										//Mobile -> 使用者使用瀏覽器登入
										userform.setLOGINMODE("");
									}
									//Create UserForm Data
									userform.setSC0030_01(rs.getString("SC0030_01"));
									userform.setSC0030_02(rs.getString("SC0030_02"));
									userform.setSC0030_03(rs.getString("SC0030_03"));
									userform.setSC0030_04(rs.getString("SC0030_04"));
									userform.setSC0030_05(rs.getString("SC0030_05"));
									userform.setSC0030_06(rs.getString("SC0030_06"));
									userform.setSC0030_07(rs.getString("SC0030_07"));
									userform.setSC0030_08(rs.getInt("SC0030_08"));
									userform.setSC0030_09(rs.getString("SC0030_09"));
									userform.setSC0030_10(rs.getString("SC0030_10"));
									userform.setSC0030_11(rs.getString("SC0030_11"));
									userform.setSC0030_12(rs.getString("SC0030_12"));
									userform.setSC0030_14(rs.getString("SC0030_14"));
									userform.setSC0030_EMPID(rs.getString("EHF010100T0_01")==null?"":rs.getString("EHF010100T0_01"));
									
									//因為整合EMS系統中的Struts虛擬框架, 必須要新增Session資訊使框架能正常運作, edit by joe 2012/09/07
									//建立 AuthBean 物件
									AuthorizedBean login_authBean = new AuthorizedBean();
									login_authBean.setEmployeeID(userform.getSC0030_EMPID());  //員工系統代碼
									login_authBean.setUserId(userform.getSC0030_01());	//登入者帳號
									login_authBean.setUserCode(userform.getSC0030_01());	//登入者帳號
									login_authBean.setUserName(userform.getSC0030_04());  //登入者姓名
									login_authBean.setCompId(userform.getSC0030_14());  //公司代碼
									
									// 將使用者資訊存入session中
									request.getSession().setAttribute("userform", userform);
									request.getSession().setAttribute("pgmsauth", login_form.showPgmListAndAuths(userform.getSC0030_01(), 
																	  ems_db_c.getConn(), userform.getLOGINMODE()));
									
									//將 AuthBean資訊存入Session中, edit by joe 2012/09/07
									request.getSession().setAttribute("auth", login_authBean);
									
									//所有程式清單 
									request.getSession().setAttribute("pgmlist", login_form.showPgmList( ems_db_c.getConn()));
									
									//登入成功, 清除登入失敗次數
									sc003a.setLoginSuccess( ems_db_c.getConn(), login_form, request);
									success_flag = true;
									message = "登入成功!!";
									
									//產生登入記錄
									super.createLoginLog(ems_db_c.getConn(), userform(request).getSC0030_01(), 
														 request.getRemoteAddr(), userform(request).getSC0030_14());
									
								}else {
									//使用者輸入密碼錯誤
									sc003a.setLoginFail(ems_db_c.getConn(), login_form, request);
									message = "密碼錯誤!!";
									forward = "success";
								}
							}else {
								//使用者登入帳號已被鎖定
								message = "帳號已鎖定!!";
								forward = "success";
							}
						}else {
							//使用者登入帳號已被停用
							message = "帳號已停用!!";
							forward = "success";
						}
					}else{
						//使用者登入帳號不存在
						message = "帳號不存在!!";
						forward = "success";
					}
					
					rs.close();
					pstmt.close();
					
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("MobileLoginForm.login() -> 驗證使用者帳號密碼資訊:" + e);
				}			
				
			}else{
				//使用者驗證失敗
				message = "公司代碼、帳號、密碼錯誤!!";
				forward = "success";
			}
			
			//取得 struts forward
			if(success_flag){
				forward = "success_pass";
			}

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
			
			//寫入訊息至前端
			//request.setAttribute("MSG", message);
			
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
	
	public ActionForward logout(ActionMapping mapping, ActionForm form,
							    HttpServletRequest request, HttpServletResponse response){
		
		try{
			//清除使用者登入 Login FormBean
			request.getSession().removeAttribute("userform");
			request.getSession().removeAttribute("auth");
			request.getSession().invalidate();
			
			//取得 struts forward
			String forward = "success";

			//建立 return JSON Object
			JSONObject resultJSON = new JSONObject();
			resultJSON.accumulate("success", true);  //是否成功
			resultJSON.accumulate("message", "登出成功!!");  //顯示訊息
			resultJSON.accumulate("forward", mapping.findForward(forward).getPath());  //程式導向路徑
			
			//將資料送前端頁面
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(resultJSON.toString());
			response.getWriter().flush();
			response.getWriter().close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	

}