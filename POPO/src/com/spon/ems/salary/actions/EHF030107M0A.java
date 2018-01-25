package com.spon.ems.salary.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.salary.forms.EHF030107M0F;
import com.spon.ems.salary.models.EHF030107;

/**
 * <Action> 加班費規則設定
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030107M0A extends QueryAction {

	public EHF030107M0A() {

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
		EHF030107M0F Form = (EHF030107M0F) form;
		Form = new EHF030107M0F();
		
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
		EHF030107M0F Form = (EHF030107M0F) form;
		Map return_map = new HashMap();
		EHF030107M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{	
			//建立空清單
			List<EHF030107M0F> querylist = new ArrayList<EHF030107M0F>();
			
			//建立EHF030107元件
			EHF030107 ehf030107 = new EHF030107(comp_id);
			
			//建立EHF030107M0 查詢資料
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			//處理條件設定
			queryCondMap.put("EHF030107T0_02", Form.getEHF030107T0_02());  //加班費規則代號
			queryCondMap.put("EHF030107T0_03", Form.getEHF030107T0_03());  //加班費規則名稱
			//queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			
			//使用加班費規則設定元件進行處理
			List ehf030107_queryList = ehf030107.queryData(queryCondMap);
			
			if(ehf030107_queryList.size() > 0){
				chk_flag = true;
			}
			
			//設定querylist
			Form.setEHF030107T0_LIST(ehf030107_queryList);
			
			//關閉EHF030107元件
			ehf030107.close();
			
			
			Form.setEHF030107T0_02("");
			Form.setEHF030107T0_03("");
			
			
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
			//建立EHF030107元件
			EHF030107 ehf030107 = new EHF030107(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF030107T0_01", key[0]);  //加班費規則序號
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			//執行EHF030107刪除功能
			ehf030107.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			
			//關閉EHF030107元件
			ehf030107.close();
			
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