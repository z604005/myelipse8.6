package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.popo.forms.EHF320300M0F;
import com.spon.ems.popo.forms.EHF320300M0F;
import com.spon.ems.popo.models.EHF320300;
import com.spon.ems.popo.models.EHF320300;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * (Action)餐點預排管理
 */
public class EHF320300M1A extends EditAction {
	
	private BA_TOOLS tool;
	
	public EHF320300M1A()
	{
		tool = BA_TOOLS.getInstance();
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
		EHF320300M0F Form = (EHF320300M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF320300元件
			EHF320300 ehf320300 = new EHF320300(comp_id);
			
			//String HR_JobNameSysNo = EMS_GetCodeRules.getInstance().getCodeRule("", comp_id);
//			String EHF320300T0_01 = tools.createJOBUID(conn, "JOB", "EHF320300T0", "EHF320300T0_01", comp_id);
			String EHF320300T0_01 = tools.createEMSUID(conn, "PA", "EHF320300T0", "EHF320300T0_01", comp_id);
			
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form); //把Fome 放到Map裡面, 等同於下面的東西。
			dataMap.put("EHF320300T0_01", EHF320300T0_01);
			dataMap.put("EHF320300T0_02", Form.getEHF320300T0_02());
			dataMap.put("EHF320300T0_03", Form.getEHF320300T0_03());

			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf320300.addData(dataMap);
			
			//自動帶入明細			
			ehf320300.SelectImportDetailData(dataMap);
			
			//關閉EHF320300元件
			ehf320300.close();
			
			//取出KEY_ID
			Form.setEHF320300T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF320300T0_01());
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
		EHF320300M0F Form = (EHF320300M0F) form;
		EHF320300M0F bean = null;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010109元件
			EHF320300 ehf320300 = new EHF320300();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
//			System.out.println(queryCondMap.get("EHF320300T0_01"));
			queryCondMap.put("EHF320300T0_01", key[0]);  
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf320300.queryEditData(queryCondMap);
			
			//建立空清單
			List<EHF320300M0F> queryDetaillist = new ArrayList<EHF320300M0F>();
			
			//取得List  //要改
			//Form.setEHF320300_DETAIL(ehf320300.queryDetailList(queryCondMap,comp_id));
			//使用元件進行處理-取出list
			List ehf320300_queryDetailList = ehf320300.queryDetailList(queryCondMap,comp_id);
			
			//建立空清單
			List<EHF320300M0F> querydetaillist = new ArrayList<EHF320300M0F>();
			
			Iterator it = ehf320300_queryDetailList.iterator();
			Map tempMap = null;
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF320300M0F();
				
				
				bean.setEHF320300T1_01((String)tempMap.get("EHF320300T1_01"));	//系統編號
				bean.setEHF320300T1_02(String.valueOf((Integer)tempMap.get("EHF320300T1_02")));	//項次
				bean.setEHF320300T1_03(Form.getITEM_VALUE((String)tempMap.get("EHF320300T1_03"), "MENU_MEAL", comp_id));	//
				bean.setEHF320300T1_04(Form.getITEM_VALUE((String)tempMap.get("EHF320300T1_04"), "MENU_TYPE", comp_id));	//
				bean.setEHF320300T1_05(ehf320300.getVegetables((String)tempMap.get("EHF320300T1_05"), comp_id));	//
				
//				bean.setEHF320300T1_03(Integer.toString((Integer)tempMap.get("EHF320300T1_03")));	//上菜順序天
				
