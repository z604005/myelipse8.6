package com.spon.ems.popo.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.mysql.jdbc.Connection;
import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.popo.forms.EHF320300M0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;

public class EHF320300 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF320300(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF320300( String comp_id ){
		
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
			" SELECT EHF320300T0_01, EHF320300T0_02, EHF320300T0_03, EHF320300T0_04 " +			
			" FROM EHF320300T0 " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF320300T0_02_01"))){	//使用者選取的開始日期
				sql += " and EHF320300T0_02 >= ?";
			}
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF320300T0_02_02"))){	//使用者選取的結束日期
				sql += " and EHF320300T0_02 <= ?";
			}
			
			
			sql += "" +
			" AND EHF320300T0_04 like ? " +  //公司代碼
			" ORDER BY EHF320300T0_02 DESC " ;
			if("".equals((String)queryCondMap.get("EHF320300T0_02_01")) && "".equals((String)queryCondMap.get("EHF320300T0_02_02"))){
				sql += " LIMIT 7 ";
			} 
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF320300T0_02_01"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF320300T0_02_01"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF320300T0_02_02"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF320300T0_02_02"));
				indexid++;
			}	
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
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
			" FROM EHF320300T0 " +
			" WHERE 1=1 " +
			" AND EHF320300T0_01 = '"+(String)queryCondMap.get("EHF320300T0_01")+"' " +  //系統編號
			" AND EHF320300T0_04 = '"+(String)queryCondMap.get("COMP_ID")+"' ";	//公司代碼
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
			" INSERT INTO EHF320300T0 " +
			" (EHF320300T0_01, EHF320300T0_02, EHF320300T0_03, " +
			" EHF320300T0_04, " +			
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, " +
			" ?, " +			
			" ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF320300T0_01"));  //系統編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF320300T0_02"));  //日期
			indexid++;
			p6stmt.setInt(indexid, Integer.valueOf((String)dataMap.get("EHF320300T0_03")).intValue());  //上菜順序天
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
			
			
			String SysNum = (String)dataMap.get("EHF320300T0_01");
			//下面是自動編號時用,吐出他的int編號
			//String SysNum = String.valueOf(this.base_tools.getDb_tools().getLastInsertId(this.base_tools.getConn()));
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫 職務系統代碼
			dataMap.put("KEY_ID", SysNum);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF320300().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF320300.addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF320300T0
			sql = "" +
			" DELETE FROM EHF320300T0 " +
			" WHERE 1=1 " +
			" AND EHF320300T0_01 = '"+(String)dataMap.get("EHF320300T0_01")+"' " +
			" AND EHF320300T0_04 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
			sql_list.add(sql);
			
			
			//執行刪除明細
			this.delDetailData("delall", dataMap);
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
			this.base_tools.writeDELETEMSG("EHF320300().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320300().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE EHF320300T0 SET " +
			" EHF320300T0_01=?, EHF320300T0_02=?, EHF320300T0_03=?, " +			
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF320300T0_04=? " +
			" AND EHF320300T0_01=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF320300T0_01"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF320300T0_02"));
			indexid++;
			p6stmt.setInt(indexid, Integer.valueOf((String)dataMap.get("EHF320300T0_03")).intValue());  //顯示順序
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF320300T0_01"));  //職務系統代碼
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
			this.base_tools.writeUPDATEMSG("EHF320300().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF320300().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		try{
			//AddDetail
			if("EHF320300T1".equals(detailType)){
				//新增明細資料
				this.addEHF320300T1(detailDataMap);
			}
	
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private void addEHF320300T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{									
			//新增
			sql = "" +
			" INSERT INTO EHF320300T1 " +
			" (EHF320300T1_01, EHF320300T1_02, EHF320300T1_03, EHF320300T1_04, EHF320300T1_05," +
			" USER_CREATE, DATE_CREATE) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320300T1_01"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320300T1_02"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320300T1_03")==null?"":(String)detailDataMap.get("EHF320300T1_03"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320300T1_04"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320300T1_05"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //資料建立者,為何傳不進來?
			indexid++;

			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得
			String EHF320300T1_01 = (String)detailDataMap.get("EHF320300T1_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫
			detailDataMap.put("KEY_ID", EHF320300T1_01);
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("SM010500.addLexicon()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("SM010500.addLexicon()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		try{
			if("delone".equals(detailType)){
				//刪除一筆明細資料
				this.delone(detailDataMap);
			}
			
			
			if("delall".equals(detailType)){
				//刪除全部明細資料
				this.delall(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private void delone(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料一筆
			sql = "" +
			" DELETE FROM EHF320300T1 " +
			" WHERE 1=1 " +
			" AND EHF320300T1_01 = '"+(String)detailDataMap.get("EHF320300T1_01")+"' " +  //
			" AND EHF320300T1_02 = '"+(String)detailDataMap.get("EHF320300T1_02")+"' ";  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF320300().delone()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320300().delEHF320300T1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			e.printStackTrace();
		}
	}
	
	
	private void delall(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料全部
			sql = "" +
			" DELETE FROM EHF320300T1 " +
			" WHERE 1=1 " +
			" AND EHF320300T1_01 = '"+(String)detailDataMap.get("EHF320300T0_01")+"' " ;  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF320300().delall()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320300().delFOODT1_all()",
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
	 * 檢查路線名稱是否使用中
	 * @param RouteNum
	 * @param HR_JobName
	 * @param EHF320300T0_06
	 * @return
	 */
	public boolean selectEHF310100T0(String RouteNum, String RouteName,
			String EHF310100T0_32) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = " SELECT EHF310100T0_17 FROM EHF310100T0 " +
				  " WHERE EHF310100T0_32 = '"+EHF310100T0_32+"' " +
				  " AND EHF310100T0_17 = '"+RouteNum+"' ";
			
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
	
	//檢查日期是否重覆
	public boolean selectData(String Data,
			String comp_id) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = " SELECT * FROM EHF320300T0 " +
				  " WHERE EHF320300T0_04 = '"+comp_id+"' " +
				  " AND EHF320300T0_02 = '"+Data+"' ";
			
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
	
	//檢查上菜順序是否重覆
	public boolean selectEHF320300T1(String EHF320300T1_01,
			String EHF320300T1_02) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = 	" SELECT EHF320300T1_01 FROM EHF320300T1 " +
					" WHERE 1=1 " +
					" AND EHF320300T1_01 = '"+ EHF320300T1_01 +"' " +  //
					" AND EHF320300T1_02 = '"+ EHF320300T1_02 +"' ";  //
			
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
	
	
	//檢查日期是否已經過去
	public boolean selectPreterit(String No, String comp_id) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			
			sql = " SELECT * FROM EHF320300T0 " +
				  " WHERE EHF320300T0_04 = '"+comp_id+"' " +
				  " AND EHF320300T0_01 = '"+No+"' ";
			
			dataMap = this.base_tools.query(sql);
			
			
			
			//欲轉換的日期字串
			String dateString =dataMap.get("EHF320300T0_02").toString();
			//設定日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			//進行轉換
			Date todate = sdf.parse(dateString);
			
			
			Date date = new Date();
	        Calendar c = Calendar.getInstance();
	        //Date date = c.getTime();
	        c.setTime(date); 
	        c.add(c.DATE,-1);
	        date = c.getTime();
	                 
	        if(todate.before(date)) {
	            chkFlag = true;
	        }
	        
			
			
			
			
		}catch(Exception e){			
			e.printStackTrace();
		}
		
		return chkFlag;
	}
	
	public List queryDetailList(Map queryCondMap, String comp_id) {
		// TODO Auto-generated method stub
		
		List setEHF320300_DETAIL = new ArrayList();
		
		try{
			//
			String sql = "" +
			" SELECT  EHF320300T1_01 , EHF320300T1_02 , EHF320300T1_03 , EHF320300T1_04 , EHF320300T1_05  " +
			" FROM EHF320300T1 " +
			" WHERE 1=1 " +
			" AND EHF320300T1_01 = '"+(String)queryCondMap.get("EHF320300T0_01")+"' " +
			" ORDER BY EHF320300T1_01, EHF320300T1_02 " ;
			
			setEHF320300_DETAIL = this.base_tools.queryList(sql);
			  
			//Map testMap = new HashMap();
			//testMap = this.listconversionmap(setEHF320300_DETAIL, comp_id);
			//System.out.println(testMap.get("EHF320300T1_02"));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return setEHF320300_DETAIL;
	}
	
	public List queryMealcategoryDetailList(String UID, String comp_id) {
		// TODO Auto-generated method stub
		
		List DetailList = new ArrayList();
		
		try{
			//
			String sql = "" +
			" SELECT EHF320300T1_03 " +
			" FROM EHF320300T1 " +
			" WHERE 1=1 " +
			" AND EHF320300T1_01 = '"+UID+"' " +
			" Group By EHF320300T1_03 " +
			" ORDER BY EHF320300T1_01, EHF320300T1_03 " ;
			
			DetailList = this.base_tools.queryList(sql);
			  
		}catch(Exception e){
			e.printStackTrace();
		}
		return DetailList;
	}
	
	public List queryMealNameDetailList(String UID, String EHF320300T1_03, String comp_id) {
		// TODO Auto-generated method stub
		
		List DetailList = new ArrayList();
		
		try{
			//
			String sql = "" +
			" SELECT EHF320300T1_02 , EHF320300T1_05 " +
			" FROM EHF320300T1 " +
			" WHERE 1=1 " +
			" AND EHF320300T1_01 = '"+UID+"' " +
			" AND EHF320300T1_03 = '"+EHF320300T1_03+"' " +
			" ORDER BY EHF320300T1_01, EHF320300T1_02 " ;
			
			DetailList = this.base_tools.queryList(sql);
			  
		}catch(Exception e){
			e.printStackTrace();
		}
		return DetailList;
	}
	

	public String getVegetables(String EHF320100T0_01, String comp_id){
		
		String Vegetables = null;
		Map dataMap = null;
		
		try{
				
			String sql = "" +
			" SELECT EHF320100T0_03 " +
			" FROM EHF320100T0 " +
			" WHERE 1=1 " +
			" AND EHF320100T0_01 = '"+EHF320100T0_01+"' " +
			" AND EHF320100T0_08 = '"+comp_id+"' " ;
				
			
			dataMap = this.base_tools.query(sql);
			
			Vegetables = dataMap.get("EHF320100T0_03").toString();
			
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return Vegetables;
		
	}
	
	
	/**
	* ITEM_ID轉為ITEM_VALUE
	*/
	public String getITEM_VALUE(String item_ID, String classKey, String comp_id ){
		
		Map dataMap = null;
		
		String ItemValue = null;
		
		try{
				
			String sql = "" +
			" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = '"+classKey+"' " +
			" AND EMS_CategoryT1_04 = '"+item_ID+"' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		
			
			dataMap = this.base_tools.query(sql);
			
			ItemValue = dataMap.get("ITEM_VALUE").toString();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return ItemValue;
	}
	
	public void SelectImportDetailData(Map dataMap) {
		
		List dataList = null;
		
		try{
			//Query
			String sql = "" +
			" SELECT EHF320200T0_02 as 'EHF320300T1_02', EHF320100T0_04 as 'EHF320300T1_03', EHF320100T0_05 as 'EHF320300T1_04', EHF320100T0_01 as 'EHF320300T1_05' " +
			"  FROM EHF320200T0 a " +
			      " LEFT OUTER JOIN ehf320100t0 b ON a.EHF320200T0_03 = b.EHF320100T0_01 " +//b.EHF320100T0_02 為菜單編號,01為菜單系統編號
			" WHERE 1 = 1 " ;		
									
			
			sql += "" +
			" AND a.EHF320200T0_01 like ? " +
			" AND a.EHF320200T0_04 like ? " +
			" ORDER BY EHF320200T0_02 ASC ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			if(this.base_tools.existString((String)dataMap.get("EHF320300T0_03"))){
				pstmt.setString(indexid, (String)dataMap.get("EHF320300T0_03"));
				indexid++;
			}	
			pstmt.setString(indexid, (String)dataMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
			
			
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		EHF320300M0F bean = null;
		Iterator it = dataList.iterator();
		Map tempMap = null;
		
		try{
		
			while(it.hasNext()){
			
				tempMap = (Map) it.next();
				
				tempMap.put("EHF320300T1_01", dataMap.get("EHF320300T0_01"));
				tempMap.put("USER_NAME", dataMap.get("USER_NAME"));
				tempMap.put("EHF320300T1_02",String.valueOf(tempMap.get("EHF320300T1_02")));
//				tempMap.put("EHF320300T1_04",String.valueOf(tempMap.get("EHF320300T1_04")).substring(1, 2));
				this.addEHF320300T1(tempMap);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public List getTodayOrder(Map dataMap) {
		// TODO Auto-generated method stub
		List todayOrderList = null;
				
		try{
			//Query
			String sql = "" +
			" SELECT a.EHF310100T1_01, a.EHF310100T1_03, b.EHF310100T0_03, " +
			"		 DATE_FORMAT(a.EHF310100T1_02, '%Y/%m/%d %H：%i：%s') AS EHF310100T1_02 " +						
			" FROM EHF310100T1 a " +
			" LEFT JOIN EHF310100T0 b ON a.EHF310100T1_01 = b.EHF310100T0_01 AND a.EHF310100T1_06 = b.EHF310100T0_34 " +
			" WHERE 1=1 " +
			"	AND EHF310100T1_06 = ? " ;//公司代碼
									
			if(this.base_tools.existString((String)dataMap.get("date"))){	//日期
				sql += " and EHF310100T1_02 = ?";
			}
			if(this.base_tools.existString((String)dataMap.get("type"))){	//餐別
				sql += " and EHF310100T1_03 = ?";
			}
			
			
			sql += "" +
			" ORDER BY EHF310100T1_01 ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			pstmt.setString(indexid, (String)dataMap.get("comp_id"));
			indexid++;
			
			if(this.base_tools.existString((String)dataMap.get("date"))){
				pstmt.setString(indexid, (String)dataMap.get("date"));
				indexid++;
			}
			
			if(this.base_tools.existString((String)dataMap.get("type"))){
				pstmt.setString(indexid, (String)dataMap.get("type"));
				indexid++;
			}
			
			todayOrderList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
			pstmt.close();
			
		}catch(Exception e){
			System.out.println("取得今日訂餐人員清單時錯誤"+e);
		}
		return todayOrderList;
	}

	public void getNotEat(Map returnMap) {
		// TODO Auto-generated method stub
				
		try{
			String notEat = "";
			//Query
			String sql = "" +
			"   SELECT a.EHF310200T1_03 " +						
			"     FROM EHF310200T1 a " +
			"LEFT JOIN EHF310200T0 b ON a.EHF310200T1_01 = b.EHF310200T0_01" +
			"    WHERE 1=1 " +
			"	   AND a.EHF310200T1_01 = '"+returnMap.get("EHF310100T1_01")+"' " +//系統編號
			"	   AND b.EHF310200T0_02 = '0' " ;//是否人工處理
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				notEat+=rs.getString("EHF310200T1_03")+",";
			}
			returnMap.put("notEat", notEat);
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得該人員之不吃食物時錯誤"+e);
		}
		
	}

	public void getNotDrink(Map returnMap) {
		// TODO Auto-generated method stub
				
		try{
			String notDrink = "";
			//Query
			String sql = "" +
			"   SELECT a.EHF310200T2_03 " +						
			"     FROM EHF310200T2 a " +
			"LEFT JOIN EHF310200T0 b ON a.EHF310200T2_01 = b.EHF310200T0_01" +
			"    WHERE 1=1 " +
			"	   AND a.EHF310200T2_01 = '"+returnMap.get("EHF310100T1_01")+"' " +//系統編號
			"	   AND b.EHF310200T0_02 = '0' " +//是否人工處理
			" ORDER BY a.EHF310200T2_03 " ;
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
//				notDrink+=rs.getString("EHF310200T2_03")+",";
				returnMap.put(rs.getString("EHF310200T2_03"), rs.getString("EHF310200T2_03"));
			}
//			returnMap.put("notDrink", notDrink);
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得該人員之不喝飲品時錯誤"+e);
		}
		
	}

	public void getVegetarian(Map dataMap, Map returnMap) {
		// TODO Auto-generated method stub
		
				
		try{
			//Query
			String sql = "" +
			" SELECT EHF310100T1_05 " +						
			"   FROM EHF310100T1 " +
			"  WHERE 1=1 " +
			"	 AND EHF310100T1_01 = '"+returnMap.get("EHF310100T1_01")+"' " +//系統編號
			"	 AND EHF310100T1_02 = '"+dataMap.get("date")+"' " +//日期
			"	 AND EHF310100T1_03 = '"+dataMap.get("type")+"' " +//餐別
			"	 AND EHF310100T1_06 = '"+dataMap.get("comp_id")+"' " ;//公司代碼
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				returnMap.put("vegetarian", rs.getBoolean("EHF310100T1_05"));
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得該人員是否吃素時錯誤"+e);
		}
		
	}

	public List getTodayMenu(Map dataMap) {
		// TODO Auto-generated method stub
		List todayOrderList = null;
				
		try{
			//Query
			String sql = "" +
			"    SELECT EHF320300T1_04,EHF320300T1_05 " +		
			
			"      FROM EHF320300T0 a " +
			" LEFT JOIN EHF320300T1 b ON a.EHF320300T0_01 = b.EHF320300T1_01" +
			
			"     WHERE 1=1 " +
			"	    AND EHF320300T0_04 = ? " ;//公司代碼
									
			if(this.base_tools.existString((String)dataMap.get("date"))){	//日期
				sql += " and EHF320300T0_02 = ?";
			}
			if(this.base_tools.existString((String)dataMap.get("type"))){	//餐別
				sql += " and EHF320300T1_03 = ?";
			}
			
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			pstmt.setString(indexid, (String)dataMap.get("comp_id"));
			indexid++;
			
			if(this.base_tools.existString((String)dataMap.get("date"))){
				pstmt.setString(indexid, (String)dataMap.get("date"));
				indexid++;
			}
			
			if(this.base_tools.existString((String)dataMap.get("type"))){
				pstmt.setString(indexid, (String)dataMap.get("type"));
				indexid++;
			}
			
			todayOrderList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
			pstmt.close();
			
		}catch(Exception e){
			System.out.println("取得今日餐點預排時錯誤"+e);
		}
		return todayOrderList;
	}

	public Boolean getfinalMenu(Map returnMap, String UID, Statement stmt, ResultSet rs) {
		// TODO Auto-generated method stub
				
		try{
			String sql = "";
			String[] notEat = returnMap.get("notEat").toString().split(",");
			String foodType = "";
			String foodDetail = "";
			boolean notEatM = false;
			boolean notEatV = false;
			String[] menuType = {"M","V"};
			
			Map tempMap_menuTypeSpare = null;
			//先比對主食材再比對副食材
			for(int i = 0 ; i < menuType.length ; i++){
				
				for(int j = 0 ; j < notEat.length ; j++){
					//解析不吃食物
					if(notEat[j].split("/").length==1){
						//代表只選類，這類的食物都不吃
						foodType = notEat[j].split("/")[0];
					}else if(notEat[j].split("/").length==2){
						//代表有選到特定的食物
						foodType = notEat[j].split("/")[0];
						foodDetail = notEat[j].split("/")[1];
					}
					
					//找食材中有無該產婦不吃食物
					//先比較主食材 主食材沒有不吃再比較副食材(若主副食材都有不吃則以主食材遞補優先)
					sql = "   SELECT EHF320100T3_04,EHF320100T3_05 " +						
					      "     FROM EHF320100T3  " +
						  "    WHERE 1=1 " +
						  //↓↓↓↓UID若是空字串代表是判斷遞補食材是否不吃， 菜單編碼不會從UID取得而是從MAP中取得
						  "	     AND EHF320100T3_01 = '"+(UID.equals("")?(String)returnMap.get("spareUID"):UID)+"' " +//菜單編號  
						  "	     AND EHF320100T3_03 = '"+menuType[i]+"' " +//主類別  M:主食材  V:副食材
						  "		 AND EHF320100T3_04 = '"+foodType+"' " ;//食物代碼(非明細)
					if(!"".equals(foodDetail)){
						//若食物名系代碼不為空值才加入條件
						sql+=" AND EHF320100T3_05 = '"+foodDetail+"' " ;//食物明細代碼
					}
					if("V".equals(menuType[i])){
						//若主類別為副食材須加入條件找有設定遞補副食材菜單的資料
						sql+=" AND EHF320100T3_06 = '1' " ;//是否遞補副食菜單
					}
					
//					System.out.println(sql);
					rs = stmt.executeQuery(sql);
					
					while(rs.next()){
						
						if("M".equals(menuType[i])){
							notEatM = true;
						}else if("V".equals(menuType[i])){
							notEatV = true;
						}
					}
				}
			}
			if(UID.equals("") && (notEatM || notEatV) ){
				//代表此次程式是判斷 "今日菜單的遞補食材" 是否不吃而不是判斷 "今日菜單" 是否不吃
				//並且主食材與副食材其中一個是該人員不吃的食物
				//因為是判斷遞補食材為不吃，所以不用在往下找遞補，只需回傳false讓程式往下找今日菜單的下一順位
				return false;
			}
			int count = 1;
			if(notEatM){
				//代表須找遞補主食
				//找到遞補主食材清單
				List MenuTypeSpare = this.getMenuTypeSpare("M",UID);
				Iterator it_menuTypeSpare = MenuTypeSpare.iterator();
				while(it_menuTypeSpare.hasNext()){
//					if(count<4){
						tempMap_menuTypeSpare = (Map) it_menuTypeSpare.next();
						//用遞迴在比較一次是否有不吃主食
						
						if(!"".equals(UID)){
							//存入遞補菜單，避免遞迴時無單號判斷是否不吃
							returnMap.put("spareUID",(String)tempMap_menuTypeSpare.get("EHF320100T4_03"));
						}
						boolean flag = this.getfinalMenu(returnMap,"",stmt,rs);
						if(flag){
							//代表已遞補到，結束迴圈
							return flag;
						}
						count++;
//					}else{
//						//已找完所有順位(遞補順位只有三個)，強制跳出，避免無線迴圈
//						break;
//					}
					
				}
			}else if(!notEatM && notEatV){
				//代表須找遞補副食
				//找到遞補副食材清單
				List MenuTypeSpare = this.getMenuTypeSpare("V",UID);
				Iterator it_menuTypeSpare = MenuTypeSpare.iterator();
				while(it_menuTypeSpare.hasNext()){
//					if(count<4){
						tempMap_menuTypeSpare = (Map) it_menuTypeSpare.next();
						//用遞迴在比較一次是否有不吃副食
						if(!"".equals(UID)){
							//存入遞補菜單，避免遞迴時無單號判斷是否不吃
							returnMap.put("spareUID",(String)tempMap_menuTypeSpare.get("EHF320100T5_03"));
						}
						boolean flag = this.getfinalMenu(returnMap,"",stmt,rs);
						if(flag){
							//代表已遞補到，結束迴圈
							return flag;
						}
						count++;
//					}else{
//						//已找完所有順位(遞補順位只有三個)，強制跳出，避免無線迴圈
//						break;
//					}
					
				}
			}else{
				//代表主副食材皆可吃
				//UID為空字串代表是在判斷遞補食材為該員可以吃，UID須從MAP取;反之若非空字串代表再判斷今日菜單為該員可以吃，UID從參數UID取
				returnMap.put("finalMenu", UID.equals("")?(String)returnMap.get("spareUID"):UID);
				return true;
			}
			
			
		}catch(Exception e){
			System.out.println("取得最終菜單時錯誤"+e);
		}
		return false;
	}

	private List getMenuTypeSpare(String MenuType, String UID) {
		// TODO Auto-generated method stub
		
		List MenuTypeSpare = null;
		
		String table = "";
		
		if(MenuType.equals("M")){
			table = "EHF320100T4";
		}else{
			table = "EHF320100T5";
		}
		try{
			String sql = "   SELECT "+table+"_02, "+table+"_03 " +
						 "     FROM "+table+" " +
						 "    WHERE "+table+"_01 = ? " +
						 " ORDER BY "+table+"_02 ";
			
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			pstmt.setString(indexid, UID);
			indexid++;
			
			
			MenuTypeSpare = this.base_tools.resultSetToList(pstmt.executeQuery());
			
			pstmt.close();
		}catch(Exception e){
			if(MenuType.equals("M")){
				System.out.println("取得遞補主食清單時錯誤"+e);
			}else{
				System.out.println("取得遞補副食清單時錯誤"+e);
			}
		}
		return MenuTypeSpare;
	}

	public String getTodayTea(Map dataMap) {
		// TODO Auto-generated method stub
		
		String teaOfToday = "";
		String dayOfWeek = "";
		//取得今日星期幾
		if("日".equals(dataMap.get("dayOfWeek"))){
			dayOfWeek = "7";
		}else if("一".equals(dataMap.get("dayOfWeek"))){
			dayOfWeek = "1";
		}else if("二".equals(dataMap.get("dayOfWeek"))){
			dayOfWeek = "2";
		}else if("三".equals(dataMap.get("dayOfWeek"))){
			dayOfWeek = "3";
		}else if("四".equals(dataMap.get("dayOfWeek"))){
			dayOfWeek = "4";
		}else if("五".equals(dataMap.get("dayOfWeek"))){
			dayOfWeek = "5";
		}else if("六".equals(dataMap.get("dayOfWeek"))){
			dayOfWeek = "6";
		}
		
		try{
			
			//Query
			String sql = "" +
			" SELECT EHF300400T0_03 " +						
			"   FROM EHF300400T0 " +
			"  WHERE 1=1 " +
			"	 AND EHF300400T0_02 = '"+dayOfWeek+"' " +//星期幾
			"	 AND EHF300400T0_05 = 1 " +//啟用
			"	 AND EHF300400T0_06 = '"+dataMap.get("comp_id")+"' " ;//公司代碼
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
//				returnMap.put("vegetarian", rs.getBoolean("EHF310100T1_05"));
				teaOfToday = rs.getString("EHF300400T0_03");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得當天茶飲時錯誤"+e);
		}
		return teaOfToday;
	}

	public void getLight(Map returnMap) {
		// TODO Auto-generated method stub
				
		try{
			String Light = "";
			//Query
			String sql = "" +
			"   SELECT a.EHF310200T5_03 " +						
			"     FROM EHF310200T5 a " +
			"LEFT JOIN EHF310200T0 b ON a.EHF310200T5_01 = b.EHF310200T0_01" +
			"    WHERE 1=1 " +
			"	   AND a.EHF310200T5_01 = '"+returnMap.get("EHF310100T1_01")+"' " +//系統編號
			"	   AND b.EHF310200T0_02 = '0' " ;//是否人工處理
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				Light+=rs.getString("a.EHF310200T5_03")+",";
			}
			returnMap.put("Light", Light);
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得該人員之清淡去油時錯誤"+e);
		}
		
	}

	public void getSauce(Map returnMap) {
		// TODO Auto-generated method stub
				
		try{
			String Sauce = "";
			//Query
			String sql = "" +
			"   SELECT a.EHF310200T4_03 " +						
			"     FROM EHF310200T4 a " +
			"LEFT JOIN EHF310200T0 b ON a.EHF310200T4_01 = b.EHF310200T0_01" +
			"    WHERE 1=1 " +
			"	   AND a.EHF310200T4_01 = '"+returnMap.get("EHF310100T1_01")+"' " +//系統編號
			"	   AND b.EHF310200T0_02 = '0' " ;//是否人工處理
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
//				Sauce+=rs.getString("a.EHF310200T4_03")+",";
				returnMap.put(rs.getString("a.EHF310200T4_03"), rs.getString("a.EHF310200T4_03"));
			}
//			returnMap.put("Sauce", Sauce);
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得該人員之調味時錯誤"+e);
		}
		
	}

	public void getMenuSauce(Map returnMap) {
		// TODO Auto-generated method stub
				
		try{
			String MenuSauce = "";
			//Query
			String sql = "" +
			"   SELECT EHF320100T1_03 " +						
			"     FROM EHF320100T1 " +
			"    WHERE 1=1 " +
			"	   AND EHF320100T1_01 = '"+returnMap.get("finalMenu")+"' " ;//最終菜單之菜單系統編號
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				MenuSauce+=rs.getString("EHF320100T1_03")+",";
			}
			returnMap.put("MenuSauce", MenuSauce);
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得該菜單之調味時錯誤"+e);
		}
		
	}

	public void getFinalMenuType(Map returnMap, Map dataMap) {
		// TODO Auto-generated method stub
				
		try{
			//Query
			String sql = "" +
			"   SELECT EHF320100T0_05 " +						
			"     FROM EHF320100T0 " +
			"    WHERE 1=1 " +
			"	   AND EHF320100T0_01 = '"+returnMap.get("finalMenu")+"' " +//最終菜單之菜單系統編號
			"	   AND EHF320100T0_08 = '"+dataMap.get("comp_id")+"' " ;//公司代碼
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				returnMap.put("FinalMenuType", rs.getString("EHF320100T0_05"));
			}
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得最終菜單之菜單類別時錯誤"+e);
		}
		
	}

	public Map getMenuName(String comp_id) {
		// TODO Auto-generated method stub
		Map MenuName = new HashMap();;
		try{
			String sql = " SELECT EHF320100T0_01, EHF320100T0_03 " +
						 "   FROM EHF320100T0 " +
						 "  WHERE EHF320100T0_08 = '"+comp_id+"' ";
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				MenuName.put(rs.getString("EHF320100T0_01"), rs.getString("EHF320100T0_03"));
			}
		}catch(Exception e){
			System.out.println("取得所有菜單名稱時錯誤"+e);
		}
		return MenuName;
	}

	public String getMore(String UID) {
		// TODO Auto-generated method stub

		String More = "";
		try{
			//Query
			String sql = "" +
			"   SELECT a.EHF310200T3_03 " +						
			"     FROM EHF310200T3 a " +
			"LEFT JOIN EHF310200T0 b ON a.EHF310200T3_01 = b.EHF310200T0_01" +
			"    WHERE 1=1 " +
			"	   AND a.EHF310200T3_01 = '"+UID+"' " +//系統編號
			"	   AND b.EHF310200T0_02 = '0' " ;//是否人工處理
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				More+=rs.getString("a.EHF310200T3_03")+",";
			}
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("取得該人員之加量需求時錯誤"+e);
		}
		return More;
	}

}
