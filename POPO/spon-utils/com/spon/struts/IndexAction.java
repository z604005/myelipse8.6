package com.spon.struts;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.utils.sc.actions.SC006A;
import com.spon.utils.sc.actions.SC007A;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.sc.forms.SC006F;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 首頁
 * @version 1.0
 * @created 07-四月-2006 下午 02:15:23
 */
public class IndexAction extends NewDispatchAction {

	public IndexAction(){

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
		
		SC003F userform = null;
		request.getSession().setAttribute("Pageid",null);
		
		if (request.getSession().getAttribute("userform") != null 
			&& request.getSession().getAttribute("auth") != null ){
			
			userform = (SC003F)request.getSession().getAttribute("userform");
			SC007A sc007a = new SC007A();
			
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Connection conn = null;
			
			try {
				//conn = ds.getConnection();
			    conn = tools.getConnection();
				
				ArrayList list = sc007a.showMsgListForUser(conn,form,request,userform(request).getSC0030_01());	//原簡訊
				request.setAttribute("Form1Datas",list);
				
				//String shutdwon = new SC006A().getSysParam(conn, form, request, "SHUTDOWN");
				String sql = " select * from SC0060 where SC0060_01 = 'SHUTDOWN'";
				SC006F sc006f=(SC006F)BA_TOOLS.getInstance().getFormBean(conn ,sql ,new SC006F());
				
				if(sc006f.getSC0060_03().equals("Y") & !userform.getSC0030_01().startsWith("001"))
				{
					request.setAttribute("ShutDownText", sc006f.getSC0060_04());
					return mapping.findForward("shutdown");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}finally{
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			return mapping.findForward("success");
		}else{
			return mapping.findForward("fail");
		}
	}
	
	public void finalize() throws Throwable {
		super.finalize();
	}
}