<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.utils.struts.form.EMS_VIEWDATAF" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

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
function cls2(){
	
	document.getElementById("DATA12").value = "";
	document.getElementById("DATA15").value = "";
	document.getElementById("DATA21").value = "";

	return true;
}
</script>

<layout:form action="EHF020508M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="考勤異常記錄表">

	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF020508M0" reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
	
		<%--	人資用的按鈕			--%>
		<logic:equal name="person_manager" value="yes">
	
		<layout:text styleClass="DATAS" tooltip="部門內碼" key="部門內碼" property="DATA11" styleId="DATA11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="DATA14" styleId="DATA14" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return cls1();">
			<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA11,DATA20,DATA14" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND HR_CompanySysNo = '${compid}' "
							mode="E,E,F"							
							beforerun="cls2()"
							/>
							
			<layout:text layout="false" property="DATA20" styleId="DATA20" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA12" styleId="DATA12" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA15" styleId="DATA15" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
			<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA12,DATA15,DATA21" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EMS_VIEWDATAF.DATA11.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "							
							beforerun="chkApplyDeptID()"																	
						 	/>	 	
						 	
			<layout:text layout="false" property="DATA21" styleId="DATA21" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		</logic:equal>
		
		<%--	會計用的按鈕			--%>
		<logic:equal name="accounting" value="yes">
	
		<layout:text styleClass="DATAS" tooltip="部門內碼" key="部門內碼" property="DATA11" styleId="DATA11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="DATA14" styleId="DATA14"  size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="DATA20" styleId="DATA20" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA12" styleId="DATA12" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA15" styleId="DATA15" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="DATA21" styleId="DATA21" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		</logic:equal>
		
		<%--	老闆用的按鈕			--%>
		<logic:equal name="boss_manager" value="yes">
	
		<layout:text styleClass="DATAS" tooltip="部門內碼" key="部門內碼" property="DATA11" styleId="DATA11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="DATA14" styleId="DATA14" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return cls1();">
			<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA11,DATA20,DATA14" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND HR_CompanySysNo = '${compid}' "
							mode="E,E,F"							
							beforerun="cls2()"
							/>
							
			<layout:text layout="false" property="DATA20" styleId="DATA20" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA12" styleId="DATA12" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA15" styleId="DATA15" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
			<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA12,DATA15,DATA21" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EMS_VIEWDATAF.DATA11.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "							
							beforerun="chkApplyDeptID()"																	
						 	/>	 	
						 	
			<layout:text layout="false" property="DATA21" styleId="DATA21" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		</logic:equal>
		
		<%--	一般員工用的按鈕			--%>
		<logic:equal name="person" value="yes">
		
		<layout:text styleClass="DATAS" tooltip="部門內碼" key="部門內碼" property="DATA11" styleId="DATA11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="DATA14" styleId="DATA14"  size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="DATA20" styleId="DATA20" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA12" styleId="DATA12" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA15" styleId="DATA15" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="DATA21" styleId="DATA21" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		</logic:equal>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2009" endYear="2025"  key="日期區間" name="Form1Datas" property="DATA30" size="10" styleClass="DATAS" >
			&nbsp;~&nbsp;
			<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2009" endYear="2025"  key="日期區間" name="Form1Datas" property="DATA31" size="10" styleClass="DATAS" layout="false" />
		</layout:date>
		
		<layout:select key="狀態" name="Form1Datas" property="DATA05" styleId="DATA05" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listDATA05" property="item_id" labelProperty="item_value" />
		</layout:select>
	
	</layout:grid>

</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" /> 