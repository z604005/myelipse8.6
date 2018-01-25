package com.spon.layout.utils;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * StrutsLayout AbstractPolicy 內的參數在 StrutsLayout 1.4 已改由 介面 來實作
 * 原本 SysPolicy 繼承  AbstractPolicy 改為 繼承介面 : Policy
 * Modify by joe  2010/08/23
 */
import fr.improve.struts.taglib.layout.policy.Policy;
import fr.improve.struts.taglib.layout.policy.FieldPolicy;

public class SysPolicy implements Policy , FieldPolicy {
	
	public short getAuthorizedDisplayMode(String in_policy, String in_name,
			String in_property, PageContext in_pageContext) {
		
		short mode = MODE_DISABLED;
		try {
			HashMap pgms = new HashMap();
			if (in_pageContext.getSession().getAttribute("pgmsauth") != null){
				pgms = (HashMap)in_pageContext.getSession().getAttribute("pgmsauth");
			}
			int[] auths = (int[])pgms.get(in_property);
			int authType = 0;
			if (in_policy.toUpperCase().equals("ADD")){
				authType = 0;
			}else if (in_policy.toUpperCase().equals("DEL")){
				authType = 1;
			}else if (in_policy.toUpperCase().equals("MODIFY")){
				authType = 2;
			}else if (in_policy.toUpperCase().equals("QUERY")){
				authType = 3;
			}else if (in_policy.toUpperCase().equals("PRINT")){
				authType = 4;
			}else{
				authType = -1;
			}
			
			if (in_policy.toUpperCase().equals("ALL")){
				mode = MODE_EDIT;
			}else{
				//如果大於0就有權限
				if (authType >= 0 && auths[authType] > 0){
					mode = MODE_EDIT;
				}else{
					mode = MODE_DISABLED;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			//System.out.println("SysPolicy.getAuthorizedDisplayMode():" + e);
		}
		return mode;
	}
}
