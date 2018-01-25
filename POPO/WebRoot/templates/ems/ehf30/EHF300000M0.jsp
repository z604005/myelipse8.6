<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF300000M0F" %>
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

<layout:form action="EHF300000M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="食物代碼查詢">
	<layout:row>
		<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" reqCode="queryForm" property="EHF300000M0" ></layout:image>
		<layout:image alt="新增" mode="D,D,D" name="btnimage?text=button.add&type=t" reqCode="addDataForm" property="EHF300000M0" ></layout:image>
		<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t" reqCode="editDataForm" property="EHF300000M0" ></layout:image>
		<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		reqCode="delForm" property="EHF300000M0" ></layout:image>
	</layout:row>

	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="食物類別代碼" property="PSFOODT0_01" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="6" maxlength="10" />
		<layout:text key="食物類別名稱" property="PSFOODT0_04" mode="E,E,I" name="Form1Datas" styleClass="DATAS" 
					 size="12" maxlength="20" />
		<layout:radios cols="2" styleClass="DATAS" key="啟用*" property="PSFOODT0_07" mode="E,E,I" name="Form1Datas" isRequired="true">
			<layout:options collection="Enable_list" property="item_id" labelProperty="item_value" />
		</layout:radios>
		<layout:cell styleClass="DATAS" >
<%--			<layout:select styleClass="DATAS" key="Old Options" name="Form1Datas" property="PSFOODT0_06" mode="E,E,I" layout="false" >--%>
<%--				<layout:options collection="listPSFOODT0_06" property="item_id" labelProperty="item_value" />--%>
<%--			</layout:select>--%>
<%--			<sp:emscategorypopup classkey="EMS" target_select="PSFOODT0_06" value="測試用" />--%>
		</layout:cell>
	</layout:grid>
	
<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		EHF300000M0F ehf300000m0f =(EHF300000M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf300000m0f.getPSFOODT0_LIST();
		String strTmp = "";
	%>
	
	<%
		//建立頁次session 
		//if(session.getAttribute("Pageid")==null)
			session.setAttribute("Pageid","0");
	%>

<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%"  maxPageItems="15">
<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="PSFOODT0_LIST"  id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
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
				EHF300000M0F FORM=(EHF300000M0F)list.get(i);
				strTmp = FORM.getPSFOODT0_01();					
				item_index++;
		%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
					
		<%
			}
		%>
		</center>
		</layout:collectionItem>
		<layout:collectionItem property="PSFOODT0_01" style="TEXT-ALIGN: CENTER" title="食物類別代碼"  />
		<layout:collectionItem property="PSFOODT0_04" style="TEXT-ALIGN: CENTER" title="食物類別名稱" />
		<layout:collectionItem property="PSFOODT0_07_TXT" style="TEXT-ALIGN: CENTER" title="啟用" />
		<layout:collectionItem property="PSFOODT0_05" style="TEXT-ALIGN: CENTER" title="食物類別說明" />
		<%-- <layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除"
						       action="EHF300000M0.do?reqCode=delForm"
							   paramId="PSFOODT0_01" paramProperty="PSFOODT0_01" 
							   onclick=" return confirmShowEMSWait('您確定要刪除資料?'); " >
			刪除				
		</layout:collectionItem> --%>
	</layout:collection>
	</layout:pager>
	</logic:equal>
</layout:form>