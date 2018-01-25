package com.spon.ems.util.card_system;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.utils.util.BA_TOOLS;

public abstract class EMS_CardSystem {
	
	
	/**
	 * 門禁刷卡取得資料程式
	 * @param conn
	 * @param setting_key
	 * @param comp_id
	 * @return
	 */
	public Map process(Connection conn, String setting_no, String comp_id){
		
		List dataList = null;
		String[] Key = null;
		Map dataMap = null;
		Map msgMap = new HashMap();
		int alldata = 0;
		int okdata = 0;
		int cardng = 0;
		int datang = 0;
		
		try{
				
			//取得資料排序Key
			Key = this.getMapKey(conn, setting_no, comp_id);
			
			if(Key == null || Key.length == 0){
				//表示檔案匯入設定格式取得失敗
				System.out.println("門禁刷卡資料 - 檔案匯入設定格式取得失敗!!");
				return msgMap;
			}
			
			//取得資料清單
			dataList = this.getCardDataList(conn, comp_id);
			
			//判斷 dataList 是否存在, 如果不存在表示未取得檔案
			if( dataList == null ){
				//表示原始檔案取得失敗
				System.out.println("門禁刷卡資料 - 原始檔案取得失敗!!");
				return msgMap;
			}
			
			Iterator it = dataList.iterator();
			//String tmp_date = "";
			//boolean getCardYear = false;
			
			while(it.hasNext()){
				//取得清單中的 String[] 資料
				String[] data = (String[]) it.next();
				
				//組合 dataMap
				dataMap = this.setMapValue(Key, data);
				
				//getCardYear = false;
				
				//確認感應卡資料是否存在
				if(true ){
					
					
					//需要有年月日
					//打卡日期
                    String EHF020404T0_04 = ((String)dataMap.get(12)).trim() +
					 					  	((String)dataMap.get(14)).trim() +
					 					  	((String)dataMap.get(16)).trim();
                    //至少需要時分，秒可要可不要
                    String EHF020404T0_05 = "";
                    if((String)dataMap.get(22) != null){
                                      
                    	//打卡時間
                    	EHF020404T0_05 = ((String)dataMap.get(18)).trim() +
                    					 ((String)dataMap.get(20)).trim() +
                    					 ((String)dataMap.get(22)).trim();
                    }else{
                    	 //打卡時間
                        EHF020404T0_05 = ((String)dataMap.get(18)).trim() +
                        				 ((String)dataMap.get(20)).trim();
                    }

                  
					
					String EHF020404T0_02 = EHF020404T0_04 +EHF020404T0_05+ ((String)dataMap.get(0)).trim();
						
						
					
					
					//確認資料是否已存在
					if((!chkdataexist( conn, EHF020404T0_02, comp_id))){
						//建立門禁資料
						this.addData(conn, dataMap, EHF020404T0_02, comp_id);
						okdata++;
					}else{
						//System.out.println("門禁刷卡資料已存在，系統號："+EHF020404T0_02 );
						datang++;
					}
					
				}else{
					cardng++;
				}	
				
				alldata++;
			}
			
			msgMap.put("OKDATA", okdata);
			msgMap.put("DATANG", datang);
			msgMap.put("CARDNG", cardng);
			msgMap.put("ALLDATA", alldata);
			
			System.out.println("門禁刷卡資料成功匯入: "+okdata+"筆資料!!");
			System.out.println("門禁刷卡資料共有: "+datang+"筆重複資料未匯入!!");
			System.out.println("門禁刷卡資料共有: "+cardng+"筆資料未匯入!! 門禁卡號不存在!!");
			System.out.println("門禁刷卡資料全部共處理: "+alldata+"筆資料!!");
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return msgMap;
	}
	
	/**
	 * 新增門禁資料
	 * @param conn
	 * @param dataMap
	 * @param comp_id
	 */
	protected void addData( Connection conn, Map dataMap, String EHF020404T0_02, String comp_id ){
		
		BA_TOOLS ba_tools = BA_TOOLS.getInstance();
		
		try{
				
			//新增門禁刷卡資料
			String sql = "" +
			" INSERT INTO ehf020404t0 (" +
			" EHF020404T0_01, EHF020404T0_02, EHF020404T0_03, EHF020404T0_04, EHF020404T0_05, " +
			" EHF020404T0_06, EHF020404T0_07, EHF020404T0_08, EHF020404T0_09, EHF020404T0_10,EHF020404T0_11,EHF020404T0_FLAG, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, 1, NOW()) ";

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;

			
			
			//打卡日期
            String EHF020404T0_04=((String)dataMap.get(12)).trim() +"/"+
			 					  ((String)dataMap.get(14)).trim() +"/"+
			 					  ((String)dataMap.get(16)).trim();
            //秒數不要的話帶入00
            String EHF020404T0_05 = "";
            if((String)dataMap.get(22) != null){
            	//打卡時間
            	EHF020404T0_05=((String)dataMap.get(18)).trim() +":"+
            			   	   ((String)dataMap.get(20)).trim() +":"+
            			       ((String)dataMap.get(22)).trim();
            }else{
            	//打卡時間
                EHF020404T0_05=((String)dataMap.get(18)).trim() +":"+
                			   ((String)dataMap.get(20)).trim() +":00";
            }
            
            String EHF020404T0_11="";
            //考勤日期
            if(dataMap.get(6)!=null){
            	EHF020404T0_11 = ((String)dataMap.get(6)).trim() +
								 ((String)dataMap.get(8)).trim() +
								 ((String)dataMap.get(10)).trim();
            }
            
            
			p6stmt.setString(indexid, EHF020404T0_02);  //刷卡資料序號EHF020404T0_01
			indexid++;
			p6stmt.setString(indexid, EHF020404T0_02);  //門禁系統號  ==> 日期+時間+卡號EHF020404T0_02
			indexid++;
			p6stmt.setString(indexid, dataMap.containsKey(0)==true?((String)dataMap.get(0)).trim():"");  //感應卡號EHF020404T0_03
			indexid++;
			
			//處理打卡日期時間 use Calendar
			Calendar tmp_cal = 	ba_tools.covertStringToCalendar(EHF020404T0_04+" " + EHF020404T0_05, this.getDateFormat() + " " + this.getTimeFormat());
		
			p6stmt.setString(indexid, ba_tools.convertADDateToStrng(tmp_cal.getTime()));  //打卡日期EHF020404T0_04
			indexid++;
			p6stmt.setString(indexid, ba_tools.covertDateToString(tmp_cal.getTime(), "HH:mm:ss"));  //打卡時間EHF020404T0_05
			indexid++;
			p6stmt.setString(indexid, ba_tools.covertDateToString(tmp_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));  //打卡日期時間EHF020404T0_06
			indexid++;
			
			p6stmt.setString(indexid, dataMap.containsKey(2)==true?((String)dataMap.get(2)).trim():"");  //門禁刷卡機器代號EHF020404T0_07
			indexid++;
			p6stmt.setString(indexid, dataMap.containsKey(4)==true?((String)dataMap.get(4)).trim():"");  //動作代碼EHF020404T0_08
			indexid++;
			p6stmt.setString(indexid, comp_id);  //公司代碼EHF020404T0_09
			indexid++;
			p6stmt.setString(indexid, "1");  //1->自動匯入 (預設)EHF020404T0_10
			indexid++;
			p6stmt.setString(indexid, this.setDatetimeFormat( EHF020404T0_11,"120000"));  //考勤日期
			indexid++;
			
			p6stmt.setString(indexid, "1");  //1->未確認 EHF020404T0_FLAG
			indexid++;
			
			p6stmt.setString(indexid, "SYSTEM");  //建立人員
			indexid++;
			p6stmt.setString(indexid, "SYSTEM");  //修改人員
			indexid++;
			
			//System.out.println("sql ==>"+p6stmt.getQueryFromPreparedStatement());
			
			p6stmt.executeUpdate();
				
			pstmt.close();
			p6stmt.close();
		
			//執行刪除LOGDATA_EMS資料
			//if(!"".equals((String)dataMap.get("EHF020404T0_02")) && (String)dataMap.get("EHF020404T0_02") != null && dataMap.containsKey("EHF020404T0_01") ){
			//	this.del_logdata_ems(conn, (String)dataMap.get("EHF020404T0_01"));
			//}

		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	

	/**
	 * 檢核門禁感應卡資料是否已存在, 存在 return true, 不存在 return false
	 * @param conn_ems
	 * @return
	 */
	protected boolean chkCardExist( Connection conn_ems, String Card_SN, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			
			Statement stmt = conn_ems.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT * FROM EHF020403T1 " +
			" WHERE 1=1 " +
			" AND EHF020403T1_02 = '"+Card_SN.trim()+"' " +  //感應卡號
			" AND EHF020403T1_06 = '"+comp_id+"' " ;  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//門禁感應卡資料存在
				chk_flag = true;
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 檢核門禁刷卡資料是否已存在, 存在 return true, 不存在 return false
	 * @param conn_ems
	 * @return
	 */
	protected boolean chkdataexist( Connection conn_ems, String iLogDataSN, String comp_id ){
		
		boolean chk_flag = false;
		
		try{
			
			Statement stmt = conn_ems.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = " SELECT * " +
						 " FROM EHF020404T0 " +
						 " WHERE 1=1 " +
						 " AND EHF020404T0_02 = '"+iLogDataSN+"' " +  //刷卡系統號 = 日期+時間+卡號
						 " AND EHF020404T0_09 = '"+comp_id+"' ";  //公司代碼
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				//該筆門禁刷卡資料已存在
				chk_flag = true;
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 刪除已複製到emsdb 的門禁資料
	 * @param conn
	 * @param iLogDataSN
	 * @return
	 */
	protected abstract boolean del_logdata_ems(Connection conn, String iLogDataSN);
	
	/**
	 * 取得資料List
	 * @param conn
	 * @param comp_id
	 * @return
	 */
	protected abstract List getCardDataList(Connection conn, String comp_id);
	
	/**
	 * 取得List格式的排序 Index
	 * @return
	 */
	public  abstract String[] getMapKey(Connection conn , String setting_no, String comp_id);
	
	/**
	 * 組成 data Map
	 * @param data
	 * @return
	 */
	protected Map setMapValue(String[] key, String[] data){
		
		Map dataMap = new HashMap();
		String[] tmp_key = null;
		try{
			for(int i=0; i<key.length; i++){
				//加入條件, 判斷該筆資料是否需要擷取
				if("N".equalsIgnoreCase(key[i])){
					//表示該欄位資料不需要擷取
					i+=1;//換下一個欄位
					continue;
				}
				if("NULL".equalsIgnoreCase(key[i])){
					//表示該欄位資料不需要擷取
					continue;
				}
				
				if((key[i].indexOf("=")) != -1){
					//表示Key中含有 "="
					//Handle Special Key
					tmp_key = key[i].split("=");  //分割KEY
					dataMap.put(tmp_key[0], this.handleSpecialKey(tmp_key[1]));  //處理特殊KEY
				}else{
					//產生 dataMap
					dataMap.put(i, data[0].substring((Integer.valueOf(key[i])-1), (Integer.valueOf(key[i+1]))));
				}
				i+=1;//換下一個欄位
				
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	/**
	 * 轉換卡鐘日期時間格式 To SQL Datetime
	 * @param date
	 * @param time
	 * @return
	 */
	private String setDatetimeFormat(String date , String time){
		
		String re_date = "";
		String re_time = "";
		
		try{
			if(date.length() == 6){
				re_date = String.valueOf((Integer.parseInt(date.substring(0, 2))+1911))+"/"+date.substring(2, 4)+"/"+date.substring(4, 6);
			}else if(date.length() == 7){
				re_date = String.valueOf((Integer.parseInt(date.substring(0, 3))+1911))+"/"+date.substring(3, 5)+"/"+date.substring(5, 7);
			}else if(date.length() == 8){
				re_date = date.substring(0, 4)+"/"+date.substring(4, 6)+"/"+date.substring(6, 8);
			}else{
				re_date = date;
			}
			
			if(time.length() == 6){
				re_time = time.substring(0, 2)+":"+time.substring(2, 4)+":"+time.substring(4, 6);
			}else if(time.length() == 4){
				re_time = time.substring(0, 2)+":"+time.substring(2, 4)+":00";
			}else{
				re_time = time;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return re_date+" "+re_time;
	}
	
	/**
	 * 處理特殊的擷取Key
	 * @param special_key
	 * @return
	 */
	protected String handleSpecialKey(String special_key){
		
		String return_string = "";
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_string;
	}
	
	/**
	 * 日期格式
	 * @return
	 */
	protected String getDateFormat(){
		return "yyyy/MM/dd";
	}
	
	/**
	 * 時間格式
	 * @return
	 */
	protected String getTimeFormat(){
		return "HH:mm:ss";
	}
	
	

}
