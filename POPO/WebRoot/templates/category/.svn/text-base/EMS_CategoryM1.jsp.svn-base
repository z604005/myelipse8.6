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

<!-- JavaScript -->
<script type="text/javascript">

</script>

<layout:form action="EMS_CategoryM1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="類別代碼管理作業">
	
	<layout:row>
		<layout:image alt="確認新增" mode="D,H,H" name="btnimage?text=button.add.ok&type=t" reqCode="addDataForm" property="EMS_CategoryM1" policy="all"></layout:image>
		<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EMS_CategoryM1" policy="all"></layout:image>
<%--		<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=刪除&type=t" reqCode="delForm" property="EMS_CategoryM1" policy="all"></layout:image>--%>
		<layout:image alt="回前一頁" mode="D,D,D" name="btnimage?text=button.Back&type=t" reqCode="redirect" property="EMS_CategoryM1" policy="all"></layout:image>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="類別代碼*" property="DATA01" mode="E,I,I" name="Form1Datas" styleClass="DATAS" 
					 size="6" maxlength="10" isRequired="true"/>
		<layout:text key="類別名稱*" property="DATA02" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="12" maxlength="20" isRequired="true"/>
	
		<layout:text key="類別說明" property="DATA03" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="18" maxlength="30" />
		<layout:number key="顯示順序*" property="DATA04" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					   size="3" maxlength="4" isRequired="true"/>	
		<layout:text key="父階層類別*" property="DATA08" mode="I,I,I" name="Form1Datas" styleClass="DATAS" >
			<layout:select styleClass="DATAS" key="" name="Form1Datas" property="DATA06"  mode="E,E,E" layout="false">
				<layout:options collection="listDATA06" property="item_id" labelProperty="item_value" sourceOf="DATA07" />
			</layout:select>
			<layout:select styleClass="DATAS" key="" name="Form1Datas" property="DATA07"  mode="E,E,E" layout="false">
				<layout:optionsDependent collection="dependslist" property="item_id" labelProperty="item_value" dependsFrom="DATA06" />
			</layout:select>
		</layout:text>
		<layout:radios cols="2" styleClass="DATAS" tooltip="啟用" key="啟用*" property="DATA05" mode="E,E,I" name="Form1Datas" isRequired="true">
			<layout:options collection="Enable_list" property="item_id" labelProperty="item_value" />
		</layout:radios>
<%--		<layout:cell styleClass="DATAS" />--%>
		
		
	</layout:grid>
	
	<layout:notMode value="create" >
		
		<layout:grid cols="3" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text key="代 碼*" property="DATA10" mode="H,E,I" name="Form1Datas" styleClass="DATAS11" 
						 size="6" maxlength="10" isRequired="true"/>
			<layout:text key="代碼名稱*" property="DATA11" mode="H,E,I" name="Form1Datas" styleClass="DATAS11" 
						 size="12" maxlength="20" isRequired="true"/>
			<layout:cell styleClass="DATAS11" />
			<layout:text key="代 碼說明" property="DATA12" mode="H,E,I" name="Form1Datas" styleClass="DATAS11" 
						 size="18" maxlength="30" />
			<layout:number key="顯示順序*" property="DATA13" mode="H,E,I" name="Form1Datas" styleClass="DATAS11" 
						   size="3" maxlength="4" isRequired="true"/>
			<layout:cell styleClass="DATAS11" >
				<layout:submit mode="N,D,N" value="新增代碼明細" reqCode="addDetailDataForm" />
			</layout:cell>
		</layout:grid>
		
		<layout:collection emptyKey="沒有資料列" name="Form2Datas" selectId="" selectProperty="" selectName=""  
						   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="DATA02" style="TEXT-ALIGN: CENTER" title="代碼" />
			<layout:collectionItem property="DATA03" style="TEXT-ALIGN: CENTER" title="代碼名稱" />
			<layout:collectionItem property="DATA04" style="TEXT-ALIGN: CENTER" title="顯示順序" />
			<layout:collectionItem property="DATA05" style="TEXT-ALIGN: CENTER" title="代碼說明" />
			<layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除" 
								   url="EMS_CategoryM1.do?reqCode=delDetailForm" 
								   paramId="DATA01,DATA10" 
								   paramProperty="DATA01,DATA02" 
								   onclick=" return confirmShowEMSWait('您確定要刪除資料嗎?'); " >
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