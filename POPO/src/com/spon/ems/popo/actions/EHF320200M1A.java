package com.spon.ems.popo.actions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.popo.forms.EHF320200M0F;
import com.spon.ems.popo.models.EHF320200;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 上菜順序
 * (Action)
 */
public class EHF320200M1A extends EditAction {
	
	private BA_TOOLS tool;
	
	public EHF320200M1A()
	{
		tool = BA_TOOLS.getInstance();
	}
	
	
	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		EHF320200M0F Form = (EHF320200M0F) form;

	}

	
	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF320200M0F Form = (EHF320200M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF320200元件
			EHF320200 ehf320200 = new EHF320200(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			//dataMap = BeanUtils.describe(Form);
			dataMap.put("EHF320200T1_01", Form.getEHF320200T1_01()); //上菜順序天
			dataMap.put("EHF320200T1_02", Form.getEHF320200T1_02()); //上菜順序項次
			//dataMap.put("EHF320200T1_03", Form.getEHF320200T1_03()); //菜單編號
			dataMap.put("EHF320100T0_01", Form.getEHF320100T0_01()); //系統菜單編號

			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf320200.addData(dataMap);
			
			//關閉EHF320200元件
			ehf320200.close();
	
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF320200T1_01()+"/"+Form.getEHF320200T1_02());
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
		EHF320200M0F Form = (EHF320200M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);

		try{
			//建立EHF320200元件
			EHF320200 ehf320200 = new EHF320200();
			String[] key_id = key[0].split("/");
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF320200T1_01", key_id[0]);  //
			queryCondMap.put("EHF320200T1_02", key_id[1]);  //
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf320200.queryEditData(queryCondMap);
			
			//關閉EHF320200元件
			ehf320200.close();
			
			if(!dataMap.isEmpty()){  //SQL查詢完的值傳給JSP
				Form.setEHF320200T1_01(Integer.toString((Integer)dataMap.get("EHF320200T0_01")));  //上菜順序天
				Form.setEHF320200T1_02(Integer.toString((Integer)dataMap.get("EHF320200T0_02")));  //上菜順序項次
				Form.setEHF320200T1_03((String) dataMap.get("EHF320200T0_03"));  //菜單編號
				Form.setEHF320200T1_03_TXT((String) dataMap.get("EHF320100T0_03"));  //菜單名稱
				Form.setEHF320200T0_04((String) dataMap.get("EHF320200T0_04"));  //公司

				Form.setEHF320100T0_01((String) dataMap.get("EHF320100T0_01"));  //菜單系統編號(隱藏)
				Form.setEHF320100T0_04((String) dataMap.get("EHF320100T0_04_TXT"));  //菜單餐別
				Form.setEHF320100T0_05((String) dataMap.get("EHF320100T0_05_TXT"));  //菜單類別
				
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
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
		EHF320200M0F Form = (EHF320200M0F) form;
		
		try{
			//建立EHF320200元件
			EHF320200 EHF320200 = new EHF320200();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			EHF320200.saveData(dataMap);
			
			//關閉EHF320200元件
			EHF320200.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF320200T1_01()+"/"+Form.getEHF320200T1_02()); //KEY值回傳合併
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
		EHF320200M0F Form = (EHF320200M0F) form;
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF320200T1_01()+"/"+Form.getEHF320200T1_02());
			//key_id[1] = String.valueOf(Form.getEHF320200T1_02());
			
			//key_id = ((String)Form.getKEY_01C02C()).split("/");
			
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
		EHF320200M0F Form = (EHF320200M0F) form;
		String key = "";
		try{			
		//	key = String.valueOf(Form.getEHF320200T1_01());
			key = String.valueOf(Form.getKEY_01C02C());
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
		EHF320200M0F Form = (EHF320200M0F) form;
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
		
		try{
			request.setAttribute("listEHF320100T0_04", tools.getOptions(conn, true, "MENU_MEAL", getLoginUser(request).getCompId())); 
			
		}catch(Exception e){
			request.setAttribute("listEHF320100T0_04", new ArrayList()); 
			//request.setAttribute("listPSFOODT0_02", new ArrayList()); 
			e.printStackTrace();
		}
		
		
		try{
			request.setAttribute("listEHF320100T0_05", tools.getOptions(conn, true, "MENU_TYPE", getLoginUser(request).getCompId())); 
			
		}catch(Exception e){
			request.setAttribute("listEHF320100T0_05", new ArrayList()); 
			//request.setAttribute("listPSFOODT0_02", new ArrayList()); 
			e.printStackTrace();
		}
		
		
	}
	
	
}
