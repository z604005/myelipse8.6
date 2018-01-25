<%@ page language="java" contentType="text/html; charset=UTF-8"
		import="java.text.DateFormat,java.text.SimpleDateFormat"
%>
<%@ taglib uri='http://struts.apache.org/tags-bean' prefix='bean' %>
<%@ taglib uri='http://struts.apache.org/tags-logic' prefix='logic' %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout" %>

<script type="text/javascript">
<!--
<%--window.onload = function(){--%>
<%--	showclock();--%>
<%--}--%>
<%--function showclock(){--%>
<%--	var Day = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];--%>
<%--	var now = new Date();--%>
<%--	var hour = now.getHours()<10?"0"+now.getHours():now.getHours();--%>
<%--	var min = now.getMinutes()<10?"0"+now.getMinutes():now.getMinutes();--%>
<%--	var sec = now.getSeconds()<10?"0"+now.getSeconds():now.getSeconds();--%>
<%--	document.getElementById('time').value=Day[now.getDay()]+" "+now.getYear()+"/"+(now.getMonth()+1)+"/"+now.getDate()+" "+hour+":"+min+":"+sec;--%>
<%--	setTimeout("showclock()",1000);--%>
<%--}--%>
<%--function topcreateHttpRequest() {--%>
<%--    if (window.ActiveXObject) {--%>
<%--        try {--%>
<%--            return new ActiveXObject("Microsoft.XMLHTTP");--%>
<%--        }--%>
<%--        catch (e2) {--%>
<%--            return null;--%>
<%--        }--%>
<%--    } else {--%>
<%--        if (window.XMLHttpRequest) {--%>
<%--            return new XMLHttpRequest();--%>
<%--        } else {--%>
<%--            return null;--%>
<%--        }--%>
<%--    }--%>
<%--}--%>
<%--function keep_menu_status(status){--%>
<%--	ajax = topcreateHttpRequest();--%>
<%--    ajax.onreadystatechange = function () {--%>
<%--        if (ajax.readyState == 4) {--%>
<%--            if (ajax.status == 200) {	--%>
<%--            } else {--%>
<%--               alert("\u57f7\u884c\u932f\u8aa4!" + ajax.status);--%>
<%--            }--%>
<%--        }--%>
<%--    };--%>
<%--    ajax.open("POST", "login.do");--%>
<%--    ajax.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");--%>
<%--    ajax.setRequestHeader("If-Modified-Since","0");--%>
<%--    ajax.send("reqCode=menustatus&mode="+status); --%>
<%--}--%>
<%----%>
<%--	function hideMENU(){--%>
<%--		if (document.all.MAINMAIN.rows(2).cells(0).style.display == "none") {--%>
<%--			document.all.MAINMAIN.rows(0).style.display = "inline";--%>
<%--			document.all.MAINMAIN.rows(2).cells(0).style.display = "inline";--%>
<%--			document.all.MAINMAIN.rows(2).cells(1).style.width = "800px";--%>
<%--			document.getElementById('menubb').innerHTML="<img src=\"config/hidemenu.jpg\" border='0' />";--%>
<%--			keep_menu_status("false");--%>
<%----%>
<%--		}else{--%>
<%--			document.all.MAINMAIN.rows(0).style.display = "none";--%>
<%--			document.all.MAINMAIN.rows(2).cells(0).style.display = "none";--%>
<%--			document.all.MAINMAIN.rows(2).cells(1).style.width = "1000px";--%>
<%--			document.getElementById('menubb').innerHTML="<img src=\"config/showmenu.jpg\" border='0' />";--%>
<%--			keep_menu_status("true");--%>
<%--		}--%>
<%--	}--%>
<%--	window.document.body.onbeforeunload = function(){--%>
<%--   	if(document.getElementById("dataChanged")!=null && document.getElementById("dataChanged").value!="")--%>
<%--	   	return '您尚未將編輯過的表單資料儲存，請問您確定要離開嗎？';--%>
<%--	}--%>
<%--	window.onerror = function(msg, url, line){--%>
<%--		window.status = "Script Error: " +msg + ", Ln: " + line;--%>
<%--		return false;--%>
<%--	}--%>
	
	var _rys = jQuery.noConflict();
	_rys("document").ready(function(){
	
		_rys(window).scroll(function () {
			if (_rys(this).scrollTop() > 80) {
				_rys('.nav-container').addClass("f-nav");
			} else {
				_rys('.nav-container').removeClass("f-nav");
			}
		});

	});
	
