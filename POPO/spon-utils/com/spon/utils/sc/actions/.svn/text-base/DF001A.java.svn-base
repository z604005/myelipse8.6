package com.spon.utils.sc.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.SC006F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.BA_Detail_Attr;
import com.spon.utils.struts.form.BA_Detail_Head_Attr;
import com.spon.utils.util.BA_DATAGRID;
import com.spon.utils.util.BA_TOOLS;



/**
 * 使用者管理
 * 
 * @version 1.0
 */
public class DF001A extends NewDispatchAction {

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 一開始進來程式時顯示空的資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		generateSelectBox(null, form, request);
		
		return mapping.findForward("success");
	}

	public ActionForward Detail_cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		response.addHeader("Content-Type", "text/xml;charset=utf-8");
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		StringBuffer remsg=new StringBuffer();
		String con = request.getParameter("content");
		String actionCode = request.getParameter("actionCode");
		String[] contents = con.split("\\{;\\}");
		if(actionCode.equals("new"))
		{
			//將值清除 丟回前端
			contents[0]="";
			contents[1]="";
			contents[2]="";
			contents[3]="";
			contents[4]="";
			contents[5]="";
			contents[6]="";
			
			for (int i = 0; i < contents.length; i++) {
				remsg.append(contents[i]);
				remsg.append("{;}");
			}
			
			
			try {
				sendtoclient(remsg.toString(),response);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
		{
		String sql="select SC0060_01,SC0060_02,SC0060_03,SC0060_04,VERSION,'' as 程式代碼,'' as 程式名稱 from SC0060 where SC0060_01=?";
		try {
			conn = tools.getConnection();
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1,contents[0].trim());
			
			ResultSet rs=stmt.executeQuery();
			ResultSetMetaData rsm=rs.getMetaData();
			if(rs.next())
			{
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					remsg.append(rs.getString(i)==null?"":rs.getString(i));
					remsg.append("{;}");
				}
				
				
				sendtoclient(remsg.toString(),response);
				
			}
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
			try {
				
				sendtoclient(remsg.toString(),response);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}
		return null;
	}
	
	public ActionForward Detail_save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		response.addHeader("Content-Type", "text/xml;charset=utf-8");
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		String con = request.getParameter("content");
		String[] contents = con.split("\\{;\\}");

		try {

			conn = tools.getConnection();

			SC006F Form = new SC006F();
			Form.setSC0060_01(contents[0].trim());
			Form.setSC0060_02(contents[1].trim());
			Form.setSC0060_03(contents[2].trim());
			Form.setSC0060_04(contents[3].trim());
			if(!contents[4].trim().equals(""))
				Form.setVERSION(Integer.parseInt(contents[4].trim()));

			// 處裡資料
			if (request.getParameter("actionCode").equals("insert")) {
				// 新增
				
				
				sendtoclient(Detail_insert(mapping, Form, request, conn),response);
				
				
			} else if (request.getParameter("actionCode").equals("update")) {
				// 修改
				
				
				sendtoclient(Detail_upadte(mapping, Form, request, conn),response);
				
				
			} else if (request.getParameter("actionCode").equals("delete")) {
				// 刪除
				
				sendtoclient(Detail_delete(mapping, Form, request, conn),response);
				
			}

		} catch (Exception e) {
			e.printStackTrace();
			try {
				
				sendtoclient(e.toString(),response);
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private String Detail_insert(ActionMapping mapping, ActionForm form, HttpServletRequest request, Connection conn) {

		request.setAttribute("action", "addData");
		SC006F Form = (SC006F) form;
		ActionErrors l_actionErrors = ((SC006F) Form).validate(mapping, request, conn);
		String msg = "";
		if (l_actionErrors.isEmpty()) {
			String sql = "insert into SC0060 (" + "SC0060_01," + "SC0060_02," + "SC0060_03," + "SC0060_04," + "DATE_CREATE," + "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE,"
					+ "VERSION) values (?,?,?,?,NOW(),NOW(),?,?,?)";

			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(sql);

				P6PreparedStatement p6stmt = new P6PreparedStatement(null, stmt, null, sql);

				p6stmt.setString(1, Form.getSC0060_01());
				p6stmt.setString(2, Form.getSC0060_02());
				p6stmt.setString(3, Form.getSC0060_03());
				p6stmt.setString(4, Form.getSC0060_04());

				p6stmt.setString(5, userform(request).getSC0030_01());
				p6stmt.setString(6, userform(request).getSC0030_01());

				p6stmt.setInt(7, 1);

				// 未來要LOG
				// System.out.println(p6stmt.getQueryFromPreparedStatement());

				stmt.execute();
				p6stmt.close();
				stmt.close();

				conn.commit();
			} catch (SQLException e) {
				e.printStackTrace();
				msg=e.toString();
			}
			msg = "ok";
		} else {
			Iterator it = l_actionErrors.get();
			while (it.hasNext()) {
				msg += ((ActionMessage) it.next()).getKey() + "\r\n";
			}
		}
		return msg;
	}

	private String Detail_upadte(ActionMapping mapping, ActionForm form, HttpServletRequest request, Connection conn) {
		request.setAttribute("action", "saveData");
		String msg = "";
		SC006F Form = (SC006F) form;
		ActionErrors l_actionErrors = Form.validate(mapping, request, conn);
		if (l_actionErrors.isEmpty()) {
			String sql = "update SC0060 set " + "SC0060_02=?,SC0060_03=?,SC0060_04=?" + ",DATE_UPDATE=NOW(),USER_UPDATE=?,VERSION=?" + "where SC0060_01='" + Form.getSC0060_01() + "'";

			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, stmt, null, sql);

				p6stmt.setString(1, Form.getSC0060_02());
				p6stmt.setString(2, Form.getSC0060_03());
				p6stmt.setString(3, Form.getSC0060_04());

				p6stmt.setString(4, userform(request).getSC0030_01());
				p6stmt.setInt(5, Form.getVERSION() + 1);
				// 未來要LOG
				// System.out.println(p6stmt.getQueryFromPreparedStatement());

				stmt.executeUpdate();
				stmt.close();
				conn.commit();
				p6stmt.close();

				msg = "ok";
			} catch (SQLException e) {
				e.printStackTrace();
				msg=e.toString();
			}
		} else {
			Iterator it = l_actionErrors.get();
			while (it.hasNext()) {
				msg += ((ActionMessage) it.next()).getKey() + "\r\n";
			}

		}

		return msg;
	}

	private String Detail_delete(ActionMapping mapping, ActionForm form, HttpServletRequest request, Connection conn) {

		String msg = "";
		SC006F Form = (SC006F) form;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.execute("delete from SC0060 where SC0060_01='" + Form.getSC0060_01() + "'");
			stmt.close();
			conn.commit();
			msg = "ok";
		} catch (SQLException e) {
			e.printStackTrace();
			msg = e.toString();
		}
		return msg;
	}

	public ActionForward Detail_select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		response.addHeader("Content-Type", "text/xml;charset=utf-8");
		//表頭key值用{;}分隔
		//System.out.println(request.getParameter("keyvalue"));
		
