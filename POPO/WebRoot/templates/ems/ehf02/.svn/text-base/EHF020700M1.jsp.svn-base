<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020700M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<!-- JavaScript -->
<script type="text/javascript">


function chkApplicantID(){
	if(document.getElementById("EHF020700T0_03").value == "" || document.getElementById("EHF020700T0_03").value == null ){
		alert("請先選擇申請人!!");
		return false;
	}else{
		return true;
	}
}

function chkApplyDeptID(){
	if(document.getElementById("EHF020700T0_04").value == "" || document.getElementById("EHF020700T0_04").value == null ){
		alert("請先選擇申請人的部門!!");
		return false;
	}else{
		return true;
	}
}


function setCondition(){
	
	var cond = document.getElementById("condition01");
	var source = document.getElementById("EHF020700T0_01");
	cond.value = source.value;
	
//	alert("公務單條件condition01==>"+cond.value);
	
	return openStrutsLayoutPopup('fileupload');
	
}

function saveBeforeUploadFile(){
	
	//啟動讀取動畫
	showEMSWait();
	
	return fbutton('saveBeforeUploadFile');
	
}

function fbutton(reqCode) {
				EHF020700M0F.elements['reqCode'].value=reqCode;
				EHF020700M0F.elements['confirmb'].value='';
				EHF020700M0F.submit();
		}	

function getSelectBox(){
	
//	return fbutton('changeSelectBox');

}

function setAction(Flow_action){
	document.getElementById("FLOW_ACTION").value = Flow_action;
//	alert("flow_action==>"+document.getElementById("FLOW_ACTION").value);
	return openStrutsLayoutPopup('comment2');
}

