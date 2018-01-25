package com.spon.ems.hr.actions;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.ems.hr.forms.EHF010101M0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;


import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>津貼基本資料設定
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF010101M0A extends NewDispatchAction {

	public EHF010101M0A() {

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
		EHF010101M0F Form = (EHF010101M0F) form;

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
					" INSERT INTO ehf010101t0 ( EHF010101T0_02 ,EHF010101T0_03 ,EHF010101T0_04 ,EHF010101T0_05 " +
					" ,EHF010101T0_06 ,EHF010101T0_06_FLG ,EHF010101T0_06_RATE " +
					" ,EHF010101T0_07 ,EHF010101T0_08 ,EHF010101T0_09 ,EHF010101T0_10 ,EHF010101T0_11 ,EHF010101T0_12 " +
					" ,EHF010101T0_13 ,EHF010101T0_14 ,EHF010101T0_15 ,EHF010101T0_16 ,EHF010101T0_17 ,EHF010101T0_18 " +
					" ,EHF010101T0_19 ,EHF010101T0_20 ,EHF010101T0_21 ,EHF010101T0_22 ,EHF010101T0_23,EHF010101T0_24" +
					" ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
					" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) " ;

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
					int indexid = 1;
					p6stmt.setString(indexid,Form.getEHF010101T0_02());  //津貼名稱
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_03())); //所得稅類別
					indexid++;
					p6stmt.setBoolean(indexid, false); //津貼類別
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_05())); //是否啟用
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_06());  //津貼金額
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_06_FLG()));  //是否依據一日基本薪給津貼
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_06_RATE()==null?"0":Form.getEHF010101T0_06_RATE()==""?"0":Form.getEHF010101T0_06_RATE());  //一日基本薪加成比率
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_07()));  //是否有津貼條件
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_08());  //津貼條件類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_09_HH()+Form.getEHF010101T0_09_MM());  //津貼條件時間
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_10());  //津貼條件時數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_11()==null?"":Form.getEHF010101T0_11());  //津貼特殊條件Key
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_12()));  //是否有津貼加成
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_13());  //津貼加成類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_14()==null?"0":Form.getEHF010101T0_14()==""?"0":Form.getEHF010101T0_14());  //津貼加成率
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_15()==null?"0":Form.getEHF010101T0_15()==""?"0":Form.getEHF010101T0_15());  //津貼加成固定金額
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_16()==null?"":Form.getEHF010101T0_16());  //津貼加成職等條件
					indexid++;
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_17()));  //是否有津貼加成條件
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_18());  //津貼加成條件類型
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_19_HH()+Form.getEHF010101T0_19_MM());  //津貼加成條件時間
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_20());  //津貼加成條件時數
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_21()==null?"":Form.getEHF010101T0_21());  //津貼加成特殊條件Key
					indexid++;
					p6stmt.setString(indexid, Form.getEHF010101T0_22());  //備註
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
					indexid++; 
					p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_24()));  //是否參考加班單  20131107新增  BY賴泓錡
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //使用員工姓名
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());
					indexid++;
				
					//System.out.println("insert sql ==>"+p6stmt.getQueryFromPreparedStatement());
					
					p6stmt.executeUpdate();
					pstmt.close();
					p6stmt.close();
				
				
