package com.spon.ems.fileimport;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.spon.utils.util.CSVReader;

import jxl.Workbook;
import jxl.WorkbookSettings;

/**
 * <Abstract Action> EMS 檔案匯入元件

 */
public abstract class EMS_FILEIMPORT {
	
	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}
	
	/**
	 * 取得Excel的匯入格式(ex:CSV or XLS)
	 * @return
	 */
	abstract public String getEMS_IMPORT_TYPE();

	/**
	 * 檔案匯入
	 * @param conn
	 * @param file_name
	 * @param file_size
	 * @param content_type
	 * @param file_inputstream
	 * @param file_owner
	 * @param comp_id
	 * @return
	 */
	public Map fileImport( Connection conn, String file_name, int file_size, String content_type,
						   InputStream file_inputstream, String file_owner, String comp_id ){
		
		return this.fileimport( conn, file_name, file_size, content_type, file_inputstream, file_owner, comp_id);
	}
	
	/**
	 * 執行檔案匯入
	 * @param conn
	 * @param file_name
	 * @param file_size
	 * @param content_type
	 * @param file_inputstream
	 * @param file_owner
	 * @param comp_id
	 * @return
	 */
	private Map fileimport( Connection conn, String file_name, int file_size, String content_type,
						InputStream file_inputstream, String file_owner, String comp_id ){
		
		List datalist = null;
		Map msgMap = new HashMap();
		
		try{
			//取得資料清單
			datalist = this.getFileDataList(conn, file_inputstream ,comp_id);
			
			//檢核資料清單
			Map err_msgMap = this.chkImportDataList( conn, datalist, comp_id );
			
			//執行匯入動作, 並取得回傳訊息
			msgMap = this.fileimport(conn, datalist, file_owner, comp_id);
			
			if( err_msgMap!=null ){
				msgMap.putAll(err_msgMap);
			}
			
			//更新資料庫
			conn.commit();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return msgMap;
	}
	
	/**
	 * 取得資料清單
	 * @param file_inputstream
	 * @return
	 */
	protected final List getFileDataList(Connection conn, InputStream file_inputstream, String comp_id ){
		
		List datalist = new ArrayList();
		
		try{
			if("CSV".equals(this.getEMS_IMPORT_TYPE().toUpperCase())){
				
				CSVReader reader = new CSVReader(new InputStreamReader(file_inputstream));
				
				datalist = (List) reader.readAll();
				
			}else if("XLS".equals(this.getEMS_IMPORT_TYPE().toUpperCase())){
				
				//設置編碼格式
				WorkbookSettings setting = new WorkbookSettings();
				
				//設置讀取格式
				setting.setEncoding("iso-8859-1"); 
				
				//取得工作表
				//Workbook wbk = Workbook.getWorkbook(file_inputstream);
				Workbook wbk = Workbook.getWorkbook(file_inputstream,setting);
				
				//建立工作表資料清單
				datalist = this.generateXLSDataList(conn, wbk, comp_id);
				
				//關閉工作表
				wbk.close();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return datalist;
	}
	
	/**
	 * 建立 XLS DATA LIST
	 * @param wbk
	 * @return
	 */
	protected List generateXLSDataList( Connection conn, Workbook wbk, String comp_id ){
		
		List xlsdatalist = new ArrayList();
		
		try{
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;
	}
	
	protected abstract Map chkImportDataList( Connection conn, List datalist, String comp_id  );
	
	/**
	 * 執行檔案匯入
	 * @param conn
	 * @param datalist
	 * @param owner
	 * @param comp_id
	 * @return
	 */
	protected abstract Map fileimport( Connection conn, List datalist, String owner, String comp_id  );
	
}