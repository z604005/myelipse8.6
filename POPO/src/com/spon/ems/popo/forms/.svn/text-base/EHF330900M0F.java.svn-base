package com.spon.ems.popo.forms;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF330900M0F extends ActionForm implements ValidateForm {
	
	private String EHF310100T2_01;
	private int EHF310100T2_02;
	private String EHF310100T2_03;
	private String EHF310100T2_04;
	private String EHF310100T2_05;
	private String EHF310100T2_06;

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(l_actionErrors, EHF310100T2_03, "EHF310100T2_03", "不可空白");
		
		if (l_actionErrors.isEmpty()) {
			
			
		}
		
		return l_actionErrors;
	}

	public String getEHF310100T2_01() {
		return EHF310100T2_01;
	}

	public void setEHF310100T2_01(String eHF310100T2_01) {
		EHF310100T2_01 = eHF310100T2_01;
	}

	public int getEHF310100T2_02() {
		return EHF310100T2_02;
	}

	public void setEHF310100T2_02(int eHF310100T2_02) {
		EHF310100T2_02 = eHF310100T2_02;
	}

	public String getEHF310100T2_03() {
		return EHF310100T2_03;
	}

	public void setEHF310100T2_03(String eHF310100T2_03) {
		EHF310100T2_03 = eHF310100T2_03;
	}

	public String getEHF310100T2_04() {
		return EHF310100T2_04;
	}

	public void setEHF310100T2_04(String eHF310100T2_04) {
		EHF310100T2_04 = eHF310100T2_04;
	}

	public String getEHF310100T2_05() {
		return EHF310100T2_05;
	}

	public void setEHF310100T2_05(String eHF310100T2_05) {
		EHF310100T2_05 = eHF310100T2_05;
	}

	public String getEHF310100T2_06() {
		return EHF310100T2_06;
	}

	public void setEHF310100T2_06(String eHF310100T2_06) {
		EHF310100T2_06 = eHF310100T2_06;
	}

}
