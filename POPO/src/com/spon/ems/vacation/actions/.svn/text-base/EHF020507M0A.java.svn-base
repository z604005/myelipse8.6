package com.spon.ems.vacation.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Colour;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;

import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.ems.vacation.forms.EX020507R0F;

import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;

import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;

import fr.improve.struts.taglib.layout.util.FormUtils;
/**
 * (Action)員工刷卡記錄表
 * @author SPONPC2
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020507M0A extends NewDispatchAction {
	
	private BA_TOOLS tools ;
	private EMS_ACCESS ems_access;
	
	private float work_hours;
	
	public EHF020507M0A(){
		tools = BA_TOOLS.getInstance();
		ems_access = EMS_ACCESS.getInstance();
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
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
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
			
			String day[]=(tools.ymdTostring(tools.getSysDate())).split("/");
			
			
			//設定日期區間初始值
			Form.setDATA13(day[0]+"/"+day[1]);
			//Form.setDATA14(tools.ymdTostring(tools.getSysDate()));
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//當前登入者權限
			String HR 		= tools.getSysParam(conn, authbean.getCompId(),"HR_IDENTITY");		//預設人資		100
			String SYSTEM 	= tools.getSysParam(conn, authbean.getCompId(),"SYSTEM_IDENTITY");	//系統管理者	000
			String AC 		= tools.getSysParam(conn, authbean.getCompId(),"AC_IDENTITY");		//會計		110
			String BOSS 	= tools.getSysParam(conn, authbean.getCompId(),"BOSS_IDENTITY");	//老闆		111
			
			if(ems_access.checkEmsRoleEmp(conn, authbean, HR) || ems_access.checkEmsRoleEmp(conn, authbean, BOSS)){
				//人事經辦
			}else if(ems_access.checkEmsRoleEmp(conn, authbean, SYSTEM)){
				//系統管理者
			}else{
				//一般員工
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				Form.setDATA12( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("USER_CODE") );  //申請人員工工號
				Form.setDATA15( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_ID") );
				Form.setDATA21( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_NAME") );  //申請人姓名
				Form.setDATA11( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID") );  //申請人部們組織代號
				Form.setDATA14( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DEPT_ID") );
				Form.setDATA20( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DESC") );  //申請人部們名稱
			}
			
			hr_tools.close();
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
			this.generateSelectBox(conn, Form, request);
			request.setAttribute("Form1Datas", Form);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	protected void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request) {
			
		//產生考勤類別
		try{
			List listDATA15 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("全部考勤資料");
			listDATA15.add(bform);
			bform = new BA_ALLKINDForm();
//			bform.setItem_id("02");
//			bform.setItem_value("異常考勤資料");
//			listDATA15.add(bform);
			request.setAttribute("listDATA15", listDATA15);
		}catch(Exception e) {
			System.out.println(e);
		}
		//產生--日期類別-- 選單
		try{
			List listDATA05 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("月份");
			listDATA05.add(bform);
			request.setAttribute("listDATA05", listDATA05);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			List listDATA06 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("出勤明細表");
			listDATA06.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("加班明細表");
			listDATA06.add(bform);
			request.setAttribute("listDATA06", listDATA06);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public ActionForward print_select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
	
		try{
			if("01".equals(Form.getDATA15())){
				//列印全部考勤資料
				return this.print2(mapping, Form, request, response);
			}else if("02".equals(Form.getDATA15())){
				//列印異常考勤資料
				//return this.print3(mapping, Form, request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward("success");
	}

	/**
	 * 列印查詢結果 (全部)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward print2(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//HR_TOOLS hr_tools = HR_TOOLS.getInstance();
		
//		this.getSelectOption(request);
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		request.setAttribute("Form1Datas",Form);
		Connection conn = null;
		ArrayList list = new ArrayList();
		ActionErrors lc_errors = new ActionErrors();
		
		int count=0;
		int number=1;
		Map empInfMap= new HashMap();	
		/*
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?true:arrid[0].equals(""))){
    		lc_errors.add("",new ActionMessage("請選擇要列印的文件"));
    		request.setAttribute("MSG","請選取要列印的資料!!");
    	}
    	*/
		
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
    	BA_Vaildate ve = BA_Vaildate.getInstance();
		

		if (!lc_errors.isEmpty()) {
			saveErrors(request, lc_errors);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			this.generateSelectBox(conn, Form, request);
			
			return mapping.findForward("success");
		}
		Calendar cal = null;
        String nowtime = "";
       
		try {
			//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			
			String path = "";

			
			//一天工時
			 work_hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
			
			
			path = getServlet().getServletContext().getRealPath("");
			
			this.generateSelectBox(conn, Form, request);

			EX020507R0F ef = new EX020507R0F(conn,path,getLoginUser(request).getEmployeeID(),request);
			
			try{

				int DataCount =0;
				
				//確認表單類型
				String form_type = "";
				if(!"".equals(Form.getDATA15()) && Form.getDATA15() != null ){
					form_type = Form.getDATA15();
				}
				//公司代碼
				String comp_id = authbean.getCompId();
				
				HR_TOOLS hr_tools = new HR_TOOLS();

				//取得公司名稱
				Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
				//員工Map
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				//部門Map
				Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
				//職務
				Map titleMap = hr_tools.getTitleNameMap(authbean.getCompId());
				
				hr_tools.close();
				
				String empName="";

				
				if(!"".equals(Form.getDATA12())||!"".equals(Form.getDATA11())){
					//整理Map，如有指定部門與員工，將會被剔除，只會留下指定的部門或是員工
					Iterator iter_emp = empMap.entrySet().iterator(); 
					while (iter_emp.hasNext()) {
						Map.Entry entry = (Map.Entry) iter_emp.next(); 
					    String key = (String)entry.getKey(); 
					    if(!"".equals(Form.getDATA12())){
					    	//當有指定員工，必須將其餘人員存起來，之後從Map刪除
					    	if(!key.equals(Form.getDATA12())){
					    		empName+=key+",";
					    	}	
					    }else if(!"".equals(Form.getDATA11())){
					    	//指定部門時，必須將非此部門的員工剔除
					    	String dep = (String)((Map)empMap.get(key)).get("DEPT_ID");//部門
					    	if(!dep.equals(Form.getDATA11())){
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
				
				}
				
				
				boolean chk_run_flag = true;
				Calendar month_start_cal = null;
				Calendar month_end_cal = null;
				Iterator iter = empMap.entrySet().iterator(); 
				Map HeadValue = new HashMap();
				
				String type=Form.getDATA06();
				
				//取得不計算考勤名單
				Map EHF030200T0_17 = new HashMap();
				//取得所有人的考勤狀況
				Map EMPLOYEE_IDMap= new HashMap();
				//取得當月的行事曆公休天
				Map NotWorkDay= new HashMap();
				
				//取得所有人的考勤狀況
				Map Ask_for_leave= new HashMap();
				
				Map over_tome= new HashMap();
				
				
				String FirstMonthDay=tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(Form.getDATA13()+"/01")));
				String LastMonthDay =tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(Form.getDATA13()+"/01")));
				
				
				this.SELECT_EHF030200T0_17(conn, comp_id,Form.getDATA11(),Form.getDATA12(), EHF030200T0_17);
				//取得考勤狀況
				this.getEMPLOYEE_IDMap(conn, comp_id,Form.getDATA11(),Form.getDATA12(),Form.getDATA13(), EMPLOYEE_IDMap);
				//取得請假狀況
				this.get_Ask_for_leave(conn, comp_id,Form.getDATA11(),Form.getDATA12(),Form.getDATA13(), Ask_for_leave);
				
				this.get_OVER_TIME(conn, comp_id,Form.getDATA11(),Form.getDATA12(),Form.getDATA13(), over_tome);
				
				
				
				//this.getNotWorkDay(conn, comp_id, FirstMonthDay,LastMonthDay,NotWorkDay);
				
				Statement stmt = null;
				ResultSet rs = null;
				
				//產生預排換班處理元件
				EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
				
				
				
				Map WorkDay= new HashMap();
				while (iter.hasNext()) {
					
					
				    Map.Entry entry = (Map.Entry) iter.next(); 
				    String key = (String)entry.getKey(); 
				    
				    String SEARCHSTATUS = (String)((Map)empMap.get(key)).get("SEARCHSTATUS");//在職狀態
				    String CHECKDATE    = (String)((Map)empMap.get(key)).get("CHECKDATE");//日期
				    
				    String dep = (String)((Map)empMap.get(key)).get("DEPT_ID");//部門
					String title = (String)((Map)empMap.get(key)).get("TITLE_NO");//職務代號
				    
					if(Integer.valueOf(SEARCHSTATUS)==2 || Integer.valueOf(SEARCHSTATUS)==4){
						
						String day []= Form.getDATA13().split("/");//列印年月
						
						String CHECKDATEday []= CHECKDATE.split("/");//離職日
						
						if(Integer.valueOf(day[0]+day[1])>Integer.valueOf(CHECKDATEday[0]+CHECKDATEday[1])){
							 continue;
						}
						
					}
					
					//取得排班表與行事曆
					ems_wsc_tools.getVacations(conn, empMap, depMap, comp_id, Form.getDATA13(),"", NotWorkDay);
					
					WorkDay=(Map) NotWorkDay.get(key);
					
					//取得查詢月份的第一天
					month_start_cal = tools.covertStringToCalendar(tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(Form.getDATA13()+"/01")))); // 月份開始日期Calendar
			    	//取得查詢月份的最後一天
					month_end_cal = tools.covertStringToCalendar(tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(Form.getDATA13()+"/01")))); // 月份結束日期Calendar
				    
					if(EHF030200T0_17.get(key)!=null){
				    	//不計算考勤，跳過
				    	continue;
				    }else{
				    	while(chk_run_flag){
				    	//開始列印報表
				    	
				    	HeadValue = new HashMap();
				    	HeadValue.put("COMP_NAME_C", compMap.get("COMP_NAME_C"));
				    	HeadValue.put("WeekDisplayName", "星期"+this.getWeekDisplayName(month_start_cal));
				    	HeadValue.put("EMPLOYEE_ID", key);
				    	HeadValue.put("SHOW_DESC", (String)((Map)depMap.get(dep)).get("SHOW_DESC"));//部門名稱(中文)
				    	HeadValue.put("DEPT_ID", (String)((Map)depMap.get(dep)).get("DEPT_ID"));//部門名稱(中文)
				    	HeadValue.put("EMPLOYEE_NAME", (String)((Map)empMap.get(key)).get("EMPLOYEE_NAME"));//員工姓名
				    	HeadValue.put("TITLE_NAME", (String)((Map)titleMap.get(title)).get("TITLE_NAME"));//職務名稱(中文)
				    	HeadValue.put("SHOW_MONTH", (Form.getDATA13()).substring(5, 7)+"月");
				    	HeadValue.put("CARD", (String)((Map)empMap.get(key)).get("CARD"));
//				    	HeadValue.put("FALSE", this.getTotle(conn, key, FirstMonthDay, LastMonthDay, work_hours, comp_id));
						
						if(month_start_cal.equals(month_end_cal)){
							chk_run_flag = false;
						}
						
						
						String cur_date = tools.covertDateToString(month_start_cal.getTime(), "yyyy/MM/dd");  //當前計算的考勤日期
						if(chk_run_flag){
							this.goPrint(conn,key,cur_date,ef,type,authbean.getCompId(), HeadValue,EMPLOYEE_IDMap,WorkDay,Ask_for_leave,over_tome);
						}else{
							this.goPrint(conn,key,cur_date,ef,type,authbean.getCompId(), HeadValue,EMPLOYEE_IDMap,WorkDay,Ask_for_leave,over_tome);
							
							
							
							
							while(ef.getNowPageRecordNum()<32){
								ef.next();//下一筆
							}
							
							ef.setDetail(1, "G", String.valueOf(ef.objTotal[0][0]),false); //"工作時數
							ef.setDetail(1, "H", String.valueOf(ef.objTotal[0][1]),false); //"工作時數
							
							ef.setNowPage();
							
							ef.objTotal[0][0]=0;
							ef.objTotal[0][1]=0;               
							               
							continue;
						}
		
				    	
						month_start_cal.add(Calendar.DAY_OF_MONTH, 1);
				    	}
				    }
				    
				    if(!chk_run_flag){
				    	chk_run_flag = true;	
				    }
				    count++;
				} 

