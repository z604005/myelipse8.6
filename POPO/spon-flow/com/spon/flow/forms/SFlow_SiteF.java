package com.spon.flow.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.flow.models.SFLOW_SITE;
import com.spon.utils.util.BA_Vaildate;

/**
 *@author maybe
 *@version 1.0
 *@created 2016/12/21 下午4:17:33
 */
public class SFlow_SiteF extends ActionForm implements ValidateForm {
	
	private String SFLOW_SITE_HD_01;//表單編號
	private String SFLOW_SITE_HD_02;//空表單編號
	private String SFLOW_SITE_HD_03;//申請人
	
	private String SFLOW_SITE_T0_01;
	private int SFLOW_SITE_T0_02;
	private String SFLOW_SITE_T0_03;
	private String SFLOW_SITE_T0_04;
	private String SFLOW_SITE_T0_05;
	private String SFLOW_SITE_T0_06;
	private String SFLOW_SITE_T0_07;
	private String SFLOW_SITE_T0_08;
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private List SFLOW_SITE_LIST = new ArrayList();
	private List SFLOW_SITE_T0_DETAIL = new ArrayList();

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
				
				ve.isEmpty(l_actionErrors, SFLOW_SITE_HD_01, "SFLOW_SITE_HD_01","不可空白");
				ve.isEmpty(l_actionErrors, SFLOW_SITE_HD_02, "SFLOW_SITE_HD_02","不可空白");
				
				if((!"".equals(getSFLOW_SITE_HD_01()) && getSFLOW_SITE_HD_01()!=null) && !GenericValidator.maxLength(getSFLOW_SITE_HD_01(), 30)){//
					l_actionErrors.add("SFLOW_SITE_HD_01", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"FLOW編號字數不得超過30個字!!");
				}
				if((!"".equals(getSFLOW_SITE_HD_02()) && getSFLOW_SITE_HD_02()!=null) && !GenericValidator.maxLength(getSFLOW_SITE_HD_02(), 50)){//
					l_actionErrors.add("SFLOW_SITE_HD_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"FLOW名稱字數不得超過50個字!!");
				}
				
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				ve.isNumEmpty(l_actionErrors, SFLOW_SITE_T0_02+"", "SFLOW_SITE_T0_02","請輸入數值",true);
				
				if (SFLOW_SITE_T0_02 != 0){
					ve.isLength(l_actionErrors, SFLOW_SITE_T0_02+"", "SFLOW_SITE_T0_02", 11, "長度不得超過11位");
				}
				
				ve.isEmpty(l_actionErrors, SFLOW_SITE_T0_03, "SFLOW_SITE_T0_03","不可空白");
				ve.isEmpty(l_actionErrors, SFLOW_SITE_T0_04, "SFLOW_SITE_T0_04","不可空白");
				ve.isEmpty(l_actionErrors, SFLOW_SITE_T0_05, "SFLOW_SITE_T0_05","不可空白");
				ve.isEmpty(l_actionErrors, SFLOW_SITE_T0_07, "SFLOW_SITE_T0_07","不可空白");
				ve.isEmpty(l_actionErrors, SFLOW_SITE_T0_08, "SFLOW_SITE_T0_08","不可空白");
				
				if((!"".equals(getSFLOW_SITE_T0_03()) && getSFLOW_SITE_T0_03()!=null) && !GenericValidator.maxLength(getSFLOW_SITE_T0_03(), 30)){//
					l_actionErrors.add("SFLOW_SITE_T0_03", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"站別名稱字數不得超過30個字!!");
				}
				
				if((!"".equals(getSFLOW_SITE_T0_04()) && getSFLOW_SITE_T0_04()!=null) && !GenericValidator.maxLength(getSFLOW_SITE_T0_04(), 4)){//
					l_actionErrors.add("SFLOW_SITE_T0_04", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"FLOW狀態號碼字數不得超過4個字!!");
				}
				
				if((!"".equals(getSFLOW_SITE_T0_05()) && getSFLOW_SITE_T0_05()!=null) && !GenericValidator.maxLength(getSFLOW_SITE_T0_05(), 30)){//
					l_actionErrors.add("SFLOW_SITE_T0_05", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"FLOW狀態名稱字數不得超過30個字!!");
				}
				
				if((!"".equals(getSFLOW_SITE_T0_08()) && getSFLOW_SITE_T0_08()!=null) && !GenericValidator.maxLength(getSFLOW_SITE_T0_08(), 40)){//
					l_actionErrors.add("SFLOW_SITE_T0_08", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"處理人員的指定Key值字數不得超過40個字!!");
				}
				
				addDetailData_validate(l_actionErrors, request, conn);
			}
			
			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				ve.isEmpty(l_actionErrors, SFLOW_SITE_HD_01, "SFLOW_SITE_HD_01","不可空白");
				ve.isEmpty(l_actionErrors, SFLOW_SITE_HD_02, "SFLOW_SITE_HD_02","不可空白");
				
				if((!"".equals(getSFLOW_SITE_HD_01()) && getSFLOW_SITE_HD_01()!=null) && !GenericValidator.maxLength(getSFLOW_SITE_HD_01(), 30)){//
					l_actionErrors.add("SFLOW_SITE_HD_01", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"FLOW編號字數不得超過30個字!!");
				}
				if((!"".equals(getSFLOW_SITE_HD_02()) && getSFLOW_SITE_HD_02()!=null) && !GenericValidator.maxLength(getSFLOW_SITE_HD_02(), 50)){//
					l_actionErrors.add("SFLOW_SITE_HD_02", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"FLOW名稱字數不得超過50個字!!");
				}
				
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
		
