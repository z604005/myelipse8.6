package com.spon.ems.util.overtime;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.utils.util.BA_TOOLS;

/**
 * EMS 加班記錄控制元件
 * 
 * @version 1.0
 * @created 10-四月-2006 下午 09:16:47
 */
public class EMS_Overtime_Record_Control implements BaseSystem {
	
	
	private BaseFunction base_tools;
	
	public EMS_Overtime_Record_Control(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public void finalize() throws Throwable {

	}
	
	/**
	 * Mapping OV Map Data
	 * @param source_map
	 * @return
	 */
	public Map<String, String> mappingOVMapData(Map<String, String> source_map){
		
		Map<String, String> mapping_map = new HashMap<String, String>();
		
		try{
			//員工工號 EMPLOYEE_ID
			if(source_map.containsKey("EMPLOYEE_ID")){
				mapping_map.put("EHF020801T0_02", source_map.get("EMPLOYEE_ID"));
			}
			
			//加班資料UID(Mapping用) OV_DATA_UID
			if(source_map.containsKey("OV_DATA_UID")){
				mapping_map.put("EHF020801T0_03", source_map.get("OV_DATA_UID"));
			}
			
			//加班日期 OV_DATE
			if(source_map.containsKey("OV_DATE")){
				mapping_map.put("EHF020801T0_04", source_map.get("OV_DATE"));
			}
			
			//加班起始時間 OV_START_TIME
			if(source_map.containsKey("OV_START_TIME")){
				mapping_map.put("EHF020801T0_05", source_map.get("OV_START_TIME"));
			}
			
			//加班結束時間 OV_END_TIME
			if(source_map.containsKey("OV_END_TIME")){
				mapping_map.put("EHF020801T0_06", source_map.get("OV_END_TIME"));
			}
			
			//加班時數 OV_HOURS
			if(source_map.containsKey("OV_HOURS")){
				mapping_map.put("EHF020801T0_07", source_map.get("OV_HOURS"));
			}
			
			//加班休息起始時間 OV_BREAK_START_TIME
			if(source_map.containsKey("OV_BREAK_START_TIME")){
				mapping_map.put("EHF020801T0_05_BRK", source_map.get("OV_BREAK_START_TIME"));
			}
			
			//加班休息結束時間 OV_BREAK_END_TIME
			if(source_map.containsKey("OV_BREAK_END_TIME")){
				mapping_map.put("EHF020801T0_06_BRK", source_map.get("OV_BREAK_END_TIME"));
			}
			
			//加班內扣時數 OV_HOURS
			if(source_map.containsKey("OV_DE_HOURS")){
				mapping_map.put("EHF020801T0_07_DE", source_map.get("OV_DE_HOURS"));
			}
			
			//公司代碼 COMP_ID
			if(source_map.containsKey("COMP_ID")){
				mapping_map.put("EHF020801T0_08", source_map.get("COMP_ID"));
			}
			
			//加班時數處理方式   20131007新增  BY 賴泓錡
			if(source_map.containsKey("EHF020800T1_11")){
				mapping_map.put("EHF020801T0_09", source_map.get("EHF020800T1_11"));
			}
			
			//中午加班時間
			if(source_map.containsKey("EHF020800T1_12")){
				mapping_map.put("EHF020801T0_10", String.valueOf(source_map.get("EHF020800T1_12")));
			}
			
			//加班類別
			if(source_map.containsKey("EHF020800T1_13")){
				mapping_map.put("OV_TYPE", String.valueOf(source_map.get("EHF020800T1_13")));
			}
			
			//放入原始資料Map
			mapping_map.putAll(source_map);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping_map;
	}
	
	/**
	 * 建立加班記錄(List)
	 * @param paramList
	 * @return
	 */
	public List<Map<String, String>> putOvertimeRecord(List<Map<String, String>> paramList){
		
		List<Map<String, String>> return_list = new ArrayList<Map<String, String>>();
		
		try{
			Map<String, String> paramMap = null;
			Map<String, String> return_map = null;
			
			Iterator<Map<String, String>> it = paramList.iterator();
			
			while(it.hasNext()){
				
				paramMap = it.next();
				//建立加班記錄
				return_map = this.putOvertimeRecord(paramMap);
				
				//記錄訊息
				return_list.add(return_map);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_list;
	}
	
	/**
	 * 建立加班記錄(Map)
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> putOvertimeRecord(Map<String, String> paramMap){
		
		Map<String, String> return_map = new HashMap<String, String>();
		
		try{
			//轉換Map Data
			paramMap = this.mappingOVMapData(paramMap);
			
			//判斷加班記錄資料是否存在
			if(this.chkOvertimeRecordExist(paramMap)){
				//已存在加班記錄資料
				//更新加班記錄
				return_map = this.updateOvertimeRecord(paramMap);
			}else{
				//不存在加班記錄資料
				//新增加班記錄
				return_map = this.insertOvertimeRecord(paramMap);
			}
			
			/*
			//同步連動考勤資料
			this.syncAttendanceData(this.base_tools.getConn(), 
								    paramMap.get("EHF020801T0_04"), paramMap.get("EHF020801T0_02"), 
								    paramMap.get("USER_ID"), paramMap.get("COMP_ID"));
			
			//更新資料庫
			this.base_tools.commit();
			*/
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 移除加班記錄(List)
	 * @param paramList
	 */
	public void removeOvertimeRecord(List<Map<String, String>> paramList){
		
		try{
			Map<String, String> paramMap = null;
			
			Iterator<Map<String, String>> it = paramList.iterator();
			
			while(it.hasNext()){
				
				paramMap = it.next();
				//刪除加班記錄
				this.removeOvertimeRecord(paramMap);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 移除加班記錄(Map)
	 * @param paramMap
	 */
	public void removeOvertimeRecord(Map<String, String> paramMap){
		
		try{
			//轉換Map Data
			paramMap = this.mappingOVMapData(paramMap);
			
			//判斷加班記錄資料是否存在
			if(this.chkOvertimeRecordExist(paramMap)){
				//已存在加班記錄資
				//刪除加班記錄
				this.deleteOvertimeRecord(paramMap);
				
				/*
				//同步連動考勤資料
				this.syncAttendanceData(this.base_tools.getConn(), 
					    				paramMap.get("EHF020801T0_04"), paramMap.get("EHF020801T0_02"), 
					    				paramMap.get("USER_ID"), paramMap.get("COMP_ID"));
				
				//更新資料庫
				this.base_tools.commit();
				*/
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 檢查加班記錄至否存在
	 * @param paramMap
	 * @return
	 */
	public boolean chkOvertimeRecordExist(Map<String, String> paramMap){
		
		boolean chk_flag = false;
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF020801T0_01, " +
			" EHF020801T0_03 " +
			" FROM EHF020801T0 " +
			" WHERE 1=1 " +
			" AND EHF020801T0_08 = '"+(String)paramMap.get("EHF020801T0_08")+"' ";
			
			if(this.base_tools.existString(paramMap.get("EHF020801T0_03"))){
				sql += " AND EHF020801T0_03 = '"+(String)paramMap.get("EHF020801T0_03")+"' ";  //加班資料UID
			}else if(this.base_tools.existString(paramMap.get("EHF020801T0_01"))){
				sql += " AND EHF020801T0_01 = '"+(String)paramMap.get("EHF020801T0_01")+"' ";  //加班記錄UID
			}
			
			//執行SQL
			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				chk_flag = true;
				
				//將資料放入paramMap中
				paramMap.put("EHF020801T0_01", rs.getString("EHF020801T0_01"));
				paramMap.put("EHF020801T0_03", rs.getString("EHF020801T0_03"));
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 新增加班記錄
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> insertOvertimeRecord(Map<String, String> paramMap){
		
		Map<String, String> return_map = new HashMap<String, String>();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		String show_sql = "";
		
		try{
			//Add
			sql = "" +
			" INSERT INTO EHF020801T0 " +
			" ( " +
			" EHF020801T0_01, EHF020801T0_02, EHF020801T0_03, " +
			" EHF020801T0_04, " +
			" EHF020801T0_05, EHF020801T0_06, EHF020801T0_07, " +
			" EHF020801T0_05_BRK, EHF020801T0_06_BRK, EHF020801T0_07_DE, " +
			" EHF020801T0_08,EHF020801T0_09, EHF020801T0_10," +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, ?, " +
			" ?, " +
			" ?, ?, ?, " +
			" ?, ?, ?, " +
			" ?, ?, ?," +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			//加班記錄UID
			String EHF020801T0_01 = "";
			//使用 CodeRules 做單號取得的動作
			//EHF020801T0_01 = EMS_GetCodeRules.getInstance().getCodeRule("EHF020801T0", paramMap.get("COMP_ID"));
			EHF020801T0_01 = tools.createEMSUID(this.base_tools.getConn(), "EOR", "EHF020801T0", "EHF020801T0_01", paramMap.get("COMP_ID"));
			
			p6stmt.setString(indexid, EHF020801T0_01);  //加班記錄UID
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_02"));  //員工工號 EMPLOYEE_ID
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_03"));  //加班資料UID(Mapping用) OV_DATA_UID
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_04"));  //加班日期 OV_DATE
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_05"));  //加班起始時間 OV_START_TIME
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_06"));  //加班結束時間 OV_END_TIME
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_07"));  //加班時數 OV_HOURS
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_05_BRK"));  //加班休息起始時間 OV_BREAK_START_TIME
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_06_BRK"));  //加班休息結束時間 OV_BREAK_END_TIME
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_07_DE"));  //加班內扣時數 OV_DE_HOURS
			indexid++;
			p6stmt.setString(indexid, paramMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_09"));  //加班時數處理方式   20131007新增  BY 賴泓錡
			indexid++;
			
			p6stmt.setFloat(indexid, Float.valueOf(paramMap.get("EHF020801T0_10")));  //中午加班時間
			indexid++;

			p6stmt.setString(indexid, paramMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, paramMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫 加班記錄UID
			paramMap.put("KEY_ID", EHF020801T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EMS_Overtime_Record().insertOvertimeRecord()", show_sql, 
										paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EMS_Overtime_Record().insertOvertimeRecord()", show_sql+", "+e.getMessage(), 
										   paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										   paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 更新加班記錄
	 * @param paramMap
	 * @return
	 */
	public Map<String, String> updateOvertimeRecord(Map<String, String> paramMap){
		
		Map<String, String> return_map = new HashMap<String, String>();
		
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF020801T0 SET " +
			" EHF020801T0_02=?, " +
			" EHF020801T0_04=?, " +
			" EHF020801T0_05=?, EHF020801T0_06=?, EHF020801T0_07=?, " +
			" EHF020801T0_05_BRK=?, EHF020801T0_06_BRK=?, EHF020801T0_07_DE=?,EHF020801T0_09 =?,  EHF020801T0_10 =?," +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF020801T0_01 = ? " +
			" AND EHF020801T0_08 = ? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_02"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_04"));  //加班日期
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_05"));  //加班起始時間
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_06"));  //加班結束時間
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_07"));  //加班時數
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_05_BRK"));  //加班起始時間
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_06_BRK"));  //加班結束時間
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_07_DE"));  //加班時數
			indexid++;
			
			p6stmt.setString(indexid, paramMap.get("EHF020800T1_11"));  //加班時數處理方式     20131007新增   BY 賴泓錡
			indexid++;
			
			p6stmt.setFloat(indexid, Float.valueOf(paramMap.get("EHF020800T1_11")));  //加班時數處理方式     20131007新增   BY 賴泓錡
			indexid++;
			
			p6stmt.setString(indexid, paramMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_01"));  //加班記錄UID
			indexid++;
			p6stmt.setString(indexid, paramMap.get("EHF020801T0_08"));  //公司代碼
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();

			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EMS_Overtime_Record().updateOvertimeRecord()", show_sql, 
										paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EMS_Overtime_Record().updateOvertimeRecord()", show_sql+", "+e.getMessage(), 
										   paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										   paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 刪除加班記錄
	 * @param paramMap
	 */
	public void deleteOvertimeRecord(Map<String, String> paramMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			List sql_list = new ArrayList();

			//DELETE EHF020801T0
			sql = "" +
			" DELETE FROM EHF020801T0 " +
			" WHERE 1=1 " +
			" AND EHF020801T0_01 = '"+paramMap.get("EHF020801T0_01")+"' " +  //加班記錄UID
			" AND EHF020801T0_08 = '"+paramMap.get("COMP_ID")+"' ";  //公司代碼
			sql_list.add(sql);
			
			
			//DELETE EHF020802T0
			sql = "" +
			" DELETE FROM EHF020802T0 " +
			" WHERE 1=1 " +
			" AND EHF020802T0_03 = '"+paramMap.get("EHF020801T0_01")+"' " +  //加班記錄UID
			" AND EHF020802T0_08 = '"+paramMap.get("COMP_ID")+"' ";  //公司代碼
			sql_list.add(sql);
			
			
			
			//執行刪除
			int[] dataCount_array = this.base_tools.executeBatchSQL(sql_list);
			int dataCount = 0;
			int mainDataCount = 0;
			for(int i=0; i<dataCount_array.length; i++){
				dataCount += dataCount_array[i];
				mainDataCount = dataCount_array[i];
			}
			//int dataCount = this.base_tools.delete(sql);
			
			paramMap.put("MAIN_DATA_COUNT", mainDataCount+"");
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EMS_Overtime_Record().deleteOvertimeRecord()", sql+" total delete "+dataCount+"rows data", 
										   paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
										   paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EMS_Overtime_Record().deleteOvertimeRecord()", sql+", "+e.getMessage(), 
											  paramMap.containsKey("USER_NAME")==true?((String)paramMap.get("USER_NAME")):"",
											  paramMap.containsKey("COMP_ID")==true?((String)paramMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 同步考勤資料
	 * @param conn
	 * @param date
	 * @param emp_id
	 * @param user_id
	 * @param comp_id
	 */
	private void syncAttendanceData(Connection conn, String date, String emp_id, String user_id, String comp_id){
		
		try{
			//Create EMS_TOOLS
			BA_TOOLS tools = BA_TOOLS.getInstance();
			
			//取得系統當前日期時間
			Calendar cur_system_cal = Calendar.getInstance();
			//設定系統時間
			cur_system_cal.set(Calendar.HOUR_OF_DAY, 8);
			cur_system_cal.set(Calendar.MINUTE, 0);
			cur_system_cal.set(Calendar.SECOND, 0);
			cur_system_cal.set(Calendar.MILLISECOND, 0);
			
			Calendar ov_cal = tools.covertStringToCalendar(date);  //加班日期
			//設定時間
			ov_cal.set(Calendar.HOUR_OF_DAY, 8);
			ov_cal.set(Calendar.MINUTE, 0);
			ov_cal.set(Calendar.SECOND, 0);
			ov_cal.set(Calendar.MILLISECOND, 0);
			
			//連動考勤資料重新產生
			//需要判斷加班日期是否在當前系統日期之前, 若是在當前系統日期之前才可以執行"連動考勤"
			//判斷是否在當前系統日期之前
			if( ov_cal.compareTo(cur_system_cal) < 0 ){
				//在系統日期之前
				//需要連動考勤資料
				//使用指定的加班日期變更考勤
				//同步連動考勤 -> 重新產生考勤資料
				//EMS_AttendanceAction att_act_tools = new EMS_AttendanceAction(comp_id, date, user_id );
				EMS_AttendanceAction att_act_tools = EMS_AttendanceAction.getInstance(comp_id, date, user_id );
				
				//重新產生員工考勤資料
				att_act_tools.execute_Attendance(true, emp_id);
				
			}else{
				//在系統日期之後
				//不需要連動考勤資料
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return this.base_tools.isClosed();
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.base_tools.close();
	}

	
}
