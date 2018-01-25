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

import com.spon.ems.abs.actions.QueryAction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.ems.popo.forms.EHF300300M0F;
import com.spon.ems.popo.models.EHF300000;
import com.spon.ems.popo.models.EHF300300;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 早中晚餐定價
 * (Action)
 */
public class EHF300300M0A extends QueryAction {


	private BA_TOOLS tool;
	
	public EHF300300M0A(){
		tool = BA_TOOLS.getInstance();

	}
	
	
	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);

		try{
			EHF300300 ehf300300 = new EHF300300(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF300300T0_01", key[0]);  //代碼
			dataMap.put("COMP_ID", comp_id);
			
			ehf300300.delData(dataMap);
			
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			ehf300300.close();
			
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
		// TODO Auto-generated method stubEHF300000M0F Form = new EHF300000M0F();
		EHF300300M0F Form = new EHF300300M0F();
		List list = new ArrayList();
		Form.setEHF300300C(list);
		return (ActionForm) Form;
	}

	
	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF300300M0F Form1 = (EHF300300M0F) form;
		Map return_map = new HashMap();
		EHF300300M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF300300M0F> querylist = new ArrayList<EHF300300M0F>();
			
			//建立EHF300000元件
			EHF300300 ehf300300 = new EHF300300(comp_id);
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form1);
			
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			List ehf300300_queryList = ehf300300.queryData(queryCondMap);
			
			Iterator it = ehf300300_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				tempMap = (Map) it.next();
				bean = new EHF300300M0F();
				bean.setEHF300300T0_01((String)tempMap.get("EHF300300T0_01"));
				bean.setEHF300300T0_02_TXT((String)tempMap.get("EHF300300T0_02"));
				bean.setEHF300300T0_03((Integer)tempMap.get("EHF300300T0_03"));
				bean.setEHF300300T0_04((String)tempMap.get("EHF300300T0_04"));

				querylist.add(bean);
			}
			
			//設定querylist
			Form1.setEHF300300C(querylist);
			
			//關閉EHF000700元件
			ehf300300.close();
			
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
		}
	}
