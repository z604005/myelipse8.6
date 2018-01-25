package com.spon.utils.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Date;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WritableFont.FontName;

import com.spon.utils.sc.actions.SC006A;


/** 
 * 列印EXCEL用的元件
 * Creation date: 02-01-2006
 * 
 * XDoclet definition:
 * 
 */
public class BA_EXCEL_HONGCHI {
	
	private String filename="";
	protected WritableWorkbook workbook = null;  //存放資料用的
	protected WritableSheet sheet = null; //存放資料用的
	
	protected Workbook swk = null; //樣板用
	protected Sheet Tsheet= null ; //樣板用的sheet
	protected FileOutputStream os = null;
	protected int rowCount = 0; //目前頁次的第幾筆
	protected int PageCout = 0 ; //目前頁數
	
	//以下資料須調整
	public String TemplateName="";
	protected String SheetName = "";
	protected int PageRowCount = 0; 	//樣板的總比數
	protected int PageColCount = 0; 	//樣板總欄位數
	protected int PageDataCount = 0; //樣板一頁可放資料總筆數,不管一筆資料使用了幾列
	protected int RecordRow = 0; //一筆資料用了幾列
	protected int RecordStart = 0; //一筆資料要丟的位置
	protected File imageFile;
	
	
	private String HeadData[][];
	private int headsize = 0; //標題個數
	
	protected HttpServletRequest request = null;   //傳遞當前request
	
	
	protected Colour colour=Colour.BLACK;//設定初始顏色
	protected BorderLineStyle BorderLine=BorderLineStyle.THIN;//設定線條粗細(初始細線)
	
	protected Alignment Alignment_CENTRE=jxl.format.Alignment.CENTRE;//把水平對齊方式指定為居中 
	protected VerticalAlignment VerticalAlignment_CENTRE=jxl.format.VerticalAlignment.CENTRE;//把垂直對齊方式指定為居中 
	
	protected Alignment Alignment_LEFT=jxl.format.Alignment.LEFT;//把水平對齊方式指定為靠左
	protected VerticalAlignment VerticalAlignment_TOP=jxl.format.VerticalAlignment.TOP;//把垂直對齊方式指定為至頂 
	
	
	protected FontName createFont=WritableFont.createFont("新細明體");
	protected int size=12;
	
	
	/** 
	 * 取得目前的頁次
	 */
	public int getNowPageNum()throws Exception{
		return PageCout;
	}
	
	/** 
	 * 取得目前的頁次的資料筆數
	 */
	public int getNowPageRecordNum()throws Exception{
		return rowCount;
	}
	
	/** 
	 * 強迫換頁(注意：需搭配 next() 使用且須在 next()之前
	 */
	public void setNowPage()throws Exception{
		rowCount=0;
	}
	
	/** 
	 * 強迫換sheet(注意：需搭配 next() 使用且須在 next()之前
	 */
	public void setSheet(WritableSheet sheet)throws Exception{
		this.sheet=sheet;
	}
	/** 
	 * 強迫換sheet(注意：需搭配 next() 使用且須在 next()之前
	 */
	public void setSheet(Sheet Tsheet)throws Exception{
		this.Tsheet=Tsheet;
	}
	/** 
	 * 取得目前的頁次
	 */
	public WritableWorkbook getWorkbook()throws Exception{
		return workbook;
	}
	/** 
	 * 設定樣板的欄位數
	 * pageColName 如樣版有5個欄位，則傳入 "E" 
	 * Creation date: 02-01-2006
	 */
	public void setPageColCount(String pageColName)throws Exception{
		PageColCount = trueCol(pageColName) +1;
	}

	/** 
	 * 傳入樣板的excel檔案
	 * Creation date: 02-01-2006
	 */
	protected void init(Connection conn,String rpt,String SC0030_01) throws Exception{
		 
//		下面的都不要改
		BA_REPORT bar=new BA_REPORT();
		String REPORT_PATH="";
		String Rpt="";
		String osName = System.getProperty("os.name");
		SC006A sc006a=new SC006A();
		//增加request傳遞
		if(request != null){
			if (osName.equals("Linux")) {
				REPORT_PATH=sc006a.getSysParam(conn,null,request,"REPORT_PATH_LINUX");
			} else {
				REPORT_PATH=sc006a.getSysParam(conn,null,request,"REPORT_PATH_WINDOWS");
			}
		}else{
			if (osName.equals("Linux")) {
				REPORT_PATH=sc006a.getSysParam(conn,null,null,"REPORT_PATH_LINUX");
			} else {
				REPORT_PATH=sc006a.getSysParam(conn,null,null,"REPORT_PATH_WINDOWS");
			}
		}
		Rpt=rpt+"/excelrpt/";
		bar.Check_Tmp_Path(rpt, REPORT_PATH, SC0030_01);
		filename=REPORT_PATH+"/reports/"+SC0030_01+"/"+new Date().getTime()+".xls";
		swk = Workbook.getWorkbook(new File(Rpt+"//" + TemplateName));
		os = new FileOutputStream(filename);
		workbook = Workbook.createWorkbook(os,swk);
		sheet = workbook.getSheet(0);
		Tsheet = swk.getSheet(0);
		//		刪除已存在的內容
		int rows = sheet.getRows();
		for(int j=0;j<rows;j++){ //列數
			sheet.removeRow(0);
		}
		if (PageRowCount==0){
			PageRowCount = Tsheet.getRows();
		}
		if (PageColCount==0){
			PageColCount = Tsheet.getColumns();
		}
		sheet.setName(SheetName);
	}
	
	/** 
	 * 寫入Excel檔案
	 * Creation date: 02-01-2006
	 * 
	 * XDoclet definition:
	 * 
	 */
	public String  write() throws Exception{
		try{
			//關掉Excel
			swk.close();
			//寫入資料
			workbook.write();
			//關掉Excel
			workbook.close();
			os.close();
		}catch (Exception E){
			
		}
		return filename;
	}
	
	
	/** 
	 * colName 所要丟的資料的Col如 "A" or "AB"
	 * return Excel上面真實的欄數,(從0開始) 
	 * 
	 */
	
	protected int trueCol(String colName) throws Exception{
		byte[] ba = colName.getBytes();
		int DataCol =0;
		if (ba.length > 1)
			DataCol = ((ba[0]-64)*26) + (ba[1]-65);
		else
			DataCol = (ba[0]-65);
		return DataCol;
	}
	
	
	/** 
	 *丟下一筆資料 
	 */
	public void next() throws Exception{
		if (rowCount==0){ //第一次進入
//			設定頁面格式
			newPage();			
		}else{
			//頁計
			if ( rowCount >= PageDataCount ){
				rowCount = 0;
//				設定頁面格式
				newPage();	
			}
		}
		rowCount ++;
	}
	
	/** 
	 * 設定標題用(新的一頁產生時會執行本method),每一頁換頁前都會執行
	 */
	protected void setPageHead() throws Exception{
		for(int i=1;i<=headsize;i++){
			if (!HeadData[2][i-1].trim().equals("")){
				if (HeadData[4][i-1].trim().equals("")){
					if (HeadData[3][i-1].trim().equals("Y")){
						setValue(Integer.parseInt(HeadData[0][i-1]),HeadData[1][i-1],HeadData[2][i-1],true);
					}else{
						setValue(Integer.parseInt(HeadData[0][i-1]),HeadData[1][i-1],HeadData[2][i-1],false);
					}
				}else{
					if (HeadData[3][i-1].trim().equals("Y")){
						setValue(Integer.parseInt(HeadData[0][i-1]),HeadData[1][i-1],Long.parseLong(HeadData[2][i-1]),true,HeadData[4][i-1]);
					}else{
						setValue(Integer.parseInt(HeadData[0][i-1]),HeadData[1][i-1],Long.parseLong(HeadData[2][i-1]),false,HeadData[4][i-1]);
					}
				}
			}
		}
	}

