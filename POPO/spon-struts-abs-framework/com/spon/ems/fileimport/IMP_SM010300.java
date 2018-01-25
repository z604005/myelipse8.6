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

public class IMP_SM010300 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private String user_emp_id = "";
	private AuthorizedBean authbean = null;
	private HttpServletRequest request= null;
	//取得驗證工具
	private IMP_Verification Verification = null;
	
	public IMP_SM010300( HttpServletRequest request, String user_emp_id, AuthorizedBean authbean ) {
		
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
					&&"".equals(st.getCell( 14, i).getContents())){
					continue;
				}
				
				dataMap = new HashMap<String,String>();
				dataMap.put("SM010300T0_01", st.getCell( 0, i).getContents().trim());//客戶編號
				dataMap.put("SM010300T0_02", st.getCell( 1, i).getContents().trim());//客戶名稱
				dataMap.put("SM010300T0_04", st.getCell( 2, i).getContents().trim());//統一編號
				dataMap.put("SM010300T0_17", st.getCell( 3, i).getContents().trim());//交易方式
				dataMap.put("SM010300T0_06", st.getCell( 4, i).getContents().trim());//客戶地址
				dataMap.put("SM010300T0_14", st.getCell( 5, i).getContents().trim());//出貨地址
				dataMap.put("SM010300T0_07", st.getCell( 6, i).getContents().trim());//電話							
				dataMap.put("SM010300T0_09", st.getCell( 7, i).getContents().trim());//傳真
				dataMap.put("SM010300T0_10", st.getCell( 8, i).getContents().trim());//負責人
				dataMap.put("SM010300T0_11", st.getCell( 9, i).getContents().trim());//聯絡人
				dataMap.put("SM010300T0_15", st.getCell( 10, i).getContents().trim());//發票抬頭
				dataMap.put("SM010300T0_16", st.getCell( 11, i).getContents().trim());//發票地址
				dataMap.put("SM010300T0_23", st.getCell( 12, i).getContents().trim());//備註
				dataMap.put("SM010300T0_19", st.getCell( 13, i).getContents().trim());//電子信箱
				dataMap.put("SM010300T0_18", st.getCell( 14, i).getContents().trim());//開立發票
				
				
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
		boolean check_Email = false;
		boolean check_TWCompanyId = false;
		boolean check_TWID = false;
		boolean check_LeastLength = false;
		String DEAL = "";
		String INVOICE = "";
		
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
				check_Email = false;
				check_TWCompanyId = false;
				check_TWID = false;
				check_LeastLength = false;
				DEAL = "";
				INVOICE = "";
				
				//取得資料
				dataMap = (Map) it.next();
				
				if(
					 	   "".equals((String) dataMap.get("SM010300T0_01")) 		// 客戶編號
						&& "".equals((String) dataMap.get("SM010300T0_02")) 		// 客戶名稱
						&& "".equals((String) dataMap.get("SM010300T0_04")) 		// 統一編號
						&& "".equals((String) dataMap.get("SM010300T0_17")) 		// 交易方式			
						&& "".equals((String) dataMap.get("SM010300T0_06"))			// 客戶地址
						&& "".equals((String) dataMap.get("SM010300T0_14")) 		// 出貨地址
						&& "".equals((String) dataMap.get("SM010300T0_07")) 		// 電話
						&& "".equals((String) dataMap.get("SM010300T0_09")) 		// 傳真
						&& "".equals((String) dataMap.get("SM010300T0_10")) 		// 負責人						
						&& "".equals((String) dataMap.get("SM010300T0_11")) 		// 聯絡人
						&& "".equals((String) dataMap.get("SM010300T0_15")) 		// 發票抬頭
						&& "".equals((String) dataMap.get("SM010300T0_16")) 		// 發票地址
						&& "".equals((String) dataMap.get("SM010300T0_23")) 		// 備註
						&& "".equals((String) dataMap.get("SM010300T0_19")) 		// 電子信箱													
						&& "".equals((String) dataMap.get("SM010300T0_18")) 		// 開立發票			
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				if(
						   (String)dataMap.get("SM010300T0_01") 		== null		
						&& (String)dataMap.get("SM010300T0_02")			== null	
						&& (String)dataMap.get("SM010300T0_04")			== null		
						&& (String)dataMap.get("SM010300T0_17")			== null						
						&& (String)dataMap.get("SM010300T0_06")			== null		
						&& (String)dataMap.get("SM010300T0_14")			== null
						&& (String)dataMap.get("SM010300T0_07")			== null		
						&& (String)dataMap.get("SM010300T0_09")			== null
						&& (String)dataMap.get("SM010300T0_10")			== null		
						&& (String)dataMap.get("SM010300T0_11")			== null	
						&& (String)dataMap.get("SM010300T0_15")			== null		
						&& (String)dataMap.get("SM010300T0_16")			== null	
						&& (String)dataMap.get("SM010300T0_23")			== null		
						&& (String)dataMap.get("SM010300T0_19")			== null							
						&& (String)dataMap.get("SM010300T0_18")			== null								
			
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   "".equals((String) dataMap.get("SM010300T0_01")) 		// 客戶編號
						|| "".equals((String) dataMap.get("SM010300T0_02")) 		// 客戶名稱
						//|| "".equals((String) dataMap.get("SM010300T0_04")) 		// 統一編號
						|| "".equals((String) dataMap.get("SM010300T0_17")) 		// 交易方式			
						//|| "".equals((String) dataMap.get("SM010300T0_06"))		// 客戶地址
						//|| "".equals((String) dataMap.get("SM010300T0_14")) 		// 出貨地址
						|| "".equals((String) dataMap.get("SM010300T0_07")) 		// 電話
						//|| "".equals((String) dataMap.get("SM010300T0_09")) 		// 傳真
						//|| "".equals((String) dataMap.get("SM010300T0_10")) 		// 負責人						
						//|| "".equals((String) dataMap.get("SM010300T0_11")) 		// 聯絡人
						//|| "".equals((String) dataMap.get("SM010300T0_15")) 		// 發票抬頭
						//|| "".equals((String) dataMap.get("SM010300T0_16")) 		// 發票地址
						//|| "".equals((String) dataMap.get("SM010300T0_23")) 		// 備註
						//|| "".equals((String) dataMap.get("SM010300T0_19")) 		// 電子信箱													
						|| "".equals((String) dataMap.get("SM010300T0_18")) 		// 開立發票						
					){
					dataMap.put("error", "未正確填寫欄位資料!" + "<br>");
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   (String)dataMap.get("SM010300T0_01") 		== null		
						|| (String)dataMap.get("SM010300T0_02")			== null	
						//|| (String)dataMap.get("SM010300T0_04")			== null		
						|| (String)dataMap.get("SM010300T0_17")			== null						
						//|| (String)dataMap.get("SM010300T0_06")			== null		
						//|| (String)dataMap.get("SM010300T0_14")			== null
						|| (String)dataMap.get("SM010300T0_07")			== null		
						//|| (String)dataMap.get("SM010300T0_09")			== null
						//|| (String)dataMap.get("SM010300T0_10")			== null		
						//|| (String)dataMap.get("SM010300T0_11")			== null	
						//|| (String)dataMap.get("SM010300T0_15")			== null		
						//|| (String)dataMap.get("SM010300T0_16")			== null	
						//|| (String)dataMap.get("SM010300T0_23")			== null		
						//|| (String)dataMap.get("SM010300T0_19")			== null							
						|| (String)dataMap.get("SM010300T0_18")			== null							
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
				
				//驗證廠商編號是否大於10個字
				if(((String)dataMap.get("SM010300T0_01")).length()>10){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "客戶編號不能大於10個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "客戶編號不能大於10個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證廠商名稱是否大於50個字
				if(((String)dataMap.get("SM010300T0_02")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "客戶名稱不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "客戶名稱不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證廠商地址是否大於100個字
				if(((String)dataMap.get("SM010300T0_06")).length()>100){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "客戶地址不能大於100個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "客戶地址不能大於100個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證出貨地址是否大於100個字
				if(((String)dataMap.get("SM010300T0_14")).length()>100){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "出貨地址不能大於100個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "出貨地址不能大於100個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證電話是否大於20個字
				if(((String)dataMap.get("SM010300T0_07")).length()>20){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "電話不能大於20個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "電話不能大於20個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證傳真是否大於20個字
				if(((String)dataMap.get("SM010300T0_09")).length()>20){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "傳真不能大於20個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "傳真不能大於20個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證負責人是否大於20個字
				if(((String)dataMap.get("SM010300T0_10")).length()>20){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "負責人不能大於20個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "負責人不能大於20個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證聯絡人是否大於20個字
				if(((String)dataMap.get("SM010300T0_11")).length()>20){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "聯絡人不能大於20個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "聯絡人不能大於20個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證發票抬頭是否大於50個字
				if(((String)dataMap.get("SM010300T0_15")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "發票抬頭不能大於20個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "發票抬頭不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證發票地址是否大於100個字
				if(((String)dataMap.get("SM010300T0_16")).length()>100){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "發票地址不能大於100個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "發票地址不能大於100個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證備註是否大於200個字
				if(((String)dataMap.get("SM010300T0_23")).length()>200){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "備註不能大於200個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "備註不能大於200個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if(!"".equals((String) dataMap.get("SM010300T0_04")) && (String) dataMap.get("SM010300T0_04") !=null){
					if(((String) dataMap.get("SM010300T0_04")).length()==8){
						check_TWCompanyId = Verification.isTWCompanyId((String) dataMap.get("SM010300T0_04"));
					}else if(((String) dataMap.get("SM010300T0_04")).length()==10){
						check_TWID = Verification.isTWID((String) dataMap.get("SM010300T0_04"));
					}else{
						check_LeastLength = Verification.isLeastLength((String) dataMap.get("SM010300T0_04"), 8);
					}
					
					if(check_TWCompanyId){
						if (dataMap.get("error") == null){
							dataMap.put("error", "請輸入正確的統一編號!"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "請輸入正確的統一編號!" + "<br>");
						}
						check_Exist=true;
					}
					
					if(check_TWID){
						if (dataMap.get("error") == null){
							dataMap.put("error", "請輸入正確的身分證字號!"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "請輸入正確的身分證字號!" + "<br>");
						}
						check_Exist=true;
					}
					
					if(check_LeastLength){
						if (dataMap.get("error") == null){
							dataMap.put("error", "請填入統一編號或身分證字號!"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "請填入統一編號或身分證字號!" + "<br>");
						}
						check_Exist=true;
					}
					
				}
				
				if(!"".equals((String) dataMap.get("SM010300T0_19")) && (String) dataMap.get("SM010300T0_19") !=null){
					check_Email = Verification.isEmail((String) dataMap.get("SM010300T0_19"));
					if(check_Email){
						if (dataMap.get("error") == null){
							dataMap.put("error", "請輸入正確的Email格式!"+"<br>");//
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "請輸入正確的Email格式!" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				DEAL = Verification.getEMSCategory(conn, (String)dataMap.get("SM010300T0_17"), "DEAL", comp_id);
				
				if("".equals(DEAL) || DEAL == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此交易方式類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此交易方式類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				INVOICE = Verification.getEMSCategory(conn, (String)dataMap.get("SM010300T0_18"), "INVOICE", comp_id);
				
				if("".equals(INVOICE) || INVOICE == null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "系統內無此開立發票類別!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "系統內無此開立發票類別!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				/*if(!"".equals((String) dataMap.get("SM010300T0_07")) && (String) dataMap.get("SM010300T0_07") !=null){
					check_Number = Verification.isNumEmpty((String) dataMap.get("SM010300T0_07"), true);
				}*/
				
				sql = "" +
				" SELECT SM010300T0_01 " +
				" FROM SM010300T0 " +
				" WHERE 1=1 " +
				" AND SM010300T0_01 = '"+(String)dataMap.get("SM010300T0_01")+"' " +  //客戶代號
				" AND SM010300T0_02 = '"+(String)dataMap.get("SM010300T0_02")+"' " +  //客戶名稱
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
			" INSERT INTO SM010300T0 ( " +
			" SM010300T0_01, SM010300T0_02, SM010300T0_04, SM010300T0_17, SM010300T0_06, " +
			" SM010300T0_14, SM010300T0_07, SM010300T0_09, SM010300T0_10, SM010300T0_11, " +
			" SM010300T0_15, SM010300T0_16, SM010300T0_23, SM010300T0_19, SM010300T0_18, " +
			" SM010300T0_20, " +
			" CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
			" VALUES ( " +
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?, ?, ?, "+
			" ?, " +
			" ?, ?, ?, '1', NOW(), NOW()) ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				indexid = 1;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_01"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_02"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_04")==null?"":(String)dataMap.get("SM010300T0_04"));//
				indexid++;
				p6stmt.setString(indexid,Verification.getEMSCategory(conn, (String)dataMap.get("SM010300T0_17"), "DEAL", comp_id));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_06")==null?"":(String)dataMap.get("SM010300T0_06"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_14")==null?"":(String)dataMap.get("SM010300T0_14"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_07"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_09")==null?"":(String)dataMap.get("SM010300T0_09"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_10")==null?"":(String)dataMap.get("SM010300T0_10"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_11")==null?"":(String)dataMap.get("SM010300T0_11"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_15")==null?"":(String)dataMap.get("SM010300T0_15"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_16")==null?"":(String)dataMap.get("SM010300T0_16"));//
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_23")==null?"":(String)dataMap.get("SM010300T0_23"));//備註
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM010300T0_19")==null?"":(String)dataMap.get("SM010300T0_19"));//
				indexid++;
				p6stmt.setString(indexid,Verification.getEMSCategory(conn, (String)dataMap.get("SM010300T0_18"), "INVOICE", comp_id));//
				indexid++;
				p6stmt.setString(indexid, "A");  //
				indexid++;
				p6stmt.setString(indexid, comp_id);  //公司代碼
				indexid++;
				p6stmt.setString(indexid, owner);  //公司代碼
				indexid++;
				p6stmt.setString(indexid, owner);  //公司代碼
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
