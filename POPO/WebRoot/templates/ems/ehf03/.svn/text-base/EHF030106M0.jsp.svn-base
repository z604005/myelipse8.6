<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.utils.struts.form.BA_REPORTF" %>
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

$(document).ready(function(){
	var id = $("input[name=DATA05]");
	var reqCode = $("input[name=reqCode]");
	var insert = $("input[name=insert]");
	var save = $("input[name=save]");
	
	insert.bind("click",function(){
		if(id.val().length > 0){
			reqCode.val("InsertForm");
		}else{
			alert("銀行代號欄位必填");
			id.focus();
			return false;
		}
	});
	
	save.bind("click",function(){
		if(id.val().length > 0){
			reqCode.val("SaveForm");
		}else{
			alert("銀行代號欄位必填");
			id.focus();
			return false;
		}
	});
});
</script>

<layout:form action="EHF030106M0.do" reqCode="" width="100%"  styleClass="TITLE" method="post" key="轉帳銀行設定">
	<!--<layout:row> 
		<c:if test="${Status != '2'}">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=查詢&type=t" reqCode="queryForm" property="query" policy="all"></layout:image>
			<layout:image alt="新增" mode="D,D,D" name="btnimage?text=新增&type=t" property="insert" policy="all"></layout:image>
			<layout:image alt="明細資料" mode="D,D,D" name="btnimage?text=明細資料&type=t" reqCode="DetailForm" property="EHF030106M0" policy="all"></layout:image>
			<layout:image alt="刪除" mode="D,D,D" name="btnimage?text=刪除&type=t" reqCode="DelForm" property="EHF030106M0" policy="all" 
						  onclick="return confirm('您確定要刪除資料嗎?')" ></layout:image>
		</c:if>
		<c:if test="${Status == '2'}">
			<layout:image alt="儲存" mode="D,D,D" name="btnimage?text=儲存&type=t" property="save" policy="all"></layout:image>
			<layout:image alt="回查詢頁面" mode="D,D,D" name="btnimage?text=回查詢頁面&type=t" reqCode="goback" property="EHF030106M0" policy="all"></layout:image>
		</c:if>
	</layout:row>-->
	
	<!--<layout:row>
		<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=查詢&type=t" reqCode="queryForm" 	property="EHF030106M0" ></layout:image>
		<layout:image alt="新增" mode="D,D,D" name="btnimage?text=新增&type=t" reqCode="InsertForm" 	property="EHF030106M0" ></layout:image>
		<layout:image alt="刪除" mode="D,D,D" name="btnimage?text=刪除&type=t" reqCode="DelForm" 	property="EHF030106M0"   onclick="return confirm('您確定要刪除資料嗎?')" ></layout:image>					
		<!--<layout:image alt="回上一頁" mode="D,D,D" name="btnimage?text=回上一頁&type=t" reqCode="goback" property="EHF030105M1" policy="all"></layout:image>
	</layout:row>-->
	
	<layout:row>
		<logic:equal name="button" value="init">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF030106M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="init_add" property="EHF030106M0" ></layout:image>
		</logic:equal>
		
		<logic:equal name="button" value="add">
			<layout:image alt="儲存" mode="D,D,D" name="btnimage?text=button.save&type=t" reqCode="InsertForm" property="EHF030106M0" policy="all"></layout:image>
			<layout:image alt="回查詢頁面" mode="D,D,D" name="btnimage?text=button.Back&type=t" reqCode="goback" property="EHF030106M0" policy="all"></layout:image>
		</logic:equal>
		
		
		<logic:equal name="button" value="save">
			<layout:image alt="儲存" mode="D,D,D" name="btnimage?text=button.save&type=t" reqCode="SaveForm" property="EHF030106M0" policy="all"></layout:image>
			<layout:image alt="回查詢頁面" mode="D,D,D" name="btnimage?text=button.Back&type=t" reqCode="goback" property="EHF030106M0" policy="all"></layout:image>
		</logic:equal>
		
		<logic:equal name="button" value="query">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF030106M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="init_add" property="EHF030106M0" ></layout:image>
			<layout:image alt="修改明細" mode="D,D,H" name="btnimage?text=button.update&type=t"	reqCode="DetailForm" property="EHF030106M0" ></layout:image>
			<layout:image alt="刪除" mode="D,D,H" name="btnimage?text=button.delete&type=t"			reqCode="DelForm" property="EHF030106M0"   confirm="您確定要刪除資料嗎?" ></layout:image>
		</logic:equal>
	</layout:row>
	

	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>

	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="銀行代號" 	property="DATA05" styleId="DATA05" mode="E,I,I" name="Form1Datas" styleClass="DATAS" maxlength="10"/>
		<layout:text key="銀行名稱" 	property="DATA06" styleId="DATA06" mode="E,E,I" name="Form1Datas" styleClass="DATAS" maxlength="40"/>
		<layout:text key="備註" 		property="DATA07" styleId="DATA07" mode="E,E,I" name="Form1Datas" styleClass="DATAS" maxlength="50"/>
		<layout:cell styleClass="DATAS" ></layout:cell>
		
		<c:if test="${Status == '2'}">
			<layout:text key="資料建立人員" property="DATA11" mode="I,I,I" name="Form1Datas" styleClass="DATAS" />
			<layout:text key="最後異動人員" property="DATA12" mode="I,I,I" name="Form1Datas" styleClass="DATAS" />
			<layout:text key="資料版本" property="DATA13" mode="I,I,I" name="Form1Datas" styleClass="DATAS" />
			<layout:text key="最後異動日期" property="DATA14" mode="I,I,I" name="Form1Datas" styleClass="DATAS" />
		</c:if>
	</layout:grid>
	
	
	
	
	
	<logic:equal name="collection" value="show">
	
		<%
			int item_index = 0;
			ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
			String strTmp = "";
		%>
		<layout:collection emptyKey="沒有資料列" name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="300" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem title="選取">
				<center>
				<%
			
				if (item_index < list.size()){
					//EMS_VIEWDATAF FORM = (EMS_VIEWDATAF)list.get(item_index);
					BA_REPORTF FORM = (BA_REPORTF)list.get(item_index);
					strTmp = String.valueOf(FORM.getDATA00());
					item_index++;
				%>
				<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
				<%
				}
				%>
				</center>
			</layout:collectionItem>
		
			<layout:collectionItem property="DATA08" title="銀行代號" />
			<layout:collectionItem property="DATA09" title="銀行名稱" />
			<layout:collectionItem property="DATA10" title="備註"/>
		</layout:collection>
	</logic:equal>
</layout:form>