//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.actions.SC002A;
import com.spon.utils.sc.forms.SC002F;

/** 
 * MyEclipse Struts
 * Creation date: 04-06-2006
 * 
 * XDoclet definition:
 * @struts.action validate="true"
 */
public class BA_MENULISTA extends NewDispatchAction {

	// --------------------------------------------------------- Instance Variables
	String icon = "<img src=\"config/menu_img/menu_A01.gif\" onclick=\"return showEMSWait();\" />";
	String url = "";
	String target = "_self";
	ArrayList folderList = null;
	private String layout = "vbr"; //"vbr";   //hbr-橫式   vbr-直式
	// --------------------------------------------------------- Methods

	/** 
	 * Method execute
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward init(
		ActionMapping mapping,
		ActionForm form,
		HttpServletRequest request,
		HttpServletResponse response) {
		String html = getMenuList(request, userform(request).getSC0030_01(),userform(request).getLOGINMODE(), userform(request).getSC0030_14());
		// TODO Auto-generated method stub
		try {
			response.getWriter().println(html);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("BA_MENULISTA.execute()");
		}
		return mapping.findForward(null);
	}
	
	private Collection getmenudata(String node, VirTable table,HttpServletRequest request) {
		Collection data = new ArrayList();
		try {
			VirResultSet rs = table.query("SC0020_04=" + node + " and SC0020_08='"+userform(request).getSC0030_14()+"' order by SC0020_06");
			while (rs.next()){
				SC002F Form = new SC002F();
				Form.setSC0020_01(rs.getString("SC0020_01"));
				Form.setSC0020_02(rs.getString("SC0020_02"));
				Form.setSC0020_03(rs.getString("SC0020_03"));
				Form.setSC0020_04(rs.getString("SC0020_04"));
				Form.setSC0020_05(rs.getString("SC0020_05"));
				//Form.setSC0020_06(Integer.parseInt(rs.getString("SC0020_06")));
				data.add(Form);
			}
		} catch (Exception e) {
			System.out.println("Error MenulistDao.getmenudata:" + e);
		}
		return data;
	}

	private Collection getMenuDatas(Connection conn,HttpServletRequest request) {
		Collection data = new ArrayList();
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from SC0020 where SC0020_08='"+userform(request).getSC0030_14()+"'");
			while (rs.next()){
				SC002F Form = new SC002F();
				Form.setSC0020_01(rs.getString("SC0020_01"));
				Form.setSC0020_02(rs.getString("SC0020_02"));
				Form.setSC0020_03(rs.getString("SC0020_03"));
				Form.setSC0020_04(rs.getString("SC0020_04"));
				Form.setSC0020_05(rs.getString("SC0020_05"));
				Form.setSC0020_06(rs.getInt("SC0020_06"));
				data.add(Form);
			}
			new VirTable().putList(data);
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println("Error MenulistDao.getmenudata:" + e);
		}
		return data;
	}
	
	private Collection getmenudata(Connection conn, String node,HttpServletRequest request) {
		Collection data = new ArrayList();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt
					.executeQuery("select * from SC0020 where SC0020_04='"
							+ node + "' and SC0020_08='"+userform(request).getSC0030_14()+"' order by SC0020_06");
			while (rs.next()){
				SC002F Form = new SC002F();
				
				Form.setSC0020_01(rs.getString("SC0020_01"));
				Form.setSC0020_02(rs.getString("SC0020_02"));
				Form.setSC0020_03(rs.getString("SC0020_03"));
				Form.setSC0020_04(rs.getString("SC0020_04"));
				Form.setSC0020_05(rs.getString("SC0020_05"));
				Form.setSC0020_06(rs.getInt("SC0020_06"));
				data.add(Form);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			System.out.println("Error MenulistDao.getmenudata:" + e);
		}
		return data;
	}
	
	public String revmenu(Connection conn, String tree, ArrayList menuIdList, VirTable table,HttpServletRequest request) {
		//Add by John 2014/02/20
		//判斷語言並國際套用國際化Resource
		//若新增其他語言, 請記得SponMark.doGet()也要同時異動
		Locale languagetype = (Locale) request.getSession().getAttribute(Globals.LOCALE_KEY);
		ResourceBundle reesource = null;
		if(languagetype.toString().equals("zh_TW")){
			reesource = ResourceBundle.getBundle("EMSResources_zh_TW");
		}else if(languagetype.toString().equals("zh_CN")){
			reesource = ResourceBundle.getBundle("EMSResources_zh_CN");
		}else if(languagetype.toString().equals("en_US")){
			reesource = ResourceBundle.getBundle("EMSResources_en");
		}else if(languagetype.toString().equals("ja_JP")){
			reesource = ResourceBundle.getBundle("EMSResources_ja");
		}else{
			reesource = ResourceBundle.getBundle("EMSResources");
		}
		
		StringBuffer s = new StringBuffer();
		Iterator iterator = getmenudata(conn, tree, request).iterator();
		SC002F bean = new SC002F();
		
		while (iterator.hasNext()) {
			bean = (SC002F)iterator.next();
			
			//Add by John 2014/02/20
			//從EMSResource取得國際化程式名稱
			//若新增其他語言, 請記得SponMark.doGet()也要同時異動
			String SC0020_02 = reesource.getString("pgm."+bean.getSC0020_01()+".name");

			String a = "";
			
			String temp = "";
			boolean hasChild = false;
			int ChildCount=0;
			String link = "";
			String icon2 = "";
			if (menuIdList.contains(bean.getSC0020_01()) || folderList.contains(bean.getSC0020_01())){
				if (bean.getSC0020_03().equals("FOLDER")){
					link = "FOLDER";
					icon2 = "<img src=\"config/menu_img/folder.gif\"/>";
				}else{
					link = bean.getSC0020_03()+"?jsession="+new Date().getTime();
					icon2 = icon;
				}
//				s.append("['" + icon2 + "','" + bean.getSC0020_02() + "','"
				s.append("['" + icon2 + "','" + SC0020_02 + "','"
						+ link + "','" + target + "','"
						+ bean.getSC0020_05() + "'");
				a = revmenu(conn,bean.getSC0020_01(), menuIdList, table,request);
				if (!a.trim().equals("")) {
					s.append("," + a);
					s.append("],");
				} else {
					s.append("],");
				}
			}
		}

		return s.toString();
	}
	
	public String getMenuList(HttpServletRequest request, String SC0030_01,String LOGINMODE, String SC0030_14){
		//DataSource ds = null;
		StringBuffer s = new StringBuffer();
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		if(request.getSession().getAttribute("MENULIST")==null)
		{
		try {
			//ds = getDataSource(request, "SPOS");

			//conn = ds.getConnection();
			conn = tools.getConnection();

			
			SC002A sc002a = new SC002A();
			ArrayList pgmList = sc002a.showPgmListForUser(conn, null, request, SC0030_01,LOGINMODE, SC0030_14);
			folderList = sc002a.showFolderListForUser(conn, null, request, SC0030_01,LOGINMODE, SC0030_14);
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("select * from SC0020 where SC0020_08='"+userform(request).getSC0030_14()+"'");
			VirTable table = new VirTable();
			table.putList(rs);
			
			Iterator itePgmList = pgmList.iterator();
			String temp = "";
			boolean hasChild = false;
			int ChildCount=0;
			ArrayList menuIdList = new ArrayList();
			while (itePgmList.hasNext()){
				SC002F sc002f = (SC002F)itePgmList.next();
				menuIdList.add(sc002f.getSC0020_01());
				//System.out.println(sc002f.getSC0020_01());
			}
			
			//移除 <!-- Begin ... End -->，避免google chrome不支援
//			s.append("<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"config/JSCookMenu.js\"></SCRIPT>" +
//					"<LINK REL=\"stylesheet\" HREF=\"config/theme.css\" TYPE=\"text/css\">" +
//					"<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"config/theme.js\"></SCRIPT>" +
//					"<SCRIPT LANGUAGE=\"JavaScript\"> \r\n <!-- Begin \r\n	 var myMenu=[");
//			s.append(revmenu(conn, "ROOT", menuIdList, table,request));
//			s.append("]; \r\n End --></SCRIPT><DIV align=\"left\" ID=myMenuID2>您未被授權!</DIV><SCRIPT LANGUAGE=\"JavaScript\">cmDraw ('myMenuID2', myMenu, '"
//					+ layout + "', cmThemeOffice2003, 'ThemeOffice2003');</SCRIPT>");
			
			s.append("<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"config/JSCookMenu.js\"></SCRIPT>" +
					"<LINK REL=\"stylesheet\" HREF=\"config/theme.css\" TYPE=\"text/css\">" +
					"<SCRIPT LANGUAGE=\"JavaScript\" SRC=\"config/theme.js\"></SCRIPT>" +
					"<SCRIPT LANGUAGE=\"JavaScript\"> \r\n 	 var myMenu=[");
			s.append(revmenu(conn, "ROOT", menuIdList, table,request));
			s.append("]; \r\n </SCRIPT><DIV style=\"position:absolute; top:150px; left:0px; z-index:9999; height:50px;\" align=\"left\" ID=myMenuID2>您未被授權!</DIV><SCRIPT LANGUAGE=\"JavaScript\">cmDraw ('myMenuID2', myMenu, '"
					+ layout + "', cmThemeOffice2003, 'ThemeOffice2003');</SCRIPT>");
			
			request.getSession().setAttribute("MENULIST", s.toString());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("BA_MENULISTA.getMenuList()");
			e.printStackTrace();
		}
		finally
		{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}
		else
		{
			s.append(request.getSession().getAttribute("MENULIST")+"");
		}
		
		return s.toString();
	}
}

