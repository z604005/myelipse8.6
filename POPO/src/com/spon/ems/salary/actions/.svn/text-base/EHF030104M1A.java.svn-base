package com.spon.ems.salary.actions;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
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

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.ems.salary.forms.EHF030104M1F;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.HR_TOOLS;


import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>勞保等級維護
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030104M1A extends NewDispatchAction {

	public EHF030104M1A() {

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
		EHF030104M1F Form = (EHF030104M1F) form;

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
					" INSERT INTO ehf030104t0 ( " +
					" EHF030104T0_02 ," +//EHF030104T0_02 勞保版本代碼  20140120修改 BY 賴泓錡
					" EHF030104T0_02_VERSION," +//EHF030104T0_02_VERSION 勞保版本名稱 20140120修改 BY 賴泓錡
					" EHF030104T0_03 ,EHF030104T0_04 ," +
					" EHF030104T0_05 ," +
					" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
					" VALUES (?,?,?,?,?,?,?, 1, NOW() ) "  ;
					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
					p6stmt.setString(indexid, Form.getEHF030104T0_02());  //年度(yy)
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030104T0_02_VERSION());  //
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司組織(代碼)
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030104T0_04());  //備註
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //建立人員
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
					indexid++;
				
					p6stmt.executeUpdate();
					
					sql = "SELECT LAST_INSERT_ID() AS ID FROM ehf030104t0 ";
					
					Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs=stmt.executeQuery(sql);
					if(rs.next()){
						Form.setEHF030104T0_01(rs.getString("ID"));
						//System.out.println("ID==>"+rs.getShort("ID"));
					}
					
					rs.close();
					stmt.close();
					pstmt.close();
					p6stmt.close();			

					conn.commit();
					generateSelectBox(conn, Form, request);
					
					request.setAttribute("MSG", "新增成功");
					request.setAttribute("button", "edit");
					request.setAttribute("collection", "show");
					request.setAttribute("SHOW", "YES");//讓頁面顯示可編輯狀態  20140120修改 BY 賴泓錡
					// 清掉畫面上的新增
			
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				request.setAttribute("SHOW", "YES");//讓頁面顯示可編輯狀態  20140120修改 BY 賴泓錡
//				return init(mapping, form, request, response);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF030104M1A.addData() " + e);
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

		return queryDatas_forADD(mapping, form, request, response);
