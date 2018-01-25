package com.spon.ems.vacation.models;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.p6spy.engine.spy.P6PreparedStatement;

import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;
import com.spon.mvc.models.EditDetailFunction;
import com.spon.mvc.models.EditFunction;
import com.spon.mvc.models.QueryFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

public class EHF020404  implements QueryFunction,EditFunction,EditDetailFunction,BaseSystem {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	private BaseFunction base_tools;
	
	public EHF020404(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF020404( String comp_id ){
		
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
		
		ArrayList DepartureWorkEmplist = new ArrayList();//離職員工清單
		ArrayList SuspensionWithoutPayEmplist = new ArrayList();//留職停薪員工清單
		
		//取得正職員工清單
		//Map	WorkEmp=(Map)queryCondMap.get("WorkEmp");
		//取得離職員工清單
		//Map	DepartureWorkEmp=(Map)queryCondMap.get("DepartureWorkEmp");
		//取得留職停薪員工清單
		//Map	SuspensionWithoutPayEmp=(Map)queryCondMap.get("SuspensionWithoutPayEmp");
		HR_TOOLS hr_tools = new HR_TOOLS();
		
		try{
			String sql = "" +
			" SELECT  " +
			" EHF020404T0_01 AS EHF020404T0_01,EHF020404T0_02 AS EHF020404T0_02," +
			" EHF020404T0_03 AS EHF020404T0_03,EHF020404T0_04 AS EHF020404T0_04," +
			" EHF020404T0_05 AS EHF020404T0_05,EHF020404T0_06 AS EHF020404T0_06," +
			" IFNULL(EHF020404T0_07, '') AS EHF020404T0_07," +
			" IFNULL(EHF020404T0_08, '') AS EHF020404T0_08," +
			" EHF020404T0_09 AS EHF020404T0_09," +
			" EHF020404T0_10 AS EHF020404T0_10," +
			//" EHF020404T0_11 AS EHF020404T0_11,"+
			//" EHF020404T0_AFK AS EHF020404T0_AFK," +
			" EHF020403T0_02 AS EHF020403T0_02,EHF020403T0_01 AS EHF020403T0_01," +
			" DATE_FORMAT(EHF020404T0_06, '%Y/%m/%d %H:%i:%s') AS EHF020404T0_06," +
			" IFNULL(DATE_FORMAT(EHF020404T0_11, '%Y/%m/%d'),'') AS EHF020404T0_11"+ 
			" FROM EHF020404T0 " +
			" LEFT JOIN "+
		    " EHF020403T1 ON EHF020404T0_03 = EHF020403T1_02"+
		    " AND EHF020404T0_09 = EHF020403T1_06"+
		    " LEFT JOIN "+
		    " EHF020403T0 ON EHF020403T1_01 = EHF020403T0_01"+
		    " AND EHF020403T1_06 = EHF020403T0_04"+

		    " WHERE   1 = 1";
			
			
			//部門代號
			if(this.base_tools.existString((String)queryCondMap.get("EHF020404T0_dep_UID"))){
				sql += " AND EHF020403T0_02 ='"+(String)queryCondMap.get("EHF020404T0_dep_UID")+"'";  //
			}
			//員工工號
			if(this.base_tools.existString((String)queryCondMap.get("EHF020404T0_emp_UID"))){
				sql += " AND EHF020403T0_01 ='"+(String)queryCondMap.get("EHF020404T0_emp_UID")+"'";  //
			}
			//考勤日期
			if(this.base_tools.existString((String)queryCondMap.get("EHF020404T0_11"))){
				sql += " AND DATE_FORMAT(EHF020404T0_11, '%Y/%m/%d') = '"+(String)queryCondMap.get("EHF020404T0_11")+"'";  //
			}
			//打卡日期時間
			if(this.base_tools.existString((String)queryCondMap.get("EHF020404T0_06"))){
				sql += " AND DATE_FORMAT(EHF020404T0_06, '%Y/%m/%d') = '"+(String)queryCondMap.get("EHF020404T0_06")+"'";  //
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF020404T0_01"))){
				sql += " AND EHF020404T0_01 = '"+(String)queryCondMap.get("EHF020404T0_01")+"'";  //
			}
			if ("".equals((String) queryCondMap.get("EHF020404T0_FLAG"))|| (String) queryCondMap.get("EHF020404T0_FLAG") == null) {
				sql += " AND EHF020404T0_FLAG ='1'"; // 確認狀態 2013/10/03 賴泓錡
			} else {
				sql += " AND EHF020404T0_FLAG ='"+ (String) queryCondMap.get("EHF020404T0_FLAG") + "'"; // 確認狀態 2013/10/03  賴泓錡
			}
			sql += " AND EHF020404T0_09 ='"+(String)queryCondMap.get("COMP_ID")+"'";  //公司代碼
			sql += " AND EHF020404T0_10 ='"+2+"'";  //資料來源
			sql += "ORDER BY EHF020404T0_01 DESC";
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			//System.out.println("sql ==> "+ sql);
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			pstmt.close();
			
			
			
			//BIND FLOW DATA
			BA_TOOLS ems_tools = BA_TOOLS.getInstance();
			
			//取得Flow 員工資訊Map
			//Map empMap = ems_tools.getEmpNameMap((String)queryCondMap.get("USER_ID"), (String)queryCondMap.get("COMP_ID"));
			//取得Flow 部門資訊Map
			//Map depMap = ems_tools.getDepNameMap((String)queryCondMap.get("USER_ID"), Integer.parseInt((String)queryCondMap.get("USER_CODE")),(String)queryCondMap.get("COMP_ID"));
			
			
			// 取得部門資訊
			Map depMap = hr_tools.getDepNameMap((String)queryCondMap.get("COMP_ID"));

			// 取得員工資訊
			Map empMap = hr_tools.getEmpNameMap((String)queryCondMap.get("COMP_ID"));
			
			
			Iterator it = dataList.iterator();
			Map tempMap = null;
			List tempQueryList = null;
			
			
			while(it.hasNext()){
				
				boolean chk_flag = false;//在職判斷
				boolean SuspensionWithoutPay= false;//留職停薪判斷
				boolean DepartureWork= false;//離職停薪判斷
				String tmp_emp_id ="";
				
				
				
				tempMap = (Map) it.next();
				
				//1.先處理留職停薪人員
//				if("0".equals((String) queryCondMap.get("EHF020404T0_15"))||"3".equals((String) queryCondMap.get("EHF020404T0_15"))){
//					Iterator departure_emp_it = SuspensionWithoutPayEmp.keySet().iterator();
//					while (departure_emp_it.hasNext()) {
//						tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
//						if (tmp_emp_id.equals(tempMap.get("EHF020403T0_01"))) {
//							SuspensionWithoutPay = true;
//							break;
//						} else {
//							SuspensionWithoutPay = false;
//						}
//					}
//				}
			
				//2.處理離職員工
//				if("0".equals((String) queryCondMap.get("EHF020404T0_14"))||"2".equals((String) queryCondMap.get("EHF020404T0_14"))){
//					Iterator departure_emp_it = DepartureWorkEmp.keySet().iterator();
//					while (departure_emp_it.hasNext()) {
//						tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
//						if (tmp_emp_id.equals(tempMap.get("EHF020403T0_01"))) {
//							DepartureWork = true;
//							break;
//						} else {
//							DepartureWork = false;
//						}
//					}
//				}
			
				//2.處理在職員工
//				if("0".equals((String) queryCondMap.get("EHF020404T0_13"))||"1".equals((String) queryCondMap.get("EHF020404T0_13"))){
//					Iterator departure_emp_it = WorkEmp.keySet().iterator();
//					while (departure_emp_it.hasNext()) {
//						tmp_emp_id = (String) departure_emp_it.next(); // 留職停薪人員工號
//						if (tmp_emp_id.equals(tempMap.get("EHF020403T0_01"))) {
//							chk_flag = true;
//							break;
//						} else {
//							chk_flag = false;
//						}
//					}
//				}
				
				chk_flag = true;
				if(chk_flag){
					//處理填單人資料Mapping
					tempMap.put("EHF020404T0_emp", (String) ((Map)empMap.get(tempMap.get("EHF020403T0_01"))).get("EMPLOYEE_NAME"));
					tempMap.put("EHF020404T0_dep",(String) ((Map)depMap.get(tempMap.get("EHF020403T0_02"))).get("SHOW_DESC"));
				}else if(DepartureWork){
					//處理填單人資料Mapping
					tempMap.put("EHF020404T0_emp", (String) ((Map)empMap.get(tempMap.get("EHF020403T0_01"))).get("EMPLOYEE_NAME")+"(離職)");
					tempMap.put("EHF020404T0_dep",(String) ((Map)depMap.get(tempMap.get("EHF020403T0_02"))).get("SHOW_DESC"));
					DepartureWorkEmplist.add(tempMap);
					it.remove();
				}else if(SuspensionWithoutPay){
					//處理填單人資料Mapping
					tempMap.put("EHF020404T0_emp", (String) ((Map)empMap.get(tempMap.get("EHF020403T0_01"))).get("EMPLOYEE_NAME")+"(留職停薪)");
					tempMap.put("EHF020404T0_dep",(String) ((Map)depMap.get(tempMap.get("EHF020403T0_02"))).get("SHOW_DESC"));
					SuspensionWithoutPayEmplist.add(tempMap);
					it.remove();
				}else{
					it.remove();
				}
			}

			
			
			dataList.addAll(DepartureWorkEmplist);
			dataList.addAll(SuspensionWithoutPayEmplist);
			hr_tools.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataList;
	}

	@Override
	public Map queryEditData(Map queryCondMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addData(Map dataMap) {
// TODO Auto-generated method stub
		
		String sql = "";
		String show_sql = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd"); 
		try{
			//新增門禁刷卡資料
			sql = "" +
			" INSERT INTO ehf020404t0 (" +
			" EHF020404T0_01, EHF020404T0_02, EHF020404T0_03, EHF020404T0_04, EHF020404T0_05, " +
			" EHF020404T0_06, EHF020404T0_07, EHF020404T0_08, EHF020404T0_09, EHF020404T0_10,EHF020404T0_11,EHF020404T0_FLAG," +
			//" EHF020404T0_AFK,EHF020404T0_SFK, " +
			" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " +
			" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?," +
			//" ?, ?, " +
			" ?, ?, 1, NOW()) ";

			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
			int indexid = 1;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_01"));  //刷卡資料序號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_02"));  //門禁系統號  ==> 日期+時間+卡號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_03"));  //感應卡號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_04"));  //打卡日期
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_05"));  //打卡時間
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_06"));  //打卡日期時間
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_07"));  //門禁刷卡機器代號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_08"));  //動作代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_09"));  //公司代碼
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_10"));  //資料來源
			indexid++;
			
			Date date = sdf.parse((String)dataMap.get("EHF020404T0_11"));
			p6stmt.setObject(indexid, date);  //考勤日期
			indexid++;
			p6stmt.setString(indexid, "1");  //確認狀態
			indexid++;
			//p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_AFK"));  //考勤處理狀態
			//indexid++;
			//p6stmt.setString(indexid, (String)dataMap.get("EHF020404T0_SFK"));  //薪資處理狀態
			//indexid++;
			p6stmt.setString(indexid, "SYSTEM");  //建立人員
			indexid++;
			p6stmt.setString(indexid, "SYSTEM");  //修改人員
			indexid++;
		//	System.out.println("sql ==>"+p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();
			pstmt.close();
			p6stmt.close();
			this.base_tools.commit();			
		}catch(Exception e){
			
			//記錄新增錯誤訊息
			//this.base_tools.writeADDERRMSG("EHF030604.addData()", show_sql+", "+e.getMessage(), 
			//							   dataMap.containsKey("USER_NAME")==true?((String)dataMap.get("USER_NAME")):"",
			//							   dataMap.containsKey("COMP_ID")==true?((String)dataMap.get("COMP_ID")):this.base_tools.getSta_comp_id());
			
			e.printStackTrace();
		}
		
	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		String sql = "";

		try {
			// 刪除資料
			sql = "" + " DELETE FROM EHF020404T0 " 
			         + " WHERE 1=1 AND EHF020404T0_01 = '"
					 +  dataMap.get("EHF020404T0_01") + "' ";
			// 執行刪除
			int dataCount = this.base_tools.delete(sql);
			// 更新資料庫
			this.base_tools.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveData(Map dataMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map queryDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDetailData(String detailType, Map detailDataMap) {
		// TODO Auto-generated method stub
		
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
	/**
	 * 日期格式
	 * @return
	 */
	protected String getDateFormat(){
		return "yyyy/MM/dd";
	}
	/**
	 * 時間格式
	 * @return
	 */
	protected String getTimeFormat(){
		return "HH:mm:ss";
	}
}
