package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.popo.models.EHF300200;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.util.BA_Vaildate;

public class EHF300200M0F extends ActionForm implements ValidateForm {

	private String EHF300200T0_01;
	private String EHF300200T0_02;
	private String EHF300200T0_03;
	private String EHF300200T0_04;
	private String EHF300200T0_05;
	private String EHF300200T0_05_TXT;
	private String EHF300200T0_06;
	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private List EHF300200T0_LIST = new ArrayList();
	


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
		EHF300200 ehf300200 = new EHF300200();
		

		String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");

		try{
			
			
			if( arrid==null && (EHF300200T0_01==null||"".equals(EHF300200T0_01)) ){
				request.setAttribute("ERR_MSG", "請先選擇一筆要刪除的資料!!");
			}else{
			
			boolean isEnable ;
			
			
				
				if( null == EHF300200T0_01 || "".equals(EHF300200T0_01) ){
					EHF300200T0_01 = arrid[0];
					EHF300200T0_06 = comp_id;
				}
				
				isEnable = ehf300200.selectEnabled(EHF300200T0_01, EHF300200T0_06);
			
				if(isEnable){//啟用中醫院無法刪除
					errors.add("EHF300200T0_01", new ActionMessage(""));
					request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"啟用中的醫院無法刪除!!");
				}
				
				deleteHPEnable_validate(errors, request, conn);//called method
		
			ehf300200.close();	
				
	}

			
		}catch(Exception e){
			e.printStackTrace();
		}
		 
		
	}

	
	
	private  void deleteHPEnable_validate(ActionErrors errors,	HttpServletRequest request, Connection conn){
			boolean query ;
			EHF300200 ehf300200 = new EHF300200();
			
			query = ehf300200.selectEHF310100T0(EHF300200T0_01, EHF300200T0_02, EHF300200T0_06);
			
			ehf300200.close();
		
			if(query){
				errors.add("EHF300200T0_01", new ActionMessage(""));
				request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"此醫院正在使用中，無法刪除!!");
			}
	}
	private void saveData_validate(ActionErrors errors,	HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF300200T0_02, "EHF300200T0_02", "不可空白");
		//ve.isEmpty(errors, EHF300200T0_03, "EHF300200T0_03", "不可空白");
		ve.isEmpty(errors, EHF300200T0_04, "EHF300200T0_04", "不可空白");
		
		//檢核字數	
		if((!"".equals(getEHF300200T0_02())|| getEHF300200T0_02()==null) && !GenericValidator.maxLength(getEHF300200T0_02(), 10)){//
			errors.add("EHF300200T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"醫院名稱字數不得超過10個字!!");
		}
		
		/*
		if(EHF300200T0_02.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("EHF300200T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務代碼請輸入英文加數字，且勿超過10碼!!");
		}*/
		
		
		//修改時，檢查是否有員工主檔正在使用，如有，則不能停用
		boolean query ;
		
		//EHF010106T6中未使用
		EHF300200 ehf300200 = new EHF300200();
		
		query = ehf300200.selectEHF310100T0(EHF300200T0_01, EHF300200T0_02, EHF300200T0_06);
		
		if(query){
			if (EHF300200T0_05.equals("0")){
			errors.add("EHF300200T0_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此醫院正在使用中，不能停用!!");
			}
		}
		
		//檢核該醫院名稱是否已經被使用。
		
		boolean queryHPName ;
		//boolean queryrouteName ;

		
		queryHPName = ehf300200.selectHPName(EHF300200T0_02, (String)request.getAttribute("compid"), EHF300200T0_01);
		//queryrouteName = ehf300200.queryrouteName(EHF300200T0_03, (String)request.getAttribute("compid"), EHF300200T0_01);
		
		ehf300200.close();

		if(queryHPName){
			errors.add("EHF300200T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此醫院名稱已經存在!!");
		}
		/*if(queryrouteName){
			errors.add("EHF300200T0_03", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此路線名稱已經存在!!");
		}*/
		
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
		
		ve.isEmpty(errors, EHF300200T0_02, "EHF300200T0_02", "不可空白");
		//ve.isEmpty(errors, EHF300200T0_03, "EHF300200T0_03", "不可空白");
		ve.isEmpty(errors, EHF300200T0_04, "EHF300200T0_04", "不可空白");
		ve.isEmpty(errors, EHF300200T0_05, "EHF300200T0_05", "不可空白");
		
		//檢核字數
		if((!"".equals(getEHF300200T0_02())|| getEHF300200T0_02()==null) && !GenericValidator.maxLength(getEHF300200T0_02(), 10)){//
			errors.add("EHF300200T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"醫院名稱字數不得超過10個字!!");
		}
				
		/*
		if(EHF300200T0_02.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("EHF300200T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"請輸入英文加數字，且勿超過10碼!!");
		}*/
		
		//檢核該路線名稱是否已經被使用。
		
		boolean query ;
		
		//ehf300200中未使用
		EHF300200 ehf300200 = new EHF300200();
		
		query = ehf300200.selectHPName(EHF300200T0_02, (String)request.getAttribute("compid"), EHF300200T0_01);
		
		ehf300200.close();
		
		if(query){
			errors.add("EHF300200T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此醫院名稱已經存在!!");
		}
		
	}

	public String getEHF300200T0_01() {
		return EHF300200T0_01;
	}

	public void setEHF300200T0_01(String eHF300200T0_01) {
		EHF300200T0_01 = eHF300200T0_01;
	}

	public String getEHF300200T0_02() {
		return EHF300200T0_02;
	}

	public void setEHF300200T0_02(String eHF300200T0_02) {
		EHF300200T0_02 = eHF300200T0_02;
	}

	public String getEHF300200T0_03() {
		return EHF300200T0_03;
	}

	public void setEHF300200T0_03(String eHF300200T0_03) {
		EHF300200T0_03 = eHF300200T0_03;
	}

	public String getEHF300200T0_04() {
		return EHF300200T0_04;
	}

	public void setEHF300200T0_04(String eHF300200T0_04) {
		EHF300200T0_04 = eHF300200T0_04;
	}

	public String getEHF300200T0_05() {
		return EHF300200T0_05;
	}

	public void setEHF300200T0_05(String eHF300200T0_05) {
		EHF300200T0_05 = eHF300200T0_05;
	}

	public String getEHF300200T0_06() {
		return EHF300200T0_06;
	}

	public void setEHF300200T0_06(String eHF300200T0_06) {
		EHF300200T0_06 = eHF300200T0_06;
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

	public void setDATE_UPDATE(String dATE_UPDATE) {
		DATE_UPDATE = dATE_UPDATE;
	}

	public List getEHF300200T0_LIST() {
		return EHF300200T0_LIST;
	}

	public void setEHF300200T0_LIST(List eHF300200T0LIST) {
		EHF300200T0_LIST = eHF300200T0LIST;
	}

	public String getEHF300200T0_05_TXT() {
		return EHF300200T0_05_TXT;
	}

	public void setEHF300200T0_05_TXT(String eHF300200T0_04TXT) {
		EHF300200T0_05_TXT = eHF300200T0_04TXT;
	}

}
