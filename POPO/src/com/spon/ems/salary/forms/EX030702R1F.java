package com.spon.ems.salary.forms;

import com.spon.utils.util.BA_EXCEL_For_Individual_salary;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import jxl.format.Colour;
import jxl.write.WritableFont;

public class EX030702R1F extends BA_EXCEL_For_Individual_salary {
	
private int headsize = 12; //標題個數
	
	//	存放標題用的陣列
	//	HeadData[0][] 資料列
	//	HeadData[1][] 欄位代號
	//	HeadData[2][] 資料內容
	//	HeadData[3][] 零是否列印 Y 印 N 不印
	//	HeadData[4][] 資料格式 如 "##,###" or "0.00000"



	BA_TOOLS tools = BA_TOOLS.getInstance();
	private String HeadData[][];
	public long objTotal[][]; //計算合計用的
//===========================加項===========================
	private List Allowance_Greater_than;//津貼基本資料設定(加項)
	private int Allowance_Greater_than_count;//加項總數(津貼基本資料設定)
	private List Greater_than_ALL;//取得薪資加項總明細(薪資項目金額設定)
	private int Greater_than_count;//加項總數(薪資項目金額設定)
	private int Greater_than_count_01;// 加班費,不休假加班費,差旅費
//===========================加項===========================	
	
//===========================扣項===========================	
	private List Allowance_Less_than;//津貼基本資料設定(扣項)
	private int Allowance_Less_than_count;//扣項總數(津貼基本資料設定)
	private int Less_than_count;//扣項總數(薪資項目金額設定)
	private List Less_than_ALL;//取得薪資扣項總明細(薪資項目金額設定)
	private int Less_than_count_02;//請假扣薪金額,薪資所得扣繳金,勞退自提,福利金,遲到扣薪金額,早退扣薪金額,曠職扣薪金額
//===========================扣項===========================	
	
//===========================請假時間與次數===========================	
	String month_start_date="";
	String month_end_date="";
	private List Other_Leave_time;//所有請假單、異常出勤的紀錄
	private int Other_Leave_time_count;//所有請假單、異常出勤的總數

//===========================請假時間與次數===========================	
	
	private int i=0;
	private int tatle=0;
	private String day="";
	private int MUNBER_MAX=0;
	
	private String print_date="";
	private String comp_name="";
	private String salYYMM="";
	//private String print_date="";
	
	/** 
	 * 一般建構子
	 * 
	 */
	public EX030702R1F(Connection conn,String rpt,String SC0030_01) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF030702R1.xls";
		
		//sheet的名稱
		super.SheetName = "個人薪資條";
			
		//樣板一頁總列數
		super.PageRowCount = 42;
		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 2;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal = new long[2][4];
			
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 6;
		
		//標題設定標題資料個數 
		setHeadSize(headsize);
			
		//開始產生Excel
		super.init(conn,rpt,SC0030_01);
	}
	
	/** 
	 * 公司別建構子
	 * 
	 */
	public EX030702R1F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request, EHF030600M0F form,String comp_id,List printlist, String print_date, String comp_name) throws Exception{
		

		this.print_date=print_date;
		this.comp_name=comp_name;
		
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF030702R1.xls";
		
		this.day=form.getEHF030600T0_02();
		//sheet的名稱
		super.SheetName = "個人薪資條";
		//取得薪資主檔中   已經計算好的基本加項、扣項金額
		Map Add_items=this.Add_items(conn,new HashMap(),comp_id,printlist,"");
		
//=======================加項開始================================================================		
		//取得薪資加項總明細(津貼基本資料設定)
		this.Allowance_Greater_than=this.SELECT_Allowance_Project_Count(conn, form,comp_id,printlist,">","");
		//取得薪資加項總數量(津貼基本資料設定)
		List Allowance_ALL_Greater_than_count_list=this.SELECT_Allowance_Project_Count(conn, form,comp_id,printlist,">","01");
		this.Allowance_Greater_than_count=Integer.valueOf(Allowance_ALL_Greater_than_count_list.get(0).toString());//1
		//**********************************************************************************
		//取得薪資加項總明細(薪資項目金額設定)
		this.Greater_than_ALL=this.SELECT_Salary_Project_Count(conn, form,comp_id,printlist,">","");
		//取得薪資加項總數量(薪資項目金額設定)
		List Greater_than_count_list=this.SELECT_Salary_Project_Count(conn, form,comp_id,printlist,">","01");
		this.Greater_than_count=Integer.valueOf(Greater_than_count_list.get(0).toString());//3
		//**********************************************************************************
		// 加班費,不休假加班費,差旅費
		this.Greater_than_count_01=Integer.valueOf(Add_items.get("Additems_count").toString());//5	
//=======================加項結束================================================================	
		
//=======================扣項開始================================================================			
		//取得薪資扣項總明細(津貼基本資料設定)
		this.Allowance_Less_than=this.SELECT_Allowance_Project_Count(conn, form,comp_id,printlist,"<","");
		//取得薪資扣項總數量(津貼基本資料設定)
		List Allowance_ALL_Less_than_count_list=this.SELECT_Allowance_Project_Count(conn, form,comp_id,printlist,"<","01");
		this.Allowance_Less_than_count=Integer.valueOf(Allowance_ALL_Less_than_count_list.get(0).toString());//2
		//**********************************************************************************
		//取得薪資扣項總明細(薪資項目金額設定)
		this.Less_than_ALL=this.SELECT_Salary_Project_Count(conn, form,comp_id,printlist,"<","");
		//取得薪資扣項總數量(薪資項目金額設定)
		List Less_than_count_list=this.SELECT_Salary_Project_Count(conn, form,comp_id,printlist,"<","01");
		this.Less_than_count=Integer.valueOf(Less_than_count_list.get(0).toString());//4
		//**********************************************************************************
		//請假扣薪金額,薪資所得扣繳金,勞退自提,福利金,遲到扣薪金額,早退扣薪金額,曠職扣薪金額
		this.Less_than_count_02=Integer.valueOf(Add_items.get("Reduction_count").toString());//6

//=======================扣項結束================================================================			

//=======================請假、考勤異常次數與資料=====================================================
		// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
		this.month_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(form.getEHF030600T0_02()+ "/01")));
		// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
		this.month_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(form.getEHF030600T0_02()+ "/01")));
		//取得系統參數設定的一天工作時數
		String dayhours = tools.getSysParam(conn, comp_id, "WORK_HOURS");
		this.Other_Leave_time=this.getPrintSQL(conn, "", "", this.month_start_date, this.month_end_date, dayhours, "0006", comp_id, "02",new HashMap());
		List Other_Leave_time_count_list=this.getPrintSQL(conn, "", "", this.month_start_date, this.month_end_date, dayhours, "0006", comp_id, "01",new HashMap());
		this.Other_Leave_time_count=Integer.valueOf(Other_Leave_time_count_list.get(0).toString());
//=======================請假、考勤異常次數與資料=====================================================		

		//樣板一頁總列數
		super.PageRowCount = 51;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 1;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal= new long[10][10];
			
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 4;
		
		//標題設定標題資料個數
		setHeadSize(headsize);
		
