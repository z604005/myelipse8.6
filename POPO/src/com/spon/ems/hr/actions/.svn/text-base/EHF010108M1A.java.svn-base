package com.spon.ems.hr.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.hr.forms.EHF010108M0F;
import com.spon.ems.hr.forms.EHF010108M0F;
import com.spon.ems.hr.forms.EHF010108M0F;
import com.spon.ems.hr.models.EHF010108;
import com.spon.ems.hr.models.EHF010108;
import com.spon.ems.hr.models.EHF010108;
import com.sun.org.apache.commons.beanutils.BeanUtils;
/**
 * 部門資料查詢
 * @author maybe
 *
 */
public class EHF010108M1A extends EditAction{

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		
		boolean chk_flag = false;
		EHF010108M0F Form = (EHF010108M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010108元件
			EHF010108 EHF010108 = new EHF010108(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			
			dataMap.put("HR_DepartmentNo", Form.getHR_DepartmentNo());	//本層部門代號
			dataMap.put("HR_DepartmentName", Form.getHR_DepartmentName());	//本層部門名稱
			dataMap.put("HR_UpDepartmentSysNo", Form.getHR_UpDepartmentSysNo());	//上層部門系統代號
			dataMap.put("HR_UpDepartmentName", Form.getHR_UpDepartmentName());	//上層部門名稱
			dataMap.put("EHF010108T0_01", Form.getEHF010108T0_01());	//部門簡稱
			dataMap.put("EHF010108T0_02", Form.getEHF010108T0_02());	//成立日期
			dataMap.put("EHF010108T0_03", Form.getEHF010108T0_03());	//結束日期
			dataMap.put("EHF010108T0_04", Form.getEHF010108T0_04());	//部門簡介
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			EHF010108.addData(dataMap);
			
			//關閉EHF010108元件
			EHF010108.close();
			
			//取出KEY_ID
			Form.setHR_DepartmentSysNo((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getHR_DepartmentSysNo());
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
		EHF010108M0F Form = (EHF010108M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010108元件
			EHF010108 EHF010108 = new EHF010108();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("HR_DepartmentSysNo", key[0]);  //本層部門系統代號
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = EHF010108.queryEditData(queryCondMap);
			
			//建立下轄部門清單
			List EHF010108T0_LIST = EHF010108.queryEHF010108T0List(queryCondMap);
			
			//關閉EHF010108元件
			EHF010108.close();
			
			if(!dataMap.isEmpty()){

				BeanUtils.copyProperties(Form, dataMap);
				
				Form.setEHF010108T0_LIST(EHF010108T0_LIST);
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
		EHF010108M0F Form = (EHF010108M0F) form;
		
		try{
			//建立EHF010108元件
			EHF010108 ehf010108 = new EHF010108();
			
			//建立資料Map
			Map dataMap = new HashMap();
			
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf010108.saveData(dataMap);
			
			//關閉EHF010108元件
			ehf010108.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getHR_DepartmentSysNo());
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
		
		EHF010108M0F Form = (EHF010108M0F) form;
		
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getHR_DepartmentSysNo());
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
		
		EHF010108M0F Form = (EHF010108M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getHR_DepartmentSysNo());
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

	/*@Override
	public boolean executeDelDetailData(Connection conn, String type,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF010108M0F Form = new EHF010108M0F();
		boolean chk_flag = false;
		
		try{
			EHF010108 ehf010108 = new EHF010108();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			ehf010108.delDetailData(type,detailDataMap);
			
			ehf010108.close();
			
			//設定Query Key
			String[] key_id = new String[1];			
			key_id[0] = (String)detailDataMap.get("HR_DepartmentSysNo");
			paramMap.put(super.KEY_ID, key_id);
									
			chk_flag = true;
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}*/

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		
		// TODO Auto-generated method stub
		EHF010108M0F Form = new EHF010108M0F();
		
		List list = new ArrayList();
		
		Form.setEHF010108T0_LIST(list);
		
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
