package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.ems.salary.forms.EHF030600M0F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>薪資計算發放
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030600M0A extends NewDispatchAction {

	public EHF030600M0A() {

	}

	/**
	 * 儲存新增資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF030600M0F Form = (EHF030600M0F) form;

		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("action", "addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);

		try {
			if (lc_errors.isEmpty()) {
					//新增表單資訊
					String sql = ""  ;

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
					int indexid = 1;
				
					
					p6stmt.executeUpdate();
					pstmt.close();
					p6stmt.close();			

					conn.commit();
//					generateSelectBox(conn, Form, request);
					
					request.setAttribute("MSG", "新增成功");
					request.setAttribute("button", "edit");
					request.setAttribute("collection", "");
					// 清掉畫面上的新增
			
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));

				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF030600M0A.addData() " + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
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

		return init(mapping, form, request, response);
	}

	/**
	 * 新增
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return addData(mapping, form, request, response);
	}
	
	/**
	 * 新增畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("redirectADD");
	}
	
	/**
	 * 明細資料畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward editForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		try{
			
			String arrid[] = request.getParameterValues("checkId");
			if ((arrid==null?false:!arrid[0].equals(""))){
				return mapping.findForward("redirectEDIT");
			}else{
				request.setAttribute("MSG","請選取要查看的資料!!");
				return queryDatas(mapping,form,request,response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return queryDatas(mapping,form,request,response);
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	private void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request) {
		
		//產生發放類別
		try{
			List listEHF030600T0_04 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030600T0_04.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("薪資");
			listEHF030600T0_04.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("獎金");
			listEHF030600T0_04.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("臨時");
			listEHF030600T0_04.add(bform);
			request.setAttribute("listEHF030600T0_04", listEHF030600T0_04);
		}catch(Exception e) {
			System.out.println(e);
		}
		

	}

	/**
	 * Action的進入點
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		
		
		
		
		try{
			
			conn = tools.getConnection("SPOS");
			EHF030600M0F Form = new EHF030600M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
			
			Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getCompId());
			
			//入扣帳年月
//			Form.setEHF030600T0_M(tools.ymTostring(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2))));
			//薪資年月
//			Form.setEHF030600T0_02(tools.ymTostring(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2))));
			//公司代碼
			Form.setEHF030600T0_03(getLoginUser(request).getCompId());
			//公司名稱
			Form.setEHF030600T0_03_TXT((String) (compMap.get("COMP_NAME_C")));
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "query");
			request.setAttribute("collection", "show");
			hr_tools.close();

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
	 * 查詢資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		EHF030600M0F Form = (EHF030600M0F) form;

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			
			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT EHF030600T0_01, EHF030600T0_U, " +
				" RPAD( REPLACE( EHF030600T0_M, '/', '年' ), CHAR_LENGTH(EHF030600T0_M)+1, '月' ) AS COST_YEAR, " +
				" RPAD( REPLACE( EHF030600T0_02, '/', '年' ), CHAR_LENGTH(EHF030600T0_02)+1, '月' ) AS SALARY_YEAR, " +
				" EHF030600T0_03, " +
				" CASE EHF030600T0_04 WHEN '01' THEN '薪資' WHEN '02' THEN '獎金' WHEN '03' THEN '臨時' END AS EHF030600T0_04, " +
				" EHF030600T0_C, EHF030600T0_AM, " +
				" USER_UPDATE, DATE_UPDATE, " +
				" CASE EHF030600T0_SCP WHEN '01' THEN '處理中' WHEN '02' THEN '已完成薪資計算' WHEN '03' THEN '已確認' WHEN '04' THEN '已出帳' " +
				"					   WHEN '05' THEN '已結算' END AS EHF030600T0_SCP " +
				" FROM EHF030600T0 " +
				" WHERE 1=1 " +
				" AND EHF030600T0_14 = '"+getLoginUser(request).getCompId()+"' " ;  //公司代碼
				
				if(!"".equals(Form.getEHF030600T0_M()) && Form.getEHF030600T0_M() != null ){
					sql += " AND EHF030600T0_M = '"+Form.getEHF030600T0_M()+"' ";  //入扣帳年月
				}
				if(!"".equals(Form.getEHF030600T0_02()) && Form.getEHF030600T0_02() != null ){
					sql += " AND EHF030600T0_02 = '"+Form.getEHF030600T0_02()+"' ";  //薪資年月
				}
				//發放類別
				if(!"".equals(Form.getEHF030600T0_04()) && Form.getEHF030600T0_04()!= null ){
	    			sql += " and EHF030600T0_04 = '"+Form.getEHF030600T0_04()+"' ";
	    		}
				
				sql += " ORDER BY EHF030600T0_01 DESC LIMIT 20 ";

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					rs.beforeFirst();
					
					Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getCompId());
					
					while(rs.next()){
					
						EHF030600M0F bean = new EHF030600M0F();
						bean.setEHF030600T0_01(rs.getString("EHF030600T0_01"));  //薪資計算序號
						bean.setEHF030600T0_U(rs.getString("EHF030600T0_U"));  //薪資計算UID
						bean.setEHF030600T0_M(rs.getString("COST_YEAR"));  //入扣帳年月
						bean.setEHF030600T0_02(rs.getString("SALARY_YEAR"));  //薪資年月
						bean.setEHF030600T0_03((String) (compMap.get("COMP_NAME_C")));  //公司組織
						bean.setEHF030600T0_04(rs.getString("EHF030600T0_04"));  //發放類別
						bean.setEHF030600T0_C(rs.getString("EHF030600T0_C"));  //處理總筆數
						bean.setEHF030600T0_AM(rs.getString("EHF030600T0_AM"));  //處理總金額
						bean.setEHF030600T0_SCP(rs.getString("EHF030600T0_SCP"));  //薪資處理狀態
						bean.setUSER_UPDATE(rs.getString("USER_UPDATE"));  //最後修改人員
						bean.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));  //最後異動日期
						
						list.add(bean);
					}
					
					request.setAttribute("MSG","查詢成功!!");
				}else{
					
					request.setAttribute("MSG","查無資料!!");
				}
				
				request.setAttribute("Form2Datas",list);
//				Form.setEHF030600C(list);
				
				rs.close();
				stmt.close();
				
				hr_tools.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Form.setEHF030600T0_M("");
		Form.setEHF030600T0_02("");

		request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF030600M0F Form = (EHF030600M0F) form;
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		String arrid[] = request.getParameterValues("checkId");
//    	if ((arrid==null?false:!arrid[0].equals(""))){
//    		Form.setEHF030102T0_01(arrid[0]);
//		}else if("".equals(Form.getEHF030102T0_01()) || Form.getEHF030102T0_01() == null){
//			request.setAttribute("MSG", "請先選擇一筆要修改的資料!!");
//			return queryDatas(mapping, form, request, response);
//		}
    	
    	try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "editDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" ;

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				if(rs.next()){
					

					
				}
				
				
				request.setAttribute("Form2Datas",list);
//				Form.setEHF030102C(list);
				
				rs.close();
				stmt.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
	}

	public ActionForward saveData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030600M0F Form = (EHF030600M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
//			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			BA_Vaildate ve=BA_Vaildate.getInstance();
//			if("".equals(Form.getEHF030102T0_01())){
//				request.setAttribute("MSG","薪資計算序號錯誤!!請重新查詢資料!!");
//				return init(mapping, form, request, response);
//				
//			}

			if (lc_errors.isEmpty()) {
				
				String sql = "" ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				request.setAttribute("MSG","儲存成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
	
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {

			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			
			request.setAttribute("MSG","薪資計算儲存錯誤!!請重新操作!!");
						
			System.out.println(e);
			e.printStackTrace();
			
			return init(mapping, form, request, response);
			
			
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return editDataForm(mapping, form, request, response);
	}

	public ActionForward delData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		Connection conn = null;
		EHF030600M0F Form = (EHF030600M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030600T0_01(arrid[0]);  //薪資計算序號
		}else{
			request.setAttribute("MSG", "請先選擇一筆要刪除的資料!!");
			return queryDatas(mapping, form, request, response);
		}
		
		try {
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
					
					String comp_id = getLoginUser(request).getCompId();
				
					Statement stmt = conn.createStatement();
					ResultSet rs = null;
					String sql = "" ;
					sql = " SELECT * FROM EHF030600T0 " +
						  " WHERE 1=1 " +
						  " AND EHF030600T0_01 = "+Form.getEHF030600T0_01()+" " +
						  " AND EHF030600T0_14 = '"+comp_id+"' ";				
					rs = stmt.executeQuery(sql);
					
					if(rs.next()){
					
						//判斷薪資處理狀態
						if("01".equals(rs.getString("EHF030600T0_SCP")) || "02".equals("EHF030600T0_SCP") ){
							//薪資處理狀態 = '01' or '02', 才可以刪除
							
							//刪除計算明細資料
							sql = " DELETE FROM EHF030600T1 " +
							  	  " WHERE 1=1 " +
							  	  " AND EHF030600T1_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
							  	  " AND EHF030600T1_08 = '"+comp_id+"' ";  //公司別條件
							stmt.execute(sql);	
							
							//刪除計算表頭資料
							sql = " DELETE FROM EHF030600T0 " +
								  " WHERE 1=1 " +
								  " AND EHF030600T0_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
								  " AND EHF030600T0_14 = '"+comp_id+"' ";  //公司別條件
						
							stmt.execute(sql);
							
							//先刪除舊資料
							//刪除 EHF030600T2
							sql = "" +
							" DELETE FROM EHF030600T2 " +
							" WHERE 1=1 " +
							" AND EHF030600T2_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
							//" AND EHF030600T2_M = '"+salYYMM+"' " +  //薪資年月
							//" AND EHF030600T2_04 = '"+costYYMM+"' " +  //入扣帳年月
							" AND EHF030600T2_23 = '"+comp_id+"' ";  //公司代碼
							stmt.execute(sql);
							
							
							//刪除EHF030600T3
							sql = "" +
							" DELETE FROM EHF030600T3 " +
							" WHERE 1=1 " +
							" AND EHF030600T3_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
							//" AND EHF030600T3_M1 = '"+salYYMM+"' " +  //薪資年月
							//" AND EHF030600T3_M = '"+costYYMM+"' " +  //入扣帳年月
							" AND EHF030600T3_07 = '"+comp_id+"' ";  //公司代碼
							stmt.execute(sql);
							
							
							
							conn.commit();
							request.setAttribute("MSG", "刪除成功");
						}else{
							//薪資處理狀態 >= '03', 不可刪除
							//顯示不可刪除的錯誤訊息
							request.setAttribute("MSG", "薪資處理狀態 = "+tools.getSalaryStatus(rs.getString("EHF030600T0_SCP"))+", 資料不可刪除!!");
						}
						
					}else{
						request.setAttribute("MSG", "刪除失敗，選取的資料不存在!!");
					}
					
					rs.close();
					stmt.close();
								
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return init(mapping, form, request, response);
			}
			
		} catch (Exception e) {
			System.out.println(e);
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
		
		return init(mapping, form, request, response);
	}
	
}