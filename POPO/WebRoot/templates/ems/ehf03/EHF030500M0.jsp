<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.salary.forms.EHF030500M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<jsp:include page="/templates/begin.jsp"></jsp:include>

<script>

function chkApplyDeptID(){
	if(document.getElementById("EHF030500T0_03").value == "" || document.getElementById("EHF030500T0_03").value == null ){
		alert("請先選擇員工的部門!!");
		return false;
	}else{
		
		
			return true;
		
	}
}



//清除員工所有欄位
function cls2(){
	document.getElementById("EHF030500T0_02_UID").value = "";
	document.getElementById("EHF030500T0_02").value = "";
	document.getElementById("EHF030500T0_02_TXT").value = "";

	return true;
}
</script>

<layout:form action="EHF030500M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="其他補扣款">
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" 		name="btnimage?text=button.query&type=t"  	reqCode="queryForm" property="EHF030500M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" 		name="btnimage?text=button.add&type=t"  	reqCode="addForm" property="EHF030500M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
<%--			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=儲存&type=t"  reqCode="saveData" property="EHF030500M0" ></layout:image>--%>
<%--			<layout:image alt="回查詢畫面" mode="D,D,D" name="btnimage?text=回查詢畫面&type=t"  reqCode="init" property="EHF030500M0" ></layout:image>--%>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改明細" mode="H,D,H" 	name="btnimage?text=button.update&type=t"  reqCode="editForm" property="EHF030500M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" 		name="btnimage?text=button.delete&type=t"   reqCode="delData" property="EHF030500M0"  onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
		</logic:equal>
		<layout:image alt="補扣款匯入" mode="D,D,D" name="btnimage?text=button.import.other.fee&type=t" onclick="openStrutsLayoutPopup('fileimport'); return false;"  ></layout:image>
		<layout:image alt="範例檔下載" mode="D,D,D" name="btnimage?text=button.download.example&type=t" property="EHF030500M0" reqCode="print_example" confirm="您確定要下載資料嗎?" ></layout:image>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="3" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,I"  tooltip="◎薪資年月" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF030500T0_05" 
				     key="薪資年月" name="Form1Datas" styleClass="DATAS"  onkeydown="nextFiled()" />
		<layout:select key="放扣款類別" name="Form1Datas" property="EHF030500T0_06" styleId="EHF030500T0_06" styleClass="DATAS" mode="E,E,I" >
				<layout:options collection="listEHF030500T0_06" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:cell styleClass="DATAS" ></layout:cell>
		
		
		
		<layout:text styleClass="DATAS" key="部門系統代碼" property="EHF030500T0_03_UID" styleId="EHF030500T0_03_UID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF030500T0_03" styleId="EHF030500T0_03" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF030500M0F" 
							id="EHF030500T0_03_UID,EHF030500T0_03,EHF030500T0_03_TXT" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
							beforerun="cls2()"
							/>
				<layout:text layout="false" property="EHF030500T0_03_TXT" styleId="EHF030500T0_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		
		
<%--		<layout:text styleClass="DATAS" tooltip="部門代號" key="部門代號" property="EHF030500T0_03" styleId="EHF030500T0_03" size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >--%>
<%--			<sp:emspopup function="dep" id="EHF030500T0_03,EHF030500T0_03_TXT" clearfield="EHF030500T0_02,EHF030500T0_02_TXT"></sp:emspopup>--%>
<%--			<layout:text layout="false" property="EHF030500T0_03_TXT" styleId="EHF030500T0_03_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />--%>
<%--		</layout:text>--%>
		
