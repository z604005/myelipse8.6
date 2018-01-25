package com.spon.ems.popo.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.util.BA_TOOLS;

public class EHF320100 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	private BA_TOOLS tools;
	
	public EHF320100(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
			tools = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF320100( String comp_id ){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction(comp_id);
			tools = BA_TOOLS.getInstance();
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
			" SELECT EHF320100T0_01, EHF320100T0_02, EHF320100T0_03, EHF320100T0_04, " +			
			" A.EMS_CategoryT1_05 AS EHF320100T0_04_TXT, B.EMS_CategoryT1_05 AS EHF320100T0_05_TXT, C.EMS_CategoryT1_05 AS EHF320100T0_07_TXT, " +
			" CASE EHF320100T0_06 WHEN '1' THEN '是' WHEN '0' THEN '否' END AS EHF320100T0_06_TXT " +
			" FROM EHF320100T0 " +
			" LEFT JOIN EMS_CategoryT1 A ON A.EMS_CategoryT1_04 = EHF320100T0_04 AND A.EMS_CategoryT1_01 = 'MENU_MEAL' " +
			" LEFT JOIN EMS_CategoryT1 B ON B.EMS_CategoryT1_04 = EHF320100T0_05 AND B.EMS_CategoryT1_01 = 'MENU_TYPE' " +
			" LEFT JOIN EMS_CategoryT1 C ON C.EMS_CategoryT1_04 = EHF320100T0_07 AND C.EMS_CategoryT1_01 = 'MENU_OIL' " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF320100T0_02"))){	//菜單編號
				sql += " and EHF320100T0_02 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF320100T0_03"))){	//菜單名稱
				sql += " and EHF320100T0_03 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF320100T0_04"))){	//菜單餐別
				sql += " and EHF320100T0_04 = ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF320100T0_05"))){	//菜單類別
				sql += " and EHF320100T0_05 = ?";
			}
			
			sql += "" +
			" AND EHF320100T0_08 = ? " +  //公司代碼
			" ORDER BY EHF320100T0_02 DESC ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF320100T0_02"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF320100T0_02")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF320100T0_03"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF320100T0_03")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF320100T0_04"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF320100T0_04"));
				indexid++;
			}	
			if(this.base_tools.existString((String)queryCondMap.get("EHF320100T0_05"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF320100T0_05"));
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
			" SELECT EHF320100T0_01, EHF320100T0_02, EHF320100T0_03, EHF320100T0_04, EHF320100T0_05, EHF320100T0_06, EHF320100T0_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" FROM EHF320100T0 " +
			" WHERE 1=1 " +
			" AND EHF320100T0_01 = '"+(String)queryCondMap.get("EHF320100T0_01")+"' " +	//班別序號
			" AND EHF320100T0_08 = '"+(String)queryCondMap.get("COMP_ID")+"' " ;	//公司代碼
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
		
		try{
			//Add
			sql = "" +
			" INSERT INTO EHF320100T0 ( EHF320100T0_01 ,EHF320100T0_02 ,EHF320100T0_03 ,EHF320100T0_04 ,EHF320100T0_05 " +
			" ,EHF320100T0_06 ,EHF320100T0_07" +
			" ,EHF320100T0_08 ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE, DATE_CREATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?, 1, NOW(), NOW() ) " ;

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_01"));  //系統編號
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_02"));  //菜單編號
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_03"));  //菜單名稱
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_04"));  //菜單餐別
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_05"));  //菜單類別
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_06"));  //菜單用酒
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_07"));  //菜單用油
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++; 
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //使用員工姓名
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得 資料表序號
			String EHF320100T0_01 = (String)dataMap.get("EHF320100T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//回寫 職務系統代碼
			dataMap.put("KEY_ID", EHF320100T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF320100().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF320100.addData()", show_sql+", "+e.getMessage(), 
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
			
			sql = "" +
			" DELETE FROM EHF320100T5 " +
			" WHERE 1=1 " +
			" AND EHF320100T5_01 = '"+(String)dataMap.get("EHF320100T0_01")+"' " ;
			sql_list.add(sql);
			
			sql = "" +
			" DELETE FROM EHF320100T4 " +
			" WHERE 1=1 " +
			" AND EHF320100T4_01 = '"+(String)dataMap.get("EHF320100T0_01")+"' " ;
			sql_list.add(sql);
			
			sql = "" +
			" DELETE FROM EHF320100T3 " +
			" WHERE 1=1 " +
			" AND EHF320100T3_01 = '"+(String)dataMap.get("EHF320100T0_01")+"' " ;
			sql_list.add(sql);
			
			sql = "" +
			" DELETE FROM EHF320100T2 " +
			" WHERE 1=1 " +
			" AND EHF320100T2_01 = '"+(String)dataMap.get("EHF320100T0_01")+"' " ;
			sql_list.add(sql);
			
			sql = "" +
			" DELETE FROM EHF320100T1 " +
			" WHERE 1=1 " +
			" AND EHF320100T1_01 = '"+(String)dataMap.get("EHF320100T0_01")+"' " ;
			sql_list.add(sql);
			
			//DELETE EHF320100T0
			sql = "" +
			" DELETE FROM EHF320100T0 " +
			" WHERE 1=1 " +
			" AND EHF320100T0_01 = '"+(String)dataMap.get("EHF320100T0_01")+"' " +
			" AND EHF320100T0_08 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
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
			this.base_tools.writeDELETEMSG("EHF320100().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320100().delData()", sql+", "+e.getMessage(), 
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
			" UPDATE EHF320100T0 SET  " + 
			" EHF320100T0_02=? ,EHF320100T0_03=? ,EHF320100T0_04=? ,EHF320100T0_05=? ,EHF320100T0_06=? " +
			" ,EHF320100T0_07=? " +
			" ,USER_UPDATE=? ,VERSION=VERSION+1 ,DATE_UPDATE=NOW() " +
			" WHERE EHF320100T0_01 = ? " +
			" AND EHF320100T0_08 = ? " ;

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_02"));  //菜單編號
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_03"));  //菜單名稱
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_04"));  //菜單餐別
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_05"));  //菜單類別
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_06"));  //菜單用酒
			indexid++;
			p6stmt.setString(indexid,(String)dataMap.get("EHF320100T0_07"));  //菜單用油
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF320100T0_01"));  //系統編號
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
			this.base_tools.writeUPDATEMSG("EHF320100().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF320100().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		try{
			//AddDetail
			if("EHF320100T3".equals(detailType)){	//明細
				this.addEHF320100T3(detailDataMap);
			}else if("EHF320100T1".equals(detailType)){	//添加物
				this.addEHF320100T1(detailDataMap);
			}else if("EHF320100T2".equals(detailType)){	//擺盤
				this.addEHF320100T2(detailDataMap);
			}else if("EHF320100T4".equals(detailType)){	//遞補主食選項
				this.addEHF320100T4(detailDataMap);
			}else if("EHF320100T5".equals(detailType)){	//遞補副食選項
				this.addEHF320100T5(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private void addEHF320100T3(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		int seqno_new;	//新的順序號碼
		
		try{
			seqno_new = this.base_tools.getMaxSN("EHF320100T3_02", "EHF320100T3", "AND EHF320100T3_01 = '"+(String)detailDataMap.get("EHF320100T0_01")+"'");
			
			sql = "" +
			" INSERT INTO EHF320100T3 " +
			" (EHF320100T3_01, EHF320100T3_02, EHF320100T3_03, EHF320100T3_04, EHF320100T3_05, EHF320100T3_06, EHF320100T3_07) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T0_01"));  //系統編號
			indexid++;
			p6stmt.setInt(indexid, seqno_new);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T3_03"));  //主類別
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T3_04"));  //食物代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T3_05"));  //食物明細代碼
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)detailDataMap.get("EHF320100T3_06")));  //遞補副食菜單
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T3_07")==null?"":(String)detailDataMap.get("EHF320100T3_07"));  //備註
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得
			String EHF320100T3_01 = (String)detailDataMap.get("EHF320100T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫
			detailDataMap.put("KEY_ID", EHF320100T3_01);
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF320100.addEHF320100T3()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF320100.addEHF320100T3()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF320100T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		int seqno_new;	//新的順序號碼
		
		try{
			seqno_new = this.base_tools.getMaxSN("EHF320100T1_02", "EHF320100T1", "AND EHF320100T1_01 = '"+(String)detailDataMap.get("EHF320100T0_01")+"'");
			
			sql = "" +
			" INSERT INTO EHF320100T1 " +
			" (EHF320100T1_01, EHF320100T1_02, EHF320100T1_03) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T0_01"));  //系統編號
			indexid++;
			p6stmt.setInt(indexid, seqno_new);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T1_03"));  //添加物
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得
			String EHF320100T1_01 = (String)detailDataMap.get("EHF320100T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫
			detailDataMap.put("KEY_ID", EHF320100T1_01);
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF320100.addEHF320100T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF320100.addEHF320100T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF320100T2(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		int seqno_new;	//新的順序號碼
		
		try{
			seqno_new = this.base_tools.getMaxSN("EHF320100T2_02", "EHF320100T2", "AND EHF320100T2_01 = '"+(String)detailDataMap.get("EHF320100T0_01")+"'");
			
			sql = "" +
			" INSERT INTO EHF320100T2 " +
			" (EHF320100T2_01, EHF320100T2_02, EHF320100T2_03) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T0_01"));  //系統編號
			indexid++;
			p6stmt.setInt(indexid, seqno_new);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T2_03"));  //擺盤
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得
			String EHF320100T2_01 = (String)detailDataMap.get("EHF320100T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫
			detailDataMap.put("KEY_ID", EHF320100T2_01);
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF320100.addEHF320100T2()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF320100.addEHF320100T2()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
	
	private void addEHF320100T4(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{			
			sql = "" +
			" INSERT INTO EHF320100T4 " +
			" (EHF320100T4_01, EHF320100T4_02, EHF320100T4_03) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T0_01"));  //系統編號
			indexid++;
			p6stmt.setInt(indexid, Integer.parseInt((String)detailDataMap.get("EHF320100T4_02")));  //遞補順序
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T4_03"));  //菜單編號
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得
			String EHF320100T4_01 = (String)detailDataMap.get("EHF320100T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫
			detailDataMap.put("KEY_ID", EHF320100T4_01);
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF320100.addEHF320100T4()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF320100.addEHF320100T4()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF320100T5(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{			
			sql = "" +
			" INSERT INTO EHF320100T5 " +
			" (EHF320100T5_01, EHF320100T5_02, EHF320100T5_03) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T0_01"));  //系統編號
			indexid++;
			p6stmt.setInt(indexid, Integer.parseInt((String)detailDataMap.get("EHF320100T5_02")));  //遞補順序
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF320100T5_03"));  //菜單編號
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得
			String EHF320100T5_01 = (String)detailDataMap.get("EHF320100T0_01");
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫
			detailDataMap.put("KEY_ID", EHF320100T5_01);
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF320100.addEHF320100T5()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF320100.addEHF320100T5()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		try{
			//AddDetail
			if("EHF320100T3".equals(detailType)){	//明細				
				this.delEHF320100T3(detailDataMap);
			}else if("EHF320100T1".equals(detailType)){	//添加物				
				this.delEHF320100T1(detailDataMap);
			}else if("EHF320100T2".equals(detailType)){	//擺盤				
				this.delEHF320100T2(detailDataMap);
			}else if("EHF320100T4".equals(detailType)){	//遞補主食選項
				this.delEHF320100T4(detailDataMap);
			}else if("EHF320100T5".equals(detailType)){	//遞補副食選項
				this.delEHF320100T5(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private void delEHF320100T3(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料
			sql = "" +
			" DELETE FROM EHF320100T3 " +
			" WHERE 1=1 " +
			" AND EHF320100T3_01 = '"+(String)detailDataMap.get("EHF320100T3_01")+"' " +  //
			" AND EHF320100T3_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF320100T3_02"))+"' ";  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF320100().delEHF320100T3()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			//重新訂單明細資料的 - "順序號碼"
			sql = "" +
			" SELECT EHF320100T3_01, EHF320100T3_02 " +
			" FROM EHF320100T3 " +
			" WHERE 1=1 " +
			" AND EHF320100T3_01 = '"+(String)detailDataMap.get("EHF320100T3_01")+"' " +
			" ORDER BY EHF320100T3_02 ";
			//取得訂單明細資料清單
			List ehf320100t1 = this.base_tools.queryList(sql);
			Iterator it = ehf320100t1.iterator();
			Map tempMap  = null;
			int snCount = 0;
			List sql_list = new ArrayList();
			//針對每一筆訂單明細資料清單做順序號碼的處裡
			while(it.hasNext()){
				tempMap = (Map) it.next();
				sql = "" +
				" UPDATE EHF320100T3 SET " +
				" EHF320100T3_02 = '"+snCount+"' " +
				" WHERE 1=1 " +
				" AND EHF320100T3_01 = '"+(String)tempMap.get("EHF320100T3_01")+"' " +  //
				" AND EHF320100T3_02 = '"+(Integer)tempMap.get("EHF320100T3_02")+"' ";  //順序號碼
				sql_list.add(sql);
				snCount++;
			}
			this.base_tools.executeBatchSQL(sql_list);
						
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320100().delEHF320100T3()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF320100T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料
			sql = "" +
			" DELETE FROM EHF320100T1 " +
			" WHERE 1=1 " +
			" AND EHF320100T1_01 = '"+(String)detailDataMap.get("EHF320100T1_01")+"' " +  //
			" AND EHF320100T1_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF320100T1_02"))+"' ";  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF320100().delEHF320100T1()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			//重新訂單明細資料的 - "順序號碼"
			sql = "" +
			" SELECT EHF320100T1_01, EHF320100T1_02 " +
			" FROM EHF320100T1 " +
			" WHERE 1=1 " +
			" AND EHF320100T1_01 = '"+(String)detailDataMap.get("EHF320100T1_01")+"' " +
			" ORDER BY EHF320100T1_02 ";
			//取得訂單明細資料清單
			List ehf320100t1 = this.base_tools.queryList(sql);
			Iterator it = ehf320100t1.iterator();
			Map tempMap  = null;
			int snCount = 0;
			List sql_list = new ArrayList();
			//針對每一筆訂單明細資料清單做順序號碼的處裡
			while(it.hasNext()){
				tempMap = (Map) it.next();
				sql = "" +
				" UPDATE EHF320100T1 SET " +
				" EHF320100T1_02 = '"+snCount+"' " +
				" WHERE 1=1 " +
				" AND EHF320100T1_01 = '"+(String)tempMap.get("EHF320100T1_01")+"' " +  //
				" AND EHF320100T1_02 = '"+(Integer)tempMap.get("EHF320100T1_02")+"' ";  //順序號碼
				sql_list.add(sql);
				snCount++;
			}
			this.base_tools.executeBatchSQL(sql_list);
						
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320100().delEHF320100T1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF320100T2(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料
			sql = "" +
			" DELETE FROM EHF320100T2 " +
			" WHERE 1=1 " +
			" AND EHF320100T2_01 = '"+(String)detailDataMap.get("EHF320100T2_01")+"' " +  //
			" AND EHF320100T2_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF320100T2_02"))+"' ";  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF320100().delEHF320100T2()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			//重新訂單明細資料的 - "順序號碼"
			sql = "" +
			" SELECT EHF320100T2_01, EHF320100T2_02 " +
			" FROM EHF320100T2 " +
			" WHERE 1=1 " +
			" AND EHF320100T2_01 = '"+(String)detailDataMap.get("EHF320100T2_01")+"' " +
			" ORDER BY EHF320100T2_02 ";
			//取得訂單明細資料清單
			List ehf320100t1 = this.base_tools.queryList(sql);
			Iterator it = ehf320100t1.iterator();
			Map tempMap  = null;
			int snCount = 0;
			List sql_list = new ArrayList();
			//針對每一筆訂單明細資料清單做順序號碼的處裡
			while(it.hasNext()){
				tempMap = (Map) it.next();
				sql = "" +
				" UPDATE EHF320100T2 SET " +
				" EHF320100T2_02 = '"+snCount+"' " +
				" WHERE 1=1 " +
				" AND EHF320100T2_01 = '"+(String)tempMap.get("EHF320100T2_01")+"' " +  //
				" AND EHF320100T2_02 = '"+(Integer)tempMap.get("EHF320100T2_02")+"' ";  //順序號碼
				sql_list.add(sql);
				snCount++;
			}
			this.base_tools.executeBatchSQL(sql_list);
						
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320100().delEHF320100T2()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
	
	private void delEHF320100T4(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料
			sql = "" +
			" DELETE FROM EHF320100T4 " +
			" WHERE 1=1 " +
			" AND EHF320100T4_01 = '"+(String)detailDataMap.get("EHF320100T4_01")+"' " +  //
			" AND EHF320100T4_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF320100T4_02"))+"' ";  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF320100().delEHF320100T4()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320100().delEHF320100T4()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
	
	private void delEHF320100T5(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除明細資料
			sql = "" +
			" DELETE FROM EHF320100T5 " +
			" WHERE 1=1 " +
			" AND EHF320100T5_01 = '"+(String)detailDataMap.get("EHF320100T5_01")+"' " +  //
			" AND EHF320100T5_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF320100T5_02"))+"' ";  //
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF320100().delEHF320100T5()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF320100().delEHF320100T5()",
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
	
	public List queryEHF320100T1List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF320100T1List = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT EHF320100T1_01, EHF320100T1_02, EHF320100T1_03, EHF320100T1_01 AS EHF320100T0_01, " +	
			" EMS_CategoryT1_05 AS EHF320100T1_03_TXT " +
			" FROM EHF320100T1 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_04 = EHF320100T1_03 AND EMS_CategoryT1_01 = 'ADDITIVES' " +
			" WHERE 1=1 " +
			" AND EHF320100T1_01 = '"+(String)queryCondMap.get("EHF320100T0_01")+"' " ;
			
			EHF320100T1List = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF320100T1List;
	}
	
	public List queryEHF320100T2List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF320100T2List = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT EHF320100T2_01, EHF320100T2_02, EHF320100T2_03, EHF320100T2_01 AS EHF320100T0_01, " +	
			" EMS_CategoryT1_05 AS EHF320100T2_03_TXT " +
			" FROM EHF320100T2 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_04 = EHF320100T2_03 AND EMS_CategoryT1_01 = 'PLATE' " +
			" WHERE 1=1 " +
			" AND EHF320100T2_01 = '"+(String)queryCondMap.get("EHF320100T0_01")+"' " ;
			
			EHF320100T2List = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF320100T2List;
	}
	
	public List queryEHF320100T3List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF320100T3List = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT EHF320100T3_01, EHF320100T3_02, EHF320100T3_03, EHF320100T3_04, EHF320100T3_05, EHF320100T3_06, EHF320100T3_07, " +	
			" EMS_CategoryT1_05 AS EHF320100T3_03_TXT, PSFOODT0_04 AS EHF320100T3_04_TXT, PSFOODT1_05 AS EHF320100T3_05_TXT, " +
			" CASE EHF320100T3_06 WHEN '1' THEN '是' WHEN '0' THEN '否' END AS EHF320100T3_06_TXT " +
			" FROM EHF320100T3 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_04 = EHF320100T3_03 AND EMS_CategoryT1_01 = 'MAIN_TYPE' " +
			" LEFT JOIN FOODT0 ON PSFOODT0_01 = EHF320100T3_04 " +
			" LEFT JOIN FOODT1 ON PSFOODT1_04 = EHF320100T3_05 AND PSFOODT1_01 = PSFOODT0_01 " +
			" WHERE 1=1 " +
			" AND EHF320100T3_01 = '"+(String)queryCondMap.get("EHF320100T0_01")+"' " ;
			
			EHF320100T3List = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF320100T3List;
	}
	
	public List queryEHF320100T4List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF320100T4List = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT EHF320100T4_01, EHF320100T4_02, EHF320100T4_03, EHF320100T4_01 AS EHF320100T0_01, " +	
			" EHF320100T0_03 AS EHF320100T4_03_TXT " +
			" FROM EHF320100T4 " +
			" LEFT JOIN EHF320100T0 ON EHF320100T0_01 = EHF320100T4_03 " +
			" WHERE 1=1 " +
			" AND EHF320100T4_01 = '"+(String)queryCondMap.get("EHF320100T0_01")+"' " +
			" ORDER BY EHF320100T4_02 ";
			
			EHF320100T4List = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF320100T4List;
	}
	
	public List queryEHF320100T5List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF320100T5List = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT EHF320100T5_01, EHF320100T5_02, EHF320100T5_03, EHF320100T5_01 AS EHF320100T0_01, " +	
			" EHF320100T0_03 AS EHF320100T5_03_TXT " +
			" FROM EHF320100T5 " +
			" LEFT JOIN EHF320100T0 ON EHF320100T0_01 = EHF320100T5_03 " +
			" WHERE 1=1 " +
			" AND EHF320100T5_01 = '"+(String)queryCondMap.get("EHF320100T0_01")+"' " +
			" ORDER BY EHF320100T5_02 ";
			
			EHF320100T5List = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF320100T5List;
	}
	
	/**
	 * 檢核單一值是否重複
	 * @param ch_field
	 * @param table_name
	 * @param key_sql
	 * @return
	 */
	public Map CheckSingleField(String ch_field, String table_name, String ch_value, String key_sql) {
		
		Map dataMap = null;
		
		try{
			//QueryEdit
			String sql = "" +
						 " SELECT "+ch_field+" FROM "+table_name+" " +
						 " WHERE 1=1 " +
						 " AND "+ch_field+" = '"+ch_value+"' " +
						 key_sql ;
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
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
