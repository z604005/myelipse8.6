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
import com.spon.utils.sc.forms.SC901F;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;


/**
 * 設定密碼
 * @version 1.0
 * @created 10-四月-2006 下午 04:37:53
 */
public class SC901A extends NewDispatchAction {

	/**
	 * 一開始進來程式時顯示空的資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		FormUtils.setFormDisplayMode(request, form, "edit");
		request.setAttribute("Form1Datas", new SC901F());
		request.getSession().removeAttribute("QueryString");
		//request.setAttribute("MSG", "請查詢資料!");
		return mapping.findForward("success");
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
		SC901F Form = (SC901F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
		Form.setUSERID(userform(request).getSC0030_01());
        request.setAttribute("action","saveData");
		ActionErrors lc_errors = Form.validate(mapping, request,conn);
		
			
		try {
			if (lc_errors.isEmpty()) {
			//Statement stmt = conn.createStatement();
			String sql="";
			PreparedStatement stmt=null;
			
				sql="update SC0030 set " +
					"SC0030_02=?," +
					"SC0030_09=NOW()," +
					"DATE_UPDATE=NOW()," +
					"USER_UPDATE=?" +
					" where SC0030_01='" +userform(request).getSC0030_01()+ "'";
				
				stmt = conn.prepareStatement(sql);
				stmt.setString(1,BA_TOOLS.encodeInMD5(Form.getNEW_PASSWORD()));
				stmt.setString(2, userform(request).getSC0030_01());
				
			
			stmt.executeUpdate();
			stmt.close();
			conn.commit();
			
			request.setAttribute("MSG", "修改成功!");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status","error");
				return init(mapping, form, request, response);
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "修改失敗!");
			System.out.println("SC901A.saveData() "+e );
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
		
		return init(mapping, form, request, response);	}
	}