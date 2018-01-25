package com.spon.struts;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.spon.utils.sc.actions.SC005A;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.sc.forms.SC004F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;

public class NewDispatchAction extends DispatchAction {

	
	public ActionForward execute(ActionMapping in_mapping, ActionForm in_form, HttpServletRequest request, HttpServletResponse in_response) throws Exception {
		String sqlunit=" <> <~> <~> "; //瀏覽使用的單位判斷條件值 
		String lc_parameterName = in_mapping.getParameter();
		String path = in_mapping.getPath();
		
		// 判斷是否有此程式權限，如沒有就 getout
		String ActionName = path.replaceAll("/", "");
		String reqCode = request.getParameter("reqCode");
		HashMap pgms = new HashMap();
		
		//若要轉換語言，則直接導向LanguageSelect.do
		if(path.equals("/Locale") 
//				&&(
//				   "traditionalchinese".equals(reqCode) ||
//				   "simplechinese".equals(reqCode) ||
//				   "english".equals(reqCode) ||
//				   "japanese".equals(reqCode)
//				)
		){
			return super.execute(in_mapping, in_form, request, in_response);
		}
		
		pgms = (HashMap) request.getSession().getAttribute("pgmlist");
		// 判斷有在程式清單裡面的 才要檢何權限
		if (pgms != null) {
			if (pgms.get(ActionName) != null)
				if (request.getSession().getAttribute("pgmsauth") != null) {
					request.setAttribute("ActionName", ActionName + ".do");
					pgms = (HashMap) request.getSession().getAttribute("pgmsauth");
					// 判斷可否進入
					int[] auths = (int[]) pgms.get(ActionName);
					if (auths == null)
						return in_mapping.findForward("getout");
					else if (reqCode != null) {
						if (reqCode.indexOf("add") > -1) {// 新增
							if (auths[0] == 0)
								return in_mapping.findForward("getout");
						} else if (reqCode.indexOf("del") > -1) {// 刪除
							if (auths[1] == 0)
								return in_mapping.findForward("getout");
						} else if (reqCode.indexOf("edit") > -1) {// 修改
							if (auths[2] == 0)
								return in_mapping.findForward("getout");
						} else if (reqCode.indexOf("query") > -1) {// 查詢
							if (auths[03] == 0)
								return in_mapping.findForward("getout");
						} else if (reqCode.indexOf("print") > -1) {// 列印
							if (auths[4] == 0)
								return in_mapping.findForward("getout");
						}

					}
				}

			
//			 判斷是否有login且action不是getout，否則會進入無窮迴圈
			if (!isLogin(request) && !path.equals("/login") && !path.equals("/mobile_login") && !lc_parameterName.equals("loginScreen")) {
				return in_mapping.findForward("getout");
			}
			
			//放入登入者的名子
			request.setAttribute("loginid", userform(request).getSC0030_01());
			request.setAttribute("loginname", userform(request).getSC0030_04());
			request.setAttribute("compid", userform(request).getSC0030_14());
//			request.getSession().setAttribute("compid12345", userform(request).getSC0030_14()); //測試session使用
						
			//放入程式代號
			request.setAttribute("pgmname", ActionName);
			
			// 製作單位的權限
			String UNIT_AUTH = "";
			if (userform(request).getSC0030_07() != null) {
				if (userform(request).getSC0030_07().equals("99")) {
					//串上 <> ''
					sqlunit = "<><~> <~>";
				} else if (userform(request).getSC0030_07().length() == 2) {
					UNIT_AUTH = " and SC_UNITM_01 like '" + userform(request).getSC0030_07() + "%' ";
					sqlunit = " like <~>" + userform(request).getSC0030_07() + "<^><~>";
				} else {
					UNIT_AUTH = " and SC_UNITM_01 = '" + userform(request).getSC0030_07() + "' ";
					sqlunit = "=<~>" + userform(request).getSC0030_07() + "<~>";
				}
				if (!userform(request).getSC0030_07().equals("99"))
					request.setAttribute("ID_OTHERS", " and SA_SALAM_A like '" + userform(request).getSC0030_07()+"%'");
			}	
			request.setAttribute("UNIT_OTHERS", UNIT_AUTH);
			
		}
		else
		{
//			 判斷是否有login且action不是getout，否則會進入無窮迴圈
			if (!isLogin(request) && !path.equals("/login") && !path.equals("/mobile_login") && !lc_parameterName.equals("loginScreen")) {
				return in_mapping.findForward("getout");
			}
		}
		
		
		// 如果是顯示選單的話，就直接呼叫init即可
		if (path.equals("/MENULIST")) {
			
			return dispatchMethod(in_mapping, in_form, request, in_response, "init");
		}

		if (lc_parameterName != null) {
			String lc_methodName = request.getParameter(lc_parameterName);

			if (lc_methodName != null) {
				if ("execute".equals(lc_methodName) || "perform".equals(lc_methodName)) {
					throw new IllegalArgumentException("illegal parameter");
				}
			} else {
				// 如果沒有指定reqCode就預設呼叫init method
				return dispatchMethod(in_mapping, in_form, request, in_response, "init");
			}

		}
//		System.out.println(ActionName+" "+reqCode);
		
		if (isLogin(request))
				Log_Pgm_run_status(request,ActionName,reqCode);
		
		return super.execute(in_mapping, in_form, request, in_response);
	}

