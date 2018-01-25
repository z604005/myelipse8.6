<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.utils.struts.form.BA_REPORTF" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020402M0F" %>
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

function setData(obj){
	alert("this.value==>"+obj.value);
	
	var path_value = getPath(obj);
	var filepath = path_value.split("\\");
	alert("陣列長度為 ==> "+filepath.length);
	
	for(i=0; i < filepath.length; i++){
		alert("陣列元素第"+i+"個是 ==> " + filepath[i]);
	}
	var realpath = "";
	for(j=0; j < filepath.length-1; j++ ){
		realpath += filepath[j]+"\\";
	}
	
//	document.getElementById("EHF020402T0_04").value = obj.value;
	
//	alert("修改後的路徑為 ==> "+realpath);

	document.getElementById("EHF020402T0_04").value = realpath;
	document.getElementById("EHF020402T0_03").value = filepath[filepath.length-1];
}

function getPath(obj){    
  if(obj){    
    if (window.navigator.userAgent.indexOf("MSIE")>=1)    
      {    
        obj.select();    
        return document.selection.createRange().text;    
      }    
   
    else if(window.navigator.userAgent.indexOf("Firefox")>=1)    
      {    
      if(obj.files)    
        {    
    	  return obj.files.item(0).getAsDataURL();    
        }    
      return obj.value;    
      }    
    return obj.value;    
    }    
}

function chk_file_type(){
	
	var cond = document.getElementById("queryCondition").value
	var type = document.getElementById("EHF020402T0_07").value;
	
	if(cond != "yes"){
		if(!(type == "CSV")){
			//document.getElementById("EHF020402T0_04_TEMP").disabled = true;
			document.getElementById("EHF020402T0_03").disabled = true;
			document.getElementById("EHF020402T0_04").disabled = true;
			
		}else{
			//document.getElementById("EHF020402T0_04_TEMP").disabled = false;
			document.getElementById("EHF020402T0_03").disabled = false;
			document.getElementById("EHF020402T0_04").disabled = false;
			
		}
	}
	
}

window.onload = function (evt){
	
	chk_file_type();
}


</script>

<layout:form action="EHF020402M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="卡鐘擷取設定">
	<input type="hidden" id="queryCondition" value="${queryCondition}" />
	<layout:row>
		<logic:equal name="button" value="init">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020402M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,D" name="btnimage?text=button.add&type=t"  reqCode="init_save" property="EHF020402M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="edit">
			<layout:image alt="儲存" 		mode="H,D,H"	name="btnimage?text=button.save&type=t"   				reqCode="saveData" 		property="EHF020402M0" ></layout:image>
			<layout:image alt="手動擷取" 	mode="D,D,D" 	name="btnimage?text=button.manually.capture&type=t"  	reqCode="cardSystem" 	property="EHF020402M0" ></layout:image>
			<layout:image alt="回前作業" 	mode="D,D,D" 	name="btnimage?text=button.Back&type=t"  				reqCode="init" 			property="EHF020402M0" ></layout:image>
		</logic:equal>	
		<logic:equal name="button" value="save">
			<layout:image alt="儲存" mode="D,D,D" name="btnimage?text=button.save&type=t"  reqCode="addDataForm" property="EHF020402M0" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="init" property="EHF020402M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020402M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,D" name="btnimage?text=button.add&type=t"  reqCode="init_save" property="EHF020402M0" ></layout:image>
			<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t"  reqCode="editDataForm" property="EHF020402M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF020402M0"  onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
		<!--  	<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=回前作業&type=t"  reqCode="init" property="EHF020402M0" ></layout:image>-->
		</logic:equal>
