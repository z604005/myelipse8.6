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
import com.spon.ems.popo.forms.EHF300400M0F;
import com.spon.ems.popo.models.EHF300400;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * (Action)茶飲資料
 */
public class EHF300400M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF300400元件
			EHF300400 ehf300400 = new EHF300400(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF300400T0_01", key[0]);  //系統編號
			dataMap.put("COMP_ID", comp_id);
			
			boolean delFlag = ehf300400.delCheck(key[0], "1", comp_id);
			
			if(delFlag){
				request.setAttribute("ERR_MSG", "此茶飲正在使用中，無法刪除!!");
				chk_flag = false;
			}else{
				//執行EHF300400刪除功能
				ehf300400.delData(dataMap);
				
				if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
					chk_flag = true;
				}
			}
			
			//關閉EHF300400元件
			ehf300400.close();
			
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
		
		EHF300400M0F Form = new EHF300400M0F();
		
		List list = new ArrayList();
		
		Form.setEHF300400T0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF300400M0F Form = (EHF300400M0F) form;
		Map return_map = new HashMap();
		EHF300400M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF300400M0F> querylist = new ArrayList<EHF300400M0F>();
			
			//建立EHF300400元件
			EHF300400 ehf300400 = new EHF300400(comp_id);
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf300400_queryList = ehf300400.queryData(queryCondMap);
			
			Iterator it = ehf300400_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF300400M0F();
				
				bean.setEHF300400T0_01((String)tempMap.get("EHF300400T0_01"));	//系統編號
				bean.setEHF300400T0_02(Integer.toString((Integer)tempMap.get("EHF300400T0_02")));  //星期
				bean.setEHF300400T0_03((String)tempMap.get("EHF300400T0_03"));	//茶飲代號
				bean.setEHF300400T0_04((String)tempMap.get("EHF300400T0_04"));	//茶飲名稱
				
				if((Boolean)tempMap.get("EHF300400T0_05")){
					bean.setEHF300400T0_05_TXT("啟用");
				}else{
					bean.setEHF300400T0_05_TXT("停用");
				}
				
				if((Integer)tempMap.get("EHF300400T0_02")==1){
					bean.setEHF300400T0_02_TXT("星期一");
				}else if((Integer)tempMap.get("EHF300400T0_02")==2){
					bean.setEHF300400T0_02_TXT("星期二");
				}else if((Integer)tempMap.get("EHF300400T0_02")==3){
					bean.setEHF300400T0_02_TXT("星期三");
				}else if((Integer)tempMap.get("EHF300400T0_02")==4){
					bean.setEHF300400T0_02_TXT("星期四");
				}else if((Integer)tempMap.get("EHF300400T0_02")==5){
					bean.setEHF300400T0_02_TXT("星期五");
				}else if((Integer)tempMap.get("EHF300400T0_02")==6){
					bean.setEHF300400T0_02_TXT("星期六");
				}else{
					bean.setEHF300400T0_02_TXT("星期日");
				}
				
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF300400T0_LIST(querylist);
			
			//關閉EHF010109元件
			ehf300400.close();
			
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
		//產生 radio選單 --> 星期
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("星期一");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("2");
			bform.setItem_value("星期二");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("3");
			bform.setItem_value("星期三");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("4");
			bform.setItem_value("星期四");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("5");
			bform.setItem_value("星期五");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("6");
			bform.setItem_value("星期六");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("7");
			bform.setItem_value("星期日");
			datas.add(bform);
			request.setAttribute("listEHF300400T0_02", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
