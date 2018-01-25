package com.spon.utils.sc.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.apache.struts.util.MessageResources;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.SC0011F;
import com.spon.utils.sc.forms.SC001F;
import com.spon.utils.sc.forms.SC002F;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 程式管理
 * @version 1.0
 * @created 04-四月-2006 下午 02:20:03
 */
public class SC002A extends NewDispatchAction {


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
		SC002F Form = (SC002F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        Form.setSC0020_08(userform(request).getSC0030_14());
        request.setAttribute("action","addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		
			
			try {
				if (lc_errors.isEmpty()) {
				sql = "insert into SC0020 (" + "SC0020_01," + "SC0020_02," + "SC0020_03," + "SC0020_04," + "SC0020_05," + "SC0020_06," + "SC0020_07," 
				    + "SC0020_08,"+ "DATE_CREATE,"+ "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) values (?,?,?,?,?,?,?,?,NOW(),NOW(),?,?,?)";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1,Form.getSC0020_01());
				stmt.setString(2,Form.getSC0020_02());
				stmt.setString(3,Form.getSC0020_03());
				stmt.setString(4,Form.getSC0020_04());
				stmt.setString(5,Form.getSC0020_05());
				stmt.setInt(6,Form.getSC0020_06());
				stmt.setString(7,Form.getSC0020_07());
				stmt.setString(8,userform(request).getSC0030_14());
				stmt.setString(9,userform(request).getSC0030_01());
				stmt.setString(10,userform(request).getSC0030_01());
				
				stmt.setInt(11,1);
				
				
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
				System.out.println("SC002A.addData() "+e);
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
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		if(request.getAttribute("save_status")==null)
		{
			request.setAttribute("save_status","");
		}
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		if(request.getAttribute("save_status").equals("")){
		try{
			conn = tools.getConnection();
			
			SC002F Form = new SC002F();
			
			generateSelectBox(conn,Form,request);
				
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
			
			
			generateSelectBox(conn,form,request);			
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
	 * 取消作業，判斷查詢條件還在不在，如果在的話，就呼叫queryDatas
	 * 如果不在就呼叫init()
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
//		按下取消鈕後，回到顯示查詢結果的頁面
		if((SC002F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC002F)request.getSession().getAttribute("QueryString");
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
	public ActionForward delData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		SC002F Form = (SC002F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try{
			conn = tools.getConnection();
			Statement stmt = conn.createStatement();
			
			//確認使用者有選擇資料列後，才按下刪除鈕
			if (Form.getSC0020_01() == null){
				//如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
				request.setAttribute("MSG", "請先選擇一筆要刪除的資料。");
				return cancel(mapping, form, request, response);
			}
			stmt.execute("delete from SC0020 where SC0020_01='" + Form.getSC0020_01()+ "' AND SC0020_08='"+userform(request).getSC0030_14()+"'");
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
		form=(SC002F)request.getSession().getAttribute("QueryString");
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
	public ActionForward doQueryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute("Pageid",null);
		return queryDatas(mapping,form,request,response);
	}

	/**
	 * 編輯表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		SC002F Form = (SC002F) form;
		String sql = "";
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		//確認使用者有選擇資料列後，才按下編輯鈕
		if (Form.getSC0020_01() == null){
			//如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
			request.setAttribute("MSG", "請選擇一筆資料。");
			return cancel(mapping,form,request,response);
		}
		
		if(request.getAttribute("save_status")==null)
		{
			request.setAttribute("save_status","");
		}
		
		if(request.getAttribute("save_status").equals("")){
			
		try{
			
			conn = tools.getConnection();
			Statement stmt = conn.createStatement();

			sql = "select a.*,b.SC0030_04 as USER_CREATE_NAME, c.SC0030_04 as USER_UPDATE_NAME from SC0020 a" +
			" left join SC0030 b on a.USER_CREATE = b.SC0030_01 and b.SC0030_14 = a.SC0020_08 " +
			" left join SC0030 c on a.USER_UPDATE = c.SC0030_01 and c.SC0030_14 = a.SC0020_08 " +
			" where a.SC0020_01 = '" + Form.getSC0020_01() + "' "+
			" and a.SC0020_08 = '"+userform(request).getSC0030_14()+"' ";  //公司別條件
			
			System.out.println(sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			Form = new SC002F();
			if (rs.next()){
				
				Form.setSC0020_01(rs.getString("SC0020_01"));
				Form.setSC0020_02(rs.getString("SC0020_02"));
				Form.setSC0020_03(rs.getString("SC0020_03"));
				Form.setSC0020_04(rs.getString("SC0020_04"));
				Form.setSC0020_05(rs.getString("SC0020_05"));
				Form.setSC0020_06(rs.getInt("SC0020_06"));
				Form.setSC0020_07(rs.getString("SC0020_07"));
				BA_TOOLS ba=BA_TOOLS.getInstance();
				Form.setDATE_CREATE(ba.DateToChineseDate(rs.getTimestamp("DATE_CREATE")));
				Form.setDATE_UPDATE(ba.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
				Form.setUSER_CREATE(ba.GetUserName(conn,rs.getString("USER_CREATE"),userform(request).getSC0030_14()));
				Form.setUSER_UPDATE(ba.GetUserName(conn,rs.getString("USER_UPDATE"),userform(request).getSC0030_14()));
				Form.setVERSION(rs.getInt("VERSION"));
				
			}
			generateSelectBox(conn,form,request);			
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
			generateSelectBox(conn,form,request);		
			
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
	 * 程式一開始進來時，不做任何查詢
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		FormUtils.setFormDisplayMode(request, form, "inspect");
		request.setAttribute("Form1Datas", new ArrayList());
		request.getSession().removeAttribute("QueryString");
		request.setAttribute("MSG", "請查詢資料!");
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
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
	try{	
		conn = tools.getConnection();
		//generateSelectBox(conn,form,request);
		
		FormUtils.setFormDisplayMode(request, form, "inspect");
		
		
		SC002F queryform =(SC002F) form;
		ArrayList collection=new ArrayList();
		String sql = "";
		
		
		//Statement stmt = conn.createStatement();
		sql = "select * from SC0020 where 1=1 and SC0020_08='"+userform(request).getSC0030_14()+"'";
		try{
		if (queryform.getSC0020_01() != null & !queryform.getSC0020_01().equals("") ) sql += " and SC0020_01 like '%" + queryform.getSC0020_01() + "%'";
		if (queryform.getSC0020_02() != null & !queryform.getSC0020_02().equals("") ) sql += " and SC0020_02 like '%" + queryform.getSC0020_02() + "%'";
		if (queryform.getSC0020_03() != null & !queryform.getSC0020_03().equals("")) sql += " and SC0020_03 like '%" + queryform.getSC0020_03() + "%'";
		if (queryform.getSC0020_04() != null & !queryform.getSC0020_04().equals("")) sql += " and SC0020_04 like '%" + queryform.getSC0020_04() + "%'";
		if (queryform.getSC0020_05() != null & !queryform.getSC0020_05().equals("")) sql += " and SC0020_05 like '%" + queryform.getSC0020_05() + "%'";
		sql+=" order by SC0020_04,SC0020_06 ";
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		//colletion 列印 sql語法要加密  
		request.setAttribute("Form1Datassql",new Security().encode(sql));
		
		while (rs.next()) {
			
			SC002F Form = new SC002F();
			
			Form.setSC0020_01(rs.getString("SC0020_01"));
			Form.setSC0020_02(rs.getString("SC0020_02"));
			//Form.setSC0020_03(rs.getString("SC0020_03"));
			Form.setSC0020_04(rs.getString("SC0020_04"));
			//Form.setSC0020_05(rs.getString("SC0020_05"));
			Form.setSC0020_06(rs.getInt("SC0020_06"));
			Form.setSC0020_07(rs.getString("SC0020_07"));
			
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
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DataSource ds;
		Connection conn = null;
		try{
			SC002F Form = new SC002F();
			
			generateSelectBox(conn,form,request);
			
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
	 * 儲存編輯結果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		SC002F Form = (SC002F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Form.setSC0020_08(userform(request).getSC0030_14());
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("update SC0020 set " +
					"SC0020_02=?,SC0020_03=?,SC0020_04=?,SC0020_05=?,SC0020_06=?,SC0020_07=?" +
					",DATE_UPDATE=NOW(),USER_UPDATE=?,VERSION=?" +
					" where SC0020_01='" + Form.getSC0020_01()+ "' and SC0020_08='"+userform(request).getSC0030_14()+"'");
			
			
			stmt.setString(1, Form.getSC0020_02());
			stmt.setString(2, Form.getSC0020_03());
			stmt.setString(3, Form.getSC0020_04());
			stmt.setString(4, Form.getSC0020_05());
			stmt.setInt(5, Form.getSC0020_06());
			stmt.setString(6, Form.getSC0020_07());
			stmt.setString(7,userform(request).getSC0030_01());
			stmt.setInt(8, Form.getVERSION()+1);
			
			
			stmt.executeUpdate();
			stmt.close();
			conn.commit();
			
			request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status","error");
				if(request.getAttribute("FormData")!=null)
				{
					
				 form=(SC002F)request.getAttribute("FormData");
				 request.setAttribute("MSG",request.getAttribute("ErrMSG") );
				}
				return editDataForm(mapping, form, request, response);
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "修改失敗!");
			System.out.println("SC002A.saveData() "+e );
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
		
		
		if((SC002F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC002F)request.getSession().getAttribute("QueryString");
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
	 * 顯示查詢資料
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
	 * 取得所有的程式清單
	 * @param form 
	 * @param request 
	 */
	public ArrayList showPgmList( Connection conn,ActionForm form,HttpServletRequest request) {
		
		ArrayList collection=new ArrayList();
		String sql = "";
		
		
		//Statement stmt = conn.createStatement();
		sql = " select * from SC0020,SC0040  where 1=1  and SC0020_04=SC0040_01  and SC0020_08 = '"+userform(request).getSC0030_14()+"' " +
			  " and SC0040_05 = '"+userform(request).getSC0030_14()+"' ";  //公司別條件
		try{
		
		sql+=" order by SC0020_01 ";
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()){
			
			SC002F Form = new SC002F();
			
			Form.setSC0020_01(rs.getString("SC0020_01"));
			Form.setSC0020_02(rs.getString("SC0020_02"));
			Form.setSC0020_03(rs.getString("SC0020_03"));
			Form.setSC0020_04(rs.getString("SC0020_04")+" "+rs.getString("SC0040_02"));
			Form.setSC0020_05(rs.getString("SC0020_05"));
			Form.setSC0020_06(rs.getInt("SC0020_06"));
			
			collection.add(Form);
		}
		rs.close();
		stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			return collection;
	}

	/**
	 * 以群組代碼查詢程式清單
	 * 
	 * @param SC001_01    群組代碼
	 * @throws SQLException 
	 */
	public ArrayList showPgmList(Connection conn,ActionForm form,HttpServletRequest request,String SC001_01) throws SQLException {

		ArrayList collection=new ArrayList();
		String sql = "";
		
		//Statement stmt = conn.createStatement();
		sql = " select * from SC0020 " +
			  " left join SC0011 on  SC0011_02=SC0020_01 and SC0011_01='"+SC001_01+"' "+
			  " and SC0011_09 = SC0020_08 "+
			  " where SC0020_08 = '"+userform(request).getSC0030_14()+"' "+
		      " order by SC0020_04,SC0020_01 ";
		
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()){
			SC0011F Form2 = new SC0011F();
			Form2.setSC0011_02(rs.getString("SC0020_01"));
			Form2.setSC0020_02(rs.getString("SC0020_02"));
			Form2.setSC0011_08(rs.getString("SC0011_08"));
			Form2.setSC0020_04(rs.getString("SC0020_04"));
			if(rs.getString("SC0011_01")!=null)
			{
				if(rs.getString("SC0011_01").equals(SC001_01))
				{	
					Form2.setCHECKED(true);
					
					Form2.setSC0011_03(rs.getInt("SC0011_03")==1?true:false);
					Form2.setSC0011_04(rs.getInt("SC0011_04")==1?true:false);
					Form2.setSC0011_05(rs.getInt("SC0011_05")==1?true:false);
					Form2.setSC0011_06(rs.getInt("SC0011_06")==1?true:false);
					Form2.setSC0011_07(rs.getInt("SC0011_07")==1?true:false);
				}
			}
				
			
			collection.add(Form2);
		
		}
		rs.close();
		stmt.close();
		return collection;
	}
	/**
	 * 取得使用者可使用的程式所屬於的Folder List，包含其所有的父節點
	 * @param conn
	 * @param form
	 * @param request
	 * @param SC0031_01
	 * @param LOGINMODE
	 * @return
	 */
	public ArrayList showFolderListForUser(Connection conn,ActionForm form,HttpServletRequest request,String SC0031_01,String LOGINMODE,String SC0031_04){
		
		ArrayList collection=new ArrayList();
		String sql = "";
		//判斷使用者登入的方式
		if (LOGINMODE.equals("key")){
			//使用USBKey登入
			sql = "select DISTINCT a.SC0020_01,a.SC0020_02,a.SC0020_03,a.SC0020_04,a.SC0020_05,a.SC0020_06,a.SC0020_07 " +
					"from SC0020 a,SC0031 b,SC0011 c " +
					"where  c.SC0011_02=a.SC0020_01 " +
					"and b.SC0031_02=c.SC0011_01 and SC0020_03<>'FOLDER' " +
					"and a.SC0020_08=c.SC0011_09 and a.SC0020_08=b.SC0031_04 "+
					"and b.SC0031_01='"+SC0031_01+"' and b.SC0031_04='"+SC0031_04+"' order by SC0020_04,SC0020_06";
		}else{
			//直接進入
			sql = "select DISTINCT a.SC0020_04 as FOLDERLIST " +
					"from SC0020 a,SC0031 b,SC0011 c " +
					"where a.SC0020_07='N' and c.SC0011_02=a.SC0020_01 and SC0020_03<>'FOLDER' " +
					"and a.SC0020_08=c.SC0011_09 and a.SC0020_08=b.SC0031_04 "+
					"and b.SC0031_02=c.SC0011_01 and b.SC0031_01='"+SC0031_01+"' and b.SC0031_04='"+SC0031_04+"' order by 1";
		}
		
		try{
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		String folderList = "";
		while (rs.next()){
			if (!folderList.equals("")) folderList += ","; 
			collection.add(rs.getString("FOLDERLIST"));
			folderList += "'" + rs.getString("FOLDERLIST") + "'";
		}
		//取得所有符合權限的父節點
		collection = findParentFolder(conn, folderList, collection,request);
		
		rs.close();
		stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return collection;	
	}
	
	/**
	 * 取得所有符合權限的父節點
	 * @param conn
	 * @param folderList  folder 清單，每個folder以單引號括起來後，再以逗號隔開
	 *                    例如：'SM03','SM04' ...
	 * @param coll        處理集合
	 * @return
	 */
	public ArrayList findParentFolder(Connection conn, String folderList, ArrayList coll,HttpServletRequest request){
		try {
			String sql = "";
			sql = "select distinct SC0020_04 from SC0020 where SC0020_01 in (" + folderList + ") and SC0020_08='"+userform(request).getSC0030_14()+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			folderList = "";
			while (rs.next()){
				if (!folderList.equals("")) folderList += ",";
				folderList += "'" + rs.getString("SC0020_04") + "'";
				if (!coll.contains(rs.getString("SC0020_04")))
					coll.add(rs.getString("SC0020_04"));
			}
			rs.close();
			stmt.close();
			if (!folderList.equals(""))
				return findParentFolder(conn, folderList, coll,request);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return coll;
	}

	public ArrayList findAllParentFolder(Connection conn, ArrayList coll, ArrayList collToAdd, String target,HttpServletRequest request){
		try {
			Iterator list = coll.iterator();
			String sql = "";
			sql = "select SC0020_04 from SC0020 where SC0020_01='" + target + "' and SC0020_08='"+userform(request).getSC0030_14()+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()){
				String parentFolder = rs.getString("SC0020_04");
				if (!coll.contains(parentFolder) && !collToAdd.contains(parentFolder))
					collToAdd.add(parentFolder);
			}else{
				return findAllParentFolder(conn, coll, collToAdd, target,request);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return coll;
	}
	
	/**
	 * 透過使用者代號取得符合權限的程式清單
	 * 
	 * @param SC0030_01    使用者代碼
	 */
	public ArrayList showPgmListForUser(Connection conn,ActionForm form,HttpServletRequest request,String SC0031_01,String LOGINMODE,String SC0031_04) {
		
		
		ArrayList collection=new ArrayList();
		String sql = "";
		//判斷使用者登入的方式
		if (LOGINMODE.equals("key")){
			//使用USBKey登入
			sql = "select DISTINCT a.SC0020_01,a.SC0020_02,a.SC0020_03,a.SC0020_04,a.SC0020_05,a.SC0020_06,a.SC0020_07 " +
					"from SC0020 a,SC0031 b,SC0011 c " +
					"where  c.SC0011_02=a.SC0020_01 " +
					"and b.SC0031_02=c.SC0011_01 and SC0020_03<>'FOLDER' " +
					"and a.SC0020_08=c.SC0011_09 and a.SC0020_08=b.SC0031_04 "+
					"and b.SC0031_01='"+SC0031_01+"' and b.SC0031_04='"+SC0031_04+"' order by SC0020_04,SC0020_06";
		}else{
			//直接進入
			sql = "select DISTINCT a.SC0020_01,a.SC0020_02,a.SC0020_03,a.SC0020_04,a.SC0020_05,a.SC0020_06,a.SC0020_07 " +
					"from SC0020 a,SC0031 b,SC0011 c " +
					"where a.SC0020_07='N' and c.SC0011_02=a.SC0020_01 and SC0020_03<>'FOLDER' " +
					"and a.SC0020_08=c.SC0011_09 and a.SC0020_08=b.SC0031_04 "+
					"and b.SC0031_02=c.SC0011_01 and b.SC0031_01='"+SC0031_01+"' and b.SC0031_04='"+SC0031_04+"' order by SC0020_04,SC0020_06";
		}
		
		try{
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()){
			
			SC002F Form = new SC002F();
			
			Form.setSC0020_01(rs.getString("SC0020_01"));
			Form.setSC0020_02(rs.getString("SC0020_02"));
			Form.setSC0020_03(rs.getString("SC0020_03"));
			Form.setSC0020_04(rs.getString("SC0020_04"));
			Form.setSC0020_05(rs.getString("SC0020_05"));
			Form.setSC0020_06(rs.getInt("SC0020_06"));
			
			
			collection.add(Form);
		}
		rs.close();
		stmt.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
			return collection;
	}
	
	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request){
		boolean openbyme = false;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		if(conn==null){
			try {
				conn = tools.getConnection();
	            openbyme=true;
	        } catch (Exception e2) {
	            e2.printStackTrace();
	        }
		}	
			
		String sql="select SC0040_01 as item_id,SC0040_02 as item_value from SC0040 where SC0040_05='"+userform(request).getSC0030_14()+"'";
		request.setAttribute("listSC0020_04",this.getSelectOptions(conn,sql,true));
		
		
		if(openbyme){
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
}