package com.spon.ems.hr.forms;

import java.sql.Connection;
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
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.util.BA_Vaildate;

public class EHF010109M0F extends ActionForm implements ValidateForm {
	
	private String HR_JobNameSysNo;
	private String HR_JobNameNo;
	private String HR_JobName;
	private String HR_JobNameLevel;
	private boolean EHF010109T0_01;
	private String EHF010109T0_02;
	private String HR_CompanySysNo;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String HR_LastUpdateDate;
	
	private List EHF010109T0_LIST = new ArrayList();
	
	private String EHF010109T0_01_TXT;

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
		EHF010109 ehf010109 = new EHF010109();
		
		query = ehf010109.selectEHF010106T6(HR_JobNameNo, HR_JobName, HR_CompanySysNo);
		
		if(query){
			errors.add("HR_JobName", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此職務使用中，請勿刪除!!");
		}
		
		ehf010109.close();
		
	}

	private void saveData_validate(ActionErrors errors,	HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, HR_JobNameNo, "HR_JobNameNo", "不可空白");
		ve.isEmpty(errors, HR_JobName, "HR_JobName", "不可空白");
		
		//檢核字數
		if((!"".equals(getEHF010109T0_02())|| getEHF010109T0_02()==null) && !GenericValidator.maxLength(getEHF010109T0_02(), 50)){//
			errors.add("EHF010109T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註字數不得超過50個字!!");
		}
		
		if((!"".equals(getHR_JobName())|| getHR_JobName()==null) && !GenericValidator.maxLength(getHR_JobName(), 10)){//
			errors.add("HR_JobName", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務名稱字數不得超過10個字!!");
		}
				
		if(HR_JobNameNo.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("HR_JobNameNo", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務代碼請輸入英文加數字，且勿超過10碼!!");
		}
		
		
		//修改時，檢查是否有員工主檔正在使用，如有，則不能停用
		boolean query ;
		
		//EHF010106T6中未使用
		EHF010109 ehf010109 = new EHF010109();
		
		query = ehf010109.selectEHF010106T6(HR_JobNameNo, HR_JobName, HR_CompanySysNo);
		
		if(query){
			errors.add("HR_JobName", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此職務使用中，請勿刪除!!");
		}
		
		ehf010109.close();
		
		
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
		
		ve.isEmpty(errors, HR_JobNameNo, "HR_JobNameNo", "不可空白");
		ve.isEmpty(errors, HR_JobName, "HR_JobName", "不可空白");
		
		//檢核字數
		if((!"".equals(getEHF010109T0_02())|| getEHF010109T0_02()==null) && !GenericValidator.maxLength(getEHF010109T0_02(), 50)){//
			errors.add("EHF010109T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註欄位字數不得超過50個字!!");
		}
		
		if((!"".equals(getHR_JobName())|| getHR_JobName()==null) && !GenericValidator.maxLength(getHR_JobName(), 10)){//
			errors.add("HR_JobName", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務名稱欄位字數不得超過10個字!!");
		}
				
		if(HR_JobNameNo.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("HR_JobNameNo", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"請輸入英文加數字，且勿超過10碼!!");
		}
		
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

	public String getHR_JobNameLevel() {
		return HR_JobNameLevel;
	}

	public void setHR_JobNameLevel(String hRJobNameLevel) {
		HR_JobNameLevel = hRJobNameLevel;
	}

	public boolean isEHF010109T0_01() {
		return EHF010109T0_01;
	}

	public void setEHF010109T0_01(boolean eHF010109T0_01) {
		EHF010109T0_01 = eHF010109T0_01;
	}

	public String getEHF010109T0_02() {
		return EHF010109T0_02;
	}

	public void setEHF010109T0_02(String eHF010109T0_02) {
		EHF010109T0_02 = eHF010109T0_02;
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

	public List getEHF010109T0_LIST() {
		return EHF010109T0_LIST;
	}

	public void setEHF010109T0_LIST(List eHF010109T0LIST) {
		EHF010109T0_LIST = eHF010109T0LIST;
	}

	public String getEHF010109T0_01_TXT() {
		return EHF010109T0_01_TXT;
	}

	public void setEHF010109T0_01_TXT(String eHF010109T0_01TXT) {
		EHF010109T0_01_TXT = eHF010109T0_01TXT;
	}
	
}
