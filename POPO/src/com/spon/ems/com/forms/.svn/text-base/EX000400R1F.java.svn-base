package com.spon.ems.com.forms;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spon.utils.util.BA_EXCEL;

public class EX000400R1F extends BA_EXCEL { 
	
	private int headsize = 5;//標題個數
	
	/*	存放標題用的陣列
	    HeadData[0][]	資料列
	    HeadData[1][]	欄位代碼
	    HeadData[2][]	資料內容
	    HeadData[3][]	資料為零時是否列印 ==> Y(印) N(不印)
	    HeadData[4][]	資料格式 如 "##,###" or "0.00000"
	*/
	private String HeadData[][];
	public long objTotal[][];//計算合計用的
	
	/**
	 * 一般建構子
	 * 
	 */
	public EX000400R1F(Connection conn, String rpt, String SC0030_01) throws Exception{
		
		//樣板檔的名稱，注意此元件只能處理樣板上只有一個Sheet
		super.TemplateName = "EHF010100R1.xls";
		
		//sheet的名稱
		super.SheetName = "公司班別資料表";
				
		//樣板一頁總列數
		super.PageRowCount = 59;
		
		//樣板一頁可放資料筆數，不管一筆資料使用了幾列
		super.PageDataCount = 53;
		
		//合計物件 ==> [0]頁計，[1]總計
		objTotal = new long[0][0];
		
		//一筆資料用了幾列
		super.RecordRow = 1;
		
		//第一筆資料要丟的列數
		super.RecordStart = 7;
		
		//設定標題資料個數
		setHeadSize(headsize);
		
		//開始產生Excel
		super.init(conn, rpt, SC0030_01);
	}
	
	/**
	 * 公司別建構子
	 * 
	 */
	
	public EX000400R1F(Connection conn, String rpt, String SC0030_01, HttpServletRequest request) throws Exception{
		
		//樣板檔的名稱，注意此元件只能處理樣板上一個sheet
		super.TemplateName = "EHF010100R1.xls";
		
		//sheer的名稱
		super.SheetName = "公司班別資料表";

		
		//樣板一頁總列數
		super.PageRowCount = 59;
		
		//樣板一頁可放資料筆數，不管一筆資料使用幾列
		super.PageDataCount =53;
		
		//合計物件 ==> [0]頁計，[1]總計
		objTotal = new long[0][0];
		
		//一筆資料用了幾列
		 super.RecordRow = 1;
		 
		 //第一筆資料要丟的個數
		 super.RecordStart = 7 ;
		 
		 //設定標題資料個數
		 setHeadSize(headsize);
		 
		 super.request = request;
		 
		 //開始產生Excel
		 super.init(conn, rpt, SC0030_01);
	}
	
	/**
	 * 新的一頁開始之前(通常是丟頁計用的)
	 * 如第一頁要換頁前須先將第一頁的頁計寫入，然後才進入第二頁
	 */
	
	public void BeforeChangePage() throws Exception{
		
	}
	
	/**
	 * 丟總計
	 * 
	 */
	
	public void EndDocument()throws Exception{
		
	}

