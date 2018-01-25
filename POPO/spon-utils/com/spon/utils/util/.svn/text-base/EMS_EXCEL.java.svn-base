package com.spon.utils.util;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Date;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.biff.DisplayFormat;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.CellFormat;
import jxl.write.Blank;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormats;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;



/** 
 * 列印EXCEL用的元件
 * Creation date: 02-01-2006
 * 
 * XDoclet definition:
 * 
 */
public class EMS_EXCEL extends BA_EXCEL {
	
	/** 
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列 (從1開始)
	 * colName 在每一頁的Col 如 "A" or "AB"
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 */
	public void setValue(int Trow,String colName,String Sdata,boolean PrintZero) throws Exception{
		//取得位置
		int DataCol = trueCol(colName);
		
		writeValue(Trow,DataCol,Sdata,PrintZero,null,null);
	}
	
	/**
	 * 寫入Integer
	 * @param Trow
	 * @param colName
	 * @param Sdata
	 * @throws Exception
	 */
	public void setValue(int Trow,String colName,int Sdata ) throws Exception{
		//設定邊框
		this.setValue(Trow, colName, Sdata, null, null);
		
	}
	
	/**
	 * 寫入Integer
	 * @param Trow
	 * @param colName
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setValue(int Trow,String colName,int Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int DataCol = trueCol(colName);
		
		//建立整數格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.INTEGER);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		//Integer to Double
		double numnber = new Integer(Sdata).doubleValue();
		
		writeValue( Trow, DataCol, numnber, wcf );
		
	}
	
	/**
	 * 寫入 Float
	 * @param Trow
	 * @param colName
	 * @param Sdata
	 * @throws Exception
	 */
	public void setValue(int Trow,String colName,float Sdata ) throws Exception{

		//設定邊框
		this.setValue(Trow, colName, Sdata, null, null);
	}
	
	/**
	 * 寫入 Float
	 * @param Trow
	 * @param colName
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setValue(int Trow,String colName,float Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int DataCol = trueCol(colName);
		
		//建立Float格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.FLOAT);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		//Float to Double
		double numnber = new Float(Sdata).doubleValue();
		
		writeValue( Trow, DataCol, numnber, wcf );
	}
	
	/**
	 * 寫入 Double
	 * @param Trow
	 * @param colName
	 * @param Sdata
	 * @throws Exception
	 */
	public void setValue(int Trow,String colName,double Sdata ) throws Exception{
		
		//設定邊框
		this.setValue(Trow, colName, Sdata, null, null);
	}
	
	/**
	 * 寫入 Double
	 * @param Trow
	 * @param colName
	 * @param Sdata
	 * @throws Exception
	 */
	public void setValue(int Trow,String colName,double Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int DataCol = trueCol(colName);
		
		//建立Float格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.FLOAT);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		writeValue( Trow, DataCol, Sdata, wcf );
	}
	
	/** 
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列 (從1開始)
	 * Tcol 在每一頁的地幾欄 (從1開始)
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 */
	public void setValue(int Trow,int Tcol,String Sdata,boolean PrintZero) throws Exception{
		
		//設定格式
		WritableCellFormat wcf = new WritableCellFormat();
		
		//取得位置
		writeValue(Trow,(Tcol-1),Sdata,PrintZero,null,null);
	}
	
	/**
	 * 寫入整數
	 * @param Trow
	 * @param Tcol
	 * @param Sdata
	 * @throws Exception
	 */
	public void setValue(int Trow,int Tcol,int Sdata ) throws Exception{
		
		//設定邊框
		this.setValue(Trow, Tcol, Sdata, null, null);
	}
	
	/**
	 * 寫入整數(可設定邊框)
	 * @param Trow
	 * @param Tcol
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setValue(int Trow,int Tcol,int Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		
		//建立整數格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.INTEGER);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		//Integer to Double
		double numnber = new Integer(Sdata).doubleValue();
		
		writeValue( Trow, (Tcol-1), numnber, wcf );
	}
	
	/**
	 * 寫入Float
	 * @param Trow
	 * @param Tcol
	 * @param Sdata
	 * @throws Exception
	 */
	public void setValue(int Trow,int Tcol,float Sdata ) throws Exception{
		//設定邊框
		this.setValue(Trow, Tcol, Sdata, null, null );
	}
	
	/**
	 * 寫入Float(可設定邊框)
	 * @param Trow
	 * @param Tcol
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setValue(int Trow,int Tcol,float Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		
		//建立Float格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.FLOAT);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		//Float to Double
		double numnber = new Float(Sdata).doubleValue();
		
		writeValue( Trow, (Tcol-1), numnber, wcf );
	}
	
	/**
	 * 寫入Double
	 * @param Trow
	 * @param Tcol
	 * @param Sdata
	 * @throws Exception
	 */
	public void setValue(int Trow,int Tcol,double Sdata ) throws Exception{
		//設定邊框
		this.setValue(Trow, Tcol, Sdata, null, null );
	}
	
