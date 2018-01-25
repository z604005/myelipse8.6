<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020100M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>
$(document).ready(function(){
	var vacId = $("input[name = EHF020100T0_03]");
	var vacation = $("input[name = EHF020100T0_04]");
	var beHours = $("input[name = EHF020100T0_05]");
	var endHours  =$("input[name = EHF020100T0_06]");
	var reqCode = $("input[name = reqCode]");
	var insertBtn = $("input[name = insertBtn]");
	//var EHF020100T0_06_FLG = $("input[name = EHF020100T0_06_FLG]");
	
	beHours.bind("change",function(){
		var number = /^[0-9]{0,4}$/;
		if(!number.test(beHours.val())){
			alert("請輸入數字!!");
			beHours.focus();
		}
	});
	
	endHours.bind("change",function(){
		if(isNaN(endHours.val())){
			alert("請輸入數字!!");
			endHours.focus();
		}
	});
	
	insertBtn.bind("click",function(){
		var flag = true;
		if(vacId.val().length == 0){
			alert("假別代號欄位必填!!!");
			vacId.focus();
			flag = false;
			return false; 
		}
		if(vacation.val().length == 0){
			alert("假別欄位必填!!!");
			vacation.focus();
			flag = false;
			return false;
		}
		if(flag){
			reqCode.val("InsertForm");
		}
	});
});

function chkEHF020100T0_06_FLG(){
	//是否檢核單次最低請假時數
	if(!document.getElementById("EHF020100T0_06_FLG").checked){
		document.getElementById("flag_EHF020100T0_06_FLG").style.display = "none";
	}else{
		document.getElementById("flag_EHF020100T0_06_FLG").style.display = "";
	}
}

function chkEHF020100T0_05_FLG(){
	//是否檢核年度請假總時數
	if(!document.getElementById("EHF020100T0_05_FLG").checked){
		document.getElementById("flag_EHF020100T0_05_FLG").style.display = "none";
	}else{
		document.getElementById("flag_EHF020100T0_05_FLG").style.display = "";
	}
}

function chkEHF020100T0_03_TYPE(){
	//假別薪資設定
	if(document.getElementById("EHF020100T0_03_TYPE").value == "04"){
		document.getElementById("flag_EHF020100T0_03_VAL").style.display = "";
	}else{
		document.getElementById("flag_EHF020100T0_03_VAL").style.display = "none";
	}
}

window.onload = function (evt){
	
	if(document.getElementById("chk_type").value == "yes"){
		chkEHF020100T0_05_FLG();
		chkEHF020100T0_06_FLG();
		chkEHF020100T0_03_TYPE();
	}
		
}

</script>

<layout:form action="EHF020100M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="公司假別設定">
	<input type="hidden" id="chk_type" value="${chk_type}" />
	
	<layout:row>
		<logic:equal name="button" value="init"> 
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020100M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="init_add" property="EHF020100M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="add">
			<layout:image alt="儲存" mode="D,D,H" name="btnimage?text=button.save&type=t"  reqCode="InsertForm" property="EHF020100M0" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="goback" property="EHF020100M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="edit">
			<layout:image alt="儲存" mode="D,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveForm" property="EHF020100M0" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="goback" property="EHF020100M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020100M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="init_add" property="EHF020100M0" ></layout:image>
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t"  reqCode="UpdateForm" property="EHF020100M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delForm" property="EHF020100M0" onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
			<!--<layout:image alt="回查詢畫面" mode="D,D,D" name="btnimage?text=回查詢畫面&type=t"  reqCode="goback" property="EHF020100M0" ></layout:image>-->
				
			<layout:row>
				<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF020100M0" reqCode="print" confirm="您確定要列印資料嗎?" ></layout:image>
				<logic:notEqual name="DisplayFileName" value="" >
					<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=button.download&type=t" onclick="opendownloadfile(); return false;" ></layout:image>
				</logic:notEqual>
			</layout:row>
			
			
		</logic:equal>
