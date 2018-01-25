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

<layout:form action="SFlow_SiteM0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="簽核流程設定">

	<layout:row>		
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"
						  reqCode="queryForm" property="SFlow_SiteM0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"
						  reqCode="addDataForm" property="SFlow_SiteM0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t"  
						  reqCode="editDataForm" property="SFlow_SiteM0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="SFlow_SiteM0" ></layout:image>
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
	
		<layout:text key="FLOW編號" property="SFLOW_SITE_HD_01" styleId="SFLOW_SITE_HD_01" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="12" maxlength="30" />
		<layout:text key="FLOW名稱" property="SFLOW_SITE_HD_02" styleId="SFLOW_SITE_HD_02" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="12" maxlength="50" />
	
	</layout:grid>
	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		SFlow_SiteF sflow_sitef =(SFlow_SiteF)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) sflow_sitef.getSFLOW_SITE_LIST();
		String strTmp = "";
		
	%>
		<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
	
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="25" >
	
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="SFLOW_SITE_LIST" styleId="SFLOW_SITE_LIST" 
						   id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
						   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
			<layout:collectionItem name="bean1" title="選取">
				<center>
				<%
			
				if (item_index < list.size()){
					int i=Integer.valueOf((String) session.getAttribute("Pageid"))*25+item_index;//可以隨著換頁時  累加 i
					SFlow_SiteF FORM=(SFlow_SiteF)list.get(i);
					strTmp = FORM.getSFLOW_SITE_HD_01();					
					item_index++;
				%>
						<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
				<%
				}
				%>
				</center>
			</layout:collectionItem>
		
			<layout:collectionItem property="SFLOW_SITE_HD_01" style="TEXT-ALIGN: CENTER" title="FLOW編號" />
			<layout:collectionItem property="SFLOW_SITE_HD_02" style="TEXT-ALIGN: CENTER" title="FLOW名稱" />

		</layout:collection>
	
	</layout:pager>
	
	</logic:equal>

</layout:form>