	/**
	 * 列印表頭
	 * @param comp_name
	 */
	public void printHeadValue(String comp_name,String print_date) {

		try {
			// 列印報表表頭資料
			this.setHeadValue(0, 1, "A", comp_name, false, ""); // 列印公司抬頭
			this.setHeadValue(1, 2, "A", "公司班別資料表", false, ""); // 列印公司抬頭
			this.setHeadValue(2, 4, "H", "列印日期："+print_date, false, ""); // 列印公司抬頭
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 列印
	 * 
	 * @param conn
	 * @param comp_id 
	 * @param printlist
	 * @param comp_id
	 * @param comp_id 
	 * @param depMap 
	 * @param empMap 
	 * @param returnMap 
	 * @return
	 */
	public int print(Connection conn,  EHF000400M0F Form, String comp_id, Map Vacation, Map empMap, Map depMap) {
		
		
		int DataCount = 0;

		List dataList =new ArrayList();
		
		
		try {
			String sql = "" +
			" SELECT EHF000400T0_01 ,EHF000400T0_02 ,EHF000400T0_03 ,EHF000400T0_04 ,EHF000400T0_05 ,EHF000400T0_06 ,EHF000400T0_07 " +
			" ,EHF000400T0_08 ,EHF000400T0_09 ,EHF000400T0_10 " +
			" FROM EHF000400T0 " +
			" WHERE 1=1 " +
			" AND EHF000400T0_11 = '"+comp_id+"' " ;
			
			if(!"".equals(Form.getEHF000400T0_02()) && Form.getEHF000400T0_02()!=null ){
				sql += " AND EHF000400T0_02 LIKE '%"+Form.getEHF000400T0_02()+"%' ";//組織單位
			}
			if(!"".equals(Form.getEHF000400T0_03()) && Form.getEHF000400T0_03()!=null ){
				sql += " AND EHF000400T0_03 LIKE '%"+Form.getEHF000400T0_03()+"%' ";//班別代號
			}
			if(!"".equals(Form.getEHF000400T0_04()) && Form.getEHF000400T0_04()!=null ){
				sql += " AND EHF000400T0_04 LIKE '%"+Form.getEHF000400T0_04()+"%' ";//班別
			}
			if(!"".equals(Form.getEHF000400T0_09()) && Form.getEHF000400T0_09()!=null ){
				sql += " AND EHF000400T0_09 = '"+Form.getEHF000400T0_09()+"' ";//組織預設
			}
			
			sql += " ORDER BY EHF000400T0_02 , EHF000400T0_03 ";//LIMIT 30 ";

			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){
			
				rs.beforeFirst();
				
				while(rs.next()){
					EHF000400M0F bean = new EHF000400M0F();
					bean.setEHF000400T0_01(rs.getString("EHF000400T0_01"));  //班別序號
					//bean.setEHF010100T0_02( (String) ((Map)depMap.get(rs.getString("EHF010100T0_02"))).get("SHOW_DESC")==null?"":rs.getString("EHF010100T0_02")+"/"+(String) ((Map)depMap.get(rs.getString("EHF010100T0_02"))).get("SHOW_DESC"));  //部門組織(中文名稱)
					bean.setEHF000400T0_02( (String) ((Map)depMap.get(rs.getString("EHF000400T0_02"))).get("SHOW_DESC") );
					bean.setEHF000400T0_03(rs.getString("EHF000400T0_03"));  //班別代號
					bean.setEHF000400T0_04(rs.getString("EHF000400T0_04"));  //班別
					bean.setEHF000400T0_05(rs.getString("EHF000400T0_05").substring(0, 2)+":"+rs.getString("EHF000400T0_05").substring(2, 4));  //上班時間
					bean.setEHF000400T0_06(rs.getString("EHF000400T0_06").substring(0, 2)+":"+rs.getString("EHF000400T0_06").substring(2, 4));  //下班時間
					bean.setEHF000400T0_07(rs.getString("EHF000400T0_07").substring(0, 2)+":"+rs.getString("EHF000400T0_07").substring(2, 4)+" ~ "
										   +rs.getString("EHF000400T0_08").substring(0, 2)+":"+rs.getString("EHF000400T0_08").substring(2, 4) );	 //午休時間
					if("0".equals(rs.getString("EHF000400T0_09"))){
						bean.setEHF000400T0_09("否");
					}else if("1".equals(rs.getString("EHF000400T0_09"))){    //是否系統預設
						bean.setEHF000400T0_09("是");
					}
					bean.setEHF000400T0_10(rs.getString("EHF000400T0_10"));	 //備註
					dataList.add(bean);
				}
			}
				DataCount++;
				
				this.printDetail(dataList,Form);
				rs.close();
				stmt.close();
				
				
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DataCount;
	}
	
	
	/**
	 * 列印一筆資料
	 * 
	 * @param dataMap
	 */
	public void printDetail(List dataList, EHF000400M0F Form) {

		try {
			Iterator it = dataList.iterator();
			while(it.hasNext()){
				
				
				this.setHeadValue(4,3,"H","頁        次:"+(this.getNowPageNum()+1)+"",false,"");
				this.next();  //換下一行
				EHF000400M0F bean = (EHF000400M0F)it.next();
				
				this.setDetail(1, "A", bean.getEHF000400T0_02(), false); 
				this.setDetail(1, "B", bean.getEHF000400T0_03(), false); 
				this.setDetail(1, "C", bean.getEHF000400T0_04(), false); 
				this.setDetail(1, "D", bean.getEHF000400T0_05(), false); 
				this.setDetail(1, "E", bean.getEHF000400T0_06(), false); 
				this.setDetail(1, "F", bean.getEHF000400T0_07(), false); 
				this.setDetail(1, "G", bean.getEHF000400T0_09(), false); 
				this.setDetail(1, "H", bean.getEHF000400T0_10(), false); 	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
