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
function chkDeptID(){
	if(document.getElementById("EHF020700T0_04").value == "" || document.getElementById("EHF020700T0_04").value == null ){
		alert("請先選擇部門組織!!");
		return false;
	}else{
		return true;
	}
}
function setTime(){
	//document.getElementById("EHF020700T0_08").value = document.getElementById("EHF020700T0_07").value;
	$("input[name='EHF020700T0_08']").val($("input[name='EHF020700T0_07']").val());
	return true;
}
</script>


<layout:form action="EHF020700M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="未打卡單申請">
	
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"
						  reqCode="queryForm" property="EHF020700M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"
						  reqCode="addDataForm" property="EHF020700M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">

		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t"  
						  reqCode="editDataForm" property="EHF020700M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF020700M0" ></layout:image>
		</logic:equal>
	</layout:row>
		

	<layout:row >
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>	
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<%--	人資用的按鈕			--%>
		<logic:equal name="person_manager" value="yes">
			
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020700T0_04" styleId="EHF020700T0_04" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020700T0_04_SHOW" styleId="EHF020700T0_04_SHOW" size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020700M0F" 
					id="EHF020700T0_04,EHF020700T0_04_SHOW,EHF020700T0_04_TXT" 
					lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
					table="EHF000200T0"
					fieldAlias="系統代碼,部門代號,部門名稱" 
					fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
					others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					/>
				<layout:text layout="false" property="EHF020700T0_04_TXT" styleId="EHF020700T0_04_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020700T0_03" styleId="EHF020700T0_03" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020700T0_03_SHOW" styleId="EHF020700T0_03_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
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
					beforerun="chkDeptID()"																	
					/>
				<layout:text layout="false" property="EHF020700T0_03_TXT" styleId="EHF020700T0_03_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			</layout:text>
			
		</logic:equal>
		
		<%--	會計用的按鈕			--%>
		<logic:equal name="accounting" value="yes">
			
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020700T0_04" styleId="EHF020700T0_04" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020700T0_04_SHOW" styleId="EHF020700T0_04_SHOW" size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020700M0F" 
					id="EHF020700T0_04,EHF020700T0_04_SHOW,EHF020700T0_04_TXT" 
					lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
					table="EHF000200T0"
					fieldAlias="系統代碼,部門代號,部門名稱" 
					fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
					others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					/>
				<layout:text layout="false" property="EHF020700T0_04_TXT" styleId="EHF020700T0_04_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020700T0_03" styleId="EHF020700T0_03" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020700T0_03_SHOW" styleId="EHF020700T0_03_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
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
					beforerun="chkDeptID()"																	
					/>
				<layout:text layout="false" property="EHF020700T0_03_TXT" styleId="EHF020700T0_03_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			</layout:text>
			
		</logic:equal>
		
		<%--	老闆用的按鈕			--%>
		<logic:equal name="boss_manager" value="yes">
			
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020700T0_04" styleId="EHF020700T0_04" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020700T0_04_SHOW" styleId="EHF020700T0_04_SHOW" size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020700M0F" 
					id="EHF020700T0_04,EHF020700T0_04_SHOW,EHF020700T0_04_TXT" 
					lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
					table="EHF000200T0"
					fieldAlias="系統代碼,部門代號,部門名稱" 
					fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
					others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					/>
				<layout:text layout="false" property="EHF020700T0_04_TXT" styleId="EHF020700T0_04_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020700T0_03" styleId="EHF020700T0_03" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020700T0_03_SHOW" styleId="EHF020700T0_03_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
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
					beforerun="chkDeptID()"																	
					/>
				<layout:text layout="false" property="EHF020700T0_03_TXT" styleId="EHF020700T0_03_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			</layout:text>
			
		</logic:equal>
				
		<%--	一般員工用的按鈕			--%>
		<logic:notEqual name="person_manager" value="yes">
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020700T0_04" styleId="EHF020700T0_04" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020700T0_04_SHOW" styleId="EHF020700T0_04_SHOW" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="EHF020700T0_04_TXT" styleId="EHF020700T0_04_TXT"size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020700T0_03" styleId="EHF020700T0_03" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020700T0_03_SHOW" styleId="EHF020700T0_03_SHOW" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" > 
				<layout:text layout="false" property="EHF020700T0_03_TXT" styleId="EHF020700T0_03_TXT"size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		</logic:notEqual>


		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2010" endYear="2025"  key="未打卡日期區間" 
					 name="Form1Datas" property="EHF020700T0_07" styleClass="DATAS" onchange="setTime()" >
			&nbsp;~&nbsp;
		
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  key="日期區間(迄)" 
			             name="Form1Datas" property="EHF020700T0_08" styleClass="DATAS" />
		</layout:date>
			
		<layout:select key="簽核狀態" name="Form1Datas" property="EHF020700T0_14" styleId="EHF020700T0_14" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="EHF020700T0_14_list" property="item_id" labelProperty="item_value" />
		</layout:select>
		
<%--		<layout:cell styleClass="DATAS" />--%>
		
	</layout:grid>
	
	<logic:equal name="collection" value="show" >
	
	<%
		int item_index = 0;
		EHF020700M0F ehf020700m0f =(EHF020700M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf020700m0f.getEHF020700C();
		String strTmp = "";
		
	%>
	<%
			//建立頁次session 
			//if(session.getAttribute("Pageid")!=null)
			session.setAttribute("Pageid","0");
	%>
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF020700C" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
						   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				EHF020700M0F FORM=(EHF020700M0F)list.get(i);
				strTmp = FORM.getEHF020700T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF020700T0_01" style="TEXT-ALIGN: CENTER" title="未打卡單號" />
		<layout:collectionItem property="EHF020700T0_07" style="TEXT-ALIGN: CENTER" title="申請日期" />
		<layout:collectionItem property="EHF020700T0_03_TXT" style="TEXT-ALIGN: CENTER" title="申請人" />
		<layout:collectionItem property="EHF020700T0_09" style="TEXT-ALIGN: CENTER" title="未打卡類別"/>
		<layout:collectionItem property="EHF020700T0_08" style="TEXT-ALIGN: CENTER" title="未打卡日期"/>
		<layout:collectionItem property="EHF020700T0_14_DESC" style="TEXT-ALIGN: LEFT" title="狀態"/>
	</layout:collection>
	</layout:pager>
			
	</logic:equal>
	
</layout:form>