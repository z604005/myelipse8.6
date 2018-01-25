<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.com.forms.EHF000500M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<jsp:include page="/templates/begin.jsp"></jsp:include> 


<!-- JavaScript -->
<script type="text/javascript">


window.onload = function (evt){	
}

function setTime(){
	//document.getElementById("EHF000500T0_06").value = document.getElementById("EHF000500T0_05").value;
     $("input[name='EHF000500T0_06']").val($("input[name='EHF000500T0_05']").val());
	return true;
}
</script>

<layout:form action="EHF000500M0.do" reqCode="" width="100%" styleClass="TITLE"  enctype="multipart/form-data" method="post" key="公司行事曆設定"  >
<%--	<input type="hidden" id="chk_type" value="${chk_type}" />--%>
	<layout:row>
		<logic:equal name="button" value="init">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF000500M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="init_sava" property="EHF000500M0" ></layout:image>
			<layout:image alt="行事曆匯入" mode="D,D,D" name="btnimage?text=button.import.schedule&type=t"  property="EHF000500M0"  onclick="openStrutsLayoutPopup('fileimport'); return false;" ></layout:image> 		  
			<layout:image alt="範例檔下載" mode="D,D,D" name="btnimage?text=button.download.example&type=t" property="EHF000500M0" reqCode="print_example" confirm="您確定要下載資料嗎?"  ></layout:image>  
			
		</logic:equal>
		
		
		<logic:equal name="button" value="save">
			<layout:image alt="儲存" mode="D,D,D" name="btnimage?text=button.save&type=t"  reqCode="addDataForm" property="EHF000500M0" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="init" property="EHF000500M0" ></layout:image>
		</logic:equal>
		
		<logic:equal name="button" value="edit">
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveData" property="EHF000500M0" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="init" property="EHF000500M0" ></layout:image>
		</logic:equal>
		
		
		<logic:equal name="button" value="query">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF000500M0" ></layout:image> 
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"   reqCode="init_sava" property="EHF000500M0" ></layout:image>
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update.Details&type=t"  reqCode="editDataForm" property="EHF000500M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF000500M0" onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
			<layout:image alt="行事曆匯出" mode="D,D,D" name="btnimage?text=button.downlaod.schedule&type=t" reqCode="print" property="EHF000500M0" policy="all"confirm="您確定要列印資料嗎?"/>				
			<layout:image alt="行事曆匯入" mode="D,D,D" name="btnimage?text=button.import.schedule&type=t"  property="EHF000500M0"  onclick="openStrutsLayoutPopup('fileimport'); return false;" />		  
			<layout:image alt="範例檔下載" mode="D,D,D" name="btnimage?text=button.download.example&type=t" property="EHF000500M0"   reqCode="print_example" alert="檔案下載中!! 請稍後!!" ></layout:image>
		</logic:equal>
		
		
		<logic:equal name="button" value="import">
			
			<layout:image alt="行事曆匯入" mode="D,D,D" name="btnimage?text=button.import.schedule&type=t"  property="EHF000500M0"  onclick="openStrutsLayoutPopup('fileimport'); return false;" ></layout:image>		  
			<layout:image alt="範例檔下載" mode="D,D,D" name="btnimage?text=button.download.example&type=t" property="EHF000500M0"   reqCode="print_example" alert="檔案下載中!! 請稍後!!" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="init" property="EHF000500M0" ></layout:image>
		</logic:equal>
		
		
	</layout:row>
	
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	
	
	<logic:notEqual name="button" value="import">
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="公司行事曆資料序號" property="EHF000500T0_01" styleId="EHF000500T0_01" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<logic:notEqual name="button" value="edit" >

		</logic:notEqual>
		
		<logic:equal name="button" value="edit">

		</logic:equal>
		
		<layout:select styleClass="DATAS" key="年度" name="Form1Datas" property="EHF000500T0_03" styleId="EHF000500T0_03"  mode="E,E,I">
			<layout:options collection="EHF000500T0_03_list" property="item_id" labelProperty="item_value" />
		</layout:select>
		<layout:select styleClass="DATAS" tooltip="休假類別" key="休假類別" property="EHF000500T0_04" styleId="EHF000500T0_04" mode="E,E,I" name="Form1Datas">
			<layout:options collection="Vacationlist" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2010" endYear="2100"  key="休假日期(起):" name="Form1Datas" property="EHF000500T0_05" styleClass="DATAS" onchange="setTime()" >
		
				<layout:select key="休假日期(起)(時)" name="Form1Datas" property="EHF000500T0_07_HH" styleId="EHF000500T0_07_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
					<layout:options collection="listEHF000500T0_07_HH" property="item_id" labelProperty="item_value" />
				</layout:select>
				時
				<layout:select key="休假日期(起)(分)" name="Form1Datas" property="EHF000500T0_07_MM" styleId="EHF000500T0_07_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
					<layout:options collection="listEHF000500T0_07_MM" property="item_id" labelProperty="item_value" />
				</layout:select>
				分
		</layout:date>  
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2010" endYear="2100"  key="休假日期(迄):" name="Form1Datas" property="EHF000500T0_06" styleClass="DATAS" >

				<layout:select key="休假時間(迄)(時)" name="Form1Datas" property="EHF000500T0_08_HH" styleId="EHF000500T0_08_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
					<layout:options collection="listEHF000500T0_07_HH" property="item_id" labelProperty="item_value" />
				</layout:select>
				時
				<layout:select key="休假時間(迄)(分)" name="Form1Datas" property="EHF000500T0_08_MM" styleId="EHF000500T0_08_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
					<layout:options collection="listEHF000500T0_07_MM" property="item_id" labelProperty="item_value" />
				</layout:select>
				分
		</layout:date>
		<layout:select key="大小禮拜" name="Form1Datas" property="EHF000500T0_12" styleClass="DATAS" mode="E,E,I" maxlength="10" >
			<layout:options collection="EHF000500T0_12_list" property="item_id" labelProperty="item_value" />
		</layout:select>
		
			
		<layout:cell styleClass="DATAS" ></layout:cell>
		
	</layout:grid>
	
	
	<logic:equal name="button" value="save">
	
		<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
			<logic:equal name="button" value="edit">
				<layout:text key="處理人事" property="EHF000500T0_10" styleId="EHF000500T0_10" mode="I,I,I" name="Form1Datas" styleClass="DATAS" />
				<layout:text key="組織單位" property="EHF000500T0_02" styleId="EHF000500T0_02" mode="I,I,I" name="Form1Datas" styleClass="DATAS" />
			</logic:equal>
		<layout:text key="公司休假原因" property="EHF000500T0_09" styleId="EHF000500T0_09" mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="110" maxlength="50"  />
	
		</layout:grid>
		
	</logic:equal>
	
	<logic:equal name="button" value="edit">
		<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
			<layout:text key="公司休假原因" property="EHF000500T0_09" styleId="EHF000500T0_09" mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="110" maxlength="50"  />
		</layout:grid>
	</logic:equal>
	
	<logic:equal name="button" value="edit">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" 	styleClass="LOGDATA" property="VERSION" 	styleId="VERSION" 		name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>
	
	
	<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
		String strTmp = "";
	%>
	<%
			//建立頁次session 
			//if(session.getAttribute("Pageid")==null)
				session.setAttribute("Pageid","0");
			%>
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%"  styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
			if (item_index < list.size()){ 
				
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				EHF000500M0F FORM=(EHF000500M0F)list.get(i);
				strTmp = FORM.getEHF000500T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" id="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF000500T0_03" style="TEXT-ALIGN: CENTER" title="年度" />
		
		<layout:collectionItem property="EHF000500T0_04" style="TEXT-ALIGN: CENTER" title="類別" />
		<layout:collectionItem property="EHF000500T0_05" style="TEXT-ALIGN: CENTER" title="休假日期" />
		<layout:collectionItem property="EHF000500T0_09" title="公司休假原因" />
	</layout:collection>
	</layout:pager>
	</logic:equal>
	
	
	</logic:notEqual>
	
	
	<logic:equal name="correct_collection" value="show">
		<layout:row>
			<layout:message styleClass="" key="行事曆正確匯入詳細資料" />
		</layout:row>
		
		<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF000500T0_03" style="TEXT-ALIGN: CENTER" title="年度" />
			<layout:collectionItem property="EHF000500T0_04" style="TEXT-ALIGN: CENTER" title="類別" />
			<layout:collectionItem property="EHF000500T0_05" style="TEXT-ALIGN: CENTER" title="休假日期" />
			<layout:collectionItem property="EHF000500T0_09" style="TEXT-ALIGN: CENTER" title="公司休假原因" />
		</layout:collection>
	</logic:equal>
	
	
	<logic:equal name="error_collection" value="show">
		<layout:row>
			 <layout:message styleClass="MESSAGE_ERROR" key="行事曆錯誤未匯入詳細資料" />
		</layout:row>
	
		<layout:collection emptyKey="沒有資料列"  name="Form3Datas" selectId="" selectProperty="" selectName=""  width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF000500T0_03" style="TEXT-ALIGN: CENTER" title="年度" />
			<layout:collectionItem property="EHF000500T0_04" style="TEXT-ALIGN: CENTER" title="類別" />
			<layout:collectionItem property="EHF000500T0_05" style="TEXT-ALIGN: CENTER" title="休假日期" />
			<layout:collectionItem property="EHF000500T0_09" style="TEXT-ALIGN: CENTER" title="公司休假原因" />
			<layout:collectionItem property="ERROR" 				                    title="未匯入原因"   filter="false"/>
			
		</layout:collection>
	</logic:equal>
	
	
	<logic:equal name="button" value="query">
	<layout:popup styleId="fileimport" styleClass="DATAS" key="行事曆檔案匯入">
		<layout:file key="檔案路徑:" property="IMP_EHF000501" fileKey="IMP_EHF000501" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF000500"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>
	</logic:equal>
	
	<logic:equal name="button" value="import">
		<layout:popup styleId="fileimport" styleClass="DATAS" key="行事曆檔案匯入">
		<layout:file key="檔案路徑:" property="IMP_EHF000501" fileKey="IMP_EHF000501" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF000500"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>
	</logic:equal>

	
	<logic:equal name="button" value="init">
	<layout:popup styleId="fileimport" styleClass="DATAS" key="行事曆檔案匯入">
		<layout:file key="檔案路徑:" property="IMP_EHF000501" fileKey="IMP_EHF000501" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF000500"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>
	</logic:equal>
	
	
	
</layout:form>

<%--<jsp:include page="/templates/end.jsp"></jsp:include>--%>

