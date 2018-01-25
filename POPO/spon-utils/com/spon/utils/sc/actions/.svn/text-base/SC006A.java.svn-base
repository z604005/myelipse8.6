package com.spon.utils.sc.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;


import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.SC006F;
import com.spon.utils.util.BA_TOOLS;


import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 系統參數設定
 * @version 1.0
 * @updated 13-四月-2006 下午 06:56:25
 */
public class SC006A extends NewDispatchAction {


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
	public ActionForward addData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		String sql = "";
		SC006F Form = (SC006F) form;
		
		//DataSource ds;
		//ds = getDataSource(request, "SPOS");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		
		try {
			conn = tools.getConnection();
        } catch (SQLException e2) {
            e2.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
        Form.setSC0060_05(userform(request).getSC0030_14());
        request.setAttribute("action","addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
	
			
			try {
				if (lc_errors.isEmpty()) {
				sql = "insert into SC0060 (" + "SC0060_01," + "SC0060_02," + "SC0060_03," + "SC0060_04," + "SC0060_05," +"DATE_CREATE,"
						+ "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) values (?,?,?,?,?,NOW(),NOW(),?,?,?)";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1,Form.getSC0060_01());
				stmt.setString(2,Form.getSC0060_02());
				stmt.setString(3,Form.getSC0060_03());
				stmt.setString(4,Form.getSC0060_04());
				stmt.setString(5,userform(request).getSC0030_14());
				stmt.setString(6,userform(request).getSC0030_01());
				stmt.setString(7,userform(request).getSC0030_01());
				
				stmt.setInt(8,1);
				
				
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
				System.out.println("SC006A.addData() "+e);
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
		return addDataForm(mapping, form, request, response);
	}

	/**
	 * 新增資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		if(request.getAttribute("save_status")==null)
		{
			request.setAttribute("save_status","");
		}
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		if(request.getAttribute("save_status").equals(""))
		{
		try{
			
			conn = tools.getConnection();
			
			SC006F Form = new SC006F();
			
			//generateSelectBox(conn,Form,request);
				
			request.setAttribute("Form1Datas", Form);
			
			//將表單的顯示模式設定為create (新增模式)
			FormUtils.setFormDisplayMode(request, form, "create");
			//指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "addData");
			
		}catch (Exception e){
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		}
		else
		{
			
			
			//generateSelectBox(conn,form,request);			
			request.setAttribute("Form1Datas", form);
			
			//將表單的顯示模式設定為create (新增模式)
			FormUtils.setFormDisplayMode(request, form, "create");
			//指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "addData");
			
		}
		//依據 struts-config.xml 的定義forward到showedit
		//雖然是新增狀態，但為了維護便利，不須再為新增再作一個表單
		//就沿用showedit即可
		
		
		
		request.setAttribute("action", "新增");
		
		String Required="true";
		String Mode="E,R,I";
		
		request.setAttribute("Required",Required);
		request.setAttribute("Mode",Mode);
		
        return mapping.findForward("showedit");
	}

	/**
	 * 取消作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
//		按下取消鈕後，回到顯示查詢結果的頁面
		if((SC006F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC006F)request.getSession().getAttribute("QueryString");
			return queryDatas(mapping, form, request, response);
		}
		else
		{
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
	public ActionForward delData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		SC006F Form = (SC006F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try{
			conn = tools.getConnection();
			
			Statement stmt = conn.createStatement();
			//確認使用者有選擇資料列後，才按下刪除鈕
			if (Form.getSC0060_01() == null){
				//如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
				request.setAttribute("MSG", "請先選擇一筆要刪除的資料。");
				return cancel(mapping, form, request, response);
			}
			stmt.execute("delete from SC0060 where SC0060_01='" + Form.getSC0060_01()+ "' and SC0060_05='"+userform(request).getSC0030_14()+"'");
			stmt.close();
			conn.commit();
			
		}catch (Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", "刪除失敗!");
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//刪除資料後，回到顯示查詢結果的頁面
		form=(SC006F)request.getSession().getAttribute("QueryString");
        return queryDatas(mapping,form,request,response);
	}

	/**
	 * 執行查詢，實際查詢交給queryDatas做
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward doQueryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		request.getSession().setAttribute("Pageid",null);
		return queryDatas(mapping,form,request,response);
	}

	/**
	 * 編修資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		SC006F Form = (SC006F) form;
		String sql = "";
		//確認使用者有選擇資料列後，才按下編輯鈕
		if (Form.getSC0060_01() == null){
			//如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
			request.setAttribute("MSG", "請選擇一筆資料。");
			return cancel(mapping,form,request,response);
		}
		
		if(request.getAttribute("save_status")==null)
		{
			request.setAttribute("save_status","");
		}
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		if(request.getAttribute("save_status").equals(""))
		{
		try{
			conn = tools.getConnection();
			Statement stmt = conn.createStatement();

			sql = " select a.*,b.SC0030_04 as USER_CREATE_NAME, c.SC0030_04 as USER_UPDATE_NAME from SC0060 a " +
			" left join SC0030 b on a.USER_CREATE = b.SC0030_01 and b.SC0030_14 = a.SC0060_05 " +
			" left join SC0030 c on a.USER_UPDATE = c.SC0030_01 and c.SC0030_14 = a.SC0060_05 " +
			" where a.SC0060_01 = '" + Form.getSC0060_01() + "' and a.SC0060_05 = '"+userform(request).getSC0030_14()+"' ";
			
			
			
			ResultSet rs = stmt.executeQuery(sql);
			Form = new SC006F();
			if (rs.next()){
				
				Form.setSC0060_01(rs.getString("SC0060_01"));
				Form.setSC0060_02(rs.getString("SC0060_02"));
				Form.setSC0060_03(rs.getString("SC0060_03"));
				Form.setSC0060_04(rs.getString("SC0060_04"));
				
				BA_TOOLS ba=BA_TOOLS.getInstance();
				Form.setDATE_CREATE((rs.getDate("DATE_CREATE").toString()));
				Form.setDATE_UPDATE((rs.getDate("DATE_UPDATE").toString()));
				Form.setUSER_CREATE(ba.GetUserName(conn,rs.getString("USER_CREATE"),userform(request).getSC0030_14()));
				Form.setUSER_UPDATE(ba.GetUserName(conn,rs.getString("USER_UPDATE"),userform(request).getSC0030_14()));
				Form.setVERSION(rs.getInt("VERSION"));
				
			}
			//generateSelectBox(conn,form,request);			
			request.setAttribute("Form1Datas", Form);
			//產生下拉選單
		
			//將表單的顯示模式設定為edit (編輯模式)
			FormUtils.setFormDisplayMode(request, form, "edit");
			//指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "saveData");
			rs.close();
			stmt.close();
		}catch (Exception e){
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		}
		else
		{
			//generateSelectBox(conn,form,request);		
			
			request.setAttribute("Form1Datas", form);
			
			//將表單的顯示模式設定為edit (編輯模式)
			FormUtils.setFormDisplayMode(request, form, "edit");
			//指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "saveData");
			
			
		
		}
		//依據 struts-config.xml 的定義forward到showedit
		request.setAttribute("action", "修改");
		
//		判斷欄位是否為必要
		String Required="true";
		String Mode="E,R,I";
		
		request.setAttribute("Required",Required);
		request.setAttribute("Mode",Mode);
	   
        return mapping.findForward("showedit");
	}

	

	/**
	 * 一開始進來程式時顯示空的資料
	 * 在這裡要用isLogin判斷使用者是否有登入系統
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		FormUtils.setFormDisplayMode(request, form, "inspect");
		request.setAttribute("Form1Datas", new ArrayList());
		request.getSession().removeAttribute("QueryString");
		request.setAttribute("MSG", "請查詢資料!");
		return mapping.findForward("success");
	}

	/**
	 * 實際查詢資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
	try{	
		conn = tools.getConnection();
		//generateSelectBox(conn,form,request);
		
		FormUtils.setFormDisplayMode(request, form, "inspect");
		
		
		SC006F queryform =(SC006F) form;
		ArrayList collection=new ArrayList();
		String sql = "";
		
		
		//Statement stmt = conn.createStatement();
		sql = "select * from SC0060  where 1=1  and  SC0060_05='"+userform(request).getSC0030_14()+"'";
		try{
		if (queryform.getSC0060_01() != null & !queryform.getSC0060_01().equals("") ) sql += " and SC0060_01 like '%" + queryform.getSC0060_01() + "%'";
		if (queryform.getSC0060_02() != null & !queryform.getSC0060_02().equals("") ) sql += " and SC0060_02 like '%" + queryform.getSC0060_02() + "%'";
		if (queryform.getSC0060_03() != null & !queryform.getSC0060_03().equals("")) sql += " and SC0060_03 like '%" + queryform.getSC0060_03() + "%'";
		if (queryform.getSC0060_04() != null & !queryform.getSC0060_04().equals("")) sql += " and SC0060_04 like '%" + queryform.getSC0060_04() + "%'";
		
		
		sql+=" order by SC0060_01 ";
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			
			SC006F Form = new SC006F();
			
			Form.setSC0060_01(rs.getString("SC0060_01"));
			Form.setSC0060_02(rs.getString("SC0060_02"));
			Form.setSC0060_03(rs.getString("SC0060_03"));
			Form.setSC0060_04(rs.getString("SC0060_04"));
			
			
			collection.add(Form);
		}
		rs.close();
		stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		request.setAttribute("Form1Datas", collection);
		
	}catch(Exception e){
		e.printStackTrace();
	}finally{
		try {
			if (conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

		
		
		//request.setAttribute("FM0102Query", queryform);
		request.getSession().setAttribute("QueryString",form);
		//當要用到Datagrid時，才需要這段程式
		//Datagrid lc_datagrid = Datagrid.getInstance();
		//lc_datagrid.setDataClass(FM0102Form.class);
		//lc_datagrid.setData(new ArrayList(datas.values()));
		//FM0102Form.setDatagrid(lc_datagrid);
		
	
	
	
	//依據 struts-config.xml 的定義forward到success
	return mapping.findForward("success");
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		DataSource ds;
		Connection conn = null;
		try{
			SC006F Form = new SC006F();
			
			//generateSelectBox(conn,form,request);
			
			request.setAttribute("Form1Datas", Form);
			
			//將表單的顯示模式設定為edit (編輯模式)，因為struts-layout沒有定義查詢模式，只好用edit了
			FormUtils.setFormDisplayMode(request, form, "edit");
			//指定執行鈕，應該要呼叫的method
			
			request.setAttribute("BUTTON_TYPE", "doQueryDatas");
			
		}catch (Exception e){
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//依據 struts-config.xml 的定義forward到showedit
		request.setAttribute("action", "查詢");
		
		
		//判斷欄位是否為必要
		String Required="true";
		String Mode="E,R,I";
		
		Required="";
		Mode="E,E,I";
		
		request.setAttribute("Required",Required);
		request.setAttribute("Mode",Mode);
	
        return mapping.findForward("showedit");
	}

	/**
	 * 儲存編修資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		SC006F Form = (SC006F) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
        } catch (SQLException e2) {
            e2.printStackTrace();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        Form.setSC0060_05(userform(request).getSC0030_14());
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("update SC0060 set " +
					"SC0060_02=?,SC0060_03=?,SC0060_04=?"+
					",DATE_UPDATE=NOW(),USER_UPDATE=?,VERSION=? " +
					"where SC0060_01='" + Form.getSC0060_01()+ "' and SC0060_05='"+userform(request).getSC0030_14()+"'");
			
			
			stmt.setString(1, Form.getSC0060_02());
			stmt.setString(2, Form.getSC0060_03());
			stmt.setString(3, Form.getSC0060_04());
			
			stmt.setString(4,userform(request).getSC0030_01());
			stmt.setInt(5, Form.getVERSION()+1);
			
			
			stmt.executeUpdate();
			stmt.close();
			conn.commit();
			
			request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status","error");
				if(request.getAttribute("FormData")!=null)
				{
					
				 form=(SC006F)request.getAttribute("FormData");
				 request.setAttribute("MSG",request.getAttribute("ErrMSG") );
				}
				return editDataForm(mapping, form, request, response);
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "修改失敗!");
			System.out.println("SC006A.saveData() "+e );
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		//儲存後，回到顯示查詢結果的頁面
		
		
		if((SC006F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC006F)request.getSession().getAttribute("QueryString");
			return queryDatas(mapping, form, request, response);
		}
		else
		{
			return init(mapping, form, request, response);
		}
//		form=(FM0102Form)request.getSession().getAttribute("FM0102Query");
//		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 取得系統參數
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param param
	 * @throws SQLException 
	 */
	public String getSysParam(Connection conn, ActionForm form, HttpServletRequest request, String param) throws SQLException{
		String value="";
		String sql = "select SC0060_03 from SC0060  where SC0060_01='"+param+"' and  SC0060_05='"+userform(request).getSC0030_14()+"'";
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
			
		if(	rs.next())
		{
			value=rs.getString(1);
		}
		rs.close();
		stmt.close();
		return value; 
	}

}