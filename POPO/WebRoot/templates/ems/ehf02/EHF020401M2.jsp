<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020401M0F" %>
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


function chkDeptID(){
	if(document.getElementById("EHF020403T0_02").value == "" || document.getElementById("EHF020403T0_02").value == null ){
		alert("請先選擇部門組織!!");
		return false;
	}else{				
		return true;
	}
}

function chkClass(){
	if(document.getElementById("EHF020401T0_04").value == document.getElementById("EHF020401T0_FID").value ){
		alert("調班班別不可與目前班別一樣!!");
		document.getElementById("EHF020401T0_FID").value = "";
		return false;
	}else{
		return true;
	}
}

window.onload = function chkopen(){
	
	var open_chg_class = document.getElementById("open_chg_class").value;
	var open_edit_class = document.getElementById("open_edit_class").value;
	
	if( open_chg_class == "true" ){
		openStrutsLayoutPopup('transferClass');
		return false; 
	}
	
	if( open_edit_class == "true" ){
		openStrutsLayoutPopup('editClass');
		return false; 
	}
}
function chkCondition(){
	
	//當前端沒有勾選資料時  跳出警告視窗
	var c = document.getElementsByName("checkId"); 
	for(i=0;i<c.length;i++)
	{
  	 if(c[i].checked == true )
   		{
       		var v  = c[i].value;
        	break;
   		}
	}
	if(v=="" || v==null ){
		alert("請先選擇要修正的資料!!");
		return false;
	}else{
			openStrutsLayoutPopup('comment'); 
		return false;
	}
}
function ALL(obj) {
	//變數checkItem為checkbox的集合
	 var checkboxs = document.getElementsByName('checkId'); 
    for(var i=0;i<checkboxs.length;i++){
    	checkboxs[i].checked = obj.checked;
    	} 
}
</script>

<layout:form action="EHF020401M2.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="考勤異常處理" >
<%--	<input type="text" id="button" value="${buttonType}" />--%>
	<input type="hidden" id="open_chg_class" value="${open_chg_class}" />
	<input type="hidden" id="open_edit_class" value="${open_edit_class}" />
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020401M2" ></layout:image>
					  	
		</logic:notEqual>
		
		<logic:equal name="buttonSHOW" value="YES">
		<layout:image alt="考勤異常自動修正" mode="D,D,D" name="btnimage?text=button.auto.abattendance&type=t"  reqCode="autoFixAttData" property="EHF020401M2" confirm="您確定要執行考勤異常自動修正?" ></layout:image>
			<layout:image alt="考勤確認" mode="D,D,D" name="btnimage?text=button.att.confirm&type=t"  reqCode="confirm" property="EHF020401M2" ></layout:image>	
			<layout:image alt="強制修正" mode="D,D,D" name="btnimage?text=button.forced.correction&type=t" property="EHF020401M2" onclick="return chkCondition();"></layout:image>
			<layout:image alt="還原修正" mode="D,D,D" name="btnimage?text=button.restore.correction&type=t" confirm="您確定要還原修正?"
						  reqCode="recoveryFixAttData" property="EHF020401M2" ></layout:image>
		</logic:equal>
		
		<logic:equal name="buttonSHOW" value="NO">
			<layout:image alt="考勤回復" mode="D,D,D" name="btnimage?text=button.att.reply&type=t"  reqCode="recoveryConfirm" property="EHF020401M2" ></layout:image>	
		</logic:equal>
		<logic:equal name="SHOW" value="NO">
			<!--<layout:image alt="加班換休時數確認" mode="D,D,D" name="btnimage?text=加班換休時數確認&type=t"   property="EHF020401M2" onclick="openStrutsLayoutPopup('confirmOverTime'); return false;"></layout:image>	-->
		</logic:equal>
		
		<logic:equal name="buttonSHOW" value="YES">
		<layout:image alt="異常訊息列印" mode="D,D,D" name="btnimage?text=button.print_ERROR&type=t"  reqCode="print2" property="EHF020401M2" ></layout:image>
		</logic:equal>
		
		<logic:equal name="button" value="query" >
			
