<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF320300M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<!-- JavaScript -->
<script type="text/javascript">

</script>

<layout:form action="EHF330200M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="餐點分類表">

	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF330200M0"   reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>
		<logic:notEqual name="DisplayFileName" value="" >
			<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" ></layout:image>
		</logic:notEqual>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
	
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2010" endYear="2035"  key="*日期區間" name="Form1Datas" property="EHF320300T0_02" styleClass="DATAS" />
	
		<layout:select key="*餐別" name="Form1Datas" property="EHF320300T1_03" styleId="EHF320300T1_03" styleClass="DATAS" mode="E,E,I" maxlength="3">
			<layout:options collection="listEHF320300T1_03" property="item_id" labelProperty="item_value" />
		</layout:select>
									
	</layout:grid>
</layout:form>

<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />