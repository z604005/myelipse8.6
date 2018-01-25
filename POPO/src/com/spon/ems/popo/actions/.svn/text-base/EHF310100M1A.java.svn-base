package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.popo.forms.EHF310100M0F;
import com.spon.ems.popo.forms.EX330100R0F;
import com.spon.ems.popo.models.EHF310100;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 客戶需求單
 * (Action)
 */
public class EHF310100M1A extends EditAction {
	
	private EMS_ACCESS ems_access;
	
	public EHF310100M1A(){
		ems_access = EMS_ACCESS.getInstance();
		
	}
	
	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub
		EHF310100M0F Form = (EHF310100M0F) form;
		
		//建立EHF310100元件
		EHF310100 ehf310100 = new EHF310100();
		
		String[] drink = new String[50];
		String[] order = new String[50];
		
		List drinkList =  ehf310100.getcheckboxes(false, "Drink", (String)Form.getEHF310100T0_34());
		for(int i = 0;i<drinkList.size();i++){
			//預設帶勾選的話將下兩行註解拿掉  第三行改成註解
//			Map map = (Map)drinkList.get(i);
//			drink[i]=(String)map.get("ITEM_ID");
			drink[i]="";
		}
		
		List orderList =  ehf310100.getcheckboxes(false, "MENU_MEAL", (String)Form.getEHF310100T0_34());
		for(int i = 0;i<orderList.size();i++){
			//預設帶勾選的話將下兩行註解拿掉  第三行改成註解
//			Map map = (Map)orderList.get(i);
//			order[i]=(String)map.get("ITEM_ID");
			order[i]="";
		}
		
		//餐飲設定
		Form.setEHF310100T2_06_total(drink);				//養生飲品(飲品取值用陣列)
		Form.setEHF310100T1_03_total(order);				//每日明細(餐別取值用陣列)
		Form.setEHF310100T1_04_total(order);				//每日明細(路線取值用陣列)
		Form.setEHF310100T1_05_total(order);				//每日明細(素食取值用陣列)
		
		Form.setEHF310100T2_03("");
		Form.setEHF310100T2_04("");
		Form.setEHF310100T2_05("");
//		Form.setEHF310100T2_06_total();//改在executeQueryEditData中清空欄位
		Form.setEHF310100T1_02_B("");
		Form.setEHF310100T1_02_E("");
//		Form.setEHF310100T1_03_total();//改在executeQueryEditData中清空欄位
//		Form.setEHF310100T1_04_total();//改在executeQueryEditData中清空欄位
//		Form.setEHF310100T1_05_total();//改在executeQueryEditData中清空欄位
		Form.setEHF310200T1_03("");
		Form.setEHF310200T1_03_type("");
		Form.setEHF310200T1_03_type_TXT("");
		Form.setEHF310200T1_03_detail("");
		Form.setEHF310200T1_03_detail_TXT("");
		Form.setEHF310200T2_03("");
		Form.setEHF310200T3_03("");
		Form.setEHF310200T4_03("");
		Form.setEHF310200T5_03("");
		
		Form.setEHF310300T0_03("");
		Form.setEHF310300T0_03_SHOW("");
		Form.setEHF310300T0_03_TXT("");
		Form.setEHF310300T0_04("");
		Form.setEHF310300T0_05("");
		Form.setEHF310300T0_06("");
		
		Form.setEHF310400T1_03("");
		Form.setEHF310400T1_04("");
		Form.setEHF310400T1_04_SHOW("");
		Form.setEHF310400T1_04_TXT("");
		Form.setEHF310400T1_05("");
		Form.setEHF310400T1_05_SHOW("");
		Form.setEHF310400T1_05_TXT("");
		Form.setEHF310400T1_06("");
		Form.setEHF310400T1_07("");
		Form.setEHF310400T1_08("");
		Form.setEHF310400T1_09(0);
		Form.setEHF310400T1_10("");
		Form.setEHF310400T1_11(0);
		Form.setEHF310400T1_12("");
		
		Form.setEHF310500T0_04("");
		Form.setEHF310500T0_05("");
		Form.setEHF310500T0_06("");
		Form.setEHF310500T0_08("");
	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF310100M0F Form = (EHF310100M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF310100元件
			EHF310100 ehf310100 = new EHF310100(comp_id);
			

			String SysNo = tools.createEMSUID(conn, "CR", "EHF310100T0", "EHF310100T0_01", comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap.put("EHF310100T0_01", new String(SysNo));	//客戶需求單單號
			dataMap.put("EHF310100T0_02", (String)Form.getEHF310100T0_02()==null?"":Form.getEHF310100T0_02());	//檔案編號
			dataMap.put("EHF310100T0_03", (String)Form.getEHF310100T0_03()==null?"":Form.getEHF310100T0_03());	//櫃號
			dataMap.put("EHF310100T0_04", (String)Form.getEHF310100T0_04());	//產婦姓名
			dataMap.put("EHF310100T0_05", (String)Form.getEHF310100T0_05()==null?"":Form.getEHF310100T0_05());	//產別
			dataMap.put("EHF310100T0_06", (String)Form.getEHF310100T0_06()==null?"":Form.getEHF310100T0_06());	//預產期
			dataMap.put("EHF310100T0_07", (String)Form.getEHF310100T0_07()==null?"":Form.getEHF310100T0_07());	//生產日
			dataMap.put("EHF310100T0_08", (String)Form.getEHF310100T0_08()==null?"":Form.getEHF310100T0_08());	//訂餐日期
			dataMap.put("EHF310100T0_09", (Integer)Form.getEHF310100T0_09());	//訂餐天數
			dataMap.put("EHF310100T0_10", (String)Form.getEHF310100T0_10()==null?"":Form.getEHF310100T0_10());	//送餐日期
			
			dataMap.put("EHF310100T0_11", (String)Form.getEHF310100T0_11()==null?"":Form.getEHF310100T0_11());	//醫院代碼
			dataMap.put("EHF310100T0_12", (Integer)Form.getEHF310100T0_12());	//醫院用餐天數
			dataMap.put("EHF310100T0_13", (String)Form.getEHF310100T0_13()==null?"":Form.getEHF310100T0_13());	//醫院用餐期間起
			dataMap.put("EHF310100T0_14", (String)Form.getEHF310100T0_14()==null?"":Form.getEHF310100T0_14());	//醫院用餐期間迄
			dataMap.put("EHF310100T0_15", (String)Form.getEHF310100T0_15()==null?"":Form.getEHF310100T0_15());	//醫院房號
			dataMap.put("EHF310100T0_16", (String)Form.getEHF310100T0_16()==null?"":Form.getEHF310100T0_16());	//醫院路線代號
			
			dataMap.put("EHF310100T0_17", (String)Form.getEHF310100T0_17()==null?"":Form.getEHF310100T0_17());	//住宅路線代號
			dataMap.put("EHF310100T0_18", (Integer)Form.getEHF310100T0_18());	//住宅用餐天數
			dataMap.put("EHF310100T0_19", (String)Form.getEHF310100T0_19()==null?"":Form.getEHF310100T0_19());	//住宅用餐期間起
			dataMap.put("EHF310100T0_20", (String)Form.getEHF310100T0_20()==null?"":Form.getEHF310100T0_20());	//住宅用餐期間迄
			
			dataMap.put("EHF310100T0_21", (String)Form.getEHF310100T0_21());	//行動電話(產婦)
			dataMap.put("EHF310100T0_22", (String)Form.getEHF310100T0_22());	//連絡電話(住)
			dataMap.put("EHF310100T0_23", (String)Form.getEHF310100T0_23());	//連絡1
			dataMap.put("EHF310100T0_24", (String)Form.getEHF310100T0_24());	//連絡1
			dataMap.put("EHF310100T0_25", (String)Form.getEHF310100T0_25());	//連絡2
			dataMap.put("EHF310100T0_26", (String)Form.getEHF310100T0_26());	//連絡2
			dataMap.put("EHF310100T0_27", (String)Form.getEHF310100T0_27());	//連絡3
			dataMap.put("EHF310100T0_28", (String)Form.getEHF310100T0_28());	//連絡3
			dataMap.put("EHF310100T0_29", (String)Form.getEHF310100T0_29());	//連絡4
			dataMap.put("EHF310100T0_30", (String)Form.getEHF310100T0_30());	//連絡4
			dataMap.put("EHF310100T0_31", (String)Form.getEHF310100T0_31());	//產婦地址
			dataMap.put("EHF310100T0_32", (String)Form.getEHF310100T0_32()==null?"":Form.getEHF310100T0_32());	//備註1
			dataMap.put("EHF310100T0_33", (String)Form.getEHF310100T0_33()==null?"":Form.getEHF310100T0_33());	//備註2
			
			dataMap.put("EHF310200T0_01", SysNo);	//系統編碼
			dataMap.put("EHF310200T0_02", "0");		//特殊習慣
			dataMap.put("EHF310200T0_03", "");		//備註
			/*
			dataMap.put("EHF310500T0_01", SysNo);	//系統編碼
			dataMap.put("EHF310500T0_03", "1");		//是否有贈品
			dataMap.put("EHF310500T0_04", "");		//備註
			*/
			//設定使用者資訊
			dataMap.putAll(paramMap);
			//新增產婦的基本資料
			ehf310100.addData(dataMap);
			//新增訂餐特殊資訊
			ehf310100.addDataSpecial(dataMap);
			//新增付款資訊
			ehf310100.addDataPay(dataMap);
			//新增贈品資訊
			//ehf310100.addDataGifts(dataMap);
			
			//關閉EHF310100元件
			ehf310100.close();
			
			//取出KEY_ID
			Form.setEHF310100T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF310100T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//新增完成
			chk_flag = true;
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	public boolean executeAddDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF310100M0F Form = (EHF310100M0F) form;
		boolean chk_flag = false;
		
		try{
			//建立EHF310100元件
			EHF310100 ehf310100 = new EHF310100();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			

			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			
			
			HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
			if("EHF310100T2".equals(type)){//訂餐資訊-養生飲品明細
				//養生飲品依飲品數量分開存複數筆資料    順序號碼有刪除明細需重新排序
				for(int i = 0 ; i<((String[])Form.getEHF310100T2_06_total()).length; i++){
					
					if(((String[])Form.getEHF310100T2_06_total())[i]!=null){
						
						detailDataMap.put("EHF310100T2_06",(String)((String[])Form.getEHF310100T2_06_total())[i]);
						ehf310100.addDetailData(type,detailDataMap);						
					}
				}
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐資訊", false);
				Form.setTabsutil_ORD("yes");
			}else if("EHF310100T1".equals(type)){	//訂餐資訊-每日明細
				//取得欲新增日期
				Calendar cal_start = tools.covertStringToCalendar((String)Form.getEHF310100T1_02_B());
				Calendar cal_end = tools.covertStringToCalendar((String)Form.getEHF310100T1_02_E());
				
				//若 結束日期在開始日期之後 或 結束日期等於開始日期 → 儲存當前日期資料
				while(cal_end.after(cal_start) || cal_end.equals(cal_start)){
					String startDate = tools.covertDateToString(cal_start.getTime(), "yyyy/MM/dd");
					detailDataMap.put("EHF310100T1_02",startDate);
					
					
					//取得欲新增餐別
					for(int i = 0 ; i<((String[])Form.getEHF310100T1_03_total()).length; i++){
						
						if(((String[])Form.getEHF310100T1_03_total())[i]!=null && !"".equals(((String[])Form.getEHF310100T1_03_total())[i])){
							detailDataMap.put("EHF310100T1_03",(String)((String[])Form.getEHF310100T1_03_total())[i]);
							
//							if("A".equals((String)detailDataMap.get("EHF310100T1_03"))){
								detailDataMap.put("EHF310100T1_04",(String)((String[])Form.getEHF310100T1_04_total())[i]==null?"R":"H");//R：住宅     H：醫院
								detailDataMap.put("EHF310100T1_05",(String)((String[])Form.getEHF310100T1_05_total())[i]==null?"0":"1");//是：素        否：葷
//							}else if("B".equals((String)detailDataMap.get("EHF310100T1_03"))){
//								
//							}else if("C".equals((String)detailDataMap.get("EHF310100T1_03"))){
//								
//							}
							
							ehf310100.addDetailData(type,detailDataMap);
						}else{
							//代表此餐不訂→跳過
						}
						
					}
					
					cal_start.add(Calendar.DAY_OF_MONTH, 1);
				}
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐資訊", false);
				Form.setTabsutil_ORD("yes");
			}else if("EHF310200T1".equals(type)){	//訂餐特殊資訊-不吃食物
				
				ehf310100.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310200T2".equals(type)){	//訂餐特殊資訊-不喝飲品
				
				ehf310100.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310200T3".equals(type)){//訂餐特殊資訊-特殊需求
				
				ehf310100.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310200T4".equals(type)){//訂餐特殊資訊-特殊口味
				
				ehf310100.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310200T5".equals(type)){//訂餐特殊資訊-清淡去油
				
				ehf310100.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310300T0".equals(type)){//電訪紀錄
				
				ehf310100.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "電訪紀錄", false);
				Form.setTabsutil_CALL("yes");
			}else if("EHF310400T1".equals(type)){//付款資訊
				
				ehf310100.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "付款資訊", false);
				Form.setTabsutil_PAY("yes");
			}else if("EHF310500T0".equals(type)){//贈品資訊明細
				ehf310100.addDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "贈品資訊", false);
				Form.setTabsutil_GIFT("yes");
			}
			
			//關閉EHF310100 元件
			ehf310100.close();
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF310100T0_01");
			paramMap.put(super.KEY_ID, key_id);
									
			//新增完成
			chk_flag = true;
			
