package com.spon.ems.com.forms;

import java.sql.Connection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.utils.util.BA_Vaildate;

public class EHF000100M0F extends ActionForm implements ValidateForm {
	
	private String EHF000100T0_01;
	private String EHF000100T0_02;
	private String EHF000100T0_03;
	private String EHF000100T0_04;
	private String EHF000100T0_05;
	private String EHF000100T0_06;
	private String EHF000100T0_07;
	private String EHF000100T0_08;
	private String EHF000100T0_09;
	private String EHF000100T0_10;
	private String EHF000100T0_11;
	private String EHF000100T0_12;
	private String EHF000100T0_13;
	private String EHF000100T0_14;
	private String EHF000100T0_15;
	private String EHF000100T0_16;
	private String EHF000100T0_17;
	private String EHF000100T0_18;
	private String EHF000100T0_19;
	private String EHF000100T0_20;
	
	private String HR_CompanySysNo;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String HR_LastUpdateDate;
	
	private String changeFormType;
	private String EHF000100T0_13_TAC;
	private String EHF000100T0_13_PN;
	private String EHF000100T0_14_TAC;
	private String EHF000100T0_14_PN;
	private String EHF000100T0_15_TAC;
	private String EHF000100T0_15_PN;

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
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF000100T0_01, "EHF000100T0_01", "不可空白");	//公司名稱
		ve.isNumEmpty(errors, EHF000100T0_02, "EHF000100T0_02", "不可空白", false);	//統一編號
		ve.isEmpty(errors, EHF000100T0_03, "EHF000100T0_03", "不可空白");	//公司名稱(中)
		ve.isEmpty(errors, EHF000100T0_05, "EHF000100T0_05", "不可空白");	//公司簡稱
		ve.isEmpty(errors, EHF000100T0_06, "EHF000100T0_06", "不可空白");	//成立日期
		ve.isEmpty(errors, EHF000100T0_07, "EHF000100T0_07", "不可空白");	//負責人
		ve.isEmpty(errors, EHF000100T0_09, "EHF000100T0_09", "不可空白");	//登記地址(中)
		ve.isEmpty(errors, EHF000100T0_13_TAC, "EHF000100T0_13_TAC", "電話區碼不可空白");	//電話(代表號)
		//ve.isEmpty(errors, EHF000100T0_14_TAC, "EHF000100T0_14_TAC", "電話(二)區碼不可空白");	//電話(二)(代表號)
		//ve.isEmpty(errors, EHF000100T0_15_TAC, "EHF000100T0_15_TAC", "傳真區碼不可空白");	
		//ve.isEmpty(errors, EHF000100T0_13_PN, "EHF000100T0_13_PN", "不可空白");
		
		
		
		if((EHF000100T0_13_PN==null?"":EHF000100T0_13_PN).equals("")){
			errors.add("EHF000100T0_13_PN", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"電話號碼不正確!!");
		}
		
		//if((EHF000100T0_14_PN==null?"":EHF000100T0_14_PN).equals("")){
		//	errors.add("EHF000100T0_14_PN", new ActionMessage(""));
		//	request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"電話(二)號碼不正確!!");
		//}
		
		//if((EHF000100T0_15_PN==null?"":EHF000100T0_15_PN).equals("")){
		//	errors.add("EHF000100T0_15_PN", new ActionMessage(""));
		//	request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"傳真號碼不正確!!");
		//}

		
		if((EHF000100T0_02==null?"":EHF000100T0_02).equals("")){
			ve.isTWCompanyId(errors, EHF000100T0_02, "EHF000100T0_02", "請輸入正確的統一編號");
		}
		
		if((EHF000100T0_16==null?"":EHF000100T0_16).equals("")){
			ve.isEmail(errors, EHF000100T0_16, "EHF000100T0_16", "MAIL 格式不符");
		}

		Pattern pattern = Pattern.compile("^[A-Za-z0-9]{1,10}$");
		Matcher matcher = pattern.matcher(getEHF000100T0_01());
		if(matcher.find()) {
			//不做動作  
		} else {
			errors.add("EHF000100T0_01", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"公司代碼請輸入英文加數字，且勿超過10碼!!");
		}
		
		//檢核字數
		if((!"".equals(getEHF000100T0_01())|| getEHF000100T0_01()==null) && !GenericValidator.maxLength(getEHF000100T0_01(), 10)){//
			errors.add("EHF000100T0_01", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"公司代碼字數不得超過10個字!!");
		}
		
		if((!"".equals(getEHF000100T0_03())|| getEHF000100T0_03()==null) && !GenericValidator.maxLength(getEHF000100T0_03(), 20)){//
			errors.add("EHF000100T0_03", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"公司名稱(中)字數不得超過20個字!!");
		}
		
		if((!"".equals(getEHF000100T0_04())|| getEHF000100T0_04()==null) && !GenericValidator.maxLength(getEHF000100T0_04(), 20)){//
			errors.add("EHF000100T0_04", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"公司名稱(英)字數不得超過20個字!!");
		}
		
		if((!"".equals(getEHF000100T0_05())|| getEHF000100T0_05()==null) && !GenericValidator.maxLength(getEHF000100T0_05(), 50)){//
			errors.add("EHF000100T0_05", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"公司簡稱字數不得超過50個字!!");
		}
		
		if((!"".equals(getEHF000100T0_07())|| getEHF000100T0_07()==null) && !GenericValidator.maxLength(getEHF000100T0_07(), 10)){//
			errors.add("EHF000100T0_07", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"負責人字數不得超過10個字!!");
		}
		
		if((!"".equals(getEHF000100T0_08())|| getEHF000100T0_08()==null) && !GenericValidator.maxLength(getEHF000100T0_08(), 10)){//
			errors.add("EHF000100T0_08", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"聯絡人字數不得超過10個字!!");
		}
		
		if((!"".equals(getEHF000100T0_09())|| getEHF000100T0_09()==null) && !GenericValidator.maxLength(getEHF000100T0_09(), 50)){//
			errors.add("EHF000100T0_09", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"登記地址(中)字數不得超過50個字!!");
		}
		
		if((!"".equals(getEHF000100T0_10())|| getEHF000100T0_10()==null) && !GenericValidator.maxLength(getEHF000100T0_10(), 50)){//
			errors.add("EHF000100T0_10", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"登記地址(英)字數不得超過50個字!!");
		}
		
		if((!"".equals(getEHF000100T0_11())|| getEHF000100T0_11()==null) && !GenericValidator.maxLength(getEHF000100T0_11(), 50)){//
			errors.add("EHF000100T0_11", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"營業地址(中)字數不得超過50個字!!");
		}
		
		if((!"".equals(getEHF000100T0_12())|| getEHF000100T0_12()==null) && !GenericValidator.maxLength(getEHF000100T0_12(), 50)){//
			errors.add("EHF000100T0_12", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"營業地址(英)字數不得超過50個字!!");
		}
		
		if((!"".equals(getEHF000100T0_16())|| getEHF000100T0_16()==null) && !GenericValidator.maxLength(getEHF000100T0_16(), 50)){//
			errors.add("EHF000100T0_16", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"電子郵件字數不得超過50個字!!");
		}
		
		if((!"".equals(getEHF000100T0_17())|| getEHF000100T0_17()==null) && !GenericValidator.maxLength(getEHF000100T0_17(), 100)){//
			errors.add("EHF000100T0_17", new ActionMessage(""));
			request.setAttribute("ErrMSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"公司簡介字數不得超過100個字!!");
		}
		
	}

	private void addDetailData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}

	public void setHR_CompanySysNo(String hR_CompanySysNo) {
		HR_CompanySysNo = hR_CompanySysNo;
	}

	public String getHR_CompanySysNo() {
		return HR_CompanySysNo;
	}

	public String getEHF000100T0_01() {
		return EHF000100T0_01;
	}

	public void setEHF000100T0_01(String eHF000100T0_01) {
		EHF000100T0_01 = eHF000100T0_01;
	}

	public String getEHF000100T0_02() {
		return EHF000100T0_02;
	}

	public void setEHF000100T0_02(String eHF000100T0_02) {
		EHF000100T0_02 = eHF000100T0_02;
	}

	public String getEHF000100T0_03() {
		return EHF000100T0_03;
	}

	public void setEHF000100T0_03(String eHF000100T0_03) {
		EHF000100T0_03 = eHF000100T0_03;
	}

	public String getEHF000100T0_04() {
		return EHF000100T0_04;
	}

	public void setEHF000100T0_04(String eHF000100T0_04) {
		EHF000100T0_04 = eHF000100T0_04;
	}

	public String getEHF000100T0_05() {
		return EHF000100T0_05;
	}

	public void setEHF000100T0_05(String eHF000100T0_05) {
		EHF000100T0_05 = eHF000100T0_05;
	}

	public String getEHF000100T0_06() {
		return EHF000100T0_06;
	}

	public void setEHF000100T0_06(String eHF000100T0_06) {
		EHF000100T0_06 = eHF000100T0_06;
	}

	public String getEHF000100T0_07() {
		return EHF000100T0_07;
	}

	public void setEHF000100T0_07(String eHF000100T0_07) {
		EHF000100T0_07 = eHF000100T0_07;
	}

	public String getEHF000100T0_08() {
		return EHF000100T0_08;
	}

	public void setEHF000100T0_08(String eHF000100T0_08) {
		EHF000100T0_08 = eHF000100T0_08;
	}

	public String getEHF000100T0_09() {
		return EHF000100T0_09;
	}

	public void setEHF000100T0_09(String eHF000100T0_09) {
		EHF000100T0_09 = eHF000100T0_09;
	}

	public String getEHF000100T0_10() {
		return EHF000100T0_10;
	}

	public void setEHF000100T0_10(String eHF000100T0_10) {
		EHF000100T0_10 = eHF000100T0_10;
	}

	public String getEHF000100T0_11() {
		return EHF000100T0_11;
	}

	public void setEHF000100T0_11(String eHF000100T0_11) {
		EHF000100T0_11 = eHF000100T0_11;
	}

	public String getEHF000100T0_12() {
		return EHF000100T0_12;
	}

	public void setEHF000100T0_12(String eHF000100T0_12) {
		EHF000100T0_12 = eHF000100T0_12;
	}

	public String getEHF000100T0_13() {
		return EHF000100T0_13;
	}

	public void setEHF000100T0_13(String eHF000100T0_13) {
		EHF000100T0_13 = eHF000100T0_13;
	}

	public String getEHF000100T0_14() {
		return EHF000100T0_14;
	}

	public void setEHF000100T0_14(String eHF000100T0_14) {
		EHF000100T0_14 = eHF000100T0_14;
	}

	public String getEHF000100T0_15() {
		return EHF000100T0_15;
	}

	public void setEHF000100T0_15(String eHF000100T0_15) {
		EHF000100T0_15 = eHF000100T0_15;
	}

	public String getEHF000100T0_16() {
		return EHF000100T0_16;
	}

	public void setEHF000100T0_16(String eHF000100T0_16) {
		EHF000100T0_16 = eHF000100T0_16;
	}

	public String getEHF000100T0_17() {
		return EHF000100T0_17;
	}

	public void setEHF000100T0_17(String eHF000100T0_17) {
		EHF000100T0_17 = eHF000100T0_17;
	}

	public String getEHF000100T0_18() {
		return EHF000100T0_18;
	}

	public void setEHF000100T0_18(String eHF000100T0_18) {
		EHF000100T0_18 = eHF000100T0_18;
	}

	public String getEHF000100T0_19() {
		return EHF000100T0_19;
	}

	public void setEHF000100T0_19(String eHF000100T0_19) {
		EHF000100T0_19 = eHF000100T0_19;
	}

	public String getEHF000100T0_20() {
		return EHF000100T0_20;
	}

	public void setEHF000100T0_20(String eHF000100T0_20) {
		EHF000100T0_20 = eHF000100T0_20;
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

	public String getChangeFormType() {
		return changeFormType;
	}

	public void setChangeFormType(String changeFormType) {
		this.changeFormType = changeFormType;
	}

	public String getEHF000100T0_13_TAC() {
		return EHF000100T0_13_TAC;
	}

	public void setEHF000100T0_13_TAC(String eHF000100T0_13TAC) {
		EHF000100T0_13_TAC = eHF000100T0_13TAC;
	}

	public String getEHF000100T0_13_PN() {
		return EHF000100T0_13_PN;
	}

	public void setEHF000100T0_13_PN(String eHF000100T0_13PN) {
		EHF000100T0_13_PN = eHF000100T0_13PN;
	}

	public String getEHF000100T0_14_TAC() {
		return EHF000100T0_14_TAC;
	}

	public void setEHF000100T0_14_TAC(String eHF000100T0_14TAC) {
		EHF000100T0_14_TAC = eHF000100T0_14TAC;
	}

	public String getEHF000100T0_14_PN() {
		return EHF000100T0_14_PN;
	}

	public void setEHF000100T0_14_PN(String eHF000100T0_14PN) {
		EHF000100T0_14_PN = eHF000100T0_14PN;
	}

	public String getEHF000100T0_15_TAC() {
		return EHF000100T0_15_TAC;
	}

	public void setEHF000100T0_15_TAC(String eHF000100T0_15TAC) {
		EHF000100T0_15_TAC = eHF000100T0_15TAC;
	}

	public String getEHF000100T0_15_PN() {
		return EHF000100T0_15_PN;
	}

	public void setEHF000100T0_15_PN(String eHF000100T0_15PN) {
		EHF000100T0_15_PN = eHF000100T0_15PN;
	}

}
