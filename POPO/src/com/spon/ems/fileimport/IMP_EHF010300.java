package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.utils.util.BA_TOOLS;

import jxl.Sheet;
import jxl.Workbook;

import vtgroup.ems.common.vo.AuthorizedBean;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/24 下午5:31:08
 */
public class IMP_EHF010300 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private AuthorizedBean authbean = null;
	private String user_emp_id = "";
	private String user_dept_id = "";
	private Map empDepInf = null;
	private Map depMap = null;
	private Map empMap = null;
	
	public IMP_EHF010300( String user_emp_id, String user_dept_id, AuthorizedBean authbean, Map depMap, Map empMap ) { 
			
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;
		this.user_dept_id = user_dept_id;
		this.authbean = authbean;
		this.depMap = depMap;// 取得部門資訊
		this.empMap = empMap;// 取得員工資訊
		
	}
	
	/**
	 * 
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
		
		List<Map<String,String>> xlsdatalist = new ArrayList<Map<String,String>>();
		Map<String,String> dataMap = null;
		
		try{
			//取得第一個Sheet資料
			Sheet st = wbk.getSheet(0);
			
			//前兩筆資料為表頭, 不列入匯入資料
			for( int i=3; i<st.getRows(); i++ ){
				
				dataMap = new LinkedHashMap<String,String>();
				
				if("".equals(st.getCell( 0, i).getContents())&&"".equals(st.getCell( 1, i).getContents())
				 &&"".equals(st.getCell( 2, i).getContents())&&"".equals(st.getCell( 3, i).getContents())
				 &&"".equals(st.getCell( 4, i).getContents())&&"".equals(st.getCell( 5, i).getContents())
				 &&"".equals(st.getCell( 6, i).getContents())&&"".equals(st.getCell( 7, i).getContents())
				 &&"".equals(st.getCell( 8, i).getContents())&&"".equals(st.getCell( 9, i).getContents())
				 &&"".equals(st.getCell(10, i).getContents())&&"".equals(st.getCell(11, i).getContents())
				 &&"".equals(st.getCell(12, i).getContents())){
					continue;
				}
				
				dataMap.put("EHF010300T0_02", 		st.getCell( 0, i).getContents());  //民國年
				dataMap.put("EHF010300T0_04", 		st.getCell( 1, i).getContents());  //單位代號
				dataMap.put("EHF010300T0_05", 		st.getCell( 2, i).getContents());  //員工工號
				dataMap.put("EHF010300T0_06", 		st.getCell( 3, i).getContents());  //假別代碼
				dataMap.put("EHF010300T0_07_Day", 	st.getCell( 4, i).getContents());  //休假時數(天)
				dataMap.put("EHF010300T0_07_Hour", 	"".equals(st.getCell( 5, i).getContents())?"0":st.getCell( 5, i).getContents());  //休假時數(時)
				dataMap.put("EHF010300T0_09_Year",	st.getCell( 6, i).getContents());  //使用日期(起)(年)
				dataMap.put("EHF010300T0_09_Month", st.getCell( 7, i).getContents());  //使用日期(起)(月)
				dataMap.put("EHF010300T0_09_Day", 	st.getCell( 8, i).getContents());  //使用日期(起)(日)
				dataMap.put("EHF010300T0_10_Year", 	st.getCell( 9, i).getContents());  //使用日期(迄)(年)
				dataMap.put("EHF010300T0_10_Month", st.getCell(10, i).getContents());  //使用日期(迄)(月)
				dataMap.put("EHF010300T0_10_Day", 	st.getCell(11, i).getContents());  //使用日期(迄)(日)
				dataMap.put("EHF010300T0_11", 		st.getCell(12, i).getContents());  //給薪休假原因
				
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
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Map dataMap = null;
		Map errMsgMap = null;
		String sql_overlap = "";
		
		try{
			//重複不匯入清單
			List ng_list = new ArrayList();
			int ng_count = 0;
			
			//資料不正確清單
			List error_list = new ArrayList();
			int error_count = 0;
			
			//預先刪除所有匯入檔案重複資料
			this.DELETE_Overlap(datalist);
			
			//檢核匯入資料清單
			Iterator it = datalist.iterator();
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			//格式化日期
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");	
			
			boolean sql_Vacation_Exist=false;
			boolean check_Exist=false;
			//取得系統設定一天工作時數
			float hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
			String[] month_start_date ;
			String[] month_end_date;
			String sql = "";
			
			while(it.hasNext()){
				
				check_Exist=false;
				sql_Vacation_Exist=false;
				String dep_id = "";
				String emp_id = "";
				
				dataMap = (Map) it.next();
				
				if("".equals((String)dataMap.get("EHF010300T0_02")) && (String)dataMap.get("EHF010300T0_02") == null
				&& "".equals((String)dataMap.get("EHF010300T0_04")) && (String)dataMap.get("EHF010300T0_04") == null
				&& "".equals((String)dataMap.get("EHF010300T0_05")) && (String)dataMap.get("EHF010300T0_05") == null
				&& "".equals((String)dataMap.get("EHF010300T0_06")) && (String)dataMap.get("EHF010300T0_06") == null
				&& "".equals((String)dataMap.get("EHF010300T0_07_Day")) && (String)dataMap.get("EHF010300T0_07_Day") == null
				&& "".equals((String)dataMap.get("EHF010300T0_07_Hour")) && (String)dataMap.get("EHF010300T0_07_Hour") == null
				&& "".equals((String)dataMap.get("EHF010300T0_09_Year")) && (String)dataMap.get("EHF010300T0_09_Year") == null
				&& "".equals((String)dataMap.get("EHF010300T0_09_Month")) && (String)dataMap.get("EHF010300T0_09_Month") == null
				&& "".equals((String)dataMap.get("EHF010300T0_09_Day")) && (String)dataMap.get("EHF010300T0_09_Day") == null
				&& "".equals((String)dataMap.get("EHF010300T0_10_Year")) && (String)dataMap.get("EHF010300T0_10_Year") == null
				&& "".equals((String)dataMap.get("EHF010300T0_10_Month")) && (String)dataMap.get("EHF010300T0_10_Month") == null
				&& "".equals((String)dataMap.get("EHF010300T0_10_Day")) && (String)dataMap.get("EHF010300T0_10_Day") == null
				&& "".equals((String)dataMap.get("EHF010300T0_11")) && (String)dataMap.get("EHF010300T0_11") == null){

					//結束此次迴圈
					continue;
				}
				
				if("".equals((String)dataMap.get("EHF010300T0_02")) || (String)dataMap.get("EHF010300T0_02") == null
				|| "".equals((String)dataMap.get("EHF010300T0_04")) || (String)dataMap.get("EHF010300T0_04") == null
				|| "".equals((String)dataMap.get("EHF010300T0_05")) || (String)dataMap.get("EHF010300T0_05") == null
				|| "".equals((String)dataMap.get("EHF010300T0_06")) || (String)dataMap.get("EHF010300T0_06") == null
				|| "".equals((String)dataMap.get("EHF010300T0_07_Day")) || (String)dataMap.get("EHF010300T0_07_Day") == null
				|| "".equals((String)dataMap.get("EHF010300T0_07_Hour")) || (String)dataMap.get("EHF010300T0_07_Hour") == null
				|| "".equals((String)dataMap.get("EHF010300T0_09_Year")) || (String)dataMap.get("EHF010300T0_09_Year") == null
				|| "".equals((String)dataMap.get("EHF010300T0_09_Month")) || (String)dataMap.get("EHF010300T0_09_Month") == null
				|| "".equals((String)dataMap.get("EHF010300T0_09_Day")) || (String)dataMap.get("EHF010300T0_09_Day") == null
				|| "".equals((String)dataMap.get("EHF010300T0_10_Year")) || (String)dataMap.get("EHF010300T0_10_Year") == null
				|| "".equals((String)dataMap.get("EHF010300T0_10_Month")) || (String)dataMap.get("EHF010300T0_10_Month") == null
				|| "".equals((String)dataMap.get("EHF010300T0_10_Day")) || (String)dataMap.get("EHF010300T0_10_Day") == null){
						
					if (dataMap.get("error") == null){
						dataMap.put("error", "必填欄位未填寫!請再次確認!" + "<br>");//20131016修改   BY 賴泓錡
					} else {
					    dataMap.put("error", dataMap.get("error").toString()+ " " + "必填欄位未填寫!請再次確認!" + "<br>");
					}				
					error_list.add(dataMap); // 將此筆資料移至資料不正確清單
					it.remove(); // 將此筆資料由待匯入清單移除
					error_count++;
					// 結束此次迴圈
					continue;
				}
				
				//針對年月日，先補0				
				dataMap.put("EHF010300T0_09_Month", 	this.addZeroForNum((String)dataMap.get("EHF010300T0_09_Month"),2));
				dataMap.put("EHF010300T0_09_Day", 		this.addZeroForNum((String)dataMap.get("EHF010300T0_09_Day"),2));				

				
				dataMap.put("EHF010300T0_10_Month", this.addZeroForNum((String)dataMap.get("EHF010300T0_10_Month"),2));
				dataMap.put("EHF010300T0_10_Day", this.addZeroForNum((String)dataMap.get("EHF010300T0_10_Day"),2));
				
				//判斷假別資料是否在系統設定內
				sql_overlap = "" 
					+ " SELECT * FROM EHF020100T0 WHERE 1=1 "
					+ " AND EHF020100T0_03 = '" + (String)dataMap.get("EHF010300T0_06") + "' "
					+ " AND EHF020100T0_08 = '" + authbean.getCompId() + "' ";
				sql_Vacation_Exist=this.check_Exist(conn, sql_overlap);
				
				//驗證假別，是否有值
				if(("".equals((String)dataMap.get("EHF010300T0_06")))){
					
					check_Exist=true;
					if (dataMap.get("ERROR") == null){
						dataMap.put("ERROR", "假別未填寫!!請檢查!!"+ "<br>");
					}else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "假別未填寫!!請檢查!!" + "<br>");
					}
				}else if(!sql_Vacation_Exist){
					//驗證系統有無假別資料
					
					check_Exist=true;
					if (dataMap.get("ERROR") == null){
						dataMap.put("ERROR", "無假別資料!!請檢查!!"+ "<br>");
					}else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "無假別資料!!請檢查!!" + "<br>");
					}
				}
				
				//轉化代碼為系統內碼
				dep_id = tools.ShowIdtoRealId(conn, "EHF000200T0", (String)dataMap.get("EHF010300T0_04"), authbean.getCompId()); 
				emp_id = tools.ShowIdtoRealId(conn, "EHF010100T0", (String)dataMap.get("EHF010300T0_05"), authbean.getCompId());
				
				//檢驗單位代號是否存在
				if(depMap.get(dep_id)==null){
					
					check_Exist=true;
					if (dataMap.get("ERROR") == null){
						dataMap.put("ERROR", "單位代號不存在!!請檢查!!"+ "<br>");
					}else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "單位代號不存在!!請檢查!!" + "<br>");
					}
				}
				
				//檢驗員工工號是否存在
				if(empMap.get(emp_id)==null){
					
					check_Exist=true;
					if (dataMap.get("ERROR") == null){
						dataMap.put("ERROR", "員工工號不存在!!請檢查!!"+ "<br>");
					}else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "員工工號不存在!!請檢查!!" + "<br>");
					}
				}				
				
				if("".equals((String)dataMap.get("EHF010300T0_07_Day"))){
					
					check_Exist=true;
					if (dataMap.get("ERROR") == null){
						dataMap.put("ERROR", "天數未填寫!!請檢查!!"+ "<br>");
					}else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "天數未填寫!!請檢查!!" + "<br>");
					}
				}
				
				if(!("".equals((String)dataMap.get("EHF010300T0_07_Hour")))){
					//驗證請假"時"是否大於24小時
						if(Integer.parseInt((String)dataMap.get("EHF010300T0_07_Hour"))>=25){
								
							check_Exist=true;
							if (dataMap.get("ERROR") == null){
								dataMap.put("ERROR", "時間錯誤!!請檢查!!"+ "<br>");
							}else {
								dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "時間錯誤!!請檢查!!" + "<br>");
							}
						}
				}else{
						check_Exist=true;
						if (dataMap.get("ERROR") == null){
							dataMap.put("ERROR", "時間未填寫!!請檢查!!"+ "<br>");
						}else {
							dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "時間未填寫!!請檢查!!" + "<br>");
						}
				}
				
				if(!("".equals((String)dataMap.get("EHF010300T0_09_Month"))||"".equals((String)dataMap.get("EHF010300T0_10_Month")))){
					//驗證"使用日期" 月份不能大於12
					if(Integer.parseInt((String)dataMap.get("EHF010300T0_09_Month"))>=13||Integer.parseInt((String)dataMap.get("EHF010300T0_10_Month"))>=13){
							
						check_Exist=true;
						if (dataMap.get("ERROR") == null){
							dataMap.put("ERROR", "月份錯誤!!請檢查!!"+ "<br>");
						}else {
							dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "月份錯誤!!請檢查!!" + "<br>");
						}
					}
				}else{
					check_Exist=true;
					if (dataMap.get("ERROR") == null){
						dataMap.put("ERROR", "月份未填寫!!請檢查!!"+ "<br>");
					}else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "月份未填寫!!請檢查!!" + "<br>");
					}
				}
				
				if(!("".equals((String)dataMap.get("EHF010300T0_09_Year"))||"".equals((String)dataMap.get("EHF010300T0_09_Month"))||"".equals((String)dataMap.get("EHF010300T0_09_Day"))
				   ||"".equals((String)dataMap.get("EHF010300T0_10_Year"))||"".equals((String)dataMap.get("EHF010300T0_10_Month"))||"".equals((String)dataMap.get("EHF010300T0_10_Day")))){	
						//分割字串	
						month_start_date = tools.convertADDateToStrng(
									tools.getLastMonthDay(tools.covertStringToCalendar(
											(String)dataMap.get("EHF010300T0_09_Year")+"/"+(String)dataMap.get("EHF010300T0_09_Month")+"/"+(String)dataMap.get("EHF010300T0_09_Day")))).split("/");
						//分割字串
						month_end_date = tools.convertADDateToStrng(
									tools.getLastMonthDay(tools.covertStringToCalendar(
											(String)dataMap.get("EHF010300T0_10_Year")+"/"+(String)dataMap.get("EHF010300T0_10_Month")+"/"+(String)dataMap.get("EHF010300T0_10_Day")))).split("/");

						//驗證"使用日期" 日期不能大於當月最後一天
							if(Integer.parseInt((String)dataMap.get("EHF010300T0_09_Day"))>Integer.parseInt(month_start_date[2])||
									Integer.parseInt((String)dataMap.get("EHF010300T0_10_Day"))>Integer.parseInt(month_end_date[2])){
								
								check_Exist=true;
								if (dataMap.get("ERROR") == null)
									dataMap.put("ERROR", "日期錯誤!!請檢查!!"+ "<br>");
								else {
									dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "日期錯誤!!請檢查!!" + "<br>");
								}
							}
				}else{
						check_Exist=true;
						dataMap.put("ERROR", "日期未填寫正確!!請檢查!!"+ "<br>");
				}
				
				//檢核給薪休假原因	
				if(((String)dataMap.get("EHF010300T0_11")).length()>=101){
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "給薪休假原因字數長度，請勿超過100個字!!請檢查!!"+ "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "給薪休假原因字數長度，請勿超過100個字!!請檢查!!" + "<br>");
					}
					check_Exist=true;
				}
				
				//檢核年度與使用日期(起)是否為同一年度
				if(Integer.valueOf((String)dataMap.get("EHF010300T0_02"))!=(Integer.valueOf((String)dataMap.get("EHF010300T0_09_Year"))-1911)){
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "使用日期(起)請從今年度開始申請!!請檢查!!"+ "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "使用日期(起)請從今年度開始申請!!請檢查!!" + "<br>");
					}
					check_Exist=true;
				}
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
				Date date01 = sdf.parse((String)dataMap.get("EHF010300T0_09_Year")+"/"+(String)dataMap.get("EHF010300T0_09_Month")+"/"+(String)dataMap.get("EHF010300T0_09_Day"));
//				System.out.println(date01);
				Date date02 = sdf.parse((String)dataMap.get("EHF010300T0_10_Year")+"/"+(String)dataMap.get("EHF010300T0_10_Month")+"/"+(String)dataMap.get("EHF010300T0_10_Day")); 
//				System.out.println(date02);
				
				if(date01.compareTo(date02) > 0){
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "起始日期不可小於結束日期!!請檢查!!"+ "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "起始日期不可小於結束日期!!請檢查!!" + "<br>");
					}
					check_Exist=true;
				}
				
				sql = " SELECT * FROM EHF010300T0 " +
				   	  " WHERE 1=1 " +
				   	  " AND EHF010300T0_12 = '"+comp_id+"' " +  //公司代碼
				   	  " AND EHF010300T0_02 = '"+(String)dataMap.get("EHF010300T0_02")+"' " +
				   	  " AND EHF010300T0_05 = '"+emp_id+"' " +	//員工工號
				   	  " AND EHF010300T0_06 = '"+(String)dataMap.get("EHF010300T0_06")+"' ";	//假別代號
			
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "該員工假別資料設定重複!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "該員工假別資料設定重複!" + "<br>");
					}
				}
				
				//匯入資料格式不正確, 不可匯入
				if (check_Exist) {
					error_list.add(dataMap); // 將此筆資料移至資料不正確清單
					it.remove(); // 將此筆資料由待匯入清單移除
					error_count++;
					// 結束此次迴圈
					
				}
				
			}
			
			if(rs!=null){
				rs.close();
			}							
			stmt.close();
			
			//判斷是否有重複資料
			//判斷是否有格式不正確資料
			if(ng_list.size() > 0 || error_list.size() > 0){
				errMsgMap = new HashMap();
				errMsgMap.put("NGDATACOUNT", ng_count);
				errMsgMap.put("NGDATALIST", ng_list);
				errMsgMap.put("ERRORDATACOUNT", error_count);
				errMsgMap.put("ERRORDATALIST", error_list);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return errMsgMap;
	}

	@Override
	protected Map fileimport(Connection conn, List datalist, String owner,
			String comp_id) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Map msgMap = new HashMap();
		Map dataMap = null;
		//取得系統設定一天工作時數
		float hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
		
		try{
			//格式化日期
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;			
			
			String sql = "" +
			" INSERT INTO ehf010300t0 ( EHF010300T0_02 ,EHF010300T0_03 ,EHF010300T0_04 ,EHF010300T0_05 ,EHF010300T0_06 " +
			" ,EHF010300T0_07 ,EHF010300T0_08, EHF010300T0_09, EHF010300T0_10, EHF010300T0_11, EHF010300T0_12 ,EHF010300T0_13,EHF010300T0_FK" +
			" ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE  ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) " ;
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				String dep_id = "";
				String emp_id = "";
				float dayhours = 0;
				
				//取得資料
				dataMap = (Map) it.next();
				
				//轉化代碼為系統內碼
				dep_id = tools.ShowIdtoRealId(conn, "EHF000200T0", (String)dataMap.get("EHF010300T0_04"), authbean.getCompId()); 
				emp_id = tools.ShowIdtoRealId(conn, "EHF010100T0", (String)dataMap.get("EHF010300T0_05"), authbean.getCompId());
				
				indexid = 1;				
				p6stmt.setString(indexid, (String)dataMap.get("EHF010300T0_02")); // 年度
				indexid++;
				p6stmt.setString(indexid, authbean.getEmployeeID()); // 處理人事
				indexid++;
				p6stmt.setString(indexid, dep_id); // 組織單位
				indexid++;
				p6stmt.setString(indexid, emp_id); // 員工工號
				indexid++;
				p6stmt.setString(indexid, (String)dataMap.get("EHF010300T0_06")); // 假別
				indexid++;
				
				dayhours = (Float.parseFloat((String)dataMap.get("EHF010300T0_07_Day")) * hours) + Float.parseFloat((String)dataMap.get("EHF010300T0_07_Hour"));
				
				p6stmt.setString(indexid, dayhours+""); // 休假時數
				indexid++;
				p6stmt.setString(indexid, dayhours+""); // 剩餘時數
				indexid++;
				p6stmt.setString(indexid, 
						tools.covertDateToString(
								dateFormat.parse((String)dataMap.get("EHF010300T0_09_Year")+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF010300T0_09_Month")))+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF010300T0_09_Day")))+" 12:00:00"), "yyyy/MM/dd HH:mm:ss"));  //使用日期(起)
				indexid++;
				p6stmt.setString(indexid, 
						tools.covertDateToString(
								dateFormat.parse((String)dataMap.get("EHF010300T0_10_Year")+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF010300T0_10_Month")))+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF010300T0_10_Day")))+" 12:00:00"), "yyyy/MM/dd HH:mm:ss"));  //使用日期(迄)
				indexid++;
				p6stmt.setString(indexid, (String)dataMap.get("EHF010300T0_11"));  //給薪休假原因
				indexid++;
				p6stmt.setString(indexid, comp_id);
				indexid++;
				p6stmt.setString(indexid, "02");
				indexid++;
				p6stmt.setString(indexid, "");
				indexid++;
				p6stmt.setString(indexid, owner);
				indexid++;
				p6stmt.setString(indexid, owner);
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
	 * 預先刪除所有匯入檔案重複資料
	 * @param datalist
	 */
	protected void DELETE_Overlap(List datalist) {
		
		Set set = new HashSet();
		List newList = new ArrayList();
		for (Iterator iter = datalist.iterator(); iter.hasNext();) {
			Object element = iter.next();
			if (set.add(element))
				newList.add(element);
		}
		datalist.clear();
		datalist.addAll(newList);
	}
	
	/**
	 * 不足補0
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
	    int strLen = str.length();
	    if (strLen < strLength) {
	        while (strLen < strLength) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(str);// 左補0
	            // sb.append(str).append("0");//右補0
	            str = sb.toString();
	            strLen = str.length();
	        }
	    }

	    return str;
	}
	
	/**
	 * 驗證SQL語法是否有資料
	 * @param conn
	 * @param sql
	 * @return
	 */
	private boolean check_Exist(Connection conn,String sql){
		boolean Exist=false;
		try {
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				Exist=true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Exist;
	}

}
