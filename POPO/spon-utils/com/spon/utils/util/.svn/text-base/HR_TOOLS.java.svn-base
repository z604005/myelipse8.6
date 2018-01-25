package com.spon.utils.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import vtgroup.ems.common.vo.AuthorizedBean;
import com.spon.mvc.models.BaseFunction;
import com.spon.mvc.models.BaseSystem;

/**
 * HR公用元件
 * @author maybe
 * @version 1.0
 */
public class HR_TOOLS implements BaseSystem {
	
	//private static HR_TOOLS hr_tools; 
	private BaseFunction base_tools;
	private BA_TOOLS tools;
	
	/*public static HR_TOOLS getInstance() {
        if (hr_tools == null)
        	hr_tools = new HR_TOOLS();
         return hr_tools;
    }*/
	
	public HR_TOOLS(){
		try{
			//建立基本操作元件
			base_tools = new BaseFunction();
		}catch(Exception e){
			e.printStackTrace();
		}
		tools = BA_TOOLS.getInstance();
	}

	public void finalize() throws Throwable {

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
	 * 取得公司資料
	 * @param conn
	 * @param comp_id
	 * @return
	 * @throws Exception 
	 */
	public Map getCompNameMap( Connection conn, String comp_id ) throws Exception{
		
		Map compNameMap = new HashMap();
		
		try{
			String sql = "" +
			" SELECT *, " +
			" DATE_FORMAT(EHF000100T0_06, '%Y/%m/%d') AS EHF000100T0_06 " +
			" FROM  EHF000100T0 " +
			" WHERE 1 = 1 " +			
			" AND HR_CompanySysNo = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				compNameMap.put("COMP_SHOW_ID", rs.getString("EHF000100T0_01"));	//公司代碼
				compNameMap.put("COMP_UNI", rs.getString("EHF000100T0_02"));	//統一編號
				compNameMap.put("COMP_NAME_C", rs.getString("EHF000100T0_03"));	//公司名稱(中)
				compNameMap.put("COMP_NAME_E", rs.getString("EHF000100T0_04")==null?"":rs.getString("EHF000100T0_04"));	//公司名稱(英)
				compNameMap.put("COMP_USED_DT", rs.getString("EHF000100T0_06"));//成立日期
				compNameMap.put("MASTER_ID", rs.getString("EHF000100T0_07"));	//負責人姓名
				
			}
			
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("取得公司資料，發生錯誤!!" + e.toString());
		}
		
		return compNameMap;
		
	}
	
	/**
	 * 取得部門資料 --> 以 DEPT_ID 為 Key
	 * @param conn
	 * @param comp_id
	 * @return
	 * @throws Exception 
	 */
	public Map getDepNameMap( String comp_id ) throws Exception{
		
		Map depNameMap = new HashMap();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF000200T0_01 AS DEPT_ID, EHF000200T0_02 AS SHOW_DEPT_ID, EHF000200T0_03 AS SHOW_DESC, EHF000200T0_06 AS COMP_LEVEL, " +
			" DATE_FORMAT(EHF000200T0_08, '%Y/%m/%d') AS USED_DT, " +
			" DATE_FORMAT(EHF000200T0_09, '%Y/%m/%d') AS END_DT " +
			" FROM  EHF000200T0 " +
			" WHERE 1 = 1 " +
			" AND HR_CompanySysNo = '"+comp_id+"' ";
			
			List deplist = base_tools.queryList(sql);
			
			Iterator it = deplist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				depNameMap.put(columnMap.get("DEPT_ID"), columnMap);
				
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("以部門系統內碼取得部門資料，發生錯誤!!" + e.toString());
		}
		
