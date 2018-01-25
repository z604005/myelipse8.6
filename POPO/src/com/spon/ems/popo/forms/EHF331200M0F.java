package com.spon.ems.popo.forms;

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
import org.apache.struts.upload.FormFile;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF331200M0F extends ActionForm implements ValidateForm {
	
	
	private String EHF310100T0_01;		
	private String EHF310100T0_03;	
	private String EHF310100T0_04;		
	private String EHF310500T0_05;
	private String EHF310500T0_04;	
	private String EHF310100T0_09;		
	private String EHF310500T0_06;	
	private String EHF310500T0_08;		
	private String EHF310100T0_34;
	private String EHF310500T0_09;
	private String EHF310100T0_01_01;
	private String EHF310100T0_01_02;
	private String EHF310100T0_01_03;
	private String EHF310100T0_03_01;
	private String EHF310100T0_03_02;	
	private String EHF310500T0_04_01;
	private String EHF310500T0_04_02;
	private String EHF310500T0_05_01;
	private String EHF310500T0_05_02;
	private List EHF310500T0_List = new ArrayList();
	private List listEHF310100T0_03_01 = new ArrayList();
	private List listEHF310100T0_03_02 = new ArrayList();
	
	
	
	
	
	
	//private List EHF331200C = new ArrayList();
	
	//private FormFile UPLOADFILE;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		//ve.isEmpty(l_actionErrors, EHF331200T0_02, "EHF331200T0_02", "不可空白");
		
		/*
		if("01".equals(EHF331200T0_06)){  //放款
			
			ve.isEmpty(l_actionErrors, EHF331200T0_08, "EHF331200T0_06", "放款項目不可空白");
			ve.isNumEmpty(l_actionErrors, String.valueOf(EHF331200T0_09), "EHF331200T0_06", "放款金額不可空白", true);
			
		}else if("02".equals(EHF331200T0_06)){  //扣款
			
			ve.isEmpty(l_actionErrors, EHF331200T0_10, "EHF331200T0_06", "扣款項目不可空白");
			ve.isNumEmpty(l_actionErrors, String.valueOf(EHF331200T0_11), "EHF331200T0_06", "扣款金額不可空白", true);
			
		}
		
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
		}*/

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

	public String getEHF310100T0_01() {
		return EHF310100T0_01;
	}

	public void setEHF310100T0_01(String eHF310100T0_01) {
		EHF310100T0_01 = eHF310100T0_01;
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

	public String getEHF310500T0_05() {
		return EHF310500T0_05;
	}

	public void setEHF310500T0_05(String eHF310500T0_05) {
		EHF310500T0_05 = eHF310500T0_05;
	}

	public String getEHF310500T0_04() {
		return EHF310500T0_04;
	}

	public void setEHF310500T0_04(String eHF310500T0_04) {
		EHF310500T0_04 = eHF310500T0_04;
	}

	public String getEHF310100T0_09() {
		return EHF310100T0_09;
	}

	public void setEHF310100T0_09(String eHF310100T0_09) {
		EHF310100T0_09 = eHF310100T0_09;
	}

	public String getEHF310500T0_06() {
		return EHF310500T0_06;
	}

	public void setEHF310500T0_06(String eHF310500T0_06) {
		EHF310500T0_06 = eHF310500T0_06;
	}

	public String getEHF310500T0_08() {
		return EHF310500T0_08;
	}

	public void setEHF310500T0_08(String eHF310500T0_08) {
		EHF310500T0_08 = eHF310500T0_08;
	}

	public String getEHF310100T0_34() {
		return EHF310100T0_34;
	}

	public void setEHF310100T0_34(String eHF310100T0_34) {
		EHF310100T0_34 = eHF310100T0_34;
	}

	public String getEHF310500T0_09() {
		return EHF310500T0_09;
	}

	public void setEHF310500T0_09(String eHF310500T0_09) {
		EHF310500T0_09 = eHF310500T0_09;
	}

	public String getEHF310100T0_01_01() {
		return EHF310100T0_01_01;
	}

	public void setEHF310100T0_01_01(String eHF310100T0_01_01) {
		EHF310100T0_01_01 = eHF310100T0_01_01;
	}

	public String getEHF310100T0_01_02() {
		return EHF310100T0_01_02;
	}

	public void setEHF310100T0_01_02(String eHF310100T0_01_02) {
		EHF310100T0_01_02 = eHF310100T0_01_02;
	}

	public String getEHF310100T0_01_03() {
		return EHF310100T0_01_03;
	}

	public void setEHF310100T0_01_03(String eHF310100T0_01_03) {
		EHF310100T0_01_03 = eHF310100T0_01_03;
	}

	public String getEHF310100T0_03_01() {
		return EHF310100T0_03_01;
	}

	public void setEHF310100T0_03_01(String eHF310100T0_03_01) {
		EHF310100T0_03_01 = eHF310100T0_03_01;
	}

	public String getEHF310100T0_03_02() {
		return EHF310100T0_03_02;
	}

	public void setEHF310100T0_03_02(String eHF310100T0_03_02) {
		EHF310100T0_03_02 = eHF310100T0_03_02;
	}

	public String getEHF310500T0_04_01() {
		return EHF310500T0_04_01;
	}

	public void setEHF310500T0_04_01(String eHF310500T0_04_01) {
		EHF310500T0_04_01 = eHF310500T0_04_01;
	}

	public String getEHF310500T0_04_02() {
		return EHF310500T0_04_02;
	}

	public void setEHF310500T0_04_02(String eHF310500T0_04_02) {
		EHF310500T0_04_02 = eHF310500T0_04_02;
	}

	public String getEHF310500T0_05_01() {
		return EHF310500T0_05_01;
	}

	public void setEHF310500T0_05_01(String eHF310500T0_05_01) {
		EHF310500T0_05_01 = eHF310500T0_05_01;
	}

	public String getEHF310500T0_05_02() {
		return EHF310500T0_05_02;
	}

	public void setEHF310500T0_05_02(String eHF310500T0_05_02) {
		EHF310500T0_05_02 = eHF310500T0_05_02;
	}

	public List getEHF310500T0_List() {
		return EHF310500T0_List;
	}

	public void setEHF310500T0_List(List eHF310500T0List) {
		EHF310500T0_List = eHF310500T0List;
	}

	public List getListEHF310100T0_03_01() {
		return listEHF310100T0_03_01;
	}

	public void setListEHF310100T0_03_01(List listEHF310100T0_03_01) {
		this.listEHF310100T0_03_01 = listEHF310100T0_03_01;
	}

	public List getListEHF310100T0_03_02() {
		return listEHF310100T0_03_02;
	}

	public void setListEHF310100T0_03_02(List listEHF310100T0_03_02) {
		this.listEHF310100T0_03_02 = listEHF310100T0_03_02;
	}



	
	

	
	
}