//		return mapping.findForward("success");
//		return init(mapping, form, request, response);
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
		BaseFunction base_tools= new BaseFunction();
		EHF030104M1F Form = (EHF030104M1F) form;
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
					" INSERT INTO ehf030104t1 ( EHF030104T1_01 ,EHF030104T1_02 ,EHF030104T1_03, " +
					" EHF030104T1_04 ,EHF030104T1_05 ,EHF030104T1_06 ,EHF030104T1_07 ,EHF030104T1_08, EHF030104T1_09," +
					" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
					" VALUES (?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) "  ;

					
					
					//新增勞保等級明細序號    20140722  BY Alvin
					//取得順序號碼
					int EHF030104T1_02 = 
					base_tools.getMaxSN("EHF030104T1_02", "EHF030104T1", 
					" AND EHF030104T1_01 = '"+Form.getEHF030104T0_01()+"' " +
					" AND EHF030104T1_08 = '"+getLoginUser(request).getCompId()+"' ");
					
					
					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
					int indexid = 1;
					p6stmt.setString(indexid, Form.getEHF030104T0_01());  //勞保等級序號
					indexid++;
					p6stmt.setInt(indexid, EHF030104T1_02);  //勞保等級明細序號
					indexid++;
					p6stmt.setString(indexid, Form.getEHF030104T1_03()==""?"0":Form.getEHF030104T1_03());  //勞保投保級距
					indexid++;
					
					String EHF030104T1_04 = Form.getEHF030104T1_04();
					
					if("".equals(EHF030104T1_04) || EHF030104T1_04 == null){
						EHF030104T1_04 = "0";
					}
					
					//p6stmt.setString(indexid, Form.getEHF030104T1_04()==""?"0":Form.getEHF030104T1_04());  //個人負擔
					p6stmt.setString(indexid, EHF030104T1_04);  //個人負擔
					indexid++;
					
					String EHF030104T1_05 = Form.getEHF030104T1_05();
					
					if("".equals(EHF030104T1_05) || EHF030104T1_05 == null){
						EHF030104T1_05 = "0";
					}
					
					//p6stmt.setString(indexid, Form.getEHF030104T1_05()==""?"0":Form.getEHF030104T1_05());  //雇主負擔
					p6stmt.setString(indexid, EHF030104T1_05);  //雇主負擔
					indexid++;
					
					String EHF030104T1_06 = Form.getEHF030104T1_06();
					
					if("".equals(EHF030104T1_06) || EHF030104T1_06 == null){
						EHF030104T1_06 = "0";
					}
					
					//p6stmt.setString(indexid, Form.getEHF030104T1_06()==""?"0":Form.getEHF030104T1_06());  //合計金額
					p6stmt.setString(indexid, EHF030104T1_06);  //合計金額
					indexid++;
					
					p6stmt.setString(indexid, Form.getEHF030104T1_07());  //備註
					indexid++;
					p6stmt.setString(indexid, getLoginUser(request).getCompId());  //公司代碼
					indexid++;
					p6stmt.setString(indexid, "0");  //勞保投保等級(顯示序號)    20140722  BY Alvin
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
					Form.setEHF030104T1_02("");
					Form.setEHF030104T1_03("0");
					Form.setEHF030104T1_04("0");
					Form.setEHF030104T1_05("0");
					Form.setEHF030104T1_06("0");
					Form.setEHF030104T1_07("");
			
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				generateSelectBox(conn, Form, request);
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("SHOW", "YES");
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
//				return init(mapping, form, request, response);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增明細失敗!");
			System.out.println("EHF030104M1A.addDetailData() " + e);
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
//		return mapping.findForward("success");
//		return init(mapping, form, request, response);
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
		
		//產生年度
		try{
			List EHF030104T0_02_list=new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			EHF030104T0_02_list.add(bform);
			DecimalFormat df=new DecimalFormat("00");
			for (int i = 111; i > 99; i--) {
				bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				EHF030104T0_02_list.add(bform);
			}
			request.setAttribute("EHF030104T0_02_list",EHF030104T0_02_list);

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
			EHF030104M1F Form = new EHF030104M1F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//Map compMap = ems_tools.getCompNameMap(getEmscontext(), getLoginUser(request).getUserId());
			//Form.setEHF030104T0_03( (String) ((Map)compMap.get(getLoginUser(request).getCompId())).get("COMP_NAME_C") );
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "");
			request.setAttribute("collection", "");
			request.setAttribute("SHOW", "YES");//讓頁面顯示可編輯狀態  20140120修改 BY 賴泓錡
			
			hr_tools.close();
			
