<head>
		<link rel="stylesheet" href="/HONGCHIA-WAREHOUSE/config/default.css" type="text/css">
		<script>var imgsrc="/HONGCHIA-WAREHOUSE/config/"; var scriptsrc="/HONGCHIA-WAREHOUSE/config/"; var langue="zh"; var contextPath="/HONGCHIA-WAREHOUSE";</script>
		<script src="/HONGCHIA-WAREHOUSE/config/javascript.js"></script>
<%--		<title><layout:message key="system.name"/></title>--%>
		<meta http-equiv="Content-Language" content="tw" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<script language="JavaScript" src="/HONGCHIA-WAREHOUSE/config/swap.js"></script>
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
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%
	request.setAttribute("Form1Datas",request.getSession().getAttribute("msg"));
	request.setAttribute("PagerMessage",request.getSession().getAttribute("pamsg"));
%>
<%
	//建立頁次session 
	if(session.getAttribute("Pageid")==null)
		session.setAttribute("Pageid","0");
%>
<layout:form action="SM020400M1.do" reqCode="" width="100%" styleClass="TITLE" key="刀具安全存量不足訊息列表" >
<%--	<layout:pager sessionPagerId="Pageid" maxPageItems="5" linksLocation="both" width="100%">--%>
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%"  maxPageItems="15">
		<layout:collection name="Form1Datas" emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" id="Form1Datas" width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2" indexId="index" >
			<layout:collectionItem property="SM010100T0_01" style="TEXT-ALIGN: LEFT" title="刀具編號" />
			<layout:collectionItem property="SM010100T0_02" style="TEXT-ALIGN: LEFT" title="刀具名稱" />
			<layout:collectionItem property="SM010100T0_03" style="TEXT-ALIGN: LEFT" title="刀具別名" />
			<layout:collectionItem property="SM010400T0_02" style="TEXT-ALIGN: LEFT" title="廠商" />
			<layout:collectionItem property="SM010100T0_SN" style="TEXT-ALIGN: RIGHT" title="新品安全存量" />
			<layout:collectionItem property="SM010100T0_RN" style="TEXT-ALIGN: RIGHT" title="新品現存量" />
			<layout:collectionItem property="SM010100T0_SO" style="TEXT-ALIGN: RIGHT" title="舊品安全存量" />
			<layout:collectionItem property="SM010100T0_RO" style="TEXT-ALIGN: RIGHT" title="舊品現存量" />		
		</layout:collection>
	</layout:pager>
	<layout:message styleClass="FORM" key="${PagerMessage}" />
</layout:form>