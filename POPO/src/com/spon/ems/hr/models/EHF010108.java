package com.spon.ems.hr.models;

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

public class EHF010108 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF010108(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF010108( String comp_id ){
		
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
		" SELECT *" +
		" FROM EHF010108T0 " +
		" WHERE 1=1 ";
		
		if(this.base_tools.existString((String)queryCondMap.get("HR_UpDepartmentSysNo"))){	//
			sql += " and HR_UpDepartmentSysNo = ?";
		}
		if(this.base_tools.existString((String)queryCondMap.get("HR_DepartmentName"))){	//
			sql += " and HR_DepartmentName like ?";
		}
		if(this.base_tools.existString((String)queryCondMap.get("HR_DepartmentNo"))){	//
			sql += " and HR_DepartmentNo like ?";
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF010108T0_02"))){	//
			sql += " and date_format(EHF010108T0_02, '%Y/%m/%d') = ?";
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF010108T0_03"))){	//
			sql += " and date_format(EHF010108T0_03, '%Y/%m/%d') = ?";
		}
		
		sql += "" +
		" AND HR_CompanySysNo = ? " +//公司代碼
		" ORDER BY HR_DepartmentSysNo"; 
		
		//執行SQL
		PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
		int indexid = 1;
		if(this.base_tools.existString((String)queryCondMap.get("HR_UpDepartmentSysNo"))){
			pstmt.setString(indexid, (String)queryCondMap.get("HR_UpDepartmentSysNo"));
			indexid++;
		}
		if(this.base_tools.existString((String)queryCondMap.get("HR_DepartmentName"))){
			pstmt.setString(indexid, "%"+(String)queryCondMap.get("HR_DepartmentName")+"%");
			indexid++;
		}
		if(this.base_tools.existString((String)queryCondMap.get("HR_DepartmentNo"))){
			pstmt.setString(indexid, "%"+(String)queryCondMap.get("HR_DepartmentNo")+"%");
			indexid++;
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF010108T0_02"))){
			pstmt.setString(indexid, (String)queryCondMap.get("EHF010108T0_02"));
			indexid++;
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF010108T0_03"))){
			pstmt.setString(indexid, (String)queryCondMap.get("EHF010108T0_03"));
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
		Map dataMap = null;
		
		try{			
			//QueryEdit
			String sql = "" +
			" SELECT *, " +
			" DATE_FORMAT(EHF010108T0_02, '%Y/%m/%d') AS EHF010108T0_02, " +
			" DATE_FORMAT(EHF010108T0_03, '%Y/%m/%d') AS EHF010108T0_03 " +
			" FROM EHF010108T0 " +
			" WHERE 1=1 " +
			" AND HR_DepartmentSysNo = '"+(String)queryCondMap.get("HR_DepartmentSysNo")+"' " +			
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' " ;
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}

	@Override
	public void addData(Map dataMap) {
		String sql_1 = "";
		String show_sql_1 = "";
		String HR_DepartmentSysNo_seq = "";
		String HR_DepartmentLevel = "";
		//上一層完整部門名稱; ex:思邦科技\董事長室\總經理室\資料處
		String HR_UpFullDepartmentName = "";
		//上一層完整部門代碼; ex:SPONTECH\000\101\1012
		String HR_UpFullDepartmentNo = "";
		//上一層完整部門名稱&部門代碼; ex:思邦科技(SPONTECH)\董事長室(001)\總經理室(101)\資料處(1012)
		String HR_UpFullDepartment = "";
		
		try{
			//找當前新增部門的系統序號; 自動填零到3碼
			Map map = this.base_tools.query("select max(substr(HR_DepartmentSysNo,4,3))+1 as seq from ehf010108t0");
			if(!map.isEmpty()){
				//將序號補滿為3位數的字串
				HR_DepartmentSysNo_seq = String.format("%03d", ((Double)map.get("seq")).intValue());
			}else{
				//000為公司根部門(目前須直接由資料庫新增根部門)，所以其餘UI新增的部門由001開始
				HR_DepartmentSysNo_seq = "001";
			}
			
			//找當前新增部門的階層; 根部門為第1階;
			map.clear();
			map = this.base_tools.query("select HR_DepartmentLevel+1 as seq from ehf010108t0 where HR_DepartmentSysNo = '"+(String)dataMap.get("HR_UpDepartmentSysNo")+"'");
			if(!map.isEmpty()){
				HR_DepartmentLevel = ((Double)map.get("seq")).intValue()+"";
			}
			
			//找上一層完整的部門名稱
			map.clear();
			map = this.base_tools.query("select HR_DepartmentName from ehf010108t0 where HR_DepartmentSysNo = '"+(String)dataMap.get("HR_UpDepartmentSysNo")+"'");
			if(!map.isEmpty()){
				HR_UpFullDepartmentName = (String)map.get("HR_DepartmentName");
			}
			
			//找上一層完整的部門代碼
			map.clear();
			map = this.base_tools.query("select HR_DepartmentNo from ehf010108t0 where HR_DepartmentSysNo = '"+(String)dataMap.get("HR_UpDepartmentSysNo")+"'");
			if(!map.isEmpty()){
				HR_UpFullDepartmentNo = (String)map.get("HR_DepartmentNo");
			}
			
			//找上一層完整部門名稱&部門代碼
			map.clear();
			map = this.base_tools.query("select HR_FullDepartemet from ehf010108t0 where HR_DepartmentSysNo = '"+(String)dataMap.get("HR_UpDepartmentSysNo")+"'");
			if(!map.isEmpty()){
				HR_UpFullDepartment = (String)map.get("HR_FullDepartemet");
			}
			
			//Add EHF010108T0
			sql_1 = "" +
			" INSERT INTO EHF010108T0 " +
			" (HR_DepartmentSysNo, HR_DepartmentNo, HR_DepartmentName, HR_UpDepartmentSysNo, HR_UpDepartmentName, HR_DepartmentLevel, " +
			" EHF010108T0_01, EHF010108T0_02, EHF010108T0_03, EHF010108T0_04, HR_CompanySysNo, HR_UpFullDepartmentName, " +
			" HR_UpFullDepartmentNo, HR_FullDepartmentName, HR_FullDepartmentNo, HR_UpFullDepartment, HR_FullDepartemet, HR_DepartmentInfo, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, HR_LastUpdateDate) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt_1 = this.base_tools.getConn().prepareStatement(sql_1);
			P6PreparedStatement p6stmt_1 = new P6PreparedStatement(null, pstmt_1, null, sql_1);
			int indexid_1 = 1;
			
			p6stmt_1.setString(indexid_1, "DEP"+HR_DepartmentSysNo_seq+(String)dataMap.get("COMP_ID"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentNo"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentName"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_UpDepartmentSysNo"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_UpDepartmentName"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_DepartmentLevel);
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010108T0_01"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "".equals((String)dataMap.get("EHF010108T0_02"))?null:(String)dataMap.get("EHF010108T0_02"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "".equals((String)dataMap.get("EHF010108T0_03"))?null:(String)dataMap.get("EHF010108T0_03"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, dataMap.get("EHF010108T0_04")==null?"":(String)dataMap.get("EHF010108T0_04"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("COMP_ID"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartmentName);
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, HR_UpFullDepartmentNo);
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartmentName+"\\"+(String)dataMap.get("HR_DepartmentName"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartmentNo+"\\"+(String)dataMap.get("HR_DepartmentNo"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartment);
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartment+"\\"+(String)dataMap.get("HR_DepartmentName")+"("+(String)dataMap.get("HR_DepartmentNo")+")");
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentNo")+"^"+(String)dataMap.get("HR_DepartmentName"));
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));
			indexid_1++;
			
			//System.out.println(p6stmt_1.getQueryFromPreparedStatement());
			show_sql_1 = p6stmt_1.getQueryFromPreparedStatement();
			//執行
			p6stmt_1.executeUpdate();
			
			//本層部門系統代號
			String HR_DepartmentSysNo = "DEP"+HR_DepartmentSysNo_seq+(String)dataMap.get("COMP_ID");
			
			//更新資料庫
			this.base_tools.commit();
						
			pstmt_1.close();
			p6stmt_1.close();
			
			//回寫 本層部門系統代號
			dataMap.put("KEY_ID", HR_DepartmentSysNo);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010108().addData()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010108.addData()", show_sql_1+", "+e.getMessage(), 
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
			
			//DELETE EHF010108T0
			sql = "" +
			" DELETE FROM EHF010108T0 " +
			" WHERE 1=1 " +
			" AND HR_DepartmentSysNo = '"+(String)dataMap.get("HR_DepartmentSysNo")+"' " +
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
			this.base_tools.writeDELETEMSG("EHF010108().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			this.base_tools.rollback();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010108().delData()", sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void saveData(Map dataMap) {
		String sql ="";
		String show_sql = "";
		
		try{
			sql = "" +
			" UPDATE EHF010108T0 SET " +
			" HR_DepartmentName=?, EHF010108T0_01=?, EHF010108T0_02=?, EHF010108T0_03=?, EHF010108T0_04=?," +			
			" USER_UPDATE=?, VERSION=VERSION+1, HR_LastUpdateDate=NOW() " +
			" WHERE 1=1 " +
			" AND HR_CompanySysNo=? " +
			" AND HR_DepartmentSysNo=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("HR_DepartmentName"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010108T0_01"));
			indexid++;
			p6stmt.setString(indexid, "".equals((String)dataMap.get("EHF010108T0_02"))?null:(String)dataMap.get("EHF010108T0_02"));
			indexid++;
			p6stmt.setString(indexid, "".equals((String)dataMap.get("EHF010108T0_03"))?null:(String)dataMap.get("EHF010108T0_03"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010108T0_04"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("HR_DepartmentSysNo"));  //本層部門系統代號
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
			this.base_tools.writeUPDATEMSG("EHF010108().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF010108().saveData()", 
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
		
		/*if("EHF010108T0".equals(detailType)){
			
			String sql = "";
			
			try{
				//刪除
				sql = "" +
				" DELETE FROM EHF010108T0 " +
				" WHERE 1=1 " +
				" AND HR_DepartmentSysNo = '"+(String)detailDataMap.get("HR_DepartmentSysNo")+"' " +  //
				" AND HR_CompanySysNo = '"+(String)detailDataMap.get("COMP_ID")+"' ";	//公司代碼
				//執行刪除
				int dataCount = this.base_tools.delete(sql);
				
				//更新資料庫
				this.base_tools.commit();
				
				//記錄刪除訊息
				this.base_tools.writeDELETEMSG("EHF010108().delDetailData()",
											   sql+" total delete "+dataCount+"rows data", 
											   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
				
			}catch(Exception e){
				
				//記錄刪除訊息
				this.base_tools.writeDELETEERRMSG("EHF010108().delDetailData()",
												  sql+", "+e.getMessage(), 
												  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
												  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
				
				e.printStackTrace();
			}
			
		}*/

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
	
	public List queryEHF010108T0List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF010108T0List = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT * " +
			" FROM  EHF010108T0 " +
			" WHERE 1 = 1 " +
			" AND HR_UpDepartmentSysNo = '"+(String)queryCondMap.get("HR_DepartmentSysNo")+"'" +			
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF010108T0List = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF010108T0List;
	}

	public void addDataInit(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql_1 = "";
		String show_sql_1 = "";
		
		try{
			sql_1 = "" +
			" INSERT INTO EHF010108T0 " +
			" (HR_DepartmentSysNo, HR_DepartmentNo, HR_DepartmentName, HR_UpDepartmentSysNo, HR_UpDepartmentName, HR_DepartmentLevel, " +
			" EHF010108T0_01, EHF010108T0_02, EHF010108T0_03, EHF010108T0_04, HR_CompanySysNo, HR_UpFullDepartmentName, " +
			" HR_UpFullDepartmentNo, HR_FullDepartmentName, HR_FullDepartmentNo, HR_UpFullDepartment, HR_FullDepartemet, HR_DepartmentInfo, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, HR_LastUpdateDate) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt_1 = this.base_tools.getConn().prepareStatement(sql_1);
			P6PreparedStatement p6stmt_1 = new P6PreparedStatement(null, pstmt_1, null, sql_1);
			int indexid_1 = 1;
			
			p6stmt_1.setString(indexid_1, "DEP000"+(String)dataMap.get("COMP_ID"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentNo"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentName"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_UpDepartmentSysNo"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_UpDepartmentName"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "1");
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010108T0_01"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "".equals((String)dataMap.get("EHF010108T0_02"))?null:(String)dataMap.get("EHF010108T0_02"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "".equals((String)dataMap.get("EHF010108T0_03"))?null:(String)dataMap.get("EHF010108T0_03"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, dataMap.get("EHF010108T0_04")==null?"":(String)dataMap.get("EHF010108T0_04"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("COMP_ID"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "");
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, "");
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentName"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentNo"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "");
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentName")+"("+(String)dataMap.get("HR_DepartmentNo")+")");
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("HR_DepartmentNo")+"^"+(String)dataMap.get("HR_DepartmentName"));
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));
			indexid_1++;
			
			//System.out.println(p6stmt_1.getQueryFromPreparedStatement());
			show_sql_1 = p6stmt_1.getQueryFromPreparedStatement();
			//執行
			p6stmt_1.executeUpdate();
			
			//本層部門系統代號
			String HR_DepartmentSysNo = "DEP000"+(String)dataMap.get("COMP_ID");
			
			//更新資料庫
			this.base_tools.commit();
						
			pstmt_1.close();
			p6stmt_1.close();
			
			//回寫 本層部門系統代號
			dataMap.put("KEY_ID", HR_DepartmentSysNo);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010108().addDataInit()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010108.addDataInit()", show_sql_1+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
}
