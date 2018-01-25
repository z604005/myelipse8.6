package com.spon.utils.work_schedule;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

public class EMS_Work_Schedule_TOOLS {
	
	//EMS_TOOLS 共用元件
	private static BA_TOOLS tools = BA_TOOLS.getInstance();
	//EMS 考勤資料處理共用元件
	//private static EMS_AttendanceAction ems_att_tools = EMS_AttendanceAction.getInstance(); 
	
	private volatile static EMS_Work_Schedule_TOOLS ems_work_schedule_tools;
	
	public static EMS_Work_Schedule_TOOLS getInstance() {
        /*if (ems_work_schedule_tools == null)
        	ems_work_schedule_tools = new EMS_Work_Schedule_TOOLS();*/
        if(ems_work_schedule_tools == null) {
            synchronized(EMS_Work_Schedule_TOOLS.class) {
            	ems_work_schedule_tools = new EMS_Work_Schedule_TOOLS();
            }
        }
        return ems_work_schedule_tools;
    }
	
	private EMS_Work_Schedule_TOOLS(){
		
	}
	
	/**
	 * 建立公司班別詳細資訊
	 * @param conn
	 * @param detail_flag
	 * @param comp_id
	 * @return
	 */
	public Map createCompClassMap( Connection conn, boolean detail_flag, String comp_id ){
		
		Map compClassMap = new HashMap();
		Map tempMap = null;
		Map classMap = null;
		List allowancelist = null;
		List overtimelist = null;
		List haovertimelist = null;
		
		try{
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			String sql = "" +
			" SELECT EHF000400T0_01 FROM EHF000400T0" +
			" WHERE 1=1 " +
			" AND EHF000400T0_11 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				//建立tempMap
				tempMap = new HashMap();
				
				//取得班別資訊
				classMap = hr_tools.getEmpClassByNo( conn, comp_id, rs.getInt("EHF000400T0_01"));
				//建立公司班別詳細資訊
				tempMap.put("CLASS", classMap);
				
				if(detail_flag){
					//取得班別設定的津貼詳細資料
					//allowancelist = ems_tools.getClassAllowanceData(conn, (Integer)classMap.get("WORK_CLASS_NO"), comp_id);
					
					//取得班別設定的加班詳細資料
					//overtimelist = tools.getClassOvertimeData(conn, (Integer)classMap.get("WORK_CLASS_NO"), comp_id);
					
					//取得班別設定的不休假加班詳細資料
					//haovertimelist = tools.getClassHaOvertimeData(conn, (Integer)classMap.get("WORK_CLASS_NO"), comp_id);

					//建立公司班別津貼、加班費資訊
					tempMap.put("ALLOWANCE", allowancelist);
					tempMap.put("OVERTIME", overtimelist);
					tempMap.put("HAOVERTIME", overtimelist);
				}
				
				//Put into compClassMap
				compClassMap.put( (Integer)classMap.get("WORK_CLASS_NO"), tempMap);
			}
			
			rs.close();
			stmt.close();
			hr_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return compClassMap;
	}
	
	
	
}
