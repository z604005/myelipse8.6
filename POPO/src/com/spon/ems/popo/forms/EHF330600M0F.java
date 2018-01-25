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

public class EHF330600M0F extends ActionForm implements ValidateForm {

	


	private String EHF330600T0_01;
	private String EHF330600T0_03;			//產婦的基本資料			櫃號
	private String EHF330600T0_03_B;		//產婦的基本資料			櫃號(起)
	private String EHF330600T0_03_E;		//產婦的基本資料			櫃號(迄)
	private String EHF330600T0_04;			//產婦的基本資料			產婦姓名
	private String EHF330600T0_10;          //產婦的基本資料			日期
	private String EHF330600T0_21;          //產婦的基本資料			日期
	private String EHF330600T0_31;          //產婦的基本資料			日期
	
	private String EHF310100T0_01_01;
	private String EHF310100T0_01_02;
	private String EHF310100T0_01_03;
	private String EHF310100T0_03_01;
	
	private String EHF330600T0__pic;    //圖檔存放位置
	
	
	
	private List EHF330600_C = new ArrayList();
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		ActionErrors l_actionErrors = new ActionErrors();
		
		
		if (request.getAttribute("action").equals("addData")) {
			//檢核欄位空白
			BA_Vaildate ve=BA_Vaildate.getInstance();
			ve.isEmpty(l_actionErrors, EHF330600T0_04, "EHF310100T0_04", "不可空白");
			ve.isEmpty(l_actionErrors, EHF330600T0_21, "EHF310100T0_21", "不可空白");
			ve.isEmpty(l_actionErrors, EHF330600T0_31, "EHF310100T0_31", "不可空白");
			
			
		}
		
		
		return l_actionErrors;
	}

	public void setEHF330600T0_10(String eHF330600T0_10) {
		EHF330600T0_10 = eHF330600T0_10;
	}

	public String getEHF330600T0_10() {
		return EHF330600T0_10;
	}

	public void setEHF330600T0_04(String eHF330600T0_04) {
		EHF330600T0_04 = eHF330600T0_04;
	}

	public String getEHF330600T0_04() {
		return EHF330600T0_04;
	}

	public void setEHF330600T0_03(String eHF330600T0_03) {
		EHF330600T0_03 = eHF330600T0_03;
	}

	public String getEHF330600T0_03() {
		return EHF330600T0_03;
	}


	public void setEHF330600T0_03_B(String eHF330600T0_03_B) {
		EHF330600T0_03_B = eHF330600T0_03_B;
	}

	public String getEHF330600T0_03_B() {
		return EHF330600T0_03_B;
	}

	public void setEHF330600T0_03_E(String eHF330600T0_03_E) {
		EHF330600T0_03_E = eHF330600T0_03_E;
	}

	public String getEHF330600T0_03_E() {
		return EHF330600T0_03_E;
	}

	public void setEHF330600_C(List eHF330600_C) {
		EHF330600_C = eHF330600_C;
	}

	public List getEHF330600_C() {
		return EHF330600_C;
	}

	public void setEHF330600T0_01(String eHF330600T0_01) {
		EHF330600T0_01 = eHF330600T0_01;
	}

	public String getEHF330600T0_01() {
		return EHF330600T0_01; 
	}

	public void setEHF330600T0_21(String eHF330600T0_21) {
		EHF330600T0_21 = eHF330600T0_21;
	}

	public String getEHF330600T0_21() {
		return EHF330600T0_21;
	}

	public void setEHF330600T0_31(String eHF330600T0_31) {
		EHF330600T0_31 = eHF330600T0_31;
	}

	public String getEHF330600T0_31() {
		return EHF330600T0_31;
	}

	public void setEHF310100T0_01_01(String eHF310100T0_01_01) {
		EHF310100T0_01_01 = eHF310100T0_01_01;
	}

	public String getEHF310100T0_01_01() {
		return EHF310100T0_01_01;
	}

	public void setEHF310100T0_01_02(String eHF310100T0_01_02) {
		EHF310100T0_01_02 = eHF310100T0_01_02;
	}

	public String getEHF310100T0_01_02() {
		return EHF310100T0_01_02;
	}

	public void setEHF310100T0_03_01(String eHF310100T0_03_01) {
		EHF310100T0_03_01 = eHF310100T0_03_01;
	}

	public String getEHF310100T0_03_01() {
		return EHF310100T0_03_01;
	}

	public void setEHF310100T0_01_03(String eHF310100T0_01_03) {
		EHF310100T0_01_03 = eHF310100T0_01_03;
	}

	public String getEHF310100T0_01_03() {
		return EHF310100T0_01_03;
	}

	public void setEHF330600T0__pic(String eHF330600T0__pic) {
		EHF330600T0__pic = eHF330600T0__pic;
	}

	public String getEHF330600T0__pic() {
		return EHF330600T0__pic;
	}



}