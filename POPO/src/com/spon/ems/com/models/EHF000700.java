package com.spon.ems.com.models;

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
public class EHF000700 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF000700(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF000700( String comp_id ){
		
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
			" SELECT EHF000700T0_01,EHF000700T0_02,EHF000700T0_03,EHF000700T0_04,EHF000700T0_05 " +		
			" FROM EHF000700T0 " +
			" WHERE 1=1 " ;			

			sql += "" +
			" AND HR_CompanySysNo like ? " +  //公司代碼
			" ORDER BY EHF000700T0_01 DESC LIMIT 100 ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
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
			" SELECT EHF000700T0.* , " +
			" DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE " +
			" FROM EHF000700T0 " +
			" WHERE 1=1 " +
			" AND EHF000700T0_01 = '"+(String)queryCondMap.get("EHF000700T0_01")+"' " +  //請假單單號
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";  //公司代馬
			
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
			" INSERT INTO ehf000700t0 ( EHF000700T0_02, EHF000700T0_03, EHF000700T0_04, EHF000700T0_05," +
			" HR_CompanySysNo, USER_CREATE, USER_UPDATE, DATE_CREATE, DATE_UPDATE, VERSION) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ?, NOW(), NOW(), 1) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
//			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_01"));  //表單編號
//			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_02"));  //表格名稱
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_03")); //表格簡述
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_04")); //表格說明
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_05"));//附件檔案在Server的路徑
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//職務系統代碼
			int EHF000700T0_01 = this.base_tools.getDb_tools().getLastInsertId(this.base_tools.getConn());
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫 職務系統代碼
			dataMap.put("KEY_ID", EHF000700T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF000700().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF000700.addData()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		//System.out.print("88888888888888" );
		String sql = "";

		try{
			List sql_list = new ArrayList();
			
			//DELETE EHF000700T0
			sql = "" +
			" DELETE FROM EHF000700T0 " +
			" WHERE 1=1 " +
			" AND EHF000700T0_01 = '"+(String)dataMap.get("EHF000700T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			this.base_tools.writeDELETEMSG("EHF000700().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF000700().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE ehf000700t0 SET " +
			" EHF000700T0_02=?, EHF000700T0_03=?,EHF000700T0_04=?, USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE EHF000700T0_01 = ? " +
			" AND HR_CompanySysNo = ? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_02"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_03"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_04"));  
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000700T0_01"));  //職務系統代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF000700().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF000700().saveData()", 
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
	 * 更新流程空表單編號
	 * @param form_no
	 * @param flow_no
	 * @param comp_id
	 */
	public void updateFlowNo(String form_no, String flow_no, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			String sql = "UPDATE EHF000700T0 SET EHF000700T0_01='"+flow_no+"' " +
						 "WHERE EHF000700T0_01 = '"+form_no+"' AND HR_CompanySysNo = '"+comp_id+"' ";
			
			if(base_tools.update(sql)>0){
				//更新資料庫
				this.base_tools.commit();
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 更新LOGS的flow_no
	 * @param ehf000700t0_01
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
