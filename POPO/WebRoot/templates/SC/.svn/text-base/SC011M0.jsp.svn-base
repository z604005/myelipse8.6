<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

	<layout:form action="SC011.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="新增公司別資料" >
		<layout:row>
				<layout:row>
<%--					<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" reqCode="queryForm" property="SC011" policy="query"></layout:image>--%>
					<layout:image alt="新增公司" mode="D,D,D" name="btnimage?text=button.add.company&type=t" disabledName="btnimage?text=button.add.company&type=f" reqCode="addCompanyForm" property="SC011" policy="add"></layout:image>
		</layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text  key="公司代碼"  name="Form1Datas" property="DATA01" styleId="DATA01" tooltip="◎公司代碼"  mode= "E,E,I" styleClass="DATAS10" size="10" />
	</layout:grid>

		
	</layout:form>

