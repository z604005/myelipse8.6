<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.utils.struts.form.BA_REPORTF" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<jsp:include page="/templates/begin.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <% String path = ""; %>
	<% path = request.getContextPath(); %>
    
    <link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.css" />
	<script type="text/javascript" src="<%= request.getContextPath() %>/config/jquery-1.3.2.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.min.js"></script>
    
    <link rel="stylesheet" href="<%=path%>/config/default.css" type="text/css">
    <script type="text/javascript">var imgsrc="<%=path%>/config/"; var scriptsrc="<%=path%>/config/"; var langue="zh"; var contextPath="<%=path%>";</script>
    <script type="text/javascript" src="<%=path%>/config/javascript.js"></script>
	<script language="JavaScript" src="<%=path%>/config/swap.js"></script>
	<script type="text/javascript" src="/config/javascript.js"></script>
    
    <title>EMS</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
  </head>

<!-- JavaScript -->
<script type="text/javascript">

<%--window.onload = function(){--%>
<%--	$(document.body).mask("讀取中...");  //開啟畫面遮罩--%>
<%--}--%>
$('.button').click(function() {
    $.ajax({
        url: "",
        context: document.body,
        success: function(s,x){
	 
	        $('html[manifest=saveappoffline.appcache]').attr('content', '');
	 
	            $(this).html(s);
	    }
	});
});

//若以組織樹模式顯示，則需搭配此功能實作 "點選部門單位來帶回資料"
function onSelectTreeItem(obj){
	var returnfiled =  document.getElementById("id").value.split(",");
	var _obj = obj.split("/");
	var UID = _obj[1].split("~");
	
	parent.opener.document.getElementById(returnfiled[0]).value = _obj[0];
	//parent.opener.document.getElementById(returnfiled[1]).value = _obj[1];
	parent.opener.document.getElementById(returnfiled[1]).value = UID[0];
	parent.opener.document.getElementById(returnfiled[2]).value = UID[1];
	
	filedchangescript();
	//清除相依欄位資料
	clearfield();
	
	window.close();
}

function getEmpRowValue(obj){  //取得員工資料script
		
	var returnfiled =  document.getElementById("id").value.split(",");
	var page = document.getElementById("Page").value;

//	alert("index==>"+obj);

	var Pkey = (page * 10) + obj;
//	alert("Pkey==>"+Pkey);
	
	parent.opener.document.getElementById(returnfiled[0]).value = document.getElementById("EMSC["+Pkey+"].EMPLOYEE_ID").value;
	parent.opener.document.getElementById(returnfiled[1]).value = document.getElementById("EMSC["+Pkey+"].EMPLOYEE_NAME").value;
	
	filedchangescript();
	//清除相依欄位資料
	clearfield();
	
	window.close();	
}

function getDepRowValue(obj){  //取得部門資料script

	var returnfiled =  document.getElementById("id").value.split(",");
	var page = document.getElementById("Page").value;

//	alert("index==>"+obj);

	var Pkey = (page * 10) + obj;
//	alert("Pkey==>"+Pkey);
	
	parent.opener.document.getElementById(returnfiled[0]).value = document.getElementById("EMSC["+Pkey+"].DEPT_ID").value;
	parent.opener.document.getElementById(returnfiled[1]).value = document.getElementById("EMSC["+Pkey+"].SHOW_DESC").value;
	
	filedchangescript();
	//清除相依欄位資料
	clearfield();
	
	window.close();	
}

function getSameDepEmpRowValue(obj){  //取得同部門同仁資料script

	var returnfiled =  document.getElementById("id").value.split(",");
	var page = document.getElementById("Page").value;

//	alert("index==>"+obj);

	var Pkey = (page * 10) + obj;
//	alert("Pkey==>"+Pkey);
	
	parent.opener.document.getElementById(returnfiled[0]).value = document.getElementById("EMSC["+Pkey+"].EMPLOYEE_ID").value;
	parent.opener.document.getElementById(returnfiled[1]).value = document.getElementById("EMSC["+Pkey+"].EMPLOYEE_NAME").value;
	
	filedchangescript();
	//清除相依欄位資料
	clearfield();
	
	window.close();	
}

