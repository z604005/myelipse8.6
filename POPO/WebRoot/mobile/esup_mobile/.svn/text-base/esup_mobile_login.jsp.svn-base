<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!-- Include mobile header setting -->
<jsp:include page="/mobile_templates/mobile_begin.jsp" />

<form id="login_form" action="mobile_login.do" method="post" >
<input type="hidden" id="reqCode" name="reqCode" />
<div data-role="page" id="login_page" >
	<div data-role="header" data-position="fixed">
		<h1>賓瑋興業供應商平台</h1>
<%--        <a href="#" data-icon="information" data-iconpos="notext" class="ui-btn-right" >PW資訊</a>--%>
	</div>
	<div data-role="content">
		<div data-role="fieldcontain" >
        	<label for="comp_id">公司代碼：</label>
            <input type="text" class="ui-input-text" 
            	   id="comp_id" name="comp_id" placeholder="請輸入公司代碼..." value="A99999" required />
        </div>
		<div data-role="fieldcontain" >
        	<label for="user_id">帳號：</label>
            <input type="text" class="ui-input-text" 
            	   id="user_id" name="user_id" placeholder="請輸入帳號..." value="" required />
        </div>
        <div data-role="fieldcontain" >
        	<label for="password">密碼：</label>
            <input type="password" class="ui-input-text" 
            	   id="password" name="password" placeholder="請輸入密碼..." value="" required />
        </div>
        <div data-role="fieldcontain" >
        	<h3 id="login_message" ></h3>
        	<button id="sign_in" data-transition="slideup" >登    入</button>
        </div>
	</div>
	<div data-role="footer" class="ui-navbar-ems-index" data-position="fixed" >
    	<!--<div class="spon-footer-bar" >	
        </div>-->
	</div>
	<!-- JavaScript -->
<script type="text/javascript">
	
	$('#login_page').die('pagecreate').live('pagecreate', function(e) {
		//準備相關控制程式
		var path = $("#web_current_contextpath").val();
		$('#login_message').css("color","red");  //改變顯示訊息的CSS
		//登入
		$('#sign_in').bind('click', function(){
			if($('#user_id').val() != "" && 
			   $('#password').val() != "" &&
			   $('#comp_id').val() != "" ){
				//Ajax呼叫驗證帳號密碼
				var cur_url = path+'/mobile_login.do';
				
				//送出 jQuery Ajax 要求到伺服器
				$.ajax({
					type: 'POST',
					url: cur_url,
					data: {reqCode: "login", 
						   comp_id: $('#comp_id').val(),
						   user_id: $('#user_id').val(),
						   password: $('#password').val()},
					dataType: 'json',
					beforeSend: function(request){
						//Open loading mask
				        $.mobile.showPageLoadingMsg();
						$('#sign_in').attr("disabled",true);
			        },
			        complete: function(request, status){
			        	//Close loading mask
				        $.mobile.hidePageLoadingMsg();
			        	$('#sign_in').attr("disabled",false);
			        },
					success: function(data){
								//Ajax 執行成功
								if(data.success){
									$('#comp_id').val('');
									$('#user_id').val('');
									$('#password').val('');
									$('#login_message').text('');
									//跳轉至Function頁面
									$.mobile.changePage( path+data.forward, {
											type: 'post',
											reloadPage: false,
											transition: "slideup"
									});
								}else{
									$('#login_message').text(data.message);
								}
							 },
					error: function(xhr, ajaxOptions, thrownError){
								//登入失敗, Ajax return Error code
						   		alert(xhr.status);
								alert(thrownError);
						   }
				});
				
				
			}else{
				//alert("請輸入公司代碼、帳號與密碼!!");
				$('#login_message').text('請輸入公司代碼、帳號與密碼!!');
				return false;
			}
		});
		
    });
	
	//當隱藏時將Page由DOM中移除
	$('#login_page').die('pagehide').live('pagehide', function(e) {
		$(this).remove();
	});
		
</script>
</div>

</form>

<!-- Include mobile end setting -->
<jsp:include page="/mobile_templates/mobile_end.jsp" />
