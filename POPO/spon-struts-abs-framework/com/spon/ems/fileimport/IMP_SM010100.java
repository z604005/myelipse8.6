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

import com.p6spy.engine.spy.P6PreparedStatement;

import jxl.Sheet;
import jxl.Workbook;

import vtgroup.ems.common.vo.AuthorizedBean;

public class IMP_SM010100 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private String user_emp_id = "";
	private AuthorizedBean authbean = null;
	private HttpServletRequest request= null;
	//取得驗證工具
	private IMP_Verification Verification = null;
	
	public IMP_SM010100( HttpServletRequest request, String user_emp_id, AuthorizedBean authbean ) {
		
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
					&&"".equals(st.getCell( 14, i).getContents())	&&"".equals(st.getCell( 15, i).getContents())
					&&"".equals(st.getCell( 16, i).getContents())	&&"".equals(st.getCell( 17, i).getContents())
					&&"".equals(st.getCell( 18, i).getContents())	&&"".equals(st.getCell( 19, i).getContents()))	{
					continue;
				}
				
				dataMap = new HashMap<String,String>();
				dataMap.put("SM010100T0_01", st.getCell( 0, i).getContents().trim());//刀具編號
				dataMap.put("SM010100T0_02", st.getCell( 1, i).getContents().trim());//刀具名稱
				dataMap.put("SM010100T0_12", st.getCell( 2, i).getContents().trim());//刃長外徑
				dataMap.put("SM010100T0_06", st.getCell( 3, i).getContents().trim());//有效長度
				dataMap.put("SM010100T0_17", st.getCell( 4, i).getContents().trim());//R度
				dataMap.put("SM010100T0_18", st.getCell( 5, i).getContents().trim());//刃數
				dataMap.put("SM010100T0_08", st.getCell( 6, i).getContents().trim());//角度							
				dataMap.put("SM010100T0_19", st.getCell( 7, i).getContents().trim());//桿徑
				dataMap.put("SM010100T0_07", st.getCell( 8, i).getContents().trim());//總長
				dataMap.put("SM010100T0_05", st.getCell( 9, i).getContents().trim());//材質
				dataMap.put("SM010100T0_09", st.getCell( 10, i).getContents().trim());//廠商
				dataMap.put("SM010100T0_10", st.getCell( 11, i).getContents().trim());//刀具狀態
				dataMap.put("SM010100T0_14", st.getCell( 12, i).getContents().trim());//廠商刀具編號
				dataMap.put("STOCKB", 		 st.getCell( 13, i).getContents().trim());//新品存放位置(倉)
				dataMap.put("STOCKMED", 	 st.getCell( 14, i).getContents().trim());//新品存放位置(儲)
				dataMap.put("STOCKS", 		 st.getCell( 15, i).getContents().trim());//新品存放位置(櫃)
				dataMap.put("STOCKB_O", 	 st.getCell( 16, i).getContents().trim());//舊品存放位置(倉)
				dataMap.put("STOCKMED_O", 	 st.getCell( 17, i).getContents().trim());//舊品存放位置(儲)
				dataMap.put("STOCKS_O", 	 st.getCell( 18, i).getContents().trim());//舊品存放位置(櫃)
				dataMap.put("SM010100T0_16", st.getCell( 19, i).getContents().trim());//刀具說明
				
				
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
		boolean check_STOCK3 = false;
		boolean check_STOCK4 = false;
		String OITDIAMETER = "";
		String SIZE = "";
		String RDEGREE = "";
		String TEETH = "";
		String RODDIAMETER = "";
		String MATERIAL = "";
		String LENGTH = "";
		String ANGLE = "";
		String STOCKB = "";
		String STOCKMED = "";
		String STOCKS = "";
		String STOCKB_O = "";
		String STOCKMED_O = "";
		String STOCKS_O = "";
		String supplier = "";
		String UID[];
		String NEW_UID = "";
		
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
				check_STOCK3 = false;
				check_STOCK4 = false;
				OITDIAMETER = "";
				SIZE = "";
				RDEGREE = "";
				TEETH = "";
				RODDIAMETER = "";
				MATERIAL = "";
				LENGTH = "";
				ANGLE = "";
				STOCKB = "";
				STOCKMED = "";
				STOCKS = "";
				STOCKB_O = "";
				STOCKMED_O = "";
				STOCKS_O = "";
				supplier = "";
				
				//取得資料
				dataMap = (Map) it.next();
				
				if(
					 	   "".equals((String) dataMap.get("SM010100T0_01")) 		// 刀具編號
						&& "".equals((String) dataMap.get("SM010100T0_02")) 		// 刀具名稱
						&& "".equals((String) dataMap.get("SM010100T0_12")) 		// 刃長外徑
						&& "".equals((String) dataMap.get("SM010100T0_06")) 		// 有效長度			
						&& "".equals((String) dataMap.get("SM010100T0_17"))			// R度
						&& "".equals((String) dataMap.get("SM010100T0_18")) 		// 刃數
						&& "".equals((String) dataMap.get("SM010100T0_08")) 		//
						&& "".equals((String) dataMap.get("SM010100T0_19")) 		//
						&& "".equals((String) dataMap.get("SM010100T0_07")) 		//
						&& "".equals((String) dataMap.get("SM010100T0_05")) 		//
						&& "".equals((String) dataMap.get("SM010100T0_09")) 		//
						&& "".equals((String) dataMap.get("SM010100T0_10")) 		//
						&& "".equals((String) dataMap.get("SM010100T0_14")) 		//
						&& "".equals((String) dataMap.get("STOCKB")) 				// 存放位置(倉)
						&& "".equals((String) dataMap.get("STOCKMED")) 				// 存放位置(儲)
						&& "".equals((String) dataMap.get("STOCKS")) 				// 存放位置(櫃)	
						&& "".equals((String) dataMap.get("STOCKB_O")) 				// 存放位置(倉)
						&& "".equals((String) dataMap.get("STOCKMED_O")) 			// 存放位置(儲)
						&& "".equals((String) dataMap.get("STOCKS_O")) 				// 存放位置(櫃)	
						&& "".equals((String) dataMap.get("SM010100T0_16")) 		// 刀具說明		
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				if(
						   (String)dataMap.get("SM010100T0_01") 		== null		
						&& (String)dataMap.get("SM010100T0_02")			== null	
						&& (String)dataMap.get("SM010100T0_12")			== null		
						&& (String)dataMap.get("SM010100T0_06")			== null						
						&& (String)dataMap.get("SM010100T0_17")			== null		
						&& (String)dataMap.get("SM010100T0_18")			== null
						&& (String)dataMap.get("SM010100T0_08")			== null
						&& (String)dataMap.get("SM010100T0_19")			== null
						&& (String)dataMap.get("SM010100T0_07")			== null
						&& (String)dataMap.get("SM010100T0_05")			== null
						&& (String)dataMap.get("SM010100T0_09")			== null
						&& (String)dataMap.get("SM010100T0_10")			== null
						&& (String)dataMap.get("SM010100T0_14")			== null
						&& (String)dataMap.get("STOCKB")				== null		
						&& (String)dataMap.get("STOCKMED")				== null
						&& (String)dataMap.get("STOCKS")				== null
						&& (String)dataMap.get("STOCKB_O")				== null		
						&& (String)dataMap.get("STOCKMED_O")			== null
						&& (String)dataMap.get("STOCKS_O")				== null
						&& (String)dataMap.get("SM010100T0_16")			== null								
			
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   "".equals((String) dataMap.get("SM010100T0_01")) 		// 刀具編號
						|| "".equals((String) dataMap.get("SM010100T0_02")) 		// 刀具名稱
						|| "".equals((String) dataMap.get("SM010100T0_12")) 		// 刃長外徑
						|| "".equals((String) dataMap.get("SM010100T0_06")) 		// 有效長度			
						|| "".equals((String) dataMap.get("SM010100T0_17"))			// R度
						|| "".equals((String) dataMap.get("SM010100T0_18")) 		// 刃數
						|| "".equals((String) dataMap.get("SM010100T0_08")) 		//
						|| "".equals((String) dataMap.get("SM010100T0_19")) 		//
						|| "".equals((String) dataMap.get("SM010100T0_07")) 		//
						|| "".equals((String) dataMap.get("SM010100T0_05")) 		//
						|| "".equals((String) dataMap.get("SM010100T0_09")) 		//
						|| "".equals((String) dataMap.get("SM010100T0_10")) 		//
						//|| "".equals((String) dataMap.get("SM010100T0_14")) 		//
						|| "".equals((String) dataMap.get("STOCKB")) 				// 存放位置(倉)
						//|| "".equals((String) dataMap.get("STOCKMED")) 			// 存放位置(儲)
						//|| "".equals((String) dataMap.get("STOCKS")) 				// 存放位置(櫃)	
						|| "".equals((String) dataMap.get("STOCKB_O")) 				// 存放位置(倉)
						//|| "".equals((String) dataMap.get("STOCKMED_O")) 			// 存放位置(儲)
						//|| "".equals((String) dataMap.get("STOCKS_O")) 			// 存放位置(櫃)
						//|| "".equals((String) dataMap.get("SM010100T0_16")) 		// 刀具說明						
					){
					dataMap.put("error", "未正確填寫欄位資料!" + "<br>");
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   (String)dataMap.get("SM010100T0_01") 		== null		
						|| (String)dataMap.get("SM010100T0_02")			== null	
						|| (String)dataMap.get("SM010100T0_12")			== null		
						|| (String)dataMap.get("SM010100T0_06")			== null						
						|| (String)dataMap.get("SM010100T0_17")			== null		
						|| (String)dataMap.get("SM010100T0_18")			== null
						|| (String)dataMap.get("SM010100T0_08")			== null
						|| (String)dataMap.get("SM010100T0_19")			== null
						|| (String)dataMap.get("SM010100T0_07")			== null
						|| (String)dataMap.get("SM010100T0_05")			== null
						|| (String)dataMap.get("SM010100T0_09")			== null
						|| (String)dataMap.get("SM010100T0_10")			== null
						//|| (String)dataMap.get("SM010100T0_14")			== null
						|| (String)dataMap.get("STOCKB")				== null		
						//|| (String)dataMap.get("STOCKMED")				== null
						//|| (String)dataMap.get("STOCKS")					== null
						|| (String)dataMap.get("STOCKB_O")				== null		
						//|| (String)dataMap.get("STOCKMED_O")				== null
						//|| (String)dataMap.get("STOCKS_O")				== null
						//|| (String)dataMap.get("SM010100T0_16")			== null								
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
				
				//驗證刀具編號是否大於100個字
				if(((String)dataMap.get("SM010100T0_01")).length()>100){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "刀具編號不能大於100個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "刀具編號不能大於100個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證刀具名稱是否大於50個字
				if(((String)dataMap.get("SM010100T0_02")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "刀具名稱不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "刀具名稱不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證廠商刀具編號是否大於50個字
				if(((String)dataMap.get("SM010100T0_14")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "廠商刀具編號不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "廠商刀具編號不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證刀具說明是否大於200個字
				if(((String)dataMap.get("SM010100T0_16")).length()>200){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "刀具說明不能大於200個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "刀具說明不能大於200個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				OITDIAMETER = Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_12"), "OITDIAMETER", comp_id);
				
				if("".equals(OITDIAMETER) || OITDIAMETER == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此刃長外徑類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此刃長外徑類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				SIZE = Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_06"), "SIZE", comp_id);
				
				if("".equals(SIZE) || SIZE == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此有效長度類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此有效長度類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				RDEGREE = Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_17"), "RDEGREE", comp_id);
				
				if("".equals(RDEGREE) || RDEGREE == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此R度類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此R度類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				TEETH = Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_18"), "TEETH", comp_id);
				
				if("".equals(TEETH) || TEETH == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此刃數類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此刃數類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				RODDIAMETER = Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_19"), "RODDIAMETER", comp_id);
				
				if("".equals(RODDIAMETER) || RODDIAMETER == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此桿徑類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此桿徑類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				MATERIAL = Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_05"), "MATERIAL", comp_id);
				
				if("".equals(MATERIAL) || MATERIAL == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此材質類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此材質類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				LENGTH = Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_07"), "LENGTH", comp_id);
				
				if("".equals(LENGTH) || LENGTH == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此總長類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此總長類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				ANGLE = Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_08"), "ANGLE", comp_id);
				
				if("".equals(ANGLE) || ANGLE == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此角度類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此角度類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if("新品".equals((String)dataMap.get("SM010100T0_10"))){
					
				}else if("二次加工品".equals((String)dataMap.get("SM010100T0_10"))){
					
				}else{
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此刀具狀態!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此刀具狀態!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				STOCKB = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKB"), comp_id);
				
				if("".equals(STOCKB) || STOCKB == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此新品倉位類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此新品倉位類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if(!"".equals((String) dataMap.get("STOCKMED")) && (String) dataMap.get("STOCKMED") !=null){
					STOCKMED = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED"), comp_id);
				
					if("".equals(STOCKMED) || STOCKMED == null){
						if (dataMap.get("error") == null){
							dataMap.put("error", "系統內無此新品儲位類別!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此新品儲位類別!請再次確認" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				if(!"".equals((String) dataMap.get("STOCKS")) && (String) dataMap.get("STOCKS") !=null){
					STOCKS = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKS"), comp_id);
				
					if("".equals(STOCKS) || STOCKS == null){
						if (dataMap.get("error") == null){
							dataMap.put("error", "系統內無此新品櫃位類別!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此新品櫃位類別!請再次確認" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				if(!"".equals((String) dataMap.get("STOCKMED")) && (String) dataMap.get("STOCKMED") !=null){
					check_STOCK1 = Verification.isParent(conn, Verification.getSTOCKD(conn, (String)dataMap.get("STOCKB"), comp_id), 
										  				 Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED"), comp_id), comp_id);
					if(check_STOCK1){
						if (dataMap.get("error") == null){
							dataMap.put("error", "此新品倉位與儲位上下關係錯誤!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "此新品倉位與儲位上下關係錯誤!請再次確認" + "<br>");
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
								dataMap.put("error", "此新品儲位與櫃位上下關係錯誤!請再次確認"+"<br>");//
							} else {
								dataMap.put("error", dataMap.get("error").toString()+ " " + "此新品儲位與櫃位上下關係錯誤!請再次確認" + "<br>");
							}
							check_Exist=true;
						}
					}
				}
				
				//-----
				STOCKB_O = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKB_O"), comp_id);
				
				if("".equals(STOCKB_O) || STOCKB_O == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此舊品倉位類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此舊品倉位類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if(!"".equals((String) dataMap.get("STOCKMED_O")) && (String) dataMap.get("STOCKMED_O") !=null){
					STOCKMED_O = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED_O"), comp_id);
				
					if("".equals(STOCKMED_O) || STOCKMED_O == null){
						if (dataMap.get("error") == null){
							dataMap.put("error", "系統內無此舊品儲位類別!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此舊品儲位類別!請再次確認" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				if(!"".equals((String) dataMap.get("STOCKS_O")) && (String) dataMap.get("STOCKS_O") !=null){
					STOCKS_O = Verification.getSTOCKD(conn, (String)dataMap.get("STOCKS_O"), comp_id);
				
					if("".equals(STOCKS_O) || STOCKS_O == null){
						if (dataMap.get("error") == null){
							dataMap.put("error", "系統內無此舊品櫃位類別!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此舊品櫃位類別!請再次確認" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				if(!"".equals((String) dataMap.get("STOCKMED_O")) && (String) dataMap.get("STOCKMED_O") !=null){
					check_STOCK3 = Verification.isParent(conn, Verification.getSTOCKD(conn, (String)dataMap.get("STOCKB_O"), comp_id), 
										  				 Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED_O"), comp_id), comp_id);
					if(check_STOCK3){
						if (dataMap.get("error") == null){
							dataMap.put("error", "此舊品倉位與儲位上下關係錯誤!請再次確認"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "此舊品倉位與儲位上下關係錯誤!請再次確認" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				if(!"".equals((String) dataMap.get("STOCKMED_O")) && (String) dataMap.get("STOCKMED_O") !=null){
					if(!"".equals((String) dataMap.get("STOCKS_O")) && (String) dataMap.get("STOCKS_O") !=null){
						check_STOCK4 = Verification.isParent(conn, Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED_O"), comp_id), 
								  							 Verification.getSTOCKD(conn, (String)dataMap.get("STOCKS_O"), comp_id), comp_id);
						if(check_STOCK4){
							if (dataMap.get("error") == null){
								dataMap.put("error", "此舊品儲位與櫃位上下關係錯誤!請再次確認"+"<br>");//
							} else {
								dataMap.put("error", dataMap.get("error").toString()+ " " + "此舊品儲位與櫃位上下關係錯誤!請再次確認" + "<br>");
							}
							check_Exist=true;
						}
					}
				}
				//-----
				
				supplier = Verification.getSupplier(conn, (String)dataMap.get("SM010100T0_09"), comp_id);
				
				if("".equals(supplier) || supplier == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此廠商!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此廠商!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if(!"".equals(supplier) && supplier != null){
					UID = ((String)dataMap.get("SM010100T0_01")).split("\\*");
				
					//重組新UID
					NEW_UID = UID[0]+"*"+UID[1]+"*"+UID[2]+"*"+UID[3]+"*"+UID[4]+"*"+UID[5]+"*"+UID[6]+"*"+UID[7]
				                 +"*"+Verification.getSupplier(conn, (String)dataMap.get("SM010100T0_09"), comp_id)+"*"+UID[8];
				
					sql = "" +
					" SELECT SM010100T0_01 " +
					" FROM SM010100T0 " +
					" WHERE 1=1 " +
					" AND SM010100T0_01 = '"+NEW_UID+"' " +  //刀具編號
					" AND SM010100T0_09 = '"+Verification.getSupplier(conn, (String)dataMap.get("SM010100T0_09"), comp_id)+"' " +  //廠商編號
					" AND CompanySysNo = '"+comp_id+"' ";  //公司代碼
				
					rs = stmt.executeQuery(sql);
				
					if(rs.next()){
						check_Exist = true;
						if (dataMap.get("error") == null)
							dataMap.put("error", "與資料庫資料重複，請再次確認!" + "<br>");
						else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "與資料庫資料重複，請再次確認!" + "<br>");
						}
					}
				
					if(rs!=null){
						rs.close();
					}				
				}else{
					if (dataMap.get("error") == null){
						dataMap.put("error", "由於廠商錯誤!故不檢查是否有資料重複"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "由於廠商錯誤!故不檢查是否有資料重複" + "<br>");
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
		String UID[];
		String NEW_UID = "";
		
		try{
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;
			
			//新增
			String sql = "" +
			" INSERT INTO SM010100T0 ( " +
			" SM010100T0_01, SM010100T0_02, SM010100T0_12, SM010100T0_06, SM010100T0_17, " +
			" SM010100T0_18, SM010100T0_08, SM010100T0_19, SM010100T0_07, SM010100T0_05, " +
			" SM010100T0_09, SM010100T0_10, SM010100T0_14, SM010100T0_13, " +
			" STOCKB, 	     STOCKMED, 	    STOCKS, 	   SM010100T0_16, " +
			" STOCKB_O, 	 STOCKMED_O, 	STOCKS_O, " +
			" CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( " +
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?, ?, " +
			" ?, ?, ?, ?, " +
			" ?, ?, ?, " +
			" ?, ?, ?, '1', NOW(), NOW()) ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				UID = ((String)dataMap.get("SM010100T0_01")).split("\\*");
				
				//重組新UID
				NEW_UID = UID[0]+"*"+UID[1]+"*"+UID[2]+"*"+UID[3]+"*"+UID[4]+"*"+UID[5]+"*"+UID[6]+"*"+UID[7]
				                 +"*"+Verification.getSupplier(conn, (String)dataMap.get("SM010100T0_09"), comp_id)+"*"+UID[8];
				
				indexid = 1;
				p6stmt.setString(indexid,NEW_UID);//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010100T0_02"));//
				indexid++;
				p6stmt.setString(indexid,Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_12"), "OITDIAMETER", comp_id));//
				indexid++;
				p6stmt.setString(indexid,Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_06"), "SIZE", comp_id));//
				indexid++;
				p6stmt.setString(indexid,Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_17"), "RDEGREE", comp_id));//
				indexid++;
				p6stmt.setString(indexid,Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_18"), "TEETH", comp_id));//
				indexid++;
				p6stmt.setString(indexid,Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_08"), "ANGLE", comp_id));//
				indexid++;
				p6stmt.setString(indexid,Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_19"), "RODDIAMETER", comp_id));//
				indexid++;
				p6stmt.setString(indexid,Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_07"), "LENGTH", comp_id));//
				indexid++;
				p6stmt.setString(indexid,Verification.getLEXICON(conn, (String)dataMap.get("SM010100T0_05"), "MATERIAL", comp_id));//
				indexid++;
				p6stmt.setString(indexid,Verification.getSupplier(conn, (String)dataMap.get("SM010100T0_09"), comp_id));//
				indexid++;
				if("新品".equals((String)dataMap.get("SM010100T0_10"))){
					p6stmt.setString(indexid, "N");//
					indexid++;
				}else if("二次加工品".equals((String)dataMap.get("SM010100T0_10"))){
					p6stmt.setString(indexid, "R");//
					indexid++;
				}
				p6stmt.setString(indexid,(String)dataMap.get("SM010100T0_14")==null?"":(String)dataMap.get("SM010100T0_14"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010100T0_01"));//
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
				p6stmt.setString(indexid,(String)dataMap.get("SM010100T0_16")==null?"":(String)dataMap.get("SM010100T0_16"));//
				indexid++;
				p6stmt.setString(indexid,Verification.getSTOCKD(conn, (String)dataMap.get("STOCKB_O"), comp_id));//
				indexid++;
				if(!"".equals((String) dataMap.get("STOCKMED_O")) && (String) dataMap.get("STOCKMED_O") !=null){
					p6stmt.setString(indexid,Verification.getSTOCKD(conn, (String)dataMap.get("STOCKMED_O"), comp_id));//
					indexid++;
				}else{
					p6stmt.setString(indexid,(String)dataMap.get("STOCKMED_O")==null?"":(String)dataMap.get("STOCKMED_O"));//
					indexid++;
				}
				if(!"".equals((String) dataMap.get("STOCKS_O")) && (String) dataMap.get("STOCKS_O") !=null){
					p6stmt.setString(indexid,Verification.getSTOCKD(conn, (String)dataMap.get("STOCKS_O"), comp_id));//
					indexid++;
				}else{
					p6stmt.setString(indexid,(String)dataMap.get("STOCKS_O")==null?"":(String)dataMap.get("STOCKS_O"));//
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
