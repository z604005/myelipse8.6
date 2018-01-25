package com.spon.utils.util;



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.spon.ems.NewDispatchAction;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;

import vtgroup.ems.common.vo.AuthorizedBean;


/**
 * EMS 取得EMS-Flow相關資訊元件
 * 
 * @version 1.0
 * @created 18-十月-2010 下午 06:00:47
 */
public class EMS_GetEmsFlowInf extends NewDispatchAction{
	

	static Logger loger = Logger.getLogger(EMS_GetEmsFlowInf.class.getName());
	
	private static EMS_GetEmsFlowInf get_emsflowinf_tools; 
	
	
	public static EMS_GetEmsFlowInf getInstance() {
        if (get_emsflowinf_tools == null)
        	get_emsflowinf_tools = new EMS_GetEmsFlowInf();
         return get_emsflowinf_tools;
       }

	public EMS_GetEmsFlowInf(){

	}

	public void finalize() throws Throwable {

	}
	
	private ApplicationContext emscontext = null;
	
	private AuthorizedBean authbean = null ;
	
	/**
	 * 取得 EMS-Flow 的 Emscontext  return ApplicationContext
	 */
	/*public ApplicationContext getEmscontext(){
		
		try{
			this.emscontext = super.getEmscontext();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.emscontext;
	}*/
	
	/**
	 * 取得當前登入者的 EMS-Flow authbean  return AuthorizedBean
	 * @param request
	 * @return
	 */
	public AuthorizedBean getAuthbean(HttpServletRequest request){
		
		try{
			this.authbean = super.getLoginUser(request);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return this.authbean;
	}
	
		
}
