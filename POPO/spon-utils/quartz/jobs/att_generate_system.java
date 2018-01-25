package quartz.jobs;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;

public class att_generate_system implements Job {
	
	/**
	 * 產生每日考勤資料
	 */
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		
		//取消使用 Quartz 自帶的 DBConnectionManager 因為有問題，會造成MySQL連線異常 edit by joe 2011/11/29
		//若要使用DB 則 要在quartz.properties 設定 Datasources
		//DBConnectionManager dbc = DBConnectionManager.getInstance();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn_ems = null;
		
		try {

			if(context.getMergedJobDataMap().size() > 0) {
				
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
	            	conn_ems = tools.getConnection("SPOS");
	            	conn_ems.setAutoCommit(false);
	            }catch(Exception e){
	            	System.out.println("取得EMS資料庫連線失敗!! "+e);
	            	e.printStackTrace();
	            }
	            
	            //取得考勤產生的設定
	            String user_id = context.getMergedJobDataMap().getString("user_id");
				String comp_id = context.getMergedJobDataMap().getString("comp_id");
				String AttSystem = tools.getSysParam(conn_ems, comp_id, "ATT_GENERATE_SYSTEM");  //控制是否啟動考勤自動產生
				String CtlABtttMSGSend = tools.getSysParam(conn_ems, comp_id, "CONTROL_ABATT_MSG_SEND");  //控制是否發送考勤異常訊息
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				//取得公司名稱
				Map compMap = (Map)hr_tools.getCompNameMap(conn_ems,comp_id);
				//員工Map
				Map empMap = hr_tools.getEmpNameMap(comp_id);
				//部門Map
				Map depMap = hr_tools.getDepNameMap(comp_id);
				//職務
				Map titleMap = hr_tools.getTitleNameMap(comp_id);
				
				hr_tools.close();
	            
				if("ON".equals(AttSystem)){
					//對考勤日期的Calendar資料加入偏移量, 控制考勤產生的日期
					int displacement_day = context.getMergedJobDataMap().getIntFromString("displacement_day");  //位移量
					cal.add(Calendar.DAY_OF_MONTH, displacement_day);
					
					//取得必須要的參數值
					String cur_date = tools.covertDateToString(cal.getTime(), "yyyy/MM/dd");  //當前考勤日期
					//產生考勤處理元件
					EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(comp_id, cur_date, user_id );
					//建立考勤異常處理元件
					//EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
					
					try{
						if( !conn_ems.isClosed() && conn_ems != null ){
							
							EMS_AttendanceAction.setToday_FK(false);
							
							Map WorkSchedule= new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							//取得排班表與行事曆
							ems_wsc_tools.getVacations(conn_ems, empMap, depMap, comp_id, cur_date,"", WorkSchedule);
							
							EMS_AttendanceAction.setWorkSchedule(WorkSchedule);
							
							//執行考勤資料產生程式
							att.execute_Attendance( true, "");
							
							conn_ems.commit();
							System.out.println("EMS系統:"+cur_date+" 考勤資料產生完成!!");
							
							if("ON".equals(CtlABtttMSGSend)){
								try{
									//考勤異常訊息發送
									//fix_att_tools.sendAbnormalMessage(conn_ems, cur_date, user_id, comp_id, null);
								}catch(Exception e){
									System.out.println("EMS系統:"+cur_date+" 考勤異常訊息發送失敗!!");
									e.printStackTrace();
								}
							}else{
								System.out.println("考勤異常訊息發送系統尚未啟用!!");
							}
							
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
							System.out.println("資料庫連線異常，無法產生考勤資料!!");
						}
					
					}catch(Exception e){
						System.out.println("執行考勤資料產生程式發生異常!! "+e);
						e.printStackTrace();
					}
					
					
					//產生當天的(禾益佳需求：要能夠看到當天早上的考勤)
					cal = Calendar.getInstance();
					cur_date = tools.covertDateToString(cal.getTime(), "yyyy/MM/dd");  //當前考勤日期
					try{
						if( !conn_ems.isClosed() && conn_ems != null ){
							
							att.setSta_cur_day(cur_date);
							
							att.setToday_FK(true);
							
							
							Map WorkSchedule= new HashMap();
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							//取得排班表與行事曆
							ems_wsc_tools.getVacations(conn_ems, empMap, depMap, comp_id, cur_date,"", WorkSchedule);
							
							att.setWorkSchedule(WorkSchedule);
							
							
							//執行考勤資料產生程式
							att.execute_Attendance( false, "");
							
							conn_ems.commit();
							System.out.println("EMS系統:"+cur_date+" 當天早上考勤資料產生完成!!");
							
							if("ON".equals(CtlABtttMSGSend)){
								try{
									//考勤異常訊息發送
									//fix_att_tools.sendAbnormalMessage(conn_ems, cur_date, user_id, comp_id, null);
								}catch(Exception e){
									System.out.println("EMS系統:"+cur_date+" 當天早上考勤異常訊息發送失敗!!");
									e.printStackTrace();
								}
							}else{
								System.out.println("當天早上考勤異常訊息發送系統尚未啟用!!");
							}
							
							try{
				            	//顯示完成時間
				            	cal = Calendar.getInstance();
				            	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				            	nowtime = ""+sdf.format( cal.getTime());
				            	System.out.println("當天早上考勤完成時間是 ==> "+nowtime);
				            }catch(Exception e){
				            	e.printStackTrace();
				            }
							
						}else{
							System.out.println("資料庫連線異常，無法產生當天早上考勤資料!!");
						}
					
					}catch(Exception e){
						System.out.println("執行當天早上考勤資料產生程式發生異常!! "+e);
						e.printStackTrace();
					}

				}else if("OFF".equals(AttSystem)){
					System.out.println("當天早上考勤自動產生系統尚未啟用!!");
				}else{
					System.out.println("當天早上考勤自動產生系統設定錯誤!!");
				}
			}        

		} catch (Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally
		{
			try {
				if(conn_ems != null && !conn_ems.isClosed() ){
					conn_ems.close();
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
