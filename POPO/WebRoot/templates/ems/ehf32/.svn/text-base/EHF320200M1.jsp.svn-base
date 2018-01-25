<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF320200M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JavaScript -->
<script type="text/javascript">

</script>

<layout:form action="EHF320200M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="上菜順序設定">

	<layout:row>
		<layout:image alt="確認新增" mode="D,H,H" name="btnimage?text=button.add.ok&type=t" reqCode="addDataForm" property="EHF320200M1" ></layout:image>
		<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF320200M1" ></layout:image>
<%--		<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=刪除&type=t" reqCode="delForm" property="EHF320200M1" policy="all"></layout:image>--%>
		<layout:image alt="回前一頁" mode="D,D,D" name="btnimage?text=button.Back&type=t" reqCode="redirect" property="EHF320200M1" ></layout:image>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>

					 
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:number key="*上菜順序(天)" property="EHF320200T1_01" styleId="EHF320200T1_01" mode="E,I,I" name="Form1Datas" styleClass="DATAS" 
					 size="7" maxlength="2" isRequired="true"/>
		<layout:number key="*上菜順序(項)" property="EHF320200T1_02" styleId="EHF320200T1_02" mode="E,I,I" name="Form1Datas" styleClass="DATAS" 
					 size="7" maxlength="2" isRequired="true"/>
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="*菜單名稱" property="EHF320200T1_03_TXT" mode="R,R,I" name="Form1Datas" styleClass="DATAS" 
					 size="16" maxlength="30" >
				 <sp:lov 
				 		form="EHF320200M0F" 
						id="EHF320100T0_01,EHF320200T1_03,EHF320200T1_03_TXT,EHF320100T0_04,EHF320100T0_05" 
						lovField="EHF320100T0_01,EHF320100T0_02,EHF320100T0_03,A.EMS_CategoryT1_05,B.EMS_CategoryT1_05" 
						table="EHF320100T0 LEFT JOIN EMS_CategoryT1 A ON A.EMS_CategoryT1_01= 'MENU_MEAL' AND A.EMS_CategoryT1_04 = EHF320100T0_04"
						fieldAlias="菜單系統編號,菜單編號,菜單名稱,菜單餐別,菜單類別" 
						fieldName="EHF320100T0_01,EHF320100T0_02,EHF320100T0_03,A.EMS_CategoryT1_05,B.EMS_CategoryT1_05"
						leftjoin=" LEFT JOIN EMS_CategoryT1 B ON B.EMS_CategoryT1_01= 'MENU_TYPE' AND B.EMS_CategoryT1_04 = EHF320100T0_05 "	
						mode="E,E,F"							
						others=" AND EHF320100T0_08 = '${compid}'"
						/> 
		</layout:text>
	</layout:grid>
	
	<layout:text key="菜單編號" property="EHF320200T1_03" styleId="EHF320200T1_03" mode="H,H,H" name="Form1Datas" styleClass="DATAS" 
		 		size="20" maxlength="20"/>	
	<layout:text key="菜單系統編號" property="EHF320100T0_01" styleId="EHF320100T0_01" mode="H,H,H" name="Form1Datas" styleClass="DATAS" 
		 		size="20" maxlength="20"/>	
		 		
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">	

		<layout:text key="菜單餐別" property="EHF320100T0_04" styleId="EHF320100T0_04" mode="R,R,R" name="Form1Datas" styleClass="DATAS" />

		<layout:text key="菜單類別" property="EHF320100T0_05" styleId="EHF320100T0_05" mode="R,R,R" name="Form1Datas" styleClass="DATAS" />
	</layout:grid>

	<layout:notMode value="create" >
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</layout:notMode>
</layout:form>