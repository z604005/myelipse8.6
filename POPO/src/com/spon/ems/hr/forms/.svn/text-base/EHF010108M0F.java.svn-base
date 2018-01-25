package com.spon.ems.hr.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

public class EHF010108M0F extends ActionForm implements ValidateForm{

	private String HR_DepartmentSysNo;
	private String HR_DepartmentNo;
	private String HR_DepartmentName;
	private String HR_UpDepartmentSysNo;
	private String HR_DepartmentLevel;
	private String EHF010108T0_01;
	private String EHF010108T0_02;
	private String EHF010108T0_03;
	private String EHF010108T0_04;
	private String HR_CompanySysNo;
	
	private String HR_UpDepartmentName;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String HR_LastUpdateDate;
	
	private List EHF010108T0_LIST = new ArrayList();
	
	
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
				
				ve.isEmpty(l_actionErrors, HR_DepartmentNo, "HR_DepartmentNo", "不可空白");  //
				ve.isEmpty(l_actionErrors, HR_DepartmentName, "HR_DepartmentName", "不可空白");  //
				ve.isEmpty(l_actionErrors, HR_UpDepartmentSysNo, "HR_UpDepartmentSysNo", "不可空白");  //
				ve.isEmpty(l_actionErrors, EHF010108T0_02, "EHF010108T0_02", "不可空白");  //
				
				if((!"".equals(getHR_DepartmentNo()) && getHR_DepartmentNo()!=null) && !GenericValidator.maxLength(getHR_DepartmentNo(), 10)){//
					l_actionErrors.add("HR_DepartmentNo", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門代碼字數不得超過10個字!!");
				}
				if((!"".equals(getHR_DepartmentName()) && getHR_DepartmentName()!=null) && !GenericValidator.maxLength(getHR_DepartmentName(), 200)){//
					l_actionErrors.add("HR_DepartmentName", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門名稱字數不得超過200個字!!");
				}
				if((!"".equals(getEHF010108T0_01()) && getEHF010108T0_01()!=null) && !GenericValidator.maxLength(getEHF010108T0_01(), 20)){//
					l_actionErrors.add("EHF010108T0_01", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門簡稱字數不得超過20個字!!");
				}
				if((!"".equals(getEHF010108T0_04()) && getEHF010108T0_04()!=null) && !GenericValidator.maxLength(getEHF010108T0_04(), 50)){//
					l_actionErrors.add("EHF010108T0_04", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門簡介字數不得超過50個字!!");
				}
								
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				addDetailData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				ve.isEmpty(l_actionErrors, HR_DepartmentNo, "HR_DepartmentNo", "不可空白");  //
				ve.isEmpty(l_actionErrors, HR_DepartmentName, "HR_DepartmentName", "不可空白");  //
				ve.isEmpty(l_actionErrors, EHF010108T0_02, "EHF010108T0_02", "不可空白");  //
				
				if((!"".equals(getHR_DepartmentNo()) && getHR_DepartmentNo()!=null) && !GenericValidator.maxLength(getHR_DepartmentNo(), 10)){//
					l_actionErrors.add("HR_DepartmentNo", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門代碼字數不得超過10個字!!");
				}
				if((!"".equals(getHR_DepartmentName()) && getHR_DepartmentName()!=null) && !GenericValidator.maxLength(getHR_DepartmentName(), 200)){//
					l_actionErrors.add("HR_DepartmentName", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門名稱字數不得超過200個字!!");
				}
				if((!"".equals(getEHF010108T0_01()) && getEHF010108T0_01()!=null) && !GenericValidator.maxLength(getEHF010108T0_01(), 20)){//
					l_actionErrors.add("EHF010108T0_01", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門簡稱字數不得超過20個字!!");
				}
				if((!"".equals(getEHF010108T0_04()) && getEHF010108T0_04()!=null) && !GenericValidator.maxLength(getEHF010108T0_04(), 50)){//
					l_actionErrors.add("EHF010108T0_04", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"部門簡介字數不得超過50個字!!");
				}

				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}
			
			//
			/*if (request.getAttribute("action").equals("deleteEHF010108T0")) {
				
				deleteEHF010108T0_validate(l_actionErrors, request, conn);
			}*/
			
		}
		
		return l_actionErrors;
	}

	/*private void deleteEHF010108T0_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM EHF010108T0 WHERE HR_UpDepartmentSysNo = '"
					+ HR_DepartmentSysNo + "' and HR_CompanySysNo = '"+request.getAttribute("compid")+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {				
				errors.add("HR_DepartmentSysNo", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
									"":request.getAttribute("ERR_MSG")+"<br>")+"該部門還有轄下部門，請勿刪除!!");				
			}
			
			rs.close();
			stmt.close();
			
		}catch (SQLException e) {
			System.out.println("EHF010108M0F.delData_validate()" + e);
		}
		
	}*/

	private void delData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tool = BA_TOOLS.getInstance();
		
		try{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM EHF010108T0 WHERE HR_UpDepartmentSysNo = '"
					+ HR_DepartmentSysNo + "' and HR_CompanySysNo = '"+request.getAttribute("compid")+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {				
				errors.add("HR_DepartmentSysNo", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
									"":request.getAttribute("ERR_MSG")+"<br>")+"該部門還有轄下部門，請勿刪除!!");				
			}
			
			rs.close();
			stmt.close();
			
		}catch (SQLException e) {
			System.out.println("EHF010108M0F.delData_validate()" + e);
		}
		
		try{
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM EHF010106T6 WHERE HR_DepartmentSysNo = '" + HR_DepartmentSysNo + "' " +
						 "and '"+tool.ymdTostring(tool.getSysDateYYMMDD())+"' between DATE_FORMAT(EHF010106T6_01, '%Y/%m/%d') and DATE_FORMAT(EHF010106T6_02, '%Y/%m/%d')" +
						 "and HR_CompanySysNo = '"+request.getAttribute("compid")+"'";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {				
				errors.add("HR_DepartmentSysNo", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
									"":request.getAttribute("ERR_MSG")+"<br>")+"尚有員工歸屬該部門，請勿刪除!!");				
			}
			
			rs.close();
			stmt.close();
			
		}catch (SQLException e) {
			System.out.println("EHF010108M0F.delData_validate()" + e);
		}
		
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
		
		try{
			Statement stmt = conn.createStatement();
			
			String sql = " SELECT HR_DepartmentNo FROM EHF010108T0 WHERE HR_DepartmentNo = '"+HR_DepartmentNo+"' " +
						 " and HR_CompanySysNo = '"+request.getAttribute("compid")+"' ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				errors.add("HR_DepartmentNo", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?
									"":request.getAttribute("ERR_MSG")+"<br>")+"部門代碼已重複，請重新填寫!!");
			}
			
