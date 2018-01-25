<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF330400M0F" %>
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

<layout:form action="EHF330400M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="送餐確認表">

	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF330400M0" reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="請注意：每條路線最多只能列印35位孕婦" />
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
	
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" startYear="2015" endYear="2030" key="*日期" name="Form1Datas" property="EHF310100T1_02" size="10" styleClass="DATAS" />
						
		<layout:text styleClass="DATAS" key="路線代碼" property="EHF310100T1_04" styleId="EHF310100T1_04" name="Form1Datas" mode="H,H,H" />
		<layout:text styleClass="DATAS" key="*路線名稱" property="EHF310100T1_04_TXT" styleId="EHF310100T1_04_TXT" size="10" maxlength="10" name="Form1Datas" mode="R,R,I" >
			<sp:lov form="EHF330400M0F" 
					id="EHF310100T1_04,EHF310100T1_04_TXT" 
					lovField="EHF300100T0_01,EHF300100T0_02" 
					table="EHF300100T0"
					fieldAlias="路線代碼,路線名稱" 
					fieldName="EHF300100T0_01,EHF300100T0_02"
					others=" AND EHF300100T0_06 = '${compid}' AND EHF300100T0_04 = '1' "
					mode="E,E,F"/>
		</layout:text>
					
		<layout:text styleClass="DATAS" tooltip="外送人員" key="外送人員" property="EHF010100T0_01" styleId="EHF010100T0_01" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="*外送人員" key="*外送人員" property="EHF010100T0_02" styleId="EHF010100T0_02" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF330400M0F" 
				id="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
				others=" AND EHF010100T6.EHF010100T6_02 IN ('DEP004COM42760782') AND EHF010100T6.HR_CompanySysNo = '${compid}' "
				mode="E,E,F"																
			/>
			<layout:text layout="false" property="EHF010100T0_05" styleId="EHF010100T0_05" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>	
		
		<layout:space styleClass="DATAS"></layout:space>		
		
	</layout:grid>

</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" /> 