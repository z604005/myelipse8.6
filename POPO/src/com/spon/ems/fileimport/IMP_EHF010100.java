package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;

import com.spon.ems.hr.models.EHF010100;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

import vtgroup.ems.common.vo.AuthorizedBean;

/**
 * 員工基本資料資料匯入作業 所用元件
 *@author maybe
 *@version 1.0
 *@created 2015/9/22 上午10:56:58
 */
public class IMP_EHF010100 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private AuthorizedBean authbean = null;
	private String user_emp_id = "";
	//private String user_dept_id = "";
	//private Map empDepInf = null;
	private Map depMap = null;
	private Map empMap = null;
	private Map titleNameMap = null;
	private HttpServletRequest request= null;
	//取得驗證工具
	private IMP_Verification Verification=null;
	
	public IMP_EHF010100( String user_emp_id, AuthorizedBean authbean, HttpServletRequest request, Map depMap, Map empMap, Map titleNameMap ) {
		
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;
		//this.user_dept_id = user_dept_id;
		this.authbean = authbean;
		//this.empDepInf = empDepInf;//取得員工的主要歸屬部門代號
		this.depMap = depMap;// 取得部門資訊
		this.empMap = empMap;// 取得員工資訊
		this.titleNameMap = titleNameMap;
		this.request= request;
		this.Verification = new IMP_Verification(request, user_emp_id, authbean);
		
	}
	
	/**
	 * 取得匯入的所有資料
	 */
	@Override
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
						 &&"".equals(st.getCell(12, i).getContents().trim())/*&&"".equals(st.getCell(13, i).getContents().trim())*/
						 &&"".equals(st.getCell(14, i).getContents().trim())&&"".equals(st.getCell(15, i).getContents().trim())
						 &&"".equals(st.getCell(16, i).getContents().trim())&&"".equals(st.getCell(17, i).getContents().trim())
						 &&"".equals(st.getCell(18, i).getContents().trim())&&"".equals(st.getCell(19, i).getContents().trim())
						 &&"".equals(st.getCell(20, i).getContents().trim())/*&&"".equals(st.getCell(21, i).getContents().trim())*/
						 &&"".equals(st.getCell(22, i).getContents().trim())&&"".equals(st.getCell(23, i).getContents().trim())){
					continue;
				}
				
				dataMap = new HashMap();
					
				dataMap.put("EHF010100T0_02", 		st.getCell( 0, i).getContents());	//員工工號
				dataMap.put("EHF010100T0_05", 		st.getCell( 1, i).getContents());  	//姓名(中)
				dataMap.put("EHF010100T0_11", 		st.getCell( 2, i).getContents());	//生日
				dataMap.put("EHF010100T0_08", 		st.getCell( 3, i).getContents());	//性別
				dataMap.put("EHF010100T0_I_TYPE",  	st.getCell( 4, i).getContents());	//身分證字號類型
				dataMap.put("EHF010100T0_I",  		st.getCell( 5, i).getContents());	//身分證字號
				dataMap.put("EHF010100T0_07", 		st.getCell( 6, i).getContents());	//員工類別
				dataMap.put("EHF010100T1_03", 		st.getCell( 7, i).getContents());	//到職日
				dataMap.put("EHF010100T0_14_N", 	st.getCell( 8, i).getContents());	//聯絡電話(區碼)	
				dataMap.put("EHF010100T0_14", 		st.getCell( 9, i).getContents());	//聯絡電話(號碼)
				dataMap.put("EHF010100T0_16", 		st.getCell(10, i).getContents());	//手機號碼
				dataMap.put("EHF010100T0_13_NUM", 	st.getCell(11, i).getContents());	//郵遞區號
				dataMap.put("EHF010100T0_13", 		st.getCell(12, i).getContents());	//戶籍地址
//				dataMap.put("EHF010100T0_31", 		st.getCell(13, i).getContents());	//電子郵件帳號
				dataMap.put("EHF010100T6_02", 		st.getCell(14, i).getContents());	//部門系統代碼
				dataMap.put("EHF010100T6_03", 		st.getCell(15, i).getContents());	//部門歸屬區間(起)
				dataMap.put("EHF010100T6_04", 		st.getCell(16, i).getContents());	//部門歸屬區間(迄)
				dataMap.put("EHF010100T6_05", 		st.getCell(17, i).getContents());	//主要歸屬部門
				dataMap.put("EHF010100T6_06", 		st.getCell(18, i).getContents());	//職務系統代碼
				dataMap.put("EHF010100T6_07", 		st.getCell(19, i).getContents());	//是否為主管
				dataMap.put("EHF010100T6_08", 		st.getCell(20, i).getContents());	//級等
