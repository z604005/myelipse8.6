package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.salary.forms.EHF030107M0F;
import com.spon.ems.salary.models.EHF030107;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action> 加班規則設定作業

 */
public class EHF030107M1A extends EditAction {

	public EHF030107M1A() {
		
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {
		
	}

	/**
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	protected void generateSelectBox( Connection conn, ActionForm form, HttpServletRequest request ) {
		
		try{
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
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF030107M0F Form = (EHF030107M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//設定EHF030107M1 新增語句
			
			//建立EHF030107元件
			EHF030107 ehf030107 = new EHF030107(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行新增資料
			ehf030107.addData(dataMap);
			
			//關閉EHF030107元件
			ehf030107.close();
			
			//取出KEY_ID
			Form.setEHF030107T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF030107T0_01());
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
	
	/**
	 * 清除新增欄位
	 */
	protected void cleanAddField(ActionForm form){
		
		EHF030107M0F Form = (EHF030107M0F) form;
		
		try{
			//Do Nothing!!
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF030107M0F Form = (EHF030107M0F) form;
		
		try{
			//設定EHF030107M1 儲存語句
			//建立EHF030107元件
			EHF030107 ehf030107 = new EHF030107();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf030107.saveData(dataMap);
			
			
			//關閉EHF030107元件
			ehf030107.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF030107T0_01());
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
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		EHF030107M0F Form = (EHF030107M0F) form;
		//Form = new EHF030107M0F();
		
		try{
			//加班(時數一)
			Form.setEHF030107T0_04("0");
			//加班(加成率一)
			Form.setEHF030107T0_05("0");
			
			//加班(時數二)
			Form.setEHF030107T0_06("0");
			//加班(加成率二)
			Form.setEHF030107T0_07("0");
			
			//國定假日加班加成率
			Form.setEHF030107T0_08("0");
			//例假日加班加成率
			Form.setEHF030107T0_11("0");
			
			//是否啟用
			Form.setEHF030107T0_09("1");  //預設 = 是
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		
		EHF030107M0F Form = (EHF030107M0F) form;
		Map return_map = new HashMap();
		
		try{
			//Return資料
			return_map.put("CHK_FLAG", true);
			return_map.put("FORM", form);
		
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}

	@Override
	protected Map executeQueryEditData(Connection conn, String[] key, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF030107M0F Form = (EHF030107M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		Map return_map = new HashMap();
		
		try{
			//建立EHF030107M1 查詢語句
			//建立EHF030107元件
			EHF030107 ehf030107 = new EHF030107();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF030107T0_01", (key[0]));  //加班費規則序號
			queryCondMap.putAll(paramMap);  //放入使用者資訊
			
			Map dataMap = ehf030107.queryEditData(queryCondMap);
			
			//關閉EHF030107 元件
			ehf030107.close();
			
			if(!dataMap.isEmpty()){
				
				BeanUtils.copyProperties(Form, dataMap);
				
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
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		EHF030107M0F Form = (EHF030107M0F) form;
		String key = "";
		try{			
			key = String.valueOf(Form.getEHF030107T0_01());
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return key;
	}

	@Override
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		EHF030107M0F Form = (EHF030107M0F) form;
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF030107T0_01());
			paramMap.put(super.KEY_ID, key_id);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
	}
	
}