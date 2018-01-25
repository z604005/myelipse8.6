package quartz.jobs;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.utils.util.BA_TOOLS;


/**
 * 產生員工資料
 * @author John
 * @since 2014/11/7
 */
public class empdata_sync_system extends NewDispatchAction implements Job{

	
	/**
	 * 產生員工資料程式
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		
		//取消使用 Quartz 自帶的 DBConnectionManager 因為有問題，會造成MySQL連線異常 edit by joe 2011/11/29
		//若要使用DB 則 要在quartz.properties 設定 Datasources
		//DBConnectionManager dbc = DBConnectionManager.getInstance();
		EMS_DB_CONTROLLER ems_db_controller = new EMS_DB_CONTROLLER();
		EMS_DB_CONTROLLER hongchia_stock_db_controller = new EMS_DB_CONTROLLER();
		Connection conn_ems = null;
		Connection conn_hongchia_stock = null;
		
		try {
//			if(context.getMergedJobDataMap().size() > 0) {
				
				//確認JobSchedule.xml 的參數是否有設定
	            Set keys = context.getMergedJobDataMap().keySet();
	            Iterator itr = (Iterator) keys.iterator();
	            while(itr.hasNext()) {
	                String key = (String) itr.next();
	                String val = context.getMergedJobDataMap().getString(key);
//	                _log.info(" - jobDataMap entry: " + key + " = " + val);
	                System.out.println("The "+key+" is "+ val);
	            }
	            
	            Calendar cal = null;
	            String nowtime = "";
	            try{
	            	//顯示執行時間
	            	cal = Calendar.getInstance();
	            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	            	nowtime = ""+sdf.format( cal.getTime());
	            	System.out.println("執行時間是 ==> "+nowtime);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            
	            //取得EMS資料庫連線
	            try{
	            	ems_db_controller.setDatabase_resource_name("database2.resourceName");
	    			conn_ems = ems_db_controller.getConnection();
	            	conn_ems.setAutoCommit(false);
	            }catch(Exception e){
	            	System.out.println("取得EMS資料庫連線失敗!! "+e);
	            	e.printStackTrace();
	            }
	            
	            //取得local刀具庫存管理系統資料庫連線
	            try{
	            	hongchia_stock_db_controller.setDatabase_resource_name("database.resourceName");
	    			conn_hongchia_stock = hongchia_stock_db_controller.getConnection();
	    			conn_hongchia_stock.setAutoCommit(false);
	            }catch(Exception e){
	            	System.out.println("取得刀具庫存管理系統資料庫連線失敗!! "+e);
	            	e.printStackTrace();
	            }
	            
	            //取得產生人員資料同步的設定
				String comp_id_ems = context.getMergedJobDataMap().getString("comp_id_ems");
				String comp_id_hongchia_stock = context.getMergedJobDataMap().getString("comp_id_hongchia_stock");
	            
				try{
					if( !conn_ems.isClosed() && conn_ems != null && !conn_hongchia_stock.isClosed() && conn_hongchia_stock != null ){
						//執行員工資料產生程式

						//從EMS薪資系統取得人員資料
						Statement stmt_ems = conn_ems.createStatement();
						ResultSet rs_ems = stmt_ems.executeQuery("select * from ems_empdata where COMP_ID = '" + comp_id_ems + "'");
						rs_ems.last();
						int rows = rs_ems.getRow();
						rs_ems.beforeFirst();
						
						//寫入人員資料
						Statement stmt_honchia_stock = conn_hongchia_stock.createStatement();
						
						try{
							//清除人員資料
							if(rows>0){
								stmt_honchia_stock.executeUpdate("delete from ems_empdata where COMP_ID = '" + comp_id_hongchia_stock + "'");
							}
							
							PreparedStatement pstmt = null;
							String sql = "";
							while(rs_ems.next()){
								sql = "insert into ems_empdata(COMP_ID, DEPT_ID, DEPT_NAME, EMPLOYEE_ID, EMPLOYEE_NAME, STATUS) values(?,?,?,?,?,?)";
								
								pstmt = conn_hongchia_stock.prepareStatement(sql);
								
								pstmt.setString(1, comp_id_hongchia_stock);
								pstmt.setString(2, rs_ems.getString("DEPT_ID"));
								pstmt.setString(3, rs_ems.getString("DEPT_NAME"));
								pstmt.setString(4, rs_ems.getString("EMPLOYEE_ID"));
								pstmt.setString(5, rs_ems.getString("EMPLOYEE_NAME"));
								pstmt.setString(6, rs_ems.getString("STATUS"));
								pstmt.execute();
							}
							
							conn_hongchia_stock.commit();
				    		pstmt.close();
						}catch (SQLException e) {
							e.printStackTrace();
							conn_ems.rollback();							
						}
						
						stmt_honchia_stock.close();
						stmt_ems.close();
					}else{
						System.out.println("資料庫連線異常，無法同步員工資料!!");
					}
				
				}catch(Exception e){
					System.out.println("執行員工資料產生程式發生異常!! "+e);
					e.printStackTrace();
				}
//			}        

		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				if(conn_ems != null && !conn_ems.isClosed() ){
					conn_ems.close();
				}
				if(conn_hongchia_stock != null && !conn_hongchia_stock.isClosed() ){
					conn_hongchia_stock.close();
				}
				//取消使用 Quartz 自帶的 DBConnectionManager
				/*
				if(dbc != null){
					//關閉 Quartz DBConnectionManager
					dbc.shutdown("master");
				}
				*/
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
