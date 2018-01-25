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
 * Creation date: 05-11-2010
 * 
 * XDoclet definition:
 * @struts.form name="SC010F"
 */
public class SC010F extends ActionForm {

	// --------------------------------------------------------- Instance Variables
	
	private String SC_UNITM_01;

	private String SC_UNITM_02;

	private String SC_UNITM_03;
	
	private String SC_UNITM_04;
	
	private String SC_UNITM_05;
	
	private String SC_UNITM_06;
	
	private String SC_UNITM_07;
	
	private String SC_UNITM_08;
	
	private String SC_UNITM_09;
	
	private String SC_UNITM_10;
	
	private String SC_UNITM_11;
	
	private String SC_UNITM_12;

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
		ve.isEmpty(l_actionErrors, SC_UNITM_02, "SC_UNITM_02","不可空白");
		ve.isEmpty(l_actionErrors, SC_UNITM_08, "SC_UNITM_08","不可空白");
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
				String sql = "select * from SC_UNITM where SC_UNITM_01='"
						+ SC_UNITM_01 + "' and SC_UNITM_02='" + SC_UNITM_02 + "' and SC_UNITM_12='"+SC_UNITM_12+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					if (rs.getInt("VERSION") != VERSION) {
						in_actionErrors.add("VERSION",new ActionMessage("資料版本不符"));
						SC010F Form = new SC010F();
						Form.setSC_UNITM_01(rs.getString("SC_UNITM_01"));
						Form.setSC_UNITM_02(rs.getString("SC_UNITM_02"));
						Form.setSC_UNITM_08(rs.getString("SC_UNITM_08"));
						Form.setVERSION(rs.getInt("VERSION"));
						Form.setDATE_CREATE(rs.getString("DATE_CREATE"));
						Form.setDATE_UPDATE(rs.getString("DATE_UPDATE"));
						Form.setUSER_CREATE(rs.getString("USER_CREATE"));
						Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
						
						request.setAttribute("FormData", Form);
						request.setAttribute("ErrMSG", "資料版本不符，請重新修改!");
					}
				} else {
					in_actionErrors.add("SC_UNITM_01", new ActionMessage("資料已被刪除"));
				}
				rs.close();
				stmt.close();

			} catch (Exception e) {
				System.out.println("SC010F.saveData_validate()" + e);
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
				String sql = "select SC_UNITM_01 from SC_UNITM where SC_UNITM_01='"
						+ SC_UNITM_01 + "' and SC_UNITM_02='" + SC_UNITM_02 + "' and SC_UNITM_12='"+SC_UNITM_12+"'";
				ResultSet rs = stmt.executeQuery(sql);
				if (rs.next()) {
					in_actionErrors.add("SC_UNITM_01", new ActionMessage("鍵值重複"));
				}
				rs.close();
				stmt.close();

			} catch (SQLException e) {
				System.out.println("SC010F.addData_validate()" + e);
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

	public String getSC_UNITM_01() {
		return SC_UNITM_01;
	}

	public void setSC_UNITM_01(String sc_unitm_01) {
		SC_UNITM_01 = sc_unitm_01;
	}

	public String getSC_UNITM_02() {
		return SC_UNITM_02;
	}

	public void setSC_UNITM_02(String sc_unitm_02) {
		SC_UNITM_02 = sc_unitm_02;
	}

	public String getSC_UNITM_03() {
		return SC_UNITM_03;
	}

	public void setSC_UNITM_03(String sc_unitm_03) {
		SC_UNITM_03 = sc_unitm_03;
	}

	public String getSC_UNITM_04() {
		return SC_UNITM_04;
	}

	public void setSC_UNITM_04(String sc_unitm_04) {
		SC_UNITM_04 = sc_unitm_04;
	}

	public String getSC_UNITM_05() {
		return SC_UNITM_05;
	}

	public void setSC_UNITM_05(String sc_unitm_05) {
		SC_UNITM_05 = sc_unitm_05;
	}

	public String getSC_UNITM_06() {
		return SC_UNITM_06;
	}

	public void setSC_UNITM_06(String sc_unitm_06) {
		SC_UNITM_06 = sc_unitm_06;
	}

	public String getSC_UNITM_07() {
		return SC_UNITM_07;
	}

	public void setSC_UNITM_07(String sc_unitm_07) {
		SC_UNITM_07 = sc_unitm_07;
	}

	public String getSC_UNITM_08() {
		return SC_UNITM_08;
	}

	public void setSC_UNITM_08(String sc_unitm_08) {
		SC_UNITM_08 = sc_unitm_08;
	}

	public String getSC_UNITM_09() {
		return SC_UNITM_09;
	}

	public void setSC_UNITM_09(String sc_unitm_09) {
		SC_UNITM_09 = sc_unitm_09;
	}

	public String getSC_UNITM_10() {
		return SC_UNITM_10;
	}

	public void setSC_UNITM_10(String sc_unitm_10) {
		SC_UNITM_10 = sc_unitm_10;
	}

	public String getSC_UNITM_11() {
		return SC_UNITM_11;
	}

	public void setSC_UNITM_11(String sc_unitm_11) {
		SC_UNITM_11 = sc_unitm_11;
	}

	public String getSC_UNITM_12() {
		return SC_UNITM_12;
	}

	public void setSC_UNITM_12(String sc_unitm_12) {
		SC_UNITM_12 = sc_unitm_12;
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
	
	
	

}
