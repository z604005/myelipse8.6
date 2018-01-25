<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.utils.struts.form.EMS_VIEWDATAF" %>
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
	EMS_VIEWDATAF.elements['reqCode'].value=reqCode;
	EMS_VIEWDATAF.elements['confirmb'].value='';
	EMS_VIEWDATAF.submit();
}

function getList(){
	//Show EMS Wait
	showEMSWait();
	return fbutton('queryForm');
}

function setAction(Flow_action){
	document.getElementById("FLOW_ACTION").value = Flow_action;
//	alert("flow_action==>"+document.getElementById("FLOW_ACTION").value);
	return openStrutsLayoutPopup('comment');
}

function setEmergencyAction(Flow_action){
	document.getElementById("emergency_flow_action").value = Flow_action;
}

</script>

<layout:form action="EMS_SIGNERM0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="表單待簽核區">
<%--	<input type="text" id="button" value="${buttonType}" />--%>
<input type="hidden" name="confirmb" value="">
<input type="hidden" id="FLOW_ACTION" name="FLOW_ACTION" value="" />
<%--<layout:text styleClass="DATAS" key="flow動作" name="Form1Datas" property="FLOW_ACTION" mode="H,H,H"></layout:text>--%>
<input type="hidden" name="emergency_flow_action" id="emergency_flow_action" value=""/>
	<layout:row>
			<logic:equal name="emergency_flow" value="N">
			<logic:equal name="boss" value="yes">
<%--				<logic:notEqual name="person_manager" value="yes">--%>
					<layout:image alt="核准" mode="H,D,H" name="btnimage?text=button.approval&type=t"  onclick="setAction('0002'); return false;" ></layout:image>	
<%--				</logic:notEqual>--%>
				
<%--				<logic:equal name="person_manager" value="yes">--%>
<%--					<layout:image alt="完成" mode="H,D,H" name="btnimage?text=button.complete&type=t"  onclick="setAction('0002'); return false;" ></layout:image>	--%>
<%--				</logic:equal>--%>
				<layout:image alt="駁回" mode="H,D,H" name="btnimage?text=button.reject&type=t"  onclick="setAction('0003'); return false;" ></layout:image>
			</logic:equal>
			</logic:equal>
			<logic:equal name="emergency_flow" value="Y">
				<logic:equal name="boss" value="yes">
					<logic:equal name="person_manager" value="yes">
						<layout:image alt="完成" mode="H,D,H" name="btnimage?text=button.complete&type=t" reqCode="EmergencySignForm" property="NT_SIGNER" onclick="setEmergencyAction('0002');" ></layout:image>	
					</logic:equal>
					<logic:equal name="boss_manager" value="yes">
						<layout:image alt="完成" mode="H,D,H" name="btnimage?text=button.complete&type=t" reqCode="EmergencySignForm" property="NT_SIGNER" onclick="setEmergencyAction('0002');" ></layout:image>	
					</logic:equal>
					<layout:image alt="駁回" mode="H,D,H" name="btnimage?text=button.reject&type=t" reqCode="EmergencySignForm" property="NT_SIGNER" onclick="setEmergencyAction('0003');" ></layout:image>
				</logic:equal>
			</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERROR_MSG}" />
	</layout:row>
	<layout:row>
   		 <layout:message styleClass="SMALL_MESSAGE" key="${FormINF}" />
	</layout:row>
	
	<logic:equal name="collection" value="show">
	
		<logic:equal name="boss" value="yes">
			
			<layout:row>
				<layout:select styleClass="DATAS" key="待簽核單據" name="Form1Datas" property="DATA02"  mode="E,E,I" onchange="return getList();" >
					<layout:options collection="listDATA02" property="item_id" labelProperty="item_value" />
				</layout:select>
			</layout:row>

<%--     請假單待簽核單據	--%>
			<logic:equal name="receipt_type" value="EHF020200M0">	
			
				<%
					int item_index = 0;
					ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
					String strTmp = "";
				%>
				
				<layout:collection emptyKey="沒有資料列"  name="Form2Datas" indexId="index" selectId="" selectProperty="" selectName=""  
					    		   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					<layout:collectionItem title="選取">
						<center>
						<%
					
						if (item_index < list.size()){
							EMS_VIEWDATAF FORM =(EMS_VIEWDATAF)list.get(item_index);
							strTmp = FORM.getDATA01();
							item_index++;
						%>
							<input type="checkbox" name="checkId_boss" value="<%=strTmp%>" >
						<%
						}
						%>
						</center>
					</layout:collectionItem>
					<layout:collectionItem property="DATA01" style="TEXT-ALIGN: CENTER" title="請假單號" 
										   url="EHF020200M1.do?reqCode=queryForm" 
										   paramId="EHF020200T0_01,flow_sign_flag" 
										   paramProperty="DATA01,DATA20"
										   onclick="return showEMSWait();" />
					<layout:collectionItem property="DATA02" style="TEXT-ALIGN: CENTER" title="申請日期" />
					<layout:collectionItem property="DATA03" style="TEXT-ALIGN: CENTER" title="申請人" />
					<layout:collectionItem property="DATA06" style="TEXT-ALIGN: CENTER" title="代理人" />
					<layout:collectionItem property="DATA04" style="TEXT-ALIGN: CENTER" title="假別" />
					<layout:collectionItem property="DATA05" style="TEXT-ALIGN: CENTER" title="日期/時間"/>
					<layout:collectionItem property="DATA07" style="TEXT-ALIGN: LEFT" title="天/小時"/>
					<layout:collectionItem property="DATA09" style="TEXT-ALIGN: LEFT" title="事由"/>
					<layout:collectionItem property="DATA08" style="TEXT-ALIGN: LEFT" title="狀態"/>
				</layout:collection>
				
			</logic:equal>
			
<%--     出差單待簽核單據	--%>			
			<logic:equal name="receipt_type" value="EHF020301M0">	
			
				<%
					int item_index = 0;
					ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
					String strTmp = "";
				%>
	
				<layout:collection emptyKey="沒有資料列"  name="Form2Datas" indexId="index" selectId="" selectProperty="" selectName=""  
					    		   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					<layout:collectionItem title="選取">
						<center>
						<%
					
						if (item_index < list.size()){
							EMS_VIEWDATAF FORM =(EMS_VIEWDATAF)list.get(item_index);
							strTmp = FORM.getDATA01();
							item_index++;
						%>
							<input type="checkbox" name="checkId_boss" value="<%=strTmp%>" >
						<%
						}
						%>
						</center>
					</layout:collectionItem>
					<layout:collectionItem property="DATA01" style="TEXT-ALIGN: CENTER" title="出差單號" 
										   url="EHF020300M1.do?reqCode=queryForm" paramId="EHF020300T0_01,flow_sign_flag" paramProperty="DATA01,DATA20"
										   onclick="return showEMSWait();" />
					<layout:collectionItem property="DATA02" style="TEXT-ALIGN: CENTER" title="申請日期" />
					<layout:collectionItem property="DATA03" style="TEXT-ALIGN: CENTER" title="申請人" />
					<layout:collectionItem property="DATA04" style="TEXT-ALIGN: CENTER" title="地點"/>
					<layout:collectionItem property="DATA06" style="TEXT-ALIGN: CENTER" title="日期/時間"/>
					<layout:collectionItem property="DATA08" style="TEXT-ALIGN: LEFT" title="天/小時"/>
					<layout:collectionItem property="DATA05" style="TEXT-ALIGN: LEFT" title="事由"/>
					<layout:collectionItem property="DATA09" style="TEXT-ALIGN: LEFT" title="狀態"/>
				</layout:collection>
				
			</logic:equal>
			
<%--     銷假差待簽核單據	--%>
		<logic:equal name="receipt_type" value="EHF020600M0">	
			
				<%
					int item_index = 0;
					ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
					String strTmp = "";
				%>
	
				<layout:collection emptyKey="沒有資料列"  name="Form2Datas" indexId="index" selectId="" selectProperty="" selectName=""  
					    		   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					<layout:collectionItem title="選取">
						<center>
						<%
					
						if (item_index < list.size()){
							EMS_VIEWDATAF FORM =(EMS_VIEWDATAF)list.get(item_index);
							strTmp = FORM.getDATA01();
							item_index++;
						%>
							<input type="checkbox" name="checkId_boss" value="<%=strTmp%>" >
						<%
						}
						%>
						</center>
					</layout:collectionItem>
					<layout:collectionItem property="DATA01" style="TEXT-ALIGN: CENTER" title="銷假差單號" 
										   url="EHF020600M1.do?reqCode=queryForm" paramId="EHF020601T0_01,flow_sign_flag" paramProperty="DATA01,DATA20"
										   onclick="return showEMSWait();" />
					<layout:collectionItem property="DATA02" style="TEXT-ALIGN: CENTER" title="申請日期" />
					<layout:collectionItem property="DATA03" style="TEXT-ALIGN: CENTER" title="申請人" />
					<layout:collectionItem property="DATA04" style="TEXT-ALIGN: CENTER" title="單別"/>
					<layout:collectionItem property="DATA09" style="TEXT-ALIGN: LEFT" title="事由"/>
					<layout:collectionItem property="DATA05" style="TEXT-ALIGN: CENTER" title="日期/時間"/>
					<layout:collectionItem property="DATA07" style="TEXT-ALIGN: LEFT" title="天/小時"/>
					<layout:collectionItem property="DATA08" style="TEXT-ALIGN: LEFT" title="狀態"/>
				</layout:collection>
				
			</logic:equal>

