<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020801M0F" %>
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
	if(document.getElementById("EHF020800T0_11").value == "" 
	   || document.getElementById("EHF020800T0_11").value == null ){
		alert("請先選擇申請人的部門!!");
		return false;
	}else{
		return true;
	}
}
function setTime(){
	//document.getElementById("EHF020800T1_07_year").value = document.getElementById("EHF020800T1_06_year").value;
	$("input[name='EHF020800T1_07_year']").val($("input[name='EHF020800T1_06_year']").val());
	return true;
}

</script>

<layout:form action="EHF020801M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="加班單申請">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,H" name="btnimage?text=button.add.ok&type=t"   reqCode="addDataForm" property="EHF020801M1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF020801M1" ></layout:image>
		</logic:notEqual>

		<logic:equal name="button" value="query">
			
			
			<logic:equal name="Form1Datas" property="EHF020800T0_09" value="01" >
				<layout:image alt="確認" mode="H,D,D" name="btnimage?text=button.confirmData&type=t" reqCode="confirm" property="EHF020801M1" confirm="您確定要確認加班資料嗎?" ></layout:image>
			</logic:equal>
			<logic:notEqual name="SHOW" value="NO" >	
			<logic:equal name="Form1Datas" property="EHF020800T0_09" value="03" >
				<layout:image alt="回復確認" mode="H,D,D" name="btnimage?text=button.revertData&type=t" reqCode="recovery" property="EHF020801M1" confirm="您確定要還原加班資料嗎?" ></layout:image>
			</logic:equal>
			</logic:notEqual>
<%--			<logic:notEqual name="DisplayFileName" value="" >--%>
<%--				<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" >--%>
<%--				</layout:image>--%>
<%--			</logic:notEqual>--%>
			
			<logic:lessThan name="Form1Datas" property="EHF020800T0_09" value="04" >	  
