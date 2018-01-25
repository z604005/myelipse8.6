<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020800M0F" %>
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
function setTime(){
	//document.getElementById("EHF020800T0_06_END").value = document.getElementById("EHF020800T0_06").value;
	$("input[name='EHF020800T0_06_END']").val($("input[name='EHF020800T0_06']").val());
	return true;
}

</script>
<layout:form action="EHF020800M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="加班單申請" >

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<logic:notEqual name="button" value="Import">
				<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020800M0" ></layout:image>
				<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="addDataForm" property="EHF020800M0" ></layout:image>
				
			</logic:notEqual>
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
<%--			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=儲存&type=t" 
							  reqCode="saveDataForm" property="EHF030600M0" ></layout:image>--%>
<%--			<layout:image alt="回查詢畫面" mode="D,D,D" name="btnimage?text=回查詢畫面&type=t" 
							  reqCode="init" property="EHF030600M0" ></layout:image>--%>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t" reqCode="editDataForm" property="EHF020800M0" ></layout:image>
		</logic:equal>
		<logic:notEqual name="button" value="edit">
			<logic:notEqual name="button" value="Import">
				<%--<layout:image alt="加班單匯入" mode="D,D,D" name="btnimage?text=botton.import.overtime&type=t" onclick="openStrutsLayoutPopup('fileimport'); return false;"  ></layout:image>--%>
				<%--<layout:image alt="範例檔下載" mode="D,D,D" name="btnimage?text=button.download.example&type=t" property="EHF020800M0"   reqCode="print_example" confirm="您確定要下載資料嗎?"  ></layout:image>--%>  
<%--				<logic:notEqual name="DisplayFile" value="" >--%>
<%--					<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t"   onclick="opendownloadfile(); return false;" ></layout:image>--%>
<%--				</logic:notEqual>--%>
			</logic:notEqual>
		</logic:notEqual>
		<logic:equal name="button" value="Import">
<%--			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=儲存&type=t" reqCode="saveDataForm" property="EHF030600M0" ></layout:image>--%>
			<layout:image alt="加班單匯入" mode="D,D,D" name="btnimage?text=botton.import.overtime&type=t" onclick="openStrutsLayoutPopup('fileimport'); return false;"  ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t" 	  reqCode="init" property="EHF020800M0" ></layout:image>
		</logic:equal>
	</layout:row>
	
	<logic:notEqual name="SHOW" value="NO">
		<layout:row >
   			 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 	<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
		</layout:row>
	</logic:notEqual>
	
	<logic:equal name="SHOW" value="NO">
	 	<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
	</logic:equal>
	
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
	
	
	<logic:notEqual name="button" value="edit">
		<logic:notEqual name="button" value="Import">
			<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2009" endYear="2025"  key="加班日期區間" 		name="Form1Datas" property="EHF020800T0_06" 	size="10" styleClass="DATAS"  onchange="setTime()">
					&nbsp;&nbsp;~&nbsp;&nbsp;
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2009" endYear="2025"  key="加班日期區間(迄)"	name="Form1Datas" property="EHF020800T0_06_END" size="10" styleClass="DATAS"  layout="false" />
			</layout:date>
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11" styleId="EHF020800T0_11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11_SHOW" styleId="EHF020800T0_11_SHOW"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020800M0F" 
					id="EHF020800T0_11,EHF020800T0_11_SHOW,EHF020800T0_11_TXT" 
					lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
					table="EHF000200T0"
					fieldAlias="系統代碼,部門代號,部門名稱" 
					fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
					others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					mode="E,E,F"
					/>
				<layout:text layout="false" property="EHF020800T0_11_TXT" styleId="EHF020800T0_11_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			<layout:select key="表單狀態" name="Form1Datas" property="EHF020800T0_09" styleId="EHF020800T0_09" styleClass="DATAS" mode="E,E,I" >
				<layout:options collection="listEHF020800T0_09" property="item_id" labelProperty="item_value" />
			</layout:select>
			<layout:space styleClass="DATAS" />
			</layout:grid>

		</logic:notEqual>
	</logic:notEqual>
	
	
	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF020800M0F ehf020800m0f =(EHF020800M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf020800m0f.getEHF020800T0_LIST();
		String strTmp = "";
		
	%>
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
	%>
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF020800T0_LIST" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
		<layout:collectionItem name="bean1" title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				Map FORM = (Map) list.get(i);
				strTmp = ((String) FORM.get("EHF020800T0_01"))+"";
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF020800T0_06" style="TEXT-ALIGN: CENTER" name="bean1" title="加班日期" />
		<layout:collectionItem property="EHF020800T0_03" style="TEXT-ALIGN: CENTER" name="bean1" title="填單人" />
		<layout:collectionItem property="EHF020800T0_11" style="TEXT-ALIGN: CENTER" name="bean1" title="加班部門" />
		<layout:collectionItem property="EHF020800T0_09_DESC" style="TEXT-ALIGN: CENTER" name="bean1" title="處理狀態" />
		
		<layout:nestedCollection property="EHF020800T1_LIST" id="bean2" indexId="index2" >
			<layout:collectionItem property="EHF020800T1_04" title="員工"  name="bean2" style="TEXT-ALIGN: CENTER" />
			<layout:collectionItem property="EHF020800T1_06" title="加班時間"  name="bean2" style="TEXT-ALIGN: CENTER" />
		</layout:nestedCollection>
	</layout:collection>
	</layout:pager> 
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
			<layout:collectionItem property="EHF020800T1_11" 		style="TEXT-ALIGN: CENTER"  title="加班時數處理方法" />
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
			<layout:collectionItem property="EHF020800T1_11" 		style="TEXT-ALIGN: CENTER"  title="加班時數處理方法" />
			<layout:collectionItem property="ERROR" 				title="未匯入原因"   filter="false"/>
			
		</layout:collection>
	</logic:equal>
	
	<layout:popup styleId="fileimport" styleClass="DATAS" key="檔案匯入">
		<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF020800"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>


</layout:form>

<%--以下方法，在列印時，可以出現遮罩，目前未調整遮罩的CSS，因此先不用   Alvin--%>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />