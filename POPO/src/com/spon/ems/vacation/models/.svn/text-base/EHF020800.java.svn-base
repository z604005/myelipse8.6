package com.spon.ems.vacation.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.util.overtime.EMS_Overtime_Record_Control;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;

/**
 * 加班申請 Business Model 元件
 * @author Joe
 *
 */
public class EHF020800 implements QueryFunction,EditFunction,EditDetailFunction,BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF020800(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF020800( String comp_id ){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction(comp_id);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public List queryData(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		List dataList = null;
		
		try{
			//Query
			String sql = "" +
			" SELECT " +
			" EHF020800T0_01, " +
			" EHF020800T0_03, EHF020800T0_04, " +
			" DATE_FORMAT(EHF020800T0_06, '%Y/%m/%d') AS EHF020800T0_06, " +
			" EHF020800T0_10, " +
			" EHF020800T0_09, " +
			" CASE EHF020800T0_09 " +
			" WHEN '01' THEN '處理中' " +
			" WHEN '02' THEN '已完成試算' " +
			" WHEN '03' THEN '已確認' " +
			" WHEN '04' THEN '已出帳' " +
			" WHEN '05' THEN '已結算' " +
			" WHEN '06' THEN '呈核'" +
			" WHEN '07' THEN '已完成'" +
			" WHEN '08' THEN '駁回'" +
			" WHEN '09' THEN '抽單'" +
			" WHEN '10' THEN '作廢'" +
			" END AS EHF020800T0_09_DESC,EHF020800T0_11, " +
			" USER_UPDATE, " +
			" DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H:%i:%s') AS DATE_UPDATE " +
			" FROM EHF020800T0 " +
			" WHERE 1=1 ";
			//if(this.base_tools.existString((String)queryCondMap.get("EHF020800T0_06"))){
			//	sql += " AND DATE_FORMAT(EHF020800T0_06, '%Y/%m') = ? ";  //加班日期
			//}
			
			if(!"".equals((String)queryCondMap.get("EHF020800T0_06")) && queryCondMap.get("EHF020800T0_06")!= null 
					&& !"".equals((String)queryCondMap.get("EHF020800T0_06_END")) && queryCondMap.get("EHF020800T0_06_END") !=null ){
				sql += " AND EHF020800T0_06 BETWEEN '"+(String)queryCondMap.get("EHF020800T0_06")+"' AND '"+(String)queryCondMap.get("EHF020800T0_06_END")+"' " ;  //日期區間  
			}else if(!"".equals((String)queryCondMap.get("EHF020800T0_06")) && (String)queryCondMap.get("EHF020800T0_06") != null
					 && (String)queryCondMap.get("EHF020800T0_06_END") == null){
				sql += " AND DATE_FORMAT(EHF020800T0_06, '%Y/%m') = '"+(String)queryCondMap.get("EHF020800T0_06")+"' ";  //加班日期
			}else if(!"".equals((String)queryCondMap.get("EHF020800T0_06")) && (String)queryCondMap.get("EHF020800T0_06") != null){
				sql += " AND EHF020800T0_06 >= '"+(String)queryCondMap.get("EHF020800T0_06")+"' ";
			}else if(!"".equals((String)queryCondMap.get("EHF020800T0_06_END")) && (String)queryCondMap.get("EHF020800T0_06_END") != null){
				sql += " AND EHF020800T0_06 <= '"+(String)queryCondMap.get("EHF020800T0_06_END")+"' ";
			}
			
			if(!"".equals((String)queryCondMap.get("EHF020800T0_11")) && (String)queryCondMap.get("EHF020800T0_11") != null){
				sql += " AND EHF020800T0_11 = '"+(String)queryCondMap.get("EHF020800T0_11")+"' ";
			}
			if(!"".equals((String)queryCondMap.get("EHF020800T0_09")) && (String)queryCondMap.get("EHF020800T0_09") != null){
				sql += " AND EHF020800T0_09 = '"+(String)queryCondMap.get("EHF020800T0_09")+"' ";  //表單狀態
			}
			sql += " AND EHF020800T0_10 = ? ";  //公司代碼
			sql += " ORDER BY EHF020800T0_06";
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			
			//if(this.base_tools.existString((String)queryCondMap.get("EHF020800T0_06"))){
			//	pstmt.setString(indexid, (String)queryCondMap.get("EHF020800T0_06"));
			//	indexid++;
			//}
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID"));
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			//dataList = this.base_tools.queryList(sql);
			
			pstmt.close();
			
			//BIND FLOW DATA
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//取得Flow 員工資訊Map
			Map empMap = hr_tools.getEmpNameMap((String)queryCondMap.get("COMP_ID"));
			//取得Flow 部門資訊Map
			Map depMap = hr_tools.getDepNameMap((String)queryCondMap.get("COMP_ID"));
			
			hr_tools.close();
			
			//取得正職員工清單
			//Map	WorkEmp=(Map)queryCondMap.get("WorkEmp");
			//取得離職員工清單
			//Map	DepartureWorkEmp=(Map)queryCondMap.get("DepartureWorkEmp");
			//取得留職停薪員工清單
			//Map	SuspensionWithoutPayEmp=(Map)queryCondMap.get("SuspensionWithoutPayEmp");
			

			String tmp_emp_id ="";
			
			
			Iterator it = dataList.iterator();
			Map tempMap = null;
			Map tempQueryCondMap = new HashMap();
			List tempQueryList = null;
			//複製queryCondMap
			tempQueryCondMap.putAll(queryCondMap);
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				boolean chk_flag = false;//在職判斷
				boolean SuspensionWithoutPay= false;//留職停薪判斷
				boolean DepartureWork= false;//離職停薪判斷
				/*
				//1.先處理留職停薪人員
				
					Iterator departure_emp_it = SuspensionWithoutPayEmp.keySet().iterator();
					while (departure_emp_it.hasNext()) {
						tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
						if (tmp_emp_id.equals(tempMap.get("EHF020800T0_03"))) {
							SuspensionWithoutPay = true;
							break;
						} else {
							SuspensionWithoutPay = false;
						}
					}
				
				//2.處理離職員工
				if(!SuspensionWithoutPay){
					departure_emp_it = DepartureWorkEmp.keySet().iterator();
					while (departure_emp_it.hasNext()) {
						tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
						if (tmp_emp_id.equals(tempMap.get("EHF020800T0_03"))) {
							DepartureWork = true;
							break;
						} else {
							DepartureWork = false;
						}
					}
					
				}
				
				//2.處理在職員工
				if(!SuspensionWithoutPay && !DepartureWork){
					departure_emp_it = WorkEmp.keySet().iterator();
					while (departure_emp_it.hasNext()) {
						tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
						if (tmp_emp_id.equals(tempMap.get("EHF020800T0_03"))) {
							chk_flag = true;
							break;
						} else {
							chk_flag = false;
						}
					}
					
				}
				*/
				/*if(SuspensionWithoutPay){
					//處理填單人資料Mapping
					tempMap.put("EHF020800T0_03", tempMap.get("EHF020800T0_03")+" "+(String) ((Map)empMap.get(tempMap.get("EHF020800T0_03"))).get("EMPLOYEE_NAME")+"(留職停薪)");
				}else if(DepartureWork){
					//處理填單人資料Mapping
					tempMap.put("EHF020800T0_03", tempMap.get("EHF020800T0_03")+" "+(String) ((Map)empMap.get(tempMap.get("EHF020800T0_03"))).get("EMPLOYEE_NAME")+"(離職)");
				}else if(chk_flag){*/
					//處理填單人資料Mapping
					tempMap.put("EHF020800T0_03", (String) ((Map)empMap.get(tempMap.get("EHF020800T0_03"))).get("EMPLOYEE_ID")
							+" "+
							(String) ((Map)empMap.get(tempMap.get("EHF020800T0_03"))).get("EMPLOYEE_NAME"));
				/*}*/
				
				
				
				
				
				tempMap.put("EHF020800T0_11", (String) ((Map)depMap.get(tempMap.get("EHF020800T0_11"))).get("SHOW_DEPT_ID")
						+" "+
						(String) ((Map)depMap.get(tempMap.get("EHF020800T0_11"))).get("SHOW_DESC"));
				
				//Query加班明細
				//建立加班明細Query Key
				tempQueryCondMap.put("EHF020800T0_01", (String)tempMap.get("EHF020800T0_01"));
				
				//Query
				//tempQueryList = this.queryEHF020800T1List(tempQueryCondMap,WorkEmp,DepartureWorkEmp,SuspensionWithoutPayEmp);
				tempQueryList = this.queryEHF020800T1List(tempQueryCondMap);
				
				//Set queryList to Map
				tempMap.put("EHF020800T1_LIST", tempQueryList);
			}
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataList;
	}

	@Override
	public Map queryEditData(Map queryCondMap) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		
		try{
			List EHF020800T1_LIST = new ArrayList();
			//BIND FLOW DATA
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//取得Flow 員工資訊Map
			Map empMap = hr_tools.getEmpNameMap((String)queryCondMap.get("COMP_ID"));
			//取得Flow 部門資訊Map
			Map depMap = hr_tools.getDepNameMap((String)queryCondMap.get("COMP_ID"));
			
			hr_tools.close();
			
			//取得正職員工清單
			//Map	WorkEmp=(Map)queryCondMap.get("WorkEmp");
			//取得離職員工清單
			//Map	DepartureWorkEmp=(Map)queryCondMap.get("DepartureWorkEmp");
			//取得留職停薪員工清單
			//Map	SuspensionWithoutPayEmp=(Map)queryCondMap.get("SuspensionWithoutPayEmp");
			
			
			//QueryEdit
			String sql = "" +
			" SELECT " +
			" EHF020800T0_01,EHF020800T0_06, " +
			" EHF020800T0_03, EHF020800T0_04, " +
			" DATE_FORMAT(EHF020800T0_05, '%Y/%m/%d') AS EHF020800T0_05, " +
			" DATE_FORMAT(EHF020800T0_06, '%Y/%m/%d') AS EHF020800T0_06, " +
			" EHF020800T0_09, EHF020800T0_11," +
			" USER_CREATE, USER_UPDATE, " +
			" VERSION, " +
			" DATE_FORMAT(DATE_CREATE, '%Y/%m/%d %H:%i:%s') AS DATE_CREATE, " +
			" DATE_FORMAT(DATE_UPDATE, '%Y/%m/%d %H:%i:%s') AS DATE_UPDATE " +
			" FROM EHF020800T0 " +
			" WHERE 1=1 " +
			" AND EHF020800T0_01 = '"+(String)queryCondMap.get("EHF020800T0_01")+"' " +
			" AND EHF020800T0_10 = '"+(String)queryCondMap.get("COMP_ID")+"' ";
			//執行SQL
			dataMap = this.base_tools.query(sql);
			
			if(!dataMap.isEmpty()){
			
				//Query 加班明細資訊
				queryCondMap.put("EHF020800T0_06", dataMap.get("EHF020800T0_06"));

				//EHF020800T1_LIST = this.queryEHF020800T1List(queryCondMap,WorkEmp,DepartureWorkEmp,SuspensionWithoutPayEmp);
				EHF020800T1_LIST = this.queryEHF020800T1List(queryCondMap);
				dataMap.put("EHF020800T1_LIST", EHF020800T1_LIST);
				
			}
			
			
			boolean chk_flag = false;//在職判斷
			boolean SuspensionWithoutPay= false;//留職停薪判斷
			boolean DepartureWork= false;//離職停薪判斷
			String tmp_emp_id ="";
			/*
			//1.先處理留職停薪人員
			
			Iterator departure_emp_it = SuspensionWithoutPayEmp.keySet().iterator();
			while (departure_emp_it.hasNext()) {
				tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
				if (tmp_emp_id.equals(dataMap.get("EHF020800T0_03"))) {
					SuspensionWithoutPay = true;
					break;
				} else {
					SuspensionWithoutPay = false;
				}
			}
		
			//2.處理離職員工
			if(!SuspensionWithoutPay){
				departure_emp_it = DepartureWorkEmp.keySet().iterator();
				while (departure_emp_it.hasNext()) {
					tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
					if (tmp_emp_id.equals(dataMap.get("EHF020800T0_03"))) {
						DepartureWork = true;
						break;
					} else {
						DepartureWork = false;
					}
				}
			}
		
			//2.處理在職員工
			if(!SuspensionWithoutPay && !DepartureWork){
				departure_emp_it = WorkEmp.keySet().iterator();
				while (departure_emp_it.hasNext()) {
					tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
					if (tmp_emp_id.equals(dataMap.get("EHF020800T0_03"))) {
						chk_flag = true;
						break;
					} else {
						chk_flag = false;
					}
				}
			}
			*/
			
			
			
			//處理填單人部門資料Mapping
			dataMap.put("EHF020800T0_04_TXT", (String) ((Map)depMap.get(dataMap.get("EHF020800T0_04"))).get("SHOW_DESC"));
			//處理填單人資料Mapping
			dataMap.put("EHF020800T0_03_TXT", (String) ((Map)empMap.get(dataMap.get("EHF020800T0_03"))).get("EMPLOYEE_NAME"));
			
			/*if(SuspensionWithoutPay){
				//處理填單人資料Mapping
				dataMap.put("EHF020800T0_03_TXT", (String) ((Map)empMap.get(dataMap.get("EHF020800T0_03"))).get("EMPLOYEE_NAME")+"(留職停薪)");
			}else if(DepartureWork){
				//處理填單人資料Mapping
				dataMap.put("EHF020800T0_03_TXT", (String) ((Map)empMap.get(dataMap.get("EHF020800T0_03"))).get("EMPLOYEE_NAME")+"(離職)");
			}else if(chk_flag){*/
				//處理填單人資料Mapping
				dataMap.put("EHF020800T0_03_TXT", (String) ((Map)empMap.get(dataMap.get("EHF020800T0_03"))).get("EMPLOYEE_NAME"));
			/*}*/
			
			
			
			dataMap.put("EHF020800T0_11", (String) dataMap.get("EHF020800T0_11"));
			dataMap.put("EHF020800T0_11_SHOW", (String) ((Map)depMap.get(dataMap.get("EHF020800T0_11"))).get("SHOW_DEPT_ID"));
			dataMap.put("EHF020800T0_11_TXT", (String) ((Map)depMap.get(dataMap.get("EHF020800T0_11"))).get("SHOW_DESC"));
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return dataMap;
	}
	
	/**
	 * 查詢加班明細資訊
	 * @param queryCondMap
	 * @param suspensionWithoutPayEmp 
	 * @param departureWorkEmp 
	 * @param workEmp 
	 * @return
	 */
	//public List queryEHF020800T1List(Map queryCondMap, Map WorkEmp, Map DepartureWorkEmp, Map SuspensionWithoutPayEmp){
	public List queryEHF020800T1List(Map queryCondMap){
		
		List EHF020800T1_LIST = new ArrayList();
		String tmp_emp_id="";
		boolean chk_flag = false;//在職判斷
		boolean SuspensionWithoutPay= false;//留職停薪判斷
		boolean DepartureWork= false;//離職停薪判斷
		
		
		
		try{
			//Query 加班明細資訊
			String sql = "" +
			" SELECT " +
			" DATE_FORMAT(EHF020800T0_06, '%Y/%m/%d') AS EHF020800T0_06,EHF020800T1.*, " +
			" CONCAT(DATE_FORMAT(EHF020800T1_06, '%Y/%m/%d %H:%i'),   ' ~ ',           " +
			" DATE_FORMAT(EHF020800T1_07, '%Y/%m/%d %H:%i')) AS EHF020800T1_06," +
			" CONCAT( DATE_FORMAT(EHF020800T1_06_BRK, '%H:%i'), ' ~ ', DATE_FORMAT(EHF020800T1_07_BRK, '%H:%i') ) " +
			" AS EHF020800T1_06_BRK, " +
			" EHF020800T1_07, " +
			" (EHF020800T1_08 - EHF020800T1_08_DE+EHF020800T1_12) AS EHF020800T1_08_REAL ," +
			" EHF020802T0_07,EHF020802T0_07_DE,EHF020802T0_10" +
			" FROM EHF020800T1 " +
			" LEFT JOIN EHF020800T0 " +
			"      ON EHF020800T0_01=EHF020800T1_01 AND EHF020800T0_10=EHF020800T1_10" +
			" LEFT JOIN EHF020801T0 " +
			"      ON EHF020801T0_03=EHF020800T1_02 " +
			" LEFT JOIN EHF020802T0 " +
			"      ON EHF020801T0_01=EHF020802T0_03 " +
			" WHERE 1=1 " +
			" AND EHF020800T1_01 = '"+(String)queryCondMap.get("EHF020800T0_01")+"' " +
			" AND EHF020800T1_10 = '"+(String)queryCondMap.get("COMP_ID")+"' " +
			" ORDER BY EHF020800T1_03 ";
			EHF020800T1_LIST = this.base_tools.queryList(sql);
			//BIND FLOW DATA
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//取得Flow 員工資訊Map
			Map empMap = hr_tools.getEmpNameMap((String)queryCondMap.get("COMP_ID"));
			//取得Flow 部門資訊Map
			Map depMap = hr_tools.getDepNameMap((String)queryCondMap.get("COMP_ID"));
			
			hr_tools.close();
			
			Iterator it = EHF020800T1_LIST.iterator();
			Map tempMap = null;
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				
			/*	
				//1.先處理留職停薪人員
				
				Iterator departure_emp_it = SuspensionWithoutPayEmp.keySet().iterator();
				while (departure_emp_it.hasNext()) {
					tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
					if (tmp_emp_id.equals(tempMap.get("EHF020800T1_04"))) {
						SuspensionWithoutPay = true;
						break;
					} else {
						SuspensionWithoutPay = false;
					}
				}
			
			//2.處理離職員工
			if(!SuspensionWithoutPay){
				departure_emp_it = DepartureWorkEmp.keySet().iterator();
				while (departure_emp_it.hasNext()) {
					tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
					if (tmp_emp_id.equals(tempMap.get("EHF020800T1_04"))) {
						DepartureWork = true;
						break;
					} else {
						DepartureWork = false;
					}
				}
				
			}
			
			//2.處理在職員工
			if(!SuspensionWithoutPay && !DepartureWork){
				departure_emp_it = WorkEmp.keySet().iterator();
				while (departure_emp_it.hasNext()) {
					tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
					if (tmp_emp_id.equals(tempMap.get("EHF020800T1_04"))) {
						chk_flag = true;
						break;
					} else {
						chk_flag = false;
					}
				}
				
			}
			*/
			/*if(SuspensionWithoutPay){
				//處理填單人資料Mapping
				tempMap.put("EHF020800T1_04", tempMap.get("EHF020800T1_04")+"/"+(String) ((Map)empMap.get(tempMap.get("EHF020800T1_04"))).get("EMPLOYEE_NAME")+"(留職停薪)");
			}else if(DepartureWork){
				//處理填單人資料Mapping
				tempMap.put("EHF020800T1_04", tempMap.get("EHF020800T1_04")+"/"+(String) ((Map)empMap.get(tempMap.get("EHF020800T1_04"))).get("EMPLOYEE_NAME")+"(離職)");
			}else if(chk_flag){*/
				//處理填單人資料Mapping
				tempMap.put("EHF020800T1_04", (String) ((Map)empMap.get(tempMap.get("EHF020800T1_04"))).get("EMPLOYEE_ID")
						+"/"+
						(String) ((Map)empMap.get(tempMap.get("EHF020800T1_04"))).get("EMPLOYEE_NAME"));
			/*}*/
			
			
				//tempMap.put("EHF020800T1_04", tempMap.get("EHF020800T1_04")+"/"+(String) ((Map)empMap.get(tempMap.get("EHF020800T1_04"))).get("EMPLOYEE_NAME"));
				//處理部門員工資料Mapping
				//tempMap.put("EHF020800T1_05", 
							//tempMap.get("EHF020800T1_05")+" "+ (String) ((Map)depMap.get(tempMap.get("EHF020800T1_05"))).get("SHOW_DESC")+" / "+
				//			tempMap.get("EHF020800T1_04")+" "+(String) ((Map)empMap.get(tempMap.get("EHF020800T1_04"))).get("EMPLOYEE_NAME"));
				if("01".equals(tempMap.get("EHF020800T1_11"))){
					tempMap.put("EHF020800T1_11", "換錢");
				}else{
					tempMap.put("EHF020800T1_11", "換休");
				}
				
				if("01".equals(tempMap.get("EHF020800T1_13"))){
					tempMap.put("EHF020800T1_13", "例假日");
				}else if("02".equals(tempMap.get("EHF020800T1_13"))){
					tempMap.put("EHF020800T1_13", "休息日");
				}else if("03".equals(tempMap.get("EHF020800T1_13"))){
					tempMap.put("EHF020800T1_13", "國定假日");
				}else if("00".equals(tempMap.get("EHF020800T1_13"))){
					tempMap.put("EHF020800T1_13", "平日");
				}
				
				
				if("on".equals(tempMap.get("NOON_LIMITED"))){
					//表示該單子為限定中午，實際加班時間就是
					//(EHF020800T1_08 - EHF020800T1_08_DE + EHF020800T1_12)
				}else{
					
					if(tempMap.get("EHF020802T0_07")==null){
						//無加班考勤資料，因此實際加班時間為0
						tempMap.put("EHF020800T1_08_REAL", "0");
						//System.out.println("001");
					}else{
						tempMap.put("EHF020800T1_08_REAL", Float.valueOf((Float)tempMap.get("EHF020802T0_07"))
								- Float.valueOf((Float)tempMap.get("EHF020802T0_07_DE"))
								+  Float.valueOf((Float)tempMap.get("EHF020802T0_10")));
						//System.out.println("002");
					}
					
				}				
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF020800T1_LIST;
	}

	@Override
	public void addData(Map dataMap) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		String show_sql = "";
		
		try{
			//Add
			sql = "" +
			" INSERT INTO EHF020800T0 " +
			" ( " +
			" EHF020800T0_01, EHF020800T0_02, " +
			" EHF020800T0_03, EHF020800T0_04, " +
			" EHF020800T0_05, EHF020800T0_06, EHF020800T0_07, " +
			" EHF020800T0_08, EHF020800T0_09, EHF020800T0_09_DESC, " +
			" EHF020800T0_10, EHF020800T0_11, EHF020800T0_12, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, " +
			" ?, ?, " +
			" ?, ?, ?, " +
			" ?, ?, ?, " +
			" ?, ?, ?, " +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			//加班明細UID
			String EHF020800T0_01 = "";
			//使用 CodeRules 做單號取得的動作
			//EHF020800T0_01 = EMS_GetCodeRules.getInstance().getCodeRule("EHF020800T0", (String)dataMap.get("COMP_ID"));
			EHF020800T0_01 = tools.createEMSUID(this.base_tools.getConn(), "EO", "EHF020800T0", "EHF020800T0_01", (String)dataMap.get("COMP_ID"));
			
			p6stmt.setString(indexid, EHF020800T0_01);  //加班明細UID
			indexid++;
			p6stmt.setString(indexid, "");  //流程空表單編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_03"));  //填單人員工工號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_04"));  //填單人部門代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_05"));  //填單日期
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_06"));  //加班日期
			indexid++;
			p6stmt.setString(indexid, "");  //加班原因
			indexid++;
			p6stmt.setString(indexid, "");  //附加檔案在Server的路徑
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_09"));  //表單狀態
			indexid++;
			p6stmt.setString(indexid, "");  //表單狀態說明
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_11"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_12"));  //
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
			//回寫 加班表單編號
			dataMap.put("KEY_ID", EHF020800T0_01);
			
			//記錄新增訊息
			this.base_tools.writeADDMSG("EHF020800().addData()", show_sql, 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF020800.addData()", show_sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		
		try{
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			//取得加班記錄 use 加班單
			overtime_list = this.queryOVList(dataMap);
			//連動加班記錄
			ems_ov_ctrl.removeOvertimeRecord(overtime_list);
			
			//關閉加班記錄連動元件
			ems_ov_ctrl.close();
			
			List sql_list = new ArrayList();
			
			//DELETE EHF020800T1
			sql = "" +
			" DELETE FROM EHF020800T1 " +
			" WHERE 1=1 " +
			" AND EHF020800T1_01 = '"+(String)dataMap.get("EHF020800T0_01")+"' " +  //加班表單編號
			" AND EHF020800T1_10 = '"+(String)dataMap.get("COMP_ID")+"' ";  //公司代碼
			sql_list.add(sql);
			
			//DELETE EHF020800T0
			sql = "" +
			" DELETE FROM EHF020800T0 " +
			" WHERE 1=1 " +
			" AND EHF020800T0_01 = '"+(String)dataMap.get("EHF020800T0_01")+"' " +  //加班表單編號
			" AND EHF020800T0_10 = '"+(String)dataMap.get("COMP_ID")+"' ";  //公司代碼
			sql_list.add(sql);
			
			//DELETE SFLOW_LOG_T0
			sql = "" +
			" DELETE FROM SFLOW_LOG_T0 " +
			" WHERE 1=1 " +
			" AND SFLOW_LOG_T0_02 = '"+(String)dataMap.get("EHF020800T0_01")+"' " +
			" AND SFLOW_LOG_T0_14 = '"+(String)dataMap.get("COMP_ID")+"' ";	//公司代碼
			sql_list.add(sql);
			
			//執行刪除
			int[] dataCount_array = this.base_tools.executeBatchSQL(sql_list);
			int dataCount = 0;
			int mainDataCount = 0;
			for(int i=0; i<dataCount_array.length; i++){
				dataCount += dataCount_array[i];
				mainDataCount = dataCount_array[i];
			}
			//int dataCount = this.base_tools.delete(sql);
			
			dataMap.put("MAIN_DATA_COUNT", mainDataCount);
			
			//更新資料庫
			this.base_tools.commit();
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF020800().delData()", sql+" total delete "+dataCount+"rows data", 
										dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF020800().delData()", sql+", "+e.getMessage(), 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF020800T0 SET " +
			" EHF020800T0_06 = ?, " +
			" EHF020800T0_07 = '', " +
			" USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
			" WHERE 1=1 " +
			" AND EHF020800T0_01 = ? " +  //加班表單編號
			" AND EHF020800T0_10 = ? ";  //公司代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_06"));  //加班日期
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_01"));  //加班表單編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
					
			//更新資料庫
			this.base_tools.commit();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF020800().saveData()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF020800().saveData()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 組成加班單的加班時間資訊
	 * @param overtime_date
	 * @param overtime_start_time
	 * @param overtime_end_time
	 * @param string 
	 * @param min_pay_hour 
	 * @param EHF020800T1_12 
	 * @return
	 */
	public Map composeOvertimeDate(String overtime_start_date,  String overtime_start_time, String overtime_end_date, String overtime_end_time, float min_pay_hour){
		//取得最低付薪時數
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Map return_map = new HashMap();
		
		try{
			//處理加班時間與加班時數
			int overtime_in = Integer.parseInt(overtime_start_time);
			int overtime_out = Integer.parseInt(overtime_end_time);
			
			if(overtime_in > overtime_out){
				//表示加班區間有跨午夜十二點
				overtime_out = overtime_out + ((int)2400);
			}
			
			//型態格式器
			DecimalFormat formatter = new DecimalFormat("0000");
			
			//處理加班時數
			int overtime_in_sec = tools.TimeToSecs(formatter.format(overtime_in));
			int overtime_out_sec = tools.TimeToSecs(formatter.format(overtime_out));
			int overtime_sec = overtime_out_sec - overtime_in_sec;
			float overtime_hours = ((float)(((int) ((float)overtime_sec/1800))*(min_pay_hour*60*60))/60/60);//修改於20131113  BY賴泓錡
			
			//設定加班開始時間
			Calendar overtime_in_cal =
			tools.covertStringToCalendar(overtime_start_date+" "+formatter.format(Integer.parseInt(overtime_start_time)),"yyyy/MM/dd HHmm");
			
			//建立Calendar處理加班時間
			Calendar overtime_out_cal =
			tools.covertStringToCalendar(overtime_end_date+" "+formatter.format(Integer.parseInt(overtime_end_time)), "yyyy/MM/dd HHmm");
			
			//建立加班時間與加班時數資訊
			return_map.put("EHF020800T1_06", tools.covertDateToString(overtime_in_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));  //加班開始時間
			return_map.put("EHF020800T1_07", tools.covertDateToString(overtime_out_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));  //加班結束時間
			return_map.put("EHF020800T1_08", overtime_hours+"");  //加班時數
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 組成加班單的加班休息時間資訊
	 * @param overtime_date
	 * @param overtime_start_time
	 * @param overtime_end_time
	 * @param overtime_break_start_time
	 * @param overtime_break_end_time
	 * @return
	 */
	public Map composeOvertimeBreakDate(String overtime_start_date,String overtime_end_date,
										String overtime_start_time, String overtime_end_time,
										String overtime_break_start_time, 
										String overtime_break_end_time) {

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Map return_map = new HashMap();

		try {
			// 處理加班時間與加班休息時間
			int overtime_in = Integer.parseInt(overtime_start_time);
			int overtime_out = Integer.parseInt(overtime_end_time);
			int overtime_break_in = Integer.parseInt(overtime_break_start_time);
			int overtime_break_out = Integer.parseInt(overtime_break_end_time);

			//判斷休息時間區段
			if(overtime_break_in != overtime_break_out){
				if( overtime_in > overtime_break_in && overtime_in > overtime_break_out){
					//表示加班休息開始時間與結束時間皆跨越午夜十二點
					overtime_break_in = overtime_break_in + ((int)2400);
					overtime_break_out = overtime_break_out + ((int)2400);
				}else if(overtime_in > overtime_break_out){
					//表示加班休息結束時間皆跨越午夜十二點
					overtime_break_out = overtime_break_out + ((int)2400);
				}else{
					//表示加班休息時間皆未跨越午夜十二點
					
				}
			}
			
			//型態格式器
			DecimalFormat formatter = new DecimalFormat("0000");
			
			// 處理加班內扣時數
			int overtime_break_in_sec = tools.TimeToSecs(formatter.format(overtime_break_in));
			int overtime_break_out_sec = tools.TimeToSecs(formatter.format(overtime_break_out));
			int overtime_break_sec = overtime_break_out_sec - overtime_break_in_sec;
			float overtime_break_hours = (((float) overtime_break_sec) / 60 / 60);

			//建立Calendar處理加班休息開始時間
			Calendar overtime_in_cal = 
			tools.covertStringToCalendar(
			overtime_start_date + " " + formatter.format(overtime_break_in),
			"yyyy/MM/dd HHmm");

			//建立Calendar處理加班休息結束時間
			Calendar overtime_out_cal = 
			tools.covertStringToCalendar(
			overtime_end_date + " " + formatter.format(overtime_break_out), 
			"yyyy/MM/dd HHmm");

			// 建立加班時間與加班時數資訊
			return_map.put("EHF020800T1_06_BRK", tools.covertDateToString(overtime_in_cal.getTime(), "yyyy/MM/dd HH:mm:ss")); // 加班休息開始時間
			return_map.put("EHF020800T1_07_BRK", tools.covertDateToString(overtime_out_cal.getTime(), "yyyy/MM/dd HH:mm:ss")); // 加班休息結束時間
			return_map.put("EHF020800T1_08_DE", overtime_break_hours + ""); // 加班內扣時數

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	/**
	 * 檢核加班休息時間區間是否異常
	 * @param over_start_datetime
	 * @param over_end_datetime
	 * @param over_break_start_datetime
	 * @param over_break_end_datetime
	 * @return
	 */
	public Map checkOvertimeBreakDatAbnormal(String over_start_datetime, String over_end_datetime,
									  		 String over_break_start_datetime,
									  		 String over_break_end_datetime) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Map return_map = new HashMap();
		boolean chk_flag = false;

		try {
			//處理加班時間資訊
			Calendar ov_start_cal = tools.covertStringToCalendar(over_start_datetime, "yyyy/MM/dd HH:mm:ss");
			Calendar ov_end_cal = tools.covertStringToCalendar(over_end_datetime, "yyyy/MM/dd HH:mm:ss");
			Calendar ov_brk_start_cal = tools.covertStringToCalendar(over_break_start_datetime, "yyyy/MM/dd HH:mm:ss");
			Calendar ov_brk_end_cal = tools.covertStringToCalendar(over_break_end_datetime, "yyyy/MM/dd HH:mm:ss");
			
			
			
			
			//判斷加班休息時間區間是否異常
			if(ov_brk_start_cal.compareTo(ov_brk_end_cal) == 0){
				//表示加班休息時間區間未設定
				chk_flag = false;
			}else{
				if((ov_brk_start_cal.compareTo(ov_start_cal) > 0 && ov_brk_start_cal.compareTo(ov_end_cal) < 0)
					&& (ov_brk_end_cal.compareTo(ov_start_cal) > 0 && ov_brk_end_cal.compareTo(ov_end_cal) < 0)
					&& (ov_brk_start_cal.compareTo(ov_brk_end_cal) < 0)){
					//表示加班休息時間區間正常
					chk_flag = false;
				}else{
					chk_flag = true;
				}
			}
			
			// 建立 RETURN MAP
			return_map.put("CHK_FLAG", chk_flag);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}

	public Map checkOvertimeBreakDatAbnormal_Middle(String over_start_datetime,	String over_end_datetime, String over_break_start_datetime,	String over_break_end_datetime) {

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Map return_map = new HashMap();
		boolean chk_flag = false;

		try {
			// 處理加班時間資訊
			//加班開始時間
			Calendar ov_start_cal = tools.covertStringToCalendar(over_start_datetime, "yyyy/MM/dd HH:mm:ss");
			//加班休息時間
			Calendar ov_end_cal = tools.covertStringToCalendar(over_end_datetime, "yyyy/MM/dd HH:mm:ss");
			//加班中間休息開始時間
			Calendar ov_brk_start_cal = tools.covertStringToCalendar(over_break_start_datetime, "yyyy/MM/dd HH:mm:ss");
			//加班中間休息結束時間
			Calendar ov_brk_end_cal = tools.covertStringToCalendar(over_break_end_datetime, "yyyy/MM/dd HH:mm:ss");

			// 判斷加班休息時間區間是否異常
			if (ov_brk_start_cal.compareTo(ov_brk_end_cal) == 0) {
				// 表示加班休息時間區間未設定
				chk_flag = false;
			} else {
				if ((ov_brk_start_cal.compareTo(ov_start_cal) > 0 && ov_brk_start_cal.compareTo(ov_end_cal) < 0)
						&& (ov_brk_end_cal.compareTo(ov_start_cal) > 0 && ov_brk_end_cal.compareTo(ov_end_cal) < 0)
						&& (ov_brk_start_cal.compareTo(ov_brk_end_cal) < 0)) {
					// 表示加班休息時間區間正常
					chk_flag = false;
				} else {
					chk_flag = true;
				}
			}

			// 建立 RETURN MAP
			return_map.put("CHK_FLAG", chk_flag);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	/**
	 * 檢核加班時間是否有大於三十分
	 * @param conn 
	 * @param over_start_datetime
	 * @param over_end_datetime
	 * @param over_break_start_datetime
	 * @param over_break_end_datetime
	 * @param comp_id 
	 * @return
	 */
	
	public Map checkOvertime(Connection conn, String over_start_datetime,	String over_end_datetime, String over_break_start_datetime,	String over_break_end_datetime, String comp_id) {

		BA_TOOLS tools = BA_TOOLS.getInstance();
		Map return_map = new HashMap();
		boolean chk_flag = false;

		try {
			
			//取得最低付薪時數
			float min_pay_hour = Float.parseFloat(tools.getSysParam(conn, comp_id, "MIN_PAY_HOUR"));
			
			// 處理加班時間資訊
			//加班開始時間
			Calendar ov_start_cal = tools.covertStringToCalendar(over_start_datetime, "yyyy/MM/dd HH:mm:ss");
			//加班休息時間
			Calendar ov_end_cal = tools.covertStringToCalendar(over_end_datetime, "yyyy/MM/dd HH:mm:ss");
			//加班中間休息開始時間
			Calendar ov_brk_start_cal = tools.covertStringToCalendar(over_break_start_datetime, "yyyy/MM/dd HH:mm:ss");
			//加班中間休息結束時間
			Calendar ov_brk_end_cal = tools.covertStringToCalendar(over_break_end_datetime, "yyyy/MM/dd HH:mm:ss");

			long beforenooninmillis = ov_end_cal.getTimeInMillis()-ov_start_cal.getTimeInMillis();  
			
			long hourinmillis = (long) (min_pay_hour * 60 * 60 * 1000);  //轉換為微秒
			// 判斷加班時間區間是否異常
			if (beforenooninmillis<hourinmillis) {
				// 表示加班區間小於30分鐘
				chk_flag = true;
			} 

			// 建立 RETURN MAP
			return_map.put("CHK_FLAG", chk_flag);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_map;
	}
	
	
	
	
	
	/**
	 * 檢查加班區間資料是否有重複
	 * @param dept_id
	 * @param employee_id
	 * @param over_start_datetime
	 * @param over_end_datetime
	 * @param comp_id
	 * @return
	 */
	public Map checkConflictOvertimeData(String dept_id, String employee_id, 
										 String over_start_datetime, String over_end_datetime, 
										 String comp_id){
		
		Map return_map = new HashMap();
		boolean chk_flag = false;
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF020800T1_01, EHF020800T1_03, " +
			" EHF020800T1_02, " +
			//" EHF020800T1_05, " +
			" EHF020800T1_04 " +
			" FROM EHF020800T1 " +
			" LEFT JOIN ehf020800t0 ON ehf020800t0_01 = ehf020800t1_01 AND ehf020800t0_10 = ehf020800t1_10" +
			" WHERE 1=1 " +
			" AND EHF020800T1_04 = '"+employee_id+"' " +  //加班員工
			" AND EHF020800T1_10 = '"+comp_id+"' " + //公司代碼
			" AND EHF020800T0_09 != 10" ;   //已作廢的單子不算
			
			sql += "" +
			//同一天可能會有兩張以上的加班單,所以必須依據實際的加班日期時間進去區間判斷, 避免加班資料重複的狀況發生
			" AND ( '"+over_start_datetime+"' BETWEEN EHF020800T1_06 AND EHF020800T1_07 " +  //加班區間判斷
			"      OR '"+over_end_datetime+"' BETWEEN EHF020800T1_06 AND EHF020800T1_07 " +
			"      OR EHF020800T1_06 BETWEEN '"+over_start_datetime+"' AND '"+over_end_datetime+"' " +
			"      OR EHF020800T1_07 BETWEEN '"+over_start_datetime+"' AND '"+over_end_datetime+"' ) ";
			
			//執行SQL
			Map dataMap = this.base_tools.query(sql);
			
			if(!dataMap.isEmpty()){
				//有找到資料, 表示加班區間資料重複
				chk_flag = true;
			}
			
			//建立 RETURN MAP
			return_map.put("CHK_FLAG", chk_flag);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	@Override
	public void addDetailData(String detail_type, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		try{
			//AddDetail
			if("EHF020800T1".equals(detail_type)){
				//新加班明細資訊
				this.addEHF020800T1(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增加班明細
	 * @param detailDataMap
	 */
	private void addEHF020800T1(Map detailDataMap){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		String show_sql = "";
		
		try{
			//取得順序號碼
			int EHF020800T1_03 = 
			this.base_tools.getMaxSN("EHF020800T1_03", "EHF020800T1", 
			" AND EHF020800T1_01 = '"+(String)detailDataMap.get("EHF020800T1_01")+"' " +
			" AND EHF020800T1_10 = '"+(String)detailDataMap.get("COMP_ID")+"' ");
			
			//新增加班明細
			sql = "" +
			" INSERT INTO EHF020800T1 " +
			" ( " +
			" EHF020800T1_01, EHF020800T1_02, " +
			" EHF020800T1_03, EHF020800T1_04," +
			//" EHF020800T1_05, " +
			" EHF020800T1_06, EHF020800T1_07, EHF020800T1_08, " +
			" EHF020800T1_06_BRK, EHF020800T1_07_BRK, EHF020800T1_08_DE, " +
			" EHF020800T1_09, " +
			" EHF020800T1_10, " +
			" EHF020800T1_11," +
			" EHF020800T1_12, " +
			" EHF020800T1_13, " +
			" NOON_LIMITED ," +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_CREATE, DATE_UPDATE " +
			" ) " +
			" VALUES ( " +
			" ?, ?, " +
			" ?, ?," +
			//" ?, " +
			" ?, ?, ?, " +
			" ?, ?, ?, " +
			" ?, " +
			" ?, " +
			" ?," +
			" ?, " +
			" ?, " +
			" ?, " +
			" ?, ?, 1, NOW(), NOW() " +
			" ) ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,
					pstmt, null, sql);
			int indexid = 1;
			
			//加班資料UID
			String EHF020800T1_02 = "";
			//使用 CodeRules 做單號取得的動作
			//EHF020800T1_02 = EMS_GetCodeRules.getInstance().getCodeRule("EHF020800T1", (String)detailDataMap.get("COMP_ID"));
			EHF020800T1_02 = tools.createEMSUID(this.base_tools.getConn(), "EOV", "EHF020800T1", "EHF020800T1_02", (String)detailDataMap.get("COMP_ID"));
			
			p6stmt.setString(indexid, 
			(String)detailDataMap.get("EHF020800T1_01")==null?(String)detailDataMap.get("EHF020800T0_01")
			:(String)detailDataMap.get("EHF020800T1_01"));  //加班表單編號
			indexid++;
			p6stmt.setString(indexid, EHF020800T1_02);  //加班資料UID
			indexid++;
			p6stmt.setInt(indexid, EHF020800T1_03);  //資料序號
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_04"));  //加班員工工號
			indexid++;
			//p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_05"));  //加班員工部門組織(代號)
			//indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_06"));  //加班開始時間
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_07"));  //加班結束時間
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_08"));  //加班時數
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_06_BRK"));  //加班休息開始時間
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_07_BRK"));  //加班休息結束時間
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_08_DE"));  //加班內扣時數
			indexid++;
			
			p6stmt.setString(indexid, (String)detailDataMap.get("EHF020800T1_09")==null?"":(String)detailDataMap.get("EHF020800T1_09"));  //加班事由
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			if("01".equals((String)detailDataMap.get("EHF020800T1_11"))||"換錢".equals((String)detailDataMap.get("EHF020800T1_11"))){
				p6stmt.setString(indexid, "01");  //加班時數處理方式  20131007新增   BY 賴泓錡
				indexid++;
			}else if("02".equals((String)detailDataMap.get("EHF020800T1_11"))||"換休".equals((String)detailDataMap.get("EHF020800T1_11"))){
				p6stmt.setString(indexid, "02");  //加班時數處理方式  20131007新增   BY 賴泓錡
				indexid++;
			}
			p6stmt.setFloat(indexid, (Float)detailDataMap.get("EHF020800T1_12"));  
			indexid++;
			

			if("01".equals((String)detailDataMap.get("EHF020800T1_13"))||"例假日".equals((String)detailDataMap.get("EHF020800T1_13"))){
				p6stmt.setString(indexid, "01");  //加班類別  20170221新增   BY 吳逸群
				indexid++;
			}else if("02".equals((String)detailDataMap.get("EHF020800T1_13"))||"休息日".equals((String)detailDataMap.get("EHF020800T1_13"))){
				p6stmt.setString(indexid, "02");  //加班類別  20170221新增   BY 吳逸群
				indexid++;
			}else if("03".equals((String)detailDataMap.get("EHF020800T1_13"))||"國定假日".equals((String)detailDataMap.get("EHF020800T1_13"))){
				p6stmt.setString(indexid, "03");  //加班類別  20170221新增   BY 吳逸群
				indexid++;
			}else if("00".equals((String)detailDataMap.get("EHF020800T1_13"))||"平日".equals((String)detailDataMap.get("EHF020800T1_13"))){
				p6stmt.setString(indexid, "00");
				indexid++;
			}else{
				p6stmt.setString(indexid, "");  //加班類別  20170223新增   BY 吳逸群
				indexid++;
			}
			
			p6stmt.setString(indexid, (String)detailDataMap.get("NOON_LIMITED")==null?"":(String)detailDataMap.get("NOON_LIMITED"));  //加班事由
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //資料建立者
			indexid++;
			p6stmt.setString(indexid, (String)detailDataMap.get("USER_NAME"));  //資料修改者
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			
			//更新資料庫
			this.base_tools.commit();
			
			pstmt.close();
			p6stmt.close();
			
			//記錄細項新增訊息
			this.base_tools.writeADDMSG("EHF020800().addEHF020800T1()", show_sql, 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄細項新增錯誤訊息
			this.base_tools.writeADDERRMSG("EHF020800().addEHF020800T1()", show_sql+", "+e.getMessage(), 
							detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
							detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void delDetailData(String detail_type, Map detailDataMap) {
		// TODO Auto-generated method stub
		try{
			if("EHF020800T1".equals(detail_type)){
				//刪除加班明細資訊
				this.delEHF020800T1(detailDataMap);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 刪除加班明細資料
	 * @param detailDataMap
	 */
	private void delEHF020800T1(Map detailDataMap){

		String sql = "";
		
		try{
			//刪除加班明細資料
			sql = "" +
			" DELETE FROM EHF020800T1 " +
			" WHERE 1=1 " +
			" AND EHF020800T1_02 = '"+(String)detailDataMap.get("EHF020800T1_02")+"' " +  //加班資料UID
			" AND EHF020800T1_10 = '"+(String)detailDataMap.get("COMP_ID")+"' ";  //公司代碼
			
			//執行刪除
			int dataCount = this.base_tools.delete(sql);
			
			//記錄刪除訊息
			this.base_tools.writeDELETEMSG("EHF020800().delEHF020800T1()",
										   sql+" total delete "+dataCount+"rows data", 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			//重排加班明細的順序號碼
			String[] key = {"EHF020800T1_02","EHF020800T1_10"};
			this.base_tools.reDoSN(
			key, 
			" EHF020800T1_03", "EHF020800T1", 
			" AND EHF020800T1_01 = '"+(String)detailDataMap.get("EHF020800T1_01")+"' " +
			" AND EHF020800T1_10 = '"+(String)detailDataMap.get("COMP_ID")+"' ");
			
			//更新資料庫
			this.base_tools.commit();
			
		}catch(Exception e){
			
			//記錄刪除訊息
			this.base_tools.writeDELETEERRMSG("EHF020800().delEHF020800T1()",
											  sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}

	@Override
	public void saveDetailData(String detail_type, Map detailDataMap) {
		// TODO Auto-generated method stub
		try{
			if("EHF020800T1".equals(detail_type)){
				//更新加班明細資訊
				this.saveEHF020800T1(detailDataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新加班明細資訊
	 * @param detailDataMap
	 */
	private void saveEHF020800T1(Map detailDataMap){

		String sql = "";
		String show_sql = "";
		
		try{
			
			
			//記錄細項更新訊息
			this.base_tools.writeUPDATEMSG("EHF020800().saveEHF020800T1()", 
										   show_sql, 
										   detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
										   detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF020800().saveEHF020800T1()", 
											  show_sql+", "+e.getMessage(), 
											  detailDataMap.containsKey("USER_NAME")==true?((String)detailDataMap.get("USER_NAME")):"",
											  detailDataMap.containsKey("COMP_ID")==true?((String)detailDataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	public Map queryDetailData(String detail_type, Map detailDataMap) {
		// TODO Auto-generated method stub
		
		Map return_map = null;
		
		try{
			if("EHF020800T1".equals(detail_type)){
				//查詢加班明細資訊
				this.queryEHF020800T1(detailDataMap);
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return return_map;
	}
	
	/**
	 * 查詢加班明細
	 * @param detailDataMap
	 * @return
	 */
	private Map queryEHF020800T1(Map detailDataMap){
		
		Map EHF020800T1 = new HashMap();
		String sql = "";
		
		try{
			//Query 加班明細資訊
			sql = "" +
			" SELECT " +
			" * " +
			" FROM EHF020800T1 " +
			" WHERE 1=1 " +
			" AND EHF020800T1_02 = '"+(String)detailDataMap.get("EHF020800T1_02")+"' " +  //加班資料UID
			" AND EHF020800T1_10 = '"+(String)detailDataMap.get("COMP_ID")+"' " +  //公司代碼
			" ";
			
			//執行查詢
			EHF020800T1 = this.base_tools.query(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF020800T1;
	}
	
	/**
	 * 確認
	 * @param dataMap
	 */
	public void confirm(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF020800T0 " +
			" SET " +
			" EHF020800T0_09 = '03', " +
			" EHF020800T0_09_DESC = '已確認' " +
			" WHERE 1=1 " +
			" AND EHF020800T0_01 = ? " +  //加班表單編號
			" AND EHF020800T0_10 = ? ";  //公司代碼
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_01"));  //加班表單編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
			//執行加班資料連動
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			
			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			
			//取得加班記錄 use 加班單
			overtime_list = this.queryOVList(dataMap);
			
			//連動加班記錄
			ems_ov_ctrl.putOvertimeRecord(overtime_list);
			
			//關閉加班記錄連動元件
			ems_ov_ctrl.close();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF020800().confirm()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF020800().confirm()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 還原
	 * @param dataMap
	 */
	public void recovery(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF020800T0 " +
			" SET " +
			" EHF020800T0_09 = '01', " +
			" EHF020800T0_09_DESC = '填寫中' " +
			" WHERE 1=1 " +
			" AND EHF020800T0_01 = ? " +
			" AND EHF020800T0_10 = ? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_01"));  //加班表單編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
			//執行加班資料連動
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			
			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			
			//取得加班記錄 use 加班單
			overtime_list = this.queryOVList(dataMap);
			
			//連動加班記錄
			ems_ov_ctrl.removeOvertimeRecord(overtime_list);
			
			//關閉加班記錄連動元件
			ems_ov_ctrl.close();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF020800().recovery()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF020800().recovery()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 作廢
	 * @param dataMap
	 */
	public void remove(Map dataMap) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		
		try{
			//Update
			sql = "" +
			" UPDATE EHF020800T0 " +
			" SET " +
			" EHF020800T0_09 = '10', " +
			" EHF020800T0_09_DESC = '作廢' " +
			" WHERE 1=1 " +
			" AND EHF020800T0_01 = ? " +
			" AND EHF020800T0_10 = ? ";
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF020800T0_01"));  //加班表單編號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("COMP_ID"));  //公司代碼
			indexid++;
			
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			show_sql = p6stmt.getQueryFromPreparedStatement();
			//執行
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			
			//更新資料庫
			this.base_tools.commit();
			
			//執行加班資料連動
			//建立加班記錄連動元件
			EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
			
			//建立加班記錄List
			List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
			
			//取得加班記錄 use 加班單
			overtime_list = this.queryOVList(dataMap);
			
			//連動加班記錄
			ems_ov_ctrl.removeOvertimeRecord(overtime_list);
			
			//關閉加班記錄連動元件
			ems_ov_ctrl.close();
			
			//記錄更新訊息
			this.base_tools.writeUPDATEMSG("EHF020800().recovery()", 
										   show_sql, 
										   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
										   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
		}catch(Exception e){
			
			//記錄更新錯誤訊息
			this.base_tools.writeUPDATEERRMSG("EHF020800().recovery()", 
											  show_sql+", "+e.getMessage(), 
											  dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
											  dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
	}
	
	/**
	 * 查詢加班資料清單 for 加班資料連動用
	 * @param queryCondMap
	 * @return
	 */
	public List<Map<String, String>> queryOVList(Map queryCondMap){
		
		List<Map<String, String>> OV_LIST = new ArrayList<Map<String, String>>();
		
		try{
			//Query 加班明細資訊
			String sql = "" +
			" SELECT " +
			" EHF020800T1_04 AS EMPLOYEE_ID, " +
			" EHF020800T1_02 AS OV_DATA_UID, " +
			" DATE_FORMAT(EHF020800T0_06, '%Y/%m/%d') AS OV_DATE, " +
			" DATE_FORMAT(EHF020800T1_06, '%Y/%m/%d %H:%i:%s') AS OV_START_TIME, " +
			" DATE_FORMAT(EHF020800T1_07, '%Y/%m/%d %H:%i:%s') AS OV_END_TIME, " +
			" (CAST(EHF020800T1_08 AS CHAR(10))) AS OV_HOURS, " +
			" DATE_FORMAT(EHF020800T1_06_BRK, '%Y/%m/%d %H:%i:%s') AS OV_BREAK_START_TIME, " +
			" DATE_FORMAT(EHF020800T1_07_BRK, '%Y/%m/%d %H:%i:%s') AS OV_BREAK_END_TIME, " +
			" (CAST(EHF020800T1_08_DE AS CHAR(10))) AS OV_DE_HOURS, " +
			" EHF020800T1_10 AS COMP_ID, " +
			" EHF020800T1.USER_UPDATE AS USER_NAME, " +//" '"+(String)queryCondMap.get("USER_ID")+"' AS USER_ID, " +
			" EHF020800T1_11 AS EHF020800T1_11 ," +//加班時數處理方式  20131007新增   BY 賴泓錡
			" EHF020800T1_12 AS EHF020800T1_12 ," +//中午加班時數
			" EHF020800T1_13 AS EHF020800T1_13  " +//加班類別(00=平日 01=例假，02=休息，03=國定)
			" FROM EHF020800T1 " +
			" LEFT JOIN EHF020800T0 ON EHF020800T0_01 = EHF020800T1_01 " +
			" AND EHF020800T0_10 = EHF020800T1_10 " +
			" WHERE 1=1 " ;
			if(!"".equals((String)queryCondMap.get("EHF020800T0_12"))&&(String)queryCondMap.get("EHF020800T0_12")!=null){
				sql +=" AND EHF020800T0_12 = '"+(String)queryCondMap.get("EHF020800T0_12")+"' " ;
			}
			sql +=" AND EHF020800T1_01 = '"+(String)queryCondMap.get("EHF020800T0_01")+"' " +
			" AND EHF020800T1_10 = '"+(String)queryCondMap.get("COMP_ID")+"' " +
			" AND NOON_LIMITED NOT IN ('on')" +
			" ORDER BY EHF020800T1_03 ";
			
			OV_LIST = this.base_tools.queryList(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return OV_LIST;
	}
	
	/**
	 * 重產考勤
	 * @param conn
	 * @param att_time
	 * @param name
	 * @param CompId
	 * @param UserId
	 */
	public void Again_Produce_att(Connection conn, String att_time, String name, String CompId, String UserId) {
		
		try{
			Calendar cur_system_cal = Calendar.getInstance();
			// 設定系統時間
			cur_system_cal.set(Calendar.HOUR_OF_DAY, 8);
			cur_system_cal.set(Calendar.MINUTE, 0);
			cur_system_cal.set(Calendar.SECOND, 0);
			cur_system_cal.set(Calendar.MILLISECOND, 0);
			HR_TOOLS hr_tools = new HR_TOOLS();
				
			// TODO Auto-generated method stub
			String time[]=att_time.split("/");
			Calendar cur_schdule_cal = (Calendar) cur_system_cal.clone(); // 複製當前系統Calendar
			cur_schdule_cal.set(Calendar.YEAR, Integer.valueOf(time[0]));
			cur_schdule_cal.set(Calendar.MONTH, Integer.valueOf(time[1]) - 1);
			cur_schdule_cal.set(Calendar.DAY_OF_MONTH,Integer.valueOf(time[2]));
		
			//=======================20131018 新增重產考勤   BY賴泓錡   
			if (cur_schdule_cal.compareTo(cur_system_cal) < 0) {
				// 在系統日期之前
				// 建立考勤元件
				EMS_AttendanceAction att = EMS_AttendanceAction.getInstance(CompId, "",	UserId);
				//att.setSta_cur_day(this.getSpecifiedDayBefore(att_time));//從產加班日期前一天的考勤
				// 重產考勤
				//att.execute_Attendance(this.base_tools.getConn(), true, name);
				//取得公司名稱
				Map compMap = (Map)hr_tools.getCompNameMap(conn,CompId);
				//員工Map
				Map empMap = hr_tools.getEmpNameMap(CompId);
				//部門Map
				Map depMap = hr_tools.getDepNameMap(CompId);
				//職務
				Map titleMap = hr_tools.getTitleNameMap(CompId);
			
				hr_tools.close();
			
				Map WorkSchedule= new HashMap();
			
				//產生預排換班處理元件
				EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
			
				ems_wsc_tools.sortoutList("",name,empMap,depMap);
			
				//取得排班表與行事曆
				ems_wsc_tools.getVacations(conn, empMap, depMap, CompId, att_time,"", WorkSchedule);
			
				att.setWorkSchedule(WorkSchedule);

			
				att.setSta_cur_day(att_time);//從產加班日期的考勤
				// 重產考勤
				att.execute_Attendance(true, name);
			
			}else{
				// 在系統日期之後
			
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	 /**
	  * 取得前一天的時間
	  * @param specifiedDay
	  * @return
	  */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		Calendar time = Calendar.getInstance();
		Date date = null;
		String dayAfter = "";
		try {
			date = new SimpleDateFormat("yy/MM/dd").parse(specifiedDay);
			time.setTime(date);
			int day = time.get(Calendar.DATE);
			time.set(Calendar.DATE, day - 1);
			dayAfter = new SimpleDateFormat("yyyy/MM/dd").format(time.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dayAfter;
	}
	
	
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		this.base_tools.close();
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return this.base_tools.isClosed();
	}
	
	
	
	public  boolean getEHF020800T0(Map dataMap,String COMP_ID) {
		boolean chk_flag = false;
		Statement stmt =null;
		ResultSet rs = null;
		try {
		String sql =" SELECT * FROM EHF020800T0 WHERE 1=1" +
		" AND EHF020800T0_06 = '"+(String)dataMap.get("EHF020800T0_06_Year")+
				"/"+(String)dataMap.get("EHF020800T0_06_Month")+
				"/"+(String)dataMap.get("EHF020800T0_06_Day")+"' " +  
		" AND EHF020800T0_11 = '"+(String)dataMap.get("EHF020800T0_11")+"' " + 
		" AND EHF020800T0_12 = '"+(String)dataMap.get("EHF020800T0_12")+"' " + 
		" AND EHF020800T0_10 = '"+COMP_ID+"' "+
		" AND EHF020800T0_09 = '01'"; 
		
		 stmt = this.base_tools.getConn().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);			
		 rs = stmt.executeQuery(sql);
			if (rs.next()) {
				dataMap.put("EHF020800T0_09", rs.getString("EHF020800T0_09"));
				dataMap.put("EHF020800T1_01", rs.getString("EHF020800T0_01"));
				dataMap.put("EHF020800T0_01", rs.getString("EHF020800T0_01"));
				dataMap.put("chk_flag", "true");
				chk_flag=true;
			} else {
				dataMap.put("chk_flag", "false");
			}
		 rs.close();
		stmt.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return chk_flag;
	}

	
	
	
	public boolean chk_List(List list_1,String EHF020800T0_06) {
		// TODO Auto-generated method stub
		boolean chk_flag = false;
		if(list_1.size()<=0){
			return chk_flag;
		}
		
		for(int i=0;i<list_1.size();i++){
			Map map=(Map)list_1.get(i);
			if(EHF020800T0_06.equals(map.get("EHF020800T0_06"))){
				chk_flag=true;	
				// 結束迴圈
				break;	
			}
		}
		return chk_flag;
	}
	
	/**
	 * 呈核
	 * @param queryCondMap
	 * @param comp_id
	 * @param user_name
	 * @return
	 */
	public boolean submitDatas(Map queryCondMap, String comp_id, String user_name) {
		// TODO Auto-generated method stub
		
		boolean confirm = false;
		
		/*String sql = "UPDATE EHF020200T0 SET EHF020200T0_16='0006', EHF020200T0_16_DESC='完成', " +
					 "VERSION=VERSION+1, USER_UPDATE = '"+user_name+"', DATE_UPDATE = NOW() " +
				  	 "WHERE EHF020200T0_01 = '"+(String)queryCondMap.get("EHF020200T0_01")+"' AND EHF020200T0_18 = '"+comp_id+"' ";*/
		
		String sql = "UPDATE EHF020800T0 SET EHF020800T0_09='06', EHF020800T0_09_DESC='呈核', " +
		 			 "VERSION=VERSION+1, USER_UPDATE = '"+user_name+"', DATE_UPDATE = NOW() " +
		 			 "WHERE EHF020800T0_01 = '"+(String)queryCondMap.get("EHF020800T0_01")+"' AND EHF020800T0_10 = '"+comp_id+"' ";
		
		if(base_tools.update(sql)>0){
			confirm = true;
			
			//更新資料庫
			this.base_tools.commit();
		}
		
		return confirm;
	}

	public void updateFlowNo(String form_no, String flow_no, String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			String sql = "UPDATE EHF020800T0 SET EHF020800T0_02='"+flow_no+"' " +
						 "WHERE EHF020800T0_01 = '"+form_no+"' AND EHF020800T0_10 = '"+comp_id+"' ";
			
			if(base_tools.update(sql)>0){
				//更新資料庫
				this.base_tools.commit();
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public boolean submitFlowDatas(Map queryCondMap, String comp_id, String user_name) {
		// TODO Auto-generated method stub
		
		boolean confirm = false;
		
		String sql = "UPDATE EHF020800T0 SET EHF020800T0_09='"+(String)queryCondMap.get("EHF020800T0_09")+"', " +
					 "EHF020800T0_09_DESC='"+(String)queryCondMap.get("EHF020800T0_09_DESC")+"', " +
		 			 "VERSION=VERSION+1, USER_UPDATE = '"+user_name+"', DATE_UPDATE = NOW() " +
		 			 "WHERE EHF020800T0_01 = '"+(String)queryCondMap.get("EHF020800T0_01")+"' AND EHF020800T0_10 = '"+comp_id+"' ";
		
		if(base_tools.update(sql)>0){
			confirm = true;
			
			//更新資料庫
			this.base_tools.commit();
		}
		
		return confirm;
	}
	
	/**
	 * 更新LOGS的flow_no
	 * @param ehf020200t0_01
	 * @param flowNo
	 * @param compId
	 */
	public void updateFlowLogs(String form_no, String flow_no,
			String comp_id) {
		// TODO Auto-generated method stub
		
		try{
			String sql = "UPDATE SFLOW_LOG_T0 SET SFLOW_LOG_T0_01='"+flow_no+"' " +
						 "WHERE SFLOW_LOG_T0_02 = '"+form_no+"' AND SFLOW_LOG_T0_14 = '"+comp_id+"' ";
			
			if(base_tools.update(sql)>0){
				//更新資料庫
				this.base_tools.commit();
			}			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 有無流程空表單編號
	 * @param form_no
	 * @param comp_id
	 * @return 無：false，有：true
	 */
	public boolean chkFlowNo(String form_no, String comp_id) {
		// TODO Auto-generated method stub
		
		Map dataMap = null;
		boolean confirm = false;
		
		try{
			String sql = "" +
			" SELECT EHF020800T0_02 " +
			" FROM EHF020800T0 " +
			" WHERE 1=1 " +
			" AND EHF020800T0_01 = '"+form_no+"' " +
			" AND EHF020800T0_10 = '"+comp_id+"' ";
			
			dataMap = this.base_tools.query(sql);
			
			if("".equals((String)dataMap.get("EHF020800T0_02"))){
				confirm = false;
			}else{
				confirm = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return confirm;
	}
	
}
