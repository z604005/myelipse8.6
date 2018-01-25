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

<!-- JavaScript -->
<script type="text/javascript">

function chkApplyDeptID(){
	if(document.getElementById("EHF020104T1_02").value == "" || document.getElementById("EHF020104T1_02").value == null ){
		alert("請先選擇部門!!");
		return false;
	}else{
		return true;
	}
}

function chkClass(){
	if(document.getElementById("EHF020104T1_04").value == document.getElementById("EHF020104T1_05").value ){
		alert("新班別不可與舊班別一樣!!");
		//document.getElementById("EHF010104T1_04").value = "";
		document.getElementById("EHF020104T1_05").value = "";
		return false;
	}else{
		return true;
	}
}

function fbutton(reqCode) {
				EHF020104M0F.elements['reqCode'].value=reqCode;
				EHF020104M0F.submit();
		}	

function getSelectBox(){
	
	//showEMSWait();
	return fbutton('changeSelectBox');

}


function cls2(){
	
	document.getElementById("EHF020104T1_03").value = "";
	document.getElementById("EHF020104T1_03_NUM").value = "";
	document.getElementById("EHF020104T1_03_TXT").value = "";
	return true;
}

</script>

<layout:form action="EHF020104M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="預排換班設定">
<input type="hidden" id="dataChanged" name="dataChanged" value="" />	
<input type="hidden" id="chk_type" value="${chk_type}" />
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="儲存" mode="D,H,H" name="btnimage?text=button.save&type=t"  reqCode="addDataForm" property="EHF020104M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveData" property="EHF020104M1" ></layout:image>
			<logic:equal name="Form1Datas" property="EHF020104T0_03" value="01">
<%--				<layout:image alt="確認資料" mode="H,D,H" name="btnimage?text=button.confirmData&type=t"  reqCode="confirm" property="EHF020104M1" --%>
<%--						  	  confirm="您確定要確認資料嗎?" ></layout:image>--%>
			</logic:equal>			  
			<layout:image alt="刪除明細" mode="H,D,H" name="btnimage?text=button.deleteDetailData&type=t"  reqCode="delDetailData" property="EHF020104M1"
						  confirm="您確定要刪除明細資料嗎?" ></layout:image>
			<logic:equal name="Form1Datas" property="EHF020104T0_03" value="02">
<%--				<layout:image alt="還原資料" mode="H,H,D" name="btnimage?text=button.revertData&type=t"  reqCode="recovery" property="EHF020104M1"--%>
<%--						  	  confirm="您確定要還原資料嗎?" ></layout:image>--%>
<%--				<layout:image alt="手動執行" mode="H,H,D" name="btnimage?text=button.manually.procedure&type=t"  reqCode="doManual" property="EHF020104M1"--%>
<%--						  	  confirm="您確定要手動執行嗎?" ></layout:image>--%>
			</logic:equal>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF020104M1" ></layout:image>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="預排換班序號" property="EHF020104T0_01" styleId="EHF020104T0_01" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<logic:notEqual name="button" value="query">
			<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" tooltip="◎換班日期" startYear="2012" endYear="2025" size="8" maxlength="9" property="EHF020104T0_02" 
				     key="換班日期" name="Form1Datas" styleClass="DATAS" isRequired="true" onkeydown="nextFiled()" />
		</logic:notEqual>		     
<%--		<layout:cell styleClass="DATAS" ></layout:cell>--%>
		<logic:equal name="button" value="query">
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I" tooltip="◎換班日期" startYear="2012" endYear="2025" size="8" maxlength="9" property="EHF020104T0_02" 
				     key="換班日期" name="Form1Datas" styleClass="DATAS" isRequired="true" onkeydown="nextFiled()" />
				 
		</logic:equal>
		<logic:notEqual name="Form1Datas" property="EHF020104T0_03" value="" >
