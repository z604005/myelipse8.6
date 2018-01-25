package com.spon.ems.fileimport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import vtgroup.ems.common.vo.AuthorizedBean;

/**
 * 匯入驗證原件
 * @author SPONPC2
 * By  泓錡
 *
 */
public class IMP_Verification {
	private AuthorizedBean authbean = null;
	private String user_emp_id = "";		
	private HttpServletRequest request= null;
	boolean check_Exist = false;
	
	
	public IMP_Verification( HttpServletRequest request, String user_emp_id, AuthorizedBean authbean ) {
		
		//建立相關元件所需資訊
		this.user_emp_id = user_emp_id;		
		this.authbean = authbean;
		this.request= request;
	}
	
	
	/**
	 *  驗證"使用日期" '年 ' 長度不能大於4位數
	 * @param csvDatas
	 * @return
	 */
	protected boolean check_year(String csvDatas) {
		if (csvDatas.length() != 4) {
			check_Exist = true;
		}
		return check_Exist;
	}

	/**
	 *  驗證"使用日期" 月份不能大於12
	 * @param csvDatas
	 * @return
	 */
	protected boolean check_month(String csvDatas) {
		if (Integer.parseInt(csvDatas) >= 13) {
			check_Exist = true;
		}
		return check_Exist;
	}

	/**
	 *  驗證"使用日期" 日期不能大於當月最後一天
	 * @param csvDatas
	 * @param Last_month
	 * @return
	 */
	protected boolean check_day(String csvDatas, String Last_month) {
		if (Integer.parseInt(csvDatas) > Integer.parseInt(Last_month)) {
			check_Exist = true;
			// System.out.println("日期不得大於最後一天");
		}
		return check_Exist;
	}

	/**
	 *  驗證請假"時"是否大於24
	 * @param csvDatas
	 * @return
	 */
	protected boolean check_hour(String csvDatas) {
		if (Integer.parseInt(csvDatas) >= 25) {
			check_Exist = true;
		}
		return check_Exist;
	}

	/**
	 *  驗證請假"分"是否大於60
	 * @param csvDatas
	 * @return
	 */
	protected boolean check_Minute(String csvDatas) {
		if (Integer.parseInt(csvDatas) > 60) {
			check_Exist = true;
		}
		return check_Exist;
	}
		
