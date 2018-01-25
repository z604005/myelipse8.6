//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 04-06-2006
 * 
 * XDoclet definition:
 * @struts.form name="SC004F"
 */
public class SC004F extends ActionForm {


	private String SC0040_01;

	private String SC0040_02;

	private String SC0040_03;
	
	private int SC0040_04;
	
	private String SC0040_05;

	private String DATE_CREATE;

	private String DATE_UPDATE;

	private String USER_CREATE;

	private String USER_UPDATE;

	private int VERSION;

	// --------------------------------------------------------- Methods

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		isEmpty(l_actionErrors, SC0040_01, "SC0040_01");
		isEmpty(l_actionErrors, SC0040_02, "SC0040_02");

		if (l_actionErrors.isEmpty()) {
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				addData_validate(l_actionErrors, request, conn);
			}

			//	     修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				saveData_validate(l_actionErrors, request, conn);
			}
		}

		return l_actionErrors;
	}
	/**
	 * 儲存資料前作資料版本的比對
	 * 
	 * @param in_actionErrors
	 * @param request
	 * @param conn
	 */
	private void saveData_validate(ActionErrors in_actionErrors,
			HttpServletRequest request, Connection conn) {
		if (conn != null) {
			try {
				Statement stmt = conn.createStatement();
				String sql = "select * from SC0040 where SC0040_01='"
						+ SC0040_01 + "' and SC0040_05='"+SC0040_05+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					if (rs.getInt("VERSION") != VERSION) {
						in_actionErrors.add("VERSION",new ActionMessage("資料版本不符"));
						
						SC004F Form = new SC004F();
						Form.setSC0040_01(rs.getString("SC0040_01"));
						Form.setSC0040_02(rs.getString("SC0040_02"));
						Form.setSC0040_03(rs.getString("SC0040_03"));
						Form.setDATE_CREATE(rs.getString("DATE_CREATE"));
						Form.setDATE_UPDATE(rs.getString("DATE_UPDATE"));
						Form.setUSER_CREATE(rs.getString("USER_CREATE"));
						Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
						Form.setVERSION(rs.getInt("VERSION"));
						request.setAttribute("FormData", Form);
						request.setAttribute("ErrMSG", "資料版本不符，請重新修改!");
					}
				} else {
					in_actionErrors.add("SC0040_01", new ActionMessage("資料已被刪除"));
				}
				rs.close();
				stmt.close();

			} catch (Exception e) {
				System.out.println("SC004F.saveData_validate()" + e);
			}
		}

	}

	/**
	 * 檢查欄位值是否為空白
	 * 
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 */
	private void isEmpty(ActionErrors in_actionErrors, String in_parameter,
			String in_nameParameter) {
		if (in_parameter == null || in_parameter.equals("")) {
			in_actionErrors.add(in_nameParameter, new ActionMessage("不可空白"));
		}
	}

	/**
	 * 新增資料時，檢查鍵值有無重複
	 * @param in_actionErrors
	 * @param request
	 * @param conn
	 */
	private void addData_validate(ActionErrors in_actionErrors,
			HttpServletRequest request, Connection conn) {
		if (conn != null) {
			try {
				Statement stmt = conn.createStatement();
				String sql = "select SC0040_01 from SC0040 where SC0040_01='"
						+ SC0040_01 + "' and SC0040_05='"+SC0040_05+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					in_actionErrors.add("SC0040_01", new ActionMessage("鍵值重複"));
				}
				rs.close();
				stmt.close();

			} catch (SQLException e) {
				System.out.println("SC004F.addData_validate()" + e);
			}
		}
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	/** 
	 * Returns the SC0040_03.
	 * @return String
	 */
	public String getSC0040_03() {
		return SC0040_03;
	}

	/** 
	 * Set the SC0040_03.
	 * @param SC0040_03 The SC0040_03 to set
	 */
	public void setSC0040_03(String SC0040_03) {
		this.SC0040_03 = SC0040_03;
	}

	/** 
	 * Returns the SC0040_01.
	 * @return String
	 */
	public String getSC0040_01() {
		return SC0040_01;
	}

	/** 
	 * Set the SC0040_01.
	 * @param SC0040_01 The SC0040_01 to set
	 */
	public void setSC0040_01(String SC0040_01) {
		this.SC0040_01 = SC0040_01;
	}

	/** 
	 * Returns the SC0040_02.
	 * @return String
	 */
	public String getSC0040_02() {
		return SC0040_02;
	}

	/** 
	 * Set the SC0040_02.
	 * @param SC0040_02 The SC0040_02 to set
	 */
	public void setSC0040_02(String SC0040_02) {
		this.SC0040_02 = SC0040_02;
	}

	public String getDATE_CREATE() {
		return DATE_CREATE;
	}

	public void setDATE_CREATE(String date_create) {
		DATE_CREATE = date_create;
	}

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	public void setDATE_UPDATE(String date_update) {
		DATE_UPDATE = date_update;
	}

	public String getUSER_CREATE() {
		return USER_CREATE;
	}

	public void setUSER_CREATE(String user_create) {
		USER_CREATE = user_create;
	}

	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}

	public void setUSER_UPDATE(String user_update) {
		USER_UPDATE = user_update;
	}

	public int getVERSION() {
		return VERSION;
	}

	public void setVERSION(int version) {
		VERSION = version;
	}

	public int getSC0040_04() {
		return SC0040_04;
	}

	public void setSC0040_04(int sc0040_04) {
		SC0040_04 = sc0040_04;
	}

	public String getSC0040_05() {
		return SC0040_05;
	}

	public void setSC0040_05(String sc0040_05) {
		SC0040_05 = sc0040_05;
	}

}