<%--		<layout:text property="EHF030500T0_19" styleId="EHF030500T0_19" mode="I,I,I" styleClass="DATAS" key="員工在職狀況" size="12" name="Form1Datas" maxlength="16" isRequired="true" >--%>
<%--			<layout:checkbox styleClass="DATAS" name="Form1Datas" key="在職員工"	property="EHF030500T0_20" styleId="EHF030500T0_20" value="0" mode="E,E,I" layout="false" />在職員工--%>
<%--			<layout:checkbox styleClass="DATAS" name="Form1Datas" key="離職員工"	property="EHF030500T0_21" styleId="EHF030500T0_21" value="0" mode="E,E,I" layout="false" />離職員工--%>
<%--			<layout:checkbox styleClass="DATAS" name="Form1Datas" key="留職停薪"	property="EHF030500T0_22" styleId="EHF030500T0_22" value="0" mode="E,E,I" layout="false" />留職停薪員工--%>
<%--		</layout:text>--%>
		
					
<%--		<layout:text styleClass="DATAS" tooltip="員工工號" key="員工工號" property="EHF030500T0_02" styleId="EHF030500T0_02" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >--%>
<%--			<sp:emspopup function="samedepemp" id="EHF030500T0_02,EHF030500T0_02_TXT" depkey="window.EHF030500M0F.EHF030500T0_03.value" --%>
<%--						 emptype = "window.EHF030500M0F.EHF030500T0_20.value+window.EHF030500M0F.EHF030500T0_21.value+window.EHF030500M0F.EHF030500T0_22.value"--%>
<%--					     beforerun="chkApplyDeptID()" ></sp:emspopup>--%>
<%--			<layout:text layout="false" property="EHF030500T0_02_TXT" styleId="EHF030500T0_02_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />--%>
<%--		</layout:text>--%>
		
		
		<layout:text styleClass="DATAS" key="員工系統代碼" property="EHF030500T0_02_UID" styleId="EHF030500T0_02_UID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF030500T0_02" styleId="EHF030500T0_02" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF030500M0F" 
							id="EHF030500T0_02_UID,EHF030500T0_02,EHF030500T0_02_TXT" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EHF020404M0F.EHF030500T0_02_UID.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "   
							beforerun="chkDeptID()"	/>	
									
				<layout:text layout="false" property="EHF030500T0_02_TXT" styleId="EHF030500T0_02_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		
		
<%--		
		<layout:select key="員工類別" name="Form1Datas" property="EHF030500T0_04" styleClass="DATAS" mode="E,E,I" >
				<layout:options collection="listEHF030500T0_04" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:select key="發放類別" name="Form1Datas" property="EHF030500T0_07" styleClass="DATAS" mode="E,E,I" >
				<layout:options collection="listEHF030500T0_07" property="item_id" labelProperty="item_value" />
		</layout:select>
		--%>
		
		
		<logic:notEqual name="button" value="edit" >
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
		</logic:equal>
		
	
	</layout:grid>

	
	<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
		String strTmp = "";
		
	%>
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="300" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				EHF030500M0F FORM=(EHF030500M0F)list.get(i);
				strTmp = String.valueOf(FORM.getEHF030500T0_01());  //轉換 int to String
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>

		<layout:collectionItem property="EHF030500T0_05" style="TEXT-ALIGN: CENTER" title="薪資年月" />
		<layout:collectionItem property="EHF030500T0_03" style="TEXT-ALIGN: CENTER" title="部門/員工" />
		<%--<layout:collectionItem property="EHF030500T0_07" style="TEXT-ALIGN: CENTER" title="發放類別" />--%>
		<layout:collectionItem property="EHF030500T0_08" style="TEXT-ALIGN: CENTER" title="金額" />
		<layout:collectionItem property="USER_UPDATE" style="TEXT-ALIGN: CENTER" title="最後異動人員" />
		<layout:collectionItem property="DATE_UPDATE" style="TEXT-ALIGN: CENTER" title="最後異動日期" />
	</layout:collection>
	</layout:pager>
	</logic:equal>
	
	<layout:popup styleId="fileimport" styleClass="DATAS" key="檔案匯入">
		<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF030500"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup> 

</layout:form>

<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />