package com.spon.ems.com.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.com.forms.EHF000200M0F;
import com.spon.ems.com.models.EHF000200;
import com.spon.ems.hr.forms.EHF010108M0F;
import com.spon.ems.hr.models.EHF010108;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * (Action)部門基本資料
 * @author maybe
 * @version 1.0
 * @created 2015/8/18 上午9:26:06
 */
public class EHF000200M0A extends QueryAction {
	
	//private HR_TOOLS hr_tools;
	
	public EHF000200M0A()
	{
		//hr_tools = HR_TOOLS.getInstance();
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			EHF000200 ehf000200 = new EHF000200(comp_id);
			
			if(this.chKInitial(conn, key[0], comp_id)){
				request.setAttribute("ERR_MSG", "該筆資料為組織樹樹根，請勿刪除!!");
			}else{
				
				//Ready Del Condition Map
				Map dataMap = new HashMap();
				dataMap.put("EHF000200T0_01", key[0]);  //
				dataMap.put("COMP_ID", comp_id);
				
				//執行EHF010108刪除功能
				ehf000200.delData(dataMap);
				
				if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
					chk_flag = true;
				}
				
			}
			
			ehf000200.close();
			
			//控制刪除後的顯示畫面
			paramMap.put(super.DEL_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
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
		
		EHF000200M0F Form = new EHF000200M0F();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{		
			if(this.chBlankDatabase(conn)){
			
				HR_TOOLS hr_tools = new HR_TOOLS();
			
				Map compMap = hr_tools.getCompNameMap(conn, comp_id);
			
				hr_tools.close();
			
				EHF000200 ehf000200 = new EHF000200(comp_id);
			
				//建立新增資料Map
				Map dataMap = new HashMap();
				//設定使用者資訊
				dataMap.putAll(paramMap);
			
				dataMap.put("EHF000200T0_02", (String)compMap.get("COMP_SHOW_ID"));	//本層部門代號
				dataMap.put("EHF000200T0_03", (String)compMap.get("COMP_NAME_C"));	//本層部門名稱
				dataMap.put("EHF000200T0_04", "#");	//上層部門系統代號
				dataMap.put("EHF000200T0_05", "#");	//上層部門名稱
				dataMap.put("EHF000200T0_07", "");	//部門簡稱
				dataMap.put("EHF000200T0_08", (String)compMap.get("COMP_USED_DT"));	//成立日期
				dataMap.put("EHF000200T0_09", "");	//結束日期
				dataMap.put("EHF000200T0_10", "");	//部門簡介
			
				ehf000200.addDataInit(dataMap);
			
				//關閉元件
				ehf000200.close();
			
				paramMap.put(this.INIT_FORWARD_CONFIG,"queryForm");
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		
		boolean chk_flag = false;
		EHF000200M0F Form = (EHF000200M0F) form;
		Map return_map = new HashMap();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{	
			//建立空清單
			List<EHF000200M0F> querylist = new ArrayList<EHF000200M0F>();
			
			//建立元件
			EHF000200 ems_OrgTree = new EHF000200(comp_id);
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			
			//使用加班申請元件進行處理
			List queryList = ems_OrgTree.queryData(queryCondMap);
			
			if(queryList.size() > 0){
				chk_flag = true;
			}
			Form.setEHF000200T0_LIST(queryList);
			
			this.cleanAddField(form);
			
			//關閉元件
			ems_OrgTree.close();

			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", Form);
			
			request.setAttribute("MSG","查詢成功!!"+ "    " +
					""+(request.getAttribute("MSG")==null?"":request.getAttribute("MSG")));
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}

	private void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		
		EHF000200M0F Form = (EHF000200M0F) form;
		
		Form.setEHF000200T0_04("");
		Form.setEHF000200T0_04_1("");
		Form.setEHF000200T0_05("");
		Form.setEHF000200T0_03("");
		Form.setEHF000200T0_02("");
		Form.setEHF000200T0_08("");
		Form.setEHF000200T0_09("");
		
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 檢查資料庫是否完全無資料
	 * @param conn
	 * @param errors
	 * @param request
	 * @param TABLE 
	 * @return
	 */
	private boolean chBlankDatabase(Connection conn) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		
		try {
			
			String sql = "";

			sql += " SELECT * FROM EHF000200T0" + 
		       " WHERE 1 = 1 ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if (!rs.next()) {
				chk_flag=true;
			}
			
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return chk_flag;
	}
	
	/**
	 * 檢查預刪除的資料是否為樹根
	 * @param conn
	 * @param errors
	 * @param request
	 * @param TABLE 
	 * @return
	 */
	private boolean chKInitial(Connection conn, String HR_DepartmentSysNo, String comp_id) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		
		try {			
			String sql = "";

			sql += " SELECT * FROM EHF000200T0" + 
		       " WHERE 1 = 1 " +
		       " AND EHF000200T0_01 = '"+HR_DepartmentSysNo+"'" +
			   " AND HR_CompanySysNo = '"+comp_id+"'" +//公司代碼
		       " AND EHF000200T0_04 = '#'" +
		       " AND EHF000200T0_05 = '#'" ;
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				chk_flag=true;
			}
			
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
		return chk_flag;
	}

}
