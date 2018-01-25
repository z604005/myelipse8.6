<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout" %>
<% /** 左邊的選單列 **/ %>
    	<table width="100%" cellspacing="0">
    		<tr>
    			<td width="20" class="menutitle"><img border="0" src="templates/default/images/node.gif"/></td>
    			<td width="100%" class="menutitle">功能選單</td>
    		</tr>
    	</table>
		<layout:message styleClass="MENU" key="${menu}"/>