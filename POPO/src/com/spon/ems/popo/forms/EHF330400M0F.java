package com.spon.ems.popo.forms;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF330400M0F extends ActionForm implements ValidateForm {
	
	private String EHF310100T1_01;
	private String EHF310100T1_02;
	private String EHF310100T1_03;
	private String EHF310100T1_04;
	private String EHF310100T1_04_TXT;
	
	private String EHF310100T0_03;
	private String EHF310100T0_04;
	private String EHF310100T0_11;
	private String EHF310100T0_16;
	private String EHF310100T0_17;
	private String EHF310100T0_31;
	
	private String EHF010100T0_01;
	private String EHF010100T0_02;
	private String EHF010100T0_05;

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			
		}
		
		return l_actionErrors;
	}

	public String getEHF310100T1_01() {
		return EHF310100T1_01;
	}

	public void setEHF310100T1_01(String eHF310100T1_01) {
		EHF310100T1_01 = eHF310100T1_01;
	}

	public String getEHF310100T1_02() {
		return EHF310100T1_02;
	}

	public void setEHF310100T1_02(String eHF310100T1_02) {
		EHF310100T1_02 = eHF310100T1_02;
	}

	public String getEHF310100T1_03() {
		return EHF310100T1_03;
	}

	public void setEHF310100T1_03(String eHF310100T1_03) {
		EHF310100T1_03 = eHF310100T1_03;
	}

	public String getEHF310100T1_04() {
		return EHF310100T1_04;
	}

	public void setEHF310100T1_04(String eHF310100T1_04) {
		EHF310100T1_04 = eHF310100T1_04;
	}

	public String getEHF310100T1_04_TXT() {
		return EHF310100T1_04_TXT;
	}

	public void setEHF310100T1_04_TXT(String eHF310100T1_04TXT) {
		EHF310100T1_04_TXT = eHF310100T1_04TXT;
	}

	public String getEHF310100T0_03() {
		return EHF310100T0_03;
	}

	public void setEHF310100T0_03(String eHF310100T0_03) {
		EHF310100T0_03 = eHF310100T0_03;
	}

	public String getEHF310100T0_04() {
		return EHF310100T0_04;
	}

	public void setEHF310100T0_04(String eHF310100T0_04) {
		EHF310100T0_04 = eHF310100T0_04;
	}

	public String getEHF310100T0_11() {
		return EHF310100T0_11;
	}

	public void setEHF310100T0_11(String eHF310100T0_11) {
		EHF310100T0_11 = eHF310100T0_11;
	}

	public String getEHF310100T0_16() {
		return EHF310100T0_16;
	}

	public void setEHF310100T0_16(String eHF310100T0_16) {
		EHF310100T0_16 = eHF310100T0_16;
	}

	public String getEHF310100T0_17() {
		return EHF310100T0_17;
	}

	public void setEHF310100T0_17(String eHF310100T0_17) {
		EHF310100T0_17 = eHF310100T0_17;
	}

	public String getEHF310100T0_31() {
		return EHF310100T0_31;
	}

	public void setEHF310100T0_31(String eHF310100T0_31) {
		EHF310100T0_31 = eHF310100T0_31;
	}

	public String getEHF010100T0_01() {
		return EHF010100T0_01;
	}

	public void setEHF010100T0_01(String eHF010100T0_01) {
		EHF010100T0_01 = eHF010100T0_01;
	}

	public String getEHF010100T0_02() {
		return EHF010100T0_02;
	}

	public void setEHF010100T0_02(String eHF010100T0_02) {
		EHF010100T0_02 = eHF010100T0_02;
	}

	public String getEHF010100T0_05() {
		return EHF010100T0_05;
	}

	public void setEHF010100T0_05(String eHF010100T0_05) {
		EHF010100T0_05 = eHF010100T0_05;
	}

}
