package com.spon.ems.hr.actions;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.hr.forms.EHF010106M0F;
import com.spon.ems.hr.forms.EHF010109M0F;
import com.spon.ems.hr.models.EHF010106;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class EHF010106M0A extends QueryAction {
	
	private BA_TOOLS tool;
	//private BA_EMS_TOOLS ems_tool;
	public EHF010106M0A()
	{
		tool = BA_TOOLS.getInstance();
		//ems_tool = BA_EMS_TOOLS.getInstance();
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010106元件
			EHF010106 ehf010106 = new EHF010106(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("HR_EmployeeSysNo", key[0]);  //員工系統代碼
			dataMap.put("COMP_ID", comp_id);
			
			//執行EHF010106刪除功能
			ehf010106.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			
			//關閉EHF010106元件
			ehf010106.close();
			
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
		
		EHF010106M0F Form = new EHF010106M0F();
		List list = new ArrayList();
		
		Form.setEHF010106T0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF010106M0F Form = (EHF010106M0F) form;
		Map return_map = new HashMap();
		EHF010106M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF010106M0F> querylist = new ArrayList<EHF010106M0F>();
			
			//建立EHF010106元件
			EHF010106 ehf010106 = new EHF010106(comp_id);
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf010106_queryList = ehf010106.queryData(queryCondMap);
			
			Iterator it = ehf010106_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF010106M0F();
				
				bean.setHR_EmployeeSysNo((String)tempMap.get("HR_EmployeeSysNo"));	//員工系統代碼
				bean.setHR_DepartmentName((String)tempMap.get("HR_DepartmentName"));	//部門名稱
				bean.setHR_EmployeeNo((String)tempMap.get("HR_EmployeeNo"));	//員工工號
				bean.setEHF010106T0_04((String)tempMap.get("EHF010106T0_04"));	//員工姓名
				if("1".equals((String)tempMap.get("EHF010106T0_06"))){
					bean.setEHF010106T0_06("正式員工");	//員工類別
				}else if("2".equals((String)tempMap.get("EHF010106T0_06"))){
					bean.setEHF010106T0_06("臨時員工");	//員工類別
				}else if("3".equals((String)tempMap.get("EHF010106T0_06"))){
					bean.setEHF010106T0_06("外籍勞工");	//員工類別
				}else if("4".equals((String)tempMap.get("EHF010106T0_06"))){
					bean.setEHF010106T0_06("外籍配偶");	//員工類別
				}else if("5".equals((String)tempMap.get("EHF010106T0_06"))){
					bean.setEHF010106T0_06("工讀生");	//員工類別
				}
				if("1".equals((String)tempMap.get("EHF010106T1_01"))){
					bean.setEHF010106T1_01("在職");	//職務狀況
				}else if("2".equals((String)tempMap.get("EHF010106T1_01"))){
					bean.setEHF010106T1_01("離職");	//職務狀況
				}else if("3".equals((String)tempMap.get("EHF010106T1_01"))){
					bean.setEHF010106T1_01("復職");	//職務狀況
				}else if("4".equals((String)tempMap.get("EHF010106T1_01"))){
					bean.setEHF010106T1_01("留職停薪");	//職務狀況
				}else{
					bean.setEHF010106T1_01("");	//職務狀況
				}				
												
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF010106T0_LIST(querylist);
			
			//關閉EHF010106元件
			ehf010106.close();
			
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
		
		//request.setAttribute("listEHF010106T0_06", ems_tool.getOptions(conn, true, "EMPTYPE", getLoginUser(request).getCompId()));
		request.setAttribute("listEHF010106T0_06", this.getSelectOptions(conn,true, "EMPTYPE","and FM010501_06='"+userform(request).getSC0030_14()+"'"));//員工類別
		request.setAttribute("listEHF010106T1_01", this.getSelectOptions(conn,true, "JOBTYPE","and FM010501_06='"+userform(request).getSC0030_14()+"'"));//職務狀況

	}

}