			// 清掉畫面上的新增明細欄位
			this.cleanAddField(Form);
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	@Override
	protected Map executeQueryEditData(Connection conn, String[] key,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF310100M0F Form = (EHF310100M0F) form;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String user_id = (String)paramMap.get(super.USER_ID);
		String user_name = (String)paramMap.get(super.USER_NAME);
		Map return_map = new HashMap();		

		try{
			//建立EHF310100元件
			EHF310100 ehf310100 = new EHF310100();		
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF310100T0_01", key[0]);  //員工系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map menuMap = ehf310100.getMENU_MEAL("MENU_MEAL",comp_id);
			Map dataMap = ehf310100.queryEditData(queryCondMap);
			Map pay1Map = ehf310100.queryPay1Data(queryCondMap);
			Map pay2Map = ehf310100.queryPay2Data(queryCondMap);

			//醫院資訊
			Map hospitalMap = ehf310100.getHospital(comp_id);
			//住宅資訊
			Map residentialMap = ehf310100.getResidential(comp_id);
			//取得訂餐天數與開始送餐日期
			Map dateInf = ehf310100.getOrderDays(key[0],comp_id);
			int days = (Integer)dateInf.get("days");
			String date = (String)dateInf.get("date");
			//食物代碼
			Map foodtypeMap = ehf310100.getFoodType(comp_id);
			Map fooddetailMap = ehf310100.getFoodDetail(comp_id);
			//茶飲代碼
			Map teaMap = ehf310100.getTea(comp_id);
			
			//特殊需求
//			Map speDemandMap = ehf310100.getOptions("SpeDemand", comp_id);			
			//特殊口味
			Map speTasteMap = ehf310100.getOptions("ADDITIVES", comp_id);
			//清淡去油
			Map menuTypeMap = ehf310100.getOptions("MENU_TYPE", comp_id);
			//贈品資訊
			Map giftTypeMap = ehf310100.getOptions("GIFT", comp_id);
			//取得部門員工清單
			HR_TOOLS hr_tools = new HR_TOOLS();
			Map empMap = hr_tools.getEmpNameMap(comp_id);
//			Map depMap = hr_tools.getDepNameMap(comp_id);
			hr_tools.close();
			
			//養生飲品明細
			List EHF310100T2_List = ehf310100.queryEHF310100T2_List(queryCondMap);
			
			Iterator it_EHF310100T2_List = EHF310100T2_List.iterator();
			Map tempMap_EHF310100T2_List = null;
			EHF310100M0F bean_1 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_1 = new ArrayList<EHF310100M0F>();
			String EHF310100T2_02 ="";
			String EHF310100T2_03 ="";
			String EHF310100T2_05 ="";
			String EHF310100T2_06 ="";
			while(it_EHF310100T2_List.hasNext()){
				
				tempMap_EHF310100T2_List = (Map) it_EHF310100T2_List.next();
				
				bean_1 = new EHF310100M0F();
				//判斷日期起是否等於日期迄
				if(((String)tempMap_EHF310100T2_List.get("EHF310100T2_03")).equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_04"))){
					//代表日期起訖為同一天
					if(EHF310100T2_03.equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_03")) &&
					   EHF310100T2_06.equals(this.getEHF310100T2_06(conn,
							   										(String)queryCondMap.get("EHF310100T0_01"),
																    (String)tempMap_EHF310100T2_List.get("EHF310100T2_03"),
																    (String)tempMap_EHF310100T2_List.get("EHF310100T2_04"),
																    (String)tempMap_EHF310100T2_List.get("EHF310100T2_05"),
																    comp_id))){
						
						
					}else{
						EHF310100T2_03 = (String)tempMap_EHF310100T2_List.get("EHF310100T2_03");
						EHF310100T2_05 = (String)tempMap_EHF310100T2_List.get("EHF310100T2_05");
						bean_1.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));
						
						bean_1.setEHF310100T2_03(EHF310100T2_03);	//
						
						EHF310100T2_06 = this.getEHF310100T2_06(conn,
																(String)queryCondMap.get("EHF310100T0_01"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_03"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_04"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_05"),
															    comp_id);
						EHF310100T2_02 = this.getEHF310100T2_02(conn,
																(String)queryCondMap.get("EHF310100T0_01"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_03"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_04"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_05"),
															    comp_id);
						
						bean_1.setEHF310100T2_02(EHF310100T2_02);	//
						bean_1.setEHF310100T2_06(EHF310100T2_06);	//
						bean_1.setEHF310100T2_05((String)menuMap.get(EHF310100T2_05));	
						querylist_1.add(bean_1);
					}
					
				}else{
					
					if(EHF310100T2_03.equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_03")+" ~ "+(String)tempMap_EHF310100T2_List.get("EHF310100T2_04")) &&
							   EHF310100T2_06.equals(this.getEHF310100T2_06(conn,
																			(String)queryCondMap.get("EHF310100T0_01"),
																		    (String)tempMap_EHF310100T2_List.get("EHF310100T2_03"),
																		    (String)tempMap_EHF310100T2_List.get("EHF310100T2_04"),
																		    (String)tempMap_EHF310100T2_List.get("EHF310100T2_05"),
																		    comp_id))){
								
					}else{
						
						EHF310100T2_03 = (String)tempMap_EHF310100T2_List.get("EHF310100T2_03")+" ~ "+(String)tempMap_EHF310100T2_List.get("EHF310100T2_04");
						bean_1.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));
						bean_1.setEHF310100T2_03(EHF310100T2_03);	//
						
						EHF310100T2_06 = this.getEHF310100T2_06(conn,
																(String)queryCondMap.get("EHF310100T0_01"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_03"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_04"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_05"),
															    comp_id);
						
						EHF310100T2_02 = this.getEHF310100T2_02(conn,
																(String)queryCondMap.get("EHF310100T0_01"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_03"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_04"),
															    (String)tempMap_EHF310100T2_List.get("EHF310100T2_05"),
															    comp_id);
						
						bean_1.setEHF310100T2_02(EHF310100T2_02);	//
						bean_1.setEHF310100T2_06(EHF310100T2_06);	//
						bean_1.setEHF310100T2_05((String)menuMap.get((String)tempMap_EHF310100T2_List.get("EHF310100T2_05")));	
						querylist_1.add(bean_1);
					}
					
				}
				
				
					//				
											
			}
			
			Form.setEHF310100T2_List(querylist_1);
			
			//每日明細 - 日期
			List EHF310100T1_02_List = ehf310100.queryEHF310100T1_02_List(queryCondMap);
			
			Iterator it_EHF310100T1_02_List = EHF310100T1_02_List.iterator();
			Map tempMap_EHF310100T1_02_List = null;

			Map tempMap_EHF310100T1_List = null;
			//每日明細
			List EHF310100T1_List = null;
			
			EHF310100M0F bean_2 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_2 = new ArrayList<EHF310100M0F>();
			
			while(it_EHF310100T1_02_List.hasNext()){
				tempMap_EHF310100T1_02_List = (Map) it_EHF310100T1_02_List.next();
				
				//每日明細
				EHF310100T1_List = ehf310100.queryEHF310100T1_List(queryCondMap,tempMap_EHF310100T1_02_List);
				
				Iterator it_EHF310100T1_List = EHF310100T1_List.iterator();
				bean_2 = new EHF310100M0F();
				bean_2.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));
				bean_2.setEHF310100T1_02((String)tempMap_EHF310100T1_02_List.get("EHF310100T1_02"));
				while(it_EHF310100T1_List.hasNext()){
					
					tempMap_EHF310100T1_List = (Map) it_EHF310100T1_List.next();
					
					if("A".equals(((String)tempMap_EHF310100T1_List.get("EHF310100T1_03")))){
						bean_2.setEHF310100T1_03_A("1");
						bean_2.setEHF310100T1_04_A((String)tempMap_EHF310100T1_List.get("EHF310100T1_04"));
						bean_2.setEHF310100T1_05_A((Boolean)tempMap_EHF310100T1_List.get("EHF310100T1_05")==true?"1":"0");  //
					}else if("B".equals(((String)tempMap_EHF310100T1_List.get("EHF310100T1_03")))){
						bean_2.setEHF310100T1_03_B("1");  //
						bean_2.setEHF310100T1_04_B((String)tempMap_EHF310100T1_List.get("EHF310100T1_04"));  //
						bean_2.setEHF310100T1_05_B((Boolean)tempMap_EHF310100T1_List.get("EHF310100T1_05")==true?"1":"0");  
					}else if("C".equals(((String)tempMap_EHF310100T1_List.get("EHF310100T1_03")))){
						bean_2.setEHF310100T1_03_C("1");  //
						bean_2.setEHF310100T1_04_C((String)tempMap_EHF310100T1_List.get("EHF310100T1_04"));  //
						bean_2.setEHF310100T1_05_C((Boolean)tempMap_EHF310100T1_List.get("EHF310100T1_05")==true?"1":"0");  //
					}
					
				}
				if("".equals(bean_2.getEHF310100T1_03_A()) || bean_2.getEHF310100T1_03_A()==null){
					bean_2.setEHF310100T1_03_A("0");
					bean_2.setEHF310100T1_04_A("");
					bean_2.setEHF310100T1_05_A("");  //
				}
				if("".equals(bean_2.getEHF310100T1_03_B()) || bean_2.getEHF310100T1_03_B()==null){
					bean_2.setEHF310100T1_03_B("0");
					bean_2.setEHF310100T1_04_B("");
					bean_2.setEHF310100T1_05_B("");  //
				}
				if("".equals(bean_2.getEHF310100T1_03_C()) || bean_2.getEHF310100T1_03_C()==null){
					bean_2.setEHF310100T1_03_C("0");
					bean_2.setEHF310100T1_04_C("");
					bean_2.setEHF310100T1_05_C("");  //
				}
				
				querylist_2.add(bean_2);
				EHF310100T1_List.clear();
			}
			
			
			
			Form.setEHF310100T1_List(querylist_2);
			//不吃食物
			List EHF310200T1_List = ehf310100.queryEHF310200T1_List(queryCondMap);
			
			Iterator it_EHF310200T1_List = EHF310200T1_List.iterator();
			Map tempMap_EHF310200T1_List = null;
			EHF310100M0F bean_3 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_3 = new ArrayList<EHF310100M0F>();
			
			while(it_EHF310200T1_List.hasNext()){
				
				tempMap_EHF310200T1_List = (Map) it_EHF310200T1_List.next();
				
				bean_3 = new EHF310100M0F();
				

				bean_3.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));
				bean_3.setEHF310200T1_02(String.valueOf((Integer)tempMap_EHF310200T1_List.get("EHF310200T1_02")));
				if((Integer)tempMap_EHF310200T1_List.get("EHF310200T1_03").toString().split("/").length<2){
					bean_3.setEHF310200T1_03(foodtypeMap.get(tempMap_EHF310200T1_List.get("EHF310200T1_03").toString().split("/")[0])+"/全部");	//
				}else{
					bean_3.setEHF310200T1_03(foodtypeMap.get(tempMap_EHF310200T1_List.get("EHF310200T1_03").toString().split("/")[0])+"/"+
											 fooddetailMap.get(tempMap_EHF310200T1_List.get("EHF310200T1_03").toString().split("/")[1]));	//
				}
				
				
				querylist_3.add(bean_3);
				
			}
									
			Form.setEHF310200T1_List(querylist_3);
			
			
			//不喝飲品
			List EHF310200T2_List = ehf310100.queryEHF310200T2_List(queryCondMap);
			
			Iterator it_EHF310200T2_List = EHF310200T2_List.iterator();
			Map tempMap_EHF310200T2_List = null;
			EHF310100M0F bean_6 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_6 = new ArrayList<EHF310100M0F>();
			
			while(it_EHF310200T2_List.hasNext()){
				
				tempMap_EHF310200T2_List = (Map) it_EHF310200T2_List.next();
				
				bean_6 = new EHF310100M0F();
				bean_6.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));
				bean_6.setEHF310200T2_02(String.valueOf((Integer)tempMap_EHF310200T2_List.get("EHF310200T2_02")));
				bean_6.setEHF310200T2_03((String)teaMap.get((String)tempMap_EHF310200T2_List.get("EHF310200T2_03")));	//
				
				querylist_6.add(bean_6);
				
			}
															
			Form.setEHF310200T2_List(querylist_6);						
			//加量需求
			List EHF310200T3_List = ehf310100.queryEHF310200T3_List(queryCondMap);
			
			Iterator it_EHF310200T3 = EHF310200T3_List.iterator();
			Map tempMap_EHF310200T3_List = null;
			EHF310100M0F bean_4 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_4 = new ArrayList<EHF310100M0F>();
			
			while(it_EHF310200T3.hasNext()){
				
				tempMap_EHF310200T3_List = (Map) it_EHF310200T3.next();
				
				bean_4 = new EHF310100M0F();
				bean_4.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));
				bean_4.setEHF310200T3_02(String.valueOf((Integer)tempMap_EHF310200T3_List.get("EHF310200T3_02")));
				bean_4.setEHF310200T3_03((String)menuTypeMap.get((String)tempMap_EHF310200T3_List.get("EHF310200T3_03")));	//特殊需求
				
				querylist_4.add(bean_4);
				
			}
			
			Form.setEHF310200T3_List(querylist_4);
			
			
			//特殊口味						
			List EHF310200T4_List = ehf310100.queryEHF310200T4_List(queryCondMap);
			
			Iterator it_EHF310200T4 = EHF310200T4_List.iterator();
			Map tempMap_EHF310200T4_List = null;
			EHF310100M0F bean_5 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_5 = new ArrayList<EHF310100M0F>();
			
			while(it_EHF310200T4.hasNext()){
				
				tempMap_EHF310200T4_List = (Map) it_EHF310200T4.next();
				
				bean_5 = new EHF310100M0F();
				bean_5.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));
				bean_5.setEHF310200T4_02(String.valueOf((Integer)tempMap_EHF310200T4_List.get("EHF310200T4_02")));
				bean_5.setEHF310200T4_03((String)speTasteMap.get((String)tempMap_EHF310200T4_List.get("EHF310200T4_03")));	//特殊口味
				
				querylist_5.add(bean_5);
				
			}
			
			Form.setEHF310200T4_List(querylist_5);
			
			//清淡去油
			List EHF310200T5_List = ehf310100.queryEHF310200T5_List(queryCondMap);
			
			Iterator it_EHF310200T5 = EHF310200T5_List.iterator();
			Map tempMap_EHF310200T5_List = null;
			EHF310100M0F bean_7 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_7 = new ArrayList<EHF310100M0F>();
			
			while(it_EHF310200T5.hasNext()){
				
				tempMap_EHF310200T5_List = (Map) it_EHF310200T5.next();
				
				bean_7 = new EHF310100M0F();
				bean_7.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));
				bean_7.setEHF310200T5_02(String.valueOf((Integer)tempMap_EHF310200T5_List.get("EHF310200T5_02")));
				bean_7.setEHF310200T5_03((String)menuTypeMap.get((String)tempMap_EHF310200T5_List.get("EHF310200T5_03")));	//清淡去油
				
				querylist_7.add(bean_7);
				
			}
			
			Form.setEHF310200T5_List(querylist_7);
			
			//電訪次數			
			//判斷是否已填送餐日期資訊
			if(date!=null && !"".equals(date)){
				//開始送餐日期
				Calendar cal_start = tools.covertStringToCalendar(date);
				Calendar cal_first = (Calendar)cal_start.clone();
				Calendar cal_second = (Calendar)cal_start.clone();
				Calendar cal_third = (Calendar)cal_start.clone();
				String firstDate = "";
				String secondDate = "";
				String thirdDate = "";
				//計算預計電訪日期
				if(days>=30){
					cal_first.add(Calendar.DAY_OF_MONTH, 4);
					cal_second.add(Calendar.DAY_OF_MONTH, 14);
					cal_third.add(Calendar.DAY_OF_MONTH, 24);
					
					firstDate = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
					secondDate = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");
					thirdDate = tools.covertDateToString(cal_third.getTime(), "yyyy/MM/dd");
					
					request.setAttribute("DATE_MSG", "預計3次電訪："+firstDate+"、"+secondDate+"、"+thirdDate);
				}else if(days<30 && days>10){
					cal_first.add(Calendar.DAY_OF_MONTH, 3);
					cal_second.add(Calendar.DAY_OF_MONTH, 8);
					cal_third.add(Calendar.DAY_OF_MONTH, 14);
					
					firstDate = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
					secondDate = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");
					thirdDate = tools.covertDateToString(cal_third.getTime(), "yyyy/MM/dd");
					
					request.setAttribute("DATE_MSG", "預計3次電訪："+firstDate+"、"+secondDate+"、"+thirdDate);
				}else if(days<=10){
					cal_first.add(Calendar.DAY_OF_MONTH, 2);
					cal_second.add(Calendar.DAY_OF_MONTH, 7);
					
					firstDate = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
					secondDate = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");
					
					request.setAttribute("DATE_MSG", "預計2次電訪："+firstDate+"、"+secondDate);
				}else{
					request.setAttribute("DATE_MSG", "未設定訂餐天數，請檢察產婦基本資料頁籤是否填寫正確!!");
				}
			}else{
				request.setAttribute("DATE_MSG", "未設定送餐日期，請檢察產婦基本資料頁籤是否填寫正確!!");
			}
			
			//電訪紀錄
			List EHF310300T0_List = ehf310100.queryEHF310300T0_List(queryCondMap);
			
			Iterator it_EHF310300T0 = EHF310300T0_List.iterator();
			Map tempMap_EHF310300T0_List = null;
			EHF310100M0F bean_8 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_8 = new ArrayList<EHF310100M0F>();
			
			while(it_EHF310300T0.hasNext()){
				
				tempMap_EHF310300T0_List = (Map) it_EHF310300T0.next();
				
				bean_8 = new EHF310100M0F();
				bean_8.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));//系統編號
				bean_8.setEHF310300T0_02(String.valueOf((Integer)tempMap_EHF310300T0_List.get("EHF310300T0_02")));//順序號碼
				bean_8.setEHF310300T0_03((String)tempMap_EHF310300T0_List.get("EHF310300T0_03"));	//電訪人員系統編號
				bean_8.setEHF310300T0_03_TXT((String)((Map)empMap.get((String)tempMap_EHF310300T0_List.get("EHF310300T0_03"))).get("EMPLOYEE_NAME"));	//電訪人員姓名
				bean_8.setEHF310300T0_04((String)tempMap_EHF310300T0_List.get("EHF310300T0_04"));//電訪日期
				bean_8.setEHF310300T0_05((String)tempMap_EHF310300T0_List.get("EHF310300T0_05"));//電訪內容
				bean_8.setEHF310300T0_06((String)tempMap_EHF310300T0_List.get("EHF310300T0_06"));//備註
				
				querylist_8.add(bean_8);
				
			}
			
			Form.setEHF310300T0_List(querylist_8);
			
			
			
			//付款資訊
			List EHF310400T1_List = ehf310100.queryEHF310400T1_List(queryCondMap);
			
			Iterator it_EHF310400T1_List = EHF310400T1_List.iterator();
			Map tempMap_EHF310400T1_List = null;
			EHF310100M0F bean_9 = null;
			request.setAttribute("status", "no");//隱藏付款資訊是否確認欄位
			//建立空清單
			List<EHF310100M0F> querylist_9 = new ArrayList<EHF310100M0F>();
			

			while(it_EHF310400T1_List.hasNext()){
				
				tempMap_EHF310400T1_List = (Map) it_EHF310400T1_List.next();
				
				bean_9 = new EHF310100M0F();
				bean_9.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));	//系統編號
				bean_9.setEHF310400T1_02((Integer)tempMap_EHF310400T1_List.get("EHF310400T1_02"));	//順序編號
				bean_9.setEHF310400T1_03((String)tempMap_EHF310400T1_List.get("EHF310400T1_03"));	//開單日期
				
				bean_9.setEHF310400T1_04((String)tempMap_EHF310400T1_List.get("EHF310400T1_04"));	//開單人員
				bean_9.setEHF310400T1_04_TXT((String)((Map)empMap.get((String)tempMap_EHF310400T1_List.get("EHF310400T1_04"))).get("EMPLOYEE_NAME"));
				
				bean_9.setEHF310400T1_05((String)tempMap_EHF310400T1_List.get("EHF310400T1_05")==null?"":(String)tempMap_EHF310400T1_List.get("EHF310400T1_05"));	//經手人員
				
