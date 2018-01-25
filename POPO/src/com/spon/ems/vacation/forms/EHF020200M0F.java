package com.spon.ems.vacation.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.vacation.tools.EMS_CaculateVacationByCal;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_GetEmsFlowInf;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午9:46:59
 */
public class EHF020200M0F extends ActionForm implements ValidateForm {
	
	private  String  EHF020200T0_01;
	private  String  EHF020200T0_02;
	private  String  EHF020200T0_03;
	private  String  EHF020200T0_03_TXT;
	private  String  EHF020200T0_03_SHOW;
	private  String  EHF020200T0_04;
	private  String  EHF020200T0_04_TXT;
	private  String  EHF020200T0_04_SHOW;
	private  String  EHF020200T0_05;
	private  String  EHF020200T0_05_TXT;
	private  String EHF020200T0_05_SHOW;
	private  String  EHF020200T0_06;
	private  String  EHF020200T0_06_TXT;
	private  String EHF020200T0_06_SHOW;
	private  String  EHF020200T0_07;
	private  String  EHF020200T0_07_TXT;
	private  String EHF020200T0_07_SHOW;
	private  String  EHF020200T0_08;
	private  String  EHF020200T0_09;
	private  String  EHF020200T0_10;
	private  String  EHF020200T0_11;
	private  String  EHF020200T0_11_HH;
	private  String  EHF020200T0_11_MM;
	private  String  EHF020200T0_12;
	private  String  EHF020200T0_12_HH;
	private  String  EHF020200T0_12_MM;
	private  String  EHF020200T0_13;
	private  String  EHF020200T0_13_DAY;
	private  String  EHF020200T0_13_HOUR;
	private  String  EHF020200T0_14;
	private  String  EHF020200T0_14_DESC;
	private  String  EHF020200T0_15;
	private  String  EHF020200T0_16;
	private  String  EHF020200T0_16_DESC;
	private  String  EHF020200T0_17;
	private  String  EHF020200T0_18;
	
	private  String  USER_CREATE;
	private  String  USER_UPDATE;
	private   int  VERSION;
	private  String  DATE_UPDATE;
	
	private List EHF020200C = new ArrayList();
	
	private FormFile UPLOADFILE;
	
