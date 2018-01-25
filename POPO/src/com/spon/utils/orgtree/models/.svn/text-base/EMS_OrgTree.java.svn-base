package com.spon.utils.orgtree.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts.action.ActionMessage;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;

public class EMS_OrgTree implements QueryFunction,EditFunction,EditDetailFunction,BaseSystem {
	private BaseFunction base_tools;
	
	public EMS_OrgTree(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EMS_OrgTree( String comp_id ){
		
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
		" SELECT " +
		" ems_orgtree_01 AS EMS_OrgTree_01, " +
		" ems_orgtree_02 AS EMS_OrgTree_02, " +
		" ems_orgtree_03 AS EMS_OrgTree_03, " +
		" ems_orgtree_04 AS EMS_OrgTree_04, " +
		" ems_orgtree_05 AS EMS_OrgTree_05, " +
		" ems_orgtree_06 AS EMS_OrgTree_06 " +
		" FROM ems_orgtree " +
		" WHERE 1=1 "+
		" AND ems_orgtree_08 = ? " +//公司代碼
		" GROUP BY ems_orgtree_01"; 
		
		//執行SQL
		PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
		int indexid = 1;
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
		Map dataMap = new HashMap();
		List dataList = null;
		try{
			List EHF020800T1_LIST = new ArrayList();
			//BIND FLOW DATA
			//BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
			
			
			String sql = "" +
			" SELECT  " +
			" ems_orgtree_01 AS EMS_OrgTree_01, " +
			" ems_orgtree_02 AS EMS_OrgTree_02, " +
			" ems_orgtree_03 AS EMS_OrgTree_03, " +
			" ems_orgtree_04 AS EMS_OrgTree_04, " +
			" ems_orgtree_05 AS EMS_OrgTree_05, " +
			" ems_orgtree_06 AS EMS_OrgTree_06, " +
			" ems_orgtree_07 AS EMS_OrgTree_07 " +
			" FROM ems_orgtree " +
			" WHERE 1=1 "+
			" AND ems_orgtree_08 = ? "+
			" AND ems_orgtree_01 = '"+(String)queryCondMap.get("ems_orgtree_01")+"' " +
		   // " AND ems_orgtree_05 <>'' " +
		    " GROUP BY ems_orgtree_01"; 
			
			
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
		
			if(dataList.size()>0){
				Map getMap =(Map) dataList.get(0);
				dataMap.put("EMS_OrgTree_03", getMap.get("EMS_OrgTree_03"));
				dataMap.put("EMS_OrgTree_04", getMap.get("EMS_OrgTree_04"));
				dataMap.put("EMS_OrgTree_07", getMap.get("EMS_OrgTree_07").toString());
				
				//查詢明細
				sql = "" +
				" SELECT  " +
				" ems_orgtree_01 AS EMS_OrgTree_01, " +
				" ems_orgtree_02 AS EMS_OrgTree_02, " +
				" ems_orgtree_03 AS EMS_OrgTree_03, " +
				" ems_orgtree_04 AS EMS_OrgTree_04, " +
				" ems_orgtree_05 AS EMS_OrgTree_05, " +
				" ems_orgtree_06 AS EMS_OrgTree_06, " +
				" ems_orgtree_07 AS EMS_OrgTree_07 " +
				" FROM ems_orgtree " +
				" WHERE 1=1 "+
				" AND ems_orgtree_08 = ? "+
				" AND ems_orgtree_01 = '"+(String)queryCondMap.get("ems_orgtree_01")+"' " +
			    " AND ems_orgtree_05 <>'' "; 
				PreparedStatement pstmt_01 = this.base_tools.getConn().prepareStatement(sql);
				indexid = 1;
				pstmt_01.setString(indexid, (String)queryCondMap.get("COMP_ID"));
				indexid++;
				dataList = this.base_tools.resultSetToList(pstmt_01.executeQuery());
				dataMap.put("EMS_OrgTree_LIST", dataList);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}

	@Override
	public void addData(Map dataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		try{
			//Add
			sql = "" +
			" INSERT INTO ems_orgtree " +
			" ( " +
			" ems_orgtree_01, ems_orgtree_02, " +
			" ems_orgtree_03, ems_orgtree_04, " +
			" ems_orgtree_05, ems_orgtree_06, " +
			" ems_orgtree_07, ems_orgtree_08, " +
			" USER_CREATE, USER_UPDATE, " +
			" VERSION, " +
			" DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, ?, ?," +
			" ?, ?, ?, ?," +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EMS_OrgTree_01"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EMS_OrgTree_02"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EMS_OrgTree_03"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EMS_OrgTree_04"));  
			indexid++;
			p6stmt.setString(indexid, "");  
			indexid++;
			p6stmt.setString(indexid, "");  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EMS_OrgTree_07"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID")); 
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			//執行
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫 加班表單編號
			dataMap.put("KEY_ID", (String)dataMap.get("EMS_OrgTree_01")+","+(String)dataMap.get("EMS_OrgTree_02"));		
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		
		try{
			List sql_list = new ArrayList();
			
			//DELETE EHF030108T0
			sql = "" +
			" DELETE FROM ems_orgtree " +
			" WHERE 1=1 " +
			" AND ems_orgtree_01 = '"+(String)dataMap.get("EMS_OrgTree_01")+"' " +  
			" AND ems_orgtree_08 = '"+(String)dataMap.get("COMP_ID")+"' ";
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
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		try{
			//Add
			sql = "" +
			" INSERT INTO ems_orgtree " +
			" ( " +
			" ems_orgtree_01, ems_orgtree_02, " +
			" ems_orgtree_03, ems_orgtree_04, " +
			" ems_orgtree_05, ems_orgtree_06, " +
			" ems_orgtree_07, ems_orgtree_08, " +
			" USER_CREATE, USER_UPDATE, " +
			" VERSION, " +
			" DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, ?, ?," +
			" ?, ?, ?, ?," +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_01"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_02"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_03"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_04"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_05"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_06"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_07"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID")); 
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			//執行
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
		
			//回寫 編號
			detailDataMap.put("KEY_ID", (String)detailDataMap.get("EMS_OrgTree_01")+","+(String)detailDataMap.get("EMS_OrgTree_02"));	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		
		try{
			sql = "" +
			" DELETE FROM EMS_OrgTree " +
			" WHERE 1=1 " +
			" AND EMS_OrgTree_05 = '"+(String)detailDataMap.get("EMS_OrgTree_05")+"' " +  
			" AND EMS_OrgTree_08 = '"+(String)detailDataMap.get("COMP_ID")+"' "; 
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	
	
	public void delDetailData_UPDATE(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		
		try{
			//Update
		    sql = "" +
			" UPDATE ems_orgtree " +
			" SET " +
			" ems_orgtree_05 = ?, " +
			" ems_orgtree_06 = ?, " +
			" DATE_UPDATE = NOW(), " +
			" VERSION = VERSION+1 " +
			" WHERE 1=1 " +
			" AND ems_orgtree_01 = ? " + 
			" AND ems_orgtree_02 = ? " +
			" AND ems_orgtree_03 = ? " +
			" AND ems_orgtree_04 = ? " +
			" AND ems_orgtree_08 = ? ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, "");  
			indexid++;
			p6stmt.setString(indexid, "");  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_01"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_02"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_03"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_04")); 
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));
			indexid++;
		
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
		
		
			//回寫 編號
			detailDataMap.put("KEY_ID", (String)detailDataMap.get("EMS_OrgTree_01")+","+(String)detailDataMap.get("EMS_OrgTree_02"));	
			
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
		try{
			//Update
			String sql = "" +
			" UPDATE ems_orgtree " +
			" SET " +
			" ems_orgtree_05 = ?, " +
			" ems_orgtree_06 = ?, " +
			" DATE_UPDATE = NOW(), " +
			" VERSION = VERSION+1 " +
			" WHERE 1=1 " +
			" AND ems_orgtree_01 = ? " + 
			" AND ems_orgtree_02 = ? " +
			" AND ems_orgtree_03 = ? " +
			" AND ems_orgtree_04 = ? " +
			" AND ems_orgtree_08 = ? ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_05"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_06"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_01"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_02"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_03"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EMS_OrgTree_04")); 
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));
			indexid++;
			
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
		
		
			//回寫 編號
			detailDataMap.put("KEY_ID", (String)detailDataMap.get("EMS_OrgTree_01")+","+(String)detailDataMap.get("EMS_OrgTree_02"));	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 查詢組織樹參數設定
	 */
	public Map queryOrgTreeConfig(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		
		try{
			
			//QueryEdit
			String sql = " select * from ems_orgtree_config where COMP_ID = '" + (String)queryCondMap.get("COMP_ID") + "'";
			
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	/**
	 * 儲存組織樹參數設定
	 */
	public void saveOrgTreeConfig(Map detailDataMap) {
		// TODO Auto-generated method stub
		try{
			//Update
			String sql = "" +
			" UPDATE ems_orgtree_config " +
			" SET " +
			" tree_layer_font_color_01 = ?, " +
			" tree_layer_background_color_01 = ?, " +
			" tree_layer_font_color_02 = ?, " +
			" tree_layer_background_color_02 = ?, " +
			" tree_layer_font_color_03 = ?, " +
			" tree_layer_background_color_03 = ?, " +
			" tree_layer_font_color_04 = ?, " +
			" tree_layer_background_color_04 = ?, " +
			" tree_layer_font_color_05 = ?, " +
			" tree_layer_background_color_05 = ?, " +
			" tree_layer_font_color_06 = ?, " +
			" tree_layer_background_color_06 = ?, " +
			" tree_layer_font_color_07 = ?, " +
			" tree_layer_background_color_07 = ?, " +
			" tree_layer_font_color_08 = ?, " +
			" tree_layer_background_color_08 = ?, " +
			" tree_layer_font_color_09 = ?, " +
			" tree_layer_background_color_09 = ?, " +
			" COMP_ID = ?, " +
			" DATE_UPDATE = NOW(), " +
			" VERSION = VERSION+1 ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_01"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_01"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_02"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_02"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_03"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_03"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_04"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_04"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_05"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_05"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_06"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_06"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_07"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_07"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_08"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_08"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_font_color_09"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("tree_layer_background_color_09"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
		}catch(Exception e){
			e.printStackTrace();
		}
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
