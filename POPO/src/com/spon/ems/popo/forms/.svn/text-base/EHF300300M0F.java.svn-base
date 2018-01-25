package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


import com.spon.ems.abs.interfaces.ValidateForm;

import com.spon.ems.popo.models.EHF300000;
import com.spon.ems.popo.models.EHF300300;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;


/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午9:46:59
 */
public class EHF300300M0F extends ActionForm implements ValidateForm {
	private  String  EHF300300T0_02_select;
	private  String  EHF300300T0_01;
	private  String  EHF300300T0_02;
	private  int  EHF300300T0_03;
	private  String  EHF300300T0_04; //HR_companySysNo
//	private  String  EHF000700T0_05;
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;
	private  String  DATE_CREATE;
	private List EHF300300C = new ArrayList(); //M0查詢
//	private List EHF300300T0_LIST = new ArrayList();
	//private FormFile UPLOADFILE;
	//private String SIGN_COMMENT;
	//private String HR_CompanySysNo;
	private String EHF300300T1_01;
	private int EHF300300T1_02;
	private String EHF300300T1_03;
	
	private String EHF300300T0_02_TXT;

	private List listEHF300300T1_03 = new ArrayList(); //m1餐點明細collcation
	private List EHF300300_DETAIL = new ArrayList(); //M1明細
	//private List listEHF300300T1_03 = new ArrayList();
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		List flow_sign_log_list = new ArrayList();
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			EMS_CheckVacation checkva_tools = (EMS_CheckVacation) EMS_CheckVacation.getInstance();
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				ve.isEmpty(l_actionErrors, EHF300300T0_02_TXT, "EHF300300T0_02_TXT", "不可空白");
				ve.isEmpty(l_actionErrors, EHF300300T0_01, "EHF300300T0_01", "不可空白");
				ve.isNumEmpty(l_actionErrors, EHF300300T0_03 + "", "EHF300300T0_03","請輸入數值",false);

				addData_validate(l_actionErrors, request, conn);

			}
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				ve.isEmpty(l_actionErrors, EHF300300T1_03, "EHF300300T1_03", "不可空白");
				
				addDetailData_validate(l_actionErrors, request, conn);
			}
			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				ve.isEmpty(l_actionErrors, EHF300300T0_02_TXT, "EHF300300T0_02_TXT", "不可空白");
				ve.isNumEmpty(l_actionErrors, EHF300300T0_03 + "", "EHF300300T0_03","請輸入數值",false);
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
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		try{
			//代碼不可重複
			Statement stmt = conn.createStatement();
			String sql = "SELECT EHF300300T0_01 FROM EHF300300T0  WHERE EHF300300T0_01 = '"
					+ EHF300300T0_01 + "' and EHF300300T0_04 = '"+request.getAttribute("compid")+"'";
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("EHF300300T0_01", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"代碼不可重複!!");
			}
			
			//名稱不可重複
			sql = "SELECT EHF300300T0_02 FROM EHF300300T0  WHERE EHF300300T0_02 = '"
				+ EHF300300T0_02_TXT + "' and EHF300300T0_04 = '"+request.getAttribute("compid")+"'";
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("EHF300300T0_02_TXT", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"名稱不可重複!!");
		}
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("EHF300300M0F.addData_validate()" + e);
		}
		
