<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020200M0F" %>
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


function chkApplicantID(){
	if(document.getElementById("EHF020200T0_03").value == "" || document.getElementById("EHF020200T0_03").value == null ){
		alert("請先選擇申請人!!");
		return false;
	}else{
		return true;
	}
}

function chkApplyDeptID(){
	if(document.getElementById("EHF020200T0_04").value == "" || document.getElementById("EHF020200T0_04").value == null ){
		alert("請先選擇申請人的部門!!");
		return false;
	}else{
		return true;
	}
}

function setCondition(){
	
	var cond = document.getElementById("condition01");
	var source = document.getElementById("EHF020200T0_01");
	cond.value = source.value;
	
//	alert("假單條件condition01==>"+cond.value);
	
	return openStrutsLayoutPopup('fileupload');
	
}

function saveBeforeUploadFile(){
	
	//啟動讀取動畫
	showEMSWait();
	
	return fbutton('saveBeforeUploadFile');
	
}

function fbutton(reqCode) {
	EHF020200M0F.elements['reqCode'].value=reqCode;
	EHF020200M0F.submit();
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
	var form_status = document.getElementById("EHF020200T0_16").value;
//	alert("Form_status==>"+form_status);
	
	if(form_status == "0001" || form_status == "0005" || form_status == "0008" || form_status == ""){
		if(document.getElementById("emsbtn_EHF020200T0_07") != null){
			document.getElementById("emsbtn_EHF020200T0_07").disabled = false;
		}
		if(document.getElementById("emsbtn_EHF020200T0_03") != null){
			document.getElementById("emsbtn_EHF020200T0_03").disabled = false;
		}
		if(document.getElementById("emsbtn_EHF020200T0_04") != null){
			document.getElementById("emsbtn_EHF020200T0_04").disabled = false;
		}
	}else{
		if(document.getElementById("emsbtn_EHF020200T0_07") != null){
			document.getElementById("emsbtn_EHF020200T0_07").disabled = true;
		}
		if(document.getElementById("emsbtn_EHF020200T0_03") != null){
			document.getElementById("emsbtn_EHF020200T0_03").disabled = true;
		}
		if(document.getElementById("emsbtn_EHF020200T0_04") != null){
			document.getElementById("emsbtn_EHF020200T0_04").disabled = true;
		}
	}

	var upload_status = document.getElementById("uploadflag").value;
	
	if( upload_status == "open" ){
		setCondition();
	}
	
}
function setTime(){
	//document.getElementById("EHF020200T0_10").value = document.getElementById("EHF020200T0_09").value;
	 $("input[name='EHF020200T0_10']").val($("input[name='EHF020200T0_09']").val());
	return true;
}
function setEmergencyAction(Flow_action){
	document.getElementById("emergency_flow_action").value = Flow_action;
}
</script>

<layout:form action="EHF020200M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="請假單申請">

	<input type="hidden" name="emergency_flow_action" id="emergency_flow_action" value=""/>
	<input type="hidden" name="FLOW_ACTION" id="FLOW_ACTION" value=""/>

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<logic:lessEqual name="Form1Datas" property="EHF020200T0_16" value="0001">
				<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF020200M1" ></layout:image>
				<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF020200M1" ></layout:image>
				
				<layout:image alt="確認" mode="H,D,H" name="btnimage?text=button.confirmData&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0002');" confirm="您確定要確認請假資料嗎?"></layout:image>
<%--			<layout:image alt="回復確認" mode="H,H,D" name="btnimage?text=button.revertData&type=t" reqCode="recovery" property="EHF020200M1" confirm="您確定要還原請假資料嗎?" ></layout:image> --%>
				
			</logic:lessEqual>
			<logic:equal name="personself" value="yes">
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0008">
					<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF020200M1" ></layout:image>
				</logic:equal>
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0005">
					<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF020200M1" ></layout:image>
				</logic:equal>
			</logic:equal>
		</logic:notEqual>
		<logic:equal name="button" value="query">
<%--堯堉不須看到呈核流程相關按鈕  只需完成即可		
			<logic:equal name="emergency_flow" value="Y">
				<logic:lessEqual name="Form1Datas" property="EHF020200T0_16" value="0001">
					<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  reqCode="submitForm" property="EHF020200M1" ></layout:image>
				</logic:lessEqual>
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0008">
					<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  reqCode="submitForm" property="EHF020200M1" ></layout:image>
				</logic:equal>
			</logic:equal>
