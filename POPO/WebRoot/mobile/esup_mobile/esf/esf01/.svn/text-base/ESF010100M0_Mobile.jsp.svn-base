<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Include mobile header setting -->
<jsp:include page="/mobile_templates/mobile_begin.jsp" />

<form id="" action="" method="post" >
<input type="hidden" id="reqCode" name="reqCode" />
<div data-role="page" id="esf010100m0_Mobile_nav1" data-add-back-btn="false" >
	<input type="hidden" id="defaultQuery" name="defaultQuery" />
	<div data-role="header" data-position="fixed" >
		<a id="function_page_back" name="function_page_back" href="#" data-icon="arrow-l" class="ui-btn-left" >功能頁</a>
		<h1>廠商基本資料</h1>
		<a id="esf010100m0_query" name="esf010100m0_query" href="#" data-icon="search" class="ui-btn-right" >查詢</a>
	</div>
	<div data-role="content" >
		<div id="esf010100m0_message_field" data-role="fieldcontain" >
        	<h3 id="esf010100m0_message" ></h3>
        </div>
        <div data-role="fieldcontain" >
        	<label for="ESF010100T0_02">廠商編號：</label>
        	<input type="text" class="ui-input-text" 
            	   id="ESF010100T0_02" name="ESF010100T0_02" placeholder="請輸入廠商編號..." value="" />
            
        </div>
        <div data-role="fieldcontain" >
        	<label for="ESF010100T0_05">廠商名稱：</label>
        	<input type="text" class="ui-input-text" 
            	   id="ESF010100T0_05" name="ESF010100T0_05" placeholder="請輸入廠商名稱..." value="" />
            
        </div>
        <div data-role="fieldcontain" >
        	<label for="ESF010100T0_11">聯絡人：</label>
        	<input type="text" class="ui-input-text" 
            	   id="ESF010100T0_11" name="ESF010100T0_11" placeholder="請輸入聯絡人..." value="" />
            
        </div>
        <div data-role="fieldcontain" >
        	<label for="ESF010100T0_12">電話：</label>
        	<input type="text" class="ui-input-text" 
            	   id="ESF010100T0_12" name="ESF010100T0_12" placeholder="請輸入電話..." value="" />
            
        </div>
        <div data-role="fieldcontain" >
        	<label for="ESF010100T0_16">電子郵件：</label>
        	<input type="text" class="ui-input-text" 
            	   id="ESF010100T0_16" name="ESF010100T0_16" placeholder="請輸入電子郵件..." value="" />
            
        </div>
        <div data-role="fieldcontain" >
        	<label for="ESF010100T0_08">地址：</label>
        	<input type="text" class="ui-input-text" 
            	   id="ESF010100T0_08" name="ESF010100T0_08" placeholder="請輸入地址..." value="" />
            
        </div>
	</div>
	<div data-role="footer" data-position="fixed">
		<div data-role="navbar">
				<ul>
					<li><a id="" name="" 
						   href="#" class="ui-btn-active ui-state-persist" >查詢條件</a>
					</li>
					<li><a id="esf010100m0_Mobile_query_default" name="esf010100m0_Mobile_query_default" 
						   href="#" data-prefetch="true">查詢結果</a>
					</li>
				</ul>
		</div>
	</div>
	<!-- JavaScript -->
<script type="text/javascript">

	var function_page_back_click = true;
	var esf010100m0_query_click = true;
	var esf010100m0_Mobile_query_default_click = true;
	
	$('#esf010100m0_Mobile_nav1').die('pagecreate').live('pagecreate', function(e) {
		//準備相關控制程式
		var path = $("#web_current_contextpath").val();
		$('#esf010100m0_message').css("color","red");  //改變顯示訊息的CSS
		$('#esf010100m0_message_field').hide();  //隱藏訊息顯示區域
		
		//執行預設查詢
		$('#esf010100m0_Mobile_query_default').bind('click', function(){
				
				//設定執行預設查詢
				$('#defaultQuery').val(true);
			
				//判斷是否執行click
				if(!esf010100m0_Mobile_query_default_click){
					return false;
				}
				
				//Ajax呼叫
				var cur_url = path+'/ESF010100M0_Mobile.do';
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "getAjaxData",
						   mobile_reqCode: "getForwardPath",
						   mobile_forward: "success_nav2"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#esf010100m0_Mobile_query_default').addClass('ui-disabled');
						esf010100m0_Mobile_query_default_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#esf010100m0_Mobile_query_default').removeClass('ui-disabled');
			        	esf010100m0_Mobile_query_default_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//使用 jQuery Mobile API導向
									//跳轉至功能列表頁面
									$.mobile.changePage( path+data.forward, {
											type: 'post',
											reverse: true,
											reloadPage: false,
											data:{}
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
		
		//執行查詢
		$('#esf010100m0_query').bind('click', function(){
				
				//判斷是否執行click
				if(!esf010100m0_query_click){
					return false;
				}
				
				//Ajax呼叫
				var cur_url = path+'/ESF010100M0_Mobile.do';
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "getAjaxData",
						   mobile_reqCode: "getForwardPath",
						   mobile_forward: "success_nav2"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#esf010100m0_query').addClass('ui-disabled');
						esf010100m0_query_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#esf010100m0_query').removeClass('ui-disabled');
			        	esf010100m0_query_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//使用 jQuery Mobile API導向
									//跳轉至功能列表頁面
									$.mobile.changePage( path+data.forward, {
											type: 'post',
											reverse: true,
											reloadPage: false,
											data:{
												ESF010100T0_02: $('#ESF010100T0_02').val(),
 		   										ESF010100T0_05: $('#ESF010100T0_05').val(),
		   										ESF010100T0_11: $('#ESF010100T0_11').val(),
		   										ESF010100T0_12: $('#ESF010100T0_12').val(),
		   										ESF010100T0_16: $('#ESF010100T0_16').val(),
		   										ESF010100T0_08: $('#ESF010100T0_08').val()
											}
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
		$('#function_page_back').bind('click', function(){
			
				//判斷是否執行click
				if(!function_page_back_click){
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
						$('#function_page_back').addClass('ui-disabled');
						function_page_back_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#function_page_back').removeClass('ui-disabled');
			        	function_page_back_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//使用 jQuery Mobile API導向
									//跳轉至功能列表頁面
									$.mobile.changePage( path+data.forward, {
											type: 'post',
											reverse: false,
											relaodPage: false
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
	$('#esf010100m0_Mobile_nav1').die('pagehide').live('pagehide', function(e) {
		$(this).remove();
	});
	
</script>
</div>

</form>

<!-- Include mobile end setting -->
<jsp:include page="/mobile_templates/mobile_end.jsp" />
