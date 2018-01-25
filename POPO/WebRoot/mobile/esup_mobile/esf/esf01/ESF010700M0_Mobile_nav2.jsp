<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Include mobile header setting -->
<jsp:include page="/mobile_templates/mobile_begin.jsp" />

<form id="" action="" method="post" >
<input type="hidden" id="reqCode" name="reqCode" />
<div data-role="page" id="esf010200m0_Mobile_nav2" data-add-back-btn="false" >
	<div data-role="header" data-position="fixed" >
		<a id="function_page_nav2_back" name="function_page_nav2_back" href="#" data-icon="arrow-l" class="ui-btn-left" >功能頁</a>
		<h1>採購即時查詢</h1>
<%--		<a href="#" data-icon="search" class="ui-btn-right" >查詢</a>--%>
	</div>
	<div data-role="content" >
		<div id="esf010200m0_nav2_message_field" data-role="fieldcontain" >
        	<h3 id="esf010200m0_nav2_message" ></h3>
        </div>
        <ul id="esf010200m0_query_list" data-role="listview" data-inset="true" >
        </ul>
	</div>
	<div data-role="footer" data-position="fixed">
		<div data-role="navbar">
				<ul>
					<li><a id="esf010200m0_Mobile_nav1_button" name="esf010200m0_Mobile_nav1_button" 
						   href="#" >查詢條件</a>
					</li>
					<li><a id="" name="" 
						   href="#" class="ui-btn-active ui-state-persist" >查詢結果</a>
					</li>
				</ul>
		</div>
	</div>
	<!-- JavaScript -->
