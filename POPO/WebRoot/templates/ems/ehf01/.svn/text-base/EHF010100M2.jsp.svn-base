<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.hr.forms.EHF010100M0F" %>
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
	if(document.getElementById("EHF000200T0_03").value == "" || document.getElementById("EHF000200T0_03").value == null ){
		alert("請先選擇部門!!");
		document.getElementById("EHF010100T0_01").value == "";
		return false;
	}else{
		return true;
	}
}



function cls2(){
	document.getElementById("EHF000200T0_01").value = "";
	document.getElementById("EHF000200T0_02").value = "";
	document.getElementById("EHF000200T0_03").value = "";

	return true;
}
</script>

<layout:form action="EHF010100M2.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="員工基本資料表">
	
	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF010100M2"   reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>
		<logic:notEqual name="DisplayFileName" value="" >
			<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" ></layout:image>
		</logic:notEqual>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		
		<%--	人資用的按鈕			--%>
		<logic:equal name="person_manager" value="yes">
		
		<layout:text styleClass="DATAS" tooltip="部門" key="部門名稱" property="EHF010100T8_02" styleId="EHF010100T8_02" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門名稱" property="EHF000200T0_03" styleId="EHF000200T0_03" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
			<sp:lov 	form="EHF010100M0F" 
							id="EHF010100T8_02,EHF000200T0_03,EHF000200T0_02" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"		
							others=" AND HR_CompanySysNo = '${compid}' "
							mode="E,E,F"
							/>
							
			<layout:text layout="false" property="EHF000200T0_02" styleId="EHF000200T0_02" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工名稱" property="EHF010100T0_01" styleId="EHF010100T0_01" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工名稱" property="EHF010100T0_02" styleId="EHF010100T0_02" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
			<sp:lov 	form="EHF010100M0F" 
							id="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EHF010100M0F.EHF010100T8_02.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "
							beforerun="chkApplyDeptID()"	
						 	/>	 	
						 	
			<layout:text layout="false" property="EHF010100T0_05" styleId="EHF010100T0_05" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		</logic:equal>
		
		<%--	會計用的按鈕			--%>
		<logic:equal name="accounting" value="yes">
		
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="EHF010100T8_02" styleId="EHF010100T8_02" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="EHF000200T0_03" styleId="EHF000200T0_02"  size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF000200T0_02" styleId="EHF000200T0_02" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF010100T0_01" styleId="EHF010100T0_01" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF010100T0_02" styleId="EHF010100T0_02" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF010100T0_05" styleId="EHF010100T0_05" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		</logic:equal>
		
		<%--	老闆用的按鈕			--%>
		<logic:equal name="boss_manager" value="yes">
		
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="EHF010100T8_02" styleId="EHF010100T8_02" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="EHF000200T0_03" styleId="EHF000200T0_03" size="10" mode="E,E,I" maxlength="20" name="Form1Datas">
			<sp:lov 	form="EHF010100M0F" 
							id="EHF010100T8_02,EHF000200T0_03,EHF000200T0_02" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"
							others=" AND HR_CompanySysNo = '${compid}' "
							mode="E,E,F"
							/>
							
			<layout:text layout="false" property="EHF000200T0_02" styleId="EHF000200T0_02" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF010100T0_01" styleId="EHF010100T0_01" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF010100T0_02" styleId="EHF010100T0_02" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
			<sp:lov 	form="EHF010100M0F" 
							id="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EHF010100M0F.EHF010100T8_02.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "
							mode="E,E,F"
							beforerun="chkApplyDeptID()"	
						 	/>	 	
						 	
			<layout:text layout="false" property="EHF010100T0_05" styleId="EHF010100T0_05" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		</logic:equal>
		
		<%--	一般員工用的按鈕			--%>
		<logic:equal name="person" value="yes">
		
		<layout:text styleClass="DATAS" tooltip="部門" key="部門名稱" property="EHF010100T8_02" styleId="EHF010100T8_02" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門名稱" property="EHF000200T0_03" styleId="EHF000200T0_03"  size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF000200T0_02" styleId="EHF000200T0_02" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF010100T0_01" styleId="EHF010100T0_01" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF010100T0_02" styleId="EHF010100T0_02" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF010100T0_05" styleId="EHF010100T0_05" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		</logic:equal>
					

		
		
		
		<layout:select key="員工類別" name="Form1Datas" property="EHF010100T0_07" styleId="EHF010100T0_07" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listDATA05" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		
		<layout:select key="職務狀況" name="Form1Datas" property="EHF010100T1_02" styleId="EHF010100T1_02" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listDATA06" property="item_id" labelProperty="item_value" />
		</layout:select>
		
	

	</layout:grid>
	
</layout:form>
<%--以下方法，在列印時，可以出現遮罩，目前未調整遮罩的CSS，因此先不用   Alvin--%>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />



<%--<jsp:include page="/templates/end.jsp"></jsp:include>--%>

