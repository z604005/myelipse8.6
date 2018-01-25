<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF330500M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="/templates/begin.jsp"></jsp:include>

<script>

function chkApplyDeptID(){
	if(document.getElementById("DATA11").value == "" || document.getElementById("DATA11").value == null ){
		alert("請先選擇部門!!");
		document.getElementById("DATA12").value == "";
		return false;
	}else{
		return true;
	}
}

function cls1(){
	document.getElementById("DATA11").value = "";
	document.getElementById("DATA14").value = "";
	document.getElementById("DATA20").value = "";
	
	document.getElementById("DATA12").value = "";
	document.getElementById("DATA15").value = "";
	document.getElementById("DATA21").value = "";

	return true;
}

function cls2(){
	document.getElementById("DATA12").value = "";
	document.getElementById("DATA15").value = "";
	document.getElementById("DATA21").value = "";

	return true;
}


</script>

<layout:form action="EHF330500M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="今日餐點">

	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF330500M0"   reqCode="print" confirm="您確定要列印資料嗎?"  >
		</layout:image>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="日期" size="10"  name="Form1Datas" property="EHF320100T0_02" styleClass="DATAS"/>
			
	</layout:grid>
	
</layout:form>
<%--以下方法，在列印時，可以出現遮罩，目前未調整遮罩的CSS，因此先不用   Alvin--%>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />



<%--<jsp:include page="/templates/end.jsp"></jsp:include>--%>