<%--     加班單待簽核單據	--%>
		<logic:equal name="receipt_type" value="EHF020800M0">	
			
				<%
					int item_index = 0;
					ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
					String strTmp = "";
				%>
	
				<layout:collection emptyKey="沒有資料列"  name="Form2Datas" id="bean1" indexId="index" selectId="" selectProperty="" selectName=""  
					    		   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					<layout:collectionItem name="bean1" title="選取">
						<center>
						<%
					
						if (item_index < list.size()){
							EMS_VIEWDATAF FORM =(EMS_VIEWDATAF)list.get(item_index);
							strTmp = FORM.getDATA05();
							item_index++;
						%>
							<input type="checkbox" name="checkId_boss" value="<%=strTmp%>" >
						<%
						}
						%>
						</center>
					</layout:collectionItem>
 					<layout:collectionItem property="DATA05" style="TEXT-ALIGN: CENTER" name="bean1" title="加班單號" 
										   url="EHF020800M1.do?reqCode=queryForm" 
										   paramId="EHF020800T0_01,flow_sign_flag" 
										   paramProperty="DATA05,DATA20"
										   onclick="return showEMSWait();" />
<%--				<layout:collectionItem property="DATA02" style="TEXT-ALIGN: CENTER" title="申請日期" />
					<layout:collectionItem property="DATA03" style="TEXT-ALIGN: CENTER" title="申請人" />
					<layout:collectionItem property="DATA05" style="TEXT-ALIGN: CENTER" title="門禁日期"/>
					<layout:collectionItem property="DATA04" style="TEXT-ALIGN: LEFT" title="事由"/>
					<layout:collectionItem property="DATA06" style="TEXT-ALIGN: CENTER" title="申請日期/時間"/>
					<layout:collectionItem property="DATA07" style="TEXT-ALIGN: LEFT" title="申請時數"/>
					<layout:collectionItem property="DATA08" style="TEXT-ALIGN: LEFT" title="狀態"/> --%>
									
					<layout:collectionItem property="DATA01" style="TEXT-ALIGN: CENTER"  name="bean1" title="加班日期" />
					<layout:collectionItem property="DATA02" style="TEXT-ALIGN: CENTER"  name="bean1" title="填單人" />
					<layout:collectionItem property="DATA03" style="TEXT-ALIGN: CENTER"  name="bean1" title="加班部門" />
					<layout:collectionItem property="DATA04" style="TEXT-ALIGN: CENTER"  name="bean1" title="處理狀態" />
 		
					<layout:nestedCollection property="DATALIST" id="bean2" indexId="index2" >
						<layout:collectionItem property="EHF020800T1_04" title="員工"  name="bean2" style="TEXT-ALIGN: CENTER" />
						<layout:collectionItem property="EHF020800T1_06" title="加班時間"  name="bean2" style="TEXT-ALIGN: CENTER" />
					</layout:nestedCollection>
				</layout:collection>
				
			</logic:equal>
									
		</logic:equal>
		
	</logic:equal>
	
	<layout:popup styleId="comment" styleClass="DATAS" key="簽核意見">
		<layout:text styleClass="DATAS" property="DATA01" key="內容" size="30" maxlength="40" name="Form1Datas"  mode="E,E,I" />
		<layout:row >				
			<layout:submit value="送出" reqCode="signForm" property="EMS_SIGNER"  ></layout:submit>
			<layout:button value="取消" onclick="closeStrutsLayoutPopup('comment');" />
		</layout:row>
	</layout:popup>
	
</layout:form>