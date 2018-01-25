package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.fileimport.IMP_EHF030500;
import com.spon.ems.salary.forms.EHF030500M0F;
import com.spon.ems.salary.forms.EX030500R0F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.BA_REPORTF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>其他補扣款
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030500M0A extends NewDispatchAction {

	public EHF030500M0A() {

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
		EHF030500M0F Form = (EHF030500M0F) form;

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
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
				
					
					p6stmt.executeUpdate();
					pstmt.close();
					p6stmt.close();			

					conn.commit();
					generateSelectBox(conn, Form, request);
					
					request.setAttribute("MSG", "新增成功");
					request.setAttribute("button", "edit");
					request.setAttribute("collection", "");
					// 清掉畫面上的新增
			
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
//				return init(mapping, form, request, response);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF030500M0A.addData() " + e);
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
		
		//產生員工類別
		try{
			List listEHF030500T0_04 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030500T0_04.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("正式員工");
			listEHF030500T0_04.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("臨時員工");
			listEHF030500T0_04.add(bform);
			request.setAttribute("listEHF030500T0_04", listEHF030500T0_04);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生發放類別
		try{
			List listEHF030500T0_07 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030500T0_07.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("薪資");
			listEHF030500T0_07.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("獎金");
			listEHF030500T0_07.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("臨時");
			listEHF030500T0_07.add(bform);
			request.setAttribute("listEHF030500T0_07", listEHF030500T0_07);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生發放類別
		try{
			List listEHF030500T0_06 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030500T0_06.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("放款");
			listEHF030500T0_06.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("扣款");
			listEHF030500T0_06.add(bform);
			request.setAttribute("listEHF030500T0_06", listEHF030500T0_06);
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
		
		try{
			
			conn = tools.getConnection("SPOS");
			EHF030500M0F Form = new EHF030500M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
			
			//入扣帳年月
			//Form.setEHF030500T0_05(tools.ymTostring(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2))));
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "query");
			request.setAttribute("collection", "show");
			

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
		EHF030500M0F Form = (EHF030500M0F) form;

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			
			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				ArrayList DepartureWorkEmplist = new ArrayList();//離職員工清單
				ArrayList SuspensionWithoutPayEmplist = new ArrayList();//留職停薪員工清單
				String sql = "" +
				" SELECT EHF030500T0_01, EHF030500T0_U, " +
				" RPAD( REPLACE( EHF030500T0_05, '/', '年' ), CHAR_LENGTH(EHF030500T0_05)+1, '月' ) AS SALARY_YEAR, " +
//				" CONCAT( SUBSTRING(EHF030500T0_05, 1, 2), '年', SUBSTRING(EHF030500T0_05, 3, 2), '月' ) AS SALARY_YEAR, " +
				" EHF030500T0_02, EHF030500T0_03, " +
				" CASE EHF030500T0_07 WHEN '01' THEN '薪資' WHEN '02' THEN '獎金' WHEN '03' THEN '臨時' END AS EHF030500T0_07, " +
				" CONCAT( ( CASE EHF030500T0_06 WHEN '01' THEN '放款' " +
				" 				      WHEN '02' THEN '扣款' END ), ' ', " +
				" ( CASE EHF030500T0_06 WHEN '01' THEN EHF030500T0_08 " +
				"						WHEN '02' THEN EHF030500T0_10 END ), ':', " +
				" ( CASE EHF030500T0_06 WHEN '01' THEN EHF030500T0_09 " +
				"  					    WHEN '02' THEN EHF030500T0_11 END ), '元' ) AS MONEY, " +
				" USER_UPDATE, DATE_UPDATE, EHF030500T0_SCP " +
				" FROM EHF030500T0 " +
				" WHERE 1=1 " +
				" AND EHF030500T0_15 = '"+getLoginUser(request).getCompId()+"' " ;
				
				if(!"".equals(Form.getEHF030500T0_05()) && Form.getEHF030500T0_05() != null ){
					sql += " AND EHF030500T0_05 = '"+Form.getEHF030500T0_05()+"' ";  //薪資年月
				}
				
				//員工工號
				if(!"".equals(Form.getEHF030500T0_02()) && Form.getEHF030500T0_02()!= null ){
	    			sql += " and EHF030500T0_02 = '"+Form.getEHF030500T0_02()+"' ";
	    		}
				
				//部門代號
				if(!"".equals(Form.getEHF030500T0_03()) && Form.getEHF030500T0_03()!= null ){
	    			sql += " and EHF030500T0_03 = '"+Form.getEHF030500T0_03()+"' ";
	    		}
				
				//員工類別
				if(!"".equals(Form.getEHF030500T0_04()) && Form.getEHF030500T0_04()!= null ){
	    			sql += " and EHF030500T0_04 = '"+Form.getEHF030500T0_04()+"' ";
	    		}
				
				//發放類別
				if(!"".equals(Form.getEHF030500T0_07()) && Form.getEHF030500T0_07()!= null ){
	    			sql += " and EHF030500T0_07 = '"+Form.getEHF030500T0_07()+"' ";
	    		}
				
				//放扣款類別
				if(!"".equals(Form.getEHF030500T0_06()) && Form.getEHF030500T0_06()!= null ){
	    			sql += " and EHF030500T0_06 = '"+Form.getEHF030500T0_06()+"' ";
	    		}
				
				
				sql += " ORDER BY EHF030500T0_01 DESC";// LIMIT 20 

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				//員工Map
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				//部門Map
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
				
				
				
				//在職員工清單
				Map WorkEmp = new HashMap();

				//離職員工清單
				Map DepartureWorkEmp = new HashMap();
			
				//留職停薪員工清單
				Map SuspensionWithoutPayEmp= new HashMap();

				//全部都不選，則表示查詢全部
				if(Form.getEHF030500T0_20()==null&&Form.getEHF030500T0_21()==null&&Form.getEHF030500T0_22()==null){
					Form.setEHF030500T0_20("0");
					Form.setEHF030500T0_21("0");
					Form.setEHF030500T0_22("0");
				}
//				if("0".equals(Form.getEHF030500T0_20())||"1".equals(Form.getEHF030500T0_20())){
//					//在職員工清單
//					WorkEmp=ems_tools.getWorkEmpNameMap(getLoginUser(request).getUserId(),getLoginUser(request).getCompId());
//				}
//					
//				if("0".equals(Form.getEHF030500T0_21())||"2".equals(Form.getEHF030500T0_21())){
//					//離職員工清單
//					DepartureWorkEmp=ems_tools.getDepartureWorkEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
//				}
//				
//				if("0".equals(Form.getEHF030500T0_22())||"3".equals(Form.getEHF030500T0_22())){
//					//留職停薪員工清單
//					SuspensionWithoutPayEmp=ems_tools.getSuspensionWithoutPayEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());	
//				}
				boolean chk_flag = false;//在職判斷
				boolean SuspensionWithoutPay= false;//留職停薪判斷
				boolean DepartureWork= false;//離職停薪判斷
				String tmp_emp_id ="";
				

				
				if(rs.next()){
					
					rs.beforeFirst();
					
					while(rs.next()){
						chk_flag = false;//在職判斷
						SuspensionWithoutPay= false;//留職停薪判斷
						DepartureWork= false;//離職停薪判斷
						
						
//						//根據前端勾選的找尋員工
//						
//						//1.先處理留職停薪人員
//						if("0".equals(Form.getEHF030500T0_20())||"3".equals(Form.getEHF030500T0_20())){
//							Iterator departure_emp_it = SuspensionWithoutPayEmp.keySet().iterator();
//							while (departure_emp_it.hasNext()) {
//								tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
//								if (tmp_emp_id.equals(rs.getString("EHF030500T0_02"))) {
//									SuspensionWithoutPay = true;
//									break;
//								} else {
//									SuspensionWithoutPay = false;
//								}
//							}
//						}
//						//2.處理離職員工
//						if("0".equals(Form.getEHF030500T0_21())||"2".equals(Form.getEHF030500T0_21())){
//							Iterator departure_emp_it = DepartureWorkEmp.keySet().iterator();
//							while (departure_emp_it.hasNext()) {
//								tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
//								if (tmp_emp_id.equals(rs.getString("EHF030500T0_02"))) {
//									DepartureWork = true;
//									break;
//								} else {
//									DepartureWork = false;
//								}
//							}
//							
//						}
//						
//						//2.處理在職員工
//						if("0".equals(Form.getEHF030500T0_22())||"1".equals(Form.getEHF030500T0_22())){
//							Iterator departure_emp_it = WorkEmp.keySet().iterator();
//							while (departure_emp_it.hasNext()) {
//								tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
//								if (tmp_emp_id.equals(rs.getString("EHF030500T0_02"))) {
//									chk_flag = true;
//									break;
//								} else {
//									chk_flag = false;
//								}
//							}
//							
//						}

//						if(chk_flag){

							EHF030500M0F bean = new EHF030500M0F();
							bean.setEHF030500T0_01(rs.getInt("EHF030500T0_01"));  //其他費用序號
							bean.setEHF030500T0_U(rs.getString("EHF030500T0_U"));  //其他費用UID
							bean.setEHF030500T0_05(rs.getString("SALARY_YEAR"));  //薪資年月
							bean.setEHF030500T0_03(rs.getString("EHF030500T0_03")+" "+ (String) ((Map)depMap.get(rs.getString("EHF030500T0_03"))).get("SHOW_DESC")+" / "+
							rs.getString("EHF030500T0_02")+" "+(String) ((Map)empMap.get(rs.getString("EHF030500T0_02"))).get("EMPLOYEE_NAME"));  //部門/員工
							bean.setEHF030500T0_07(rs.getString("EHF030500T0_07"));  //發放類別
							bean.setEHF030500T0_08(rs.getString("MONEY"));  //金額
							bean.setUSER_UPDATE(rs.getString("USER_UPDATE"));  //最後修改人員
							bean.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));  //最後異動日期
							
							list.add(bean);
							
							
//						}else if(DepartureWork){
//							
//							EHF030500M0F bean = new EHF030500M0F();
//							bean.setEHF030500T0_01(rs.getInt("EHF030500T0_01"));  //其他費用序號
//							bean.setEHF030500T0_U(rs.getString("EHF030500T0_U"));  //其他費用UID
//							bean.setEHF030500T0_05(rs.getString("SALARY_YEAR"));  //薪資年月
//							bean.setEHF030500T0_03(rs.getString("EHF030500T0_03")+" "+ (String) ((Map)depMap.get(rs.getString("EHF030500T0_03"))).get("SHOW_DESC")+" / "+
//							rs.getString("EHF030500T0_02")+" "+(String) ((Map)empMap.get(rs.getString("EHF030500T0_02"))).get("EMPLOYEE_NAME")+"(離職)");  //部門/員工
//							bean.setEHF030500T0_07(rs.getString("EHF030500T0_07"));  //發放類別
//							bean.setEHF030500T0_08(rs.getString("MONEY"));  //金額
//							bean.setUSER_UPDATE(rs.getString("USER_UPDATE"));  //最後修改人員
//							bean.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));  //最後異動日期
//				
//							
//					
//							DepartureWorkEmplist.add(bean);
//						}
//						else if(SuspensionWithoutPay){
//							EHF030500M0F bean = new EHF030500M0F();
//							bean.setEHF030500T0_01(rs.getInt("EHF030500T0_01"));  //其他費用序號
//							bean.setEHF030500T0_U(rs.getString("EHF030500T0_U"));  //其他費用UID
//							bean.setEHF030500T0_05(rs.getString("SALARY_YEAR"));  //薪資年月
//							bean.setEHF030500T0_03(rs.getString("EHF030500T0_03")+" "+ (String) ((Map)depMap.get(rs.getString("EHF030500T0_03"))).get("SHOW_DESC")+" / "+
//							rs.getString("EHF030500T0_02")+" "+(String) ((Map)empMap.get(rs.getString("EHF030500T0_02"))).get("EMPLOYEE_NAME")+"(留職停薪)");  //部門/員工
//							bean.setEHF030500T0_07(rs.getString("EHF030500T0_07"));  //發放類別
//							bean.setEHF030500T0_08(rs.getString("MONEY"));  //金額
//							bean.setUSER_UPDATE(rs.getString("USER_UPDATE"));  //最後修改人員
//							bean.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));  //最後異動日期
//				
//							SuspensionWithoutPayEmplist.add(bean);
//						}
						
						
						
					}
					
					request.setAttribute("MSG","查詢成功!!");
				}else{
					
					request.setAttribute("MSG","查無資料!!");
				}
				
				list.addAll(DepartureWorkEmplist);
				list.addAll(SuspensionWithoutPayEmplist);
				request.setAttribute("Form2Datas",list);
//				Form.setEHF030500C(list);
				
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

		Form.setEHF030500T0_03("");
		Form.setEHF030500T0_03_TXT("");
		Form.setEHF030500T0_02("");
		Form.setEHF030500T0_02_TXT("");
		Form.setEHF030500T0_20("");
		Form.setEHF030500T0_21("");
		Form.setEHF030500T0_22("");
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
		
		EHF030500M0F Form = (EHF030500M0F) form;
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
		EHF030500M0F Form = (EHF030500M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			BA_Vaildate ve=BA_Vaildate.getInstance();
//			if("".equals(Form.getEHF030102T0_01())){
//				request.setAttribute("MSG","其他費用序號錯誤!!請重新查詢資料!!");
//				return init(mapping, form, request, response);
//				
//			}

			if (lc_errors.isEmpty()) {
				
				String sql = "" ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				
			//	System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
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
			
			request.setAttribute("MSG","其他費用儲存錯誤!!請重新操作!!");
						
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
//		return queryDatas(mapping, form, request, response);
//		return mapping.findForward("success");
		return editDataForm(mapping, form, request, response);
	}

	public ActionForward delData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		Connection conn = null;
		EHF030500M0F Form = (EHF030500M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030500T0_01(Integer.parseInt(arrid[0]));  //其他費用序號
		}else{
			request.setAttribute("MSG", "請先選擇一筆要刪除的資料!!");
			return queryDatas(mapping, form, request, response);
		}
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
					
					String comp_id = getLoginUser(request).getCompId();
				
					Statement stmt = conn.createStatement();
					ResultSet rs=null;
					String sql = "" ;
					sql = " SELECT * FROM EHF030500T0 " +
						  " WHERE 1=1 " +
						  " AND EHF030500T0_01 = "+Form.getEHF030500T0_01()+" " +
						  " AND EHF030500T0_15 = '"+comp_id+"' ";				
					rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						
						//判斷薪資處理狀態
						if("01".equals(rs.getString("EHF030500T0_SCP")) || "02".equals("EHF030500T0_SCP") ){
							//薪資處理狀態 = '01' or '02', 才可以刪除
							sql = " DELETE FROM ehf030500t0 " +
								  " WHERE 1=1 " +
								  " AND EHF030500T0_01 = "+Form.getEHF030500T0_01()+" " +
								  " AND EHF030500T0_15 = '"+comp_id+"' ";
							stmt.execute(sql);	
							conn.commit();
							request.setAttribute("MSG", "刪除成功");
						}else{
							//薪資處理狀態 >= '03', 不可刪除
							//顯示不可刪除的錯誤訊息
							request.setAttribute("MSG", "薪資處理狀態 = "+tools.getSalaryStatus(rs.getString("EHF030500T0_SCP"))+", 資料不可刪除!!");
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
	
	/**
	 * 下載空白範例匯入檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print_example(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF030500M0F Form = (EHF030500M0F) form;
		Connection conn = null;
		
		//建立資料庫連線
    	if (conn == null) {
    		try {
    			conn = tools.getConnection("SPOS");
    		} catch (SQLException e2) {
    			e2.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		}
    	
		try {
			//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			
    		HR_TOOLS hr_tools = new HR_TOOLS();
    		
    		//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			
			hr_tools.close();
			
			int DataCount = 0;
			
			String sql = "" +
			 			 " SELECT EHF010100T0_01, EHF010100T0_02, EHF010100T0_05, EHF010100T6_02 FROM EHF010100T0 " +
			 			 " LEFT JOIN EHF010100T1 ON EHF010100T1_01 = EHF010100T0_01 " +
			 			 " LEFT JOIN EHF010100T6 ON EHF010100T6_01 = EHF010100T0_01 " +
			 			 " WHERE 1=1 " +
			 			 " AND EHF010100T1_02 IN ('1','3') AND EHF010100T1_04 = '0' "+	//員工在職才執行
			 			 " AND EHF010100T0.HR_CompanySysNo = '"+authbean.getCompId()+"' " +
			 			 " ORDER BY EHF010100T6_02, EHF010100T0_02 ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			
			//產生Excel元件
			EX030500R0F ef = new EX030500R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getEmployeeID(), request, count);
			
			while(rs.next()){
				if(DataCount==0){
					
				}
				ef.next();
				
				if(count > 1){
					if(DataCount==0){
						for (int i = 0; i < count-1; i++) {
							ef.CopyCellFormat(5 + i , "A", 4, "A");// 複製格式
							ef.CopyCellFormat(5 + i , "B", 4, "B");// 複製格式
							ef.CopyCellFormat(5 + i , "C", 4, "C");// 複製格式
							ef.CopyCellFormat(5 + i , "D", 4, "D");// 複製格式
							ef.CopyCellFormat(5 + i , "E", 4, "E");// 複製格式
							ef.CopyCellFormat(5 + i , "F", 4, "F");// 複製格式
							ef.CopyCellFormat(5 + i , "G", 4, "G");// 複製格式
							ef.CopyCellFormat(5 + i , "H", 4, "H");// 複製格式
							ef.CopyCellFormat(5 + i , "I", 4, "I");// 複製格式
							ef.CopyCellFormat(5 + i , "J", 4, "J");// 複製格式
							ef.CopyCellFormat(5 + i , "K", 4, "K");// 複製格式
						}
					}
				}
				
				ef.setDetail(1,"A", (String) ((Map)depMap.get(rs.getString("EHF010100T6_02"))).get("SHOW_DEPT_ID"),false);  //部門代號
				ef.setDetail(1,"B", rs.getString("EHF010100T0_02"),false);//員工工號
				ef.setDetail(1,"C", rs.getString("EHF010100T0_05"),false);//員工姓名
				ef.setDetail(1,"F", "正式員工",false);//員工類別
				ef.setDetail(1,"G", "薪資",false);//發放類別
				
				DataCount++;
				
			}
			
			ef.EndDocument();
			
			rs.close();
			stmt.close();
			
			String FileName="";
			String name = "其他補扣款匯入.xls";
			
			if(DataCount>0){
				//存入檔案
				FileName = ef.write();
				request.setAttribute("MSG","列印完成!!");
				//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
				
			}else{				
				request.setAttribute("ERR_MSG","沒有資料可列印!!");	
			}
			
		} catch (Exception E) {
			E.printStackTrace();
			request.setAttribute("MSG", "列印失敗!!");
		} finally {
		// ef.write();
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return this.init(mapping, form, request, response);
	}
	
	/**
	 * 補扣款匯入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward impEHF030500(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF030500M0F Form = (EHF030500M0F) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//Collection List
			List list = new ArrayList();
			//NG_Collection List
			List ng_list = new ArrayList();
			//ERROR_Collection List
			List error_list = new ArrayList();
			
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			// 取得部門資訊
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

			// 取得員工資訊
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			
			//建立補扣款匯入元件
			IMP_EHF030500 imp_ehf030500 = new IMP_EHF030500( authbean.getEmployeeID(), (String)((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID"), authbean, depMap, empMap);
			
			//取得匯入檔案
			FormFile importfile = Form.getUPLOADFILE();
			
			//取得回傳訊息Map
			Map msgMap = imp_ehf030500.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(),
												  importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());
			//取得匯入的詳細資料清單
			List datalist = (List) msgMap.get("DATALIST");
			
			Iterator it = datalist.iterator();
			Map tempMap = null;
			EHF030500M0F bean = null;
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				bean = new EHF030500M0F();
				
				String SalaryMonth = 
					(String)tempMap.get("EHF030500T0_05_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF030500T0_05_month")));
				
				bean.setEHF030500T0_05(SalaryMonth);
				bean.setEHF030500T0_03((String)tempMap.get("EHF030500T0_03")+"/"+(String)tempMap.get("EHF030500T0_02_NAME"));
				bean.setEHF030500T0_03((String)tempMap.get("EHF030500T0_03")+"/"+(String)tempMap.get("EHF030500T0_02_NAME"));
				bean.setEHF030500T0_06((String)tempMap.get("EHF030500T0_06")==null?"":(String)tempMap.get("EHF030500T0_06"));
				bean.setEHF030500T0_10((String)tempMap.get("FEE_NAME")==null?"":(String)tempMap.get("FEE_NAME"));
				bean.setEHF030500T0_08((String)tempMap.get("FEE")==null?"":(String)tempMap.get("FEE"));
				bean.setEHF030500T0_13((String)tempMap.get("EHF030500T0_13")==null?"":(String)tempMap.get("EHF030500T0_13"));
				
				list.add(bean);
			}
			
			//判斷是否有重複未匯入的資料清單
			if(msgMap.containsKey("NGDATACOUNT")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("NGDATALIST");
				
				it = datalist.iterator();
				tempMap = null;
				bean = null;
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bean = new EHF030500M0F();
					//String SalaryMonth = 
					//	(String)tempMap.get("EHF030500T0_05_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF030500T0_05_month")));
					
					//bean.setEHF030500T0_05(SalaryMonth);
					bean.setEHF030500T0_03((String)tempMap.get("EHF030500T0_03")+"/"+(String)tempMap.get("EHF030500T0_02_NAME"));
					bean.setEHF030500T0_06((String)tempMap.get("EHF030500T0_06"));
					bean.setEHF030500T0_10((String)tempMap.get("FEE_NAME"));
					bean.setEHF030500T0_08((String)tempMap.get("FEE"));
					bean.setEHF030500T0_13((String)tempMap.get("error"));
					
					ng_list.add(bean);
				}
				if(ng_list.size()>0){
					// 設定前端顯示訊息
					request.setAttribute("NGMSG", "補扣款共有 "	+ (Integer) msgMap.get("NGDATACOUNT")+ " 筆重複資料未匯入!!");
					request.setAttribute("Form3Datas", ng_list);
					request.setAttribute("ng_collection", "show");
				}
			}
			
			//判斷是否有重複未匯入的資料清單
			if(msgMap.containsKey("ERRORDATACOUNT")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("ERRORDATALIST");
				
				it = datalist.iterator();
				tempMap = null;
				bean = null;
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bean = new EHF030500M0F();
					
					//String SalaryMonth = 
					//	(String)tempMap.get("EHF030500T0_05_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF030500T0_05_month")));
					
					//bean.setEHF030500T0_05(SalaryMonth);
					bean.setEHF030500T0_03((String)tempMap.get("EHF030500T0_03")+"/"+(String)tempMap.get("EHF030500T0_02_NAME"));
					bean.setEHF030500T0_06((String)tempMap.get("EHF030500T0_06"));
					bean.setEHF030500T0_10((String)tempMap.get("FEE_NAME"));
					bean.setEHF030500T0_08((String)tempMap.get("FEE"));
					bean.setEHF030500T0_13((String)tempMap.get("error"));
					
					error_list.add(bean);
				}
				if(error_list.size()>0){
					//設定前端顯示訊息
					request.setAttribute("ERRORMSG", "補扣款共有 "+(Integer)msgMap.get("ERRORDATACOUNT")+" 筆格式不正確資料未匯入!!");
					request.setAttribute("Form4Datas",error_list);
					request.setAttribute("error_collection", "show");
				}
			}
			
			hr_tools.close();
			
			//設定前端顯示訊息
			request.setAttribute("MSG", "補扣款共匯入 "+(Integer)msgMap.get("DATACOUNT")+" 筆!!");

			//設定前端顯示資訊
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("collection", "show");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if( conn!=null && !conn.isClosed() ){
					conn.close();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return mapping.findForward("redirectIMP");
	}
	
}