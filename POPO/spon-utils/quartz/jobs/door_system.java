package quartz.jobs;


import java.sql.Connection;

import java.sql.SQLException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Set;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.spon.ems.util.card_system.EMS_CardControl;
import com.spon.utils.util.BA_TOOLS;


/**
 * 擷取門禁刷卡資料
 * @author Administrator
 *
 */
public class door_system implements Job{

	
	/**
	 * 門禁刷卡擷取程式
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		
		//取消使用 Quartz 自帶的 DBConnectionManager 因為有問題，會造成MySQL連線異常 edit by joe 2011/11/29
		//若要使用DB 則 要在quartz.properties 設定 Datasources
		//DBConnectionManager dbc = DBConnectionManager.getInstance();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		EMS_CardControl ems_cc_tools = EMS_CardControl.getInstance();
		Connection conn_ems = null;
		
		try {

			if(context.getMergedJobDataMap().size() > 0) {
				
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
	            String execute_nowtime = "";
	            try{
	            	//顯示執行時間
	            	cal = Calendar.getInstance();
	            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	            	SimpleDateFormat execute_sdf = new SimpleDateFormat("HHmm");
	            	nowtime = ""+sdf.format( cal.getTime());
	            	execute_nowtime = ""+execute_sdf.format( cal.getTime());
	            	System.out.println("執行時間是 ==> "+nowtime);
	            }catch(Exception e){
	            	e.printStackTrace();
	            }
	            
	          //取得EMS資料庫連線
	            try{
	            	conn_ems = tools.getConnection("SPOS");
	            	conn_ems.setAutoCommit(false);
	            }catch(Exception e){
	            	System.out.println("取得EMS資料庫連線失敗!! "+e);
	            	e.printStackTrace();
	            }
				
	            String comp_id = context.getMergedJobDataMap().getString("comp_id");
				String DoorSystem = tools.getSysParam(conn_ems, comp_id, "DOOR_ACCESS_SYSTEM");
				
				if("ON".equals(DoorSystem)){
				
					if( !conn_ems.isClosed() && conn_ems != null ){
						
						//執行門禁控制元件
						ems_cc_tools.startSystem(conn_ems, execute_nowtime, comp_id);

						conn_ems.commit();
						
						System.out.println("門禁資料擷取完成!!");
						
						try{
			            	//顯示完成時間
			            	cal = Calendar.getInstance();
			            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			            	nowtime = ""+sdf.format( cal.getTime());
			            	System.out.println("完成時間是 ==> "+nowtime);
			            }catch(Exception e){
			            	e.printStackTrace();
			            }
						
					}else{
						
						System.out.println("資料庫連線異常，無法執行門禁資料擷取!!");
					}
					
				}else if("OFF".equals(DoorSystem)){
					System.out.println("門禁系統尚未啟用!!");
				}else{
					System.out.println("門禁系統設定錯誤!!");
				}
				
			}
	        

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				if(conn_ems != null && !conn_ems.isClosed() ){
					conn_ems.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}


}
