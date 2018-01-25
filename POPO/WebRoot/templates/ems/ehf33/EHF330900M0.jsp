<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF330900M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

</script>

<layout:form action="EHF330900M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="特殊飲品對照表">

	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF330900M0" reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
	
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" startYear="2015" endYear="2030" key="*日期" name="Form1Datas" property="EHF310100T2_03" size="10" styleClass="DATAS" />
	
	</layout:grid>

</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" /> 