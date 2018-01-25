<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.salary.forms.EHF030600M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>

<%--function chkApplyDeptID(){--%>
<%--	if(document.getElementById("DATA04").value == "" || document.getElementById("DATA04").value == null ){--%>
<%--		alert("請先選擇部門!!");--%>
<%--		return false;--%>
<%--	}else{--%>
<%--		return true;--%>
<%--	}--%>
<%--}--%>

function setTime(){
	//document.getElementById("EHF030600T0_02").value = document.getElementById("EHF030600T0_M").value;
	$("input[name='EHF030600T0_02']").val($("input[name='EHF030600T0_M']").val());
	return true;
}


</script>

<layout:form action="EHF030702M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="個人薪資條">
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF030702M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="列印" mode="H,D,D" name="btnimage?text=button.print&type=t" property="EHF030702M1" reqCode="print" confirm="您確定要列印資料嗎?">
			</layout:image>
		</logic:equal>
<%--		<logic:notEqual name="DisplayFileName" value="" >--%>
<%--			<layout:image alt="下載檔案" mode="H,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" ></layout:image>--%>
<%--		</logic:notEqual>--%>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
		<layout:message styleClass="MESSAGE_ERROR" key="${ERRMSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,I" tooltip="◎入扣帳年月" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF030600T0_M" 
				     key="入扣帳年月" name="Form1Datas" styleClass="DATAS" onkeydown="nextFiled()" onchange="setTime()"/>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,I" tooltip="◎薪資年月" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF030600T0_02" 
				     key="薪資年月" name="Form1Datas" styleClass="DATAS" onkeydown="nextFiled()" />
		
<%--		<layout:text styleClass="DATAS" tooltip="部門代號" key="部門代號" property="DATA04" size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >--%>
<%--			<sp:emspopup function="dep" id="DATA04,DATA05" ></sp:emspopup>--%>
<%--			<layout:text layout="false" property="DATA05" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />--%>
<%--		</layout:text>--%>
<%--			--%>
<%--		<layout:text styleClass="DATAS" tooltip="員工工號" key="員工工號" property="DATA06" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >--%>
<%--			<sp:emspopup function="samedepemp" id="DATA06,DATA07" depkey="window.EMS_VIEWDATAF.DATA04.value" --%>
<%--				         beforerun="chkApplyDeptID()" ></sp:emspopup>--%>
<%--			<layout:text layout="false" property="DATA07" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />--%>
<%--		</layout:text>--%>
		
		
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
		<layout:collectionItem property="EHF030600T0_U" style="TEXT-ALIGN: CENTER" title="薪資計算UID" />
		<layout:collectionItem property="EHF030600T0_M" style="TEXT-ALIGN: CENTER" title="入扣帳年月" />
		<layout:collectionItem property="EHF030600T0_02" style="TEXT-ALIGN: CENTER" title="薪資年月" />
		<layout:collectionItem property="EHF030600T0_03" style="TEXT-ALIGN: CENTER" title="公司組織" />
		<layout:collectionItem property="EHF030600T0_04" style="TEXT-ALIGN: CENTER" title="發放類別" />
		<layout:collectionItem property="EHF030600T0_C" style="TEXT-ALIGN: CENTER" title="處理總筆數" />
<%--		<layout:collectionItem property="EHF030600T0_AM" style="TEXT-ALIGN: CENTER" title="處理總金額" />--%>
		<layout:collectionItem property="EHF030600T0_SCP" style="TEXT-ALIGN: CENTER" title="薪資處理狀態" />
<%--		<layout:collectionItem property="USER_UPDATE" style="TEXT-ALIGN: CENTER" title="最後異動人員" />--%>
<%--		<layout:collectionItem property="DATE_UPDATE" style="TEXT-ALIGN: CENTER" title="最後異動日期" />--%>
	</layout:collection>
	</layout:pager> 
	</logic:equal>
	
	
</layout:form>

<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />