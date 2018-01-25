package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.popo.forms.EHF300100M0F;
import com.spon.ems.popo.models.EHF300100;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * (Action)路線資料
 */
public class EHF300100M1A extends EditAction {
	
	private BA_TOOLS tool;
	
	public EHF300100M1A()
	{
		tool = BA_TOOLS.getInstance();
	}

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF300100M0F Form = (EHF300100M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF300100元件
			EHF300100 ehf300100 = new EHF300100(comp_id);
			
			//String HR_JobNameSysNo = EMS_GetCodeRules.getInstance().getCodeRule("", comp_id);
//			String EHF300100T0_01 = tools.createJOBUID(conn, "JOB", "EHF300100T0", "EHF300100T0_01", comp_id);
			
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form); //把Fome 放到Map裡面, 等同於下面的東西。
//			dataMap.put("EHF300100T0_01", new String(EHF300100T0_01));
			dataMap.put("EHF300100T0_02", Form.getEHF300100T0_02());
			dataMap.put("EHF300100T0_03", Form.getEHF300100T0_03());
			dataMap.put("EHF300100T0_04", tool.StringToBoolean(Form.getEHF300100T0_04()));
			dataMap.put("EHF300100T0_05", Form.getEHF300100T0_05());
//			dataMap.put("EHF300100T0_06", Form.getEHF300100T0_06()==null?"":Form.getEHF300100T0_06());

			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf300100.addData(dataMap);
			
			//關閉EHF300100元件
			ehf300100.close();
			
			//取出KEY_ID
			Form.setEHF300100T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF300100T0_01());
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
		EHF300100M0F Form = (EHF300100M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010109元件
			EHF300100 ehf300100 = new EHF300100();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF300100T0_01", key[0]);  //路線代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf300100.queryEditData(queryCondMap);
			
			//關閉EHF010109元件
			ehf300100.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setEHF300100T0_01(Integer.toString((Integer)dataMap.get("EHF300100T0_01")));  //路線代碼
				Form.setEHF300100T0_02((String)dataMap.get("EHF300100T0_02"));  //路線名稱
				Form.setEHF300100T0_03(Integer.toString((Integer)dataMap.get("EHF300100T0_03")));  //路線顯示順序
				if((Boolean) dataMap.get("EHF300100T0_04")){	//使用狀況
					Form.setEHF300100T0_04("1");					
				}else{
					Form.setEHF300100T0_04("0");					
				}
				Form.setEHF300100T0_05((String)dataMap.get("EHF300100T0_05"));  //備註
				Form.setEHF300100T0_06((String) dataMap.get("EHF300100T0_06"));  //公司代碼
				//Create Information
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				//Form.setDATE_UPDATE(ems_tools.convertADDatetimeToStrng((Date)dataMap.get("DATE_UPDATE")));//修改日期
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				
				chk_flag = true;
				
			}
			
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
	protected boolean executeSaveData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF300100M0F Form = (EHF300100M0F) form;
		
		try{
			//建立EHF010109元件
			EHF300100 ehf300100 = new EHF300100();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap.put("EHF300100T0_01", Form.getEHF300100T0_01());
			dataMap.put("EHF300100T0_02", Form.getEHF300100T0_02());
			dataMap.put("EHF300100T0_03", Form.getEHF300100T0_03());
			dataMap.put("EHF300100T0_05", Form.getEHF300100T0_05());
			dataMap.put("EHF300100T0_06", Form.getEHF300100T0_06());
			if("1".equals(Form.getEHF300100T0_04())){
				dataMap.put("EHF300100T0_04", true);
			}else{
				dataMap.put("EHF300100T0_04", false);
			}
			//dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf300100.saveData(dataMap);
			
			//關閉EHF010109元件
			ehf300100.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF300100T0_01());
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
		
		EHF300100M0F Form = (EHF300100M0F) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF300100T0_01());
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
		
		EHF300100M0F Form = (EHF300100M0F) form;
		
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF300100T0_01());
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
		
		EHF300100M0F Form = (EHF300100M0F) form;
		
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

	}

}
