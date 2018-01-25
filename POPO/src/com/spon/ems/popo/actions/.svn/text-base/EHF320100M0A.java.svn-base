package com.spon.ems.popo.actions;

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
import com.spon.ems.popo.forms.EHF320100M0F;
import com.spon.ems.popo.models.EHF320100;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 菜單管理設定
 * (Action)
 * @author maybe
 * @version 1.0
 * @created 下午3:05:28
 */
public class EHF320100M0A extends QueryAction {
	
	private BA_TOOLS tools;
	
	public EHF320100M0A(){
		tools = BA_TOOLS.getInstance();
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			EHF320100 ehf320100 = new EHF320100(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF320100T0_01", key[0]);  //系統代碼
			dataMap.put("COMP_ID", comp_id);
			
			//執行EHF320100刪除功能
			ehf320100.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			
			ehf320100.close();
			
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
		
		EHF320100M0F Form = new EHF320100M0F();
		
		try{
			List list = new ArrayList();
			
			Form.setEHF320100T0_LIST(list);
			
		}catch(Exception e){
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
		EHF320100M0F Form = (EHF320100M0F) form;
		Map return_map = new HashMap();
		EHF320100M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			List<EHF320100M0F> querylist = new ArrayList<EHF320100M0F>();
			
			EHF320100 ehf320100 = new EHF320100(comp_id);
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf320100_queryList = ehf320100.queryData(queryCondMap);
			
			Iterator it = ehf320100_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF320100M0F();
				
				bean.setEHF320100T0_01((String)tempMap.get("EHF320100T0_01"));
				bean.setEHF320100T0_02((String)tempMap.get("EHF320100T0_02"));
				bean.setEHF320100T0_03((String)tempMap.get("EHF320100T0_03"));
				bean.setEHF320100T0_04_TXT((String)tempMap.get("EHF320100T0_04_TXT"));
				bean.setEHF320100T0_05_TXT((String)tempMap.get("EHF320100T0_05_TXT"));
				bean.setEHF320100T0_06_TXT((String)tempMap.get("EHF320100T0_06_TXT"));
				bean.setEHF320100T0_07_TXT((String)tempMap.get("EHF320100T0_07_TXT"));
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF320100T0_LIST(querylist);
			
			ehf320100.close();
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			//清空
			
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
		
		request.setAttribute("listEHF320100T0_04", tools.getOptions(conn, true, "MENU_MEAL", getLoginUser(request).getCompId()));//菜單餐別
		request.setAttribute("listEHF320100T0_05", tools.getOptions(conn, true, "MENU_TYPE", getLoginUser(request).getCompId()));//菜單類別

	}

}
