package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF320300M0F;
import com.spon.ems.popo.forms.EX330200R0F;
import com.spon.ems.popo.models.EHF310100;
import com.spon.ems.popo.models.EHF320300;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 餐點分類表
 * (Action)
 */
public class EHF330200M0A extends QueryAction{
	
	
	private BA_TOOLS tools;
	
	public EHF330200M0A(){
		tools = BA_TOOLS.getInstance();
	}
	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		EHF320300M0F Form = new EHF320300M0F();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		List list = new ArrayList();
		
		
		return (ActionForm) Form;
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
			//餐別
			List listEHF320300T1_03 = tools.getOptions(conn, true, "MENU_MEAL", getLoginUser(request).getCompId());
			request.setAttribute("listEHF320300T1_03", listEHF320300T1_03);
			
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}

	/**
	 * 列印餐點分類表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		EHF320300M0F Form = (EHF320300M0F) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		//檢核欄位不可空白
		ActionErrors lc_errors = new ActionErrors();
		BA_Vaildate ve = BA_Vaildate.getInstance();
    	ve.isEmpty(lc_errors, (String)Form.getEHF320300T0_02(), "EHF320300T0_02", "不可空白");
		ve.isEmpty(lc_errors, (String)Form.getEHF320300T1_03(), "EHF320300T1_03", "不可空白");
		
		if (!lc_errors.isEmpty()) {
			saveErrors(request, lc_errors);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas",Form);
			return init(mapping, form, request, response);	
		}

		
		Connection conn = null;
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
    	
		Map dataMap = new HashMap();
		String comp_id = getLoginUser(request).getCompId();
		EHF320300 ehf320300 = new EHF320300(comp_id);
		
    	try {
    		String path = getServlet().getServletContext().getRealPath("");
    		EX330200R0F ef = new EX330200R0F(conn,path,getLoginUser(request).getEmployeeID(),request);
    		
    		Map tempMap_todayOrder = null;
    		Map tempMap_todayMenu = null;
    		Statement stmt = conn.createStatement();
			ResultSet rs = null;
    		Map returnMap = new HashMap();
//    		Map finalMenu = new HashMap();//存放遞補菜單資料
    		Map totalMenuMap = new HashMap();//存放所有菜單資料(今日+遞補)
    		Map totalTeaMap = new HashMap();//存放所有茶飲資料(今日+遞補)
    		List finalMenuList = new ArrayList();//存放遞補菜單資料→存放所有人今日訂餐與調味等資訊
    		List totalMenuList = new ArrayList();//存放所有菜單資料(今日+遞補)
    		List totalTeaList = new ArrayList();//存放所有茶飲資料(今日+遞補)
//    		String finalMenuKey = "";//存放finalMenu的key
			String teaNum = "";//列印茶飲用櫃號
    		//取得欲列印日期與餐別
    		String date = (String)Form.getEHF320300T0_02();//日期
    		String type = (String)Form.getEHF320300T1_03();//餐別
    		dataMap.put("date", date);
    		dataMap.put("type", type);
    		dataMap.put("comp_id", comp_id);
    		
//    		System.out.println("當前列印資料為："+date+"/"+type);
    		//取得今日餐點預排
    		List todayMenuList = ehf320300.getTodayMenu(dataMap);
    		//取得今日訂餐人員清單
    		List todayOrderList = ehf320300.getTodayOrder(dataMap);
    		Iterator it_todayOrder = todayOrderList.iterator();
    		
    		while(it_todayOrder.hasNext()){
				
				tempMap_todayOrder = (Map) it_todayOrder.next();
				returnMap.put("EHF310100T1_01", tempMap_todayOrder.get("EHF310100T1_01"));
				returnMap.put("EHF310100T0_03", tempMap_todayOrder.get("EHF310100T0_03"));
				//取得該人員之不吃食物設定
				ehf320300.getNotEat(returnMap);
				//取得該人員之不喝飲品設定
				ehf320300.getNotDrink(returnMap);
				//取得該人員之是否吃素設定
				ehf320300.getVegetarian(dataMap,returnMap);
				//取得該人員之清淡去油 設定
				ehf320300.getLight(returnMap);
				//取得該人員之調味設定
				ehf320300.getSauce(returnMap);
//				System.out.println("不吃食物："+returnMap.get("notEat"));
////				System.out.println("不喝飲品："+returnMap.get("notDrink"));
//				System.out.println("是否吃素："+returnMap.get("vegetarian"));
//				System.out.println("清淡去油："+returnMap.get("Light"));
////				System.out.println("調味："+returnMap.get("Sauce"));

	    		Iterator it_todayMenu = todayMenuList.iterator();
				while(it_todayMenu.hasNext()){
					tempMap_todayMenu = (Map) it_todayMenu.next();
//					System.out.println("菜單編號："+tempMap_todayMenu.get("EHF320300T1_05"));
//					returnMap.put("EHF320300T1_05", tempMap_todayMenu.get("EHF320300T1_05"));
					String Light = "";
					String MenuSauce = "";
					if((Boolean)returnMap.get("vegetarian")){
						MenuSauce+="素食+";
					}
					//取得最終菜單(若有不吃則找遞補)
					boolean flag = ehf320300.getfinalMenu(returnMap,(String)tempMap_todayMenu.get("EHF320300T1_05"),stmt,rs);
					if(flag){
						//代表有遞補到
						//取得該菜單之食材明細、調味
						//比對菜單管理(食材明細、調味)與客戶需求單(不吃食物、調味)
//						System.out.println("finalMenu:"+returnMap.get("finalMenu"));
						//取得最終菜單之菜單類別
						ehf320300.getFinalMenuType(returnMap,dataMap);
						//取得清淡去油 
						for(int i = 0 ; i < returnMap.get("Light").toString().split(",").length ; i++){
							if(returnMap.get("FinalMenuType").toString().equals(returnMap.get("Light").toString().split(",")[i])){
								//代表最終菜單之菜單類別有被設定清淡去油
								Light = "Light";
							}else{
								//代表當前菜單類別沒有被設定清淡去油
							}
						}
						
						
						//取得最終菜單之調味
						ehf320300.getMenuSauce(returnMap);
						for(int i = 0 ; i < returnMap.get("MenuSauce").toString().split(",").length ; i++){
							if(returnMap.containsKey(returnMap.get("MenuSauce").toString().split(",")[i])){
								//代表最終菜單之調味有在使用者設定口味中
								MenuSauce += returnMap.get("MenuSauce").toString().split(",")[i]+"+";
							}else{
								//代表最終菜單之調味沒有在使用者設定口味中
							}
						}
						//存入 key = 客戶需求單號/今日菜單編號   value = 遞補菜單編號
//						finalMenu.put(returnMap.get("EHF310100T1_01")+"/"+tempMap_todayMenu.get("EHF320300T1_05"), returnMap.get("finalMenu"));
						//存入 List = 今日菜單編號 /最終菜單編號/清淡去油&調味/櫃號
						finalMenuList.add(tempMap_todayMenu.get("EHF320300T1_05")+"/"+returnMap.get("finalMenu")+"/"+Light+"&"+MenuSauce+"/"+returnMap.get("EHF310100T0_03")+"+"+returnMap.get("EHF310100T1_01"));
						
					}else{
						//代表沒遞補到，特殊處理
//						System.out.println("finalMenu:無遞補");
						//存入 key = 客戶需求單號/今日菜單編號     value = exception(找不到遞補→例外處理)
//						finalMenu.put(returnMap.get("EHF310100T1_01")+"/"+tempMap_todayMenu.get("EHF320300T1_05"), "exception");
						//存入 List = 今日菜單編號 /今日菜單編號(找不到遞補→例外處理)/special(特殊人工處理)/櫃號+客戶需求單
						finalMenuList.add(tempMap_todayMenu.get("EHF320300T1_05")+"/"+tempMap_todayMenu.get("EHF320300T1_05")+"/"+"special"+"/"+returnMap.get("EHF310100T0_03")+"+"+returnMap.get("EHF310100T1_01"));
					}
					//存放finalMenu的key
//					finalMenuKey += returnMap.get("EHF310100T1_01")+"/"+tempMap_todayMenu.get("EHF320300T1_05")+",";
					
				}
				Collections.sort(finalMenuList);
//				Collections.sort(finalMenuList, Collections.reverseOrder());
				for(int i = 0 ; i < finalMenuList.size() ; i++){
//					System.out.println(finalMenuList.get(i));
//					System.out.println(todayMenuList);
					if(!totalMenuList.contains(finalMenuList.get(i).toString().split("/")[0])){
						//若所有菜單清單中還未儲存今日菜單才會新增以避免重複
						totalMenuList.add(finalMenuList.get(i).toString().split("/")[0]);
					}
					if(!totalMenuList.contains(finalMenuList.get(i).toString().split("/")[1])){
						//若所有菜單清單中還未儲存遞補菜單才會新增以避免重複
						totalMenuList.add(finalMenuList.get(i).toString().split("/")[1]);
					}
				}
				//依照今日最終出餐清單(totalMenuList)逐一去(finalMenuList)找訂餐人員與口味設定
				for(int i = 0 ; i < totalMenuList.size() ; i++){
					String selfTaste = "";//個人口味+櫃號
					for(int j = 0 ; j < finalMenuList.size() ; j++){
						if(finalMenuList.get(j).toString().split("/")[1].equals(totalMenuList.get(i).toString())){
							//代表今日最終出餐清單中此次資料為當前人員之訂餐
							selfTaste += finalMenuList.get(j).toString().split("/")[2]+"/"+finalMenuList.get(j).toString().split("/")[3]+",";
						}else{
							//代表今日最終出餐清單中此次資料非當前人員之訂餐
						}
					}
					totalMenuMap.put(totalMenuList.get(i).toString(), selfTaste);
//					System.out.println(totalMenuList.get(i));
//					System.out.println("totalMenuMap:"+totalMenuMap);
				}
			
				//菜單處理完處理茶飲
				//星期顯示名稱定義
				String[] dayOfWeek = {"", "日", "一", "二","三", "四", "五", "六"};
				//取得今天是禮拜幾
				Calendar cal = tools.covertStringToCalendar(date);
				dataMap.put("dayOfWeek", dayOfWeek[cal.get(Calendar.DAY_OF_WEEK)]);
				//取得今日茶飲
				String todayTea = ehf320300.getTodayTea(dataMap);
				int teaType = 1;
				if("TEA01".equals(todayTea)){
					teaType = 1;
				}else if("TEA02".equals(todayTea)){
					teaType = 2;
				}else if("TEA03".equals(todayTea)){
					teaType = 3;
				}else if("TEA04".equals(todayTea)){
					teaType = 4;
				}else if("TEA05".equals(todayTea)){
					teaType = 5;
				}
				String finalTea = "";
				
				while("".equals(finalTea)){
//					System.out.println("START:"+teaType);
					switch (teaType){
					
					case 1:
						if(returnMap.containsKey("TEA01")){
							//代表不喝
							teaType = 3;
							returnMap.remove("TEA01");
						}else{
							finalTea = "TEA01";
						}
						break;
						
					case 2:
						if(returnMap.containsKey("TEA02")){
							//代表不喝
							teaType = 5;
							returnMap.remove("TEA02");
						}else{
							finalTea = "TEA02";
						}
						break;
						
					case 3:
						if(returnMap.containsKey("TEA03")){
							//代表不喝
							teaType = 2;
							returnMap.remove("TEA03");
						}else{
							finalTea = "TEA03";
						}
						break;
						
					case 4:
						if(returnMap.containsKey("TEA04")){
							//代表不喝
							finalTea = "特殊(人工處理)";
							returnMap.remove("TEA04");
						}else{
							finalTea = "TEA04";
						}
						break;
					
					case 5:
						if(returnMap.containsKey("TEA05")){
							//代表不喝
							teaType = 4;
							returnMap.remove("TEA05");
						}else{
							finalTea = "TEA05";
						}
						break;
					}
				}
				
//				System.out.println("END");
				//重製茶飲櫃號，避免上個人資料殘留
				teaNum="";
				//茶飲
				if(!totalTeaList.contains(finalTea)){
					totalTeaList.add(finalTea);
				}
				
				if(totalTeaMap.containsKey(finalTea)){
					//代表此茶飲已存過，取出VALUE累加櫃號
					teaNum=(String)totalTeaMap.get(finalTea)+","+returnMap.get("EHF310100T0_03");
				}else{
					//代表此茶飲尚未存過，存入櫃號
					teaNum=(String)returnMap.get("EHF310100T0_03");
				}
				totalTeaMap.put(finalTea, teaNum);
//				System.out.println("totalTeaList:"+totalTeaList);
//				System.out.println("totalTeaMap:"+totalTeaMap);
//				if(returnMap.containsKey(todayTea)){
//					//代表此茶不喝
//					//養肝茶(TEA01)不喝→黃耆茶   黃耆茶(TEA02)不喝→黑豆茶   黑豆茶(TEA03)不喝→杜仲水(TEA04)
//					if("TEA01".equals(todayTea) && !returnMap.containsKey("TEA02")){
//						
//					}else if("TEA02".equals(todayTea) && !returnMap.containsKey("TEA03")){
//						
//					}else if("TEA03".equals(todayTea) && !returnMap.containsKey("TEA04")){
//						
//					}
//				}else{
//					//代表此茶可以喝
////					totalTeaList
//				}
//				String[] notDrink =  returnMap.get("notDrink").toString().split(",");
//				for(int i = 0 ; i < notDrink.length ; i++){
//					
//				}
				
//				totalMenuList.clear();
//				returnMap.clear();
    		}

			this.goPrint(ef,dataMap,totalMenuList,totalMenuMap,ehf320300,totalTeaList,totalTeaMap);
//			ehf320300.close();
//			以下方法，在列印時，可以出現遮罩   Alvin
			//傳入前端需要的檔名
			String name ="";
		
			name="餐點分類表.xls";
			
			
			String FileName="";
		
			//String cur_date = tool.ymdTostring(tools.getSysDate());
			//存入檔案
			FileName=ef.write();
			request.setAttribute("MSG","列印完成!!");
			//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
			request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");

    		if(rs!=null){
        		rs.close();
        	}
			stmt.close();
    		
    	}catch (Exception E) {
			E.printStackTrace();
		    request.setAttribute("Form1Datas",Form);
			request.setAttribute("MSG","列印失敗!!");
		
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				
			}
		}
		if(!ehf320300.isClosed()){
			ehf320300.close();
		}
		
		return init(mapping, form, request, response);	
	}
	private void goPrint(EX330200R0F ef, Map dataMap, List totalMenuList, Map totalMenuMap, EHF320300 ehf320300, List totalTeaList, Map totalTeaMap) {
		// TODO Auto-generated method stub

//		totalMenuMap.get(totalMenuList.get(i).toString());//取得MAP資料
		
		try{
			//取得餐別
			String type = "";
			if("A".equals((String)dataMap.get("type"))){
				type="早餐";
			}else if("B".equals((String)dataMap.get("type"))){
				type="中餐";
			}else if("C".equals((String)dataMap.get("type"))){
				type="晚餐";
			}
			EHF310100 ehf310100 = new EHF310100();
			//取得所有菜單名稱MAP
			Map MenuName = ehf320300.getMenuName((String)dataMap.get("comp_id"));
			//取得所有調味名稱MAP
			Map SeasoningMap = ehf310100.getOptions("ADDITIVES", (String)dataMap.get("comp_id"));
			//取得所有調味名稱MAP
			Map TeaMap = ehf310100.getOptions("TEA", (String)dataMap.get("comp_id"));
			ehf310100.close();
			Map taste = new HashMap();//原始儲存所有菜單的調味&櫃號
			Map AllKindtaste_saved = new HashMap();//已儲存的菜單清淡去油&調味種類
			Map AllKindtaste = new HashMap();//儲存所有菜單的清淡去油&調味種類
			Map AllKind = new HashMap();;//儲存所有菜單的種類數量
			ef.setHeadValue(1, 1, "A", dataMap.get("date")+" 餐點分類表", false, "");
			ef.next();//下一筆
			
			//取得各餐點之種類數量(依調味區分  假如：A菜有 清淡去油、勿太甜   代表A蔡種類數量為2)
			for(int i=0 ; i < totalMenuList.size() ; i++){
				if(!"".equals(totalMenuMap.get(totalMenuList.get(i)).toString()) &&
				   totalMenuMap.get(totalMenuList.get(i)).toString()!=null){
					//代表當前菜單資料不為空值
					for(int j = 0 ; j <totalMenuMap.get(totalMenuList.get(i)).toString().split(",").length  ; j++){
						
						//取得訂餐者櫃號
						String num = totalMenuMap.get(totalMenuList.get(i)).toString().split(",")[j].split("/")[1];
						//取得調味資訊
						String seasoning = totalMenuMap.get(totalMenuList.get(i)).toString().split(",")[j].split("/")[0];
						
						if(taste.containsKey(totalMenuList.get(i)+"@"+totalMenuMap.get(totalMenuList.get(i)).toString().split(",")[j].split("/")[0])){
							//代表此調味已儲存過→取得value將櫃號累加
							taste.put(totalMenuList.get(i)+"@"+totalMenuMap.get(totalMenuList.get(i)).toString().split(",")[j].split("/")[0], 
									  taste.get(totalMenuList.get(i)+"@"+totalMenuMap.get(totalMenuList.get(i)).toString().split(",")[j].split("/")[0])+","+num);
						}else{
							//代表此調味尚未儲存→儲存櫃號資訊
							taste.put(totalMenuList.get(i)+"@"+totalMenuMap.get(totalMenuList.get(i)).toString().split(",")[j].split("/")[0], num) ;
						}
						//調味只會存最後一筆  須修正
						if(AllKindtaste.containsKey(totalMenuList.get(i)+"@所有調味")){
							//代表此KEY已儲存過→取得value將調味累加
							if(AllKindtaste_saved.containsKey(seasoning)){
								//代表此調味已存過，不儲存以免重複
							}else{
								AllKindtaste.put(totalMenuList.get(i)+"@所有調味", AllKindtaste.get(totalMenuList.get(i)+"@所有調味")+","+seasoning);
							}
						}else{
							//代表此KEY尚未儲存→儲存調味資訊
							AllKindtaste.put(totalMenuList.get(i)+"@所有調味", seasoning);
						}
						AllKindtaste_saved.put(seasoning, seasoning);
						AllKindtaste.put(totalMenuList.get(i)+"@調味", totalMenuMap.get(totalMenuList.get(i)).toString().split(",")[j].split("/")[0]);
						AllKindtaste.put(totalMenuList.get(i)+((String)AllKindtaste.get(totalMenuList.get(i)+"@調味"))+"@櫃號", 
										 taste.get(totalMenuList.get(i)+"@"+totalMenuMap.get(totalMenuList.get(i)).toString().split(",")[j].split("/")[0]));
						//
						
					}
					//跑完當前菜單後清空以免資料殘留造成判斷錯誤
					AllKindtaste_saved.clear();
					//取得各餐點之種類數量 KEY = 餐點代號  VALUE = 餐點種類數量
					AllKind.put(totalMenuList.get(i).toString(), taste.size());
				}else{
					//代表當前菜單資料為空值
					//取得各餐點之種類數量 KEY = 餐點代號  VALUE = 餐點種類數量
					AllKind.put(totalMenuList.get(i).toString(), 0);
				}
//				AllKindtaste.putAll(taste);
//				AllKind.put("MAP of "+totalMenuList.get(i).toString(), AllKindtaste);
				taste.clear();
				
			}
			int count = 0;//列印菜單時用到的計數器(B欄位)
			int count1 = 0;//列印菜單調味時用到的計數器(D欄位)
			int count0 = 0;//計算當前菜單列印的調味數
			for(int i=0 ; i < totalMenuList.size() ; i++){
//				System.out.println(totalMenuList.get(i).toString()+"餐點種類數量:"+AllKind.get(totalMenuList.get(i).toString()));
				
				if((Integer)AllKind.get(totalMenuList.get(i).toString())==0){
					
					//因為餐點種類數量為0  單行合併即可
					ef.mergeCells(3+count, "B", 3+count, "C", "CENTRE", "CENTRE", "N", "Y", "", "", "", "", "", 0, 9, "", 0, "N");
					ef.setValue(3+count, "B", (String)MenuName.get(totalMenuList.get(i).toString()),false); //菜單名稱
					count++;
					count1++;
					count0++;
				}else{
					ef.mergeCells(3+count, "B", 3+count+(Integer)AllKind.get(totalMenuList.get(i))-1, "C", "CENTRE", "CENTRE", "N", "Y", "", "", "", "", "", 0, 9, "", 0, "N");
					ef.setValue(3+count, "B", (String)MenuName.get(totalMenuList.get(i).toString()),false); //菜單名稱
					
//					System.out.println(AllKindtaste);
					//取得清淡去油&調味(EHF320100T1_03)
					String menuTaste = (String)AllKindtaste.get(totalMenuList.get(i).toString()+"@調味");
					//取得所有清淡去油&調味(EHF320100T1_03)
					String allMenuTaste = (String)AllKindtaste.get(totalMenuList.get(i).toString()+"@所有調味");
//					System.out.println("allMenuTaste:"+allMenuTaste);
					for(int k = 0 ; k < allMenuTaste.split(",").length ; k++){
						//取得櫃號
						
						//取得當前調味菜單之櫃號清單  VALUE=櫃號+客戶需求單
						String cabinetNum = (String)AllKindtaste.get(totalMenuList.get(i).toString()+allMenuTaste.split(",")[k]+"@櫃號");
//						String cabinetNum = (String)AllKindtaste.get(totalMenuList.get(i).toString()+AllKindtaste.get(totalMenuList.get(i).toString()+"@調味")+"@櫃號");
						String Seasoning="";//儲存調味資料用字串
						
						//依菜單取得菜單類別FinalMenuType
						dataMap.put("finalMenu", totalMenuList.get(i).toString());//菜單單號
						ehf320300.getFinalMenuType(dataMap,dataMap);
						//依照櫃號取得加量資訊
						//櫃號+單號  0=櫃號   1=單號
						String cabinet = "";//存放櫃號
						String list = "";//存放單號
						int moreCount = 0;//計算加量份數
						for(int g = 0 ; g < cabinetNum.split(",").length ; g++){
							if(!"".equals(cabinet)){
								cabinet+=",";
							}
//							list = list + cabinetNum.split(",")[g].split("\\+")[1];//單號
							list = cabinetNum.split(",")[g].split("\\+")[1];//單號
							
							

							//有無加量之FLAG   true代表要加量 false代表不用
							boolean moreFlag = false;
							//取得加量設定
							String More = ehf320300.getMore(list);
							//判斷此菜單之菜單類別有無在加量設定中
							for(int f = 0 ; f < More.split(",").length ; f++){
								if(More.split(",")[f].equals((String)dataMap.get("FinalMenuType"))){
									//代表當前加量設定之類別=此菜單之類別  →加量
									moreFlag=true;
								}
							}
							if(moreFlag){
								//代表此人要加量 →櫃號後括號顯示加量，並將數量多+1(因為加量算兩份)
								cabinet = cabinet+ cabinetNum.split(",")[g].split("\\+")[0]+"(加量)";//櫃號
								moreCount++;
							}else{
								cabinet = cabinet+ cabinetNum.split(",")[g].split("\\+")[0];//櫃號
							}
						
						}
						
						
						
						String tasteName = "";
						if(!"special".equals(allMenuTaste.split(",")[k])){
							//代表不是特殊處理
							if(allMenuTaste.split(",")[k].split("&").length<=1){
								//代表清淡去油與調味只有其中一種有設定或皆未設定
								if("&".equals(allMenuTaste.split(",")[k])){
									//代表沒特別設定調味與清淡去油
									tasteName="一般";
								}else{
									//代表只設定了調味或清淡去油其中一種
									//判斷是設定了清淡去油OR調味
									if("Light".equals(allMenuTaste.split(",")[k].split("&")[0])){
										tasteName="清淡去油";
									}else{
										//代表只設定調味
										for(int j = 0 ; j < allMenuTaste.split(",")[k].split("&")[1].split("\\+").length ; j++){
											if(!"".equals(Seasoning)){
												Seasoning+="+";
											}
											Seasoning += "素食".equals(allMenuTaste.split(",")[k].split("&")[1].split("\\+")[j])?"素食":
												  						SeasoningMap.get(allMenuTaste.split(",")[k].split("&")[1].split("\\+")[j]).toString().split("/")[0];
										}
										tasteName=Seasoning;
									}
								}
							}else{
								if(!"".equals(allMenuTaste.split(",")[k].split("&")[0])){
									//代表兩者皆有設定
									for(int j = 0 ; j < allMenuTaste.split(",")[k].split("&")[1].split("\\+").length ; j++){
										if(!"".equals(Seasoning)){
											Seasoning+="+";
										}
										Seasoning += "素食".equals(allMenuTaste.split(",")[k].split("&")[1].split("\\+")[j])?"素食":
											  						SeasoningMap.get(allMenuTaste.split(",")[k].split("&")[1].split("\\+")[j]).toString().split("/")[0];
									}
									tasteName="清淡去油+"+Seasoning;
								}else{
									//代表只設定調味
									for(int j = 0 ; j < allMenuTaste.split(",")[k].split("&")[1].split("\\+").length ; j++){
										if(!"".equals(Seasoning)){
											Seasoning+="+";
										}
										Seasoning += "素食".equals(allMenuTaste.split(",")[k].split("&")[1].split("\\+")[j])?"素食":
																  SeasoningMap.get(allMenuTaste.split(",")[k].split("&")[1].split("\\+")[j]).toString().split("/")[0];
									}
									tasteName=Seasoning;
								}
								
							}
						}else{
							//代表是特殊處理
							tasteName="特殊(人工處理)";
						}
						ef.mergeCells(3+count1, "D", 3+count1, "F", "CENTRE", "CENTRE", "N", "Y", "", "", "", "", "", 0, 9, "", 0, "N");
						ef.setValue(3+count1, "D", tasteName+"="+(cabinetNum.split(",").length+moreCount),false); //菜單調味
						
						
						
						ef.mergeCells(3+count1, "G", 3+count1, "K", "LEFT", "CENTRE", "N", "Y", "", "", "", "", "", 0, 9, "", 0, "N");
						ef.setValue(3+count1, "G", cabinet,false); //櫃號
						count1++;
						count0++;
//						count += (Integer)AllKind.get(totalMenuList.get(i).toString());
						
						

					}

					count = count+count0;
					count0 = 0;
				}
			}
			
			//列印茶飲資料
			int count2 = 0; //計算列印的茶飲種類
			for(int i=0 ; i < totalTeaList.size() ; i++){
				totalTeaList.get(i);//茶飲代碼
//				totalTeaMap.get(totalTeaList.get(i));//櫃號
				ef.mergeCells(3+count1+count2, "B", 3+count1+count2, "C", "CENTRE", "CENTRE", "N", "Y", "", "", "", "", "", 0, 9, "", 0, "N");
				ef.setValue(3+count1+count2, "B", ("特殊(人工處理)".equals((String)totalTeaList.get(i)))?"茶飲 - 特殊(人工處理)":
																									(String)TeaMap.get((String)totalTeaList.get(i))+"="+totalTeaMap.get(totalTeaList.get(i)).toString().split(",").length,false); //茶飲名稱
				
				ef.mergeCells(3+count1+count2, "D", 3+count1+count2, "K", "LEFT", "CENTRE", "N", "Y", "", "", "", "", "", 0, 9, "", 0, "N");
				ef.setValue(3+count1+count2, "D", (String)totalTeaMap.get(totalTeaList.get(i)),false); //櫃號
				count2++;
			}
			count1++;
			ef.mergeCells(3, "A", 3+count1-1+count2-1, "A", "CENTRE", "CENTRE", "Y", "N", "", "", "", "", "", 0, 9, "", 22, "N");
			ef.setValue(3, "A", type,false);//餐別
		}catch(Exception e){
			System.out.println("列印餐點分類表時錯誤(goPrint):"+e);
		}
		
	}
}