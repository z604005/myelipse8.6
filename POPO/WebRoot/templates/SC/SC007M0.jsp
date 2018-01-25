<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>

<style type="text/css"> 
　　 .noborderline{
　　　　border-top-style: none;
　　　　 border-right-style: none;
　　　　 border-bottom-style: none;
　　　　 border-left-style: none;
　　　     border-bottom-color: #000000;
　　 } 
</style>


<layout:form action="SC007.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="訊息廣播管理">
	<layout:row>
		<layout:row>
			<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" mode="F,D,D" reqCode="queryForm" property="SC007" policy="query"></layout:image>
			<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="F,D,D" reqCode="addDataForm" property="SC007" policy="add"></layout:image>
			<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f" mode="F,D,F" reqCode="editDataForm" property="SC007" policy="modify"></layout:image>
			<layout:image alt="取消" mode="F,F,F" reqCode="cancel" name="btnimage?text=button.cancel&type=t" property="SC007" policy="cancel" disabledName="btnimage?text=button.cancel&type=f"></layout:image>
			<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f" mode="F,D,F" property="SC007" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</layout:row>

	</layout:row>

	<!-- Bean內部要傳到頁面上訊息，在此用JSTL的方式顯示 -->
	<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	<layout:message styleClass="MESSAGE_ERROR" key="${MSG2}" />
	<%//建立頁次session 
			if (session.getAttribute("Pageid") == null)
				session.setAttribute("Pageid", "0");

		%>

	<!-- pager 是用來分頁的特殊 panel -->
	<layout:pager sessionPagerId="Pageid" linksLocation="both" width="100%">
		<layout:collection name="Form1Datas" emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" emptyLines="true" selectName="SC0070_01" selectProperty="SC0070_01" id="Form1Datas" selectType="radio" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" indexId="index" >
			<layout:collectionItem width="60%" title="訊息標題" sortable="true">
				<layout:text styleClass="DATAS" size="40" name="Form1Datas" property="SC0070_02" styleId="SC0070_02" mode="I,I,I" layout="false" />
			</layout:collectionItem>
			<layout:collectionItem width="10%" title="啟用">
				<center>
					<layout:checkbox name="Form1Datas" property="SC0070_07" styleId="SC0070_07" mode="I,I,I" layout="false" />
				</center>
			</layout:collectionItem>
			<layout:collectionItem width="30%" title="備註" property="SC0070_08" />
		</layout:collection>

	</layout:pager>
	<layout:message styleClass="FORM" key="${PagerMessage}" />
</layout:form>

