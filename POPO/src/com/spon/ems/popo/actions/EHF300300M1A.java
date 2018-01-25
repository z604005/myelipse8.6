package com.spon.ems.popo.actions;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.popo.forms.EHF300000M0F;
import com.spon.ems.popo.forms.EHF300300M0F;
import com.spon.ems.popo.models.EHF300000;
import com.spon.ems.popo.models.EHF300300;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 早中晚餐定價
 * (Action)
 */
public class EHF300300M1A extends EditAction {
	
	private BA_TOOLS tool;
	
	public EHF300300M1A()
	{
		tool = BA_TOOLS.getInstance();
	}
	
	
	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		EHF300300M0F Form = (EHF300300M0F) form;
	}

	
	
	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF300300M0F Form = (EHF300300M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF300000元件
			EHF300300 ehf300300 = new EHF300300(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			//dataMap = BeanUtils.describe(Form);
			dataMap.put("EHF300300T0_01", Form.getEHF300300T0_01());
			dataMap.put("EHF300300T0_02_TXT", Form.getEHF300300T0_02_TXT());
			dataMap.put("EHF300300T0_03", Form.getEHF300300T0_03());
		//	dataMap.put("PSFOODT0_04", Form.getPSFOODT0_04());

			String UID = Form.getEHF300300T0_01();
			dataMap.put("UID", UID);
			//設定使用者資訊
			dataMap.putAll(paramMap);
			ehf300300.addData(dataMap);
			//關閉EHF300000元件
			ehf300300.close();
			//取出KEY_ID
			Form.setEHF300300T0_01(UID);
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF300300T0_01());
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
		EHF300300M0F Form = (EHF300300M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);

		try{
			//建立EHF300300元件
			EHF300300 ehf300300 = new EHF300300();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF300300T0_01", key[0]);  //系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf300300.queryEditData(queryCondMap);
			
			//取得List
			Form.setEHF300300_DETAIL(ehf300300.queryLexiconDList(dataMap));
			
			//關閉EHF300300元件
			ehf300300.close();
			
			if(!dataMap.isEmpty()){
				Form.setEHF300300T0_01((String)dataMap.get("EHF300300T0_01"));  
				Form.setEHF300300T0_02_TXT((String)dataMap.get("EHF300300T0_02"));  
				Form.setEHF300300T0_03((Integer)dataMap.get("EHF300300T0_03"));  
				Form.setEHF300300T0_04((String) dataMap.get("EHF300300T0_04"));  
			//	Form.setEHF300300T1_03((String)dataMap.get("EHF300300T1_03"));
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				chk_flag = true;

			}
			
			if("SUPPLIER".equals((String)dataMap.get("EHF300300T0_01"))){
				request.setAttribute("SUPPLIER", "yes");
			}else{
				request.setAttribute("SUPPLIER", "no");
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
		EHF300300M0F Form = (EHF300300M0F) form;
		
		try{
			//建立EHF300000元件
			EHF300300 EHF300300 = new EHF300300();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			EHF300300.saveData(dataMap);	
			//關閉EHF300000元件
			EHF300300.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF300300T0_01());
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
		EHF300300M0F Form = (EHF300300M0F) form;
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF300300T0_01());
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
		EHF300300M0F Form = (EHF300300M0F) form;
		String key = "";
		try{			
			if(Form.getEHF300300T0_01()==null)
				key = String.valueOf(Form.getEHF300300T0_01());
			else
				key = String.valueOf(Form.getEHF300300T1_01());
			
			
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
		EHF300300M0F Form = new EHF300300M0F();
		List list = new ArrayList();
		Form.setEHF300300C(list);
		return (ActionForm)Form;
	}

	
	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
			
	return null;
	}
	

	
	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		try{
			List optionlist = new ArrayList();
			BA_ALLKINDForm bform = null;
		
			String sql = "" +
			" select EMS_CategoryT1_04 as ITEM_ID, EMS_CategoryT1_05 as ITEM_VALUE " +
			" from EMS_CategoryT1 "+
			" where EMS_CategoryT1_01= 'MENU_MEAL' " +
			" ORDER BY EMS_CategoryT1_07 ";	

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			//建立第一筆選項
			bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			optionlist.add(bform);

			Statement stmt_sub = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_sub = null;
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("ITEM_ID"));
				bform.setItem_value(rs.getString("ITEM_ID")+"/"+rs.getString("ITEM_VALUE"));
				optionlist.add(bform);
			
			
			
			}
			
			rs.close();
			stmt.close();
			request.setAttribute("listEHF300300T1_03", optionlist); 
		}catch(Exception e){
			request.setAttribute("listEHF300300T1_03", new ArrayList()); 
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected boolean executeAddDetailData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF300300M0F Form = (EHF300300M0F) form;
	
		try{
			//設定EHF300300 細項 新增語句
			//建立EHF300300元件
			EHF300300 EHF300300 = new EHF300300();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			//新增訂單單明細資料
			EHF300300.addDetailData("ehf300300t1_add", detailDataMap);
			
			
			
			
			
			
			//關閉EHF300300元件
			EHF300300.close();
			
			//取出KEY_ID
			Form.setEHF300300T1_01((String)detailDataMap.get("KEY_ID"));
			
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			//key_id[0] = (String)detailDataMap.get("EHF300300T1_01");
			key_id[0] = (String)detailDataMap.get("KEY_ID");
			paramMap.put(super.KEY_ID, key_id);
						
			//新增明細完成
			chk_flag = true;
			this.cleanAddField(Form);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return chk_flag;
	}
	
	
	public boolean executeDelDetailData(Connection conn, String type, ActionForm form, Map paramMap ){
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		EHF300300M0F Form = (EHF300300M0F) form;
		
		try{
			//建立EHF300300元件
			EHF300300 EHF300300 = new EHF300300(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap= BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行刪除FOODT0功能
			EHF300300.delDetailData("del300300t1", dataMap);
			
			//關閉EHF300300元件
			EHF300300.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf((String)dataMap.get("EHF300300T1_01"));
			
			paramMap.put(super.KEY_ID, key_id);
			
			chk_flag = true;
			
			//清除新增欄位的內容
			this.cleanAddField(form);
			Form.setEHF300300T0_01((String)dataMap.get("EHF300300T1_01"));
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return chk_flag;
	}
	

	
		@Override
	protected Map handleDelDetailKey(String[] key, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF300300M0F Form = (EHF300300M0F) form;
		Map return_map = new HashMap();
		
		try{
			paramMap.put("EHF300300T0_01", Form.getEHF300300T1_01());
			paramMap.put("EHF300300T1_01", Form.getEHF300300T1_01());
		//	paramMap.put("EHF300300T1_04", Form.getEHF300300T1_04());
			return_map.put("CHK_FLAG", true);
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}		
		return return_map;
	}
	
	
	
}
