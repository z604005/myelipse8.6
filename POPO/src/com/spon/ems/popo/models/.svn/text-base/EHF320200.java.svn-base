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

public class EHF320200 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	private BA_TOOLS tool;
	
	public EHF320200(){
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
			tool = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public EHF320200( String comp_id ){
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
			" SELECT EHF320200T0_01,EHF320200T0_02,EHF320100T0_03 AS EHF320200T0_03_TXT FROM ehf320200t0 " +
			" LEFT JOIN EHF320100T0 " +
			" ON EHF320200T0_03=EHF320100T0_01 " +
			" WHERE 1=1 " +
			" AND EHF320200T0_03=EHF320100T0_01 " +
			" AND EHF320200T0_04 like ? "; //公司代碼
			if(this.base_tools.existString((String)queryCondMap.get("EHF320200T0_01"))){	
				sql += " and EHF320200T0_01 = ? ";  
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF320200T0_02"))){	
				sql += " and EHF320200T0_02 = ? ";  
			}
			
			sql += " ORDER BY EHF320200T0_01,EHF320200T0_02 "; 

			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF320200T0_01"))){	
				pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF320200T0_01")));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF320200T0_02"))){	
				pstmt.setInt(indexid, Integer.parseInt((String)queryCondMap.get("EHF320200T0_02")));
				indexid++;
			}
			//System.out.println(sql);
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
		" SELECT ehf320200t0.*, " +
		" EHF320100T0_01, EHF320100T0_04, EHF320100T0_05, EHF320100T0_03, " +
		" A.EMS_CategoryT1_05 as EHF320100T0_04_TXT, B.EMS_CategoryT1_05 as EHF320100T0_05_TXT, " +
		" DATE_FORMAT(ehf320200t0.DATE_CREATE, '%Y/%m/%d') AS DATE_CREATE, " +
		" DATE_FORMAT(ehf320200t0.DATE_UPDATE, '%Y/%m/%d') AS DATE_UPDATE " +
		" FROM ehf320200t0 " +
		" LEFT JOIN EHF320100T0 " +
		" ON EHF320200T0_03=EHF320100T0_01 " +
		" LEFT JOIN EMS_CategoryT1 A " +
		" ON EHF320100T0_04=A.EMS_CategoryT1_04 " +
		" AND A.EMS_CategoryT1_01= 'MENU_MEAL' " +
		" LEFT JOIN EMS_CategoryT1 B " +
		" ON EHF320100T0_05=B.EMS_CategoryT1_04 " +
		" AND B.EMS_CategoryT1_01= 'MENU_TYPE' " +
		" WHERE 1=1 " +
		" AND EHF320200T0_03=EHF320100T0_01 " +
		" AND EHF320100T0_04=A.EMS_CategoryT1_04 " +
		" AND EHF320100T0_05=B.EMS_CategoryT1_04 " +
		" AND EHF320200T0_01 = '"+Integer.parseInt((String)queryCondMap.get("EHF320200T1_01"))+"' " +  //上菜順序天
		" AND EHF320200T0_02 = '"+Integer.parseInt((String)queryCondMap.get("EHF320200T1_02"))+"' " +  //上菜順序項次
		" AND EHF320200T0_04 = '"+(String)queryCondMap.get("COMP_ID")+"' ";	//公司代碼
		//執行SQL
		dataMap = this.base_tools.query(sql);
		
		//System.out.println(sql);
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
			
		sql = " Insert Into EHF320200T0 (" +
    	  	  " EHF320200T0_01,EHF320200T0_02,EHF320200T0_03,EHF320200T0_04, " +
    	  	  " USER_CREATE,USER_UPDATE,VERSION,DATE_CREATE,DATE_UPDATE) " +
    	  	  " values(?,?,?,?,?,?,1,NOW(),NOW()) ";
		
		//執行SQL
		PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
		P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
		int indexid = 1;

		p6stmt.setString(indexid, (String)dataMap.get("EHF320200T1_01"));  //上菜順序天
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("EHF320200T1_02"));  //上菜順序項次
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("EHF320100T0_01"));  //菜單編號
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料建立者
		indexid++;
		//show_sql = p6stmt.getQueryFromPreparedStatement();
		
		//執行
		p6stmt.executeUpdate();
		
		pstmt.close();
		p6stmt.close();
		
		//更新資料庫
		this.base_tools.commit();

		//記錄新增訊息
		this.base_tools.writeADDMSG("EHF320200().addData()", show_sql, 
									dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
									dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			this.base_tools.writeADDMSG("EHF320200().addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF320200T0
			sql = "" +
			" DELETE FROM EHF320200T0 " +
			" WHERE 1=1 " +
			" AND EHF320200T0_01 = '"+(String)dataMap.get("EHF320200T0_01")+"' " + //上菜順序天
			" AND EHF320200T0_02 = '"+(String)dataMap.get("EHF320200T0_02")+"' " + //上菜順序天
			" AND EHF320200T0_04 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			this.base_tools.writeDELETEMSG("EHF320200().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320200().delData()", sql+", "+e.getMessage(), 
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
			sql = " update EHF320200T0 set " +
		  	  " EHF320200T0_03=?, " +
		  	  " USER_UPDATE=?,VERSION=VERSION+1,DATE_UPDATE=NOW() "+
		  	  " where EHF320200T0_01 = ? and EHF320200T0_02 = ? and EHF320200T0_04 = ? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;

			p6stmt.setString(indexid, (String)dataMap.get("EHF320100T0_01"));  //菜單編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF320200T1_01"));  //上菜順序天
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF320200T1_02"));	//上菜順序項次
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;	
		
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
	//		show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF320200().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF320200().saveData()", 
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