package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;



import com.spon.ems.abs.interfaces.ValidateForm;

public class EHF330500M0F extends ActionForm implements ValidateForm {


	//餐點預排管理
	private  String  EHF320300T0_01;
	private  String  EHF320300T0_02;//日期
	private  String  EHF320300T0_03;//上菜順序天
	private  String  EHF320300T0_04;//公司代碼

	//餐點預排明細
	private  String  EHF320300T1_01;//系統編號
	private  String  EHF320300T1_02;//上菜順序項次
	private  String  EHF320300T1_03;//菜單餐別
	private  String  EHF320300T1_04;//菜單類別
	private  String  EHF320300T1_05;//菜單編號
	
	
	//菜單管理
	private  String EHF320100T0_01;//系統編號
	private  String EHF320100T0_02;//菜單編號
	private  String EHF320100T0_03;//菜單名稱
	private  String EHF320100T0_04;//菜單餐別
	private  String EHF320100T0_05;//菜單類別
	private  String EHF320100T0_06;//菜單用酒
	private  String EHF320100T0_07;//菜單用油
	private  String EHF320100T0_08;//公司代碼

	

	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stu
		ActionErrors lc_error=new ActionErrors() ;
		
		return lc_error;
	}



	public void setEHF320300T0_01(String eHF320300T0_01) {
		EHF320300T0_01 = eHF320300T0_01;
	}



	public String getEHF320300T0_01() {
		return EHF320300T0_01;
	}



	public void setEHF320300T0_02(String eHF320300T0_02) {
		EHF320300T0_02 = eHF320300T0_02;
	}



	public String getEHF320300T0_02() {
		return EHF320300T0_02;
	}



	public void setEHF320300T0_03(String eHF320300T0_03) {
		EHF320300T0_03 = eHF320300T0_03;
	}



	public String getEHF320300T0_03() {
		return EHF320300T0_03;
	}



	public void setEHF320300T0_04(String eHF320300T0_04) {
		EHF320300T0_04 = eHF320300T0_04;
	}



	public String getEHF320300T0_04() {
		return EHF320300T0_04;
	}



	public void setEHF320300T1_01(String eHF320300T1_01) {
		EHF320300T1_01 = eHF320300T1_01;
	}



	public String getEHF320300T1_01() {
		return EHF320300T1_01;
	}



	public void setEHF320300T1_02(String eHF320300T1_02) {
		EHF320300T1_02 = eHF320300T1_02;
	}



	public String getEHF320300T1_02() {
		return EHF320300T1_02;
	}



	public void setEHF320300T1_04(String eHF320300T1_04) {
		EHF320300T1_04 = eHF320300T1_04;
	}



	public String getEHF320300T1_04() {
		return EHF320300T1_04;
	}



	public void setEHF320300T1_03(String eHF320300T1_03) {
		EHF320300T1_03 = eHF320300T1_03;
	}



	public String getEHF320300T1_03() {
		return EHF320300T1_03;
	}



	public void setEHF320300T1_05(String eHF320300T1_05) {
		EHF320300T1_05 = eHF320300T1_05;
	}



	public String getEHF320300T1_05() {
		return EHF320300T1_05;
	}



	public void setEHF320100T0_01(String eHF320100T0_01) {
		EHF320100T0_01 = eHF320100T0_01;
	}



	public String getEHF320100T0_01() {
		return EHF320100T0_01;
	}



	public void setEHF320100T0_02(String eHF320100T0_02) {
		EHF320100T0_02 = eHF320100T0_02;
	}



	public String getEHF320100T0_02() {
		return EHF320100T0_02;
	}



	public void setEHF320100T0_03(String eHF320100T0_03) {
		EHF320100T0_03 = eHF320100T0_03;
	}



	public String getEHF320100T0_03() {
		return EHF320100T0_03;
	}



	public void setEHF320100T0_04(String eHF320100T0_04) {
		EHF320100T0_04 = eHF320100T0_04;
	}



	public String getEHF320100T0_04() {
		return EHF320100T0_04;
	}



	public void setEHF320100T0_08(String eHF320100T0_08) {
		EHF320100T0_08 = eHF320100T0_08;
	}



	public String getEHF320100T0_08() {
		return EHF320100T0_08;
	}



	public void setEHF320100T0_07(String eHF320100T0_07) {
		EHF320100T0_07 = eHF320100T0_07;
	}



	public String getEHF320100T0_07() {
		return EHF320100T0_07;
	}



	public void setEHF320100T0_06(String eHF320100T0_06) {
		EHF320100T0_06 = eHF320100T0_06;
	}



	public String getEHF320100T0_06() {
		return EHF320100T0_06;
	}



	public void setEHF320100T0_05(String eHF320100T0_05) {
		EHF320100T0_05 = eHF320100T0_05;
	}



	public String getEHF320100T0_05() {
		return EHF320100T0_05;
	}

}
