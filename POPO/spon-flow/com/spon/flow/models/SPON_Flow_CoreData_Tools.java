package com.spon.flow.models;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;

public class SPON_Flow_CoreData_Tools implements BaseSystem {
	
	private BaseFunction base_tools;
	
	/**
	 * 建構子
	 */
	public SPON_Flow_CoreData_Tools(){
		
		try{
			//建立基本操作元件
			this.base_tools = new BaseFunction("sponflowdatabase.resourceName");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 取得FLOW資料序號
	 * @param flow_map
	 * @return
	 */
	public int getFlowSN(Map flow_map){
		
		int flow_sn = 0;
		String sql = "";
		
		try{
			sql = "" +
			" SELECT SFLOW010100T0_01 AS FLOW_SN " +
			" FROM SFLOW010100T0 " +
			" WHERE 1=1 " +
			" AND SFLOW010100T0_02 = '"+(String)flow_map.get("FLOW_NO")+"' " +  //FLOW代號
			" AND SFLOW010100T0_11 = '"+(String)flow_map.get("COMP_ID")+"' ";  //公司代碼
			
			//取的FLOW資料序號
			flow_sn = ((Integer)(this.base_tools.query(sql)).get("FLOW_SN")).intValue();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_sn;
	}
	
	/**
	 * 取得FLOW使用記錄
	 * @param flow_map
	 * @return
	 */
	public Map getFlowUseRecord(Map flow_map){
		
		Map flow_use_record_map = new HashMap();
		//int flow_use_record_sn = 0;
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010200T0_01 AS FLOW_USE_RECORD_SN, " +
			" SFLOW010200T0_02 AS FLOW_SN, " +
			" SFLOW010200T0_03 AS RECORD_OPEN_DATE, " +
			" SFLOW010200T0_04 AS FORM_NUMBER, " +
			" SFLOW010200T0_05 AS FLOW_COMPOSE_SN, " +
			" SFLOW010200T0_05 AS FLOW_STRUCTURE_DATA_SN, " +
			" SFLOW010200T0_06 AS FLOW_CURRENT_STATUS " +
			" FROM SFLOW010200T0 " +
			" WHERE 1=1 " +
			" AND SFLOW010200T0_02 = '"+(Integer)flow_map.get("FLOW_SN")+"' " +  //FLOW_SN
			" AND SFLOW010200T0_04 = '"+(String)flow_map.get("FORM_NUMBER")+"' " +  //表單單號
			" AND SFLOW010200T0_07 = '"+(String)flow_map.get("COMP_ID")+"' ";  //公司代碼
			
			//取的FLOW使用記錄序號
			flow_use_record_map = this.base_tools.query(sql);
			//flow_use_record_sn = ((Integer)(this.base_tools.query(sql)).get("FLOW_USE_RECORD_SN")).intValue();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_use_record_map;
	}
	
	/**
	 * 建立FLOW表單使用記錄
	 * @param flow_map
	 * @return
	 */
	public int createFlowUseRecord(Map flow_map){
		
		int flow_use_record_sn = 0;
		String sql = "";
		String show_sql = "";
		
		try{
			//取得FLOW資料序號
			int flow_sn = this.getFlowSN(flow_map);
			
			//建立FLOW使用記錄
			sql = "" +
			" INSERT INTO SFLOW010200T0 " +
			" ( " +
			" SFLOW010200T0_02, SFLOW010200T0_03, SFLOW010200T0_04, " +
			" SFLOW010200T0_05, SFLOW010200T0_06, SFLOW010200T0_07, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" VALUES ( " +
			" ?, NOW(), ?, " +
			" 0, '', ?, " +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setInt(indexid, flow_sn);  //FLOW資料序號
			indexid++;
			p6stmt.setString(indexid, (String)flow_map.get("FORM_NUMBER"));  //表單單號
			indexid++;
			p6stmt.setString(indexid, (String)flow_map.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)flow_map.get("USER_ID"));  //FLOW使用者
			indexid++;
			p6stmt.setString(indexid, (String)flow_map.get("USER_ID"));  //FLOW使用者
			indexid++;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//取得 委外加工單資料序號
			flow_use_record_sn = this.base_tools.getDb_tools().getLastInsertId(this.base_tools.getConn());
			
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_use_record_sn;
	}
	
	/**
	 * 輸入FLOW參數值
	 * @param condMap
	 */
	public void putFlowParameterValue(Map condMap){
		//設定FLOW參數
		try{
			//複製condMap做為新的參數值Map
			Map new_param = new HashMap();
			new_param.putAll(condMap);
			
			//取得目前已存在的參數, 並且取得是否可更新資料的flag
			List exist_param = this.getExistFlowParameterList(condMap);
			Iterator it = exist_param.iterator();
			Map param = null;
			
			//依據取得的已存在參數代碼, 取得condMap的新值進行更新
			while(it.hasNext()){
				
				param = (Map)it.next();
				
				if((Boolean)param.get("ALLOW_UPDATE_FLAG") 
				    && condMap.containsKey((String)param.get("PARAM_NO"))){
					//更新已存在的參數值
					param.put("PARAM_VALUE", (String)condMap.get((String)param.get("PARAM_NO")));
					param.put("FLOW_USE_SN", (Integer)condMap.get("FLOW_USE_SN"));
					param.put("COMP_ID", (String)condMap.get("COMP_ID"));
					param.put("USER_ID", (String)condMap.get("USER_ID"));
					this.updateFlowParameterValue(param);
				}
				
				//移除已存在的參數 from 新參數Map
				new_param.remove((String)param.get("PARAM_NO"));
			}
			
			//依據condMap目前不存在的參數, 建立參數值
			Set new_param_key = new_param.keySet();
			it = new_param_key.iterator();
			Map new_param_cond = null;
			
			while(it.hasNext()){
				//建立新參數值
				new_param_cond = new HashMap();
				new_param_cond.put("PARAM_NO", (String)it.next());
				new_param_cond.put("PARAM_VALUE", (String)new_param.get((String)new_param_cond.get("PARAM_NO")));
				new_param_cond.put("FLOW_USE_SN", (Integer)condMap.get("FLOW_USE_SN"));
				new_param_cond.put("COMP_ID", (String)condMap.get("COMP_ID"));
				new_param_cond.put("USER_ID", (String)condMap.get("USER_ID"));
				this.insertFlowParameterValue(new_param_cond);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 取得已存在的FLOW參數清單
	 * @param flow_map
	 * @return
	 */
	public List getExistFlowParameterList(Map flow_map){
		
		List dataList = null;
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010100T1_03 AS PARAM_NO, " +
			" SFLOW010200T1_03 AS PARAM_VALUE, " +
			" SFLOW010100T1_09 AS ALLOW_UPDATE_FLAG " +
			" FROM SFLOW010100T1 " +
			" LEFT JOIN SFLOW010200T0 ON SFLOW010200T0_02 = SFLOW010100T1_01 " +
			" LEFT JOIN SFLOW010200T1 ON SFLOW010200T1_01 = SFLOW010200T0_01 " +
			" AND SFLOW010200T1_02 = SFLOW010100T1_03 " +
			" WHERE 1=1 " +
			" AND SFLOW010200T1_01 = '"+(Integer)flow_map.get("FLOW_USE_SN")+"' " +  //FLOW表單使用資料序號
			" AND SFLOW010200T1_02 IS NOT NULL ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			
			pstmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 新增FLOW參數設定值
	 * @param param_map
	 */
	public void insertFlowParameterValue(Map param_map){
		
		String sql = "";
		String show_sql = "";
		
		try{
			if(!this.getFlowParameter(param_map).isEmpty()){
				//如果有取到FLOW參數, 才執行新增
				sql = "" +
				" INSERT INTO SFLOW010200T1 " +
				" ( " +
				" SFLOW010200T1_01, SFLOW010200T1_02, SFLOW010200T1_03, SFLOW010200T1_04, " +
				" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
				" VALUE ( " +
				" ?, ?, ?, ?, " +
				" ?, ?, 1, NOW(), NOW() " +
				" ) ";
				
				//執行SQL
				PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,
						pstmt, null, sql);
				int indexid = 1;
				p6stmt.setInt(indexid, (Integer)param_map.get("FLOW_USE_SN"));  //FLOW表單使用序號
				indexid++;
				p6stmt.setString(indexid, (String)param_map.get("PARAM_NO"));  //參數代碼
				indexid++;
				p6stmt.setString(indexid, (String)param_map.get("PARAM_VALUE"));  //參數設定值
				indexid++;
				p6stmt.setString(indexid, (String)param_map.get("COMP_ID"));  //公司代碼
				indexid++;
				p6stmt.setString(indexid, (String)param_map.get("USER_ID"));  //FLOW使用者
				indexid++;
				p6stmt.setString(indexid, (String)param_map.get("USER_ID"));  //FLOW使用者
				indexid++;
				
				System.out.println(p6stmt.getQueryFromPreparedStatement());
				show_sql = p6stmt.getQueryFromPreparedStatement();
				//執行
				p6stmt.executeUpdate();
				
				pstmt.close();
				p6stmt.close();
				
				//更新資料庫
				this.base_tools.commit();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 更新FLOW參數設定值
	 * @param param_map
	 */
	public void updateFlowParameterValue(Map param_map){
		
		String sql = "";
		String show_sql = "";
		
		try{
			//如果有取到FLOW參數, 才執行新增
			sql = "" +
			" UPDATE SFLOW010200T1 SET " +
			" SFLOW010200T1_03 = ?, " +
			" USER_UPDATE = ?, VERSION = VERSION+1, DATE_UPDATE = NOW() " +
			" WHERE 1=1 " +
			" AND SFLOW010200T1_01 = ? " +  //FLOW表單使用記錄序號
			" AND SFLOW010200T1_02 = ? ";  //參數代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)param_map.get("PARAM_VALUE"));  //參數設定值
			indexid++;
			p6stmt.setString(indexid, (String)param_map.get("USER_ID"));  //FLOW使用者
			indexid++;
			p6stmt.setString(indexid, (String)param_map.get("PARAM_NO"));  //參數代碼
			indexid++;
			p6stmt.setInt(indexid, (Integer)param_map.get("FLOW_USE_SN"));  //FLOW表單使用序號
			indexid++;;
			
			System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 取得FLOW基本資訊
	 * @param condMap
	 * @return
	 */
	public Map getFlowBasicData(Map condMap){
		
		Map flow_basic_map = new HashMap();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010100T0_01 AS FLOW_SN, " +
			" SFLOW010100T0_02 AS FLOW_NO, " +
			" SFLOW010100T0_03 AS FLOW_NAME, " +
			" SFLOW010100T0_04 AS FLOW_CAPTION, " +
			" SFLOW010100T0_05 AS FLOW_START_DATE, " +
			" SFLOW010100T0_06 AS FLOW_END_DATE, " +
			" SFLOW010100T0_07 AS RELEASE_FLAG, " +
			" SFLOW010100T0_08 AS RELEASE_DATE, " +
			" SFLOW010100T0_09 AS ALLOW_USE_COMMON_PARAM, " +
			" SFLOW010100T0_10 AS FLOW_VERSION " +
			" FROM SFLOW010100T0 " +
			" WHERE 1=1 ";
			
			//如果有FLOW資料序號, 就使用訊號進行查詢
			if(condMap.containsKey("FLOW_SN")){
				sql += "" +
				" AND SFLOW010100T0_01 = '"+(Integer)condMap.get("FLOW_SN")+"' ";  //FLOW_SN
			}else{
				sql += "" +
				" AND SFLOW010100T0_02 = '"+(String)condMap.get("FLOW_NO")+"' " +  //FLOW_NO
				" AND SFLOW010100T0_11 = '"+(String)condMap.get("COMP_ID")+"' ";  //公司代碼
			}
			
			//執行SQL
			flow_basic_map = this.base_tools.query(sql);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_basic_map;
	}
	
	/**
	 * 取得指定的FLOW參數設定(single parameter)
	 * @param param_map
	 * @return
	 */
	public Map getFlowParameter(Map param_map){
		
		String sql = "";
		Map dataMap = new HashMap();
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010100T1_03 AS PARAM_NO, " +
			" SFLOW010100T1_04 AS PARAM_NAME, " +
			" SFLOW010100T1_05 AS PARAM_CAPTION, " +
			" SFLOW010100T1_06 AS NECESSARY_FLAG, " +
			" SFLOW010100T1_07 AS DATA_TYPE, " +
			" SFLOW010100T1_08 AS DEFAULT_VALUE, " +
			" SFLOW010100T1_09 AS ALLOW_UPDATE_FLAG " +
			" FROM SFLOW010100T1 " +
			" WHERE 1=1 " +
			" AND SFLOW010100T1_01 = '"+(Integer)param_map.get("FLOW_SN")+"' " +  //FLOW_SN
			" AND SFLOW010100T1_03 = '"+(String)param_map.get("PARAM_NO")+"' ";  //參數代碼
			
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	/**
	 * 取得FLOW參數清單(List)
	 * @param param_map
	 * @return
	 */
	public List getFlowParameters(Map condMap){
		
		List dataList = new ArrayList();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010100T1_01 AS FLOW_SN, " +
			" SFLOW010100T1_02 AS PARAM_DATA_SN, " +
			" SFLOW010100T1_03 AS PARAM_NO, " +
			" SFLOW010100T1_04 AS PARAM_NAME, " +
			" SFLOW010100T1_05 AS PARAM_CAPTION, " +
			" SFLOW010100T1_06 AS NECESSARY_FLAG, " +
			" SFLOW010100T1_07 AS DATA_TYPE, " +
			" SFLOW010100T1_08 AS DEFAULT_VALUE, " +
			" SFLOW010100T1_09 AS ALLOW_UPDATE_FLAG " +
			" FROM SFLOW010100T1 " +
			" WHERE 1=1 " +
			" AND SFLOW010100T1_01 = '"+(Integer)condMap.get("FLOW_SN")+"' ";  //FLOW_SN
			
			//執行SQL
			dataList = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取得指定的FLOW狀態資訊
	 * @param status_map
	 * @return
	 */
	public Map getFlowFormStatus(Map status_map){
		
		Map flow_status_map = new HashMap();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010100T2_01 AS FLOW_SN, " +
			" SFLOW010100T2_02 AS FLOW_FORM_STATUS_NO, " +
			" SFLOW010100T2_03 AS FLOW_FORM_STATUS_NAME, " +
			" SFLOW010100T2_04 AS FLOW_FORM_STATUS_CAPTION " +
			" FROM SFLOW010100T2 " +
			" WHERE 1=1 " +
			" AND SFLOW010100T2_01 = '"+(Integer)status_map.get("FLOW_SN")+"' " +  //FLOW_SN
			" AND SFLOW010100T2_02 = '"+(String)status_map.get("FLOW_FORM_STATUS_NO")+"' ";  //FLOW表單狀態代碼
			
			//執行SQL
			flow_status_map = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_status_map;
	}
	
	/**
	 * 取得FLOW狀態清單
	 * @param condMap
	 * @return
	 */
	public List getFlowFormStatusList(Map condMap){
		
		List dataList = new ArrayList();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010100T2_01 AS FLOW_SN, " +
			" SFLOW010100T2_02 AS FLOW_FORM_STATUS_NO, " +
			" SFLOW010100T2_03 AS FLOW_FORM_STATUS_NAME, " +
			" SFLOW010100T2_04 AS FLOW_FORM_STATUS_CAPTION " +
			" FROM SFLOW010100T2 " +
			" WHERE 1=1 " +
			" AND SFLOW010100T2_01 = '"+(Integer)condMap.get("FLOW_SN")+"' ";  //FLOW_SN
			
			//執行SQL
			dataList = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取得FLOW結構定義清單(List)
	 * @param flow_structure_map
	 * @return
	 */
	public List getFlowStructureDefinitionList(Map flow_structure_map){
		
		List dataList = new ArrayList();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010100T3_01 AS FLOW_SN, " +
			" SFLOW010100T3_02 AS FLOW_STRUCTURE_DATA_SN, " +
			" SFLOW010100T3_03 AS FLOW_STRUCTURE_TYPE, " +
			" SFLOW010100T3_04 AS FLOW_STRUCTURE_KEY " +
			" FROM SFLOW010100T3 " +
			" WHERE 1=1 " +
			" AND SFLOW010100T3_01 = '"+(Integer)flow_structure_map.get("FLOW_SN")+"' ";  //FLOW_SN
			
			//執行SQL
			dataList = this.base_tools.queryList(sql);
			
			//取得站別資訊
			Iterator it = dataList.iterator();
			Map flow_strucure_definition_map = null;
			
			while(it.hasNext()){
				
				flow_strucure_definition_map = (Map)it.next();
				
				//取得FLOW結構定義詳細資訊, 置放到目前資料Map
				flow_strucure_definition_map.putAll(
				this.getFlowStructureDefinition(flow_strucure_definition_map));
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取得指定的FLOW結構定義(Map)
	 * @param flow_structure_map
	 * @return
	 */
	public Map getFlowStructureDefinition(Map flow_structure_map){
		
		Map flow_strucure_definition_map = new HashMap();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010100T3_01 AS FLOW_SN, " +
			" SFLOW010100T3_02 AS FLOW_STRUCTURE_DATA_SN, " +
			" SFLOW010100T3_03 AS FLOW_STRUCTURE_TYPE, " +
			" SFLOW010100T3_04 AS FLOW_STRUCTURE_KEY " +
			" FROM SFLOW010100T3 " +
			" WHERE 1=1 " +
			" AND SFLOW010100T3_01 = '"+(Integer)flow_structure_map.get("FLOW_SN")+"' " +  //FLOW_SN
			" AND SFLOW010100T3_02 = '"+(Integer)flow_structure_map.get("FLOW_STRUCTURE_DATA_SN")+"' ";  //順序序號
			
			//執行SQL
			flow_strucure_definition_map = this.base_tools.query(sql);
			
			//取得站別資訊
			if("01".equals((String)flow_strucure_definition_map.get("FLOW_STRUCTURE_TYPE"))){
				//結構類型:單一站別
				flow_strucure_definition_map.put("FLOW_STATION_SN", 
				Integer.parseInt((String)flow_strucure_definition_map.get("FLOW_STRUCTURE_KEY")));
				//取得單一站別資訊
				flow_strucure_definition_map.put("FLOW_STRUCTURE_DATA_NAME", "單一站別");
				flow_strucure_definition_map.put("FLOW_STRUCTURE_DATA", 
												 this.getFlowStationData(flow_strucure_definition_map));
			}else{
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_strucure_definition_map;
		
	}
	
	/**
	 * 取得FLOW站別資訊(Map)
	 * @param station_map
	 * @return
	 */
	public Map getFlowStationData(Map station_map){
		
		Map flow_station_map = new HashMap();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010101T0_01 AS FLOW_STATION_SN, " +
			" SFLOW010101T0_02 AS FLOW_SN, " +
			" SFLOW010101T0_03 AS FLOW_STATION_NAME, " +
			" SFLOW010101T0_04 AS FLOW_STATION_TYPE, " +
			" SFLOW010101T0_05 AS FLOW_STATION_KEY, " +
			" SFLOW010101T0_06 AS FLOW_STATION_CAPTION " +
			" FROM SFLOW010101T0 " +
			" WHERE 1=1 " +
			" AND SFLOW010101T0_01 = '"+(Integer)station_map.get("FLOW_STATION_SN")+"' ";  //FLOW站別序號
			
			//執行SQL
			flow_station_map = this.base_tools.query(sql);
			
			//取得站別的詳細資訊
			if("01".equals((String)flow_station_map.get("FLOW_STATION_TYPE"))){
				//站別型態:簽核站別
				flow_station_map.put("FLOW_SIGN_STATION_SN", 
				Integer.parseInt((String)flow_station_map.get("FLOW_STATION_KEY")));
				//取得簽核站別資訊
				flow_station_map.put("FLOW_STATION_DATA_NAME", "簽核站別");
				flow_station_map.put("FLOW_STATION_DATA",
									 this.getFlowSignStationData(flow_station_map));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_station_map;
	}
	
	/**
	 * 取得簽核站別資訊(Map)
	 * @param sign_station_map
	 * @return
	 */
	public Map getFlowSignStationData(Map sign_station_map){
		
		Map flow_sign_station_map = new HashMap();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010102T0_01 AS FLOW_SIGN_STATION_SN, " +
			" SFLOW010102T0_02 AS FLOW_STATION_SN, " +
			" SFLOW010102T0_03 AS PASS_CONDITION_TYPE, " +
			" SFLOW010102T0_04 AS PASS_CONDITION_VALUE " +
			" FROM SFLOW010102T0 " +
			" WHERE 1=1 " +
			" AND SFLOW010102T0_01 = '"+(Integer)sign_station_map.get("FLOW_SIGN_STATION_SN")+"' ";  //FLOW簽核站別序號
			
			//執行SQL
			flow_sign_station_map = this.base_tools.query(sql);
			
			//取得當站人員資訊清單
			flow_sign_station_map.put("FLOW_SIGN_STATION_STAFF_LIST", 
									  this.getFlowSignStationStaffList(sign_station_map));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_sign_station_map;
	}
	
	/**
	 * 取得簽核站別當站人員清單(List)
	 * @param condMap
	 * @return
	 */
	public List getFlowSignStationStaffList(Map condMap){
		
		List dataList = new ArrayList();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010102T1_01 AS FLOW_SIGN_STATION_STAFF_SN, " +
			" SFLOW010102T1_02 AS FLOW_SIGN_STATION_SN, " +
			" SFLOW010102T1_03 AS FLOW_SIGN_STATION_STAFF_TYPE, " +
			" SFLOW010102T1_04 AS FLOW_SIGN_STATION_STAFF_SOURCE_TYPE, " +
			" SFLOW010102T1_05 AS FLOW_SIGN_STATION_STAFF_KEY, " +
			" SFLOW010102T1_06 AS FLOW_SIGN_STATION_STAFF_PERMISSION " +
			" FROM SFLOW010102T1 " +
			" WHERE 1=1 " +
			" AND SFLOW010102T1_02 = '"+(Integer)condMap.get("FLOW_SIGN_STATION_SN")+"' ";  //FLOW簽核站別序號
			
			//執行SQL
			dataList = this.base_tools.queryList(sql);
			
			//取得簽核站別當站人員的可執行動作資訊
			Iterator it = dataList.iterator();
			Map staff_map = null;
			List staff_useable_action_list = null;
			
			while(it.hasNext()){
				
				//取得當站人員資訊
				staff_map = (Map)it.next();
				
				//依據當站人員資訊取得 -> 當站人員可執行動作清單資訊
				staff_useable_action_list = 
				this.getFlowSignStationStaffUseableActionList(staff_map);
				
				//加入簽核站別當站人員可執行動作清單
				staff_map.put("FLOW_SIGN_STATION_STAFF_USEABLE_ACTION_LIST",
							  staff_useable_action_list);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取得FLOW簽核站別當站人員可執行動作清單(List)
	 * @param condMap
	 * @return
	 */
	public List getFlowSignStationStaffUseableActionList(Map condMap){
		
		List dataList = new ArrayList();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW010102T2_01 AS FLOW_SIGN_STATION_STAFF_SN, " +
			" SFLOW010102T2_02 AS FLOW_ACTION_NO, " +
			" SFLOW010102T2_03 AS ALLOW_CHANGE_FORM_STATUS_FLAG, " +
			" SFLOW010102T2_04 AS CHANGE_STATUS_NO " +
			//之後要考慮可直接取得FLOW表單狀態名稱
			" FROM SFLOW010102T2 " +
			" WHERE 1=1 " +
			" AND SFLOW010102T2_01 = '"+(Integer)condMap.get("FLOW_SIGN_STATION_STAFF_SN")+"' ";  //FLOW簽核站別當站人員序號
			
			//執行SQL
			dataList = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取的FLOW目前的站別資訊
	 * @param flow_use_record_map
	 * @return
	 */
	public Map getFlowCurrentStationData(Map flow_use_record_map){
		
		Map station_map = new HashMap();
		
		try{
			//取得當站的結構定義
			station_map = this.getFlowStructureDefinition(flow_use_record_map);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return station_map;
	}
	
	
	/**
	 * 判斷元件是否已關閉
	 */
	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return this.base_tools.isClosed();
	}
	
	/**
	 * 關閉元件
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.base_tools.close();
	}
	
	
}
