package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
//import com.spon.ems.util.card_system.EMS_CardControl;
import com.spon.ems.util.card_system.EMS_CardControl;
import com.spon.ems.vacation.forms.EHF020402M0F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
//import com.spon.utils.util.BA_EMS_TOOLS;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>卡鐘擷取設定作業
 *  @version 1.6
 *  @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020402M0A extends NewDispatchAction {

	public EHF020402M0A() {

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
		//EMS_SetClockXml xml_tools = EMS_SetClockXml.getInstance();
		EHF020402M0F Form = (EHF020402M0F) form;

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
		String EHF020402T0_08="";
		try {
			if (lc_errors.isEmpty()) {
					//新增表單資訊
				String sql = "" +
				" INSERT INTO ehf020402t0 (EHF020402T0_02, EHF020402T0_03, EHF020402T0_04, EHF020402T0_05, EHF020402T0_06," +
				" EHF020402T0_07, EHF020402T0_07_FLG, EHF020402T0_08, " +
				" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " +
				" VALUES (?,?,?,?,?,?,?,?,?,?, 1, NOW() ) " ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,
						pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, (Form.getEHF020402T0_02_HH()==null?"":Form.getEHF020402T0_02_HH())+
										  (Form.getEHF020402T0_02_MM()==null?"":Form.getEHF020402T0_02_MM()));  //資料擷取時間
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020402T0_03()==null?"":Form.getEHF020402T0_03() ); //檔案名稱
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020402T0_04()==null?"":Form.getEHF020402T0_04()); //擷取檔案路徑
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020402T0_05()); //備註
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020402T0_07()); //資料來源類型
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020402T0_07_FLG())); //是否啟用
				indexid++;
				
				
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_01())==true?"N,":Form.getEHF020402T0_08_01()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_02())==true?"N,":Form.getEHF020402T0_08_02()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_03())==true?"N,":Form.getEHF020402T0_08_03()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_04())==true?"N,":Form.getEHF020402T0_08_04()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_05())==true?"N,":Form.getEHF020402T0_08_05()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_06())==true?"N,":Form.getEHF020402T0_08_06()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_07())==true?"N,":Form.getEHF020402T0_08_07()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_08())==true?"N,":Form.getEHF020402T0_08_08()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_09())==true?"N,":Form.getEHF020402T0_08_09()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_10())==true?"N,":Form.getEHF020402T0_08_10()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_11())==true?"N,":Form.getEHF020402T0_08_11()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_12())==true?"N,":Form.getEHF020402T0_08_12()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_13())==true?"N,":Form.getEHF020402T0_08_13()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_14())==true?"N,":Form.getEHF020402T0_08_14()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_15())==true?"N,":Form.getEHF020402T0_08_15()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_16())==true?"N,":Form.getEHF020402T0_08_16()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_17())==true?"N,":Form.getEHF020402T0_08_17()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_18())==true?"N,":Form.getEHF020402T0_08_18()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_19())==true?"N,":Form.getEHF020402T0_08_19()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_20())==true?"N,":Form.getEHF020402T0_08_20()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_21())==true?"N,":Form.getEHF020402T0_08_21()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_22())==true?"N,":Form.getEHF020402T0_08_22()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_23())==true?"N,":Form.getEHF020402T0_08_23()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_24())==true?"N,":Form.getEHF020402T0_08_24()+",";
				
				//指定動態檔案名稱
				//格式: [ADDATE@-1.TXT],[CHIDATE@-1.DAT] 
				//-> ADDATE = 西元年, CHIDATE = 民國年
				// @ -> 分隔符號, -1 -> 日期位移量, "." -> 副檔名分隔符號, TXT -> 副檔名
				String temp = "";
				if(!"".equals(Form.getEHF020402T0_08_25()) && Form.getEHF020402T0_08_25() != null &&
						!"".equals(Form.getEHF020402T0_08_27()) && Form.getEHF020402T0_08_27() != null){
					temp = Form.getEHF020402T0_08_25()+"@"+Form.getEHF020402T0_08_26()+Form.getEHF020402T0_08_27();
				}
				
				EHF020402T0_08+=this.isnull(temp)==true?"N,":temp+",";
				
				p6stmt.setString(indexid, EHF020402T0_08==null?"":EHF020402T0_08); //資料格式
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //使用員工姓名
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());
				indexid++;
				
					
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();

				conn.commit();

				
