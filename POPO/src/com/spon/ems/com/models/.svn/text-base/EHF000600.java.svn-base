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
public class EHF000600 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF000600(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF000600( String comp_id ){
		
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
			" Select EHF000600T0_01, EHF000600T0_02, EHF000600T0_03, EHF000600T0_04, HR_CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE from ehf000600t0 " 
			+ " WHERE 1=1 "
			+ " AND HR_CompanySysNo = ? " ;  //公司代碼
									
			/*if(this.base_tools.existString((String)queryCondMap.get("EHF000600T0_01"))){	//規則編號
				sql += " and EHF000600T0_01 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF000600T0_02"))){	//規則名稱
				sql += " and EHF000600T0_02 like ?";
			}*/

			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			/*if(this.base_tools.existString((String)queryCondMap.get("EHF000600T0_01"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF000600T0_01"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF000600T0_02"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF000600T0_02"));
				indexid++;
			}		*/	
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
			" SELECT *, DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE " +
			" FROM EHF000600T0 " +
			" WHERE 1=1 " +
			" AND EHF000600T0_01 = '"+(String)queryCondMap.get("EHF000600T0_01")+"' " + 
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";	//公司代碼
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
			"INSERT INTO `ehf000600t0` (`EHF000600T0_01`,`EHF000600T0_02`,`EHF000600T0_03`,`HR_CompanySysNo`,`USER_CREATE`,`USER_UPDATE`,`VERSION`,`DATE_CREATE`,`DATE_UPDATE`) VALUES " +
			" (?, ?, ?, ?, ?, ?, 0, NOW(), NOW()); " ;
			
			/*" INSERT INTO ehf000600t0 (EHF000600T0_01, EHF000600T0_02, EHF000600T0_03, EHF000600T0_04, EHF000600T0_05," +
			" EHF000600T0_06, EHF000600T0_07, EHF000600T0_08, EHF000600T0_09, EHF000600T0_10, EHF000600T0_11, EHF000600T0_12, " +
			" EHF000600T0_13, EHF000600T0_14, EHF000600T0_15, EHF000600T0_16, EHF000600T0_16_DESC, EHF000600T0_18, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " + 
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, NOW()) ";*/
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF000600T0_01"));  //規則編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000600T0_02"));  //規則名稱
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000600T0_03"));  //規則內容簡述
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
			
			//規則編號
			String EHF000600T0_01 = (String)dataMap.get("EHF000600T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫規則編號
			dataMap.put("KEY_ID", EHF000600T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF000600().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF000600.addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF000600T0
			sql = "" +
			" DELETE FROM EHF000600T0 " +
			" WHERE 1=1 " +
			" AND EHF000600T0_01 = '"+(String)dataMap.get("EHF000600T0_01")+"' " +
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
			this.base_tools.writeDELETEMSG("EHF000600().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF000600().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE EHF000600T0 SET " +
			" EHF000600T0_02=?, EHF000600T0_03=?, " +			
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND HR_CompanySysNo=? " +
			" AND EHF000600T0_01=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000600T0_02"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000600T0_03"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF000600T0_01"));//規則編號
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
			this.base_tools.writeUPDATEMSG("EHF000600().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF000600().saveData()", 
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
