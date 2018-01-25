//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;



import java.sql.Connection;


import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;



/** 
 * MyEclipse Struts
 * Creation date: 02-01-2006
 * 
 * XDoclet definition:
 * @struts.form name="FM0102Form"
 */
public class SC0011F {
	
	
	
	private boolean CHECKED;
	
	private String SC0011_01;
	private String SC0011_02;
	private String SC0020_02;
	
	private boolean SC0011_03;
	private boolean SC0011_04;
	private boolean SC0011_05;
	private boolean SC0011_06;
	private boolean SC0011_07;
	
	private String SC0011_08;
	private String SC0020_04;
	
	
	private String DATE_CREATE;
	private String DATE_UPDATE;
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	
	
	// --------------------------------------------------------- Methods
	
//	當要用到Datagrid時，才需要這段程式
//	public void setDatagrid(Datagrid in_datagrid) {
//		datagrid = in_datagrid;
//	}
//	public Datagrid getDatagrid() {
//		return datagrid;
//	}

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
	        isEmpty(l_actionErrors, SC0011_01, "SC0011_01");
	        
	       
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
	 
	  private void isEmpty(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter)
	    {
	        if(in_parameter == null || in_parameter.equals(""))
	        {
	            in_actionErrors.add(in_nameParameter,new ActionMessage("不可空白"));
	        }
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

	public String getDATE_CREATE() {
		return DATE_CREATE;
	}

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

		

	public String getUSER_CREATE() {
		return USER_CREATE;
	}

	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}

	public int getVERSION() {
		return VERSION;
	}

	public void setDATE_CREATE(String date_create) {
		DATE_CREATE = date_create;
	}

	public void setDATE_UPDATE(String date_update) {
		DATE_UPDATE = date_update;
	}

	

	
	public void setUSER_CREATE(String user_create) {
		USER_CREATE = user_create;
	}

	public void setUSER_UPDATE(String user_update) {
		USER_UPDATE = user_update;
	}

	public void setVERSION(int version) {
		VERSION = version;
	}

	public String getSC0011_01() {
		return SC0011_01;
	}

	public String getSC0011_02() {
		return SC0011_02;
	}

	

	public String getSC0011_08() {
		return SC0011_08;
	}

	public void setSC0011_01(String sc0011_01) {
		SC0011_01 = sc0011_01;
	}

	public void setSC0011_02(String sc0011_02) {
		SC0011_02 = sc0011_02;
	}

	

	public void setSC0011_08(String sc0011_08) {
		SC0011_08 = sc0011_08;
	}

	public boolean isSC0011_03() {
		return SC0011_03;
	}

	public boolean isSC0011_04() {
		return SC0011_04;
	}

	public boolean isSC0011_05() {
		return SC0011_05;
	}

	public boolean isSC0011_06() {
		return SC0011_06;
	}

	public boolean isSC0011_07() {
		return SC0011_07;
	}

	public void setSC0011_03(boolean sc0011_03) {
		SC0011_03 = sc0011_03;
	}

	public void setSC0011_04(boolean sc0011_04) {
		SC0011_04 = sc0011_04;
	}

	public void setSC0011_05(boolean sc0011_05) {
		SC0011_05 = sc0011_05;
	}

	public void setSC0011_06(boolean sc0011_06) {
		SC0011_06 = sc0011_06;
	}

	public void setSC0011_07(boolean sc0011_07) {
		SC0011_07 = sc0011_07;
	}

	public boolean isCHECKED() {
		return CHECKED;
	}

	public void setCHECKED(boolean checked) {
		CHECKED = checked;
	}

	public String getSC0020_02() {
		return SC0020_02;
	}

	public void setSC0020_02(String sc0020_02) {
		SC0020_02 = sc0020_02;
	}

	public String getSC0020_04() {
		return SC0020_04;
	}

	public void setSC0020_04(String sc0020_04) {
		SC0020_04 = sc0020_04;
	}

	
		
	
}


