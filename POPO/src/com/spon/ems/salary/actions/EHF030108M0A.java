package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.salary.forms.EHF030108M0F;
import com.spon.ems.salary.models.EHF030108;

/**
 * <Action> 全勤獎金扣費規則設定
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030108M0A extends QueryAction {

	public EHF030108M0A() {

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
				
				try{
					
				}catch(Exception e){
					//記錄錯誤訊息
					e.printStackTrace();
				}
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		EHF030108M0F Form = (EHF030108M0F) form;
		Form = new EHF030108M0F();
		
		try{

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
		
		boolean chk_flag = false;
		EHF030108M0F Form = (EHF030108M0F) form;
		Map return_map = new HashMap();
		EHF030108M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{	
			//建立空清單
			List<EHF030108M0F> querylist = new ArrayList<EHF030108M0F>();
			
			//建立EHF030108元件
			EHF030108 ehf030108 = new EHF030108(comp_id);
			
			//建立EHF030108M0 查詢資料
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			//處理條件設定
			queryCondMap.put("EHF030108T0_02", Form.getEHF030108T0_02());  //全勤獎金扣費規則代號
			queryCondMap.put("EHF030108T0_03", Form.getEHF030108T0_03());  //全勤獎金扣費規則名稱
			//queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			
			//使用全勤獎金扣費規則設定元件進行處理
			List ehf030108_queryList = ehf030108.queryData(queryCondMap);
			
			if(ehf030108_queryList.size() > 0){
				chk_flag = true;
			}
			
			//設定querylist
			Form.setEHF030108T0_LIST(ehf030108_queryList);
			
			//關閉EHF030108元件
			ehf030108.close();
			
			Form.setEHF030108T0_02("");
			Form.setEHF030108T0_03("");
			
			
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
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF030108元件
			EHF030108 ehf030108 = new EHF030108(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF030108T0_01", key[0]);  //全勤獎金扣費規則序號
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行EHF030108刪除功能
			ehf030108.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			
			//關閉EHF030108元件
			ehf030108.close();
			
			//控制刪除後的顯示畫面
			paramMap.put(super.DEL_FORWARD_CONFIG, super.FORWARD_INIT);
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
}