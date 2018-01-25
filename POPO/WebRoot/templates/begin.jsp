<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<html>
	<!-- H E A D E R -->
	<head>
		<layout:skin includeScript="true" />
		<title><layout:message key="system.name"/></title>
		<meta http-equiv="Content-Language" content="tw" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<script language="JavaScript" src="<layout:resource type="cfg" name="swap.js"/>"></script>
		
		<%--	設定CSS Resource ediy by joe 2012/09/11	--%>
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.css" />
<%--		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery-ui-1.8.23.custom.css" />與日曆衝突--%>
		
		<%--	設定jQuery Resource ediy by joe 2012/09/11	--%>
<%--		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/ems_javascript.js"></script>需先載入jquery--%>
<%--		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery-1.8.1.min.js"></script>與日曆衝突--%>
<%--		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.min.js"></script>需先載入jquery--%>
<%--		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery-ui-1.8.23.custom.min.js"></script>與日曆衝突--%>
<%--		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.ui.datepicker-zh-TW.js"></script>需先載入jquery--%>
		
		<!-- float menu's javascript start-->
<%--		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js" type="text/javascript"></script>--%>
		<script language="JavaScript" src="<layout:resource type="cfg" name="jquery1.4.min.js"/>"></script>
		<script language="JavaScript" src="<layout:resource type="cfg" name="jquery.easing.1.3.js"/>"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.min.js"></script>
		<!-- float menu's javascript end-->
		
		<!-- 電子簽名畫布 by troy 2015/05/11-->
		<script language="JavaScript" type="text/javascript" src="<%= request.getContextPath() %>/config/jSignature.js"></script>
		
		<!-- top's javascript start-->
		<script language="JavaScript" src="<layout:resource type="cfg" name="jquery1.7.min.js"/>"></script>
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/style.css" />
		<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery-ui-1.7.2.custom.css" />
		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery-ui-1.7.2.custom.min.js"></script>
		<!-- top's javascript end-->
		
		<%--	設定jQuery Resource ediy by joe 2012/09/11	--%>
<%--		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.min.js"></script>--%>
		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/ems_javascript.js"></script>
		<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.ui.datepicker-zh-TW.js"></script>
		
		<script language="JavaScript">
	<!--//
    function save1Cookie(days,skinName) {
          if (days) {
                var date = new Date();
                date.setTime(date.getTime()+(days*24*60*60*1000));
                var expires = "; expires="+date.toGMTString();
          }
          else expires = "";
          document.cookie = "skinName="+skinName+expires+"; path=/";
    }

    function choix(choix) {
        save1Cookie(60,choix);
    }
    //-->
   </script>
		<meta http-equiv="Pragma" content="no-cache">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Expires" content="0">

	</head>