	/**
	 * 寫入Double(可設定邊框)
	 * @param Trow
	 * @param Tcol
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setValue(int Trow,int Tcol,double Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		
		//建立Float格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.FLOAT);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		writeValue( Trow, (Tcol-1), Sdata, wcf );
	}
	
	/**
	 * 取得數字格式
	 * @param format
	 * @return
	 */
	protected WritableCellFormat getNumberFormat(DisplayFormat format){
		
		WritableCellFormat wcf = null;
		
		try{
			wcf = new WritableCellFormat(format);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return wcf;
	}
	
	/**
	 * 取得當前儲存格的Format
	 * @param DataCol
	 * @param DataRow
	 * @return
	 */
	private CellFormat getCellFormat(int DataCol,int DataRow){
		
		CellFormat cf = null;
		
		try{
			
			cf = sheet.getCell(1,6).getCellFormat();
			cf = sheet.getCell(DataCol,DataRow).getCellFormat();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return cf;
	}
	
	/**
	 * 複製原始格式
	 * @param cf
	 * @param DataCol
	 * @param DataRow
	 */
	private void copyOriginalCellFormat(CellFormat cf, int DataCol,int DataRow){
		
		try{
			//EDIT BY FELISTUM
			//複製格式
			 if(null != cf)
	         {
				 sheet.getWritableCell(DataCol,DataRow).setCellFormat(cf);
	         } else
	         {
	        	 WritableCell cell = sheet.getWritableCell(DataCol,DataRow);
	        	 sheet.getWritableCell(DataCol,DataRow).setCellFormat(cell.getCellFormat());
//	             lbl.setCellFormat(cell.getCellFormat());
	         }
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 寫入數字格式
	 * @param Trow
	 * @param DataCol
	 * @param Sdata
	 * @param wcf
	 * @throws Exception
	 */
	protected void writeValue(int Trow,int DataCol,double Sdata, WritableCellFormat wcf ) throws Exception{
		
		try{
			
			//取得位置
			int DataRow = (Trow-1) + ((PageCout-1) * PageRowCount) ;
			//取得當前儲存格的Format
			CellFormat cf = this.getCellFormat(DataCol, DataRow);
			
			//丟'整數'資料
			Number IntegerCF = new Number(DataCol, DataRow, Sdata, wcf);
			
			//寫入資料至儲存格
			sheet.addCell(IntegerCF);
			
			//複製格式
			this.copyOriginalCellFormat(cf, DataCol, DataRow);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/** 
	 * 寫入資料(針對某個欄位)
	 * TRow 在每一頁的第幾列 (從1開始)
	 * DataCol 在每一頁的Col (從0開始)
	 * Sdata 資料內容
	 * PrintZero 如果資料為 "0" true 印出"0" false 則印空白
	 */
	private void writeValue(int Trow,int DataCol,String Sdata,boolean PrintZero, Border border, BorderLineStyle border_style ) throws Exception{
		
		//取得位置
		int DataRow = (Trow-1) + ((PageCout-1) * PageRowCount) ;
		//取得當前儲存格的Format
		CellFormat cf = this.getCellFormat(DataCol, DataRow);
		
		//設定格式
		WritableCellFormat wcf = new WritableCellFormat(cf);
		this.setBorderFormat(wcf, border, border_style);
		
		//丟資料
		Sdata = Sdata +"";
		Label labelCF = null;
		if (Sdata.trim().equals("0")&& !PrintZero)
			labelCF = new Label(DataCol,DataRow,"",wcf);
		else
			labelCF = new Label(DataCol,DataRow, Sdata,wcf);
		
		//寫入資料至儲存格
		sheet.addCell(labelCF);
		
		//複製格式
		this.copyOriginalCellFormat(wcf, DataCol, DataRow);
		
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
	 * 寫入 Integer
	 * @param Vrow
	 * @param Vcol
	 * @param Sdata
	 * @throws Exception
	 */
	public void setDetail( int Vrow, int Vcol, int Sdata ) throws Exception{
		//取得位置
		writeDetail( Vrow, (Vcol-1), Sdata, null, null );
	}
	
	/**
	 * 寫入Integer
	 * @param Vrow
	 * @param Vcol
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setDetail( int Vrow, int Vcol, int Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		writeDetail( Vrow, (Vcol-1), Sdata, border, border_style );
	}
	
	/**
	 * 寫入 Integer
	 * @param Vrow
	 * @param ColName
	 * @param Sdata
	 * @throws Exception
	 */
	public void setDetail(int Vrow,String ColName, int Sdata) throws Exception{
		//取得位置
		int DataCol = trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata,null,null);
	}
	
	/**
	 * 寫入Integer
	 * @param Vrow
	 * @param ColName
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setDetail(int Vrow,String ColName, int Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int DataCol = trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata,border,border_style);
	}
	
	/**
	 * 寫入 Integer
	 * @param Vrow
	 * @param DataCol
	 * @param Sdata
	 * @throws Exception
	 */
	protected void writeDetail(int Vrow,int DataCol, int Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int Trow = trueRow(Vrow);
		
		//建立整數格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.INTEGER);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		//Integer to Double
		double numnber = new Integer(Sdata).doubleValue();
		
		//丟資料
		writeValue( Trow, DataCol, numnber, wcf) ;
	}
	
	/**
	 * 寫入 Float
	 * @param Vrow
	 * @param Vcol
	 * @param Sdata
	 * @throws Exception
	 */
	public void setDetail( int Vrow, int Vcol, float Sdata ) throws Exception{
		//取得位置
		writeDetail( Vrow, (Vcol-1), Sdata, null, null );
	}
	
	/**
	 * 寫入Float
	 * @param Vrow
	 * @param Vcol
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setDetail( int Vrow, int Vcol, float Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		writeDetail( Vrow, (Vcol-1), Sdata, border, border_style );
	}
	
	/**
	 * 寫入 Float
	 * @param Vrow
	 * @param ColName
	 * @param Sdata
	 * @throws Exception
	 */
	public void setDetail(int Vrow,String ColName, float Sdata ) throws Exception{
		//取得位置
		int DataCol =trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata, null, null);
	}
	
	/**
	 * 寫入Float
	 * @param Vrow
	 * @param ColName
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setDetail(int Vrow,String ColName, float Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int DataCol =trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata, border, border_style );
	}
	
	/**
	 * 寫入 Float
	 * @param Vrow
	 * @param DataCol
	 * @param Sdata
	 * @throws Exception
	 */
	protected void writeDetail(int Vrow,int DataCol, float Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int Trow = trueRow(Vrow);
		
		//建立整數格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.FLOAT);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		//Integer to Double
		double numnber = new Float(Sdata).doubleValue();
		
		//丟資料
		writeValue( Trow, DataCol, numnber, wcf) ;
	}
	
	/**
	 * 寫入 Double
	 * @param Vrow
	 * @param Vcol
	 * @param Sdata
	 * @throws Exception
	 */
	public void setDetail( int Vrow, int Vcol, double Sdata ) throws Exception{
		//取得位置
		writeDetail( Vrow, (Vcol-1), Sdata, null, null );
	}
	
	/**
	 * 寫入Double
	 * @param Vrow
	 * @param Vcol
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setDetail( int Vrow, int Vcol, double Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		writeDetail( Vrow, (Vcol-1), Sdata, border, border_style );
	}
	
	/**
	 * 寫入 Double
	 * @param Vrow
	 * @param ColName
	 * @param Sdata
	 * @throws Exception
	 */
	public void setDetail(int Vrow,String ColName, double Sdata ) throws Exception{
		//取得位置
		int DataCol =trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata,null,null);
	}
	
	/**
	 * 寫入Double
	 * @param Vrow
	 * @param ColName
	 * @param Sdata
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setDetail(int Vrow,String ColName, double Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int DataCol =trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata,border,border_style);
	}
	
	/**
	 * 寫入 Double
	 * @param Vrow
	 * @param DataCol
	 * @param Sdata
	 * @throws Exception
	 */
	protected void writeDetail(int Vrow,int DataCol, double Sdata, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int Trow = trueRow(Vrow);
		
		//建立整數格式
		WritableCellFormat wcf = this.getNumberFormat(NumberFormats.FLOAT);
		
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		//丟資料
		writeValue( Trow, DataCol, Sdata, wcf) ;
	}
	
	/**
	 * 設定邊框格式
	 * @param wcf
	 * @param border
	 * @param border_style
	 */
	protected void setBorderFormat( WritableCellFormat wcf, Border border, BorderLineStyle border_style  ){
		
		try{
			//有設定格式才執行
			if( border != null && border_style != null ){
				//設定邊框
				wcf.setBorder(border, border_style);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 寫入文字
	 */
	public void setDetail(int Vrow,String ColName,String Sdata,boolean PrintZero ) throws Exception{
		//取得位置
		int DataCol =trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata,PrintZero, null, null );
	}
	
	/**
	 * 寫入文字
	 * @param Vrow
	 * @param ColName
	 * @param Sdata
	 * @param PrintZero
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void setDetail(int Vrow,String ColName,String Sdata,boolean PrintZero, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int DataCol =trueCol(ColName);
		writeDetail(Vrow,DataCol,Sdata,PrintZero, border, border_style );
	}
	
	/**
	 * 寫入文字資料
	 * @param Vrow
	 * @param DataCol
	 * @param Sdata
	 * @param PrintZero
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	private void writeDetail(int Vrow,int DataCol,String Sdata,boolean PrintZero, Border border, BorderLineStyle border_style ) throws Exception{
		//取得位置
		int Trow = trueRow(Vrow);
		
		//丟資料
		writeValue(Trow,DataCol,Sdata,PrintZero,border,border_style);
	}
	
	/**
	 * 改變框線
	 * @param Trow
	 * @param colName
	 * @param border
	 * @param border_style
	 * @throws Exception
	 */
	public void chgBorderType( int Vrow, String ColName, Border border, BorderLineStyle border_style ) throws Exception{
		
		//取得位置
		int DataCol =trueCol(ColName);
		int Trow = trueRow(Vrow);
		
		//取得位置
		int DataRow = (Trow-1) + ((PageCout-1) * PageRowCount) ;
		//取得當前儲存格的Format
		CellFormat cf = this.getCellFormat(DataCol, DataRow);
		
		//建立格式
		WritableCellFormat wcf = new WritableCellFormat(cf);
		//設定邊框
		this.setBorderFormat( wcf, border, border_style );
		
		//寫入格式
		if( wcf != null){
			sheet.getWritableCell(DataCol,DataRow).setCellFormat(wcf);
		}
		
	}
	

}


