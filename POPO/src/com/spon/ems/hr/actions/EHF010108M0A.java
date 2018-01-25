package com.spon.ems.hr.actions;

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
import com.spon.ems.hr.forms.EHF010108M0F;
import com.spon.ems.hr.models.EHF010108;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;
/**
 * 部門資料查詢
 * @author maybe
 *
 */
public class EHF010108M0A extends QueryAction{
	
	//private HR_TOOLS hr_tools;
	
	public EHF010108M0A()
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
			EHF010108 ehf010108 = new EHF010108(comp_id);
			
			if(this.chKInitial(conn, key[0], comp_id)){
				request.setAttribute("ERR_MSG", "該筆資料為組織樹樹根，請勿刪除!!");
			}else{
				
				//Ready Del Condition Map
				Map dataMap = new HashMap();
				dataMap.put("HR_DepartmentSysNo", key[0]);  //
				dataMap.put("COMP_ID", comp_id);
				
				//執行EHF010108刪除功能
				ehf010108.delData(dataMap);
				
				if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
					chk_flag = true;
				}
				
			}
			
			ehf010108.close();
			
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
		
		EHF010108M0F Form = new EHF010108M0F();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			if(this.chBlankDatabase(conn)){
			
				HR_TOOLS hr_tools = new HR_TOOLS();
			
				Map compMap = hr_tools.getCompNameMap(conn, comp_id);
			
				hr_tools.close();
			
				EHF010108 ehf010108 = new EHF010108(comp_id);
			
				//建立新增資料Map
				Map dataMap = new HashMap();
				//設定使用者資訊
				dataMap.putAll(paramMap);
			
				dataMap.put("HR_DepartmentNo", (String)compMap.get("COMP_SHOW_ID"));	//本層部門代號
				dataMap.put("HR_DepartmentName", (String)compMap.get("COMP_NAME_C"));	//本層部門名稱
				dataMap.put("HR_UpDepartmentSysNo", "#");	//上層部門系統代號
				dataMap.put("HR_UpDepartmentName", "#");	//上層部門名稱
				dataMap.put("EHF010108T0_01", "");	//部門簡稱
				dataMap.put("EHF010108T0_02", (String)compMap.get("COMP_USED_DT"));	//成立日期
				dataMap.put("EHF010108T0_03", "");	//結束日期
				dataMap.put("EHF010108T0_04", "");	//部門簡介
			
				ehf010108.addDataInit(dataMap);
			
				//關閉元件
				ehf010108.close();
			
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
		EHF010108M0F Form = (EHF010108M0F) form;
		Map return_map = new HashMap();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{	
			//建立空清單
			List<EHF010108M0F> querylist = new ArrayList<EHF010108M0F>();
			
			//建立元件
			EHF010108 ems_OrgTree = new EHF010108(comp_id);
			
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
			Form.setEHF010108T0_LIST(queryList);
			
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
		
		EHF010108M0F Form = (EHF010108M0F) form;
		
		Form.setHR_UpDepartmentSysNo("");
		Form.setHR_UpDepartmentName("");
		Form.setHR_DepartmentName("");
		Form.setHR_DepartmentNo("");
		Form.setEHF010108T0_02("");
		Form.setEHF010108T0_03("");
		
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

			sql += " SELECT * FROM EHF010108T0" + 
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

			sql += " SELECT * FROM EHF010108T0" + 
		       " WHERE 1 = 1 " +
		       " AND HR_DepartmentSysNo = '"+HR_DepartmentSysNo+"'" +
			   " AND HR_CompanySysNo = '"+comp_id+"'" +//公司代碼
		       " AND HR_UpDepartmentSysNo = '#'" +
		       " AND HR_UpDepartmentName = '#'" ;
			
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
