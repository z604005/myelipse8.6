package com.spon.ems.hr.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spon.utils.util.BA_EXCEL;

public class EX010111R0F extends BA_EXCEL {
	
private int headsize = 3; //標題個數
	
	//	存放標題用的陣列
	//	HeadData[0][] 資料列
	//	HeadData[1][] 欄位代號
	//	HeadData[2][] 資料內容
	//	HeadData[3][] 零是否列印 Y 印 N 不印
	//	HeadData[4][] 資料格式 如 "##,###" or "0.00000"
	private String HeadData[][];
	public long objTotal[][]; //計算合計用的
	
	/** 
	 * 一般建構子
	 * 
	 */
	public EX010111R0F(Connection conn,String rpt,String SC0030_01) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010111R0.xls";
		
		//sheet的名稱
		super.SheetName = "員工通訊錄";
			
		//樣板一頁總列數
		super.PageRowCount = 80;
		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 40;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal = new long[0][0];
			
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 3;
		
		//標題設定標題資料個數
		setHeadSize(headsize);
			
		//開始產生Excel
		super.init(conn,rpt,SC0030_01);
	}
	
	/** 
	 * 公司別建構子
	 * 
	 */
	public EX010111R0F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010111R0.xls";
		
		//sheet的名稱
		super.SheetName = "員工通訊錄";
			
		//樣板一頁總列數
		super.PageRowCount = 80;
//		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 40;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal= new long[0][0];
			
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 3;
		
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
	 * 列印表頭資料
	 * @param salYYMM
	 * @param costYYMM
	 * @param print_date
	 * @param comp_name
	 * @param report_name
	 */
	public void printHeadValue( String salYYMM, String costYYMM, String print_date, String comp_name, String report_name ){
		
		try{			
			this.setHeadValue(0,2,"A","列印日期:"+print_date+"",false,"");	//列印日期							
			this.setHeadValue(1,1,"A",comp_name+""+report_name,false,""); //列印公司抬頭						
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	public int print(Connection conn, ResultSet rs, String comp_id) {
		// TODO Auto-generated method stub
		
		int DataCount = 0;
		int i = 0;
		Map printMap = new HashMap();
		
		try{
			while(rs.next()) {
				
				printMap.put("序號", DataCount);
				printMap.put("部門", rs.getString("HR_DepartmentName"));
				printMap.put("職稱", rs.getString("HR_JobName"));
				printMap.put("員工工號", rs.getString("HR_EmployeeNo"));
				printMap.put("姓名", rs.getString("EHF010106T0_04"));
				printMap.put("手機", rs.getString("EHF010106T0_15"));
				printMap.put("連絡電話", rs.getString("EHF010106T0_13"));
				printMap.put("戶籍電話", rs.getString("EHF010106T0_11"));
				printMap.put("連絡地址", rs.getString("EHF010106T0_14"));
				printMap.put("戶籍地址", rs.getString("EHF010106T0_12"));
				
				for(i=0;i<2;i++){
					//列印一筆資料
					this.printDetail( printMap, i );
				}
				
				DataCount++;
				
			}
			rs.close();
			
			this.EndDocument();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return DataCount;
	}

	private void printDetail(Map printMap, int displacement) {
		// TODO Auto-generated method stub
		
		try{
			if(displacement==0){
				
				this.setHeadValue(2,2,"I","頁        次:"+(this.getNowPageNum()+1)+"",false,"");
				
				this.next();  //換下一行
				
				this.setDetail(1,"A", (Integer)printMap.get("序號")+"",false);  //
				this.setDetail(1,"C", (String)printMap.get("部門")+"/"+(String)printMap.get("職稱"),false);  //
				this.setDetail(1,"E", (String)printMap.get("姓名")+"",false);  //
				this.setDetail(1,"G", (String)printMap.get("連絡電話")+"",false);  //
				this.setDetail(1,"I", (String)printMap.get("連絡地址")+"",false);  //
				
			}
			
			if(displacement==1){
				
				this.next();  //換下一行
				
				this.setDetail(1,"C", (String)printMap.get("員工工號")+"",false);  //
				this.setDetail(1,"E", (String)printMap.get("手機")+"",false);  //
				this.setDetail(1,"G", (String)printMap.get("戶籍電話")+"",false);  //
				this.setDetail(1,"I", (String)printMap.get("戶籍地址")+"",false);  //
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