//				dataMap.put("EHF010100T0_32", 		st.getCell(21, i).getContents());	//教/職員
				dataMap.put("EHF010100T8_04", 		st.getCell(22, i).getContents());	//班別代號
				dataMap.put("EHF020403T1_02", 		st.getCell(23, i).getContents());	//感應卡號
				
				xlsdatalist.add(dataMap);
			}						
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;
	
		
		/*
		List<Map<String,String>> xlsdatalist = new ArrayList<Map<String,String>>();
		Map dataMap = null;
		BA_TOOLS tool = BA_TOOLS.getInstance();
		try{
			//取得第一個Sheet資料
			Sheet st = wbk.getSheet(0);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
//			Map depMap = hr_tools.getDep("COM89883265");
//			Map CMap = hr_tools.getC("COM89883265");
			Map depMap = hr_tools.getDep("COM23405257");
			Map CMap = hr_tools.getC("COM23405257");
			//第一筆資料為表頭, 不列入匯入資料
			for( int i=2; i<st.getRows(); i++ ){
				
					dataMap = new HashMap();

					String HR_EmployeeSysNo = tool.createEMSUID(conn, "EMP", "EHF010100T0", "EHF010100T0_01", comp_id);
					//dataMap.put("EHF010100T0_01", new String("EMP201509010")+String.format("%03d",i+1));	//員工系統代碼
					dataMap.put("EHF010100T0_01", HR_EmployeeSysNo);	//員工系統代碼
					dataMap.put("EHF010100T0_02", st.getCell( 3, i).getContents());	//員工工號
					dataMap.put("EHF010100T0_I",  st.getCell( 10, i).getContents());	//身分證字號
					dataMap.put("EHF010100T0_03", st.getCell( 12, i).getContents());	//護照號碼 
					dataMap.put("EHF010100T0_04", st.getCell( 11, i).getContents());	//居留證號碼
					dataMap.put("EHF010100T0_05", st.getCell( 2, i).getContents());	//姓名(中)
					dataMap.put("EHF010100T0_06", "");	//姓名(英)
					dataMap.put("EHF010100T0_07", "1");	//員工類別
					dataMap.put("EHF010100T0_08", "");	//性別
					dataMap.put("EHF010100T0_09", "");	//婚姻狀況
					dataMap.put("EHF010100T0_10", "");	//籍貫
					dataMap.put("EHF010100T0_11", null);	//生日
					dataMap.put("EHF010100T0_12", "0-0");	//戶籍電話		
					dataMap.put("EHF010100T0_13", "彰化縣");	//戶籍地址
					dataMap.put("EHF010100T0_14", "0-0");	//聯絡電話		
					dataMap.put("EHF010100T0_15", "0");	//聯絡地址
					dataMap.put("EHF010100T0_17", "0");	//緊急聯絡人
					dataMap.put("EHF010100T0_16", "0-0");	//手機號碼
					dataMap.put("EHF010100T0_18", "0");	//緊急聯絡人關係
					dataMap.put("EHF010100T0_19", "0"+"-"+"0");	//緊急聯絡人電話			
					dataMap.put("EHF010100T0_20", "0");	//專長
					dataMap.put("EHF010100T0_21", tool.StringToBoolean("0"));	//是否有汽車駕照
					dataMap.put("EHF010100T0_22", tool.StringToBoolean("0"));	//是否有機車駕照
					dataMap.put("EHF010100T0_23", "");	//其他證照
					dataMap.put("EHF010100T0_24", "");	//上傳照片檔案路徑
					dataMap.put("EHF010100T0_25", "");	//上傳身分證明檔案路徑
					dataMap.put("EHF010100T0_26", "");	//人資備註
					dataMap.put("EHF010100T0_27", "");	//到職日期
					dataMap.put("EHF010100T0_28", "");	//離職日期
					dataMap.put("EHF010100T0_29", "");	//服務年資
					dataMap.put("EHF010100T0_30", "");	//服務年資調整值(天)		
					dataMap.put("EHF010100T0_31", "");	//會員編號
					dataMap.put("EHF010100T1_02", "1");	//職務狀況
					dataMap.put("EHF010100T1_03", "2015/08/01");	//日期
					dataMap.put("EHF010100T1_04", tool.StringToBoolean("0"));	//是否失效
					dataMap.put("EHF010100T6_02", depMap.get(st.getCell( 4, i).getContents()));	//部門系統代碼
					dataMap.put("EHF010100T6_03", st.getCell( 6, i).getContents());	//部門歸屬區間(起)
					dataMap.put("EHF010100T6_04", "9999/01/01");	//部門歸屬區間(迄)
					dataMap.put("EHF010100T6_05", tool.StringToBoolean("1"));	//主要歸屬部門
					dataMap.put("EHF010100T6_06", "JOB"+String.format("%03d",Integer.parseInt(st.getCell( 7, i).getContents())));	//職務系統代碼
					dataMap.put("EHF010100T6_07", "0");	//是否為主管
					dataMap.put("EHF010100T6_08", "");	//級等
					dataMap.put("EHF010100T6_09", "");	//指定直屬主管
					dataMap.put("EHF010100T6_10", "");	//第一順位職務代理人
					dataMap.put("EHF010100T6_11", "");	//第二順位職務代理人
					dataMap.put("EHF010100T6_12", "");	//第三順位職務代理人
					dataMap.put("EHF010100T6_13", "0");	//是否請假
					dataMap.put("EHF010100T6_14", "");	//請假開始時間
					dataMap.put("EHF010100T6_15", "");	//請假結束時間
					dataMap.put("EHF020403T0_05", "0");	//
//					System.out.println("getCell1:"+st.getCell( 1, i).getContents());
//					System.out.println("getCell2:"+st.getCell( 2, i).getContents());
//					System.out.println("getCell3:"+String.format("%04d",Integer.parseInt(st.getCell( 3, i).getContents())));
//					System.out.println("getCell5:"+String.format("%04d",Integer.parseInt(st.getCell( 5, i).getContents())));
//					System.out.println("getCell13:"+String.format("%010d",Integer.parseInt(st.getCell( 13, i).getContents())));
//					dataMap.put("EHF010100T8_04_KEY", Integer.valueOf( (Integer)(CMap.get(st.getCell( 3, i).getContents()))));	//
//					dataMap.put("EHF010100T8_04_KEY", Integer.valueOf( (Integer)(CMap.get(String.format("%04d",Integer.parseInt(st.getCell( 3, i).getContents()))))));
					System.out.println("depMap:"+depMap);
					System.out.println("CMap:"+CMap);
					if("1".equals(st.getCell( 2, i).getContents()) || "3".equals(st.getCell( 2, i).getContents())){
						dataMap.put("EHF010100T8_04_KEY", Integer.valueOf( (Integer)(CMap.get("0001"))));
					}else{
						dataMap.put("EHF010100T8_04_KEY", Integer.valueOf( (Integer)(CMap.get("0002"))));
					}
					
					System.out.println(st.getCell( 2, i).getContents());
//					dataMap.put("COMP_ID","COM89883265");
					dataMap.put("COMP_ID","COM23405257");	//公司代碼
					dataMap.put("USER_NAME","管理者");  //資料建立者
					dataMap.put("USER_NAME","管理者");  //資料修改者
					dataMap.put("EHF020403T1_02", String.format("%010d",Integer.parseInt(st.getCell( 1, i).getContents()))); //感應卡號	(筌振)
					dataMap.put("EHF020403T1_04_START", "2015/07/01");	
					dataMap.put("EHF020403T1_04_START_HH", "00");	
					dataMap.put("EHF020403T1_04_START_MM", "00");
					if("1".equals(st.getCell( 2, i).getContents()) || "3".equals(st.getCell( 2, i).getContents())){
						dataMap.put("EHF010100T8_04",  String.valueOf( (Integer)(CMap.get("0001"))) );//班別序號  行政部門與PartTime部門一樣(筌振)
					}else{
						dataMap.put("EHF010100T8_04",  String.valueOf( (Integer)(CMap.get("0002"))) );//班別序號
					}
					dataMap.put("EHF020403T1_04_END", "");	
					dataMap.put("EHF020403T1_04_END_HH", "00");	
					dataMap.put("EHF020403T1_04_END_MM", "00");
					dataMap.put("EHF020403T1_05", "");

					xlsdatalist.add(dataMap);
			}
			
			hr_tools.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;*/
	}

	@Override
	protected Map chkImportDataList(Connection conn, List datalist,
			String compId) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tool = BA_TOOLS.getInstance();
		Map dataMap = new HashMap();
		Map errMsgMap = new HashMap();

		try{
			//重複不匯入清單
			List ng_list = new ArrayList();
			int ng_count = 0;
			//資料不正確清單
			List error_list = new ArrayList();
			int error_count = 0;
			
			//預先刪除所有匯入資料中重複資料
			Verification.DELETE_Overlap(datalist);
			
			boolean check_Exist=false;
			String sql = "";
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			//檢核匯入資料清單
			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				check_Exist=false;
				
				dataMap = (Map) it.next();
				
				if("".equals((String)dataMap.get("EHF010100T0_02")) && (String)dataMap.get("EHF010100T0_02") == null
				&& "".equals((String)dataMap.get("EHF010100T0_05")) && (String)dataMap.get("EHF010100T0_05") == null
				//&& "".equals((String)dataMap.get("EHF010100T0_I_TYPE")) && (String)dataMap.get("EHF010100T0_I_TYPE") == null
				//&& "".equals((String)dataMap.get("EHF010100T0_I")) && (String)dataMap.get("EHF010100T0_I") == null
				&& "".equals((String)dataMap.get("EHF010100T0_07")) && (String)dataMap.get("EHF010100T0_07") == null
				&& "".equals((String)dataMap.get("EHF010100T1_03")) && (String)dataMap.get("EHF010100T1_03") == null
				//&& "".equals((String)dataMap.get("EHF010100T0_16")) && (String)dataMap.get("EHF010100T0_16") == null
				//&& "".equals((String)dataMap.get("EHF010100T0_13")) && (String)dataMap.get("EHF010100T0_13") == null
				&& "".equals((String)dataMap.get("EHF010100T6_02")) && (String)dataMap.get("EHF010100T6_02") == null
				&& "".equals((String)dataMap.get("EHF010100T6_03")) && (String)dataMap.get("EHF010100T6_03") == null
				&& "".equals((String)dataMap.get("EHF010100T6_04")) && (String)dataMap.get("EHF010100T6_04") == null
				&& "".equals((String)dataMap.get("EHF010100T6_05")) && (String)dataMap.get("EHF010100T6_05") == null
				&& "".equals((String)dataMap.get("EHF010100T6_06")) && (String)dataMap.get("EHF010100T6_06") == null
				&& "".equals((String)dataMap.get("EHF010100T6_07")) && (String)dataMap.get("EHF010100T6_07") == null
//				&& "".equals((String)dataMap.get("EHF010100T0_32")) && (String)dataMap.get("EHF010100T0_32") == null
				&& "".equals((String)dataMap.get("EHF010100T8_04")) && (String)dataMap.get("EHF010100T8_04") == null){
					//結束此次迴圈
					continue;
				}
				
				if("".equals((String)dataMap.get("EHF010100T0_02")) || (String)dataMap.get("EHF010100T0_02") == null
				|| "".equals((String)dataMap.get("EHF010100T0_05")) || (String)dataMap.get("EHF010100T0_05") == null
				//|| "".equals((String)dataMap.get("EHF010100T0_I_TYPE")) || (String)dataMap.get("EHF010100T0_I_TYPE") == null
				//|| "".equals((String)dataMap.get("EHF010100T0_I")) || (String)dataMap.get("EHF010100T0_I") == null
				|| "".equals((String)dataMap.get("EHF010100T0_07")) || (String)dataMap.get("EHF010100T0_07") == null
				|| "".equals((String)dataMap.get("EHF010100T1_03")) || (String)dataMap.get("EHF010100T1_03") == null
				//|| "".equals((String)dataMap.get("EHF010100T0_16")) || (String)dataMap.get("EHF010100T0_16") == null
				//|| "".equals((String)dataMap.get("EHF010100T0_13")) || (String)dataMap.get("EHF010100T0_13") == null
				|| "".equals((String)dataMap.get("EHF010100T6_02")) || (String)dataMap.get("EHF010100T6_02") == null
				|| "".equals((String)dataMap.get("EHF010100T6_03")) || (String)dataMap.get("EHF010100T6_03") == null
				|| "".equals((String)dataMap.get("EHF010100T6_04")) || (String)dataMap.get("EHF010100T6_04") == null
				|| "".equals((String)dataMap.get("EHF010100T6_05")) || (String)dataMap.get("EHF010100T6_05") == null
				|| "".equals((String)dataMap.get("EHF010100T6_06")) || (String)dataMap.get("EHF010100T6_06") == null
				|| "".equals((String)dataMap.get("EHF010100T6_07")) || (String)dataMap.get("EHF010100T6_07") == null
//				|| "".equals((String)dataMap.get("EHF010100T0_32")) || (String)dataMap.get("EHF010100T0_32") == null
				|| "".equals((String)dataMap.get("EHF010100T8_04")) || (String)dataMap.get("EHF010100T8_04") == null){
					
					if (dataMap.get("error") == null){
						dataMap.put("error", "必填欄位未填寫!請再次確認!" + "<br>");
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
				
				//不足補0
				//生日
				if(!"".equals((String)dataMap.get("EHF010100T0_11"))&&(String)dataMap.get("EHF010100T0_11")!=null){					
					String birthday = tool.convertADDateToStrng(this.covertStringToDate((String)dataMap.get("EHF010100T0_11"), "yyyy/MM/dd"));
				}
				//到職日
				String work_day = tool.convertADDateToStrng(this.covertStringToDate((String)dataMap.get("EHF010100T1_03"), "yyyy/MM/dd"));
				//部門歸屬區間(起)
				String start_day = tool.convertADDateToStrng(this.covertStringToDate((String)dataMap.get("EHF010100T6_03"), "yyyy/MM/dd"));
				//部門歸屬區間(迄)
				String end_day = tool.convertADDateToStrng(this.covertStringToDate((String)dataMap.get("EHF010100T6_04"), "yyyy/MM/dd"));
				//感應卡
				if(!"".equals((String)dataMap.get("EHF020403T1_02"))&&(String)dataMap.get("EHF020403T1_02")!=null){	
					String card = tool.addZeroForNum((String)dataMap.get("EHF020403T1_02"),10);
				}
				
				//檢核部門是在系統內
				if(depMap.get((String)dataMap.get("EHF010100T6_02"))==null){
					if(dataMap.get("error")==null)
						dataMap.put("error", "部門資料未設定!請再次確認!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"部門資料未設定!請再次確認!!" + "<br>");
					}
					check_Exist=true;
				}
				
				//檢核職務是在系統內
				if(titleNameMap.get((String)dataMap.get("EHF010100T6_06"))==null){
					if(dataMap.get("error")==null)
						dataMap.put("error", "職務資料未設定!請再次確認!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"職務資料未設定!請再次確認!!" + "<br>");
					}
					check_Exist=true;
				}
				
				//檢核戶籍地址長度不可超過50個字
				if(((String)dataMap.get("EHF010100T0_13")).length()>=51){
					check_Exist = true;
					if (dataMap.get("error") == null)
						dataMap.put("error", "地址字數請勿超過50個字!" + "<br>");
					else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "地址字數請勿超過50個字!" + "<br>");
					}
				}
				
				//檢核電子郵件帳號長度不可超過100個字
//				if(((String)dataMap.get("EHF010100T0_31")).length()>=101){
//					check_Exist = true;
//					if (dataMap.get("error") == null)
//						dataMap.put("error", "電子郵件帳號字數請勿超過100個字!" + "<br>");
//					else {
//						dataMap.put("error", dataMap.get("error").toString()+ " " + "電子郵件帳號字數請勿超過100個字!" + "<br>");
//					}
//				}
				
//				if(!"".equals((String)dataMap.get("EHF010100T0_31"))&&(String)dataMap.get("EHF010100T0_31")!=null){
//					if(Verification.isEmail((String)dataMap.get("EHF010100T0_31"))){
//						check_Exist = true;
//						if (dataMap.get("error") == null)
//							dataMap.put("error", "Email格式不正確!" + "<br>");
//						else {
//							dataMap.put("error", dataMap.get("error").toString()+ " " + "Email格式不正確!" + "<br>");
//						}
//					}
//				}
				
				Calendar CHK_start_cal = tool.covertStringToCalendar(
						start_day+" 00:00:00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
				Calendar CHK_end_cal   = tool.covertStringToCalendar(
						end_day+" 00:00:00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
				
				if(CHK_start_cal.compareTo(CHK_end_cal)>0){
					check_Exist = true;
					if (dataMap.get("error") == null)
						dataMap.put("error", "時間錯誤!!歸屬日期開始時間不能在歸屬日期結束時間之前。" + "<br>");
					else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "時間錯誤!!歸屬日期開始時間不能在歸屬日期結束時間之前。" + "<br>");
					}
				}
				
				//是否為主管
				if("Y".equals((String)dataMap.get("EHF010100T6_07"))){	
					if("".equals((String)dataMap.get("EHF010100T6_08"))){
						check_Exist = true;
						if (dataMap.get("error") == null)
							dataMap.put("error", "員工是主管時，請填寫主管級等。" + "<br>");
						else {
							dataMap.put("error", dataMap.get("error").toString()+ " " + "員工是主管時，請填寫主管級等。" + "<br>");
						}
					}
				}
				
				sql = "" +
				" SELECT EHF010100T0_01, EHF010100T0_02, EHF010100T0_05 " +
				" FROM EHF010100T0 " +
				" WHERE 1=1 " +
				" AND EHF010100T0_02 = '"+(String)dataMap.get("EHF010100T0_02")+"' " +	//員工工號
				" AND EHF010100T0_05 = '"+(String)dataMap.get("EHF010100T0_05")+"' ";	//姓名(中)				
				
				rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					check_Exist = true;
					if (dataMap.get("error") == null)
						dataMap.put("error", "與資料庫資料重複，請再次確認!" + "<br>");
					else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "與資料庫資料重複，請再次確認!" + "<br>");
					}
				}
				
				if(dataMap.get("error")!=null){
					error_list.add(dataMap); // 將此筆資料移至資料不正確清單
					it.remove(); // 將此筆資料由待匯入清單移除
					error_count++;
					// 結束此次迴圈
					continue;
				}										
				
			}
			
			if(rs!=null){
				rs.close();
			}				
			stmt.close();
			
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
		
		BA_TOOLS tool = BA_TOOLS.getInstance();
		Map msgMap = new HashMap();
		Map dataMap = null;
		//建立EHF010106元件
		EHF010100 ehf010100 = new EHF010100(comp_id);
		try{
			
			
			
			//匯入筆數
			int dataCount = 0;

			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				
				String HR_EmployeeSysNo = tool.createEMSUID(conn, "EMP", "EHF010100T0", "EHF010100T0_01", comp_id);
				
				dataMap.put("EHF010100T0_01", HR_EmployeeSysNo);	//員工系統代碼
				dataMap.put("EHF010100T0_02", (String)dataMap.get("EHF010100T0_02"));	//員工工號
				if("0001".equals((String)dataMap.get("EHF010100T0_I_TYPE"))){	//身分證字號類型
					dataMap.put("EHF010100T0_I", (String)dataMap.get("EHF010100T0_I"));	//身分證字號
					dataMap.put("EHF010100T0_03", "");	//護照號碼
					dataMap.put("EHF010100T0_04", "");	//居留證號碼
				}else if("0002".equals((String)dataMap.get("EHF010100T0_I_TYPE"))){
					dataMap.put("EHF010100T0_I", "");	//身分證字號
					dataMap.put("EHF010100T0_03", (String)dataMap.get("EHF010100T0_I"));	//護照號碼
					dataMap.put("EHF010100T0_04", "");	//居留證號碼
				}else if("0003".equals((String)dataMap.get("EHF010100T0_I_TYPE"))){
					dataMap.put("EHF010100T0_I", "");	//身分證字號
					dataMap.put("EHF010100T0_03", "");	//護照號碼
					dataMap.put("EHF010100T0_04", (String)dataMap.get("EHF010100T0_I"));	//居留證號碼
				}else{
					dataMap.put("EHF010100T0_I", "");	//身分證字號
					dataMap.put("EHF010100T0_03", "");	//護照號碼
					dataMap.put("EHF010100T0_04", "");	//居留證號碼
				}
				dataMap.put("EHF010100T0_05", (String)dataMap.get("EHF010100T0_05"));	//姓名(中)
				dataMap.put("EHF010100T0_06", "");	//姓名(英)
				dataMap.put("EHF010100T0_07", (String)dataMap.get("EHF010100T0_07"));	//員工類別
				if("M".equals((String)dataMap.get("EHF010100T0_08"))){
					dataMap.put("EHF010100T0_08", "M");	//性別
				}else if("F".equals((String)dataMap.get("EHF010100T0_08"))){
					dataMap.put("EHF010100T0_08", "F");	//性別
				}else{
					dataMap.put("EHF010100T0_08", "");	//性別
				}
				dataMap.put("EHF010100T0_09", "");	//婚姻狀況
				dataMap.put("EHF010100T0_10", "");	//籍貫
				if(!"".equals((String)dataMap.get("EHF010100T0_11"))&&(String)dataMap.get("EHF010100T0_11")!=null){					
					String birthday = tool.convertADDateToStrng(this.covertStringToDate((String)dataMap.get("EHF010100T0_11"), "yyyy/MM/dd"));
					dataMap.put("EHF010100T0_11", birthday);	//生日
				}else{
					dataMap.put("EHF010100T0_11", null);	//生日
				}
				if("".equals((String)dataMap.get("EHF010100T0_14_N"))){
					dataMap.put("EHF010100T0_12", (String)dataMap.get("EHF010100T0_14")==null?"":(String)dataMap.get("EHF010100T0_14"));	//戶籍電話
				}else{
					dataMap.put("EHF010100T0_12", (String)dataMap.get("EHF010100T0_14_N")+"-"+(String)dataMap.get("EHF010100T0_14"));	//戶籍電話
				}
				dataMap.put("EHF010100T0_13", (String)dataMap.get("EHF010100T0_13"));	//戶籍地址
				if("".equals((String)dataMap.get("EHF010100T0_14_N"))){
					dataMap.put("EHF010100T0_14", (String)dataMap.get("EHF010100T0_14")==null?"":(String)dataMap.get("EHF010100T0_14"));	//聯絡電話	
				}else{
					dataMap.put("EHF010100T0_14", (String)dataMap.get("EHF010100T0_14_N")+"-"+(String)dataMap.get("EHF010100T0_14"));	//聯絡電話	
				}
				dataMap.put("EHF010100T0_15", (String)dataMap.get("EHF010100T0_13"));	//聯絡地址
				dataMap.put("EHF010100T0_16", (String)dataMap.get("EHF010100T0_16"));	//手機號碼
				dataMap.put("EHF010100T0_17", "");	//緊急聯絡人
				dataMap.put("EHF010100T0_18", "");	//緊急聯絡人關係
				dataMap.put("EHF010100T0_19", "0-0");	//緊急聯絡人電話			
				dataMap.put("EHF010100T0_20", "");	//專長
				dataMap.put("EHF010100T0_21", tool.StringToBoolean("0"));	//是否有汽車駕照
				dataMap.put("EHF010100T0_22", tool.StringToBoolean("0"));	//是否有機車駕照
				dataMap.put("EHF010100T0_23", "");	//其他證照
				dataMap.put("EHF010100T0_24", "");	//上傳照片檔案路徑
				dataMap.put("EHF010100T0_25", "");	//上傳身分證明檔案路徑
				dataMap.put("EHF010100T0_26", "");	//人資備註
				//到職日
				String work_day = tool.convertADDateToStrng(this.covertStringToDate((String)dataMap.get("EHF010100T1_03"), "yyyy/MM/dd"));
				dataMap.put("EHF010100T0_27", work_day);	//到職日期
				dataMap.put("EHF010100T0_28", "");	//離職日期
				dataMap.put("EHF010100T0_29", "");	//服務年資
				dataMap.put("EHF010100T0_30", "");	//服務年資調整值(天)				
//				dataMap.put("EHF010100T0_31", (String)dataMap.get("EHF010100T0_31"));	//電子郵件帳號
//				if("教員".equals((String)dataMap.get("EHF010100T0_32"))){
//					dataMap.put("EHF010100T0_32", "T");	//教/職員
//				}else if("職員".equals((String)dataMap.get("EHF010100T0_32"))){
//					dataMap.put("EHF010100T0_32", "O");	//教/職員
//				}
				
				dataMap.put("EHF010100T1_02", "1");	//職務狀況
				dataMap.put("EHF010100T1_03", work_day);	//日期
				dataMap.put("EHF010100T1_04", tool.StringToBoolean("0"));	//是否失效
				
				dataMap.put("EHF010100T6_02", (String) (depMap.get((String)dataMap.get("EHF010100T6_02"))));	//部門系統代碼
				//部門歸屬區間(起)
				String start_day = tool.convertADDateToStrng(this.covertStringToDate((String)dataMap.get("EHF010100T6_03"), "yyyy/MM/dd"));
				//部門歸屬區間(迄)
				String end_day = tool.convertADDateToStrng(this.covertStringToDate((String)dataMap.get("EHF010100T6_04"), "yyyy/MM/dd"));
				dataMap.put("EHF010100T6_03", start_day);	//部門歸屬區間(起)
				dataMap.put("EHF010100T6_04", end_day);		//部門歸屬區間(迄)
				if("Y".equals((String)dataMap.get("EHF010100T6_05"))){
					dataMap.put("EHF010100T6_05", tool.StringToBoolean("1"));	//主要歸屬部門
				}else{
					dataMap.put("EHF010100T6_05", tool.StringToBoolean("0"));	//主要歸屬部門
				}
				
				dataMap.put("EHF010100T6_06", (String) ((Map)titleNameMap.get((String)dataMap.get("EHF010100T6_06"))).get("TITLE_NO"));	//職務系統代碼
				
				if("Y".equals((String)dataMap.get("EHF010100T6_07"))){
					dataMap.put("EHF010100T6_07", "1");	//是否為主管
				}else{
					dataMap.put("EHF010100T6_07", "0");	//是否為主管
				}
				if("".equals((String)dataMap.get("EHF010100T6_08"))){
					dataMap.put("EHF010100T6_08", "");	//級等
				}else{
					dataMap.put("EHF010100T6_08", (String)dataMap.get("EHF010100T6_08"));	//級等
				}
				dataMap.put("EHF010100T6_09", "");	//指定直屬主管
				dataMap.put("EHF010100T6_10", "");	//第一順位職務代理人
				dataMap.put("EHF010100T6_11", "");	//第二順位職務代理人
				dataMap.put("EHF010100T6_12", "");	//第三順位職務代理人
				dataMap.put("EHF010100T6_13", "0");	//是否請假
				dataMap.put("EHF010100T6_14", "");	//請假開始時間
				dataMap.put("EHF010100T6_15", "");	//請假結束時間
				
				if("".equals((String)dataMap.get("EHF020403T1_02"))){
					//感應卡號空白不記錄考勤
					dataMap.put("EHF020403T0_05", "0");	//是否記錄考勤
				}else{
					dataMap.put("EHF020403T0_05", "1");	//是否記錄考勤
				}
				
				dataMap.put("EHF010100T8_04_KEY", this.getClassNo(conn, (String)dataMap.get("EHF010100T8_04"), comp_id));	//班別序號
				dataMap.put("EHF010100T8_05", "");	//備註
				
				dataMap.put("EHF020403T1_02", tool.addZeroForNum((String)dataMap.get("EHF020403T1_02"),10));	//感應卡號
				dataMap.put("EHF020403T1_04", "");	//啟用
				dataMap.put("EHF020403T1_04_START", work_day);//使用期間(起)
				dataMap.put("EHF020403T1_04_START_HH", "00");
				dataMap.put("EHF020403T1_04_START_MM", "00");
				dataMap.put("EHF020403T1_04_END", "9999/12/31");//使用期間(迄)
				dataMap.put("EHF020403T1_04_END_HH", "00");
				dataMap.put("EHF020403T1_04_END_MM", "00");
				dataMap.put("EHF020403T1_05", "");	//備註
				
				dataMap.put("COMP_ID", comp_id);
				dataMap.put("USER_NAME", owner);  //資料建立者
				dataMap.put("USER_NAME", owner);  //資料修改者			
				
				
				//薪資資料
				dataMap.put("EHF030200T0_05","");//
				dataMap.put("EHF030200T0_06","");//
				dataMap.put("EHF030200T0_06_AC","");//
				dataMap.put("EHF030200T0_07","");//
				dataMap.put("EHF030200T0_08","0");//
				dataMap.put("EHF030200T0_12","");//
				dataMap.put("EHF030200T0_18","");//
				dataMap.put("EHF030200T0_20","");//
				dataMap.put("EHF030200T0_21","1");//
				
				dataMap.put("EHF030300T0_05","");//
				dataMap.put("EHF030300T0_05_NUMBER","");//
				dataMap.put("EHF030300T0_07","");//
				dataMap.put("EHF030300T0_07_NUMBER","");//
				dataMap.put("EHF030300T0_09",0);//
				dataMap.put("EHF030300T0_10",0);//
				dataMap.put("EHF030300T0_11",1);//
				dataMap.put("EHF030300T0_15",0);//
				dataMap.put("EHF030300T0_05_START",tool.ymdTostring(tool.getSysDateYYMMDD()));//
				dataMap.put("EHF030300T0_05_END","0000-00-00");//
				dataMap.put("EHF030300T0_07_START",tool.ymdTostring(tool.getSysDateYYMMDD()));//
				dataMap.put("EHF030300T0_07_END","0000-00-00");//
				
				//新增員工
				ehf010100.addData(dataMap);
				//新增職務現況明細
				ehf010100.addDataDuty(dataMap);
				//新增狀況明細
				ehf010100.addDataJobLife(dataMap);
				//新增感應卡
				ehf010100.addDataCard(dataMap);
				//新增班別
				ehf010100.addDataClass(dataMap);
				//新增感應卡
				ehf010100.addDetailData("EHF020403T1",dataMap);
				//薪資資料
				ehf010100.addDataSalary(dataMap);
				ehf010100.addDataInsurance(dataMap);
				dataCount++;
			}
			
			
			ehf010100.close();
			conn.commit();
			//建立回傳訊息Map
			msgMap.put("DATALIST", datalist);
			msgMap.put("DATACOUNT", dataCount);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return msgMap;
	}
	
	/**
	 * 依班別代號取得班別序號
	 * @param conn
	 * @param show_class_no
	 * @param comp_id
	 * @return
	 */
	private int getClassNo(Connection conn, String show_class_no, String comp_id) {
		// TODO Auto-generated method stub
		
		int class_no = 0;
		
		try{
			String sql = ""+
			" SELECT EHF000400T0_01, EHF000400T0_03, EHF000400T0_04 " +
			" FROM EHF000400T0 " +
			" WHERE 1=1 " +
			" AND EHF000400T0_03 = '"+show_class_no+"' " +
			" AND EHF000400T0_11 = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				class_no = rs.getInt("EHF000400T0_01");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return class_no;
	}
	
	/**
	 * 依據傳入的日期與日期格式產生 Date
	 * @param date
	 * @param date_format
	 * @return
	 */
	private Date covertStringToDate(String date, String date_format) {
		// TODO Auto-generated method stub
		
		Date return_date = null;
		DateFormat dateFormat = new SimpleDateFormat(date_format);
		
		try{
			if(!"".equals(date) && date != null){
				return_date = dateFormat.parse(date);
			}else{
				throw new Exception("傳入的日期格式:"+date_format+" 有問題!!");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_date;
	}
	@Override
	public String getEMS_IMPORT_TYPE() {
		// TODO Auto-generated method stub
		//設定匯入檔案的型態
		return "XLS";
	}

}