//			try{
//			Statement stmt = conn.createStatement();
//			String sql = "SELECT EHF300300T0_02 FROM EHF300300T0  WHERE EHF300300T0_02 = '"
//					+ EHF300300T0_02_TXT + "' and EHF300300T0_04 = '"+request.getAttribute("compid")+"'";
//			ResultSet rs = stmt.executeQuery(sql);
//			if (rs.next()) {
//				errors.add("EHF300300T0_02_TXT", new ActionMessage(""));
//				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"名稱不可重複!!");
//		}
//			stmt.close();
//			rs.close();
//		}catch (SQLException e) {
//			System.out.println("EHF300300M0F.addData_validate()" + e);
//		}
	}
	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{
			//不可重複
			Statement stmt = conn.createStatement();
			String sql = "SELECT EHF300300T1_03 FROM EHF300300T1 WHERE EHF300300T1_03 = '"
					+ EHF300300T1_03 + "' " +
					" AND EHF300300T1_01= '" +EHF300300T0_01+ "' " ;
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				errors.add("EHF300300T1_03", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"餐點類別不可重複!!");
			}
			//System.out.println(sql);
			stmt.close();
			rs.close();
		}catch (SQLException e) {
			System.out.println("EHF300300M0F.addDetailData_validate()" + e);
		}
	
		
		if(!errors.isEmpty()){
		
			try{
				/*
				 * 取得之前作業的錯誤訊息參數
				 * 避免因為前面的方法處理中已發生檢核錯誤 -> 最後執行 return queryDatas() 時
				 * 因 queryDatas() 不知已發生檢核錯誤, 執行了查詢動作造成使用者填寫的資料消失
				 * 由於 EditAction 中此一功能的實現，導致頁面在檢核出errors時若已有明細資料，
				 * 則已有明細資料會顯示不出來，故需要以下程式來抓取明細資料
				 */
				EHF300300 EHF300300 = new EHF300300();
			
				//建立查詢資料Map
				Map queryCondMap = new HashMap();
				queryCondMap.put("EHF300300T0_01", EHF300300T0_01);  // 食物代碼
			
			setEHF300300_DETAIL(EHF300300.queryLexiconDList(queryCondMap));
			
				EHF300300.close();
			
			}catch (Exception e) {
				System.out.println("EHF300300M0F.addDetailData_validate()" + e);
			}
		}
	}

	public void setEHF300300T0_01(String eHF300300T0_01) {
		EHF300300T0_01 = eHF300300T0_01;
	}

	public String getEHF300300T0_01() {
		return EHF300300T0_01;
	}

	public void setEHF300300T0_03(int eHF300300T0_03) {
		EHF300300T0_03 = eHF300300T0_03;
	}

	public int getEHF300300T0_03() {
		return EHF300300T0_03;
	}

	public void setEHF300300T0_04(String eHF300300T0_04) {
		EHF300300T0_04 = eHF300300T0_04;
	}

	public String getEHF300300T0_04() {
		return EHF300300T0_04;
	}

	public void setUSER_CREATE(String uSER_CREATE) {
		USER_CREATE = uSER_CREATE;
	}

	public String getUSER_CREATE() {
		return USER_CREATE;
	}

	public void setUSER_UPDATE(String uSER_UPDATE) {
		USER_UPDATE = uSER_UPDATE;
	}

	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}

	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}

	public int getVERSION() {
		return VERSION;
	}

	public void setDATE_UPDATE(String dATE_UPDATE) {
		DATE_UPDATE = dATE_UPDATE;
	}

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	public void setDATE_CREATE(String dATE_CREATE) {
		DATE_CREATE = dATE_CREATE;
	}

	public String getDATE_CREATE() {
		return DATE_CREATE;
	}

	public List getEHF300300C() {
		return EHF300300C;
	}

	public void setEHF300300C(List eHF300300C) {
		EHF300300C = eHF300300C;
	}

	public String getEHF300300T0_02() {
		return EHF300300T0_02;
	}

	public void setEHF300300T0_02(String EHF300300T0_02) {
		EHF300300T0_02 = EHF300300T0_02;
	}

	public void setEHF300300T1_03(String eHF300300T1_03) {
		EHF300300T1_03 = eHF300300T1_03;
	}

	public String getEHF300300T1_03() {
		return EHF300300T1_03;
	}

	public void setEHF300300T1_02(int eHF300300T1_02) {
		EHF300300T1_02 = eHF300300T1_02;
	}

	public int getEHF300300T1_02() {
		return EHF300300T1_02;
	}

	public void setEHF300300T1_01(String eHF300300T1_01) {
		EHF300300T1_01 = eHF300300T1_01;
	}

	public String getEHF300300T1_01() {
		return EHF300300T1_01;
	}




	public void setEHF300300_DETAIL(List eHF300300_DETAIL) {
		EHF300300_DETAIL = eHF300300_DETAIL;
	}

	public List getEHF300300_DETAIL() {
		return EHF300300_DETAIL;
	}

	public void setListEHF300300T1_03(List listEHF300300T1_03) {
		this.listEHF300300T1_03 = listEHF300300T1_03;
	}

	public List getListEHF300300T1_03() {
		return listEHF300300T1_03;
	}

	public void setEHF300300T0_02_TXT(String eHF300300T0_02_TXT) {
		EHF300300T0_02_TXT = eHF300300T0_02_TXT;
	}

	public String getEHF300300T0_02_TXT() {
		return EHF300300T0_02_TXT;
	}

	public void setEHF300300T0_02_select(String eHF300300T0_02_select) {
		EHF300300T0_02_select = eHF300300T0_02_select;
	}

	public String getEHF300300T0_02_select() {
		return EHF300300T0_02_select;
	}
}

//	public void setEHF300300T0_LIST(List eHF300300T0_LIST) {
//		EHF300300T0_LIST = eHF300300T0_LIST;
//	}
//
//	public List getEHF300300T0_LIST() {
//		return EHF300300T0_LIST;
//	}


