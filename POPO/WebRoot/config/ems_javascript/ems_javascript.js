/**
 * EMS Javascript
 * 
 * 
 * Created by Spontech
 */

/**
 * 顯示 EMS Wait
 */
function showEMSWait(){
	//開啟讀取動畫
	var waitobj = parent.app;
	if(waitobj != null){
		waitobj.openWait();
	}else{
		if($("#content") != null){
			$("#content").mask("讀取中...");  //開啟遮罩
		}
	}
}

/**
 * 關閉 EMS Wait
 */
function closeEMSWait(){
	//關閉讀取動畫
	var waitobj = parent.app;
	if(waitobj != null){
		waitobj.closeWait();
	}else{
		if($("#content") != null){
			if($("#content").isMasked()){
				$("#content").unmask();  //解除遮罩		
			}
		}
	}
}

/**
 * EMS StrutsLayout View 結束時要呼叫的 Javascript Method
 */
function endView(){
	//關閉讀取動畫
	closeEMSWait();
	window.status = "Finished";
}

/**
 * 顯示 EMS Wait 前先確認
 * @param {Object} obj
 * @return {TypeName} 
 */
function confirmShowEMSWait(msg){
	
	if(confirm(msg)){
		return showEMSWait();
	}else{
		return false;
	}
}

/**
 * Confirm Delete
 * @param {Object} msg
 * @return {TypeName} 
 */
function confirmDelData(msg){
	
	if(confirm(msg)){
		return showEMSWait();
	}else{
		return false;
	}
}

/**
 * Setting Detail Action&Type
 * @param {Object} action
 * @param {Object} type
 */
function ctrlDetail(action, type){
	//設定 Detail formAction
	document.getElementById("Detail_formAction").value = action;
	//設定 Detail formType
	document.getElementById("Detail_formType").value = type;
}

/**
 * Control the LayoutPopup
 */
function ctrlOpenLayoutPopup(){
	
	//取得 Control LayoutPopup 設定
	var popup_type = document.getElementById("ctrlOpenLayoutPopup").value;
	
	if(popup_type != "" && popup_type != null){
		openStrutsLayoutPopup(popup_type);
	}
}

