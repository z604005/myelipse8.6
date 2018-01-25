package com.spon.ems.hr.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.hr.forms.EHF010100M0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

public class EHF010100 implements QueryFunction, EditFunction,
		EditDetailFunction, BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF010100(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF010100( String comp_id ){
		
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
			" SELECT A.EHF010100T0_01, A.EHF010100T0_02, A.EHF010100T0_05, A.EHF010100T0_07, B.EHF010100T1_02, G.EHF010100T6_02, H.EHF000200T0_03 " +						
			" FROM EHF010100T0 A " +
			" LEFT JOIN EHF010100T1 B ON A.EHF010100T0_01 = B.EHF010100T1_01 AND A.HR_CompanySysNo = B.HR_CompanySysNo AND B.EHF010100T1_04 = '0' " +
			//" LEFT JOIN EHF010106T2 C ON A.HR_EmployeeSysNo = C.HR_EmployeeSysNo AND A.HR_CompanySysNo = C.HR_CompanySysNo " +
			//" LEFT JOIN EHF010106T3 D ON A.HR_EmployeeSysNo = D.HR_EmployeeSysNo AND A.HR_CompanySysNo = D.HR_CompanySysNo " +
			//" LEFT JOIN EHF010106T4 E ON A.HR_EmployeeSysNo = E.HR_EmployeeSysNo AND A.HR_CompanySysNo = E.HR_CompanySysNo " +
			//" LEFT JOIN EHF010106T5 F ON A.HR_EmployeeSysNo = F.HR_EmployeeSysNo AND A.HR_CompanySysNo = F.HR_CompanySysNo " +
			" LEFT JOIN EHF010100T6 G ON A.EHF010100T0_01 = G.EHF010100T6_01 AND A.HR_CompanySysNo = G.HR_CompanySysNo " +
			" LEFT JOIN EHF000200T0 H ON G.EHF010100T6_02 = H.EHF000200T0_01 AND G.HR_CompanySysNo = H.HR_CompanySysNo " +
			" WHERE 1=1 " ;			
									
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T6_02"))){	//部門代號
				sql += " and G.EHF010100T6_02 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T0_02"))){	//員工工號
				sql += " and A.EHF010100T0_02 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T0_05"))){	//員工姓名
				sql += " and A.EHF010100T0_05 like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T0_I"))){	//身分證字號
				sql += " and A.EHF010100T0_I like ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T0_07"))){	//員工類別
				sql += " and A.EHF010100T0_07 = ?";
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T1_02"))){	//職務狀況
				sql += " and B.EHF010100T1_02 = ?";
			}
			
			sql += "" +
			" AND A.HR_CompanySysNo like ? " +  //公司代碼
			" ORDER BY g.EHF010100T6_02,G.EHF010100T6_06 ,A.EHF010100T0_01 ASC ";  
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T6_02"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF010100T6_02"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T0_02"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF010100T0_02"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T0_05"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF010100T0_05"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T0_I"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF010100T0_I"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T0_07"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF010100T0_07"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010100T1_02"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF010100T1_02"));
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
			" SELECT " +
			" A.*, DATE_FORMAT(A.EHF010100T0_11, '%Y/%m/%d') AS EHF010100T0_11, " +
			" B.EHF010100T1_02, DATE_FORMAT(B.EHF010100T1_03, '%Y/%m/%d') AS EHF010100T1_03, B.EHF010100T1_04, " +
			" G.EHF010100T6_02, G.EHF010100T6_06, G.EHF010100T6_07," +
			" DATE_FORMAT(G.EHF010100T6_03, '%Y/%m/%d') AS EHF010100T6_03, " +
			" DATE_FORMAT(G.EHF010100T6_04, '%Y/%m/%d') AS EHF010100T6_04, " +
			" GD.EHF000200T0_03, GD.EHF000200T0_02, " +
			" GJ.EHF000300T0_02, GJ.EHF000300T0_03, " +
			" AA.EHF000400T0_03, AA.EHF000400T0_04, AA.EHF000400T0_01," +
			" BB.EHF020403T0_05, " +
			" CC.EHF020403T1_02, CC.EHF020403T1_04_START, CC.EHF020403T1_04_END, CC.EHF020403T1_05, " +
			" DD.EHF030200T0_07, DD.EHF030200T0_05, DD.EHF030200T0_06, DD.EHF030200T0_06_AC, DD.EHF030200T0_04, DD.EHF030200T0_19, " +
			" DD.EHF030200T0_18, DD.EHF030200T0_20, DD.EHF030200T0_21, DD.EHF030200T0_08, DD.EHF030200T0_12, " +
			" EE.EHF030300T0_05_VERSION, EE.EHF030300T0_05, " +
			" DATE_FORMAT(EE.EHF030300T0_05_START, '%Y/%m/%d') AS EHF030300T0_05_START, " +
			" DATE_FORMAT(EE.EHF030300T0_05_END, '%Y/%m/%d') AS EHF030300T0_05_END, " +
			" EE.EHF030300T0_07_VERSION, EE.EHF030300T0_07, " +
			" DATE_FORMAT(EE.EHF030300T0_07_START, '%Y/%m/%d') AS EHF030300T0_07_START, " +
			" DATE_FORMAT(EE.EHF030300T0_07_END, '%Y/%m/%d') AS EHF030300T0_07_END, " +
			" EE.EHF030300T0_15, EE.EHF030300T0_09, EE.EHF030300T0_11, EE.EHF030300T0_10, " +
			" EA.EHF030103T0_01, EA.EHF030103T0_02, EA.EHF030103T0_03, EA.EHF030103T0_02_VERSION, " +
			" EB.EHF030103T1_02, EB.EHF030103T1_03, EB.EHF030103T1_04, " +
			" EC.EHF030104T0_01, EC.EHF030104T0_02, EC.EHF030104T0_03, EC.EHF030104T0_02_VERSION, " +
			" ED.EHF030104T1_02, ED.EHF030104T1_03, ED.EHF030104T1_04 " +
			" FROM EHF010100T0 A " +
			" LEFT JOIN EHF010100T1 B ON A.EHF010100T0_01 = B.EHF010100T1_01 AND A.HR_CompanySysNo = B.HR_CompanySysNo AND B.EHF010100T1_04 = '0' " +			
			" LEFT JOIN EHF010100T6 G ON A.EHF010100T0_01 = G.EHF010100T6_01 AND A.HR_CompanySysNo = G.HR_CompanySysNo " +
			" LEFT JOIN EHF000200T0 GD ON G.EHF010100T6_02 = GD.EHF000200T0_01 AND G.HR_CompanySysNo = GD.HR_CompanySysNo " +
			" LEFT JOIN EHF000300T0 GJ ON G.EHF010100T6_06 = GJ.EHF000300T0_01 AND G.HR_CompanySysNo = GJ.HR_CompanySysNo " +
			" LEFT JOIN EHF010100T8 AE ON A.EHF010100T0_01 = AE.EHF010100T8_03 AND A.HR_CompanySysNo = AE.EHF010100T8_06 " +	//員工班別資料
			" LEFT JOIN EHF000400T0 AA ON AE.EHF010100T8_04 = AA.EHF000400T0_01 AND AE.EHF010100T8_06 = AA.EHF000400T0_11 " +	//班別資料
			" LEFT JOIN EHF020403T0 BB ON A.EHF010100T0_01 = BB.EHF020403T0_01 AND A.HR_CompanySysNo = BB.EHF020403T0_04 " +	//感應卡設定資料
			" LEFT JOIN EHF020403T1 CC ON A.EHF010100T0_01 = CC.EHF020403T1_01 AND A.HR_CompanySysNo = CC.EHF020403T1_06 " +	//感應卡明細
			" LEFT JOIN EHF030200T0 DD ON A.EHF010100T0_01 = DD.EHF030200T0_01 AND A.HR_CompanySysNo = DD.EHF030200T0_13 " +	//薪資基本資料
			" LEFT JOIN EHF030300T0 EE ON A.EHF010100T0_01 = EE.EHF030300T0_01 AND A.HR_CompanySysNo = EE.EHF030300T0_14 " +	//保險基本資料
			" LEFT JOIN EHF030103T0 EA ON EA.EHF030103T0_01 = EE.EHF030300T0_05_VERSION AND EA.EHF030103T0_05 = EE.EHF030300T0_14 " +	//健保等級資料
			" LEFT JOIN EHF030103T1 EB ON EB.EHF030103T1_01 = EA.EHF030103T0_01 AND EB.EHF030103T1_08 = EA.EHF030103T0_05 AND EB.EHF030103T1_02 = EE.EHF030300T0_05 " +	//健保等級明細資料
			" LEFT JOIN EHF030104T0 EC ON EC.EHF030104T0_01 = EE.EHF030300T0_07_VERSION AND EC.EHF030104T0_05 = EE.EHF030300T0_14 " +	//勞保等級資料
			" LEFT JOIN EHF030104T1 ED ON ED.EHF030104T1_01 = EC.EHF030104T0_01 AND ED.EHF030104T1_08 = EC.EHF030104T0_05  AND ED.EHF030104T1_02 = EE.EHF030300T0_07 " +	//勞保等級明細資料
			" WHERE 1=1 " +
			//" AND EB.EHF030103T1_02 = EE.EHF030300T0_07 AND ED.EHF030104T1_02 = EE.EHF030300T0_05 " +
			" AND A.EHF010100T0_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"' " +			
			" AND A.HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' " ;
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
			//Add EHF010100T0
			sql_1 = "" +
			" INSERT INTO EHF010100T0 " +
			" (EHF010100T0_01, EHF010100T0_02, EHF010100T0_I, EHF010100T0_03, EHF010100T0_04, EHF010100T0_05, " +
			" EHF010100T0_06, EHF010100T0_07, EHF010100T0_08, EHF010100T0_09, EHF010100T0_10, EHF010100T0_11, " +
			" EHF010100T0_12, EHF010100T0_13, EHF010100T0_14, EHF010100T0_15, EHF010100T0_16, EHF010100T0_17, " +
			" EHF010100T0_18, EHF010100T0_19, EHF010100T0_20, EHF010100T0_21, EHF010100T0_22, EHF010100T0_23, " +
			" EHF010100T0_24, EHF010100T0_25, EHF010100T0_26, EHF010100T0_27, EHF010100T0_28, EHF010100T0_29, " +
			" EHF010100T0_30, EHF010100T0_31, HR_CompanySysNo, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, HR_LastUpdateDate) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, ?, ?, ?, " +
			" ?, ?, ?, " +
			" ?, ?, 1, NOW(), NOW()) ";
			
			//執行SQL
			PreparedStatement pstmt_1 = this.base_tools.getConn().prepareStatement(sql_1);
			P6PreparedStatement p6stmt_1 = new P6PreparedStatement(null, pstmt_1, null, sql_1);
			int indexid_1 = 1;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_01"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_02"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_I"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_03"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_04"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_05"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_06"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_07"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_08"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_09"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_10"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_11"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_12"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_13"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_14"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_15"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_16"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_17"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_18"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_19"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_20"));  //
			indexid_1++;
			p6stmt_1.setBoolean(indexid_1, (Boolean)dataMap.get("EHF010100T0_21"));  //
			indexid_1++;
			p6stmt_1.setBoolean(indexid_1, (Boolean)dataMap.get("EHF010100T0_22"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_23"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_24"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_25"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_26"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_27"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_28"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_29"));  //
			indexid_1++;
			
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_30"));  //
			indexid_1++;
			p6stmt_1.setString(indexid_1, (String)dataMap.get("EHF010100T0_31")==null?"":(String)dataMap.get("EHF010100T0_31"));  //
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
			
			//員工系統代碼
			String HR_EmployeeSysNo = (String)dataMap.get("EHF010100T0_01");
			
			//更新資料庫
			this.base_tools.commit();
						
			pstmt_1.close();
			p6stmt_1.close();
			
			//回寫 員工系統代碼
			dataMap.put("KEY_ID", HR_EmployeeSysNo);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010100().addData()", show_sql_1, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100.addData()", show_sql_1+", "+e.getMessage(), 
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
			List<String> sql_list = new ArrayList<String>();
			
			//DELETE EHF010100T0
			sql = "" +
			" DELETE FROM EHF010100T0 " +
			" WHERE 1=1 " +
			" AND EHF010100T0_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' "	;		
			sql_list.add(sql);
			
			//DELETE EHF010100T1
			sql = "" +
			" DELETE FROM EHF010100T1 " +
			" WHERE 1=1 " +
			" AND EHF010100T1_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF010100T2
			sql = "" +
			" DELETE FROM EHF010100T2 " +
			" WHERE 1=1 " +
			" AND EHF010100T2_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF010100T3
			sql = "" +
			" DELETE FROM EHF010100T3 " +
			" WHERE 1=1 " +
			" AND EHF010100T3_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF010100T4
			sql = "" +
			" DELETE FROM EHF010100T4 " +
			" WHERE 1=1 " +
			" AND EHF010100T4_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF010100T5
			sql = "" +
			" DELETE FROM EHF010100T5 " +
			" WHERE 1=1 " +
			" AND EHF010100T5_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF010100T6
			sql = "" +
			" DELETE FROM EHF010100T6 " +
			" WHERE 1=1 " +
			" AND EHF010100T6_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF010100T8
			sql = "" +
			" DELETE FROM EHF010100T8 " +
			" WHERE 1=1 " +
			" AND EHF010100T8_03 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND EHF010100T8_06 = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF020403T0
			sql = "" +
			" DELETE FROM EHF020403T0 " +
			" WHERE 1=1 " +
			" AND EHF020403T0_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND EHF020403T0_04 = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF020403T1
			sql = "" +
			" DELETE FROM EHF020403T1 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND EHF020403T1_06 = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF030200T0
			sql = "" +
			" DELETE FROM EHF030200T0 " +
			" WHERE 1=1 " +
			" AND EHF030200T0_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND EHF030200T0_13 = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF030200T1
			sql = "" +
			" DELETE FROM EHF030200T1 " +
			" WHERE 1=1 " +
			" AND EHF030200T1_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND EHF030200T1_04 = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF030300T0
			sql = "" +
			" DELETE FROM EHF030300T0 " +
			" WHERE 1=1 " +
			" AND EHF030300T0_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND EHF030300T0_14 = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//DELETE EHF030300T1
			sql = "" +
			" DELETE FROM EHF030300T1 " +
			" WHERE 1=1 " +
			" AND EHF030300T1_01 = '"+(String)dataMap.get("EHF010100T0_01")+"' " +
			" AND HR_CompanySysNo = '"+(String)dataMap.get("COMP_ID")+"' "	;
			sql_list.add(sql);
			
			//執行刪除
			int[] dataCount_array = this.base_tools.executeBatchSQL(sql_list);
			int dataCount = 0;
			int mainDataCount = 0;
			for(int i=0; i<dataCount_array.length; i++){
				dataCount += dataCount_array[i];
				mainDataCount = dataCount_array[i];
			}
			
			dataMap.put("MAIN_DATA_COUNT", dataCount);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delData()", sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}

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
			if("EHF020403T1".equals(detailType)){
				//新增感應卡
				this.addEHF020403T1(detailDataMap);
			}else if("EHF030102T0".equals(detailType)){
				//新增薪資項目
				this.addEHF030102T0(detailDataMap);
			}else if("EHF010101T0".equals(detailType)){
				//新增津貼項目
				this.addEHF010101T0(detailDataMap);
			}else if("EHF030300T1".equals(detailType)){
				//新增保險眷屬
				this.addEHF030300T1(detailDataMap);
			}else if("EHF010100T5".equals(detailType)){
				//新增家庭關係明細
				this.addEHF010100T5(detailDataMap);
			}else if("EHF010100T3".equals(detailType)){
				//新增學歷明細
				this.addEHF010100T3(detailDataMap);
			}else if("EHF010100T2".equals(detailType)){
				//新增履經歷明細
				this.addEHF010100T2(detailDataMap);
			}else if("EHF010100T4".equals(detailType)){
				//新增證照資料明細
				this.addEHF010100T4(detailDataMap);
			}else if("EHF010300T0".equals(detailType)){
				//員工年度休假設定
				this.addEHF010300T0(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private void addEHF010300T0(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		float EHF010300T0_07_HOUR_number=0;
		//取得一天工作時數
		float work_hours = Float.parseFloat(tools.getSysParam(this.base_tools.getConn(), (String)detailDataMap.get("COMP_ID"), "WORK_HOURS"));
		float dayhours = 0;
		
		try{
			sql = "" +
			" INSERT INTO ehf010300t0 ( EHF010300T0_02 ,EHF010300T0_03 ,EHF010300T0_04 ,EHF010300T0_05 ,EHF010300T0_06 " +
			" ,EHF010300T0_07 ,EHF010300T0_08, EHF010300T0_09, EHF010300T0_10, EHF010300T0_11, EHF010300T0_12 ,EHF010300T0_13,EHF010300T0_FK," +
			" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE  ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) " ;
			
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010300T0_02"));
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_ID")); //處理人事(員工工號)
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T6_02"));
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01")); //員工工號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010300T0_06"));  //假別代號
			indexid++;
			
			if((String)detailDataMap.get("EHF010300T0_07_HOUR")==null || "".equals((String)detailDataMap.get("EHF010300T0_07_HOUR"))){
				EHF010300T0_07_HOUR_number =0;
			}else{
				EHF010300T0_07_HOUR_number=Float.parseFloat((String)detailDataMap.get("EHF010300T0_07_HOUR"));
			}
			//轉換天小時
			if( !"".equals((String)detailDataMap.get("EHF010300T0_07_DAY")) && (String)detailDataMap.get("EHF010300T0_07_DAY") != null  ){
				dayhours = (Float.parseFloat((String)detailDataMap.get("EHF010300T0_07_DAY")) * work_hours) + EHF010300T0_07_HOUR_number;
			}else{
				dayhours = 0;
			}
			p6stmt.setString(indexid, dayhours+"");  //休假時數
			indexid++;
			p6stmt.setString(indexid, dayhours+"");  //剩餘時數
			indexid++;
			
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010300T0_09"));
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010300T0_10"));
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010300T0_11")==null?"":(String)detailDataMap.get("EHF010300T0_11"));
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));
			indexid++;
			
			p6stmt.setString(indexid, "02");
			indexid++;
			
			p6stmt.setString(indexid, "");
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));
			indexid++;
			
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF010300T0()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF010300T0()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void addEHF010100T4(Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		String sql_seqno="";
		int seqno_new;	//新的順序號碼
		
		try{
			sql_seqno = "SELECT MAX(EHF010100T4_02)+1 AS EHF010100T4_02_NEW FROM EHF010100T4 WHERE EHF010100T4_01='"+(String)detailDataMap.get("EHF010100T0_01")+"'";
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql_seqno);
			
			if(rs.next()){
				seqno_new = rs.getInt("EHF010100T4_02_NEW");
			}else{
				seqno_new = 0;
			}
			rs.close();
			stmt.close();
			
			sql = "" +
			" INSERT INTO EHF010100T4 ( EHF010100T4_01, EHF010100T4_02, EHF010100T4_03, EHF010100T4_04, EHF010100T4_05, " +
			" EHF010100T4_06, EHF010100T4_07, EHF010100T4_08, HR_CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, HR_LastUpdateDate ) " +
			" VALUES (?,?,?,?,?," +
			" ?,?,?,?,?,?,1,NOW(),NOW() ) "  ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01"));  //員工系統代碼
			indexid++;
			p6stmt.setInt(indexid, seqno_new);  //員工筆數
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T4_03"));  //證照名稱
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T4_04")==null?"":(String)detailDataMap.get("EHF010100T4_04"));  //檔案名稱
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T4_05")==null?null:(String)detailDataMap.get("EHF010100T4_05"));  //有效期間(起)
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T4_06")==null?null:(String)detailDataMap.get("EHF010100T4_06"));  //有效期間(迄)
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T4_07")==null?"":(String)detailDataMap.get("EHF010100T4_07"));  //證照檔案上傳路徑
			indexid++;	
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T4_08")==null?"":(String)detailDataMap.get("EHF010100T4_08"));  //證照備註
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //修改人員
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF010100T4()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF010100T4()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	private void addEHF010100T2(Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		String sql_seqno="";
		int seqno_new;	//新的順序號碼
		
		try{
			sql_seqno = "SELECT MAX(EHF010100T2_02)+1 AS EHF010100T2_02_NEW FROM EHF010100T2 WHERE EHF010100T2_01='"+(String)detailDataMap.get("EHF010100T0_01")+"'";
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql_seqno);
			
			if(rs.next()){
				seqno_new = rs.getInt("EHF010100T2_02_NEW");
			}else{
				seqno_new = 0;
			}
			rs.close();
			stmt.close();
			
			sql = "" +
			" INSERT INTO EHF010100T2 ( EHF010100T2_01, EHF010100T2_02, EHF010100T2_03, EHF010100T2_04, EHF010100T2_05, " +
			" EHF010100T2_06, EHF010100T2_07, EHF010100T2_08, EHF010100T2_09, EHF010100T2_10," +
			" HR_CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, HR_LastUpdateDate ) " +
			" VALUES (?,?,?,?,?," +
			" ?,?,?,?,?, " +
			" ?,?,?,1,NOW(),NOW() ) "  ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01"));  //
			indexid++;
			p6stmt.setInt(indexid, seqno_new);  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T2_03"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T2_04"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T2_05"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T2_06"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T2_07"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T2_08"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T2_09"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T2_10"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF010100T2()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF010100T2()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	private void addEHF010100T3(Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		String sql_seqno="";
		int seqno_new;	//新的順序號碼
		
		try{
			sql_seqno = "SELECT MAX(EHF010100T3_02)+1 AS EHF010100T3_02_NEW FROM EHF010100T3 WHERE EHF010100T3_01='"+(String)detailDataMap.get("EHF010100T0_01")+"'";
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql_seqno);
			
			if(rs.next()){
				seqno_new = rs.getInt("EHF010100T3_02_NEW");
			}else{
				seqno_new = 0;
			}
			rs.close();
			stmt.close();
			
			sql = "" +
			" INSERT INTO EHF010100T3 ( EHF010100T3_01, EHF010100T3_02, EHF010100T3_03, EHF010100T3_04, EHF010100T3_05, " +
			" EHF010100T3_06, EHF010100T3_07, EHF010100T3_08, HR_CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, HR_LastUpdateDate ) " +
			" VALUES (?,?,?,?,?," +
			" ?,?,?,?,?,?,1,NOW(),NOW() ) "  ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01"));  //員工系統代碼
			indexid++;
			p6stmt.setInt(indexid, seqno_new);  //員工筆數
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T3_03"));  //學歷
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T3_04")==null?"":(String)detailDataMap.get("EHF010100T3_04"));  //學校名稱
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T3_05")==null?"":(String)detailDataMap.get("EHF010100T3_05"));  //學校科系
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T3_06")==null?null:(String)detailDataMap.get("EHF010100T3_06"));  //在校期間(起)
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T3_07")==null?null:(String)detailDataMap.get("EHF010100T3_07"));  //在校期間(迄)
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T3_08")==null?"":(String)detailDataMap.get("EHF010100T3_08"));  //畢(肄)業
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //修改人員
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF010100T3()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF010100T3()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	private void addEHF010100T5(Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		String sql_seqno="";
		int seqno_new;	//新的順序號碼
		
		try{
			sql_seqno = "SELECT MAX(EHF010100T5_02)+1 AS EHF010100T5_02_NEW FROM EHF010100T5 WHERE EHF010100T5_01='"+(String)detailDataMap.get("EHF010100T0_01")+"'";
			Statement stmt = this.base_tools.getConn().createStatement();
			ResultSet rs = stmt.executeQuery(sql_seqno);
			
			if(rs.next()){
				seqno_new = rs.getInt("EHF010100T5_02_NEW");
			}else{
				seqno_new = 0;
			}
			rs.close();
			stmt.close();
			
			sql = "" +
			" INSERT INTO EHF010100T5 ( EHF010100T5_01, EHF010100T5_02, EHF010100T5_03, EHF010100T5_04, EHF010100T5_05, " +
			" EHF010100T5_06, HR_CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, HR_LastUpdateDate ) " +
			" VALUES (?,?,?,?,?," +
			" ?,?,?,?,1,NOW(),NOW() ) "  ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01"));  //
			indexid++;
			p6stmt.setInt(indexid, seqno_new);  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T5_03"));  //
			indexid++;
			p6stmt.setInt(indexid, Integer.parseInt((String)detailDataMap.get("EHF010100T5_04")));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T5_05"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T5_06"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF010100T5()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF010100T5()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	private void addEHF030300T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" INSERT INTO EHF030300T1 ( EHF030300T1_01, EHF030300T1_02, EHF030300T1_03, EHF030300T1_04, EHF030300T1_05, " +
			" EHF030300T1_06, HR_CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?," +
			" ?,?,?,?,1,NOW() ) "  ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01"));  //員工系統工號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030300T1_02"));  //眷屬姓名
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030300T1_03"));  //眷屬關係
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030300T1_04"));  //眷屬身分證字號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030300T1_05"));  //眷屬生日
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030300T1_06"));  //眷屬加保日期
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF030300T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF030300T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	private void addEHF010101T0(Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" INSERT INTO EHF030200t1 ( EHF030200T1_01, EHF030200T1_02, EHF030200T1_03 " +
			" , EHF030200T1_04, EHF030200T1_05, EHF030200T1_06 ) " +
			" VALUES (?,?,?,?,?,2 ) "  ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030200T1_02_01"));  //津貼項目序號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030200T1_03_01")==null?"":(String)detailDataMap.get("EHF030200T1_03_01"));  //備註
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030200T1_04_02"));  //可使用班別
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF010101T0()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF010101T0()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	private void addEHF030102T0(Map detailDataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" INSERT INTO EHF030200t1 ( EHF030200T1_01, EHF030200T1_02, EHF030200T1_03 " +
			" , EHF030200T1_04, EHF030200T1_06 ) " +
			" VALUES (?,?,?,?,1 ) "  ;
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030200T1_02"));  //薪資項目金額序號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF030200T1_03")==null?"":(String)detailDataMap.get("EHF030200T1_03"));  //備註
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;		
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF030102T0()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF030102T0()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	private void addEHF020403T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			sql = "" +
			" INSERT INTO EHF020403T1 " +
			" ( " +
			" EHF020403T1_01, EHF020403T1_02, EHF020403T1_03, EHF020403T1_04, " +
			" EHF020403T1_04_START, EHF020403T1_04_END, EHF020403T1_05, EHF020403T1_06 " +			
			" ) " +
			" VALUES ( " +
			" ?, ?, ?, ?, " +
			" ?, ?, ?, ? " +			
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T0_01"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, tools.addZeroForNum((String)detailDataMap.get("EHF020403T1_02"),10));  //感應卡號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF010100T8_04"));  //班別代號
			indexid++;
			p6stmt.setString(indexid, "");  //啟用
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020403T1_04_START")+" "+
							(String)detailDataMap.get("EHF020403T1_04_START_HH")+":"+(String)detailDataMap.get("EHF020403T1_04_START_MM"));  //
			indexid++;
			if("".equals((String)detailDataMap.get("EHF020403T1_04_END"))){
				p6stmt.setString(indexid, "9999/12/31"+" "+
								(String)detailDataMap.get("EHF020403T1_04_END_HH")+":"+(String)detailDataMap.get("EHF020403T1_04_END_MM"));  //使用期間(迄)
			}
			else{
				p6stmt.setString(indexid, (String)detailDataMap.get("EHF020403T1_04_END")+" "+
								(String)detailDataMap.get("EHF020403T1_04_END_HH")+":"+(String)detailDataMap.get("EHF020403T1_04_END_MM"));  //使用期間(迄)	
			}
			indexid++;			
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020403T1_05")==null?"":(String)detailDataMap.get("EHF020403T1_05"));  //備註
			indexid++;			
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;			
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF010100().addEHF020403T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100().addEHF020403T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		try{
			if("EHF020403T1".equals(detailType)){
				//刪除感應卡
				this.delEHF020403T1(detailDataMap);
			}else if("EHF030200T1".equals(detailType)){
				//刪除薪資項目
				this.delEHF030200T1(detailDataMap);
			}else if("EHF030300T1".equals(detailType)){
				//刪除保險眷屬
				this.delEHF030300T1(detailDataMap);
			}else if("EHF010100T5".equals(detailType)){
				//刪除家庭關係明細
				this.delEHF010100T5(detailDataMap);
			}else if("EHF010100T3".equals(detailType)){
				//刪除學歷明細
				this.delEHF010100T3(detailDataMap);
			}else if("EHF010100T2".equals(detailType)){
				//刪除履經歷明細
				this.delEHF010100T2(detailDataMap);
			}else if("EHF010100T4".equals(detailType)){
				//刪除證照資料明細
				this.delEHF010100T4(detailDataMap);
			}else if("EHF010300T0".equals(detailType)){
				//刪除員工年度休假設定
				this.delEHF010300T0(detailDataMap);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	private void delEHF010300T0(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//員工年度休假資料
			sql = "" +
			" DELETE FROM EHF010300T0 " +
			" WHERE 1=1 " +
			" AND EHF010300T0_01 = '"+(String)detailDataMap.get("EHF010300T0_01")+"' " +  //年度休假序號
			" AND EHF010300T0_05 = '"+(String)detailDataMap.get("EHF010100T0_01")+"' " +  //員工工號
			" AND EHF010300T0_12 = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delEHF010300T0()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delEHF010300T0()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	private void delEHF020403T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除感應卡
			sql = "" +
			" DELETE FROM EHF020403T1 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+(String)detailDataMap.get("EHF010100T0_01")+"' " +  //員工工號
			" AND EHF020403T1_02 = '"+(String)detailDataMap.get("EHF020403T1_02")+"' " +  //感應卡號
			" AND EHF020403T1_06 = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delEHF020403T1()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delEHF020403T1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
	
	private void delEHF030200T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			if ("01".equals((String)detailDataMap.get("type"))) {
				//刪除薪資項目
				sql = "" +
				" DELETE FROM EHF030200t1 " +
				" WHERE 1=1 " +
				" AND EHF030200T1_01 = '"+(String)detailDataMap.get("EHF010100T0_01")+"' " +
				" AND EHF030200T1_02 = '"+(String)detailDataMap.get("EHF030200T1_02")+"' " +
				" AND EHF030200T1_06 = '1' " +
				" AND EHF030200T1_04 = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			}else if("02".equals((String)detailDataMap.get("type"))){
				//刪除津貼項目
				sql = "" +
				" DELETE FROM EHF030200t1 " +
				" WHERE 1=1 " +
				" AND EHF030200T1_01 = '"+(String)detailDataMap.get("EHF010100T0_01")+"' " +
				" AND EHF030200T1_02 = '"+(String)detailDataMap.get("EHF030200T1_02")+"'" +
				" AND EHF030200T1_06 = '2'" +
				" AND EHF030200T1_04 = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			}
			
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delEHF030200T1()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delEHF030200T1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
	
	private void delEHF030300T1(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除加保眷屬
			sql = "" +
			" DELETE FROM EHF030300T1 " +
			" WHERE 1=1 " +
			" AND EHF030300T1_01 = '"+(String)detailDataMap.get("EHF010100T0_01")+"' " +  //員工系統工號
			" AND EHF030300T1_04 = '"+(String)detailDataMap.get("EHF030300T1_04")+"' " +  //眷屬身分證字號
			" AND HR_CompanySysNo = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delEHF030300T1()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delEHF030300T1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	private void delEHF010100T5(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除家庭關係明細
			sql = "" +
			" DELETE FROM EHF010100T5 " +
			" WHERE 1=1 " +
			" AND EHF010100T5_01 = '"+(String)detailDataMap.get("EHF010100T5_01")+"' " +  //員工系統工號
			" AND EHF010100T5_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF010100T5_02"))+"' " +  //
			" AND HR_CompanySysNo = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delEHF010100T5()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delEHF010100T5()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
	
	private void delEHF010100T3(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除學歷明細
			sql = "" +
			" DELETE FROM EHF010100T3 " +
			" WHERE 1=1 " +
			" AND EHF010100T3_01 = '"+(String)detailDataMap.get("EHF010100T3_01")+"' " +  //員工系統工號
			" AND EHF010100T3_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF010100T3_02"))+"' " +  //
			" AND HR_CompanySysNo = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delEHF010100T3()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delEHF010100T3()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
	
	private void delEHF010100T2(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除履經歷明細
			sql = "" +
			" DELETE FROM EHF010100T2 " +
			" WHERE 1=1 " +
			" AND EHF010100T2_01 = '"+(String)detailDataMap.get("EHF010100T2_01")+"' " +  //員工系統工號
			" AND EHF010100T2_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF010100T2_02"))+"' " +  //
			" AND HR_CompanySysNo = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delEHF010100T2()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delEHF010100T2()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}
	
	private void delEHF010100T4(Map detailDataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//刪除證照資料明細
			sql = "" +
			" DELETE FROM EHF010100T4 " +
			" WHERE 1=1 " +
			" AND EHF010100T4_01 = '"+(String)detailDataMap.get("EHF010100T4_01")+"' " +  //員工系統工號
			" AND EHF010100T4_02 = '"+Integer.parseInt((String)detailDataMap.get("EHF010100T4_02"))+"' " +  //
			" AND HR_CompanySysNo = '"+(String)detailDataMap.get("COMP_ID")+"' ";
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF010100().delEHF010100T4()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF010100().delEHF010100T4()",
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

	public void addDataDuty(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" INSERT INTO EHF010100T6 ( HR_CompanySysNo, EHF010100T6_01, EHF010100T6_02, EHF010100T6_03, EHF010100T6_04, EHF010100T6_05, " +
			" EHF010100T6_06, EHF010100T6_07, EHF010100T6_08, EHF010100T6_09, EHF010100T6_10, " +
			" EHF010100T6_11, EHF010100T6_12, EHF010100T6_13, EHF010100T6_14, EHF010100T6_15, " +
			" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_CREATE, HR_LastUpdateDate ) " +
			" VALUES (?,?,?,?,?,?," +
			" ?,?,?,?,?, " +
			" ?,?,?,?,?, " +
			" ?,?,1, NOW(), NOW() ) " ;

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T0_01"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_02"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_03"));
			indexid++;
			p6stmt.setString(indexid, ((String)dataMap.get("EHF010100T6_04"))==null?"9999/01/01":(String)dataMap.get("EHF010100T6_04")); //
			indexid++;
			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF010100T6_05"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_06"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_07"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_08"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_09"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_10"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_11"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_12"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_13"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_14"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_15"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
									
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
									
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010100().addDataDuty()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100.addDataDuty()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	public void addDataJobLife(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" INSERT INTO EHF010100T1 ( EHF010100T1_01 ,EHF010100T1_02 ,EHF010100T1_03 ,EHF010100T1_04, HR_CompanySysNo, " +
			" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_CREATE, HR_LastUpdateDate ) " +
			" VALUES (?,?,?,?,?,?,?,1, NOW(), NOW() ) " ;

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T0_01"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T1_02"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T1_03")); //
			indexid++;
			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF010100T1_04"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
									
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
									
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010106().addDataJobLife()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010106.addDataJobLife()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	public void addDataCard(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" INSERT INTO ehf020403t0 ( EHF020403T0_01 ,EHF020403T0_02 ,EHF020403T0_03 " +
			" ,EHF020403T0_04,EHF020403T0_05" +
			" ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?, 1, NOW() ) "  ;

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T0_01"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_02"));  //部門代號
			indexid++;
			p6stmt.setString(indexid, "");  //備註
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020403T0_05"));  //是否記錄考勤
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //建立人員
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
									
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
									
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010100().addDataCard()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100.addDataCard()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	public void addDataClass(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" INSERT INTO EHF010100T8 ( EHF010100T8_02 ,EHF010100T8_03 ,EHF010100T8_04 ,EHF010100T8_05 ,EHF010100T8_06 " +
			" ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?, 1, NOW() ) " ;

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_02"));	//部門組織(代號)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T0_01"));
			indexid++;
			p6stmt.setInt(indexid, (Integer)dataMap.get("EHF010100T8_04_KEY")); //班別序號
			indexid++;
			p6stmt.setString(indexid, "");	//備註
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
									
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
									
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010100().addDataClass()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100.addDataClass()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	public void addDataSalary(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			sql = "" +
			" INSERT INTO EHF030200t0 ( EHF030200T0_01, EHF030200T0_02, EHF030200T0_03, EHF030200T0_04, " +
			" EHF030200T0_05, EHF030200T0_06, EHF030200T0_06_AC, EHF030200T0_07, EHF030200T0_08, EHF030200T0_09, " +
			" EHF030200T0_10, EHF030200T0_11, EHF030200T0_12, EHF030200T0_13, EHF030200T0_14, EHF030200T0_14_TYPE, " +
			" EHF030200T0_15, EHF030200T0_16, " +
			//" EHF030200T0_17, " +//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
			" EHF030200T0_18, EHF030200T0_19, EHF030200T0_20, " +
			" EHF030200T0_21, " +
			" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, " +
			" ?,?,?, " +
			//" ?, " +//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
			" ?,?, 1, NOW() ) "  ;
			
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T0_01"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_02"));  //部門代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T0_07"));  //員工類別(正式員工)
			indexid++;
			p6stmt.setInt(indexid, 0);  //基本薪資
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030200T0_05"));  //薪資發放方式
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030200T0_06"));  //銀行代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030200T0_06_AC"));  //金融帳號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030200T0_07"));  //薪資計算方式
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)dataMap.get("EHF030200T0_08")));  //是否啟用
			indexid++;
			p6stmt.setInt(indexid, 0);  //應發薪資
			indexid++;
			p6stmt.setString(indexid, "50");  //各類所得代號(預設為薪資)
			indexid++;
			p6stmt.setInt(indexid, 0);  //各類所得(扣款金額)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030200T0_12"));  //備註
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean("0"));  //是否有代扣所得稅
			indexid++;
			p6stmt.setString(indexid, "");  //代扣類型
			indexid++;
			p6stmt.setFloat(indexid, 0);  //代扣率
			indexid++;
			p6stmt.setInt(indexid, 0);  //代扣金額
			indexid++;
			//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
			//p6stmt.setBoolean(indexid, tools.StringToBoolean(Form.getEHF030200T0_17()));  //是否計算考勤
			//indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030200T0_18"));  //加班費規則代碼
			indexid++;
			p6stmt.setInt(indexid, 0);  //全勤獎金金額
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030200T0_20"));  //全勤獎金扣費規則
			indexid++;
			p6stmt.setBoolean(indexid, tools.StringToBoolean((String)dataMap.get("EHF030200T0_21")));  //是否代扣福利金
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //建立人員
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
									
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
									
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010100().addDataSalary()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100.addDataSalary()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	public void addDataInsurance(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" INSERT INTO ehf030300t0 ( EHF030300T0_01, EHF030300T0_02, EHF030300T0_03, EHF030300T0_04, EHF030300T0_05,EHF030300T0_05_VERSION, " +
			" EHF030300T0_06, EHF030300T0_07, EHF030300T0_07_VERSION, EHF030300T0_08, EHF030300T0_09, EHF030300T0_10, EHF030300T0_11, EHF030300T0_12, " +
			" EHF030300T0_13, EHF030300T0_14, EHF030300T0_15, EHF030300T0_16, " +
			" EHF030300T0_05_START, EHF030300T0_05_END, EHF030300T0_07_START, EHF030300T0_07_END, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?,?,?,?,?,?,?,?,?, 1, NOW() ) ";

			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T0_01"));  //員工工號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_02"));  //部門組織(代碼)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T0_07"));  //員工類別
			indexid++;
			p6stmt.setInt(indexid, 0);  //基本薪資(本俸)
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030300T0_05"));  //勞保投保等級
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030300T0_05_NUMBER"));  //勞保設定檔編號  新增於  BY賴泓錡 20140120
			indexid++;
			p6stmt.setInt(indexid, 0);  //勞保員工負擔金額
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030300T0_07"));  //健保投保等級
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030300T0_07_NUMBER"));  //勞保設定檔編號 新增於  BY賴泓錡 20140120
			indexid++;
			p6stmt.setInt(indexid, 0);  //健保員工負擔金額
			indexid++;
			p6stmt.setInt(indexid, (Integer)dataMap.get("EHF030300T0_09"));  //勞健保總金額
			indexid++;
			p6stmt.setInt(indexid, (Integer)dataMap.get("EHF030300T0_10"));  //地方政府補助款
			indexid++;
			p6stmt.setInt(indexid, (Integer)dataMap.get("EHF030300T0_11"));  //口數
			indexid++;
			p6stmt.setString(indexid, "");  //備註
			indexid++;
//			p6stmt.setString(indexid, Form.getEHF030300T0_03());  //建立日期
//			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setInt(indexid, (Integer)dataMap.get("EHF030300T0_15"));  //勞退自提金額
			indexid++;
			p6stmt.setInt(indexid, 0);  //勞退公提金額
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030300T0_05_START"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030300T0_05_END"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030300T0_07_START"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030300T0_07_END"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //建立人員
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //修改人員
			indexid++;
			
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
									
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
									
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010100().addDataInsurance()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100.addDataInsurance()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	public List queryEHF020403CList(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF020403CList = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT *, " +	
			" DATE_FORMAT(EHF020403T1_04_START, '%Y/%m/%d %H：%i：%s') AS EHF020403T1_04_START, " +
			" DATE_FORMAT(EHF020403T1_04_END, '%Y/%m/%d') AS EHF020403T1_04_END, " +
			" DATE_FORMAT(EHF020403T1_04_END, '%H') AS EHF020403T1_04_END_HH," +
			" DATE_FORMAT(EHF020403T1_04_END, '%i') AS EHF020403T1_04_END_MM " +
			" FROM EHF020403T1 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"' " +
			" AND EHF020403T1_06 = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF020403CList = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF020403CList;
	}

	public List queryEHF030200CList_1(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF030200CList = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT *" +
			" FROM  EHF030200T1 " +
			" LEFT JOIN EHF010101t0 ON EHF030200T1_02 = EHF010101T0_01  AND EHF030200T1_04 = EHF010101T0_23 " +
			" LEFT JOIN EHF000400T0 ON EHF000400T0_11 = EHF030200T1_04  AND EHF030200T1_05 = EHF000400T0_03 " +
			" LEFT JOIN EHF030102T0 ON EHF030102T0_01 = EHF030200T1_02 	AND EHF030102T0_08 = EHF030200T1_04" +
			" LEFT JOIN EHF030101T0 ON EHF030101T0_01 = EHF030102T0_03 	AND EHF030101T0_05 = EHF030200T1_04" +
			" WHERE 1 = 1 " +
			" AND EHF030200T1_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"'" +
			" AND EHF030200T1_06 = '1' " +
			" AND EHF030200T1_04 = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF030200CList = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF030200CList;
	}

	public List queryEHF030200CList_2(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF030200CList = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT *" +
			" FROM  EHF030200T1 " +
			" LEFT JOIN EHF010101t0 ON EHF030200T1_02 = EHF010101T0_01  AND EHF030200T1_04 = EHF010101T0_23 " +
			" LEFT JOIN EHF000400T0 ON EHF000400T0_11 = EHF030200T1_04  AND EHF030200T1_05 = EHF000400T0_01 " +
			" LEFT JOIN EHF030102T0 ON EHF030102T0_01 = EHF030200T1_02 	AND EHF030102T0_08 = EHF030200T1_04" +
			" LEFT JOIN EHF030101T0 ON EHF030101T0_01 = EHF030102T0_03 	AND EHF030101T0_05 = EHF030200T1_04" +
			" WHERE 1 = 1 " +
			" AND EHF030200T1_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"'" +
			" AND EHF030200T1_06 = '2' " +
			" AND EHF030200T1_04 = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF030200CList = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF030200CList;
	}

	public List queryEHF030300CList(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF030300CList = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT * " +
			" FROM  EHF030300T1 " +			
			" WHERE 1 = 1 " +
			" AND EHF030300T1_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"'" +			
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF030300CList = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF030300CList;
	}

	public List queryEHF010100T5List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF010106T5List = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT * " +
			" FROM  EHF010100T5 " +			
			" WHERE 1 = 1 " +
			" AND EHF010100T5_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"'" +			
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF010106T5List = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF010106T5List;
	}

	public List queryEHF010100T3List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF010100T3List = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT *, " +
			" DATE_FORMAT(EHF010100T3_06, '%Y/%m/%d') AS EHF010100T3_06, " +
			" DATE_FORMAT(EHF010100T3_07, '%Y/%m/%d') AS EHF010100T3_07 " +
			" FROM  EHF010100T3 " +			
			" WHERE 1 = 1 " +
			" AND EHF010100T3_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"'" +			
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF010100T3List = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF010100T3List;
	}

	public List queryEHF010100T2List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF010100T2List = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT *, " +
			" DATE_FORMAT(EHF010100T2_08, '%Y/%m/%d') AS EHF010100T2_08, " +
			" DATE_FORMAT(EHF010100T2_09, '%Y/%m/%d') AS EHF010100T2_09 " +
			" FROM  EHF010100T2 " +			
			" WHERE 1 = 1 " +
			" AND EHF010100T2_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"'" +			
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF010100T2List = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF010100T2List;
	}

	public List queryEHF010100T4List(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF010100T4List = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT *, " +
			" DATE_FORMAT(EHF010100T4_05, '%Y/%m/%d') AS EHF010100T4_05, " +
			" DATE_FORMAT(EHF010100T4_06, '%Y/%m/%d') AS EHF010100T4_06 " +
			" FROM  EHF010100T4 " +			
			" WHERE 1 = 1 " +
			" AND EHF010100T4_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"'" +			
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			
			EHF010100T4List = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF010100T4List;
	}

	public void UPDATEDataDuty(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			
			sql = "" +
			" UPDATE EHF010100T6 SET " +
			" EHF010100T6_02 		= '"+(String)dataMap.get("EHF010100T6_02")+"'," 	+//部門系統代碼
			" EHF010100T6_03 	 	= '"+(String)dataMap.get("EHF010100T6_03")+"'," 		+//部門歸屬區間(起)
			" EHF010100T6_04 		= '"+(String)dataMap.get("EHF010100T6_04")+"'," 		+//部門歸屬區間(起)
			" EHF010100T6_05 		= ?," 		+//是否為主要歸屬部門
			" EHF010100T6_06 		= '"+(String)dataMap.get("EHF010100T6_06")+"'," 	+//職務系統代碼
			" EHF010100T6_07 		= '"+(String)dataMap.get("EHF010100T6_07")+"'," 	+//職務系統代碼
			" USER_UPDATE			= '"+(String)dataMap.get("USER_NAME")+"'," 			+//修改人員
			" VERSION				= VERSION+1," 										+//版本
			" HR_LastUpdateDate		= NOW()" 										+//最後修改日期
			
			" WHERE 1=1 " +
			" AND EHF010100T6_01 = ?" +  //員工代號
			" AND HR_CompanySysNo = ?";  //公司代碼
			
			PreparedStatement stmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, stmt, null, sql);

			int indexid = 1;
			p6stmt.setBoolean(indexid, (Boolean)dataMap.get("EHF010100T6_05"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T6_01"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));
			indexid++;
			
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
	
			stmt.executeUpdate();
			stmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();					
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010100().addDataDuty()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			

			
			
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100.addDataDuty()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	/**
	 * 更新職務現況
	 * @param dataMap
	 */
	public void UPDATEJobLife(Map dataMap) {
		// TODO Auto-generated method stub
		String sql = "";
		String show_sql = "";
		BA_TOOLS tool = BA_TOOLS.getInstance();
		try{
			
			sql = "" +
			" UPDATE EHF010100T1 SET " +
			//h" EHF010100T1_02 		= '"+(String)dataMap.get("EHF010100T1_02")+"'," 	+//部門系統代碼
			//h" EHF010100T1_03 	 	= '"+(String)dataMap.get("EHF010100T1_03")+"'," 		+//部門歸屬區間(起)
			" EHF010100T1_04 		= ?," 		+//部門歸屬區間(起)
			" USER_UPDATE			= '"+(String)dataMap.get("USER_NAME")+"'," 			+//修改人員
			" VERSION				= VERSION+1," 										+//版本
			" HR_LastUpdateDate		= NOW()" 										+//最後修改日期
			
			" WHERE 1=1 " +
			" AND EHF010100T1_01 = ?" +  //員工代號
			" AND HR_CompanySysNo = ?";  //公司代碼
			
			PreparedStatement stmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, stmt, null, sql);

			int indexid = 1;
			p6stmt.setBoolean(indexid, tool.StringToBoolean("1"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF010100T1_01"));
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));
			indexid++;
					
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
	
			stmt.executeUpdate();
			stmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();					
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF010100().UPDATEJobLife()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			

			
			
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF010100UPDATEJobLifee()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	public List queryEHF010300CList(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF010300CList = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT *, EHF020100T0_04, " +	
			" DATE_FORMAT(EHF010300T0_09, '%Y/%m/%d') AS EHF010300T0_09, " +
			" DATE_FORMAT(EHF010300T0_10, '%Y/%m/%d') AS EHF010300T0_10 " +
			" FROM EHF010300T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF010300T0_06 AND EHF020100T0_08 = EHF010300T0_12 " +
			" WHERE 1=1 " +
			" AND EHF010300T0_05 = '"+(String)queryCondMap.get("EHF010100T0_01")+"' " +
			" AND EHF010300T0_12 = '"+(String)queryCondMap.get("COMP_ID")+"' " +
			" ORDER BY EHF010300T0_02 DESC";
			
			EHF010300CList = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF010300CList;
	}
	
	public List queryEHF010100T1_LIST(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List EHF010100T1List = new ArrayList();
		
		try{
			//Query
			String sql = "" +
			" SELECT EHF010100T1_02, " +	
			" DATE_FORMAT(EHF010100T1_03, '%Y/%m/%d') AS EHF010100T1_03," +
			" DATE_FORMAT(DATE_CREATE, '%Y/%m/%d %H:%i:%s') AS DATE_CREATE" +
			" FROM EHF010100T1 " +
			" WHERE 1=1 " +
			" AND EHF010100T1_01 = '"+(String)queryCondMap.get("EHF010100T0_01")+"' " +
			" AND EHF010100T1_04 = '1' " +
			" AND HR_CompanySysNo = '"+(String)queryCondMap.get("COMP_ID")+"' " +
			" ORDER BY DATE_CREATE DESC";
			
			EHF010100T1List = this.base_tools.queryList(sql);
						
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF010100T1List;
	}

	
	
	/**
	 * 是否需要更新狀況明細以及是否需要更新服務年資
	 * @param employee_id
	 * @param status
	 * @param check_date
	 * @param comp_id
	 * @return
	 */
	public boolean chkJobLife(String employee_id, String status, String check_date,
			String comp_id, String type) {
		// TODO Auto-generated method stub
		
		boolean confirm = false;
		
		try{
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empInfMap = hr_tools.getEmpInfMapByEmpId("", employee_id, comp_id);
			
			hr_tools.close();
			if("01".equals(type)){
				//變更職務狀況並更新服務年資
				if(((String)empInfMap.get("CHECKDATE")).equals(check_date) && ((String)empInfMap.get("SEARCHSTATUS")).equals(status)){
					confirm = true;
				}
			}else if("02".equals(type)){
				//若只變更日期 → 不需更新服務年資
				if(!((String)empInfMap.get("CHECKDATE")).equals(check_date) && ((String)empInfMap.get("SEARCHSTATUS")).equals(status)){
					confirm = true;
				}
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return confirm;
	}
	
	
	
	
	
}
