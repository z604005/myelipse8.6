package com.spon.ems.popo.forms;
import java.sql.Connection;
//import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.spon.ems.salary.actions.EHF030600M1A;
import com.spon.utils.util.BA_EXCEL;
/**
 *@author maybe
 *@version 1.0
 *@created 2016/11/14 下午3:26:50
 */
public class EX331200R0F extends BA_EXCEL {
	
	private int headsize = 2; //標題個數
		
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
	public EX331200R0F(Connection conn,String rpt,String SC0030_01) throws Exception{
			
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF331200R0.xls";
			
		//sheet的名稱		
		super.SheetName = "贈品明細報表";

				
		//樣板一頁總列數
		super.PageRowCount = 46;
		//super.PageRowCount = 5000;
//		super.PageColCount = 10;
				
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		//super.PageDataCount = count;
		super.PageDataCount = 43;
			
		//合計物件 => [0]頁計 , [0]總計
		objTotal = new long[10][10];
				
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 4;
			
		//標題設定標題資料個數
		setHeadSize(headsize);
				
		//開始產生Excel
		super.init(conn,rpt,SC0030_01);
	}
		
	/** 
	 * 公司別建構子
	 * 
	 */
	public EX331200R0F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request, int count) throws Exception{
			
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF331200R0.xls";
			
		//sheet的名稱
		super.SheetName = "贈品明細報表";
		
		//樣板一頁總列數
		//super.PageRowCount = count+2;
		super.PageRowCount = 46;
//		super.PageColCount = 10;
				
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 43;
			
		//合計物件 => [0]頁計 , [0]總計
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


	
}
