package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF331000M0F extends ActionForm implements ValidateForm {

	private String EHF310400T1_10;  //實際收款日
	
	private String EHF310400T1_05;  //經手人員(員工姓名)
	private String EHF010100T0_02;  //員工工號
	private String EHF010100T0_01;  //系統代號
	private String EHF310100T0_03_B;
	private String EHF310100T0_03_E;
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		ActionErrors l_actionErrors = new ActionErrors();

		return l_actionErrors;
	}

	public void setEHF310400T1_10(String eHF310400T1_10) {
		EHF310400T1_10 = eHF310400T1_10;
	}

	public String getEHF310400T1_10() {
		return EHF310400T1_10;
	}

	public void setEHF310400T1_05(String eHF310400T1_05) {
		EHF310400T1_05 = eHF310400T1_05;
	}

	public String getEHF310400T1_05() {
		return EHF310400T1_05;
	}

	public void setEHF010100T0_02(String eHF010100T0_02) {
		EHF010100T0_02 = eHF010100T0_02;
	}

	public String getEHF010100T0_02() {
		return EHF010100T0_02;
	}

	public void setEHF010100T0_01(String eHF010100T0_01) {
		EHF010100T0_01 = eHF010100T0_01;
	}

	public String getEHF010100T0_01() {
		return EHF010100T0_01;
	}

	public void setEHF310100T0_03_B(String eHF310100T0_03_B) {
		EHF310100T0_03_B = eHF310100T0_03_B;
	}

	public String getEHF310100T0_03_B() {
		return EHF310100T0_03_B;
	}

	public void setEHF310100T0_03_E(String eHF310100T0_03_E) {
		EHF310100T0_03_E = eHF310100T0_03_E;
	}

	public String getEHF310100T0_03_E() {
		return EHF310100T0_03_E;
	}



}