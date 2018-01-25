package com.spon.ems.vacation.forms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.spon.utils.util.BA_EXCEL;


public class EX020100R0F extends BA_EXCEL{ 
	
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
	public EX020100R0F(Connection conn, String rpt, String SC0030_01) throws Exception{
		
		//樣板檔的名稱，注意此元件只能處理樣板上只有一個Sheet
		super.TemplateName = "EHF020100R0.xls";
		
		//sheet的名稱
		super.SheetName = "公司假別資料表";
				
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
	
	public EX020100R0F(Connection conn, String rpt, String SC0030_01, HttpServletRequest request) throws Exception{
		
		//樣板檔的名稱，注意此元件只能處理樣板上一個sheet
		super.TemplateName = "EHF020100R0.xls";
		
		//sheer的名稱
		super.SheetName = "公司假別資料表";

		
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
			this.setHeadValue(1, 2, "A", "公司假別資料表", false, ""); // 列印公司抬頭
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
	 * @param work_hours 
	 * @param returnMap 
	 * @return
	 */
	public int print(Connection conn,  EHF020100M0F Form, String comp_id, Map Vacation, Map empMap, Map depMap, float work_hours) {
		
		
		int DataCount = 0;

		List dataList =new ArrayList();
		String sql = "";
		
		try {
			sql = " select * from EHF020100T0 " +
			  " where 1=1 " +
			  " and EHF020100T0_08 = '"+comp_id+"' ";  //公司代碼
		
		if (!"".equals(Form.getEHF020100T0_03())) {//假別代號
			sql += " and EHF020100T0_03 = '"+Form.getEHF020100T0_03()+"'";
		}
		if(!"".equals(Form.getEHF020100T0_03_TYPE())){//假別薪資設定
			sql += " and EHF020100T0_03_TYPE = '"+Form.getEHF020100T0_03_TYPE()+"'";
		}
		if(!"".equals(Form.getEHF020100T0_04())){//假別
			sql += " and EHF020100T0_04 = '"+Form.getEHF020100T0_04()+"'";
		}
		
		sql += " ORDER BY EHF020100T0_03 ";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		ResultSet rs = pstmt.executeQuery();
		
		EHF020100M0F Form_List = null;
		
		while(rs.next()){
			Form_List = new EHF020100M0F();
			Form_List.setEHF020100T0_01(rs.getInt("EHF020100T0_01"));
			
			
			//假別代號	
			Form_List.setEHF020100T0_03((String)rs.getString("EHF020100T0_03"));

			//假別薪資設定
			if("01".equals(rs.getString("EHF020100T0_03_TYPE"))){
				Form_List.setEHF020100T0_03_TYPE("全薪");
			}else if("02".equals(rs.getString("EHF020100T0_03_TYPE"))){
				Form_List.setEHF020100T0_03_TYPE("半薪");
			}else if("03".equals(rs.getString("EHF020100T0_03_TYPE"))){
				Form_List.setEHF020100T0_03_TYPE("無薪");
			}else if("04".equals(rs.getString("EHF020100T0_03_TYPE"))){
				Form_List.setEHF020100T0_03_TYPE("依比例設定:"+rs.getString("EHF020100T0_03_VAL")+"倍");
			}
			
			//假別名稱
			Form_List.setEHF020100T0_04((String)rs.getString("EHF020100T0_04"));
			
			
			Form_List.setEHF020100T0_05_FLG((String)rs.getString("EHF020100T0_05_FLG"));
			
			Form_List.setEHF020100T0_06_FLG((String)rs.getString("EHF020100T0_06_FLG"));
			
			
			//年度請假總時數
			if(Float.parseFloat(rs.getString("EHF020100T0_05"))>0){
				Form_List.setEHF020100T0_05_day(((int)(rs.getFloat("EHF020100T0_05")/work_hours))+"");
				Form_List.setEHF020100T0_05_hour((rs.getFloat("EHF020100T0_05")%work_hours)+"");
			}else{
				Form_List.setEHF020100T0_05_day("0");//最低請假時數(天)
				Form_List.setEHF020100T0_05_hour("0");//最低請假時數(時)
			}
			
			//單次最低請假時數
			if(Float.parseFloat(rs.getString("EHF020100T0_06"))>0){
				Form_List.setEHF020100T0_06_day(((int)(rs.getFloat("EHF020100T0_06")/work_hours))+"");
				Form_List.setEHF020100T0_06_hour((rs.getFloat("EHF020100T0_06")%work_hours)+"");
			}else{
				Form_List.setEHF020100T0_06_day("0");//最低請假時數(天)
				Form_List.setEHF020100T0_06_hour("0");//最低請假時數(時)
			}
			
			//假日是否合併計算
			if("0".equals(rs.getString("EHF020100T0_11"))){
				Form_List.setEHF020100T0_11("否");
			}else{
				Form_List.setEHF020100T0_11("是");
			}
			
			//限定連續請假
			if("0".equals(rs.getString("EHF020100T0_12"))){
				Form_List.setEHF020100T0_12("否");
			}else{
				Form_List.setEHF020100T0_12("是");
			}

			Form_List.setEHF020100T0_07(rs.getString("EHF020100T0_07"));
			
			
			dataList.add(Form_List);
		}
				
				DataCount++;
				
				this.printDetail(dataList,Form);
				rs.close();
				pstmt.close();
				
				
				
			
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
	public void printDetail(List dataList, EHF020100M0F Form) {

		try {
			
			String day="";
			Iterator it = dataList.iterator();
			while(it.hasNext()){
				day="";
				
				this.setHeadValue(4,3,"H","頁        次:"+(this.getNowPageNum()+1)+"",false,"");
				this.next();  //換下一行
				EHF020100M0F bean = (EHF020100M0F)it.next();

				this.setDetail(1, "A", bean.getEHF020100T0_03(), false); //假別代號
				this.setDetail(1, "B", bean.getEHF020100T0_04(), false); //假別名稱
				this.setDetail(1, "C", bean.getEHF020100T0_03_TYPE(), false); //假別薪資設定
				
				if("0".equals(bean.getEHF020100T0_05_FLG())){
					day+="無";
				}else{
					if(Float.parseFloat(bean.getEHF020100T0_05_day())>0){
						day+=bean.getEHF020100T0_05_day()+"天";
					}
					if(Float.parseFloat(bean.getEHF020100T0_05_hour())>0){
						day+=(int)(Float.parseFloat(bean.getEHF020100T0_05_hour()))+"時";
					}
					if(Float.parseFloat(bean.getEHF020100T0_05_day())<=0 &&  Float.parseFloat(bean.getEHF020100T0_05_hour())<=0){
						day+="0天0時";
					}
				}
				
			
				this.setDetail(1, "D", day, false); //最低請假時數
				
				day="";
				
				this.setDetail(1, "E", bean.getEHF020100T0_12(), false); //限定連續休假
				this.setDetail(1, "F", bean.getEHF020100T0_11(), false); //假日合併計算
				if("0".equals(bean.getEHF020100T0_06_FLG())){
					day+="無";
				}else{
					if(Float.parseFloat(bean.getEHF020100T0_06_day())>0){
						day+=bean.getEHF020100T0_06_day()+"天";
					}
					if(Float.parseFloat(bean.getEHF020100T0_06_hour())>0){
						day+=(int)(Float.parseFloat(bean.getEHF020100T0_06_hour()))+"時";
					}
					if(Float.parseFloat(bean.getEHF020100T0_06_day())<=0 &&  Float.parseFloat(bean.getEHF020100T0_06_hour())<=0){
						day+="0天0時";
					}
				}
				
				
				this.setDetail(1, "G", day, false); //年度請假總時數
				this.setDetail(1, "H", bean.getEHF020100T0_03(), false); //備註
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

