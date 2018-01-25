package com.spon.ems.vacation.actions;

import java.sql.Connection;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * (Action)門禁記錄查詢
 * @author maybe
 * @version 1.0
 * @created 2015/8/18 上午9:21:45
 */
public class EHF020404M1A extends NewDispatchAction {
	
	private EMS_ACCESS ems_access;

	public EHF020404M1A() {
		ems_access = EMS_ACCESS.getInstance();
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
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;

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


				
				request.setAttribute("MSG", "新增成功");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				// 清掉畫面上的新增
			
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				generateSelectBox(conn, Form, request);
				request.setAttribute("Form1Datas" , Form);
				if (request.getAttribute("Form1Datas") != null) {
					form = (EMS_VIEWDATAF) request.getAttribute("Form1Datas");
				}
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				//return init(mapping, form, request, response);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF020404M1A.addData() " + e);
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

		return init(mapping, form, request, response);
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
		
		//產生--日期類別-- 選單
		try{
			List listDATA21 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("月份");
			listDATA21.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("指定日期");
			listDATA21.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("指定日期區間");
			listDATA21.add(bform);*/
			request.setAttribute("listDATA21", listDATA21);
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
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		String at_type = "";
		
		try{
			ArrayList list = new ArrayList();
			
			//判斷身分別
			//ems_tools.checkIdentity(getEmscontext(), request, getLoginUser(request), conn, "Vacation_FLOW_NO", "", "");
			
//			at_type = ems_tools.getSysParam(conn, getLoginUser(request).getCompId(), "DOOR_ACCESS_TYPE");
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//當前登入者權限
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
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
				Form.setDATA07( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("USER_CODE") );  //申請人員工工號
				Form.setDATA03( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_ID") );
				Form.setDATA04( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_NAME") );  //申請人姓名
				Form.setDATA08( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID") );  //申請人部們組織代號
				Form.setDATA01( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DEPT_ID") );
				Form.setDATA02( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DESC") );  //申請人部們名稱
			}
			
			hr_tools.close();
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
			//產生下拉選單
			this.generateSelectBox(conn, Form, request);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("collection", "show");
			
			request.setAttribute("Form2Datas",list);
			request.setAttribute("Form1Datas", Form);
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
	    		if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			}catch(Exception e){
				
			}
	    }
		
		return mapping.findForward("success");
	}
	
	
	/**
	 * 與魏先生的門禁機器(EK-3000R)整合的門禁查詢作業
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		ArrayList list = new ArrayList();
		ArrayList DepartureWorkEmplist = new ArrayList();//離職員工清單
		ArrayList SuspensionWithoutPayEmplist = new ArrayList();//留職停薪員工清單
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			
			if(!Form.getDATA05().equals("") && !Form.getDATA06().equals("")){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
				//進行轉換 
				try {
					//檢核日期區間是否正確
			
					Date date01 = sdf.parse(Form.getDATA05());
					//System.out.println(date01);
					Date date02 = sdf.parse(Form.getDATA06()); 
					//System.out.println(date02);
					
					if(date01.compareTo(date02) > 0){
						lc_errors.add("DATA05",new ActionMessage(""));
						request.setAttribute("ErrMSG",
								(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
								"起始日期不可小於結束日期!! ");
					}
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
			

			if (lc_errors.isEmpty()) {
				
				AuthorizedBean authbean = getLoginUser(request);
				
				//使用系統參數將兩套運作規則切開 edit by joe 2013/05/09
				//String ems_version = tools.getSysParam(conn, authbean.getCompId(), "EMS_VERSION");
				
				//取得FLOW_NO,使用請假單資料
				//String flow_no = tools.getSysParam(conn, authbean.getCompId(), "Vacation_FLOW_NO");
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				//取得員工Map
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				
				hr_tools.close();
				
				//製作假門禁用的
//				int a=(int)(Math.random()*60+1);
//
//				Map emp_ID=new HashMap();
//				Set set = empMap.keySet();
//				Iterator it = set.iterator();
//				boolean ceacka = false;
//				while (it.hasNext()) {
//					String key = (String) it.next();
//					emp_ID =(Map)empMap.get(key);
//					String CARD= (String)emp_ID.get("CARD");
//					
//					int CLASS= (Integer)emp_ID.get("CLASS");
//					
//					if(CLASS==27){
//						for(int i=1;i<32;i++){
//							System.out.println(CARD+"	"+"2016	01	"+i+"	"+"2016	01	"+i+"	07	"+(int)(Math.random()*59)+"	0");
//						}
//						
//						for(int i=1;i<32;i++){
//							System.out.println(CARD+"	"+"2016	01	"+i+"	"+"2016	01	"+i+"	17	"+(int)(Math.random()*29+30)+"	0");
//						}
//					}else{
//						for(int i=1;i<32;i++){
//							System.out.println(CARD+"	"+"2016	01	"+i+"	"+"2016	01	"+i+"	19	"+(int)(Math.random()*59+1)+"	0");
//							
//						}
//						for(int i=1;i<32;i++){
//							
//							System.out.println(CARD+"	"+"2016	01	"+i+"	"+"2016	01	"+(i+1)+"	05	"+(int)(Math.random()*59+1)+"	0");
//						}
//					}
//					
//					
//				}
				
				
				
				
				
				
				
				
				
				
				
				
				
				//在職員工清單
				/*Map WorkEmp = null;

				//離職員工清單
				Map DepartureWorkEmp = null;
			
				//留職停薪員工清單
				Map SuspensionWithoutPayEmp= null;
				
				//全部都不選，則表示查詢全部
				if(Form.getDATA16()==null&&Form.getDATA17()==null&&Form.getDATA18()==null){
					Form.setDATA16("0");
					Form.setDATA17("0");
					Form.setDATA18("0");
				}

				if("0".equals(Form.getDATA16())||"1".equals(Form.getDATA16())){
					//在職員工清單
					WorkEmp=ems_tools.getWorkEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
				}
					
				if("0".equals(Form.getDATA17())||"2".equals(Form.getDATA17())){
					//離職員工清單
					DepartureWorkEmp=ems_tools.getDepartureWorkEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
				}
				if("0".equals(Form.getDATA18())||"3".equals(Form.getDATA18())){
					//留職停薪員工清單
					SuspensionWithoutPayEmp=ems_tools.getSuspensionWithoutPayEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());	
				}*/

				String sql = "";
				
				sql = "" +
				" SELECT EHF020404T0.*, B.EHF020403T1_01 AS EHF020403T1_01, " +
				" IFNULL(EHF020404T0_07, '') AS MACHINE_NO, " +
				" IFNULL(EHF020404T0_08, '') AS CARD_STATUS, " +
				" DATE_FORMAT(EHF020404T0_06, '%Y年%m月%d日') AS CARD_DATE, DATE_FORMAT(EHF020404T0_06, '%k點%i分%s秒') AS CARD_TIME " +
				" FROM EHF020404T0 " +
				" LEFT JOIN EHF020403T1 B ON EHF020403T1_02 = EHF020404T0_03 AND EHF020403T1_06 = EHF020404T0_09 " +
				" LEFT JOIN EHF020403T0 A ON EHF020403T0_01 = EHF020403T1_01 AND EHF020403T0_04 = EHF020404T0_09 " +
				" WHERE 1=1 " +
				" AND EHF020404T0_09 = '"+authbean.getCompId()+"' " ;  //公司代碼

				
				//判斷當前登入者的身份，主管或是人事經辦
				/*if(ems_tools.checkEmsRoleEmp(getEmscontext(), authbean, "", "2", "0001", flow_no, "") || "1.6".equals(ems_version) ){*/
					//人事經辦
					
					sql += "" +
					" AND EHF020403T1_01 IS NOT NULL " ;
					
					
					if ( !Form.getDATA03().equals("")){  //員工
						sql += " AND EHF020403T1_01 = '"+Form.getDATA07()+"' ";  //員工工號
					}
					
					if(!Form.getDATA01().equals("")){  //部門組織
						sql += " AND EHF020403T0_02 = '"+Form.getDATA08()+"' ";  //部門代號
					}	
					
					//request.setAttribute("person_manager", "yes");
					//request.setAttribute("boss", "yes");
					
				/*}else if("1".equals(authbean.getManagerFlag())){
					//主管
					
					sql += "" +
					" AND EHF020403T1_01 IS NOT NULL " ;
//					" AND EHF020403T1_01 = '"+getLoginUser(request).getEmployeeID()+"' " +  //員工工號
					
					if (!Form.getDATA01().equals("") && !Form.getDATA03().equals("")){  //員工
						sql += " AND EHF020403T0_02 = '"+Form.getDATA01()+"' " +  //部門代號
							   " AND EHF020403T1_01 = '"+Form.getDATA03()+"' ";  //員工工號
					}else if(!Form.getDATA01().equals("")){  //部門組織
						sql += " AND EHF020403T0_02 = '"+Form.getDATA01()+"' ";  //部門代號
					}else if(Form.getDATA01().equals("") && Form.getDATA03().equals("")){
						//當部門與員工條件為空白時，取出主管轄下人員的資料
						sql += " AND ( 1=0 " ;
//						List bossdeplist = ems_tools.getEmsBossUnderDep(getEmscontext(), getLoginUser(request).getUserId(), "",
//										   getLoginUser(request).getUserCode(),getLoginUser(request).getCompId(), "");
						List bossdeplist = ems_tools.getEmsBossAllUnderDep(getEmscontext(), authbean.getUserId(), authbean.getEmployeeID(),
										   "", authbean.getUserCode(), authbean.getCompId());
						Iterator it = bossdeplist.iterator();
						while(it.hasNext()){
							Map tempMap = (Map) it.next();
							sql += " OR EHF020403T0_02 = '"+tempMap.get("DEPT_ID")+"' ";
						}
						sql += " ) ";
					}			
					
					request.setAttribute("person_manager", "no");
					request.setAttribute("boss", "yes");
					
				}else {
					//一般員工
				
					sql += "" +
					" AND EHF020403T1_01 = '"+authbean.getEmployeeID()+"' ";  //員工工號
				
					request.setAttribute("person", "yes");*/
				/*}*/
				
				//日期區間
				if (!Form.getDATA05().equals("") && !Form.getDATA06().equals("")) {  
					sql += " AND EHF020404T0_04 BETWEEN '"+Form.getDATA05()+"' AND  '"+Form.getDATA06()+"'  ";
				}else if (!Form.getDATA05().equals("") ) {
					sql += " AND EHF020404T0_04 >= '"+Form.getDATA05()+"' ";
				}else if (!Form.getDATA06().equals("")) {
					sql += " AND EHF020404T0_04 <= '"+Form.getDATA06()+"'  ";
				}	
				
				sql += "  ORDER BY EHF020403T0_02, EHF020403T1_01, EHF020404T0_04, EHF020404T0_06 ; ";
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				/*boolean chk_flag = false;//在職判斷
				boolean SuspensionWithoutPay= false;//留職停薪判斷
				boolean DepartureWork= false;//離職停薪判斷
*/				String tmp_emp_id ="";
				
				
				
				while(rs.next()){
					

					 /*chk_flag = false;//在職判斷
					 SuspensionWithoutPay= false;//留職停薪判斷
					 DepartureWork= false;//離職停薪判斷
					
					
					//根據前端勾選的找尋員工
					
					//1.先處理留職停薪人員
					if("0".equals(Form.getDATA18())||"3".equals(Form.getDATA18())){
						Iterator departure_emp_it = SuspensionWithoutPayEmp.keySet().iterator();
						while (departure_emp_it.hasNext()) {
							tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
							if (tmp_emp_id.equals(rs.getString("EHF020403T1_01"))) {
								SuspensionWithoutPay = true;
								break;
							} else {
								SuspensionWithoutPay = false;
							}
						}
					}
					//2.處理離職員工
					if("0".equals(Form.getDATA17())||"2".equals(Form.getDATA17())){
						Iterator departure_emp_it = DepartureWorkEmp.keySet().iterator();
						while (departure_emp_it.hasNext()) {
							tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
							if (tmp_emp_id.equals(rs.getString("EHF020403T1_01"))) {
								DepartureWork = true;
								break;
							} else {
								DepartureWork = false;
							}
						}
						
					}
					
					//2.處理在職員工
					if("0".equals(Form.getDATA16())||"1".equals(Form.getDATA16())){
						Iterator departure_emp_it = WorkEmp.keySet().iterator();
						while (departure_emp_it.hasNext()) {
							tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
							if (tmp_emp_id.equals(rs.getString("EHF020403T1_01"))) {
								chk_flag = true;
								break;
							} else {
								chk_flag = false;
							}
						}
						
					}*/
					
					/*if(chk_flag){*/


						EMS_VIEWDATAF bean = new EMS_VIEWDATAF();
						bean.setDATA10(rs.getString("CARD_DATE"));  //日期
						bean.setDATA11( (String) ((Map)empMap.get(rs.getString("EHF020403T1_01"))).get("EMPLOYEE_ID") +" / "+ (String) ((Map)empMap.get(rs.getString("EHF020403T1_01"))).get("EMPLOYEE_NAME") );  //員工姓名
						
						if("".equals(rs.getString("CARD_STATUS")) || rs.getString("CARD_STATUS") == null){
							bean.setDATA13( (String) tools.getDoorAccessStatus((int)-1) );
						}else{
							bean.setDATA13((String) tools.getDoorAccessStatus(Integer.parseInt(rs.getString("CARD_STATUS"))));  //門禁刷卡狀態
						}
						
						bean.setDATA14(rs.getString("CARD_TIME"));  //門禁刷卡時間
		
						
					/*}else if(DepartureWork){
						
						EMS_VIEWDATAF bean = new EMS_VIEWDATAF();
						bean.setDATA10(rs.getString("CARD_DATE"));  //日期
						bean.setDATA11( rs.getString("EHF020403T1_01") +" / "+ (String) ((Map)empMap.get(rs.getString("EHF020403T1_01"))).get("EMPLOYEE_NAME")+"(離職)" );  //員工姓名
						
						if("".equals(rs.getString("CARD_STATUS")) || rs.getString("CARD_STATUS") == null){
							bean.setDATA13( (String) ems_tools.getDoorAccessStatus((int)-1) );
						}else{
							bean.setDATA13((String) ems_tools.getDoorAccessStatus(Integer.parseInt(rs.getString("CARD_STATUS"))));  //門禁刷卡狀態
						}
						
						bean.setDATA14(rs.getString("CARD_TIME"));  //門禁刷卡時間
				
						DepartureWorkEmplist.add(bean);
					}
					else if(SuspensionWithoutPay){
						EMS_VIEWDATAF bean = new EMS_VIEWDATAF();
						bean.setDATA10(rs.getString("CARD_DATE"));  //日期
						bean.setDATA11( rs.getString("EHF020403T1_01") +" / "+ (String) ((Map)empMap.get(rs.getString("EHF020403T1_01"))).get("EMPLOYEE_NAME")+"(留職停薪)" );  //員工姓名
						
						if("".equals(rs.getString("CARD_STATUS")) || rs.getString("CARD_STATUS") == null){
							bean.setDATA13( (String) ems_tools.getDoorAccessStatus((int)-1) );
						}else{
							bean.setDATA13((String) ems_tools.getDoorAccessStatus(Integer.parseInt(rs.getString("CARD_STATUS"))));  //門禁刷卡狀態
						}
						
						bean.setDATA14(rs.getString("CARD_TIME"));  //門禁刷卡時間
				
						SuspensionWithoutPayEmplist.add(bean);
					}*/	
						
					int CARD_STATUS= "".equals(rs.getString("CARD_STATUS"))?0:Integer.parseInt(rs.getString("CARD_STATUS"));
						
					bean.setDATA15((String) tools.getDoorAccessStatus(CARD_STATUS));  //門禁刷卡狀態
					
					list.add(bean);
				}
			
				rs.close();
				stmt.close();
				list.addAll(DepartureWorkEmplist);
				list.addAll(SuspensionWithoutPayEmplist);
				request.setAttribute("Form2Datas",list);
				
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				
				Form.setDATA01("");
				Form.setDATA02("");
				Form.setDATA03("");
				Form.setDATA04("");
				Form.setDATA05("");
				Form.setDATA06("");
				Form.setDATA07("");
				Form.setDATA08("");
				Form.setDATA16("");
				Form.setDATA17("");
				Form.setDATA18("");
				request.setAttribute("Form1Datas", Form);
				
				//判斷身分別
				ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
				
			} else {
				
				//判斷身分別
				//ems_tools.checkIdentity(getEmscontext(), request, getLoginUser(request), conn, "Vacation_FLOW_NO", "", "");
				//判斷身分別
				ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form2Datas",list);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				return mapping.findForward("success");
			}
			
		} catch (Exception e) {
			request.setAttribute("Form2Datas",list);
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("MSG", "查詢失敗!");
			System.out.println("EHF020404M1A.addData() " + e);
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
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String dac_type = "";
		
		try{
			
			dac_type = tools.getSysParam(conn, getLoginUser(request).getCompId(), "DOOR_ACCESS_TYPE");
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			String HR 		= tools.getSysParam(conn, authbean.getCompId(),"HR_IDENTITY");		//預設人資		100
			String SYSTEM 	= tools.getSysParam(conn, authbean.getCompId(),"SYSTEM_IDENTITY");	//系統管理者	000
			
			if(ems_access.checkEmsRoleEmp(conn, authbean, HR)){
				//人事經辦
			}else if(ems_access.checkEmsRoleEmp(conn, authbean, SYSTEM)){
				//系統管理者
			}else{
				//一般員工
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				Form.setDATA07( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("USER_CODE") );  //申請人員工工號
				Form.setDATA03( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_ID") );
				Form.setDATA04( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_NAME") );  //申請人姓名
				Form.setDATA08( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID") );  //申請人部們組織代號
				Form.setDATA01( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DEPT_ID") );
				Form.setDATA02( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DESC") );  //申請人部們名稱
			}
			
			hr_tools.close();
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
			
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
		
		 if("EK-3000R".equals(dac_type)){
				//與魏先生的門禁機器(EK-3000R)整合的門禁查詢作業
				return queryDatas(mapping, form, request, response);
				
		}else{
				
			return queryDatas(mapping, form, request, response);
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
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
//   		Form.setEHF010300T0_01(arrid[0]);
		}else{
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
				
				String sql = "" ;
				

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				if(rs.next()){
					
					
					
				}
				
				
				request.setAttribute("Form2Datas",list);
//				Form.setEHF010100C(list);
				
				rs.close();
				stmt.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
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
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			BA_Vaildate ve=BA_Vaildate.getInstance();

			if (lc_errors.isEmpty()) {
				
				String sql = "" ;
				
				
				request.setAttribute("MSG","儲存成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
	
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				return mapping.findForward("success");
			}
		} catch (Exception e) {

			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			
			request.setAttribute("MSG","門禁刷卡資料儲存錯誤!!請重新操作!!");
						
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
//		return queryDatas(mapping, form, request, response);
		return mapping.findForward("success");
	}

	public ActionForward delData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
 //   		Form.setEHF010300T0_01(arrid[0]);
		}else{
			request.setAttribute("MSG", "請先選擇一筆要刪除的資料!!");
			return queryDatas(mapping, form, request, response);
		}
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");

			Statement stmt = conn.createStatement();
			String sql = "" ;
					

			ResultSet rs = stmt.executeQuery(sql);
					
			if(rs.next()){
				sql = "";
				
				stmt.execute(sql);
				conn.commit();
				request.setAttribute("MSG", "刪除成功");
			}else{
				request.setAttribute("MSG", "刪除資料不存在!!請重新查詢!!");
			}
					
					
					
			rs.close();
			stmt.close();
					

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
	 * 畫面導向個人出勤查詢作業
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward redirect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("redirect");
	}
	
	
	
}
