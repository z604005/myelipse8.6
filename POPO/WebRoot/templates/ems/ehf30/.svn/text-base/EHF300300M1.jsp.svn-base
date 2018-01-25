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
function fbutton(reqCode) {
	EHF300300M0F.elements['reqCode'].value=reqCode;
	EHF300300M0F.submit();
}	

function chkdelfile(){
	
	if(confirm('您確定要刪除檔案嗎?')){
		//啟動讀取動畫
		showEMSWait();
		fbutton('delAttachedFile'); 
	}
	return false;
}
</script>

<layout:form action="EHF300300M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="早中晚餐定價">

	<layout:row>

			<layout:image alt="確認新增" mode="D,H,H" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF300300M1" policy="all" ></layout:image>
<%--			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF010100M1" ></layout:image>--%>
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF300300M1" policy="all"></layout:image>		
			
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF300300M1" policy="all" ></layout:image>
 
		 </layout:row>
    
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<%----------------------------------------------------------------------------------------------------------------------------------------------------------------------------%>

<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
				<layout:text styleClass="DATAS" key="定價代碼"property="EHF300300T0_01" size="10"mode="E,I,I" maxlength="10"name="Form1Datas" ></layout:text>
				<layout:text styleClass="DATAS" key="餐點名稱" property="EHF300300T0_02_TXT" styleId="EHF300300T0_02_TXT" size="10" mode="E,E,I" maxlength="10" name="Form1Datas" />
				<layout:number styleClass="DATAS" key="定價" property="EHF300300T0_03" styleId="EHF300300T0_03" size="10" mode="E,E,I" maxlength="5" name="Form1Datas"/>
			<layout:space styleClass="DATAS"/>
</layout:grid>

<%------------------------------------------------------------------------------------------------------------------------------------------------------------------------%>
<layout:notMode value="create" >

<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
			
<%-------------------------------------------餐點類別-------------------------------------------------------------------------------------------------------------------------%>
<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">		
<layout:select key="餐點類別" name="Form1Datas" property="EHF300300T1_03" styleClass="DATAS"mode="E,E,I" >
<layout:options collection="listEHF300300T1_03" property="item_id" labelProperty="item_value" />
</layout:select>
<%-------------------------------------------新增明細-------------------------------------------------------------------------------------------------------------------------%>
<layout:cell styleClass="DATAS11" >
				<layout:submit mode="N,D,N" value="新增代碼明細" reqCode="addDetailDataForm" />
</layout:cell>
</layout:grid>
				
<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF300300_DETAIL" selectId="EHF300300_DETAIL" selectProperty="" selectName=""  
			  width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF300300T1_03_TXT" title="餐點名稱" style="text-align: center" />
			<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
			paramId="EHF300300T1_01,EHF300300T1_02"
			paramProperty="EHF300300T1_01,EHF300300T1_02"
			url="EHF300300M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF300300T1" 
			onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>					
</layout:collection>					
</layout:notMode>		
</layout:form>