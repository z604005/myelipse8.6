<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>


<layout:form action="SC004.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="子系統代碼${action}" focus="SC0040_01">
	<layout:row>
		<layout:image alt="查詢" mode="F,F,F" reqCode="queryForm" name="btnimage?text=button.query&type=t" property="SC004" policy="query" disabledName="btnimage?text=button.query&type=f"></layout:image>
		<layout:image alt="新增" mode="F,F,F" reqCode="addDataForm" name="btnimage?text=button.add&type=t" property="SC004" policy="add" disabledName="btnimage?text=button.add&type=f"></layout:image>
		<layout:image alt="修改" mode="F,F,F" reqCode="editDataForm" name="btnimage?text=button.update&type=t" property="SC004" policy="modify" disabledName="btnimage?text=button.update&type=f"></layout:image>
		<layout:image alt="取消" mode="D,D,D" reqCode="cancel" name="btnimage?text=button.cancel&type=t" property="SC004" policy="all" disabledName="btnimage?text=button.cancel&type=f"></layout:image>
		<layout:image alt="執行" mode="D,D,D" reqCode="${BUTTON_TYPE}" name="btnimage?text=button.execute&type=t" property="SC004" policy="all" disabledName="btnimage?text=button.execute&type=f"></layout:image>
		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
			<layout:image alt="刪除" mode="H,D,D" reqCode="delData" name="btnimage?text=button.delete&type=t" property="SC004" policy="del" disabledName="btnimage?text=button.delete&type=f" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</logic:notEqual>
	</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text tooltip="◎子系統代碼" key="子系統代碼:" name="Form1Datas" property="SC0040_01" styleId="SC0040_01" styleClass="DATAS" mode="${Mode}" onkeydown="nextFiled()"/>
		<layout:text tooltip="◎子系統名稱" key="子系統名稱:" name="Form1Datas" property="SC0040_02" styleId="SC0040_02" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		<layout:text key="排列序號" name="Form1Datas" property="SC0040_04" styleId="SC0040_04" styleClass="DATAS" mode="E,E,I" />
		<layout:text tooltip="備註" key="備註" name="Form1Datas" property="SC0040_03" styleId="SC0040_03" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		<logic:notEqual name="Form1Datas" property="SC0040_01" value="">
			<layout:text styleClass="DATAS" key="版本編號" property="VERSION" styleId="VERSION" name="Form1Datas" tooltip="版本編號" mode="I,I,I" />
		</logic:notEqual>
	</layout:grid>
	<layout:space />
	<logic:notEqual name="Form1Datas" property="SC0040_01" value="">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text key="資料建立日期" styleClass="LOGDATA" property="DATE_CREATE" styleId="DATE_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode="I,I,I" />
		</layout:grid>
	</logic:notEqual>

</layout:form>
