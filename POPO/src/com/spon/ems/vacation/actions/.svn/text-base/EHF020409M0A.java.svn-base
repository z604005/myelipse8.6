package com.spon.ems.vacation.actions;

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
import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.ems.vacation.forms.EHF020409M0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>遲到早退曠職設定
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020409M0A extends NewDispatchAction {

	public EHF020409M0A() {

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
		EHF020409M0F Form = (EHF020409M0F) form;
		
		//建立資料庫連線
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
					String sql = "" +
					" INSERT INTO EHF020409T0 " +
					" ( " +
					" EHF020409T0_02, EHF020409T0_03, " +
					" EHF020409T0_04, EHF020409T0_05, EHF020409T0_06, EHF020409T0_07, " +
					" EHF020409T0_08, EHF020409T0_09, " +
					" EHF020409T0_10, EHF020409T0_11, EHF020409T0_12, EHF020409T0_13, " +
					" EHF020409T0_14, EHF020409T0_15, " +
					" EHF020409T0_16, EHF020409T0_17, EHF020409T0_18, " +
					" EHF020409T0_19, EHF020409T0_20, " +
					" EHF020409T0_21, EHF020409T0_22, EHF020409T0_23, " +
					" EHF020409T0_24,EHF020409T0_25, " +
					" EHF020409T0_26,EHF020409T0_27,EHF020409T0_28,EHF020409T0_29,EHF020409T0_30,EHF020409T0_31," +
					" EHF020409T0_32,EHF020409T0_33,EHF020409T0_34," +
					" EHF020409T0_35,EHF020409T0_36,EHF020409T0_37," +
					
					" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE" +
					" ) " +
					" VALUES ( " +
					" ?, ?, " +
					" ?, ?, ?, ?, " +
					" ?, ?, " +
					" ?, ?, ?, ?, " +
					" ?, ?, " +
					" ?, ?, ?, " +
					" ?, ?, " +
					" ?, ?, ?, " +
					" ?, ?, " +
					" ?, ?, ?, ?, ?," +
					" ?, ?, ?, ?," +
					" ?, ?, ?, " +
					
					" ?, ?, 1, NOW(), NOW() " +
					" ) " ;

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
					p6stmt.setString(indexid,Form.getEHF020409T0_02());  //規則名稱
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_03())); //是否啟用
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_04());  //遲到判斷條件類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_05()==null?"0":Form.getEHF020409T0_05()==""?"0":Form.getEHF020409T0_05());  //遲到分數/次數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_06()==null?"0":Form.getEHF020409T0_06()==""?"0":Form.getEHF020409T0_06());  //遲到扣薪單位
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_07());  //遲到扣薪類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_08()==null?"0":Form.getEHF020409T0_08()==""?"0":Form.getEHF020409T0_08());  //遲到扣薪倍數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_09()==null?"0":Form.getEHF020409T0_09()==""?"0":Form.getEHF020409T0_09());  //遲到扣薪金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_10());  //早退判斷條件類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_11()==null?"0":Form.getEHF020409T0_11()==""?"0":Form.getEHF020409T0_11());  //早退分數/次數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_12()==null?"0":Form.getEHF020409T0_12()==""?"0":Form.getEHF020409T0_12());  //早退扣薪單位
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_13());  //早退扣薪類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_14()==null?"0":Form.getEHF020409T0_14()==""?"0":Form.getEHF020409T0_14());  //早退扣薪倍數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_15()==null?"0":Form.getEHF020409T0_15()==""?"0":Form.getEHF020409T0_15());  //早退扣薪金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_16());  //早退判斷條件類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_17()==null?"0":Form.getEHF020409T0_17()==""?"0":Form.getEHF020409T0_17());  //曠職判斷時數條件
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_18()==null?"0":Form.getEHF020409T0_18()==""?"0":Form.getEHF020409T0_18());  //曠職扣薪單位(小時/次數)
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_19());  //曠職扣薪類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_20()==null?"0":Form.getEHF020409T0_20()==""?"0":Form.getEHF020409T0_20());  //曠職扣薪倍數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_21()==null?"0":Form.getEHF020409T0_21()==""?"0":Form.getEHF020409T0_21());  //曠職扣薪金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_22()==null?"":Form.getEHF020409T0_22());  //備註
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_24())); //是否記錄下班與加班上班刷卡
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_25())); //是否補足遲到時數
					indexid++;
					
					
					
					
					p6stmt.setString(indexid, Form.getEHF020409T0_26()==null?"0":Form.getEHF020409T0_26()==""?"0":Form.getEHF020409T0_26());  //遲到扣薪倍數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_27()==null?"0":Form.getEHF020409T0_27()==""?"0":Form.getEHF020409T0_27());  //遲到扣薪金額
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_28())); //是否啟用
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_29()==null?"0":Form.getEHF020409T0_29()==""?"0":Form.getEHF020409T0_29());  //遲到扣薪金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_30()==null?"0":Form.getEHF020409T0_30()==""?"0":Form.getEHF020409T0_30());  //遲到扣薪倍數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_31()==null?"0":Form.getEHF020409T0_31()==""?"0":Form.getEHF020409T0_31());  //遲到扣薪金額
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_32())); //是否啟用
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_33()==null?"0":Form.getEHF020409T0_33()==""?"0":Form.getEHF020409T0_33());  //遲到扣薪金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_34()==null?"0":Form.getEHF020409T0_34()==""?"0":Form.getEHF020409T0_34());  //遲到扣薪金額
					indexid++;
					
					p6stmt.setString(indexid, Form.getEHF020409T0_35()==null?"0":Form.getEHF020409T0_35()==""?"0":Form.getEHF020409T0_35());  //遲到扣薪金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_36()==null?"0":Form.getEHF020409T0_36()==""?"0":Form.getEHF020409T0_36());  //遲到扣薪金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF020409T0_37()==null?"0":Form.getEHF020409T0_37()==""?"0":Form.getEHF020409T0_37());  //遲到扣薪金額
					indexid++;
					
					
					
					
					
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //使用員工姓名
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());
					indexid++;
				
					//System.out.println("insert sql ==>"+p6stmt.getQueryFromPreparedStatement());
					
					p6stmt.executeUpdate();
					pstmt.close();
					p6stmt.close();
				
				
				//Query auto id
				Statement stmt = conn.createStatement();
				String chksql = "" +
				" SELECT Last_Insert_Id() AS ID " ;
				ResultSet rs = stmt.executeQuery(chksql);
				String query_table_id = "";
				
				if(rs.next()){
					query_table_id = rs.getString("ID");
				}
				
				rs.close();
				stmt.close();
				
				conn.commit();
				
				//Set Query id to form
				Form.setEHF020409T0_01(query_table_id);
				
				//generateSelectBox(conn, Form, request);
				
				//request.setAttribute("MSG", "新增成功");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				// 清掉畫面上的新增
				
			} else {
				
				//處理新增錯誤
				request.setAttribute("button", "init");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				if (request.getAttribute("Form1Datas") != null) {
					form = (EHF020409M0F) request.getAttribute("Form1Datas");
				}
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));

				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF020409M0A.addData() " + e);
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

		return this.queryDatas(mapping, Form, request, response);
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
		
		//產生 radio選單 --> 是,否
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("是");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0");
			bform.setItem_value("否");
			datas.add(bform);
			request.setAttribute("listTF", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生 遲到/早退單位選單
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依每次分鐘條件");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依累計分鐘條件");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("依次數條件");
			datas.add(bform);
			request.setAttribute("listUNIT", datas);
		}catch(Exception e) {
			System.out.println(e);
		}

		//產生 扣薪類型選單(遲到早退共用)
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依時薪比例");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依固定金額");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("依區段條件");
			datas.add(bform);
			request.setAttribute("listDe", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		
		
		
		//產生 曠職單位選單
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依每次小時條件");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依累計小時條件");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("依次數條件");
			datas.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("04");
			bform.setItem_value("依天數條件");
			datas.add(bform);*/
			request.setAttribute("listAbsenteeism", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生 曠職單位選單
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依時薪比例");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依固定金額");
			datas.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("依倍率");
			datas.add(bform);*/
			
			
			request.setAttribute("listAbsenteeism1", datas);
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
		DataSource ds = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020409M0F Form = (EHF020409M0F) form;
		
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
			String sql = "";
			
			sql = "" +
			" SELECT " +
			" EHF020409T0_01 " +
			" FROM EHF020409T0 " +
			" WHERE 1=1 " +
			" AND EHF020409T0_23 = '"+getLoginUser(request).getCompId()+"' " +  //公司代碼
			" LIMIT 1 ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			String type = "";
			
			if(rs.next()){
				//有資料 -> Query
				type = "01";
				Form.setEHF020409T0_01(rs.getString("EHF020409T0_01"));  //ID
				request.setAttribute("ems_system_work_mode", "SYSTEM_SALARY_MODE");//前端控制顯示欄位 20130731 by Hedwig
			}else{
				//未有資料,  必須先執行新增
				type = "02";
				//設定Form資料
				Form.setEHF020409T0_02(""); //規則名稱
				Form.setEHF020409T0_03("false"); //是否啟用
				Form.setEHF020409T0_04(""); //遲到判斷條件類型
				Form.setEHF020409T0_05(""); //遲到分鐘/次數
				Form.setEHF020409T0_06(""); //遲到扣薪單位
				Form.setEHF020409T0_07(""); //遲到扣薪類型
				Form.setEHF020409T0_08(""); //遲到扣薪倍數
				Form.setEHF020409T0_09(""); //遲到扣薪金額
				Form.setEHF020409T0_10(""); //早退判斷條件類型
				Form.setEHF020409T0_11(""); //早退分鐘/次數
				Form.setEHF020409T0_12(""); //早退扣薪單位
				Form.setEHF020409T0_13(""); //早退扣薪類型
				Form.setEHF020409T0_14(""); //早退扣薪倍數
				Form.setEHF020409T0_15(""); //早退扣薪金額
				Form.setEHF020409T0_16(""); //曠職判斷條件類型
				Form.setEHF020409T0_17(""); //曠職判斷時數條件
				Form.setEHF020409T0_18(""); //曠職扣薪單位
				Form.setEHF020409T0_19(""); //曠職扣薪類型
				Form.setEHF020409T0_20(""); //曠職扣薪倍數
				Form.setEHF020409T0_21(""); //曠職扣薪金額
				Form.setEHF020409T0_22(""); //備註
				Form.setEHF020409T0_24("false"); //是否記錄下班與加班上班刷卡 
				Form.setEHF020409T0_25("false"); //是否記錄下班與加班上班刷卡
			}
			
			rs.close();
			stmt.close();
			
			//判斷類型導向不同功能
			if("01".equals(type)){
				return this.queryDatas(mapping, form, request, response);
			}else if("02".equals(type)){
				return this.addData(mapping, form, request, response);
			}
			
			/*
			generateSelectBox(conn, Form, request);
	
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "init");
			request.setAttribute("collection", "show");
			
			request.setAttribute("Form1Datas", Form);
			*/
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return this.queryDatas(mapping, Form, request, response);
	}

	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		EHF020409M0F Form = (EHF020409M0F) form;
		
		//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			request.setAttribute("action", "queryDataForm");
			request.setAttribute("ems_system_work_mode", "SYSTEM_SALARY_MODE");//前端控制顯示欄位 20130731 by Hedwig
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				" SELECT " +
				" EHF020409T0.*, " +
				" DATE_FORMAT(EHF020409T0.DATE_UPDATE, '%Y/%m/%d %H:%i:%s') AS DATE_UPDATE " +
				" FROM EHF020409T0 " +
				" WHERE 1=1 " +
				" AND EHF020409T0_01 = '"+Form.getEHF020409T0_01()+"' " +  //ID
				" AND EHF020409T0_23 = '"+getLoginUser(request).getCompId()+"' " +  //公司代碼
				" LIMIT 1 " ;
				
				BaseFunction base_tools = new BaseFunction();
				
				//執行SQL
				Map dataMap = base_tools.query(sql);
				
				if(!dataMap.isEmpty()){
					//Set Map to Form
					BeanUtils.populate(Form, dataMap);
				}
				
				base_tools.close();
				
				//建立下拉選單
				generateSelectBox(conn, Form, request);
				
				request.setAttribute("Form1Datas", Form);
				
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("queryCondition", "no");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "show");
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "查詢失敗!");
			System.out.println("EHF020409M0A.queryDatas() " + e);
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
		
		return mapping.findForward("success");
	}
	
	
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		EHF020409M0F Form = (EHF020409M0F) form;
		
		//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			request.setAttribute("action", "queryDataForm");
			request.setAttribute("ems_system_work_mode", "SYSTEM_SALARY_MODE");//前端控制顯示欄位 20130731 by Hedwig
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				" SELECT " +
				" EHF020409T0.*, " +
				" DATE_FORMAT(EHF020409T0.DATE_UPDATE, '%Y/%m/%d %H:%i:%s') AS DATE_UPDATE " +
				" FROM EHF020409T0 " +
				" WHERE 1=1 " +
				" AND EHF020409T0_01 = '"+Form.getEHF020409T0_01()+"' " +  //ID
				" AND EHF020409T0_23 = '"+getLoginUser(request).getCompId()+"' " +  //公司代碼
				" LIMIT 1 " ;
				
				BaseFunction base_tools = new BaseFunction();
				
				//執行SQL
				Map dataMap = base_tools.query(sql);
				
				if(!dataMap.isEmpty()){
					//Set Map to Form
					BeanUtils.populate(Form, dataMap);
				}
				
				base_tools.close();
				
				//建立下拉選單
				generateSelectBox(conn, Form, request);
				
				request.setAttribute("Form1Datas", Form);
				
				FormUtils.setFormDisplayMode(request, form, "create");
				request.setAttribute("queryCondition", "no");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "show");
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "查詢失敗!");
			System.out.println("EHF020409M0A.queryDatas() " + e);
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

	public ActionForward saveData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF020409M0F Form = (EHF020409M0F) form;
		
		//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);


			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				
				" UPDATE EHF020409T0 SET " + 
				" EHF020409T0_02=?, " +
				" EHF020409T0_04=?, EHF020409T0_05=?, EHF020409T0_06=?, EHF020409T0_07=?, " +
				" EHF020409T0_08=?, EHF020409T0_09=?, " +
				" EHF020409T0_10=?, EHF020409T0_11=?, EHF020409T0_12=?, EHF020409T0_13=?, " +
				" EHF020409T0_14=?, EHF020409T0_15=?, " +
				" EHF020409T0_16=?, EHF020409T0_17=?, EHF020409T0_18=?, " +
				" EHF020409T0_19=?, EHF020409T0_20=?, " +
				" EHF020409T0_21=?, EHF020409T0_22=?, " +
				" EHF020409T0_24=?, EHF020409T0_25=?, " +
				
				" USER_UPDATE=?, " +
				
				" EHF020409T0_26=?, EHF020409T0_27=?, " +
				" EHF020409T0_28=?, EHF020409T0_29=?, " +
				" EHF020409T0_30=?, EHF020409T0_31=?, " +
				" EHF020409T0_32=?, EHF020409T0_33=?, " +
				" EHF020409T0_34=?,  " +
				" EHF020409T0_35=?,  " +
				" EHF020409T0_36=?, " +
				" EHF020409T0_37=?, " +
				
				
				" VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF020409T0_01 = '"+Form.getEHF020409T0_01()+"' " +  //ID
				" AND EHF020409T0_23 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid,Form.getEHF020409T0_02());  //規則名稱
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_04()==null?"0":Form.getEHF020409T0_04());  //遲到判斷條件類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_05()==null?"0":Form.getEHF020409T0_05()==""?"0":Form.getEHF020409T0_05());  //遲到分數/次數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_06()==null?"0":Form.getEHF020409T0_06()==""?"0":Form.getEHF020409T0_06());  //遲到扣薪單位
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_07()==null?"0":Form.getEHF020409T0_07());  //遲到扣薪類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_08()==null?"0":Form.getEHF020409T0_08()==""?"0":Form.getEHF020409T0_08());  //遲到扣薪倍數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_09()==null?"0":Form.getEHF020409T0_09()==""?"0":Form.getEHF020409T0_09());  //遲到扣薪金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_10()==null?"0":Form.getEHF020409T0_10());  //早退判斷條件類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_11()==null?"0":Form.getEHF020409T0_11()==""?"0":Form.getEHF020409T0_11());  //早退分數/次數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_12()==null?"0":Form.getEHF020409T0_12()==""?"0":Form.getEHF020409T0_12());  //早退扣薪單位
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_13()==null?"0":Form.getEHF020409T0_13());  //早退扣薪類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_14()==null?"0":Form.getEHF020409T0_14()==""?"0":Form.getEHF020409T0_14());  //早退扣薪倍數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_15()==null?"0":Form.getEHF020409T0_15()==""?"0":Form.getEHF020409T0_15());  //早退扣薪金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_16()==null?"0":Form.getEHF020409T0_16());  //早退判斷條件類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_17()==null?"0":Form.getEHF020409T0_17()==""?"0":Form.getEHF020409T0_17());  //曠職判斷時數條件
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_18()==null?"0":Form.getEHF020409T0_18()==""?"0":Form.getEHF020409T0_18());  //曠職扣薪單位(小時/次數)
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_19()==null?"0":Form.getEHF020409T0_19());  //曠職扣薪類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_20()==null?"0":Form.getEHF020409T0_20()==""?"0":Form.getEHF020409T0_20());  //曠職扣薪倍數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_21()==null?"0":Form.getEHF020409T0_21()==""?"0":Form.getEHF020409T0_21());  //曠職扣薪金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_22()==null?"":Form.getEHF020409T0_22());  //備註
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_24()));  //是否記錄下班與加班上班的刷卡
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_25()));  //是否記錄下班與加班上班的刷卡
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());
				indexid++;
				
				
				p6stmt.setString(indexid, Form.getEHF020409T0_26()==null?"0":Form.getEHF020409T0_26());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_27()==null?"0":Form.getEHF020409T0_27());  //
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_28()==null?"0":Form.getEHF020409T0_28()));  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_29()==null?"0":Form.getEHF020409T0_29());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_30()==null?"0":Form.getEHF020409T0_30());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_31()==null?"0":Form.getEHF020409T0_31());  //
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020409T0_32()==null?"0":Form.getEHF020409T0_32()));  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_33()==null?"0":Form.getEHF020409T0_33());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_34()==null?"0":Form.getEHF020409T0_34());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_35()==null?"0":Form.getEHF020409T0_35());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_36()==null?"0":Form.getEHF020409T0_36());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020409T0_37()==null?"0":Form.getEHF020409T0_37());  //
				indexid++;
				
				
				
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				
				request.setAttribute("MSG","儲存成功!!");
				
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			} else {
				
				generateSelectBox(conn, Form, request);
				
				request.setAttribute("chk_type", "yes");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				
				return mapping.findForward("success");
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

		return this.queryDatas(mapping, Form, request, response);
	}

	public ActionForward delData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF020409M0F Form = (EHF020409M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF020409T0_01(arrid[0]);
		}
		
    	//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {

					Statement stmt = conn.createStatement();
					
					String sql = "" +
					" DELETE FROM EHF020409T0 " +
					" WHERE 1=1 " +
					" AND EHF020409T0_01 = '"+Form.getEHF020409T0_01()+"' " +  //序號
					" AND EHF020409T0_23 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
					
					stmt.execute(sql);
					stmt.close();
					conn.commit();
				
				request.setAttribute("MSG", "刪除成功");
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
	 * 畫面導向
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward redirect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("redirect");
	}
	
	
}