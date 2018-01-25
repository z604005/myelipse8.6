package com.spon.utils.sc.actions;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

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
import com.spon.utils.sc.forms.DF0021F;
import com.spon.utils.sc.forms.DF002F;
import com.spon.utils.sc.forms.SC006F;
import com.spon.utils.struts.form.BA_Detail_Attr;
import com.spon.utils.struts.form.BA_Detail_Head_Attr;
import com.spon.utils.util.BA_DATAGRID;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 使用者管理
 * @version 1.0
 * @created 10-四月-2006 下午 04:37:53
 */
public class DF002A extends NewDispatchAction {

	/**
	 * 使用者資訊
	 */
	//private SC003F userform;

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 儲存新增資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward addData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		String sql = "";
		DF002F Form = (DF002F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		request.setAttribute("action", "addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);

		try {
			if (lc_errors.isEmpty()) {
				sql = "insert into DF0020 (" + "DF0020_01," + "DF0020_02," + "DF0020_03," + "DATE_CREATE," + "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) values (?,?,?,NOW(),NOW(),?,?,1)";

				PreparedStatement stmt = conn.prepareStatement(sql);

				stmt.setString(1, Form.getDF0020_01());

				stmt.setString(2, Form.getDF0020_02());
				stmt.setString(3, Form.getDF0020_03());

				stmt.setString(4, userform(request).getSC0030_01());
				stmt.setString(5, userform(request).getSC0030_01());

				stmt.execute();
				stmt.close();

				conn.commit();
				request.setAttribute("Form1Datas", form);

				request.setAttribute("MSG", "新增成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("DF002A.addData() " + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}

		//新增資料後，回到顯示查詢結果的頁面
		//return showAllDatas(in_mapping, in_form, in_request, in_response);
		return queryForm(mapping, form, request, response);
	}

	/**
	 * 新增資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return addData(mapping, form, request, response);
	}

	/**
	 * 取消作業，判斷查詢條件還在不在，如果在的話，就呼叫queryDatas
	 * 如果不在就呼叫init()
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	/**
	 * 刪除使用者資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward delData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DF002F Form = (DF002F) form;

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			
			conn = tools.getConnection();
			Statement stmt = conn.createStatement();

			ResultSet rs = stmt.executeQuery("select DF0020_01,DF0020_02 from DF0020 where DF0020_01='" + Form.getDF0020_01() + "' AND DF0020_02='" + Form.getDF0020_02() + "' ");
			if (rs.next()) {
				stmt.execute("delete from DF0020 where DF0020_01='" + Form.getDF0020_01() + "' AND DF0020_02='" + Form.getDF0020_02() + "' ");
				//刪除表身資料
				delAllData(conn, form, request,  Form.getDF0020_01(),Form.getDF0020_01());
				request.setAttribute("MSG", "刪除成功!");
			} else {
				request.setAttribute("MSG", "刪除失敗，無此資料!");
			}
			rs.close();
			stmt.close();

			conn.commit();

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("MSG", "刪除失敗!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return init(mapping, form, request, response);
	}

	/**
	 *刪除表身資料
	 * @param conn
	 * @param form
	 * @param request
	 * @param df0020_01
	 * @param df0020_012
	 */
	private void delAllData(Connection conn, ActionForm form, HttpServletRequest request, String df0020_01, String df0020_012) {
		DF002F Form = (DF002F) form;
	
		try {
			Statement stmt = conn.createStatement();
			stmt.execute("delete from DF0021 where DF0021_01='" + Form.getDF0020_01() + "' AND DF0021_02='" + Form.getDF0020_02() + "' ");
			stmt.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("MSG", "刪除失敗!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} 
	}

	/**
	 * 執行查詢，實際查詢交給queryDatas做
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward doQueryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("Pageid", null);
		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 編輯資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		if (!request.getParameter("LastKey1").equals(((DF002F) form).getDF0020_01()) | !request.getParameter("LastKey2").equals(((DF002F) form).getDF0020_02())) {
			request.setAttribute("MSG", "修改失敗!條件值已改變");
			return init(mapping, form, request, response);
		}
		return saveData(mapping, form, request, response);

	}

	/**
	 * 批次產生下拉選單
	 * 過程中，需要透過呼叫getSelectOptions取得選項
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request) {

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

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();

			DF002F Form = new DF002F();

			generateSelectBox(conn, Form, request);

			request.setAttribute("Form1Datas", Form);

			//將表單的顯示模式設定為create (新增模式)
			FormUtils.setFormDisplayMode(request, form, "create");
			//指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "addData");
			request.setAttribute("rownum", "0");

		} catch (Exception e) {
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		//依據 struts-config.xml 的定義forward到showedit
		//雖然是新增狀態，但為了維護便利，不須再為新增再作一個表單
		//就沿用showedit即可

		request.setAttribute("action", "新增");

		String Required = "true";
		String Mode = "E,R,I";

		request.setAttribute("Required", Required);
		request.setAttribute("Mode", Mode);

		return mapping.findForward("success");
	}

	/**
	 * 實際執行查詢
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DF002F Form = (DF002F) form;
		if (!Form.getDF0020_01().equals("") & !Form.getDF0020_02().equals("")) {
			String sql = "";

			BA_TOOLS tools = BA_TOOLS.getInstance();
			Connection conn = null;

			try {
				conn = tools.getConnection();
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				sql = "select * from DF0020 " + 
						 " where DF0020_01='" + Form.getDF0020_01() + "' AND  DF0020_02='" + Form.getDF0020_02() + "' order by DF0020_01,DF0020_02 ";

				ResultSet rs = stmt.executeQuery(sql);
				DF002F Form1 = new DF002F();

				if (rs.next()) {

					Form1.setDF0020_01(rs.getString("DF0020_01"));
					Form1.setDF0020_02(rs.getString("DF0020_02"));
					Form1.setDF0020_03(rs.getString("DF0020_03"));
					BA_TOOLS ba=BA_TOOLS.getInstance();
					Form1.setDATE_CREATE(ba.DateToChineseDate(rs.getTimestamp("DATE_CREATE")));
					Form1.setDATE_UPDATE(ba.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
					Form1.setUSER_CREATE(ba.GetUserName(conn,rs.getString("USER_CREATE")));
					Form1.setUSER_UPDATE(ba.GetUserName(conn,rs.getString("USER_UPDATE")));
					Form1.setVERSION(rs.getInt("VERSION"));

					generateSelectBox(conn, form, request);
					request.setAttribute("Form1Datas", Form1);
					FormUtils.setFormDisplayMode(request, form, "edit");
					
					//有查到資料 要把button 現出來
					{
					StringBuffer sb =new StringBuffer();	
					sb.append("<input id=\"DetailButton\" type=\"button\" value='明細資料' onclick=\"");
					BA_Detail_Head_Attr D_H_A=(BA_Detail_Head_Attr) request.getSession().getAttribute("D_H_A");
					int page=D_H_A.getPAGENUM();
					if(page>0)
						page=1;
					sb.append("Detail_select_Head('"+D_H_A.getPROGRAM()+"','DBGRIDDetail','','"+page+"','"+D_H_A.getKEYID()+"');");
					sb.append("\" />");
					request.setAttribute("DETAIL",sb.toString());
					}
					
				} else {
					request.setAttribute("MSG", "查無資料");
					request.setAttribute("Form1Datas", Form);
					return mapping.findForward("success");
				}

				//產生下拉選單
				generateSelectBox(conn, form, request);
				//將表單的顯示模式設定為edit (編輯模式)

				//指定執行鈕，應該要呼叫的method
				request.setAttribute("BUTTON_TYPE", "saveData");
				rs.close();
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} else {
			request.setAttribute("MSG", "請輸入條件值");
			request.setAttribute("Form1Datas", Form);
			return mapping.findForward("success");
		}

		//依據 struts-config.xml 的定義forward到showedit
		request.setAttribute("action", "修改");

		//		判斷欄位是否為必要
		String Required = "true";
		String Mode = "E,R,I";

		request.setAttribute("Required", Required);
		request.setAttribute("Mode", Mode);

		//request.setAttribute("FM0102Query", queryform);
		//request.getSession().setAttribute("QueryString",queryform);
		//當要用到Datagrid時，才需要這段程式
		//Datagrid lc_datagrid = Datagrid.getInstance();
		//lc_datagrid.setDataClass(FM0102Form.class);
		//lc_datagrid.setData(new ArrayList(datas.values()));
		//FM0102Form.setDatagrid(lc_datagrid);

		FormUtils.setFormDisplayMode(request, form, "edit");
		//依據 struts-config.xml 的定義forward到success
		return mapping.findForward("success");
	}

	/**
	 * 查詢資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 儲存編輯結果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DF002F Form = (DF002F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();
		} catch (Exception e2) {
			e2.printStackTrace();
		}

		request.setAttribute("action", "saveData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);

		try {
			if (lc_errors.isEmpty()) {
				//Statement stmt = conn.createStatement();
				String sql = "";
				PreparedStatement stmt = null;

				sql = "update DF0020 set " + "DF0020_03=?," + "DATE_UPDATE=NOW()," + "USER_UPDATE=?," + "VERSION=?" + "where DF0020_01='" + Form.getDF0020_01() + "' AND DF0020_02='" + Form.getDF0020_02() + "'";

				stmt = conn.prepareStatement(sql);

				stmt.setString(1, Form.getDF0020_03());

				stmt.setString(2, userform(request).getSC0030_01());
				stmt.setInt(3, Form.getVERSION() + 1);

				stmt.executeUpdate();
				stmt.close();

				conn.commit();

				request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");

			}
		} catch (Exception e) {
			request.setAttribute("MSG", "修改失敗!");
			System.out.println("DF002A.saveData() " + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//儲存後，回到顯示查詢結果的頁面

		return queryDatas(mapping, form, request, response);

		//		form=(FM0102Form)request.getSession().getAttribute("FM0102Query");
		//		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 顯示查詢結果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward showAllDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		return null;
	}

	/**
	 * 顯示使用者清單
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	public ArrayList showUserList(Connection conn, ActionForm form, HttpServletRequest request) {
		return null;
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
//			contents[0]="";
//			contents[1]="";
			contents[2]="";
			contents[3]="";
			
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
		String sql="select DF0021_01,DF0021_02,DF0021_03,DF0021_04,VERSION from DF0021 where DF0021_01=? and DF0021_02=? and DF0021_03=? ";
		try {
			conn = tools.getConnection();
			
			PreparedStatement stmt=conn.prepareStatement(sql);
			stmt.setString(1,contents[0].trim());
			stmt.setString(2,contents[1].trim());
			stmt.setString(3,contents[2].trim());
			
			
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
				
				sendtoclient(e.toString(),response);
				
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

			DF0021F Form = new DF0021F();
			Form.setDF0021_01(contents[0].trim());
			Form.setDF0021_02(contents[1].trim());
			Form.setDF0021_03(contents[2].trim());
			Form.setDF0021_04(contents[3].trim());
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
		
		DF0021F Form = (DF0021F) form;
		ActionErrors l_actionErrors = ((DF0021F) Form).validate(mapping, request, conn);
		String msg = "";
		if (l_actionErrors.isEmpty()) {
			String sql = "insert into DF0021 (" + "DF0021_01," + "DF0021_02," + "DF0021_03," + "DF0021_04," + "DATE_CREATE," + "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE,"
					+ "VERSION) values (?,?,?,?,NOW(),NOW(),?,?,?)";

			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(sql);

				P6PreparedStatement p6stmt = new P6PreparedStatement(null, stmt, null, sql);

				p6stmt.setString(1, Form.getDF0021_01());
				p6stmt.setString(2, Form.getDF0021_02());
				p6stmt.setString(3, Form.getDF0021_03());
				p6stmt.setString(4, Form.getDF0021_04());

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
		DF0021F Form = (DF0021F) form;
		ActionErrors l_actionErrors = Form.validate(mapping, request, conn);
		if (l_actionErrors.isEmpty()) {
			String sql = "update DF0021 set DF0021_04=?,DATE_UPDATE=NOW(),USER_UPDATE=?,VERSION=?" + "where DF0021_01='" + Form.getDF0021_01() + "' AND DF0021_02='" + Form.getDF0021_02() + "' AND DF0021_03='" + Form.getDF0021_03() + "'";

			PreparedStatement stmt;
			try {
				stmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, stmt, null, sql);

				p6stmt.setString(1, Form.getDF0021_04());
				
				p6stmt.setString(2, userform(request).getSC0030_01());
				p6stmt.setInt(3, Form.getVERSION() + 1);
				// 未來要LOG
				// System.out.println(p6stmt.getQueryFromPreparedStatement());

				stmt.executeUpdate();
				stmt.close();
				p6stmt.close();
				conn.commit();

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
		DF0021F Form = (DF0021F) form;
		Statement stmt;
		try {
			stmt = conn.createStatement();
			stmt.execute("delete from DF0021 where DF0021_01='" + Form.getDF0021_01() + "' AND DF0021_02='" + Form.getDF0021_02() + "' AND DF0021_03='" + Form.getDF0021_03() + "'" );
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
			
			try {

				//把表頭的key值放進去
				if(!request.getParameter("keyvalue").equals(""))
				{
					String [] keyvalue_Arr=request.getParameter("keyvalue").split("\\{;\\}");
					BA_Detail_Attr D_A=null;
					D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"DF0020_01");
					D_A.setVALUE(keyvalue_Arr[0]);
					D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"DF0020_02");
					D_A.setVALUE(keyvalue_Arr[1]);
					datagrid.setKeyvalue_Arr(keyvalue_Arr);
				}

			} catch (Exception e) {
				e.printStackTrace();	
			}
			
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
				D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"DF0021_03");
				D_A.setDISP_TYPE("LKE");
				D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"VERSION");
				D_A.setDISP_TYPE("TNH");
			}
			else //非新增時的D_A的變化
			{
				BA_Detail_Attr D_A=null;
				D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"DF0021_03");
				D_A.setDISP_TYPE("LKR");
				D_A=datagrid.getD_AbyID(D_H_A.getBA_Detail_attr(),"VERSION");
				D_A.setDISP_TYPE("TNR");
			}
			
			try {
				
				OutputStream os= response.getOutputStream();
				os.write( datagrid.getDATAGRID(conn, D_H_A).toString().getBytes("UTF-8"));
				os.flush();
				os.close();
				
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

	private  void sendtoclient(String msg,HttpServletResponse response) throws IOException
	{
		byte [] in =msg.getBytes("UTF-8");
		for (int i = 0; i < in.length; i++) {
			response.getOutputStream().write(in[i]);
		} 
	}
	
}