<%--				<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=刪除&type=t"  reqCode="delForm" property="EHF020801M1"  confirm="您確定要刪除資料嗎?" ></layout:image>--%>
			</logic:lessThan>	
		</logic:equal>
		
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t" reqCode="redirect" property="EHF020801M1" ></layout:image>
	</layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${DETAIL_ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	
		<layout:text styleClass="DATAS" key="表單編號" name="Form1Datas" property="EHF020800T0_01"  styleId="EHF020800T0_01" size="18" maxlength="20" mode="H,H,H" />
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I" styleClass="DATAS"  tooltip="◎填單日期" key="填單日期" name="Form1Datas" property="EHF020800T0_05"
					 startYear="2010" endYear="2025" size="8" maxlength="9"  isRequired="true" onkeydown="nextFiled()" />
		<layout:select key="表單狀態" name="Form1Datas" property="EHF020800T0_09" styleId="EHF020800T0_09" styleClass="DATAS" mode="I,I,I" >
			<layout:options collection="listEHF020800T0_09" property="item_id" labelProperty="item_value" />
		</layout:select>
		<layout:text styleClass="DATAS" tooltip="填單人部門" key="填單人部門" property="EHF020800T0_04" styleId="EHF020800T0_04" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" /> 
		<layout:text styleClass="DATAS" tooltip="填單人部門" key="填單人部門" property="EHF020800T0_04_SHOW" styleId="EHF020800T0_04_SHOW" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020800T0_04_TXT" styleId="EHF020800T0_04_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:text styleClass="DATAS" tooltip="填單人" key="填單人" property="EHF020800T0_03" styleId="EHF020800T0_03" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />	
		<layout:text styleClass="DATAS" tooltip="填單人" key="填單人" property="EHF020800T0_03_SHOW" styleId="EHF020800T0_03_SHOW" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020800T0_03_TXT" styleId="EHF020800T0_03_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" styleClass="DATAS"  tooltip="◎加班考勤日期" key="加班考勤日期" name="Form1Datas" property="EHF020800T0_06"
					  startYear="2010" endYear="2025" size="8" maxlength="9"  isRequired="true" onkeydown="nextFiled()" />
		<layout:cell styleClass="DATAS" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11" styleId="EHF020800T0_11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11_SHOW" styleId="EHF020800T0_11_SHOW"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020801M0F" 
				id="EHF020800T0_11,EHF020800T0_11_SHOW,EHF020800T0_11_TXT" 
				lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
				table="EHF000200T0"
				fieldAlias="系統代碼,部門代號,部門名稱" 
				fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
				others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
				mode="E,E,F"
			/>
			<layout:text layout="false" property="EHF020800T0_11_TXT" styleId="EHF020800T0_11_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020800T1_04" styleId="EHF020800T1_04" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020800T1_04_SHOW" styleId="EHF020800T1_04_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020801M0F" 
				id="EHF020800T1_04,EHF020800T1_04_SHOW,EHF020800T1_04_TXT" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
				parentId="EHF010100T6_02" 
				parentField="window.EHF020801M0F.EHF020800T0_11.value" 
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "	
				mode="E,E,F"
				beforerun="chkApplyDeptID()"																
			/>
			<layout:text layout="false" property="EHF020800T1_04_TXT" styleId="EHF020800T1_04_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<%--加班開始時間--%>	
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  tooltip="◎加班開始日期時間" startYear="2010" endYear="2025"	 size="10" maxlength="12" 
						property="EHF020800T1_06_year"     key="加班開始日期時間" name="Form1Datas" styleClass="DATAS"  layout="true" onchange="setTime()" >
				&nbsp;&nbsp;    
				<layout:cell styleClass="DATAS" >
					<layout:select key="加班開始時間" name="Form1Datas" property="EHF020800T1_06_HH" styleId="EHF020800T1_06_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listHOUR" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;點&nbsp;
					<layout:select key="加班開始時間(分)" name="Form1Datas" property="EHF020800T1_06_MM" styleId="EHF020800T1_06_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listMINUTE" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;
				</layout:cell>
			
		</layout:date>
		
		<%--加班結束時間--%>
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  tooltip="◎加班結束日期時間" startYear="2010" endYear="2025"	 size="10" maxlength="12" 
						property="EHF020800T1_07_year"     key="加班結束日期時間" name="Form1Datas" styleClass="DATAS"  layout="true" onkeydown="nextFiled()" >
				&nbsp;&nbsp;    
				<layout:cell styleClass="DATAS" >
					<layout:select key="加班結束時間" name="Form1Datas" property="EHF020800T1_07_HH" styleId="EHF020800T1_07_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listHOUR" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;點&nbsp;
					<layout:select key="加班結束時間(分)" name="Form1Datas" property="EHF020800T1_07_MM" styleId="EHF020800T1_07_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listMINUTE" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;
				</layout:cell>
			
		</layout:date>
		
		<%--加班休息時間--%>
		<layout:text styleClass="DATAS" key="加班休息開始時間" property="EHF020800T1_06_BRK" styleId="EHF020800T1_06_BRK" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:select key="加班休息開始時間(時)"
					   name="Form1Datas" property="EHF020800T1_06_BRK_HH" styleId="EHF020800T1_06_BRK_HH" 
					   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listHOUR" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;點&nbsp;
			<layout:select key="加班休息開始時間(分)"  name="Form1Datas" property="EHF020800T1_06_BRK_MM" styleId="EHF020800T1_06_BRK_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listMINUTE" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;分&nbsp;
		</layout:text>
		
		<layout:text styleClass="DATAS" key="加班休息結束時間" property="EHF020800T1_07_BRK" styleId="EHF020800T1_07_BRK" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:select key="加班休息結束時間(時)" 
						   name="Form1Datas" property="EHF020800T1_07_BRK_HH" styleId="EHF020800T1_07_BRK_HH" 
						   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listHOUR" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;點&nbsp;
			<layout:select key="加班休息結束時間(分)" name="Form1Datas" property="EHF020800T1_07_BRK_MM" styleId="EHF020800T1_07_BRK_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listMINUTE" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;分	&nbsp;
		</layout:text>
	
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		<layout:text cols="2" styleClass="DATAS" tooltip="加班事由" key="加班事由" size="40" maxlength="50" mode="E,E,I" name="Form1Datas"  property="EHF020800T1_09" styleId="EHF020800T1_09" />
	</layout:grid>
	
	<layout:notMode value="create" >
	<logic:equal name="button" value="query">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" 	styleClass="LOGDATA" property="USER_CREATE" 	styleId="USER_CREATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" 	styleClass="LOGDATA" property="USER_UPDATE" 	styleId="USER_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" 	 	styleClass="LOGDATA" property="VERSION" 		styleId="VERSION" 		name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" 	styleClass="LOGDATA" property="DATE_UPDATE" 	styleId="DATE_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>
	</layout:notMode>

</layout:form>