package com.spon.ems.vacation.tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.log4j.Logger;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

/**
 * 處理員工年度休假設定
 *@author maybe
 *@version 1.0
 *@created 2016/2/3 上午10:22:04
 */
public class EMS_setVacationUpdateSQL {
	
	static Logger loger = Logger.getLogger(EMS_setVacationUpdateSQL.class.getName());
	
	private volatile static EMS_setVacationUpdateSQL set_vacationsql_tools; 
	
	
	public static EMS_setVacationUpdateSQL getInstance() {
        /*if (set_vacationsql_tools == null)
        	set_vacationsql_tools = new EMS_setVacationUpdateSQL();*/
        if(set_vacationsql_tools == null) {
            synchronized(EMS_setVacationUpdateSQL.class) {
            	set_vacationsql_tools = new EMS_setVacationUpdateSQL();
            }
        }
        return set_vacationsql_tools;
    }

	private EMS_setVacationUpdateSQL(){

	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * 準備員工休假資訊回寫
	 * @param conn
	 * @param queryCondMap
	 * @param comp_id
	 */
	public void updataVacationData(Map queryCondMap, String comp_id) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		String sql = "";
		String old_proc_status = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			sql = "" +
			" SELECT EHF020200T0_16 " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+(String)queryCondMap.get("EHF020200T0_01")+"' " +
			" AND EHF020200T0_18 = '"+comp_id+"' ";
			
			ResultSet old_rs = stmt.executeQuery(sql);
			
			if(old_rs.next()){
				old_proc_status = old_rs.getString("EHF020200T0_16");  //表單狀態
			}
			old_rs.close();
			
			//若是執行完成的動作，要回寫請假時數到年度休假資料中
			if("0006".equals(old_proc_status)){  //使用者自訂的狀態代碼
				
				//一個工作天的時數
				float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS")); 
				
				sql = "" +
				" SELECT *, EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
				" FROM EHF020200T0 " +
				" WHERE 1=1 " +
				" AND EHF020200T0_01 = '"+(String)queryCondMap.get("EHF020200T0_01")+"' " +
				" AND EHF020200T0_18 = '"+comp_id+"' ";
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					String[] time = rs.getString("EHF020200T0_13").split("/");
					
					float day = Float.parseFloat(time[0]);  //天數
					float hours = Float.parseFloat(time[1]);  //時數
					
					float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
					
					String chiyear = rs.getString("CHIYEAR");  //年度(yy)
					String employee_id = rs.getString("EHF020200T0_03");  //員工工號
					String va_type = rs.getString("EHF020200T0_14");  //假別代號
					String start_date = rs.getString("EHF020200T0_09");  //請假開始日期
					String end_date = rs.getString("EHF020200T0_10");  //請假結束日期
					
					this.handleVacationData(conn, chiyear, employee_id, va_type, true, 
						    start_date, end_date, totalhours, (String)queryCondMap.get("EHF020200T0_01"), comp_id);
					
				}
				rs.close();
			
			//執行作廢後，要還原剩餘時數, 如果表單是完成後作廢才需要還原
			}else if("0009".equals(old_proc_status)){  //使用者自訂的狀態代碼
				
				float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));  //一個工作天的時數
				
				sql = "" +
				" SELECT *, EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
				" FROM EHF020200T0 " +
				" WHERE 1=1 " +
				" AND EHF020200T0_01 = '"+(String)queryCondMap.get("EHF020200T0_01")+"' " +
				" AND EHF020200T0_18 = '"+comp_id+"' ";
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					String[] time = rs.getString("EHF020200T0_13").split("/");
					
					float day = Float.parseFloat(time[0]);  //天數
					float hours = Float.parseFloat(time[1]);  //時數
					
					float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
					
					String chiyear = rs.getString("CHIYEAR");  //年度(yy)
					String employee_id = rs.getString("EHF020200T0_03");  //員工工號
					String va_type = rs.getString("EHF020200T0_14");  //假別代號
					String start_date = rs.getString("EHF020200T0_09");  //請假開始日期
					String end_date = rs.getString("EHF020200T0_10");  //請假結束日期
					
					this.handleVacationData(conn, chiyear, employee_id, va_type, false, 
						    start_date, end_date, totalhours, (String)queryCondMap.get("EHF020200T0_01"), comp_id);
					
				}
				rs.close();
				
			}
			
			stmt.close();
			
			conn.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 準備員工休假資訊回寫
	 * @param conn
	 * @param queryCondMap
	 * @param comp_id
	 */
	public void updataVacationData(Connection conn, String receipt, String comp_id) {
		
		
		String sql = "";
		String old_proc_status = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
				
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			sql = "" +
			" SELECT EHF020200T0_16 " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+receipt+"' " +
			" AND EHF020200T0_18 = '"+comp_id+"' ";
			
			ResultSet old_rs = stmt.executeQuery(sql);
			
			if(old_rs.next()){
				old_proc_status = old_rs.getString("EHF020200T0_16");  //表單狀態
			}
			old_rs.close();
			
			//若是執行完成的動作，要回寫請假時數到年度休假資料中
			if("0006".equals(old_proc_status)){  //使用者自訂的狀態代碼
				
				//一個工作天的時數
				float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS")); 
				
				sql = "" +
				" SELECT *, EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
				" FROM EHF020200T0 " +
				" WHERE 1=1 " +
				" AND EHF020200T0_01 = '"+receipt+"' " +
				" AND EHF020200T0_18 = '"+comp_id+"' ";
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					String[] time = rs.getString("EHF020200T0_13").split("/");
					
					float day = Float.parseFloat(time[0]);  //天數
					float hours = Float.parseFloat(time[1]);  //時數
					
					float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
					
					String chiyear = rs.getString("CHIYEAR");  //年度(yy)
					String employee_id = rs.getString("EHF020200T0_03");  //員工工號
					String va_type = rs.getString("EHF020200T0_14");  //假別代號
					String start_date = rs.getString("EHF020200T0_09");  //請假開始日期
					String end_date = rs.getString("EHF020200T0_10");  //請假結束日期
					
					this.handleVacationData(conn, chiyear, employee_id, va_type, true, 
						    start_date, end_date, totalhours, receipt, comp_id);
					
				}
				rs.close();
			
			//執行作廢後，要還原剩餘時數, 如果表單是完成後作廢才需要還原
			}else if("0009".equals(old_proc_status)){  //使用者自訂的狀態代碼
				
				float Day_work_hours = Float.parseFloat(tools.getSysParam(conn, comp_id, "WORK_HOURS"));  //一個工作天的時數
				
				sql = "" +
				" SELECT *, EXTRACT( YEAR FROM EHF020200T0_09 ) - 1911 AS CHIYEAR " +
				" FROM EHF020200T0 " +
				" WHERE 1=1 " +
				" AND EHF020200T0_01 = '"+receipt+"' " +
				" AND EHF020200T0_18 = '"+comp_id+"' ";
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					String[] time = rs.getString("EHF020200T0_13").split("/");
					
					float day = Float.parseFloat(time[0]);  //天數
					float hours = Float.parseFloat(time[1]);  //時數
					
					float totalhours = hours + ( day * Day_work_hours);  //總共請假時數
					
					String chiyear = rs.getString("CHIYEAR");  //年度(yy)
					String employee_id = rs.getString("EHF020200T0_03");  //員工工號
					String va_type = rs.getString("EHF020200T0_14");  //假別代號
					String start_date = rs.getString("EHF020200T0_09");  //請假開始日期
					String end_date = rs.getString("EHF020200T0_10");  //請假結束日期
					
					this.handleVacationData(conn, chiyear, employee_id, va_type, false, 
						    start_date, end_date, totalhours, receipt, comp_id);
					
				}
				rs.close();
				
			}
			
			stmt.close();
			
			conn.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 處理員工休假資訊回寫
	 * @param conn
	 * @param chiyear
	 * @param employee_id
	 * @param va_type
	 * @param use_type
	 * @param totalhours
	 * @param EHF020200T0_01 請假單UID 主要是用於新增員工扣除請假時數時使用，新增於 20140604 Hedwig
	 * @param comp_id
	 */
	private void handleVacationData(Connection conn, String chiyear,
			String employee_id, String va_type, boolean use_type, String start_date,
			String end_date, float totalhours, String EHF020200T0_01, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			 " SELECT " +
			 " EHF010300T0_01, " +
			 " EHF010300T0_07, EHF010300T0_08 " +
			 //LEFT JOIN EHF010300T1去查詢此員工年度休假扣除多少時數，提供刪除時需要回補時數參考。 20140604 by Hedwig
			 //" IFNULL(EHF010300T1_03,0) AS EHF010300T1_03 " +
			 " FROM EHF010300T0 " +
			 //" LEFT JOIN EHF010300T1 ON EHF010300T1_02 = EHF010300T0_01 AND EHF010300T0_12 = EHF010300T1_04"+
			 " WHERE 1=1 " +
			 //取得年度的限制, 避免無法正常扣除之前年度所留下的假別時數資料
			 //" AND EHF010300T0_02 = ? " + //年度(yy)
			 " AND EHF010300T0_05 = ? " + //員工工號
			 " AND EHF010300T0_06 = ? " + //假別代號
//			 " AND EHF010300T0_08 >= ? " +  //剩餘時數
			 " AND ( ( ? BETWEEN EHF010300T0_09 AND EHF010300T0_10 ) " +
			 " 		 AND ( ? BETWEEN EHF010300T0_09 AND EHF010300T0_10 ) ) " +  //申請日期是否在區間內
			 " AND EHF010300T0_12 = ? ";  //公司代碼
			
			if(use_type){
				//回寫請假時數
				sql += " AND EHF010300T0_08 > ? " +  //剩餘時數
					   "ORDER BY EHF010300T0_02, EHF010300T0_10, EHF010300T0_09 ";
			}else{
				//還原請假時數
				sql += "" +
					   //" AND EHF010300T1_01 = '" +EHF020200T0_01+"' " +
					   " AND EHF010300T0_08 >= ? " +  //剩餘時數
					   " ORDER BY EHF010300T0_02 DESC, EHF010300T0_10 DESC, EHF010300T0_09 DESC ";
			}
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			
			//p6stmt.setString(indexid, chiyear);  //年度(yy)
			//indexid++;
			p6stmt.setString(indexid, employee_id);  //員工工號
			indexid++;
			p6stmt.setString(indexid, va_type);  //假別代號
			indexid++;
//			p6stmt.setInt(indexid, (int)0);  //剩餘時數
//			indexid++;
			p6stmt.setString(indexid, start_date);  //申請日期是否在區間內 - 請假開始日期
			indexid++;
			p6stmt.setString(indexid, end_date);  //申請日期是否在區間內 - 請假結束日期
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, "0");  //剩餘時數
			indexid++;
			
