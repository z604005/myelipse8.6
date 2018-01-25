<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF300200M0F" %>
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

/*
function chkApplicantID(){
	if(document.getElementById("EHF000600T0_03").value == "" || document.getElementById("EHF000600T0_03").value == null ){
		alert("請先選擇申請人!!");
		return false;
	}else{
		return true;
	}
}

function chkApplyDeptID(){
	if(document.getElementById("EHF000600T0_04").value == "" || document.getElementById("EHF000600T0_04").value == null ){
		alert("請先選擇申請人的部門!!");
		return false;
	}else{
		return true;
	}
}

function setCondition(){
	
	var cond = document.getElementById("condition01");
	var source = document.getElementById("EHF000600T0_01");
	cond.value = source.value;
	
//	alert("假單條件condition01==>"+cond.value);
	
	return openStrutsLayoutPopup('fileupload');
	
}
*/

function saveBeforeUploadFile(){
	
	//啟動讀取動畫
	showEMSWait();
	
	return fbutton('saveBeforeUploadFile');
	
}

function fbutton(reqCode) {
	EHF300200M0F.elements['reqCode'].value=reqCode;
	EHF300200M0F.submit();
}	

function getSelectBox(){
	
	//啟動讀取動畫
	showEMSWait();
	
	return fbutton('changeSelectBox');

}

function setAction(Flow_action){
	document.getElementById("FLOW_ACTION").value = Flow_action;
//	alert("flow_action==>"+document.getElementById("FLOW_ACTION").value);
	return openStrutsLayoutPopup('comment');
}

function setFinishAction(Flow_action, finish_value){
	document.getElementById("FLOW_FINISHACTION").value = finish_value;
	return setAction(Flow_action);
}

function setSubmit(){
	
	if(true){
		document.getElementById("FLOW_ACTION").value = "0001";
	}else{
		return false;
	}
//	alert("flow_action==>"+document.getElementById("FLOW_ACTION").value);
//	return openStrutsLayoutPopup('comment');
}

function chkdelfile(){
	
	if(confirm('您確定要刪除附加檔案嗎?')){
		//啟動讀取動畫
		showEMSWait();
		fbutton('delAttachedFile'); 
	}
	
	return false;
}


window.onload = function chkbutton(){
	var form_status = document.getElementById("EHF300200T0_16").value;
//	alert("Form_status==>"+form_status);
	
	if(form_status == "0001" || form_status == "0005" || form_status == "0008" || form_status == ""){
		if(document.getElementById("emsbtn_EHF300200T0_07") != null){
			document.getElementById("emsbtn_EHF300200T0_07").disabled = false;
		}
		if(document.getElementById("emsbtn_EHF300200T0_03") != null){
			document.getElementById("emsbtn_EHF300200T0_03").disabled = false;
		}
		if(document.getElementById("emsbtn_EHF300200T0_04") != null){
			document.getElementById("emsbtn_EHF300200T0_04").disabled = false;
		}
	}else{
		if(document.getElementById("emsbtn_EHF300200T0_07") != null){
			document.getElementById("emsbtn_EHF300200T0_07").disabled = true;
		}
		if(document.getElementById("emsbtn_EHF300200T0_03") != null){
			document.getElementById("emsbtn_EHF300200T0_03").disabled = true;
		}
		if(document.getElementById("emsbtn_EHF300200T0_04") != null){
			document.getElementById("emsbtn_EHF300200T0_04").disabled = true;
		}
		
		
		
		
		
		
		
	}

	var upload_status = document.getElementById("uploadflag").value;
	
	if( upload_status == "open" ){
		setCondition();
	}
	
}
function setTime(){
	//document.getElementById("EHF000600T0_10").value = document.getElementById("EHF000600T0_09").value;
	 $("input[name='EHF300200T0_10']").val($("input[name='EHF300200T0_09']").val());
	return true;
}
function setEmergencyAction(Flow_action){
	document.getElementById("emergency_flow_action").value = Flow_action;
}
</script>

<layout:form action="EHF300200M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="醫院資料設定">

	<layout:row>
		<logic:notEqual name="button" value="edit">
				<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF300200M1" ></layout:image>
				<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF300200M1" ></layout:image>
			</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF300100M1" ></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF300200M1" ></layout:image>
    </layout:row>
        
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>

	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
	<layout:number styleClass="DATAS" tooltip="醫院代碼" key="醫院代碼" property="EHF300200T0_01" styleId="EHF300200T0_01" size="20" mode="H,H,H" maxlength="20" name="Form1Datas" >
	</layout:number>
	<layout:text styleClass="DATAS" tooltip="醫院名稱" key="*醫院名稱" property="EHF300200T0_02" styleId="EHF300200T0_02" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
	</layout:text>
	<layout:radios styleClass="DATAS" key="*啟用"  tooltip="啟用"property="EHF300200T0_05" mode="E,E,I" name="Form1Datas" cols="2"  isRequired="false" >
		<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
	</layout:radios>
	<layout:number styleClass="DATAS" tooltip="顯示順序" key="*顯示順序" property="EHF300200T0_04" styleId="EHF300200T0_04" size="5" mode="E,E,I" maxlength="5" name="Form1Datas" >
	</layout:number>
<%--	<layout:text styleClass="DATAS" tooltip="路線名稱" key="*路線名稱" property="EHF300200T0_03" styleId="EHF300200T0_03" size="10" mode="E,E,I" maxlength="10" name="Form1Datas" >--%>
<%--	</layout:text>--%>
	<layout:space styleClass="DATAS" />
	<layout:number styleClass="DATAS" tooltip="醫院代碼" key="醫院代碼" property="EHF300200T0_06" styleId="EHF300200T0_06" size="20" mode="H,H,H" maxlength="20" name="Form1Datas" >
	</layout:number>

	</layout:grid>
	
	<logic:equal name="collection" value="show">
		<logic:notEqual name="Form1Datas" property="EHF300200T0_01" value="">
			<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
				<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" styleId="VERSION" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
			</layout:grid>
		</logic:notEqual>
	</logic:equal>
	
</layout:form>