--%>			
			<logic:notEqual name="emergency_flow" value="Y">
			<logic:equal name="personself" value="yes">
				<logic:lessEqual name="Form1Datas" property="EHF020200T0_16" value="0001">
					<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  reqCode="submitForm" property="EHF020200M1" ></layout:image>
				</logic:lessEqual>
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0005">
					<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  reqCode="submitForm" property="EHF020200M1" ></layout:image>
				</logic:equal>
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0008">
					<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  reqCode="submitForm" property="EHF020200M1" ></layout:image>
				</logic:equal>
<%--				<logic:equal name="flow_manager" value="yes">--%>
<%--					<layout:image alt="自動簽核" mode="H,D,H" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020200M1" ></layout:image>--%>
<%--				</logic:equal>--%>
				<logic:equal name="flow_hr" value="yes">
					<layout:image alt="自動簽核" mode="H,D,H" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020200M1" ></layout:image>
				</logic:equal>
				<logic:equal name="flow_se" value="yes">
					<layout:image alt="自動簽核" mode="H,D,H" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020200M1" ></layout:image>
				</logic:equal>
			</logic:equal>
			</logic:notEqual>
						
			<logic:equal name="emergency_flow" value="Y">
			
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0001">
					<layout:image alt="核准" mode="H,H,D" name="btnimage?text=button.approval&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0002');" ></layout:image>
				</logic:equal>
				
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0002">
					<%--		是否為申請者本人			--%>
					<logic:equal name="personself" value="yes">
						<layout:image alt="抽單" mode="H,D,D" name="btnimage?text=button.withdrawal&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0013');" ></layout:image>					
					</logic:equal>
					<%--		END_是否為申請者本人			--%>
					<logic:equal name="person_manager" value="yes" >
						<layout:image alt="核准" mode="H,H,D" name="btnimage?text=button.approval&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0002');" ></layout:image>														
						<layout:image alt="駁回" mode="H,H,D" name="btnimage?text=button.reject&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0003');" ></layout:image>
					</logic:equal>
					<logic:equal name="boss_manager" value="yes" >
						<layout:image alt="核准" mode="H,H,D" name="btnimage?text=button.approval&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0002');" ></layout:image>														
						<layout:image alt="駁回" mode="H,H,D" name="btnimage?text=button.reject&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0003');" ></layout:image>
					</logic:equal>
				</logic:equal>
				
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0006">
					<logic:equal name="person_manager" value="yes" >
						<layout:image alt="作廢" mode="H,D,D" name="btnimage?text=button.invalid&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0009');" ></layout:image>
					</logic:equal>
					<logic:equal name="boss_manager" value="yes" >
						<layout:image alt="作廢" mode="H,D,D" name="btnimage?text=button.invalid&type=t" reqCode="signFormEmergency" property="EHF020200M1" onclick="setEmergencyAction('0009');" ></layout:image>
					</logic:equal>
				</logic:equal>
			</logic:equal>
			
			<logic:notEqual name="emergency_flow" value="Y">
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0002">
					<%--		Start_是否為申請者本人			--%>
					<logic:equal name="personself" value="yes">
						<layout:image alt="抽單" mode="H,D,D" name="btnimage?text=button.withdrawal&type=t" reqCode="cancel" property="EHF020200M1" onclick="setAction('0008'); return false;" ></layout:image>					
					</logic:equal>
					<%--		END_是否為申請者本人			--%>
					<logic:equal name="sign" value="yes" >
						<layout:image alt="核准" mode="H,H,D" name="btnimage?text=button.approval&type=t" reqCode="approve" property="EHF020200M1" onclick="setAction('0004'); return false;" ></layout:image>														
						<layout:image alt="駁回" mode="H,H,D" name="btnimage?text=button.reject&type=t" reqCode="reject" property="EHF020200M1" onclick="setAction('0005'); return false;" ></layout:image>
						<logic:equal name="last_sign" value="yes" >
							<layout:image alt="自動簽核" mode="H,H,D" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020200M1" ></layout:image>
						</logic:equal>
					</logic:equal>
				</logic:equal>
				<logic:equal name="Form1Datas" property="EHF020200T0_16" value="0004">
					<%--		Start_是否為申請者本人			--%>
					<logic:equal name="personself" value="yes">
						<layout:image alt="抽單" mode="H,D,D" name="btnimage?text=button.withdrawal&type=t" reqCode="cancel" property="EHF020200M1" onclick="setAction('0008'); return false;" ></layout:image>					
					</logic:equal>
					<%--		END_是否為申請者本人			--%>
					<logic:equal name="sign" value="yes" >
						<layout:image alt="核准" mode="H,H,D" name="btnimage?text=button.approval&type=t" reqCode="approve" property="EHF020200M1" onclick="setAction('0004'); return false;" ></layout:image>														
						<layout:image alt="駁回" mode="H,H,D" name="btnimage?text=button.reject&type=t" reqCode="reject" property="EHF020200M1" onclick="setAction('0005'); return false;" ></layout:image>
						<logic:equal name="last_sign" value="yes" >
							<layout:image alt="自動簽核" mode="H,H,D" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020200M1" ></layout:image>
						</logic:equal>	
					</logic:equal>
				</logic:equal>
			</logic:notEqual>
			
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF020200M1" ></layout:image>
		<%--堯堉不須看到呈核流程相關按鈕  只需完成即可	
		<layout:image alt="簽核歷程" mode="H,D,D" name="btnimage?text=button.flow.sign.logs&type=t" reqCode="" property="EHF020200M1" onclick="openStrutsLayoutPopup('formsignlog'); return false;" />--%>
    </layout:row>
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:row >
	
	</layout:row>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="請假單單號" property="EHF020200T0_01" styleId="EHF020200T0_01" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="空表單編號" property="EHF020200T0_02" styleId="EHF020200T0_02" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="表單狀態" property="EHF020200T0_16" styleId="EHF020200T0_16"	size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="表單狀態說明" property="EHF020200T0_16_DESC" styleId="EHF020200T0_16_DESC" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I" size="10" startYear="2010" endYear="2025"  key="填表日期" name="Form1Datas" property="EHF020200T0_08" styleClass="DATAS"  />
