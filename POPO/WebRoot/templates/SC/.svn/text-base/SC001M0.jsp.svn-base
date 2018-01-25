<%@ page language="java"  contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<layout:form action="SC001.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="群組管理" >
	<layout:row>
		<layout:row>
			<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" mode="F,F,D" reqCode="queryForm" property="SC001" policy="query"></layout:image>
			<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="F,F,D" reqCode="addDataForm" 	property="SC001" policy="add"></layout:image>
			<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f" mode="F,F,D" reqCode="editDataForm" property="SC001" policy="modify"></layout:image>
			<layout:image alt="取消" name="btnimage?text=button.cancel&type=t" disabledName="btnimage?text=button.cancel&type=f" mode="F,F,F" reqCode="cancel" property="SC001" policy="cancel" ></layout:image>					
			<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f" mode="F,F,D" reqCode="delData" property="SC001" policy="del" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</layout:row>
	</layout:row>

	<!-- Bean內部要傳到頁面上訊息，在此用JSTL的方式顯示 -->
	<layout:message styleClass="MESSAGE_ERROR" key="${MSG}"/>
			
		<%
		//建立頁次session 
		if(session.getAttribute("Pageid")==null)
			session.setAttribute("Pageid","0");
		%>
			
		<!-- pager 是用來分頁的特殊 panel -->
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection 
				name="Form1Datas"  
				emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" emptyLines="true"
				selectName="SC0010_01"
				selectProperty="SC0010_01"
				id="Form1Datas"
				selectType="radio"
				width="100%"
				styleClass="COLLECTION"
				styleClass2="COLLECTION_2"
			>
				<layout:collectionItem width="20%" title="群組代碼"  property="SC0010_01"  sortable="true" />
				<layout:collectionItem width="30%" title="群組名稱"  property="SC0010_02"/>
				<layout:collectionItem width="50%" title="備註" property="SC0010_03"/>		
	
			</layout:collection>
		</layout:pager>
		
	<layout:message styleClass="FORM" key="${PagerMessage}"/>
</layout:form>

