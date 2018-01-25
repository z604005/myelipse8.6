package com.spon.ems.com.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.com.models.EHF000300;
import com.spon.ems.vacation.tools.EMS_CaculateVacationByCal;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_GetEmsFlowInf;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午9:46:59
 */
public class EHF000600M0F extends ActionForm implements ValidateForm {
	
	private  String  EHF000600T0_01;
	private  String  EHF000600T0_02;
	private  String  EHF000600T0_03;
	private  String  EHF000600T0_03_TXT;
	private  String  EHF000600T0_03_SHOW;
	private  String  EHF000600T0_04;
	private  String  EHF000600T0_04_TXT;
	private  String  EHF000600T0_04_SHOW;
	private  String  EHF000600T0_05;
	private  String  EHF000600T0_05_TXT;
	private  String EHF000600T0_05_SHOW;
	private  String  EHF000600T0_06;
	private  String  EHF000600T0_06_TXT;
	private  String EHF000600T0_06_SHOW;
	private  String  EHF000600T0_07;
	private  String  EHF000600T0_07_TXT;
	private  String EHF000600T0_07_SHOW;
	private  String  EHF000600T0_08;
	private  String  EHF000600T0_09;
	private  String  EHF000600T0_10;
	private  String  EHF000600T0_11;
	private  String  EHF000600T0_11_HH;
	private  String  EHF000600T0_11_MM;
	private  String  EHF000600T0_12;
	private  String  EHF000600T0_12_HH;
	private  String  EHF000600T0_12_MM;
	private  String  EHF000600T0_13;
	private  String  EHF000600T0_13_DAY;
	private  String  EHF000600T0_13_HOUR;
	private  String  EHF000600T0_14;
	private  String  EHF000600T0_14_DESC;
	private  String  EHF000600T0_15;
	private  String  EHF000600T0_16;
	private  String  EHF000600T0_16_DESC;
	private  String  EHF000600T0_17;
	private  String  EHF000600T0_18;
	
	private String HR_CompanySysNo;
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String DATE_CREATE;
	private  String  DATE_UPDATE;
	
	
	
	
	private List EHF000600C = new ArrayList();
	
	private FormFile UPLOADFILE;
	
	private String SIGN_COMMENT;

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
			
			/*//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				
				addDetailData_validate(l_actionErrors, request, conn);
			}*/

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
		
		ve.isEmpty(errors, EHF000600T0_02, "EHF000600T0_02", "不可空白");
		ve.isEmpty(errors, EHF000600T0_03, "EHF000600T0_03", "不可空白");
		
		//檢核字數
		if((!"".equals(getEHF000600T0_02())|| getEHF000600T0_02()==null) && !GenericValidator.maxLength(getEHF000600T0_02(), 20)){//
			errors.add("EHF000600T0_06", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"規則名稱不得多於二十字");
		}
		
		if((!"".equals(getEHF000600T0_03())|| getEHF000600T0_03()==null) && !GenericValidator.maxLength(getEHF000600T0_03(), 100)){//
			errors.add("EHF000600T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"規則內容簡述不得多於一百字");
		}
				
		/*if(EHF000600T0_02.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("EHF000600T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"請輸入英文加數字，且勿超過10碼!!");
		}*/
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF000600T0_02, "EHF000600T0_02", "不可空白");
		ve.isEmpty(errors, EHF000600T0_03, "EHF000600T0_03", "不可空白");
		
		//檢核字數
		if((!"".equals(getEHF000600T0_02())|| getEHF000600T0_02()==null) && !GenericValidator.maxLength(getEHF000600T0_02(), 20)){//
			errors.add("EHF000600T0_06", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"規則名稱不得多於二十字");
		}
		
		if((!"".equals(getEHF000600T0_03())|| getEHF000600T0_03()==null) && !GenericValidator.maxLength(getEHF000600T0_03(), 100)){//
			errors.add("EHF000600T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"規則內容簡述不得多於一百字");
		}
				
		/*if(EHF000600T0_02.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("EHF000600T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"請輸入英文加數字，且勿超過10碼!!");
		}*/
		
	}

	public String getEHF000600T0_01() {
		return EHF000600T0_01;
	}

	public void setEHF000600T0_01(String eHF000600T0_01) {
		EHF000600T0_01 = eHF000600T0_01;
	}

	public String getEHF000600T0_02() {
		return EHF000600T0_02;
	}

	public void setEHF000600T0_02(String eHF000600T0_02) {
		EHF000600T0_02 = eHF000600T0_02;
	}

