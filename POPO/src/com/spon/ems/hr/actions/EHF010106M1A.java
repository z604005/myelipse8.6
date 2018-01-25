package com.spon.ems.hr.actions;

import java.awt.image.BufferedImage;
import java.io.File;
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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.ems.hr.forms.EHF010106M0F;
import com.spon.ems.hr.models.EHF010106;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class EHF010106M1A extends EditAction {
	
	private BA_TOOLS tool;
	//private BA_EMS_TOOLS ems_tool;
	public EHF010106M1A()
	{
		tool = BA_TOOLS.getInstance();
		//ems_tool = BA_EMS_TOOLS.getInstance();
	}

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		
		EHF010106M0F Form = (EHF010106M0F) form;
		
		Form.setEHF010106T5_02("");
		Form.setEHF010106T5_03(0);
		Form.setEHF010106T5_04("");
		Form.setEHF010106T5_05("");
		
		Form.setEHF010106T3_02("1");
		Form.setEHF010106T3_03("");
		Form.setEHF010106T3_04("");
		Form.setEHF010106T3_05("");
		Form.setEHF010106T3_06("");
		Form.setEHF010106T3_07("");
		
		Form.setEHF010106T2_02("");
		Form.setEHF010106T2_03("");
		Form.setEHF010106T2_04("");
		Form.setEHF010106T2_05("");
		Form.setEHF010106T2_06("");
		Form.setEHF010106T2_07("");
		Form.setEHF010106T2_08("");
		Form.setEHF010106T2_09("");
		
		Form.setEHF010106T4_02("");
		Form.setEHF010106T4_03("");
		Form.setEHF010106T4_04("");
		Form.setEHF010106T4_05("");
		
		Form.setEHF020403T1_02("");
		Form.setEHF020403T1_04_START("");
		Form.setEHF020403T1_04_START_HH("00");
		Form.setEHF020403T1_04_START_MM("00");
		Form.setEHF020403T1_04_END("");
		Form.setEHF020403T1_04_END_HH("00");
		Form.setEHF020403T1_04_END_MM("00");
		Form.setEHF020403T1_05("");
		
		Form.setEHF030200T1_02("");
		Form.setEHF030200T1_02_TXT("");
		Form.setEHF030200T1_02_MONEY("");
		Form.setEHF030200T1_03("");
		Form.setEHF030200T1_02_01("");
		Form.setEHF030200T1_02_TXT_01("");
		Form.setEHF030200T1_04_01("");
		Form.setEHF030200T1_04_01_TXT("");
		Form.setEHF030200T1_02_MONEY_01("");
		Form.setEHF030200T1_03_01("");
		
		Form.setEHF030300T1_02("");
		Form.setEHF030300T1_03("");
		Form.setEHF030300T1_04("");
		Form.setEHF030300T1_05("");
		Form.setEHF030300T1_06("");

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF010106M0F Form = (EHF010106M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010106元件
			EHF010106 ehf010106 = new EHF010106(comp_id);
			
			//String HR_EmployeeSysNo = EMS_GetCodeRules.getInstance().getCodeRule("", comp_id);
			//String HR_EmployeeSysNo = "EMP0001COM28727369";
			String HR_EmployeeSysNo = tools.createEMSUID(conn, "EMP", "EHF010106T0", "HR_EmployeeSysNo", comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap.put("HR_EmployeeSysNo", new String(HR_EmployeeSysNo));	//員工系統代碼
			dataMap.put("HR_EmployeeNo", Form.getHR_EmployeeNo());	//員工工號
			dataMap.put("EHF010106T0_01", Form.getEHF010106T0_01()==null?"":Form.getEHF010106T0_01());	//
			dataMap.put("EHF010106T0_02", Form.getEHF010106T0_02()==null?"":Form.getEHF010106T0_02());	//
			dataMap.put("EHF010106T0_03", Form.getEHF010106T0_03()==null?"":Form.getEHF010106T0_03());	//
			dataMap.put("EHF010106T0_04", Form.getEHF010106T0_04());	//姓名(中)
			dataMap.put("EHF010106T0_05", Form.getEHF010106T0_05()==null?"":Form.getEHF010106T0_05());	//
			dataMap.put("EHF010106T0_06", Form.getEHF010106T0_06()==null?"1":Form.getEHF010106T0_06());	//
			dataMap.put("EHF010106T0_07", Form.getEHF010106T0_07()==null?"":Form.getEHF010106T0_07());	//
			dataMap.put("EHF010106T0_08", Form.getEHF010106T0_08()==null?"":Form.getEHF010106T0_08());	//
			dataMap.put("EHF010106T0_09", Form.getEHF010106T0_09()==null?"":Form.getEHF010106T0_09());	//
			dataMap.put("EHF010106T0_10", "".equals(Form.getEHF010106T0_10())?null:Form.getEHF010106T0_10());	//
			if("".equals(Form.getEHF010106T0_1101())){
				dataMap.put("EHF010106T0_11", Form.getEHF010106T0_1102()==null?"":Form.getEHF010106T0_1102());	//
			}else{
				dataMap.put("EHF010106T0_11", Form.getEHF010106T0_1101()+"-"+Form.getEHF010106T0_1102());	//
			}			
			dataMap.put("EHF010106T0_12", Form.getEHF010106T0_12());	//
			if("".equals(Form.getEHF010106T0_1301())){
				dataMap.put("EHF010106T0_13", Form.getEHF010106T0_1302()==null?"":Form.getEHF010106T0_1302());	//
			}else{
				dataMap.put("EHF010106T0_13", Form.getEHF010106T0_1301()+"-"+Form.getEHF010106T0_1302());	//
			}			
			dataMap.put("EHF010106T0_14", Form.getEHF010106T0_14()==null?"":Form.getEHF010106T0_14());	//
			dataMap.put("EHF010106T0_15", Form.getEHF010106T0_15()==null?"":Form.getEHF010106T0_15());	//
			dataMap.put("EHF010106T0_16", Form.getEHF010106T0_16()==null?"":Form.getEHF010106T0_16());	//
			dataMap.put("EHF010106T0_17", Form.getEHF010106T0_17()==null?"":Form.getEHF010106T0_17());	//
			if("".equals(Form.getEHF010106T0_1801())){
				dataMap.put("EHF010106T0_18", Form.getEHF010106T0_1802()==null?"":Form.getEHF010106T0_1802());	//
			}else{
				dataMap.put("EHF010106T0_18", Form.getEHF010106T0_1801()+"-"+Form.getEHF010106T0_1802());	//	
			}						
			dataMap.put("EHF010106T0_19", Form.getEHF010106T0_19()==null?"":Form.getEHF010106T0_19());	//
			dataMap.put("EHF010106T0_20", tool.StringToBoolean("0"));	//
			dataMap.put("EHF010106T0_21", tool.StringToBoolean("0"));	//
			dataMap.put("EHF010106T0_22", Form.getEHF010106T0_22()==null?"":Form.getEHF010106T0_22());	//
			dataMap.put("EHF010106T0_23", Form.getEHF010106T0_23()==null?"":Form.getEHF010106T0_23());	//
			dataMap.put("EHF010106T0_24", Form.getEHF010106T0_24()==null?"":Form.getEHF010106T0_24());	//
			dataMap.put("EHF010106T0_25", Form.getEHF010106T0_25()==null?"":Form.getEHF010106T0_25());	//
			dataMap.put("HR_JobDate", "");	//
			dataMap.put("HR_OffJobDate", "");	//
			dataMap.put("HR_Seniority", "");	//
			dataMap.put("HR_SeniorityAdjust", "");	//
			
			dataMap.put("EHF010106T1_01", Form.getEHF010106T1_01()==null?"1":Form.getEHF010106T1_01());	//
			if("1".equals(Form.getEHF010106T1_01())){
				dataMap.put("EHF010106T1_02", Form.getEHF010106T1_0201()==null?"":Form.getEHF010106T1_0201());	//
			}else if("2".equals(Form.getEHF010106T1_01())){
				dataMap.put("EHF010106T1_02", Form.getEHF010106T1_0202()==null?"":Form.getEHF010106T1_0202());	//
			}else if("3".equals(Form.getEHF010106T1_01())){
				dataMap.put("EHF010106T1_02", Form.getEHF010106T1_0203()==null?"":Form.getEHF010106T1_0203());	//
			}else if("4".equals(Form.getEHF010106T1_01())){
				dataMap.put("EHF010106T1_02", Form.getEHF010106T1_0204()==null?"":Form.getEHF010106T1_0204());	//
			}else{
				dataMap.put("EHF010106T1_02", tool.ymdTostring(tool.getSysDate()));	//
			}			
			dataMap.put("EHF010106T1_03", tool.StringToBoolean("1"));	//
			
			dataMap.put("HR_DepartmentSysNo", Form.getHR_DepartmentSysNo()==null?"":Form.getHR_DepartmentSysNo());
			dataMap.put("EHF010106T6_01", "".equals(Form.getEHF010106T6_01())?null:Form.getEHF010106T6_01());	//
			dataMap.put("EHF010106T6_02", "".equals(Form.getEHF010106T6_02())?null:Form.getEHF010106T6_02());	//
			dataMap.put("EHF010106T6_03", tool.StringToBoolean("1"));	//
			dataMap.put("HR_JobNameSysNo", Form.getHR_JobNameSysNo()==null?"":Form.getHR_JobNameSysNo());
			dataMap.put("HR_IsManager", "0");
			dataMap.put("HR_ManagerLevel", "");
			dataMap.put("HR_DesignateManager", "");
			dataMap.put("HR_JobAgent1", "");
			dataMap.put("HR_JobAgent2", "");
			dataMap.put("HR_JobAgent3", "");
			dataMap.put("HR_CheckLeave", "0");
			dataMap.put("HR_LeaveStartTime", "");
			dataMap.put("HR_LeaveEndTime", "");
			
			dataMap.put("EHF020403T0_05", Form.getEHF020403T0_05()==null?"1":Form.getEHF020403T0_05());	//
			dataMap.put("EHF010100T1_04_KEY", Integer.valueOf( "".equals(Form.getEHF010100T1_04_KEY())?"0":Form.getEHF010100T1_04_KEY() ));	//
			
			dataMap.put("EHF030200T0_07", Form.getEHF030200T0_07());	//
			dataMap.put("EHF030200T0_05", Form.getEHF030200T0_05());
			dataMap.put("EHF030200T0_06", Form.getEHF030200T0_06());
			dataMap.put("EHF030200T0_06_AC", Form.getEHF030200T0_06_AC());
			dataMap.put("EHF030200T0_04", Form.getEHF030200T0_04());
			dataMap.put("EHF030200T0_19", Form.getEHF030200T0_19());
			dataMap.put("EHF030200T0_18", Form.getEHF030200T0_18());
			dataMap.put("EHF030200T0_20", Form.getEHF030200T0_20());
			dataMap.put("EHF030200T0_21", Form.getEHF030200T0_21()==null?"1":Form.getEHF030200T0_21());
			dataMap.put("EHF030200T0_08", Form.getEHF030200T0_08()==null?"1":Form.getEHF030200T0_08());
			dataMap.put("EHF030200T0_12", Form.getEHF030200T0_12());
			
			dataMap.put("EHF030300T0_05", Form.getEHF030300T0_05());
			dataMap.put("EHF030300T0_05_NUMBER", Form.getEHF030300T0_05_NUMBER());
			dataMap.put("EHF030300T0_07", Form.getEHF030300T0_07());
			dataMap.put("EHF030300T0_07_NUMBER", Form.getEHF030300T0_07_NUMBER());
			dataMap.put("EHF030300T0_09", Form.getEHF030300T0_09());
			dataMap.put("EHF030300T0_10", Form.getEHF030300T0_10());
			dataMap.put("EHF030300T0_11", Form.getEHF030300T0_11()==0?1:Form.getEHF030200T0_08());	//口數
			dataMap.put("EHF030300T0_15", Form.getEHF030300T0_15());
			dataMap.put("EHF030300T0_05_START", tool.ymdTostring(tool.getSysDateYYMMDD()));
			dataMap.put("EHF030300T0_05_END", "0000-00-00");
			dataMap.put("EHF030300T0_07_START", tool.ymdTostring(tool.getSysDateYYMMDD()));
			dataMap.put("EHF030300T0_07_END", "0000-00-00");
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			//新增員工
			ehf010106.addData(dataMap);
			//新增職務現況明細
			ehf010106.addDataDuty(dataMap);
			//新增狀況明細
			ehf010106.addDataJobLife(dataMap);
			//新增感應卡
			ehf010106.addDataCard(dataMap);
			//新增班別
			ehf010106.addDataClass(dataMap);
			//新增薪資基本資料
			ehf010106.addDataSalary(dataMap);
			//新增保險基本資料
			ehf010106.addDataInsurance(dataMap);
			
			//關閉EHF010106元件
			ehf010106.close();
			
			//取出KEY_ID
			Form.setHR_EmployeeSysNo((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getHR_EmployeeSysNo());
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
	public boolean executeAddDetailData(Connection conn, String type,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF010106M0F Form = (EHF010106M0F) form;
		boolean chk_flag = false;
		
		try{
			//建立EHF010106元件
			EHF010106 ehf010106 = new EHF010106();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//set tabsutil
			//Form.setTabsutil("yes");
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			if("EHF020403T1".equals(type)){
				
				ehf010106.addDetailData(type,detailDataMap);
				
				//若材料明細資料有改變則要讓Tab停留在材料明細
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工考勤基本資料", false);
				
			}else if("EHF030102T0".equals(type)){	//薪資項目
				
				ehf010106.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工薪資基本資料", false);
				
			}else if("EHF010101T0".equals(type)){	//津貼項目
				
				ehf010106.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工薪資基本資料", false);
				
			}else if("EHF030300T1".equals(type)){
				
				ehf010106.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工保險基本資料", false);
				
			}else if("EHF010106T5".equals(type)){
				
				ehf010106.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工基本資料", false);
				
			}else if("EHF010106T3".equals(type)){
				
				ehf010106.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工履經歷資料", false);
				
			}else if("EHF010106T2".equals(type)){
				
				ehf010106.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工履經歷資料", false);
				
			}else if("EHF010106T4".equals(type)){
				
				ehf010106.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工履經歷資料", false);
				
			}
			
			//關閉EHF010106 元件
			ehf010106.close();
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("HR_EmployeeSysNo");
			paramMap.put(super.KEY_ID, key_id);
									
			//新增完成
			chk_flag = true;
			
			// 清掉畫面上的新增明細欄位
			this.cleanAddField(Form);
			
		}catch(Exception e){
			chk_flag = false;
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
		EHF010106M0F Form = (EHF010106M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String user_id = (String)paramMap.get(super.USER_ID);
		String user_name = (String)paramMap.get(super.USER_NAME);
		Map return_map = new HashMap();		
		
		try{
			//建立EHF010106元件
			EHF010106 ehf010106 = new EHF010106();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("HR_EmployeeSysNo", key[0]);  //員工系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf010106.queryEditData(queryCondMap);
			//
			List EHF020403CList = ehf010106.queryEHF020403CList(queryCondMap);
			
			Iterator it_EHF020403C = EHF020403CList.iterator();
			Map tempMap_EHF020403C = null;
			EHF010106M0F bean_1 = null;
			
			//建立空清單
			List<EHF010106M0F> querylist_1 = new ArrayList<EHF010106M0F>();
			
			while(it_EHF020403C.hasNext()){
				
				tempMap_EHF020403C = (Map) it_EHF020403C.next();
				
				bean_1 = new EHF010106M0F();
				
				bean_1.setHR_EmployeeSysNo((String)tempMap_EHF020403C.get("EHF020403T1_01"));	//
				bean_1.setEHF020403T1_01((String)tempMap_EHF020403C.get("EHF020403T1_01"));	//
				bean_1.setEHF020403T1_02((String)tempMap_EHF020403C.get("EHF020403T1_02"));	//				
				bean_1.setEHF020403T1_04_START((String)tempMap_EHF020403C.get("EHF020403T1_04_START"));	//
				bean_1.setEHF020403T1_04_END((String)tempMap_EHF020403C.get("EHF020403T1_04_END"));	//
				bean_1.setEHF020403T1_04_END_HH((String)tempMap_EHF020403C.get("EHF020403T1_04_END_HH"));
				bean_1.setEHF020403T1_04_END_MM((String)tempMap_EHF020403C.get("EHF020403T1_04_END_MM"));
				bean_1.setEHF020403T1_05((String)tempMap_EHF020403C.get("EHF020403T1_05"));	//
				bean_1.setEHF020403T1_06((String)tempMap_EHF020403C.get("EHF020403T1_06"));	//
												
				querylist_1.add(bean_1);
			}
			
			Form.setEHF020403C(querylist_1);
			//
			List EHF030200CList_1 = ehf010106.queryEHF030200CList_1(queryCondMap);
			
			Iterator it_EHF030200C_1 = EHF030200CList_1.iterator();
			Map tempMap_EHF030200C_1 = null;
			EHF010106M0F bean_2 = null;
			
			//建立空清單
			List<EHF010106M0F> querylist_2 = new ArrayList<EHF010106M0F>();
			
			while(it_EHF030200C_1.hasNext()){
				
				tempMap_EHF030200C_1 = (Map) it_EHF030200C_1.next();
				
				bean_2 = new EHF010106M0F();
				
				bean_2.setType("01");
				bean_2.setHR_EmployeeSysNo((String)tempMap_EHF030200C_1.get("EHF030200T1_01"));
				bean_2.setEHF030200T1_01((String)tempMap_EHF030200C_1.get("EHF030200T1_01"));  //員工工號
				bean_2.setEHF030200T1_02((String)tempMap_EHF030200C_1.get("EHF030200T1_02"));  //薪資項目金額序號
				bean_2.setEHF030200T1_02_TXT((String)tempMap_EHF030200C_1.get("EHF030101T0_02"));  //薪資項目名稱
				bean_2.setEHF030200T1_04_01((String)tempMap_EHF030200C_1.get("EHF030200T1_05"));  
				bean_2.setEHF030200T1_02_MONEY(((Integer)tempMap_EHF030200C_1.get("EHF030102T0_06")).toString());  //金額
				bean_2.setEHF030200T1_03((String)tempMap_EHF030200C_1.get("EHF030200T1_03"));  //備註
				if(!"".equals(bean_2.getEHF030200T1_02()) && bean_2.getEHF030200T1_02() != null ){
					querylist_2.add(bean_2);
				}
				
			}
			//
			List EHF030200CList_2 = ehf010106.queryEHF030200CList_2(queryCondMap);
			
			Iterator it_EHF030200C_2 = EHF030200CList_2.iterator();
			Map tempMap_EHF030200C_2 = null;
			EHF010106M0F bean_21 = null;
									
			while(it_EHF030200C_2.hasNext()){
				
				tempMap_EHF030200C_2 = (Map) it_EHF030200C_2.next();
				
				bean_21 = new EHF010106M0F();
				
				bean_21.setType("02");
				bean_21.setHR_EmployeeSysNo((String)tempMap_EHF030200C_1.get("EHF030200T1_01"));
				bean_21.setEHF030200T1_01((String)tempMap_EHF030200C_2.get("EHF030200T1_01"));  //員工工號
				bean_21.setEHF030200T1_02((String)tempMap_EHF030200C_2.get("EHF030200T1_02"));  //薪資項目金額序號
				bean_21.setEHF030200T1_02_TXT((String)tempMap_EHF030200C_2.get("EHF010101T0_02"));  //薪資項目名稱
				bean_21.setEHF030200T1_02_MONEY(((Integer)tempMap_EHF030200C_2.get("EHF010101T0_06")).toString());  //金額
				bean_21.setEHF030200T1_03((String)tempMap_EHF030200C_2.get("EHF030200T1_03"));  //備註
				bean_21.setEHF030200T1_04_01((String)tempMap_EHF030200C_2.get("EHF010100T0_04"));
				if(!"".equals(bean_21.getEHF030200T1_02()) && bean_21.getEHF030200T1_02() != null ){
					querylist_2.add(bean_21);
				}
				
			}
			
			Form.setEHF030200C(querylist_2);
			//
			List EHF030300CList = ehf010106.queryEHF030300CList(queryCondMap);
			
			Iterator it_EHF030300C = EHF030300CList.iterator();
			Map tempMap_EHF030300C = null;
			EHF010106M0F bean_3 = null;
			
			//建立空清單
			List<EHF010106M0F> querylist_3 = new ArrayList<EHF010106M0F>();
			
			while(it_EHF030300C.hasNext()){
				
				tempMap_EHF030300C = (Map) it_EHF030300C.next();
				
				bean_3 = new EHF010106M0F();
				
				bean_3.setHR_EmployeeSysNo((String)tempMap_EHF030300C.get("EHF030300T1_01"));	//
				bean_3.setEHF030300T1_01((String)tempMap_EHF030300C.get("EHF030300T1_01"));	//
				bean_3.setEHF030300T1_02((String)tempMap_EHF030300C.get("EHF030300T1_02"));	//
				bean_3.setEHF030300T1_03((String)tempMap_EHF030300C.get("EHF030300T1_03"));	//				
				bean_3.setEHF030300T1_04((String)tempMap_EHF030300C.get("EHF030300T1_04"));	//
				bean_3.setEHF030300T1_05((String)tempMap_EHF030300C.get("EHF030300T1_05"));	//
				bean_3.setEHF030300T1_06((String)tempMap_EHF030300C.get("EHF030300T1_06"));	//
				bean_3.setHR_CompanySysNo((String)tempMap_EHF030300C.get("HR_CompanySysNo"));	//
				
				querylist_3.add(bean_3);
				
			}
									
			Form.setEHF030300C(querylist_3);
			//家庭關係明細
			List EHF010106T5_LIST = ehf010106.queryEHF010106T5List(queryCondMap);
															
			Form.setEHF010106T5_LIST(EHF010106T5_LIST);						
			//學歷明細
			List EHF010106T3_LIST = ehf010106.queryEHF010106T3List(queryCondMap);
			
			Iterator it_EHF010106T3 = EHF010106T3_LIST.iterator();
			Map tempMap_EHF010106T3 = null;
			EHF010106M0F bean_4 = null;
			
			//建立空清單
			List<EHF010106M0F> querylist_4 = new ArrayList<EHF010106M0F>();
			
			while(it_EHF010106T3.hasNext()){
				
				tempMap_EHF010106T3 = (Map) it_EHF010106T3.next();
				
				bean_4 = new EHF010106M0F();
				
				bean_4.setHR_EmployeeSysNo((String)tempMap_EHF010106T3.get("HR_EmployeeSysNo"));	//
				bean_4.setEHF010106T3_01((Integer)tempMap_EHF010106T3.get("EHF010106T3_01"));	//
				if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
					bean_4.setEHF010106T3_02("國小");	//
				}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
					bean_4.setEHF010106T3_02("國中");	//
				}else if("3".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
					bean_4.setEHF010106T3_02("高中職");	//
				}else if("4".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
					bean_4.setEHF010106T3_02("專科");	//
				}else if("5".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
					bean_4.setEHF010106T3_02("大學");	//
				}else if("6".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
					bean_4.setEHF010106T3_02("碩士");	//
				}else if("7".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
					bean_4.setEHF010106T3_02("博士");	//
				}
				bean_4.setEHF010106T3_03((String)tempMap_EHF010106T3.get("EHF010106T3_03"));	//				
				bean_4.setEHF010106T3_04((String)tempMap_EHF010106T3.get("EHF010106T3_04"));	//
				bean_4.setEHF010106T3_05((String)tempMap_EHF010106T3.get("EHF010106T3_05")+"~"+(String)tempMap_EHF010106T3.get("EHF010106T3_06"));	//				
				if("0".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
					bean_4.setEHF010106T3_07("畢業");
				}else if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
					bean_4.setEHF010106T3_07("肄業");
				}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
					bean_4.setEHF010106T3_07("在學");
				}				
				bean_4.setHR_CompanySysNo((String)tempMap_EHF010106T3.get("HR_CompanySysNo"));	//
				
				querylist_4.add(bean_4);
				
			}
			
			Form.setEHF010106T3_LIST(querylist_4);
			//履經歷明細						
			List EHF010106T2_LIST = ehf010106.queryEHF010106T2List(queryCondMap);
			
			Iterator it_EHF010106T2 = EHF010106T2_LIST.iterator();
			Map tempMap_EHF010106T2 = null;
			EHF010106M0F bean_5 = null;
			
			//建立空清單
			List<EHF010106M0F> querylist_5 = new ArrayList<EHF010106M0F>();
			
			while(it_EHF010106T2.hasNext()){
				
				tempMap_EHF010106T2 = (Map) it_EHF010106T2.next();
				
				bean_5 = new EHF010106M0F();
				
				bean_5.setHR_EmployeeSysNo((String)tempMap_EHF010106T2.get("HR_EmployeeSysNo"));	//
				bean_5.setEHF010106T2_01((Integer)tempMap_EHF010106T2.get("EHF010106T2_01"));	//
				bean_5.setEHF010106T2_02((String)tempMap_EHF010106T2.get("EHF010106T2_02"));	//
				bean_5.setEHF010106T2_05((String)tempMap_EHF010106T2.get("EHF010106T2_05"));	//
				bean_5.setEHF010106T2_06((String)tempMap_EHF010106T2.get("EHF010106T2_06"));	//
				bean_5.setEHF010106T2_07((String)tempMap_EHF010106T2.get("EHF010106T2_07")+"~"+(String)tempMap_EHF010106T2.get("EHF010106T2_08"));	//
				bean_5.setEHF010106T2_09((String)tempMap_EHF010106T2.get("EHF010106T2_09"));	//
				bean_5.setHR_CompanySysNo((String)tempMap_EHF010106T2.get("HR_CompanySysNo"));	//
				
				querylist_5.add(bean_5);
				
			}
			
			Form.setEHF010106T2_LIST(querylist_5);
			//證照資料明細
			List EHF010106T4_LIST = ehf010106.queryEHF010106T4List(queryCondMap);
			
			Form.setEHF010106T4_LIST(EHF010106T4_LIST);
			
			//關閉EHF010106元件
			ehf010106.close();
			
			if(!dataMap.isEmpty()){
								
				if(!"".equals((String)dataMap.get("EHF010106T0_23"))){
					if(this.chk_Picture(getServlet().getServletContext().getRealPath("/")+"\\config\\"+(String)dataMap.get("EHF010106T0_23"))){
						Form.setEHF010106T0_23((String)dataMap.get("EHF010106T0_23"));
					}else{
					
						Form.setEHF010106T0_23("pic\\No_picture.png");
					}
				}else{
					
					Form.setEHF010106T0_23("pic\\No_picture.png");
				}
				Form.setLogin_No(user_id);  //登入帳號
				Form.setHR_EmployeeSysNo((String)dataMap.get("HR_EmployeeSysNo"));  //員工系統代碼
				Form.setHR_EmployeeNo((String)dataMap.get("HR_EmployeeNo"));  //員工工號
				Form.setHR_DepartmentSysNo((String)dataMap.get("HR_DepartmentNo"));	//部門代號
				Form.setHR_DepartmentName((String)dataMap.get("HR_DepartmentName"));	//部門名稱
				Form.setEHF010106T0_01((String)dataMap.get("EHF010106T0_01"));
				Form.setEHF010106T0_02((String)dataMap.get("EHF010106T0_02"));
				Form.setEHF010106T0_03((String)dataMap.get("EHF010106T0_03"));
				Form.setEHF010106T0_04((String)dataMap.get("EHF010106T0_04"));  //姓名(中)
				Form.setEHF010106T0_05((String)dataMap.get("EHF010106T0_05"));
				Form.setEHF010106T0_06((String)dataMap.get("EHF010106T0_06"));
				Form.setEHF010106T0_07((String)dataMap.get("EHF010106T0_07"));
				Form.setEHF010106T0_08((String)dataMap.get("EHF010106T0_08"));
				Form.setEHF010106T0_09((String)dataMap.get("EHF010106T0_09"));
				Form.setEHF010106T0_10((String)dataMap.get("EHF010106T0_10"));
				
				if(!"".equals((String)dataMap.get("EHF010106T0_11")) && (String)dataMap.get("EHF010106T0_11") != null){
					String phone_2[] = ((String)dataMap.get("EHF010106T0_11")).split("-");
					Form.setEHF010106T0_1101(phone_2[0]);
					Form.setEHF010106T0_1102(phone_2[1]);
				}else{
					Form.setEHF010106T0_1101("");
					Form.setEHF010106T0_1102("");
				}
				
				Form.setEHF010106T0_12((String)dataMap.get("EHF010106T0_12"));
				
				if(!"".equals((String)dataMap.get("EHF010106T0_13")) && (String)dataMap.get("EHF010106T0_13") != null){
					String phone_3[] = ((String)dataMap.get("EHF010106T0_13")).split("-");
					Form.setEHF010106T0_1301(phone_3[0]);
					Form.setEHF010106T0_1302(phone_3[1]);
				}else{
					Form.setEHF010106T0_1301("");
					Form.setEHF010106T0_1302("");
				}
				
				Form.setEHF010106T0_14((String)dataMap.get("EHF010106T0_14"));
				Form.setEHF010106T0_15((String)dataMap.get("EHF010106T0_15"));
				Form.setEHF010106T0_16((String)dataMap.get("EHF010106T0_16"));
				Form.setEHF010106T0_17((String)dataMap.get("EHF010106T0_17"));
				
				if(!"".equals((String)dataMap.get("EHF010106T0_18")) && (String)dataMap.get("EHF010106T0_18") != null){
					String phone_1[] = ((String)dataMap.get("EHF010106T0_18")).split("-");
					Form.setEHF010106T0_1801(phone_1[0]);
					Form.setEHF010106T0_1802(phone_1[1]);
				}else{
					Form.setEHF010106T0_1801("");
					Form.setEHF010106T0_1802("");
				}
				
				Form.setEHF010106T0_19((String)dataMap.get("EHF010106T0_19"));
				//Form.setEHF010106T0_20((Boolean)dataMap.get("EHF010106T0_20"));
				//Form.setEHF010106T0_21((Boolean)dataMap.get("EHF010106T0_21"));
				Form.setEHF010106T0_22((String)dataMap.get("EHF010106T0_22"));
				Form.setEHF010106T0_23((String)dataMap.get("EHF010106T0_23"));
				Form.setEHF010106T0_24((String)dataMap.get("EHF010106T0_24"));
				Form.setEHF010106T0_25((String)dataMap.get("EHF010106T0_25"));
				
				Form.setEHF010106T1_01((String)dataMap.get("EHF010106T1_01"));
				if("1".equals((String)dataMap.get("EHF010106T1_01"))){
					Form.setEHF010106T1_0201((String)dataMap.get("EHF010106T1_02"));
				}else if("2".equals((String)dataMap.get("EHF010106T1_01"))){
					Form.setEHF010106T1_0202((String)dataMap.get("EHF010106T1_02"));
				}else if("3".equals((String)dataMap.get("EHF010106T1_01"))){
					Form.setEHF010106T1_0203((String)dataMap.get("EHF010106T1_02"));
				}else if("4".equals((String)dataMap.get("EHF010106T1_01"))){
					Form.setEHF010106T1_0204((String)dataMap.get("EHF010106T1_02"));
				}
								
				Form.setEHF010106T6_01((String)dataMap.get("EHF010106T6_01"));
				Form.setEHF010106T6_02((String)dataMap.get("EHF010106T6_02"));
				Form.setHR_JobNameSysNo((String)dataMap.get("HR_JobNameNo"));
				Form.setHR_JobName((String)dataMap.get("HR_JobName"));
				
				Form.setEHF010100T1_04((String)dataMap.get("EHF010100T0_03"));  //班別代號
				Form.setEHF010100T1_04_TXT((String)dataMap.get("EHF010100T0_04"));  //班別名稱
				Form.setEHF020403T0_05((String)dataMap.get("EHF020403T0_05"));  //是否計算考勤
				
				Form.setEHF030200T0_07((String)dataMap.get("EHF030200T0_07"));  //計薪方式
				Form.setEHF030200T0_05((String)dataMap.get("EHF030200T0_05"));  //給薪方式
				Form.setEHF030200T0_06((String)dataMap.get("EHF030200T0_06"));  //銀行代號
				Form.setEHF030200T0_06_AC((String)dataMap.get("EHF030200T0_06_AC"));  //匯款帳號
				Form.setEHF030200T0_04((Integer)dataMap.get("EHF030200T0_04"));  //基本薪資
				Form.setEHF030200T0_19((Integer)dataMap.get("EHF030200T0_19"));  //全勤獎金
				Form.setEHF030200T0_18((String)dataMap.get("EHF030200T0_18"));  //加班費規則
				Form.setEHF030200T0_20((String)dataMap.get("EHF030200T0_20"));  //全勤扣費規則
				if((Boolean)dataMap.get("EHF030200T0_21")){	//是否代扣福利金
					Form.setEHF030200T0_21("1");
				}else{
					Form.setEHF030200T0_21("0");	
				}				
				Form.setEHF030200T0_08((String)dataMap.get("EHF030200T0_08"));  //是否啟用
				Form.setEHF030200T0_12((String)dataMap.get("EHF030200T0_12"));  //備註
				
				Form.setEHF030300T0_05((String)dataMap.get("EHF030300T0_05")); //勞保投保等級
				Form.setEHF030300T0_05_NUMBER((String)dataMap.get("EHF030104T0_01")); //健保投保等級
				Form.setEHF030300T0_05_VERSION((String)dataMap.get("EHF030104T0_02"));
				Form.setEHF030300T0_05_VERSION_NAME((String)dataMap.get("EHF030104T0_02_VERSION"));
				Form.setEHF030300T0_05_HIDE((Integer)dataMap.get("EHF030104T1_03")==null?0:(Integer)dataMap.get("EHF030104T1_03")); //勞保投保薪資
				Form.setEHF030300T0_05_START(dataMap.get("EHF030300T0_05_START").toString());  //勞保投保日期
				Form.setEHF030300T0_05_END( (dataMap.get("EHF030300T0_05_END").equals("0000/00/00")?"":dataMap.get("EHF030300T0_05_END")).toString() );  //勞保退保日期
				Form.setEHF030300T0_07((String)dataMap.get("EHF030300T0_07")); //健保投保等級
				Form.setEHF030300T0_07_NUMBER((String)dataMap.get("EHF030103T0_01")); //健保投保等級
				Form.setEHF030300T0_07_VERSION((String)dataMap.get("EHF030103T0_02"));
				Form.setEHF030300T0_07_VERSION_NAME((String)dataMap.get("EHF030103T0_02_VERSION"));
				Form.setEHF030300T0_07_HIDE((Integer)dataMap.get("EHF030103T1_03")==null?0:(Integer)dataMap.get("EHF030103T1_03")); //健保投保薪資
				Form.setEHF030300T0_07_START(dataMap.get("EHF030300T0_07_START").toString());  //健保投保日期
				Form.setEHF030300T0_07_END( (dataMap.get("EHF030300T0_07_END").equals("0000/00/00")?"":dataMap.get("EHF030300T0_07_END")).toString() );  //健保退保日期
				Form.setEHF030300T0_10((Integer)dataMap.get("EHF030300T0_10")==null?0:(Integer)dataMap.get("EHF030300T0_10"));  //
				Form.setEHF030300T0_15((Integer)dataMap.get("EHF030300T0_15")==null?0:(Integer)dataMap.get("EHF030300T0_15"));  //勞退自提金額
				Form.setEHF030300T0_11((Integer)dataMap.get("EHF030300T0_11")==null?0:(Integer)dataMap.get("EHF030300T0_11"));  //員工與眷屬加保人數
				//temp
				/*EMS_InsuranceTools ems_insurance_tools = new EMS_InsuranceTools("", "", user_id, user_name, comp_id);
				
				//計算健保費用
				int health_fee = ems_insurance_tools.countEmpHealthFee(conn, (String)dataMap.get("HR_EmployeeSysNo"), (String)dataMap.get("EHF010106T0_06"),
								(Integer)dataMap.get("EHF030103T1_03"), 0, 0);
				
				//計算勞保費用
				int labor_fee = ems_insurance_tools.countEmpLaborFee(conn, (String)dataMap.get("HR_EmployeeSysNo"), (String)dataMap.get("EHF010106T0_06"),
								(Integer)dataMap.get("EHF030104T1_03"), 30);
				
				//計算健保費用(包含眷屬)
				health_fee = ems_insurance_tools.countEmpHealthFee(conn, (String)dataMap.get("HR_EmployeeSysNo"), (String)dataMap.get("EHF010106T0_06"),
							(Integer)dataMap.get("EHF030103T1_03"), (Integer)dataMap.get("EHF030300T0_11") - 1, 
							(Integer)dataMap.get("EHF030300T0_10"));*/
				
				Form.setEHF030300T0_09(0); //勞健保總金額
				
				chk_flag = true;
				
			}
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
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
		return false;
	}

	@Override
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF010106M0F Form = (EHF010106M0F) form;
		
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getHR_EmployeeSysNo());
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
		
		EHF010106M0F Form = (EHF010106M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getHR_EmployeeSysNo());
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
	public boolean executeDelDetailData(Connection conn, String type,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF010106M0F Form = (EHF010106M0F) form;		
		boolean chk_flag = false;
		
		try{
			//建立EHF010106元件
			EHF010106 ehf010106 = new EHF010106();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			if("EHF020403T1".equals(type)){
				//刪除感應卡				
				ehf010106.delDetailData(type,detailDataMap);
				
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工考勤基本資料", false);
				
			}else if("EHF030200T1".equals(type)){
				//刪除薪資項目
				ehf010106.delDetailData(type,detailDataMap);
				
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工薪資基本資料", false);
				
			}else if("EHF030300T1".equals(type)){
				//刪除保險眷屬
				ehf010106.delDetailData(type,detailDataMap);
				
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工保險基本資料", false);
				
			}else if("EHF010106T5".equals(type)){
				//刪除家庭關係明細
				ehf010106.delDetailData(type,detailDataMap);
				
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工基本資料", false);
				
			}else if("EHF010106T3".equals(type)){
				//刪除學歷明細
				ehf010106.delDetailData(type,detailDataMap);
				
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工履經歷資料", false);
				
			}else if("EHF010106T2".equals(type)){
				//刪除履經歷明細
				ehf010106.delDetailData(type,detailDataMap);
				
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工履經歷資料", false);
				
			}else if("EHF010106T4".equals(type)){
				//刪除證照資料明細
				ehf010106.delDetailData(type,detailDataMap);
				
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF010106M1_Tabs_01", "員工履經歷資料", false);
				
			}
			
			//關閉EHF010106 元件
			ehf010106.close();
			
			//設定Query Key
			String[] key_id = new String[1];			
			key_id[0] = (String)detailDataMap.get("HR_EmployeeSysNo");
			paramMap.put(super.KEY_ID, key_id);
									
			chk_flag = true;
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		EHF010106M0F Form = (EHF010106M0F) form;
		List list = new ArrayList();
		
		try{
			Form.setEHF020403C(list);
			Form.setEHF010106T5_LIST(list);
			Form.setEHF010106T2_LIST(list);
			Form.setEHF010106T3_LIST(list);
			Form.setEHF010106T4_LIST(list);
			
			Form.setEHF010106T0_23("pic\\No_picture.png");
			request.setAttribute("button_init", "init");
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
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
			List listEHF020403T1_04_HH = new ArrayList();
			DecimalFormat df=new DecimalFormat("00");
			for (int i = 0; i <= 23; i++) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF020403T1_04_HH.add(bform);
			}
			request.setAttribute("listEHF020403T1_04_HH", listEHF020403T1_04_HH);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		try{
			List listEHF020403T1_04_MM = new ArrayList();
			DecimalFormat df=new DecimalFormat("00");
			for (int i = 0; i < 60; i=i+5) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF020403T1_04_MM.add(bform);
			}
			request.setAttribute("listEHF020403T1_04_MM", listEHF020403T1_04_MM);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生員工類別
		try{
			List EHF030200T0_03_list = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			EHF030200T0_03_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("正式員工");
			EHF030200T0_03_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("臨時員工");
			EHF030200T0_03_list.add(bform);
			request.setAttribute("EHF030200T0_03_list", EHF030200T0_03_list);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生計薪方式
		try{
			List EHF030200T0_07_list = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			EHF030200T0_07_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("月薪");
			EHF030200T0_07_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("日薪");
			EHF030200T0_07_list.add(bform);
			request.setAttribute("listEHF030200T0_07", EHF030200T0_07_list);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生給薪方式
		try{
			List EHF030200T0_05_list = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			EHF030200T0_05_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("現金");
			EHF030200T0_05_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("銀行轉帳");
			EHF030200T0_05_list.add(bform);
			request.setAttribute("listEHF030200T0_05", EHF030200T0_05_list);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生銀行代號
		try {
			List datas = new ArrayList();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = " SELECT EHF030106T0_01, EHF030106T0_02 " +
						 " FROM EHF030106T0 " +
					     " WHERE 1=1 " +
					     " AND EHF030106T0_04 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
			
			ResultSet rs=stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			datas.add(bform);
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("EHF030106T0_01"));
				bform.setItem_value("("+rs.getString("EHF030106T0_01")+")"+rs.getString("EHF030106T0_02"));
				datas.add(bform);	
				
			}
			
			rs.close();
			stmt.close();
			
			request.setAttribute("listBank", datas);
							
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//產生代扣類型
		try{
			List listEHF030200T0_14_TYPE = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF030200T0_14_TYPE.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("依比例");
			listEHF030200T0_14_TYPE.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("依固定金額");
			listEHF030200T0_14_TYPE.add(bform);
			request.setAttribute("listEHF030200T0_14_TYPE", listEHF030200T0_14_TYPE);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生加班費規則
		try{
			request.setAttribute("list_EHF030200T0_18", 
			tools.getSelectOptionByTable(conn, 
										 "EHF030107T0_02", 
										 "CONCAT('(', EHF030107T0_02, ')', EHF030107T0_03)",
										 "EHF030107T0", 
										 "EHF030107T0_10", getLoginUser(request).getCompId(), 
										 "", "01")
			);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//產生全勤獎金扣費規則
		try{
			request.setAttribute("list_EHF030200T0_20", 
			tools.getSelectOptionByTable(conn, 
										 "EHF030108T0_02", 
										 "CONCAT('(', EHF030108T0_02, ')', EHF030108T0_03)",
										 "EHF030108T0", 
										 "EHF030108T0_13", getLoginUser(request).getCompId(), 
										 "", "01")
			);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			List datas = new ArrayList();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF010100T0_01 ,EHF010100T0_02 ,EHF010100T0_03 ,EHF010100T0_04 ,EHF010100T0_05 ,EHF010100T0_06 ,EHF010100T0_07 " +
			" ,EHF010100T0_08 ,EHF010100T0_09 ,EHF010100T0_10 " +
			" FROM EHF010100T0 " +
			" WHERE 1=1 " +
			" AND EHF010100T0_11 = '"+getLoginUser(request).getCompId()+"' " ;
			
			ResultSet rs=stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			datas.add(bform);
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("EHF010100T0_03"));
				bform.setItem_value(rs.getString("EHF010100T0_04"));
				datas.add(bform);	
				
			}
			
			rs.close();
			stmt.close();
			
			request.setAttribute("listEHF030200T1_04", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		try{
			request.setAttribute("listEHF010106T0_06", this.getSelectOptions(conn,true, "EMPTYPE","and FM010501_06='"+userform(request).getSC0030_14()+"'"));//員工類別
		}catch(Exception e) {
			System.out.println(e);
		}
	
	
	}
	/**
	 * 儲存員工考勤資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataATT(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF010106M0F Form = (EHF010106M0F) form;
		Connection conn = null;
		String month_start_date = "";
		AuthorizedBean authbean = getLoginUser(request);
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataATT");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			
			if (lc_errors.isEmpty()) {
				
				String sql_2 = "" +
				
				" UPDATE ehf010100t1 SET  " + 
				" EHF010100T1_02=? ,EHF010100T1_04=? ,EHF010100T1_05=? " +
				" ,USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE EHF010100T1_03 = '"+Form.getHR_EmployeeSysNo()+"' " +
				" AND EHF010100T1_06 = '"+getLoginUser(request).getCompId()+"' " ;

				PreparedStatement pstmt_2 = conn.prepareStatement(sql_2);
				P6PreparedStatement p6stmt_2 = new P6PreparedStatement(null,pstmt_2, null, sql_2);
				int indexid_2 = 1;
				pstmt_2.setString(indexid_2, "");
				indexid_2++;				
				pstmt_2.setString(indexid_2, Form.getEHF010100T1_04_KEY());
				indexid_2++;
				pstmt_2.setString(indexid_2, "");
				indexid_2++;
				pstmt_2.setString(indexid_2, getLoginUser(request).getUserName());
				indexid_2++;
				
				p6stmt_2.executeUpdate();
				
				pstmt_2.close();
				p6stmt_2.close();
				
				//conn.commit();
				
				//更新明細資料
				int count = updateList( conn ,Form.getEHF020403C() ,"UPDATE" ,getLoginUser(request).getUserName() ,getLoginUser(request).getCompId() ,Form );
				//更新表頭資料
				String sql = "" +
				" UPDATE ehf020403t0 SET " +
				" EHF020403T0_03=? ,EHF020403T0_05=?," +
				" USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE EHF020403T0_01 = '"+Form.getHR_EmployeeSysNo()+"' " +
				" AND EHF020403T0_04 = '"+getLoginUser(request).getCompId()+"' ";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, "");  //備註
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020403T0_05());  //是否記錄考勤
				indexid++;				
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功，表頭及明細修改" + count + "筆");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				//temp
				//取得系統當前日期時間
				/*Calendar cur_system_cal = Calendar.getInstance();
							
				
				cur_system_cal.add(Calendar.DATE, -1);//往前一天
				
				if(this.Inquiry_charge_off(conn, request, form,cur_system_cal)){
					
					
					cur_system_cal = Calendar.getInstance();
					cur_system_cal.add(Calendar.MONTH, -1);//前一個月
					// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
					month_start_date = ems_tools.convertADDateToStrng(ems_tools.getFirstMonthDay(cur_system_cal));
					
					Calendar start_cal=(Calendar)ems_tools.covertStringToCalendar(month_start_date).clone();
					
					
					cur_system_cal = Calendar.getInstance();
					cur_system_cal.add(Calendar.DATE, -1);//往前一天
					cur_system_cal.set(Calendar.HOUR_OF_DAY, 0);
					cur_system_cal.set(Calendar.MINUTE, 0);
					cur_system_cal.set(Calendar.SECOND, 0);
					cur_system_cal.set(Calendar.MILLISECOND, 0);

					Calendar end_cal=(Calendar)cur_system_cal.clone();

					boolean chk_run_flag = true;
					
					EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(authbean.getCompId(), "", authbean.getUserId() );
					
					while(chk_run_flag){
						
						if(start_cal.equals(end_cal)){
							chk_run_flag = false;
						}
						
						//產生考勤日期
						String cur_date = ems_tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd");  //當前計算的考勤日期
						
						//設定考勤日期
						att.setSta_cur_day( cur_date );
						//執行產生考勤資料的動作
						att.execute_Attendance(conn, true, Form.getEHF020403T0_01());
						
						start_cal.add(Calendar.DAY_OF_MONTH, 1);
						
						
						System.out.println(ems_tools.covertDateToString(start_cal.getTime(), "yyyy/MM/dd"));
					}
				}*/
				
				// 清掉畫面上的新增明細欄位
				this.cleanAddField(Form);
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","儲存失敗"+ request.getAttribute("ErrMSG"));
				return queryForm(mapping, form, request, response);
			}
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","員工班別或感應卡設定儲存錯誤!!請重新操作!!");			
	        e.printStackTrace();	        
	        return queryForm(mapping, form, request, response);	        
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
	
	private int updateList( Connection conn ,List EHF020403c ,String Action ,String user_name ,String comp_id, EHF010106M0F Form ) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String insql = "" +
				" UPDATE ehf020403t1 SET " +
				" EHF020403T1_05=? ,EHF020403T1_04_END=?" +
				" WHERE 1=1 " +
				" AND EHF020403T1_01=? AND EHF020403T1_02=? AND EHF020403T1_06=? ";
		
		PreparedStatement pstmt = conn.prepareStatement(insql);
		P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
		Statement stmt = conn.createStatement();
		int ucount = 0;
		int dcount = 0;
		for (int i = 0; i < EHF020403c.size(); i++) {
			EHF010106M0F Form_1 = (EHF010106M0F) EHF020403c.get(i);
			
			if (Action.equalsIgnoreCase("UPDATE")) {
				Date date = sdf.parse(Form_1.getEHF020403T1_04_END()+" "+Form_1.getEHF020403T1_04_END_HH()+":"+Form_1.getEHF020403T1_04_END_MM()+":00");
				p6stmt.setString(1, Form_1.getEHF020403T1_05());  //備註
				//p6stmt.setString(2, ems_tools.covertDateToString(date, "yyyy/MM/dd HH:mm:ss"));  //備註
				p6stmt.setString(2, "");
				p6stmt.setString(3, Form.getEHF020403T0_01());  //員工工號
				p6stmt.setString(4, Form_1.getEHF020403T1_02());  //感應卡號
				p6stmt.setString(5, comp_id);  //公司代碼
				p6stmt.executeUpdate();
				System.out.println(p6stmt.getQueryFromPreparedStatement());
				ucount++;
			}
			if (Action.equalsIgnoreCase("delete")) {
				if (Form_1.isCHECKED()) {
					stmt.execute("DELETE FROM ehf020403t1 " +
						" WHERE 1=1 " +
						" AND EHF020403T1_01 = '"+Form.getEHF020403T1_01()+"' " +//員工工號
						" AND EHF020403T1_02 = '"+Form.getEHF020403T1_02()+"'" +//感應卡號
						" AND EHF020403T1_06 = '"+comp_id+"' ");
					dcount++;	
					
					
					/*
					String sql = "" +
					" UPDATE ehf020403t1 SET " +
					" EHF020403T1_04=? ,EHF020403T1_04_END=? " +
					" WHERE 1=1 " +
					" AND EHF020403T1_01=? " +
					" AND EHF020403T1_02=? " +
					" AND EHF020403T1_06=? ";
					
					
					PreparedStatement pstmt_01 = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt_01 = new P6PreparedStatement(null,pstmt_01, null, sql);

					p6stmt_01.setString(1, Form.getEHF020403T1_05());  //備註
					p6stmt_01.setString(2, Form.getEHF020403T1_01());  //員工工號
					p6stmt_01.setString(3, Form.getEHF020403T1_02());  //感應卡號
					p6stmt_01.setString(4, comp_id);  //公司代碼
					p6stmt_01.executeUpdate();
					
					System.out.println(p6stmt.getQueryFromPreparedStatement());

					pstmt_01.close();
					p6stmt_01.close();
					
					conn.commit();*/
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
	 * 驗證前一個月的薪資或是考勤  是否出帳或確認
	 * @param conn
	 * @param request
	 * @param form
	 * @param cur_system_cal
	 * @return
	 */
	private boolean Inquiry_charge_off(Connection conn,	HttpServletRequest request, ActionForm form, Calendar cur_system_cal) {
		// TODO Auto-generated method stub
		boolean chk_flag = false;		
		
		/*cur_system_cal.add(Calendar.MONTH, -1);//往前一天
		
		String month[]=ems_tools.convertADDateToStrng(ems_tools.getFirstMonthDay(cur_system_cal)).split("/");
			// TODO Auto-generated method stub
			try {
			Statement stmt = conn.createStatement();
			String sql = "";

			sql += " SELECT * FROM EHF020900T0 WHERE 1 = 1 " +
					" AND EHF020900T0_M1 = '"+month[0]+"/"+month[1]+"'"+
					" AND EHF020900T0_04='" +getLoginUser(request).getCompId()+"'"+
					" ORDER BY EHF020900T0_M1 ";
		
			ResultSet rs = stmt.executeQuery(sql);
		
			if(rs.next()){	
				if(rs.getString("EHF020900T0_02").equals("02")&&rs.getString("EHF020900T0_02").equals("03")&&
						rs.getString("EHF020900T0_03").equals("03")&&rs.getString("EHF020900T0_03").equals("04")){
					//考勤已經確認或換休，薪資已經確認或出帳
					//不做任何處理
				}else{
					//考勤未確認或換休，薪資未確認或出帳
					chk_flag=true;
				}
				
			}else{
				//考勤未確認或換休，薪資未確認或出帳
				chk_flag=true;
			}
		
			rs.close();
			stmt.close();
			} catch (Exception e) {
				System.out.println(e);
				e.printStackTrace();
			}*/
			
		return chk_flag;
	}
	
	
	/**
	 * 儲存員工基本資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataEMP(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF010106M0F Form = (EHF010106M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataEMP");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			
			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				" UPDATE EHF010106T0 SET " +
				" EHF010106T0_01=?, EHF010106T0_02=?, EHF010106T0_03=?, EHF010106T0_04=?, EHF010106T0_05=?, " +
				" EHF010106T0_06=?, EHF010106T0_07=?, EHF010106T0_08=?, EHF010106T0_09=?, EHF010106T0_10=?, " +
				" EHF010106T0_11=?, EHF010106T0_12=?, EHF010106T0_13=?, EHF010106T0_14=?, EHF010106T0_15=?, " +
				" EHF010106T0_16=?, EHF010106T0_17=?, EHF010106T0_18=?, EHF010106T0_19=?, EHF010106T0_20=?, " +
				" EHF010106T0_21=?, EHF010106T0_22=?, EHF010106T0_23=?, EHF010106T0_24=?, EHF010106T0_25=?, " +
				" HR_JobDate=?, HR_OffJobDate=?, HR_Seniority=?, HR_SeniorityAdjust=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, HR_LastUpdateDate=NOW() " +
				" WHERE 1=1 " +
				" AND HR_EmployeeSysNo = '"+Form.getHR_EmployeeSysNo()+"' " +  //員工代號
				" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, Form.getEHF010106T0_01()==null?"":Form.getEHF010106T0_01());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_02()==null?"":Form.getEHF010106T0_02());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_03()==null?"":Form.getEHF010106T0_03());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_04());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_05()==null?"":Form.getEHF010106T0_05());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_06()==null?"1":Form.getEHF010106T0_06());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_07()==null?"":Form.getEHF010106T0_07());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_08()==null?"":Form.getEHF010106T0_08());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_09()==null?"":Form.getEHF010106T0_09());  //
				indexid++;
				p6stmt.setString(indexid, "".equals(Form.getEHF010106T0_10())?null:Form.getEHF010106T0_10());  //
				indexid++;
				if("".equals(Form.getEHF010106T0_1101())){
					p6stmt.setString(indexid, Form.getEHF010106T0_1102()==null?"":Form.getEHF010106T0_1102());  //
					indexid++;
				}else{
					p6stmt.setString(indexid, Form.getEHF010106T0_1101()+"-"+Form.getEHF010106T0_1102());	//
					indexid++;
				}				
				p6stmt.setString(indexid, Form.getEHF010106T0_12());  //
				indexid++;
				if("".equals(Form.getEHF010106T0_1301())){
					p6stmt.setString(indexid, Form.getEHF010106T0_1302()==null?"":Form.getEHF010106T0_1302());  //
					indexid++;
				}else{
					p6stmt.setString(indexid, Form.getEHF010106T0_1301()+"-"+Form.getEHF010106T0_1302());  //
					indexid++;
				}
				p6stmt.setString(indexid, Form.getEHF010106T0_14()==null?"":Form.getEHF010106T0_14());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_15()==null?"":Form.getEHF010106T0_15());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_16()==null?"":Form.getEHF010106T0_16());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_17()==null?"":Form.getEHF010106T0_17());  //
				indexid++;
				if("".equals(Form.getEHF010106T0_1801())){
					p6stmt.setString(indexid, Form.getEHF010106T0_1802()==null?"":Form.getEHF010106T0_1802());  //
					indexid++;
				}else{
					p6stmt.setString(indexid, Form.getEHF010106T0_1801()+"-"+Form.getEHF010106T0_1802());  //
					indexid++;
				}				
				p6stmt.setString(indexid, Form.getEHF010106T0_19()==null?"":Form.getEHF010106T0_19());  //
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_22()==null?"":Form.getEHF010106T0_22());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_23()==null?"":Form.getEHF010106T0_23());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_24()==null?"":Form.getEHF010106T0_24());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF010106T0_25()==null?"":Form.getEHF010106T0_25());  //
				indexid++;
				p6stmt.setString(indexid, Form.getHR_JobDate()==null?"":Form.getHR_JobDate());  //
				indexid++;
				p6stmt.setString(indexid, Form.getHR_OffJobDate()==null?"":Form.getHR_OffJobDate());  //
				indexid++;
				p6stmt.setString(indexid, Form.getHR_Seniority()==null?"":Form.getHR_Seniority());  //
				indexid++;
				p6stmt.setString(indexid, Form.getHR_SeniorityAdjust()==null?"":Form.getHR_SeniorityAdjust());  //
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();
				//建立新增資料Map
				Map dataMap = new HashMap();
				dataMap.put("HR_DepartmentSysNo", Form.getHR_DepartmentSysNo()==null?"":Form.getHR_DepartmentSysNo());
				dataMap.put("EHF010106T6_01", "".equals(Form.getEHF010106T6_01())?null:Form.getEHF010106T6_01());	//
				dataMap.put("EHF010106T6_02", "".equals(Form.getEHF010106T6_02())?null:Form.getEHF010106T6_02());	//
				dataMap.put("EHF010106T6_03", tool.StringToBoolean("1"));	//
				dataMap.put("HR_JobNameSysNo", Form.getHR_JobNameSysNo()==null?"":Form.getHR_JobNameSysNo());
				dataMap.put("HR_IsManager", "0");
				dataMap.put("HR_ManagerLevel", "");
				dataMap.put("HR_DesignateManager", "");
				dataMap.put("HR_JobAgent1", "");
				dataMap.put("HR_JobAgent2", "");
				dataMap.put("HR_JobAgent3", "");
				dataMap.put("HR_CheckLeave", "0");
				dataMap.put("HR_LeaveStartTime", "");
				dataMap.put("HR_LeaveEndTime", "");
				dataMap.put("HR_EmployeeSysNo", Form.getHR_EmployeeSysNo());
				dataMap.put("COMP_ID", getLoginUser(request).getCompId());
				
				//建立EHF010106元件
				EHF010106 ehf010106 = new EHF010106(getLoginUser(request).getCompId());
				
				//先將職務現況明細(EHF010106T6) 新增到歷史職務明細
				ehf010106.UPDATEDataDuty(dataMap);
				
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				generateSelectBox(conn, Form, request);
				request.setAttribute("mode", "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("MSG_EDIT", request.getAttribute("ErrMSG"));
				
				return mapping.findForward("success");
			}
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","員工基本資料儲存錯誤!!請重新操作!!");															
	        e.printStackTrace();
	        return queryForm(mapping, form, request, response);	
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
	 * 儲存員工履經歷資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataEXP(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF010106M0F Form = (EHF010106M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataEXP");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			
			if (lc_errors.isEmpty()) {
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				generateSelectBox(conn, Form, request);
				request.setAttribute("mode", "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("MSG_EDIT", request.getAttribute("ErrMSG"));
				
				return mapping.findForward("success");
			}
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","員工履經歷資料儲存錯誤!!請重新操作!!");															
	        e.printStackTrace();
	        return queryForm(mapping, form, request, response);	
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
	 * 儲存員工薪資資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataSAL(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF010106M0F Form = (EHF010106M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataSAL");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			
			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				" UPDATE EHF030200t0 SET " +
				" EHF030200T0_03=?, EHF030200T0_04=?, EHF030200T0_05=?, EHF030200T0_06=?, " +
				" EHF030200T0_06_AC=?, EHF030200T0_07=?, EHF030200T0_08=?, EHF030200T0_10=?, EHF030200T0_12=?, " +
				" EHF030200T0_14=?, EHF030200T0_14_TYPE=?, EHF030200T0_15=?, EHF030200T0_16=?, " +
				//" EHF030200T0_17=?, " +////修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
				" EHF030200T0_18=?, EHF030200T0_19=?, EHF030200T0_20=?, " +
				" EHF030200T0_21=?, " +
				" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF030200T0_01 = '"+Form.getHR_EmployeeSysNo()+"' " +  //員工代號
				" AND EHF030200T0_13 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, Form.getEHF030200T0_03()==null?"01":Form.getEHF030200T0_03());  //員工類別
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030200T0_04());  //基本薪資
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_05());  //薪資發放方式
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_06()==null?"":Form.getEHF030200T0_06());  //銀行代號
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_06_AC()==null?"":Form.getEHF030200T0_06_AC());  //金融帳號
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_07());  //薪資計算方式
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF030200T0_08()));  //是否啟用
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_10()==null?"50":Form.getEHF030200T0_10());  //各類所得代號
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_12());  //備註
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF030200T0_14()));  //是否有代扣所得稅
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_14_TYPE()==null?"":Form.getEHF030200T0_14_TYPE());  //代扣類型
				indexid++;
				p6stmt.setFloat(indexid, Form.getEHF030200T0_15());  //代扣率
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030200T0_16());  //代扣金額
				indexid++;
				////修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
				//p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF030200T0_17()));  //是否計算考勤
				//indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_18());  //加班費規則
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030200T0_19());  //全勤獎金金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030200T0_20());  //全勤獎金扣費規則
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF030200T0_21()));  //是否代扣福利金
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
				
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				generateSelectBox(conn, Form, request);
				request.setAttribute("mode", "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("MSG_EDIT", request.getAttribute("ErrMSG"));
				
				return mapping.findForward("success");
			}
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","員工薪資基本資料儲存錯誤!!請重新操作!!");															
	        e.printStackTrace();
	        return queryForm(mapping, form, request, response);	
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
	 * 儲存員工保險資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataINS(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF010106M0F Form = (EHF010106M0F) form;
		Connection conn = null;
		AuthorizedBean authbean = getLoginUser(request);
		/*EMS_InsuranceTools ems_insurance_tools = new EMS_InsuranceTools("", "", authbean.getUserId(), authbean.getUserName(),authbean.getCompId());*/
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataINS");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			
			if (lc_errors.isEmpty()) {
				
				String sql = "" +
				" UPDATE ehf030300t0 SET EHF030300T0_05=? ,EHF030300T0_05_VERSION=?,EHF030300T0_06=? " +
				" ,EHF030300T0_07=? ,EHF030300T0_07_VERSION=?,EHF030300T0_08=? ,EHF030300T0_09=? ,EHF030300T0_10=? ,EHF030300T0_11=? ,EHF030300T0_12=? " +
				" ,EHF030300T0_15=? ,EHF030300T0_16=?, EHF030300T0_05_START=?, EHF030300T0_05_END=?, EHF030300T0_07_START=?, EHF030300T0_07_END=? " +
				" ,USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
				" WHERE EHF030300T0_01 = '"+Form.getHR_EmployeeSysNo()+"' " +
				" AND EHF030300T0_14 = '"+getLoginUser(request).getCompId()+"' ";

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				p6stmt.setString(indexid, Form.getEHF030300T0_05());  //勞保投保等級
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030300T0_05_NUMBER());  //勞保設定檔編號 修改BY賴泓錡 20140120
				indexid++;
								
				//計算勞保費用
				/*int labor_fee = ems_insurance_tools.countEmpLaborFee(conn, Form.getEHF030300T0_01(), Form.getEHF030300T0_03(),Integer.valueOf(Form.getEHF030300T0_07_HIDE()), 30);*/
				Form.setEHF030300T0_06(0); //勞保員工負擔金額
				
				p6stmt.setInt(indexid, Form.getEHF030300T0_06());  //勞保員工負擔金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030300T0_07());  //健保投保等級
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030300T0_07_NUMBER());  //健保設定檔編號 修改BY賴泓錡 20140120
				indexid++;
												
				//計算健保費用
				/*int health_fee = ems_insurance_tools.countEmpHealthFee(conn,  Form.getEHF030300T0_01(),  Form.getEHF030300T0_03(),Integer.valueOf(Form.getEHF030300T0_05_HIDE()), 0, 0);*/
				//前端顯示金額改用系統計算
				Form.setEHF030300T0_08(0); //健保員工負擔金額
					
				p6stmt.setInt(indexid, Form.getEHF030300T0_08());  //健保員工負擔金額
				indexid++;

				//計算健保費用
				/*health_fee = ems_insurance_tools.countEmpHealthFee(conn, Form.getEHF030300T0_01(), Form.getEHF030300T0_03(),
							Integer.valueOf(Form.getEHF030300T0_07_HIDE()), Form.getEHF030300T0_11() - 1,Form.getEHF030300T0_10());*/
				
				
				p6stmt.setInt(indexid, 0);  //勞健保總金額
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030300T0_10());  //地方政府補助款
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030300T0_11());  //口數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030300T0_12()==null?"":Form.getEHF030300T0_12());  //備註
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030300T0_15());  //勞退自提金額
				indexid++;
				p6stmt.setInt(indexid, Form.getEHF030300T0_16());  //勞退公提金額
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030300T0_05_START());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030300T0_05_END()==""?"0000-00-00":Form.getEHF030300T0_05_END());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030300T0_07_START());  //
				indexid++;
				p6stmt.setString(indexid, Form.getEHF030300T0_07_END()==""?"0000-00-00":Form.getEHF030300T0_07_END());  //
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());  //修改人員
				indexid++;
												
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				
				request.setAttribute("mode", "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				request.setAttribute("MSG_EDIT", request.getAttribute("ErrMSG"));
				
				return mapping.findForward("success");
			}
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","保險基本資料設定儲存錯誤!!請重新操作!!");															
	        e.printStackTrace();
	        return queryForm(mapping, form, request, response);	
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
	 * 上傳照片
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward archive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		EHF010106M0F Form = (EHF010106M0F) form;
		EMS_DB_CONTROLLER ems_db_c = null;
		Connection conn = null;
		String pictureName = "";
		//檢查上傳檔案是否存在
		try{
			if("".equals(Form.getUPLOADFILE().getFileName()) || Form.getUPLOADFILE().getFileName() == null || Form.getUPLOADFILE().getFileSize() <= 0 ){
				request.setAttribute("MSG_EDIT", "檔案上傳發生錯誤，請重新選取附加檔案!!");
				
				Form.setEHF010106T0_23("pic\\No_picture.png");
				request.setAttribute("button", "query");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
						
		//建立資料庫連線
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
			conn=ems_db_c.getConn();
			
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}

		try{
			String name[] = Form.getUPLOADFILE().getFileName().toString().split("\\.");
			
			
			//當檔名遇到多個小數點時，需取得最後一個點之前的檔名，當作要存檔的名稱
			//EX：如要將圖片檔名如右2014.05.06.bmp取出來為2014.05.06
			if(name.length>2){
				for(int i=0;i<name.length-1;i++){
					pictureName+=name[i];
					if(i!=name.length-2){
						pictureName+=".";
					}
				}
			}else{
				pictureName+=name[0];
			}
			
			File file_NAME = new File(getServlet().getServletContext().getRealPath("/")+"\\config\\pic\\"+pictureName+".png");    
			if(file_NAME.exists())    
			{    
				request.setAttribute("MSG_EDIT", "已有相同檔名的圖片，無法上傳。");
				
				
				request.setAttribute("button", "query");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");  
			}

			//上傳檔案
			BufferedImage input = ImageIO.read(Form.getUPLOADFILE().getInputStream());
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(input, "png", new File(getServlet().getServletContext().getRealPath("/")+"\\config\\pic\\"+pictureName+".png"));
			
			
			
			
			
			File file =new File("C:\\pictureBackup");    
			//如果資料夾不存在，則創建。
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    //System.out.println("//不存在");  
			    file .mkdir();    
			} else   
			{  
			    //System.out.println("//資料夾存在");  
			} 
			//同時備份到C：\pictureBackup 避免 WAR檔重新佈署，造成圖片檔消失
			ImageIO.write(input, "png", new File("C:\\pictureBackup\\"+pictureName+".png"));
			
			
			//更新資料庫檔案名稱
			String sql = "" +
				" UPDATE EHF010106T0 SET EHF010106T0_23=?, USER_UPDATE=?, VERSION=VERSION+1, HR_LastUpdateDate=NOW() " +
				" WHERE HR_EmployeeSysNo = '"+Form.getHR_EmployeeSysNo()+"' ";
				
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, "pic\\"+pictureName+".png");  //附加檔案路徑
			indexid++;
			p6stmt.setString(indexid, getLoginUser(request).getUserName() );  //修改人員
			indexid++;
				
			//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
				
			conn.commit();
			
			request.setAttribute("button_init", "");
			request.setAttribute("button", "query");
			request.setAttribute("MSG_EDIT", "附加檔案上傳成功!!");
			
			if(this.chk_Picture(getServlet().getServletContext().getRealPath("/")+"\\config\\pic\\"+pictureName+".png")){
				//Form.setPicture_Name("pic\\"+name[0]+".png");
				Form.setEHF010106T0_23("pic\\"+pictureName+".png");
			}else{	
				//Form.setPicture_Name("pic\\No_picture.png");
				Form.setEHF010106T0_23("pic\\No_picture.png");
			}
		}catch (Exception e) {
			request.setAttribute("MSG_EDIT", "附加檔案上傳失敗!");
			System.out.println("FILEUPLOAD.uploadEHF010106M1() " + e);
			e.printStackTrace();
			//Form.setPicture_Name("pic\\No_picture.png");
			Form.setEHF010106T0_23("pic\\No_picture.png");
			request.setAttribute("button", "query");
			
			
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
		
		request.setAttribute("Form1Datas", Form);
		return queryForm(mapping,form,request,response);
	}
	
	
	/**
	 * 檢查圖片是否存在
	 * @param picture_Path
	 * @return
	 */
	protected boolean chk_Picture(String picture_Path){
		boolean chk_flag = false;
		File file = new File(picture_Path);
		if (file.exists()) {
			chk_flag=true;
		}
		return chk_flag;
	}

}
