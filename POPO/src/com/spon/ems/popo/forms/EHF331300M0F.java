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

public class EHF331300M0F extends ActionForm implements ValidateForm {

	


	private String EHF331300T0_01;          //產婦的基本資料			系統編號
	private String EHF331300T0_02;          //產婦的基本資料			檔案編號
	private String EHF331300T0_03;			//產婦的基本資料			櫃號
	private String EHF331300T0_03_B;		//產婦的基本資料			櫃號(起)
	private String EHF331300T0_03_E;		//產婦的基本資料			櫃號(迄)
	private String EHF331300T0_04;			//產婦的基本資料			產婦姓名
	private String EHF331300T0_10;          //產婦的基本資料			送餐日期(起)	
	private String EHF331300T0_11;          //產婦的基本資料			送餐日期(迄)
	
	private String EHF310400T2_03;          //付款資訊1			訂餐天數
	private String EHF310400T2_03_A;          //付款資訊1			訂餐天數A
	private String EHF310400T2_03_B;          //付款資訊1			訂餐天數B
	private String EHF310400T2_03_C;          //付款資訊1			訂餐天數C
	
	
	
	
	private String EHF311300T0_21;			//付款資訊      			未收款項		
	private String EHF311300T0_22;			//付款資訊				已收款項
	
	
	
	private String EHF311300T0_06;			//贈品資訊				贈品類別
	private String EHF311300T0_06_TXT;		//贈品資訊				贈品類別(中文)

	
	
	

	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		ActionErrors l_actionErrors = new ActionErrors();
		
		
		if (request.getAttribute("action").equals("addData")) {
			//檢核欄位空白
//			BA_Vaildate ve=BA_Vaildate.getInstance();
//			ve.isEmpty(l_actionErrors, EHF330600T0_04, "EHF310100T0_04", "不可空白");
//			ve.isEmpty(l_actionErrors, EHF330600T0_21, "EHF310100T0_21", "不可空白");
//			ve.isEmpty(l_actionErrors, EHF330600T0_31, "EHF310100T0_31", "不可空白");
			
			
		}
		
		
		return l_actionErrors;
	}






	public void setEHF331300T0_01(String eHF331300T0_01) {
		EHF331300T0_01 = eHF331300T0_01;
	}






	public String getEHF331300T0_01() {
		return EHF331300T0_01;
	}






	public void setEHF331300T0_02(String eHF331300T0_02) {
		EHF331300T0_02 = eHF331300T0_02;
	}






	public String getEHF331300T0_02() {
		return EHF331300T0_02;
	}






	public void setEHF331300T0_03(String eHF331300T0_03) {
		EHF331300T0_03 = eHF331300T0_03;
	}






	public String getEHF331300T0_03() {
		return EHF331300T0_03;
	}






	public void setEHF331300T0_03_B(String eHF331300T0_03_B) {
		EHF331300T0_03_B = eHF331300T0_03_B;
	}






	public String getEHF331300T0_03_B() {
		return EHF331300T0_03_B;
	}






	public void setEHF331300T0_03_E(String eHF331300T0_03_E) {
		EHF331300T0_03_E = eHF331300T0_03_E;
	}






	public String getEHF331300T0_03_E() {
		return EHF331300T0_03_E;
	}






	public void setEHF331300T0_04(String eHF331300T0_04) {
		EHF331300T0_04 = eHF331300T0_04;
	}






	public String getEHF331300T0_04() {
		return EHF331300T0_04;
	}






	public void setEHF331300T0_10(String eHF331300T0_10) {
		EHF331300T0_10 = eHF331300T0_10;
	}






	public String getEHF331300T0_10() {
		return EHF331300T0_10;
	}






	public void setEHF331300T0_11(String eHF331300T0_11) {
		EHF331300T0_11 = eHF331300T0_11;
	}






	public String getEHF331300T0_11() {
		return EHF331300T0_11;
	}






	public void setEHF310400T2_03(String eHF310400T2_03) {
		EHF310400T2_03 = eHF310400T2_03;
	}






	public String getEHF310400T2_03() {
		return EHF310400T2_03;
	}






	public void setEHF310400T2_03_A(String eHF310400T2_03_A) {
		EHF310400T2_03_A = eHF310400T2_03_A;
	}






	public String getEHF310400T2_03_A() {
		return EHF310400T2_03_A;
	}






	public void setEHF310400T2_03_B(String eHF310400T2_03_B) {
		EHF310400T2_03_B = eHF310400T2_03_B;
	}






	public String getEHF310400T2_03_B() {
		return EHF310400T2_03_B;
	}






	public void setEHF310400T2_03_C(String eHF310400T2_03_C) {
		EHF310400T2_03_C = eHF310400T2_03_C;
	}






	public String getEHF310400T2_03_C() {
		return EHF310400T2_03_C;
	}






	public void setEHF311300T0_21(String eHF311300T0_21) {
		EHF311300T0_21 = eHF311300T0_21;
	}






	public String getEHF311300T0_21() {
		return EHF311300T0_21;
	}






	public void setEHF311300T0_22(String eHF311300T0_22) {
		EHF311300T0_22 = eHF311300T0_22;
	}






	public String getEHF311300T0_22() {
		return EHF311300T0_22;
	}






	public void setEHF311300T0_06(String eHF311300T0_06) {
		EHF311300T0_06 = eHF311300T0_06;
	}






	public String getEHF311300T0_06() {
		return EHF311300T0_06;
	}






	public void setEHF311300T0_06_TXT(String eHF311300T0_06_TXT) {
		EHF311300T0_06_TXT = eHF311300T0_06_TXT;
	}






	public String getEHF311300T0_06_TXT() {
		return EHF311300T0_06_TXT;
	}





}