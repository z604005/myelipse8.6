package com.spon.ems.hr.forms;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spon.ems.hr.models.EHF010106;
import com.spon.ems.hr.tools.EMS_CompanyTools;
import com.spon.utils.util.BA_EXCEL;
import com.spon.utils.util.BA_TOOLS;

public class EX010110R0F extends BA_EXCEL {
	
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
	public EX010110R0F(Connection conn,String rpt,String SC0030_01) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010110R0.xls";
		
		//sheet的名稱
		super.SheetName = "員工基本資料卡";
			
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
		super.RecordStart = 3;
		
		//標題設定標題資料個數
		setHeadSize(headsize);
			
		//開始產生Excel
		super.init(conn,rpt,SC0030_01);
	}
	
	/** 
	 * 公司別建構子
	 * 
	 */
	public EX010110R0F(Connection conn,String rpt,String SC0030_01,HttpServletRequest request) throws Exception{
		
		//樣版檔的名稱，注意此元件只能處理樣板上只有一個sheet
		super.TemplateName = "EHF010110R0.xls";
		
		//sheet的名稱
		super.SheetName = "員工基本資料卡";
		
//		super.imageFile = imageFile;
			
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
		super.RecordStart = 3;
		
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
			this.setHeadValue(0,1,"A",comp_name+" "+report_name,false,""); //列印公司抬頭
			//this.setHeadValue(1,2,"A",report_name+"",false,""); //標題
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	public int print(Connection conn, EHF010110M0F Form, List printList, String comp_id) {
		// TODO Auto-generated method stub
		
		int DataCount = 0;
		List dataList = new ArrayList();
		BA_TOOLS tool = BA_TOOLS.getInstance();
		
		try{
			String sql = "" +			
			" SELECT A.*, " +
			" DATE_FORMAT(A.EHF010106T0_10, '%Y/%m/%d') AS EHF010106T0_10, " +
			" B.HR_JobNameSysNo, B.HR_DepartmentSysNo, " +			
			" H.HR_DepartmentNo, H.HR_DepartmentName, " +
			" I.HR_JobNameNo, I.HR_JobName " +
			" FROM EHF010106T0 A " +						
			" LEFT JOIN EHF010106T6 B ON A.HR_EmployeeSysNo = B.HR_EmployeeSysNo AND A.HR_CompanySysNo = B.HR_CompanySysNo " +						
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
			//" AND C.EHF010106T1_01 IN (1,2) " +
			" AND A.HR_CompanySysNo like '"+comp_id+"' " ;  //公司代碼
			//" ORDER BY A.HR_EmployeeNo DESC " ;
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			EMS_CompanyTools ems_company_tools = EMS_CompanyTools.getInstance("", comp_id, "");
			
			Map queryMap = ems_company_tools.getCompanyInf(conn, "", comp_id, "");
			
			EHF010110M0F bean = null;
			
			while(rs.next()) {
				
				bean = new EHF010110M0F();
				
				bean.setHR_EmployeeSysNo(rs.getString("HR_EmployeeSysNo"));  //
				bean.setHR_DepartmentName(rs.getString("HR_DepartmentName"));  //
				bean.setHR_JobName(rs.getString("HR_JobName"));  //
				bean.setHR_EmployeeNo(rs.getString("HR_EmployeeNo"));  //
				bean.setEHF010106T0_01(rs.getString("EHF010106T0_01"));  //
				bean.setEHF010106T0_02(rs.getString("EHF010106T0_02"));  //
				bean.setEHF010106T0_03(rs.getString("EHF010106T0_03"));  //
				bean.setEHF010106T0_04(rs.getString("EHF010106T0_04"));  //
				bean.setEHF010106T0_05(rs.getString("EHF010106T0_05"));  //
				bean.setEHF010106T0_06(rs.getString("EHF010106T0_06"));  //
				bean.setEHF010106T0_07(rs.getString("EHF010106T0_07"));  //
				bean.setEHF010106T0_08(rs.getString("EHF010106T0_08"));  //
				bean.setEHF010106T0_09(rs.getString("EHF010106T0_09"));  //
				bean.setEHF010106T0_10(rs.getString("EHF010106T0_10"));  //
				bean.setEHF010106T0_11(rs.getString("EHF010106T0_11"));  //
				bean.setEHF010106T0_12(rs.getString("EHF010106T0_12"));  //
				bean.setEHF010106T0_13(rs.getString("EHF010106T0_13"));  //
				bean.setEHF010106T0_14(rs.getString("EHF010106T0_14"));  //
				bean.setEHF010106T0_15(rs.getString("EHF010106T0_15"));  //
				bean.setEHF010106T0_16(rs.getString("EHF010106T0_16"));  //
				bean.setEHF010106T0_17(rs.getString("EHF010106T0_17"));  //
				bean.setEHF010106T0_18(rs.getString("EHF010106T0_18"));  //
				bean.setEHF010106T0_20(tool.StringToBoolean(rs.getString("EHF010106T0_20")));  //
				bean.setEHF010106T0_21(tool.StringToBoolean(rs.getString("EHF010106T0_21")));  //
				bean.setEHF010106T0_23(rs.getString("EHF010106T0_23"));  //
				bean.setHR_CompanySysNo(rs.getString("HR_CompanySysNo"));  //
												
				dataList.add(bean);
				
				DataCount++;
				
			}
			rs.close();
			stmt.close();
			
			this.printDetail(dataList, queryMap, printList, 0);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return DataCount;
	}

	private void printDetail(List dataList, Map queryMap, List printList, int displacement) {
		// TODO Auto-generated method stub
		
		EHF010106 ehf010106 = new EHF010106();		
		Map queryCondMap = new HashMap();
		
		try {
			Iterator it = dataList.iterator();
			
			while(it.hasNext()){
				
				//建立新頁面
				this.newPage();								
				EHF010110M0F bean = (EHF010110M0F)it.next();
				
				queryCondMap.put("HR_EmployeeSysNo", bean.getHR_EmployeeSysNo());  //員工系統代碼
				queryCondMap.put("COMP_ID", bean.getHR_CompanySysNo());
				//將要列印資料的dataList與相片路徑的printList交互比對後Print
				Iterator it_print = printList.iterator();
				
				while(it_print.hasNext()){
					Map tempMap_Print = (Map)it_print.next();
					if(bean.getHR_EmployeeSysNo().equals((String)tempMap_Print.get("HR_EmployeeSysNo"))){
						super.imageFile = (File)tempMap_Print.get("imageFile");
						
						this.print_Picture(3.7, 33, 1, 5, 0.3, 0, 0.35, "2");
					}
				}
				
				this.setValue(3+displacement, "B", bean.getEHF010106T0_04(), false);
				this.setValue(3+displacement, "D", bean.getEHF010106T0_01(), false);
				this.setValue(4+displacement, "B", bean.getEHF010106T0_05(), false);
				this.setValue(4+displacement, "D", bean.getEHF010106T0_03(), false);
				this.setValue(5+displacement, "B", bean.getEHF010106T0_10(), false);
				this.setValue(5+displacement, "D", bean.getEHF010106T0_02(), false);
				if("1".equals(bean.getEHF010106T0_07())){
					this.setValue(6+displacement, "B", "女", false);
				}else{
					this.setValue(6+displacement, "B", "男", false);
				}
				if(bean.getEHF010106T0_21()){
					this.setValue(6+displacement, "D", "V", false);
				}else{
					this.setValue(6+displacement, "D", "", false);
				}
				if("1".equals(bean.getEHF010106T0_08())){
					this.setValue(7+displacement, "B", "未婚", false);
				}else if("2".equals(bean.getEHF010106T0_08())){
					this.setValue(7+displacement, "B", "已婚", false);
				}else if("3".equals(bean.getEHF010106T0_08())){
					this.setValue(7+displacement, "B", "離婚", false);
				}else if("4".equals(bean.getEHF010106T0_08())){
					this.setValue(7+displacement, "B", "鰥寡", false);
				}
				if(bean.getEHF010106T0_20()){
					this.setValue(7+displacement, "D", "V", false);
				}else{
					this.setValue(7+displacement, "D", "", false);
				}
				this.setValue(8+displacement, "B", bean.getEHF010106T0_12(), false);
				this.setValue(8+displacement, "E", bean.getEHF010106T0_11(), false);
				this.setValue(9+displacement, "B", bean.getEHF010106T0_14(), false);
				this.setValue(9+displacement, "E", bean.getEHF010106T0_13(), false);
				this.setValue(10+displacement, "B", bean.getEHF010106T0_16(), false);
				this.setValue(10+displacement, "E", bean.getEHF010106T0_18(), false);
				
				//學歷明細
				List EHF010106T3_LIST = ehf010106.queryEHF010106T3List(queryCondMap);
				if(!EHF010106T3_LIST.isEmpty()){
				Iterator it_EHF010106T3 = EHF010106T3_LIST.iterator();
				Map tempMap_EHF010106T3 = null;
				
				while(it_EHF010106T3.hasNext()){
					
					tempMap_EHF010106T3 = (Map) it_EHF010106T3.next();
					
					if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
						this.setValue(12+displacement, "B", (String)tempMap_EHF010106T3.get("EHF010106T3_03"), false);
						this.setValue(12+displacement, "C", (String)tempMap_EHF010106T3.get("EHF010106T3_04"), false);
						if("0".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(12+displacement, "D", "畢業", false);
						}else if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(12+displacement, "D", "肄業", false);
						}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(12+displacement, "D", "在學", false);
						}
						this.setValue(12+displacement, "E", (String)tempMap_EHF010106T3.get("EHF010106T3_05")+"~"+
															(String)tempMap_EHF010106T3.get("EHF010106T3_06"), false);
					}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
						this.setValue(13+displacement, "B", (String)tempMap_EHF010106T3.get("EHF010106T3_03"), false);
						this.setValue(13+displacement, "C", (String)tempMap_EHF010106T3.get("EHF010106T3_04"), false);
						if("0".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(13+displacement, "D", "畢業", false);
						}else if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(13+displacement, "D", "肄業", false);
						}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(13+displacement, "D", "在學", false);
						}
						this.setValue(13+displacement, "E", (String)tempMap_EHF010106T3.get("EHF010106T3_05")+"~"+
															(String)tempMap_EHF010106T3.get("EHF010106T3_06"), false);
					}else if("3".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
						this.setValue(14+displacement, "B", (String)tempMap_EHF010106T3.get("EHF010106T3_03"), false);
						this.setValue(14+displacement, "C", (String)tempMap_EHF010106T3.get("EHF010106T3_04"), false);
						if("0".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(14+displacement, "D", "畢業", false);
						}else if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(14+displacement, "D", "肄業", false);
						}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(14+displacement, "D", "在學", false);
						}
						this.setValue(14+displacement, "E", (String)tempMap_EHF010106T3.get("EHF010106T3_05")+"~"+
															(String)tempMap_EHF010106T3.get("EHF010106T3_06"), false);
					}else if("4".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
						this.setValue(15+displacement, "B", (String)tempMap_EHF010106T3.get("EHF010106T3_03"), false);
						this.setValue(15+displacement, "C", (String)tempMap_EHF010106T3.get("EHF010106T3_04"), false);
						if("0".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(15+displacement, "D", "畢業", false);
						}else if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(15+displacement, "D", "肄業", false);
						}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(15+displacement, "D", "在學", false);
						}
						this.setValue(15+displacement, "E", (String)tempMap_EHF010106T3.get("EHF010106T3_05")+"~"+
															(String)tempMap_EHF010106T3.get("EHF010106T3_06"), false);
					}else if("5".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
						this.setValue(15+displacement, "B", (String)tempMap_EHF010106T3.get("EHF010106T3_03"), false);
						this.setValue(15+displacement, "C", (String)tempMap_EHF010106T3.get("EHF010106T3_04"), false);
						if("0".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(15+displacement, "D", "畢業", false);
						}else if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(15+displacement, "D", "肄業", false);
						}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(15+displacement, "D", "在學", false);
						}
						this.setValue(15+displacement, "E", (String)tempMap_EHF010106T3.get("EHF010106T3_05")+"~"+
															(String)tempMap_EHF010106T3.get("EHF010106T3_06"), false);
					}else if("6".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
						this.setValue(16+displacement, "B", (String)tempMap_EHF010106T3.get("EHF010106T3_03"), false);
						this.setValue(16+displacement, "C", (String)tempMap_EHF010106T3.get("EHF010106T3_04"), false);
						if("0".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(16+displacement, "D", "畢業", false);
						}else if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(16+displacement, "D", "肄業", false);
						}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(16+displacement, "D", "在學", false);
						}
						this.setValue(16+displacement, "E", (String)tempMap_EHF010106T3.get("EHF010106T3_05")+"~"+
															(String)tempMap_EHF010106T3.get("EHF010106T3_06"), false);
					}else if("7".equals((String)tempMap_EHF010106T3.get("EHF010106T3_02"))){
						this.setValue(17+displacement, "B", (String)tempMap_EHF010106T3.get("EHF010106T3_03"), false);
						this.setValue(17+displacement, "C", (String)tempMap_EHF010106T3.get("EHF010106T3_04"), false);
						if("0".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(17+displacement, "D", "畢業", false);
						}else if("1".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(17+displacement, "D", "肄業", false);
						}else if("2".equals((String)tempMap_EHF010106T3.get("EHF010106T3_07"))){
							this.setValue(17+displacement, "D", "在學", false);
						}
						this.setValue(17+displacement, "E", (String)tempMap_EHF010106T3.get("EHF010106T3_05")+"~"+
															(String)tempMap_EHF010106T3.get("EHF010106T3_06"), false);
					}
				}
				}
				
				//履經歷明細						
				List EHF010106T2_LIST = ehf010106.queryEHF010106T2List(queryCondMap);
				if(!EHF010106T2_LIST.isEmpty()){
				Iterator it_EHF010106T2 = EHF010106T2_LIST.iterator();
				Map tempMap_EHF010106T2 = null;
				int count_EHF010106T2 = 0;
				
				while(it_EHF010106T2.hasNext()){
					
					tempMap_EHF010106T2 = (Map) it_EHF010106T2.next();
					
					if(count_EHF010106T2==0){
						this.setValue(19+displacement, "B", (String)tempMap_EHF010106T2.get("EHF010106T2_02"), false);
						this.setValue(20+displacement, "B", (String)tempMap_EHF010106T2.get("EHF010106T2_03"), false);
						this.setValue(21+displacement, "B", (String)tempMap_EHF010106T2.get("EHF010106T2_04"), false);
						this.setValue(22+displacement, "B", (String)tempMap_EHF010106T2.get("EHF010106T2_05"), false);
						this.setValue(23+displacement, "B", (String)tempMap_EHF010106T2.get("EHF010106T2_07")+"~"+
															(String)tempMap_EHF010106T2.get("EHF010106T2_08"), false);
						count_EHF010106T2++;
					}else if(count_EHF010106T2==1){
						this.setValue(19+displacement, "C", (String)tempMap_EHF010106T2.get("EHF010106T2_02"), false);
						this.setValue(20+displacement, "C", (String)tempMap_EHF010106T2.get("EHF010106T2_03"), false);
						this.setValue(21+displacement, "C", (String)tempMap_EHF010106T2.get("EHF010106T2_04"), false);
						this.setValue(22+displacement, "C", (String)tempMap_EHF010106T2.get("EHF010106T2_05"), false);
						this.setValue(23+displacement, "C", (String)tempMap_EHF010106T2.get("EHF010106T2_07")+"~"+
															(String)tempMap_EHF010106T2.get("EHF010106T2_08"), false);
						count_EHF010106T2++;
					}else if(count_EHF010106T2==2){
						this.setValue(19+displacement, "D", (String)tempMap_EHF010106T2.get("EHF010106T2_02"), false);
						this.setValue(20+displacement, "D", (String)tempMap_EHF010106T2.get("EHF010106T2_03"), false);
						this.setValue(21+displacement, "D", (String)tempMap_EHF010106T2.get("EHF010106T2_04"), false);
						this.setValue(22+displacement, "D", (String)tempMap_EHF010106T2.get("EHF010106T2_05"), false);
						this.setValue(23+displacement, "D", (String)tempMap_EHF010106T2.get("EHF010106T2_07")+"~"+
															(String)tempMap_EHF010106T2.get("EHF010106T2_08"), false);
						count_EHF010106T2++;
					}else if(count_EHF010106T2==3){
						this.setValue(19+displacement, "E", (String)tempMap_EHF010106T2.get("EHF010106T2_02"), false);
						this.setValue(20+displacement, "E", (String)tempMap_EHF010106T2.get("EHF010106T2_03"), false);
						this.setValue(21+displacement, "E", (String)tempMap_EHF010106T2.get("EHF010106T2_04"), false);
						this.setValue(22+displacement, "E", (String)tempMap_EHF010106T2.get("EHF010106T2_05"), false);
						this.setValue(23+displacement, "E", (String)tempMap_EHF010106T2.get("EHF010106T2_07")+"~"+
															(String)tempMap_EHF010106T2.get("EHF010106T2_08"), false);
						count_EHF010106T2++;
					}
				}
				}
				
				//證照資料明細
				List EHF010106T4_LIST = ehf010106.queryEHF010106T4List(queryCondMap);
				if(!EHF010106T4_LIST.isEmpty()){
				Iterator it_EHF010106T4 = EHF010106T4_LIST.iterator();
				Map tempMap_EHF010106T4 = null;				
				int count_EHF010106T4 = 0;
				
				while(it_EHF010106T4.hasNext()){
					
					tempMap_EHF010106T4 = (Map) it_EHF010106T4.next();
					
					if(count_EHF010106T4==0){
						this.setValue(25+displacement, "B", (String)tempMap_EHF010106T4.get("EHF010106T4_02"), false);
						this.setValue(26+displacement, "B", (String)tempMap_EHF010106T4.get("EHF010106T4_04")+"~"+
															(String)tempMap_EHF010106T4.get("EHF010106T4_05"), false);
						this.setValue(27+displacement, "B", (String)tempMap_EHF010106T4.get("EHF010106T4_03"), false);
						count_EHF010106T4++;
					}else if(count_EHF010106T4==1){
						this.setValue(25+displacement, "C", (String)tempMap_EHF010106T4.get("EHF010106T4_02"), false);
						this.setValue(26+displacement, "C", (String)tempMap_EHF010106T4.get("EHF010106T4_04")+"~"+
															(String)tempMap_EHF010106T4.get("EHF010106T4_05"), false);
						this.setValue(27+displacement, "C", (String)tempMap_EHF010106T4.get("EHF010106T4_03"), false);
						count_EHF010106T4++;
					}else if(count_EHF010106T4==2){
						this.setValue(25+displacement, "D", (String)tempMap_EHF010106T4.get("EHF010106T4_02"), false);
						this.setValue(26+displacement, "D", (String)tempMap_EHF010106T4.get("EHF010106T4_04")+"~"+
															(String)tempMap_EHF010106T4.get("EHF010106T4_05"), false);
						this.setValue(27+displacement, "D", (String)tempMap_EHF010106T4.get("EHF010106T4_03"), false);
						count_EHF010106T4++;
					}else if(count_EHF010106T4==3){
						this.setValue(25+displacement, "E", (String)tempMap_EHF010106T4.get("EHF010106T4_02"), false);
						this.setValue(26+displacement, "E", (String)tempMap_EHF010106T4.get("EHF010106T4_04")+"~"+
															(String)tempMap_EHF010106T4.get("EHF010106T4_05"), false);
						this.setValue(27+displacement, "E", (String)tempMap_EHF010106T4.get("EHF010106T4_03"), false);
						count_EHF010106T4++;
					}
				}
				}
				
				//家庭關係明細
				List EHF010106T5_LIST = ehf010106.queryEHF010106T5List(queryCondMap);
				if(!EHF010106T5_LIST.isEmpty()){
				Iterator it_EHF010106T5 = EHF010106T5_LIST.iterator();
				Map tempMap_EHF010106T5 = null;				
				int count_EHF010106T5 = 0;
				
				while(it_EHF010106T5.hasNext()){
					
					tempMap_EHF010106T5 = (Map) it_EHF010106T5.next();
					
					if(count_EHF010106T5==0){
						this.setValue(29+displacement, "B", (String)tempMap_EHF010106T5.get("EHF010106T5_02"), false);
						this.setValue(30+displacement, "B", (String)tempMap_EHF010106T5.get("EHF010106T5_05"), false);
						this.setValue(31+displacement, "B", (Integer)tempMap_EHF010106T5.get("EHF010106T5_03")+"", false);
						this.setValue(32+displacement, "B", (String)tempMap_EHF010106T5.get("EHF010106T5_04"), false);
						count_EHF010106T5++;
					}else if(count_EHF010106T5==1){
						this.setValue(29+displacement, "C", (String)tempMap_EHF010106T5.get("EHF010106T5_02"), false);
						this.setValue(30+displacement, "C", (String)tempMap_EHF010106T5.get("EHF010106T5_05"), false);
						this.setValue(31+displacement, "C", (Integer)tempMap_EHF010106T5.get("EHF010106T5_03")+"", false);
						this.setValue(32+displacement, "C", (String)tempMap_EHF010106T5.get("EHF010106T5_04"), false);
						count_EHF010106T5++;
					}else if(count_EHF010106T5==2){
						this.setValue(29+displacement, "D", (String)tempMap_EHF010106T5.get("EHF010106T5_02"), false);
						this.setValue(30+displacement, "D", (String)tempMap_EHF010106T5.get("EHF010106T5_05"), false);
						this.setValue(31+displacement, "D", (Integer)tempMap_EHF010106T5.get("EHF010106T5_03")+"", false);
						this.setValue(32+displacement, "D", (String)tempMap_EHF010106T5.get("EHF010106T5_04"), false);
						count_EHF010106T5++;
					}else if(count_EHF010106T5==3){
						this.setValue(29+displacement, "E", (String)tempMap_EHF010106T5.get("EHF010106T5_02"), false);
						this.setValue(30+displacement, "E", (String)tempMap_EHF010106T5.get("EHF010106T5_05"), false);
						this.setValue(31+displacement, "E", (Integer)tempMap_EHF010106T5.get("EHF010106T5_03")+"", false);
						this.setValue(32+displacement, "E", (String)tempMap_EHF010106T5.get("EHF010106T5_04"), false);
						count_EHF010106T5++;
					}
				}
				}
				
			}
			
			ehf010106.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
