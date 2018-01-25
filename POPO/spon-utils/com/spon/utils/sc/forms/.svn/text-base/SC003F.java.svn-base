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

//import org.apache.struts.action.ActionError;
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
public class SC003F extends ActionForm {
	//當要用到Datagrid時，才需要這段程式
	//private Datagrid datagrid;
	
	
	private String SC0030_01;
	private String SC0030_02;
	private String SC0030_02_CHK;
	private String SC0030_03;
	private String SC0030_04;
	private String SC0030_05;
	private String SC0030_06;
	private String SC0030_07;
	private int SC0030_08;
	private String SC0030_09;
	private String SC0030_10;
	private String SC0030_11;
	private String SC0030_12;
	private String SC0030_14;
	
	private String LOGINMODE=""; //進入程式的方式 "key" 表示用USBKey進入,""表示透過瀏覽器進入
	
	private String HSC0030_01;
	private String HSC0030_03;
	private String HSC0030_04;
	
	
	private String DATE_CREATE;
	private String DATE_UPDATE;
	private String USER_CREATE;
	private String USER_UPDATE;
	private int VERSION;
	
	private String SC0030_EMPID;
	
	
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
		 ve.isEmpty(l_actionErrors, SC0030_01, "SC0030_01","不可空白");
		 //ve.isLeastLength(l_actionErrors, SC0030_01, "SC0030_01",5,"使用者代碼長度至少必須為 "+5+" 位");
		 ve.isLeastLength(l_actionErrors, SC0030_01, "SC0030_01",2,"使用者代碼長度至少必須為 "+2+" 位");
	 
		 if(!SC0030_02.equals(""))
		 {
			 ve.isEquals(l_actionErrors, SC0030_02, "SC0030_02",SC0030_02_CHK,"SC0030_02_CHK","密碼確認錯誤");
		 }
		 
	     ve.isEmpty(l_actionErrors, SC0030_03, "SC0030_03","不可空白");
	     ve.isEmpty(l_actionErrors, SC0030_07, "SC0030_07","不可空白");
	     //ve.isTWID(l_actionErrors,SC0030_03,"SC0030_03","身分證字號不正確"); 
	     ve.isEmpty(l_actionErrors, SC0030_04, "SC0030_04","不可空白");
	     ve.isEmpty(l_actionErrors, SC0030_10, "SC0030_10","不可空白");
	     ve.isEmail(l_actionErrors, SC0030_10, "SC0030_10","Email格式不正確");
	      
	     //判斷單位代號
	     /*try{
	     Statement stmt = conn.createStatement();
         String sql="select SC_UNITM_01 from SC_UNITM where SC_UNITM_01='"+SC0030_07+"'";
		 ResultSet rs=stmt.executeQuery(sql);
		 if(!rs.next())
		 {
			 l_actionErrors.add("SC0030_07",new ActionMessage("單位代碼錯誤!"));
		 }
		 
		 rs.close();
		 stmt.close();
		 
	     }catch (Exception e) {
	    	 e.printStackTrace();
		}*/
	    try{
	    	Statement stmt = conn.createStatement();
	        String sql="select EHF010100T6_01, EHF010100T6_02 " +
	        		   "from EHF010100T6 " +
	        		   "left join EHF010100T0 on EHF010100T0_01 = '"+SC0030_03+"' and EHF010100T0.HR_CompanySysNo = EHF010100T6.HR_CompanySysNo " +
	        		   "where EHF010100T6_02='"+SC0030_07+"' and EHF010100T6_01 = EHF010100T0_01 ";
			ResultSet rs=stmt.executeQuery(sql); 
			if(!rs.next())
			 {
				 l_actionErrors.add("SC0030_07",new ActionMessage("單位代碼錯誤!"));
			 }
			 
			 rs.close();
			 stmt.close();
	    }catch (Exception e) {
	    	e.printStackTrace();
		}
	     
