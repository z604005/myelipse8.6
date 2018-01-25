package com.spon.ems.util.card_system;

import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


public class EMS_CardControl {
	
	private static EMS_CardControl ems_cc_tools; 
	
	
	public static EMS_CardControl getInstance() {
		if (ems_cc_tools == null)
        	ems_cc_tools = new EMS_CardControl();
         return ems_cc_tools;
    }
	
	public Map startSystem(Connection conn, String nowtime, String comp_id){
	//public void startSystem(Connection conn, String nowtime, String comp_id){
		
		Map msgMap = new HashMap();
		
		try{
			
			//取得卡鐘擷取設定資料
			String sql = "" +
						 " SELECT * FROM EHF020402T0 " +
						 " WHERE 1=1 " +
						 " AND ( ( EHF020402T0_02 = '"+nowtime+"' AND EHF020402T0_07 = 'CSV' ) OR ( EHF020402T0_07 = 'EK-3000R' ) ) " +  //執行時間
						 " AND EHF020402T0_07_FLG = true " + //是否啟用
						 " AND EHF020402T0_06 = '"+comp_id+"' " ;  //公司代碼
	            
	        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
//				System.out.println(" 現在執行 "+rs.getString("EHF020402T0_02")+" 的卡鐘匯入設定 ");
				msgMap = new HashMap();
				
				if("CSV".equals(rs.getString("EHF020402T0_07"))){
					//CSV File 匯入
					EMS_CardCSVFile card_system = new EMS_CardCSVFile();
					card_system.setFile_name(rs.getString("EHF020402T0_03"));  //檔案名稱
					card_system.setFile_path(rs.getString("EHF020402T0_04"));  //檔案路徑
					card_system.setMapKey(rs.getString("EHF020402T0_08").split(","));  //資料格式
					msgMap = 
					card_system.process(conn, rs.getString("EHF020402T0_01"), comp_id);  //截取設定資料序號
					
					conn.commit();
				}else if("EK-3000R".equals(rs.getString("EHF020402T0_07"))){
					//資料庫匯入
					EMS_CardDataBase card_system = new EMS_CardDataBase();
					card_system.setMapKey(rs.getString("EHF020402T0_08").split(","));  //資料格式
					card_system.process(conn, rs.getString("EHF020402T0_01"), comp_id);  //截取設定資料序號
					card_system.connClose();  //關閉Log資料庫連線
					
					conn.commit();
				}else{
					
					
				}
				
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return msgMap;
	}
	
	
	
	
}