<script type="text/javascript">

	var esf010200m0_Mobile_nav1_button_click = true;
	var function_page_nav2_back = true;
	var path = $("#web_current_contextpath").val();
	
	$('#esf010200m0_Mobile_nav2').die('pagebeforeshow').live('pagebeforeshow', function(e, ui) {
		
		/*
		var cond_data = {
		   ESF010200T0_02: $('#ESF010200T0_02').val(),
 		   ESF010200T0_03: $('#ESF010200T0_03').val(),
		   ESF010200T0_03_END: $('#ESF010200T0_03_END').val(),
		   ESF010200T0_09: $('#ESF010200T0_09').val(),
		   ESF010200T0_11: $('#ESF010200T0_11').val(),
		   ESF010200T0_18: $('#ESF010200T0_18').val()
		};
		*/
		
		/*
		$.each( cond_data, function(k, v){
   			alert( "Key: " + k + ", Value: " + v );
 		});
		*/

				//Ajax呼叫
				var cur_url = path+'/ESF010700M0_Mobile.do';
				var dynamic_mobile_reqCode = "";
				
				if($('#defaultQuery').val()){
					//alert('executeDefaultQueryData');
					dynamic_mobile_reqCode = "executeDefaultQueryData"
				}else{
					//alert('executeQueryData');
					dynamic_mobile_reqCode = "executeQueryData";
				}
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "getAjaxData",
						   mobile_reqCode: dynamic_mobile_reqCode,
						   ESF010200T0_02: $('#ESF010200T0_02').val(),
 		   				   ESF010200T0_03: $('#ESF010200T0_03').val(),
		   				   ESF010200T0_03_END: $('#ESF010200T0_03_END').val(),
		   				   ESF010200T0_09: $('#ESF010200T0_09').val(),
		   				   ESF010200T0_11: $('#ESF010200T0_11').val(),
		   				   ESF010200T0_18: $('#ESF010200T0_18').val()},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
			        },
			        complete: function(request, status){
			        	//Close loading mask
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//使用 jQuery Mobile API
									//alert(data.message);
									$.each( data.datalist, function(index, value){
   										//alert(index + ': ' + data.datalist[index] + '-->' +data.datalist[index].ESF010200T0_02); 
 									
									
										//Create動態List
										$('<li data-role="list-divider">' +
									  	  '<h3>採購單號</h3>' +
									  	  '<h3 class="ui-li-aside">'+data.datalist[index].ESF010200T0_02+'</h3>' +
									  	  '</li>' +
									  	  '<li data-role="fieldcontain" >' +
									  	  //'<a href="#" data-transition="slide" >' +
									  	  '<h5>單據日期：'+data.datalist[index].ESF010200T0_03+'</h5>' +
									  	  '<h5>採購人員：'+data.datalist[index].ESF010200T0_05+'</h5>' +
									  	  '<h5>廠商：'+data.datalist[index].ESF010200T0_11+'</h5>' +
									  	  '<h5>廠別：'+data.datalist[index].ESF010200T0_06+'</h5>' +
									  	  '<h5>處理狀態：'+data.datalist[index].ESF010200T0_18_NAME+'</h5>' +
									  	  //'<div class="ui-block-b"><h6>'+'</h6></div>' +
									  	  //'</a>' +
									  	  '</li>').appendTo($('#esf010200m0_query_list'));
										/*
										var a = '<li data-role="list-divider">' +
									  	  '<h3>採購單號</h3>' +
									  	  '<h3 class="ui-li-aside">'+data.datalist[index].ESF010200T0_02+'</h3>' +
									  	  '</li>' +
									  	  '<li>' +
									  	  '<a href="#" data-transition="slide" >' +
									  	  '<h3>單據日期：'+data.datalist[index].ESF010200T0_03+' 採購人員：'+data.datalist[index].ESF010200T0_05+'</h3>' +
									  	  '<h3>廠商：'+data.datalist[index].ESF010200T0_11+' 廠別：'+data.datalist[index].ESF010200T0_06+'</h3>' +
									  	  '<h3>處理狀態：'+data.datalist[index].ESF010200T0_18+'</h3>' +
									  	  '</a>' +
									  	  '</li>';
									  	*/
										//alert(a);
									});
									
									//更新 ListView
									$('#esf010200m0_query_list').listview('refresh');
									
								}else{
									//顯示查無資料訊息
									//alert('show message!!'+data.message);
									//Create動態List
										$('<li data-role="list-divider">' +
									  	  '<h3>'+data.message+'</h3>' +
									  	  '</li>' +
									  	  '</li>').appendTo($('#esf010200m0_query_list'));
									
									//更新 ListView
									$('#esf010200m0_query_list').listview('refresh');
								}
							 },
					error: function(xhr, ajaxOptions, thrownError){
								//登入失敗, Ajax return Error code
						   		alert(xhr.status);
								alert(thrownError);
						   }
				});
		
	});
	
	$('#esf010200m0_Mobile_nav2').die('pagecreate').live('pagecreate', function(e) {
		//準備相關控制程式
		
		$('#esf010200m0_nav2_message').css("color","red");  //改變顯示訊息的CSS
		$('#esf010200m0_nav2_message_field').hide();  //隱藏訊息顯示區域
		
		//導向查詢條件頁面
		$('#esf010200m0_Mobile_nav1_button').bind('click', function(){
				
				//判斷是否執行click
				if(!esf010200m0_Mobile_nav1_button_click){
					return false;
				}
				
				//Ajax呼叫
				var cur_url = path+'/ESF010700M0_Mobile.do';
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "getAjaxData",
						   mobile_reqCode: "getForwardPath",
						   mobile_forward: "success"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#esf010200m0_Mobile_nav1_button').addClass('ui-disabled');
						esf010200m0_Mobile_nav1_button_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#esf010200m0_Mobile_nav1_button').removeClass('ui-disabled');
			        	esf010200m0_Mobile_nav1_button_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//使用 jQuery Mobile API導向
									//跳轉至功能列表頁面
									$.mobile.changePage( path+data.forward, {
											type: 'post',
											reverse: false,
											reloadPage: false
									});
								}
							 },
					error: function(xhr, ajaxOptions, thrownError){
								//登入失敗, Ajax return Error code
						   		alert(xhr.status);
								alert(thrownError);
						   }
				});
		});
		
		//導向功能列表頁面
		$('#function_page_nav2_back').bind('click', function(){
			
				//判斷是否執行click
				if(!function_page_nav2_back){
					return false;
				}
				//Ajax呼叫
				var cur_url = path+'/mobile_function.do';
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "getAjaxData",
						   mobile_reqCode: "getForwardPath"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#function_page_nav2_back').addClass('ui-disabled');
						function_page_nav2_back = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#function_page_nav2_back').removeClass('ui-disabled');
			        	function_page_nav2_back = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//使用 jQuery Mobile API導向
									//跳轉至功能列表頁面
									$.mobile.changePage( path+data.forward, {
											type: 'post',
											reloadPage: false,
											reverse: false
									});
								}
							 },
					error: function(xhr, ajaxOptions, thrownError){
								//登入失敗, Ajax return Error code
						   		alert(xhr.status);
								alert(thrownError);
						   }
				});
				
		});
		
	});
	
	//當隱藏時將Page由DOM中移除
	$('#esf010200m0_Mobile_nav2').die('pagehide').live('pagehide', function(e) {
		$(this).remove();
	})
	
</script>
</div>

</form>

<!-- Include mobile end setting -->
<jsp:include page="/mobile_templates/mobile_end.jsp" />
