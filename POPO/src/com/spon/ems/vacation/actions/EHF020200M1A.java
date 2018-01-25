package com.spon.ems.vacation.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.abs.interfaces.ValidateForm;
import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.ems.vacation.forms.EHF020200M0F;
import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.ems.vacation.models.EHF020200;
import com.spon.ems.vacation.tools.EMS_CaculateVacationByCal;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
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
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action>請假單申請
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午10:32:34
 */
public class EHF020200M1A extends EditAction {
	
	private EMS_ACCESS ems_access;
	private EMS_FLOW ems_flow;
	
	public EHF020200M1A(){
		ems_access = EMS_ACCESS.getInstance();
		ems_flow = EMS_FLOW.getInstance();
	}

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020200M0F Form = (EHF020200M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF020200元件
			EHF020200 ehf020200 = new EHF020200(comp_id);
			
			String UID = tools.createEMSUID(conn, "EA", "EHF020200T0", "EHF020200T0_01", comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			
			dataMap = BeanUtils.describe(Form);
			dataMap.put("EHF020200T0_01", UID);
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
			//不是緊急流程就要寫入流程空表單編號
			if(!"Y".equals(emergency_flow)){
				dataMap.put("EHF020200T0_02", ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), 
						Form.getEHF020200T0_04(), comp_id));//Flow流程空表單編號
			}
			
			EMS_CaculateVacationByCal cacuva_tools = new EMS_CaculateVacationByCal();
			//取得員工目前班別資訊
			AuthorizedBean user_authbean = new AuthorizedBean();
			user_authbean.setEmployeeID(Form.getEHF020200T0_03());
			user_authbean.setCompId(getLoginUser(request).getCompId());
			user_authbean.setUserId(getLoginUser(request).getUserId());
			user_authbean.setUserCode(getLoginUser(request).getUserCode());
			Map result = cacuva_tools.caculateRealVa( conn, user_authbean, Form.getEHF020200T0_14(), Form.getEHF020200T0_09(), Form.getEHF020200T0_10(),
					 Form.getEHF020200T0_11_HH()+Form.getEHF020200T0_11_MM(), Form.getEHF020200T0_12_HH()+Form.getEHF020200T0_12_MM());
			dataMap.put("EHF020200T0_13", ((String)result.get("DAYS"))+"/"+((String)result.get("HOURS")));
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf020200.addData(dataMap);
			
			//關閉EHF020200元件
			ehf020200.close();
			
