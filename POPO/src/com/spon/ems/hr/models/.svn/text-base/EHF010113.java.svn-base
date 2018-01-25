package com.spon.ems.hr.models;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;

public class EHF010113 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF010113(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF010113( String comp_id ){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction(comp_id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List queryData(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List dataList = null;
		
		try{
			//Query
			String sql = "" +
			" SELECT * FROM "+
			" ( " +
			" SELECT A.HR_EmployeeSysNo, A.HR_EmployeeNo, A.EHF010106T0_01, A.EHF010106T0_02, A.EHF010106T0_03, A.EHF010106T0_04, " +
			" B.HR_JobNameSysNo, B.HR_DepartmentSysNo, " +
			" C.EHF010106T1_01, DATE_FORMAT(C.EHF010106T1_02, '%Y/%m/%d') AS EHF010106T1_02, " +
			" H.HR_DepartmentNo, H.HR_DepartmentName, " +
			" I.HR_JobNameNo, I.HR_JobName " +
			" FROM EHF010106T0 A " +						
			" LEFT JOIN EHF010106T6 B ON A.HR_EmployeeSysNo = B.HR_EmployeeSysNo AND A.HR_CompanySysNo = B.HR_CompanySysNo " +
			" LEFT JOIN EHF010106T1 C ON A.HR_EmployeeSysNo = C.HR_EmployeeSysNo AND A.HR_CompanySysNo = C.HR_CompanySysNo " +
			" LEFT JOIN EHF010108T0 H ON B.HR_DepartmentSysNo = H.HR_DepartmentSysNo AND B.HR_CompanySysNo = H.HR_CompanySysNo " +
			" LEFT JOIN EHF010109T0 I ON B.HR_JobNameSysNo = I.HR_JobNameSysNo AND B.HR_CompanySysNo = I.HR_CompanySysNo " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("HR_DepartmentSysNo"))){	//部門代號
				sql += " and B.HR_DepartmentSysNo like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("HR_EmployeeSysNo"))){	//員工工號
				sql += " and A.HR_EmployeeSysNo like ?";
			}			
			
			sql += "" +
			" AND C.EHF010106T1_01 IN (1,3,4) " +
			" AND A.HR_CompanySysNo like ? " +  //公司代碼
			" ORDER BY EHF010106T1_02 DESC " +
			" ) tableA " +
			" WHERE 1=1 " +
			" GROUP BY HR_EmployeeSysNo ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("HR_DepartmentSysNo"))){
				pstmt.setString(indexid, (String)queryCondMap.get("HR_DepartmentSysNo"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("HR_EmployeeSysNo"))){
				pstmt.setString(indexid, (String)queryCondMap.get("HR_EmployeeSysNo"));
				indexid++;
			}			
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}

	@Override
	public Map queryEditData(Map queryCondMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addData(Map dataMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map queryDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.base_tools.close();
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return this.base_tools.isClosed();
	}

}
