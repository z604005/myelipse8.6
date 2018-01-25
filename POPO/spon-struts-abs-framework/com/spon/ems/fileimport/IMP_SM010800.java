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
import com.spon.utils.util.BA_TOOLS;

import jxl.Sheet;
import jxl.Workbook;

import vtgroup.ems.common.vo.AuthorizedBean;

public class IMP_SM010800 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private String user_emp_id = "";
	private AuthorizedBean authbean = null;
	private HttpServletRequest request= null;
	//取得驗證工具
	private IMP_Verification Verification = null;
	private BA_TOOLS tool;
	
	public IMP_SM010800( HttpServletRequest request, String user_emp_id, AuthorizedBean authbean ) {
		
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;
		this.authbean = authbean;
		this.request= request;
		this.Verification = new IMP_Verification(request, user_emp_id, authbean);
		tool = BA_TOOLS.getInstance();
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
					&&"".equals(st.getCell( 6, i).getContents())
					){
					continue;
				}
				
				dataMap = new HashMap<String,String>();
				dataMap.put("SM010800T0_01", st.getCell( 0, i).getContents().trim());//零件編號
				dataMap.put("SM010600T0_03", st.getCell( 1, i).getContents().trim());//客戶
				dataMap.put("SM010800T0_02", st.getCell( 2, i).getContents().trim());//材料
				dataMap.put("SM010800T0_03", st.getCell( 3, i).getContents().trim());//零件加工單位
				dataMap.put("SM010800T0_04", st.getCell( 4, i).getContents().trim());//加工內容
				dataMap.put("SM010800T0_05", st.getCell( 5, i).getContents().trim());//是否代料
				dataMap.put("SM010800T0_C",  st.getCell( 6, i).getContents().trim());//每件委外成本						
				
				
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
		boolean num_flag = false;
		String customer = "";
		
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
				num_flag = false;
				customer = "";
				
				//取得資料
				dataMap = (Map) it.next();
				
				if(
					 	   "".equals((String) dataMap.get("SM010800T0_01")) 		// 
						&& "".equals((String) dataMap.get("SM010600T0_03")) 		// 
						&& "".equals((String) dataMap.get("SM010800T0_02")) 		// 
						&& "".equals((String) dataMap.get("SM010800T0_03")) 		// 		
						&& "".equals((String) dataMap.get("SM010800T0_04"))			// 
						&& "".equals((String) dataMap.get("SM010800T0_05")) 		// 
						&& "".equals((String) dataMap.get("SM010800T0_C")) 			//		
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				if(
						   (String)dataMap.get("SM010800T0_01") 		== null		
						&& (String)dataMap.get("SM010600T0_03")			== null	
						&& (String)dataMap.get("SM010800T0_02")			== null		
						&& (String)dataMap.get("SM010800T0_03")			== null						
						&& (String)dataMap.get("SM010800T0_04")			== null		
						&& (String)dataMap.get("SM010800T0_05")			== null
						&& (String)dataMap.get("SM010800T0_C")			== null							
			
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   "".equals((String) dataMap.get("SM010800T0_01")) 		// 
						|| "".equals((String) dataMap.get("SM010600T0_03")) 		// 
						|| "".equals((String) dataMap.get("SM010800T0_02")) 		// 
						//|| "".equals((String) dataMap.get("SM010800T0_03")) 		// 			
						//|| "".equals((String) dataMap.get("SM010800T0_04"))		// 
						|| "".equals((String) dataMap.get("SM010800T0_05")) 		// 
						|| "".equals((String) dataMap.get("SM010800T0_C")) 			//					
					){
					dataMap.put("error", "未正確填寫欄位資料!" + "<br>");
				}
				
				//匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   (String)dataMap.get("SM010800T0_01") 		== null		
						|| (String)dataMap.get("SM010600T0_03")			== null	
						|| (String)dataMap.get("SM010800T0_02")			== null		
						//|| (String)dataMap.get("SM010800T0_03")			== null						
						//|| (String)dataMap.get("SM010800T0_04")			== null		
						|| (String)dataMap.get("SM010800T0_05")			== null
						|| (String)dataMap.get("SM010800T0_C")			== null							
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
				
				//驗證零件編號是否大於20個字
				if(((String)dataMap.get("SM010800T0_01")).length()>20){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "零件編號不能大於20個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "零件編號不能大於20個字!請再次確認" + "<br>");
					}
					check_Exist=true;
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
				
				//驗證材料是否大於50個字
				if(((String)dataMap.get("SM010800T0_02")).length()>50){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "材料不能大於50個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "材料不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證零件加工單位是否大於20個字
				if(((String)dataMap.get("SM010800T0_03")).length()>20){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "零件加工單位不能大於20個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "零件加工單位不能大於20個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證加工內容是否大於200個字
				if(((String)dataMap.get("SM010800T0_04")).length()>200){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "加工內容不能大於200個字!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "加工內容不能大於200個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				if("是".equals((String)dataMap.get("SM010800T0_05"))){
					
				}else if("否".equals((String)dataMap.get("SM010800T0_05"))){
					
				}else{
					if (dataMap.get("error") == null){
						dataMap.put("error", "是否代料只能填是或否!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "是否代料只能填是或否!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				num_flag = Verification.isNumEmpty((String)dataMap.get("SM010800T0_C"), true);
				
				if(!num_flag){
					if (dataMap.get("error") == null){
						dataMap.put("error", "委外成本請輸入數值!請再次確認"+"<br>");//
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "委外成本請輸入數值!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				sql = "" +
				" SELECT SM010600T0_01 " +
				" FROM SM010600T0 " +
				" WHERE 1=1 " +
				" AND SM010600T0_01 = '"+(String)dataMap.get("SM010800T0_01")+"' " +  //零件編號
				" AND CompanySysNo = '"+comp_id+"' ";  //公司代碼
			
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
				}else{
					check_Exist = true;
					if (dataMap.get("error") == null)
						dataMap.put("error", "無此零件編號，請再次確認!" + "<br>");
					else {
					dataMap.put("error", dataMap.get("error").toString()+ " " + "無此零件編號，請再次確認!" + "<br>");
					}
				}
				
				sql = "" +
				" SELECT SM010800T0_01 " +
				" FROM SM010800T0 " +
				" WHERE 1=1 " +
				" AND SM010800T0_01 = '"+(String)dataMap.get("SM010800T0_01")+"' " +  //零件編號
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
		PreparedStatement pstmt = null;
		P6PreparedStatement p6stmt = null;
		String sql_1 = "";
		String sql = "";
		String show_sql = "";
		boolean flag = false;
		
		try{
			Iterator it = datalist.iterator();
			
			//匯入筆數
			int dataCount = 0;
			//是否代料轉換
			String material = "";
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				sql_1 = "SELECT * FROM SM010800T0 WHERE SM010800T0_01 = '"+(String)dataMap.get("SM010800T0_01")+"'";
				
				//判斷是否已存在委外成本
				flag = Verification.check_Exist(conn,sql_1);
				
				if("是".equals((String)dataMap.get("SM010800T0_05"))){
					material = "true";
				}else{
					material = "false";
				}
				
				if(flag){//更新
					
					sql = "" +
					" UPDATE SM010800T0 SET " +
					" SM010800T0_02=?, SM010800T0_03=?, SM010800T0_04=?, SM010800T0_05=?, SM010800T0_C=?, " +
					" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
					" WHERE 1=1 " +
					" AND SM010800T0_01=? " +
					" AND CompanySysNo=? ";
					
					//執行SQL
					pstmt = conn.prepareStatement(sql);
					p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
					
					int indexid = 1;
					p6stmt.setString(indexid, (String)dataMap.get("SM010800T0_02"));
					indexid++;
					p6stmt.setString(indexid, (String)dataMap.get("SM010800T0_03"));
					indexid++;
					p6stmt.setString(indexid, (String)dataMap.get("SM010800T0_04"));
					indexid++;
					p6stmt.setBoolean(indexid, tool.StringToBoolean(material));
					indexid++;
					p6stmt.setDouble(indexid, Double.parseDouble((String)dataMap.get("SM010800T0_C")));  
					indexid++;
					p6stmt.setString(indexid, owner);  //資料修改者
					indexid++;
					
					p6stmt.setString(indexid, (String)dataMap.get("SM010800T0_01"));  //
					indexid++;
					p6stmt.setString(indexid, comp_id);  //公司代碼
					indexid++;
					
					show_sql = p6stmt.getQueryFromPreparedStatement();
					//執行
					p6stmt.executeUpdate();
					
					dataCount++;
					
				}else{//新增
					
					//新增
					sql = "" +
					" INSERT INTO SM010800T0 ( " +
					" SM010800T0_01, SM010800T0_02, SM010800T0_03, SM010800T0_04, SM010800T0_05, " +
					" SM010800T0_C, " +
					" CompanySysNo, USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE) " +
					" VALUES ( " +
					" ?, ?, ?, ?, ?, "+		
					" ?, " +
					" ?, ?, ?, '1', NOW(), NOW()) ";
					
					pstmt = conn.prepareStatement(sql);
					p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
					
					int indexid = 1;
					p6stmt.setString(indexid,(String)dataMap.get("SM010800T0_01"));//
					indexid++;
					p6stmt.setString(indexid,(String)dataMap.get("SM010800T0_02"));//
					indexid++;
					p6stmt.setString(indexid,(String)dataMap.get("SM010800T0_03"));//
					indexid++;
					p6stmt.setString(indexid,(String)dataMap.get("SM010800T0_04"));//
					indexid++;
					p6stmt.setBoolean(indexid, tool.StringToBoolean(material));
					indexid++;
					p6stmt.setDouble(indexid, Double.parseDouble((String)dataMap.get("SM010800T0_C")));  
					indexid++;
					p6stmt.setString(indexid, comp_id);  //公司代碼
					indexid++;
					p6stmt.setString(indexid, owner);  //
					indexid++;
					p6stmt.setString(indexid, owner);  //
					indexid++;
					
					p6stmt.executeUpdate();
					
					dataCount++;
					
				}
				
			}
			
			if(pstmt != null && p6stmt != null){
				pstmt.close();
				p6stmt.close();
			}
			
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