<%--		<layout:image alt="查詢" mode="D,H,H" name="btnimage?text=查詢&type=t" reqCode="queryForm" property="EHF020100M0" policy="all"></layout:image>--%>
<%--		<layout:image alt="新增" mode="D,H,H" name="btnimage?text=新增&type=t" property="insertBtn" policy="all"></layout:image>--%>
<%--		<layout:image alt="修改" mode="H,D,H" name="btnimage?text=修改&type=t" reqCode="UpdateForm" property="EHF020100M0" policy="all"></layout:image>--%>
<%--		<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=刪除&type=t" reqCode="delForm" property="EHF020100M0" policy="all" ></layout:image>					--%>
<%--		<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=儲存&type=t" reqCode="saveForm" property="EHF020100M0" policy="all"></layout:image>--%>
<%--		<layout:image alt="回查詢畫面" mode="H,D,D" name="btnimage?text=回查詢畫面&type=t" reqCode="goback" property="EHF020100M0" policy="all"></layout:image>--%>
	</layout:row>
	
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="假別序號" 	property="EHF020100T0_01" styleId="EHF020100T0_01" mode="H,H,H" name="Form1Datas" styleClass="DATAS" size="4" maxlength="10"></layout:text>
		<layout:text key="假別代號" 	property="EHF020100T0_03" styleId="EHF020100T0_03" mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="5" maxlength="4"></layout:text>
		<layout:text key="假別" 		property="EHF020100T0_04" styleId="EHF020100T0_04" mode="E,E,I" name="Form1Datas" styleClass="DATAS"  	size="30" maxlength="20"></layout:text>
		
		<layout:select styleClass="DATAS" key="假別薪資設定" name="Form1Datas" property="EHF020100T0_03_TYPE"  styleId="EHF020100T0_03_TYPE" mode="E,E,I" onchange="return chkEHF020100T0_03_TYPE();" >
			<layout:options collection="listEHF020100T0_03_TYPE" property="item_id" labelProperty="item_value" />
			
			<layout:cell styleClass="DATAS" >
				&nbsp;&nbsp;
				<span id="flag_EHF020100T0_03_VAL" style="display:none;" >
					<layout:number styleClass="DATAS" key="假別薪資設定比例" property="EHF020100T0_03_VAL" styleId="EHF020100T0_03_VAL" layout="false"  size="4" maxlength="4" 
					   	   		   style="TEXT-ALIGN: RIGHT" /> 倍
				</span>
				
			</layout:cell>
			
			<layout:cell styleClass="DATAS" />
		</layout:select>
		
		<logic:equal name="button" value="init">
			<layout:cell styleClass="DATAS" />
		</logic:equal>
		
		<logic:equal name="button" value="query">
			<layout:cell styleClass="DATAS" />
		</logic:equal>

		<logic:notEqual name="button" value="query">
		<logic:notEqual name="button" value="init">
		<layout:checkbox key="是否檢核單次最低請假時數" name="Form1Datas" property="EHF020100T0_06_FLG" styleId="EHF020100T0_06_FLG" styleClass="DATAS" mode="E,E,I" onclick="return chkEHF020100T0_06_FLG();"  >
			<layout:cell styleClass="DATAS" >
				&nbsp;&nbsp;
				<span id="flag_EHF020100T0_06_FLG" style="display:none;" >
					單次最低請假時數:
					<layout:text key="年度請假總時數" property="EHF020100T0_06_day"  styleId="EHF020100T0_06_day" layout="false"   mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="4" maxlength="4"  /> 
					天
					<layout:text key="年度請假總時數" property="EHF020100T0_06_hour" styleId="EHF020100T0_06_hour" layout="false"   mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="4" maxlength="4"  /> 
					 小時
				</span>
			</layout:cell>
		</layout:checkbox>
		
		<layout:checkbox key="是否檢核年度請假總時數" name="Form1Datas" property="EHF020100T0_05_FLG" styleId="EHF020100T0_05_FLG" styleClass="DATAS" 	 mode="E,E,I" onclick="return chkEHF020100T0_05_FLG();"  >
			<layout:cell styleClass="DATAS" >
				&nbsp;&nbsp;
				<span id="flag_EHF020100T0_05_FLG" style="display:none;" >
					年度請假總時數:
					<layout:text key="年度請假總時數" property="EHF020100T0_05_day" styleId="EHF020100T0_05_day" layout="false"
								 mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="4" maxlength="4"  /> 天
					
					 <layout:text key="年度請假總時數" property="EHF020100T0_05_hour" styleId="EHF020100T0_05_hour" layout="false"
								 mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="4" maxlength="4"  /> 小時
				</span>
			</layout:cell>
		</layout:checkbox>