	/**
	 * 驗證SQL語法是否有資料
	 * @param conn
	 * @param sql
	 * @return
	 */
	protected boolean check_Exist(Connection conn,String sql){
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
	 * 檢查員工姓名是否在公司裡
	 * 
	 * @param empMap2
	 * @param name 
	 */
	/*protected boolean chack(Map empMap2, String name) {
		Set set = empMap2.keySet();
		Iterator it = set.iterator();
		boolean ceacka = false;
		while (it.hasNext()) {
			String key = (String) it.next();
			if (!((Map) (empMap.get(key))).get("EMPLOYEE_NAME").equals(name)) {
				continue;
			} else {
				ceacka = true;
				break;
			}
		}
		return ceacka;
	}*/
		
	/**
	 *	利用名稱找尋員工工號
	 *	前提是此員工名稱必須要在公司裡
	 * 
	 * @param empMap2
	 * @param dataMap 
	 */
	/*protected String chack_NAME(Map empMap2, Map dataMap ,String name) {
		Set set = empMap2.keySet();
		Iterator it = set.iterator();
		String ceacka = "";
		while (it.hasNext()) {
			String key = (String) it.next();
			if (!((Map) (empMap.get(key))).get("EMPLOYEE_NAME").equals(name)) {
				continue;
			} else {
				ceacka = (String) ((Map) (empMap.get(key))).get("EMPLOYEE_ID");
				break;
			}
		}
		return ceacka;
	}*/
	
	/**
	 * 預先刪除所有匯入檔案重複資料
	 * @param datalist
	 */
	protected void DELETE_Overlap(List datalist) {
		
		Set set = new HashSet();
		  List newList = new ArrayList();
		  for (Iterator iter = datalist.iterator(); iter.hasNext(); ) {
		   Object element = iter.next();
		   if (set.add(element))
			   newList.add(element);
		  }
		  datalist.clear();
		  datalist.addAll(newList);
		/*  
		for (int i = 0; i < datalist.size(); i++) {
			LinkedHashMap data_list = (LinkedHashMap) datalist.get(i);

			String[] csvDatas = (String[]) data_list.values().toArray(new String[data_list.size()]); 
			List chklist = new ArrayList();
			chklist.addAll(datalist);
			for (int k = i; k >= 0; k--) {
				chklist.remove(k);
			}
			for (int j = 0; j < chklist.size(); j++) {
				LinkedHashMap list_02 = (LinkedHashMap) chklist.get(j);
				String[] chkDatas = (String[]) list_02.values().toArray(new String[list_02.size()]);
				String Comparing_Strings_01="";
				String Comparing_Strings_02="";
				for (int chkData = 0; chkData < chkDatas.length; chkData++) {
					Comparing_Strings_01+=chkDatas[chkData];
					Comparing_Strings_02+=csvDatas[chkData];
				}
				if (Comparing_Strings_01.equals(Comparing_Strings_02)) {
					// db_list.add(csvDatas);
					// db_count++;
					datalist.remove(i);
					i=i-1;
					continue;
				}
			}
		}*/
	}
	
	/**
	 * 不足補0
	 * @param str
	 * @param strLength
	 * @return
	 */
	public static String addZeroForNum(String str, int strLength) {
	    int strLen = str.length();
	    if (strLen < strLength) {
	        while (strLen < strLength) {
	            StringBuffer sb = new StringBuffer();
	            sb.append("0").append(str);// 左補0
	            // sb.append(str).append("0");//右補0
	            str = sb.toString();
	            strLen = str.length();
	        }
	    }

	    return str;
	}
	
	/**
	 * 檢核Email格式
	 * @param csvDatas
	 * @return
	 */
	protected boolean isEmail(String csvDatas) {
		if(!GenericValidator.isEmail(csvDatas)) {
			check_Exist = true;
		}
		return check_Exist;
	}
	
	/**
	 * 檢核公司行號統一編號
	 * @param csvDatas
	 * @return
	 */
	protected boolean isTWCompanyId(String csvDatas) {
		
		try{			
			if(!csvDatas.equals("") & csvDatas.length()==8){
				int v[] = {1,2,1,2,1,2,4,1};
	            int temp=0;
	            int sum=0;
	            int sum1=0;
	            int aAddition=0;
	            for (int i=0;i<v.length;i++){
	                temp= Integer.parseInt(String.valueOf(csvDatas.charAt(i))) * v[i];
	                aAddition = ((temp / 10) + (temp % 10));
	
	                sum += (aAddition == 10)? 1: aAddition;
	                sum1 += (aAddition == 10)? 0: aAddition;
	            }
	            if (sum%10 ==0 ){
	            	
	            }else if(sum1%10 ==0){
	            	
				}else{
					check_Exist = true;
	            }
			}else{
				check_Exist = true;
			}						
			
		} catch (Exception e) {
			check_Exist = true;
			
		} finally {
			
		}
		
		return check_Exist;
	}
	
	/**
	 * 檢核身分證字號
	 * @param csvDatas
	 * @return
	 */
	protected boolean isTWID(String csvDatas)
	{
		if(!csvDatas.equals("") & csvDatas.length()==10)
		{
			String EA="ABCDEFGHJKLMNPQRSTUVWXYZIO";
			if(EA.indexOf(String.valueOf(csvDatas.charAt(0)))== -1){
				check_Exist = true;
			}
			String EB="0123456789";
			for(int j = 2; j <= 10; j++){
				if(EB.indexOf(String.valueOf(csvDatas.charAt(j-1)))== -1){
					check_Exist = true;
				}
			}
			if (!check_Exist){
				String v[][] = {{"A", "台北市"}, {"B", "台中市"}, {"C", "基隆市"},
				         {"D", "台南市"}, {"E", "高雄市"}, {"F", "台北縣"}, {"G", "宜蘭縣"},
				         {"H", "桃園縣"}, {"J", "新竹縣"}, {"K", "苗栗縣"}, {"L", "台中縣"},
				         {"M", "南投縣"}, {"N", "彰化縣"}, {"P", "雲林縣"}, {"Q", "嘉義縣"},
				         {"R", "台南縣"}, {"S", "高雄縣"}, {"T", "屏東縣"}, {"U", "花蓮縣"},
				         {"V", "台東縣"}, {"X", "澎湖縣"}, {"Y", "陽明山"}, {"W", "金門縣"},
				         {"Z", "連江縣"}, {"I", "嘉義市"}, {"O", "新竹市"}
				      };
				 
				      int inte = -1;
				      String s1 = String.valueOf(Character.toUpperCase(csvDatas.charAt(0)));
				      for(int i = 0; i < 26; i++){
				         if(s1.compareTo(v[i][0]) == 0){
				            inte = i;
				         }
				      }
				      int total = 0;
				      int all[] = new int[11];
				      String E = String.valueOf(inte + 10);
				      int E1 = Integer.parseInt(String.valueOf(E.charAt(0)));
				      int E2 = Integer.parseInt(String.valueOf(E.charAt(1)));
				      all[0] = E1;
				      all[1] = E2;
				      try{
				         for(int j = 2; j <= 10; j++)
				            all[j] = Integer.parseInt(String.valueOf(csvDatas.charAt(j - 1)));
				         for(int k = 1; k <= 9; k++)
				            total += all[k] * (10 - k);
				         total += all[0] + all[10];
				         if(total % 10 != 0)
				         {
				        	 check_Exist = true;
				         }
				      }
				      catch(Exception ee){;
				      }
			}
		}
		else
		{
			check_Exist = true;
		}
		
		return check_Exist;

	}
	
	/**
	 * 檢核最小長度
	 * @param csvDatas
	 * @param length
	 * @return
	 */
	protected boolean isLeastLength(String csvDatas,int length)
	{
		 if(!GenericValidator.isInRange(csvDatas.getBytes().length,length,99))
	     {
			 check_Exist = true;
	     }
		 
		 return check_Exist;
	}
	
	/**
	 * 檢核數字型態是否有值，如果為數值型態必填的數值 zerochk true 可為零, false 不得為零
	 * @param csvDatas
	 * @return
	 */
	protected boolean isNumEmpty(String csvDatas, boolean zerochk) {
		/*csvDatas.replaceAll("-", "");
		csvDatas.replaceAll("(", "");
		csvDatas.replaceAll(")", "");
		csvDatas.replaceAll("#", "");*/
		if (GenericValidator.isInt(csvDatas) || GenericValidator.isDouble(csvDatas) || GenericValidator.isLong(csvDatas) || GenericValidator.isShort(csvDatas ) ){			 			
			 if (!zerochk){
				 if ( csvDatas.equals("0") | csvDatas.equals("0.0")) {
					 check_Exist = false;
				 }
			 }else{
				 check_Exist = true;
			 }
			
		}else{
			check_Exist = false;
		}
		
		return check_Exist;
	}
	
	/**
	 * 從EMS_Category取得相對應的代號(ITEM_ID)
	 * @param str
	 * @param classKey
	 * @return
	 */
	public String getEMSCategory(Connection conn, String str, String classKey, String comp_id) {
		
		String sql = "";
		String ITEM_ID = "";
		
		try{
			sql = "" +
			" SELECT EMS_CategoryT1_04 " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 AND EMS_CategoryT1_05 = '"+str+"' " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = '"+classKey+"' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				ITEM_ID = rs.getString("EMS_CategoryT1_04");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	    return ITEM_ID;
	}
	
	/**
	 * 從STOCKD取得相對應的代號(ITEM_ID)
	 * @param str
	 * @param classKey
	 * @return
	 */
	public String getSTOCKD(Connection conn, String str, String comp_id) {
		
		String sql = "";
		String ITEM_ID = "";
		
		try{
			sql = "" +
			" SELECT STOCKD_04 " +
			" FROM STOCKD " +
			" WHERE 1=1 " +
			" AND STOCKD_05 = '"+str+"' " +
			" AND CompanySysNo = '"+comp_id+"' " ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				ITEM_ID = rs.getString("STOCKD_04");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	    return ITEM_ID;
	}
	
	/**
	 * 從LEXICON取得相對應的代號(ITEM_ID)
	 * @param str
	 * @param classKey
	 * @return
	 */
	public String getLEXICON(Connection conn, String str, String classKey, String comp_id) {
		
		String sql = "";
		String ITEM_ID = "";
		
		try{
			/*sql = "" +
			" SELECT D.LEXICOND_04 " +
			" FROM lexiconM M " +
			" LEFT JOIN lexiconD D ON D.LEXICOND_01 = M.LEXICONM_01 AND D.CompanySysNo = M.CompanySysNo AND D.LEXICOND_05 = '"+str+"' " +
			" WHERE 1=1 " +
			" AND M.LEXICONM_01 = '"+classKey+"' " +
			" AND M.CompanySysNo = '"+comp_id+"' " ;*/
			
			sql = "" +
			" SELECT LEXICOND_04 " +
			" FROM LEXICOND " +
			" WHERE 1=1 " +
			" AND LEXICOND_04 = '"+str+"' " +
			" AND LEXICOND_01 = '"+classKey+"' " +
			" AND CompanySysNo = '"+comp_id+"' " ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				ITEM_ID = rs.getString("LEXICOND_04");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	    return ITEM_ID;
	}
	
	/**
	 * 檢核庫別櫃位上下是否有階層關係
	 * @param conn
	 * @param Father_ID
	 * @param Son_ID
	 * @param comp_id
	 * @return
	 */
	public boolean isParent(Connection conn, String Father_ID, String Son_ID, String comp_id) {
		
		String Father_sql = "";
		String Son_sql = "";
		String Father_SuperClass = "";
		String Father_SuperCode = "";
		String Son_SuperClass = "";
		String Son_SuperCode = "";
		
		try{
			Father_sql = "" +
			" SELECT D.STOCKD_01, D.STOCKD_04 " +
			" FROM STOCKD D " +
			" WHERE 1=1 " +
			" AND D.STOCKD_04 = '"+Father_ID+"' " +
			" AND D.CompanySysNo = '"+comp_id+"' " ;
			
			Statement Father_stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet Father_rs = Father_stmt.executeQuery(Father_sql);
			
			if(Father_rs.next()){
				Father_SuperClass = Father_rs.getString("STOCKD_01");
				Father_SuperCode = Father_rs.getString("STOCKD_04");
			}
			
			Father_rs.close();
			Father_stmt.close();
			
			Son_sql = "" +
			" SELECT M.STOCKM_02, M.STOCKM_03 " +
			" FROM STOCKM M " +
			" LEFT JOIN STOCKD D ON D.STOCKD_01 = M.STOCKM_01 AND D.CompanySysNo = M.CompanySysNo AND D.STOCKD_04 = '"+Son_ID+"' " +
			" WHERE 1=1 " +
			" AND M.STOCKM_01 = D.STOCKD_01 " +
			" AND M.CompanySysNo = '"+comp_id+"' " ;
			
			Statement Son_stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet Son_rs = Son_stmt.executeQuery(Son_sql);
			
			if(Son_rs.next()){
				Son_SuperClass = Son_rs.getString("STOCKM_02");
				Son_SuperCode = Son_rs.getString("STOCKM_03");
			}
			
			Son_rs.close();
			Son_stmt.close();
			
			if(Son_SuperClass.equals(Father_SuperClass) && Son_SuperCode.equals(Father_SuperCode)){
				
			}else{
				check_Exist = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}

	    return check_Exist;
	}

	/**
	 * 取得廠商代號
	 * @param conn
	 * @param str
	 * @param comp_id
	 * @return
	 */
	public String getSupplier(Connection conn, String str, String comp_id) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String supplier = "";
		
		try{
			sql = "" +
			" SELECT SM010400T0_01 " +
			" FROM SM010400T0 " +
			" WHERE 1=1 " +
			" AND SM010400T0_02 = '"+str+"' " +
			" AND CompanySysNo = '"+comp_id+"' " ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				supplier = rs.getString("SM010400T0_01");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	    return supplier;
	}

	/**
	 * 取得客戶代號
	 * @param conn
	 * @param str
	 * @param comp_id
	 * @return
	 */
	public String getCustomer(Connection conn, String str, String comp_id) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String customer = "";
		
		try{
			sql = "" +
			" SELECT SM010300T0_01 " +
			" FROM SM010300T0 " +
			" WHERE 1=1 " +
			" AND SM010300T0_02 = '"+str+"' " +
			" AND CompanySysNo = '"+comp_id+"' " ;
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				customer = rs.getString("SM010300T0_01");
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	    return customer;
	}
	
}
