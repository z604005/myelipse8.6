package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF310100M0F;
import com.spon.ems.popo.models.EHF310100;
import com.spon.utils.util.EMS_ACCESS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 客戶需求單
 * (Action)
 */
public class EHF310100M0A extends QueryAction {

	private EMS_ACCESS ems_access;
	
	public EHF310100M0A(){
		ems_access = EMS_ACCESS.getInstance();
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
		EHF310100M0F Form = new EHF310100M0F();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		List list = new ArrayList();
		
		Form.setEHF310100_C(list);
		//判斷身分別
		ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF310100M0F Form = (EHF310100M0F) form;
		Map return_map = new HashMap();
		EHF310100M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF310100M0F> querylist = new ArrayList<EHF310100M0F>();
			
			//建立EHF310100元件
			EHF310100 ehf310100 = new EHF310100(comp_id);

			//住宅資訊
			Map residentialMap = ehf310100.getResidential(comp_id);
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf310100_queryList = ehf310100.queryData(queryCondMap);
			
			Iterator it = ehf310100_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF310100M0F();
				
				bean.setEHF310100T0_01((String)tempMap.get("EHF310100T0_01"));	//客戶需求單單號
				bean.setEHF310100T0_02((String)tempMap.get("EHF310100T0_02"));	//檔案編號
				bean.setEHF310100T0_03((String)tempMap.get("EHF310100T0_03"));	//櫃號
				bean.setEHF310100T0_04((String)tempMap.get("EHF310100T0_04"));	//產婦姓名
				bean.setEHF310100T0_17((String)residentialMap.get((String)tempMap.get("EHF310100T0_17")));	//路線
				bean.setEHF310100T0_21((String)tempMap.get("EHF310100T0_21"));	//行動電話
				bean.setEHF310100T0_31((String)tempMap.get("EHF310100T0_31"));	//地址
				
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF310100_C(querylist);
			
			//關閉EHF310100元件
			ehf310100.close();
			
			Form.setEHF310100T0_03("");	//櫃號
			Form.setEHF310100T0_21("");	//行動電話(產婦)
			Form.setEHF310100T0_04("");	//產婦姓名
			Form.setEHF310100T0_31("");	//地址
			Form.setEHF310100T0_08_B("");	//訂餐日期起
			Form.setEHF310100T0_08_E("");	//訂餐日期迄
			
			
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", Form);
			
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