<%--			<layout:select key="狀態" name="Form1Datas" property="EHF020104T0_03" styleId="EHF020104T0_03" styleClass="DATAS" mode="I,I,I" >--%>
<%--				<layout:options collection="EHF020104T0_03_list" property="item_id" labelProperty="item_value" />--%>
<%--			</layout:select>--%>
		</logic:notEqual>
		
		<logic:greaterEqual name="Form1Datas" property="EHF020104T0_03" value="02" >
			<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" tooltip="執行日期" startYear="2012" endYear="2025" size="8" maxlength="9" property="EHF020104T0_04" 
				     	 key="執行日期" name="Form1Datas" styleClass="DATAS"  isRequired="true" onkeydown="nextFiled()" />
		</logic:greaterEqual>
		
	</layout:grid>
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
<%--		<layout:textarea styleClass="DATAS" property="EHF010104T0_05" key="備註" --%>
<%--						 cols="5" rows="10" size="100" maxlength="75" name="Form1Datas"  mode="E,E,I" />--%>
		<layout:text styleClass="DATAS" property="EHF020104T0_05" styleId="EHF020104T0_05" key="備註" size="50" maxlength="50" name="Form1Datas"  mode="E,E,I" />
	</layout:grid>
	
	
	<logic:equal name="collection" value="show">	
	
	<logic:equal name="Form1Datas" property="EHF020104T0_03" value="01">
		<layout:grid cols="3"  space="false" borderSpacing="0" align="left" width="100%" styleClass="DATAGRID">
			
			<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="EHF020104T1_02" 		styleId="EHF020104T1_02" 	 size="10" mode="H,H,I" maxlength="20" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="EHF020104T1_02_NUM" styleId="EHF020104T1_02_NUM"  size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
					<sp:lov 	
						form="EHF020104M0F" 
						id="EHF020104T1_02,EHF020104T1_02_NUM,EHF020104T1_02_TXT" 
						lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
						table="EHF000200T0"
						fieldAlias="部門名稱,部門代號,系統代碼" 
						fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"	
						beforerun="cls2()"									
						others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					/>
				<layout:text layout="false" property="EHF020104T1_02_TXT" styleId="EHF020104T1_02_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020104T1_03" styleId="EHF020104T1_03" size="12" mode="H,H,I" maxlength="20" name="Form1Datas" />
			
			<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020104T1_03_NUM" styleId="EHF020104T1_03_NUM" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF020104M0F" 
							id="EHF020104T1_03,EHF020104T1_03_NUM,EHF020104T1_03_TXT" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EHF020104M0F.EHF020104T1_02.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "
							changescript="getSelectBox()" 
							beforerun="chkApplyDeptID()"																	
						 	/>
				<layout:text layout="false" property="EHF020104T1_03_TXT" styleId="EHF020104T1_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			<layout:cell styleClass="DATAS" ></layout:cell>
			<layout:select key="舊班別" name="Form1Datas" property="EHF020104T1_04" styleId="EHF020104T1_04" styleClass="DATAS" mode="I,I,I" onchange="return chkClass();" >
				<layout:options collection="EHF020104T1_04_list" property="item_id" labelProperty="item_value" />
			</layout:select>
			<layout:select key="新班別" name="Form1Datas" property="EHF020104T1_05" styleId="EHF020104T1_05" styleClass="DATAS" mode="E,E,I" onchange="return chkClass();" >
				<layout:options collection="EHF020104T1_04_list" property="item_id" labelProperty="item_value" />
			</layout:select>
			<layout:cell styleClass="DATAS">
				<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF020104M1" 
							  reqCode="addDetailDataForm" ></layout:image>
			</layout:cell>
		</layout:grid>
	</logic:equal>
	
		<layout:collection emptyKey="沒有資料列"  name="Form1Datas" property="EHF020104C" id="bean1" indexId="index" selectId="" selectProperty=""   selectName=""  width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER" width="5%" >
				<layout:text name="Form1Datas" property="EHF020104C[${index}].EHF020104T1_01" layout="false" mode="H,H,H" />
				<layout:text name="Form1Datas" property="EHF020104C[${index}].EHF020104T1_02" layout="false" mode="H,H,H" />
				<layout:text name="Form1Datas" property="EHF020104C[${index}].EHF020104T1_03" layout="false" mode="H,H,H" />
				<layout:checkbox name="Form1Datas" property="EHF020104C[${index}].CHECKED" layout="false"/><BR>
				<div style="display: none;"><layout:checkbox name="Form1Datas" property="EHF020104C[${index}].CHANGED" layout="false"/></div>	
			</layout:collectionItem>
			
			<layout:collectionItem property="EHF020104T1_02_TXT" style="TEXT-ALIGN: CENTER" title="部門" />
			<layout:collectionItem property="EHF020104T1_03_TXT" style="TEXT-ALIGN: CENTER" title="員工" />
			<layout:collectionItem property="EHF020104T1_04" style="TEXT-ALIGN: CENTER" title="舊班別" />
			<layout:collectionItem property="EHF020104T1_05" style="TEXT-ALIGN: CENTER" title="新班別" />
			
		</layout:collection>
	
	</logic:equal>
	
	<logic:equal name="button" value="query">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>

</layout:form>