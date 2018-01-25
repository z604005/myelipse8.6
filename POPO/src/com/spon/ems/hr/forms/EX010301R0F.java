package com.spon.ems.hr.forms;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import com.spon.utils.util.BA_EXCEL;

public class EX010301R0F extends BA_EXCEL {
	
private int headsize = 16; //標題個數
	
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
	public EX010301R0F(Connection conn,String rpt,String SC0030_01) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010301R0.xls";
		
		//sheet的名稱
		super.SheetName = "休假計數清單";
			
		//樣板一頁總列數
		super.PageRowCount = 45;
		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 45;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal = new long[2][8];
			
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
	 * 
	 */
	public EX010301R0F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010301R0.xls";
		
		//sheet的名稱
		super.SheetName = "休假計數清單";
			
		//樣板一頁總列數
		super.PageRowCount = 45;
//		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 45;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal= new long[0][1];
			
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
		
		//丟頁計
//		super.setValue(25,"J",this.objTotal[0][0],true,"#,###");
		
		
		//頁計放入總計
//		this.objTotal[1][0] += this.objTotal[0][0];

		//將頁計清空
//		this.objTotal[0] = new long[7];
	}
	
	/** 
	* 丟總計
	* 
	*/
	public void EndDocument() throws Exception{
		
		//丟最後一頁的頁計
//		this.BeforeChangePage();
		
		//丟總計
		//super.setValue(25,"A","合計",false);
		//super.setValue(25,"J",this.objTotal[1][0],true,"#,###");
		
	}

}
