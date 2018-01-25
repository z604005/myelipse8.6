//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

//import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;



/** 
 * MyEclipse Struts
 * Creation date: 02-01-2006
 * 
 * XDoclet definition:
 * @struts.form name="FM0102Form"
 */
public class SC002F extends ActionForm {
	//當要用到Datagrid時，才需要這段程式
	//private Datagrid datagrid;
	
	private String SC0020_01;
	private String SC0020_02;
	private String SC0020_03;
	private String SC0020_04;
	private String SC0020_05;
	private int SC0020_06;
	private String SC0020_07="N";
	private String SC0020_08;
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
	        isEmpty(l_actionErrors, SC0020_01, "SC0020_01");
	        isEmpty(l_actionErrors, SC0020_02, "SC0020_02");
	        isEmpty(l_actionErrors, SC0020_03, "SC0020_03");
	        isEmpty(l_actionErrors, SC0020_04, "SC0020_04");
	        
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
		  if(conn!=null)
	        {
		        try {
		            Statement stmt = conn.createStatement();
		            String sql="select SC0020_01 from SC0020 where SC0020_01='"+SC0020_01+"' and SC0020_08='"+SC0020_08+"'";
					ResultSet rs=stmt.executeQuery(sql);
		            if(rs.next())
		            {
		            	in_actionErrors.add("SC0020_01",new ActionMessage("鍵值重複") );
		            }
		            rs.close();
		            stmt.close();
		            
				} catch (SQLException e) {
					e.printStackTrace();
				}
	        }
		 
	 }
	 private void saveData_validate(ActionErrors in_actionErrors,HttpServletRequest request, Connection conn)
	 {
		    if(conn!=null)
	        {
		        try {
		        	 Statement stmt = conn.createStatement();
		        	 String sql="select * from SC0020 where SC0020_01='"+SC0020_01+"' and SC0020_08='"+SC0020_08+"'";
		        	 ResultSet rs=stmt.executeQuery(sql);
			            if(rs.next())
			            {
			            	if(rs.getInt("VERSION")!=VERSION)
			            	{
			            		in_actionErrors.add("VERSION",new ActionMessage("資料版本不符") );
			            		SC002F Form=new SC002F();
			            		Form.setSC0020_01(rs.getString("SC0020_01"));
			    				Form.setSC0020_02(rs.getString("SC0020_02"));
			    				Form.setSC0020_03(rs.getString("SC0020_03"));
			    				Form.setSC0020_04(rs.getString("SC0020_04"));
			    				Form.setSC0020_06(rs.getInt("SC0020_06"));
			    				Form.setSC0020_07(rs.getString("SC0020_07"));
			    				Form.setDATE_CREATE(rs.getString("DATE_CREATE"));
			    				Form.setDATE_UPDATE(rs.getString("DATE_UPDATE"));
			    				Form.setUSER_CREATE(rs.getString("USER_CREATE"));
			    				Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
			    				Form.setVERSION(rs.getInt("VERSION"));
			    				request.setAttribute("FormData",Form);
			    				request.setAttribute("ErrMSG","資料版本不符，請重新修改!");
			            	}
			            }
			            else
			            {
			            	in_actionErrors.add("SC0020_01",new ActionMessage("資料已被刪除") );
			            }
			            rs.close();
			            stmt.close();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
		 
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

	public String getSC0020_01() {
		return SC0020_01;
	}

	public String getSC0020_02() {
		return SC0020_02;
	}

	public String getSC0020_03() {
		return SC0020_03;
	}

	public String getSC0020_04() {
		return SC0020_04;
	}

	public String getSC0020_05() {
		return SC0020_05;
	}
    
	public String getSC0020_08() {
		return SC0020_08;
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

	public void setSC0020_01(String sc0020_01) {
		SC0020_01 = sc0020_01;
	}

	public void setSC0020_02(String sc0020_02) {
		SC0020_02 = sc0020_02;
	}

	public void setSC0020_03(String sc0020_03) {
		SC0020_03 = sc0020_03;
	}

	public void setSC0020_04(String sc0020_04) {
		SC0020_04 = sc0020_04;
	}

	public void setSC0020_05(String sc0020_05) {
		SC0020_05 = sc0020_05;
	}
	
	public void setSC0020_08(String sc0020_08) {
		this.SC0020_08 = sc0020_08;
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

	public int getSC0020_06() {
		return SC0020_06;
	}

	public void setSC0020_06(int sc0020_06) {
		SC0020_06 = sc0020_06;
	}

	public String getSC0020_07() {
		return SC0020_07;
	}

	public void setSC0020_07(String sc0020_07) {
		SC0020_07 = sc0020_07;
	}
	
	
	
}


