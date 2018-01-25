<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template" %>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout" %>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
		
    <layout:form action="FU001.do" reqCode=""  width="100%" styleClass="FORM" method="post" key="Excel上傳樣板" enctype="multipart/form-data" >
		<layout:row>
		<layout:image alt="上傳" name="btnimage?text=執行&type=t" disabledName="btnimage?text=執行&type=f" policy="all" mode="D,D,F" reqCode="upload" ></layout:image>
		</layout:row>
		<layout:row>
		    <layout:message styleClass="MESSAGE_ERROR" key="${MSG}"/>
   		</layout:row>
    	<layout:row>
				<layout:file key="檔案" fileKey="FILE1" property="FILE1" styleClass="FORM" ></layout:file>
		</layout:row>
		<layout:column>
		    	${BODY}
   		</layout:column>
	</layout:form>