				querydetaillist.add(bean);
			}
			
			//設定querylist
			Form.setEHF320300T0_LIST(querydetaillist);
			
			//把資料傳出畫面
			Form.setEHF320300_DETAIL(querydetaillist);
			
			//關閉EHF010109元件
			ehf320300.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setEHF320300T0_01((String)dataMap.get("EHF320300T0_01"));  //系統代碼
				Form.setEHF320300T0_02((String)dataMap.get("EHF320300T0_02"));  //日期
				Form.setEHF320300T0_03(Integer.toString((Integer)dataMap.get("EHF320300T0_03")));  //上菜順序天
				Form.setEHF320300T0_04((String) dataMap.get("EHF320300T0_04"));  //公司代碼
				//Create Information
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				//Form.setDATE_UPDATE(ems_tools.convertADDatetimeToStrng((Date)dataMap.get("DATE_UPDATE")));//修改日期
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				
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
	
	protected void QueryDetailData(Connection conn, ActionForm form, Map paramMap) {
		EHF320300M0F Form = (EHF320300M0F) form;
		EHF320300M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		try{
			
			//建立EHF010109元件
			EHF320300 ehf320300 = new EHF320300();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			//取得所有已填欄位資料
			//queryCondMap = BeanUtils.describe(Form);
			//取得菜單系統編號
			//queryCondMap.put("EHF320300T1_01", Form.getEHF320300T0_01());
			//取得使用者資訊
			//queryCondMap.putAll(paramMap);
			
			//建立空清單
			List<EHF320300M0F> querydetaillist = new ArrayList<EHF320300M0F>();
			
			//使用元件進行處理-直接帶入list
//			Form.setEHF320300_DETAIL(ehf320300.queryDetailList(queryCondMap,comp_id));
			
			//建立空清單
			List<EHF320300M0F> queryDetaillist = new ArrayList<EHF320300M0F>();
			
			//使用元件進行處理-取出list
			List ehf320300_queryDetailList = ehf320300.queryDetailList(queryCondMap,comp_id);
			
			Iterator it = ehf320300_queryDetailList.iterator();
			Map tempMap = null;
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF320300M0F();
				
				
				bean.setEHF320300T1_01((String)tempMap.get("EHF320300T1_01"));	//系統編號
				bean.setEHF320300T1_02(String.valueOf((Integer)tempMap.get("EHF320300T1_02")));	//項次
				bean.setEHF320300T1_03(ehf320300.getITEM_VALUE((String)tempMap.get("EHF320300T1_03"), "MENU_MEAL", comp_id));	//
				bean.setEHF320300T1_04(Form.getITEM_VALUE((String)tempMap.get("EHF320300T1_04"), "MENU_TYPE", comp_id));	//
				bean.setEHF320300T1_05(ehf320300.getVegetables((String)tempMap.get("EHF320300T1_05"), comp_id));	//
				
//				bean.setEHF320300T1_03(Integer.toString((Integer)tempMap.get("EHF320300T1_03")));	//上菜順序天
				
				querydetaillist.add(bean);
			}
			
			//設定querylist
			Form.setEHF320300T0_LIST(querydetaillist);
			
			//把資料傳出畫面
			Form.setEHF320300_DETAIL(querydetaillist);
			
			
			//關閉EHF010109元件
			ehf320300.close();
			}catch(Exception e){
				//記錄錯誤訊息
				this.handleException(e, paramMap);
				e.printStackTrace();
			}
		
		
		
		
	}
