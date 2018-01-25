package com.spon.ems.hr.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spon.ems.hr.tools.EMS_CompanyTools;
import com.spon.utils.util.BA_EXCEL;
import com.spon.utils.util.BA_TOOLS;

public class EX010113R0F extends BA_EXCEL {
	
	private int headsize = 3; //標題個數
	
	//	存放標題用的陣列
	//	HeadData[0][] 資料列
	//	HeadData[1][] 欄位代號
	//	HeadData[2][] 資料內容
	//	HeadData[3][] 零是否列印 Y 印 N 不印
	//	HeadData[4][] 資料格式 如 "##,###" or "0.00000"
	private String HeadData[][];
	public long objTotal[][]; //計算合計用的
	
	/** 
	 * 一般建構子
	 * 
	 */
	public EX010113R0F(Connection conn,String rpt,String SC0030_01) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010113R0.xls";
		
		//sheet的名稱
		super.SheetName = "員工在職證明書";
			
		//樣板一頁總列數
		super.PageRowCount = 35;
		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 1;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal = new long[0][0];
			
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 4;
		
		//標題設定標題資料個數
		setHeadSize(headsize);
			
		//開始產生Excel
		super.init(conn,rpt,SC0030_01);
	}
	
	/** 
	 * 公司別建構子
	 * 
	 */
	public EX010113R0F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010113R0.xls";
		
		//sheet的名稱
		super.SheetName = "員工在職證明書";
			
		//樣板一頁總列數
		super.PageRowCount = 35;
//		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 1;
		
		//合計物件 => [0]頁計 , [1]總計
		objTotal= new long[0][0];
			
		//一筆資料用了幾列
		super.RecordRow = 1;

		//第一筆資料要丟的列數
		super.RecordStart = 4;
		
		//標題設定標題資料個數
		setHeadSize(headsize);
		
//		設定request
		super.request = request;
			
		//開始產生Excel
		super.init(conn,rpt,SC0030_01);
	}
	
	/** 
	 * 新的一頁開始之前(通常是丟頁計用的)
	 * 如第一頁要換頁前須先將第一頁的頁計寫入，然後才能進入第二頁
	 */
	public void BeforeChangePage() throws Exception{
		
	}
	
	/** 
	* 丟總計
	* 
	*/
	public void EndDocument() throws Exception{
		
	}

	public void printHeadValue(String temp, String temp2, String print_date,
			String comp_name, String report_name) {
		// TODO Auto-generated method stub
		
		try{			
			//this.setHeadValue(0,2,"A","日期:"+print_date+"",false,"");	//列印日期							
			this.setHeadValue(0,1,"A",comp_name+"",false,""); //列印公司抬頭
			this.setHeadValue(1,2,"A",report_name+"",false,""); //標題
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public int print(Connection conn, EHF010113M0F Form, String comp_id) {
		
		int DataCount = 0;
		List dataList =new ArrayList();
		
		try{
			String sql = "" +
			" SELECT * FROM "+
			" ( " +
			" SELECT A.HR_EmployeeSysNo, A.HR_EmployeeNo, A.EHF010106T0_01, A.EHF010106T0_02, A.EHF010106T0_03, A.EHF010106T0_04, " +
			" A.EHF010106T0_07, DATE_FORMAT(A.EHF010106T0_10, '%Y/%m/%d') AS EHF010106T0_10, " +
			" B.HR_JobNameSysNo, B.HR_DepartmentSysNo, " +
			" C.EHF010106T1_01, DATE_FORMAT(C.EHF010106T1_02, '%Y/%m/%d') AS EHF010106T1_02, " +
			" H.HR_DepartmentNo, H.HR_DepartmentName, " +
			" I.HR_JobNameNo, I.HR_JobName " +
			" FROM EHF010106T0 A " +						
			" LEFT JOIN EHF010106T6 B ON A.HR_EmployeeSysNo = B.HR_EmployeeSysNo AND A.HR_CompanySysNo = B.HR_CompanySysNo " +
			" LEFT JOIN EHF010106T1 C ON A.HR_EmployeeSysNo = C.HR_EmployeeSysNo AND A.HR_CompanySysNo = C.HR_CompanySysNo " +
			" LEFT JOIN EHF010108T0 H ON B.HR_DepartmentSysNo = H.HR_DepartmentSysNo AND B.HR_CompanySysNo = H.HR_CompanySysNo " +
			" LEFT JOIN EHF010109T0 I ON B.HR_JobNameSysNo = I.HR_JobNameSysNo AND B.HR_CompanySysNo = I.HR_CompanySysNo " +
			" WHERE 1=1 " ;			
									
			if (!"".equals(Form.getHR_EmployeeSysNo()) && !"".equals(Form.getHR_DepartmentSysNo())){  //員工
				sql += " AND B.HR_DepartmentSysNo = '"+Form.getHR_DepartmentSysNo()+"' " +  //部門代號
					   " AND A.HR_EmployeeSysNo = '"+Form.getHR_EmployeeSysNo()+"' ";  //員工工號
			}else if(!"".equals(Form.getHR_DepartmentSysNo())){  //部門組織
				sql += " AND B.HR_DepartmentSysNo = '"+Form.getHR_DepartmentSysNo()+"' ";  //部門代號
			}			
			
			sql += "" +
			" AND C.EHF010106T1_01 IN (1,3,4) " +
			" AND A.HR_CompanySysNo like '"+comp_id+"' " +  //公司代碼
			" ORDER BY EHF010106T1_02 DESC " +
			" ) tableA " +
			" WHERE 1=1 " +
			" GROUP BY HR_EmployeeSysNo "; 
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			EMS_CompanyTools ems_company_tools = EMS_CompanyTools.getInstance("", comp_id, "");
			
			Map queryMap = ems_company_tools.getCompanyInf(conn, "", comp_id, "");
			
			while(rs.next()) {
				
				EHF010113M0F bean = new EHF010113M0F();
				bean.setHR_DepartmentName(rs.getString("HR_DepartmentName"));  //
				bean.setHR_JobName(rs.getString("HR_JobName"));  //
				bean.setHR_EmployeeNo(rs.getString("HR_EmployeeNo"));  //
				bean.setEHF010106T0_01(rs.getString("EHF010106T0_01"));  //
				bean.setEHF010106T0_02(rs.getString("EHF010106T0_02"));  //
				bean.setEHF010106T0_03(rs.getString("EHF010106T0_03"));  //
				bean.setEHF010106T0_04(rs.getString("EHF010106T0_04"));  //
				bean.setEHF010106T0_07(rs.getString("EHF010106T0_07"));  //
				bean.setEHF010106T0_10(rs.getString("EHF010106T0_10"));  //
				bean.setEHF010106T1_01(rs.getString("EHF010106T1_01"));  //
				bean.setEHF010106T1_02(rs.getString("EHF010106T1_02"));  //
				
				dataList.add(bean);
				
				DataCount++;
				
			}
			rs.close();
			stmt.close();
			
			this.printDetail(dataList, queryMap, 0);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return DataCount;
	}

	private void printDetail(List dataList, Map queryMap, int displacement) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tool = BA_TOOLS.getInstance();
		
		try {
			Iterator it = dataList.iterator();
			
			while(it.hasNext()){
				
				//建立新頁面
				this.newPage();								
				EHF010113M0F bean = (EHF010113M0F)it.next();
				
				this.setValue(4+displacement, "B", bean.getEHF010106T0_04(), false);
				this.setValue(4+displacement, "E", bean.getHR_EmployeeNo(), false);
				this.setValue(5+displacement, "B", bean.getHR_DepartmentName(), false); 
				this.setValue(5+displacement, "E", bean.getHR_JobName(), false);
				if(!"".equals(bean.getEHF010106T0_01())){
					this.setValue(6+displacement, "B", bean.getEHF010106T0_01(), false);
				}else if(!"".equals(bean.getEHF010106T0_02())){
					this.setValue(6+displacement, "B", bean.getEHF010106T0_02(), false); 
				}else{
					this.setValue(6+displacement, "B", bean.getEHF010106T0_03(), false); 
				}				 
				this.setValue(6+displacement, "E", bean.getEHF010106T0_07(), false); 
				this.setValue(7+displacement, "B", bean.getEHF010106T0_10(), false);
				this.setValue(7+displacement, "E", bean.getEHF010106T1_02(), false);
				if("1".equals(bean.getEHF010106T1_01())){
					this.setValue(8+displacement, "B", "現仍在職", false);
				}else if("3".equals(bean.getEHF010106T1_01())){
					this.setValue(8+displacement, "B", "現仍在職", false);
				}else if("4".equals(bean.getEHF010106T1_01())){
					this.setValue(8+displacement, "B", "留職停薪", false);
				}
				this.setValue(16+displacement, "B", (String)queryMap.get("CompanyName_TW"), false);
				this.setValue(17+displacement, "B", (String)queryMap.get("Leader"), false);
				this.setValue(18+displacement, "B", (String)queryMap.get("CompanyId"), false);
				this.setValue(19+displacement, "B", (String)queryMap.get("RepPhone"), false);
				this.setValue(20+displacement, "B", (String)queryMap.get("BusinessAddress_TW"), false);
				this.setValue(34+displacement, "D", "日期："+tool.ymdTostring(tool.getSysDate()), false);								
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
