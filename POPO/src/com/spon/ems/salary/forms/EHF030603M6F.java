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

public class EHF030603M6F extends ActionForm {
	
	private String EHF030603T6_U;
	private String EHF030603T6_01;
	private String EHF030603T6_02;
	private int EHF030603T6_03;
	private float EHF030603T6_04;
	private float EHF030603T6_05;
	private String EHF030603T6_DS;
	private String EHF030603T6_06;
	private String EHF030603T6_07;


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

	public String getEHF030603T6_U() {
		return EHF030603T6_U;
	}

	public void setEHF030603T6_U(String eHF030603T6U) {
		EHF030603T6_U = eHF030603T6U;
	}

	public String getEHF030603T6_01() {
		return EHF030603T6_01;
	}

	public void setEHF030603T6_01(String eHF030603T6_01) {
		EHF030603T6_01 = eHF030603T6_01;
	}

	public String getEHF030603T6_02() {
		return EHF030603T6_02;
	}

	public void setEHF030603T6_02(String eHF030603T6_02) {
		EHF030603T6_02 = eHF030603T6_02;
	}

	

	public int getEHF030603T6_03() {
		return EHF030603T6_03;
	}

	public void setEHF030603T6_03(int eHF030603T6_03) {
		EHF030603T6_03 = eHF030603T6_03;
	}

	public float getEHF030603T6_04() {
		return EHF030603T6_04;
	}

	public void setEHF030603T6_04(float eHF030603T6_04) {
		EHF030603T6_04 = eHF030603T6_04;
	}

	public float getEHF030603T6_05() {
		return EHF030603T6_05;
	}

	public void setEHF030603T6_05(float eHF030603T6_05) {
		EHF030603T6_05 = eHF030603T6_05;
	}

	public String getEHF030603T6_DS() {
		return EHF030603T6_DS;
	}

	public void setEHF030603T6_DS(String eHF030603T6DS) {
		EHF030603T6_DS = eHF030603T6DS;
	}

	public String getEHF030603T6_06() {
		return EHF030603T6_06;
	}

	public void setEHF030603T6_06(String eHF030603T6_06) {
		EHF030603T6_06 = eHF030603T6_06;
	}

	public String getEHF030603T6_07() {
		return EHF030603T6_07;
	}

	public void setEHF030603T6_07(String eHF030603T6_07) {
		EHF030603T6_07 = eHF030603T6_07;
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