/*
	protected String getVegetables(String EHF320100T0_01, String comp_id){
		
		String Vegetables = null;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
			
		try{
				
			String sql = "" +
			" SELECT EHF320100T0_03 " +
			" FROM EHF320100T0 " +
			" WHERE 1=1 " +
			" AND EHF320100T0_01 = '"+EHF320100T0_01+"' " +
			" AND EHF320100T0_10 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			if(rs.next()){
				Vegetables = rs.getString("ITEM_VALUE");
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return Vegetables;
		
	}*/
	
	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF320300M0F Form = (EHF320300M0F) form;
		
		try{
			//建立EHF010109元件
			EHF320300 ehf320300 = new EHF320300();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap.put("EHF320300T0_01", Form.getEHF320300T0_01());
			dataMap.put("EHF320300T0_02", Form.getEHF320300T0_02());
			dataMap.put("EHF320300T0_03", Form.getEHF320300T0_03());
			

			//dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf320300.saveData(dataMap);
			
			//關閉EHF010109元件
			ehf320300.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF320300T0_01());
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
		
		EHF320300M0F Form = (EHF320300M0F) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = (Form.getEHF320300T0_01());
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
		
		EHF320300M0F Form = (EHF320300M0F) form;
		
		String key = "";
		
		try{			
			key = Form.getEHF320300T0_01();
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
		
		EHF320300M0F Form = (EHF320300M0F) form;
		
		List list = new ArrayList();
		
		Form.setEHF320300_DETAIL(list);
		
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
			String comp_id = (String) request.getAttribute("compid");
			//("欄位名稱", tools.getOptions(conn, true, "類別代碼", "公司編號" ))
			request.setAttribute("listEHF320300T0_03", tools.getOptions(conn, true, "DAY", comp_id ));//天數
			request.setAttribute("listEHF320300T1_03", tools.getOptions(conn, true, "MENU_MEAL", comp_id ));//菜單餐別
			request.setAttribute("listEHF320300T1_04", tools.getOptions(conn, true, "MENU_TYPE", comp_id ));//菜單類別
		}catch(Exception e) {
			System.out.println(e);
		}

	}
	

	
	@Override
	public boolean executeAddDetailData(Connection conn, String type,
			ActionForm form, Map paramMap) {
		boolean chk_flag = false;
		EHF320300M0F Form = (EHF320300M0F) form;
	
		try{
			//設定EHF320300 細項 新增語句
			//建立EHF320300元件
			EHF320300 EHF320300 = new EHF320300();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			detailDataMap.put("EHF320300T1_01", Form.getEHF320300T0_01());

			
			//新增訂單單明細資料
			EHF320300.addDetailData(type, detailDataMap);
			
			//關閉EHF320300元件
			EHF320300.close();
			
			//取出KEY_ID
			Form.setEHF320300T1_01((String)detailDataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF320300T1_01");
			paramMap.put(super.KEY_ID, key_id);
						
			//新增明細完成
			chk_flag = true;
			this.cleanAddField(Form);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return chk_flag;
		
	}

	protected boolean executeAddDetailData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF320300M0F Form = (EHF320300M0F) form;
	
		try{
			//設定EHF320300 細項 新增語句
			//建立EHF320300元件
			EHF320300 EHF320300 = new EHF320300();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			paramMap.put("EHF320300T1_01", Form.getEHF320300T0_01());
			
			//新增訂單單明細資料
			EHF320300.addDetailData("addEHF320300T1", detailDataMap);
			
			//關閉EHF320300元件
			EHF320300.close();
			
			//取出KEY_ID
			Form.setEHF320300T1_01((String)detailDataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF320300T1_01");
			paramMap.put(super.KEY_ID, key_id);
						
			//新增明細完成
			chk_flag = true;
			this.cleanAddField(Form);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return chk_flag;
	}
	
	
	@Override
	protected boolean executeDelDetailData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		EHF320300M0F Form = (EHF320300M0F) form;
		
		try{
			//建立EHF320300元件
			EHF320300 EHF320300 = new EHF320300(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF320300T1_01", paramMap.get("EHF320300T1_01"));
			dataMap.put("EHF320300T1_02", paramMap.get("EHF320300T1_02"));
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行刪除FOODT0功能
			EHF320300.delDetailData("delone", dataMap);
			
			//關閉EHF320300元件
			EHF320300.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)dataMap.get("EHF320300T1_01");
			Form.setEHF320300T1_01((String)dataMap.get("EHF320300T1_01"));
			paramMap.put(super.KEY_ID, key_id);
			
			chk_flag = true;
			
			//清除新增欄位的內容
			this.cleanAddField(form);
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return chk_flag;
	}
	

	@Override
	protected Map handleDelDetailKey(String[] key, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF320300M0F Form = (EHF320300M0F) form;
		Map return_map = new HashMap();
		
		
		try{
			//paramMap.put = BeanUtils.describe(Form);
			//把T1_01丟回T0_01給於查詢之用。
			paramMap.put("EHF320300T0_01", Form.getEHF320300T1_01());
			paramMap.put("EHF320300T1_01", Form.getEHF320300T1_01());
			paramMap.put("EHF320300T1_02", Form.getEHF320300T1_02());
			return_map.put("CHK_FLAG", true);
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}		
		return return_map;
	}


}
