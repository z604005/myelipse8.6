package com.spon.ems.msg;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.p6spy.engine.spy.P6PreparedStatement;


/**
 * EMS系統訊息元件
 * 
 * @version 1.0
 * @created 10-四月-2006 下午 09:16:47
 */
public class EMS_MSGSYSTEM {
	
	private static EMS_MSGSYSTEM ems_msg_tools; 
	
	//定義事件嚴重層級
	public static final String Serious = "A";
	public static final String Warning = "B";
	public static final String Important = "C";
	public static final String General = "D";
	
	//定義訊息通知層級
	public static final String NONE = "NONE";
	public static final String E_SYSTEM_MANGER = "EMIS";
	public static final String EM_SYSTEM_MANGER = "EMMIS";
	
	//目前先不實作系統自動回應處理功能
	//定義回應處理執行碼
	
	public static EMS_MSGSYSTEM getInstance() {
        if (ems_msg_tools == null)
        	ems_msg_tools = new EMS_MSGSYSTEM();
         return ems_msg_tools;
    }
	
	public EMS_MSGSYSTEM(){
		
	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * 寫入EMS系統訊息
	 * @param conn
	 * @param error_code
	 * @param error_type
	 * @param error_msg
	 * @param user
	 * @param severity_level
	 * @param msg_notification_level
	 * @param comp_id
	 * @return
	 */
	public boolean writeMSGForm( Connection conn, String error_code, String error_type, String error_msg, String user, 
			  					 String severity_level, String msg_notification_level, String comp_id ){
		
		return writeMSG( conn, error_code, error_type, error_msg, user, severity_level,msg_notification_level, comp_id  );
	}
	
	/**
	 * 寫入EMS系統訊息資料
	 * @param conn
	 * @param error_code
	 * @param error_type
	 * @param error_msg
	 * @param user
	 * @param severity_level
	 * @param msg_notification_level
	 * @param comp_id
	 * @return
	 */
	private boolean writeMSG( Connection conn, String error_code, String error_type, String error_msg, String user, 
							  String severity_level, String msg_notification_level, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			//新增表頭資訊
			String sql = "" +
			" INSERT INTO ems_messaget0 " +
			" (ems_messaget0_02, ems_messaget0_03, ems_messaget0_04, ems_messaget0_05, ems_messaget0_06, " +
			"  ems_messaget0_07, ems_messaget0_08, ems_messaget0_09, ems_messaget0_10, ems_messaget0_11, " +
			"  USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " + 
			" VALUES ( ?, ?, ?, NOW(), ?, ?, ?, ?, ?, ?, ?, ?, 1, NOW(), NOW() ) ";


			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, error_code);  //錯誤代碼
			indexid++;
			p6stmt.setString(indexid, error_type);  //類型
			indexid++;
			p6stmt.setString(indexid, error_msg);  //錯誤訊息
			indexid++;
			p6stmt.setString(indexid, user);  //執行人員
			indexid++;
			p6stmt.setString(indexid, severity_level);  //事件嚴重層級
			indexid++;
			p6stmt.setString(indexid, msg_notification_level);  //訊息通知層級
			indexid++;
			
			//進行訊息回應的處理, 取得回應處理執行碼與回應處理狀態碼, 目前未實作
			p6stmt.setString(indexid, "NONE");  //回應處理執行碼
			indexid++;
			p6stmt.setString(indexid, "DONE");  //回應處理狀態碼
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, "EMS_SYSTEM");  //建立人員
			indexid++;
			p6stmt.setString(indexid, "EMS_SYSTEM");  //修改人員
			indexid++;

			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();			

			conn.commit();
			chk_flag = true;
				
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
		
}