	/** 
	 * 合併儲存格
	 * VrowTopLeft 一筆資料也許有兩列以上，所要合併儲存格左上的資料在第幾列(通常為1)
	 * colTopLeftName 要合併儲存格的左上Col如 "A" or "AB"
	 * VrowBottomRight 一筆資料也許有兩列以上，所要合併儲存格右下的資料在第幾列(通常為1)
	 * colBottomRightName 要合併儲存格右下的Col如 "A" or "AB"
	 */
	public void mergeDetailCells(int VrowTopLeft,String colTopLeftName,int VrowBottomRight,String colBottomRightName) throws Exception{
//		取得位置
		int rowTopLeft = trueRow(VrowTopLeft);
		int rowBottomRight = trueRow(VrowBottomRight);
		mergeCells(rowTopLeft,colTopLeftName,rowBottomRight,colBottomRightName);
	}
	
	/** 
	 * 合併儲存格
	 * VrowTopLeft 一筆資料也許有兩列以上，所要合併儲存格左上的資料在第幾列(通常為1)
	 * colTopLeft 要合併儲存格的左上欄位 (從1開始) Col如 "A"則傳 1 "B"則傳2
	 * VrowBottomRight 一筆資料也許有兩列以上，所要合併儲存格右下的資料在第幾列(通常為1)
	 * colBottomRight 要合併儲存格右下欄位 (從1開始) Col如 "A"則傳 1 "B"則傳2
	 */
	public void mergeDetailCells(int VrowTopLeft,int colTopLeft,int VrowBottomRight,int colBottomRight) throws Exception{
//		取得位置
		int rowTopLeft = trueRow(VrowTopLeft);
		int rowBottomRight = trueRow(VrowBottomRight);
		mergeCells(rowTopLeft,colTopLeft,rowBottomRight,colBottomRight);
	}
	/** 
	 * 設定標題用(新的一頁產生時會執行本method),每一頁換頁前都會執行
	 */
	public void setHeadSize(int size) throws Exception{
//		存放標題用的陣列
		HeadData= new String[5][size];
		headsize = size;
		for(int i =0;i<size;i++){
			HeadData[0][i]= "";
			HeadData[1][i]= "";
			HeadData[2][i]= "";
			HeadData[3][i]= "Y";
			HeadData[4][i]= "";
		}
	}
	/*
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列
	 * colName 在每一頁的Col 如 "A" or "AB"
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 * strFormat 格式化資料，如 "##,###" or "0.00000000"
	 * 通常在呼叫 next() mothed 之前設定表頭資料
	 */
	public void setHeadValue(int arrIndex,int Trow,String colName,String Sdata,boolean PrintZero,String strFormat) throws Exception{
		HeadData[0][arrIndex]= Trow +"";
		HeadData[1][arrIndex]= colName +"";
		HeadData[2][arrIndex]= Sdata +"";
		if (PrintZero){
			HeadData[3][arrIndex]= "Y";
		}else{
			HeadData[3][arrIndex]= "N";
		}
		HeadData[4][arrIndex]= strFormat +"";
	}
	
	/** 
	 * 新的一頁開始之前(通常是丟頁計用的)
	 * 如第一頁要換頁前須先將第一頁的頁計寫入，然後才能進入第二頁
	 */
	public void BeforeChangePage() throws Exception{
	}
	
	
	
	/** 
	 * 複製格式(指定某一格)
	 * Drow 複製到目前頁面的列 (從1開始)
	 * DcolName 複製到目前頁面的欄位名稱
	 * Srow 要複製樣板的列 (從1開始)
	 * ScolName 要複製樣板的欄位名稱
	 */
	public void CopyCellFormat(int Drow,String DcolName,int Srow,String ScolName) throws Exception{
		int Scol = trueCol(ScolName)+1;
		int Dcol = trueCol(DcolName)+1;
		CopyCellFormat(Drow,Dcol,Srow,Scol);
	}
	