<%--			<layout:image alt="還原修正" mode="D,D,D" name="btnimage?text=還原修正&type=t" confirm="您確定要還原修正?"--%>
<%--						  reqCode="recoveryFixAttData" property="EHF020401M2" ></layout:image>--%>
<%--			<layout:image alt="調班處理" mode="D,D,D" name="btnimage?text=調班處理&type=t" confirm="您確定要調班處理?"--%>
<%--						  reqCode="transferClass" property="EHF020401M2" ></layout:image>--%>
<%--			<layout:image alt="修改考勤" mode="D,D,D" name="btnimage?text=修改考勤&type=t" confirm="您確定要修改考勤資料?"--%>
<%--						  reqCode="editClass" property="EHF020401M2" ></layout:image>--%>
		</logic:equal>
<%--		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=回前作業&type=t"  reqCode="redirect" property="EHF020401M2" ></layout:image>--%>
	</layout:row>
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="3" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">	
		
		<layout:date calendarType="datepicker" patternKey="yy/mm" mode="R,R,I"   size="10" startYear="2010" endYear="2025"  key="考勤異常年月"  name="Form1Datas" property="EHF020401T0_05" styleClass="DATAS"  />
		
		
			
		
		
<%--		<layout:cell styleClass="DATAS" ></layout:cell>--%>
		<logic:equal name="boss" value="yes">
		
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020403T0_02" styleId="EHF020403T0_02" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF000200T0_02" styleId="EHF000200T0_02"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020401M0F" 
					id="EHF020403T0_02,EHF000200T0_02,EHF000200T0_03" 
					lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
					table="EHF000200T0"
					fieldAlias="系統代碼,部門代號,部門名稱" 
					fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
					others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					mode="E,E,F"
					/>
				<layout:text layout="false" property="EHF000200T0_03" styleId="EHF000200T0_03" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020403T0_01" styleId="EHF020403T0_01" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF010100T0_02" styleId="EHF010100T0_02" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020401M0F" 
					id="EHF020403T0_01,EHF010100T0_02,EHF010100T0_05" 
					lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
					table="EHF010100T6"
					leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
					fieldAlias="系統代碼,工號,姓名" 
					fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
					parentId="EHF010100T6_02" 
					parentField="window.EHF020401M0F.EHF020403T0_02.value" 
					others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
					beforerun="chkDeptID()"	
					mode="E,E,F"																
					/>
				<layout:text layout="false" property="EHF010100T0_05" styleId="EHF010100T0_05" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			
			
			
			<layout:select key="是否修正" name="Form1Datas" property="EHF020401T0_FIX" styleId="EHF020401T0_FIX" styleClass="DATAS" mode="E,E,I" layout="true"  >
				<layout:options collection="listEHF020401T0_FIX" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:select key="顯示加班考勤" name="Form1Datas" property="OVERTIME_DATA_FLAG" styleId="OVERTIME_DATA_FLAG" styleClass="DATAS" mode="E,E,I" layout="true"  >
				<layout:options collection="listOVERTIME_DATA_FLAG" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		
		<layout:cell styleClass="DATAS" ></layout:cell>
		</logic:equal>
		
		<logic:equal name="boss_manager" value="yes">
		
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF020403T0_02" styleId="EHF020403T0_02" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF000200T0_02" styleId="EHF000200T0_02"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020401M0F" 
					id="EHF020403T0_02,EHF000200T0_02,EHF000200T0_03" 
					lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
					table="EHF000200T0"
					fieldAlias="系統代碼,部門代號,部門名稱" 
					fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
					others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
					mode="E,E,F"
					/>
				<layout:text layout="false" property="EHF000200T0_03" styleId="EHF000200T0_03" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF020403T0_01" styleId="EHF020403T0_01" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="EHF010100T0_02" styleId="EHF010100T0_02" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	
					form="EHF020401M0F" 
					id="EHF020403T0_01,EHF010100T0_02,EHF010100T0_05" 
					lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
					table="EHF010100T6"
					leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
					fieldAlias="系統代碼,工號,姓名" 
					fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
					parentId="EHF010100T6_02" 
					parentField="window.EHF020401M0F.EHF020403T0_02.value" 
					others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
					beforerun="chkDeptID()"	
					mode="E,E,F"																
					/>
				<layout:text layout="false" property="EHF010100T0_05" styleId="EHF010100T0_05" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			
			
			
			<layout:select key="是否修正" name="Form1Datas" property="EHF020401T0_FIX" styleId="EHF020401T0_FIX" styleClass="DATAS" mode="E,E,I" layout="true"  >
				<layout:options collection="listEHF020401T0_FIX" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:select key="顯示加班考勤" name="Form1Datas" property="OVERTIME_DATA_FLAG" styleId="OVERTIME_DATA_FLAG" styleClass="DATAS" mode="E,E,I" layout="true"  >
				<layout:options collection="listOVERTIME_DATA_FLAG" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		
		<layout:cell styleClass="DATAS" ></layout:cell>
		</logic:equal>

	</layout:grid>
	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
		String strTmp = "";
		
	%>
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%"  styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<logic:equal name="SHOW" value="YES">
		
		<layout:collectionItem title='<input type="checkbox" name="check" onclick="ALL(this);"> 全選 '>
			<center>
			<%
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				EHF020401M0F FORM=(EHF020401M0F)list.get(i);
				strTmp = FORM.getEHF020401T0_SUID();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		</logic:equal>
		<layout:collectionItem property="EHF020401T0_05" style="TEXT-ALIGN: CENTER" title="考勤日期" />
		<layout:collectionItem property="EHF020403T0_02" style="TEXT-ALIGN: CENTER" title="員工工號/姓名" />
		<layout:collectionItem property="EHF020401T0_04" style="TEXT-ALIGN: CENTER" title="班別" />
		<layout:collectionItem property="EHF020401T0_08_STATUS" style="TEXT-ALIGN: CENTER" title="狀態" />
		<layout:collectionItem property="EHF020401T0_06" style="TEXT-ALIGN: CENTER" title="打卡日期時間" />
		<layout:collectionItem property="EHF020401T0_09_DESC" style="TEXT-ALIGN: CENTER" title="異常說明" />
		<layout:collectionItem property="EHF020401T0_FIX" style="TEXT-ALIGN: CENTER" title="是否修正" />
		<layout:collectionItem property="EHF020401T0_PS" style="TEXT-ALIGN: LEFT" title="修正備註" />
	</layout:collection>
	</layout:pager> 	
	</logic:equal>
	
	
	<layout:popup styleId="comment" styleClass="DATAS" key="修正備註">
		<layout:text styleClass="DATAS" property="EHF020401T0_PS" key="內容" size="20" maxlength="20"name="Form1Datas"  mode="E,E,E" />
				<layout:row >				
					<layout:submit value="確認" reqCode="fixAttData" property="EHF020401M2" ></layout:submit>
					<layout:button value="取消" onclick="closeStrutsLayoutPopup('comment');" />
				</layout:row>
	</layout:popup>
	
	<logic:equal name="chg_class" value="yes">
	<layout:popup styleId="transferClass" styleClass="DATAS" key="調班處理">
		<layout:row >
   			<layout:message styleClass="MESSAGE_ERROR" key="${CHG_MSG}" />
		</layout:row>
		<layout:row>
			<layout:text styleClass="DATAS" property="EHF020401T0_05_DATE" key="考勤日期" size="20" name="Form1Datas"  mode="I,I,I" />
			<layout:text styleClass="DATAS" property="EHF020401T0_02_EMP" key="員工工號" size="20" name="Form1Datas"  mode="H,H,H" />
		</layout:row>
