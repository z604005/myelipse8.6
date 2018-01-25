<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<jsp:include page="/templates/begin.jsp"></jsp:include>


<!-- JavaScript -->
<script type="text/javascript">

function chkApplyDeptID(){
	if(document.getElementById("EHF030500T0_03").value == "" || document.getElementById("EHF030500T0_03").value == null ){
		alert("請先選擇員工的部門!!");
		return false;
	}else{
		return true;
	}
}

function chkEHF030500T0_06(){
	//放扣款類別
	if(document.getElementById("EHF030500T0_06").value == "01"){
		document.getElementById("flag_EHF030500T0_08").style.display = "";
		document.getElementById("flag_EHF030500T0_10").style.display = "none";
	}else if(document.getElementById("EHF030500T0_06").value == "02"){
		document.getElementById("flag_EHF030500T0_08").style.display = "none";
		document.getElementById("flag_EHF030500T0_10").style.display = "";
	}else{
		document.getElementById("flag_EHF030500T0_08").style.display = "none";
		document.getElementById("flag_EHF030500T0_10").style.display = "none";
	}
}

window.onload = function (evt){
	
	if(document.getElementById("chk_type").value == "yes"){
		chkEHF030500T0_06();
	}
		
}

$(document).ready(function(){
	$('[name="EHF030500T0_03"]').bind('change', function(){ $('[name="EHF030500T0_02"]').val("") });
});



function cls2(){
	document.getElementById("EHF030500T0_02_UID").value = "";
	document.getElementById("EHF030500T0_02").value = "";
	document.getElementById("EHF030500T0_02_TXT").value = "";

	return true;
}
</script>

<layout:form action="EHF030500M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="其他補扣款">
<input type="hidden" id="dataChanged" name="dataChanged" value="" />	
<input type="hidden" id="chk_type" value="${chk_type}" />
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="儲存" mode="D,H,H" name="btnimage?text=button.save&type=t"  reqCode="addDataForm" property="EHF030500M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveData" property="EHF030500M1" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF030500M1"  onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF030500M1" ></layout:image>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_01}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="其他費用序號" property="EHF030500T0_01" styleId="EHF030500T0_01" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<layout:text styleClass="DATAS" property="EHF030500T0_U" styleId="EHF030500T0_U" key="其他費用UID" size="18" maxlength="20" name="Form1Datas"  mode="H,H,H" />
		
		
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,I"  tooltip="◎薪資年月" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF030500T0_05" 
					 key="薪資年月" name="Form1Datas" styleClass="DATAS"  isRequired="true" onkeydown="nextFiled()" />
		
		<layout:select key="薪資計算處理狀態" name="Form1Datas" property="EHF030500T0_SCP" styleId="EHF030500T0_SCP" styleClass="DATAS" mode="I,I,I" >
				<layout:options collection="listEHF030500T0_SCP" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:text styleClass="DATAS" key="部門系統代碼" property="EHF030500T0_03_UID" styleId="EHF030500T0_03_UID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF030500T0_03" styleId="EHF030500T0_03" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF030500M0F" 
							id="EHF030500T0_03_UID,EHF030500T0_03_TXT,EHF030500T0_03" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
							beforerun="cls2()"
							/>
				<layout:text layout="false" property="EHF030500T0_03_TXT" styleId="EHF030500T0_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
		<layout:text styleClass="DATAS" key="員工系統代碼" property="EHF030500T0_02_UID" styleId="EHF030500T0_02_UID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF030500T0_02" styleId="EHF030500T0_02" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF030500M0F" 
							id="EHF030500T0_02_UID,EHF030500T0_02,EHF030500T0_02_TXT" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EHF030500M0F.EHF030500T0_03_UID.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "   
							beforerun="chkApplyDeptID()"	/>	
									
				<layout:text layout="false" property="EHF030500T0_02_TXT" styleId="EHF030500T0_02_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
	<%--	
		<layout:select key="員工類別" name="Form1Datas" property="EHF030500T0_04" styleClass="DATAS" mode="E,E,I" >
				<layout:options collection="listEHF030500T0_04" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:select key="發放類別" name="Form1Datas" property="EHF030500T0_07" styleClass="DATAS" mode="E,E,I" >
				<layout:options collection="listEHF030500T0_07" property="item_id" labelProperty="item_value" />
		</layout:select>
		--%>
	</layout:grid>
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:select key="放扣款類別" name="Form1Datas" property="EHF030500T0_06" styleId="EHF030500T0_06" styleClass="DATAS" mode="E,E,I" onchange="return chkEHF030500T0_06();" >
				<layout:options collection="listEHF030500T0_06" property="item_id" labelProperty="item_value" />
				
				<layout:cell styleClass="DATAS" >
				
				<span id="flag_EHF030500T0_08" style="display:none;" >
					&nbsp;&nbsp;放款項目:&nbsp;&nbsp;
					<layout:text styleClass="DATAS" key="放款項目" property="EHF030500T0_08" styleId="EHF030500T0_08" layout="false" size="10" maxlength="12" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;&nbsp;
					<layout:number styleClass="DATAS" key="放款金額" property="EHF030500T0_09" styleId="EHF030500T0_09" layout="false" size="6" maxlength="6" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;元
				</span>
				<span id="flag_EHF030500T0_10" style="display:none;" >
					&nbsp;&nbsp;扣款項目:&nbsp;&nbsp;
					<layout:text styleClass="DATAS" key="扣款項目" property="EHF030500T0_10" styleId="EHF030500T0_10" layout="false" size="10" maxlength="12" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;&nbsp;
					<layout:number styleClass="DATAS" key="扣款金額" property="EHF030500T0_11" styleId="EHF030500T0_11" layout="false" size="6" maxlength="6" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;元
				</span>
				
				</layout:cell>
				
		</layout:select>
	</layout:grid>
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text styleClass="DATAS" property="EHF030500T0_13" styleId="EHF030500T0_13" key="備註" size="100" maxlength="50" name="Form1Datas"  mode="E,E,I" />
	</layout:grid>
	
	
	
	<logic:equal name="collection" value="show">	
	
	</logic:equal>
	
	<logic:equal name="button" value="query">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" 	property="USER_CREATE" 	styleId="USER_CREATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" 	property="USER_UPDATE" 	styleId="USER_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" 	styleClass="LOGDATA" 	property="VERSION" 		styleId="VERSION" 		name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" 	property="DATE_UPDATE" 	styleId="DATE_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>

</layout:form>



