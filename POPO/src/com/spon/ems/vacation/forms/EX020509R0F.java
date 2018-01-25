package com.spon.ems.vacation.forms;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import jxl.format.Colour;

import com.spon.utils.util.BA_EXCEL;

public class EX020509R0F extends BA_EXCEL {
	private int headsize = 10; // 標題個數

	// 存放標題用的陣列
	// HeadData[0][] 資料列
	// HeadData[1][] 欄位代號
	// HeadData[2][] 資料內容
	// HeadData[3][] 零是否列印 Y 印 N 不印
	// HeadData[4][] 資料格式 如 "##,###" or "0.00000"
	private String HeadData[][];
	public float objTotal[][]; // 計算合計用的

	/**
	 * 一般建構子
	 * 
	 */
	public EX020509R0F(Connection conn, String rpt, String SC0030_01)
			throws Exception {

		// 樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF020509R0.xls";

		// sheet的名稱
		super.SheetName = "加班休假月報表";

		// 樣板一頁總列數
		super.PageRowCount = 25;

		//是否需要使用顏色
		//super.Need_color="YES";
		
		// super.PageColCount = 10;

		// 樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 22;

		// 合計物件 => [0]頁計 , [1]總計
		objTotal = new float[2][8];

		// 一筆資料用了幾列
		super.RecordRow = 1;

		// 第一筆資料要丟的列數
		super.RecordStart = 4;

		// 標題設定標題資料個數
		setHeadSize(headsize);

		// 開始產生Excel
		super.init(conn, rpt, SC0030_01);
	}

	/**
	 * 公司別建構子
	 * 
	 */
	public EX020509R0F(Connection conn, String rpt, String SC0030_01,HttpServletRequest request) throws Exception {

		// 樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF020509R0.xls";

		// sheet的名稱
		super.SheetName = "加班休假月報表";

		// 樣板一頁總列數
		super.PageRowCount = 25;
		
		//是否需要使用顏色
		//super.Need_color="YES";
		
		// super.PageColCount = 10;

		// 樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 22;

		// 合計物件 => [0]頁計 , [1]總計
		objTotal = new float[2][8];

		// 一筆資料用了幾列
		super.RecordRow = 1;

		// 第一筆資料要丟的列數
		super.RecordStart = 4;

		// 標題設定標題資料個數
		setHeadSize(headsize);

		// 設定request
		super.request = request;

		// 開始產生Excel
		super.init(conn, rpt, SC0030_01);
	}

	/**
	 * 新的一頁開始之前(通常是丟頁計用的) 如第一頁要換頁前須先將第一頁的頁計寫入，然後才能進入第二頁
	 */
	public void BeforeChangePage() throws Exception {

	}

	/**
	 * 丟總計
	 * 
	 */
	public void EndDocument() throws Exception {

	}
	public void print(Colour  Colour)throws Exception{
		//super.colour=Colour;
		//super.BorderLine= BorderLineStyle.THIN;

	}

}
