package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;

import com.p6spy.engine.spy.P6PreparedStatement;

import jxl.Sheet;
import jxl.Workbook;

import vtgroup.ems.common.vo.AuthorizedBean;

public class IMP_SM010600 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private String user_emp_id = "";
	private AuthorizedBean authbean = null;
	private HttpServletRequest request= null;
	//取得驗證工具
	private IMP_Verification Verification = null;
	
	public IMP_SM010600( HttpServletRequest request, String user_emp_id, AuthorizedBean authbean ) {
		
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;
		this.authbean = authbean;
		this.request= request;
		this.Verification = new IMP_Verification(request, user_emp_id, authbean);
	}
	
	/**
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}
	
	/**
	 * 建立 XLS DATA LIST
	 * @param wbk
	 * @return
	 */
	public List generateXLSDataList( Connection conn, Workbook wbk, String comp_id ){
		
		List xlsdatalist = new ArrayList();
		Map<String,String> dataMap = null;
		
		try{
			//取得第一個Sheet資料
			Sheet st = wbk.getSheet(0);
			
			//第一筆資料為表頭, 不列入匯入資料
			for( int i=3; i<st.getRows(); i++ ){				
				//所有欄位皆空白就跳出該迴圈
				if("".equals(st.getCell( 0, i).getContents())	&&"".equals(st.getCell( 1, i).getContents())
					&&"".equals(st.getCell( 2, i).getContents())	&&"".equals(st.getCell( 3, i).getContents())
					&&"".equals(st.getCell( 4, i).getContents())	&&"".equals(st.getCell( 5, i).getContents())
					&&"".equals(st.getCell( 6, i).getContents())	&&"".equals(st.getCell( 7, i).getContents())
					&&"".equals(st.getCell( 8, i).getContents())	&&"".equals(st.getCell( 9, i).getContents())
					&&"".equals(st.getCell( 10, i).getContents())	&&"".equals(st.getCell( 11, i).getContents())
					&&"".equals(st.getCell( 12, i).getContents())	&&"".equals(st.getCell( 13, i).getContents())
					){
					continue;
				}
				
				dataMap = new HashMap<String,String>();
				dataMap.put("SM010600T0_02", st.getCell( 0, i).getContents().trim());//零件名稱
				dataMap.put("SM010600T0_04", st.getCell( 1, i).getContents().trim());//料號
				dataMap.put("SM010600T0_05", st.getCell( 2, i).getContents().trim());//規格
				dataMap.put("SM010600T0_06", st.getCell( 3, i).getContents().trim());//圖號
				dataMap.put("SM010600T0_07", st.getCell( 4, i).getContents().trim());//圖號版次
				dataMap.put("SM010600T0_08", st.getCell( 5, i).getContents().trim());//圖號日期
				dataMap.put("SM010600T0_09", st.getCell( 6, i).getContents().trim());//母工單號						
				dataMap.put("SM010600T0_10", st.getCell( 7, i).getContents().trim());//製程編號
				dataMap.put("SM010600T0_11", st.getCell( 8, i).getContents().trim());//工序編號
				dataMap.put("SM010600T0_03", st.getCell( 9, i).getContents().trim());//客戶
				dataMap.put("STOCKB", 		 st.getCell( 10, i).getContents().trim());//存放位置(倉)
				dataMap.put("STOCKMED", 	 st.getCell( 11, i).getContents().trim());//存放位置(儲)
				dataMap.put("STOCKS", 		 st.getCell( 12, i).getContents().trim());//存放位置(櫃)
				dataMap.put("SM010600T0_13", st.getCell( 13, i).getContents().trim());//零件說明
				
				
				xlsdatalist.add(dataMap);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;
		
	}

	@Override
	protected Map chkImportDataList(Connection conn, List datalist,
			String comp_id) {
		// TODO Auto-generated method stub
		
		Map err_msgMap = new HashMap();
		List ng_list = new ArrayList();	//重複不匯入清單
		List error_list = new ArrayList();	//資料不正確清單	
		String sql = "";
		Map dataMap = null;
		boolean check_Exist = false;
		boolean check_STOCK1 = false;
		boolean check_STOCK2 = false;
		String STOCKB = "";
		String STOCKMED = "";
		String STOCKS = "";
		String customer = "";
		String UID = "";
		String parts_day[];
		
		try{
			//檢核未通過的筆數(重複不匯入清單)
			int ng_count = 0;
			
			//資料不正確清單			
			int error_count = 0;
			
			//預先刪除所有匯入資料中重複資料
			Verification.DELETE_Overlap(datalist);
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			//檢核匯入資料清單
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//重置
				check_Exist = false;
				check_STOCK1 = false;
				check_STOCK2 = false;
				STOCKB = "";
				STOCKMED = "";
				STOCKS = "";
				customer = "";
				UID = "";
				
				//取得資料
				dataMap = (Map) it.next();
				
				if(
					 	   "".equals((String) dataMap.get("SM010600T0_02")) 		// 
						&& "".equals((String) dataMap.get("SM010600T0_04")) 		// 
						&& "".equals((String) dataMap.get("SM010600T0_05")) 		// 
						&& "".equals((String) dataMap.get("SM010600T0_06")) 		// 		
						&& "".equals((String) dataMap.get("SM010600T0_07"))			// 
						&& "".equals((String) dataMap.get("SM010600T0_08")) 		// 
						&& "".equals((String) dataMap.get("SM010600T0_09")) 		//
						&& "".equals((String) dataMap.get("SM010600T0_10")) 		//
						&& "".equals((String) dataMap.get("SM010600T0_11")) 		//
						&& "".equals((String) dataMap.get("SM010600T0_03")) 		//
						&& "".equals((String) dataMap.get("STOCKB")) 				// 存放位置(倉)
						&& "".equals((String) dataMap.get("STOCKMED")) 				// 存放位置(儲)
						&& "".equals((String) dataMap.get("STOCKS")) 				// 存放位置(櫃)					
						&& "".equals((String) dataMap.get("SM010600T0_13")) 		// 說明		
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				if(
						   (String)dataMap.get("SM010600T0_02") 		== null		
						&& (String)dataMap.get("SM010600T0_04")			== null	
						&& (String)dataMap.get("SM010600T0_05")			== null		
						&& (String)dataMap.get("SM010600T0_06")			== null						
						&& (String)dataMap.get("SM010600T0_07")			== null		
						&& (String)dataMap.get("SM010600T0_08")			== null
						&& (String)dataMap.get("SM010600T0_09")			== null
						&& (String)dataMap.get("SM010600T0_10")			== null
						&& (String)dataMap.get("SM010600T0_11")			== null
						&& (String)dataMap.get("SM010600T0_03")			== null
						&& (String)dataMap.get("STOCKB")				== null		
						&& (String)dataMap.get("STOCKMED")				== null
						&& (String)dataMap.get("STOCKS")				== null		
						&& (String)dataMap.get("SM010600T0_13")			== null								
			
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   "".equals((String) dataMap.get("SM010600T0_02")) 		// 
						|| "".equals((String) dataMap.get("SM010600T0_04")) 		// 
						|| "".equals((String) dataMap.get("SM010600T0_05")) 		// 
						|| "".equals((String) dataMap.get("SM010600T0_06")) 		// 			
						|| "".equals((String) dataMap.get("SM010600T0_07"))			// 
						|| "".equals((String) dataMap.get("SM010600T0_08")) 		// 
						//|| "".equals((String) dataMap.get("SM010600T0_09")) 		//
						|| "".equals((String) dataMap.get("SM010600T0_10")) 		//
						|| "".equals((String) dataMap.get("SM010600T0_11")) 		//
						|| "".equals((String) dataMap.get("SM010600T0_03")) 		//
						|| "".equals((String) dataMap.get("STOCKB")) 				// 存放位置(倉)
						//|| "".equals((String) dataMap.get("STOCKMED")) 			// 存放位置(儲)
						//|| "".equals((String) dataMap.get("STOCKS")) 				// 存放位置(櫃)							
						//|| "".equals((String) dataMap.get("SM010600T0_13")) 		// 說明						
					){
					dataMap.put("error", "未正確填寫欄位資料!" + "<br>");
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   (String)dataMap.get("SM010600T0_02") 		== null		
						|| (String)dataMap.get("SM010600T0_04")			== null	
						|| (String)dataMap.get("SM010600T0_05")			== null		
						|| (String)dataMap.get("SM010600T0_06")			== null						
						|| (String)dataMap.get("SM010600T0_07")			== null		
						|| (String)dataMap.get("SM010600T0_08")			== null
						//|| (String)dataMap.get("SM010600T0_09")			== null
						|| (String)dataMap.get("SM010600T0_10")			== null
						|| (String)dataMap.get("SM010600T0_11")			== null
						|| (String)dataMap.get("SM010600T0_03")			== null
						|| (String)dataMap.get("STOCKB")				== null		
						//|| (String)dataMap.get("STOCKMED")				== null
						//|| (String)dataMap.get("STOCKS")					== null		
						//|| (String)dataMap.get("SM010600T0_13")			== null								
					){
					if(dataMap.get("error")==null){
						dataMap.put("error", "欄位資料未正確填寫!" + "<br>");
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"欄位資料未正確填寫!!" + "<br>");
					}
				}
				
				if (dataMap.get("error") != null) {
					error_list.add(dataMap); // 將此筆資料移至資料不正確清單
					it.remove(); // 將此筆資料由待匯入清單移除
					error_count++;
					// 結束此次迴圈
					continue;	
				}
				
				//針對年月日，先補0
				parts_day = ((String)dataMap.get("SM010600T0_08")).split("/");
				dataMap.put("SM010600T0_08_year", Verification.addZeroForNum(parts_day[0],2));
				dataMap.put("SM010600T0_08_month", Verification.addZeroForNum(parts_day[1],2));
				dataMap.put("SM010600T0_08_day", Verification.addZeroForNum(parts_day[2],2));
				
				if(Verification.check_year((String)dataMap.get("SM010600T0_08_year"))){
					if (dataMap.get("error") == null){
						dataMap.put("error", "圖號日期  '年' 長度大於4!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "圖號日期  '年' 長度大於4!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if(Verification.check_month((String)dataMap.get("SM010600T0_08_month"))){
					if (dataMap.get("error") == null){
						dataMap.put("error", "圖號日期月份不能大於12!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "圖號日期月份不能大於12!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證零件名稱是否大於50個字
				if(((String)dataMap.get("SM010600T0_02")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "零件名稱不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "零件名稱不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證料號是否大於200個字
				if(((String)dataMap.get("SM010600T0_04")).length()>200){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "料號不能大於200個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "料號不能大於200個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證規格是否大於50個字
				if(((String)dataMap.get("SM010600T0_05")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "規格不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "規格不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證圖號是否大於200個字
				if(((String)dataMap.get("SM010600T0_06")).length()>200){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "圖號不能大於200個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "圖號不能大於200個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if(GenericValidator.isInt((String)dataMap.get("SM010600T0_07"))){
					if(Integer.parseInt((String)dataMap.get("SM010600T0_07"))<0){
						if (dataMap.get("error") == null){
							dataMap.put("error", "圖號版次不可能有負數!"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "圖號版次不可能有負數!" + "<br>");
						}
						check_Exist=true;
					}					
				}else{
					if (dataMap.get("error") == null){
						dataMap.put("error", "圖號版次請輸入整數值!"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "圖號版次請輸入整數值!" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證母工單號是否大於20個字
				if(((String)dataMap.get("SM010600T0_09")).length()>20){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "母工單號不能大於20個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "母工單號不能大於20個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證製程編號是否大於2個字
				if(((String)dataMap.get("SM010600T0_10")).length()>2){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "製程編號不能大於2個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "製程編號不能大於2個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證工序編號是否大於2個字
				if(((String)dataMap.get("SM010600T0_11")).length()>2){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "工序編號不能大於2個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "工序編號不能大於2個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證零件說明是否大於100個字
				if(((String)dataMap.get("SM010600T0_13")).length()>100){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "零件說明不能大於100個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "零件說明不能大於100個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				STOCKB = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKB"), comp_id);
				
				if("".equals(STOCKB) || STOCKB == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此倉位類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此倉位類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if(!"".equals((String) dataMap.get("STOCKMED")) && (String) dataMap.get("STOCKMED") !=null){
					STOCKMED = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED"), comp_id);
				
					if("".equals(STOCKMED) || STOCKMED == null){
						if (dataMap.get("error") == null){
							dataMap.put("error", "系統內無此儲位類別!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此儲位類別!請再次確認" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				if(!"".equals((String) dataMap.get("STOCKS")) && (String) dataMap.get("STOCKS") !=null){
					STOCKS = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKS"), comp_id);
				
					if("".equals(STOCKS) || STOCKS == null){
						if (dataMap.get("error") == null){
							dataMap.put("error", "系統內無此櫃位類別!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此櫃位類別!請再次確認" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				if(!"".equals((String) dataMap.get("STOCKMED")) && (String) dataMap.get("STOCKMED") !=null){
					check_STOCK1 = Verification.isParent(conn, Verification.getSTOCKD(conn, (String)dataMap.get("STOCKB"), comp_id), 
										  				 Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED"), comp_id), comp_id);
					if(check_STOCK1){
						if (dataMap.get("error") == null){
							dataMap.put("error", "此倉位與儲位上下關係錯誤!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "此倉位與儲位上下關係錯誤!請再次確認" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				if(!"".equals((String) dataMap.get("STOCKMED")) && (String) dataMap.get("STOCKMED") !=null){
					if(!"".equals((String) dataMap.get("STOCKS")) && (String) dataMap.get("STOCKS") !=null){
						check_STOCK2 = Verification.isParent(conn, Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED"), comp_id), 
								  							 Verification.getSTOCKD(conn, (String)dataMap.get("STOCKS"), comp_id), comp_id);
						if(check_STOCK2){
							if (dataMap.get("error") == null){
								dataMap.put("error", "此儲位與櫃位上下關係錯誤!請再次確認"+"<br>");//
							} else {
								dataMap.put("error", dataMap.get("error").toString()+ " " + "此儲位與櫃位上下關係錯誤!請再次確認" + "<br>");
							}
							check_Exist=true;
						}
					}
				}
				
				customer = Verification.getCustomer(conn, (String)dataMap.get("SM010600T0_03"), comp_id);
				
				if("".equals(customer) || customer == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此客戶!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此客戶!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if(!"".equals((String)dataMap.get("SM010600T0_04")) && (String)dataMap.get("SM010600T0_04") != null
				 &&!"".equals((String)dataMap.get("SM010600T0_10")) && (String)dataMap.get("SM010600T0_10") != null
				 &&!"".equals((String)dataMap.get("SM010600T0_11")) && (String)dataMap.get("SM010600T0_11") != null){
					
					UID = ((String)dataMap.get("SM010600T0_04")).substring(0, 11) + (String)dataMap.get("SM010600T0_10") + (String)dataMap.get("SM010600T0_11");
					
					sql = "" +
					" SELECT SM010600T0_01 " +
					" FROM SM010600T0 " +
					" WHERE 1=1 " +
					" AND SM010600T0_01 = '"+UID+"' " +  //零件編號
					" AND CompanySysNo = '"+comp_id+"' ";  //公司代碼
				
					rs = stmt.executeQuery(sql);
				
					if(rs.next()){
						check_Exist = true;
						if (dataMap.get("error") == null){
							dataMap.put("error", "與資料庫資料重複，請再次確認!" + "<br>");
						}else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "與資料庫資料重複，請再次確認!" + "<br>");
						}
					}
				
					if(rs!=null){
						rs.close();
					}
					
				}else{
					if (dataMap.get("error") == null){
						dataMap.put("error", "由於料號、製程編號、工序編號錯誤!故不檢查是否有資料重複"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "由於料號、製程編號、工序編號錯誤!故不檢查是否有資料重複" + "<br>");
					}
					check_Exist=true;
				}
				
				//請假單匯入資料格式不正確, 不可匯入
				if (check_Exist) {
					error_list.add(dataMap); // 將此筆資料移至資料不正確清單
					it.remove(); // 將此筆資料由待匯入清單移除
					error_count++;
					// 結束此次迴圈
					continue;
				}
				
			}
			stmt.close();
			
			//判斷是否有重複資料
			//判斷是否有格式不正確資料
			if(ng_list.size() > 0 || error_list.size() > 0){
				err_msgMap.put("NGDATACOUNT", ng_count);
				err_msgMap.put("NGDATALIST", ng_list);
				err_msgMap.put("ERRORDATACOUNT", error_count);
				err_msgMap.put("ERRORDATALIST", error_list);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return err_msgMap;
	}

	@Override
	protected Map fileimport(Connection conn, List datalist, String owner,
			String comp_id) {
		// TODO Auto-generated method stub
		
		Map msgMap = new HashMap();
		Map dataMap = null;
		String UID = "";
		
		try{
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;
			
			//新增
			String sql = "" +
			" INSERT INTO SM010600T0 ( " +
			" SM010600T0_01, SM010600T0_02, SM010600T0_03, SM010600T0_04, SM010600T0_05, " +
			" SM010600T0_06, SM010600T0_07, SM010600T0_08, SM010600T0_09, SM010600T0_10, " +
			" SM010600T0_11, SM010600T0_13, " +
			" STOCKB, 	     STOCKMED, 	    STOCKS, " +
			" CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( " +
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?, ?, ?, "+
			" ?, ?, " +
			" ?, ?, ?, " +
			" ?, ?, ?, '1', NOW(), NOW()) ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				UID = ((String)dataMap.get("SM010600T0_04")).substring(0, 11) + (String)dataMap.get("SM010600T0_10") + (String)dataMap.get("SM010600T0_11");
				
				indexid = 1;
				p6stmt.setString(indexid,UID);//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_02"));//
				indexid++;
				p6stmt.setString(indexid,Verification.getCustomer(conn, (String)dataMap.get("SM010600T0_03"), comp_id));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_04"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_05"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_06"));//
				indexid++;
				p6stmt.setInt(indexid,Integer.parseInt((String)dataMap.get("SM010600T0_07")));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_08_year")+"/"+(String)dataMap.get("SM010600T0_08_month")+
								"/"+(String)dataMap.get("SM010600T0_08_day"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_09")==null?"":(String)dataMap.get("SM010600T0_09"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_10"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_11"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010600T0_13")==null?"":(String)dataMap.get("SM010600T0_13"));//
				indexid++;
				p6stmt.setString(indexid,Verification.getSTOCKD(conn, (String)dataMap.get("STOCKB"), comp_id));//
				indexid++;
				if(!"".equals((String) dataMap.get("STOCKMED")) && (String) dataMap.get("STOCKMED") !=null){
					p6stmt.setString(indexid,Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED"), comp_id));//
					indexid++;
				}else{
					p6stmt.setString(indexid,(String)dataMap.get("STOCKMED")==null?"":(String)dataMap.get("STOCKMED"));//
					indexid++;
				}
				if(!"".equals((String) dataMap.get("STOCKS")) && (String) dataMap.get("STOCKS") !=null){
					p6stmt.setString(indexid,Verification.getSTOCKD(conn, (String)dataMap.get("STOCKS"), comp_id));//
					indexid++;
				}else{
					p6stmt.setString(indexid,(String)dataMap.get("STOCKS")==null?"":(String)dataMap.get("STOCKS"));//
					indexid++;
				}
				p6stmt.setString(indexid, comp_id);  //公司代碼
				indexid++;
				p6stmt.setString(indexid, owner);  //
				indexid++;
				p6stmt.setString(indexid, owner);  //
				indexid++;
				
				p6stmt.executeUpdate();
				
				dataCount++;
				
			}
			
			pstmt.close();
			p6stmt.close();
			
			//建立回傳訊息Map
			msgMap.put("DATALIST", datalist);
			msgMap.put("DATACOUNT", dataCount);
			
		}catch(Exception e){
			
			e.printStackTrace();
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		return msgMap;
	}

	@Override
	public String getEMS_IMPORT_TYPE() {
		// TODO Auto-generated method stub
		//設定匯入檔案的型態
		return "XLS";
	}

}
