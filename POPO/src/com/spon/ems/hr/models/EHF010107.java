package com.spon.ems.hr.models;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;

public class EHF010107 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF010107(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF010107( String comp_id ){
		
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
		return null;
	}

	@Override
	public Map queryEditData(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		
		try{
			
			//QueryEdit
			String sql = "" +
			" SELECT *, " +
			" DATE_FORMAT(EHF000100T0_06, '%Y/%m/%d') AS EHF000100T0_06, " +
			" DATE_FORMAT(HR_LastUpdateDate, '%Y/%m/%d %H：%i：%s') AS HR_LastUpdateDate " +
			" FROM EHF000100T0 " +
			" WHERE 1=1 " +
			" AND HR_CompanySysNo = '"+ (String)queryCondMap.get("COMP_ID") +"' ";  //公司系統代碼
			
			dataMap = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return dataMap;
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
		
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF000100T0 SET " +
			" EHF000100T0_01=?, EHF000100T0_02=?, EHF000100T0_03=?, EHF000100T0_04=?, " +
			" EHF000100T0_05=?, EHF000100T0_06=?, EHF000100T0_07=?, EHF000100T0_08=?, " +
			" EHF000100T0_09=?, EHF000100T0_10=?, EHF000100T0_11=?, EHF000100T0_12=?, " +
			" EHF000100T0_13=?, EHF000100T0_14=?, EHF000100T0_15=?, EHF000100T0_16=?, " +
			" EHF000100T0_17=?, EHF000100T0_18=?, EHF000100T0_19=?, EHF000100T0_20=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, HR_LastUpdateDate=NOW() " +
			" WHERE 1=1 " +
			" AND HR_CompanySysNo = ? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_01"));  //
			indexid++;
			p6stmt.setInt(indexid, (Integer)dataMap.get("EHF000100T0_02"));  //統一編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_03"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_04"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_05"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_06"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_07"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_08"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_09"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_10"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_11"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_12"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_13"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_14"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_15"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_16"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_17"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_18"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_19"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000100T0_20"));  //
			indexid++;			
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代號
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF010107().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF010107().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

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
