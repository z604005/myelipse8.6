<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<tiles:insert template="/templates/template.jsp">
<tiles:put name="begin" content="begin.jsp" />
<tiles:put name="title" content="title.jsp" />
<tiles:put name="top" content="top.jsp" />
<tiles:put name="left" content="/MENULIST.do" />
<tiles:put name="content" content="ems/ehf03/EHF030600M1.jsp" />
</tiles:insert>