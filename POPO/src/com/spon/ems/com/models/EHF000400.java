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
import com.spon.utils.util.BA_TOOLS;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/5 下午3:29:29
 */
public class EHF000400 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	private BA_TOOLS tools;
	
	public EHF000400(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
			tools = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF000400( String comp_id ){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction(comp_id);
			tools = BA_TOOLS.getInstance();
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
			" SELECT EHF000400T0_01 ,EHF000400T0_02 ,EHF000400T0_03 ,EHF000400T0_04 ,EHF000400T0_05 ,EHF000400T0_06 ,EHF000400T0_07 " +
			" ,EHF000400T0_08 ,EHF000400T0_09 ,EHF000400T0_10 ,EHF000400T0_18 " +
			" FROM EHF000400T0 " +
			" WHERE 1=1 " ;
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF000400T0_03"))){
				sql += " AND EHF000400T0_03 LIKE ? ";//班別代號
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF000400T0_04"))){
				sql += " AND EHF000400T0_04 LIKE ? ";//班別
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF000400T0_09"))){
				sql += " AND EHF000400T0_09 = ? ";//組織預設
			}
			
			sql += " AND EHF000400T0_11 = ? " +
				   " ORDER BY EHF000400T0_02 , EHF000400T0_03 ";//LIMIT 30 "; 
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF000400T0_03"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF000400T0_03")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF000400T0_04"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF000400T0_04")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF000400T0_09"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF000400T0_09"));
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
			" SELECT *, DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE FROM EHF000400T0 " +
			" WHERE 1=1 " +
			" AND EHF000400T0_01 = '"+(String)queryCondMap.get("EHF000400T0_01")+"' " +	//班別序號
			" AND EHF000400T0_11 = '"+(String)queryCondMap.get("COMP_ID")+"' " ;	//公司代碼
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
			" INSERT INTO ehf000400t0 ( EHF000400T0_02 ,EHF000400T0_03 ,EHF000400T0_04 ,EHF000400T0_05 ,EHF000400T0_06 " +
			" ,EHF000400T0_NFLG " +
			" ,EHF000400T0_07 ,EHF000400T0_08 ,EHF000400T0_05_FLG ,EHF000400T0_05_VAL ,EHF000400T0_06_FLG ,EHF000400T0_06_VAL " +
			" ,EHF000400T0_09 ,EHF000400T0_10 ,EHF000400T0_11 ,EHF000400T0_17 ,EHF000400T0_18 ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) " ;

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid,"");  //部門組織(代號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_03")); //班別代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_04")); //班別
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_05_HH")+(String)dataMap.get("EHF000400T0_05_MM"));  //上班起間
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_06_HH")+(String)dataMap.get("EHF000400T0_06_MM"));  //下班起間
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //是否記錄中午打卡
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_07_HH")+(String)dataMap.get("EHF000400T0_07_MM"));  //午休時間(起)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_08_HH")+(String)dataMap.get("EHF000400T0_08_MM"));  //午休時間(迄)
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //上班彈性開關
			indexid++;
			p6stmt.setInt(indexid, 0);  //上班彈性時間
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //下班彈性開關
			indexid++;
			p6stmt.setInt(indexid, 0);  //下班彈性時間
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_09"));  //是否系統預設
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_10")==null?"":(String)dataMap.get("EHF000400T0_10"));  //備註
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++; 
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_17")==null?"":(String)dataMap.get("EHF000400T0_17"));  //休假方式
			indexid++; 
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_18"));  //是否時薪班別
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //使用員工姓名
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得 資料表序號
			int EHF000400T0_01 = this.base_tools.getDb_tools().getLastInsertId(this.base_tools.getConn());
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫 職務系統代碼
			dataMap.put("KEY_ID", EHF000400T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF000400().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF000400.addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF000400T0
			sql = "" +
			" DELETE FROM EHF000400T0 " +
			" WHERE 1=1 " +
			" AND EHF000400T0_01 = '"+(String)dataMap.get("EHF000400T0_01")+"' " +
			" AND EHF000400T0_11 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			this.base_tools.writeDELETEMSG("EHF000400().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF000400().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE ehf000400t0 SET  " + 
			" EHF000400T0_03=? ,EHF000400T0_04=? ,EHF000400T0_05=? ,EHF000400T0_06=? ,EHF000400T0_NFLG=? " +
			" ,EHF000400T0_07=? ,EHF000400T0_08=? ,EHF000400T0_05_FLG=? ,EHF000400T0_05_VAL=? ,EHF000400T0_06_FLG=? ,EHF000400T0_06_VAL=? " +
			" ,EHF000400T0_09=? ,EHF000400T0_10=? ,EHF000400T0_14=? ,EHF000400T0_15=? ,EHF000400T0_16=? ,EHF000400T0_17=? ,EHF000400T0_18=? " +
			" ,USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
			" WHERE EHF000400T0_01 = ? " +
			" AND EHF000400T0_11 = ? " ;

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_03")); //班別代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_04")); //班別
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_05_HH")+(String)dataMap.get("EHF000400T0_05_MM"));  //上班時間
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_06_HH")+(String)dataMap.get("EHF000400T0_06_MM"));  //下班時間
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)dataMap.get("EHF000400T0_NFLG")));  //是否記錄中午打卡
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_07_HH")+(String)dataMap.get("EHF000400T0_07_MM"));  //午休時間(起)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_08_HH")+(String)dataMap.get("EHF000400T0_08_MM"));  //午休時間(迄)
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //上班彈性開關
			indexid++;
			p6stmt.setInt(indexid, 0);  //上班彈性時間
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //下班彈性開關
			indexid++;
			p6stmt.setInt(indexid, 0);  //下班彈性時間
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_09"));  //是否系統預設
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_10"));  //備註
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //是否可延後下班
			indexid++;
			p6stmt.setString(indexid, "0");  //延後下班時數
			indexid++;
			p6stmt.setFloat(indexid, 0);  //延後下班加成率
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_17")==null?"":(String)dataMap.get("EHF000400T0_17"));  //休假方式
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_18"));  //是否時薪班別
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000400T0_01"));  //職務系統代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
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
			this.base_tools.writeUPDATEMSG("EHF000400().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF000400().saveData()", 
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
	
	public boolean selectEHF010100T8(String EHF000400T0_01, String EHF000400T0_03,
			String comp_id) {
		// TODO Auto-generated method stub
		
		String sql ="";
		Map dataMap = null;
		boolean chkFlag = false;
		
		try{
			sql = " SELECT EHF010100T8_04 FROM EHF010100T8 " +
				  " WHERE EHF010100T8_06 = '"+comp_id+"' " +
				  " AND EHF010100T8_04 = '"+EHF000400T0_01+"' ";
			
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
