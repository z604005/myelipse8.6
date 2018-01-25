package com.spon.ems.vacation.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.utils.util.BA_Vaildate;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/19 下午5:00:09
 */
public class EHF020700M0F extends ActionForm implements ValidateForm {
	
	private  String  EHF020700T0_01;
	private  String  EHF020700T0_02;
	private  String  EHF020700T0_03;
	private  String  EHF020700T0_03_TXT;
	private  String  EHF020700T0_03_SHOW;
	private  String  EHF020700T0_04;
	private  String  EHF020700T0_04_TXT;
	private  String  EHF020700T0_04_SHOW;
	private  String  EHF020700T0_05;
	private  String  EHF020700T0_05_TXT;
	private  String  EHF020700T0_05_SHOW;
	private  String  EHF020700T0_06;
	private  String  EHF020700T0_06_TXT;
	private  String  EHF020700T0_06_SHOW;
	private  String  EHF020700T0_07;
	private  String  EHF020700T0_08;
	private  String  EHF020700T0_09;
	private  String  EHF020700T0_09_DESC;
	private  String  EHF020700T0_10;
	private  String  EHF020700T0_10_HH;
	private  String  EHF020700T0_10_MM;
	private  String  EHF020700T0_11;
	private  String  EHF020700T0_11_HH;
	private  String  EHF020700T0_11_MM;
	private  String  EHF020700T0_12;
	private  String  EHF020700T0_13;
	private  String  EHF020700T0_14;
	private  String  EHF020700T0_14_DESC;
	private  String  EHF020700T0_15;
	
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;
	
	private List EHF020700C=new ArrayList();
	
	private FormFile UPLOADFILE;

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				ve.isEmpty(l_actionErrors, EHF020700T0_03, "EHF020700T0_03", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020700T0_08, "EHF020700T0_08", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020700T0_09, "EHF020700T0_09", "未打卡類別不可空白");
				//ve.isEmpty(l_actionErrors, EHF020700T0_09, "EHF020700T0_08", "未打卡類別不可空白");
				ve.isEmpty(l_actionErrors, EHF020700T0_12, "EHF020700T0_12", "不可空白");
				
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				addDetailData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				ve.isEmpty(l_actionErrors, EHF020700T0_03, "EHF020700T0_03", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020700T0_08, "EHF020700T0_08", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020700T0_09, "EHF020700T0_09", "未打卡類別不可空白");
				//ve.isEmpty(l_actionErrors, EHF020700T0_09, "EHF020700T0_08", "未打卡類別不可空白");
				ve.isEmpty(l_actionErrors, EHF020700T0_12, "EHF020700T0_12", "不可空白");
				
