package com.spon.ems.hr.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF010114M0F extends ActionForm implements ValidateForm {
	
	private String HR_EmployeeSysNo;
	private String HR_EmployeeNo;
	private String EHF010106T0_01;
	private String EHF010106T0_02;
	private String EHF010106T0_03;
	private String EHF010106T0_04;	//姓名(中)
	private String EHF010106T0_07;
	private String EHF010106T0_10;
	
	private String EHF010106T1_01;	//職務狀況
	private String EHF010106T1_02;	//日期
	
	private String EHF010106T1_02_take;
	private String EHF010106T1_02_leave;
	
	private String HR_DepartmentSysNo;
	private String HR_DepartmentNo;
	private String HR_DepartmentName;
	
	private String HR_JobNameSysNo;
	private String HR_JobNameNo;
	private String HR_JobName;
	
	private List EHF010114M0_LIST = new ArrayList();

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

	public String getEHF010106T1_01() {
		return EHF010106T1_01;
	}

	public void setEHF010106T1_01(String eHF010106T1_01) {
		EHF010106T1_01 = eHF010106T1_01;
	}

	public String getEHF010106T1_02() {
		return EHF010106T1_02;
	}

	public void setEHF010106T1_02(String eHF010106T1_02) {
		EHF010106T1_02 = eHF010106T1_02;
	}

	public String getEHF010106T1_02_take() {
		return EHF010106T1_02_take;
	}

	public void setEHF010106T1_02_take(String eHF010106T1_02Take) {
		EHF010106T1_02_take = eHF010106T1_02Take;
	}

	public String getEHF010106T1_02_leave() {
		return EHF010106T1_02_leave;
	}

	public void setEHF010106T1_02_leave(String eHF010106T1_02Leave) {
		EHF010106T1_02_leave = eHF010106T1_02Leave;
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

	public String getHR_JobNameSysNo() {
		return HR_JobNameSysNo;
	}

	public void setHR_JobNameSysNo(String hRJobNameSysNo) {
		HR_JobNameSysNo = hRJobNameSysNo;
	}

	public String getHR_JobNameNo() {
		return HR_JobNameNo;
	}

	public void setHR_JobNameNo(String hRJobNameNo) {
		HR_JobNameNo = hRJobNameNo;
	}

	public String getHR_JobName() {
		return HR_JobName;
	}

	public void setHR_JobName(String hRJobName) {
		HR_JobName = hRJobName;
	}

	public List getEHF010114M0_LIST() {
		return EHF010114M0_LIST;
	}

	public void setEHF010114M0_LIST(List eHF010114M0LIST) {
		EHF010114M0_LIST = eHF010114M0LIST;
	}

}
