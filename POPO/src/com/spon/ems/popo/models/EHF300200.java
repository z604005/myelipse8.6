package com.spon.ems.popo.models;

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

public class EHF300200 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF300200(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF300200( String comp_id ){
		
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
//			String sql = "" +
//			" SELECT EHF300200T0_01, EHF300200T0_02, EHF300200T0_03, EHF300200T0_04, " +			
//			" EHF300200T0_05, EHF300200T0_06 " +			
//			" FROM EHF300200T0 " +
//			" WHERE 1=1 " ;			
//									
//			if(this.base_tools.existString((String)queryCondMap.get("EHF300200T0_02"))){	//醫院名稱
//				sql += " and EHF300200T0_02 = '"+(String)queryCondMap.get("EHF300200T0_02")+"'";
//			}
//			if(this.base_tools.existString((String)queryCondMap.get("EHF300200T0_05"))){	//是否啟用
//				sql += " and EHF300200T0_05 = '"+(String)queryCondMap.get("EHF300200T0_05")+"'";
//			}
//			
//			sql += "" +
//			" AND EHF300200T0_06 = '"+(String)queryCondMap.get("COMP_ID")+"'"+  //公司代碼
//			" ORDER BY EHF300200T0_04 ASC ";  //由小至大排序
			//Query
			String sql = "" +
			" SELECT EHF300200T0_01, EHF300200T0_02, EHF300200T0_03, EHF300200T0_04, " +			
			" EHF300200T0_05, EHF300200T0_06 " +			
			" FROM EHF300200T0 " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF300200T0_02"))){	//醫院名稱
				sql += " and EHF300200T0_02 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF300200T0_05"))){	//是否啟用
				sql += " and EHF300200T0_05 = ?";
			}
			
			sql += "" +
			" AND EHF300200T0_06 = ? " +  //公司代碼
			" ORDER BY EHF300200T0_04 ASC ";  
			
//			執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF300200T0_02"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF300200T0_02")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF300200T0_05"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF300200T0_05"));
				indexid++;
			}			
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
//			queryCondMap = this.base_tools.query(sql);
		
//			System.out.println(sql);
			
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
			" SELECT *, DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE " +
			" FROM EHF300200T0 " +
			" WHERE 1=1 " +
			" AND EHF300200T0_01 = '"+(String)queryCondMap.get("EHF300200T0_01")+"' " +  //醫院代碼
			" AND EHF300200T0_06 = '"+(String)queryCondMap.get("COMP_ID")+"' ";	//公司代碼
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
			" INSERT INTO EHF300200T0 " +
			" (EHF300200T0_02, " +
			//" EHF300200T0_03, " +
			" EHF300200T0_04, EHF300200T0_05, " +
			" EHF300200T0_06, " +			
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, " +
			//" ?, " +			
			" ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			/*p6stmt.setString(indexid, (String)dataMap.get("EHF300200T0_01"));  //自動編號，不用加上去
			indexid++;*/
			p6stmt.setString(indexid, (String)dataMap.get("EHF300200T0_02"));  //醫院名稱
			indexid++;
			//p6stmt.setString(indexid, (String)dataMap.get("EHF300200T0_03"));  //路線名稱
			//indexid++;
		
//			p6stmt.setInt(indexid, Integer.valueOf(dataMap.get("EHF300200T0_04").toString()).intValue());  //顯示順序
			p6stmt.setInt(indexid, Integer.valueOf((String)dataMap.get("EHF300200T0_04")).intValue());  //顯示順序
			indexid++;
		
//			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF300200T0_04"));  //啟用
			int Enabled = 0 ;
			if ((Boolean)dataMap.get("EHF300200T0_05")) {
				Enabled=1;}
			p6stmt.setInt(indexid, Enabled);  //是否啟用
			indexid++;

			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
