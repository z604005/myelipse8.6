<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.com.forms.EHF000600M0F" %>
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
	EHF000600M0F.elements['reqCode'].value=reqCode;
	EHF000600M0F.submit();
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
	var form_status = document.getElementById("EHF000600T0_16").value;
//	alert("Form_status==>"+form_status);
	
	if(form_status == "0001" || form_status == "0005" || form_status == "0008" || form_status == ""){
		if(document.getElementById("emsbtn_EHF000600T0_07") != null){
			document.getElementById("emsbtn_EHF000600T0_07").disabled = false;
		}
		if(document.getElementById("emsbtn_EHF000600T0_03") != null){
			document.getElementById("emsbtn_EHF000600T0_03").disabled = false;
		}
		if(document.getElementById("emsbtn_EHF000600T0_04") != null){
			document.getElementById("emsbtn_EHF000600T0_04").disabled = false;
		}
	}else{
		if(document.getElementById("emsbtn_EHF000600T0_07") != null){
			document.getElementById("emsbtn_EHF000600T0_07").disabled = true;
		}
		if(document.getElementById("emsbtn_EHF000600T0_03") != null){
			document.getElementById("emsbtn_EHF000600T0_03").disabled = true;
		}
		if(document.getElementById("emsbtn_EHF000600T0_04") != null){
			document.getElementById("emsbtn_EHF000600T0_04").disabled = true;
		}
		
		
		
		
		
		
		
	}

	var upload_status = document.getElementById("uploadflag").value;
	
	if( upload_status == "open" ){
		setCondition();
	}
	
}
function setTime(){
	//document.getElementById("EHF000600T0_10").value = document.getElementById("EHF000600T0_09").value;
	 $("input[name='EHF000600T0_10']").val($("input[name='EHF000600T0_09']").val());
	return true;
}
function setEmergencyAction(Flow_action){
	document.getElementById("emergency_flow_action").value = Flow_action;
}
</script>

<layout:form action="EHF000600M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="「堯堉」員工守則">

	<input type="hidden" name="emergency_flow_action" id="emergency_flow_action" value=""/>
	<input type="hidden" name="FLOW_ACTION" id="FLOW_ACTION" value=""/>

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<logic:lessEqual name="Form1Datas" property="EHF000600T0_16" value="0001">
				<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF000600M1" ></layout:image>
				<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF000600M1" ></layout:image>
			</logic:lessEqual>
			<logic:equal name="personself" value="yes">
				<logic:equal name="Form1Datas" property="EHF000600T0_16" value="0008">
					<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF000600M1" ></layout:image>
				</logic:equal>
				<logic:equal name="Form1Datas" property="EHF000600T0_16" value="0005">
					<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF000600M1" ></layout:image>
				</logic:equal>
			</logic:equal>
		</logic:notEqual>
		
			
			
			
			
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF000600M1" ></layout:image>
    </layout:row>
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:row >
	
	</layout:row>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" tooltip="規則編號" key="規則編號" property="EHF000600T0_01" styleId="EHF000600T0_01" size="20" mode="I,I,I" maxlength="20" name="Form1Datas" >
		</layout:text>
				<layout:text styleClass="DATAS" key="規則名稱" property="EHF000600T0_02" styleId="EHF000600T0_02" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" />
	
		

<%--		--%>
<%--		<layout:cell styleClass="DATAS" >--%>
<%--			<layout:button value="上傳檔案" onclick="return setCondition();" mode="D,D,D" ></layout:button>--%>
<%--		</layout:cell>--%>
		
	</layout:grid>
	
	
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:textarea styleClass="DATAS" key="規則內容簡述" property="EHF000600T0_03" styleId="EHF000600T0_03" rows="3" cols="100" size="100" mode="E,E,I" maxlength="100" name="Form1Datas" />
<%--		<layout:file styleClass="DATAS" property="EHF000600T0_04" key="附加檔案" name="Form1Datas"  mode="E,E,I" />--%>

		<logic:notEqual name="Form1Datas" property="EHF000600T0_02" value="">
			<layout:field styleClass="DATAS" property="EHF000600T0_04" key="附加檔案" name="Form1Datas" access="READONLY" >
				<logic:notEqual name="Attached_File" value="yes" >
					<layout:button value="上傳檔案" onclick="openStrutsLayoutPopup('fileupload'); return false;" mode="D,D,H" ></layout:button>
				</logic:notEqual>
				<logic:equal name="Attached_File" value="yes">
					<layout:row styleClass="DATAS" >
						<layout:link layout="true" styleClass="DATAS" action="" onclick="fbutton('getAttachedFile'); return false;" >
   		 					 <layout:message styleClass="DATAS" key="${Attached_File_Name}" />
   		 				</layout:link>
   		 				<layout:link layout="true" styleClass="DATAS" action="" mode="D,D,H" onclick="chkdelfile(); return false;" >
   		 					<layout:img styleClass="DATAS" alt="刪除附加檔案" srcName="deleteicon.jpg" />
   		 				</layout:link>
   		 			</layout:row>
<%--   		 			action="EHF000600M1.do?reqCode=getAttachedFile"--%>
   		 		</logic:equal>
			</layout:field>
		</logic:notEqual>
	</layout:grid>
	
	
	<logic:equal name="collection" value="show">
		<logic:notEqual name="Form1Datas" property="EHF000600T0_01" value="">
			<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
				<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" styleId="VERSION" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
			</layout:grid>
		</logic:notEqual>
	</logic:equal>
	

	
	<logic:lessEqual name="Form1Datas" property="EHF000600T0_16" value="0001">
		<layout:popup styleId="fileupload" styleClass="DATAS" key="附加檔案上傳">
			<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="uploadEHF000600M1" ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileupload');" />
				</layout:row>
		</layout:popup>
	</logic:lessEqual>
	
</layout:form>