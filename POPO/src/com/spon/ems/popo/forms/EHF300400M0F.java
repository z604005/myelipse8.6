package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.popo.models.EHF300400;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.util.BA_Vaildate;

public class EHF300400M0F extends ActionForm implements ValidateForm {

	private String EHF300400T0_01;
	private String EHF300400T0_02;
	private String EHF300400T0_03;
	private String EHF300400T0_04;
	private String EHF300400T0_05;
	private String EHF300400T0_06;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private List EHF300400T0_LIST = new ArrayList();
	
	private String EHF300400T0_02_TXT;
	private String EHF300400T0_05_TXT;
	
	private List listEHF300400T0_02 = new ArrayList();
	

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

	private void delData_validate(ActionErrors errors,	HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//EHF300400 ehf300400 = new EHF300400();
		
		//啟用中不可以刪除
		/*if(ehf300400.isRepeat(EHF300400T0_02, "", "1", comp_id)){
			errors.add("EHF300400T0_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"啟用中不可以刪除!!");
		}*/
		
		//ehf300400.close();
		
	}

	private void saveData_validate(ActionErrors errors,	HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF300400T0_02, "EHF300400T0_02", "不可空白");
		ve.isEmpty(errors, EHF300400T0_03, "EHF300400T0_03", "不可空白");
		ve.isEmpty(errors, EHF300400T0_04, "EHF300400T0_04", "不可空白");
		
		String comp_id = (String) request.getAttribute("compid");
		
		//檢核字數		
		if((!"".equals(getEHF300400T0_04())|| getEHF300400T0_04()==null) && !GenericValidator.maxLength(getEHF300400T0_04(), 10)){//
			errors.add("EHF300400T0_04", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"茶飲名稱字數不得超過10個字!!");
		}

		EHF300400 ehf300400 = new EHF300400();
		
		//不可重複
		if(ehf300400.isRepeat(EHF300400T0_02, EHF300400T0_03, "", EHF300400T0_01, comp_id)){
			errors.add("EHF300400T0_02", new ActionMessage(""));
			errors.add("EHF300400T0_03", new ActionMessage(""));
			errors.add("EHF300400T0_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"資料已重複，請重新填寫!!");
		}
		
		//已有資料啟用
		if(ehf300400.isRepeat(EHF300400T0_02, "", "1", EHF300400T0_01, comp_id)){
			errors.add("EHF300400T0_02", new ActionMessage(""));
			errors.add("EHF300400T0_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"該星期已有資料啟用，請重新填寫!!!!");
		}
		
		ehf300400.close();
				
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF300400T0_02, "EHF300400T0_02", "不可空白");//檢核星期不可空白
		ve.isEmpty(errors, EHF300400T0_03, "EHF300400T0_03", "不可空白");//檢核茶飲代號不可空白
		ve.isEmpty(errors, EHF300400T0_04, "EHF300400T0_04", "不可空白");//檢核茶飲名稱不可空白
		ve.isEmpty(errors, EHF300400T0_05, "EHF300400T0_05", "不可空白");//檢核啟用不可空白
	
		String comp_id = (String) request.getAttribute("compid");
		
		//檢核字數		
		if((!"".equals(getEHF300400T0_04())|| getEHF300400T0_04()==null) && !GenericValidator.maxLength(getEHF300400T0_04(), 10)){//
			errors.add("EHF300400T0_04", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"茶飲名稱字數不得超過10個字!!");
		}
		
		EHF300400 ehf300400 = new EHF300400();
		
		//不可重複
		if(ehf300400.isRepeat(EHF300400T0_02, EHF300400T0_03, "", "", comp_id)){
			errors.add("EHF300400T0_02", new ActionMessage(""));
			errors.add("EHF300400T0_03", new ActionMessage(""));
			errors.add("EHF300400T0_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"資料已重複，請重新填寫!!");
		}
		
		//已有資料啟用
		if(ehf300400.isRepeat(EHF300400T0_02, "", "1", "", comp_id)){
			errors.add("EHF300400T0_02", new ActionMessage(""));
			errors.add("EHF300400T0_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ERR_MSG")==null?"":request.getAttribute("ERR_MSG")+"<br>")+"該星期已有資料啟用，請重新填寫!!!!");
		}
		
		ehf300400.close();
		
	}

	public String getEHF300400T0_01() {
		return EHF300400T0_01;
	}

	public void setEHF300400T0_01(String eHF300400T0_01) {
		EHF300400T0_01 = eHF300400T0_01;
	}

	public String getEHF300400T0_02() {
		return EHF300400T0_02;
	}

	public void setEHF300400T0_02(String eHF300400T0_02) {
		EHF300400T0_02 = eHF300400T0_02;
	}

	public String getEHF300400T0_03() {
		return EHF300400T0_03;
	}

	public void setEHF300400T0_03(String eHF300400T0_03) {
		EHF300400T0_03 = eHF300400T0_03;
	}

	public String getEHF300400T0_04() {
		return EHF300400T0_04;
	}

	public void setEHF300400T0_04(String eHF300400T0_04) {
		EHF300400T0_04 = eHF300400T0_04;
	}

	public String getEHF300400T0_05() {
		return EHF300400T0_05;
	}

	public void setEHF300400T0_05(String eHF300400T0_05) {
		EHF300400T0_05 = eHF300400T0_05;
	}

	public String getEHF300400T0_06() {
		return EHF300400T0_06;
	}

	public void setEHF300400T0_06(String eHF300400T0_06) {
		EHF300400T0_06 = eHF300400T0_06;
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

	public List getEHF300400T0_LIST() {
		return EHF300400T0_LIST;
	}

	public void setEHF300400T0_LIST(List eHF300400T0LIST) {
		EHF300400T0_LIST = eHF300400T0LIST;
	}

	public String getEHF300400T0_02_TXT() {
		return EHF300400T0_02_TXT;
	}

	public void setEHF300400T0_02_TXT(String eHF300400T0_02TXT) {
		EHF300400T0_02_TXT = eHF300400T0_02TXT;
	}

	public String getEHF300400T0_05_TXT() {
		return EHF300400T0_05_TXT;
	}

	public void setEHF300400T0_05_TXT(String eHF300400T0_05TXT) {
		EHF300400T0_05_TXT = eHF300400T0_05TXT;
	}

	public List getListEHF300400T0_02() {
		return listEHF300400T0_02;
	}

	public void setListEHF300400T0_02(List listEHF300400T0_02) {
		this.listEHF300400T0_02 = listEHF300400T0_02;
	}
	
}