//-->
</script>
<style type="text/css">
.sys_menu {
	width: 80px;
	height: 18px;
	border: 1px solid #ccc;
	background-color: #123456;
	font-weight: 900;
	text-align: center;
	border-top-left-radius: 10px;
	border-bottom-right-radius: 10px;
}
</style>

<%
	java.util.Date l_date = new java.util.Date(System.currentTimeMillis());
	String l_stFormatDate = new String("E yyyy/MM/dd HH:mm:ss");
	DateFormat l_formatDate = new SimpleDateFormat(l_stFormatDate, java.util.Locale.TAIWAN);
	String stDate = l_formatDate.format(l_date);
	pageContext.setAttribute("stDate", stDate);
%>
		<%--<span id='waitid'  scrolling='no' frameborder='0' style='margin-left:auto; margin-right:auto;position:absolute; top:0px; left:0px; display:none;'> <table width="170%" height="100%"><tr><td><center><img src='config/wait.gif'></center></td></tr></table></span>--%>



		<%--以下方法，在列印時，可以出現遮罩   Alvin--%>

		<DIV id='waitid'  scrolling='no' frameborder='0' style='position:absolute;  height:100%; width:100%;  background: hsla(0, 0%, 50%, 0);  display:none;'> 
			<table border='0' width='100%' height='100%'>
				<tr>
					<td>
						<center>
							<img src='config/wait.gif' border='0' >
						</center>
					</td>
				</tr>
			</table>
		</DIV>
     	<div class="wrapper">

			<div class="header">
<%--				<a href="" title="Fixed Menu on Scroll with jQuery and CSS3">--%>
<%--					<h1 class="logo">Fixed Menu on Scroll with jQuery and CSS3</h1>--%>
<%--				</a>--%>
			</div>
			
			<div class="nav-container">
				<div class="nav">
					<ul>
						<li><div onmouseover="$('#myMenuID2').show();"><input type="button" style="background-image: url('config/btn_nav.jpg'); width: 77px; height: 37px; border: 0px; color: rgb(0,62,96); font-size: medium; font-weight: bolder;" value="系統選單"/></div></li>
						<li style="padding: 14px 0px; color: rgb(255,255,255); font-size: medium; font-weight: bolder;">目前登入者：${loginid}&nbsp;&nbsp;${loginname}</li>
<%--						<li><font color="white">作業代號：${pgmname}</font></li>--%>
						<li style="float: right;"><input type="button" style="background-image: url('config/btn_bar.jpg'); width: 77px; height: 37px; border: 0px; color: rgb(15,89,72); font-size: medium; font-weight: bolder;" value="登出系統" onclick="javascript:location.href='login.do?reqCode=loginScreen'"/></li>
						<li style="float: right;"><input type="button" style="background-image: url('config/btn_bar.jpg'); width: 77px; height: 37px; border: 0px; color: rgb(15,89,72); font-size: medium; font-weight: bolder;" value="回首頁" onclick="javascript:location.href='index_warehouse.do'"/></li>
					</ul>
					<div class="clear"></div>
				</div>
			</div>
				
