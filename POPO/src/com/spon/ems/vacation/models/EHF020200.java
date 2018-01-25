package com.spon.ems.vacation.models;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.ArrayList;
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
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午10:59:34
 */
public class EHF020200 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF020200(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF020200( String comp_id ){
		
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
			" SELECT *, DATE_FORMAT(EHF020200T0_08, '%Y年%c月%e日') AS EHF020200T0_08, " +
			" DATE_FORMAT(EHF020200T0_09, '%Y年%c月%e日') AS START_DATE, DATE_FORMAT(EHF020200T0_10, '%Y年%c月%e日') AS END_DATE, " +			
			" B.EHF020100T0_04 AS EHF020100T0_04 " +			
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 B ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_04"))){	//申請人部門組織(代號)
				sql += " and EHF020200T0_04 = ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_03"))){	//申請人(員工工號)
				sql += " and EHF020200T0_03 = ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_14"))){	//假別代號
				sql += " and EHF020200T0_14 = ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_09"))){	//日期起
				sql += " and EHF020200T0_09 >= ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_10"))){	//日期迄
				sql += " and EHF020200T0_10 <= ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_16"))){	//表單狀態
				sql += " and EHF020200T0_16 = ?";
			}
			
			sql += "" +
			" AND EHF020200T0_18 = ? " +  //公司代碼
			" ORDER BY EHF020200T0_04, EHF020200T0_03, EHF020200T0_01 DESC LIMIT 100 ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_04"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020200T0_04"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_03"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020200T0_03"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_14"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020200T0_14"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_09"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020200T0_09"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_10"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020200T0_10"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020200T0_16"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020200T0_16"));
				indexid++;
			}
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
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
			" SELECT EHF020200T0.* , B.EHF020100T0_04 , " +
			" B.EHF020100T0_04 AS EHF020200T0_14_DESC, " +
			" DATE_FORMAT(EHF020200T0_08, '%Y/%m/%d') AS CREATE_DATE " +
			" ,DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') AS START_DATE , DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') AS END_DATE " +
			" ,DATE_FORMAT(EHF020200T0.DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE " +
			" FROM EHF020200T0 " +
			" LEFT JOIN EHF020100T0 B ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+(String)queryCondMap.get("EHF020200T0_01")+"' " +  //請假單單號
			" AND EHF020200T0_18 = '"+(String)queryCondMap.get("COMP_ID")+"' ";  //公司代馬
			
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
		
		String sql = "";
		String show_sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			//Add
			sql = "" +
			" INSERT INTO ehf020200t0 (EHF020200T0_01, EHF020200T0_02, EHF020200T0_03, EHF020200T0_04, EHF020200T0_05," +
			" EHF020200T0_06, EHF020200T0_07, EHF020200T0_08, EHF020200T0_09, EHF020200T0_10, EHF020200T0_11, EHF020200T0_12, " +
			" EHF020200T0_13, EHF020200T0_14, EHF020200T0_15, EHF020200T0_16, EHF020200T0_16_DESC, EHF020200T0_18, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " + 
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_01"));  //表單編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_02")==null?"":(String)dataMap.get("EHF020200T0_02"));  //流程空表單編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_03"));  //申請人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_04"));  //申請人部門組織(代號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_05"));  //填單人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_06"));  //填單人部門組織(代號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_07")==null?"":(String)dataMap.get("EHF020200T0_07"));  //申請人的代理人(員工工號)
			indexid++;
			p6stmt.setString(indexid, tools.ymdTostring(tools.getSysDate()));  //填單日期
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_09"));  //請假日期(起)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_10"));  //請假日期(迄)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_11_HH")+(String)dataMap.get("EHF020200T0_11_MM"));  //請假時間(時/分)(起)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_12_HH")+(String)dataMap.get("EHF020200T0_12_MM"));  //請假時間(時/分)(迄)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_13"));  //請假天數&時數
			indexid++;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_14"));  //假別代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_15")==null?"":(String)dataMap.get("EHF020200T0_15"));  //事由 
			indexid++;
			p6stmt.setString(indexid, "0001");  //表單狀態 -- 請假單 --> 填寫中
			indexid++;
			p6stmt.setString(indexid, "填寫中");  //表單狀態 -- 請假單 --> 填寫中
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
			
			//職務系統代碼
			String EHF020200T0_01 = (String)dataMap.get("EHF020200T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫 職務系統代碼
			dataMap.put("KEY_ID", EHF020200T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF020200().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF020200.addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF020200T0
			sql = "" +
			" DELETE FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+(String)dataMap.get("EHF020200T0_01")+"' " +
			" AND EHF020200T0_18 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
			sql_list.add(sql);
			
			//DELETE SFLOW_LOG_T0
			sql = "" +
			" DELETE FROM SFLOW_LOG_T0 " +
			" WHERE 1=1 " +
			" AND SFLOW_LOG_T0_02 = '"+(String)dataMap.get("EHF020200T0_01")+"' " +
			" AND SFLOW_LOG_T0_14 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			
//			dataMap.put("MAIN_DATA_COUNT", mainDataCount);
			dataMap.put("MAIN_DATA_COUNT", dataCount);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF020200().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF020200().delData()", sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql ="";
		String show_sql = "";
		
		try{
			sql = "" +
			" UPDATE ehf020200t0 SET EHF020200T0_03=?, EHF020200T0_04=?, EHF020200T0_05=?, EHF020200T0_06=?, " +
			" EHF020200T0_07=?, EHF020200T0_09=?, EHF020200T0_10=?, EHF020200T0_11=?, EHF020200T0_12=?, " +
			" EHF020200T0_13=?, EHF020200T0_14=?, EHF020200T0_15=?, USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE EHF020200T0_01 = ? " +
			" AND EHF020200T0_18 = ? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_03"));  //申請人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_04"));  //申請人部門組織(代號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_05"));  //填單人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_06"));  //填單人部門組織(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_07")==null?"":(String)dataMap.get("EHF020200T0_07"));  //申請人的代理人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_09"));  //請假日期(起)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_10"));  //請假日期(迄)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_11_HH")+(String)dataMap.get("EHF020200T0_11_MM"));  //請假時間(時/分)(起)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_12_HH")+(String)dataMap.get("EHF020200T0_12_MM"));  //請假時間(時/分)(迄)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_13"));  //請假天數&時數
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_14"));  //假別(假別代號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_15")==null?"":(String)dataMap.get("EHF020200T0_15"));  //事由
			indexid++;
			
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_01"));  //職務系統代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF020200().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF020200().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub

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
	
	/**
	* 刪除附加檔案
	* @param chkuploadMap
	*/
	public void delAttachedFile(Map chkuploadMap){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			String system_root = "";
			//判斷作業系統
			String osName = System.getProperty("os.name");
			if("Linux".equals(osName)){
				system_root = tools.getSysParam(this.base_tools.getConn(), (String)chkuploadMap.get("COMP_ID"), "REPORT_PATH_LINUX");
			}else{
				system_root = tools.getSysParam(this.base_tools.getConn(), (String)chkuploadMap.get("COMP_ID"), "REPORT_PATH_WINDOWS");
			}
			String filepath = system_root+"ems/uploadfiles/leave/";
			filepath += (String)chkuploadMap.get("chkupload");
			//System.out.println("filepath="+filepath);
			java.net.URLEncoder.encode(filepath, "UTF-8");
			File fout = new File(filepath);
			if(fout.exists()){
				//刪除附加檔案
				fout.delete();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 呈核
	 * @param queryCondMap
	 * @param comp_id
	 * @param user_name
	 * @return
	 */
	public boolean submitDatas(Map queryCondMap, String comp_id, String user_name) {
		// TODO Auto-generated method stub
		
		boolean confirm = false;
		
		/*String sql = "UPDATE EHF020200T0 SET EHF020200T0_16='0006', EHF020200T0_16_DESC='完成', " +
					 "VERSION=VERSION+1, USER_UPDATE = '"+user_name+"', DATE_UPDATE = NOW() " +
				  	 "WHERE EHF020200T0_01 = '"+(String)queryCondMap.get("EHF020200T0_01")+"' AND EHF020200T0_18 = '"+comp_id+"' ";*/
		
		String sql = "UPDATE EHF020200T0 SET EHF020200T0_16='0002', EHF020200T0_16_DESC='呈核', " +
		 			 "VERSION=VERSION+1, USER_UPDATE = '"+user_name+"', DATE_UPDATE = NOW() " +
		 			 "WHERE EHF020200T0_01 = '"+(String)queryCondMap.get("EHF020200T0_01")+"' AND EHF020200T0_18 = '"+comp_id+"' ";
		
		if(base_tools.update(sql)>0){
			confirm = true;
			
			//更新資料庫
			this.base_tools.commit();
		}
		
		return confirm;
	}
	
	/**
	 * 作廢
	 * @param dataMap
	 * @param compId
	 * @param userName
	 */
	public boolean Invalid(Map dataMap, String comp_id, String user_name) {
		// TODO Auto-generated method stub
		
		boolean confirm = false;
		
		String sql = "UPDATE EHF020200T0 SET EHF020200T0_16='0009', EHF020200T0_16_DESC='作廢', " +
					 "VERSION=VERSION+1, USER_UPDATE = '"+user_name+"', DATE_UPDATE = NOW() " +
				  	 "WHERE EHF020200T0_01 = '"+(String)dataMap.get("EHF020200T0_01")+"' AND EHF020200T0_18 = '"+comp_id+"' ";
		
		if(base_tools.update(sql)>0){
			confirm = true;
			
			//更新資料庫
			this.base_tools.commit();
		}
		
		return confirm;
		
	}
	
	/**
	 * 更新流程空表單編號
	 * @param form_no
	 * @param flow_no
	 * @param comp_id
	 */
	public void updateFlowNo(String form_no, String flow_no, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			String sql = "UPDATE EHF020200T0 SET EHF020200T0_02='"+flow_no+"' " +
						 "WHERE EHF020200T0_01 = '"+form_no+"' AND EHF020200T0_18 = '"+comp_id+"' ";
			
			if(base_tools.update(sql)>0){
				//更新資料庫
				this.base_tools.commit();
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public boolean submitFlowDatas(Map queryCondMap, String comp_id, String user_name) {
		// TODO Auto-generated method stub
		
		boolean confirm = false;
		
		String sql = "UPDATE EHF020200T0 SET EHF020200T0_16='"+(String)queryCondMap.get("EHF020200T0_16")+"', " +
					 "EHF020200T0_16_DESC='"+(String)queryCondMap.get("EHF020200T0_16_DESC")+"', " +
		 			 "VERSION=VERSION+1, USER_UPDATE = '"+user_name+"', DATE_UPDATE = NOW() " +
		 			 "WHERE EHF020200T0_01 = '"+(String)queryCondMap.get("EHF020200T0_01")+"' AND EHF020200T0_18 = '"+comp_id+"' ";
		
		if(base_tools.update(sql)>0){
			confirm = true;
			
			//更新資料庫
			this.base_tools.commit();
		}
		
		return confirm;
	}
	
	/**
	 * 更新LOGS的flow_no
	 * @param ehf020200t0_01
	 * @param flowNo
	 * @param compId
	 */
	public void updateFlowLogs(String form_no, String flow_no,
			String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			String sql = "UPDATE SFLOW_LOG_T0 SET SFLOW_LOG_T0_01='"+flow_no+"' " +
						 "WHERE SFLOW_LOG_T0_02 = '"+form_no+"' AND SFLOW_LOG_T0_14 = '"+comp_id+"' ";
			
			if(base_tools.update(sql)>0){
				//更新資料庫
				this.base_tools.commit();
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	/**
	 * 有無流程空表單編號
	 * @param form_no
	 * @param comp_id
	 * @return 無：false，有：true
	 */
	public boolean chkFlowNo(String form_no, String comp_id) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		boolean confirm = false;
		
		try{
			String sql = "" +
			" SELECT EHF020200T0_02 " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+form_no+"' " +
			" AND EHF020200T0_18 = '"+comp_id+"' ";
			
			dataMap = this.base_tools.query(sql);
			
			if("".equals((String)dataMap.get("EHF020200T0_02"))){
				confirm = false;
			}else{
				confirm = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return confirm;
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
