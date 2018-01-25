package com.spon.ems.popo.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.mysql.jdbc.Connection;
import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.util.BA_TOOLS;

public class EHF300400 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF300400(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF300400( String comp_id ){
		
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
			" SELECT EHF300400T0_01, EHF300400T0_02, EHF300400T0_03, EHF300400T0_04, " +			
			" EHF300400T0_05, EHF300400T0_06 " +			
			" FROM EHF300400T0 " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF300400T0_02"))){	//星期
				sql += " and EHF300400T0_02 = ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF300400T0_04"))){	//茶飲名稱
				sql += " and EHF300400T0_04 like ?";
			}
			
			sql += "" +
			" AND EHF300400T0_06 = ? " +  //公司代碼
			" ORDER BY EHF300400T0_02 ASC " ;

			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF300400T0_02"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF300400T0_02"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF300400T0_04"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF300400T0_04")+"%");
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
			" SELECT *, DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE " +
			" FROM EHF300400T0 " +
			" WHERE 1=1 " +
			" AND EHF300400T0_01 = '"+(String)queryCondMap.get("EHF300400T0_01")+"' " +  //系統編號
			" AND EHF300400T0_06 = '"+(String)queryCondMap.get("COMP_ID")+"' ";	//公司代碼
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
			" INSERT INTO EHF300400T0 " +
			" (EHF300400T0_01,EHF300400T0_02, EHF300400T0_03, EHF300400T0_04, EHF300400T0_05, " +
			" EHF300400T0_06, " +			
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( ?,?, ?, ?, ?, " +
			" ?, " +			
			" ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF300400T0_01"));  //系統編號
			indexid++;
			p6stmt.setInt(indexid, Integer.valueOf((String)dataMap.get("EHF300400T0_02")).intValue());  //星期幾
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300400T0_03"));  //茶飲代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300400T0_04"));  //茶飲名稱
			indexid++;
			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF300400T0_05"));  //是否啟用
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
			
			//系統編號
			String EHF300400T0_01 = (String)dataMap.get("EHF300400T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫系統編號
			dataMap.put("KEY_ID", EHF300400T0_01);
	
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF300400().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF300400.addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF300400T0
			sql = "" +
			" DELETE FROM EHF300400T0 " +
			" WHERE 1=1 " +
			" AND EHF300400T0_01 = '"+(String)dataMap.get("EHF300400T0_01")+"' " +
			" AND EHF300400T0_06 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			this.base_tools.writeDELETEMSG("EHF300400().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF300400().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE EHF300400T0 SET " +
			" EHF300400T0_02=?, EHF300400T0_03=?, EHF300400T0_04=?, EHF300400T0_05=?, " +			
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF300400T0_06=? " +
			" AND EHF300400T0_01=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;

			p6stmt.setInt(indexid, Integer.valueOf((String)dataMap.get("EHF300400T0_02")).intValue());  //星期幾
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300400T0_03"));//茶飲代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300400T0_04"));//茶飲名稱
			indexid++;
			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF300400T0_05"));  //是否啟用
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF300400T0_01"));  //系統編號
			indexid++;



			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();

			//執行
			p6stmt.executeUpdate();
			
			//系統編號
			String EHF300400T0_01 = (String)dataMap.get("EHF300400T0_01");
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫系統編號
			dataMap.put("KEY_ID", EHF300400T0_01);
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF300400().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF300400().saveData()", 
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
	 * 檢查茶飲名稱是否使用中
	 * @param RouteNum
	 * @param HR_JobName
	 * @param EHF300400T0_06
	 * @return
	 */
	public boolean delCheck(String form_id, String enabled_flag,
			String comp_id) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = " SELECT EHF300400T0_01 FROM EHF300400T0 " +
				  " WHERE EHF300400T0_01 = '"+form_id+"' " +
				  " AND EHF300400T0_05 = '"+enabled_flag+"' ";
			
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
	
	/**
	 * 
	 * @param week_id
	 * @param tea_id
	 * @param enabled_flag
	 * @param comp_id
	 * @return
	 */
	public boolean isRepeat(String week_id, String tea_id, String enabled_flag, String form_id, String comp_id) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chk_flag = false;
		
		try{
			sql = " SELECT EHF300400T0_01, EHF300400T0_02, EHF300400T0_03 FROM EHF300400T0 " +
				  " WHERE " +
				  " EHF300400T0_06 = '"+comp_id+"'";
				  if(!"".equals(form_id)){
					  sql += " AND EHF300400T0_01 <> '"+form_id+"' " ;
				  }
				  if(!"".equals(week_id)){
					  sql += " AND EHF300400T0_02 = '"+week_id+"' " ;
				  }
				  if(!"".equals(tea_id)){
					  sql += " AND EHF300400T0_03 = '"+tea_id+"' ";
				  }
			 	  if(!"".equals(enabled_flag)){
			 		  sql += " AND EHF300400T0_05 = '"+enabled_flag+"' ";
			 	  }
			
			dataMap = this.base_tools.query(sql);
			
			if(!dataMap.isEmpty()){
				chk_flag = true;
			}
			
		}catch(Exception e){			
			e.printStackTrace();
		}
		return chk_flag;
	}

	

}
