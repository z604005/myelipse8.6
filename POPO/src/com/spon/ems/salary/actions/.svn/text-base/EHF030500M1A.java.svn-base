package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.salary.forms.EHF030500M0F;

import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>其他補扣款
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030500M1A extends NewDispatchAction {

	public EHF030500M1A() {

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
		String EHF030500T0_U = "";
		
		try {
			if (lc_errors.isEmpty()) {
					//新增表頭資訊
					String sql = "" +
					" INSERT INTO EHF030500t0 ( EHF030500T0_U, EHF030500T0_02, EHF030500T0_03, " +
					" EHF030500T0_04, EHF030500T0_05, EHF030500T0_06, EHF030500T0_07, EHF030500T0_08, EHF030500T0_09, " +
					" EHF030500T0_10, EHF030500T0_11, EHF030500T0_12, EHF030500T0_13, EHF030500T0_14, EHF030500T0_15, " +
					" EHF030500T0_SCU, EHF030500T0_SCP, " +
					" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
					" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?, NOW(),?,?,?,?,?, 1, NOW() ) "  ;

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
					
					//使用 CodeRules 做單號取得的動作
					EHF030500T0_U = tools.createEMSUID(conn, "EMP", "EHF030500T0", "EHF030500T0_01", getLoginUser(request).getCompId());
					//= EMS_GetCodeRules.getInstance().getCodeRule("EHF030500", );
					
					Form.setEHF030500T0_U(EHF030500T0_U);
					
					p6stmt.setString(indexid, Form.getEHF030500T0_U());  //其他費用UID
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_02_UID());  //員工工號
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_03_UID());  //部門組織
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_04());  //員工類別
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_05());  //薪資年月
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_06());  //放扣款類別
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_07());  //發放類別
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_08());  //放款項目
					indexid++;
					p6stmt.setInt(indexid, Form.getEHF030500T0_09());  //放款金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_10());  //扣款項目
					indexid++;
					p6stmt.setInt(indexid, Form.getEHF030500T0_11());  //扣款金額
					indexid++;
					p6stmt.setString(indexid, "");  //入帳狀態
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030500T0_13());  //備註
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
					indexid++;
					p6stmt.setString(indexid, "");  //薪資計算UID
					indexid++;
					p6stmt.setString(indexid, "01");  //薪資計算處理狀態
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //建立人員
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
					indexid++;
					
					//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
					p6stmt.executeUpdate();
					
					sql = " SELECT LAST_INSERT_ID() AS ID FROM EHF030500t0 ";
					
					Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs=stmt.executeQuery(sql);
					if(rs.next()){
						Form.setEHF030500T0_01(rs.getInt("ID"));
						//System.out.println("ID==>"+rs.getShort("ID"));
					}
					
					rs.close();
					stmt.close();
					pstmt.close();
					p6stmt.close();			

					conn.commit();
					generateSelectBox(conn, Form, request);
					
					Map Map =this.chack(request, conn, Form.getEHF030500T0_05(),"新增");
					
					if("true".equals(Map.get("check"))){
						request.setAttribute("MSG", Map.get("MSG_01"));
						request.setAttribute("MSG_01", Map.get("MSG_02"));
					}else{
						request.setAttribute("MSG","儲存成功");
					}
					request.setAttribute("button", "edit");
					request.setAttribute("collection", "");
					// 清掉畫面上的新增
					
					
			
			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
