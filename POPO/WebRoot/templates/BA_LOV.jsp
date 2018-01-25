<%@ page language="java" contentType="text/html; charset=UTF-8"
		import="java.util.*,
				java.net.URLDecoder,
				java.net.URLEncoder,
				java.sql.*"
%>
<%
request.setCharacterEncoding("UTF-8"); 

%>


<%!

//example of gb2312
//CHARSET = UTF-8 
public String toCharset(String str, String fromCharset , String toCharset){
	String s = "";
		try{
		s = new String(str.getBytes(fromCharset), toCharset);
		}catch(Exception er){ 
		System.out.println(er.toString());
		return ""; 
		}
	return s;
}
 %>
<jsp:useBean id="lov" class="com.spon.utils.util.BA_LOV" scope="request" >
	<jsp:setProperty name="lov" property="*"/>
</jsp:useBean>
<%

	String[] lovField = lov.getLovField().split(",");
	String[] id = lov.getId().split(",");
	String[] fieldAlias = URLDecoder.decode(lov.getFieldAlias(),"UTF-8").split(",");
	String[] fieldName = lov.getFieldName().split(",");
	String chgScript = "";  //啟動執行完成後的script
	if(!"".equals(lov.getChangescript()) && lov.getChangescript()!=null ){
		chgScript = lov.getChangescript();
	}
	String return_flag = lov.getReturn_flag();
	int colspan = fieldAlias.length + 1;
