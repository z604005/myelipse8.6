package com.spon.ems.salary.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.w3c.dom.Document;

import vtgroup.ems.common.vo.AuthorizedBean;


import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
//import com.spon.ems.history.tools.EMS_HistoryAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.ems.salary.forms.EHF030600M0F;
import com.spon.ems.salary.forms.EHF030600M1F;
import com.spon.ems.salary.forms.EHF030600M2F;
import com.spon.ems.salary.forms.EX030600R1F;
//import com.spon.ems.salary.forms.EX030600R2F;
import com.spon.ems.salary.tools.EMS_OATools;
import com.spon.ems.salary.tools.EMS_SalaryTools;
//import com.spon.ems.salary.transfer.EMS_STFORBOT;
import com.spon.utils.sc.actions.SC005A;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
//import com.spon.utils.util.EMS_GetCodeRules;
import com.spon.utils.util.HR_TOOLS;


import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>薪資計算發放
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030600M1A extends NewDispatchAction {

	public EHF030600M1A() {

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
		String EHF030600T0_U = "";
		
		try {
			if (lc_errors.isEmpty()) {
				//登入者的資訊
				AuthorizedBean authbean = getLoginUser(request);
				//取得本薪比例模式
				String salary_rate_change_mode = tools.getSysParam(conn, authbean.getCompId(), "SALARY_RATE_CHANGE_MODE");
				
					//新增表頭資訊
					String sql = "" +
					" INSERT INTO EHF030600t0 ( EHF030600T0_U, EHF030600T0_M, EHF030600T0_02, EHF030600T0_03, " +
					" EHF030600T0_04, EHF030600T0_05, EHF030600T0_06, EHF030600T0_07, EHF030600T0_08, EHF030600T0_09, " +
					" EHF030600T0_10, EHF030600T0_11, EHF030600T0_13, EHF030600T0_14, EHF030600T0_C, " +
					" EHF030600T0_AM, EHF030600T0_SCP,EHF030600T0_AFK ," +
					" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
					" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) "  ;

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
					
					//使用 CodeRules 做單號取得的動作
					EHF030600T0_U = tools.createEMSUID(conn, "EMP", "EHF030600T0", "EHF030600T0_01", getLoginUser(request).getCompId());

					Form.setEHF030600T0_U(EHF030600T0_U);
					
					p6stmt.setString(indexid, Form.getEHF030600T0_U());  //薪資計算UID
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T0_M());  //入扣帳年月
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T0_02());  //薪資年月
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T0_03());  //公司組織
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T0_04());  //發放類別
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T0_05()==null?"":Form.getEHF030600T0_05());  //天數計算類別
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T0_06()==null?"0":Form.getEHF030600T0_06()==""?"0":Form.getEHF030600T0_06());  //天數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T0_07());  //發放方式
					indexid++;
					
					/*
					if(!"ENABLE".equals(salary_rate_change_mode)){
						p6stmt.setString(indexid, "100");  //依本俸百分比 - 100%
					}else{
						p6stmt.setString(indexid, Form.getEHF030600T0_08()==null?"0":Form.getEHF030600T0_08()==""?"0":Form.getEHF030600T0_08());  //依本俸百分比
					}
					indexid++;
					*/
					
					p6stmt.setString(indexid, "100");  //依本俸百分比 - 100%
					indexid++;
					
					p6stmt.setString(indexid, Form.getEHF030600T0_09()==null?"0":Form.getEHF030600T0_09()==""?"0":Form.getEHF030600T0_09());  //依固定金額
					indexid++;
					p6stmt.setString(indexid, "0");  //本次發放金額
					indexid++;
					p6stmt.setBoolean(indexid, false);  //是否已發放
					indexid++;
//					p6stmt.setString(indexid, "");  //發放日期
//					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T0_13());  //備註
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
					indexid++;
					p6stmt.setString(indexid, "0");  //處理總筆數
					indexid++;
					p6stmt.setString(indexid, "0");  //處理總金額
					indexid++;
					p6stmt.setString(indexid, "01");  //薪資計算處理狀態
					indexid++;
					p6stmt.setString(indexid, "");  //考勤薪資紀錄UID
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //建立人員
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
					indexid++;
					
					//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
					p6stmt.executeUpdate();
					
					sql = " SELECT LAST_INSERT_ID() AS ID FROM EHF030600t0 ";
					
					Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs=stmt.executeQuery(sql);
					if(rs.next()){
						Form.setEHF030600T0_01(rs.getString("ID"));
						//System.out.println("ID==>"+rs.getShort("ID"));
					}
					
					rs.close();
					stmt.close();
					pstmt.close();
					p6stmt.close();			

					conn.commit();
					
