package com.spon.ems.hr.tools;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.spon.ems.vacation.tools.EMS_AttendanceAction;

public class EMS_CompanyTools {
	
	static Logger loger = Logger.getLogger(EMS_CompanyTools.class.getName());
	
	private volatile static EMS_CompanyTools ems_company_tools;
	private static String sta_user_id = "";
	private static String sta_comp_id = "";
	private static String use_comp_id = "";
	
	public static EMS_CompanyTools getInstance( String user_id, String comp_id, String use_comp_id) {
        /*if (ems_company_tools == null)
        	ems_company_tools = new EMS_CompanyTools();*/
        if(ems_company_tools == null) {
            synchronized(EMS_CompanyTools.class) {
            	ems_company_tools = new EMS_CompanyTools(user_id, comp_id, use_comp_id);
            }
        }
        return ems_company_tools;
    }
	
	private EMS_CompanyTools( String user_id, String comp_id, String use_comp_id){
		EMS_CompanyTools.sta_user_id = user_id;
		EMS_CompanyTools.sta_comp_id = comp_id;
		EMS_CompanyTools.use_comp_id = use_comp_id;
	}
	
	private EMS_CompanyTools(){
	}

	public void finalize() throws Throwable {

	}
	
	public static void setSta_user_id(String staUserId) {
		sta_user_id = staUserId;
	}

	public static String getSta_user_id() {
		return sta_user_id;
	}
	
	public static void setSta_comp_id(String staCompId) {
		sta_comp_id = staCompId;
	}
	
	public static String getSta_comp_id() {
		return sta_comp_id;
	}
	
	public static void setUse_comp_id(String useCompId) {
		use_comp_id = useCompId;
	}

	public static String getUse_comp_id() {
		return use_comp_id;
	}
	
	public Map getCompanyInf( Connection conn, String user_id, String comp_id, String use_comp_id){
		
		Map queryMap = new HashMap();
		
		try{
			if(!"".equals(use_comp_id) && use_comp_id != null){
				
			}else{
				String sql = "" +
				" SELECT * " +
				" FROM EHF010107T0 " +
				" WHERE 1=1 " +
				" AND HR_CompanySysNo = '"+comp_id+"' ";
				
				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				if(rs.next()) {
					
					queryMap.put("CompanySysNo", rs.getString("HR_CompanySysNo"));
					queryMap.put("CompanyNo", rs.getString("HR_CompanyNo"));
					queryMap.put("CompanyId", rs.getString("EHF010107T0_01"));
					queryMap.put("CompanyName_TW", rs.getString("HR_CompanyName"));
					queryMap.put("CompanyName_EN", rs.getString("EHF010107T0_02"));
					queryMap.put("Abbreviation", rs.getString("EHF010107T0_03"));
					queryMap.put("CreateDate", rs.getString("EHF010107T0_04"));
					queryMap.put("Leader", rs.getString("EHF010107T0_05"));
					queryMap.put("ContactPerson", rs.getString("EHF010107T0_06"));
					queryMap.put("RegisteredAddress_TW", rs.getString("EHF010107T0_07"));
					queryMap.put("RegisteredAddress_EN", rs.getString("EHF010107T0_08"));
					queryMap.put("BusinessAddress_TW", rs.getString("EHF010107T0_09"));
					queryMap.put("BusinessAddress_EN", rs.getString("EHF010107T0_10"));
					queryMap.put("RepPhone", rs.getString("EHF010107T0_11"));
					queryMap.put("Phone", rs.getString("EHF010107T0_12"));
					queryMap.put("Fax", rs.getString("EHF010107T0_13"));
					queryMap.put("Email", rs.getString("EHF010107T0_14"));
					queryMap.put("Introduction", rs.getString("EHF010107T0_15"));
					
				}
				rs.close();
				stmt.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return queryMap;
		
	}

}
