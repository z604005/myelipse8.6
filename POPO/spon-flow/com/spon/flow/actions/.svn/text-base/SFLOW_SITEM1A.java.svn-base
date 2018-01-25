package com.spon.flow.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.flow.forms.SFlow_SiteF;
import com.spon.flow.models.SFLOW_SITE;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 *@author maybe
 *@version 1.0
 *@created 2016/12/22 上午10:45:46
 */
public class SFLOW_SITEM1A extends EditAction {

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		
		SFlow_SiteF Form = (SFlow_SiteF) form;
		
		Form.setSFLOW_SITE_T0_02(0);
		Form.setSFLOW_SITE_T0_03("");
		Form.setSFLOW_SITE_T0_04("");
		Form.setSFLOW_SITE_T0_05("");
		Form.setSFLOW_SITE_T0_07("");
		Form.setSFLOW_SITE_T0_08("");

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		SFlow_SiteF Form = (SFlow_SiteF) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			SFLOW_SITE sflow = new SFLOW_SITE(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			sflow.addData(dataMap);
			
			sflow.close();
			
			//取出KEY_ID
			Form.setSFLOW_SITE_HD_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getSFLOW_SITE_HD_01());
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
	protected boolean executeAddDetailData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
				
		boolean chk_flag = false;
		SFlow_SiteF Form = (SFlow_SiteF) form;
		
		try{
			SFLOW_SITE sflow = new SFLOW_SITE();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			//新增明細資料
			sflow.addDetailData("SFLOW_SITE_T0", detailDataMap);
			
			sflow.close();
			
			//取出KEY_ID
			Form.setSFLOW_SITE_T0_01((String)detailDataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("SFLOW_SITE_T0_01");
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
	protected Map executeQueryEditData(Connection conn, String[] key,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		SFlow_SiteF Form = (SFlow_SiteF) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			SFLOW_SITE sflow = new SFLOW_SITE();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("SFLOW_SITE_HD_01", key[0]);  //FLOW編號
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = sflow.queryEditData(queryCondMap);
			
			//取得List
			Form.setSFLOW_SITE_T0_DETAIL(sflow.querySFLOW_SITE_T0_DETAILList(queryCondMap));
						
			sflow.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setSFLOW_SITE_HD_01((String)dataMap.get("SFLOW_SITE_HD_01"));  //FLOW編號
				Form.setSFLOW_SITE_HD_02((String)dataMap.get("SFLOW_SITE_HD_02"));  //FLOW名稱
				
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
		
		boolean chk_flag = false;
		SFlow_SiteF Form = (SFlow_SiteF) form;
		
		try{
			SFLOW_SITE sflow = new SFLOW_SITE();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			sflow.saveData(dataMap);
			
			sflow.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getSFLOW_SITE_HD_01());
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
		
		SFlow_SiteF Form = (SFlow_SiteF) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getSFLOW_SITE_HD_01());
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
		
		SFlow_SiteF Form = (SFlow_SiteF) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getSFLOW_SITE_HD_01());
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return key;
	}

	@Override
	protected Map handleDelDetailKey(String[] key, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		SFlow_SiteF Form = (SFlow_SiteF) form;
		Map return_map = new HashMap();
		
		try{
			paramMap.put("SFLOW_SITE_HD_01", Form.getSFLOW_SITE_T0_01());
			paramMap.put("SFLOW_SITE_T0_01", Form.getSFLOW_SITE_T0_01());
			paramMap.put("SFLOW_SITE_T0_02", Form.getSFLOW_SITE_T0_02());
			
			return_map.put("CHK_FLAG", true);
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
		return return_map;
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean executeDelDetailData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		SFlow_SiteF Form = (SFlow_SiteF) form;
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			SFLOW_SITE sflow = new SFLOW_SITE(comp_id);
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap.put("SFLOW_SITE_T0_01", paramMap.get("SFLOW_SITE_T0_01"));
			detailDataMap.put("SFLOW_SITE_T0_02", paramMap.get("SFLOW_SITE_T0_02"));
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
						
			//刪除明細
			//刪除明細資訊
			sflow.delDetailData("SFLOW_SITE_T0",detailDataMap);
			
			sflow.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("SFLOW_SITE_T0_01");
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

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		SFlow_SiteF Form = (SFlow_SiteF) form;
		
		try{
			
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
		
		//處理人員類型
		try{
			List SFLOW_SITE_T0_07_list = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			SFLOW_SITE_T0_07_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("指定人員");
			SFLOW_SITE_T0_07_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("指定欄位");
			SFLOW_SITE_T0_07_list.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("指定組織");
			SFLOW_SITE_T0_07_list.add(bform);
			request.setAttribute("SFLOW_SITE_T0_07_list", SFLOW_SITE_T0_07_list);
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}
