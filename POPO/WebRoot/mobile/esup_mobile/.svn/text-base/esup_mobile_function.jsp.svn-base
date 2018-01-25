<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Include mobile header setting -->
<jsp:include page="/mobile_templates/mobile_begin.jsp" />

<form id="" action="" method="post" >
<input type="hidden" id="reqCode" name="reqCode" />
<div data-role="page" id="function_page" data-backbtn="false" >
	<input type="hidden" id="defaultQuery" name="defaultQuery" />
	<div data-role="header" data-position="fixed" >
    	<a href="#" id="sign_out" data-icon="back" class="ui-btn-left" data-transition="slidedown" >登出</a>
		<h1>賓瑋興業供應商平台</h1>
	</div>
	<div data-role="content" >
		<div id="message_field" data-role="fieldcontain" >
        	<h3 id="message" ></h3>
        </div>
		<div id="function_list" class="ui-grid-b" style="text-align: center;" >
        	<div class="ui-block-a ui-ems-image-button" >
            	<a id="firm" name="firm" href="#" data-transition="slide">
                <img id="img1" />
                <span class="icon-label">廠商基本資料</span>
                </a>
            </div>
        	<div class="ui-block-b ui-ems-image-button" >
            	<a id="purchase" name="purchase" href="#" data-transition="slide">
                <img id="img2" />
                <span class="icon-label">採購交貨作業</span>
                </a>
            </div>
            <div class="ui-block-c ui-ems-image-button" >
            	<a id="subcontract" name="subcontract" href="#" data-transition="slide">
                <img id="img3" />
                <span class="icon-label">委外管理作業</span>
                </a>
            </div>
            <div class="ui-block-a ui-ems-image-button" >
            	<a id="transport" name="transport" href="#" data-transition="slide">
                <img id="img4" />
                <span class="icon-label">加工廠運送查詢</span>
                </a>
            </div>
            <div class="ui-block-b ui-ems-image-button" >
            	<a id="machining" name="machining" href="#" data-transition="slide">
                <img id="img5" />
                <span class="icon-label">加工即時查詢</span>
                </a>
            </div>
            <div class="ui-block-c ui-ems-image-button" >
            	<a id="query_purchase" name="query_purchase" href="#" data-transition="slide">
                <img id="img6" />
                <span class="icon-label">採購即時查詢</span>
                </a>
            </div>
            <div class="ui-block-a ui-ems-image-button" >
            	<a id="pendingform" name="pendingform" href="#" data-transition="slide">
                <img id="img7" />
                <span class="icon-label">加工廠交貨作業</span>
                </a>
            </div>
        </div>    
	</div>
	<div data-role="footer" class="ui-navbar-ems-index" data-position="fixed">
<%--		<div class="spon-footer-bar" >--%>
<%--        </div>--%>
	</div>
	<!-- JavaScript -->
