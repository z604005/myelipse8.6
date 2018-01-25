package com.spon.ems.com.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import vtgroup.ems.common.vo.AuthorizedBean;
import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.ems.com.forms.EHF000500M0F;
import com.spon.ems.com.forms.EX000500R0F;
import com.spon.ems.fileimport.IMP_EHF010201;
import com.spon.utils.sc.actions.SC005A;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.util.BA_Vaildate;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>公司行事曆設定
 *  @version 1.6
 *  @created 29-八月-2013 上午 09:20:12 整理
 *  */
public class EHF000500M0A extends NewDispatchAction {
	protected final BA_TOOLS tools = BA_TOOLS.getInstance();
	public EHF000500M0A() {

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
		EHF000500M0F Form = (EHF000500M0F) form;
		Savepoint savp = null;

		try {
			conn = tools.getConnection("SPOS");
			savp = conn.setSavepoint();
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
				
				AuthorizedBean authbean = getLoginUser(request);
				
				//新增表單資訊
				String sql = " " +
				" insert into EHF000500T0 ( " +
				" EHF000500T0_02,EHF000500T0_03,EHF000500T0_04,EHF000500T0_05,EHF000500T0_06," +
				" EHF000500T0_07,EHF000500T0_08,EHF000500T0_09,EHF000500T0_10,EHF000500T0_11,EHF000500T0_12," +
				" USER_CREATE,USER_UPDATE,VERSION,DATE_UPDATE) " +
				" values(?,?,?,?,?,?,?,?,?,?,?,?,?,1,NOW()) ";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, authbean.getCompId());  //公司組織代號
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_03()); //年度
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_04()); //休假類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_05());  //起始日期
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_06());  //結束日期
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_07_HH()+Form.getEHF000500T0_07_MM());  //休假時間(起)
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_08_HH()+Form.getEHF000500T0_08_MM());  //休假時間(迄)
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_09());
				indexid++;
				p6stmt.setString(indexid, authbean.getEmployeeID());  //處理人事
				indexid++;
				p6stmt.setString(indexid, authbean.getCompId());  //公司代碼
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_12());  //大小禮拜    ""：不選擇，1：大禮拜，2：小禮拜
				indexid++;
				p6stmt.setString(indexid, authbean.getUserName());  //使用員工姓名
				indexid++;
				p6stmt.setString(indexid, authbean.getUserName());  //使用員工姓名
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
				//更新資料庫
				conn.commit();
				
				
				
				int now = Integer.valueOf(tools.covertDateToString(Calendar.getInstance().getTime(), "yyyyMM"));//取得當前時間
				int chk = Integer.valueOf((Form.getEHF000500T0_05().replace("/", "")).substring(0, 6));
				
				
				//劦佑不需要這一段
