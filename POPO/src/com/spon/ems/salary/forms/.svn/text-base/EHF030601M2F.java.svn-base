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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.utils.util.BA_Vaildate;

public class EHF030601M2F extends ActionForm {
	
	private String EHF030601T2_U;
	private String EHF030601T2_01;
	private String EHF030601T2_02;
	private String EHF030601T2_03;
	private String EHF030601T2_04;
	private String EHF030601T2_DS;
	private String EHF030601T2_05;
	private String EHF030601T2_06;

	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				addDetailData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
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
	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	}
	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
		} catch (Exception e) {
		}

	}

	public String getEHF030601T2_U() {
		return EHF030601T2_U;
	}

	public void setEHF030601T2_U(String eHF030601T2U) {
		EHF030601T2_U = eHF030601T2U;
	}

	public String getEHF030601T2_01() {
		return EHF030601T2_01;
	}

	public void setEHF030601T2_01(String eHF030601T2_01) {
		EHF030601T2_01 = eHF030601T2_01;
	}

	public String getEHF030601T2_02() {
		return EHF030601T2_02;
	}

	public void setEHF030601T2_02(String eHF030601T2_02) {
		EHF030601T2_02 = eHF030601T2_02;
	}

	public String getEHF030601T2_03() {
		return EHF030601T2_03;
	}

	public void setEHF030601T2_03(String eHF030601T2_03) {
		EHF030601T2_03 = eHF030601T2_03;
	}

	public String getEHF030601T2_04() {
		return EHF030601T2_04;
	}

	public void setEHF030601T2_04(String eHF030601T2_04) {
		EHF030601T2_04 = eHF030601T2_04;
	}

	public String getEHF030601T2_DS() {
		return EHF030601T2_DS;
	}

	public void setEHF030601T2_DS(String eHF030601T2DS) {
		EHF030601T2_DS = eHF030601T2DS;
	}

	public String getEHF030601T2_05() {
		return EHF030601T2_05;
	}

	public void setEHF030601T2_05(String eHF030601T2_05) {
		EHF030601T2_05 = eHF030601T2_05;
	}

	public String getEHF030601T2_06() {
		return EHF030601T2_06;
	}

	public void setEHF030601T2_06(String eHF030601T2_06) {
		EHF030601T2_06 = eHF030601T2_06;
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

	public String getDATE_CREATE() {
		return DATE_CREATE;
	}

	public void setDATE_CREATE(String dATECREATE) {
		DATE_CREATE = dATECREATE;
	}

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}

	
	
	
	
	
}
