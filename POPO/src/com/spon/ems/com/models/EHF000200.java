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

public class EHF000200 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF000200(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF000200( String comp_id ){
		
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
		" FROM EHF000200T0 " +
		" WHERE 1=1 ";
		
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_04"))){	//
			sql += " and EHF000200T0_04 = ?";
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_03"))){	//
			sql += " and EHF000200T0_03 like ?";
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_02"))){	//
			sql += " and EHF000200T0_02 like ?";
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_08"))){	//
			sql += " and date_format(EHF000200T0_08, '%Y/%m/%d') = ?";
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_09"))){	//
			sql += " and date_format(EHF000200T0_09, '%Y/%m/%d') = ?";
		}
		
		sql += "" +
		" AND HR_CompanySysNo = ? " +//公司代碼
		" ORDER BY EHF000200T0_01"; 
		
		//執行SQL
		PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
		int indexid = 1;
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_04"))){
			pstmt.setString(indexid, (String)queryCondMap.get("EHF000200T0_04"));
			indexid++;
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_03"))){
			pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF000200T0_03")+"%");
			indexid++;
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_02"))){
			pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF000200T0_02")+"%");
			indexid++;
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_08"))){
			pstmt.setString(indexid, (String)queryCondMap.get("EHF000200T0_08"));
			indexid++;
		}
		if(this.base_tools.existString((String)queryCondMap.get("EHF000200T0_09"))){
			pstmt.setString(indexid, (String)queryCondMap.get("EHF000200T0_09"));
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
			" SELECT a.*, " +
			" DATE_FORMAT(EHF000200T0_08, '%Y/%m/%d') AS EHF000200T0_08, " +
			" DATE_FORMAT(EHF000200T0_09, '%Y/%m/%d') AS EHF000200T0_09, " +
			" b.EHF000200T0_01 AS ONEHF000200T0_01," +
			" b.EHF000200T0_02 AS ONEHF000200T0_02," +
			" b.EHF000200T0_03 AS ONEHF000200T0_03" +
			" FROM EHF000200T0 a" +
			" LEFT JOIN (" +
			" 	SELECT " +
			"    EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" +
			"   FROM" +
			"    EHF000200T0" +
			"	WHERE    1 = 1" +
			"   AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' " +
			"	) b ON a.EHF000200T0_04=b.EHF000200T0_01" +
			" WHERE 1=1 " +
			" AND a.EHF000200T0_01 = '"+(String)queryCondMap.get("EHF000200T0_01")+"' " +			
			" AND a.HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' " ;
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
			Map map = this.base_tools.query("select max(substr(EHF000200T0_01,4,3))+1 as seq from EHF000200T0");
			if(!map.isEmpty()){
				//將序號補滿為3位數的字串
				HR_DepartmentSysNo_seq = String.format("%03d", ((Double)map.get("seq")).intValue());
			}else{
				//000為公司根部門(目前須直接由資料庫新增根部門)，所以其餘UI新增的部門由001開始
				HR_DepartmentSysNo_seq = "001";
			}
			
			//找當前新增部門的階層; 根部門為第1階;
			map.clear();
			map = this.base_tools.query("select EHF000200T0_06+1 as seq from EHF000200T0 where EHF000200T0_01 = '"+(String)dataMap.get("EHF000200T0_04")+"'");
			if(!map.isEmpty()){
				HR_DepartmentLevel = ((Double)map.get("seq")).intValue()+"";
			}
			
			//找上一層完整的部門名稱
			map.clear();
			map = this.base_tools.query("select EHF000200T0_03 from EHF000200T0 where EHF000200T0_01 = '"+(String)dataMap.get("EHF000200T0_04")+"'");
			if(!map.isEmpty()){
				HR_UpFullDepartmentName = (String)map.get("EHF000200T0_03");
			}
			
			//找上一層完整的部門代碼
			map.clear();
			map = this.base_tools.query("select EHF000200T0_02 from EHF000200T0 where EHF000200T0_01 = '"+(String)dataMap.get("EHF000200T0_04")+"'");
			if(!map.isEmpty()){
				HR_UpFullDepartmentNo = (String)map.get("EHF000200T0_02");
			}
			
			//找上一層完整部門名稱&部門代碼
			map.clear();
			map = this.base_tools.query("select EHF000200T0_16 from EHF000200T0 where EHF000200T0_01 = '"+(String)dataMap.get("EHF000200T0_04")+"'");
			if(!map.isEmpty()){
				HR_UpFullDepartment = (String)map.get("EHF000200T0_16");
			}
			
			//Add EHF010108T0
			sql_1 = "" +
			" INSERT INTO EHF000200T0 " +
			" (EHF000200T0_01, EHF000200T0_02, EHF000200T0_03, EHF000200T0_04, EHF000200T0_05, EHF000200T0_06, " +
			" EHF000200T0_07, EHF000200T0_08, EHF000200T0_09, EHF000200T0_10, HR_CompanySysNo, EHF000200T0_11, " +
			" EHF000200T0_12, EHF000200T0_13, EHF000200T0_14, EHF000200T0_15, EHF000200T0_16, EHF000200T0_17, " +
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
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_02"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_03"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_04"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_05"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_DepartmentLevel);
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_07"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "".equals((String)dataMap.get("EHF000200T0_08"))?null:(String)dataMap.get("EHF000200T0_08"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "".equals((String)dataMap.get("EHF000200T0_09"))?null:(String)dataMap.get("EHF000200T0_09"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_10")==null?"":(String)dataMap.get("EHF000200T0_10"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("COMP_ID"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartmentName);
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, HR_UpFullDepartmentNo);
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartmentName+"\\"+(String)dataMap.get("EHF000200T0_03"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartmentNo+"\\"+(String)dataMap.get("EHF000200T0_02"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartment);
			indexid_1++;
			p6stmt_1.setString(indexid_1, HR_UpFullDepartment+"\\"+(String)dataMap.get("EHF000200T0_03")+"("+(String)dataMap.get("EHF000200T0_02")+")");
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_02")+"^"+(String)dataMap.get("EHF000200T0_03"));
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
			this.base_tools.writeADDMSG("EHF000200().addData()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF000200.addData()", show_sql_1+", "+e.getMessage(), 
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
			
			//DELETE EHF000200T0
			sql = "" +
			" DELETE FROM EHF000200T0 " +
			" WHERE 1=1 " +
			" AND EHF000200T0_01 = '"+(String)dataMap.get("EHF000200T0_01")+"' " +
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
			this.base_tools.writeDELETEMSG("EHF000200().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			this.base_tools.rollback();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF000200().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE EHF000200T0 SET " +
			" EHF000200T0_03=?, EHF000200T0_07=?, EHF000200T0_08=?, EHF000200T0_09=?, EHF000200T0_10=?," +			
			" USER_UPDATE=?, VERSION=VERSION+1, HR_LastUpdateDate=NOW() " +
			" WHERE 1=1 " +
			" AND HR_CompanySysNo=? " +
			" AND EHF000200T0_01=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000200T0_03"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000200T0_07"));
			indexid++;
			p6stmt.setString(indexid, "".equals((String)dataMap.get("EHF000200T0_08"))?null:(String)dataMap.get("EHF000200T0_08"));
			indexid++;
			p6stmt.setString(indexid, "".equals((String)dataMap.get("EHF000200T0_09"))?null:(String)dataMap.get("EHF000200T0_09"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000200T0_10"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000200T0_01"));  //本層部門系統代號
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
			this.base_tools.writeUPDATEMSG("EHF000200().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF000200().saveData()", 
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
	
	public List queryEHF000200T0List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF000200T0List = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT * " +
			" FROM  EHF000200T0 " +
			" WHERE 1 = 1 " +
			" AND EHF000200T0_04 = '"+(String)queryCondMap.get("EHF000200T0_01")+"'" +			
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF000200T0List = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF000200T0List;
	}

	public void addDataInit(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql_1 = "";
		String show_sql_1 = "";
		
		try{
			sql_1 = "" +
			" INSERT INTO EHF000200T0 " +
			" (EHF000200T0_01, EHF000200T0_02, EHF000200T0_03, EHF000200T0_04, EHF000200T0_05, EHF000200T0_06, " +
			" EHF000200T0_07, EHF000200T0_08, EHF000200T0_09, EHF000200T0_10, HR_CompanySysNo, EHF000200T0_11, " +
			" EHF000200T0_12, EHF000200T0_13, EHF000200T0_14, EHF000200T0_15, EHF000200T0_16, EHF000200T0_17, " +
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
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_02"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_03"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_04"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_05"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "1");
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_07"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "".equals((String)dataMap.get("EHF000200T0_08"))?null:(String)dataMap.get("EHF000200T0_08"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "".equals((String)dataMap.get("EHF000200T0_09"))?null:(String)dataMap.get("EHF000200T0_09"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, dataMap.get("EHF000200T0_10")==null?"":(String)dataMap.get("EHF000200T0_10"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("COMP_ID"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "");
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, "");
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_03"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_02"));
			indexid_1++;
			p6stmt_1.setString(indexid_1, "");
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_03")+"("+(String)dataMap.get("EHF000200T0_02")+")");
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF000200T0_02")+"^"+(String)dataMap.get("EHF000200T0_03"));
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
			this.base_tools.writeADDMSG("EHF000200().addDataInit()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF000200.addDataInit()", show_sql_1+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

}
