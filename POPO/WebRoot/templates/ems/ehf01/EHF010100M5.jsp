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

<!-- JavaScript -->
<script type="text/javascript">

</script>

<layout:form action="EHF010100M5.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="員工基本資料">

	<layout:row>		
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  
					  reqCode="redirect" property="EHF010100M5" ></layout:image>		
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
	
	<logic:equal name="collection" value="show">
	
		<layout:row>
			<layout:message styleClass="" key="員工資料匯入詳細資料" />
		</layout:row>
		<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  
					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF010100T0_02" style="TEXT-ALIGN: CENTER" title="員工工號" />
			<layout:collectionItem property="EHF010100T0_05" style="TEXT-ALIGN: CENTER" title="員工姓名" />
		</layout:collection>
		
	</logic:equal>
	
	<logic:equal name="error_collection" value="show">
		
		<layout:row>
			 <layout:message styleClass="MESSAGE_ERROR" key="員工資料格式不正確未匯入詳細資料" />
		</layout:row>
	
		<layout:collection emptyKey="沒有資料列"  name="Form4Datas" selectId="" selectProperty="" selectName=""  
					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF010100T0_02" style="TEXT-ALIGN: CENTER" title="員工工號" />
			<layout:collectionItem property="EHF010100T0_05" style="TEXT-ALIGN: CENTER" title="員工姓名" />
			<layout:collectionItem property="EHF010100T0_26" style="TEXT-ALIGN: LEFT" title="未匯入原因" filter="false"/>
		</layout:collection>
	</logic:equal>
	
	<logic:equal name="ng_collection" value="show">
		
		<layout:row>
			 <layout:message styleClass="MESSAGE_ERROR" key="員工資料重複未匯入詳細資料" />
		</layout:row>
	
		<layout:collection emptyKey="沒有資料列"  name="Form3Datas" selectId="" selectProperty="" selectName=""  
					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF010100T0_02" style="TEXT-ALIGN: CENTER" title="員工工號" />
			<layout:collectionItem property="EHF010100T0_05" style="TEXT-ALIGN: CENTER" title="員工姓名" />
			<layout:collectionItem property="EHF010100T0_26" style="TEXT-ALIGN: LEFT" title="未匯入原因" filter="false"/>
		</layout:collection>
	</logic:equal>

</layout:form>