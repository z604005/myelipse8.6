<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.com.forms.EHF000300M0F" %>
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

</script>

<layout:form action="EHF000300M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="職務基本資料">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF000300M1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF000300M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF000300M1" ></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF000300M1" ></layout:image>
    </layout:row>

	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:text styleClass="DATAS" property="EHF000300T0_01" styleId="EHF000300T0_01" size="16" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="職務代碼" property="EHF000300T0_02" styleId="EHF000300T0_02" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="職務名稱" property="EHF000300T0_03" styleId="EHF000300T0_03" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" />
		<layout:radios styleClass="DATAS" key="使用狀況" property="EHF000300T0_05_TXT" mode="E,E,I" name="Form1Datas" cols="2" >
			<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
		</layout:radios>
		<layout:space styleClass="DATAS"></layout:space>
		<layout:text styleClass="DATAS" key="" property="HR_CompanySysNo" styleId="HR_CompanySysNo" mode="H,H,H" name="Form1Datas" />
		
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
	
		<layout:text styleClass="DATAS" key="備註" property="EHF000300T0_06" styleId="EHF000300T0_06" size="50" mode="E,E,I" maxlength="50" name="Form1Datas" />
		
	</layout:grid>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="最後異動日期" styleClass="LOGDATA" property="HR_LastUpdateDate" name="Form1Datas" mode= "I,I,I" />
	</layout:grid>

</layout:form>