<%--		<layout:row>--%>
<%--			<layout:cell>--%>
<%--				<layout:message styleClass="SMALL_MESSAGE" key="原始資料" />--%>
<%--			</layout:cell>--%>
<%--		</layout:row>--%>
		<layout:collection emptyKey="沒有資料列"  name="Form3Datas" selectId="" selectProperty="" selectName=""  
						   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem property="EHF020401T0_02_EMP" style="TEXT-ALIGN: CENTER" title="員工工號/姓名" />
<%--			<layout:collectionItem property="EHF020401T0_02" style="TEXT-ALIGN: CENTER" title="感應卡" />--%>
			<layout:collectionItem property="EHF020401T0_05" style="TEXT-ALIGN: CENTER" title="考勤日期" />
			<layout:collectionItem property="EHF020401T0_04" style="TEXT-ALIGN: CENTER" title="班別" />
			<layout:collectionItem property="EHF020401T0_08" style="TEXT-ALIGN: CENTER" title="狀態" />
			<layout:collectionItem property="EHF020401T0_06" style="TEXT-ALIGN: CENTER" title="打卡日期時間" />
			<layout:collectionItem property="EHF020401T0_09_DESC" style="TEXT-ALIGN: CENTER" title="異常說明" />
		</layout:collection>
		
