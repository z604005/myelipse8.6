package com.spon.utils.sc.forms;
import com.spon.utils.util.BA_EXCEL;

import java.sql.Connection;

/** 
 * 列印EXCEL用的元件
 * Creation date: 02-01-2006
 * 
 * XDoclet definition:
 * 
 */
public class EXRP002F extends BA_EXCEL{
	private int headsize = 0; //標題個數
	
//	存放標題用的陣列
//	HeadData[0][] 資料列
//	HeadData[1][] 欄位代號
//	HeadData[2][] 資料內容
//	HeadData[3][] 零是否列印 Y 印 N 不印
//	HeadData[4][] 資料格式 如 "##,###" or "0.00000"
	private String HeadData[][];
//	              objdata[]; //計算合計用的

	/** 
	 * 傳入樣板的excel檔案
	 * Creation date: 02-01-2006
	 */
	public EXRP002F(Connection conn,String rpt,String SC0030_01) throws Exception{
		//樣本相關設定
			//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
			super.TemplateName="RP002_01.xls";
			//產生後sheet的名稱
			super.SheetName = "差異明細檔";
			
			super.PageRowCount = 52;
			
			//樣板一頁可放資料總筆數,不管一筆資料使用了幾列
			super.PageDataCount=25;

			//一筆資料用了幾列
			super.RecordRow = 2;

			//第一筆資料要丟的列數
			super.RecordStart = 3;
			
//			標題設定標題資料個數
			setHeadSize(26);
			
//			下面的陣列昰計算合計用的，本報表因為有頁計和合計 要統計的資料也有兩個 所以是 [2][2]
			 
			
			
			//開始產生Excel
			super.init(conn,rpt,SC0030_01);	
	}
	
	/** 
	 * 新的一頁開始之前(通常是丟頁計用的)
	 * 如第一頁要換頁前須先將第一頁的頁計寫入，然後才能進入第二頁
	 */
	public void BeforeChangePage() throws Exception{
		//丟頁計
//			//頁計
//			super.setValue(36,"A","頁計",false);
////			總筆數：
//			super.setValue(36,"B","筆數小計：",false);
//			super.setValue(36,"C",objTotal[0][0]+"",false);
////			總金額：
//			super.setValue(36,"D","金額小計：",false);
//			super.setValue(36,"E",objTotal[0][1],false,"##,###");
//		
//		//將頁計清空
//		objTotal[0] = new long[2];
	}
	
	/** 
	 * 丟總計
	 * 
	 */
	public void EndDocument() throws Exception{
		//丟頁計
//			//頁計
//			super.setValue(36,"A","頁計",false);
////			總筆數：
//			super.setValue(36,"B","筆數小計：",false);
//			super.setValue(36,"C",objTotal[0][0]+"",false);
////			總金額：
//			super.setValue(36,"D","金額小計：",false);
//			super.setValue(36,"E",objTotal[0][1],false,"##,###");
//		//將頁計清空
//			objTotal[0] = new long[2];
//			
//			
////		丟合計
////			因為合計要放在37但是樣板只有36所以要複製36的格式來處理(這樣才有格線)
//			super.CopyCellFormat(37,"A",36,"A");
//			super.CopyCellFormat(37,"B",36,"B");
//			super.CopyCellFormat(37,"C",36,"C");
//			super.CopyCellFormat(37,"D",36,"D");
//			super.CopyCellFormat(37,"E",36,"E");
//			//合計
//			super.setValue(37,"A","合計",false);
//	//		總筆數：
//			super.setValue(37,"B","總筆數：",false);
//			super.setValue(37,"C",objTotal[1][0]+"",false);
//	//		總金額：
//			super.setValue(37,"D","總金額：",false);
//			super.setValue(37,"E",objTotal[1][1],false,"##,###");
//			
//
//			
//			//將頁計清空
//			objTotal[1] = new long[2];
		
	}
}