<%--			<div class="container">	--%>
<%--				<div class="main-content">	--%>
<%--					<div class="watch-me"></div> <div class="clear"></div>--%>
<%--					<div class="look-at-me"></div>--%>
<%--					<div class="the-end"></div>--%>
<%--				</div>--%>
<%--			</div>--%>
<%--			--%>
<%--			<div class="scroll-down"></div>--%>
<%--			<a href="http://www.sutanaryan.com/blog/jquery/how-to-create-fixed-menu-when-scrolling-page-with-css-and-jquery/">--%>
<%--				<div class="back"></div>--%>
<%--			</a>--%>
			
		</div>
     
     
<%--        <table border="0" cellspacing = "0" cellpadding = "0" width = "100%">--%>
<%--        <tr>--%>
<%--                <td class="top2_banner_left" align="center">--%>
					<button type="button" id="hideleftmenu" value="隱藏左選單" style="display: none;"
                            onclick="$('#myMenuID2').hide();">隱藏左選單</button>
                    <button type="button" id="hideleftmenu" value="顯示左選單" style="display: none;"
                            onclick="$('#myMenuID2').show();">顯示左選單</button>
<%--                	<button type="button" id="hideleftmenu" value="隱藏左選單"--%>
<%--                            onclick="$('#myMenuID2').hide(); this.style.display='none'; document.getElementById('showleftmenu').style.display='';">隱藏左選單</button>--%>
<%--                	<button type="button" id="showleftmenu" value="顯示左選單" style="display: none"--%>
<%--                            onclick="$('#myMenuID2').show(); this.style.display='none'; document.getElementById('hideleftmenu').style.display='';"--%>
<%--                            onmouseover="$('#myMenuID2').show(); this.style.display='none'; document.getElementById('hideleftmenu').style.display=''; ">顯示左選單</button>--%>
<%--                    <button type="button" id="hidetitle" value="隱藏系統標題"--%>
<%--                            onclick="$('#title').hide(); this.style.display='none'; document.getElementById('showtitle').style.display='';">隱藏系統標題</button>--%>
<%--                	<button type="button" id="showtitle" value="顯示系統標題" style="display: none"--%>
<%--                            onclick="$('#title').show(); this.style.display='none'; document.getElementById('hidetitle').style.display='';">顯示系統標題</button>--%>
<%--                </td>--%>
<%--                --%>
<%--                <td align="left" class="top2_banner_right">--%>
<%--                	<p class="barremenu">--%>
<%--                	--%>
<%--                	<font color="white">目前登入者：${loginid}&nbsp;&nbsp;${loginname}&nbsp;</font>--%>
<%--                	<font color="white">作業代號：${pgmname}&nbsp;&nbsp;</font> --%>
<%--                	--%>
<%--					本搜尋功能僅適用於IE, 若要使用此功能需實作適用多瀏覽器的script--%>
<%--                	<input size="12" value="輸入本頁面關鍵字" type="text" onclick="this.value=''" onmouseout="this.value=this.value==''?'輸入本頁面關鍵字':this.value" onkeydown="findFiledpress()" name="JSfindtext" id="JSfindtext"/>&nbsp;--%>
<%--                	<img src="config/MAGNIFY.GIF" style="cursor:hand;"  onClick="selecttoolid(JSfindtext.value)"/>--%>
<%--                	<a href="index.do"><font color="a8a5a8" onmouseover="this.color='red'" onmouseout="this.color='a8a5a8'">回首頁</font></a>--%>
<%--                	--%>
<%--                	&nbsp;&nbsp;--%>
<%--                	--%>
<%--                	</p>--%>
<%--                </td>--%>
<%--                <td align="center" class="top2_banner_center">--%>
<%--                <p class="barremenu">--%>
<%--                	--%>
<%--                	<a href="login.do?reqCode=loginScreen"><font color="#ff4747">登出系統</font></a> --%>
<%--					&nbsp;&nbsp;--%>
<%--					<input type="text" style="background: transparent; border:0px; font-family:serif; font-weight:900; color:#ffffff" name="time" id="time" size="24" readonly>--%>
<%--		    	</p>--%>
<%--                </td>--%>
<%--        </tr>--%>
<%--        </table>--%>