/*
				Statement stmt = conn.createStatement();
				String chksql = "" +
				" SELECT Last_Insert_Id() AS ID FROM EHF020402T0 " ;
				ResultSet rs=stmt.executeQuery(chksql);
				String chkId = "";
				
				if(rs.next()){
					chkId = rs.getString("ID");
				}
				
				String xml_filepath = getServlet().getServletContext().getRealPath("")+"/WEB-INF/classes/jobSchedule.xml";
				
				xml_tools.addTrigger(xml_filepath, chkId, Form.getEHF020402T0_03(), Form.getEHF020402T0_04(), Form.getEHF020402T0_02_HH()+Form.getEHF020402T0_02_MM());
				
*/		
		
				generateSelectBox(conn, Form, request);
				
				request.setAttribute("MSG", "新增成功");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				// 清掉畫面上的新增
			
			} else {
				
				request.setAttribute("button", "init");
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				if (request.getAttribute("Form1Datas") != null) {
					form = (EHF020402M0F) request.getAttribute("Form1Datas");
				}
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
//				return init(mapping, form, request, response);
				return init_save(mapping, form, request, response);
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF020402M0A.addData() " + e);
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
		
//		request.setAttribute("FM020201_11_list", this.getSelectOptions(conn,false, "SA002"," AND FM010501_06 = '"+userform(request).getSC0030_14()+"' "));
//		request.setAttribute("FM020201_P_list", this.getSelectOptions(conn,true, "FM02002"," AND FM010501_06 = '"+userform(request).getSC0030_14()+"' "));
		
		List listEHF020402T0_02_HH=new ArrayList();
		DecimalFormat df=new DecimalFormat("00");
		for (int i = 0; i < 24; i++) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listEHF020402T0_02_HH.add(bform);
		}
		request.setAttribute("listEHF020402T0_02_HH",listEHF020402T0_02_HH);
		
		List listEHF020402T0_02_MM=new ArrayList();
		
		for (int i = 0; i < 60; i= i+10) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listEHF020402T0_02_MM.add(bform);
		}
		request.setAttribute("listEHF020402T0_02_MM",listEHF020402T0_02_MM);
		
		//產生資料來源類型下拉選單
		try{
			List listEHF020402T0_07 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("CSV");
			bform.setItem_value("CSV檔案");
			listEHF020402T0_07.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("EK-3000R");
			bform.setItem_value("EK-3000R資料庫");
			listEHF020402T0_07.add(bform);
			
			request.setAttribute("listEHF020402T0_07", listEHF020402T0_07);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
		
		try{
			List listEHF020402T0_08_25 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF020402T0_08_25.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("ADDATE");
			bform.setItem_value("西元年");
			listEHF020402T0_08_25.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("CHIDATE");
			bform.setItem_value("民國年");
			listEHF020402T0_08_25.add(bform);
			
			request.setAttribute("listEHF020402T0_08_25", listEHF020402T0_08_25);
			
		}catch(Exception e){
			e.printStackTrace();
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
		EMS_ACCESS ems_access = EMS_ACCESS.getInstance();
		try{
			
			conn = tools.getConnection("SPOS");
			EHF020402M0F Form = new EHF020402M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "init");
			request.setAttribute("collection", "show");
			request.setAttribute("queryCondition", "yes");
			//request.setAttribute("collection", "");
			
//			FormUtils.setFormDisplayMode(request, form, "create");
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
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
	 * 給新增時的介面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward init_save(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_ACCESS ems_access = EMS_ACCESS.getInstance();
		try{
			
			conn = tools.getConnection("SPOS");
			EHF020402M0F Form = new EHF020402M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "save");
			request.setAttribute("collection", "");
			request.setAttribute("queryCondition", "");
			//request.setAttribute("collection", "");
			
//			FormUtils.setFormDisplayMode(request, form, "create");
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
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
		EMS_ACCESS ems_access = EMS_ACCESS.getInstance();
		EHF020402M0F Form = (EHF020402M0F) form;

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT EHF020402T0_01, EHF020402T0_02, EHF020402T0_03, EHF020402T0_04, EHF020402T0_05, " +
				" EHF020402T0_07, EHF020402T0_07_FLG, EHF020402T0_08 " +
				" FROM EHF020402T0 " +
				" WHERE 1=1 " +
				" AND EHF020402T0_06 = '"+getLoginUser(request).getCompId()+"' " ;
				
				if(!"".equals(Form.getEHF020402T0_07()) && Form.getEHF020402T0_07()!=null ){  //資料來源類型
					sql += " AND EHF020402T0_07 = '"+Form.getEHF020402T0_07()+"' ";
				}
//				if(!"".equals(Form.getEHF020402T0_02_HH()+Form.getEHF020402T0_02_MM()) ){  //資料擷取時間
//					sql += " AND EHF020402T0_02 = '"+Form.getEHF020402T0_02_HH()+Form.getEHF020402T0_02_MM()+"' ";
//				}
				if(!"".equals(Form.getEHF020402T0_03()) && Form.getEHF020402T0_03()!=null ){  //檔案名稱
					sql += " AND EHF020402T0_03 LIKE '%"+Form.getEHF020402T0_03()+"%' ";
				}
//				if(!"".equals(Form.getEHF020402T0_04()) && Form.getEHF020402T0_04()!=null ){  //擷取檔案路徑
//					sql += " AND EHF020402T0_04 LIKE '%"+Form.getEHF020402T0_04()+"%' ";
//				}
				
				sql += " ORDER BY EHF020402T0_02 , EHF020402T0_01 LIMIT 10 ";

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				while(rs.next()){
					EHF020402M0F bean = new EHF020402M0F();
					bean.setEHF020402T0_01(rs.getString("EHF020402T0_01"));  //擷取序號
					bean.setEHF020402T0_07(rs.getString("EHF020402T0_07"));  //資料來源類型
					if(!"".equals(rs.getString("EHF020402T0_02")) && rs.getString("EHF020402T0_02") != null){
						bean.setEHF020402T0_02(rs.getString("EHF020402T0_02").substring(0, 2)+":"+rs.getString("EHF020402T0_02").substring(2, 4)); //資料擷取時間
					}else{
						bean.setEHF020402T0_02("");  //資料擷取時間
					}
					bean.setEHF020402T0_03(rs.getString("EHF020402T0_03"));  //檔案名稱
					bean.setEHF020402T0_04(rs.getString("EHF020402T0_04"));  //檔案擷取路徑
					bean.setEHF020402T0_05(rs.getString("EHF020402T0_05"));  //備註
					if("CSV".equals(rs.getString("EHF020402T0_07"))){
						bean.setEHF020402T0_07("CSV檔案");  //資料來源類型
					}else if("EK-3000R".equals(rs.getString("EHF020402T0_07"))){
						bean.setEHF020402T0_07("EK-3000R資料庫");  //資料來源類型
					}else{
						bean.setEHF020402T0_07("");  //資料來源類型
					}
					if("0".equals(rs.getString("EHF020402T0_07_FLG"))){
						bean.setEHF020402T0_07_FLG("否");
					}else if("1".equals(rs.getString("EHF020402T0_07_FLG"))){    //是否啟用
						bean.setEHF020402T0_07_FLG("是");
					}
					
					list.add(bean);
				}
				
				request.setAttribute("Form2Datas",list);
//				Form.setEHF010100C(list);
				
				rs.close();
				stmt.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("queryCondition", "yes");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				//判斷身分別
				ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			this.cleanAddField(Form);
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
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF020402M0F Form = (EHF020402M0F) form;
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_ACCESS ems_access = EMS_ACCESS.getInstance();
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF020402T0_01(arrid[0]);
		}else if("".equals(Form.getEHF020402T0_01()) || Form.getEHF020402T0_01() == null){
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
				" SELECT * FROM EHF020402T0 " +
				" WHERE 1=1 " +
				" AND EHF020402T0_01 = '"+Form.getEHF020402T0_01()+"' " +
				" AND EHF020402T0_06 = '"+getLoginUser(request).getCompId()+"' " ;
				

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);

				if(rs.next()){
					
					if(!"".equals(rs.getString("EHF020402T0_02")) && rs.getString("EHF020402T0_02") != null){
						Form.setEHF020402T0_02_HH(rs.getString("EHF020402T0_02").substring(0, 2));  //資料擷取時間
						Form.setEHF020402T0_02_MM(rs.getString("EHF020402T0_02").substring(2, 4));  //資料擷取時間
					}else{
						Form.setEHF020402T0_02_HH("");  //資料擷取時間
						Form.setEHF020402T0_02_MM("");  //資料擷取時間
					}
					Form.setEHF020402T0_03(rs.getString("EHF020402T0_03"));  //檔案名稱
					Form.setEHF020402T0_04(rs.getString("EHF020402T0_04"));  //擷取檔案路徑
					Form.setEHF020402T0_05(rs.getString("EHF020402T0_05"));  //備註
					Form.setEHF020402T0_07(rs.getString("EHF020402T0_07"));  //資料來源類型
					Form.setEHF020402T0_07_FLG(rs.getString("EHF020402T0_07_FLG"));  //是否啟用
					Form.setEHF020402T0_08("");  //資料格式
					
					String EHF020402T0_08[]=((String)rs.getString("EHF020402T0_08")).split(",");
					
					Form.setEHF020402T0_08_01(EHF020402T0_08[0]);  //資料格式
					Form.setEHF020402T0_08_02(EHF020402T0_08[1]);  //資料格式
					Form.setEHF020402T0_08_03(EHF020402T0_08[2]);  //資料格式
					Form.setEHF020402T0_08_04(EHF020402T0_08[3]);  //資料格式
					Form.setEHF020402T0_08_05(EHF020402T0_08[4]);  //資料格式
					Form.setEHF020402T0_08_06(EHF020402T0_08[5]);  //資料格式
					Form.setEHF020402T0_08_07(EHF020402T0_08[6]);  //資料格式
					Form.setEHF020402T0_08_08(EHF020402T0_08[7]);  //資料格式
					Form.setEHF020402T0_08_09(EHF020402T0_08[8]);  //資料格式
					Form.setEHF020402T0_08_10(EHF020402T0_08[9]);  //資料格式
					Form.setEHF020402T0_08_11(EHF020402T0_08[10]);  //資料格式
					Form.setEHF020402T0_08_12(EHF020402T0_08[11]);  //資料格式
					Form.setEHF020402T0_08_13(EHF020402T0_08[12]);  //資料格式
					Form.setEHF020402T0_08_14(EHF020402T0_08[13]);  //資料格式
					Form.setEHF020402T0_08_15(EHF020402T0_08[14]);  //資料格式
					Form.setEHF020402T0_08_16(EHF020402T0_08[15]);  //資料格式
					Form.setEHF020402T0_08_17(EHF020402T0_08[16]);  //資料格式
					Form.setEHF020402T0_08_18(EHF020402T0_08[17]);  //資料格式
					Form.setEHF020402T0_08_19(EHF020402T0_08[18]);  //資料格式
					Form.setEHF020402T0_08_20(EHF020402T0_08[19]);  //資料格式
					Form.setEHF020402T0_08_21(EHF020402T0_08[20]);  //資料格式
					Form.setEHF020402T0_08_22(EHF020402T0_08[21]);  //資料格式
					Form.setEHF020402T0_08_23(EHF020402T0_08[22]);  //資料格式
					Form.setEHF020402T0_08_24(EHF020402T0_08[23]);  //資料格式
					
					if(!"N".equals(EHF020402T0_08[24])){
						String[] tmp_array = null;
						
						tmp_array = EHF020402T0_08[24].split("\\.");
						
						Form.setEHF020402T0_08_27("."+tmp_array[1]);  //資料格式
						
						tmp_array = tmp_array[0].split("@");
						
						/*int displacement = Integer.parseInt(tmp_array[1]);
						
						Calendar cal = Calendar.getInstance();
						//執行位移
						cal.add(Calendar.DAY_OF_MONTH, displacement);*/
						
						Form.setEHF020402T0_08_26(tmp_array[1]);  //資料格式
						Form.setEHF020402T0_08_25(tmp_array[0]);  //資料格式
						
						Calendar cal = Calendar.getInstance();
						//執行位移
						cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(tmp_array[1]));
						
						Form.setEHF020402T0_08_28(this.CalendarToString(cal));
						
						if(!"-1".equals(tmp_array[1])){
							request.setAttribute("WMSG","請注意!!擷取參數預設應該要是 -1 !!");
						}
					}

					Form.setUSER_CREATE(rs.getString("USER_CREATE"));
					Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
					Form.setVERSION(rs.getInt("VERSION"));
					Form.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
					
				}
				
				request.setAttribute("Form2Datas",list);
//				Form.setEHF010100C(list);
				
				rs.close();
				stmt.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				//判斷身分別
				ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
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
//		return saveData(mapping, form, request, response);
	}

	public ActionForward saveData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF020402M0F Form = (EHF020402M0F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			BA_Vaildate ve=BA_Vaildate.getInstance();
			if("".equals(Form.getEHF020402T0_01())){
				request.setAttribute("MSG","卡鐘擷取序號錯誤!!請重新查詢資料!!");
				return init(mapping, form, request, response);
				
			}

			
			String EHF020402T0_08="";
			
			
			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				
				" UPDATE ehf020402t0 SET  " + 
				" EHF020402T0_02=?, EHF020402T0_03=?, EHF020402T0_04=?, EHF020402T0_05=?, " +
				" EHF020402T0_07=?, EHF020402T0_07_FLG=?, EHF020402T0_08=?, " +
				" USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE EHF020402T0_01 = '"+Form.getEHF020402T0_01()+"' " +
				" AND EHF020402T0_06 = '"+getLoginUser(request).getCompId()+"' " ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, (Form.getEHF020402T0_02_HH()==null?"":Form.getEHF020402T0_02_HH()) +
						  				  (Form.getEHF020402T0_02_MM()==null?"":Form.getEHF020402T0_02_MM()));  //資料擷取時間
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020402T0_03()==null?"":Form.getEHF020402T0_03() ); //檔案名稱
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020402T0_04()==null?"":Form.getEHF020402T0_04()); //擷取檔案路徑
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020402T0_05()); //備註
				indexid++;