	public String getEHF000600T0_03() {
		return EHF000600T0_03;
	}

	public void setEHF000600T0_03(String eHF000600T0_03) {
		EHF000600T0_03 = eHF000600T0_03;
	}

	public String getEHF000600T0_03_TXT() {
		return EHF000600T0_03_TXT;
	}

	public void setEHF000600T0_03_TXT(String eHF000600T0_03TXT) {
		EHF000600T0_03_TXT = eHF000600T0_03TXT;
	}

	public String getEHF000600T0_04() {
		return EHF000600T0_04;
	}

	public void setEHF000600T0_04(String eHF000600T0_04) {
		EHF000600T0_04 = eHF000600T0_04;
	}

	public String getEHF000600T0_04_TXT() {
		return EHF000600T0_04_TXT;
	}

	public void setEHF000600T0_04_TXT(String eHF000600T0_04TXT) {
		EHF000600T0_04_TXT = eHF000600T0_04TXT;
	}

	public String getEHF000600T0_05() {
		return EHF000600T0_05;
	}

	public void setEHF000600T0_05(String eHF000600T0_05) {
		EHF000600T0_05 = eHF000600T0_05;
	}

	public String getEHF000600T0_05_TXT() {
		return EHF000600T0_05_TXT;
	}

	public void setEHF000600T0_05_TXT(String eHF000600T0_05TXT) {
		EHF000600T0_05_TXT = eHF000600T0_05TXT;
	}

	public String getEHF000600T0_06() {
		return EHF000600T0_06;
	}

	public void setEHF000600T0_06(String eHF000600T0_06) {
		EHF000600T0_06 = eHF000600T0_06;
	}

	public String getEHF000600T0_06_TXT() {
		return EHF000600T0_06_TXT;
	}

	public void setEHF000600T0_06_TXT(String eHF000600T0_06TXT) {
		EHF000600T0_06_TXT = eHF000600T0_06TXT;
	}

	public String getEHF000600T0_07() {
		return EHF000600T0_07;
	}

	public void setEHF000600T0_07(String eHF000600T0_07) {
		EHF000600T0_07 = eHF000600T0_07;
	}

	public String getEHF000600T0_07_TXT() {
		return EHF000600T0_07_TXT;
	}

	public void setEHF000600T0_07_TXT(String eHF000600T0_07TXT) {
		EHF000600T0_07_TXT = eHF000600T0_07TXT;
	}

	public String getEHF000600T0_08() {
		return EHF000600T0_08;
	}

	public void setEHF000600T0_08(String eHF000600T0_08) {
		EHF000600T0_08 = eHF000600T0_08;
	}

	public String getEHF000600T0_09() {
		return EHF000600T0_09;
	}

	public void setEHF000600T0_09(String eHF000600T0_09) {
		EHF000600T0_09 = eHF000600T0_09;
	}

	public String getEHF000600T0_10() {
		return EHF000600T0_10;
	}

	public void setEHF000600T0_10(String eHF000600T0_10) {
		EHF000600T0_10 = eHF000600T0_10;
	}

	public String getEHF000600T0_11() {
		return EHF000600T0_11;
	}

	public void setEHF000600T0_11(String eHF000600T0_11) {
		EHF000600T0_11 = eHF000600T0_11;
	}

	public String getEHF000600T0_11_HH() {
		return EHF000600T0_11_HH;
	}

	public void setEHF000600T0_11_HH(String eHF000600T0_11HH) {
		EHF000600T0_11_HH = eHF000600T0_11HH;
	}

	public String getEHF000600T0_11_MM() {
		return EHF000600T0_11_MM;
	}

	public void setEHF000600T0_11_MM(String eHF000600T0_11MM) {
		EHF000600T0_11_MM = eHF000600T0_11MM;
	}

	public String getEHF000600T0_12() {
		return EHF000600T0_12;
	}

	public void setEHF000600T0_12(String eHF000600T0_12) {
		EHF000600T0_12 = eHF000600T0_12;
	}

	public String getEHF000600T0_12_HH() {
		return EHF000600T0_12_HH;
	}

	public void setEHF000600T0_12_HH(String eHF000600T0_12HH) {
		EHF000600T0_12_HH = eHF000600T0_12HH;
	}

	public String getEHF000600T0_12_MM() {
		return EHF000600T0_12_MM;
	}

	public void setEHF000600T0_12_MM(String eHF000600T0_12MM) {
		EHF000600T0_12_MM = eHF000600T0_12MM;
	}

	public String getEHF000600T0_13() {
		return EHF000600T0_13;
	}

