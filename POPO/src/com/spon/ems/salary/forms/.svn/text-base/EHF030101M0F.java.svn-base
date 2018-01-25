package com.spon.ems.salary.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.utils.util.BA_Vaildate;

public class EHF030101M0F extends ActionForm {
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF030101T0_01;
	private  String  EHF030101T0_02;
	private  String  EHF030101T0_03;
	private  String  EHF030101T0_04;
	private  String  EHF030101T0_05;
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;

	private List EHF030101C=new ArrayList();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF030101T0_02, "EHF030101T0_02", "不可空白");

		
	
		
		
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
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			Statement stmt = conn.createStatement();
			String sql = "";
			
			sql += " SELECT * FROM EHF030101T0 " +
				   " WHERE EHF030101T0_02 = '"+EHF030101T0_02+"' " +
				   " AND EHF030101T0_05 = '"+request.getAttribute("compid")+"' ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				errors.add("EHF030101T0_02",new ActionMessage("薪資項目重複,請確認後再建立資料"));
			}
			
			rs.close();
			stmt.close();
			
		}catch (SQLException e) {
			System.out.println("EHF030101M0F.addData_validate()" + e);
		}

	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EHF030101C = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new EHF030101M0F();
				            }
			 });
		} catch (Exception e) {
		}

	}
	public boolean isCHECKED() {
		return CHECKED;
	}
	public void setCHECKED(boolean cHECKED) {
		CHECKED = cHECKED;
	}
	public boolean isCHANGED() {
		return CHANGED;
	}
	public void setCHANGED(boolean cHANGED) {
		CHANGED = cHANGED;
	}
	public String getEHF030101T0_01() {
		return EHF030101T0_01;
	}
	public void setEHF030101T0_01(String eHF030101T0_01) {
		EHF030101T0_01 = eHF030101T0_01;
	}
	public String getEHF030101T0_02() {
		return EHF030101T0_02;
	}
	public void setEHF030101T0_02(String eHF030101T0_02) {
		EHF030101T0_02 = eHF030101T0_02;
	}
	public String getEHF030101T0_03() {
		return EHF030101T0_03;
	}
	public void setEHF030101T0_03(String eHF030101T0_03) {
		EHF030101T0_03 = eHF030101T0_03;
	}
	public String getEHF030101T0_04() {
		return EHF030101T0_04;
	}
	public void setEHF030101T0_04(String eHF030101T0_04) {
		EHF030101T0_04 = eHF030101T0_04;
	}
	public String getEHF030101T0_05() {
		return EHF030101T0_05;
	}
	public void setEHF030101T0_05(String eHF030101T0_05) {
		EHF030101T0_05 = eHF030101T0_05;
	}
	public String getUSER_CREATE() {
		return USER_CREATE;
	}
	public void setUSER_CREATE(String uSERCREATE) {
		USER_CREATE = uSERCREATE;
	}
	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}
	public void setUSER_UPDATE(String uSERUPDATE) {
		USER_UPDATE = uSERUPDATE;
	}
	public int getVERSION() {
		return VERSION;
	}
	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}
	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}
	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}
	public List getEHF030101C() {
		return EHF030101C;
	}
	public void setEHF030101C(List eHF030101C) {
		EHF030101C = eHF030101C;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}