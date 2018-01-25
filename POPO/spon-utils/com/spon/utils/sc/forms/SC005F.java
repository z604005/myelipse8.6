//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.spon.utils.util.BA_Vaildate;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import java.text.SimpleDateFormat;
/** 
 * MyEclipse Struts
 * Creation date: 04-06-2006
 * 
 * XDoclet definition:
 * @struts.form name="SC004F"
 */
public class SC005F extends ActionForm {

	// --------------------------------------------------------- Instance Variables
	
	private String SC0050_01;

	private String SC0050_02;

	private String SC0050_03;
	
	private String SC0050_04;
	
	private String SC0050_05;
	
	private String SC0050_06;

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
		BA_Vaildate ve=BA_Vaildate.getInstance();
		ve.isEmpty(l_actionErrors, SC0050_01, "SC0050_01","不可空白");
		ve.isEmpty(l_actionErrors, SC0050_02, "SC0050_02","不可空白");
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
				String sql = "select * from SC0050 where SC0050_01='"
						+ SC0050_01 + "' and SC0050_02='" + SC0050_02 + "' and SC0050_06='"+SC0050_06+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					if (rs.getInt("VERSION") != VERSION) {
						in_actionErrors.add("VERSION",new ActionMessage("資料版本不符"));
						SC005F Form = new SC005F();
						Form.setSC0050_01(rs.getString("SC0050_01"));
						Form.setSC0050_02(rs.getString("SC0050_02"));
						Form.setSC0050_03(rs.getString("SC0050_03"));
						Form.setSC0050_04(rs.getString("SC0050_04"));
						Form.setSC0050_05(rs.getString("SC0050_05"));
						Form.setDATE_CREATE(rs.getString("DATE_CREATE"));
						Form.setDATE_UPDATE(rs.getString("DATE_UPDATE"));
						Form.setUSER_CREATE(rs.getString("USER_CREATE"));
						Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
						Form.setVERSION(rs.getInt("VERSION"));
						request.setAttribute("FormData", Form);
						request.setAttribute("ErrMSG", "資料版本不符，請重新修改!");
					}
				} else {
					in_actionErrors.add("SC0050_01", new ActionMessage("資料已被刪除"));
				}
				rs.close();
				stmt.close();

			} catch (Exception e) {
				System.out.println("SC005F.saveData_validate()" + e);
			}
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
				String sql = "select SC0050_01 from SC0050 where SC0050_01='"
						+ SC0050_01 + "' and SC0050_02='" + SC0050_02 + "' and SC0050_06='"+SC0050_06+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					in_actionErrors.add("SC0050_01", new ActionMessage("鍵值重複"));
				}
				rs.close();
				stmt.close();

			} catch (SQLException e) {
				System.out.println("SC005F.addData_validate()" + e);
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
	
	
	public String getSC0050_01() {
		return SC0050_01;
	}

	public void setSC0050_01(String sc0050_01) {
		SC0050_01 = sc0050_01;
	}

	public String getSC0050_02() {
		return SC0050_02;
	}

	public void setSC0050_02(String sc0050_02) {
		SC0050_02 = sc0050_02;
	}

	public String getSC0050_03() {
		return SC0050_03;
	}

	public void setSC0050_03(String sc0050_03) {
		SC0050_03 = sc0050_03;
	}

	public String getSC0050_04() {
		return SC0050_04;
	}

	public void setSC0050_04(String sc0050_04) {
		SC0050_04 = sc0050_04;
	}

	public String getSC0050_05() {
		return SC0050_05;
	}

	public void setSC0050_05(String sc0050_05) {
		SC0050_05 = sc0050_05;
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

	public String getSC0050_06() {
		return SC0050_06;
	}

	public void setSC0050_06(String sc0050_06) {
		SC0050_06 = sc0050_06;
	}

}