	private void Log_Pgm_run_status(HttpServletRequest request, String actionName, String reqCode) {
	
	BA_TOOLS tools = BA_TOOLS.getInstance();
	Connection conn = null;
	
	try {
		//DataSource ds = getDataSource(request, "SPOS");
		//conn = ds.getConnection();
		
		conn = tools.getConnection();
		
		Statement stmt = conn.createStatement();
		//stmt.execute("INSERT INTO PGM_LOG(CLASS, METHED, DATA_CREATE, USER_CREATE)     VALUES('"+actionName+"', '"+reqCode+"', NOW(), '"+userform(request).getSC0030_01()+"') ");
		
		stmt.close();
		conn.commit();
	} catch (Exception e) {
		e.printStackTrace();
		try {
			conn.rollback();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	} finally {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
		
	}

	/**
	 * 產生下拉選單的選項(資料來源不是SC_CODEM)
	 * 以SQL語法查詢選項
	 * 
	 * @param conn
	 *            資料庫連結物件
	 * @param sql
	 *            查詢字串
	 * @param emptyOption
	 *            是否要有一個空白的選項 true: 要 false: 不要
	 */
	public List getSelectOptions(Connection conn, String sql, boolean emptyOption) {
		List datas = new ArrayList();
		try {
			if (conn != null){
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if (emptyOption ) {
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id("");
					bform.setItem_value("");
					datas.add(bform);
				}
				while (rs.next()) {
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id(rs.getString("item_id"));
					bform.setItem_value(rs.getString("item_value"));
					datas.add(bform);
				}
				rs.close();
				stmt.close(); 
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("NewDispatchAction.getSelectOptions()" + e +sql);
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 產生下拉選單的選項(代碼資料表 FM010501 專用)
	 * 以FM010501_01語法查詢選項,程式帶入
	 * select FM010501_03 as item_id,FM010501_04 as item_value from FM010501 where FM010501_01='FM010501_01'
	 * @param conn
	 *            資料庫連結物件
	 * @param emptyOption
	 *            是否要有一個空白的選項 true: 要 false: 不要	 *            
	 * @param SC_CODEM.SC_CODEM_01
            
	 */
	public List getSelectOptions(Connection conn, boolean emptyOption, String FM010501_01) {
		List datas = new ArrayList();
		try {
			if (conn != null){
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT FM010501_03 AS ITEM_ID,FM010501_04 AS ITEM_VALUE FROM FM010501 WHERE FM010501_01='" + FM010501_01 + "' ORDER BY FM010501_03 ");
				if (emptyOption ) {
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id("");
					bform.setItem_value("");
					datas.add(bform);
				}
				while (rs.next()) {
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id(rs.getString("ITEM_ID"));
					bform.setItem_value(rs.getString("ITEM_VALUE"));
					datas.add(bform);
				}
				rs.close();
				stmt.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("NewDispatchAction.getSelectOptions()" + e +"select FM010501_03 as item_id,FM010501_04 as item_value from FM010501 where FM010501_01='" + FM010501_01 + "'");
			e.printStackTrace();
		}
		return datas;
	}
	
	/**
	 * 產生下拉選單的選項(代碼資料表 FM010501 專用)
	 * 以FM010501_01語法查詢選項,程式帶入
	 * select FM010501_03 as item_id,FM010501_04 as item_value from FM010501 where FM010501_01='FM010501_01'
	 * @param conn
	 *            資料庫連結物件
	 * @param emptyOption
	 *            是否要有一個空白的選項 true: 要 false: 不要	 *            
	 * @param SC_CODEM.SC_CODEM_01
            
	 */
	public List getSelectOptions(Connection conn, boolean emptyOption, String FM010501_01,String other) {
		List datas = new ArrayList();
		try {
			if (conn != null){
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT FM010501_03 AS ITEM_ID,FM010501_04 as ITEM_VALUE FROM FM010501 WHERE FM010501_01='" + FM010501_01 + "' " + other + " ORDER BY FM010501_03 ");
				if (emptyOption ) {
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id("");
					bform.setItem_value("");
					datas.add(bform);
				}
				while (rs.next()) {
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id(rs.getString("ITEM_ID"));
					bform.setItem_value(rs.getString("ITEM_VALUE"));
					datas.add(bform);
				}
				rs.close();
				stmt.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("NewDispatchAction.getSelectOptions()" + e +"select FM010501_03 as item_id,FM010501_04 as item_value from SC_CODEM where FM010501='" + FM010501_01 + "'");
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 取得目前的序號並回傳，增加1會回存資料庫
	 * 
	 * @param conn
	 * @param table
	 *            table 名稱
	 * @param column
	 *            column名稱
	 * @param format
	 *            傳入"000000"且序號是1話，就回傳"000001" 如果傳入""，則回傳"1"
	 */
	public String getSequence_test(Connection conn, String table, String column, String format) {
		SC005A form = new SC005A();
		return form.getSequence_test(conn, table, column, format);
	}
	/**
	 * 取得目前的序號並回傳，增加1會回存資料庫
	 * 
	 * @param conn
	 * @param table
	 *            table 名稱
	 * @param column
	 *            column名稱
	 * @param format
	 *            傳入"000000"且序號是1話，就回傳"000001" 如果傳入""，則回傳"1"
	 */
	public String getSequence(Connection conn, String table, String column, String format,HttpServletRequest request) {
		SC005A form = new SC005A();
		return form.getSequence(conn, table, column, format,request);
	}
	
	/**
	 * 判斷使用者是否有登入，方法： 檢查session中是否有userform，如果有的話就將userform放到Member varialbes
	 * 如果沒有的話，就回傳false
	 * 
	 * @param request
	 * @return
	 */
	public boolean isLogin(HttpServletRequest request) {
		boolean check = false;
		try {
			if (request.getSession().getAttribute("userform") == null) {
				check = false;
			} else {
				check = true;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("NewDispatchAction.isLogin()" + e);
			check = false;
		}
		return check;
	}

	/**
	 * 跳出LOV視窗
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward popup(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		ArrayList list = new ArrayList();
		for (int i = 0; i < 5; i++) {
			BA_ALLKINDForm formbean = new BA_ALLKINDForm();
			formbean.setItem_id("" + i);
			formbean.setItem_value("" + i * 100);
			list.add(formbean);
		}
		SC004F formbean = new SC004F();
		formbean.setSC0040_01("1");
		request.setAttribute("FormDatas", formbean);
		request.setAttribute("subjects", list);
		return mapping.findForward("popup");
	}

	public List getRPT_TYPE(String[][] report_type) {
		List list = new ArrayList();

		BA_ALLKINDForm bform;
		for (int i = 0; i < report_type.length; i++) {

			bform = new BA_ALLKINDForm();
			bform.setItem_id(report_type[i][0]);
			bform.setItem_value(report_type[i][1]);
			list.add(bform);

		}

		return list;
	}

	public String getDate() {
		DecimalFormat df = new DecimalFormat("00");
		DecimalFormat dfy = new DecimalFormat("000");
		Calendar c = Calendar.getInstance();
		int nYear = c.get(Calendar.YEAR) - 1911;
		int nMonth = c.get(Calendar.MONTH);
		int nDay = c.get(Calendar.DAY_OF_MONTH);

		return dfy.format(nYear) + df.format(nMonth + 1) + df.format(nDay);
	}

	public String getTime() {
		DecimalFormat df = new DecimalFormat("00");
		Calendar c = Calendar.getInstance();
		int nHour = c.get(Calendar.HOUR_OF_DAY);
		int nMin = c.get(Calendar.MINUTE);
		int nSec = c.get(Calendar.SECOND);

		return df.format(nHour) + ":" + df.format(nMin) + ":" + df.format(nSec);
	}

	public SC003F userform(HttpServletRequest request)
	{
		if(request.getSession().getAttribute("userform")==null)
			return null;
		else
			return  (SC003F) request.getSession().getAttribute("userform");
	}
	
	public String sqlunit(HttpServletRequest request)
	{
		String sqlunit="";
	if (userform(request).getSC0030_07() != null) {
		if (userform(request).getSC0030_07().equals("99")) {
			//串上 <> ''
			sqlunit = "<><~> <~>";
		} else if (userform(request).getSC0030_07().length() == 2) {
			
			sqlunit = " like <~>" + userform(request).getSC0030_07() + "<^><~>";
		} else {
			
			sqlunit = "=<~>" + userform(request).getSC0030_07() + "<~>";
		}
		if (!userform(request).getSC0030_07().equals("99"))
			request.setAttribute("ID_OTHERS", " and SA_SALAM_A like '" + userform(request).getSC0030_07()+"%'");
	}
	return sqlunit;
	}
	
	public ActionForward exceldownload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		File file = new File(request.getParameter("filename"));
	
		response.setHeader("Content-Disposition", "attachment;filename="+file.getName());
		
		 if (file.exists()) {
		      MimetypesFileTypeMap MIME_TYPES=new MimetypesFileTypeMap();
			//使用MimetypesFileTypeMap 自行以副檔名回覆相對應的MIME_TYPE
		      response.setContentType(MIME_TYPES.getContentType(file.getName()));
		      try {
		  		IOUtils.copy(new FileInputStream(file), response.getOutputStream());
		  		response.setStatus(response.SC_OK);
		  	    response.flushBuffer();
		  	    response=null;
		  	} catch (Exception e) {
		  		// TODO Auto-generated catch block
//		  		e.printStackTrace();
		  	}
		      
		 }
		
		return null;
	}
	public String getCompanyData(Connection conn,  String FieldName) {
		String datas = "";
		try {
			if (conn != null){
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT "+FieldName+" AS DATAS FROM FM010001  ");
				if (rs.next()) {
					datas=rs.getString("DATAS");
				}
				rs.close();
				stmt.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("NewDispatchAction.getCompanyData()" + e +"  SELECT "+FieldName+" AS DATAS FROM FM010001 ");
			e.printStackTrace();
		}
		return datas;
	}
	
}