			rs.close();
			stmt.close();
			
		}catch (SQLException e) {
			System.out.println("EHF010108M0F.addData_validate()" + e);
		}
		
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


	public String getHR_UpDepartmentSysNo() {
		return HR_UpDepartmentSysNo;
	}


	public void setHR_UpDepartmentSysNo(String hRUpDepartmentSysNo) {
		HR_UpDepartmentSysNo = hRUpDepartmentSysNo;
	}


	public String getHR_DepartmentLevel() {
		return HR_DepartmentLevel;
	}


	public void setHR_DepartmentLevel(String hRDepartmentLevel) {
		HR_DepartmentLevel = hRDepartmentLevel;
	}


	public String getEHF010108T0_01() {
		return EHF010108T0_01;
	}


	public void setEHF010108T0_01(String eHF010108T0_01) {
		EHF010108T0_01 = eHF010108T0_01;
	}


	public String getEHF010108T0_02() {
		return EHF010108T0_02;
	}


	public void setEHF010108T0_02(String eHF010108T0_02) {
		EHF010108T0_02 = eHF010108T0_02;
	}


	public String getEHF010108T0_03() {
		return EHF010108T0_03;
	}


	public void setEHF010108T0_03(String eHF010108T0_03) {
		EHF010108T0_03 = eHF010108T0_03;
	}


	public String getEHF010108T0_04() {
		return EHF010108T0_04;
	}


	public void setEHF010108T0_04(String eHF010108T0_04) {
		EHF010108T0_04 = eHF010108T0_04;
	}


	public String getHR_CompanySysNo() {
		return HR_CompanySysNo;
	}


	public void setHR_CompanySysNo(String hRCompanySysNo) {
		HR_CompanySysNo = hRCompanySysNo;
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


	public String getDATE_CREATE() {
		return DATE_CREATE;
	}


	public void setDATE_CREATE(String dATECREATE) {
		DATE_CREATE = dATECREATE;
	}


	public String getHR_LastUpdateDate() {
		return HR_LastUpdateDate;
	}


	public void setHR_LastUpdateDate(String hRLastUpdateDate) {
		HR_LastUpdateDate = hRLastUpdateDate;
	}


	public List getEHF010108T0_LIST() {
		return EHF010108T0_LIST;
	}


	public void setEHF010108T0_LIST(List eHF010108T0LIST) {
		EHF010108T0_LIST = eHF010108T0LIST;
	}


	public String getHR_UpDepartmentName() {
		return HR_UpDepartmentName;
	}


	public void setHR_UpDepartmentName(String hRUpDepartmentName) {
		HR_UpDepartmentName = hRUpDepartmentName;
	}

}
