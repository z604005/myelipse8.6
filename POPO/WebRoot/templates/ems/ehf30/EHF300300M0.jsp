<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF300300M0F" %>
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

<layout:form action="EHF300300M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="早中晚餐定價">
<layout:row>
		<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" reqCode="queryForm" property="EHF300300M0" ></layout:image>
		<layout:image alt="新增" mode="D,D,D" name="btnimage?text=button.add&type=t" reqCode="addDataForm" property="EHF300300M0" ></layout:image>
		<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t" reqCode="editDataForm" property="EHF300300M0" ></layout:image>
		<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		reqCode="delForm" property="EHF300300M0" ></layout:image>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF300300M0F ehf300300m0f =(EHF300300M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf300300m0f.getEHF300300C();
		String strTmp = "";
		%>
<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >	
		<layout:text styleClass="DATAS" key="餐點名稱"property="EHF300300T0_02_select" styleId="EHF300300T0_02" size="10" mode="E,E,I" maxlength="10"
					name="Form1Datas" />
					
</layout:grid>
		<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
	
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
		
			<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF300300C" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
							   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
				<layout:collectionItem name="bean1" title="選取">
					<center>
					<%
					if (item_index < list.size()){
						int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i
						EHF300300M0F FORM=(EHF300300M0F)list.get(i);
						strTmp = FORM.getEHF300300T0_01();					
						item_index++;
					%>
						<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
					<%
					}
					%>
					</center>
				</layout:collectionItem>
				<layout:collectionItem property="EHF300300T0_02_TXT" style="TEXT-ALIGN: CENTER" title="餐點名稱" />
				<layout:collectionItem property="EHF300300T0_03" style="TEXT-ALIGN: CENTER" title="定價" />

			</layout:collection>
		
		</layout:pager>
	</logic:equal>

</layout:form>