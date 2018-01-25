package com.spon.ems.hr.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010109;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class EHF010109M1A extends EditAction {
	
	private BA_TOOLS tool;
	//private BA_EMS_TOOLS ems_tool;
	public EHF010109M1A()
	{
		tool = BA_TOOLS.getInstance();
		//ems_tool = BA_EMS_TOOLS.getInstance();
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
		EHF010109M0F Form = (EHF010109M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010109元件
			EHF010109 ehf010109 = new EHF010109(comp_id);
			
			//String HR_JobNameSysNo = EMS_GetCodeRules.getInstance().getCodeRule("", comp_id);
			String HR_JobNameSysNo = tools.createEMSUID(conn, "JOB", "EHF010109T0", "HR_JobNameSysNo", comp_id);
			
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			
			dataMap.put("HR_JobNameSysNo", new String(HR_JobNameSysNo));
			dataMap.put("HR_JobNameNo", Form.getHR_JobNameNo());
			dataMap.put("HR_JobName", Form.getHR_JobName());
			dataMap.put("EHF010109T0_01", tool.StringToBoolean(Form.getEHF010109T0_01_TXT()));
			dataMap.put("EHF010109T0_02", Form.getEHF010109T0_02()==null?"":Form.getEHF010109T0_02());
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf010109.addData(dataMap);
			
			//關閉EHF010109元件
			ehf010109.close();
			
			//取出KEY_ID
			Form.setHR_JobNameSysNo((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getHR_JobNameSysNo());
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
		EHF010109M0F Form = (EHF010109M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010109元件
			EHF010109 ehf010109 = new EHF010109();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("HR_JobNameSysNo", key[0]);  //職務系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf010109.queryEditData(queryCondMap);
			
			//關閉EHF010109元件
			ehf010109.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setHR_JobNameSysNo((String)dataMap.get("HR_JobNameSysNo"));  //職務系統代碼
				Form.setHR_JobNameNo((String)dataMap.get("HR_JobNameNo"));  //職務顯示代碼
				Form.setHR_JobName((String) dataMap.get("HR_JobName"));  //職務名稱
				if((Boolean) dataMap.get("EHF010109T0_01")){	//使用狀況
					Form.setEHF010109T0_01_TXT("1");					
				}else{
					Form.setEHF010109T0_01_TXT("0");					
				}
				//Form.setEHF010109T0_01((Boolean) dataMap.get("EHF010109T0_01"));  //使用狀況
				Form.setEHF010109T0_02((String) dataMap.get("EHF010109T0_02"));  //備註
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
		EHF010109M0F Form = (EHF010109M0F) form;
		
		try{
			//建立EHF010109元件
			EHF010109 ehf010109 = new EHF010109();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap.put("HR_JobNameSysNo", Form.getHR_JobNameSysNo());
			dataMap.put("HR_JobNameNo", Form.getHR_JobNameNo());
			dataMap.put("HR_JobName", Form.getHR_JobName());
			dataMap.put("EHF010109T0_02", Form.getEHF010109T0_02());
			if("1".equals(Form.getEHF010109T0_01_TXT())){
				dataMap.put("EHF010109T0_01", true);
			}else{
				dataMap.put("EHF010109T0_01", false);
			}
			//dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf010109.saveData(dataMap);
			
			//關閉EHF010109元件
			ehf010109.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getHR_JobNameSysNo());
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
		
		EHF010109M0F Form = (EHF010109M0F) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getHR_JobNameSysNo());
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
		
		EHF010109M0F Form = (EHF010109M0F) form;
		
		String key = "";
		
		try{			
			key = String.valueOf(Form.getHR_JobNameSysNo());
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
		
		EHF010109M0F Form = (EHF010109M0F) form;
		
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
