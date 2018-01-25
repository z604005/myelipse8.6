package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.vacation.forms.EX020509R0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

/**
 * 職員加班休假月報表
 * (Action)
 * @author maybe
 * @version 1.0
 * @created 上午9:35:44
 */
public class EHF020509M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		
		try{
			String day[]=(tools.ymdTostring(tools.getSysDate())).split("/");
						
			//設定日期區間初始值
			Form.setDATA01(day[0]+"/"+day[1]);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 列印查詢結果 (全部)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
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
    	
    	try{
    		//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			
    		HR_TOOLS hr_tools = new HR_TOOLS();
			
			//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			
			hr_tools.close();
			
			String empName="";
			
			Iterator iter_emp = empMap.entrySet().iterator(); 
			while (iter_emp.hasNext()) {
				Map.Entry entry = (Map.Entry) iter_emp.next(); 
			    String key = (String)entry.getKey(); 
			    //排除人資、理事長、秘書長
			    if("EMP201607040001".equals(key) || "EMP201703020001".equals(key) || "EMP201703020002".equals(key)){			    				    	
			    	empName+=key+",";			    		
			    }else{
			    	
			    	String status = (String)((Map)empMap.get(key)).get("SEARCHSTATUS");
			    	//排除離職
			    	if("2".equals(status) || "4".equals(status)){
			    		empName+=key+",";
			    	}
			    }   
			}
			
			if(!"".equals(empName)){
				String[] empNameList=empName.split(",");
				for(int i=0; i < empNameList.length; i++){    
					   //System.out.println(empNameList[i]); 
					   empMap.remove(empNameList[i]);
				}   
			}
			
			// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
			String month_start_date = tools.convertADDateToStrng(BA_TOOLS.getFirstMonthDay(tools.covertStringToCalendar(Form.getDATA01()+"/01")));
			// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
			String month_end_date = tools.convertADDateToStrng(BA_TOOLS.getLastMonthDay(tools.covertStringToCalendar(Form.getDATA01()+"/01")));			
			//加班換休狀況
			Map OvertimeToVacation = new HashMap();
			//請假狀況
			Map Vacation = new HashMap();
			//特休
			Map AnnualLeave = new HashMap();
			//補休
			Map CompensatoryLeave = new HashMap();
			
			this.getOvertimeToVacation(month_start_date, month_end_date, authbean.getCompId(), OvertimeToVacation);
			
			this.getVacation(month_start_date, month_end_date, authbean.getCompId(), Vacation);
			
			this.getAnnualLeave(month_start_date, month_end_date, authbean.getCompId(), AnnualLeave);
			
			this.getCompensatoryLeave(month_start_date, month_end_date, authbean.getCompId(), CompensatoryLeave);
			
			EX020509R0F ef = new EX020509R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request);
			
			int DataCount =0;
			boolean chk_run_flag = true;
			boolean print_flag = false;
			//一天工時
			float work_hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
			
			Iterator iter = empMap.entrySet().iterator(); 
			
			while (iter.hasNext()) {
				
				//取得查詢月份的第一天
				Calendar month_start_cal = tools.covertStringToCalendar(month_start_date); // 月份開始日期Calendar
		    	//取得查詢月份的最後一天
				Calendar month_end_cal = tools.covertStringToCalendar(month_end_date); // 月份結束日期Calendar
				
				Map.Entry entry = (Map.Entry) iter.next();
				//員工內碼
			    String key = (String)entry.getKey(); 
				
				if(DataCount==0){
					
				}
				//System.out.println(key);
				//ef.next();
				print_flag = false;
				
				while(chk_run_flag){
					
					if(month_start_cal.equals(month_end_cal)){
						chk_run_flag = false;
					}
					
					String cur_date = tools.covertDateToString(month_start_cal.getTime(), "yyyy/MM/dd");  //當前計算的日期
					
					String EMPLOYEE = key+cur_date;
					String C_VA = key+"A13";
					String A_VA = key+"A03";
					
					if(OvertimeToVacation.get(EMPLOYEE)!=null || Vacation.get(EMPLOYEE)!=null 
							){
						ef.next();
					}
					
					if(OvertimeToVacation.get(EMPLOYEE)!=null){
						ef.setDetail(1,"A", (String) ((Map)depMap.get((String)((Map)empMap.get(key)).get("DEPT_ID"))).get("SHOW_DESC"),false);//部門
						ef.setDetail(1,"B", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_ID"),false);//員工編號
						ef.setDetail(1,"C", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_NAME"),false);//姓名
						ef.setDetail(1,"D", (String) ((Map)OvertimeToVacation.get(EMPLOYEE)).get("EHF020802T0_04"),false);
						ef.setDetail(1,"E", new Float((Float) ((Map)OvertimeToVacation.get(EMPLOYEE)).get("EHF020802T0_07")).longValue(),false,"0.0");
						print_flag = true;
					}
					
					if(Vacation.get(EMPLOYEE)!=null){
						ef.setDetail(1,"A", (String) ((Map)depMap.get((String)((Map)empMap.get(key)).get("DEPT_ID"))).get("SHOW_DESC"),false);//部門
						ef.setDetail(1,"B", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_ID"),false);//員工編號
						ef.setDetail(1,"C", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_NAME"),false);
						ef.setDetail(1,"G", (String) ((Map)Vacation.get(EMPLOYEE)).get("EHF020200T0_09"),false);
						ef.setDetail(1,"H", (String) ((Map)Vacation.get(EMPLOYEE)).get("KIND"),false);
						ef.setDetail(1,"I", (String) ((Map)Vacation.get(EMPLOYEE)).get("EHF020200T0_13"),false);
						print_flag = true;
					}
					
					if(print_flag){
						if(CompensatoryLeave.get(C_VA)!=null){
							ef.setDetail(1,"A", (String) ((Map)depMap.get((String)((Map)empMap.get(key)).get("DEPT_ID"))).get("SHOW_DESC"),false);//部門
							ef.setDetail(1,"B", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_ID"),false);//員工編號
							ef.setDetail(1,"C", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_NAME"),false);
//							ef.setDetail(1,"K", new Double((Double) ((Map)CompensatoryLeave.get(C_VA)).get("DAYHOUR")).longValue(),false,"0.0");
						}
						
						if(AnnualLeave.get(A_VA)!=null){
							ef.setDetail(1,"A", (String) ((Map)depMap.get((String)((Map)empMap.get(key)).get("DEPT_ID"))).get("SHOW_DESC"),false);//部門
							ef.setDetail(1,"B", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_ID"),false);//員工編號
							ef.setDetail(1,"C", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_NAME"),false);
							
							double annual_day = (Double) ((Map)AnnualLeave.get(A_VA)).get("DAYHOUR") / work_hours;
							
//							ef.setDetail(1,"L", new Double(annual_day).longValue(),false,"0.0");
						}
					}else{
						if(!chk_run_flag && !print_flag){
							ef.next();
							if(CompensatoryLeave.get(C_VA)!=null){
								ef.setDetail(1,"A", (String) ((Map)depMap.get((String)((Map)empMap.get(key)).get("DEPT_ID"))).get("SHOW_DESC"),false);//部門
								ef.setDetail(1,"B", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_ID"),false);//員工編號
								ef.setDetail(1,"C", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_NAME"),false);
//								ef.setDetail(1,"K", new Double((Double) ((Map)CompensatoryLeave.get(C_VA)).get("DAYHOUR")).longValue(),false,"0.0");
							}
							
							if(AnnualLeave.get(A_VA)!=null){
								ef.setDetail(1,"A", (String) ((Map)depMap.get((String)((Map)empMap.get(key)).get("DEPT_ID"))).get("SHOW_DESC"),false);//部門
								ef.setDetail(1,"B", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_ID"),false);//員工編號
								ef.setDetail(1,"C", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_NAME"),false);
								
								double annual_day = (Double) ((Map)AnnualLeave.get(A_VA)).get("DAYHOUR") / work_hours;
								
//								ef.setDetail(1,"L", new Double(annual_day).longValue(),false,"0.0");
							}
							
							if(CompensatoryLeave.get(C_VA)==null && AnnualLeave.get(A_VA)==null){
								ef.setDetail(1,"A", (String) ((Map)depMap.get((String)((Map)empMap.get(key)).get("DEPT_ID"))).get("SHOW_DESC"),false);//部門
								ef.setDetail(1,"B", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_ID"),false);//員工編號
								ef.setDetail(1,"C", (String) ((Map)empMap.get((String)((Map)empMap.get(key)).get("USER_CODE"))).get("EMPLOYEE_NAME"),false);
							}
						}
					}
																														
					month_start_cal.add(Calendar.DAY_OF_MONTH, 1);
					
				}
				
				if(!chk_run_flag){
			    	chk_run_flag = true;	
			    }
				
				DataCount++;
				
			}
			
			
			/*
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			int DataCount =0;
			
			String sql = "" +
			" SELECT " +
			" EHF010100T0_01, " +
			" EHF020802T0_04, " +
			" CASE EHF020802T0_09 WHEN '01' THEN '換錢' WHEN '02' THEN '換休' END AS KIND, " +
			" EHF020802T0_07, " +
			" '1' AS TYPE, " +
			" EHF010100T6_02 " +
			" FROM EHF010100T0 " +
			" LEFT JOIN EHF020802T0 ON EHF020802T0_02 = EHF010100T0_01 AND EHF020802T0_08 = HR_CompanySysNo " +
			" LEFT JOIN EHF010100T6 ON EHF010100T6_01 = EHF010100T0_01 AND EHF010100T6.HR_CompanySysNo = EHF010100T0.HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND EHF020802T0_04 BETWEEN '"+month_start_date+"' AND '"+month_end_date+"' " +
			" AND EHF020802T0_09 = '02' " +
			" UNION " +
			" SELECT " +
			" EHF010100T0_01, " +
			" DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS EHF020200T0_09, " +
			" CASE EHF020200T0_14 WHEN 'A13' THEN '補休' WHEN 'A03' THEN '特休' END AS KIND, " +
			" EHF020200T0_13, " +
			" '2' AS TYPE, " +
			" EHF010100T6_02 " +
			" FROM EHF010100T0 " +
			" LEFT JOIN EHF020200T0 ON EHF020200T0_03 = EHF010100T0_01 AND EHF020200T0_18 = HR_CompanySysNo " +
			" LEFT JOIN EHF010100T6 ON EHF010100T6_01 = EHF010100T0_01 AND EHF010100T6.HR_CompanySysNo = EHF010100T0.HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND EHF020200T0_09 BETWEEN '"+month_start_date+"' AND '"+month_end_date+"' " +
			" AND (EHF020200T0_14 = 'A03' OR EHF020200T0_14 = 'A13') " +
			" AND EHF020200T0_16 = '0006' " +
			" UNION " +
			" SELECT EHF010100T0_01, " +
			" '', " +
			" '', " +
			" SUM(EHF010300T0_08), " +
			" '3' AS TYPE, " +
			" EHF010100T6_02 " +
			" FROM EHF010100T0" +
			" LEFT JOIN EHF010300T0 ON EHF010300T0_05 = EHF010100T0_01 AND EHF010300T0_12 = HR_CompanySysNo " +
			" LEFT JOIN EHF010100T6 ON EHF010100T6_01 = EHF010100T0_01 AND EHF010100T6.HR_CompanySysNo = EHF010100T0.HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND (EHF010300T0_09 <= '"+month_start_date+"' OR EHF010300T0_10 >= '"+month_end_date+"') " +
			" AND (EHF010300T0_09 <= '"+month_start_date+"' OR EHF010300T0_10 <= '"+month_end_date+"') " +
			" AND (EHF010300T0_09 >= '"+month_start_date+"' OR EHF010300T0_10 >= '"+month_end_date+"') " +
			" AND EHF010300T0_06 = 'A13' " +
			" GROUP BY EHF010100T0_01 " +
			" UNION " +
			" SELECT EHF010100T0_01, " +
			" '', " +
			" '', " +
			" SUM(EHF010300T0_08), " +
			" '4' AS TYPE, " +
			" EHF010100T6_02 " +
			" FROM EHF010100T0 " +
			" LEFT JOIN EHF010300T0 ON EHF010300T0_05 = EHF010100T0_01 AND EHF010300T0_12 = HR_CompanySysNo " +
			" LEFT JOIN EHF010100T6 ON EHF010100T6_01 = EHF010100T0_01 AND EHF010100T6.HR_CompanySysNo = EHF010100T0.HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND (EHF010300T0_09 <= '"+month_start_date+"' OR EHF010300T0_10 >= '"+month_end_date+"') " +
			" AND (EHF010300T0_09 <= '"+month_start_date+"' OR EHF010300T0_10 <= '"+month_end_date+"') " +
			" AND (EHF010300T0_09 >= '"+month_start_date+"' OR EHF010300T0_10 >= '"+month_end_date+"') " +
			" AND EHF010300T0_06 = 'A03' " +
			" GROUP BY EHF010100T0_01 " +
			" ORDER BY EHF010100T6_02, EHF010100T0_01, EHF020802T0_04 ";
			
			rs = stmt.executeQuery(sql);
			
			String temp = "";
			
			while(rs.next()) {
				
				if(DataCount==0){
					
				}
				
				ef.next();
				
				if("1".equals(rs.getString("TYPE"))){//加班
					
					ef.setDetail(1,"A", (String) ((Map)depMap.get(rs.getString("EHF010100T6_02"))).get("SHOW_DESC"),false);//
					ef.setDetail(1,"B", (String) ((Map)empMap.get(rs.getString("EHF010100T0_01"))).get("EMPLOYEE_ID"),false);//
					ef.setDetail(1,"C", (String) ((Map)empMap.get(rs.getString("EHF010100T0_01"))).get("EMPLOYEE_NAME"),false);
					ef.setDetail(1,"D", rs.getString("EHF020802T0_04"),false);
					ef.setDetail(1,"E", rs.getString("EHF020802T0_07"),false);
					
					if(!temp.equals(rs.getString("EHF010100T0_01"))){
						temp = "";
						ef.setDetail(1,"K", rs.getString("EHF020802T0_07"),false);
						ef.setDetail(1,"L", rs.getString("EHF020802T0_07"),false);
					}
					temp = rs.getString("EHF010100T0_01");
					
				}else if("2".equals(rs.getString("TYPE"))){//請假
					
					ef.setDetail(1,"A", (String) ((Map)depMap.get(rs.getString("EHF010100T6_02"))).get("SHOW_DESC"),false);//
					ef.setDetail(1,"B", (String) ((Map)empMap.get(rs.getString("EHF010100T0_01"))).get("EMPLOYEE_ID"),false);//
					ef.setDetail(1,"C", (String) ((Map)empMap.get(rs.getString("EHF010100T0_01"))).get("EMPLOYEE_NAME"),false);
					ef.setDetail(1,"G", rs.getString("EHF020802T0_04"),false);
					ef.setDetail(1,"H", rs.getString("KIND"),false);
					ef.setDetail(1,"I", rs.getString("EHF020802T0_07"),false);
					
					if(!temp.equals(rs.getString("EHF010100T0_01"))){
						temp = "";
						ef.setDetail(1,"K", rs.getString("EHF020802T0_07"),false);
						ef.setDetail(1,"L", rs.getString("EHF020802T0_07"),false);
					}
					temp = rs.getString("EHF010100T0_01");
					
				}else{
					ef.setDetail(1,"A", (String) ((Map)depMap.get(rs.getString("EHF010100T6_02"))).get("SHOW_DESC"),false);//
					ef.setDetail(1,"B", (String) ((Map)empMap.get(rs.getString("EHF010100T0_01"))).get("EMPLOYEE_ID"),false);//
					ef.setDetail(1,"C", (String) ((Map)empMap.get(rs.getString("EHF010100T0_01"))).get("EMPLOYEE_NAME"),false);
					
					if(!temp.equals(rs.getString("EHF010100T0_01"))){
						temp = "";
						ef.setDetail(1,"K", rs.getString("EHF020802T0_07"),false);
						ef.setDetail(1,"L", rs.getString("EHF020802T0_07"),false);
					}
					temp = rs.getString("EHF010100T0_01");
				}
				
				DataCount++;
				
			}
			ef.EndDocument();
			
			rs.close();
			stmt.close();
			*/			
			
			//傳入前端需要的檔名
			String name ="";
			String FileName="";
			
			if (DataCount > 0){//表示有資料
				name="職員加班休假月報表.xls";
				//存入檔案
				FileName = ef.write();
				request.setAttribute("MSG","列印完成!!");
				//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
				return init(mapping, Form, request, response);	
			}else{
				request.setAttribute("MSG","沒有資料可列印!!");
			}						
    		
    	}catch (Exception E) {
			E.printStackTrace();
			request.setAttribute("MSG","列印失敗!!");
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return init(mapping, Form, request, response);
	}
	
	/**
	 * 補休
	 * @param month_start_date
	 * @param month_end_date
	 * @param comp_id
	 * @param CompensatoryLeave
	 */
	private void getCompensatoryLeave(String month_start_date,
			String month_end_date, String comp_id, Map CompensatoryLeave) {
		// TODO Auto-generated method stub
		
		BaseFunction base_tools = new BaseFunction();
		
		try{
			String sql = "" +
			" SELECT EHF010100T0_01, " +
			" EHF010300T0_06, " +
			" SUM(EHF010300T0_08) AS DAYHOUR " +
			" FROM EHF010100T0" +
			" LEFT JOIN EHF010300T0 ON EHF010300T0_05 = EHF010100T0_01 AND EHF010300T0_12 = HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND EHF010300T0_10 >= '"+month_start_date+"' " +
			" AND EHF010300T0_06 = 'A13' " +
			" GROUP BY EHF010100T0_01 " ;
			
			List templist = base_tools.queryList(sql);
			
			Iterator it = templist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				CompensatoryLeave.put((String)columnMap.get("EHF010100T0_01")+(String)columnMap.get("EHF010300T0_06"), columnMap);
				
			}
			
			base_tools.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 特休
	 * @param month_start_date
	 * @param month_end_date
	 * @param comp_id
	 * @param AnnualLeave
	 */
	private void getAnnualLeave(String month_start_date,
			String month_end_date, String comp_id, Map AnnualLeave) {
		// TODO Auto-generated method stub
		
		BaseFunction base_tools = new BaseFunction();
		
		try{
			String sql = "" +
			" SELECT EHF010100T0_01, " +
			" EHF010300T0_06, " +
			" SUM(EHF010300T0_08) AS DAYHOUR " +
			" FROM EHF010100T0" +
			" LEFT JOIN EHF010300T0 ON EHF010300T0_05 = EHF010100T0_01 AND EHF010300T0_12 = HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND EHF010300T0_10 >= '"+month_start_date+"' " +
			" AND EHF010300T0_06 = 'A03' " +
			" GROUP BY EHF010100T0_01 " ;
			
			List templist = base_tools.queryList(sql);
			
			Iterator it = templist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				AnnualLeave.put((String)columnMap.get("EHF010100T0_01")+(String)columnMap.get("EHF010300T0_06"), columnMap);
				
			}
			
			base_tools.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 請假狀況
	 * @param month_start_date
	 * @param month_end_date
	 * @param comp_id
	 * @param Vacation
	 */
	private void getVacation(String month_start_date,
			String month_end_date, String comp_id, Map Vacation) {
		// TODO Auto-generated method stub
		
		BaseFunction base_tools = new BaseFunction();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF010100T0_01, " +
			" DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS EHF020200T0_09, " +
			" CASE EHF020200T0_14 WHEN 'A13' THEN '補休' WHEN 'A03' THEN '特休' END AS KIND, " +
			" EHF020200T0_13 " +
			" FROM EHF010100T0 " +
			" LEFT JOIN EHF020200T0 ON EHF020200T0_03 = EHF010100T0_01 AND EHF020200T0_18 = HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND EHF020200T0_09 BETWEEN '"+month_start_date+"' AND '"+month_end_date+"' " +
			" AND (EHF020200T0_14 = 'A03' OR EHF020200T0_14 = 'A13') " +
			" AND EHF020200T0_16 = '0006' " ;
			
			List templist = base_tools.queryList(sql);
			
			Iterator it = templist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				Vacation.put((String)columnMap.get("EHF010100T0_01")+(String)columnMap.get("EHF020200T0_09"), columnMap);
				
			}
			
			base_tools.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 加班換休
	 * @param month_start_date
	 * @param month_end_date
	 * @param comp_id
	 * @param OvertimeToVacation
	 */
	private void getOvertimeToVacation(String month_start_date,
			String month_end_date, String comp_id, Map OvertimeToVacation) {
		// TODO Auto-generated method stub
		
		BaseFunction base_tools = new BaseFunction();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF010100T0_01, " +
			" EHF020802T0_04, " +
			" CASE EHF020802T0_09 WHEN '01' THEN '換錢' WHEN '02' THEN '換休' END AS KIND, " +
			" EHF020802T0_07 " +
			" FROM EHF010100T0 " +
			" LEFT JOIN EHF020802T0 ON EHF020802T0_02 = EHF010100T0_01 AND EHF020802T0_08 = HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND EHF020802T0_04 BETWEEN '"+month_start_date+"' AND '"+month_end_date+"' " +
			" AND EHF020802T0_09 = '02' " ;
			
			List templist = base_tools.queryList(sql);
			
			Iterator it = templist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				OvertimeToVacation.put((String)columnMap.get("EHF010100T0_01")+(String)columnMap.get("EHF020802T0_04"), columnMap);
				
			}
			
			base_tools.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
