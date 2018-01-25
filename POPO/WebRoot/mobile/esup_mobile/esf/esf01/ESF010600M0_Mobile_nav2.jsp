<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Include mobile header setting -->
<jsp:include page="/mobile_templates/mobile_begin.jsp" />

<form id="" action="" method="post" >
<input type="hidden" id="reqCode" name="reqCode" />
<div data-role="page" id="esf010300m0_Mobile_nav2" data-add-back-btn="false" >
	<div data-role="header" data-position="fixed" >
		<a id="function_page_nav2_back" name="function_page_nav2_back" href="#" data-icon="arrow-l" class="ui-btn-left" >功能頁</a>
		<h1>加工廠運送查詢</h1>
	</div>
	<div data-role="content" >
		<div id="esf010300m0_nav2_message_field" data-role="fieldcontain" >
        	<h3 id="esf010300m0_nav2_message" ></h3>
        </div>
        <ul id="esf010300m0_query_list" data-role="listview" data-inset="true" >
        </ul>
	</div>
	<div data-role="footer" data-position="fixed">
		<div data-role="navbar">
				<ul>
					<li><a id="esf010300m0_Mobile_nav1_button" name="esf010300m0_Mobile_nav1_button" 
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
	
	var esf010300m0_Mobile_nav1_button_click = true;
	var function_page_nav2_back = true;
	var path = $("#web_current_contextpath").val();
	
	$('#esf010300m0_Mobile_nav2').die('pagebeforeshow').live('pagebeforeshow', function(e, ui) {
		
			//Ajax呼叫
			var cur_url = path+'/ESF010600M0_Mobile.do';
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
					   ESF010300T0_02: $('#ESF010300T0_02').val(),
 		   			   ESF010300T0_04: $('#ESF010300T0_04').val(),
		   			   ESF010300T0_04_END: $('#ESF010300T0_04_END').val(),
		   			   ESF010300T0_11: $('#ESF010300T0_11').val(),
		   			   ESF010300T0_12: $('#ESF010300T0_12').val(),
		   			   ESF010300T0_27: $('#ESF010300T0_27').val()},
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
									//Create動態List
									$('<li data-role="list-divider">' +
								  	  '<h3>委外加工單號</h3>' +
								  	  '<h3 class="ui-li-aside">'+data.datalist[index].ESF010300T0_02+'</h3>' +
								  	  '</li>' +
								  	  '<li data-role="fieldcontain" >' +
								  	  //'<a href="#" data-transition="slide" >' +
								  	  '<h5>開單日期：'+data.datalist[index].ESF010300T0_04+'</h5>' +
								  	  '<h5>廠商：'+data.datalist[index].ESF010300T0_12+'</h5>' +
								  	  '<h5>生產廠別：'+data.datalist[index].ESF010300T0_08+'</h5>' +
								  	  '<h5>產品：'+data.datalist[index].ESF010300T0_06+'</h5>' +
								  	  '<h5>預計產量：'+data.datalist[index].ESF010300T0_20+'</h5>' +
								  	  '<h5>開工日期：'+data.datalist[index].ESF010300T0_15+'</h5>' +
								  	  '<h5>完工日期：'+data.datalist[index].ESF010300T0_16+'</h5>' +
								  	  '<h5>處理狀態：'+data.datalist[index].ESF010300T0_27_NAME+'</h5>' +
								  	  //'<div class="ui-block-b"><h6>'+'</h6></div>' +
								  	  //'</a>' +
								  	  '</li>').appendTo($('#esf010300m0_query_list'));
								});
									
								//更新 ListView
								$('#esf010300m0_query_list').listview('refresh');
								
							}else{
								//顯示查無資料訊息
								//alert('show message!!'+data.message);
								//Create動態List
								$('<li data-role="list-divider">' +
							 	  '<h3>'+data.message+'</h3>' +
							  	  '</li>' +
							  	  '</li>').appendTo($('#esf010300m0_query_list'));
								
								//更新 ListView
								$('#esf010300m0_query_list').listview('refresh');
							}
						},
				error: function(xhr, ajaxOptions, thrownError){
							//登入失敗, Ajax return Error code
					   		alert(xhr.status);
							alert(thrownError);
						}
			});
	});
	
	$('#esf010300m0_Mobile_nav2').die('pagecreate').live('pagecreate', function(e) {
		//準備相關控制程式
		
		$('#esf010300m0_nav2_message').css("color","red");  //改變顯示訊息的CSS
		$('#esf010300m0_nav2_message_field').hide();  //隱藏訊息顯示區域
		
		//導向查詢條件頁面
		$('#esf010300m0_Mobile_nav1_button').bind('click', function(){
				
				//判斷是否執行click
				if(!esf010300m0_Mobile_nav1_button_click){
					return false;
				}
				
				//Ajax呼叫
				var cur_url = path+'/ESF010600M0_Mobile.do';
				
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
						$('#esf010300m0_Mobile_nav1_button').addClass('ui-disabled');
						esf010300m0_Mobile_nav1_button_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#esf010300m0_Mobile_nav1_button').removeClass('ui-disabled');
			        	esf010300m0_Mobile_nav1_button_click = true;
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
	$('#esf010300m0_Mobile_nav2').die('pagehide').live('pagehide', function(e) {
		$(this).remove();
	})
	
</script>
</div>

</form>

<!-- Include mobile end setting -->
<jsp:include page="/mobile_templates/mobile_end.jsp" />