<%-- 		<layout:cell styleClass="DATAS" ></layout:cell> --%>
	<layout:text styleClass="DATAS" tooltip="代理人" key="代理人" property="EHF020200T0_07" styleId="EHF020200T0_07" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="代理人" key="代理人" property="EHF020200T0_07_SHOW" styleId="EHF020200T0_07_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020200M0F" 
				id="EHF020200T0_07,EHF020200T0_07_SHOW,EHF020200T0_07_TXT" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 LEFT JOIN EHF010100T1 ON EHF010100T6_01 = EHF010100T1_01"
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
                parentId=""          
                parentField=""       
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' AND EHF010100T1.EHF010100T1_02 in ('1','3') and  EHF010100T1.EHF010100T1_04='0'" 
				beforerun="chkApplyDeptID()"	
				mode="E,E,F"																
			/>
			<%--parentId="EHF010100T6_02"   代理人無須指定和申請人同部門  故上述lov的 parentId & parentField屬性拿掉--%>
			<%--parentField="window.EHF020200M0F.EHF020200T0_04.value"--%>
			<layout:text layout="false" property="EHF020200T0_07_TXT" styleId="EHF020200T0_07_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
	
		<layout:text styleClass="DATAS" tooltip="填單人部門" key="填單人部門" property="EHF020200T0_06" styleId="EHF020200T0_06" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />	
		<layout:text styleClass="DATAS" tooltip="填單人部門" key="填單人部門" property="EHF020200T0_06_SHOW" styleId="EHF020200T0_06_SHOW" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020200T0_06_TXT" styleId="EHF020200T0_06_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="填單人" key="填單人" property="EHF020200T0_05" styleId="EHF020200T0_05" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />		
		<layout:text styleClass="DATAS" tooltip="填單人" key="填單人" property="EHF020200T0_05_SHOW" styleId="EHF020200T0_05_SHOW" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020200T0_05_TXT" styleId="EHF020200T0_05_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<logic:equal name="person_manager" value="yes" >
		
		<%--人資顯示頁面			--%>
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020200T0_04" styleId="EHF020200T0_04" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020200T0_04_SHOW" styleId="EHF020200T0_04_SHOW"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020200M0F" 
				id="EHF020200T0_04,EHF020200T0_04_SHOW,EHF020200T0_04_TXT" 
				lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
				table="EHF000200T0"
				fieldAlias="系統代碼,部門代號,部門名稱" 
				fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
				others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
				mode="E,E,F"
			/>
			<layout:text layout="false" property="EHF020200T0_04_TXT" styleId="EHF020200T0_04_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020200T0_03" styleId="EHF020200T0_03" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020200T0_03_SHOW" styleId="EHF020200T0_03_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020200M0F" 
				id="EHF020200T0_03,EHF020200T0_03_SHOW,EHF020200T0_03_TXT" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 LEFT JOIN EHF010100T1 ON EHF010100T6_01 = EHF010100T1_01"
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
				parentId="EHF010100T6_02" 
				parentField="window.EHF020200M0F.EHF020200T0_04.value" 
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' AND EHF010100T1.EHF010100T1_02 in ('1','3') and  EHF010100T1.EHF010100T1_04='0'" 
				beforerun="chkApplyDeptID()"	
				mode="E,E,F"																
			/>
			<layout:text layout="false" property="EHF020200T0_03_TXT" styleId="EHF020200T0_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<%--人資顯示頁面			--%>
		
		</logic:equal>
		
		<logic:equal name="boss_manager" value="yes" >
		
		<%--老闆顯示頁面			--%>
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020200T0_04" styleId="EHF020200T0_04" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020200T0_04_SHOW" styleId="EHF020200T0_04_SHOW"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020200M0F" 
				id="EHF020200T0_04,EHF020200T0_04_SHOW,EHF020200T0_04_TXT" 
				lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
				table="EHF000200T0"
				fieldAlias="系統代碼,部門代號,部門名稱" 
				fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
				others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
				mode="E,E,F"
			/>
			<layout:text layout="false" property="EHF020200T0_04_TXT" styleId="EHF020200T0_04_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020200T0_03" styleId="EHF020200T0_03" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020200T0_03_SHOW" styleId="EHF020200T0_03_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020200M0F" 
				id="EHF020200T0_03,EHF020200T0_03_SHOW,EHF020200T0_03_TXT" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
				parentId="EHF010100T6_02" 
				parentField="window.EHF020200M0F.EHF020200T0_04.value" 
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
				beforerun="chkApplyDeptID()"	
				mode="E,E,F"																
			/>
			<layout:text layout="false" property="EHF020200T0_03_TXT" styleId="EHF020200T0_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<%--老闆顯示頁面			--%>
		
		</logic:equal>
		
		<logic:equal name="accounting" value="yes">
		
		<%--會計顯示頁面			--%>
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020200T0_04" styleId="EHF020200T0_04" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020200T0_04_SHOW" styleId="EHF020200T0_04_SHOW"  size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020200T0_04_TXT" styleId="EHF020200T0_04_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020200T0_03" styleId="EHF020200T0_03" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020200T0_03_SHOW" styleId="EHF020200T0_03_SHOW" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020200T0_03_TXT" styleId="EHF020200T0_03_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<%--會計顯示頁面			--%>
		
		</logic:equal>
		
		<logic:equal name="person" value="yes">
		
		<%--一般員工顯示頁面			--%>
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020200T0_04" styleId="EHF020200T0_04" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020200T0_04_SHOW" styleId="EHF020200T0_04_SHOW"  size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020200T0_04_TXT" styleId="EHF020200T0_04_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020200T0_03" styleId="EHF020200T0_03" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020200T0_03_SHOW" styleId="EHF020200T0_03_SHOW" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020200T0_03_TXT" styleId="EHF020200T0_03_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<%--一般員工顯示頁面			--%>
		
		</logic:equal>
		
		<layout:select key="假別" name="Form1Datas" property="EHF020200T0_14" styleId="EHF020200T0_14" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="EHF020200T0_14_list" property="item_id" labelProperty="item_value" />
		</layout:select>
			
		<layout:number key="請假天數" size="1" name="Form1Datas" property="EHF020200T0_13_DAY" styleClass="DATAS" mode="I,I,I">
			&nbsp;天&nbsp;
			<layout:number layout="false" size="1" name="Form1Datas" property="EHF020200T0_13_HOUR" styleClass="DATAS" mode="I,I,I" />
			&nbsp;時&nbsp;
