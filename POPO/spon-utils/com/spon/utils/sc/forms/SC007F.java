//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;


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



/** 
 * MyEclipse Struts
 * Creation date: 02-01-2006
 * 
 * XDoclet definition:
 * @struts.form name="FM0102Form"
 */
public class SC007F extends ActionForm {
	//當要用到Datagrid時，才需要這段程式
	//private Datagrid datagrid;
	
	
	private String SC0070_01;
	private String SC0070_02;
	
	private String SC0070_03;
	private boolean SC0070_04;
	private String SC0070_05;
	private String SC0070_05_HH;
	private String SC0070_05_MM;
	
	private String SC0070_06;
	private String SC0070_06_HH;
	private String SC0070_06_MM;
	
	private boolean SC0070_07;
	private String SC0070_08;
	private String SC0070_09;
	
	
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
		
		 BA_Vaildate ve=BA_Vaildate.getInstance();
		 
		 ve.isEmpty(l_actionErrors, SC0070_02, "SC0070_02", "");
		 ve.isEmpty(l_actionErrors, SC0070_03, "SC0070_03", "");
		 
		 ve.isLength(l_actionErrors, SC0070_03, "SC0070_03",200,"");
	      
	        
		 if(!SC0070_05.equals(""))
		 {
			ve.isTypeDate(l_actionErrors,SC0070_05,"SC0070_05","");
			 ve.isEmpty(l_actionErrors, SC0070_06, "SC0070_06", "");
		 } 
		 if(!SC0070_06.equals(""))
		 {
			 ve.isEmpty(l_actionErrors, SC0070_05, "SC0070_05", "");
			 ve.isTypeDate(l_actionErrors,SC0070_06,"SC0070_06","");
		 }
		 if(!SC0070_06.equals("")&!SC0070_05.equals(""))
		 {
			 String start=SC0070_05.replaceAll("/","")+SC0070_05_HH+SC0070_05_MM;
			 String end=SC0070_06.replaceAll("/","")+SC0070_06_HH+SC0070_06_MM;
			 if(Long.parseLong(start)>=Long.parseLong(end))
			 {
				 l_actionErrors.add("SC0070_05",new ActionMessage("開始時間不得大於等於結束時間"));
			 }
			
		 }
		 
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
		    if(conn!=null)
	        {
		        try {
		        	 Statement stmt = conn.createStatement();
		        	 String sql="select * from SC0070 where SC0070_01='"+SC0070_01+"' and SC0070_09='"+SC0070_09+"'";
		        	
		        	 ResultSet rs=stmt.executeQuery(sql);
			            if(rs.next())
			            {
			            	if(rs.getInt("VERSION")!=VERSION)
			            	{
			            		in_actionErrors.add("VERSION",new ActionMessage("資料版本不符") );
			            		SC007F Form=new SC007F();
			            		Form.setSC0070_01(rs.getString("SC0070_01"));
			    				Form.setSC0070_02(rs.getString("SC0070_02"));
			    				Form.setSC0070_03(rs.getString("SC0070_03"));
			    				Form.setSC0070_04(rs.getInt("SC0070_04")==1?true:false);
			    				Form.setSC0070_05(rs.getString("SC0070_05"));
			    				Form.setSC0070_06(rs.getString("SC0070_06"));
			    				Form.setSC0070_07(rs.getInt("SC0070_07")==1?true:false);
			    				Form.setSC0070_08(rs.getString("SC0070_08"));
			    				
			    				 
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
			            	in_actionErrors.add("SC0070_01",new ActionMessage("資料已被刪除") );
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



	//表身資料
	protected List SC0071  ;
	
	
	public List getSC0071() {
        return SC0071;
    }

	
	public void setSC0071(List  SC0071) {
        this.SC0071=SC0071;
     }

	
	public void setSC0071(SC0071F SC0071) {
        this.SC0071.add(SC0071);
    }
	

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			 SC0071 = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new SC0071F();
				            }
			 });
		} catch (Exception e) {
		}
	}

	public String getSC0070_01() {
		return SC0070_01;
	}

	public String getSC0070_02() {
		return SC0070_02;
	}

	public String getSC0070_03() {
		return SC0070_03;
	}

	
	
	public String getSC0070_06() {
		return SC0070_06;
	}

	
	public String getSC0070_08() {
		return SC0070_08;
	}

	

	public void setSC0070_01(String sc0030_01) {
		SC0070_01 = sc0030_01;
	}

	public void setSC0070_02(String sc0030_02) {
		SC0070_02 = sc0030_02;
	}

	public void setSC0070_03(String sc0030_03) {
		SC0070_03 = sc0030_03;
	}


	

	public void setSC0070_06(String sc0030_06) {
		SC0070_06 = sc0030_06;
	}

	

	public void setSC0070_08(String sc0030_08) {
		SC0070_08 = sc0030_08;
	}

	public boolean isSC0070_04() {
		return SC0070_04;
	}

	public String getSC0070_05() {
		return SC0070_05;
	}

	public boolean isSC0070_07() {
		return SC0070_07;
	}

	public void setSC0070_04(boolean sc0070_04) {
		SC0070_04 = sc0070_04;
	}

	public void setSC0070_05(String sc0070_05) {
		SC0070_05 = sc0070_05;
	}

	public void setSC0070_07(boolean sc0070_07) {
		SC0070_07 = sc0070_07;
	}

	public String getSC0070_05_HH() {
		return SC0070_05_HH;
	}

	public String getSC0070_05_MM() {
		return SC0070_05_MM;
	}

	public String getSC0070_06_HH() {
		return SC0070_06_HH;
	}

	public String getSC0070_06_MM() {
		return SC0070_06_MM;
	}

	public void setSC0070_05_HH(String sc0070_05_hh) {
		SC0070_05_HH = sc0070_05_hh;
	}

	public void setSC0070_05_MM(String sc0070_05_mm) {
		SC0070_05_MM = sc0070_05_mm;
	}

	public void setSC0070_06_HH(String sc0070_06_hh) {
		SC0070_06_HH = sc0070_06_hh;
	}

	public void setSC0070_06_MM(String sc0070_06_mm) {
		SC0070_06_MM = sc0070_06_mm;
	}

	public String getSC0070_09() {
		return SC0070_09;
	}

	public void setSC0070_09(String sc0070_09) {
		SC0070_09 = sc0070_09;
	}

		
}


