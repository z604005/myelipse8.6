package com.spon.ems.com.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.com.forms.EHF000200M0F;
import com.spon.ems.com.models.EHF000200;
import com.spon.ems.hr.forms.EHF010108M0F;
import com.spon.ems.hr.models.EHF010108;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * (Action)部門基本資料
 * @author maybe
 * @version 1.0
 * @created 2015/8/18 上午9:26:36
 */
public class EHF000200M1A extends EditAction {

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		
		boolean chk_flag = false;
		EHF000200M0F Form = (EHF000200M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010108元件
			EHF000200 ehf000200 = new EHF000200(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			
			dataMap.put("EHF000200T0_02", Form.getEHF000200T0_02());	//本層部門代號
			dataMap.put("EHF000200T0_03", Form.getEHF000200T0_03());	//本層部門名稱
			dataMap.put("EHF000200T0_04", Form.getEHF000200T0_04());	//上層部門系統代號
			dataMap.put("EHF000200T0_05", Form.getEHF000200T0_05());	//上層部門名稱
			dataMap.put("EHF000200T0_07", Form.getEHF000200T0_07());	//部門簡稱
			dataMap.put("EHF000200T0_08", Form.getEHF000200T0_08());	//成立日期
			dataMap.put("EHF000200T0_09", Form.getEHF000200T0_09());	//結束日期
			dataMap.put("EHF000200T0_10", Form.getEHF000200T0_10());	//部門簡介
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000200.addData(dataMap);
			
			//關閉EHF010108元件
			ehf000200.close();
			
			//取出KEY_ID
			Form.setEHF000200T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000200T0_01());
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
		
		boolean chk_flag = false;
		EHF000200M0F Form = (EHF000200M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010108元件
			EHF000200 ehf000200 = new EHF000200();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF000200T0_01", key[0]);  //本層部門系統代號
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf000200.queryEditData(queryCondMap);
			
			//建立下轄部門清單
			List EHF010108T0_LIST = ehf000200.queryEHF000200T0List(queryCondMap);
			
			//關閉EHF010108元件
			ehf000200.close();
			
			if(!dataMap.isEmpty()){

				BeanUtils.copyProperties(Form, dataMap);
				
				Form.setEHF000200T0_LIST(EHF010108T0_LIST);
				
				Form.setEHF000200T0_04	((String) dataMap.get("ONEHF000200T0_01")); //上層系統代碼(系統使用) 
				Form.setEHF000200T0_04_1((String) dataMap.get("ONEHF000200T0_02")); //上層部門代號(使用者自訂) 
				Form.setEHF000200T0_05	((String) dataMap.get("ONEHF000200T0_03")); //上層部門名稱
/*
				Form.setHR_DepartmentSysNo((String)dataMap.get("HR_DepartmentSysNo"));  //本層部門系統代號
				Form.setHR_DepartmentNo((String)dataMap.get("HR_DepartmentNo"));  //本層部門代號
				Form.setHR_DepartmentName((String) dataMap.get("HR_DepartmentName"));  //本層部門名稱
				Form.setHR_UpDepartmentSysNo((String) dataMap.get("HR_UpDepartmentSysNo"));  //上層部門系統代號
				Form.setHR_DepartmentLevel((String) dataMap.get("HR_DepartmentLevel"));  //層級
				Form.setEHF010108T0_01((String) dataMap.get("EHF010108T0_01"));  //部門簡稱
				Form.setEHF010108T0_02((String) dataMap.get("EHF010108T0_02"));  //成立日期
				Form.setEHF010108T0_03((String) dataMap.get("EHF010108T0_03"));  //結束日期
				Form.setEHF010108T0_04((String) dataMap.get("EHF010108T0_04"));  //部門簡介
				Form.setHR_CompanySysNo((String) dataMap.get("HR_CompanySysNo"));	//公司系統代碼
				
				//Create Information
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				//Form.setHR_LastUpdateDate(ems_tools.convertADDatetimeToStrng((Date)dataMap.get("HR_LastUpdateDate")));//修改日期
				Form.setHR_LastUpdateDate((String)dataMap.get("HR_LastUpdateDate"));
*/				
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
		
		boolean chk_flag = false;
		EHF000200M0F Form = (EHF000200M0F) form;
		
		try{
			//建立EHF010108元件
			EHF000200 ehf000200 = new EHF000200();
			
			//建立資料Map
			Map dataMap = new HashMap();
			
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000200.saveData(dataMap);
			
			//關閉EHF010108元件
			ehf000200.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000200T0_01());
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
		
		EHF000200M0F Form = (EHF000200M0F) form;
		
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000200T0_01());
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
		
		EHF000200M0F Form = (EHF000200M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF000200T0_01());
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
		
		EHF000200M0F Form = (EHF000200M0F) form;
		
		List list = new ArrayList();
		
		Form.setEHF000200T0_LIST(list);
		
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

}
