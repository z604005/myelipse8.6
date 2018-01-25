<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Include mobile header setting -->
<jsp:include page="/mobile_templates/mobile_begin.jsp" />

<form id="" action="" method="post" >
<input type="hidden" id="reqCode" name="reqCode" />
<div data-role="page" id="esf010400m0_Mobile" data-add-back-btn="false" >
	<div data-role="header" data-position="fixed" >
		<a id="function_page_back" name="function_page_nav_back" href="#" data-icon="arrow-l" class="ui-btn-left" >功能頁</a>
		<h1>加工交貨作業</h1>
	</div>
	<div data-role="content" >
		<div id="esf010400m0_message_field" data-role="fieldcontain" >
        	<h3 id="esf010400m0_message" ></h3>
        </div>
        <ul id="esf010400m0_query_list" data-role="listview" data-inset="true" >
        </ul>
	</div>
	<div data-role="footer" data-position="fixed">
		<div data-role="navbar">
				<ul>
					<li><a id="esf010400m0_Mobile_nav1_button" name="esf010400m0_Mobile_nav1_button" 
						   href="#" class="ui-btn-active ui-state-persist" >採購單</a>
					</li>
					<li><a id="esf010400m0_Mobile_nav2_button" name="esf010400m0_Mobile_nav2_button" 
						   href="#" >委外加工單</a>
					</li>
				</ul>
		</div>
	</div>
	<!-- JavaScript -->
<script type="text/javascript">
	
	var esf010400m0_Mobile_nav1_button_click = true;
	var esf010400m0_Mobile_nav2_button_click = true;
	var function_page_back_click = true;
	var path = $("#web_current_contextpath").val();
	
	$('#esf010400m0_Mobile').die('pagebeforeshow').live('pagebeforeshow', function(e, ui) {
		//判斷是否執行預設Function
		if($('#defaultQuery').val()){
			//預設產生採購單ListView
			$('#esf010400m0_Mobile_nav1_button').click();
		}
	});
	
	//產生採購單List View
	function gen_esf010400m0_nav1(data){
		
		//清空List View
		$('#esf010400m0_query_list').empty();
		
		if(data.success){
			//使用 jQuery Mobile API
			//alert(data.message);
			$.each( data.datalist, function(index, value){									
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
			  	  '</li>' +
			  	  '<li class="ui-body ui-body-b">' +
			  	  '<fieldset class="ui-grid-a">' +
			  	  '<div class="ui-block-a">' +
			  	  //'<button data-theme="d" data-ajax="false" onclick="" >核准</button>' +
			  	  '<a href="#" data-role="button" data-theme="d" onclick="execute_esf010400m0(\'approve\',\'PW_SUP_FLOW_0001\',\''+data.datalist[index].ESF010200T0_02+'\'); return false;" >核准</a>' +
			  	  '</div>' +
			  	  '<div class="ui-block-b">' +
			  	  '<a href="#" data-role="button" data-theme="a" onclick="execute_esf010400m0(\'reject\',\'PW_SUP_FLOW_0001\',\''+data.datalist[index].ESF010200T0_02+'\'); return false;" >駁回</a>' +
			  	  '</div>' +
			  	  '</fieldset>' +
			  	  '</li>').appendTo($('#esf010400m0_query_list'));
				$('#esf010400m0_query_list').trigger('create');
			});
			
			//execute_esf010400m0(\'approve\',\'PW_SUP_FLOW_0001\',\''+data.datalist[index].ESF010200T0_02+'\');
				
			//更新 ListView
			$('#esf010400m0_query_list').listview('refresh');
				
		}else{
			//顯示查無資料訊息
			//alert('show message!!'+data.message);
			//Create動態List
			$('<li data-role="list-divider">' +
			  '<h3>'+data.message+'</h3>' +
		  	  '</li>' +
		  	  '</li>').appendTo($('#esf010400m0_query_list'));
		
			//更新 ListView
			$('#esf010400m0_query_list').listview('refresh');
		}
		
	}
	
	//產生委外加工單List View
	function gen_esf010400m0_nav2(data){
		
		//清空List View
		$('#esf010400m0_query_list').empty();
		
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
			  	  '</li>' +
			  	  '<li class="ui-body ui-body-b">' +
			  	  '<fieldset class="ui-grid-a">' +
			  	  '<div class="ui-block-a">' +
			  	  '<button data-theme="d" ' +
			  	  '<a href="#" data-role="button" data-theme="d" onclick="execute_esf010400m0(\'approve\',\'PW_SUP_FLOW_0002\',\''+data.datalist[index].ESF010300T0_02+'\'); return false;" >核准</a>' +
			  	  '</div>' +
			  	  '<div class="ui-block-b">' +
			  	  '<a href="#" data-role="button" data-theme="a" onclick="execute_esf010400m0(\'reject\',\'PW_SUP_FLOW_0002\',\''+data.datalist[index].ESF010300T0_02+'\'); return false;" >駁回</a>' +
			  	  '</div>' +
			  	  '</fieldset>' +
			  	  '</li>').appendTo($('#esf010400m0_query_list'));
				$('#esf010400m0_query_list').trigger('create');
				
				//execute_esf010400m0(\'approve\',\'PW_SUP_FLOW_0002\',\''+data.datalist[index].ESF010300T0_02+'\'); return false;"
				
				/*
				var aa = '<li data-role="list-divider">' +
			  	  '<h3>委外加工單號</h3>' +
			  	  '<h3 class="ui-li-aside">'+data.datalist[index].ESF010300T0_02+'</h3>' +
			  	  '</li>' +
			  	  '<li data-role="fieldcontain" >' +
			  	  //'<a href="#" data-transition="slide" >' +
			  	  '<h5>開單日期：'+data.datalist[index].ESF010300T0_03+'</h5>' +
			  	  '<h5>廠商：'+data.datalist[index].ESF010300T0_12+'</h5>' +
			  	  '<h5>生產廠別：'+data.datalist[index].ESF010300T0_08+'</h5>' +
			  	  '<h5>產品：'+data.datalist[index].ESF010300T0_06+'</h5>' +
			  	  '<h5>預計產量：'+data.datalist[index].ESF010300T0_20+'</h5>' +
			  	  '<h5>開工日期：'+data.datalist[index].ESF010300T0_15+'</h5>' +
			  	  '<h5>完工日期：'+data.datalist[index].ESF010300T0_16+'</h5>' +
			  	  '<h5>處理狀態：'+data.datalist[index].ESF010300T0_27+'</h5>' +
			  	  //'<div class="ui-block-b"><h6>'+'</h6></div>' +
			  	  //'</a>' +
			  	  '</li>' +
			  	  '<li class="ui-body ui-body-b">' +
			  	  '<fieldset class="ui-grid-a">' +
			  	  '<div class="ui-block-a">' +
			  	  '<button data-theme="d" >核准</button>' +
			  	  '</div>' +
			  	  '<div class="ui-block-b">' +
			  	  '<button data-theme="a" >駁回</button>' +
			  	  '</div>' +
			  	  '</fieldset>' +
			  	  '</li>';
			  	   $('#esf010400m0_query_list').append(aa).trigger('create');
				*/
			});
				
			//更新 ListView
			$('#esf010400m0_query_list').listview('refresh');
				
		}else{
			//顯示查無資料訊息
			//alert('show message!!'+data.message);
			//Create動態List
			$('<li data-role="list-divider">' +
			  '<h3>'+data.message+'</h3>' +
		  	  '</li>' +
		  	  '</li>').appendTo($('#esf010400m0_query_list'));
			
			//更新 ListView
			$('#esf010400m0_query_list').listview('refresh');
		}
	}
	
	//核准駁回頁面的處理
	function execute_esf010400m0(mobile_reqCode_name, flow_no, form_no){
			//Ajax呼叫
			var local_path = $("#web_current_contextpath").val();
			var cur_url = local_path+'/ESF010400M0_Mobile.do';
			
			//alert(mobile_reqCode_name);
			//alert(flow_no);
			//alert(form_no);
			//alert(cur_url);
			
			//送出 jQuery Ajax 要求到伺服器
			$.ajax({
				type: 'POST',
				url: cur_url,
				data: {reqCode: "getAjaxData",
					   mobile_reqCode: mobile_reqCode_name,
					   FLOW_NO: flow_no,
					   FORM_NO: form_no},
				dataType: 'json',
				success: function(data){
							//Ajax 執行成功
							//使用 jQuery Mobile API
							//產生ListView
							if(flow_no == "PW_SUP_FLOW_0001"){
								gen_esf010400m0_nav1(data);
							}else if(flow_no == "PW_SUP_FLOW_0002"){
								gen_esf010400m0_nav2(data);
							}
						 },
				error: function(xhr, ajaxOptions, thrownError){
							//登入失敗, Ajax return Error code
					   		alert(xhr.status);
							alert(thrownError);
					   }
			});
	}
	
	$('#esf010400m0_Mobile').die('pagecreate').live('pagecreate', function(e) {
		//準備相關控制程式
		
		$('#esf010400m0_message').css("color","red");  //改變顯示訊息的CSS
		$('#esf010400m0_message_field').hide();  //隱藏訊息顯示區域
		
		//導向採購單頁面
		$('#esf010400m0_Mobile_nav1_button').bind('click', function(){
				
				//判斷是否執行click
				if(!esf010400m0_Mobile_nav1_button_click){
					return false;
				}
				
				//Ajax呼叫
				var cur_url = path+'/ESF010400M0_Mobile.do';
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "getAjaxData",
						   mobile_reqCode: "executeQueryData_nav1"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#esf010400m0_Mobile_nav1_button').addClass('ui-disabled');
						esf010400m0_Mobile_nav1_button_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#esf010400m0_Mobile_nav1_button').removeClass('ui-disabled');
			        	esf010400m0_Mobile_nav1_button_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								//使用 jQuery Mobile API
								//產生ListView
								gen_esf010400m0_nav1(data);
							 },
					error: function(xhr, ajaxOptions, thrownError){
								//登入失敗, Ajax return Error code
						   		alert(xhr.status);
								alert(thrownError);
						   }
				});
		});
		
		//導向委外加工單頁面
		$('#esf010400m0_Mobile_nav2_button').bind('click', function(){
				
				//判斷是否執行click
				if(!esf010400m0_Mobile_nav2_button_click){
					return false;
				}
				
				//Ajax呼叫
				var cur_url = path+'/ESF010400M0_Mobile.do';
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "getAjaxData",
						   mobile_reqCode: "executeQueryData_nav2"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#esf010400m0_Mobile_nav2_button').addClass('ui-disabled');
						esf010400m0_Mobile_nav2_button_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#esf010400m0_Mobile_nav2_button').removeClass('ui-disabled');
			        	esf010400m0_Mobile_nav2_button_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								//使用 jQuery Mobile API
								//產生ListView
								gen_esf010400m0_nav2(data);
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
	$('#esf010400m0_Mobile').die('pagehide').live('pagehide', function(e) {
		$(this).remove();
	})
	
</script>
</div>

</form>

<!-- Include mobile end setting -->
<jsp:include page="/mobile_templates/mobile_end.jsp" />
