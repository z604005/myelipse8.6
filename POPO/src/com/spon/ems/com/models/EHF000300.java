package com.spon.ems.com.models;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;

public class EHF000300 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF000300(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF000300( String comp_id ){
		
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
			" SELECT EHF000300T0_01, EHF000300T0_02, EHF000300T0_03, EHF000300T0_04, " +			
			" EHF000300T0_05, EHF000300T0_06 " +			
			" FROM EHF000300T0 " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF000300T0_02"))){	//職務代碼
				sql += " and EHF000300T0_02 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF000300T0_03"))){	//職務名稱
				sql += " and EHF000300T0_03 like ?";
			}
			
			sql += "" +
			" AND HR_CompanySysNo like ? " +  //公司代碼
			" ORDER BY EHF000300T0_02 DESC ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF000300T0_02"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF000300T0_02"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF000300T0_03"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF000300T0_03"));
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
		
		Map dataMap = null;
		
		try{
			//QueryEdit
			String sql = "" +
			" SELECT *, DATE_FORMAT(HR_LastUpdateDate, '%Y/%m/%d %H：%i：%s') AS HR_LastUpdateDate " +
			" FROM EHF000300T0 " +
			" WHERE 1=1 " +
			" AND EHF000300T0_01 = '"+(String)queryCondMap.get("EHF000300T0_01")+"' " +  //職務系統代碼
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";	//公司代碼
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}

	@Override
	public void addData(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			//Add
			sql = "" +
			" INSERT INTO EHF000300T0 " +
			" (EHF000300T0_01, EHF000300T0_02, EHF000300T0_03, EHF000300T0_04, EHF000300T0_05, " +
			" EHF000300T0_06, HR_CompanySysNo, " +			
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, HR_LastUpdateDate) " +
			" VALUES ( ?, ?, ?, ?, ?," +
			" ?, ?, " +			
			" ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF000300T0_01"));  //職務系統代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000300T0_02"));  //職務顯示代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000300T0_03"));  //職務名稱
			indexid++;
			p6stmt.setString(indexid, "");  //職等
			indexid++;
			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF000300T0_05"));  //使用狀況
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000300T0_06"));  //備註
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//職務系統代碼
			String HR_JobNameSysNo = (String)dataMap.get("EHF000300T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫 職務系統代碼
			dataMap.put("KEY_ID", HR_JobNameSysNo);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF000300().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF000300.addData()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			List sql_list = new ArrayList();
			
			//DELETE EHF000300T0
			sql = "" +
			" DELETE FROM EHF000300T0 " +
			" WHERE 1=1 " +
			" AND EHF000300T0_01 = '"+(String)dataMap.get("EHF000300T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
			sql_list.add(sql);
			
			//執行刪除
			int[] dataCount_array = this.base_tools.executeBatchSQL(sql_list);
			int dataCount = 0;
			int mainDataCount = 0;
			for(int i=0; i<dataCount_array.length; i++){
				dataCount += dataCount_array[i];
				mainDataCount = dataCount_array[i];
			}
			//int dataCount = this.base_tools.delete(sql);
			
			dataMap.put("MAIN_DATA_COUNT", mainDataCount);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF000300().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF000300().delData()", sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql ="";
		String show_sql = "";
		
		try{
			sql = "" +
			" UPDATE EHF000300T0 SET " +
			" EHF000300T0_02=?, EHF000300T0_03=?, EHF000300T0_05=?, EHF000300T0_06=?, " +			
			" USER_UPDATE=?, VERSION=VERSION+1, HR_LastUpdateDate=NOW() " +
			" WHERE 1=1 " +
			" AND HR_CompanySysNo=? " +
			" AND EHF000300T0_01=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000300T0_02"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000300T0_03"));
			indexid++;
			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF000300T0_05"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000300T0_06"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000300T0_01"));  //職務系統代碼
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF000300().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF000300().saveData()", 
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
	
	/**
	 * 檢查職務代碼是否已使用
	 * @param HR_JobNameSysNo
	 * @param HR_JobName
	 * @param HR_CompanySysNo
	 * @return
	 */
	public boolean selectEHF010100T6(String HR_JobNameSysNo, String HR_JobName,
			String HR_CompanySysNo) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = " SELECT EHF010100T6_06 FROM EHF010100T6 " +
				  " WHERE HR_CompanySysNo = '"+HR_CompanySysNo+"' " +
				  " AND EHF010100T6_06 = '"+HR_JobNameSysNo+"' ";
			
			dataMap = this.base_tools.query(sql);
			
			if(!dataMap.isEmpty()){
				//dataMap.put("chkFlag", true);
				chkFlag = true;
			}
			
		}catch(Exception e){			
			e.printStackTrace();
		}
		
		return chkFlag;
	}

}
