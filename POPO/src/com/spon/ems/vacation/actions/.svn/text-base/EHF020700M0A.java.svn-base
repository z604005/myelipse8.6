package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.vacation.forms.EHF020200M0F;
import com.spon.ems.vacation.forms.EHF020700M0F;
import com.spon.ems.vacation.models.EHF020700;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action>未打卡單申請作業
 *@author maybe
 *@version 1.0
 *@created 2015/11/19 下午5:22:14
 */
public class EHF020700M0A extends QueryAction {
	
	private EMS_ACCESS ems_access;
	
	public EHF020700M0A(){
		ems_access = EMS_ACCESS.getInstance();
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			EHF020700 ehf020700 = new EHF020700(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF020700T0_01", key[0]);  //職務系統代碼
			dataMap.put("COMP_ID", comp_id);
			
			//檢查是否有附加檔案
			Map chkuploadMap = new HashMap();
			//檢查是否順利刪除附加檔案
			Boolean del_flag = false;
			
			chkuploadMap = ehf020700.queryEditData(dataMap);
			
			//檢查是否有附加檔案
			if(!"".equals((String)chkuploadMap.get("EHF020700T0_13"))){
				chkuploadMap.put("COMP_ID", comp_id);
				chkuploadMap.put("chkupload", (String)chkuploadMap.get("EHF020700T0_13"));
				//刪除附加檔案
				ehf020700.delAttachedFile(chkuploadMap);
				request.setAttribute("MSG", "已刪除表單與附加檔案!!");
				del_flag = true;
			}else{
				request.setAttribute("MSG", "已刪除表單，此表單無附加檔案!!");
				del_flag = true;
			}
			
			if(del_flag){
				
				//執行EHF020700刪除功能
				ehf020700.delData(dataMap);
				
			}			
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}	
			
			ehf020700.close();
			
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
		
		EHF020700M0F Form = new EHF020700M0F();
		List list = new ArrayList();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String employee_id = (String)paramMap.get(super.EMPLOYEE_ID);
		
		try{
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			Form.setEHF020700T0_03( (String) ((Map)empMap.get(employee_id)).get("USER_CODE") );  //申請人員工工號
			Form.setEHF020700T0_03_SHOW( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_ID") );
			Form.setEHF020700T0_03_TXT( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME") );  //申請人姓名
			Form.setEHF020700T0_04( (String) ((Map)empMap.get(employee_id)).get("DEPT_ID") );  //申請人部們組織代號
			Form.setEHF020700T0_04_SHOW( (String) ((Map)empMap.get(employee_id)).get("SHOW_DEPT_ID") );
			Form.setEHF020700T0_04_TXT( (String) ((Map)empMap.get(employee_id)).get("SHOW_DESC") );  //申請人部們名稱
			
			hr_tools.close();
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
			Form.setEHF020700C(list);
			
		}catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF020700M0F Form = (EHF020700M0F) form;
		Map return_map = new HashMap();
		EHF020700M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF020700M0F> querylist = new ArrayList<EHF020700M0F>();
			
			EHF020700 ehf020700 = new EHF020700(comp_id);
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			Map empMap = null;
			Map depMap = null;
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			empMap = hr_tools.getEmpNameMap(comp_id);
			depMap = hr_tools.getDepNameMap(comp_id);
			
			//使用元件進行處理
			List ehf020700_queryList = ehf020700.queryData(queryCondMap);
			
			Iterator it = ehf020700_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			ehf020700.close();
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF020700M0F();
				
				bean.setEHF020700T0_01((String)tempMap.get("EHF020700T0_01"));  //表單編號
				bean.setEHF020700T0_07((String)tempMap.get("EHF020700T0_07"));  //申請日期
				bean.setEHF020700T0_03_TXT((String) ((Map)empMap.get((String)tempMap.get("EHF020700T0_03"))).get("EMPLOYEE_NAME"));  //申請人
				if("01".equals((String)tempMap.get("EHF020700T0_09"))){
					bean.setEHF020700T0_09("上班未打卡");  //未打卡類別
				}else if("02".equals((String)tempMap.get("EHF020700T0_09"))){
					bean.setEHF020700T0_09("下班未打卡");  //未打卡類別
				}else if("03".equals((String)tempMap.get("EHF020700T0_09"))){
					bean.setEHF020700T0_09("全天未打卡");  //未打卡類別
				}else{
					bean.setEHF020700T0_09("");  //未打卡類別
				}
				bean.setEHF020700T0_08((String)tempMap.get("NO_CARD_DATE")+" "+((String)tempMap.get("START_TIME")).substring(0, 2)+"點 " +
						((String)tempMap.get("START_TIME")).substring(2, 4)+"分"+ " ~ " +
						((String)tempMap.get("END_TIME")).substring(0, 2)+"點 "+((String)tempMap.get("END_TIME")).substring(2, 4)+"分 ");  //日期區間					
				bean.setEHF020700T0_14_DESC((String)tempMap.get("EHF020700T0_14_DESC"));  //表單狀態
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF020700C(querylist);
			
			//關閉EHF020700元件
			ehf020700.close();
			hr_tools.close();
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
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
		
		//產生系統預設類別
		try{
			List datas = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0001");
			bform.setItem_value("填寫中");
			datas.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("0002");
			bform.setItem_value("表單送審(草稿呈核)");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0004");
			bform.setItem_value("審核通過");
			datas.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0005");
			bform.setItem_value("審核駁回");
			datas.add(bform);*/
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0006");
			bform.setItem_value("完成");
			datas.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("0008");
			bform.setItem_value("抽單");
			datas.add(bform);*/
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0009");
			bform.setItem_value("作廢");
			datas.add(bform);
			request.setAttribute("EHF020700T0_14_list", datas);
		}catch(Exception e) {
			System.out.println(e);
		}

	}

}