//			System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());
			ResultSet rs = pstmt.executeQuery();
			//ResultSet rs = stmt.executeQuery(sql);
			
			String hours = "0";
			boolean chk_flag = true;
			
			while(rs.next() && chk_flag){
				
				int data_sn = rs.getInt("EHF010300T0_01");  //員工休假資料序號
				float init_hour = rs.getFloat("EHF010300T0_07");  //假別原始時數
				float last_hour = rs.getFloat("EHF010300T0_08");  //假別剩餘時數
				
				if(use_type){
					//回寫請假時數
					if(last_hour >= totalhours){
						//假別剩餘時數大於等於請假時數
						hours = String.valueOf(last_hour - totalhours);
						//this.handleEHF020100t1(conn, EHF020200T0_01, data_sn, use_type, totalhours, va_type, employee_id,comp_id);
						chk_flag = false;
					}else{
						//假別剩餘時數小於請假時數
						hours = String.valueOf((float)0);
						//this.handleEHF020100t1(conn, EHF020200T0_01, data_sn, use_type, totalhours, va_type, employee_id, comp_id);
						totalhours = totalhours - last_hour;
					}
				}else{
					//因為有記錄此員工休假所扣除的時數，因此直接將扣除的時數+假別剩餘時數=回補的時數。 20140604 by Hedwig
//					//還原請假時數
//					if((last_hour + totalhours) > init_hour){
//						//還原請假時數 + 假別剩餘時數 > 假別原始時數
//						hours = String.valueOf(init_hour);
//						totalhours = (last_hour + totalhours) - init_hour;
//						this.handleEHF020100t1(conn, EHF020200T0_01, data_sn, use_type, 0, employee_id, comp_id);
//					}else{
//						//還原請假時數 + 假別剩餘時數 <= 假別原始時數
//						hours = String.valueOf(last_hour + totalhours);
//						this.handleEHF020100t1(conn, EHF020200T0_01, data_sn, use_type, 0, employee_id, comp_id);
//						chk_flag = false;
//					}
					//hours = String.valueOf(last_hour + rs.getFloat("EHF010300T1_03"));
					hours = String.valueOf(last_hour + totalhours);
					//this.handleEHF020100t1(conn, EHF020200T0_01, data_sn, use_type, 0, va_type, employee_id, comp_id);
				}				
				
				sql = "" +
				" UPDATE EHF010300T0 SET " +
				" EHF010300T0_08 = '"+hours+"' " +  //剩餘時數
				" WHERE 1=1 " +
				" AND EHF010300T0_01 = "+data_sn+" " + //員工休假資料序號
				" AND EHF010300T0_12 = '"+comp_id+"' ";  //公司代碼
				stmt.executeUpdate(sql);
				
			}
			
			rs.close();
			stmt.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 將請假扣時數的資料寫入員工扣除請假時數TABLE
	 * @param conn
	 * @param EHF020200T0_01
	 * @param EHF010300T0_01
	 * @param use_type
	 * @param deduction 扣除的時數
	 * @param comp_id
	 */
	/*
	private void handleEHF020100t1(Connection conn, String EHF020200T0_01, int EHF010300T0_01,
									boolean use_type, float deduction, String va_type, String employee_id, String comp_id){

			try{
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				String sql = "";
				
				if(use_type){
					//因為要扣除時數，因此要新增員工扣除請假時數。 20140604 by Hedwig
					sql = "INSERT INTO EHF010300T1 (EHF010300T1_01, EHF010300T1_02, EHF010300T1_03, EHF010300T1_04, " +
						" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
						" VALUES (?, ?, ?, ?, " +
						" ?, ?, 1, NOW(), NOW()) ";
				}else{
					//因為要還原扣除時數，因此要刪除原本寫進去的員工扣除請假時數。 20140604 by Hedwig
					sql = " DELETE FROM EHF010300T1 " +
						" WHERE 1=1 " +
						" AND EHF010300T1_01 = ? " +  //扣除時數請假單UID
						" AND EHF010300T1_02 = ? " + //扣除時數員工年度休假UID
						" AND EHF010300T1_04 = ? ";  //公司代碼
				}
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
				int indexid = 1;
				if(use_type){
					p6stmt.setString(indexid, EHF020200T0_01);  //扣除時數請假單UID
					indexid++;
					p6stmt.setInt(indexid, EHF010300T0_01);  //扣除時數員工年度休假UID
					indexid++;
					p6stmt.setFloat(indexid, deduction);  //扣除時數
					indexid++;
					p6stmt.setString(indexid, comp_id);  //公司代碼
					indexid++;
					p6stmt.setString(indexid, "99999");  //暫時存放99999 20140604 by Hedwig
					indexid++;
					p6stmt.setString(indexid, "99999");  //暫時存放99999 20140604 by Hedwig
					indexid++;
				}else{
					p6stmt.setString(indexid, EHF020200T0_01);  //扣除時數請假單UID
					indexid++;
					p6stmt.setInt(indexid, EHF010300T0_01);  //扣除時數員工年度休假UID
					indexid++;
					p6stmt.setString(indexid, comp_id);  //公司代碼
					indexid++;
				}
				
//				System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());
				p6stmt.executeUpdate();
				
				stmt.close();
				p6stmt.close();
				pstmt.close();
			
			}catch(Exception e){
				System.out.println("取得請假扣時數的資料寫入員工扣除請假時數TABLE時，發生錯誤："+e);
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		}
	*/
	/**
	 * 加班單準備員工休假資訊回寫
	 * @param status
	 * @param dataMap
	 * @param authbean
	 */
	public void updataOverTimeVacation(String status, Map<String, String> dataMap, AuthorizedBean authbean) {
		// TODO Auto-generated method stub
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		boolean chkflag = false;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			String chiyear = tools.getDateADYear(dataMap.get("EHF020801T0_04"), false);
			float totalhours = Float.parseFloat(dataMap.get("EHF020801T0_07")) - Float.parseFloat(dataMap.get("EHF020801T0_07_DE"));
			SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd");
			String start_date = sf.format((tools.datetimeToCalendar(dataMap.get("EHF020801T0_05"))).getTime());
			String end_date = sf.format((tools.datetimeToCalendar(dataMap.get("EHF020801T0_06"))).getTime());
									
			//若是執行完成的動作，要回寫請假時數到年度休假資料中
			if("0006".equals(status)){
				
				//檢查有無個人補休
				chkflag = this.chkVacationData(conn, chiyear, dataMap.get("EHF020801T0_02"), authbean.getCompId());
								
				//處理員工休假資訊回寫
				if(chkflag){
					
					this.handleOverTimeVacation(conn, chiyear, dataMap.get("EHF020801T0_02"), "A13", false, 
						    start_date, end_date, totalhours, authbean.getCompId());
				
				//新增員工休假資訊
				}else{
					
					String use_start_date = tools.getDateADYear(dataMap.get("EHF020801T0_04"), true)+"/01/01";
					//String use_end_year = String.valueOf(Integer.parseInt(tools.getDateADYear(dataMap.get("EHF020801T0_04"), true))+1);
					//String use_end_date = use_end_year+"/06/30";
					String use_end_date = "9999/12/31";
					
					this.addEHF010300T0(conn, chiyear, dataMap.get("EHF020801T0_02"), "A13", use_start_date, use_end_date, totalhours, authbean);
					
				}
			
			//執行作廢、刪除後，要還原剩餘時數, 如果表單是完成後作廢才需要還原
			}else if("0009".equals(status)){
				
				this.handleOverTimeVacation(conn, chiyear, dataMap.get("EHF020801T0_02"), "A13", true, 
					    start_date, end_date, totalhours, authbean.getCompId());
			
			//例假日，外加補休一天
			}else if("1000".equals(status)){
				
				//檢查有無個人補休
				chkflag = this.chkVacationData(conn, chiyear, dataMap.get("EHF020801T0_02"), authbean.getCompId());
				
				if(chkflag){
					
					this.handleOverTimeVacation(conn, chiyear, dataMap.get("EHF020801T0_02"), "A13", false, 
						    start_date, end_date, Float.parseFloat("8"), authbean.getCompId());
					
				}else{
					
					String use_start_date = tools.getDateADYear(dataMap.get("EHF020801T0_04"), true)+"/01/01";
					String use_end_date = "9999/12/31";
					
					this.addEHF010300T0(conn, chiyear, dataMap.get("EHF020801T0_02"), "A13", use_start_date, use_end_date, Float.parseFloat("8"), authbean);
					
				}
				
			}
			
			conn.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void handleOverTimeVacation(Connection conn, String chiyear,
		String employee_id, String va_type, boolean use_type, String start_date,
		String end_date, float totalhours, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			 " SELECT " +
			 " EHF010300T0_01, " +
			 " EHF010300T0_07, EHF010300T0_08 " +
			 " FROM EHF010300T0 " +
			 " WHERE 1=1 " +
			 //取得年度的限制, 避免無法正常扣除之前年度所留下的假別時數資料
			 " AND EHF010300T0_02 = ? " + //年度(yy)
			 " AND EHF010300T0_05 = ? " + //員工工號
			 " AND EHF010300T0_06 = ? " + //假別代號
			 //" AND EHF010300T0_08 >= ? " +  //剩餘時數
			 " AND ( ( ? BETWEEN EHF010300T0_09 AND EHF010300T0_10 ) " +
			 " 		 AND ( ? BETWEEN EHF010300T0_09 AND EHF010300T0_10 ) ) " +  //申請日期是否在區間內
			 " AND EHF010300T0_12 = ? ";  //公司代碼
			
			//if(use_type){
				//還原時數
				//sql += " AND EHF010300T0_08 > ? " +  //剩餘時數
				//	   "ORDER BY EHF010300T0_02, EHF010300T0_10, EHF010300T0_09 ";
			//}else{
				//回寫時數
				sql += "" +
					   " AND EHF010300T0_08 >= ? " +  //剩餘時數
					   " ORDER BY EHF010300T0_02 DESC, EHF010300T0_10 DESC, EHF010300T0_09 DESC ";
			//}
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, chiyear);  //年度(yy)
			indexid++;
			p6stmt.setString(indexid, employee_id);  //員工工號
			indexid++;
			p6stmt.setString(indexid, va_type);  //假別代號
			indexid++;
			//p6stmt.setInt(indexid, (int)0);  //剩餘時數
			//indexid++;
			p6stmt.setString(indexid, start_date);  //申請日期是否在區間內 - 請假開始日期
			indexid++;
			p6stmt.setString(indexid, end_date);  //申請日期是否在區間內 - 請假結束日期
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, "0");  //剩餘時數
			indexid++;
			
			//System.out.println("SQL語句==>"+p6stmt.getQueryFromPreparedStatement());
			ResultSet rs = pstmt.executeQuery();
			
			String hours = "0";
			String surplus = "0";
			boolean chk_flag = true;
			
			while(rs.next() && chk_flag){
				
				int data_sn = rs.getInt("EHF010300T0_01");  //員工休假資料序號
				float init_hour = rs.getFloat("EHF010300T0_07");  //假別原始時數
				float last_hour = rs.getFloat("EHF010300T0_08");  //假別剩餘時數
				
				if(use_type){
					//還原時數
					if(last_hour >= totalhours){
						//假別剩餘時數大於等於加班時數
						hours = String.valueOf(last_hour - totalhours);						
						//chk_flag = false;
					}else{
						//假別剩餘時數小於加班時數
						hours = String.valueOf((float)0);						
						//totalhours = totalhours - last_hour;
					}
					if(init_hour >= totalhours){
						surplus = String.valueOf(init_hour - totalhours);
					}else{
						surplus = String.valueOf((float)init_hour);
					}
					chk_flag = false;
				}else{
					//回寫時數
					hours = String.valueOf(last_hour + totalhours);
					
					surplus = String.valueOf(init_hour + totalhours);
					
					chk_flag = false;
				}				
				
				sql = "" +
				" UPDATE EHF010300T0 SET " +
				" EHF010300T0_08 = '"+hours+"' " +  //剩餘時數
				" WHERE 1=1 " +
				" AND EHF010300T0_01 = "+data_sn+" " + //員工休假資料序號
				" AND EHF010300T0_12 = '"+comp_id+"' ";  //公司代碼
				stmt.executeUpdate(sql);
				
				String sql_2 = "" +
				" UPDATE EHF010300T0 SET " +
				" EHF010300T0_07 = '"+surplus+"' " +  //剩餘時數
				" WHERE 1=1 " +
				" AND EHF010300T0_01 = "+data_sn+" " + //員工休假資料序號
				" AND EHF010300T0_12 = '"+comp_id+"' ";  //公司代碼
				stmt.executeUpdate(sql_2);
				
			}
			
			rs.close();
			stmt.close();
			p6stmt.close();
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 加班單的新增個人年度休假
	 * @param conn
	 * @param chiyear
	 * @param employee_id
	 * @param va_type
	 * @param use_start_date
	 * @param use_end_date
	 * @param totalhours
	 * @param authbean
	 */
	public void addEHF010300T0(Connection conn, String chiyear, String employee_id, String va_type,
			String use_start_date, String use_end_date, float totalhours, AuthorizedBean authbean) {
		// TODO Auto-generated method stub
		
		String sql = "";
		HR_TOOLS hr_tools = new HR_TOOLS();	
		hr_tools.close();
		
		try{
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			
			sql = "" +
			" INSERT INTO ehf010300t0 ( EHF010300T0_02 ,EHF010300T0_03 ,EHF010300T0_04 ,EHF010300T0_05 ,EHF010300T0_06 " +
			" ,EHF010300T0_07 ,EHF010300T0_08, EHF010300T0_09, EHF010300T0_10, EHF010300T0_11, EHF010300T0_12 ,EHF010300T0_13,EHF010300T0_FK," +
			" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE  ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) " ;
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			
			int indexid = 1;
			
			p6stmt.setString(indexid, chiyear);
			indexid++;
			p6stmt.setString(indexid, authbean.getEmployeeID()); //處理人事(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)((Map)empMap.get(employee_id)).get("DEPT_ID"));
			indexid++;
			p6stmt.setString(indexid, employee_id); //員工工號
			indexid++;
			p6stmt.setString(indexid, va_type);  //假別代號
			indexid++;
			p6stmt.setString(indexid, totalhours+"");  //休假時數
			indexid++;
			p6stmt.setString(indexid, totalhours+"");  //剩餘時數
			indexid++;
			p6stmt.setString(indexid, use_start_date);
			indexid++;
			p6stmt.setString(indexid, use_end_date);
			indexid++;
			p6stmt.setString(indexid, "");
			indexid++;
			p6stmt.setString(indexid, authbean.getCompId());
			indexid++;
			
			p6stmt.setString(indexid, "01");
			indexid++;
			
			p6stmt.setString(indexid, "");
			indexid++;
			p6stmt.setString(indexid, authbean.getUserName());
			indexid++;
			p6stmt.setString(indexid, authbean.getUserName());
			indexid++;
			
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			conn.commit();
			
			pstmt.close();
			p6stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 檢查該年度有無該員工的個人補休(A13)
	 * @param conn
	 * @param chiyear
	 * @param string
	 * @param compId
	 * @return
	 */
	public boolean chkVacationData(Connection conn, String chiyear,
			String employee_id, String comp_id) {
		// TODO Auto-generated method stub
		
		boolean chkflag = false;
		String sql = "";
		
		try{
			sql = "SELECT EHF010300T0_01, EHF010300T0_02, EHF010300T0_05 " +
				  "FROM EHF010300T0 " +
				  "WHERE 1=1 " +
				  "AND EHF010300T0_02 = '"+chiyear+"' " +
				  "AND EHF010300T0_05 = '"+employee_id+"' " +
				  "AND EHF010300T0_06 = 'A13' " +
				  "AND EHF010300T0_12 = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				chkflag = true;
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chkflag;
	}

}