	private String SIGN_COMMENT;
	
	
	EMS_CheckVacation ems_checkVacation = EMS_CheckVacation.getInstance();
	BA_TOOLS tools = BA_TOOLS.getInstance();
	
	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		List flow_sign_log_list = new ArrayList();
		ActionErrors l_actionErrors = new ActionErrors();
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		if(EHF020200T0_09 != null && EHF020200T0_10 != null && EHF020200T0_11_HH != null && EHF020200T0_11_MM != null 
				&& EHF020200T0_12_HH != null && EHF020200T0_12_MM != null ){
		
			BA_TOOLS tools = BA_TOOLS.getInstance();
		
			Calendar start_cal = tools.covertStringToCalendar(EHF020200T0_09+" "+EHF020200T0_11_HH+":"+EHF020200T0_11_MM+":00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
			Calendar end_cal   = tools.covertStringToCalendar(EHF020200T0_10+" "+EHF020200T0_12_HH+":"+EHF020200T0_12_MM+":00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
		
			if(start_cal.compareTo(end_cal)>0){
				l_actionErrors.add("EHF020200T0_09",new ActionMessage(""));  
				l_actionErrors.add("EHF020200T0_10",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+"日期錯誤，請檢查!!"+" ");
			}
		}
		
		if (l_actionErrors.isEmpty()) {
			
			EMS_CheckVacation checkva_tools = (EMS_CheckVacation) EMS_CheckVacation.getInstance();
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				
				ve.isEmpty(l_actionErrors, EHF020200T0_03, "EHF020200T0_03", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020200T0_14, "EHF020200T0_14", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020200T0_09, "EHF020200T0_09", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020200T0_10, "EHF020200T0_10", "不可空白");
//				ve.isEmpty(l_actionErrors, EHF020200T0_07, "EHF020200T0_07_SHOW", "不可空白");
				
//				if(EHF020200T0_07.equals(EHF020200T0_03)){//若代理人與申請人相同
//					
//					l_actionErrors.add("EHF020200T0_07_SHOW", new ActionMessage("代理人不能為申請人"));
//					
//				}else if(!EHF020200T0_07.equals(EHF020200T0_03)){//若代理人與申請人不同
//					
//					//檢核代理人一天有無代理超過一張假單							
//					if(!"".equals(EHF020200T0_07)){//若代理人欄位不為空		
//						
//							checkva_tools.checkAgent(l_actionErrors, request , conn, EHF020200T0_07,EHF020200T0_01,
//													 EHF020200T0_09,EHF020200T0_10,"代理人重複");
//					}
//				}			
				//檢核請假期間是否有例假日問題
				//於EMS1.1.0時可以選擇六日的是否計算到請假天數，因此不能檢核請假區間是否為例假日。 20140610 by Hedwig
//				checkva_tools.vacation_holiday_validate(l_actionErrors, request, conn, EHF020200T0_09, EHF020200T0_11_HH+EHF020200T0_11_MM,
//														EHF020200T0_10, EHF020200T0_12_HH+EHF020200T0_12_MM);
				
				//檢核請假期間是否符合邏輯
				checkva_tools.checkVacationDateTime(l_actionErrors, request, conn, EHF020200T0_03, EHF020200T0_09, 
												    EHF020200T0_11_HH+EHF020200T0_11_MM, EHF020200T0_10,
													EHF020200T0_12_HH+EHF020200T0_12_MM, "1");
				
				//檢核請假年度月份是否符合邏輯
				checkva_tools.checkVacationYEARMONTH(l_actionErrors, request, EHF020200T0_09, EHF020200T0_10);
				
				//檢核同一時間區間內是否有已完成的請假單
				checkva_tools.checkConflictVacationForm(l_actionErrors, request, conn, EHF020200T0_03, 
													    EHF020200T0_09, EHF020200T0_11_HH+EHF020200T0_11_MM, 
													    EHF020200T0_10, EHF020200T0_12_HH+EHF020200T0_12_MM );
				
				if(!"".equals(EHF020200T0_14)){
				//EMS1.1.0新增檢核此假別是否可以申請。 20140609 by Hedwig
				checkva_tools.checkIsAskForLeave(l_actionErrors, request, conn, EHF020200T0_03, EHF020200T0_09, EHF020200T0_14);
				
				//EMS1.1.0新增檢核請假日期是否連續。 20140609 by Hedwig
				checkva_tools.checkContinuousData(l_actionErrors, request, conn, EHF020200T0_01, EHF020200T0_09, EHF020200T0_10,EHF020200T0_14, EHF020200T0_03);
				}
				
				addData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				
				ve.isEmpty(l_actionErrors, EHF020200T0_03, "EHF020200T0_03", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020200T0_14, "EHF020200T0_14", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020200T0_09, "EHF020200T0_09", "不可空白");
				ve.isEmpty(l_actionErrors, EHF020200T0_10, "EHF020200T0_10", "不可空白");
//				ve.isEmpty(l_actionErrors, EHF020200T0_07, "EHF020200T0_07_SHOW", "不可空白");
				
//				if(EHF020200T0_07.equals(EHF020200T0_03)){//若代理人與申請人相同
//					
//					l_actionErrors.add("EHF020200T0_07_SHOW", new ActionMessage("代理人不能為申請人"));
//										
//				}else if(!EHF020200T0_07.equals(EHF020200T0_03)){//若代理人與申請人不同
//					
//					//檢核代理人一天有無代理超過一張假單							
//					if(!"".equals(EHF020200T0_07)){//若代理人欄位不為空		
//						
//							checkva_tools.checkAgent(l_actionErrors, request , conn, EHF020200T0_07,EHF020200T0_01,
//													 EHF020200T0_09,EHF020200T0_10,"代理人重複");
//							
//					}
//				}	
				
				//檢核請假期間是否有例假日問題
				//於EMS1.1.0時可以選擇六日的是否計算到請假天數，因此不能檢核請假區間是否為例假日。 20140610 by Hedwig
//				checkva_tools.vacation_holiday_validate(l_actionErrors, request, conn, EHF020200T0_09, EHF020200T0_11_HH+EHF020200T0_11_MM,
//														EHF020200T0_10, EHF020200T0_12_HH+EHF020200T0_12_MM);
				
				//檢核請假期間是否符合邏輯
				checkva_tools.checkVacationDateTime(l_actionErrors, request, conn, EHF020200T0_03, EHF020200T0_09, 
												    EHF020200T0_11_HH+EHF020200T0_11_MM, EHF020200T0_10,
													EHF020200T0_12_HH+EHF020200T0_12_MM, "1");
				
				//檢核請假年度月份是否符合邏輯
				checkva_tools.checkVacationYEARMONTH(l_actionErrors, request, EHF020200T0_09, EHF020200T0_10);
				
				//檢核同一時間區間內是否有已完成的請假單
				checkva_tools.checkConflictVacationForm(l_actionErrors, request, conn, EHF020200T0_03, 
													    EHF020200T0_09, EHF020200T0_11_HH+EHF020200T0_11_MM, 
													    EHF020200T0_10, EHF020200T0_12_HH+EHF020200T0_12_MM );
				
				//EMS1.1.0新增檢核此假別是否可以申請。 20140609 by Hedwig
				checkva_tools.checkIsAskForLeave(l_actionErrors, request, conn, EHF020200T0_03, EHF020200T0_09, EHF020200T0_14);
				
				//EMS1.1.0新增檢核請假日期是否連續。 20140609 by Hedwig
				checkva_tools.checkContinuousData(l_actionErrors, request, conn, EHF020200T0_01, EHF020200T0_09, EHF020200T0_10,EHF020200T0_14, EHF020200T0_03);
				
				EMS_GetEmsFlowInf emsflowinf_tools = EMS_GetEmsFlowInf.getInstance();
				
				EMS_CaculateVacationByCal cacuva_tools = new EMS_CaculateVacationByCal();
				Map result = cacuva_tools.caculateRealVa( conn, emsflowinf_tools.getAuthbean(request), EHF020200T0_14, EHF020200T0_09, EHF020200T0_10,
						 	 EHF020200T0_11_HH+EHF020200T0_11_MM, EHF020200T0_12_HH+EHF020200T0_12_MM);
				
				String error ="";
				if(result.get("ERROR_TIME_START")!=null){
					error+=" "+result.get("ERROR_TIME_START");
				}
				if(result.get("ERROR_TIME_END")!=null){
					error+=" "+result.get("ERROR_TIME_END");
				}
				if(result.get("ERROR_START")!=null){
					error+=" "+result.get("ERROR_START");
				}
				if(result.get("ERROR_END")!=null){
					error+=" "+result.get("ERROR_END");
				}
				if(!"".equals(error)){
					l_actionErrors.add("EHF020200T0_09",new ActionMessage(""));  
					l_actionErrors.add("EHF020200T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+error+" ");
				}
				
				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {
				
				delData_validate(l_actionErrors, request, conn);
			}
			//核准時判斷條件
			if (request.getAttribute("action").equals("approve")) {
				
				this.checkVacationRule(l_actionErrors, request, conn, request.getAttribute("compid").toString());
			}
			//確認資料時判斷條件
			if (request.getAttribute("action").equals("confirm")) {
				
				this.checkVacationRule(l_actionErrors, request, conn, request.getAttribute("compid").toString());
			}

		}
		
		//處理因為儲存檢核失敗造成無法儲存的問題
		if(!l_actionErrors.isEmpty()){
			request.setAttribute("personself", "yes");
			request.setAttribute("Form5Datas", flow_sign_log_list);
		}
		
		return l_actionErrors;
	}

	private void delData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String arrid[] = request.getParameterValues("checkId");
		String comp_id = (String) request.getAttribute("compid");
		
		try{
			Statement stmt = conn.createStatement();
			String sql = " SELECT * FROM EHF020200T0 " +
						 " WHERE 1=1 " +
						 " AND EHF020200T0_01 = '"+arrid[0]+"' " +
					     " AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
					     " ORDER BY EHF020200T0_01 ";
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				if( "0002".equals(rs.getString("EHF020200T0_16")) || "0004".equals(rs.getString("EHF020200T0_16")) 
						 || "0006".equals(rs.getString("EHF020200T0_16")) || "0009".equals(rs.getString("EHF020200T0_16"))){
					errors.add("EHF020200T0_16",new ActionMessage(""));
					request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
						request.getAttribute("ErrMSG")+"<br>")+"刪除失敗，該表單狀態不可執行刪除!!" + "<br>");
				}
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			String sql = "" +
			" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
			" FROM EHF020900T0 LEFT JOIN EHF020200T0 ON " +
			" EHF020900T0_M2 BETWEEN DATE_FORMAT(EHF020200T0_09, '%Y/%m') AND DATE_FORMAT(EHF020200T0_10, '%Y/%m')" +
			" AND EHF020900T0_04=EHF020200T0_18" +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+arrid[0]+"' " +
		    " AND EHF020200T0_18 = '"+comp_id+"' " +  //公司代碼
			//" AND EHF020900T0_M2 BETWEEN '"+2013/04+"' AND '"+2013/04+"'"+	//考勤年月
			" AND EHF020900T0_04 = '"+comp_id+"'" +//公司代碼
			" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

	    	Statement stmt_NEW = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_NEW = stmt_NEW.executeQuery(sql);
			
			while(rs_NEW.next()){
				errors.add("EHF020200T0_09",new ActionMessage(""));
				errors.add("EHF020200T0_10",new ActionMessage(""));
				request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
					request.getAttribute("ErrMSG")+"<br>")+"考勤已確認，不可刪除" + "<br>");
			}
			rs_NEW.close();
			stmt_NEW.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	private void saveData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String EHF020200T0_09_year[]=EHF020200T0_09.split("/");
		String EHF020200T0_10_year[]=EHF020200T0_10.split("/");
		try {
		
		//檢核剩餘時數夠不夠扣 → 與學長討論之後將新增與儲存的檢核拿掉
		//this.checkVacationRule(errors, request, conn, request.getAttribute("compid").toString());
			
		//驗證考勤是否確認
		String sql = "" +
		" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
		" FROM EHF020900T0 " +
		" WHERE 1=1 " +
		" AND (EHF020900T0_M2 = '"+EHF020200T0_09_year[0]+"/"+EHF020200T0_09_year[1]+"' OR "+	//考勤年月
		"      EHF020900T0_M2 = '"+EHF020200T0_10_year[0]+"/"+EHF020200T0_10_year[1]+"')"+
		" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
		" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

    	Statement stmt_01;
		
			stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs_01 = stmt_01.executeQuery(sql);
		
		while(rs_01.next()){
			if("02".equals(rs_01.getString("EHF020900T0_02"))||"03".equals(rs_01.getString("EHF020900T0_02")))
			//error_year+=rs_01.getString("EHF020900T0_M2");
				errors.add("EHF020200T0_09",new ActionMessage(""));  
			errors.add("EHF020200T0_10",new ActionMessage(""));
			request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
				request.getAttribute("ErrMSG")+"<br>")+rs_01.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認" + "<br>");
		
		}
		rs_01.close();
		stmt_01.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void addData_validate(ActionErrors errors,
			HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
		
		String EHF020200T0_09_year[]=EHF020200T0_09.split("/");
		String EHF020200T0_10_year[]=EHF020200T0_10.split("/");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try {
		//檢核剩餘時數夠不夠扣 → 與學長討論之後將新增與儲存的檢核拿掉
		//this.checkVacationRule(errors, request, conn, request.getAttribute("compid").toString());

		//驗證考勤是否確認
		String sql = "" +
		" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
		" FROM EHF020900T0 " +
		" WHERE 1=1 " +
		" AND (EHF020900T0_M2 = '"+EHF020200T0_09_year[0]+"/"+EHF020200T0_09_year[1]+"' OR "+	//考勤年月
		"      EHF020900T0_M2 = '"+EHF020200T0_10_year[0]+"/"+EHF020200T0_10_year[1]+"')"+
		" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
		" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

    	Statement stmt_01;
		
			stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		ResultSet rs_01 = stmt_01.executeQuery(sql);
		
		while(rs_01.next()){
			if("02".equals(rs_01.getString("EHF020900T0_02"))||"03".equals(rs_01.getString("EHF020900T0_02")))
			//error_year+=rs_01.getString("EHF020900T0_M2");
				errors.add("EHF020200T0_09",new ActionMessage(""));  
			errors.add("EHF020200T0_10",new ActionMessage(""));
			request.setAttribute("ErrMSG",(request.getAttribute("ErrMSG")==null?"":
				request.getAttribute("ErrMSG")+"<br>")+rs_01.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認" + "<br>");
		
		}
		rs_01.close();
		stmt_01.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			String comp_id = (String) request.getAttribute("compid");
			
			int CountLimit = Integer.parseInt("".equals(tools.getSysParam(conn, comp_id, "TRAILCOUNTLIMIT"))?
					"0":tools.getSysParam(conn, comp_id, "TRAILCOUNTLIMIT"));
			
			if(CountLimit > 0){
				//試用版限制只能新增至n筆
				//若TRAILCOUNTLIMIT = 0, 則為正常版
				String sql = "SELECT COUNT(*) FROM EHF020200T0";
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql);
				if(rs.next()){
					if(!(Integer.parseInt(rs.getString("COUNT(*)")) < CountLimit)){
						errors.add("EHF020200T0_01",new ActionMessage(""));
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":
							request.getAttribute("MSG")+"<br>")+"此版本為試用版本, 無法繼續新增請假單, 請洽詢您的經銷商做處理");
					}
				}
				rs.close();
				stmt.close();
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 請假單新增或修改時進行檢核
	 * @param errors
	 * @param request
	 * @param conn
	 * @param EHF020200T0_01
	 * @param comp_id
	 */
	public void checkVacationRule( ActionErrors errors, HttpServletRequest request, 
							   Connection conn, String comp_id){
		
		try{			
			
			//判斷呈核時是否檢核假別資訊 edit by joe 2012/05/16
			if("ON".equals(tools.getSysParam(conn, comp_id, "VACATION_CHECK_BY_VA_TYPE"))){
				
				//請假單呈核進行檢核
				this.checkVacation(errors, request, conn, comp_id);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}	
	/**
	 * 請假單新增或修改時進行檢核
	 * @param errors
	 * @param request
	 * @param conn
	 * @param EHF020200T0_01
	 * @param comp_id
	 */
	public void checkVacation( ActionErrors errors, HttpServletRequest request, 
							   Connection conn, String comp_id){
		
		try{			
			//檢核請假單資料
			Map error_map = this.checkVacationData(conn, request, comp_id);
			
			
			
			//處理請假單錯誤訊息
			this.handleErrorMSG(errors, request, conn, (Integer)error_map.get("ERROR_TYPE"), (String)error_map.get("VACATION_TYPE"), comp_id);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 檢查請假單資料
	 * @param conn
	 * @param EHF020200T0_01
	 * @param comp_id
	 * @return
	 */
	public Map checkVacationData( Connection conn, HttpServletRequest request, String comp_id){
		
		Map return_Map = new HashMap();
		
		try{
			/*
			String sql = "" +
			 " SELECT * , EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
			 " FROM EHF020200T0 " +
			 " WHERE 1=1 " +
			 " AND EHF020200T0_01 = ? " +
			 " AND EHF020200T0_18 = ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);

			p6stmt.setString(1, EHF020200T0_01);  //請假單單號
			p6stmt.setString(2, comp_id);  //公司代碼
			
//			System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());

			ResultSet rs = pstmt.executeQuery();
			*/
			if(request.getAttribute("action").equals("addData")){
				EMS_CaculateVacationByCal cacuva_tools = new EMS_CaculateVacationByCal();
				AuthorizedBean user_authbean = new AuthorizedBean();
				user_authbean.setEmployeeID(EHF020200T0_03);
//				System.out.println("setEmployeeID:"+Form.getEHF020200T0_03());
				user_authbean.setCompId(comp_id);
//				user_authbean.setUserId(getLoginUser(request).getUserId());
//				user_authbean.setUserCode(getLoginUser(request).getUserCode());
				Map result = cacuva_tools.caculateRealVa( conn, user_authbean, EHF020200T0_14, EHF020200T0_09, EHF020200T0_10,
						 EHF020200T0_11_HH+EHF020200T0_11_MM, EHF020200T0_12_HH+EHF020200T0_12_MM);
				EHF020200T0_13_DAY=(String)result.get("DAYS");
				EHF020200T0_13_HOUR=(String)result.get("HOURS");
			}
			
			
			//檢核勞基法相關規定(病假,事假,特休,補休 其餘假由人資經辦判定)  -- 呈核時檢核
			int error_type = 0;
			
//			if(rs.next()){
			System.out.println("EHF020200T0_09:"+EHF020200T0_09);
				error_type = ems_checkVacation.checkVacationHours( conn, EHF020200T0_03, 
							String.valueOf(Integer.parseInt(EHF020200T0_09.split("/")[0])-1911), EHF020200T0_09, EHF020200T0_10, EHF020200T0_14,
							EHF020200T0_13_DAY+"/"+EHF020200T0_13_HOUR, comp_id);
				
				return_Map.put("ERROR_TYPE", error_type);
				return_Map.put("VACATION_TYPE", EHF020200T0_14);
//			}
			
//			rs.close();
//			pstmt.close();
//			p6stmt.close();
					
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_Map;
	}
	
	/**
	 * 處理檢核錯誤訊息
	 * @param errors
	 * @param request
	 * @param conn
	 * @param error_type
	 * @param vacation_type
	 * @param comp_id
	 */
	private void handleErrorMSG(ActionErrors errors, HttpServletRequest request, 
						  	   Connection conn, int error_type, String vacation_type, String comp_id ){
		
		try{
			switch(error_type){
			
			case 1:
				//該假別時數已超過年度假別請假總時數
				errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
				errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						this.generateErrorMSG(conn, error_type, vacation_type, comp_id));
			break;
			
			case 2:
				//該假別時數已超過年度假別請假總時數
				errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
				errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						this.generateErrorMSG(conn, error_type, vacation_type, comp_id));
			break;
			
			case 3:
				//該假別剩餘時數不足，檢核失敗
				errors.add("EHF020200T0_13_DAY",new ActionMessage(""));
				errors.add("EHF020200T0_13_HOUR",new ActionMessage(""));
				errors.add("EHF020200T0_14",new ActionMessage(""));
				request.setAttribute("ErrMSG",
						(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
						this.generateErrorMSG(conn, error_type, vacation_type, comp_id));
			break;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 產生檢核錯誤訊息
	 * @param conn
	 * @param error_type
	 * @param vacation_type
	 * @param comp_id
	 * @return
	 */
	private String generateErrorMSG(Connection conn, int error_type, String vacation_type, String comp_id ){
		
		String ERROR_MSG = "";
		float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));
		try{
			//取得系統假別設定資訊
			EHF020100M0F ehf020100m0 = ems_checkVacation.getVacationTypeInf(conn, vacation_type, comp_id);
			String work_hourss=(Float.parseFloat(ehf020100m0.getEHF020100T0_05_day()) * Day_work_hours) + Float.parseFloat(ehf020100m0.getEHF020100T0_05_day())+"";
			String work_hourss_06=(Float.parseFloat(ehf020100m0.getEHF020100T0_06_day()) * Day_work_hours) + Float.parseFloat(ehf020100m0.getEHF020100T0_06_day())+"";
			
			if( ehf020100m0 != null ){
				switch(error_type){
				
				case 1:
					//該假別時數已超過年度假別請假總時數
					ERROR_MSG = "該假別的單次最低請假時數:"+work_hourss_06+"小時, 請再次確認!!";
				break;
				
				case 2:
					//該假別時數已超過年度假別請假總時數
					ERROR_MSG = "該假別請假時數已超過年度請假總時數:"+work_hourss+"小時, 請再次確認!!";
				break;
				
				case 3:
					//該假別剩餘時數不足，檢核失敗
					ERROR_MSG = "假別剩餘時數不足!!請再次確認!!";
				break;
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ERROR_MSG;
	}
	
	public String getEHF020200T0_01() {
		return EHF020200T0_01;
	}

	public void setEHF020200T0_01(String eHF020200T0_01) {
		EHF020200T0_01 = eHF020200T0_01;
	}

	public String getEHF020200T0_02() {
		return EHF020200T0_02;
	}

	public void setEHF020200T0_02(String eHF020200T0_02) {
		EHF020200T0_02 = eHF020200T0_02;
	}

	public String getEHF020200T0_03() {
		return EHF020200T0_03;
	}

	public void setEHF020200T0_03(String eHF020200T0_03) {
		EHF020200T0_03 = eHF020200T0_03;
	}

	public String getEHF020200T0_03_TXT() {
		return EHF020200T0_03_TXT;
	}

	public void setEHF020200T0_03_TXT(String eHF020200T0_03TXT) {
		EHF020200T0_03_TXT = eHF020200T0_03TXT;
	}

	public String getEHF020200T0_04() {
		return EHF020200T0_04;
	}

	public void setEHF020200T0_04(String eHF020200T0_04) {
		EHF020200T0_04 = eHF020200T0_04;
	}

	public String getEHF020200T0_04_TXT() {
		return EHF020200T0_04_TXT;
	}

	public void setEHF020200T0_04_TXT(String eHF020200T0_04TXT) {
		EHF020200T0_04_TXT = eHF020200T0_04TXT;
	}

	public String getEHF020200T0_05() {
		return EHF020200T0_05;
	}

	public void setEHF020200T0_05(String eHF020200T0_05) {
		EHF020200T0_05 = eHF020200T0_05;
	}

	public String getEHF020200T0_05_TXT() {
		return EHF020200T0_05_TXT;
	}

	public void setEHF020200T0_05_TXT(String eHF020200T0_05TXT) {
		EHF020200T0_05_TXT = eHF020200T0_05TXT;
	}

	public String getEHF020200T0_06() {
		return EHF020200T0_06;
	}

	public void setEHF020200T0_06(String eHF020200T0_06) {
		EHF020200T0_06 = eHF020200T0_06;
	}

	public String getEHF020200T0_06_TXT() {
		return EHF020200T0_06_TXT;
	}

	public void setEHF020200T0_06_TXT(String eHF020200T0_06TXT) {
		EHF020200T0_06_TXT = eHF020200T0_06TXT;
	}

	public String getEHF020200T0_07() {
		return EHF020200T0_07;
	}

	public void setEHF020200T0_07(String eHF020200T0_07) {
		EHF020200T0_07 = eHF020200T0_07;
	}

	public String getEHF020200T0_07_TXT() {
		return EHF020200T0_07_TXT;
	}

	public void setEHF020200T0_07_TXT(String eHF020200T0_07TXT) {
		EHF020200T0_07_TXT = eHF020200T0_07TXT;
	}

	public String getEHF020200T0_08() {
		return EHF020200T0_08;
	}

	public void setEHF020200T0_08(String eHF020200T0_08) {
		EHF020200T0_08 = eHF020200T0_08;
	}

	public String getEHF020200T0_09() {
		return EHF020200T0_09;
	}

	public void setEHF020200T0_09(String eHF020200T0_09) {
		EHF020200T0_09 = eHF020200T0_09;
	}

	public String getEHF020200T0_10() {
		return EHF020200T0_10;
	}

	public void setEHF020200T0_10(String eHF020200T0_10) {
		EHF020200T0_10 = eHF020200T0_10;
	}

	public String getEHF020200T0_11() {
		return EHF020200T0_11;
	}

	public void setEHF020200T0_11(String eHF020200T0_11) {
		EHF020200T0_11 = eHF020200T0_11;
	}

	public String getEHF020200T0_11_HH() {
		return EHF020200T0_11_HH;
	}

	public void setEHF020200T0_11_HH(String eHF020200T0_11HH) {
		EHF020200T0_11_HH = eHF020200T0_11HH;
	}

	public String getEHF020200T0_11_MM() {
		return EHF020200T0_11_MM;
	}

	public void setEHF020200T0_11_MM(String eHF020200T0_11MM) {
		EHF020200T0_11_MM = eHF020200T0_11MM;
	}

	public String getEHF020200T0_12() {
		return EHF020200T0_12;
	}

	public void setEHF020200T0_12(String eHF020200T0_12) {
		EHF020200T0_12 = eHF020200T0_12;
	}

	public String getEHF020200T0_12_HH() {
		return EHF020200T0_12_HH;
	}

	public void setEHF020200T0_12_HH(String eHF020200T0_12HH) {
		EHF020200T0_12_HH = eHF020200T0_12HH;
	}

	public String getEHF020200T0_12_MM() {
		return EHF020200T0_12_MM;
	}

	public void setEHF020200T0_12_MM(String eHF020200T0_12MM) {
		EHF020200T0_12_MM = eHF020200T0_12MM;
	}

	public String getEHF020200T0_13() {
		return EHF020200T0_13;
	}

	public void setEHF020200T0_13(String eHF020200T0_13) {
		EHF020200T0_13 = eHF020200T0_13;
	}

	public String getEHF020200T0_13_DAY() {
		return EHF020200T0_13_DAY;
	}

	public void setEHF020200T0_13_DAY(String eHF020200T0_13DAY) {
		EHF020200T0_13_DAY = eHF020200T0_13DAY;
	}

	public String getEHF020200T0_13_HOUR() {
		return EHF020200T0_13_HOUR;
	}

	public void setEHF020200T0_13_HOUR(String eHF020200T0_13HOUR) {
		EHF020200T0_13_HOUR = eHF020200T0_13HOUR;
	}

	public String getEHF020200T0_14() {
		return EHF020200T0_14;
	}

	public void setEHF020200T0_14(String eHF020200T0_14) {
		EHF020200T0_14 = eHF020200T0_14;
	}

	public String getEHF020200T0_14_DESC() {
		return EHF020200T0_14_DESC;
	}

	public void setEHF020200T0_14_DESC(String eHF020200T0_14DESC) {
		EHF020200T0_14_DESC = eHF020200T0_14DESC;
	}

	public String getEHF020200T0_15() {
		return EHF020200T0_15;
	}

	public void setEHF020200T0_15(String eHF020200T0_15) {
		EHF020200T0_15 = eHF020200T0_15;
	}

	public String getEHF020200T0_16() {
		return EHF020200T0_16;
	}

	public void setEHF020200T0_16(String eHF020200T0_16) {
		EHF020200T0_16 = eHF020200T0_16;
	}

	public String getEHF020200T0_16_DESC() {
		return EHF020200T0_16_DESC;
	}

	public void setEHF020200T0_16_DESC(String eHF020200T0_16DESC) {
		EHF020200T0_16_DESC = eHF020200T0_16DESC;
	}

	public String getEHF020200T0_17() {
		return EHF020200T0_17;
	}

	public void setEHF020200T0_17(String eHF020200T0_17) {
		EHF020200T0_17 = eHF020200T0_17;
	}

	public String getEHF020200T0_18() {
		return EHF020200T0_18;
	}

	public void setEHF020200T0_18(String eHF020200T0_18) {
		EHF020200T0_18 = eHF020200T0_18;
	}

	public String getUSER_CREATE() {
		return USER_CREATE;
	}

	public void setUSER_CREATE(String uSERCREATE) {
		USER_CREATE = uSERCREATE;
	}

	public String getUSER_UPDATE() {
		return USER_UPDATE;
	}

	public void setUSER_UPDATE(String uSERUPDATE) {
		USER_UPDATE = uSERUPDATE;
	}

	public int getVERSION() {
		return VERSION;
	}

	public void setVERSION(int vERSION) {
		VERSION = vERSION;
	}

	public String getDATE_UPDATE() {
		return DATE_UPDATE;
	}

	public void setDATE_UPDATE(String dATEUPDATE) {
		DATE_UPDATE = dATEUPDATE;
	}

	public List getEHF020200C() {
		return EHF020200C;
	}

	public void setEHF020200C(List eHF020200C) {
		EHF020200C = eHF020200C;
	}

	public void setEHF020200T0_03_SHOW(String eHF020200T0_03_SHOW) {
		EHF020200T0_03_SHOW = eHF020200T0_03_SHOW;
	}

	public String getEHF020200T0_03_SHOW() {
		return EHF020200T0_03_SHOW;
	}

	public void setEHF020200T0_04_SHOW(String eHF020200T0_04_SHOW) {
		EHF020200T0_04_SHOW = eHF020200T0_04_SHOW;
	}

	public String getEHF020200T0_04_SHOW() {
		return EHF020200T0_04_SHOW;
	}

	public void setEHF020200T0_05_SHOW(String eHF020200T0_05_SHOW) {
		EHF020200T0_05_SHOW = eHF020200T0_05_SHOW;
	}

	public String getEHF020200T0_05_SHOW() {
		return EHF020200T0_05_SHOW;
	}

	public void setEHF020200T0_06_SHOW(String eHF020200T0_06_SHOW) {
		EHF020200T0_06_SHOW = eHF020200T0_06_SHOW;
	}

	public String getEHF020200T0_06_SHOW() {
		return EHF020200T0_06_SHOW;
	}

	public void setUPLOADFILE(FormFile uPLOADFILE) {
		UPLOADFILE = uPLOADFILE;
	}

	public FormFile getUPLOADFILE() {
		return UPLOADFILE;
	}

	public void setEHF020200T0_07_SHOW(String eHF020200T0_07_SHOW) {
		EHF020200T0_07_SHOW = eHF020200T0_07_SHOW;		
	}
	
	public String getEHF020200T0_07_SHOW() {
		return EHF020200T0_07_SHOW;
	}

	public void setSIGN_COMMENT(String sIGN_COMMENT) {
		SIGN_COMMENT = sIGN_COMMENT;
	}

	public String getSIGN_COMMENT() {
		return SIGN_COMMENT;
	}

}
