<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<layout:form action="SC004.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="子系統管理">

	<layout:row>
		<layout:row>
			<layout:image alt="查詢" mode="F,F,D" reqCode="queryForm" name="btnimage?text=button.query&type=t" property="SC004" policy="query" disabledName="btnimage?text=button.query&type=f"></layout:image>
			<layout:image alt="新增" mode="F,F,D" reqCode="addDataForm" name="btnimage?text=button.add&type=t" property="SC004" policy="add" disabledName="btnimage?text=button.add&type=f"></layout:image>
			<layout:image alt="修改" mode="F,F,D" reqCode="editDataForm" name="btnimage?text=button.update&type=t" property="SC004" policy="modify" disabledName="btnimage?text=button.update&type=f"></layout:image>
			<layout:image alt="取消" mode="F,F,F" reqCode="cancel" name="btnimage?text=button.cancel&type=t" property="SC004" policy="cancel" disabledName="btnimage?text=button.cancel&type=f"></layout:image>
			<layout:image alt="刪除" mode="F,F,D" reqCode="delData" name="btnimage?text=button.delete&type=t" property="SC004" policy="del" disabledName="btnimage?text=button.delete&type=f" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</layout:row>
	</layout:row>

	<!-- Bean內部要傳到頁面上訊息，在此用JSTL的方式顯示 -->
	<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />

	<%//建立頁次session 
			if (session.getAttribute("Pageid") == null)
				session.setAttribute("Pageid", "0");
		%>
	<!-- pager 是用來分頁的特殊 panel -->
	<layout:pager sessionPagerId="Pageid" maxPageItems="10" linksLocation="both" width="100%">
		<layout:pagerStatus key="ddd" />
		<layout:collection name="Form1Datas" emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" emptyLines="true" width="100%" selectName="SC0040_01" selectProperty="SC0040_01" id="Form1Datas" selectType="radio" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem title="子系統代碼" width="100" property="SC0040_01" />
			<layout:collectionItem title="子系統名稱" property="SC0040_02" />
			<layout:collectionItem title="備註" property="SC0040_03" />
			<layout:collectionItem title="排列序號" property="SC0040_04" />
		</layout:collection>
	</layout:pager>
	
</layout:form>

