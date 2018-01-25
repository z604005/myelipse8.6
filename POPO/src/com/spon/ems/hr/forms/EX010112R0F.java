package com.spon.ems.hr.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.spon.utils.util.BA_EXCEL;

public class EX010112R0F extends BA_EXCEL {
	
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
	public EX010112R0F(Connection conn,String rpt,String SC0030_01) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010112R0.xls";
		
		//sheet的名稱
		super.SheetName = "員工生日清冊";
			
		//樣板一頁總列數
		super.PageRowCount = 56;
		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 53;
		
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
	public EX010112R0F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010112R0.xls";
		
		//sheet的名稱
		super.SheetName = "員工生日清冊";
			
		//樣板一頁總列數
		super.PageRowCount = 56;
//		
//		super.PageColCount = 10;
			
		//樣板一頁可放資料筆數,不管一筆資料使用了幾列
		super.PageDataCount = 53;
		
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
			this.setHeadValue(0,2,"A","列印日期:"+print_date+"",false,"");	//列印日期							
			this.setHeadValue(1,1,"A",comp_name+""+report_name,false,""); //列印公司抬頭						
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public int print(Connection conn, EHF010112M0F Form, String comp_id) {
		// TODO Auto-generated method stub
		
		int DataCount = 0;
		List dataList =new ArrayList();
		
		try{
			String sql = "" +
			" SELECT A.HR_EmployeeSysNo, A.HR_EmployeeNo, A.EHF010106T0_04, " +
			" DATE_FORMAT(A.EHF010106T0_10, '%Y/%m/%d') AS EHF010106T0_10, " +
			" B.HR_JobNameSysNo, B.HR_DepartmentSysNo, " +
			" H.HR_DepartmentNo, H.HR_DepartmentName, " +
			" I.HR_JobNameNo, I.HR_JobName " +
			" FROM EHF010106T0 A " +						
			" LEFT JOIN EHF010106T6 B ON A.HR_EmployeeSysNo = B.HR_EmployeeSysNo AND A.HR_CompanySysNo = B.HR_CompanySysNo " +
			" LEFT JOIN EHF010108T0 H ON B.HR_DepartmentSysNo = H.HR_DepartmentSysNo AND B.HR_CompanySysNo = H.HR_CompanySysNo " +
			" LEFT JOIN EHF010109T0 I ON B.HR_JobNameSysNo = I.HR_JobNameSysNo AND B.HR_CompanySysNo = I.HR_CompanySysNo " +
			" WHERE 1=1 " +
			" AND A.HR_CompanySysNo = '"+comp_id+"' ";
			
			if (!"".equals(Form.getHR_EmployeeSysNo()) && !"".equals(Form.getHR_DepartmentSysNo())){  //員工
				sql += " AND B.HR_DepartmentSysNo = '"+Form.getHR_DepartmentSysNo()+"' " +  //部門代號
					   " AND A.HR_EmployeeSysNo = '"+Form.getHR_EmployeeSysNo()+"' ";  //員工工號
			}else if(!"".equals(Form.getHR_DepartmentSysNo())){  //部門組織
				sql += " AND B.HR_DepartmentSysNo = '"+Form.getHR_DepartmentSysNo()+"' ";  //部門代號
			}
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				
				EHF010112M0F bean = new EHF010112M0F();
				bean.setHR_DepartmentName(rs.getString("HR_DepartmentName"));  //
				bean.setHR_JobName(rs.getString("HR_JobName"));  //
				bean.setHR_EmployeeNo(rs.getString("HR_EmployeeNo"));  //
				bean.setEHF010106T0_04(rs.getString("EHF010106T0_04"));  //
				bean.setEHF010106T0_10(rs.getString("EHF010106T0_10"));  //
				
				dataList.add(bean);
				
				DataCount++;
				
			}
			rs.close();
			stmt.close();
			
			this.printDetail(dataList,Form);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return DataCount;
	}

	private void printDetail(List dataList, EHF010112M0F Form) {
		// TODO Auto-generated method stub
		
		int i = 1;
		
		try {
			Iterator it = dataList.iterator();
			
			while(it.hasNext()){
								
				//this.setHeadValue(3,2,"F","頁        次:"+(this.getNowPageNum()+1)+"",false,"");
				this.next();  //換下一行
				EHF010112M0F bean = (EHF010112M0F)it.next();
				
				this.setDetail(1, "A", i+"", false); 
				this.setDetail(1, "B", bean.getHR_DepartmentName(), false); 
				this.setDetail(1, "C", bean.getHR_JobName(), false); 
				this.setDetail(1, "D", bean.getHR_EmployeeNo(), false); 
				this.setDetail(1, "E", bean.getEHF010106T0_04(), false); 
				this.setDetail(1, "F", bean.getEHF010106T0_10(), false);
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
