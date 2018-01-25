package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
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

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.ems.util.overtime.EMS_Overtime_Record_Control;
import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.ems.vacation.forms.EHF020800M0F;
import com.spon.ems.vacation.models.EHF020800;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.ems.vacation.tools.EMS_FixAttendance;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.flow.tools.SPON_ParticularFlowForPWSUP_Tools;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.EMS_FLOW;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action> 加班申請作業(1張多人)

 */
public class EHF020800M1A extends EditAction {
	//private EMS_Work_ScheduleForm cur_emp_work_schedule = null;
	private EMS_ACCESS ems_access;
	private EMS_FLOW ems_flow;
	
	public EHF020800M1A() {
		ems_access = EMS_ACCESS.getInstance();
		ems_flow = EMS_FLOW.getInstance();
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
	protected void generateSelectBox( Connection conn, ActionForm form, HttpServletRequest request ) {
		
		try{
			
			DecimalFormat df = new DecimalFormat("00");
			
			//產生 "時" 的下拉選單
			try{
				List listHOUR=new ArrayList();
				for (int i = 0; i < 24; i++) {
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id(df.format(i));
					bform.setItem_value(df.format(i));
					listHOUR.add(bform);
				}
				request.setAttribute("listHOUR",listHOUR);
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//產生 "分" 的下拉選單
			try{
				List listMINUTE=new ArrayList();
				for (int i = 0; i < 60; i= i+5) {//20131016修改   BY 賴泓錡
					BA_ALLKINDForm bform = new BA_ALLKINDForm();
					bform.setItem_id(df.format(i));
					bform.setItem_value(df.format(i));
					listMINUTE.add(bform);
				}
				request.setAttribute("listMINUTE",listMINUTE);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			//產生處理狀態
			try{
				List listEHF020800T0_09 = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("");
				bform.setItem_value("一請選擇一");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("處理中");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("已完成計算");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("03");
				bform.setItem_value("已確認");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("04");
				bform.setItem_value("已出帳");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("05");
				bform.setItem_value("已結算");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("06");
				bform.setItem_value("呈核");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("07");
				bform.setItem_value("已完成");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("08");
				bform.setItem_value("駁回");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("09");
				bform.setItem_value("抽單");
				listEHF020800T0_09.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("10");
				bform.setItem_value("作廢");
				listEHF020800T0_09.add(bform);
				request.setAttribute("listEHF020800T0_09", listEHF020800T0_09);
			}catch(Exception e) {
				System.out.println(e);
			}
			try{
				List listEHF020800T1_11 = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("換錢");
				listEHF020800T1_11.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("換休");
				listEHF020800T1_11.add(bform);

				request.setAttribute("listEHF020800T1_11", listEHF020800T1_11);
			}catch(Exception e) {
				System.out.println(e);
			}
			//產生加班類別的radio button
			try{
				List listEHF020800T1_13 = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("00");
				bform.setItem_value("平日");
				listEHF020800T1_13.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("例假日");
				listEHF020800T1_13.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("休息日");
				listEHF020800T1_13.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("03");
				bform.setItem_value("國定假日");
				listEHF020800T1_13.add(bform);

				request.setAttribute("listEHF020800T1_13", listEHF020800T1_13);
			}catch(Exception e) {
				System.out.println(e);
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020800M0F Form = (EHF020800M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String EHF020800T0_06[]=Form.getEHF020800T0_06().split("/");
		BaseFunction base_tools = new BaseFunction(comp_id);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		
		String sql = "" +
		" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
		" FROM EHF020900T0 " +
		" WHERE 1=1 " +
		" AND EHF020900T0_M2 = '"+EHF020800T0_06[0]+"/"+EHF020800T0_06[1]+"'"+	//考勤年月
		" AND EHF020900T0_04 = '"+comp_id+"'" +//公司代碼
		" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

		List dataList = base_tools.queryList(sql);
		if (dataList.size() > 0) {
			Map data=(Map)dataList.get(0);
			
			if("02".equals((String)data.get("EHF020900T0_02"))||"03".equals((String)data.get("EHF020900T0_02"))){
				request.setAttribute("ERR_MSG", Form.getEHF020800T0_06()+"考勤已確認，不可新增加班單");
				paramMap.put(super.ADD_FORWARD_CONFIG, "Init");
				return chk_flag;
			}
		}

		try{
			//設定EHF020800M1 新增語句
			
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			dataMap.put("EHF020800T0_12", "01");
			
			//執行新增資料
			ehf020800.addData(dataMap);
			
			//關閉EHF020800元件
			ehf020800.close();
			
			//取出KEY_ID
			Form.setEHF020800T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020800T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//設定Tab資訊
			//super.addCurrentTabConfig(paramMap, "EHF030604M1_Tab_01", "基本資料", false);
			
			//新增完成
			chk_flag = true;
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		} finally {
			try {
				base_tools.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return chk_flag;
	}
	
	/**
	 * 清除新增欄位
	 */
	protected void cleanAddField(ActionForm form){
		
		EHF020800M0F Form = (EHF020800M0F) form;
		
		try{
			//清除加班員工
			Form.setEHF020800T1_04("");
			Form.setEHF020800T1_04_TXT("");
			Form.setEHF020800T1_04_SHOW("");
			Form.setEHF020800T1_06_year("");
			Form.setEHF020800T1_06_HH("");
			Form.setEHF020800T1_06_MM("");
			Form.setEHF020800T1_07_year("");
			Form.setEHF020800T1_07_HH("");
			Form.setEHF020800T1_07_MM("");
			Form.setEHF020800T1_06_BRK("");
			Form.setEHF020800T1_06_BRK_HH("");
			Form.setEHF020800T1_06_BRK_MM("");
			Form.setEHF020800T1_07_BRK("");
			Form.setEHF020800T1_07_BRK_HH("");
			Form.setEHF020800T1_07_BRK_MM("");
			Form.setEHF020800T1_09("");
			
			Form.setEHF020800T1_11("02");
			Form.setEHF020800T1_12((float)0.0);
			Form.setEHF020800T1_13("");
			
			Form.setNOON_LIMITED("");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020800M0F Form = (EHF020800M0F) form;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String flow_no = "";
		
		try{
			//設定EHF020800M1 儲存語句
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf020800.saveData(dataMap);
			
			//儲存加班明細資料
			List ehf020800t1_list = new ArrayList();
			Map ehf020800t1_dataMap = null;
			Iterator it = Form.getEHF020800T1_LIST().iterator();		
			while(it.hasNext()){
				ehf020800t1_dataMap = BeanUtils.describe((EHF020800M0F)it.next());
				//設定加班日期
				ehf020800t1_dataMap.put("EHF020800T1_06", (String)dataMap.get("EHF0208040T0_06"));
				ehf020800t1_list.add(ehf020800t1_dataMap);
			}
			//判斷是否有加班明細資料需儲存
			if(ehf020800t1_list.size() > 0){
				ehf020800t1_dataMap = new HashMap();
				ehf020800t1_dataMap.put("EHF020800T1_LIST", ehf020800t1_list);
				//設定使用者資訊
				ehf020800t1_dataMap.putAll(paramMap);
				//儲存加班明細資料
				ehf020800.saveDetailData("EHF020800T1", ehf020800t1_dataMap);
				
			}
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
			
			if(!"Y".equals(emergency_flow)){
				//取Flow流程空表單編號
				flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", Form.getEHF020800T0_03(), Form.getEHF020800T0_04(), comp_id);
				//更新Flow流程空表單編號
				ehf020800.updateFlowNo(Form.getEHF020800T0_01(), flow_no, comp_id);
				//駁回、抽單時要更新簽核LOGS
				if(!"".equals(Form.getEHF020800T0_09()) && !"0001".equals(Form.getEHF020800T0_09())){
					ehf020800.updateFlowLogs(Form.getEHF020800T0_01(), flow_no, comp_id);
				}
			}
			
			//關閉EHF020800元件
			ehf020800.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020800T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//儲存完成
			chk_flag = true;
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		EHF020800M0F Form = (EHF020800M0F) form;
		Form = new EHF020800M0F();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String employee_id = (String)paramMap.get(super.EMPLOYEE_ID);
		List flow_sign_log_list = new ArrayList();
		
		try{
			//填單日期
			Form.setEHF020800T0_05(tools.ymdTostring(tools.getSysDate()));
			//加班日期
			Form.setEHF020800T0_06(tools.ymdTostring(tools.getSysDate()));
			//處理狀態
			Form.setEHF020800T0_09("01");  //處理中
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//設定填單人資訊
			//Map empMap = ems_tools.getEmpInfMapByEmpId((String)paramMap.get("USER_ID"), (String)paramMap.get("EMPLOYEE_ID"),   (String)paramMap.get("COMP_ID"));
			//Map depMap = ems_tools.getEmpBelongMainDepInf((String)paramMap.get("USER_ID"), (String)paramMap.get("EMPLOYEE_ID"),(String)paramMap.get("COMP_ID"), "" );
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
			
			//填單人員工工號
			Form.setEHF020800T0_03((String)((Map)empMap.get(employee_id)).get("USER_CODE"));
			Form.setEHF020800T0_03_SHOW((String)((Map)empMap.get(employee_id)).get("EMPLOYEE_ID"));
			//填單人姓名
			Form.setEHF020800T0_03_TXT((String)((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME"));
			
			//填單人部門代號
			Form.setEHF020800T0_04((String)((Map)empMap.get(employee_id)).get("DEPT_ID"));
			Form.setEHF020800T0_04_SHOW((String)((Map)empMap.get(employee_id)).get("SHOW_DEPT_ID"));
			//填單人部門名稱
			Form.setEHF020800T0_04_TXT((String)((Map)empMap.get(employee_id)).get("SHOW_DESC"));
			
			//預設加班時數處理方式 為01.換錢
			Form.setEHF020800T1_11("01");//加班時數處理方式
			//設定Tab資訊
			//super.addCurrentTabConfig(paramMap, "ESF010200M1_Tab_01", "基本資料", true);
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
			if("Y".equals(emergency_flow)){
				request.setAttribute("emergency_flow", "Y");
			}else{
				request.setAttribute("emergency_flow", "N");
			}
			
			request.setAttribute("Form5Datas", flow_sign_log_list);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		
		EHF020800M0F Form = (EHF020800M0F) form;
		Map return_map = new HashMap();
		
		try{
			//Return資料
			return_map.put("CHK_FLAG", true);
			return_map.put("FORM", form);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}

	@Override
	protected Map executeQueryEditData(Connection conn, String[] key, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020800M0F Form = (EHF020800M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		Map return_map = new HashMap();
		BaseFunction base_tools = new BaseFunction(comp_id);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		//建立FLOW元件
		SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
		List flow_sign_log_list = new ArrayList();
		String flow_no = "";
		
		try{
			
			//在職員工清單
			Map WorkEmp = null;

			//離職員工清單
			Map DepartureWorkEmp = null;
		
			//留職停薪員工清單
			Map SuspensionWithoutPayEmp= null;

			//在職員工清單
			//WorkEmp=ems_tools.getWorkEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
			
			//離職員工清單
			//DepartureWorkEmp=ems_tools.getDepartureWorkEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
			
			//留職停薪員工清單
			//SuspensionWithoutPayEmp=ems_tools.getSuspensionWithoutPayEmpNameMap(getLoginUser(request).getUserId(), getLoginUser(request).getCompId());	

			
			
			//建立EHF030604M1 查詢語句
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF020800T0_01", (key[0]));  //加班申請表單編號
			queryCondMap.putAll(paramMap);  //放入使用者資訊
			
			
			//存放正職員工清單
			//queryCondMap.put("WorkEmp", WorkEmp);
			//存放離職員工清單
			//queryCondMap.put("DepartureWorkEmp", DepartureWorkEmp);
			//存放留職停薪員工清單
			//queryCondMap.put("SuspensionWithoutPayEmp", SuspensionWithoutPayEmp);
			
			
			
			Map dataMap = ehf020800.queryEditData(queryCondMap);
			
			//關閉EHF020800 元件
			ehf020800.close();
			
			if(!dataMap.isEmpty()){
				
				BeanUtils.copyProperties(Form, dataMap);
				String EHF020800T0_06[]=Form.getEHF020800T0_06().split("/");
				//設定Collection資訊
				//Form.setESF010200_DETAIL((List)dataMap.get("ESF010200_DETAIL"));
				
				//設定前端STRUTS-LAYOUT顯示模式
				if(Integer.parseInt(Form.getEHF020800T0_09()) >= 3 && !"09".equals(Form.getEHF020800T0_09())){
					//出帳後則頁面不可編輯
					super.setFormDisplayMode(paramMap, super.StrutsLayoutInspectMode);
				}
				String sql = "" +
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
				" FROM EHF020900T0 " +
				" WHERE 1=1 " +
				" AND EHF020900T0_M2 = '"+EHF020800T0_06[0]+"/"+EHF020800T0_06[1]+"'"+	//考勤年月
				" AND EHF020900T0_04 = '"+comp_id+"'" +//公司代碼
				" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

				List dataList = base_tools.queryList(sql);
				if (dataList.size() > 0) {
					Map data=(Map)dataList.get(0);
					
					if("02".equals((String)data.get("EHF020900T0_02"))||"03".equals((String)data.get("EHF020900T0_02"))){
						request.setAttribute("SHOW", "NO");
						request.setAttribute("MSG_EDIT", "考勤已確認");
					}
				}
				chk_flag = true;
			}
			Form.setEHF020800T1_06_year(Form.getEHF020800T0_06());
			Form.setEHF020800T1_07_year(Form.getEHF020800T0_06());
			Form.setEHF020800T1_11("02");
			
			
			//設定Tab資訊
			//super.addCurrentTabConfig(paramMap, "EHF030604M1_Tab_01", "基本資料", true);
			request.setAttribute("Fields_Hide", "YES");
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", Form);
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", Form.getEHF020800T0_03(), "");
			
			//01 草稿儲存，06 呈核，11 審核中，08 駁回，03 完成，09 抽單，10 作廢 
			if(!"".equals(Form.getEHF020800T0_09()) && !"01".equals(Form.getEHF020800T0_09()) &&
					!"08".equals(Form.getEHF020800T0_09()) && !"09".equals(Form.getEHF020800T0_09())){
				paramMap.put(super.EMS_STRUTSLAYOUT_MODE, super.StrutsLayoutInspectMode);
			}else{
				paramMap.put(super.EMS_STRUTSLAYOUT_MODE, super.StrutsLayoutEditMode);
			}
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
			if("Y".equals(emergency_flow)){
				request.setAttribute("emergency_flow", "Y");
			}else{
				flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", Form.getEHF020800T0_03(), Form.getEHF020800T0_04(), comp_id);
				
				if(!"".equals(Form.getEHF020800T0_09()) && !"0001".equals(Form.getEHF020800T0_09())){
					if(ems_flow.checkFlowNo(conn, Form.getEHF020800T0_01(), "EHF020800M0", flow_no, comp_id)){
						//判斷簽核流程身分
						ems_flow.checkFlowIdentity(conn, request, getLoginUser(request), flow_no, Form.getEHF020800T0_01(), "EHF020200T0", comp_id);
						//判斷是否為FLOW最後一站
						ems_flow.checkIsLastFlowStation(conn, request, getLoginUser(request), flow_no, Form.getEHF020800T0_01(), comp_id);
					}
				}
				
				flow_sign_log_list = spon_flow_tools.getFlowSignLogList(flow_no, Form.getEHF020800T0_01(), comp_id);
				
				request.setAttribute("emergency_flow", "N");
			}
			
			request.setAttribute("Form5Datas", flow_sign_log_list);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}finally {
			try {
				base_tools.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return return_map;
	}


	@Override
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		EHF020800M0F Form = (EHF020800M0F) form;
		String key = "";
		try{			
			key = String.valueOf(Form.getEHF020800T0_01());
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return key;
	}

	@Override
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		EHF020800M0F Form = (EHF020800M0F) form;
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020800T0_01());
			paramMap.put(super.KEY_ID, key_id);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
	}
	
	/**
	 * 清除新增明細欄位
	 */
	protected void cleanAddDetailField(ActionForm form){
		
		EHF020800M0F Form = (EHF020800M0F) form;
		//Form.setEHF020800T1_04("");
		
		try{
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public boolean executeAddDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub

		boolean chk_flag = false;
		EHF020800M0F Form = (EHF020800M0F) form;
		

		BA_TOOLS tools = BA_TOOLS.getInstance();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		Map tempClassMap = null;
		
		try{
			//設定EHF030604 細項 新增語句
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);

			//設定使用者資訊
			detailDataMap.putAll(paramMap);

			AuthorizedBean authbean = getLoginUser(request);
			
			
			
			HR_TOOLS hr_tools = new HR_TOOLS();

			//取得公司名稱
			Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
			//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			//職務
			Map titleMap = hr_tools.getTitleNameMap(authbean.getCompId());
			
			
			
			String empName="";

			
			if(!"".equals(Form.getEHF020800T1_04())||!"".equals(Form.getEHF020800T0_11())){
				//整理Map，如有指定部門與員工，將會被剔除，只會留下指定的部門或是員工
				Iterator iter_emp = empMap.entrySet().iterator(); 
				while (iter_emp.hasNext()) {
					Map.Entry entry = (Map.Entry) iter_emp.next(); 
				    String key = (String)entry.getKey(); 
				    if(!"".equals(Form.getEHF020800T1_04())){
				    	//當有指定員工，必須將其餘人員存起來，之後從Map刪除
				    	if(!key.equals(Form.getEHF020800T1_04())){
				    		empName+=key+",";
				    	}	
				    }else if(!"".equals(Form.getEHF020800T0_11())){
				    	//指定部門時，必須將非此部門的員工剔除
				    	String dep = (String)((Map)empMap.get(key)).get("DEPT_ID");//部門
				    	if(!dep.equals(Form.getEHF020800T0_11())){
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
			
			//建立考勤產生元件
			EMS_AttendanceAction ems_att_tools = EMS_AttendanceAction.getInstance((String)paramMap.get(super.COMP_ID), "", authbean.getUserId());
			
			//產生預排換班處理元件
			EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
			
			
			Map NotWorkDay= new HashMap();
			//取得排班表與行事曆
			ems_wsc_tools.getVacations(conn, empMap, depMap, authbean.getCompId(), Form.getEHF020800T0_06(),"", NotWorkDay);
			
			NotWorkDay=(Map) NotWorkDay.get(Form.getEHF020800T1_04());

			
			
			boolean isHoliday = true;
		
			if(NotWorkDay.get(Form.getEHF020800T0_06())==null){
				//表示要上班,但   是使用預設班別
				isHoliday = false;
			}else if(NotWorkDay.get(Form.getEHF020800T0_06()).equals("-1")){
				//表示假日
				isHoliday = false;
			}else{
				//表示要上班
				isHoliday = true;
			}
			//取得員工目前班別資訊
			AuthorizedBean user_authbean = new AuthorizedBean();
			user_authbean.setEmployeeID(Form.getEHF020800T1_04());
			user_authbean.setCompId(authbean.getCompId());
			
			//依據排班表設定員工指定日期的班別資料
			if( isHoliday){
				tempClassMap = hr_tools.getEmpClassByNo( conn, (String)paramMap.get(super.COMP_ID),Integer.valueOf( (String)NotWorkDay.get(Form.getEHF020800T0_06())));
				//避免 class_no 不正確, 若以 class_no 找不到 classMap 則以員工當前班別作為 classMap
				if(tempClassMap.isEmpty()){
					tempClassMap = hr_tools.getEmpClass(conn, user_authbean);
				}
			}else{
				/*request.setAttribute("ERR_MSG", Form.getEHF020800T0_06()+"排班表錯誤，請檢查");
				return chk_flag;*/
				tempClassMap = hr_tools.getEmpClass(conn, user_authbean);
			}
			
			
			hr_tools.close();
			//依照排班表取得班別上班時間
			Calendar class_in_cal = ems_att_tools.getCalendarByClass(
					conn, Form.getEHF020800T0_06(), Form.getEHF020800T1_04(),(String)paramMap.get(super.COMP_ID), tempClassMap, 1);  //班別設定的上班時間(Calendar)
				
			//依照排班表取得班別下班時間
			Calendar class_out_cal = ems_att_tools.getCalendarByClass(
					conn, Form.getEHF020800T0_06(), Form.getEHF020800T1_04(), (String)paramMap.get(super.COMP_ID), tempClassMap, 2);  //班別設定的下班時間(Calendar)

			
			
			Calendar class_siesta_in_cal = ems_att_tools.getCalendarByClass(
					conn, Form.getEHF020800T0_06(), Form.getEHF020800T1_04(), (String) (String)paramMap.get(super.COMP_ID), tempClassMap, 7);  //班別設定的上午下班時間(Calendar)
			Calendar class_siesta_out_cal = ems_att_tools.getCalendarByClass(
					conn, Form.getEHF020800T0_06(), Form.getEHF020800T1_04(), (String) (String)paramMap.get(super.COMP_ID), tempClassMap, 8);  //班別設定的下午上班時間(Calendar)
			
			
			
			Calendar cur_cal_in = tools.covertStringToCalendar(Form.getEHF020800T1_06_year()+" "+Form.getEHF020800T1_06_HH()+":"+Form.getEHF020800T1_06_MM()+":00", "yyyy/MM/dd HH:mm:ss");
			Calendar cur_cal_out = tools.covertStringToCalendar(Form.getEHF020800T1_07_year()+" "+Form.getEHF020800T1_07_HH()+":"+Form.getEHF020800T1_07_MM()+":00", "yyyy/MM/dd HH:mm:ss");

			//System.out.println("加班開始時間			 		:"+tools.covertDateToString(cur_cal_in.getTime(), "yyyy/MM/dd HH:mm:ss"));//加班開始時間
			//System.out.println("加班結束時間			 		:"+tools.covertDateToString(cur_cal_out.getTime(), "yyyy/MM/dd HH:mm:ss"));//加班開始時間
			//System.out.println("依照排班表設定的班別->下班時間		:"+tools.covertDateToString(class_out_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));//依照排班表設定的班別->下班時間
			//System.out.println("依照排班表設定的班別->休息開始時間		:"+tools.covertDateToString(class_siesta_in_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));//依照排班表設定的班別->下班時間
			//System.out.println("依照排班表設定的班別->休息結束時間		:"+tools.covertDateToString(class_siesta_out_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));//依照排班表設定的班別->下班時間
			//System.out.println("依照排班表設定的班別->上班時間		:"+tools.covertDateToString(class_in_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));//依照排班表設定的班別->上班時間

			
			
			if(NotWorkDay.get(Form.getEHF020800T0_06())==null){
				//表示要上班
				isHoliday = true;
			}else if(NotWorkDay.get(Form.getEHF020800T0_06()).equals("-1")){
				//表示假日
				isHoliday = false;
			}else{
				//表示要上班
				isHoliday = true;
			}

			if(isHoliday){
				//平日才需要判斷
				if (!tools.isHoliday(conn, Form.getEHF020800T0_06(),(String) paramMap.get(super.COMP_ID))) {
					if (cur_cal_in.compareTo(class_in_cal) >= 0&& cur_cal_in.compareTo(class_out_cal) < 0) {
						request.setAttribute("ERR_MSG", "加班必須在上班之前或是下班之後，請檢查");
						return chk_flag;
					}
				} 
			
			}else if(!isHoliday){
				//假日
				
			}
			
			if("EHF020800T1".equals(type)){
				//建立加班明細
				
				//Set 加班表單編號
				detailDataMap.put("EHF020800T1_01", Form.getEHF020800T0_01());
				detailDataMap.put("EHF020800T1_12", Form.getEHF020800T1_12());
				
				/*
				//處理加班時間與加班時數
				int overtime_in = Integer.parseInt(Form.getEHF020800T1_06_HH()+Form.getEHF020800T1_06_MM());
				int overtime_out = Integer.parseInt(Form.getEHF020800T1_07_HH()+Form.getEHF020800T1_07_MM());
				
				if(overtime_in > overtime_out){
					//表示加班區間有跨午夜十二點
					overtime_out = overtime_out + ((int)2400);
				}
				
				//處理加班時數
				int overtime_in_sec = ems_tools.TimeToSecs(overtime_in+"");
				int overtime_out_sec = ems_tools.TimeToSecs(overtime_out+"");
				int overtime_sec = overtime_out_sec - overtime_in_sec;
				float overtime_hours = (((float)overtime_sec)/60/60);
				
				//設定加班開始時間
				Calendar overtime_in_cal =
				ems_tools.covertStringToCalendar(
				Form.getEHF020800T0_06()+" "+overtime_in, 
				"yyyy/MM/dd HHmm");
				
				//建立Calendar處理加班時間
				Calendar overtime_out_cal =
				ems_tools.covertStringToCalendar(
				Form.getEHF020800T0_06()+" "+overtime_out, 
				"yyyy/MM/dd HHmm");
				
				//建立加班時間與加班時數資訊
				detailDataMap.put("EHF020800T1_06", ems_tools.covertDateToString(overtime_in_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));  //加班開始時間
				detailDataMap.put("EHF020800T1_07", ems_tools.covertDateToString(overtime_out_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));  //加班結束時間
				detailDataMap.put("EHF020800T1_08", overtime_hours+"");  //加班時數
				*/
				
				//修改於20131113  BY賴泓錡
				float min_pay_hour = Float.parseFloat(tools.getSysParam(conn, (String)paramMap.get("COMP_ID"), "MIN_PAY_HOUR"));
				//處理加班時間資訊
				Map overtime_datetime_map =
				ehf020800.composeOvertimeDate(Form.getEHF020800T1_06_year(), 
											  Form.getEHF020800T1_06_HH()+Form.getEHF020800T1_06_MM(),
											  Form.getEHF020800T1_07_year(), 
											  Form.getEHF020800T1_07_HH()+Form.getEHF020800T1_07_MM(),min_pay_hour);
				
				//Set overtime_datetime data
				detailDataMap.putAll(overtime_datetime_map);
				
				//處理加班休息時間資訊
				Map overtime_break_datetime_map =
				ehf020800.composeOvertimeBreakDate(Form.getEHF020800T1_06_year(),Form.getEHF020800T1_07_year(),
												   Form.getEHF020800T1_06_HH()+Form.getEHF020800T1_06_MM(), 
												   Form.getEHF020800T1_07_HH()+Form.getEHF020800T1_07_MM(), 
												   Form.getEHF020800T1_06_BRK_HH()+ Form.getEHF020800T1_06_BRK_MM(), 
												   Form.getEHF020800T1_07_BRK_HH()+ Form.getEHF020800T1_07_BRK_MM());
				
				//Set overtime break_datetime data
				detailDataMap.putAll(overtime_break_datetime_map);
				
				//新增加班明細資訊
				ehf020800.addDetailData(type, detailDataMap);
			}
			
			//關閉EHF020800 元件
			ehf020800.close();
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF020800T0_01");
			paramMap.put(super.KEY_ID, key_id);
			
			//設定Tab資訊
			//super.addCurrentTabConfig(paramMap, "ESF010200M1_Tab_01", "採購明細", false);
			this.cleanAddField(Form);
			//新增採購明細完成
			chk_flag = true;
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	public boolean executeDelDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020800M0F Form = (EHF020800M0F) form;
		boolean chk_flag = false;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		try{
			//執行細項資料刪除
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			AuthorizedBean authbean = getLoginUser(request);
			
			Calendar cur_system_cal = Calendar.getInstance();//取得當前時間
			// 設定系統時間
			cur_system_cal.set(Calendar.HOUR_OF_DAY, 8);
			cur_system_cal.set(Calendar.MINUTE, 0);
			cur_system_cal.set(Calendar.SECOND, 0);
			cur_system_cal.set(Calendar.MILLISECOND, 0);

			if("EHF020800T1".equals(type)){
				//刪除加班明細
				//刪除加班明細資訊
				ehf020800.delDetailData(type,detailDataMap);
			}
			
			//關閉EHF020800 元件
			ehf020800.close();

			
			String name[]=Form.getEHF020800T1_04().split("/");
			
			
			//重產考勤
			ehf020800.Again_Produce_att(conn,Form.getEHF020800T0_06(),name[0],authbean.getCompId(),authbean.getUserId());
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF020800T0_01");
			paramMap.put(super.KEY_ID, key_id);
			
			//設定Tab資訊
			//super.addCurrentTabConfig(paramMap, "ESF010200M1_Tab_01", "採購明細", false);
			
			chk_flag = true;
			
			
			Form.setEHF020800T1_04("");
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	public boolean executeSaveDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020800M0F Form = (EHF020800M0F) form;
		boolean chk_flag = false;
		
		try{
			//執行細項資料更新
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//建立細項更新資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);

			if("EHF020800T1".equals(type)){
				//更新加班明細
				ehf020800.saveDetailData(type,detailDataMap);
			}
			
			//關閉EHF020800 元件
			ehf020800.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF020800T0_01");
			paramMap.put(super.KEY_ID, key_id);
			
			//設定Tab資訊
			//super.addCurrentTabConfig(paramMap, "ESF010200M1_Tab_01", "採購明細", false);
			
			chk_flag = true;
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	public boolean executeQueryDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020800M0F Form = (EHF020800M0F) form;
		boolean chk_flag = false;
		try{
			//執行細項資料查詢
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			//建立細項查詢資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);

			//設定使用者資訊
			detailDataMap.putAll(paramMap);

			if("EHF020800T1".equals(type)){
				//加班明細
				//查詢加班明細資訊
				Map dataMap = ehf020800.queryDetailData(type,detailDataMap);
			}
			
			//關閉EHF020800 元件
			ehf020800.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF020800T0_01");
			paramMap.put(super.KEY_ID, key_id);

			//設定Tab資訊
			//super.addCurrentTabConfig(paramMap, "ESF010200M1_Tab_01", "採購明細", false);
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 確認
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		EHF020800M0F Form = (EHF020800M0F) form;

		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		AuthorizedBean authbean = getLoginUser(request);
		
		Calendar cur_system_cal = Calendar.getInstance();
		// 設定系統時間
		cur_system_cal.set(Calendar.HOUR_OF_DAY, 8);
		cur_system_cal.set(Calendar.MINUTE, 0);
		cur_system_cal.set(Calendar.SECOND, 0);
		cur_system_cal.set(Calendar.MILLISECOND, 0);
		
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			//執行確認
			
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//建立細項查詢資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);

			//設定使用者資訊
			dataMap.putAll(paramMap);
			//執行確認
			ehf020800.confirm(dataMap);

			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			//取得加班記錄 use 加班單
			overtime_list = ehf020800.queryOVList(dataMap);
			
			try {
				Map<String, String> Map = null;
				Map<String, String> return_map = null;

				Iterator<Map<String, String>> it = overtime_list.iterator();

				while (it.hasNext()) {

					Map = it.next();
					//轉換Map Data
					Map = ems_ov_ctrl.mappingOVMapData(Map);
					//重產考勤
					ehf020800.Again_Produce_att(conn,Map.get("EHF020801T0_04"),Map.get("EHF020801T0_02"),authbean.getCompId(),authbean.getUserId());
					//針對加班處理方式處理
					if("02".equals(Map.get("EHF020801T0_09"))){//換休->換補休
						//換休當年度可以使用到隔年6月底
						EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						setVaSql.updataOverTimeVacation("0006", Map, authbean);
					}
					//例假日，外加補休一天
					if("01".equals(Map.get("OV_TYPE"))){
						EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						setVaSql.updataOverTimeVacation("1000", Map, authbean);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			//關閉EHF020800 元件
			ehf020800.close();
			ems_ov_ctrl.close();
			// 更新資料庫
			conn.commit();
			
			
			//處理顯示訊息
			request.setAttribute(super.msg_edit_config, "確認成功!!");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				conn.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		return this.queryForm(mapping, Form, request, response);
	}
	
	

	/**
	 * 還原
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward recovery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		EHF020800M0F Form = (EHF020800M0F) form;
		EMS_DB_CONTROLLER ems_db_c = null;
		AuthorizedBean authbean = getLoginUser(request);
		
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			//執行還原
			
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//建立細項查詢資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);

			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行還原
			ehf020800.recovery(dataMap);
			
			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			//取得加班記錄 use 加班單
			overtime_list = ehf020800.queryOVList(dataMap);
			
			try {
				Map<String, String> Map = null;
				Map<String, String> return_map = null;

				Iterator<Map<String, String>> it = overtime_list.iterator();

				while (it.hasNext()) {

					Map = it.next();
					//轉換Map Data
					Map = ems_ov_ctrl.mappingOVMapData(Map);
					//針對加班處理方式處理
					if("02".equals(Map.get("EHF020801T0_09"))){//換休->換補休
						//換休當年度可以使用到隔年３月底
						EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						setVaSql.updataOverTimeVacation("0009", Map, authbean);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//關閉EHF020800 元件
			ehf020800.close();
			
			//處理顯示訊息
			request.setAttribute(super.msg_edit_config, "還原成功!!");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		return this.queryForm(mapping, Form, request, response);
	}
	
	
	/**
	 * 作廢
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		EHF020800M0F Form = (EHF020800M0F) form;
		EMS_DB_CONTROLLER ems_db_c = null;
		AuthorizedBean authbean = getLoginUser(request);
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
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(super.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			//執行還原
			
			//建立EHF020800元件
			EHF020800 ehf020800 = new EHF020800();
			
			//建立細項查詢資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);

			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行還原
			ehf020800.remove(dataMap);
			
			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			//取得加班記錄 use 加班單
			overtime_list = ehf020800.queryOVList(dataMap);
			
			try {
				Map<String, String> Map = null;
				Map<String, String> return_map = null;

				Iterator<Map<String, String>> it = overtime_list.iterator();

				while (it.hasNext()) {

					Map = it.next();
					//轉換Map Data
					Map = ems_ov_ctrl.mappingOVMapData(Map);
					ehf020800.Again_Produce_att(conn,(String)Map.get("EHF020801T0_04"),(String) Map.get("EHF020801T0_02"),authbean.getCompId(), USER_ID);
					//針對加班處理方式處理
					if("02".equals(Map.get("EHF020801T0_09"))){//換休->換補休
						//換休當年度可以使用到隔年３月底
						EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						setVaSql.updataOverTimeVacation("0009", Map, authbean);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//關閉EHF020800 元件
			ehf020800.close();
			
			//處理顯示訊息
			request.setAttribute(super.msg_edit_config, "作廢成功!!");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		return this.queryForm(mapping, Form, request, response);
	}
	
	/**
	 * 加班單呈核動作 -- 緩衝區
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */	
	public ActionForward submitForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return submitDatas(mapping, form, request, response);
	}
	
	/**
	 * 加班單 -- 呈核動作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward submitDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EHF020800M0F Form = (EHF020800M0F) form;
		String comp_id = getLoginUser(request).getCompId();  //公司代碼
		String user_name = getLoginUser(request).getUserName();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
			
			if("Y".equals(emergency_flow)){
				
				EHF020800 ehf020800 = new EHF020800();
				
				//建立查詢資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020800T0_01", Form.getEHF020800T0_01());  // 
			
				if(ehf020800.submitDatas(queryCondMap, comp_id, user_name)){
					//EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
					//setVaSql.updataVacationData(queryCondMap, comp_id);
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核失敗!!");
				}
			
				ehf020800.close();
				
			}else{
				//建立FLOW元件
				SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
				
				//取得當前使用者資訊
				SC003F userform = super.userform(request);
				
				flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", Form.getEHF020800T0_03(), Form.getEHF020800T0_04(), comp_id);
				
				//組成 condMap
				Map condMap = new HashMap();
				condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
				
				//執行FLOW submit
				spon_flow_tools.submit(flow_no, 
									   Form.getEHF020800T0_01(), 
									   condMap,  //條件資料Map
									   userform.getSC0030_01(), 
									   userform.getSC0030_14());
				
				//取得當前待簽核LOG
				Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020800T0_01(), comp_id);
				
				this.updateFormStatus(spon_flow_tools, request, Form.getEHF020800T0_01(), current_sign_map, flow_no, "submit");
				
			}
			
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
		
		return queryForm(mapping, form, request, response);
	}
		
	//從緊急流程抽單
	public ActionForward signFormEmergency(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		String emergency_flow_action = request.getParameter("emergency_flow_action");
		return signDatasEmergency(mapping, form, request, response, emergency_flow_action);
	}
	
	public ActionForward signDatasEmergency(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
			HttpServletResponse response, String flow_action){
		
		BaseFunction base_tools = new BaseFunction();
		
		EHF020800M0F Form = (EHF020800M0F) form;
		String fromKey = Form.getEHF020800T0_01()+"";
		String agent_sign_table = "EHF020800T0";	//簽核table
		String agent_sign_form_status = "EHF020800T0_09";	//簽核表單狀態
		String agent_sign_form_status_desc = "EHF020800T0_09_DESC";	//簽核表單狀態說明
		String agent_sign_form_status_code = ""; //flow_action
		String agent_sign_form_status_code_desc = ""; //flow_action說明
		String agent_sign_form_recieptNo = "EHF020800T0_01";	//簽核表單單號
		String agent_sign_compId = getLoginUser(request).getCompId();	//簽核公司代碼
		String compId = "EHF020800T0_10";
		
		Connection conn = null;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			if("0001".equals(flow_action)){	//呈核
				agent_sign_form_status_code = "06";
				agent_sign_form_status_code_desc = "呈核";
			}else if("0003".equals(flow_action)){	//駁回
				agent_sign_form_status_code = "08";
				agent_sign_form_status_code_desc = "駁回";
			}else if("0013".equals(flow_action)){	//抽單
				agent_sign_form_status_code = "09";
				agent_sign_form_status_code_desc = "抽單";
			}else if("0002".equals(flow_action)){	//作廢
				agent_sign_form_status_code = "10";
				agent_sign_form_status_code_desc = "作廢";	//
			}
			
			String sql = " UPDATE " + agent_sign_table +
			" SET " + agent_sign_form_status + "='" + agent_sign_form_status_code + "'," +
			" " + agent_sign_form_status_desc + "='" + agent_sign_form_status_code_desc + "' " +
			" WHERE " + agent_sign_form_recieptNo + "='" + fromKey + "' " +
			" AND " + compId + "='" + agent_sign_compId + "'";
	
			base_tools.executeSQL(sql);
	
			base_tools.commit();
			
			//若是執行完成的動作，要回寫請假時數到年度休假資料中
			if("07".equals(agent_sign_form_status_code)){
				
				//建立查詢資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020800T0_01", Form.getEHF020800T0_01());  //表單編號
				
//				EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
//				setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());
			}	
			
		}catch (Exception e) {
			System.out.println(e);
			base_tools.close();
			base_tools.rollback();
			request.setAttribute("MSG", agent_sign_form_status_code_desc+"失敗");
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		base_tools.close();
		request.setAttribute("MSG", agent_sign_form_status_code_desc+"成功");
		
		return queryForm(mapping, form, request, response);
	}
	
	/**
	 * 簽核動作 -- 緩衝區
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward signForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		String flow_action = request.getParameter("FLOW_ACTION");
		
		try{
			switch (Integer.parseInt(flow_action)){
				
				case 11://核准
					return approve(mapping, form, request, response);
				
				case 8://駁回
					return reject(mapping, form, request, response);
					
				case 9://抽單
					return cancel(mapping, form, request, response);
					
				case 10:
					return invalid(mapping, form, request, response);
			
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 核准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		EHF020800M0F Form = (EHF020800M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
							
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", Form.getEHF020800T0_03(), Form.getEHF020800T0_04(), comp_id);
				
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020800T0_01(), comp_id);
				
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF020800T0_01(), current_sign_map, flow_no, "approve");
				
			//組成 condMap                                                                                                                                                                                                                                                                                                    
			Map condMap = new HashMap();
				
			//執行FLOW approve
			spon_flow_tools.approve(flow_no, 
									Form.getEHF020800T0_01(), 
									condMap,  //條件資料Map 
									Form.getSIGN_COMMENT(),  //簽核意見
								    userform.getSC0030_01(), 
								    userform.getSC0030_14());
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准失敗!!");
		}finally {
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
	 * 駁回
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		EHF020800M0F Form = (EHF020800M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", Form.getEHF020800T0_03(), Form.getEHF020800T0_04(), comp_id);
			
			//組成 condMap
			Map condMap = new HashMap();
			//condMap.put("SUPPLY_USER_ID", Form.getDATA02());
			
			//執行FLOW reject
			spon_flow_tools.reject(flow_no, 
								   Form.getEHF020800T0_01(), 
								   condMap,  //條件資料Map
								   Form.getSIGN_COMMENT(),  //簽核意見
								   userform.getSC0030_01(), 
								   userform.getSC0030_14());
			
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020800T0_01(), comp_id);
			
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF020800T0_01(), current_sign_map, flow_no, "reject");
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
		}finally {
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
	 * 抽單
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		EHF020800M0F Form = (EHF020800M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
			
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", Form.getEHF020800T0_03(), Form.getEHF020800T0_04(), comp_id);
			
			//執行FLOW reject
			spon_flow_tools.cancel(flow_no, 
								   Form.getEHF020800T0_01(), 
								   userform.getSC0030_01(),  //抽單人
								   Form.getSIGN_COMMENT(),  //簽核意見
								   userform.getSC0030_01(), 
								   userform.getSC0030_14());
			
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020800T0_01(), comp_id);
			
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF020800T0_01(), current_sign_map, flow_no, "cancel");
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
		}finally {
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
	
	public ActionForward invalid(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		EHF020800M0F Form = (EHF020800M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			EHF020800 ehf020800 = new EHF020800();
			
			boolean flow_flag = ehf020800.chkFlowNo(Form.getEHF020800T0_01(), comp_id);
			
			ehf020800.close();
			
			if(flow_flag){
				
				//建立FLOW元件
				SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
				
				//取得當前使用者資訊
				SC003F userform = super.userform(request);
				
				flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", Form.getEHF020800T0_03(), Form.getEHF020800T0_04(), comp_id);
				
				//組成 condMap
				Map condMap = new HashMap();
				condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
				
				//執行FLOW invalid
				spon_flow_tools.invalid(flow_no, 
						   			   	Form.getEHF020800T0_01(), 
						   			   	userform.getSC0030_01(),  //抽單人
						   			   	Form.getSIGN_COMMENT(),  //簽核意見
						   			   	userform.getSC0030_01(), 
						   			   	userform.getSC0030_14());
				
				//取得當前待簽核LOG
				Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020800T0_01(), comp_id);
				
				this.updateFormStatus(spon_flow_tools, request, Form.getEHF020800T0_01(), current_sign_map, flow_no, "invalid");
				
			}else{
				
				this.signDatasEmergency(mapping, form, request, response, "0002");
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"作廢失敗!!");
		}finally {
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
	 * 自動核准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward AutoApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		EHF020800M0F Form = (EHF020800M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		int auto_flow_site_sn = 0;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
							
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020800M0", Form.getEHF020800T0_03(), Form.getEHF020800T0_04(), comp_id);
			
			auto_flow_site_sn = this.getAutoFlowSiteSN(conn, flow_no, comp_id);						
			
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020800T0_01(), comp_id);
			
			if("填寫中".equals((String)current_sign_map.get("CURRENT_SIGN_FORM_STATUS_NAME"))){
				//尚未呈核過，申請人是主管流程角色
				Map condMap = new HashMap();
				condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
				
				//執行FLOW submit
				spon_flow_tools.submit(flow_no, 
									   Form.getEHF020800T0_01(), 
									   condMap,  //條件資料Map
									   userform.getSC0030_01(), 
									   userform.getSC0030_14());
				
				//取得當前待簽核LOG
				Map auto_current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020800T0_01(), comp_id);
				
				this.updateFormStatus(spon_flow_tools, request, Form.getEHF020800T0_01(), auto_current_sign_map, flow_no, "submit");
			}
			
			Map auto_current_approve_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020800T0_01(), comp_id);
				
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF020800T0_01(), auto_current_approve_map, flow_no, "approve");
				
			//組成 condMap                                                                                                                                                                                                                                                                                                    
			Map condMap = new HashMap();
				
			//執行FLOW approve
			spon_flow_tools.AutoApprove(flow_no, 
										Form.getEHF020800T0_01(), 
										condMap,  //條件資料Map 
										"系統自動核准",  //簽核意見
										userform.getSC0030_01(), 
										userform.getSC0030_14(),
										auto_flow_site_sn);
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"自動核准失敗!!");
		}finally {
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
	 * 更新表單狀態
	 * @param spon_flow_tools
	 * @param request
	 * @param form_no
	 * @param current_sign_map
	 * @param flow_no
	 * @param action
	 */
	private void updateFormStatus(SPON_ParticularFlowForPWSUP_Tools spon_flow_tools, HttpServletRequest request,
			String form_no, Map current_sign_map, String flow_no, String action) {
		// TODO Auto-generated method stub
		
		String comp_id = getLoginUser(request).getCompId();
		String user_name = getLoginUser(request).getUserName();
		
		try{
			if("submit".equals(action)){
				
				EHF020800 ehf020800 = new EHF020800();
				
				//建立查詢資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020800T0_01", form_no);  // 
			
				if(ehf020800.submitDatas(queryCondMap, comp_id, user_name)){
					//不是緊急流程就要寫入流程空表單編號
					ehf020800.updateFlowNo(form_no, flow_no, comp_id);
					
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核失敗!!");
				}
			
				ehf020800.close();
				
			}else if("approve".equals(action)){
				
				boolean next_flow_station_flag = spon_flow_tools.isNextFlowStation(current_sign_map, comp_id);
				
				EHF020800 ehf020800 = new EHF020800();
				
				if(next_flow_station_flag){
					//有下一站
					//建立資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF020800T0_01", form_no);  //表單編號
					queryCondMap.put("EHF020800T0_09", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
					queryCondMap.put("EHF020800T0_09_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
					
					if(ehf020800.submitFlowDatas(queryCondMap, comp_id, user_name)){
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准失敗!!");
					}
					
				}else{
					//無下一站
					//建立資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF020800T0_01", form_no);  //表單編號
					queryCondMap.put("EHF020800T0_09", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
					queryCondMap.put("EHF020800T0_09_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
					queryCondMap.put("COMP_ID", comp_id);
					
					if(ehf020800.submitFlowDatas(queryCondMap, comp_id, user_name)){
						
						this.updataOvertime(queryCondMap, getLoginUser(request), ehf020800, "approve");						
						
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成失敗!!");
					}
					
				}
				
				ehf020800.close();
				
			}else if("reject".equals(action)){
				
				EHF020800 ehf020800 = new EHF020800();
				
				//建立資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020800T0_01", form_no);  //表單編號
				queryCondMap.put("EHF020800T0_09", "08");	//FLOW狀態號碼
				queryCondMap.put("EHF020800T0_09_DESC", "駁回"); //FLOW狀態名稱
				
				if(ehf020800.submitFlowDatas(queryCondMap, comp_id, user_name)){
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
				}
				
				ehf020800.close();
				
			}else if("cancel".equals(action)){
				
				EHF020800 ehf020800 = new EHF020800();
				
				//建立資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020800T0_01", form_no);  //表單編號
				queryCondMap.put("EHF020800T0_09", "09");	//FLOW狀態號碼
				queryCondMap.put("EHF020800T0_09_DESC", "抽單"); //FLOW狀態名稱
				
				if(ehf020800.submitFlowDatas(queryCondMap, comp_id, user_name)){
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"抽單成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"抽單失敗!!");
				}
				
				ehf020800.close();
				
			}else if("invalid".equals(action)){
				
				EHF020800 ehf020800 = new EHF020800();
				
				//建立資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020800T0_01", form_no);  //表單編號
				queryCondMap.put("EHF020800T0_09", "10");	//FLOW狀態號碼
				queryCondMap.put("EHF020800T0_09_DESC", "作廢"); //FLOW狀態名稱
				queryCondMap.put("COMP_ID", comp_id);
				
				if(ehf020800.submitFlowDatas(queryCondMap, comp_id, user_name)){
					
					this.updataOvertime(queryCondMap, getLoginUser(request), ehf020800, "invalid");	
					
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"作廢成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"作廢失敗!!");
				}
				
				ehf020800.close();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * 重產加班考勤與換休寫入
	 * @param queryCondMap
	 * @param authbean
	 * @param ehf020800
	 */
	private void updataOvertime(Map queryCondMap, AuthorizedBean authbean,
			EHF020800 ehf020800, String action) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			
			if("approve".equals(action)){
				
				//取得加班記錄 use 加班單
				overtime_list = ehf020800.queryOVList(queryCondMap);
				
				//連動加班記錄
				ems_ov_ctrl.putOvertimeRecord(overtime_list);
				
				try {
					Map<String, String> Map = null;
					Map<String, String> return_map = null;

					Iterator<Map<String, String>> it = overtime_list.iterator();

					while (it.hasNext()) {

						Map = it.next();
						//轉換Map Data
						Map = ems_ov_ctrl.mappingOVMapData(Map);
						//重產考勤
						ehf020800.Again_Produce_att(conn, Map.get("EHF020801T0_04"), Map.get("EHF020801T0_02"), authbean.getCompId(), authbean.getUserId());
						//針對加班處理方式處理
						if("02".equals(Map.get("EHF020801T0_09"))){//換休->換補休
							//換休當年度可以使用到隔年6月底
							EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
							setVaSql.updataOverTimeVacation("0006", Map, authbean);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}else if("invalid".equals(action)){
				
				//取得加班記錄 use 加班單
				overtime_list = ehf020800.queryOVList(queryCondMap);
				
				//連動加班記錄
				ems_ov_ctrl.removeOvertimeRecord(overtime_list);
				
				try {
					Map<String, String> Map = null;
					Map<String, String> return_map = null;

					Iterator<Map<String, String>> it = overtime_list.iterator();

					while (it.hasNext()) {

						Map = it.next();
						//轉換Map Data
						Map = ems_ov_ctrl.mappingOVMapData(Map);
						//重產考勤
						ehf020800.Again_Produce_att(conn,(String)Map.get("EHF020801T0_04"),(String) Map.get("EHF020801T0_02"),authbean.getCompId(), USER_ID);
						//針對加班處理方式處理
						if("02".equals(Map.get("EHF020801T0_09"))){//換休->換補休
							//換休當年度可以使用到隔年３月底
							EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
							setVaSql.updataOverTimeVacation("0009", Map, authbean);
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			ems_ov_ctrl.close();
			// 更新資料庫
			conn.commit();
			
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
		
	}
	
	/**
	 * 自動核准到哪一站
	 * @param conn
	 * @param flow_no
	 * @param comp_id
	 * @return
	 */
	private int getAutoFlowSiteSN(Connection conn, String flow_no, String comp_id) {
		// TODO Auto-generated method stub
		
		int auto_flow_site_sn = 0;
		
		try{
			if("HC_OVERTIME_FLOW".equals(flow_no)){
				auto_flow_site_sn = 5;
			}else if("HC_M_OVERTIME_FLOW".equals(flow_no)){
				auto_flow_site_sn = 4;
			}else if("HC_M1_OVERTIME_FLOW".equals(flow_no)){
				auto_flow_site_sn = 3;
			}else if("HC_M2_OVERTIME_FLOW".equals(flow_no)){
				auto_flow_site_sn = 2;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		return auto_flow_site_sn;
	}
	
}