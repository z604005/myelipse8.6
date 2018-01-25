<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020800M0F" %>
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
	if(document.getElementById("EHF020800T1_11").value == "" 
	   || document.getElementById("EHF020800T1_11").value == null ){
		alert("請先選擇申請人的部門!!");
		return false;
	}else{
		return true;
	}
}
function fbutton(reqCode) {
	EHF020800M0F.elements['reqCode'].value=reqCode;
	EHF020800M0F.submit();
}	
function setTime(){
	//document.getElementById("EHF020800T1_07_year").value = document.getElementById("EHF020800T1_06_year").value;
	$("input[name='EHF020800T1_07_year']").val($("input[name='EHF020800T1_06_year']").val());
	return true;
}
function setEmergencyAction(Flow_action){
	document.getElementById("emergency_flow_action").value = Flow_action;
}
function setAction(Flow_action){
	document.getElementById("FLOW_ACTION").value = Flow_action;
//	alert("flow_action==>"+document.getElementById("FLOW_ACTION").value);
	return openStrutsLayoutPopup('comment');
}
</script>

<layout:form action="EHF020800M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="加班單申請">

<input type="hidden" id="dataChanged" name="dataChanged" value="" />	
<input type="hidden" id="stf_open_type" value="${stf_open_type}" />
<input type="hidden" name="emergency_flow_action" id="emergency_flow_action" value=""/>
<input type="hidden" name="FLOW_ACTION" id="FLOW_ACTION" value=""/>

	<layout:row>
		<logic:notEqual name=	"button" value="edit">
		   <layout:image alt="儲存" mode="D,H,H" name="btnimage?text=button.save&type=t"   reqCode="addDataForm" property="EHF020800M1" ></layout:image>
		   
		   <%--		判斷是否為申請者本人並且不是人事經辦或老闆START			--%>
		   <logic:notEqual name="person_manager" value="yes">	
		   		<logic:notEqual name="boss_manager" value="yes">	
			   		<logic:equal name="personself" value="yes">
			   			<logic:notEqual name="Form1Datas" property="EHF020800T0_09" value="03">
	           				<logic:notEqual name="Form1Datas" property="EHF020800T0_09"  value="10">
	           					<layout:image alt="刪除" 	mode="H,D,D"  name="btnimage?text=button.delete&type=t"    reqCode="delForm" 		 property="EHF020800M0" confirm="您確定要刪除資料嗎?" ></layout:image>
	           				</logic:notEqual>
	           			</logic:notEqual>
	           		</logic:equal>
	           	</logic:notEqual>
           </logic:notEqual>
           <%--		判斷是否為申請者本人並且不是人事經辦或老闆END			--%>
           
           <logic:equal name="person_manager" value="yes">
           	 	<logic:notEqual name="Form1Datas" property="EHF020800T0_09" value="03">
           			<logic:notEqual name="Form1Datas" property="EHF020800T0_09"  value="10">
           				<layout:image alt="刪除" 	mode="H,D,D"  name="btnimage?text=button.delete&type=t"    reqCode="delForm" 		 property="EHF020800M0" confirm="您確定要刪除資料嗎?" ></layout:image>
           			</logic:notEqual>
           		</logic:notEqual>
           </logic:equal>
           
           <logic:equal name="boss_manager" value="yes">
           	 	<logic:notEqual name="Form1Datas" property="EHF020800T0_09" value="03">
           			<logic:notEqual name="Form1Datas" property="EHF020800T0_09"  value="10">
           				<layout:image alt="刪除" 	mode="H,D,D"  name="btnimage?text=button.delete&type=t"    reqCode="delForm" 		 property="EHF020800M0" confirm="您確定要刪除資料嗎?" ></layout:image>
           			</logic:notEqual>
           		</logic:notEqual>
           </logic:equal>
		</logic:notEqual>

		<logic:equal name="button" value="query">
		
		  <logic:equal name="emergency_flow" value="Y">
		  <%--		判斷是否為人事經辦START			--%>
		  <logic:equal name="person_manager" value="yes">	
	  		  	<logic:equal name="Form1Datas" property="EHF020800T0_09" value="01" >
		     		<layout:image alt="確認" mode="H,D,D" name="btnimage?text=button.confirmData&type=t" reqCode="confirm" property="EHF020800M1" confirm="您確定要確認加班資料嗎?" ></layout:image>
			  	</logic:equal>
			  	<logic:equal name="Form1Datas" property="EHF020800T0_09" value="06" >
			     		<layout:image alt="核准" mode="H,D,D" name="btnimage?text=button.approval&type=t" reqCode="confirm" property="EHF020800M1" confirm="您確定要確認加班資料嗎?" ></layout:image>
			  	</logic:equal>
			  	
			  	<logic:notEqual name="Form1Datas" property="EHF020800T0_09" value="06" >
			  		<logic:equal name="Form1Datas" property="EHF020800T0_09" value="07" >
			     		<layout:image alt="核准" mode="H,D,D" name="btnimage?text=button.approval&type=t" reqCode="confirm" property="EHF020800M1" confirm="您確定要確認加班資料嗎?" ></layout:image>
			  		</logic:equal>
			  	</logic:notEqual>
			  	
			  	<logic:notEqual name="SHOW" value="NO" >	
			  		<logic:equal name="Form1Datas" property="EHF020800T0_09" value="03" >
				    		<layout:image alt="作廢" mode="H,D,D" name="btnimage?text=button.invalid&type=t" reqCode="remove" property="EHF020800M1" confirm="您確定要作廢加班資料嗎?" ></layout:image>
			  		</logic:equal>
			  	</logic:notEqual>
		  </logic:equal>
		  <%--		判斷是否為人事經辦END			--%>
		  
		   <%--		判斷是否為老闆START			--%>
		  <logic:equal name="boss_manager" value="yes">	
		  		<logic:equal name="Form1Datas" property="EHF020800T0_09" value="01" >
			     		<layout:image alt="確認" mode="H,D,D" name="btnimage?text=button.confirmData&type=t" reqCode="confirm" property="EHF020800M1" confirm="您確定要確認加班資料嗎?" ></layout:image>
			  	</logic:equal>
			  	
			  	<logic:equal name="Form1Datas" property="EHF020800T0_09" value="06" >
			     		<layout:image alt="核准" mode="H,D,D" name="btnimage?text=button.approval&type=t" reqCode="confirm" property="EHF020800M1" confirm="您確定要確認加班資料嗎?" ></layout:image>
			  	</logic:equal>
			  	
			  	<logic:notEqual name="Form1Datas" property="EHF020800T0_09" value="06" >
			  		<logic:equal name="Form1Datas" property="EHF020800T0_09" value="07" >
			     		<layout:image alt="核准" mode="H,D,D" name="btnimage?text=button.approval&type=t" reqCode="confirm" property="EHF020800M1" confirm="您確定要確認加班資料嗎?" ></layout:image>
			  		</logic:equal>
			  	</logic:notEqual>
			  	
			  	<logic:notEqual name="SHOW" value="NO" >	
			  		<logic:equal name="Form1Datas" property="EHF020800T0_09" value="03" >
				    		<layout:image alt="作廢" mode="H,D,D" name="btnimage?text=button.invalid&type=t" reqCode="remove" property="EHF020800M1" confirm="您確定要作廢加班資料嗎?" ></layout:image>
			  		</logic:equal>
			  	</logic:notEqual>
		  </logic:equal>
		  <%--		判斷是否為老闆END			--%>
		  
