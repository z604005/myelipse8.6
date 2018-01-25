<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html> 
<html>
<!-- MOBILE HTMLE HEADER -->
<head>
	<meta charset="utf-8">
	<title>SUP Mobile</title>
	<meta name="viewport" content="width=device-width, minimum-scale=1, maximum-scale=1" />
	<%--<meta http-equiv="Pragma" content="no-cache" />--%>
	<%--<meta http-equiv="Expires" content="-1" />--%>
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<link rel="apple-touch-icon" 
		  href="<%= request.getContextPath() %>/config/ems_javascript/images/SUP_Icon_72.png" />

		<%--	設定CSS Resource ediy by joe 2012/09/11	--%>
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.css" />
<%--		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery.mobile-1.1.1.min.css" />--%>
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery.mobile-1.2.0-rc.2.min.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery.mobile.structure-1.2.0-rc.2.min.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery.mobile.theme-1.2.0-rc.2.min.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/ems_mobile.css" />
		
		<!-- JavaScript -->
		<%--	設定jQuery Resource ediy by joe 2012/09/11	--%>
		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/ems_javascript.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery-1.8.1.min.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.min.js"></script>
		<script >
			//設定 jQueryMobule 初始化參數
			 $(document).bind("mobileinit", function(){      
            	$.mobile.loadingMessage = '讀取中...';
            	$.mobile.page.prototype.options.backBtnText = '返回';
            	//$.mobile.ajaxEnabled = false;  //關閉Ajax
     		});
		</script>
<%--		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.mobile-1.1.1.min.js"></script>--%>
		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.mobile-1.2.0-rc.2.min.js"></script>
		
</head>

<body id="mobile_body_main" >
	<input type="hidden" id="web_current_contextpath" value="<%= request.getContextPath() %>" />
	
