//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.sc.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

//import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.ActionMessage;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.utils.sc.actions.SC003A;
import com.spon.utils.util.BA_TOOLS;

/**
 * MyEclipse Struts Creation date: 04-07-2006
 * 
 * XDoclet definition:
 * 
 * @struts.form name="LoginForm"
 */
public class LoginForm extends ActionForm {

	// --------------------------------------------------------- Instance
	// Variables

	/** password property */
	private String password;

	/** userid property */
	private String userid;
	
	/** userid property */
	private String compid;

	/** submit property */
	private String submit;

	private String chacha;
	
	// --------------------------------------------------------- Methods

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method reset
	 * 
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	/**
	 * Returns the password.
	 * 
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password.
	 * 
	 * @param password
	 *            The password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns the userid.
	 * 
	 * @return String
	 */
	public String getUserid() {
		return userid;
	}
	
	public String getCompid() {
		return compid;
	}

	/**
	 * Set the submit.
	 * 
	 * @param userid
	 *            The userid to set
	 */
	public void setSubmit(String submit) {
		this.submit = submit;
	}

	/**
	 * Returns the submit.
	 * 
	 * @return String
	 */
	public String getSubmit() {
		return submit;
	}

	/**
	 * Set the userid.
	 * 
	 * @param userid
	 *            The userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	public void setCompid(String compid) {
		this.compid = compid;
	}

	public String getChacha() {
		return chacha;
	}

	public void setChacha(String chacha) {
		this.chacha = chacha;
	}

	/**
	 * Method validate
	 * 
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		// 檢查不可為空白
		isEmpty(l_actionErrors, compid, "compid");
		isEmpty(l_actionErrors, userid, "userid");
		isEmpty(l_actionErrors, password, "password");
		if (l_actionErrors.isEmpty()) {
			try {
				PreparedStatement pstmt = 
					conn.prepareStatement("select SC0030.*, EHF010100T0_01 from SC0030 " +
										  "LEFT JOIN EHF010100T0 ON EHF010100T0_01 = SC0030_03 AND HR_CompanySysNo = SC0030_14 AND EHF010100T0_I != '' " +
										  "where SC0030_01=? and SC0030_14='"+compid+"'");
				pstmt.setString(1, userid);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					//判斷是否啟用
					if ("Y".equals(rs.getString("SC0030_05"))) {
						//判斷登入錯誤次數

						SC003A sc003a = new SC003A();
						if (sc003a.getLoginFail(conn, this, request) <= 3) {
							// 密碼如果相等
							if (BA_TOOLS.chkTextInMD5(password, rs.getString("SC0030_02"))) {
								SC003F userform = new SC003F();
								//這一段是用來判斷使用者是否使用KEY登入
								//如果SC0030_13有值的話，代表該使用者需要用到KEY才可以登入
								//chacha是前端Submit過來的資料，須與SC0030_13做比對
								if (rs.getString("SC0030_13") != null && !rs.getString("SC0030_13").equals("")){
									if (chacha == null || !chacha.equals(rs.getString("SC0030_13"))){
										//比對錯誤當成使用者使用瀏覽器登入
										userform.setLOGINMODE("");
									}else{
//										比對成功使用者使用Key登入
										userform.setLOGINMODE("key");
									}
								}else{
//									使用者使用瀏覽器登入
									userform.setLOGINMODE("");
								}
								userform.setSC0030_01(rs.getString("SC0030_01"));
								userform.setSC0030_02(rs.getString("SC0030_02"));
								userform.setSC0030_03(rs.getString("SC0030_03"));
								userform.setSC0030_04(rs.getString("SC0030_04"));
								userform.setSC0030_05(rs.getString("SC0030_05"));
								userform.setSC0030_06(rs.getString("SC0030_06"));
								userform.setSC0030_07(rs.getString("SC0030_07"));
								userform.setSC0030_08(rs.getInt("SC0030_08"));
								userform.setSC0030_09(rs.getString("SC0030_09"));
								userform.setSC0030_10(rs.getString("SC0030_10"));
								userform.setSC0030_11(rs.getString("SC0030_11"));
								userform.setSC0030_12(rs.getString("SC0030_12"));
								userform.setSC0030_14(rs.getString("SC0030_14"));
								userform.setSC0030_EMPID(rs.getString("EHF010100T0_01")==null?"":rs.getString("EHF010100T0_01"));
								
								//因為整合EMS系統中的Struts虛擬框架, 必須要新增Session資訊使框架能正常運作, edit by joe 2012/09/07
								//建立 AuthBean 物件
								AuthorizedBean login_authBean = new AuthorizedBean();
								login_authBean.setEmployeeID(userform.getSC0030_EMPID());	//員工系統代碼
								login_authBean.setUserId(userform.getSC0030_01());	//登入者帳號
								login_authBean.setUserCode(userform.getSC0030_01());	//登入者帳號
								login_authBean.setUserName(userform.getSC0030_04());	//登入者姓名
								login_authBean.setCompId(userform.getSC0030_14());	//公司代碼
								
								// 將使用者資訊存入session中
								request.getSession().setAttribute("userform", userform);
								request.getSession().setAttribute("pgmsauth", showPgmListAndAuths(userform.getSC0030_01(), conn,userform.getLOGINMODE()));
								
								//將 AuthBean資訊存入Session中, edit by joe 2012/09/07
								request.getSession().setAttribute("auth", login_authBean);
								
								//所有程式清單 
								request.getSession().setAttribute("pgmlist", showPgmList(conn));
								
								sc003a.setLoginSuccess(conn, this, request);
							} else {
								l_actionErrors.add("password", new ActionMessage("密碼錯誤"));
								sc003a.setLoginFail(conn, this, request);
								conn.commit();
							}
						} else {
							l_actionErrors.add("userid", new ActionMessage("帳號已被鎖定，請洽系統管理員"));
						}
					} else {
						l_actionErrors.add("userid", new ActionMessage("帳號已被停用，請洽系統管理員"));
					}
				}
				
				rs.close();
				pstmt.close();
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("LoginForm.validate():" + e);
			}
		}
		return l_actionErrors;
	}

	/**
	 * 取得使用者可使用程式的個別權限組合，結構如下
	 * HashMap --> "SC0011_02", auths[]{0, 0, 0, 0, 0}
	 * 		"SC0011_02" 表示程式代碼，是字串型態
	 * 		auths[]的陣列元素 0 到 4分別代表 增刪改查印
	 * 		0:沒有權限 1:有權限
	 * @param form
	 * @param conn
	 * @param LOGINMODE //使用者登入方式 "key" 使用USBKEy "" 直接登入
	 * @return
	 */
	public HashMap showPgmListAndAuths(String SC0030_01, Connection conn,String LOGINMODE) {
		HashMap pgms = new HashMap();
		String sql = "";
		if (LOGINMODE.equals("key")){
			sql = "select d.SC0011_02, sum(d.SC0011_03) as AUTH_ADD," + " sum(d.SC0011_04) as AUTH_DEL," + " sum(d.SC0011_05) as AUTH_MODIFY," + " sum(d.SC0011_06) as AUTH_QUERY," + " sum(d.SC0011_07) as AUTH_PRINT"
				+ " from SC0030 a" 
				+ " left join SC0031 b on a.SC0030_01 = b.SC0031_01 and b.SC0031_04 = a.SC0030_14 " 
				+ " left join SC0010 c on c.SC0010_01 = b.SC0031_02 and c.SC0010_04 = a.SC0030_14 " 
				+ " left join SC0011 d on d.SC0011_01 = c.SC0010_01 and d.SC0011_09 = a.SC0030_14 " 
				+ " where a.SC0030_01 = '"
				+ SC0030_01 + "' "
				+ " and a.SC0030_14 = '"+compid+"' "   //公司別條件
				+ " group by d.SC0011_02";
		}else{
			sql = "select d.SC0011_02, sum(d.SC0011_03) as AUTH_ADD," + " sum(d.SC0011_04) as AUTH_DEL," + " sum(d.SC0011_05) as AUTH_MODIFY," + " sum(d.SC0011_06) as AUTH_QUERY," + " sum(d.SC0011_07) as AUTH_PRINT"
			+ " from SC0030 a" 
			+ " left join SC0031 b on a.SC0030_01 = b.SC0031_01 and b.SC0031_04 = a.SC0030_14 " 
			+ " left join SC0010 c on c.SC0010_01 = b.SC0031_02 and c.SC0010_04 = a.SC0030_14 " 
			+ " left join SC0011 d on d.SC0011_01 = c.SC0010_01 and d.SC0011_09 = a.SC0030_14 "
			+ " left join SC0020 e on d.SC0011_02 = e.SC0020_01 and e.SC0020_08 = a.SC0030_14 "
			+ " where e.SC0020_07='N' and  a.SC0030_01 = '"
			+ SC0030_01 + "' "
			+ " and a.SC0030_14 = '"+compid+"' "   //公司別條件
			+ " group by d.SC0011_02";
		}
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				int[] auths = { 0, 0, 0, 0, 0 };
				auths[0] = (rs.getInt("AUTH_ADD") > 0) ? 1 : 0;
				auths[1] = (rs.getInt("AUTH_DEL") > 0) ? 1 : 0;
				auths[2] = (rs.getInt("AUTH_MODIFY") > 0) ? 1 : 0;
				auths[3] = (rs.getInt("AUTH_QUERY") > 0) ? 1 : 0;
				auths[4] = (rs.getInt("AUTH_PRINT") > 0) ? 1 : 0;
				pgms.put(rs.getString("SC0011_02"), auths);
			}
		} catch (Exception e) {
			System.out.println("LoginForm.showPgmListAndAuths():" + e);
		}
		return pgms;
	}

	public HashMap showPgmList(Connection conn) {
		HashMap pgms = new HashMap();
		String sql = " select SC0020_01 from SC0020 " +
					 " where 1=1 " +
					 " and SC0020_08 = '"+compid+"' ";  //公司別條件
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				pgms.put(rs.getString("SC0020_01"),"true");
			}
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println("LoginForm.showPgmList():" + e);
		}
		return pgms;
	}
	
	/**
	 * 判斷欄位值是否有輸入
	 * 
	 * @param in_actionErrors
	 * @param in_parameter
	 * @param in_nameParameter
	 */
	public void isEmpty(ActionErrors in_actionErrors, String in_parameter, String in_nameParameter) {
		if (in_parameter == null || in_parameter.equals("")) {
			in_actionErrors.add(in_nameParameter, new ActionMessage("不可空白"));
		}
	}
}
