package com.spon.utils.sc.actions;

import org.apache.struts.action.ActionForward;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.SC010F;
import com.spon.utils.util.BA_TOOLS;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.quartz.utils.DBConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 單位資料維護
 * @version 1.01006 下午 01:09:56
 */
public class SC010A extends NewDispatchAction {

	static HttpServletRequest request1 = null ;
	public SC010A(){

	}

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
		
		SC010F Form = (SC010F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		String sql = "";
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        Form.setSC_UNITM_12(userform(request).getSC0030_14());
        request.setAttribute("action","addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		
			
			try {
				if (lc_errors.isEmpty()) {
				sql = "insert into SC_UNITM (" + "SC_UNITM_01," + "SC_UNITM_02," + "SC_UNITM_08,"+
					  " SC_UNITM_12,"
				+ "DATE_CREATE,"+ "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) " 
				+ "values (?,?,?,?,NOW(),NOW(),?,?,1)";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1,Form.getSC_UNITM_01());
				stmt.setString(2,Form.getSC_UNITM_02());
				stmt.setString(3,Form.getSC_UNITM_08());
				stmt.setString(4,userform(request).getSC0030_14());
				stmt.setString(5,userform(request).getSC0030_01());
				stmt.setString(6,userform(request).getSC0030_01());
				
				
				stmt.execute();
				stmt.close();
						
				conn.commit();
				request.setAttribute("Form1Datas",form);
				
				request.setAttribute("MSG", "新增成功!");
				} else {
					saveErrors(request, lc_errors);
					request.setAttribute("save_status", "error");
					request.setAttribute("Form1Datas",Form);
					return mapping.findForward("success");
				}
			} catch (Exception e) {
				request.setAttribute("MSG", "新增失敗!");
				System.out.println("SC010A.addData() "+e);
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
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return addData(mapping, form, request, response);
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

		request.setAttribute("action","delData");
		SC010F Form = (SC010F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		try{
			if (lc_errors.isEmpty()) {	
				Statement stmt = conn.createStatement();
				String Sql;
				Sql = "delete from SC_UNITM where " +
				  " SC_UNITM_01='" + Form.getSC_UNITM_01()+ "' " +
				  " and SC_UNITM_02='" + Form.getSC_UNITM_02()+ "' and SC_UNITM_12='"+userform(request).getSC0030_14()+"'";
				stmt.execute(Sql);
				request.setAttribute("MSG", "刪除成功!");
				stmt.close();
				
				conn.commit();
			}
			else {
					//saveErrors(request, lc_errors);
					request.setAttribute("save_status", "error");
					request.setAttribute("Form1Datas",Form);
					request.setAttribute("MSG",request.getAttribute("ErrMSG") );
					return mapping.findForward("success");
			}
				
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
        return init(mapping,form,request,response);
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
		if(!request.getParameter("LastKey1").equals(((SC010F)form).getSC_UNITM_01()))
		{
			request.setAttribute("MSG", "修改失敗!條件值已改變");
			return init(mapping, form, request, response);
		}
		return saveData(mapping, form, request, response);
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
		return init(mapping, form, request, response);
//		DataSource ds;
//		Connection conn = null;
//		request.setAttribute("action","delData");
//		String temp="";
//		SC005F Form = (SC005F) form;
//		ds = getDataSource(request, "SPOS");
//		try {
//            conn = ds.getConnection();
//        } catch (SQLException e2) {
//            // TODO Auto-generated catch block
//            e2.printStackTrace();
//        }
//        temp=getSequence(conn,"SC001","SC001_01","00");
//        System.out.println(temp);
//        return init(mapping, form, request, response);
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
		try {
			DataSource ds;
			SC010F Form = new SC010F();
			request.setAttribute("Form1Datas", Form);
			// 將表單的顯示模式設定為create (新增模式)
			FormUtils.setFormDisplayMode(request, form, "create");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
//			System.out.println("run");
		}
		return  mapping.findForward("success");
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
		SC010F Form = (SC010F) form;
		if(!Form.getSC_UNITM_01().equals(""))
		{

		String sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		FormUtils.setFormDisplayMode(request, form, "create");
		try{
			conn = tools.getConnection();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			sql = "select * from  SC_UNITM where SC_UNITM_01='" + Form.getSC_UNITM_01() + "' and SC_UNITM_02='" + Form.getSC_UNITM_02() + 
			      "' and SC_UNITM_12='"+userform(request).getSC0030_14()+"' order by SC_UNITM_01" ;
			ResultSet rs = stmt.executeQuery(sql);
			SC010F Form1 = new SC010F();
			
			if (rs.next()){
				Form1.setSC_UNITM_01(rs.getString("SC_UNITM_01"));
				Form1.setSC_UNITM_02(rs.getString("SC_UNITM_02"));
				Form1.setSC_UNITM_08(rs.getString("SC_UNITM_08"));
				BA_TOOLS ba=BA_TOOLS.getInstance();
				Form1.setDATE_CREATE(ba.DateToChineseDate(rs.getTimestamp("DATE_CREATE")));
				Form1.setDATE_UPDATE(ba.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
				Form1.setUSER_CREATE(ba.GetUserName(conn,rs.getString("USER_CREATE"),userform(request).getSC0030_14()));
				Form1.setUSER_UPDATE(ba.GetUserName(conn,rs.getString("USER_UPDATE"),userform(request).getSC0030_14()));
				Form1.setVERSION(rs.getInt("VERSION"));
				request.setAttribute("Form1Datas", Form1);
				FormUtils.setFormDisplayMode(request, form, "edit");
			}
			else
			{	
				request.setAttribute("MSG", "查無資料");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
			
			//產生下拉選單
		
			//將表單的顯示模式設定為edit (編輯模式)
			
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
			request.setAttribute("Required","true");
			request.setAttribute("MSG", "請輸入條件值");
			request.setAttribute("Form1Datas", Form);
			return mapping.findForward("success");
		}
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
		return queryDatas(mapping, form, request, response);
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
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		SC010F Form = (SC010F) form;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Form.setSC_UNITM_12(userform(request).getSC0030_14());
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			String sql="";
			PreparedStatement stmt=null;
			if(!Form.getSC_UNITM_01().equals("") || !Form.getSC_UNITM_02().equals(""))
			{
				sql="update SC_UNITM set " +
					"SC_UNITM_08=?," +
					"DATE_UPDATE=NOW()," +
					"USER_UPDATE=?," +
					"VERSION=?" +
					" where SC_UNITM_01='" + Form.getSC_UNITM_01()+ "' " +
					" and SC_UNITM_02='" + Form.getSC_UNITM_02() + "'" +
					" and SC_UNITM_12='"+userform(request).getSC0030_14()+"'";
				
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, Form.getSC_UNITM_08());
				stmt.setString(2, userform(request).getSC0030_01());
				stmt.setInt(3, Form.getVERSION()+1);
			}
			stmt.executeUpdate();
			stmt.close();
			conn.commit();
			
			request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status","error");
				request.setAttribute("Form1Datas",Form);
				request.setAttribute("MSG",request.getAttribute("ErrMSG") );
				return mapping.findForward("success");
				
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "修改失敗!");
			System.out.println("SC010A.saveData() "+e );
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
		return queryDatas(mapping, form, request, response);
//		form=(FM0102Form)request.getSession().getAttribute("FM0102Query");
	}
	public void main(String[] arg) {
	}

 


}