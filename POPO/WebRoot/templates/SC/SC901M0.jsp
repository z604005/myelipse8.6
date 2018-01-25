<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<layout:form action="SC901.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="密碼修改作業" focus="OLD_PASSWORD">
	<layout:row>
		<layout:image mode="D,D,D" reqCode="saveData" name="btnimage?text=button.execute&type=t" property="SC901" policy="modify" disabledName="btnimage?text=button.execute&type=f">執行</layout:image>
	</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:password isRequired="ture" key="舊密碼　:" name="Form1Datas" property="OLD_PASSWORD" styleId="OLD_PASSWORD" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />		
		<layout:password isRequired="ture" key="新密碼　:" name="Form1Datas" property="NEW_PASSWORD" styleId="NEW_PASSWORD" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		<layout:password isRequired="ture" key="確認密碼:" name="Form1Datas" property="NEW_PASSWORD_CHK" styleId="NEW_PASSWORD_CHK" styleClass="DATAS" mode="E,E,I"  />
	</layout:grid>
</layout:form>
