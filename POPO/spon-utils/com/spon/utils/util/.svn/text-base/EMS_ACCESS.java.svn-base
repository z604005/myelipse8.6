package com.spon.utils.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;

import vtgroup.ems.common.vo.AuthorizedBean;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 下午2:53:42
 */
public class EMS_ACCESS {
	
	private volatile static EMS_ACCESS ems_access; 
	
	private EMS_FLOW ems_flow;
		
	public static EMS_ACCESS getInstance() {
        /*if (ems_access == null)
        	ems_access = new EMS_ACCESS();*/
        if(ems_access == null) {
            synchronized(EMS_ACCESS.class) {
            	ems_access = new EMS_ACCESS();
            }
        }
        return ems_access;
    }
	
	private EMS_ACCESS(){
		ems_flow = EMS_FLOW.getInstance();
	}

	public void finalize() throws Throwable {

	}
	
	/**
	 * 檢核人員的身份別，用於前端畫面顯示
	 * @param request
	 * @param authbean
	 * @param conn
	 * @param flow_type
	 * @param applyemp_id
	 * @param receipt_no
	 */
	public void checkIdentity(HttpServletRequest request,
			AuthorizedBean authbean, Connection conn, String flow_type, String applyemp_id, String receipt_no) {
		// TODO Auto-generated method stub
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			// 判斷當前登入者是否為申請者本人
			if (applyemp_id.equals(authbean.getEmployeeID())) {
				// 為申請者本人登入
				request.setAttribute("personself", "yes");
			}
			String HR 		= tools.getSysParam(conn, authbean.getCompId(),"HR_IDENTITY");		//預設人資		100
			String SYSTEM 	= tools.getSysParam(conn, authbean.getCompId(),"SYSTEM_IDENTITY");	//系統管理者	000
			String USER 	= tools.getSysParam(conn, authbean.getCompId(),"USER_IDENTITY");	//一般使用者	001
			String MANAGER 	= tools.getSysParam(conn, authbean.getCompId(),"MANAGER_IDENTITY");	//主管使用者	002
			String AC 		= tools.getSysParam(conn, authbean.getCompId(),"AC_IDENTITY");		//會計		110
			String BOSS 	= tools.getSysParam(conn, authbean.getCompId(),"BOSS_IDENTITY");	//老闆		111
			String SALSE 	= tools.getSysParam(conn, authbean.getCompId(),"SALSE_IDENTITY");	//業務		112
			String ADMIN 	= tools.getSysParam(conn, authbean.getCompId(),"ADMIN_IDENTITY");	//行政		113
			
			// 判斷當前登入者的身份，主管或是人事經辦
			if (this.checkEmsRoleEmp(conn, authbean, HR)) {
				// 人事經辦
				request.setAttribute("person_manager", "yes");
				request.setAttribute("boss", "yes");
			} else if (this.checkEmsRoleEmp(conn, authbean, AC)) {
				//會計				
				request.setAttribute("accounting", "yes");
			} else if (this.checkEmsRoleEmp(conn, authbean, BOSS)) {
				//老闆				
				request.setAttribute("boss_manager", "yes");
			} else if (this.checkManager(conn, authbean) && this.checkEmsRoleEmp(conn, authbean, MANAGER)) {
				// 主管
				request.setAttribute("person_manager", "no");
				request.setAttribute("boss", "yes");
			} else if (!this.checkManager(conn, authbean) && this.checkEmsRoleEmp(conn, authbean, SALSE)) {
				// 業務且非主管
				request.setAttribute("salse", "yes");
			} else if (this.checkManager(conn, authbean) && this.checkEmsRoleEmp(conn, authbean, SALSE)) {
				// 業務主管
				request.setAttribute("salse", "yes");
				request.setAttribute("boss", "yes");
			} else if (this.checkEmsRoleEmp(conn, authbean, ADMIN)) {
				// 行政
				request.setAttribute("admin", "yes");
			} else {
				// 一般員工
				request.setAttribute("person", "yes");
			}
			
			//判斷當前登入者的身份，系統管理者
			if(this.checkEmsRoleEmp(conn, authbean, SYSTEM)){
				request.setAttribute("system", "yes");
			}
			
			//String FLOW_MANAGER = tools.getSysParam(conn, authbean.getCompId(), "FLOW_MANAGER");	//主管流程角色
			String FLOW_HR = tools.getSysParam(conn, authbean.getCompId(), "FLOW_HR");	//人資流程角色
			String FLOW_SE = tools.getSysParam(conn, authbean.getCompId(), "FLOW_SE");	//秘書長流程角色
			String FLOW_CH = tools.getSysParam(conn, authbean.getCompId(), "FLOW_CH");	//理事長流程角色
			/*
			if(ems_flow.checkEmsFlowRole(conn, authbean, FLOW_MANAGER)){//主管流程角色
				request.setAttribute("flow_manager", "yes");
			}
			*/
			if(ems_flow.checkEmsFlowRole(conn, authbean, FLOW_HR)){
				request.setAttribute("flow_hr", "yes");
			}else if(ems_flow.checkEmsFlowRole(conn, authbean, FLOW_SE)){
				request.setAttribute("flow_se", "yes");
			}else if(ems_flow.checkEmsFlowRole(conn, authbean, FLOW_CH)){
				request.setAttribute("flow_ch", "yes");
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 檢查當前登入員工群組權限，指定SC0031_02
	 * @param conn
	 * @param authbean
	 * @param role_type
	 * @return
	 */
	public boolean checkEmsRoleEmp(Connection conn, AuthorizedBean authbean, String role_type){
		
		boolean flag = false;
		String sql = "";
		
		try{
			sql = "" +
			" SELECT * " +
			" FROM SC0031 " +
			" WHERE 1=1 " +
			" AND SC0031_01 = '"+authbean.getUserId()+"' " +
			" AND SC0031_02 = '"+role_type+"' " +
			" AND SC0031_04 = '"+authbean.getCompId()+"' ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				flag = true;
			}
			
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		return flag;
	}
	
	/**
	 * 檢查當前登入員工是否主管職務
	 * @param conn
	 * @param authbean
	 * @return
	 */
	public boolean checkManager(Connection conn, AuthorizedBean authbean){
		
		boolean ManagerFlag = false;
		String sql = "";
		
		try{
			sql = "" +
			" SELECT EHF010100T6_01, EHF010100T6_07 " +
			" FROM EHF010100T6 " +
			" WHERE 1=1 " +
			" AND EHF010100T6_01 = '"+authbean.getEmployeeID()+"' " +
			" AND HR_CompanySysNo = '"+authbean.getCompId()+"' ";
			
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				if("1".equals(rs.getString("EHF010100T6_07"))){	//是否為主管
					ManagerFlag = true;
				}
			}
			
			rs.close();
			stmt.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return ManagerFlag;
	}

}
