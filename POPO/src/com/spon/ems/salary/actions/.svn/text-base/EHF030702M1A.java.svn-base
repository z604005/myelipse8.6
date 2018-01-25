package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.salary.forms.EHF030600M0F;
import com.spon.ems.salary.forms.EX030702R1F;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;


/**
 * <Action>個人薪資條
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030702M1A extends EHF030600M1A {
	
	public EHF030702M1A(){

	}

	/**
	 * Action的進入點
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws SQLException 
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		EHF030600M0F Form = new EHF030600M0F();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		ArrayList list = new ArrayList();
		
		try{
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
			
			//設定日期區間初始值
			//入扣帳年月
			//Form.setEHF030600T0_M(tools.ymTostring(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2))));
			//薪資年月
			//Form.setEHF030600T0_02(tools.ymTostring(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2))));
//			Form.setDATA02(tools.ymTostring(tools.addmonth(Integer.parseInt((tools.getSysDate()+"").substring(0,(tools.getSysDate()+"").length()-2)), -1)));
			
			//產生下拉選單
			this.generateSelectBox(conn, Form, request);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "query");
			request.setAttribute("collection", "show");
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", list);
			
		}catch(Exception e){
			e.printStackTrace();
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
	
	protected void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request) {
		
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
				" AND EHF030600T0_04  = '01' " +
				" AND EHF030600T0_SCP = '04' " +  //類別 - 薪資
				" AND EHF030600T0_14 = '"+getLoginUser(request).getCompId()+"' " ;  //公司代碼
				
				if(!"".equals(Form.getEHF030600T0_M()) && Form.getEHF030600T0_M() != null ){
					sql += " AND EHF030600T0_M = '"+Form.getEHF030600T0_M()+"' ";  //入扣帳年月
				}
				if(!"".equals(Form.getEHF030600T0_02()) && Form.getEHF030600T0_02() != null ){
					sql += " AND EHF030600T0_02 = '"+Form.getEHF030600T0_02()+"' ";  //薪資年月
				}
				//發放類別
//				if(!"".equals(Form.getEHF030600T0_04()) && Form.getEHF030600T0_04()!= null ){
//	    			sql += " and EHF030600T0_04 = '"+Form.getEHF030600T0_04()+"' ";
//	    		}
				
				sql += " ORDER BY EHF030600T0_01 DESC ";

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					rs.beforeFirst();
					
					HR_TOOLS hr_tools = new HR_TOOLS();
					
					Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getCompId());
					
					hr_tools.close();
					
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
				
				rs.close();
				stmt.close();
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
	 * 列印個人薪資條
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EHF030600M0F Form = (EHF030600M0F)form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		ActionErrors lc_errors = new ActionErrors();
		String salary_no = "";
		
		try{
			//檢查使用是否有選取列印資料
			String arrid[] = request.getParameterValues("checkId");
			if ((arrid==null?false:!arrid[0].equals(""))){
				//salary_no = arrid[0];  //薪資計算序號
				Form.setEHF030600T0_01(arrid[0]);
			}else{
				request.setAttribute("ERRMSG","請選取一筆要列印的資料!!");
				return queryDatas(mapping,form,request,response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
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
    	
    	try{
    		AuthorizedBean authbean = getLoginUser(request);
			
    		//產生Excel元件
			//EX030702R1F ef = new EX030702R1F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request);
			
			//int DataCount = 0;
			//取得薪資計算明細資料
			//List printlist = this.queryListDatas(conn, authbean, salary_no);
			//取得公司名稱
			//Map compMap = (Map)ems_tools.getCompNameMap(authbean.getUserId()).get(authbean.getCompId());
			//列印報表表頭
			//ef.printHeadValue( "", (String)compMap.get("COMP_NAME_C"), "個人薪資條");
			//列印報表
			//DataCount = ef.print(conn, printlist, authbean.getCompId());
    		
    		//預先撈出資料
    		Form=this.putEHF030600T0(conn,Form,Form.getEHF030600T0_01(),authbean);	
			
    		
    		int DataCount = 0;
			//取得薪資計算明細資料
			List printlist = this.queryListDatas(conn, authbean, Form.getEHF030600T0_01()); 
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			//取得公司名稱
			Map compMap = hr_tools.getCompNameMap(conn,authbean.getCompId());
			
			hr_tools.close();
			
			EX030702R1F ef = new EX030702R1F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request,Form,authbean.getCompId(),printlist,tools.ymdTostring(tools.getSysDate())+"",(String)compMap.get("COMP_NAME_C"));
			
			//列印報表表頭
			ef.printHeadValue(Form.getEHF030600T0_02(), Form.getEHF030600T0_M(), tools.ymdTostring(tools.getSysDate())+"",   (String)compMap.get("COMP_NAME_C"), "個  人  薪  資  條");
			//列印報表
			DataCount = ef.print(conn, printlist, Form.getEHF030600T0_U(), authbean.getCompId());
			
			//傳入前端需要的檔名
			String name ="個人薪資條.xls";
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
				request.setAttribute("ERRMSG","沒有資料可列印!!");
			}
    		
			//產生下拉選單
//			this.generateSelectBox(conn, Form, request);
    		
    	}catch(Exception e){
    		e.printStackTrace();
			request.setAttribute("ERRMSG","列印失敗!!");
    	}finally {
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
	/**
	 * 依照EHF030600T0_01(薪資計算資料序號)
	 * 撈出EHF030600T0資料
	 * @param conn
	 * @param Form
	 * @param EHF030600t0_01
	 * @param authbean
	 * @return
	 */
	private EHF030600M0F putEHF030600T0(Connection conn, EHF030600M0F Form,	String EHF030600t0_01, AuthorizedBean authbean) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		try {
		//表頭資料查詢
		String sql = "" +
		" SELECT EHF030600T0.*, IFNULL(DATE_FORMAT(EHF030600T0_12, '%Y/%m/%d'), '') AS APPLY_DAY " +
		" FROM EHF030600T0 " +
		" WHERE 1=1 " +
		" AND EHF030600T0_01 = "+EHF030600t0_01+" " +  //薪資計算序號
		" AND EHF030600T0_14 = '"+authbean.getCompId()+"' " ;  //公司代碼

		sql += " ORDER BY EHF030600T0_01 DESC ";

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		HR_TOOLS hr_tools = new HR_TOOLS();
		
		Map compMap = hr_tools.getCompNameMap(conn,authbean.getCompId());
		Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
		Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
		
		hr_tools.close();

		if(rs.next()){
			
			Form.setEHF030600T0_01(rs.getString("EHF030600T0_01")); //薪資計算序號
			Form.setEHF030600T0_U(rs.getString("EHF030600T0_U"));  //薪資計算UID
			Form.setEHF030600T0_SCP(rs.getString("EHF030600T0_SCP"));  //薪資計算處理狀態
			Form.setEHF030600T0_M(rs.getString("EHF030600T0_M")); //入扣帳年月
			Form.setEHF030600T0_02(rs.getString("EHF030600T0_02")); //薪資年月
			Form.setEHF030600T0_03(rs.getString("EHF030600T0_03")); //公司代號
			Form.setEHF030600T0_03_TXT((String) (compMap.get("COMP_NAME_C")));  //公司名稱
			Form.setEHF030600T0_04(rs.getString("EHF030600T0_04")); //發放類別
			Form.setEHF030600T0_05(rs.getString("EHF030600T0_05")); //天數計算類別
			Form.setEHF030600T0_06(rs.getString("EHF030600T0_06")); //天數
			Form.setEHF030600T0_07(rs.getString("EHF030600T0_07")); //發放方式
			Form.setEHF030600T0_08(rs.getString("EHF030600T0_08")); //依本俸比例
			Form.setEHF030600T0_09(rs.getString("EHF030600T0_09")); //依固定金額
			Form.setEHF030600T0_10(rs.getString("EHF030600T0_10")); //本次發放金額
//			Form.setEHF030600T0_11(rs.getString("EHF030600T0_11"));  //是否已發放
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
		stmt.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return Form;
	}
	
}