//				return init(mapping, form, request, response);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF030500M1A.addData() " + e);
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

		return queryDatas(mapping, form, request, response);
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
	public ActionForward addDetailData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF030500M0F Form = (EHF030500M0F) form;
		
		//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("action", "addDetailData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);

		try {
			
		} catch (Exception e) {
			request.setAttribute("MSG", "新增明細失敗!");
			System.out.println("EHF030500M1A.addDetailData() " + e);
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
		

		return queryDatas(mapping, form, request, response);
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
	 * 新增明細
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addDetailDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return addDetailData(mapping, form, request, response);
	}
	
	/**
	 * 畫面導向
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward redirect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("redirect");
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
		
		//產生薪資處理狀態
		try{
			List listEHF030500T0_SCP = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030500T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("處理中");
			listEHF030500T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("已完成薪資計算");
			listEHF030500T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("已確認");
			listEHF030500T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("04");
			bform.setItem_value("已出帳");
			listEHF030500T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("05");
			bform.setItem_value("已結算");
			listEHF030500T0_SCP.add(bform);
			request.setAttribute("listEHF030500T0_SCP", listEHF030500T0_SCP);
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
		
		//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			EHF030500M0F Form = new EHF030500M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
			
			//薪資處理狀態
			Form.setEHF030500T0_SCP("01");  //處理中
			//薪資年月
			Form.setEHF030500T0_05(tools.ymTostring(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2))));
			 	
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "");
			request.setAttribute("collection", "");
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	    }
		
		return mapping.findForward("success");
	}

	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		EHF030500M0F Form = (EHF030500M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030500T0_01(Integer.parseInt(arrid[0]));  //其他費用維護序號
		}

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT EHF030500T0.* " +
				" FROM EHF030500T0 " +
				" WHERE 1=1 " +
				" AND EHF030500T0_01 = "+Form.getEHF030500T0_01()+" " +  //薪資計算序號
				" AND EHF030500T0_15 = '"+getLoginUser(request).getCompId()+"' " ;  //公司代碼
	
				sql += " ORDER BY EHF030500T0_01 DESC ";

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				//員工Map
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				//部門Map
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

				if(rs.next()){
					
					Form.setEHF030500T0_01(rs.getInt("EHF030500T0_01")); //其他費用序號
					Form.setEHF030500T0_U(rs.getString("EHF030500T0_U"));  //其他費用UID
					Form.setEHF030500T0_SCP(rs.getString("EHF030500T0_SCP"));  //薪資計算處理狀態
					Form.setEHF030500T0_02(rs.getString("EHF030500T0_02")); //員工工號
					Form.setEHF030500T0_02_TXT((String) ((Map)empMap.get(rs.getString("EHF030500T0_02"))).get("EMPLOYEE_NAME"));  //員工姓名
					Form.setEHF030500T0_03(rs.getString("EHF030500T0_03")); //部門代號
					Form.setEHF030500T0_03_TXT((String) ((Map)depMap.get(rs.getString("EHF030500T0_03"))).get("SHOW_DESC"));  //部門名稱
					Form.setEHF030500T0_04(rs.getString("EHF030500T0_04")); //員工類別
					Form.setEHF030500T0_05(rs.getString("EHF030500T0_05")); //薪資年月
					Form.setEHF030500T0_06(rs.getString("EHF030500T0_06")); //放扣款類別
					Form.setEHF030500T0_07(rs.getString("EHF030500T0_07")); //發放類別
					Form.setEHF030500T0_08(rs.getString("EHF030500T0_08")); //放款項目
					Form.setEHF030500T0_09(rs.getInt("EHF030500T0_09"));  //放款金額
					Form.setEHF030500T0_10(rs.getString("EHF030500T0_10")); //扣款項目
					Form.setEHF030500T0_11(rs.getInt("EHF030500T0_11"));  //扣款金額
					Form.setEHF030500T0_13(rs.getString("EHF030500T0_13")); //備註
					Form.setUSER_CREATE(rs.getString("USER_CREATE"));
					Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
					Form.setVERSION(rs.getInt("VERSION"));
					Form.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
					
				}
				
//				request.setAttribute("Form2Datas",list);
//				Form.setEHF030500C(list);
				
				
				Map Map =this.chack(request, conn, Form.getEHF030500T0_05(),"");
				
				if("true".equals(Map.get("check"))&&!"01".equals(rs.getString("EHF030500T0_SCP"))){
					
					FormUtils.setFormDisplayMode(request, form, "inspect");
					request.setAttribute("chack_show", "NO");
				}else{
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("chack_show", "");
				}
				
				
				
				
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("chk_type", "yes");
				
				// 清掉畫面上的新增明細欄位
				rs.close();
				stmt.close();
				hr_tools.close();
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
	 * 修改表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {	
		return saveData(mapping, form, request, response);
	}
	
	/**
	 * 儲存資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030500M0F Form = (EHF030500M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
//			BA_Vaildate ve = BA_Vaildate.getInstance();
//			if("".equals(Form.getEHF030102T0_01())){
//				request.setAttribute("MSG","其他費用序號錯誤!!請重新查詢資料!!");
//				return init(mapping, form, request, response);
//				
//			}

			if (lc_errors.isEmpty()) {
				//更新明細資料
//				int count = updateList( conn ,Form.getEHF030500C() ,"UPDATE" ,getLoginUser(request).getUserName() ,getLoginUser(request).getCompId() );		
				
				//更新表頭資料
				String sql = "" +
				" UPDATE EHF030500t0 SET " +
				" EHF030500T0_02=?, EHF030500T0_03=?, " +
				" EHF030500T0_04=?, EHF030500T0_05=?, EHF030500T0_06=?, EHF030500T0_07=?, " +
				" EHF030500T0_08=?, EHF030500T0_09=?, EHF030500T0_10=?, EHF030500T0_11=?, " +
				" EHF030500T0_13=?, " +
				" USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030500T0_01 = "+Form.getEHF030500T0_01()+" " +  //其他費用序號
				" AND EHF030500T0_15 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, Form.getEHF030500T0_02());  //員工工號
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030500T0_03());  //部門組織
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030500T0_04());  //員工類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030500T0_05());  //薪資年月
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030500T0_06());  //放扣款類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030500T0_07());  //發放類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030500T0_08());  //放款項目
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030500T0_09());  //放款金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030500T0_10());  //扣款項目
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030500T0_11());  //扣款金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030500T0_13());  //備註
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				
				
				Map Map =this.chack(request, conn, Form.getEHF030500T0_05(),"儲存");
				
				if("true".equals(Map.get("check"))){
					request.setAttribute("MSG", Map.get("MSG_01"));
					request.setAttribute("MSG_01", Map.get("MSG_02"));
				}else{
					request.setAttribute("MSG","儲存成功");
				}
				//request.setAttribute("MSG", "儲存成功!!");
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
			
			request.setAttribute("MSG","其他費用維護儲存錯誤!!請重新操作!!");
						
			System.out.println(e);
			e.printStackTrace();
			;
			return queryDatas(mapping, form, request, response);
			
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryDatas(mapping, form, request, response);
	}
	
	/**
	 * 刪除資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		Connection conn = null;
		EHF030500M0F Form = (EHF030500M0F) form;

		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
				
				String comp_id = getLoginUser(request).getCompId();
				
				if(!"".equals(Form.getEHF030500T0_01())){
					
					Statement stmt = conn.createStatement();
					String sql = " SELECT * " +
								 " FROM EHF030500T0 " +
								 " WHERE 1=1 " +
								 " AND EHF030500T0_01 = "+Form.getEHF030500T0_01()+" " +  //其他費用序號
							     " AND EHF030500T0_15 = '"+comp_id+"' " +  //公司別條件
							     " ORDER BY EHF030500T0_01 ";
					ResultSet rs = stmt.executeQuery(sql);
					
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
						request.setAttribute("MSG", "刪除失敗，資料不存在!!");
					}
					
					rs.close();
					stmt.close();
					
				}else{
					request.setAttribute("MSG", "刪除失敗，請重新操作!!");
				}
				
				
				
			} else {
				request.setAttribute("MSG", "刪除失敗，請重新操作!!");
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);

				return queryDatas(mapping, form, request, response);
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
		
		return mapping.findForward("redirect");
	}
	
	/**
	 * 搜尋該薪資年月的考勤是否確認
	 * @param request
	 * @param conn
	 */
	private Map chack(HttpServletRequest request, Connection conn,String EHF030600T0_02,String Operating_name ) {
		// TODO Auto-generated method stub
		Map Map = new HashMap();
		boolean check=false;
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);

			String sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02 " +
			" FROM EHF020900T0 " +
			" WHERE 1=1 " +
			" AND EHF020900T0_M2 = '"+EHF030600T0_02+"'"+	//薪資年月
			" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'"; //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				//有考勤資料
				if(Integer.valueOf((String)rs.getString("EHF020900T0_02"))==2){
					Map.put("check", "true");
					Map.put("MSG_01", EHF030600T0_02+"薪資已經計算，該筆"+ Operating_name +"補扣款將不列入計算");
					Map.put("MSG_02", "若要列入計算，請先至薪資計算發放裡重新計算薪資。");
				}else if(Integer.valueOf((String)rs.getString("EHF020900T0_02"))==3){
					Map.put("check", "true");
					Map.put("MSG_01", EHF030600T0_02+"薪資已經確認，該筆"+ Operating_name +"補扣款將不列入計算");
					Map.put("MSG_02", "若要列入計算，請先至薪資計算發放裡重新計算薪資。");
				}else if(Integer.valueOf((String)rs.getString("EHF020900T0_02"))==4){
					Map.put("check", "true");
					Map.put("MSG_01", EHF030600T0_02+"薪資已出帳，該筆"+ Operating_name +"補扣款將不列入計算");
					Map.put("MSG_02", "若要列入計算，請先至薪資計算發放裡重新計算薪資。");
				}else{
					Map.put("check", "false");
				}
			}
			rs.close();
			stmt.close();
		
		}catch(Exception e){
			System.out.println(" EHF030600M0F.validate() "+e);
			e.printStackTrace();
		}
		return Map;
	}
}