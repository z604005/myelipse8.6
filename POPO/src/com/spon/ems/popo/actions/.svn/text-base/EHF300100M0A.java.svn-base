package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF300100M0F;
import com.spon.ems.popo.models.EHF300100;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * (Action)路線資料
 */
public class EHF300100M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF300100元件
			EHF300100 ehf300100 = new EHF300100(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF300100T0_01", key[0]);  //路線代碼
			dataMap.put("COMP_ID", comp_id);
						
			//執行EHF300100刪除功能
			ehf300100.delData(dataMap);
				
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
						
			//關閉EHF300100元件
			ehf300100.close();
			
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
		
		EHF300100M0F Form = new EHF300100M0F();
		
		List list = new ArrayList();
		
		Form.setEHF300100T0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF300100M0F Form = (EHF300100M0F) form;
		Map return_map = new HashMap();
		EHF300100M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF300100M0F> querylist = new ArrayList<EHF300100M0F>();
			
			//建立EHF300100元件
			EHF300100 ehf300100 = new EHF300100(comp_id);
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf300100_queryList = ehf300100.queryData(queryCondMap);
			
			Iterator it = ehf300100_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF300100M0F();
				
				bean.setEHF300100T0_01(Integer.toString((Integer)tempMap.get("EHF300100T0_01")));	//路線代碼
				bean.setEHF300100T0_02((String)tempMap.get("EHF300100T0_02"));	//路線名稱
				bean.setEHF300100T0_03(Integer.toString((Integer)tempMap.get("EHF300100T0_03")));	//顯示順序
				bean.setEHF300100T0_05((String)tempMap.get("EHF300100T0_05"));	//備註
				
				
				if((Boolean)tempMap.get("EHF300100T0_04")){
					bean.setEHF300100T0_04_TXT("啟用");
				}else{
					bean.setEHF300100T0_04_TXT("停用");
				}
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF300100T0_LIST(querylist);
			
			//關閉EHF010109元件
			ehf300100.close();
			
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
	}

}