<%--		不管任何假別都應該檢核剩餘時數，因此"是否檢核年度假別剩餘時數"此欄位預設皆為TRUE，並且須將此欄位隱藏不讓使用這選擇 20140530 by Hedwig--%>
<%--		<layout:checkbox key="是否檢核年度假別剩餘時數" name="Form1Datas" property="EHF020100T0_09" styleClass="DATAS"  mode="E,E,I" />--%>
<%--		如果此欄位是TRUE，則此假別不需要再員工年度休假設定裡面新增就可以再請假單使用。 20140530 by Hedwig--%>
			<layout:checkbox key="是否為預設假別" 	name="Form1Datas" property="EHF020100T0_10" styleId="EHF020100T0_10" styleClass="DATAS"  mode="E,E,I" />
			<layout:checkbox key="包含休假日" 		name="Form1Datas" property="EHF020100T0_11" styleId="EHF020100T0_11" styleClass="DATAS"  mode="E,E,I" />
			<layout:checkbox key="限定連續請假" 		name="Form1Datas" property="EHF020100T0_12" styleId="EHF020100T0_12" styleClass="DATAS"  mode="E,E,I" />
		
		</logic:notEqual>
		</logic:notEqual>
		
		
		<logic:equal name="button" value="add">
			
		</logic:equal>
		
		<logic:notEqual name="button" value="add">
		
		</logic:notEqual>
	</layout:grid>
	
	<logic:notEqual name="button" value="query">
		<logic:notEqual name="button" value="init">
		<layout:grid cols="1" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text key="備註" property="EHF020100T0_07" styleId="EHF020100T0_07" mode="E,E,I" name="Form1Datas" styleClass="DATAS"  size="80" maxlength="50"  />
		</layout:grid>
		</logic:notEqual>
	</logic:notEqual>
	
		<c:if test="${State != ''}">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  styleClass="DATAS" key="修改人員"  			maxlength="12" name="Form1Datas" property="USER_UPDATE" styleId="USER_UPDATE" tooltip="修改人員" 			mode="I,I,I" />
			<layout:text  styleClass="DATAS" key="版次/最後修改日期"  	maxlength="12" name="Form1Datas" property="DATE_UPDATE" styleId="DATE_UPDATE" tooltip="版次/最後修改日期" 	mode="I,I,I"  />
		</layout:grid>
		</c:if>
		
	
	
	<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		ArrayList list = (ArrayList)request.getAttribute("Form2Datas");
		String strTmp = "";
	%>
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列" name="Form2Datas"  selectId="" selectProperty="" selectName=""   width="100%" height="500" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				EHF020100M0F FORM = (EHF020100M0F)list.get(i);
				strTmp = String.valueOf(FORM.getEHF020100T0_01());
				item_index++;
			%>
			<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		
		<layout:collectionItem property="EHF020100T0_02" title="組織單位" />
		<layout:collectionItem property="EHF020100T0_03" title="假別代號/假別" />
		<layout:collectionItem property="EHF020100T0_03_TYPE" title="假別薪資設定" />
		<layout:collectionItem property="EHF020100T0_07" title="備註" />
	</layout:collection>
		</layout:pager> 
	</logic:equal>
	
</layout:form>