<%--		<logic:notEqual name="after_chg_class" value="yes" >--%>
			<layout:row>
				<layout:select key="目前班別" name="Form1Datas" property="EHF020401T0_04" styleId="EHF020401T0_04" styleClass="DATAS" mode="I,I,I" onchange="return chkClass();" >
					<layout:options collection="EHF020401T0_04_list" property="item_id" labelProperty="item_value" />
				</layout:select>
				<layout:cell>
					<layout:message styleClass="SMALL_MESSAGE" key="轉換為" />
				</layout:cell>
				<layout:select key="調班班別" name="Form1Datas" property="EHF020401T0_FID" styleId="EHF020401T0_FID" styleClass="DATAS" mode="E,E,I" onchange="return chkClass();" >
					<layout:options collection="EHF020401T0_04_list" property="item_id" labelProperty="item_value" />
				</layout:select>
			</layout:row>
<%--		</logic:notEqual>--%>
		
		<logic:equal name="after_chg_class" value="yes">
			<layout:row>
				<layout:message styleClass="SMALL_MESSAGE" key="調班後資料" />
			</layout:row>
			<layout:collection emptyKey="沒有資料列"  name="Form4Datas" selectId="" selectProperty="" selectName=""  
						       width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
				<layout:collectionItem property="EHF020401T0_02_EMP" style="TEXT-ALIGN: CENTER" title="員工工號/姓名" />
<%--				<layout:collectionItem property="EHF020401T0_02" style="TEXT-ALIGN: CENTER" title="感應卡" />--%>
				<layout:collectionItem property="EHF020401T0_05" style="TEXT-ALIGN: CENTER" title="考勤日期" />
				<layout:collectionItem property="EHF020401T0_04" style="TEXT-ALIGN: CENTER" title="班別" />
				<layout:collectionItem property="EHF020401T0_08" style="TEXT-ALIGN: CENTER" title="狀態" />
				<layout:collectionItem property="EHF020401T0_06" style="TEXT-ALIGN: CENTER" title="打卡日期時間" />
				<layout:collectionItem property="EHF020401T0_09_DESC" style="TEXT-ALIGN: CENTER" title="異常說明" />
			</layout:collection>
		</logic:equal>
		
				<layout:row >
					<logic:equal name="after_chg_class" value="yes" >
						<layout:submit value="確認調班" reqCode="finishTransfer" property="EHF020401M2" />	
					</logic:equal>
<%--					<logic:notEqual name="after_chg_class" value="yes" >--%>
						<layout:submit value="調班" reqCode="transfer" property="EHF020401M2" />
<%--					</logic:notEqual>--%>
					<layout:button value="取消" onclick="closeStrutsLayoutPopup('transferClass');" />
				</layout:row>
	</layout:popup>
	</logic:equal>


	
	
	
	
	
	<logic:equal name="edit_class" value="yes">
	
	
	<layout:popup styleId="editClass" styleClass="DATAS" key="考勤資料修改處理">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
