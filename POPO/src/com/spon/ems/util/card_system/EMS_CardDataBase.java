package com.spon.ems.util.card_system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.spon.utils.util.BA_TOOLS;

public class EMS_CardDataBase extends EMS_CardSystem {
	
	
	protected String [] MapKey = null;
	protected Connection conn_log = null;
	
	/**
	 * 建構子
	 */
	public EMS_CardDataBase(){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try {
			conn_log = tools.getConnection("EMSCARDDS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 關閉Log資料庫連線
	 */
	 public void connClose(){
		 
		try {
			if (conn_log != null && !conn_log.isClosed()) {
				conn_log.commit();
				conn_log.close();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		 
	 }
	
	@Override
	protected boolean del_logdata_ems(Connection conn, String iLogDataSN) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		
		try{
			
			Statement stmt = conn_log.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = " DELETE FROM LOGDATA_EMS" +
						 " WHERE 1=1 " +
						 " AND iLogDataSN = '"+iLogDataSN+"' ";
			
			stmt.executeUpdate(sql);
			
			chk_flag = true;
			
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected List getCardDataList(Connection conn, String compId) {
		// TODO Auto-generated method stub
		List datalist = new ArrayList();
		
		try{
			Statement stmt = conn_log.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = " SELECT *, DATE_FORMAT(Datatime, '%Y/%m/%d') as Date, " +
					 	 " DATE_FORMAT(Datatime, '%Y%m%d%H%i%s') as Date2, " +
					 	 " DATE_FORMAT(Datatime, '%H%i%s') as Time, " +
					 	 " DATE_FORMAT(Datatime, '%r') as Time2, " +
					 	 " DATE_FORMAT(Datatime, '%H:%i:%s') as Time3 " +
					 	 " FROM LOGDATA_EMS " +  // e_keyo4 use trigger 產生的 logdata 暫存table
					 	 " WHERE 1=1 " +
					 	 " ORDER BY iLogDataSN ";
		
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				String[] dataarray = new String[9];
				
				dataarray[0] = rs.getString("iLogDataSN");
				dataarray[1] = rs.getString("Date2")+rs.getString("cCardNo");
				dataarray[2] = rs.getString("cCardNo");
				dataarray[3] = rs.getString("Date");
				dataarray[4] = rs.getString("Time");
				dataarray[5] = rs.getString("Date")+" "+rs.getString("Time3");
				dataarray[6] = rs.getString("iMachineId");
				dataarray[7] = rs.getString("iStateNo");
				dataarray[8] = compId;
				
				datalist.add(dataarray);
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return datalist;
	}

	@Override
	public String[] getMapKey(Connection conn, String settingNo, String compId) {
		// TODO Auto-generated method stub
		return MapKey;
	}
	
	public void setMapKey(String[] key){
		
		try{
			MapKey = key;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 時間格式
	 * @return
	 */
	protected String getTimeFormat(){
		return "HHmmss";
	}

	
	
}
