package com.spon.ems.vacation.actions;

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
import com.spon.ems.vacation.forms.EHF020104M0F;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>預排換班查詢作業
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020104M1A extends NewDispatchAction {
	
	//private HR_TOOLS hr_tools;

	public EHF020104M1A() {
		//hr_tools = HR_TOOLS.getInstance();
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
		EHF020104M0F Form = (EHF020104M0F) form;

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
					//新增表頭資訊
					String sql = "" +
					" INSERT INTO EHF020104t0 ( EHF020104T0_02, EHF020104T0_03, " +
					" EHF020104T0_05, EHF020104T0_06, " +
					" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
					" VALUES ( ?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
					p6stmt.setString(indexid, Form.getEHF020104T0_02());  //換班日期
					indexid++;
					p6stmt.setString(indexid, "01");  //狀態
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020104T0_05());  //備註
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //建立人員
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
					indexid++;
				
					p6stmt.executeUpdate();
					
					sql = "SELECT LAST_INSERT_ID() AS ID FROM EHF020104T0 ";
					
					Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs = stmt.executeQuery(sql);
					if(rs.next()){
						Form.setEHF020104T0_01(rs.getString("ID"));
						System.out.println("ID==>"+rs.getShort("ID"));
					}
					rs.close();
					stmt.close();
					
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
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF020104M1A.addData() " + e);
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
		EHF020104M0F Form = (EHF020104M0F) form;

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
			if (lc_errors.isEmpty()) {
					//新增表頭資訊
					String sql = "" +
					" INSERT INTO EHF020104t1 ( EHF020104T1_01, EHF020104T1_02, EHF020104T1_03, " +
					" EHF020104T1_04, EHF020104T1_05, EHF020104T1_06, " +
					" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
					" VALUES (?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) ";

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
					p6stmt.setString(indexid, Form.getEHF020104T0_01());  //預排換班序號
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020104T1_02());  //部門
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020104T1_03());  //員工
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020104T1_04());  //舊班別
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020104T1_05());  //新班別
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
					indexid++;			
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //建立人員
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
					indexid++;
					
					p6stmt.executeUpdate();
					pstmt.close();
					p6stmt.close();			

					conn.commit();
					generateSelectBox(conn, Form, request);
					
					request.setAttribute("MSG", "新增明細成功");
					request.setAttribute("button", "edit");
					request.setAttribute("collection", "");
					
					//產生預排換班處理元件
					EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
					
					//執行預排換班程式
					ems_wsc_tools.startSystem(conn, Form.getEHF020104T0_01(), Form.getEHF020104T1_03(), true, 
											  getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
					
					//更新資料庫
					conn.commit();
					
					// 清掉畫面上的新增明細欄位
					Form.setEHF020104T1_02("");
					Form.setEHF020104T1_02_TXT("");
					Form.setEHF020104T1_02_NUM("");
					Form.setEHF020104T1_03("");
					Form.setEHF020104T1_03_TXT("");
					Form.setEHF020104T1_03_NUM("");
					Form.setEHF020104T1_04("");
					Form.setEHF020104T1_05("");
			
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				generateSelectBox(conn, Form, request);
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return queryDatas(mapping, form, request, response);
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增明細失敗!");
			System.out.println("EHF020104M1A.addDetailData() " + e);
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
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020104M0F Form = (EHF020104M0F) form;
		
		//產生狀態
		try{
			List EHF020104T0_03_list = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			EHF020104T0_03_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("填寫中");
			EHF020104T0_03_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("已確認");
			EHF020104T0_03_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("已執行完成");
			EHF020104T0_03_list.add(bform);
			request.setAttribute("EHF020104T0_03_list", EHF020104T0_03_list);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生舊班別
		try{
			List EHF020104T1_04_list = new ArrayList();
			if(!"".equals(Form.getEHF020104T1_02()) && Form.getEHF020104T1_02() != null 
			   && !"".equals(Form.getEHF020104T1_03()) && Form.getEHF020104T1_03() != null ){
				EHF020104T1_04_list = tools.getSelectOptionByTable(conn, "EHF000400T0_01", 
																   "CONCAT('(',EHF000400T0_03,')',EHF000400T0_04)", "EHF000400T0",
															   	   "EHF000400T0_11", getLoginUser(request).getCompId(),
															   	   //" AND EHF000400T0_02 = '"+Form.getEHF020104T1_02()+"' " +  //部門
															   	   " ORDER BY EHF000400T0_03 ","01");
			}
			request.setAttribute("EHF020104T1_04_list", EHF020104T1_04_list);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生新班別
		/*
		try{
			List EHF010104T1_05_list = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			EHF010104T1_05_list.add(bform);
			request.setAttribute("EHF010104T1_05_list", EHF010104T1_05_list);
		}catch(Exception e) {
			System.out.println(e);
		}
		*/
		

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
			EHF020104M0F Form = new EHF020104M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);		 
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("mode", "create");
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
		EHF020104M0F Form = (EHF020104M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF020104T0_01(arrid[0]);
		}

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT EHF020104T0_01, " +
				" DATE_FORMAT(EHF020104T0_02, '%Y/%m/%d') AS EHF020104T0_02, " +
				" EHF020104T0_03, " +
				" IFNULL(DATE_FORMAT(EHF020104T0_04, '%Y/%m/%d'), '') AS EHF020104T0_04, " +
				" EHF020104T0_05, " +
				" EHF020104T0.USER_CREATE, EHF020104T0.USER_UPDATE, EHF020104T0.VERSION, EHF020104T0.DATE_UPDATE, " +
				" EHF020104T1.*, " +
				" CONCAT('(',A.EHF000400T0_03,')',A.EHF000400T0_04) AS 舊班別, " +
				" CONCAT('(',B.EHF000400T0_03,')',B.EHF000400T0_04) AS 新班別 " +
				" FROM EHF020104T0 " +
				" LEFT JOIN EHF020104T1 ON EHF020104T1_01 = EHF020104T0_01 AND EHF020104T1_06 = EHF020104T0_06 " +
				" LEFT JOIN EHF000400T0 A ON A.EHF000400T0_01 = EHF020104T1_04 AND A.EHF000400T0_11 = EHF020104T0_06 " +
				" LEFT JOIN EHF000400T0 B ON B.EHF000400T0_01 = EHF020104T1_05 AND B.EHF000400T0_11 = EHF020104T0_06 " +
				" WHERE 1=1 " +
				" AND EHF020104T0_01 = '"+Form.getEHF020104T0_01()+"' " +  //預排換班序號
				" AND EHF020104T0_06 = '"+getLoginUser(request).getCompId()+"' " ;  //公司代碼
	
				sql += " ORDER BY EHF020104T1_02, EHF020104T1_03, EHF020104T1_01 DESC ";

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				AuthorizedBean authbean = getLoginUser(request);
				//Map empMap = ems_tools.getEmpNameMap(getEmscontext(), authbean.getUserId(), authbean.getCompId());
				//Map depMap = ems_tools.getDepNameMap(getEmscontext(), authbean.getUserId(), Integer.parseInt(authbean.getUserCode()), authbean.getCompId());
				HR_TOOLS hr_tools = new HR_TOOLS();
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
				hr_tools.close();
				
				String EHF020104T0_01 = "";
				String EHF020104T0_03 = "";
				
				while(rs.next()){
					
					if("".equals(EHF020104T0_01)){
//						Form.setEHF030200T0_01(rs.getString("EHF030200T0_01")); //員工工號
//						Form.setEHF030200T0_01_TXT( (String) ((Map)empMap.get(rs.getString("EHF030200T0_01"))).get("EMPLOYEE_NAME"));  //員工姓名
//						Form.setEHF030200T0_02(rs.getString("EHF030200T0_02")); //部門代號
//						Form.setEHF030200T0_02_TXT((String) ((Map)depMap.get(rs.getString("EHF030200T0_02"))).get("SHOW_DESC"));  //部門名稱
						Form.setEHF020104T0_01(rs.getString("EHF020104T0_01")); //預排換班序號
						Form.setEHF020104T0_02(rs.getString("EHF020104T0_02")); //換班日期
						Form.setEHF020104T0_03(rs.getString("EHF020104T0_03")); //狀態
						Form.setEHF020104T0_04(rs.getString("EHF020104T0_04")); //執行日期
						Form.setEHF020104T0_05(rs.getString("EHF020104T0_05")); //備註
						Form.setUSER_CREATE(rs.getString("USER_CREATE"));
						Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
						Form.setVERSION(rs.getInt("VERSION"));
						Form.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
					}
					
					if(!"".equals(rs.getString("EHF020104T1_01")) && rs.getString("EHF020104T1_01") != null ){
						EHF020104M0F bean = new EHF020104M0F();
						bean.setEHF020104T1_01(rs.getString("EHF020104T1_01"));  //預排換班序號
						bean.setEHF020104T1_02(rs.getString("EHF020104T1_02"));  //部門代號
						bean.setEHF020104T1_02_TXT((String) ((Map)depMap.get(rs.getString("EHF020104T1_02"))).get("SHOW_DEPT_ID")
													+"/"+(String) ((Map)depMap.get(rs.getString("EHF020104T1_02"))).get("SHOW_DESC"));  //部門
						bean.setEHF020104T1_03(rs.getString("EHF020104T1_03"));  //員工工號
						bean.setEHF020104T1_03_TXT((String) ((Map)empMap.get(rs.getString("EHF020104T1_03"))).get("EMPLOYEE_ID")
													+"/"+(String) ((Map)empMap.get(rs.getString("EHF020104T1_03"))).get("EMPLOYEE_NAME"));  //員工
						bean.setEHF020104T1_04(rs.getInt("EHF020104T1_04")<0?"假日":rs.getString("舊班別"));  //舊班別
						bean.setEHF020104T1_05(rs.getInt("EHF020104T1_05")<0?"假日":rs.getString("新班別"));  //新班別
						
						if(!"".equals(bean.getEHF020104T1_01()) && bean.getEHF020104T1_01() != null ){
							list.add(bean);
						}
					}
					
					EHF020104T0_01 = rs.getString("EHF020104T0_01");
					EHF020104T0_03 = rs.getString("EHF020104T0_03");
					
					/*String EHF020104T0_02_new[]=rs.getString("EHF020104T0_02").toString().split("/");
					sql = "" +
					" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
					" FROM EHF020900T0 " +
					" WHERE 1=1 " +
					" AND EHF020900T0_M2 = '"+EHF020104T0_02_new[0]+"/"+EHF020104T0_02_new[1]+"'"+	//考勤年月
					" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
					" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

					Statement stmt_new = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs_new = stmt_new.executeQuery(sql);
					
					while(rs_new.next()){
						if("02".equals(rs_new.getString("EHF020900T0_02"))||"03".equals(rs_new.getString("EHF020900T0_02")))
							EHF020104T0_03 = "03";
						request.setAttribute("MSG", "考勤已確認，不可編輯");
						Form.setEHF020104T0_03("03"); //狀態
						
					}
					rs_new.close();
					stmt_new.close();*/	
				}
				
				Form.setEHF020104C(list);
				
				rs.close();
				stmt.close();
				
				if("01".equals(EHF020104T0_03)){
					FormUtils.setFormDisplayMode(request, form, "edit");
				}else{
					FormUtils.setFormDisplayMode(request, form, "inspect");
				}
				request.setAttribute("mode", "edit");
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
		EHF020104M0F Form = (EHF020104M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);

			if (lc_errors.isEmpty()) {
				
				//更新明細資料
//				int count = updateList( conn ,Form.getEHF010104C() ,"UPDATE" ,getLoginUser(request).getUserName() ,getLoginUser(request).getCompId() );		
				
				//更新表頭資料
				String sql = "" +
				" UPDATE EHF020104T0 SET " +
				" EHF020104T0_02=?, EHF020104T0_05=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF020104T0_01 = '"+Form.getEHF020104T0_01()+"' " +  //預排換班序號
				" AND EHF020104T0_06 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, Form.getEHF020104T0_02());  //換班日期
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020104T0_05());  //備註
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
//				request.setAttribute("MSG", "儲存成功，表頭及明細修改" + count + "筆");
				request.setAttribute("MSG", "儲存成功!!");
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
			
			request.setAttribute("MSG","預排換班資料儲存錯誤!!請重新操作!!");
						
			System.out.println(e);
			e.printStackTrace();
			
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
	 * 確認資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF020104M0F Form = (EHF020104M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);

			if (lc_errors.isEmpty()) {
				
				//更新表頭資料
				String sql = "" +
				" UPDATE EHF020104T0 SET " +
				" EHF020104T0_03=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF020104T0_01 = ? " +
				" AND EHF020104T0_06 = ? ";

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, "02");  //狀態
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020104T0_01());  //預排換班序號
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				request.setAttribute("MSG", "確認資料成功!!");
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
			
			request.setAttribute("MSG","確認資料發生錯誤!!請重新操作!!");
						
			System.out.println(e);
			e.printStackTrace();
			
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
	 * 還原資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward recovery(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF020104M0F Form = (EHF020104M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);

			if (lc_errors.isEmpty()) {
				
				//更新表頭資料
				String sql = "" +
				" UPDATE EHF020104T0 SET " +
				" EHF020104T0_03=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF020104T0_01 = ? " +
				" AND EHF020104T0_06 = ? ";

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, "01");  //狀態
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020104T0_01());  //預排換班序號
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				request.setAttribute("MSG", "還原資料成功!!");
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
			
			request.setAttribute("MSG","還原資料發生錯誤!!請重新操作!!");
						
			System.out.println(e);
			e.printStackTrace();
			
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
	 * 刪除明細資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delDetailData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		Connection conn_schedule = null;
		EHF020104M0F Form = (EHF020104M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			conn_schedule = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
				
				//刪除明細資料
				int count = updateList( conn ,Form.getEHF020104C() ,"DELETE" ,getLoginUser(request).getUserName() ,getLoginUser(request).getCompId() );
				
				if( count > 0 ){
					//conn.commit();
					//產生預排換班處理元件
					EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
					
					//執行刪除預排換班程式
					ems_wsc_tools.delSystem(conn_schedule, Form.getEHF020104C(), getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
					
					//更新資料庫
					conn.commit();
					conn_schedule.commit();
				}
				request.setAttribute("MSG", "刪除成功，共刪除" + count + "筆");
			} else {
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
				if (conn_schedule != null && !conn_schedule.isClosed()) {
					conn_schedule.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryDatas(mapping, form, request, response);
	}
	
	/**
	 * 更新明細資料
	 * @param conn
	 * @param FM030103c
	 * @param Action
	 * @param user_name
	 * @param comp_id
	 * @return
	 * @throws Exception
	 */
	private int updateList( Connection conn ,List EHF020104c ,String Action ,String user_name ,String comp_id )
		throws Exception {
		
		String insql = "" +
				" UPDATE EHF020104T1 SET " +
				" EHF020104T1_04=?, EHF020104T1_05=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF020104T1_01=? " +
				" AND EHF020104T1_02=? " +
				" AND EHF020104T1_03=? " +
				" AND EHF020104T1_06=? ";
		
		PreparedStatement pstmt = conn.prepareStatement(insql);
		P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
		Statement stmt = conn.createStatement();
		int ucount = 0;
		int dcount = 0;
		
		for (int i = 0; i < EHF020104c.size(); i++) {
			
			EHF020104M0F Form = (EHF020104M0F) EHF020104c.get(i);
			
			if (Form.isCHANGED()) {
				p6stmt.setString(1, Form.getEHF020104T1_04());  //舊班別
				p6stmt.setString(1, Form.getEHF020104T1_05());  //新班別
				p6stmt.setString(2, user_name);  //修改人員
				p6stmt.setString(3, Form.getEHF020104T1_01());  //預排換班序號
				p6stmt.setString(4, Form.getEHF020104T1_02());  //部門
				p6stmt.setString(5, Form.getEHF020104T1_03());  //員工
				p6stmt.setString(6, comp_id);  //公司代碼
				
				p6stmt.executeUpdate();
//				System.out.println(p6stmt.getQueryFromPreparedStatement());
				ucount++;
			}
			
			if (Action.equalsIgnoreCase("delete")) {
				if (Form.isCHECKED()) {
					stmt.execute(" DELETE FROM EHF020104T1 " +
						" WHERE 1=1 " +
						" AND EHF020104T1_01 = '"+Form.getEHF020104T1_01()+"' " +  //預排換班序號
						" AND EHF020104T1_02 = '"+Form.getEHF020104T1_02()+"'" +  //部門
						" AND EHF020104T1_03 = '"+Form.getEHF020104T1_03()+"'  " +  //員工
						" AND EHF020104T1_06 = '"+comp_id+"' ");
					dcount++;
					
					
				}
			}
		}
		
		stmt.close();
		pstmt.close();
		p6stmt.close();
		
		if (Action.equalsIgnoreCase("delete")) {
			return dcount;
		} else {
			return ucount;
		}
		
	}
	
	public ActionForward changeSelectBox(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020104M0F Form = (EHF020104M0F) form;

		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			AuthorizedBean authbean = getLoginUser(request);
			//Map empMap = ems_tools.getEmpNameMap(getEmscontext(), authbean.getUserId(), authbean.getCompId());
			//Map depMap = ems_tools.getDepNameMap(getEmscontext(), authbean.getUserId(), Integer.parseInt(authbean.getUserCode()), authbean.getCompId());
			HR_TOOLS hr_tools = new HR_TOOLS();
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			hr_tools.close();
			
			if(!"".equals(Form.getEHF020104T1_02()) && Form.getEHF020104T1_02() != null ){
				Form.setEHF020104T1_02_TXT((String) ((Map)depMap.get(Form.getEHF020104T1_02())).get("SHOW_DESC"));  //部門
			}
			
			if(!"".equals(Form.getEHF020104T1_03()) && Form.getEHF020104T1_03() != null ){
				Form.setEHF020104T1_03_TXT((String) ((Map)empMap.get(Form.getEHF020104T1_03())).get("EMPLOYEE_NAME"));  //員工
				
				//取得員工的目前班別設定
				AuthorizedBean emp_authbean = new AuthorizedBean();
				emp_authbean.setUserId(authbean.getUserId());
				emp_authbean.setEmployeeID(Form.getEHF020104T1_03());
				emp_authbean.setCompId(authbean.getCompId());
				//Map empClassMap = ems_tools.getEmpClass(conn, emp_authbean);
				Map empClassMap = new HashMap();
				
				Map ChangeClass= new HashMap();
				
				EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(authbean.getCompId(), "", authbean.getUserId() );
				
				
				
				
				Map WorkSchedule= new HashMap();
				
				//產生預排換班處理元件
				EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
				
				
				ems_wsc_tools.sortoutList(Form.getEHF020104T1_02(),Form.getEHF020104T1_03(),empMap,depMap);
				
				//取得排班表與行事曆
				ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), Form.getEHF020104T0_02(),"", WorkSchedule);
				
				att.setWorkSchedule(WorkSchedule);
				
				
				Map NotWorkDay=(Map) WorkSchedule.get(Form.getEHF020104T1_03());
				
				
				boolean isHoliday = true;
				int  choose=0;
				
				if(NotWorkDay==null){
					//表示要上班,但   是使用預設班別
					isHoliday = true;
					choose=1;
				}else if(NotWorkDay.get(Form.getEHF020104T0_02())==null){
					//表示要上班,但   是使用預設班別
					isHoliday = true;
					choose=2;
				}else if(NotWorkDay.get(Form.getEHF020104T0_02()).equals("-1")){
					//表示假日
					isHoliday = false;
					choose=3;
				}else{
					//表示要上班，且有指定班別
					isHoliday = true;
					choose=4;
				}
				
				
				
				
				switch(choose){
				
				case 0:
					//表示異常
					break;
					
				case 1:
					//表示要上班,但   是使用預設班別
					empClassMap = hr_tools.getEmpClassByNo( conn, authbean.getCompId(),(Integer) ((Map)empMap.get(Form.getEHF020104T1_03())).get("CLASS") );
					break;
				case 2:
					//表示要上班,但   是使用預設班別
					empClassMap = hr_tools.getEmpClassByNo( conn, authbean.getCompId(),(Integer) ((Map)empMap.get(Form.getEHF020104T1_03())).get("CLASS") );
					break;
				case 3:
					//表示放假
					ChangeClass.put("NEW_CLASS_NO", -1);
					break;
				case 4:
					//表示要上班,且有指定班別
					empClassMap = hr_tools.getEmpClassByNo( conn, authbean.getCompId(), (Integer) NotWorkDay.get(Form.getEHF020104T0_02()));
					break;
					
				}
				

				
				
				
				
				
				
				if(!empClassMap.isEmpty()){
					Form.setEHF020104T1_04((Integer)empClassMap.get("WORK_CLASS_NO")+"");  //舊班別	
				}else{
					
					if(((Integer) ChangeClass.get("NEW_CLASS_NO"))==-1){
						Form.setEHF020104T1_04("-1");  //舊班別、行事曆設定假日
					}
				}
				
				hr_tools.close();
			}
			
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
		
	    return queryDatas(mapping, form, request, response);
	}
	
	/**
	 * 手動執行預排換班功能
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward doManual(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF020104M0F Form = (EHF020104M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);

			if (lc_errors.isEmpty()) {
				
				//User AuthBean
				AuthorizedBean authbean = getLoginUser(request);
				
				//產生預排換班處理元件
				EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
				
				//執行預排換班程式
				ems_wsc_tools.startSystem(conn, Form.getEHF020104T0_01(), true, authbean.getCompId());
				
				//更新資料庫
				conn.commit();
				
				request.setAttribute("MSG", "手動執行成功!!");
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
			request.setAttribute("MSG","手動執行發生錯誤!!請重新操作!!");		
			System.out.println(e);
			e.printStackTrace();
			
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
	}*/
	
	
}