			//取出KEY_ID
			Form.setEHF020200T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020200T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//新增完成
			chk_flag = true;
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected Map executeQueryEditData(Connection conn, String[] key,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020200M0F Form = (EHF020200M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String Attached_File = "";
		String Attached_File_Name = "";
		//建立FLOW元件
		SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
		List flow_sign_log_list = new ArrayList();
		String flow_no = "";
		
		try{
			EHF020200 ehf020200 = new EHF020200();
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF020200T0_01", key[0]);  //職務系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf020200.queryEditData(queryCondMap);
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			Map depMap = hr_tools.getDepNameMap(comp_id);
			
			//關閉EHF020200元件
			ehf020200.close();
			hr_tools.close();

			if(!dataMap.isEmpty()){
				
				Form.setEHF020200T0_01((String)dataMap.get("EHF020200T0_01"));  //請假單單號
				Form.setEHF020200T0_03((String)dataMap.get("EHF020200T0_03"));  //申請人(員工工號)
				Form.setEHF020200T0_03_SHOW( (String) ((Map)empMap.get((String)dataMap.get("EHF020200T0_03"))).get("EMPLOYEE_ID") );
				Form.setEHF020200T0_03_TXT( (String) ((Map)empMap.get((String)dataMap.get("EHF020200T0_03"))).get("EMPLOYEE_NAME") );  //申請人姓名
				Form.setEHF020200T0_04((String)dataMap.get("EHF020200T0_04"));  //申請人部們組織(代號)
				Form.setEHF020200T0_04_SHOW( (String) ((Map)depMap.get((String)dataMap.get("EHF020200T0_04"))).get("SHOW_DEPT_ID") );
				Form.setEHF020200T0_04_TXT( (String) ((Map)depMap.get((String)dataMap.get("EHF020200T0_04"))).get("SHOW_DESC") );  //申請人部門名稱
				Form.setEHF020200T0_05((String)dataMap.get("EHF020200T0_05"));  //填單人(員工工號)
				Form.setEHF020200T0_05_SHOW( (String) ((Map)empMap.get((String)dataMap.get("EHF020200T0_05"))).get("EMPLOYEE_ID") );
				Form.setEHF020200T0_05_TXT( (String) ((Map)empMap.get((String)dataMap.get("EHF020200T0_05"))).get("EMPLOYEE_NAME") );  //填單人姓名
				Form.setEHF020200T0_06((String)dataMap.get("EHF020200T0_06"));  //填單人部門組織(代號)
				Form.setEHF020200T0_06_SHOW( (String) ((Map)depMap.get((String)dataMap.get("EHF020200T0_06"))).get("SHOW_DEPT_ID") );
				Form.setEHF020200T0_06_TXT( (String) ((Map)depMap.get((String)dataMap.get("EHF020200T0_06"))).get("SHOW_DESC") );  //填單人部門名稱
				if(!"".equals((String)dataMap.get("EHF020200T0_07"))){//有指定代理人
					Form.setEHF020200T0_07((String)dataMap.get("EHF020200T0_07"));  //代理人(員工工號)
					Form.setEHF020200T0_07_SHOW((String) ((Map)empMap.get((String)dataMap.get("EHF020200T0_07"))).get("EMPLOYEE_ID") );
					Form.setEHF020200T0_07_TXT( (String) ((Map)empMap.get((String)dataMap.get("EHF020200T0_07"))).get("EMPLOYEE_NAME") );//代理人姓名
				}else{//無指定代理人
					Form.setEHF020200T0_07("");  //代理人(員工工號)
					Form.setEHF020200T0_07_SHOW("");
					Form.setEHF020200T0_07_TXT("");//代理人姓名
				}
				Form.setEHF020200T0_08((String)dataMap.get("CREATE_DATE"));  //填單日期
				Form.setEHF020200T0_09((String)dataMap.get("START_DATE"));  //請假日期(起)
				Form.setEHF020200T0_10((String)dataMap.get("END_DATE"));    //請假日期(迄)
				Form.setEHF020200T0_11_HH(((String)dataMap.get("EHF020200T0_11")).substring(0, 2));  //請假時間 (時/分) (起)
				Form.setEHF020200T0_11_MM(((String)dataMap.get("EHF020200T0_11")).substring(2, 4));
				Form.setEHF020200T0_12_HH(((String)dataMap.get("EHF020200T0_12")).substring(0, 2));  //請假時間 (時/分) (迄)
				Form.setEHF020200T0_12_MM(((String)dataMap.get("EHF020200T0_12")).substring(2, 4));
				String[] dayhour = ((String)dataMap.get("EHF020200T0_13")).split("/");
				Form.setEHF020200T0_13_DAY(dayhour[0]);  //請假天數 -- 天
				Form.setEHF020200T0_13_HOUR(dayhour[1]);  //請假天數 -- 時
				Form.setEHF020200T0_14((String)dataMap.get("EHF020200T0_14"));  //假別(假別代號)
				Form.setEHF020200T0_14_DESC((String)dataMap.get("EHF020200T0_14_DESC"));  //假別名稱
				Form.setEHF020200T0_15((String)dataMap.get("EHF020200T0_15"));  //事由
				Form.setEHF020200T0_16((String)dataMap.get("EHF020200T0_16"));  //表單狀態
				Form.setEHF020200T0_16_DESC((String)dataMap.get("EHF020200T0_16_DESC"));  //表單狀態(中文名稱)
				if(!"".equals((String)dataMap.get("EHF020200T0_17")) && (String)dataMap.get("EHF020200T0_17") != null ){
					String[] filename = ((String)dataMap.get("EHF020200T0_17")).split("/");
					request.setAttribute("Attached_File_Name", filename[1]);
					request.setAttribute("Attached_File", "yes");
					Attached_File = "yes";
					Attached_File_Name = filename[1];
				}else {
					request.setAttribute("Attached_File", "no");
					Attached_File = "no";
					Attached_File_Name = "";
				}
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));
				Form.setVERSION((Integer)dataMap.get("VERSION"));
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				
				chk_flag = true;
				
			}
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", Form.getEHF020200T0_03(), "");
			//0001 草稿儲存，0002 呈核，0004 審核中，0005 駁回，0006 完成，0008 抽單，0009 作廢 
			if(!"".equals(Form.getEHF020200T0_16()) && !"0001".equals(Form.getEHF020200T0_16()) &&
					!"0005".equals(Form.getEHF020200T0_16()) && !"0008".equals(Form.getEHF020200T0_16())){
				paramMap.put(super.EMS_STRUTSLAYOUT_MODE, super.StrutsLayoutInspectMode);
			}
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
			if("Y".equals(emergency_flow)){
				request.setAttribute("emergency_flow", "Y");
			}else{
				flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
				
				if(!"".equals(Form.getEHF020200T0_16()) && !"0001".equals(Form.getEHF020200T0_16())){
					if(ems_flow.checkFlowNo(conn, Form.getEHF020200T0_01(), "EHF020200T0", flow_no, comp_id)){
						//判斷簽核流程身分
						ems_flow.checkFlowIdentity(conn, request, getLoginUser(request), flow_no, Form.getEHF020200T0_01(), "EHF020200T0", comp_id);
						//判斷是否為FLOW最後一站
						ems_flow.checkIsLastFlowStation(conn, request, getLoginUser(request), flow_no, Form.getEHF020200T0_01(), comp_id);
					}
				}
				
				flow_sign_log_list = spon_flow_tools.getFlowSignLogList(flow_no, Form.getEHF020200T0_01(), comp_id);
				
				request.setAttribute("emergency_flow", "N");
			}
			
			request.setAttribute("Form5Datas", flow_sign_log_list);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}

	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020200M0F Form = (EHF020200M0F) form;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String flow_no = "";
		
