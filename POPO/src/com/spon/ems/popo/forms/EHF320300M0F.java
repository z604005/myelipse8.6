package com.spon.ems.popo.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.popo.models.EHF320300;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

public class EHF320300M0F extends ActionForm implements ValidateForm {

	private String EHF320300T0_01;
	private String EHF320300T0_02;
	private String EHF320300T0_02_TXT;
	private String EHF320300T0_02_01;
	private String EHF320300T0_02_02;
	private String EHF320300T0_03;
	private List listEHF320300T0_03 = new ArrayList();
	private String EHF320300T0_03_TXT;
	private String EHF320300T0_04;
	private List listEHF320300T0_05 = new ArrayList();
	private String EHF320300T0_05_TXT;
	private String EHF320300T1_05_TXT_0;
	
	
	
	private String EHF320300T1_01;
	private String EHF320300T1_02;
	private String EHF320300T1_03;
	private List listEHF320300T1_03 = new ArrayList();
	private String EHF320300T1_04;
	private List listEHF320300T1_04 = new ArrayList();
	private String EHF320300T1_05;
	private String EHF320300T1_05_TXT;
	
	private String EE;
	
	
	
	private List EHF320300_C = new ArrayList();
	private List EHF320300_DETAIL = new ArrayList();


	
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	private String DATE_CREATE;
	private String DATE_UPDATE;
	
	private List EHF320300T0_LIST = new ArrayList();
	
	private List EHF320300T1_LIST = new ArrayList();
	
	private List setEHF320300_DETAIL = new ArrayList();

	
	


	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if (l_actionErrors.isEmpty()) {
			
			//查詢時判斷條件
			if (request.getAttribute("action").equals("queryData")) {
								
				queryData_validate(l_actionErrors, request, conn);
			}
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
								
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addEHF320300T1")) {
				
				addEHF320300T1_validate(l_actionErrors, request, conn);
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
		EHF320300 ehf320300 = new EHF320300();
		

		String arrid[] = request.getParameterValues("checkId"); //取得勾選的ID
		String comp_id = (String) request.getAttribute("compid"); //取得使用者的公司編號
		
		
		boolean cc ;
		
		
			
		if( null == EHF320300T0_01 || "".equals(EHF320300T0_01) ){
			EHF320300T0_01 = arrid[0];
			EHF320300T0_04 = comp_id;
		}
			
		cc = ehf320300.selectPreterit(EHF320300T0_01, EHF320300T0_04);
		
		if(cc){
			errors.add("EHF320300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"過去的日期不能刪除。");
		}
		
		/*boolean query ;
		
		query = ehf320300.selectEHF310100T0(EHF320300T0_01, EHF320300T0_02, EHF320300T0_06);
		
		if(query){
			errors.add("EHF320300T0_01", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"此路線正在使用中，無法刪除!!");
		}
		*/
		ehf320300.close();
		
	}
	
	private void queryData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		try {
				String Begin =EHF320300T0_02_01.toString();
				String End = EHF320300T0_02_02.toString();
					if (End != null && !End.equals("") && !"".equals(Begin)) {
						if (Begin != null && !Begin.equals("") && !"".equals(End)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
							Date Begindate = sdf.parse(Begin);
							Date Enddate = sdf.parse(End);
							if (Begindate.after(Enddate)){
								errors.add("EHF320300T0_02_01", new ActionMessage(""));
								errors.add("EHF320300T0_02_02", new ActionMessage(""));
								request.setAttribute("ERR_MSG", "查詢開始日期不能大於查詢結束日期");
							}
						}
				}
			} catch (ParseException e) {
			e.printStackTrace();
			
		}
	
	}
	

	private void saveData_validate(ActionErrors errors,	HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF320300T0_02, "EHF320300T0_02", "不可空白");
		ve.isEmpty(errors, EHF320300T0_03, "EHF320300T0_03", "不可空白");
		ve.isEmpty(errors, EHF320300T0_04, "EHF320300T0_04", "不可空白");
		
		
		/*
		//檢核字數
		if((!"".equals(getEHF320300T0_05())|| getEHF320300T0_05()==null) && !GenericValidator.maxLength(getEHF320300T0_05(), 100)){//
			errors.add("EHF320300T0_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註字數不得超過100個字!!");
		}
		
		if((!"".equals(getEHF320300T0_02())|| getEHF320300T0_02()==null) && !GenericValidator.maxLength(getEHF320300T0_02(), 10)){//
			errors.add("EHF320300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"路線名稱字數不得超過10個字!!");
		}*/
		
		/*
		if(EHF320300T0_02.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("EHF320300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"職務代碼請輸入英文加數字，且勿超過10碼!!");
		}*/
		
