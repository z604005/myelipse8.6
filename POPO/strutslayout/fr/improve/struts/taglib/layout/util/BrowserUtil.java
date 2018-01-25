package fr.improve.struts.taglib.layout.util;

import javax.servlet.http.HttpServletRequest;

public class BrowserUtil {
	public static boolean isIe(HttpServletRequest theRequest) {
		String userAgent = getUserAgent(theRequest);
		return userAgent!=null && userAgent.indexOf("MSIE")!=-1;
	}
	
	public static boolean isGecko(HttpServletRequest theRequest) {
		String userAgent = getUserAgent(theRequest);
		return userAgent!=null && userAgent.indexOf("Gecko")!=-1;
	}
	
	private static String getUserAgent(HttpServletRequest theRequest) {
		String userAgent = theRequest.getHeader("User-Agent");
		return userAgent;
	}
}
