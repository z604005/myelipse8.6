package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.hr.forms.EHF010100M0F;
import com.spon.ems.popo.forms.EHF320100M0F;
import com.spon.ems.popo.models.EHF320100;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 菜單管理設定
 * (Action)
 * @author maybe
 * @version 1.0
 * @created 下午3:05:40
 */
public class EHF320100M1A extends EditAction {

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		
		EHF320100M0F Form = (EHF320100M0F) form;
		
		Form.setEHF320100T1_03("");
		Form.setEHF320100T1_03_TXT("");
		Form.setEHF320100T2_03("");
		Form.setEHF320100T2_03_TXT("");
		Form.setEHF320100T3_03("");
		Form.setEHF320100T3_04("");
		Form.setEHF320100T3_04_TXT("");
		Form.setEHF320100T3_05("");
		Form.setEHF320100T3_05_TXT("");
		Form.setEHF320100T3_06("");
		Form.setEHF320100T3_07("");
		Form.setEHF320100T4_02(1);
		Form.setEHF320100T4_03("");
		Form.setEHF320100T4_03_SHOW("");
		Form.setEHF320100T4_03_TXT("");
		Form.setEHF320100T5_02(1);
		Form.setEHF320100T5_03("");
		Form.setEHF320100T5_03_SHOW("");
		Form.setEHF320100T5_03_TXT("");

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF320100M0F Form = (EHF320100M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			EHF320100 ehf320100 = new EHF320100(comp_id);
			
			String EHF320100T0_01 = tools.createEMSUID(conn, "MU", "EHF320100T0", "EHF320100T0_01", comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();

			dataMap = BeanUtils.describe(Form);
			dataMap.put("EHF320100T0_01", EHF320100T0_01);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf320100.addData(dataMap);
						
			ehf320100.close();
			
			//取出KEY_ID
			Form.setEHF320100T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF320100T0_01());
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
	public boolean executeAddDetailData(Connection conn, String type,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF320100M0F Form = (EHF320100M0F) form;
		boolean chk_flag = false;
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
						
			ehf320100.addDetailData(type, detailDataMap);
									
			ehf320100.close();
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF320100T0_01");
			paramMap.put(super.KEY_ID, key_id);
									
			//新增完成
			chk_flag = true;
			
			// 清掉畫面上的新增明細欄位
			this.cleanAddField(Form);
			
		}catch(Exception e){
			chk_flag = false;
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
		EHF320100M0F Form = (EHF320100M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF320100T0_01", key[0]);  //班別序號
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf320100.queryEditData(queryCondMap);
			
			//添加物
			List EHF320100T1List = ehf320100.queryEHF320100T1List(queryCondMap);
			
			Form.setEHF320100T1_DETAIL(EHF320100T1List);
			
			//擺盤
			List EHF320100T2List = ehf320100.queryEHF320100T2List(queryCondMap);
			
			Form.setEHF320100T2_DETAIL(EHF320100T2List);
			
			//明細
			List EHF320100T3List = ehf320100.queryEHF320100T3List(queryCondMap);
			
			Iterator it_EHF320100T3 = EHF320100T3List.iterator();
			Map tempMap_EHF320100T3 = null;
			EHF320100M0F bean_1 = null;
			
			//建立空清單
			List<EHF320100M0F> querylist_1 = new ArrayList<EHF320100M0F>();
			
			while(it_EHF320100T3.hasNext()){
				
				tempMap_EHF320100T3 = (Map) it_EHF320100T3.next();
				
				bean_1 = new EHF320100M0F();
				
				bean_1.setEHF320100T0_01((String)tempMap_EHF320100T3.get("EHF320100T3_01"));
				bean_1.setEHF320100T3_01((String)tempMap_EHF320100T3.get("EHF320100T3_01"));
				bean_1.setEHF320100T3_02((Integer)tempMap_EHF320100T3.get("EHF320100T3_02"));
				bean_1.setEHF320100T3_03_TXT((String)tempMap_EHF320100T3.get("EHF320100T3_03_TXT"));	//主類別
				bean_1.setEHF320100T3_04_TXT((String)tempMap_EHF320100T3.get("EHF320100T3_04_TXT"));	//食材分類
				bean_1.setEHF320100T3_05_TXT((String)tempMap_EHF320100T3.get("EHF320100T3_05_TXT"));	//食材內容
				bean_1.setEHF320100T3_06_TXT((String)tempMap_EHF320100T3.get("EHF320100T3_06_TXT"));	//遞補副食菜單
				bean_1.setEHF320100T3_07((String)tempMap_EHF320100T3.get("EHF320100T3_07"));	//備註
												
				querylist_1.add(bean_1);
			}
			
			Form.setEHF320100T3_DETAIL(querylist_1);
			
			List EHF320100T4List = ehf320100.queryEHF320100T4List(queryCondMap);
			
			Form.setEHF320100T4_DETAIL(EHF320100T4List);
			
			List EHF320100T5List = ehf320100.queryEHF320100T5List(queryCondMap);
			
			Form.setEHF320100T5_DETAIL(EHF320100T5List);
			
			ehf320100.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setEHF320100T0_01((String)dataMap.get("EHF320100T0_01"));
				Form.setEHF320100T0_02((String)dataMap.get("EHF320100T0_02"));
				Form.setEHF320100T0_03((String)dataMap.get("EHF320100T0_03"));
				Form.setEHF320100T0_04((String)dataMap.get("EHF320100T0_04"));
				Form.setEHF320100T0_05((String)dataMap.get("EHF320100T0_05"));
				Form.setEHF320100T0_06((String)dataMap.get("EHF320100T0_06"));
				Form.setEHF320100T0_07((String)dataMap.get("EHF320100T0_07"));
				
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				Form.setDATE_UPDATE(tools.convertADDatetimeToStrng((Date)dataMap.get("DATE_UPDATE")));//修改日期
				
				chk_flag = true;
			}
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			request.setAttribute("Form_No", Form.getEHF320100T0_01()==null?"系統編號:"+"":"系統編號:"+Form.getEHF320100T0_01());
			
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
		EHF320100M0F Form = (EHF320100M0F) form;
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf320100.saveData(dataMap);
			
			ehf320100.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF320100T0_01());
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
		
		EHF320100M0F Form = (EHF320100M0F) form;
		
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF320100T0_01());
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
		
