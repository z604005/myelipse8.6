package com.spon.flow.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.flow.forms.SFlow_SiteF;
import com.spon.flow.models.SFLOW_SITE;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 *@author maybe
 *@version 1.0
 *@created 2016/12/22 上午10:45:16
 */
public class SFLOW_SITEM0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			SFLOW_SITE sflow = new SFLOW_SITE(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("SFLOW_SITE_HD_01", key[0]);  //
			dataMap.put("COMP_ID", comp_id);
			
			//執行SFLOW_SITE刪除功能
			sflow.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			
			sflow.close();
			
			//控制刪除後的顯示畫面
			paramMap.put(super.DEL_FORWARD_CONFIG, super.FORWARD_INIT);
			
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
		
		SFlow_SiteF Form = new SFlow_SiteF();
		List list = new ArrayList();
		
		try{
			Form.setSFLOW_SITE_LIST(list);
			
		}catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		SFlow_SiteF Form = (SFlow_SiteF) form;
		Map return_map = new HashMap();
		SFlow_SiteF bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<SFlow_SiteF> querylist = new ArrayList<SFlow_SiteF>();
			
			SFLOW_SITE sflow = new SFLOW_SITE(comp_id);
			
			//建立SFLOW_SITEM0A 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List sflow_queryList = sflow.queryData(queryCondMap);
			
			Iterator it = sflow_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new SFlow_SiteF();
				
				bean.setSFLOW_SITE_HD_01((String)tempMap.get("SFLOW_SITE_HD_01"));	//
				bean.setSFLOW_SITE_HD_02((String)tempMap.get("SFLOW_SITE_HD_02"));
				
				querylist.add(bean);
				
			}
			
			//設定querylist
			Form.setSFLOW_SITE_LIST(querylist);
			
			sflow.close();
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			this.cleanAddField(form);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}

	private void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		
		SFlow_SiteF Form = (SFlow_SiteF) form;
		
		Form.setSFLOW_SITE_HD_01("");
		Form.setSFLOW_SITE_HD_02("");
		
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