	/** 
	 * 複製格式(指定某一格)
	 * Drow 複製到目前頁面的列 (從1開始)
	 * Dcol 複製到目前頁面的欄位  (從1開始)
	 * Srow 要複製樣板的列 (從1開始)
	 * Scol 要複製樣板的欄位 (從1開始)
	 */
	public void CopyCellFormat(int Drow,int Dcol,int Srow,int Scol) throws Exception{
		int scol = Scol-1 ;
		int dcol = Dcol-1;
		int row = (PageCout-1) * PageRowCount;
		Blank blank = null;
		Label labelCF = null;
		blank=new Blank(dcol,Drow-1+row);
		sheet.addCell(blank);
		if ((Tsheet.getCell(scol,Srow-1).getContents()+"").equals("")){
			blank=new Blank(dcol,Drow-1+row);
			sheet.addCell(blank);
		}else{
			labelCF=new Label(dcol,Drow-1+row,Tsheet.getCell(scol,Srow-1).getContents());
			sheet.addCell(labelCF);
		}
		sheet.getWritableCell(dcol,Drow-1+row).setCellFormat(Tsheet.getCell(scol,Srow-1).getCellFormat());
	}
	
	
	/** 
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列 (從1開始)
	 * colName 在每一頁的Col 如 "A" or "AB"
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 */
	public void setValue(int Trow,String colName,String Sdata,boolean PrintZero) throws Exception{
		//取得位置
		int DataCol =trueCol(colName);
		writeValue(Trow,DataCol,Sdata,PrintZero);
	}
	/** 
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列 (從1開始)
	 * Tcol 在每一頁的地幾欄 (從1開始)
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 */
	public void setValue(int Trow,int Tcol,String Sdata,boolean PrintZero) throws Exception{
		//取得位置
		writeValue(Trow,(Tcol-1),Sdata,PrintZero);
	}
	
	/** 
	 * 插入分頁線
	 */
	public void addRowPageBreak() throws Exception{
//		取得位置
		PageCout++; //頁數加一
		int Row = (PageCout-1) * PageRowCount;
		sheet.addRowPageBreak(Row);
		rowCount=0; //資料位置歸零
		
	}
	
	
	/** 
	 * 換頁
	 */
	protected void newPage() throws Exception{
		CellFormat ss = null ;
		Label labelCF = null;
		Blank blank = null;
		//丟上一頁的頁計
//		丟頁計
		if (PageCout!=0){
			BeforeChangePage();
		}
		PageCout++;
		int Row = (PageCout-1) * PageRowCount;
		sheet.addRowPageBreak(Row);
		for(int i=0;i<PageColCount;i++){ //欄位數
			for(int j=0;j<PageRowCount;j++){ //列數
				if ((Tsheet.getCell(i,j).getContents()+"").equals("")){
					blank=new Blank(i,j+Row);
					sheet.addCell(blank);
				}else{
					labelCF=new Label(i,j+Row, Tsheet.getCell(i,j).getContents());
					sheet.addCell(labelCF);
				}
			}
		}
		//調整欄寬
		for(int i=0;i<PageColCount;i++){ //欄位數
			sheet.setColumnView(i,Tsheet.getColumnView(i));
		}
		for(int j=0;j<PageRowCount;j++){ //列數
			sheet.setRowView(j+Row,Tsheet.getRowView(j).getSize());
		}
		//合併儲存格
		Range rg[]= null;
		rg = Tsheet.getMergedCells();
		for (int i =0;i<rg.length;i++){
			sheet.mergeCells(rg[i].getTopLeft().getColumn(),rg[i].getTopLeft().getRow()+Row,rg[i].getBottomRight().getColumn(),rg[i].getBottomRight().getRow()+Row);
		}
		
		for(int i=0;i<PageColCount;i++){ //欄位數
			for(int j=0;j<PageRowCount;j++){ //列數
				ss =Tsheet.getCell(i,j).getCellFormat();
				if (ss!=null){
					sheet.getWritableCell(i,j+Row).setCellFormat(ss);
				}
			}
		}
		//丟標題資料
		setPageHead();
	}
	
	/** 
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列 (從1開始)
	 * DataCol 在每一頁的Col (從0開始)
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 * 修改於20140425 BY 賴泓錡
	 */
	private void writeValue(int Trow,int DataCol,String Sdata,boolean PrintZero) throws Exception{
		//取得位置
		int DataRow = (Trow-1) + ((PageCout-1) * PageRowCount) ;
		CellFormat cf ;
		cf = sheet.getCell(1,6).getCellFormat();
		cf = sheet.getCell(DataCol,DataRow).getCellFormat();
		//丟資料
		Sdata = Sdata +"";
		Label labelCF = null;
		if (Sdata.trim().equals("0")&& !PrintZero)
			labelCF=new Label(DataCol,DataRow,"");
		else
			labelCF=new Label(DataCol,DataRow, Sdata);
		sheet.addCell(labelCF);
		//EDIT BY FELISTUM
		//複製格式
		 if(null != cf)
         {
			 sheet.getWritableCell(DataCol,DataRow).setCellFormat(cf);
         } else
         {
        	 WritableCell cell = sheet.getWritableCell(DataCol,DataRow);
        	 sheet.getWritableCell(DataCol,DataRow).setCellFormat(cell.getCellFormat());
//             lbl.setCellFormat(cell.getCellFormat());
         }
	}
	