<%--			<layout:submit reqCode="changeVacation" property="EHF020200M1" value="計算" mode="I,I,I" ></layout:submit>--%>
		</layout:number>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"   startYear="2010" endYear="2025"  key="請假日期(起)" size="10"
					 name="Form1Datas" property="EHF020200T0_09" styleClass="DATAS"  onchange="setTime()">
			&nbsp;
			<layout:select key="請假時間(起)(時)" name="Form1Datas" property="EHF020200T0_11_HH" styleId="EHF020200T0_11_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF020200T0_11_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;點&nbsp;
			<layout:select key="請假時間(起)(分)" name="Form1Datas" property="EHF020200T0_11_MM" styleId="EHF020200T0_11_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF020200T0_11_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;分
		</layout:date>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="請假日期(迄)" size="10" 
					 name="Form1Datas" property="EHF020200T0_10" styleClass="DATAS"  >
			&nbsp;
			<layout:select key="請假時間(起)(時)" name="Form1Datas" property="EHF020200T0_12_HH" styleId="EHF020200T0_12_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF020200T0_11_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;點&nbsp;
			<layout:select key="請假時間(起)(分)" name="Form1Datas" property="EHF020200T0_12_MM" styleId="EHF020200T0_12_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF020200T0_11_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;分
		</layout:date>