//				if(now>=chk){
//				//取得系統參數 - 是否將工作表與EMS行事曆同步
//				String work_schedule_generator = tools.getSysParam(conn, authbean.getCompId(), "SYNC_WORK_SCHEDULE_WITH_EMS_CALENDAR");
//				
//					if("ON".equals(work_schedule_generator)){
//						//先檢查休假日期是否已經有排班表，沒有則先產生排班表   Alvin 修改 2014/07/01
//						this.Add_Scheduling_table(conn, request, Form.getEHF000500T0_05(), authbean.getUserId(), authbean.getCompId(), authbean.getUserName(), "", "");
//					
//						//修改公司排班表資料 edit by joe 2012/07/23  
//						EMS_Work_Schedule_Table ems_work_schedule_tools = new EMS_Work_Schedule_Table(authbean.getUserName(), authbean.getCompId());
//						//新增工作表假日設定
//						ems_work_schedule_tools.chgCompScheduleHolidayByDateRange(conn, Form.getEHF000500T0_05(), Form.getEHF000500T0_06(), true,
//																		      authbean.getUserId());  
//					}
//				}
				//更新資料庫
				conn.commit();
				
				//取得下拉選單資料
				generateSelectBox(conn, Form, request);
				
				request.setAttribute("MSG", "新增成功");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				// 清掉畫面上的新增
			
			} else {
				
				request.setAttribute("button", "save");
				
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				if (request.getAttribute("Form1Datas") != null) {
					form = (EHF000500M0F) request.getAttribute("Form1Datas");
				}
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));

				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF010201M0A.addData() " + e);
			e.printStackTrace();
			try {
				conn.rollback(savp);
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

		return queryForm(mapping, form, request, response);//20131017  更改return方向   BY賴泓錡
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
		
		//產生年度下拉選單
		try{
			List datas = new ArrayList();
			for(int i=100;i<111;i++){
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(String.valueOf(i));
				bform.setItem_value(String.valueOf(i));
				datas.add(bform);
			}
			request.setAttribute("EHF000500T0_03_list", datas);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//產生(時)的下拉選單
		try{
		List listHOUR=new ArrayList();
		DecimalFormat df=new DecimalFormat("00");
		for (int i = 0; i < 24; i++) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listHOUR.add(bform);
		}
		request.setAttribute("listEHF000500T0_07_HH",listHOUR);

		//產生(分)的下拉選單
		List listMINUTE=new ArrayList();
		
		for (int i = 0; i < 60; i= i+5) {
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(df.format(i));
			bform.setItem_value(df.format(i));
			listMINUTE.add(bform);
		}
		request.setAttribute("listEHF000500T0_07_MM",listMINUTE);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
		
		List Vacationlist = this.getVacationlist(conn,request);
		
		request.setAttribute("Vacationlist",Vacationlist);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//大小禮拜    ""：不選擇，1：大禮拜，2：小禮拜
		try{
			
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("0");
			bform.setItem_value("");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("大禮拜");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("2");
			bform.setItem_value("小禮拜");
			datas.add(bform);
			
			request.setAttribute("EHF000500T0_12_list", datas);
		}catch(Exception e){
				e.printStackTrace();
		}
		
		
	}

	
	
	/**
	 * 取得休假類別資料清單
	 * @param conn
	 * @param request 
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public List getVacationlist( Connection conn, HttpServletRequest request ){
		
		List return_list = new ArrayList();
		
		try{

			String sql = "" +
			" SELECT " +
			" EHF000500T1_01 AS item_id, " +
			" EHF000500T1_03 AS item_value " +
			" FROM EHF000500T1 " +
			" WHERE 1=1 " +
			" AND EHF000500T1_04 = 1 " +
			" AND EHF000500T1_08 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("==請選擇==");
			return_list.add(bform);
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("item_id"));
				bform.setItem_value(rs.getString("item_value"));
				return_list.add(bform);
			}
			rs.close();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_list;
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
		EHF000500M0F Form = (EHF000500M0F) form;
		
		try{
			
			conn = tools.getConnection("SPOS");
			Form = new EHF000500M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
			
			Form.setEHF000500T0_03(tools.getSysYY()+"");
			
			//設定請假區間初始值
//			Form.setEHF000500T0_05(tools.ymdTostring(tools.getSysDate()));
//			Form.setEHF000500T0_06(tools.ymdTostring(tools.getSysDate()));
	
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "init");
			request.setAttribute("collection", "show");
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
		
	    request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
	}
	public ActionForward init_sava(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF000500M0F Form = (EHF000500M0F) form;
		
		try{
			
			conn = tools.getConnection("SPOS");
			Form = new EHF000500M0F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
			
			Form.setEHF000500T0_03(tools.getSysYY()+"");
			
			//設定請假區間初始值
//			Form.setEHF000500T0_05(tools.ymdTostring(tools.getSysDate()));
//			Form.setEHF000500T0_06(tools.ymdTostring(tools.getSysDate()));
	
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "save");
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			}catch(Exception e){
				
			}
	    }
	    request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
	}

	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		//HR_TOOLS HR_tools = HR_TOOLS.getInstance();
		
		EHF000500M0F Form = (EHF000500M0F) form;
		ArrayList list = new ArrayList();
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			if (lc_errors.isEmpty()) {
				
				Map return_map = new HashMap();
				
				this.getVacation(conn,return_map,getLoginUser(request).getCompId());
				
				String sql = "" +
				" SELECT EHF000500T0.*, " +
				" DATE_FORMAT(EHF000500T0_05, '%Y年%c月%e日') AS START_DATE, " +
				" DATE_FORMAT(EHF000500T0_06, '%Y年%c月%e日') AS END_DATE " +
				" FROM EHF000500T0 " +
				" WHERE 1=1 " +
				" AND EHF000500T0_11 = '"+getLoginUser(request).getCompId()+"' " ;
				
				//年度
				if(!"".equals(Form.getEHF000500T0_03()) && Form.getEHF000500T0_03()!=null ){
					sql += " AND EHF000500T0_03 = '"+Form.getEHF000500T0_03()+"' ";
				}
				//休假類別
				if(!"".equals(Form.getEHF000500T0_04()) && Form.getEHF000500T0_04()!=null ){
					sql += " AND EHF000500T0_04 = '"+Form.getEHF000500T0_04()+"' ";
				}
				//休假日期區間  
				if(!"".equals(Form.getEHF000500T0_05()) && Form.getEHF000500T0_05() != null && !"".equals(Form.getEHF000500T0_06()) && Form.getEHF000500T0_06() !=null ){
					sql += " AND EHF000500T0_05 >= '"+Form.getEHF000500T0_05()+"' " +
						   " AND EHF000500T0_06 <= '"+Form.getEHF000500T0_06()+"' " ;  
				}else if(!"".equals(Form.getEHF000500T0_06()) && Form.getEHF000500T0_06() != null){
					sql += " AND EHF000500T0_06 <= '"+Form.getEHF000500T0_06()+"' ";
				}else if(!"".equals(Form.getEHF000500T0_05()) && Form.getEHF000500T0_05() != null){
					sql += " AND EHF000500T0_05 >= '"+Form.getEHF000500T0_05()+"' ";
				}
				
				//
				if((Integer.valueOf(Form.getEHF000500T0_12()))>0  ){
					sql += " AND EHF000500T0_12 = '"+Form.getEHF000500T0_12()+"' ";
				}
				
				
				sql += " ORDER BY EHF000500T0_03, EHF000500T0_05, EHF000500T0_07, " +
					   " EHF000500T0_06, EHF000500T0_08, EHF000500T0_04 " ;
					   //" LIMIT 30 ";

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				ArrayList getVacationlist = new ArrayList();
				if(rs.next()){
					
					Map CompMap = hr_tools.getCompNameMap(conn, getLoginUser(request).getCompId());
					
					rs.beforeFirst();
					
					while(rs.next()){
						
						EHF000500M0F bean = new EHF000500M0F();
						bean.setEHF000500T0_01(rs.getString("EHF000500T0_01"));  //行事曆序號
						bean.setEHF000500T0_02(  CompMap.get("COMP_NAME_C").toString() );  //公司名稱
						bean.setEHF000500T0_03(rs.getString("EHF000500T0_03"));
						//bean.setEHF000500T0_04(rs.getString("EHF000500T0_04"));//休假類別
						if(return_map.get((String)rs.getString("EHF000500T0_04"))!=null){
							getVacationlist=(ArrayList)return_map.get((String)rs.getString("EHF000500T0_04"));
							bean.setEHF000500T0_04((String)getVacationlist.get(1));//休假類別
						}
						bean.setEHF000500T0_05(rs.getString("START_DATE")+" "+rs.getString("EHF000500T0_07").substring(0, 2)+"點 " +
								rs.getString("EHF000500T0_07").substring(2, 4)+"分 "+ " ~ " +rs.getString("END_DATE")+" " +
								rs.getString("EHF000500T0_08").substring(0, 2)+"點 "+rs.getString("EHF000500T0_08").substring(2, 4)+"分 ");
						bean.setEHF000500T0_09(rs.getString("EHF000500T0_09"));
						list.add(bean);
					}
					if(!"".equals(request.getAttribute("MSG"))&&request.getAttribute("MSG")!=null)
						request.setAttribute("MSG", request.getAttribute("MSG")+"!! "+" 查詢成功!!");
					else
						request.setAttribute("MSG", "查詢成功!!");
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
				//清除
				this.cleanAddField(Form);
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("button", "query");
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("Form2Datas",list);
				request.setAttribute("collection", "show");
				return mapping.findForward("success");
			}
			
			hr_tools.close();
			
		} catch (Exception e) {
			request.setAttribute("MSG", "查詢失敗!");
			System.out.println("EHF010201M0A.queryDatas() " + e);
			System.out.println(e);
			request.setAttribute("button", "query");
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			request.setAttribute("collection", "show");
			return mapping.findForward("success");
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//Form = new EHF000500M0F();
		//Form.setEHF000500T0_03(tools.getSysYY()+"");
		request.setAttribute("Form1Datas", Form);
		
		return mapping.findForward("success");
	}

	/**
	 * 取得休假類別
	 * @param conn
	 * @param returnMap
	 * @param compId
	 */
	private void getVacation(Connection conn, Map returnMap, String compId) {
		// TODO Auto-generated method stub
		ArrayList list = new ArrayList();
		String sql = "" +
		" SELECT * " +
		" FROM EHF000500T1 " +
		" WHERE 1=1 " +
		" AND EHF000500T1_08 = '"+compId+"' " ;
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				list = new ArrayList();
				list.add((String)rs.getString("EHF000500T1_01"));
				list.add((String)rs.getString("EHF000500T1_03"));
				returnMap.put((String)rs.getString("EHF000500T1_01"), list);
			
			
			}
			rs.close();
			stmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		request.getSession().setAttribute("Pageid",null);
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
		
		EHF000500M0F Form = (EHF000500M0F) form;
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		//HR_TOOLS HR_tools = HR_TOOLS.getInstance();
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF000500T0_01(arrid[0]);
		}else if("".equals(Form.getEHF000500T0_01()) || Form.getEHF000500T0_01() == null){
			request.setAttribute("MSG", "請先選擇一筆要修改的資料!!");
			return queryDatas(mapping, form, request, response);
		}
    	
    	try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "editDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			HR_TOOLS hr_tools = new HR_TOOLS();

			if (lc_errors.isEmpty()) {
				
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT EHF000500T0.*, " +
				" DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS START_DATE, " +
				" DATE_FORMAT(EHF000500T0_06, '%Y/%m/%d') AS END_DATE " +
				" FROM EHF000500T0 " +
				" WHERE 1=1 " +
				" AND EHF000500T0_01 = '"+Form.getEHF000500T0_01()+"' " +  //行事曆序號
				" AND EHF000500T0_11 = '"+getLoginUser(request).getCompId()+"' " ;
				

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					Map CompMap = hr_tools.getCompNameMap(conn, getLoginUser(request).getCompId());
					//Map empMap = HR_tools.getEmpNameMap(conn, getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
					
					Form.setEHF000500T0_01(rs.getString("EHF000500T0_01"));  //行事曆序號
					Form.setEHF000500T0_02(CompMap.get("COMP_NAME_C").toString());  //公司名稱
					Form.setEHF000500T0_03(rs.getString("EHF000500T0_03"));  //年度
					Form.setEHF000500T0_04(rs.getString("EHF000500T0_04"));  //休假類別
					Form.setEHF000500T0_05(rs.getString("START_DATE"));  //休假日期(起)
					Form.setEHF000500T0_06(rs.getString("END_DATE"));    //休假日期(迄)
					if(!"".equals((rs.getString("EHF000500T0_07"))) && (rs.getString("EHF000500T0_07")) != null ){
						Form.setEHF000500T0_07_HH(rs.getString("EHF000500T0_07").substring(0, 2));  //休假時間 (時/分) (起)
						Form.setEHF000500T0_07_MM(rs.getString("EHF000500T0_07").substring(2, 4));
					}else{
						Form.setEHF000500T0_07_HH("");  //休假時間 (時/分) (起)
						Form.setEHF000500T0_07_MM("");
					}
					if(!"".equals((rs.getString("EHF000500T0_08"))) && (rs.getString("EHF000500T0_08")) != null ){
						Form.setEHF000500T0_08_HH(rs.getString("EHF000500T0_08").substring(0, 2));  //休假時間 (時/分) (迄)
						Form.setEHF000500T0_08_MM(rs.getString("EHF000500T0_08").substring(2, 4));
					}else{
						Form.setEHF000500T0_08_HH("");  //休假時間 (時/分) (迄)
						Form.setEHF000500T0_08_MM("");
					}
					Form.setEHF000500T0_09(rs.getString("EHF000500T0_09"));  //公司休假原因
					Form.setEHF000500T0_10("A999");  //處理人事
					Form.setEHF000500T0_12(rs.getString("EHF000500T0_12"));  
					Form.setUSER_CREATE(rs.getString("USER_CREATE"));
					Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
					Form.setVERSION(rs.getInt("VERSION"));
					Form.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
					
				}
			
				request.setAttribute("Form2Datas",list);
				rs.close();
				stmt.close();

				String dayStart[]=Form.getEHF000500T0_05().split("/");
				String dayEnd[]  =Form.getEHF000500T0_06().split("/");
