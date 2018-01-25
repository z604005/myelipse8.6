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

public class EHF330800M0F extends ActionForm implements ValidateForm {

	private String EHF310100T0_11;
	private String EHF310100T0_11_TXT;
	private String EHF310100T0_07_B;
	private String EHF310100T0_07_E;
	
	
	
	
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		ActionErrors l_actionErrors = new ActionErrors();		
		
		return l_actionErrors;
	}







	public void setEHF310100T0_11(String eHF310100T0_11) {
		EHF310100T0_11 = eHF310100T0_11;
	}







	public String getEHF310100T0_11() {
		return EHF310100T0_11;
	}







	public void setEHF310100T0_11_TXT(String eHF310100T0_11_TXT) {
		EHF310100T0_11_TXT = eHF310100T0_11_TXT;
	}







	public String getEHF310100T0_11_TXT() {
		return EHF310100T0_11_TXT;
	}







	public void setEHF310100T0_07_B(String eHF310100T0_07_B) {
		EHF310100T0_07_B = eHF310100T0_07_B;
	}







	public String getEHF310100T0_07_B() {
		return EHF310100T0_07_B;
	}







	public void setEHF310100T0_07_E(String eHF310100T0_07_E) {
		EHF310100T0_07_E = eHF310100T0_07_E;
	}







	public String getEHF310100T0_07_E() {
		return EHF310100T0_07_E;
	}


}