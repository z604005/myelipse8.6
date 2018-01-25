<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020200M0F" %>
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


<layout:form action="EHF020200M4.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="請假單匯入" 
			 enctype="multipart/form-data" >

	<layout:row>
		<layout:image alt="請假單匯入" mode="D,D,D" name="btnimage?text=button.import.vacation&type=t" 
				      onclick="openStrutsLayoutPopup('fileimport'); return false;"  ></layout:image>		
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  
					  reqCode="redirect" property="EHF020200M4" ></layout:image>		
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
	
	
	<logic:notEqual name="collection" value="show">
		<layout:row>
   			<layout:message styleClass="MESSAGE" key="請執行\"請假單匯入\"功能!!" />
		</layout:row>
	</logic:notEqual>
	
	<logic:equal name="collection" value="show">
	
		<layout:row>
			<layout:message styleClass="" key="請假單匯入詳細資料" />
		</layout:row>
		<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  
					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF020200T0_03_TXT" style="TEXT-ALIGN: CENTER" title="申請人" />
			<layout:collectionItem property="EHF020200T0_14_DESC" style="TEXT-ALIGN: CENTER" title="假別"/>
			<layout:collectionItem property="EHF020200T0_09" style="TEXT-ALIGN: CENTER" title="日期/時間"/>
			<layout:collectionItem property="EHF020200T0_15" style="TEXT-ALIGN: LEFT" title="事由" filter="false"/>
<%--			<layout:collectionItem property="DATA05" style="TEXT-ALIGN: LEFT" title="狀態"/>--%>
		</layout:collection>
		
	</logic:equal>
	
	<logic:equal name="error_collection" value="show">
		
		<layout:row>
			 <layout:message styleClass="MESSAGE_ERROR" key="請假單格式不正確未匯入詳細資料" />
		</layout:row>
	
		<layout:collection emptyKey="沒有資料列"  name="Form4Datas" selectId="" selectProperty="" selectName=""  
					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF020200T0_03_TXT" style="TEXT-ALIGN: CENTER" title="申請人" />
			<layout:collectionItem property="EHF020200T0_14_DESC" style="TEXT-ALIGN: CENTER" title="假別"/>
			<layout:collectionItem property="EHF020200T0_09" style="TEXT-ALIGN: CENTER" title="日期/時間"/>
			<layout:collectionItem property="EHF020200T0_15" style="TEXT-ALIGN: LEFT" title="未匯入原因" filter="false"/>
<%--			<layout:collectionItem property="DATA05" style="TEXT-ALIGN: LEFT" title="狀態"/>--%>
		</layout:collection>
	</logic:equal>
	
	<logic:equal name="ng_collection" value="show">
		
		<layout:row>
			 <layout:message styleClass="MESSAGE_ERROR" key="請假單重複未匯入詳細資料" />
		</layout:row>
	
		<layout:collection emptyKey="沒有資料列"  name="Form3Datas" selectId="" selectProperty="" selectName=""  
					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF020200T0_03_TXT" style="TEXT-ALIGN: CENTER" title="申請人" />
			<layout:collectionItem property="EHF020200T0_14_DESC" style="TEXT-ALIGN: CENTER" title="假別"/>
			<layout:collectionItem property="EHF020200T0_09" style="TEXT-ALIGN: CENTER" title="日期/時間"/>
			<layout:collectionItem property="EHF020200T0_15" style="TEXT-ALIGN: LEFT" title="未匯入原因" filter="false"/>
<%--			<layout:collectionItem property="DATA05" style="TEXT-ALIGN: LEFT" title="狀態"/>--%>
		</layout:collection>
	</logic:equal>
	
	<layout:popup styleId="fileimport" styleClass="DATAS" key="檔案匯入">
		<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF020200"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>
	
</layout:form>