		try{			
			EHF020200 ehf020200 = new EHF020200();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			EMS_CaculateVacationByCal cacuva_tools = new EMS_CaculateVacationByCal();
			//取得員工目前班別資訊
			AuthorizedBean user_authbean = new AuthorizedBean();
			user_authbean.setEmployeeID(Form.getEHF020200T0_03());
			user_authbean.setCompId(getLoginUser(request).getCompId());
			user_authbean.setUserId(getLoginUser(request).getUserId());
			user_authbean.setUserCode(getLoginUser(request).getUserCode());
			Map result = cacuva_tools.caculateRealVa( conn, user_authbean, Form.getEHF020200T0_14(), Form.getEHF020200T0_09(), Form.getEHF020200T0_10(),
					 Form.getEHF020200T0_11_HH()+Form.getEHF020200T0_11_MM(), Form.getEHF020200T0_12_HH()+Form.getEHF020200T0_12_MM());
			dataMap.put("EHF020200T0_13", ((String)result.get("DAYS"))+"/"+((String)result.get("HOURS")));
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf020200.saveData(dataMap);
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
			
			if(!"Y".equals(emergency_flow)){
				//取Flow流程空表單編號
				flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
				//更新Flow流程空表單編號
				ehf020200.updateFlowNo(Form.getEHF020200T0_01(), flow_no, comp_id);
				//駁回、抽單時要更新簽核LOGS
				if(!"".equals(Form.getEHF020200T0_16()) && !"0001".equals(Form.getEHF020200T0_16())){
					ehf020200.updateFlowLogs(Form.getEHF020200T0_01(), flow_no, comp_id);
				}
			}
			
			//關閉EHF020200元件
			ehf020200.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020200T0_01());
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
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020200M0F Form = (EHF020200M0F) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020200T0_01());
			paramMap.put(super.KEY_ID, key_id);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}

	}

	@Override
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020200M0F Form = (EHF020200M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF020200T0_01());
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return key;
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020200M0F Form = (EHF020200M0F) form;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String employee_id = (String)paramMap.get(super.EMPLOYEE_ID);
		List flow_sign_log_list = new ArrayList();
		
		try{
			Form.setEHF020200T0_08(tools.ymdTostring(tools.getSysDate()));
			//設定請假區間初始值
			Form.setEHF020200T0_09(tools.ymdTostring(tools.getSysDate()));
			Form.setEHF020200T0_10(tools.ymdTostring(tools.getSysDate()));
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			Form.setEHF020200T0_03( (String) ((Map)empMap.get(employee_id)).get("USER_CODE") );  //申請人員工工號
			Form.setEHF020200T0_03_SHOW( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_ID") );
			Form.setEHF020200T0_03_TXT( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME") );  //申請人姓名
			Form.setEHF020200T0_04( (String) ((Map)empMap.get(employee_id)).get("DEPT_ID") );  //申請人部們組織代號
			Form.setEHF020200T0_04_SHOW( (String) ((Map)empMap.get(employee_id)).get("SHOW_DEPT_ID") );
			Form.setEHF020200T0_04_TXT( (String) ((Map)empMap.get(employee_id)).get("SHOW_DESC") );  //申請人部們名稱
			Form.setEHF020200T0_05( (String) ((Map)empMap.get(employee_id)).get("USER_CODE") );  //填單人員工工號
			Form.setEHF020200T0_05_SHOW( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_ID") );
			Form.setEHF020200T0_05_TXT( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME") );  //填單人姓名
			Form.setEHF020200T0_06( (String) ((Map)empMap.get(employee_id)).get("DEPT_ID") );  //填單人部們組織代號
			Form.setEHF020200T0_06_SHOW( (String) ((Map)empMap.get(employee_id)).get("SHOW_DEPT_ID") );
			Form.setEHF020200T0_06_TXT( (String) ((Map)empMap.get(employee_id)).get("SHOW_DESC") );  //填單人部們名稱
			
//			Form.setEHF020200T0_07((String) ((Map)empMap.get(employee_id)).get("USER_CODE"));  //代理人(員工工號)
//			Form.setEHF020200T0_07_SHOW((String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_ID") );
//			Form.setEHF020200T0_07_TXT( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME") );//代理人姓名
			
			//設定班別時間區間
			Map empClassMap = hr_tools.getEmpClass(conn, getLoginUser(request));
			if( empClassMap.size() > 0 ){
				Form.setEHF020200T0_11_HH(((String)empClassMap.get("WORK_START_TIME")).substring(0, 2));
				Form.setEHF020200T0_11_MM(((String)empClassMap.get("WORK_START_TIME")).substring(2, 4));
				Form.setEHF020200T0_12_HH(((String)empClassMap.get("WORK_END_TIME")).substring(0, 2));
				Form.setEHF020200T0_12_MM(((String)empClassMap.get("WORK_END_TIME")).substring(2, 4));
			}else{
				Form.setEHF020200T0_11_HH("08");
				Form.setEHF020200T0_11_MM("00");
				Form.setEHF020200T0_12_HH("17");
				Form.setEHF020200T0_12_MM("00");
			}
			Form.setEHF020200T0_16("0001");			
			
			Form.setEHF020200T0_13_DAY("0");
			Form.setEHF020200T0_13_HOUR("0");
			
			hr_tools.close();
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
			//是否啟用緊急流程
			String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
			if("Y".equals(emergency_flow)){
				request.setAttribute("emergency_flow", "Y");
			}else{
				request.setAttribute("emergency_flow", "N");
			}
			
			request.setAttribute("Form5Datas", flow_sign_log_list);
			
		}catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		return (ActionForm)Form;
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
		
		try{
			List listEHF020200T0_11_MM = new ArrayList();
			DecimalFormat df=new DecimalFormat("00");
			for (int i = 0; i <= 23; i++) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF020200T0_11_MM.add(bform);
			}
			request.setAttribute("listEHF020200T0_11_HH", listEHF020200T0_11_MM);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		try{
			List listEHF020200T0_11_MM = new ArrayList();
			DecimalFormat df=new DecimalFormat("00");
			for (int i = 0; i < 60; i=i+5) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF020200T0_11_MM.add(bform);
			}
			request.setAttribute("listEHF020200T0_11_MM", listEHF020200T0_11_MM);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生假別項目
		try {
			List datas = new ArrayList();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = " SELECT EHF020100T0_03 , EHF020100T0_04 FROM EHF020100T0 " +
					     " WHERE 1=1 " +
					     " AND EHF020100T0_08 = '"+getLoginUser(request).getCompId()+"' ";
			
			ResultSet rs=stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			datas.add(bform);
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("EHF020100T0_03"));
				bform.setItem_value(rs.getString("EHF020100T0_04"));
				datas.add(bform);	
				
			}
			
			rs.close();
			stmt.close();
			
			request.setAttribute("EHF020200T0_14_list", datas);
							
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 上傳附加檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadEHF020200M1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020200M0F Form = (EHF020200M0F) form;
		
		//檢查上傳檔案是否存在
		try{
			if("".equals(Form.getUPLOADFILE().getFileName()) || Form.getUPLOADFILE().getFileName() == null 
			   || Form.getUPLOADFILE().getFileSize() <= 0 ){
				
				request.setAttribute("MSG", "檔案上傳發生錯誤，請重新選取附加檔案!!");
				
				return queryForm(mapping, form, request, response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//方法二實體檔案上傳至系統實體路徑
			String sql = "" +
			 			 " SELECT * FROM EHF020200T0 " +
			 			 " WHERE 1=1 " +
			 			 " AND EHF020200T0_01 = '"+Form.getEHF020200T0_01()+"' " +
			 			 " AND EHF020200T0_18 = '"+getLoginUser(request).getCompId()+"' ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				String system_root = "";
				String temp = "";
				//判斷作業系統
				String osName = System.getProperty("os.name");
				if ("Linux".equals(osName)) {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_LINUX");
				} else {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_WINDOWS");
				}
				temp = system_root+"ems/uploadfiles/leave/";
				
				check_Path_Exist(temp , rs.getString("EHF020200T0_03"));
				
				temp += rs.getString("EHF020200T0_03")+"/"+Form.getUPLOADFILE().getFileName();
				
				String filepath = rs.getString("EHF020200T0_03")+"/"+Form.getUPLOADFILE().getFileName();
				
				File fout = new File(temp);
				
				try{
					InputStream in = Form.getUPLOADFILE().getInputStream();
					OutputStream out = new FileOutputStream(fout);
					int bit = 0;
					byte [] bits=new byte[4096];
					while ((bit=in.read(bits)) >-1 ) {
						out.write(bits,0,bit);
						Thread.sleep(1);
					}
					out.flush();
					out.close();
					in.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				sql = "" +
				" UPDATE ehf020200t0 SET EHF020200T0_17=?, USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE EHF020200T0_01 = '"+Form.getEHF020200T0_01()+"' " +
				" AND EHF020200T0_18 = '"+getLoginUser(request).getCompId()+"' ";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, filepath );  //附加檔案路徑
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName() );  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				
			}
			
			rs.close();
			stmt.close();
			
			request.setAttribute("MSG", "附加檔案上傳成功!!");
			
			
		}catch (Exception e) {
			request.setAttribute("MSG", "附加檔案上傳失敗!");
			System.out.println("FILEUPLOAD.uploadEHF020200M1() " + e);
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
	 * 下載請假單附加檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAttachedFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;		
		EHF020200M0F Form = (EHF020200M0F) form;

		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{			
			String sql = "" +
			" SELECT EHF020200T0.* , DATE_FORMAT(EHF020200T0_08, '%Y/%m/%d') AS CREATE_DATE " +
			" ,DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS START_DATE , DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') AS END_DATE " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+Form.getEHF020200T0_01()+"' " +
			" AND EHF020200T0_18 = '"+getLoginUser(request).getCompId()+"' ";	
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				String[] filename = rs.getString("EHF020200T0_17").split("/");				
//				String filepath = request.getSession().getServletContext().getRealPath("/")+"ems/uploadfiles/leave/";
				String system_root = "";
//				String temp = "";
				//判斷作業系統
				String osName = System.getProperty("os.name");
				if ("Linux".equals(osName)) {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_LINUX");
				} else {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_WINDOWS");
				}
				String filepath = system_root+"ems/uploadfiles/leave/";
				filepath += rs.getString("EHF020200T0_17");				
				java.net.URLEncoder.encode(filepath, "UTF-8");				
				File fout = new File(filepath);				
				//開啟檔案下載視窗
				response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename[1], "UTF-8"));
				ServletOutputStream ouputStream;
				try {
					InputStream in = new FileInputStream(fout);
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
					request.setAttribute("MSG", "檔案下載失敗!!");
					e.printStackTrace();
				}
				
				return null;
			}
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			request.setAttribute("MSG", "檔案下載失敗!!");
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
	
	/**
	 * 刪除請假單附加檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delAttachedFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EHF020200M0F Form = (EHF020200M0F) form;

		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			
			String sql = "" +
			" SELECT EHF020200T0.* , DATE_FORMAT(EHF020200T0_08, '%Y/%m/%d') AS CREATE_DATE " +
			" ,DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS START_DATE , DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') AS END_DATE " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+Form.getEHF020200T0_01()+"' " +
			" AND EHF020200T0_18 = '"+getLoginUser(request).getCompId()+"' ";	
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){				
//				String filepath = request.getSession().getServletContext().getRealPath("/")+"ems/uploadfiles/leave/";
				String system_root = "";
				//判斷作業系統
				String osName = System.getProperty("os.name");
				if ("Linux".equals(osName)) {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_LINUX");
				} else {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_WINDOWS");
				}
				String filepath = system_root+"ems/uploadfiles/leave/";				
				filepath += rs.getString("EHF020200T0_17");				
				java.net.URLEncoder.encode(filepath, "UTF-8");				
				File fout = new File(filepath);				
				if(fout.exists()){				
					//刪除附加檔案
					fout.delete();					
					String upsql = "" +
					" UPDATE EHF020200T0 " +
					" SET EHF020200T0_17 = '' " +
					" WHERE 1=1 " +
					" AND EHF020200T0_01 = '"+Form.getEHF020200T0_01()+"' " +
					" AND EHF020200T0_18 = '"+getLoginUser(request).getCompId()+"' ";
					
					stmt.executeUpdate(upsql);					
					conn.commit();					
					request.setAttribute("MSG", "附加檔案刪除成功!!");
				}else{
					request.setAttribute("MSG", "附加檔案不存在!!");
				}
			}			
			rs.close();
			stmt.close();		
		}catch(Exception e){
			request.setAttribute("MSG", "附加檔案刪除失敗!!");
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
	
	/**
	 * 請假單呈核動作 -- 緩衝區
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
	 * 請假單 -- 呈核動作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward submitDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EHF020200M0F Form = (EHF020200M0F) form;
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
			ActionErrors lc_errors = new ActionErrors();
			
			//判斷呈核時是否檢核假別資訊 edit by joe 2012/05/16
			if("ON".equals(tools.getSysParam(conn, comp_id, "VACATION_CHECK_BY_VA_TYPE"))){
				EMS_CheckVacation checkva_tools = (EMS_CheckVacation) EMS_CheckVacation.getInstance();
				//請假單呈核進行檢核
				checkva_tools.checkVacation(lc_errors, request, conn, Form.getEHF020200T0_01(), comp_id);
			}
			
			try {				
				String sql = "" +
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
				" FROM EHF020900T0 " +
				" WHERE 1=1 " +
				" AND EHF020900T0_M2 BETWEEN DATE_FORMAT('"+Form.getEHF020200T0_09()+"', '%Y/%m') AND DATE_FORMAT('"+Form.getEHF020200T0_10()+"', '%Y/%m')"+	//考勤年月
				" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
				" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

		    	Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					lc_errors.add("EHF020200T0_09",new ActionMessage(""));
					lc_errors.add("EHF020200T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"考勤已確認，不可呈核該筆請假單!!請再次確認!!");
					
				}
				rs.close();
				stmt.close();
				
			} catch (Exception e) {
				
			}
			
			if (lc_errors.isEmpty()){
				
				//是否啟用緊急流程
				String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
				
				if("Y".equals(emergency_flow)){
				
					EHF020200 ehf020200 = new EHF020200();
				
					//建立查詢資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF020200T0_01", Form.getEHF020200T0_01());  // 
				
					if(ehf020200.submitDatas(queryCondMap, comp_id, user_name)){
						//EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						//setVaSql.updataVacationData(queryCondMap, comp_id);
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核失敗!!");
					}
				
					ehf020200.close();
				
				}else{
					//建立FLOW元件
					SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
					
					//取得當前使用者資訊
					SC003F userform = super.userform(request);
					
					HR_TOOLS hr_tools = new HR_TOOLS();
					
					Map empMap = hr_tools.getEmpNameMap(comp_id);
					
					hr_tools.close();
					
					flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
					
					//組成 condMap
					Map condMap = new HashMap();
					condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
					
					//執行FLOW submit
					spon_flow_tools.submit(flow_no, 
										   Form.getEHF020200T0_01(), 
										   condMap,  //條件資料Map
										   userform.getSC0030_01(), 
										   userform.getSC0030_14());
					
					//取得當前待簽核LOG
					Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020200T0_01(), comp_id);
					
					this.updateFormStatus(spon_flow_tools, request, Form.getEHF020200T0_01(), current_sign_map, flow_no, "submit");
					
				}
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核失敗!!");
				request.setAttribute("ERR_MSG", (request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>"));
				request.setAttribute("Form1Datas", Form);

				
				//建立FLOW元件
				SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
				List flow_sign_log_list = new ArrayList();
				//是否啟用緊急流程
				String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
				if("Y".equals(emergency_flow)){
					request.setAttribute("emergency_flow", "Y");
				}else{
					flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
					
					if(!"".equals(Form.getEHF020200T0_16()) && !"0001".equals(Form.getEHF020200T0_16())){
						if(ems_flow.checkFlowNo(conn, Form.getEHF020200T0_01(), "EHF020200T0", flow_no, comp_id)){
							//判斷簽核流程身分
							ems_flow.checkFlowIdentity(conn, request, getLoginUser(request), flow_no, Form.getEHF020200T0_01(), "EHF020200T0", comp_id);
							//判斷是否為FLOW最後一站
							ems_flow.checkIsLastFlowStation(conn, request, getLoginUser(request), flow_no, Form.getEHF020200T0_01(), comp_id);
						}
					}
					
					flow_sign_log_list = spon_flow_tools.getFlowSignLogList(flow_no, Form.getEHF020200T0_01(), comp_id);
					
					request.setAttribute("emergency_flow", "N");
				}
				
				request.setAttribute("Form5Datas", flow_sign_log_list);
				
				this.generateSelectBox(conn, Form, request);
				return mapping.findForward("success");
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
		
		EHF020200M0F Form = (EHF020200M0F) form;
		String fromKey = Form.getEHF020200T0_01()+"";
		String agent_sign_table = "EHF020200T0";	//簽核table
		String agent_sign_form_status = "EHF020200T0_16";	//簽核表單狀態
		String agent_sign_form_status_desc = "EHF020200T0_16_DESC";	//簽核表單狀態說明
		String agent_sign_form_status_code = ""; //flow_action
		String agent_sign_form_status_code_desc = ""; //flow_action說明
		String agent_sign_form_recieptNo = "EHF020200T0_01";	//簽核表單單號
		String agent_sign_compId = getLoginUser(request).getCompId();	//簽核公司代碼
		String compId = "EHF020200T0_18";
		
		Connection conn = null;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			if("0001".equals(flow_action)){	//呈核
				agent_sign_form_status_code = "0002";
				agent_sign_form_status_code_desc = "呈核";
			}else if("0002".equals(flow_action)){	//核准
				agent_sign_form_status_code = "0006";
				agent_sign_form_status_code_desc = "完成";	//因為人資核准就算完成了，所以狀態直接設定為完成
			}else if("0003".equals(flow_action)){	//駁回
				agent_sign_form_status_code = "0005";
				agent_sign_form_status_code_desc = "駁回";
			}else if("0013".equals(flow_action)){	//抽單
				agent_sign_form_status_code = "0008";
				agent_sign_form_status_code_desc = "抽單";
			}else if("0009".equals(flow_action)){	//作廢
				agent_sign_form_status_code = "0009";
				agent_sign_form_status_code_desc = "作廢";
			}
			
			String sql = " UPDATE " + agent_sign_table +
			" SET " + agent_sign_form_status + "='" + agent_sign_form_status_code + "'," +
			" " + agent_sign_form_status_desc + "='" + agent_sign_form_status_code_desc + "' " +
			" WHERE " + agent_sign_form_recieptNo + "='" + fromKey + "' " +
			" AND " + compId + "='" + agent_sign_compId + "'";
	
			base_tools.executeSQL(sql);
	
			base_tools.commit();
			
			//若是執行完成的動作，要回寫請假時數到年度休假資料中
			if("0006".equals(agent_sign_form_status_code)){
				
				//建立查詢資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020200T0_01", Form.getEHF020200T0_01());  //表單編號
				
				EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
				setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());
			}else if("0009".equals(agent_sign_form_status_code)){
				
				//建立查詢資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020200T0_01", Form.getEHF020200T0_01());  //表單編號
				
				//執行作廢後，要還原剩餘時數, 如果表單是完成後作廢才需要還原
				EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
				
				setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());
				
				//還原考勤異常資料
				EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
				AuthorizedBean authbean = getLoginUser(request);
				EHF020401M0F ehf020401m0=new EHF020401M0F();
				
				ehf020401m0.setEHF020403T0_02(Form.getEHF020200T0_04());//部門代號
				ehf020401m0.setEHF020403T0_01(Form.getEHF020200T0_03());//員工工號
				List data=fix_att_tools.getAttAbnormalList(conn, ehf020401m0, authbean, false, false, Form.getEHF020200T0_01(), "");
				
				for(int i=0;i<data.size();i++){
					
					EHF020401M0F Form_data = (EHF020401M0F) data.get(i);
					fix_att_tools.recoveryFixAttAbnormal(conn, Form_data, authbean.getUserName(), authbean.getUserId());
					conn.commit();
				}
				
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
				
				case 4:
					return approve(mapping, form, request, response);
				
				case 5:
					return reject(mapping, form, request, response);
					
				case 8:
					return cancel(mapping, form, request, response);
			
				case 9:
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
		
		Connection conn = null;
		EHF020200M0F Form = (EHF020200M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		ActionErrors lc_errors = new ActionErrors();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			
			//處理檢核程式
			request.setAttribute("action", "approve");
			ValidateForm validate_form = (ValidateForm) form;
			lc_errors = validate_form.validate(mapping, request, conn);
			
		}catch(Exception e){
			//記錄錯誤訊息
//			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		try{
			
			if(lc_errors.isEmpty()){
				
				//建立FLOW元件
				SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
				
				//取得當前使用者資訊
				SC003F userform = super.userform(request);
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				Map empMap = hr_tools.getEmpNameMap(comp_id);
				
				hr_tools.close();
								
				flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
					
				//取得當前待簽核LOG
				Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020200T0_01(), comp_id);
					
				this.updateFormStatus(spon_flow_tools, request, Form.getEHF020200T0_01(), current_sign_map, flow_no, "approve");
					
				//組成 condMap                                                                                                                                                                                                                                                                                                    
				Map condMap = new HashMap();
					
				//執行FLOW approve
				spon_flow_tools.approve(flow_no, 
										Form.getEHF020200T0_01(), 
										condMap,  //條件資料Map 
										Form.getSIGN_COMMENT(),  //簽核意見
									    userform.getSC0030_01(), 
									    userform.getSC0030_14());
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", "核准失敗!!"+(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>"));
				request.setAttribute("Form1Datas", Form);
				this.generateSelectBox(conn, Form, request);
				return mapping.findForward("success");
			}
			
			
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
		EHF020200M0F Form = (EHF020200M0F) form;
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
			
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
			
			//組成 condMap
			Map condMap = new HashMap();
			//condMap.put("SUPPLY_USER_ID", Form.getDATA02());
			
			//執行FLOW reject
			spon_flow_tools.reject(flow_no, 
								   Form.getEHF020200T0_01(), 
								   condMap,  //條件資料Map
								   Form.getSIGN_COMMENT(),  //簽核意見
								   userform.getSC0030_01(), 
								   userform.getSC0030_14());
			
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020200T0_01(), comp_id);
			
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF020200T0_01(), current_sign_map, flow_no, "reject");
			
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
		EHF020200M0F Form = (EHF020200M0F) form;
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
			
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
			
			//執行FLOW reject
			spon_flow_tools.cancel(flow_no, 
								   Form.getEHF020200T0_01(), 
								   userform.getSC0030_01(),  //抽單人
								   Form.getSIGN_COMMENT(),  //簽核意見
								   userform.getSC0030_01(), 
								   userform.getSC0030_14());
			
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020200T0_01(), comp_id);
			
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF020200T0_01(), current_sign_map, flow_no, "cancel");
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"抽單失敗!!");
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
		EHF020200M0F Form = (EHF020200M0F) form;
		
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			EHF020200 ehf020200 = new EHF020200();
						
			boolean flow_flag = ehf020200.chkFlowNo(Form.getEHF020200T0_01(), comp_id);
			
			ehf020200.close();
			
			if(flow_flag){
				
				//建立FLOW元件
				SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
				
				//取得當前使用者資訊
				SC003F userform = super.userform(request);
				
				flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
				
				//組成 condMap
				Map condMap = new HashMap();
				condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
				
				//執行FLOW invalid
				spon_flow_tools.invalid(flow_no, 
						   			   	Form.getEHF020200T0_01(), 
						   			   	userform.getSC0030_01(),  //抽單人
						   			   	Form.getSIGN_COMMENT(),  //簽核意見
						   			   	userform.getSC0030_01(), 
						   			   	userform.getSC0030_14());
				
				//取得當前待簽核LOG
				Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020200T0_01(), comp_id);
				
				this.updateFormStatus(spon_flow_tools, request, Form.getEHF020200T0_01(), current_sign_map, flow_no, "invalid");
				
				//還原考勤異常資料
				EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
				AuthorizedBean authbean = getLoginUser(request);
				EHF020401M0F ehf020401m0=new EHF020401M0F();
				
				ehf020401m0.setEHF020403T0_02(Form.getEHF020200T0_04());//部門代號
				ehf020401m0.setEHF020403T0_01(Form.getEHF020200T0_03());//員工工號
				List data=fix_att_tools.getAttAbnormalList(conn, ehf020401m0, authbean, false, false, Form.getEHF020200T0_01(), "");
				
				for(int i=0;i<data.size();i++){
					
					EHF020401M0F Form_data = (EHF020401M0F) data.get(i);
					fix_att_tools.recoveryFixAttAbnormal(conn, Form_data, authbean.getUserName(), authbean.getUserId());
					conn.commit();
				}
				
			}else{
				
				this.signDatasEmergency(mapping, form, request, response, "0009");
				
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
		EHF020200M0F Form = (EHF020200M0F) form;
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
							
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF020200M0", Form.getEHF020200T0_03(), Form.getEHF020200T0_04(), comp_id);
			
			auto_flow_site_sn = this.getAutoFlowSiteSN(conn, flow_no, comp_id);						
				
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020200T0_01(), comp_id);
			
			if("填寫中".equals((String)current_sign_map.get("CURRENT_SIGN_FORM_STATUS_NAME"))){
				//尚未呈核過，申請人是主管流程角色
				Map condMap = new HashMap();
				condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
				
				//執行FLOW submit
				spon_flow_tools.submit(flow_no, 
									   Form.getEHF020200T0_01(), 
									   condMap,  //條件資料Map
									   userform.getSC0030_01(), 
									   userform.getSC0030_14());
				
				//取得當前待簽核LOG
				Map auto_current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020200T0_01(), comp_id);
				
				this.updateFormStatus(spon_flow_tools, request, Form.getEHF020200T0_01(), auto_current_sign_map, flow_no, "submit");
			}
			
			Map auto_current_approve_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF020200T0_01(), comp_id);
				
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF020200T0_01(), auto_current_approve_map, flow_no, "approve");
				
			//組成 condMap                                                                                                                                                                                                                                                                                                    
			Map condMap = new HashMap();
				
			//執行FLOW approve
			spon_flow_tools.AutoApprove(flow_no, 
										Form.getEHF020200T0_01(), 
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
	 * @param action
	 * @param action 
	 */
	private void updateFormStatus(SPON_ParticularFlowForPWSUP_Tools spon_flow_tools, HttpServletRequest request,
			String form_no, Map current_sign_map, String flow_no, String action) {
		// TODO Auto-generated method stub
		
		String comp_id = getLoginUser(request).getCompId();
		String user_name = getLoginUser(request).getUserName();
		
		try{
			if("submit".equals(action)){
				
				EHF020200 ehf020200 = new EHF020200();
				
				//建立查詢資料Map
				Map<String, String> queryCondMap = new HashMap();					
				queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
				queryCondMap.put("EHF020200T0_16", (String)current_sign_map.get("BEFORE_SIGN_FORM_STATUS_NO"));	//更改前表單狀態代碼
				queryCondMap.put("EHF020200T0_16_DESC", (String)current_sign_map.get("BEFORE_SIGN_FORM_STATUS_NAME")); //更改前表單狀態名稱
				
				if(ehf020200.submitDatas(queryCondMap, comp_id, user_name)){
					//不是緊急流程就要寫入流程空表單編號
					ehf020200.updateFlowNo(form_no, flow_no, comp_id);
					
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核失敗!!");
				}
				
				ehf020200.close();
				
			}else if("approve".equals(action)){
				
				boolean next_flow_station_flag = spon_flow_tools.isNextFlowStation(current_sign_map, comp_id);
				
				EHF020200 ehf020200 = new EHF020200();
				
				if(next_flow_station_flag){
					//有下一站
					//建立資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
					queryCondMap.put("EHF020200T0_16", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
					queryCondMap.put("EHF020200T0_16_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
					
					if(ehf020200.submitFlowDatas(queryCondMap, comp_id, user_name)){
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准失敗!!");
					}
					
				}else{
					//無下一站
					//建立資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
					queryCondMap.put("EHF020200T0_16", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
					queryCondMap.put("EHF020200T0_16_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
					
					if(ehf020200.submitFlowDatas(queryCondMap, comp_id, user_name)){						
						//若是執行完成的動作，要回寫請假時數到年度休假資料中												
						EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());						
						
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成失敗!!");
					}
					
				}
				
				ehf020200.close();
				
			}else if("reject".equals(action)){
				
				EHF020200 ehf020200 = new EHF020200();
				
				//建立資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
				queryCondMap.put("EHF020200T0_16", "0005");	//FLOW狀態號碼
				queryCondMap.put("EHF020200T0_16_DESC", "駁回"); //FLOW狀態名稱
				
				if(ehf020200.submitFlowDatas(queryCondMap, comp_id, user_name)){
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
				}
				
				ehf020200.close();
				
			}else if("cancel".equals(action)){
				
				EHF020200 ehf020200 = new EHF020200();
				
				//建立資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
				queryCondMap.put("EHF020200T0_16", "0008");	//FLOW狀態號碼
				queryCondMap.put("EHF020200T0_16_DESC", "抽單"); //FLOW狀態名稱
				
				if(ehf020200.submitFlowDatas(queryCondMap, comp_id, user_name)){
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"抽單成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"抽單失敗!!");
				}
				
				ehf020200.close();
				
			}else if("invalid".equals(action)){
				
				EHF020200 ehf020200 = new EHF020200();
				
				//建立資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF020200T0_01", form_no);  //表單編號
				queryCondMap.put("EHF020200T0_16", "0009");	//FLOW狀態號碼
				queryCondMap.put("EHF020200T0_16_DESC", "作廢"); //FLOW狀態名稱
				queryCondMap.put("COMP_ID", comp_id);
				
				if(ehf020200.submitFlowDatas(queryCondMap, comp_id, user_name)){
					
					//執行作廢後，要還原剩餘時數, 如果表單是完成後作廢才需要還原
					EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
					
					setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());
					
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"作廢成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"作廢失敗!!");
				}
				
				ehf020200.close();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
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
			if("HC_VACATION_FLOW".equals(flow_no)){
				auto_flow_site_sn = 5;
			}else if("HC_M_VACATION_FLOW".equals(flow_no)){
				auto_flow_site_sn = 4;
			}else if("HC_M1_VACATION_FLOW".equals(flow_no)){
				auto_flow_site_sn = 3;
			}else if("HC_M2_VACATION_FLOW".equals(flow_no)){
				auto_flow_site_sn = 2;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		return auto_flow_site_sn;
	}

}
