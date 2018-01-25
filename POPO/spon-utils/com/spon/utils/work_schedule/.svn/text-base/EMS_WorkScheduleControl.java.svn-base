package com.spon.utils.work_schedule;

import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.spon.ems.vacation.forms.EHF020104M0F;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.utils.util.BA_TOOLS;

/**
 * 排班表控制元件
 * @author Joe
 *
 */
public class EMS_WorkScheduleControl {
	
	private volatile static EMS_WorkScheduleControl ems_wsc_tools; 
	
	
	public static EMS_WorkScheduleControl getInstance() {
		/*if (ems_wsc_tools == null)
			ems_wsc_tools = new EMS_WorkScheduleControl();*/
		if(ems_wsc_tools == null) {
            synchronized(EMS_WorkScheduleControl.class) {
            	ems_wsc_tools = new EMS_WorkScheduleControl();
            }
        }
        return ems_wsc_tools;
    }
	
	private EMS_WorkScheduleControl(){
		
	}
	
	/**
	 * 啟動預排換班連動新增員工排班表
	 * @param conn
	 * @param nowday
	 * @param nowtime
	 * @param comp_id
	 */
	public void startSystem(Connection conn, String key, String employee_id, boolean do_manual_flag, String user_id ,String comp_id){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		int year = -1;
		int month = -1;
		
		try{ 
			int no = 0;
			Calendar cal = null;
			String cur_date = "";
			//建立預排換班執行元件
			//EMS_WorkByClass ems_wbc_tools = EMS_WorkByClass.getInstance();
			
			//取得預排換班設定資料
			String sql = "" +
						 " SELECT EHF020104T0_01, DATE_FORMAT(EHF020104T0_02, '%Y/%m/%d %H：%i：%s') AS EHF020104T0_02, " +
						 " EHF020104T1_03, EHF020104T1_05 " +
						 " FROM EHF020104T0 " +
						 " LEFT JOIN EHF020104T1 ON EHF020104T1_01 = EHF020104T0_01 AND EHF020104T1_06 = EHF020104T0_06 " +
						 " WHERE 1=1 ";
			
						 if(do_manual_flag){
							 sql += " AND EHF020104T0_01 = "+key+" ";  //預排換班序號
						 }else{
							 sql += " AND DATE_FORMAT(EHF020104T0_02, '%Y/%m/%d') = '"+key+"' ";  //換班日期
						 }
						 
						 sql += "" +
						 //" AND EHF020104T0_03 = '02' " +  //狀態
						 " AND EHF020104T1_03 = '"+employee_id+"' " +	//員工
						 " AND EHF020104T0_06 = '"+comp_id+"' " ;  //公司代碼
	            
	        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			//更新預排換班資料 - 使用參數
			//Statement stmt_update = conn.createStatement();
			//String sql_update = "";
			
			if(rs.next()){
				
//				System.out.println(" 現在執行 "+rs.getString("EHF010104T0_02")+" 的預排換班設定 ");
				
				/*no = rs.getInt("EHF010104T0_01");  //序號
				//執行預排換班動作
				ems_wbc_tools.process(conn, rs.getString("EHF010104T1_02"), rs.getString("EHF010104T1_03"), 
						              rs.getInt("EHF010104T1_04"), rs.getInt("EHF010104T1_05"), comp_id);
				
				//更新預排換班的狀態 = '03' 已執行
				sql_update = "" +
				" UPDATE EHF010104T0 SET " +
				" EHF010104T0_03 = '03', " + //狀態
				" EHF010104T0_04 = NOW(), " +  //執行日期
				" DATE_UPDATE = NOW(), " +
				" USER_UPDATE = '工作排班系統' " +  
				" WHERE 1=1 " +
				" AND EHF010104T0_01 = "+no+" " +  //預排換班序號
				" AND EHF010104T0_06 = '"+comp_id+"' ";  //公司代碼
				
				//執行更新
				stmt_update.execute(sql_update);*/
				
				//建立排班表元件
				EMS_Work_Schedule_Table ems_work_schedule = new EMS_Work_Schedule_Table("EMS系統", comp_id);
				//換班日期
				cal = tools.covertStringToCalendar(rs.getString("EHF020104T0_02"));
				cur_date = tools.covertDateToString(cal.getTime(), "yyyy/MM/dd");  //換班日期
				year = Integer.parseInt(tools.covertDateToString(cal.getTime(), "yyyy"));  //換班日期年度(西元年)
				month = Integer.parseInt(tools.covertDateToString(cal.getTime(), "MM"));  //換班日期月份
				//排班表存在,取得排班表序號,否則建立排班表
				no = ems_work_schedule.getScheduleNo(conn, employee_id, year, month);				
				//產生排班表資料
				ems_work_schedule.addSchedule(conn, 
											  no, 
											  cur_date, 
											  (rs.getInt("EHF020104T1_05")==-1),
											  rs.getInt("EHF020104T1_05"),
											  "由EMS系統自動產生排班資料!!", comp_id);
				
				conn.commit();
			}
			
			rs.close();
			stmt.close();
			//stmt_update.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 啟動預排換班連動刪除員工排班表
	 * @param conn
	 * @param EHF020104c
	 * @param user_id
	 * @param comp_id
	 */
	public void delSystem(Connection conn, List EHF020104c, String user_id, String comp_id) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		int year = -1;
		int month = -1;
		
		try{
			int no = 0;
			Calendar cal = null;
			String cur_date = "";
			
			for (int i = 0; i < EHF020104c.size(); i++) {
				
				EHF020104M0F Form = (EHF020104M0F) EHF020104c.get(i);
				
				if (Form.isCHECKED()) {
					//取得預排換班設定資料
					String sql = "" +
								 " SELECT EHF020104T0_01, DATE_FORMAT(EHF020104T0_02, '%Y/%m/%d %H：%i：%s') AS EHF020104T0_02, " +
								 " EHF020104T1_03, EHF020104T1_05 " +
								 " FROM EHF020104T0 " +
								 " LEFT JOIN EHF020104T1 ON EHF020104T1_01 = EHF020104T0_01 AND EHF020104T1_06 = EHF020104T0_06 " +
								 " WHERE 1=1 " +											
								 " AND EHF020104T0_01 = "+Form.getEHF020104T1_01()+" " +  //預排換班序號														 							 							 
								 " AND EHF020104T1_03 = '"+Form.getEHF020104T1_03()+"' " +	//員工
								 " AND EHF020104T0_06 = '"+comp_id+"' " ;  //公司代碼
			            
			        Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs = stmt.executeQuery(sql);
					
					if(rs.next()){					
						//建立排班表元件
						EMS_Work_Schedule_Table ems_work_schedule = new EMS_Work_Schedule_Table("EMS系統", comp_id);
						//換班日期
						cal = tools.covertStringToCalendar(rs.getString("EHF020104T0_02"));
						cur_date = tools.covertDateToString(cal.getTime(), "yyyy/MM/dd");  //換班日期
						year = Integer.parseInt(tools.covertDateToString(cal.getTime(), "yyyy"));  //換班日期年度(西元年)
						month = Integer.parseInt(tools.covertDateToString(cal.getTime(), "MM"));  //換班日期月份
						//排班表存在,取得排班表序號,否則建立排班表
						no = ems_work_schedule.getScheduleNo(conn, Form.getEHF020104T1_03(), year, month);
						//產生排班表資料
						ems_work_schedule.delSchedule(conn, 
													  no, 
													  cur_date, 
													 // tools.isHoliday(conn, cur_date, comp_id, this.getEHF000400T0_17(conn, rs.getInt("EHF020104T1_05"), comp_id)),
													  false,
													  rs.getInt("EHF020104T1_05"),
													  "由EMS系統自動產生排班資料!!", comp_id);
						
						conn.commit();
						
					}
					rs.close();
					stmt.close();
				}
				
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 班別資料休假方式
	 * @param conn
	 * @param EHF000400T0_01
	 * @param comp_id
	 * @return
	 */
	private String getEHF000400T0_17(Connection conn, int EHF000400T0_01, String comp_id) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String EHF000400T0_17 = "";
		
		try{
			sql = "" +
			" SELECT EHF000400T0_17 " +
			" FROM EHF000400T0 " +
			" WHERE 1=1 " +
			" AND EHF000400T0_01 = '"+EHF000400T0_01+"' " +	//員工
			" AND EHF000400T0_11 = '"+comp_id+"' " ;  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				EHF000400T0_17 = rs.getString("EHF000400T0_17");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF000400T0_17;		
	}	
	
	
	
	/**
	 * 
	 * 取得預排換班、行事曆
	 * returnMap 的Key  為員工工號
	 * returnMap內存放更多小Map(小Map的Kay 為日期  ，內容為班別代號)
	 * @param conn
	 * @param empMap   		此為hr_tools.getEmpNameMap(authbean.getCompId());  裡面包含了要找尋排班表的員工	
	 * @param depMap		部門資訊
	 * @param comp_id		公司代號
	 * @param day       	輸入2016/01   或是   2016/01/01    只接受以上兩種格式，其餘皆不做動作
	 * @param day       	 2016/01/01    只接受完整年月日 (只做為兩日期中間的判斷)，其餘皆不做動作(一天或是一個月  該格為"")
	 * @param notWorkDay		
	 */
	public void getVacations(Connection conn, Map empMap, Map depMap, String comp_id, String day1, String day2, Map<String, Map> notWorkDay) {
		// TODO Auto-generated method stub
				
		Map<String, String> dayMap = new HashMap<String, String>();//用來存放個人的每一天排班表狀況
				
		String sql="";
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			Iterator iter = empMap.entrySet().iterator(); 
		
		while (iter.hasNext()) {
			
			dayMap = new HashMap<String, String>();
			Map.Entry entry = (Map.Entry) iter.next(); 
			String key = (String)entry.getKey(); //取得員工工號
				
			sql = "" +
		          " SELECT *" +
				  " FROM EHF010105T0" +
				  " LEFT JOIN" +
				  " EHF010105T1 ON EHF010105T0_01 = EHF010105T1_01 AND EHF010105T0_06 = EHF010105T1_06	WHERE " +
				  " 1 = 1 " +
				  " AND EHF010105T0_02 = '"+key+"'" +//員工工號
				  " AND EHF010105T1_01 is not null" ;
			 
			if(!"".equals(day2)){
				//表示為兩日期中間的判斷				
				sql+=" AND  EHF010105T1_02 between  '"+day1+"'"+ "and '"+day2+"'";
			}else if(day1.length()==7){
				//表示找整個月
				sql+=  " AND EHF010105T1_02 like '"+day1+"/%'" ;//日期
			}else if(day1.length()==10){
				//表示找一天
				sql+=  " AND EHF010105T1_02 = '"+day1+"'" ;//日期
			}else{
				sql+="找尋排班表錯誤。";
				System.out.println("找尋排班表錯誤。");
			}	  
			sql+=  " AND EHF010105T0_06 = '"+comp_id +"'" ;//公司代碼
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				dayMap.put(rs.getString("EHF010105T1_02"), rs.getString("EHF010105T1_04"));//  Key為日期   內容為  班別代號
			}
			rs.close();
			stmt.close();
						 
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,	ResultSet.CONCUR_READ_ONLY);
			
			sql = " select DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS EHF000500T0_05" +
				  " From EHF000500T0 where 1=1"+
				  " AND EHF000500T0_11 = '" + comp_id + "' ";
			
			if(!"".equals(day2)){
				//表示為兩日期中間的判斷				
				sql+=" AND  (DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') between  '"+day1+"'"+ "and '"+day2+"')";
			}else if(day1.length()==7){
				//表示找整個月
				sql+=  " AND DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') like '"+day1+"/%'" ;//日期
			}else if(day1.length()==10){
				//表示找一天
				sql+=  " AND DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') = '"+day1+"'" ;//日期
			}else{
				sql+="找尋排班表錯誤。";
				System.out.println("找尋排班表錯誤。");
			}	
			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
					
				if(dayMap.get(rs.getString("EHF000500T0_05"))==null){						
					dayMap.put(rs.getString("EHF000500T0_05"), "-1");//  Key為日期   內容為 放假(-1表示放假)
				}else{
					//表示已經有這個設定了，不能使用行事曆的日期					
				}
			}
			rs.close();
			stmt.close();
			
			notWorkDay.put(key, dayMap);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		

	}	
	/**
	 * 整理員工名單
	 * @param emp_ID  	員工(UI指定)
	 * @param dep_ID	部門(UI指定)
	 * @param empMap 
	 * @param depMap 
	
	 */
	public void sortoutList(String dep_ID, String emp_ID, Map empMap, Map depMap) {
		// TODO Auto-generated method stub
		String empName="";

		
		if(!"".equals(emp_ID)||!"".equals(dep_ID)){
			//整理Map，如有指定部門與員工，將會被剔除，只會留下指定的部門或是員工
			Iterator iter_emp = empMap.entrySet().iterator(); 
			while (iter_emp.hasNext()) {
				Map.Entry entry = (Map.Entry) iter_emp.next(); 
			    String key = (String)entry.getKey(); 
			    if(!"".equals(emp_ID)){
			    	//當有指定員工，必須將其餘人員存起來，之後從Map刪除
			    	if(!key.equals(emp_ID)){
			    		empName+=key+",";
			    	}	
			    }else if(!"".equals(dep_ID)){
			    	//指定部門時，必須將非此部門的員工剔除
			    	String dep = (String)((Map)empMap.get(key)).get("DEPT_ID");//部門
			    	if(!dep.equals(dep_ID)){
			    		empName+=key+",";
			    	}
			    }   
			}
			
			if(!"".equals(empName)){
				String[] empNameList=empName.split(",");
				for(int i=0; i < empNameList.length; i++){    
					   //System.out.println(empNameList[i]); 
					   empMap.remove(empNameList[i]);
				}   
			}
		
		}
	}
	
	
	/**
	 * 依員工內碼、指定日期或日期區間取得預排換班、行事曆
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @param day1
	 * @param day2
	 * @param dayMap
	 */
	public void getVacationsByEMP(Connection conn, String employee_id, String comp_id, String day1, String day2, Map notWorkDay) {
		
		Map dayMap = new HashMap();//用來存放個人的每一天排班表狀況
		String sql="";
		Statement stmt = null;
		ResultSet rs = null;		
		
		try{
			sql = "" +
	           " SELECT *" +
			   " FROM EHF010105T0" +
			   " LEFT JOIN EHF010105T1 ON EHF010105T0_01 = EHF010105T1_01 AND EHF010105T0_06 = EHF010105T1_06 " +
			   " WHERE 1 = 1 " +
			   " AND EHF010105T0_02 = '"+employee_id+"'" +//員工工號
			   " AND EHF010105T1_01 is not null" ;
		 
			if(!"".equals(day2)){
				//表示為兩日期中間的判斷			
				sql+=" AND  EHF010105T1_02 between  '"+day1+"'"+ "and '"+day2+"'";
			}else if(day1.length()==7){
				//表示找整個月
				sql+=  " AND EHF010105T1_02 like '"+day1+"/%'" ;//日期
			}else if(day1.length()==10){
				//表示找一天
				sql+=  " AND EHF010105T1_02 = '"+day1+"'" ;//日期
			}else{
				System.out.println("找尋排班表錯誤。");
			}	  
			sql+=  " AND EHF010105T0_06 = '"+comp_id +"'" ;//公司代碼
		
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				dayMap.put(rs.getString("EHF010105T1_02"), rs.getString("EHF010105T1_04"));//  Key為日期   內容為  班別代號
			}
			rs.close();
			stmt.close();
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			sql = " select DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS EHF000500T0_05" +
				  " From EHF000500T0 where 1=1"+
				  " AND EHF000500T0_11 = '" + comp_id + "' ";
					 			 			 
			if(!"".equals(day2)){
				 //表示為兩日期中間的判斷				
				 sql+=" AND  (DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') between  '"+day1+"'"+ "and '"+day2+"')";
			}else if(day1.length()==7){
				 //表示找整個月
				sql+=  " AND DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') like '"+day1+"/%'" ;//日期
			}else if(day1.length()==10){
				//表示找一天
				sql+=  " AND DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') = '"+day1+"'" ;//日期
			}else{
				System.out.println("找尋行事曆錯誤。");
			}
			
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				if(dayMap.get(rs.getString("EHF000500T0_05"))==null){					
					dayMap.put(rs.getString("EHF000500T0_05"), "-1");//  Key為日期   內容為 放假(-1表示放假)
				}else{
					//表示已經有這個設定了，不能使用行事曆的日期
				}
			}
			rs.close();
			stmt.close();
			
			notWorkDay.put(employee_id, dayMap);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	


}
