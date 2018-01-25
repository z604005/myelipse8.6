package com.spon.ems.popo.forms;

import com.spon.utils.util.BA_EXCEL;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import jxl.write.WritableImage;


public class EX330600R0F extends BA_EXCEL {

	private int headsize = 12; //標題個數
		
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
		public EX330600R0F(Connection conn,String rpt,String SC0030_01) throws Exception{
			
			//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
			super.TemplateName = "EHF330600R0.xls";
			
			//sheet的名稱
			super.SheetName = "客戶名牌標籤";
				
			//樣板一頁總列數
			super.PageRowCount = 38;
			
//			super.PageColCount = 10;
				
			//樣板一頁可放資料筆數,不管一筆資料使用了幾列
			super.PageDataCount = 16;
			
			//合計物件 => [0]頁計 , [1]總計
			objTotal = new long[2][8];
				
			//一筆資料用了幾列
			super.RecordRow = 1;

			//第一筆資料要丟的列數
			super.RecordStart = 7;
			
			//標題設定標題資料個數
			setHeadSize(headsize);
				
			//開始產生Excel
			super.init(conn,rpt,SC0030_01);
		}
		
		/** 
		 * 公司別建構子
		 * 
		 */
	public EX330600R0F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request,File imageFile) throws Exception{
			
			//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
			super.TemplateName = "EHF330600R0.xls";
			
			//sheet的名稱
			super.SheetName = "客戶名牌標籤";
			
			super.imageFile = imageFile;
				
			//樣板一頁總列數
			//super.PageRowCount = 39;
			super.PageRowCount = 16;
//			
//			super.PageColCount = 10;
				
			//樣板一頁可放資料筆數,不管一筆資料使用了幾列
			//super.PageDataCount = 33;
			super.PageDataCount = 1;
			
			//合計物件 => [0]頁計 , [1]總計
			objTotal= new long[2][8];
				
			//一筆資料用了幾列
			super.RecordRow = 16;

			//第一筆資料要丟的列數
			super.RecordStart = 1;
			
			//標題設定標題資料個數
//			setHeadSize(headsize);
			
//			設定request
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
//			super.setValue(25,"J",this.objTotal[0][0],true,"#,###");
			
			
			//頁計放入總計
//			this.objTotal[1][0] += this.objTotal[0][0];

			//將頁計清空
//			this.objTotal[0] = new long[7];
		}
		
		/** 
		* 丟總計
		 * @param rs_total 
		 * @param rs_total 
		* 
		*/
//		public void EndDocument(ResultSet rs_total) throws Exception{
//			//---範例參考---start
//			//丟最後一頁的頁計
////			this.BeforeChangePage();
//			
//			//丟總計
//			//super.setValue(25,"A","合計",false);
//			//super.setValue(25,"J",this.objTotal[1][0],true,"#,###");
//			//---範例參考---end
//			//StringBuffer MSG = new StringBuffer();
//			int i = 0;
//			int dataCount = 0;
//			while(rs_total.next()){
//				if(dataCount == 0){
//					super.setValue(33+i,"A",rs_total.getString("類別")+"："+rs_total.getString("總計"),false);
//				}
//				if(dataCount == 1){
//					super.setValue(33+i,"C",rs_total.getString("類別")+"："+rs_total.getString("總計"),false);
//				}
//				if(dataCount == 2){
//					super.setValue(33+i,"F",rs_total.getString("類別")+"："+rs_total.getString("總計"),false);
//				}
//				if(dataCount == 3){
//					super.setValue(33+i,"I",rs_total.getString("類別")+"："+rs_total.getString("總計"),false);
//					i++;
//				}			
//				dataCount++;
//				if(dataCount > 3){
//					dataCount = 0;
//				}
//			}
//			this.print_Picture();	
//			
//		}
		public void print_Picture()throws Exception{
			//jxl插入圖片
			//1. 圖片必須是png檔，所以取得圖片後，不論是bmp, jpg, png, gif等...皆轉成png檔
			//java.io.File imageFile = new java.io.File(getServlet().getServletContext().getRealPath("") + "/pic/2011060005.png");
			BufferedImage input = ImageIO.read(imageFile);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(input, "PNG", baos);
			double width = 1, height = 1;
			double x_shift = 0, y_shift = 0;
							
			//2. 計算圖片插入cell的偏移量，使圖片置中並保持與原圖片寬高等比例
			//註: 樣板設計圖片的CELL必須為1格，且寬高必須相等，但不適用於多欄位合併為一格
			if(input.getWidth() > input.getHeight()){
				height = (double)(input.getHeight())/(double)(input.getWidth());
				y_shift = (width - height)/2;
			}else{
				width = (double)(input.getWidth())/(double)(input.getHeight());
				x_shift = (height - width)/2;
			}
							
			//3. 再取得BA_EXCEL的sheet直接建立圖片物件到指定"欄位"
			//建構子方法一: WritableImage(double x, double y, double width, double height, byte[] imageData) 
			//建構子方法二: WritableImage(double x, double y, double width, double height, java.io.File image)
			//x: 水平軸起始欄位，由0開始，例:0.5表示起始位置為第0.5格水平欄位
			//y: 垂直軸起始欄位，由0開始
			//width: 圖片占用水平軸欄位數，例:2.5表示占用2.5個水平欄位
			//height: 圖片占用垂直軸欄位數
			//因為插入圖片只能以起始點的欄位為基礎寬高來放大縮小圖片，所以樣板設計時要特別注意圖片欄位的格數、寬高
			sheet.addImage(new WritableImage(0.0, sheet.getRows()-6.9, 1.70,3.89, baos.toByteArray()));
			sheet.addImage(new WritableImage(3.0, sheet.getRows()-6.9, 1.70,3.89, baos.toByteArray()));
			sheet.addImage(new WritableImage(6.0, sheet.getRows()-6.9, 1.70,3.89, baos.toByteArray()));		
			
//			sheet.addImage(new WritableImage(1.0, sheet.getRows()-9.9, 1.89,3.89, baos.toByteArray()));
//			sheet.addImage(new WritableImage(3.0, sheet.getRows()-9.9, 1.89,3.89, baos.toByteArray()));
//			sheet.addImage(new WritableImage(5.0, sheet.getRows()-9.9, 1.89,3.89, baos.toByteArray()));
		}

}
