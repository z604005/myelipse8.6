package com.spon.ems.salary.forms;

import com.spon.utils.util.BA_EXCEL_forsalary;
import com.spon.utils.util.HR_TOOLS;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class EX030600R1F extends BA_EXCEL_forsalary {
	
private int headsize = 18; //標題個數
	
	//	存放標題用的陣列
	//	HeadData[0][] 資料列
	//	HeadData[1][] 欄位代號
	//	HeadData[2][] 資料內容
	//	HeadData[3][] 零是否列印 Y 印 N 不印
	//	HeadData[4][] 資料格式 如 "##,###" or "0.00000"
	private String HeadData[][];
	public long objTotal[][]; //計算合計用的
	private int Greater_than_count;//加項總數(薪資項目金額設定)
	private int Less_than_count;//扣項總數(薪資項目金額設定)
	
	private int Greater_than_count_01;// 加班費,不休假加班費,差旅費
	private int Less_than_count_02;//請假扣薪金額,薪資所得扣繳金,勞退自提,福利金,遲到扣薪金額,早退扣薪金額,曠職扣薪金額
	
	private List Greater_than_count_01_LIST;
	private List Less_than_count_02_LIST;
	
	private List Greater_than_ALL;//取得薪資加項總明細(薪資項目金額設定)
	private List Less_than_ALL;//取得薪資扣項總明細(薪資項目金額設定) 
	
	
	private List Allowance_Greater_than;//津貼基本資料設定(加項)
	private List Allowance_Less_than;//津貼基本資料設定(扣項)
	private int Allowance_Greater_than_count;//加項總數(津貼基本資料設定)
	private int Allowance_Less_than_count;//扣項總數(津貼基本資料設定)
	private int i=0;
	private int tatle=0;
	private String day="";
	private int MUNBER_MAX=0;
	
	
	/** 
	 * 一般建構子
	 * 
	 */
	public EX030600R1F(Connection conn,String rpt,String SC0030_01, EHF030600M0F form,String comp_id) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF030600R1.xls";
		
		//sheet的名稱
		super.SheetName = "薪津清冊";
				
		//樣板一頁總列數
		super.PageRowCount = 26;
		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 16;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal = new long[10][tatle];
			
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 5;
		
		//標題設定標題資料個數
		setHeadSize(headsize);
			
		//開始產生Excel
		super.init(conn,rpt,SC0030_01);
	}
	
	

	/** 
	 * 公司別建構子
	 * @param form 
	 * 
	 */
	public EX030600R1F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request, EHF030600M0F form,String comp_id,List printlist) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF030600R1.xls";
		
		this.day=form.getEHF030600T0_02();
		//sheet的名稱
		super.SheetName = "薪津清冊";
		if(printlist.size()>0){
		
//===============================================================================			
			//取得薪資加項總明細(薪資項目金額設定)
			this.Greater_than_ALL=this.SELECT_Salary_Project_Count(conn, form,comp_id,printlist,">","");
			//取得薪資加項總數量(薪資項目金額設定)
			List Greater_than_count_list=this.SELECT_Salary_Project_Count(conn, form,comp_id,printlist,">","01");
			this.Greater_than_count=Integer.valueOf(Greater_than_count_list.get(0).toString());
//===============================================================================	
			//取得薪資扣項總明細(薪資項目金額設定)
			this.Less_than_ALL=this.SELECT_Salary_Project_Count(conn, form,comp_id,printlist,"<","");
			//取得薪資扣項總數量(薪資項目金額設定)
			List Less_than_count_list=this.SELECT_Salary_Project_Count(conn, form,comp_id,printlist,"<","01");
			this.Less_than_count=Integer.valueOf(Less_than_count_list.get(0).toString());
//===============================================================================			
		
			//取得薪資加項總明細(津貼基本資料設定)
			this.Allowance_Greater_than=this.SELECT_Allowance_Project_Count(conn, form,comp_id,printlist,">","");
			//取得薪資加項總數量(津貼基本資料設定)
			List Allowance_ALL_Greater_than_count_list=this.SELECT_Allowance_Project_Count(conn, form,comp_id,printlist,">","01");
			this.Allowance_Greater_than_count=Integer.valueOf(Allowance_ALL_Greater_than_count_list.get(0).toString());
		
			//取得薪資扣項總明細(津貼基本資料設定)
			this.Allowance_Less_than=this.SELECT_Allowance_Project_Count(conn, form,comp_id,printlist,"<","");
			//取得薪資扣項總數量(津貼基本資料設定)
			List Allowance_ALL_Less_than_count_list=this.SELECT_Allowance_Project_Count(conn, form,comp_id,printlist,"<","01");
			this.Allowance_Less_than_count=Integer.valueOf(Allowance_ALL_Less_than_count_list.get(0).toString());
		
			//取得薪資主檔中   已經計算好的基本加項、扣項金額
			Map Add_items=this.Add_items(conn,comp_id,printlist,"");
			// 加班費,不休假加班費,差旅費
			this.Greater_than_count_01=Integer.valueOf(Add_items.get("Additems_count").toString());
			this.Greater_than_count_01_LIST=(List)Add_items.get("Additems");
			
			
			//請假扣薪金額,薪資所得扣繳金,勞退自提,福利金,遲到扣薪金額,早退扣薪金額,曠職扣薪金額
			this.Less_than_count_02=Integer.valueOf(Add_items.get("Reduction_count").toString());
			this.Less_than_count_02_LIST=(List)Add_items.get("Reduction");
			
			
			//浮動的表格數量
			this.tatle+=
				this.Allowance_Greater_than_count//加項總數(薪資項目金額設定)
				+this.Allowance_Less_than_count//扣項總數(薪資項目金額設定)
				+this.Greater_than_count//加項總數(薪資項目金額設定)
				+this.Less_than_count//扣項總數(薪資項目金額設定)
				+this.Greater_than_count_01// 加班費,不休假加班費,差旅費
				+this.Less_than_count_02//請假扣薪金額,薪資所得扣繳金,勞退自提,福利金,遲到扣薪金額,早退扣薪金額,曠職扣薪金額
				+9;//this.tatle為需要計算加總的大小
		
		}
		//樣板一頁總列數
		super.PageRowCount = this.tatle+9;
			
