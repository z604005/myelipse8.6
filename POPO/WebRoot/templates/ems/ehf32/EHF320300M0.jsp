<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF320300M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

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

<layout:form action="EHF320300M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="餐點預排管理查詢">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"
						  reqCode="queryForm" property="EHF320300M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"
						  reqCode="addDataForm" property="EHF320300M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">

		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t"  
						  reqCode="editDataForm" property="EHF320300M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF320300M0" ></layout:image>
		</logic:equal>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="請注意：沒有日期條件下最多顯示七天資料" />
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="日期(起)" size="10"  name="Form1Datas" property="EHF320300T0_02_01" styleClass="DATAS"/>
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="日期(迄)" size="10"  name="Form1Datas" property="EHF320300T0_02_02" styleClass="DATAS"/>
		
		
	</layout:grid>
	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF320300M0F EHF320300m0f =(EHF320300M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) EHF320300m0f.getEHF320300T0_LIST();
		String strTmp = "";
		
	%>
		<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
	
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF320300T0_LIST" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
						   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
			<layout:collectionItem name="bean1" title="選取">
				<center>
				<%
			
				if (item_index < list.size()){
					int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i
					EHF320300M0F FORM=(EHF320300M0F)list.get(i);
					strTmp = FORM.getEHF320300T0_01();					
					item_index++;
				%>
						<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
				<%
				}
				%>
				</center>
			</layout:collectionItem>
		
		<layout:collectionItem name="bean1" property="EHF320300T0_02" style="TEXT-ALIGN: CENTER" title="日期" />
		<layout:nestedCollection property="listEHF320300T0_03" id="bean2" indexId="index2" >
			<layout:collectionItem name="bean2" property="EHF320300T0_03_TXT" style="TEXT-ALIGN: CENTER" title="餐別" />
			<layout:nestedCollection property="listEHF320300T0_05" id="bean3" indexId="index3" >
				<layout:collectionItem name="bean3" property="EHF320300T0_02_TXT" style="TEXT-ALIGN: CENTER" title="項次" />
				<layout:collectionItem name="bean3" property="EHF320300T0_05_TXT" style="TEXT-ALIGN: CENTER" title="菜單名稱" />
			</layout:nestedCollection>
		</layout:nestedCollection>
<%--		<layout:collectionItem property="EHF320300T0_03_TXT" style="TEXT-ALIGN: CENTER" title="餐別" />--%>
<%--		<layout:collectionItem name="bean1" property="EHF320300T0_03" style="TEXT-ALIGN: RIGHT" title="菜單名稱" />--%>
		</layout:collection>
	
	</layout:pager>
	
	</logic:equal>

</layout:form>