function getAgentRowValue(obj){  //取得代理人資料script

	var returnfiled =  document.getElementById("id").value.split(",");
	var page = document.getElementById("Page").value;

//	alert("index==>"+obj);

	var Pkey = (page * 10) + obj;
//	alert("Pkey==>"+Pkey);
	
	parent.opener.document.getElementById(returnfiled[0]).value = document.getElementById("EMSC["+Pkey+"].AGENT_EMP_NO").value;
	parent.opener.document.getElementById(returnfiled[1]).value = document.getElementById("EMSC["+Pkey+"].AGENT_EMP_NAME").value;
	
	filedchangescript();
	//清除相依欄位資料
	clearfield();
	
	window.close();	
}

function getQueryEmpRowValue(obj){

	var returnfiled =  document.getElementById("id").value.split(",");
	var page = document.getElementById("Page").value;

//	alert("index==>"+obj);

	var Pkey = (page * 10) + obj;
//	alert("Pkey==>"+Pkey);
	
	parent.opener.document.getElementById(returnfiled[0]).value = document.getElementById("EMSC["+Pkey+"].DEPT_ID").value;
	parent.opener.document.getElementById(returnfiled[1]).value = document.getElementById("EMSC["+Pkey+"].DEPT_NAME_C").value;
	
	filedchangescript();
	//清除相依欄位資料
	clearfield();
	
	window.close();	
}

function getDataEmpRowValue(obj){

	var returnfiled =  document.getElementById("id").value.split(",");
	var page = document.getElementById("Page").value;

//	alert("index==>"+obj);

	var Pkey = (page * 10) + obj;
//	alert("Pkey==>"+Pkey);
	
	parent.opener.document.getElementById(returnfiled[0]).value = document.getElementById("EMSC["+Pkey+"].AGENT_EMP_NO").value;
	parent.opener.document.getElementById(returnfiled[1]).value = document.getElementById("EMSC["+Pkey+"].AGENT_EMP_NO").value;
	
	filedchangescript();
	//清除相依欄位資料
	clearfield();
	
	window.close();	
}

function fbutton(reqCode) {
				BA_EMSForm.elements['reqCode'].value=reqCode;
				BA_EMSForm.submit();
		}

function filedchangescript(){  //啟動執行完成後的script
	if(document.getElementById("changescript").value != "" && document.getElementById("changescript").value != null ){
		eval("parent.opener."+document.getElementById("changescript").value);
	}	
}

function clearfield(){  //代入值後清除相關相依的欄位內容 by joe 2012/03/13
	
	if(document.getElementById("clearfield").value != "" && document.getElementById("clearfield").value != null ){
		var clearfiled =  document.getElementById("clearfield").value.split(",");
		//走訪需清除的欄位
		for(var key in clearfiled){
			if( parent.opener.document.getElementById(clearfiled[key]) != null ){
				parent.opener.document.getElementById(clearfiled[key]).value = "";  //將欄位設定為空值
			}
		}
	}
}


function closeEmspopup(){
	
	window.close();
	
}

function onChangeDepth(depth_){
	document.getElementById("depth").value = depth_;
	fbutton("queryDatas");
}
</script>
<body>

<form name="BA_EMSForm" method="post" action="EMS.do">	
	
	<input type="hidden" name="reqCode" value="" />
	<layout:text name="Form1Datas" property="id" styleId="id" mode="H,H,H"></layout:text>
	<layout:text name="Form1Datas" property="function" styleId="function" mode="H,H,H"></layout:text>
	<layout:text name="Form1Datas" property="depkey" styleId="depkey" mode="H,H,H"></layout:text>
	<layout:text name="Form1Datas" property="changescript" styleId="changescript" mode="H,H,H"></layout:text>
	<layout:text name="Form1Datas" property="clearfield" styleId="clearfield" mode="H,H,H"></layout:text>
	<layout:text name="Form1Datas" property="depth" styleId="depth" mode="H,H,H"></layout:text>
<%--	<layout:button value="openMask" onclick="return openMask();" />--%>
	
			<%
			//建立頁次session 
			if(session.getAttribute("Pageid")==null)
				session.setAttribute("Pageid","0");
			%>
	
	<%-- 人員資料  --%>
	<logic:equal name="Form1Datas" property="function" value="emp" >
		<layout:select key="" name="Form1Datas" property="CHKBUTTON" styleClass="DATAS" mode="E,E,I">
			<layout:options collection="CHKBUTTON_list" property="item_id" labelProperty="item_value" />
		</layout:select>
		<input type="text" name="search" id="search" value="" />
		<layout:button value="查詢" onclick="return fbutton('init');return false;"></layout:button>
			
			<!-- pager 是用來分頁的特殊 panel -->
		<input type="hidden" name="Page" value="${Pageid}" />
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection name="Form2Datas" emptyKey="沒有資料列" indexId="index" width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >								    
				<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">
					<layout:button value="選取" onclick="return getEmpRowValue(${index});" />
				</layout:collectionItem>
				<layout:collectionItem title="員工工號" style="TEXT-ALIGN: CENTER" property="EMPLOYEE_ID" />			
				<layout:collectionItem title="員工姓名" style="TEXT-ALIGN: CENTER" property="EMPLOYEE_NAME" />
			</layout:collection>
		</layout:pager>
		<div style="display: none">
			<layout:collection name="Form1Datas" property="EMSC" emptyKey="沒有資料列" indexId="index" width="95%" height="95%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
								    
<%--			<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">--%>
<%--				<layout:button value="選取" onclick="return getRowValue(${index});" />--%>
<%--			</layout:collectionItem>--%>
				<layout:collectionItem title="員工工號" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].EMPLOYEE_ID"	layout="false" mode="I,I,I" />			
				</layout:collectionItem>
				<layout:collectionItem title="員工姓名" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].EMPLOYEE_NAME"	layout="false" mode="I,I,I" />
				</layout:collectionItem>			
			</layout:collection>
		</div>
	</logic:equal>
	
	<%-- 部門資料  --%>
	<logic:equal name="Form1Datas" property="function" value="dep">
<%--		<layout:select key="" name="Form1Datas" property="CHKBUTTON" styleClass="DATAS" mode="E,E,I">--%>
<%--			<layout:options collection="CHKBUTTON_list" property="item_id" labelProperty="item_value" />--%>
<%--		</layout:select>--%>
<%--		<input type="text" name="search" id="search" value="" />--%>
<%--		<layout:button value="查詢" onclick="return fbutton('init');return false;"></layout:button>--%>
<%--		<layout:button value="測試" onclick="return test2();" />--%>

			<!-- pager 是用來分頁的特殊 panel -->
		<input type="hidden" name="Page" value="${Pageid}" />
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection name="Form2Datas" emptyKey="沒有資料列" indexId="index" width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >								    
				<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">
					<layout:button value="選取" onclick="return getDepRowValue(${index});" />
				</layout:collectionItem>
				<layout:collectionItem title="部門代號" style="TEXT-ALIGN: CENTER" property="DEPT_ID" />			
				<layout:collectionItem title="部門名稱" style="TEXT-ALIGN: CENTER" property="SHOW_DESC" />
			</layout:collection>
		</layout:pager>
		<div style="display: none">
			<layout:collection name="Form1Datas" property="EMSC" emptyKey="沒有資料列" indexId="index" width="95%" height="95%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
								    
