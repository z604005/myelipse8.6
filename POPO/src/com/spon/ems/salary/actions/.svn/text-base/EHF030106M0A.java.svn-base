package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.utils.struts.form.BA_REPORTF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;

import fr.improve.struts.taglib.layout.util.FormUtils;
/**
 * <Action> 轉帳銀行設定
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030106M0A extends NewDispatchAction {
	
	private BA_TOOLS ba;
	
	public EHF030106M0A(){
		ba = BA_TOOLS.getInstance();
	}
	
	/**
	 * Initial
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		//EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		BA_REPORTF Form = new BA_REPORTF();
		List list = new ArrayList();
		
		request.setAttribute("Status", "3");
		request.setAttribute("Form1Datas",Form);
		request.setAttribute("Form2Datas",list);
		
//		FormUtils.setFieldDisplayMode(request, form, "DATA05", 2);
		FormUtils.setFormDisplayMode(request, form, "create");
		request.setAttribute("collection","show");
		request.setAttribute("button","init");
		return mapping.findForward("success");
	}
	
	
	public ActionForward init_add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		//EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		BA_REPORTF Form = new BA_REPORTF();
		List list = new ArrayList();
		
		request.setAttribute("Status", "3");
		request.setAttribute("Form1Datas",Form);
		request.setAttribute("Form2Datas",list);
		
//		FormUtils.setFieldDisplayMode(request, form, "DATA05", 2);
		FormUtils.setFormDisplayMode(request, form, "create");
		request.setAttribute("button","add");
		return mapping.findForward("success");
	}
	
	/**
	 * 新增資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward InsertForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		//EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		BA_REPORTF Form = (BA_REPORTF)form;
		Connection conn = null;
		//BA_TOOLS ba = new BA_TOOLS();
		List list = new ArrayList();
		String sql = "";
		String compid = "";
		
		try{
			compid = getLoginUser(request).getCompId();
//			System.out.println(getFlag(Form.getDATA05(), compid));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		ActionErrors lc_errors = new ActionErrors();
		BA_Vaildate ve = BA_Vaildate.getInstance();
		// 建立驗證元件
		ve.isEmpty(lc_errors, Form.getDATA05(), "DATA05","必填欄位!不可空白!!");
		ve.isNumEmpty(lc_errors, Form.getDATA05(), "DATA05", "", true);
		
		if(lc_errors.isEmpty()){
			if (!getFlag(Form.getDATA05(), compid)) {
				try {
					String name = getLoginUser(request).getUserName();
					conn = ba.getConnection("SPOS");
					conn.setAutoCommit(false);
					sql = " insert into EHF030106T0 ("
							+ " EHF030106T0_01,EHF030106T0_02,EHF030106T0_03,EHF030106T0_04, "
							+ " USER_CREATE,USER_UPDATE,VERSION,DATE_UPDATE ) "
							+ " values(?,?,?,?,?,?,1,NOW()) ";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,	pstmt, null, sql);
					p6stmt.setString(1, Form.getDATA05());
					p6stmt.setString(2, Form.getDATA06());
					p6stmt.setString(3, Form.getDATA07());
					p6stmt.setString(4, compid);
					p6stmt.setString(5, name);
					p6stmt.setString(6, name);
//					System.out.println(p6stmt.getQueryFromPreparedStatement());
					pstmt.execute();
					conn.commit();
					pstmt.close();

					request.setAttribute("MSG", "新增成功!!");
					// request.setAttribute("Status", "2");
				} catch (Exception e) {
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
				FormUtils.setFormDisplayMode(request, form, "create");
				// FormUtils.setFieldDisplayMode(request, form, "DATA05", 2);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("Form2Datas", list);
				return DetailForm(mapping, Form, request, response);

			} else {
				request.setAttribute("MSG", "銀行代號已存在，請重填!!");
				FormUtils.setFormDisplayMode(request, form, "create");
				// FormUtils.setFieldDisplayMode(request, form, "DATA05", 2);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("Form2Datas", list);
				request.setAttribute("button","add");
				return mapping.findForward("success");
			}
		}else {
			saveErrors(request, lc_errors);
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("MSG", request.getAttribute("ErrMSG"));
//			return init(mapping, form, request, response);
			request.setAttribute("button","save");
			return mapping.findForward("success");
		}
		
	}
	
	/**
	 * 檢查銀行代號是否已存在
	 * @param id
	 * @return
	 */
	private boolean getFlag(String id, String comp_id)
	{
		boolean flag = false;
		String sql = "";
		Connection conn = null;
		//BA_TOOLS ba = new BA_TOOLS();
		
		try{
			conn = ba.getConnection("SPOS");
			sql = " select * " +
				  " from EHF030106T0 " +
				  " where 1=1 " +
				  " and EHF030106T0_01 = ? " +  //銀行代碼
				  " and EHF030106T0_04 = ? ";  //公司代碼
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			p6stmt.setString(1, id);
			p6stmt.setString(2, comp_id);  //公司代碼
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				flag = true;
			}
			rs.close();
			p6stmt.close();
			pstmt.close();
			
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
		
		return flag;
	}
	
	/**
	 * 查詢資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		//EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		BA_REPORTF Form = (BA_REPORTF)form;
		Connection conn = null;
		//BA_TOOLS ba = new BA_TOOLS();
		List list = new ArrayList();
		String sql = "";
		
		try{
			conn = ba.getConnection("SPOS");
			
			sql = " SELECT * " +
				  " FROM `ehf030106t0` " +
				  " WHERE 1=1 " +
				  " and EHF030106T0_04 = '"+getLoginUser(request).getCompId()+"' ";
			
			if(!"".equals(Form.getDATA05())){
				sql += " and EHF030106T0_01 = '"+Form.getDATA05()+"'";
			}
			if(!"".equals(Form.getDATA06())){
				sql += " and EHF030106T0_02 = '"+Form.getDATA06()+"'";
			}
			if(!"".equals(Form.getDATA07())){
				sql += " and EHF030106T0_03 = '"+Form.getDATA07()+"'";
			}
			sql += " order by EHF030106T0_01";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				//EMS_VIEWDATAF bForm = new EMS_VIEWDATAF();
				BA_REPORTF bForm = new BA_REPORTF();
				bForm.setDATA00(rs.getString("EHF030106T0_01"));
				bForm.setDATA08(rs.getString("EHF030106T0_01"));
				bForm.setDATA09(rs.getString("EHF030106T0_02"));
				bForm.setDATA10(rs.getString("EHF030106T0_03"));
				list.add(bForm);
			}
			
			rs.close();
			p6stmt.close();
			pstmt.close();
			
			request.setAttribute("Status", "1");
			if(list.size() == 0){
				request.setAttribute("MSG", "查無資料!!!");
			}
			
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
		
		FormUtils.setFormDisplayMode(request, form, "create");
//		FormUtils.setFieldDisplayMode(request, form, "DATA05", 2);
		request.setAttribute("Form1Datas",Form);
		request.setAttribute("Form2Datas",list);
		request.setAttribute("button","query");
		request.setAttribute("collection","show");
		//清空新增欄位
		Form.setDATA05("");  //銀行代號
		Form.setDATA06("");  //銀行名稱
		Form.setDATA07("");  //備註
		return mapping.findForward("success");
	}
	
	public ActionForward DetailForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String[] id = request.getParameterValues("checkId");
		
		if ((id==null?false:!id[0].equals(""))){
			//EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
			BA_REPORTF Form = (BA_REPORTF)form;
			Connection conn = null;
			//BA_TOOLS ba = new BA_TOOLS();
			List list = new ArrayList();
			String sql = "";
			
			try{
				conn = ba.getConnection("SPOS");
				
				sql = " SELECT * " +
					  " FROM `ehf030106t0` " +
					  " WHERE 1=1 " +
					  " and EHF030106T0_01 = ? " +  //銀行代號
					  " and EHF030106T0_04 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
				p6stmt.setString(1, id[0]);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					Form.setDATA05(rs.getString("EHF030106T0_01"));
					Form.setDATA06(rs.getString("EHF030106T0_02"));
					Form.setDATA07(rs.getString("EHF030106T0_03"));
					Form.setDATA11(rs.getString("USER_CREATE"));
					Form.setDATA12(rs.getString("USER_UPDATE"));
					Form.setDATA13(rs.getString("VERSION"));
					Form.setDATA14(rs.getString("DATE_UPDATE"));
				}
				rs.close();
				p6stmt.close();
				pstmt.close();
				
				request.setAttribute("Status", "2");
				request.setAttribute("Form1Datas",Form);
				request.setAttribute("Form2Datas",list);
				request.setAttribute("button","save");
				FormUtils.setFormDisplayMode(request, form, "edit");
//				FormUtils.setFieldDisplayMode(request, form, "DATA05", 1);
				
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
			return mapping.findForward("success");
			
		}else{
			
			request.setAttribute("MSG", "請選擇要修改的資料");
			return queryForm(mapping,form,request,response);
		}
		
		//return mapping.findForward("success");
	}
	
	/**
	 * 儲存資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward SaveForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		String sql = "";
		Connection conn = null;
		//BA_TOOLS ba = new BA_TOOLS();
		List list = new ArrayList();
		//EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		BA_REPORTF Form = (BA_REPORTF)form;
		
		try{
			conn = ba.getConnection("SPOS");
			
			sql = " update EHF030106T0 set"+
				  " EHF030106T0_02=?, EHF030106T0_03=?, " +
				  " USER_UPDATE=?, VERSION=VERSION=VERSION+1, DATE_UPDATE=NOW() "+
				  " where 1=1 " +
				  " and EHF030106T0_01 = ? " +  //銀行代號
				  " and EHF030106T0_04 = ? ";  //公司代碼
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			p6stmt.setString(1, Form.getDATA06());
			p6stmt.setString(2, Form.getDATA07());
			p6stmt.setString(3, getLoginUser(request).getUserName());
			//p6stmt.setInt(4, Integer.parseInt(Form.getDATA13())+1);
			p6stmt.setString(4, Form.getDATA05());
			p6stmt.setString(5, getLoginUser(request).getCompId());  //公司代碼
			
			pstmt.executeUpdate();
			conn.commit();
			
			p6stmt.close();
			pstmt.close();
			request.setAttribute("MSG", "修改成功!");
			
		}catch(Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.setAttribute("MSG", "修改失敗!");
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		request.setAttribute("button","save");
		FormUtils.setFormDisplayMode(request, form, "edit");
//		FormUtils.setFieldDisplayMode(request, form, "DATA05", 1);
		request.setAttribute("Status", "2");
		request.setAttribute("Form1Datas",Form);
		request.setAttribute("Form2Datas",list);
		return mapping.findForward("success");
	}
	
	/**
	 * 回查詢畫面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward goback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		return init(mapping,form,request,response);
	}
	
	/**
	 * 刪除資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward DelForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		//BA_TOOLS ba = new BA_TOOLS();
		String[] id = request.getParameterValues("checkId");
		
		if ((id==null?false:!id[0].equals(""))){
			Connection conn = null;
			try{
				conn = ba.getConnection("SPOS");
				conn.setAutoCommit(false);
				String sql = "";
				PreparedStatement pstmt = null;
				P6PreparedStatement p6stmt = null;
				String compid = getLoginUser(request).getCompId();
				
				for(int i=0;i < id.length;i++){	
					sql = " delete from EHF030106T0 " +
						  " where 1=1 " +
						  " and EHF030106T0_01 = '"+id[i]+"' " +  //銀行代號
						  " and EHF030106T0_04 = '"+compid+"' ";  //公司代碼
					pstmt = conn.prepareStatement(sql);
					p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
					//System.out.println("===========>"+id[i]);
		    		pstmt.execute(sql);
				}
				
				conn.commit();
				pstmt.close();
				p6stmt.close();
				request.setAttribute("MSG","刪除成功!!");
				
			}catch(Exception e){
				request.setAttribute("MSG","刪除失敗!!");
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally{
				try {
    				if (conn != null && !conn.isClosed()) {
    					conn.close();
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
			}
			
			return init(mapping,form,request,response);
		}else{
			request.setAttribute("MSG","請選取要刪除的資料!!");
    		return init(mapping,form,request,response);
		}
	}
	
}
