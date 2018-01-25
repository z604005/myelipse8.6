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

<layout:form action="EHF010100M4.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="員工年度休假匯入" 
			 enctype="multipart/form-data" >

	<layout:row>
		<layout:image alt="年度休假匯入" mode="D,D,D" name="btnimage?text=button.import.empvacation&type=t" 
				      onclick="openStrutsLayoutPopup('fileimport'); return false;"  ></layout:image>		
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  
					  reqCode="redirect" property="EHF010100M4" ></layout:image>		
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
   			<layout:message styleClass="MESSAGE" key="請執行\"員工年度休假匯入\"功能!!" />
		</layout:row>
	</logic:notEqual>
	
	<logic:equal name="collection" value="show">
	
		<layout:row>
			<layout:message styleClass="" key="員工年度休假匯入詳細資料" />
		</layout:row>
		<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  
					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF010300T0_02" style="TEXT-ALIGN: CENTER" title="年度" />
			<layout:collectionItem property="EHF010300T0_04" style="TEXT-ALIGN: CENTER" title="部門名稱" />
			<layout:collectionItem property="EHF010300T0_05" style="TEXT-ALIGN: CENTER" title="員工工號/姓名" />
			<layout:collectionItem property="EHF010300T0_06" style="TEXT-ALIGN: CENTER" title="假別" />
			<layout:collectionItem property="EHF010300T0_07" style="TEXT-ALIGN: CENTER" title="休假時數"/>
			<layout:collectionItem property="EHF010300T0_09" style="TEXT-ALIGN: CENTER" title="使用期間"/>
		</layout:collection>
		
	</logic:equal>
	
	<logic:equal name="error_collection" value="show">
		
		<layout:row>
			 <layout:message styleClass="MESSAGE_ERROR" key="員工年度休假格式不正確未匯入詳細資料" />
		</layout:row>
	
		<layout:collection emptyKey="沒有資料列"  name="Form4Datas" selectId="" selectProperty="" selectName=""  
					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF010300T0_02" style="TEXT-ALIGN: CENTER" title="年度" />
			<layout:collectionItem property="EHF010300T0_04" style="TEXT-ALIGN: CENTER" title="部門名稱" />
			<layout:collectionItem property="EHF010300T0_05" style="TEXT-ALIGN: CENTER" title="員工工號/姓名" />
			<layout:collectionItem property="EHF010300T0_06" style="TEXT-ALIGN: CENTER" title="假別" />
			<layout:collectionItem property="EHF010300T0_07" style="TEXT-ALIGN: CENTER" title="休假時數"/>
			<layout:collectionItem property="EHF010300T0_09" style="TEXT-ALIGN: CENTER" title="使用期間"/>
			<layout:collectionItem property="EHF010300T0_11" style="TEXT-ALIGN: LEFT" title="備註"filter="false"/>
		</layout:collection>
	</logic:equal>
	
<%--	<logic:equal name="ng_collection" value="show">--%>
<%--		--%>
<%--		<layout:row>--%>
<%--			 <layout:message styleClass="MESSAGE_ERROR" key="員工年度休假重複未匯入詳細資料" />--%>
<%--		</layout:row>--%>
<%--	--%>
<%--		<layout:collection emptyKey="沒有資料列"  name="Form3Datas" selectId="" selectProperty="" selectName=""  --%>
<%--					   	   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">--%>
<%--			<layout:collectionItem property="EHF020200T0_03_TXT" style="TEXT-ALIGN: CENTER" title="申請人" />--%>
<%--			<layout:collectionItem property="EHF020200T0_14_DESC" style="TEXT-ALIGN: CENTER" title="假別"/>--%>
<%--			<layout:collectionItem property="EHF020200T0_09" style="TEXT-ALIGN: CENTER" title="日期/時間"/>--%>
<%--			<layout:collectionItem property="EHF020200T0_15" style="TEXT-ALIGN: LEFT" title="未匯入原因" filter="false"/>--%>
<%--		</layout:collection>--%>
<%--	</logic:equal>--%>
	
	<layout:popup styleId="fileimport" styleClass="DATAS" key="員工年度休假匯入">
		<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="uploadEHF010300M1"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>
	
</layout:form>