<script type="text/javascript">
	
	var sign_out_click = true;
	var firm_click = true;
	var purchase_click = true;
	var subcontract_click = true;
	var pendingform_click = true;
	
	$('#function_page').die('pagecreate').live('pagecreate', function(e) {
		//準備相關控制程式
		var path = $("#web_current_contextpath").val();
		$('#message').css("color","red");  //改變顯示訊息的CSS
		$('#message_field').hide();  //隱藏訊息顯示區域
		/*
		$('#img1').attr('src', path+'/config/ems_javascript/images/EMS_Icon_72.png' );
		$('#img2').attr('src', path+'/config/ems_javascript/images/EMS_Icon_72.png' );
		$('#img3').attr('src', path+'/config/ems_javascript/images/EMS_Icon_72.png' );
		$('#img4').attr('src', path+'/config/ems_javascript/images/EMS_Icon_72.png' );
		*/
		$('#img1').attr('src', path+'/config/ems_javascript/images/SUP_Icon_72.png' );
		$('#img2').attr('src', path+'/config/ems_javascript/images/SUP_Icon_72.png' );
		$('#img3').attr('src', path+'/config/ems_javascript/images/SUP_Icon_72.png' );
		$('#img4').attr('src', path+'/config/ems_javascript/images/SUP_Icon_72.png' );
		$('#img5').attr('src', path+'/config/ems_javascript/images/SUP_Icon_72.png' );
		$('#img6').attr('src', path+'/config/ems_javascript/images/SUP_Icon_72.png' );
		$('#img7').attr('src', path+'/config/ems_javascript/images/SUP_Icon_72.png' );
		
		//動態建立功能列表
		/*
		for(i=1; i<=6; i++){
		var ui_block = "";
		if(i%3 == 0){
			ui_block = "c";
		}else if(i%3 == 1){
			ui_block = "a";
		}else{
			ui_block = "b";
		}
		$("<div class='ui-block-"+ui_block+" ui-ems-image-button' >" +
		  "<a href='#sign_page' data-transition='slide'>" +
		  "<img id='img"+i+"' src='"+path+"/config/ems_javascript/images/EMS_Icon_72.png' />" +
		  "<span class='icon-label'>HELLO"+i+"</span>" +
		  "</a>" +
		  "</div>").appendTo($('#function_list'));
		}
		*/
		
		/*
		$("<div class='ui-block-b ui-ems-image-button' >" +
		  "<a href='#sign_page' data-transition='slide'>" +
		  "<img id='img3' src='"+path+"/config/ems_javascript/images/EMS_Icon_72.png' />" +
		  "<span class='icon-label'>HELLO2</span>" +
		  "</a>" +
		  "</div>").appendTo($('#function_list'));
		
		$("<div class='ui-block-c ui-ems-image-button' >" +
		  "<a href='#sign_page' data-transition='slide'>" +
		  "<img id='img3' src='"+path+"/config/ems_javascript/images/EMS_Icon_72.png' />" +
		  "<span class='icon-label'>HELLO3</span>" +
		  "</a>" +
		  "</div>").appendTo($('#function_list'));
		*/
		/*
		.append(
			$('<a>',{
			href: '#sign_page',
			data-transition: 'slide'
			})
		);
		.append(
				$('<img>',{
					id: 'img4',
					src: path+'/config/ems_javascript/images/EMS_Icon_72.png'
				})
			)
		$('<a>',{
			href: '#sign_page',
			data-transition: 'slide'
		})
		$('<img>',{
			id: 'img4',
			src: path+'/config/ems_javascript/images/EMS_Icon_72.png'
		}).appendTo()
		*/
		
		//導向廠商基本資料
		$('#firm').bind('click', function(){
				
				//設定執行預設查詢
				$('#defaultQuery').val(true);
			
				//判斷是否執行click
				if(!firm_click){
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
						$('#firm').addClass('ui-disabled');
						firm_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#firm').removeClass('ui-disabled');
			        	firm_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//跳轉至採購單頁面 
									$.mobile.changePage(path+data.forward, {
											type: 'post',
											reverse: false,
											transition: "slide",
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
		
		//導向採購單
		$('#purchase').bind('click', function(){
				
				//設定執行預設查詢
				$('#defaultQuery').val(true);
			
				//判斷是否執行click
				if(!purchase_click){
					return false;
				}
				//Ajax呼叫
				var cur_url = path+'/ESF010200M0_Mobile.do';
				
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
						$('#purchase').addClass('ui-disabled');
						purchase_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#purchase').removeClass('ui-disabled');
			        	purchase_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								//alert(path+data.forward);
								if(data.success){
									//alert(path+data.forward);
									//跳轉至採購單頁面 
									$.mobile.changePage(path+data.forward, {
											type: 'post',
											reverse: false,
											transition: "slide",
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
		
		//導向委外加工單
		$('#subcontract').bind('click', function(){
				
				//設定執行預設查詢
				$('#defaultQuery').val(true);
			
				//判斷是否執行click
				if(!subcontract_click){
					return false;
				}
				//Ajax呼叫
				var cur_url = path+'/ESF010300M0_Mobile.do';
				
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
						$('#subcontract').addClass('ui-disabled');
						subcontract_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#subcontract').removeClass('ui-disabled');
			        	subcontract_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//跳轉至採購單頁面 
									$.mobile.changePage(path+data.forward, {
											type: 'post',
											reverse: false,
											transition: "slide",
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
		
		//導向加工廠運送查詢
		$('#transport').bind('click', function(){
				
				//設定執行預設查詢
				$('#defaultQuery').val(true);
			
				//判斷是否執行click
				if(!firm_click){
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
						   mobile_forward: "success_nav2"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#transport').addClass('ui-disabled');
						firm_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#transport').removeClass('ui-disabled');
			        	firm_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//跳轉至採購單頁面 
									$.mobile.changePage(path+data.forward, {
											type: 'post',
											reverse: false,
											transition: "slide",
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
		
		//導向加工即時查詢
		$('#machining').bind('click', function(){
				
				//設定執行預設查詢
				$('#defaultQuery').val(true);
			
				//判斷是否執行click
				if(!firm_click){
					return false;
				}
				//Ajax呼叫
				var cur_url = path+'/ESF010500M0_Mobile.do';
				
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
						$('#machining').addClass('ui-disabled');
						firm_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#machining').removeClass('ui-disabled');
			        	firm_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//跳轉至採購單頁面 
									$.mobile.changePage(path+data.forward, {
											type: 'post',
											reverse: false,
											transition: "slide",
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
		
		//導向採購即時查詢
		$('#query_purchase').bind('click', function(){
				
				//設定執行預設查詢
				$('#defaultQuery').val(true);
			
				//判斷是否執行click
				if(!firm_click){
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
						   mobile_forward: "success_nav2"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#query_purchase').addClass('ui-disabled');
						firm_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#query_purchase').removeClass('ui-disabled');
			        	firm_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//跳轉至採購單頁面 
									$.mobile.changePage(path+data.forward, {
											type: 'post',
											reverse: false,
											transition: "slide",
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
		
		//導向加工交貨
		$('#pendingform').bind('click', function(){
				
				//設定執行預設查詢
				$('#defaultQuery').val(true);
			
				//判斷是否執行click
				if(!pendingform_click){
					return false;
				}
				//Ajax呼叫
				var cur_url = path+'/ESF010400M0_Mobile.do';
				
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
						$('#pendingform').addClass('ui-disabled');
						pendingform_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#pendingform').removeClass('ui-disabled');
			        	pendingform_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//跳轉至採購單頁面 
									$.mobile.changePage(path+data.forward, {
											type: 'post',
											reverse: false,
											transition: "slide",
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
		
		//登出
		$('#sign_out').bind('click', function(){
			
				//判斷是否執行click
				if(!sign_out_click){
					return false;
				}
				//Ajax呼叫
				var cur_url = path+'/mobile_login.do';
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "logout"},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#sign_out').addClass('ui-disabled');
						sign_out_click = false;
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#sign_out').removeClass('ui-disabled');
			        	sign_out_click = true;
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									//alert(data.forward);
									//跳轉至Login頁面 
									$.mobile.changePage( path+data.forward, {
											type: 'post',
											reverse: false,
											reloadPage: false,
											transition: "slidedown"
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
	$('#function_page').die('pagehide').live('pagehide', function(e) {
		$(this).remove();
	});
	
</script>
</div>

</form>

<!-- Include mobile end setting -->
<jsp:include page="/mobile_templates/mobile_end.jsp" />