		return depNameMap;
	}
	
	/**
	 * 取得部門系統內碼 --> 以 SHOW_DEPT_ID 為 Key
	 * @param conn
	 * @param comp_id
	 * @return
	 * @throws Exception 
	 */
	public Map getDep( String comp_id ) throws Exception{
		
		Map depNameMap = new HashMap();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF000200T0_01 AS DEPT_ID, EHF000200T0_02 AS SHOW_DEPT_ID, EHF000200T0_03 AS SHOW_DESC, EHF000200T0_06 AS COMP_LEVEL, " +
			" DATE_FORMAT(EHF000200T0_08, '%Y/%m/%d') AS USED_DT, " +
			" DATE_FORMAT(EHF000200T0_09, '%Y/%m/%d') AS END_DT " +
			" FROM  EHF000200T0 " +
			" WHERE 1 = 1 " +
			" AND HR_CompanySysNo = '"+comp_id+"' ";
			
			List deplist = base_tools.queryList(sql);
			
			Iterator it = deplist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				depNameMap.put(columnMap.get("SHOW_DEPT_ID"), columnMap.get("DEPT_ID"));
				
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("以部門代號取得部門系統內碼，發生錯誤!!" + e.toString());
		}
		
		return depNameMap;
	}
	
	/**
	 * 取得部門資料 --> 以 SHOW_DEPT_ID 為 Key
	 * @param comp_id
	 * @return
	 * @throws Exception
	 */
	public Map getC( String comp_id ) throws Exception{
		
		Map depNameMap = new HashMap();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF000200T0_01 AS DEPT_ID, EHF000200T0_02 AS SHOW_DEPT_ID, EHF000200T0_03 AS SHOW_DESC, EHF000200T0_06 AS COMP_LEVEL, " +
			" DATE_FORMAT(EHF000200T0_08, '%Y/%m/%d') AS USED_DT, " +
			" DATE_FORMAT(EHF000200T0_09, '%Y/%m/%d') AS END_DT,EHF000400T0_01 " +
			" FROM  EHF000200T0 " +
			" LEFT JOIN EHF000400T0 ON EHF000200T0_01=EHF000400T0_02" +
			" WHERE 1 = 1 " +
//			" AND EHF000400T0_09 = '1' " +	//組織預設
			" AND HR_CompanySysNo = '"+comp_id+"' ";
			
			List deplist = base_tools.queryList(sql);
			
			Iterator it = deplist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				depNameMap.put(columnMap.get("SHOW_DEPT_ID"), columnMap.get("EHF000400T0_01"));
				
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("以部門代號取得部門資料，發生錯誤!!" + e.toString());
		}
		
		return depNameMap;
	}
	
	/**
	 * 取得員工資料 --> 以 USER_CODE 為 Key
	 * @param conn
	 * @param comp_id
	 * @return
	 * @throws Exception 
	 */
	public Map getEmpNameMap( String comp_id ) throws Exception{
		
		Map empNameMap = new LinkedHashMap();

		try{
			String sql = "" +
			" SELECT " +
			" EHF010100T0_01 AS USER_CODE, EHF010100T0_02 AS EMPLOYEE_ID, EHF010100T0_I AS ID_NO, EHF010100T0_05 AS EMPLOYEE_NAME, " +
			" EHF010100T0_07 AS EMPLOYEE_TYPE, EHF010100T6_02 AS DEPT_ID, EHF000200T0_02 AS SHOW_DEPT_ID, EHF000200T0_03 AS SHOW_DESC ," +
			" EHF010100T6_06 AS TITLE_NO, EHF010100T6_07 AS IS_MANAGER, EHF010100T6_08 AS LEVEL, " +
			" a.EHF010100T1_02 AS SEARCHSTATUS,  date_format(a.EHF010100T1_03,'%Y/%m/%d') AS CHECKDATE ," +
			" c.EHF020403T1_02 AS CARD ,EHF010100T8_04 AS CLASS" +
			" FROM  EHF010100T0 " +
			" LEFT JOIN EHF010100T6 ON EHF010100T6.EHF010100T6_01 = EHF010100T0.EHF010100T0_01 AND EHF010100T6.HR_CompanySysNo = EHF010100T0.HR_CompanySysNo " +
			" LEFT JOIN  EHF010100T1  a ON a.EHF010100T1_01=EHF010100T0.EHF010100T0_01" +
			" LEFT JOIN ehf000200t0 b   ON EHF010100T6_02 = ehf000200t0_01" +
			" LEFT JOIN ehf020403t1 c   ON a.EHF010100T1_01 = c.EHF020403T1_01    " +
			" LEFT JOIN EHF010100T8 ON EHF010100T8_03 = EHF010100T0_01" +
			" WHERE 1 = 1 " +
			" AND EHF010100T0.HR_CompanySysNo = '"+comp_id+"' " +
			" ORDER BY SUBSTR(EHF000200T0_02, 1, 1) ,EHF010100T0_02, c.EHF020403T1_04_END";
			
			List emplist = base_tools.queryList(sql);
			
			Iterator it = emplist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				empNameMap.put(columnMap.get("USER_CODE"), columnMap);
				
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("以員工系統內碼取得員工資料，發生錯誤!!" + e.toString());
		}
		
		return empNameMap;
	}
	
	/**
	 * 取得員工系統內碼 --> 以 EMPLOYEE_ID 為 Key
	 * @param comp_id
	 * @return
	 * @throws Exception 
	 */
	public Map getEmp( String comp_id ) throws Exception{
		
		Map empNameMap = new LinkedHashMap();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF010100T0_01 AS USER_CODE, EHF010100T0_02 AS EMPLOYEE_ID, EHF010100T0_I AS ID_NO, EHF010100T0_05 AS EMPLOYEE_NAME, " +
			" EHF010100T0_07 AS EMPLOYEE_TYPE, EHF010100T6_02 AS DEPT_ID, EHF000200T0_02 AS SHOW_DEPT_ID, EHF000200T0_03 AS SHOW_DESC ," +
			" EHF010100T6_06 AS TITLE_NO, " +
			" a.EHF010100T1_02 AS SEARCHSTATUS,  date_format(a.EHF010100T1_03,'%Y/%m/%d') AS CHECKDATE " +
			" FROM  EHF010100T0 " +
			" LEFT JOIN EHF010100T6 ON EHF010100T6.EHF010100T6_01 = EHF010100T0.EHF010100T0_01 AND EHF010100T6.HR_CompanySysNo = EHF010100T0.HR_CompanySysNo " +
			" LEFT JOIN  EHF010100T1  a ON a.EHF010100T1_01=EHF010100T0.EHF010100T0_01" +
			" LEFT JOIN ehf000200t0 b   ON EHF010100T6_02 = ehf000200t0_01" +
			" LEFT JOIN ehf020403t1 c   ON a.EHF010100T1_01 = c.EHF020403T1_01    " +
			" WHERE 1 = 1 " +
			" AND EHF010100T0.HR_CompanySysNo = '"+comp_id+"' " +
			" ORDER BY SUBSTR(EHF000200T0_02, 1, 1),c.EHF020403T1_02";
			
			List emplist = base_tools.queryList(sql);
			
			Iterator it = emplist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				empNameMap.put(columnMap.get("EMPLOYEE_ID"), columnMap.get("USER_CODE"));
				
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("以員工工號取得員工系統內碼，發生錯誤!!" + e.toString());
		}
		
		return empNameMap;
		
	}
	
	/**
	 * 依據員工工號(employee_id)取得員工基本資料Map
	 * @param user_id
	 * @param employee_id
	 * @param comp_id
	 * @return
	 * @throws Exception 
	 */
	public Map getEmpInfMapByEmpId(String user_id, String employee_id,
			String comp_id) throws Exception {
		// TODO Auto-generated method stub
		
		Map empInfMap = new HashMap();
		Connection conn = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF010100T0_01 AS USER_CODE, EHF010100T0_02 AS EMPLOYEE_ID, EHF010100T0_I AS ID_NO, EHF010100T0_03 AS PASSPORT_NO, " +
			" EHF010100T0_04 AS STAY_NO, EHF010100T0_05 AS EMPLOYEE_NAME, EHF010100T0_07 AS EMPLOYEE_TYPE, EHF010100T0_08 AS EMP_SEX, " +
			" EHF010100T0_27 AS OFFICE_DATE ,EHF010100T0_28 AS OUT_DATE, " +
			" EHF000200T0_02 AS SHOW_DEPT_ID, EHF000200T0_03 AS SHOW_DESC , " +
			" EHF010100T6_02 AS DEPT_ID, EHF010100T6_06 AS TITLE_NO, EHF010100T6_07 AS IS_MANAGER, EHF010100T6_08 AS LEVEL, " +
			" a.EHF010100T1_02 AS SEARCHSTATUS, date_format(a.EHF010100T1_03,'%Y/%m/%d') AS CHECKDATE " +
			" FROM  EHF010100T0 " +
			" LEFT JOIN EHF010100T6 d ON d.EHF010100T6_01 = EHF010100T0.EHF010100T0_01 AND d.HR_CompanySysNo = EHF010100T0.HR_CompanySysNo " +
			" LEFT JOIN EHF010100T1 a ON a.EHF010100T1_01 = EHF010100T0.EHF010100T0_01" +
			" LEFT JOIN ehf000200t0 b ON d.EHF010100T6_02 = b.ehf000200t0_01" +
			//" LEFT JOIN ehf020403t1 c   ON a.EHF010100T1_01 = c.EHF020403T1_01    " +
			" WHERE 1 = 1 " +
			" AND a.EHF010100T1_04 = '0' " +
			" AND EHF010100T0.HR_CompanySysNo = '"+comp_id+"' " +
			" AND EHF010100T0_01 = '"+employee_id+"' ";
			//" ORDER BY SUBSTR(EHF000200T0_02, 1, 1),c.EHF020403T1_02";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				empInfMap.put("USER_CODE", 		rs.getString("USER_CODE"));		//員工系統代碼
				empInfMap.put("EMPLOYEE_ID", 	rs.getString("EMPLOYEE_ID"));	//員工工號
				empInfMap.put("ID_NO", 			rs.getString("ID_NO"));			//身分證字號
				empInfMap.put("EMPLOYEE_NAME", 	rs.getString("EMPLOYEE_NAME"));	//姓名(中)
				empInfMap.put("EMPLOYEE_TYPE", 	rs.getString("EMPLOYEE_TYPE"));	//員工類別
				empInfMap.put("DEPT_ID", 		rs.getString("DEPT_ID"));		//歸屬部門系統代碼
				empInfMap.put("SHOW_DEPT_ID", 	rs.getString("SHOW_DEPT_ID"));	//本層部門代號
				empInfMap.put("SHOW_DESC", 		rs.getString("SHOW_DESC"));		//本層部門名稱
				empInfMap.put("TITLE_NO", 		rs.getString("TITLE_NO"));		//職務系統代碼
				empInfMap.put("IS_MANAGER", 	rs.getString("IS_MANAGER"));	//是否為主管
				empInfMap.put("LEVEL", 			rs.getString("LEVEL"));			//主管級等
				empInfMap.put("SEARCHSTATUS", 	rs.getString("SEARCHSTATUS"));	//職務狀況
				empInfMap.put("CHECKDATE", 		rs.getString("CHECKDATE"));		//日期
				empInfMap.put("OFFICE_DATE", 	sdf.parse((String)rs.getString("OFFICE_DATE")));//到職日期
				
				if(!"".equals(rs.getString("OUT_DATE"))){
				empInfMap.put("OUT_DATE", 		sdf.parse((String)rs.getString("OUT_DATE")));		//離職日期
				}
				
				
			}
			
			rs.close();
			stmt.close();			
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("以員工工號取得員工基本資料，發生錯誤!!" + e.toString());
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return empInfMap;
	}
	
	/**
	 * 取得職務資料 --> 以 TITLE_NO 為 Key
	 * @param comp_id
	 * @return
	 * @throws Exception 
	 */
	public Map getTitleNameMap( String comp_id ) throws Exception{
		
		Map titleNameMap = new HashMap();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF000300T0_01 AS TITLE_NO, EHF000300T0_02 AS SHOW_TITLE_NO, EHF000300T0_03 AS TITLE_NAME, EHF000300T0_05 AS USED_TYPE " +
			" FROM  EHF000300T0 " +
			" WHERE 1 = 1 " +
			" AND EHF000300T0.HR_CompanySysNo = '"+comp_id+"' ";
			
			List titlelist = base_tools.queryList(sql);
			
			Iterator it = titlelist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				titleNameMap.put(columnMap.get("TITLE_NO"), columnMap);
				
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("以職務系統內碼取得職務資料，發生錯誤!!" + e.toString());
		}
		
		return titleNameMap;
	}
	
	/**
	 * 取得職務系統內碼 --> 以 SHOW_TITLE_NO 為 Key
	 * @param comp_id
	 * @return
	 */
	public Map getTitle( String comp_id ){
		
		Map titleNameMap = new HashMap();
		
		try{
			String sql = "" +
			" SELECT " +
			" EHF000300T0_01 AS TITLE_NO, EHF000300T0_02 AS SHOW_TITLE_NO, EHF000300T0_03 AS TITLE_NAME, EHF000300T0_05 AS USED_TYPE " +
			" FROM  EHF000300T0 " +
			" WHERE 1 = 1 " +
			" AND EHF000300T0.HR_CompanySysNo = '"+comp_id+"' ";
			
			List titlelist = base_tools.queryList(sql);
			
			Iterator it = titlelist.iterator();
			
			while(it.hasNext()){
				
				Map columnMap = (Map) it.next();
				
				titleNameMap.put(columnMap.get("SHOW_TITLE_NO"), columnMap);
				
			}
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return titleNameMap;
		
	}
	
	/**
	 * 取得員工當前班別資料
	 * @param conn
	 * @param authbean
	 * @return
	 */
	public Map getEmpClass( Connection conn, AuthorizedBean authbean ){
		
		Map empClassMap = new HashMap();
		
		try{
				/*int class_no = this.getEmpClassPerson(conn, authbean );
				if(class_no == 0){
					class_no = this.getEmpClassDep(this.emscontext, conn, authbean);
				}
				
				empClassMap = this.getEmpClassByNo(conn, authbean.getCompId(), class_no);*/
			
			int class_no = this.getEmpClassPerson(conn, authbean );
			
			empClassMap = this.getEmpClassByNo(conn, authbean.getCompId(), class_no);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return empClassMap;
	}
	
	/**
	 * 取得公司班別的資料Map -- 依據班別序號
	 *  WORK_CLASS_CODE = 班別代號
	 *  WORK_CLASS_NAME = 班別名稱
	 * 	WORK_START_TIME = 上班時間
	 * 	WORK_END_TIME = 下班時間
	 * 	SIESTA_START_TIME = 午休時間(起)
	 * 	SIESTA_END_TIME = 午休時間(迄)  
	 * 	return Map
	 * @param conn
	 * @param comp_id
	 * @param class_no
	 * @return
	 * @throws Exception 
	 */
	public Map getEmpClassByNo(Connection conn, String comp_id, int class_no) throws Exception {
		// TODO Auto-generated method stub
		
		Map empClassMap = new HashMap();
		
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF000400T0.*, " +
			" IFNULL(EHF000400T0_12, '') AS OKEY, " +
			" IFNULL(EHF000400T0_13, '') AS HOKEY, " +
			" CONCAT(SUBSTRING(EHF000400T0_05, 1, 2), ':', SUBSTRING(EHF000400T0_05, 3, 2), ':00') AS WORK_START_TIME, " +
			" CONCAT(SUBSTRING(EHF000400T0_06, 1, 2), ':', SUBSTRING(EHF000400T0_06, 3, 2), ':00') AS WORK_END_TIME, " +
			" CONCAT(SUBSTRING(EHF000400T0_07, 1, 2), ':', SUBSTRING(EHF000400T0_07, 3, 2), ':00') AS SIESTA_START_TIME, " +
			" CONCAT(SUBSTRING(EHF000400T0_08, 1, 2), ':', SUBSTRING(EHF000400T0_08, 3, 2), ':00') AS SIESTA_END_TIME, " +
			" EHF000400T0_NFLG AS NOON_FLAG, " +
			" EHF000400T0_14 AS LTSW, EHF000400T0_15 AS LTHR, EHF000400T0_16 AS LTRA " +
			" FROM EHF000400T0 " +
			" WHERE 1=1 " +
			" AND EHF000400T0_01 = "+class_no+"" +
			" AND EHF000400T0_11 = '"+comp_id+"' ";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				empClassMap.put("WORK_CLASS_NO", rs.getInt("EHF000400T0_01"));  //班別序號
				empClassMap.put("DEPT_ID", rs.getString("EHF000400T0_02"));  //部門代號
				empClassMap.put("WORK_CLASS_CODE", rs.getString("EHF000400T0_03"));  //班別代號
				empClassMap.put("WORK_CLASS_NAME", rs.getString("EHF000400T0_04"));  //班別名稱
				//empClassMap.put(rs.getString("EHF010100T0_03"), rs.getString("EHF010100T0_04"));  //班別資訊
				
				empClassMap.put("WORK_START_TIME", rs.getString("EHF000400T0_05"));  //上班時間
				empClassMap.put("WORK_START_TIME_CAL", rs.getString("WORK_START_TIME"));  //上班時間
				empClassMap.put("WORK_END_TIME", rs.getString("EHF000400T0_06"));  //下班時間
				empClassMap.put("WORK_END_TIME_CAL", rs.getString("WORK_END_TIME"));  //下班時間
				
				empClassMap.put("SIESTA_FLAG", rs.getBoolean("NOON_FLAG"));  //是否記錄中午打卡
				
				empClassMap.put("SIESTA_START_TIME", rs.getString("EHF000400T0_07"));  //午休時間(起)
				empClassMap.put("SIESTA_START_TIME_CAL", rs.getString("SIESTA_START_TIME"));  //午休時間(起)
				empClassMap.put("SIESTA_END_TIME", rs.getString("EHF000400T0_08"));  //午休時間(迄)
				empClassMap.put("SIESTA_END_TIME_CAL", rs.getString("SIESTA_END_TIME"));  //午休時間(迄)
				
				empClassMap.put("START_FLEXIBLE_SWITCH", rs.getBoolean("EHF000400T0_05_FLG"));  //上班彈性時間開關
				empClassMap.put("START_FLEXIBLE_TIME", rs.getString("EHF000400T0_05_VAL"));  //上班彈性時間
				empClassMap.put("END_FLEXIBLE_SWITCH", rs.getBoolean("EHF000400T0_06_FLG"));  //下班彈性時間開關
				empClassMap.put("END_FLEXIBLE_TIME", rs.getString("EHF000400T0_06_VAL"));  //下班彈性時間
				//2015.11.27新增
				empClassMap.put("WORK_CLASS_HOLIDAY", rs.getString("EHF000400T0_17"));	//休假方式
				//2016.10.25新增
				empClassMap.put("PAY_BY_HOUR", rs.getString("EHF000400T0_18"));	//是否時薪班別
				
				empClassMap.put("LATETIME_SWITCH", rs.getBoolean("LTSW"));  //延後下班是否啟用
				if(rs.getBoolean("LTSW")){
					//啟用延後下班
					empClassMap.put("LATETIME_HOUR", rs.getString("LTHR"));  //延後下班時數
					empClassMap.put("LATETIME_RATE", rs.getFloat("LTRA"));  //延後下班加成率
				}else{
					//未啟用延後下班
					empClassMap.put("LATETIME_HOUR", "0");  //延後下班時數
					empClassMap.put("LATETIME_RATE", (float)0 );  //延後下班加成率
				}
				
				if(!"".equals(rs.getString("OKEY"))){
					//empClassMap.put("OVERTIME_KEY_SWITCH", rs.getBoolean("OKEY_SW"));  //加班是否啟用
					//empClassMap.put("ADVANCE_OVERTIME_SWITCH", rs.getBoolean("AOSW"));  //是否可提前加班
					//empClassMap.put("AOVT_START_TIME", rs.getString("EHF010102T0_06"));  //提前加班開始時間
					//empClassMap.put("ADVANCE_OVERTIME_START_TIME_CAL", rs.getString("AOVT_START_TIME"));  //提前加班開始時間
					//empClassMap.put("ADVANCE_DEDUCTED_HOUR", rs.getString("AOVT_DEDUCTED_HOUR"));  //提前加班內扣時數
					//empClassMap.put("OVERTIME_SWITCH", rs.getBoolean("OSW"));  //是否可加班
					//empClassMap.put("OVERTIME_ATT_RECORD", rs.getBoolean("OATT_RECORD"));  //是否記錄下班與加班上班的刷卡資料
					//empClassMap.put("OVT_START_TIME", rs.getString("EHF010102T0_13"));  //加班開始時間
					//empClassMap.put("OVERTIME_START_TIME_CAL", rs.getString("OVT_START_TIME"));  //加班開始時間
					//empClassMap.put("OVERTIME_DEDUCTED_HOUR", rs.getString("OVT_DEDUCTED_HOUR"));  //加班內扣時數
					//empClassMap.put("OVERTIME_LIMIT_SWITCH", rs.getString("OVT_LIMIT_SW"));  //是否有加班限制
					//empClassMap.put("OVERTIME_LIMIT_TYPE", rs.getString("OVT_LIMIT_TYPE"));  //加班限制類型
					//empClassMap.put("OVERTIME_LIMIT_TIME", rs.getString("OVT_LIMIT_TIME"));  //加班限制時間or時數
				}else{
					empClassMap.put("OVERTIME_KEY_SWITCH", false );  //加班是否啟用
					empClassMap.put("ADVANCE_OVERTIME_SWITCH", false);  //是否可提前加班
					empClassMap.put("OVERTIME_SWITCH", false );  //是否可加班
				}
				
				if(!"".equals(rs.getString("HOKEY"))){
					//empClassMap.put("HOLIDAY_OVERTIME_KEY_SWITCH", rs.getBoolean("HOKEY_SW"));  //不休假加班是否啟用
					//empClassMap.put("HOLIDAY_OVERTIME_SWITCH", rs.getBoolean("HOSW"));  //是否可不休假加班
				}else{
					empClassMap.put("HOLIDAY_OVERTIME_KEY_SWITCH", false );  //不休假加班是否啟用
					empClassMap.put("HOLIDAY_OVERTIME_SWITCH", false );  //是否可不休假加班
				}
				
				int work_start_time = tools.TimeToSecs((String)empClassMap.get("WORK_START_TIME"));     //上班時間 in Secs
				int work_end_time = tools.TimeToSecs((String)empClassMap.get("WORK_END_TIME")); 	       //下班時間 in Secs
				int siesta_start_time = tools.TimeToSecs((String)empClassMap.get("SIESTA_START_TIME"));     //午休時間(起) in Secs
				int siesta_end_time = tools.TimeToSecs((String)empClassMap.get("SIESTA_END_TIME"));	   //午休時間(迄) in Secs
				
				//計算上班時數
				long full_work_time = 0;
				long beak_time = 0;
				long real_work_time = 0;
				
				//Full work time
				if(work_start_time > work_end_time){
					full_work_time = (work_end_time + (24*60*60)) - work_start_time;
				}else{
					full_work_time = work_end_time - work_start_time;
				}
				//Break time
				if(siesta_start_time > siesta_end_time){
					beak_time = (siesta_end_time + (24*60*60)) - siesta_start_time;
				}else{
					beak_time = siesta_end_time - siesta_start_time;
				}
				//Real work time
				real_work_time = full_work_time - beak_time;
				
				//建立上班時數
				empClassMap.put("FULL_WORK_HOURS", (float)(((float)full_work_time)/60/60));
				empClassMap.put("REAL_WORK_HOURS", (float)(((float)real_work_time)/60/60));
				
				//取得加班上班時間
				/*int ovt_start_tome = 0;
				String ovt_start_time = "";
				if((String)empClassMap.get("OVT_START_TIME") == null){
					ovt_start_time = "0000";
				}else{
					ovt_start_time = (String)empClassMap.get("OVT_START_TIME");
				}
				if(rs.getBoolean("OKEY_SW") && rs.getBoolean("OSW")){
					ovt_start_tome = this.TimeToSecs(ovt_start_time);  //加班上班時間 in Secs
				}*/
				
				//取得提前加班開始時間
				/*int before_ovt_start_time = 0;
				String aovt_start_time = "";
				if((String)empClassMap.get("AOVT_START_TIME") == null){
					aovt_start_time = "0000";
				}else{
					aovt_start_time = (String)empClassMap.get("AOVT_START_TIME");
				}
				if(rs.getBoolean("OKEY_SW") && rs.getBoolean("AOSW") ){
					before_ovt_start_time = this.TimeToSecs(aovt_start_time);  //提前加班開始時間 in Secs
				}*/
				
				//判斷上午下班時間是否跨午夜十二點
				if(siesta_start_time < work_start_time){
					empClassMap.put("CROSS_MIDNIGHT_SIESTA_START_TIME", true );
				}else{
					empClassMap.put("CROSS_MIDNIGHT_SIESTA_START_TIME", false );
				}
				
				//判斷下午上班時間是否跨午夜十二點
				if(siesta_end_time < work_start_time){
					empClassMap.put("CROSS_MIDNIGHT_SIESTA_END_TIME", true );
				}else{
					empClassMap.put("CROSS_MIDNIGHT_SIESTA_END_TIME", false );
				}
				
				//判斷下班時間是否跨午夜十二點
				if(work_end_time < work_start_time){
					empClassMap.put("CROSS_MIDNIGHT_WORK_END_TIME", true );
				}else{
					empClassMap.put("CROSS_MIDNIGHT_WORK_END_TIME", false );
				}
				
				//判斷加班上班時間是否跨午夜十二點
				/*if(ovt_start_tome < work_start_time){
					empClassMap.put("CROSS_MIDNIGHT_OVERTIME_START_TIME", true );
				}else{
					empClassMap.put("CROSS_MIDNIGHT_OVERTIME_START_TIME", false );
				}*/
				
				//判斷加班下班時間是否跨午夜十二點
				/*if("01".equals((String)empClassMap.get("OVERTIME_LIMIT_TYPE"))){
					int ovt_end_tome = 0;
					if(rs.getBoolean("OKEY_SW") && rs.getBoolean("OSW")){
						ovt_end_tome = this.TimeToSecs(rs.getString("EHF010102T0_21"));  //加班下班時間 in Secs
					}
					if(ovt_end_tome < work_start_time){
						empClassMap.put("CROSS_MIDNIGHT_OVERTIME_END_TIME", true );
					}else{
						empClassMap.put("CROSS_MIDNIGHT_OVERTIME_END_TIME", false );
					}
				}else if("02".equals((String)empClassMap.get("OVERTIME_LIMIT_TYPE"))){
					int ovt_limit_hour = 0;
					if(rs.getBoolean("OKEY_SW") && rs.getBoolean("OSW")){
						ovt_limit_hour = this.HourToSecs((String)empClassMap.get("OVERTIME_LIMIT_TIME"));  //加班下班時數 in Secs
					}
					if(ovt_start_tome+ovt_limit_hour > 24*60*60 ){
						empClassMap.put("CROSS_MIDNIGHT_OVERTIME_END_TIME", true );
					}else{
						empClassMap.put("CROSS_MIDNIGHT_OVERTIME_END_TIME", false );
					}
				}*/
				
				//判斷提前加班時間是否跨午夜十二點
				/*if( before_ovt_start_time > work_start_time ){
					empClassMap.put("CROSS_MIDNIGHT_BEFORE_OVERTIME_START_TIME", true );
				}else{
					empClassMap.put("CROSS_MIDNIGHT_BEFORE_OVERTIME_START_TIME", false );
				}*/
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("取得公司班別的資料Map，發生錯誤!!" + e.toString());
		}
		
		return empClassMap;
	}
	
	/**
	 * 依據所需要的 cross_type 判斷是否跨午夜十二點
	 * @param classMap
	 * @param cross_type
	 * @return
	 */
	public boolean hasCrossMidnight( Map classMap, int cross_type ){
		
		boolean chk_flag = false;
		
		try{
			//取得是否跨午夜十二點的FLAG
			switch(cross_type){
			
			//下班
			case 2:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_WORK_END_TIME");
				break;
				
			//上午下班
			case 3:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_SIESTA_START_TIME");
				break;
			
			//下午上班
			case 4:
				chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_SIESTA_END_TIME");
				break;
			
			//加班上班(不考慮)
			case 5:
				/*chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_OVERTIME_START_TIME");*/
				break;
				
			//加班下班(不考慮)
			case 6:
				/*chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_OVERTIME_END_TIME");*/
				break;	
				
			//提前加班(不考慮)
			case 7:
				/*chk_flag = (Boolean) classMap.get("CROSS_MIDNIGHT_BEFORE_OVERTIME_START_TIME");*/
				break;
				
			}
			
		}catch(Exception e){
			chk_flag = false;
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	/**
	 * 取得員工班別的序號   return int
	 * @param conn
	 * @param authbean
	 * @return
	 * @throws Exception 
	 */
	public int getEmpClassPerson(Connection conn, AuthorizedBean authbean) throws Exception {
		// TODO Auto-generated method stub
		
		int class_no = 0;
		
		try{
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = "" +
			" SELECT EHF010100T8_04 FROM EHF010100T8 " +
			" WHERE 1=1 " +
			" AND EHF010100T8_03 = '"+authbean.getEmployeeID()+"' " +
			" AND EHF010100T8_06 = '"+authbean.getCompId()+"' " ;
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				class_no = rs.getInt("EHF010100T8_04");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
			throw new Exception("取得員工班別的序號，發生錯誤!!" + e.toString());
		}
		
		return class_no;
	}

}
