package com.spon.utils.util;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.spon.ems.vacation.tools.EMS_AttendanceAction;

import sun.misc.BASE64Decoder;


/**
 * 電子簽名共用元件
 * @author John
 * @date 2015/04/23
 */
public class BA_ENAME{
	//宏家刀具庫系統104年新需求開發案要使用電子簽名，需求簡述如下:
	//1. 使用Jsignature JQuery套件取得電子簽名內碼並儲存到資料表ems_ename
	//2. 使用Jsignature JQuery套件提供的API轉換電子簽名的內碼為PNG圖片並顯示到系統畫面
	//3. 使用BASE64Decoder Java API 轉換電子簽名的內碼為圖片串流供JXL列印刀具領料單時一併附上電子簽名
	
	//CREATE TABLE `ems_ename` (
	//  `EMS_EName_01` varchar(20) NOT NULL DEFAULT '',
	//  `EMS_EName_02` text,
	//  `DATE_UPDATE` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
	//  PRIMARY KEY (`EMS_EName_01`)
	//) ENGINE=InnoDB DEFAULT CHARSET=utf8
	
	private volatile static BA_ENAME ba_ename; 
	private String connectionString = "SPOS";
	
	public static BA_ENAME getInstance() {
		/*if(ba_ename == null) ba_ename = new BA_ENAME();*/
		if(ba_ename == null) {
            synchronized(BA_ENAME.class) {
            	ba_ename = new BA_ENAME();
            }
        }
		
		return ba_ename;
	}
	
	private BA_ENAME(){
		
	}
	
	/**
	 * 儲存電子簽名內碼到ems_ename，編碼格式為BASE64
	 * @param UID 系統單號
	 * @param BASE64_STRING 簽名檔的內碼
	 * @return true:儲存成功 false:儲存失敗
	 * @throws Exception 
	 */
	public boolean insertEName(String UID, String BASE64_STRING){
		boolean result = false;
		
		Connection conn = null;
		Statement stmt = null;
		
		try{
			conn = BA_TOOLS.getInstance().getConnection(connectionString);
			stmt = conn.createStatement();
			
			String sql = "INSERT INTO `ems_ename`(`EMS_EName_01`, `EMS_EName_02`, `DATE_UPDATE`) VALUES('" + UID + "', '" + BASE64_STRING + "', NOW())";
			
			int rowCount = stmt.executeUpdate(sql);
			
			//執行SQL語句
			conn.commit();
			
			if(rowCount == 1) result = true;
			else result = false;
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * 取得電子簽名輸入串流資訊，用於輸出圖片的時候(列印在報表、或提供PNG圖供使用者下載)
	 * @param UID 系統單號
	 * @return ByteArrayInputStream:取得圖片串流成功 null:取得串流失敗
	 * @throws Exception 
	 */
	public ByteArrayInputStream getENameStream(String UID){
		ByteArrayInputStream result = null;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			conn = BA_TOOLS.getInstance().getConnection(connectionString);
			stmt = conn.createStatement();
			
			String sql = "SELECT `EMS_EName_02` FROM `ems_ename` WHERE `EMS_EName_01`='" + UID + "' ";
			
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				//電子簽名PNG檔的內碼為base64格式的字串，須從資料庫取得後解碼為InputStream，才可以給ImageIO.read()讀取
				//儲存在資料庫裏面的字串為data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAyAAA...
				String data = rs.getString("EMS_EName_02");
				//從data:image/png;base64,之後開始才是base64編碼
				int start = data.indexOf(",");
				data = data.substring(start + 1);
				BASE64Decoder bd = new BASE64Decoder();
				byte[] buffer = bd.decodeBuffer(data);
				ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
				result = bais;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * 刪除電子簽名
	 * @param UID 系統單號
	 * @return true:刪除成功 false:刪除失敗
	 * @throws Exception 
	 */
	public boolean deleteEName(String UID){
		boolean result = false;
		
		Connection conn = null;
		Statement stmt = null;
		
		try{
			conn = BA_TOOLS.getInstance().getConnection(connectionString);
			stmt = conn.createStatement();
			
			String sql = "DELETE FROM `ems_ename` WHERE `EMS_EName_01`='" + UID + "'";
			
			int rowCount = stmt.executeUpdate(sql);
			
			//執行SQL語句
			conn.commit();
			
			if(rowCount == 1) result = true;
			else result = false;
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * 更新電子簽名內碼
	 * @param UID 系統單號
	 * @param BASE64_STRING 簽名檔的內碼
	 * @return true:更新成功 false:更新失敗
	 * @throws Exception 
	 */
	public boolean updateEName(String UID, String BASE64_STRING){
		boolean result = false;
		
		Connection conn = null;
		Statement stmt = null;
		
		try{
			conn = BA_TOOLS.getInstance().getConnection(connectionString);
			stmt = conn.createStatement();
			
			String sql = "UPDATE `ems_ename` SET `EMS_EName_02`='" + BASE64_STRING + "', `DATE_UPDATE`=NOW() WHERE `EMS_EName_01`='" + UID + "'";
			
			int rowCount = stmt.executeUpdate(sql);
			
			//執行SQL語句
			conn.commit();
			
			if(rowCount == 1) result = true;
			else result = false;
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
