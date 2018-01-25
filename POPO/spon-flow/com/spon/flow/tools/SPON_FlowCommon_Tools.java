package com.spon.flow.tools;

import java.util.Map;

import com.spon.flow.interfaces.SPON_Flow_Interface;
import com.spon.flow.models.SPON_Flow_CoreData_Tools;

public class SPON_FlowCommon_Tools implements SPON_Flow_Interface {
	
	/**
	 * 建構子
	 */
	public SPON_FlowCommon_Tools(){
		
	}
	
	/**
	 * 呈核
	 */
	@Override
	public void submit(String userId, Map condMap) {
		// TODO Auto-generated method stub
		
		try{
			//建立FLOW核心資料元件
			SPON_Flow_CoreData_Tools spon_flow_coredata_tools = new SPON_Flow_CoreData_Tools();
			
			//取得FLOW基本資訊
			Map flow_basic_map = spon_flow_coredata_tools.getFlowBasicData(condMap);
			//加入條件Map中
			condMap.putAll(flow_basic_map);  //FLOW_BASIC_DATA
			
			//取得FLOW使用記錄
			Map flow_use_record_map = spon_flow_coredata_tools.getFlowUseRecord(condMap);
			
			//建立FLOW使用記錄
			if(!flow_use_record_map.containsKey("FLOW_USE_RECORD_SN") ||
			   (flow_use_record_map.containsKey("FLOW_USE_RECORD_SN") 
			   && (Integer)flow_use_record_map.get("FLOW_USE_RECORD_SN") == 0)){
				//表示之前沒有記錄
				//建立FLOW使用記錄
				spon_flow_coredata_tools.createFlowUseRecord(condMap);
				//取得FLOW使用記錄
				flow_use_record_map = spon_flow_coredata_tools.getFlowUseRecord(condMap);
			}else{
				//表示之前有記錄, 目前不需要處理
			}
			
			//建立FLOW參數設定值
			spon_flow_coredata_tools.putFlowParameterValue(condMap);
			
			//取得當前站別資訊
			Map flow_current_station_map = spon_flow_coredata_tools.getFlowCurrentStationData(flow_use_record_map);
			
			//判斷此動作執行是否異常
			
			
			//執行下一站(Next)
			
			
			//更新FLOW表單資料與建立簽核歷程
			
			
			//關閉FLOW核心資料元件
			spon_flow_coredata_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 核准
	 */
	@Override
	public void approve(String userId, Map condMap) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 作廢
	 */
	@Override
	public void reject(String userId, Map condMap) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 抽單
	 */
	@Override
	public void cancel(String userId, Map condMap) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 作廢
	 */
	@Override
	public void invalid(String userId, Map condMap) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	
}
