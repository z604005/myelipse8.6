package com.spon.ems.salary.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.util.BA_TOOLS;

/**
 * 全勤獎金扣費規則設定 Business Model 元件
 * @author Joe
 *
 */
public class EHF030108 implements QueryFunction,EditFunction,EditDetailFunction,BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF030108(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF030108( String comp_id ){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction(comp_id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List queryData(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List dataList = null;
		
		try{
			//Query
			String sql = "" +
			" SELECT " +
			" CAST(EHF030108T0_01 AS CHAR) AS EHF030108T0_01, " +
			" EHF030108T0_02, EHF030108T0_03, " +
			" CASE EHF030108T0_12 " +
			" WHEN true THEN '是' " +
			" ELSE '否' " +
			" END AS EHF030108T0_12 " +
			" FROM EHF030108T0 " +
			" WHERE 1=1 ";
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF030108T0_02"))){
				sql += " AND EHF030108T0_02 LIKE ? ";  //全勤獎金扣費規則代碼
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF030108T0_03"))){
				sql += " AND EHF030108T0_03 LIKE ? ";  //全勤獎機扣費規則名稱
			}
			
			sql += " AND EHF030108T0_13 = ? ";  //公司代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF030108T0_02"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF030108T0_02")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF030108T0_03"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF030108T0_03")+"%");
				indexid++;
			}
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			//dataList = this.base_tools.queryList(sql);
			
			pstmt.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}

	@Override
	public Map queryEditData(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		
		try{
			
			//QueryEdit
			String sql = "" +
			" SELECT " +
			" EHF030108T0_01, " +
			" EHF030108T0_02, EHF030108T0_03, " +
			" EHF030108T0_04, EHF030108T0_05, " +
			" EHF030108T0_06, EHF030108T0_07, " +
			" EHF030108T0_08, EHF030108T0_09, " +
			" EHF030108T0_10, EHF030108T0_11, " +
			" CASE EHF030108T0_12 " +
			" WHEN true THEN '1' " +
			" ELSE '0' " +
			" END AS EHF030108T0_12, " +
			" EHF030108T0_13, " +
			" USER_CREATE, USER_UPDATE, " +
			" VERSION, " +
			" DATE_FORMAT(DATE_CREATE, '%Y/%m/%d %H:%i:%s') AS DATE_CREATE, " +
			" DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H:%i:%s') AS DATE_UPDATE " +
			" FROM EHF030108T0 " +
			" WHERE 1=1 " +
			" AND EHF030108T0_01 = '"+(String)queryCondMap.get("EHF030108T0_01")+"' " +
			" AND EHF030108T0_13 = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}
	

	@Override
	public void addData(Map dataMap) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		String show_sql = "";
		
		try{
			//Add
			sql = "" +
			" INSERT INTO EHF030108T0 " +
			" ( " +
			" EHF030108T0_02, EHF030108T0_03, " +
			" EHF030108T0_04, EHF030108T0_05, EHF030108T0_06, EHF030108T0_07, " +
			" EHF030108T0_08, EHF030108T0_09, EHF030108T0_10, EHF030108T0_11, " +
			" EHF030108T0_12, " +
			" EHF030108T0_13, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, " +
			" ?, ?, ?, ?, " +
			" ?, ?, ?, ?, " +
			" ?, " +
			" ?, " +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_02"));  //全勤獎金扣費規則代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_03"));  //全勤獎金扣費規則名稱
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_04"));  //遲到早退頻率單位
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030108T0_05"), "0"));  //遲到早退扣費基準
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_06"));  //遲到早退扣費基準單位
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030108T0_07"), "0"));  //遲到早退扣費金額
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_08"));  //曠職頻率單位
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030108T0_09"), "0"));  //曠職扣費基準
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_10"));  //曠職扣費基準單位
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030108T0_11"), "0"));  //曠職扣費金額
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)dataMap.get("EHF030108T0_12")));  //是否啟用
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
			String EHF030108T0_01 = String.valueOf(this.base_tools.getDb_tools().getLastInsertId(this.base_tools.getConn()));
			
			//回寫 全勤獎金扣費規則序號
			dataMap.put("KEY_ID", EHF030108T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF030108().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF030108.addData()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			List sql_list = new ArrayList();
			
			//DELETE EHF030108T0
			sql = "" +
			" DELETE FROM EHF030108T0 " +
			" WHERE 1=1 " +
			" AND EHF030108T0_01 = '"+(String)dataMap.get("EHF030108T0_01")+"' " +  //全勤獎金扣費規則序號
			" AND EHF030108T0_13 = '"+(String)dataMap.get("COMP_ID")+"' ";  //公司代碼
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
			
			dataMap.put("MAIN_DATA_COUNT", mainDataCount);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF030108().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF030108().delData()", sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF030108T0 SET " +
			" EHF030108T0_03 = ?, " +
			" EHF030108T0_04 = ?, EHF030108T0_05 = ?, EHF030108T0_06 = ?, EHF030108T0_07 = ?, " +
			" EHF030108T0_08 = ?, EHF030108T0_09 = ?, EHF030108T0_10 = ?, EHF030108T0_11 = ?, " +
			" EHF030108T0_12 = ?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF030108T0_01 = ? " +  //全勤獎金扣費規則序號
			" AND EHF030108T0_13 = ? ";  //公司代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
										 pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_03"));  //全勤獎金扣費規則名稱
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_04"));  //遲到早退頻率單位
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030108T0_05"), "0"));  //遲到早退扣費基準
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_06"));  //遲到早退扣費基準單位
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030108T0_07"), "0"));  //遲到早退扣費金額
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_08"));  //曠職頻率單位
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030108T0_09"), "0"));  //曠職扣費基準
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_10"));  //曠職扣費基準單位
			indexid++;
			p6stmt.setString(indexid, this.base_tools.usefulString((String)dataMap.get("EHF030108T0_11"), "0"));  //曠職扣費金額
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)dataMap.get("EHF030108T0_12")));  //是否啟用
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030108T0_01"));  //全勤獎金扣費規則序號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
					
			//更新資料庫
			this.base_tools.commit();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF030108().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF030108().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 檢查全勤獎金扣費規則代碼是否有重複
	 * @param overtime_rule_code
	 * @param comp_id
	 * @return
	 */
	public Map checkDuplicateFullAttendanceRule(String full_attendance_rule_code, String comp_id){
		
		Map return_map = new HashMap();
		boolean chk_flag = false;
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF030108T0_01, " +
			" EHF030108T0_02, EHF030108T0_03, " +
			" EHF030108T0_12 " +
			" FROM EHF030108T0 " +
			" WHERE 1=1 " +
			" AND EHF030108T0_02 = '"+full_attendance_rule_code+"' " +  //全勤獎金扣費規則代碼
			" AND EHF030108T0_13 = '"+comp_id+"' ";  //公司代碼
			
			
			//執行SQL
			Map dataMap = this.base_tools.query(sql);
			
			if(!dataMap.isEmpty()){
				//有找到資料, 表示全勤獎金扣費規則代碼重複
				chk_flag = true;
			}
			
			//建立 RETURN MAP
			return_map.put("CHK_FLAG", chk_flag);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 檢查全勤獎金扣費規則是否有被員工薪資主檔使用
	 * @param overtime_rule_code
	 * @param comp_id
	 * @return
	 */
	public Map checkFullAttendanceRuleUseByEmployee(String full_attendance_rule_code, String comp_id){
		
		Map return_map = new HashMap();
		boolean chk_flag = false;
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF030200T0_01, " +
			" EHF030200T0_02, " +
			" EHF030200T0_08 " +
			//" EHF030200T0_17 " +//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
			" FROM EHF030200T0 " +
			" WHERE 1=1 " +
			" AND EHF030200T0_20 = '"+full_attendance_rule_code+"' " +  //全勤獎金扣費規則代碼
			" AND EHF030200T0_13 = '"+comp_id+"' ";  //公司代碼
			
			
			//執行SQL
			Map dataMap = this.base_tools.query(sql);
			
			if(!dataMap.isEmpty()){
				//有找到資料, 表示全勤獎金扣費規則代碼重複
				chk_flag = true;
			}
			
			//建立 RETURN MAP
			return_map.put("CHK_FLAG", chk_flag);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	
	@Override
	public void addDetailData(String detail_type, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		try{
			//AddDetail
			if("EHF030108T1".equals(detail_type)){
				//新加班明細資訊
				this.addEHF020800T1(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增加班明細
	 * @param detailDataMap
	 */
	private void addEHF020800T1(Map detailDataMap){
		
		String sql = "";
		String show_sql = "";
		
		try{

			//新增加班明細
			sql = "" +
			" INSERT INTO EHF030108T1 " +
			" ( " +
			" EHF030108T1_01, EHF030108T1_02, EHF030108T1_03," +
			" EHF030108T1_04, EHF030108T1_05, EHF030108T1_06," +
			" EHF030108T1_07, "+
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, ?," +
			" ?, ?, ?, " +
			" ?,  " +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			


			p6stmt.setInt(indexid, Integer.valueOf((String)detailDataMap.get("EHF030108T1_01")));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030108T1_02"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030108T1_03"));  
			indexid++;
			p6stmt.setFloat(indexid, Float.valueOf((String)detailDataMap.get("EHF030108T1_04")));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030108T1_05"));  
			indexid++;
			p6stmt.setInt(indexid, Integer.valueOf((String)detailDataMap.get("EHF030108T1_06"))); 
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_CREATE"));  
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_UPDATE"));  
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF020800().addEHF020800T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF020800().addEHF020800T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	public void delDetailData(String type, Map detailDataMap) {
		// TODO Auto-generated method stub
		try{
			if("EHF030108T1".equals(type)){
				//刪除獎金明細資訊
				this.delEHF030108T1(detailDataMap);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * 刪除獎金計算資料
	 * @param detailDataMap
	 */
	private void delEHF030108T1(Map detailDataMap){

		String sql = "";
		
		try{
			//刪除獎金計算資料
			sql = "" +
			" DELETE FROM EHF030108T1 " +
			" WHERE 1=1 " +
			" AND EHF030108T1_01 = "+(String)detailDataMap.get("EHF030108T1_01")+" " +  //獎金計算資料序號
			" AND EHF030108T1_02 = '"+(String)detailDataMap.get("EHF030108T1_02")+"' "+  //獎金計算明細UID
			" AND EHF030108T1_07 = '"+(String)detailDataMap.get("COMP_ID")+"' ";  //員工工號
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF030108().delEHF030108T1()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF030108().delEHF030108T1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public Map queryDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public boolean SELECT_EHF030108T1(String compId, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		
		String sql = "" +
		" SELECT " +
		" * " +
		" FROM EHF030108T1 " +
		" WHERE 1=1 " +
		" AND EHF030108T1_01 = '"+(String)detailDataMap.get("EHF030108T0_01")+"' " +  //全勤獎金扣費規則代碼
		" AND EHF030108T1_02 = '"+(String)detailDataMap.get("EHF030108T1_02")+"' " +  //全勤獎金扣費規則代碼
		" AND EHF030108T1_07 = '"+(String)detailDataMap.get("COMP_ID")+"' ";  //公司代碼
		
		
		//執行SQL
		Map dataMap = this.base_tools.query(sql);
		
		if(!dataMap.isEmpty()){
			//有找到資料, 表示全勤獎金扣費規則明細重複
			chk_flag = true;
		}
		return chk_flag;
	}
	public List query_EHF030108T1_Data(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List dataList = null;
		try{
			//Query
			String sql = "" +
			" SELECT " +
			" * " +
			" FROM EHF030108T1 " +
			" WHERE 1=1 " ;
			sql += " AND EHF030108T1_01 = ? ";  //全勤獎金扣費規則代碼
			sql += " AND EHF030108T1_07 = ? ";  //公司代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			pstmt.setString(indexid, (String) queryCondMap.get("EHF030108T0_01"));
			indexid++;
			pstmt.setString(indexid, (String) queryCondMap.get("COMP_ID"));
			indexid++;

			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			//dataList = this.base_tools.queryList(sql);
			
			
			for(int i=0;i<dataList.size();i++){
				Map map=(Map)dataList.get(i);
				map.put("EHF030108T0_01", (String) queryCondMap.get("EHF030108T0_01"));
				this.get_EHF020100T0(map,(String) queryCondMap.get("COMP_ID"));
				if(map.get("EHF030108T1_03").equals("01")){
					map.put("EHF030108T1_03_TXT","週");
				}
				else if(map.get("EHF030108T1_03").equals("02")){
					map.put("EHF030108T1_03_TXT","兩週(併入第二週)");
				}
				else if(map.get("EHF030108T1_03").equals("03")){
					map.put("EHF030108T1_03_TXT","兩週(併入第四週)");
				}
				else if(map.get("EHF030108T1_03").equals("04")){
					map.put("EHF030108T1_03_TXT","月");
				}
				else if(map.get("EHF030108T1_03").equals("05")){
					map.put("EHF030108T1_03_TXT","天");
				}
				
				
				if(map.get("EHF030108T1_05").equals("01")){
					map.put("EHF030108T1_05_TXT","依每次分鐘條件");
				}
				else if(map.get("EHF030108T1_05").equals("02")){
					map.put("EHF030108T1_05_TXT","依累計分鐘條件");
				}
				else if(map.get("EHF030108T1_05").equals("03")){
					map.put("EHF030108T1_05_TXT","依次數條件");
				}

			}
			
			
			pstmt.close();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}

	/**
	 * 將假別代號轉換為中文
	 * @param map
	 * @param COMP_ID
	 */
	private void get_EHF020100T0(Map map, String COMP_ID) {
		// TODO Auto-generated method stub
		try{
			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);			
			String sql = 
				" SELECT EHF020100T0_03, EHF020100T0_04 " +
				" FROM EHF020100T0 " +
			 	" WHERE 1=1 " +
			 	" AND EHF020100T0_03 = '"+(String)map.get("EHF030108T1_02")+"' " +
			 	" AND EHF020100T0_08 = '"+COMP_ID+"' " ;		
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				map.put("EHF030108T1_02_TXT",rs.getString("EHF020100T0_04"));
			}
			rs.close();
			stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.base_tools.close();
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return this.base_tools.isClosed();
	}
	
	
}
