<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.flow.forms.SFlow_SiteF" %>
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

<layout:form action="SFlow_SiteM1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="簽核流程設定">

	<layout:row>
	
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,H" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="SFlow_SiteM1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="SFlow_SiteM1" ></layout:image>
		</logic:notEqual>
		
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t" reqCode="redirect" property="SFlow_SiteM1" ></layout:image>
	
	</layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	
		<layout:text key="FLOW編號" property="SFLOW_SITE_HD_01" styleId="SFLOW_SITE_HD_01" mode="E,I,I" name="Form1Datas" styleClass="DATAS" size="50" maxlength="100" />
		<layout:text key="FLOW名稱" property="SFLOW_SITE_HD_02" styleId="SFLOW_SITE_HD_02" mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="50" maxlength="50" />
	
	</layout:grid>
	
	<layout:notMode value="create">
	
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		
			<layout:number key="站別順序號碼" property="SFLOW_SITE_T0_02" styleId="SFLOW_SITE_T0_02" mode="E,E,I" 
						   name="Form1Datas" styleClass="DATAS" size="12" maxlength="10" />
			<layout:text key="站別名稱" property="SFLOW_SITE_T0_03" styleId="SFLOW_SITE_T0_03" mode="E,E,I" 
						 name="Form1Datas" styleClass="DATAS" size="12" maxlength="40" />
			<layout:text key="FLOW狀態號碼" property="SFLOW_SITE_T0_04" styleId="SFLOW_SITE_T0_04" mode="E,E,I" 
						 name="Form1Datas" styleClass="DATAS" size="12" maxlength="4" />
			<layout:text key="FLOW狀態名稱" property="SFLOW_SITE_T0_05" styleId="SFLOW_SITE_T0_05" mode="E,E,I" 
						 name="Form1Datas" styleClass="DATAS" size="12" maxlength="30" />
			<layout:select key="處理人員類型" name="Form1Datas" property="SFLOW_SITE_T0_07" styleId="SFLOW_SITE_T0_07" styleClass="DATAS" mode="E,E,I" >
				<layout:options collection="SFLOW_SITE_T0_07_list" property="item_id" labelProperty="item_value" />
			</layout:select>
			<layout:text key="處理人員的指定Key值" property="SFLOW_SITE_T0_08" styleId="SFLOW_SITE_T0_08" mode="E,E,I" 
						 name="Form1Datas" styleClass="DATAS" size="12" maxlength="40" />
			<layout:space styleClass="DATAS" />
			<layout:cell styleClass="DATAS" >				
				<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="SFlow_SiteM1" reqCode="addDetailDataForm" ></layout:image>			
			</layout:cell>
		
		</layout:grid>
		
		<layout:grid cols="1" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:cell styleClass="DATAS" >
			指定人員：請直接填寫指定的人員登入帳號。<br/>
			指定欄位：AGENT_USER_ID 表示代理人。<br/>
			指定組織：LV_ONE_DEPARTMENT_HEADS 表示1級主管；LV_TWO_DEPARTMENT_HEADS 表示2級主管。
			</layout:cell>
		</layout:grid>
		
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="SFLOW_SITE_T0_DETAIL" styleId="SFLOW_SITE_T0_DETAIL" id="bean1" indexId="index" 
						   selectId="" selectProperty="" selectName=""  
						   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="SFLOW_SITE_T0_02" style="TEXT-ALIGN: CENTER" title="站別順序號碼" />
			<layout:collectionItem property="SFLOW_SITE_T0_03" style="TEXT-ALIGN: CENTER" title="站別名稱" />
			<layout:collectionItem property="SFLOW_SITE_T0_04" style="TEXT-ALIGN: CENTER" title="FLOW狀態號碼" />
			<layout:collectionItem property="SFLOW_SITE_T0_05" style="TEXT-ALIGN: CENTER" title="FLOW狀態名稱" />
			<layout:collectionItem property="SFLOW_SITE_T0_07_TXT" style="TEXT-ALIGN: CENTER" title="處理人員類型" />
			<layout:collectionItem property="SFLOW_SITE_T0_08" style="TEXT-ALIGN: CENTER" title="處理人員的指定Key值" />
<%--			<logic:equal name="DetailData" value="yse">--%>
				<layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除" 
								   	   url="SFlow_SiteM1.do?reqCode=delDetailForm" 
								   	   paramId="SFLOW_SITE_T0_01,SFLOW_SITE_T0_02" 
								       paramProperty="SFLOW_SITE_T0_01,SFLOW_SITE_T0_02" 
								       onclick=" return confirmShowEMSWait('您確定要刪除資料嗎?'); " >
				刪除				
				</layout:collectionItem>
<%--			</logic:equal>					--%>
		</layout:collection>
		
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立日期" styleClass="LOGDATA" property="DATE_CREATE" styleId="DATE_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	
	</layout:notMode>

</layout:form>