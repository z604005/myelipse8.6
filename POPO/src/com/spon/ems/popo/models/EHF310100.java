package com.spon.ems.popo.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;

public class EHF310100 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {

	private BaseFunction base_tools;
	private BA_TOOLS tools;
	
	public EHF310100(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
			tools = BA_TOOLS.getInstance();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF310100( String comp_id ){
		
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
			" SELECT A.EHF310100T0_01, A.EHF310100T0_02, A.EHF310100T0_03, A.EHF310100T0_04, " +
			" 		 A.EHF310100T0_17, A.EHF310100T0_21, A.EHF310100T0_31" +						
			" FROM EHF310100T0 A " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03"))){	//櫃號
				sql += " and A.EHF310100T0_03 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_21"))){	//行動電話(產婦)
				sql += " and A.EHF310100T0_21 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_04"))){	//產婦姓名
				sql += " and A.EHF310100T0_04 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_31"))){	//地址
				sql += " and A.EHF310100T0_31 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_08_B")) && 
			   this.base_tools.existString((String)queryCondMap.get("EHF310100T0_08_E"))){	//訂餐日期(起、迄都有填)
				sql += " and A.EHF310100T0_08 between ? and ? ";
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_08_B"))){	//只填訂餐日期(起)
				sql += " and A.EHF310100T0_08 > ?";
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_08_E"))){	//只填訂餐日期(迄)
				sql += " and A.EHF310100T0_08 < ?";
			}
			
			sql += "" +
			" AND A.EHF310100T0_34 like ? " +  //公司代碼
			" ORDER BY A.EHF310100T0_02,A.EHF310100T0_03 ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_03"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_03"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_21"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_21"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_04"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF310100T0_04")+"%");
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_31"))){
				pstmt.setString(indexid, "%"+(String)queryCondMap.get("EHF310100T0_31")+"%");
				indexid++;
			}
			
			
			
			if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_08_B")) && 
			   this.base_tools.existString((String)queryCondMap.get("EHF310100T0_08_E"))){	//訂餐日期(起、迄都有填)
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_08_B"));
				indexid++;
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_08_E"));
				indexid++;
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_08_B"))){	//只填訂餐日期(起)
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_08_B"));
				indexid++;
			}else if(this.base_tools.existString((String)queryCondMap.get("EHF310100T0_08_E"))){	//只填訂餐日期(迄)
				pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_08_E"));
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
			" SELECT A.EHF310100T0_01, A.EHF310100T0_02, A.EHF310100T0_03, A.EHF310100T0_04, A.EHF310100T0_05, " +
			"		 DATE_FORMAT(A.EHF310100T0_06, '%Y/%m/%d') AS EHF310100T0_06," +
			"		 DATE_FORMAT(A.EHF310100T0_07, '%Y/%m/%d') AS EHF310100T0_07," +
			"		 DATE_FORMAT(A.EHF310100T0_08, '%Y/%m/%d') AS EHF310100T0_08, " +
			"		 A.EHF310100T0_09, " +
			"		 DATE_FORMAT(A.EHF310100T0_10, '%Y/%m/%d') AS EHF310100T0_10,  " +
			"		 A.EHF310100T0_11, A.EHF310100T0_12, " +
			"		 DATE_FORMAT(A.EHF310100T0_13, '%Y/%m/%d') AS EHF310100T0_13, " +
			"		 DATE_FORMAT(A.EHF310100T0_14, '%Y/%m/%d') AS EHF310100T0_14, " +
			"		 A.EHF310100T0_15, " +
			"		 A.EHF310100T0_16, A.EHF310100T0_17, A.EHF310100T0_18, " +
			"		 DATE_FORMAT(A.EHF310100T0_19, '%Y/%m/%d') AS EHF310100T0_19, " +
			"		 DATE_FORMAT(A.EHF310100T0_20, '%Y/%m/%d') AS EHF310100T0_20, " +
			"		 A.EHF310100T0_21, A.EHF310100T0_22, A.EHF310100T0_23, A.EHF310100T0_24, A.EHF310100T0_25, " +
			"		 A.EHF310100T0_26, A.EHF310100T0_27, A.EHF310100T0_28, A.EHF310100T0_29, A.EHF310100T0_30, " +
			"		 A.EHF310100T0_31, A.EHF310100T0_32, A.EHF310100T0_33, " +
			"        B.EHF310200T0_02, B.EHF310200T0_03, " +
			"		 C.EHF310500T0_02, C.EHF310500T0_03, C.EHF310500T0_04, C.EHF310500T0_05, C.EHF310500T0_06, " +
			"		 C.EHF310500T0_07, C.EHF310500T0_08 " +
			" FROM EHF310100T0 A " +
			" LEFT JOIN EHF310200T0 B ON A.EHF310100T0_01 = B.EHF310200T0_01 " +
			" LEFT JOIN EHF310500T0 C ON A.EHF310100T0_01 = C.EHF310500T0_01 AND A.EHF310100T0_34 = C.EHF310500T0_09 " +
			" WHERE 1=1 " +
			" AND A.EHF310100T0_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' " +			
			" AND A.EHF310100T0_34 = '"+(String)queryCondMap.get("COMP_ID")+"' " ;
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
		
		String sql_1 = "";
		String show_sql_1 = "";
		
		try{
			//Add EHF310100T0
			sql_1 = "" +
			" INSERT INTO EHF310100T0 " +
			" (EHF310100T0_01, EHF310100T0_02, EHF310100T0_03, EHF310100T0_04, EHF310100T0_05, EHF310100T0_06, " +
			"  EHF310100T0_07, EHF310100T0_08, EHF310100T0_09, EHF310100T0_10, EHF310100T0_11, EHF310100T0_12, " +
			"  EHF310100T0_13, EHF310100T0_14, EHF310100T0_15, EHF310100T0_16, EHF310100T0_17, EHF310100T0_18, " +
			"  EHF310100T0_19, EHF310100T0_20, EHF310100T0_21, EHF310100T0_22, EHF310100T0_23, EHF310100T0_24, " +
			"  EHF310100T0_25, EHF310100T0_26, EHF310100T0_27, EHF310100T0_28, EHF310100T0_29, EHF310100T0_30, " +
			"  EHF310100T0_31, EHF310100T0_32, EHF310100T0_33, EHF310100T0_34, " +
			"  USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, " +
			" ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt_1 = this.base_tools.getConn().prepareStatement(sql_1);
			P6PreparedStatement p6stmt_1 = new P6PreparedStatement(null, pstmt_1, null, sql_1);
			int indexid_1 = 1;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_01"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_02"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_03"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_04"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_05"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_06"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_07"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_08"));  //
			indexid_1++;
			p6stmt_1.setInt(indexid_1, (Integer)dataMap.get("EHF310100T0_09"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_10"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_11"));  //
			indexid_1++;
			p6stmt_1.setInt(indexid_1, (Integer)dataMap.get("EHF310100T0_12"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_13"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_14"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_15"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_16"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_17"));  //
			indexid_1++;
			p6stmt_1.setInt(indexid_1, (Integer)dataMap.get("EHF310100T0_18"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_19"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_20"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_21"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_22"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_23"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_24"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_25"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_26"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_27"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_28"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_29"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_30"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_31"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_32")==null?"":(String)dataMap.get("EHF310100T0_32"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_33")==null?"":(String)dataMap.get("EHF310100T0_33"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));  //資料建立者
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid_1++;
			
			//System.out.println(p6stmt_1.getQueryFromPreparedStatement());
			show_sql_1 = p6stmt_1.getQueryFromPreparedStatement();
			//執行
			p6stmt_1.executeUpdate();
			
			//客戶需求單單號
			String SysNo = (String)dataMap.get("EHF310100T0_01");
			
			//更新資料庫
			this.base_tools.commit();

			p6stmt_1.close();
			pstmt_1.close();
			
			//回寫 員工系統代碼
			dataMap.put("KEY_ID", SysNo);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF310100().addData()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100.addData()", show_sql_1+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		try{
			//AddDetail
			if("EHF310100T2".equals(detailType)){
				//訂餐資訊-養生飲品明細
				this.addEHF310100T2(detailDataMap);
			}else if("EHF310100T1".equals(detailType)){
				//訂餐資訊-每日明細
				this.addEHF310100T1(detailDataMap);
			}else if("EHF310200T1".equals(detailType)){
				//訂餐特殊資訊-不吃食物
				this.addEHF310200T1(detailDataMap);
			}else if("EHF310200T2".equals(detailType)){
				//訂餐特殊資訊-不喝飲品
				this.addEHF310200T2(detailDataMap);
			}else if("EHF310200T3".equals(detailType)){
				//訂餐特殊資訊-特殊需求
				this.addEHF310200T3(detailDataMap);
			}else if("EHF310200T4".equals(detailType)){
				//訂餐特殊資訊-特殊口味
				this.addEHF310200T4(detailDataMap);
			}else if("EHF310200T5".equals(detailType)){
				//訂餐特殊資訊-清淡去油
				this.addEHF310200T5(detailDataMap);
			}else if("EHF310300T0".equals(detailType)){
				//電訪紀錄
				this.addEHF310300T0(detailDataMap);
			}else if("EHF310400T1".equals(detailType)){
				//付款資訊
				this.addEHF310400T1(detailDataMap);
			}else if("EHF310500T0".equals(detailType)){
				//贈品資訊
				this.addEHF310500T0(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private void addEHF310500T0(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			int EHF310500T0 = 
				this.base_tools.getMaxSN("EHF310500T0_02", "EHF310500T0", 
						" AND EHF310500T0_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " );
			
			sql = "" +
			" INSERT INTO EHF310500T0 " +
			" (  EHF310500T0_01, EHF310500T0_02, EHF310500T0_03, EHF310500T0_04, EHF310500T0_05," +
			"	 EHF310500T0_06, EHF310500T0_07, EHF310500T0_08, EHF310500T0_09, " +
			"	 USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES ( ?, ?, ?, ?, ?," +
			"		   ?, ?, ?, ?," +
			"		   ?, ?, 1, NOW(),NOW() ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310500T0);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, "");  //預留欄位
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310500T0_04"));  //領取日期
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310500T0_05")==null?"":(String)detailDataMap.get("EHF310500T0_05"));  //開單日期
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310500T0_06"));  //贈品類別
			indexid++;
			p6stmt.setString(indexid, "");  //預留欄位
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310500T0_08")==null?"":(String)detailDataMap.get("EHF310500T0_08"));  //備註
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //建立人員
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310500T0()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310500T0()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310400T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{			
			
			int EHF310400T1 = 
				this.base_tools.getMaxSN("EHF310400T1_02", "EHF310400T1", 
						" AND EHF310400T1_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " );
			
			sql = "" +
			" INSERT INTO EHF310400T1 " +
			" (  EHF310400T1_01, EHF310400T1_02, EHF310400T1_03, EHF310400T1_04, EHF310400T1_05," +
			"	 EHF310400T1_06, EHF310400T1_07, EHF310400T1_08, EHF310400T1_09, EHF310400T1_10, " +
			"	 EHF310400T1_11, EHF310400T1_12, EHF310400T1_13, EHF310400T1_14, EHF310400T1_15," +
			"	 USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES ( ?, ?, ?, ?, ?," +
			"		   ?, ?, ?, ?, ?," +
			"		   ?, ?, ?, ?, ?," +
			"		   ?, ?, 1, NOW(),NOW() ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310400T1);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_03"));  //開單日期
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_04"));  //開單人員
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_05_TXT"));  //經手人員
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_06"));  //付款方式
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_07"));  //付款類別
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_08"));  //預計收款日
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_09"));  //預計金額
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_10"));  //實際收款日
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_11"));  //實際金額
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310400T1_12"));  //備註
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //確認
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //刪除
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //建立人員
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310400T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310400T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310300T0(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{			
			
			int EHF310300T0 = 
				this.base_tools.getMaxSN("EHF310300T0_02", "EHF310300T0", 
						" AND EHF310300T0_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " );
			
			sql = "" +
			" INSERT INTO EHF310300T0 " +
			" (  EHF310300T0_01, EHF310300T0_02, EHF310300T0_03, EHF310300T0_04, EHF310300T0_05," +
			"	 EHF310300T0_06, EHF310300T0_07, USER_CREATE, USER_UPDATE," +
			"	 VERSION, DATE_CREATE, DATE_UPDATE ) " +
			" VALUES ( ?, ?, ?, ?, ?," +
			"		   ?, ?, ?, ?," +
			"		   1, NOW(),NOW() ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310300T0);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310300T0_03"));  //電訪人員
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310300T0_04"));  //電訪日期
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310300T0_05"));  //電訪內容
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310300T0_06"));  //備註
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //建立人員
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310300T0()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310300T0()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310200T5(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{			
			
			int EHF310200T5 = 
				this.base_tools.getMaxSN("EHF310200T5_02", "EHF310200T5", 
						" AND EHF310200T5_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " );
			
			sql = "" +
			" INSERT INTO EHF310200T5 " +
			" (  EHF310200T5_01, EHF310200T5_02, EHF310200T5_03  ) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310200T5);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T5_03"));  //特殊口味
			indexid++;
			
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310200T5()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310200T5()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310200T4(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{			
			
			int EHF310200T4 = 
				this.base_tools.getMaxSN("EHF310200T4_02", "EHF310200T4", 
						" AND EHF310200T4_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " );
			
			sql = "" +
			" INSERT INTO EHF310200T4 " +
			" (  EHF310200T4_01, EHF310200T4_02, EHF310200T4_03  ) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310200T4);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T4_03"));  //特殊口味
			indexid++;
			
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310200T4()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310200T4()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310200T3(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{			
			
			int EHF310200T3 = 
				this.base_tools.getMaxSN("EHF310200T3_02", "EHF310200T3", 
						" AND EHF310200T3_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " );
			
			sql = "" +
			" INSERT INTO EHF310200T3 " +
			" (  EHF310200T3_01, EHF310200T3_02, EHF310200T3_03  ) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310200T3);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T3_03"));  //特殊需求
			indexid++;
			
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310200T3()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310200T3()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310200T2(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
//		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{			
			
			int EHF310200T2 = 
				this.base_tools.getMaxSN("EHF310200T2_02", "EHF310200T2", 
						" AND EHF310200T2_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " );
			
			sql = "" +
			" INSERT INTO EHF310200T2 " +
			" (  EHF310200T2_01, EHF310200T2_02, EHF310200T2_03  ) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310200T2);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T2_03"));  //不喝飲品
			indexid++;
			
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310200T2()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310200T2()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310200T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
//		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{			
			
			int EHF310200T1_02 = 
				this.base_tools.getMaxSN("EHF310200T1_02", "EHF310200T1", 
						" AND EHF310200T1_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " );
			
			sql = "" +
			" INSERT INTO EHF310200T1 " +
			" (  EHF310200T1_01, EHF310200T1_02, EHF310200T1_03  ) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310200T1_02);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T1_03_type")+"/"+(String)detailDataMap.get("EHF310200T1_03_detail"));  //不吃食物
			indexid++;
			
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310200T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310200T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310100T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			
			sql = "" +
			" INSERT INTO EHF310100T1 " +
			" ( " +
			" EHF310100T1_01, EHF310100T1_02, EHF310100T1_03, EHF310100T1_04, " +
			" EHF310100T1_05, EHF310100T1_06, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +			
			" ) " +
			" VALUES ( " +
			" ?, ?, ?, ?, " +
			" ?, ?, " +
			" ?, ?, 1, NOW(), NOW() " +			
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T1_02"));  //日期
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T1_03"));  //餐點類別
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T1_04"));  //路線
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)detailDataMap.get("EHF310100T1_05")));  //素食
			indexid++;		
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;			
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //資料建立人員
			indexid++;			
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //最後異動人員
			indexid++;	
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310100T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310100T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF310100T2(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			
			int EHF310100T2_02 = 
			this.base_tools.getMaxSN("EHF310100T2_02", "EHF310100T2", 
					" AND EHF310100T2_01 = '"+(String)detailDataMap.get("EHF310100T0_01")+"' " +
					" AND EHF310100T2_07 = '"+(String)detailDataMap.get("COMP_ID")+"' ");
			
			
			
			
			sql = "" +
			" INSERT INTO EHF310100T2 " +
			" ( " +
			" EHF310100T2_01, EHF310100T2_02, EHF310100T2_03, EHF310100T2_04, " +
			" EHF310100T2_05, EHF310100T2_06, EHF310100T2_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +			
			" ) " +
			" VALUES ( " +
			" ?, ?, ?, ?, " +
			" ?, ?, ?, " +
			" ?, ?, 1, NOW(), NOW() " +			
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, EHF310100T2_02);  //順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T2_03"));  //日期起
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T2_04"));  //日期迄
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T2_05"));  //餐點類別
			indexid++;
			
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T2_06"));  //養生飲品
			
			indexid++;			
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;			
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //資料建立人員
			indexid++;			
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //最後異動人員
			indexid++;	
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF310100().addEHF310100T2()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100().addEHF310100T2()", show_sql+", "+e.getMessage(), 
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
			if("EHF310100T2".equals(detailType)){
				//訂餐資訊-養生飲品明細
				this.delEHF310100T2(detailDataMap);
			}else if("EHF310100T1".equals(detailType)){
				//訂餐資訊-每日明細
				this.delEHF310100T1(detailDataMap);
			}else if("EHF310200T1".equals(detailType)){
				//訂餐特殊資訊-不吃食物
				this.delEHF310200T1(detailDataMap);
			}else if("EHF310200T2".equals(detailType)){
				//訂餐特殊資訊-不喝飲品
				this.delEHF310200T2(detailDataMap);
			}else if("EHF310200T3".equals(detailType)){
				//訂餐特殊資訊-特殊需求
				this.delEHF310200T3(detailDataMap);
			}else if("EHF310200T4".equals(detailType)){
				//訂餐特殊資訊-特殊口味
				this.delEHF310200T4(detailDataMap);
			}else if("EHF310200T5".equals(detailType)){
				//訂餐特殊資訊-清淡去油
				this.delEHF310200T5(detailDataMap);
			}else if("EHF310300T0".equals(detailType)){
				//電訪紀錄
				this.delEHF310300T0(detailDataMap);
			}else if("EHF310500T0".equals(detailType)){
				//贈品資訊
				this.delEHF310500T0(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	private void delEHF310500T0(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";

		try{
			sql = " DELETE FROM EHF310500T0 " +
			  "  WHERE EHF310500T0_01=? " +
			  "    AND EHF310500T0_02=? " ;
		
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
				pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310500T0_02"));  //順序編號
			indexid++;
			show_sql = p6stmt.getQueryFromPreparedStatement();
//				System.out.println("show_sql:"+show_sql);
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310500T0()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			//重產養生飲品設定的"順序號碼"
			this.ReNumbers("EHF310300T0",(String)detailDataMap.get("EHF310100T0_01"));
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310500T0()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF310300T0(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";

		try{
			sql = " DELETE FROM EHF310300T0 " +
				  "  WHERE EHF310300T0_01=? " +
				  "    AND EHF310300T0_02=? " ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setInt(indexid, (Integer)detailDataMap.get("EHF310300T0_02"));  //順序編號
			indexid++;
			show_sql = p6stmt.getQueryFromPreparedStatement();
//				System.out.println("show_sql:"+show_sql);
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310300T0()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			//重產養生飲品設定的"順序號碼"
			this.ReNumbers("EHF310300T0",(String)detailDataMap.get("EHF310100T0_01"));
			
			
			
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310300T0()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF310200T5(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";

		try{
			sql = " DELETE FROM EHF310200T5 " +
				  "  WHERE EHF310200T5_01=? " +
				  "    AND EHF310200T5_02=? " ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T5_02"));  //順序編號
			indexid++;
			show_sql = p6stmt.getQueryFromPreparedStatement();
//				System.out.println("show_sql:"+show_sql);
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T5()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			//重產養生飲品設定的"順序號碼"
			this.ReNumbers("EHF310200T5",(String)detailDataMap.get("EHF310100T0_01"));
			
			
			
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T5()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF310200T4(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";

		try{
			sql = " DELETE FROM EHF310200T4 " +
				  "  WHERE EHF310200T4_01=? " +
				  "    AND EHF310200T4_02=? " ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T4_02"));  //順序編號
			indexid++;
			show_sql = p6stmt.getQueryFromPreparedStatement();
//				System.out.println("show_sql:"+show_sql);
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T4()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			//重產養生飲品設定的"順序號碼"
			this.ReNumbers("EHF310200T4",(String)detailDataMap.get("EHF310100T0_01"));
			
			
			
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T4()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF310200T3(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";

		try{
			sql = " DELETE FROM EHF310200T3 " +
				  "  WHERE EHF310200T3_01=? " +
				  "    AND EHF310200T3_02=? " ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T3_02"));  //順序編號
			indexid++;
			show_sql = p6stmt.getQueryFromPreparedStatement();
//				System.out.println("show_sql:"+show_sql);
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T3()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			//重產養生飲品設定的"順序號碼"
			this.ReNumbers("EHF310200T3",(String)detailDataMap.get("EHF310100T0_01"));
			
			
			
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T3()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF310200T2(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();

		try{
			sql = " DELETE FROM EHF310200T2 " +
				  "  WHERE EHF310200T2_01=? " +
				  "    AND EHF310200T2_02=? " ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T2_02"));  //順序編號
			indexid++;
			show_sql = p6stmt.getQueryFromPreparedStatement();
//				System.out.println("show_sql:"+show_sql);
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T2()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			//重產養生飲品設定的"順序號碼"
			this.ReNumbers("EHF310200T2",(String)detailDataMap.get("EHF310100T0_01"));
			
			
			
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T2()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF310200T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";

		try{
			sql = " DELETE FROM EHF310200T1 " +
				  "  WHERE EHF310200T1_01=? " +
				  "    AND EHF310200T1_02=? " ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310200T1_02"));  //順序編號
			indexid++;
			show_sql = p6stmt.getQueryFromPreparedStatement();
//				System.out.println("show_sql:"+show_sql);
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			
			//重產養生飲品設定的"順序號碼"
			this.ReNumbers("EHF310200T1",(String)detailDataMap.get("EHF310100T0_01"));
			
			
			
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310200T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF310100T1(Map detailDataMap) {
		// TODO Auto-generated method stub
			
		String sql = "";
		String show_sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{	
			sql = " DELETE FROM EHF310100T1 " +
				  "  WHERE EHF310100T1_01=? " +
				  "    AND EHF310100T1_02=? " +
				  "	   AND EHF310100T1_06=? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T1_02"));  //日期
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;

			show_sql = p6stmt.getQueryFromPreparedStatement();

			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.getConn().commit();

			p6stmt.close();
			pstmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310100T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310100T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	
	}

	private void delEHF310100T2(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		try{
			
			PreparedStatement pstmt = null;
			P6PreparedStatement p6stmt = null;
			for(int i = 0 ; i < (detailDataMap.get("EHF310100T2_02").toString().split(" ")).length ; i++){
				
				sql = " DELETE FROM EHF310100T2 " +
					  "  WHERE EHF310100T2_01=? " +
					  "    AND EHF310100T2_02=? " ;
				
				//執行SQL
				pstmt = this.base_tools.getConn().prepareStatement(sql);
				p6stmt = new P6PreparedStatement(null,
						pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, (String)detailDataMap.get("EHF310100T0_01"));  //系統編碼
				indexid++;
				p6stmt.setString(indexid, detailDataMap.get("EHF310100T2_02").toString().split(" ")[i]);  //順序編號
				indexid++;
				show_sql = p6stmt.getQueryFromPreparedStatement();
//				System.out.println("show_sql:"+show_sql);
				//執行
				p6stmt.executeUpdate();
				
				//更新資料庫
				this.base_tools.getConn().commit();

				
				
				//記錄細項新增訊息
				this.base_tools.writeDELETEMSG("EHF310100().delEHF310100T2()", show_sql, 
								detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
								detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			}
			if(p6stmt!=null){
				p6stmt.close();
			}
			if(pstmt!=null){
				pstmt.close();
			}
			//重產養生飲品設定的"順序號碼"
			this.ReNumbers("EHF310100T2",(String)detailDataMap.get("EHF310100T0_01"));
			
			
			
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeDELETEMSG("EHF310100().delEHF310100T2()", show_sql+", "+e.getMessage(), 
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

	public void addDataSpecial(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql_1 = "";
		String show_sql_1 = "";
		
		try{
			//Add EHF310200T0
			sql_1 = "" +
			" INSERT INTO EHF310200T0 " +
			" (EHF310200T0_01, EHF310200T0_02, EHF310200T0_03) " +
			" VALUES ( ?, ?, ? ) ";
			
			//執行SQL
			PreparedStatement pstmt_1 = this.base_tools.getConn().prepareStatement(sql_1);
			P6PreparedStatement p6stmt_1 = new P6PreparedStatement(null, pstmt_1, null, sql_1);
			int indexid_1 = 1;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_01"));  //
			indexid_1++;
			p6stmt_1.setBoolean(indexid_1, tools.StringToBoolean((String)dataMap.get("EHF310200T0_02")));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310200T0_03"));  //
			indexid_1++;
			
			
			//System.out.println(p6stmt_1.getQueryFromPreparedStatement());
			show_sql_1 = p6stmt_1.getQueryFromPreparedStatement();
			//執行
			p6stmt_1.executeUpdate();
			
			//客戶需求單單號
			String SysNo = (String)dataMap.get("EHF310100T0_01");
			
			//更新資料庫
			this.base_tools.commit();

			p6stmt_1.close();
			pstmt_1.close();
			
			//回寫 員工系統代碼
			dataMap.put("KEY_ID", SysNo);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF310100().addDataSpecial()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100.addDataSpecial()", show_sql_1+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	public void addDataGifts(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql_1 = "";
		String show_sql_1 = "";
		
		try{
			//Add EHF310500T0
			sql_1 = "" +
			" INSERT INTO EHF310500T0 " +
			" (EHF310500T0_01, EHF310500T0_02, EHF310500T0_03, EHF310500T0_04, " +
			"  EHF310500T0_05, EHF310500T0_06, EHF310500T0_07, EHF310500T0_08, EHF310500T0_09, " +
			"  USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, " +
			"		   ?, ?, ?, ?, ?, " +
			"          ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt_1 = this.base_tools.getConn().prepareStatement(sql_1);
			P6PreparedStatement p6stmt_1 = new P6PreparedStatement(null, pstmt_1, null, sql_1);
			int indexid_1 = 1;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_01"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310500T0_02")==null?"":(String)dataMap.get("EHF310500T0_02"));  //
			indexid_1++;
			p6stmt_1.setBoolean(indexid_1, tools.StringToBoolean("0"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310500T0_04")==null?"":(String)dataMap.get("EHF310500T0_04"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310500T0_05")==null?"":(String)dataMap.get("EHF310500T0_05"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310500T0_06")==null?"":(String)dataMap.get("EHF310500T0_06"));  //
			indexid_1++;
			p6stmt_1.setInt(indexid_1, 0);  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310500T0_08")==null?"":(String)dataMap.get("EHF310500T0_08"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("COMP_ID"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));  //
			indexid_1++;
			
			
			//System.out.println(p6stmt_1.getQueryFromPreparedStatement());
			show_sql_1 = p6stmt_1.getQueryFromPreparedStatement();
			//執行
			p6stmt_1.executeUpdate();
			
			//客戶需求單單號
			String SysNo = (String)dataMap.get("EHF310100T0_01");
			
			//更新資料庫
			this.base_tools.commit();

			p6stmt_1.close();
			pstmt_1.close();
			
			//回寫 員工系統代碼
			dataMap.put("KEY_ID", SysNo);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF310100().addDataSpecial()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100.addDataSpecial()", show_sql_1+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	public List queryEHF310100T2_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310100T2_List = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT *, " +	
			" DATE_FORMAT(EHF310100T2_03, '%Y/%m/%d') AS EHF310100T2_03, " +
			" DATE_FORMAT(EHF310100T2_04, '%Y/%m/%d') AS EHF310100T2_04 " +
			" FROM EHF310100T2 " +
			" WHERE 1=1 " +
			" AND EHF310100T2_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' " +
			" AND EHF310100T2_07 = '"+(String)queryCondMap.get("COMP_ID")+"' " +
			" ORDER BY EHF310100T2_03,EHF310100T2_04,EHF310100T2_05" ;
			
			EHF310100T2_List = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF310100T2_List;
	}

	public List queryEHF310100T1_List(Map queryCondMap, Map dateMap) {
		// TODO Auto-generated method stub
		List EHF310100T1_List = new ArrayList();
		
		String sql = " SELECT *, " +
					 " 		  DATE_FORMAT(EHF310100T1_02, '%Y/%m/%d') AS EHF310100T1_02 " +
					 "   FROM EHF310100T1 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310100T1_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' " +
					 "    AND EHF310100T1_02 = '"+(String)dateMap.get("EHF310100T1_02")+"' " +
					 "    AND EHF310100T1_06 = '"+(String)queryCondMap.get("COMP_ID")+"' ";
		
		EHF310100T1_List = this.base_tools.queryList(sql);
		
		return EHF310100T1_List;
	}

	public List queryEHF310200T1_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310200T1_List = new ArrayList();
		
		String sql = " SELECT * " +
					 "   FROM EHF310200T1 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310200T1_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' ";
			
		EHF310200T1_List = this.base_tools.queryList(sql);


		return EHF310200T1_List;
	}

	public List queryEHF310200T2_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310200T2_List = new ArrayList();
		
		String sql = " SELECT * " +
					 "   FROM EHF310200T2 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310200T2_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' ";
			
		EHF310200T2_List = this.base_tools.queryList(sql);

		return EHF310200T2_List;
	}

	public List queryEHF310200T3_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310200T3_List = new ArrayList();
		
		String sql = " SELECT * " +
					 "   FROM EHF310200T3 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310200T3_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' ";
			
		EHF310200T3_List = this.base_tools.queryList(sql);
			
		return EHF310200T3_List;
	}

	public List queryEHF310200T4_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310200T4_List = new ArrayList();

		String sql = " SELECT * " +
					 "   FROM EHF310200T4 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310200T4_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' ";
			
		EHF310200T4_List = this.base_tools.queryList(sql);
			
		return EHF310200T4_List;
	}

	public List queryEHF310200T5_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310200T5_List = new ArrayList();

		String sql = " SELECT * " +
					 "   FROM EHF310200T5 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310200T5_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' ";
			
		EHF310200T5_List = this.base_tools.queryList(sql);
			
		return EHF310200T5_List;
	}
	
	public List queryEHF310500T0_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF310500T0_List = new ArrayList();

		String sql = " SELECT * " +
					 "   FROM EHF310500T0 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310500T0_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' ";
			
		EHF310500T0_List = this.base_tools.queryList(sql);
			
		return EHF310500T0_List;
	}

	public int getOptionCount() {
		// TODO Auto-generated method stub
		int count = 0;
		try{
			String comp_id = this.base_tools.getSta_comp_id();
			String sql = "" +
			" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = 'Drink' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				count++;
			}
			rs.close();
			stmt.close();
		}catch(Exception e){
			
		}
		
		return count;
	}

	public List queryEHF310100T1_02_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310100T1_02_List = new ArrayList();
		
		String sql = " SELECT DATE_FORMAT(EHF310100T1_02, '%Y/%m/%d') AS EHF310100T1_02 " +
					 "   FROM EHF310100T1 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310100T1_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' " +
					 "    AND EHF310100T1_06 = '"+(String)queryCondMap.get("COMP_ID")+"' " +
			 		 "  GROUP BY EHF310100T1_02 " +
			 		 "  ORDER BY EHF310100T1_02 ";
		
		EHF310100T1_02_List = this.base_tools.queryList(sql);
		
		return EHF310100T1_02_List;
	}

	public Map getOrderMap(String ehf310100t0_01, String orderDate,
			String comp_id) {
		// TODO Auto-generated method stub
		Map dataMap = new HashMap();
		int countA = 0;
		int countB = 0;
		int countC = 0;
		try{
			String sql = " SELECT *, DATE_FORMAT(EHF310100T1_02, '%Y/%m/%d') AS EHF310100T1_02 " +
						 "   FROM EHF310100T1 " +
						 "  WHERE 1=1 " +
						 "    AND EHF310100T1_01 = '"+ehf310100t0_01+"' " ;
						 
			if(!"".equals(orderDate)){
				sql+="    AND EHF310100T1_02 = '"+orderDate+"' " ;
			}
						 
			sql+="    AND EHF310100T1_06 = '"+comp_id+"' " ;
			
//			if(!"".equals(orderDate)){
//				sql+="  GROUP BY EHF310100T1_02 " ;
//			}
				 
			sql+="  ORDER BY EHF310100T1_02 ";
			
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				if(!"".equals(orderDate)){
					if("A".equals(rs.getString("EHF310100T1_03"))){
						dataMap.put("EHF310100T1_03_A", rs.getString("EHF310100T1_03"));
						dataMap.put("EHF310100T1_04_A", rs.getString("EHF310100T1_04"));
						dataMap.put("EHF310100T1_05_A", rs.getString("EHF310100T1_05"));
					}
					
					if("B".equals(rs.getString("EHF310100T1_03"))){
						dataMap.put("EHF310100T1_03_B", rs.getString("EHF310100T1_03"));
						dataMap.put("EHF310100T1_04_B", rs.getString("EHF310100T1_04"));
						dataMap.put("EHF310100T1_05_B", rs.getString("EHF310100T1_05"));
					}
					
					if("C".equals(rs.getString("EHF310100T1_03"))){
						dataMap.put("EHF310100T1_03_C", rs.getString("EHF310100T1_03"));
						dataMap.put("EHF310100T1_04_C", rs.getString("EHF310100T1_04"));
						dataMap.put("EHF310100T1_05_C", rs.getString("EHF310100T1_05"));
					}
				}else{
					if("A".equals(rs.getString("EHF310100T1_03"))){
						countA++;
					}
					
					if("B".equals(rs.getString("EHF310100T1_03"))){
						countB++;
					}
					
					if("C".equals(rs.getString("EHF310100T1_03"))){
						countC++;
					}
				}
				
				
			}
			rs.close();
			stmt.close();
			if(!"".equals(orderDate)){
				if(!dataMap.containsKey("EHF310100T1_03_A")){
					dataMap.put("EHF310100T1_03_A", "");
					dataMap.put("EHF310100T1_04_A", "");
					dataMap.put("EHF310100T1_05_A", "");
				}
				if(!dataMap.containsKey("EHF310100T1_03_B")){
					dataMap.put("EHF310100T1_03_B", "");
					dataMap.put("EHF310100T1_04_B", "");
					dataMap.put("EHF310100T1_05_B", "");
				}
				if(!dataMap.containsKey("EHF310100T1_03_C")){
					dataMap.put("EHF310100T1_03_C", "");
					dataMap.put("EHF310100T1_04_C", "");
					dataMap.put("EHF310100T1_05_C", "");
				}
			}else{
				dataMap.put("EHF310400T2_03_A", countA);
				dataMap.put("EHF310400T2_03_B", countB);
				dataMap.put("EHF310400T2_03_C", countC);
			}
			
		}catch(Exception e){
			
		}
		
		return dataMap;
	}

	public Map getMENU_MEAL(String MENU_MEAL, String comp_id) {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();
		try{
			
			String sql = "" +
			" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = '"+MENU_MEAL+"' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
					
				returnMap.put(rs.getString("ITEM_ID"), rs.getString("ITEM_VALUE"));
				returnMap.put(rs.getString("ITEM_VALUE"), rs.getString("ITEM_ID"));
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return returnMap;
	}
	
	public void ReNumbers(String table, String EHF310100T0_01) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//重新訂單明細資料的 - "順序號碼"
			sql = "" +
			" SELECT "+table+"_01, "+table+"_02 " +
			" FROM "+table+" " +
			" WHERE 1=1 " +
			" AND "+table+"_01 = '"+EHF310100T0_01+"' " +
			" ORDER BY "+table+"_02 ";
			//取得訂單明細資料清單
			List t1 = this.base_tools.queryList(sql);
			Iterator it = t1.iterator();
			Map tempMap  = null;
			int snCount = 1;
			List sql_list = new ArrayList();
			//針對每一筆訂單明細資料清單做順序號碼的處裡
			while(it.hasNext()){
				tempMap = (Map) it.next();
				sql = "" +
				" UPDATE "+table+" SET " +
				" "+table+"_02 = '"+snCount+"' " +
				" WHERE 1=1 " +
				" AND "+table+"_01 = '"+(String)tempMap.get(table+"_01")+"' " +  //
				" AND "+table+"_02 = '"+(Integer)tempMap.get(table+"_02")+"' ";  //順序號碼
				sql_list.add(sql);
				snCount++;
			}
			this.base_tools.executeBatchSQL(sql_list);
			
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public List getcheckboxes(boolean emptyOption, String classKey, String comp_id ) {
		
		List optionlist = new ArrayList();
			
		try{
				
			String sql = "" +
			" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = '"+classKey+"' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
							
			while(rs.next()){
				
				optionlist.add(rs.getString("ITEM_ID"));
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return optionlist;
	}

	public Map getFoodType(String comp_id) {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();
		try{
			
			String sql = "" +
			" SELECT PSFOODT0_01 AS ITEM_ID, PSFOODT0_04 AS ITEM_VALUE " +
			" FROM FOODT0 " +
			" WHERE 1=1 " +
			" AND PSFOODT0_07 = '1' " +
			" AND PSFOODT0_08 = '"+comp_id+"' ";		

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
					
				returnMap.put(rs.getString("ITEM_ID"), rs.getString("ITEM_VALUE"));
				returnMap.put(rs.getString("ITEM_VALUE"), rs.getString("ITEM_ID"));
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return returnMap;
	}

	public Map getFoodDetail(String comp_id) {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();
		try{
			
			String sql = "" +
			" SELECT PSFOODT1_04 AS ITEM_ID, PSFOODT1_05 AS ITEM_VALUE " +
			" FROM FOODT1 " +
			" WHERE 1=1 " +
			" AND PSFOODT1_08 = '1' " +
			" AND PSFOODT1_09 = '"+comp_id+"' ";	

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
					
				returnMap.put(rs.getString("ITEM_ID"), rs.getString("ITEM_VALUE"));
				returnMap.put(rs.getString("ITEM_VALUE"), rs.getString("ITEM_ID"));
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return returnMap;
	}

	public Map getTea(String comp_id) {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();
		try{
			
			String sql = "" +
			" SELECT EHF300400T0_03 AS ITEM_ID, EHF300400T0_04 AS ITEM_VALUE " +
			" FROM EHF300400T0 " +
			" WHERE 1=1 " +
			" AND EHF300400T0_05 = '1' " +
			" AND EHF300400T0_06 = '"+comp_id+"' ";		

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
					
				returnMap.put(rs.getString("ITEM_ID"), rs.getString("ITEM_VALUE"));
				returnMap.put(rs.getString("ITEM_VALUE"), rs.getString("ITEM_ID"));
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return returnMap;
	}
	/**
	* 取得下拉選單
	* @param num
	* @param pattern
	* @return
	*/
	public Map getOptions(String classKey, String comp_id ){
			
		Map returnMap = new HashMap();
			
		try{
				
			String sql = "" +
			" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = '"+classKey+"' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

				
			while(rs.next()){
					
				returnMap.put(rs.getString("ITEM_ID"), rs.getString("ITEM_VALUE"));
				returnMap.put(rs.getString("ITEM_VALUE"), rs.getString("ITEM_ID"));
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return returnMap;
	}

	public List queryEHF310300T0_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310300T0_List = new ArrayList();

		String sql = " SELECT * " +
					 "   FROM EHF310300T0 " +
					 "  WHERE 1=1 " +
					 "    AND EHF310300T0_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' " +
					 " ORDER BY EHF310300T0_04 ";
			
		EHF310300T0_List = this.base_tools.queryList(sql);
			
		return EHF310300T0_List;
	}

	public Map getOrderDays(String KEY_ID, String COMP_ID) {
		// TODO Auto-generated method stub
		
		Map returnMap = new HashMap();
		int days = 0;
		String date = "" ;
		
		try{
			String sql = "" +
						 " SELECT EHF310100T0_09, DATE_FORMAT(EHF310100T0_10, '%Y/%m/%d') AS EHF310100T0_10 " +
						 " FROM EHF310100T0 " +
						 " WHERE 1=1 " +
						 " AND EHF310100T0_01 = '"+KEY_ID+"' " +
						 " AND EHF310100T0_34 = '"+COMP_ID+"' ";		
			
			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
				days = Integer.parseInt(rs.getString("EHF310100T0_09"));
				date = rs.getString("EHF310100T0_10");
				returnMap.put("days", days);
				returnMap.put("date", date);
			}
				
			rs.close();
			stmt.close();
		}catch(Exception e){
			
		}
		return returnMap;
	}

	public Map getCallMap(String KEY_ID, String ehf310300t0_02,
			String COMP_ID) {
		// TODO Auto-generated method stub
		
		Map returnMap = new HashMap();
		
		try{
			String sql = "" +
						 " SELECT EHF310300T0_05, EHF310300T0_06 " +
						 " FROM EHF310300T0 " +
						 " WHERE 1=1 " +
						 " AND EHF310300T0_01 = '"+KEY_ID+"' " +
						 " AND EHF310300T0_02='"+ehf310300t0_02+"' " +
						 " AND EHF310300T0_07 = '"+COMP_ID+"' ";		
			
			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
				returnMap.put("EHF310300T0_05", rs.getString("EHF310300T0_05"));
				returnMap.put("EHF310300T0_06", rs.getString("EHF310300T0_06"));
			}
				
			rs.close();
			stmt.close();
		}catch(Exception e){
			System.out.println("取得修改前電訪紀錄時錯誤");
		}
		
		return returnMap;
	}

	public void editPayInf(String comp_id, Map orderData) {
		// TODO Auto-generated method stub
		Map resultMap = new HashMap();
		try{
			String sql = "" +
						 " SELECT * " +
						 " FROM EHF310400T2 " +
						 " WHERE 1=1 " +
						 " AND EHF310400T2_01 = '"+(String)orderData.get("EHF310100T0_01")+"'" ;		
			
			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
				
				if("P01".equals(rs.getString("EHF310400T2_02"))){
					resultMap.put("P01", "yes");
				}else if("P02".equals(rs.getString("EHF310400T2_02"))){
					resultMap.put("P02", "yes");
				}else if("P03".equals(rs.getString("EHF310400T2_02"))){
					resultMap.put("P03", "yes");
				}else if("P04".equals(rs.getString("EHF310400T2_02"))){
					resultMap.put("P04", "yes");
				}else if("P05".equals(rs.getString("EHF310400T2_02"))){
					resultMap.put("P05", "yes");
				}
				
			}
			if("yes".equals((String)resultMap.get("P01"))){
				//代表已有資料→update
				this.updateEHF310400T2(orderData,"P01");
			}else{
				//代表尚無資料→insert
				this.addEHF310400T2(orderData,"P01");
			}
			if("yes".equals((String)resultMap.get("P02"))){
				//代表已有資料→update
				this.updateEHF310400T2(orderData,"P02");
			}else{
				//代表尚無資料→insert
				this.addEHF310400T2(orderData,"P02");
			}
			if("yes".equals((String)resultMap.get("P03"))){
				//代表已有資料→update
				this.updateEHF310400T2(orderData,"P03");
			}else{
				//代表尚無資料→insert
				this.addEHF310400T2(orderData,"P03");
			}
			if((Integer)orderData.get("days")<16){
				if("yes".equals((String)resultMap.get("P04"))){
					//代表已有資料→update
					this.updateEHF310400T2(orderData,"P04");
				}else{
					//代表尚無資料→insert
					this.addEHF310400T2(orderData,"P04");
				}
			}else if((Integer)orderData.get("days")>=16){
				if("yes".equals((String)resultMap.get("P05"))){
					//代表已有資料→update
					this.updateEHF310400T2(orderData,"P05");
				}else{
					//代表尚無資料→insert
					this.addEHF310400T2(orderData,"P05");
				}
			}
			
			
				
			rs.close();
			stmt.close();
		}catch(Exception e){
			System.out.println("編輯付款資訊2時錯誤");
		}
		
	}

	private void updateEHF310400T2(Map orderData, String type) {
		// TODO Auto-generated method stub
		
		try{
			String sql = " UPDATE EHF310400T2 SET EHF310400T2_03=? " +
						 " WHERE EHF310400T2_02=? AND EHF310400T2_01=? ";
			
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,sql);
			int indexid = 1;
			if("P01".equals(type)){
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_A"));  	//數量
				indexid++;
				p6stmt.setString(indexid, "P01");	//定價代碼
				indexid++;
			}else if("P02".equals(type)){
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_B"));  	//數量
				indexid++;
				p6stmt.setString(indexid, "P02");	//定價代碼
				indexid++;
			}else if("P03".equals(type)){
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_C"));  	//數量
				indexid++;
				p6stmt.setString(indexid, "P03");	//定價代碼
				indexid++;
			}else if("P04".equals(type)){
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_All"));  	//數量
				indexid++;
				p6stmt.setString(indexid, "P04");	//定價代碼
				indexid++;
			}else if("P05".equals(type)){
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_All"));  	//數量
				indexid++;
				p6stmt.setString(indexid, "P05");	//定價代碼
				indexid++;
			}
			p6stmt.setString(indexid, (String)orderData.get("EHF310100T0_01"));	//系統編號
			indexid++;
			
			p6stmt.executeUpdate();
			

			p6stmt.close();
			pstmt.close();
			
			this.base_tools.getConn().commit();
		}catch(Exception e){
			System.out.println("updateEHF310400T2時錯誤:"+e);
		}
	}

	private void addEHF310400T2(Map orderData, String type) {
		// TODO Auto-generated method stub
		
		try{
			String sql = " INSERT INTO EHF310400T2 (EHF310400T2_01, EHF310400T2_02, EHF310400T2_03) " +
						 " VALUES (?, ?, ?)" ;
			
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)orderData.get("EHF310100T0_01"));	//系統編號
			indexid++;
			if("P01".equals(type)){
				p6stmt.setString(indexid, "P01");	//定價代碼
				indexid++;
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_A"));  	//數量
				indexid++;
			}else if("P02".equals(type)){
				p6stmt.setString(indexid, "P02");	//定價代碼
				indexid++;
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_B"));  	//數量
				indexid++;
			}else if("P03".equals(type)){
				p6stmt.setString(indexid, "P03");	//定價代碼
				indexid++;
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_C"));  	//數量
				indexid++;
			}else if("P04".equals(type)){
				p6stmt.setString(indexid, "P04");	//定價代碼
				indexid++;
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_All"));  	//數量
				indexid++;
			}else if("P05".equals(type)){
				p6stmt.setString(indexid, "P05");	//定價代碼
				indexid++;
				p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T2_03_All"));  	//數量
				indexid++;
			}
			
			
			p6stmt.executeUpdate();
			

			p6stmt.close();
			pstmt.close();
			
			this.base_tools.getConn().commit();
		}catch(Exception e){
			System.out.println("addEHF310400T2時錯誤:"+e);
		}
	}

	public Map queryPay2Data(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		
		try{			
			//QueryEdit
			String sql = " SELECT e.P01,e.P01_count, " +
						 "        b.P02,b.P02_count, " +
						 "		  c.P03,c.P03_count, " +
						 "		  d.P04,d.P04_count, " +
						 "		  f.P05,f.P05_count " +
						 "  FROM(  SELECT EHF310400T2_02 AS P01, EHF310400T2_03 AS P01_count,EHF310400T2_01 " +
						 "		    FROM EHF310400T2  WHERE 1=1  AND EHF310400T2_02 = 'P01'  AND EHF310400T2_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"') e " +
						 
						 "  LEFT JOIN ( SELECT EHF310400T2_02 AS P02, EHF310400T2_03 AS P02_count,EHF310400T2_01 " +
						 "			    FROM EHF310400T2  WHERE 1=1  AND EHF310400T2_02 = 'P02'  AND EHF310400T2_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"') b " +
						 "		ON e.EHF310400T2_01 = b.EHF310400T2_01 " +
						 
						 "  LEFT JOIN ( SELECT EHF310400T2_02 AS P03, EHF310400T2_03 AS P03_count,EHF310400T2_01 " +
						 "				FROM EHF310400T2  WHERE 1=1  AND EHF310400T2_02 = 'P03'  AND EHF310400T2_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"') c " +
						 "      ON e.EHF310400T2_01 = c.EHF310400T2_01 " +
						 
						 "  LEFT JOIN ( SELECT EHF310400T2_02 AS P04, EHF310400T2_03 AS P04_count,EHF310400T2_01 " +
						 "				FROM EHF310400T2  WHERE 1=1  AND EHF310400T2_02 = 'P04'  AND EHF310400T2_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"') d " +
						 "		ON e.EHF310400T2_01 = d.EHF310400T2_01 " +
						 
						 "  LEFT JOIN ( SELECT EHF310400T2_02 AS P05, EHF310400T2_03 AS P05_count,EHF310400T2_01 " +
						 "				FROM EHF310400T2  WHERE 1=1  AND EHF310400T2_02 = 'P05'  AND EHF310400T2_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"') f " +
						 "		ON e.EHF310400T2_01 = f.EHF310400T2_01" ;
			
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}

	public Map queryPay1Data(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		
		try{			
			//QueryEdit
			String sql = "" +
			" SELECT * " +
			" FROM EHF310400T0 " +
			" WHERE 1=1 " +
			" AND EHF310400T0_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' " +
			" AND EHF310400T0_06 = '"+(String)queryCondMap.get("COMP_ID")+"' " ;
			
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}

	public void editEHF310400T0(String compId, Map orderData) {
		// TODO Auto-generated method stub
		
		try{
			int price = 0;
			String sql = " SELECT * FROM EHF310400T0 " +
						 "  WHERE EHF310400T0_01='"+(String)orderData.get("EHF310100T0_01")+"' " +
						 "    AND EHF310400T0_06='"+compId+"' ";
			
			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				try{
					if(orderData.containsKey("EHF310400T0_04") && orderData.containsKey("EHF310400T0_05")){
						price = (Integer)orderData.get("EHF310400T0_03");

					}else{
						price = (Integer)orderData.get("EHF310400T2_03_All_Pay")+
								(Integer)orderData.get("EHF310400T2_03_A_Pay")+
								(Integer)orderData.get("EHF310400T2_03_B_Pay")+
								(Integer)orderData.get("EHF310400T2_03_C_Pay");
					}
					
					String sql_update = " UPDATE EHF310400T0 SET " +
										"		 EHF310400T0_02=?, " +//訂餐天數
										"		 EHF310400T0_03=? " ;//定價
					
					if(orderData.containsKey("EHF310400T0_04") && orderData.containsKey("EHF310400T0_05")){
						sql_update+="		 ,EHF310400T0_04=?, " +//折扣金額
							 		"		 EHF310400T0_05=? " ;//已付金額
					}
										
										
					sql_update+=" WHERE EHF310400T0_01=? AND EHF310400T0_06=? ";
					
					PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql_update);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,sql_update);
					int indexid = 1;
					
						p6stmt.setInt(indexid, (Integer)orderData.get("days"));  	//訂餐天數
						indexid++;
						p6stmt.setInt(indexid, price);	//定價
						indexid++;
						if(orderData.containsKey("EHF310400T0_04") && orderData.containsKey("EHF310400T0_05")){
							p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T0_04"));	//折扣金額
							indexid++;
							p6stmt.setInt(indexid, (Integer)orderData.get("EHF310400T0_05"));	//已付金額
							indexid++;
						}
						p6stmt.setString(indexid, (String)orderData.get("EHF310100T0_01"));	//系統編號
						indexid++;
						p6stmt.setString(indexid, compId);	//公司代碼
						indexid++;
						
						p6stmt.executeUpdate();
						

						p6stmt.close();
						pstmt.close();
						
						this.base_tools.getConn().commit();
				}catch(Exception e){
					System.out.println("update to EHF310400T0 時錯誤:"+e);
				}
			}else{

			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			System.out.println("editEHF310400T0時錯誤:"+e);
		}
		
	}

	public void addDataPay(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql_1 = "";
		String show_sql_1 = "";
		
		try{
			//Add EHF310400T0
			sql_1 = "" +
			" INSERT INTO EHF310400T0 " +
			" (EHF310400T0_01, EHF310400T0_02, EHF310400T0_03, EHF310400T0_04, EHF310400T0_05, EHF310400T0_06, " +
			"  USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, " +
			"          ?, ?, 1, NOW(), NOW() ) ";
			
			//執行SQL
			PreparedStatement pstmt_1 = this.base_tools.getConn().prepareStatement(sql_1);
			P6PreparedStatement p6stmt_1 = new P6PreparedStatement(null, pstmt_1, null, sql_1);
			int indexid_1 = 1;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF310100T0_01"));  //
			indexid_1++;
			p6stmt_1.setInt(indexid_1, (Integer)dataMap.get("EHF310100T0_09"));  //
			indexid_1++;
			p6stmt_1.setInt(indexid_1, 0);  //
			indexid_1++;
			p6stmt_1.setInt(indexid_1, 0);  //
			indexid_1++;
			p6stmt_1.setInt(indexid_1, 0);  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("COMP_ID"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("USER_NAME"));  //
			indexid_1++;
			
			
			//System.out.println(p6stmt_1.getQueryFromPreparedStatement());
			show_sql_1 = p6stmt_1.getQueryFromPreparedStatement();
			//執行
			p6stmt_1.executeUpdate();
			
			//客戶需求單單號
			String SysNo = (String)dataMap.get("EHF310100T0_01");
			
			//更新資料庫
			this.base_tools.commit();

			p6stmt_1.close();
			pstmt_1.close();
			
			//回寫 員工系統代碼
			dataMap.put("KEY_ID", SysNo);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF310100().addDataPay()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF310100.addDataPay()", show_sql_1+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

	}

	public List queryPay3Data(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List dataList = null;
		
		try{			
			//QueryEdit
			String sql = "" +
			" SELECT * " +
			" FROM EHF310400T1 " +
			" WHERE 1=1 " +
			" AND EHF310400T1_01 = ? " ;
			
			if("yes".equals((String)queryCondMap.get("confirm"))){
				sql +=" AND EHF310400T1_13 = '1' ";//已確認				  
			}
			
			sql +=" AND EHF310400T1_14 = '0' " +//未刪除
				  " AND EHF310400T1_15 = ? " ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			pstmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_01"));
			indexid++;
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}

	public List queryEHF310400T1_List(Map queryCondMap) {
		// TODO Auto-generated method stub
		List EHF310400T1_List = new ArrayList();
		
		String sql = " SELECT *," +
					 " 		  DATE_FORMAT(EHF310400T1_03, '%Y/%m/%d') AS EHF310400T1_03, " +
					 "  	  DATE_FORMAT(EHF310400T1_08, '%Y/%m/%d') AS EHF310400T1_08, " +
					 "  	  DATE_FORMAT(EHF310400T1_10, '%Y/%m/%d') AS EHF310400T1_10 " +
					 
					 "   FROM EHF310400T1 " +
					 
					 "  WHERE 1=1 " +
					 "    AND EHF310400T1_01 = '"+(String)queryCondMap.get("EHF310100T0_01")+"' " +
					 "	  AND EHF310400T1_14 = '0' " +
					 "	  AND EHF310400T1_15 = '"+(String)queryCondMap.get("COMP_ID")+"'";
			
		EHF310400T1_List = this.base_tools.queryList(sql);
			
		return EHF310400T1_List;
	}

	public Map getPayDetailMap(String KEY_ID, int EHF310400T1_02,
			String COMP_ID) {
		// TODO Auto-generated method stub
		
		Map returnMap = new HashMap();
		
		try{
			String sql = "" +
						 " SELECT EHF310400T1_05, EHF310400T1_06, EHF310400T1_07, " +
						 "		  DATE_FORMAT(EHF310400T1_08, '%Y/%m/%d') AS EHF310400T1_08, EHF310400T1_09, " +
						 "		  DATE_FORMAT(EHF310400T1_10, '%Y/%m/%d') AS EHF310400T1_10, EHF310400T1_11, " +
						 "		  EHF310400T1_12 " +
						 " FROM EHF310400T1 " +
						 " WHERE 1=1 " +
						 " AND EHF310400T1_01 = '"+KEY_ID+"' " +
						 " AND EHF310400T1_02='"+EHF310400T1_02+"' " +
						 " AND EHF310400T1_15 = '"+COMP_ID+"' ";		
			
			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
				returnMap.put("EHF310400T1_05", rs.getString("EHF310400T1_05"));
				returnMap.put("EHF310400T1_06", rs.getString("EHF310400T1_06"));
				returnMap.put("EHF310400T1_07", rs.getString("EHF310400T1_07"));
				returnMap.put("EHF310400T1_08", rs.getString("EHF310400T1_08"));
				returnMap.put("EHF310400T1_09", rs.getInt("EHF310400T1_09"));
				returnMap.put("EHF310400T1_10", rs.getString("EHF310400T1_10"));
				returnMap.put("EHF310400T1_11", rs.getInt("EHF310400T1_11"));
				returnMap.put("EHF310400T1_12", rs.getString("EHF310400T1_12"));
			}
				
			rs.close();
			stmt.close();
		}catch(Exception e){
			System.out.println("取得修改前付款明細時錯誤");
		}
		
		return returnMap;
	}

	public void confirmRecovery(Map queryCondMap, String type) {
		// TODO Auto-generated method stub
		try{
			String sql = " UPDATE EHF310400T1 SET EHF310400T1_13=?, " +
						 "				 		  USER_UPDATE=?, " +
						 "						  VERSION = VERSION + 1," +
						 "						  DATE_UPDATE = NOW() " +
						 " WHERE EHF310400T1_01=? " +
						 "   AND EHF310400T1_02=? " +
						 "	 AND EHF310400T1_15=? ";
			
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,sql);
			int indexid = 1;
			
			if(type.equals("confirm")){
				p6stmt.setBoolean(indexid, tools.StringToBoolean("1"));  	//確認
				indexid++;
			}else if(type.equals("recovery")){
				p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  	//確認
				indexid++;
			}
			
			p6stmt.setString(indexid, (String)queryCondMap.get("USER_NAME"));	//最後修改人員
			indexid++;
			p6stmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_01"));	//系統編號
			indexid++;
			p6stmt.setInt(indexid, (Integer)queryCondMap.get("EHF310400T1_02"));	//順序編號
			indexid++;
			p6stmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));	//公司代碼
			indexid++;
			
			p6stmt.executeUpdate();
			

			p6stmt.close();
			pstmt.close();
			
			this.base_tools.getConn().commit();
		}catch(Exception e){
			System.out.println("confirmRecovery時錯誤:"+e);
		}
	}

	public void remove(Map queryCondMap) {
		// TODO Auto-generated method stub
		try{
			String sql = " UPDATE EHF310400T1 SET EHF310400T1_14=?, " +
						 "				 		  USER_UPDATE=?, " +
						 "						  VERSION = VERSION + 1," +
						 "						  DATE_UPDATE = NOW() " +
						 " WHERE EHF310400T1_01=? " +
						 "   AND EHF310400T1_02=? " +
						 "	 AND EHF310400T1_15=? ";
			
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,sql);
			int indexid = 1;
			
			p6stmt.setBoolean(indexid, tools.StringToBoolean("1"));  	//刪除
			indexid++;
			p6stmt.setString(indexid, (String)queryCondMap.get("USER_NAME"));	//最後修改人員
			indexid++;
			p6stmt.setString(indexid, (String)queryCondMap.get("EHF310100T0_01"));	//系統編號
			indexid++;
			p6stmt.setInt(indexid, (Integer)queryCondMap.get("EHF310400T1_02"));	//順序編號
			indexid++;
			p6stmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));	//公司代碼
			indexid++;
			
			p6stmt.executeUpdate();
			

			p6stmt.close();
			pstmt.close();
			
			this.base_tools.getConn().commit();
		}catch(Exception e){
			System.out.println("remove時錯誤:"+e);
		}
	}

	public Map getHospital(String comp_id) {
			
		Map returnMap = new HashMap();
			
		try{
				
			String sql = "" +
			" SELECT EHF300200T0_01 AS ITEM_ID, EHF300200T0_02 AS ITEM_VALUE " +
			" FROM EHF300200T0 " +
			" WHERE 1=1 " +
			" AND EHF300200T0_06 = '"+comp_id+"' " +
			" ORDER BY EHF300200T0_01 ";		

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

				
			while(rs.next()){
					
				returnMap.put(rs.getString("ITEM_ID"), rs.getString("ITEM_VALUE"));
				returnMap.put(rs.getString("ITEM_VALUE"), rs.getString("ITEM_ID"));
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return returnMap;
	}

	public Map getResidential(String comp_id) {
			
		Map returnMap = new HashMap();
			
		try{
				
			String sql = "" +
			" SELECT EHF300100T0_01 AS ITEM_ID, EHF300100T0_02 AS ITEM_VALUE " +
			" FROM EHF300100T0 " +
			" WHERE 1=1 " +
			" AND EHF300100T0_06 = '"+comp_id+"' " +
			" ORDER BY EHF300100T0_01 ";		

			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

				
			while(rs.next()){
					
				returnMap.put(rs.getString("ITEM_ID"), rs.getString("ITEM_VALUE"));
				returnMap.put(rs.getString("ITEM_VALUE"), rs.getString("ITEM_ID"));
					
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return returnMap;
	}

	public List getOrderInf(Map dataMap) {
		// TODO Auto-generated method stub
		List EHF310100T1_List = new ArrayList();
		
		try{
			String sql = " SELECT *, " +
						 " 		  DATE_FORMAT(EHF310100T1_02, '%Y/%m/%d') AS EHF310100T1_02 " +
						 "   FROM EHF310100T1 " +
						 "  WHERE 1=1 " +
						 "    AND EHF310100T1_01 = '"+(String)dataMap.get("EHF310100T0_01")+"' " +
						 "    AND EHF310100T1_06 = '"+(String)dataMap.get("COMP_ID")+"' " +
						 " ORDER BY EHF310100T1_02,EHF310100T1_03 ";
			
			
			EHF310100T1_List = this.base_tools.queryList(sql);
		}catch(Exception e){
			System.out.println("列印用餐確認表中，取得訂餐資訊時錯誤:"+e);
		}
		
		return EHF310100T1_List;
	}

	public List getDontEatInf(Map dataMap) {
		// TODO Auto-generated method stub
		List EHF310200T1_List = new ArrayList();
		
		try{
			String sql = " SELECT * " +
						 "   FROM EHF310200T1 " +
						 "  WHERE 1=1 " +
						 "    AND EHF310200T1_01 = '"+(String)dataMap.get("EHF310100T0_01")+"' " +
						 " ORDER BY EHF310200T1_02 ";
			
			
			EHF310200T1_List = this.base_tools.queryList(sql);
		}catch(Exception e){
			System.out.println("列印用餐確認表中，取得不吃食物時錯誤:"+e);
		}
		
		return EHF310200T1_List;
	}

	public List getspeInf(Map dataMap) {
		// TODO Auto-generated method stub
		List EHF310200T3_List = new ArrayList();
		
		try{
			String sql = " SELECT * " +
						 "   FROM EHF310200T3 e " +
						 " LEFT JOIN (SELECT EMS_CategoryT1_04,EMS_CategoryT1_05 FROM EMS_CategoryT1 WHERE EMS_CategoryT1_01='SpeDemand') a ON a.EMS_CategoryT1_04=e.ehf310200t3_03" +
						 "  WHERE 1=1 " +
						 "    AND EHF310200T3_01 = '"+(String)dataMap.get("EHF310100T0_01")+"' " +
						 " ORDER BY EHF310200T3_02 ";
			
			
			EHF310200T3_List = this.base_tools.queryList(sql);
		}catch(Exception e){
			System.out.println("列印用餐確認表中，取得特殊需求時錯誤:"+e);
		}
		
		return EHF310200T3_List;
	}

	public List getDrinkInf(Map dataMap) {
		// TODO Auto-generated method stub
		List EHF310100T2_List = new ArrayList();
		//
		try{
			String sql = " SELECT DATE_FORMAT(EHF310100T2_03, '%Y/%m/%d') AS EHF310100T2_03, " +
						 "		  DATE_FORMAT(EHF310100T2_04, '%Y/%m/%d') AS EHF310100T2_04, " +
						 "		  EHF310100T2_05, EHF310100T2_06,  EMS_CategoryT1_04, EMS_CategoryT1_05 " +
						 "   FROM EHF310100T2 e " +
						 " LEFT JOIN (SELECT EMS_CategoryT1_04,EMS_CategoryT1_05 FROM EMS_CategoryT1 WHERE EMS_CategoryT1_01='Drink') a ON a.EMS_CategoryT1_04=e.EHF310100T2_06" +
						 "  WHERE 1=1 " +
						 "    AND EHF310100T2_01 = '"+(String)dataMap.get("EHF310100T0_01")+"' " +
						 " ORDER BY EHF310100T2_02 ";
			
			EHF310100T2_List = this.base_tools.queryList(sql);
		}catch(Exception e){
			System.out.println("列印用餐確認表中，取得養生飲品時錯誤:"+e);
		}
		
		return EHF310100T2_List;
	}

	public Map getCallInf(Map dataMap) {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();

		try{
			String sql = " SELECT DATE_FORMAT(EHF310300T0_04, '%Y/%m/%d') AS EHF310300T0_04," +
						 "		  EHF310300T0_05 " +
						 "   FROM EHF310300T0 " +
						 "  WHERE 1=1 " +
						 "    AND EHF310300T0_01 = '"+(String)dataMap.get("EHF310100T0_01")+"' " +
						 "    AND EHF310300T0_07 = '"+(String)dataMap.get("COMP_ID")+"' " +
						 "    AND ( " ;
			
			if(!"".equals((String)dataMap.get("firstDate")) && (String)dataMap.get("firstDate") != null){
				sql += " EHF310300T0_04 = '"+(String)dataMap.get("firstDate")+"' ";
			}
			if(!"".equals((String)dataMap.get("secondDate")) && (String)dataMap.get("secondDate") != null){
				sql += " OR EHF310300T0_04 = '"+(String)dataMap.get("secondDate")+"' ";
			}
			if(!"".equals((String)dataMap.get("thirdDate")) && (String)dataMap.get("thirdDate") != null){
				sql += " OR EHF310300T0_04 = '"+(String)dataMap.get("thirdDate")+"' ";
			}
			sql+=") ORDER BY EHF310300T0_02 DESC ";
			
			Statement stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
				
			while(rs.next()){
				
				if(rs.getString("EHF310300T0_04").equals((String)dataMap.get("firstDate"))){
					returnMap.put("firstDate", rs.getString("EHF310300T0_05"));
				}else if(rs.getString("EHF310300T0_04").equals((String)dataMap.get("secondDate"))){
					returnMap.put("secondDate", rs.getString("EHF310300T0_05"));
				}else if(rs.getString("EHF310300T0_04").equals((String)dataMap.get("thirdDate"))){
					returnMap.put("thirdDate", rs.getString("EHF310300T0_05"));
				}
				
			}
			rs.close();
			stmt.close();;
		}catch(Exception e){
			System.out.println("列印用餐確認表中，取得電訪資訊時錯誤:"+e);
		}
		
		return returnMap;
	}

	
}