//				if(!"".equals((String)tempMap_EHF310400T1_List.get("EHF310400T1_05")) && (String)tempMap_EHF310400T1_List.get("EHF310400T1_05")!=null){
//					bean_9.setEHF310400T1_05_TXT((String)((Map)empMap.get((String)tempMap_EHF310400T1_List.get("EHF310400T1_05"))).get("EMPLOYEE_NAME"));
//				}
				
				bean_9.setEHF310400T1_06((String)tempMap_EHF310400T1_List.get("EHF310400T1_06"));	//付款方式
				bean_9.setEHF310400T1_07((String)tempMap_EHF310400T1_List.get("EHF310400T1_07"));	//付款類別
				bean_9.setEHF310400T1_08((String)tempMap_EHF310400T1_List.get("EHF310400T1_08"));	//預計收款日
				bean_9.setEHF310400T1_09((Integer)tempMap_EHF310400T1_List.get("EHF310400T1_09"));	//預計金額
				bean_9.setEHF310400T1_10((String)tempMap_EHF310400T1_List.get("EHF310400T1_10")==null?"":(String)tempMap_EHF310400T1_List.get("EHF310400T1_10"));	//實際收款日
				bean_9.setEHF310400T1_11((Integer)tempMap_EHF310400T1_List.get("EHF310400T1_11")==null?0:(Integer)tempMap_EHF310400T1_List.get("EHF310400T1_11"));	//實際金額
				bean_9.setEHF310400T1_12((String)tempMap_EHF310400T1_List.get("EHF310400T1_12")==null?"":(String)tempMap_EHF310400T1_List.get("EHF310400T1_12"));	//備註
				bean_9.setEHF310400T1_13((Boolean)tempMap_EHF310400T1_List.get("EHF310400T1_13")==true?"1":"0");	//確認
				bean_9.setEHF310400T1_14((Boolean)tempMap_EHF310400T1_List.get("EHF310400T1_14")==true?"1":"0");	//刪除
				
				querylist_9.add(bean_9);
				
			}
			
															
			Form.setEHF310400T1_List(querylist_9);
			
			//贈品資訊giftTypeMap
			List EHF310500T0_List = ehf310100.queryEHF310500T0_List(queryCondMap);
			
			Iterator it_EHF310500T0_List = EHF310500T0_List.iterator();
			Map tempMap_EHF310500T0_List = null;
			EHF310100M0F bean_10 = null;
			
			//建立空清單
			List<EHF310100M0F> querylist_10 = new ArrayList<EHF310100M0F>();
			
			while(it_EHF310500T0_List.hasNext()){
				
				tempMap_EHF310500T0_List = (Map) it_EHF310500T0_List.next();
				
				bean_10 = new EHF310100M0F();
				bean_10.setEHF310100T0_01((String)queryCondMap.get("EHF310100T0_01"));	//系統編號
				bean_10.setEHF310500T0_02((Integer)tempMap_EHF310500T0_List.get("EHF310500T0_02"));	//順序編號
				bean_10.setEHF310500T0_05((String)tempMap_EHF310500T0_List.get("EHF310500T0_05"));	//開單日期
				bean_10.setEHF310500T0_04((String)tempMap_EHF310500T0_List.get("EHF310500T0_04"));	//領取日期
				bean_10.setEHF310500T0_06_TXT((String)giftTypeMap.get((String)tempMap_EHF310500T0_List.get("EHF310500T0_06")));	//贈品
				bean_10.setEHF310500T0_08((String)tempMap_EHF310500T0_List.get("EHF310500T0_08"));	//備註
				
				querylist_10.add(bean_10);
				
			}
						
			Form.setEHF310500T0_List(querylist_10);
						
			//關閉EHF310100元件
			ehf310100.close();
			
			
			if(!dataMap.isEmpty()){
								
				//產婦基本資料
				Form.setEHF310100T0_01((String)dataMap.get("EHF310100T0_01"));  					//系統編號
				Form.setEHF310100T0_02((String)dataMap.get("EHF310100T0_02"));  					//檔案編號
				Form.setEHF310100T0_03((String)dataMap.get("EHF310100T0_03"));						//櫃號
				Form.setEHF310100T0_04((String)dataMap.get("EHF310100T0_04"));						//產婦姓名				
				Form.setEHF310100T0_05((String)dataMap.get("EHF310100T0_05"));						//產別
				
				Form.setEHF310100T0_06((String)dataMap.get("EHF310100T0_06"));						//預產期
				Form.setEHF310100T0_07((String)dataMap.get("EHF310100T0_07"));						//生產期
				Form.setEHF310100T0_08((String)dataMap.get("EHF310100T0_08"));						//訂餐日期
				Form.setEHF310100T0_09((Integer)dataMap.get("EHF310100T0_09"));    //訂餐天數
				Form.setEHF310100T0_10((String)dataMap.get("EHF310100T0_10"));						//送餐日期
				
				Form.setEHF310100T0_11((String)dataMap.get("EHF310100T0_11"));						//醫院代碼
				
				if(!"".equals((String)dataMap.get("EHF310100T0_11")) && (String)dataMap.get("EHF310100T0_11") != null){
					Form.setEHF310100T0_11_TXT((String)hospitalMap.get((String)dataMap.get("EHF310100T0_11")));					//醫院名稱
				}else{
					Form.setEHF310100T0_11_TXT("");
				}
				
				Form.setEHF310100T0_15((String)dataMap.get("EHF310100T0_15"));						//醫院房號
				Form.setEHF310100T0_16((String)dataMap.get("EHF310100T0_16"));						//醫院路線
				Form.setEHF310100T0_16_TXT( ((String)residentialMap.get((String)dataMap.get("EHF310100T0_16")))==null?""
						:((String)residentialMap.get((String)dataMap.get("EHF310100T0_16"))) );//醫院路線名稱
				Form.setEHF310100T0_12((Integer)dataMap.get("EHF310100T0_12"));	//醫院用餐天數
				Form.setEHF310100T0_13((String)dataMap.get("EHF310100T0_13"));						//醫院用餐期間起
				Form.setEHF310100T0_14((String)dataMap.get("EHF310100T0_14"));						//醫院用餐期間迄
				
				Form.setEHF310100T0_17((String)dataMap.get("EHF310100T0_17"));						//住宅路線代碼
				
				if(!"".equals((String)dataMap.get("EHF310100T0_17")) && (String)dataMap.get("EHF310100T0_17") != null){
					Form.setEHF310100T0_17_TXT((String)residentialMap.get((String)dataMap.get("EHF310100T0_17")));					//住宅路線名稱  
				}else{
					Form.setEHF310100T0_17_TXT("");
				}
				
				Form.setEHF310100T0_18((Integer)dataMap.get("EHF310100T0_18"));	//住宅用餐天數
				Form.setEHF310100T0_19((String)dataMap.get("EHF310100T0_19"));						//住宅用餐期間起
				Form.setEHF310100T0_20((String)dataMap.get("EHF310100T0_20"));						//住宅用餐期間迄
				Form.setEHF310100T0_21((String)dataMap.get("EHF310100T0_21"));						//行動電話(產婦)
				Form.setEHF310100T0_22((String)dataMap.get("EHF310100T0_22"));						//連絡電話(住)
				
				Form.setEHF310100T0_23((String)dataMap.get("EHF310100T0_23"));						//聯絡1
				Form.setEHF310100T0_24((String)dataMap.get("EHF310100T0_24"));						//聯絡1
				
				Form.setEHF310100T0_25((String)dataMap.get("EHF310100T0_25"));						//聯絡2
				Form.setEHF310100T0_26((String)dataMap.get("EHF310100T0_26"));						//聯絡2
				
				Form.setEHF310100T0_27((String)dataMap.get("EHF310100T0_27"));						//聯絡3
				Form.setEHF310100T0_28((String)dataMap.get("EHF310100T0_28"));						//聯絡3
				
				Form.setEHF310100T0_29((String)dataMap.get("EHF310100T0_29"));						//聯絡4
				Form.setEHF310100T0_30((String)dataMap.get("EHF310100T0_30"));						//聯絡4
				
				Form.setEHF310100T0_31((String)dataMap.get("EHF310100T0_31"));						//產婦地址
				Form.setEHF310100T0_32((String)dataMap.get("EHF310100T0_32"));						//備註1
				Form.setEHF310100T0_33((String)dataMap.get("EHF310100T0_33"));						//備註2
				Form.setEHF310100T0_34(comp_id);													//公司代碼
				
				//訂餐特殊資訊
				Form.setEHF310200T0_02(String.valueOf((Boolean)dataMap.get("EHF310200T0_02")));						//特殊習慣
				Form.setEHF310200T0_03((String)dataMap.get("EHF310200T0_03"));						//備註
				
				//贈品資訊
				/*Form.setEHF310500T0_02((String)dataMap.get("EHF310500T0_02"));						//接待人員
				if(!"".equals((String)dataMap.get("EHF310500T0_02")) && (String)dataMap.get("EHF310500T0_02")!=null){
					Form.setEHF310500T0_02_TXT((String)((Map)empMap.get((String)dataMap.get("EHF310500T0_02"))).get("EMPLOYEE_NAME"));						//接待人員姓名
				}
				
				Form.setEHF310500T0_03((Boolean)dataMap.get("EHF310500T0_03")==true?"1":"0");		//是否有贈品
				Form.setEHF310500T0_04((String)dataMap.get("EHF310500T0_04"));						//領取日期
				Form.setEHF310500T0_05((String)dataMap.get("EHF310500T0_05"));						//開單日期
				Form.setEHF310500T0_06((String)dataMap.get("EHF310500T0_06"));						//贈品類別
				Form.setEHF310500T0_07((Integer)dataMap.get("EHF310500T0_07")==null?0:(Integer)dataMap.get("EHF310500T0_07"));	//單位份數
				Form.setEHF310500T0_08((String)dataMap.get("EHF310500T0_08"));						//備註
				*/
				
				if(!pay1Map.isEmpty()){
					//付款資訊1
					Form.setEHF310400T0_04((Integer)pay1Map.get("EHF310400T0_04"));//折扣金額
					Form.setEHF310400T0_05((Integer)pay1Map.get("EHF310400T0_05"));//已付金額
					Form.setEHF310400T0_02((Integer)pay1Map.get("EHF310400T0_02"));//訂餐天數
					Form.setEHF310400T0_03((Integer)pay1Map.get("EHF310400T0_03"));//定價
					Form.setEHF310400T2_realPay((Integer)pay1Map.get("EHF310400T0_03")-(Integer)pay1Map.get("EHF310400T0_04"));//實際金額 = 定價 - 折扣金額  
					Form.setEHF310400T2_unPay((Integer)pay1Map.get("EHF310400T0_03")-(Integer)pay1Map.get("EHF310400T0_04")-(Integer)pay1Map.get("EHF310400T0_05"));//未付金額 = 實際金額 - 已付金額
				}
				if(!pay2Map.isEmpty()){
					//付款資訊2
					
					if(pay2Map.containsKey("P04") && pay2Map.get("P04")!=null){
						Form.setEHF310400T2_03_All((Integer)pay2Map.get("P04_count"));
						Form.setEHF310400T2_03_All_Pay((Integer)pay2Map.get("P04_count")*this.getpriceMap(conn,"P04",comp_id));
					}else if(pay2Map.containsKey("P05") && pay2Map.get("P05")!=null){
						Form.setEHF310400T2_03_All((Integer)pay2Map.get("P05_count"));
						Form.setEHF310400T2_03_All_Pay((Integer)pay2Map.get("P05_count")*this.getpriceMap(conn,"P05",comp_id));
					}
					
					
					Form.setEHF310400T2_03_A((Integer)pay2Map.get("P01_count"));
					Form.setEHF310400T2_03_A_Pay((Integer)pay2Map.get("P01_count")*this.getpriceMap(conn,"P01",comp_id));
					
					Form.setEHF310400T2_03_B((Integer)pay2Map.get("P02_count"));
					Form.setEHF310400T2_03_B_Pay((Integer)pay2Map.get("P02_count")*this.getpriceMap(conn,"P02",comp_id));
					
					Form.setEHF310400T2_03_C((Integer)pay2Map.get("P03_count"));
					Form.setEHF310400T2_03_C_Pay((Integer)pay2Map.get("P03_count")*this.getpriceMap(conn,"P03",comp_id));
					
					
					
				}
				chk_flag = true;
				
			}
			
			//set TabsUtil
			Map tabCtrlMap = (Map)paramMap.get("tabCtrlMap");
			Map config = new HashMap();
			if("yes".equals(Form.getTabsutil_CUS())){
				config.put("KEYVALUE", "產婦基本資料");//設定頁籤
				Form.setTabsutil_CUS("");//清空
			}else if("yes".equals(Form.getTabsutil_ORD())){
				config.put("KEYVALUE", "訂餐資訊");//設定頁籤
				Form.setTabsutil_ORD("");//清空
			}else if("yes".equals(Form.getTabsutil_SPE())){
				config.put("KEYVALUE", "訂餐特殊資訊");//設定頁籤
				Form.setTabsutil_SPE("");//清空
			}else if("yes".equals(Form.getTabsutil_CALL())){
				config.put("KEYVALUE", "電訪紀錄");//設定頁籤
				Form.setTabsutil_CALL("");//清空
			}else if("yes".equals(Form.getTabsutil_PAY())){
				config.put("KEYVALUE", "付款資訊");//設定頁籤
				Form.setTabsutil_PAY("");//清空
			}else if("yes".equals(Form.getTabsutil_GIFT())){
				config.put("KEYVALUE", "贈品資訊");//設定頁籤
				Form.setTabsutil_GIFT("");//清空
			}else{
				config.put("KEYVALUE", "產婦基本資料");
			}
			tabCtrlMap.put("EHF310100M1_Tabs_01", config);
			paramMap.remove("tabCtrlMap");
			paramMap.put("tabCtrlMap",tabCtrlMap);
			//super.addCurrentTabConfig(paramMap, "EHF010100M1_Tabs_01", "員工基本資料", false);
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}
	private int getpriceMap(Connection conn, String EHF300300T0_01, String comp_id) {
		// TODO Auto-generated method stub
		
		int price = 0;
		
		try{
			String sql = "" +
						 " SELECT * " +
						 " FROM EHF300300T0 " +
						 " WHERE 1=1 " +
						 " AND EHF300300T0_01 = '"+EHF300300T0_01+"'" +
						 " AND EHF300300T0_04 = '"+comp_id+"' ";		
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			while(rs.next()){
				price = rs.getInt("EHF300300T0_03");				
			}
				
			rs.close();
			stmt.close();
		}catch(Exception e){
			System.out.println("取得早中晚餐定價時錯誤");
		}
		
		return price;
	}

	private String getEHF310100T2_02(Connection conn, String KEY_ID, String startDate, String endDate, String EHF310100T2_05, String comp_id) {
		// TODO Auto-generated method stub
		
		String EHF310100T2_02="";
		try{
			String sql = "" +
			" SELECT e.EHF310100T2_02, e.EHF310100T2_06 " +
			" FROM EHF310100T2 e " +
			" WHERE 1=1 " +
			" AND EHF310100T2_01 = '"+KEY_ID+"' " +
			" AND EHF310100T2_03 = '"+startDate+"' " +
			" AND EHF310100T2_04 = '"+endDate+"' " +
			" AND EHF310100T2_05 = '"+EHF310100T2_05+"' " +
			" AND EHF310100T2_07 = '"+comp_id+"' ";		

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				EHF310100T2_02 += rs.getString("EHF310100T2_02")+" ";
			}

			rs.close();
			stmt.close();
		}catch(Exception e){
			
		}
		
		
		return EHF310100T2_02;
	}
	
	private String getEHF310100T2_06(Connection conn, String KEY_ID, String startDate, String endDate, String EHF310100T2_05, String comp_id) {
		// TODO Auto-generated method stub
		
		String EHF310100T2_06="";
		try{
			String sql = "" +
			" SELECT e.EHF310100T2_06,a.EMS_CategoryT1_05 " +
			" FROM EHF310100T2 e " +
			" LEFT JOIN EMS_CategoryT1 a ON e.EHF310100T2_06 = a.EMS_CategoryT1_04 AND e.EHF310100T2_07 = a.EMS_CategoryT1_09 " +
			" WHERE 1=1 " +
			" AND EHF310100T2_01 = '"+KEY_ID+"' " +
			" AND EHF310100T2_03 = '"+startDate+"' " +
			" AND EHF310100T2_04 = '"+endDate+"' " +
			" AND EHF310100T2_05 = '"+EHF310100T2_05+"' " +
			" AND EHF310100T2_07 = '"+comp_id+"' " +
			" AND a.EMS_CategoryT1_01='Drink' " ;		

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				EHF310100T2_06 += rs.getString("EMS_CategoryT1_05")+" ";
			}

			rs.close();
			stmt.close();
		}catch(Exception e){
			
		}
		
		
		return EHF310100T2_06;
	}

	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean executeSaveDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		boolean chk_flag = false;
		
		return chk_flag;
		
	}

	@Override
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF310100M0F Form = (EHF310100M0F) form;
		
		try{
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF310100T0_01());
			paramMap.put(super.KEY_ID, key_id);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}

	}

	@Override
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF310100M0F Form = (EHF310100M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF310100T0_01());
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return key;
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean executeDelDetailData(Connection conn, String type, ActionForm form, Map paramMap) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		boolean chk_flag = false;
		
		
		try{
			//建立EHF310100元件
			EHF310100 ehf310100 = new EHF310100();
			
			//建立細項新增資料Map
			Map detailDataMap = new HashMap();
			detailDataMap = BeanUtils.describe(Form);
			
			//set tabsutil
			//Form.setTabsutil("yes");

			//設定使用者資訊
			detailDataMap.putAll(paramMap);
			
			
			
			HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
			if("EHF310100T2".equals(type)){//訂餐資訊-養生飲品明細
				//養生飲品依飲品數量分開存複數筆資料    順序號碼有刪除明細需重新排序
				
				ehf310100.delDetailData(type,detailDataMap);
				//若材料明細資料有改變則要讓Tab停留在材料明細
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐資訊", false);
				Form.setTabsutil_ORD("yes");
			}else if("EHF310100T1".equals(type)){	//訂餐資訊-每日明細
				
				
				ehf310100.delDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐資訊", false);
				Form.setTabsutil_ORD("yes");
			}else if("EHF310200T1".equals(type)){//訂餐特殊資訊-不吃食物
				//順序號碼有刪除明細需重新排序
				ehf310100.delDetailData(type,detailDataMap);
				//設定Tab資訊
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310200T2".equals(type)){//訂餐特殊資訊-不喝飲品
				
				ehf310100.delDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310200T3".equals(type)){//訂餐特殊資訊-特殊需求
				
				ehf310100.delDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310200T4".equals(type)){//訂餐特殊資訊-特殊口味
				
				ehf310100.delDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310200T5".equals(type)){//訂餐特殊資訊-清淡去油
				
				ehf310100.delDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "訂餐特殊資訊", false);
				Form.setTabsutil_SPE("yes");
			}else if("EHF310300T0".equals(type)){//電訪紀錄
				
				ehf310100.delDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "電訪紀錄", false);
				Form.setTabsutil_CALL("yes");				
			}else if("EHF310500T0".equals(type)){//贈品資訊
				
				ehf310100.delDetailData(type,detailDataMap);
				
				super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "贈品資訊", false);
				Form.setTabsutil_GIFT("yes");
			}
			
			//關閉EHF310100 元件
			ehf310100.close();
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = (String)detailDataMap.get("EHF310100T0_01");
			paramMap.put(super.KEY_ID, key_id);
									
			//新增完成
			chk_flag = true;
			
			// 清掉畫面上的新增明細欄位
			this.cleanAddField(Form);
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
		
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		EHF310100M0F Form = (EHF310100M0F) form;
		List list = new ArrayList();
