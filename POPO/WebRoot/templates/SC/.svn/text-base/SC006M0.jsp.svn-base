<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
		<layout:form action="SC006.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="系統參數管理" >
		<layout:row>
				<layout:row>
					<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f"  mode="F,F,D" reqCode="queryForm" property="SC006" policy="query"></layout:image>
					<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="F,F,D" reqCode="addDataForm" property="SC006" policy="add"></layout:image>
					<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f"  mode="F,F,D" reqCode="editDataForm" property="SC006" policy="modify"></layout:image>
					<layout:image alt="取消" mode="F,F,F" reqCode="cancel" name="btnimage?text=button.cancel&type=t" property="SC006" policy="cancel" disabledName="btnimage?text=button.cancel&type=f"></layout:image>
					<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f"  mode="F,F,D" property="SC006" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
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
			<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%">
				<layout:collection name="Form1Datas" emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" emptyLines="true"
									selectName="SC0060_01"
									selectProperty="SC0060_01"
									id="Form1Datas"
									width="100%"
									height="280"
									selectType="radio"
									styleClass="COLLECTION"
									styleClass2="COLLECTION_2"									
									>
				  <layout:collectionItem title="參數名稱"  property="SC0060_01" sortable="true" />
  				  <layout:collectionItem title="參數說明"  property="SC0060_02"/>
  				  <layout:collectionItem title="參數值"  property="SC0060_03"/>
				  <layout:collectionItem title="備註" property="SC0060_04"/>		
		
				</layout:collection>
			</layout:pager>
			<layout:message styleClass="FORM" key="${PagerMessage}"/>
		</layout:form>

