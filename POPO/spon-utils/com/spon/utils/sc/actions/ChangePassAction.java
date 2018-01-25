package com.spon.utils.sc.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;


import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.LoginForm;
import com.spon.utils.sc.forms.SC0031F;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.struts.form.BA_REPORTF;
//import com.spon.spos.sc.forms.ChangePassForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;


import fr.improve.struts.taglib.layout.util.FormUtils;


/**
 * 使用者管理
 * @version 1.0
 * @created 10-四月-2006 下午 04:37:53
 */
public class ChangePassAction extends NewDispatchAction {

	/**
	 * 使用者資訊
	 */
	//private SC003F userform;

	
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 使用者修改密碼作業 initial function
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		//request.setAttribute("DEPT_OTHERS", request.getAttribute("UNIT_OTHERS") + " and SC0030_14 = '"+userform(request).getSC0030_14()+"'");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;


		BA_REPORTF Form = new BA_REPORTF();
		String sql = "";
		
		try{
			conn = tools.getConnection();
			
			//System.out.println("使用者帳號 ==>"+userform(request).getSC0030_01());
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			sql = "select * from SC0030 " +
			" where SC0030_01='" + userform(request).getSC0030_01() + "' and SC0030_14 = '"+userform(request).getSC0030_14()+"' order by SC0030_01 " ;
			
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				Form.setDATA01(rs.getString("SC0030_01"));  //帳號
				Form.setDATA03(rs.getString("SC0030_02"));  //密碼
				Form.setDATA02(rs.getString("SC0030_04"));  //使用者名稱
				
//				System.out.println("使用者密碼 ==> "+rs.getString("SC0030_02"));
			}
			

			//Form.setSC0011(showGroupList(conn, Form, request));
			
//			generateSelectBox(conn,Form,request);
				
			request.setAttribute("Form1Datas", Form);
			
			//將表單的顯示模式設定為create (新增模式)
			FormUtils.setFormDisplayMode(request, form, "edit");
			
			
		}catch (Exception e){
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		//依據 struts-config.xml 的定義forward到showedit
		//雖然是新增狀態，但為了維護便利，不須再為新增再作一個表單
		//就沿用showedit即可
		
		request.setAttribute("action", "新增");
		
		String Required="true";
		
		request.setAttribute("Required",Required);
			
		return mapping.findForward("changepassword");
	}

	
	
	/**
	 * 修改使用者密碼
	 * 
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public ActionForward changePassword(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
//		ds = getDataSource(in_request, "SPOS");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		BA_REPORTF Form = (BA_REPORTF) form;
		ActionErrors l_actionErrors = new ActionErrors();
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        
        request.setAttribute("action","saveData");
//		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			ve.isEmpty(l_actionErrors, Form.getDATA03(), "DATA03","不可空白");
			ve.isEmpty(l_actionErrors, Form.getDATA04(), "DATA04","不可空白");
			
			if(l_actionErrors.isEmpty() && !Form.getDATA03().equals(""))
			 {
				 ve.isEquals(l_actionErrors, Form.getDATA03(), "DATA03",Form.getDATA04(),"DATA04","密碼確認錯誤");
			 }
			
			if (l_actionErrors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			String sql="";
			PreparedStatement stmt=null;
			
		  sql = "update SC0030 set " +
				" SC0030_02=?, SC0030_09=NOW(), DATE_UPDATE=NOW(), VERSION=VERSION+1, USER_UPDATE='"+userform(request).getSC0030_01()+"' " +
				" where SC0030_01 = '" + Form.getDATA01()+ "' "+
				" and SC0030_14 = '"+userform(request).getSC0030_14()+"' ";
				
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, BA_TOOLS.encodeInMD5(Form.getDATA03()));
			
			stmt.executeUpdate();
			stmt.close();
			
			conn.commit();
			
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("MSG", "密碼修改成功!");
			
			} else {
				saveErrors(request, l_actionErrors);
				request.setAttribute("save_status","error");
				request.setAttribute("Form1Datas",Form);
				request.setAttribute("MSG",request.getAttribute("ErrMSG") );
				return mapping.findForward("changepassword");
				
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "密碼修改失敗!");
			System.out.println("SC001A.saveData() "+e );
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//儲存後，回到顯示結果的頁面
		
		
		String Required="true";
		
		request.setAttribute("Required",Required);
		
		return mapping.findForward("changepassword");	

		
	}

	
}