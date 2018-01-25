<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

<layout:form action="FLOW_TEST_ACTION.do" reqCode="" width="100%" styleClass="TITLE" 
			 method="post" enctype="multipart/form-data" acceptCharset="UTF-8" key="SPON FLOW 測試工具" >
	
	<layout:row>
		<layout:image alt="呈核" mode="D,D,D" name="btnimage?text=呈核&type=t" disabledName="btnimage?text=呈核&type=f"
					  reqCode="submit" property="FLOW_TEST_ACTION" policy="all" ></layout:image>
		<layout:image alt="核准" mode="D,D,D" name="btnimage?text=核准&type=t" disabledName="btnimage?text=核准&type=f"
					  reqCode="approve" property="FLOW_TEST_ACTION" policy="all" ></layout:image>
		<layout:image alt="駁回" mode="D,D,D" name="btnimage?text=駁回&type=t" disabledName="btnimage?text=駁回&type=f"
					  reqCode="reject" property="FLOW_TEST_ACTION" policy="all" ></layout:image>
		<layout:image alt="簽核歷程" mode="D,D,D" name="btnimage?text=簽核歷程&type=t" disabledName="btnimage?text=簽核歷程&type=f"
					  reqCode="getFlowLogList" property="FLOW_TEST_ACTION" policy="all" ></layout:image>
		<layout:image alt="待簽核表單" mode="D,D,D" name="btnimage?text=待簽核表單&type=t" disabledName="btnimage?text=待簽核表單&type=f"
					  reqCode="getUserCurrentSignList" property="FLOW_TEST_ACTION" policy="all" ></layout:image>
		<layout:image alt="表單狀態" mode="D,D,D" name="btnimage?text=表單狀態&type=t" disabledName="btnimage?text=表單狀態&type=f"
					  reqCode="getCurrentFlowFormStatus" property="FLOW_TEST_ACTION" policy="all" ></layout:image>
    </layout:row>
	
<%--	<layout:row >--%>
<%--   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />--%>
<%--	</layout:row>--%>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="780px">	
				
		<layout:text styleClass="DATAS" tooltip="表單單號" key="表單單號" property="DATA01" styleId="DATA01" size="30" 
				 	 maxlength="30" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="下一站簽核人員ID" key="下一站簽核人員ID" property="DATA02" styleId="DATA02" size="30" 
				 	 maxlength="20" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="簽核意見" key="簽核意見" property="DATA03" styleId="DATA03" size="50" 
				 	 maxlength="75" mode="E,E,I" name="Form1Datas" />
				 	 
	</layout:grid>
	
	<logic:equal name="show_log_list" value="true">
	<%--  簽核歷程	--%>
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas"
					   id="bean1" indexId="index" 
					   selectId="" selectProperty="" selectName=""  
					   width="780px" height="280px" emptyLines="true"
					   styleClass="COLLECTION" styleClass2="COLLECTION_2" >
				<layout:collectionItem property="SIGN_USER_ID" style="TEXT-ALIGN: CENTER" title="簽核者" />
				<layout:collectionItem property="SIGN_DATETIME" style="TEXT-ALIGN: CENTER" title="簽核時間" />
				<layout:collectionItem property="SIGN_ACTION_NAME" style="TEXT-ALIGN: CENTER" title="簽核狀態" />
				<layout:collectionItem property="SIGN_COMMENT" style="TEXT-ALIGN: LEFT" title="簽核意見" />
	</layout:collection>
	</logic:equal>
	
	<logic:equal name="show_user_current_sign_list" value="true">
	<%--  人員待簽核表單	--%>
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas"
					   id="bean1" indexId="index" 
					   selectId="" selectProperty="" selectName=""  
					   width="780px" height="280px" emptyLines="true"
					   styleClass="COLLECTION" styleClass2="COLLECTION_2" >
				<layout:collectionItem property="FLOW_NO" style="TEXT-ALIGN: CENTER" title="FLOW_NO" />
				<layout:collectionItem property="FORM_NO" style="TEXT-ALIGN: CENTER" title="FORM_NO" />
				<layout:collectionItem property="FLOW_LOG_SN" style="TEXT-ALIGN: CENTER" title="FLOW_LOG_SN" />
				<layout:collectionItem property="FLOW_SEND_TO_SIGN_DATE" style="TEXT-ALIGN: CENTER" title="FLOW_SEND_TO_SIGN_DATE" />
				<layout:collectionItem property="SIGN_USER_ID" style="TEXT-ALIGN: CENTER" title="SIGN_USER_ID" />
				<layout:collectionItem property="FLOW_SITE_NAME" style="TEXT-ALIGN: CENTER" title="FLOW_SITE_NAME" />
				<layout:collectionItem property="AFTER_SIGN_FORM_STATUS_NO" style="TEXT-ALIGN: CENTER" title="AFTER_SIGN_FORM_STATUS_NO" />
				<layout:collectionItem property="AFTER_SIGN_FORM_STATUS_NAME" style="TEXT-ALIGN: CENTER" title="AFTER_SIGN_FORM_STATUS_NAME" />
				<layout:collectionItem property="FLOW_SITE_SIGN_USER_TYPE" style="TEXT-ALIGN: CENTER" title="FLOW_SITE_SIGN_USER_TYPE" />
				<layout:collectionItem property="FLOW_SITE_SIGN_USER_KEY_VALUE" style="TEXT-ALIGN: CENTER" title="FLOW_SITE_SIGN_USER_KEY_VALUE" />
	</layout:collection>
	</logic:equal>
	
	<logic:equal name="show_current_form_status_list" value="true">
	<%--  表單當前狀態資訊	--%>
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas"
					   id="bean1" indexId="index" 
					   selectId="" selectProperty="" selectName=""  
					   width="780px" height="280px" emptyLines="true"
					   styleClass="COLLECTION" styleClass2="COLLECTION_2" >
				<layout:collectionItem property="FLOW_NO" style="TEXT-ALIGN: CENTER" title="FLOW_NO" />
				<layout:collectionItem property="FORM_NO" style="TEXT-ALIGN: CENTER" title="FORM_NO" />
				<layout:collectionItem property="FLOW_LOG_SN" style="TEXT-ALIGN: CENTER" title="FLOW_LOG_SN" />
				<layout:collectionItem property="FLOW_SITE_NAME" style="TEXT-ALIGN: CENTER" title="FLOW_SITE_NAME" />
				<layout:collectionItem property="HAVE_CURRENT_SIGN_USER_FLAG" style="TEXT-ALIGN: CENTER" title="HAVE_CURRENT_SIGN_USER_FLAG" />
				<layout:collectionItem property="CURRENT_SIGN_FORM_STATUS_NO" style="TEXT-ALIGN: CENTER" title="CURRENT_SIGN_FORM_STATUS_NO" />
				<layout:collectionItem property="CURRENT_SIGN_FORM_STATUS_NAME" style="TEXT-ALIGN: CENTER" title="CURRENT_SIGN_FORM_STATUS_NAME" />
				<layout:collectionItem property="FLOW_SITE_SIGN_USER_KEY_VALUE" style="TEXT-ALIGN: CENTER" title="FLOW_SITE_SIGN_USER_KEY_VALUE" />
	</layout:collection>
	</logic:equal>
	
	
</layout:form>