<%--		<layout:image alt="執行手動匯檔" mode="D,D,D" name="btnimage?text=執行手動匯檔&type=t"  onclick="openStrutsLayoutPopup('fileupload'); return false;" ></layout:image>--%>
	</layout:row>
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${WMSG}" />
	</layout:row>
	
	<layout:grid cols="3" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="卡鐘擷取序號" property="EHF020402T0_01" styleId="EHF020402T0_01" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<layout:select key="資料來源類型" name="Form1Datas" property="EHF020402T0_07" styleId="EHF020402T0_07" styleClass="DATAS" mode="E,E,I" layout="true"    onchange="return chk_file_type();" >
			<layout:options collection="listEHF020402T0_07" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:text styleClass="DATAS" name="Form1Datas" key="檔案名稱" property="EHF020402T0_03" styleId="EHF020402T0_03" size="40" mode="E,E,I" maxlength="50" />
		
		
		<%--		--%>
		<logic:notEqual name="queryCondition" value="yes">
			<layout:text styleClass="DATAS" name="Form1Datas" key="擷取檔案路徑" property="EHF020402T0_04" styleId="EHF020402T0_04" size="40" mode="E,E,I" />				
		
			<layout:text styleClass="DATAS" key="資料擷取時間" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
			
				<layout:select key="資料擷取時間(時)" name="Form1Datas" property="EHF020402T0_02_HH" styleId="EHF020402T0_02_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
					<layout:options collection="listEHF020402T0_02_HH" property="item_id" labelProperty="item_value" />
				</layout:select>
				&nbsp;:&nbsp;
				<layout:select key="資料擷取時間(分)" name="Form1Datas" property="EHF020402T0_02_MM" styleId="EHF020402T0_02_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
					<layout:options collection="listEHF020402T0_02_MM" property="item_id" labelProperty="item_value" />
				</layout:select>
			</layout:text>
			
			<layout:radios key="是否啟用" name="Form1Datas" property="EHF020402T0_07_FLG" styleClass="DATAS" mode="E,E,I" cols="2" >
				<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
			</layout:radios>
			
			<layout:cell styleClass="DATAS" ></layout:cell>

			<layout:text styleClass="DATAS" key="卡號" property="EHF020402T0_02" styleId="EHF020402T0_08" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
				第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_01" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_02" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			<layout:text styleClass="DATAS" key="卡機來源" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
				第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_03" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_04" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
		
		<layout:text styleClass="DATAS" key="刷卡類別" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
				第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_05" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_06" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
		
			
			<layout:text styleClass="DATAS" key="考勤日期_年" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
				
					第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_07" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_08" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			
			<layout:text styleClass="DATAS" key="考勤日期_月" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
					第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_09" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_10" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			
			<layout:text styleClass="DATAS" key="考勤日期_日" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
					第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_11" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_12" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			
			
			
			
			<layout:text styleClass="DATAS" key="刷卡日期_年" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
				
					第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_13" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_14" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			
			<layout:text styleClass="DATAS" key="刷卡日期_月" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
					第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_15" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_16" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			
			<layout:text styleClass="DATAS" key="刷卡日期_日" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
				第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_17" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_18" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			<layout:text styleClass="DATAS" key="刷卡時間_時" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
				
					第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_19" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_20" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			
			<layout:text styleClass="DATAS" key="刷卡時間_分" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
					第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_21" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_22" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			
			<layout:text styleClass="DATAS" key="刷卡時間_秒" property="EHF020402T0_02" styleId="EHF020402T0_02" size="8" mode="I,I,I" maxlength="16" name="Form1Datas" >
				第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_23" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false" />
				個字到&nbsp;~&nbsp;到第
				<layout:text styleClass="DATAS" name="Form1Datas" key="卡號" property="EHF020402T0_08_24" styleId="EHF020402T0_03" size="10" mode="E,E,I" maxlength="10" layout="false"  />
				個字
			</layout:text>
			
			<%--	管理者用的按鈕			--%>
			<logic:equal name="system" value="yes">
			<layout:select key="檔案日期格式" name="Form1Datas" property="EHF020402T0_08_25" styleId="EHF020402T0_08_25" styleClass="DATAS" mode="E,E,I" layout="true" >
				<layout:options collection="listEHF020402T0_08_25" property="item_id" labelProperty="item_value" />
			</layout:select>
			</logic:equal>
			<%--	非管理者用的按鈕			--%>
			<logic:notEqual name="system" value="yes">
				<layout:select key="檔案日期格式" name="Form1Datas" property="EHF020402T0_08_25" styleId="EHF020402T0_08_25" styleClass="DATAS" mode="I,I,I" layout="true" >
					<layout:options collection="listEHF020402T0_08_25" property="item_id" labelProperty="item_value" />
				</layout:select>
			</logic:notEqual>
			
			<layout:text styleClass="DATAS" key="擷取參數" property="EHF020402T0_02" styleId="EHF020402T0_02" mode="I,I,I" name="Form1Datas" >
				當下日期的加或減
				<layout:text styleClass="DATAS" name="Form1Datas" key="擷取參數" property="EHF020402T0_08_26" styleId="EHF020402T0_08_26" size="10" mode="I,I,I" maxlength="10" layout="false" />
				天，
				擷取日期：
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" startYear="2010" endYear="2025" key="日期" size="10"
					 name="Form1Datas" property="EHF020402T0_08_28" styleClass="DATAS" layout="false" />
			</layout:text>
			
			<%--	管理者用的按鈕			--%>
			<logic:equal name="system" value="yes">
			<layout:text styleClass="DATAS" name="Form1Datas" key="副檔名" property="EHF020402T0_08_27" styleId="EHF020402T0_08_27" size="10" mode="E,E,I" maxlength="10" />
			</logic:equal>
			<%--	非管理者用的按鈕			--%>
			<logic:notEqual name="system" value="yes">
				<layout:text styleClass="DATAS" name="Form1Datas" key="副檔名" property="EHF020402T0_08_27" styleId="EHF020402T0_08_27" size="10" mode="I,I,I" maxlength="10" />
			</logic:notEqual>			
			
		</logic:notEqual>
					
		
		
	</layout:grid>
	
	<logic:notEqual name="queryCondition" value="yes">

		<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:textarea styleClass="DATAS" property="EHF020402T0_05" styleId="EHF020402T0_05" key="備註" rows="2" cols="80" maxlength="50" name="Form1Datas"  mode="E,E,I" />
		</layout:grid>
	
	</logic:notEqual>
	
	
	<logic:equal name="button" value="edit">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" styleId="USER_CREATE"	property="USER_CREATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" styleId="USER_UPDATE"	property="USER_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" 	styleClass="LOGDATA" styleId="VERSION"		property="VERSION" 		name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" styleId="DATE_UPDATE"	property="DATE_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>
	
	
	<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
		String strTmp = "";
		
	%>
	
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="300" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				EHF020402M0F FORM=(EHF020402M0F)list.get(item_index);
				strTmp = FORM.getEHF020402T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" id="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF020402T0_07" style="TEXT-ALIGN: CENTER" title="資料來源類型" />
		<layout:collectionItem property="EHF020402T0_07_FLG" style="TEXT-ALIGN: CENTER" title="是否啟用" />
		<layout:collectionItem property="EHF020402T0_02" style="TEXT-ALIGN: CENTER" title="資料擷取時間" />
		<layout:collectionItem property="EHF020402T0_03" style="TEXT-ALIGN: CENTER" title="檔案名稱" />
		<layout:collectionItem property="EHF020402T0_04" style="TEXT-ALIGN: CENTER" title="擷取檔案路徑" />
		<layout:collectionItem property="EHF020402T0_05" style="TEXT-ALIGN: CENTER" title="備註" />
	</layout:collection>
	</logic:equal>

</layout:form>


<%----%>
<%--<jsp:include page="/templates/end.jsp"></jsp:include>--%>

