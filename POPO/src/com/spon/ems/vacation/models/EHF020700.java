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
 *@created 2015/11/20 下午1:57:50
 */
public class EHF020700 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF020700(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF020700( String comp_id ){
		
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
			" SELECT EHF020700T0.*, " +
			" DATE_FORMAT(EHF020700T0_07, '%Y年%c月%e日') AS EHF020700T0_07, " +
			" DATE_FORMAT(EHF020700T0_08, '%Y年%c月%e日') AS NO_CARD_DATE, " +  //未打卡日期
			" EHF020700T0_10 AS START_TIME, " +  //實際起始時間
			" EHF020700T0_11 AS END_TIME " +  //實際結束時間
			" FROM EHF020700T0 " +
			" WHERE 1=1 " ;		
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_04"))){	//申請人部門組織(代號)
				sql += " and EHF020700T0_04 = ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_03"))){	//申請人(員工工號)
				sql += " and EHF020700T0_03 = ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_07"))){	//日期起
				sql += " and DATE_FORMAT(EHF020700T0_08, '%Y/%m/%d') >= ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_08"))){	//日期迄
				sql += " and DATE_FORMAT(EHF020700T0_08, '%Y/%m/%d') <= ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_14"))){	//表單狀態
				sql += " and EHF020700T0_14 = ?";
			}
			
			sql += "" +
			" AND EHF020700T0_15 = ? " +  //公司代碼
			" ORDER BY EHF020700T0_04, EHF020700T0_03, EHF020700T0_01 DESC LIMIT 100 ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_04"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020700T0_04"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_03"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020700T0_03"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_07"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020700T0_07"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_08"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020700T0_08"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020700T0_14"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF020700T0_14"));
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
			" SELECT EHF020700T0.*, " +
			" IFNULL(CASE EHF020700T0_09 WHEN '01' THEN '上班未打卡' WHEN '02' THEN '下班未打卡' " +
			" WHEN '03' THEN '全天未打卡' END, '') AS EHF020700T0_09_DESC, " +
			" DATE_FORMAT(EHF020700T0_07, '%Y/%m/%d') AS CREATE_DATE, " +
			" DATE_FORMAT(EHF020700T0_08, '%Y/%m/%d') AS NO_CARD_DATE, " +
			" DATE_FORMAT(EHF020700T0.DATE_UPDATE, '%Y/%m/%d %H：%i：%s') AS DATE_UPDATE " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_01 = '"+(String)queryCondMap.get("EHF020700T0_01")+"' " +
			" AND EHF020700T0_15 = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
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
			" INSERT INTO ehf020700t0 (EHF020700T0_01, EHF020700T0_02, EHF020700T0_03, EHF020700T0_04, " +
			" EHF020700T0_05, " +
			" EHF020700T0_06, EHF020700T0_07, EHF020700T0_08, EHF020700T0_09, EHF020700T0_10, EHF020700T0_11, EHF020700T0_12, " +
			" EHF020700T0_13, EHF020700T0_14, EHF020700T0_14_DESC, EHF020700T0_15, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 0, NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_01"));  //未打卡單單號
			indexid++;
			p6stmt.setString(indexid, "");  //流程空表單編號 - 未打卡單
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_03"));  //申請人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_04"));  //申請人部門組織(代號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_05"));  //填單人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_06"));  //填單人部門組織(代號)
			indexid++;
			p6stmt.setString(indexid, tools.ymdTostring(tools.getSysDate()));  //填單日期
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_08"));  //未打卡日期
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_09"));  //未打卡類別
			indexid++;
//			p6stmt.setString(indexid, "");  //實際時間(時/分)(起)
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_10_HH")+(String)dataMap.get("EHF020700T0_10_MM"));  //實際時間(時/分)(起)
			indexid++;
//			p6stmt.setString(indexid, "");  //實際時間(時/分)(迄)
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_11_HH")+(String)dataMap.get("EHF020700T0_11_MM"));  //實際時間(時/分)(迄)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_12"));  //未打卡原因
			indexid++;
			p6stmt.setString(indexid, "");  //附加檔案存放路徑
			indexid++;
			p6stmt.setString(indexid, "0001");  //表單狀態 -- 未打卡單 --> 填寫中
			indexid++;
			p6stmt.setString(indexid, "填寫中");  //表單狀態 -- 未打卡單 --> 填寫中
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //建立人員
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//職務系統代碼
			String EHF020700T0_01 = (String)dataMap.get("EHF020700T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫 職務系統代碼
			dataMap.put("KEY_ID", EHF020700T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF020700().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF020700.addData()", show_sql+", "+e.getMessage(), 
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
			
			//DELETE EHF020700T0
			sql = "" +
			" DELETE FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_01 = '"+(String)dataMap.get("EHF020700T0_01")+"' " +
			" AND EHF020700T0_15 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			this.base_tools.writeDELETEMSG("EHF020700().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF020700().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE ehf020700t0 SET EHF020700T0_03 = ?, EHF020700T0_04 = ?, EHF020700T0_05 = ?, " +
			" EHF020700T0_06 = ?, EHF020700T0_08 = ?, EHF020700T0_09 = ?, EHF020700T0_10 = ?, EHF020700T0_11 = ?, EHF020700T0_12 = ?, " +
			" USER_UPDATE = ?, VERSION = VERSION+1, DATE_UPDATE = NOW() " +
			" WHERE 1=1 " +
			" AND EHF020700T0_01 = ? " +
			" AND EHF020700T0_15 = ? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_03"));  //申請人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_04"));  //申請人部門組織(代號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_05"));  //填單人(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_06"));  //填單人部門組織(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_08"));  //未打卡日期
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_09"));  //未打卡類別
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_10_HH")+(String)dataMap.get("EHF020700T0_10_MM"));  //實際時間(時/分)(起)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_11_HH")+(String)dataMap.get("EHF020700T0_11_MM"));  //實際時間(時/分)(迄)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_12"));  //未打卡原因
			indexid++;
			
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020700T0_01"));  //職務系統代碼
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
			this.base_tools.writeUPDATEMSG("EHF020700().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF020700().saveData()", 
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
			String filepath = system_root+"ems/uploadfiles/nocard/";
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
