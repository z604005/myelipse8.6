package com.spon.utils.struts.form;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.utils.util.BA_Vaildate;

public class BA_EMSForm extends ActionForm {
	private static final long serialVersionUID = 1L;
	private boolean CHECKED;
	private boolean CHANGED;
	
	private  String  CHKBUTTON;
	private  String  USERID;
	private  String  USERNAME;
	private  String  USERKEY;
	private  String  id;
	private  String  search;
	private  String  function;
	private  String  depkey;
	private  String  empkey;
	private  String  changescript;
	private  String  clearfield;
	private  String  depth;


	private List EMSC=new ArrayList();

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve=BA_Vaildate.getInstance();
		
		
		
		if (l_actionErrors.isEmpty()) {
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				addData_validate(l_actionErrors, request, conn);
			}

			//	     修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				saveData_validate(l_actionErrors, request, conn);
			}
		}

		return l_actionErrors;
	}
	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		try{

		}catch (Exception e) {
			System.out.println("BA_EMSForm.addData_validate()" + e);
		}
	}
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("utf-8");
			EMSC = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new BA_EMSForm();
				            }
			 });
		} catch (Exception e) {
		}

	}
	public boolean isCHECKED() {
		return CHECKED;
	}
	public void setCHECKED(boolean cHECKED) {
		CHECKED = cHECKED;
	}
	public boolean isCHANGED() {
		return CHANGED;
	}
	public void setCHANGED(boolean cHANGED) {
		CHANGED = cHANGED;
	}
	public String getCHKBUTTON() {
		return CHKBUTTON;
	}
	public void setCHKBUTTON(String cHKBUTTON) {
		CHKBUTTON = cHKBUTTON;
	}
	public String getUSERID() {
		return USERID;
	}
	public void setUSERID(String uSERID) {
		USERID = uSERID;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}
	public String getUSERKEY() {
		return USERKEY;
	}
	public void setUSERKEY(String uSERKEY) {
		USERKEY = uSERKEY;
	}
	public List getEMSC() {
		return EMSC;
	}
	public void setEMSC(List eMSC) {
		EMSC = eMSC;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getDepkey() {
		return depkey;
	}
	public void setDepkey(String depkey) {
		this.depkey = depkey;
	}
	public String getEmpkey() {
		return empkey;
	}
	public void setEmpkey(String empkey) {
		this.empkey = empkey;
	}
	public String getChangescript() {
		return changescript;
	}
	public void setChangescript(String changescript) {
		this.changescript = changescript;
	}
	public String getClearfield() {
		return clearfield;
	}
	public void setClearfield(String clearfield) {
		this.clearfield = clearfield;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	
	
}