//				以下方法，在列印時，可以出現遮罩   Alvin
				//傳入前端需要的檔名
				String name ="";
					
				
		        if(type.equals("01"))
					
					name="員工刷卡記錄表_出勤明細表.xls";
				else{
					name="員工刷卡記錄表_加班明細表.xls";
				}
				
				String FileName="";
				if(count>0){
					//String cur_date = tool.ymdTostring(tools.getSysDate());
					//存入檔案
					FileName=ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
					return init(mapping, form, request, response);
				
				}
				
			}catch(Exception E){
				E.printStackTrace();
				request.setAttribute("Form1Datas", form);
				request.setAttribute("Form2Datas",list);
				request.setAttribute("MSG","列印失敗!!");
			}finally{
//				ef.write();
			}
			
		}catch (Exception E) {
			E.printStackTrace();
//			this.getSelectOption(request);
		    request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return mapping.findForward("success");
	}

	



	

	

	private void goPrint(Connection conn, String key, String curDate, EX020507R0F ef, String type, String CompId, Map HeadValue, Map EMPLOYEE_IDMap, Map NotWorkDay, Map Ask_for_leave, Map over_tome) {
		// TODO Auto-generated method stub
			//紀錄員工的資料(部門+員工ID+考勤時間)
			String EMPLOYEE=(String)HeadValue.get("DEPT_ID")+key+curDate;
			float ALL_hours=0;
			float real_work_hours = 0;
			boolean chk_flag = false;//假日
			try {
				if(NotWorkDay.get(curDate)==null){
					//表示要上班
					chk_flag = true;
				}else if(NotWorkDay.get(curDate).equals("-1")){
					//表示假日
					chk_flag = false;
				}else{
					//表示要上班
					chk_flag = true;
				}
				EMS_VIEWDATAF Form_over_tome=(EMS_VIEWDATAF)over_tome.get(EMPLOYEE);
				
				if(EMPLOYEE_IDMap.get(EMPLOYEE)!=null){
					EMS_VIEWDATAF Form=(EMS_VIEWDATAF)EMPLOYEE_IDMap.get(EMPLOYEE);
					
					
					
					ef.setHeadValue(0, 1, "A", (String)HeadValue.get("COMP_NAME_C")+"刷卡紀錄表", false, ""); // 列印公司抬頭
					ef.setHeadValue(1, 3, "B", Form.getDATA01(),false, "");//卡號
					ef.setHeadValue(2, 3, "F", (String)HeadValue.get("SHOW_MONTH"), false, "");//月份
					ef.setHeadValue(3, 3, "I", tools.ymdTostring(tools.getSysDate()), false, ""); // 列印日期
					ef.setHeadValue(4, 4, "B", (String)HeadValue.get("SHOW_DESC"), false, ""); // 部門
					ef.setHeadValue(5, 4, "I", (String)HeadValue.get("EMPLOYEE_NAME"), false, ""); // 姓名
					ef.setHeadValue(6, 4, "F", (String)HeadValue.get("TITLE_NAME"), false, ""); // 職務
//					ef.setHeadValue(7, 39, "A", (String)HeadValue.get("FALSE"), false, "");
					ef.next();//下一筆
					
					
					
					
					
					if("01".equals(type)){
						if(chk_flag){
							//平常日
							ef.setDetail(1, "B", (String)HeadValue.get("WeekDisplayName"),false); //
							
							
							ef.setDetail(1, "C", Form.getDATA04()==null?"":(String)(Form.getDATA04().subSequence(0, 5)),false); // 上班
							ef.setDetail(1, "D", Form.getDATA06()==null?"":(String)(Form.getDATA06().subSequence(0, 5)),false); // 下班
							
							String ERR="";
							
							if(Form.getDATA04()==null || Form.getDATA06()==null){	//上班或下班
								ERR="考勤異常";
							}else{
								if(!Form.getDATA05().equals("0")||!Form.getDATA07().equals("0")){	//上班是否異常或下班是否異常
									ERR="考勤異常";
								}
							}
							
							if(Ask_for_leave.get(EMPLOYEE)!=null){
								//ERR+=Ask_for_leave.get(EMPLOYEE);
								EMS_VIEWDATAF Form1=(EMS_VIEWDATAF)Ask_for_leave.get(EMPLOYEE);
								if(Form1!=null){
									/*if(Form1.getDATA01()==null&&Form1.getDATA03()==null){	//刷卡類型上班或刷卡類型下班
										ERR+="";
									}else if(Form1.getDATA01()==null){
										//表示上班異常，下班被請假單修正
										ERR+=""+Form1.getDATA05();
									}else if(Form1.getDATA03()==null){
										//表示上班異常，下班被請假單修正
										ERR+=""+Form1.getDATA05();
									}else if(Form1.getDATA01()!=null&&Form1.getDATA03()!=null){
										//表示上下班都已經被修正
										ERR+=Form1.getDATA05();
									}else{
										//其餘例外，考勤異常
										ERR+="";
									}*/
									ERR+=Form1.getDATA05();
								}
							}
							ef.setDetail(1, "I", ERR,false); // 下班
						}else{
							//休假日
							ef.setDetail(1, "B", (String)HeadValue.get("WeekDisplayName"),false); //
							ef.setDetail(1, "C", "",false); // 上班
							ef.setDetail(1, "D", "",false); // 下班
							ef.setDetail(1, "I", "",false); // 備註
						}
					}else{
						//印加班單
						if(chk_flag){
							//平常日
							ef.setDetail(1, "B", (String)HeadValue.get("WeekDisplayName"),false); //
							ef.setDetail(1, "C", Form.getDATA04()==null?"":(String)(Form.getDATA04().subSequence(0, 5)),false); // 上班
							ef.setDetail(1, "D", Form.getDATA06()==null?"":(String)(Form.getDATA06().subSequence(0, 5)),false); // 下班
							ef.setDetail(1, "E", Form.getDATA08()==null?"":(String)(Form.getDATA08().subSequence(0, 5)),false); // 加班上班
							ef.setDetail(1, "F", Form.getDATA10()==null?"":(String)(Form.getDATA10().subSequence(0, 5)),false); // 加班下班
							
							if(Form_over_tome==null){
								
							}else{
								ef.setDetail(1, "G", Form_over_tome.getDATA01()==null?"0":Form_over_tome.getDATA01(),false); // 加班時數
								//ef.objTotal[0][1]=(long) Float.parseFloat(Form_over_tome.getDATA01()==null?"0":Form_over_tome.getDATA01());
							}
							EMS_VIEWDATAF Form1=(EMS_VIEWDATAF)Ask_for_leave.get(EMPLOYEE);
							String ERR="";
							
							if(Form.getDATA04()==null || Form.getDATA06()==null){	//上班或下班
								ERR="考勤異常";
							}else{
								if(!Form.getDATA05().equals("0")||!Form.getDATA07().equals("0")){	//上班是否異常或下班是否異常
									ERR="考勤異常";
								}
							}
							
							
							if(Form1!=null){
								/*if(Form1.getDATA01()==null&&Form1.getDATA03()==null){	//刷卡類型上班或刷卡類型下班
									ERR+="";
								}else if(Form1.getDATA01()==null){
									//表示上班異常，下班被請假單修正
									ERR+=""+Form1.getDATA05();
								}else if(Form1.getDATA03()==null){
									//表示上班異常，下班被請假單修正
									ERR+=""+Form1.getDATA05();
								}else if(Form1.getDATA01()!=null&&Form1.getDATA03()!=null){
									//表示上下班都已經被修正
									ERR+=Form1.getDATA05();
								}else{
									//其餘例外，考勤異常
									ERR+="";
								}*/
								ERR+=Form1.getDATA05();
							}

							if(Form_over_tome==null){
								
							}else{
								ALL_hours+=Float.valueOf((Form_over_tome.getDATA01()==null?"0":Form_over_tome.getDATA01())); // 加班時數
							}
							
							ef.objTotal[0][0]+= ALL_hours;//統計加班時數
							
							if("".equals(ERR)){
								
								ALL_hours+=Float.valueOf(work_hours);
								
							}else{
								if(Form1!=null){
									real_work_hours = work_hours - (Float.valueOf(Form1.getDATA06())+Float.valueOf(Form1.getDATA07()));
								}
								ALL_hours += real_work_hours;
							}
							
							ef.setDetail(1, "H", String.valueOf(ALL_hours),false); //"工作時數

							ef.objTotal[0][1]+= ALL_hours;//統計工作時數
							
							ef.setDetail(1, "I", ERR,false); //備註

						}else{
							//休假日
							ef.setDetail(1, "B", (String)HeadValue.get("WeekDisplayName"),false); //
							ef.setDetail(1, "E", Form.getDATA08()==null?"":(String)(Form.getDATA08().subSequence(0, 5)),false); // 上班
							ef.setDetail(1, "F", Form.getDATA10()==null?"":(String)(Form.getDATA10().subSequence(0, 5)),false); // 下班
							
							if(Form_over_tome==null){
								
							}else{
								ef.setDetail(1, "G", Form_over_tome.getDATA01()==null?"0":Form_over_tome.getDATA01(),false); // 加班時數
							}
							if(Form_over_tome==null){
								
							}else{
								ALL_hours+=Float.valueOf((Form_over_tome.getDATA01()==null?"0":Form_over_tome.getDATA01())); // 加班時數
							}
							
							
							ef.objTotal[0][0]+= ALL_hours;//統計加班時數
							ef.objTotal[0][1]+= ALL_hours;//統計工作時數
							
							
							ef.setDetail(1, "H", String.valueOf(ALL_hours),false); //"工作時數
							
							
							if(Form.getDATA08()==null||Form.getDATA10()==null)
								ef.setDetail(1, "I", "",false); // 備註
							else{
								ef.setDetail(1, "I", "",false); // 備註
							}
						}	
					}
				
				
				}else{
						//無考勤資料
						ef.setHeadValue(0, 1, "A", (String)HeadValue.get("COMP_NAME_C")+"刷卡紀錄表", false, ""); // 列印公司抬頭
						ef.setHeadValue(1, 3, "B", (String)HeadValue.get("CARD")==null?"":(String)HeadValue.get("CARD"),false, "");//卡號
						ef.setHeadValue(2, 3, "F", (String)HeadValue.get("SHOW_MONTH"), false, "");//月份
						ef.setHeadValue(3, 3, "I", tools.ymdTostring(tools.getSysDate()), false, ""); // 列印日期
						ef.setHeadValue(4, 4, "B", (String)HeadValue.get("SHOW_DESC"), false, ""); // 部門
						ef.setHeadValue(5, 4, "I", (String)HeadValue.get("EMPLOYEE_NAME"), false, ""); // 姓名
						ef.setHeadValue(6, 4, "F", (String)HeadValue.get("TITLE_NAME"), false, ""); // 職務
//						ef.setHeadValue(7, 39, "A", (String)HeadValue.get("FALSE"), false, "");
						ef.next();//下一筆
						if("01".equals(type)){
							//平日出勤明細表
							if(chk_flag){
								//平常日
								ef.setDetail(1, "B", (String)HeadValue.get("WeekDisplayName"),false); //
								EMS_VIEWDATAF Form1=(EMS_VIEWDATAF)Ask_for_leave.get(EMPLOYEE);
								String ERR="";
								if(Form1!=null){
									/*if(Form1.getDATA01()==null&&Form1.getDATA03()==null){
										ERR+="考勤異常";
									}else if(Form1.getDATA01()==null){
										//表示上班異常，下班被請假單修正
										ERR+="考勤異常"+Form1.getDATA05();
									}else if(Form1.getDATA03()==null){
										//表示上班異常，下班被請假單修正
										ERR+="考勤異常"+Form1.getDATA05();
									}else if(Form1.getDATA01()!=null&&Form1.getDATA03()!=null){
										//表示上下班都已經被修正
										if(Form1.getDATA02().equals(Form1.getDATA04())){	//表示同一張請假單
											if(Float.parseFloat(Form1.getDATA06()) > 8){
												ERR+=Form1.getDATA05()+"("+Form1.getDATA02()+")";
											}else{
												ERR+=Form1.getDATA05();
											}
										}else{	//不同一張請假單
											ERR+=Form1.getDATA05();
										}
									}else{
										//其餘例外，考勤異常
										ERR+="考勤異常";
									}*/
									ERR+="考勤異常"+Form1.getDATA05()+"("+Form1.getDATA01()+")";
								}else{
									ERR+="考勤異常";
								}

								ef.setDetail(1, "I", ERR,false); //備註
							}else{
								//休假日
								ef.setDetail(1, "B", (String)HeadValue.get("WeekDisplayName"),false); //
							}
							
						}else{
							//加班出勤明細表
							if(chk_flag){
								//平常日
								ef.setDetail(1, "B", (String)HeadValue.get("WeekDisplayName"),false); //
								EMS_VIEWDATAF Form1=(EMS_VIEWDATAF)Ask_for_leave.get(EMPLOYEE);
								String ERR="";
								if(Form1!=null){
									/*if(Form1.getDATA01()==null&&Form1.getDATA03()==null){
										ERR+="考勤異常";
									}else if(Form1.getDATA01()==null){
										//表示上班異常，下班被請假單修正
										ERR+="考勤異常"+Form1.getDATA05();
									}else if(Form1.getDATA03()==null){
										//表示上班異常，下班被請假單修正
										ERR+="考勤異常"+Form1.getDATA05();
									}else if(Form1.getDATA01()!=null&&Form1.getDATA03()!=null){
										//表示上下班都已經被修正
										if(Form1.getDATA02().equals(Form1.getDATA04())){	//表示同一張請假單
											if(Float.parseFloat(Form1.getDATA06()) > 8){
												ERR+=Form1.getDATA05()+"("+Form1.getDATA02()+")";
											}else{
												ERR+=Form1.getDATA05();
											}
										}else{	//不同一張請假單
											ERR+=Form1.getDATA05();
										}
									}else{
										//其餘例外，考勤異常
										ERR+="考勤異常";
									}*/
									ERR+="考勤異常"+Form1.getDATA05()+"("+Form1.getDATA01()+")";
								}else{
									ERR+="考勤異常";
								}
								
								if(Form_over_tome==null){
									
								}else{
									ef.setDetail(1, "G", Form_over_tome.getDATA01()==null?"0":Form_over_tome.getDATA01(),false); // 加班時數
								}
								
								
								
								ef.setDetail(1, "I", ERR,false); //備註
							}else{
								ef.setDetail(1, "B", (String)HeadValue.get("WeekDisplayName"),false); //
							}						
						}
					
					
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}


	/**
	  * 依據員工工號  取得感應卡號明細
	  * @param conn
	  * @param comp_id
	  * @param EHF030200T0_01
	 * @param rs 
	  * @return
	  */
	public String SELECT_EHF020403T1(Connection conn,String comp_id ,String EHF030200T0_01, Statement stmt, ResultSet rs) {
		String flag="無設定感應卡";
		try {
			//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
			//Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,	ResultSet.CONCUR_READ_ONLY);
			//ResultSet rs = null;
			String sql = " select * From EHF020403T1 where 1=1"
					+ " AND EHF020403T1_06 = '" + comp_id + "' "//公司代碼
					+ " AND EHF020403T1_01 = '" + EHF030200T0_01 + "' ";//員工工號
					//+ " AND EHF020403T0_02 = '" + EHF030200T0_02 + "' ";//部門代號
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				flag=rs.getString("EHF020403T1_02");	
			}
			//rs.close();
			//stmt.close();
			rs.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 統計不計算考勤的員工名單
	 * @param conn
	 * @param compId
	 * @param DEPT_ID
	 * @param EMPLOYEE_ID
	 * @param EHF030200T0_17
	 */
	private void SELECT_EHF030200T0_17(Connection conn, String compId, String DEPT_ID, String EMPLOYEE_ID, Map EHF030200T0_17) {
		// TODO Auto-generated method stub
		try {
			String sql = " select * From EHF020403T0 where 1=1"
				+ " AND EHF020403T0_04 = '" + compId + "' ";//公司代碼
				
				if(!"".equals(EMPLOYEE_ID)){
					sql+= " AND EHF020403T0_01 = '" + EMPLOYEE_ID + "' ";//員工工號
				}
				if(!"".equals(DEPT_ID)){
					sql+= " AND EHF020403T0_02 = '" + DEPT_ID + "' ";//部門代號
				}

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,	ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				if("0".equals(rs.getString("EHF020403T0_05"))){
					//要計算考勤
					EHF030200T0_17.put(rs.getString("EHF020403T0_01"), rs.getString("EHF020403T0_01"));
				}
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 找是否有設定行事曆
	 * @param conn
	 * @param comp_id
	 * @param lastMonthDay 
	 * @param notWorkDay 
	 * @param rs 
	 * @param stmt2 
	 * @param EHF030200T0_01
	 * @return
	 */
	public boolean getNotWorkDay(Connection conn,String comp_id ,String FirstMonthDay, String LastMonthDay, Map NotWorkDay) {
		boolean flag=false;		
		try {
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,	ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = " select DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS EHF000500T0_05" +
					 " From EHF000500T0 where 1=1"+
					 " AND EHF000500T0_11 = '" + comp_id + "' "+
					 " AND EHF000500T0_05 >= '" + FirstMonthDay + "' "+
					 " AND EHF000500T0_05 <= '" + LastMonthDay + "' ";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				NotWorkDay.put(rs.getString("EHF000500T0_05"), rs.getString("EHF000500T0_05"));
			}
			rs.close();
			stmt.close();
			
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	  * 取得星期顯示名稱 by Calendar
	  * @param cal
	  * @return
	  */
	 public String getWeekDisplayName(Calendar cal){
		 
		 String day_of_week_disply_name = "";
		 
		 try{
			 if(cal != null){
				 day_of_week_disply_name = this.getWeekDisplayName(cal.get(Calendar.DAY_OF_WEEK));
			 }
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return day_of_week_disply_name;
	 }
	 /**
	  * 取得星期顯示名稱 by Day of Week
	  * @param day_of_week
	  * @return
	  */
	 public String getWeekDisplayName(int day_of_week){
		 
		 String day_of_week_disply_name = "";
		 
		 try{
			 //星期顯示名稱定義
			 String[] dayOfWeek = {"", "日", "一", "二","三", "四", "五", "六"};
			 
			 day_of_week_disply_name = dayOfWeek[day_of_week];
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return day_of_week_disply_name;
	 }
	 
	 
		private void getEMPLOYEE_IDMap(Connection conn, String compId, String DEPT_ID, String EMPLOYEE_ID,String Month, Map EHF030200T0_17) {
			// TODO Auto-generated method stub
			String sql=" select  " +
			"   a.ehf020401T0_02_EMP, a.EHF020401T0_02, f.EHF010100T6_02,a.EHF020401T0_05 ," +
			"	(b.EHF020401T0_06) AS work01, b.EHF020401T0_09 AS work01ERR," +
			"	(c.EHF020401T0_06) AS work02, c.EHF020401T0_09 AS work02ERR," +
			"	(d.EHF020401T0_06) AS work05, d.EHF020401T0_09 AS work05ERR," +
			"	(e.EHF020401T0_06) AS work06, e.EHF020401T0_09 AS work06ERR" +
			"	    from" +
			"	        EHF020401T0 a" +
			
			"		LEFT JOIN EHF010100T6 f"+
	        "  		ON f.EHF010100T6_01 = a.EHF020401T0_02_EMP AND f.HR_CompanySysNo = a.EHF020401T0_11"+
			
			"	    LEFT JOIN" +
			"	        (select EHF020401T0_02_EMP,EHF020401T0_05,EHF020401T0_06,EHF020401T0_09 from  EHF020401T0 where 1 = 1 and EHF020401T0_08 = 1 and EHF020401T0_05 like '"+Month+"/%' ) b " +
			"       ON a.ehf020401T0_02_EMP = b.ehf020401T0_02_EMP and a.EHF020401T0_05 = b.EHF020401T0_05"+
			
			"	    LEFT JOIN" +
			"	        (select EHF020401T0_02_EMP,EHF020401T0_05,EHF020401T0_06,EHF020401T0_09 from  EHF020401T0 where 1 = 1 and EHF020401T0_08 = 2 and EHF020401T0_05 like '"+Month+"/%' ) c " +
			"       ON a.ehf020401T0_02_EMP = c.ehf020401T0_02_EMP and a.EHF020401T0_05 = c.EHF020401T0_05"+
			
			" 		LEFT JOIN " +
			"           (select EHF020401T0_02_EMP,EHF020401T0_05,EHF020401T0_06,EHF020401T0_09 from  EHF020401T0 where 1 = 1 and EHF020401T0_08 = 5 and EHF020401T0_05 like '"+Month+"/%' ) d " +
			"       ON a.ehf020401T0_02_EMP = d.ehf020401T0_02_EMP and a.EHF020401T0_05 = d.EHF020401T0_05"+
			
			" 		LEFT JOIN  " +
			"           (select EHF020401T0_02_EMP,EHF020401T0_05,EHF020401T0_06,EHF020401T0_09 from  EHF020401T0 where 1 = 1 and EHF020401T0_08 = 6 and EHF020401T0_05 like '"+Month+"/%' ) e " +
			"       ON a.ehf020401T0_02_EMP = e.ehf020401T0_02_EMP and a.EHF020401T0_05 = e.EHF020401T0_05"+
			
			
			"  where 1 = 1 " +
			"	and a.EHF020401T0_05 like '"+Month+"/%'" ;
			if(!"".equals(EMPLOYEE_ID)){
				sql+="	AND a.ehf020401T0_02_EMP = '"+EMPLOYEE_ID+"'" ;
			}
			if(!"".equals(EMPLOYEE_ID)){
				sql+="	AND f.EHF010100T6_02 = '"+DEPT_ID+"'" ;
			}
			
			sql+="	group by a.ehf020401T0_02_EMP,a.EHF020401T0_05" +
				 "  order by a.ehf020401T0_02_EMP,a.EHF020401T0_05";
			
			try {
				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				String EMPLOYEE="";
				EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
				
				while(rs.next()){
					EMPLOYEE="";
					Form = new EMS_VIEWDATAF();
					//紀錄員工的資料(部門+員工ID+考勤時間)
					EMPLOYEE=rs.getString("EHF010100T6_02")+rs.getString("ehf020401T0_02_EMP")+rs.getString("EHF020401T0_05");
					Form.setDATA00(rs.getString("ehf020401T0_02_EMP"));	//員工工號
					Form.setDATA01(rs.getString("EHF020401T0_02"));	//感應卡號
					Form.setDATA02(rs.getString("EHF010100T6_02"));	//部門系統代碼
					Form.setDATA03(rs.getString("EHF020401T0_05"));	//考勤日期
					Form.setDATA04(rs.getString("work01"));		//上班打卡時間
					Form.setDATA05(rs.getString("work01ERR"));	//上班是否異常
					Form.setDATA06(rs.getString("work02"));		//下班打卡時間
					Form.setDATA07(rs.getString("work02ERR"));	//下班是否異常
					Form.setDATA08(rs.getString("work05"));		//加班上班打卡時間
					Form.setDATA09(rs.getString("work05ERR"));	//加班上班是否異常
					Form.setDATA10(rs.getString("work06"));		//加班下班打卡時間
					Form.setDATA11(rs.getString("work06ERR"));	//加班下班是否異常
					
					EHF030200T0_17.put(EMPLOYEE, Form);
					
				}
				
				rs.close();
				stmt.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		
		/**
		 * 直接取得請假資訊
		 * @param conn
		 * @param compId
		 * @param DEPT_ID
		 * @param EMPLOYEE_ID
		 * @param Month
		 * @param EHF030200T0_17
		 */
		private void get_Ask_for_leave(Connection conn, String compId, String DEPT_ID, String EMPLOYEE_ID,String Month, Map EHF030200T0_17) {
			
			try{
				float work_hours = Float.parseFloat(tools.getSysParam(conn, compId, "WORK_HOURS"));
				
				float ALL_day=0;
				float ALL_hours=0;
				
				String sql = " SELECT EHF020200T0_04,  " +
						  	 " EHF020200T0_03, DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS EHF020200T0_09," +
						  	 " DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') AS EHF020200T0_10," +
						  	 " EHF020100T0_04, EHF020200T0_13," +
						  	 " '', ''," +
						  	 " EHF020200T0_01 " +
						  	 " FROM EHF020200T0" +
						  	 " LEFT JOIN EHF020100T0 ON EHF020200T0_14 = EHF020100T0_03 " +
//						  	 " LEFT JOIN EHF020410T0 ON EHF020200T0_01 = ehf020410t0_FID " +
						  	 " WHERE 1=1 " +
//						  	 " WHERE ehf020410t0_FID IS NULL" +
						  	 " AND (   DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') LIKE '"+Month+"/%' OR DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') LIKE '"+Month+"/%') ";
		
				if(!"".equals(EMPLOYEE_ID)){
					sql+="	AND EHF020200T0_03 = '"+EMPLOYEE_ID+"'" ;
				}
				if(!"".equals(EMPLOYEE_ID)){
					sql+="	AND EHF020200T0_04 = '"+DEPT_ID+"'" ;
				}
				sql+=	" AND EHF020200T0_16 = '0006' " ;
//				sql+=" order by EHF020200T0_03, EHF020200T0_09, EHF020410T0_04";
				sql+=" order by EHF020200T0_03, EHF020200T0_09";
				
				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				String EMPLOYEE="";
				String Remark="";
				String form_no="";
				EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
				while(rs.next()){	
					
					form_no = rs.getString("EHF020200T0_01");
					
					if(!"".equals(EMPLOYEE)&&  !EMPLOYEE.equals(rs.getString("EHF020200T0_04")+rs.getString("EHF020200T0_03")+rs.getString("EHF020200T0_09"))){
						Form.setDATA05(Remark);
						
						Form.setDATA06(String.valueOf(ALL_day));
						Form.setDATA07(String.valueOf(ALL_hours));
						
						Form.setDATA01(form_no);
						
						EHF030200T0_17.put(EMPLOYEE, Form);
						
						Remark="";		
						ALL_day=0;
						ALL_hours=0;
						Form = new EMS_VIEWDATAF();
					}
									
					String time[]=rs.getString("EHF020200T0_13").split("/");
					
					String day=time[0];
					String hour=time[1];
					
					if(rs.getString("EHF020200T0_09").equals(rs.getString("EHF020200T0_10"))){//請假單是同一天
						//紀錄員工的資料(部門+員工ID+考勤時間)
						EMPLOYEE=rs.getString("EHF020200T0_04")+rs.getString("EHF020200T0_03")+rs.getString("EHF020200T0_09");
						
						ALL_day+=day.equals("0")?0:(Float.valueOf(day)*work_hours);
						ALL_hours+=hour.equals("0.0")?0:Float.valueOf(hour);
						Remark+=rs.getString("EHF020100T0_04")+":"+((Float.valueOf(day)*work_hours)+Float.valueOf(hour))+"小時";
					}else{
						
						Calendar cal_start = tools.covertStringToCalendar(rs.getString("EHF020200T0_09"));
						Calendar cal_end = tools.covertStringToCalendar(rs.getString("EHF020200T0_10"));
						
						boolean run_flag = true;
						
						while(run_flag){
							
							//紀錄員工的資料(部門+員工ID+考勤時間)
							EMPLOYEE=rs.getString("EHF020200T0_04")+rs.getString("EHF020200T0_03")+this.CalendarToString(cal_start);
							
							ALL_day+=day.equals("0")?0:(Float.valueOf(day)*work_hours);
							ALL_hours+=hour.equals("0.0")?0:Float.valueOf(hour);
							Remark+=rs.getString("EHF020100T0_04")+":"+((Float.valueOf(day)*work_hours)+Float.valueOf(hour))+"小時";
							
							if(!"".equals(EMPLOYEE) && !EMPLOYEE.equals(rs.getString("EHF020200T0_04")+rs.getString("EHF020200T0_03")+this.CalendarToString(cal_end))){
								Form.setDATA05(Remark);
								
								Form.setDATA06(String.valueOf(ALL_day));
								Form.setDATA07(String.valueOf(ALL_hours));
								
								Form.setDATA01(form_no);
								
								EHF030200T0_17.put(EMPLOYEE, Form);
								
								Remark="";		
								ALL_day=0;
								ALL_hours=0;
								Form = new EMS_VIEWDATAF();
							}
							
							if(cal_start.equals(cal_end)){
								run_flag = false;
							}
							
							cal_start.add(Calendar.DAY_OF_MONTH, 1);
						}
						
					}									
					
				}
				Form.setDATA05(Remark);	//備註訊息
				Form.setDATA06(String.valueOf(ALL_day));	//請假天數
				Form.setDATA07(String.valueOf(ALL_hours));	//請假天數
				Form.setDATA01(form_no);
				EHF030200T0_17.put(EMPLOYEE, Form);
					

				rs.close();
				stmt.close();
				
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 依據考勤異常來取得請假資訊
		 * @param conn
		 * @param compId
		 * @param DEPT_ID
		 * @param EMPLOYEE_ID
		 * @param Month
		 * @param EHF030200T0_17
		 */
		/*private void get_Ask_for_leave(Connection conn, String compId, String DEPT_ID, String EMPLOYEE_ID,String Month, Map EHF030200T0_17) {
			// TODO Auto-generated method stub
			
			float work_hours = Float.parseFloat(tools.getSysParam(conn, compId, "WORK_HOURS"));
			
			float ALL_day=0;
			float ALL_hours=0;
			
			
			String sql="select EHF020200T0_04," +
					" EHF020200T0_03,ehf020410t0_03," +
					" EHF020100T0_04,EHF020200T0_13," +
					" EHF020410T0_04,EHF020410T0_FIX," +
					" EHF020200T0_01 " +
					"			FROM ehf020410t0" +
					"			LEFT JOIN EHF020200T0 ON EHF020200T0_01=ehf020410t0_FID " +
					"			LEFT JOIN EHF020100T0 ON EHF020200T0_14=EHF020100T0_03" +
					"			where EHF020200T0_01 is not null" +
					"			AND ehf020410t0_03 like '"+Month+"/%'" ;
					
			
			if(!"".equals(EMPLOYEE_ID)){
				sql+="	AND EHF020200T0_03 = '"+EMPLOYEE_ID+"'" ;
			}
			if(!"".equals(EMPLOYEE_ID)){
				sql+="	AND EHF020200T0_04 = '"+DEPT_ID+"'" ;
			}
			//因為請上班時間中間的假時，系統不會認為是異常，所以上面的SQL不會找到假單，EX：請上午10:00~12:00
			sql+=	" UNION " +
					" SELECT EHF020200T0_04,  " +
					" EHF020200T0_03, DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS EHF020200T0_09," +
					" EHF020100T0_04, EHF020200T0_13," +
					" '', ''," +
					" EHF020200T0_01 " +
					" FROM EHF020200T0" +
					" LEFT JOIN EHF020100T0 ON EHF020200T0_14 = EHF020100T0_03 " +
					" LEFT JOIN EHF020410T0 ON EHF020200T0_01 = ehf020410t0_FID " +
					" WHERE ehf020410t0_FID IS NULL" +
					" AND (   DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') LIKE '"+Month+"/%' OR DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') LIKE '"+Month+"/%') ";
			
			if(!"".equals(EMPLOYEE_ID)){
				sql+="	AND EHF020200T0_03 = '"+EMPLOYEE_ID+"'" ;
			}
			if(!"".equals(EMPLOYEE_ID)){
				sql+="	AND EHF020200T0_04 = '"+DEPT_ID+"'" ;
			}
			sql+=	" AND EHF020200T0_16 = '0006' " ;
			sql+="			order by EHF020200T0_03 , ehf020410t0_03,EHF020410T0_04";
			
			try {
				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				String EMPLOYEE="";
				String Remark="";
				EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
				while(rs.next()){					
					if(!"".equals(EMPLOYEE)&&  !EMPLOYEE.equals(rs.getString("EHF020200T0_04")+rs.getString("EHF020200T0_03")+rs.getString("ehf020410t0_03"))){
						Form.setDATA05(Remark);
						
						Form.setDATA06(String.valueOf(ALL_day));
						Form.setDATA07(String.valueOf(ALL_hours));
						
						EHF030200T0_17.put(EMPLOYEE, Form);
						
						Remark="";		
						ALL_day=0;
						ALL_hours=0;
						Form = new EMS_VIEWDATAF();
					}
					//紀錄員工的資料(部門+員工ID+考勤時間)
					EMPLOYEE=rs.getString("EHF020200T0_04")+rs.getString("EHF020200T0_03")+rs.getString("ehf020410t0_03");
					
					
					String time[]=rs.getString("EHF020200T0_13").split("/");
					
					String day=time[0];
					String hour=time[1];
					
					if("1".equals(rs.getString("EHF020410T0_04"))){
						Form.setDATA01("1");	//刷卡類型上班
						Form.setDATA02(rs.getString("EHF020200T0_01"));	//表單編號
					}else if("2".equals(rs.getString("EHF020410T0_04"))){
						Form.setDATA03("2");	//刷卡類型下班
						Form.setDATA04(rs.getString("EHF020200T0_01"));	//表單編號
					}else{
						//請上班時間中間的假單
						Form.setDATA01("3");
						Form.setDATA03("3");
						Form.setDATA02(rs.getString("EHF020200T0_01"));	//表單編號
						Form.setDATA04(rs.getString("EHF020200T0_01"));	//表單編號
					}
					
					if(Form.getDATA01()!=null && Form.getDATA03()!=null){
						
						if(!Form.getDATA02().equals(Form.getDATA04())){
							//Remark+=rs.getString("EHF020100T0_04")+":"+(day.equals("0")?"":day+"天")+(hour.equals("0.0")?"":hour+"小時");
							ALL_day+=day.equals("0")?0:(Float.valueOf(day)*work_hours);
							ALL_hours+=hour.equals("0.0")?0:Float.valueOf(hour);
							Remark+=rs.getString("EHF020100T0_04")+":"+((Float.valueOf(day)*work_hours)+Float.valueOf(hour))+"小時";
						}
						
						if("3".equals(Form.getDATA01()) && "3".equals(Form.getDATA03())){
							//請上班時間中間的假單
							ALL_day+=day.equals("0")?0:(Float.valueOf(day)*work_hours);
							ALL_hours+=hour.equals("0.0")?0:Float.valueOf(hour);
							Remark+=rs.getString("EHF020100T0_04")+":"+((Float.valueOf(day)*work_hours)+Float.valueOf(hour))+"小時";
						}
						
					}else{
 						//Remark+=rs.getString("EHF020100T0_04")+":"+(day.equals("0")?"":day+"天")+(hour.equals("0.0")?"":hour+"小時");
						ALL_day+=day.equals("0")?0:(Float.valueOf(day)*work_hours);
						ALL_hours+=hour.equals("0.0")?0:Float.valueOf(hour);
						Remark+=rs.getString("EHF020100T0_04")+":"+((Float.valueOf(day)*work_hours)+Float.valueOf(hour))+"小時";
					}
					
					
					
					
				}
				Form.setDATA05(Remark);	//備註訊息
				Form.setDATA06(String.valueOf(ALL_day));	//請假天數
				Form.setDATA07(String.valueOf(ALL_hours));	//請假天數
				EHF030200T0_17.put(EMPLOYEE, Form);
					

				rs.close();
				stmt.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		*/
		
		private void get_OVER_TIME(Connection conn, String compId, String DEPT_ID, String EMPLOYEE_ID,String Month, Map returnMAP) {
			// TODO Auto-generated method stub
			
			float work_hours = Float.parseFloat(tools.getSysParam(conn, compId, "WORK_HOURS"));
			
			float ALL_day=0;
			float ALL_hours=0;
			
			
			String sql="SELECT " +
					"   DATE_FORMAT(EHF020800T0_06, '%Y/%m/%d') AS EHF020800T0_06," +
					"   EHF020800T0_11," +
					"   EHF020800T1_04, " +
					"   NOON_LIMITED ," +
					"   EHF020800T1_12," +
					"   EHF020801T0_10," +
					"   (EHF020802T0_07 - EHF020802T0_07_DE) AS EHF020802T0_07_REAL," +
					"   EHF020802T0_07" +
					" FROM EHF020800T1" +
					"       LEFT JOIN EHF020800T0  ON     EHF020800T0_01 = EHF020800T1_01  AND EHF020800T0_10 = EHF020800T1_10 " +
					"       LEFT JOIN EHF020801T0  ON     EHF020801T0_03 = EHF020800T1_02" +
					"       LEFT JOIN EHF020802T0  ON     EHF020801T0_01 = EHF020802T0_03" +
					" WHERE     1 = 1   AND EHF020800T1_10 = '"+compId+"'" +
					" AND DATE_FORMAT(EHF020800T0_06, '%Y/%m/%d') like '"+Month+"/%'" +
					"			" ;
					
			
			if(!"".equals(EMPLOYEE_ID)){
				sql+="	AND EHF020800T1_04 = '"+EMPLOYEE_ID+"'" ;
			}
			if(!"".equals(EMPLOYEE_ID)){
				sql+="	AND EHF020800T0_11 = '"+DEPT_ID+"'" ;
			}
			sql+="			order by EHF020800T0_11,EHF020800T1_04,EHF020800T0_06";
			
			try {
				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				String EMPLOYEE="";
				String Remark="";
				EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
				float hour=0;
				while(rs.next()){					
					

					if(!"".equals(EMPLOYEE)&&  !EMPLOYEE.equals(rs.getString("EHF020800T0_11")+rs.getString("EHF020800T1_04")+rs.getString("EHF020800T0_06"))){
						Form.setDATA01(String.valueOf(hour));
						
						returnMAP.put(EMPLOYEE, Form);
						
						hour=0;
						Form = new EMS_VIEWDATAF();
					}
					
					//紀錄員工的資料(部門+員工ID+考勤時間)
					EMPLOYEE=rs.getString("EHF020800T0_11")+rs.getString("EHF020800T1_04")+rs.getString("EHF020800T0_06");
					
					
					if("on".equals(rs.getString("NOON_LIMITED"))){
						hour+=rs.getFloat("EHF020800T1_12");
						
					}else{
						hour+=rs.getFloat("EHF020801T0_10");
						//hour+=rs.getFloat("EHF020802T0_07");//未包含實際加班內扣時數
						hour+=rs.getFloat("EHF020802T0_07_REAL");//包含實際加班內扣時數
					}

				}
				
				Form.setDATA01(String.valueOf(hour));
				returnMAP.put(EMPLOYEE, Form);
					

				rs.close();
				stmt.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/**
		 * 取得所有備註欄訊息
		 * @param conn
		 * @param employee_id
		 * @param start
		 * @param end
		 * @param work_hours
		 * @param comp_id
		 * @return
		 */
		private String getTotle(Connection conn, String employee_id, String start, String end, float work_hours,String comp_id) {
			// TODO Auto-generated method stub
			
			String False = "";
			
			try{
				String sql_01 = this.getPrintSQL(employee_id, start, end, work_hours, comp_id);

				Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);

				ResultSet rs_01 = stmt_01.executeQuery(sql_01);
				
				False += "   請假時數： ";
				
				while (rs_01.next()) {
					if (!rs_01.getString("單據別").equals("")) {
						
						if (rs_01.getString("單據別").equals("請假單")) {
							False += "	" + rs_01.getString("類別");
							String split[]= rs_01.getString("總計").split(":");
							
							//day+=Integer.valueOf(split[0]);
							False+=split[0]+split[1]+split[2]+split[3];
							
							False += "，";
						}
					}
				}
				
				rs_01.close();
				stmt_01.close();
				
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			return False;
		}
		
		protected String getPrintSQL( String employee_id, String start_date, String end_date, float dayhours, String comp_id  ){
			
			String sql = "";
			
			try{
				sql = ""
					+ " SELECT "
					+ " *, "
					+ " CASE Att.TYPE "
					//+ " WHEN 0 THEN "
					//+ " CASE Att.類別代碼 "
					//+ " WHEN '01' THEN (CONCAT(CAST(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/60)) AS CHAR(4)), '小時', CAST(MOD(SUM(Att.HOURS), 60) AS CHAR(6)), '分'))"
					//+ " WHEN '02' THEN (CONCAT(CAST(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/60)) AS CHAR(4)), '小時', CAST(MOD(SUM(Att.HOURS), 60) AS CHAR(6)), '分'))  "
					//+ " WHEN '03' THEN (CONCAT(CAST(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/"+ dayhours + ")) AS CHAR(4)), '天', CAST(MOD(SUM(Att.HOURS), "+ dayhours+ ") AS CHAR(6)), '小時')) "
					//+ " END "
					+ " WHEN 1 THEN (CONCAT(CAST(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/"+ dayhours+ ")) AS CHAR(4)), ':天:', "
					+ " CAST(MOD(SUM(Att.HOURS), "+ dayhours+ ") AS CHAR(6)), ':小時' )) "
					//+ " WHEN 2 THEN (CONCAT(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/"+ dayhours+ ")), ':天:', MOD(SUM(Att.HOURS), "+ dayhours+ "), ':小時' )) "
					//+ " WHEN 3 THEN (CONCAT(FLOOR(SUM(Att.DAYS)), '次')) "
					//+ " WHEN 4 THEN (CONCAT(FLOOR(SUM(Att.DAYS)), '次')) 
					+ " END AS 總計 ,  "
					+ " CASE Att.TYPE "
					//+ " WHEN 0 THEN " 
					//+ " CASE Att.類別代碼 "
					//+ " WHEN  '01' THEN COUNT(Att.類別代碼)"
					//+ " WHEN  '02' THEN COUNT(Att.類別代碼 )"
					//+ " WHEN  '03' THEN COUNT(Att.類別代碼)"
					//+ " END " 
					+ " WHEN 1" 
					+ " THEN COUNT(Att.類別代碼)" 
					//+ "	WHEN 4" 
					//+ " THEN COUNT(Att.類別代碼)" +
					+ " END AS 總次"+
				" FROM ( ";
				// 請假單
				sql += 	  "   SELECT "
						+ "   EHF020200T0_01 AS 單號, EHF020200T0_04 AS 部門代號, EHF020200T0_03 AS 員工工號, '請假單' AS 單據別, "
						+ "   EHF020200T0_08 AS ATT_DATE, "
						+ "   1 AS TYPE, "
						+ "   EHF020200T0_14 AS 類別代碼, EHF020100T0_04 AS 類別, "
						+ "   FLOOR(((SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) * "
						+ dayhours
						+ ") + SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5))/"
						+ dayhours
						+ ") AS DAYS, "
						+ "   MOD(((SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) * "
						+ dayhours
						+ ") + SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5)), "
						+ dayhours
						+ ") AS HOURS "
						+ "   FROM EHF020200T0 "
						+ "   LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 "
						+ "   WHERE 1=1 " + "   "
						+ " AND EHF020200T0_16 = '0006' " + // 處理狀態
						"   AND EHF020200T0_18 = '" + comp_id + "' "; // 公司代碼

				if (!"".equals(employee_id)) { // 員工
					sql += " AND EHF020200T0_03 = '" + employee_id + "' "; // 員工工號
				}

				// 日期區間 -- 使用實際請假日期做判斷
				if (!"".equals(start_date) && start_date != null
						&& !"".equals(end_date) && end_date != null) {
					sql += " AND ( "
							+ "  DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') BETWEEN '"
							+ start_date + "' AND '" + end_date
							+ "' "
							+ // 請假日期(起)
							"  OR "
							+ "  DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') BETWEEN '"
							+ start_date + "' AND '" + end_date + "' " + // 請假日期(迄)
							" ) ";
				} else if (!"".equals(start_date) && start_date != null) {
					sql += " AND ( "
							+ " DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') >= '"
							+ start_date + "' " + " OR "
							+ " DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') >= '"
							+ start_date + "' " + " ) ";
				} else if (!"".equals(end_date) && end_date != null) {
					sql += " AND ( "
							+ " DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') <= '"
							+ end_date + "' " + " OR "
							+ " DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') <= '"
							+ end_date + "' " + " ) ";
				}
				
				sql += "" + "  ) Att " + " WHERE 1=1 ";

				sql += "" + " GROUP BY Att.員工工號, Att.單據別, Att.類別 "
						+ " ORDER BY Att.部門代號, Att.員工工號, Att.單據別, Att.類別 ";
				
			}catch (Exception e) {
				e.printStackTrace();
			}

			return sql;			
		}
		
		
		
		/**
		 * 轉換 Calendar to String
		 * @param cal
		 * @param time
		 * @return
		 */
		public String CalendarToString( Calendar cal ){
			
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