//		設定request
		super.request = request;
			
		//開始產生Excel
		super.init(conn,rpt,SC0030_01);
	}
	
	
	/** 
	 * 新的一頁開始之前(通常是丟頁計用的)
	 * 如第一頁要換頁前須先將第一頁的頁計寫入，然後才能進入第二頁
	 */
	public void BeforeChangePage() throws Exception{
		
	}
	
	/** 
	* 丟總計
	* 
	*/
	public void EndDocument() throws Exception{
		
	}
	
	/**
	 * 執行列印
	 * @param conn
	 * @param printlist
	 * @param EHF030600T0_U
	 * @param comp_id
	 * @return
	 */
	public int print(Connection conn, List printlist, String EHF030600T0_U, String comp_id ){
		List print_BigList = new LinkedList<String>();//存放多於25筆資料的人員
		List print_SmallList = new LinkedList<String>();//存放少於25筆資料的人員
		int count=0;
		int DataCount = 0;
		try{
			//Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			//ResultSet rs = null;
			//String sql = "";
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			// 取得部門資訊
			Map depMap = hr_tools.getDepNameMap(comp_id);

			// 取得員工資訊
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
			
			Iterator it = printlist.iterator();
			Map printMap = new HashMap();
			
			while(it.hasNext()){
				
				printMap = new HashMap();
				EHF030600M1F ehf030600m1 = (EHF030600M1F) it.next();
				
				// 傳入指定日期作為考勤年月的第一天, dateformat = 'yyy/MM/dd'
				String month_start_date = tools.convertADDateToStrng(tools.getFirstMonthDay(tools.covertStringToCalendar(this.month_start_date)));
				// 傳入指定日期作為考勤年月的最後一天, dateformat = 'yyy/MM/dd'
				String month_end_date = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(this.month_end_date)));
				//取得系統參數設定的一天工作時數
				String dayhours = tools.getSysParam(conn, comp_id, "WORK_HOURS");

				String[] name =ehf030600m1.getEHF030600T1_03().split("/");

				printMap.put("部門代號", name[0].trim());
				printMap.put("員工工號", name[2].trim());
				//printMap.put("部門", name[0].trim()+"/"+name[1].trim());
				printMap.put("部門", (String) ((Map)depMap.get(name[0].trim())).get("SHOW_DEPT_ID")+"/"+name[1].trim());
				//printMap.put("員工", name[2].trim()+"/"+name[3].trim());
				printMap.put("員工", (String) ((Map)empMap.get(name[2].trim())).get("EMPLOYEE_ID")+"/"+name[3].trim());
				printMap.put("本薪金額", ehf030600m1.getEHF030600T1_04());
				printMap.put("其他費用補款", ehf030600m1.getEHF030600T1_05());
				printMap.put("應領合計", ehf030600m1.getEHF030600T1_071());
				printMap.put("請假扣薪金額", ehf030600m1.getEHF030600T1_041());
 				printMap.put("其他費用扣款", ehf030600m1.getEHF030600T1_05_D());
				printMap.put("福利金", ehf030600m1.getEHF030600T1_19());
				printMap.put("薪資所得代扣", ehf030600m1.getEHF030600T1_06());
				printMap.put("代扣勞保費", ehf030600m1.getEHF030600T1_15());
				printMap.put("代扣健保費", ehf030600m1.getEHF030600T1_13());
				printMap.put("勞退自提", ehf030600m1.getEHF030600T1_17());
				printMap.put("應扣合計", ehf030600m1.getEHF030600T1_072());
				printMap.put("實發金額", ehf030600m1.getEHF030600T1_07());
				printMap.put("備註", ehf030600m1.getEHF030600T1_04_DESC()+", "+ehf030600m1.getEHF030600T1_041_DESC());
				printMap.put("加班費", ehf030600m1.getEHF030600T1_10());
				printMap.put("差旅費", ehf030600m1.getEHF030600T1_11());
				printMap.put("不休假加班費", ehf030600m1.getEHF030600T1_12());
				printMap.put("全勤獎金", ehf030600m1.getEHF030600T1_20());
				printMap.put("在職天數", ehf030600m1.getEHF030600T1_21());
				printMap.put("出勤天數", ehf030600m1.getEHF030600T1_22());
				printMap.put("公休天數", ehf030600m1.getEHF030600T1_23());
				
				
				/*無法由排班表計算
				int workday=0;
				int Vacation=0;
				// 取得上班天數 與休假天數
				String sql_02 = this.getWorking_daysSQL(name[2].trim(), month_start_date,month_end_date, comp_id);
				Statement stmt_02 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet rs_02 = stmt_02.executeQuery(sql_02);
				
				
				while (rs_02.next()) {
					workday += rs_02.getInt("上班天數");
					Vacation += rs_02.getInt("休假天數");
				}
				rs_02.close();
				stmt_02.close();
				printMap.put("出勤天數", workday);//應出勤天數
				printMap.put("公休天數", Vacation);
				*/
				
				Map Add_items=this.Add_items(conn,printMap,comp_id,printlist,ehf030600m1.getEHF030600T1_U());
				
				//薪資(加項)
				this.SELECT_EHF030600T3(conn,printMap,ehf030600m1,comp_id,"01",">");
				//薪資扣項
				this.SELECT_EHF030600T3(conn,printMap,ehf030600m1,comp_id,"02","<");
				
				this.SELECT_EHF030601T1(conn, printMap, ehf030600m1, comp_id, "01", ">");
				
				this.SELECT_EHF030601T1(conn, printMap, ehf030600m1, comp_id, "02", "<");

				//取得請假的時數與次數 + 取得遲到早退 曠職時數與次數
				this.getPrintSQL(conn, name[0].trim(), name[2].trim(), month_start_date, month_end_date, dayhours, "0006", comp_id, "03", printMap);

				//取得個人總加班時數、次數，換休、換錢次數
				this.getALLOvertime(conn,name[0].trim(), name[2].trim(), month_start_date, month_end_date, comp_id, printMap);
				
				//剩餘假別時數清單
				this.getResidueVacation(conn,name[0].trim(), name[2].trim(), month_start_date, month_end_date, comp_id, printMap);
				
				//找出投保人數(回傳不含本人的人數)
				this.SELECT_EHF030300T0_11(conn, name[0].trim(), name[2].trim(), comp_id, printMap);
				
				List Additems=(List)printMap.get("Additems");
				List OTHER_Plus = (List) printMap.get("OTHER_Plus");
				List OTHER_Allowance_Plus = (List) printMap.get("OTHER_Allowance_Plus");
				
				List Reduction=(List)printMap.get("Reduction");
				List OTHER_Minus= (List) printMap.get("OTHER_Minus");
				List OTHER_Allowance_Minus =(List) printMap.get("OTHER_Allowance_Minus");
				
				List Other_Leave_time_count_list =(List)printMap.get("Other_Leave_time_count_list");
				
				float HOURS=0;//請假時數
				float DAYS =0;//請假天數
				
				
				
				if(Other_Leave_time_count_list.size()>0){
					for (int i = 0; i < Other_Leave_time_count_list.size(); i++) {
						List aa = (List) Other_Leave_time_count_list.get(i);
						
						if("異常出勤".equals(String.valueOf(aa.get(0)))){
							//是否計算考勤
							if (this.SELECT_EHF020403T0_05(conn, comp_id, (String)printMap.get("員工工號"), (String)printMap.get("部門代號"))) {
								//要計算考勤
								//this.setDetail(count , "E", String.valueOf(aa.get(1)), false);
								//this.setDetail(count , "F", String.valueOf(aa.get(3))+"次/"+String.valueOf(aa.get(2)), true); 
								//this.objTotal[0][1] += Integer.valueOf(String.valueOf(aa.get(1)));
								int sss=1 ;
							}	
						}else{
							//this.setDetail(count , "E", String.valueOf(aa.get(1)), false);
							//this.setDetail(count , "F", String.valueOf(aa.get(3))+"次/"+String.valueOf(aa.get(2)), true); 
							//this.objTotal[0][1] += Integer.valueOf(String.valueOf(aa.get(1)));
							/*HOURS+=(Float.parseFloat((String)aa.get(4)));//請假時數
							DAYS+=Float.parseFloat((String)aa.get(5));*///請假天數
							HOURS+=(Float.parseFloat((String)aa.get(6)));//請假時數
							DAYS+=Float.parseFloat((String)aa.get(7));//請假天數
						}
						
						
					}
				}
				
				float va_hour = (DAYS * Float.parseFloat(dayhours)) + HOURS;
				float real_work_day = (((Float)printMap.get("出勤天數") * Float.parseFloat(dayhours)) - va_hour)/Float.parseFloat(dayhours);
				
				//printMap.put("實際出勤天數", workday-DAYS);//實際出勤天數
				printMap.put("實際出勤天數", tools.ROUND_HALF_UP(String.valueOf(real_work_day), 1));//實際出勤天數
				
				int tatle_Plus=0;
				int tatle_Minus=0;

				tatle_Plus=Additems.size()+OTHER_Plus.size()+OTHER_Allowance_Plus.size();//所有加項的總數量
				tatle_Minus=Reduction.size()+OTHER_Minus.size()+OTHER_Allowance_Minus.size();//所有扣項的總數量

				int [] numbers = new int[3] ;
				numbers[0] = tatle_Plus;
				numbers[1] = tatle_Minus;
				numbers[2] = Other_Leave_time_count_list.size();
				
				

				if((Integer)printMap.get("本薪金額")>0){
					numbers[0]+=1;
				}

				if((Integer)printMap.get("全勤獎金")>0){
					numbers[0]+=1;
				}
				
				if((Integer)printMap.get("其他費用補款")>0){
					numbers[0]+=1;
				}
				
				if((Integer) printMap.get("應領合計")>0){
					numbers[0]+=1;
				}

				
				if((Integer) printMap.get("代扣勞保費")>0){
					numbers[1]+=1;
				}
				if((Integer) printMap.get("代扣健保費")>0){
					numbers[1]+=1;
				}
				
				
				if((Integer)printMap.get("其他費用扣款")>0){
					numbers[1]+=1;
				}
				if((Integer) printMap.get("應扣合計")>0){
					numbers[1]+=1;
				}
				
				if((Integer)printMap.get("總加班時數")>0){
					numbers[2]+=1;
				}
				
				if((Integer)printMap.get("實發金額")>0){
					numbers[2]+=1;
				}

				Arrays.sort(numbers);//重新排序
				printMap.put("key", numbers[2]+"");
				if(numbers[2]>=16){
					print_BigList.add(printMap);
				}else{
					print_SmallList.add(printMap);
				}
			}
			
			if (print_BigList.size() > 0) {
				for (int i = 0; i < print_BigList.size(); i++) {
					printMap = new HashMap();
					printMap = (Map) print_BigList.get(i);
					//建立新頁面
					this.newPage();
					this.printDetail(conn, printMap, comp_id, 1);
					count++;
					DataCount++;
				}
			}
			
			if (print_SmallList.size() > 0) {
				for (int i = 0; i < print_SmallList.size(); i++) {
					printMap = new HashMap();
					printMap = (Map) print_SmallList.get(i);
					//建立新頁面
					this.newPage_Small();
					this.printDetail(conn, printMap, comp_id, 2);
					count++;
					DataCount++;
					if(!(i+1>=print_SmallList.size())){
						printMap = new HashMap();
						printMap = (Map) print_SmallList.get(i+1);
						this.printDetail(conn, printMap, comp_id, 3);
						count++;
						DataCount++;
						i++;
					}
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return DataCount;
	}
	/**
	 * 列印表頭資料
	 * @param salYYMM
	 * @param costYYMM
	 * @param print_date
	 * @param comp_name
	 * @param report_name
	 */
	public void printHeadValue( String salYYMM, String costYYMM, String print_date, String comp_name, String report_name ){
		try{
			this.salYYMM=salYYMM;
			this.setHeadValue(1,1,"A",comp_name+"",false,""); //列印公司抬頭
			this.setHeadValue(2,2,"A",report_name+"",false,""); //報表名稱
			this.setHeadValue(3,3,"B",salYYMM+"",false,"");  //薪資年月
			//this.setHeadValue(1,4,"A","入扣帳年月:"+costYYMM+"",false,"");  //入扣帳年月
			this.setHeadValue(4,3,"E",print_date+"",false,"");  //列印日期			
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	public void printDetail( Connection conn, Map dataMap , String comp_id ,int type){
		int count=4;
		int number_1=0;
		int number_2=0;
		int number_3=0;
		try{
			String Remark="";//紀錄備註欄資料
			
			Remark+="眷屬加保人數："+dataMap.get("眷屬加保人數")+"";
			
			
			if((Integer)dataMap.get("加班換錢時數")>0){
				if(!"".equals(Remark)){
					Remark+=",";
				}
				Remark+="加班換錢時數："+(Integer)dataMap.get("加班換錢時數");
			}
			if((Integer)dataMap.get("加班換休時數")>0){
				if(!"".equals(Remark)){
					Remark+=",";
				}
				Remark+="加班換休時數："+(Integer)dataMap.get("加班換休時數");
			}
			
			//取得剩餘假別時數清單
			List ResidueVacation=(List)dataMap.get("ResidueVacation");
			
			if(ResidueVacation.size()>0){
				if(!"".equals(Remark)){
					Remark+=",";
				}
				Remark+="剩餘休假時數：";
				for(int i=0;i<ResidueVacation.size();i++){
					List Vacation=(List) ResidueVacation.get(i);
					if(Float.parseFloat(Vacation.get(1)+"")>0){
						Remark+=Vacation.get(0)+":"+Vacation.get(1)+" ";
					}
				}
			}

			if((Integer)dataMap.get("地方政府補助款")>0){
				if(!"".equals(Remark)){
					Remark+=",";
				}
				Remark+="地方政府補助款："+dataMap.get("地方政府補助款");
				
			}
			
			String ALLsupplementdeduct=this.getALLsupplementdeduct(conn,dataMap,comp_id);//新增補扣款明細於備註欄。
			
			Remark+=ALLsupplementdeduct;
			
			
		
			//列印報表每筆資料
			//this.setHeadValue(5,4,"I","頁        次:"+(this.getNowPageNum()+1)+"",false,"");
			switch(type){
			case 1:
				//印資料筆數大於25的
				//this.next();  //換下一行
				for (int i = 0; i < Integer.valueOf((String)dataMap.get("key")); i++) {
					super.CopyCellFormat(9 + i , "A", 7, "D");// 複製格式
					super.CopyCellFormat(9 + i , "B", 7, "D");// 複製格式
					super.CopyCellFormat(9 + i , "C", 7, "D");// 複製格式
					super.CopyCellFormat(9 + i , "D", 7, "D");// 複製格式
					super.CopyCellFormat(9 + i , "E", 7, "E");// 複製格式
					super.CopyCellFormat(9 + i , "F", 7, "F");// 複製格式
				}
				this.mergeCell(Integer.valueOf((String)dataMap.get("key"))+7, "A", Integer.valueOf((String)dataMap.get("key"))+8, "A", "備註",2);// 合併儲存格
				this.Alignment=jxl.format.Alignment.LEFT;
				this.VerticalAlignment=jxl.format.VerticalAlignment.JUSTIFY;
				this.mergeCell(Integer.valueOf((String)dataMap.get("key"))+7, "B", Integer.valueOf((String)dataMap.get("key"))+8, "F", Remark,2);// 合併儲存格
				
				break;
			case 2:
				//印資料筆數小於25的
				//this.next_Small();  //換下一行
				for (int i = 0; i < Integer.valueOf((String)dataMap.get("key")); i++) {
					super.CopyCellFormat(9 + i , "A", 7, "D");// 複製格式
					super.CopyCellFormat(9 + i , "B", 7, "D");// 複製格式
					super.CopyCellFormat(9 + i , "C", 7, "D");// 複製格式
					super.CopyCellFormat(9 + i , "D", 7, "D");// 複製格式
					super.CopyCellFormat(9 + i , "E", 7, "E");// 複製格式
					super.CopyCellFormat(9 + i , "F", 7, "F");// 複製格式
				}
				
				this.mergeCell(Integer.valueOf((String)dataMap.get("key"))+7, "A", Integer.valueOf((String)dataMap.get("key"))+8, "A", "備註",2);// 合併儲存格
				this.Alignment=jxl.format.Alignment.LEFT;
				this.VerticalAlignment=jxl.format.VerticalAlignment.JUSTIFY;
				this.mergeCell(Integer.valueOf((String)dataMap.get("key"))+7, "B", Integer.valueOf((String)dataMap.get("key"))+8, "F", Remark,2);// 合併儲存格
				break;
			case 3:
				//印資料筆數小於25的
				//this.next_Small();  //換下一行
				
				for (int i = 1; i < Integer.valueOf((String)dataMap.get("key"))+7; i++) {
					
					if(i==5){
						super.CopyCellFormat(27 + i , "A", 5, "A");// 複製格式
						super.CopyCellFormat(27 + i , "B", 5, "B");// 複製格式
						super.CopyCellFormat(27 + i , "C", 5, "C");// 複製格式
						super.CopyCellFormat(27 + i , "D", 5, "D");// 複製格式
						super.CopyCellFormat(27 + i , "E", 5, "E");// 複製格式
						super.CopyCellFormat(27 + i , "F", 5, "F");// 複製格式
						continue; 
					}
					if(i==5){
						super.CopyCellFormat(27 + i , "A", 6, "A");// 複製格式
						super.CopyCellFormat(27 + i , "B", 6, "B");// 複製格式
						super.CopyCellFormat(27 + i , "C", 6, "C");// 複製格式
						super.CopyCellFormat(27 + i , "D", 6, "D");// 複製格式
						super.CopyCellFormat(27 + i , "E", 6, "E");// 複製格式
						super.CopyCellFormat(27 + i , "F", 6, "F");// 複製格式
						continue; 
					}
					
					super.CopyCellFormat(27 + i , "A", 7, "D");// 複製格式
					super.CopyCellFormat(27 + i , "B", 7, "D");// 複製格式
					super.CopyCellFormat(27 + i , "C", 7, "D");// 複製格式
					super.CopyCellFormat(27 + i , "D", 7, "D");// 複製格式
					super.CopyCellFormat(27 + i , "E", 7, "E");// 複製格式
					super.CopyCellFormat(27 + i , "F", 7, "F");// 複製格式
				}
				this.setDetail(27, "A","薪資年月",false);
				this.setDetail(27, "D","列印日期",false);
				this.createFont=WritableFont.createFont("新細明體");
				this.size=20;
				this.mergeCell(28, "A", 28, "F", this.comp_name,1);// 合併儲存格
				this.createFont=WritableFont.createFont("新細明體");
				this.size=14;
				this.mergeCell(29, "A", 29, "F", "個  人  薪  資  條",2);// 合併儲存格
				this.createFont=WritableFont.createFont("新細明體");
				this.size=12;
				this.mergeCell(30, "B", 30, "C", this.salYYMM,2);// 合併儲存格
				this.mergeCell(30, "E", 30, "F", this.print_date,2);// 合併儲存格		
				this.mergeCell(28+Integer.valueOf((String)dataMap.get("key"))+6, "A",28+ Integer.valueOf((String)dataMap.get("key"))+7, "A", "備註",2);// 合併儲存格
				this.Alignment=jxl.format.Alignment.LEFT;
				this.VerticalAlignment=jxl.format.VerticalAlignment.JUSTIFY;
				this.mergeCell(28+Integer.valueOf((String)dataMap.get("key"))+6, "B",28+ Integer.valueOf((String)dataMap.get("key"))+7, "F", Remark,2);// 合併儲存格
				count+=27;
				break;
			}		
			this.Alignment=jxl.format.Alignment.CENTRE;//把水平對齊方式指定為居中 
			this.VerticalAlignment=jxl.format.VerticalAlignment.CENTRE;//把垂直對齊方式指定為居中 
			
			
			if (type == 3) {
				this.setDetail(28,"A","員工姓名",false);
				this.setDetail(28,"B",(String)dataMap.get("員工"),false);
				
				this.setDetail(28,"C","職稱",false);
				this.setDetail(28,"D","",false);
				
				this.setDetail(28,"E","部門",false);
				this.setDetail(28,"F",(String)dataMap.get("部門"),false);
				
				this.setDetail(29, "B",dataMap.get("出勤天數")+"",true);
				this.setDetail(29, "D",dataMap.get("公休天數")+"",true);
				this.setDetail(29, "F",dataMap.get("實際出勤天數")+"",true);
				
				//this.setDetail(29, "F",dataMap.get("眷屬加保人數")+"",true);
				
				super.colour=Colour.GRAY_25;
				this.setDetail(30, "A","薪資加項",false);
				this.setDetail(30, "B","金額",false);
				this.setDetail(30, "C","薪資減項",false);
				this.setDetail(30, "D","金額",false);
				this.setDetail(30, "E","差勤紀錄",false);
				this.setDetail(30, "F","次數/天數",false);
				super.colour=Colour.BLACK;
				if((Integer)dataMap.get("本薪金額")>0){
					this.setDetail(count,"A", "本薪金額",false);
					this.setDetail(count,"B", (Integer)dataMap.get("本薪金額")+"",true);
					this.objTotal[0][0] += (Integer)dataMap.get("本薪金額");
					count++;
				}
				if((Integer)dataMap.get("全勤獎金")>0){
					this.setDetail(count,"A", "全勤獎金",false);
					this.setDetail(count,"B", (Integer)dataMap.get("全勤獎金")+"",true);
					this.objTotal[0][0] += (Integer)dataMap.get("全勤獎金");
					count++;
				}
				
				
			} else {
				this.setDetail(1, "B", (String) dataMap.get("員工"), false);
				this.setDetail(1, "D", "", false);
				this.setDetail(1, "F", (String) dataMap.get("部門"), false);
				if((Integer)dataMap.get("本薪金額")>0){
					this.setDetail(count,"A", "本薪金額",false);
					this.setDetail(count,"B", (Integer)dataMap.get("本薪金額")+"",true);  
					this.objTotal[0][0] += (Integer)dataMap.get("本薪金額");
					count++;
				}
				if((Integer)dataMap.get("全勤獎金")>0){
					this.setDetail(count,"A", "全勤獎金",false);
					this.setDetail(count,"B", (Integer)dataMap.get("全勤獎金")+"",true);
					this.objTotal[0][0] += (Integer)dataMap.get("全勤獎金");
					count++;
				}
				this.setDetail(2, "B",dataMap.get("出勤天數")+"",false);
				this.setDetail(2, "D",dataMap.get("公休天數")+"",false);
				this.setDetail(2, "F",dataMap.get("實際出勤天數")+"",true);
				//this.setDetail(2, "F",dataMap.get("眷屬加保人數")+"",true);

			}

			List Additems=(List)dataMap.get("Additems");
			if(Additems.size()>0){
				for (int i = 0; i < Additems.size(); i++) {
					List Additem = (List) Additems.get(i);
					if((Integer) dataMap.get(Additem.get(0))>0){
						this.setDetail(count , "A", Additem.get(0).toString(),false);
						this.setDetail(count , "B", (Integer) dataMap.get(Additem.get(0))+ "", true);
						this.objTotal[0][0] += (Integer) dataMap.get(Additem.get(0));
						count++;
					}
				}
			}
			
			List OTHER_Plus = (List) dataMap.get("OTHER_Plus");
			for (int i = 0; i < OTHER_Plus.size(); i++) {
				List aa = (List) OTHER_Plus.get(i);
				this.setDetail(count , "A", String.valueOf(aa.get(0)), false); // 
				this.setDetail(count , "B", String.valueOf(aa.get(1)), true); // 全勤獎金
				this.objTotal[0][0] += Integer.valueOf(String.valueOf(aa.get(1)));

				count++ ;
			}
			
			List OTHER_Allowance_Plus = (List) dataMap.get("OTHER_Allowance_Plus");
			for (int i = 0; i < OTHER_Allowance_Plus.size(); i++) {
				List aa = (List) OTHER_Allowance_Plus.get(i);
				this.setDetail(count , "A", String.valueOf(aa.get(0)), false); // 
				this.setDetail(count , "B", String.valueOf(aa.get(1)), true); // 全勤獎金
				this.objTotal[0][0] += Integer.valueOf(String.valueOf(aa.get(1)));
				count++ ;
			}
			
			if ((Integer) dataMap.get("其他費用補款") > 0) {
				this.setDetail(count, "A", "其他補款", false);
				this.setDetail(count, "B", (Integer) dataMap.get("其他費用補款") + "", true); // 10
				this.objTotal[0][0] += (Integer) dataMap.get("其他費用補款");
				count++;
			}
			number_1=count;
			

			count=4;
			if(type==3){
				count+=27;
			}
			if((Integer)dataMap.get("代扣勞保費")>0){
				this.setDetail(count, "C", "代扣勞保費", false); // 代扣勞保費
				this.setDetail(count, "D", (Integer)dataMap.get("代扣勞保費")+"",true);  //代扣勞保費
				this.objTotal[0][1] += (Integer) dataMap.get("代扣勞保費");
				count++;
			}
			
			if((Integer)dataMap.get("代扣健保費")>0){
				this.setDetail(count, "C", "代扣健保費", false); // 代扣健保費
				this.setDetail(count, "D", (Integer)dataMap.get("代扣健保費")+"",true);  //代扣健保費//13
				this.objTotal[0][1] += (Integer) dataMap.get("代扣健保費");
				count++;
			}
				
			
			List Reduction=(List)dataMap.get("Reduction");
			if(Reduction.size()>0){
				for (int i = 0; i < Reduction.size(); i++) {
					List Reduction_list = (List) Reduction.get(i);
					if(Integer.valueOf((String)Reduction_list.get(1))>0){
						this.setDetail(count, "C", Reduction_list.get(0).toString(),false);
						this.setDetail(count, "D", Reduction_list.get(1)+ "", true);
						this.objTotal[0][1] += Integer.valueOf((String)Reduction_list.get(1));
						count++;
					}
				}
			}
			List OTHER_Minus= (List) dataMap.get("OTHER_Minus");
			if(OTHER_Minus.size()>0){
				for (int i = 0; i < OTHER_Minus.size(); i++) {
					List aa = (List) OTHER_Minus.get(i);
					this.setDetail(count , "C", String.valueOf(aa.get(0)), false); 
					this.setDetail(count , "D", Math.abs(Integer.valueOf((String)aa.get(1)))+"", true); 
					this.objTotal[0][1] += Math.abs(Integer.valueOf((String)aa.get(1)));
					count++ ;
				}
			}
			
			List OTHER_Allowance_Minus =(List) dataMap.get("OTHER_Allowance_Minus");
			if(OTHER_Allowance_Minus.size()>0){
				for (int i = 0; i < OTHER_Allowance_Minus.size(); i++) {
					List aa = (List) OTHER_Allowance_Minus.get(i);
					this.setDetail(count , "C", String.valueOf(aa.get(0)), false); 
					this.setDetail(count , "D", Math.abs(Integer.valueOf((String)aa.get(1)))+"", true); 
					this.objTotal[0][1] += Math.abs(Integer.valueOf((String)aa.get(1)));
					count++ ;
				}
			}
			if((Integer)dataMap.get("其他費用扣款")>0){
				this.setDetail(count, "C", "其他扣款", false); //
				this.setDetail(count, "D", (Integer)dataMap.get("其他費用扣款")+"",true);
				this.objTotal[0][1] += (Integer)dataMap.get("其他費用扣款");
				count++ ;
			}
			number_2=count;
			
			
			count=4;
			
			if(type==3){
				count+=27;
			}
			
			if((Integer)dataMap.get("總加班時數")>0){
				this.setDetail(count, "E", "總加班時數", false); // 
				this.setDetail(count, "F", (Integer)dataMap.get("總加班次數")+"次/"+(Integer)dataMap.get("總加班時數")+"小時",true);  //應扣合計
				//this.objTotal[0][1] += (Integer)dataMap.get("總加班時數");
				count++ ;
			}
			
			
			List Other_Leave_time_count_list =(List)dataMap.get("Other_Leave_time_count_list");
			if(Other_Leave_time_count_list.size()>0){
				for (int i = 0; i < Other_Leave_time_count_list.size(); i++) {
					List aa = (List) Other_Leave_time_count_list.get(i);
					
					if("異常出勤".equals(String.valueOf(aa.get(0)))){
						//是否計算考勤
						if (this.SELECT_EHF020403T0_05(conn, comp_id, (String)dataMap.get("員工工號"), (String)dataMap.get("部門代號"))) {
							//要計算考勤
							this.setDetail(count , "E", String.valueOf(aa.get(1)), false);
							this.setDetail(count , "F", String.valueOf(aa.get(3))+"次/"+String.valueOf(aa.get(2)), true); 
							//this.objTotal[0][1] += Integer.valueOf(String.valueOf(aa.get(1)));
							count++ ;
						}	
					}else{
						this.setDetail(count , "E", String.valueOf(aa.get(1)), false);
						this.setDetail(count , "F", String.valueOf(aa.get(3))+"次/"+String.valueOf(aa.get(2)), true); 
						//this.objTotal[0][1] += Integer.valueOf(String.valueOf(aa.get(1)));
						count++ ;
					}
					
					
				}
			}
			number_3=count;
			
			int [] change = new int[3] ;
			change[0] = number_1;
			change[1] = number_2;
			change[2] = number_3;
			Arrays.sort(change);//重新排序
			if ((Integer) dataMap.get("應領合計") > 0) {
				this.setDetail(change[2], "A", "應發金額", false); 
				this.setDetail(change[2], "B", this.objTotal[0][0]+"",true); //11
				number_1=count;
				count++;
			}
			if((Integer)dataMap.get("應扣合計")>0){
				this.setDetail(change[2], "C", "應扣金額", false); // 
				this.setDetail(change[2], "D", this.objTotal[0][1]+"",true);  //應扣合計
				count++ ;
			}
			
			
			if((Integer)dataMap.get("實發金額")>0){
				this.setDetail(change[2], "E", "實發金額", false); // 
				this.setDetail(change[2], "F",	this.objTotal[0][0]-this.objTotal[0][1]+"",true);  //應扣合計
				//this.objTotal[0][1] += (Integer)dataMap.get("實發金額");
				this.objTotal[0][0] = 0 ;
				this.objTotal[0][1] = 0;
				count++ ;
			}
			
			
			
			/*
			if (type.equals("C")) {
				this.mergeCell(count+4, "A", count+4,"B", "實發金額");// 合併儲存格
				//this.setDetail(count, "B", "實發金額", false); // 
			}
			this.setDetail(count , type, (Integer)dataMap.get("實發金額")+"",true);  //應扣合計
			this.objTotal[0][number] += (Integer)dataMap.get("實發金額");
			this.objTotal[1][number] += (Integer)dataMap.get("實發金額");
			
			this.MUNBER_MAX=count-8;
			number++;
			count++ ;

			if (type.equals("C")) {
				this.mergeCell(count+4, "A", count+4,"B", "蓋章");// 合併儲存格
				//this.setDetail(count, "B", "蓋章", false); // 
				count++ ;
			}
			
			if (type.equals("C")) {
				this.mergeCell(count+4, "A", count+4,"B", "備註");// 合併儲存格
				//this.setDetail(count, "B", "備註", false); //
				count++ ;
			}
*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 搜尋全部加項的總數量(除了補款)
	 * @param conn
	 * @param form 
	 * @param compId 
	 * @param printlist 
	 * @return
	 */
	private List SELECT_Salary_Project_Count(Connection conn, EHF030600M0F form, String compId, List printlist,String Compare,String type) {
		// TODO Auto-generated method stub
		int i = 0;
		List All_dataList = new LinkedList<String>();
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			Iterator it = printlist.iterator();
			String UID="";
			while(it.hasNext()){
				EHF030600M1F ehf030600m1 = (EHF030600M1F) it.next();
				UID=ehf030600m1.getEHF030600T1_01();
			}
			
			
			String sql = " ";
			
			if(type.equals("01")){
				sql+=" SELECT IFNULL(Count(A.ehf030101t0_02),0) AS 加項數量" +
					 " From(" ;
			}
			
			sql+="  SELECT  EHF030600T3_02, EHF030101t0_02, EHF030600T3_M1" +
			"  FROM EHF030600T3 LEFT JOIN ehf030101t0 ON " +
			"  EHF030600T3_05 = EHF030101T0_02" +
			"  AND EHF030600T3_07 = EHF030101T0_05" +
			"  WHERE" +
			"  1 = 1 " +
			" AND EHF030600T3_01 = '"+UID+"'" +
			" AND EHF030600T3_M1 = '"+form.getEHF030600T0_02()+"'" +
			" AND EHF030600T3_06 "+Compare+"0" +
			" AND EHF030600T3_07 = '"+compId+"'" +
			//" AND EHF030600T3_05 NOT LIKE '%職務津貼%'" +
			//" AND EHF030600T3_05 NOT LIKE '%伙食津貼%'" +
			//" AND EHF030600T3_05 NOT LIKE '%夜間津貼%'" +
			" AND EHF030600T3_05 NOT LIKE '%全勤獎金%'" +
			" GROUP BY ehf030101t0_02" ;
			
			
			if (type.equals("01")) {
				sql += " ) A WHERE 1 = 1";
			}
			
			sql+=" ";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {

				if (type.equals("01")) {
					i = rs.getInt("加項數量");
					All_dataList.add(i);
				}else{
					List dataList = new LinkedList<String>();
					dataList.add((String) rs.getString("EHF030101t0_02"));
					//dataList.add(String.valueOf(rs.getInt("EHF030600T3_06")));
					All_dataList.add(dataList);
				}

			}
			rs.close();
			stmt.close();
			

		} catch (Exception e) {
			e.printStackTrace();
		}

		return All_dataList;
	}
	
	private List SELECT_Allowance_Project_Count(Connection conn,EHF030600M0F form, String compId, List printlist, String Compare,String type) {
		// TODO Auto-generated method stub
		int i = 0;
		List All_dataList = new LinkedList<String>();
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = " ";
			if(type.equals("01")){
				sql+=" SELECT IFNULL(Count(A. EHF030601T1_03),0) AS 加項數量 From(" ;
				}
			sql+=" SELECT EHF030601T1_03" +
				" FROM" +
				" EHF030601T1" +
				" LEFT JOIN" +
				" EHF030601T0 ON EHF030601T0_U = EHF030601T1_U" +
				" AND EHF030601T0_07 = EHF030601T1_07" +
				" LEFT JOIN" +
				" EHF010101t0 ON EHF030601T1_02 = EHF010101T0_01" +
				" AND EHF030601T1_07 =EHF010101T0_23" +
				" WHERE 1 = 1 " +
				" AND EHF030601T1_07 = '"+compId+"'" +
				" AND EHF010101T0_06 "+Compare+"0" +
				" AND EHF030601T0_M = '"+form.getEHF030600T0_02()+"'" +
				" GROUP BY EHF030601T1_03";
				if (type.equals("01")) {
					sql += " ) A WHERE 1 = 1";
				}
		
				sql+=" ";
				rs = stmt.executeQuery(sql);
				while (rs.next()) {

					if (type.equals("01")) {
						i = rs.getInt("加項數量");
						All_dataList.add(i);
					}else{
						List dataList = new LinkedList<String>();
						dataList.add((String) rs.getString("EHF030601T1_03"));
						//dataList.add(String.valueOf(rs.getInt("EHF030600T3_06")));
						All_dataList.add(dataList);
					}

				}
				rs.close();
				stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return All_dataList;

	}

	/**
	 * 所有薪資加項資料
	 * @param conn
	 * @param printMap
	 * @param ehf030600m1
	 * @param comp_Id
	 */
	private void SELECT_EHF030600T3(Connection conn, Map printMap, EHF030600M1F ehf030600m1, String comp_Id,String type,String Compare) {
		// TODO Auto-generated method stub
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			//List dataList = new LinkedList<String>();
			List All_dataList = new LinkedList<String>();
			Map dataMap = new LinkedHashMap<String,String>();
			int i=0;
			
			String sql = "" +
			" SELECT EHF030600T3_05,EHF030600T3_06" +
			" FROM" +
			" EHF030600T3 WHERE 1 = 1 " +
			" AND EHF030600T3_01 = "+ehf030600m1.getEHF030600T1_01()+" " +  //薪資計算序號
			" AND EHF030600T3_02 = '"+ehf030600m1.getEHF030600T1_02()+"' " +  //員工工號
			" AND EHF030600T3_M1 = '"+this.day+"'" +//薪資年月
			" AND EHF030600T3_06 "+Compare+"0" +
			" AND EHF030600T3_07 = '"+comp_Id+"' " +  //公司代碼
			//" AND EHF030600T3_05 NOT LIKE '%職務津貼%'" +
			" AND EHF030600T3_05 NOT LIKE '%全勤獎金%'" +
			//" AND EHF030600T3_05 NOT LIKE '%伙食津貼%'" +
			//" AND EHF030600T3_05 NOT LIKE '%夜間津貼%'" +
			" ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				List dataList = new LinkedList<String>();
				dataList.add((String)rs.getString("EHF030600T3_05"));
				dataList.add(String.valueOf(rs.getInt("EHF030600T3_06")));
				All_dataList.add(dataList);
				i++;
			}
			if (type.equals("01")) {
				printMap.put("OTHER_COUNT_Plus", i);
				printMap.put("OTHER_Plus", All_dataList);
			} else if (type.equals("02")) {
				printMap.put("OTHER_COUNT_Minus", i);
				printMap.put("OTHER_Minus", All_dataList);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 取得薪資主檔中 加項與扣項的金額
	 * @param conn
	 * @param printMap 
	 * @param compId
	 * @param printlist
	 * @param type 1為全公司  2為個人
	 * @return
	 */
	private Map Add_items(Connection conn, Map printMap, String compId, List printlist, String type) {
		// TODO Auto-generated method stub
		List All_Additems = new LinkedList<String>();
		List All_Reduction = new LinkedList<String>();
		//Map printMap = new HashMap();
		
		int i=0;
		int j=0;
		try{
			
			Iterator it = printlist.iterator();
			String UID="";
			if(it.hasNext()){
				EHF030600M1F ehf030600m1 = (EHF030600M1F) it.next();
				UID=ehf030600m1.getEHF030600T1_01();
				
			}
			
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = "" +
			" SELECT " +
			" SUM(EHF030600T1_10) AS 加班費," +
			" SUM(EHF030600T1_12) AS 不休假加班費," +
			" SUM(EHF030600T1_11) AS 差旅費," +
			" SUM(EHF030600T1_20) AS 全勤獎金," +
			
			" SUM(EHF030600T1_041) AS 請假扣薪金額," +
			" SUM(EHF030600T1_06) AS 薪資所得扣繳金," +
			" SUM(EHF030600T1_17) AS 勞退自提," +
			" SUM(EHF030600T1_19) AS 福利金," +
			" SUM(EHF030600T1_25) AS 遲到扣薪金額," +
			" SUM(EHF030600T1_27) AS 早退扣薪金額," +
			" SUM(EHF030600T1_29) AS 曠職扣薪金額" +
			" FROM" +
			" EHF030600T1 LEFT JOIN" +
			" EHF030600T0 ON EHF030600T0_01 = EHF030600T1_01" +
			" AND EHF030600T0_14 = EHF030600T1_08" +
			" WHERE 1 = 1 " ;
			if("".equals(type)){
				sql+=" AND EHF030600T1_01 = "+UID+" " +  //薪資計算序號
					 " AND EHF030600T1_08 = '"+compId+"'" +
					 " GROUP BY EHF030600T0_01" ;
			}else{
				sql+=" AND EHF030600T1_U = '"+type+"' " +  //薪資計算序號
				 " AND EHF030600T1_08 = '"+compId+"'" +
				 "" ;
			}
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				if(rs.getInt("加班費")>0){
					List dataList = new LinkedList<String>();
					dataList.add("加班費");
					dataList.add(String.valueOf(rs.getInt("加班費")));
					All_Additems.add(dataList);
					i++;
				}
				if(rs.getInt("不休假加班費")>0){
					List dataList = new LinkedList<String>();
					dataList.add("不休假加班費");
					dataList.add(String.valueOf(rs.getInt("不休假加班費")));
					All_Additems.add(dataList);
					i++;
				}
				if(rs.getInt("差旅費")>0){
					List dataList = new LinkedList<String>();
					dataList.add("差旅費");
					dataList.add(String.valueOf(rs.getInt("差旅費")));
					All_Additems.add(dataList);
					i++;
				}
			/*	if(rs.getInt("全勤獎金")>0){
					List dataList = new LinkedList<String>();
					dataList.add("全勤獎金");
					dataList.add(String.valueOf(rs.getInt("全勤獎金")));
					All_Additems.add(dataList);
					i++;
				}*/
				if(rs.getInt("請假扣薪金額")>0){
					List dataList = new LinkedList<String>();
					dataList.add("請假扣薪金額");
					dataList.add(String.valueOf(rs.getInt("請假扣薪金額")));
					All_Reduction.add(dataList);
					j++;
				}
				if(rs.getInt("薪資所得扣繳金")>0){
					List dataList = new LinkedList<String>();
					dataList.add("薪資所得扣繳金");
					dataList.add(String.valueOf(rs.getInt("薪資所得扣繳金")));
					All_Reduction.add(dataList);
					j++;
				}
				if(rs.getInt("勞退自提")>0){
					List dataList = new LinkedList<String>();
					dataList.add("勞退自提");
					dataList.add(String.valueOf(rs.getInt("勞退自提")));
					All_Reduction.add(dataList);
					j++;
				}
				if(rs.getInt("福利金")>0){
					List dataList = new LinkedList<String>();
					dataList.add("福利金");
					dataList.add(String.valueOf(rs.getInt("福利金")));
					All_Reduction.add(dataList);
					j++;
				}
				if(rs.getInt("遲到扣薪金額")>0){
					List dataList = new LinkedList<String>();
					dataList.add("遲到扣薪金額");
					dataList.add(String.valueOf(rs.getInt("遲到扣薪金額")));
					All_Reduction.add(dataList);
					j++;
				}
				if(rs.getInt("早退扣薪金額")>0){
					List dataList = new LinkedList<String>();
					dataList.add("早退扣薪金額");
					dataList.add(String.valueOf(rs.getInt("早退扣薪金額")));
					All_Reduction.add(dataList);
					j++;
				}
				if(rs.getInt("曠職扣薪金額")>0){
					List dataList = new LinkedList<String>();
					dataList.add("曠職扣薪金額");
					dataList.add(String.valueOf(rs.getInt("曠職扣薪金額")));
					All_Reduction.add(dataList);
					j++;
				}
				printMap.put("Additems_count", i);
				printMap.put("Additems", All_Additems);
				printMap.put("Reduction_count", j);
				printMap.put("Reduction", All_Reduction);
				
				
			}

	}catch(Exception e){
		e.printStackTrace();
	}
		return printMap;
	}
	/**
	 * 所有"個人"使用的津貼項目
	 * @param conn
	 * @param printMap
	 * @param ehf030600m1
	 * @param comp_Id
	 */
	private void SELECT_EHF030601T1(Connection conn, Map printMap, EHF030600M1F ehf030600m1, String comp_Id,String type,String Compare) {
		// TODO Auto-generated method stub
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			//List dataList = new LinkedList<String>();
			List All_dataList = new LinkedList<String>();
			Map dataMap = new LinkedHashMap<String,String>();
			int i=0;
			
			String sql=" SELECT EHF030601T1_03, SUM(EHF030601T1_04+EHF030601T1_05) AS 總金額" +
			" FROM" +
			" EHF030601T1" +
			" LEFT JOIN" +
			" EHF030601T0 ON EHF030601T0_U = EHF030601T1_U" +
			" AND EHF030601T0_07 = EHF030601T1_07" +
			" LEFT JOIN" +
			" EHF010101t0 ON EHF030601T1_02 = EHF010101T0_01" +
			" AND EHF030601T1_07 =EHF010101T0_23" +
			" WHERE 1 = 1 " +
			" AND EHF030601T1_07 = '"+comp_Id+"'" +
			" AND EHF030601T0_01 = '"+ehf030600m1.getEHF030600T1_02()+"'" +
			" AND EHF010101T0_06 "+Compare+"0" +
			" AND EHF030601T0_M = '"+this.day+"'" +
			" GROUP BY EHF030601T0_01 , EHF030601T1_03";

			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				List dataList = new LinkedList<String>();
				dataList.add((String)rs.getString("EHF030601T1_03"));
				dataList.add(String.valueOf(rs.getInt("總金額")));
				All_dataList.add(dataList);
				i++;
			}
			if (type.equals("01")) {
				printMap.put("OTHER_Allowance_COUNT_Plus", i);
				printMap.put("OTHER_Allowance_Plus", All_dataList);
			} else if (type.equals("02")) {
				printMap.put("OTHER_Allowance_COUNT_Minus", i);
				printMap.put("OTHER_Allowance_Minus", All_dataList);
			}
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private List check(List ALL, List data) {
		// TODO Auto-generated method stub

		List return_dataList = new LinkedList<String>();
		
		if (data.size() == 0) {
			for (int a = 0; a < ALL.size(); a++) {
				List dataList = new LinkedList<String>();
				List ALL_DATA = (List) ALL.get(a);// 所有薪資資料
				dataList.add(ALL_DATA.get(0));// 獎金名稱
				dataList.add("0");// 獎金金額
				return_dataList.add(dataList);
				
			}
			return return_dataList;
		}
		for (int i = 0; i < ALL.size(); i++) {
			List dataList = new LinkedList<String>();
			List ALL_DATA = (List) ALL.get(i);// 所有薪資資料
			for (int J = 0; J < data.size(); J++) {
				List Match_DATA = (List) data.get(J);// 個人擁有薪資資料
				dataList = new LinkedList<String>();
				if (ALL_DATA.get(0).equals(Match_DATA.get(0))) {
					dataList.add(ALL_DATA.get(0));// 獎金名稱
					dataList.add(Match_DATA.get(1));// 獎金金額
					return_dataList.add(dataList);
					break;
				}
				if (J == data.size() - 1) {
					dataList.add(ALL_DATA.get(0));// 獎金名稱
					dataList.add("0");// 獎金金額
					return_dataList.add(dataList);
				}
			}

		}
		return return_dataList;
	}
	/**
	 * 取得請假的時數與次數
	 * 取得遲到早退 曠職時數與次數
	 * @param dept_id  		部門代號
	 * @param employee_id	員工工號
	 * @param start_date	開始時間
	 * @param end_date		結束時間
	 * @param dayhours		系統參數設定 一天工作時數
	 * @param form_status	處理狀態 (通常為0006  完成狀態)
	 * @param comp_id		公司代碼
	 * @param type
	 * @param printMap 
	 * @return
	 */
	protected List getPrintSQL(Connection conn,String dept_id, String employee_id, String start_date, String end_date, String dayhours,
			  //String form_type, 
			  String form_status, String comp_id ,String type, Map printMap  ){

		String sql = "";

		List All_dataList = new LinkedList<String>();
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			if(type.equals("01")){
				sql+="SELECT  IFNULL(Count(A.員工工號), 0) AS 總數量 From(" ;
				}
			sql += ""
					+ " SELECT "
					+ " *, "
					+ " CASE Att.TYPE "
					+ " WHEN 0 THEN "
					+ " CASE Att.類別代碼 "
					+ " WHEN '01' THEN (CONCAT(CAST(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/60)) AS CHAR(4)), '小時', CAST(MOD(SUM(Att.HOURS), 60) AS CHAR(6)), '分'))"
					+ " WHEN '02' THEN (CONCAT(CAST(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/60)) AS CHAR(4)), '小時', CAST(MOD(SUM(Att.HOURS), 60) AS CHAR(6)), '分'))  "
					+ " WHEN '03' THEN (CONCAT(CAST(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/"+ dayhours + ")) AS CHAR(4)), '天', CAST(MOD(SUM(Att.HOURS), "+ dayhours+ ") AS CHAR(6)), '小時')) "
					+ " END "
					+ " WHEN 1 THEN (CONCAT(CAST(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/"+ dayhours+ ")) AS CHAR(4)), '天', CAST(MOD(SUM(Att.HOURS), "+ dayhours+ ") AS CHAR(6)), '小時' ))"
					+ " WHEN 2 THEN (CONCAT(FLOOR(SUM(Att.DAYS)+(SUM(Att.HOURS)/"+ dayhours+ ")), '天', MOD(SUM(Att.HOURS), "+ dayhours+ "), '小時' )) "
					+ " WHEN 3 THEN (CONCAT(FLOOR(SUM(Att.DAYS)), '次')) "
					+ " WHEN 4 THEN (CONCAT(FLOOR(SUM(Att.DAYS)), '次')) END AS 總計 ,  CASE Att.TYPE WHEN 0 THEN " 
					+ " CASE Att.類別代碼 "
					+ " WHEN  '01' THEN COUNT(Att.類別代碼)"
					+ " WHEN  '02' THEN COUNT(Att.類別代碼 )"
					+ " WHEN  '03' THEN COUNT(Att.類別代碼)"
					+ " END " 
					+ " WHEN 1" 
					+ " THEN COUNT(Att.類別代碼)" 
					+ "	WHEN 4" 
					+ " THEN COUNT(Att.類別代碼)END AS 總次,"
					+ " SUM(Att.DAYS) AS 總天數,"   
					+ " SUM(Att.HOURS) AS 總時數" +
				" FROM ( ";

			// 增加出勤異常的資料 --> 遲到、早退、曠職資訊 edit by joe 2013/05/30
			sql += "" + " SELECT "
					+ " tableA.單號, tableA.部門代號, tableA.員工工號, tableA.單據別, "
					+ " tableA.ATT_DATE, "
					+ " tableA.TYPE, tableA.類別代碼, tableA.類別, "
					+ " tableA.DAYS, tableA.HOURS " + " FROM " + " ( " + " ( "
					+ " SELECT "
					+ " EHF020410T0_01 AS 單號, EHF020403T0_02 AS 部門代號, "
					+ " EHF020410T0_01 AS 員工工號, " + " '異常出勤' AS 單據別, "
					+ " EHF020410T0_03 AS ATT_DATE, " + " 0 AS TYPE, "
					+ " EHF020410T0_06 AS 類別代碼, " + " CASE EHF020410T0_06 "
					+ " WHEN '00' THEN '正常' " + " WHEN '01' THEN '遲到' "
					+ " WHEN '02' THEN '早退' " + " WHEN '03' THEN '曠職' "
					+ " WHEN '04' THEN '未打卡' " + " END AS 類別, "
					+ " CASE EHF020410T0_06 " + " WHEN '00' THEN 0 "
					+ " WHEN '01' THEN FLOOR(EHF020410T0_07/(60*60)) "
					+ " WHEN '02' THEN FLOOR(EHF020410T0_07/(60*60)) "
					+ " WHEN '03' THEN FLOOR(EHF020410T0_07/(60*60*"
					+ dayhours
					+ ")) "
					+ " WHEN '04' THEN 0 "
					+ " END AS DAYS, "
					+ " CASE EHF020410T0_06 "
					+ " WHEN '00' THEN 0 "
					+ " WHEN '01' THEN TRUNCATE(MOD(EHF020410T0_07/60,(60)), 1) "
					+ " WHEN '02' THEN TRUNCATE(MOD(EHF020410T0_07/60,(60)), 1) "
					+ " WHEN '03' THEN TRUNCATE(MOD(EHF020410T0_07/(60*60),"
					+ dayhours
					+ "), 1) "
					+ " WHEN '04' THEN 0 "
					+ " END AS HOURS "
					+ " FROM "
					+ " EHF020410T0 "
					+ " LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020410T0_01 "
					+ " AND EHF020403T0_04 = EHF020410T0_08 "
					+ " WHERE 1=1 "
					+ " AND EHF020410T0_05 = true "
					+ " AND EHF020410T0_06 IN ('01', '02') "
					+ " AND EHF020410T0_08 = '" + comp_id + "' "; // 公司代碼

			if (!"".equals(employee_id) && !"".equals(dept_id)) { // 員工
				sql += " AND EHF020403T0_02 = '" + dept_id + "' " + // 部門代號
						" AND EHF020410T0_01 = '" + employee_id + "' "; // 員工工號
			} else if (!"".equals(dept_id)) { // 部門組織
				sql += " AND EHF020403T0_02 = '" + dept_id + "' "; // 部門代號
			}

			/*
			 * if (!"".equals(employee_id)){ //員工 sql +=
			 * " AND EHF020410T0_01 = '"+employee_id+"' "; //員工工號 }
			 */

			// 日期區間 -- 使用實際日期做判斷
			if (!"".equals(start_date) && start_date != null
					&& !"".equals(end_date) && end_date != null) {
				sql += " AND " + " ( " + " EHF020410T0_03 BETWEEN '"
						+ start_date + "' AND '" + end_date + "' " + // 考勤日期
						" ) ";
			} else if (!"".equals(start_date) && start_date != null) {
				sql += " AND " + " ( " + " EHF020410T0_03 >= '" + start_date
						+ "' " + // 考勤日期
						" ) ";
			} else if (!"".equals(end_date) && end_date != null) {
				sql += " AND " + " ( " + " EHF020410T0_03 <= '" + end_date
						+ "' " + // 考勤日期
						" ) ";
			}

			sql += "" + " ) " + " UNION " + " ( " + " SELECT "
					+ " EHF020410T0_01 AS 單號, EHF020403T0_02 AS 部門代號, "
					+ " EHF020410T0_01 AS 員工工號, " + " '異常出勤' AS 單據別, "
					+ " EHF020410T0_03 AS ATT_DATE, " + " 0 AS TYPE, "
					+ " EHF020410T0_06 AS 類別代碼, " + " "
					+ " CASE EHF020410T0_06 " + " WHEN '00' THEN '正常' "
					+ " WHEN '01' THEN '遲到' " + " WHEN '02' THEN '早退' "
					+ " WHEN '03' THEN '曠職' " + " WHEN '04' THEN '未打卡' "
					+ " END AS 類別, " + "  " + " CASE EHF020410T0_06 "
					+ " WHEN '00' THEN 0 "
					+ " WHEN '01' THEN FLOOR(EHF020410T0_07/(60*60)) "
					+ " WHEN '02' THEN FLOOR(EHF020410T0_07/(60*60)) "
					+ " WHEN '03' THEN " + " ( " + " CASE "
					+ " WHEN (SUM(EHF020410T0_07)/(60*60*"
					+ dayhours
					+ ")) >= 1 THEN 1 "
					+ " ELSE 0 "
					+ " END "
					+ " ) "
					+ " WHEN '04' THEN 0 "
					+ " END AS DAYS, "
					+ " CASE EHF020410T0_06 "
					+ " WHEN '00' THEN 0 "
					+ " WHEN '01' THEN MOD(EHF020410T0_07/60,60) "
					+ " WHEN '02' THEN MOD(EHF020410T0_07/60,60) "
					+ " WHEN '03' THEN "
					+ " ( "
					+ " CASE "
					+ " WHEN (SUM(EHF020410T0_07)/(60*60*"
					+ dayhours
					+ ")) >= 1 THEN 0 "
					+ " ELSE TRUNCATE(MOD(EHF020410T0_07/(60*60), "
					+ dayhours
					+ "), 1) "
					+ " END "
					+ " ) "
					+ " WHEN '04' THEN 0 "
					+ " END AS HOURS "
					+ " FROM "
					+ " EHF020410T0 "
					+ " LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020410T0_01 "
					+ " AND EHF020403T0_04 = EHF020410T0_08 "
					+ " WHERE 1=1 "
					+ " AND EHF020410T0_05 = true "
					+ " AND EHF020410T0_06 IN ('03') " 
					+ " AND EHF020410T0_FIX= '0'"
					+ " AND EHF020410T0_08 = '" + comp_id + "' "; // 公司代碼

			if (!"".equals(employee_id) && !"".equals(dept_id)) { // 員工
				sql += " AND EHF020403T0_02 = '" + dept_id + "' " + // 部門代號
						" AND EHF020410T0_01 = '" + employee_id + "' "; // 員工工號
			} else if (!"".equals(dept_id)) { // 部門組織
				sql += " AND EHF020403T0_02 = '" + dept_id + "' "; // 部門代號
			}

			/*
			 * if (!"".equals(employee_id)){ //員工 sql +=
			 * " AND EHF020410T0_01 = '"+employee_id+"' "; //員工工號 }
			 */

			// 日期區間 -- 使用實際日期做判斷
			if (!"".equals(start_date) && start_date != null
					&& !"".equals(end_date) && end_date != null) {
				sql += " AND " + " ( " + " EHF020410T0_03 BETWEEN '"
						+ start_date + "' AND '" + end_date + "' " + // 考勤日期
						" ) ";
			} else if (!"".equals(start_date) && start_date != null) {
				sql += " AND " + " ( " + " EHF020410T0_03 >= '" + start_date
						+ "' " + // 考勤日期
						" ) ";
			} else if (!"".equals(end_date) && end_date != null) {
				sql += " AND " + " ( " + " EHF020410T0_03 <= '" + end_date
						+ "' " + // 考勤日期
						" ) ";
			}

			sql += "" + " GROUP BY EHF020410T0_03, EHF020410T0_01 " + " ) "
					+ " ) tableA " + " WHERE 1=1 " + "";

			// 請假單
			sql += " UNION "
					+ " 	SELECT "
					+ "   EHF020200T0_01 AS 單號, EHF020200T0_04 AS 部門代號, EHF020200T0_03 AS 員工工號, '請假單' AS 單據別, "
					+ "   EHF020200T0_08 AS ATT_DATE, "
					+ "   1 AS TYPE, "
					+ "   EHF020200T0_14 AS 類別代碼, EHF020100T0_04 AS 類別, "
					+ "   FLOOR(((SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) * "
					+ dayhours
					+ ") + SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5))/"
					+ dayhours
					+ ") AS DAYS, "
					+ "   MOD(((SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1) * "
					+ dayhours
					+ ") + SUBSTRING(EHF020200T0_13,POSITION('/' IN EHF020200T0_13)+1,5)), "
					+ dayhours
					+ ") AS HOURS "
					+ "   FROM EHF020200T0 "
					+ "   LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF020200T0_14 AND EHF020100T0_08 = EHF020200T0_18 "
					+ "   WHERE 1=1 " + "   AND EHF020200T0_16 = '"
					+ form_status + "' " + // 處理狀態
					"   AND EHF020200T0_18 = '" + comp_id + "' "; // 公司代碼

			if (!"".equals(employee_id) && !"".equals(dept_id)) { // 員工
				sql += " AND EHF020200T0_04 = '" + dept_id + "' " + // 部門代號
						" AND EHF020200T0_03 = '" + employee_id + "' "; // 員工工號
			} else if (!"".equals(dept_id)) { // 部門組織
				sql += " AND EHF020200T0_04 = '" + dept_id + "' "; // 部門代號
			}

			// 日期區間 -- 使用實際請假日期做判斷
			if (!"".equals(start_date) && start_date != null
					&& !"".equals(end_date) && end_date != null) {
				sql += " AND ( "
						+ "  DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') BETWEEN '"
						+ start_date + "' AND '" + end_date
						+ "' "
						+ // 請假日期(起)
						"  OR "
						+ "  DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') BETWEEN '"
						+ start_date + "' AND '" + end_date + "' " + // 請假日期(迄)
						" ) ";
			} else if (!"".equals(start_date) && start_date != null) {
				sql += " AND ( "
						+ " DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') >= '"
						+ start_date + "' " + " OR "
						+ " DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') >= '"
						+ start_date + "' " + " ) ";
			} else if (!"".equals(end_date) && end_date != null) {
				sql += " AND ( "
						+ " DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') <= '"
						+ end_date + "' " + " OR "
						+ " DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') <= '"
						+ end_date + "' " + " ) ";
			}

			// 出差單
			sql += " UNION "
					+ "   SELECT "
					+ "   EHF020300T0_01 AS 單號, EHF020300T0_04 AS 部門代號, EHF020300T0_03 AS 員工工號, '出差單' AS 單據別, "
					+ "   EHF020300T0_08 AS ATT_DATE, "
					+ "   2 AS TYPE, "
					+ "   EHF020300T0_14 AS 類別代碼, "
					+ "   ( CASE EHF020300T0_14 WHEN 'IN' THEN '國內' WHEN 'OUT' THEN '國外' END ) AS 類別, "
					+ "   FLOOR(((SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1) * "
					+ dayhours
					+ ") + SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5))/"
					+ dayhours
					+ ") AS DAYS, "
					+ "   MOD(((SUBSTRING(EHF020300T0_13, 1, POSITION('/' IN EHF020300T0_13)-1) * "
					+ dayhours
					+ ") + SUBSTRING(EHF020300T0_13,POSITION('/' IN EHF020300T0_13)+1,5)), "
					+ dayhours + ") AS HOURS " + "   FROM EHF020300T0 "
					+ "   WHERE 1=1 " + "   AND EHF020300T0_20 = '"
					+ form_status + "' " + // 處理狀態
					"   AND EHF020300T0_21 = '" + comp_id + "' "; // 公司代碼

			if (!"".equals(employee_id) && !"".equals(dept_id)) { // 員工
				sql += " AND EHF020300T0_04 = '" + dept_id + "' " + // 部門代號
						" AND EHF020300T0_03 = '" + employee_id + "' "; // 員工工號
			} else if (!"".equals(dept_id)) { // 部門組織
				sql += " AND EHF020300T0_04 = '" + dept_id + "' "; // 部門代號
			}

			// 日期區間 -- 使用實際出差日期做判斷
			if (!"".equals(start_date) && start_date != null
					&& !"".equals(end_date) && end_date != null) {
				sql += " AND ( "
						+ "  DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d') BETWEEN '"
						+ start_date + "' AND '" + end_date
						+ "' "
						+ // 出差日期(起)
						"  OR "
						+ "  DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d') BETWEEN '"
						+ start_date + "' AND '" + end_date + "' " + // 出差日期(迄)
						" ) ";
			} else if (!"".equals(start_date) && start_date != null) {
				sql += " AND ( "
						+ " DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d') >= '"
						+ start_date + "' " + " OR "
						+ " DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d') >= '"
						+ start_date + "' " + " ) ";
			} else if (!"".equals(end_date) && end_date != null) {
				sql += " AND ( "
						+ " DATE_FORMAT(EHF020300T0_09, '%Y/%m/%d') <= '"
						+ end_date + "' " + " OR "
						+ " DATE_FORMAT(EHF020300T0_10, '%Y/%m/%d') <= '"
						+ end_date + "' " + " ) ";
			}

			// 公務單
			sql += " UNION "
					+ "   SELECT "
					+ "   EHF020600T0_01 AS 單號, EHF020600T0_04 AS 部門代號, EHF020600T0_03 AS 員工工號, '公務單' AS 單據別, "
					+ "   EHF020600T0_07 AS ATT_DATE, " + "   3 AS TYPE, "
					+ "   '' AS 類別代碼, '公務' AS 類別, "
					+ "   '1' AS DAYS, '0' AS HOURS " + "   FROM EHF020600T0 "
					+ "   WHERE 1=1 " + "   AND EHF020600T0_17 = '"
					+ form_status + "' " + // 處理狀態
					"   AND EHF020600T0_18 = '" + comp_id + "' "; // 公司代碼

			if (!"".equals(employee_id) && !"".equals(dept_id)) { // 員工
				sql += " AND EHF020600T0_04 = '" + dept_id + "' " + // 部門代號
						" AND EHF020600T0_03 = '" + employee_id + "' "; // 員工工號
			} else if (!"".equals(dept_id)) { // 部門組織
				sql += " AND EHF020600T0_04 = '" + dept_id + "' "; // 部門代號
			}

			// 日期區間 -- 使用實際會辦日期做判斷
			if (!"".equals(start_date) && start_date != null
					&& !"".equals(end_date) && end_date != null) {
				sql += " AND DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d') BETWEEN '"
						+ start_date + "' AND '" + end_date + "' "; // 會辦日期
			} else if (!"".equals(start_date) && start_date != null) {
				sql += " AND DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d') >= '"
						+ start_date + "' ";
			} else if (!"".equals(end_date) && end_date != null) {
				sql += " AND DATE_FORMAT(EHF020600T0_09, '%Y/%m/%d') <= '"
						+ end_date + "' ";
			}

			// 未打卡單
			sql += " UNION "
					+ "   SELECT "
					+ "   EHF020700T0_01 AS 單號, EHF020700T0_04 AS 部門代號, EHF020700T0_03 AS 員工工號, '未打卡單' AS 單據別, "
					+ "   EHF020700T0_07 AS ATT_DATE, "
					+ "   4 AS TYPE, "
					+ "   EHF020700T0_09 AS 類別代碼, "
					+ "   ( CASE EHF020700T0_09 WHEN '01' THEN '上班未打卡' WHEN '02' THEN '下班未打卡' WHEN '03' THEN '全天未打卡' END ) AS 類別, "
					+ "   '1' AS DAYS, '0' AS HOURS " + "   FROM EHF020700T0 "
					+ "   WHERE 1=1 " + "   AND EHF020700T0_14 = '"
					+ form_status + "' " + // 處理狀態
					"   AND EHF020700T0_15 = '" + comp_id + "' "; // 公司代碼

			if (!"".equals(employee_id) && !"".equals(dept_id)) { // 員工
				sql += " AND EHF020700T0_04 = '" + dept_id + "' " + // 部門代號
						" AND EHF020700T0_03 = '" + employee_id + "' "; // 員工工號
			} else if (!"".equals(dept_id)) { // 部門組織
				sql += " AND EHF020700T0_04 = '" + dept_id + "' "; // 部門代號
			}

			// 日期區間 -- 使用實際未打卡日期做判斷
			if (!"".equals(start_date) && start_date != null
					&& !"".equals(end_date) && end_date != null) {
				sql += " AND DATE_FORMAT(EHF020700T0_08, '%Y/%m/%d') BETWEEN '"
						+ start_date + "' AND '" + end_date + "' "; // 未打卡日期
			} else if (!"".equals(start_date) && start_date != null) {
				sql += " AND DATE_FORMAT(EHF020700T0_08, '%Y/%m/%d') >= '"
						+ start_date + "' ";
			} else if (!"".equals(end_date) && end_date != null) {
				sql += " AND DATE_FORMAT(EHF020700T0_08, '%Y/%m/%d') <= '"
						+ end_date + "' ";
			}

			sql += "" + "  ) Att " + " WHERE 1=1 ";

			//if (!"".equals(form_type) && form_type != null
			//		&& !"全部".equals(form_type)) {
			//	sql += " AND Att.單據別 = '" + form_type + "' "; // 類別
			//}GROUP BY Att.類別
			if (type.equals("02")) {
				
				sql += " GROUP BY Att.類別"
					+ " ORDER BY Att.部門代號, Att.員工工號, Att.單據別, Att.類別 ";
			}else if (type.equals("03")) {
				sql += "" + " GROUP BY Att.員工工號, Att.單據別, Att.類別 "
					 + " ORDER BY Att.部門代號, Att.員工工號, Att.單據別, Att.類別 ";
			}

			if (type.equals("01")) {
				sql += " ) A WHERE 1 = 1";
			}
			rs = stmt.executeQuery(sql);
			int i=0;
			while (rs.next()) {

				if (type.equals("01")) {//取得數量
					i = rs.getInt("總數量");
					All_dataList.add(i);
				} else if (type.equals("02")){//取得全公司有用到的資料
					List dataList = new LinkedList<String>();
					dataList.add((String) rs.getString("類別"));
					// dataList.add(String.valueOf(rs.getInt("EHF030600T3_06")));
					All_dataList.add(dataList);
				}else if (type.equals("03")){//取得個人用到的
					List dataList = new LinkedList<String>();
					
					dataList.add((String)rs.getString("單據別"));
					dataList.add((String)rs.getString("類別"));
					dataList.add((String)rs.getString("總計"));
					dataList.add((String)rs.getString("總次"));
					dataList.add((String)rs.getString("HOURS"));
					dataList.add((String)rs.getString("DAYS"));
					dataList.add((String)rs.getString("總時數"));
					dataList.add((String)rs.getString("總天數"));
					All_dataList.add(dataList);
					i++;
				}
			}
			
			if (type.equals("03")){//取得個人用到的
				printMap.put("Other_Leave_time_count", i);
				printMap.put("Other_Leave_time_count_list", All_dataList);
			}
			
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return All_dataList;
	}
	
	/**
	  * 依據員工工號  取得是否計算考勤
	  * @param conn
	  * @param comp_id
	  * @param EHF030200T0_01
	  * @return
	  */
	public boolean SELECT_EHF020403T0_05(Connection conn,String comp_id ,String EHF030200T0_01,String EHF030200T0_02) {
		boolean flag=false;
		try {
			//修改於2014/01/07   BY 賴泓錡     原因:是否計算考勤  改寫在感應卡設定
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,	ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			String sql = " select * From EHF020403T0 where 1=1"
					+ " AND EHF020403T0_04 = '" + comp_id + "' "//公司代碼
					+ " AND EHF020403T0_01 = '" + EHF030200T0_01 + "' "//員工工號
					+ " AND EHF020403T0_02 = '" + EHF030200T0_02 + "' ";//部門代號
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				if("1".equals(rs.getString("EHF020403T0_05"))){
					//要計算考勤
					flag=true;	
				}
			}
			stmt.close();
			rs.close();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag; 
	}
	

	/**
	 * 找出個人當月 所有加班時數總時數與換休、換錢時數
	 * @param conn
	 * @param dept_id
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @param comp_id
	 * @param printMap
	 */
	private void getALLOvertime(Connection conn,String dept_id, String employee_id, String start_date, String end_date, String comp_id , Map printMap) {
		// TODO Auto-generated method stub
		
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
		
			String sql = "" +
			" SELECT " +
			" tableA.員工工號, " +
			" tableA.部門代號, " +
			" SUM(tableA.總加班時數) AS 總加班時數, " +
			" IFNULL(tableA.換錢時數, 0) AS 加班換錢時數, " +
			" IFNULL(tableA.換休時數, 0) AS 加班換休時數, " +
			" tableA.次數  AS 總加班次數 " +
			" FROM (" +
	    
			" SELECT " +
			" EHF020802T0_02 AS 員工工號, " +
			" EHF020403T0_02 AS 部門代號, " +
			" EHF020802T0_04 AS 考勤日期, " +
			" SUM(EHF020802T0_07 - EHF020802T0_07_DE) AS 總加班時數, " +
			" tableC.次數 AS 次數, " +
			" tableD.換錢時數 AS 換錢時數, "+
			" tableE.換休時數 AS 換休時數 "+
			" FROM  EHF020802T0 "+
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020802T0_02 AND EHF020403T0_04 = EHF020802T0_08"+
		
			" LEFT JOIN (SELECT "+
			" EHF020802T0_02 AS 員工工號,EHF020403T0_02 AS 部門代號,EHF020802T0_08 AS 公司代碼,count(EHF020802T0_02) AS 次數"+
			" FROM EHF020802T0"+
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020802T0_02 AND EHF020403T0_04 = EHF020802T0_08"+
			" WHERE 1 = 1"+
			" AND EHF020802T0_08 = '"+comp_id+"' "+
			" AND EHF020403T0_02 = '"+dept_id+"' " +  //部門代號
			" AND EHF020802T0_02 = '"+employee_id+"' "+  //員工工號
			" AND (EHF020802T0_04 BETWEEN '"+start_date+"' AND '"+end_date+"' )" +
			" GROUP BY EHF020802T0_02"+
			" ORDER BY EHF020802T0_02 , EHF020802T0_04) tableC ON tableC.員工工號 = EHF020802T0_02 AND tableC.公司代碼 = EHF020802T0_08"+
	    
			" LEFT JOIN ( " +
			" SELECT "+
			" EHF020802T0_02 AS 員工工號,"+
			" EHF020403T0_02 AS 部門代號,"+
			" EHF020802T0_08 AS 公司代碼,"+
			" SUM(EHF020802T0_07 - EHF020802T0_07_DE) AS 換錢時數"+
			" FROM EHF020802T0"+
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020802T0_02 AND EHF020403T0_04 = EHF020802T0_08"+
			" WHERE 1 = 1"+
			" AND EHF020802T0_08 = '"+comp_id+"' "+
			" AND EHF020403T0_02 = '"+dept_id+"' " +  //部門代號
			" AND EHF020802T0_02 = '"+employee_id+"' "+  //員工工號
			" AND EHF020802T0_09 = '01'"+
			" AND (EHF020802T0_04 BETWEEN '"+start_date+"' AND '"+end_date+"' )" +
			" GROUP BY EHF020802T0_02"+
			" ORDER BY EHF020802T0_02 , EHF020802T0_04) tableD ON tableD.員工工號 = EHF020802T0_02"+
			" AND tableD.公司代碼 = EHF020802T0_08"+
			
		
			" LEFT JOIN ( " +
			" SELECT "+
			" EHF020802T0_02 AS 員工工號,"+
			" EHF020403T0_02 AS 部門代號,"+
			" EHF020802T0_08 AS 公司代碼,"+
			" SUM(EHF020802T0_07 - EHF020802T0_07_DE) AS 換休時數"+
			" FROM EHF020802T0"+
			" LEFT JOIN EHF020403T0 ON EHF020403T0_01 = EHF020802T0_02 AND EHF020403T0_04 = EHF020802T0_08"+
			" WHERE 1 = 1"+
			" AND EHF020802T0_08 = '"+comp_id+"' "+
			" AND EHF020403T0_02 = '"+dept_id+"' " +  //部門代號
			" AND EHF020802T0_02 = '"+employee_id+"' "+  //員工工號
			" AND EHF020802T0_09 = '02'"+
			" AND (EHF020802T0_04 BETWEEN '"+start_date+"' AND '"+end_date+"' )" +
			" GROUP BY EHF020802T0_02"+
			" ORDER BY EHF020802T0_02 , EHF020802T0_04) tableE ON tableD.員工工號 = EHF020802T0_02 AND tableE.公司代碼 = EHF020802T0_08"+
			
			" WHERE 1 = 1"+
			" AND EHF020802T0_08 = '"+comp_id+"' "+
			" AND EHF020403T0_02 = '"+dept_id+"' " +  //部門代號
			" AND EHF020802T0_02 = '"+employee_id+"' "+  //員工工號
			" AND (EHF020802T0_04 BETWEEN '"+start_date+"' AND '"+end_date+"' )" +
			" GROUP BY EHF020802T0_04 , EHF020802T0_02) tableA"+
			" WHERE 1 = 1"+
			" GROUP BY tableA.員工工號";
			rs = stmt.executeQuery(sql);
			int i=0;
			if (rs.next()) {
				
				printMap.put("總加班時數", rs.getInt("總加班時數"));
				printMap.put("加班換錢時數", rs.getInt("加班換錢時數"));
				printMap.put("加班換休時數", rs.getInt("加班換休時數"));
				printMap.put("總加班次數", rs.getInt("總加班次數"));
			}else{
				printMap.put("總加班時數", 0);
				printMap.put("加班換錢時數", 0);
				printMap.put("加班換休時數", 0);
				printMap.put("總加班次數", 0);
			}
			
			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 找剩餘假別時數
	 * @param conn
	 * @param dept_id
	 * @param employee_id
	 * @param start_date
	 * @param end_date
	 * @param comp_id
	 * @param printMap
	 */
	private void getResidueVacation(Connection conn,String dept_id, String employee_id, String start_date, String end_date, String comp_id , Map printMap) {
		// TODO Auto-generated method stub
		List All_dataList = new LinkedList<String>();
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
		
			String sql = "" +
			" SELECT EHF010300T0_01, EHF010300T0_02, EHF010300T0_03, EHF010300T0_04, EHF010300T0_05, EHF010300T0_06, " +
			" SUM(EHF010300T0_07) AS EHF010300T0_07, " +  //休假時數
			" SUM(EHF010300T0_08) AS EHF010300T0_08, " +  //剩餘時數
			" (SUM(EHF010300T0_07) - SUM(EHF010300T0_08)) AS EHF010300T0_081, " +  //已休時數
			" EHF010300T0_09, EHF010300T0_10, EHF010300T0_11, EHF020100T0_04 " +
			" FROM EHF010300T0 " +
			" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF010300T0_06 AND EHF020100T0_08 = EHF010300T0_12 " +
			" WHERE 1=1 " +
			" AND EHF010300T0_12 = '"+comp_id+"' " ;
			
			sql += " AND (EHF010300T0_09 BETWEEN '"+start_date+"' AND '"+end_date+"' OR EHF010300T0_10 BETWEEN '"+start_date+"' AND '"+end_date+"')";
			sql += " AND EHF010300T0_04 = '"+dept_id+"' ";  //部門組織
			sql += " AND EHF010300T0_05 = '"+employee_id+"' ";  //員工工號
			sql +=  " GROUP BY EHF010300T0_02, EHF010300T0_05, EHF010300T0_06 " +
					" ORDER BY EHF010300T0_05, EHF010300T0_02, EHF010300T0_06 ";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				List dataList = new LinkedList<String>();
				dataList.add((String) rs.getString("EHF020100T0_04"));
				dataList.add((String) rs.getString("EHF010300T0_08"));
				All_dataList.add(dataList);
			}

			printMap.put("ResidueVacation", All_dataList);

			rs.close();
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 找出投保人數(回傳不含本人的人數)
	 * @param conn
	 * @param dept_id
	 * @param employee_id
	 * @param comp_id
	 * @param printMap
	 */
	private void SELECT_EHF030300T0_11(Connection conn,String dept_id, String employee_id, String comp_id , Map printMap) {
		
		
		int i=0;
		int money=0;
		try {
			String sql = "" +
			" SELECT EHF030300T0.* " +
			" FROM EHF030300T0 " +
			" WHERE 1=1 " +
			" AND EHF030300T0_01 = '"+employee_id+"' " +
			" AND EHF030300T0_02 = '"+dept_id+"' " +
			" AND EHF030300T0_14 = '"+comp_id+"' " ;
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			i+=rs.getInt("EHF030300T0_11");
			money+=rs.getInt("EHF030300T0_10");
		}
		i=i-1;
		if(i<0){
			i=0;
		}
		printMap.put("眷屬加保人數", i);
		printMap.put("地方政府補助款", money);
		rs.close();
		stmt.close();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	/**
	 * 取得工作天數、休假天數、加班時數
	 */
	protected String getWorking_daysSQL( String name,String start_date, String end_date, String comp_id  ){
		String sql_02 = "";
		sql_02 = " SELECT    EHF010105T0_02 AS 員工工號,"
			+ " count(EHF010105T1_04) AS 上班天數,"
			+ " IFNULL(a.休假天數,0)AS 休假天數,"
			+ " IFNULL(IFNULL(b.實際加班換錢時數, 0)+IFNULL(c.實際加班換休時數,0), 0) AS 實際加班時數," 
			+ " IFNULL(b.實際加班換錢時數, 0) AS 實際加班換錢時數," 
			+ " IFNULL(c.實際加班換休時數, 0) AS 實際加班換休時數"
			+ " FROM   EHF010105T0"
			+ " LEFT JOIN EHF010105T1 ON EHF010105T0_01 = EHF010105T1_01  AND EHF010105T1_03 <> '1'  "
			+ " LEFT JOIN EHF010100T0 ON EHF010105T1_04 = EHF010100T0_01"
			
			+ " LEFT JOIN (SELECT  EHF010105T0_02 AS 員工工號,count(EHF010105T1_03) AS 休假天數"
			+ " FROM  EHF010105T0"
			+ " LEFT JOIN EHF010105T1 ON EHF010105T0_01 = EHF010105T1_01"
			+ " LEFT JOIN EHF010100T0 ON EHF010105T1_04 = EHF010100T0_01"
			+ " WHERE 1 = 1 " + " AND EHF010105T0_02 = '"+name+"'"
			+ " AND EHF010105T1_02 >= '" + start_date + "' "
			+ " AND EHF010105T1_02 <= '" + end_date + "' "
			+ " AND EHF010105T0_06='"+comp_id+"' "
			+ " AND EHF010105T1_03 = '1'"
			+ " GROUP BY EHF010105T0_02 , EHF010105T1_04"
			+ " ORDER BY EHF010105T0_02) a ON a.員工工號 = EHF010105T0_02"
			
			+ " LEFT JOIN (SELECT EHF020802t0_02 AS 員工工號," 
			+ " IFNULL(SUM(EHF020802T0_07 - EHF020802T0_07_DE), 0) AS 實際加班換錢時數"
            + " FROM ehf020802t0" 
            + " WHERE  1 = 1     " 
            + " AND ehf020802t0_02 = '"+name+"'"
            + " AND ehf020802t0_04 >= '" + start_date + "' " 
            + " AND ehf020802t0_04 <= '" + end_date + "' "
            + " AND EHF020802T0_08='"+comp_id+"' " 
            + " AND EHF020802T0_09 = '01'"
            + " GROUP BY ehf020802t0_02)b on b.員工工號 = EHF010105T0_02"
            
            + " LEFT JOIN (SELECT EHF020802t0_02 AS 員工工號," 
			+ " IFNULL(SUM(EHF020802T0_07 - EHF020802T0_07_DE), 0) AS 實際加班換休時數"
            + " FROM ehf020802t0" 
            + " WHERE  1 = 1     " 
            + " AND ehf020802t0_02 = '" + name + "'"
            + " AND ehf020802t0_04 >= '" + start_date + "' " 
            + " AND ehf020802t0_04 <= '" + end_date + "' "
            + " AND EHF020802T0_08='"+comp_id+"' " 
            + " AND EHF020802T0_09 = '02'"
            + " GROUP BY ehf020802t0_02)c on b.員工工號 = EHF010105T0_02"
            
			+ " WHERE" + " 1 = 1 AND EHF010105T0_02 = '"+name+"'"
			+ " AND EHF010105T1_02 >= '" + start_date + "' "
			+ " AND EHF010105T1_02 <= '" + end_date + "' "
			+ " AND EHF010105T0_06='"+comp_id+"' "
			+ " GROUP BY EHF010105T0_02 "
			+ " ORDER BY EHF010105T0_02";
		
		
		return sql_02;
		
	}
	
	
	/**
	 * 
	 * @param conn
	 * @param dataMap
	 * @param compId
	 * @param remark
	 * @return
	 */
	private String getALLsupplementdeduct (Connection conn, Map dataMap, String compId) {
		String remark = "";
		int i=0;
		String sql = " ";
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
		
		
			sql="  SELECT  *" +
			"  FROM EHF030500T0  " +
			"  WHERE" +
			"  1 = 1 " +
			" AND EHF030500T0_02 = '"+ dataMap.get("員工工號") +"'"+ 
			" AND EHF030500T0_05 = '"+ this.salYYMM +"'" +
			" AND EHF030500T0_03 = '"+ dataMap.get("部門代號") +"'";
		
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				
				
				if("01".equals(rs.getString("EHF030500T0_06"))){
					if(!"".equals(remark)){
						remark+=",";
					}
					remark+=rs.getString("EHF030500T0_08")+":"+rs.getString("EHF030500T0_09");
					
				}else if("02".equals(rs.getString("EHF030500T0_06"))){
					if(!"".equals(remark)){
						remark+=",";
					}
					remark+=rs.getString("EHF030500T0_10")+":"+rs.getString("EHF030500T0_11");
					
				}
			}
		
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return remark;
	}
	
}
