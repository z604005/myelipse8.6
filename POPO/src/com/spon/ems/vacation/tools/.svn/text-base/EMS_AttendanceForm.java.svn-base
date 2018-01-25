package com.spon.ems.vacation.tools;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.utils.util.BA_Vaildate;

public class EMS_AttendanceForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;

	private  String  EHF020401T0_01;
	private  String  EHF020401T0_02;
	private  String  EHF020401T0_03;
	private  String  EHF020401T0_04;
	private  String  EHF020401T0_05;
	private  String  EHF020401T0_06;
	private  String  EHF020401T0_07;
	private  String  EHF020401T0_08;
	private  String  EHF020401T0_08_FLE;
	private  String  EHF020401T0_09;
	private  String  EHF020401T0_09_DESC;
	private  String  EHF020401T0_10;
	private  String  EHF020401T0_11;
	
	private  String  EHF020404T0_01;
	private  String  EHF020404T0_02;
	private  String  EHF020404T0_03;
	private  String  EHF020404T0_04;
	private  String  EHF020404T0_05;
	private  String  EHF020404T0_06;
	private  String  EHF020404T0_07;
	private  String  EHF020404T0_08;
	private  String  EHF020404T0_09;
	
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
	public String getEHF020401T0_01() {
		return EHF020401T0_01;
	}
	public void setEHF020401T0_01(String eHF020401T0_01) {
		EHF020401T0_01 = eHF020401T0_01;
	}
	public String getEHF020401T0_02() {
		return EHF020401T0_02;
	}
	public void setEHF020401T0_02(String eHF020401T0_02) {
		EHF020401T0_02 = eHF020401T0_02;
	}
	public String getEHF020401T0_03() {
		return EHF020401T0_03;
	}
	public void setEHF020401T0_03(String eHF020401T0_03) {
		EHF020401T0_03 = eHF020401T0_03;
	}
	public String getEHF020401T0_04() {
		return EHF020401T0_04;
	}
	public void setEHF020401T0_04(String eHF020401T0_04) {
		EHF020401T0_04 = eHF020401T0_04;
	}
	public String getEHF020401T0_05() {
		return EHF020401T0_05;
	}
	public void setEHF020401T0_05(String eHF020401T0_05) {
		EHF020401T0_05 = eHF020401T0_05;
	}
	public String getEHF020401T0_06() {
		return EHF020401T0_06;
	}
	public void setEHF020401T0_06(String eHF020401T0_06) {
		EHF020401T0_06 = eHF020401T0_06;
	}
	public String getEHF020401T0_07() {
		return EHF020401T0_07;
	}
	public void setEHF020401T0_07(String eHF020401T0_07) {
		EHF020401T0_07 = eHF020401T0_07;
	}
	public String getEHF020401T0_08() {
		return EHF020401T0_08;
	}
	public void setEHF020401T0_08(String eHF020401T0_08) {
		EHF020401T0_08 = eHF020401T0_08;
	}
	public String getEHF020401T0_08_FLE() {
		return EHF020401T0_08_FLE;
	}
	public void setEHF020401T0_08_FLE(String eHF020401T0_08FLE) {
		EHF020401T0_08_FLE = eHF020401T0_08FLE;
	}
	public String getEHF020401T0_09() {
		return EHF020401T0_09;
	}
	public void setEHF020401T0_09(String eHF020401T0_09) {
		EHF020401T0_09 = eHF020401T0_09;
	}
	public String getEHF020401T0_09_DESC() {
		return EHF020401T0_09_DESC;
	}
	public void setEHF020401T0_09_DESC(String eHF020401T0_09DESC) {
		EHF020401T0_09_DESC = eHF020401T0_09DESC;
	}
	public String getEHF020401T0_10() {
		return EHF020401T0_10;
	}
	public void setEHF020401T0_10(String eHF020401T0_10) {
		EHF020401T0_10 = eHF020401T0_10;
	}
	public String getEHF020401T0_11() {
		return EHF020401T0_11;
	}
	public void setEHF020401T0_11(String eHF020401T0_11) {
		EHF020401T0_11 = eHF020401T0_11;
	}
	public String getEHF020404T0_01() {
		return EHF020404T0_01;
	}
	public void setEHF020404T0_01(String eHF020404T0_01) {
		EHF020404T0_01 = eHF020404T0_01;
	}
	public String getEHF020404T0_02() {
		return EHF020404T0_02;
	}
	public void setEHF020404T0_02(String eHF020404T0_02) {
		EHF020404T0_02 = eHF020404T0_02;
	}
	public String getEHF020404T0_03() {
		return EHF020404T0_03;
	}
	public void setEHF020404T0_03(String eHF020404T0_03) {
		EHF020404T0_03 = eHF020404T0_03;
	}
	public String getEHF020404T0_04() {
		return EHF020404T0_04;
	}
	public void setEHF020404T0_04(String eHF020404T0_04) {
		EHF020404T0_04 = eHF020404T0_04;
	}
	public String getEHF020404T0_05() {
		return EHF020404T0_05;
	}
	public void setEHF020404T0_05(String eHF020404T0_05) {
		EHF020404T0_05 = eHF020404T0_05;
	}
	public String getEHF020404T0_06() {
		return EHF020404T0_06;
	}
	public void setEHF020404T0_06(String eHF020404T0_06) {
		EHF020404T0_06 = eHF020404T0_06;
	}
	public String getEHF020404T0_07() {
		return EHF020404T0_07;
	}
	public void setEHF020404T0_07(String eHF020404T0_07) {
		EHF020404T0_07 = eHF020404T0_07;
	}
	public String getEHF020404T0_08() {
		return EHF020404T0_08;
	}
	public void setEHF020404T0_08(String eHF020404T0_08) {
		EHF020404T0_08 = eHF020404T0_08;
	}
	public String getEHF020404T0_09() {
		return EHF020404T0_09;
	}
	public void setEHF020404T0_09(String eHF020404T0_09) {
		EHF020404T0_09 = eHF020404T0_09;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;


	
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
		
		
		if (l_actionErrors.isEmpty()) {
			
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				if (l_actionErrors.isEmpty()) {
					addDetailData_validate(l_actionErrors, request, conn);
				}
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
		
		
	}
	
	
	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		
	}

	
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {

		} catch (Exception e) {
		}

	}

	
	
}