package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMessage;

import com.spon.ems.util.overtime.EMS_Overtime_Record_Control;
import com.spon.ems.vacation.models.EHF020800;
import com.spon.ems.vacation.models.EHF020801;
import com.spon.ems.vacation.tools.EMS_AttendanceAction;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.spon.utils.work_schedule.EMS_Work_ScheduleForm;
import com.spon.utils.work_schedule.EMS_Work_Schedule_Table;

import vtgroup.ems.common.vo.AuthorizedBean;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 加班單匯入元件
 * EHF020800M0A
 * @author SPONPC2
 * 
 */

public class IMP_EHF020800 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS{

	
	private AuthorizedBean authbean = null;
	private String user_emp_id = "";
	//private String user_dept_id = "";
	//private Map empDepInf = null;
	private Map depMap = null;
	private Map empMap = null;
	private HttpServletRequest request= null;
	//取得驗證工具
	private IMP_Verification Verification=null;
	private EMS_Work_ScheduleForm cur_emp_work_schedule = null;
	
	public IMP_EHF020800( String user_emp_id, AuthorizedBean authbean, HttpServletRequest request,Map depMap,Map empMap) {
			
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;
		//this.user_dept_id = user_dept_id;
		this.authbean = authbean;
		//this.empDepInf = empDepInf;//取得員工的主要歸屬部門代號
		this.depMap = depMap;// 取得部門資訊
		this.empMap = empMap;// 取得員工資訊
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
			for( int i=4; i<st.getRows(); i++ ){
				
				dataMap = new LinkedHashMap<String,String>();
				
				//所有欄位皆空白就跳出該迴圈
				if(
				   "".equals(st.getCell( 0, i).getContents())&&"".equals(st.getCell( 1, i).getContents())
				 &&"".equals(st.getCell( 2, i).getContents())&&"".equals(st.getCell( 3, i).getContents())
				 &&"".equals(st.getCell( 4, i).getContents())&&"".equals(st.getCell( 5, i).getContents())
				 &&"".equals(st.getCell( 6, i).getContents())&&"".equals(st.getCell( 7, i).getContents())
				 &&"".equals(st.getCell( 8, i).getContents())&&"".equals(st.getCell( 9, i).getContents())
				 &&"".equals(st.getCell(10, i).getContents())&&"".equals(st.getCell(11, i).getContents())
				 &&"".equals(st.getCell(12, i).getContents())&&"".equals(st.getCell(13, i).getContents())
				 &&"".equals(st.getCell(14, i).getContents())&&"".equals(st.getCell(15, i).getContents())
				 &&"".equals(st.getCell(16, i).getContents())&&"".equals(st.getCell(17, i).getContents())
				 &&"".equals(st.getCell(18, i).getContents())&&"".equals(st.getCell(19, i).getContents())
				 &&"".equals(st.getCell(20, i).getContents())&&"".equals(st.getCell(21, i).getContents())
				 )
				{
					continue;
				}

				dataMap = new LinkedHashMap<String,String>();
				dataMap.put("EHF020800T0_06_Year", 	st.getCell( 0, i).getContents().trim());  //加班考勤日期(年)
				dataMap.put("EHF020800T0_06_Month", st.getCell( 1, i).getContents().trim());  //加班考勤日期(月)
				dataMap.put("EHF020800T0_06_Day", 	st.getCell( 2, i).getContents().trim());  //加班考勤日期(日)
				dataMap.put("EHF020800T0_11", 		st.getCell( 3, i).getContents().trim());  //申請部門(代號)
				dataMap.put("EHF020800T1_04",		st.getCell( 4, i).getContents().trim());  //申請人員工工號
				dataMap.put("EHF020800T1_04_name", 	st.getCell( 5, i).getContents().trim());  //申請人員工姓名
				dataMap.put("EHF020800T1_06_Year", 	st.getCell( 6, i).getContents().trim());  //加班日期(起)(年)
				dataMap.put("EHF020800T1_06_Month", st.getCell( 7, i).getContents().trim());  //加班日期(起)(月)
				dataMap.put("EHF020800T1_06_Day", 	st.getCell( 8, i).getContents().trim());  //加班日期(起)(日)
				dataMap.put("EHF020800T1_06_HH", 	st.getCell( 9, i).getContents().trim());  //加班日期(起)(時)
				dataMap.put("EHF020800T1_06_MM", 	st.getCell(10, i).getContents().trim());  //加班日期(起)(分)
				dataMap.put("EHF020800T1_07_Year", 	st.getCell(11, i).getContents().trim());  //加班日期(迄)(年)
				dataMap.put("EHF020800T1_07_Month", st.getCell(12, i).getContents().trim());  //加班日期(迄)(月)
				dataMap.put("EHF020800T1_07_Day", 	st.getCell(13, i).getContents().trim());  //加班日期(迄)(日)
				dataMap.put("EHF020800T1_07_HH",	st.getCell(14, i).getContents().trim());  //加班日期(迄)(時)
				dataMap.put("EHF020800T1_07_MM", 	st.getCell(15, i).getContents().trim());  //加班日期(迄)(分)
				dataMap.put("EHF020800T1_06_BRK_HH",st.getCell(16, i).getContents().trim());  //休息時間(起)(時)
				dataMap.put("EHF020800T1_06_BRK_MM",st.getCell(17, i).getContents().trim());  //休息時間(起)(分)
				dataMap.put("EHF020800T1_07_BRK_HH",st.getCell(18, i).getContents().trim());  //休息時間(迄)(時)
				dataMap.put("EHF020800T1_07_BRK_MM",st.getCell(19, i).getContents().trim());  //休息時間(迄)(分)
				//dataMap.put("EHF020800T1_09", 		st.getCell(20, i).getContents().trim());  //事由
				dataMap.put("EHF020800T1_11", 		st.getCell(20, i).getContents().trim());  //加班時數處理方法
				dataMap.put("EHF020800T1_09", 		st.getCell(21, i).getContents().trim());  //事由
				xlsdatalist.add(dataMap);
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;
	}

	@Override
	protected Map fileimport(Connection conn, List datalist, String owner,String compId) {

		Map msgMap = new HashMap();
		Map dataMap = null;
		boolean chk_flag = false;
		//Map getMap=this.getMap(conn);
		BA_TOOLS tools = BA_TOOLS.getInstance();
		//建立EHF020800元件
		EHF020800 ehf020800 = new EHF020800(compId);
		
		HR_TOOLS hr_tools = new HR_TOOLS();
		
		List DAY_List = new ArrayList();//紀錄考勤日期
		
		try{
			
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;

			Iterator it = datalist.iterator();
			
			while(it.hasNext()){

				Statement stmt =null;
				ResultSet rs = null;
				String sql = "";
				String dep_id = "";
				String emp_id = "";
				//取得資料
				dataMap = (Map) it.next();
				//設定填單人資訊
				//Map empMap = ems_tools.getEmpInfMapByEmpId(authbean.getUserId(), authbean.getEmployeeID(),  authbean.getCompId());
				//Map depMap = ems_tools.getEmpBelongMainDepInf(authbean.getUserId(), authbean.getEmployeeID(),  authbean.getCompId(), "" );
				Map empMap = hr_tools.getEmpNameMap(compId);
				
				//加班日期(考勤日期)
				String EHF020800T0_06=(String)dataMap.get("EHF020800T0_06_Year")+"/"+(String)dataMap.get("EHF020800T0_06_Month")+"/"+(String)dataMap.get("EHF020800T0_06_Day");
				//加班開始時間
				String EHF020800T1_06=(String)dataMap.get("EHF020800T1_06_Year")+"/"+(String)dataMap.get("EHF020800T1_06_Month")+"/"+(String)dataMap.get("EHF020800T1_06_Day");//+" "+(String)dataMap.get("EHF020800T1_06_HH")+":"+(String)dataMap.get("EHF020800T1_06_MM")+":00";
				//加班結束時間
				String EHF020800T1_07=(String)dataMap.get("EHF020800T1_07_Year")+"/"+(String)dataMap.get("EHF020800T1_07_Month")+"/"+(String)dataMap.get("EHF020800T1_07_Day");//+" "+(String)dataMap.get("EHF020800T1_07_HH")+":"+(String)dataMap.get("EHF020800T1_07_MM")+":00";

				
				float min_pay_hour = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "MIN_PAY_HOUR"));
				//處理加班時間資訊
				Map overtime_datetime_map =ehf020800.composeOvertimeDate(EHF020800T1_06,(String)dataMap.get("EHF020800T1_06_HH")+(String)dataMap.get("EHF020800T1_06_MM"),EHF020800T1_07,(String)dataMap.get("EHF020800T1_07_HH")+(String)dataMap.get("EHF020800T1_07_MM"),  min_pay_hour);
				
				//處理加班休息時間資訊
				Map overtime_break_datetime_map = ehf020800.composeOvertimeBreakDate(EHF020800T1_06,EHF020800T1_07,(String)dataMap.get("EHF020800T1_06_HH")+(String)dataMap.get("EHF020800T1_06_MM"),(String)dataMap.get("EHF020800T1_07_HH")+(String)dataMap.get("EHF020800T1_07_MM"),(String)dataMap.get("EHF020800T1_06_BRK_HH")+(String)dataMap.get("EHF020800T1_06_BRK_MM"),(String)dataMap.get("EHF020800T1_07_BRK_HH")+(String)dataMap.get("EHF020800T1_07_BRK_MM"));
				
				//Set overtime_datetime data
				dataMap.putAll(overtime_datetime_map);
				
				//Set overtime break_datetime data
				dataMap.putAll(overtime_break_datetime_map);
				
				//填單人員工工號
				dataMap.put("EHF020800T0_03", (String)((Map)empMap.get(user_emp_id)).get("USER_CODE"));
				//填單人姓名
				dataMap.put("USER_NAME", (String)((Map)empMap.get(user_emp_id)).get("EMPLOYEE_NAME"));
				//填單人部門代號
				dataMap.put("EHF020800T0_04", (String)((Map)empMap.get(user_emp_id)).get("DEPT_ID"));
				//填單人部門名稱
				dataMap.put("EHF020800T0_04_TXT", (String)((Map)empMap.get(user_emp_id)).get("SHOW_DESC"));
				//填單日期
				dataMap.put("EHF020800T0_05", tools.ymdTostring(tools.getSysDate()));
				//加班日期(考勤日期)
				dataMap.put("EHF020800T0_06", EHF020800T0_06);
				//表單狀態
				dataMap.put("EHF020800T0_09", "03");
				//公司代號
				dataMap.put("COMP_ID", compId);
				
				dataMap.put("EHF020800T0_12", "02");
				
				//轉化代碼為系統內碼
				dep_id = tools.ShowIdtoRealId(conn, "EHF000200T0",(String)dataMap.get("EHF020800T0_11"), authbean.getCompId()); 
				emp_id = tools.ShowIdtoRealId(conn, "EHF010100T0",(String)dataMap.get("EHF020800T1_04"), authbean.getCompId());
				
				dataMap.put("EHF020800T0_11", dep_id);
				dataMap.put("EHF020800T1_04", emp_id);
				dataMap.put("EHF020800T1_12", (float)0.0);
				
				if (!ehf020800.chk_List(DAY_List, EHF020800T0_06)) {

					dataMap.put("EHF020800T0_09", "01");
					dataMap.put("EHF020800T0_09_DESC", "已確認");
					
					// 新增表頭
					ehf020800.addData(dataMap);
					dataMap.put("EHF020800T1_01", dataMap.get("KEY_ID"));
					dataMap.put("EHF020800T0_01", dataMap.get("KEY_ID"));
					// 執行新增資料
					ehf020800.addDetailData("EHF020800T1", dataMap);					
					
					Map Map  = new HashMap();//紀錄考勤日期
					//一張加班單一位員工
					Map.put("EHF020800T0_06", EHF020800T0_06);
					Map.put("KEY_ID", dataMap.get("KEY_ID"));
					DAY_List.add(Map);
					
				}
				else if (ehf020800.getEHF020800T0(dataMap, (String) request.getAttribute("compid"))) {
					// 不新增表頭，直接新增明細
					//dataMap.put("EHF020800T0_01", dataMap.get("KEY_ID"));
					// 執行新增資料
					ehf020800.addDetailData("EHF020800T1", dataMap);
					
					
				} /*else {
					dataMap.put("EHF020800T0_09", "03");
					dataMap.put("EHF020800T0_09_DESC", "已確認");
					// 新增表頭
					ehf020800.addData(dataMap);
					dataMap.put("EHF020800T1_01", dataMap.get("KEY_ID"));
					dataMap.put("EHF020800T0_01", dataMap.get("KEY_ID"));
					// 執行新增資料
					ehf020800.addDetailData("EHF020800T1", dataMap);
					
					//執行確認
					ehf020800.confirm(dataMap);
					
					//建立加班記錄List
					List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
					//建立加班記錄連動元件
					EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
					//取得加班記錄 use 加班單
					overtime_list = ehf020800.queryOVList(dataMap);
					
					
					Map<String, String> Map = null;
					Map<String, String> return_map = null;

					Iterator<Map<String, String>> list = overtime_list.iterator();

					while (list.hasNext()) {

						Map = list.next();
						//轉換Map Data
						Map = ems_ov_ctrl.mappingOVMapData(Map);
						//重產考勤
						ehf020800.Again_Produce_att(conn,Map.get("EHF020801T0_04"),Map.get("EHF020801T0_02"),authbean.getCompId(),authbean.getUserId());
					}
				}*/

				dataCount++;
				
				
				
				
			}
			
			for (int i = 0; i < DAY_List.size(); i++) {
				
				Map getMap=(Map)DAY_List.get(i);

				// 建立加班記錄List
				List<Map<String, String>> overtime_list = new ArrayList<Map<String, String>>();
				// 建立加班記錄連動元件
				EMS_Overtime_Record_Control ems_ov_ctrl = new EMS_Overtime_Record_Control();
				dataMap.put("EHF020800T0_01", getMap.get("KEY_ID"));
				dataMap.put("COMP_ID", compId);
				
				//執行確認
				ehf020800.confirm(dataMap);
				// 取得加班記錄 use 加班單
				overtime_list = ehf020800.queryOVList(dataMap);

				Map<String, String> Map = null;
				Map<String, String> return_map = null;

				Iterator<Map<String, String>> list = overtime_list.iterator();

				while (list.hasNext()) {

					Map = list.next();
					// 轉換Map Data
					Map = ems_ov_ctrl.mappingOVMapData(Map);
					// 重產考勤
					ehf020800.Again_Produce_att(conn,Map.get("EHF020801T0_04"), Map.get("EHF020801T0_02"), authbean.getCompId(), authbean.getUserId());
					//針對加班處理方式處理
					if("02".equals(Map.get("EHF020801T0_09"))){//換休->換補休
						//換休當年度可以使用到隔年３月底
						EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						setVaSql.updataOverTimeVacation("0006", Map, authbean);
					}
				}
				ems_ov_ctrl.close();
			}
			
			//建立回傳訊息Map
			msgMap.put("DATALIST", datalist);
			msgMap.put("DATACOUNT", dataCount);
		
			// 關閉EHF020800元件
			ehf020800.close();		
			hr_tools.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return msgMap;
	}

