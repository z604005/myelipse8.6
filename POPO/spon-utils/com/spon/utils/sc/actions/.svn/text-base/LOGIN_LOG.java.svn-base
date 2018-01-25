package com.spon.utils.sc.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.struts.form.BA_REPORTF;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

public class LOGIN_LOG  extends NewDispatchAction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public LOGIN_LOG(){
		
	}
	public void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Action的進入點
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		//將表單的顯示模式設定為create (新增模式)
		FormUtils.setFormDisplayMode(request, form, "create");
		try {
			conn = tools.getConnection();
			
	        generateSelectBox(conn,form,request);
	        BA_REPORTF Form=new BA_REPORTF();
	        Collection list =new ArrayList();
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
	    } catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			}catch(Exception e){
				
			}
	    }
		return mapping.findForward("success");
	}

	/**
	 * 執行查詢
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		BA_REPORTF Form=(BA_REPORTF)form;
		Collection list =new ArrayList();
		
		String sql="";
		ActionErrors l_actionErrors = new ActionErrors();
		
		BA_TOOLS ba = BA_TOOLS.getInstance();
		Connection conn = null;

		//將表單的顯示模式設定為create (新增模式)
		FormUtils.setFormDisplayMode(request, form, "edit");
		request.setAttribute("STATUS", "(查詢中)");
		try {
			conn = ba.getConnection();
	        generateSelectBox(conn,form,request);
	    } catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }
	    if (l_actionErrors.isEmpty()) {
			try {
				
				sql="SELECT * FROM login_log " +
					" WHERE  1=1 ";
				if (!Form.getDATA01().equals("") && !Form.getDATA02().equals("")) {
					sql += " AND date BETWEEN "+ba.stringToymd(Form.getDATA01())+" AND  "+ba.stringToymd(Form.getDATA02())+"  ";
				}else if (!Form.getDATA01().equals("") ) {
					sql += " AND date >= "+ba.stringToymd(Form.getDATA01())+" ";
				}else if (!Form.getDATA02().equals("")) {
					sql += " AND date <= "+ba.stringToymd(Form.getDATA02())+"  ";
				}
				
				if(!"".equals(Form.getDATA04())){
					sql += " AND comp = '"+Form.getDATA04()+"'";
				}
				
				sql +="ORDER BY date";
				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				String sql1 ="";
				while (rs.next()){
					BA_REPORTF nForm = new BA_REPORTF();
					nForm.setDATA01(rs.getString("user"));
					nForm.setDATA02(rs.getString("ip"));
					nForm.setDATA03(rs.getString("date"));
					nForm.setDATA04(rs.getString("comp"));
					nForm.setDATA11(rs.getString("index"));
					list.add(nForm);
				}
				
				rs.close();
				stmt.close();
			}catch(Exception E) {
				request.setAttribute("MSG","系統查詢資料時，發生不可預期錯誤，請洽系統人員，作業LOGIN_LOG.queryDatas()!!");
				System.out.println(E.toString());
			}finally {
		    	try {
					conn.close();
				}catch(Exception e){
					
				}
		    }		
			
		}else{
			request.setAttribute("MSG","請輸入正確的查詢條件!!");
			
		}
		request.setAttribute("Form1Datas",Form);
		request.setAttribute("Form2Datas",list);
		return mapping.findForward("success");
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return queryDatas(mapping,form,request,response);
	}
	/**
	 * 刪除明細資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward delForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		String arrid[] = request.getParameterValues("checkId");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
    	if (arrid==null?false:!arrid[0].equals("")){
    		try {
    			conn = tools.getConnection();
    			
				generateSelectBox(conn, form, request);
				String sql="";
				Statement stmt = conn.createStatement();
				for (int i=0;i<arrid.length;i++){
					sql = " DELETE FROM login_log WHERE `index`='"+arrid[i]+"' ";
					stmt.executeUpdate(sql);
				}
				stmt.close();
				conn.commit();
				conn.close();
    		} catch (Exception e) {
    			e.printStackTrace();
    		} finally {
    			try {
    				if (conn != null && !conn.isClosed()) {
    					conn.close();
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
    		}
    		return queryDatas(mapping,form,request,response);
    	}else{
    		request.setAttribute("MSG","請選取要刪除的明細資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
	}
	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request){
		// TODO Auto-generated method stub
		request.setAttribute("DATA03_LIST", this.getSelectOptions(conn, " SELECT FM010001_01 item_id,FM010001_02 item_value FROM FM010001 WHERE 1=1 ", true));
	}
	/**
	 * 執行確認
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		BA_REPORTF Form=(BA_REPORTF)form;
		ActionErrors lc_errors = new ActionErrors();//Form.validate(mapping, request, null);
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		if (lc_errors.isEmpty()) {
			String arrid[] = request.getParameterValues("checkId");
	    	if ((arrid==null?false:!arrid[0].equals(""))){
				try {
					
					Statement stmt = null;
					String sql="";
					ResultSet rs = null;
					Statement stmt1 = null;
					
					conn = tools.getConnection();
					stmt=conn.createStatement();
					
					String FM020101_01="";
					int allcount=0;
					for (int i=0 ; i<arrid.length;i++){
						FM020101_01=arrid[i];
						sql = " SELECT * FROM FM020101 WHERE FM020101_01='"+FM020101_01+"' ";
						rs = stmt.executeQuery(sql);
						if (rs.next()){
							if (rs.getString("FM020101_P").equals("1")){ //處理中的文件才可以做確認
								rs.close();
								sql = " SELECT * FROM FM020102 WHERE FM020102_01='"+FM020101_01+"' ";
								rs = stmt.executeQuery(sql);
								stmt1=conn.createStatement();
								String sql1 ="";
								while (rs.next()){
									sql1 =" UPDATE FM010101 SET FM010101_08=FM010101_08+("+rs.getString("FM020102_05")+") " +
											" WHERE FM010101_01 = '"+rs.getString("FM020102_03")+"' ";
									stmt1.execute(sql1);
								}
								stmt1.close();
								rs.close();
								stmt.execute("UPDATE FM020101 SET FM020101_P='2' WHERE FM020101_01= '"+FM020101_01+"' ");
								stmt.execute("UPDATE FM020102 SET FM020102_P='2' WHERE FM020102_01= '"+FM020101_01+"' ");
								allcount++;
							}
						}
					}
					stmt.close();
					conn.commit();
					request.setAttribute("MSG", "共處理"+ allcount+" 件,確認完成!");
				} catch (Exception e) {
					e.printStackTrace();
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
	    	}
		} else {
			saveErrors(request, lc_errors);
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			return mapping.findForward("success");
		}
		return queryDatas(mapping, form, request, response);
	}
}
