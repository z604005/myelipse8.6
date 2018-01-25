package com.spon.utils.sc.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.SC002F;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.sc.forms.SC004F;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 子系統代碼管理
 * 
 * @version 1.0
 * @created 06-四月-2006 上午 11:05:00
 */
public class SC004A extends NewDispatchAction {
	public SC004A() {

	}

	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * 新增資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (request.getAttribute("save_status") == null) {
			request.setAttribute("save_status", "");
		}

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		if (request.getAttribute("save_status").equals("")) {
			try {
				SC004F Form = new SC004F();
				
				conn = tools.getConnection();

				generateSelectBox(conn, Form, request);

				request.setAttribute("Form1Datas", Form);

				// 將表單的顯示模式設定為create (新增模式)
				FormUtils.setFormDisplayMode(request, form, "create");
				// 指定執行鈕，應該要呼叫的method
				request.setAttribute("BUTTON_TYPE", "addData");

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
		} else {

			generateSelectBox(conn, form, request);
			request.setAttribute("Form1Datas", form);

			// 將表單的顯示模式設定為create (新增模式)
			FormUtils.setFormDisplayMode(request, form, "create");
			// 指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "addData");

		}
		// 依據 struts-config.xml 的定義forward到showedit
		// 雖然是新增狀態，但為了維護便利，不須再為新增再作一個表單
		// 就沿用showedit即可

		request.setAttribute("action", "新增");

		String Required = "true";
		String Mode = "E,R,I";

		request.setAttribute("Required", Required);
		request.setAttribute("Mode", Mode);

		return mapping.findForward("showedit");
	}

