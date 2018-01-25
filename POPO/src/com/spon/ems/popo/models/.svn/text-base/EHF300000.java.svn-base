package com.spon.ems.popo.models;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;

public class EHF300000 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	private BA_TOOLS tool;
	
	public EHF300000(){
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
			tool = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public EHF300000( String comp_id ){
		try{
			//建立基本操作元件
			base_tools = new BaseFunction(comp_id);
			tool = BA_TOOLS.getInstance();
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
			" SELECT PSFOODT0_01, PSFOODT0_02, PSFOODT0_03," +
			" PSFOODT0_04, PSFOODT0_05, PSFOODT0_06, PSFOODT0_07 " +
			" FROM FOODT0 " +
			" WHERE 1=1 " +
			" AND PSFOODT0_08 like ? "; //公司代碼
			if(this.base_tools.existString((String)queryCondMap.get("PSFOODT0_01"))){	
				sql += " and PSFOODT0_01 like ? ";  //類別代碼
			}
			if(this.base_tools.existString((String)queryCondMap.get("PSFOODT0_04"))){	
				sql += " and PSFOODT0_04 like ? ";  //類別名稱
			}
			if(this.base_tools.existString((String)queryCondMap.get("PSFOODT0_07"))){	
				sql += " and PSFOODT0_07 = ? ";   //啟用
			}
			
			sql += " order by PSFOODT0_06, PSFOODT0_01 "; 

			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			if(this.base_tools.existString((String)queryCondMap.get("PSFOODT0_01"))){	
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("PSFOODT0_01")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("PSFOODT0_04"))){	
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("PSFOODT0_04")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("PSFOODT0_07"))){	
				pstmt.setBoolean(indexid, tool.StringToBoolean((String)queryCondMap.get("PSFOODT0_07")));
				indexid++;
			}
			
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
		" SELECT PSFOODT0_01, PSFOODT0_02, PSFOODT0_03, " +
		" PSFOODT0_04, PSFOODT0_05, PSFOODT0_06, PSFOODT0_07, " +
		" USER_CREATE, USER_UPDATE, VERSION, " +
		" DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE " +
		" FROM FOODT0 " +
		" WHERE 1=1 " +
		" AND PSFOODT0_01 = '"+(String)queryCondMap.get("PSFOODT0_01")+"' " +  //
		" AND PSFOODT0_08 = '"+(String)queryCondMap.get("COMP_ID")+"' ";	//公司代碼
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
			
		sql = " Insert Into FOODT0 (" +
    	  	  " PSFOODT0_01,PSFOODT0_02,PSFOODT0_03," +
    	  	  " PSFOODT0_04,PSFOODT0_05,PSFOODT0_06," +
    	  	  " PSFOODT0_07,PSFOODT0_08," +
    	  	  " USER_CREATE,USER_UPDATE,VERSION,DATE_UPDATE) " +
    	  	  " values(?,?,?,?,?,?,?,?,?,?,1,NOW())";
		
		//執行SQL
		PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
		P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
		int indexid = 1;

		p6stmt.setString(indexid, (String)dataMap.get("UID"));  
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_02")==null?"":(String)dataMap.get("PSFOODT0_02"));  
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_03")==null?"":(String)dataMap.get("PSFOODT0_03"));  
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_04"));  
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_05")==null?"":(String)dataMap.get("PSFOODT0_05"));  
		indexid++;
		p6stmt.setInt(indexid, (Integer)dataMap.get("PSFOODT0_06"));  
		indexid++;
		p6stmt.setBoolean(indexid, (Boolean)dataMap.get("PSFOODT0_07"));  
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料建立者
		indexid++;
		//System.out.println(p6stmt.getQueryFromPreparedStatement());
		show_sql = p6stmt.getQueryFromPreparedStatement();
		//執行
		p6stmt.executeUpdate();
		
		pstmt.close();
		p6stmt.close();
		
		//更新資料庫
		this.base_tools.commit();

		//記錄新增訊息
		this.base_tools.writeADDMSG("EHF300000().addData()", show_sql, 
									dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
									dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			this.base_tools.writeADDMSG("EHF300000().addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF000700T0
			sql = "" +
			" DELETE FROM FOODT0 " +
			" WHERE 1=1 " +
			" AND PSFOODT0_01 = '"+(String)dataMap.get("PSFOODT0_01")+"' " +
			" AND PSFOODT0_07 = '"+false+"' " +
			" AND PSFOODT0_08 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
			sql_list.add(sql);
			
			//執行刪除
			int[] dataCount_array = this.base_tools.executeBatchSQL(sql_list);
			int dataCount = 0;
			int mainDataCount = 0;
			for(int i=0; i<dataCount_array.length; i++){
				dataCount += dataCount_array[i];
				mainDataCount = dataCount_array[i];
			}
			
			dataMap.put("MAIN_DATA_COUNT", mainDataCount);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF300000().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			
			
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF300000().delData()", sql+", "+e.getMessage(), 
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
			sql = " update FOODT0 set " +
		  	  " PSFOODT0_02=?,PSFOODT0_03=?," +
		  	  " PSFOODT0_04=?,PSFOODT0_05=?,PSFOODT0_06=?,"+
		  	  " PSFOODT0_07=?,USER_UPDATE=?,VERSION=VERSION+1,DATE_UPDATE=NOW()"+
		  	  " where PSFOODT0_01 = ? and PSFOODT0_08 = ?";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_02")==null?"":(String)dataMap.get("PSFOODT0_02"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_03")==null?"":(String)dataMap.get("PSFOODT0_03"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_04"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_05"));
			indexid++;
			p6stmt.setInt(indexid, Integer.parseInt((String)dataMap.get("PSFOODT0_06")));
			indexid++;
			p6stmt.setBoolean(indexid, tool.StringToBoolean((String)dataMap.get("PSFOODT0_07")));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("PSFOODT0_01"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
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
			this.base_tools.writeUPDATEMSG("EHF300000().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF300000().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			e.printStackTrace();
		}		
	}

	
	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		try{
			//AddDetail
			if("LexiconD".equals(detailType)){
				//新增明細資料
				this.addLexiconD(detailDataMap);
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		try{
			if("delFOODT1".equals(detailType)){
				//明細資料
				this.delFOODT1(detailDataMap);
			}
			
			
			if("delFOODT1_all".equals(detailType)){
				//明細資料(全部)
				this.delFOODT1_all(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
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
	
	
	private void addLexiconD(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{									
			//新增
			sql = "" +
			" INSERT INTO FOODT1 " +
			" (PSFOODT1_01, PSFOODT1_02, PSFOODT1_03, PSFOODT1_04, " +
			" PSFOODT1_05, PSFOODT1_06, PSFOODT1_07, PSFOODT1_08, " +			
			" PSFOODT1_09) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("PSFOODT0_01"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("PSFOODT1_02")==null?"":(String)detailDataMap.get("PSFOODT1_02"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("PSFOODT1_03")==null?"":(String)detailDataMap.get("PSFOODT1_03"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("PSFOODT1_04"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("PSFOODT1_05"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("PSFOODT1_06")==null?"":(String)detailDataMap.get("PSFOODT1_06"));  //
			indexid++;
			p6stmt.setInt(indexid, Integer.parseInt((String)detailDataMap.get("PSFOODT1_07")));  //
			indexid++;
			p6stmt.setBoolean(indexid, tool.StringToBoolean("1"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得
			String PSFOODT1_01 = (String)detailDataMap.get("PSFOODT0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫
			detailDataMap.put("KEY_ID", PSFOODT1_01);
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("SM010500.addLexiconD()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("SM010500.addLexiconD()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	
	private void delFOODT1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料一筆
			sql = "" +
			" DELETE FROM FOODT1 " +
			" WHERE 1=1 " +
			" AND PSFOODT1_01 = '"+(String)detailDataMap.get("PSFOODT1_01")+"' " +  //
			" AND PSFOODT1_04 = '"+(String)detailDataMap.get("PSFOODT1_04")+"' ";  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF300000().delFOODT1()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF300000().delFOODT1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			e.printStackTrace();
		}
	}
	
	
	private void delFOODT1_all(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料全部
			sql = "" +
			" DELETE FROM FOODT1 " +
			" WHERE 1=1 " +
			" AND PSFOODT1_01 = '"+(String)detailDataMap.get("PSFOODT1_01")+"' " ;  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF300000().delFOODT1_all()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF300000().delFOODT1_all()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			e.printStackTrace();
		}
	}
	
	
	public List queryLexiconDList(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List LexiconD_LIST = new ArrayList();
		
		try{
			//
			String sql = "" +
			" SELECT * " +
			" FROM FOODT1 " +
			" WHERE 1=1 " +
			" AND PSFOODT1_01 = '"+(String)queryCondMap.get("PSFOODT0_01")+"' " +
			" ORDER BY PSFOODT1_07, PSFOODT1_04 " ;
			
			LexiconD_LIST = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return LexiconD_LIST;
	}
	

	
}