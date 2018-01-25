//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
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
public class SC001F extends ActionForm {
	//當要用到Datagrid時，才需要這段程式
	//private Datagrid datagrid;
	
	
	
	private String SC0010_01;
	private String SC0010_02;
	private String SC0010_03;
	private String SC0010_04;
	
	private String SC0010_99="SC";
	
	
	
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
	        isEmpty(l_actionErrors, SC0010_01, "SC0010_01");
	        isEmpty(l_actionErrors, SC0010_02, "SC0010_02");
	       
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
		            String sql="select SC0010_01 from SC0010 where SC0010_01='"+SC0010_01+"' and SC0010_04='"+SC0010_04+"'";
					ResultSet rs=stmt.executeQuery(sql);
		            if(rs.next())
		            {
		            	in_actionErrors.add("SC0010_01",new ActionMessage("鍵值重複") );
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
		        	 String sql="select * from SC0010 where SC0010_01='"+SC0010_01+"' and SC0010_04='"+SC0010_04+"'";
		        	 ResultSet rs=stmt.executeQuery(sql);
			            if(rs.next())
			            {
			            	if(rs.getInt("VERSION")!=VERSION)
			            	{
			            		in_actionErrors.add("VERSION",new ActionMessage("資料版本不符") );
			            		SC001F Form=new SC001F();
			            		Form.setSC0010_01(rs.getString("SC0010_01"));
			    				Form.setSC0010_02(rs.getString("SC0010_02"));
			    				Form.setSC0010_03(rs.getString("SC0010_03"));
			    				 
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
			            	in_actionErrors.add("SC0010_01",new ActionMessage("資料已被刪除") );
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

	public String getSC0010_01() {
		return SC0010_01;
	}

	public String getSC0010_02() {
		return SC0010_02;
	}

	public String getSC0010_03() {
		return SC0010_03;
	}
	
	public String getSC0010_04() {
		return SC0010_04;
	}

	public void setSC0010_01(String sc0010_01) {
		SC0010_01 = sc0010_01;
	}

	public void setSC0010_02(String sc0010_02) {
		SC0010_02 = sc0010_02;
	}

	public void setSC0010_03(String sc0010_03) {
		SC0010_03 = sc0010_03;
	}
	
	public void setSC0010_04(String sc0010_04) {
		SC0010_04 = sc0010_04;
	}


	//表身資料
	protected List SC0011  ;
	
	
	public List getSC0011() {
        return SC0011;
    }

	
	public void setSC0011(List  SC0011) {
        this.SC0011=SC0011;
     }

	
	public void setSC0011(SC0011F SC0011) {
        this.SC0011.add(SC0011);
    }
	

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			 SC0011 = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new SC0011F();
				            }
			 });
		} catch (Exception e) {
		}
	}

	public String getSC0010_99() {
		return SC0010_99;
	}

	public void setSC0010_99(String sc0010_99) {
		SC0010_99 = sc0010_99;
	}


	
}