	/**
	 * 檢核匯入資料
	 */
	@Override
	protected Map chkImportDataList(Connection conn, List datalist,	String compId) {
		// TODO Auto-generated method stub
		BA_TOOLS tools = BA_TOOLS.getInstance();
		BaseFunction base_tools = new BaseFunction(compId);
		//建立EHF020800元件
		EHF020800 ehf020800 = new EHF020800();
		Map dataMap = null;
		Map errMsgMap = null;
		Map tempClassMap = null;

		Map overtime_datetime_map = null;
		Map overtime_break_datetime_map = null;
		Map check_map = null;
		Map check_Middle_map= null;
		
		float min_pay_hour;
		
		
		String start="";//紀錄加班開始 年月日時分
		String end="";//紀錄加班結束 年月日時分
		
		
		boolean check_Exist=false;
		try{
			//重複不匯入清單
			List ng_list = new ArrayList();
			int ng_count = 0;
			//資料不正確清單
			List error_list = new ArrayList();
			int error_count = 0;
			
			//預先刪除所有匯入資料中重複資料
			Verification.DELETE_Overlap(datalist);
			
			//檢核匯入資料清單
			Iterator it = datalist.iterator();
			
		

			while(it.hasNext()){
				
				String dep_id = "";
				String emp_id = "";
				
				dataMap = (Map) it.next();				 
				if(
						 	   "".equals((String) dataMap.get("EHF020800T0_06_Year")) 	// 加班考勤日期(年)
							&& "".equals((String) dataMap.get("EHF020800T0_06_Month")) 	// 加班考勤日期(月)
							&& "".equals((String) dataMap.get("EHF020800T0_06_Day")) 	// 加班考勤日期(日)
	
							&& "".equals((String) dataMap.get("EHF020800T0_11")) 		// 申請部門(代號)
							&& "".equals((String) dataMap.get("EHF020800T1_04")) 		// 申請人員工工號
							&& "".equals((String) dataMap.get("EHF020800T1_04_name")) 	// 申請人員工姓名
							
							&& "".equals((String) dataMap.get("EHF020800T1_06_Year"))	// 加班日期(起)(年)
							&& "".equals((String) dataMap.get("EHF020800T1_06_Month")) 	// 加班日期(起)(月)
							&& "".equals((String) dataMap.get("EHF020800T1_06_Day")) 	// 加班日期(起)(日)
							&& "".equals((String) dataMap.get("EHF020800T1_06_HH")) 	// 加班日期(起)(時)
							&& "".equals((String) dataMap.get("EHF020800T1_06_MM")) 	// 加班日期(起)(分)
							
							&& "".equals((String) dataMap.get("EHF020800T1_07_Year")) 	// 加班日期(迄)(年)
							&& "".equals((String) dataMap.get("EHF020800T1_07_Month")) 	// 加班日期(迄)(月)
							&& "".equals((String) dataMap.get("EHF020800T1_07_Day")) 	// 加班日期(迄)(日)
							&& "".equals((String) dataMap.get("EHF020800T1_07_HH")) 	// 加班日期(迄)(時)
							&& "".equals((String) dataMap.get("EHF020800T1_07_MM")) 	// 加班日期(迄)(分)
							
							&& "".equals((String) dataMap.get("EHF020800T1_06_BRK_HH")) // 休息時間(起)(時)
							&& "".equals((String) dataMap.get("EHF020800T1_06_BRK_MM")) // 休息時間(起)(分)
							&& "".equals((String) dataMap.get("EHF020800T1_07_BRK_HH")) // 休息時間(迄)(時)
							&& "".equals((String) dataMap.get("EHF020800T1_07_BRK_MM")) // 休息時間(迄)(分)
							&& "".equals((String) dataMap.get("EHF020800T1_11")) 		// 加班時數處理方法
							&& "".equals((String) dataMap.get("EHF020800T1_09")) 		// 事由
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}

				if(
				   (String)dataMap.get("EHF020800T0_06_Year") 	== null		
				&& (String)dataMap.get("EHF020800T0_06_Month")	== null	
				&& (String)dataMap.get("EHF020800T0_06_Day")	== null		
				&& (String)dataMap.get("EHF020800T0_11")		== null	
				&& (String)dataMap.get("EHF020800T1_04")		== null		
				&& (String)dataMap.get("EHF020800T1_04_name")	== null
				&& (String)dataMap.get("EHF020800T1_06_Year")	== null		
				&& (String)dataMap.get("EHF020800T1_06_Month")	== null
				&& (String)dataMap.get("EHF020800T1_06_Day")	== null		
				&& (String)dataMap.get("EHF020800T1_06_HH")		== null
				&& (String)dataMap.get("EHF020800T1_06_MM")		== null		
				&& (String)dataMap.get("EHF020800T1_07_Year")	== null	
				&& (String)dataMap.get("EHF020800T1_07_Month")	== null		
				&& (String)dataMap.get("EHF020800T1_07_Day")	== null	
				&& (String)dataMap.get("EHF020800T1_07_HH")		== null		
				&& (String)dataMap.get("EHF020800T1_07_MM")		== null	
				&& (String)dataMap.get("EHF020800T1_06_BRK_HH")	== null		
				&& (String)dataMap.get("EHF020800T1_06_BRK_MM")	== null
				&& (String)dataMap.get("EHF020800T1_07_BRK_HH")	== null		
				&& (String)dataMap.get("EHF020800T1_07_BRK_MM")	== null
				&& (String)dataMap.get("EHF020800T1_11")		== null		
				&& (String)dataMap.get("EHF020800T1_09")		== null
	
				){
					it.remove();  //將此筆資料由待匯入清單移除
					continue;
				}
					
				
				//加班單匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   "".equals((String) dataMap.get("EHF020800T0_06_Year")) 	// 加班考勤日期(年)
						|| "".equals((String) dataMap.get("EHF020800T0_06_Month")) 	// 加班考勤日期(月)
						|| "".equals((String) dataMap.get("EHF020800T0_06_Day")) 	// 加班考勤日期(日)
						|| "".equals((String) dataMap.get("EHF020800T0_11")) 		// 申請部門(代號)
						|| "".equals((String) dataMap.get("EHF020800T1_04")) 		// 申請人員工工號
						|| "".equals((String) dataMap.get("EHF020800T1_04_name")) 	// 申請人員工姓名
						|| "".equals((String) dataMap.get("EHF020800T1_06_Year")) 	// 加班日期(起)(年)
						|| "".equals((String) dataMap.get("EHF020800T1_06_Month")) 	// 加班日期(起)(月)
						|| "".equals((String) dataMap.get("EHF020800T1_06_Day")) 	// 加班日期(起)(日)
						|| "".equals((String) dataMap.get("EHF020800T1_06_HH")) 	// 加班日期(起)(時)
						|| "".equals((String) dataMap.get("EHF020800T1_06_MM")) 	// 加班日期(起)(分)
						|| "".equals((String) dataMap.get("EHF020800T1_07_Year")) 	// 加班日期(迄)(年)
						|| "".equals((String) dataMap.get("EHF020800T1_07_Month")) 	// 加班日期(迄)(月)
						|| "".equals((String) dataMap.get("EHF020800T1_07_Day")) 	// 加班日期(迄)(日)
						|| "".equals((String) dataMap.get("EHF020800T1_07_HH")) 	// 加班日期(迄)(時)
						|| "".equals((String) dataMap.get("EHF020800T1_07_MM")) 	// 加班日期(迄)(分)
						//|| "".equals((String) dataMap.get("EHF020800T1_06_BRK_HH")) // 休息時間(起)(時)
						//|| "".equals((String) dataMap.get("EHF020800T1_06_BRK_MM")) // 休息時間(起)(分)
						//|| "".equals((String) dataMap.get("EHF020800T1_07_BRK_HH")) // 休息時間(迄)(時)
						//|| "".equals((String) dataMap.get("EHF020800T1_07_BRK_MM")) // 休息時間(迄)(分)
						|| "".equals((String) dataMap.get("EHF020800T1_11")) // 加班時數處理方法
					){
					dataMap.put("error", "未正確填寫欄位資料!" + "<br>");
				}
				
				
				//加班單匯入資料格式不正確, 不可匯入(整筆資料有部分空白)
				if(
						   (String)dataMap.get("EHF020800T0_06_Year") 	== null	// 加班考勤日期(年)	
						|| (String)dataMap.get("EHF020800T0_06_Month")	== null	// 加班考勤日期(月)
						|| (String)dataMap.get("EHF020800T0_06_Day")	== null	// 加班考勤日期(日)
						
						|| (String)dataMap.get("EHF020800T0_11")		== null	// 申請部門(代號)
						|| (String)dataMap.get("EHF020800T1_04")		== null	// 申請人員工工號	
						|| (String)dataMap.get("EHF020800T1_04_name")	== null // 申請人員工姓名
						
						|| (String)dataMap.get("EHF020800T1_06_Year")	== null	// 加班日期(起)(年)	
						|| (String)dataMap.get("EHF020800T1_06_Month")	== null // 加班日期(起)(月)
						|| (String)dataMap.get("EHF020800T1_06_Day")	== null	// 加班日期(起)(日)	
						|| (String)dataMap.get("EHF020800T1_06_HH")		== null // 加班日期(起)(時)
						|| (String)dataMap.get("EHF020800T1_06_MM")		== null	// 加班日期(起)(分)
						
						|| (String)dataMap.get("EHF020800T1_07_Year")	== null	// 加班日期(迄)(年)
						|| (String)dataMap.get("EHF020800T1_07_Month")	== null	// 加班日期(迄)(月)
						|| (String)dataMap.get("EHF020800T1_07_Day")	== null	// 加班日期(迄)(日)
						|| (String)dataMap.get("EHF020800T1_07_HH")		== null	// 加班日期(迄)(時)	
						|| (String)dataMap.get("EHF020800T1_07_MM")		== null	// 加班日期(迄)(分)
						
						//|| (String)dataMap.get("EHF020800T1_06_BRK_HH")	== null	// 休息時間(起)(時)	
						//|| (String)dataMap.get("EHF020800T1_06_BRK_MM")	== null // 休息時間(起)(分)
						//|| (String)dataMap.get("EHF020800T1_07_BRK_HH")	== null	// 休息時間(迄)(時)	
						//|| (String)dataMap.get("EHF020800T1_07_BRK_MM")	== null // 休息時間(迄)(分)
						|| (String)dataMap.get("EHF020800T1_11")		== null	// 加班時數處理方法	
					){
					if(dataMap.get("error")==null)
						dataMap.put("error", "欄位資料未正確填寫!" + "<br>");
					else{
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

				//轉化代碼為系統內碼
				dep_id = tools.ShowIdtoRealId(conn, "EHF000200T0",(String)dataMap.get("EHF020800T0_11"), authbean.getCompId()); 
				emp_id = tools.ShowIdtoRealId(conn, "EHF010100T0",(String)dataMap.get("EHF020800T1_04"), authbean.getCompId());
				
				
				if( (String)dataMap.get("EHF020800T1_06_BRK_HH")	== null	// 休息時間(起)(時)	
						|| (String)dataMap.get("EHF020800T1_06_BRK_MM")	== null // 休息時間(起)(分)
						|| (String)dataMap.get("EHF020800T1_07_BRK_HH")	== null	// 休息時間(迄)(時)	
						|| (String)dataMap.get("EHF020800T1_07_BRK_MM")	== null) {// 休息時間(迄)(分))
					
					dataMap.put("EHF020800T1_06_BRK_HH", "00");
					dataMap.put("EHF020800T1_06_BRK_MM", "00");
					dataMap.put("EHF020800T1_07_BRK_HH", "00");
					dataMap.put("EHF020800T1_07_BRK_MM", "00");
				}
				
				if( "".equals((String) dataMap.get("EHF020800T1_06_BRK_HH")) // 休息時間(起)(時)
						|| "".equals((String) dataMap.get("EHF020800T1_06_BRK_MM")) // 休息時間(起)(分)
						|| "".equals((String) dataMap.get("EHF020800T1_07_BRK_HH")) // 休息時間(迄)(時)
						|| "".equals((String) dataMap.get("EHF020800T1_07_BRK_MM"))){ // 休息時間(迄)(分)
					
					dataMap.put("EHF020800T1_06_BRK_HH", "00");
					dataMap.put("EHF020800T1_06_BRK_MM", "00");
					dataMap.put("EHF020800T1_07_BRK_HH", "00");
					dataMap.put("EHF020800T1_07_BRK_MM", "00");
				}
				
				
				//針對年月日，先補0
				//dataMap.put("EHF020800T0_06_Year", 	Verification.addZeroForNum((String)dataMap.get("EHF020800T0_06_Year"),4));
				dataMap.put("EHF020800T0_06_Month", 	Verification.addZeroForNum((String)dataMap.get("EHF020800T0_06_Month"),2));
				dataMap.put("EHF020800T0_06_Day", 		Verification.addZeroForNum((String)dataMap.get("EHF020800T0_06_Day"),2));				

				
				//dataMap.put("EHF020800T1_06_Year", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_06_Year"),4));
				dataMap.put("EHF020800T1_06_Month", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_06_Month"),2));
				dataMap.put("EHF020800T1_06_Day", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_06_Day"),2));
				dataMap.put("EHF020800T1_06_HH", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_06_HH"),2));
				dataMap.put("EHF020800T1_06_MM", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_06_MM"),2));

				//dataMap.put("EHF020800T1_07_Year", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_07_Year"),4));
				dataMap.put("EHF020800T1_07_Month", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_07_Month"),2));
				dataMap.put("EHF020800T1_07_Day", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_07_Day"),2));
				dataMap.put("EHF020800T1_07_HH", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_07_HH"),2));
				dataMap.put("EHF020800T1_07_MM", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_07_MM"),2));
				
				dataMap.put("EHF020800T1_06_BRK_HH", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_06_BRK_HH"),2));
				dataMap.put("EHF020800T1_06_BRK_MM", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_06_BRK_MM"),2));
				dataMap.put("EHF020800T1_07_BRK_HH", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_07_BRK_HH"),2));
				dataMap.put("EHF020800T1_07_BRK_MM", Verification.addZeroForNum((String)dataMap.get("EHF020800T1_07_BRK_MM"),2));

				//加班日期(考勤日期)(不足補0)
				String EHF020800T0_06=
					(String)dataMap.get("EHF020800T0_06_Year")+"/"+
					(String)dataMap.get("EHF020800T0_06_Month")+"/"+
					(String)dataMap.get("EHF020800T0_06_Day");
				
				//加班開始時間(不足補0)
				String EHF020800T1_06=
					(String)dataMap.get("EHF020800T1_06_Year")+"/"+
					(String)dataMap.get("EHF020800T1_06_Month")+"/"+
					(String)dataMap.get("EHF020800T1_06_Day");
				
				//加班結束時間(不足補0)
				String EHF020800T1_07=
					(String)dataMap.get("EHF020800T1_07_Year")+"/"+
					(String)dataMap.get("EHF020800T1_07_Month")+"/"+
					(String)dataMap.get("EHF020800T1_07_Day");
				
				
				
				//取得 "加班考勤日期" 當月最後一天   並分割字串
				String[] EHF020800T0_06_Last_day  = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(EHF020800T0_06))).split("/");
				//取得 "加班日期(起)" 當月最後一天   並分割字串
				String[] EHF020800T1_06_Last_day  = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(EHF020800T1_06))).split("/");
				//取得 "加班日期(迄)" 當月最後一天   並分割字串
				String[] EHF020800T1_07_Last_day  = tools.convertADDateToStrng(tools.getLastMonthDay(tools.covertStringToCalendar(EHF020800T1_07))).split("/");
				
				//驗證"使用日期" '年 '  長度不能大於4位數
				check_Exist=Verification.check_year((String)dataMap.get("EHF020800T0_06_Year"));
				check_Exist=Verification.check_year((String)dataMap.get("EHF020800T1_06_Year"));
				check_Exist=Verification.check_year((String)dataMap.get("EHF020800T1_07_Year"));
				
				//驗證"使用日期" 月份不能大於12
				check_Exist=Verification.check_month((String)dataMap.get("EHF020800T0_06_Month"));
				check_Exist=Verification.check_month((String)dataMap.get("EHF020800T1_06_Month"));
				check_Exist=Verification.check_month((String)dataMap.get("EHF020800T1_07_Month"));
				
				//驗證"使用日期" 日期不能大於當月最後一天
				check_Exist=Verification.check_day((String)dataMap.get("EHF020800T0_06_Day"), EHF020800T0_06_Last_day[2]);
				check_Exist=Verification.check_day((String)dataMap.get("EHF020800T1_06_Day"), EHF020800T1_06_Last_day[2]);
				check_Exist=Verification.check_day((String)dataMap.get("EHF020800T1_07_Day"), EHF020800T1_07_Last_day[2]);

				//驗證"時"是否大於24
				check_Exist=Verification.check_hour((String)dataMap.get("EHF020800T1_06_HH"));
				check_Exist=Verification.check_hour((String)dataMap.get("EHF020800T1_07_HH"));
				check_Exist=Verification.check_hour((String)dataMap.get("EHF020800T1_06_BRK_HH"));
				check_Exist=Verification.check_hour((String)dataMap.get("EHF020800T1_07_BRK_HH"));
				
				//驗證"分"是否大於60
				check_Exist=Verification.check_Minute((String)dataMap.get("EHF020800T1_06_MM"));
				check_Exist=Verification.check_Minute((String)dataMap.get("EHF020800T1_07_MM"));
				check_Exist=Verification.check_Minute((String)dataMap.get("EHF020800T1_06_BRK_MM"));
				check_Exist=Verification.check_Minute((String)dataMap.get("EHF020800T1_07_BRK_MM"));
				
				
				if (check_Exist) {					
					if(dataMap.get("error")==null)
						dataMap.put("error", "日期格式不正確!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"日期格式不正確!!" + "<br>");
					}
				}

				//檢核部門是在系統內
				if(depMap.get(dep_id)==null){
					if(dataMap.get("error")==null)
						dataMap.put("error", "部門資料未設定!請再次確認!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"部門資料未設定!請再次確認!!" + "<br>");
					}
					check_Exist=true;
				}
				
				//檢核員工工號
				if(check_Exist!=true && empMap.get(emp_id)==null){
					if(dataMap.get("error")==null)
						dataMap.put("error", "員工工號錯誤!請再次確認!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"員工工號錯誤!請再次確認!!" + "<br>");
					}
					check_Exist=true;
				}
				
				//確認員工姓名
				if(check_Exist!=true &&!((Map)(empMap.get(emp_id))).get("EMPLOYEE_NAME").equals(dataMap.get("EHF020800T1_04_name"))){
					if(dataMap.get("error")==null)
						dataMap.put("error", "員工姓名錯誤!請再次確認!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"員工姓名錯誤!請再次確認!!" + "<br>");
					}
					check_Exist=true;
				}
				
				///檢核員工歸屬部門
				if(check_Exist!=true &&!((Map)(empMap.get(emp_id))).get("DEPT_ID").equals(dep_id)){
					if(dataMap.get("error")==null)
						dataMap.put("error", "員工歸屬部門錯誤!請再次確認!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"員工歸屬部門錯誤!請再次確認!!" + "<br>");
					}
					check_Exist=true;
				}

				//檢核休假原因長度不可超過50個字
				if(((String)dataMap.get("EHF020800T1_09")).length()>=101){
					check_Exist = true;
					if (dataMap.get("error") == null)
						dataMap.put("error", "事由字數請勿超過100個字!" + "<br>");
					else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "事由字數請勿超過100個字!" + "<br>");
					}
				}

				Calendar CHK_start_cal = tools.covertStringToCalendar(
						EHF020800T0_06+" 00:00:00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
				Calendar CHK_end_cal   = tools.covertStringToCalendar(
						(String)dataMap.get("EHF020800T1_06_Year")+"/" +
					      (String)dataMap.get("EHF020800T1_06_Month")+"/" +
					      (String)dataMap.get("EHF020800T1_06_Day")+" " +
					      (String)dataMap.get("EHF020800T1_06_HH")+":" +
					      (String)dataMap.get("EHF020800T1_06_MM")+":00","yyyy/MM/dd HH:mm:ss");  //計算開始日期
				
				if(CHK_start_cal.compareTo(CHK_end_cal)>0){
					check_Exist = true;
					if (dataMap.get("error") == null)
						dataMap.put("error", "時間錯誤!!加班開始時間不能在考勤日之前。" + "<br>");
					else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "時間錯誤!!加班開始時間不能在考勤日之前。" + "<br>");
					}
				}
				
				
				start=(String)dataMap.get("EHF020800T1_06_Year")+"/" +
			      (String)dataMap.get("EHF020800T1_06_Month")+"/" +
			      (String)dataMap.get("EHF020800T1_06_Day")+" " +
			      (String)dataMap.get("EHF020800T1_06_HH")+":" +
			      (String)dataMap.get("EHF020800T1_06_MM")+":00" ;
				   
				end=(String)dataMap.get("EHF020800T1_07_Year")+"/"+
			      (String)dataMap.get("EHF020800T1_07_Month")+"/"+
			      (String)dataMap.get("EHF020800T1_07_Day")+" "+
			      (String)dataMap.get("EHF020800T1_07_HH")+":" +
			      (String)dataMap.get("EHF020800T1_07_MM")+":00" ;
				
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
				
				
				//修改於20131216  BY賴泓錡
				min_pay_hour = Float.parseFloat(tools.getSysParam(conn, (String)request.getAttribute("compid"), "MIN_PAY_HOUR"));
				
				//處理加班時間資訊
				overtime_datetime_map =ehf020800.composeOvertimeDate(
						EHF020800T1_06,
						(String)dataMap.get("EHF020800T1_06_HH")+(String)dataMap.get("EHF020800T1_06_MM"),
						EHF020800T1_07,
						(String)dataMap.get("EHF020800T1_07_HH")+(String)dataMap.get("EHF020800T1_07_MM"),  
						min_pay_hour);
				
				//處理加班休息時間資訊
				overtime_break_datetime_map = ehf020800.composeOvertimeBreakDate(
						EHF020800T1_06,
						EHF020800T1_06,
						(String)dataMap.get("EHF020800T1_06_HH")+(String)dataMap.get("EHF020800T1_06_MM"),
						(String)dataMap.get("EHF020800T1_07_HH")+(String)dataMap.get("EHF020800T1_07_MM"),
						(String)dataMap.get("EHF020800T1_06_BRK_HH")+(String)dataMap.get("EHF020800T1_06_BRK_MM"),
						(String)dataMap.get("EHF020800T1_07_BRK_HH")+(String)dataMap.get("EHF020800T1_07_BRK_MM"));
				
				//判斷加班休息時間是否有異常設定
				check_map =	ehf020800.checkOvertimeBreakDatAbnormal(
						(String)overtime_datetime_map.get("EHF020800T1_06"),
						(String)overtime_datetime_map.get("EHF020800T1_07"), 
						(String)overtime_break_datetime_map.get("EHF020800T1_06_BRK"),
						(String)overtime_break_datetime_map.get("EHF020800T1_07_BRK"));
				
				//判斷加班時數是否大於最低付薪時數(系統參數設定)
				check_Middle_map =ehf020800.checkOvertime(conn,(String)overtime_datetime_map.get("EHF020800T1_06"), (String)overtime_datetime_map.get("EHF020800T1_07"), (String)overtime_break_datetime_map.get("EHF020800T1_06_BRK"),(String)overtime_break_datetime_map.get("EHF020800T1_07_BRK"),(String)request.getAttribute("compid"));
				
				if((Boolean)check_Middle_map.get("CHK_FLAG")){
					//表示加班居間小於30分鐘
					
					if(dataMap.get("error")==null)
						dataMap.put("error", "員工加班時間設定異常，請勿低於30分鐘!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"員工加班時間設定異常，請勿低於30分鐘!!" + "<br>");
					}
				}
				
				if((Boolean)check_map.get("CHK_FLAG")){
					//表示加班休息時間區間異常
					if(dataMap.get("error")==null)
						dataMap.put("error", "員工加班休息時間設定異常!!請再次確認!!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"員工加班休息時間設定異常!!" + "<br>");
					}
				}else{
					//判斷員工加班時間區間是否重複
					check_map =	ehf020800.checkConflictOvertimeData((String)dataMap.get("EHF020800T0_11"),emp_id,(String)overtime_datetime_map.get("EHF020800T1_06"), (String)overtime_datetime_map.get("EHF020800T1_07"), (String)request.getAttribute("compid"));
					
					if((Boolean)check_map.get("CHK_FLAG")){
						//表示有重複資料
						if(dataMap.get("error")==null)
							dataMap.put("error", "員工加班時間重複!!請再次確認!!" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"員工加班時間重複!!" + "<br>");
						}
					}
				}
				ehf020800.close();

				
				
				
				String sql = "" +
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
				" FROM EHF020900T0 " +
				" WHERE 1=1 " +
				" AND EHF020900T0_M2 = '"+(String)dataMap.get("EHF020800T0_06_Year")+"/"+(String)dataMap.get("EHF020800T0_06_Month")+"'"+	//考勤年月
				" AND EHF020900T0_04 = '"+compId+"'" +//公司代碼
				" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

				List dataList = base_tools.queryList(sql);
				if (dataList.size() > 0) {
					Map data=(Map)dataList.get(0);
					
					if("02".equals((String)data.get("EHF020900T0_02"))||"03".equals((String)data.get("EHF020900T0_02"))){
						//表示有重複資料
						if(dataMap.get("error")==null)
							dataMap.put("error", "考勤已確認，不可新增加班單!!" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"考勤已確認，不可新增加班單!!" + "<br>");
						}
					}
				}

				//建立考勤產生元件
				EMS_AttendanceAction ems_att_tools = EMS_AttendanceAction.getInstance((String)request.getAttribute("compid"), "", authbean.getUserId());
				//建立員工排班表元件
				EMS_Work_Schedule_Table ems_work_schedule = new EMS_Work_Schedule_Table("EMS系統", (String)request.getAttribute("compid"));

				//取得並設定員工排班表資料
				this.cur_emp_work_schedule = ems_work_schedule.getOneDayInSchedule(conn, emp_id, EHF020800T0_06);
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				//設定 employee authbean
				AuthorizedBean emp_authbean = new AuthorizedBean();
				emp_authbean.setEmployeeID(emp_id);
				emp_authbean.setCompId(authbean.getCompId());
				emp_authbean.setUserId(authbean.getUserId());
				emp_authbean.setUserCode(authbean.getUserCode());

				//依據排班表設定員工指定日期的班別資料
				if(this.cur_emp_work_schedule.getSchedule_no() != -1){
					tempClassMap = hr_tools.getEmpClassByNo( conn, (String)request.getAttribute("compid"), this.cur_emp_work_schedule.getClass_no());
					//避免 class_no 不正確, 若以 class_no 找不到 classMap 則以員工當前班別作為 classMap
					if(tempClassMap.isEmpty()){
						//申請人工號帶入
						authbean.setEmployeeID(emp_id);
						tempClassMap = hr_tools.getEmpClass(conn, emp_authbean);
					}
					
				}else{
					/*if (dataMap.get("error") == null)
						dataMap.put("error", "排班表錯誤或是工號打錯，請檢查"+ "<br>");
					else {
						dataMap.put("error", dataMap.get("error").toString()+ " " + "排班表錯誤或是工號打錯，請檢查" + "<br>");
					}*/
					//申請人工號帶入
					authbean.setEmployeeID(emp_id);
					tempClassMap = hr_tools.getEmpClass(conn, emp_authbean);
				}
				
				hr_tools.close();				
				//若班別資訊為空則不進入執行
				if(tempClassMap==null){
					//依照排班表取得班別上班時間
					Calendar class_in_cal = ems_att_tools.getCalendarByClass(
							conn, EHF020800T0_06, emp_id,(String)request.getAttribute("compid"), tempClassMap, 1);  //班別設定的上班時間(Calendar)
					//依照排班表取得班別下班時間
					Calendar class_out_cal = ems_att_tools.getCalendarByClass(
							conn, EHF020800T0_06 , emp_id, (String)request.getAttribute("compid"), tempClassMap, 2);  //班別設定的下班時間(Calendar)
					
					//匯入的加班開始時間
					Calendar cur_cal = tools.covertStringToCalendar(EHF020800T1_06+" "+(String)dataMap.get("EHF020800T1_06_HH")+":"+(String)dataMap.get("EHF020800T1_06_MM")+":00", "yyyy/MM/dd HH:mm:ss");

					//System.out.println("加班開始時間			 :"+ems_tools.covertDateToString(cur_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));//加班開始時間]
					//System.out.println("依照排班表設定的班別->下班時間:"+ems_tools.covertDateToString(class_out_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));//依照排班表設定的班別->下班時間
					//System.out.println("依照排班表設定的班別->上班時間:"+ems_tools.covertDateToString(class_in_cal.getTime(), "yyyy/MM/dd HH:mm:ss"));//依照排班表設定的班別->上班時間

					if (!tools.isHoliday(conn, EHF020800T0_06,	(String)request.getAttribute("compid"))) {
						if (cur_cal.compareTo(class_in_cal) >= 0&& cur_cal.compareTo(class_out_cal) < 0) {
							if (dataMap.get("error") == null)
								dataMap.put("error", "加班開始時間必須在下班之後，請檢查"+ "<br>");
							else {
								dataMap.put("error", dataMap.get("error").toString()+ " " + "加班開始時間必須在下班之後，請檢查" + "<br>");
							}
						}
					}
				}
				
				

				if(dataMap.get("error")!=null){
					error_list.add(dataMap); // 將此筆資料移至資料不正確清單
					it.remove(); // 將此筆資料由待匯入清單移除
					error_count++;
					// 結束此次迴圈
					continue;
				}
				overtime_datetime_map.clear();
				overtime_break_datetime_map.clear();
			    check_map.clear();
				check_Middle_map.clear();
			}

			base_tools.close();
			
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
	 * 搜尋未確認的加班單資料
	 * @param conn
	 * @param dataMap
	 * @return
	 */
	public Map getMap(Connection conn){
		Map returnMap = new HashMap();
		try{
			String sql = "";
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			sql = "" +
			" SELECT * " +
			" FROM EHF020800T0 " +
			" WHERE 1=1 " +
			" AND EHF020800T0_09 <> '03' " ;
			//" AND EHF020800T0_06 = '"+(String)dataMap.get("EHF020800T0_06")+"' " +  
			//" AND EHF020800T0_11 = '"+(String)dataMap.get("EHF020800T0_11")+"' " ;  
		
			rs = stmt.executeQuery(sql);
		
			while(rs.next()){
				
				returnMap.put("CHK_FLAG", "true");
				returnMap.put("EHF020800T0_01", rs.getString("EHF020800T0_01"));
			}
			
			
			if(returnMap.isEmpty()){
				returnMap.put("CHK_FLAG", "false");
			}
		
			rs.close();
			stmt.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnMap;
	}

	/**
	 * 判斷是否為假日
	 * @param conn
	 * @param date
	 * @param comp_id
	 * @return
	 */
	public boolean isHoliday(Connection conn, String date, String comp_id){
		
		boolean chk_flag = false;
		
		try{
			
			if(this.cur_emp_work_schedule != null && this.cur_emp_work_schedule.getSchedule_no() != -1){
				chk_flag = this.cur_emp_work_schedule.isHoliday_flag();
			}else{
				//原始判斷是否為休假日為抓取行事曆，一律使用排班表
				//chk_flag = ems_tools.isHoliday(conn, date, comp_id);
				//若是排班表未產生，改為背端顯示訊息
				System.out.println(date+"排班表未產生，請先產生排班表。  ");
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return chk_flag;
	}
}