//			System.out.println(sql);
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//路線代碼 //吐出來的是int
			String HPNum = String.valueOf(this.base_tools.getDb_tools().getLastInsertId(this.base_tools.getConn()));
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫 職務系統代碼
			dataMap.put("KEY_ID", HPNum);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF300200().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF300200.addData()", show_sql+", "+e.getMessage(), 
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
			" UPDATE EHF300200T0 SET " +
			" EHF300200T0_02=?, " +
			//" EHF300200T0_03=?, " +
			" EHF300200T0_04=?, EHF300200T0_05=?, " +			
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF300200T0_06=? " +
			" AND EHF300200T0_01=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300200T0_02"));//醫院名稱
			indexid++;
			//p6stmt.setString(indexid, (String)dataMap.get("EHF300200T0_03"));//路線名稱
			//indexid++;
			p6stmt.setInt(indexid, Integer.valueOf((String)dataMap.get("EHF300200T0_04")).intValue());  //顯示順序
			indexid++;
			int Enabled = 0 ;
			if ((Boolean)dataMap.get("EHF300200T0_05")) {
				Enabled=1;}
			p6stmt.setInt(indexid, Enabled);  //是否啟用
//			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF300200T0_04"));
			indexid++;

			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300200T0_01"));  //職務系統代碼
			indexid++;
//			System.out.println(sql);
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF300200().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF300200().saveData()", 
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
	 * 檢查醫院名稱是否使用中
	 * @param HPNum
	 * @param HR_JobName
	 * @param EHF300200T0_06
	 * @return
	 */
	public boolean selectEHF310100T0(String HPNum, String RouteName,
			String EHF310100T0_32) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = " SELECT EHF310100T0_16 FROM EHF310100T0 " +
				  " WHERE EHF310100T0_32 = '"+EHF310100T0_32+"' " +
				  " AND EHF310100T0_16 = '"+HPNum+"' ";
			
			dataMap = this.base_tools.query(sql);
//			System.out.println(sql);
			if(!dataMap.isEmpty()){
				//dataMap.put("chkFlag", true);
				chkFlag = true;
			}
			
		}catch(Exception e){			
			e.printStackTrace();
		}
		
		return chkFlag;
	}
	
	//檢查醫院名稱是否已使用
	public boolean selectHPName(String HPName,
			String EHF300200T0_06, String Key) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = " SELECT * FROM EHF300200T0 " +
				  " WHERE EHF300200T0_06 = '"+EHF300200T0_06+"' " +
				  " AND EHF300200T0_02 = '"+HPName+"' " +
				  " AND EHF300200T0_01 != '"+Key+"' ";
//			System.out.println(sql);
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
	
	//檢查路線名稱是否已使用
	public boolean queryrouteName(String routeName,
			String EHF300200T0_06, String Key) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = " SELECT * FROM EHF300200T0 " +
				  " WHERE EHF300200T0_06 = '"+EHF300200T0_06+"' " +
				  " AND EHF300200T0_03 = '"+routeName+"' " +
				  " AND EHF300200T0_01 != '"+Key+"' ";
//			System.out.println(sql);
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

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		
		try{
			List sql_list = new ArrayList();
			
			//DELETE EHF300200T0
			sql = "" +
			" DELETE FROM EHF300200T0 " +
			" WHERE 1=1 " +
			" AND EHF300200T0_01 = '"+(String)dataMap.get("EHF300200T0_01")+"' " +
			" AND EHF300200T0_06 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			this.base_tools.writeDELETEMSG("EHF300200().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF300200().delData()", sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	//檢查路線是否啟用中
	public boolean selectEnabled(String HPNum, String EHF300200T0_06) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			
			sql = " SELECT * FROM EHF300200T0 " +
				  " WHERE EHF300200T0_06 = '"+EHF300200T0_06+"' " +
				  " AND EHF300200T0_01 = '"+HPNum+"' ";
			
			dataMap = this.base_tools.query(sql);
			
			chkFlag = (Boolean) dataMap.get("EHF300200T0_05");
			
		}catch(Exception e){			
			e.printStackTrace();
		}
		
		return chkFlag;
	}
	
	

}
