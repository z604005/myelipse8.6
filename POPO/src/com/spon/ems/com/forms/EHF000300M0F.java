package com.spon.ems.com.forms;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.com.models.EHF000300;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.util.BA_Vaildate;

public class EHF000300M0F extends ActionForm implements ValidateForm {

	private String EHF000300T0_01;
	private String EHF000300T0_02;
	private String EHF000300T0_03;
	private String EHF000300T0_04;
	private boolean EHF000300T0_05;
	private String EHF000300T0_06;
	private String HR_CompanySysNo;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String HR_LastUpdateDate;
	
	private List EHF000300T0_LIST = new ArrayList();
	
	private String EHF000300T0_05_TXT;

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

	private void delData_validate(ActionErrors errors,	HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		boolean query ;
		
		//EHF010106T6中未使用
		EHF000300 ehf000300 = new EHF000300();
		
		query = ehf000300.selectEHF010100T6(EHF000300T0_02, EHF000300T0_03, HR_CompanySysNo);
		
		if(query){
			errors.add("EHF000300T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此職務使用中，請勿刪除!!");
		}
		
		ehf000300.close();
		
	}

	private void saveData_validate(ActionErrors errors,	HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF000300T0_02, "EHF000300T0_02", "不可空白");
		ve.isEmpty(errors, EHF000300T0_03, "EHF000300T0_03", "不可空白");
		
		//檢核字數
		if((!"".equals(getEHF000300T0_06())|| getEHF000300T0_06()==null) && !GenericValidator.maxLength(getEHF000300T0_06(), 50)){//
			errors.add("EHF000300T0_06", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註字數不得超過50個字!!");
		}
		
		if((!"".equals(getEHF000300T0_03())|| getEHF000300T0_03()==null) && !GenericValidator.maxLength(getEHF000300T0_03(), 10)){//
			errors.add("EHF000300T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務名稱字數不得超過10個字!!");
		}
				
		if(EHF000300T0_02.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("EHF000300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務代碼請輸入英文加數字，且勿超過10碼!!");
		}
		
		
		//修改時，檢查是否有員工主檔正在使用，如有，則不能停用
		boolean query ;
		
		//EHF010106T6中未使用
		EHF000300 ehf000300 = new EHF000300();
		
		query = ehf000300.selectEHF010100T6(EHF000300T0_02, EHF000300T0_03, HR_CompanySysNo);
		
		if(query){
			errors.add("EHF000300T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此職務使用中，請勿刪除!!");
		}
		
		ehf000300.close();
		
		
	}

	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF000300T0_02, "EHF000300T0_02", "不可空白");
		ve.isEmpty(errors, EHF000300T0_03, "EHF000300T0_03", "不可空白");
		
		//檢核字數
		if((!"".equals(getEHF000300T0_06())|| getEHF000300T0_06()==null) && !GenericValidator.maxLength(getEHF000300T0_06(), 50)){//
			errors.add("EHF000300T0_06", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註欄位字數不得超過50個字!!");
		}
		
		if((!"".equals(getEHF000300T0_03())|| getEHF000300T0_03()==null) && !GenericValidator.maxLength(getEHF000300T0_03(), 10)){//
			errors.add("EHF000300T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務名稱欄位字數不得超過10個字!!");
		}
				
		if(EHF000300T0_02.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("EHF000300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"請輸入英文加數字，且勿超過10碼!!");
		}
		
	}

	public String getEHF000300T0_01() {
		return EHF000300T0_01;
	}

	public void setEHF000300T0_01(String eHF000300T0_01) {
		EHF000300T0_01 = eHF000300T0_01;
	}

	public String getEHF000300T0_02() {
		return EHF000300T0_02;
	}

	public void setEHF000300T0_02(String eHF000300T0_02) {
		EHF000300T0_02 = eHF000300T0_02;
	}

	public String getEHF000300T0_03() {
		return EHF000300T0_03;
	}

	public void setEHF000300T0_03(String eHF000300T0_03) {
		EHF000300T0_03 = eHF000300T0_03;
	}

	public String getEHF000300T0_04() {
		return EHF000300T0_04;
	}

	public void setEHF000300T0_04(String eHF000300T0_04) {
		EHF000300T0_04 = eHF000300T0_04;
	}

	public boolean isEHF000300T0_05() {
		return EHF000300T0_05;
	}

	public void setEHF000300T0_05(boolean eHF000300T0_05) {
		EHF000300T0_05 = eHF000300T0_05;
	}

	public String getEHF000300T0_06() {
		return EHF000300T0_06;
	}

	public void setEHF000300T0_06(String eHF000300T0_06) {
		EHF000300T0_06 = eHF000300T0_06;
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

	public List getEHF000300T0_LIST() {
		return EHF000300T0_LIST;
	}

	public void setEHF000300T0_LIST(List eHF000300T0LIST) {
		EHF000300T0_LIST = eHF000300T0LIST;
	}

	public String getEHF000300T0_05_TXT() {
		return EHF000300T0_05_TXT;
	}

	public void setEHF000300T0_05_TXT(String eHF000300T0_05TXT) {
		EHF000300T0_05_TXT = eHF000300T0_05TXT;
	}

}
