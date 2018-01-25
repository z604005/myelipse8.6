package com.spon.ems.hr.forms;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF010110M0F extends ActionForm implements ValidateForm {
	
	private String HR_EmployeeSysNo;
	private String HR_EmployeeNo;
	private String EHF010106T0_01;
	private String EHF010106T0_02;
	private String EHF010106T0_03;
	private String EHF010106T0_04;	//姓名(中)
	private String EHF010106T0_05;
	private String EHF010106T0_06;
	private String EHF010106T0_07;
	private String EHF010106T0_08;
	private String EHF010106T0_09;
	private String EHF010106T0_10;
	private String EHF010106T0_11;
	private String EHF010106T0_12;
	private String EHF010106T0_13;
	private String EHF010106T0_14;
	private String EHF010106T0_15;
	private String EHF010106T0_16;
	private String EHF010106T0_17;
	private String EHF010106T0_18;
	private Boolean EHF010106T0_20;
	private Boolean EHF010106T0_21;
	private String EHF010106T0_23;
	private String HR_CompanySysNo;
	
	private String HR_DepartmentSysNo;
	private String HR_DepartmentNo;
	private String HR_DepartmentName;
	
	private String HR_JobName;
	
	private List EHF010110M0_LIST = new ArrayList();

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
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
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}			
			
		}
		
		return l_actionErrors;
	}

	private void delData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
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

	public String getHR_EmployeeSysNo() {
		return HR_EmployeeSysNo;
	}

	public void setHR_EmployeeSysNo(String hREmployeeSysNo) {
		HR_EmployeeSysNo = hREmployeeSysNo;
	}

	public String getHR_EmployeeNo() {
		return HR_EmployeeNo;
	}

	public void setHR_EmployeeNo(String hREmployeeNo) {
		HR_EmployeeNo = hREmployeeNo;
	}

	public String getEHF010106T0_01() {
		return EHF010106T0_01;
	}

	public void setEHF010106T0_01(String eHF010106T0_01) {
		EHF010106T0_01 = eHF010106T0_01;
	}

	public String getEHF010106T0_02() {
		return EHF010106T0_02;
	}

	public void setEHF010106T0_02(String eHF010106T0_02) {
		EHF010106T0_02 = eHF010106T0_02;
	}

	public String getEHF010106T0_03() {
		return EHF010106T0_03;
	}

	public void setEHF010106T0_03(String eHF010106T0_03) {
		EHF010106T0_03 = eHF010106T0_03;
	}

	public String getEHF010106T0_04() {
		return EHF010106T0_04;
	}

	public void setEHF010106T0_04(String eHF010106T0_04) {
		EHF010106T0_04 = eHF010106T0_04;
	}

	public String getEHF010106T0_07() {
		return EHF010106T0_07;
	}

	public void setEHF010106T0_07(String eHF010106T0_07) {
		EHF010106T0_07 = eHF010106T0_07;
	}

	public String getEHF010106T0_10() {
		return EHF010106T0_10;
	}

	public void setEHF010106T0_10(String eHF010106T0_10) {
		EHF010106T0_10 = eHF010106T0_10;
	}

	public String getEHF010106T0_13() {
		return EHF010106T0_13;
	}

	public void setEHF010106T0_13(String eHF010106T0_13) {
		EHF010106T0_13 = eHF010106T0_13;
	}

	public String getHR_DepartmentSysNo() {
		return HR_DepartmentSysNo;
	}

	public void setHR_DepartmentSysNo(String hRDepartmentSysNo) {
		HR_DepartmentSysNo = hRDepartmentSysNo;
	}

	public String getHR_DepartmentNo() {
		return HR_DepartmentNo;
	}

	public void setHR_DepartmentNo(String hRDepartmentNo) {
		HR_DepartmentNo = hRDepartmentNo;
	}

	public String getHR_DepartmentName() {
		return HR_DepartmentName;
	}

	public void setHR_DepartmentName(String hRDepartmentName) {
		HR_DepartmentName = hRDepartmentName;
	}

	public List getEHF010110M0_LIST() {
		return EHF010110M0_LIST;
	}

	public void setEHF010110M0_LIST(List eHF010110M0LIST) {
		EHF010110M0_LIST = eHF010110M0LIST;
	}

	public String getEHF010106T0_05() {
		return EHF010106T0_05;
	}

	public void setEHF010106T0_05(String eHF010106T0_05) {
		EHF010106T0_05 = eHF010106T0_05;
	}

	public String getEHF010106T0_06() {
		return EHF010106T0_06;
	}

	public void setEHF010106T0_06(String eHF010106T0_06) {
		EHF010106T0_06 = eHF010106T0_06;
	}

	public String getEHF010106T0_08() {
		return EHF010106T0_08;
	}

	public void setEHF010106T0_08(String eHF010106T0_08) {
		EHF010106T0_08 = eHF010106T0_08;
	}

	public String getEHF010106T0_09() {
		return EHF010106T0_09;
	}

	public void setEHF010106T0_09(String eHF010106T0_09) {
		EHF010106T0_09 = eHF010106T0_09;
	}

	public String getEHF010106T0_11() {
		return EHF010106T0_11;
	}

	public void setEHF010106T0_11(String eHF010106T0_11) {
		EHF010106T0_11 = eHF010106T0_11;
	}

	public String getEHF010106T0_12() {
		return EHF010106T0_12;
	}

	public void setEHF010106T0_12(String eHF010106T0_12) {
		EHF010106T0_12 = eHF010106T0_12;
	}

	public String getEHF010106T0_14() {
		return EHF010106T0_14;
	}

	public void setEHF010106T0_14(String eHF010106T0_14) {
		EHF010106T0_14 = eHF010106T0_14;
	}

	public String getEHF010106T0_15() {
		return EHF010106T0_15;
	}

	public void setEHF010106T0_15(String eHF010106T0_15) {
		EHF010106T0_15 = eHF010106T0_15;
	}

	public String getEHF010106T0_16() {
		return EHF010106T0_16;
	}

	public void setEHF010106T0_16(String eHF010106T0_16) {
		EHF010106T0_16 = eHF010106T0_16;
	}

	public String getEHF010106T0_17() {
		return EHF010106T0_17;
	}

	public void setEHF010106T0_17(String eHF010106T0_17) {
		EHF010106T0_17 = eHF010106T0_17;
	}

	public String getEHF010106T0_18() {
		return EHF010106T0_18;
	}

	public void setEHF010106T0_18(String eHF010106T0_18) {
		EHF010106T0_18 = eHF010106T0_18;
	}

	public Boolean getEHF010106T0_20() {
		return EHF010106T0_20;
	}

	public void setEHF010106T0_20(Boolean eHF010106T0_20) {
		EHF010106T0_20 = eHF010106T0_20;
	}

	public Boolean getEHF010106T0_21() {
		return EHF010106T0_21;
	}

	public void setEHF010106T0_21(Boolean eHF010106T0_21) {
		EHF010106T0_21 = eHF010106T0_21;
	}

	public String getEHF010106T0_23() {
		return EHF010106T0_23;
	}

	public void setEHF010106T0_23(String eHF010106T0_23) {
		EHF010106T0_23 = eHF010106T0_23;
	}

	public String getHR_CompanySysNo() {
		return HR_CompanySysNo;
	}

	public void setHR_CompanySysNo(String hRCompanySysNo) {
		HR_CompanySysNo = hRCompanySysNo;
	}

	public String getHR_JobName() {
		return HR_JobName;
	}

	public void setHR_JobName(String hRJobName) {
		HR_JobName = hRJobName;
	}

}
