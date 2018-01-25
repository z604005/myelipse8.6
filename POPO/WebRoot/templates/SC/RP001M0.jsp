<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template" %>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

    <layout:form action="RP001.do" reqCode=""  width="100%" styleClass="FORM" method="post" key="程式清單"  >
		<layout:row>
		<layout:image alt="列印" name="btnimage?text=執行&type=t" disabledName="btnimage?text=執行&type=f" policy="all" mode="D,D,F" reqCode="print" ></layout:image>
		
		</layout:row>
		<layout:row>
		    <layout:message styleClass="MESSAGE_ERROR" key="${MSG}"/>
   		</layout:row>
    	<layout:row>
				<layout:select   key="報表型態"  name="Form1Datas" property="RPT_TYPE"  styleClass="FORM" mode="E,E,I" onkeydown="" >
					<layout:options collection="listRPT_TYPE" property="item_id" labelProperty="item_value"/>
				</layout:select>
		</layout:row>
		
	</layout:form>