//	out.print(lov.getId());
	int curPage = (request.getParameter("page") == null)?
		1:Integer.parseInt(request.getParameter("page"));
	int rsPerPage = 10;		//每頁要顯示的資料筆數

	Vector datas = lov.showDatas(request, curPage, rsPerPage);
	int totalRowCount = lov.getTotalRowCount();
	
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>LOV</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    
    <%--	設定CSS Resource ediy by joe 2012/09/11	--%>
	<link rel="stylesheet" type="text/css" href="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.css" />
		
	<%--	設定jQuery Resource ediy by joe 2012/09/11	--%>
	<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery-1.8.1.min.js"></script>
	<script type="text/javascript" src="<%= request.getContextPath() %>/config/ems_javascript/jquery.loadmask.min.js"></script>
    
  	<script language="javascript">
  	<!--//
  	//語言、瀏覽器判斷  Add by John 2014/02/13 --------------------------------------------------------------------------------------------
  	//檢測流覽器語言
	currentLang = navigator.language;   //判斷除IE外其他流覽器使用語言
	if(!currentLang){//判斷IE流覽器使用語言
	    currentLang = navigator.browserLanguage;
	}
	//alert(currentLang);
	
	
	//判斷訪問終端
	var browser={
	    versions:function(){
	        var u = navigator.userAgent, app = navigator.appVersion;
	        return {
	            trident: u.indexOf('Trident') > -1, //IE內核
	            presto: u.indexOf('Presto') > -1, //opera內核
	            webKit: u.indexOf('AppleWebKit') > -1, //蘋果、穀歌內核
	            gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1,//火狐內核
	            mobile: !!u.match(/AppleWebKit.*Mobile.*/), //是否為移動終端
	            ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios終端
	            android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android終端或者uc流覽器
	            iPhone: u.indexOf('iPhone') > -1 , //是否為iPhone或者QQHD流覽器
	            iPad: u.indexOf('iPad') > -1, //是否iPad
	            webApp: u.indexOf('Safari') == -1 //是否web應該程式，沒有頭部與底部
	        };
	    }(),
	    language:(navigator.browserLanguage || navigator.language).toLowerCase()
	}
	
	//browser.versions.trident返回真假，真則是IE內核，以此類推browser.versions.webKit是否為穀歌內核
	//判斷是否IE內核
	if(browser.versions.trident){
	    //alert("is IE");
	}
	
	//判斷是否webKit內核
	if(browser.versions.webKit){
	    //alert("is webKit");
	}
	
	//判斷是否移動端
	if(browser.versions.mobile||browser.versions.android||browser.versions.ios){
	    //alert("移動端");
	}
	//語言、瀏覽器判斷 End ---------------------------------------------------------------------------------------------------------------
	
  	function callBack(arg){
		//為了支援多瀏覽器將以下物件操作分兩大類 Add By John 2014/02/13
  		//1. IE解析table的<tr>會得到HTMLCollection瀏覽器支援chrildren操作
  		//2. 而Chrome,FireFox,Opera,Safari則得到NodeList，瀏覽器不支援chrildren操作
  		var y = new Array();
  		var currentChild = window.mytable.rows.item(arg+1).firstChild;

  		if(browser.versions.trident){	//is IE
		  	  	//	var args = callBack.arguments;
		<%	for (int i = 0; i < id.length; i ++){ %>
		  		<%--	window.opener.document.<%= lov.getForm() %>.<%= id[i] %>.value = args[<%= i %>];--%>
		  		//判斷寫入的欄位的型態,因下拉選單及checkbox radio會沒有填入值
		  		objChk = window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.length;
				if (isNaN(objChk)){
					window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.value=window.mytable.rows.item(arg+1).children(<%=i%>+1).innerText;
				}else{
					if (isNaN(window.opener.document.<%=lov.getForm()%>.<%=id[i]%>[0].checked)){
						window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.value=window.mytable.rows.item(arg+1).children(<%=i%>+1).innerText;
					}else{
						if (window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.value != undefined){
							if(window.mytable.rows.item(arg+1).children(<%=i%>+1).innerText ==  window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.value ){
								window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.checked=true;
							}
						}else{ //當有兩個以上的選項時 要用迴圈取得checked的選項
		  					for (var i=0; i<window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.length; i++){
		      					if (window.opener.document.<%=lov.getForm()%>.<%=id[i]%>[i].value == window.mytable.rows.item(arg+1).children(<%=i%>+1).innerText){
		        					window.opener.document.<%=lov.getForm()%>.<%=id[i]%>[i].checked=true;
		        					break;
		      			}}}
					}
				}
		<%	} %>
  		}else{	//is Chrome, FireFox, Safari, Opera ...etc.
  			/* 因為NodeList取值時會逐行取值如下範例，會取得13個textContent
	  		 * 所以串Array時要略掉第0,1,2,4,6,8,10,12個值
	  		<tr>
	0
	1			<td class='FORM2' nowrap><input type='button' value='選取' onclick='javascript:callBack(0);'/></td>
	2			
	3			<td class='FORM2' nowrap >32002</td>
	4			
	5			<td class='FORM2' nowrap >金葆穎股份有限公司</td>
	6			
	7			<td class='FORM2' nowrap >林'r</td>
	8			
	9			<td class='FORM2' nowrap >047-874123</td>
	10			
	11			<td class='FORM2' nowrap ></td>
	12		
			</tr>
	  		*/
  		
  		var i = 0;
  		while(currentChild!=null){
	        if(currentChild.nodeType == Node.ELEMENT_NODE){
	            if(i>2 && i%2==1){	//略掉前3筆、最後一筆、第奇數筆
	            	y[y.length]=currentChild.textContent;
	            }
	        }

	        currentChild=currentChild.nextSibling;
	        i++;
	    }
		<%	for (int i = 0; i < id.length; i ++){ %>
		  		<%--	window.opener.document.<%= lov.getForm() %>.<%= id[i] %>.value = args[<%= i %>];--%>
		  		//判斷寫入的欄位的型態,因下拉選單及checkbox radio會沒有填入值
		  		objChk = window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.length;
				if (isNaN(objChk)){
					window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.value=y[<%=i%>];
				}else{
					if (isNaN(window.opener.document.<%=lov.getForm()%>.<%=id[i]%>[0].checked)){
						window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.value=y[<%=i%>];
					}else{
						if (window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.value != undefined){
							if(y[<%=i%>] ==  window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.value ){
								window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.checked=true;
							}
						}else{ //當有兩個以上的選項時 要用迴圈取得checked的選項
							for (var i=0; i<window.opener.document.<%=lov.getForm()%>.<%=id[i]%>.length; i++){
								if (window.opener.document.<%=lov.getForm()%>.<%=id[i]%>[i].value == y[<%=i%>]){
									window.opener.document.<%=lov.getForm()%>.<%=id[i]%>[i].checked=true;
		        					break;
		      			}}}
					}
				}
		<%	} %>
  		}
		
		filedchangescript();  //啟動執行完成後的script
	  	window.close();
  	}
	
  	function filedchangescript(){  //啟動執行完成後的script
	if(document.getElementById("changescript").value != "" && document.getElementById("changescript").value != null ){
		eval("parent.opener."+document.getElementById("changescript").value.replace(/<~>/g,"\'"));
	}	
}
  	
  //-->
  </script>
  <link rel="stylesheet" href="<%= request.getContextPath() %>/config/default.css" type="text/css">
  </head>
  