//					generateSelectBox(conn, Form, request);
					
					request.setAttribute("MSG", "新增成功");
					request.setAttribute("button", "edit");
					request.setAttribute("collection", "");
					
					//更改EHF020900T0狀態
					this.change_EHF020900T0(request,Form);

			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF030600M1A.addData() " + e);
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
		EHF030600M0F Form = (EHF030600M0F) form;

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
			/*
			if (lc_errors.isEmpty()) {
				
					
					//新增表頭資訊
					String sql = "" +
					" INSERT INTO EHF030600t1 ( EHF030600T1_01 ,EHF030600T1_02 ,EHF030600T1_03 " +
					" ,EHF030600T1_04 ,EHF030600T1_05 ,EHF030600T1_06 ,EHF030600T1_07 ,EHF030600T1_08 " +
					" ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
					" VALUES (?,?,?,?,?,?,?,?,?,?, 1, NOW() ) "  ;

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
					p6stmt.setString(indexid, Form.getEHF030600T0_01());  //健保等級序號
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T1_02()==""?"0":Form.getEHF030600T1_02());  //健保投保等級
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T1_03()==""?"0":Form.getEHF030600T1_03());  //健保投保級距
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T1_04()==""?"0":Form.getEHF030600T1_04());  //個人負擔
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T1_05()==""?"0":Form.getEHF030600T1_05());  //雇主負擔
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T1_06()==""?"0":Form.getEHF030600T1_06());  //合計金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030600T1_07());  //備註
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
					// 清掉畫面上的新增明細欄位
					Form.setEHF030600T1_02("");

			
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				generateSelectBox(conn, Form, request);
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
//				return init(mapping, form, request, response);
				return mapping.findForward("success");
			}
			
			*/
		} catch (Exception e) {
			request.setAttribute("MSG", "新增明細失敗!");
			System.out.println("EHF030600M1A.addDetailData() " + e);
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
		
		//產生天數計算類別
		try{
			List listEHF030600T0_05 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030600T0_05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("日曆天");
			listEHF030600T0_05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("工作天");
			listEHF030600T0_05.add(bform);
			request.setAttribute("listEHF030600T0_05", listEHF030600T0_05);
		}catch(Exception e) {
			System.out.println(e);
		}
		
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
		
		//產生發放方式
		try{
			List listEHF030600T0_07 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030600T0_07.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依本薪比例");
			listEHF030600T0_07.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依固定金額");
			listEHF030600T0_07.add(bform);
			request.setAttribute("listEHF030600T0_07", listEHF030600T0_07);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生是否已發放狀態
		try{
		List datas = new ArrayList();
		BA_ALLKINDForm bform = new BA_ALLKINDForm();
		bform.setItem_id("1");
		bform.setItem_value("已發放");
		datas.add(bform);
		bform = new BA_ALLKINDForm();
		bform.setItem_id("0");
		bform.setItem_value("未發放");
		datas.add(bform);
		request.setAttribute("listTF", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生薪資處理狀態
		try{
			List listEHF030600T0_SCP = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030600T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("處理中");
			listEHF030600T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("已完成薪資計算");
			listEHF030600T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("已確認");
			listEHF030600T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("04");
			bform.setItem_value("已出帳");
			listEHF030600T0_SCP.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("05");
			bform.setItem_value("已結算");
			listEHF030600T0_SCP.add(bform);
			request.setAttribute("listEHF030600T0_SCP", listEHF030600T0_SCP);
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
			
			Map compMap = hr_tools.getCompNameMap(conn, getLoginUser(request).getUserId());
			
			//薪資處理狀態
			Form.setEHF030600T0_SCP("01");  //處理中
			//入扣帳年月
			Form.setEHF030600T0_M(tools.ymTostring(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2))));
			//薪資年月
			Form.setEHF030600T0_02(tools.ymTostring(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2))));
			//公司代碼
			Form.setEHF030600T0_03(getLoginUser(request).getCompId());
			//公司名稱
			Form.setEHF030600T0_03_TXT((String) (compMap.get("COMP_NAME_C")));
			Form.setEHF030600T0_08("0");
			Form.setEHF030600T0_09("0");
			
			//發放類別
			Form.setEHF030600T0_04("01");  //薪資
			//發放方式
			Form.setEHF030600T0_07("01");  //依本薪比例
			 	
			hr_tools.close();
			
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
		EHF030600M0F Form = (EHF030600M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030600T0_01(arrid[0]);  //薪資計算序號
		}

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			

			if (lc_errors.isEmpty()) {
				//登入者的資訊
				AuthorizedBean authbean = getLoginUser(request);
				
				ArrayList list = new ArrayList();
				
				//表頭資料查詢
				String sql = "" +
				" SELECT EHF030600T0.*, IFNULL(DATE_FORMAT(EHF030600T0_12, '%Y/%m/%d'), '') AS APPLY_DAY " +
				" FROM EHF030600T0 " +
				" WHERE 1=1 " +
				" AND EHF030600T0_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
				" AND EHF030600T0_14 = '"+authbean.getCompId()+"' " ;  //公司代碼
	
				sql += " ORDER BY EHF030600T0_01 DESC ";

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getCompId());
				
				
				// 取得部門資訊
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

				// 取得員工資訊
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());

				if(rs.next()){
					
					Form.setEHF030600T0_01(rs.getString("EHF030600T0_01")); //薪資計算序號
					Form.setEHF030600T0_U(rs.getString("EHF030600T0_U"));  //薪資計算UID
					Form.setEHF030600T0_SCP(rs.getString("EHF030600T0_SCP"));  //薪資計算處理狀態
					Form.setEHF030600T0_M(rs.getString("EHF030600T0_M")); //入扣帳年月
					Form.setEHF030600T0_02(rs.getString("EHF030600T0_02")); //薪資年月
					Form.setEHF030600T0_03((String) (compMap.get("COMP_SHOW_ID"))); //公司代號
					Form.setEHF030600T0_03_TXT((String) (compMap.get("COMP_NAME_C")));  //公司名稱
					Form.setEHF030600T0_04(rs.getString("EHF030600T0_04")); //發放類別
					Form.setEHF030600T0_05(rs.getString("EHF030600T0_05")); //天數計算類別
					Form.setEHF030600T0_06(rs.getString("EHF030600T0_06")); //天數
					Form.setEHF030600T0_07(rs.getString("EHF030600T0_07")); //發放方式
					Form.setEHF030600T0_08(rs.getString("EHF030600T0_08")); //依本俸比例
					Form.setEHF030600T0_09(rs.getString("EHF030600T0_09")); //依固定金額
					Form.setEHF030600T0_10(rs.getString("EHF030600T0_10")); //本次發放金額
//					Form.setEHF030600T0_11(rs.getString("EHF030600T0_11"));  //是否已發放
					if(rs.getBoolean("EHF030600T0_11")){
						Form.setEHF030600T0_12(rs.getString("APPLY_DAY"));  //發放日期
					}else{
						Form.setEHF030600T0_12("");  //發放日期
					}
					Form.setEHF030600T0_13(rs.getString("EHF030600T0_13")); //備註
					Form.setEHF030600T0_C(rs.getString("EHF030600T0_C")); //處理總筆數
					Form.setEHF030600T0_AM(rs.getString("EHF030600T0_AM")); //處理總金額
					Form.setUSER_CREATE(rs.getString("USER_CREATE"));
					Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
					Form.setVERSION(rs.getInt("VERSION"));
					Form.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
					
				}
				
				rs.close();
				
				//薪資計算明細資料查詢
				sql = "" +
				" SELECT EHF030600T1.*, " +
				" RPAD( REPLACE( EHF030600T1_M1, '/', '年' ), CHAR_LENGTH(EHF030600T1_M1)+1, '月' ) AS SALARY_YEAR, " +
				" LEFT(EHF030600T1_M1,INSTR('/',EHF030600T1_M1)-1) AS SALARY_CHIYEAR " +
//				" CASE EHF030600T1_SCP WHEN '01' THEN '處理中' WHEN '02' THEN '已完成薪資計算' WHEN '03' THEN '已確認' WHEN '04' THEN '已出帳' " +
//				"					   WHEN '05' THEN '已結算' END AS EHF030600T1_SCP " +
				" FROM EHF030600T1 " +
				" WHERE 1=1 " +
				" AND EHF030600T1_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
				" AND EHF030600T1_08 = '"+authbean.getCompId()+"' " ;  //公司代碼
	
				sql += " ORDER BY EHF030600T1_03, EHF030600T1_02 ";

				rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					
					EHF030600M1F bean = new EHF030600M1F();
					
					bean.setEHF030600T1_03((String) ((Map)depMap.get(rs.getString("EHF030600T1_03"))).get("SHOW_DEPT_ID")+" "+ (String) ((Map)depMap.get(rs.getString("EHF030600T1_03"))).get("SHOW_DESC")+" / "+
							(String) ((Map)empMap.get(rs.getString("EHF030600T1_02"))).get("EMPLOYEE_ID")+" "+(String) ((Map)empMap.get(rs.getString("EHF030600T1_02"))).get("EMPLOYEE_NAME"));  //部門/員工
					bean.setEHF030600T1_M1(rs.getString("SALARY_YEAR"));  //薪資年月
					bean.setEHF030600T1_22(rs.getFloat("EHF030600T1_22"));  //出勤天數
					bean.setEHF030600T1_071(rs.getInt("EHF030600T1_071"));  //應發金額
					bean.setEHF030600T1_072(rs.getInt("EHF030600T1_072"));  //應扣金額
					bean.setEHF030600T1_07(rs.getInt("EHF030600T1_07"));  //實發金額
					bean.setEHF030600T1_04(rs.getInt("EHF030600T1_04"));  //本薪金額
					bean.setEHF030600T1_04_O(rs.getInt("EHF030600T1_04_O"));  //薪資項目總金額
					bean.setEHF030600T1_20(rs.getInt("EHF030600T1_20"));  //全勤獎金
					bean.setEHF030600T1_041(rs.getInt("EHF030600T1_041"));  //請假扣薪金額
					bean.setEHF030600T1_09(rs.getInt("EHF030600T1_09"));  //津貼
					bean.setEHF030600T1_10(rs.getInt("EHF030600T1_10"));  //加班費
					bean.setEHF030600T1_13(rs.getInt("EHF030600T1_13"));  //健保費
					bean.setEHF030600T1_15(rs.getInt("EHF030600T1_15"));  //勞保費
					bean.setEHF030600T1_17(rs.getInt("EHF030600T1_17"));  //勞退自提金額
					bean.setEHF030600T1_06(rs.getInt("EHF030600T1_06"));  //薪資所得代扣
					
					bean.setEHF030600T1_01(rs.getString("EHF030600T1_01"));  //薪資計算序號
					bean.setEHF030600T1_02(rs.getString("EHF030600T1_02"));  //員工工號
					bean.setEHF030600T1_U(rs.getString("EHF030600T1_U"));  //薪資計算UID
					bean.setEHF030600T1_19(rs.getInt("EHF030600T1_19"));  //福利金
					bean.setEHF030600T1_05(rs.getInt("EHF030600T1_05"));  //其他補款
					bean.setEHF030600T1_05_D(rs.getInt("EHF030600T1_05_D"));  //其他扣款
					
					bean.setEHF030600T1_CHIYEAR(rs.getString("SALARY_CHIYEAR"));  //薪資年, 使用民國年表示
					bean.setEHF030600T1_11(rs.getInt("EHF030600T1_11"));  //差旅費
					bean.setEHF030600T1_12(rs.getInt("EHF030600T1_12"));  //不休假加班費
					bean.setEHF030600T1_14(rs.getInt("EHF030600T1_14"));  //健保公司補助額
					bean.setEHF030600T1_16(rs.getInt("EHF030600T1_16"));  //勞保公司補助額
					bean.setEHF030600T1_18(rs.getInt("EHF030600T1_18"));  //勞退公提金額  
					
					bean.setEHF030600T1_24(rs.getInt("EHF030600T1_24"));  //遲到分鐘/次數
					bean.setEHF030600T1_25(rs.getInt("EHF030600T1_25"));  //遲到扣薪金額
					bean.setEHF030600T1_26(rs.getInt("EHF030600T1_26"));  //早退分鐘/次數
					bean.setEHF030600T1_27(rs.getInt("EHF030600T1_27"));  //早退扣薪金額
					bean.setEHF030600T1_28(rs.getInt("EHF030600T1_28"));  //曠職分鐘/次數
					bean.setEHF030600T1_29(rs.getInt("EHF030600T1_29"));  //曠職扣薪金額
					
					bean.setEHF030600T1_SCP(tools.getSalaryStatus(rs.getString("EHF030600T1_SCP")));  //薪資處理狀態

					list.add(bean);
				}
				
