<head>
		<link rel="stylesheet" href="/SP-POS/config/default.css" type="text/css">
		<script>var imgsrc="/SP-POS/config/"; var scriptsrc="/SP-POS/config/"; var langue="zh"; var contextPath="/SP-POS";</script>
		<script src="/SP-POS/config/javascript.js"></script>
<%--		<title><layout:message key="system.name"/></title>--%>
		<meta http-equiv="Content-Language" content="tw" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<script language="JavaScript" src="/SP-POS/config/swap.js"></script>
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
<layout:form action="SC007.do" reqCode="" width="95%" styleClass="TITLE" key="訊息列表" >
	<layout:pager sessionPagerId="Pageid" maxPageItems="5" linksLocation="both" width="100%">
		<layout:collection name="Form1Datas" emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" emptyLines="true" id="Form1Datas" width="800" height="200" styleClass="COLLECTION" styleClass2="COLLECTION_2" indexId="index" >
			<layout:collectionItem width="200" title="訊息標題"  property="SC0070_02" sortable="true" />
			<layout:collectionItem width="600" title="內容" >
				<layout:textarea layout="false" styleClass="DATAS6" tooltip="" key="" property="SC0070_03" size="70" mode="R,R,R" maxlength="200" rows="5" name="Form1Datas"  />
			</layout:collectionItem>
			<layout:collectionDetail  property="SC0070_08"/>		
		</layout:collection>
	</layout:pager>
	<layout:column>	
	<layout:panel  key="備註" styleClass="TITLE" >		
		<input size="100%" type="text" name="SC0070_08" value="" readonly="readonly" class="DETAIL">
	</layout:panel>
</layout:column>
	<layout:message styleClass="FORM" key="${PagerMessage}" />
</layout:form>

<%--</tiles:insert>--%>