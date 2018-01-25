package com.spon.ems.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.spon.mvc.models.BaseSystem;


public class EMS_DB_CONTROLLER implements BaseSystem {
	
	static Logger loger = Logger.getLogger(EMS_DB_CONTROLLER.class.getName());
	
	//內建資料庫連線
	private Connection conn;  //內建資料庫連線
	
	private ArrayList<Connection> conn_list = new ArrayList<Connection>();  //資料庫清單
	
	private String database_resource_config_filename = "Database-Resource.properties";
	private String database_resource_name = "database.resourceName";
	
	public EMS_DB_CONTROLLER(){
		
		/*
		try{
			//建立資料庫連線
			//取得資料庫連線
			if (this.conn == null || this.conn.isClosed()) {
				this.conn = this.getConnection();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
	}
	
	public EMS_DB_CONTROLLER(String resource_config_name, String resource_name){
		
		try{
			this.database_resource_config_filename = resource_config_name;
			this.database_resource_name = resource_name;
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public EMS_DB_CONTROLLER(String resource_name){
		
		try{
			this.database_resource_name = resource_name;
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public String getDatabase_resource_config_filename() {
		return database_resource_config_filename;
	}

	public void setDatabase_resource_config_filename(
			String databaseResourceConfigFilename) {
		database_resource_config_filename = databaseResourceConfigFilename;
	}

	public String getDatabase_resource_name() {
		return database_resource_name;
	}

	public void setDatabase_resource_name(String databaseResourceName) {
		database_resource_name = databaseResourceName;
	}

	/**
	 * 取得內建的資料庫連線
	 * @return
	 */
	public Connection getConn() {
		
		try{
			//建立資料庫連線
			//取得資料庫連線
			if (this.conn == null || this.conn.isClosed()) {
				this.conn = this.getConnection();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.conn;
	}
	
	/**
	 * 關閉資料庫物件
	 */
	public void close(){
		
		try{
			//解除資料庫連線
			if (this.conn != null && !this.conn.isClosed()) {
				//關閉資料庫連線
				this.conn.close();
			}
			
			//解除資料庫清單中的連線
			if(this.conn_list != null && this.conn_list.size() > 0){
				for(int i =0; i<this.conn_list.size(); i++){
					if (this.conn_list.get(i) != null && !this.conn_list.get(i).isClosed()) {
						//關閉資料庫連線
						this.conn_list.get(i).close();
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 判斷資料庫物件是否已被正確關閉
	 * @return
	 */
	public boolean isClosed(){
		
		boolean chk_flag = false;
		
		try{
			//判斷 Connection 是否皆已關閉
			if( (this.conn == null || this.conn.isClosed())
				&& (this.conn_list == null || this.conn_list.size() <= 0) ){
				chk_flag = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 取得資料庫Source名稱
	 * @return
	 */
	public String getSourceName(){
		
		String source_name = "";
		
		try{
			Properties prop = new Properties();
			InputStream in =  this.getClass().getResourceAsStream("/"+this.database_resource_config_filename);
			prop.load(in);
//			System.out.println(prop.keySet());
//			System.out.println(prop.getProperty("database.resourceName").trim());
			source_name = prop.getProperty(this.database_resource_name).trim();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return source_name;
	}
	
	/**
	 * 建立新連線
	 * @return
	 */
	public Connection newConnection(){
		
		try{
			//建立資料庫連線
			this.conn_list.add(this.getConnection());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//Return 當前建立的資料庫連線
		return this.conn_list.get(this.conn_list.size()-1);
	}
	
	/**
	 * 取得 Connection by JNDIName
	 * 
	 * @param JNDIName
	 * @return Connection
	 * @throws Exception
	 */
	public Connection getConnection(String JNDIName) throws Exception {
		
		Connection conn = null;
		Context initCtx;

		initCtx = new InitialContext();
		DataSource ds = null;

		try {
			ds = (DataSource) initCtx.lookup("java:/" + JNDIName);
		} catch (Exception e) {
			try {
				ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/" + JNDIName);
			} catch (Exception e1) {
				loger.error(e1);
			}
		}
		
		try{
			conn = ds.getConnection();

			conn.setAutoCommit(false);
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return conn;
	}
	
	/**
	 * 取得 EMS 資料庫連線
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection() throws Exception {
		
		Connection conn = null;

		try {
			conn = this.getConnection(this.getSourceName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return conn;
	}
	
	/**
	 * 取得  DataSource by JNDIName
	 * @param JNDIName
	 * @return
	 * @throws Exception
	 */
	public DataSource getDataSource(String JNDIName) throws Exception{
		
		Context initCtx;

		initCtx = new InitialContext();
		DataSource ds = null;

		try {
			ds = (DataSource) initCtx.lookup("java:/" + JNDIName);
		} catch (Exception e) {
			try {
				ds = (DataSource) initCtx.lookup("java:comp/env/jdbc/" + JNDIName);
			} catch (Exception e1) {
				loger.error(e1);
			}
		}
		
		return ds;
	}
	
	/**
	 * 取得  EMS DataSource
	 * @return
	 * @throws Exception
	 */
	public DataSource getDataSource() throws Exception{
		
		DataSource ds = null;

		try {
			ds = this.getDataSource(this.getSourceName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ds;
	}
	
	/**
	 * 取得當前Connection 最後輸入的 auto_increament Id
	 * @param local_conn
	 * @return
	 */
	public int getLastInsertId( Connection local_conn ){
		
		int last_insert_id = 0;
		
		try{
			String sql = " SELECT LAST_INSERT_ID() AS ID ";
			
			Statement stmt = local_conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				last_insert_id = rs.getInt("ID");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return last_insert_id;
	}
	
}
