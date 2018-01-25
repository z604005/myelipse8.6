package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.mvc.models.BaseFunction;
/**
 * 勞保匯入元件
 * @author SPONPC2
 *
 */
public class IMP_EHF030104 extends EMS_FILEIMPORT implements EMS_FILEIMPORT_XLS {
	
	private String user_emp_id = "";
	private String User_Name="";
	private String number="";//存放序號
	public IMP_EHF030104( String user_emp_id, String UserName ) {
		
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;
		this.User_Name=UserName;
	}
	
	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}
	
	/**
	 * 建立 XLS DATA LIST
	 * @param wbk
	 * @return
	 */
	public List generateXLSDataList( Connection conn, Workbook wbk, String comp_id ){
		
		List xlsdatalist = new ArrayList();
		Map dataMap = null;
		
		try{
			//取得第一個Sheet資料
			Sheet st = wbk.getSheet(0);
			
			//第一筆資料為表頭, 不列入匯入資料
			for( int i=2; i<st.getRows(); i++ ){
				
				dataMap = new HashMap();
				if(	 "".equals(st.getCell( 0, i).getContents())&&"".equals(st.getCell( 1, i).getContents())&&
					     "".equals(st.getCell( 2, i).getContents())&&"".equals(st.getCell( 3, i).getContents())){
						continue;
					}
				dataMap.put("EHF030104T0_02", 			st.getCell( 0, 2).getContents());  //EHF030104T0_02 健保版本代碼  20140120修改 BY 賴泓錡
				dataMap.put("EHF030104T0_02_VERSION", 	st.getCell( 1, 2).getContents());  //EHF030104T0_02_VERSION 健保版本名稱 20140120修改 BY 賴泓錡
				dataMap.put("EHF030104T1_03", 			st.getCell( 2, i).getContents());  //健保投保級距
				dataMap.put("EHF030104T1_07", 			st.getCell( 3, i).getContents());  //備註
				
				xlsdatalist.add(dataMap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return xlsdatalist;
	}
	
	/**
	 * 執行檔案匯入
	 * @param conn
	 * @param datalist
	 * @param owner
	 * @param comp_id
	 * @return
	 */
	protected Map fileimport( Connection conn, List datalist, String owner, String comp_id){
		
		Map msgMap = new HashMap();
		Map dataMap = null;
		try{
			int indexid = 1;
			//匯入筆數
			int dataCount = 0;

			Iterator it = datalist.iterator();
			
			while(it.hasNext()){
				
				//取得資料
				dataMap = (Map) it.next();
				this.fileimport_Details(conn, dataMap, owner, comp_id,number);
				conn.commit();
				dataCount++;
			}

			//重排加班明細的順序號碼
			String[] key = {"EHF030104T1_01","EHF030104T1_08"};
			this.reDoSN(key , " EHF030104T1_03", "EHF030104T1", 
			" AND EHF030104T1_01 = '"+number+"' " +
			" AND EHF030104T1_08 = '"+comp_id+"' ",comp_id);
			
			
			//建立回傳訊息Map
			msgMap.put("DATALIST", datalist);
			msgMap.put("DATACOUNT", dataCount);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return msgMap;
	}
	/**
	 * 新增健保 明細
	 * @param conn
	 * @param dataMap
	 * @param owner
	 * @param comp_id
	 * @param number
	 */
	protected void fileimport_Details( Connection conn, Map dataMap, String owner, String comp_id, String number){
		
		Map msgMap = new HashMap();
		BaseFunction base_tools= new BaseFunction();
		
		try{
			int indexid = 1;
			
			String sql = "" +
			" INSERT INTO EHF030104t1 ( EHF030104T1_01 ,EHF030104T1_02 ,EHF030104T1_03 " +
			" ,EHF030104T1_04 ,EHF030104T1_05 ,EHF030104T1_06 ,EHF030104T1_07 ,EHF030104T1_08 ,EHF030104T1_09" +
			" ,USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
			" VALUES (?,?,?,?,?,?,?,?,?,?,?, 1, NOW() ) "  ;

			
			//新增健保等級明細序號    20140722  BY Alvin
			//取得順序號碼
			int EHF030104T1_02 = 
			base_tools.getMaxSN("EHF030104T1_02", "EHF030104T1", 
			" AND EHF030104t1_01 = '"+number+"' " +
			" AND EHF030104t1_08 = '"+comp_id+"' ");
			
			
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);

			p6stmt.setString(indexid, number);  //健保等級序號
			indexid++;
			p6stmt.setInt(indexid, EHF030104T1_02);  //健保等級明細序號
			indexid++;
			p6stmt.setString(indexid, (String)dataMap.get("EHF030104T1_03")==""?"0":(String)dataMap.get("EHF030104T1_03"));  //健保投保級距
			indexid++;
			
			
			p6stmt.setString(indexid, "0");  //個人負擔
			indexid++;
			p6stmt.setString(indexid, "0");  //雇主負擔
			indexid++;
			p6stmt.setString(indexid, "0");  //合計金額
			indexid++;
			
			p6stmt.setString(indexid, (String)dataMap.get("EHF030104T1_07"));  //備註
			indexid++;
			
			p6stmt.setString(indexid, comp_id);  //公司代碼
			indexid++;
			p6stmt.setString(indexid, "0");  //健保投保等級(顯示序號)
			indexid++;
			p6stmt.setString(indexid, owner);  //建立人員
			indexid++;
			p6stmt.setString(indexid, owner);  //修改人員
			indexid++;
			//System.out.println(p6stmt.getQueryFromPreparedStatement());
			p6stmt.executeUpdate();

			pstmt.close();
			p6stmt.close();	
			
			base_tools.commit();
			base_tools.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Override
	/**
	 * 檢核匯入資料
	 */
	protected Map chkImportDataList(Connection conn, List datalist,String compId) {
		// TODO Auto-generated method stub
		
		Map err_msgMap = new HashMap();
		List err_dataList = new ArrayList();
		String sql = "";
		Map dataMap = null;
		int aInt;
		
		String ERROR_messenger="";
		
		try{
			
			//檢核未通過的筆數
			int ngDataCount = 0;
			int NullDataCount = 0;
			int Count = 0;
			
			boolean check_Null=false;
			
			Iterator it = datalist.iterator();
			boolean check_Exist=false;
			while(it.hasNext()){
				Count++;
				//取得資料
				dataMap = (Map) it.next();
				
				
				
				//預先檢查第一筆資料的健保規則代號、健保規則名稱是否有填寫，若是未填寫，則之後每一筆皆不匯入。
				if(Count==1){	
					if("".equals((String)dataMap.get("EHF030104T0_02")) 		|| (String)dataMap.get("EHF030104T0_02") == null){
						if(dataMap.get("error")==null)
							dataMap.put("error", "健保規則代號未填寫。" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"健保規則代號未填寫。" + "<br>");
						}
						check_Null=true;
						
					}
					if("".equals((String)dataMap.get("EHF030104T0_02_VERSION")) 		|| (String)dataMap.get("EHF030104T0_02_VERSION") == null){
						if(dataMap.get("error")==null)
							dataMap.put("error", "健保規則名稱未填寫。" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"健保規則名稱未填寫。" + "<br>");
						}
						check_Null=true;
					}

					if(check_Null){
						ERROR_messenger=dataMap.get("error").toString();
						// 新增被移除的項目到檢核未通過的清單
						err_dataList.add(dataMap);
						// 移除此項匯入Rank
						it.remove();
						ngDataCount++;
						//結束此次迴圈
						continue;
					}
				}else{
					if(check_Null){
						dataMap.put("error", ERROR_messenger);
						// 新增被移除的項目到檢核未通過的清單
						err_dataList.add(dataMap);
						// 移除此項匯入Rank
						it.remove();
						ngDataCount++;
						//結束此次迴圈
						continue;
					}
				}
				
				//先檢核資料面是否有問題
				
				if("".equals((String)dataMap.get("EHF030104T1_03")) 		|| (String)dataMap.get("EHF030104T1_03") == null){

					if(dataMap.get("error")==null)
						dataMap.put("error", "健保投保級距未正確填寫!" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"健保投保級距未正確填寫!" + "<br>");
					}
					
					NullDataCount++;
					//資料如果未填寫，則也不需要繼續檢核，直接結束此次迴圈
					continue;
				}

				
				//檢查投保級距是否填入正確數字
					try {
				      aInt = Integer.parseInt((String)dataMap.get("EHF030104T1_03"));
				    } catch (NumberFormatException e) {
				    	System.out.println("勞保等級匯入："+(String)dataMap.get("EHF030104T1_03").toString() + "不是數字");

				      	if(dataMap.get("error")==null)
							dataMap.put("error", "請輸入數字。" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"請輸入數字。" + "<br>");
						}
				      	check_Exist=true;
				    }
				    
				    //檢查備註字數
				    if(((String)dataMap.get("EHF030104T1_07")).length()>=26){
				    	if(dataMap.get("error")==null)
							dataMap.put("error", "備註字數請勿超過25個字。" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"備註字數請勿超過25個字。" + "<br>");
						}
				      	check_Exist=true;
				    }
				    //檢查投保級距字數字數
				    if(((String)dataMap.get("EHF030104T1_03")).length()>=9){
				    	if(dataMap.get("error")==null)
							dataMap.put("error", "投保級距字數請勿超過8個字。" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"投保級距字數請勿超過8個字。" + "<br>");
						}
				      	check_Exist=true;
				    }

					
				    //檢查健保版本代碼字數
				    if(((String)dataMap.get("EHF030104T0_02")).length()>=5){
				    	if(dataMap.get("error")==null)
							dataMap.put("error", "健保版本代碼字數請勿超過4個字。" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"健保版本代碼字數請勿超過4個字。" + "<br>");
						}
				      	check_Exist=true;
				    }
				    
				    //檢查健保版本名稱字數
				    if(((String)dataMap.get("EHF030104T0_02_VERSION")).length()>=26){
				    	if(dataMap.get("error")==null)
							dataMap.put("error", "健保版本名稱字數請勿超過25個字。" + "<br>");
						else{
							dataMap.put("error", dataMap.get("error").toString()+" "+"健保版本名稱字數請勿超過25個字。" + "<br>");
						}
				      	check_Exist=true;
				    }
				    
				    
				 if(!check_Exist){
				//驗證資料庫是否有重複資料
				sql = " SELECT EHF030104T0_02 FROM EHF030104T0 " +
				   " WHERE 1=1 " +
				   " AND EHF030104T0_02 = '"+(String)dataMap.get("EHF030104T0_02")+"' " +
				   " AND EHF030104T0_05 = '"+compId+"' " ;
				boolean sql_Vacation_Exist=this.check_Exist(conn, sql);
				
				
				if(!sql_Vacation_Exist){
					//無重複表頭
					//新增表頭資訊
						sql = "" +
						" INSERT INTO EHF030104t0 ( " +
						" EHF030104T0_02," +//EHF030104T0_02 健保版本代碼  20140120修改 BY 賴泓錡
						" EHF030104T0_02_VERSION ," +//EHF030104T0_02_VERSION 健保版本名稱 20140120修改 BY 賴泓錡
						" EHF030104T0_03, EHF030104T0_04, EHF030104T0_05, " +
						" USER_CREATE ,USER_UPDATE ,VERSION ,DATE_UPDATE ) " +
						" VALUES (?,?,?,?,?,?,?, 1, NOW() ) "  ;

						PreparedStatement pstmt = conn.prepareStatement(sql);
						P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
						int indexid = 1;
						p6stmt.setString(indexid, (String)dataMap.get("EHF030104T0_02"));  //EHF030104T0_02 健保版本代碼  20140120修改 BY 賴泓錡
						indexid++;
						p6stmt.setString(indexid, (String)dataMap.get("EHF030104T0_02_VERSION"));  //EHF030104T0_02_VERSION 健保版本名稱 20140120修改 BY 賴泓錡
						indexid++;
						p6stmt.setString(indexid, compId);  //公司組織(代碼)
						indexid++;
						p6stmt.setString(indexid, (String)dataMap.get("EHF030104T1_07"));  //備註
						indexid++;
						p6stmt.setString(indexid, compId);  //公司代碼
						indexid++;
						p6stmt.setString(indexid, User_Name);  //建立人員
						indexid++;
						p6stmt.setString(indexid, User_Name);  //修改人員
						indexid++;
						
						//System.out.println(p6stmt.getQueryFromPreparedStatement());
						p6stmt.executeUpdate();
				
						sql = "SELECT LAST_INSERT_ID() AS ID FROM EHF030104t0 ";
					
						Statement stmt_01 =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
						ResultSet rs_01=stmt_01.executeQuery(sql);
						if(rs_01.next()){
							number=rs_01.getString("ID");
						//System.out.println("ID==>"+rs.getShort("ID"));
						}
						rs_01.close();
						stmt_01.close();
						p6stmt.close();
						pstmt.close();
						conn.commit();
				}else{
					sql = " SELECT EHF030104T0_01 FROM EHF030104T0 " +
					   " WHERE 1=1 " +
					   " AND EHF030104T0_05 = '"+compId+"' " +
					   " AND EHF030104T0_02 = '"+(String)dataMap.get("EHF030104T0_02")+"' "+
					   " AND EHF030104T0_02_VERSION = '"+(String)dataMap.get("EHF030104T0_02_VERSION")+"' "+
					   " ORDER BY EHF030104T0_01 DESC LIMIT 1" ;
					
					Statement stmt_01 =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
					ResultSet rs_01=stmt_01.executeQuery(sql);
					if(rs_01.next()){
						number=rs_01.getString("EHF030104T0_01");
					//System.out.println("ID==>"+rs.getShort("ID"));
					}
					rs_01.close();
					stmt_01.close();
				}
			}
				sql = " SELECT * FROM EHF030104T1 " +
			   	  " WHERE 1=1 " +
			   	  " AND EHF030104T1_01 = '"+number+"' " +
			   	  " AND EHF030104T1_03 = '"+(String)dataMap.get("EHF030104T1_03")+"' " +
			   	  " AND EHF030104T1_08 = '"+compId+"' " ;
				boolean Vacation_Exist=this.check_Exist(conn, sql);
				if(Vacation_Exist){
					check_Exist=true;
					if(dataMap.get("error")==null)
						dataMap.put("error", "已有該筆資料，請檢查。" + "<br>");
					else{
						dataMap.put("error", dataMap.get("error").toString()+" "+"已有該筆資料，請檢查。" + "<br>");
					}
				}
				
				if (check_Exist) {
					// 新增被移除的項目到檢核未通過的清單
					err_dataList.add(dataMap);
					// 移除此項匯入Rank
					it.remove();
					ngDataCount++;
				}
				
			}
			//建立回傳訊息Map
			err_msgMap.put("NGDATALIST", err_dataList);
			err_msgMap.put("NGDATACOUNT", ngDataCount);

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return err_msgMap;
	}

	@Override
	public String getEMS_IMPORT_TYPE() {
		// TODO Auto-generated method stub
		//設定匯入檔案的型態
		return "XLS";
	}
	
	/**
	 * 驗證SQL語法是否有資料
	 * @param conn
	 * @param sql
	 * @return
	 */
	private boolean check_Exist(Connection conn,String sql){
		boolean Exist=false;
		try {
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				Exist=true;
			}
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Exist;
	}
	/**
	 * 重排明細順序號碼
	 * @param key
	 * @param sn_field
	 * @param table_name
	 * @param key_sql
	 * @param request 
	 */
	public void reDoSN( String[] key, String sn_field, String table_name, String key_sql, String comp_id ){
		BaseFunction base_tools = new BaseFunction(comp_id);
		try{
			StringBuffer sql_for_key_select = new StringBuffer();
			StringBuffer sql_for_key_update = new StringBuffer();
			//處理Table Key Set
			for(int i=0; i < key.length; i++){
				//sql_for_key_select
				sql_for_key_select.append(" CAST(");
				sql_for_key_select.append(key[i]);
				sql_for_key_select.append(" AS CHAR(30)) AS "+key[i]+", ");
			}
			
			//重新排明細的 - "順序號碼"
			String sql = "" +
			" SELECT " +
			" "+sql_for_key_select.toString()+" " +  //組成 KEY 
			sn_field + " AS SN " +
			" FROM "+table_name+" " +
			" WHERE 1=1 " +
			key_sql +  //KEY SQL 設定
			" ORDER BY "+sn_field+" ";
			//取得明細清單
			List data_list = base_tools.queryList(sql);
			Iterator it = data_list.iterator();
			Map tempMap  = null;
			int snCount = 0;
			List sql_list = new ArrayList();
			//針對每一筆明細清單做順序號碼的處裡
			while(it.hasNext()){
				sql_for_key_update = new StringBuffer();
				tempMap = (Map) it.next();
				
				
				//處理Table Key Set
				for(int j=0; j < key.length; j++){
					//sql_for_key_update
					sql_for_key_update.append(" AND ");
					sql_for_key_update.append(key[j]);
					sql_for_key_update.append(" = '");
					sql_for_key_update.append((String)tempMap.get(key[j]));
					sql_for_key_update.append("' ");
				}
				
				sql = "" +
				" UPDATE "+table_name+" SET " +
				" EHF030104T1_09 = "+snCount+" " +
				" WHERE 1=1 " +
				//" AND ESF010300T1_01 = "+(Integer)tempMap.get("ESF010300T1_01")+" " +  //委外加工單資料序號
				" "+sql_for_key_update.toString()+" " +  //組成 KEY
				" AND "+sn_field+" = '"+(Integer)tempMap.get("SN")+"' ";  //序號
				sql_list.add(sql);
				snCount++;
			}
			base_tools.executeBatchSQL(sql_list);
			base_tools.commit();
			base_tools.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}