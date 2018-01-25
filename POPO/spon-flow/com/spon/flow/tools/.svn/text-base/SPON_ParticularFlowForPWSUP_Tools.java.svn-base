package com.spon.flow.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spon.flow.models.SPON_ParticularFlowForPWSUP_CoreData_Tools;
import com.spon.mvc.models.BaseFunction;

/**
 * 賓瑋供應商平台專用FLOW元件
 * @author Joe
 *
 */
public class SPON_ParticularFlowForPWSUP_Tools {
	
	/**
	 * 建構子
	 */
	public SPON_ParticularFlowForPWSUP_Tools(){
		
	}
	
	/**
	 * 呈核
	 */
	public void submit(String flow_no, String form_no, 
					   Map condMap, 
					   String user_id, String comp_id ) {
		// TODO Auto-generated method stub
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//取得表單當前狀態
			Map current_form_status_map = this.getCurrentFlowFormStatus(flow_no, 
																		form_no, 
					   													comp_id);
			
			//判斷是否為第一次呈核
			if("".equals((String)current_form_status_map.get("SIGN_USER_ID"))){
				//建立開單人員LOG(表單呈核)(待簽核與簽核一起建立)
				Map open_form_log_map = new HashMap();
				open_form_log_map.put("FLOW_NO", flow_no);
				open_form_log_map.put("FORM_NO", form_no);
				open_form_log_map.put("FLOW_SITE_SN", (int)1);
				open_form_log_map.put("SIGN_USER_ID", (String)condMap.get("FORM_OPEN_USER_ID"));
				open_form_log_map.put("SIGN_COMMENT", "");
				open_form_log_map.put("BEFORE_SIGN_FORM_STATUS_NO", "01");
				open_form_log_map.put("BEFORE_SIGN_FORM_STATUS_NAME", "填寫中");
				open_form_log_map.put("USER_ID", user_id);
				open_form_log_map.put("COMP_ID", comp_id);
				//建立待簽核LOG(For 呈核)
				spon_pa_pwsup_flow_tools.writeFlowLog(open_form_log_map);
			}
			
			//執行簽核動作
			Map current_sign_map = 
			this.sign(spon_pa_pwsup_flow_tools, 
					  flow_no, form_no, 
					  "呈核", "送出表單", 
					  user_id, comp_id);
			
			//取得下一個簽核人員資訊
			Map next_user = this.getNextUser(current_sign_map, condMap);
			
			//往下一站簽核執行
			this.nextStation(spon_pa_pwsup_flow_tools, 
							 current_sign_map, 
							 flow_no, form_no, 
							 (String)next_user.get("NEXT_USER_ID"), 
							 user_id, comp_id);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 核准
	 */
	public void approve(String flow_no, String form_no, 
						Map condMap, 
						String sign_comment,
			   			String user_id, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//執行簽核動作
			Map current_sign_map = 
			this.sign(spon_pa_pwsup_flow_tools, 
					  flow_no, form_no, 
					  "核准", sign_comment, 
					  user_id, comp_id);
			
			//取得下一個簽核人員資訊
			Map next_user = this.getNextUser(current_sign_map, condMap);
			
			//往下一站簽核執行
			this.nextStation(spon_pa_pwsup_flow_tools, 
							 current_sign_map, 
							 flow_no, form_no, 
							 (String)next_user.get("NEXT_USER_ID"), 
							 user_id, comp_id);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 駁回
	 */
	public void reject(String flow_no, String form_no, 
   					   Map condMap,
   					   String sign_comment,
   					   String user_id, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//執行簽核動作
			Map current_sign_map = 
			this.sign(spon_pa_pwsup_flow_tools, 
					  flow_no, form_no, 
					  "駁回", sign_comment, 
					  user_id, comp_id);
			
			//取得下一個簽核人員資訊
//			Map next_user = this.getNextUser(current_sign_map, condMap);
			
			//往上一站簽核執行
			/*
			this.previousStation(spon_pa_pwsup_flow_tools, 
								 current_sign_map, 
								 flow_no, form_no, 
								 user_id, comp_id);
			*/
			
			//駁回至第一站
			this.backToFirstStation(spon_pa_pwsup_flow_tools, 
									current_sign_map, 
									flow_no, form_no, 
									user_id, comp_id);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 抽單
	 */
	public void cancel(String flow_no, String form_no, 
					   String cur_sign_user_id, String sign_comment,
			   		   String user_id, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//抽單簽核執行
			Map current_sign_map = 
				this.sign(spon_pa_pwsup_flow_tools, 
						  flow_no, form_no, 
						  "抽單", sign_comment, 
						  user_id, comp_id);
			
			//等同於駁回至第一站
			this.backToFirstStation(spon_pa_pwsup_flow_tools, 
									current_sign_map, 
									flow_no, form_no, 
									user_id, comp_id);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 作廢
	 */
	public void invalid(String flow_no, String form_no, 
	   		            String cur_sign_user_id, String sign_comment,
	   		            String user_id, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//取得表單當前狀態
			Map current_form_status_map = this.getCurrentFlowFormStatus(flow_no, 
																		form_no, 
					   													comp_id);
			
			int FLOW_LOG_SN = (Integer)current_form_status_map.get("FLOW_LOG_SN");
			
			//建立作廢人員LOG(表單作廢)(待簽核與簽核一起建立)
			Map open_form_log_map = new HashMap();
			open_form_log_map.put("FLOW_NO", flow_no);
			open_form_log_map.put("FORM_NO", form_no);
			open_form_log_map.put("FLOW_SITE_SN", (Integer)current_form_status_map.get("FLOW_SITE_SN")+1);
			open_form_log_map.put("SIGN_USER_ID", cur_sign_user_id);
			open_form_log_map.put("SIGN_COMMENT", "");
			open_form_log_map.put("BEFORE_SIGN_FORM_STATUS_NO", (String)current_form_status_map.get("AFTER_SIGN_FORM_STATUS_NO"));
			open_form_log_map.put("BEFORE_SIGN_FORM_STATUS_NAME", (String)current_form_status_map.get("AFTER_SIGN_FORM_STATUS_NAME"));
			open_form_log_map.put("USER_ID", user_id);
			open_form_log_map.put("COMP_ID", comp_id);
			//建立待簽核LOG(For 作廢)
			spon_pa_pwsup_flow_tools.writeFlowLog(open_form_log_map);
			
			//作廢簽核執行
			Map current_sign_map = 
				this.signInvalid(spon_pa_pwsup_flow_tools, 
								 flow_no, form_no, 
								 "作廢", sign_comment, 
								 user_id, comp_id,
								 FLOW_LOG_SN+1);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 自動核准
	 */
	public void AutoApprove(String flow_no, String form_no, 
							Map condMap, 
							String sign_comment,
							String user_id, String comp_id,
							Integer auto_flow_site_sn) {
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//執行簽核動作
			Map current_sign_map = 
			this.sign(spon_pa_pwsup_flow_tools, 
					  flow_no, form_no, 
					  "自動核准", sign_comment, 
					  user_id, comp_id);
			
			//取得下一個簽核人員資訊
			Map next_user = this.getNextUser(current_sign_map, condMap);
			
			//往下一站簽核執行
			this.nextStation(spon_pa_pwsup_flow_tools, 
							 current_sign_map, 
							 flow_no, form_no, 
							 (String)next_user.get("NEXT_USER_ID"), 
							 user_id, comp_id);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
			//取得當前待簽核LOG
			Map auto_current_sign_map = this.getCurrentFlowFormStatus(flow_no, form_no, comp_id);
			
			if((Integer)auto_current_sign_map.get("FLOW_SITE_SN") != auto_flow_site_sn){
				this.AutoApprove(flow_no, form_no, condMap, sign_comment, user_id, comp_id, auto_flow_site_sn);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 取得下一個簽核人員資訊
	 * @param current_sign_map
	 * @param condMap
	 * @return
	 */
	protected Map getNextUser(Map current_sign_map, Map condMap){
		
		Map next_user_map = new HashMap();
		
		try{
			String next_user_id = "";
			//判斷下一個簽核人員的類型 -> FLOW_SITE_SIGN_USER_TYPE
			String next_user_type = (String)current_sign_map.get("FLOW_SITE_SIGN_USER_TYPE");
			String next_user_type_name = "";
			String next_user_key_value = 
			(String)current_sign_map.get("FLOW_SITE_SIGN_USER_KEY_VALUE");
			
			if("01".equals(next_user_type)){
				//類型:指定人員
				next_user_id = next_user_key_value;
				next_user_type_name = "指定人員";
			}else if("02".equals(next_user_type)){
				//類型:指定欄位
				next_user_id = (String)condMap.get(next_user_key_value);
				next_user_type_name = "指定欄位";
			}else if("03".equals(next_user_type)){
				//類型:指定組織
				next_user_id = (String)condMap.get(next_user_key_value);
				next_user_type_name = "指定組織";
			}
			
			//組成 NextUser Map
			next_user_map.put("NEXT_USER_TYPE", next_user_type);
			next_user_map.put("NEXT_USER_TYPE_NAME", next_user_type_name);
			next_user_map.put("NEXT_USER_ID", next_user_id);
			next_user_map.put("NEXT_USER_KEY_VALUE", next_user_key_value);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return next_user_map;
	}
	
	/**
	 * 執行作廢動作
	 * @param spon_pa_pwsup_flow_tools
	 * @param flow_no
	 * @param form_no
	 * @param sign_action_name
	 * @param sign_comment
	 * @param user_id
	 * @param comp_id
	 * @param flow_log_sn
	 * @return
	 */
	protected Map signInvalid(SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools,
		    				  String flow_no, String form_no,
		    				  String sign_action_name, String sign_comment,
		    				  String user_id, String comp_id,
		    				  int flow_log_sn){

		Map current_sign_map = new HashMap();

		try{		
			//作廢
			current_sign_map.put("FLOW_NO", flow_no);
			current_sign_map.put("FORM_NO", form_no);
			current_sign_map.put("SIGN_COMMENT", sign_comment);  //簽核意見
			current_sign_map.put("SIGN_ACTION_NAME", sign_action_name);
			current_sign_map.put("USER_ID", user_id);
			current_sign_map.put("COMP_ID", comp_id);
			current_sign_map.put("AFTER_SIGN_FORM_STATUS_NO", "10");
			current_sign_map.put("AFTER_SIGN_FORM_STATUS_NAME", sign_action_name);
			current_sign_map.put("FLOW_LOG_SN", flow_log_sn);
			//作廢 -> 寫入當前待簽核LOG中
			spon_pa_pwsup_flow_tools.signFlowLog(current_sign_map);
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return current_sign_map;
	}
	
	/**
	 * 執行簽核動作
	 * @param spon_pa_pwsup_flow_tools
	 * @param flow_no
	 * @param form_no
	 * @param sign_action_name
	 * @param sign_comment
	 * @param user_id
	 * @param comp_id
	 */
	protected Map sign(SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools,
					    String flow_no, String form_no,
						String sign_action_name, String sign_comment,
					    String user_id, String comp_id){
		
		Map current_sign_map = new HashMap();
		
		try{
			//取得當前待簽核LOG
			current_sign_map = 
			spon_pa_pwsup_flow_tools.getCurrentFlowLog(flow_no, form_no, comp_id);
			
			//簽核
			current_sign_map.put("SIGN_COMMENT", sign_comment);  //簽核意見
			current_sign_map.put("SIGN_ACTION_NAME", sign_action_name);
			current_sign_map.put("USER_ID", user_id);
			current_sign_map.put("COMP_ID", comp_id);
			//簽核 -> 寫入當前待簽核LOG中
			spon_pa_pwsup_flow_tools.signFlowLog(current_sign_map);
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return current_sign_map;
	}
	
	/**
	 * 往下站簽核
	 * @param spon_pa_pwsup_flow_tools
	 * @param current_sign_map
	 * @param flow_no
	 * @param form_no
	 * @param user_id
	 * @param comp_id
	 */
	protected void nextStation(SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools,
							   Map current_sign_map,
							   String flow_no, String form_no,
							   String next_sign_user_id,
							   String user_id, String comp_id){
		
		try{
			//取得下一站簽核站別資訊
			Map next_sign_site_map = spon_pa_pwsup_flow_tools.getNextFlowStation(current_sign_map);
			
			//判斷是否有下一站可以處理
			if(!next_sign_site_map.isEmpty()){
				//建立下一站待簽核人員LOG
				Map next_station_log_map = new HashMap();
				next_station_log_map.put("FLOW_NO", flow_no);
				next_station_log_map.put("FORM_NO", form_no);
				next_station_log_map.put("FLOW_SITE_SN", (Integer)next_sign_site_map.get("FLOW_SITE_SN"));
				next_station_log_map.put("SIGN_USER_ID", next_sign_user_id);
				next_station_log_map.put("SIGN_COMMENT", "");
				next_station_log_map.put("BEFORE_SIGN_FORM_STATUS_NO", 
										(String)next_sign_site_map.get("BEFORE_SIGN_FORM_STATUS_NO"));
				next_station_log_map.put("BEFORE_SIGN_FORM_STATUS_NAME",
										(String)next_sign_site_map.get("BEFORE_SIGN_FORM_STATUS_NAME"));
				next_station_log_map.put("USER_ID", user_id);
				next_station_log_map.put("COMP_ID", comp_id);
				//建立待簽核LOG(For Next Station)
				spon_pa_pwsup_flow_tools.writeFlowLog(next_station_log_map);
				
			}else{
				//表示FLOW表單已跑完流程!!
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
	
	/**
	 * 往上站簽核
	 * @param spon_pa_pwsup_flow_tools
	 * @param current_sign_map
	 * @param flow_no
	 * @param form_no
	 * @param user_id
	 * @param comp_id
	 */
	protected void previousStation(SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools,
			   				   Map current_sign_map,
			   				   String flow_no, String form_no,
			   				   String user_id, String comp_id){

		try{
			//取得上一站簽核站別資訊
			Map previous_sign_site_map = spon_pa_pwsup_flow_tools.getPreviousFlowStation(current_sign_map);
			
			//取得上一站簽核人員(previous_sign_user_id)
			Map previous_sign_map = spon_pa_pwsup_flow_tools.getPreviousFlowLog(current_sign_map);
			String previous_sign_user_id = (String)previous_sign_map.get("SIGN_USER_ID");
			
			//判斷是否有上一站 且 有上一站簽核者 才可以處理
			if(!previous_sign_site_map.isEmpty() && !previous_sign_map.isEmpty() 
				&& !"".equals(previous_sign_user_id) && previous_sign_user_id != null ){
				//建立上一站待簽核人員LOG
				Map previous_station_log_map = new HashMap();
				previous_station_log_map.put("FLOW_NO", flow_no);
				previous_station_log_map.put("FORM_NO", form_no);
				previous_station_log_map.put("FLOW_SITE_SN", (Integer)previous_sign_site_map.get("FLOW_SITE_SN"));
				previous_station_log_map.put("SIGN_USER_ID", previous_sign_user_id);
				previous_station_log_map.put("SIGN_COMMENT", "");
				previous_station_log_map.put("BEFORE_SIGN_FORM_STATUS_NO", 
										 	 (String)previous_sign_site_map.get("BEFORE_SIGN_FORM_STATUS_NO"));
				previous_station_log_map.put("BEFORE_SIGN_FORM_STATUS_NAME",
											 (String)previous_sign_site_map.get("BEFORE_SIGN_FORM_STATUS_NAME"));
				previous_station_log_map.put("USER_ID", user_id);
				previous_station_log_map.put("COMP_ID", comp_id);
				//建立待簽核LOG(For Next Station)
				spon_pa_pwsup_flow_tools.writeFlowLog(previous_station_log_map);
				
			}else{
				//表示FLOW表單已無前一站可以執行駁回, 不做任何處理動作!!
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/**
	 * 駁回至第一站
	 * @param spon_pa_pwsup_flow_tools
	 * @param current_sign_map
	 * @param flow_no
	 * @param form_no
	 * @param user_id
	 * @param comp_id
	 */
	protected void backToFirstStation(SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools,
			   Map current_sign_map,
			   String flow_no, String form_no,
			   String user_id, String comp_id){

		try{
			//取得第一站簽核站別資訊
			Map first_sign_site_map = spon_pa_pwsup_flow_tools.getFirstFlowStation(current_sign_map);

			//取得第一站簽核人員(first_sign_user_id)
			Map first_sign_map = spon_pa_pwsup_flow_tools.getFirstFlowLog(current_sign_map);
			String first_sign_user_id = (String)first_sign_map.get("SIGN_USER_ID");

			//判斷是否有上一站 且 有第一站簽核者 才可以處理
			if(!first_sign_site_map.isEmpty() && !first_sign_map.isEmpty() 
					&& !"".equals(first_sign_user_id) && first_sign_user_id != null ){
				//建立第一站待簽核人員LOG
				Map previous_station_log_map = new HashMap();
				previous_station_log_map.put("FLOW_NO", flow_no);
				previous_station_log_map.put("FORM_NO", form_no);
				previous_station_log_map.put("FLOW_SITE_SN", (Integer)first_sign_site_map.get("FLOW_SITE_SN"));
				previous_station_log_map.put("SIGN_USER_ID", first_sign_user_id);
				previous_station_log_map.put("SIGN_COMMENT", "");
				previous_station_log_map.put("BEFORE_SIGN_FORM_STATUS_NO", 
						 	 				 "01");
				previous_station_log_map.put("BEFORE_SIGN_FORM_STATUS_NAME",
							 				 "填寫中");
				previous_station_log_map.put("USER_ID", user_id);
				previous_station_log_map.put("COMP_ID", comp_id);
				//建立待簽核LOG(For Next Station)
				spon_pa_pwsup_flow_tools.writeFlowLog(previous_station_log_map);
				
			}else{
				//表示FLOW表單已無前一站可以執行駁回, 不做任何處理動作!!
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 取得FLOW表單簽核歷程
	 * @param flow_no
	 * @param form_no
	 * @param comp_id
	 * @return
	 */
	public List getFlowSignLogList(String flow_no, String form_no, 
			  String comp_id){
		
		List dataList = new ArrayList();
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//取得表單簽核歷程清單(List)
			dataList = 
			spon_pa_pwsup_flow_tools.getFlowLogList(flow_no, form_no, comp_id);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
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
	public Map getCurrentFlowFormStatus(String flow_no, String form_no, 
										String comp_id){
		
		Map current_flow_form_status_map = new HashMap();
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//取得FLOW表單當前狀態資訊待簽核LOG
			current_flow_form_status_map = 
			spon_pa_pwsup_flow_tools.getCurrentFlowFormStatus(flow_no, form_no, comp_id);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
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
	public List getUserCurrentSignList(String flow_no, String user_id, 
									   String comp_id){
		
		List dataList = new ArrayList();
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//取得使用者當前待簽核清單(List)
			dataList = 
			spon_pa_pwsup_flow_tools.getUserCurrentSignList(flow_no, user_id, comp_id);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}
	
	/**
	 * 取得FLOW表單當前已簽核狀態資訊(Map)
	 * @param flow_no
	 * @param form_no
	 * @param comp_id
	 * @return
	 */
	public Map getPreviousFlowFormStatus(String flow_no, String form_no, 
										String comp_id){
		
		Map current_flow_form_status_map = new HashMap();
		Map previous_flow_form_status_map = new HashMap();
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//取得FLOW表單當前狀態資訊待簽核LOG
			current_flow_form_status_map = 
			spon_pa_pwsup_flow_tools.getCurrentFlowFormStatus(flow_no, form_no, comp_id);
			//準備公司編號
			current_flow_form_status_map.put("COMP_ID", comp_id);
			
			//取得FLOW表單當前狀態資訊已簽核LOG
			previous_flow_form_status_map = 
				spon_pa_pwsup_flow_tools.getPreviousFlowLog(current_flow_form_status_map);
			
			//關閉特殊FLOW資料處理元件
			spon_pa_pwsup_flow_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return previous_flow_form_status_map;		
	}

	/**
	 * 判斷是否有下一站可以處理，true表示有，false表示沒有
	 * @param current_sign_map
	 * @param comp_id
	 * @return
	 */
	public boolean isNextFlowStation(Map current_sign_map, String comp_id){
		
		boolean next_flow_station_flag = true;
		
		try{
			//建立特殊FLOW資料處理元件
			SPON_ParticularFlowForPWSUP_CoreData_Tools spon_pa_pwsup_flow_tools = 
			new SPON_ParticularFlowForPWSUP_CoreData_Tools();
			
			//準備公司編號
			current_sign_map.put("COMP_ID", comp_id);
			
			//取得下一站簽核站別資訊
			Map next_sign_site_map = spon_pa_pwsup_flow_tools.getNextFlowStation(current_sign_map);
			
			//判斷是否有下一站可以處理
			if(next_sign_site_map.isEmpty()){
				next_flow_station_flag = false;
			}
			
			spon_pa_pwsup_flow_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return next_flow_station_flag;
	}
	
	
}
