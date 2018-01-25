<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF320200M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JavaScript -->
<script type="text/javascript">
//chooseOne()函式，參數為觸發該函式的元素本身
function chooseOne(cb){
	//先取得同name的chekcBox的集合物件
	var obj = document.getElementsByName("checkId");
	for (i=0; i<obj.length; i++){
		//判斷obj集合中的i元素是否為cb，若否則表示未被點選
		if (obj[i]!=cb)	obj[i].checked = false;
		//若是 但原先未被勾選 則變成勾選；反之 則變為未勾選
		else	obj[i].checked = cb.checked;
		//若要至少勾選一個的話，則把上面那行else拿掉，換用下面那行
		//else obj[i].checked = true;
	}
}
</script>

<layout:form action="EHF320200M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="上菜順序查詢">
	<layout:row>
		<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" reqCode="queryForm" property="EHF320200M0" ></layout:image>
		<layout:image alt="新增" mode="D,D,D" name="btnimage?text=button.add&type=t" reqCode="addDataForm" property="EHF320200M0" ></layout:image>
		<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t" reqCode="editDataForm" property="EHF320200M0" ></layout:image>
		<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		reqCode="delForm" property="EHF320200M0" ></layout:image>
	</layout:row>

	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:number key="上菜順序(天)" property="EHF320200T0_01" styleId="EHF320200T0_01" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="5" maxlength="10" />
		<layout:number key="上菜順序(項)" property="EHF320200T0_02" styleId="EHF320200T0_02" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="5" maxlength="20" />
	</layout:grid>
	
<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		EHF320200M0F ehf320200m0f =(EHF320200M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf320200m0f.getEHF320200_C();
		String strTmp = "";
	%>
	
	<%
		//建立頁次session 
		//if(session.getAttribute("Pageid")==null)
			session.setAttribute("Pageid","0");
	%>

	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%"  maxPageItems="15">
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF320200_C"  id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
								   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<%--	
			<layout:pager linksLocation="both" maxPageItems="10" sessionPagerId="10">
				<layout:collection emptyKey="沒有資料列" name="Form2Datas" selectId="" selectProperty="" selectName=""
				 width="100%" height="500" styleClass="COLLECTION" styleClass2="COLLECTION_2">
				 --%>
				<layout:collectionItem name="bean1" title="選取">
				<center>
				<%
					if (item_index < list.size()){
						int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i
						EHF320200M0F FORM=(EHF320200M0F)list.get(i);
						strTmp = FORM.getKEY_01C02C();					
						item_index++;
				%>
							<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
							
				<%
					}
				%>
				</center>
				</layout:collectionItem>
				<layout:collectionItem property="EHF320200T0_01C" style="TEXT-ALIGN: CENTER" title="上菜順序(天) "  />
				<layout:collectionItem property="EHF320200T0_02C" style="TEXT-ALIGN: CENTER" title="上菜順序(項)" />
				<layout:collectionItem property="EHF320200T0_03_TXT" style="TEXT-ALIGN: CENTER" title="菜單名稱" />
			</layout:collection>
		</layout:pager>
	</logic:equal>
</layout:form>