<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020801M0F" %>
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

<layout:form action="EHF020801M4.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="加班單匯入" >
	
	<layout:row>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  
					  reqCode="redirect" property="EHF020801M4" ></layout:image>		
	</layout:row>
	
	<layout:row>
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<logic:equal name="error_collection" value="show">
		<layout:row>
   			<layout:message styleClass="MESSAGE_ERROR" key="${ERRORMSG}" />
		</layout:row>
	</logic:equal>
	
	<logic:equal name="ng_collection" value="show">
		<layout:row>
   			<layout:message styleClass="MESSAGE_ERROR" key="${NGMSG}" />
		</layout:row>
	</logic:equal>
	
	<logic:equal name="correct_collection" value="show">
		<layout:row>
			<layout:message styleClass="" key="加班單正確匯入詳細資料" />
		</layout:row>
		
		<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF020800T0_06" 		style="TEXT-ALIGN: CENTER"  title="加班日期" />
			<layout:collectionItem property="EHF020800T0_11" 		style="TEXT-ALIGN: CENTER"  title="加班部門" />
			<layout:collectionItem property="EHF020800T1_04" 		style="TEXT-ALIGN: CENTER"  title="員工" />
			<layout:collectionItem property="EHF020800T1_06" 		style="TEXT-ALIGN: CENTER"  title="加班時間" />
<%--			<layout:collectionItem property="EHF020800T1_11" 		style="TEXT-ALIGN: CENTER"  title="加班時數處理方法" />--%>
		</layout:collection>
	</logic:equal>
	
	
	<logic:equal name="error_collection" value="show">
		<layout:row>
			 <layout:message styleClass="MESSAGE_ERROR" key="加班單錯誤未匯入詳細資料" />
		</layout:row>
	
		<layout:collection emptyKey="沒有資料列"  name="Form3Datas" selectId="" selectProperty="" selectName=""  width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF020800T0_06" 		style="TEXT-ALIGN: CENTER"  title="加班日期" />
			<layout:collectionItem property="EHF020800T0_11" 		style="TEXT-ALIGN: CENTER" 	title="加班部門" />
			<layout:collectionItem property="EHF020800T1_04" 		style="TEXT-ALIGN: CENTER"  title="員工" />
			<layout:collectionItem property="EHF020800T1_06" 		style="TEXT-ALIGN: CENTER"  title="加班時間" />
<%--			<layout:collectionItem property="EHF020800T1_11" 		style="TEXT-ALIGN: CENTER"  title="加班時數處理方法" />--%>
			<layout:collectionItem property="ERROR" 				title="未匯入原因"   filter="false"/>
			
		</layout:collection>
	</logic:equal>

</layout:form>