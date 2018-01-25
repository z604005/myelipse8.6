<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020104M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<script>


</script>

<layout:form action="EHF020104M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="預排換班設定">
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020104M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="addDataForm" property="EHF020104M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t"  reqCode="editDataForm" property="EHF020104M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF020104M0" onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:date  calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" tooltip="◎換班日期區間(起)" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF020104T0_01" 
				     key="換班日期區間(起)" name="Form1Datas" styleClass="DATAS"  
				     onkeydown="nextFiled()" />
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd"  tooltip="◎換班日期區間(迄)" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF020104T0_02" 
				     key="換班日期區間(迄)" name="Form1Datas" styleClass="DATAS" mode="R,R,I" 
				     onkeydown="nextFiled()" />
		
		<logic:notEqual name="button" value="edit" >
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
		</logic:equal>
		
	</layout:grid>

	
	<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
		String strTmp = "";
	%>
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				EHF020104M0F FORM = (EHF020104M0F)list.get(i);
				strTmp = FORM.getEHF020104T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF020104T0_01" style="TEXT-ALIGN: CENTER" title="序號" />
		<layout:collectionItem property="EHF020104T0_02" style="TEXT-ALIGN: CENTER" title="換班日期" />
		
		
		<layout:collectionItem property="USER_UPDATE" style="TEXT-ALIGN: CENTER" title="異動人員" />
		<layout:collectionItem property="EHF020104T0_05" style="TEXT-ALIGN: LEFT" title="備註" />
	</layout:collection>
	</layout:pager> 
	</logic:equal>

</layout:form>