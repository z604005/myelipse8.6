<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF300400M0F" %>
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

<layout:form action="EHF300400M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="茶飲資料設定">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF300400M1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF300400M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF300400M1" ></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF300400M1" ></layout:image>
    </layout:row>

	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
	<layout:text styleClass="DATAS" key="系統編號" property="EHF300400T0_01" styleId="EHF300400T0_01" size="10" mode="H,H,H" maxlength="10" name="Form1Datas" />
		<layout:select styleClass="DATAS" key="*星期" property="EHF300400T0_02" styleId="EHF300400T0_02" mode="E,I,I" name="Form1Datas" isRequired="true">
				<layout:options collection="listEHF300400T0_02" property="item_id" labelProperty="item_value" />
			</layout:select>
		<layout:radios styleClass="DATAS" key="*啟用" property="EHF300400T0_05" mode="E,E,I" name="Form1Datas" cols="2" >
			<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
		</layout:radios>
<%--		<layout:text styleClass="DATAS" key="*茶飲代號" property="EHF300400T0_03" styleId="EHF300400T0_03" size="10" mode="E,I,I" maxlength="10" name="Form1Datas" />--%>
<%--		<layout:text styleClass="DATAS" key="*茶飲名稱" property="EHF300400T0_04" styleId="EHF300400T0_04" size="10" mode="E,E,I" maxlength="10" name="Form1Datas" />--%>
		
		<layout:text styleClass="DATAS" key="*茶飲代號" property="EHF300400T0_03" styleId="EHF300400T0_03" size="10" mode="R,I,I" maxlength="10" name="Form1Datas" >
			<sp:lov 	
				form="EHF300400M0F" 
				id="EHF300400T0_03,EHF300400T0_04" 
				lovField="EMS_CategoryT1_04,EMS_CategoryT1_05" 
				table="EMS_CategoryT1"
				fieldAlias="茶飲代號,茶飲名稱" 
				fieldName="EMS_CategoryT1_04,EMS_CategoryT1_05"
				others=" AND EMS_CategoryT1_09 = '${compid}' AND EMS_CategoryT1_01 = 'TEA' "
				orderby=" EMS_CategoryT1_07 "
				mode="E,F,F"
			/>
		</layout:text>
		
		<layout:text styleClass="DATAS" key="*茶飲名稱" property="EHF300400T0_04" styleId="EHF300400T0_04" size="10" mode="R,I,I" maxlength="20" name="Form1Datas" />
	
		<layout:text styleClass="DATAS" key="" property="EHF300400T0_06" styleId="EHF300400T0_06" mode="H,H,H" name="Form1Datas" />
		
	</layout:grid>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
	</layout:grid>

</layout:form>