<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020404M0F" %>
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


function chkDeptID(){
	if(document.getElementById("EHF020404T0_dep").value == "" || document.getElementById("EHF020404T0_dep").value == null ){
		alert("請先選擇部門組織!!");
		return false;
	}else{
			return true;
	}
}

function chkDeptIDforsave(){
	if(document.getElementById("EHF020404T0_dep").value == "" || document.getElementById("EHF020404T0_dep").value == null ){
		alert("請先選擇部門組織!!");
		return false;
	}else{
			return true;
	}
}

function fbutton(reqCode) {
				EHF020404M0F.elements['reqCode'].value=reqCode;
				EHF020404M0F.submit();
		}

function changeFormType(){
	return fbutton('init');
	
}

function getCardList(){
	return fbutton('init_add');
	
}

function setTime(){
	//document.getElementById("EHF020404T0_06").value = document.getElementById("EHF020404T0_11").value;
	$("input[name='EHF020404T0_06']").val($("input[name='EHF020404T0_11']").val());
	return true;
}

function ALL(obj) {
	//變數checkItem為checkbox的集合
	 var checkboxs = document.getElementsByName('checkId'); 
    for(var i=0;i<checkboxs.length;i++){
    	checkboxs[i].checked = obj.checked;
    	} 
}



//清除員工所有欄位
function cls2(){
	document.getElementById("EHF020404T0_emp_UID").value = "";
	document.getElementById("EHF020404T0_emp").value = "";
	document.getElementById("EHF020404T0_emp_TXT").value = "";

	return true;
}

</script>

<layout:form action="EHF020404M2.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" acceptCharset="UTF-8" key="門禁資料輸入" >
<%--	<input type="text" id="button" value="${buttonType}" />--%>
	<layout:row>
	
		<logic:equal name="button" value="query">
			<layout:image alt="查詢" 		mode="D,D,D" name="btnimage?text=button.query&type=t"   			property="EHF020404M2"	reqCode="queryForm"  ></layout:image>
			<layout:image alt="新增" 		mode="D,D,D" name="btnimage?text=button.add&type=t"  				property="EHF020404M2"	reqCode="init_add" 	 ></layout:image>
			<layout:image alt="刪除" 		mode="H,D,D" name="btnimage?text=button.delete&type=t"  			property="EHF020404M2" 	reqCode="del" 		property="EHF020404M2" confirm="您確定要刪除資料嗎?"></layout:image>
			<layout:image alt="門禁資料匯入" mode="D,D,D" name="btnimage?text=button.import.doorimp&type=t" 		property="EHF020404M2" 	onclick="openStrutsLayoutPopup('fileupload'); return false;" />
			<layout:image alt="範例檔下載"	mode="D,D,D" name="btnimage?text=button.download.example&type=t" 	property="EHF020404M2"  reqCode="print_example" ></layout:image>
			
		</logic:equal>
	
	
		<logic:equal name="button" value="save">
			<layout:image alt="儲存" 		mode="D,D,D" name="btnimage?text=button.save&type=t" 	reqCode="add" 		property="EHF020404M2" policy="all"></layout:image>
			<layout:image alt="回前作業" 	mode="D,D,D" name="btnimage?text=button.Back&type=t"  	reqCode="redirect" 	property="EHF020404M2" ></layout:image>
		</logic:equal>
		<logic:equal name="NOTNULL" value="yes">
			<%--新增加判斷    如果是搜尋以確認資料   則不顯示確認按鈕     2013/10/03 賴泓錡  />--%>
			<logic:equal name="Show" value="NO">
				<layout:image alt="確認" mode="D,D,D" name="btnimage?text=button.confirmData&type=t"  reqCode="change_EHF020404T0" property="EHF020404M2" ></layout:image>
			</logic:equal>
		</logic:equal>
		
		
		
		
		<logic:equal name="button" value="imp">
			<layout:image alt="門禁資料匯入" mode="D,D,D" name="btnimage?text=button.import.doorimp&type=t" 		property="EHF020404M2" 	onclick="openStrutsLayoutPopup('fileupload'); return false;" />
			<layout:image alt="範例檔下載"	mode="D,D,D" name="btnimage?text=button.download.example&type=t" 	property="EHF020404M2"  reqCode="print_example" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF020404M2" ></layout:image>
		</logic:equal>
		
		
	</layout:row>
	
	<layout:row >
		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 
   		 <logic:equal name="ng_emp_list" value="open">
   			<layout:link styleClass="SMALL_MESSAGE" title="未確認成功清單" href="" onclick="openStrutsLayoutPopup('ng_emp_list'); return false;" >
				<layout:message styleClass="SMALL_MESSAGE" key="未確認成功清單" />
			</layout:link>
		</logic:equal>
	</layout:row>
	
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	<logic:equal name="button" value="query">
		
		<layout:text styleClass="DATAS" key="部門系統代碼" property="EHF020404T0_dep_UID" styleId="EHF020404T0_dep_UID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020404T0_dep" styleId="EHF020404T0_dep" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF020404M0F" 
							id="EHF020404T0_dep_UID,EHF020404T0_dep,EHF020404T0_dep_TXT" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
								beforerun="cls2()"
							/>
				<layout:text layout="false" property="EHF020404T0_dep_TXT" styleId="EHF020404T0_dep_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>

<%--			<layout:text property="EHF020404T0_12" styleId="EHF020404T0_12" mode="I,I,I" styleClass="DATAS" key="員工在職狀況" size="12" name="Form1Datas" maxlength="16" isRequired="true" >--%>
<%--				<layout:checkbox styleClass="DATAS" name="Form1Datas" key="在職員工"	property="EHF020404T0_13" styleId="EHF020404T0_13" value="0" mode="E,E,I" layout="false" />在職員工--%>
<%--				<layout:checkbox styleClass="DATAS" name="Form1Datas" key="離職員工"	property="EHF020404T0_14" styleId="EHF020404T0_14" value="0" mode="E,E,I" layout="false" />離職員工--%>
<%--				<layout:checkbox styleClass="DATAS" name="Form1Datas" key="留職停薪"	property="EHF020404T0_15" styleId="EHF020404T0_15" value="0" mode="E,E,I" layout="false" />留職停薪員工--%>
<%--			</layout:text>--%>
			
			<layout:text styleClass="DATAS" key="員工系統代碼" property="EHF020404T0_emp_UID" styleId="EHF020404T0_emp_UID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020404T0_emp" styleId="EHF020404T0_emp" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF020404M0F" 
							id="EHF020404T0_emp_UID,EHF020404T0_emp,EHF020404T0_emp_TXT" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EHF020404M0F.EHF020404T0_dep_UID.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "   
							beforerun="chkDeptID()"	/>	
									
				<layout:text layout="false" property="EHF020404T0_emp_TXT" styleId="EHF020404T0_emp_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		
		
		
		<layout:select key="門禁處理狀態" name="Form1Datas" property="EHF020404T0_FLAG" styleId="EHF020404T0_FLAG" styleClass="DATAS" mode="E,E,I">
			<layout:options collection="listEHF020404T0_FLAG" property="item_id" labelProperty="item_value" />
		</layout:select>
		<layout:cell styleClass="DATAS" ></layout:cell>

		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2010" endYear="2025"	 key="考勤日期" name="Form1Datas"	property="EHF020404T0_11" styleClass="DATAS"  tooltip="◎考勤日期"  />
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  tooltip="◎打卡日期時間" startYear="2010" endYear="2025"	 size="10" maxlength="12" property="EHF020404T0_06"     key="打卡日期時間" name="Form1Datas" styleClass="DATAS"  layout="true" >
				&nbsp;&nbsp;    
				<layout:cell styleClass="DATAS" >
					<layout:select key="打卡時間(時)" name="Form1Datas" property="EHF020404T0_05_HH" styleId="EHF020404T0_05_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listEHF020404M0_05_HH" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;點&nbsp;
					<layout:select key="打卡時間(分)" name="Form1Datas" property="EHF020404T0_05_MM" styleId="EHF020404T0_05_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listEHF020404M0_05_MM" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;
				</layout:cell>
			
			</layout:date>
		</logic:equal>	
		
		
		
		<!-- =====================新增的畫面_開始======================================================================================================= -->
		
		<logic:equal name="button" value="save">
		<layout:text styleClass="DATAS" key="部門系統代碼" property="EHF020404T0_dep_UID" styleId="EHF020404T0_dep_UID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020404T0_dep" styleId="EHF020404T0_dep" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF020404M0F" 
							id="EHF020404T0_dep_UID,EHF020404T0_dep,EHF020404T0_dep_TXT" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
								beforerun="cls2()"
							/>
				<layout:text layout="false" property="EHF020404T0_dep_TXT" styleId="EHF020404T0_dep_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>

			
		<layout:text styleClass="DATAS" key="員工系統代碼" property="EHF020404T0_emp_UID" styleId="EHF020404T0_emp_UID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020404T0_emp" styleId="EHF020404T0_emp" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EHF020404M0F" 
							id="EHF020404T0_emp_UID,EHF020404T0_emp,EHF020404T0_emp_TXT" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EHF020404M0F.EHF020404T0_dep_UID.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "   
							
							beforerun="chkDeptIDforsave()" changescript="getCardList()"
							/>	
									
				<layout:text layout="false" property="EHF020404T0_emp_TXT" styleId="EHF020404T0_emp_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		
		
		<layout:select key="感應卡號" name="Form1Datas" property="EHF020404T0_03" styleId="EHF020404T0_03" styleClass="DATAS" mode="E,E,I">
			<layout:options collection="listEHF020404T0_02" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		
		<layout:cell styleClass="DATAS" ></layout:cell>
		
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2012" endYear="2025"	 key="考勤日期" name="Form1Datas"	property="EHF020404T0_11" styleClass="DATAS"  tooltip="◎考勤日期" onchange="setTime()"/>
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2012" endYear="2025"  key="打卡日期時間" tooltip="◎打卡日期時間"   maxlength="12" property="EHF020404T0_06"  name="Form1Datas" styleClass="DATAS"  layout="true" onkeydown="nextFiled()" >
				&nbsp;&nbsp;    
				<layout:cell styleClass="DATAS" >
					<layout:select key="打卡時間(時)" name="Form1Datas" property="EHF020404T0_05_HH" styleId="EHF020404T0_05_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listEHF020404M0_05_HH" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;點&nbsp;
					<layout:select key="打卡時間(分)" name="Form1Datas" property="EHF020404T0_05_MM" styleId="EHF020404T0_05_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listEHF020404M0_05_MM" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;分&nbsp;&nbsp;&nbsp;&nbsp;
				</layout:cell>
			</layout:date>
		</logic:equal>	
		
		<!-- =====================新增的畫面_結束======================================================================================================= -->
		
		
	</layout:grid>

	<%
		int item_index = 0;
		EHF020404M0F ehf020800m0f =(EHF020404M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf020800m0f.getEHF020404T0_LIST();
		String strTmp = "";
		
	%>
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
		
	<logic:equal name="QUERYDATA" value="YES">
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="50" >
	<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF020404T0_LIST"  selectId="" selectProperty="" selectName=""  width="100%" height="400" styleClass="COLLECTION" styleClass2="COLLECTION_2">
<%--		<layout:collectionItem title="選擇">--%>
		<layout:collectionItem title='<input type="checkbox" name="check" onclick="ALL(this);"> 全選 '>
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				Map FORM = (Map) list.get(i);
				strTmp = ((String) FORM.get("EHF020404T0_01"))+"";
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF020404T0_dep" title="組織單位"    style="TEXT-ALIGN: CENTER"/>
		<layout:collectionItem property="EHF020404T0_emp" title="員工"       style="TEXT-ALIGN: CENTER"/>
		<layout:collectionItem property="EHF020404T0_03" title="感應卡號"    style="TEXT-ALIGN: CENTER"/>
		<layout:collectionItem property="EHF020404T0_11" title="考勤日期"    style="TEXT-ALIGN: CENTER" sortable="true"/>
		<layout:collectionItem property="EHF020404T0_06" title="打卡日期時間" style="TEXT-ALIGN: CENTER"/>
	</layout:collection>
	</layout:pager> 
	</logic:equal>

	
	<logic:equal name="correct_collection" value="show">
		<layout:row>
			<layout:message styleClass="" key="門禁資料正確匯入詳細資料" />
		</layout:row>
		
			<layout:collection emptyKey="沒有資料列"  name="Form3Datas" selectId="" selectProperty="" selectName=""  width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
<%--				<layout:collectionItem property="EHF020404T0_dep" title="組織單位"    style="TEXT-ALIGN: CENTER"/>--%>
<%--				<layout:collectionItem property="EHF020404T0_emp" title="員工"       style="TEXT-ALIGN: CENTER"/>--%>
				<layout:collectionItem property="EHF020404T0_03" title="感應卡號"    style="TEXT-ALIGN: CENTER"/>
				<layout:collectionItem property="EHF020404T0_11" title="考勤日期"    style="TEXT-ALIGN: CENTER" />
				<layout:collectionItem property="EHF020404T0_06" title="打卡日期時間" style="TEXT-ALIGN: CENTER"/>
			</layout:collection>
	</logic:equal>
	
	
	<logic:equal name="ng_collection" value="show">
		<layout:row>
			 <layout:message styleClass="MESSAGE_ERROR" key="門禁資料未匯入詳細資料" />
		</layout:row>
	
		<layout:collection emptyKey="沒有資料列"  name="Form4Datas" selectId="" selectProperty="" selectName=""  width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
<%--				<layout:collectionItem property="EHF020404T0_dep" title="組織單位"    style="TEXT-ALIGN: CENTER"/>--%>
<%--				<layout:collectionItem property="EHF020404T0_emp" title="員工"       style="TEXT-ALIGN: CENTER"/>--%>
				<layout:collectionItem property="EHF020404T0_03" title="感應卡號"    style="TEXT-ALIGN: CENTER"/>
				<layout:collectionItem property="EHF020404T0_11" title="考勤日期"    style="TEXT-ALIGN: CENTER"/>
				<layout:collectionItem property="EHF020404T0_06" title="打卡日期時間" style="TEXT-ALIGN: CENTER"/>
				<layout:collectionItem property="EHF020404T0_10" title="錯誤訊息" style="TEXT-ALIGN: CENTER"/>
		</layout:collection>
	</logic:equal>
	
	
		<%--  未確認成功清單	--%>
	<logic:equal name="ng_emp_list" value="open">
	<layout:popup styleId="ng_emp_list" styleClass="DATAS" key="未確認成功清單" >



		<layout:collection emptyKey="沒有資料列"  name="Form5Datas"   selectId="" selectProperty="" selectName=""     width="100%" height="200" styleClass="COLLECTION" styleClass2="COLLECTION_2" >

			<layout:collectionItem property="EHF020404T0_dep" 	title="部門名稱"    	style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF020404T0_emp" 	title="員工姓名"    	style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF020404T0_06" 	title="打卡日期時間" style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF020404T0_FLAG" 	title="未修正原因" style="TEXT-ALIGN: CENTER"/>
			
		</layout:collection>
		<layout:row >
			<layout:button value="關閉" onclick="closeStrutsLayoutPopup('ng_emp_list');" />
		</layout:row>
				
	</layout:popup>
	</logic:equal>
	
	
	
	<layout:popup styleId="fileupload" styleClass="DATAS" key="檔案匯入">
		<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF020404"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileupload');" />
				</layout:row>
	</layout:popup>
	
	

</layout:form>

<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />

