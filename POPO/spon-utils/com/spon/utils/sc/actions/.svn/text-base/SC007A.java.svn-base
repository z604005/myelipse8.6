package com.spon.utils.sc.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.spon.utils.sc.forms.SC0071F;
import com.spon.utils.sc.forms.SC007F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;





import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 訊息廣播
 * @version 1.0
 * @created 17-四月-2006 下午 12:00:06
 */
public class SC007A  extends NewDispatchAction {

	public void finalize() throws Throwable {

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

		SC007F Form = (SC007F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		String sql = "";
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        Form.setSC0070_09(userform(request).getSC0030_14());
        request.setAttribute("action","addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		
			
			try {
				if (lc_errors.isEmpty()) {
				sql = "insert into SC0070 (" + "SC0070_01," + "SC0070_02," + "SC0070_03," + "SC0070_04," + "SC0070_05," 
				      + "SC0070_06," + "SC0070_07," + "SC0070_08," + "SC0070_09,"
					  + "DATE_CREATE,"+ "DATE_UPDATE," + "USER_CREATE," 
					  + "USER_UPDATE," + "VERSION) values (?,?,?,?,?,?,?,?,?,NOW(),NOW(),?,?,?)";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				Form.setSC0070_01(this.getSequence(conn,"SC0070","SC0070_01","000000",request));
				
				stmt.setString(1,Form.getSC0070_01());
				stmt.setString(2,Form.getSC0070_02());
				stmt.setString(3,Form.getSC0070_03());
				stmt.setInt(4,Form.isSC0070_04()==true?1:0);
				
				if(!Form.getSC0070_05().equals(""))
					stmt.setTimestamp(5,to_Date(Form.getSC0070_05().trim()+" "+Form.getSC0070_05_HH()+":"+Form.getSC0070_05_MM()+":00",""));
				else
					stmt.setString(5,Form.getSC0070_05());
				
				if(!Form.getSC0070_06().equals(""))
					stmt.setTimestamp(6, to_Date(Form.getSC0070_06().trim()+" "+Form.getSC0070_06_HH()+":"+Form.getSC0070_06_MM()+":00",""));
				else
					stmt.setString(6,Form.getSC0070_06());
				
				stmt.setInt(7,Form.isSC0070_07()==true?1:0);
				stmt.setString(8,Form.getSC0070_08());
				stmt.setString(9,userform(request).getSC0030_14());
				stmt.setString(10,userform(request).getSC0030_01());
				stmt.setString(11,userform(request).getSC0030_01());
				stmt.setInt(12,1);
				
				
				stmt.execute();
				
				Statement stmt1=conn.createStatement();
				stmt1.execute("update SC0050 set SC0050_03=(SC0050_03+1)  where SC0050_01='SC0070' and SC0050_02='SC0070_01' and SC0050_06='"+userform(request).getSC0030_14()+"'");
				stmt1.close();
				
				stmt.close();
				
				//新增表身資料
				addUserDatas(conn, Form, request);
				
				
				conn.commit();
				
				request.setAttribute("MSG", "新增成功!");
				} else {
					saveErrors(request, lc_errors);
					request.setAttribute("save_status", "error");
				}
			} catch (Exception e) {
				request.setAttribute("MSG", "新增失敗!");
				System.out.println("SC007A.addData() "+e);
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
			
			SC007F Form = new SC007F();
			//預設啟用
			Form.setSC0070_07(true);
			
			
			Form.setSC0071(showUserList(conn, Form, request));
			
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
	 * 多筆儲存使用者資料
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param response
	 * @throws SQLException 
	 */
	public void addUserDatas(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{
		SC007F Form=(SC007F)form;
		
		String sql = "insert into SC0071 (" + "SC0071_01," + "SC0071_02," + "SC0071_03,"+ "SC0071_04,"
		+ "DATE_CREATE,"+ "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) values (?,?,?,?,NOW(),NOW(),?,?,?)";

		PreparedStatement stmt = conn.prepareStatement(sql);
		
		List SC0071=Form.getSC0071();

		for (int i = 0; i < SC0071.size(); i++) 
		{
			SC0071F Form1=(SC0071F)SC0071.get(i);
			if(Form1.isCHECKED()==true)
			{
				stmt.setString(1,Form.getSC0070_01());
				stmt.setString(2,Form1.getSC0071_02());
				stmt.setString(3,Form1.getSC0071_03());
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
	 * 取消作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		//按下取消鈕後，回到顯示查詢結果的頁面
		if((SC007F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC007F)request.getSession().getAttribute("QueryString");
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
		SC007F Form = (SC007F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try{
			conn = tools.getConnection();
			Statement stmt = conn.createStatement();
			
			//確認使用者有選擇資料列後，才按下刪除鈕
			if (Form.getSC0070_01() == null){
				//如果沒有選擇資料列，就以JSTL (即在JSP內使用 ${MSG}) 的方式顯示訊息到畫面上，並且回到查詢結果畫面
				request.setAttribute("MSG", "請先選擇一筆要刪除的資料。");
				return cancel(mapping, form, request, response);
			}
			stmt.execute("delete from SC0070 where SC0070_01='" + Form.getSC0070_01()+ "' and SC0070_09='"+userform(request).getSC0030_14()+"'");
			stmt.close();
			//刪除表身資料
			delUserDatas(conn, Form, request);
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
		form=(SC007F)request.getSession().getAttribute("QueryString");
        return queryDatas(mapping,form,request,response);
	}

	/**
	 * 刪除訊息發布的所有對象
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public void delUserDatas(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{
	String sql = "";
	SC007F Form = (SC007F) form;
		//Statement stmt = conn.createStatement();
		sql = "delete from SC0071 where SC0071_01='"+Form.getSC0070_01()+"' and SC0071_04='"+userform(request).getSC0030_14()+"'";
		
		Statement stmt = conn.createStatement();
		stmt.execute(sql);
		
		stmt.close();
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
		SC007F Form = (SC007F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		String sql = "";
		//確認使用者有選擇資料列後，才按下編輯鈕
		if (Form.getSC0070_01() == null ){
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

			sql = " select a.*,b.SC0030_04 as USER_CREATE_NAME, c.SC0030_04 as USER_UPDATE_NAME from SC0070 a " +
			" left join SC0030 b on a.USER_CREATE = b.SC0030_01 and b.SC0030_14 = a.SC0070_09 " +
			" left join SC0030 c on a.USER_UPDATE = c.SC0030_01 and c.SC0030_14 = a.SC0070_09 " +
			" where a.SC0070_01 = '" + Form.getSC0070_01() + "' and a.SC0070_09 = '"+userform(request).getSC0030_14()+"' ";
			
			
			
			ResultSet rs = stmt.executeQuery(sql);
			SC007F Form1 = new SC007F();
			if (rs.next()){
				
				Form1.setSC0070_01(rs.getString("SC0070_01"));
				Form1.setSC0070_02(rs.getString("SC0070_02"));
				Form1.setSC0070_03(rs.getString("SC0070_03"));
				Form1.setSC0070_04(rs.getInt("SC0070_04")==1?true:false);
				
				
				if(rs.getString("SC0070_05")!=null)
				{
					HashMap hm=to_TW_Date(rs.getString("SC0070_05"));
					Form1.setSC0070_05((String)hm.get("YYYYMD"));
					Form1.setSC0070_05_HH((String)hm.get("HH"));
					Form1.setSC0070_05_MM((String)hm.get("mm"));
					
				}
				else
				{
					Form1.setSC0070_05(rs.getString("SC0070_05"));
				}
				
				if(rs.getString("SC0070_06")!=null)
				{
					HashMap hm=to_TW_Date(rs.getString("SC0070_06"));
					Form1.setSC0070_06((String)hm.get("YYYYMD"));
					Form1.setSC0070_06_HH((String)hm.get("HH"));
					Form1.setSC0070_06_MM((String)hm.get("mm"));
					
				}
				else
				{
					Form1.setSC0070_05(rs.getString("SC0070_05"));
				}
				
				Form1.setSC0070_07(rs.getInt("SC0070_07")==1?true:false);
				Form1.setSC0070_08(rs.getString("SC0070_08"));
				 
				BA_TOOLS ba=BA_TOOLS.getInstance();
				Form1.setDATE_CREATE(rs.getDate("DATE_CREATE")+"");
				Form1.setDATE_UPDATE(rs.getDate("DATE_UPDATE")+"");
				Form1.setUSER_CREATE(ba.GetUserName(conn,rs.getString("USER_CREATE"),userform(request).getSC0030_14()));
				Form1.setUSER_UPDATE(ba.GetUserName(conn,rs.getString("USER_UPDATE"),userform(request).getSC0030_14()));
				Form1.setVERSION(rs.getInt("VERSION"));
				
				Form1.setSC0071(showUserList(conn, Form, request));
				
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
	 * 查詢資料表單
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
			SC007F Form = new SC007F();
			Form.setSC0070_07(true);
			
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
		
		SC007F queryform =(SC007F) form;
	
	try{	
		conn = tools.getConnection();
		generateSelectBox(conn,form,request);
		
		//FormUtils.setFormDisplayMode(request, form, "inspect");
		FormUtils.setFormDisplayMode(request, form, "edit");
		
		
		ArrayList collection=new ArrayList();
		String sql = "";
		
		//Statement stmt = conn.createStatement();
		sql = "select * from SC0070 where 1=1  and SC0070_09='"+userform(request).getSC0030_14()+"'";
		
		if (queryform.getSC0070_02() != null & !queryform.getSC0070_02().equals("") ) sql += " and SC0070_02 like '%" + queryform.getSC0070_02() + "%'";
		if (queryform.getSC0070_03() != null & !queryform.getSC0070_03().equals("")) sql += " and SC0070_03 like '%" + queryform.getSC0070_03() + "%'";
		if (queryform.isSC0070_07() == true)  sql += " and SC0070_07 = '1'"; else sql += " and SC0070_07 = '0'"; 
		if (queryform.getSC0070_08() != null & !queryform.getSC0070_08().equals("") ) sql += " and SC0070_08 like '%" + queryform.getSC0070_08() + "%'";

		
		sql+=" order by SC0070_01 ";
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			
			SC007F Form1 = new SC007F();
			
			Form1.setSC0070_01(rs.getString("SC0070_01"));
			Form1.setSC0070_02(rs.getString("SC0070_02"));
			Form1.setSC0070_03(rs.getString("SC0070_03"));
			Form1.setSC0070_04(rs.getInt("SC0070_04")==1?true:false);
			Form1.setSC0070_05(rs.getString("SC0070_05"));
			Form1.setSC0070_06(rs.getString("SC0070_06"));
			Form1.setSC0070_07(rs.getInt("SC0070_07")==1?true:false);
			Form1.setSC0070_08(rs.getString("SC0070_08"));
			
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
	 * 儲存編修資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){

		SC007F Form = (SC007F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Form.setSC0070_09(userform(request).getSC0030_14());
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			PreparedStatement stmt = conn.prepareStatement("update SC0070 set " +
					"SC0070_02=?,SC0070_03=?,SC0070_04=?,SC0070_05=?,SC0070_06=?,SC0070_07=?,SC0070_08=? "+
					",DATE_UPDATE=CURRENT_DATE,USER_UPDATE=?,VERSION=? " +
					"where SC0070_01='" + Form.getSC0070_01()+ "' and SC0070_09='"+userform(request).getSC0030_14()+"'");
			
			
			stmt.setString(1, Form.getSC0070_02());
			stmt.setString(2, Form.getSC0070_03());
			stmt.setInt(3, Form.isSC0070_04()==true?1:0);
			
			if(!Form.getSC0070_05().equals(""))
				stmt.setTimestamp(4,to_Date(Form.getSC0070_05().trim()+" "+Form.getSC0070_05_HH()+":"+Form.getSC0070_05_MM()+":00",""));
			else
				stmt.setString(4,Form.getSC0070_05());
			
			if(!Form.getSC0070_06().equals(""))
				stmt.setTimestamp(5,to_Date(Form.getSC0070_06().trim()+" "+Form.getSC0070_06_HH()+":"+Form.getSC0070_06_MM()+":00",""));
			else
				stmt.setString(5,Form.getSC0070_06());
			
			
			stmt.setInt(6, Form.isSC0070_07()==true?1:0);
			stmt.setString(7, Form.getSC0070_08());
			
			stmt.setString(8,userform(request).getSC0030_01());
			stmt.setInt(9, Form.getVERSION()+1);
			
			
			stmt.executeUpdate();
			stmt.close();
			
			//處理表身資料
			delUserDatas(conn, Form, request);
			addUserDatas(conn, form, request);
			//addPgmDatas(conn, form, request);
			
			
			conn.commit();
			
			request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status","error");
				if(request.getAttribute("FormData")!=null)
				{
					
				 form=(SC007F)request.getAttribute("FormData");
				 request.setAttribute("MSG",request.getAttribute("ErrMSG") );
				}
				return editDataForm(mapping, form, request, response);
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "修改失敗!");
			System.out.println("SC007A.saveData() "+e );
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
		
		
		if((SC007F)request.getSession().getAttribute("QueryString")!=null)
		{
			form=(SC007F)request.getSession().getAttribute("QueryString");
			return queryDatas(mapping, form, request, response);
		}
		else
		{
			return init(mapping, form, request, response);
		}
	}

	/**
	 * 顯示訊息查詢結果
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	public ArrayList showMsgList(Connection conn, ActionForm form, HttpServletRequest request){
		return null;
	}

	/**
	 * 以使用者代碼查詢訊息，僅列出有效的訊息(未過期，未失效)
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @param SC0030_01    使用者代碼
	 * @throws SQLException 
	 */
	public ArrayList showMsgListForUser(Connection conn, ActionForm form, HttpServletRequest request, String SC0030_01) throws SQLException{
		ArrayList collection=new ArrayList();
		String sql = "";
		sql =  " select SC0070_01,SC0070_02,SC0070_03,SC0070_04,SC0070_05,SC0070_06,SC0070_07,SC0070_08  " +
			   " from  SC0070 a " +
			   " left join SC0071 b on a.SC0070_01 = b.SC0071_01 and b.SC0071_04 = a.SC0070_09 " +
			   " where NULLIF(a.SC0070_05,CURRENT_DATE)=CURRENT_DATE and NULLIF(a.SC0070_06,CURRENT_DATE)=CURRENT_DATE   " +
			   " and a.SC0070_07=1  and   (b.SC0071_02='"+SC0030_01+"' or a.SC0070_04=1) " +
			   " and SC0070_09 = '"+userform(request).getSC0030_14()+"' union " +
			   " select SC0070_01,SC0070_02,SC0070_03,SC0070_04,SC0070_05,SC0070_06,SC0070_07,SC0070_08 " +
			   " from  SC0070 a " +
			   " left join SC0071 b on a.SC0070_01 = b.SC0071_01 and b.SC0071_04 = a.SC0070_09 " +
			   " where a.SC0070_05<=CURRENT_DATE and a.SC0070_06>=CURRENT_DATE  and a.SC0070_07=1  " +
			   " and  (b.SC0071_02='"+SC0030_01+"' or a.SC0070_04=1) and SC0070_09 = '"+userform(request).getSC0030_14()+"' " +
			   " order by SC0070_01 desc ";
		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()) {
			
			SC007F Form1=new SC007F();
			Form1.setSC0070_01(rs.getString("SC0070_01"));
			Form1.setSC0070_02(rs.getString("SC0070_02"));
			Form1.setSC0070_03(rs.getString("SC0070_03"));
			Form1.setSC0070_04(rs.getInt("SC0070_04")==1?true:false);
			Form1.setSC0070_05(rs.getString("SC0070_05"));
			Form1.setSC0070_06(rs.getString("SC0070_06"));
			Form1.setSC0070_07(rs.getInt("SC0070_07")==1?true:false);
			Form1.setSC0070_08(rs.getString("SC0070_08"));
			
			collection.add(Form1);
		
		}
		rs.close();
		stmt.close();
		return collection;
		
	}

	/**
	 * 取得所有的使用者清單，
	 * 如果此訊息有給某一位人員時，就勾選起來
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 * @throws SQLException 
	 */
	public ArrayList showUserList(Connection conn, ActionForm form, HttpServletRequest request) throws SQLException{
		ArrayList collection=new ArrayList();
		String sql = "";
		SC007F Form=(SC007F)form;
		
		//Statement stmt = conn.createStatement();
		sql = " select * from SC0030 " +
			  " left join SC0071 on  SC0030_01=SC0071_02 and SC0071_01 = '"+Form.getSC0070_01()+"' " +
			  " and SC0030_14 = SC0071_04 "+
		      " where SC0030_14 = '"+userform(request).getSC0030_14()+"' " +
		      " order by SC0030_01 ";
		
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		
		while (rs.next()){
			SC0071F Form2 = new SC0071F();
			
			Form2.setSC0071_02(rs.getString("SC0030_01"));
			Form2.setSC0071_03(rs.getString("SC0071_03"));
			Form2.setSC0030_04(rs.getString("SC0030_04"));
			
			if(rs.getString("SC0071_01")!=null)
			{
				if(rs.getString("SC0071_01").equals(Form.getSC0070_01()))
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
	
	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request){
			
		List listSC0070_05_HH=new ArrayList();
		DecimalFormat df=new DecimalFormat("00");
		for (int i = 0; i < 24; i++) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listSC0070_05_HH.add(bform);
		}
		request.setAttribute("listSC0070_05_HH",listSC0070_05_HH);
		
		List listSC0070_05_MM=new ArrayList();
		
		for (int i = 0; i < 60; i++) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listSC0070_05_MM.add(bform);
		}
		request.setAttribute("listSC0070_05_MM",listSC0070_05_MM);
	
		
	}
	
	private Timestamp to_Date(String strdate,String Kind) throws ParseException
	{
		if (Kind.equals("1")){ //民國年要轉
			strdate=(Integer.parseInt(strdate.substring(0,3))+1911)+strdate.substring(3);
	
		}
		
//		SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd hh:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = (Date) format.parse(strdate);
		Timestamp ts = new Timestamp(date.getTime());
		return ts;
	}
	
	private HashMap to_TW_Date(String strdate) throws ParseException
	{
		HashMap hm=new HashMap();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//SimpleDateFormat YMDformat = new SimpleDateFormat("yyyy/MM/dd");
//		Date date=format.parse(strdate);
		Date date=format.parse(strdate);
		SimpleDateFormat YMDformat = new SimpleDateFormat("yyyy/MM/dd");
		DecimalFormat df =new DecimalFormat("000");
		String YMD=YMDformat.format(date);
		hm.put("YMD",df.format(Integer.parseInt(YMD.substring(0,4))-1911)+YMD.substring(4));
		hm.put("YYYYMD",YMD);
		SimpleDateFormat HHformat = new SimpleDateFormat("HH");
		String HH=HHformat.format(date);
		hm.put("HH",HH);
		SimpleDateFormat mmformat = new SimpleDateFormat("mm");
		String mm=mmformat.format(date);
		hm.put("mm",mm);
		return hm;
	}
	
	
}