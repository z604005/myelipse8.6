//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;
//import org.apache.struts.action.ActionError;
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
public class DF0021F extends ActionForm {
	//當要用到Datagrid時，才需要這段程式
	//private Datagrid datagrid;
	
	private String DF0021_01;
	private String DF0021_02;
	private String DF0021_03;
	private String DF0021_04;
	
	
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
	       BA_Vaildate ve=BA_Vaildate.getInstance();
	       
	       ve.isEmpty(l_actionErrors, DF0021_03, "DF0010_03","程式代碼不可空白");
	       
	       
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
	
	 private void addData_validate(ActionErrors in_actionErrors,HttpServletRequest request, Connection conn){
		  if(conn!=null)
	        {
		        try {
		            Statement stmt = conn.createStatement();
		            String sql="select DF0021_01,DF0021_02,DF0021_03 from DF0021 where DF0021_01='"+DF0021_01+"' AND DF0021_02='"+DF0021_02+"' AND DF0021_03='"+DF0021_03+"'";
					ResultSet rs=stmt.executeQuery(sql);
		            if(rs.next())
		            {
		            	in_actionErrors.add("DF0021_03",new ActionMessage("鍵值重複") );
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
		        	 String sql="select * from DF0021 where DF0021_01='"+DF0021_01+"' AND DF0021_02='"+DF0021_02+"' AND DF0021_03='"+DF0021_03+"'";
		        	 ResultSet rs=stmt.executeQuery(sql);
			            if(rs.next())
			            {
			            	if(rs.getInt("VERSION")!=VERSION)
			            	{
			            		in_actionErrors.add("VERSION",new ActionMessage("資料版本不符") );
			            		DF0021F Form=new DF0021F();
			            		Form.setDF0021_01(rs.getString("DF0021_01"));
			    				Form.setDF0021_02(rs.getString("DF0021_02"));
			    				Form.setDF0021_03(rs.getString("DF0021_03"));
			    				Form.setDF0021_04(rs.getString("DF0021_04"));
			    				
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
			            	in_actionErrors.add("DF0020_01",new ActionMessage("資料已被刪除") );
			            	in_actionErrors.add("DF0020_02",new ActionMessage("資料已被刪除") );
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

	public String getDF0021_01() {
		return DF0021_01;
	}

	public String getDF0021_02() {
		return DF0021_02;
	}

	public String getDF0021_03() {
		return DF0021_03;
	}

	public String getDF0021_04() {
		return DF0021_04;
	}

	public void setDF0021_01(String df0021_01) {
		DF0021_01 = df0021_01;
	}

	public void setDF0021_02(String df0021_02) {
		DF0021_02 = df0021_02;
	}

	public void setDF0021_03(String df0021_03) {
		DF0021_03 = df0021_03;
	}

	public void setDF0021_04(String df0021_04) {
		DF0021_04 = df0021_04;
	}


	
	
}