	/** 
	 * 寫入資料內容部份
	 * Vrow 一筆資料也許有兩列以上，所要丟的資料在第幾列(通常為1)
	 * DataCol 所要丟的資料的Col  (從0開始)
	 * Sdata 資料內容
	 * PrintZero 0是否列印 true 印 "0" false 空白
	 */
	private void writeDetail(int Vrow,int DataCol,String Sdata,boolean PrintZero) throws Exception{
		//取得位置
		int Trow = trueRow(Vrow);
		//丟資料
		writeValue(Trow,DataCol,Sdata,PrintZero);
	}
	/** 
	 * Vrow 一筆資料可能會有兩列以上，Vrow昰指定該資料的第幾列，通常為1 
	 * return Excel上面真實的列數,第一列為 0 
	 * 
	 */
	protected int trueRow(int Vrow) throws Exception{
		//要丟的位置 = (開始位置-1)+ ((目前筆數-1)*每筆幾列)+ (要丟的位置-1) + ((頁次-1)* 每頁幾列)
		return (RecordStart-1)+((rowCount-1) * RecordRow)+(Vrow-1)+1;
	}
	/** 
	 * 寫入資料內容部份
	 * Vrow 一筆資料也許有兩列以上，所要丟的資料在第幾列(通常為1)
	 * colName 所要丟的資料的Col如 "A" or "AB"
	 * Sdata 資料內容
	 * PrintZero 0是否列印 true 印 "0" false 空白
	 * strFormat 格式化資料，如 "##,###" or "0.00000000"
	 */
	public void setDetail(int Vrow,String ColName,long Sdata,boolean PrintZero,String strFormat) throws Exception{
		DecimalFormat fm = new DecimalFormat(strFormat);
		String strTmp = fm.format(Sdata);
		setDetail(Vrow,ColName,strTmp,PrintZero);
	}
	/** 
	 * 寫入資料內容部份
	 * Vrow 一筆資料也許有兩列以上，所要丟的資料在第幾列(通常為1)
	 * Vcol 所要丟的資料欄位  (從1開始)
	 * Sdata 資料內容
	 * PrintZero 0是否列印 true 印 "0" false 空白
	 * strFormat 格式化資料，如 "##,###" or "0.00000000"
	 */
	public void setDetail(int Vrow,int Vcol,long Sdata,boolean PrintZero,String strFormat) throws Exception{
		DecimalFormat fm = new DecimalFormat(strFormat);
		String strTmp = fm.format(Sdata);
		setDetail(Vrow,Vcol,strTmp,PrintZero);
	}
	/** 
	 * 寫入資料內容部份
	 * Vrow 一筆資料也許有兩列以上，所要丟的資料在第幾列(通常為1)
	 * colName 所要丟的資料的Col如 "A" or "AB"
	 * Sdata 資料內容
	 * PrintZero 0是否列印 true 印 "0" false 空白
	 */
	public void setDetail(int Vrow,String ColName,String Sdata,boolean PrintZero) throws Exception{
		//取得位置
		int DataCol =trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata,PrintZero);
	}
	/** 
	 * 寫入資料內容部份
	 * Vrow 一筆資料也許有兩列以上，所要丟的資料在第幾列(通常為1)
	 * Vcol 所要丟的資料的欄位  (從1開始)
	 * Sdata 資料內容
	 * PrintZero 0是否列印 true 印 "0" false 空白
	 */
	public void setDetail(int Vrow,int Vcol,String Sdata,boolean PrintZero) throws Exception{
		//取得位置
		writeDetail(Vrow,(Vcol-1),Sdata,PrintZero);
	}
	/** 
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列  (從1開始)
	 * colName 在每一頁的Col 如 "A" or "AB"
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 * strFormat 格式化資料，如 "##,###" or "0.00000000"
	 */
	public void setValue(int Trow,String colName,long Sdata,boolean PrintZero,String strFormat) throws Exception{
		DecimalFormat fm = new DecimalFormat(strFormat);
		String strTmp = fm.format(Sdata);
		setValue(Trow,colName,strTmp,PrintZero);
	}
	/** 
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列 (從1開始)
	 * TRow 在每一頁的第幾欄 (從1開始)
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 * strFormat 格式化資料，如 "##,###" or "0.00000000"
	 */
	public void setValue(int Trow,int Tcol,long Sdata,boolean PrintZero,String strFormat) throws Exception{
		DecimalFormat fm = new DecimalFormat(strFormat);
		String strTmp = fm.format(Sdata);
		writeValue(Trow,(Tcol-1),strTmp,PrintZero);
	}
	/** 
	 * 合併儲存格
	 * Vrow 一筆資料也許有兩列以上，所要丟的資料在第幾列(通常為1)
	 * colTopLeftName 要合併儲存格的左上Col如 "A" or "AB"
	 * rowBottomRight 要合併儲存格的右下方列數 (從1開始)
	 * colBottomRightName 要合併儲存格右下的Col如 "A" or "AB"
	 */
	public void DetailmergeCells(int Vrow,String colTopLeftName,String colBottomRightName) throws Exception{
		//合併儲存格
		int Trow = trueRow(Vrow);
		int DataRow = (Trow-1) + ((PageCout-1) * PageRowCount) ;
		int Lrow = DataRow;
		int Rrow = DataRow;
		sheet.mergeCells(trueCol(colTopLeftName),Lrow,trueCol(colBottomRightName),Rrow);
	}
	/** 
	 * 合併儲存格
	 * rowTopLeft 要合併儲存格的左上方列數 (從1開始)
	 * colTopLeftName 要合併儲存格的左上Col如 "A" or "AB"
	 * rowBottomRight 要合併儲存格的右下方列數 (從1開始)
	 * colBottomRightName 要合併儲存格右下的Col如 "A" or "AB"
	 */
	public void mergeCells(int rowTopLeft,String colTopLeftName,int rowBottomRight,String colBottomRightName) throws Exception{
		//合併儲存格
		int Lrow = (rowTopLeft -1)+((PageCout-1) * PageRowCount);
		int Rrow = (rowBottomRight -1)+((PageCout-1) * PageRowCount);
		sheet.mergeCells(trueCol(colTopLeftName),Lrow,trueCol(colBottomRightName),Rrow);
	}
	/** 
	 * 合併儲存格
	 * rowTopLeft 要合併儲存格的左上方列數 (從1開始)
	 * colTopLeft 要合併儲存格的左上欄位 (從1開始) Col如 "A"則傳 1 "B"則傳2
	 * rowBottomRight 要合併儲存格的右下方列數 (從1開始)
	 * colBottomRight 要合併儲存格右下欄位 (從1開始) Col如 "A"則傳 1 "B"則傳2
	 */
	public void mergeCells(int rowTopLeft,int colTopLeft,int rowBottomRight,int colBottomRight) throws Exception{
		//合併儲存格
		int Lrow = (rowTopLeft -1)+((PageCout-1) * PageRowCount);
		int Rrow = (rowBottomRight -1)+((PageCout-1) * PageRowCount);
		int Lcol = colTopLeft-1;
		int Rcol = colBottomRight-1;
		sheet.mergeCells(Lcol,Lrow,Rcol,Rrow);
	}
	
	
	/**
	 * 合併儲存格
	 * @param rowTopLeft
	 * @param colTopLeftName
	 * @param rowBottomRight
	 * @param colBottomRightName
	 * @param aa
	 * @throws Exception
	 * 修改於20140425 BY 賴泓錡
	 */
	public void mergeCell(int rowTopLeft,String colTopLeftName,int rowBottomRight,String colBottomRightName ,String character,String type) throws Exception{
		//合併儲存格
		int Lrow = (rowTopLeft -1)+((PageCout-1) * PageRowCount);
		int Rrow = (rowBottomRight -1)+((PageCout-1) * PageRowCount);	
		WritableFont myFont = new WritableFont(WritableFont.createFont("新細明體"),14);
		if("A".equals(colTopLeftName)){
			if(25==rowTopLeft){
				myFont = new WritableFont(WritableFont.createFont("新細明體"),14);
			}
		}

		if("D".equals(colTopLeftName)||"E".equals(colTopLeftName)){
			myFont.setBoldStyle(WritableFont.BOLD);  
		}else{
			myFont.setBoldStyle(WritableFont.NO_BOLD);  
		}
		
		
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setFont(myFont); // 指定字型
		
		
		if(!"Y".equals(type)){
			//換行
			cellFormat.setWrap(true);
			//把水平對齊方式指定為靠左
			cellFormat.setAlignment(Alignment_LEFT);
			//把垂直對齊方式指定為靠上對齊 
			cellFormat.setVerticalAlignment(VerticalAlignment_TOP);
			
			if("H".equals(colTopLeftName)){
				cellFormat.setBorder(Border.ALL, BorderLine, Colour.BLACK);
			}else if("A".equals(colTopLeftName)){
				if(25==rowTopLeft){
					cellFormat.setBorder(Border.TOP, BorderLine, Colour.BLACK);
					cellFormat.setBorder(Border.RIGHT, BorderLine, Colour.BLACK);
					cellFormat.setBorder(Border.LEFT, BorderLine, Colour.BLACK);
				}else if(30==rowTopLeft){
					//cellFormat.setBorder(Border.TOP, BorderLine, Colour.BLACK);
					cellFormat.setBorder(Border.RIGHT, BorderLine, Colour.BLACK);
					cellFormat.setBorder(Border.LEFT, BorderLine, Colour.BLACK);
				}else if(33==rowTopLeft){
					//cellFormat.setBorder(Border.TOP, BorderLine, Colour.BLACK);
					cellFormat.setBorder(Border.RIGHT, BorderLine, Colour.BLACK);
					cellFormat.setBorder(Border.LEFT, BorderLine, Colour.BLACK);
					cellFormat.setBorder(Border.BOTTOM, BorderLine, Colour.BLACK);
				}else{
					cellFormat.setBorder(Border.RIGHT, BorderLine, Colour.BLACK);
					cellFormat.setBorder(Border.LEFT, BorderLine, Colour.BLACK);
				}
			}
			
		}
		if("Y".equals(type)){
			//縮小字型已適合欄寬
			cellFormat.setShrinkToFit(true); 
			//把水平對齊方式指定為居中 
			cellFormat.setAlignment(Alignment_CENTRE);
			//把垂直對齊方式指定為居中 
			cellFormat.setVerticalAlignment(VerticalAlignment_CENTRE);
			if("C".equals(colTopLeftName)||"D".equals(colTopLeftName)||"E".equals(colTopLeftName)||"J".equals(colTopLeftName)){
				//把水平對齊方式指定為靠左
				cellFormat.setAlignment(Alignment_LEFT);
			}
			cellFormat.setBorder(Border.ALL, BorderLine, Colour.BLACK);
		}
		
		
		sheet.mergeCells(trueCol(colTopLeftName),Lrow,trueCol(colBottomRightName),Rrow);
		//sheet.mergeCells(0, 15, 0, 18);
		Label d7_to_g7 = new Label(trueCol(colTopLeftName), Lrow,character, cellFormat);  
		sheet.addCell(d7_to_g7);
		if("Y".equals(type)){
			sheet.setRowView(Rrow,492);//設定列高為25
		}else if("1".equals(type)&&"A".equals(colTopLeftName)){
			for(int i=Lrow;i<=Rrow;i++){
				sheet.setRowView(i,615);//設定列高為25
				}
		}else if("2".equals(type)&&"A".equals(colTopLeftName)){
			sheet.setRowView(Rrow,246);//設定列高為25
		}else if("3".equals(type)&&"A".equals(colTopLeftName)){
			sheet.setRowView(Lrow,492);//設定列高為25
			sheet.setRowView(Rrow,492);//設定列高為25
		}

	}
	
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
		
		sheet.addImage(new WritableImage(7.1, sheet.getRows()-9.9, 3.89,9.89, baos.toByteArray()));		
	}
	
	
	

}