//				sql = "" +
//				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
//				" FROM EHF020900T0 " +
//				" WHERE 1=1 " +
//				"  AND (EHF020900T0_M2 BETWEEN '"+dayStart[0]+"/"+dayStart[1]+ "' AND '"+dayEnd[0]+"/"+dayEnd[1]+"')"+	//考勤年月
//				
//				" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
//				" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤
//
//		    	Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//				ResultSet rs_01 = stmt_01.executeQuery(sql);
//				
//				if(rs_01.next()){
//					FormUtils.setFormDisplayMode(request, Form, "inspect");
//					
//				}else{
//					FormUtils.setFormDisplayMode(request, Form, "edit");
//				}
//				rs_01.close();
//				stmt_01.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("chk_type", "yes");
				request.setAttribute("button", "edit");
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
			
			hr_tools.close();
			
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
	 * 儲存修改資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Savepoint savp = null;
		Connection conn = null;
		EHF000500M0F Form = (EHF000500M0F) form;
		
		try {
			AuthorizedBean authbean = getLoginUser(request);
			conn = tools.getConnection("SPOS");
			//記錄點
			savp = conn.setSavepoint();
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			
			BA_Vaildate ve = BA_Vaildate.getInstance();
			if("".equals(Form.getEHF000500T0_01())){
				request.setAttribute("MSG","行事曆資料序號錯誤!!請重新查詢資料!!");
				return init(mapping, form, request, response);
				
			}

			if (lc_errors.isEmpty()) {
				
				//查詢修改前的行事曆資料
				String sql = "";
				sql = "" +
				" SELECT EHF000500T0.*, " +
				" DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS START_DATE, " +
				" DATE_FORMAT(EHF000500T0_06, '%Y/%m/%d') AS END_DATE " +
				" FROM EHF000500T0 " +
				" WHERE 1=1 " +
				" AND EHF000500T0_01 = '"+Form.getEHF000500T0_01()+"' " +  //行事曆序號
				" AND EHF000500T0_11 = '"+getLoginUser(request).getCompId()+"' " ;
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				String EHF000500T0_05 = "";
				String EHF000500T0_06 = "";
				
				if(rs.next()){
					EHF000500T0_05 = rs.getString("START_DATE");  //休假日期(起)
					EHF000500T0_06 = rs.getString("END_DATE");    //休假日期(迄)
				}
				rs.close();
				stmt.close();
				
				//執行更新
				sql = "" +
				" UPDATE EHF000500T0 SET " +
				" EHF000500T0_02=?,EHF000500T0_03=?,EHF000500T0_04=?,EHF000500T0_05=?,"+
				" EHF000500T0_06=?,EHF000500T0_07=?,EHF000500T0_08=?,EHF000500T0_09=?,"+
				" EHF000500T0_10=?,EHF000500T0_12=?,USER_UPDATE=?,VERSION=VERSION+1,DATE_UPDATE=NOW()"+
				" where 1=1 " +
				" AND EHF000500T0_01 = '"+Form.getEHF000500T0_01()+"' " +
				" AND EHF000500T0_11 = ? ";  //公司代碼

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, authbean.getCompId());  //公司組織代號
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_03()); //年度
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_04()); //休假類別
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_05());  //休假日期(起)
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_06());  //休假日期(迄)
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_07_HH()+Form.getEHF000500T0_07_MM());  //休假時間(起)
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_08_HH()+Form.getEHF000500T0_08_MM());  //休假時間(迄)
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_09());
				indexid++;
				p6stmt.setString(indexid, authbean.getEmployeeID());  //處裡人事
				indexid++;
				p6stmt.setString(indexid, Form.getEHF000500T0_12());  //大小禮拜    ""：不選擇，1：大禮拜，2：小禮拜
				indexid++;
				p6stmt.setString(indexid, authbean.getUserName());  //使用者
				indexid++;
				p6stmt.setString(indexid, authbean.getCompId());  //公司代碼
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				
				//更新資料庫
				conn.commit();
				
				//劦佑不需要這一段
