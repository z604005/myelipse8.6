package com.spon.ems.util.card_system;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.spon.utils.util.CSVReader;

public class EMS_CardCSVFile extends EMS_CardSystem {
	
	
	protected String [] MapKey = null;
	public String file_name = null;
	public String file_path = null;
	private String date_format = super.getDateFormat();
	private String time_format = super.getTimeFormat();
	
	@Override
	protected boolean del_logdata_ems(Connection conn, String iLogDataSN) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected List getCardDataList(Connection conn, String compId) {
		// TODO Auto-generated method stub
		CSVReader reader = null;
		List csvlist = null;
		
		try{
			String sysroot = this.getSystemRoot(conn, compId);  //取得系統根目錄
			this.check_File_Path(sysroot);  //確認檔案路徑是否存在，不存在則建立資料夾
			boolean del_flag = this.getDelFileConfig(conn, compId);  //取得刪除檔案的Tag
			String new_file_path = this.copy_File(sysroot, del_flag, file_path, file_name);  //複製卡鐘LOG檔案
			
			File new_file_log = new File(new_file_path);  //檢查複製後的Log檔案是否存在
			
			if( new_file_log.exists()){
				
				reader = new CSVReader(new FileReader(new_file_path));
				csvlist = reader.readAll();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return csvlist;
	}

	@Override
	public String[] getMapKey(Connection conn, String settingNo, String compId) {
		// TODO Auto-generated method stub
		
		try{
			String tmp_string = "";
			String[] tmp_array = null;
			//處理特殊MapKey
			for(int i = 0; i< this.MapKey.length; i++){
				if(this.MapKey[i].indexOf("DATEFORMAT") != -1 && this.MapKey[i].startsWith("DATEFORMAT") ){
					//指定日期格式
					tmp_array = this.MapKey[i].split("=");
					this.date_format = tmp_array[1];
					//清除指定KEY
					this.MapKey[i] = "NULL";
				}else if(this.MapKey[i].indexOf("TIMEFORMAT") != -1 && this.MapKey[i].startsWith("TIMEFORMAT") ){
					//指定時間格式
					tmp_array = this.MapKey[i].split("=");
					this.time_format = tmp_array[1];
					this.MapKey[i] = "NULL";
				}else if(this.MapKey[i].indexOf("DYNAMICFILENAME") != -1 && this.MapKey[i].startsWith("DYNAMICFILENAME") ){
					//指定動態檔案名稱
					//格式: [ADDATE@-1.TXT],[CHIDATE@-1.DAT] 
					//-> ADDATE = 西元年, CHIDATE = 民國年
					// @ -> 分隔符號, -1 -> 日期位移量, "." -> 副檔名分隔符號, TXT -> 副檔名  
					tmp_array = this.MapKey[i].split("=");
					tmp_array = tmp_array[1].split("\\.");
					tmp_string = tmp_array[1];  //副檔名
					tmp_array = tmp_array[0].split("@");
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = null;
					int displacement = 0;
					
					if(tmp_array.length ==2){
						//表示有位移量
						displacement = Integer.parseInt(tmp_array[1]);
					}
					
					//執行位移
					cal.add(Calendar.DAY_OF_MONTH, displacement);
					
					if("ADDATE".equalsIgnoreCase(tmp_array[0])){
						//西元年
						sdf = new SimpleDateFormat("yyyyMMdd");
						tmp_string = sdf.format(cal.getTime()) + "." + tmp_string;  //檔案名稱
					}else if("CHIDATE".equalsIgnoreCase(tmp_array[0])){
						//民國年
						sdf = new SimpleDateFormat("MMdd");
						tmp_string = (cal.get(Calendar.YEAR)-1911) + sdf.format(cal.getTime()) + "." + tmp_string;  //檔案名稱
					}
					//回寫檔案名稱
					this.file_name = tmp_string;
					//清除動態檔案名稱設定
					this.MapKey[i] = "NULL";
				}else if(this.MapKey[i].indexOf("ADDATE") != -1 && this.MapKey[i].startsWith("ADDATE") ){
					//指定動態檔案名稱
					//格式: [ADDATE@-1.TXT],[CHIDATE@-1.DAT] 
					//-> ADDATE = 西元年, CHIDATE = 民國年
					// @ -> 分隔符號, -1 -> 日期位移量, "." -> 副檔名分隔符號, TXT -> 副檔名  
					tmp_array = this.MapKey[i].split("\\.");
					tmp_string = tmp_array[1];  //副檔名
					tmp_array = tmp_array[0].split("@");
					Calendar cal = Calendar.getInstance();
					SimpleDateFormat sdf = null;
					int displacement = 0;
					
					if(tmp_array.length ==2){
						//表示有位移量
						displacement = Integer.parseInt(tmp_array[1]);
					}
					
					//執行位移
					cal.add(Calendar.DAY_OF_MONTH, displacement);
					
					//西元年
					sdf = new SimpleDateFormat("yyyyMMdd");
					tmp_string = sdf.format(cal.getTime()) + "." + tmp_string;  //檔案名稱
					//回寫檔案名稱
					this.file_name = tmp_string;
					//清除動態檔案名稱設定
					this.MapKey[i] = "NULL";
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return MapKey;
	}
	
	public void setMapKey(String[] key){
		
		try{
			MapKey = key;
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 取得系統根目錄 (依據Linux or Windows)
	 * @param conn
	 * @param comp_id
	 * @return
	 */
	private String getSystemRoot(Connection conn, String comp_id){
		
		String value = "";
		String param = "";
		try{
			
			String osName = System.getProperty("os.name");
			if (osName.equals("Linux")) {
				param = "REPORT_PATH_LINUX";
			}else{
				param = "REPORT_PATH_WINDOWS";
			}
			
			String sql = " select SC0060_03 " +
						 " from SC0060 " +
						 " where 1=1 " +
						 " and SC0060_01 = '"+param+"' " +
						 " and SC0060_05 = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(	rs.next())
			{
				value=rs.getString(1);
			}
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return value;
	}
	
	/**
	 * 取得刪除匯入的門禁資料檔案
	 * @param conn
	 * @param comp_id
	 * @return
	 */
	private boolean getDelFileConfig(Connection conn, String comp_id){
		
		boolean chk_flag = false;
		String value = "";
		String param = "";
		
		try{
			
			param = "DELETE_IMPORT_DOOR";
			
			String sql = " select SC0060_03 " +
						 " from SC0060 " +
						 " where 1=1 " +
						 " and SC0060_01 = '"+param+"' " +
						 " and SC0060_05 = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(	rs.next())
			{
				value = rs.getString(1);
				//判斷是否刪除
				if("ENABLE".equals(value)){
					chk_flag = true;
				}else{
					chk_flag = false;
				}
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
			
		return chk_flag;
	}
	
	/**
	 * 建立卡鐘Log檔案路徑
	 * @param sysroot
	 */
	private void check_File_Path(String sysroot){
		
		try{
			File file=new File(sysroot+"EMSLog/CardClockLog/");
			if(!file.exists()){
				file.mkdirs();
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 複製卡鐘LOG到新的檔案位置
	 * @param sysroot
	 * @param file_path
	 * @param file_name
	 * @return
	 */
	private String copy_File(String sysroot, boolean del_flag, String file_path, String file_name ){
		
		String new_file_path = "";
		try{
			
			Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmm");
            String nowdate = ""+sdf.format( cal.getTime());
			
            //判斷file_path是否為"\"結尾 ==> 若不是則要補上避免系統抓錯路徑
            if( !(file_path.endsWith("\\") || file_path.endsWith("/")) ){
            	//在file_path加上 "\"結尾
            	file_path += "\\";
            }
            
			File inputFile = new File(file_path+file_name);
			File outputFile = new File(sysroot+"EMSLog/CardClockLog/"+nowdate+"-"+file_name);
			new_file_path = sysroot+"EMSLog/CardClockLog/"+nowdate+"-"+file_name;
			
			if(inputFile.exists()){
			
				FileReader in = new FileReader(inputFile);
				FileWriter out = new FileWriter(outputFile);
				int c;
			
				while ((c = in.read()) != -1){
					out.write(c);
				}
			
				in.close();
				out.close();
				
				//刪除已複製的原始檔案
				if(del_flag){
					//刪除檔案
					inputFile.delete();
				}
				
			}else{
				System.out.println("指定檔案不存在!!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return new_file_path;
	}
	
	/**
	 * 處理特殊的擷取Key
	 * @param special_key
	 * @return
	 */
	protected String handleSpecialKey(String special_key){
		
		String return_string = "";
		
		try{
			if("FILENAME".equalsIgnoreCase(special_key)){
				//表示特殊KEY是檔案名稱
				if(!"".equals(this.getFile_name()) && this.getFile_name() != null){
					//檔案名稱存在
					return_string = this.getFile_name();
					return_string = return_string.substring(0, return_string.indexOf("."));
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_string;
	}
	
	/**
	 * 日期格式
	 * @return
	 */
	protected String getDateFormat(){
		return this.date_format;
	}
	
	/**
	 * 時間格式
	 * @return
	 */
	protected String getTimeFormat(){
		return this.time_format;
	}

	public String getFile_name() {
		return file_name;
	}

	public void setFile_name(String fileName) {
		file_name = fileName;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String filePath) {
		file_path = filePath;
	}
	
	
	
}