	public void setEHF000600T0_13(String eHF000600T0_13) {
		EHF000600T0_13 = eHF000600T0_13;
	}

	public String getEHF000600T0_13_DAY() {
		return EHF000600T0_13_DAY;
	}

	public void setEHF000600T0_13_DAY(String eHF000600T0_13DAY) {
		EHF000600T0_13_DAY = eHF000600T0_13DAY;
	}

	public String getEHF000600T0_13_HOUR() {
		return EHF000600T0_13_HOUR;
	}

	public void setEHF000600T0_13_HOUR(String eHF000600T0_13HOUR) {
		EHF000600T0_13_HOUR = eHF000600T0_13HOUR;
	}

	public String getEHF000600T0_14() {
		return EHF000600T0_14;
	}

	public void setEHF000600T0_14(String eHF000600T0_14) {
		EHF000600T0_14 = eHF000600T0_14;
	}

	public String getEHF000600T0_14_DESC() {
		return EHF000600T0_14_DESC;
	}

	public void setEHF000600T0_14_DESC(String eHF000600T0_14DESC) {
		EHF000600T0_14_DESC = eHF000600T0_14DESC;
	}

	public String getEHF000600T0_15() {
		return EHF000600T0_15;
	}

	public void setEHF000600T0_15(String eHF000600T0_15) {
		EHF000600T0_15 = eHF000600T0_15;
	}

	public String getEHF000600T0_16() {
		return EHF000600T0_16;
	}

	public void setEHF000600T0_16(String eHF000600T0_16) {
		EHF000600T0_16 = eHF000600T0_16;
	}

	public String getEHF000600T0_16_DESC() {
		return EHF000600T0_16_DESC;
	}

	public void setEHF000600T0_16_DESC(String eHF000600T0_16DESC) {
		EHF000600T0_16_DESC = eHF000600T0_16DESC;
	}

	public String getEHF000600T0_17() {
		return EHF000600T0_17;
	}

	public void setEHF000600T0_17(String eHF000600T0_17) {
		EHF000600T0_17 = eHF000600T0_17;
	}

	public String getEHF000600T0_18() {
		return EHF000600T0_18;
	}

	public void setEHF000600T0_18(String eHF000600T0_18) {
		EHF000600T0_18 = eHF000600T0_18;
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

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}

	public List getEHF000600C() {
		return EHF000600C;
	}

	public void setEHF000600C(List eHF000600C) {
		EHF000600C = eHF000600C;
	}

	public void setEHF000600T0_03_SHOW(String eHF000600T0_03_SHOW) {
		EHF000600T0_03_SHOW = eHF000600T0_03_SHOW;
	}

	public String getEHF000600T0_03_SHOW() {
		return EHF000600T0_03_SHOW;
	}

	public void setEHF000600T0_04_SHOW(String eHF000600T0_04_SHOW) {
		EHF000600T0_04_SHOW = eHF000600T0_04_SHOW;
	}

	public String getEHF000600T0_04_SHOW() {
		return EHF000600T0_04_SHOW;
	}

	public void setEHF000600T0_05_SHOW(String eHF000600T0_05_SHOW) {
		EHF000600T0_05_SHOW = eHF000600T0_05_SHOW;
	}

	public String getEHF000600T0_05_SHOW() {
		return EHF000600T0_05_SHOW;
	}

	public void setEHF000600T0_06_SHOW(String eHF000600T0_06_SHOW) {
		EHF000600T0_06_SHOW = eHF000600T0_06_SHOW;
	}

	public String getEHF000600T0_06_SHOW() {
		return EHF000600T0_06_SHOW;
	}

	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}

	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}

	public void setEHF000600T0_07_SHOW(String eHF000600T0_07_SHOW) {
		EHF000600T0_07_SHOW = eHF000600T0_07_SHOW;		
	}
	
	public String getEHF000600T0_07_SHOW() {
		return EHF000600T0_07_SHOW;
	}

	public void setSIGN_COMMENT(String sIGN_COMMENT) {
		SIGN_COMMENT = sIGN_COMMENT;
	}

	public String getSIGN_COMMENT() {
		return SIGN_COMMENT;
	}
	
	public String getDATE_CREATE() {
		return DATE_CREATE;
	}

	public void setDATE_CREATE(String dATECREATE) {
		DATE_CREATE = dATECREATE;
	}
	
	public String getHR_CompanySysNo() {
		return HR_CompanySysNo;
	}

	public void setHR_CompanySysNo(String hRCompanySysNo) {
		HR_CompanySysNo = hRCompanySysNo;
	}


}
