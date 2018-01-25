<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout" %>

<!DOCTYPE HTML>
<html>
<head>
	<layout:skin includeScript="true"/> 
	<title><layout:message key="system.name"/></title>
</head>

<style type="text/css">
.loginBG{
<%--	background: -moz-linear-gradient(top,  rgba(30,87,153,1) 0%, rgba(125,185,232,0) 100%); /* FF3.6+ */--%>
<%--	background: -webkit-gradient(linear, left top, left bottom, color-stop(0%,rgba(30,87,153,1)), color-stop(100%,rgba(125,185,232,0))); /* Chrome,Safari4+ */--%>
<%--	background: -webkit-linear-gradient(top,  rgba(30,87,153,1) 0%,rgba(125,185,232,0) 100%); /* Chrome10+,Safari5.1+ */--%>
<%--	background: -o-linear-gradient(top,  rgba(30,87,153,1) 0%,rgba(125,185,232,0) 100%); /* Opera 11.10+ */--%>
<%--	background: -ms-linear-gradient(top,  rgba(30,87,153,1) 0%,rgba(125,185,232,0) 100%); /* IE10+ */--%>
<%--	background: linear-gradient(to bottom,  rgba(30,87,153,1) 0%,rgba(125,185,232,0) 100%); /* W3C */--%>
<%--	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#1e5799', endColorstr='#007db9e8',GradientType=0 ); /* IE6-9 */--%>

	background-image: url(config/logo_login.png), url(config/bg_login.jpg);
    background-size: 40% auto, cover;
	background-position: 50% 15%, center;
	background-repeat: no-repeat;
    <%--    因IE8不支援background-size 所以要用-ms-filter宣告--%>
    -ms-filter: "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='path_relative_to_the_HTML_file', sizingMethod='scale')";
	background-attachment:fixed;
}

.body{
<%--	background-color: rgba(123, 123, 123, 0.9);--%>
<%--	border: 5px double rgba(255, 255, 255, 0.8);--%>
	background-image: url(config/box_login.png);
	background-repeat: no-repeat;
    background-size: 90%;
	background-position: center;
	-moz-border-radius: 1em;
	border-radius: 1em;
	-webkit-border-radius: 1em;
	width: 460px;
	height: 287px;
<%--	float: right;--%>
<%--	margin-top: 1em;--%>
<%--	margin-left: 1em;--%>
<%--	margin-right: 3em;--%>
<%--	margin-bottom: 5em;--%>
<%--	padding: 1em;--%>
	margin: 15% auto;
<%--	position: absolute;--%>
<%--	left: 35%;--%>
<%--	top: 30%;--%>
	
<%--	bottom: 0px;--%>
<%--	right: 0px;--%>
<%--	border: 5px double rgba(255, 255, 255, 0.8);--%>
}

.footer{
	background-image: url("config/SPON_LOGO.png"), url("config/footer.jpg");
    background-size: auto 100%, cover;
	background-position: 15%, center;
	background-repeat: no-repeat;
<%--	background-color: rgba(29, 124, 189, 0.8);--%>
	background-color: rgba(0, 0, 0, 1.0);
<%--	background-color: rgba(21, 65, 100, 0.9);--%>
<%--	border: 5px double rgba(255, 255, 255, 0.8);--%>
<%--	-moz-border-radius: 1em;--%>
<%--	border-radius: 1em;--%>
<%--	-webkit-border-radius: 1em;--%>
	width: 100%;
	height: 58px;
<%--	float: right;--%>
<%--	margin: 1em;--%>
<%--	padding: 1em;--%>
	position: absolute;
	bottom: 0px;
	right: 0px;
}

.copyright{
<%--	width:940px;--%>
	margin:0px auto;
	padding:5px 0px;
	font-size:12px;
	text-align:center;
	color:#FFFFFF;
	line-height:24px;
}

#compid, #userid, #password{
	width: 277px;
	height: 24px;
	border: 0px;
	background-color: transparent;
}

.form_text{
	margin-top: 1.5em;
	margin-left: 3.4em;
	line-height: 2.3em;;
}

.loginBTN {
<%--	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #c3c6c9), color-stop(1, #6392c2) );--%>
<%--	background:-moz-linear-gradient( center top, #c3c6c9 5%, #6392c2 100% );--%>
<%--	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#c3c6c9', endColorstr='#6392c2');--%>
	background-color:#6392c2;
	-webkit-border-top-left-radius:23px;
	-moz-border-radius-topleft:23px;
	border-top-left-radius:23px;
	-webkit-border-top-right-radius:23px;
	-moz-border-radius-topright:23px;
	border-top-right-radius:23px;
	-webkit-border-bottom-right-radius:23px;
	-moz-border-radius-bottomright:23px;
	border-bottom-right-radius:23px;
	-webkit-border-bottom-left-radius:23px;
	-moz-border-radius-bottomleft:23px;
	border-bottom-left-radius:23px;
	text-indent:0px;
	display:inline-block;
	color:#ffffff;
<%--	font-family:Arial;--%>
	font-size:15px;
	font-weight:bold;
	font-style:normal;
	height:30px;
	line-height:30px;
	width:100px;
	text-decoration:none;
	text-align:center;
	margin-top: 5px;
	margin-left: 320px;
}

.loginBTN:hover {
<%--	background:-webkit-gradient( linear, left top, left bottom, color-stop(0.05, #6392c2), color-stop(1, #c3c6c9) );--%>
<%--	background:-moz-linear-gradient( center top, #6392c2 5%, #c3c6c9 100% );--%>
<%--	filter:progid:DXImageTransform.Microsoft.gradient(startColorstr='#6392c2', endColorstr='#c3c6c9');--%>
	background-color:#c3c6c9;
}

.loginBTN:active {
	position:relative;
	top:1px;
}
<%--/* This button was generated using CSSButtonGenerator.com */--%>

</style>


<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery-1.8.1.min.js"></script>
<script language="javascript">
	$(document).ready(function(){
		$("#loginBTN").val($("#loginBTN_DESC").val());
	});
</script>

<%--  <body background="config/LoginBg.jpg">--%>
  <body class="loginBG">
<%--	<center>--%>
<%--	<layout:form action="/login" >--%>
<html:form action="/login.do" focus="userid">
	<div class="body">
<%--	<table>--%>
<%--		<tr>--%>
<%--			<td vAlign="top" width="" height="">--%>
<%--				<table  Class="login_mid_all" cellspacing="0" cellpadding="0">--%>
<%--					<tr>--%>
<%--						<td width="620" valign="top" align="left">--%>
							<div style="padding-top: 4em; padding-left: 2em;">
								<font color="black"><b>　<bean:message key="label.language"/>：</b></font>
								<html:link page="/Locale.do?reqCode=traditionalchinese"><img src="config/images/tw.gif" border="0"/></html:link>
<%--							  	<html:link page="/Locale.do?reqCode=simplechinese"><img src="config/images/cn.gif" border="0"/></html:link>--%>
<%--								<html:link page="/Locale.do?reqCode=english"><img src="config/images/us.gif" border="0"/></html:link>--%>
<%--							  	<html:link page="/Locale.do?reqCode=japanese"><img src="config/images/jp.gif" border="0"/></html:link>--%>
						  	</div>
<%--						</td>--%>
<%--						<td valign="top" width="244">--%>
<%--							<table cellpadding="8" cellspacing="0" height="130">--%>
<%--								<tr><td colspan="2" height="234">&nbsp;</td></tr>--%>
<%--								<tr>--%>
<%--									<td> --%>
									<div class="form_text">
										<font color="black"><b><bean:message key="label.company.no"/>　</b></font>
										<layout:text key="label.company.no" styleId="compid" property="compid" value="COM42760782" layout="false" />
										<br/>
										<font color="black"><b>　<bean:message key="label.id"/>　　</b></font>
										<layout:text key="label.id" styleId="userid" property="userid" value="" layout="false" />
										<br/>
										<font color="black"><b>　<bean:message key="label.password"/>　　</b></font>
										<layout:password key="label.password" styleId="password" property="password" layout="false" />
									</div>
<%--									</td>--%>
<%--								</tr>	--%>
<%--							</table>--%>
<%--						</td>--%>
<%--						<td>--%>
<%--							<table>--%>
<%--								<tr>--%>
<%--									<td>--%>
										<input type="hidden" id="loginBTN_DESC" value="<bean:message key="label.login"/>">
										<html:submit styleId="loginBTN" property="" styleClass="loginBTN"></html:submit>
<%--										<html:image src="config/Login-button.jpg" />--%>
<%--									</td>--%>
<%--								</tr>--%>
<%--							</table>--%>
<%--						</td>--%>
<%--					</tr>--%>
<%--				</table>--%>
<%--			</td>--%>
<%--		</tr>--%>
<%--	</table>--%>
	</div>
	<div class="footer">
		<div class="copyright">Copyright © 2017 SPON TECHNOLOGY CO., LTD. All rights reserved.　思邦科技股份有限公司  版權所有<br />
	  |　台中市北區進化北路238號14樓之3</div>
	</div>


<%--	<SPAN id=process></SPAN>--%>
<%--	</layout:form>--%>
</html:form>
<%--	</center>--%>
	</body>
</html>
