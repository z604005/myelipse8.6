package com.spon.ems.com.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.com.forms.EHF000300M0F;
import com.spon.ems.com.models.EHF000300;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * (Action)職務基本資料
 * @author maybe
 * @version 1.0
 * @created 2015/8/18 上午9:26:52
 */
public class EHF000300M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF000300元件
			EHF000300 ehf000300 = new EHF000300(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF000300T0_01", key[0]);  //職務系統代碼
			dataMap.put("COMP_ID", comp_id);
			
			boolean delFlag = ehf000300.selectEHF010100T6(key[0], "", comp_id);
			
			if(delFlag){
				request.setAttribute("ERR_MSG", "此職務使用中，請勿刪除!!");
			}else{
				//執行EHF000300刪除功能
				ehf000300.delData(dataMap);
				
				if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
					chk_flag = true;
				}
			}
			
			//關閉EHF000300元件
			ehf000300.close();
			
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
		
		EHF000300M0F Form = new EHF000300M0F();
		
		List list = new ArrayList();
		
		Form.setEHF000300T0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF000300M0F Form = (EHF000300M0F) form;
		Map return_map = new HashMap();
		EHF000300M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF000300M0F> querylist = new ArrayList<EHF000300M0F>();
			
			//建立EHF000300元件
			EHF000300 ehf000300 = new EHF000300(comp_id);
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf000300_queryList = ehf000300.queryData(queryCondMap);
			
			Iterator it = ehf000300_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF000300M0F();
				
				bean.setEHF000300T0_01((String)tempMap.get("EHF000300T0_01"));	//職務系統代碼
				bean.setEHF000300T0_02((String)tempMap.get("EHF000300T0_02"));	//職務顯示代碼
				bean.setEHF000300T0_03((String)tempMap.get("EHF000300T0_03"));	//職務名稱
				bean.setEHF000300T0_05((Boolean)tempMap.get("EHF000300T0_05"));	//使用狀況
				bean.setEHF000300T0_06((String)tempMap.get("EHF000300T0_06"));	//備註
				
				if((Boolean)tempMap.get("EHF000300T0_05")){
					bean.setEHF000300T0_05_TXT("啟用");
				}else{
					bean.setEHF000300T0_05_TXT("停用");
				}
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF000300T0_LIST(querylist);
			
			//關閉EHF010109元件
			ehf000300.close();
			
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
