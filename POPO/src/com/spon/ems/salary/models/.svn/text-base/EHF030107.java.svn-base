package com.spon.ems.salary.models;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.util.BA_TOOLS;

/**
 * 加班費規則設定 Business Model 元件
 * @author Joe
 *
 */
public class EHF030107 implements QueryFunction,EditFunction,BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF030107(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF030107( String comp_id ){
		
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
			" CAST(EHF030107T0_01 AS CHAR) AS EHF030107T0_01, " +
			" EHF030107T0_02, EHF030107T0_03, " +
			" CASE EHF030107T0_09 " +
			" WHEN true THEN '是' " +
			" ELSE '否' " +
			" END AS EHF030107T0_09 " +
			" FROM EHF030107T0 " +
			" WHERE 1=1 ";
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF030107T0_02"))){
				sql += " AND EHF030107T0_02 LIKE ? ";  //加班費規則代碼
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF030107T0_03"))){
				sql += " AND EHF030107T0_03 LIKE ? ";  //加班費規則名稱
			}
			
			sql += " AND EHF030107T0_10 = ? ";  //公司代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF030107T0_02"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF030107T0_02")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF030107T0_03"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF030107T0_03")+"%");
				indexid++;
			}
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			//dataList = this.base_tools.queryList(sql);
			
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
			" SELECT " +
			" EHF030107T0_01, " +
			" EHF030107T0_02, EHF030107T0_03, " +
			" EHF030107T0_04, EHF030107T0_05, " +
			" EHF030107T0_06, EHF030107T0_07, " +
			" EHF030107T0_08, " +
			" CASE EHF030107T0_09 " +
			" WHEN true THEN '1' " +
			" ELSE '0' " +
			" END AS EHF030107T0_09, " +
			" EHF030107T0_10, " +
			" EHF030107T0_11, " +
			" USER_CREATE, USER_UPDATE, " +
			" VERSION, " +
			" DATE_FORMAT(DATE_CREATE, '%Y/%m/%d %H:%i:%s') AS DATE_CREATE, " +
			" DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H:%i:%s') AS DATE_UPDATE " +
			" FROM EHF030107T0 " +
			" WHERE 1=1 " +
			" AND EHF030107T0_01 = '"+(String)queryCondMap.get("EHF030107T0_01")+"' " +
			" AND EHF030107T0_10 = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
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
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		String show_sql = "";
		
		try{
			//Add
			sql = "" +
			" INSERT INTO EHF030107T0 " +
			" ( " +
			" EHF030107T0_02, EHF030107T0_03, " +
			" EHF030107T0_04, EHF030107T0_05, " +
			" EHF030107T0_06, EHF030107T0_07, " +
			" EHF030107T0_08, " +
			" EHF030107T0_09, " +
			" EHF030107T0_10, " +
			" EHF030107T0_11, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, " +
			" ?, ?, " +
			" ?, ?, " +
			" ?, " +
			" ?, " +
			" ?, " +
			" ?, " +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF030107T0_02"));  //加班費規則代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030107T0_03"));  //加班費規則名稱
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_04"), "0"));  //加班(時數一)
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_05"), "0"));  //加班(加成率一)
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_06"), "0"));  //加班(時數二)
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_07"), "0"));  //加班(加成率二)
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_08"), "0"));  //國定假日加班加成率
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)dataMap.get("EHF030107T0_09"))); //是否啟用
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_11"), "0"));  //例假日加班加成率
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
			String EHF030107T0_01 = String.valueOf(this.base_tools.getDb_tools().getLastInsertId(this.base_tools.getConn()));
			
			//回寫 加班計費規則序號
			dataMap.put("KEY_ID", EHF030107T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF030107().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF030107.addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF030107T0
			sql = "" +
			" DELETE FROM EHF030107T0 " +
			" WHERE 1=1 " +
			" AND EHF030107T0_01 = '"+(String)dataMap.get("EHF030107T0_01")+"' " +  //加班費規則序號
			" AND EHF030107T0_10 = '"+(String)dataMap.get("COMP_ID")+"' ";  //公司代碼
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
			this.base_tools.writeDELETEMSG("EHF030107().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF030107().delData()", sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF030107T0 SET " +
			" EHF030107T0_03 = ?, " +
			" EHF030107T0_04 = ?, EHF030107T0_05 = ?, " +
			" EHF030107T0_06 = ?, EHF030107T0_07 = ?, " +
			" EHF030107T0_08 = ?, " +
			" EHF030107T0_09 = ?, " +
			" EHF030107T0_11 = ?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030107T0_01 = ? " +  //加班費規則序號
			" AND EHF030107T0_10 = ? ";  //公司代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
										 pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030107T0_03"));  //加班費規則名稱
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_04"), "0"));  //加班(時數一)
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_05"), "0"));  //加班(加成率一)
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_06"), "0"));  //加班(時數二)
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_07"), "0"));  //加班(加成率二)
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_08"), "0"));  //國定假日加班加成率
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)dataMap.get("EHF030107T0_09")));  //是否啟用
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030107T0_11"), "0"));  //例假日加班加成率
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030107T0_01"));  //加班費規則序號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
					
			//更新資料庫
			this.base_tools.commit();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF030107().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF030107().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 檢查加班規則代碼是否有重複
	 * @param overtime_rule_code
	 * @param comp_id
	 * @return
	 */
	public Map checkDuplicateOvertimeRule(String overtime_rule_code, String comp_id){
		
		Map return_map = new HashMap();
		boolean chk_flag = false;
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF030107T0_01, " +
			" EHF030107T0_02, EHF030107T0_03, " +
			" EHF030107T0_09 " +
			" FROM EHF030107T0 " +
			" WHERE 1=1 " +
			" AND EHF030107T0_02 = '"+overtime_rule_code+"' " +  //加班規則代碼
			" AND EHF030107T0_10 = '"+comp_id+"' ";  //公司代碼
			
			
			//執行SQL
			Map dataMap = this.base_tools.query(sql);
			
			if(!dataMap.isEmpty()){
				//有找到資料, 表示加班費規則代碼重複
				chk_flag = true;
			}
			
			//建立 RETURN MAP
			return_map.put("CHK_FLAG", chk_flag);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 檢查加班費規則是否有被員工薪資主檔使用
	 * @param overtime_rule_code
	 * @param comp_id
	 * @return
	 */
	public Map checkOvertimeRuleUseByEmployee(String overtime_rule_code, String comp_id){
		
		Map return_map = new HashMap();
		boolean chk_flag = false;
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF030200T0_01, " +
			" EHF030200T0_02, " +
			" EHF030200T0_08 " +
			//" EHF030200T0_17 " +//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
			" FROM EHF030200T0 " +
			" WHERE 1=1 " +
			" AND EHF030200T0_18 = '"+overtime_rule_code+"' " +  //加班規則代碼
			" AND EHF030200T0_13 = '"+comp_id+"' ";  //公司代碼
			
			
			//執行SQL
			Map dataMap = this.base_tools.query(sql);
			
			if(!dataMap.isEmpty()){
				//有找到資料, 表示加班費規則代碼重複
				chk_flag = true;
			}
			
			//建立 RETURN MAP
			return_map.put("CHK_FLAG", chk_flag);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
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