//		ds = getDataSource(in_request, "SPOS");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
			//建立表頭共用資料物件
			BA_Detail_Head_Attr D_H_A =(BA_Detail_Head_Attr) request.getSession().getAttribute("D_H_A");
			
			//設定新增或修改
			D_H_A.setMODE(request.getParameter("mode"));
			//目前的頁次 1代表第一頁 0表示不分頁
			D_H_A.setPAGENUM(Integer.parseInt(request.getParameter("pagenum")));
		
			BA_DATAGRID datagrid=BA_DATAGRID.getInstance();
			
			
			/*
			 * DISP_TYPE之定義方式
			 * 
			 *	1	2	3
			 *  S	K	R
			 *  T	N	E
			 *  L		H
			 *  
			 *  1.代表型態 S:select T:texe L:lov
			 *  2.代表是否為Key
			 *  3.R 唯讀 E 可編輯  H 隱藏 
			 */
			
			
//			設定新增模式的D_A變化
			if(request.getParameter("mode").equals("new"))
			{
				BA_Detail_Attr D_A=null;
				D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"SC0060_01");
				D_A.setDISP_TYPE("TKE");
				D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"VERSION");
				D_A.setDISP_TYPE("TNH");
			}
			else //非新增時的D_A的變化
			{
				BA_Detail_Attr D_A=null;
				D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"SC0060_01");
				D_A.setDISP_TYPE("TKR");
				D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"VERSION");
				D_A.setDISP_TYPE("TNR");
			}
			
			try {
				
				sendtoclient(datagrid.getDATAGRID(conn, D_H_A).toString(),response);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}



	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request) {
		boolean openbyme = false;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		try {

			if (conn.isClosed()) {

				DataSource ds = tools.getDataSource("SPOS");
				
				try {
					conn = ds.getConnection();
					openbyme = true;
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		} catch (Exception e1) {
			try {
				DataSource ds = tools.getDataSource("SPOS");
				conn = ds.getConnection();
				openbyme = true;
			}catch(Exception e2) {
				e2.printStackTrace();
			}
		}
//		List listSC0070_05_HH=new ArrayList();
//		DecimalFormat df=new DecimalFormat("00");
//		for (int i = 0; i < 24; i++) {
//			BA_ALLKINDForm bform = new BA_ALLKINDForm();
//			bform.setItem_id(df.format(i));
//			bform.setItem_value(df.format(i));
//			listSC0070_05_HH.add(bform);
//		}
//		request.setAttribute("listSC0070_05_HH",listSC0070_05_HH);
		
		if (openbyme) {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}


	
	private  void sendtoclient(String msg,HttpServletResponse response) throws IOException
	{
		byte [] in =msg.getBytes("UTF-8");
		for (int i = 0; i < in.length; i++) {
			response.getOutputStream().write(in[i]);
		} 
	}
	
	

}