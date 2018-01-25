<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.hr.forms.EHF010112M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<script>

function chkApplyDeptID(){
	if(document.getElementById("HR_DepartmentSysNo").value == "" || document.getElementById("HR_DepartmentSysNo").value == null ){
		alert("請先選擇部門!!");
		return false;
	}else{
		return true;
	}
}
function chkprint(){
	
	if(confirm('您確定要列印資料嗎?')){
		//啟動讀取動畫
		showEMSWait();
		fbutton('print'); 
	}
	
	return false;
}
</script>

<layout:form action="EHF010112M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="員工生日清冊">

	<layout:row>
<%--		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=列印&type=t" property="PHF020501M0" --%>
<%--					  reqCode="print2" alert="報表列印中!! 請勿重複執行!!" ></layout:image>--%>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF010112M0" 
					  reqCode="print" onclick="return chkprint();return false;" ></layout:image>
		<logic:notEqual name="DisplayFileName" value="" >
			<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" ></layout:image>
		</logic:notEqual>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		
<%--		<logic:equal name="boss" value="yes">--%>
			
			<layout:text styleClass="DATAS" key="部門名稱" property="HR_DepartmentSysNo" styleId="HR_DepartmentSysNo" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" >				
				<sp:lov 	form="EHF010111M0F" 
							id="HR_DepartmentSysNo,HR_DepartmentName" 
							lovField="HR_DepartmentSysNo,HR_DepartmentNo,HR_DepartmentName,HR_DepartmentLevel" 
							table="EHF010108T0"
							fieldAlias="系統代號,部門代號,部門名稱,層級" 
							fieldName="HR_DepartmentSysNo,HR_DepartmentNo,HR_DepartmentName,HR_DepartmentLevel"									
							others=" AND HR_CompanySysNo = '${compid}' "
							/>
				<layout:text layout="false" property="HR_DepartmentName" styleId="HR_DepartmentName" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
				<logic:equal name="person_manager" value="yes">
				<%-- 人事用的取得所有部門POPUP視窗	--%>
				
				</logic:equal>
				<logic:equal name="person_manager" value="no">
				<%-- 主管用的取得轄下部門POPUP視窗	--%>
				
				</logic:equal>
			</layout:text>			
		
			<layout:text styleClass="DATAS" key="員工名稱" property="HR_EmployeeSysNo" styleId="HR_EmployeeSysNo" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	form="EHF010111M0F" 
							id="HR_EmployeeSysNo,EHF010106T0_04" 
							lovField="HR_EmployeeSysNo,HR_EmployeeNo,EHF010106T0_04" 
							table="EHF010106T0"
							fieldAlias="系統代號,員工工號,姓名" 
							fieldName="HR_EmployeeSysNo,HR_EmployeeNo,EHF010106T0_04"									
							others=" AND HR_CompanySysNo = '${compid}' "
							beforerun="chkApplyDeptID()"/>
				<layout:text layout="false" property="EHF010106T0_04" styleId="EHF010106T0_04" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			</layout:text>
			
<%--		</logic:equal>				--%>
		
	</layout:grid>
	
	<logic:equal name="collection" value="show">
	
		<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
		
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
		
			<layout:collection emptyKey="沒有資料列"  name="Form1Datas" property="EHF010112M0_LIST" selectId="" selectProperty="" selectName=""  
							   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
				<layout:collectionItem property="HR_DepartmentName" style="TEXT-ALIGN: CENTER" title="部門名稱" />
				<layout:collectionItem property="HR_JobName" style="TEXT-ALIGN: CENTER" title="員工職稱" />				
				<layout:collectionItem property="HR_EmployeeNo" style="TEXT-ALIGN: CENTER" title="員工工號" />
				<layout:collectionItem property="EHF010106T0_04" style="TEXT-ALIGN: CENTER" title="員工姓名" />
				<layout:collectionItem property="EHF010106T0_10" style="TEXT-ALIGN: CENTER" title="出生日期"/>						
			</layout:collection>
		
		</layout:pager>
		
	</logic:equal>
		
</layout:form>