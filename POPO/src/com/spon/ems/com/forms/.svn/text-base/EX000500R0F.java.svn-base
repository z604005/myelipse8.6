package com.spon.ems.com.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_EXCEL_Calendar;


public class EX000500R0F extends BA_EXCEL_Calendar{
	
	private int headsize = 4;//標題個數
	
	/*	存放標題用的陣列
	    HeadData[0][]	資料列
	    HeadData[1][]	欄位代碼
	    HeadData[2][]	資料內容
	    HeadData[3][]	資料為零時是否列印 ==> Y(印) N(不印)
	    HeadData[4][]	資料格式 如 "##,###" or "0.00000"
	*/
	private String HeadData[][];
	public long objTotal[][];//計算合計用的
	
	/**
	 * 一般建構子
	 * 
	 */
	public EX000500R0F(Connection conn, String rpt, String SC0030_01) throws Exception{
		
		//樣板檔的名稱，注意此元件只能處理樣板上只有一個Sheet
		super.TemplateName = "EHF010201R0.xls";
		
		//sheet的名稱
		super.SheetName = "公司行事曆";
				
		//樣板一頁總列數
		super.PageRowCount = 47;
		
		//樣板一頁可放資料筆數，不管一筆資料使用了幾列
		super.PageDataCount = 3;
		
		//合計物件 ==> [0]頁計，[1]總計
		objTotal = new long[0][0];
		
		//一筆資料用了幾列
		super.RecordRow = 15;
		
		//第一筆資料要丟的列數
		super.RecordStart = 5;
		
		//設定標題資料個數
		setHeadSize(headsize);
		
		//開始產生Excel
		super.init(conn, rpt, SC0030_01);
	}
	
	/**
	 * 公司別建構子
	 * 
	 */
	
	public EX000500R0F(Connection conn, String rpt, String SC0030_01, HttpServletRequest request) throws Exception{
		
		//樣板檔的名稱，注意此元件只能處理樣板上一個sheet
		super.TemplateName = "EHF010201R0.xls";
		
		//sheer的名稱
		super.SheetName = "公司行事曆";

		
		//樣板一頁總列數
		super.PageRowCount = 47;
		
		//樣板一頁可放資料筆數，不管一筆資料使用幾列
		super.PageDataCount =3;
		
		//合計物件 ==> [0]頁計，[1]總計
		objTotal = new long[0][0];
		
		//一筆資料用了幾列
		 super.RecordRow = 15;
		 
		 //第一筆資料要丟的個數
		 super.RecordStart = 5 ;
		 
		 //設定標題資料個數
		 setHeadSize(headsize);
		 
		 super.request = request;
		 
		 //開始產生Excel
		 super.init(conn, rpt, SC0030_01);
	}
	
	/**
	 * 新的一頁開始之前(通常是丟頁計用的)
	 * 如第一頁要換頁前須先將第一頁的頁計寫入，然後才進入第二頁
	 */
	
	public void BeforeChangePage() throws Exception{
		
	}
	
	/**
	 * 丟總計
	 * 
	 */
	
	public void EndDocument()throws Exception{
		
	}
	public void printHeadValue(String comp_name) {

		try {
			// 列印報表表頭資料
			this.setHeadValue(0, 1, "B", comp_name + "", false, ""); // 列印公司抬頭
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 列印
	 * 
	 * @param conn
	 * @param comp_id 
	 * @param printlist
	 * @param comp_id
	 * @param comp_id 
	 * @param returnMap 
	 * @return
	 */
	public int print(Connection conn,  String year, String comp_id, Map Vacation) {
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		BaseFunction base_tools = new BaseFunction(comp_id);
		int DataCount = 0;
		String month_start_date = "";
		String month_end_date = "";
		String  day[][] = new String [12][7];
		List dataList = null;
		int week=0;
		
		try {
			Map returndataMap=new HashMap();
			Map dataMap=new HashMap();
			//一年12個月
			for (int i = 1; i <= 12; i++) {
				int Data_day_First=1;//日期初始值
				int Data_day_Last=1;//日期初始值
				
				String moth=String.format("%02d", i);
				
				// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
				month_start_date = ems_tools.convertADDateToStrng(ems_tools.getFirstMonthDay(ems_tools.covertStringToCalendar((Integer.valueOf(year)+1911)+ "/"+moth+"/01")));
				// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
				month_end_date = ems_tools.convertADDateToStrng(ems_tools.getLastMonthDay(ems_tools.covertStringToCalendar((Integer.valueOf(year)+1911)+ "/"+moth+"/01")));
				
				String[] token = month_end_date.split("/");
				
				//每月第一天星期幾
				String Week_First=this.getWeekDisplayName(ems_tools.covertStringToCalendar(month_start_date));
				//每月最後一天星期幾
				String Week_Last=this.getWeekDisplayName(ems_tools.covertStringToCalendar(month_end_date));
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				String sql = "";

				Map printMap = null;
				
				String department_name = "";
				String money = "";
				printMap = new HashMap();

				sql = ""
					+ " SELECT  "
					+ " EHF000500T0_02 AS 公司組織,"
					+ " EHF000500T0_04 AS 休假類別,"
					+ " DATE_FORMAT(EHF000500T0_05, '%Y/%m/%d') AS 休假日期,"
					+ " EHF000500T0_09 AS 休假原因,"
					+ " EHF000500T0_11 AS 公司代碼" 
					+ " FROM EHF000500T0"
					+ " WHERE 1=1 " 
					+ " AND EHF000500T0_11 = '"	+ comp_id+ "' " // 公司代碼
					+ " ORDER BY EHF000500T0_05";

				PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
				dataList = base_tools.resultSetToList(pstmt.executeQuery());
				
				if(!Week_First.equals("")){
					week=Integer.valueOf(Week_First).intValue();
				}
				
				if (!Week_First.equals("0")) {
					// 第一天不為禮拜天
					// 先填滿第一天之前為空白
					for (int day_2 = 0; day_2 < week; day_2++) {
						day[0][day_2] = String.valueOf("");
						day[1][day_2] = String.valueOf("");
					}
					// 再填滿第一天之後的第一個禮拜
					for (int day_2 = week; day_2 < 7; day_2++) {
						dataMap=new HashMap();
						returndataMap=new HashMap();
						day[0][day_2] = String.valueOf(Data_day_First);
						
						String test_day=(Integer.valueOf(year)+1911)+"/"+moth+"/"+String.format("%02d", Data_day_First);
						this.select(dataList, test_day,returndataMap,dataMap,Vacation);
						
						
						day[1][day_2] = (String)returndataMap.get("classifying");
						Data_day_First++;
						Data_day_Last++;
					}
				} else {
					for (int day_2 = week; day_2 < 7; day_2++) {
						dataMap=new HashMap();
						returndataMap=new HashMap();
						day[0][day_2] = String.valueOf(Data_day_First);
						
						String test_day=(Integer.valueOf(year)+1911)+"/"+moth+"/"+String.format("%02d", Data_day_First);
						this.select(dataList, test_day,returndataMap,dataMap,Vacation);
						day[1][day_2] = (String)returndataMap.get("classifying");
						Data_day_First++;
						Data_day_Last++;
					}
				}
				
				//放星期
				for (int day_1 = 2; day_1 < 12; day_1+=2) {
					for (int day_2 = 0; day_2 < 7; day_2++) {
						day[day_1][day_2] = String.valueOf(Data_day_First);
						Data_day_First++;
						if(Data_day_First>Integer.valueOf(token[2])){
							break;
						}
					}
					if(Data_day_First>Integer.valueOf(token[2])){
						break;
					}
				}
				//放備註
				for (int day_1 = 3; day_1 < 12; day_1+=2) {
					for (int day_2 = 0; day_2 < 7; day_2++) {
						dataMap=new HashMap();
						returndataMap=new HashMap();
						String test_day=(Integer.valueOf(year)+1911)+"/"+moth+"/"+String.format("%02d", Data_day_Last);
  						this.select(dataList, test_day,returndataMap,dataMap,Vacation);
						day[day_1][day_2] = (String)returndataMap.get("classifying");
						Data_day_Last++;
						if(Data_day_Last>Integer.valueOf(token[2])){
							break;
						}
					}
					if(Data_day_Last>Integer.valueOf(token[2])){
						break;
					}
				}
				
				
				//清除NULL
				for (int day_1 = 0; day_1 < 12; day_1++) {
					for (int day_2 = 0; day_2 < 7; day_2++) {
						if(day[day_1][day_2]==null){
							day[day_1][day_2]="";
						}
					}
				}
				
				
				
				DataCount++;
				printMap.put("WEEK", Week_First);
				this.printDetail(printMap, day,i,year);
				// rs.close();
				stmt.close();
				base_tools.close();
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DataCount;
	}
	
	
	/**
	 * 列印一筆資料
	 * 
	 * @param dataMap
	 */
	public void printDetail(Map dataMap ,String[][] day,int month,String year) {

		try {
			//this.colour=Colour.BLACK;
			
			switch (month) {
			case 1:
				this.BorderLine=BorderLineStyle.MEDIUM;
				this.setHeadValue(1, 3, "B",  year+" 年  " + "一" + " 月", false, "");
				this.setHeadValue(2, 18, "B", year+" 年  " + "二" + " 月", false, "");
				this.setHeadValue(3, 33, "B", year+" 年  " + "三" + " 月", false, "");
				break;
			case 4:
				this.BorderLine=BorderLineStyle.MEDIUM;
				this.setHeadValue(1, 3, "B",  year+" 年  " + "四" + " 月", false, "");
				this.setHeadValue(2, 18, "B", year+" 年  " + "五" + " 月", false, "");
				this.setHeadValue(3, 33, "B", year+" 年  " + "六" + " 月", false, "");
				break;
			case 7:
				this.BorderLine=BorderLineStyle.MEDIUM;
				this.setHeadValue(1, 3, "B",  year+" 年  " + "七" + " 月", false, "");
				this.setHeadValue(2, 18, "B", year+" 年  " + "八" + " 月", false, "");
				this.setHeadValue(3, 33, "B", year+" 年  " + "九" + " 月", false, "");
				break;
			case 10: 
				this.BorderLine=BorderLineStyle.MEDIUM;
				this.setHeadValue(1, 3, "B",  year+" 年  " + "十" + " 月", false, "");
				this.setHeadValue(2, 18, "B", year+" 年  " + "十一"+ " 月", false, "");
				this.setHeadValue(3, 33, "B", year+" 年  " + "十二"+ " 月", false, "");
				break;
			}

			// 列印報表每筆資料
			this.next(); // 換下一行
			

			for (int i = 0; i < 11; i += 2) {
				if (!day[i + 1][0].equals("")) {
					this.colour = Colour.YELLOW;
				} else{
					this.colour = Colour.BLACK;
				}
				this.BorderLine= BorderLineStyle.THIN;
				this.setDetail(i + 1, "C", day[i + 0][0], false);
				this.setDetail(i + 2, "C", day[i + 1][0], false);

				if (!day[i + 1][1].equals("")) {
					
					this.colour = Colour.YELLOW;
				} else{
					this.colour = Colour.BLACK;
				}
				this.BorderLine= BorderLineStyle.THIN;
				this.setDetail(i + 1, "D", day[i + 0][1], false);
				this.setDetail(i + 2, "D", day[i + 1][1], false);

				if (!day[i + 1][2].equals("")) {
					this.colour = Colour.YELLOW;
				} else{
					this.colour = Colour.BLACK;
				}
				this.BorderLine= BorderLineStyle.THIN;
				this.setDetail(i + 1, "E", day[i + 0][2], false);
				this.setDetail(i + 2, "E", day[i + 1][2], false);

				if (!day[i + 1][3].equals("")) {
					this.colour = Colour.YELLOW;
				} else{
					this.colour = Colour.BLACK;
				}
				this.BorderLine= BorderLineStyle.THIN;
				this.setDetail(i + 1, "F", day[i + 0][3], false);
				this.setDetail(i + 2, "F", day[i + 1][3], false);

				if (!day[i + 1][4].equals("")) {
					this.colour = Colour.YELLOW;
				} else{
					this.colour = Colour.BLACK;
				}
				this.BorderLine= BorderLineStyle.THIN;
				this.setDetail(i + 1, "G", day[i + 0][4], false);
				this.setDetail(i + 2, "G", day[i + 1][4], false);

				if (!day[i + 1][5].equals("")) {
					this.colour = Colour.YELLOW;
				} else{
					this.colour = Colour.BLACK;
				}
				this.BorderLine= BorderLineStyle.THIN;
				this.setDetail(i + 1, "H", day[i + 0][5], false);
				this.setDetail(i + 2, "H", day[i + 1][5], false);

				if (!day[i + 1][6].equals("")) {
					this.colour = Colour.YELLOW;
				} else{
					this.colour = Colour.BLACK;
				}
				this.BorderLine= BorderLineStyle.THIN;
				this.setDetail(i + 1, "I", day[i + 0][6], false);
				this.setDetail(i + 2, "I", day[i + 1][6], false);
			}
			
			
			/*
			this.setDetail(1, "C", day[0][0], false); 
			this.setDetail(1, "D", day[0][1], false); 
			this.setDetail(1, "E", day[0][2], false); 
			this.setDetail(1, "F", day[0][3], false); 
			this.setDetail(1, "G", day[0][4], false); 
			this.setDetail(1, "H", day[0][5], false); 
			this.setDetail(1, "I", day[0][6], false);
			
			this.setDetail(2, "C", day[1][0], false); 
			this.setDetail(2, "D", day[1][1], false); 
			this.setDetail(2, "E", day[1][2], false); 
			this.setDetail(2, "F", day[1][3], false); 
			this.setDetail(2, "G", day[1][4], false); 
			this.setDetail(2, "H", day[1][5], false); 
			this.setDetail(2, "I", day[1][6], false);
			
			this.setDetail(3, "C", day[2][0], false); 
			this.setDetail(3, "D", day[2][1], false); 
			this.setDetail(3, "E", day[2][2], false); 
			this.setDetail(3, "F", day[2][3], false); 
			this.setDetail(3, "G", day[2][4], false); 
			this.setDetail(3, "H", day[2][5], false); 
			this.setDetail(3, "I", day[2][6], false);
			*/
			//this.setDetail(i, "F", (Integer) dataMap.get("實發金額"), true,"##,###"); // 實發金額
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	
	
	public void select(List data,String day,Map returndataMap,Map dataMap,Map VacationMap) {
		ArrayList getVacationlist = new ArrayList();
		for(int i=0;i<data.size();i++){
			dataMap=(Map)data.get(i);
			if (day.equals((String) dataMap.get("休假日期"))) {
				returndataMap.put("DATA", "yes");
				
				
				if(VacationMap.get((String) dataMap.get("休假類別"))!=null){
					getVacationlist=(ArrayList)VacationMap.get((String) dataMap.get("休假類別"));
					returndataMap.put("classifying", (String)getVacationlist.get(1)+"/"+(String) dataMap.get("休假原因"));
				}
				break;
			} else {
				returndataMap.put("DATA", "no");
				returndataMap.put("classifying", "");
				
			}
		}
		
	}
	
	
	/**
	  * 取得星期顯示名稱 by Calendar
	  * @param cal
	  * @return
	  */
	 public String getWeekDisplayName(Calendar cal){
		 
		 String day_of_week_disply_name = "";
		 
		 try{
			 if(cal != null){
				 day_of_week_disply_name = this.getWeekDisplayName(cal.get(Calendar.DAY_OF_WEEK));
			 }
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return day_of_week_disply_name;
	 }
	 /**
	  * 取得星期顯示名稱 by Day of Week
	  * @param day_of_week
	  * @return
	  */
	 public String getWeekDisplayName(int day_of_week){
		 
		 String day_of_week_disply_name = "";
		 
		 try{
			 //星期顯示名稱定義
			 String[] dayOfWeek = {"", "0", "1", "2","3", "4", "5", "6"};
			 
			 day_of_week_disply_name = dayOfWeek[day_of_week];
			 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		 
		 return day_of_week_disply_name;
	 }
	
	
	
}