		/*
		//修改時，檢查是否有員工主檔正在使用，如有，則不能停用
		boolean query ;
		
		//EHF010106T6中未使用
		EHF320300 ehf320300 = new EHF320300();
		
		query = ehf320300.selectEHF310100T0(EHF320300T0_01, EHF320300T0_02, EHF320300T0_06);
		
		if(query){
			if (EHF320300T0_04.equals("0")){
			errors.add("EHF320300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此路線正在使用中，不能停用!!");
			}
		}
		
		ehf320300.close();*/
		
		
	}

	private void addEHF320300T1_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		ve.isEmpty(errors, EHF320300T1_02, "EHF320300T1_02", "不可空白");
		ve.isEmpty(errors, EHF320300T1_03, "EHF320300T1_03", "不可空白");
		ve.isEmpty(errors, EHF320300T1_04, "EHF320300T1_04", "不可空白");
		ve.isEmpty(errors, EHF320300T1_05, "EHF320300T1_05", "不可空白");
		ve.isEmpty(errors, EHF320300T1_05_TXT_0, "EHF320300T1_05_TXT_0", "不可空白");
		
		
		boolean query ;
		EHF320300 ehf320300 = new EHF320300();
		query = ehf320300.selectEHF320300T1(EHF320300T0_01, EHF320300T1_02);
		
		if(query){
			errors.add("EHF320300T1_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"上菜順序不可重覆。");
		}
		
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		ve.isEmpty(errors, EHF320300T0_02, "EHF320300T0_02", "不可空白");
		ve.isEmpty(errors, EHF320300T0_03, "EHF320300T0_03", "不可空白");
//		ve.isEmpty(errors, EHF320300T0_04, "EHF320300T0_04", "不可空白");
		
		/*
		//檢核字數
		if((!"".equals(getEHF320300T0_05())|| getEHF320300T0_05()==null) && !GenericValidator.maxLength(getEHF320300T0_05(), 100)){//
			errors.add("EHF320300T0_05", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"備註字數不得超過100個字!!");
		}
		
		if((!"".equals(getEHF320300T0_02())|| getEHF320300T0_02()==null) && !GenericValidator.maxLength(getEHF320300T0_02(), 10)){//
			errors.add("EHF320300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"路線名稱字數不得超過10個字!!");
		}*/
				
		/*
		if(EHF320300T0_02.matches("^[A-Za-z0-9]{1,10}+$")){
			//不做動作
		}else{
			errors.add("EHF320300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"請輸入英文加數字，且勿超過10碼!!");
		}*/
		
		//檢核日期是玻重覆
		
		boolean query ;
		
		//ehf320300中未使用
		EHF320300 ehf320300 = new EHF320300();
		
		query = ehf320300.selectData(EHF320300T0_02, (String)request.getAttribute("compid"));
		
		if(query){
			errors.add("EHF320300T0_02", new ActionMessage(""));
			request.setAttribute("ERR_MSG", "此日期已經存在。");
		}
		
		
	}

	public String getEHF320300T0_01() {
		return EHF320300T0_01;
	}

	public void setEHF320300T0_01(String eHF320300T0_01) {
		EHF320300T0_01 = eHF320300T0_01;
	}

	public String getEHF320300T0_02() {
		return EHF320300T0_02;
	}
	
	public void setEHF320300T0_02(String eHF320300T0_02) {
		EHF320300T0_02 = eHF320300T0_02;
	}

	public String getEHF320300T0_02_TXT() {
		return EHF320300T0_02_TXT;
	}

	public void setEHF320300T0_02_TXT(String eHF320300T0_02TXT) {
		EHF320300T0_02_TXT = eHF320300T0_02TXT;
	}

	public String getEHF320300T0_02_01() {
		return EHF320300T0_02_01;
	}

	public void setEHF320300T0_02_01(String eHF320300T0_02_01) {
		EHF320300T0_02_01 = eHF320300T0_02_01;
	}

	public String getEHF320300T0_02_02() {
		return EHF320300T0_02_02;
	}

	public void setEHF320300T0_02_02(String eHF320300T0_02_02) {
		EHF320300T0_02_02 = eHF320300T0_02_02;
	}
	
	public String getEHF320300T0_03() {
		return EHF320300T0_03;
	}

	public void setEHF320300T0_03(String eHF320300T0_03) {
		EHF320300T0_03 = eHF320300T0_03;
	}

	public String getEHF320300T0_03_TXT() {
		return EHF320300T0_03_TXT;
	}

	public void setEHF320300T0_03_TXT(String eHF320300T0_03TXT) {
		EHF320300T0_03_TXT = eHF320300T0_03TXT;
	}

	public String getEHF320300T0_05_TXT() {
		return EHF320300T0_05_TXT;
	}

	public void setEHF320300T0_05_TXT(String eHF320300T0_05TXT) {
		EHF320300T0_05_TXT = eHF320300T0_05TXT;
	}

	public String getEHF320300T0_04() {
		return EHF320300T0_04;
	}

	public void setEHF320300T0_04(String eHF320300T0_04) {
		EHF320300T0_04 = eHF320300T0_04;
	}

	public String getEHF320300T1_01() {
		return EHF320300T1_01;
	}

	public void setEHF320300T1_01(String eHF320300T1_01) {
		EHF320300T1_01 = eHF320300T1_01;
	}

	public String getEHF320300T1_02() {
		return EHF320300T1_02;
	}

	public void setEHF320300T1_02(String eHF320300T1_02) {
		EHF320300T1_02 = eHF320300T1_02;
	}

	public String getEHF320300T1_03() {
		return EHF320300T1_03;
	}

	public void setEHF320300T1_03(String eHF320300T1_03) {
		EHF320300T1_03 = eHF320300T1_03;
	}

	public String getEHF320300T1_04() {
		return EHF320300T1_04;
	}

	public void setEHF320300T1_04(String eHF320300T1_04) {
		EHF320300T1_04 = eHF320300T1_04;
	}

	public String getEHF320300T1_05() {
		return EHF320300T1_05;
	}

	public void setEHF320300T1_05(String eHF320300T1_05) {
		EHF320300T1_05 = eHF320300T1_05;
	}

	public String getEHF320300T1_05_TXT() {
		return EHF320300T1_05_TXT;
	}

	public void setEHF320300T1_05_TXT(String eHF320300T1_05TXT) {
		EHF320300T1_05_TXT = eHF320300T1_05TXT;
	}

	public String getEHF320300T1_05_TXT_0() {
		return EHF320300T1_05_TXT_0;
	}

	public void setEHF320300T1_05_TXT_0(String eHF320300T1_05TXT_0) {
		EHF320300T1_05_TXT_0 = eHF320300T1_05TXT_0;
	}

	public List getEHF320300_C() {
		return EHF320300_C;
	}

	public void setEHF320300_C(List eHF320300C) {
		EHF320300_C = eHF320300C;
	}

	public List getEHF320300_DETAIL() {
		return EHF320300_DETAIL;
	}

	public void setEHF320300_DETAIL(List eHF320300DETAIL) {
		EHF320300_DETAIL = eHF320300DETAIL;
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

	public List getEHF320300T0_LIST() {
		return EHF320300T0_LIST;
	}

	public void setEHF320300T0_LIST(List eHF320300T0LIST) {
		EHF320300T0_LIST = eHF320300T0LIST;
	}

	public List getEHF320300T1_LIST() {
		return EHF320300T1_LIST;
	}

	public void setEHF320300T1_LIST(List eHF320300T1LIST) {
		EHF320300T1_LIST = eHF320300T1LIST;
	}

	public List getSetEHF320300_DETAIL() {
		return setEHF320300_DETAIL;
	}

	public void setSetEHF320300_DETAIL(List setEHF320300DETAIL) {
		setEHF320300_DETAIL = setEHF320300DETAIL;
	}
	public List getListEHF320300T0_03() {
		return listEHF320300T0_03;
	}

	public void setListEHF320300T0_03(List listEHF320300T0_03) {
		this.listEHF320300T0_03 = listEHF320300T0_03;
	}

	public List getListEHF320300T0_05() {
		return listEHF320300T0_05;
	}

	public void setListEHF320300T0_05(List listEHF320300T0_05) {
		this.listEHF320300T0_05 = listEHF320300T0_05;
	}

	public List getListEHF320300T1_03() {
		return listEHF320300T1_03;
	}

	public void setListEHF320300T1_03(List listEHF320300T1_03) {
		this.listEHF320300T1_03 = listEHF320300T1_03;
	}

	public List getListEHF320300T1_04() {
		return listEHF320300T1_04;
	}

	public void setListEHF320300T1_04(List listEHF320300T1_04) {
		this.listEHF320300T1_04 = listEHF320300T1_04;
	}



	public String getEE() {
		return EE;
	}

	public void setEE(String eE) {
		EE = eE;
	}

	/**
	* ITEM_ID轉為ITEM_VALUE
	*/
	public String getITEM_VALUE(String item_ID, String classKey, String comp_id ){
		
		
		String ItemValue = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
			
		try{
				
			String sql = "" +
			" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = '"+classKey+"' " +
			" AND EMS_CategoryT1_04 = '"+item_ID+"' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			if(rs.next()){
				ItemValue = rs.getString("ITEM_VALUE");
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
			
		return ItemValue;
	}



}