<%--			<logic:notEqual name="DisplayFileName" value="" >--%>
<%--				<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" >--%>
<%--				</layout:image>--%>
<%--			</logic:notEqual>--%>
			
			<logic:lessThan name="Form1Datas" property="EHF020800T0_09" value="04" >	  
<%--				<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=刪除&type=t"  reqCode="delForm" property="EHF020800M1"  confirm="您確定要刪除資料嗎?" ></layout:image>--%>
			</logic:lessThan>
			</logic:equal>	

		</logic:equal>
<!--CREATE BY JIMMYWU-->		
		<logic:equal name="button" value="query">
		
			<logic:equal name="emergency_flow" value="Y">
			<%--		判斷是否為申請者本人且不是人事經辦START			--%>
			<%--堯堉不須看到呈核流程相關按鈕  只需完成即可	
			<logic:equal name="person" value="yes">	
					<logic:equal name="personself" value="yes">
							<logic:lessEqual name="Form1Datas" property="EHF020800T0_09" value="01">
								<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  			reqCode="submitForm" property="EHF020800M1" ></layout:image>
							</logic:lessEqual>

							<logic:equal name="Form1Datas" property="EHF020800T0_09" value="09">
								<layout:image alt="呈核" mode="H,D,D" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  			reqCode="submitForm" property="EHF020800M1" ></layout:image>
							</logic:equal>
					</logic:equal>
			</logic:equal>
			--%>
			<%--		判斷是否為申請者本人且不是人事經辦END			--%>
			<%--		判斷是否為人事經辦START			--%>
			<%--堯堉不須看到呈核流程相關按鈕  只需完成即可	
			<logic:equal name="person_manager" value="yes" >
					<logic:lessEqual name="Form1Datas" property="EHF020800T0_09" value="01">
						<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  	reqCode="submitForm" property="EHF020800M1" ></layout:image>
					</logic:lessEqual>

					<logic:equal name="Form1Datas" property="EHF020800T0_09" value="09">
						<layout:image alt="呈核" mode="H,D,D" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  	reqCode="submitForm" property="EHF020800M1" ></layout:image>
					</logic:equal>						
			</logic:equal>
			--%>
			<%--		判斷是否為人事經辦END		--%>
			<%--堯堉不須看到呈核流程相關按鈕  只需完成即可	
			<logic:equal name="boss_manager" value="yes" >
					<logic:lessEqual name="Form1Datas" property="EHF020800T0_09" value="01">
						<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  	reqCode="submitForm" property="EHF020800M1" ></layout:image>
					</logic:lessEqual>

					<logic:equal name="Form1Datas" property="EHF020800T0_09" value="09">
						<layout:image alt="呈核" mode="H,D,D" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  	reqCode="submitForm" property="EHF020800M1" ></layout:image>
					</logic:equal>						
			</logic:equal>
			--%>
				<logic:equal name="Form1Datas" property="EHF020800T0_09" value="06">
					<%--		是否為申請者本人			--%>
					<logic:equal name="personself" value="yes">
						<layout:image alt="抽單" mode="H,D,D" name="btnimage?text=button.withdrawal&type=t" reqCode="signFormEmergency" property="EHF020800M1" onclick="setEmergencyAction('0013');" ></layout:image>					
					</logic:equal>
					<%--		END_是否為申請者本人			--%>
					<logic:equal name="person_manager" value="yes" >
						<layout:image alt="駁回" mode="H,H,D" name="btnimage?text=button.reject&type=t" reqCode="signFormEmergency" property="EHF020800M1" onclick="setEmergencyAction('0003');" ></layout:image>
					</logic:equal>
					<logic:equal name="boss_manager" value="yes" >
						<layout:image alt="駁回" mode="H,H,D" name="btnimage?text=button.reject&type=t" reqCode="signFormEmergency" property="EHF020800M1" onclick="setEmergencyAction('0003');" ></layout:image>
					</logic:equal>
				</logic:equal>
			</logic:equal>
			
			<logic:notEqual name="emergency_flow" value="Y">
			
			<logic:equal name="personself" value="yes">
				<logic:lessEqual name="Form1Datas" property="EHF020800T0_09" value="01">
					<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  reqCode="submitForm" property="EHF020800M1" ></layout:image>
				</logic:lessEqual>
				<logic:equal name="Form1Datas" property="EHF020800T0_09" value="08">
					<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  reqCode="submitForm" property="EHF020800M1" ></layout:image>
				</logic:equal>
				<logic:equal name="Form1Datas" property="EHF020800T0_09" value="09">
					<layout:image alt="呈核" mode="H,D,H" name="btnimage?text=button.submit&type=t" confirm="您確定要呈核資料嗎?"
							  	  reqCode="submitForm" property="EHF020800M1" ></layout:image>
				</logic:equal>
				<logic:equal name="flow_hr" value="yes">
					<layout:image alt="自動簽核" mode="H,D,H" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020800M1" ></layout:image>
				</logic:equal>
				<logic:equal name="flow_se" value="yes">
					<layout:image alt="自動簽核" mode="H,D,H" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020800M1" ></layout:image>
				</logic:equal>
			</logic:equal>
				
				<logic:equal name="Form1Datas" property="EHF020800T0_09" value="06">
					<%--		Start_是否為申請者本人			--%>
					<logic:equal name="personself" value="yes">
						<layout:image alt="抽單" mode="H,D,D" name="btnimage?text=button.withdrawal&type=t" reqCode="cancel" property="EHF020800M1" onclick="setAction('09'); return false;" ></layout:image>					
					</logic:equal>
					<%--		END_是否為申請者本人			--%>
					<logic:equal name="sign" value="yes" >
						<layout:image alt="核准" mode="H,H,D" name="btnimage?text=button.approval&type=t" reqCode="approve" property="EHF020800M1" onclick="setAction('11'); return false;" ></layout:image>														
						<layout:image alt="駁回" mode="H,H,D" name="btnimage?text=button.reject&type=t" reqCode="reject" property="EHF020800M1" onclick="setAction('08'); return false;" ></layout:image>
						<logic:equal name="last_sign" value="yes" >
							<layout:image alt="自動簽核" mode="H,H,D" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020800M1" ></layout:image>
						</logic:equal>
					</logic:equal>
				</logic:equal>
				
				<logic:equal name="Form1Datas" property="EHF020800T0_09" value="11">
					<%--		Start_是否為申請者本人			--%>
					<logic:equal name="personself" value="yes">
						<layout:image alt="抽單" mode="H,D,D" name="btnimage?text=button.withdrawal&type=t" reqCode="cancel" property="EHF020800M1" onclick="setAction('09'); return false;" ></layout:image>					
					</logic:equal>
					<%--		END_是否為申請者本人			--%>
					<logic:equal name="sign" value="yes" >
						<layout:image alt="核准" mode="H,H,D" name="btnimage?text=button.approval&type=t" reqCode="approve" property="EHF020800M1" onclick="setAction('11'); return false;" ></layout:image>														
						<layout:image alt="駁回" mode="H,H,D" name="btnimage?text=button.reject&type=t" reqCode="reject" property="EHF020800M1" onclick="setAction('08'); return false;" ></layout:image>
						<logic:equal name="last_sign" value="yes" >
							<layout:image alt="自動簽核" mode="H,H,D" name="btnimage?text=button.auto.approval&type=t" reqCode="AutoApprove" property="EHF020800M1" ></layout:image>
						</logic:equal>	
					</logic:equal>
				</logic:equal>
				
				<logic:equal name="Form1Datas" property="EHF020800T0_09" value="03">
					<logic:equal name="boss" value="yes">
					<logic:equal name="person_manager" value="yes">
						<layout:image alt="作廢" mode="H,D,D" name="btnimage?text=button.invalid&type=t" reqCode="invalid" property="EHF020800M1" onclick="setAction('10'); return false;" ></layout:image>
					</logic:equal>
					</logic:equal>
					<logic:equal name="boss_manager" value="yes">
						<layout:image alt="作廢" mode="H,D,D" name="btnimage?text=button.invalid&type=t" reqCode="invalid" property="EHF020800M1" onclick="setAction('10'); return false;" ></layout:image>
					</logic:equal>
				</logic:equal>
				
			</logic:notEqual>
			
		</logic:equal>
