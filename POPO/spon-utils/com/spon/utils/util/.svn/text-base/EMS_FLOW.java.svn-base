package com.spon.utils.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.flow.tools.SPON_ParticularFlowForPWSUP_Tools;

/**
 *@author maybe
 *@version 1.0
 *@created 2017/1/4 下午3:45:33
 */
public class EMS_FLOW {
	
	private static EMS_FLOW ems_flow; 
		
	public static EMS_FLOW getInstance() {
        if (ems_flow == null){
        	synchronized(EMS_FLOW.class) {
        		ems_flow = new EMS_FLOW();
        	}
        }
        return ems_flow;
    }
	
	public EMS_FLOW(){
				
	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * 判斷是否為當前的簽核者
	 * @param flow_no
	 * @param form_no
	 * @param comp_id
	 * @throws Exception
	 */
	public void checkFlowIdentity(Connection conn, HttpServletRequest request, AuthorizedBean authbean, 
			String flow_no, String form_no, String form_type, String comp_id) throws Exception{
		
		//建立FLOW元件
		SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();		
		
		try{
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, form_no, comp_id);
			
			//取得當前已簽核LOG
			Map previous_sign_map = spon_flow_tools.getPreviousFlowFormStatus(flow_no, form_no, comp_id);
			
			if("01".equals((String)previous_sign_map.get("FLOW_SITE_SIGN_USER_TYPE"))){//處理人員類型
				//指定人員
				if(authbean.getUserId().equals((String)previous_sign_map.get("FLOW_SITE_SIGN_USER_KEY_VALUE"))){//處理人員的指定Key值
					//是指定的登入帳號
					request.setAttribute("sign", "yes");
				}else{
					//不是指定的登入帳號
					request.setAttribute("sign", "no");
				}
				
			}else if("02".equals((String)previous_sign_map.get("FLOW_SITE_SIGN_USER_TYPE"))){
				//指定欄位				
				//代理人
				if("AGENT_USER_ID".equals((String)previous_sign_map.get("FLOW_SITE_SIGN_USER_KEY_VALUE"))){//處理人員的指定Key值
					//判斷代理人是否為登入者 
					if(this.checkAgent(conn, authbean, form_type, form_no)){
						//代理人是登入者
						request.setAttribute("sign", "yes");
					}else{
						//代理人不是登入者
						request.setAttribute("sign", "no");
					}
				}				
				
			}else if("03".equals((String)previous_sign_map.get("FLOW_SITE_SIGN_USER_TYPE"))){
				//指定組織
				if("LV_ONE_DEPARTMENT_HEADS".equals((String)previous_sign_map.get("FLOW_SITE_SIGN_USER_KEY_VALUE"))){//處理人員的指定Key值
					if(authbean.getUserCode().equals((String)current_sign_map.get("SIGN_USER_ID"))){
						request.setAttribute("sign", "yes");
					}else{
						request.setAttribute("sign", "no");
					}
				}else if("LV_TWO_DEPARTMENT_HEADS".equals((String)previous_sign_map.get("FLOW_SITE_SIGN_USER_KEY_VALUE"))){//處理人員的指定Key值
					if(authbean.getUserCode().equals((String)current_sign_map.get("SIGN_USER_ID"))){
						request.setAttribute("sign", "yes");
					}else{
						request.setAttribute("sign", "no");
					}
				}
				/*if(authbean.getUserId().equals((String)current_sign_map.get("FLOW_SITE_SIGN_USER_KEY_VALUE"))){//處理人員的指定Key值
					//是指定的登入帳號
					request.setAttribute("sign", "yes");
				}else{
					//不是指定的登入帳號
					request.setAttribute("sign", "no");
				}*/
				
			}else{
				throw new Exception("簽核流程的處理人員類型，發生錯誤!!");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("判斷是否為當前的簽核者，發生錯誤!!" + e.toString());
		}
		
	}
	
	/**
	 * 判斷是否為FLOW最後一站
	 * @param conn
	 * @param request
	 * @param authbean
	 * @param flow_no
	 * @param form_no
	 * @param auto_flow_site_sn
	 * @param comp_id
	 * @throws Exception
	 */
	public void checkIsLastFlowStation(Connection conn, HttpServletRequest request, AuthorizedBean authbean, 
			String flow_no, String form_no, String comp_id) throws Exception{
		
		//建立FLOW元件
		SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
		
		try{
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, form_no, comp_id);
			
			boolean next_flow_station_flag = spon_flow_tools.isNextFlowStation(current_sign_map, comp_id);
			
			if(next_flow_station_flag){
				//有下一站
				request.setAttribute("last_sign", "yes");
			}else{
				//無下一站
				request.setAttribute("last_sign", "no");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception("判斷是否為FLOW最後一站，發生錯誤!!" + e.toString());
		}
		
	}
	
	/**
	 * 判斷代理人是否為登入者 
	 * @param conn
	 * @param authbean
	 * @param flow_no
	 * @param form_no
	 * @return
	 */
	private boolean checkAgent(Connection conn, AuthorizedBean authbean, String form_type, String form_no){
		
		boolean flag = false;
		String agent = "";
		
		try{
			if("EHF020200T0".equals(form_type)){
				//請假單
				agent = this.getVacationAgent(conn, authbean, form_no);
				
				if(agent.equals(authbean.getEmployeeID())){
					flag = true;
				}else{
					flag = false;
				}
				
			}else if("EHF020300T0".equals(form_type)){
				//出差單
				agent = this.getTravelAgent(conn, authbean, form_no);
				
				if(agent.equals(authbean.getEmployeeID())){
					flag = true;
				}else{
					flag = false;
				}
				
			}else{
				throw new Exception("FLOW編號，發生錯誤!!");
			}
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 請假單的代理人
	 * @param conn
	 * @param authbean
	 * @param form_no
	 * @return
	 */
	private String getVacationAgent(Connection conn, AuthorizedBean authbean,
			String form_no) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String agent = "";
		
		try{
			sql = "" +
			" SELECT EHF020200T0_07 " +
			" FROM EHF020200T0 " +
			" WHERE 1=1 " +
			" AND EHF020200T0_01 = '"+form_no+"' " +
			" AND EHF020200T0_18 = '"+authbean.getCompId()+"' ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				agent = rs.getString("EHF020200T0_07");
			}
			
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return agent;
	}
	
	/**
	 * 出差單的代理人
	 * @param conn
	 * @param authbean
	 * @param form_no
	 * @return
	 */
	private String getTravelAgent(Connection conn, AuthorizedBean authbean,
			String form_no) {
		// TODO Auto-generated method stub
		
		String sql = "";
		String agent = "";
		
		try{
			sql = "" +
			" SELECT EHF020300T0_07 " +
			" FROM EHF020300T0 " +
			" WHERE 1=1 " +
			" AND EHF020300T0_01 = '"+form_no+"' " +
			" AND EHF020300T0_21 = '"+authbean.getCompId()+"' ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				agent = rs.getString("EHF020300T0_07");
			}
			
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return agent;
	}
	
	/**
	 * 檢查當前登入者簽核流程角色
	 * @param conn
	 * @param authbean
	 * @param flow_role_id
	 * @return
	 */
	public boolean checkEmsFlowRole(Connection conn, AuthorizedBean authbean,
			String flow_role_id) {
		// TODO Auto-generated method stub
		
		boolean flag = false;
		
		try{
			if(!"".equals(flow_role_id)){
				
				String temp[] = flow_role_id.split(",");
				
				for(int i=0;i<temp.length;i++){
					if(temp[i].equals(authbean.getUserId())){
						flag = true;
						break;
					}
				}
				
			}
			
			/*
			if(flow_role_id.equals(authbean.getUserId())){
				flag = true;
			}
			*/
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		return flag;
	}
	
	/**
	 * 依據員工代碼檢查簽核流程角色
	 * @param conn
	 * @param employee_id
	 * @param flow_role_id
	 * @return
	 */
	public boolean checkEmsFlowRole(Connection conn, String employee_id,
			String flow_role_id, String comp_id) {
		
		boolean flag = false;
		String user_id = "";
		
		try{
			if(!"".equals(flow_role_id)){
				
				//取得登入帳號
				String sql = "" +
				" SELECT SC0030_01, SC0030_04 " +
				" FROM SC0030 " +
				" WHERE 1=1 " +
				" AND SC0030_03 = '"+employee_id+"' " +
				" AND SC0030_14 = '"+comp_id+"' ";
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					user_id = rs.getString("SC0030_01");			
				}
				
				rs.close();
				stmt.close();
				
				if(!"".equals(user_id)){
					
					String temp[] = flow_role_id.split(",");
					
					for(int i=0;i<temp.length;i++){
						if(temp[i].equals(user_id)){
							flag = true;
							break;
						}
					}
					
				}
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		return flag;
		
	}
	
	/**
	 * 判斷正確的flow_no
	 * @param conn
	 * @param form_no
	 * @param form_type
	 * @param flow_no
	 * @param comp_id
	 * @return
	 */
	public boolean checkFlowNo(Connection conn, String form_no, String form_type, String flow_no, String comp_id){
		
		boolean flag = false;
		String sql = "";
		
		try{
			if("EHF020200T0".equals(form_type)){
				
				sql = "" +
				" SELECT EHF020200T0_02 " +
				" FROM EHF020200T0 " +
				" WHERE 1=1 " +
				" AND EHF020200T0_01 = '"+form_no+"' " +
				" AND EHF020200T0_18 = '"+comp_id+"' ";
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					if(flow_no.equals(rs.getString("EHF020200T0_02"))){
						flag = true;
					}					
				}
				
				rs.close();
				stmt.close();
				
			}else if("EHF020300T0".equals(form_type)){
				
				sql = "" +
				" SELECT EHF020300T0_02 " +
				" FROM EHF020300T0 " +
				" WHERE 1=1 " +
				" AND EHF020300T0_01 = '"+form_no+"' " +
				" AND EHF020300T0_21 = '"+comp_id+"' ";
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					if(flow_no.equals(rs.getString("EHF020300T0_02"))){
						flag = true;
					}					
				}
				
				rs.close();
				stmt.close();
				
			}else if("EHF020800M0".equals(form_type)){
				
				sql = "" +
				" SELECT EHF020800T0_02 " +
				" FROM EHF020800T0 " +
				" WHERE 1=1 " +
				" AND EHF020800T0_01 = '"+form_no+"' " +
				" AND EHF020800T0_10 = '"+comp_id+"' ";
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					if(flow_no.equals(rs.getString("EHF020800T0_02"))){
						flag = true;
					}					
				}
				
				rs.close();
				stmt.close();
				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}
	
	/**
	 * 取Flow流程空表單編號
	 * @param conn
	 * @param receipt_type
	 * @param employee_id
	 * @param dep_id
	 * @param comp_id
	 * @return
	 */
	public String getFlowNo(Connection conn, AuthorizedBean authbean, String receipt_type, String employee_id,
			String dep_id, String comp_id) {
		// TODO Auto-generated method stub
		
		String flow_no = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
			
			String FLOW_HR = tools.getSysParam(conn, comp_id, "FLOW_HR");	//人資流程角色
			String FLOW_SE = tools.getSysParam(conn, comp_id, "FLOW_SE");	//秘書長流程角色
			String FLOW_CH = tools.getSysParam(conn, comp_id, "FLOW_CH");	//理事長流程角色
			
			if("EHF020200M0".equals(receipt_type)){
				
				if(this.checkEmsFlowRole(conn, employee_id, FLOW_HR, comp_id)){//人資流程角色
					
					flow_no = "HC_M_VACATION_FLOW";
					
				}else if(this.checkEmsFlowRole(conn, employee_id, FLOW_SE, comp_id)){//秘書長
					
					flow_no = "HC_M1_VACATION_FLOW";
					
				}else if(this.checkEmsFlowRole(conn, employee_id, FLOW_CH, comp_id)){//理事長
					
					flow_no = "HC_M2_VACATION_FLOW";
					
				}else{//員工
					
					flow_no = "HC_VACATION_FLOW";
					
				}
				
			}else if("EHF020800M0".equals(receipt_type)){
				
				if(this.checkEmsFlowRole(conn, employee_id, FLOW_HR, comp_id)){//人資流程角色
					
					flow_no = "HC_M_OVERTIME_FLOW";
					
				}else if(this.checkEmsFlowRole(conn, employee_id, FLOW_SE, comp_id)){//秘書長
					
					flow_no = "HC_M1_OVERTIME_FLOW";
					
				}else if(this.checkEmsFlowRole(conn, employee_id, FLOW_CH, comp_id)){//理事長
					
					flow_no = "HC_M2_OVERTIME_FLOW";
					
				}else{//員工
					
					flow_no = "HC_OVERTIME_FLOW";
					
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return flow_no;
	}

}
