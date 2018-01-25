package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;


import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF320300M0F;
import com.spon.ems.popo.models.EHF320300;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * (Action)餐點預排管理
 */
public class EHF320300M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF320300元件
			EHF320300 ehf320300 = new EHF320300(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF320300T0_01", key[0]);  
			dataMap.put("COMP_ID", comp_id);
			
			
				
				//執行EHF320300刪除功能
				ehf320300.delData(dataMap);
				
				
				if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
					chk_flag = true;
				}
			
			
			//關閉EHF320300元件
			ehf320300.close();
			
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
		
		EHF320300M0F Form = new EHF320300M0F();
		
		List list = new ArrayList();
		
		Form.setEHF320300T0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF320300M0F Form = (EHF320300M0F) form;
		Map return_map = new HashMap();
		EHF320300M0F bean_0 = null;
		EHF320300M0F bean = null;
		EHF320300M0F bean_2 = null;
		EHF320300M0F bean_3 = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF320300M0F> querylist = new ArrayList<EHF320300M0F>();
			
			//建立EHF320300元件
			EHF320300 ehf320300 = new EHF320300(comp_id);
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf320300_queryList = ehf320300.queryData(queryCondMap);
			
			Iterator it = ehf320300_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				String UID = (String)tempMap.get("EHF320300T0_01");
				
				List MealCategoryDetailDetailList = ehf320300.queryMealcategoryDetailList(UID, comp_id);
				
				List<EHF320300M0F> queryMealCategoryDetailList = new ArrayList<EHF320300M0F>();
				Iterator it_2 = MealCategoryDetailDetailList.iterator();
				Map tempMap_2 = null;
				
				bean = new EHF320300M0F();
				
				while(it_2.hasNext()){
					
					tempMap_2 = (Map) it_2.next();
					
					bean_2 = new EHF320300M0F();
					
					bean_2.setEHF320300T0_03_TXT(Form.getITEM_VALUE((String)tempMap_2.get("EHF320300T1_03"), "MENU_MEAL", comp_id));	//採購品項
					
						List MealNameDetailDetailList = ehf320300.queryMealNameDetailList(UID, (String)tempMap_2.get("EHF320300T1_03"), comp_id);
					
						List<EHF320300M0F> queryMealnameDetailList = new ArrayList<EHF320300M0F>();
						Iterator it_3 = MealNameDetailDetailList.iterator();
						Map tempMap_3 = null;
					
						while(it_3.hasNext()){
						
							tempMap_3 = (Map) it_3.next();
						
							bean_3 = new EHF320300M0F();
							
							bean_3.setEHF320300T0_02_TXT(Integer.toString((Integer)tempMap_3.get("EHF320300T1_02")));
						
							bean_3.setEHF320300T0_05_TXT(ehf320300.getVegetables((String)tempMap_3.get("EHF320300T1_05"), comp_id));
						
							String a = ehf320300.getVegetables((String)tempMap_3.get("EHF320300T1_05"), comp_id);
							
							queryMealnameDetailList.add(bean_3);

						
						}
											
					bean_2.setListEHF320300T0_05(queryMealnameDetailList);
						
					queryMealCategoryDetailList.add(bean_2);
						
				}
				

					if (MealCategoryDetailDetailList.isEmpty()){
						bean.setEHF320300T0_03_TXT("");
						bean.setEHF320300T0_02_TXT("");
						bean.setEHF320300T0_05_TXT("");
						queryMealCategoryDetailList.add(bean);
					}

				
				bean.setEHF320300T0_01((String)tempMap.get("EHF320300T0_01"));	//系統編號
				bean.setEHF320300T0_02((String)tempMap.get("EHF320300T0_02"));	//日期
				bean.setListEHF320300T0_03(queryMealCategoryDetailList);
				//bean.setEHF320300T0_03(Integer.toString((Integer)tempMap.get("EHF320300T0_03")));	//上菜順序天
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF320300T0_LIST(querylist);
			
			//關閉EHF010109元件
			ehf320300.close();
			
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
			/*List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("是");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0");
			bform.setItem_value("否");
			datas.add(bform);
			request.setAttribute("listTF", datas);*/
		}catch(Exception e) {
			System.out.println(e);
		}
	}

}