//				p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
//				indexid++;
				p6stmt.setString(indexid, Form.getEHF020402T0_07()); //資料來源類型
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF020402T0_07_FLG())); //是否啟用
				indexid++;
				
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_01())==true?"N,":Form.getEHF020402T0_08_01()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_02())==true?"N,":Form.getEHF020402T0_08_02()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_03())==true?"N,":Form.getEHF020402T0_08_03()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_04())==true?"N,":Form.getEHF020402T0_08_04()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_05())==true?"N,":Form.getEHF020402T0_08_05()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_06())==true?"N,":Form.getEHF020402T0_08_06()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_07())==true?"N,":Form.getEHF020402T0_08_07()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_08())==true?"N,":Form.getEHF020402T0_08_08()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_09())==true?"N,":Form.getEHF020402T0_08_09()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_10())==true?"N,":Form.getEHF020402T0_08_10()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_11())==true?"N,":Form.getEHF020402T0_08_11()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_12())==true?"N,":Form.getEHF020402T0_08_12()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_13())==true?"N,":Form.getEHF020402T0_08_13()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_14())==true?"N,":Form.getEHF020402T0_08_14()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_15())==true?"N,":Form.getEHF020402T0_08_15()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_16())==true?"N,":Form.getEHF020402T0_08_16()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_17())==true?"N,":Form.getEHF020402T0_08_17()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_18())==true?"N,":Form.getEHF020402T0_08_18()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_19())==true?"N,":Form.getEHF020402T0_08_19()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_20())==true?"N,":Form.getEHF020402T0_08_20()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_21())==true?"N,":Form.getEHF020402T0_08_21()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_22())==true?"N,":Form.getEHF020402T0_08_22()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_23())==true?"N,":Form.getEHF020402T0_08_23()+",";
				EHF020402T0_08+=this.isnull(Form.getEHF020402T0_08_24())==true?"N,":Form.getEHF020402T0_08_24()+",";
				
				//指定動態檔案名稱
				//格式: [ADDATE@-1.TXT],[CHIDATE@-1.DAT] 
				//-> ADDATE = 西元年, CHIDATE = 民國年
				// @ -> 分隔符號, -1 -> 日期位移量, "." -> 副檔名分隔符號, TXT -> 副檔名
				String temp = "";
				if(!"".equals(Form.getEHF020402T0_08_25()) && Form.getEHF020402T0_08_25() != null &&
						!"".equals(Form.getEHF020402T0_08_27()) && Form.getEHF020402T0_08_27() != null){
					//temp = Form.getEHF020402T0_08_25()+"@"+Form.getEHF020402T0_08_26()+Form.getEHF020402T0_08_27();
					Calendar cur_cal = tools.covertStringToCalendar(tools.ymdTostring(tools.getSysDate()));
					Calendar temp_cal = tools.covertStringToCalendar(Form.getEHF020402T0_08_28());
					//int days = temp_cal.get(Calendar.DAY_OF_MONTH) - cur_cal.get(Calendar.DAY_OF_MONTH);
					int days = this.getDateDiffDays(tools.ymdTostring(tools.getSysDate()), Form.getEHF020402T0_08_28());
					temp = Form.getEHF020402T0_08_25()+"@"+String.valueOf(days)+Form.getEHF020402T0_08_27();
				}
				
				EHF020402T0_08+=this.isnull(temp)==true?"N,":temp+",";
				
				p6stmt.setString(indexid, EHF020402T0_08==null?"":EHF020402T0_08); //資料格式
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());
				indexid++;
				
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
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				return mapping.findForward("success");
			}
		} catch (Exception e) {

			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			
			request.setAttribute("MSG","卡鐘擷取儲存錯誤!!請重新操作!!");
						
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

	/**
	 * 取得 兩個日期相減的天數 
	 * @param date1
	 * @param date2
	 * @return
	 */
	private int getDateDiffDays(String date1, String date2) {
		// TODO Auto-generated method stub
		
		int days = 0;
		
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
			
			Date beginDate = sdf.parse(date1);
			Date endDate = sdf.parse(date2);
			
			long a = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
			
			days = new Long(a).intValue();
			
		}catch (Exception e) {
			
		}
		
		return days;
	}	
	
	/**
	 * 是否為空或是NULL
	 * @param EHF020402t0_08
	 * @return
	 */
	private boolean isnull(String impString) {
		// TODO Auto-generated method stub
		boolean  impStringFK=false;
		
		if(!(impString==null)){
			if("".equals(impString)){
				impStringFK=true;
			}
			
		}else{
			impStringFK=true;
		}
		
		
		
		
		return impStringFK;
	}

	public ActionForward delData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF020402M0F Form = (EHF020402M0F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF020402T0_01(arrid[0]);
		}else if("".equals(Form.getEHF020402T0_01()) || Form.getEHF020402T0_01() == null){
			request.setAttribute("MSG", "請先選擇一筆要刪除的資料!!");
			return queryDatas(mapping, form, request, response);
		}
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			if (lc_errors.isEmpty()) {

					Statement stmt = conn.createStatement();
					String sql = "" +
					" DELETE FROM EHF020402T0 " +
					" WHERE 1=1 " +
					" AND EHF020402T0_01 = '"+Form.getEHF020402T0_01()+"' " +
					" AND EHF020402T0_06 = '"+getLoginUser(request).getCompId()+"' ";
					
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
	 * CardSystem
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward cardSystem(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_CardControl ems_cc_tools = EMS_CardControl.getInstance();
		Connection conn = null;
		EHF020402M0F Form = (EHF020402M0F) form;
		
		try{
			conn = tools.getConnection("SPOS");
			
			//執行門禁控制元件
			Map msgMap = ems_cc_tools.startSystem(conn, Form.getEHF020402T0_02_HH()+Form.getEHF020402T0_02_MM(), getLoginUser(request).getCompId());
			
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"門禁刷卡資料成功匯入: "+(Integer)msgMap.get("OKDATA")+"筆資料!!");
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"門禁刷卡資料共有: "+(Integer)msgMap.get("DATANG")+"筆重複資料未匯入!!");
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"門禁刷卡資料共有: "+(Integer)msgMap.get("CARDNG")+"筆資料未匯入!! 門禁卡號不存在!!");
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"門禁刷卡資料全部共處理: "+(Integer)msgMap.get("ALLDATA")+"筆資料!!");
			
		}catch(Exception e){
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
	 * 清除
	 * @param form
	 * @throws Exception
	 */
	private void cleanAddField( ActionForm form) throws Exception {
		
		EHF020402M0F Form = (EHF020402M0F) form;
		
		// 清掉畫面上的新增明細欄位
		Form.setEHF020402T0_03("");
		
	}
	
	/**
	 * 轉換Calendar to String  format:yyyy/MM/dd
	 */
	protected String CalendarToString( Calendar cal ){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String return_date = ""; 
		
		try{
			return_date = tools.covertDateToString(cal.getTime(), "yyyy/MM/dd");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_date;
	}
}