<!--CREATE BY JIMMYWU-->
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t" reqCode="redirect" property="EHF020800M1" ></layout:image>
		<%--<layout:image alt="簽核歷程" mode="H,D,D" name="btnimage?text=button.flow.sign.logs&type=t" reqCode="" property="EHF020800M1" onclick="openStrutsLayoutPopup('formsignlog'); return false;" />--%>
	</layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<logic:notEqual name="Fields_Hide" value="YES">
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
		
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11" styleId="EHF020800T0_11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11_SHOW" styleId="EHF020800T0_11_SHOW"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020800M0F" 
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
		</logic:notEqual>
		
		
		
		<logic:equal name="Fields_Hide" value="YES">
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
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I" styleClass="DATAS"  tooltip="◎加班考勤日期" key="加班考勤日期" name="Form1Datas" property="EHF020800T0_06"
					  startYear="2010" endYear="2025" size="8" maxlength="9"  isRequired="true" onkeydown="nextFiled()" />
		
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11" styleId="EHF020800T0_11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11_SHOW" styleId="EHF020800T0_11_SHOW" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
			<layout:text layout="false" property="EHF020800T0_11_TXT" styleId="EHF020800T0_11_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		</logic:equal>

	</layout:grid>
	
	<layout:notMode value="create" >
	<logic:equal name="collection" value="show">
	
	<layout:notMode value="inspect">
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${DETAIL_ERR_MSG}" />
		<layout:message styleClass="MESSAGE_ERROR" key="勾選'限定中午加班'時，則只有紀錄中午加班小時，其餘不紀錄。" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">	
		
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020800T1_04" styleId="EHF020800T1_04" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020800T1_04_SHOW" styleId="EHF020800T1_04_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020800M0F" 
				id="EHF020800T1_04,EHF020800T1_04_SHOW,EHF020800T1_04_TXT" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 LEFT JOIN EHF010100T1 ON EHF010100T6_01 = EHF010100T1_01"
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
				parentId="EHF010100T6_02" 
				parentField="window.EHF020800M0F.EHF020800T0_11.value" 
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' AND EHF010100T1.EHF010100T1_02 in ('1','3') and  EHF010100T1.EHF010100T1_04='0'"	
				mode="E,E,F"																
			/>
			<layout:text layout="false" property="EHF020800T1_04_TXT" styleId="EHF020800T1_04_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
		<layout:radios styleClass="DATAS" cols="2" tooltip="加班處理方式" key="加班處理方式" property="EHF020800T1_11" mode="E,E,I" name="Form1Datas" value = "01">
			<layout:options collection="listEHF020800T1_11" property="item_id" labelProperty="item_value" />
		</layout:radios>
		
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
		
		<layout:text cols="2" styleClass="DATAS" tooltip="加班事由" key="加班事由" size="40" maxlength="50" mode="E,E,I" name="Form1Datas"  property="EHF020800T1_09" styleId="EHF020800T1_09" />
		
 	 	<layout:text styleClass="DATAS" tooltip="中午加班時數" key="中午加班時數" property="EHF020800T1_12" styleId="EHF020800T1_12" size="12" mode="E,E,H" maxlength="20" name="Form1Datas" >			
		小時，
		<layout:checkbox key="中午加班"	 name="Form1Datas" property="NOON_LIMITED" styleClass="DATAS" styleId="NOON_LIMITED" 	 mode="E,E,I"  layout="false"/>
		
		是否限定中午加班
		</layout:text>
		<layout:cell styleClass="DATAS" >
		
				<%--		判斷是否為申請者本人並且不是人事經辦START			--%>
				<logic:notEqual name="person_manager" value="yes">
				   	   <logic:equal name="personself" value="yes">
							<layout:image alt="新增加班明細" mode="D,D,H" 
							  			name="btnimage?text=button.addDetailData&type=t" disabledName="btnimage?text=新增加班明細&type=f" 
							  			property="EHF020800M0" 
							  			reqCode="handleDetailDataForm" 
							  			onclick="ctrlDetail('add', 'EHF020800T1');" >
							</layout:image>
					   </logic:equal>
				</logic:notEqual>
				<%--		判斷是否為申請者本人並且不是人事經辦END			--%>
				
			    <%--		判斷是否為人事經辦START			--%>
			    <logic:equal name="person_manager" value="yes">
					    <layout:image alt="新增加班明細" mode="D,D,H" 
							  		name="btnimage?text=button.addDetailData&type=t" disabledName="btnimage?text=新增加班明細&type=f" 
							  		property="EHF020800M0" 
							  		reqCode="handleDetailDataForm" 
							  		onclick="ctrlDetail('add', 'EHF020800T1');" >
						</layout:image>
			    </logic:equal>
			    <%--		判斷是否為人事經辦END			--%>
			
		</layout:cell>
		
		<layout:radios styleClass="DATAS" cols="4" tooltip="加班類別" key="加班類別" property="EHF020800T1_13" mode="E,E,I" name="Form1Datas">
			<layout:options collection="listEHF020800T1_13" property="item_id" labelProperty="item_value" />
		</layout:radios>
		
	</layout:grid>
	</layout:notMode>
	
	<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF020800T1_LIST" 
					   id="bean1" indexId="index" 
					   selectId="" selectProperty="" selectName="" 
					   width="100%" 
					   styleClass="COLLECTION" styleClass2="COLLECTION_2">
			
			<layout:notMode value="inspect">
			
			<%--		判斷是否為申請者本人並且不是人事經辦START			--%>
			<logic:notEqual name="person_manager" value="yes">
			  		<logic:equal name="personself" value="yes">
							<layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除" 
									   			url="EHF020800M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF020800T1" 
									   			paramId="EHF020800T0_01,EHF020800T0_06,EHF020800T1_01,EHF020800T1_02,EHF020800T1_03,EHF020800T1_04" 
									   			paramProperty="EHF020800T1_01,EHF020800T0_06,EHF020800T1_01,EHF020800T1_02,EHF020800T1_03,EHF020800T1_04" 
									   			onclick="return confirmDelData('您確定要刪除加班資料嗎?');" >
							刪除	
							</layout:collectionItem>
			  		</logic:equal>
			</logic:notEqual>
			<%--		判斷是否為申請者本人並且不是人事經辦END			--%>
			
			<%--		判斷是否為人事經辦START			--%>
			  <logic:equal name="person_manager" value="yes">
					<layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除" 
									   	url="EHF020800M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF020800T1" 
									   	paramId="EHF020800T0_01,EHF020800T0_06,EHF020800T1_01,EHF020800T1_02,EHF020800T1_03,EHF020800T1_04" 
									   	paramProperty="EHF020800T1_01,EHF020800T0_06,EHF020800T1_01,EHF020800T1_02,EHF020800T1_03,EHF020800T1_04" 
									   	onclick="return confirmDelData('您確定要刪除加班資料嗎?');" >
					刪除	
					</layout:collectionItem>
			  </logic:equal>
			<%--		判斷是否為人事經辦END			--%>
			</layout:notMode>
			
			<layout:collectionItem property="EHF020800T1_04" style="TEXT-ALIGN: CENTER" title="員工" />
			<layout:collectionItem property="EHF020800T1_06" style="TEXT-ALIGN: CENTER" title="加班時間" />
			<layout:collectionItem property="EHF020800T1_08" style="TEXT-ALIGN: CENTER" title="預計加班時數" />
			<layout:collectionItem property="EHF020800T1_06_BRK" style="TEXT-ALIGN: CENTER" title="加班休息時間" />
			<layout:collectionItem property="EHF020800T1_08_DE" style="TEXT-ALIGN: CENTER" title="加班內扣時數" />
			<layout:collectionItem property="EHF020800T1_12" style="TEXT-ALIGN: CENTER" title="中午加班時數" />
			<layout:collectionItem property="EHF020800T1_08_REAL" style="TEXT-ALIGN: CENTER" title="實際加班時數" />
			<layout:collectionItem property="EHF020800T1_11" style="TEXT-ALIGN: CENTER" title="處理方式" />
			<layout:collectionItem property="EHF020800T1_13" style="TEXT-ALIGN: CENTER" title="加班類別" />
			<layout:collectionItem property="EHF020800T1_09" style="TEXT-ALIGN: LEFT" title="加班事由" />
			
			
		</layout:collection>
	
	</logic:equal>
	</layout:notMode>
	
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
	
	<layout:popup styleId="comment" styleClass="DATAS" key="簽核意見">
		<layout:text styleClass="DATAS" property="SIGN_COMMENT" key="內容" size="30" maxlength="40" name="Form1Datas"  mode="E,E,E" />
		<layout:row >				
			<layout:submit value="送出" reqCode="signForm" property="EHF020800M1"  ></layout:submit>
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
	
</layout:form>