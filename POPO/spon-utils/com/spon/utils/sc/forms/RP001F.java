//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;


import com.spon.utils.util.BA_Vaildate;



/** 
 * MyEclipse Struts
 * Creation date: 02-01-2006
 * 
 * XDoclet definition:
 * @struts.form name="FM0102Form"
 */
public class RP001F extends ActionForm {
	
	
	private String PEUNIT;
	private String PEUNITN="";
	
	private String RPT_TYPE;
	
	private String DATEB;
	private String DATEE;
	
	
	public String getDATEB() {
		return DATEB;
	}

	public String getDATEE() {
		return DATEE;
	}

	public void setDATEB(String dateb) {
		DATEB = dateb;
	}

	public void setDATEE(String datee) {
		DATEE = datee;
	}

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request,Connection conn) {
		 ActionErrors l_actionErrors = new ActionErrors();
		 //檢查不可為空白
		 BA_Vaildate ve=BA_Vaildate.getInstance();
		 ve.isEmpty(l_actionErrors, DATEB, "SDATE","不可空白");
		 ve.isEmpty(l_actionErrors, DATEE, "EDATE","不可空白");
		    
	        if(l_actionErrors.isEmpty())
	        {
		        //新增時判斷條件
		        if(request.getAttribute("action").equals("addData"))
		        {    
				      addData_validate(l_actionErrors,request,conn);
		        }
		        
	//	     修改時判斷條件
		        if(request.getAttribute("action").equals("saveData"))
		        {    
		        	 saveData_validate(l_actionErrors,request,conn);
		        }
	        }
	        
	        return l_actionErrors;
	}
	
	 private void addData_validate(ActionErrors in_actionErrors,HttpServletRequest request, Connection conn)
	 {
		 
		 
	 }
	 private void saveData_validate(ActionErrors in_actionErrors,HttpServletRequest request, Connection conn)
	 {
		    
		 
	 }
	 private void delData_validate(ActionErrors in_actionErrors,HttpServletRequest request, Connection conn)
	 {
		 
	 }
	 
	
	  
	

	

	
	

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			
		} catch (Exception e) {
		}
	}

	

	public String getPEUNIT() {
		return PEUNIT;
	}



	public void setPEUNIT(String peunit) {
		PEUNIT = peunit;
	}

	public String getPEUNITN() {
		return PEUNITN;
	}

	public void setPEUNITN(String peunitn) {
		PEUNITN = peunitn;
	}

	public String getRPT_TYPE() {
		return RPT_TYPE;
	}

	public void setRPT_TYPE(String rpt_type) {
		RPT_TYPE = rpt_type;
	}


	
}