<%--		
		<layout:text styleClass="DATAS" tooltip="代理人" key="代理人" property="EHF020200T0_07" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" >
			<sp:emspopup function="agents" id="EHF020200T0_07,EHF020200T0_07_TXT" empkey="window.EHF020200M1F.EHF020200T0_03.value" 
					 	beforerun="chkApplicantID()" />
			<sp:emspopup function="agents" id="EHF020200T0_07,EHF020200T0_07_TXT" empkey="window.EHF020200M1F.EHF020200T0_03.value" 
					 	 />
			<layout:text layout="false" property="EHF020200T0_07_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
--%>		
<%--		--%>
<%--		<layout:cell styleClass="DATAS" >--%>
<%--			<layout:button value="上傳檔案" onclick="return setCondition();" mode="D,D,D" ></layout:button>--%>
<%--		</layout:cell>--%>
		
	</layout:grid>
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text styleClass="DATAS" property="EHF020200T0_15" styleId="EHF020200T0_15" key="事由" size="100" maxlength="100" name="Form1Datas"  mode="E,E,I" />
<%--		<layout:file styleClass="DATAS" property="EHF020200T0_17" key="附加檔案" name="Form1Datas"  mode="E,E,I" />--%>

		<logic:notEqual name="Form1Datas" property="EHF020200T0_01" value="">
			<layout:field styleClass="DATAS" property="EHF020200T0_17" key="附加檔案" name="Form1Datas" access="READONLY" >
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
<%--   		 			action="EHF020200M1.do?reqCode=getAttachedFile"--%>
   		 		</logic:equal>
			</layout:field>
		</logic:notEqual>
	</layout:grid>
	
	<logic:equal name="button" value="edit">	
	</logic:equal>
	
	<logic:equal name="collection" value="show">
		<logic:notEqual name="Form1Datas" property="EHF020200T0_01" value="">
			<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
				<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" styleId="VERSION" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
			</layout:grid>
		</logic:notEqual>
	</logic:equal>
	
	<layout:popup styleId="comment" styleClass="DATAS" key="簽核意見">
		<layout:text styleClass="DATAS" property="SIGN_COMMENT" key="內容" size="30" maxlength="40" name="Form1Datas"  mode="E,E,E" />
		<layout:row >				
			<layout:submit value="送出" reqCode="signForm" property="EHF020200M1"  ></layout:submit>
			<layout:button value="取消" onclick="closeStrutsLayoutPopup('comment');" />
		</layout:row>
	</layout:popup>
	
	<%--  表單簽核歷程	--%>
	<layout:popup styleId="formsignlog" styleClass="DATAS" key="簽核歷程">
	
		<layout:collection emptyKey="沒有資料列"  name="Form5Datas" selectId="" selectProperty="" selectName=""  
					       width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
			<layout:collectionItem property="SIGN_USER_NAME" style="TEXT-ALIGN: CENTER" title="簽核者" />
			<layout:collectionItem property="SIGN_DATETIME" style="TEXT-ALIGN: CENTER" title="簽核時間" />
			<layout:collectionItem property="SIGN_ACTION_NAME" style="TEXT-ALIGN: CENTER" title="簽核狀態" />
			<layout:collectionItem property="SIGN_COMMENT" style="TEXT-ALIGN: CENTER" title="簽核意見"/>
		</layout:collection>
		<layout:row >
			<layout:button value="關閉" onclick="closeStrutsLayoutPopup('formsignlog');" />
		</layout:row>
				
	</layout:popup>

	<logic:lessEqual name="Form1Datas" property="EHF020200T0_16" value="0001">
		<layout:popup styleId="fileupload" styleClass="DATAS" key="附加檔案上傳">
			<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="uploadEHF020200M1" ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileupload');" />
				</layout:row>
		</layout:popup>
	</logic:lessEqual>
	
</layout:form>