				saveData_validate(l_actionErrors, request, conn);
			}
		}

		return l_actionErrors;
	}

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	public String getEHF020700T0_01() {
		return EHF020700T0_01;
	}

	public void setEHF020700T0_01(String eHF020700T0_01) {
		EHF020700T0_01 = eHF020700T0_01;
	}

	public String getEHF020700T0_02() {
		return EHF020700T0_02;
	}

	public void setEHF020700T0_02(String eHF020700T0_02) {
		EHF020700T0_02 = eHF020700T0_02;
	}

	public String getEHF020700T0_03() {
		return EHF020700T0_03;
	}

	public void setEHF020700T0_03(String eHF020700T0_03) {
		EHF020700T0_03 = eHF020700T0_03;
	}

	public String getEHF020700T0_03_TXT() {
		return EHF020700T0_03_TXT;
	}

	public void setEHF020700T0_03_TXT(String eHF020700T0_03TXT) {
		EHF020700T0_03_TXT = eHF020700T0_03TXT;
	}

	public String getEHF020700T0_03_SHOW() {
		return EHF020700T0_03_SHOW;
	}

	public void setEHF020700T0_03_SHOW(String eHF020700T0_03SHOW) {
		EHF020700T0_03_SHOW = eHF020700T0_03SHOW;
	}

	public String getEHF020700T0_04() {
		return EHF020700T0_04;
	}

	public void setEHF020700T0_04(String eHF020700T0_04) {
		EHF020700T0_04 = eHF020700T0_04;
	}

	public String getEHF020700T0_04_TXT() {
		return EHF020700T0_04_TXT;
	}

	public void setEHF020700T0_04_TXT(String eHF020700T0_04TXT) {
		EHF020700T0_04_TXT = eHF020700T0_04TXT;
	}

	public String getEHF020700T0_04_SHOW() {
		return EHF020700T0_04_SHOW;
	}

	public void setEHF020700T0_04_SHOW(String eHF020700T0_04SHOW) {
		EHF020700T0_04_SHOW = eHF020700T0_04SHOW;
	}

	public String getEHF020700T0_05() {
		return EHF020700T0_05;
	}

	public void setEHF020700T0_05(String eHF020700T0_05) {
		EHF020700T0_05 = eHF020700T0_05;
	}

	public String getEHF020700T0_05_TXT() {
		return EHF020700T0_05_TXT;
	}

	public void setEHF020700T0_05_TXT(String eHF020700T0_05TXT) {
		EHF020700T0_05_TXT = eHF020700T0_05TXT;
	}

	public String getEHF020700T0_06() {
		return EHF020700T0_06;
	}

	public void setEHF020700T0_06(String eHF020700T0_06) {
		EHF020700T0_06 = eHF020700T0_06;
	}

	public String getEHF020700T0_06_TXT() {
		return EHF020700T0_06_TXT;
	}

	public void setEHF020700T0_06_TXT(String eHF020700T0_06TXT) {
		EHF020700T0_06_TXT = eHF020700T0_06TXT;
	}

	public String getEHF020700T0_07() {
		return EHF020700T0_07;
	}

	public void setEHF020700T0_07(String eHF020700T0_07) {
		EHF020700T0_07 = eHF020700T0_07;
	}

	public String getEHF020700T0_08() {
		return EHF020700T0_08;
	}

	public void setEHF020700T0_08(String eHF020700T0_08) {
		EHF020700T0_08 = eHF020700T0_08;
	}

	public String getEHF020700T0_09() {
		return EHF020700T0_09;
	}

	public void setEHF020700T0_09(String eHF020700T0_09) {
		EHF020700T0_09 = eHF020700T0_09;
	}

	public String getEHF020700T0_09_DESC() {
		return EHF020700T0_09_DESC;
	}

	public void setEHF020700T0_09_DESC(String eHF020700T0_09DESC) {
		EHF020700T0_09_DESC = eHF020700T0_09DESC;
	}

	public String getEHF020700T0_10() {
		return EHF020700T0_10;
	}

	public void setEHF020700T0_10(String eHF020700T0_10) {
		EHF020700T0_10 = eHF020700T0_10;
	}

	public String getEHF020700T0_10_HH() {
		return EHF020700T0_10_HH;
	}

	public void setEHF020700T0_10_HH(String eHF020700T0_10HH) {
		EHF020700T0_10_HH = eHF020700T0_10HH;
	}

	public String getEHF020700T0_10_MM() {
		return EHF020700T0_10_MM;
	}

	public void setEHF020700T0_10_MM(String eHF020700T0_10MM) {
		EHF020700T0_10_MM = eHF020700T0_10MM;
	}

	public String getEHF020700T0_11() {
		return EHF020700T0_11;
	}

	public void setEHF020700T0_11(String eHF020700T0_11) {
		EHF020700T0_11 = eHF020700T0_11;
	}

	public String getEHF020700T0_11_HH() {
		return EHF020700T0_11_HH;
	}

	public void setEHF020700T0_11_HH(String eHF020700T0_11HH) {
		EHF020700T0_11_HH = eHF020700T0_11HH;
	}

	public String getEHF020700T0_11_MM() {
		return EHF020700T0_11_MM;
	}

	public void setEHF020700T0_11_MM(String eHF020700T0_11MM) {
		EHF020700T0_11_MM = eHF020700T0_11MM;
	}

	public String getEHF020700T0_12() {
		return EHF020700T0_12;
	}

	public void setEHF020700T0_12(String eHF020700T0_12) {
		EHF020700T0_12 = eHF020700T0_12;
	}

	public String getEHF020700T0_13() {
		return EHF020700T0_13;
	}

	public void setEHF020700T0_13(String eHF020700T0_13) {
		EHF020700T0_13 = eHF020700T0_13;
	}

	public String getEHF020700T0_14() {
		return EHF020700T0_14;
	}

	public void setEHF020700T0_14(String eHF020700T0_14) {
		EHF020700T0_14 = eHF020700T0_14;
	}

	public String getEHF020700T0_14_DESC() {
		return EHF020700T0_14_DESC;
	}

	public void setEHF020700T0_14_DESC(String eHF020700T0_14DESC) {
		EHF020700T0_14_DESC = eHF020700T0_14DESC;
	}

	public String getEHF020700T0_15() {
		return EHF020700T0_15;
	}

	public void setEHF020700T0_15(String eHF020700T0_15) {
		EHF020700T0_15 = eHF020700T0_15;
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

	public List getEHF020700C() {
		return EHF020700C;
	}

	public void setEHF020700C(List eHF020700C) {
		EHF020700C = eHF020700C;
	}

	public void setEHF020700T0_05_SHOW(String eHF020700T0_05_SHOW) {
		EHF020700T0_05_SHOW = eHF020700T0_05_SHOW;
	}

	public String getEHF020700T0_05_SHOW() {
		return EHF020700T0_05_SHOW;
	}

	public void setEHF020700T0_06_SHOW(String eHF020700T0_06_SHOW) {
		EHF020700T0_06_SHOW = eHF020700T0_06_SHOW;
	}

	public String getEHF020700T0_06_SHOW() {
		return EHF020700T0_06_SHOW;
	}

	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}

	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}

}
