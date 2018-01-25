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

function chkDeptID(){
	if(document.getElementById("DATA04").value == "" || document.getElementById("DATA04").value == null ){
		alert("請先選擇部門組織!!");
		return false;
	}else{
		return true;
	}
}

</script>

<layout:form action="EHF020501M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="請假月報表">

	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF020501M0"   reqCode="print_select" confirm="您確定要列印資料嗎?"  ></layout:image>
		<logic:notEqual name="DisplayFileName" value="" >
			<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" ></layout:image>
		</logic:notEqual>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
	

		<%--人資顯示頁面			--%>
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="DATA04" styleId="DATA04" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="部門" key="部門" property="DATA08" styleId="DATA08"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EMS_VIEWDATAF" 
				id="DATA04,DATA08,DATA12" 
				lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
				table="EHF000200T0"
				fieldAlias="系統代碼,部門代號,部門名稱" 
				fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
				others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
				mode="E,E,F"
			/>
			<layout:text layout="false" property="DATA12" styleId="DATA12" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA03" styleId="DATA03" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA06" styleId="DATA06" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EMS_VIEWDATAF" 
				id="DATA03,DATA06,DATA09" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
				parentId="EHF010100T6_02" 
				parentField="window.EMS_VIEWDATAF.DATA04.value" 
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
				beforerun="chkDeptID()"	
				mode="E,E,F"																
			/>
			<layout:text layout="false" property="DATA09" styleId="DATA09" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<%--人資顯示頁面			--%>
		
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,I" size="10" startYear="2010" endYear="2025"  key="日期區間" name="Form1Datas" property="DATA05" styleClass="DATAS"  >
		請輸入要列印的年份(只取年月)
		</layout:date>
		<layout:radios styleClass="DATAS" cols="3" tooltip="列印類別˙" key="列印類別" property="DATA07" value="01" mode="E,E,I" name="Form1Datas" >
			<layout:options collection="listDATA06" property="item_id" labelProperty="item_value" />
		</layout:radios>
	</layout:grid>
	
</layout:form>
<%--以下方法，在列印時，可以出現遮罩，目前未調整遮罩的CSS，因此先不用   Alvin--%>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />



<%--<jsp:include page="/templates/end.jsp"></jsp:include>--%>