package com.spon.utils.sc.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.LoginForm;
import com.spon.utils.sc.forms.SC0031F;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.util.BA_TOOLS;


import fr.improve.struts.taglib.layout.util.FormUtils;


/**
 * 使用者管理
 * @version 1.0
 * @created 10-四月-2006 下午 04:37:53
 */
public class SC003A extends NewDispatchAction {

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
	public ActionForward addData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		String sql = "";
		SC003F Form = (SC003F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        
        request.setAttribute("action","addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
	
			
			try {
				if (lc_errors.isEmpty()) {
				sql = "insert into SC0030 (" + "SC0030_01," + "SC0030_02," + "SC0030_03,"
				+ "SC0030_04,"+ "SC0030_05,"+ "SC0030_06,"+ "SC0030_07,"+ "SC0030_08,"+ "SC0030_09,"+ "SC0030_10,"+ "SC0030_12,"+ "SC0030_14,"
				+ "DATE_CREATE,"+ "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) values (?,?,?,?,?,'0',' ',0,CURDATE(),?,?,?,NOW(),NOW(),?,?,1)";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1,Form.getSC0030_01());
				if(Form.getSC0030_02().equals(""))
					stmt.setString(2,BA_TOOLS.encodeInMD5(Form.getSC0030_03()));
				else
					stmt.setString(2,BA_TOOLS.encodeInMD5(Form.getSC0030_02()));
				stmt.setString(3,Form.getSC0030_03());
				stmt.setString(4,Form.getSC0030_04());
				stmt.setString(5,Form.getSC0030_05());
				stmt.setString(6,Form.getSC0030_10());
				stmt.setString(7,Form.getSC0030_12());
				stmt.setString(8,userform(request).getSC0030_14());
				stmt.setString(9,userform(request).getSC0030_01());
				stmt.setString(10,userform(request).getSC0030_01());
				
				
				stmt.execute();
				stmt.close();
				
				//新增表身資料
				addGroupDatas(conn, form, request);
				
				conn.commit();
				
				request.setAttribute("MSG", "新增成功!");
				} else {
					saveErrors(request, lc_errors);
					request.setAttribute("save_status", "error");
				}
			} catch (Exception e) {
				request.setAttribute("MSG", "新增失敗!");
				System.out.println("SC001A.addData() "+e);
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
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		if(request.getAttribute("save_status")==null)
		{
			request.setAttribute("save_status","");
		}
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		if(request.getAttribute("save_status").equals("")){
		try{
			conn = tools.getConnection();
			
			SC003F Form = new SC003F();
			SC001A sc001a=new SC001A();
			
			Form.setSC0030_05("Y");
			Form.setSC0031(sc001a.showGroupList(conn, Form, request,""));
			//Form.setSC0011(showGroupList(conn, Form, request));
			
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
	 * 新增使用者所屬群組資料(多筆)
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public void addGroupDatas(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{

		SC003F Form=(SC003F)form;
		
		String sql = "insert into SC0031 (" + "SC0031_01," + "SC0031_02," + "SC0031_03,"+ "SC0031_04,"
		+ "DATE_CREATE,"+ "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) values (?,?,?,NOW(),NOW(),?,?,?)";

		PreparedStatement stmt = conn.prepareStatement(sql);
		
		List SC0031=Form.getSC0031();

		for (int i = 0; i < SC0031.size(); i++) 
		{
			SC0031F Form1=(SC0031F)SC0031.get(i);
			if(Form1.isCHECKED()==true)
			{
				stmt.setString(1,Form.getSC0030_01());
				stmt.setString(2,Form1.getSC0031_02());
				stmt.setString(3,Form1.getSC0031_03());
				stmt.setString(4,userform(request).getSC0030_14());
				stmt.setString(5,userform(request).getSC0030_01());
				stmt.setString(6,userform(request).getSC0030_01());
				stmt.setInt(7,1);
				stmt.execute();
			}
		}
		
		stmt.close();
		
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
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
//		按下取消鈕後，回到顯示查詢結果的頁面
		if((SC003F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC003F)request.getSession().getAttribute("QueryString");
			return queryDatas(mapping, form, request, response);
		}
		else
		{
			return init(mapping, form, request, response);
		}
	}

	/**
	 * 刪除使用者所歸屬的所有群組資料
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param SC0030_01
	 * @throws SQLException 
	 */
	public void delAllData(Connection conn, ActionForm form, HttpServletRequest request, String SC0030_01) throws SQLException{
		String sql = "";
		
		//Statement stmt = conn.createStatement();
		sql = "delete from SC0031 where SC0031_01='"+SC0030_01+"' and SC0031_04='"+userform(request).getSC0030_14()+"'";
		
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
		
		stmt.close();
	}

	/**
	 * 刪除使用者資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward delData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		SC003F Form = (SC003F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try{
			conn = tools.getConnection();
			
			Statement stmt = conn.createStatement();
			//確認使用者有選擇資料列後，才按下刪除鈕
			if (Form.getSC0030_01() == null ){
				//如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
				request.setAttribute("MSG", "請先選擇一筆要刪除的資料。");
				return cancel(mapping, form, request, response);
			}
			stmt.execute("delete from SC0030 where SC0030_01='" + Form.getSC0030_01()+ "' and SC0030_14='"+userform(request).getSC0030_14()+"'");
			stmt.close();
			//刪除表身資料
			delAllData(conn, form, request,  Form.getSC0030_01());
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
		form=(SC003F)request.getSession().getAttribute("QueryString");
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
	 * 編輯資料表單
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		SC003F Form = (SC003F) form;
		String sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		//確認使用者有選擇資料列後，才按下編輯鈕
		if (Form.getSC0030_01() == null ){
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
			
			sql = "select a.*,b.SC0030_04 as USER_CREATE_NAME, c.SC0030_04 as USER_UPDATE_NAME from SC0030 a" +
			" left join SC0030 b on a.USER_CREATE = b.SC0030_01 and b.SC0030_14 = a.SC0030_14 " +
			" left join SC0030 c on a.USER_UPDATE = c.SC0030_01 and c.SC0030_14 = a.SC0030_14 " +
			" where a.SC0030_01 = '" + Form.getSC0030_01() + "' "+
			" and a.SC0030_14 = '"+userform(request).getSC0030_14()+"' ";
			
			
			ResultSet rs = stmt.executeQuery(sql);
			SC003F Form1 = new SC003F();
			if (rs.next()){
				
				Form1.setSC0030_01(rs.getString("SC0030_01"));
				Form1.setSC0030_02(rs.getString("SC0030_02"));
				Form1.setSC0030_03(rs.getString("SC0030_03"));
				Form1.setSC0030_04(rs.getString("SC0030_04"));
				Form1.setSC0030_05(rs.getString("SC0030_05"));
				//Form1.setSC0030_06(rs.getString("SC0030_06"));
				Form1.setSC0030_07(rs.getString("SC0030_07"));
				Form1.setSC0030_08(rs.getInt("SC0030_08"));
				Form1.setSC0030_09(rs.getString("SC0030_09"));
				Form1.setSC0030_10(rs.getString("SC0030_10"));
				Form1.setSC0030_11(rs.getString("SC0030_11"));
				Form1.setSC0030_12(rs.getString("SC0030_12"));
				 
				Form1.setDATE_CREATE(rs.getString("DATE_CREATE"));
				Form1.setDATE_UPDATE(rs.getString("DATE_UPDATE"));
				Form1.setUSER_CREATE(rs.getString("USER_CREATE_NAME"));
				Form1.setUSER_UPDATE(rs.getString("USER_UPDATE_NAME"));
				Form1.setVERSION(rs.getInt("VERSION"));
			
				SC001A sc001a=new SC001A();
				Form1.setSC0031(sc001a.showGroupList(conn, Form1, request,rs.getString("SC0030_01")));
				
			}
			generateSelectBox(conn,form,request);			
			request.setAttribute("Form1Datas", Form1);
			//產生下拉選單
		
			//將表單的顯示模式設定為edit (編輯模式)
			FormUtils.setFormDisplayMode(request, form, "edit");
			//指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "saveData");
			rs.close();
			stmt.close();
		}catch (Exception e){
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
	 * 批次產生下拉選單
	 * 過程中，需要透過呼叫getSelectOptions取得選項
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request){

	}

	/**
	 * 一開始進來程式時顯示空的資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		FormUtils.setFormDisplayMode(request, form, "inspect");
		SC003F Form = new SC003F();
		
		//generateSelectBox(conn,form,request);
		Form.setHSC0030_01("");
		Form.setHSC0030_03("");
		Form.setHSC0030_04("");
		request.setAttribute("Form0Datas", Form);
		
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
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		
		SC003F queryform =(SC003F) form;
	   
		try{	
			conn = tools.getConnection();
		 	//generateSelectBox(conn,form,request);
		
		FormUtils.setFormDisplayMode(request, queryform, "inspect");
		
		
		ArrayList collection=new ArrayList();
		String sql = "";
		
		//Statement stmt = conn.createStatement();
		sql = "select  * from SC0030 where 1=1  and SC0030_14='"+userform(request).getSC0030_14()+"'";
		
		if (queryform.getHSC0030_01() != null & !queryform.getHSC0030_01().equals("") ) sql += " and SC0030_01 like '%" + queryform.getHSC0030_01() + "%'";
		if (queryform.getHSC0030_03() != null & !queryform.getHSC0030_03().equals("")) sql += " and SC0030_03 like '%" + queryform.getHSC0030_03() + "%'";
		if (queryform.getHSC0030_04() != null & !queryform.getHSC0030_04().equals("")) sql += " and SC0030_04 like '%" + queryform.getHSC0030_04() + "%'";
		
		sql+=" order by SC0030_01 ";
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
	
		while (rs.next()) {
			
			SC003F Form1 = new SC003F();
			
			Form1.setSC0030_01(rs.getString("SC0030_01"));
			Form1.setSC0030_03(rs.getString("SC0030_03"));
			Form1.setSC0030_04(rs.getString("SC0030_04"));
			Form1.setSC0030_05(rs.getString("SC0030_05"));
			Form1.setSC0030_12(rs.getString("SC0030_12"));
			
			
			
			collection.add(Form1);
		}
		request.setAttribute("Form1Datas", collection);
		rs.close();
		stmt.close();
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
		request.getSession().setAttribute("QueryString",queryform);
		//當要用到Datagrid時，才需要這段程式
		//Datagrid lc_datagrid = Datagrid.getInstance();
		//lc_datagrid.setDataClass(FM0102Form.class);
		//lc_datagrid.setData(new ArrayList(datas.values()));
		//FM0102Form.setDatagrid(lc_datagrid);
		
		
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
	public ActionForward queryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		request.getSession().setAttribute("Pageid",null);
		return queryDatas(mapping,form,request,response);
//		DataSource ds;
//		Connection conn = null;
//		try{
//			SC003F Form = new SC003F();
//			
//			generateSelectBox(conn,form,request);
//			
//			
//			request.setAttribute("Form1Datas", Form);
//			
//			//將表單的顯示模式設定為edit (編輯模式)，因為struts-layout沒有定義查詢模式，只好用edit了
//			FormUtils.setFormDisplayMode(request, form, "edit");
//			//指定執行鈕，應該要呼叫的method
//			
//			request.setAttribute("BUTTON_TYPE", "doQueryDatas");
//			
//		}catch (Exception e){
//		}finally{
//			try {
//				if (conn != null && !conn.isClosed()){
//					conn.close();
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}
//		//依據 struts-config.xml 的定義forward到showedit
//		request.setAttribute("action", "查詢");
//		
//		
//		//判斷欄位是否為必要
//		String Required="true";
//		String Mode="E,R,I";
//		
//		Required="";
//		Mode="E,E,I";
//		
//		request.setAttribute("Required",Required);
//		request.setAttribute("Mode",Mode);
//	
//        return mapping.findForward("showedit");
	}

	/**
	 * 儲存編輯結果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		SC003F Form = (SC003F) form;
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
		
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			String sql="";
			PreparedStatement stmt=null;
			if(Form.getSC0030_02().equals(""))
			{
				sql="update SC0030 set " +
					"SC0030_03=?," +
					"SC0030_04=?," +
					"SC0030_05=?," +
					"SC0030_08=?," +
					"SC0030_10=?," +
					"SC0030_12=?"+
					",DATE_UPDATE=NOW()," +
					"USER_UPDATE=?," +
					"VERSION=?" +
					"where SC0030_01='" + Form.getSC0030_01()+ "'"+
					" and SC0030_14='"+userform(request).getSC0030_14()+"'";
				
				stmt = conn.prepareStatement(sql);
				
				
				stmt.setString(1, Form.getSC0030_03());
				stmt.setString(2, Form.getSC0030_04());
				stmt.setString(3, Form.getSC0030_05());
				
				stmt.setInt(4, Form.getSC0030_08());
				stmt.setString(5, Form.getSC0030_10());
				stmt.setString(6, Form.getSC0030_12());
				
				stmt.setString(7, userform(request).getSC0030_01());
				stmt.setInt(8, Form.getVERSION()+1);
				
			}else{
				sql = "update SC0030 set " +
				"SC0030_02=?," +
				"SC0030_03=?," +
				"SC0030_04=?," +
				"SC0030_05=?," +
				
				"SC0030_08=?," +
				"SC0030_09=NOW()," +
				"SC0030_10=?," +
				"SC0030_12=?"+
				",DATE_UPDATE=NOW()," +
				"USER_UPDATE=?," +
				"VERSION=?" +
				"where SC0030_01='" + Form.getSC0030_01()+ "'"+
				" and SC0030_14='"+userform(request).getSC0030_14()+"'";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, BA_TOOLS.encodeInMD5(Form.getSC0030_02()));
			stmt.setString(2, Form.getSC0030_03());
			stmt.setString(3, Form.getSC0030_04());
			stmt.setString(4, Form.getSC0030_05());
			
			stmt.setInt(5, Form.getSC0030_08());
			stmt.setString(6, Form.getSC0030_10());
			stmt.setString(7, Form.getSC0030_12());
			
			stmt.setString(8, userform(request).getSC0030_01());
			stmt.setInt(9, Form.getVERSION()+1);
				
			}
			
			
			stmt.executeUpdate();
			stmt.close();
			
			//處理表身資料
			delAllData(conn, form, request,Form.getSC0030_01());
			addGroupDatas(conn, form, request);
			//addPgmDatas(conn, form, request);
			
			
			conn.commit();
			
			request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status","error");
				if(request.getAttribute("FormData")!=null)
				{
					
				 form=(SC003F)request.getAttribute("FormData");
				 request.setAttribute("MSG",request.getAttribute("ErrMSG") );
				}
				return editDataForm(mapping, form, request, response);
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "修改失敗!");
			System.out.println("SC001A.saveData() "+e );
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
		
		
		if((SC003F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC003F)request.getSession().getAttribute("QueryString");
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
	 * 顯示查詢結果
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward showAllDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return null;
	}

	/**
	 * 顯示使用者清單
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	public ArrayList showUserList(Connection conn, ActionForm form, HttpServletRequest request){
		return null;
	}

	/**
	 * 取得登入失敗的次數
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public int getLoginFail(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{
		
		int num=0;	
		String sql = "";
		LoginForm lf = (LoginForm)form;
		
		try{
			sql = " select  SC0030_08 " +
			  " from SC0030 " +
			  " where 1=1 " +
			  " and SC0030_01 = '"+lf.getUserid()+"' "+  //登入帳號
		      " and SC0030_14 = '"+lf.getCompid()+"' ";  //公司代碼
		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				num = rs.getInt(1);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return num;
	}
	
	/**
	 * 增加登錄失敗的次數後
	 * 
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public void setLoginFail(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{

		String sql = "";
		LoginForm lf=(LoginForm)form;
		
		try{
			sql = " select  SC0030_08, SC0031_02 " +
			  	  " from SC0030 " +
			  	  " left join SC0031 ON SC0031_01 = SC0030_01 AND SC0030_14 = SC0031_04 "+
			  	  " where 1=1 " +
			  	  " and SC0030_01 = '"+lf.getUserid()+"' "+
			  	  " and SC0030_14 = '"+lf.getCompid()+"' ";
		
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				if(!"000".equals(rs.getString("SC0031_02"))){
					stmt.execute(" update SC0030 set SC0030_08 = "+(rs.getInt("SC0030_08")+1)+" " +
						     	 " where 1=1 " +
						     	 " and SC0030_01 = '"+lf.getUserid()+"' " +
						     	 " and SC0030_14='"+lf.getCompid()+"' ");
				}
			}
			
			rs.close();
			stmt.close();
			
			//更新資料庫
			conn.commit();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 成功登入清除失敗的次數
	 * 
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public void setLoginSuccess(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{

		String sql = "";
		LoginForm lf=(LoginForm)form;
		
		try{
			sql = " select  SC0030_08 " +
				  " from SC0030 " +
				  " where 1=1 " +
				  " and SC0030_01 = '"+lf.getUserid()+"' " +
				  " and SC0030_14 = '"+lf.getCompid()+"' ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next())
			{
				stmt.execute(" update SC0030 set SC0030_08 = 0 " +
						     " where 1=1 " +
						     " and SC0030_01 = '"+lf.getUserid()+"' " +
						     " and SC0030_14 = '"+lf.getCompid()+"' ");
			}
			
			rs.close();
			stmt.close();
			
			//更新資料庫
			conn.commit();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}