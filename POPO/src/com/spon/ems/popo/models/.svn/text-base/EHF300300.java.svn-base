package com.spon.ems.popo.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
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

public class EHF300300 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	private BA_TOOLS tool;
	
	public EHF300300(){
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
			tool = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public EHF300300( String comp_id ){
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
			" SELECT  EHF300300T0_01,EHF300300T0_02, EHF300300T0_03,EHF300300T0_04 " +
			" FROM EHF300300T0 " +
			" WHERE 1=1 " +
			" AND EHF300300T0_04 like ? "; //公司代碼
			if(this.base_tools.existString((String)queryCondMap.get("EHF300300T0_02_select"))){	
				sql += " and EHF300300T0_02 like ? ";  //餐點名稱
			}
		//	System.out.println(sql);
			//執行SQL
				PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
				int indexid = 1;
				
				pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
				indexid++;
				
				if(this.base_tools.existString((String)queryCondMap.get("EHF300300T0_02_select"))){
					pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF300300T0_02_select")+"%");
					indexid++;
				}
				
			
				dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
				pstmt.close();
				
			
		} catch(Exception e){
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
		" SELECT EHF300300T0_01, EHF300300T0_02, EHF300300T0_03, " +
		" USER_CREATE, USER_UPDATE, VERSION, " +
		" DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE " +
		" FROM EHF300300T0 " +
		" WHERE 1=1 " +
		" AND EHF300300T0_01 = '"+(String)queryCondMap.get("EHF300300T0_01")+"' " +  //
		" AND EHF300300T0_04 = '"+(String)queryCondMap.get("COMP_ID")+"' ";	//公司代碼
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
			
		sql = " Insert Into EHF300300T0 (" +
    	  	  " EHF300300T0_01,EHF300300T0_02,EHF300300T0_03, " +
    	  	  " EHF300300T0_04, " +
    	  	  " USER_CREATE,USER_UPDATE,VERSION,DATE_CREATE,DATE_UPDATE) " +
    	  	  " values(?,?,?,?,?,?,1,NOW(),NOW())";
		
		//執行SQL
		PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
		P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
		int indexid = 1;

		p6stmt.setString(indexid, (String)dataMap.get("UID"));  
		indexid++;
		p6stmt.setString(indexid, (String)dataMap.get("EHF300300T0_02_TXT")==null?"":(String)dataMap.get("EHF300300T0_02_TXT"));  
		indexid++;
		p6stmt.setInt(indexid, (Integer)dataMap.get("EHF300300T0_03"));  
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
		this.base_tools.writeADDMSG("EHF300300.addData()", show_sql, 
									dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
									dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			this.base_tools.writeADDMSG("EHF300300.addData()", show_sql+", "+e.getMessage(), 
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
			" DELETE FROM EHF300300T0 " +
			" WHERE 1=1 " +
			" AND EHF300300T0_01 = '"+(String)dataMap.get("EHF300300T0_01")+"' " +
			" AND EHF300300T0_04 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			this.base_tools.writeDELETEMSG("EHF300300.delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			
			
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF300300.delData()", sql+", "+e.getMessage(), 
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
			sql = " update EHF300300T0 set " +
		  	  " EHF300300T0_02=?,EHF300300T0_03=?," +
		  	  " USER_UPDATE=?,VERSION=VERSION+1,DATE_UPDATE=NOW()"+
		  	  " where EHF300300T0_01 = ? and EHF300300T0_04 = ?";
		
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300300T0_02_TXT")==null?"":(String)dataMap.get("EHF300300T0_02_TXT"));
			indexid++;
			p6stmt.setInt(indexid, Integer.parseInt((String)dataMap.get("EHF300300T0_03")));
			indexid++;	
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300300T0_01"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID")); 
			indexid++;
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF300300.saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF300300.saveData()", 
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
			if("ehf300300t1_add".equals(detailType)){
				//新增明細資料
				this.addehf300300t1_add(detailDataMap);
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		try{
			if("del300300t1".equals(detailType)){
				//明細資料
				this.del300300t1(detailDataMap);
			}
			
			
			if("del300300t1_all".equals(detailType)){
				//明細資料(全部)
				this.del300300t1_all(detailDataMap);
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
	
	
	private void addehf300300t1_add(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		String sql_seqno = "";
		int seqno_new;	//新的順序號碼
		try{			
			
			sql_seqno = "SELECT MAX(EHF300300T1_02)+1 AS EHF300300T1_02_NEW FROM EHF300300T1 WHERE EHF300300T1_01='"+(String)detailDataMap.get("EHF300300T0_01")+"'";
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql_seqno);
			
			if(rs.next()){
				seqno_new = rs.getInt("EHF300300T1_02_NEW");
			}else{
				seqno_new = 0;
			}
			rs.close();
			stmt.close();
			
			//新增
			sql = "" +
			" INSERT INTO EHF300300T1 " +
			" (EHF300300T1_01,EHF300300T1_02,EHF300300T1_03) " +		
			" VALUES (?,?,?) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF300300T0_01"));  
			indexid++;
			p6stmt.setInt(indexid, seqno_new);  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF300300T1_03")==null?"":(String)detailDataMap.get("EHF300300T1_03"));  //
			indexid++;
//			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
//			indexid++;

			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
		
			p6stmt.executeUpdate();
			
			//取得
			String EHF300300T1_01 = (String)detailDataMap.get("EHF300300T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫
			detailDataMap.put("KEY_ID", EHF300300T1_01);
			
			pstmt.close();
			p6stmt.close();
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF300300.addehf300300t1_add()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF300300.addehf300300t1_add()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	
	private void del300300t1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料一筆
			sql = "" +
			" DELETE FROM EHF300300T1 " +
			" WHERE 1=1 " +
			" AND EHF300300T1_01 = '"+(String)detailDataMap.get("EHF300300T1_01")+"' "+
			" AND EHF300300T1_02 = '"+(String)detailDataMap.get("EHF300300T1_02")+"' ";  
	
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF300300.del300300t1()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			//重新訂單明細資料的 - "順序號碼"
			sql = "" +
			" SELECT EHF300300T1_01, EHF300300T1_02 " +
			" FROM EHF300300T1 " +
			" WHERE 1=1 " +
			" AND EHF300300T1_01 = '"+(String)detailDataMap.get("EHF300300T1_01")+"' " +
			" ORDER BY EHF300300T1_02 ";
			//取得訂單明細資料清單
			List sm300300t1 = this.base_tools.queryList(sql);
			Iterator it = sm300300t1.iterator();
			Map tempMap  = null;
			int snCount = 0;
			List sql_list = new ArrayList();
			//針對每一筆訂單明細資料清單做順序號碼的處裡
			while(it.hasNext()){
				tempMap = (Map) it.next();
				sql = "" +
				" UPDATE EHF300300T1 SET " +
				" EHF300300T1_02 = '"+snCount+"' " +
				" WHERE 1=1 " +
				" AND EHF300300T1_01 = '"+(String)tempMap.get("EHF300300T1_01")+"' " +  //
				" AND EHF300300T1_02 = '"+(Integer)tempMap.get("EHF300300T1_02")+"' ";  //順序號碼
				sql_list.add(sql);
				snCount++;
			}
			this.base_tools.executeBatchSQL(sql_list);
			
			
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF300300.del300300t1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			e.printStackTrace();
		}
	}
	
	
	private void del300300t1_all(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料全部
			sql = "" +
			" DELETE FROM EHF300300T1 " +
			" WHERE 1=1 " +
			" AND EHF300300T1_01 = '"+(String)detailDataMap.get("EHF300300T1_01")+"' " ;  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF300300.del300300t1_all()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			//重新訂單明細資料的 - "順序號碼"
			sql = "" +
			" SELECT EHF300300T1_01, EHF300300T1_02 " +
			" FROM EHF300300T1 " +
			" WHERE 1=1 " +
			" AND EHF300300T1_01 = '"+(String)detailDataMap.get("EHF300300T1_01")+"' " +
			" ORDER BY EHF300300T1_02 ";
			//取得訂單明細資料清單
			List sm300300t1 = this.base_tools.queryList(sql);
			Iterator it = sm300300t1.iterator();
			Map tempMap  = null;
			int snCount = 0;
			List sql_list = new ArrayList();
			//針對每一筆訂單明細資料清單做順序號碼的處裡
			while(it.hasNext()){
				tempMap = (Map) it.next();
				sql = "" +
				" UPDATE EHF300300T1 SET " +
				" EHF300300T1_02 = '"+snCount+"' " +
				" WHERE 1=1 " +
				" AND EHF300300T1_01 = '"+(String)tempMap.get("EHF300300T1_01")+"' " +  //
				" AND EHF300300T1_02 = '"+(Integer)tempMap.get("EHF300300T1_02")+"' ";  //順序號碼
				sql_list.add(sql);
				snCount++;
			}
			this.base_tools.executeBatchSQL(sql_list);
			
			
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF300300.del300300t1_all()",
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
			" SELECT *,EMS_CategoryT1_05 as EHF300300T1_03_TXT "+
			" FROM EHF300300T1 a "+
			" LEFT JOIN EHF300300T0 b ON a.EHF300300T1_01 = b.EHF300300T0_01 "+
			" LEFT JOIN EMS_CategoryT1 c ON a.EHF300300T1_03 = c.EMS_CategoryT1_04 and c.EMS_CategoryT1_01='MENU_MEAL' "+
			" WHERE EHF300300T1_01 = '"+(String)queryCondMap.get("EHF300300T0_01")+"' "+ 
			" ORDER BY EHF300300T1_02  ";
		
			LexiconD_LIST = this.base_tools.queryList(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return LexiconD_LIST;
	}


	

	
}