<!-- JavaScript -->
<script type="text/javascript">
	
function maskLOV(){
	$("#lov_body").mask("讀取中...");
}
	
</script>
  
  <body id="lov_body" name="lov_body">
	<table width="100%">
		<tr>
			<td colspan="<%= colspan %>" class="lovfield1">
				<form method="POST" name="findForm" action="BA_LOV.jsp">
					<input type="hidden" name="fieldAlias" value="<%= URLEncoder.encode(URLDecoder.decode(lov.getFieldAlias(),"UTF-8"),"UTF-8") %>"/>
					<input type="hidden" name="id" value="<%= lov.getId() %>"/>
					<input type="hidden" name="lovField" value="<%= lov.getLovField() %>"/>
					<input type="hidden" name="form" value="<%= lov.getForm() %>"/>
					<input type="hidden" name="table" value="<%= lov.getTable() %>"/>
					<input type="hidden" name="fieldName" value="<%= lov.getFieldName() %>"/>
					<input type="hidden" name="leftjoin" value="<%= lov.getLeftjoin() %>"/>
					<input type="hidden" name="others" value="<%= lov.getOthers() %>"/>
					<input type="hidden" name="parentId" value="<%= lov.getParentId() %>"/>
					<input type="hidden" name="parentField" value="<%= lov.getParentField() %>"/>
					<input type="hidden" name="parentTarget" value="<%= lov.getParentTarget() %>"/>
					<input type="hidden" name="sourceTarget" value="<%= lov.getSourceTarget() %>"/>
					<input type="hidden" name="changescript" id="changescript" value="<%= lov.getChangescript() %>"/>
					<select name="keywordsField">
						<option value=""></option>
<%
	for (int i = 0; i < fieldName.length; i ++){
		String selected = "";
		if (fieldName[i].equals(lov.getKeywordsField())) selected = "selected";
%>
						<option value="<%= fieldName[i] %>" <%= selected %>><%= fieldAlias[i] %></option>
<%
	}
%>
					</select>
					<input type="text" name="keywords" value="<%= lov.getKeywords() %>"/>
					<input type="submit" name="submit" value="查詢" onclick="return maskLOV();" />
				</form>
			</td>
		</tr>
		<tr>
			<td colspan="<%= colspan %>" class="lovfield1">
				<%= lov.navigator("BA_LOV.jsp", totalRowCount, curPage, rsPerPage, 10,
						"&fieldAlias=" +  URLEncoder.encode(lov.getFieldAlias() ,"UTF-8") +
						"&id=" + lov.getId() +
						"&lovField=" + lov.getLovField() +
						"&form=" + lov.getForm() +
						"&table=" + lov.getTable() +
						"&leftjoin= " + lov.getLeftjoin() +
						"&fieldName=" + lov.getFieldName() +
						"&keywordsField=" + lov.getKeywordsField() +
						//"&keywords=" +  URLEncoder.encode(lov.getKeywords() ,"UTF-8") +
						"&keywords=" +  URLEncoder.encode(lov.getKeywords(),"UTF-8")  +
						"&keywordsget=" + URLEncoder.encode(lov.getKeywords(),"UTF-8") +  
						"&others=" + lov.getOthers()+ 
						"&parentId=" + lov.getParentId()+ 						
						"&parentField=" + lov.getParentField()+				
						"&parentTarget=" + lov.getParentTarget()+
						"&sourceTarget=" + lov.getSourceTarget()+
						"&changescript=" + lov.getChangescript()) %>
			</td>
		</tr>
		<%--
		<tr>
			<td colspan="<%= colspan %>" class="lovfield1">
				<%= lov.navigator("BA_LOV.jsp", totalRowCount, curPage, rsPerPage, 10,
						"&fieldAlias=" + URLEncoder.encode(lov.getFieldAlias(),"UTF-8") +
						"&id=" + lov.getId() +
						"&lovField=" + lov.getLovField() +
						"&form=" + lov.getForm() +
						"&table=" + lov.getTable() +
						"&leftjoin= " + lov.getLeftjoin() +
						"&fieldName=" + lov.getFieldName() +
						"&keywordsField=" + lov.getKeywordsField() +
						"&keywords=" + URLEncoder.encode(lov.getKeywords(),"UTF-8") +
						//"&keywords=" +  lov.getKeywords()  +
						"&others=" + lov.getOthers()+ 
						"&parentId=" + lov.getParentId()+ 						
						"&parentField=" + lov.getParentField()) %>
			</td>
		</tr>
		--%>
		<tr>
			<td colspan="<%= colspan %>" class="lovfield1">
			<div style="OVERFLOW: auto;width: 548; height: 374">
				<table class="FORM" cellspacing="1" cellpadding="1" border="0" id="mytable">
				<tr>
