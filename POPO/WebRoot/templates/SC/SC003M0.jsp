<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>

<layout:form action="SC003.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="使用者管理">
	<layout:row>
		<layout:row>
			<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" mode="F,F,D" reqCode="doQueryDatas" property="SC003" policy="query"></layout:image>
			<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="F,F,D" reqCode="addDataForm" property="SC003" policy="add"></layout:image>
			<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f" mode="F,F,D" reqCode="editDataForm" property="SC003" policy="modify"></layout:image>
			<layout:image alt="取消" mode="F,F,F" reqCode="cancel" name="btnimage?text=button.cancel&type=t" property="SC003" policy="cancel" disabledName="btnimage?text=button.cancel&type=f">取消</layout:image>
			<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f" mode="F,F,D" property="SC003" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</layout:row>
	</layout:row>
		<!-- Bean內部要傳到頁面上訊息，在此用JSTL的方式顯示 -->
	<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	
	<layout:row>
		<layout:text size="10" maxlength="10" key="使用者代碼:"  property="HSC0030_01" styleClass="DATAS" mode="E,E,E" onkeydown="nextFiled()" />
	</layout:row>
	<layout:row>
		<layout:text size="10" maxlength="10" key="身分證字號:"  property="HSC0030_03" styleClass="DATAS" mode="E,E,E" onkeydown="nextFiled()" />
	</layout:row>
	<layout:row>
		<layout:text size="20" maxlength="50" key="使用者姓名:"   property="HSC0030_04" styleClass="DATAS" mode="E,E,E" onkeydown="nextFiled()" />
	</layout:row>
	
	<%//建立頁次session 
			if (session.getAttribute("Pageid") == null)
				session.setAttribute("Pageid", "0");

		%>

	<!-- pager 是用來分頁的特殊 panel -->
	<layout:pager sessionPagerId="Pageid" linksLocation="both" width="100%">
		<layout:collection name="Form1Datas" emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" emptyLines="true" selectName="SC0030_01" selectProperty="SC0030_01" id="Form1Datas" selectType="radio" width="90%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem width="20%" title="使用者代碼" property="SC0030_01" sortable="true" />
			<layout:collectionItem width="20%" title="身分證字號" property="SC0030_03" sortable="true" />
			<layout:collectionItem width="20%" title="使用者名稱" property="SC0030_04" sortable="true" />
			<layout:collectionItem width="20%" title="啟用" sortable="true">
				<center>
					<layout:checkbox name="Form1Datas" property="SC0030_05" mode="I,I,I" layout="false" />
				</center>
			</layout:collectionItem>
			<layout:collectionItem width="20%" title="備註" property="SC0030_12" />

		</layout:collection>
	</layout:pager>
	<layout:message styleClass="FORM" key="${PagerMessage}" />
</layout:form>

