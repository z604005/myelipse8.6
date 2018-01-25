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
import com.spon.ems.popo.forms.EHF300000M0F;
import com.spon.ems.popo.models.EHF300000;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 食物代碼
 * (Action)
 * @author
 */
public class EHF300000M1A extends EditAction {
	
	private BA_TOOLS tool;
	
	public EHF300000M1A()
	{
		tool = BA_TOOLS.getInstance();
	}
	
	
	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		EHF300000M0F Form = (EHF300000M0F) form;
		Form.setPSFOODT1_02("");
		Form.setPSFOODT1_04("");
		Form.setPSFOODT1_05("");
		Form.setPSFOODT1_06("");
		Form.setPSFOODT1_07(0);		
	}

	
	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF300000M0F Form = (EHF300000M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF300000元件
			EHF300000 ehf300000 = new EHF300000(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			//dataMap = BeanUtils.describe(Form);
			dataMap.put("PSFOODT0_01", Form.getPSFOODT0_01());
			dataMap.put("PSFOODT0_02", Form.getPSFOODT0_02());
			dataMap.put("PSFOODT0_03", Form.getPSFOODT0_03());
			dataMap.put("PSFOODT0_04", Form.getPSFOODT0_04());
			dataMap.put("PSFOODT0_05", Form.getPSFOODT0_05());
			dataMap.put("PSFOODT0_06", Form.getPSFOODT0_06());
			dataMap.put("PSFOODT0_07", Form.getPSFOODT0_07());
			dataMap.put("PSFOODT0_08", Form.getPSFOODT0_08());
		
			String UID = Form.getPSFOODT0_01();
			dataMap.put("UID", UID);
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf300000.addData(dataMap);
			
			//關閉EHF300000元件
			ehf300000.close();
			
			//取出KEY_ID
			Form.setPSFOODT0_01(UID);
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getPSFOODT0_01());
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
		EHF300000M0F Form = (EHF300000M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);

		try{
			//建立EHF300000元件
			EHF300000 ehf300000 = new EHF300000();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("PSFOODT0_01", key[0]);  //職務系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf300000.queryEditData(queryCondMap);
			
			//取得List
			Form.setEHF300000_DETAIL(ehf300000.queryLexiconDList(queryCondMap));
			
			//關閉EHF300000元件
			ehf300000.close();
			
			if(!dataMap.isEmpty()){
				Form.setPSFOODT0_01((String)dataMap.get("PSFOODT0_01"));  //
				Form.setPSFOODT0_02((String)dataMap.get("PSFOODT0_02"));  //
				Form.setPSFOODT0_03((String) dataMap.get("PSFOODT0_03"));  //
				Form.setPSFOODT0_04((String) dataMap.get("PSFOODT0_04"));  //
				Form.setPSFOODT0_05((String) dataMap.get("PSFOODT0_05"));  //
				Form.setPSFOODT0_06((Integer) dataMap.get("PSFOODT0_06"));  //
				Form.setPSFOODT0_07((Boolean) dataMap.get("PSFOODT0_07"));  //
				Form.setPSFOODT0_08((String) dataMap.get("PSFOODT0_08"));
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				chk_flag = true;
			}
			
			if("SUPPLIER".equals((String)dataMap.get("PSFOODT0_01"))){
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
		EHF300000M0F Form = (EHF300000M0F) form;
		
		try{
			//建立EHF300000元件
			EHF300000 EHF300000 = new EHF300000();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			EHF300000.saveData(dataMap);
			
			//關閉EHF300000元件
			EHF300000.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getPSFOODT0_01());
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
		EHF300000M0F Form = (EHF300000M0F) form;
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getPSFOODT0_01());
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
		EHF300000M0F Form = (EHF300000M0F) form;
		String key = "";
		try{			
			key = String.valueOf(Form.getPSFOODT0_01());
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
		EHF300000M0F Form = (EHF300000M0F) form;
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
		String[] priority = {"是","否"};
		boolean[] flag = {true,false};
		try{
			List datas = new ArrayList();
			for(int i=0;i<priority.length;i++){
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(String.valueOf(flag[i]));
				bform.setItem_value(priority[i]);
				datas.add(bform);
			}
			request.setAttribute("Enable_list", datas);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			List optionlist = new ArrayList();
			List optionlist_sub = null;
			BA_ALLKINDForm bform = null;
			BA_ALLKINDForm bform_sub = null;
		
			String sql = "" +
			" SELECT PSFOODT0_01 AS CLASSKEY, PSFOODT0_04 AS CLASSKEY_VALUE " +
			" FROM FOODT0 " +
			" WHERE 1=1 " +
			" AND PSFOODT0_07 = true " +  //啟用
			" AND PSFOODT0_08 = '"+getLoginUser(request).getCompId()+"' " +  //公司代碼
			" ORDER BY PSFOODT0_06 ";		

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
				bform.setItem_id(rs.getString("CLASSKEY"));
				bform.setItem_value(rs.getString("CLASSKEY")+"/"+rs.getString("CLASSKEY_VALUE"));
				optionlist_sub = new ArrayList();
				
				sql = "" +
				" SELECT PSFOODT1_01 AS CLASSKEY, PSFOODT1_04 AS ITEM_ID, PSFOODT1_05 AS ITEM_VALUE " +
				" FROM FOODT1 " +
				" WHERE 1=1 " +
				" AND PSFOODT1_01 = '"+rs.getString("CLASSKEY")+"' " +  //類別代碼
				" AND PSFOODT1_08 = true " +  //啟用
				" AND PSFOODT1_09 = '"+getLoginUser(request).getCompId()+"' " +  //公司代碼
				" ORDER BY PSFOODT1_07 ";	
				
				rs_sub = stmt_sub.executeQuery(sql);
				
				while(rs_sub.next()){
					bform_sub = new BA_ALLKINDForm();
					bform_sub.setItem_id(rs_sub.getString("ITEM_ID"));
					bform_sub.setItem_value(rs_sub.getString("ITEM_ID")+"/"+rs_sub.getString("ITEM_VALUE"));
					optionlist_sub.add(bform_sub);
				}
				//設定相依清單
				bform.setDependslist(optionlist_sub);
				optionlist.add(bform);
				rs_sub.close();
			}
			rs.close();
			stmt.close();
			stmt_sub.close();
			request.setAttribute("listPSFOODT0_02", optionlist); 
		}catch(Exception e){
			request.setAttribute("listPSFOODT0_02", new ArrayList()); 
			e.printStackTrace();
		}
	}
	
	
	@Override
	protected boolean executeAddDetailData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF300000M0F Form = (EHF300000M0F) form;
	
		try{
			//設定EHF300000 細項 新增語句
			//建立EHF300000元件
			EHF300000 EHF300000 = new EHF300000();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			//新增訂單單明細資料
			EHF300000.addDetailData("LexiconD", detailDataMap);
			
			//關閉EHF300000元件
			EHF300000.close();
			
			//取出KEY_ID
			Form.setPSFOODT1_01((String)detailDataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("PSFOODT1_01");
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
	
	
	public boolean executeDelDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		EHF300000M0F Form = (EHF300000M0F) form;
		
		try{
			//建立EHF300000元件
			EHF300000 EHF300000 = new EHF300000(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap= BeanUtils.describe(Form);
			//dataMap.put("PSFOODT0_01", paramMap.get("PSFOODT0_01"));
			//dataMap.put("PSFOODT1_01", paramMap.get("PSFOODT1_01"));
			//dataMap.put("PSFOODT1_04", paramMap.get("PSFOODT1_04"));
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
//			//檢核明細是否使用中
//			if (Form.selectEHF300000T0Using((String)dataMap.get("PSFOODT1_04"),conn)) {
//				
//				chk_flag = false;
//			}
//			else{
//				
//			}
			EHF300000.delDetailData("delFOODT1", dataMap); //執行刪除FOODT0功能
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf((String)dataMap.get("PSFOODT1_01"));
			Form.setPSFOODT0_01((String)dataMap.get("PSFOODT1_01"));
			paramMap.put(super.KEY_ID, key_id);
			
			chk_flag = true;
			
			//清除新增欄位的內容
			this.cleanAddField(form);				
			
			//關閉EHF300000元件
			EHF300000.close();
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return chk_flag;
	}
	
//	@Override
//	protected boolean executeDelDetailData(Connection conn, ActionForm form,
//			Map paramMap) {
//		// TODO Auto-generated method stub
//		
//		boolean chk_flag = false;
//		String comp_id = (String)paramMap.get(super.COMP_ID);
//		EHF300000M0F Form = (EHF300000M0F) form;
//		
//		try{
//			//建立EHF300000元件
//			EHF300000 EHF300000 = new EHF300000(comp_id);
//			
//			//Ready Del Condition Map
//			Map dataMap = new HashMap();
//			//dataMap.put("PSFOODT0_01", paramMap.get("PSFOODT0_01"));
//			dataMap.put("PSFOODT1_01", paramMap.get("PSFOODT1_01"));
//			dataMap.put("PSFOODT1_04", paramMap.get("PSFOODT1_04"));
//			
//			//設定使用者資訊
//			dataMap.putAll(paramMap);
//			
////			//檢核明細是否使用中
////			if (Form.selectEHF300000T0Using((String)dataMap.get("PSFOODT1_04"),conn)) {
////				
////				chk_flag = false;
////			}
////			else{
////				
////			}
//			EHF300000.delDetailData("delFOODT1", dataMap); //執行刪除FOODT0功能
//			
//			//設定Query Key
//			String[] key_id = new String[1];
//			key_id[0] = String.valueOf((String)dataMap.get("PSFOODT1_01"));
//			Form.setPSFOODT1_01((String)dataMap.get("PSFOODT1_01"));
//			paramMap.put(super.KEY_ID, key_id);
//			
//			chk_flag = true;
//			
//			//清除新增欄位的內容
//			this.cleanAddField(form);				
//			
//			//關閉EHF300000元件
//			EHF300000.close();
//			
//		}catch(Exception e){
//			chk_flag = false;
//			//記錄錯誤訊息
//			this.handleException(e, paramMap);
//			e.printStackTrace();
//		}
//		return chk_flag;
//	}
	

	@Override
	protected Map handleDelDetailKey(String[] key, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF300000M0F Form = (EHF300000M0F) form;
		Map return_map = new HashMap();
		
		try{
			paramMap.put("PSFOODT0_01", Form.getPSFOODT1_01());
			paramMap.put("PSFOODT1_01", Form.getPSFOODT1_01());
			paramMap.put("PSFOODT1_04", Form.getPSFOODT1_04());
			return_map.put("CHK_FLAG", true);
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}		
		return return_map;
	}
	
	
	
}
