package com.spon.ems.com.actions;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.com.forms.EHF000400M0F;
import com.spon.ems.com.models.EHF000400;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action>公司班別設定
 *@author maybe
 *@version 1.0
 *@created 2015/11/5 下午3:24:05
 */
public class EHF000400M1A extends EditAction {

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF000400M0F Form = (EHF000400M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF000400元件
			EHF000400 ehf000400 = new EHF000400(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			
			Form.setEHF000400T0_05_HH("00");
			Form.setEHF000400T0_05_MM("00");
			
			Form.setEHF000400T0_06_HH("00");
			Form.setEHF000400T0_06_MM("00");
			
			Form.setEHF000400T0_07_HH("00");
			Form.setEHF000400T0_07_MM("00");
			
			Form.setEHF000400T0_08_HH("00");
			Form.setEHF000400T0_08_MM("00");

			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000400.addData(dataMap);
			
			//關閉EHF000400元件
			ehf000400.close();
			
			//取出KEY_ID
			Form.setEHF000400T0_01(String.valueOf((Integer)dataMap.get("KEY_ID")));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000400T0_01());
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
	protected Map executeQueryEditData(Connection conn, String[] key,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF000400M0F Form = (EHF000400M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF000400元件
			EHF000400 ehf000400 = new EHF000400();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF000400T0_01", key[0]);  //班別序號
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf000400.queryEditData(queryCondMap);
			
			//關閉EHF000400元件
			ehf000400.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setEHF000400T0_01(String.valueOf((Integer)dataMap.get("EHF000400T0_01")));
				Form.setEHF000400T0_03((String)dataMap.get("EHF000400T0_03"));  //班別代號
				Form.setEHF000400T0_04((String)dataMap.get("EHF000400T0_04"));  //班別
				if(!"".equals((String)dataMap.get("EHF000400T0_05")) && (String)dataMap.get("EHF000400T0_05")!=null){
					Form.setEHF000400T0_05_HH(((String)dataMap.get("EHF000400T0_05")).substring(0, 2));  //上班時間
					Form.setEHF000400T0_05_MM(((String)dataMap.get("EHF000400T0_05")).substring(2, 4));
				}
				if(!"".equals((String)dataMap.get("EHF000400T0_06")) && (String)dataMap.get("EHF000400T0_06")!=null){
					Form.setEHF000400T0_06_HH(((String)dataMap.get("EHF000400T0_06")).substring(0, 2));  //下班時間
					Form.setEHF000400T0_06_MM(((String)dataMap.get("EHF000400T0_06")).substring(2, 4));
				}
				Form.setEHF000400T0_07_HH(((String)dataMap.get("EHF000400T0_07")).substring(0, 2));  //午休時間(起)
				Form.setEHF000400T0_07_MM(((String)dataMap.get("EHF000400T0_07")).substring(2, 4));
				Form.setEHF000400T0_08_HH(((String)dataMap.get("EHF000400T0_08")).substring(0, 2));  //午休時間(迄)
				Form.setEHF000400T0_08_MM(((String)dataMap.get("EHF000400T0_08")).substring(2, 4));
				Form.setEHF000400T0_NFLG(tools.BooleanToString((Boolean)dataMap.get("EHF000400T0_NFLG"), "2"));  //是否記錄中午打卡
				Form.setEHF000400T0_09((String)dataMap.get("EHF000400T0_09"));  //是否系統預設
				Form.setEHF000400T0_10((String)dataMap.get("EHF000400T0_10"));  //備註
				Form.setEHF000400T0_17((String)dataMap.get("EHF000400T0_17"));  //休假方式
				Form.setEHF000400T0_18((String)dataMap.get("EHF000400T0_18"));  //是否時薪班別
				//Create Information
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				//Form.setHR_LastUpdateDate(ems_tools.convertADDatetimeToStrng((Date)dataMap.get("HR_LastUpdateDate")));//修改日期
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				
				chk_flag = true;
			}
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			if("1".equals(Form.getEHF000400T0_18())){
				request.setAttribute("PAY_BY_HOUR", "yes");
			}else{
				request.setAttribute("PAY_BY_HOUR", "no");
			}
			
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
		EHF000400M0F Form = (EHF000400M0F) form;
		
		try{
			//建立EHF000400元件
			EHF000400 ehf000400 = new EHF000400();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000400.saveData(dataMap);
			
			//關閉EHF000400元件
			ehf000400.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000400T0_01());
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
		
		EHF000400M0F Form = (EHF000400M0F) form;
		
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000400T0_01());
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
		
		EHF000400M0F Form = (EHF000400M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF000400T0_01());
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return key;
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF000400M0F Form = (EHF000400M0F) form;
		
		Form.setEHF000400T0_18("0");
		
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
		
		DecimalFormat df = new DecimalFormat("00");
		
		//產生 "時" 的下拉選單
		try{
			List listEHF000400T0_05_HH=new ArrayList();
			for (int i = 0; i < 24; i++) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF000400T0_05_HH.add(bform);
			}
			request.setAttribute("listEHF000400T0_05_HH",listEHF000400T0_05_HH);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//產生 "分" 的下拉選單
		try{
			List listEHF000400T0_05_MM=new ArrayList();
			for (int i = 0; i < 60; i= i+5) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF000400T0_05_MM.add(bform);
			}
			request.setAttribute("listEHF000400T0_05_MM",listEHF000400T0_05_MM);
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//產生系統預設類別
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
			request.setAttribute("listEHF000400T0_09", datas);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//休假方式：(1)周休二日，(2)隔周休二日
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("周休二日");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("2");
			bform.setItem_value("隔周休二日");
			datas.add(bform);
			request.setAttribute("EHF000400T0_17_list", datas);
		}catch(Exception e){
				e.printStackTrace();
		}
		
		//是否時薪班別
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
			request.setAttribute("listEHF000400T0_18", datas);
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}