//		String[] drink = new String[50];
//		List drinkList =  this.getcheckboxes(conn, false, "Drink", getLoginUser(request).getCompId());
//		for(int i = 0;i<=drinkList.size();i++){
////			Map map = (Map)drinkList.get(i);
//			drink[i] = "";
//		}

		
		try{
			Form.setEHF310100T2_List(list);
			Form.setEHF310100T1_List(list);
			Form.setEHF310200T1_List(list);
			Form.setEHF310200T2_List(list);
			Form.setEHF310200T3_List(list);
			Form.setEHF310200T4_List(list);
			Form.setEHF310200T5_List(list);
			Form.setEHF310400T1_List(list);

			request.setAttribute("button_init", "init");
			super.addCurrentTabConfig(paramMap, "EHF310100M1_Tabs_01", "產婦基本資料", false);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return (ActionForm)Form;
	}

	

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		try{
			//產別
			List listEHF310100T0_05 = tools.getOptions(conn, true, "BirthType", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310100T0_05", listEHF310100T0_05);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		try{
			//路線設定
			List listEHF310100T1_04 = new ArrayList();
			BA_ALLKINDForm 	bform = new BA_ALLKINDForm();
			bform.setItem_id("R");
			bform.setItem_value("住宅");
			listEHF310100T1_04.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("H");
			bform.setItem_value("醫院");
			listEHF310100T1_04.add(bform);
			request.setAttribute("listEHF310100T1_04", listEHF310100T1_04);
			
		}catch(Exception e) {
			System.out.println(e);
		}
		try{
			//餐點素食
			List listEHF310100T1_05 = new ArrayList();
			BA_ALLKINDForm 	bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("是");
			listEHF310100T1_05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0");
			bform.setItem_value("否");
			listEHF310100T1_05.add(bform);
			request.setAttribute("listEHF310100T1_05", listEHF310100T1_05);
			request.setAttribute("listEHF310500T0_03", listEHF310100T1_05);
		}catch(Exception e) {
			System.out.println(e);
		}
		try{
			//有無訂餐
			List listEHF310100T1_03 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("有");
			listEHF310100T1_03.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("0");
			bform.setItem_value("無");
			listEHF310100T1_03.add(bform);
			request.setAttribute("listEHF310100T1_03_TF", listEHF310100T1_03);
		}catch(Exception e) {
			System.out.println(e);
		}
		try{
			//餐別
			List listEHF310100T2_05 = tools.getOptions(conn, false, "MENU_MEAL", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310100T2_05", listEHF310100T2_05);
			request.setAttribute("listEHF310100T1_03", listEHF310100T2_05);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			//菜單類別
			List listEHF310200T5_03 = tools.getOptions(conn, true, "MENU_TYPE", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310200T5_03", listEHF310200T5_03);//清淡去油
			request.setAttribute("listEHF310200T3_03", listEHF310200T5_03);//加量需求
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			//特殊口味
			List listEHF310200T4_03 = tools.getOptions(conn, true, "ADDITIVES", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310200T4_03", listEHF310200T4_03);
		}catch(Exception e) {
			System.out.println(e);
		}
		
/*		try{
			//特殊需求
			List listEHF310200T3_03 = tools.getOptions(conn, true, "SpeDemand", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310200T3_03", listEHF310200T3_03);
		}catch(Exception e) {
			System.out.println(e);
		}*/
		
		try{
			//飲品
			List listEHF310100T2_06 = tools.getOptions(conn, false, "Drink", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310100T2_06", listEHF310100T2_06);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			//茶飲
			List listEHF310200T2_03 = tools.getOptions(conn, true, "TEA", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310200T2_03", listEHF310200T2_03);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			//付款方式
			List listEHF310400T1_06 = tools.getOptions(conn, true, "PayType", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310400T1_06", listEHF310400T1_06);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			//付款類別
			List listEHF310400T1_07 = tools.getOptions(conn, true, "PayCategor", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310400T1_07", listEHF310400T1_07);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			//贈品類別
			List listEHF310500T0_06 = tools.getOptions(conn, true, "GIFT", getLoginUser(request).getCompId());
			request.setAttribute("listEHF310500T0_06", listEHF310500T0_06);
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	/**
	 * 儲存產婦基本資料資訊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataCUS(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataCUS");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			if (lc_errors.isEmpty()) {
				
				
				String insql = "" +
				" UPDATE EHF310100T0 SET EHF310100T0_02=?, EHF310100T0_03=?, EHF310100T0_04=?, EHF310100T0_05=?, " +
				"						 EHF310100T0_06=?, EHF310100T0_07=?, EHF310100T0_08=?, EHF310100T0_09=?, EHF310100T0_10=?, " +
				"						 EHF310100T0_11=?, EHF310100T0_12=?, EHF310100T0_13=?, EHF310100T0_14=?, EHF310100T0_15=?, " +
				"						 EHF310100T0_16=?, EHF310100T0_17=?, EHF310100T0_18=?, EHF310100T0_19=?, EHF310100T0_20=?, " +
				"						 EHF310100T0_21=?, EHF310100T0_22=?, EHF310100T0_23=?, EHF310100T0_24=?, EHF310100T0_25=?, " +
				"						 EHF310100T0_26=?, EHF310100T0_27=?, EHF310100T0_28=?, EHF310100T0_29=?, EHF310100T0_30=?, " +
				"						 EHF310100T0_31=?, EHF310100T0_32=?, EHF310100T0_33=?, " +
				"						 USER_UPDATE=? , VERSION = VERSION + 1, DATE_UPDATE = NOW() " +
				" WHERE 1=1 " +
				"   AND EHF310100T0_01=? " +
				"   AND EHF310100T0_34=? ";
		
				PreparedStatement pstmt = conn.prepareStatement(insql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
				Statement stmt = conn.createStatement();
				int indexid = 1;
				
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_02());  	//檔案編號
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_03());	//櫃號
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_04());	//產婦姓名
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_05());	//產別
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_06());	//預產期
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_07());	//生產日
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_08());	//訂餐日期
				indexid++;
				p6stmt.setInt(indexid, (Integer)Form.getEHF310100T0_09());	//訂餐天數
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_10());	//送餐日期
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_11());	//醫院
				indexid++;
				p6stmt.setInt(indexid, (Integer)Form.getEHF310100T0_12());	//醫院用餐天數
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_13());	//醫院用餐期間起
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_14());	//醫院用餐期間迄
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_15());	//醫院房號
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_16());	//醫院路線
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_17());	//住宅路線
				indexid++;
				p6stmt.setInt(indexid, (Integer)Form.getEHF310100T0_18());	//住宅用餐天數
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_19());	//住宅用餐期間起
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_20());	//住宅用餐期間迄
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_21());	//行動電話(產婦)
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_22());	//連絡電話(住)
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_23());	//聯絡1
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_24());	//聯絡1
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_25());	//聯絡2
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_26());	//聯絡2
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_27());	//聯絡3
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_28());	//聯絡3
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_29());	//聯絡4
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_30());	//聯絡4
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_31());	//產婦地址
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_32());	//備註1
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_33());	//備註2
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());	//最後修改人員
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_01());	//系統編號
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getCompId());   //公司代碼
				indexid++;
				p6stmt.executeUpdate();
				
				
				stmt.close();
				p6stmt.close();
				pstmt.close();
				
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","儲存失敗，"+ request.getAttribute("ErrMSG"));
			}
			
			//set tabsutil
			Form.setTabsutil_CUS("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","產婦基本資料儲存錯誤!!請重新操作!!");			
	        e.printStackTrace();	             
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);		
	}
	/**
	 * 儲存訂餐明細資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataORD(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataORD");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			EHF310100 ehf310100 = new EHF310100(getLoginUser(request).getCompId());
			if (lc_errors.isEmpty()) {
				
				
				String insql = "" +
				" UPDATE EHF310100T1 SET EHF310100T1_04=?," +
				" EHF310100T1_05=?, USER_UPDATE=?, VERSION = VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF310100T1_01=? AND EHF310100T1_06=? AND EHF310100T1_03=? AND EHF310100T1_02=? ";
		
				PreparedStatement pstmt = conn.prepareStatement(insql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
				Statement stmt = conn.createStatement();
				
				int ucount = 0;
		
				for (int i = 0; i < Form.getEHF310100T1_List().size(); i++) {
					
					EHF310100M0F Form_1 = (EHF310100M0F) Form.getEHF310100T1_List().get(i);
					
					//取得訂餐日期
					String orderDate = (String)Form_1.getEHF310100T1_02();
					//取得當日訂餐數量
					Map orderMap = ehf310100.getOrderMap(Form.getEHF310100T0_01(),orderDate,getLoginUser(request).getCompId());
					//若有早餐資料(A)  檢查欄位是否有變更
					if(!"".equals((String)orderMap.get("EHF310100T1_03_A")) && (String)orderMap.get("EHF310100T1_03_A")!=null){

						
						if( ("A".equals((String)orderMap.get("EHF310100T1_03_A"))?"1":"0").equals((String)Form_1.getEHF310100T1_03_A()) &&
							((String)orderMap.get("EHF310100T1_04_A")).equals((String)Form_1.getEHF310100T1_04_A()) &&
							((String)orderMap.get("EHF310100T1_05_A")).equals((String)Form_1.getEHF310100T1_05_A()) ){
							//代表所有欄位皆未變動，不需更新
							
						}else{
							//代表欄位有變動，更新
							p6stmt.setString(1, (String)Form_1.getEHF310100T1_04_A());  //路線
							p6stmt.setBoolean(2, tools.StringToBoolean((String)Form_1.getEHF310100T1_05_A()));  //素食
							p6stmt.setString(3, getLoginUser(request).getUserName());  //最後修改人員
							p6stmt.setString(4, (String)Form.getEHF310100T0_01());	//系統編號
							p6stmt.setString(5, getLoginUser(request).getCompId());  //公司代碼
							p6stmt.setString(6, Form_1.getEHF310100T1_03_A().toString().equals("1")?"A":"");  //餐別
							p6stmt.setString(7, orderDate);  //日期
							
							p6stmt.executeUpdate();
//							System.out.println("更新"+orderDate+" 早餐明細："+p6stmt.getQueryFromPreparedStatement());
							ucount++;
						}
						
					}
					//若有中餐資料(B)  檢查欄位是否有變更
					if(!"".equals((String)orderMap.get("EHF310100T1_03_B")) && (String)orderMap.get("EHF310100T1_03_B")!=null){
						
						if( ("B".equals((String)orderMap.get("EHF310100T1_03_B"))?"1":"0").equals((String)Form_1.getEHF310100T1_03_B()) &&
							((String)orderMap.get("EHF310100T1_04_B")).equals((String)Form_1.getEHF310100T1_04_B()) &&
							((String)orderMap.get("EHF310100T1_05_B")).equals((String)Form_1.getEHF310100T1_05_B()) ){
							//代表所有欄位皆未變動，不需更新
							
						}else{
							//代表欄位有變動，更新
							p6stmt.setString(1, (String)Form_1.getEHF310100T1_04_B());  //路線
							p6stmt.setBoolean(2, tools.StringToBoolean((String)Form_1.getEHF310100T1_05_B()));  //素食
							p6stmt.setString(3, getLoginUser(request).getUserName());  //最後修改人員
							p6stmt.setString(4, (String)Form.getEHF310100T0_01());	//系統編號
							p6stmt.setString(5, getLoginUser(request).getCompId());  //公司代碼
							p6stmt.setString(6, Form_1.getEHF310100T1_03_B().toString().equals("1")?"B":"");  //餐別
							p6stmt.setString(7, orderDate);  //日期
							
							p6stmt.executeUpdate();
//							System.out.println("更新"+orderDate+" 中餐明細："+p6stmt.getQueryFromPreparedStatement());
							ucount++;
						}
						
					}
					//若有晚餐資料(C)  檢查欄位是否有變更
					if(!"".equals((String)orderMap.get("EHF310100T1_03_C")) && (String)orderMap.get("EHF310100T1_03_C")!=null){
						
						if( ("C".equals((String)orderMap.get("EHF310100T1_03_C"))?"1":"0").equals((String)Form_1.getEHF310100T1_03_C()) &&
							((String)orderMap.get("EHF310100T1_04_C")).equals((String)Form_1.getEHF310100T1_04_C()) &&
							((String)orderMap.get("EHF310100T1_05_C")).equals((String)Form_1.getEHF310100T1_05_C()) ){
							//代表所有欄位皆未變動，不需更新
							
						}else{
							//代表欄位有變動，更新
							p6stmt.setString(1, (String)Form_1.getEHF310100T1_04_C());  //路線
							p6stmt.setBoolean(2, tools.StringToBoolean((String)Form_1.getEHF310100T1_05_C()));  //素食
							p6stmt.setString(3, getLoginUser(request).getUserName());  //最後修改人員
							p6stmt.setString(4, (String)Form.getEHF310100T0_01());	//系統編號
							p6stmt.setString(5, getLoginUser(request).getCompId());  //公司代碼
							p6stmt.setString(6, Form_1.getEHF310100T1_03_C().toString().equals("1")?"C":"");  //餐別
							p6stmt.setString(7, orderDate);  //日期
							
							p6stmt.executeUpdate();
//							System.out.println("更新"+orderDate+" 晚餐明細："+p6stmt.getQueryFromPreparedStatement());
							ucount++;
						}
						
					}
					
				}
				
				stmt.close();
				p6stmt.close();
				pstmt.close();
				ehf310100.close();
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功，明細修改" + ucount + "筆");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				ehf310100.close();
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","儲存失敗，"+ request.getAttribute("ErrMSG"));
				this.generateSelectBox(conn, Form, request);
//				return queryForm(mapping, form, request, response);
			}
			
			//set tabsutil
			Form.setTabsutil_ORD("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","訂餐明細設定儲存錯誤!!請重新操作!!");			
	        e.printStackTrace();	         
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);		
	}
	/**
	 * 儲存訂餐特殊資訊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataSPE(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataSPE");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			if (lc_errors.isEmpty()) {
				
				
				String insql = "" +
				" UPDATE EHF310200T0 SET EHF310200T0_02=?, EHF310200T0_03=? " +
				" WHERE 1=1 " +
				" AND EHF310200T0_01=? ";
		
				PreparedStatement pstmt = conn.prepareStatement(insql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
				Statement stmt = conn.createStatement();
				
				
		
				
				p6stmt.setBoolean(1, tools.StringToBoolean((String)Form.getEHF310200T0_02()));  	//特殊習慣
				p6stmt.setString(2, (String)Form.getEHF310200T0_03());  	//備註
				p6stmt.setString(3, (String)Form.getEHF310100T0_01());	//系統編號
				
				
				p6stmt.executeUpdate();
				
				
				stmt.close();
				p6stmt.close();
				pstmt.close();
				
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","儲存失敗，"+ request.getAttribute("ErrMSG"));
			}
			
			//set tabsutil
			Form.setTabsutil_SPE("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","訂餐特殊資訊儲存錯誤!!請重新操作!!");			
	        e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);		
	}
	
	/**
	 * 儲存電訪紀錄明細資訊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataCALL(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataCALL");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			EHF310100 ehf310100 = new EHF310100(getLoginUser(request).getCompId());
			if (lc_errors.isEmpty()) {
				
				
				String sql = "" +
							 " UPDATE EHF310300T0 SET EHF310300T0_05=?," +
							 " EHF310300T0_06=?, USER_UPDATE=?, VERSION = VERSION+1, DATE_UPDATE=NOW() " +
							 " WHERE 1=1 " +
							 " AND EHF310300T0_01=? " +
							 " AND EHF310300T0_02=? " +
							 " AND EHF310300T0_07=? ";
		
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,sql);
				Statement stmt = conn.createStatement();
				
				int ucount = 0;
		
				for (int i = 0; i < Form.getEHF310300T0_List().size(); i++) {
					
					EHF310100M0F Form_1 = (EHF310100M0F) Form.getEHF310300T0_List().get(i);
					
					
					//取得修改前電訪紀錄
					Map callMap = ehf310100.getCallMap(Form.getEHF310100T0_01(),(String)Form_1.getEHF310300T0_02(),getLoginUser(request).getCompId());
					
					if(((String)callMap.get("EHF310300T0_05")).equals((String)Form_1.getEHF310300T0_05()) &&
					   ((String)callMap.get("EHF310300T0_06")).equals((String)Form_1.getEHF310300T0_06())){
						//代表欄位未變動，不需更新
					}else{
						//代表欄位有變動，須更新
						p6stmt.setString(1, (String)Form_1.getEHF310300T0_05());  //電訪內容
						p6stmt.setString(2, (String)Form_1.getEHF310300T0_06());  //備註
						p6stmt.setString(3, getLoginUser(request).getUserName());  //最後修改人員
						p6stmt.setString(4, (String)Form.getEHF310100T0_01());	//系統編號
						p6stmt.setString(5, (String)Form_1.getEHF310300T0_02());  //順序號碼
						p6stmt.setString(6, getLoginUser(request).getCompId());  //公司代碼
						p6stmt.executeUpdate();
						ucount++;
					}
					
					
					
					
				}
				
				stmt.close();
				p6stmt.close();
				pstmt.close();
				ehf310100.close();
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功，明細修改" + ucount + "筆");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				ehf310100.close();
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","儲存失敗，"+ request.getAttribute("ErrMSG"));
			}
			
			//set tabsutil
			Form.setTabsutil_CALL("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","電訪紀錄明細儲存錯誤!!請重新操作!!");			
	        e.printStackTrace();     
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);		
	}
	
	/**
	 * 儲存贈品資訊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	/*public ActionForward saveDataGIFT(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataGIFT");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			if (lc_errors.isEmpty()) {
				
				
				String insql = "" +
				" UPDATE EHF310500T0 SET EHF310500T0_02=?, EHF310500T0_03=?, EHF310500T0_04=?, " +
				"						 EHF310500T0_05=?, EHF310500T0_06=?, EHF310500T0_07=?, EHF310500T0_08=?, " +
				"						 USER_UPDATE=? , VERSION = VERSION + 1, DATE_UPDATE = NOW() " +
				" WHERE 1=1 " +
				"   AND EHF310500T0_01=? " +
				"   AND EHF310500T0_09=? ";
		
				PreparedStatement pstmt = conn.prepareStatement(insql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
				Statement stmt = conn.createStatement();
				int indexid = 1;
				
				p6stmt.setString(indexid, (String)Form.getEHF310500T0_02());  	//接待人員
				indexid++;
				p6stmt.setBoolean(indexid, tools.StringToBoolean((String)Form.getEHF310500T0_03()));	//是否有贈品
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310500T0_04());	//領取日期
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310500T0_05());	//開單日期
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310500T0_06());	//贈品類別
				indexid++;
				p6stmt.setInt(indexid, (Integer)Form.getEHF310500T0_07());	//份數
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310500T0_08());	//備註
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());	//最後修改人員
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_01());	//系統編號
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getCompId());   //公司代碼
				indexid++;
				p6stmt.executeUpdate();
				
				
				stmt.close();
				p6stmt.close();
				pstmt.close();
				
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","儲存失敗，"+ request.getAttribute("ErrMSG"));
			}
			
			//set tabsutil
			Form.setTabsutil_GIFT("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","贈品資料儲存錯誤!!請重新操作!!");			
	        e.printStackTrace();    
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);		
	}*/
	/**
	 * 儲存付款資訊資訊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataPAY(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataPAY");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			if (lc_errors.isEmpty()) {
				
				
				String insql = "" +
				" UPDATE EHF310400T0 SET EHF310400T0_04=?, " +
				"						 USER_UPDATE=? , VERSION = VERSION + 1, DATE_UPDATE = NOW() " +
				" WHERE 1=1 " +
				"   AND EHF310400T0_01=? " +
				"   AND EHF310400T0_06=? ";
		
				PreparedStatement pstmt = conn.prepareStatement(insql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
				Statement stmt = conn.createStatement();
				int indexid = 1;
				
				
				p6stmt.setInt(indexid, (Integer)Form.getEHF310400T0_04());	//折扣金額
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName());	//最後修改人員
				indexid++;
				p6stmt.setString(indexid, (String)Form.getEHF310100T0_01());	//系統編號
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getCompId());   //公司代碼
				indexid++;
				p6stmt.executeUpdate();
				
				
				stmt.close();
				p6stmt.close();
				pstmt.close();
				
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","儲存失敗，"+ request.getAttribute("ErrMSG"));
			}
			
			//set tabsutil
			Form.setTabsutil_PAY("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","付款資訊儲存錯誤!!請重新操作!!");			
	        e.printStackTrace();     
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);		
	}
	/**
	 * 儲存付款明細資訊
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward saveDataPAYDETAIL(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "saveDataPAYDETAIL");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			Map payMap = new HashMap();
			EHF310100 ehf310100 = new EHF310100(getLoginUser(request).getCompId());
			if (lc_errors.isEmpty()) {
				
				
				String insql = "" +
				" UPDATE EHF310400T1 SET EHF310400T1_05=?,  EHF310400T1_06=?, EHF310400T1_07=?, EHF310400T1_08=?, EHF310400T1_09=?, EHF310400T1_10=?, " +
				"						 EHF310400T1_11=?,  EHF310400T1_12=?, USER_UPDATE=?, VERSION = VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE 1=1 " +
				" AND EHF310400T1_01=?  AND EHF310400T1_02=? AND EHF310400T1_13='0' AND EHF310400T1_14='0' AND EHF310400T1_15=? ";
		
				PreparedStatement pstmt = conn.prepareStatement(insql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null,insql);
				Statement stmt = conn.createStatement();
				
				int ucount = 0;
		
				for (int i = 0; i < Form.getEHF310400T1_List().size(); i++) {
					
					EHF310100M0F Form_1 = (EHF310100M0F) Form.getEHF310400T1_List().get(i);
					
					
					//取得修改前電訪紀錄
					payMap = ehf310100.getPayDetailMap(Form.getEHF310100T0_01(),(Integer)Form_1.getEHF310400T1_02(),getLoginUser(request).getCompId());
					
					if(((String)payMap.get("EHF310400T1_05")).equals((String)Form_1.getEHF310400T1_05()) &&
					   ((String)payMap.get("EHF310400T1_06")).equals((String)Form_1.getEHF310400T1_06()) &&
					   ((String)payMap.get("EHF310400T1_07")).equals((String)Form_1.getEHF310400T1_07()) &&
					   ((String)payMap.get("EHF310400T1_08")==null?"":(String)payMap.get("EHF310400T1_08")).equals((String)Form_1.getEHF310400T1_08()==null?"":(String)Form_1.getEHF310400T1_08()) &&
					   ((Integer)payMap.get("EHF310400T1_09")).equals((Integer)Form_1.getEHF310400T1_09()) &&
					   ((String)payMap.get("EHF310400T1_10")==null?"":(String)payMap.get("EHF310400T1_10")).equals((String)Form_1.getEHF310400T1_10()==null?"":(String)Form_1.getEHF310400T1_10()) &&
					   ((Integer)payMap.get("EHF310400T1_11")).equals((Integer)Form_1.getEHF310400T1_11()) &&
					   ((String)payMap.get("EHF310400T1_12")==null?"":(String)payMap.get("EHF310400T1_12")).equals((String)Form_1.getEHF310400T1_12()==null?"":(String)Form_1.getEHF310400T1_12())){
						//代表欄位未變動，不需更新
						
					}else{
						//代表欄位有變動，須更新
						p6stmt.setString(1, (String)Form_1.getEHF310400T1_05());  //經手人員
						p6stmt.setString(2, (String)Form_1.getEHF310400T1_06());  //付款方式
						p6stmt.setString(3, (String)Form_1.getEHF310400T1_07());  //付款類別
						p6stmt.setString(4, (String)Form_1.getEHF310400T1_08());  //預計收款日
						p6stmt.setInt(5, (Integer)Form_1.getEHF310400T1_09());  //預計金額
						p6stmt.setString(6, (String)Form_1.getEHF310400T1_10());  //實際收款日
						p6stmt.setInt(7, (Integer)Form_1.getEHF310400T1_11());  //實際金額
						p6stmt.setString(8, (String)Form_1.getEHF310400T1_12());  //備註
						p6stmt.setString(9, getLoginUser(request).getUserName());  //最後修改人員
						p6stmt.setString(10, (String)Form.getEHF310100T0_01());	//系統編號
						p6stmt.setInt(11, (Integer)Form_1.getEHF310400T1_02());  //順序號碼
						p6stmt.setString(12, getLoginUser(request).getCompId());  //公司代碼
						p6stmt.executeUpdate();
						ucount++;
					}
					payMap.clear();
				}
				
				stmt.close();
				p6stmt.close();
				pstmt.close();
				ehf310100.close();
				conn.commit();
				
				request.setAttribute("MSG_EDIT", "儲存成功，明細修改" + ucount + "筆");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				ehf310100.close();
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","儲存失敗，"+ request.getAttribute("ErrMSG"));
			}
			
			//set tabsutil
			Form.setTabsutil_PAY("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","付款明細儲存錯誤!!請重新操作!!");			
	        e.printStackTrace();     
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);		
	}
	/**
	 * 試算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward startCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "startCount");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			String comp_id = getLoginUser(request).getCompId();
			EHF310100 ehf310100 = new EHF310100(comp_id);
			Map orderData = new HashMap();
			int countA=0;
			int countB=0;
			int countC=0;
			if (lc_errors.isEmpty()) {
				
				//取得訂餐數量
				Map orderMap = ehf310100.getOrderMap(Form.getEHF310100T0_01(),"",comp_id);
				countA = (Integer)orderMap.get("EHF310400T2_03_A");
				countB = (Integer)orderMap.get("EHF310400T2_03_B");
				countC = (Integer)orderMap.get("EHF310400T2_03_C");
				
				//取得訂餐天數與開始送餐日期
				Map dateInf = ehf310100.getOrderDays(Form.getEHF310100T0_01(),comp_id);
				int days = (Integer)dateInf.get("days");
				
				if(countA<countB && countA<countC){
					//代表A餐數量最少
					Form.setEHF310400T2_03_All(countA);//套餐數量
					Form.setEHF310400T2_03_A(0);//A餐數量
					Form.setEHF310400T2_03_B(countB-countA);//B餐數量
					Form.setEHF310400T2_03_C(countC-countA);//C餐數量
					
					Form.setEHF310400T2_03_A_Pay(0);//A餐價格
					Form.setEHF310400T2_03_B_Pay((countB-countA)*this.getpriceMap(conn,"P02",comp_id));//B餐價格
					Form.setEHF310400T2_03_C_Pay((countC-countA)*this.getpriceMap(conn,"P03",comp_id));//C餐價格
					if(days>=16){
						//訂餐滿16天
						Form.setEHF310400T2_03_All_Pay(countA*this.getpriceMap(conn,"P05",comp_id));//套餐價格(16天以上)
					}else if(days<16){
						//訂餐未滿16天
						Form.setEHF310400T2_03_All_Pay(countA*this.getpriceMap(conn,"P04",comp_id));//套餐價格(16天以下)
					}
					
				}else if(countB<countA && countB<countC){
					//代表B餐數量最少
					Form.setEHF310400T2_03_All(countB);//套餐數量
					Form.setEHF310400T2_03_A(countA-countB);//A餐數量
					Form.setEHF310400T2_03_B(0);//B餐數量
					Form.setEHF310400T2_03_C(countC-countB);//C餐數量
					
					Form.setEHF310400T2_03_A_Pay((countA-countB)*this.getpriceMap(conn,"P01",comp_id));//A餐價格
					Form.setEHF310400T2_03_B_Pay(0);//B餐價格
					Form.setEHF310400T2_03_C_Pay((countC-countB)*this.getpriceMap(conn,"P03",comp_id));//C餐價格
					if(days>=16){
						//訂餐滿16天
						Form.setEHF310400T2_03_All_Pay(countB*this.getpriceMap(conn,"P05",comp_id));//套餐價格(16天以上)
					}else if(days<16){
						//訂餐未滿16天
						Form.setEHF310400T2_03_All_Pay(countB*this.getpriceMap(conn,"P04",comp_id));//套餐價格(16天以下)
					}
				}else if(countC<countA && countC<countB){
					//代表C餐數量最少
					Form.setEHF310400T2_03_All(countC);//套餐數量
					Form.setEHF310400T2_03_A(countA-countC);//A餐數量
					Form.setEHF310400T2_03_B(countB-countC);//B餐數量
					Form.setEHF310400T2_03_C(0);//C餐數量

					Form.setEHF310400T2_03_A_Pay((countA-countC)*this.getpriceMap(conn,"P01",comp_id));//A餐價格
					Form.setEHF310400T2_03_B_Pay((countB-countC)*this.getpriceMap(conn,"P02",comp_id));//B餐價格
					Form.setEHF310400T2_03_C_Pay(0);//C餐價格
					if(days>=16){
						//訂餐滿16天
						Form.setEHF310400T2_03_All_Pay(countC*this.getpriceMap(conn,"P05",comp_id));//套餐價格(16天以上)
					}else if(days<16){
						//訂餐未滿16天
						Form.setEHF310400T2_03_All_Pay(countC*this.getpriceMap(conn,"P04",comp_id));//套餐價格(16天以下)
					}
				}else{
					if(countA==countB && countB==countC){
						//代表ABC餐數量皆一樣
						Form.setEHF310400T2_03_All(countA);//套餐數量
						Form.setEHF310400T2_03_A(0);//A餐數量
						Form.setEHF310400T2_03_B(0);//B餐數量
						Form.setEHF310400T2_03_C(0);//C餐數量
						
						Form.setEHF310400T2_03_A_Pay(0);//A餐價格
						Form.setEHF310400T2_03_B_Pay(0);//B餐價格
						Form.setEHF310400T2_03_C_Pay(0);//C餐價格
						
						if(days>=16){
							//訂餐滿16天
							Form.setEHF310400T2_03_All_Pay(countA*this.getpriceMap(conn,"P05",comp_id));//套餐價格(16天以上)
						}else if(days<16){
							//訂餐未滿16天
							Form.setEHF310400T2_03_All_Pay(countA*this.getpriceMap(conn,"P04",comp_id));//套餐價格(16天以下)
						}
					}else{
						//代表ABC餐數量不一樣，且只剩下一種餐別
						if(countA==countB){
							//代表只剩下C
							Form.setEHF310400T2_03_All(countA);//套餐數量
							Form.setEHF310400T2_03_A(0);//A餐數量
							Form.setEHF310400T2_03_B(0);//B餐數量
							Form.setEHF310400T2_03_C(countC-countB);//C餐數量
							
							Form.setEHF310400T2_03_A_Pay(0);//A餐價格
							Form.setEHF310400T2_03_B_Pay(0);//B餐價格
							Form.setEHF310400T2_03_C_Pay((countC-countB)*this.getpriceMap(conn,"P03",comp_id));//C餐價格
							
							if(days>=16){
								//訂餐滿16天
								Form.setEHF310400T2_03_All_Pay(countA*this.getpriceMap(conn,"P05",comp_id));//套餐價格(16天以上)
							}else if(days<16){
								//訂餐未滿16天
								Form.setEHF310400T2_03_All_Pay(countA*this.getpriceMap(conn,"P04",comp_id));//套餐價格(16天以下)
							}
						}else if(countB==countC){
							//代表只剩下A
							Form.setEHF310400T2_03_All(countB);//套餐數量
							Form.setEHF310400T2_03_A(countA-countB);//A餐數量
							Form.setEHF310400T2_03_B(0);//B餐數量
							Form.setEHF310400T2_03_C(0);//C餐數量
							
							Form.setEHF310400T2_03_A_Pay((countA-countB)*this.getpriceMap(conn,"P01",comp_id));//A餐價格
							Form.setEHF310400T2_03_B_Pay(0);//B餐價格
							Form.setEHF310400T2_03_C_Pay(0);//C餐價格
							
							if(days>=16){
								//訂餐滿16天
								Form.setEHF310400T2_03_All_Pay(countB*this.getpriceMap(conn,"P05",comp_id));//套餐價格(16天以上)
							}else if(days<16){
								//訂餐未滿16天
								Form.setEHF310400T2_03_All_Pay(countB*this.getpriceMap(conn,"P04",comp_id));//套餐價格(16天以下)
							}
						}else if(countA==countC){
							//代表只剩下B
							Form.setEHF310400T2_03_All(countA);//套餐數量
							Form.setEHF310400T2_03_A(0);//A餐數量
							Form.setEHF310400T2_03_B(countB-countA);//B餐數量
							Form.setEHF310400T2_03_C(0);//C餐數量
							
							Form.setEHF310400T2_03_A_Pay(0);//A餐價格
							Form.setEHF310400T2_03_B_Pay((countB-countA)*this.getpriceMap(conn,"P02",comp_id));//B餐價格
							Form.setEHF310400T2_03_C_Pay(0);//C餐價格
							
							if(days>=16){
								//訂餐滿16天
								Form.setEHF310400T2_03_All_Pay(countA*this.getpriceMap(conn,"P05",comp_id));//套餐價格(16天以上)
							}else if(days<16){
								//訂餐未滿16天
								Form.setEHF310400T2_03_All_Pay(countA*this.getpriceMap(conn,"P04",comp_id));//套餐價格(16天以下)
							}
						}
					}
					
				}
				
				orderData.put("EHF310100T0_01", (String)Form.getEHF310100T0_01());
				orderData.put("days",days);
				orderData.put("EHF310400T2_03_All", (Integer)Form.getEHF310400T2_03_All());
				orderData.put("EHF310400T2_03_A", (Integer)Form.getEHF310400T2_03_A());
				orderData.put("EHF310400T2_03_B", (Integer)Form.getEHF310400T2_03_B());
				orderData.put("EHF310400T2_03_C", (Integer)Form.getEHF310400T2_03_C());
				orderData.put("EHF310400T2_03_All_Pay", (Integer)Form.getEHF310400T2_03_All_Pay());
				orderData.put("EHF310400T2_03_A_Pay", (Integer)Form.getEHF310400T2_03_A_Pay());
				orderData.put("EHF310400T2_03_B_Pay", (Integer)Form.getEHF310400T2_03_B_Pay());
				orderData.put("EHF310400T2_03_C_Pay", (Integer)Form.getEHF310400T2_03_C_Pay());
				orderData.put("USER_NAME", getLoginUser(request).getUserName().toString());
				
				
				ehf310100.editEHF310400T0(comp_id,orderData);
				ehf310100.editPayInf(comp_id,orderData);
				
				ehf310100.close();
				request.setAttribute("MSG_EDIT", "試算成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
			}else{
				ehf310100.close();
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","試算失敗，"+ request.getAttribute("ErrMSG"));
			}
			//set tabsutil
			Form.setTabsutil_PAY("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","付款資訊試算錯誤!!請重新操作!!");			
	        e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);
	}
	
	/**
	 * 結算
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward endCount(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "endCount");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			String comp_id = getLoginUser(request).getCompId();
			EHF310100 ehf310100 = new EHF310100(comp_id);
			Map queryCondMap = new HashMap();
			Map orderData = new HashMap();
			EHF310100M0F bean = null;
			int total_price=0;//定價
			int discount=0;//折扣金額
			int final_price=0;//實際金額
			int paied_goad=0;//已付金額
			int unpay_goad=0;//未付金額
			if (lc_errors.isEmpty()) {
				queryCondMap.put("EHF310100T0_01", (String)Form.getEHF310100T0_01());
				queryCondMap.put("COMP_ID", comp_id);
				queryCondMap.put("confirm", "yes");
				//從資料庫取得定價與折扣金額
				Map pay1Map = ehf310100.queryPay1Data(queryCondMap);
				
				total_price = (Integer)pay1Map.get("EHF310400T0_03");
				discount = (Integer)pay1Map.get("EHF310400T0_04");
				
				//取得已付金額 →從付款明細找已確認並且未刪除之資料
				List pay3List = ehf310100.queryPay3Data(queryCondMap);
				Iterator it = pay3List.iterator();
				Map tempMap = null;
				
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					if("04".equals((String)tempMap.get("EHF310400T1_07"))){
						//代表付款類別為退款  →減項
						paied_goad = paied_goad - (Integer)tempMap.get("EHF310400T1_11");
					}else{
						//代表付款類別非退款  →加項
						paied_goad = paied_goad + (Integer)tempMap.get("EHF310400T1_11");
					}
				}
				
				orderData.put("EHF310100T0_01", Form.getEHF310100T0_01());//系統編號
				orderData.put("days", Form.getEHF310400T0_02());//訂餐天數
				orderData.put("EHF310400T0_03", total_price);//定價
				orderData.put("EHF310400T0_04", discount);//折扣金額
				orderData.put("EHF310400T0_05", paied_goad);//已付金額
				
				ehf310100.editEHF310400T0(comp_id,orderData);
				
				request.setAttribute("MSG_EDIT", "結算成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");

				ehf310100.close();
			}else{
				ehf310100.close();
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","結算失敗，"+ request.getAttribute("ErrMSG"));
			}
			//set tabsutil
			Form.setTabsutil_PAY("yes");
			
		}catch(Exception e){
	        // TODO Auto-generated catch block
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);			
			request.setAttribute("MSG_EDIT","付款資訊結算錯誤!!請重新操作!!");			
	        e.printStackTrace();      
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return queryForm(mapping, form, request, response);
	}
	/**
	 * 確認/回復
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward confirmRecovery(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {

		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "confirmRecovery");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			String comp_id = getLoginUser(request).getCompId();
			EHF310100 ehf310100 = new EHF310100(comp_id);
			Map queryCondMap = new HashMap();

			if (lc_errors.isEmpty()) {
				queryCondMap.put("EHF310100T0_01", Form.getEHF310100T0_01());
				queryCondMap.put("EHF310400T1_02", Form.getEHF310400T1_02());
				queryCondMap.put("COMP_ID", comp_id);
				queryCondMap.put("USER_NAME", getLoginUser(request).getUserName());
				
				//判斷當前狀態
				if("1".equals(Form.getEHF310400T1_13())){
					//代表當前為已確認  →回復資料
					//執行回復
					ehf310100.confirmRecovery(queryCondMap,"recovery");
				}else if("0".equals(Form.getEHF310400T1_13())){
					//代表當前為未確認  →確認資料
					//執行確認
					ehf310100.confirmRecovery(queryCondMap,"confirm");
				}
				
				request.setAttribute("MSG_EDIT", "確認/回復 成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				ehf310100.close();
			}else{
				ehf310100.close();
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","確認/回復 失敗，"+ request.getAttribute("ErrMSG"));
			}
			Form.setTabsutil_PAY("yes");
			
		}catch(Exception e){
			System.out.println("確認/回復 時錯誤"+e);
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return queryForm(mapping, form, request, response);
	}
	
	/**
	 * 刪除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {

		EHF310100M0F Form = (EHF310100M0F) form;
		Connection conn = null;
		try{
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "confirmRecovery");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			String comp_id = getLoginUser(request).getCompId();
			EHF310100 ehf310100 = new EHF310100(comp_id);
			Map queryCondMap = new HashMap();

			if (lc_errors.isEmpty()) {
				queryCondMap.put("EHF310100T0_01", Form.getEHF310100T0_01());
				queryCondMap.put("EHF310400T1_02", Form.getEHF310400T1_02());
				queryCondMap.put("COMP_ID", comp_id);
				queryCondMap.put("USER_NAME", getLoginUser(request).getUserName());
				
				//執行刪除(作廢)
				ehf310100.remove(queryCondMap);
				
				request.setAttribute("MSG_EDIT", "刪除付款明細成功");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				ehf310100.close();
			}else{
				ehf310100.close();
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG_EDIT","刪除付款明細失敗，"+ request.getAttribute("ErrMSG"));
			}
			Form.setTabsutil_PAY("yes");
			
		}catch(Exception e){
			System.out.println("刪除時錯誤"+e);
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return queryForm(mapping, form, request, response);
	}
	
	
	/**
	 * 列印用餐確認表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//HR_TOOLS hr_tools = HR_TOOLS.getInstance();
		
//		this.getSelectOption(request);
		EHF310100M0F Form = (EHF310100M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		EHF310100 ehf310100 = new EHF310100(comp_id);
		request.setAttribute("Form1Datas",Form);
		Connection conn = null;
		ArrayList list = new ArrayList();
		ActionErrors lc_errors = new ActionErrors();
		String firstDate = "";
		String secondDate = "";
		String thirdDate = "";
		String notEat="";
		String spe="";
		
		//建立資料庫連線
    	if (conn == null) {
    		try {
    			conn = tools.getConnection("SPOS");
    		} catch (SQLException e2) {
    			e2.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		}
    	BA_Vaildate ve = BA_Vaildate.getInstance();
		

		if (!lc_errors.isEmpty()) {
			saveErrors(request, lc_errors);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			this.generateSelectBox(conn, Form, request);
			Form.setTabsutil_ORD("yes");
			return queryForm(mapping, form, request, response);	
		}
		Calendar cal = null;
        String nowtime = "";
       
		try {

			Map tempMap_EHF310100T1_List = null;
			Map tempMap_EHF310100T2_List = null;
			Map tempMap_EHF310200T1_List = null;
			Map tempMap_EHF310200T3_List = null;
			//建立細項新增資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			

			//設定使用者資訊
			dataMap.put("COMP_ID", comp_id);
			dataMap.put("USER_NAME", getLoginUser(request).getUserName());
			//取得產婦基本資料
			Map CusMap= this.getCusInf(conn,dataMap);	
			
			//產別
			Map BirthTypeMap = ehf310100.getOptions("BirthType", comp_id);
			//醫院
			Map hospitalMap = ehf310100.getHospital(comp_id);
			//取得訂餐資訊
			List OrderList= ehf310100.getOrderInf(dataMap);	
			
			//計算電訪日期
			int days = 0;
			String date = (String)CusMap.get("EHF310100T0_10");//開始送餐日期
			if(!"".equals((String)CusMap.get("EHF310100T0_09")) && (String)CusMap.get("EHF310100T0_09") != null){
				days = Integer.parseInt((String)CusMap.get("EHF310100T0_09"));//訂餐天數
			}
			
			//判斷是否已填送餐日期資訊
			if(date!=null && !"".equals(date)){
				//開始送餐日期
				Calendar cal_start = tools.covertStringToCalendar(date);
				Calendar cal_first = (Calendar)cal_start.clone();
				Calendar cal_second = (Calendar)cal_start.clone();
				Calendar cal_third = (Calendar)cal_start.clone();
				
				//計算預計電訪日期
				if(days>=30){
					cal_first.add(Calendar.DAY_OF_MONTH, 4);
					cal_second.add(Calendar.DAY_OF_MONTH, 14);
					cal_third.add(Calendar.DAY_OF_MONTH, 24);
					
					firstDate = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
					secondDate = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");
					thirdDate = tools.covertDateToString(cal_third.getTime(), "yyyy/MM/dd");
					
				}else if(days<30 && days>10){
					cal_first.add(Calendar.DAY_OF_MONTH, 3);
					cal_second.add(Calendar.DAY_OF_MONTH, 8);
					cal_third.add(Calendar.DAY_OF_MONTH, 14);
					
					firstDate = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
					secondDate = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");
					thirdDate = tools.covertDateToString(cal_third.getTime(), "yyyy/MM/dd");
					
				}else if(days<=10 && days>0 ){
					cal_first.add(Calendar.DAY_OF_MONTH, 2);
					cal_second.add(Calendar.DAY_OF_MONTH, 7);
					
					firstDate = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
					secondDate = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");
					
				}else{
					dataMap.put("firstDate", "未設定訂餐天數");
					dataMap.put("secondDate", "未設定訂餐天數");
					dataMap.put("thirdDate", "未設定訂餐天數");
				}
				dataMap.put("firstDate", firstDate);
				dataMap.put("secondDate", secondDate);
				dataMap.put("thirdDate", thirdDate);
				
			}else{
				dataMap.put("firstDate", "未設定送餐日期");
				dataMap.put("secondDate", "未設定送餐日期");
				dataMap.put("thirdDate", "未設定送餐日期");
			}

			//食物代碼
			Map foodtypeMap = ehf310100.getFoodType(comp_id);
			Map fooddetailMap = ehf310100.getFoodDetail(comp_id);
			//取得不吃食物
			List dontEatList= ehf310100.getDontEatInf(dataMap);	
			Iterator it_EHF310200T1_List = dontEatList.iterator();
			int count = 1;
			while(it_EHF310200T1_List.hasNext()){
				tempMap_EHF310200T1_List = (Map) it_EHF310200T1_List.next();
				if((Integer)tempMap_EHF310200T1_List.get("EHF310200T1_03").toString().split("/").length<2){
					notEat+=foodtypeMap.get(tempMap_EHF310200T1_List.get("EHF310200T1_03").toString().split("/")[0])+"/全部    ";
					if(count!=0 &&count%2==0){
						notEat+="\r\n";
					}
				}else{
					notEat+=foodtypeMap.get(tempMap_EHF310200T1_List.get("EHF310200T1_03").toString().split("/")[0])+"/"+
 							fooddetailMap.get(tempMap_EHF310200T1_List.get("EHF310200T1_03").toString().split("/")[1])+"  ";
					if(count!=0 &&count%2==0){
						notEat+="\r\n";
					}
				}
				count++;
			}
			
			//取得特殊需求
			List speList= ehf310100.getspeInf(dataMap);	
			Iterator it_EHF310200T3_List = speList.iterator();
			int count2=1;
			while(it_EHF310200T3_List.hasNext()){
				
				tempMap_EHF310200T3_List = (Map) it_EHF310200T3_List.next();
				
				spe+=tempMap_EHF310200T3_List.get("EMS_CategoryT1_05")+"  ";
				if(count2!=0 &&count2%3==0){
					spe+="\r\n";
				}
				count2++;
			}
			//取得養生飲品
			List drinkList= ehf310100.getDrinkInf(dataMap);	
			
			//取得實際金額
			Map PayMap= this.getPayInf(conn,dataMap);	
			//取得付款明細
			Map PayDetailMap= this.getPayDetailInf(conn,dataMap);
			
			//取得電訪紀錄
			Map CallMap= ehf310100.getCallInf(dataMap);			
			
			String path = getServlet().getServletContext().getRealPath("");
			
			EX330100R0F ef = new EX330100R0F(conn,path,getLoginUser(request).getEmployeeID(),request);

			
			ef.setHeadValue( 0,  1, "A", "用餐確認表", false, ""); 		//報表標題 
			ef.setHeadValue( 1,  3, "C", (String)CusMap.get("EHF310100T0_02")==null?"":(String)CusMap.get("EHF310100T0_02"), false, "");		//檔案編號
			ef.setHeadValue( 2,  4, "C", (String)CusMap.get("EHF310100T0_03")==null?"":(String)CusMap.get("EHF310100T0_03"), false, "");		//櫃號
			ef.setHeadValue( 3,  5, "C", (String)CusMap.get("EHF310100T0_04")==null?"":(String)CusMap.get("EHF310100T0_04"), false, ""); 		//姓名
			if(!"".equals((String)CusMap.get("EHF310100T0_05")) && (String)CusMap.get("EHF310100T0_05")!=null){
				ef.setHeadValue( 4,  7, "C", (String)BirthTypeMap.get((String)CusMap.get("EHF310100T0_05")), false, ""); 		//產別
			}else{
				ef.setHeadValue( 4,  7, "C", "", false, ""); 		//產別
			}
			
			ef.setHeadValue( 5,  8, "C", (String)CusMap.get("EHF310100T0_07")==null?"":(String)CusMap.get("EHF310100T0_07"), false, ""); 		//生產日
			ef.setHeadValue( 6,  9, "C", (String)CusMap.get("EHF310100T0_09")==null?"":(String)CusMap.get("EHF310100T0_09"), false, ""); 		//訂餐天數
			
			if(!"".equals((String)CusMap.get("EHF310100T0_11")) && (String)CusMap.get("EHF310100T0_11")!=null){
				ef.setHeadValue( 7, 10, "C", (String)hospitalMap.get((String)CusMap.get("EHF310100T0_11")), false, "");			//醫院
			}else{
				ef.setHeadValue( 7, 10, "C", "", false, "");			//醫院
			}
			
			
			ef.setHeadValue( 8,  7, "V", (String)CusMap.get("EHF310100T0_22")==null?"":(String)CusMap.get("EHF310100T0_22"), false, "");		//電話(住)
			ef.setHeadValue( 9,  7, "AB",(String)CusMap.get("EHF310100T0_21")==null?"":(String)CusMap.get("EHF310100T0_21"), false, "");		//行動電話(產婦)
			ef.setHeadValue(10,  8, "V", (String)CusMap.get("EHF310100T0_31")==null?"":(String)CusMap.get("EHF310100T0_31"), false, "");		//住址
			
			ef.setHeadValue(11, 4, "E", notEat, false, ""); 			//不吃食物
			ef.setHeadValue(12, 8, "E", spe,false, "");					//特殊需求(加量)
			ef.setHeadValue(13, 4, "J", firstDate, false, "");			//電聯(一)
			ef.setHeadValue(14, 6, "J", secondDate, false, ""); 		//電聯(二)
			ef.setHeadValue(15, 8, "J", thirdDate, false, ""); 			//電聯(三)
			ef.setHeadValue(16, 4, "V", (String)CallMap.get("firstDate")==null?"":(String)CallMap.get("firstDate"), false, ""); 		//吃餐狀況+通知頭款(一)
			ef.setHeadValue(17, 5, "V", (String)CallMap.get("secondDate")==null?"":(String)CallMap.get("secondDate"), false, ""); 		//吃餐狀況+通知頭款(二)
			ef.setHeadValue(18, 6, "V", (String)CallMap.get("thirdDate")==null?"":(String)CallMap.get("thirdDate"), false, "");			//吃餐狀況+通知頭款(三)
			
			
			ef.setHeadValue(19, 10, "J", String.valueOf(Integer.parseInt((String)PayMap.get("EHF310400T0_03"))-Integer.parseInt((String)PayMap.get("EHF310400T0_04"))), false, ""); 	//總金額 
			ef.setHeadValue(20, 10, "M", String.valueOf((Integer)PayDetailMap.get("deposit")),false, "");		//訂金
			ef.setHeadValue(21, 10, "P", String.valueOf((Integer)PayDetailMap.get("first")), false, "");		//頭款
			ef.setHeadValue(22, 10, "S", String.valueOf((Integer)PayDetailMap.get("last")), false, ""); 		//尾款
			ef.setHeadValue(23, 10, "V", String.valueOf((Integer)PayDetailMap.get("moreLast")), false, ""); 	//續訂尾款
			
			
			String culum[]={"M","P","S","M","P","S","M","P"};
			Iterator it_EHF310100T2_List = drinkList.iterator();
			String type = "";
			String type1 = "";
			String type2 = "";
			String type3 = "";
			String type4 = "";
			String type5 = "";
			String type6 = "";
			String type7 = "";
			while(it_EHF310100T2_List.hasNext()){
				
				tempMap_EHF310100T2_List = (Map) it_EHF310100T2_List.next();
				if(tempMap_EHF310100T2_List.get("EHF310100T2_03").toString().equals(tempMap_EHF310100T2_List.get("EHF310100T2_04").toString())){
					type = (String)tempMap_EHF310100T2_List.get("EHF310100T2_03");
				}else{
					type = tempMap_EHF310100T2_List.get("EHF310100T2_03")+"~"+tempMap_EHF310100T2_List.get("EHF310100T2_04");
				}
				
				if("01".equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_06"))){
					//杜仲水
					type1+=type+",";
				}else if("02".equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_06"))){
					//生化湯
					type2+=type+",";
				}else if("03".equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_06"))){
					//通乳水
					type3+=type+",";
				}else if("04".equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_06"))){
					//麥芽水
					type4+=type+",";
				}else if("05".equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_06"))){
					//韭菜
					type5+=type+",";
				}else if("06".equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_06"))){
					//其他
					type6+=type+",";
				}else if("07".equals((String)tempMap_EHF310100T2_List.get("EHF310100T2_06"))){
					//杜仲粉
					type7+=type+",";
				}
			}
			
			ef.setHeadValue(24, 4, "M", type7, false, "");		//養生飲品(一) 杜仲粉
			ef.setHeadValue(25, 4, "P", type2, false, "");		//養生飲品(二) 生化湯
			ef.setHeadValue(26, 4, "S", type3, false, "");		//養生飲品(三) 通乳水
			
			ef.setHeadValue(27, 6, "M", type1, false, "");		//養生飲品(四) 杜仲水
			ef.setHeadValue(28, 6, "P", type4, false, "");		//養生飲品(五) 麥芽水
			ef.setHeadValue(29, 6, "S", type5, false, "");		//養生飲品(六) 韭菜
			
			ef.setHeadValue(30, 8, "M", type6, false, "");		//養生飲品(七) 其他
			ef.next();//下一筆
			
			Iterator it_EHF310100T1_List = OrderList.iterator();
			int count5=1;//計算每一列印到第幾天的資料
			int count6=0;
			int count7=0;
			int count8=0;//計算明細資料印到第幾列
			String culum1[]={"A","F","K","P","U","AA"};
			Map menuMap = new HashMap();
			menuMap.put("A", "早餐");
			menuMap.put("B", "中餐");
			menuMap.put("C", "晚餐");
			String date_before = "";
			String ord = "";
			boolean dis_flag = false;
			while(it_EHF310100T1_List.hasNext()){
				
				tempMap_EHF310100T1_List = (Map) it_EHF310100T1_List.next();
				
				if(date_before.equals((String)tempMap_EHF310100T1_List.get("EHF310100T1_02"))){
					//代表此次列印資料於前次日期相同，為同一天的資料(因為一天會有1~3筆資料)，故count回扣1。
					count5--;
					dis_flag = true;
				}else{
					ord="";
					dis_flag = false;
				}
				if(count5>6){
					//代表每列印到超過第六筆資料，須重製count5。並將count8+1
					count5=1;
					count8++;
				}
				
				ord += menuMap.get((String)tempMap_EHF310100T1_List.get("EHF310100T1_03"));
				if(!"C".equals((String)tempMap_EHF310100T1_List.get("EHF310100T1_03"))){
					ord+="\r\n";
				}
				ef.setValue(11+(count8*4), culum1[(count5-1)], (String)tempMap_EHF310100T1_List.get("EHF310100T1_02"),false); 
				ef.setValue(12+(count8*4), culum1[(count5-1)], ord,false); 
				
				if(count5==1 && !dis_flag){
					count6++;
					if(count6>=6){
						
						ef.CopyCellFormat(31+count7 , "A", 27, "A");// 複製格式
						ef.CopyCellFormat(31+count7 , "B", 27, "B");// 複製格式
						ef.CopyCellFormat(31+count7 , "C", 27, "C");// 複製格式
						ef.CopyCellFormat(31+count7 , "D", 27, "D");// 複製格式
						ef.CopyCellFormat(31+count7 , "E", 27, "E");// 複製格式
						
						ef.CopyCellFormat(32+count7 , "A", 28, "A");// 複製格式
						ef.CopyCellFormat(32+count7 , "B", 28, "B");// 複製格式
						ef.CopyCellFormat(32+count7 , "C", 28, "C");// 複製格式
						ef.CopyCellFormat(32+count7 , "D", 28, "D");// 複製格式
						ef.CopyCellFormat(32+count7 , "E", 28, "E");// 複製格式
						
						ef.CopyCellFormat(33+count7 , "A", 29, "A");// 複製格式
						ef.CopyCellFormat(33+count7 , "B", 29, "B");// 複製格式
						ef.CopyCellFormat(33+count7 , "C", 29, "C");// 複製格式
						ef.CopyCellFormat(33+count7 , "D", 29, "D");// 複製格式
						ef.CopyCellFormat(33+count7 , "E", 29, "E");// 複製格式
						
						ef.CopyCellFormat(34+count7 , "A", 30, "A");// 複製格式
						ef.CopyCellFormat(34+count7 , "B", 30, "B");// 複製格式
						ef.CopyCellFormat(34+count7 , "C", 30, "C");// 複製格式
						ef.CopyCellFormat(34+count7 , "D", 30, "D");// 複製格式
						ef.CopyCellFormat(34+count7 , "E", 30, "E");// 複製格式
						
						
						ef.CopyCellFormat(31+count7 , "F", 27, "F");// 複製格式
						ef.CopyCellFormat(31+count7 , "G", 27, "G");// 複製格式
						ef.CopyCellFormat(31+count7 , "H", 27, "H");// 複製格式
						ef.CopyCellFormat(31+count7 , "I", 27, "I");// 複製格式
						ef.CopyCellFormat(31+count7 , "J", 27, "J");// 複製格式
						
						ef.CopyCellFormat(32+count7 , "F", 28, "F");// 複製格式
						ef.CopyCellFormat(32+count7 , "G", 28, "G");// 複製格式
						ef.CopyCellFormat(32+count7 , "H", 28, "H");// 複製格式
						ef.CopyCellFormat(32+count7 , "I", 28, "I");// 複製格式
						ef.CopyCellFormat(32+count7 , "J", 28, "J");// 複製格式
						
						ef.CopyCellFormat(33+count7 , "F", 29, "F");// 複製格式
						ef.CopyCellFormat(33+count7 , "G", 29, "G");// 複製格式
						ef.CopyCellFormat(33+count7 , "H", 29, "H");// 複製格式
						ef.CopyCellFormat(33+count7 , "I", 29, "I");// 複製格式
						ef.CopyCellFormat(33+count7 , "J", 29, "J");// 複製格式
						
						ef.CopyCellFormat(34+count7 , "F", 30, "F");// 複製格式
						ef.CopyCellFormat(34+count7 , "G", 30, "G");// 複製格式
						ef.CopyCellFormat(34+count7 , "H", 30, "H");// 複製格式
						ef.CopyCellFormat(34+count7 , "I", 30, "I");// 複製格式
						ef.CopyCellFormat(34+count7 , "J", 30, "J");// 複製格式
						
						
						
						ef.CopyCellFormat(31+count7 , "K", 27, "K");// 複製格式
						ef.CopyCellFormat(31+count7 , "L", 27, "L");// 複製格式
						ef.CopyCellFormat(31+count7 , "M", 27, "M");// 複製格式
						ef.CopyCellFormat(31+count7 , "N", 27, "N");// 複製格式
						ef.CopyCellFormat(31+count7 , "O", 27, "O");// 複製格式
						
						ef.CopyCellFormat(32+count7 , "K", 28, "K");// 複製格式
						ef.CopyCellFormat(32+count7 , "L", 28, "L");// 複製格式
						ef.CopyCellFormat(32+count7 , "M", 28, "M");// 複製格式
						ef.CopyCellFormat(32+count7 , "N", 28, "N");// 複製格式
						ef.CopyCellFormat(32+count7 , "O", 28, "O");// 複製格式
						
						ef.CopyCellFormat(33+count7 , "K", 29, "K");// 複製格式
						ef.CopyCellFormat(33+count7 , "L", 29, "L");// 複製格式
						ef.CopyCellFormat(33+count7 , "M", 29, "M");// 複製格式
						ef.CopyCellFormat(33+count7 , "N", 29, "N");// 複製格式
						ef.CopyCellFormat(33+count7 , "O", 29, "O");// 複製格式
						
						ef.CopyCellFormat(34+count7 , "K", 30, "K");// 複製格式
						ef.CopyCellFormat(34+count7 , "L", 30, "L");// 複製格式
						ef.CopyCellFormat(34+count7 , "M", 30, "M");// 複製格式
						ef.CopyCellFormat(34+count7 , "N", 30, "N");// 複製格式
						ef.CopyCellFormat(34+count7 , "O", 30, "O");// 複製格式
						
						
						
						ef.CopyCellFormat(31+count7 , "P", 27, "P");// 複製格式
						ef.CopyCellFormat(31+count7 , "Q", 27, "Q");// 複製格式
						ef.CopyCellFormat(31+count7 , "R", 27, "R");// 複製格式
						ef.CopyCellFormat(31+count7 , "S", 27, "S");// 複製格式
						ef.CopyCellFormat(31+count7 , "T", 27, "T");// 複製格式
						
						ef.CopyCellFormat(32+count7 , "P", 28, "P");// 複製格式
						ef.CopyCellFormat(32+count7 , "Q", 28, "Q");// 複製格式
						ef.CopyCellFormat(32+count7 , "R", 28, "R");// 複製格式
						ef.CopyCellFormat(32+count7 , "S", 28, "S");// 複製格式
						ef.CopyCellFormat(32+count7 , "T", 28, "T");// 複製格式
						
						ef.CopyCellFormat(33+count7 , "P", 29, "P");// 複製格式
						ef.CopyCellFormat(33+count7 , "Q", 29, "Q");// 複製格式
						ef.CopyCellFormat(33+count7 , "R", 29, "R");// 複製格式
						ef.CopyCellFormat(33+count7 , "S", 29, "S");// 複製格式
						ef.CopyCellFormat(33+count7 , "T", 29, "T");// 複製格式
						
						ef.CopyCellFormat(34+count7 , "P", 30, "P");// 複製格式
						ef.CopyCellFormat(34+count7 , "Q", 30, "Q");// 複製格式
						ef.CopyCellFormat(34+count7 , "R", 30, "R");// 複製格式
						ef.CopyCellFormat(34+count7 , "S", 30, "S");// 複製格式
						ef.CopyCellFormat(34+count7 , "T", 30, "T");// 複製格式
						
						
						ef.CopyCellFormat(31+count7 , "U", 27, "U");// 複製格式
						ef.CopyCellFormat(31+count7 , "V", 27, "V");// 複製格式
						ef.CopyCellFormat(31+count7 , "W", 27, "W");// 複製格式
						ef.CopyCellFormat(31+count7 , "X", 27, "X");// 複製格式
						ef.CopyCellFormat(31+count7 , "Y", 27, "Y");// 複製格式
						ef.CopyCellFormat(31+count7 , "Z", 27, "Z");// 複製格式
						
						ef.CopyCellFormat(32+count7 , "U", 28, "U");// 複製格式
						ef.CopyCellFormat(32+count7 , "V", 28, "V");// 複製格式
						ef.CopyCellFormat(32+count7 , "W", 28, "W");// 複製格式
						ef.CopyCellFormat(32+count7 , "X", 28, "X");// 複製格式
						ef.CopyCellFormat(32+count7 , "Y", 28, "Y");// 複製格式
						ef.CopyCellFormat(32+count7 , "Z", 28, "Z");// 複製格式
						
						ef.CopyCellFormat(33+count7 , "U", 29, "U");// 複製格式
						ef.CopyCellFormat(33+count7 , "V", 29, "V");// 複製格式
						ef.CopyCellFormat(33+count7 , "W", 29, "W");// 複製格式
						ef.CopyCellFormat(33+count7 , "X", 29, "X");// 複製格式
						ef.CopyCellFormat(33+count7 , "Y", 29, "Y");// 複製格式
						ef.CopyCellFormat(33+count7 , "Z", 29, "Z");// 複製格式
						
						ef.CopyCellFormat(34+count7 , "U", 30, "U");// 複製格式
						ef.CopyCellFormat(34+count7 , "V", 30, "V");// 複製格式
						ef.CopyCellFormat(34+count7 , "W", 30, "W");// 複製格式
						ef.CopyCellFormat(34+count7 , "X", 30, "X");// 複製格式
						ef.CopyCellFormat(34+count7 , "Y", 30, "Y");// 複製格式
						ef.CopyCellFormat(34+count7 , "Z", 30, "Z");// 複製格式
						

						ef.CopyCellFormat(31+count7 , "AA", 27, "AA");// 複製格式
						ef.CopyCellFormat(31+count7 , "AB", 27, "AB");// 複製格式
						ef.CopyCellFormat(31+count7 , "AC", 27, "AC");// 複製格式
						ef.CopyCellFormat(31+count7 , "AD", 27, "AD");// 複製格式
						ef.CopyCellFormat(31+count7 , "AE", 27, "AE");// 複製格式
						
						ef.CopyCellFormat(32+count7 , "AA", 28, "AA");// 複製格式
						ef.CopyCellFormat(32+count7 , "AB", 28, "AB");// 複製格式
						ef.CopyCellFormat(32+count7 , "AC", 28, "AC");// 複製格式
						ef.CopyCellFormat(32+count7 , "AD", 28, "AD");// 複製格式
						ef.CopyCellFormat(32+count7 , "AE", 28, "AE");// 複製格式
						
						ef.CopyCellFormat(33+count7 , "AA", 29, "AA");// 複製格式
						ef.CopyCellFormat(33+count7 , "AB", 29, "AB");// 複製格式
						ef.CopyCellFormat(33+count7 , "AC", 29, "AC");// 複製格式
						ef.CopyCellFormat(33+count7 , "AD", 29, "AD");// 複製格式
						ef.CopyCellFormat(33+count7 , "AE", 29, "AE");// 複製格式
						
						ef.CopyCellFormat(34+count7 , "AA", 30, "AA");// 複製格式
						ef.CopyCellFormat(34+count7 , "AB", 30, "AB");// 複製格式
						ef.CopyCellFormat(34+count7 , "AC", 30, "AC");// 複製格式
						ef.CopyCellFormat(34+count7 , "AD", 30, "AD");// 複製格式
						ef.CopyCellFormat(34+count7 , "AE", 30, "AE");// 複製格式
						
						ef.mergeCells(31+count7,1,31+count7,5);
						ef.mergeCells(32+count7,1,34+count7,5);
						
						ef.mergeCells(31+count7,6,31+count7,10);
						ef.mergeCells(32+count7,6,34+count7,10);
						
						ef.mergeCells(31+count7,11,31+count7,15);
						ef.mergeCells(32+count7,11,34+count7,15);
						
						ef.mergeCells(31+count7,16,31+count7,20);
						ef.mergeCells(32+count7,16,34+count7,20);
						
						ef.mergeCells(31+count7,21,31+count7,26);
						ef.mergeCells(32+count7,21,34+count7,26);
						
						ef.mergeCells(31+count7,27,31+count7,31);
						ef.mergeCells(32+count7,27,34+count7,31);
						count7 = count7 + 4;
						
					}
				}
								
				date_before = (String)tempMap_EHF310100T1_List.get("EHF310100T1_02");
				if(count5>6){
					count5=0;
				}
				count5++;
				
			}
			ef.setZoom(100);
			String name ="用餐確認表"+tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";
			String FileName = ef.write();
			request.setAttribute("MSG","列印完成!!");

			request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");

		}catch (Exception E) {
			E.printStackTrace();
		    request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		ehf310100.close();
		Form.setTabsutil_ORD("yes");
		return queryForm(mapping, form, request, response);	
	}

	private Map getPayDetailInf(Connection conn, Map dataMap) {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();
		int deposit = 0;
		int first = 0;
		int last = 0;
		int back = 0;
		int moreLast = 0;
		try{
			String sql = " SELECT EHF310400T1_07, EHF310400T1_11 " +
						 "   FROM EHF310400T1 " +
						 "  WHERE 1=1 " +
						 "    AND EHF310400T1_01 = '"+(String)dataMap.get("EHF310100T0_01")+"' " +
						 "	  AND EHF310400T1_13 = '1' " +
						 "	  AND EHF310400T1_14 <> '1' " +
						 "    AND EHF310400T1_15 = '"+(String)dataMap.get("COMP_ID")+"' " ;
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
				
			while(rs.next()){
				if("01".equals(rs.getString("EHF310400T1_07"))){
					//訂金
					deposit += rs.getInt("EHF310400T1_11");
				}else if("02".equals(rs.getString("EHF310400T1_07"))){
					//頭款
					first += rs.getInt("EHF310400T1_11");
				}else if("03".equals(rs.getString("EHF310400T1_07"))){
					//尾款
					last += rs.getInt("EHF310400T1_11");
				}else if("04".equals(rs.getString("EHF310400T1_07"))){
					//退款
					back += rs.getInt("EHF310400T1_11");
				}else if("05".equals(rs.getString("EHF310400T1_07"))){
					//續訂尾款
					moreLast += rs.getInt("EHF310400T1_11");
				}
				
				
			}
			
			returnMap.put("deposit", deposit);
			returnMap.put("first", first);
			returnMap.put("last", last);
			returnMap.put("back", back);
			returnMap.put("moreLast", moreLast);
			
			rs.close();
			stmt.close();;
		}catch(Exception e){
			System.out.println("列印用餐確認表中，取得付款明細時錯誤:"+e);
		}
		
		return returnMap;
	}

	private Map getPayInf(Connection conn, Map dataMap) {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();
		
		try{
			String sql = " SELECT EHF310400T0_03, EHF310400T0_04 " +
						 "   FROM EHF310400T0 " +
						 "  WHERE 1=1 " +
						 "    AND EHF310400T0_01 = '"+(String)dataMap.get("EHF310100T0_01")+"' " +
						 "    AND EHF310400T0_06 = '"+(String)dataMap.get("COMP_ID")+"' " ;
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
				
			while(rs.next()){
				
				returnMap.put("EHF310400T0_03", rs.getString("EHF310400T0_03"));
				returnMap.put("EHF310400T0_04", rs.getString("EHF310400T0_04"));
				
			}
				
			rs.close();
			stmt.close();;
		}catch(Exception e){
			System.out.println("列印用餐確認表中，取得付款資訊時錯誤:"+e);
		}
		
		return returnMap;
	}

	private Map getCusInf(Connection conn, Map dataMap) {
		// TODO Auto-generated method stub
		Map returnMap = new HashMap();
		
		try{
			String sql = " SELECT EHF310100T0_02, EHF310100T0_03, EHF310100T0_04, EHF310100T0_05, EHF310100T0_07," +
						 " 		  EHF310100T0_09, EHF310100T0_10, EHF310100T0_11, EHF310100T0_21, EHF310100T0_22," +
						 "		  EHF310100T0_31 " +
						 "   FROM EHF310100T0 " +
						 "  WHERE 1=1 " +
						 "    AND EHF310100T0_01 = '"+(String)dataMap.get("EHF310100T0_01")+"' " +
						 "    AND EHF310100T0_34 = '"+(String)dataMap.get("COMP_ID")+"' " +
						 " ORDER BY EHF310100T0_01 ";
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
				
			while(rs.next()){
				
				returnMap.put("EHF310100T0_02", rs.getString("EHF310100T0_02"));
				returnMap.put("EHF310100T0_03", rs.getString("EHF310100T0_03"));
				returnMap.put("EHF310100T0_04", rs.getString("EHF310100T0_04"));
				returnMap.put("EHF310100T0_05", rs.getString("EHF310100T0_05"));
				returnMap.put("EHF310100T0_07", rs.getString("EHF310100T0_07"));
				returnMap.put("EHF310100T0_09", rs.getString("EHF310100T0_09"));
				returnMap.put("EHF310100T0_10", rs.getString("EHF310100T0_10"));
				returnMap.put("EHF310100T0_11", rs.getString("EHF310100T0_11"));
				returnMap.put("EHF310100T0_21", rs.getString("EHF310100T0_21"));
				returnMap.put("EHF310100T0_22", rs.getString("EHF310100T0_22"));
				returnMap.put("EHF310100T0_31", rs.getString("EHF310100T0_31"));
			}
				
			rs.close();
			stmt.close();;
		}catch(Exception e){
			System.out.println("列印用餐確認表中，取得產婦資訊時錯誤:"+e);
		}
		
		return returnMap;
	}

}
