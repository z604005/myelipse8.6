<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE HTML>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<html style="height: 100%; background: url('config/bg_main.jpg'); background-attachment: fixed;">

<!-- H E A D -->
<tiles:get name="begin"/>

<script>
$(document).ready(function(){
	//<!-- float menu's script start-->
	//config
	$float_speed=1500; //milliseconds
	$float_easing="easeOutQuint";
	$menu_fade_speed=500; //milliseconds
	$closed_menu_opacity=0.75;
	 
	//cache vars
	$fl_menu=$("#myMenuID2");
	//$fl_menu_menu=$("#fl_menu .menu");
	//$fl_menu_label=$("#fl_menu .label");
	 
	//$(window).load(function() {
	    menuPosition=$fl_menu.position().top;
	    FloatMenu();
	//    $fl_menu.hover(
	//        function(){ //mouse over
	//            $fl_menu_label.fadeTo($menu_fade_speed, 1);
	//            $fl_menu_menu.fadeIn($menu_fade_speed);
	//        },
	//        function(){ //mouse out
	//            $fl_menu_label.fadeTo($menu_fade_speed, $closed_menu_opacity);
	//            $fl_menu_menu.fadeOut($menu_fade_speed);
	//        }
	//    );
	//});
	 
	$(window).scroll(function () {
	    FloatMenu();
	});
	 
	function FloatMenu(){
	    var scrollAmount=$(document).scrollTop();
	    var newPosition=menuPosition+scrollAmount;
	//    if($(window).height()<$fl_menu.height()+$fl_menu_menu.height()){
	    if($(window).height()<$fl_menu.height()){
	        $fl_menu.css("top",menuPosition);
	    } else {
	        $fl_menu.stop().animate({top: newPosition}, $float_speed, $float_easing);
	    }
	}
	//<!-- float menu's script end-->
	
	$('#myMenuID2').mouseleave(function(){
		//隱藏左選單
    	$('#hideleftmenu').trigger("click");
	});
	
	//隱藏左選單
    $('#hideleftmenu').trigger("click");
    //隱藏系統標題
    //$('#hidetitle').trigger("click");
});
</script>

<!-- / H E A D -->
<body style="height: 100%; background: url('config/bg_main.jpg'); background-attachment: fixed;">
<%
	//處裡menu狀態
//	String top_style="";
//	String menu_style="width: 200px";
//	String content_style="width: 100%; height:100%";

//	if(session.getAttribute("MENUHIDESTATUS")!=null)
//	{
//		if(session.getAttribute("MENUHIDESTATUS").equals("true"))
//		{
//		 top_style="display: none;";
//		 menu_style="display: none;";
//		 content_style="display: inline;width:1000px;";
//		}
//	}	

%>

<tiles:get name="left"/>
<tiles:get name="top" />
<tiles:get name="content"/>
<%--<table id="MAINMAIN" name="MAINMAIN" cellspacing="0" cellpadding="0" border="0" width="100%" height="" align="center">--%>
<%--	<tr>--%>
<%--		<td style="<%=top_style%>" valign="top" colspan="2" id="title"><tiles:get name="title" /></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td valign="top" colspan="2"><tiles:get name="top" /></td>--%>
<%--	</tr>--%>
<%--	<tr>--%>
<%--		<td valign="top" style="<%=content_style%>" class="mid_bg">--%>
<%--			<div id="content" name="content" style="width:100%;height:100%;overflow-x:auto;overflow-y:auto">--%>
<%--					<table cellspacing="0" cellpadding="0" border="0" align="center">--%>
<%--						<tr>--%>
<%--							<td align="center" height="100%" valign="top"><tiles:get name="content"/></td>--%>
<%--						</tr>--%>
<%--					</table>--%>
<%--			</div>--%>
<%--		</td>--%>
<%--	</tr>--%>
<%--</table>--%>
</body>
</html>