package com.spon.ems.hr.models;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;

public class EHF010111 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF010111(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF010111( String comp_id ){
		
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
			" SELECT A.*, B.HR_DepartmentSysNo, B.HR_JobNameSysNo, C.HR_DepartmentNo, C.HR_DepartmentName, D.HR_JobNameNo, D.HR_JobName " +
			" FROM EHF010106T0 A " +
			" LEFT JOIN EHF010106T6 B ON B.HR_EmployeeSysNo = A.HR_EmployeeSysNo AND B.HR_CompanySysNo = A.HR_CompanySysNo " +
			" LEFT JOIN EHF010108T0 C ON C.HR_DepartmentSysNo = B.HR_DepartmentSysNo AND C.HR_CompanySysNo = B.HR_CompanySysNo " +
			" LEFT JOIN EHF010109T0 D ON D.HR_JobNameSysNo = B.HR_JobNameSysNo AND D.HR_CompanySysNo = B.HR_CompanySysNo " +
			" WHERE 1=1 " ;		
									
			if(this.base_tools.existString((String)queryCondMap.get("HR_DepartmentSysNo"))){	//部門代號
				sql += " and B.HR_DepartmentSysNo like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("HR_EmployeeSysNo"))){	//員工工號
				sql += " and A.HR_EmployeeSysNo like ?";
			}			
			
			sql += "" +
			" AND A.HR_CompanySysNo like ? " +  //公司代碼
			" ORDER BY B.HR_JobNameSysNo DESC ";  
			
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
