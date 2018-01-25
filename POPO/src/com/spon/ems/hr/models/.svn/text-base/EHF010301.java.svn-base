package com.spon.ems.hr.models;

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
 * 休假時數
 
 */
public class EHF010301 implements QueryFunction,EditFunction,EditDetailFunction,BaseSystem {
	
	private BaseFunction base_tools;
	
	public EHF010301(){
		
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public EHF010301( String comp_id ){
		
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
			" select EHF000200T0_03 as 單位 ,EHF010100T0_05 as 姓名 ,EHF010300T0_02 as 年度 , " +
			" CASE EHF010300T0_06 " +
			"			 WHEN 'A01' THEN '病假' " +
			"			 WHEN 'A02' THEN '事假' " +
			"			 WHEN 'A03' THEN '特休' " +
			"			 WHEN 'A04' THEN '公假' " +
			"			 WHEN 'A05' THEN '產假' " +
			"			 WHEN 'A06' THEN '婚假' " +
			"			 WHEN 'A07' THEN '喪假' " +
			"			 WHEN 'A08' THEN '輪休' " +
			"			 WHEN 'A09' THEN '生理假' " +
			"			 END AS 假別名稱 " +
			" ,EHF010300T0_07 as 休假時數 , " +
			" EHF010300T0_08 as 剩餘時數, " +
			" EHF010300T0_09 AS 使用期限起  " +
			",EHF010300T0_10 AS 使用期間訖  "+
			",EHF010300T0_04 AS 部門代號  "+
			",EHF010300T0_05 AS 員工工號  "+
			" FROM EHF010300T0 a " +
			" LEFT JOIN EHF010100T0 b ON  a.EHF010300T0_05 = b.EHF010100t0_01  and a.EHF010300T0_12 = b.HR_CompanySysNo " +
			" LEFT JOIN EHF000200T0 c ON  a.EHF010300T0_04 = c.EHF000200T0_01  and a.EHF010300T0_12 = c.HR_CompanySysNo " +
			" WHERE 1=1 " ;
			//if(this.base_tools.existString((String)queryCondMap.get("EHF020800T0_06"))){
			//	sql += " AND DATE_FORMAT(EHF020800T0_06, '%Y/%m') = ? ";  //加班日期
			//}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010300T0_04"))){	//部門組織(代號)
				sql += " and EHF010300T0_04 = ? " ;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010300T0_05"))){	//申請人(員工工號)
				sql += " and EHF010300T0_05 = ? " ;
			}
			sql += "" +
			" AND EHF010300T0_12 = ? ";  //公司代碼
		
			
			//執行SQL
			PreparedStatement pstmt = this.base_tools.getConn().prepareStatement(sql);
			int indexid = 1;
			if(this.base_tools.existString((String)queryCondMap.get("EHF010300T0_04"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF010300T0_04"));
				indexid++;
			}
			if(this.base_tools.existString((String)queryCondMap.get("EHF010300T0_05"))){
				pstmt.setString(indexid, (String)queryCondMap.get("EHF010300T0_05"));
				indexid++;
			}
			pstmt.setString(indexid, (String)queryCondMap.get("COMP_ID")); 
			indexid++;
			
			dataList = this.base_tools.resultSetToList(pstmt.executeQuery());
			//dataList = this.base_tools.queryList(sql);
			
			pstmt.close();
			
			System.out.print(sql);
			
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
		
	}

	@Override
	public void delData(Map dataMap) {
		// TODO Auto-generated method stub
		
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
		
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}



}