//			FormUtils.setFormDisplayMode(request, form, "create");
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
		EHF030104M1F Form = (EHF030104M1F) form;
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030104T0_01(arrid[0]);
		}
    	
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			String EHF030104T0_06="";
			HR_TOOLS hr_tools = new HR_TOOLS();

			if (lc_errors.isEmpty()) {
				
				//重排加班明細的順序號碼
				String[] key = {"EHF030104T1_01","EHF030104T1_08"};
				this.reDoSN(
				key, 
				" EHF030104T1_03", "EHF030104T1", 
				" AND EHF030104T1_01 = '"+Form.getEHF030104T0_01()+"' " +
				" AND EHF030104T1_08 = '"+getLoginUser(request).getCompId()+"' ",request);

				ArrayList list = new ArrayList();
				
				Map SafetyMap= new HashMap();
				this.getAllSafety(conn, Form.getEHF030104T0_01(), request, SafetyMap);
				
				
				String sql = "" +
				" SELECT EHF030104T0.* " +
				" ,EHF030104T1_01 ,EHF030104T1_02 ,EHF030104T1_03 ,EHF030104T1_04 ,EHF030104T1_05" +
				" ,EHF030104T1_06 ,EHF030104T1_07 ,EHF030104T1_09" +
				" FROM EHF030104T0 " +
				" LEFT JOIN EHF030104T1 ON EHF030104T1_01 = EHF030104T0_01 AND EHF030104T1_08 = EHF030104T0_05 " +
				" WHERE 1=1 " +
				" AND EHF030104T0_01 = '"+Form.getEHF030104T0_01()+"' " +  //勞保等級序號
				" AND EHF030104T0_05 = '"+getLoginUser(request).getCompId()+"' " ;
	
				sql += " ORDER BY EHF030104T0_01 , EHF030104T0_02_VERSION , EHF030104T0_03 ,EHF030104T1_01 ,EHF030104T1_03 ";

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				//Map compMap = ems_tools.getCompNameMap(getEmscontext(), getLoginUser(request).getUserId());
				
				String EHF030104T0_01 = "";
				
				while(rs.next()){
					
					if("".equals(EHF030104T0_01)){
						Form.setEHF030104T0_01(rs.getString("EHF030104T0_01")); //表頭勞保等級序號
						Form.setEHF030104T0_02(rs.getString("EHF030104T0_02")); //年度(yy)
						Form.setEHF030104T0_02_VERSION(rs.getString("EHF030104T0_02_VERSION")); //年度(yy)
						//Form.setEHF030104T0_03( (String) ((Map)compMap.get(rs.getString("EHF030104T0_03"))).get("COMP_NAME_C") ); //公司組織(代號)
						Form.setEHF030104T0_04(rs.getString("EHF030104T0_04")); //備註
						Form.setUSER_CREATE(rs.getString("USER_CREATE"));
						Form.setUSER_UPDATE(rs.getString("USER_UPDATE"));
						Form.setVERSION(rs.getInt("VERSION"));
						Form.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));
						EHF030104T0_06=rs.getString("EHF030104T0_06");
					}
					
					EHF030104M1F bean = new EHF030104M1F();
					bean.setEHF030104T1_01(rs.getString("EHF030104T1_01"));  //勞保等級序號
					bean.setEHF030104T1_02(rs.getString("EHF030104T1_02"));  //勞保投保等級
					bean.setEHF030104T1_03(rs.getString("EHF030104T1_03"));  //勞保投保級距
					bean.setEHF030104T1_04(rs.getString("EHF030104T1_04"));  //個人負擔
					bean.setEHF030104T1_05(rs.getString("EHF030104T1_05"));  //雇主負擔
					bean.setEHF030104T1_06(rs.getString("EHF030104T1_06"));  //合計金額
					//bean.setEHF030104T1_07(rs.getString("EHF030104T1_07"));  //備註
					bean.setEHF030104T1_09(rs.getInt("EHF030104T1_09"));  //健保投保等級(顯示序號)

					//存放前端是否開放修改(0.不可修改、1.可修改)
					if(SafetyMap.containsKey(rs.getString("EHF030104T1_03"))){
						bean.setSHOW(0);
						bean.setEHF030104T1_07(rs.getString("EHF030104T1_07")+"  (已有員工使用，不可修改)");  //備註
					}else{
						bean.setSHOW(1);
						bean.setEHF030104T1_07(rs.getString("EHF030104T1_07"));  //備註
					}
					
					
					
					if(!"".equals(bean.getEHF030104T1_02()) && bean.getEHF030104T1_02() != null ){
						list.add(bean);
					}
					EHF030104T0_01 = rs.getString("EHF030104T0_01");
					
				}
				