<%
	if (!"false".equals(return_flag)) {  //為了不要回傳值所加   by felistum    %>				
					<th class="FORM" nowrap>回傳</th>
<%}%>					
					
<%	for (int i = 0; i < fieldAlias.length; i ++){ %>
					<th class="FORM" nowrap><%= fieldAlias[i] %></th>
<%	} %>
				</tr>
<%
	for (int i = 0; i < datas.size(); i ++){
		HashMap data = (HashMap)datas.get(i);
//		String args = "";
//		for (int j = 0; j < lovField.length; j ++){
//			if (j > 0) args += ",";
//			args += "\"" + data.get(lovField[j]) + "\"";
//		}
		out.println("<tr>\n");
		if( i%2 ==0 ){
			if (!"false".equals(return_flag)) {    //為了不要回傳值所加   by felistum
				out.println("<td class='FORM2' nowrap><input type='button' value='選取' onclick='javascript:callBack(" +
					i + ");'/></td>\n");
					//args + ");'/></td>\n");
			}	
			for (int k = 0; k < lovField.length; k ++){
				if("yes".equals(lov.getSourceTarget())){
					out.println("<td class='FORM2'   nowrap "+ (k<fieldName.length?"":"style='display:none;'")+"><textarea  OnFocus=\"blur()\" rows=\"4\" cols=\"60\" >" + data.get(lovField[k]) + "</textarea></td>\n");
					//新增OnFocus=\"blur()\"  功能是把輸入權移除    20140801   By Alvin
				}else{
					out.println("<td class='FORM2' nowrap "+ (k<fieldName.length?"":"style='display:none;'")+">" + data.get(lovField[k]) + "</td>\n");
				}
			}
		}else{
			if (!"false".equals(return_flag)) {    //為了不要回傳值所加   by felistum
				out.println("<td class='FORM' nowrap><input type='button' value='選取' onclick='javascript:callBack(" +
					i + ");'/></td>\n");
					//args + ");'/></td>\n");
			}	
			for (int k = 0; k < lovField.length; k ++){
				
				if("yes".equals(lov.getSourceTarget())){
					out.println("<td class='FORM'  nowrap "+ (k<fieldName.length?"":"style='display:none;'")+"><textarea OnFocus=\"blur()\" rows=\"4\" cols=\"60\" >" + data.get(lovField[k]) + "</textarea></td>\n");
					//新增OnFocus=\"blur()\"  功能是把輸入權移除    20140801   By Alvin
				}else{
					out.println("<td class='FORM' nowrap "+ (k<fieldName.length?"":"style='display:none;'")+">" + data.get(lovField[k]) + "</td>\n");
				}
			}
		}
		out.println("</tr>\n");
	}
%>
				</table>
			</div>
			</td>
		</tr>
	
	</table>
  </body>
</html>