//				request.setAttribute("Form2Datas",list);
				Form.setEHF030600C1(list);
				
				rs.close();
				stmt.close();
				hr_tools.close();
				
				
				//設定回復確認出帳的顯示狀態
				String control_recovery_transfer = tools.getSysParam(conn, authbean.getCompId(), "CONTROL_RECOVERY_TRANSFER");
				request.setAttribute("control_recovery_transfer", control_recovery_transfer);
				
				//設定本薪比例模式
				String salary_rate_change_mode = tools.getSysParam(conn, authbean.getCompId(), "SALARY_RATE_CHANGE_MODE");
				request.setAttribute("salary_rate_change_mode", salary_rate_change_mode);
				
				if("01".equals(Form.getEHF030600T0_SCP()) || "02".equals(Form.getEHF030600T0_SCP())){
					FormUtils.setFormDisplayMode(request, form, "edit");
				}else{
					FormUtils.setFormDisplayMode(request, form, "inspect");
				}
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("chk_type", "yes");
				
				// 清掉畫面上的新增明細欄位
				
				
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
	
	protected List queryListDatas( Connection conn, AuthorizedBean authbean, String salary_no ) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		List list = new ArrayList();
		try {
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = null;
				
				// 取得部門資訊
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

				// 取得員工資訊
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				
				//薪資計算明細資料查詢
				String sql = "" +
				" SELECT EHF030600T1.*, " +
				" RPAD( REPLACE( EHF030600T1_M1, '/', '年' ), CHAR_LENGTH(EHF030600T1_M1)+1, '月' ) AS SALARY_YEAR, " +
//				" CASE EHF030600T1_SCP WHEN '01' THEN '處理中' WHEN '02' THEN '已完成薪資計算' WHEN '03' THEN '已確認' WHEN '04' THEN '已出帳' " +
//				"					   WHEN '05' THEN '已結算' END AS EHF030600T1_SCP " +
				" EHF030600T0_U AS SCU " +
				" FROM EHF030600T1 " +
				" LEFT JOIN EHF030600T0 ON EHF030600T0_01 = EHF030600T1_01 AND EHF030600T0_14 = EHF030600T1_08 " +
				" WHERE 1=1 " +
				" AND EHF030600T1_01 = "+salary_no+" " +  //薪資計算序號
				" AND EHF030600T1_08 = '"+authbean.getCompId()+"' " ;  //公司代碼
	
				sql += " ORDER BY EHF030600T1_03, EHF030600T1_02 ";

				rs = stmt.executeQuery(sql);
				EHF030600M1F bean = null;
				
				while(rs.next()){
					
					bean = new EHF030600M1F();
					bean.setEHF030600T1_01(rs.getString("EHF030600T1_01"));  //薪資計算序號
					bean.setEHF030600T1_02(rs.getString("EHF030600T1_02"));  //員工工號
					bean.setEHF030600T1_U(rs.getString("EHF030600T1_U"));  //薪資計算明細UID
					bean.setEHF030600T1_03(rs.getString("EHF030600T1_03")+"/"+ (String) ((Map)depMap.get(rs.getString("EHF030600T1_03"))).get("SHOW_DESC")+" / "+
					rs.getString("EHF030600T1_02")+"/"+(String) ((Map)empMap.get(rs.getString("EHF030600T1_02"))).get("EMPLOYEE_NAME"));  //部門/員工
					bean.setUSER_CREATE((String) ((Map)empMap.get(rs.getString("EHF030600T1_02"))).get("EMPLOYEE_NAME"));  //員工姓名
					bean.setEHF030600T1_M1(rs.getString("SALARY_YEAR"));  //薪資年月
					bean.setEHF030600T1_071(rs.getInt("EHF030600T1_071"));  //應發金額
					bean.setEHF030600T1_072(rs.getInt("EHF030600T1_072"));  //應扣金額
					
					bean.setEHF030600T1_04(rs.getInt("EHF030600T1_04"));  //本薪金額
					bean.setEHF030600T1_04_DESC(rs.getString("EHF030600T1_04_DESC"));  //本薪計算記錄
					bean.setEHF030600T1_04_O(rs.getInt("EHF030600T1_04_O"));  //薪資項目總金額
					bean.setEHF030600T1_041(rs.getInt("EHF030600T1_041"));  //請假扣薪金額
					bean.setEHF030600T1_041_DESC(rs.getString("EHF030600T1_041_DESC"));  //請假扣薪記錄
					bean.setEHF030600T1_06(rs.getInt("EHF030600T1_06"));  //薪資所得代扣
					bean.setEHF030600T1_05(rs.getInt("EHF030600T1_05"));  //其他補款
					bean.setEHF030600T1_05_D(rs.getInt("EHF030600T1_05_D"));  //其他扣款
					bean.setEHF030600T1_07(rs.getInt("EHF030600T1_07"));  //實發金額
					bean.setEHF030600T1_09(rs.getInt("EHF030600T1_09"));  //津貼
					bean.setEHF030600T1_10(rs.getInt("EHF030600T1_10"));  //加班費
					bean.setEHF030600T1_11(rs.getInt("EHF030600T1_11"));  //差旅費
					bean.setEHF030600T1_12(rs.getInt("EHF030600T1_12"));  //不休假加班費
					bean.setEHF030600T1_13(rs.getInt("EHF030600T1_13"));  //健保費
					bean.setEHF030600T1_14(rs.getInt("EHF030600T1_14"));  //健保公司補助額
					bean.setEHF030600T1_15(rs.getInt("EHF030600T1_15"));  //勞保費
					bean.setEHF030600T1_16(rs.getInt("EHF030600T1_16"));  //勞保公司補助額
					bean.setEHF030600T1_17(rs.getInt("EHF030600T1_17"));  //勞退自提
					bean.setEHF030600T1_18(rs.getInt("EHF030600T1_18"));  //勞退公提
					bean.setEHF030600T1_19(rs.getInt("EHF030600T1_19"));  //福利金
					bean.setEHF030600T1_20(rs.getInt("EHF030600T1_20"));  //全勤獎金
					bean.setEHF030600T1_21(rs.getInt("EHF030600T1_21"));  //在職天數
					bean.setEHF030600T1_22(rs.getInt("EHF030600T1_22"));  //出勤天數
					bean.setEHF030600T1_23(rs.getInt("EHF030600T1_23"));  //公休天數
					
					bean.setEHF030600T1_24(rs.getInt("EHF030600T1_24"));  //遲到分鐘/次數
					bean.setEHF030600T1_25(rs.getInt("EHF030600T1_25"));  //遲到扣薪金額
					bean.setEHF030600T1_26(rs.getInt("EHF030600T1_26"));  //早退分鐘/次數
					bean.setEHF030600T1_27(rs.getInt("EHF030600T1_27"));  //早退扣薪金額
					bean.setEHF030600T1_28(rs.getInt("EHF030600T1_28"));  //曠職分鐘/次數
					bean.setEHF030600T1_29(rs.getInt("EHF030600T1_29"));  //曠職扣薪金額
					bean.setEHF030600T1_30(rs.getString("EHF030600T1_30"));  //曠職扣薪金額
					
					
					
					bean.setEHF030600T1_SCP(tools.getSalaryStatus(rs.getString("EHF030600T1_SCP")));  //薪資處理狀態
					bean.setEHF030600T1_SCU(rs.getString("SCU"));  //薪資計算UID

					list.add(bean);
				}
				
				rs.close();
				stmt.close();
				hr_tools.close();
				
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return list;
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
		EHF030600M0F Form = (EHF030600M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
//			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
//			BA_Vaildate ve = BA_Vaildate.getInstance();
//			if("".equals(Form.getEHF030102T0_01())){
//				request.setAttribute("MSG","薪資計算序號錯誤!!請重新查詢資料!!");
//				return init(mapping, form, request, response);
//				
//			}

			if (lc_errors.isEmpty()) {
				//登入者的資訊
				AuthorizedBean authbean = getLoginUser(request);
				//取得本薪比例模式
				String salary_rate_change_mode = tools.getSysParam(conn, authbean.getCompId(), "SALARY_RATE_CHANGE_MODE");
				
				//更新薪資計算明細資料
//				int count = updateList( conn ,Form.getEHF030600C1() ,"UPDATE" ,getLoginUser(request).getUserName() ,getLoginUser(request).getCompId() );		
				
				//更新表頭資料
				String sql = "" +
				" UPDATE EHF030600t0 SET " +
				" EHF030600T0_M=?, EHF030600T0_02=?, EHF030600T0_04=?, EHF030600T0_05=?, " +
				" EHF030600T0_06=?, EHF030600T0_07=?, EHF030600T0_08=?, EHF030600T0_09=?, " +
				" EHF030600T0_13=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030600T0_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
				" AND EHF030600T0_14 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, Form.getEHF030600T0_M());  //入扣帳年月
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030600T0_02());  //薪資年月
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030600T0_04());  //發放類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030600T0_05()==null?"":Form.getEHF030600T0_05());  //天數計算類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030600T0_06()==null?"0":Form.getEHF030600T0_06()==""?"0":Form.getEHF030600T0_06());  //天數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030600T0_07());  //發放方式
				indexid++;
				
				/*
				if(!"ENABLE".equals(salary_rate_change_mode)){
					p6stmt.setString(indexid, "100");  //依本俸百分比 - 100%
				}else{
					p6stmt.setString(indexid, Form.getEHF030600T0_08()==null?"0":Form.getEHF030600T0_08()==""?"0":Form.getEHF030600T0_08());  //依本俸百分比
				}
				indexid++;
				*/
				
				p6stmt.setString(indexid, "100");  //依本俸百分比 - 100%
				indexid++;
				
				p6stmt.setString(indexid, Form.getEHF030600T0_09()==null?"0":Form.getEHF030600T0_09()==""?"0":Form.getEHF030600T0_09());  //依固定金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030600T0_13());  //備註
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				request.setAttribute("MSG", "儲存成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
				//更改EHF020900T0狀態
				this.change_EHF020900T0(request,Form);
				
			} else {
				generateSelectBox(conn, Form, request);
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				request.setAttribute("Form1Datas", Form);
				//return mapping.findForward("success");
				return this.queryDatas(mapping, Form, request, response);
			}
			
		} catch (Exception e) {

			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			
			request.setAttribute("MSG","薪資計算儲存錯誤!!請重新操作!!");
						
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
	 * 刪除資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030600M0F Form = (EHF030600M0F) form;

		try {
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
				
				//取得登入者資訊
				AuthorizedBean authbean = getLoginUser(request);
				
				//EMS_OATools ems_oa_tools =  new EMS_OATools(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				//ems_oa_tools.setSta_date_type("01");  //設定日期類型
				
				
				//ems_oa_tools.delAllowanceData(conn, new String[]{});
				//ems_oa_tools.delOvertimeData(conn, new String[]{});
				//ems_oa_tools.delHaOvertimeData(conn, new String[]{});

				if(!"".equals(Form.getEHF030600T0_01())){
					
					Statement stmt = conn.createStatement();
					String sql = " SELECT * " +
								 " FROM EHF030600T0 " +
								 " WHERE 1=1 " +
								 " AND EHF030600T0_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
							     " AND EHF030600T0_14 = '"+authbean.getCompId()+"' " +  //公司別條件
							     " ORDER BY EHF030600T0_01 ";
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						
						//判斷薪資處理狀態
						if("01".equals(rs.getString("EHF030600T0_SCP")) || "02".equals("EHF030600T0_SCP") ){
							//薪資處理狀態 = '01' or '02', 才可以刪除
							
							//刪除計算明細資料
							sql = " DELETE FROM EHF030600T1 " +
							  	  " WHERE 1=1 " +
							  	  " AND EHF030600T1_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
							  	  " AND EHF030600T1_08 = '"+authbean.getCompId()+"' ";  //公司別條件
							stmt.execute(sql);	
							
							//刪除計算表頭資料
							sql = " DELETE FROM EHF030600T0 " +
								  " WHERE 1=1 " +
								  " AND EHF030600T0_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
								  " AND EHF030600T0_14 = '"+authbean.getCompId()+"' ";  //公司別條件
						
							stmt.execute(sql);
							
							
							
							//刪除薪資歷史資料
							sql = " DELETE FROM EHF030600T2 " +
								  " WHERE 1=1 " +
								  " AND EHF030600T2_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
								  " AND EHF030600T2_23 = '"+authbean.getCompId()+"' ";  //公司別條件
						
							stmt.execute(sql);
							//刪除薪資歷史薪資項目
							sql = " DELETE FROM EHF030600T3 " +
								  " WHERE 1=1 " +
								  " AND EHF030600T3_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
								  " AND EHF030600T3_07 = '"+authbean.getCompId()+"' ";  //公司別條件
						
							stmt.execute(sql);
							
							conn.commit();
							request.setAttribute("MSG", "刪除成功");
						}else{
							//薪資處理狀態 >= '03', 不可刪除
							//顯示不可刪除的錯誤訊息
							request.setAttribute("MSG", "薪資處理狀態 = "+tools.getSalaryStatus(rs.getString("EHF030600T0_SCP"))+", 資料不可刪除!!");
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
	 * 刪除明細資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
/*
	public ActionForward delDetailData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030600M0F Form = (EHF030600M0F) form;
		
//		String arrid[] = request.getParameterValues("checkId");
//    	if ((arrid==null?false:!arrid[0].equals(""))){
//    		Form.setDATA01(arrid[0]);
//		}
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			if (lc_errors.isEmpty()) {
				int count = updateList( conn ,Form.getEHF030600C() ,"DELETE" ,getLoginUser(request).getUserName() ,getLoginUser(request).getCompId() );
				
				if( count > 0 ){
					conn.commit();
				}
				
				request.setAttribute("MSG", "刪除成功，共刪除" + count + "筆");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
//				return init(mapping, form, request, response);
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
//		return init(mapping, form, request, response);
		return queryDatas(mapping, form, request, response);
	}
*/
	

	/**
	 * 計算薪資
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward countSalary(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
	
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		EHF030600M0F Form = (EHF030600M0F) form;
	
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		request.setAttribute("action", "countSalary");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		try {
			if (lc_errors.isEmpty()) {
				
				List show_ngEmpList = new ArrayList();
				Map salaryMsgMap = null;
				ArrayList ngEmpList = null;
				int ng_count = 0;
				AuthorizedBean authbean = getLoginUser(request);
				
				//測試ID
				String[] test_id = new String[]{};
				if(!"".equals(Form.getTESTID()) && Form.getTESTID() != null){
					test_id = (Form.getTESTID()).split(",");
				}
				
				//產生薪資計算元件
				/*
				EMS_SalaryTools ems_salary_tools = EMS_SalaryTools.getInstance(Form.getEHF030600T0_01(), 
												   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
												   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				/*EMS_SalaryTools ems_salary_tools = new EMS_SalaryTools(Form.getEHF030600T0_01(), 
						   											   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
						   											   authbean.getEmployeeID(), authbean.getUserCode(),
						   											   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				EMS_SalaryTools ems_salary_tools = EMS_SalaryTools.getInstance(Form.getEHF030600T0_01(), 
						   											   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
						   											   authbean.getEmployeeID(), authbean.getUserCode(),
						   											   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				
				//產生津貼,加班費,不休假加班費計算元件
				
				EMS_OATools ems_oa_tools = EMS_OATools.getInstance(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), 
					       authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				
				//EMS_OATools ems_oa_tools =  new EMS_OATools(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				ems_oa_tools.setSta_date_type("01");  //設定日期類型
				
				//執行計算迴圈
				if(test_id.length > 0){
					
					if("01".equals(Form.getEHF030600T0_04())){
						//計算津貼資料
						ems_oa_tools.executeCount(conn, 1, true, test_id);  
						//計算加班費資料
						ems_oa_tools.executeCount(conn, 2, true, test_id); 
						//計算不休假加班費資料
						ems_oa_tools.executeCount(conn, 3, true, test_id);  
					}
					
					//計算薪資
					salaryMsgMap = ems_salary_tools.count( conn, Form.getEHF030600T0_01(), Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
														   authbean.getUserName(), authbean.getCompId(),test_id);
				}else{
					if("01".equals(Form.getEHF030600T0_04())){
						//計算津貼資料
						ems_oa_tools.executeCount(conn, 1, true, test_id);  
						//計算加班費資料
						ems_oa_tools.executeCount(conn, 2, true, test_id); 
						//計算不休假加班費資料
						ems_oa_tools.executeCount(conn, 3, true, test_id); 
					}
					
					//計算薪資
					salaryMsgMap = ems_salary_tools.count( conn, Form.getEHF030600T0_01(), Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
														   authbean.getUserName(), authbean.getCompId(),test_id);
				}
				
				//更新資料庫
				conn.commit();
				
				//處理salaryMsgMap
				ngEmpList = (ArrayList) salaryMsgMap.get("NGLIST");
				Iterator it = ngEmpList.iterator();
				// 取得部門資訊
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
	
				// 取得員工資訊
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				
				
				EHF030600M2F ehf030600m2 = null;
				EHF030600M1F bean = null;
				boolean have_ng_data_flag = false;
				while(it.hasNext()){
					have_ng_data_flag = true;
					ehf030600m2 = (EHF030600M2F) it.next();
					bean = new EHF030600M1F();
					bean.setEHF030600T1_03(ehf030600m2.getEHF030600T2_03()+" "+ (String) ((Map)depMap.get(ehf030600m2.getEHF030600T2_03())).get("SHOW_DESC")+" / "+
				    ehf030600m2.getEHF030600T2_02()+" "+(String) ((Map)empMap.get(ehf030600m2.getEHF030600T2_02())).get("EMPLOYEE_NAME"));  //部門/員工
					show_ngEmpList.add(bean);
				}
				//設定前端顯示資料
				if(have_ng_data_flag){
					request.setAttribute("ng_emp_list", "open");
					request.setAttribute("Form2Datas", show_ngEmpList);
				}
				
				//未處理資料
				ng_count = (Integer) salaryMsgMap.get("NGCOUNT");
				
				if(ng_count > 0){
					request.setAttribute("MSG", Form.getEHF030600T0_02()+" 薪資計算完畢!!" + " 尚有 " + ng_count + " 位員工未列入計薪名單!!" );
				}else{
					request.setAttribute("MSG", Form.getEHF030600T0_02()+" 薪資計算完畢!!");
				}
				
				//釋放資源
				System.gc();
			
			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return queryDatas(mapping, form, request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "薪資計算失敗!");
			System.out.println("EHF030600M1A.countSalary() " + e);
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
	 * 修改&刪除明細資料
	 * @param conn
	 * @param FM030103c
	 * @param Action
	 * @param user_name
	 * @param comp_id
	 * @return
	 * @throws Exception
	 */
/*
	private int updateList( Connection conn ,List FM030103c ,String Action ,String user_name ,String comp_id )
		throws Exception {
		String insql = "" +
				" UPDATE EHF030600t1 SET " +
				" EHF030600T1_03=? ,EHF030600T1_04=? ,EHF030600T1_05=? ,EHF030600T1_06=? " +
				" ,EHF030600T1_07=? " +
				" ,USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE EHF030600T1_01=? AND EHF030600T1_02=? AND EHF030600T1_08=? ";
		
		PreparedStatement pstmt = conn.prepareStatement(insql);
		P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
		Statement stmt = conn.createStatement();
		int ucount = 0;
		int dcount = 0;
		for (int i = 0; i < FM030103c.size(); i++) {
			EHF030600M0F Form = (EHF030600M0F) FM030103c.get(i);
			if (Form.isCHANGED()) {
				p6stmt.setString(1, Form.getEHF030600T1_03());  //健保投保級距
				p6stmt.setString(2, Form.getEHF030600T1_04());  //個人負擔
				p6stmt.setString(3, Form.getEHF030600T1_05());  //雇主負擔
				p6stmt.setString(4, Form.getEHF030600T1_06());  //合計金額
				p6stmt.setString(5, Form.getEHF030600T1_07());  //備註
				p6stmt.setString(6, user_name);
				p6stmt.setString(7, Form.getEHF030600T1_01());
				p6stmt.setString(8, Form.getEHF030600T1_02());
				p6stmt.setString(9, comp_id);  //公司代碼
				
				p6stmt.executeUpdate();
//				System.out.println(p6stmt.getQueryFromPreparedStatement());
				ucount++;
			}
			if (Action.equalsIgnoreCase("delete")) {
				if (Form.isCHECKED()) {
					stmt.execute("DELETE FROM EHF030600t1 " +
						" WHERE EHF030600T1_01 = '"+Form.getEHF030600T1_01()+"' " +
						" AND EHF030600T1_02 = '"+Form.getEHF030600T1_02()+"'" +
						" AND EHF030600T1_08 = '"+comp_id+"' ");
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
*/
	/**
	 * 計算津貼資料
	 */
	public ActionForward countAllowance(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

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

		request.setAttribute("action", "countAllowance");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		try {
			if (lc_errors.isEmpty()) {
				AuthorizedBean authbean = getLoginUser(request);
				
				//測試ID
				String[] test_id = new String[1];
				if(!"".equals(Form.getTESTID()) && Form.getTESTID() != null){
					test_id = (Form.getTESTID()).split(",");
				}
				/*
				EMS_OATools ems_oa_tools = EMS_OATools.getInstance(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), 
									       authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				/*EMS_OATools ems_oa_tools =  new EMS_OATools(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), 
					       					authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				ems_oa_tools.setSta_date_type("01");  //設定日期類型
				*/
				//執行計算迴圈
				if(test_id.length > 0){
					//ems_oa_tools.executeCount(conn, 1, true, test_id);  //計算津貼資料
				}else{
					//ems_oa_tools.executeCount(conn, 1, true, null);  //計算津貼資料
				}
				
				//更新資料庫
				conn.commit();
				
				request.setAttribute("MSG", "津貼資料計算完畢!!");
			
			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "津貼資料計算失敗!");
			System.out.println("EHF030600M1A.countAllowance() " + e);
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
	 * 計算加班費資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward countOvertime(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

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

		request.setAttribute("action", "countOvertime");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		try {
			if (lc_errors.isEmpty()) {
				
				AuthorizedBean authbean = getLoginUser(request);
				
				//測試ID
				String[] test_id = new String[1];
				if(!"".equals(Form.getTESTID()) && Form.getTESTID() != null){
					test_id = (Form.getTESTID()).split(",");
				}
				
				/*
				EMS_OATools ems_oa_tools = EMS_OATools.getInstance(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), 
									       authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				/*EMS_OATools ems_oa_tools =  new EMS_OATools(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), 
       					authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				ems_oa_tools.setSta_date_type("01");  //設定日期類型
				*/
				//執行計算迴圈
				if(test_id.length > 0){
					//ems_oa_tools.executeCount(conn, 2, true, test_id);  //計算加班費資料
				}else{
					//ems_oa_tools.executeCount(conn, 2, true, null);  //計算加班費資料
				}
				
				//更新資料庫
				conn.commit();
				
				request.setAttribute("MSG", "加班費資料計算完畢!!");
			
			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "加班費資料計算失敗!");
			System.out.println("EHF030600M1A.countOvertime() " + e);
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
	 * 計算不休假加班費資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward countHaOvertime(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

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

		request.setAttribute("action", "countHaOvertime");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		try {
			if (lc_errors.isEmpty()) {
				
				AuthorizedBean authbean = getLoginUser(request);
				
				//測試ID
				String[] test_id = new String[1];
				if(!"".equals(Form.getTESTID()) && Form.getTESTID() != null){
					test_id = (Form.getTESTID()).split(",");
				}
				/*
				EMS_OATools ems_oa_tools = EMS_OATools.getInstance(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), 
									       authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				/*EMS_OATools ems_oa_tools =  new EMS_OATools(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), 
       					authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				ems_oa_tools.setSta_date_type("01");  //設定日期類型
				*/
				//執行計算迴圈
				if(test_id.length > 0){
					//ems_oa_tools.executeCount(conn, 3, true, test_id);  //計算不休假加班費資料
				}else{
					//ems_oa_tools.executeCount(conn, 3, true, null);  //計算不休假加班費資料
				}
				
				//更新資料庫
				conn.commit();
				
				request.setAttribute("MSG", "不休假加班費資料計算完畢!!");
			
			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "不休假加班費資料計算失敗!");
			System.out.println("EHF030600M1A.countHaOvertime() " + e);
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
	 * 確認薪資
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward confirmSalary(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

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

		request.setAttribute("action", "confirmSalary");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		try {
			if (lc_errors.isEmpty()) {
				AuthorizedBean authbean = getLoginUser(request);
				
				/*
				EMS_SalaryTools ems_salary_tools = EMS_SalaryTools.getInstance(Form.getEHF030600T0_01(), Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
												   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				/*EMS_SalaryTools ems_salary_tools = new EMS_SalaryTools(Form.getEHF030600T0_01(), 
												       Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
												       authbean.getEmployeeID(), authbean.getUserCode(),
												       authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				EMS_SalaryTools ems_salary_tools = EMS_SalaryTools.getInstance(Form.getEHF030600T0_01(), 
												       Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
												       authbean.getEmployeeID(), authbean.getUserCode(),
												       authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				//確認薪資
				ems_salary_tools.chgAllSalaryStatus(conn, Form.getEHF030600T0_U(), "03", 
													authbean.getUserName(), authbean.getCompId());
				
				//更新資料庫
				conn.commit();
				
				request.setAttribute("MSG", Form.getEHF030600T0_02()+" 薪資確認完畢!!");
			
			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "薪資確認失敗!");
			System.out.println("EHF030600M1A.confirmSalary() " + e);
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
	 * 回復確認薪資
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward recoverySalary(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

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

		request.setAttribute("action", "recoverySalary");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		try {
			if (lc_errors.isEmpty()) {
				AuthorizedBean authbean = getLoginUser(request);
				
				/*
				EMS_SalaryTools ems_salary_tools = EMS_SalaryTools.getInstance(Form.getEHF030600T0_01(), 
												   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
												   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				/*EMS_SalaryTools ems_salary_tools = new EMS_SalaryTools(Form.getEHF030600T0_01(), 
						   							   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
						   							   authbean.getEmployeeID(), authbean.getUserCode(),
						   							   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				EMS_SalaryTools ems_salary_tools = EMS_SalaryTools.getInstance(Form.getEHF030600T0_01(), 
						   							   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
						   							   authbean.getEmployeeID(), authbean.getUserCode(),
						   							   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				
				//薪資狀態回復  修改於20140213  修改人員賴泓錡
				if("04".equals(Form.getEHF030600T0_SCP())){//確認出帳
					//回復確認出帳
					ems_salary_tools.chgAllSalaryStatus(conn, Form.getEHF030600T0_U(), "03", authbean.getUserName(), authbean.getCompId());
					//將薪資歷史資料刪除。 20140714 by Hedwig
					//EMS_HistoryAction history = new EMS_HistoryAction();
					//history.insertSalaryHistory(conn, Integer.parseInt(Form.getEHF030600T0_02().replace("/", "")), false, authbean.getCompId());
				}else if("03".equals(Form.getEHF030600T0_SCP())){//確認薪資
					//回復確認薪資
					ems_salary_tools.chgAllSalaryStatus(conn, Form.getEHF030600T0_U(), "02", authbean.getUserName(), authbean.getCompId());
				}else {
					
					ems_salary_tools.chgAllSalaryStatus(conn, Form.getEHF030600T0_U(), "-1", authbean.getUserName(), authbean.getCompId());
				}
				//寫入未發放flag
				this.ctrlGrant(conn, false, Form.getEHF030600T0_01(), authbean.getUserName(), authbean.getCompId());
				
				//更新資料庫
				conn.commit();
				
				this.chgSafety(conn,Form,authbean,0);//修改保險設定檔
				
				request.setAttribute("MSG", Form.getEHF030600T0_02()+" 回復完畢!!");
			
			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "回復確認失敗!");
			System.out.println("EHF030600M1A.recoverySalary() " + e);
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
	 * 確認出帳
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward confirmTransfer(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

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

		request.setAttribute("action", "confirmTransfer");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		try {
			if (lc_errors.isEmpty()) {
				AuthorizedBean authbean = getLoginUser(request);
				
				/*
				EMS_SalaryTools ems_salary_tools = EMS_SalaryTools.getInstance(Form.getEHF030600T0_01(), 
												   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
												   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				/*EMS_SalaryTools ems_salary_tools = new EMS_SalaryTools(Form.getEHF030600T0_01(), 
						   							   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
						   							   authbean.getEmployeeID(), authbean.getUserCode(),
						   							   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				*/
				EMS_SalaryTools ems_salary_tools = EMS_SalaryTools.getInstance(Form.getEHF030600T0_01(), 
						   							   Form.getEHF030600T0_02(), Form.getEHF030600T0_M(),
						   							   authbean.getEmployeeID(), authbean.getUserCode(),
						   							   authbean.getUserId(), authbean.getUserName(), authbean.getCompId());
				//確認出帳
				ems_salary_tools.chgAllSalaryStatus(conn, Form.getEHF030600T0_U(), "04", 
													authbean.getUserName(), authbean.getCompId());
				
				//寫入已發放flag
				this.ctrlGrant(conn, true, Form.getEHF030600T0_01(), authbean.getUserName(), authbean.getCompId());
				//寫入薪資發放日期
				this.writeGrantDate(conn, Form.getEHF030600T0_01(), authbean.getUserName(), authbean.getCompId());
				//將薪資資料寫入歷史資料庫。 20140714 by Hedwig
				//EMS_HistoryAction history = new EMS_HistoryAction();
				
				
				//先刪除之前有寫入歷史區的資料  20150427 by Alvin
				//history.insertSalaryHistory(conn, Integer.parseInt(Form.getEHF030600T0_02().replace("/", "")), false, authbean.getCompId());
				//conn.commit();
				//history.insertSalaryHistory(conn, Integer.parseInt(Form.getEHF030600T0_02().replace("/", "")), true, authbean.getCompId());
				//更新資料庫
				conn.commit();
				
				request.setAttribute("MSG", Form.getEHF030600T0_02()+" 出帳確認完畢!!");
				this.chgSafety(conn,Form,authbean,1);//修改保險設定檔
			
			} else {
				
				request.setAttribute("chk_type", "yes");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "出帳確認失敗!");
			System.out.println("EHF030600M1A.confirmTransfer() " + e);
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
	 * 修改勞保、健保等級資料狀態
	 * @param conn
	 * @param Form
	 * @param authbean
	 */
	private void chgSafety(Connection conn, EHF030600M0F Form,AuthorizedBean authbean,int chk) {
		// TODO Auto-generated method stub
		BaseFunction base_tools = new BaseFunction(authbean.getCompId());
		int number_Labor_INT=0;
		int number_Health_INT=0;
		List sql_list = new ArrayList();
		String number_Labor="";
		String number_Health="";
		String sql = "";
		try {
		//薪資計算明細資料查詢
		sql = "" +
			" SELECT  EHF030600T1_13_VERSION, EHF030600T1_17_VERSION" +
			" FROM EHF030600T1 " +
			" WHERE 1=1 " +
			" AND EHF030600T1_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
			" AND EHF030600T1_08 = '"+authbean.getCompId()+"' " ;  //公司代碼

			sql += " ORDER BY EHF030600T1_03, EHF030600T1_02 ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				number_Labor_INT=0;
				if(number_Labor_INT!=Integer.valueOf("".equals(rs.getString("EHF030600T1_13_VERSION"))?"0":rs.getString("EHF030600T1_13_VERSION"))){
					number_Labor_INT=Integer.valueOf(rs.getString("EHF030600T1_13_VERSION"));
					number_Labor+=rs.getString("EHF030600T1_13_VERSION")+",";
				}
				number_Health_INT=0;
				if(number_Health_INT!=Integer.valueOf("".equals(rs.getString("EHF030600T1_17_VERSION"))?"0":rs.getString("EHF030600T1_17_VERSION"))){
					number_Health_INT=Integer.valueOf(rs.getString("EHF030600T1_17_VERSION"));
					number_Health+=rs.getString("EHF030600T1_17_VERSION")+",";
				}
			}
			rs.close();
			stmt.close();
			
			
			String []Labor_number=number_Labor.split(",");
			String []Health_number=number_Health.split(",");
			String Labor="";
			String Health="";
			
			
			for(int i=0;i<Labor_number.length;i++){
				Labor+="'"+Labor_number[i]+"'";
				if(i!=Labor_number.length-1){
					Labor+=",";
				}
			}
			
			sql = "" +
			" UPDATE EHF030103T0 SET EHF030103T0_06="+chk+", " +
			" USER_UPDATE='"+authbean.getEmployeeID()+"'"+", " +
			" VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE EHF030103T0_01 IN ("+Labor+")" +
			" AND EHF030103T0_05 = '"+authbean.getCompId()+"' ";
			sql_list.add(sql);
			
			
			sql = "" +
			" UPDATE EHF030104T0 SET EHF030104T0_06="+chk+", " +
			" USER_UPDATE='"+authbean.getUserName()+"'"+", " +
			" VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE EHF030104T0_01 IN ("+Labor+")" +
			" AND EHF030104T0_05 = '"+authbean.getCompId()+"' ";
			sql_list.add(sql);
			
			base_tools.executeBatchSQL(sql_list);
			base_tools.commit();
			base_tools.close();

		} catch (Exception e) {	
			System.out.println(sql);
			System.out.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * 設定是否已發放
	 * @param conn
	 * @param ctrl_grant_flag
	 * @param salary_no
	 * @param user_name
	 * @param comp_id
	 */
	private void ctrlGrant( Connection conn, boolean ctrl_grant_flag, String salary_no, String user_name, String comp_id ) {
		
		try {

			//更新表頭資料
			String sql = "" +
			" UPDATE EHF030600t0 SET " +
			" EHF030600T0_11=?, " +
			" USER_UPDATE=?, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030600T0_01 = "+salary_no+" " +  //薪資計算序號
			" AND EHF030600T0_14 = '"+comp_id+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setBoolean(indexid, ctrl_grant_flag);  //修改人員
			indexid++;
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
		} catch (Exception e) {			
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 寫入薪資發放日期
	 * @param conn
	 * @param salary_no
	 * @param user_name
	 * @param comp_id
	 */
	private void writeGrantDate( Connection conn, String salary_no, String user_name, String comp_id ) {
		
		try {

			//更新表頭資料
			String sql = "" +
			" UPDATE EHF030600t0 SET " +
			" EHF030600T0_12=NOW(), " +
			" USER_UPDATE=?,  DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030600T0_01 = "+salary_no+" " +  //薪資計算序號
			" AND EHF030600T0_14 = '"+comp_id+"' ";  //公司代碼

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, user_name);  //修改人員
			indexid++;
			
			//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
		} catch (Exception e) {			
			System.out.println(e);
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 列印薪津清冊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward printSalary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		
		
		EHF030600M0F Form = (EHF030600M0F) form;
		Connection conn = null;
		ActionErrors lc_errors = new ActionErrors();
    	
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
			AuthorizedBean authbean = getLoginUser(request); 
			
//			產生Excel元件
			
			try{
				
				int DataCount = 0;
				//取得薪資計算明細資料
				List printlist = this.queryListDatas(conn, authbean, Form.getEHF030600T0_01());
				//取得公司名稱
				Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getCompId());
				
				hr_tools.close();
				
				EX030600R1F ef = new EX030600R1F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request,Form,authbean.getCompId(),printlist);
				
				//列印報表表頭
				ef.printHeadValue(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), tools.ymdTostring(tools.getSysDate())+"",   (String)compMap.get("COMP_NAME_C"), "薪    津    清    冊");
				//列印報表
				DataCount = ef.print(conn, printlist, Form.getEHF030600T0_U(), authbean.getCompId());
				
				//傳入前端需要的檔名
				String name ="薪津清冊.xls";
				String FileName="";
				
				if (DataCount > 0){//表示有資料
					
					//存入檔案
					FileName=ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
					return this.queryDatas(mapping, Form, request, response);
	
				}else{
					saveErrors(request, lc_errors);
					request.setAttribute("MSG","沒有資料可列印!!");
				}
			}catch(Exception E){
				E.printStackTrace();
				request.setAttribute("MSG","列印失敗!!");
			}finally{
//				ef.write();
			}
			
		}catch (Exception E) {
			E.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return this.queryDatas(mapping, Form, request, response);
	}
	
	/**
	 * 列印銀行轉帳清單
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
//	public ActionForward printTransferList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
//		
//		BA_TOOLS tools = BA_TOOLS.getInstance();
//		EHF030600M0F Form = (EHF030600M0F) form;
//		Connection conn = null;
//		ActionErrors lc_errors = new ActionErrors();
//    	
//    	//建立資料庫連線
//    	if (conn == null) {
//    		try {
//    			conn = tools.getConnection("SPOS");
//    		} catch (SQLException e2) {
//    			e2.printStackTrace();
//    		} catch (Exception e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
//		}
//
//		try {
//			AuthorizedBean authbean = getLoginUser(request);
//			
//			//產生Excel元件
//			EX030600R2F ef = new EX030600R2F(conn,getServlet().getServletContext().getRealPath(""),authbean.getEmployeeID(),request);
//			
//			try{
//				
//				int DataCount = 0;
//				String salYYMM = Form.getEHF030600T0_02().replaceAll("/", "月").trim().concat("月");  //薪資年月
//				
//				//取得薪資計算明細資料
//				List printlist = this.queryListDatas(conn, authbean, Form.getEHF030600T0_01());
//				//取得公司名稱
//				Map compMap = (Map)ems_tools.getCompNameMap(authbean.getUserId()).get(authbean.getCompId());
//				//列印報表表頭
//				ef.printHeadValue( (String)compMap.get("COMP_NAME_C"),
//									salYYMM+" 薪資轉帳清單",
//									tools.ymdTostring(tools.getSysDate())+"");
//				//列印報表
//				DataCount = ef.print(conn, printlist, authbean.getCompId());
//				
//				if (DataCount > 0){//表示有資料
//					
//					//存入檔案
//					String FileName	= ef.write();
//					request.setAttribute("FileName", FileName);
//					//多加一個每日更新的參數，讓報表下載時不會有重複的檔案名稱。 20140720 by Hedwig
//					String number = new SC005A().getSequence(conn, "EX030600R2F", "EX030600R2F", "0", getLoginUser(request).getCompId());
////					String name ="銀行轉帳清單"+ems_tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";
//					String name ="銀行轉帳清單"+tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+"("+number+").xls";
//					request.setAttribute("DisplayFileName",  new String(name.getBytes("BIG5"), "iso8859-1"));
//					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"'></iframe>");
//					request.setAttribute("MSG","請點選下載檔案，下載報表檔案!!");
//	
//				}else{
//					saveErrors(request, lc_errors);
//					request.setAttribute("MSG","沒有資料可列印!!");
//				}
//			}catch(Exception E){
//				E.printStackTrace();
//				request.setAttribute("MSG","列印失敗!!");
//			}finally{
////				ef.write();
//			}
//			
//		}catch (Exception E) {
//			E.printStackTrace();
//		} finally {
//			try {
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
//			} catch (Exception e) {
//			}
//		}
//		
//		return this.queryDatas(mapping, Form, request, response);
//	}
	
	/**
	 * 產生薪資媒體轉帳檔
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
//	public ActionForward generateTransferFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
//		
//		BA_TOOLS tools = BA_TOOLS.getInstance();
//		
//		EHF030600M0F Form = (EHF030600M0F) form;
//		Connection conn = null;
//		ActionErrors lc_errors = new ActionErrors();
//    	
//    	//建立資料庫連線
//    	if (conn == null) {
//    		try {
//    			conn = tools.getConnection("SPOS");
//    		} catch (SQLException e2) {
//    			e2.printStackTrace();
//    		} catch (Exception e) {
//    			// TODO Auto-generated catch block
//    			e.printStackTrace();
//    		}
//		}
//    	
//    	//檢核登錄日期、轉帳日期格式
//    	try{
//    		//建立驗證元件
//    		BA_Vaildate ve = BA_Vaildate.getInstance();
//    		ve.isADDate(lc_errors, Form.getLOGIN_DATE(), "LOGIN_DATE", "yyyy/MM/dd 日期格式不正確!!");
//    		ve.isADDate(lc_errors, Form.getDO_TRANSFER_DATE(), "DO_TRANSFER_DATE", "yyyy/MM/dd 日期格式不正確!!");
//    		
//    		if(!lc_errors.isEmpty()){
//    			saveErrors(request, lc_errors);
//				request.setAttribute("save_status", "error");
//				request.setAttribute("stf_open_type", "on");
//				return this.queryDatas(mapping, Form, request, response);
//    		}
//    		
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//
//		try {
//			AuthorizedBean authbean = getLoginUser(request);
//			//轉帳資料清單
//			List datalist = new ArrayList();
//			
//			//建立薪資媒體轉帳檔元件 For 台灣銀行
//			EMS_STFORBOT ems_stforbot = new EMS_STFORBOT();
//			
//			//取得SalaryTransferInformationXML
//			//取得XML檔案路徑
//			ServletContext servletContext = this.getServlet().getServletContext(); 
//			String rootPath = servletContext.getRealPath("/");
//			String path = rootPath+"/ems/resource/SalaryTransferInformation.xml";
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			
//			File stxmlfile = new File(path);
//			Document doc = db.parse(stxmlfile);  //使用DOM PARSE轉換XML檔案
//			
//			//XML檔案走訪
//			String back_code = doc.getElementsByTagName("bankcode").item(0).getFirstChild().getNodeValue();
//			String uniform_number = doc.getElementsByTagName("uniformnumber").item(0).getFirstChild().getNodeValue();
//			String customer_code = doc.getElementsByTagName("customercode").item(0).getFirstChild().getNodeValue();
//			
//			//處理登錄日期、轉帳日期格式
//			String login_date = Form.getLOGIN_DATE();
//			String do_transfer_date = Form.getDO_TRANSFER_DATE();
//			
//			//轉換成民國年
//			login_date = tools.ADtoCHI(login_date);
//			do_transfer_date = tools.ADtoCHI(do_transfer_date);
//			
//			//設定headMap
//			Map headMap = new HashMap();
//			//取得登錄日期, 轉帳日期
//			headMap.put("LOGIN_DATE", login_date.replaceAll("/", "").replaceAll("-", "").trim().substring(1));
//			headMap.put("DO_TRANSFER_DATE", do_transfer_date.replaceAll("/", "").replaceAll("-", "").trim().substring(1));
//			//取得薪資轉帳檔資訊
//			headMap.put("BANK_CODE", back_code);
//			headMap.put("UNIFORM_NUMBER", uniform_number);
//			headMap.put("CUSTOMER_CODE", customer_code);
//			headMap.put("TOTAL_MONEY", Long.parseLong(Form.getEHF030600T0_AM()));
//			
//			
//			//薪資計算明細資料查詢
//			String sql = "" +
//			" SELECT " +
//			" EHF030200T0_06_AC AS EMPLOYEE_ACCOUNT, " +
//			" EHF030600T1_07 AS TRANSFER_MONEY " +
//			" FROM EHF030600T1 " +
//			" LEFT JOIN EHF050307T0 ON EHF030200T0_01 = EHF030600T1_02 AND EHF030200T0_13 = EHF030600T1_08 " +
//			" WHERE 1=1 " +
//			" AND EHF030600T1_01 = "+Form.getEHF030600T0_01()+" " +  //薪資計算序號
//			" AND EHF030200T0_05 = '02' " +  //使用銀行轉帳
//			" AND EHF030600T1_08 = '"+authbean.getCompId()+"' " ;  //公司代碼
//
//			sql += " ORDER BY EHF030600T1_03, EHF030600T1_02 ";
//
//			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			ResultSet rs = stmt.executeQuery(sql);
//			Map tempMap = null;
//			int count = 0;
//			while(rs.next()){
//				tempMap = new HashMap();
//				tempMap.put("EMPLOYEE_ACCOUNT", rs.getString("EMPLOYEE_ACCOUNT").replaceAll("-", "").trim());
//				tempMap.put("TRANSFER_MONEY", rs.getString("TRANSFER_MONEY"));
//				count++;
//				datalist.add(tempMap);
//			}
//			
//			headMap.put("TOTAL_COUNT", Long.valueOf(count));
//			
//			
//			try{
//				//產生薪資媒體檔
//				Map fileMap = ems_stforbot.generateTransferFile(conn, headMap, datalist, authbean.getCompId());
//				
//				String filename = (String) fileMap.get("FILENAME");
//				
//				if (!"".equals(filename) && filename!=null ){//表示有資料
//					
//					//存入檔案
//					request.setAttribute("FileName", filename);
//					request.setAttribute("DisplayFileName", (String) fileMap.get("DISPLAYFILENAME"));
//					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 " +
//					"src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+filename+"'></iframe>");
//					request.setAttribute("MSG","請點選下載檔案，下載薪資媒體轉帳檔案!!");
//	
//				}else{
//					saveErrors(request, lc_errors);
//					request.setAttribute("MSG","沒有資料可產生!!");
//				}
//			}catch(Exception E){
//				E.printStackTrace();
//				request.setAttribute("MSG","產生薪資媒體轉帳檔失敗!!");
//			}finally{
//				
//			}
//			rs.close();
//			stmt.close();
//		}catch (Exception E) {
//			E.printStackTrace();
//		} finally {
//			try {
//				if (conn != null && !conn.isClosed()) {
//					conn.close();
//				}
//			} catch (Exception e) {
//			}
//		}
//		
//		return this.queryDatas(mapping, Form, request, response);
//	}
	
	/**
	 * 當新增薪資計算時
	 * 更改EHF020900T0(考勤確認)狀態
	 * @param request
	 * @param conn
	 * @param Form 
	 */
	private void change_EHF020900T0(HttpServletRequest request, EHF030600M0F Form) {
		// TODO Auto-generated method stub
		
		String comp_id = getLoginUser(request).getCompId();
		BaseFunction base_tools = new BaseFunction(comp_id);
		try{

			String sql = ""
				+ " UPDATE ehf020900t0 SET"
				+ " EHF020900T0_03=? ,EHF020900T0_M=? ,EHF020900T0_M1=?,EHF020900T0_03FK=?," 
				+ " USER_UPDATE=?,DATE_UPDATE=NOW(),VERSION=VERSION+1" 
				+ " WHERE 1=1 " 
				+ " AND EHF020900T0_04 = '"+comp_id+"'" 
				+ " AND EHF020900T0_M2 = '"+Form.getEHF030600T0_02()+"'";
			// 執行SQL
			PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, "01"); //EHF020900T0_03
			indexid++;
			p6stmt.setString(indexid, Form.getEHF030600T0_M()); //EHF020900T0_M
			indexid++;
			p6stmt.setString(indexid, Form.getEHF030600T0_02()); //EHF020900T0_M1
			indexid++;
			p6stmt.setString(indexid, Form.getEHF030600T0_U()); //EHF020900T0_03FK
			indexid++;
			p6stmt.setString(indexid, getLoginUser(request).getUserName()); //USER_UPDATE
			indexid++;		
			//System.out.println("sql ==>"+ p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			base_tools.commit();
			base_tools.close();
			
		} catch (Exception e) {
			System.out.println(" 修改EHF020900T0錯誤!! ");
			e.printStackTrace();
		}
	}	
}