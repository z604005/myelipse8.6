<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020409M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<!-- JavaScript -->
<script type="text/javascript">


window.onload = function (evt){
	
	if(document.getElementById("ems_system_work_mode").value == "SYSTEM_SALARY_MODE"){
		document.getElementById("flag_salary_01").style.display = "";
		document.getElementById("flag_salary_02").style.display = "";
		document.getElementById("flag_salary_03").style.display = "";
	}
	
	chkEHF020409T0_07();
	chkEHF020409T0_13();
	chkEHF020409T0_19();
	chkEHF020409T0_28_FLG();
	chkEHF020409T0_32_FLG();
}



/**
 * 遲到處理
 */
 
function chkEHF020409T0_07(){
	
	try{
		//遲到扣薪類型
		if(document.getElementById("EHF020409T0_07").value == "01"){
			document.getElementById("flag_EHF020409T0_07_1").style.display = "";
			document.getElementById("flag_EHF020409T0_07_2").style.display = "none";
			document.getElementById("flag_EHF020409T0_07_3").style.display = "none";
		}else if(document.getElementById("EHF020409T0_07").value == "02"){
			document.getElementById("flag_EHF020409T0_07_1").style.display = "none";
			document.getElementById("flag_EHF020409T0_07_2").style.display = "";
			document.getElementById("flag_EHF020409T0_07_3").style.display = "none";
		}else if(document.getElementById("EHF020409T0_07").value == "03"){
			document.getElementById("flag_EHF020409T0_07_1").style.display = "none";
			document.getElementById("flag_EHF020409T0_07_2").style.display = "none";
			document.getElementById("flag_EHF020409T0_07_3").style.display = "";
		}else{
			document.getElementById("flag_EHF020409T0_07_1").style.display = "none";
			document.getElementById("flag_EHF020409T0_07_2").style.display = "none";
			document.getElementById("flag_EHF020409T0_07_3").style.display = "none";
		}
	}catch(err){
		alert(err.message);
	}
}



/**
 * 早退處理
 */
function chkEHF020409T0_13(){
	
	try{
		//遲到扣薪類型
		if(document.getElementById("EHF020409T0_13").value == "01"){
			document.getElementById("flag_EHF020409T0_13_1").style.display = "";
			document.getElementById("flag_EHF020409T0_13_2").style.display = "none";
			document.getElementById("flag_EHF020409T0_13_3").style.display = "none";
		}else if(document.getElementById("EHF020409T0_13").value == "02"){
			document.getElementById("flag_EHF020409T0_13_1").style.display = "none";
			document.getElementById("flag_EHF020409T0_13_2").style.display = "";
			document.getElementById("flag_EHF020409T0_13_3").style.display = "none";
		}else if(document.getElementById("EHF020409T0_13").value == "03"){
			document.getElementById("flag_EHF020409T0_13_1").style.display = "none";
			document.getElementById("flag_EHF020409T0_13_2").style.display = "none";
			document.getElementById("flag_EHF020409T0_13_3").style.display = "";
		}else{
			document.getElementById("flag_EHF020409T0_13_1").style.display = "none";
			document.getElementById("flag_EHF020409T0_13_2").style.display = "none";
			document.getElementById("flag_EHF020409T0_13_3").style.display = "none";
		}
	}catch(err){
		alert(err.message);
	}
}

/**
 * 曠職處理
 */
function chkEHF020409T0_19(){
	
	try{
		//曠職扣薪類型
		if(document.getElementById("EHF020409T0_19").value == "01"){
			document.getElementById("flag_EHF020409T0_20").style.display = "";
			document.getElementById("flag_EHF020409T0_21").style.display = "none";
			document.getElementById("flag_EHF020409T0_22").style.display = "none";
		
		}else if(document.getElementById("EHF020409T0_19").value == "02"){
			document.getElementById("flag_EHF020409T0_20").style.display = "none";
			document.getElementById("flag_EHF020409T0_21").style.display = "";
			document.getElementById("flag_EHF020409T0_22").style.display = "none";
		
		}else if(document.getElementById("EHF020409T0_19").value == "03"){
			//依據倍率
			document.getElementById("flag_EHF020409T0_20").style.display = "none";
			document.getElementById("flag_EHF020409T0_21").style.display = "none";
			document.getElementById("flag_EHF020409T0_22").style.display = "";
		
		}else{
			document.getElementById("flag_EHF020409T0_20").style.display = "none";
			document.getElementById("flag_EHF020409T0_21").style.display = "none";
			document.getElementById("flag_EHF020409T0_22").style.display = "none";
		}
	}catch(err){
		alert(err.message);
	}
}





function chkEHF020409T0_28_FLG(){
	//遲到是否有不計費區段
	if(!document.getElementById("EHF020409T0_28").checked){
		document.getElementById("flag_EHF020409T0_28_FLG").style.display = "none";
		if(document.getElementById("EHF020409T0_28").value=="true"){
			document.getElementById("flag_EHF020409T0_28_FLG").style.display = "";
		}else{
			document.getElementById("flag_EHF020409T0_28_FLG").style.display = "none";
		}
	}else{
		document.getElementById("flag_EHF020409T0_28_FLG").style.display = "";	
	}
}

function chkEHF020409T0_32_FLG(){
	//早退是否有不計費區段
	if(!document.getElementById("EHF020409T0_32").checked){
		document.getElementById("flag_EHF020409T0_32_FLG").style.display = "none";
		if(document.getElementById("EHF020409T0_32").value=="true"){
			document.getElementById("flag_EHF020409T0_32_FLG").style.display = "";
		}else{
			document.getElementById("flag_EHF020409T0_32_FLG").style.display = "none";
		}
	}else{
		document.getElementById("flag_EHF020409T0_32_FLG").style.display = "";
	}
}



</script>


<layout:form action="EHF020409M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="遲到早退曠職設定">
	
	<input type="hidden" id="chk_type" value="${chk_type}" />
	<input type="hidden" id="ems_system_work_mode" value="${ems_system_work_mode}" />
	
	<layout:row>
		<logic:equal name="button" value="init">
<%--			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=查詢&type=t"  reqCode="queryForm" property="EHF020409M0" ></layout:image>--%>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="addDataForm" property="EHF020409M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="edit">
<%--			<layout:image alt="查詢" mode="H,D,H" name="btnimage?text=查詢&type=t"  reqCode="queryForm" property="EHF020409M0" ></layout:image>--%>
			<layout:image alt="儲存" mode="D,H,H" name="btnimage?text=button.save&type=t"  reqCode="saveData" property="EHF020409M0" ></layout:image>
			<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t"  reqCode="editDataForm" property="EHF020409M0" ></layout:image>
			<layout:image alt="回前作業" mode="D,H,H" name="btnimage?text=button.Back&type=t"  reqCode="init" property="EHF020409M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="query">
<%--			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=查詢&type=t"  reqCode="queryForm" property="EHF020409M0" ></layout:image>--%>
			
<%--			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=刪除&type=t"  reqCode="delData" property="EHF020409M0" confirm="您確定要刪除資料嗎?" ></layout:image>--%>
<%--			<layout:image alt="回查詢畫面" mode="D,D,D" name="btnimage?text=回查詢畫面&type=t"  reqCode="init" property="EHF020409M0" ></layout:image>--%>
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
<%--	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">--%>
		
		<layout:text styleClass="DATAS" key="遲到早退曠職資料序號" property="EHF020409T0_01" 	 size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<logic:notEqual name="button" value="edit" >

		</logic:notEqual>
		
		<logic:equal name="button" value="edit">

		</logic:equal>
		
		<layout:text styleClass="DATAS" key="規則名稱" property="EHF020409T0_02" styleId="EHF020409T0_02" size="16" mode="H,H,H" maxlength="20" name="Form1Datas" />

		<layout:radios key="是否啟用" name="Form1Datas" property="EHF020409T0_03" styleClass="DATAS"  mode="H,H,H" cols="2" >
			<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
		</layout:radios>
		
<%--	</layout:grid>--%>
	
	<logic:notEqual name="queryCondition" value="yes">
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;遲到:
			<layout:number styleClass="DATAS" key="分鐘/次" property="EHF020409T0_05" styleId="EHF020409T0_05" layout="false"   size="4" maxlength="4" mode="E,I,I" 	   style="TEXT-ALIGN: RIGHT" />
			分鐘內(包含)不計算遲到
			
<%--			<logic:equal name="button" value="9999">--%>
			<span id="flag_salary_01" style="display:none;" >
			<br>
			&nbsp;&nbsp;遲到以
			<layout:select key="遲到單位" name="Form1Datas" property="EHF020409T0_04" styleId="EHF020409T0_04" styleClass="DATAS" mode="E,I,I" layout="false"  onchange="return chkEHF020409T0_04();">
				<layout:options collection="listUNIT" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;&nbsp;每	
			<layout:number styleClass="DATAS" key="遲到扣薪單位(分鐘/次)" property="EHF020409T0_06" styleId="EHF020409T0_06" layout="false"   size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" />   	   	   	   
			為單位扣薪，每一個	
		    	<layout:select key="遲到扣薪類型" name="Form1Datas" property="EHF020409T0_07" styleId="EHF020409T0_07"  styleClass="DATAS" mode="E,I,I" layout="false" onchange="return chkEHF020409T0_07();" >
					<layout:options collection="listDe" property="item_id" labelProperty="item_value" />
					<layout:cell styleClass="DATAS" >
						<span id="flag_EHF020409T0_07_1" style="display:none;" >
							扣
							<layout:number styleClass="DATAS" key="時薪比例"   property="EHF020409T0_08" layout="false" styleId="EHF020409T0_08"  size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" />
							倍
						</span>
						<span id="flag_EHF020409T0_07_2" style="display:none;" >
					   		扣	
					   		<layout:number styleClass="DATAS" key="固定金額"   property="EHF020409T0_09" layout="false" styleId="EHF020409T0_09"  size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" /> 
					   		元
						</span>
						<span id="flag_EHF020409T0_07_3" style="display:none;" >
					   		區段基礎扣	
					   		<layout:number styleClass="DATAS" key="依照區段"   property="EHF020409T0_35" layout="false" styleId="EHF020409T0_09"  size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" /> 
					   		元
					   		<br/>
					   		&nbsp;&nbsp;額外扣除：(除第
					   		<layout:number styleClass="DATAS" key="遲到額外扣除_區段"   property="EHF020409T0_26" styleId="EHF020409T0_26"  layout="false" size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" /> 
					   		區段無額外扣除，其餘區段乘上
					   		<layout:number styleClass="DATAS" key="遲到額外扣除_倍數"   property="EHF020409T0_27" styleId="EHF020409T0_27"  layout="false" size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" /> 
					   		元)
					   		<br/>
					   		&nbsp;&nbsp;是否有不計費區段:&nbsp;&nbsp;
					   		<layout:checkbox key="是否有不計費區段"  name="Form1Datas" property="EHF020409T0_28" styleId="EHF020409T0_28"styleClass="DATAS"  mode="E,I,I" layout="false" onchange="return chkEHF020409T0_28_FLG();"/>
								<layout:cell styleClass="DATAS" >
									&nbsp;&nbsp;
									<span id="flag_EHF020409T0_28_FLG" style="display:none;" >
									第
									<layout:text key="不計費區段" property="EHF020409T0_29" styleId="EHF020409T0_29" layout="false"	mode="E,I,I" name="Form1Datas" styleClass="DATAS" size="4" maxlength="4"  /> 
								 		區段前(包含)不計費，但會計算遲到次數
									</span>
								</layout:cell>
						</span>
					</layout:cell>
				</layout:select>
			</span>
			
<%--			</logic:equal>--%>
		</layout:cell>
	
	</layout:grid>
	

	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;早退:&nbsp;&nbsp;
			<layout:number styleClass="DATAS" key="分鐘/次" property="EHF020409T0_11" layout="false" styleId="EHF020409T0_11"  size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" />
			分鐘內(包含)不計算早退
			<span id="flag_salary_02" style="display:none;" >
			<br>
			&nbsp;&nbsp;早退以
			<layout:select key="早退單位" name="Form1Datas" property="EHF020409T0_10" styleId="EHF020409T0_10"  styleClass="DATAS" mode="E,I,I" layout="false" >
				<layout:options collection="listUNIT" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;&nbsp;每
			<layout:number styleClass="DATAS" key="早退扣薪單位(分鐘/次)" property="EHF020409T0_12" layout="false" styleId="EHF020409T0_12" size="4" maxlength="4" mode="E,I,I" style="TEXT-ALIGN: RIGHT" />
			為單位扣薪，每一個	
		    <layout:select key="早退扣薪類型" name="Form1Datas" property="EHF020409T0_13" styleId="EHF020409T0_13" styleClass="DATAS" mode="E,I,I" layout="false" onchange="return chkEHF020409T0_13();" >
				<layout:options collection="listDe" property="item_id" labelProperty="item_value" />
				<layout:cell styleClass="DATAS" >
						<span id="flag_EHF020409T0_13_1" style="display:none;" >
							扣
							<layout:number styleClass="DATAS" key="時薪比例"   property="EHF020409T0_14" layout="false" styleId="EHF020409T0_14"  size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" />
							倍
						</span>
						<span id="flag_EHF020409T0_13_2" style="display:none;" >
					   		扣	
					   		<layout:number styleClass="DATAS" key="固定金額"   property="EHF020409T0_15" layout="false" styleId="EHF020409T0_15"  size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" /> 
					   		元
						</span>
						<span id="flag_EHF020409T0_13_3" style="display:none;" >
					   		區段基礎扣	
					   		<layout:number styleClass="DATAS" key="依照區段"   property="EHF020409T0_36" layout="false" styleId="EHF020409T0_15"  size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" /> 
					   		元
					   		<br/>
					   		&nbsp;&nbsp;額外扣除：(除第
					   		<layout:number styleClass="DATAS" key="早退額外扣除_區段"   property="EHF020409T0_30" styleId="EHF020409T0_30"  layout="false" size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" /> 
					   		區段無額外扣除，其餘區段乘上
					   		<layout:number styleClass="DATAS" key="早退額外扣除_倍數"   property="EHF020409T0_31" styleId="EHF020409T0_31"  layout="false" size="4" maxlength="4" mode="E,I,I"  style="TEXT-ALIGN: RIGHT" /> 
					   		元)
					   		<br/>
					   		&nbsp;&nbsp;是否有不計費區段:&nbsp;&nbsp;
					   		<layout:checkbox key="是否有不計費區段"  name="Form1Datas" property="EHF020409T0_32" styleId="EHF020409T0_32"styleClass="DATAS"  mode="E,I,I" layout="false" onchange="return chkEHF020409T0_32_FLG();"/>
								<layout:cell styleClass="DATAS" >
									&nbsp;&nbsp;
									<span id="flag_EHF020409T0_32_FLG" style="display:none;" >
									第
									<layout:text key="不計費區段" property="EHF020409T0_33" styleId="EHF020409T0_33" layout="false"	mode="E,I,I" name="Form1Datas" styleClass="DATAS" size="4" maxlength="4"  /> 
								 		區段前(包含)不計費，但會計算遲到次數
									</span>
								</layout:cell>
						</span>
					</layout:cell>

				
			</layout:select>
			</span>
		</layout:cell>
	
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;遲到/早退:&nbsp;&nbsp;
			<layout:number styleClass="DATAS" key="曠職小時" property="EHF020409T0_17" layout="false" styleId="EHF020409T0_17" size="4" maxlength="4" mode="E,I,I" style="TEXT-ALIGN: RIGHT" />
				&nbsp;&nbsp;小時以上以曠職計算&nbsp;&nbsp;
			
			<span id="flag_salary_03" style="display:none;" >
			<br>
			&nbsp;&nbsp;曠職&nbsp;&nbsp;
			
			<layout:select key="曠職單位" name="Form1Datas" property="EHF020409T0_16" styleId="EHF020409T0_16" 	  styleClass="DATAS" mode="E,I,I" layout="false" >
				<layout:options collection="listAbsenteeism" property="item_id" labelProperty="item_value" />
			</layout:select>	
			
			  	   
			&nbsp;&nbsp;，每
			<layout:number styleClass="DATAS" key="曠職扣薪單位(小時/次)" property="EHF020409T0_18" layout="false" styleId="EHF020409T0_18"   size="4" maxlength="4"  mode="E,I,I"  style="TEXT-ALIGN: RIGHT" />   	   
		  	  為單位扣薪&nbsp;&nbsp;並
		    <layout:select key="曠職扣薪類型" name="Form1Datas" property="EHF020409T0_19" styleId="EHF020409T0_19" styleClass="DATAS" mode="E,I,I" layout="false" onchange="return chkEHF020409T0_19();" >
				<layout:options collection="listAbsenteeism1" property="item_id" labelProperty="item_value" />
				
				<layout:cell styleClass="DATAS" >
				
					<span id="flag_EHF020409T0_20" style="display:none;" >
						<layout:number styleClass="DATAS" key="時薪比例" property="EHF020409T0_20" layout="false" styleId="EHF020409T0_20"  size="4" maxlength="4" mode="E,I,I" style="TEXT-ALIGN: RIGHT" />
						&nbsp; 倍
					</span>
					<span id="flag_EHF020409T0_21" style="display:none;" >
					   	<layout:number styleClass="DATAS" key="固定金額" property="EHF020409T0_21" styleId="EHF020409T0_21" layout="false"  size="4" maxlength="4" mode="E,I,I" style="TEXT-ALIGN: RIGHT" />
					   	&nbsp; 元
					</span>
					
					<span id="flag_EHF020409T0_22" style="display:none;" >
					<%--搭配第三種，使用天數為扣薪依據					--%>
					<layout:number styleClass="DATAS" key="倍率" property="EHF020409T0_37" styleId="EHF020409T0_37" layout="false"  size="4" maxlength="4" mode="E,I,I" style="TEXT-ALIGN: RIGHT" />
					 	&nbsp; 倍	&nbsp; (不足
					 		<layout:number styleClass="DATAS" key="倍率" property="EHF020409T0_34" styleId="EHF020409T0_34" layout="false"  size="4" maxlength="4" mode="E,I,I" style="TEXT-ALIGN: RIGHT" />
					 	&nbsp; 天，為一天扣費)
					</span>
				</layout:cell>
				
			</layout:select>
			</span>
		</layout:cell>
	
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;是否記錄加班上下班刷卡:&nbsp;&nbsp;
			<layout:checkbox key="是否記錄下班與加班上班刷卡"	 name="Form1Datas" property="EHF020409T0_24" styleClass="DATAS" styleId="EHF020409T0_24" 	 mode="E,I,I" layout="false" />
						 	 
		</layout:cell>
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;是否補足遲到時數:&nbsp;&nbsp;
			<layout:checkbox key="是否補足遲到時數"  name="Form1Datas" property="EHF020409T0_25" styleClass="DATAS" styleId="EHF020409T0_25"  mode="E,I,I" layout="false" />
						 	 
		</layout:cell>
	</layout:grid>

	</logic:notEqual>
	
	<logic:equal name="button" value="edit">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" 	styleClass="LOGDATA" property="USER_CREATE" 		styleId="USER_CREATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" 	styleClass="LOGDATA" property="USER_UPDATE" 		styleId="USER_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" 		styleClass="LOGDATA" property="VERSION" 			styleId="VERSION" 		name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" 	styleClass="LOGDATA" property="DATE_UPDATE" 		styleId="DATE_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>
	
	
	<logic:equal name="collection" value="show">
		
	</logic:equal>
</layout:form>