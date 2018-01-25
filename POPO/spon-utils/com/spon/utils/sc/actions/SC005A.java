package com.spon.utils.sc.actions;
import org.apache.struts.action.ActionForward;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.SC005F;
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
 * 序號設定
 * @version 1.0
 * @updated 20-六月-2006 下午 01:09:56
 */
public class SC005A extends NewDispatchAction {
	

	public SC005A(){

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
		String sql = "";
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		SC005F Form = (SC005F) form;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }
        Form.setSC0050_06(userform(request).getSC0030_14());
        request.setAttribute("action","addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		
			
			try {
				if (lc_errors.isEmpty()) {
				sql = "insert into SC0050 (" + "SC0050_01," + "SC0050_02," + "SC0050_03,"
				+ "SC0050_04,SC0050_05,SC0050_06,"
				+ "DATE_CREATE,"+ "DATE_UPDATE," + "USER_CREATE," + "USER_UPDATE," + "VERSION) " 
				+ "values (?,?,?,?,?,?,NOW(),NOW(),?,?,1)";
				
				PreparedStatement stmt = conn.prepareStatement(sql);
				
				stmt.setString(1,Form.getSC0050_01());
				stmt.setString(2,Form.getSC0050_02());
				stmt.setString(3,Form.getSC0050_03());
				stmt.setString(4,Form.getSC0050_04());
				stmt.setString(5,Form.getSC0050_05());
				stmt.setString(6,userform(request).getSC0030_14());
				stmt.setString(7,userform(request).getSC0030_01());
				stmt.setString(8,userform(request).getSC0030_01());
				
				
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
				System.out.println("SC0050A.addData() "+e);
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
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		request.setAttribute("action","delData");
		SC005F Form = (SC005F) form;
		
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
				Sql = "delete from SC0050 where " +
				  " SC0050_01='" + Form.getSC0050_01()+ "' " +
				  " and SC0050_02='" + Form.getSC0050_02()+ "' and SC0050_06='"+userform(request).getSC0030_14()+"'";
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
		if(!request.getParameter("LastKey1").equals(((SC005F)form).getSC0050_01()))
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
	 * 轉換日期成為民國年yyyMMdd，如：0941001
	 * @param date 日期物件
	 * @return
	 */
	public String conYYYMMdd(java.util.Date date){
		String yyyMMdd = "";
		SimpleDateFormat df = new SimpleDateFormat("MMdd");
		//java.util.Date date = new java.util.Date();
		
	
		yyyMMdd = ((date.getYear() - 11 > 99)?
				"" + (date.getYear() - 11):"0" + (date.getYear() - 11)) +
				df.format(date);
		return yyyMMdd;
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
			SC005F Form = new SC005F();
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
		SC005F Form = (SC005F) form;
		if(!Form.getSC0050_01().equals("")){
			
		String sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		FormUtils.setFormDisplayMode(request, form, "create");
		try{
			
			conn = tools.getConnection();
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			sql = "select * from  SC0050 where SC0050_01='" + Form.getSC0050_01() + "' and SC0050_02='" + Form.getSC0050_02() + 
			      "' and SC0050_06='"+userform(request).getSC0030_14()+"' order by SC0050_01" ;
			ResultSet rs = stmt.executeQuery(sql);
			SC005F Form1 = new SC005F();
			
			if (rs.next()){
				Form1.setSC0050_01(rs.getString("SC0050_01"));
				Form1.setSC0050_02(rs.getString("SC0050_02"));
				Form1.setSC0050_03(rs.getString("SC0050_03"));
				Form1.setSC0050_04(rs.getString("SC0050_04"));
				Form1.setSC0050_05(rs.getString("SC0050_05"));
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
		
		SC005F Form = (SC005F) form;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        Form.setSC0050_06(userform(request).getSC0030_14());
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			String sql="";
			PreparedStatement stmt=null;
			if(!Form.getSC0050_01().equals("") || !Form.getSC0050_02().equals(""))
			{
				sql="update SC0050 set " +
					"SC0050_03=?," +
					"SC0050_04=?," +
					"SC0050_05=?," +
					"DATE_UPDATE=NOW()," +
					"USER_UPDATE=?," +
					"VERSION=?" +
					" where SC0050_01='" + Form.getSC0050_01()+ "' " +
					" and SC0050_02='" + Form.getSC0050_02() + "'" +
					" and SC0050_06='"+userform(request).getSC0030_14()+"'";
				
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, Form.getSC0050_03());
				stmt.setString(2, Form.getSC0050_04());
				stmt.setString(3, Form.getSC0050_05());
				stmt.setString(4, userform(request).getSC0030_01());
				stmt.setInt(5, Form.getVERSION()+1);
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
			System.out.println("SC005A.saveData() "+e );
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

	
	/**
	 * 取得目前的序號並回傳，增加1會回存資料庫
	 * 
	 * @param conn
	 * @param table    table 名稱
	 * @param column    column名稱
	 * @param format    傳入"000000"且序號是1話，就回傳"000001"
	 * @param comp_id 公司代碼
	 * 如果傳入""，則回傳"1"
	 */
	public String getSequence(Connection conn_null, String table, String column, String format, String comp_id ){
		
		DBConnectionManager dbc = DBConnectionManager.getInstance();
		Connection conn = conn_null;
		
		
		int seqno = 0;
		String seqtext = "";
		String yyyMMdd = "";
		String temp="";
		String temp1="";
		java.util.Date date = new java.util.Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		yyyMMdd = df.format(date);//conYYYMMdd(date);
		try {
			conn.setAutoCommit(false);
			DecimalFormat Format = new DecimalFormat(format);
			Statement stmt = conn.createStatement();
			String sql = "select SC0050_01,SC0050_02,SC0050_03,SC0050_04,IFNULL(SC0050_04,0) as A04,SC0050_05" +
//					" from SC0050 where SC0050_01='" + table + "' and SC0050_06='"+userform(request1).getSC0030_14()+
						 " from SC0050 where SC0050_01='" + table + "' and SC0050_06='"+comp_id+
						 "' and SC0050_02='" + column + "' FOR UPDATE" ;
//			sql += " for update ";
			
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()){
				seqno = rs.getInt("SC0050_03");
				switch (rs.getString("A04").length()) {  
				case 0:case 1:  //代表沒有更新時間，序號一直加總
					seqno++;
					break;
				case 4:  //每年更新
					seqno++;
					if (Integer.parseInt(yyyMMdd.substring(0,4))>rs.getInt("SC0050_04")) {
						seqno=1;
						temp=",SC0050_04=?";
						temp1=Integer.parseInt(yyyMMdd.substring(0,4))+"";
					}
					break;
				case 6:  //每月更新
					seqno++;
					if (Integer.parseInt(yyyMMdd.substring(0,6))>rs.getInt("SC0050_04")) {
						seqno=1;
						temp=",SC0050_04=?";
						temp1=Integer.parseInt(yyyMMdd.substring(0,6))+"";
					}
					break;
				case 8: //每日更新 
					seqno++;
					if (Integer.parseInt(yyyMMdd.substring(0,8))>rs.getInt("SC0050_04")) {
						seqno=1;
						temp=",SC0050_04=?";
						temp1=Integer.parseInt(yyyMMdd.substring(0,8))+"";
					}
					break;
				}
				
			}
			rs.close();
			stmt.close();

			sql="update SC0050 set SC0050_03=?" + temp + " where SC0050_01='"+ table +
			"' and SC0050_02='" + column + "' and SC0050_06='"+comp_id+"'";
				PreparedStatement stmt2=null;
				stmt2 = conn.prepareStatement(sql);
				stmt2.setInt(1,seqno);
				if (!temp.equals("")) 
					stmt2.setString(2,temp1);
				stmt2.executeUpdate();
				stmt2.close();
			seqtext = Format.format(seqno);
			conn.commit();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.out.println("SC0050:getSequence() error!");
			System.out.println(e);
		}
		finally
		{
			
			/*try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
		}
		return seqtext;
	}
 


}