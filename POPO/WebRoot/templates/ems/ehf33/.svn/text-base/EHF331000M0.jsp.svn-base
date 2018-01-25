<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF331000M0F" %>
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

<layout:form action="EHF331000M0.do" reqCode="" width="100%" styleClass="TITLE" enctype="multipart/form-data" method="post" key="當日已收明細表">
	
	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF331100M0"   reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>

	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />

	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >

		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="收費日期" size="10"  name="Form1Datas" property="EHF310400T1_10" styleClass="DATAS" />
		<layout:text styleClass="DATAS" key="收費者" property="EHF310400T1_05" styleId="EHF310400T1_05" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	form="EHF331000M0F" 
						id="EHF010100T0_01,EHF010100T0_02,EHF310400T1_05" 
						lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
						table="ehf010100t0"
						fieldAlias="系統代號,員工工號,姓名" 
						fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"									
						others=" AND HR_CompanySysNo = '${compid}' " />
			<layout:text layout="false" property="EHF010100T0_02" styleId="EHF010100T0_02" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text layout="false" property="EHF010100T0_01" styleId="EHF010100T0_01" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
		</layout:text>
	</layout:grid>
	
	
</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />