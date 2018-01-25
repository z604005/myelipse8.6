//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.utils.sc.actions.SC003A;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

/**
 * MyEclipse Struts Creation date: 04-07-2006
 * 
 * XDoclet definition:
 * 
 * @struts.form name="LoginForm"
 */
public class SC901F extends ActionForm {

	// --------------------------------------------------------- Instance
	// Variables

	/** password property */
	private String OLD_PASSWORD;
	
	private String NEW_PASSWORD;
	
	private String NEW_PASSWORD_CHK;

	/** userid property */
	private String USERID;


	// --------------------------------------------------------- Methods

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	
	

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();

		BA_Vaildate ve=BA_Vaildate.getInstance();
		// 檢查不可為空白
		ve.isEmpty(l_actionErrors, OLD_PASSWORD, "OLD_PASSWORD","請輸入舊密碼");
		ve.isEmpty(l_actionErrors, NEW_PASSWORD, "NEW_PASSWORD","請輸入新密碼");
		ve.isEmpty(l_actionErrors, NEW_PASSWORD_CHK, "NEW_PASSWORD_CHK","請輸入確認密碼");
		
		if (l_actionErrors.isEmpty()) {
			ve.isEquals(l_actionErrors, NEW_PASSWORD, "NEW_PASSWORD",NEW_PASSWORD_CHK, "NEW_PASSWORD_CHK","密碼不一致");
			ve.isLeastLength(l_actionErrors, NEW_PASSWORD, "NEW_PASSWORD", 8, "密碼長度不夠至少8碼");
			//判斷舊密碼是否正確
			try{
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery("Select SC0030_02 from SC0030 where SC0030_01='"+USERID+"'");
			if(rs.next())
			{
				if(!BA_TOOLS.chkTextInMD5(OLD_PASSWORD,rs.getString(1)))
				{
					l_actionErrors.add("OLD_PASSWORD",new ActionMessage("密碼輸入錯誤"));
				}
			}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return l_actionErrors;
	}

	public String getNEW_PASSWORD() {
		return NEW_PASSWORD;
	}

	public String getNEW_PASSWORD_CHK() {
		return NEW_PASSWORD_CHK;
	}

	public String getOLD_PASSWORD() {
		return OLD_PASSWORD;
	}

	public String getUSERID() {
		return USERID;
	}

	public void setNEW_PASSWORD(String new_password) {
		NEW_PASSWORD = new_password;
	}

	public void setNEW_PASSWORD_CHK(String new_password_chk) {
		NEW_PASSWORD_CHK = new_password_chk;
	}

	public void setOLD_PASSWORD(String old_password) {
		OLD_PASSWORD = old_password;
	}

	public void setUSERID(String userid) {
		USERID = userid;
	}

}
