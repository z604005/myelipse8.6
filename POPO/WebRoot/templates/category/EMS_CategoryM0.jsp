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
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JavaScript -->
<script type="text/javascript">

</script>

<layout:form action="EMS_CategoryM0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="類別代碼查詢作業">
	<layout:row>
		<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" reqCode="queryForm" property="EMS_CategoryM0" policy="all"></layout:image>
		<layout:image alt="新增類別代碼" mode="D,D,D" name="btnimage?text=button.add.category&type=t" reqCode="addDataForm" property="EMS_CategoryM0" policy="all"></layout:image>
<%--		<layout:image alt="刪除" mode="D,D,D" name="btnimage?text=刪除&type=t" property="delForm" policy="all"></layout:image>--%>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="類別代碼" property="DATA01" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="6" maxlength="10" />
		<layout:text key="類別名稱" property="DATA02" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="12" maxlength="20" />
	
		<layout:radios cols="2" styleClass="DATAS" tooltip="啟用" key="啟用" property="DATA03" mode="E,E,E" name="Form1Datas" value="true" >
			<layout:options collection="Enable_list" property="item_id" labelProperty="item_value" />
		</layout:radios>
		<layout:cell styleClass="DATAS" >
<%--			<layout:select styleClass="DATAS" key="Old Options" name="Form1Datas" property="DATA04" mode="E,E,I" layout="false" >--%>
<%--				<layout:options collection="listDATA04" property="item_id" labelProperty="item_value" />--%>
<%--			</layout:select>--%>
<%--			<sp:emscategorypopup classkey="EMS" target_select="DATA04" value="測試用" />--%>
		</layout:cell>
		
	</layout:grid>
	

	<layout:pager linksLocation="both" maxPageItems="10" sessionPagerId="10">
		<layout:collection emptyKey="沒有資料列" name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="500" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
		<layout:collectionItem style="TEXT-ALIGN: CENTER" property="DATA01" title="類別代碼" 
							   action="EMS_CategoryM1.do?reqCode=queryForm" 
							   paramId="DATA01" paramProperty="DATA01" 
							   onclick="return showEMSWait();" />
		<layout:collectionItem property="DATA02" style="TEXT-ALIGN: CENTER" title="類別名稱" />
		<layout:collectionItem property="DATA03" style="TEXT-ALIGN: CENTER" title="啟用" />
		<layout:collectionItem property="DATA04" style="TEXT-ALIGN: CENTER" title="類別說明" />
		<layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除"
						       action="EMS_CategoryM0.do?reqCode=delForm"
							   paramId="DATA01" paramProperty="DATA01" 
							   onclick=" return confirmShowEMSWait('您確定要刪除資料?'); " >
			刪除				
		</layout:collectionItem>
	</layout:collection>
	</layout:pager>
	
</layout:form>