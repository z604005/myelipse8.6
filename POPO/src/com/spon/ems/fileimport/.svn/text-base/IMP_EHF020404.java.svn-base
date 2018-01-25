package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import vtgroup.ems.common.vo.AuthorizedBean;
import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.utils.struts.form.BA_ALLKINDForm;

import com.spon.utils.util.BA_TOOLS;

/**
 * 門禁資料匯入作業 所用元件
 * EHF020404M2A
 * @author SPONPC2
 * 
 */
public class IMP_EHF020404 extends EMS_FILEIMPORT{
	private AuthorizedBean authbean = null;
	private String user_emp_id = "";
	private String user_dept_id = "";
	
	
	public IMP_EHF020404(  AuthorizedBean authbean ) {
	
		//建立相關元件所需資訊
		//this.user_emp_id = user_emp_id;
		//this.user_dept_id = user_dept_id;
		this.authbean = authbean;
		
	}
	/**
	 * 建立 XLS DATA LIST
	 * @param wbk
	 * @return
	 */
	protected List generateXLSDataList( Connection conn, Workbook wbk, String comp_id ){
		
		List<Map<String,String>> xlsdatalist = new ArrayList<Map<String,String>>();
		Map<String,String> dataMap = null;
		
		try{
			//取得第一個Sheet資料
			Sheet st = wbk.getSheet(0);
			
			//第一筆資料為表頭, 不列入匯入資料
			for( int i=3; i<st.getRows(); i++ ){
				if("".equals(st.getCell( 0, i).getContents())&&"".equals(st.getCell( 1, i).getContents())
				 &&"".equals(st.getCell( 2, i).getContents())&&"".equals(st.getCell( 3, i).getContents())
				 &&"".equals(st.getCell( 4, i).getContents())&&"".equals(st.getCell( 5, i).getContents())
				 &&"".equals(st.getCell( 6, i).getContents())&&"".equals(st.getCell( 7, i).getContents())
				 &&"".equals(st.getCell( 8, i).getContents())&&"".equals(st.getCell( 9, i).getContents())){
					continue;
				}
				
				dataMap = new LinkedHashMap<String,String>();
				dataMap.put("DATE_00", st.getCell( 0, i).getContents());  //
				dataMap.put("DATE_01", st.getCell( 1, i).getContents());  //
				dataMap.put("DATE_02", st.getCell( 2, i).getContents());  //
				dataMap.put("DATE_03", st.getCell( 3, i).getContents());  //
				dataMap.put("DATE_04", st.getCell( 4, i).getContents());  //
				dataMap.put("DATE_05", st.getCell( 5, i).getContents());  //
				dataMap.put("DATE_06", st.getCell( 6, i).getContents());  //
				dataMap.put("DATE_07", st.getCell( 7, i).getContents());  //
				dataMap.put("DATE_08", st.getCell( 8, i).getContents());  //
				dataMap.put("DATE_09", st.getCell( 9, i).getContents());  //
				xlsdatalist.add(dataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;
	}
	/**
	 *  檢核匯入資料
	 */
	@Override
	protected Map chkImportDataList(Connection conn, List datalist,	String compId) {
		// TODO Auto-generated method stub
		Map dataMap = null;
		Map errMsgMap = new HashMap();
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		try{
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			//重複不匯入清單
			List ng_list = new ArrayList();
			int ng_count = 0;
			List db_list = new ArrayList();
			int db_count = 0;
			
			//資料不正確清單
			List error_list = new ArrayList();
			int error_count = 0;
			
			//資料是否空白
			List empty_list = new ArrayList();
			int empty_count = 0;
			
			//未設定感應卡資料
			List carderror_list = new ArrayList();
			int carderror_count = 0;
			
			int [] error_number; 
			
			List chklist = new ArrayList();
			List chkcardlist = new ArrayList();
			
			
			//檢核匯入資料清單
			Iterator it = datalist.iterator();
		
			//檢核匯入資料清單
			//取得匯入資料，無表頭因此從第0比開始
			for(int i=0; i<datalist.size(); i++){

				LinkedHashMap o =(LinkedHashMap) datalist.get(i);

				String[] csvDatas =(String[])o.values().toArray(new String[o.size()]); //(String[]) o.values().toArray();

				//檢驗是否空白
				if(!"".equals(csvDatas[0]) && !"".equals(csvDatas[1]) && !"".equals(csvDatas[2]) && !"".equals(csvDatas[3]) && !"".equals(csvDatas[4])&& !"".equals(csvDatas[5])&& !"".equals(csvDatas[6])&& !"".equals(csvDatas[7])&& !"".equals(csvDatas[8])){//20131016修改   BY 賴泓錡
					//檢驗是否與資料庫資料重複
					if ("".equals(csvDatas[9])) {
						csvDatas[9] = "00";
					}
					String insql = "" +
							" SELECT EHF020404T0_01 " +
							" FROM EHF020404T0 "+
							" WHERE 1=1 " +
							" AND EHF020404T0_01 = '"+csvDatas[4]
							                         +String.format("%02d", Integer.valueOf(csvDatas[5]))+String.format("%02d", Integer.valueOf(csvDatas[6]))
							                         +String.format("%02d", Integer.valueOf(csvDatas[7]))+String.format("%02d", Integer.valueOf(csvDatas[8]))
							                         +String.format("%02d", Integer.valueOf(csvDatas[9]))+csvDatas[0]+"' " +
							" AND EHF020404T0_09 = '"+compId+"'";
					rs = stmt.executeQuery(insql);
					if(rs.next()){
						//System.out.println("--nb--");//Hedwig
						ng_list.add(o);
						ng_count++;
						datalist.remove(i);//於原本資料裡刪除有問題資料
						i--;
						continue;
					}
					
					String[] month_end_date_start = ems_tools.convertADDateToStrng(ems_tools.getLastMonthDay(ems_tools.covertStringToCalendar(csvDatas[1]+"/"+csvDatas[2]+"/"+csvDatas[3]))).split("/");
					String[] month_end_date_end   = ems_tools.convertADDateToStrng(ems_tools.getLastMonthDay(ems_tools.covertStringToCalendar(csvDatas[4]+"/"+csvDatas[5]+"/"+csvDatas[6]))).split("/");

				
					//檢驗資料正確性(日期)
					if(csvDatas[1].length()!=4 ||  Integer.valueOf(csvDatas[2])>=13 || Integer.valueOf(csvDatas[3])>Integer.valueOf(month_end_date_start[2]) || 
					   csvDatas[4].length()!=4 ||  Integer.valueOf(csvDatas[5])>=13 || Integer.valueOf(csvDatas[6])>Integer.valueOf(month_end_date_end[2])||
					   Integer.valueOf(csvDatas[7])>=25 ||  Integer.valueOf(csvDatas[8])>=61 || Integer.valueOf(csvDatas[9])>=61){
						//System.out.println("--error--");//Hedwig
						error_list.add(o);
						error_count++;
						datalist.remove(i);//於原本資料裡刪除有問題資料
						i--;
						continue;
					}
					//檢驗是否與匯入資料重複
					chklist = new ArrayList();
					chklist.addAll(datalist);
					for(int k=i; k>=0; k--){
						chklist.remove(k);
					}
					for(int j=0; j<chklist.size(); j++){
						LinkedHashMap list_02 =(LinkedHashMap) chklist.get(j);
						String[] chkDatas = (String[])list_02.values().toArray(new String[list_02.size()]);	
						
							if((chkDatas[0]+chkDatas[1]+chkDatas[2]+chkDatas[3]+chkDatas[4]+chkDatas[5]+chkDatas[6]+chkDatas[7]+chkDatas[8]+chkDatas[9]).equals((csvDatas[0]+csvDatas[1]+csvDatas[2]+csvDatas[3]+csvDatas[4]+csvDatas[5]+csvDatas[6]+csvDatas[7]+csvDatas[8]+csvDatas[9]))){
								//System.out.println("--db--");//Hedwig
								db_list.add(o);
								db_count++;
								datalist.remove(i);
								i--;
								continue;
						}
					}
					//檢驗感應卡號是否建立
					//取得員工感應卡資料
					chkcardlist = new ArrayList();
					chkcardlist = this.getEmpList(conn, csvDatas[0], compId);
					if(chkcardlist.size() == 1){
						//System.out.println("--ce--");//Hedwig
						carderror_list.add(o);
						carderror_count++;
						datalist.remove(i);//於原本資料裡刪除有問題資料
						i--;
						continue;
					}
				}else{//資料為空白
					//System.out.println("--empty--");//Hedwig
					empty_list.add(o);//空白資料
					empty_count++;//空白資料數目
					datalist.remove(i);//於原本資料裡刪除有問題資料
					i--;
				}
			}
			
			if(ng_list.size() > 0 ){
				errMsgMap.put("NGDATACOUNT", ng_count);
				errMsgMap.put("NGDATALIST", ng_list);
			}
			if(error_list.size() > 0 ){
				
				errMsgMap.put("ERRORDATACOUNT", error_count);
				errMsgMap.put("ERRORDATALIST", error_list);
			}
			
			if(empty_list.size() > 0 ){
				errMsgMap.put("EMPTYCOUNT", empty_count);
				errMsgMap.put("EMPTYDATALIST", empty_list);
			}
			
			if(carderror_list.size() > 0 ){
				errMsgMap.put("CARDERRORCOUNT", carderror_count);
				errMsgMap.put("CARDERRORDATALIST", carderror_list);
			}	

			if(db_list.size() > 0 ){
				errMsgMap.put("DBCOUNT", db_count);
				errMsgMap.put("DBDATALIST", db_list);
			}	
			//建立回傳訊息Map
			//errMsgMap.put("MSG", "1.有"+ng_count+"筆資料與資料庫相同。2.有"+db_count+"筆資料與匯入資料相同。3.有"+error_count+"筆資料不正確。"+"5.有"+empty_count+"筆資料有空白欄位。" +"6.有"+carderror_count+"筆資料感應卡尚未設定。共有"+(ng_count+db_count+Out_of_account_count+error_count+empty_count+carderror_count+datalist.size())+"筆資料，匯入"+datalist.size()+"筆。");
		}catch(Exception e){
			e.printStackTrace();
		}
		return errMsgMap;
	}


	@Override
	protected Map fileimport(Connection conn, List datalist, String owner,String compId) {
			// TODO Auto-generated method stub
			Map msgMap = new HashMap();
			//ehf020401T0
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			SimpleDateFormat sdf_01 = new SimpleDateFormat("yyyy/MM/dd");
			//same
			//String UserName = this.employee_name;
			int dataCount = 0;//計算匯入401資料數
			BA_TOOLS ems_tools = BA_TOOLS.getInstance();
			
			try{
				//匯入PHF020401T0
				String sql401 = "" +
				" INSERT INTO ehf020404t0 (" +
				" EHF020404T0_01, EHF020404T0_02, EHF020404T0_03, EHF020404T0_04, EHF020404T0_05, " +
				" EHF020404T0_06, EHF020404T0_07, EHF020404T0_08, EHF020404T0_09, EHF020404T0_10,EHF020404T0_11,EHF020404T0_FLAG," +
				//" EHF020404T0_AFK,EHF020404T0_SFK, " +
				" USER_CREATE, USER_UPDATE, VERSION, DATE_UPDATE) " +
				" VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?," +
				//" ?, ?, " +
				" ?, ?, 1, NOW()) ";

				PreparedStatement pstmt_401 = conn.prepareStatement(sql401);
				P6PreparedStatement p6stmt_401 = new P6PreparedStatement(null, pstmt_401, null, sql401);
				
				for(int i=0; i<datalist.size(); i++){
					LinkedHashMap o =(LinkedHashMap) datalist.get(i);
					String[] csvDatas =(String[])o.values().toArray(new String[o.size()]); //(String[]) o.values().toArray();
					if ("".equals(csvDatas[9])) {
						csvDatas[9] = "00";
					}
					//打卡日期
					String dateString_01 = csvDatas[4]+"/"+csvDatas[5]+"/"+csvDatas[6]+" "+csvDatas[7]+":"+csvDatas[8]+":"+csvDatas[9];
					Date date = sdf.parse(dateString_01);
					
					//考勤日期
					String dateString_02 = csvDatas[1]+"/"+csvDatas[2]+"/"+csvDatas[3];
					Date date_01 = sdf_01.parse(dateString_02);
					
					//刷卡資料序號
					String EHF020404T0_01 = csvDatas[4]+String.format("%02d",  Integer.valueOf(csvDatas[5]))+String.format("%02d",  Integer.valueOf(csvDatas[6])) +String.format("%02d",  Integer.valueOf(csvDatas[7]))+String.format("%02d",  Integer.valueOf(csvDatas[8]))+String.format("%02d",  Integer.valueOf(csvDatas[9]))+csvDatas[0];//EHF020401T0_01

					//匯入資料
					int indexid = 1;
					p6stmt_401.setString(indexid, EHF020404T0_01);//刷卡資料序號
					indexid++;
					p6stmt_401.setString(indexid, EHF020404T0_01);//門禁系統號  ==> 日期+時間+卡號
					indexid++;
					p6stmt_401.setString(indexid, csvDatas[0]);//感應卡號
					indexid++;
					p6stmt_401.setString(indexid, ems_tools.covertDateToString(date, "yyyy/MM/dd"));//打卡日期
					indexid++;
					p6stmt_401.setString(indexid, ems_tools.covertDateToString(date, "HH:mm:ss"));//打卡時間
					indexid++;
					p6stmt_401.setString(indexid, ems_tools.covertDateToString(date, "yyyy/MM/dd HH:mm:ss"));//打卡日期時間
					indexid++;
					p6stmt_401.setString(indexid, "");//門禁刷卡機器代號
					indexid++;
					p6stmt_401.setObject(indexid, "");//動作代碼
					indexid++;
					p6stmt_401.setString(indexid, compId);//公司代碼
					indexid++;
					p6stmt_401.setString(indexid, "2");//資料來源
					indexid++;
					p6stmt_401.setObject(indexid, date_01);//考勤日期
					indexid++;
					p6stmt_401.setString(indexid, "1");  //門禁資料處理狀態
					indexid++;
					//p6stmt_401.setString(indexid, "0");  //薪資處理狀態
					//indexid++;
					p6stmt_401.setString(indexid, owner);//建立人員
					indexid++;
					p6stmt_401.setString(indexid, owner);//修改人員
					indexid++;
					
					p6stmt_401.executeUpdate();
					
					dataCount++;
					//System.out.println("匯入成功");
				}
				pstmt_401.close();
				p6stmt_401.close();
				
				//更新資料庫
				conn.commit();
				
			}catch(Exception e){
				e.printStackTrace();
			}
			//建立回傳訊息Map
			msgMap.put("DATALIST", datalist);
			msgMap.put("DATACOUNT", dataCount);
			return msgMap;
		
	}

	@Override
	public String getEMS_IMPORT_TYPE() {
		// TODO Auto-generated method stub
		return "XLS";
	}
	
	
	
	/**
	 * 取得員工感應卡資料清單
	 * @param conn
	 * @param employee_id
	 * @param comp_id
	 * @return
	 */
	public List getEmpList(Connection conn, String card_id, String comp_id){
		List return_list = new ArrayList();
		try{
			if("".equals(card_id)){
				return return_list;
			}
			String sql = "" +
					" SELECT " +
					" EHF020403T1_01 AS item_id, " +
					" EHF020403T1_01 AS item_value " +
					" FROM EHF020403T1 " +
					" WHERE 1=1 " +
					" AND EHF020403T1_02 = '"+card_id+"' " +
					" AND EHF020403T1_06 = '"+comp_id+"' ";
			//System.out.println("IMP_PHF020402_getEmpCardList_SQL="+sql);//Hedwig
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			return_list.add(bform);
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("item_id"));
				bform.setItem_value(rs.getString("item_value"));
				return_list.add(bform);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return return_list;
	}
}
