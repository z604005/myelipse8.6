<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF330800M0F" %>
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

<layout:form action="EHF330800M0.do" reqCode="" width="100%" styleClass="TITLE" enctype="multipart/form-data" method="post" key="醫院用餐資訊">
	
	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF330800M0"   reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="請注意：需要在客戶需求單有填寫醫院用餐期間才會列印出資料" />
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		<layout:number key="醫院代碼" size="" maxlength="" name="Form1Datas" property="EHF310100T0_11"  styleId="EHF310100T0_11" styleClass="DATAS" mode="H,H,H" tooltip="醫院代碼"/>
		<layout:text styleClass="DATAS" key="醫院名稱" property="EHF310100T0_11_TXT"  styleId="EHF310100T0_11_TXT" size="20" mode="R,R,I" maxlength="20" tooltip="醫院名稱" name="Form1Datas">
			<sp:lov 	form="EHF330800M0F" 
						id="EHF310100T0_11,EHF310100T0_11_TXT" 
						lovField="EHF300200T0_01,EHF300200T0_02" 
						table="EHF300200T0"
						fieldAlias="醫院代碼,醫院名稱" 
						fieldName="EHF300200T0_01,EHF300200T0_02"											
						mode="E,E,F"									
						others=" AND EHF300200T0_06 = '${compid}' AND EHF300200T0_05 = '1' "
			/>					 
		</layout:text>
		<layout:cell styleClass="DATAS" />
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="12" startYear="1960" endYear="2030"  
													 key="生產日期(起)" name="Form1Datas" property="EHF310100T0_07_B" styleClass="DATAS"/>									 
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="12" startYear="1960" endYear="2030"  
													 key="生產日期(迄)" name="Form1Datas" property="EHF310100T0_07_E" styleClass="DATAS"/>
	</layout:grid>
	
</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />