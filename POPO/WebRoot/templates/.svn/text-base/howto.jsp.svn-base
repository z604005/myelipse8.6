<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>


	<%
	ArrayList list=(ArrayList)request.getAttribute("Form1Datas");
	String aa = (String) request.getAttribute("PagerMessage");
		if(list.size()>0)
		{
			request.getSession().setAttribute("msg",list);
			request.getSession().setAttribute("pamsg",aa);
	%>
	<script>
		window.open("index_warehouse.jsp?","MSG","scrollbars=no,center=yes,border=thin,help=no,status=no,top=50,left=280,width=1450,height=450")
	</script>
	<%
		}
	%>
	
	<style type="text/css">
	.body{
	<%--	background-color: rgba(123, 123, 123, 0.9);--%>
	<%--	border: 5px double rgba(255, 255, 255, 0.8);--%>
		background-image: url(config/bg_welcome.jpg);
		background-repeat: no-repeat;
	    background-size: cover;
		background-position: center;
		-moz-border-radius: 1em;
<%--		border-radius: 1em;--%>
<%--		-webkit-border-radius: 1em;--%>
		width: 100%;
		height: 80%;
	<%--	float: right;--%>
<%--		margin-top: 1em;--%>
<%--		margin-left: 1em;--%>
<%--		margin-right: 3em;--%>
<%--		margin-bottom: 5em;--%>
<%--		padding: 1em;--%>
<%--		position: absolute;--%>
<%--		bottom: 0px;--%>
<%--		right: 0px;--%>
	}
	</style>
	
	<div class="body"></div>
	
<%--	<img src="config/sPOS.jpg" />--%>
		
