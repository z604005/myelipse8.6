package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.ems.abs.actions.QueryAction;

import com.spon.ems.popo.forms.EHF320200M0F;
import com.spon.ems.popo.models.EHF320200;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 上菜順序
 * (Action)
 */
public class EHF320200M0A extends QueryAction {


	private BA_TOOLS tool;
	
	public EHF320200M0A(){
		tool = BA_TOOLS.getInstance();

	}
	
	
	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);

		try{
			EHF320200 ehf320200 = new EHF320200(comp_id);
			String[] key_id = key[0].split("/"); //取到的KEY "EHF320200T0_01/EHF320200T0_02" 分成2個
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF320200T0_01", key_id[0]);  //
			dataMap.put("EHF320200T0_02", key_id[1]);
			dataMap.put("COMP_ID", comp_id);
			
			ehf320200.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			ehf320200.close();
			
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
	
	
	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stubEHF320200M0F Form = new EHF320200M0F();
		EHF320200M0F Form = new EHF320200M0F();
		List list = new ArrayList();
		Form.setEHF320200_C(list);
		return (ActionForm) Form;
	}

	
	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF320200M0F Form1 = (EHF320200M0F) form;
		Map return_map = new HashMap();
		EHF320200M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF320200M0F> querylist = new ArrayList<EHF320200M0F>();
			
			//建立EHF320200元件
			EHF320200 ehf320200 = new EHF320200(comp_id);
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form1);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			List EHF320200_C = ehf320200.queryData(queryCondMap);

			Iterator it = EHF320200_C.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				tempMap = (Map) it.next();
				bean = new EHF320200M0F();
				bean.setEHF320200T0_01C(Integer.toString((Integer)tempMap.get("EHF320200T0_01")));
				bean.setEHF320200T0_02C(Integer.toString((Integer)tempMap.get("EHF320200T0_02")));
				bean.setEHF320200T0_03_TXT((String)tempMap.get("EHF320200T0_03_TXT"));
				bean.setKEY_01C02C(Integer.toString((Integer)tempMap.get("EHF320200T0_01"))+"/"+Integer.toString((Integer)tempMap.get("EHF320200T0_02"))); //要傳的KEY值
				querylist.add(bean);
			}
			
			//設定querylist
			Form1.setEHF320200_C(querylist);
			
			//關閉EHF000700元件
			ehf320200.close();
			
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
	}


	
}
