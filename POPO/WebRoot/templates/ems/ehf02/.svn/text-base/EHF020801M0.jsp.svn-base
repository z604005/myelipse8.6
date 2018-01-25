<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.vacation.forms.EHF020801M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<script>
function setTime(){
	//document.getElementById("EHF020800T0_06_END").value = document.getElementById("EHF020800T0_06").value;
	$("input[name='EHF020800T0_06_END']").val($("input[name='EHF020800T0_06']").val());
	return true;
}
//chooseOne()函式，參數為觸發該函式的元素本身
function chooseOne(cb){
	//先取得同name的chekcBox的集合物件
	var obj = document.getElementsByName("checkId");
	for (i=0; i<obj.length; i++){
		//判斷obj集合中的i元素是否為cb，若否則表示未被點選
		if (obj[i]!=cb)	obj[i].checked = false;
		//若是 但原先未被勾選 則變成勾選；反之 則變為未勾選
		else	obj[i].checked = cb.checked;
		//若要至少勾選一個的話，則把上面那行else拿掉，換用下面那行
		//else obj[i].checked = true;
	}
}
function chkApplyDeptID(){
	if(document.getElementById("EHF020800T0_11").value == "" 
	   || document.getElementById("EHF020800T0_11").value == null ){
		alert("請先選擇申請人的部門!!");
		return false;
	}else{
		return true;
	}
}
</script>

<layout:form action="EHF020801M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="加班單申請" >

	<layout:row>
		<logic:notEqual name="button" value="edit">
<%--			<logic:notEqual name="button" value="Import">--%>
				<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF020801M0" ></layout:image>
				<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="addDataForm" property="EHF020801M0" ></layout:image>
				
<%--			</logic:notEqual>--%>
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
			
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t" reqCode="editDataForm" property="EHF020801M0" ></layout:image>
			<layout:image alt="刪除" 	mode="H,D,H"  name="btnimage?text=button.delete&type=t"    reqCode="delForm" 		 property="EHF020801M0" confirm="您確定要刪除資料嗎?" ></layout:image>
		</logic:equal>
		<logic:notEqual name="button" value="edit">
<%--			<logic:notEqual name="button" value="Import">--%>
				<layout:image alt="加班單匯入" mode="D,D,D" name="btnimage?text=botton.import.overtime&type=t" onclick="openStrutsLayoutPopup('fileimport'); return false;"  ></layout:image>
				<layout:image alt="範例檔下載" mode="D,D,D" name="btnimage?text=button.download.example&type=t" property="EHF020801M0"   reqCode="print_example" confirm="您確定要下載資料嗎?"  ></layout:image>
<%--			</logic:notEqual>--%>
		</logic:notEqual>
	</layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2009" endYear="2025"  key="加班日期區間" 		name="Form1Datas" property="EHF020800T0_06" 	size="10" styleClass="DATAS"  onchange="setTime()">
			&nbsp;&nbsp;~&nbsp;&nbsp;
			<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2009" endYear="2025"  key="加班日期區間(迄)"	name="Form1Datas" property="EHF020800T0_06_END" size="10" styleClass="DATAS"  layout="false" />
		</layout:date>
		<layout:cell styleClass="DATAS" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11" styleId="EHF020800T0_11" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="申請人部門" key="申請人部門" property="EHF020800T0_11_SHOW" styleId="EHF020800T0_11_SHOW"  size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020801M0F" 
				id="EHF020800T0_11,EHF020800T0_11_SHOW,EHF020800T0_11_TXT" 
				lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
				table="EHF000200T0"
				fieldAlias="系統代碼,部門代號,部門名稱" 
				fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
				others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
				mode="E,E,F"
				/>
			<layout:text layout="false" property="EHF020800T0_11_TXT" styleId="EHF020800T0_11_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020800T1_04" styleId="EHF020800T1_04" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="申請人" key="申請人" property="EHF020800T1_04_SHOW" styleId="EHF020800T1_04_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF020801M0F" 
				id="EHF020800T1_04,EHF020800T1_04_SHOW,EHF020800T1_04_TXT" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
				parentId="EHF010100T6_02" 
				parentField="window.EHF020801M0F.EHF020800T0_11.value" 
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "	
				mode="E,E,F"
				beforerun="chkApplyDeptID()"																	
			/>
			<layout:text layout="false" property="EHF020800T1_04_TXT" styleId="EHF020800T1_04_TXT" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
		</layout:text>
	</layout:grid>
	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF020801M0F ehf020801m0f =(EHF020801M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf020801m0f.getEHF020800T0_LIST();
		String strTmp = "";
		
	%>
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
	%>
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF020800T0_LIST" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
		<layout:collectionItem name="bean1" title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				EHF020801M0F FORM = (EHF020801M0F) list.get(i);
				strTmp = FORM.getEHF020800T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF020800T0_06" style="TEXT-ALIGN: CENTER" title="加班日期" />
		<layout:collectionItem property="EHF020800T0_03" style="TEXT-ALIGN: CENTER" title="填單人" />
		<layout:collectionItem property="EHF020800T0_11" style="TEXT-ALIGN: CENTER" title="加班部門" />
		<layout:collectionItem property="EHF020800T1_04" style="TEXT-ALIGN: CENTER" title="員工" />
		<layout:collectionItem property="EHF020800T1_06" style="TEXT-ALIGN: CENTER" title="加班時間" />
		<layout:collectionItem property="EHF020800T0_09_DESC" style="TEXT-ALIGN: CENTER" title="處理狀態" />
	</layout:collection>
	</layout:pager> 
	</logic:equal>
	
	<layout:popup styleId="fileimport" styleClass="DATAS" key="檔案匯入">
		<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF020800"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>

</layout:form>
<%--以下方法，在列印時，可以出現遮罩，目前未調整遮罩的CSS，因此先不用   Alvin--%>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />