<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<jsp:include page="/templates/begin.jsp"></jsp:include>


<!-- JavaScript -->
<script type="text/javascript">

function chkEHF030600T0_04(){
	//天數計算類別
	if(document.getElementById("EHF030600T0_04").value == "01" ){
		document.getElementById("EHF030600T0_05").disabled = false;
		document.getElementById("EHF030600T0_06").disabled = false;
	}else if(document.getElementById("EHF030600T0_04").value == "02" ){
		document.getElementById("EHF030600T0_05").disabled = true;
		document.getElementById("EHF030600T0_06").disabled = true;
	}else if(document.getElementById("EHF030600T0_04").value == "03"){
		document.getElementById("EHF030600T0_05").disabled = true;
		document.getElementById("EHF030600T0_06").disabled = true;
	}else{
		document.getElementById("EHF030600T0_05").disabled = true;
		document.getElementById("EHF030600T0_06").disabled = true;
	}
}

function chkEHF030600T0_05(){
	//天數計算類別
	if(document.getElementById("EHF030600T0_05").value == "01" || document.getElementById("EHF030600T0_05").value == "02" ){
		document.getElementById("flag_EHF030600T0_06").style.display = "";
	}else{
		document.getElementById("flag_EHF030600T0_06").style.display = "none";
	}
}

function chkEHF030600T0_07(){
	//發放方式
	if(document.getElementById("EHF030600T0_07").value == "01" ){
		document.getElementById("flag_EHF030600T0_08").style.display = "";
		document.getElementById("flag_EHF030600T0_09").style.display = "none";
	}else if(document.getElementById("EHF030600T0_07").value == "02"){
		document.getElementById("flag_EHF030600T0_08").style.display = "none";
		document.getElementById("flag_EHF030600T0_09").style.display = "";
	}else{
		document.getElementById("flag_EHF030600T0_08").style.display = "none";
		document.getElementById("flag_EHF030600T0_09").style.display = "none";
	}
}

function chkSTDate(){
	
	var login_date = document.getElementById("LOGIN_DATE").value;
	var do_transfer_date = document.getElementById("DO_TRANSFER_DATE").value;
	
	//檢核登錄日期 轉帳日期 不可為空白或null
	if(login_date == "" || login_date == null 
	   || do_transfer_date == "" || do_transfer_date == null){
		alert("登錄日期、轉帳日期不可為空白!!")
		return false;	
	}else{
		return true;
	}
	
	return false;
}

window.onload = function (evt){
		chkEHF030600T0_04();
	if(document.getElementById("chk_type").value == "yes"){
		chkEHF030600T0_05();
		chkEHF030600T0_07();
	}
	
	if(document.getElementById("open_type").value == "1"){
		//open 津貼
		window.open('EHF030601M0.do','','height=800,width=1080,resizable=yes,status=no,toolbar=no,menubar=no,location=no');
	}else if(document.getElementById("open_type").value == "2"){
		//open 加班費
		window.open('EHF030602M0.do','','height=800,width=1080,resizable=yes,status=no,toolbar=no,menubar=no,location=no');
	}else if(document.getElementById("open_type").value == "3"){
		//open 不休假加班費
		window.open('EHF030603M0.do','','height=800,width=1080,resizable=yes,status=no,toolbar=no,menubar=no,location=no');
	}
	
	if(document.getElementById("stf_open_type").value == "on"){
		openStrutsLayoutPopup('SalaryTransfer');
	}
		
}
function setTime(){
	//document.getElementById("EHF030600T0_02").value = document.getElementById("EHF030600T0_M").value;
	$("input[name='EHF030600T0_02']").val($("input[name='EHF030600T0_M']").val());
	return true;
}
</script>

<layout:form action="EHF030600M1.do" reqCode=""  width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="薪資計算發放">
<input type="hidden" id="dataChanged" name="dataChanged" value="" />	
<input type="hidden" id="chk_type" value="${chk_type}" />
<input type="hidden" id="open_type" value="${open_type}" />
<input type="hidden" id="stf_open_type" value="${stf_open_type}" />
<logic:equal name="ems_system_work_mode" value="SYSTEM_DEVELOP_MODE">
	<layout:row>	
		<layout:text styleClass="DATAS" key="薪資計算測試ID" property="TESTID" size="10" mode="E,E,H" maxlength="16" name="Form1Datas" />
	</layout:row>
</logic:equal>
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="儲存" mode="D,H,H" name="btnimage?text=button.save&type=t"   reqCode="addDataForm" property="EHF030600M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
		</logic:equal>
		<logic:equal name="button" value="query">
			<logic:lessEqual name="Form1Datas" property="EHF030600T0_SCP" value="02" >
				<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveData" property="EHF030600M1" ></layout:image>
				
				<layout:image alt="計算津貼" mode="H,H,H" name="btnimage?text=計算津貼&type=t"  reqCode="countAllowance" property="EHF030600M1" 
					  	      onclick="confirm('該入扣帳年月,薪資年月的津貼資料會刪除後重新計算!! 您確定要計算津貼嗎? ')" ></layout:image>
				<layout:image alt="計算加班費" mode="H,H,H" name="btnimage?text=計算加班費&type=t"  reqCode="countOvertime" property="EHF030600M1" 
					 	 	  onclick="confirm('該入扣帳年月,薪資年月的加班費資料會刪除後重新計算!! 您確定要計算加班費嗎? ')" ></layout:image>
				<layout:image alt="計算不休假加班費" mode="H,H,H" name="btnimage?text=計算不休假加班費&type=t"  reqCode="countHaOvertime" property="EHF030600M1" 
					 		  onclick="confirm('該入扣帳年月,薪資年月的不休假加班費資料會刪除後重新計算!! 您確定要計算不休假加班費嗎? ')" ></layout:image>
				
				
				
				<layout:image alt="計算薪資" mode="H,D,H" name="btnimage?text=EHF030600_count&type=t"  reqCode="countSalary" property="EHF030600M1" 
					 		  confirm="您確定要計算薪資嗎?" ></layout:image>
			</logic:lessEqual>
			
			<logic:equal name="Form1Datas" property="EHF030600T0_SCP" value="02" >
				<layout:image alt="回復計算薪資" mode="H,D,H" name="btnimage?text=EHF030600_return_confirm&type=t"  reqCode="recoverySalary" property="EHF030600M1" 
					 		   ></layout:image>
				<layout:image alt="確認薪資" mode="H,D,H" name="btnimage?text=EHF030600_confirm&type=t"  reqCode="confirmSalary" property="EHF030600M1" 
					 		   ></layout:image>
			</logic:equal>
			
			<logic:equal name="Form1Datas" property="EHF030600T0_SCP" value="03" >
				<layout:image alt="回復確認薪資" mode="H,H,D" name="btnimage?text=EHF030600_return_confirm&type=t"  reqCode="recoverySalary" property="EHF030600M1" 
					 		   ></layout:image>
				<layout:image alt="確認出帳" mode="H,H,D" name="btnimage?text=EHF030600_charge_off&type=t"  reqCode="confirmTransfer" property="EHF030600M1" 
					 		   ></layout:image>
			</logic:equal>
				
			<logic:equal name="Form1Datas" property="EHF030600T0_SCP" value="04" >
				<logic:equal name="control_recovery_transfer" value="ENABLE">
					<layout:image alt="回復確認出帳" mode="H,H,D" name="btnimage?text=EHF030600_return_charge_off&type=t"  reqCode="recoverySalary" property="EHF030600M1" ></layout:image>
				</logic:equal>
			</logic:equal>
			
			<logic:greaterEqual name="Form1Datas" property="EHF030600T0_SCP" value="02" >
				<layout:image alt="列印薪津清冊" mode="H,D,D" name="btnimage?text=EHF030600_print&type=t"  reqCode="printSalary" property="EHF030600M1" 
					 		  confirm="您確定要列印資料嗎?" ></layout:image>	
			</logic:greaterEqual>
			
			<logic:greaterEqual name="Form1Datas" property="EHF030600T0_SCP" value="04" >
<%--				<layout:image alt="列印銀行轉帳清單" mode="H,H,D" name="btnimage?text=列印銀行轉帳清單&type=t"  reqCode="printTransferList"--%>
<%--							  confirm="您確定要列印資料嗎?" property="EHF030600M1" ></layout:image>	--%>


<%--				<layout:image alt="產生薪資媒體轉帳檔案" mode="H,H,D" name="btnimage?text=產生薪資媒體轉帳檔案&type=t"  reqCode="generateTransferFile"--%>
<%--							  alert="檔案產生中!! 請勿重複執行!!" property="EHF030600M1" ></layout:image>	--%>
				
				
				
<%--				<layout:image alt="產生薪資媒體轉帳檔案" mode="H,H,D" name="btnimage?text=產生薪轉媒體檔&type=t"--%>
<%--							  property="EHF030600M1" onclick="openStrutsLayoutPopup('SalaryTransfer'); return false; "  ></layout:image>--%>
			</logic:greaterEqual>
			
<%--			<logic:notEqual name="DisplayFileName" value="" >--%>
<%--				<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" >--%>
<%--				</layout:image>--%>
<%--			</logic:notEqual>--%>
			
			<logic:lessThan name="Form1Datas" property="EHF030600T0_SCP" value="02" >	  
				<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"   reqCode="delData" property="EHF030600M1"  onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
			</logic:lessThan>	
		
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF030600M1" ></layout:image>
	</layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
   		<logic:equal name="ng_emp_list" value="open">
   			<layout:link styleClass="SMALL_MESSAGE" title="未計薪員工清單" href="" onclick="openStrutsLayoutPopup('ng_emp_list'); return false;" >
				<layout:message styleClass="SMALL_MESSAGE" key="未計薪員工清單" />
			</layout:link>
		</logic:equal>
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:text styleClass="DATAS" key="薪資計算序號" property="EHF030600T0_01" styleId="EHF030600T0_01" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<layout:text styleClass="DATAS" property="EHF030600T0_U" key="薪資計算UID" styleId="EHF030600T0_U" size="18" maxlength="20" name="Form1Datas"  mode="I,I,I" />
		
<%--		<layout:select key="薪資計算處理狀態" name="Form1Datas" property="EHF030600T0_SCP" styleClass="DATAS" mode="I,I,I" >--%>
<%--				<layout:options collection="listEHF030600T0_SCP" property="item_id" labelProperty="item_value" />--%>
<%--		</layout:select>--%>

		<layout:cell styleClass="DATAS" ></layout:cell>
		
<%--		<layout:select key="入扣帳年月" name="Form1Datas" property="EHF030600T0_05_YY" styleClass="DATAS" mode="E,E,I" >--%>
<%--				<layout:options collection="YY_list" property="item_id" labelProperty="item_value" />--%>
<%--				&nsps;年&nsps;--%>
<%--				<layout:select key="月份" name="Form1Datas" property="EHF030600T0_05_MM" styleClass="DATAS" mode="E,E,I" layout="false" >--%>
<%--					<layout:options collection="MM_list" property="item_id" labelProperty="item_value" />--%>
<%--				</layout:select>--%>
<%--				&nsps;月&nsps;--%>
<%--		</layout:select>--%>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,R"  tooltip="◎入扣帳年月" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF030600T0_M" 
				     key="入扣帳年月" name="Form1Datas" styleClass="DATAS"  isRequired="true" onkeydown="nextFiled()" onchange="setTime()"/>
				     
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,R"  tooltip="◎薪資年月" startYear="2010" endYear="2025" size="8" maxlength="9" property="EHF030600T0_02" 
				     key="薪資年月" name="Form1Datas" styleClass="DATAS"  isRequired="true" onkeydown="nextFiled()" />
		
		<layout:text styleClass="DATAS" tooltip="公司代號" key="公司代號" property="EHF030600T0_03" styleId="EHF030600T0_03" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
<%--			<sp:emspopup function="dep" id="EHF030600T0_03,EHF030600T0_03_TXT" ></sp:emspopup>--%>
			<layout:text layout="false" property="EHF030600T0_03_TXT" styleId="EHF030600T0_03_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		
<%--		<layout:cell styleClass="DATAS" ></layout:cell>--%>
		
		<layout:select key="發放類別" name="Form1Datas" property="EHF030600T0_04" styleId="EHF030600T0_04" styleClass="DATAS" mode="I,I,I"
					   onchange="return chkEHF030600T0_04();" >
				<layout:options collection="listEHF030600T0_04" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<span id="flag_EHF030600T0_05" style="display:none;" >
		<layout:select key="天數計算類別" name="Form1Datas" property="EHF030600T0_05" styleId="EHF030600T0_05" styleClass="DATAS" mode="E,E,I" 
					   layout="false" onchange="return chkEHF030600T0_05();" >
				<layout:options collection="listEHF030600T0_05" property="item_id" labelProperty="item_value" />
				
				<layout:cell styleClass="DATAS" >
				
				<span id="flag_EHF030600T0_06" style="display:none;" >
					&nbsp;&nbsp;&nbsp;&nbsp;天數:&nbsp;&nbsp;
					<layout:number styleClass="DATAS" key="天數" property="EHF030600T0_06" styleId="EHF030600T0_06" layout="false" size="6" maxlength="6" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;天
				</span>
				
				</layout:cell>
				
		</layout:select>
		</span>
		
	</layout:grid>
	
<%--	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="1070px" styleClass="DATAGRID">--%>
		<layout:select key="發放方式" name="Form1Datas" property="EHF030600T0_07" styleId="EHF030600T0_07" styleClass="DATAS" mode="H,H,H"
					   onchange="return chkEHF030600T0_07();" >
				<layout:options collection="listEHF030600T0_07" property="item_id" labelProperty="item_value" />
				
				<layout:cell styleClass="DATAS" >
				<span id="flag_EHF030600T0_08" style="display:none;" >
					<logic:equal name="salary_rate_change_mode" value="ENABLE" >
						&nbsp;&nbsp;依基本薪資比例:&nbsp;&nbsp;
						<layout:number styleClass="DATAS" key="依基本薪資比例" property="EHF030600T0_08" styleId="EHF030600T0_08" layout="false" size="6" maxlength="6" 
					   	   			   style="TEXT-ALIGN: RIGHT" />&nbsp;%&nbsp;做計算
					</logic:equal>
					<logic:notEqual name="salary_rate_change_mode" value="ENABLE" >
						<layout:number styleClass="DATAS" key="依基本薪資比例" property="EHF030600T0_08" styleId="EHF030600T0_08" layout="false" size="6" maxlength="6" 
					   	   		   	   style="TEXT-ALIGN: RIGHT" mode="H,H,H" value="100" />
					</logic:notEqual>
				</span>
				<span id="flag_EHF030600T0_09" style="display:none;" >
					&nbsp;&nbsp;依固定金額:&nbsp;&nbsp;
					<layout:number styleClass="DATAS" key="依固定金額" property="EHF030600T0_09" styleId="EHF030600T0_09" layout="false" size="6" maxlength="6" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;元&nbsp;做計算
				</span>
				
				</layout:cell>
				
		</layout:select>
<%--	</layout:grid>--%>
	
	<layout:grid cols="2"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
	<logic:equal name="Form1Datas" property="EHF030600T0_SCP" value="01" >
			<layout:select key="薪資處理狀態" name="Form1Datas" property="EHF030600T0_SCP" styleId="EHF030600T0_SCP" styleClass="DATAS" mode="I,I,I" >
				<layout:options collection="listEHF030600T0_SCP" property="item_id" labelProperty="item_value" />
			</layout:select>
			<layout:cell styleClass="DATAS" ></layout:cell>
		</logic:equal>
		
		<logic:greaterThan name="Form1Datas" property="EHF030600T0_SCP" value="01" >
			<layout:text styleClass="DATAS" property="EHF030600T0_C" 	styleId="EHF030600T0_C" key="處理總筆數" size="12" maxlength="14" name="Form1Datas"  mode="I,I,I" />
			<layout:text styleClass="DATAS" property="EHF030600T0_AM" 	styleId="EHF030600T0_AM" key="處理總金額" size="12" maxlength="14" name="Form1Datas"  mode="I,I,I" />
			<layout:text styleClass="DATAS" property="EHF030600T0_12" 	styleId="EHF030600T0_12" key="發放日期" size="12" maxlength="14" name="Form1Datas"  mode="I,I,I" />
			<layout:select key="薪資處理狀態" name="Form1Datas" property="EHF030600T0_SCP" styleId="EHF030600T0_SCP" styleClass="DATAS" mode="I,I,I" >
				<layout:options collection="listEHF030600T0_SCP" property="item_id" labelProperty="item_value" />
			</layout:select>
		</logic:greaterThan>
		
		
	</layout:grid>
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text styleClass="DATAS" property="EHF030600T0_13" key="備註" size="100" maxlength="50" name="Form1Datas"  mode="E,E,I" />
	</layout:grid>
	
	
	
	<logic:equal name="collection" value="show">
	
	<layout:collection emptyKey="沒有資料列"  name="Form1Datas" property="EHF030600C1" id="bean1" indexId="index" selectId="" selectProperty=""
						   selectName=""  width="100%"  styleClass="COLLECTION" styleClass2="COLLECTION_2">
			
<%--			<layout:collectionItem property="EHF030600T1_U" style="TEXT-ALIGN: CENTER" title="薪資計算明細UID" />--%>
			<layout:collectionItem property="EHF030600T1_03" 	style="TEXT-ALIGN: CENTER" title="部門/員工" />
			<layout:collectionItem property="EHF030600T1_M1" 	style="TEXT-ALIGN: CENTER" title="薪資年月" />
			<layout:collectionItem property="EHF030600T1_22" 	style="TEXT-ALIGN: CENTER" title="出勤天數/時數" />
			<layout:collectionItem property="EHF030600T1_071" 	style="TEXT-ALIGN: RIGHT" title="應發金額" />
			<layout:collectionItem property="EHF030600T1_072" 	style="TEXT-ALIGN: RIGHT" title="應扣金額" />
			<layout:collectionItem property="EHF030600T1_07" 	style="TEXT-ALIGN: RIGHT" title="實發金額" />
			<layout:collectionItem property="EHF030600T1_04" 	style="TEXT-ALIGN: RIGHT" title="本薪金額" />
			<layout:collectionItem property="EHF030600T1_04_O" 	style="TEXT-ALIGN: RIGHT" title="薪資項目" />
			<layout:collectionItem property="EHF030600T1_20" 	style="TEXT-ALIGN: RIGHT" title="全勤獎金" />
			<layout:collectionItem property="EHF030600T1_041" 	style="TEXT-ALIGN: RIGHT" title="請假扣薪" />
			<layout:collectionItem property="EHF030600T1_09" 	style="TEXT-ALIGN: RIGHT" title="津貼" />
			<layout:collectionItem property="EHF030600T1_10" 	style="TEXT-ALIGN: RIGHT" title="加班費" />
<%--			<layout:collectionItem property="EHF030600T1_11" style="TEXT-ALIGN: RIGHT" title="差旅費" />--%>
			<layout:collectionItem property="EHF030600T1_12" 	style="TEXT-ALIGN: RIGHT" title="不休假加班費" />
			<layout:collectionItem property="EHF030600T1_13" 	style="TEXT-ALIGN: RIGHT" title="健保費" />
			<layout:collectionItem property="EHF030600T1_15" 	style="TEXT-ALIGN: RIGHT" title="勞保費" />
			<layout:collectionItem property="EHF030600T1_17" 	style="TEXT-ALIGN: RIGHT" title="勞退自提金額" />
			
			<layout:collectionItem property="EHF030600T1_24" 	style="TEXT-ALIGN: CENTER" title="遲到(分/次)" />
			<layout:collectionItem property="EHF030600T1_25" 	style="TEXT-ALIGN: CENTER" title="遲到(元)" />
			<layout:collectionItem property="EHF030600T1_26" 	style="TEXT-ALIGN: CENTER" title="早退(分/次)" />
			<layout:collectionItem property="EHF030600T1_27" 	style="TEXT-ALIGN: CENTER" title="早退(元)" />
			<layout:collectionItem property="EHF030600T1_28" 	style="TEXT-ALIGN: CENTER" title="曠職(分/次)" />
			<layout:collectionItem property="EHF030600T1_29" 	style="TEXT-ALIGN: CENTER" title="曠職(元)" />
			
			<layout:collectionItem property="EHF030600T1_06" 	style="TEXT-ALIGN: RIGHT" title="薪資所得代扣" />
			<layout:collectionItem property="EHF030600T1_19" 	style="TEXT-ALIGN: RIGHT" title="福利金" />

			<layout:collectionItem property="EHF030600T1_05" 	style="TEXT-ALIGN: RIGHT" title="其他補款" />
			<layout:collectionItem property="EHF030600T1_05_D" 	style="TEXT-ALIGN: RIGHT" title="其他扣款" />
			<layout:collectionItem property="EHF030600T1_SCP" 	style="TEXT-ALIGN: RIGHT" title="薪資處理狀態" />
		</layout:collection>
	
	</logic:equal>
	
	<logic:equal name="button" value="query">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" 	styleId="USER_CREATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" 	styleId="USER_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" 	styleClass="LOGDATA" property="VERSION" 		styleId="VERSION" 		name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" 	styleId="DATE_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>
	
	<layout:popup styleId="SalaryTransfer" styleClass="DATAS" key="薪資媒體轉檔">
<%--		<layout:date calendarType="JSCAL2" patternKey="%Y/%m/%d" tooltip="◎入扣帳年月" startYear="20100101" endYear="20201231" --%>
<%--				     size="8" maxlength="9" property="LOGIN_DATE" --%>
<%--				     key="入扣帳年月" name="Form1Datas" styleClass="DATAS" mode="I,I,E" isRequired="true" onkeydown="nextFiled()" />--%>

		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,E"  tooltip="登入日期" startYear="2010" endYear="2025" size="10" maxlength="20" property="LOGIN_DATE" 
				     key="登入日期" name="Form1Datas" styleClass="DATAS"  isRequired="true" />
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,E"  tooltip="轉帳日期" startYear="2010" endYear="2025" size="10" maxlength="20" property="DO_TRANSFER_DATE" 
				     key="轉帳日期" name="Form1Datas" styleClass="DATAS"  isRequired="true" />
		<%--<layout:text styleClass="DATAS" property="LOGIN_DATE" key="登錄日期" size="10" maxlength="20" name="Form1Datas"  mode="I,I,E" >
			日期格式：yyyy/MM/dd
		</layout:text>
		<layout:text styleClass="DATAS" property="DO_TRANSFER_DATE" key="轉帳日期" size="10" maxlength="20" name="Form1Datas"  mode="I,I,E" >
			日期格式：yyyy/MM/dd
		</layout:text>--%>
				<layout:row >				
					<layout:submit value="產生薪資媒體轉帳檔" reqCode="generateTransferFile" property="EHF030600M1" 
					onclick="var agree=chkSTDate() ;if (!agree) return false;"/>
					<layout:button value="取消" onclick="closeStrutsLayoutPopup('SalaryTransfer');" />
				</layout:row>
	</layout:popup>
	
	<%--  未計薪員工清單	--%>
	<logic:equal name="ng_emp_list" value="open">
	<layout:popup styleId="ng_emp_list" styleClass="DATAS" key="未計薪員工清單" >
	
		<layout:collection emptyKey="沒有資料列"  name="Form2Datas" 
						   selectId="" selectProperty="" selectName=""  
					       width="100%" height="200" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
			<layout:collectionItem property="EHF030600T1_03" style="TEXT-ALIGN: CENTER" title="部門/員工" />
		</layout:collection>
		<layout:row >
			<layout:button value="關閉" onclick="closeStrutsLayoutPopup('ng_emp_list');" />
		</layout:row>
				
	</layout:popup>
	</logic:equal>
	
</layout:form>

<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />


