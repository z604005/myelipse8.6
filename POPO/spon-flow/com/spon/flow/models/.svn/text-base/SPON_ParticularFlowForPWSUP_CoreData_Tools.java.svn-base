package com.spon.flow.models;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;

public class SPON_ParticularFlowForPWSUP_CoreData_Tools implements BaseSystem {
	
	private BaseFunction base_tools;
	
	/**
	 * 建構子
	 */
	public SPON_ParticularFlowForPWSUP_CoreData_Tools(){
		
		try{
			//建立基本操作元件
			this.base_tools = new BaseFunction();
			
			//設定資料庫連線檔案 -> sponflowdatabase.resourceName(指定FLOW資料庫名稱)
			if(this.base_tools != null){
				this.base_tools.setDatabase_resource_name("sponflowdatabase.resourceName");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 建立待簽核紀錄
	 * @param log_map
	 */
	public void writeFlowLog(Map log_map){
		
		String sql = "";
		String show_sql = "";
		
		try{
			//順序號碼
			int FLOW_LOG_SN = this.base_tools.getMaxSN("SFLOW_LOG_T0_03", "SFLOW_LOG_T0",
							  " AND SFLOW_LOG_T0_01 = '"+(String)log_map.get("FLOW_NO")+"' " +
							  " AND SFLOW_LOG_T0_02 = '"+(String)log_map.get("FORM_NO")+"' ");
			
			sql = "" +
			" INSERT INTO SFLOW_LOG_T0 " +
			" ( " +
			" SFLOW_LOG_T0_01, SFLOW_LOG_T0_02, SFLOW_LOG_T0_03, SFLOW_LOG_T0_04, " +
			" SFLOW_LOG_T0_05, SFLOW_LOG_T0_06, SFLOW_LOG_T0_07, " +
			" SFLOW_LOG_T0_09, SFLOW_LOG_T0_10, SFLOW_LOG_T0_11, SFLOW_LOG_T0_12, " +
			" SFLOW_LOG_T0_13, SFLOW_LOG_T0_14, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, ?, ?, " +
			" NOW(), ?, ?, " +
			" '', ?, ?, '', " +
			" '', ?, " +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)log_map.get("FLOW_NO"));  //FLOW編號
			indexid++;
			p6stmt.setString(indexid, (String)log_map.get("FORM_NO"));  //表單編號
			indexid++;
			p6stmt.setInt(indexid, FLOW_LOG_SN);  //簽核順序號碼
			indexid++;
			p6stmt.setInt(indexid, (Integer)log_map.get("FLOW_SITE_SN"));  //站別順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)log_map.get("SIGN_USER_ID"));  //簽核者
			indexid++;
			p6stmt.setString(indexid, (String)log_map.get("SIGN_COMMENT"));  //簽核意見
			indexid++;
			p6stmt.setString(indexid, (String)log_map.get("BEFORE_SIGN_FORM_STATUS_NO"));  //更改前表單狀態代碼
			indexid++;
			p6stmt.setString(indexid, (String)log_map.get("BEFORE_SIGN_FORM_STATUS_NAME"));  //更改前表單狀態名稱
			indexid++;
			p6stmt.setString(indexid, (String)log_map.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)log_map.get("USER_ID"));  //建立人員
			indexid++;
			p6stmt.setString(indexid, (String)log_map.get("USER_ID"));  //修改人員
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
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
	 * 簽核 -> 寫入簽核資料到簽核LOG中
	 * @param sign_log_map
	 */
	public void signFlowLog(Map sign_log_map){
		
		String sql = "";
		String show_sql = "";
		
		try{
			sql += "" +
			" UPDATE SFLOW_LOG_T0 SET " +
			" SFLOW_LOG_T0_07=?, SFLOW_LOG_T0_08=NOW(), SFLOW_LOG_T0_09=?, " +
			" SFLOW_LOG_T0_12=?, SFLOW_LOG_T0_13=?, " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND SFLOW_LOG_T0_01=? " +  //FLOW編號
			" AND SFLOW_LOG_T0_02=? " +  //表單單號
			" AND SFLOW_LOG_T0_03=? " +  //簽核順序號碼
			" AND SFLOW_LOG_T0_14=? ";  //公司代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)sign_log_map.get("SIGN_COMMENT"));  //簽核意見
			indexid++;
			p6stmt.setString(indexid, (String)sign_log_map.get("SIGN_ACTION_NAME"));  //執行動作名稱
			indexid++;
			p6stmt.setString(indexid, (String)sign_log_map.get("AFTER_SIGN_FORM_STATUS_NO"));  //更改後表單狀態代碼
			indexid++;
			p6stmt.setString(indexid, (String)sign_log_map.get("AFTER_SIGN_FORM_STATUS_NAME"));  //更改後表單狀態名稱
			indexid++;
			p6stmt.setString(indexid, (String)sign_log_map.get("USER_ID"));  //修改人員
			indexid++;
			p6stmt.setString(indexid, (String)sign_log_map.get("FLOW_NO"));  //FLOW編號
			indexid++;
			p6stmt.setString(indexid, (String)sign_log_map.get("FORM_NO"));  //表單單號
			indexid++;
			p6stmt.setInt(indexid, (Integer)sign_log_map.get("FLOW_LOG_SN"));  //簽核順序號碼
			indexid++;
			p6stmt.setString(indexid, (String)sign_log_map.get("COMP_ID"));  //公司代碼
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
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
	 * 取得FLOW表單當前待簽核LOG(Map)
	 * @param flow_no
	 * @param form_no
	 * @return
	 */
	public Map getCurrentFlowLog(String flow_no, String form_no, String comp_id){
		
		Map current_sign_map = new HashMap();
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW_LOG_T0_01 AS FLOW_NO, " +
			" SFLOW_LOG_T0_02 AS FORM_NO, " +
			" SFLOW_LOG_T0_03 AS FLOW_LOG_SN, " +
			" SFLOW_LOG_T0_04 AS FLOW_SITE_SN, " +
			" SFLOW_LOG_T0_05 AS FLOW_SEND_TO_SIGN_DATE, " +
			" SFLOW_LOG_T0_06 AS SIGN_USER_ID, " +
			" '' AS SIGN_COMMENT, " +
			" '' AS SIGN_DATETIME, " +
			" '簽核中' AS SIGN_ACTION_NAME, " +
			" SFLOW_SITE_T0_04 AS AFTER_SIGN_FORM_STATUS_NO, " +
			" SFLOW_SITE_T0_05 AS AFTER_SIGN_FORM_STATUS_NAME, " +
			" SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE, " +  //處理人員的指定Key值
			" SC0030_04 AS SIGN_USER_NAME " +
			" FROM SFLOW_LOG_T0 " +
			" LEFT JOIN SFLOW_SITE_T0 ON SFLOW_SITE_T0_01 = SFLOW_LOG_T0_01 " +
			" AND SFLOW_SITE_T0_02 = SFLOW_LOG_T0_04 AND SFLOW_SITE_T0_06 = SFLOW_LOG_T0_14 " +
			" LEFT JOIN SC0030 ON SC0030_01 = SFLOW_LOG_T0_06 AND SC0030_14 = SFLOW_LOG_T0_14 " +
			" WHERE 1=1 " +
			" AND SFLOW_LOG_T0_01 = '"+flow_no+"' " +  //FLOW編號
			" AND SFLOW_LOG_T0_02 = '"+form_no+"' " +  //表單單號
			" AND SFLOW_LOG_T0_14 = '"+comp_id+"' " +  //公司代碼
			" AND SFLOW_LOG_T0_08 IS NULL ";
			
			current_sign_map = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return current_sign_map;
	}
	
	/**
	 * 取得FLOW表單已完成的簽核LOG(List)
	 * @param flow_no
	 * @param form_no
	 * @param comp_id
	 * @return
	 */
	public List getDoneFlowLogList(String flow_no, String form_no, String comp_id){
		
		List dataList = new ArrayList();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW_LOG_T0_01 AS FLOW_NO, " +
			" SFLOW_LOG_T0_02 AS FORM_NO, " +
			" SFLOW_LOG_T0_03 AS FLOW_LOG_SN, " +
			" SFLOW_LOG_T0_04 AS FLOW_SITE_SN, " +
			" SFLOW_LOG_T0_05 AS FLOW_SEND_TO_SIGN_DATE, " +
			" SFLOW_LOG_T0_06 AS SIGN_USER_ID, " +
			" SFLOW_LOG_T0_07 AS SIGN_COMMENT, " +
			//" SFLOW_LOG_T0_08 AS SIGN_DATETIME, " +
			" DATE_FORMAT(SFLOW_LOG_T0_08, '%Y/%m/%d %H：%i：%s') AS SIGN_DATETIME, " +
			" SFLOW_LOG_T0_09 AS SIGN_ACTION_NAME, " +
			" SFLOW_LOG_T0_10 AS BEFORE_SIGN_FORM_STATUS_NO, " +
			" SFLOW_LOG_T0_11 AS BEFORE_SIGN_FORM_STATUS_NAME, " +
			" SFLOW_LOG_T0_12 AS AFTER_SIGN_FORM_STATUS_NO, " +
			" SFLOW_LOG_T0_13 AS AFTER_SIGN_FORM_STATUS_NAME, " +
			" SFLOW_SITE_T0_03 AS FLOW_SITE_NAME, " +
			" SFLOW_SITE_T0_04 AS FLOW_SITE_STATUS_NO, " +
			" SFLOW_SITE_T0_05 AS FLOW_SITE_STATUS_NAME, " +
			" SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE, " +  //處理人員的指定Key值
			" SC0030_04 AS SIGN_USER_NAME " +
			" FROM SFLOW_LOG_T0 " +
			" LEFT JOIN SFLOW_SITE_T0 ON SFLOW_SITE_T0_01 = SFLOW_LOG_T0_01 " +
			" AND SFLOW_SITE_T0_02 = SFLOW_LOG_T0_04 AND SFLOW_SITE_T0_06 = SFLOW_LOG_T0_14 " +
			" LEFT JOIN SC0030 ON SC0030_01 = SFLOW_LOG_T0_06 AND SC0030_14 = SFLOW_LOG_T0_14 " +
			" WHERE 1=1 " +
			" AND SFLOW_LOG_T0_01 = '"+flow_no+"' " +  //FLOW編號
			" AND SFLOW_LOG_T0_02 = '"+form_no+"' " +  //表單單號
			" AND SFLOW_LOG_T0_14 = '"+comp_id+"' " +  //公司代碼
			" AND SFLOW_LOG_T0_08 IS NOT NULL " +  //簽核時間
			" AND SFLOW_LOG_T0_12 <> '' " +  //更改後表單狀態代碼
			" AND SFLOW_LOG_T0_13 <> '' " +  //更改後表單狀態名稱
			" ORDER BY SFLOW_LOG_T0_03 ";  
			
			//執行SQL
			dataList = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取得FLOW表單簽核歷程
	 * @param flow_no
	 * @param form_no
	 * @param comp_id
	 * @return
	 */
	public List getFlowLogList(String flow_no, String form_no, String comp_id){
		
		List dataList = new ArrayList();
		
		try{
			//取得FLOW表單已完成的簽核LOG(List)
			dataList = this.getDoneFlowLogList(flow_no, form_no, comp_id);
			
			//取得FLOW表單當前待簽核LOG(Map)
			Map current_log_map = this.getCurrentFlowLog(flow_no, form_no, comp_id);
			//判斷是否存在當前待簽核LOG
			if(current_log_map != null && !current_log_map.isEmpty()){
				//將當前待簽核LOG加入List中
				dataList.add(current_log_map);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取得FLOW表單當前狀態資訊(Map)
	 * @param flow_no
	 * @param form_no
	 * @param comp_id
	 * @return
	 */
	public Map getCurrentFlowFormStatus(String flow_no, String form_no, String comp_id){
		
		Map current_flow_form_status_map = new HashMap();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" B.SFLOW_LOG_T0_01 AS FLOW_NO, " +
			" B.SFLOW_LOG_T0_02 AS FORM_NO, " +
			" B.SFLOW_LOG_T0_03 AS FLOW_LOG_SN, " +
			" B.SFLOW_LOG_T0_04 AS FLOW_SITE_SN, " +
			" B.SFLOW_LOG_T0_05 AS FLOW_SEND_TO_SIGN_DATE, " +
			" B.SFLOW_LOG_T0_06 AS SIGN_USER_ID, " +
			" B.SFLOW_LOG_T0_07 AS SIGN_COMMENT, " +
			" B.SFLOW_LOG_T0_08 AS SIGN_DATETIME, " +
			" IF((B.SFLOW_LOG_T0_08 IS NULL), true, false) AS HAVE_CURRENT_SIGN_USER_FLAG, " +  //判斷FLOW表單是否有當前待簽核者
			" B.SFLOW_LOG_T0_09 AS SIGN_ACTION_NAME, " +
			" IFNULL(A.SFLOW_LOG_T0_09, '') AS PREVIOUS_SIGN_ACTION_NAME, " +
			" B.SFLOW_LOG_T0_10 AS BEFORE_SIGN_FORM_STATUS_NO, " +
			" B.SFLOW_LOG_T0_11 AS BEFORE_SIGN_FORM_STATUS_NAME, " +
			" B.SFLOW_LOG_T0_12 AS AFTER_SIGN_FORM_STATUS_NO, " +
			" B.SFLOW_LOG_T0_13 AS AFTER_SIGN_FORM_STATUS_NAME, " +
			" IF((B.SFLOW_LOG_T0_08 IS NULL), B.SFLOW_LOG_T0_10, B.SFLOW_LOG_T0_12) AS CURRENT_SIGN_FORM_STATUS_NO, " +  //判斷當前表單狀態代碼 
			" IF((B.SFLOW_LOG_T0_08 IS NULL), B.SFLOW_LOG_T0_11, B.SFLOW_LOG_T0_13) AS CURRENT_SIGN_FORM_STATUS_NAME, " +  //判斷當前表單狀態名稱
			" SFLOW_SITE_T0_03 AS FLOW_SITE_NAME, " +
			" SFLOW_SITE_T0_04 AS FLOW_SITE_STATUS_NO, " +
			" SFLOW_SITE_T0_05 AS FLOW_SITE_STATUS_NAME, " +
			" SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE " +  //處理人員的指定Key值
			" FROM SFLOW_LOG_T0 B " +
			" LEFT JOIN SFLOW_SITE_T0 ON SFLOW_SITE_T0_01 = B.SFLOW_LOG_T0_01 " +
			" AND SFLOW_SITE_T0_02 = B.SFLOW_LOG_T0_04 AND SFLOW_SITE_T0_06 = B.SFLOW_LOG_T0_14 " +
			" LEFT JOIN SFLOW_LOG_T0 A ON A.SFLOW_LOG_T0_01 = B.SFLOW_LOG_T0_01 " +
			" AND A.SFLOW_LOG_T0_02 = B.SFLOW_LOG_T0_02 AND A.SFLOW_LOG_T0_14 = B.SFLOW_LOG_T0_14 " +
			" AND A.SFLOW_LOG_T0_03 = (B.SFLOW_LOG_T0_03 - 1) " +
			" WHERE 1=1 " +
			" AND B.SFLOW_LOG_T0_01 = '"+flow_no+"' " +  //FLOW編號
			" AND B.SFLOW_LOG_T0_02 = '"+form_no+"' " +  //表單單號
			" AND B.SFLOW_LOG_T0_14 = '"+comp_id+"' " +  //公司代碼
			" ORDER BY B.SFLOW_LOG_T0_03 DESC " +
			" LIMIT 1 ";  //取出一筆  
			
			current_flow_form_status_map = this.base_tools.query(sql);
			
			//判斷是否有取得資料, 如果沒有表示該FLOW沒有此一FORM_NO的記錄
			//則此狀況當作FLOW表單正在填寫中
			if(current_flow_form_status_map == null
			   || current_flow_form_status_map.isEmpty()){
				//建立填寫中的FLOW表單資訊
				current_flow_form_status_map = new HashMap();
				current_flow_form_status_map.put("FLOW_NO", flow_no);
				current_flow_form_status_map.put("FORM_NO", form_no);
				current_flow_form_status_map.put("HAVE_CURRENT_SIGN_USER_FLAG", new Boolean(false)); //沒有當前待簽核者
				current_flow_form_status_map.put("SIGN_USER_ID", "");
				current_flow_form_status_map.put("SIGN_ACTION_NAME", "");
				current_flow_form_status_map.put("PREVIOUS_SIGN_ACTION_NAME", "");
				current_flow_form_status_map.put("CURRENT_SIGN_FORM_STATUS_NO", "01");
				current_flow_form_status_map.put("CURRENT_SIGN_FORM_STATUS_NAME", "填寫中");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return current_flow_form_status_map;
	}
	
	/**
	 * 取得人員當前待簽核表單清單(List)
	 * @param flow_no
	 * @param user_id
	 * @param comp_id
	 * @return
	 */
	public List getUserCurrentSignList(String flow_no, String user_id, String comp_id){
		
		List dataList = new ArrayList();
		String sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW_LOG_T0_01 AS FLOW_NO, " +
			" SFLOW_LOG_T0_02 AS FORM_NO, " +
			" SFLOW_LOG_T0_03 AS FLOW_LOG_SN, " +
			" SFLOW_LOG_T0_04 AS FLOW_SITE_SN, " +
			" SFLOW_LOG_T0_05 AS FLOW_SEND_TO_SIGN_DATE, " +
			" SFLOW_LOG_T0_06 AS SIGN_USER_ID, " +
			" SFLOW_SITE_T0_03 AS FLOW_SITE_NAME, " +
			" SFLOW_SITE_T0_04 AS AFTER_SIGN_FORM_STATUS_NO, " +
			" SFLOW_SITE_T0_05 AS AFTER_SIGN_FORM_STATUS_NAME, " +
			" SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE " +  //處理人員的指定Key值
			" FROM SFLOW_LOG_T0 " +
			" LEFT JOIN SFLOW_SITE_T0 ON SFLOW_SITE_T0_01 = SFLOW_LOG_T0_01 " +
			" AND SFLOW_SITE_T0_02 = SFLOW_LOG_T0_04 AND SFLOW_SITE_T0_06 = SFLOW_LOG_T0_14 " +
			" WHERE 1=1 " +
			" AND SFLOW_LOG_T0_01 = '"+flow_no+"' " +  //FLOW編號
			" AND SFLOW_LOG_T0_06 = '"+user_id+"' " +  //簽核者
			" AND SFLOW_LOG_T0_14 = '"+comp_id+"' " +  //公司代碼
			" AND SFLOW_LOG_T0_08 IS NULL ";
			
			//執行SQL
			dataList = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取得FLOW表單上一站簽核資訊(Map)
	 * @param current_sign_map
	 * @return
	 */
	public Map getPreviousFlowLog(Map current_sign_map){
		
		Map previous_sign_map = new HashMap();
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW_LOG_T0_01 AS FLOW_NO, " +
			" SFLOW_LOG_T0_02 AS FORM_NO, " +
			" SFLOW_LOG_T0_03 AS FLOW_LOG_SN, " +
			" SFLOW_LOG_T0_04 AS FLOW_SITE_SN, " +
			" SFLOW_LOG_T0_05 AS FLOW_SEND_TO_SIGN_DATE, " +
			" SFLOW_LOG_T0_06 AS SIGN_USER_ID, " +
			" SFLOW_SITE_T0_04 AS AFTER_SIGN_FORM_STATUS_NO, " +
			" SFLOW_SITE_T0_05 AS AFTER_SIGN_FORM_STATUS_NAME, " +
			" SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE " +  //處理人員的指定Key值
			" FROM SFLOW_LOG_T0 " +
			" LEFT JOIN SFLOW_SITE_T0 ON SFLOW_SITE_T0_01 = SFLOW_LOG_T0_01 " +
			" AND SFLOW_SITE_T0_02 = SFLOW_LOG_T0_04 AND SFLOW_SITE_T0_06 = SFLOW_LOG_T0_14 " +
			" WHERE 1=1 " +
			" AND SFLOW_LOG_T0_01 = '"+(String)current_sign_map.get("FLOW_NO")+"' " +  //FLOW編號
			" AND SFLOW_LOG_T0_02 = '"+(String)current_sign_map.get("FORM_NO")+"' " +  //表單單號
			" AND SFLOW_LOG_T0_03 = '"+((Integer)current_sign_map.get("FLOW_LOG_SN")-1)+"' " +  //簽核順序號碼 - 1 = 前一站的簽核順序號碼
			" AND SFLOW_LOG_T0_14 = '"+(String)current_sign_map.get("COMP_ID")+"' " +  //公司代碼
			" AND SFLOW_LOG_T0_04 IS NOT NULL " +
			" AND SFLOW_LOG_T0_08 IS NOT NULL ";
			
			previous_sign_map = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return previous_sign_map;
	}
	
	/**
	 * 取得第一站簽核LOG(Map)
	 * @param current_sign_map
	 * @return
	 */
	public Map getFirstFlowLog(Map current_sign_map){
		
		Map first_sign_map = new HashMap();
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" SFLOW_LOG_T0_01 AS FLOW_NO, " +
			" SFLOW_LOG_T0_02 AS FORM_NO, " +
			" SFLOW_LOG_T0_03 AS FLOW_LOG_SN, " +
			" SFLOW_LOG_T0_04 AS FLOW_SITE_SN, " +
			" SFLOW_LOG_T0_05 AS FLOW_SEND_TO_SIGN_DATE, " +
			" SFLOW_LOG_T0_06 AS SIGN_USER_ID, " +
			" SFLOW_SITE_T0_04 AS AFTER_SIGN_FORM_STATUS_NO, " +
			" SFLOW_SITE_T0_05 AS AFTER_SIGN_FORM_STATUS_NAME, " +
			" SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE " +  //處理人員的指定Key值
			" FROM SFLOW_LOG_T0 " +
			" LEFT JOIN SFLOW_SITE_T0 ON SFLOW_SITE_T0_01 = SFLOW_LOG_T0_01 " +
			" AND SFLOW_SITE_T0_02 = SFLOW_LOG_T0_04 AND SFLOW_SITE_T0_06 = SFLOW_LOG_T0_14 " +
			" WHERE 1=1 " +
			" AND SFLOW_LOG_T0_01 = '"+(String)current_sign_map.get("FLOW_NO")+"' " +  //FLOW編號
			" AND SFLOW_LOG_T0_02 = '"+(String)current_sign_map.get("FORM_NO")+"' " +  //表單單號
			" AND SFLOW_LOG_T0_03 = '1' " +  //站別順序號碼 = 第一站別 -> '1'
			" AND SFLOW_LOG_T0_14 = '"+(String)current_sign_map.get("COMP_ID")+"' " +  //公司代碼
			" AND SFLOW_LOG_T0_04 IS NOT NULL " +
			" AND SFLOW_LOG_T0_08 IS NOT NULL ";
			
			first_sign_map = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return first_sign_map;
	}
	
	/**
	 * 取得下一站簽核LOG(Map)
	 * @param current_sign_map
	 * @return
	 */
	public Map getNextFlowStation(Map current_sign_map){
		
		Map next_sign_map = new HashMap();
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" A1.SFLOW_SITE_T0_01 AS FLOW_NO, " +
			" A1.SFLOW_SITE_T0_02 AS FLOW_SITE_SN, " +
			" A1.SFLOW_SITE_T0_03 AS FLOW_SITE_NAME, " +
			" A1.SFLOW_SITE_T0_04 AS FLOW_SITE_STATUS_NO, " +
			" A1.SFLOW_SITE_T0_05 AS FLOW_SITE_STATUS_NAME, " +
			" A1.SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" A1.SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE, " +  //處理人員的指定Key值
			" A2.SFLOW_SITE_T0_04 AS BEFORE_SIGN_FORM_STATUS_NO, " +
			" A2.SFLOW_SITE_T0_05 AS BEFORE_SIGN_FORM_STATUS_NAME, " +
			" A2.SFLOW_SITE_T0_07 AS BEFORE_FLOW_SITE_SIGN_USER_TYPE, " +  //之前的處理人員類型
			" A2.SFLOW_SITE_T0_08 AS BEFORE_FLOW_SITE_SIGN_USER_KEY_VALUE " +  //之前的處理人員的指定Key值
			" FROM SFLOW_SITE_T0 A1 " +
			" LEFT JOIN SFLOW_SITE_T0 A2 ON A2.SFLOW_SITE_T0_01 = A1.SFLOW_SITE_T0_01 " +
			" AND A2.SFLOW_SITE_T0_02 = '"+(Integer)current_sign_map.get("FLOW_SITE_SN")+"' " +  //前一站的站別順序號碼
			" AND A2.SFLOW_SITE_T0_06 = A1.SFLOW_SITE_T0_06 " +
			" WHERE 1=1 " +
			" AND A1.SFLOW_SITE_T0_01 = '"+(String)current_sign_map.get("FLOW_NO")+"' " +  //FLOW編號
			" AND A1.SFLOW_SITE_T0_02 = '"+((Integer)current_sign_map.get("FLOW_SITE_SN")+1)+"' " +  //站別順序號碼 + 1 = next 站別
			" AND A1.SFLOW_SITE_T0_06 = '"+(String)current_sign_map.get("COMP_ID")+"' " +  //公司代碼
			" AND A1.SFLOW_SITE_T0_01 IS NOT NULL " +
			" AND A1.SFLOW_SITE_T0_02 IS NOT NULL ";
			
			next_sign_map = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return next_sign_map;
	}
	
	/**
	 * 取得上一站簽核LOG(Map)
	 * @param current_sign_map
	 * @return
	 */
	public Map getPreviousFlowStation(Map current_sign_map){
		
		Map previous_sign_map = new HashMap();
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" A1.SFLOW_SITE_T0_01 AS FLOW_NO, " +
			" A1.SFLOW_SITE_T0_02 AS FLOW_SITE_SN, " +
			" A1.SFLOW_SITE_T0_03 AS FLOW_SITE_NAME, " +
			" A1.SFLOW_SITE_T0_04 AS FLOW_SITE_STATUS_NO, " +
			" A1.SFLOW_SITE_T0_05 AS FLOW_SITE_STATUS_NAME, " +
			" A1.SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" A1.SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE, " +  //處理人員的指定Key值
			" A2.SFLOW_SITE_T0_04 AS BEFORE_SIGN_FORM_STATUS_NO, " +
			" A2.SFLOW_SITE_T0_05 AS BEFORE_SIGN_FORM_STATUS_NAME, " +
			" A2.SFLOW_SITE_T0_07 AS BEFORE_FLOW_SITE_SIGN_USER_TYPE, " +  //之前的處理人員類型
			" A2.SFLOW_SITE_T0_08 AS BEFORE_FLOW_SITE_SIGN_USER_KEY_VALUE " +  //之前的處理人員的指定Key值
			" FROM SFLOW_SITE_T0 A1 " +
			" LEFT JOIN SFLOW_SITE_T0 A2 ON A2.SFLOW_SITE_T0_01 = A1.SFLOW_SITE_T0_01 " +
			" AND A2.SFLOW_SITE_T0_02 = '"+(Integer)current_sign_map.get("FLOW_SITE_SN")+"' " +  //前一站的站別順序號碼
			" AND A2.SFLOW_SITE_T0_06 = A1.SFLOW_SITE_T0_06 " +  
			" WHERE 1=1 " +
			" AND A1.SFLOW_SITE_T0_01 = '"+(String)current_sign_map.get("FLOW_NO")+"' " +  //FLOW編號
			" AND A1.SFLOW_SITE_T0_02 = '"+((Integer)current_sign_map.get("FLOW_SITE_SN")-1)+"' " +  //站別順序號碼 - 1 = previous 站別
			" AND A1.SFLOW_SITE_T0_06 = '"+(String)current_sign_map.get("COMP_ID")+"' " +  //公司代碼
			" AND A1.SFLOW_SITE_T0_01 IS NOT NULL " +
			" AND A1.SFLOW_SITE_T0_02 IS NOT NULL ";
			
			previous_sign_map = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return previous_sign_map;
	}
	
	/**
	 * 取得第一站簽核LOG(Map)
	 * @param current_sign_map
	 * @return
	 */
	public Map getFirstFlowStation(Map current_sign_map){
		
		Map first_sign_map = new HashMap();
		String sql = "";
		String show_sql = "";
		
		try{
			sql = "" +
			" SELECT " +
			" A1.SFLOW_SITE_T0_01 AS FLOW_NO, " +
			" A1.SFLOW_SITE_T0_02 AS FLOW_SITE_SN, " +
			" A1.SFLOW_SITE_T0_03 AS FLOW_SITE_NAME, " +
			" A1.SFLOW_SITE_T0_04 AS FLOW_SITE_STATUS_NO, " +
			" A1.SFLOW_SITE_T0_05 AS FLOW_SITE_STATUS_NAME, " +
			" A1.SFLOW_SITE_T0_07 AS FLOW_SITE_SIGN_USER_TYPE, " +  //處理人員類型
			" A1.SFLOW_SITE_T0_08 AS FLOW_SITE_SIGN_USER_KEY_VALUE, " +  //處理人員的指定Key值
			" A2.SFLOW_SITE_T0_04 AS BEFORE_SIGN_FORM_STATUS_NO, " +
			" A2.SFLOW_SITE_T0_05 AS BEFORE_SIGN_FORM_STATUS_NAME, " +
			" A2.SFLOW_SITE_T0_07 AS BEFORE_FLOW_SITE_SIGN_USER_TYPE, " +  //之前的處理人員類型
			" A2.SFLOW_SITE_T0_08 AS BEFORE_FLOW_SITE_SIGN_USER_KEY_VALUE " +  //之前的處理人員的指定Key值
			" FROM SFLOW_SITE_T0 A1 " +
			" LEFT JOIN SFLOW_SITE_T0 A2 ON A2.SFLOW_SITE_T0_01 = A1.SFLOW_SITE_T0_01 " +
			" AND A2.SFLOW_SITE_T0_02 = '"+(Integer)current_sign_map.get("FLOW_SITE_SN")+"' " +  //前一站的站別順序號碼
			" AND A2.SFLOW_SITE_T0_06 = A1.SFLOW_SITE_T0_06 " +  
			" WHERE 1=1 " +
			" AND A1.SFLOW_SITE_T0_01 = '"+(String)current_sign_map.get("FLOW_NO")+"' " +  //FLOW編號
			" AND A1.SFLOW_SITE_T0_02 = '1' " +  //站別順序號碼 = 第一站別 -> '1'
			" AND A1.SFLOW_SITE_T0_06 = '"+(String)current_sign_map.get("COMP_ID")+"' " +  //公司代碼
			" AND A1.SFLOW_SITE_T0_01 IS NOT NULL " +
			" AND A1.SFLOW_SITE_T0_02 IS NOT NULL ";
			
			first_sign_map = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return first_sign_map;
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
