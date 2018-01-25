<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.com.forms.EHF000200M0F" %>
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

</script>

<layout:form action="EHF000200M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="部門基本資料">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF000200M1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF000200M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF000200M1" ></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF000200M1" ></layout:image>
    </layout:row>

	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:text styleClass="DATAS" property="EHF000200T0_01" styleId="EHF000200T0_01" size="20" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="部門代碼" property="EHF000200T0_02" styleId="EHF000200T0_02" size="20" mode="E,E,I" maxlength="200" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="部門名稱" property="EHF000200T0_03" styleId="EHF000200T0_03" size="20" mode="E,E,I" maxlength="200" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="上層部門" property="EHF000200T0_04" styleId="EHF000200T0_04" size="20" mode="H,H,I" maxlength="200" name="Form1Datas" />				
		
		<layout:text styleClass="DATAS" key="上層部門" property="EHF000200T0_04_1" styleId="EHF000200T0_04_1" size="20" mode="E,E,I" maxlength="200" name="Form1Datas" >				
			<sp:lov 	form="EHF000200M0F" 
						id="EHF000200T0_04,EHF000200T0_04_1,EHF000200T0_05" 
						lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03,EHF000200T0_06" 
						table="EHF000200T0"
						fieldAlias="系統代號,部門名稱,部門代號,層級" 
						fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03,EHF000200T0_06"									
						others=" AND HR_CompanySysNo = '${compid}' "
						mode="E,F,F"
			/>
			<layout:text layout="false" property="EHF000200T0_05" styleId="EHF000200T0_05" size="20" mode="R,R,R" maxlength="200" name="Form1Datas" />
		</layout:text>
		<layout:text styleClass="DATAS" key="部門簡稱" property="EHF000200T0_07" styleId="EHF000200T0_07" size="20" mode="E,E,I" maxlength="20" name="Form1Datas" />
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="成立日期" size="10"  name="Form1Datas" property="EHF000200T0_08" styleClass="DATAS"/>
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="結束日期" size="10"  name="Form1Datas" property="EHF000200T0_09" styleClass="DATAS"/>
		<layout:text styleClass="DATAS" key="" property="HR_CompanySysNo" styleId="HR_CompanySysNo" mode="H,H,H" name="Form1Datas" />
		
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		<layout:text styleClass="DATAS" key="部門簡介" property="EHF000200T0_10" styleId="EHF000200T0_10" size="50" mode="E,E,I" maxlength="50" name="Form1Datas" />
	</layout:grid>
<%--	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >--%>
<%--		<layout:text styleClass="DATAS" key="下層部門" property="HR_UpDepartmentSysNo" styleId="HR_UpDepartmentSysNo" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" >				--%>
<%--			<sp:lov 	form="EHF010106M0F" --%>
<%--							id="HR_DepartmentSysNo,HR_DepartmentName" --%>
<%--							lovField="HR_DepartmentSysNo,HR_DepartmentNo,HR_DepartmentName,HR_DepartmentLevel" --%>
<%--							table="EHF010108T0"--%>
<%--							fieldAlias="系統代號,部門代號,部門名稱,層級" --%>
<%--							fieldName="HR_DepartmentSysNo,HR_DepartmentNo,HR_DepartmentName,HR_DepartmentLevel"									--%>
<%--							others=" AND HR_CompanySysNo = '${compid}' "--%>
<%--				 			/>--%>
<%--			<layout:text layout="false" property="HR_UpDepartmentName" styleId="HR_UpDepartmentName" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />--%>
<%--		</layout:text>--%>
<%--		<layout:cell styleClass="DATAS">--%>
<%--			<layout:image alt="新增下層部門" mode="D,D,D" name="btnimage?text=button.add.detail&type=t" property="EHF010108M1" --%>
<%--							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF010108T1');" ></layout:image>--%>
<%--		</layout:cell>--%>
<%--	</layout:grid>--%>
	<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF000200T0_LIST" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
<%--		<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" --%>
<%--			paramId="HR_DepartmentSysNo,HR_DepartmentNo,HR_UpDepartmentSysNo" --%>
<%--			paramProperty="HR_DepartmentSysNo,HR_DepartmentNo,HR_UpDepartmentSysNo"--%>
<%--			url="EHF010108M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF010108T0" --%>
<%--			onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>					--%>
		<layout:collectionItem property="EHF000200T0_02" title="下層部門代號" style="text-align: center" />
		<layout:collectionItem property="EHF000200T0_03" title="下層部門名稱" style="text-align: center" />
	</layout:collection>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="最後異動日期" styleClass="LOGDATA" property="HR_LastUpdateDate" name="Form1Datas" mode= "I,I,I" />
	</layout:grid>

</layout:form>