package com.spon.utils.orgtree.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.abs.actions.EditAction;
import com.spon.utils.orgtree.forms.EMS_OrgTree_ConfigM1F;
import com.spon.utils.orgtree.models.EMS_OrgTree;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action> 組織樹參數設定作業  2014/10/03 整理  By Alvin
 */
public class EMS_OrgTree_ConfigM1A extends EditAction{

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,Map paramMap) {
		// TODO Auto-generated method stub
		boolean chk_flag = false;
		
		return chk_flag;
	}

	@Override
	protected Map executeQueryEditData(Connection conn, String[] key,ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		boolean chk_flag = false;
		EMS_OrgTree_ConfigM1F Form = (EMS_OrgTree_ConfigM1F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		Map return_map = new HashMap();
		
		try{
			//建立元件
			EMS_OrgTree ems_OrgTree = new EMS_OrgTree(comp_id);
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("COMP_ID", comp_id); 
			queryCondMap.putAll(paramMap); 
			
			Map dataMap = ems_OrgTree.queryOrgTreeConfig(queryCondMap);
			
			ems_OrgTree.close();
			if(!dataMap.isEmpty()){
				
				BeanUtils.copyProperties(Form, dataMap);
			
		    chk_flag = true;
			}

			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			this.cleanAddField(Form);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}finally {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return return_map;
	}

	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form,
			Map paramMap) {
		EMS_OrgTree_ConfigM1F Form = (EMS_OrgTree_ConfigM1F) form;
		EMS_OrgTree orgtree = new EMS_OrgTree();
		
		try{
			Map queryCondMap = new HashMap();			
			//處理條件設定
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", (String)paramMap.get("COMP_ID"));  //公司代碼
			
			orgtree.saveOrgTreeConfig(queryCondMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		orgtree.close();
		
		//Return資料
//		return_map.put("CHK_FLAG", chk_flag);
		
		return false;
	}

	@Override
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
	}

	@Override
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,Map paramMap) {
//		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		EMS_OrgTree_ConfigM1F Form = (EMS_OrgTree_ConfigM1F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		Map return_map = new HashMap();
		
		try{
			//建立元件
			EMS_OrgTree ems_OrgTree = new EMS_OrgTree(comp_id);
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("COMP_ID", comp_id); 
			queryCondMap.putAll(paramMap); 
			
			Map dataMap = ems_OrgTree.queryOrgTreeConfig(queryCondMap);
			
			ems_OrgTree.close();
			if(!dataMap.isEmpty()){
				
				BeanUtils.copyProperties(Form, dataMap);
			
			}

		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}finally {
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return Form;
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
	
	
	/**
	 * 新增明細
	 */
	public boolean executeAddDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub

		boolean chk_flag = false;
		
		return chk_flag;
	}

	/**
	 *刪除明細 
	 */
	public boolean executeDelDetailData(Connection conn, String type,ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub

		boolean chk_flag = false;

		return chk_flag;
	}
	

}