//		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 7;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal= new long[10][this.tatle];
			
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 5;
		
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
		for(int i=0;i<this.tatle;i++){
		//丟頁面小計
		//this.setDetail(4+i, "H", this.objTotal[0][i]+"", true);
		this.Alignment=jxl.format.Alignment.RIGHT;
		this.setDetail(4+i , "J",  this.objTotal[0][i],true,"##,###");
		
		}
		this.Alignment=jxl.format.Alignment.CENTRE;
		//頁計歸零
		for(int i=0;i<this.tatle;i++){
			//丟頁面小計
			this.objTotal[0][i]=0;
		}
	}
	
	/** 
	* 丟總計
	* 
	*/
	public void EndDocument() throws Exception{

		this.mergeCell(5, "K", 7, "K", "總計");// 合併儲存格
		super.CopyCellFormat(7, "K", 7, "C",false);// 複製格式
		super.CopyCellFormat(8, "K", 8, "C",false);// 複製格式
		
		
		for (int i = 0; i < this.MUNBER_MAX + 6; i++) {
			if(!(this.MUNBER_MAX + 6-i==2))
				super.CopyCellFormat(9 + i, "K", 7, "C",false);// 複製格式
			else{
				super.CopyCellFormat(9 + i, "K", 7, "C",true);// 複製格式
			}
		}

		for (int i = 0; i < this.tatle; i++) {
			// 丟頁面總計
			//this.setDetail(3 + i, "L", this.objTotal[1][i] + "", true);
			this.Alignment=jxl.format.Alignment.RIGHT;
			this.setDetail(4+i , "K",  this.objTotal[1][i],true,"##,###");
		}
		this.Alignment=jxl.format.Alignment.CENTRE;
		
	}
	
	
	public int print( Connection conn, List printlist, String EHF030600T0_U, String comp_id ){
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
			
			while(it.hasNext()){
				
				Map printMap = new HashMap();
				EHF030600M1F ehf030600m1 = (EHF030600M1F) it.next();
				String[] name =ehf030600m1.getEHF030600T1_03().split("/");
				//printMap.put("部門", name[1]);
				//printMap.put("員工", name[0]);
				//printMap.put("部門", name[0].trim()+"/"+name[1].trim());
				printMap.put("部門", (String) ((Map)depMap.get(name[0].trim())).get("SHOW_DEPT_ID")+"/"+name[1].trim());
				//printMap.put("員工工號", name[2].trim());
				printMap.put("員工工號", (String) ((Map)empMap.get(name[2].trim())).get("EMPLOYEE_ID"));
				printMap.put("員工名稱", name[3].trim());
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
				/*
				sql = "" +
				" SELECT " +
				" tableA.職務津貼 AS 職務津貼, " +
				" tableA.全勤獎金 AS 全勤獎金, " +
				" tableA.伙食津貼 AS 伙食津貼, " +
				" tableA.夜間津貼 AS 夜間津貼, " +
				" (tableA.薪資項目津貼總金額 - (tableA.職務津貼+tableA.全勤獎金+tableA.伙食津貼+tableA.夜間津貼) ) AS 其他津貼薪資項目 " +
				" FROM ( " +
				" SELECT " +
				" EHF030600T1_01, EHF030600T1_U, EHF030600T1_SCU, (EHF030600T1_04_O+EHF030600T1_09) AS 薪資項目津貼總金額 " +
				" FROM EHF030600T1 " +
				" LEFT JOIN ( " +
				" SELECT SUM(EHF030600T3_06) AS 職務津貼 " +
				" FROM EHF030600T3" +
				" WHERE 1=1" +
				" ) A ON EHF030600T3_01 = EHF030600T1_01 AND EHF030600T3_02 = EHF030600T1_02 " +
				" AND EHF030600T3_07 = EHF030600T1_08 AND EHF030600T3_05 LIKE '%職務津貼%' " +
				" LEFT JOIN ( " +
				" SELECT SUM(EHF030600T3_06) AS 全勤獎金 " +
				" FROM EHF030600T3" +
				" WHERE 1=1" +
				" ) B ON EHF030600T3_01 = EHF030600T1_01 AND EHF030600T3_02 = EHF030600T1_02 " +
				" AND EHF030600T3_07 = EHF030600T1_08 AND EHF030600T3_05 LIKE '%全勤獎金%' " +
				" LEFT JOIN ( " +
				" SELECT SUM(EHF030601T1_04+EHF030601T1_05) AS 伙食津貼 " +
				" FROM EHF030601T1 " +
				" LEFT JOIN EFH030601T0 ON EHF030601T0_U = EHF030601T1_U AND EHF030601T0_07 = EHF030601T1_07 " +
				" WHERE 1=1 " +
				" ) C ON EHF030601T0_SCU = EHF030600T1_SCU AND EHF030601T0_01 = EHF030600T1_02 " +
				" AND EHF030601T0_07 = EHF030600T1_08 AND EHF030601T1_03 LIKE '%伙食津貼%' " +
				" LEFT JOIN ( " +
				" SELECT SUM(EHF030601T1_04+EHF030601T1_05) AS 夜間津貼 " +
				" FROM EHF030601T1 " +
				" LEFT JOIN EFH030601T0 ON EHF030601T0_U = EHF030601T1_U AND EHF030601T0_07 = EHF030601T1_07 " +
				" WHERE 1=1 " +
				" ) D ON EHF030601T0_SCU = EHF030600T1_SCU AND EHF030601T0_01 = EHF030600T1_02 " +
				" AND EHF030601T0_07 = EHF030600T1_08 AND EHF030601T1_03 LIKE '%伙食津貼%' " +
				" WHERE 1=1 " +
				" AND EHF030600T1_01 = "+ehf030600m1.getEHF030600T1_01()+" " +  //津貼計算序號 
				" AND EHF030600T1_02 = '"+ehf030600m1.getEHF030600T1_02()+"' " +  //員工工號
				" AND EHF030600T1_08 = '"+comp_id+"' " +  //公司代碼
				" ) tableA " +
				" WHERE 1=1 ";
				*/
				
				
				/*sql = "" +
				" SELECT  " +
				" IFNULL(tableA.職務津貼,0) AS 職務津貼, " +
				" IFNULL(tableA.全勤獎金,0) AS 全勤獎金, " +
				" IFNULL(tableA.伙食津貼,0) AS 伙食津貼, " +
				" IFNULL(tableA.夜間津貼,0) AS 夜間津貼, " +
				" IFNULL(tableA.薪資項目津貼總金額,0) AS 薪資項目津貼總金額, " +
				" (IFNULL(tableA.薪資項目津貼總金額, 0) - (IFNULL(tableA.職務津貼,0)+IFNULL(tableA.全勤獎金,0)+IFNULL(tableA.伙食津貼,0)+IFNULL(tableA.夜間津貼,0)) ) AS 其他津貼薪資項目 " +  
				" FROM ( " +  
				" SELECT  EHF030600T1_01, EHF030600T1_U, EHF030600T1_SCU, " +
				" 職務津貼, 全勤獎金, 伙食津貼, 夜間津貼, (EHF030600T1_04_O+EHF030600T1_09) AS 薪資項目津貼總金額 " +
				" FROM EHF030600T1  LEFT JOIN ( " +  
				" SELECT SUM(EHF030600T3_06) AS 職務津貼 " +  
				" FROM EHF030600T3 "+ 
				" WHERE 1=1 " +
				" AND EHF030600T3_01 = "+ehf030600m1.getEHF030600T1_01()+" " +  //薪資計算序號
				" AND EHF030600T3_02 = '"+ehf030600m1.getEHF030600T1_02()+"' " +  //員工工號
				" AND EHF030600T3_07 = '"+comp_id+"' " +  //公司代碼
				" AND EHF030600T3_05 LIKE '%職務津貼%' " +
				" ) A ON 1=1 " +  
				" LEFT JOIN ( " + 
				" SELECT SUM(EHF030600T3_06) AS 全勤獎金 " +  
				" FROM EHF030600T3 " + 
				" WHERE 1=1 " +
				" AND EHF030600T3_01 = "+ehf030600m1.getEHF030600T1_01()+" " +  //薪資計算序號
				" AND EHF030600T3_02 = '"+ehf030600m1.getEHF030600T1_02()+"' " +  //員工工號
				" AND EHF030600T3_07 = '"+comp_id+"' " +  //公司代碼
				" AND EHF030600T3_05 LIKE '%全勤獎金%' " +
				" ) B ON 1=1 " +
				" LEFT JOIN ( " +  
				" SELECT SUM(EHF030601T1_04+EHF030601T1_05) AS 伙食津貼 " +  
				" FROM EHF030601T1 " +  
				" LEFT JOIN EHF030601T0 ON EHF030601T0_U = EHF030601T1_U AND EHF030601T0_07 = EHF030601T1_07 " +
				" WHERE 1=1 " + 
				" AND EHF030601T0_SCU = '"+EHF030600T0_U+"' " +  //薪資計算UID
				" AND EHF030601T0_01 = '"+ehf030600m1.getEHF030600T1_02()+"' " +  //員工工號
				" AND EHF030601T1_07 = '"+comp_id+"' " +  //公司代碼
				" AND EHF030601T1_03 LIKE '%伙食津貼%' " +
				" ) C ON 1=1 " +
				" LEFT JOIN ( " +
				" SELECT SUM(EHF030601T1_04+EHF030601T1_05) AS 夜間津貼 " +
				" FROM EHF030601T1 " +  
				" LEFT JOIN EHF030601T0 ON EHF030601T0_U = EHF030601T1_U AND EHF030601T0_07 = EHF030601T1_07 " +
				" WHERE 1=1 " +  
				" AND EHF030601T0_SCU = '"+EHF030600T0_U+"' " +  //薪資計算UID
				" AND EHF030601T0_01 = '"+ehf030600m1.getEHF030600T1_02()+"' " +  //員工工號
				" AND EHF030601T1_07 = '"+comp_id+"' " +  //公司代碼
				" AND EHF030601T1_03 LIKE '%夜間津貼%' " +
				" ) D ON 1=1 " +
				" WHERE 1=1 " +  
				" AND EHF030600T1_01 = "+ehf030600m1.getEHF030600T1_01()+" " + 
				" AND EHF030600T1_02 = '"+ehf030600m1.getEHF030600T1_02()+"' " +  
				" AND EHF030600T1_08 = '"+comp_id+"' " +  
				" ) tableA " +  
				" WHERE 1=1 ";
				
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					printMap.put("職務津貼", rs.getInt("職務津貼"));
					printMap.put("全勤獎金", rs.getInt("全勤獎金"));
					printMap.put("伙食津貼", rs.getInt("伙食津貼"));
					printMap.put("夜間津貼", rs.getInt("夜間津貼"));
					printMap.put("其他津貼薪資項目", rs.getInt("其他津貼薪資項目"));
				}else{
					printMap.put("職務津貼", (int)0 );
					printMap.put("全勤獎金", (int)0 );
					printMap.put("伙食津貼", (int)0 );
					printMap.put("夜間津貼", (int)0 );
					printMap.put("其他津貼薪資項目", (int)0 );
				}*/

				Map Add_items=this.Add_items(conn,comp_id,printlist,name[2].trim());
				
				//薪資(加項)
				this.SELECT_EHF030600T3(conn,printMap,ehf030600m1,comp_id,"01",">");
				//薪資扣項
				this.SELECT_EHF030600T3(conn,printMap,ehf030600m1,comp_id,"02","<");
				
				this.SELECT_EHF030601T1(conn, printMap, ehf030600m1, comp_id, "01", ">");
				
				this.SELECT_EHF030601T1(conn, printMap, ehf030600m1, comp_id, "02", "<");
				
				//List bb=this.check(ALL,data);
				String []a={"C","D","E","F","G","H","I","J","K"};
				//列印一筆資料
				this.printDetail( printMap ,Add_items,a[count],printlist);
				
				count++;
				DataCount++;
				//rs.close();
				//if(count==printlist.size()){
				//	this.BeforeChangePage();
				//}
				if(count%7==0){
					this.setNowPage();;
					count=0;
				}				
			}
			this.BeforeChangePage();
			this.EndDocument();
			
			//stmt.close();
			
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
			this.setHeadValue(0,1,"A",comp_name+"",false,""); //列印公司抬頭
			this.setHeadValue(1,2,"A",report_name+"",false,""); //報表名稱
			this.setHeadValue(2,3,"A","薪資年月:"+salYYMM+"",false,"");  //薪資年月
			this.setHeadValue(3,4,"A","入扣帳年月:"+costYYMM+"",false,"");  //入扣帳年月
			this.setHeadValue(4,3,"I","列印日期:"+print_date+"",false,"");  //列印日期	
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void printDetail( Map dataMap, Map Add_items ,String type, List printlist){
		super.RecordStart = 5;
		int count=6;
		int ALL_Plus_Money=0;
		int ALL_Minus_Money=0;
		try{
			//列印報表每筆資料
			this.setHeadValue(5,4,"I","頁        次:"+(this.getNowPageNum()+1)+"",false,"");
			
			if (type.equals("L")) {
				this.i = 0;
				this.newPage();
			}

			this.next();  //換下一行
			
			this.Alignment=jxl.format.Alignment.CENTRE;
			this.VerticalAlignment=jxl.format.VerticalAlignment.CENTRE;
			this.setDetail(1,type,(String)dataMap.get("部門"),false);//員工姓名
			this.setDetail(2,type,(String)dataMap.get("員工工號"),false);//
			this.setDetail(3,type,(String)dataMap.get("員工名稱"),false);//
			if (type.equals("C")) {
				// 補足格數(加項)
				for (int i = 0; i < this.Greater_than_count+ this.Greater_than_count_01 + 5 + this.Allowance_Greater_than_count; i++) {
					super.CopyCellFormat(9 + i, "B", 7, "C",false);// 複製格式
					super.CopyCellFormat(9 + i, "C", 7, "C",false);// 複製格式
					super.CopyCellFormat(9 + i, "D", 7, "C",false);// 複製格式
					super.CopyCellFormat(9 + i, "E", 7, "C",false);// 複製格式
					super.CopyCellFormat(9 + i, "F", 7, "C",false);// 複製格式
					super.CopyCellFormat(9 + i, "G", 7, "C",false);// 複製格式
					super.CopyCellFormat(9 + i, "H", 7, "C",false);// 複製格式
					super.CopyCellFormat(9 + i, "I", 7, "C",false);// 複製格式
					super.CopyCellFormat(9 + i, "J", 7, "C",false);// 複製格式
					//super.CopyCellFormat(9 + i, "K", 7, "C");// 複製格式
					//super.CopyCellFormat(9 + i, "L", 7, "C");// 複製格式
				}
			}
			if (type.equals("C")) {
				int count_01 = 11+ this.Greater_than_count+ this.Greater_than_count_01+this.Allowance_Greater_than_count;
				this.mergeCells(8, 1, count_01, 1);// 合併儲存格
				this.setDetail(4, "A", "應   領   項   目", false);
				this.mergeCell(count_01 + 1, "A", count_01+ this.Less_than_count+ this.Less_than_count_02+this.Allowance_Less_than_count + 4, "A", "應   扣   項   目");// 合併儲存格
			}
			
			//this.setDetail(4,"A", "應   扣   項   目",false);
			
			int number=0;
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.setDetail(4,"B", "本薪金額",false);
			}
			//this.setDetail(3,type, (Integer)dataMap.get("本薪金額")+"",true);  //本薪金額
			this.Alignment=jxl.format.Alignment.RIGHT;
			this.setDetail(4,type, (Integer)dataMap.get("本薪金額"),true,"##,###");
			this.objTotal[0][number] += (Integer)dataMap.get("本薪金額");
			this.objTotal[1][number] += (Integer)dataMap.get("本薪金額");
			ALL_Plus_Money += (Integer)dataMap.get("本薪金額");
			number++;
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.setDetail(5,"B", "全勤獎金",false);
			}
			//this.setDetail(4,type, (Integer)dataMap.get("全勤獎金")+"",true);  //全勤獎金
			this.Alignment=jxl.format.Alignment.RIGHT;
			this.setDetail(5,type, (Integer)dataMap.get("全勤獎金"),true,"##,###");
			this.objTotal[0][number] += (Integer)dataMap.get("全勤獎金");
			this.objTotal[1][number] += (Integer)dataMap.get("全勤獎金");
			ALL_Plus_Money += (Integer)dataMap.get("全勤獎金");
			number++;
			
			// 加班費,不休假加班費,差旅費
			List Additems=(List)Add_items.get("Additems");//個人會用到的
			List Additems_0=this.Greater_than_count_01_LIST;//全公司會用到的
			
			
			Map DMap = new HashMap();
			if(Additems.size()>0){
				for (int i = 0; i < Additems.size(); i++) {
					List Additem = (List) Additems.get(i);
					DMap.put((String)Additem.get(0), (String)Additem.get(1));
				}
			}

			
			
			if(Additems_0.size()>0){
				for (int i = 0; i < Additems_0.size(); i++) {
					List Additem = (List) Additems_0.get(i);
					this.Alignment=jxl.format.Alignment.CENTRE;
					this.setDetail(count, "B", Additem.get(0).toString(),false);
					
					if(DMap.get(Additem.get(0).toString())!=null){
						this.Alignment=jxl.format.Alignment.RIGHT;
						//this.setDetail(count, type, Math.abs((Integer) dataMap.get(Additem.get(0)))+ "", true);
						this.setDetail(count , type, Math.abs(Integer.valueOf((String)DMap.get(Additem.get(0).toString()))) ,true,"##,###");
						this.objTotal[0][number] +=  Math.abs(Integer.valueOf((String)DMap.get(Additem.get(0).toString())));
						this.objTotal[1][number] +=  Math.abs(Integer.valueOf((String)DMap.get(Additem.get(0).toString())));
						//ALL_Minus_Money+= Math.abs(Integer.valueOf((String)Additem.get(1)));
						number++;
					}
					
					count++;
				}	
			}
			
			
			//取得薪資加項總明細(薪資項目金額設定)
			List ALL=this.Greater_than_ALL;//全部有使用到的加項
			List data = (List) dataMap.get("OTHER_Plus");
			
			List bb=this.check(ALL,data);
			for (int i = 0; i < bb.size(); i++) {
				List aa = (List) bb.get(i);
				if (type.equals("C")) {
					this.Alignment=jxl.format.Alignment.CENTRE;
					this.setDetail(count , "B", String.valueOf(aa.get(0)), false); // 
				}
				this.Alignment=jxl.format.Alignment.RIGHT;
				//this.setDetail(count , type, String.valueOf(aa.get(1)), true); // 全勤獎金
				this.setDetail(count , type, Integer.valueOf(String.valueOf(aa.get(1))),true,"##,###");
				this.objTotal[0][number] += Integer.valueOf(String.valueOf(aa.get(1)));
				this.objTotal[1][number] += Integer.valueOf(String.valueOf(aa.get(1)));
				ALL_Plus_Money += Integer.valueOf(String.valueOf(aa.get(1)));
				number++;
				count++ ;
			}
			
			//取得薪資加項總明細(津貼基本資料設定)
			ALL=this.Allowance_Greater_than;//津貼(加項)
			data = (List) dataMap.get("OTHER_Allowance_Plus");
			bb=this.check(ALL,data);
			for (int i = 0; i < bb.size(); i++) {
				List aa = (List) bb.get(i);
				if (type.equals("C")) {
					this.Alignment=jxl.format.Alignment.CENTRE;
					this.setDetail(count , "B", String.valueOf(aa.get(0)), false); // 
				}
				this.Alignment=jxl.format.Alignment.RIGHT;
				//this.setDetail(count , type, String.valueOf(aa.get(1)), true); // 全勤獎金
				this.setDetail(count , type, Integer.valueOf(String.valueOf(aa.get(1))),true,"##,###");
				this.objTotal[0][number] += Integer.valueOf(String.valueOf(aa.get(1)));
				this.objTotal[1][number] += Integer.valueOf(String.valueOf(aa.get(1)));
				ALL_Plus_Money += Integer.valueOf(String.valueOf(aa.get(1)));
				number++;
				count++ ;
			}
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.setDetail(count, "B", "其他補款", false); 
			}
			this.Alignment=jxl.format.Alignment.RIGHT;
			//this.setDetail(count,type, (Integer)dataMap.get("其他費用補款")+"",true);  //10
			this.setDetail(count , type, (Integer)dataMap.get("其他費用補款") ,true,"##,###");
			this.objTotal[0][number] += (Integer) dataMap.get("其他費用補款");
			this.objTotal[1][number] += (Integer) dataMap.get("其他費用補款");
			ALL_Plus_Money += (Integer) dataMap.get("其他費用補款");
			number++;
			count++;
			
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.setDetail(count, "B", "應發金額", false); 
			}
			this.Alignment=jxl.format.Alignment.RIGHT;
			//this.setDetail(count, type, ALL_Plus_Money+"",true); //11
			this.setDetail(count , type, ALL_Plus_Money ,true,"##,###");
			this.objTotal[0][number] += ALL_Plus_Money;
			this.objTotal[1][number] += ALL_Plus_Money;
			number++;
			count++;
			
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.setDetail(count, "B", "代扣勞保費", false); // 代扣勞保費
			}
			this.Alignment=jxl.format.Alignment.RIGHT;
			//this.setDetail(count, type, (Integer)dataMap.get("代扣勞保費")+"",true);  //代扣勞保費
			this.setDetail(count , type, (Integer)dataMap.get("代扣勞保費") ,true,"##,###");
			this.objTotal[0][number] += (Integer) dataMap.get("代扣勞保費");
			this.objTotal[1][number] += (Integer) dataMap.get("代扣勞保費");
			ALL_Minus_Money+= (Integer) dataMap.get("代扣勞保費");
			number++;
			count++;
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.setDetail(count, "B", "代扣健保費", false); // 代扣健保費
			}
			this.Alignment=jxl.format.Alignment.RIGHT;
			//this.setDetail(count , type, (Integer)dataMap.get("代扣健保費")+"",true);  //代扣健保費//13
			this.setDetail(count , type, (Integer)dataMap.get("代扣健保費") ,true,"##,###");
			this.objTotal[0][number] += (Integer) dataMap.get("代扣健保費");
			this.objTotal[2][number] += (Integer) dataMap.get("代扣健保費");
			ALL_Minus_Money+= (Integer) dataMap.get("代扣健保費");
			number++;
			count++;
			
			if (type.equals("C")) {
				//補足格數(扣項)
				for (int i = 0; i < this.Less_than_count+this.Less_than_count_02+this.Allowance_Less_than_count+3; i++) {
					super.CopyCellFormat(count+4+i, "B", 7, "C",false);// 複製格式
					super.CopyCellFormat(count+4+i, "C", 7, "C",false);// 複製格式
					super.CopyCellFormat(count+4+i, "D", 7, "C",false);// 複製格式
					super.CopyCellFormat(count+4+i, "E", 7, "C",false);// 複製格式
					super.CopyCellFormat(count+4+i, "F", 7, "C",false);// 複製格式
					super.CopyCellFormat(count+4+i, "G", 7, "C",false);// 複製格式
					super.CopyCellFormat(count+4+i, "H", 7, "C",false);// 複製格式
					super.CopyCellFormat(count+4+i, "I", 7, "C",false);// 複製格式
					super.CopyCellFormat(count+4+i, "J", 7, "C",false);// 複製格式
					//super.CopyCellFormat(count+4+i, "K", 7, "C");// 複製格式
					//super.CopyCellFormat(count+4+i, "L", 7, "C");// 複製格式
				}
			}
			
			//請假扣薪金額,薪資所得扣繳金,勞退自提,福利金,遲到扣薪金額,早退扣薪金額,曠職扣薪金額
			Additems=(List)Add_items.get("Reduction");//個人有的項目
			Additems_0=this.Less_than_count_02_LIST;//該薪資年月全公司會用到的項目
			
			DMap = new HashMap();
			if(Additems.size()>0){
				for (int i = 0; i < Additems.size(); i++) {
					List Additem = (List) Additems.get(i);
					DMap.put((String)Additem.get(0), (String)Additem.get(1));
				}
			}
			
			
			if(Additems_0.size()>0){
				for (int i = 0; i < Additems_0.size(); i++) {
					List Additem = (List) Additems_0.get(i);
					this.Alignment=jxl.format.Alignment.CENTRE;
					this.setDetail(count, "B", Additem.get(0).toString(),false);
					
					
					if(DMap.get(Additem.get(0).toString())!=null){
						this.Alignment=jxl.format.Alignment.RIGHT;
						//this.setDetail(count, type, Math.abs((Integer) dataMap.get(Additem.get(0)))+ "", true);
						this.setDetail(count , type, Math.abs(Integer.valueOf((String)DMap.get(Additem.get(0).toString()))) ,true,"##,###");
						this.objTotal[0][number] +=  Math.abs(Integer.valueOf((String)DMap.get(Additem.get(0).toString())));
						this.objTotal[1][number] +=  Math.abs(Integer.valueOf((String)DMap.get(Additem.get(0).toString())));
						ALL_Minus_Money+= Math.abs(Integer.valueOf((String)Additem.get(1)));
						number++;
					}
					count++;
				}	
			}
			
			
			//取得薪資扣項總明細(薪資項目金額設定)
			ALL=this.Less_than_ALL;//全部有使用到的扣項
			data = (List) dataMap.get("OTHER_Minus");
			bb=this.check(ALL,data);
			for (int i = 0; i < bb.size(); i++) {
				List aa = (List) bb.get(i);
				if (type.equals("C")) {
					this.Alignment=jxl.format.Alignment.CENTRE;
					this.setDetail(count , "B", String.valueOf(aa.get(0)), false); // 
				}
				this.Alignment=jxl.format.Alignment.RIGHT;
				//this.setDetail(count , type, Math.abs(Integer.valueOf((String)aa.get(1)))+"", true); // 全勤獎金
				this.setDetail(count , type, Math.abs(Integer.valueOf((String)aa.get(1))),true,"##,###");
				this.objTotal[0][number] +=  Math.abs(Integer.valueOf((String)aa.get(1)));
				this.objTotal[1][number] +=  Math.abs(Integer.valueOf((String)aa.get(1)));
				ALL_Minus_Money+=  Math.abs(Integer.valueOf((String)aa.get(1)));
				number++;
				count++ ;
			}
			
			//取得薪資扣項總明細(津貼基本資料設定)
			ALL=this.Allowance_Less_than;//全部有使用到的扣項
			data = (List) dataMap.get("OTHER_Allowance_Minus");
			bb=this.check(ALL,data);
			for (int i = 0; i < bb.size(); i++) {
				List aa = (List) bb.get(i);
				if (type.equals("C")) {
					this.Alignment=jxl.format.Alignment.CENTRE;
					this.setDetail(count , "B", String.valueOf(aa.get(0)), false); // 
				}
				this.Alignment=jxl.format.Alignment.RIGHT;
				//this.setDetail(count , type, Math.abs(Integer.valueOf((String)aa.get(1)))+"", true); // 全勤獎金
				this.setDetail(count , type, Math.abs(Integer.valueOf((String)aa.get(1))),true,"##,###");
				this.objTotal[0][number] += Math.abs(Integer.valueOf((String)aa.get(1)));
				this.objTotal[1][number] += Math.abs(Integer.valueOf((String)aa.get(1)));
				ALL_Minus_Money+= Math.abs(Integer.valueOf((String)aa.get(1)));
				number++;
				count++ ;
			}
			
			
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.setDetail(count, "B", "其他扣款", false); // 
			}
			this.Alignment=jxl.format.Alignment.RIGHT;
			//this.setDetail(count , type, (Integer)dataMap.get("其他費用扣款")+"",true);  //扣薪金額
			this.setDetail(count , type, (Integer)dataMap.get("其他費用扣款"),true,"##,###");
			this.objTotal[0][number] += (Integer)dataMap.get("其他費用扣款");
			this.objTotal[1][number] += (Integer)dataMap.get("其他費用扣款");
			ALL_Minus_Money+= (Integer)dataMap.get("其他費用扣款");
			number++;
			count++ ;
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				//this.mergeCell(count+4, "A", count+4,"B", "應扣金額");// 合併儲存格
				this.setDetail(count, "B", "應扣金額", false); // 
			}
			this.Alignment=jxl.format.Alignment.RIGHT;
			//this.setDetail(count , type, ALL_Minus_Money+"",true);  //應扣合計
			this.setDetail(count , type, ALL_Minus_Money,true,"##,###");
			this.objTotal[0][number] += ALL_Minus_Money;
			this.objTotal[1][number] += ALL_Minus_Money;
			number++;
			count++ ;
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.mergeCell(count+4, "A", count+4,"B", "實發金額");// 合併儲存格
				//this.setDetail(count, "B", "實發金額", false); // 
			}
			this.Alignment=jxl.format.Alignment.RIGHT;
			//this.setDetail(count , type, (Integer)dataMap.get("實發金額")+"",true);  //應扣合計
			this.setDetail(count , type,  (Integer)dataMap.get("實發金額"),true,"##,###");
			this.objTotal[0][number] += (Integer)dataMap.get("實發金額");
			this.objTotal[1][number] += (Integer)dataMap.get("實發金額");
			
			this.MUNBER_MAX=count-8;
			number++;
			count++ ;

			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.mergeCell(count+4, "A", count+4,"B", "蓋章");// 合併儲存格
				//this.setDetail(count, "B", "蓋章", false); // 
				count++ ;
			}
			
			if (type.equals("C")) {
				this.Alignment=jxl.format.Alignment.CENTRE;
				this.mergeCell(count+4, "A", count+4,"B", "備註");// 合併儲存格
				//this.setDetail(count, "B", "備註", false); //
				//count++ ;
			}
			if (type.equals("C")) {
				for (int i = 0; i < 2; i++) {
					boolean change_Size=true;
					if(i!=0){
						change_Size=false;
					}
					super.CopyCellFormat(count+3+i, "C", 7, "C",change_Size);// 複製格式
					super.CopyCellFormat(count+3+i, "D", 7, "C",change_Size);// 複製格式
					super.CopyCellFormat(count+3+i, "E", 7, "C",change_Size);// 複製格式
					super.CopyCellFormat(count+3+i, "F", 7, "C",change_Size);// 複製格式
					super.CopyCellFormat(count+3+i, "G", 7, "C",change_Size);// 複製格式
					super.CopyCellFormat(count+3+i, "H", 7, "C",change_Size);// 複製格式
					super.CopyCellFormat(count+3+i, "I", 7, "C",change_Size);// 複製格式
					super.CopyCellFormat(count+3+i, "J", 7, "C",change_Size);// 複製格式
					
					//super.CopyCellFormat(count+3+i, "K", 7, "C");// 複製格式
					//super.CopyCellFormat(count+3+i, "L", 7, "C");// 複製格式
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param ALL 
	 * @param data
	 * @return
	 */
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
				sql+=" SELECT IFNULL(Count(EHF030600T3_05),0) AS 加項數量" +
					 " From(" ;
			}
			
			sql+="  SELECT  EHF030600T3_02, EHF030600T3_05, EHF030600T3_M1" +
			"  FROM EHF030600T3 " +
			"  " +
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
			" GROUP BY EHF030600T3_05" ;
			
			
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
					dataList.add((String) rs.getString("EHF030600T3_05"));
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
				" WHERE 1 = 1 " +
				" AND EHF030601T1_07 = '"+compId+"'" +
				" AND EHF030601T1_04 "+Compare+"0" +
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
	 * @param compId
	 * @param printlist
	 * @return
	 */
	private Map Add_items(Connection conn, String compId, List printlist,String EHF030600T1_02) {
		// TODO Auto-generated method stub
		List All_Additems = new LinkedList<String>();
		List All_Reduction = new LinkedList<String>();
		Map printMap = new HashMap();
		
		int i=0;
		int j=0;
		try{
			
			Iterator it = printlist.iterator();
			String UID="";
			while(it.hasNext()){
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
			" WHERE 1 = 1 " +
			" AND EHF030600T1_01 = "+UID+" " +  //薪資計算序號
			" AND EHF030600T1_08 = '"+compId+"'" ;
			if(!"".equals(EHF030600T1_02)){
				sql+=" AND EHF030600T1_02='"+EHF030600T1_02+"'" ;
			}
			sql+=" GROUP BY EHF030600T0_01" ;
			
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
	
}