//				//取得系統參數 - 是否將工作表與EMS行事曆同步
//				String work_schedule_generator = ems_tools.getSysParam(conn, authbean.getCompId(), "SYNC_WORK_SCHEDULE_WITH_EMS_CALENDAR");
//				if("ON".equals(work_schedule_generator)){
//					
//					//先檢查休假日期是否已經有排班表，沒有則先產生排班表   Alvin 修改 2014/07/01
// 					this.Add_Scheduling_table(conn, request, Form.getEHF000500T0_05(), authbean.getUserId(), authbean.getCompId(), authbean.getUserName(), "", "");
//
//					//修改公司排班表資料 edit by joe 2012/08/06
//					EMS_Work_Schedule_Table ems_work_schedule_tools = new EMS_Work_Schedule_Table(authbean.getUserName(), authbean.getCompId());
//					//取消工作表假日設定
//					ems_work_schedule_tools.chgCompScheduleHolidayByDateRange(conn, EHF000500T0_05, EHF000500T0_06, false, 
//											                                  authbean.getUserId());  
//					//新增工作表假日設定
//					ems_work_schedule_tools.chgCompScheduleHolidayByDateRange(conn, Form.getEHF000500T0_05(), Form.getEHF000500T0_06(), true, 
//																			  authbean.getUserId());  
//				}
//				
//				//更新資料庫
//				conn.commit();
				
				//設定前端顯示畫面資料
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
			
			request.setAttribute("MSG","行事曆資料儲存錯誤!!請重新操作!!");
						
			System.out.println(e);
			e.printStackTrace();
			
			//還原資料庫內容
			try {
				conn.rollback(savp);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
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
	
	public ActionForward delData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		Connection conn = null;
		EHF000500M0F Form = (EHF000500M0F) form;
		Savepoint savp = null;
		String NUM="";
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF000500T0_01(arrid[0]);
    		
    		for(int i=0;i<arrid.length;i++){
    			NUM+="'"+arrid[i]+"'";
    			if(i!=arrid.length-1){
    				NUM+=",";
    			}
    		}
    		
    		
    		
		}else if("".equals(Form.getEHF000500T0_01()) || Form.getEHF000500T0_01() == null){
			request.setAttribute("MSG", "請先選擇一筆要刪除的資料!!");
			return queryDatas(mapping, form, request, response);
		}
		
		try {
			String sql = "";
			String EHF000500T0_05 = "";
			String EHF000500T0_06 = "";
			conn = tools.getConnection("SPOS");
			//記錄點
			savp = conn.setSavepoint();
			
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			
			
//			sql = "" +
//			" SELECT EHF000500T0.*, " +
//			" DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS START_DATE, " +
//			" DATE_FORMAT(EHF000500T0_06, '%Y/%m/%d') AS END_DATE " +
//			" FROM EHF000500T0 " +
//			" WHERE 1=1 " +
//			" AND EHF000500T0_01 = '"+Form.getEHF000500T0_01()+"' " +  //行事曆序號
//			" AND EHF000500T0_11 = '"+getLoginUser(request).getCompId()+"' " ;
//			
//			Statement stmt = conn.createStatement();
//			
//			ResultSet rs = stmt.executeQuery(sql);
//			
//			
//			if(rs.next()){
//				EHF000500T0_05 = rs.getString("START_DATE");  //休假日期(起)
//				EHF000500T0_06 = rs.getString("END_DATE");    //休假日期(迄)
//			}
//			rs.close();
//			stmt.close();
	
			
//			String dayStart[]=EHF000500T0_05.split("/");
//			String dayEnd[]  =EHF000500T0_06.split("/");
//			sql = "" +
//			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
//			" FROM EHF020900T0 " +
//			" WHERE 1=1 " +
//			"  AND (EHF020900T0_M2 BETWEEN '"+dayStart[0]+"/"+dayStart[1]+ "' AND '"+dayEnd[0]+"/"+dayEnd[1]+"')"+	//考勤年月
//			
//			" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
//			" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤
//
//	    	Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			ResultSet rs_01 = stmt_01.executeQuery(sql);
//			
//			while(rs_01.next()){
//				if("02".equals(rs_01.getString("EHF020900T0_02"))||"03".equals(rs_01.getString("EHF020900T0_02")))
//					lc_errors.add("",new ActionMessage(""));    
//					request.setAttribute("MSG",(request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+
//							rs_01.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認"+" ");
//				
//			}
//			rs_01.close();
//			stmt_01.close();		

			if (lc_errors.isEmpty()) {
					
				AuthorizedBean authbean = getLoginUser(request);
				
				
				
//				//取得系統參數 - 是否將工作表與EMS行事曆同步
//				String work_schedule_generator = ems_tools.getSysParam(conn, authbean.getCompId(), "SYNC_WORK_SCHEDULE_WITH_EMS_CALENDAR");
//				if("ON".equals(work_schedule_generator)){
//					//修改公司排班表資料 edit by joe 2012/08/06
//					EMS_Work_Schedule_Table ems_work_schedule_tools = new EMS_Work_Schedule_Table(authbean.getUserName(), authbean.getCompId());
//					//取消工作表假日設定
//					ems_work_schedule_tools.chgCompScheduleHolidayByDateRange(conn, EHF000500T0_05, EHF000500T0_06, false, 
//																			  authbean.getUserId());  
//				}
				
				
				Statement stmt_02 = conn.createStatement();
				//執行刪除
				sql = "" +
				" DELETE FROM EHF000500T0 " +
				" WHERE 1=1 " +
				" AND EHF000500T0_01 IN ("+NUM+") " +  //序號
				" AND EHF000500T0_11 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
				
				stmt_02.execute(sql);
				stmt_02.close();
				conn.commit();
				//取得系統參數 - 是否將工作表與EMS行事曆同步
				String work_schedule_generator = tools.getSysParam(conn, authbean.getCompId(), "SYNC_WORK_SCHEDULE_WITH_EMS_CALENDAR");
				
				if("ON".equals(work_schedule_generator)){
					//Alvin 修改 2014/07/01
					//建立排班自動產生元件
					//EMS_Work_Schedule_Generator ems_work_schedule_generator = EMS_Work_Schedule_Generator.getInstance("EMS系統",authbean.getUserId(), authbean.getCompId());
					
					
					//建立考勤產生元件
					//EMS_AttendanceAction att_act_tools = null;
					
					//取得系統當前日期時間
					Calendar cur_system_cal = Calendar.getInstance();
					//設定系統時間
					cur_system_cal.set(Calendar.HOUR_OF_DAY, 8);
					cur_system_cal.set(Calendar.MINUTE, 0);
					cur_system_cal.set(Calendar.SECOND, 0);
					cur_system_cal.set(Calendar.MILLISECOND, 0);
					
					
					
					
					Calendar start_cal = tools.covertStringToCalendar(EHF000500T0_05);  //計算開始日期
					Calendar end_cal = tools.covertStringToCalendar(EHF000500T0_06);  //計算結束日期
					tools.covertDateToString(end_cal.getTime(), "yyyy/MM/dd HH:mm:ss");
					//計算迴圈建立
					boolean chk_run_flag = true;
					while(chk_run_flag){
						if(start_cal.equals(end_cal)){
							chk_run_flag = false;
						}
						//預先刪除排班表資料
						//this.delSchedule(conn, ems_tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd"),true,authbean.getCompId());
						//conn.commit();
						//執行排班資料產生程式
						//ems_work_schedule_generator.generate(conn, start_cal, "","");
						//conn.commit();
						
						//連動考勤資料重新產生, edit by joe 2013/01/23
						//需要判斷日期是否在當前系統日期之前, 若是在當前系統日期之前才可以執行"連動考勤"
						//判斷是否在當前系統日期之前
						if( start_cal.compareTo(cur_system_cal) < 0 ){
							//在系統日期之前
							//需要連動考勤資料
							//使用指定日期變更考勤
							//att_act_tools = EMS_AttendanceAction.getInstance(authbean.getCompId(), 
							//		ems_tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd"), authbean.getUserId() );
							//產生考勤資料
							//att_act_tools.execute_Attendance(conn, true, "");
							
						}else{
							//在系統日期之後
							//不需要連動考勤資料
						}
						
						
						//日期加一天
						start_cal.add(Calendar.DAY_OF_MONTH, 1);
					}
				
					
//					//修改公司排班表資料 edit by joe 2012/08/06
//					EMS_Work_Schedule_Table ems_work_schedule_tools = new EMS_Work_Schedule_Table(authbean.getUserName(), authbean.getCompId());
//					
//					//取消工作表假日設定
//					ems_work_schedule_tools.chgCompScheduleHolidayByDateRange(conn, EHF000500T0_05, EHF000500T0_06, false, 
//											                                  authbean.getUserId());
//	
				}
				conn.commit();
				
				request.setAttribute("MSG", "刪除成功");
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return queryForm(mapping, form, request, response);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			try {
				conn.rollback(savp);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);
	}
	/**
	 * 刪除排班表日期資料
	 * @param conn
	 * @param schedule_no
	 * @param schedule_date
	 * @param holiday_flag
	 * @param class_no
	 * @param comment
	 * @param comp_id
	 * @return
	 */
	public boolean delSchedule( Connection conn, String startCal, boolean holiday_flag ,String comp_id){
		
		boolean chk_flag = false;
		
		try{
			
			//刪除排班表日期資料
			String sql = "" +
			" DELETE FROM ehf010105t1 " +
			" WHERE 1=1 " +
		//	" AND EHF010105T1_01 = ? " +
			" AND EHF010105T1_02 = ? " +
			" AND EHF010105T1_06 = ? ";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			//p6stmt.setInt(indexid, schedule_no);  //員工排班表序號
			//indexid++;
			p6stmt.setString(indexid, startCal);  //排班表日期
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;

			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();			
			
			chk_flag = true;
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
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
	 * 匯入行事曆資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward impEHF000500(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF000500M0F Form = (EHF000500M0F) form;
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
			
			//正確的匯入資料
			List datalist = new ArrayList();
			//錯誤的匯入資料
			List errordatalist = new ArrayList();
			
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			
			//建立行事曆匯入元件
			IMP_EHF010201 imp_ehf010201 = new IMP_EHF010201( authbean.getEmployeeID() );
			
			//取得匯入檔案
			FormFile importfile = Form.getIMP_EHF000501();
			
			//取得回傳訊息Map
			Map msgMap = imp_ehf010201.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(),
												  importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());
		
			//設定前端顯示訊息
			request.setAttribute("MSG","有 "+(msgMap.get("NGDATACOUNT")==null? "0": (Integer)msgMap.get("NGDATACOUNT"))+" 筆資料未匯入!!") ;

			datalist =(List)msgMap.get("DATALIST");
			
			errordatalist =(List)msgMap.get("NGDATALIST");
			
			if( datalist!=null && datalist.size()>0 ){
				request.setAttribute("correct_collection", "show");
				request.setAttribute("Form2Datas",datalist);
				
				//取得系統參數 - 是否將工作表與EMS行事曆同步
				String work_schedule_generator = tools.getSysParam(conn, authbean.getCompId(), "SYNC_WORK_SCHEDULE_WITH_EMS_CALENDAR");
				if("ON".equals(work_schedule_generator)){
					//修改公司排班表資料 edit by joe 2012/08/06
					//EMS_Work_Schedule_Table ems_work_schedule_tools = new EMS_Work_Schedule_Table(authbean.getUserName(), authbean.getCompId());
					
					
					Iterator it = datalist.iterator();
					Map<String, String> dataMap = null;
					int now = Integer.valueOf(tools.covertDateToString(Calendar.getInstance().getTime(), "yyyyMM"));//取得當前時間
					while(it.hasNext()){
						
						//取得資料
						dataMap = (Map) it.next();
						
						int chk = Integer.valueOf((((String)dataMap.get("EHF000500T0_05_year")+"/"
	 							+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_month"), 2)).replace("/", "")).substring(0, 6));
						
						
						if(now>=chk){
						//先檢查休假日期是否已經有排班表，沒有則先產生排班表   Alvin 修改 2014/07/01
	 					//this.Add_Scheduling_table(conn, request, 
	 					//		(String)dataMap.get("EHF000500T0_05_year")+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_month"), 2)+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_day"), 2), 
	 					//		authbean.getUserId(), authbean.getCompId(), authbean.getUserName(), "", "");
						//取消工作表假日設定
						//ems_work_schedule_tools.chgCompScheduleHolidayByDateRange(conn, 
						//		(String)dataMap.get("EHF000500T0_05_year")+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_month"), 2)+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_day"), 2), 
	 					//		(String)dataMap.get("EHF000500T0_05_year")+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_month"), 2)+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_day"), 2),
	 					//		false, authbean.getUserId());  
						//新增工作表假日設定
						//ems_work_schedule_tools.chgCompScheduleHolidayByDateRange(conn, 
						//		(String)dataMap.get("EHF000500T0_05_year")+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_month"), 2)+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_day"), 2), 
	 					//		(String)dataMap.get("EHF000500T0_05_year")+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_month"), 2)+"/"
	 					//		+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_day"), 2),
	 					//		true, authbean.getUserId());  
					}
					}
				}
			}
			if( errordatalist!=null && errordatalist.size()>0 ){
				request.setAttribute("error_collection", "show");
				request.setAttribute("Form3Datas",errordatalist);
			}
			//設定前端顯示資訊
			request.setAttribute("Form1Datas", Form);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "import");
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
		
		return mapping.findForward("success");
	}
	
	
	
	/**
	 * 行事曆匯出
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//HR_TOOLS HR_tools = HR_TOOLS.getInstance();
		EHF000500M0F Form = (EHF000500M0F) form;
		Connection conn = null;
		ActionErrors lc_errors = new ActionErrors();

		// 建立資料庫連線
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
			HR_TOOLS hr_tools = new HR_TOOLS();
			AuthorizedBean authbean = getLoginUser(request);

			// 產生Excel元件
			EX000500R0F ef = new EX000500R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getUserId(), request);

			try {
				Map return_map = new HashMap();
				
				this.getVacation(conn,return_map,getLoginUser(request).getCompId());
				
				int DataCount = 0;
				// 取得薪資計算明細資料
				//List printlist = this.queryListDatas(conn, authbean, Form.getEHF030600T0_01());
				// 取得公司名稱
				Map compMap = (Map) hr_tools.getCompNameMap(conn,authbean.getCompId());
				
				hr_tools.close();
				// 列印報表表頭
				ef.printHeadValue((String)(compMap.get("COMP_NAME_C"))+"行事曆");
				// 列印報表
				DataCount = ef.print(conn,  Form.getEHF000500T0_03(),authbean.getCompId(),return_map);

				if (DataCount > 0) {// 表示有資料
					
					String number = new SC005A().getSequence(conn, "EX000500R0F", "EX000500R0F", "0", getLoginUser(request).getCompId());
					String name ="公司行事曆"+tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+"("+number+").xls";
					response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("BIG5"), "iso8859-1"));
					ServletOutputStream ouputStream;
					try {
						InputStream in = new FileInputStream(ef.write());
						ouputStream = response.getOutputStream();
						int bit = 0;
						byte [] bits=new byte[4096];
						while ((bit=in.read(bits)) >-1 ) {
							ouputStream.write(bits,0,bit);
							Thread.sleep(1);
						}
						ouputStream.flush();
						ouputStream.close();
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					return null;
				} else {
					saveErrors(request, lc_errors);
					request.setAttribute("MSG", "沒有資料可列印!!");
				}
			} catch (Exception E) {
				E.printStackTrace();
				request.setAttribute("MSG", "列印失敗!!");
			} finally {
				// ef.write();
			}

		} catch (Exception E) {
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
	 * 下載空白範例匯入檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print_example(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		BA_TOOLS tool = BA_TOOLS.getInstance();
		try {			
			// 存入檔案
			String FileName = getServlet().getServletContext().getRealPath("")+"/excelrpt/"+"//" + "EHF010201R0_example.xls"; 
			String name ="行事曆(範本)"+tool.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("BIG5"), "iso8859-1"));
			ServletOutputStream ouputStream;
			try {
				InputStream in = new FileInputStream(FileName);
				ouputStream = response.getOutputStream();
				int bit = 0;
				byte [] bits=new byte[4096];
				while ((bit=in.read(bits)) >-1 ) {
					ouputStream.write(bits,0,bit);
					Thread.sleep(1);
				}
				ouputStream.flush();
				ouputStream.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		
		} catch (Exception E) {
			E.printStackTrace();
			request.setAttribute("MSG", "列印失敗!!");
		} finally {
			// ef.write();
		}

		return this.init(mapping, form, request, response);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	/**
	 * 清除
	 * @param form
	 * @throws Exception
	 */
	private void cleanAddField( ActionForm form) throws Exception {
		
		EHF000500M0F Form = (EHF000500M0F) form;
		
		// 清掉畫面上的新增明細欄位
		Form.setEHF000500T0_04("");
		Form.setEHF000500T0_05("");
		Form.setEHF000500T0_06("");
		Form.setEHF000500T0_07_HH("00");
		Form.setEHF000500T0_07_MM("00");
		Form.setEHF000500T0_08_HH("00");
		Form.setEHF000500T0_08_MM("00");
		
		
	}
	/**
	 * 執行排班資料產生程式
	 * @param conn
	 * @param request
	 * @param Date
	 * @param userId
	 * @param compId
	 * @param userName
	 * @param DEPT_ID
	 * @param number 
	 * @param list 
	 * @param string 
	 */
//	protected void Add_Scheduling_table(Connection conn, HttpServletRequest request, String Date,  String userId,
//			                            String compId, String userName, String DEPT_ID, String number ) {
//		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
//		
//		String date = Date;  //日期起
//		String start_date = "";
//		String end_date = "";
//		Calendar start_cal = null;
//		Calendar end_cal = null;
//		
//		AuthorizedBean authbean = getLoginUser(request);
//		boolean run_flag = false;
//		try{
//			
//			//傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
//			start_date = ems_tools.convertADDateToStrng(ems_tools.getFirstMonthDay(ems_tools.covertStringToCalendar(date)));
//			//傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
//			end_date = ems_tools.convertADDateToStrng(ems_tools.getLastMonthDay(ems_tools.covertStringToCalendar(date)));
//			
//			start_cal = ems_tools.covertStringToCalendar(start_date);  //考勤計算開始日期
//			end_cal = ems_tools.covertStringToCalendar(end_date);  //考勤計算結束日期
//			
//			String []day_start=start_date.split("/");
//			//查詢排班表
//			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//			
//			String	sql = "" +
//					" SELECT * " +
//					" FROM EHF010105T0 " +
//					" WHERE 1 = 1 " +
//					" AND EHF010105T0_03 = '"+day_start[0]+"'"+
//					" AND EHF010105T0_04 = '"+day_start[1]+"'" +
//					" AND EHF010105T0_06 = '"+authbean.getCompId()+"'";
//				
//					//執行SQL
//			ResultSet rs = stmt.executeQuery(sql);
//			if(!rs.next()){
//				run_flag = true;						
//			}
//			rs.close();
//			stmt.close();
//			
//			
//			if(run_flag){
//				//建立排班自動產生元件
//				EMS_Work_Schedule_Generator ems_work_schedule_generator = EMS_Work_Schedule_Generator.getInstance(
//						"EMS系統",authbean.getUserId(), authbean.getCompId());
//				boolean chk_run_flag = true;
//			
//				while(chk_run_flag){
//					if(start_cal.equals(end_cal)){
//						chk_run_flag = false;
//					}
//					if("0002".equals(number)){
//						//執行預設排班資料產生程式
//						ems_work_schedule_generator.Default_generate(conn, start_cal,"",DEPT_ID);
//					}else{
//						//執行排班資料產生程式
//						ems_work_schedule_generator.generate(conn, start_cal, "",DEPT_ID);
//					}
//					start_cal.add(Calendar.DAY_OF_MONTH, 1);
//				}
//			}
//			conn.commit();
//			}catch(Exception e){
//				System.out.println("公司排班表產生失敗");
//				e.printStackTrace();
//			}			
//			System.out.println("公司排班表產生成功");	
//	}
	
	
	
	
}