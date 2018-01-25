<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF300000M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JavaScript -->
<script type="text/javascript">

</script>

<layout:form action="EHF300000M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="食物代碼設定">

	<layout:row>
		<layout:image alt="確認新增" mode="D,H,H" name="btnimage?text=button.add.ok&type=t" reqCode="addDataForm" property="EHF300000M1" policy="all"></layout:image>
		<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF300000M1" policy="all"></layout:image>
<%--		<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=刪除&type=t" reqCode="delForm" property="EHF300000M1" policy="all"></layout:image>--%>
		<layout:image alt="回前一頁" mode="D,D,D" name="btnimage?text=button.Back&type=t" reqCode="redirect" property="EHF300000M1" policy="all"></layout:image>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>

	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="*食物代碼" property="PSFOODT0_01" mode="E,I,I" name="Form1Datas" styleClass="DATAS" 
					 size="6" maxlength="10" isRequired="true"/>
		<layout:text key="*食物名稱" property="PSFOODT0_04" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="12" maxlength="20" isRequired="true"/>
		<layout:text key="食物說明" property="PSFOODT0_05" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="18" maxlength="30" />
		<layout:number key="*顯示順序" property="PSFOODT0_06" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					   size="3" maxlength="4" isRequired="true"/>	

		<layout:text key="父階層食物" property="space" mode="I,I,I" name="Form1Datas" styleClass="DATAS" >
			<layout:select styleClass="DATAS" key="" name="Form1Datas" property="PSFOODT0_02"  mode="E,E,E" layout="false">
				<layout:options collection="listPSFOODT0_02" property="item_id" labelProperty="item_value" sourceOf="PSFOODT0_03" />
			</layout:select>

			<layout:select styleClass="DATAS" key="" name="Form1Datas" property="PSFOODT0_03"  mode="E,E,E" layout="false">
				<layout:optionsDependent collection="dependslist" property="item_id" labelProperty="item_value" dependsFrom="PSFOODT0_02" />
			</layout:select>
		</layout:text>

		<layout:radios cols="2" styleClass="DATAS" tooltip="啟用" key="*啟用" property="PSFOODT0_07" mode="E,E,I" name="Form1Datas" isRequired="true">
			<layout:options collection="Enable_list" property="item_id" labelProperty="item_value" />
		</layout:radios>
<%--		<layout:cell styleClass="DATAS" />--%>
		
		
	</layout:grid>

	<layout:notMode value="create" >
		
		<layout:grid cols="3" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text key="*代碼" property="PSFOODT1_04" mode="H,E,I" name="Form1Datas" styleClass="DATAS11" 
						 size="6" maxlength="10" isRequired="true"/>
			<layout:text key="*代碼名稱" property="PSFOODT1_05" mode="H,E,I" name="Form1Datas" styleClass="DATAS11" 
						 size="12" maxlength="20" isRequired="true"/>
			<layout:cell styleClass="DATAS11" />
			<layout:text key="代碼說明" property="PSFOODT1_06" mode="H,E,I" name="Form1Datas" styleClass="DATAS11" 
						 size="18" maxlength="30" />
			<layout:number key="*顯示順序" property="PSFOODT1_07" mode="H,E,I" name="Form1Datas" styleClass="DATAS11" 
						   size="3" maxlength="4" isRequired="true"/>
			<layout:cell styleClass="DATAS11" >
				<layout:submit mode="N,D,N" value="新增代碼明細" reqCode="addDetailDataForm" />
			</layout:cell>
		</layout:grid>
		
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF300000_DETAIL" selectId="EHF300000_DETAIL" selectProperty="" selectName=""  
						   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="PSFOODT1_04" style="TEXT-ALIGN: CENTER" title="代碼" />
			<layout:collectionItem property="PSFOODT1_05" style="TEXT-ALIGN: CENTER" title="代碼名稱" />
			<layout:collectionItem property="PSFOODT1_07" style="TEXT-ALIGN: CENTER" title="顯示順序" />
			<layout:collectionItem property="PSFOODT1_06" style="TEXT-ALIGN: CENTER" title="代碼說明" />
			<layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除" 
								   url="EHF300000M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=FOODT1" 
								   paramId="PSFOODT1_01,PSFOODT1_04" 
								   paramProperty="PSFOODT1_01,PSFOODT1_04" 
								   onclick="return confirmDelData('您確定要刪除加班資料嗎?');" >
				刪除				
			</layout:collectionItem>
		</layout:collection>
		
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</layout:notMode>
</layout:form>