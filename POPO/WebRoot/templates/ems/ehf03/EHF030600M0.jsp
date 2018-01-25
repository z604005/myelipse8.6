<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.salary.forms.EHF030600M0F" %>
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

<script>

function setTime(){
	//document.getElementById("EHF030600T0_02").value = document.getElementById("EHF030600T0_M").value;
	$("input[name='EHF030600T0_02']").val($("input[name='EHF030600T0_M']").val());
	return true;
}
</script>

<layout:form action="EHF030600M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="薪資計算發放">
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF030600M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="addForm" property="EHF030600M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
<%--			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=儲存&type=t"  reqCode="saveData" property="EHF030600M0" ></layout:image>--%>
<%--			<layout:image alt="回查詢畫面" mode="D,D,D" name="btnimage?text=回查詢畫面&type=t"  reqCode="init" property="EHF030600M0" ></layout:image>--%>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改明細" mode="H,D,H" 	name="btnimage?text=button.update&type=t"  reqCode="editForm" property="EHF030600M0" ></layout:image>
			<layout:image alt="刪除" 	mode="H,D,H" 	name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF030600M0" onclick="return confirm('您確定要刪除資料嗎?')" ></layout:image>
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >

<%--		<layout:select key="入扣帳年月" name="Form1Datas" property="EHF030600T0_05_YY" styleClass="DATAS" mode="E,E,I" >--%>
<%--				<layout:options collection="YY_list" property="item_id" labelProperty="item_value" />--%>
<%--				&nsps;年&nsps;--%>
<%--				<layout:select key="月份" name="Form1Datas" property="EHF030600T0_05_MM" styleClass="DATAS" mode="E,E,I" layout="false" >--%>
<%--					<layout:options collection="MM_list" property="item_id" labelProperty="item_value" />--%>
<%--				</layout:select>--%>
<%--				&nsps;月&nsps;--%>
<%--		</layout:select>--%>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,I"  tooltip="◎入扣帳年月" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF030600T0_M" 
				     key="入扣帳年月" name="Form1Datas" styleClass="DATAS" onkeydown="nextFiled()" onchange="setTime()"/>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,I"  tooltip="◎薪資年月" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF030600T0_02" 
				     key="薪資年月" name="Form1Datas" styleClass="DATAS" onkeydown="nextFiled()" />
		
<%--		<layout:text styleClass="DATAS" tooltip="公司代號" key="公司代號" property="EHF030600T0_03" size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >--%>
<%--			<sp:emspopup function="dep" id="EHF030600T0_03,EHF030600T0_03_TXT" ></sp:emspopup>--%>
<%--			<layout:text layout="false" property="EHF030600T0_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />--%>
<%--		</layout:text>--%>
		
		<layout:text styleClass="DATAS" tooltip="公司代號" key="公司代號" property="EHF030600T0_03" styleId="EHF030600T0_03" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" >
<%--			<sp:emspopup function="dep" id="EHF030600T0_03,EHF030600T0_03_TXT" ></sp:emspopup>--%>
			<layout:text layout="false" property="EHF030600T0_03_TXT" styleId="EHF030600T0_03_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		
		<layout:select key="發放類別" name="Form1Datas" property="EHF030600T0_04" styleId="EHF030600T0_04" styleClass="DATAS" mode="H,H,H" value="01" >
				<layout:options collection="listEHF030600T0_04" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<logic:notEqual name="button" value="edit" >
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
		</logic:equal>
		
<%--		<layout:cell styleClass="DATAS" ></layout:cell>--%>
		
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
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="350" 
				       styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				EHF030600M0F FORM = (EHF030600M0F)list.get(item_index);
				strTmp = FORM.getEHF030600T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
<%--		<layout:collectionItem property="EHF030600T0_U" style="TEXT-ALIGN: CENTER" title="薪資計算UID" />--%>
		<layout:collectionItem property="EHF030600T0_M" style="TEXT-ALIGN: CENTER" title="入扣帳年月" />
		<layout:collectionItem property="EHF030600T0_02" style="TEXT-ALIGN: CENTER" title="薪資年月" />
		<layout:collectionItem property="EHF030600T0_03" style="TEXT-ALIGN: CENTER" title="公司組織" />
		<layout:collectionItem property="EHF030600T0_04" style="TEXT-ALIGN: CENTER" title="發放類別" />
		<layout:collectionItem property="EHF030600T0_C" style="TEXT-ALIGN: CENTER" title="處理總筆數" />
		<layout:collectionItem property="EHF030600T0_AM" style="TEXT-ALIGN: CENTER" title="處理總金額" />
		<layout:collectionItem property="EHF030600T0_SCP" style="TEXT-ALIGN: CENTER" title="薪資處理狀態" />
		<layout:collectionItem property="USER_UPDATE" style="TEXT-ALIGN: CENTER" title="最後異動人員" />
		<layout:collectionItem property="DATE_UPDATE" style="TEXT-ALIGN: CENTER" title="最後異動日期" />
	</layout:collection>
	</layout:pager> 
	</logic:equal>

</layout:form>



