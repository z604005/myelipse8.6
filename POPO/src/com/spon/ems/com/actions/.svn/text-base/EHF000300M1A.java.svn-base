package com.spon.ems.com.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.com.forms.EHF000300M0F;
import com.spon.ems.com.models.EHF000300;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;

/**
 * (Action)職務基本資料
 * @author maybe
 * @version 1.0
 * @created 2015/8/18 上午9:27:24
 */
public class EHF000300M1A extends EditAction {
	
	private BA_TOOLS tool;
	
	public EHF000300M1A()
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
		EHF000300M0F Form = (EHF000300M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF000300元件
			EHF000300 ehf000300 = new EHF000300(comp_id);
			
			//String HR_JobNameSysNo = EMS_GetCodeRules.getInstance().getCodeRule("", comp_id);
			String EHF000300T0_01 = tools.createJOBUID(conn, "JOB", "EHF000300T0", "EHF000300T0_01", comp_id);
			
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			
			dataMap.put("EHF000300T0_01", new String(EHF000300T0_01));
			dataMap.put("EHF000300T0_02", Form.getEHF000300T0_02());
			dataMap.put("EHF000300T0_03", Form.getEHF000300T0_03());
			dataMap.put("EHF000300T0_05", tool.StringToBoolean(Form.getEHF000300T0_05_TXT()));
			dataMap.put("EHF000300T0_06", Form.getEHF000300T0_06()==null?"":Form.getEHF000300T0_06());
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000300.addData(dataMap);
			
			//關閉EHF000300元件
			ehf000300.close();
			
			//取出KEY_ID
			Form.setEHF000300T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000300T0_01());
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
		EHF000300M0F Form = (EHF000300M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010109元件
			EHF000300 ehf000300 = new EHF000300();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF000300T0_01", key[0]);  //職務系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf000300.queryEditData(queryCondMap);
			
			//關閉EHF010109元件
			ehf000300.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setEHF000300T0_01((String)dataMap.get("EHF000300T0_01"));  //職務系統代碼
				Form.setEHF000300T0_02((String)dataMap.get("EHF000300T0_02"));  //職務顯示代碼
				Form.setEHF000300T0_03((String) dataMap.get("EHF000300T0_03"));  //職務名稱
				if((Boolean) dataMap.get("EHF000300T0_05")){	//使用狀況
					Form.setEHF000300T0_05_TXT("1");					
				}else{
					Form.setEHF000300T0_05_TXT("0");					
				}
				//Form.setEHF010109T0_01((Boolean) dataMap.get("EHF010109T0_01"));  //使用狀況
				Form.setEHF000300T0_06((String) dataMap.get("EHF000300T0_06"));  //備註
				Form.setHR_CompanySysNo((String) dataMap.get("HR_CompanySysNo"));
				//Create Information
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				//Form.setHR_LastUpdateDate(ems_tools.convertADDatetimeToStrng((Date)dataMap.get("HR_LastUpdateDate")));//修改日期
				Form.setHR_LastUpdateDate((String)dataMap.get("HR_LastUpdateDate"));
				
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
		EHF000300M0F Form = (EHF000300M0F) form;
		
		try{
			//建立EHF010109元件
			EHF000300 ehf000300 = new EHF000300();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap.put("EHF000300T0_01", Form.getEHF000300T0_01());
			dataMap.put("EHF000300T0_02", Form.getEHF000300T0_02());
			dataMap.put("EHF000300T0_03", Form.getEHF000300T0_03());
			dataMap.put("EHF000300T0_06", Form.getEHF000300T0_06());
			if("1".equals(Form.getEHF000300T0_05_TXT())){
				dataMap.put("EHF000300T0_05", true);
			}else{
				dataMap.put("EHF000300T0_05", false);
			}
			//dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000300.saveData(dataMap);
			
			//關閉EHF010109元件
			ehf000300.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000300T0_01());
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
		
		EHF000300M0F Form = (EHF000300M0F) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000300T0_01());
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
		
		EHF000300M0F Form = (EHF000300M0F) form;
		
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF000300T0_01());
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
		
		EHF000300M0F Form = (EHF000300M0F) form;
		
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
			bform.setItem_value("啟用");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0");
			bform.setItem_value("停用");
			datas.add(bform);
			request.setAttribute("listTF", datas);
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}
