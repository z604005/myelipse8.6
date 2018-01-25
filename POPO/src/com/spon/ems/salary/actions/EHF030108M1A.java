package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionForm;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.salary.forms.EHF030108M0F;
import com.spon.ems.salary.models.EHF030108;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action> 全勤獎金扣費規則設定作業

 */
public class EHF030108M1A extends EditAction {

	public EHF030108M1A() {
		
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
			
			//產生 發生頻率選單
			try{
				List datas = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("05");
				bform.setItem_value("天");
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("週");
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("兩週(併入第二週)");//"兩週(第一週日數=第五週日數時第一週併入第二週)"
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("03");
				bform.setItem_value("兩週(併入第四週)");
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("04");
				bform.setItem_value("月");
				datas.add(bform);
				request.setAttribute("listFREQ", datas);
			}catch(Exception e) {
				System.out.println(e);
			}
			
			//產生遲到早退扣費基準單位
			try{
				List datas = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("依每次分鐘條件");
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("依累計分鐘條件");
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("03");
				bform.setItem_value("依次數條件");
				datas.add(bform);
				request.setAttribute("list_EHF030108T0_06", datas);
			}catch(Exception e) {
				System.out.println(e);
			}
			
			//產生曠職扣費基準單位
			try{
				List datas = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("01");
				bform.setItem_value("依每次小時條件");
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("02");
				bform.setItem_value("依累計小時條件");
				datas.add(bform);
				bform = new BA_ALLKINDForm();
				bform.setItem_id("03");
				bform.setItem_value("依次數條件");
				datas.add(bform);
				request.setAttribute("list_EHF030108T0_10", datas);
			}catch(Exception e) {
				System.out.println(e);
			}
			
			
			//產生假別
			try{
				
				List datas = new ArrayList();			
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);			
				String sql = " SELECT EHF020100T0_03, EHF020100T0_04 FROM EHF020100T0 " +
				 			 " WHERE 1=1 " +
				 			 " AND EHF020100T0_08 = '"+getLoginUser(request).getCompId()+"' " ;		
				ResultSet rs=stmt.executeQuery(sql);			
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("");
				bform.setItem_value("一請選擇一");
				datas.add(bform);			
				while(rs.next()){
					bform = new BA_ALLKINDForm();
					bform.setItem_id(rs.getString("EHF020100T0_03"));
					bform.setItem_value(rs.getString("EHF020100T0_04"));
					datas.add(bform);					
				}			
				rs.close();
				stmt.close();
				
				request.setAttribute("list_EHF030108T1_02", datas);
			}catch(Exception e) {
				System.out.println(e);
			}
			
			
			
			
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
		EHF030108M0F Form = (EHF030108M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		try{
			//設定EHF030108M1 新增語句
			
			//建立EHF030108元件
			EHF030108 ehf030108 = new EHF030108(comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行新增資料
			ehf030108.addData(dataMap);
			
			//關閉EHF030108元件
			ehf030108.close();
			
			//取出KEY_ID
			Form.setEHF030108T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF030108T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//新增完成
			chk_flag = true;
			request.setAttribute("SHOW", "add");
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
		
		EHF030108M0F Form = (EHF030108M0F) form;
		
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
		EHF030108M0F Form = (EHF030108M0F) form;
		
		try{
			//設定EHF030108M1 儲存語句
			//建立EHF030108元件
			EHF030108 ehf030108 = new EHF030108();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf030108.saveData(dataMap);
			
			
			//關閉EHF030108元件
			ehf030108.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF030108T0_01());
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
		EHF030108M0F Form = (EHF030108M0F) form;
		//Form = new EHF030108M0F();
		
		try{
			//遲到早退扣費基準
			Form.setEHF030108T0_05("0");
			//遲到早退扣費金額
			Form.setEHF030108T0_07("0");
			
			//曠職扣費基準
			Form.setEHF030108T0_09("0");
			//曠職扣費金額
			Form.setEHF030108T0_11("0");
			
			
			
			//是否啟用
			Form.setEHF030108T0_12("1");  //預設 = 是
			
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
		
		
		EHF030108M0F Form = (EHF030108M0F) form;
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
		EHF030108M0F Form = (EHF030108M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		Map return_map = new HashMap();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		try{
			//建立EHF030108M1 查詢語句
			//建立EHF030108元件
			EHF030108 ehf030108 = new EHF030108();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF030108T0_01", (key[0]));  //全勤獎金扣費規則序號
			queryCondMap.putAll(paramMap);  //放入使用者資訊
			
			Map dataMap = ehf030108.queryEditData(queryCondMap);
			
			//關閉EHF030108 元件
			ehf030108.close();
			
			if(!dataMap.isEmpty()){
				
				BeanUtils.copyProperties(Form, dataMap);
				
				chk_flag = true;
			}
			List EHF030108T1_LIST = ehf030108.query_EHF030108T1_Data(queryCondMap);
			
			
			Form.setEHF030108T1_02("");
			Form.setEHF030108T1_03("");
			Form.setEHF030108T1_04("0");
			
			Form.setEHF030108T1_06("0");
			
			Form.setEHF030108T1_LIST(EHF030108T1_LIST);
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			request.setAttribute("SHOW", "add");
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
		EHF030108M0F Form = (EHF030108M0F) form;
		String key = "";
		try{			
			key = String.valueOf(Form.getEHF030108T0_01());
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
		EHF030108M0F Form = (EHF030108M0F) form;
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF030108T0_01());
			paramMap.put(super.KEY_ID, key_id);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 新增明細
	 */
	@Override
	public boolean executeAddDetailData(Connection conn, String type,ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String user_id = (String)paramMap.get(super.USER_ID);
		String user_code = (String)paramMap.get(super.USER_CODE);
		boolean chk_flag = false;
		EHF030108M0F Form = (EHF030108M0F) form;
		List EHF030403T0_LIST = new ArrayList();//儲存員工資訊
		try {
			EHF030108 ehf030108 = new EHF030108();

			//全勤獎金扣費規則序號
			Form.setEHF030108T1_01(Form.getEHF030108T0_01());
			
			// 建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form); 
			detailDataMap.putAll(paramMap);

			//檢查是否有相同的扣費明細
			if (!ehf030108.SELECT_EHF030108T1(comp_id, detailDataMap)) {
				if ("EHF030108T1".equals(type)) {
					// 新增全勤獎金扣費規則明細
					ehf030108.addDetailData(type, detailDataMap);
				}
			}
			else{
				chk_flag = false;
				return chk_flag;
			}
			
			

			// 關閉EHF030604 元件
			ehf030108.close();

			// 設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);

			// 設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String) detailDataMap.get("EHF030604T0_01");
			paramMap.put(super.KEY_ID, key_id);


			
			chk_flag = true;
		} catch (Exception e) {
			// 記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}

		return chk_flag;
	}


	/**
	 *刪除明細 
	 */
	public boolean executeDelDetailData(Connection conn, String type,ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub

		EHF030108M0F Form = (EHF030108M0F) form;
		boolean chk_flag = false;

		try {
			// 執行細項資料刪除
			// 建立EHF030604元件
			EHF030108 ehf030108 = new EHF030108();

			// 建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);

			// 設定使用者資訊
			detailDataMap.putAll(paramMap);

			if ("EHF030108T1".equals(type)) {
				// 刪除獎金計算明細
				// 刪除獎金計算明細資訊
				ehf030108.delDetailData(type, detailDataMap);
				detailDataMap.get("EHF030108T1_01");
			}

			// 關閉EHF030604 元件
			ehf030108.close();

			// 設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String) detailDataMap.get("EHF030108T0_01");
			paramMap.put(super.KEY_ID, key_id);

			// 設定Tab資訊
			// super.addCurrentTabConfig(paramMap, "ESF010200M1_Tab_01", "採購明細",
			// false);

			chk_flag = true;

		} catch (Exception e) {
			chk_flag = false;
			// 記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}

		return chk_flag;
	}

	
	
	
}