<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.salary.forms.EHF030108M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<script>
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

<layout:form action="EHF030108M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="全勤獎金扣費規則設定">
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF03010M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="addDataForm" property="EHF030108M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
<%--			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=儲存&type=t"   reqCode="saveDataForm" property="EHF030108M0" ></layout:image>--%>
<%--			<layout:image alt="回查詢畫面" mode="D,D,D" name="btnimage?text=回查詢畫面&type=t" reqCode="init" property="EHF030108M0" ></layout:image>--%>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t"    reqCode="editDataForm" property="EHF030108M0" ></layout:image>
<%--			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=刪除&type=t"  reqCode="delForm" property="EHF030108M0"  confirm="您確定要刪除資料嗎?" ></layout:image>--%>
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:text styleClass="DATAS" key="全勤獎金扣費規則代碼" property="EHF030108T0_02" styleId="EHF030108T0_02" size="16" mode="E,E,I" maxlength="20" name="Form1Datas" />
					 
		<layout:text styleClass="DATAS" key="全勤獎金扣費規則名稱" property="EHF030108T0_03" styleId="EHF030108T0_03" size="16" mode="E,E,I" maxlength="20" name="Form1Datas" />
		
<%--		<layout:cell styleClass="DATAS" />--%>
		
		<logic:notEqual name="button" value="edit" >
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
		</logic:equal>
		
	</layout:grid>

	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF030108M0F ehf030108m0f =(EHF030108M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf030108m0f.getEHF030108T0_LIST();
		String strTmp = "";
		
	%>
	
	<layout:collection emptyKey="沒有資料列"  name="Form1Datas" property="EHF030108T0_LIST" 
					   id="bean1" indexId="index"  selectId="" selectProperty="" selectName=""  
					   selectType=""  width="100%" height=""   styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
		<layout:collectionItem name="bean1" title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				Map FORM = (Map) list.get(item_index);
				strTmp = ((String) FORM.get("EHF030108T0_01"))+"";
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
			<%
			}
			%>
			</center>
		</layout:collectionItem> 
		
		<layout:collectionItem property="EHF030108T0_02" style="TEXT-ALIGN: CENTER" title="全勤獎金扣費規則代號" />
		<layout:collectionItem property="EHF030108T0_03" style="TEXT-ALIGN: CENTER" title="全勤獎金扣費規則名稱" />
		<layout:collectionItem property="EHF030108T0_12" style="TEXT-ALIGN: CENTER" title="是否啟用" />
		<layout:collectionItem title="刪除" style="text-align: center" paramId="EHF030108T0_02" paramProperty="EHF030108T0_02" url="EHF030108M1.do?reqCode=delForm" 
		                       onclick="return confirmShowEMSWait('您確定要刪除資料嗎?');" >刪除</layout:collectionItem>

	</layout:collection>
	
	</logic:equal>

</layout:form>