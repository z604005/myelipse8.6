<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8"%>
<%@ page import="com.spon.utils.struts.form.BA_REPORTF" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<script language="javascript">
<!--//

window.onload = function(){
	document.getElementById('DATA04').style.display="none";
}

//-->
</script>
<layout:form action="LOGIN_LOG.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="登入紀錄查詢作業">
	<layout:row>
		<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" reqCode="queryForm" property="FM020300M0" policy="query"></layout:image>
		<layout:image alt="刪除" mode="F,D,D" name="btnimage?text=刪除&type=t" disabledName="btnimage?text=刪除&type=f" reqCode="delForm" property="FM020300M0" policy="modify" onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
		<layout:image alt="清空畫面條件資料" mode="D,D,D" name="btnimage?text=取消&type=t" disabledName="btnimage?text=取消&type=f" reqCode="init" property="FM020300M0" policy="all"></layout:image>
	</layout:row>
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:grid cols="3" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		<layout:text key="日期" property="DATA04" styleClass="DATAS">
			<layout:date layout="false" size="10" startYear="2010" endYear="2100" patternKey="yyyy/MM/dd" key="" tooltip="日期起" name="Form1Datas" property="DATA01" styleClass="DATAS" mode="E,E,I" />
			~
			<layout:date layout="false" size="10" startYear="2010" endYear="2100" patternKey="yyyy/MM/dd" key="" tooltip="日期訖" name="Form1Datas" property="DATA02" styleClass="DATAS" mode="E,E,I" />
		</layout:text>
		<layout:select key="公司名稱" name="Form1Datas" property="DATA03" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="DATA03_LIST" property="item_id" labelProperty="item_value" />
		</layout:select>
	</layout:grid>
	<%
		int item_index = 0;
		ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
		String strTmp = "";
		
	%>
	<layout:grid cols="1" space="false" borderSpacing="0" align="left" width="100%">
		<input type = "button" value='全部選取' onclick='chkalls("BA_REPORTF",true,"checkId");' name="chk">
		<input type = "button" value='全不選取' onclick='chkalls("BA_REPORTF",false,"checkId");' name="chk">
	</layout:grid>
	<layout:collection emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" emptyLines="true" name="Form2Datas" selectId="" selectProperty="" selectName=""  width="800" height="336" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				BA_REPORTF FORM=(BA_REPORTF)list.get(item_index);
				strTmp = FORM.getDATA11();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="DATA01" title="user" />
		<layout:collectionItem property="DATA02" title="ip" />
		<layout:collectionItem property="DATA03" title="time" />
		<layout:collectionItem property="DATA04" title="comp"/>
	</layout:collection>
</layout:form>
