package com.spon.ems.hr.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class EHF010109M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF010109元件
			EHF010109 ehf010109 = new EHF010109(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("HR_JobNameSysNo", key[0]);  //職務系統代碼
			dataMap.put("COMP_ID", comp_id);
			
			boolean delFlag = ehf010109.selectEHF010106T6(key[0], "", comp_id);
			
			if(delFlag){
				request.setAttribute("ERR_MSG", "此職務使用中，請勿刪除!!");
			}else{
				//執行EHF010109刪除功能
				ehf010109.delData(dataMap);
				
				if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
					chk_flag = true;
				}
			}
			
			//關閉EHF010109元件
			ehf010109.close();
			
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
		
		EHF010109M0F Form = new EHF010109M0F();
		
		List list = new ArrayList();
		
		Form.setEHF010109T0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF010109M0F Form = (EHF010109M0F) form;
		Map return_map = new HashMap();
		EHF010109M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF010109M0F> querylist = new ArrayList<EHF010109M0F>();
			
			//建立EHF010109元件
			EHF010109 ehf010109 = new EHF010109(comp_id);
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf010109_queryList = ehf010109.queryData(queryCondMap);
			
			Iterator it = ehf010109_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF010109M0F();
				
				bean.setHR_JobNameSysNo((String)tempMap.get("HR_JobNameSysNo"));	//職務系統代碼
				bean.setHR_JobNameNo((String)tempMap.get("HR_JobNameNo"));	//職務顯示代碼
				bean.setHR_JobName((String)tempMap.get("HR_JobName"));	//職務名稱
				bean.setEHF010109T0_01((Boolean)tempMap.get("EHF010109T0_01"));	//使用狀況
				bean.setEHF010109T0_02((String)tempMap.get("EHF010109T0_02"));	//備註
				
				if((Boolean)tempMap.get("EHF010109T0_01")){
					bean.setEHF010109T0_01_TXT("啟用");
				}else{
					bean.setEHF010109T0_01_TXT("停用");
				}
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF010109T0_LIST(querylist);
			
			//關閉EHF010109元件
			ehf010109.close();
			
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
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

	}

}
