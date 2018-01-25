package com.spon.mvc.models;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.ems.msg.EMS_MSGSYSTEM;

public class BaseFunction implements BaseSystem {
	
	
	//EMS系統訊息元件
	private EMS_MSGSYSTEM ems_msg_tools = EMS_MSGSYSTEM.getInstance();
	private Connection conn_msg;
	
	//建立資料庫工具
	private EMS_DB_CONTROLLER db_tools; 
	
	private String sta_comp_id;
	
	/**
	 * 建構子
	 */
	public BaseFunction(){
		
		try{
			//建立資料庫
			db_tools = new EMS_DB_CONTROLLER();
			//建立訊息資料庫連線
			this.conn_msg = this.db_tools.newConnection();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 建構子
	 * @param comp_id
	 */
	public BaseFunction( String comp_id ){
		
		try{
			//建立資料庫
			db_tools = new EMS_DB_CONTROLLER();
			
			//設定公司代碼
			this.sta_comp_id = comp_id;
			
			//建立訊息資料庫連線
			this.conn_msg = this.db_tools.newConnection();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void setSta_comp_id(String staCompId) {
		sta_comp_id = staCompId;
	}
	public String getSta_comp_id() {
		return sta_comp_id;
	}
	
	/**
	 * 設定資料庫連線檔案名稱
	 * @param databaseResourceConfigFilename
	 */
	public void setDatabase_resource_config_filename(
			String databaseResourceConfigFilename) {
		if(this.db_tools != null){
			this.db_tools.setDatabase_resource_config_filename(databaseResourceConfigFilename);
		}
	}
	
	/**
	 * 設定資料庫連線名稱
	 * @param databaseResourceName
	 */
	public void setDatabase_resource_name(String databaseResourceName) {
		if(this.db_tools != null){
			this.db_tools.setDatabase_resource_name(databaseResourceName);
		}
	}

	/**
	 * 取得資料庫連線
	 * @return
	 */
	public Connection getConn(){
		return this.db_tools.getConn();
	}
	
	public void commit(){
		try{
			this.db_tools.getConn().commit();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void rollback(){
		try{
			this.db_tools.getConn().rollback();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EMS_MSGSYSTEM getEms_msg_tools() {
		return ems_msg_tools;
	}
	
	public Connection getConn_msg() {
		return conn_msg;
	}

	public EMS_DB_CONTROLLER getDb_tools() {
		return db_tools;
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {
		
	}
	
	/**
	 * 例外記錄
	 * @param e
	 */
	public final void handleException( Exception e ){
		
		try{
			System.out.println("JAVA例外錯誤訊息："+e+",  "+e.getMessage());
			
			//寫入錯誤記錄LOG - In DataBase
			this.ems_msg_tools.writeMSGForm( this.conn_msg, "ERR_SQL_0001", "SQL_EXCEPTION",
					 						 e.getMessage(),
					 						 "BaseFunction", EMS_MSGSYSTEM.Serious, EMS_MSGSYSTEM.E_SYSTEM_MANGER, this.sta_comp_id);
			
			
			
		}catch(Exception e_log ){
			System.out.println("執行例外處理記錄時，發生錯誤!!"+e_log.getMessage());
			e_log.printStackTrace();
		}
		
	}
	
	/**
	 * Result to Map
	 * @param rs
	 * @return
	 */
	public Map resultSetToMap( ResultSet rs ){
		
		Map resultMap = new HashMap();
		
		try{
			//處理 ResultSet
			if(rs.next()){
				//取得當前 ResultSetMetaData
				ResultSetMetaData rsm = rs.getMetaData();
				//走訪Data Column
				for(int i = 1; i <= rsm.getColumnCount(); i++ ){
					//取得Column型態
					resultMap.put(rsm.getColumnLabel(i)+"_SQL_TYPE", rsm.getColumnType(i));
					//取得Column型態名稱
					resultMap.put(rsm.getColumnLabel(i)+"_SQL_TYPE_NAME", rsm.getColumnTypeName(i));
					//取得Column資料
					resultMap.put(rsm.getColumnLabel(i), rs.getObject(i));
				}
			}
			
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	/**
	 * Result to List
	 * @param rs
	 * @return
	 */
	public List resultSetToList( ResultSet rs ){
		
		List resultList = new ArrayList();
		Map resultMap = null;
		
		try{
			//走訪取得 ResultSet 資料
			while(rs.next()){
				rs.previous();
				resultMap = this.resultSetToMap(rs);
				resultList.add(resultMap);
			}
			
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	/**
	 * 查詢
	 * @param sql
	 * @return
	 */
	public Map query( String sql ){
		
		Map dataMap = new HashMap();
		
		try{
			//執行SQL
			Statement stmt = this.db_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			//Result to Map
			dataMap = this.resultSetToMap(rs);
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	/**
	 * 查詢 List
	 * @param sql
	 * @return
	 */
	public List queryList( String sql ){
		
		List resultList = new ArrayList();
		
		try{
			//執行SQL
			Statement stmt = this.db_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			//Result to List
			resultList = this.resultSetToList(rs);
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	/**
	 * 執行查詢SQL
	 * @param sql
	 * @return
	 */
	public List executeQuery( String sql ){
		
		List resultList = new ArrayList();
		
		try{
			//執行SQL語句
			Statement stmt = this.db_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			resultList = this.resultSetToList(rs);
			rs.close();
			stmt.close();
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return resultList;
	}
	
	/**
	 * 執行 Batch SQL
	 * @param sql_list
	 * @return
	 */
	public int[] executeBatchSQL( List sql_list ){
		
		int[] rows = new int[1];
		
		try{
			//執行SQL語句
			String sql = "";
			Statement stmt = this.db_tools.getConn().createStatement();
			
			//走訪取出 Batch SQL
			Iterator it = sql_list.iterator();
			
			while(it.hasNext()){
				sql = (String) it.next();
				stmt.addBatch(sql);
			}
			
			//執行 Batch SQL
			rows = stmt.executeBatch();
			stmt.close();
			
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return rows;
	}
	
	/**
	 * 執行 Batch SQL
	 * @param conn
	 * @param sql_list
	 * @return
	 */
	public int[] executeBatchSQL(Connection conn, List sql_list ){
		
		int[] rows = new int[1];
		
		try{
			//執行SQL語句
			String sql = "";
			Statement stmt = conn.createStatement();
			
			//走訪取出 Batch SQL
			Iterator it = sql_list.iterator();
			
			while(it.hasNext()){
				sql = (String) it.next();
				stmt.addBatch(sql);
			}
			
			//執行 Batch SQL
			rows = stmt.executeBatch();
			stmt.close();
			
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return rows;
	}
	
	/**
	 * 執行SQL
	 * @param sql
	 * @return
	 */
	public int executeSQL( String sql ){
		
		int rows = 0;
		
		try{
			//執行SQL語句
			Statement stmt = this.db_tools.getConn().createStatement();
			rows = stmt.executeUpdate(sql);
			stmt.close();
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return rows;
	}
	
	/**
	 * 執行 Insert SQL
	 * @param sql
	 * @return
	 */
	public int add( String sql ){
		
		int rows = 0;
		
		try{
			//執行SQL新增語句
			rows = this.executeSQL(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rows;
	}
	
	/**
	 * 寫入新增訊息
	 * @param Class_Method_Name
	 * @param msg
	 * @param user_name
	 * @param comp_id
	 */
	public void writeADDMSG( String Class_Method_Name, String msg, String user_name, String comp_id ){
		
		try{
			//記錄新增訊息
			this.getEms_msg_tools().writeMSGForm( this.conn_msg,
										          "SQL_MSG_0001", "SQL_INSERT", Class_Method_Name+" -> Log Insert SQL statement , " +
										          msg,
											      this.existString(user_name)==true?user_name:"BaseFunction",
											      EMS_MSGSYSTEM.General, EMS_MSGSYSTEM.NONE,
											      this.existString(comp_id)==true?comp_id:"BaseFunction");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 寫入新增錯誤訊息
	 * @param Class_Method_Name
	 * @param msg
	 * @param user_name
	 * @param comp_id
	 */
	public void writeADDERRMSG( String Class_Method_Name, String msg, String user_name, String comp_id ){
		
		try{
			//記錄新增錯誤訊息
			this.getEms_msg_tools().writeMSGForm( this.conn_msg,
											      "SQL_ERR_0001", "SQL_INSERT_ERR", Class_Method_Name+" -> Insert SQL statement Error!! , " +
											      msg,
											      this.existString(user_name)==true?user_name:"BaseFunction",
											      EMS_MSGSYSTEM.General, EMS_MSGSYSTEM.NONE,
											      this.existString(comp_id)==true?comp_id:"BaseFunction");
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 執行 Update SQL
	 * @param sql
	 * @return
	 */
	public int update( String sql ){
		
		int rows = 0;
		
		try{
			//執行SQL更新語句
			rows = this.executeSQL(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rows;
		
	}
	
	/**
	 * 寫入更新訊息
	 * @param Class_Method_Name
	 * @param msg
	 * @param user_name
	 * @param comp_id
	 */
	public void writeUPDATEMSG( String Class_Method_Name, String msg, String user_name, String comp_id ){
		
		try{
			//記錄更新訊息
			this.getEms_msg_tools().writeMSGForm( this.conn_msg, 
											      "SQL_MSG_0001", "SQL_UPDATE", Class_Method_Name+" -> Log Update SQL statement , " +
											      msg,
											      this.existString(user_name)==true?user_name:"BaseFunction",
												  EMS_MSGSYSTEM.General, EMS_MSGSYSTEM.NONE,
												  this.existString(comp_id)==true?comp_id:"BaseFunction");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 寫入更新錯誤訊息
	 * @param Class_Method_Name
	 * @param msg
	 * @param user_name
	 * @param comp_id
	 */
	public void writeUPDATEERRMSG( String Class_Method_Name, String msg, String user_name, String comp_id ){
		
		try{
			//記錄更新訊息
			this.getEms_msg_tools().writeMSGForm( this.conn_msg, 
											      "SQL_ERR_0001", "SQL_UPDATE_ERR", Class_Method_Name+" -> Update SQL statement Error!! , " +
											      msg,
											      this.existString(user_name)==true?user_name:"BaseFunction",
												  EMS_MSGSYSTEM.General, EMS_MSGSYSTEM.NONE,
												  this.existString(comp_id)==true?comp_id:"BaseFunction");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 執行 Delete SQL
	 * @param sql
	 * @return
	 */
	public int delete( String sql ){
		
		int rows = 0;
		
		try{
			//執行SQL刪除語句
			rows = this.executeSQL(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return rows;
		
	}
	
	/**
	 * 寫入刪除訊息
	 * @param Class_Method_Name
	 * @param msg
	 * @param user_name
	 * @param comp_id
	 */
	public void writeDELETEMSG( String Class_Method_Name, String msg, String user_name, String comp_id ){
		
		try{
			//記錄刪除訊息
			this.getEms_msg_tools().writeMSGForm( this.conn_msg,
												  "SQL_MSG_0001", "SQL_DELETE", Class_Method_Name+" -> Log Delete SQL statement , " +
										          msg,
											      this.existString(user_name)==true?user_name:"BaseFunction",
											      EMS_MSGSYSTEM.General, EMS_MSGSYSTEM.NONE,
											      this.existString(comp_id)==true?comp_id:"BaseFunction");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 寫入刪除錯誤訊息
	 * @param Class_Method_Name
	 * @param msg
	 * @param user_name
	 * @param comp_id
	 */
	public void writeDELETEERRMSG( String Class_Method_Name, String msg, String user_name, String comp_id ){
		
		try{
			//記錄刪除訊息
			this.getEms_msg_tools().writeMSGForm( this.conn_msg,
												  "SQL_ERR_0001", "SQL_DELETE_ERR", "EEF020102().delData() -> Delete SQL statement Error!! , " +
										          msg,
											      this.existString(user_name)==true?user_name:"BaseFunction",
											      EMS_MSGSYSTEM.General, EMS_MSGSYSTEM.NONE,
											      this.existString(comp_id)==true?comp_id:"BaseFunction");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 判斷是否增加字串SQL條件
	 * @param sql
	 * @param add_sql
	 * @param value
	 */
	public void addStringSQL( String sql, String add_sql, String value ){
		
		try{
			if(this.existString(value)){
				sql += add_sql.replace("?", "'"+value+"'");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 判斷字串是否可使用
	 * @param str
	 * @return
	 */
	public boolean existString( String str ){
		
		boolean chk_flag = false;
		
		try{
			if(!"".equals(str) && str != null){
				chk_flag = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 判斷字串是否可以使用
	 * @param str
	 * @return
	 */
	public String usefulString( String str ){
		
		String return_str = "";
		
		try{
			if(this.existString(str)){
				return_str = str;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_str;
	}
	
	/**
	 * 判斷字串是否可以使用, 若不可使用返回default_value
	 * @param str
	 * @param default_value
	 * @return
	 */
	public String usefulString( String str, String default_value ){
		
		String return_str = "";
		
		try{
			if(this.existString(str)){
				return_str = str;
			}else{
				return_str = default_value;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_str;
	}
	
	/**
	 * 將List轉換為 in ( '', '', '') 的 SQL 格式
	 * @param dataList
	 * @param key
	 * @return
	 */
	public String listToINSQL( List dataList, String key ){
		
		String return_sql = "";
		
		try{
			int TempCount = 0;
			Iterator it = dataList.iterator();
			Map tempMap = null;
			return_sql = " ( ";
			
			if(!it.hasNext()){
				return_sql += " '' ";
			}
			
			while(it.hasNext()){
				tempMap = (Map) it.next();
				if(TempCount == 0){
					return_sql += " '"+tempMap.get(key)+"' ";
				}else{
					return_sql += " ,'"+tempMap.get(key)+"' ";
				}
				TempCount++;
			}
			
			return_sql += " ) ";
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_sql;
	}
	
	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		
		try{
			chk_flag = this.db_tools.isClosed();
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 取得目前TABLE中最大的順序號碼
	 * @param sn_field
	 * @param table_name
	 * @param key_sql
	 * @return
	 */
	public int getMaxSN( String sn_field, String table_name, String key_sql ){
		
		//順序號碼
		int max_sn = 1;
		
		try{	
			//取得目前最大的順序號碼
			String sql = "" +
			" SELECT "+sn_field+" AS MAX_SN " +
			" FROM "+table_name+" " +
			" WHERE 1=1 " +
			key_sql +  //KEY SQL 設定
			" ORDER BY "+sn_field+" DESC LIMIT 1 ";  
			Map snMap = this.query(sql);
			if(!snMap.isEmpty()){
				max_sn = (Integer)snMap.get("MAX_SN");
				max_sn++;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return max_sn;
	}
	
	/**
	 * 重排明細順序號碼
	 * @param key
	 * @param sn_field
	 * @param table_name
	 * @param key_sql
	 */
	public void reDoSN( String[] key, String sn_field, String table_name, String key_sql ){
		
		try{
			StringBuffer sql_for_key_select = new StringBuffer();
			StringBuffer sql_for_key_update = new StringBuffer();
			//處理Table Key Set
			for(int i=0; i < key.length; i++){
				//sql_for_key_select
				sql_for_key_select.append(" CAST(");
				sql_for_key_select.append(key[i]);
				sql_for_key_select.append(" AS CHAR(30)) AS "+key[i]+", ");
			}
			
			//重新排明細的 - "順序號碼"
			String sql = "" +
			" SELECT " +
			" "+sql_for_key_select.toString()+" " +  //組成 KEY 
			sn_field + " AS SN " +
			" FROM "+table_name+" " +
			" WHERE 1=1 " +
			key_sql +  //KEY SQL 設定
			" ORDER BY "+sn_field+" ";
			//取得明細清單
			List data_list = this.queryList(sql);
			Iterator it = data_list.iterator();
			Map tempMap  = null;
			int snCount = 0;
			List sql_list = new ArrayList();
			//針對每一筆明細清單做順序號碼的處裡
			while(it.hasNext()){
				sql_for_key_update = new StringBuffer();
				tempMap = (Map) it.next();
				snCount++;
				
				//處理Table Key Set
				for(int j=0; j < key.length; j++){
					//sql_for_key_update
					sql_for_key_update.append(" AND ");
					sql_for_key_update.append(key[j]);
					sql_for_key_update.append(" = '");
					sql_for_key_update.append((String)tempMap.get(key[j]));
					sql_for_key_update.append("' ");
				}
				
				sql = "" +
				" UPDATE "+table_name+" SET " +
				" "+sn_field+" = "+snCount+" " +
				" WHERE 1=1 " +
				//" AND ESF010300T1_01 = "+(Integer)tempMap.get("ESF010300T1_01")+" " +  //委外加工單資料序號
				" "+sql_for_key_update.toString()+" " +  //組成 KEY
				" AND "+sn_field+" = '"+(Integer)tempMap.get("SN")+"' ";  //序號
				sql_list.add(sql);
			}
			this.executeBatchSQL(sql_list);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		try{
			this.db_tools.close();
		}catch(Exception e){
			//處理例外訊息記錄
			this.handleException(e);
			e.printStackTrace();
		}
	}
	
	
}