//				request.setAttribute("Form2Datas",list);
				Form.setEHF030104C(list);
				
				rs.close();
				stmt.close();

				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				
				// 清掉畫面上的新增明細欄位
				Form.setEHF030104T1_02("");
				Form.setEHF030104T1_03("0");
				Form.setEHF030104T1_04("0");
				Form.setEHF030104T1_05("0");
				Form.setEHF030104T1_06("0");
				Form.setEHF030104T1_07("");
				
				
			} else {
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

	public ActionForward saveData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030104M1F Form = (EHF030104M1F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			BA_Vaildate ve=BA_Vaildate.getInstance();
//			if("".equals(Form.getEHF030102T0_01())){
//				request.setAttribute("MSG","薪資計算公式序號錯誤!!請重新查詢資料!!");
//				return init(mapping, form, request, response);
//				
//			}

			if (lc_errors.isEmpty()) {
				//更新明細資料
				int count = updateList( conn ,Form.getEHF030104C() ,"UPDATE" ,getLoginUser(request).getUserName() ,getLoginUser(request).getCompId() );		
				
				if(count<0){
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("MSG","勞保級距請輸入數字!!請重新操作!!");
					return queryDatas(mapping, form, request, response);
				}
				
				//更新表頭資料
				String sql = "" +
				" UPDATE ehf030104t0 SET " +
				" EHF030104T0_02=? ,EHF030104T0_02_VERSION=? ,EHF030104T0_04=? " +
				" ,USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE EHF030104T0_01 = '"+Form.getEHF030104T0_01()+"' " +
				" AND EHF030104T0_05 = '"+getLoginUser(request).getCompId()+"' ";

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, Form.getEHF030104T0_02());  //勞保等級序號
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030104T0_02_VERSION());  //健保等級序號
				indexid++;
//				p6stmt.setString(indexid, Form.getEHF030104T0_03());  //公司組織(代碼)
//				indexid++;
				p6stmt.setString(indexid, Form.getEHF030104T0_04());  //備註
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				request.setAttribute("MSG", "儲存成功，表頭及明細修改" + count + "筆");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
				
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("button", "query");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
		} catch (Exception e) {

			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("MSG","勞保等級設定儲存錯誤!!請重新操作!!");	
			System.out.println(e);
			e.printStackTrace();
			
//			return init(mapping, form, request, response);
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
//		return mapping.findForward("success");
//		return editDataForm(mapping, form, request, response);
	}

	public ActionForward delData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030104M1F Form = (EHF030104M1F) form;
		
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
				int count = updateList( conn ,Form.getEHF030104C() ,"DELETE" ,getLoginUser(request).getUserName() ,getLoginUser(request).getCompId() );
				
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
	


	private int updateList( Connection conn ,List FM030104c ,String Action ,String user_name ,String comp_id )
		throws Exception {
		String insql = "" +
				" UPDATE ehf030104t1 SET " +
				" EHF030104T1_03=? ,EHF030104T1_04=? ,EHF030104T1_05=? ,EHF030104T1_06=? " +
				" ,EHF030104T1_07=? " +
				" ,USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE EHF030104T1_01=? AND EHF030104T1_09=? AND EHF030104T1_08=? ";
		
		PreparedStatement pstmt = conn.prepareStatement(insql);
		P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
		Statement stmt = conn.createStatement();
		int ucount = 0;
		int dcount = 0;
		int aInt;
		
		for (int i = 0; i < FM030104c.size(); i++) {
			EHF030104M1F Form = (EHF030104M1F) FM030104c.get(i);
			if (Form.isCHANGED()) {
				
				try {
				      aInt = Integer.parseInt(Form.getEHF030104T1_03());
				    } catch (NumberFormatException e) {
				      System.out.println("勞保等級維護："+Form.getEHF030104T1_03().toString() + "不是數字");
				      return -1;
				    }
				
				p6stmt.setString(1, Form.getEHF030104T1_03());  //勞保投保級距
				p6stmt.setString(2, Form.getEHF030104T1_04()==null?"0":Form.getEHF030104T1_04());  //個人負擔
				p6stmt.setString(3, Form.getEHF030104T1_05()==null?"0":Form.getEHF030104T1_05());  //雇主負擔
				p6stmt.setString(4, Form.getEHF030104T1_06()==null?"0":Form.getEHF030104T1_06());  //合計金額
				p6stmt.setString(5, Form.getEHF030104T1_07());  //備註
				p6stmt.setString(6, user_name);
				p6stmt.setString(7, Form.getEHF030104T1_01());
				p6stmt.setInt(8, Form.getEHF030104T1_09());
				p6stmt.setString(9, comp_id);  //公司代碼
				
				p6stmt.executeUpdate();
//				System.out.println(p6stmt.getQueryFromPreparedStatement());
				ucount++;
			}
			if (Action.equalsIgnoreCase("delete")) {
				if (Form.isCHECKED()) {
					stmt.execute("DELETE FROM ehf030104t1 " +
						" WHERE EHF030104T1_01 = '"+Form.getEHF030104T1_01()+"' " +
						" AND EHF030104T1_03 = '"+Form.getEHF030104T1_03()+"'" +
						" AND EHF030104T1_08 = '"+comp_id+"' ");
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
	
	/**
	 * 新增表頭時   直接將上一筆表頭的明細複製過來
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @新增於20140120 BY 賴泓錡 
	 */
	public ActionForward queryDatas_forADD(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF030104M1F Form = (EHF030104M1F) form;
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030104T0_01(arrid[0]);
		}
    	int EHF030104T0_01_number=Integer.valueOf(Form.getEHF030104T0_01());
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			int number=this.getNumber(conn,getLoginUser(request).getCompId(),Form.getEHF030104T0_01());

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT EHF030104T0.* " +
				" ,EHF030104T1_01 ,EHF030104T1_02 ,EHF030104T1_03 ,EHF030104T1_04 ,EHF030104T1_05" +
				" ,EHF030104T1_06 ,EHF030104T1_07 " +
				" FROM EHF030104T0 " +
				" LEFT JOIN EHF030104T1 ON EHF030104T1_01 = EHF030104T0_01 AND EHF030104T1_08 = EHF030104T0_05 " +
				" WHERE 1=1 " +
				" AND EHF030104T0_01 = '"+number+"' " +  //健保等級序號
				" AND EHF030104T0_05 = '"+getLoginUser(request).getCompId()+"' " ;
	
				sql += " ORDER BY EHF030104T0_01 , EHF030104T0_02_VERSION , EHF030104T0_03 ,EHF030104T1_01 ,EHF030104T1_03 ";

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				while(rs.next()){
					EHF030104M1F bean = new EHF030104M1F();
					bean.setEHF030104T1_01(rs.getString("EHF030104T1_01"));  //勞保等級序號
					bean.setEHF030104T1_02(rs.getString("EHF030104T1_02"));  //勞保投保等級
					bean.setEHF030104T1_03(rs.getString("EHF030104T1_03"));  //勞保投保級距
					bean.setEHF030104T1_04(rs.getString("EHF030104T1_04"));  //個人負擔
					bean.setEHF030104T1_05(rs.getString("EHF030104T1_05"));  //雇主負擔
					bean.setEHF030104T1_06(rs.getString("EHF030104T1_06"));  //合計金額
					bean.setEHF030104T1_07(rs.getString("EHF030104T1_07"));  //備註

					if(!"".equals(bean.getEHF030104T1_02()) && bean.getEHF030104T1_02() != null ){
						list.add(bean);
					}
				}
				
				this.INSERT_EHF030104t1(conn, getLoginUser(request).getCompId(), list, request,EHF030104T0_01_number);

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

		//request.setAttribute("Form1Datas", Form);
		return queryDatas(mapping, Form, request, response);
	}

	/**
	 * 取得勞保等級序號
	 * @param conn
	 * @param compId
	 * @return
	 * @新增於20140120 BY 賴泓錡 
	 */
	private int getNumber(Connection conn, String compId,String EHF030104T0_01) {
		// TODO Auto-generated method stub
		int number=0;
		try {
		String sql = "" +
		" SELECT * " +
		" FROM EHF030104T0 " +
		" WHERE 1=1 " +
		" AND EHF030104T0_05 = '"+compId+"' " +
		" AND EHF030104T0_01<>"+EHF030104T0_01 ;
		sql += " ORDER BY EHF030104T0_01 DESC LIMIT 1";

		Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs=stmt.executeQuery(sql);
		while(rs.next()){
			number=Integer.valueOf((String)rs.getString("EHF030104T0_01"));
		}
		} catch (Exception e) {
			System.out.println(e);
		} 
		return number;
	}
	
	
	private void INSERT_EHF030104t1(Connection conn, String CompId, ArrayList list, HttpServletRequest request, int EHF030104T0_01_number) {
		
		Iterator it = list.iterator();
		
		
		while(it.hasNext()){
			EHF030104M1F Form_1 = (EHF030104M1F) it.next();
			try {
				String sql = "" +
				" INSERT INTO ehf030104t1 ( EHF030104T1_01 ,EHF030104T1_02 ,EHF030104T1_03 " +
				" ,EHF030104T1_04 ,EHF030104T1_05 ,EHF030104T1_06 ,EHF030104T1_07 ,EHF030104T1_08 " +
				" ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
				" VALUES (?,?,?,?,?,?,?,?,?,?, 1, NOW() ) "  ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, String.valueOf(EHF030104T0_01_number));  //勞保等級序號
				indexid++;
				p6stmt.setString(indexid, Form_1.getEHF030104T1_02()==""?"0":Form_1.getEHF030104T1_02());  //勞保投保等級
				indexid++;
				p6stmt.setString(indexid, Form_1.getEHF030104T1_03()==""?"0":Form_1.getEHF030104T1_03());  //勞保投保級距
				indexid++;
				p6stmt.setString(indexid, Form_1.getEHF030104T1_04());  //個人負擔
				indexid++;
				p6stmt.setString(indexid, Form_1.getEHF030104T1_05());  //雇主負擔
				indexid++;
				p6stmt.setString(indexid, Form_1.getEHF030104T1_06());  //合計金額
				indexid++;
				p6stmt.setString(indexid, Form_1.getEHF030104T1_07());  //備註
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
			
			} catch (Exception e) {
				System.out.println(e);
			} 
		}
}
	
	
	
	/**
	 * 重排明細順序號碼
	 * @param key
	 * @param sn_field
	 * @param table_name
	 * @param key_sql
	 * @param request 
	 */
	public void reDoSN( String[] key, String sn_field, String table_name, String key_sql, HttpServletRequest request ){
		BaseFunction base_tools = new BaseFunction(getLoginUser(request).getCompId());
		try{
			StringBuffer sql_for_key_select = new StringBuffer();
			StringBuffer sql_for_key_update = new StringBuffer();
			//處理Table Key Set
			for(int i=0; i < key.length; i++){
				//sql_for_key_select
				sql_for_key_select.append(" CAST(");
				sql_for_key_select.append(key[i]);
				sql_for_key_select.append(" AS CHAR(30)) AS "+key[i]+", ");
			}
			
			//重新排明細的 - "順序號碼"
			String sql = "" +
			" SELECT " +
			" "+sql_for_key_select.toString()+" " +  //組成 KEY 
			sn_field + " AS SN " +
			" FROM "+table_name+" " +
			" WHERE 1=1 " +
			key_sql +  //KEY SQL 設定
			" ORDER BY "+sn_field+" ";
			//取得明細清單
			List data_list = base_tools.queryList(sql);
			Iterator it = data_list.iterator();
			Map tempMap  = null;
			int snCount = 0;
			List sql_list = new ArrayList();
			//針對每一筆明細清單做順序號碼的處裡
			while(it.hasNext()){
				sql_for_key_update = new StringBuffer();
				tempMap = (Map) it.next();
				
				
				//處理Table Key Set
				for(int j=0; j < key.length; j++){
					//sql_for_key_update
					sql_for_key_update.append(" AND ");
					sql_for_key_update.append(key[j]);
					sql_for_key_update.append(" = '");
					sql_for_key_update.append((String)tempMap.get(key[j]));
					sql_for_key_update.append("' ");
				}
				
				sql = "" +
				" UPDATE "+table_name+" SET " +
				" EHF030104T1_09 = "+snCount+" " +
				" WHERE 1=1 " +
				//" AND ESF010300T1_01 = "+(Integer)tempMap.get("ESF010300T1_01")+" " +  //委外加工單資料序號
				" "+sql_for_key_update.toString()+" " +  //組成 KEY
				" AND "+sn_field+" = '"+(Integer)tempMap.get("SN")+"' ";  //序號
				sql_list.add(sql);
				snCount++;
			}
			base_tools.executeBatchSQL(sql_list);
			base_tools.commit();
			base_tools.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 取得所有有使用的勞保資料(根據)
	 * @param conn
	 * @param key
	 * @param request
	 */
	public void getAllSafety(Connection conn, String key, HttpServletRequest request ,Map SafetyMap){
		
		try{
			String sql = "" +
			" SELECT EHF030104T1_03 " +
			" FROM EHF030300T0 " +
			" LEFT JOIN EHF030104T0 ON EHF030104T0_01 = EHF030300T0_05_VERSION AND EHF030104T0_05 = EHF030300T0_14 " + //勞保  
			" LEFT JOIN EHF030104T1 ON EHF030104T1_01 = EHF030104T0_01 AND EHF030104T1_08 = EHF030300T0_14 AND EHF030104T1_02 = EHF030300T0_07 " +
			" WHERE 1=1 " +
			" AND EHF030104T0_01 = '"+key+"' "  +
			" AND EHF030300T0_14 = '"+getLoginUser(request).getCompId()+"' " ;
		
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				if(!SafetyMap.containsKey(rs.getString("EHF030104T1_03"))){
					SafetyMap.put(rs.getString("EHF030104T1_03"), rs.getString("EHF030104T1_03"));
				}
				
			}
			rs.close();
			stmt.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}