<%--			<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">--%>
<%--				<layout:button value="選取" onclick="return getRowValue(${index});" />--%>
<%--			</layout:collectionItem>--%>
				<layout:collectionItem title="部門代號" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].DEPT_ID"	layout="false" mode="I,I,I" />			
				</layout:collectionItem>
				<layout:collectionItem title="部門名稱" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].SHOW_DESC"	layout="false" mode="I,I,I" />
				</layout:collectionItem>			
			</layout:collection>
		</div>
	
	</logic:equal>
	
		<%-- 同部門同仁資料  --%>
	<logic:equal name="Form1Datas" property="function" value="samedepemp">
<%--		<layout:select key="" name="Form1Datas" property="CHKBUTTON" styleClass="DATAS" mode="E,E,I">--%>
<%--			<layout:options collection="CHKBUTTON_list" property="item_id" labelProperty="item_value" />--%>
<%--		</layout:select>--%>
<%--		<input type="text" name="search" id="search" value="" />--%>
<%--		<layout:button value="查詢" onclick="return fbutton('init');return false;"></layout:button>--%>
<%--		<layout:button value="測試" onclick="return test2();" />--%>

			<!-- pager 是用來分頁的特殊 panel -->
		<input type="hidden" name="Page" value="${Pageid}" />
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection name="Form2Datas" emptyKey="沒有資料列" indexId="index" width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >								    
				<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">
					<layout:button value="選取" onclick="return getSameDepEmpRowValue(${index});" />
				</layout:collectionItem>
				<layout:collectionItem title="員工工號" style="TEXT-ALIGN: CENTER" property="EMPLOYEE_ID" />			
				<layout:collectionItem title="員工姓名" style="TEXT-ALIGN: CENTER" property="EMPLOYEE_NAME" />
			</layout:collection>
		</layout:pager>
		<div style="display: none">
			<layout:collection name="Form1Datas" property="EMSC" emptyKey="沒有資料列" indexId="index" width="95%" height="95%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
								    
<%--			<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">--%>
<%--				<layout:button value="選取" onclick="return getRowValue(${index});" />--%>
<%--			</layout:collectionItem>--%>
				<layout:collectionItem title="員工工號" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].EMPLOYEE_ID"	layout="false" mode="I,I,I" />			
				</layout:collectionItem>
				<layout:collectionItem title="員工姓名" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].EMPLOYEE_NAME"	layout="false" mode="I,I,I" />
				</layout:collectionItem>			
			</layout:collection>
		</div>
	
	</logic:equal>
	
	<%-- 申請人角色別資料  --%>
	<logic:equal name="Form1Datas" property="function" value="queryempdetp">
			<!-- pager 是用來分頁的特殊 panel -->
		<input type="hidden" name="Page" value="${Pageid}" />
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection name="Form2Datas" emptyKey="沒有資料列" indexId="index" width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >								    
				<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">
					<layout:button value="選取" onclick="return getQueryEmpRowValue(${index});" />
				</layout:collectionItem>
				<layout:collectionItem title="歸屬部門代碼" style="TEXT-ALIGN: CENTER" property="DEPT_ID" />			
				<layout:collectionItem title="歸屬部門名稱" style="TEXT-ALIGN: CENTER" property="DEPT_NAME_C" />
			</layout:collection>
		</layout:pager>
		<div style="display: none">
			<layout:collection name="Form1Datas" property="EMSC" emptyKey="沒有資料列" indexId="index" width="95%" height="95%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >	   
				<layout:collectionItem title="歸屬部門代碼" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].DEPT_ID"	layout="false" mode="I,I,I" />			
				</layout:collectionItem>
				<layout:collectionItem title="歸屬部門名稱" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].DEPT_NAME_C"	layout="false" mode="I,I,I" />
				</layout:collectionItem>			
			</layout:collection>
		</div>
	</logic:equal>
	
	<%-- 代理人資料  --%>
	<logic:equal name="Form1Datas" property="function" value="dbed">
			<!-- pager 是用來分頁的特殊 panel -->
		<input type="hidden" name="Page" value="${Pageid}" />
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection name="Form2Datas" emptyKey="沒有資料列" indexId="index" width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >								    
				<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">
					<layout:button value="選取" onclick="return getDataEmpRowValue(${index});" />
				</layout:collectionItem>
				<layout:collectionItem title="代理人員工工號" style="TEXT-ALIGN: CENTER" property="AGENT_EMP_NO" />			
				<layout:collectionItem title="代理人員工姓名" style="TEXT-ALIGN: CENTER" property="AGENT_EMP_NO" />
			</layout:collection>
		</layout:pager>
		<div style="display: none">
			<layout:collection name="Form1Datas" property="EMSC" emptyKey="沒有資料列" indexId="index" width="95%" height="95%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >	   
				<layout:collectionItem title="代理人員工工號" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].AGENT_EMP_NO"	layout="false" mode="I,I,I" />			
				</layout:collectionItem>
				<layout:collectionItem title="代理人員工姓名" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].AGENT_EMP_NO"	layout="false" mode="I,I,I" />
				</layout:collectionItem>			
			</layout:collection>
		</div>
	</logic:equal>
	
	<%-- 代理人資料  create by joe 99/10/25  --%>
	<logic:equal name="Form1Datas" property="function" value="agents">

			<!-- pager 是用來分頁的特殊 panel -->
		<input type="hidden" name="Page" value="${Pageid}" />
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection name="Form2Datas" emptyKey="沒有資料列" indexId="index" width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >								    
				<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">
					<layout:button value="選取" onclick="return getAgentRowValue(${index});" />
				</layout:collectionItem>
				<layout:collectionItem title="代理人工號" style="TEXT-ALIGN: CENTER" property="AGENT_EMP_NO" />			
				<layout:collectionItem title="代理人姓名" style="TEXT-ALIGN: CENTER" property="AGENT_EMP_NAME" />
				<layout:collectionItem title="代理順位" style="TEXT-ALIGN: CENTER" property="AGENT_SEQ" />
			</layout:collection>
		</layout:pager>
		<div style="display: none">
			<layout:collection name="Form1Datas" property="EMSC" emptyKey="沒有資料列" indexId="index" width="95%" height="95%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
								    
				<layout:collectionItem title="代理人工號" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].AGENT_EMP_NO"	layout="false" mode="I,I,I" />			
				</layout:collectionItem>
				<layout:collectionItem title="代理人姓名" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].AGENT_EMP_NAME"	layout="false" mode="I,I,I" />
				</layout:collectionItem>
				<layout:collectionItem title="代理順位" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].AGENT_SEQ"	layout="false" mode="I,I,I" />
				</layout:collectionItem>			
			</layout:collection>
		</div>
	
	</logic:equal>
	
	<%-- 主管轄下部門資料  create by joe 99/11/01  --%>
	<logic:equal name="Form1Datas" property="function" value="bossunderdep">

			<!-- pager 是用來分頁的特殊 panel -->
		<input type="hidden" name="Page" value="${Pageid}" />
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection name="Form2Datas" emptyKey="沒有資料列" indexId="index" width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >								    
				<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">
					<layout:button value="選取" onclick="return getDepRowValue(${index});" />
				</layout:collectionItem>
				<layout:collectionItem title="部門代號" style="TEXT-ALIGN: CENTER" property="DEPT_ID" />			
				<layout:collectionItem title="部門名稱" style="TEXT-ALIGN: CENTER" property="SHOW_DESC" />
			</layout:collection>
		</layout:pager>
		<div style="display: none">
			<layout:collection name="Form1Datas" property="EMSC" emptyKey="沒有資料列" indexId="index" width="95%" height="95%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
								    
				<layout:collectionItem title="部門代號" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].DEPT_ID"	layout="false" mode="I,I,I" />			
				</layout:collectionItem>
				<layout:collectionItem title="部門名稱" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].SHOW_DESC"	layout="false" mode="I,I,I" />
				</layout:collectionItem>			
			</layout:collection>
		</div>
	
	</logic:equal>
	
	<%-- 部門下轄N階(or 下轄到底)人員資料; 空白階視同下轄到底 add by John 2014/06/12 --%>
	<logic:equal name="Form1Datas" property="function" value="empunderdep">
		搜尋&nbsp;<layout:select name="Form1Datas" property="depth" onchange="onChangeDepth(this.value)" layout="false">
			<layout:option value="" key="下轄所有"></layout:option>
			<layout:option value="0" key="本階"></layout:option>
			<layout:option value="1" key="下轄一階"></layout:option>
			<layout:option value="2" key="下轄二階"></layout:option>
			<layout:option value="3" key="下轄三階"></layout:option>
			<layout:option value="4" key="下轄四階"></layout:option>
			<layout:option value="5" key="下轄五階"></layout:option>
			<layout:option value="6" key="下轄六階"></layout:option>
			<layout:option value="7" key="下轄七階"></layout:option>
			<layout:option value="8" key="下轄八階"></layout:option>
			<layout:option value="9" key="下轄九階"></layout:option>
		</layout:select>&nbsp;的員工
		<br/>
			<!-- pager 是用來分頁的特殊 panel -->
		<input type="hidden" name="Page" value="${Pageid}" />
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" >
			<layout:collection name="Form2Datas" emptyKey="沒有資料列" indexId="index" width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >								    
				<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">
					<layout:button value="選取" onclick="return getSameDepEmpRowValue(${index});" />
				</layout:collectionItem>
				<layout:collectionItem title="部門名稱" style="TEXT-ALIGN: CENTER" property="DEPT_DESC" />
				<layout:collectionItem title="員工工號" style="TEXT-ALIGN: CENTER" property="EMPLOYEE_ID" />			
				<layout:collectionItem title="員工姓名" style="TEXT-ALIGN: CENTER" property="EMPLOYEE_NAME" />
			</layout:collection>
		</layout:pager>
		<div style="display: none">
			<layout:collection name="Form1Datas" property="EMSC" emptyKey="沒有資料列" indexId="index" width="95%" height="95%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
								    
<%--			<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">--%>
<%--				<layout:button value="選取" onclick="return getRowValue(${index});" />--%>
<%--			</layout:collectionItem>--%>
				<layout:collectionItem title="員工工號" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].EMPLOYEE_ID"	layout="false" mode="I,I,I" />			
				</layout:collectionItem>
				<layout:collectionItem title="員工姓名" style="TEXT-ALIGN: CENTER" >
					<layout:text  name="Form1Datas" property="EMSC[${index}].EMPLOYEE_NAME"	layout="false" mode="I,I,I" />
				</layout:collectionItem>			
			</layout:collection>
		</div>
	
	</logic:equal>
	
	<%-- 部門資料_樹狀結構  create by John 2014/05/13 --%>
	<logic:equal name="Form1Datas" property="function" value="dep_tree">
		<layout:treeview name="DEP_tree" />
		
		<script language="javascript">
			$(document).ready(function(){
				//預設此畫面的tree由樹頭(為第1階)開始算起下轄N階全部收合
				//避免上次操作tree view時暫存的展開樹狀階層
				//closeAll('DEP_tree', 99);
				openAll('DEP_tree', 99);
			});
			
			//延伸運用
			//tree由樹頭(為第1階)開始算起下轄N階展開
			//目前用於BOM表，客戶要求展料須預設自動展開第一層子BOM
			//expandFirstLevels('DEP_tree', 2);
		</script>
		
	</logic:equal>
</form>

</body>

</html>

<%--<jsp:include page="/templates/end.jsp"></jsp:include>--%>

