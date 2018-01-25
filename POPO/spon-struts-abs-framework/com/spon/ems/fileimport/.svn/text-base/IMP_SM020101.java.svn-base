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

import org.apache.commons.validator.GenericValidator;

import com.p6spy.engine.spy.P6PreparedStatement;

import jxl.Sheet;
import jxl.Workbook;

import vtgroup.ems.common.vo.AuthorizedBean;

public class IMP_SM020101 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private String user_emp_id = "";
	private AuthorizedBean authbean = null;
	private String UID = "";
	
	public IMP_SM020101( String user_emp_id, AuthorizedBean authbean, String UID ) {
		
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;
		this.authbean = authbean;
		this.UID = UID;	//盤點單單號
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
		Map dataMap = null;
		
		try{
			//取得第一個Sheet資料
			Sheet st = wbk.getSheet(0);
			
			//第一筆資料為表頭, 不列入匯入資料
			for( int i=6; i<st.getRows(); i++ ){				
				//所有欄位皆空白就跳出該迴圈
				if("".equals(st.getCell( 0, i).getContents())	&&"".equals(st.getCell( 1, i).getContents())
					&&"".equals(st.getCell( 2, i).getContents())	&&"".equals(st.getCell( 3, i).getContents())
					&&"".equals(st.getCell( 4, i).getContents())	&&"".equals(st.getCell( 5, i).getContents())
					&&"".equals(st.getCell( 6, i).getContents())	&&"".equals(st.getCell( 7, i).getContents())
					&&"".equals(st.getCell( 8, i).getContents())	&&"".equals(st.getCell( 9, i).getContents())){
					continue;
				}
				
				dataMap = new HashMap();
				dataMap.put("NUMBER", st.getCell( 0, i).getContents());//項次
				dataMap.put("SM020101T1_03", st.getCell( 1, i).getContents().trim());//刀具編號
				dataMap.put("SM010100T0_13", st.getCell( 2, i).getContents().trim());//刀具編號
				dataMap.put("SM010100T0_02", st.getCell( 3, i).getContents().trim());//刀具名稱
				dataMap.put("SM010100T0_03", st.getCell( 4, i).getContents().trim());//刀具別名
				dataMap.put("SM010100T0_09", st.getCell( 5, i).getContents().trim());//廠商
				dataMap.put("PALCE", st.getCell( 6, i).getContents().trim());//存放位置
				dataMap.put("SM020101T1_09", st.getCell( 7, i).getContents().trim());//盤點數量
				dataMap.put("SM020101T1_10", st.getCell( 8, i).getContents().trim());//目前存量								
				//dataMap.put("SM020101T1_11", st.getCell( 8, i).getContents().trim());//差異數量
				dataMap.put("SM020101T1_12", st.getCell( 9, i).getContents().trim());//備註
				
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
		String place[];
		
		try{
			//檢核未通過的筆數(重複不匯入清單)
			int ng_count = 0;
			
			//資料不正確清單			
			int error_count = 0;
			
			//檢核匯入資料清單
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if("".equals((String)dataMap.get("SM020101T1_03")) || "".equals((String)dataMap.get("SM010100T0_02")) 
				|| "".equals((String)dataMap.get("SM010100T0_09")) || "".equals((String)dataMap.get("SM020101T1_09"))
				|| "".equals((String)dataMap.get("SM020101T1_10"))
				|| "".equals((String)dataMap.get("SM010100T0_13"))){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!欄位資料未正確填寫!輸入資料有空白!" + "<br>");
						error_list.add(dataMap);	// 將此筆資料移至資料不正確清單					
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", (dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!欄位資料未正確填寫!輸入資料有空白!")+ "<br>");
						error_list.add(dataMap);	// 將此筆資料移至資料不正確清單					
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}					
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if((String)dataMap.get("SM020101T1_03") == null || (String)dataMap.get("SM010100T0_02") == null
				|| (String)dataMap.get("SM010100T0_09") == null || (String)dataMap.get("SM020101T1_09") == null
				|| (String)dataMap.get("SM020101T1_10") == null
				|| (String)dataMap.get("SM010100T0_13") == null){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!欄位資料未正確填寫!輸入資料有空白!" + "<br>");
						error_list.add(dataMap);	// 將此筆資料移至資料不正確清單						
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", (dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!欄位資料未正確填寫!輸入資料有空白!")+ "<br>");
						error_list.add(dataMap);	// 將此筆資料移至資料不正確清單					
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}					
				}
				
				if(((String)dataMap.get("SM020101T1_03")).length()>100){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!刀具編號字數超過100個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!刀具編號字數超過100個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}
				
				if(((String)dataMap.get("SM010100T0_02")).length()>50){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!刀具名稱字數超過50個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!刀具名稱字數超過50個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}
				
				if(((String)dataMap.get("SM010100T0_03")).length()>50){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!刀具別名字數超過50個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!刀具別名字數超過50個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}
				
				if(((String)dataMap.get("SM010100T0_09")).length()>50){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!廠商字數超過50個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!廠商字數超過50個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}
				
				if(((String)dataMap.get("SM020101T1_12")).length()>100){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!單項備註字數超過100個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!單項備註字數超過100個字!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}
				
				if(GenericValidator.isInt((String)dataMap.get("SM020101T1_09")) || GenericValidator.isDouble((String)dataMap.get("SM020101T1_09")) 
				|| GenericValidator.isLong((String)dataMap.get("SM020101T1_09")) || GenericValidator.isShort((String)dataMap.get("SM020101T1_09")) ){
					if(Integer.parseInt((String)dataMap.get("SM020101T1_09"))<0){
						if(dataMap.get("error")==null){
							dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!盤點數量最小等於0，不可能有負數!" + "<br>");
							error_list.add(dataMap);
							it.remove(); // 將此筆資料由待匯入清單移除
							error_count++;
							// 結束此次迴圈
							continue;
						}else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!盤點數量最小等於0，不可能有負數!" + "<br>");
							error_list.add(dataMap);
							it.remove(); // 將此筆資料由待匯入清單移除
							error_count++;
							// 結束此次迴圈
							continue;
						}
					}		
				}else{
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!盤點數量請輸入數值!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!盤點數量請輸入數值!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}
				
				if(GenericValidator.isInt((String)dataMap.get("SM020101T1_10")) || GenericValidator.isDouble((String)dataMap.get("SM020101T1_10")) 
				|| GenericValidator.isLong((String)dataMap.get("SM020101T1_10")) || GenericValidator.isShort((String)dataMap.get("SM020101T1_10")) ){
					if(Integer.parseInt((String)dataMap.get("SM020101T1_10"))<0){
						if(dataMap.get("error")==null){
							dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!目前存量最小等於0，不可能有負數!" + "<br>");
							error_list.add(dataMap);
							it.remove(); // 將此筆資料由待匯入清單移除
							error_count++;
							// 結束此次迴圈
							continue;
						}else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!目前存量最小等於0，不可能有負數!" + "<br>");
							error_list.add(dataMap);
							it.remove(); // 將此筆資料由待匯入清單移除
							error_count++;
							// 結束此次迴圈
							continue;
						}
					}
				}else{
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!目前存量請輸入數值!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!目前存量請輸入數值!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}
				
				/*if(GenericValidator.isInt((String)dataMap.get("SM020101T1_11")) || GenericValidator.isDouble((String)dataMap.get("SM020101T1_11")) 
				|| GenericValidator.isLong((String)dataMap.get("SM020101T1_11")) || GenericValidator.isShort((String)dataMap.get("SM020101T1_11")) ){
											
				}else{
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!差異數量請輸入數值!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+"筆資料有誤!差異數量請輸入數值!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}*/
				
				/*int DifferencesNum = Integer.parseInt((String)dataMap.get("SM020101T1_11"));
				//int InventoryNum = Integer.parseInt((String)dataMap.get("SM020101T1_10")) - Integer.parseInt((String)dataMap.get("SM020101T1_09"));
				int InventoryNum = Integer.parseInt((String)dataMap.get("SM020101T1_09")) - Integer.parseInt((String)dataMap.get("SM020101T1_10"));
				
				if(DifferencesNum != InventoryNum){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!請再重新計算盤點差異數量!" + "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}else{
						dataMap.put("error", (dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!請再重新計算盤點差異數量!")+ "<br>");
						error_list.add(dataMap);
						it.remove(); // 將此筆資料由待匯入清單移除
						error_count++;
						// 結束此次迴圈
						continue;
					}
				}*/
				
				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = null;
				
				place = ((String)dataMap.get("PALCE")).split("-");
				
				sql = "" +
				" SELECT SM020101T1_03 " +
				" FROM SM020101T1 " +
				" WHERE 1=1 " +
				" AND SM020101T1_01 = '"+this.UID+"' " +  //
				" AND SM020101T1_03 = '"+(String)dataMap.get("SM020101T1_03")+"' " +
				" AND SM020101T1_04 = '"+this.getwarehouseType(conn, place[0], comp_id)+"' " +
				" AND SM020101T1_05 = '"+this.getwarehouseType(conn, place[1], comp_id)+"' " +
				" AND SM020101T1_06 = '"+this.getwarehouseType(conn, place[2], comp_id)+"' " +
				" AND CompanySysNo = '"+comp_id+"' ";  //公司代碼
				
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!此筆資料於資料庫重複!" + "<br>");
						//有找到重複資料, 此筆資料不匯入系統
						ng_list.add(dataMap);  //將此筆資料移至重複不匯入清單
						it.remove();  //將此筆資料由待匯入清單移除
						ng_count++;
					}else{
						dataMap.put("error", (dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!此筆資料於資料庫重複!")+ "<br>");
						//有找到重複資料, 此筆資料不匯入系統
						ng_list.add(dataMap);  //將此筆資料移至重複不匯入清單
						it.remove();  //將此筆資料由待匯入清單移除
						ng_count++;
					}
				}
				//stmt.close();
				rs.close();
				
				ResultSet rs_2 = null;
				
				String sql_2 = "" +
				" SELECT SM010400T0_01 " +
				" FROM SM010400T0 " +
				" WHERE 1=1 " +
				" AND SM010400T0_02 = '"+(String)dataMap.get("SM010100T0_09")+"' " +  //廠商名稱
				" AND CompanySysNo = '"+comp_id+"' ";  //公司代碼
				
				rs_2 = stmt.executeQuery(sql_2);
				
				if(rs_2.next()){
					
				}else{
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!無此廠商名稱!" + "<br>");
						//有找到重複資料, 此筆資料不匯入系統
						error_list.add(dataMap);  //將此筆資料移至重複不匯入清單
						it.remove();  //將此筆資料由待匯入清單移除
						error_count++;
					}else{
						dataMap.put("error", (dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!無此廠商名稱!")+ "<br>");
						//有找到重複資料, 此筆資料不匯入系統
						error_list.add(dataMap);  //將此筆資料移至重複不匯入清單
						it.remove();  //將此筆資料由待匯入清單移除
						error_count++;
					}
				}
				rs_2.close();
				
				ResultSet rs_3 = null;
				
				String sql_3 = "" +
				" SELECT SM010100T0_01 " +
				" FROM SM010100T0 " +
				" WHERE 1=1 " +
				" AND SM010100T0_01 = '"+(String)dataMap.get("SM020101T1_03")+"' " +  //刀具編號
				" AND CompanySysNo = '"+comp_id+"' ";  //公司代碼
				
				rs_3 = stmt.executeQuery(sql_3);
				
				if(rs_3.next()){
					
				}else{
					if(dataMap.get("error")==null){
						dataMap.put("error", "第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!無此刀具編號!" + "<br>");
						//有找到重複資料, 此筆資料不匯入系統
						error_list.add(dataMap);  //將此筆資料移至重複不匯入清單
						it.remove();  //將此筆資料由待匯入清單移除
						error_count++;
					}else{
						dataMap.put("error", (dataMap.get("error").toString()+" "+"第 "+(String)dataMap.get("NUMBER")+" 筆資料有誤!無此刀具編號!")+ "<br>");
						//有找到重複資料, 此筆資料不匯入系統
						error_list.add(dataMap);  //將此筆資料移至重複不匯入清單
						it.remove();  //將此筆資料由待匯入清單移除
						error_count++;
					}
				}
				stmt.close();
				rs_3.close();
				
			}
			
			//判斷是否有重複資料
			//判斷是否有格式不正確資料
			//if(ng_list.size() > 0 || error_list.size() > 0){
				//datalist.removeAll(datalist);	//只要有錯誤就整張報表不準匯入
				//建立回傳訊息Map
				err_msgMap.put("NGDATALIST", ng_list);
				err_msgMap.put("NGDATACOUNT", ng_count);
				err_msgMap.put("ERRORDATACOUNT", error_count);
				err_msgMap.put("ERRORDATALIST", error_list);
				//err_msgMap.put("ERR_MSG", (String)dataMap.get("error")==null?"":(String)dataMap.get("error"));
			//}
			
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
		String place[];
		
		try{
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;
			
			//新增
			String sql = "" +
			" INSERT INTO SM020101T1 ( " +
			" SM020101T1_01, SM020101T1_02, SM020101T1_03, SM020101T1_04, SM020101T1_05, " +
			" SM020101T1_06, SM020101T1_07, SM020101T1_08, SM020101T1_09, SM020101T1_10, " +
			" SM020101T1_11, SM020101T1_12, CompanySysNo) " +
			" VALUES ( " +
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?, ?, ?, "+
			" ?, ?, ?) ";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				indexid = 1;
				p6stmt.setString(indexid,this.UID);//盤點單單號
				indexid++;
				p6stmt.setInt(indexid,Integer.parseInt((String)dataMap.get("NUMBER")));//順序號碼
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM020101T1_03"));//產品編號
				indexid++;
				
				place = ((String)dataMap.get("PALCE")).split("-");
				
				p6stmt.setString(indexid,this.getwarehouseType(conn, place[0], comp_id));//刀具庫別編號
				indexid++;
				p6stmt.setString(indexid,this.getwarehouseType(conn, place[1], comp_id));//刀具儲位編號
				indexid++;
				p6stmt.setString(indexid,this.getwarehouseType(conn, place[2], comp_id));//刀具櫃位編號
				indexid++;
				p6stmt.setString(indexid,"");//
				indexid++;
				p6stmt.setString(indexid,"");//
				indexid++;
				p6stmt.setInt(indexid,Integer.parseInt((String)dataMap.get("SM020101T1_09")) );//盤點數量
				indexid++;
				p6stmt.setInt(indexid,Integer.parseInt((String)dataMap.get("SM020101T1_10")) );//目前存量
				indexid++;
				//p6stmt.setInt(indexid,Integer.parseInt((String)dataMap.get("SM020101T1_11")) );//差異數量
				p6stmt.setInt(indexid,Integer.parseInt((String)dataMap.get("SM020101T1_09")) - Integer.parseInt((String)dataMap.get("SM020101T1_10")) );//差異數量
				indexid++;
				p6stmt.setString(indexid,(String)dataMap.get("SM020101T1_12"));//備註
				indexid++;
				p6stmt.setString(indexid, comp_id);  //公司代碼
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
	
	/**
	 * 取得庫別代號
	 * @param conn
	 * @param type_name 庫別名稱
	 * @param comp_id
	 * @return
	 */
	public String getwarehouseType(Connection conn, String type_name,
			String comp_id) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String type_id = "";
		
		try{
			sql = "" +
			" SELECT STOCKD_04 FROM stockD " +
			" WHERE 1=1 " +
			" AND STOCKD_05 = '"+type_name+"' " +
			" AND CompanySysNo = '"+comp_id+"' ";
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				type_id = rs.getString("STOCKD_04");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return type_id;
	}

}