<%--			<layout:text styleClass="DATAS" property="EHF020401T0_TYPE" key="資料類型" size="20" name="Form1Datas"  mode="I,I,I" />--%>
			<layout:text styleClass="DATAS" property="EHF020401T0_05_DATE" 		styleId="EHF020401T0_05_DATE" 	key="考勤日期" size="20" name="Form1Datas"  mode="I,I,I" />
			<layout:text styleClass="DATAS" property="EHF020401T0_02_EMP" 		styleId="EHF020401T0_02_EMP" 	key="員工工號" size="20" name="Form1Datas"  mode="H,H,H" />
			<layout:text styleClass="DATAS" property="EHF020401T0_02" 			styleId="EHF020401T0_02" 		key="員工姓名" size="20" name="Form1Datas"  mode="I,I,I" />
			<layout:text styleClass="DATAS" property="EHF020401T0_04" 			styleId="EHF020401T0_04" 		key="班別" size="20" name="Form1Datas"  mode="I,I,I" />
			
			<logic:equal name="open_card_status_select" value="true" >
				<layout:text styleClass="DATAS" property="EHF020401T0_08" styleId="EHF020401T0_08" key="狀態代碼" size="20" name="Form1Datas"  mode="H,H,H" />
				<layout:select key="狀態" name="Form1Datas" property="EHF020401T0_08_STATUS" styleId="EHF020401T0_08_STATUS" styleClass="DATAS" mode="E,E,I" layout="true" >
					<layout:options collection="list_EHF020401T0_08_STATUS" property="item_id" labelProperty="item_value" />
				</layout:select>
			</logic:equal>
			<logic:equal name="open_card_status_select" value="false" >
				<layout:text styleClass="DATAS" property="EHF020401T0_08" styleId="EHF020401T0_08" key="狀態代碼" size="20" name="Form1Datas"  mode="H,H,H" />
<%--				<layout:text styleClass="DATAS" property="EHF020401T0_08_STATUS" key="狀態" size="20" name="Form1Datas"  mode="I,I,I" />--%>
				<layout:select key="狀態" name="Form1Datas" property="EHF020401T0_08_STATUS" styleId="EHF020401T0_08_STATUS" styleClass="DATAS" mode="I,I,I" layout="true" >
					<layout:options collection="list_EHF020401T0_08_STATUS" property="item_id" labelProperty="item_value" />
				</layout:select>
			</logic:equal>
			
		</layout:grid>
		
		
		<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
			<layout:date  calendarType="datepicker" patternKey="yy/mm/dd" size="10" startYear="2010" endYear="2025"  key="打卡日期時間"  name="Form1Datas" property="EHF020401T0_07" styleClass="DATAS" mode="R,R,I" >
				&nbsp;
				<layout:select key="打卡時間(時)" name="Form1Datas" property="EHF020401T0_07_HH" styleId="EHF020401T0_07_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
					<layout:options collection="list_EHF020401T0_07_HH" property="item_id" labelProperty="item_value" />
				</layout:select>
				&nbsp;點&nbsp;
				<layout:select key="打卡時間(分)" name="Form1Datas" property="EHF020401T0_07_MM" styleId="EHF020401T0_07_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
					<layout:options collection="list_EHF020401T0_07_MM" property="item_id" labelProperty="item_value" />
				</layout:select>
				&nbsp;分
			</layout:date>
			<layout:text styleClass="DATAS" property="EHF020401T0_09_DESC" styleId="EHF020401T0_09_DESC" key="異常說明" size="20" name="Form1Datas"  mode="I,I,I" />
		</layout:grid>
		<layout:row >
			<layout:submit value="修改考勤" reqCode="editAttForm" property="EHF020401M2" />
			<layout:button value="關閉" onclick="closeStrutsLayoutPopup('editClass');" />
		</layout:row>
	</layout:popup>
	</logic:equal>
<%--以下方法，在列印時，可以出現遮罩，目前未調整遮罩的CSS，因此先不用   Alvin--%>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />

</layout:form>