	/**
	 * 儲存新增資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward addData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		String sql = "";

		SC004F Form = (SC004F) form;

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		Form.setSC0040_05(userform(request).getSC0030_14());
		request.setAttribute("action", "addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);

		

			try {
				if (lc_errors.isEmpty()) {
				sql = "insert into SC0040 (" + "SC0040_01," + "SC0040_02,"
						+ "SC0040_03," + "SC0040_04,"+ "SC0040_05,"+"DATE_CREATE," + "DATE_UPDATE,"
						+ "USER_CREATE," + "USER_UPDATE," + "VERSION)"
						+ " values (?,?,?,?,?,NOW(),NOW(),?,?,?)";

				PreparedStatement stmt = conn.prepareStatement(sql);

				stmt.setString(1, Form.getSC0040_01());
				stmt.setString(2, Form.getSC0040_02());
				stmt.setString(3, Form.getSC0040_03());
				stmt.setInt(4, Form.getSC0040_04());
				stmt.setString(5, userform(request).getSC0030_14());
				stmt.setString(6, userform(request).getSC0030_01());
				stmt.setString(7, userform(request).getSC0030_01());
				stmt.setInt(8, 1);

				stmt.execute();
				stmt.close();
				conn.commit();

				request.setAttribute("MSG", "新增成功!");
				} else {
					saveErrors(request, lc_errors);
					request.setAttribute("save_status", "error");
				}
			} catch (Exception e) {
				request.setAttribute("MSG", "新增失敗!");
				System.out.println("SC002A.addData() " + e);
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
		
		// 新增資料後，回到顯示查詢結果的頁面
		// return showAllDatas(in_mapping, in_form, in_request, in_response);
		return addDataForm(mapping, form, request, response);
	}

	/**
	 * 取消作業，判斷查詢條件還在不在，如果在的話，就呼叫queryDatas 如果不在就呼叫init()
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if ((SC004F) request.getSession().getAttribute("QueryString") != null) {
			form = (SC004F) request.getSession().getAttribute("QueryString");
			return queryDatas(mapping, form, request, response);
		} else {
			return init(mapping, form, request, response);
		}
	}

	/**
	 * 刪除資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward delData(ActionMapping mapping ,ActionForm form ,HttpServletRequest request ,HttpServletResponse response) {
		SC004F formBean = (SC004F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
			Statement stmt = conn.createStatement();
			// 確認使用者有選擇資料列後，才按下刪除鈕
			if (formBean.getSC0040_01() == null) {
				// 如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
				request.setAttribute("MSG", "請先選擇一筆要刪除的資料。");
				return cancel(mapping, form, request, response);
			}
			stmt.execute("delete from SC0040 where SC0040_01='"
					+ formBean.getSC0040_01() + "' and SC0040_05 = '"+userform(request).getSC0030_14()+"' ");
			stmt.close();
			conn.commit();

		} catch (Exception e) {
			System.out.println("SC004A.delData()" + e);
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
		// 刪除資料後，回到顯示查詢結果的頁面
		form = (SC004F) request.getSession().getAttribute("QueryString");
		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 執行查詢，實際查詢交給queryDatas做
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward doQueryDatas(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
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
		SC004F Form = (SC004F) form;
		String sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		// 確認使用者有選擇資料列後，才按下編輯鈕
		if (Form.getSC0040_01() == null) {
			// 如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
			request.setAttribute("MSG", "請選擇一筆資料。");
			return cancel(mapping, form, request, response);
		}

		if (request.getAttribute("save_status") == null) {
			request.setAttribute("save_status", "");
		}

		if (request.getAttribute("save_status").equals("")) {
			try {
				conn = tools.getConnection();
				Statement stmt = conn.createStatement();
				sql = "select * from SC0040 " +
						" where SC0040_01='" + Form.getSC0040_01() + "' and SC0040_05='"+userform(request).getSC0030_14()+"'";
				ResultSet rs = stmt.executeQuery(sql);
				SC004F formbean = new SC004F();
				if (rs.next()) {

					formbean.setSC0040_01(rs.getString("SC0040_01"));
					formbean.setSC0040_02(rs.getString("SC0040_02"));
					formbean.setSC0040_03(rs.getString("SC0040_03"));
					formbean.setSC0040_04(rs.getInt("SC0040_04"));
					
					BA_TOOLS ba=BA_TOOLS.getInstance();
					formbean.setDATE_CREATE(ba.DateToChineseDate(rs.getTimestamp("DATE_CREATE")));
					formbean.setDATE_UPDATE(ba.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
					formbean.setUSER_CREATE(ba.GetUserName(conn,rs.getString("USER_CREATE"),userform(request).getSC0030_14()));
					formbean.setUSER_UPDATE(ba.GetUserName(conn,rs.getString("USER_UPDATE"),userform(request).getSC0030_14()));
					
					formbean.setVERSION(rs.getInt("VERSION"));
				}
				generateSelectBox(conn, form, request);
				request.setAttribute("Form1Datas", formbean);
				// 產生下拉選單

				// 將表單的顯示模式設定為edit (編輯模式)
				FormUtils.setFormDisplayMode(request, form, "edit");
				// 指定執行鈕，應該要呼叫的method
				request.setAttribute("BUTTON_TYPE", "saveData");

				stmt.close();
			} catch (Exception e) {
				System.out.println("SC004A.editDataForm()" + e);
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
			generateSelectBox(conn, form, request);

			request.setAttribute("Form1Datas", form);

			// 將表單的顯示模式設定為edit (編輯模式)
			FormUtils.setFormDisplayMode(request, form, "edit");
			// 指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "saveData");

		}
		// 依據 struts-config.xml 的定義forward到showedit
		request.setAttribute("action", "修改");

		// 判斷欄位是否為必要
		String Required = "true";
		String Mode = "E,R,I";

		request.setAttribute("Required", Required);
		request.setAttribute("Mode", Mode);

		return mapping.findForward("showedit");
	}

	/**
	 * 批次產生下拉選單 過程中，需要透過呼叫getSelectOptions取得選項
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	public void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {

	}

	/**
	 * 一開始進來程式時顯示空的資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		FormUtils.setFormDisplayMode(request, form, "inspect");
		request.setAttribute("Form1Datas", new ArrayList());
		request.getSession().removeAttribute("QueryString");
		request.setAttribute("MSG", "請查詢資料!");
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
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		DataSource ds;
		Connection conn = null;
		try {
			SC004F Form = new SC004F();
			request.setAttribute("Form1Datas", Form);

			// 將表單的顯示模式設定為edit (編輯模式)，因為struts-layout沒有定義查詢模式，只好用edit了
			FormUtils.setFormDisplayMode(request, form, "edit");
			// 指定執行鈕，應該要呼叫的method

			request.setAttribute("BUTTON_TYPE", "doQueryDatas");

		} catch (Exception e) {
			System.out.println("SC004A.queryForm()" + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		// 依據 struts-config.xml 的定義forward到showedit
		request.setAttribute("action", "查詢");

		// 判斷欄位是否為必要
		String Required = "true";
		String Mode = "E,R,I";

		Required = "";
		Mode = "E,E,I";

		request.setAttribute("Required", Required);
		request.setAttribute("Mode", Mode);

		return mapping.findForward("showedit");
	}

	/**
	 * 實際查詢資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();

			FormUtils.setFormDisplayMode(request, form, "inspect");
			request.setAttribute("Form1Datas", showSystemList(conn, form,
					request));
			conn.close();

		} catch (Exception e) {
			System.out.println("SC004A.queryDatas()" + e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		request.getSession().setAttribute("QueryString", form);
		return mapping.findForward("success");
	}

	/**
	 * 儲存編修資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		SC004F Form = (SC004F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		Form.setSC0040_05(userform(request).getSC0030_14());
		request.setAttribute("action", "saveData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		//如果沒有任何錯誤的話
		
			try {
				if (lc_errors.isEmpty()) {
				// Statement stmt = conn.createStatement();
				
				String sql = "UPDATE SC0040 SET "
						   + " SC0040_02=? ,SC0040_03=? ,SC0040_04=?"
						   + " ,DATE_UPDATE=NOW() ,USER_UPDATE=? ,VERSION=?"
						   + " WHERE SC0040_01 = '" + Form.getSC0040_01() + "'" 
						   + " and SC0040_05 = '"+userform(request).getSC0030_14()+ "'";
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1, Form.getSC0040_02());
				stmt.setString(2, Form.getSC0040_03());
				stmt.setInt(3, Form.getSC0040_04());
				stmt.setString(4, userform(request).getSC0030_01());
				stmt.setInt(5, Form.getVERSION() + 1);
								
				stmt.executeUpdate();
				stmt.close();
				conn.commit();

				request.setAttribute("MSG", "修改成功!");
				} else {
					saveErrors(request, lc_errors);
					request.setAttribute("save_status", "error");
					if (request.getAttribute("FormData") != null) {

						form = (SC004F) request.getAttribute("FormData");
						request.setAttribute("MSG", request.getAttribute("ErrMSG"));
					}
					return editDataForm(mapping, form, request, response);
				}
			} catch (Exception e) {
				request.setAttribute("MSG", "修改失敗!");
				System.out.println("SC004A.saveData() " + e);
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
			// 儲存後，回到顯示查詢結果的頁面
		

		if ((SC004F) request.getSession().getAttribute("QueryString") != null) {
			form = (SC004F) request.getSession().getAttribute("QueryString");
			return queryDatas(mapping, form, request, response);
		} else {
			return init(mapping, form, request, response);
		}
	}

	/**
	 * 取得子系統清單
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	public ArrayList showSystemList(Connection conn, ActionForm form,
			HttpServletRequest request) {
		ArrayList list = new ArrayList();
		String sql = "";
		try {
			sql = "select * from SC0040 where 1=1 and SC0040_05='"+userform(request).getSC0030_14()+"'";
			if (form != null) {
				SC004F queryForm = (SC004F) form;

				if (queryForm.getSC0040_01() != null
						& !queryForm.getSC0040_01().equals(""))
					sql += " and SC0040_01 like '%" + queryForm.getSC0040_01()
							+ "%'";
				if (queryForm.getSC0040_02() != null
						& !queryForm.getSC0040_02().equals(""))
					sql += " and SC0040_02 like '%" + queryForm.getSC0040_02()
							+ "%'";
				if (queryForm.getSC0040_03() != null
						& !queryForm.getSC0040_03().equals(""))
					sql += " and SC0040_03 like '%" + queryForm.getSC0040_03()
							+ "%'";
			}
			
			sql += " order by SC0040_04 ";  
			SC004F formBean = null;
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				formBean = new SC004F();
				formBean.setSC0040_01(rs.getString("SC0040_01"));
				formBean.setSC0040_02(rs.getString("SC0040_02"));
				formBean.setSC0040_03(rs.getString("SC0040_03"));
				formBean.setSC0040_04(rs.getInt("SC0040_04"));
				list.add(formBean);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("SC004A.showSystemList()" + e);
		}
		return list;
	}
}