		try{
			Statement stmt = conn.createStatement();
			String sql = 
				 	" SELECT SFLOW_SITE_T0_01, SFLOW_SITE_T0_02 " +
					" FROM SFLOW_SITE_T0 " +
					" WHERE SFLOW_SITE_T0_01 = '"+SFLOW_SITE_T0_01+"'" +
					" AND SFLOW_SITE_T0_02 = '"+SFLOW_SITE_T0_01+"'" +
					" AND SFLOW_SITE_T0_06 = '"+(String)request.getAttribute("compid")+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("SFLOW_SITE_T0_01", new ActionMessage(""));
				errors.add("SFLOW_SITE_T0_02", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"簽核站不可重複，請重填!!");
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("SFlow_SiteF.addDetailData_validate()" + e);
		}
		
		if(!errors.isEmpty()){
			
			try{
				SFLOW_SITE sflow = new SFLOW_SITE();
			
				//建立查詢資料Map
				Map queryCondMap = new HashMap();
				queryCondMap.put("SFLOW_SITE_HD_01", SFLOW_SITE_HD_01);  // 類別代碼
			
				//取得List
				setSFLOW_SITE_T0_DETAIL(sflow.querySFLOW_SITE_T0_DETAILList(queryCondMap));
			
				//關閉SFLOW_SITE元件
				sflow.close();
			
			}catch (Exception e) {
				System.out.println("SM020101M0F.addDetailData_validate()" + e);
			}
		
		}
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		try{
			Statement stmt = conn.createStatement();
			String sql = 
				 	" SELECT SFLOW_SITE_HD_01 " +
					" FROM SFLOW_SITE_HD " +
					" WHERE SFLOW_SITE_HD_01 = '"+SFLOW_SITE_HD_01+"'" +
					" AND SFLOW_SITE_HD_03 = '"+(String)request.getAttribute("compid")+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("SFLOW_SITE_HD_01", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"FLOW編號不可重複，請重填!!");
			}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("SFlow_SiteF.addData_validate()" + e);
		}
		
	}

	public String getSFLOW_SITE_HD_01() {
		return SFLOW_SITE_HD_01;
	}

	public void setSFLOW_SITE_HD_01(String sFLOWSITEHD_01) {
		SFLOW_SITE_HD_01 = sFLOWSITEHD_01;
	}

	public String getSFLOW_SITE_HD_02() {
		return SFLOW_SITE_HD_02;
	}

	public void setSFLOW_SITE_HD_02(String sFLOWSITEHD_02) {
		SFLOW_SITE_HD_02 = sFLOWSITEHD_02;
	}

	public String getSFLOW_SITE_HD_03() {
		return SFLOW_SITE_HD_03;
	}

	public void setSFLOW_SITE_HD_03(String sFLOWSITEHD_03) {
		SFLOW_SITE_HD_03 = sFLOWSITEHD_03;
	}

	public String getSFLOW_SITE_T0_01() {
		return SFLOW_SITE_T0_01;
	}

	public void setSFLOW_SITE_T0_01(String sFLOWSITET0_01) {
		SFLOW_SITE_T0_01 = sFLOWSITET0_01;
	}

	public int getSFLOW_SITE_T0_02() {
		return SFLOW_SITE_T0_02;
	}

	public void setSFLOW_SITE_T0_02(int sFLOWSITET0_02) {
		SFLOW_SITE_T0_02 = sFLOWSITET0_02;
	}

	public String getSFLOW_SITE_T0_03() {
		return SFLOW_SITE_T0_03;
	}

	public void setSFLOW_SITE_T0_03(String sFLOWSITET0_03) {
		SFLOW_SITE_T0_03 = sFLOWSITET0_03;
	}

	public String getSFLOW_SITE_T0_04() {
		return SFLOW_SITE_T0_04;
	}

	public void setSFLOW_SITE_T0_04(String sFLOWSITET0_04) {
		SFLOW_SITE_T0_04 = sFLOWSITET0_04;
	}

	public String getSFLOW_SITE_T0_05() {
		return SFLOW_SITE_T0_05;
	}

	public void setSFLOW_SITE_T0_05(String sFLOWSITET0_05) {
		SFLOW_SITE_T0_05 = sFLOWSITET0_05;
	}

	public String getSFLOW_SITE_T0_06() {
		return SFLOW_SITE_T0_06;
	}

	public void setSFLOW_SITE_T0_06(String sFLOWSITET0_06) {
		SFLOW_SITE_T0_06 = sFLOWSITET0_06;
	}

	public String getSFLOW_SITE_T0_07() {
		return SFLOW_SITE_T0_07;
	}

	public void setSFLOW_SITE_T0_07(String sFLOWSITET0_07) {
		SFLOW_SITE_T0_07 = sFLOWSITET0_07;
	}

	public String getSFLOW_SITE_T0_08() {
		return SFLOW_SITE_T0_08;
	}

	public void setSFLOW_SITE_T0_08(String sFLOWSITET0_08) {
		SFLOW_SITE_T0_08 = sFLOWSITET0_08;
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

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}

	public List getSFLOW_SITE_LIST() {
		return SFLOW_SITE_LIST;
	}

	public void setSFLOW_SITE_LIST(List sFLOWSITELIST) {
		SFLOW_SITE_LIST = sFLOWSITELIST;
	}

	public void setSFLOW_SITE_T0_DETAIL(List sFLOW_SITE_T0_DETAIL) {
		SFLOW_SITE_T0_DETAIL = sFLOW_SITE_T0_DETAIL;
	}

	public List getSFLOW_SITE_T0_DETAIL() {
		return SFLOW_SITE_T0_DETAIL;
	}

}