//				Statement stmt = conn.createStatement();
//				String chksql = "" +
//				" SELECT Last_Insert_Id() AS ID " ;
//				ResultSet rs=stmt.executeQuery(chksql);
//				String chkId = "";
//				
//				if(rs.next()){
//					chkId = rs.getString("ID");
//				}
				

				conn.commit();
				generateSelectBox(conn, Form, request);
				
				request.setAttribute("MSG", "新增成功");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				// 清掉畫面上的新增
			
			} else {
				
				request.setAttribute("button", "edit");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				if (request.getAttribute("Form1Datas") != null) {
					form = (EHF010101M0F) request.getAttribute("Form1Datas");
				}
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));

				return init_add(mapping, form, request, response);
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF010101M0A.addData() " + e);
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
		
		return queryForm(mapping, form, request, response);
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
		
		//產生(時)的下拉選單
		List listHOUR=new ArrayList();
		DecimalFormat df=new DecimalFormat("00");
		for (int i = 0; i < 24; i++) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listHOUR.add(bform);
		}
		request.setAttribute("listHOUR",listHOUR);
		
		//產生(分)的下拉選單
		List listMINUTE=new ArrayList();
		
		for (int i = 0; i < 60; i= i+5) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listMINUTE.add(bform);
		}
		request.setAttribute("listMINUTE",listMINUTE);
		
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
		
		//產生 所得稅下拉選單 --> 未稅,應稅
		try{
		List datas = new ArrayList();
		BA_ALLKINDForm bform = new BA_ALLKINDForm();
		bform.setItem_id("");
		bform.setItem_value("一請選擇一");
		datas.add(bform);
		bform = new BA_ALLKINDForm();
		bform.setItem_id("1");
		bform.setItem_value("未稅");
		datas.add(bform);
		bform = new BA_ALLKINDForm();
		bform.setItem_id("0");
		bform.setItem_value("應稅");
		datas.add(bform);
		request.setAttribute("listEHF010101T0_03", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生津貼條件類型
		try{
			List listEHF010101T0_08 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF010101T0_08.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依時間條件");
			listEHF010101T0_08.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依時數條件");
			listEHF010101T0_08.add(bform);
			request.setAttribute("listEHF010101T0_08", listEHF010101T0_08);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生津貼加成類型
		try{
			List listEHF010101T0_13 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF010101T0_13.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依比例");
			listEHF010101T0_13.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依固定金額");
			listEHF010101T0_13.add(bform);
			request.setAttribute("listEHF010101T0_13", listEHF010101T0_13);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生津貼加成條件類型
		try{
			List listEHF010101T0_18 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF010101T0_18.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依時間條件");
			listEHF010101T0_18.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依時數條件");
			listEHF010101T0_18.add(bform);
			request.setAttribute("listEHF010101T0_18", listEHF010101T0_18);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			List datas = new ArrayList();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF010100T0_01 ,EHF010100T0_02 ,EHF010100T0_03 ,EHF010100T0_04 ,EHF010100T0_05 ,EHF010100T0_06 ,EHF010100T0_07 " +
			" ,EHF010100T0_08 ,EHF010100T0_09 ,EHF010100T0_10 " +
			" FROM EHF010100T0 " +
			" WHERE 1=1 " +
			" AND EHF010100T0_11 = '"+getLoginUser(request).getCompId()+"' " ;
			
			ResultSet rs=stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			datas.add(bform);
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("EHF010100T0_03"));
				bform.setItem_value(rs.getString("EHF010100T0_04"));
				datas.add(bform);	
				
			}
			
			rs.close();
			stmt.close();
			
			request.setAttribute("listEHF010101T1_03", datas);
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
		
		try{
			
			conn = tools.getConnection("SPOS");
			EHF010101M0F Form = new EHF010101M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
	
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			//FormUtils.setFormDisplayMode(request, form, "create");
			//request.setAttribute("button", "init");
			request.setAttribute("collection", "show"); 
			FormUtils.setFormDisplayMode(request, form, "edit");
			request.setAttribute("queryCondition", "yes");
			request.setAttribute("button", "init");

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
	public ActionForward init_add(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		DataSource ds = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			
			conn = tools.getConnection("SPOS");
			EHF010101M0F Form = new EHF010101M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
	
			Form.setEHF010101T0_05("1");
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);

			FormUtils.setFormDisplayMode(request, form, "edit");
			request.setAttribute("queryCondition", "NO");
			
			request.setAttribute("button", "edit");
			request.setAttribute("edit", "add");
			request.setAttribute("collection", "");

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

	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF010101M0F Form = (EHF010101M0F) form;

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
				
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT * " +
				" FROM EHF010101T0 " +
				" WHERE 1=1 " +
				" AND EHF010101T0_23 = '"+getLoginUser(request).getCompId()+"' " ;
				
				//津貼名稱
				if(!"".equals(Form.getEHF010101T0_02()) && Form.getEHF010101T0_02()!=null ){
					sql += " AND EHF010101T0_02 LIKE '%"+Form.getEHF010101T0_02()+"%' ";
				}
				//所得稅
				if(!"".equals(Form.getEHF010101T0_03()) && Form.getEHF010101T0_03()!=null ){
					sql += " AND EHF010101T0_03 = "+Form.getEHF010101T0_03()+" ";
				}
				//是否啟用
				if(!"".equals(Form.getEHF010101T0_05()) && Form.getEHF010101T0_05()!=null ){
					sql += " AND EHF010101T0_05 = "+Form.getEHF010101T0_05()+" ";
				}
				//津貼金額
				if(!"".equals(Form.getEHF010101T0_06()) && Form.getEHF010101T0_06()!=null ){
					sql += " AND EHF010101T0_06 = "+Form.getEHF010101T0_06()+" ";
				}

				sql += " ORDER BY EHF010101T0_02 ";//LIMIT 10 ";

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					rs.beforeFirst();
					
					while(rs.next()){
						
						EHF010101M0F bean = new EHF010101M0F();
						bean.setEHF010101T0_01(rs.getString("EHF010101T0_01"));  //津貼資料序號
						bean.setEHF010101T0_02(rs.getString("EHF010101T0_02"));  //津貼名稱
						//所得稅
						if("0".equals(rs.getString("EHF010101T0_03"))){
							bean.setEHF010101T0_03("未稅");
						}else if("1".equals(rs.getString("EHF010101T0_03"))){
							bean.setEHF010101T0_03("應稅");
						}
						//是否啟用
						if("0".equals(rs.getString("EHF010101T0_05"))){
							bean.setEHF010101T0_05("否");
						}else if("1".equals(rs.getString("EHF010101T0_05"))){    
							bean.setEHF010101T0_05("是");
						}
						//是否依據一日基本薪給津貼
						if("0".equals(rs.getString("EHF010101T0_06_FLG"))){
							bean.setEHF010101T0_06(rs.getString("EHF010101T0_06"));	 //津貼金額
						}else if("1".equals(rs.getString("EHF010101T0_06_FLG"))){    
							bean.setEHF010101T0_06("依據一日基本薪 "+rs.getFloat("EHF010101T0_06_RATE")+" 倍給津貼");	 //津貼金額 (依據一日基本薪給津貼)
						}else{
							bean.setEHF010101T0_06(rs.getString("EHF010101T0_06"));	 //津貼金額
						}
						
						bean.setEHF010101T0_22(rs.getString("EHF010101T0_22"));	 //備註
						list.add(bean);
					}
					if(request.getAttribute("MSG")==null||"".equals(request.getAttribute("MSG"))){
						request.setAttribute("MSG", "查詢成功!!");
					}else{
						request.setAttribute("MSG", request.getAttribute("MSG")+"查詢成功!!");
					}
				}else{
					
					request.setAttribute("MSG", "查無資料!!");
				}
				
				request.setAttribute("Form2Datas",list);
				
				rs.close();
				stmt.close();
				
				
				
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("queryCondition", "yes");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "查詢失敗!");
			System.out.println("EHF010101M0A.queryDatas() " + e);
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
		this.clear(Form);
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
		
		EHF010101M0F Form = (EHF010101M0F) form;
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF010101T0_01(arrid[0]);
		}else if("".equals(Form.getEHF010101T0_01()) || Form.getEHF010101T0_01() == null){
			request.setAttribute("MSG", "請先選擇一筆要修改的資料!!");
			return queryDatas(mapping, form, request, response);
		}
    	
    	try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "editDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			

			if (lc_errors.isEmpty()) {
				
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT * FROM EHF010101T0 " +
				" WHERE 1=1 " +
				" AND EHF010101T0_01 = '"+Form.getEHF010101T0_01()+"' " +
				" AND EHF010101T0_23 = '"+getLoginUser(request).getCompId()+"' " ;
				

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					Form.setEHF010101T0_02(rs.getString("EHF010101T0_02"));//津貼金額
					Form.setEHF010101T0_03(rs.getString("EHF010101T0_03"));  //所得稅
					Form.setEHF010101T0_05(rs.getString("EHF010101T0_05"));  //是否啟用
					Form.setEHF010101T0_06(rs.getString("EHF010101T0_06"));  //津貼金額
					Form.setEHF010101T0_06_FLG(tools.BooleanToString(rs.getBoolean("EHF010101T0_06_FLG"), "2"));  //是否有津貼加成
					Form.setEHF010101T0_06_RATE(rs.getString("EHF010101T0_06_RATE"));  //津貼加成比率
					Form.setEHF010101T0_07(tools.BooleanToString(rs.getBoolean("EHF010101T0_07"), "2"));
					Form.setEHF010101T0_08(rs.getString("EHF010101T0_08"));  //津貼條件類型
					Form.setEHF010101T0_09_HH(rs.getString("EHF010101T0_09").substring(0, 2));  //津貼條件時間
					Form.setEHF010101T0_09_MM(rs.getString("EHF010101T0_09").substring(2, 4));
					Form.setEHF010101T0_10(rs.getString("EHF010101T0_10"));  //津貼條件時數
					Form.setEHF010101T0_11(rs.getString("EHF010101T0_11"));  //津貼特殊條件Key
					Form.setEHF010101T0_12(tools.BooleanToString(rs.getBoolean("EHF010101T0_12"), "2"));  //是否有津貼加成
					Form.setEHF010101T0_13(rs.getString("EHF010101T0_13"));  //津貼加成類型
					Form.setEHF010101T0_14(rs.getString("EHF010101T0_14"));  //津貼加成比率
					Form.setEHF010101T0_15(rs.getString("EHF010101T0_15"));  //津貼加成固定金額
					Form.setEHF010101T0_16(rs.getString("EHF010101T0_16"));  //津貼加成職等條件Key
					Form.setEHF010101T0_17(tools.BooleanToString(rs.getBoolean("EHF010101T0_17"), "2"));  //是否有津貼加成條件
					Form.setEHF010101T0_18(rs.getString("EHF010101T0_18"));  //津貼條件類型
					Form.setEHF010101T0_19_HH(rs.getString("EHF010101T0_19").substring(0, 2));  //津貼條件時間
					Form.setEHF010101T0_19_MM(rs.getString("EHF010101T0_19").substring(2, 4));
					Form.setEHF010101T0_20(rs.getString("EHF010101T0_20"));  //津貼條件時數
					Form.setEHF010101T0_21(rs.getString("EHF010101T0_21"));  //津貼特殊條件Key
					Form.setEHF010101T0_22(rs.getString("EHF010101T0_22"));  //備註
					
					Form.setEHF010101T0_24(tools.BooleanToString(rs.getBoolean("EHF010101T0_24"), "2"));  //是否參考加班單  20131107新增  BY賴泓錡
					Form.setUSER_CREATE(rs.getString("USER_CREATE"));
					Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
					Form.setVERSION(rs.getInt("VERSION"));
					Form.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
				}
			
				request.setAttribute("Form2Datas",list);

				rs.close();
				stmt.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("chk_type", "yes");
				request.setAttribute("button", "edit");
				request.setAttribute("edit", "edit");
				request.setAttribute("collection", "");
				
			} else {
				
				request.setAttribute("chk_type", "yes");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
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
		EHF010101M0F Form = (EHF010101M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			
			BA_Vaildate ve = BA_Vaildate.getInstance();
			if("".equals(Form.getEHF010101T0_01())){
				request.setAttribute("MSG","津貼資料序號錯誤!!請重新查詢資料!!");
				return init(mapping, form, request, response);
				
			}

			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				
				" UPDATE ehf010101t0 SET  " + 
				" EHF010101T0_02=? ,EHF010101T0_03=? ,EHF010101T0_04=? ,EHF010101T0_05=? " +
				" ,EHF010101T0_06=? ,EHF010101T0_06_FLG=? ,EHF010101T0_06_RATE=? " +
				" ,EHF010101T0_07=? ,EHF010101T0_08=? ,EHF010101T0_09=? ,EHF010101T0_10=? ,EHF010101T0_11=? ,EHF010101T0_12=? " +
				" ,EHF010101T0_13=? ,EHF010101T0_14=? ,EHF010101T0_15=? ,EHF010101T0_16=? ,EHF010101T0_17=? ,EHF010101T0_18=? " +
				" ,EHF010101T0_19=? ,EHF010101T0_20=? ,EHF010101T0_21=? ,EHF010101T0_22=? ,EHF010101T0_24=?" +
				" ,USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE EHF010101T0_01 = '"+Form.getEHF010101T0_01()+"' " +
				" AND EHF010101T0_23 = '"+getLoginUser(request).getCompId()+"' " ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid,Form.getEHF010101T0_02());  //津貼名稱
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_03())); //所得稅類別
				indexid++;
				p6stmt.setBoolean(indexid, false); //津貼類別
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_05())); //是否啟用
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_06());  //津貼金額
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_06_FLG()));  //是否依據一日基本薪給津貼
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_06_RATE()==null?"0":Form.getEHF010101T0_06_RATE()==""?"0":Form.getEHF010101T0_06_RATE());  //一日基本薪加成比率
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_07()));  //是否有津貼條件
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_08());  //津貼條件類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_09_HH()+Form.getEHF010101T0_09_MM());  //津貼條件時間
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_10());  //津貼條件時數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_11()==null?"":Form.getEHF010101T0_11());  //津貼特殊條件Key
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_12()));  //是否有津貼加成
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_13());  //津貼加成類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_14()==null?"0":Form.getEHF010101T0_14()==""?"0":Form.getEHF010101T0_14());  //津貼加成率
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_15()==null?"0":Form.getEHF010101T0_15()==""?"0":Form.getEHF010101T0_15());  //津貼加成固定金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_16()==null?"":Form.getEHF010101T0_16());  //津貼加成職等條件
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_17()));  //是否有津貼加成條件
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_18());  //津貼加成條件類型
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_19_HH()+Form.getEHF010101T0_19_MM());  //津貼加成條件時間
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_20());  //津貼加成條件時數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_21()==null?"":Form.getEHF010101T0_21());  //津貼加成特殊條件Key
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010101T0_22());  //備註
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF010101T0_24())); //是否參考加班單  20131107新增  BY賴泓錡
				indexid++;
				
				p6stmt.setString(indexid, getLoginUser(request).getUserName());
				indexid++;
				
				System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				request.setAttribute("MSG","儲存成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
	
				
			} else {
				
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

			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			
			request.setAttribute("MSG","津貼資料儲存錯誤!!請重新操作!!");
						
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
		EHF010101M0F Form = (EHF010101M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF010101T0_01(arrid[0]);
		}else{
			request.setAttribute("MSG", "請選擇一筆要刪除的資料!!");
			return queryDatas(mapping, form, request, response);
		}
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			//處理刪除檢核程式
			lc_errors = Form.validate(mapping, request, conn);
			
			if (lc_errors.isEmpty()) {

					Statement stmt = conn.createStatement();
					
					String sql = "" +
					" DELETE FROM EHF010101T0 " +
					" WHERE 1=1 " +
					" AND EHF010101T0_01 = '"+Form.getEHF010101T0_01()+"' " +  //序號
					" AND EHF010101T0_23 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
					
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
	/**
	 * 清除畫面殘留
	 * @param form
	 */
	protected void clear(ActionForm form){
		
		try{
			EHF010101M0F Form = (EHF010101M0F) form;
			//清除殘留資料
			Form.setEHF010101T0_01("");
			Form.setEHF010101T0_02("");
			Form.setEHF010101T0_05("");
			Form.setEHF010101T0_03("");
			Form.setEHF010101T0_06("");


		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
	}
}