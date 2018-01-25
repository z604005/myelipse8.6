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

public class IMP_SM010200 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private String user_emp_id = "";
	private AuthorizedBean authbean = null;
	private HttpServletRequest request= null;
	//取得驗證工具
	private IMP_Verification Verification = null;
	
	public IMP_SM010200( HttpServletRequest request, String user_emp_id, AuthorizedBean authbean ) {
		
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
					&&"".equals(st.getCell( 8, i).getContents())	&&"".equals(st.getCell( 9, i).getContents())){
					continue;
				}
				
				dataMap = new HashMap<String,String>();
				dataMap.put("SM010200T0_01", st.getCell( 0, i).getContents().trim());//器具編號
				dataMap.put("SM010200T0_02", st.getCell( 1, i).getContents().trim());//器具名稱
				dataMap.put("SM010200T0_03", st.getCell( 2, i).getContents().trim());//器具別名
				dataMap.put("SM010200T0_04", st.getCell( 3, i).getContents().trim());//器具種類
				dataMap.put("SM010200T0_10", st.getCell( 4, i).getContents().trim());//廠牌
				dataMap.put("SM010200T0_05", st.getCell( 5, i).getContents().trim());//單位
				dataMap.put("STOCKB", 		 st.getCell( 6, i).getContents().trim());//存放位置(倉)
				dataMap.put("STOCKMED", 	 st.getCell( 7, i).getContents().trim());//存放位置(儲)
				dataMap.put("STOCKS", 		 st.getCell( 8, i).getContents().trim());//存放位置(櫃)
				dataMap.put("SM010200T0_16", st.getCell( 9, i).getContents().trim());//器具說明				
				
				
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
		String UNIT = "";
		String STOCKB = "";
		String STOCKMED = "";
		String STOCKS = "";
		
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
				UNIT = "";
				STOCKB = "";
				STOCKMED = "";
				STOCKS = "";
				
				//取得資料
				dataMap = (Map) it.next();
				
				if(
					 	   "".equals((String) dataMap.get("SM010200T0_01")) 		// 器具編號
						&& "".equals((String) dataMap.get("SM010200T0_02")) 		// 器具名稱
						&& "".equals((String) dataMap.get("SM010200T0_03")) 		// 器具別名
						&& "".equals((String) dataMap.get("SM010200T0_04")) 		// 器具種類			
						&& "".equals((String) dataMap.get("SM010200T0_10"))			// 廠牌
						&& "".equals((String) dataMap.get("SM010200T0_05")) 		// 單位
						&& "".equals((String) dataMap.get("STOCKB")) 				// 存放位置(倉)
						&& "".equals((String) dataMap.get("STOCKMED")) 				// 存放位置(儲)
						&& "".equals((String) dataMap.get("STOCKS")) 				// 存放位置(櫃)					
						&& "".equals((String) dataMap.get("SM010200T0_16")) 		// 器具說明		
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				if(
						   (String)dataMap.get("SM010200T0_01") 		== null		
						&& (String)dataMap.get("SM010200T0_02")			== null	
						&& (String)dataMap.get("SM010200T0_03")			== null		
						&& (String)dataMap.get("SM010200T0_04")			== null						
						&& (String)dataMap.get("SM010200T0_10")			== null		
						&& (String)dataMap.get("SM010200T0_05")			== null
						&& (String)dataMap.get("STOCKB")				== null		
						&& (String)dataMap.get("STOCKMED")				== null
						&& (String)dataMap.get("STOCKS")				== null		
						&& (String)dataMap.get("SM010200T0_16")			== null								
			
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   "".equals((String) dataMap.get("SM010200T0_01")) 		// 器具編號
						|| "".equals((String) dataMap.get("SM010200T0_02")) 		// 器具名稱
						//|| "".equals((String) dataMap.get("SM010200T0_03")) 		// 器具別名
						|| "".equals((String) dataMap.get("SM010200T0_04")) 		// 器具種類			
						|| "".equals((String) dataMap.get("SM010200T0_10"))			// 廠牌
						|| "".equals((String) dataMap.get("SM010200T0_05")) 		// 單位
						|| "".equals((String) dataMap.get("STOCKB")) 				// 存放位置(倉)
						//|| "".equals((String) dataMap.get("STOCKMED")) 			// 存放位置(儲)
						//|| "".equals((String) dataMap.get("STOCKS")) 				// 存放位置(櫃)							
						//|| "".equals((String) dataMap.get("SM010200T0_16")) 		// 器具說明						
					){
					dataMap.put("error", "未正確填寫欄位資料!" + "<br>");
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   (String)dataMap.get("SM010200T0_01") 		== null		
						|| (String)dataMap.get("SM010200T0_02")			== null	
						//|| (String)dataMap.get("SM010200T0_03")			== null		
						|| (String)dataMap.get("SM010200T0_04")			== null						
						|| (String)dataMap.get("SM010200T0_10")			== null		
						|| (String)dataMap.get("SM010200T0_05")			== null
						|| (String)dataMap.get("STOCKB")				== null		
						//|| (String)dataMap.get("STOCKMED")				== null
						//|| (String)dataMap.get("STOCKS")					== null		
						//|| (String)dataMap.get("SM010200T0_16")			== null								
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
				
				//驗證器具編號是否大於10個字
				if(((String)dataMap.get("SM010200T0_01")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "器具編號不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "器具編號不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證器具名稱是否大於50個字
				if(((String)dataMap.get("SM010200T0_02")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "器具名稱不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "器具名稱不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證器具別名是否大於50個字
				if(((String)dataMap.get("SM010200T0_03")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "器具別名不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "器具別名不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證廠牌是否大於50個字
				if(((String)dataMap.get("SM010200T0_10")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "廠牌不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "廠牌不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證器具說明是否大於200個字
				if(((String)dataMap.get("SM010200T0_16")).length()>200){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "器具說明不能大於200個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "器具說明不能大於200個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if("量具".equals((String)dataMap.get("SM010200T0_04"))){
					
				}else if("製具".equals((String)dataMap.get("SM010200T0_04"))){
					
				}else{
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此器具種類!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此器具種類!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				UNIT = Verification.getEMSCategory(conn, (String)dataMap.get("SM010200T0_05"), "UNIT", comp_id);
				
				if("".equals(UNIT) || UNIT == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此單位類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此單位類別!請再次確認" + "<br>");
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
				
				sql = "" +
				" SELECT SM010200T0_01 " +
				" FROM SM010200T0 " +
				" WHERE 1=1 " +
				" AND SM010200T0_01 = '"+(String)dataMap.get("SM010200T0_01")+"' " +  //器具編號
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
		
		try{
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;
			
			//新增
			String sql = "" +
			" INSERT INTO SM010200T0 ( " +
			" SM010200T0_01, SM010200T0_02, SM010200T0_03, SM010200T0_04, SM010200T0_05, " +
			" SM010200T0_10, SM010200T0_16, STOCKB, 	   STOCKMED, 	  STOCKS, " +
			" CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( " +
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?, '1', NOW(), NOW()) ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				indexid = 1;
				p6stmt.setString(indexid,(String)dataMap.get("SM010200T0_01"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010200T0_02"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010200T0_03")==null?"":(String)dataMap.get("SM010200T0_03"));//
				indexid++;
				if("量具".equals((String)dataMap.get("SM010200T0_04"))){
					p6stmt.setString(indexid, "01");//
					indexid++;
				}else if("製具".equals((String)dataMap.get("SM010200T0_04"))){
					p6stmt.setString(indexid, "02");//
					indexid++;
				}
				p6stmt.setString(indexid,Verification.getEMSCategory(conn, (String)dataMap.get("SM010200T0_05"), "UNIT", comp_id));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010200T0_10"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010200T0_16")==null?"":(String)dataMap.get("SM010200T0_16"));//
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