		EHF320100M0F Form = (EHF320100M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF320100T0_01());
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
		return super.handleDelDetailKey(key, form, paramMap);
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean executeDelDetailData(Connection conn, String type,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF320100M0F Form = (EHF320100M0F) form;
		boolean chk_flag = false;
		
		try{
			EHF320100 ehf320100 = new EHF320100();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			ehf320100.delDetailData(type, detailDataMap);
			
			ehf320100.close();
			
			//設定Query Key
			String[] key_id = new String[1];			
			key_id[0] = (String)detailDataMap.get("EHF320100T0_01");
			paramMap.put(super.KEY_ID, key_id);
									
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
		
		EHF320100M0F Form = (EHF320100M0F) form;
		
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
		
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("第1順位");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("2");
			bform.setItem_value("第2順位");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("3");
			bform.setItem_value("第3順位");
			datas.add(bform);
			request.setAttribute("listORDER", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		request.setAttribute("listEHF320100T0_04", tools.getOptions(conn, true, "MENU_MEAL", getLoginUser(request).getCompId()));//菜單餐別
		request.setAttribute("listEHF320100T0_05", tools.getOptions(conn, true, "MENU_TYPE", getLoginUser(request).getCompId()));//菜單類別
		request.setAttribute("listEHF320100T0_07", tools.getOptions(conn, true, "MENU_OIL", getLoginUser(request).getCompId()));//菜單用油
		request.setAttribute("listEHF320100T3_03", tools.getOptions(conn, true, "MAIN_TYPE", getLoginUser(request).getCompId()));//主類別

	}

}