function setSubmit(){
	document.getElementById("FLOW_ACTION").value = "0001";
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

<%--window.onload = function chkbutton(){--%>
<%--	--%>
<%--	var form_status = document.getElementById("EHF020700T0_14").value;--%>
<%--//	alert("Form_status==>"+form_status);--%>
<%--	--%>
<%--	if(form_status == "0001" || form_status == "0005" || form_status == "0008" || form_status == ""){--%>
<%----%>
<%--		if(document.getElementById("emsbtn_EHF020700T0_03") != null){--%>
<%--			document.getElementById("emsbtn_EHF020700T0_03").disabled = false;--%>
<%--		}--%>
<%--		if(document.getElementById("emsbtn_EHF020700T0_04") != null){--%>
<%--			document.getElementById("emsbtn_EHF020700T0_04").disabled = false;--%>
<%--		}--%>
<%--	}else{--%>
<%----%>
<%--		if(document.getElementById("emsbtn_EHF020700T0_03") != null){--%>
<%--			document.getElementById("emsbtn_EHF020700T0_03").disabled = true;--%>
<%--		}--%>
<%--		if(document.getElementById("emsbtn_EHF020700T0_04") != null){--%>
<%--			document.getElementById("emsbtn_EHF020700T0_04").disabled = true;--%>
<%--		}--%>
<%--	}--%>
<%--	--%>
<%--	var upload_status = document.getElementById("uploadflag").value;--%>
<%--	--%>
<%--	if( upload_status == "open" ){--%>
<%--		setCondition();--%>
<%--	}--%>
<%--	--%>
<%--	--%>
<%--}--%>



</script>

<layout:form action="EHF020700M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="未打卡單申請">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<logic:lessEqual name="Form1Datas" property="EHF020700T0_14" value="0001">
				<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF020700M1" ></layout:image>
				<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF020700M1" ></layout:image>
			</logic:lessEqual>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<logic:lessEqual name="Form1Datas" property="EHF020700T0_14" value="0001">
				<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  reqCode="submitForm" property="EHF020700M1" ></layout:image>
			</logic:lessEqual>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF020700M1" ></layout:image>
    </layout:row>
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="未打卡單單號" property="EHF020700T0_01" 		styleId="EHF020700T0_01" 		size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="空表單編號" 	property="EHF020700T0_02" 		styleId="EHF020700T0_02" 		size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="表單狀態" 	property="EHF020700T0_14" 		styleId="EHF020700T0_14" 		size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="表單狀態說明" property="EHF020700T0_14_DESC" 	styleId="EHF020700T0_14_DESC" 	size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I" size="10" startYear="2010" endYear="2025"  key="填表日期" name="Form1Datas" property="EHF020700T0_07" styleClass="DATAS"  />
		<layout:cell styleClass="DATAS" ></layout:cell>
		
		<layout:text styleClass="DATAS" tooltip="填單人部門" key="填單人部門" property="EHF020700T0_06" styleId="EHF020700T0_06" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />		
		<layout:text styleClass="DATAS" tooltip="填單人部門" key="填單人部門" property="EHF020700T0_06_SHOW" styleId="EHF020700T0_06_SHOW" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020700T0_06_TXT" styleId="EHF020700T0_06_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="填單人" key="填單人" property="EHF020700T0_05" styleId="EHF020700T0_05" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="填單人" 	key="填單人" 	property="EHF020700T0_05_SHOW" styleId="EHF020700T0_05_SHOW" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020700T0_05_TXT" styleId="EHF020700T0_05_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
									
		<logic:equal name="person" value="yes">
			<%--一般員工顯示頁面			--%>
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020700T0_04" styleId="EHF020700T0_04" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020700T0_04_SHOW" styleId="EHF020700T0_04_SHOW" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="EHF020700T0_04_TXT" styleId="EHF020700T0_04_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020700T0_03" styleId="EHF020700T0_03" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020700T0_03_SHOW" styleId="EHF020700T0_03_SHOW" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="EHF020700T0_03_TXT" styleId="EHF020700T0_03_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			<%--一般員工顯示頁面			--%>
		</logic:equal>
		
		<logic:equal name="accounting" value="yes">
			<%--會計顯示頁面			--%>
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020700T0_04" styleId="EHF020700T0_04" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020700T0_04_SHOW" styleId="EHF020700T0_04_SHOW" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="EHF020700T0_04_TXT" styleId="EHF020700T0_04_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020700T0_03" styleId="EHF020700T0_03" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020700T0_03_SHOW" styleId="EHF020700T0_03_SHOW" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="EHF020700T0_03_TXT" styleId="EHF020700T0_03_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			<%--會計顯示頁面			--%>
		</logic:equal>
			
		<logic:equal name="person_manager" value="yes">
							
			<%--人資顯示頁面			--%>
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020700T0_04" styleId="EHF020700T0_04" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />	
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020700T0_04_SHOW" styleId="EHF020700T0_04_SHOW" size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020700M0F" 
					id="EHF020700T0_04,EHF020700T0_04_SHOW,EHF020700T0_04_TXT" 
					lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
					table="EHF000200T0"
					fieldAlias="系統代碼,部門代號,部門名稱" 
					fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
					others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					mode="E,E,F"
					/>
				<layout:text layout="false" property="EHF020700T0_04_TXT" styleId="EHF020700T0_04_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020700T0_03" styleId="EHF020700T0_03" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020700T0_03_SHOW" styleId="EHF020700T0_03_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020700M0F" 
					id="EHF020700T0_03,EHF020700T0_03_SHOW,EHF020700T0_03_TXT" 
					lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
					table="EHF010100T6"
					leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
					fieldAlias="系統代碼,工號,姓名" 
					fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
					parentId="EHF010100T6_02" 
					parentField="window.EHF020700M0F.EHF020700T0_04.value" 
					others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
					beforerun="chkApplyDeptID()"
					mode="E,E,F"																	
					/>	
				<layout:text layout="false" property="EHF020700T0_03_TXT" styleId="EHF020700T0_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			<%--人資顯示頁面			--%>	
												
		</logic:equal>
		
		<logic:equal name="boss_manager" value="yes">
							
			<%--老闆顯示頁面			--%>
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020700T0_04" styleId="EHF020700T0_04" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />	
			<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020700T0_04_SHOW" styleId="EHF020700T0_04_SHOW" size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020700M0F" 
					id="EHF020700T0_04,EHF020700T0_04_SHOW,EHF020700T0_04_TXT" 
					lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
					table="EHF000200T0"
					fieldAlias="系統代碼,部門代號,部門名稱" 
					fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
					others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					mode="E,E,F"
					/>
				<layout:text layout="false" property="EHF020700T0_04_TXT" styleId="EHF020700T0_04_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020700T0_03" styleId="EHF020700T0_03" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020700T0_03_SHOW" styleId="EHF020700T0_03_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020700M0F" 
					id="EHF020700T0_03,EHF020700T0_03_SHOW,EHF020700T0_03_TXT" 
					lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
					table="EHF010100T6"
					leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
					fieldAlias="系統代碼,工號,姓名" 
					fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
					parentId="EHF010100T6_02" 
					parentField="window.EHF020700M0F.EHF020700T0_04.value" 
					others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
					beforerun="chkApplyDeptID()"
					mode="E,E,F"																	
					/>	
				<layout:text layout="false" property="EHF020700T0_03_TXT" styleId="EHF020700T0_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			<%--老闆顯示頁面			--%>	
												
		</logic:equal>
		
	</layout:grid>
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
					
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2010" endYear="2025"  key="未打卡日期" name="Form1Datas" property="EHF020700T0_08" styleClass="DATAS"  >
<%--			&nbsp;&nbsp;&nbsp;&nbsp;<layout:message styleClass="DATAS" key="未打卡類別:" />--%>
<%--			--%>
		</layout:date>
				
		<layout:select styleClass="DATAS" key="未打卡類別" name="Form1Datas" property="EHF020700T0_09"  styleId="EHF020700T0_09" mode="E,E,I"  >
				<layout:options collection="listEHF020700T0_09" property="item_id" labelProperty="item_value" />
		</layout:select>	
						
		<layout:text styleClass="DATAS" key="實際時間" property="EHF020700T0_10" styleId="EHF020700T0_10" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
			&nbsp;
			<layout:select key="實際時間(起)(時)" name="Form1Datas" property="EHF020700T0_10_HH" styleId="EHF020700T0_10_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF020700T0_10_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;點&nbsp;
			<layout:select key="實際時間(起)(分)" name="Form1Datas" property="EHF020700T0_10_MM" styleId="EHF020700T0_10_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF020700T0_10_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;分
			&nbsp;
			~
			&nbsp;
			<layout:select key="實際時間(起)(時)" name="Form1Datas" property="EHF020700T0_11_HH" styleId="EHF020700T0_11_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF020700T0_10_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;點&nbsp;
			<layout:select key="實際時間(起)(分)" name="Form1Datas" property="EHF020700T0_11_MM" styleId="EHF020700T0_11_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF020700T0_10_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;分
		</layout:text>
		
		<layout:text styleClass="DATAS" property="EHF020700T0_12" styleId="EHF020700T0_12" key="未打卡原因" name="Form1Datas"  mode="E,E,I"size="120" maxlength="100"/> 
		
		<logic:notEqual name="Form1Datas" property="EHF020700T0_01" value="">
			<layout:field styleClass="DATAS" property="EHF020700T0_13" key="附加檔案" name="Form1Datas" access="READONLY" >
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
<%--   		 			action="EHF020700M1.do?reqCode=getAttachedFile"--%>
   		 		</logic:equal>
			</layout:field>
		</logic:notEqual>		
		
	</layout:grid>
	
	<logic:equal name="collection" value="show">
	
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<logic:notEqual name="Form1Datas" property="EHF020700T0_01" value="">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE"	styleId="USER_CREATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE"	styleId="USER_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" 	styleClass="LOGDATA" property="VERSION" 	styleId="VERSION" 		name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE"	styleId="DATE_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
		</logic:notEqual>
		</layout:grid>
		
	</logic:equal>
	
<%--	<layout:popup styleId="comment2" styleClass="DATAS" key="簽核意見">--%>
<%--		<layout:text styleClass="DATAS" property="COMMENT2" key="內容" size="30" maxlength="40" name="Form1Datas"  mode="E,E,E" />--%>
<%--				<layout:row >				--%>
<%--					<layout:submit value="送出" reqCode="signForm" property="EHF020700M1"  ></layout:submit>--%>
<%--					<layout:button value="取消" onclick="closeStrutsLayoutPopup('comment2');" />--%>
<%--				</layout:row>--%>
<%--	</layout:popup>--%>
	
<%--  表單簽核歷程	--%>
<%--	<layout:popup styleId="formsignlog" styleClass="DATAS" key="簽核歷程">--%>
<%--	--%>
<%--		<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  --%>
<%--					       width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >--%>
<%--			<layout:collectionItem property="SIGNER" style="TEXT-ALIGN: CENTER" title="簽核者" />--%>
<%--			<layout:collectionItem property="SIGN_TIME" style="TEXT-ALIGN: CENTER" title="簽核時間" />--%>
<%--			<layout:collectionItem property="SIGN_STATUS" style="TEXT-ALIGN: CENTER" title="簽核狀態" />--%>
<%--			<layout:collectionItem property="SIGN_COMMENT" style="TEXT-ALIGN: CENTER" title="簽核意見"/>--%>
<%--		</layout:collection>--%>
<%--		<layout:row >--%>
<%--			<layout:button value="關閉" onclick="closeStrutsLayoutPopup('formsignlog');" />--%>
<%--		</layout:row>--%>
<%--				--%>
<%--	</layout:popup>--%>
	
	<logic:lessEqual name="Form1Datas" property="EHF020700T0_14" value="0001">
		<layout:popup styleId="fileupload" styleClass="DATAS" key="附加檔案上傳">
			<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="uploadEHF020700M1" ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileupload');" />
				</layout:row>
		</layout:popup>
	</logic:lessEqual>
	
</layout:form>