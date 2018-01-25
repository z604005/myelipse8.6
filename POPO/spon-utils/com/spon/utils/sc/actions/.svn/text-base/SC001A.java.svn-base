package com.spon.utils.sc.actions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
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
import com.spon.utils.sc.forms.SC0011F;
import com.spon.utils.sc.forms.SC001F;
import com.spon.utils.sc.forms.SC0031F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;




import fr.improve.struts.taglib.layout.util.FormUtils;


/**
 * 群組權限管理
 * @version 1.0
 * @created 07-四月-2006 下午 02:30:04 
 */
public class SC001A extends NewDispatchAction {

	
	

	/**
	 * 儲存新增資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward addData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DataSource ds;
		
		
		SC001F Form = (SC001F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Form.setSC0010_04(userform(request).getSC0030_14());
        request.setAttribute("action","addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		
			
			try {
				if (lc_errors.isEmpty()) {
				String sql = "";
				sql = "insert into SC0010 (" 
					+ "SC0010_01," + "SC0010_02," + "SC0010_03,"+ "SC0010_04,"
					+ "DATE_CREATE," + "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION"
					+ ") values ("
					+ "?,?,?,?,NOW(),NOW(),?,?,?)";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1,Form.getSC0010_01());
				stmt.setString(2,Form.getSC0010_02());
				stmt.setString(3,Form.getSC0010_03());
				stmt.setString(4,userform(request).getSC0030_14());
				stmt.setString(5,userform(request).getSC0030_01());
				stmt.setString(6,userform(request).getSC0030_01());
				stmt.setInt(7,1);
				
				
				stmt.execute();
				stmt.close();
				
				//新增表身資料
				addPgmDatas(conn, form, request);
				
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

		
//		if(request.getAttribute("save_status").equals(""))
		{
		try{
			conn = tools.getConnection();
			
			SC001F Form = new SC001F();
			SC002A sc002a=new SC002A();
			
			Form.setSC0011(sc002a.showPgmList(conn, Form, request,""));
			
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
//		else
//		{
//			
//			generateSelectBox(conn,form,request);			
//			request.setAttribute("Form1Datas", form);
//			
//			//將表單的顯示模式設定為create (新增模式)
//			FormUtils.setFormDisplayMode(request, form, "create");
//			//指定執行鈕，應該要呼叫的method
//			request.setAttribute("BUTTON_TYPE", "addData");
//			
//		}
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
	 * 批次新增群組使用程式
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public void addPgmDatas(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{
				SC001F Form=(SC001F)form;
		
				String sql = "insert into SC0011 (" + "SC0011_01," + "SC0011_02," + "SC0011_03,"
				+"SC0011_04,"+"SC0011_05,"+"SC0011_06,"+"SC0011_07,"+"SC0011_08,"+"SC0011_09,"
				+ "DATE_CREATE,"+ "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) values (?,?,?,?,?,?,?,?,?,NOW(),NOW(),?,?,?)";
		
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				List SC0011=Form.getSC0011();
		
				for (int i = 0; i < SC0011.size(); i++) 
				{
					SC0011F Form1=(SC0011F)SC0011.get(i);
					if(Form1.isCHECKED()==true)
					{
						stmt.setString(1,Form.getSC0010_01());
						stmt.setString(2,Form1.getSC0011_02());
						stmt.setInt(3,Form1.isSC0011_03()==true?1:0);
						stmt.setInt(4,Form1.isSC0011_04()==true?1:0);
						stmt.setInt(5,Form1.isSC0011_05()==true?1:0);
						stmt.setInt(6,Form1.isSC0011_06()==true?1:0);
						stmt.setInt(7,Form1.isSC0011_07()==true?1:0);
						stmt.setString(8,Form1.getSC0011_08());
						stmt.setString(9,userform(request).getSC0030_14());
						stmt.setString(10,userform(request).getSC0030_01());
						stmt.setString(11,userform(request).getSC0030_01());
						stmt.setInt(12,1);
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
		if((SC001F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC001F)request.getSession().getAttribute("QueryString");
			return queryDatas(mapping, form, request, response);
		}
		else
		{
			return init(mapping, form, request, response);
		}
	}

	/**
	 * 刪除群組可使用的所有程式資料
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param SC0010_01
	 * @throws SQLException 
	 */
	public void delAllData(Connection conn, ActionForm form, HttpServletRequest request, String SC0010_01) throws SQLException{
		
		String sql = "";
		
		//Statement stmt = conn.createStatement();
		sql = "delete from SC0011 where SC0011_01='"+SC0010_01+"' and SC0011_09='"+userform(request).getSC0030_14()+"'";
		
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
		
		stmt.close();
		
	}

	/**
	 * 刪除群組資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward delData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		SC001F Form = (SC001F) form;
		
		//DB連線
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try{
			conn = tools.getConnection();
			Statement stmt = conn.createStatement();
			
			//確認使用者有選擇資料列後，才按下刪除鈕
			if (Form.getSC0010_01() == null){
				//如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
				request.setAttribute("MSG", "請先選擇一筆要刪除的資料。");
				return cancel(mapping, form, request, response);
			}
			if (Form.getSC0010_01().equals("000")){
				request.setAttribute("MSG", "預設群組 000 系統管理 不得刪除。");
				return cancel(mapping, form, request, response);
			}
			stmt.execute("delete from SC0010 where SC0010_01='" + Form.getSC0010_01()+ "' and SC0010_04='"+userform(request).getSC0030_14()+"'");
			stmt.close();
			//刪除表身資料
			delAllData(conn, form, request,  Form.getSC0010_01());
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
		form=(SC001F)request.getSession().getAttribute("QueryString");
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
		SC001F Form = (SC001F) form;
		String sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		generateSelectBox(null,Form,request);
		//確認使用者有選擇資料列後，才按下編輯鈕
		if (Form.getSC0010_01() == null ){
			//如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
			request.setAttribute("MSG", "請選擇一筆資料。");
			return cancel(mapping,form,request,response);
		}
		
		if(request.getAttribute("save_status")==null)
		{
			request.setAttribute("save_status","");
		}
		
		if(request.getAttribute("save_status").equals(""))
		{

		try{
			conn = tools.getConnection();
			Statement stmt = conn.createStatement();

			sql = "select a.*,b.SC0030_04 as USER_CREATE_NAME, c.SC0030_04 as USER_UPDATE_NAME from SC0010 a" +
			" left join SC0030 b on a.USER_CREATE = b.SC0030_01 and b.SC0030_14 = a.SC0010_04 " +
			" left join SC0030 c on a.USER_UPDATE = c.SC0030_01 and c.SC0030_14 = a.SC0010_04 " +
			" where a.SC0010_01='" + Form.getSC0010_01() + "'"+
			" and a.SC0010_04='"+userform(request).getSC0030_14()+"'";  //公司別條件
			
			
			ResultSet rs = stmt.executeQuery(sql);
			SC001F Form1 = new SC001F();
			if (rs.next()){
				
				Form1.setSC0010_01(rs.getString("SC0010_01"));
				Form1.setSC0010_02(rs.getString("SC0010_02"));
				Form1.setSC0010_03(rs.getString("SC0010_03"));
				 
				BA_TOOLS ba=BA_TOOLS.getInstance();
				Form1.setDATE_CREATE(ba.DateToChineseDate(rs.getTimestamp("DATE_CREATE")));
				Form1.setDATE_UPDATE(ba.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
				Form1.setUSER_CREATE(ba.GetUserName(conn,rs.getString("USER_CREATE"),userform(request).getSC0030_14()));
				Form1.setUSER_UPDATE(ba.GetUserName(conn,rs.getString("USER_UPDATE"),userform(request).getSC0030_14()));
				Form1.setVERSION(rs.getInt("VERSION"));
			
				
				SC002A sc002a=new SC002A();
				Form1.setSC0011(sc002a.showPgmList(conn, Form1, request,rs.getString("SC0010_01")));
				
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
			generateSelectBox(conn ,form ,request);		
			
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
			String sql = "SELECT SC0040_01 as item_id,concat(concat(SC0040_01,' - '),SC0040_02)  as item_value FROM SC0040 "+
			             " where SC0040_05='"+userform(request).getSC0030_14()+"' order by SC0040_01" ;
			List list1= new ArrayList();
			list1=getSelectOptions(conn,sql,true);
			request.setAttribute("DATA02_list",list1);

	}
	/**
	 * 用使用者代碼取得該使用者可使用的所有程式清單，如果該使用者同時屬於多個群組，則以聯集的方式取得可使用程式清單
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param SC0030_01
	 */
	public ArrayList getPgmListForUser(Connection conn, ActionForm form, HttpServletRequest request, String SC0030_01){
		return null;
	}

	/**
	 * 取得使用者對於程式的讀寫使用權限
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param SC0020_01
	 * @param SC0030_01
	 */
	public ArrayList getUsePerm(Connection conn, ActionForm form, HttpServletRequest request, String SC0020_01, String SC0030_01){
		return null;
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
		request.setAttribute("Form1Datas", new ArrayList());
		request.getSession().removeAttribute("QueryString");
		request.setAttribute("MSG", "請查詢資料!");
		return mapping.findForward("success");
	}

	/**
	 * 查詢資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		SC001F queryform =(SC001F) form;

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		
	try{	
		conn = tools.getConnection();
		//generateSelectBox(conn,form,request);
		
		FormUtils.setFormDisplayMode(request, form, "inspect");
		
		
		ArrayList collection=new ArrayList();
		String sql = "";
		
		//Statement stmt = conn.createStatement();
		sql = "select * from SC0010 where 1=1 and SC0010_04='"+userform(request).getSC0030_14()+"'";
		
		if (queryform.getSC0010_01() != null & !queryform.getSC0010_01().equals("") ) sql += " and SC0010_01 like '%" + queryform.getSC0010_01() + "%'";
		if (queryform.getSC0010_02() != null & !queryform.getSC0010_02().equals("") ) sql += " and SC0010_02 like '%" + queryform.getSC0010_02() + "%'";
		if (queryform.getSC0010_03() != null & !queryform.getSC0010_03().equals("")) sql += " and SC0010_03 like '%" + queryform.getSC0010_03() + "%'";
		
		sql+=" order by SC0010_01 ";
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			
			SC001F Form1 = new SC001F();
			
			Form1.setSC0010_01(rs.getString("SC0010_01"));
			Form1.setSC0010_02(rs.getString("SC0010_02"));
			Form1.setSC0010_03(rs.getString("SC0010_03"));
			
			
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
	 * 查詢表單
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
			SC001F Form = new SC001F();
			
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
	public ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		
		SC001F Form = (SC001F) form;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Form.setSC0010_04(userform(request).getSC0030_14());
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
				
			String sql = " UPDATE SC0010 SET "
					   + " SC0010_02=?"
					   + " ,SC0010_03=?"
					   + " ,DATE_UPDATE=NOW() ,USER_UPDATE=? ,VERSION=? " 
					   + " where SC0010_01='" + Form.getSC0010_01()+ "'" 
					   + " and SC0010_04='" + userform(request).getSC0030_14() + "'";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, Form.getSC0010_02());
			stmt.setString(2, Form.getSC0010_03());
			
			stmt.setString(3,userform(request).getSC0030_01());
			stmt.setInt(4, Form.getVERSION()+1);
			
			stmt.executeUpdate();
			stmt.close();
			
			//處理表身資料
			delAllData(conn, form, request,Form.getSC0010_01());
			addPgmDatas(conn, form, request);
			
			
			conn.commit();
			
			request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status","error");
				if(request.getAttribute("FormData")!=null)
				{
					
				 form=(SC001F)request.getAttribute("FormData");
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
		
		
		if((SC001F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC001F)request.getSession().getAttribute("QueryString");
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
	 * 取得群組清單
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public ArrayList showGroupList(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{
		
		//SC001F Form1 = (SC001F)form;
		
		ArrayList collection=new ArrayList();
		String sql = "";
		
		//Statement stmt = conn.createStatement();
		sql = "select * from SC0010 where SC0010_04='"+userform(request).getSC0030_14()+"' order by SC0010_01 ";
		
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()){
			SC0031F Form1 =new SC0031F();
			Form1.setSC0031_02(rs.getString("SC0010_01"));
			Form1.setSC0010_02(rs.getString("SC0010_02"));
			collection.add(Form1);
		}
		rs.close();
		stmt.close();
		return collection;
	}
	
	/**
	 * 取得群組清單 by User
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public ArrayList showGroupList(Connection conn, ActionForm form, HttpServletRequest request,String SC0030_01) throws SQLException{
		
		ArrayList collection=new ArrayList();
		String sql = "";
		
		//Statement stmt = conn.createStatement();
		sql = " select * from SC0010 " +
			  " left join SC0031 on  SC0031_02 = SC0010_01 and SC0031_01 = '"+SC0030_01+"' and SC0031_04 = SC0010_04  " +
			  " where SC0010_04='"+userform(request).getSC0030_14()+"' AND SC0010_01 NOT  IN ('000')";
//		if(!SC0030_01.equals("")){
//		sql+="where SC0031_04='"+userform(request).getSC0030_14()+"' and SC0031_01='"+SC0030_01+"'";
//		}else{
//	    sql+="where SC0031_04='"+userform(request).getSC0030_14()+"'";
//		}
		sql+=" group by SC0010_01 ";
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()){
			SC0031F Form2 = new SC0031F();
			
			Form2.setSC0031_02(rs.getString("SC0010_01"));
			Form2.setSC0031_03(rs.getString("SC0031_03"));
			Form2.setSC0010_02(rs.getString("SC0010_02"));
			
			if(rs.getString("SC0031_01")!=null)
			{
				if(rs.getString("SC0031_01").equals(SC0030_01))
				{	
					Form2.setCHECKED(true);
				}
			}
				
			
			collection.add(Form2);
		
		}
		rs.close();
		stmt.close();
		return collection;
	}
}