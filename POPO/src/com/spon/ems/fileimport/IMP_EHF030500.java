package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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

import vtgroup.ems.common.vo.AuthorizedBean;

/**
 *@author maybe
 *@version 1.0
 *@created 2016/11/1 上午10:31:38
 */
public class IMP_EHF030500 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private AuthorizedBean authbean = null;
	private String user_emp_id = "";
	private String user_dept_id = "";
	private Map empDepInf = null;
	private Map depMap = null;
	private Map empMap = null;
	
	public IMP_EHF030500( String user_emp_id, String user_dept_id, AuthorizedBean authbean, Map depMap, Map empMap ) { 
			
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
			
			//第一筆資料為表頭, 不列入匯入資料
			for( int i=2; i<st.getRows(); i++ ){
				if("".equals(st.getCell( 0, i).getContents().trim())&&"".equals(st.getCell( 1, i).getContents().trim())
						 &&"".equals(st.getCell( 2, i).getContents().trim())&&"".equals(st.getCell( 3, i).getContents().trim())
						 &&"".equals(st.getCell( 4, i).getContents().trim())&&"".equals(st.getCell( 5, i).getContents().trim())
						 &&"".equals(st.getCell( 6, i).getContents().trim())&&"".equals(st.getCell( 7, i).getContents().trim())
						 &&"".equals(st.getCell( 8, i).getContents().trim())&&"".equals(st.getCell( 9, i).getContents().trim())
						 &&"".equals(st.getCell(10, i).getContents().trim())){
							continue;
						}
				dataMap = new HashMap<String,String>();
				dataMap.put("EHF030500T0_03", 		st.getCell( 0, i).getContents());  //部門代號
				dataMap.put("EHF030500T0_02", 		st.getCell( 1, i).getContents());  //員工工號
				dataMap.put("EHF030500T0_02_NAME", 	st.getCell( 2, i).getContents());  //員工姓名
				dataMap.put("EHF030500T0_05_year", 	st.getCell( 3, i).getContents());  //薪資年月(西元年)
				dataMap.put("EHF030500T0_05_month", st.getCell( 4, i).getContents());  //薪資年月(月)
				dataMap.put("EHF030500T0_04", 		st.getCell( 5, i).getContents());  //員工類別
				dataMap.put("EHF030500T0_07", 		st.getCell( 6, i).getContents());  //發放類別
				dataMap.put("EHF030500T0_06", 		st.getCell( 7, i).getContents());  //放/扣款類別
				dataMap.put("FEE_NAME",				st.getCell( 8, i).getContents());  //費用名稱
				dataMap.put("FEE", 					st.getCell( 9, i).getContents());  //費用金額
				dataMap.put("EHF030500T0_13", 		st.getCell(10, i).getContents());  //備註
				
				xlsdatalist.add(dataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;
	}

	@Override
	protected Map chkImportDataList(Connection conn, List datalist,
			String compId) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Map dataMap = null;
		Map errMsgMap = null;
		
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
			
			String sql = "";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			boolean check_Exist = false;
			String chkname = "";
			
			while(it.hasNext()){
				
				String dep_id = "";
				String emp_id = "";
				check_Exist = false;
				chkname = "";
				
				dataMap = (Map) it.next();
				
				if("".equals((String)dataMap.get("EHF030500T0_03")) && (String)dataMap.get("EHF030500T0_03") == null 
						&& "".equals((String)dataMap.get("EHF030500T0_02")) && (String)dataMap.get("EHF030500T0_02") == null
						&& "".equals((String)dataMap.get("EHF030500T0_02_NAME")) && (String)dataMap.get("EHF030500T0_02_NAME") == null
						&& "".equals((String)dataMap.get("EHF030500T0_05_year")) && (String)dataMap.get("EHF030500T0_05_year") == null
						&& "".equals((String)dataMap.get("EHF030500T0_05_month")) && (String)dataMap.get("EHF030500T0_05_month") == null
						&& "".equals((String)dataMap.get("EHF030500T0_04")) && (String)dataMap.get("EHF030500T0_04") == null
						&& "".equals((String)dataMap.get("EHF030500T0_07")) && (String)dataMap.get("EHF030500T0_07") == null
						&& "".equals((String)dataMap.get("EHF030500T0_06")) && (String)dataMap.get("EHF030500T0_06") == null
						&& "".equals((String)dataMap.get("FEE_NAME")) && (String)dataMap.get("FEE_NAME") == null
						&& "".equals((String)dataMap.get("FEE")) && (String)dataMap.get("FEE") == null){
					//結束此次迴圈
					continue;
				}
				
				if("".equals((String)dataMap.get("EHF030500T0_03")) || (String)dataMap.get("EHF030500T0_03") == null
						|| "".equals((String)dataMap.get("EHF030500T0_02")) || (String)dataMap.get("EHF030500T0_02") == null
						|| "".equals((String)dataMap.get("EHF030500T0_02_NAME")) || (String)dataMap.get("EHF030500T0_02_NAME") == null
						|| "".equals((String)dataMap.get("EHF030500T0_05_year")) || (String)dataMap.get("EHF030500T0_05_year") == null
						|| "".equals((String)dataMap.get("EHF030500T0_05_month")) || (String)dataMap.get("EHF030500T0_05_month") == null
						|| "".equals((String)dataMap.get("EHF030500T0_04")) || (String)dataMap.get("EHF030500T0_04") == null
						|| "".equals((String)dataMap.get("EHF030500T0_07")) || (String)dataMap.get("EHF030500T0_07") == null
						|| "".equals((String)dataMap.get("EHF030500T0_06")) || (String)dataMap.get("EHF030500T0_06") == null
						|| "".equals((String)dataMap.get("FEE_NAME")) || (String)dataMap.get("FEE_NAME") == null
						|| "".equals((String)dataMap.get("FEE")) || (String)dataMap.get("FEE") == null){
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
				
				//不足補0  addZeroForNum
				dataMap.put("EHF030500T0_05_month",	this.addZeroForNum((String)dataMap.get("EHF030500T0_05_month"), 2));
				
				//轉化代碼為系統內碼
				dep_id = tools.ShowIdtoRealId(conn, "EHF000200T0",(String)dataMap.get("EHF030500T0_03"), authbean.getCompId()); 
				emp_id = tools.ShowIdtoRealId(conn, "EHF010100T0",(String)dataMap.get("EHF030500T0_02"), authbean.getCompId());
				
				//檢核部門是在系統內
				if(depMap.get(dep_id)==null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "部門資料未設定!請再次確認!" + "<br>");//20131016修改   BY 賴泓錡
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "部門資料未設定!請再次確認!" + "<br>");
					}
					check_Exist=true;
					chkname+="YES";
				}
				
				//檢核員工工號
				if( empMap.get(emp_id)==null){
					if (dataMap.get("error") == null){
						dataMap.put("error", "員工工號錯誤!請再次確認!" + "<br>");
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "員工工號錯誤!請再次確認!" + "<br>");
					}
					check_Exist=true;
					chkname+="NO";
				}
				
				if(!check_Exist){
					//確認員工姓名
					if(!((Map)(empMap.get(emp_id))).get("EMPLOYEE_NAME").equals(dataMap.get("EHF030500T0_02_NAME"))){
						if (dataMap.get("error") == null){
							dataMap.put("error", "員工姓名錯誤!請再次確認!" + "<br>");
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "員工姓名錯誤!請再次確認!" + "<br>");
						}
						check_Exist=true;
					}	
				}else{
					//當部門代號與員工工號錯誤時，顯示不檢核員工姓名
					
					//檢核部門是在系統內
					if(chkname.equals("YES")){
						
						if (dataMap.get("error") == null){
							dataMap.put("error", "部門資料未設定，不檢核員工姓名!"+"<br>");
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "部門資料未設定，不檢核員工姓名!" + "<br>");
						}
						check_Exist=true;
					}
					//檢核員工工號
					if(chkname.equals("NO")){
						if (dataMap.get("error") == null){
							dataMap.put("error", "員工工號錯誤，不檢核員工姓名!"+"<br>");
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "員工工號錯誤，不檢核員工姓名!" + "<br>");
						}
						check_Exist=true;
					}
					
					//檢核部門是在系統內+員工工號
					if(chkname.equals("YESNO")){
						if (dataMap.get("error") == null){
							dataMap.put("error", "部門資料與員工工號錯誤，不檢核員工姓名!"+"<br>");
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "部門資料與員工工號錯誤，不檢核員工姓名!" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				//驗證"使用日期" '年 '  長度不能大於4位數
				if(((String)dataMap.get("EHF030500T0_05_year")).length()!=4){
					if (dataMap.get("error") == null){
						dataMap.put("error", "薪資年月  '年' 長度大於4!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "薪資年月  '年' 長度大於4!請再次確認" + "<br>");
					}
					check_Exist=true;					
				}
				
				//驗證"使用日期" 月份不能大於12
				if(Integer.parseInt((String)dataMap.get("EHF030500T0_05_month"))>=13){
					if (dataMap.get("error") == null){
						dataMap.put("error", "薪資年月 '月份' 不能大於12!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "薪資年月 '月份' 不能大於12!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證費用名稱是否大於20個字
				if(((String)dataMap.get("FEE_NAME")).length()>=21){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "費用名稱不能大於20個字!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "費用名稱不能大於20個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證備註是否大於50個字
				if(((String)dataMap.get("EHF030500T0_13")).length()>=51){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "備註不能大於50個字!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "備註不能大於50個字!請再次確認" + "<br>");
					}
					check_Exist=true;
				}
				
				sql = " SELECT EHF030600T0_01, EHF030600T0_U, EHF030600T0_M, EHF030600T0_02, EHF030600T0_SCP " +
				" FROM EHF030600T0 " +
				" WHERE 1=1 " +
				" AND EHF030600T0_02 = '"+(String)dataMap.get("EHF030500T0_05_year")+"/"+(String)dataMap.get("EHF030500T0_05_month")+"' "+	//薪資年月
				" AND EHF030600T0_14 = '"+authbean.getCompId()+"'" +//公司代碼
				" AND (EHF030600T0_SCP = '03' OR EHF030600T0_SCP = '04' OR EHF030600T0_SCP = '05')"; //薪資處理狀態 >= '03'
				
				Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs_01 = stmt_01.executeQuery(sql);
				
				if(rs_01.next()){
					if (dataMap.get("error") == null){
						dataMap.put("error", rs_01.getString("EHF030600T0_02")+"已經確認薪資!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + rs_01.getString("EHF030600T0_02")+"已經確認薪資!請再次確認" + "<br>");
					}	
					check_Exist=true;
				}
				
				rs_01.close();
				stmt_01.close();
				
				//請假單匯入資料格式不正確, 不可匯入
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
		
		try{
			String UID = "";
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;
			
			//新增表頭資訊
			String sql = "" +
			" INSERT INTO EHF030500t0 ( EHF030500T0_U, EHF030500T0_02, EHF030500T0_03, " +
			" EHF030500T0_04, EHF030500T0_05, EHF030500T0_06, EHF030500T0_07, EHF030500T0_08, EHF030500T0_09, " +
			" EHF030500T0_10, EHF030500T0_11, EHF030500T0_12, EHF030500T0_13, EHF030500T0_14, EHF030500T0_15, " +
			" EHF030500T0_SCU, EHF030500T0_SCP, " +
			" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?, NOW(),?,?,?,?,?, 1, NOW() ) "  ;
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				String dep_id = "";
				String emp_id = "";
				//取得資料
				dataMap = (Map) it.next();
				
				String SalaryMonth = 
					(String)dataMap.get("EHF030500T0_05_year")+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF030500T0_05_month")));
				
				//轉化代碼為系統內碼
				dep_id = tools.ShowIdtoRealId(conn, "EHF000200T0",(String)dataMap.get("EHF030500T0_03"), authbean.getCompId()); 
				emp_id = tools.ShowIdtoRealId(conn, "EHF010100T0",(String)dataMap.get("EHF030500T0_02"), authbean.getCompId());
				
				indexid = 1;
				//建立請假單單號UID
				UID = tools.createEMSUID(conn, "EMP", "EHF030500T0", "EHF030500T0_01", comp_id);
				p6stmt.setString(indexid, UID);  //其他費用UID
				indexid++;
				p6stmt.setString(indexid, emp_id);  //員工工號
				indexid++;
				p6stmt.setString(indexid, dep_id);  //部門組織 (代號)
				indexid++;
				if("正式員工".equals((String)dataMap.get("EHF030500T0_04"))){
					p6stmt.setString(indexid, "01");  //員工類別
					indexid++;
				}else if("臨時員工".equals((String)dataMap.get("EHF030500T0_04"))){
					p6stmt.setString(indexid, "02");  //員工類別
					indexid++;
				}else{
					p6stmt.setString(indexid, "01");  //員工類別
					indexid++;
				}
				p6stmt.setString(indexid, SalaryMonth);  //薪資年月
				indexid++;
				if("放款".equals((String)dataMap.get("EHF030500T0_06"))){//放
					p6stmt.setString(indexid, "01");  //放扣款類別
					indexid++;
				}else if("扣款".equals((String)dataMap.get("EHF030500T0_06"))){//扣
					p6stmt.setString(indexid, "02");  //放扣款類別
					indexid++;
				}
				if("薪資".equals((String)dataMap.get("EHF030500T0_07"))){
					p6stmt.setString(indexid, "01");  //發放類別
					indexid++;
				}else if("獎金".equals((String)dataMap.get("EHF030500T0_07"))){
					p6stmt.setString(indexid, "02");  //發放類別
					indexid++;
				}else if("臨時".equals((String)dataMap.get("EHF030500T0_07"))){
					p6stmt.setString(indexid, "03");  //發放類別
					indexid++;
				}else{
					p6stmt.setString(indexid, "01");  //發放類別
					indexid++;
				}
				if("放款".equals((String)dataMap.get("EHF030500T0_06"))){
					p6stmt.setString(indexid, (String)dataMap.get("FEE_NAME"));  //放款項目
					indexid++;
					p6stmt.setInt(indexid, Integer.parseInt((String)dataMap.get("FEE")));  //放款金額
					indexid++;
					p6stmt.setString(indexid, "");  //扣款項目
					indexid++;
					p6stmt.setInt(indexid, 0);  //扣款金額
					indexid++;
				}else if("扣款".equals((String)dataMap.get("EHF030500T0_06"))){
					p6stmt.setString(indexid, "");  //放款項目
					indexid++;
					p6stmt.setInt(indexid, 0);  //放款金額
					indexid++;
					p6stmt.setString(indexid, (String)dataMap.get("FEE_NAME"));  //扣款項目
					indexid++;
					p6stmt.setInt(indexid, Integer.parseInt((String)dataMap.get("FEE")));  //扣款金額
					indexid++;
				}
				p6stmt.setString(indexid, "");  //入帳狀態
				indexid++;
				p6stmt.setString(indexid, (String)dataMap.get("EHF030500T0_13"));  //備註
				indexid++;
				p6stmt.setString(indexid, comp_id);  //公司代碼
				indexid++;
				p6stmt.setString(indexid, "");  //薪資計算UID
				indexid++;
				p6stmt.setString(indexid, "01");  //薪資計算處理狀態
				indexid++;
				p6stmt.setString(indexid, owner);  //建立人員
				indexid++;
				p6stmt.setString(indexid, owner);  //修改人員
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

}
