package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.ems.salary.forms.EHF030102M0F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.HR_TOOLS;


import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>薪資項目金額設定
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030102M0A extends NewDispatchAction {

	public EHF030102M0A() {

	}

	/**
	 * 儲存新增資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF030102M0F Form = (EHF030102M0F) form;
		
		//建立資料庫控制元件
		EMS_DB_CONTROLLER db_tools = null;
		
		try {
			//建立資料庫連線
			conn = tools.getConnection("SPOS");
			
			//資料庫控制元件
			db_tools = new EMS_DB_CONTROLLER();
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("action", "addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);

		try {
			if (lc_errors.isEmpty()) {
				
				//取得薪資項目
				//依據新的整合方式:先檢查是否已有薪資項目
				//if true ==> 使用之前已設定好的薪資項目
				//if false ==> 則將此薪資項目新增至薪資項目EHF030101T0中
				
				
				AuthorizedBean authbean = getLoginUser(request);
				
				//新增表單資訊
				String sql = "" +
				" INSERT INTO EHF030102T0" +
				" ( " +
				"  EHF030102T0_02, EHF030102T0_03, EHF030102T0_04, EHF030102T0_05, " +
				"  EHF030102T0_06, EHF030102T0_07, EHF030102T0_08, EHF030102T0_09, " +
				"  USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE " +
				" ) " +
				" VALUES " +
				" ( " +
				" ?,?,?,?,?,?,?,?,?,?, 1, NOW() " +
				" ) " ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,
						pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, Form.getEHF030102T0_02_ID());  //部門組織(代號)
				indexid++;
				p6stmt.setString(indexid, this.getSalaryItem(conn, Form.getEHF030102T0_03(), 
														     authbean.getUserName(),  authbean.getCompId()));  //薪資項目
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_04()==null?"01":Form.getEHF030102T0_04());  //所得稅別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_05()==null?"01":Form.getEHF030102T0_05());  //類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_06());  //金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_07());  //備註
				indexid++;
				p6stmt.setString(indexid, authbean.getCompId());
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_09()==null?"":Form.getEHF030102T0_09());  //薪資計算公式
				indexid++;
				p6stmt.setString(indexid, authbean.getUserName());  //使用員工姓名
				indexid++;
				p6stmt.setString(indexid, authbean.getUserName());  //使用員工姓名
				indexid++;
			
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();			

				conn.commit();
				
				//取得新增的薪資項目金額序號
				Form.setEHF030102T0_01(String.valueOf(db_tools.getLastInsertId(conn)));
				
				
				//建立下拉選單
				generateSelectBox(conn, Form, request);
				
				request.setAttribute("MSG", "新增成功");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
				// 清掉畫面上的新增
				
				
			} else {
				
				request.setAttribute("button", "init");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				if (request.getAttribute("Form1Datas") != null) {
					form = (EHF030102M0F) request.getAttribute("Form1Datas");
				}
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
//				return init(mapping, form, request, response);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF030102M0A.addData() " + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				
				if(db_tools != null && !db_tools.isClosed()){
					//關閉資料庫控制元件
					db_tools.close();
				}
			} catch (Exception e) {
			}
		}
		
		return editDataForm(mapping, form, request, response);
	}
	
	/**
	 * 取得薪資項目
	 * @param conn
	 * @param salary_item
	 * @param comp_id
	 * @return
	 */
	public String getSalaryItem(Connection conn, String salary_item, 
							    String user_name, String comp_id){
		
		String return_str = "";
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF030101T0_01, EHF030101T0_02, " +
			" EHF030101T0_05 " +
			" FROM EHF030101T0 " +
			" WHERE 1=1 " +
			" AND EHF030101T0_02 = '"+salary_item+"' " +  //薪資項目名稱
			" AND EHF030101T0_05 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//已有薪資項目設定
				
				//取得薪資項目序號
				return_str = rs.getString("EHF030101T0_01");  //薪資項目序號
				
			}else{
				//尚未有薪資項目設定
				
				//建立薪資項目
				return_str = this.createSalaryItem(conn, salary_item, user_name, comp_id);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_str;
	}
	
	/**
	 * 建立薪資項目
	 * @param conn
	 * @param salary_item
	 * @param user_name
	 * @param comp_id
	 * @return
	 */
	public String createSalaryItem(Connection conn, String salary_item, 
								   String user_name, String comp_id){
		
		//建立資料庫控制元件
		EMS_DB_CONTROLLER db_tools = new EMS_DB_CONTROLLER();
		String return_str = "";	
		
		try{
			
			//新增表單資訊
			String sql = "" +
			" INSERT INTO EHF030101T0" +
			" ( " +
			"  EHF030101T0_02, EHF030101T0_03, EHF030101T0_04, " +
			"  EHF030101T0_05, " +
			"   " +
			"  USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE " +
			" ) " +
			" VALUES " +
			" ( " +
			" ?,?,?, " +
			" ?, " +
			" ?,?, 1, NOW() " +
			" ) " ;
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, salary_item);  //薪資項目
			indexid++;
			p6stmt.setString(indexid, "");  //所得稅別
			indexid++;
			p6stmt.setString(indexid, "");  //備註
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, user_name);  //使用員工姓名
			indexid++;
			p6stmt.setString(indexid, user_name);  //使用員工姓名
			indexid++;
		
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			
			//取得建立的薪資項目序號
			return_str = String.valueOf(db_tools.getLastInsertId(conn));
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(db_tools != null && !db_tools.isClosed()){
				//關閉資料庫控制元件
				db_tools.close();
			}
		}
		
		return return_str;
	}
	
	/**
	 * 新增
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return addData(mapping, form, request, response);
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	private void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request) {
		
		//產生所得稅別
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
//			bform.setItem_id("");
//			bform.setItem_value("-請選擇-");
//			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("是");  //應稅
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("否");  //未稅
			datas.add(bform);
			request.setAttribute("EHF030102T0_04_list", datas);
			}catch(Exception e) {
				System.out.println(e);
			}
			
		//產生類別
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
//			bform.setItem_id("");
//			bform.setItem_value("-請選擇-");
//			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("主管");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("非主管");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("特殊");
			datas.add(bform);
			request.setAttribute("EHF030102T0_05_list", datas);
			}catch(Exception e) {
				System.out.println(e);
			}
		
			//產生薪資項目
			try {
				List datas = new ArrayList();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				String sql = " SELECT EHF030101T0_01, EHF030101T0_02 " +
							 " FROM EHF030101T0 " +
						     " WHERE 1=1 " +
						     " AND EHF030101T0_05 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
				
				ResultSet rs=stmt.executeQuery(sql);
				
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("");
				bform.setItem_value("一請選擇一");
				datas.add(bform);
				
				while(rs.next()){
					bform = new BA_ALLKINDForm();
					bform.setItem_id(rs.getString("EHF030101T0_01"));
					bform.setItem_value(rs.getString("EHF030101T0_02"));
					datas.add(bform);	
					
				}
				
				rs.close();
				stmt.close();
				
				request.setAttribute("EHF030102T0_03_list", datas);
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//產生薪資計算公式
			/*try {
				List datas = new ArrayList();
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				String sql = " SELECT EHF030102T1_01, EHF030102T1_02 " +
							 " FROM EHF030102T1 " +
						     " WHERE 1=1 " +
						     " AND EHF030102T1_11 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
				
				ResultSet rs=stmt.executeQuery(sql);
				
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("");
				bform.setItem_value("一請選擇一");
				datas.add(bform);
				
				while(rs.next()){
					bform = new BA_ALLKINDForm();
					bform.setItem_id(rs.getString("EHF030102T1_01"));
					bform.setItem_value(rs.getString("EHF030102T1_02"));
					datas.add(bform);	
					
				}
				
				rs.close();
				stmt.close();
				
				request.setAttribute("EHF030102T0_09_list", datas);
								
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			

	}

	/**
	 * Action的進入點
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			
			conn = tools.getConnection("SPOS");
			EHF030102M0F Form = new EHF030102M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
	
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			
			//設定執行按鈕為查詢狀態
			request.setAttribute("button", "query");
			
			//顯示前端查詢結果的Collection
			request.setAttribute("collection", "show");
			
			//設定前端查詢條件的顯示模式
			request.setAttribute("queryCondition", "yes");
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
	    }
		
		return mapping.findForward("success");
	}
	
	/**
	 * 新增畫面的init
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward init_add(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			
			conn = tools.getConnection("SPOS");
			EHF030102M0F Form = new EHF030102M0F();
			//ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
	
			
			request.setAttribute("Form1Datas", Form);
			//request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			
			//設定執行按鈕為新增初始狀態
			request.setAttribute("button", "init");
			
			//顯示前端查詢結果的Collection
			request.setAttribute("collection", "");
			
			//設定前端查詢條件的顯示模式
			request.setAttribute("queryCondition", "no");
			
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
	    }
		
		return mapping.findForward("success");
	}

	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		EHF030102M0F Form = (EHF030102M0F) form;
		HR_TOOLS hr_tools = new HR_TOOLS();

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT " +
				" EHF030102T0_01, EHF030102T0_02, EHF030102T0_03, EHF030102T0_04, " +
				" EHF030102T0_05, EHF030102T0_06, EHF030102T0_07, " +
				" EHF030101T0_02 " +
				"  " +
				" FROM EHF030102T0 " +
				" LEFT JOIN EHF030101T0 ON EHF030101T0_01 = EHF030102T0_03 AND EHF030101T0_05 = EHF030102T0_08 " +
				" WHERE 1=1 " +
				" AND EHF030102T0_08 = '"+getLoginUser(request).getCompId()+"' " ;
				
				//部門代號
				if(!"".equals(Form.getEHF030102T0_02()) && Form.getEHF030102T0_02() != null ){
					sql += " AND EHF030102T0_02 = '"+Form.getEHF030102T0_02()+"' ";
				}
				
				//薪資項目
				if(!"".equals(Form.getEHF030102T0_03()) && Form.getEHF030102T0_03() != null ){
					//sql += " AND EHF030102T0_03 = '"+Form.getEHF030102T0_03()+"' ";
					//使用薪資項目名稱作為查詢條件
					sql += " AND EHF030101T0_02 LIKE '%"+Form.getEHF030102T0_03()+"%' ";
				}
				
//				if(!"".equals(Form.getEHF030102T0_04()) && Form.getEHF030102T0_04() != null ){
//					sql += " AND EHF030102T0_04 LIKE '%"+Form.getEHF030102T0_04()+"%' ";
//				}
//				if(!"".equals(Form.getEHF030102T0_05()) && Form.getEHF030102T0_05() != null ){
//					sql += " AND EHF030102T0_05 LIKE '%"+Form.getEHF030102T0_05()+"%' ";
//				}
//				if(!"".equals(Form.getEHF030102T0_06()) && Form.getEHF030102T0_06() != null ){
//					sql += " AND EHF030102T0_06 = '"+Form.getEHF030102T0_06()+"' ";   //金額
//				}
				
				sql += " ORDER BY EHF030102T0_02 , EHF030102T0_03 DESC ";

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
								
				if(rs.next()){
					
					//Map depMap = ems_tools.getDepNameMap(getEmscontext(), getLoginUser(request).getUserId(), Integer.parseInt(getLoginUser(request).getUserCode()), getLoginUser(request).getCompId());
					Map depMap = hr_tools.getDepNameMap(getLoginUser(request).getCompId());
					rs.beforeFirst();
				
					while(rs.next()){
						EHF030102M0F bean = new EHF030102M0F();
						bean.setEHF030102T0_01(rs.getString("EHF030102T0_01"));  //薪資計算公式序號
						//bean.setEHF030102T0_02(  (String) ((Map)depMap.get(rs.getString("EHF030102T0_02"))).get("SHOW_DESC") );  //部門組織(中文名稱)
						bean.setEHF030102T0_02((String)((Map)depMap.get(rs.getString("EHF030102T0_02"))).get("SHOW_DESC"));
						bean.setEHF030102T0_03(rs.getString("EHF030101T0_02"));  //薪資項目
						bean.setEHF030102T0_04(getTaxTypeName(rs.getString("EHF030102T0_04")));  //所得稅別
						bean.setEHF030102T0_05(getTypeName(rs.getString("EHF030102T0_05")));  //類別
						bean.setEHF030102T0_06(rs.getString("EHF030102T0_06"));  //金額
						bean.setEHF030102T0_07(rs.getString("EHF030102T0_07"));  //備註
						
						list.add(bean);
					}
					
					
					if("".equals(request.getAttribute("MSG"))||request.getAttribute("MSG")==null){
						request.setAttribute("MSG", "查詢成功!!");
					}else{
						request.setAttribute("MSG", request.getAttribute("MSG")+"查詢成功");
					}
				}else{
					
					request.setAttribute("MSG", "查無資料!!");
				}
				
				request.setAttribute("Form2Datas",list);
				
				rs.close();
				stmt.close();
				
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("queryCondition", "yes");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				
				hr_tools.close();
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
				
				Form.setEHF030102T0_02("");
				Form.setEHF030102T0_02_TXT("");
				Form.setEHF030102T0_03("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF030102M0F Form = (EHF030102M0F) form;
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030102T0_01(arrid[0]);
		}else if("".equals(Form.getEHF030102T0_01()) || Form.getEHF030102T0_01() == null){
			request.setAttribute("MSG", "請先選擇一筆要修改的資料!!");
			return queryDatas(mapping, form, request, response);
		}
    	
    	try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "editDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT * FROM EHF030102T0 " +
				" WHERE 1=1 " +
				" AND EHF030102T0_01 = '"+Form.getEHF030102T0_01()+"' " +
				" AND EHF030102T0_08 = '"+getLoginUser(request).getCompId()+"' " ;
				

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				//Map depMap = ems_tools.getDepNameMap(getEmscontext(), getLoginUser(request).getUserId(), Integer.parseInt(getLoginUser(request).getUserCode()), getLoginUser(request).getCompId());
				Map depMap = hr_tools.getDepNameMap(getLoginUser(request).getCompId());
				if(rs.next()){
					
					Form.setEHF030102T0_02((String)((Map)depMap.get(rs.getString("EHF030102T0_02"))).get("SHOW_DEPT_ID"));  //部門組織(代號)
					//Form.setEHF030102T0_02_TXT( (String) ((Map)depMap.get(rs.getString("EHF030102T0_02"))).get("SHOW_DESC") ); //部門組織(中文名稱)
					Form.setEHF030102T0_02_TXT((String)((Map)depMap.get(rs.getString("EHF030102T0_02"))).get("SHOW_DESC"));
					Form.setEHF030102T0_03(rs.getString("EHF030102T0_03"));  //薪資項目
					Form.setEHF030102T0_04(rs.getString("EHF030102T0_04"));  //所得稅別
					Form.setEHF030102T0_05(rs.getString("EHF030102T0_05"));  //類別
					Form.setEHF030102T0_06(rs.getString("EHF030102T0_06"));  //金額
					Form.setEHF030102T0_07(rs.getString("EHF030102T0_07"));  //備註
					Form.setEHF030102T0_09(rs.getString("EHF030102T0_09"));  //薪資計算公式
					Form.setUSER_CREATE(rs.getString("USER_CREATE"));
					Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
					Form.setVERSION(rs.getInt("VERSION"));
					Form.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
					
				}
				
				
				request.setAttribute("Form2Datas",list);
//				Form.setEHF030102C(list);
				
				rs.close();
				stmt.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("chk_type", "yes");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
			} else {
				
				request.setAttribute("chk_type", "yes");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
			hr_tools.close();
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
//		return saveData(mapping, form, request, response);
	}

	public ActionForward saveData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030102M0F Form = (EHF030102M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			BA_Vaildate ve=BA_Vaildate.getInstance();
			if("".equals(Form.getEHF030102T0_01())){
				request.setAttribute("MSG","薪資項目金額序號錯誤!!請重新查詢資料!!");
				return init(mapping, form, request, response);
				
			}

			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				
				" UPDATE EHF030102T0 SET  " + 
				//" EHF030102T0_02=?, EHF030102T0_03=?, " +
				" EHF030102T0_04=?, EHF030102T0_05=?, " +
				" EHF030102T0_06=?, EHF030102T0_07=?, EHF030102T0_09=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030102T0_01 = '"+Form.getEHF030102T0_01()+"' " +
				" AND EHF030102T0_08 = '"+getLoginUser(request).getCompId()+"' " ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				//p6stmt.setString(indexid, Form.getEHF030102T0_02());  //部門組織(代號)
				//indexid++;
				//p6stmt.setString(indexid, Form.getEHF030102T0_03());  //薪資項目
				//indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_04()==null?"01":Form.getEHF030102T0_04());  //所得稅別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_05()==null?"01":Form.getEHF030102T0_05());  //類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_06());  //金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_07());  //備註
				indexid++;
//				p6stmt.setString(indexid, getLoginUser(request).getCompId());
//				indexid++;
				p6stmt.setString(indexid, Form.getEHF030102T0_09()==null?"":Form.getEHF030102T0_09());  //薪資計算公式
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());
				indexid++;
				
//				System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				request.setAttribute("MSG","儲存成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
	
				
			} else {
				
				request.setAttribute("chk_type", "yes");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
		} catch (Exception e) {

			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			
			request.setAttribute("MSG","薪資項目金額儲存錯誤!!請重新操作!!");
						
			System.out.println(e);
			e.printStackTrace();
			
			return init(mapping, form, request, response);
			
			
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return editDataForm(mapping, form, request, response);
	}

	public ActionForward delData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030102M0F Form = (EHF030102M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030102T0_01(arrid[0]);
		}else if(!"".equals(Form.getEHF030102T0_01()) && Form.getEHF030102T0_01() != null){
			//已存在薪資項目序號
			//Do Nothing!!
		}else{
			request.setAttribute("MSG", "請先選擇一筆要刪除的資料!!");
			return queryForm(mapping, form, request, response);
		}
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			if (lc_errors.isEmpty()) {
					
					String comp_id = getLoginUser(request).getCompId();
				
					Statement stmt = conn.createStatement();
					ResultSet rs = null;
					String sql = "";
					
					//檢查薪資項目金額是否還有被使用，若有被使用則不可刪除
					sql  = " SELECT EHF030200T1_02 " +
						   " FROM EHF030200T1 " +
						   " WHERE 1=1 " +
						   " AND EHF030200T1_02 = '"+Form.getEHF030102T0_01()+"' " +  //薪資項目金額序號
						   " AND EHF030200T1_06 = '1' " +  //薪資項目金額序號
						   " AND EHF030200T1_04 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼

					rs = stmt.executeQuery(sql);
					
					if(!rs.next()){
						
						//檢查薪資項目是否有被其他薪資項目金額使用
						sql = "" +
						" SELECT " +
						" EHF030102T0_01 " +
						" FROM EHF030102T0 " +
						" WHERE 1=1 " +
						" AND EHF030102T0_03 = " +
						" ( " +
						"  SELECT " +
						"  EHF030102T0_03 " +
						"  FROM EHF030102T0 " +
						"  WHERE 1=1 " +
						"  AND EHF030102T0_01 = '"+Form.getEHF030102T0_01()+"' " +  //薪資項目金額代號
						"  AND EHF030102T0_08 = '"+comp_id+"' " +  //公司代碼
						" ) " +
						" AND EHF030102T0_01 <> '"+Form.getEHF030102T0_01()+"' " +  //薪資項目金額代號
						" AND EHF030102T0_08 = '"+comp_id+"' ";  //公司代碼
						
						ResultSet rs2 = stmt.executeQuery(sql);
						
						if(!rs2.next()){
							
							//表示不存在其他薪資項目金額使用此薪資項目
							//可以刪除原始的薪資項目資料
							
							sql = "" +
							" DELETE FROM EHF030101T0 " +
							" WHERE 1=1 " +
							" AND EHF030101T0_01 = " +  //薪資項目序號
							" ( " +
							"  SELECT " +
							"  EHF030102T0_03 " +
							"  FROM EHF030102T0 " +
							"  WHERE 1=1 " +
							"  AND EHF030102T0_01 = '"+Form.getEHF030102T0_01()+"' " +  //薪資項目金額代號
							"  AND EHF030102T0_08 = '"+comp_id+"' " +  //公司代碼
							" ) " +  
							" AND EHF030101T0_05 = '"+comp_id+"' ";  //公司代碼
							
							stmt.execute(sql);
						}
						
						rs2.close();
						
						//刪除薪資項目金額資料
						sql = "" +
						" DELETE FROM EHF030102T0 " +
						" WHERE 1=1 " +
						" AND EHF030102T0_01 = '"+Form.getEHF030102T0_01()+"' " +  //薪資項目金額序號
						" AND EHF030102T0_08 = '"+comp_id+"' ";  //公司代碼
						
						stmt.execute(sql);
						conn.commit();
						
						request.setAttribute("MSG", "刪除成功");
					}else{
						//薪資項目金額已被使用不可刪除
						request.setAttribute("MSG", "薪資項目金額已被使用不可刪除");
						
						rs.close();
						stmt.close();
						
						return queryForm(mapping, form, request, response);
					}
					
					rs.close();
					stmt.close();
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return init(mapping, form, request, response);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return init(mapping, form, request, response);
	}
	
	/**
	 * 畫面導向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward redirect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("redirect");
	}
	
	/**
	 * 取得類別中文名稱
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public String getTypeName(String type_key){
		
		String type = "";
		
		if(!"".equals(type_key) && (type_key != null )){
			if(type_key.equals("01")){
				type = "主管";
			}else if(type_key.equals("02")){
				type = "非主管";
			}else if(type_key.equals("03")){
				type = "特殊";
			}
		}
		
		
		return type;
	}
	
	/**
	 * 取得所得稅別中文名稱
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public String getTaxTypeName(String taxtype_key){
		
		String taxtype = "";
		
		if(!"".equals(taxtype_key) && (taxtype_key != null )){
			if(taxtype_key.equals("01")){
				taxtype = "是";  //應稅
			}else if(taxtype_key.equals("02")){
				taxtype = "否";  //未稅
			}
		}
		
		return taxtype;
	}
	
}