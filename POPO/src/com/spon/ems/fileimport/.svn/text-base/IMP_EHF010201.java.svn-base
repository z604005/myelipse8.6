package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.utils.util.BA_TOOLS;

import jxl.Sheet;
import jxl.Workbook;

/**
 * <Abstract Action> EMS 行事曆匯入元件
 */
public class IMP_EHF010201 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private String user_emp_id = "";
	
	public IMP_EHF010201( String user_emp_id ) {
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;
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
		
		List xlsdatalist = new ArrayList();
		Map dataMap = null;
		
		try{
			//取得第一個Sheet資料
			Sheet st = wbk.getSheet(0);
			
			//第一筆資料為表頭, 不列入匯入資料
			for( int i=3; i<st.getRows(); i++ ){
				if( "".equals(st.getCell( 0, i).getContents())&&"".equals(st.getCell( 1, i).getContents())
				  &&"".equals(st.getCell( 2, i).getContents())&&"".equals(st.getCell( 3, i).getContents())
				  &&"".equals(st.getCell( 4, i).getContents())&&"".equals(st.getCell( 5, i).getContents())
				  &&"".equals(st.getCell( 6, i).getContents())&&"".equals(st.getCell( 7, i).getContents())
				  &&"".equals(st.getCell( 8, i).getContents())&&"".equals(st.getCell( 9, i).getContents())
				  &&"".equals(st.getCell(10, i).getContents())&&"".equals(st.getCell(11, i).getContents())
				  &&"".equals(st.getCell(12, i).getContents())&&"".equals(st.getCell(13, i).getContents()))
						{
							continue;
						}
				
				dataMap = new HashMap();
				dataMap.put("EHF000500T0_03", 		st.getCell( 0, i).getContents());  //年度
				dataMap.put("EHF000500T0_04", 		st.getCell( 1, i).getContents());  //休假類別
				dataMap.put("EHF000500T0_05_year", 	st.getCell( 2, i).getContents());  //休假日期(起)
				dataMap.put("EHF000500T0_05_month", st.getCell( 3, i).getContents());  //休假日期(起)
				dataMap.put("EHF000500T0_05_day", 	st.getCell( 4, i).getContents());  //休假日期(起)
				dataMap.put("EHF000500T0_07_HH", 	st.getCell( 5, i).getContents());  //休假(起)(時)
				dataMap.put("EHF000500T0_07_MM", 	st.getCell( 6, i).getContents());  //休假(起)(分)
				dataMap.put("EHF000500T0_06_year", 	st.getCell( 7, i).getContents());  //休假日期(迄)
				dataMap.put("EHF000500T0_06_month", st.getCell( 8, i).getContents());  //休假日期(迄)
				dataMap.put("EHF000500T0_06_day", 	st.getCell( 9, i).getContents());  //休假日期(迄)
				dataMap.put("EHF000500T0_08_HH", 	st.getCell(10, i).getContents());  //休假(迄)(時)
				dataMap.put("EHF000500T0_08_MM", 	st.getCell(11, i).getContents());  //休假(迄)(分)
				dataMap.put("EHF000500T0_09", 		st.getCell(13, i).getContents());  //休假原因
				dataMap.put("EHF000500T0_12", 		st.getCell(12, i).getContents());  //"大小禮拜"


				
				
				xlsdatalist.add(dataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;
	}
	
	/**
	 * 執行檔案匯入
	 * @param conn
	 * @param datalist
	 * @param owner
	 * @param comp_id
	 * @return
	 */
	protected Map fileimport( Connection conn, List datalist, String owner, String comp_id){
		
		Map msgMap = new HashMap();
		Map<String, String> dataMap = null;
		Map VacationMap = new HashMap();//取得休假類別
		
		this.getVacation(conn,VacationMap,comp_id);
		try{
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;
			
			//新增行事曆資料
			String sql = "" +
			" INSERT INTO EHF000500T0 ( EHF000500T0_02 ,EHF000500T0_03 ,EHF000500T0_04 ,EHF000500T0_05 ,EHF000500T0_06 ," +
			" EHF000500T0_07 ,EHF000500T0_08, EHF000500T0_09, EHF000500T0_10, EHF000500T0_11 ,EHF000500T0_12 ," +
			" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE  ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) " ;

			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			
			Iterator it = datalist.iterator();
		     ArrayList getVacationlist = new ArrayList();
		     
		     
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				indexid = 1;
				p6stmt.setString(indexid, comp_id);  //公司組織(代號)
				indexid++;
				p6stmt.setString(indexid, (String)dataMap.get("EHF000500T0_03"));  //年度
				indexid++;
				if(VacationMap.get((String) dataMap.get("EHF000500T0_04"))!=null){
					getVacationlist=(ArrayList)VacationMap.get((String) dataMap.get("EHF000500T0_04"));
					p6stmt.setString(indexid, (String)getVacationlist.get(0));  //休假類別
				}else{
					p6stmt.setString(indexid, "");  //休假類別
				}
				indexid++;
				p6stmt.setString(indexid,
						(String)dataMap.get("EHF000500T0_05_year")+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_month"), 2)
						+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_day"), 2));  //休假日期(起)
				indexid++;
				p6stmt.setString(indexid, 
						(String)dataMap.get("EHF000500T0_06_year")+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_06_month"), 2)
						+IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_06_day"), 2));  //休假日期(迄)
				indexid++;
				
				p6stmt.setString(indexid, IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_07_HH"), 2)+
						IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_07_MM"), 2));  //休假時間(時/分)(起)
				indexid++;
				p6stmt.setString(indexid, IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_08_HH"), 2)+
						IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_08_MM"), 2));  //休假時間(時/分)(迄)
				indexid++;
				p6stmt.setString(indexid, (String)dataMap.get("EHF000500T0_09"));  //休假原因
				indexid++;
				
				p6stmt.setString(indexid, this.user_emp_id);  //處理人事工號
				indexid++;
				p6stmt.setString(indexid, comp_id);  //公司代碼
				indexid++;
				p6stmt.setString(indexid, (String)dataMap.get("EHF000500T0_12"));  //休假原因
				indexid++;
				p6stmt.setString(indexid, owner);  //建立人員
				indexid++;
				p6stmt.setString(indexid, owner);  //修改人員
				indexid++;
				System.out.println(p6stmt.getQueryFromPreparedStatement());
				p6stmt.executeUpdate();
				dataMap.put("EHF000500T0_05",  
						 (String)dataMap.get("EHF000500T0_05_year")+"/"
						+(String)dataMap.get("EHF000500T0_05_month")+"/"
						+(String)dataMap.get("EHF000500T0_05_day")+" "
						+(String)dataMap.get("EHF000500T0_07_HH")+"："
						+(String)dataMap.get("EHF000500T0_07_MM")
						+"~"
						+(String)dataMap.get("EHF000500T0_06_year")+"/"
						+(String)dataMap.get("EHF000500T0_06_month")+"/"
						+(String)dataMap.get("EHF000500T0_06_day")+" "
						+(String)dataMap.get("EHF000500T0_08_HH")+"："
						+(String)dataMap.get("EHF000500T0_08_MM"));
				if(VacationMap.get((String) dataMap.get("EHF000500T0_04"))!=null){
					getVacationlist=(ArrayList)VacationMap.get((String) dataMap.get("EHF000500T0_04"));
					
					dataMap.put("EHF000500T0_04",  (String)getVacationlist.get(1));
				}
				
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
	/**
	 * 檢核行事曆匯入資料
	 */
	protected Map chkImportDataList(Connection conn, List datalist,String compId) {
		// TODO Auto-generated method stub
		
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		Map err_msgMap = new HashMap();
		Map dataMap = null;
		
		List err_dataList = new ArrayList();
		
		String sql = "";
		String start="";
		String end="";
		
		String[] month_end_date_start;
		String[] month_end_date_end;
		boolean check_Exist=false;
		ArrayList getVacationlist = new ArrayList();
		
		Map VacationMap = new HashMap();
		
		//預先刪除所有匯入檔案重複資料
		this.DELETE_Overlap(datalist);
		
		
		this.getVacation(conn,VacationMap,compId);
		
		try{
			//檢核未通過的筆數
			int ngDataCount = 0;
	
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				check_Exist=false;
				//取得資料
				dataMap = (Map) it.next();
				
				if("".equals((String)dataMap.get("EHF000500T0_03")) && (String)dataMap.get("EHF000500T0_03") == null
						   && "".equals((String)dataMap.get("EHF000500T0_04")) && (String)dataMap.get("EHF000500T0_04") == null
						   && "".equals((String)dataMap.get("EHF000500T0_05_year")) && (String)dataMap.get("EHF000500T0_05_year") == null
						   && "".equals((String)dataMap.get("EHF000500T0_05_day")) && (String)dataMap.get("EHF000500T0_05_day") == null
						   && "".equals((String)dataMap.get("EHF000500T0_07_HH")) && (String)dataMap.get("EHF000500T0_07_HH") == null
						   && "".equals((String)dataMap.get("EHF000500T0_07_MM")) && (String)dataMap.get("EHF000500T0_07_MM") == null
						   && "".equals((String)dataMap.get("EHF000500T0_06_year")) && (String)dataMap.get("EHF000500T0_06_year") == null
						   && "".equals((String)dataMap.get("EHF000500T0_06_month")) && (String)dataMap.get("EHF000500T0_06_month") == null
						   && "".equals((String)dataMap.get("EHF000500T0_06_day")) && (String)dataMap.get("EHF000500T0_06_day") == null
						   && "".equals((String)dataMap.get("EHF000500T0_08_HH")) && (String)dataMap.get("EHF000500T0_08_HH") == null
						   && "".equals((String)dataMap.get("EHF000500T0_08_MM")) && (String)dataMap.get("EHF000500T0_08_MM") == null
						   && "".equals((String)dataMap.get("EHF000500T0_09")) && (String)dataMap.get("EHF000500T0_09") == null){
							//行事曆匯入資料格式不正確, 不可匯入
							//error_list.add(dataMap);  //將此筆資料移至資料不正確清單
							//it.remove();  //將此筆資料由待匯入清單移除
							//error_count++;
							//check_Exist=true;

							//結束此次迴圈
							continue;
						}
						if("".equals((String)dataMap.get("EHF000500T0_03")) || (String)dataMap.get("EHF000500T0_03") == null
								|| "".equals((String)dataMap.get("EHF000500T0_04")) || (String)dataMap.get("EHF000500T0_04") == null
								|| "".equals((String)dataMap.get("EHF000500T0_05_year")) || (String)dataMap.get("EHF000500T0_05_year") == null
								|| "".equals((String)dataMap.get("EHF000500T0_05_day")) || (String)dataMap.get("EHF000500T0_05_day") == null
								|| "".equals((String)dataMap.get("EHF000500T0_07_HH")) || (String)dataMap.get("EHF000500T0_07_HH") == null
								|| "".equals((String)dataMap.get("EHF000500T0_07_MM")) || (String)dataMap.get("EHF000500T0_07_MM") == null
								|| "".equals((String)dataMap.get("EHF000500T0_06_year")) || (String)dataMap.get("EHF000500T0_06_year") == null
								|| "".equals((String)dataMap.get("EHF000500T0_06_month")) || (String)dataMap.get("EHF000500T0_06_month") == null
								|| "".equals((String)dataMap.get("EHF000500T0_06_day")) || (String)dataMap.get("EHF000500T0_06_day") == null
								|| "".equals((String)dataMap.get("EHF000500T0_08_HH")) || (String)dataMap.get("EHF000500T0_08_HH") == null
								|| "".equals((String)dataMap.get("EHF000500T0_08_MM")) || (String)dataMap.get("EHF000500T0_08_MM") == null){
									//行事曆匯入資料格式不正確, 不可匯入

							if (dataMap.get("error") == null){
								dataMap.put("error", "必填欄位未填寫!請再次確認!" + "<br>");//20131016修改   BY 賴泓錡
						    } else {
						    	dataMap.put("error", dataMap.get("error").toString()+ " " + "必填欄位未填寫!請再次確認!" + "<br>");
						    }
							
							
							
							// 新增被移除的項目到檢核未通過的清單
							dataMap.put("EHF000500T0_05",  
									 (String)dataMap.get("EHF000500T0_05_year")+"/"
									+(String)dataMap.get("EHF000500T0_05_month")+"/"
									+(String)dataMap.get("EHF000500T0_05_day")+" "
									+(String)dataMap.get("EHF000500T0_07_HH")+"："
									+(String)dataMap.get("EHF000500T0_07_MM")
									+"~"
									+(String)dataMap.get("EHF000500T0_06_year")+"/"
									+(String)dataMap.get("EHF000500T0_06_month")+"/"
									+(String)dataMap.get("EHF000500T0_06_day")+" "
									+(String)dataMap.get("EHF000500T0_08_HH")+"："
									+(String)dataMap.get("EHF000500T0_08_MM"));
							if(VacationMap.get((String) dataMap.get("EHF000500T0_04"))!=null){
								
								
								getVacationlist=(ArrayList)VacationMap.get((String) dataMap.get("EHF000500T0_04"));
								
								dataMap.put("EHF000500T0_04",  (String)getVacationlist.get(1));
							}
							err_dataList.add(dataMap);
							// 移除此項匯入Rank
							it.remove();
							ngDataCount++;
							continue;
									
						}
				//****************************************************************
				
				//先將月份與日期、時、分補足兩位數
				
				
				dataMap.put("EHF000500T0_05_month",	IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_month"), 2));
				dataMap.put("EHF000500T0_05_day",	IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_05_day"), 2));
				dataMap.put("EHF000500T0_07_HH",	IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_07_HH"), 2));
				dataMap.put("EHF000500T0_07_MM",	IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_07_MM"), 2));
				
				
				dataMap.put("EHF000500T0_06_month",	IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_06_month"), 2));
				dataMap.put("EHF000500T0_06_day",	IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_06_day"), 2));
				dataMap.put("EHF000500T0_08_HH",	IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_08_HH"), 2));
				dataMap.put("EHF000500T0_08_MM",	IMP_EHF010201.addZeroForNum((String)dataMap.get("EHF000500T0_08_MM"), 2));
			      
				

				//****************************************************************
				
				
//------------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------------
				start=(String)dataMap.get("EHF000500T0_05_year")+"/"+
				      (String)dataMap.get("EHF000500T0_05_month")+"/"+
				      (String)dataMap.get("EHF000500T0_05_day")+" ";
				     // (String)dataMap.get("EHF000500T0_07_HH")+":"+
				      //(String)dataMap.get("EHF000500T0_07_MM")+":00";

				end=(String)dataMap.get("EHF000500T0_06_year")+"/"+
				    (String)dataMap.get("EHF000500T0_06_month")+"/"+
				    (String)dataMap.get("EHF000500T0_06_day");
				     // (String)dataMap.get("EHF000500T0_08_HH")+":"+
				      //(String)dataMap.get("EHF000500T0_08_MM")+":00";
				//驗證資料庫是否有重複資料
				//sql = ""+
				//" SELECT EHF000500T0_01 " +
				//" FROM EHF000500T0 " +
				//" WHERE 1=1 " +
				//" AND EHF000500T0_03 = '"+(String)dataMap.get("EHF000500T0_03")+"' " +  //年度
				//" AND EHF000500T0_02 = '"+compId+"' " +  //公司組織(代號)
				//" AND EHF000500T0_05 = '"+start+"' " +  //休假日期(起)
				//" AND EHF000500T0_07 = '"+(String)dataMap.get("EHF000500T0_07_HH")+(String)dataMap.get("EHF000500T0_07_MM")+"' " +  //休假時間(時/分)(起)
				//" AND EHF000500T0_06 = '"+end+"' " +  //休假日期(迄)
				//" AND EHF000500T0_08 = '"+(String)dataMap.get("EHF000500T0_08_HH")+(String)dataMap.get("EHF000500T0_08_MM")+"' " +  //休假時間(時/分)(迄)
				//" AND EHF000500T0_11 = '"+compId+"' ";  //公司代碼
				
				
				sql = ""
					+ " SELECT EHF000500T0.*, "
					+ " DATE_FORMAT(EHF000500T0_05, '%Y年%c月%e日') AS START_DATE, "
					+ " DATE_FORMAT(EHF000500T0_06, '%Y年%c月%e日') AS END_DATE "
					+ " FROM EHF000500T0 " 
					+ " WHERE 1=1 "
					+ " AND EHF000500T0_11 = '"	+ compId + "' ";

				// 年度
				if (!"".equals((String)dataMap.get("EHF000500T0_03"))&& (String)dataMap.get("EHF000500T0_03") != null) {
				sql += " AND EHF000500T0_03 = '" + (String)dataMap.get("EHF000500T0_03")	+ "' ";
				}
				// 休假類別
				//if (!"".equals((String)dataMap.get("EHF000500T0_04"))&& (String)dataMap.get("EHF000500T0_04") != null) {
				//sql += " AND EHF000500T0_04 = '" + (String)dataMap.get("EHF000500T0_04")+ "' ";
				//}

				// 休假日期區間
				if (!"".equals(start)&& start != null && !"".equals(end)&& end != null) {
					//sql += " AND EHF000500T0_05 >= '" + EHF000500T0_05 + "' " 
					//     + " AND EHF000500T0_06 <= '" + EHF000500T0_06 + "' ";
					sql += " AND  '" + start + "' between EHF000500T0_05 AND EHF000500T0_06"
						+  " AND  '" + end + "' between EHF000500T0_05 AND EHF000500T0_06";
				} else if (!"".equals(end)&& end != null) {
					//sql += " AND EHF000500T0_06 <= '" + EHF000500T0_06 + "' ";
					sql += " AND  '" + end + "' between EHF000500T0_05 AND EHF000500T0_06";
				} else if (!"".equals(start)&& start != null) {
					//sql += " AND EHF000500T0_05 >= '" + EHF000500T0_05+ "' ";
					sql += " AND  '" + start + "' between EHF000500T0_05 AND EHF000500T0_06";
				}

				sql += " ORDER BY EHF000500T0_03, EHF000500T0_05, EHF000500T0_07, "
					+ " EHF000500T0_06, EHF000500T0_08, EHF000500T0_04 ";

				boolean sql_Vacation_Exist=this.check_Exist(conn, sql);
				if(sql_Vacation_Exist){
					check_Exist=true;
					dataMap.put("ERROR", "資料庫已有該筆資料!!");
				}
				
				
				//驗證輸入的年度與(起)(訖)年度相同
				int EHF000500T0_03_number= Integer.valueOf((String) dataMap.get("EHF000500T0_03"));//年度
				int EHF000500T0_05_year_number= Integer.valueOf((String) dataMap.get("EHF000500T0_05_year"));//休假日期(起)年度
				int EHF000500T0_06_year_number=Integer.valueOf((String)dataMap.get("EHF000500T0_06_year"));
				
				if(EHF000500T0_03_number!=(EHF000500T0_05_year_number-1911))//年度
				{
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "休假日期(起)年度請與年度相同!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "休假日期(起)年度請與年度相同!" + "<br>");
					}
				}
				
				if(EHF000500T0_03_number!=(EHF000500T0_06_year_number-1911))//年度
				{
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "休假日期(迄)年度請與年度相同!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "休假日期(迄)年度請與年度相同!" + "<br>");
					}
				}
					
//------------------------------------------------------------------------------------------------------------------------------------------------				
//------------------------------------------------------------------------------------------------------------------------------------------------				
				if (!("".equals((String) dataMap.get("EHF000500T0_05_year"))||"".equals((String) dataMap.get("EHF000500T0_06_year")))) {
					// 驗證"使用日期" '年 ' 長度不能大於4位數
					if (((String) dataMap.get("EHF000500T0_05_year")).length() != 4|| ((String) dataMap.get("EHF000500T0_06_year")).length() != 4) {
						check_Exist = true;
						if (dataMap.get("ERROR") == null)
							dataMap.put("ERROR", "年度請輸入四個字!" + "<br>");
						else {
							dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "年度請輸入四個字!" + "<br>");
						}
					}
				}else{
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "年度不可空白!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "年度不可空白!" + "<br>");
					}
				}
//------------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------------
				if (!("".equals((String) dataMap.get("EHF000500T0_05_month"))||"".equals((String) dataMap.get("EHF000500T0_06_month")))) {
					// 驗證"使用日期" 月份不能大於12
					if (Integer.parseInt((String) dataMap.get("EHF000500T0_05_month")) >= 13	
							|| Integer.parseInt((String) dataMap.get("EHF000500T0_06_month")) >= 13) {
						check_Exist = true;
						if (dataMap.get("ERROR") == null)
							dataMap.put("ERROR", "月份不能超過12 " + "<br>");
						else {
							dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "月份不能超過12 " + "<br>");
						}
						// System.out.println("月份不得大於13");
					}
				}else{
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "月份不可空白!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "月份不可空白!" + "<br>");
					}
				}
//------------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------------
				if (!("".equals((String) dataMap.get("EHF000500T0_05_day"))   ||"".equals((String) dataMap.get("EHF000500T0_06_day")))) {
					
					
					if (!("".equals((String) dataMap.get("EHF000500T0_05_year"))  ||"".equals((String) dataMap.get("EHF000500T0_06_year"))||
							"".equals((String) dataMap.get("EHF000500T0_05_month")) ||"".equals((String) dataMap.get("EHF000500T0_06_month"))||
							"".equals((String) dataMap.get("EHF000500T0_05_day"))   ||"".equals((String) dataMap.get("EHF000500T0_06_day")))) {
						//當月最後一天
						month_end_date_start = ems_tools.convertADDateToStrng(ems_tools.getLastMonthDay(
								ems_tools.covertStringToCalendar(
										(String)dataMap.get("EHF000500T0_05_year")+"/"
										+(String)dataMap.get("EHF000500T0_05_month")+"/"
										+(String)dataMap.get("EHF000500T0_05_day")))).split("/");
						month_end_date_end   = ems_tools.convertADDateToStrng(ems_tools.getLastMonthDay(
								ems_tools.covertStringToCalendar(
										(String)dataMap.get("EHF000500T0_06_year")+"/"+
										(String)dataMap.get("EHF000500T0_06_month")+"/"+
										(String)dataMap.get("EHF000500T0_06_day")))).split("/");
						
						//驗證"使用日期" 日期不能大於當月最後一天
						if(Integer.parseInt((String)dataMap.get("EHF000500T0_05_day"))>Integer.parseInt(month_end_date_start[2])
								||Integer.parseInt((String)dataMap.get("EHF000500T0_06_day"))>Integer.parseInt(month_end_date_end[2])){
							check_Exist=true;
							if(dataMap.get("ERROR")==null)
								dataMap.put("ERROR", "日期不存在!" + "<br>");
							else{
								dataMap.put("ERROR", dataMap.get("ERROR").toString()+" "+"日期不存在!" + "<br>");
							}
							//System.out.println("日期不得大於最後一天");
						}	
					}
					
				}else{
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "日期不可空白!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "日期不可空白!" + "<br>");
					}
				}
//------------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------------
				if (!("".equals((String) dataMap.get("EHF000500T0_07_HH"))||"".equals((String) dataMap.get("EHF000500T0_08_HH")))) {
					//驗證請假"時"是否大於24
					if(Integer.parseInt((String)dataMap.get("EHF000500T0_07_HH"))>=25||Integer.parseInt((String)dataMap.get("EHF000500T0_08_HH"))>=25){	
						check_Exist=true;
						if(dataMap.get("ERROR")==null)
							dataMap.put("ERROR", "時錯誤，請檢查!" + "<br>");
						else{
							dataMap.put("ERROR", dataMap.get("ERROR").toString()+" "+"時錯誤，請檢查!" + "<br>");
						}
						//System.out.println("請假 '(時)'大於12");
					}
				}else{
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "時不可空白!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "時不可空白!" + "<br>");
					}
				}
				
//------------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------------
				if (!("".equals((String) dataMap.get("EHF000500T0_07_MM"))||"".equals((String) dataMap.get("EHF000500T0_08_MM")))) {
					//驗證請假"分"是否大於60
					if(Integer.parseInt((String)dataMap.get("EHF000500T0_07_MM"))>=61||Integer.parseInt((String)dataMap.get("EHF000500T0_08_MM"))>=61){	
						check_Exist=true;
						if(dataMap.get("ERROR")==null)
							dataMap.put("ERROR", "分錯誤，請檢查!" + "<br>");
						else{
							dataMap.put("ERROR", dataMap.get("ERROR").toString()+" "+"分錯誤，請檢查!" + "<br>");
						}
						//System.out.println("請假 '(時)'大於12");
					}
				}else{
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "分不可空白!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "分不可空白!" + "<br>");
					}
				}
				
				
				
				
				if(VacationMap.get((String) dataMap.get("EHF000500T0_04"))==null){
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "休假類別錯誤!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "休假類別錯誤!" + "<br>");
					}
				}
				
				
				
				
				
				Calendar start_cal = ems_tools.covertStringToCalendar(start);  //計算開始日期
				Calendar end_cal = ems_tools.covertStringToCalendar(end);  //計算結束日期
				
				if(start_cal.compareTo(end_cal)>0){
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "時間錯誤!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "時間錯誤!" + "<br>");
					}
				}
				
				
				//檢核休假原因長度不可超過50個字
				if(((String)dataMap.get("EHF000500T0_09")).length()>=51){
					check_Exist = true;
					if (dataMap.get("ERROR") == null)
						dataMap.put("ERROR", "休假原因字數請勿超過50個字!" + "<br>");
					else {
						dataMap.put("ERROR", dataMap.get("ERROR").toString()+ " " + "休假原因字數請勿超過50個字!" + "<br>");
					}
				}

				
				
				try
		    	{
		    	  String str =(String)dataMap.get("EHF000500T0_12");
		    	  int a = Integer.parseInt(str);
		    	}
		    	catch (NumberFormatException e)
		    	{
		    		check_Exist = true;
		    		if (dataMap.get("error") == null){
						dataMap.put("error", "大小禮拜請勿填寫3以上數字(包含3)!!" + "<br>");//20131016修改   BY 賴泓錡
				    } else {
				    	dataMap.put("error", dataMap.get("error").toString()+ " " + "大小禮拜請勿填寫3以上數字(包含3)!!" + "<br>");
				    }
		    	}
		    	
		    	if(Integer.valueOf((String)dataMap.get("EHF000500T0_12"))>=3){
		    		check_Exist = true;
		    		if (dataMap.get("error") == null){
		    			dataMap.put("error", "大小禮拜請勿填寫3以上數字(包含3)!!" + "<br>");//20131016修改   BY 賴泓錡
		    		} else {
		    			dataMap.put("error", dataMap.get("error").toString()+ " " + "大小禮拜請勿填寫3以上數字(包含3)!!" + "<br>");
		    		}
		    	}
//------------------------------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------------------------------
				if (check_Exist) {
					// 新增被移除的項目到檢核未通過的清單
					dataMap.put("EHF000500T0_05",  
							 (String)dataMap.get("EHF000500T0_05_year")+"/"
							+(String)dataMap.get("EHF000500T0_05_month")+"/"
							+(String)dataMap.get("EHF000500T0_05_day")+" "
							+(String)dataMap.get("EHF000500T0_07_HH")+"："
							+(String)dataMap.get("EHF000500T0_07_MM")
							+"~"
							+(String)dataMap.get("EHF000500T0_06_year")+"/"
							+(String)dataMap.get("EHF000500T0_06_month")+"/"
							+(String)dataMap.get("EHF000500T0_06_day")+" "
							+(String)dataMap.get("EHF000500T0_08_HH")+"："
							+(String)dataMap.get("EHF000500T0_08_MM"));
					if(VacationMap.get((String) dataMap.get("EHF000500T0_04"))!=null){
						
						
						getVacationlist=(ArrayList)VacationMap.get((String) dataMap.get("EHF000500T0_04"));
						
						dataMap.put("EHF000500T0_04",  (String)getVacationlist.get(1));
					}
					err_dataList.add(dataMap);
					// 移除此項匯入Rank
					it.remove();
					ngDataCount++;
				}
				
			}

			
			
			//建立回傳訊息Map
			err_msgMap.put("NGDATALIST", err_dataList);
			err_msgMap.put("NGDATACOUNT", ngDataCount);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return err_msgMap;
	}

	@Override
	public String getEMS_IMPORT_TYPE() {
		// TODO Auto-generated method stub
		//設定匯入檔案的型態
		return "XLS";
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
	/**
	 * 取得休假類別
	 * @param conn
	 * @param returnMap
	 * @param compId
	 */
	private void getVacation(Connection conn, Map returnMap, String compId) {
		// TODO Auto-generated method stub
		ArrayList list = new ArrayList();
		String sql = "" +
		" SELECT * " +
		" FROM EHF000500T1 " +
		" WHERE 1=1 " +
		" AND EHF000500T1_08 = '"+compId+"' " ;
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				list = new ArrayList();
				list.add((String)rs.getString("EHF000500T1_01"));
				list.add((String)rs.getString("EHF000500T1_03"));
				returnMap.put((String)rs.getString("EHF000500T1_02"), list);
			
			
			}
			rs.close();
			stmt.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
	
}