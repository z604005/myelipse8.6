package com.spon.utils.util;

import java.sql.Connection;
import java.sql.Statement;

/**
 * LOG紀錄產生器
 * @version 1.0
 * @updated 14-七月-2006 上午 11:43:54
 */
public class BA_LOGGER {
	private String LOGGER_STATUS;
	private String LOGGER_IP;
	private String LOGGER_USER;
	private String LOGGER_CLASS;
	public BA_LOGGER(){

	}

	public void finalize() throws Throwable {

	}

	/**
	 * 儲存LOG資料
	 * Level 2: 紀錄SQL syntax
	 * 
	 * 
	 * @param conn
	 * @param sql    SQL Syntax
	 */
	public void doLog2(Connection conn, String sql){
		try {
			Statement stmt =conn.createStatement();
			StringBuffer insertsql =new StringBuffer();
			insertsql.append("insert into LOGGER (LOGGER_SQL,LOGGER_STATUS,"); 
			insertsql.append("LOGGER_USER,LOGGER_IP,LOGGER_CLASS,LOGGER_DATE) values ");
			insertsql.append("('" + sql.replaceAll("'","\"")+"',");
			insertsql.append("'" + LOGGER_STATUS + "',");
			insertsql.append("'" + LOGGER_USER + "',");
			insertsql.append("'" + LOGGER_IP + "',");
			insertsql.append("'" + LOGGER_CLASS + "',");
			insertsql.append("sysdate) ");
			stmt.execute(insertsql.toString());
		}catch(Exception E){
			E.printStackTrace();
		}
	}

	/**
	 * 資料全備份
	 * 如果被指定要做level1紀錄的table，每一次的異動(insert update delete)都將原始資料儲存於結構完全相同的另一table
	 * 
	 * @param conn
	 * @param table    儲存紀錄的表格名稱
	 */
	public void doLog1(Connection conn, String table){

	}
	public void setLOGGER_CLASS(String logger_class) {
		LOGGER_CLASS = logger_class;
	}

	public void setLOGGER_IP(String logger_ip) {
		LOGGER_IP = logger_ip;
	}

	public void setLOGGER_STATUS(String logger_status) {
		LOGGER_STATUS = logger_status;
	}

	public void setLOGGER_USER(String logger_user) {
		LOGGER_USER = logger_user;
	}
}