package com.spon.flow.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;

/**
 *@author maybe
 *@version 1.0
 *@created 2016/12/29 上午11:32:04
 */
public class SFLOW_SITE implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public SFLOW_SITE(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public SFLOW_SITE( String comp_id ){
		
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
			" SELECT SFLOW_SITE_HD_01, SFLOW_SITE_HD_02 " +
			" FROM SFLOW_SITE_HD " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("SFLOW_SITE_HD_01"))){	//
				sql += " AND SFLOW_SITE_HD_01 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("SFLOW_SITE_HD_02"))){	//
				sql += " AND SFLOW_SITE_HD_02 like ?";
			}
			
			sql += "" +
			" AND SFLOW_SITE_HD_03 = ? " +  //公司代碼
			" ORDER BY SFLOW_SITE_HD_01 ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("SFLOW_SITE_HD_01"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("SFLOW_SITE_HD_01")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("SFLOW_SITE_HD_02"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("SFLOW_SITE_HD_02")+"%");
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
			" SELECT SFLOW_SITE_HD_01 ,SFLOW_SITE_HD_02, " +
			" DATE_FORMAT(SFLOW_SITE_HD.DATE_CREATE, '%Y/%m/%d %H：%i') AS DATE_CREATE, " +
			" DATE_FORMAT(SFLOW_SITE_HD.DATE_UPDATE, '%Y/%m/%d %H：%i') AS DATE_UPDATE " +
			" FROM SFLOW_SITE_HD " +
			" WHERE 1=1 " +
			" AND SFLOW_SITE_HD_01 = '"+(String)queryCondMap.get("SFLOW_SITE_HD_01")+"' " +  //FLOW編號
			" AND SFLOW_SITE_HD_03 = '"+(String)queryCondMap.get("COMP_ID")+"' ";  //公司代馬
			
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
			" INSERT INTO SFLOW_SITE_HD " +
			" (SFLOW_SITE_HD_01, SFLOW_SITE_HD_02, SFLOW_SITE_HD_03, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES (?, ?, ?, ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);			
			
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("SFLOW_SITE_HD_01"));	//FLOW編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("SFLOW_SITE_HD_02"));	//FLOW名稱
			indexid++;		
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得資料表序號
			String SFLOW_SITE_HD_01 = (String)dataMap.get("SFLOW_SITE_HD_01");			
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫FLOW編號
			dataMap.put("KEY_ID", SFLOW_SITE_HD_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("SFLOW_SITE().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("SFLOW_SITE.addData()", show_sql+", "+e.getMessage().replace("'", ""), 
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
			
			//DELETE SFLOW_SITE_T0
			sql = "" +
			" DELETE FROM SFLOW_SITE_T0 " +
			" WHERE 1=1 " +
			" AND SFLOW_SITE_T0_01 = '"+(String)dataMap.get("SFLOW_SITE_HD_01")+"' " ;
			sql_list.add(sql);
			
			//DELETE SFLOW_SITE_HD
			sql = "" +
			" DELETE FROM SFLOW_SITE_HD " +
			" WHERE 1=1 " +
			" AND SFLOW_SITE_HD_01 = '"+(String)dataMap.get("SFLOW_SITE_HD_01")+"' " ;
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
			this.base_tools.writeDELETEMSG("SFLOW_SITE().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			this.base_tools.rollback();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("SFLOW_SITE().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE SFLOW_SITE_HD SET " +
			" SFLOW_SITE_HD_02=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND SFLOW_SITE_HD_01=? " +
			" AND SFLOW_SITE_HD_03=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("SFLOW_SITE_HD_02"));	//FLOW名稱
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			p6stmt.setString(indexid, (String)dataMap.get("SFLOW_SITE_HD_01"));  //FLOW編號
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
			this.base_tools.writeUPDATEMSG("SFLOW_SITE().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("SFLOW_SITE().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		String sql_seqno = "";
		//int seqno_new;	//新的順序號碼
		
		try{
			if("SFLOW_SITE_T0".equals(detailType)){
				/*
				sql_seqno = "SELECT MAX(SM020101T1_02)+1 AS SM020101T1_02_NEW FROM SM020101T1 WHERE SM020101T1_01='"+(String)detailDataMap.get("SM020101T0_01")+"'";
				Statement stmt = this.base_tools.getConn().createStatement();
				ResultSet rs = stmt.executeQuery(sql_seqno);
				
				if(rs.next()){
					seqno_new = rs.getInt("SM020101T1_02_NEW");
				}else{
					seqno_new = 0;
				}
				rs.close();
				stmt.close();
				*/
				//新增明細資料
				sql = "" +
				" INSERT INTO SFLOW_SITE_T0 " +
				" (" +
				" SFLOW_SITE_T0_01, SFLOW_SITE_T0_02, SFLOW_SITE_T0_03, SFLOW_SITE_T0_04, " +
				" SFLOW_SITE_T0_05, SFLOW_SITE_T0_06, SFLOW_SITE_T0_07, SFLOW_SITE_T0_08, " +		
				" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
				" VALUES ( " +
				" ?, ?, ?, ?, " +
				" ?, ?, ?, ?, " +
				" ?, ?, 1, NOW(), NOW() " +
				" ) ";
				
				//執行SQL
				PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,
						pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, (String)detailDataMap.get("SFLOW_SITE_HD_01"));  //FLOW編號
				indexid++;
				p6stmt.setInt(indexid, Integer.parseInt((String)detailDataMap.get("SFLOW_SITE_T0_02")));  //站別順序號碼
				indexid++;
				p6stmt.setString(indexid, (String)detailDataMap.get("SFLOW_SITE_T0_03"));  //站別名稱
				indexid++;
				p6stmt.setString(indexid, (String)detailDataMap.get("SFLOW_SITE_T0_04"));  //FLOW狀態號碼
				indexid++;
				p6stmt.setString(indexid, (String)detailDataMap.get("SFLOW_SITE_T0_05"));  //FLOW狀態名稱
				indexid++;
				p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
				indexid++;
				p6stmt.setString(indexid, (String)detailDataMap.get("SFLOW_SITE_T0_07"));  //處理人員類型
				indexid++;
				p6stmt.setString(indexid, (String)detailDataMap.get("SFLOW_SITE_T0_08"));  //處理人員的指定Key值
				indexid++;
				p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //資料建立者
				indexid++;
				p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //資料建立者
				indexid++;
				
				//System.out.println(p6stmt.getQueryFromPreparedStatement());
				show_sql = p6stmt.getQueryFromPreparedStatement();
				//執行
				p6stmt.executeUpdate();
				
				//取得
				String SFLOW_SITE_T0_01 = (String)detailDataMap.get("SFLOW_SITE_HD_01");
				
				//更新資料庫
				this.base_tools.commit();
				
				//回寫
				detailDataMap.put("KEY_ID", SFLOW_SITE_T0_01);
				
				pstmt.close();
				p6stmt.close();
				
				//記錄細項新增訊息
				this.base_tools.writeADDMSG("SFLOW_SITE.addDetailData()", show_sql, 
								detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
								detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			}
			
		}catch(Exception e){
			
			this.base_tools.rollback();
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("SFLOW_SITE.addDetailData()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			if("SFLOW_SITE_T0".equals(detailType)){
				//刪除明細資料
				sql = "" +
				" DELETE FROM SFLOW_SITE_T0 " +
				" WHERE 1=1 " +
				" AND SFLOW_SITE_T0_01 = '"+(String)detailDataMap.get("SFLOW_SITE_T0_01")+"' " +  //FLOW編號
				" AND SFLOW_SITE_T0_02 = '"+(Integer)detailDataMap.get("SFLOW_SITE_T0_02")+"' ";  //站別順序號碼
				//執行刪除
				int dataCount = this.base_tools.delete(sql);
				
				//記錄刪除訊息
				this.base_tools.writeDELETEMSG("SFLOW_SITE().delDetailData()",
											   sql+" total delete "+dataCount+"rows data", 
											   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
				/*
				//重新訂單明細資料的 - "順序號碼"
				sql = "" +
				" SELECT SM020101T1_01, SM020101T1_02 " +
				" FROM SM020101T1 " +
				" WHERE 1=1 " +
				" AND SM020101T1_01 = '"+(String)detailDataMap.get("SM020101T1_01")+"' " +
				" ORDER BY SM020101T1_02 ";
				//取得訂單明細資料清單
				List sm020101t1 = this.base_tools.queryList(sql);
				Iterator it = sm020101t1.iterator();
				Map tempMap  = null;
				int snCount = 0;
				List sql_list = new ArrayList();
				//針對每一筆訂單明細資料清單做順序號碼的處裡
				while(it.hasNext()){
					tempMap = (Map) it.next();
					sql = "" +
					" UPDATE SM020101T1 SET " +
					" SM020101T1_02 = '"+snCount+"' " +
					" WHERE 1=1 " +
					" AND SM020101T1_01 = '"+(String)tempMap.get("SM020101T1_01")+"' " +  //
					" AND SM020101T1_02 = '"+(Integer)tempMap.get("SM020101T1_02")+"' ";  //順序號碼
					sql_list.add(sql);
					snCount++;
				}
				this.base_tools.executeBatchSQL(sql_list);
				*/
				//更新資料庫
				this.base_tools.commit();
			}
			
		}catch(Exception e){
			
			this.base_tools.rollback();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("SFLOW_SITE().delDetailData()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
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
	
	public List querySFLOW_SITE_T0_DETAILList(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List SFLOW_SITE_T0_DETAILList = new ArrayList();
		
		try{
			//
			String sql = "" +
			" SELECT " +
			" SFLOW_SITE_T0_01, SFLOW_SITE_T0_02 ,SFLOW_SITE_T0_03, SFLOW_SITE_T0_04, " +
			" SFLOW_SITE_T0_05, SFLOW_SITE_T0_06, SFLOW_SITE_T0_07, SFLOW_SITE_T0_08, " +
			" CASE SFLOW_SITE_T0_07 WHEN 01 THEN '指定人員' WHEN 02 THEN '指定欄位' WHEN 03 THEN '指定組織' " +
			" ELSE '指定錯誤' END AS SFLOW_SITE_T0_07_TXT" +
			" FROM SFLOW_SITE_T0 " +
			" WHERE 1=1 " +
			" AND SFLOW_SITE_T0_01 = '"+(String)queryCondMap.get("SFLOW_SITE_HD_01")+"' " ;
			
			SFLOW_SITE_T0_DETAILList = this.base_tools.queryList(sql);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return SFLOW_SITE_T0_DETAILList;
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