	     //判斷一定要選擇一個群組
	     int chk_count=0;
	     for (int i = 0; i < SC0031.size(); i++) {
	    	 SC0031F sc0031f=(SC0031F)SC0031.get(i);
	    	 if(sc0031f.isCHECKED())
	    		 chk_count++;
	     }
	     if(chk_count<=0)
	     {
	    	 request.setAttribute("MSG","作業失敗  請最少選取一個群組!");
	    	 l_actionErrors.add("SC0030",new ActionMessage("請最少選取一個群組"));
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
		  if(conn!=null)
	        {
		        try {
		            Statement stmt = conn.createStatement();
		            String sql="select SC0030_01 from SC0030 where SC0030_01='"+SC0030_01+"' and  SC0030_14='"+SC0030_14+"'";
					ResultSet rs=stmt.executeQuery(sql);
		            if(rs.next())
		            {
		            	in_actionErrors.add("SC0030_01",new ActionMessage("鍵值重複") );
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
		        	 String sql="select * from SC0030 where SC0030_01='"+SC0030_01+"' and  SC0030_14='"+SC0030_14+"'";
		        	 ResultSet rs=stmt.executeQuery(sql);
			            if(rs.next())
			            {
			            	if(rs.getInt("VERSION")!=VERSION)
			            	{
			            		in_actionErrors.add("VERSION",new ActionMessage("資料版本不符") );
			            		SC003F Form=new SC003F();
			            		Form.setSC0030_01(rs.getString("SC0030_01"));
			    				Form.setSC0030_02(rs.getString("SC0030_02"));
			    				Form.setSC0030_03(rs.getString("SC0030_03"));
			    				Form.setSC0030_04(rs.getString("SC0030_04"));
			    				Form.setSC0030_05(rs.getString("SC0030_05"));
			    				Form.setSC0030_06(rs.getString("SC0030_06"));
			    				Form.setSC0030_07(rs.getString("SC0030_07"));
			    				Form.setSC0030_08(rs.getInt("SC0030_08"));
			    				Form.setSC0030_09(rs.getString("SC0030_09"));
			    				Form.setSC0030_10(rs.getString("SC0030_10"));
			    				Form.setSC0030_11(rs.getString("SC0030_11"));
			    				Form.setSC0030_12(rs.getString("SC0030_12"));
			    				 
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
			            	in_actionErrors.add("SC0030_01",new ActionMessage("資料已被刪除") );
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
	protected List SC0031  ;
	
	
	public List getSC0031() {
        return SC0031;
    }

	
	public void setSC0031(List  SC0031) {
        this.SC0031=SC0031;
     }

	
	public void setSC0031(SC0031F SC0031) {
        this.SC0031.add(SC0031);
    }
	

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {
		try {
			request.setCharacterEncoding("utf-8");
			 SC0031 = ListUtils.lazyList(new ArrayList(),new Factory() {
				            public Object create() {
				                return new SC0031F();
				            }
			 });
		} catch (Exception e) {
		}
	}

	public String getSC0030_01() {
		return SC0030_01;
	}

	public String getSC0030_02() {
		return SC0030_02;
	}

	public String getSC0030_03() {
		return SC0030_03;
	}

	public String getSC0030_04() {
		return SC0030_04;
	}

	
	public String getSC0030_06() {
		return SC0030_06;
	}

	public String getSC0030_07() {
		return SC0030_07;
	}

	public int getSC0030_08() {
		return SC0030_08;
	}

	public String getSC0030_09() {
		return SC0030_09;
	}

	public String getSC0030_10() {
		return SC0030_10;
	}

	public String getSC0030_11() {
		return SC0030_11;
	}

	public String getSC0030_12() {
		return SC0030_12;
	}
	
	public String getSC0030_14() {
		return SC0030_14;
	}

	public void setSC0030_01(String sc0030_01) {
		SC0030_01 = sc0030_01;
	}

	public void setSC0030_02(String sc0030_02) {
		SC0030_02 = sc0030_02;
	}

	public void setSC0030_03(String sc0030_03) {
		SC0030_03 = sc0030_03;
	}

	public void setSC0030_04(String sc0030_04) {
		SC0030_04 = sc0030_04;
	}

	

	public void setSC0030_06(String sc0030_06) {
		SC0030_06 = sc0030_06;
	}

	public void setSC0030_07(String sc0030_07) {
		SC0030_07 = sc0030_07;
	}

	public void setSC0030_08(int sc0030_08) {
		SC0030_08 = sc0030_08;
	}

	public void setSC0030_09(String sc0030_09) {
		SC0030_09 = sc0030_09;
	}

	public void setSC0030_10(String sc0030_10) {
		SC0030_10 = sc0030_10;
	}

	public void setSC0030_11(String sc0030_11) {
		SC0030_11 = sc0030_11;
	}

	public void setSC0030_12(String sc0030_12) {
		SC0030_12 = sc0030_12;
	}
	
	public void setSC0030_14(String sc0030_14) {
		SC0030_14 = sc0030_14;
	}

	public String getSC0030_05() {
		return SC0030_05;
	}

	public void setSC0030_05(String sc0030_05) {
		SC0030_05 = sc0030_05;
	}

	public String getSC0030_02_CHK() {
		return SC0030_02_CHK;
	}

	public void setSC0030_02_CHK(String sc0030_02_chk) {
		SC0030_02_CHK = sc0030_02_chk;
	}

	public String getHSC0030_01() {
		return HSC0030_01;
	}

	public String getHSC0030_03() {
		return HSC0030_03;
	}

	public String getHSC0030_04() {
		return HSC0030_04;
	}

	public void setHSC0030_01(String hsc0030_01) {
		HSC0030_01 = hsc0030_01;
	}

	public void setHSC0030_03(String hsc0030_03) {
		HSC0030_03 = hsc0030_03;
	}

	public void setHSC0030_04(String hsc0030_04) {
		HSC0030_04 = hsc0030_04;
	}

	
	/** 
	 * Method getLoginMode 
	 * @return LoginMode
	 * 取得進入系統的方式
	 * 如為 "key" 表示透過USBKEY進入
	 * 如為 "" 表示使用瀏覽器直接進入
	 */
	public String getLOGINMODE() {
		return LOGINMODE;
	}
	/** 
	 * Method getLoginMode 
	 * @param loginMode
	 * 設定進入系統的方式
	 * 如為 "key" 表示透過USBKEY進入
	 * 如為 "" 表示使用瀏覽器直接進入
	 */
	public void setLOGINMODE(String loginmode) {
		LOGINMODE = loginmode;
	}

	public void setSC0030_EMPID(String sC0030_EMPID) {
		SC0030_EMPID = sC0030_EMPID;
	}

	public String getSC0030_EMPID() {
		return SC0030_EMPID;
	}

	
	
}


