<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
		<layout:form action="SC002.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="程式模組管理" >
		<layout:row>
				<layout:row>
					<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f"  mode="F,F,D" reqCode="queryForm" property="SC002" policy="query"></layout:image>
					<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="F,F,D" reqCode="addDataForm" property="SC002" policy="add"></layout:image>
					<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f"  mode="F,F,D" reqCode="editDataForm" property="SC002" policy="modify"></layout:image>
					<layout:image alt="取消" mode="F,F,F" reqCode="cancel" name="btnimage?text=button.cancel&type=t" property="SC002" policy="cancel" disabledName="btnimage?text=button.cancel&type=f"></layout:image>
					<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f"  mode="F,F,D" property="SC002" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
					<layout:image alt="列印" mode="F,F,D" onclick="return collectionprint(this);" name="btnimage?text=button.print&type=t" property="SC002" policy="print" reqCode="print" disabledName="btnimage?text=button.print&type=f"></layout:image>
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
			
			<sp:collectionprint name="Form1Datas" sql="${Form1Datassql}" heads="程式代碼,程式名稱,子系統,排列序號,是否使用KEY" propertys="SC0020_01,SC0020_02,SC0020_04,SC0020_06,SC0020_07"/>
			
 			  <layout:collection name="Form1Datas" emptyKey="沒有資料列，請依條件輸入關鍵字再作查詢" emptyLines="true"
									selectName="SC0020_01"
									selectProperty="SC0020_01"
									id="Form1Datas"
									width="100%"
									selectType="radio"
									styleClass="COLLECTION"
									styleClass2="COLLECTION_2"		
				>
				  <layout:collectionItem width="20%" title="程式代碼"  property="SC0020_01" sortable="true" />
  				  <layout:collectionItem width="30%" title="程式名稱"  property="SC0020_02"/>
  				  <layout:collectionItem width="30%" title="父節點"   property="SC0020_04"  />
  				  <layout:collectionItem width="10%" title="排列序號"   property="SC0020_06"  />		
  				  <layout:collectionItem width="10%" title="是否使用KEY"   property="SC0020_07"  />		
				</layout:collection>
			</layout:pager>
			<layout:message styleClass="FORM" key="${PagerMessage}"/>
		
		</layout:form>
