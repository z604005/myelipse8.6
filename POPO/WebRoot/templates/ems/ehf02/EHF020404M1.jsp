<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.utils.struts.form.EMS_VIEWDATAF" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<script>


</script>

<!-- JavaScript -->
<script type="text/javascript">


function chkDeptID(){
	if(document.getElementById("DATA08").value == "" || document.getElementById("DATA08").value == null ){
		alert("請先選擇部門組織!!");
		return false;
	}else{
<%--		if(!document.getElementById("DATA16").checked && !document.getElementById("DATA17").checked &&!document.getElementById("DATA18").checked){--%>
<%--			alert("請先選擇員工在職狀況!!");--%>
<%--			return false;--%>
<%--		}else{--%>
<%--			if(document.getElementById("DATA16").checked){--%>
<%--				document.getElementById("DATA16").value="1";--%>
<%--			}else{--%>
<%--				document.getElementById("DATA16").value="0";--%>
<%--			}--%>
<%--		--%>
<%--		--%>
<%--			if(document.getElementById("DATA17").checked){--%>
<%--				document.getElementById("DATA17").value="2";--%>
<%--			}else{--%>
<%--				document.getElementById("DATA17").value="0";--%>
<%--			}--%>
<%--		--%>
<%--		--%>
<%--			if(document.getElementById("DATA18").checked){--%>
<%--				document.getElementById("DATA18").value="3";--%>
<%--			}else{--%>
<%--				document.getElementById("DATA18").value="0";--%>
<%--			}--%>
<%--		--%>
			return true;
<%--		}--%>
	}
}
<%--function setTime(){--%>
<%--	document.getElementById("DATA06").value = document.getElementById("DATA05").value;--%>
<%--	return true;--%>
<%--}--%>


function cls2(){
	document.getElementById("DATA07").value = "";
	document.getElementById("DATA03").value = "";
	document.getElementById("DATA04").value = "";

	return true;
}




</script>

<layout:form action="EHF020404M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="門禁記錄查詢" >
<%--	<input type="text" id="button" value="${buttonType}" />--%>
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020404M1" ></layout:image>
			
<%--			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=新增&type=t"  reqCode="addDataForm" property="EHF020401M1" ></layout:image>--%>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
		</logic:equal>
		<logic:equal name="button" value="query">
		</logic:equal>
<%--		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=回前作業&type=t"  reqCode="redirect" property="EHF020404M1" ></layout:image>--%>
	</layout:row>
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
					
		<%--	人資用的按鈕			--%>
		<logic:equal name="person_manager" value="yes">
			
			<layout:text styleClass="DATAS" key="部門系統代碼" property="DATA08" styleId="DATA08" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="DATA01" styleId="DATA01" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA08,DATA02,DATA01" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
								beforerun="cls2()"
							/>
				<layout:text layout="false" property="DATA02" styleId="DATA02" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		
<%--			<layout:text property="DATA15" mode="I,I,I" styleClass="DATAS" key="員工在職狀況" size="12" name="Form1Datas" maxlength="16" isRequired="true" >--%>
<%--				<layout:checkbox styleClass="DATAS" name="Form1Datas" key="在職員工"	property="DATA16" value="0" mode="E,E,I" layout="false" />在職員工--%>
<%--				<layout:checkbox styleClass="DATAS" name="Form1Datas" key="離職員工"	property="DATA17" value="0" mode="E,E,I" layout="false" />離職員工--%>
<%--				<layout:checkbox styleClass="DATAS" name="Form1Datas" key="留職停薪"	property="DATA18" value="0" mode="E,E,I" layout="false" />留職停薪員工--%>
<%--			</layout:text>--%>						
			
			<layout:text styleClass="DATAS" key="員工系統代碼" property="DATA07" styleId="DATA07" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA03" styleId="DATA03" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA07,DATA03,DATA04" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EMS_VIEWDATAF.DATA08.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "   
							beforerun="chkDeptID()"	/>	
									
				<layout:text layout="false" property="DATA04" styleId="DATA04" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
		</logic:equal>
		
		<%--	會計用的按鈕			--%>
		<logic:equal name="accounting" value="yes">
			
			<layout:text styleClass="DATAS" key="部門系統代碼" property="DATA08" styleId="DATA08" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="DATA01" styleId="DATA01"  size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="DATA02" styleId="DATA02" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		
			<layout:text styleClass="DATAS" key="員工系統代碼" property="DATA07" styleId="DATA07" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA03" styleId="DATA03" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="DATA04" styleId="DATA04" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
		</logic:equal>
		
		<%--	老闆用的按鈕			--%>
		<logic:equal name="boss_manager" value="yes">
			
			<layout:text styleClass="DATAS" key="部門系統代碼" property="DATA08" styleId="DATA08" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="DATA01" styleId="DATA01" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA08,DATA02,DATA01" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
								beforerun="cls2()"
							/>
				<layout:text layout="false" property="DATA02" styleId="DATA02" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
						
			<layout:text styleClass="DATAS" key="員工系統代碼" property="DATA07" styleId="DATA07" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA03" styleId="DATA03" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA07,DATA03,DATA04" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EMS_VIEWDATAF.DATA08.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "   
							beforerun="chkDeptID()"	/>	
									
				<layout:text layout="false" property="DATA04" styleId="DATA04" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
		</logic:equal>
		
		<%--	一般員工用的按鈕			--%>
		<logic:equal name="person" value="yes">
			<layout:text styleClass="DATAS" key="部門系統代碼" property="DATA08" styleId="DATA08" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="DATA01" styleId="DATA01"  size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="DATA02" styleId="DATA02" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		
			<layout:text styleClass="DATAS" key="員工系統代碼" property="DATA07" styleId="DATA07" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA03" styleId="DATA03" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="DATA04" styleId="DATA04" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		</logic:equal>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2012" endYear="2025"  key="日期區間(起):" name="Form1Datas" property="DATA05" styleClass="DATAS"  />
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2012" endYear="2025"  key="日期區間(迄):" name="Form1Datas" property="DATA06" styleClass="DATAS"  />
	</layout:grid>
	
	
	<logic:equal name="collection" value="show">
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
	%>
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem property="DATA10" style="TEXT-ALIGN: CENTER" title="日期" />
		<layout:collectionItem property="DATA11" style="TEXT-ALIGN: CENTER" title="員工工號/姓名" />
<%--		<layout:collectionItem property="DATA13" style="TEXT-ALIGN: CENTER" title="狀態"/>--%>
		<layout:collectionItem property="DATA14" style="TEXT-ALIGN: CENTER" title="門禁刷卡時間"/>
<%--		<layout:collectionItem property="DATA15" style="TEXT-ALIGN: CENTER" title="門禁刷卡狀態"/>--%>
	</layout:collection>
	</layout:pager> 
	</logic:equal>
	
</layout:form>