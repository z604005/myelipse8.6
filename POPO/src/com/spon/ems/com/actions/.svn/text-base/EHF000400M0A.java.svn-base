package com.spon.ems.com.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.com.forms.EHF000300M0F;
import com.spon.ems.com.forms.EHF000400M0F;
import com.spon.ems.com.models.EHF000400;
import com.spon.utils.struts.form.BA_ALLKINDForm;

import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action>公司班別設定
 *@author maybe
 *@version 1.0
 *@created 2015/11/5 下午3:23:33
 */
public class EHF000400M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立EHF000400元件
			EHF000400 ehf000400 = new EHF000400(comp_id);	
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF000400T0_01", key[0]);  //職務系統代碼
			dataMap.put("COMP_ID", comp_id);
									
			//執行EHF000300刪除功能
			ehf000400.delData(dataMap);
				
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}			
			
			//關閉EHF000400元件
			ehf000400.close();
			
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
		// TODO Auto-generated method stub
		
		EHF000400M0F Form = new EHF000400M0F();
		List list = new ArrayList();
		
		Form.setEHF000400C(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF000400M0F Form = (EHF000400M0F) form;
		Map return_map = new HashMap();
		EHF000400M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF000400M0F> querylist = new ArrayList<EHF000400M0F>();
			//建立EHF000400元件
			EHF000400 ehf000400 = new EHF000400(comp_id);						
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼						
			
			//使用元件進行處理
			List ehf000400_queryList = ehf000400.queryData(queryCondMap);
			
			Iterator it = ehf000400_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF000400M0F();
				
				bean.setEHF000400T0_01(String.valueOf((Integer)tempMap.get("EHF000400T0_01")));  //班別序號
				bean.setEHF000400T0_03((String)tempMap.get("EHF000400T0_03"));  //班別代號
				bean.setEHF000400T0_04((String)tempMap.get("EHF000400T0_04"));  //班別													
				bean.setEHF000400T0_05(((String)tempMap.get("EHF000400T0_05")).substring(0, 2)+":"+((String)tempMap.get("EHF000400T0_05")).substring(2, 4));  //上班時間								
				bean.setEHF000400T0_06(((String)tempMap.get("EHF000400T0_06")).substring(0, 2)+":"+((String)tempMap.get("EHF000400T0_06")).substring(2, 4));  //下班時間				
				bean.setEHF000400T0_07(((String)tempMap.get("EHF000400T0_07")).substring(0, 2)+":"+((String)tempMap.get("EHF000400T0_07")).substring(2, 4)+" ~ "
									   +((String)tempMap.get("EHF000400T0_08")).substring(0, 2)+":"+((String)tempMap.get("EHF000400T0_08")).substring(2, 4) );	 //午休時間
				if("0".equals((String)tempMap.get("EHF000400T0_09"))){
					bean.setEHF000400T0_09("否");
				}else if("1".equals((String)tempMap.get("EHF000400T0_09"))){    //是否系統預設
					bean.setEHF000400T0_09("是");
				}
				if("0".equals((String)tempMap.get("EHF000400T0_18"))){
					bean.setEHF000400T0_18("否");
				}else if("1".equals((String)tempMap.get("EHF000400T0_18"))){    //是否時薪班別
					bean.setEHF000400T0_18("是");
				}
				bean.setEHF000400T0_10((String)tempMap.get("EHF000400T0_10"));	 //備註
				
				querylist.add(bean);
				
			}
			
			//設定querylist
			Form.setEHF000400C(querylist);
			
			//關閉EHF000400元件
			ehf000400.close();
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			this.cleanAddField(form);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}

	private void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		
		EHF000400M0F Form = (EHF000400M0F) form;
		
		Form.setEHF000400T0_03("");
		Form.setEHF000400T0_04("");
		Form.setEHF000400T0_09("");
		
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		//產生系統預設類別
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
			request.setAttribute("listEHF000400T0_09", datas);
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}
