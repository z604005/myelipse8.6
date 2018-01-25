package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts.action.ActionMessage;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.vacation.tools.EMS_CaculateVacationByCal;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.utils.util.BA_TOOLS;

import jxl.Sheet;
import jxl.Workbook;

/**
 * <Abstract Action> EMS 請假單匯入元件

 */
public class IMP_EHF020200 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private AuthorizedBean authbean = null;
	private String user_emp_id = "";
	private String user_dept_id = "";
	private Map empDepInf = null;
	private Map depMap = null;
	private Map empMap = null;
	
	public IMP_EHF020200( String user_emp_id, String user_dept_id, AuthorizedBean authbean, Map depMap, Map empMap ) { 
			
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
			for( int i=3; i<st.getRows(); i++ ){
				if("".equals(st.getCell( 0, i).getContents().trim())&&"".equals(st.getCell( 1, i).getContents().trim())
						 &&"".equals(st.getCell( 2, i).getContents().trim())&&"".equals(st.getCell( 3, i).getContents().trim())
						 &&"".equals(st.getCell( 4, i).getContents().trim())&&"".equals(st.getCell( 5, i).getContents().trim())
						 &&"".equals(st.getCell( 6, i).getContents().trim())&&"".equals(st.getCell( 7, i).getContents().trim())
						 &&"".equals(st.getCell( 8, i).getContents().trim())&&"".equals(st.getCell( 9, i).getContents().trim())
						 &&"".equals(st.getCell(10, i).getContents().trim())&&"".equals(st.getCell(11, i).getContents().trim())
						 &&"".equals(st.getCell(12, i).getContents().trim())&&"".equals(st.getCell(13, i).getContents().trim())
						 &&"".equals(st.getCell(14, i).getContents().trim())){
							continue;
						}
				dataMap = new HashMap<String,String>();
				dataMap.put("EHF020200T0_04", 		st.getCell( 0, i).getContents());  //部門代號
				dataMap.put("EHF020200T0_03", 		st.getCell( 1, i).getContents());  //員工工號
				dataMap.put("EHF020200T0_03_NAME", 	st.getCell( 2, i).getContents());  //員工姓名
				dataMap.put("EHF020200T0_14", 		st.getCell( 3, i).getContents());  //假別名稱
				dataMap.put("EHF020200T0_09_year", 	st.getCell( 4, i).getContents());  //請假日期(起)年
				dataMap.put("EHF020200T0_09_month", st.getCell( 5, i).getContents());  //請假日期(起)月
				dataMap.put("EHF020200T0_09_day", 	st.getCell( 6, i).getContents());  //請假日期(起)日
				dataMap.put("EHF020200T0_11_HH", 	st.getCell( 7, i).getContents());  //請假(起)(時)
				dataMap.put("EHF020200T0_11_MM",	st.getCell( 8, i).getContents());  //請假(起)(分)
				dataMap.put("EHF020200T0_10_year", 	st.getCell( 9, i).getContents());  //請假日期(迄)
				dataMap.put("EHF020200T0_10_month", st.getCell(10, i).getContents());  //請假日期(迄)
				dataMap.put("EHF020200T0_10_day", 	st.getCell(11, i).getContents());  //請假日期(迄)
				dataMap.put("EHF020200T0_12_HH", 	st.getCell(12, i).getContents());  //請假(迄)(時)
				dataMap.put("EHF020200T0_12_MM", 	st.getCell(13, i).getContents());  //請假(迄)(分)
				dataMap.put("EHF020200T0_15", 		st.getCell(14, i).getContents());  //事由
				
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
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
		Map msgMap = new HashMap();
		Map dataMap = null;
		
		try{
			//建立請假單元件
			//EMS_VacationFlow ems_va_tools = EMS_VacationFlow.getInstance();
			//建立請假單檢核元件
			EMS_CaculateVacationByCal cacuva_tools = new EMS_CaculateVacationByCal();
			
			String UID = "";
			int indexid = 1;
			//請假單流程flow
			//String flow_no = ems_tools.getSysParam(conn, comp_id, "Vacation_FLOW_NO");
			//請假單流程form
			//String form_no = ems_tools.getSysParam(conn, comp_id, "Vacation_FORM_NO");
			//填單日期
			String cur_date = tools.ymdTostring(tools.getSysDate());
			//匯入筆數
			int dataCount = 0;
			
			//新增請假單
			String sql = "" +
			" INSERT INTO ehf020200t0 (EHF020200T0_01, EHF020200T0_02, EHF020200T0_03, EHF020200T0_04, EHF020200T0_05," +
			" EHF020200T0_06, EHF020200T0_07, EHF020200T0_08, EHF020200T0_09, EHF020200T0_10, EHF020200T0_11, EHF020200T0_12, " +
			" EHF020200T0_13, EHF020200T0_14, EHF020200T0_15, EHF020200T0_16, EHF020200T0_16_DESC, EHF020200T0_18, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " + 
			" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, NOW()) ";


			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				String dep_id = "";
				String emp_id = "";
				//取得資料
				dataMap = (Map) it.next();

				String yera_month_day_start=
					(String)dataMap.get("EHF020200T0_09_year")+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_09_month")))+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_09_day")));
				String yera_month_day_end  =
					(String)dataMap.get("EHF020200T0_10_year")+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_10_month")))+"/"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_10_day")));
				
				//轉化代碼為系統內碼
				dep_id = tools.ShowIdtoRealId(conn, "EHF000200T0",(String)dataMap.get("EHF020200T0_04"), authbean.getCompId()); 
				emp_id = tools.ShowIdtoRealId(conn, "EHF010100T0",(String)dataMap.get("EHF020200T0_03"), authbean.getCompId());
				
				indexid = 1;
				//建立請假單單號UID
				UID = tools.createEMSUID(conn,"EA", "EHF020200T0", "EHF020200T0_01", comp_id);
				p6stmt.setString(indexid, UID);  //請假單單號
				indexid++;
				p6stmt.setString(indexid, "");  //流程空表單編號
				indexid++;
				p6stmt.setString(indexid, emp_id);  //申請人(員工工號)
				indexid++;
				p6stmt.setString(indexid, dep_id);  //申請人部門組織(代號)
				indexid++;
				p6stmt.setString(indexid, this.user_emp_id);  //填單人(員工工號)
				indexid++;
				p6stmt.setString(indexid, this.user_dept_id);  //填單人部門組織(代號)
				indexid++;
				p6stmt.setString(indexid, "");  //申請人的代理人(員工工號)
				indexid++;
				p6stmt.setString(indexid, cur_date);  //填單日期
				indexid++;
				p6stmt.setString(indexid, yera_month_day_start);  //請假日期(起)
				indexid++;
				p6stmt.setString(indexid, yera_month_day_end  );  //請假日期(迄)
				indexid++;
				p6stmt.setString(indexid, String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_11_HH")))
						+ String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_11_MM"))));  //請假時間(時/分)(起)
				indexid++;
				p6stmt.setString(indexid, String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_12_HH")))
						+ String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_12_MM"))));  //請假時間(時/分)(迄)
				indexid++;
				//設定 employee authbean
				AuthorizedBean emp_authbean = new AuthorizedBean();
				emp_authbean.setEmployeeID(emp_id);
				emp_authbean.setCompId(authbean.getCompId());
				emp_authbean.setUserId(authbean.getUserId());
				emp_authbean.setUserCode(authbean.getUserCode());
				
				//計算請假天數與時數
				/*
				EMS_CaculateVacation cacuva_tools = (EMS_CaculateVacation) EMS_CaculateVacation.getInstance();
				Map result = cacuva_tools.caculateRealVa(conn, emp_authbean, (String)dataMap.get("EHF020200T0_09"), (String)dataMap.get("EHF020200T0_10"),
						     (String)dataMap.get("EHF020200T0_11_HH")+(String)dataMap.get("EHF020200T0_11_MM"), 
						     (String)dataMap.get("EHF020200T0_12_HH")+(String)dataMap.get("EHF020200T0_12_MM"));
				*/
				
				
				//在檢核請假時間時，多加一個參數來檢查假別是否計算假日的檢核。 20140614 by Hedwig
//				Map result = cacuva_tools.caculateRealVa( conn, emp_authbean, 
//						yera_month_day_start, yera_month_day_end,
//						 (String)dataMap.get("EHF020200T0_11_HH")+(String)dataMap.get("EHF020200T0_11_MM"), 
//						 (String)dataMap.get("EHF020200T0_12_HH")+(String)dataMap.get("EHF020200T0_12_MM"));
				Map result = cacuva_tools.caculateRealVa( conn, emp_authbean, (String)dataMap.get("EHF020200T0_14"),
						yera_month_day_start, yera_month_day_end,
						String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_11_HH")))
						+ String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_11_MM"))), 
						String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_12_HH")))
						+ String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_12_MM"))));
				
				p6stmt.setString(indexid, ((String)result.get("DAYS"))+"/"+((String)result.get("HOURS")));  //請假天數&時數
				indexid++;
				p6stmt.setString(indexid, this.getVaTypeByVaName(conn, (String)dataMap.get("EHF020200T0_14"), comp_id));  //假別代號
				indexid++;
				p6stmt.setString(indexid, (String)dataMap.get("EHF020200T0_15"));  //事由
				indexid++;
				p6stmt.setString(indexid, "0006");  //表單狀態 -- 請假單 --> 完成
				indexid++;
				p6stmt.setString(indexid, "完成");  //表單狀態 -- 請假單 --> 完成
				indexid++;
				p6stmt.setString(indexid, comp_id);  //公司代碼
				indexid++;
				p6stmt.setString(indexid, owner);  //建立人員
				indexid++;
				p6stmt.setString(indexid, owner);  //修改人員
				indexid++;
				
				p6stmt.executeUpdate();
				
				//建立匯入結單LOG
				//ems_va_tools.updateFormData(conn, this.authbean, flow_no, ems_tools.getImportFormLogList(this.user_emp_id), UID, "");
				setVaSql.updataVacationData(conn, UID, comp_id);
				
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
	 * 檢查是否有重複匯入資料
	 */
	protected Map chkImportDataList(Connection conn, List datalist,String comp_id) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EMS_CheckVacation checkva_tools = (EMS_CheckVacation) EMS_CheckVacation.getInstance();
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
			String start="";
			String end="";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			boolean check_Exist=false;
			String chkname="";
			
			
			while(it.hasNext()){
				
				String dep_id = "";
				String emp_id = "";
				check_Exist=false;
				
				dataMap = (Map) it.next();
				String yera_month_day_start=(String)dataMap.get("EHF020200T0_09_year")+"/"+(String)dataMap.get("EHF020200T0_09_month")+"/"+(String)dataMap.get("EHF020200T0_09_day");
				String yera_month_day_end  =(String)dataMap.get("EHF020200T0_10_year")+"/"+(String)dataMap.get("EHF020200T0_10_month")+"/"+(String)dataMap.get("EHF020200T0_10_day");
				
				if("".equals((String)dataMap.get("EHF020200T0_03")) && (String)dataMap.get("EHF020200T0_03") == null
				   && "".equals((String)dataMap.get("EHF020200T0_04")) && (String)dataMap.get("EHF020200T0_04") == null
				   && "".equals((String)dataMap.get("EHF020200T0_14")) && (String)dataMap.get("EHF020200T0_14") == null
				   && "".equals((String)dataMap.get("EHF020200T0_09_year")) && (String)dataMap.get("EHF020200T0_09_year") == null
				   && "".equals((String)dataMap.get("EHF020200T0_09_month")) && (String)dataMap.get("EHF020200T0_09_month") == null
				   && "".equals((String)dataMap.get("EHF020200T0_09_day")) && (String)dataMap.get("EHF020200T0_09_day") == null
				   && "".equals((String)dataMap.get("EHF020200T0_10_year")) && (String)dataMap.get("EHF020200T0_10_year") == null
				   && "".equals((String)dataMap.get("EHF020200T0_10_month")) && (String)dataMap.get("EHF020200T0_10_month") == null
				   && "".equals((String)dataMap.get("EHF020200T0_10_day")) && (String)dataMap.get("EHF020200T0_10_day") == null
				   && "".equals((String)dataMap.get("EHF020200T0_11_HH")) && (String)dataMap.get("EHF020200T0_11_HH") == null
				   && "".equals((String)dataMap.get("EHF020200T0_11_MM")) && (String)dataMap.get("EHF020200T0_11_MM") == null
				   && "".equals((String)dataMap.get("EHF020200T0_12_HH")) && (String)dataMap.get("EHF020200T0_12_HH") == null
				   && "".equals((String)dataMap.get("EHF020200T0_12_MM")) && (String)dataMap.get("EHF020200T0_12_MM") == null){
					//請假單匯入資料格式不正確, 不可匯入
					//error_list.add(dataMap);  //將此筆資料移至資料不正確清單
					//it.remove();  //將此筆資料由待匯入清單移除
					//error_count++;
					//check_Exist=true;

					//結束此次迴圈
					continue;
				}
				if("".equals((String)dataMap.get("EHF020200T0_03")) || (String)dataMap.get("EHF020200T0_03") == null
						|| "".equals((String)dataMap.get("EHF020200T0_04")) || (String)dataMap.get("EHF020200T0_04") == null
						|| "".equals((String)dataMap.get("EHF020200T0_14")) || (String)dataMap.get("EHF020200T0_14") == null
						|| "".equals((String)dataMap.get("EHF020200T0_09_year")) || (String)dataMap.get("EHF020200T0_09_year") == null
						|| "".equals((String)dataMap.get("EHF020200T0_09_month")) || (String)dataMap.get("EHF020200T0_09_month") == null
						|| "".equals((String)dataMap.get("EHF020200T0_09_day")) || (String)dataMap.get("EHF020200T0_09_day") == null
						|| "".equals((String)dataMap.get("EHF020200T0_10_year")) || (String)dataMap.get("EHF020200T0_10_year") == null
						|| "".equals((String)dataMap.get("EHF020200T0_10_month")) || (String)dataMap.get("EHF020200T0_10_month") == null
						|| "".equals((String)dataMap.get("EHF020200T0_10_day")) || (String)dataMap.get("EHF020200T0_10_day") == null
						|| "".equals((String)dataMap.get("EHF020200T0_11_HH")) || (String)dataMap.get("EHF020200T0_11_HH") == null
						|| "".equals((String)dataMap.get("EHF020200T0_11_MM")) || (String)dataMap.get("EHF020200T0_11_MM") == null
						|| "".equals((String)dataMap.get("EHF020200T0_12_HH")) || (String)dataMap.get("EHF020200T0_12_HH") == null
						|| "".equals((String)dataMap.get("EHF020200T0_12_MM")) || (String)dataMap.get("EHF020200T0_12_MM") == null){
							//請假單匯入資料格式不正確, 不可匯入
							//error_list.add(dataMap);  //將此筆資料移至資料不正確清單
							//it.remove();  //將此筆資料由待匯入清單移除
							//error_count++;
						if (dataMap.get("error") == null){
							dataMap.put("error", "必填欄位未填寫!請再次確認!" + "<br>");//20131016修改   BY 賴泓錡
					    	} else {
					    		dataMap.put("error", dataMap.get("error").toString()+ " " + "必填欄位未填寫!請再次確認!" + "<br>");
					    	}
							//check_Exist=true;

							error_list.add(dataMap); // 將此筆資料移至資料不正確清單
							it.remove(); // 將此筆資料由待匯入清單移除
							error_count++;
							// 結束此次迴圈
							continue;
						}
				
				//不足補0  addZeroForNum

				dataMap.put("EHF020200T0_09_month",	this.addZeroForNum((String)dataMap.get("EHF020200T0_09_month"), 2));
				dataMap.put("EHF020200T0_09_day",	this.addZeroForNum((String)dataMap.get("EHF020200T0_09_day"), 2));
				dataMap.put("EHF020200T0_10_day",	this.addZeroForNum((String)dataMap.get("EHF020200T0_10_day"), 2));
				dataMap.put("EHF020200T0_11_HH",	this.addZeroForNum((String)dataMap.get("EHF020200T0_11_HH"), 2));
				dataMap.put("EHF020200T0_11_MM",	this.addZeroForNum((String)dataMap.get("EHF020200T0_11_MM"), 2));
				dataMap.put("EHF020200T0_12_HH",	this.addZeroForNum((String)dataMap.get("EHF020200T0_12_HH"), 2));
				dataMap.put("EHF020200T0_12_MM",	this.addZeroForNum((String)dataMap.get("EHF020200T0_12_MM"), 2));
				
				
				
				//分割字串
				String[] month_end_date_start = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(
						(String)dataMap.get("EHF020200T0_09_year")+"/"+(String)dataMap.get("EHF020200T0_09_month")+"/"+(String)dataMap.get("EHF020200T0_09_day")))).split("/");
				String[] month_end_date_end   = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(
						(String)dataMap.get("EHF020200T0_10_year")+"/"+(String)dataMap.get("EHF020200T0_10_month")+"/"+(String)dataMap.get("EHF020200T0_10_day")))).split("/");
				
				//轉化代碼為系統內碼
				dep_id = tools.ShowIdtoRealId(conn, "EHF000200T0",(String)dataMap.get("EHF020200T0_04"), authbean.getCompId()); 
				emp_id = tools.ShowIdtoRealId(conn, "EHF010100T0",(String)dataMap.get("EHF020200T0_03"), authbean.getCompId());

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
						dataMap.put("error", "員工工號錯誤!請再次確認!" + "<br>");//20131016修改   BY 賴泓錡
					    } else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "員工工號錯誤!請再次確認!" + "<br>");
					}
					check_Exist=true;
					chkname+="NO";
				}
				
				
				if(!check_Exist){
					//確認員工姓名
					if(!((Map)(empMap.get(emp_id))).get("EMPLOYEE_NAME").equals(dataMap.get("EHF020200T0_03_NAME"))){
						if (dataMap.get("error") == null){
							dataMap.put("error", "員工姓名錯誤!請再次確認!" + "<br>");//20131016修改   BY 賴泓錡
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
							dataMap.put("error", "部門資料未設定，不檢核員工姓名!"+"<br>");//20131016修改   BY 賴泓錡
						    } else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "部門資料未設定，不檢核員工姓名!" + "<br>");
						}
						check_Exist=true;
					}
					//檢核員工工號
					if(chkname.equals("NO")){
						if (dataMap.get("error") == null){
							dataMap.put("error", "員工工號錯誤，不檢核員工姓名!"+"<br>");//20131016修改   BY 賴泓錡
						    } else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "員工工號錯誤，不檢核員工姓名!" + "<br>");
						}
						check_Exist=true;
					}
					
					//檢核部門是在系統內+員工工號
					if(chkname.equals("YESNO")){
						if (dataMap.get("error") == null){
							dataMap.put("error", "部門資料與員工工號錯誤，不檢核員工姓名!"+"<br>");//20131016修改   BY 賴泓錡
						    } else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "部門資料與員工工號錯誤，不檢核員工姓名!" + "<br>");
						}
						check_Exist=true;
					}
				}
				
				String sql_Vacation = "" 
					+ " SELECT * FROM EHF020100T0 WHERE 1=1 "
					+ " AND EHF020100T0_03 = '" + this.getVaTypeByVaName(conn, (String)dataMap.get("EHF020200T0_14"), comp_id) + "' "
					+ " AND EHF020100T0_08 = '" + authbean.getCompId() + "' ";
				boolean sql_Vacation_Exist=this.check_Exist(conn, sql_Vacation);
				
				
				//檢核假別是否在系統內
				if(!sql_Vacation_Exist){
					if (dataMap.get("error") == null){
						dataMap.put("error", "假別不再系統內!請再次確認!"+"<br>");//20131016修改   BY 賴泓錡
					    } else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "假別不再系統內!請再次確認!" + "<br>");
					}
					check_Exist=true;
				}
				
				//驗證"使用日期" '年 '  長度不能大於4位數
				if(((String)dataMap.get("EHF020200T0_09_year")).length()!=4||((String)dataMap.get("EHF020200T0_10_year")).length()!=4){
					if (dataMap.get("error") == null){
						dataMap.put("error", "使用日期  '年 ' 長度大於4!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					    } else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "使用日期  '年 ' 長度大於4!請再次確認" + "<br>");
					}
					check_Exist=true;
					//System.out.println("不得大於13");
				}
				
				//驗證"使用日期" 月份不能大於12
				if(Integer.parseInt((String)dataMap.get("EHF020200T0_09_month"))>=13||Integer.parseInt((String)dataMap.get("EHF020200T0_10_month"))>=13){
					if (dataMap.get("error") == null){
						dataMap.put("error", "使用日期'月份 ' 不能大於12!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					    } else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "使用日期'月份 ' 不能大於12!請再次確認" + "<br>");
					}
					check_Exist=true;
					//System.out.println("月份不得大於13");
				}
				
				//驗證"使用日期" 日期不能大於當月最後一天
				if(Integer.parseInt((String)dataMap.get("EHF020200T0_09_day"))>Integer.parseInt(month_end_date_start[2])||Integer.parseInt((String)dataMap.get("EHF020200T0_10_day"))>Integer.parseInt(month_end_date_end[2])){
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "使用日期'日期 ' 不能大於當月最後一天!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					    } else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "使用日期'日期 ' 不能大於當月最後一天!請再次確認" + "<br>");
					}
					check_Exist=true;
					//System.out.println("日期不得大於最後一天");
				}
				
				//驗證請假"時"是否大於24
				if(Integer.parseInt((String)dataMap.get("EHF020200T0_11_HH"))>=25||Integer.parseInt((String)dataMap.get("EHF020200T0_12_HH"))>=25){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "使用日期'時間 ' 不能大於24!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					    } else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "使用日期'時間 ' 不能大於24!請再次確認" + "<br>");
					}
					check_Exist=true;
					//System.out.println("請假 '(時)'大於12");
				}
				
				//驗證請假"分"是否大於60
				if(Integer.parseInt((String)dataMap.get("EHF020200T0_11_MM"))>=61||Integer.parseInt((String)dataMap.get("EHF020200T0_12_MM"))>=61){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "使用日期'分鐘' 不能大於60!請再次確認");//20131016修改   BY 賴泓錡
					    } else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "使用日期'分鐘' 不能大於60!請再次確認" + "<br>");
					}
					check_Exist=true;
					//System.out.println("請假 '(時)'大於12");
				}
				
				
				//驗證事由是否大於100個字
				if(((String)dataMap.get("EHF020200T0_15")).length()>=101){	
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "事由不能大於100個字!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
					    } else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "事由不能大於100個字!請再次確認" + "<br>");
					}
					check_Exist=true;
					//System.out.println("請假 '(時)'大於12");
				}

				/*
				try {
					//檢核，請假單申請時，是否有設定好排班表
					
				
					start=(String)dataMap.get("EHF020200T0_09_year")+"/" +
					      (String)dataMap.get("EHF020200T0_09_month")+"/" +
					      (String)dataMap.get("EHF020200T0_09_day")+" "+"00:00:00" ;
						   
					  end=(String)dataMap.get("EHF020200T0_10_year")+"/"+
					      (String)dataMap.get("EHF020200T0_10_month")+"/"+
					      (String)dataMap.get("EHF020200T0_10_day")+" "+"00:00:00" ;
					
					
					
					
					Calendar CHK_start_cal =tools.covertStringToCalendar(start+" 00:00:00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
					Calendar CHK_end_cal   = tools.covertStringToCalendar(end+" 00:00:00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
					
					boolean chk_run_flag = true;
					boolean chk_flag = true;
					
					while(chk_run_flag){
						if(CHK_start_cal.equals(CHK_end_cal)){
							chk_run_flag = false;
						}
						String strdate = null;

						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

						if (CHK_start_cal != null) {
							strdate = sdf.format(CHK_start_cal.getTime());
						}
						sql = "" +
				           " SELECT *" +
						   " FROM EHF010105T0" +
						   " LEFT JOIN" +
						   " EHF010105T1 ON EHF010105T0_01 = EHF010105T1_01 AND EHF010105T0_06 = EHF010105T1_06	WHERE " +
						   " 1 = 1 " +
						   " AND EHF010105T0_02 = '"+(String)dataMap.get("EHF020200T0_03")+"'" +
						   " AND EHF010105T1_02 = '"+strdate+"'" +
						   " AND EHF010105T0_06 = '"+authbean.getCompId() +"'" ;
						Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
						ResultSet rs_01 = stmt_01.executeQuery(sql);
					
						while(!rs_01.next()&&chk_flag){
							chk_run_flag = false;
							if (dataMap.get("error") == null){
								dataMap.put("error", "排班表未設定，請檢查!!"+"<br>");//20131016修改   BY 賴泓錡
							    } else {
								dataMap.put("error", dataMap.get("error").toString()+ " " + "排班表未設定，請檢查!!" + "<br>");
							}
							chk_flag= false;
							check_Exist=true;
						}
						rs_01.close();
						stmt_01.close();
						
						
						
						CHK_start_cal.add(Calendar.DAY_OF_MONTH, 1);
					}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				*/
				
				if(!"".equals(this.getVaTypeByVaName(conn, (String)dataMap.get("EHF020200T0_14"), comp_id))){
					//有價別代碼，才需要檢查是否可以申請與請假日期是否連續。
					
					
				//EMS1.1.0新增檢核此假別是否可以申請。 20140725 by Hedwig
				Map conflict_map = checkva_tools.checkIsAskForLeaveForm(conn, 
								 emp_id, 
								(String)dataMap.get("EHF020200T0_09_year")+
								(String)dataMap.get("EHF020200T0_09_month")+
								(String)dataMap.get("EHF020200T0_09_day"), 
								this.getVaTypeByVaName(conn, (String)dataMap.get("EHF020200T0_14"), comp_id), comp_id);
				if((Boolean)conflict_map.get("CHK_FLAG")){
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "此員工無法申請"+conflict_map.get("LeaveName")+"!請再次確認"+"<br>");
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "此員工無法申請"+conflict_map.get("LeaveName")+"!請再次確認" + "<br>");
					}
					check_Exist=true;
					//System.out.println("檢核此假別是否可以申請");
				}
				
				//EMS1.1.0新增檢核請假日期是否連續。(資料庫) 20140725 by Hedwig
				conflict_map = checkva_tools.checkContinuousDataForm(conn, "", (String)dataMap.get("EHF020200T0_09_year")+"/"+(String)dataMap.get("EHF020200T0_09_month")+"/"+(String)dataMap.get("EHF020200T0_09_day"), 
														(String)dataMap.get("EHF020200T0_10_year")+"/"+(String)dataMap.get("EHF020200T0_10_month")+"/"+(String)dataMap.get("EHF020200T0_10_day") ,this.getVaTypeByVaName(conn, (String)dataMap.get("EHF020200T0_14"), comp_id), 
														emp_id, comp_id);
				if((Boolean)conflict_map.get("CHK_FLAG")){
					if (dataMap.get("error") == null){
						dataMap.put("error", (String)dataMap.get("EHF020200T0_14")+"無法分開請，或是申請日期有誤!請再次確認"+"<br>");
					} else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + (String)dataMap.get("EHF020200T0_14")+"無法分開請，或是申請日期有誤!請再次確認" + "<br>");
					}
					check_Exist=true;
					//System.out.println("檢核請假日期是否連續");
				}

				}
				
				
				
				
				
				
				
				sql = "" +
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
				" FROM EHF020900T0 " +
				" WHERE 1=1 " +
				" AND (EHF020900T0_M2 = '"+(String)dataMap.get("EHF020200T0_09_year")+"/"+(String)dataMap.get("EHF020200T0_09_month")+"' OR "+	//考勤年月
				"      EHF020900T0_M2 = '"+(String)dataMap.get("EHF020200T0_10_year")+"/"+(String)dataMap.get("EHF020200T0_10_month")+"')"+
				" AND EHF020900T0_04 = '"+authbean.getCompId()+"'" +//公司代碼
				" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

		    	Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs_01 = stmt_01.executeQuery(sql);
				
				while(rs_01.next()){
					if("02".equals(rs_01.getString("EHF020900T0_02"))||"03".equals(rs_01.getString("EHF020900T0_02")))
					//error_year+=rs_01.getString("EHF020900T0_M2");
						
						if (dataMap.get("error") == null){
							dataMap.put("error", rs_01.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認"+"<br>");//20131016修改   BY 賴泓錡
						} else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + rs_01.getString("EHF020900T0_M2")+"已經確認考勤!請再次確認" + "<br>");
						}	
					check_Exist=true;
					
				}
				rs_01.close();
				stmt_01.close();
				
				start=(String)dataMap.get("EHF020200T0_09_year")+"/" +
				      (String)dataMap.get("EHF020200T0_09_month")+"/" +
				      (String)dataMap.get("EHF020200T0_09_day")+" " +
				      (String)dataMap.get("EHF020200T0_11_HH")+":" +
				      (String)dataMap.get("EHF020200T0_11_MM")+":00" ;
					   
				  end=(String)dataMap.get("EHF020200T0_10_year")+"/"+
				      (String)dataMap.get("EHF020200T0_10_month")+"/"+
				      (String)dataMap.get("EHF020200T0_10_day")+" "+
				      (String)dataMap.get("EHF020200T0_12_HH")+":" +
				      (String)dataMap.get("EHF020200T0_12_MM")+":00" ;
				
				
				tools.covertStringToCalendar(start, "yyyy/MM/dd HH:mm:ss");
				Calendar start_cal = tools.covertStringToCalendar(start);  //計算開始日期
				Calendar end_cal = tools.covertStringToCalendar(end);  //計算結束日期
				
				if(start_cal.compareTo(end_cal)>0){
					check_Exist = true;
					if (dataMap.get("error") == null)
						dataMap.put("error", "時間錯誤!" + "<br>");
					else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "時間錯誤!" + "<br>");
					}
				}
				
				
				
				
			
				sql = "" +
				" SELECT EHF020200T0_01 " +
				" FROM EHF020200T0 " +
				" WHERE 1=1 " +
				" AND EHF020200T0_03 = '"+emp_id+"' " +  //員工工號
				" AND EHF020200T0_04 = '"+dep_id+"' " +  //部門代號
				" AND EHF020200T0_14 = '"+this.getVaTypeByVaName(conn, (String)dataMap.get("EHF020200T0_14"), comp_id)+"' " +  //假別代號
				" AND DATE_FORMAT(EHF020200T0_09 , '%Y/%m/%d') = '"+yera_month_day_start+"' " +  //請假日期(起)
				" AND DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d')  = '"+yera_month_day_end+"' " +  //請假日期(迄)
				" AND EHF020200T0_11 = '"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_11_HH")))+
				                          String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_11_MM")))+"' " +  //請假時間(起)
				" AND EHF020200T0_12 = '"+String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_12_HH")))+
				                          String.format("%02d",Integer.valueOf((String)dataMap.get("EHF020200T0_12_MM")))+"' " +  //請假時間(迄)
				" AND EHF020200T0_18 = '"+comp_id+"' ";  //公司代碼
				
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
//					dataMap.put("error", "與資料庫資料重複，請再次確認");//20131016修改   BY 賴泓錡
//					//有找到重複資料, 此筆資料不匯入系統
//					ng_list.add(dataMap);  //將此筆資料移至重複不匯入清單
//					it.remove();  //將此筆資料由待匯入清單移除
//					ng_count++;
					
					
					check_Exist = true;
					if (dataMap.get("error") == null)
						dataMap.put("error", "與資料庫資料重複，請再次確認!" + "<br>");
					else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "與資料庫資料重複，請再次確認!" + "<br>");
					}
				}
				
				//請假單匯入資料格式不正確, 不可匯入
				if (check_Exist) {
					error_list.add(dataMap); // 將此筆資料移至資料不正確清單
					it.remove(); // 將此筆資料由待匯入清單移除
					error_count++;
					// 結束此次迴圈
					
				}
			}
			
			if(rs!=null)
			rs.close();
			
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
	 * 取得假別代號
	 * @param conn
	 * @param va_name
	 * @param comp_id
	 * @return
	 */
	protected String getVaTypeByVaName( Connection conn, String va_name, String comp_id ){
		
		String va_type = "";
		
		try{
			String sql = "" +
			" SELECT EHF020100T0_03 " +
			" FROM EHF020100T0 " +
			" WHERE 1=1 " +
			" AND EHF020100T0_04 = '"+va_name+"' " +  //假別名稱
			" AND EHF020100T0_08 = '"+comp_id+"' ";  //公司代碼
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				va_type = rs.getString("EHF020100T0_03");  //假別代號
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return va_type;
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