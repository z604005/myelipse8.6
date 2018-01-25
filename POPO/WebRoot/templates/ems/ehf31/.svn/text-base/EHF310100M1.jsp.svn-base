<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF310100M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<!-- JavaScript -->
<script type="text/javascript">

function fbutton(reqCode) {
	EHF310100M0F.elements['reqCode'].value=reqCode;
	EHF310100M0F.submit();
}

function chkchange(obj){
	
	if(obj=="CUS"){		
		document.getElementById("EHF310100T0_TYPE").value = "CUS";
	}else if(obj=="ORD"){		
		document.getElementById("EHF310100T0_TYPE").value = "ORD";
	}else if(obj=="SPE"){
		document.getElementById("EHF310100T0_TYPE").value = "SPE";
	}else if(obj=="CALL"){
		document.getElementById("EHF310100T0_TYPE").value = "CALL";
	}else if(obj=="PAY"){
		document.getElementById("EHF310100T0_TYPE").value = "PAY";
	}else if(obj=="PAY_DETAIL"){
		document.getElementById("EHF310100T0_TYPE").value = "PAY_DETAIL";
	}else if(obj=="GIFT"){
		document.getElementById("EHF310100T0_TYPE").value = "GIFT";
	}
	
}

function setAction(){
	
	var saveType = document.getElementById("EHF310100T0_TYPE").value;
	
	if(saveType=="CUS"){	//產婦基本資料
		return fbutton('saveDataCUS');
	}else if(saveType=="ORD"){	//訂餐資訊
		return fbutton('saveDataORD');
	}else if(saveType=="SPE"){	//訂餐特殊資訊
		return fbutton('saveDataSPE');
	}else if(saveType=="CALL"){	//電訪記錄
		return fbutton('saveDataCALL');
	}else if(saveType=="PAY"){	//付款資訊
		return fbutton('saveDataPAY');
	}else if(saveType=="PAY_DETAIL"){	//付款明細
		return fbutton('saveDataPAYDETAIL');
	}else if(saveType=="GIFT"){	//贈品資訊
		return fbutton('saveDataGIFT');
	}else{
		return fbutton('saveDataCUS');
	}
}

function chkApplyFoodID(){
	if(document.getElementById("EHF310200T1_03_type").value == "" || document.getElementById("EHF310200T1_03_type").value == null ){
		alert("請先選擇食物代碼!!");
		return false;
	}else{
		return true;
	}
}

function cls2(){
	document.getElementById("EHF310200T1_03_detail").value = "";
	document.getElementById("EHF310200T1_03_detail_TXT").value = "";

	return true;
}
</script>

<layout:form action="EHF310100M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" acceptCharset="UTF-8" key="客戶需求單">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF310100M1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" property="EHF310100M1" onclick="setAction(); return false;" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<%--<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF310100M1" ></layout:image>--%>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF310100M1" ></layout:image>
    </layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="請注意!每個分頁修改資料後需要先按下儲存才會生效。" />
	<layout:row >
	
	</layout:row>
	<%--
	<%
			String strTmp = "";			
			
			EHF310100M0F FORM=(EHF310100M0F)request.getAttribute("Form1Datas");
			
			strTmp = FORM.getEHF310100T0_01().replace("\\","//");	
			
	%>
	--%>
	<layout:tabs styleClass="DATAS" width="100%" selectedTabKeyName="EHF310100M1_Tabs_01">
	
				<layout:text styleClass="DATAS" key="系統編號" property="EHF310100T0_01" styleId="EHF310100T0_01" mode="H,H,H" name="Form1Datas" />
				<input type="hidden" id="EHF310100T0_TYPE" value="" />
	
		<layout:tab key="產婦基本資料" width="15%" >
			
			
				<layout:grid cols="3" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
				
					<layout:text styleClass="DATAS" key="檔案編號" property="EHF310100T0_02" styleId="EHF310100T0_02" size="7" mode="E,E,I" maxlength="7" tooltip="檔案編號" name="Form1Datas" onchange="return chkchange('CUS');" />
					
					<layout:text styleClass="DATAS" key="櫃號" property="EHF310100T0_03" styleId="EHF310100T0_03" size="3" mode="E,E,I" maxlength="3" tooltip="櫃號" name="Form1Datas" onchange="return chkchange('CUS');" />
					
					<layout:text styleClass="DATAS" key="產婦姓名" property="EHF310100T0_04" styleId="EHF310100T0_04" size="10" mode="E,E,I" maxlength="10" tooltip="產婦姓名" name="Form1Datas"  onchange="return chkchange('CUS');" isRequired="true"/>
					
					<layout:select styleClass="DATAS" key="產別" property="EHF310100T0_05" styleId="EHF310100T0_05" mode="E,E,I" name="Form1Datas" onchange="return chkchange('CUS');">
						<layout:options collection="listEHF310100T0_05" property="item_id" labelProperty="item_value" />
					</layout:select>
					
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="預產期" size="10"  name="Form1Datas" property="EHF310100T0_06" styleClass="DATAS" onchange="return chkchange('CUS');"/>
					
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="生產期" size="10"  name="Form1Datas" property="EHF310100T0_07" styleClass="DATAS" onchange="return chkchange('CUS');"/>
					
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="訂餐日期" size="10"  name="Form1Datas" property="EHF310100T0_08" styleClass="DATAS" onchange="return chkchange('CUS');"/>

					<layout:number styleClass="DATAS" key="訂餐天數*" property="EHF310100T0_09"  styleId="EHF310100T0_09" size="5" mode="E,E,I" maxlength="3" tooltip="訂餐天數*" name="Form1Datas"  onchange="return chkchange('CUS');" isRequired="true"/>

					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="送餐日期" size="10"  name="Form1Datas" property="EHF310100T0_10" styleClass="DATAS" onchange="return chkchange('CUS');"/>
					
					<layout:number key="醫院代碼" size="" maxlength="" name="Form1Datas" property="EHF310100T0_11"  styleId="EHF310100T0_11" styleClass="DATAS" mode="H,H,H" tooltip="醫院代碼"/>
					<layout:text styleClass="DATAS" key="醫院名稱" property="EHF310100T0_11_TXT"  styleId="EHF310100T0_11_TXT" size="20" mode="R,R,I" maxlength="20" tooltip="醫院名稱" name="Form1Datas">
						<sp:lov 	form="EHF310100M0F" 
									id="EHF310100T0_11,EHF310100T0_11_TXT" 
									lovField="EHF300200T0_01,EHF300200T0_02" 
									table="EHF300200T0"
									fieldAlias="醫院代碼,醫院名稱" 
									fieldName="EHF300200T0_01,EHF300200T0_02"											
									mode="E,E,F"									
									others=" AND EHF300200T0_06 = '${compid}' AND EHF300200T0_05 = '1' "
									changescript="chkchange('CUS')"
						/>					 
					</layout:text>
					
					<layout:text styleClass="DATAS" key="醫院房號" property="EHF310100T0_15"  styleId="EHF310100T0_15" size="10" mode="E,E,I" maxlength="10" tooltip="醫院房號" name="Form1Datas" onchange="return chkchange('CUS');"/>
					
<%--					<layout:text styleClass="DATAS" key="醫院路線" property="EHF310100T0_16"  styleId="EHF310100T0_16" size="10" mode="R,R,I" maxlength="10" tooltip="醫院路線" name="Form1Datas" onchange="return chkchange('CUS');"/>--%>
					<layout:number key="醫院路線代碼" size="" maxlength="" name="Form1Datas" property="EHF310100T0_16"  styleId="EHF310100T0_16" styleClass="DATAS" mode="H,H,H" tooltip="醫院路線代碼"/>
					<layout:text styleClass="DATAS" key="醫院路線名稱" property="EHF310100T0_16_TXT"  styleId="EHF310100T0_16_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="醫院路線名稱" name="Form1Datas">
						<sp:lov 	form="EHF310100M0F" 
									id="EHF310100T0_16,EHF310100T0_16_TXT" 
									lovField="EHF300100T0_01,EHF300100T0_02" 
									table="EHF300100T0"
									fieldAlias="路線代碼,路線名稱" 
									fieldName="EHF300100T0_01,EHF300100T0_02"	
									mode="E,E,F"								
									others=" AND EHF300100T0_06 = '${compid}' AND EHF300100T0_04 = '1' "
									changescript="chkchange('CUS')"
						/>					
					</layout:text>
					
					<layout:number key="醫院用餐天數" size="5" maxlength="3" name="Form1Datas" property="EHF310100T0_12"  styleId="EHF310100T0_12" styleClass="DATAS" mode="E,E,I" tooltip="醫院用餐天數" onchange="return chkchange('CUS');"/>
					
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="醫院用餐期間起" size="10"  name="Form1Datas" property="EHF310100T0_13" styleClass="DATAS" onchange="return chkchange('CUS');"/>
					
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="醫院用餐期間迄" size="10"  name="Form1Datas" property="EHF310100T0_14" styleClass="DATAS" onchange="return chkchange('CUS');"/>
					
					<layout:number key="住宅路線代碼" size="" maxlength="" name="Form1Datas" property="EHF310100T0_17"  styleId="EHF310100T0_17" styleClass="DATAS" mode="H,H,H" tooltip="住宅路線代碼"/>
					<layout:text styleClass="DATAS" key="住宅路線名稱" property="EHF310100T0_17_TXT"  styleId="EHF310100T0_17_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="住宅路線名稱" name="Form1Datas">
						<sp:lov 	form="EHF310100M0F" 
									id="EHF310100T0_17,EHF310100T0_17_TXT" 
									lovField="EHF300100T0_01,EHF300100T0_02" 
									table="EHF300100T0"
									fieldAlias="路線代碼,路線名稱" 
									fieldName="EHF300100T0_01,EHF300100T0_02"	
									mode="E,E,F"								
									others=" AND EHF300100T0_06 = '${compid}' AND EHF300100T0_04 = '1' "
									changescript="chkchange('CUS')"
						/>					
					</layout:text>
					
					<layout:cell styleClass="DATAS"/>
					
					<layout:number key="住宅用餐天數" size="5" maxlength="3" name="Form1Datas" property="EHF310100T0_18"  styleId="EHF310100T0_18" styleClass="DATAS" mode="E,E,I" tooltip="住宅用餐天數" onchange="return chkchange('CUS');"/>
					
					<layout:cell styleClass="DATAS"/>
					
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="住宅用餐期間起" size="10"  name="Form1Datas" property="EHF310100T0_19" styleClass="DATAS" onchange="return chkchange('CUS');"/>
					
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="住宅用餐期間迄" size="10"  name="Form1Datas" property="EHF310100T0_20" styleClass="DATAS" onchange="return chkchange('CUS');"/>
					
					<layout:text styleClass="DATAS" key="行動電話(產婦)" property="EHF310100T0_21"  styleId="EHF310100T0_21" size="10" mode="E,E,I" maxlength="15" tooltip="行動電話(產婦)" name="Form1Datas"  isRequired="true" onchange="return chkchange('CUS');"/>
					
					<layout:text styleClass="DATAS" key="連絡1" property="EHF310100T0_23"  styleId="EHF310100T0_23" size="4" mode="E,E,I" maxlength="4" tooltip="連絡1" name="Form1Datas" onchange="return chkchange('CUS');">
						<layout:text styleClass="DATAS" key="連絡1" property="EHF310100T0_24"  styleId="EHF310100T0_24" size="10" mode="E,E,I" maxlength="15" tooltip="連絡1" name="Form1Datas" layout="false" onchange="return chkchange('CUS');"/>
					</layout:text>
					
					<layout:text styleClass="DATAS" key="連絡2" property="EHF310100T0_25"  styleId="EHF310100T0_25" size="4" mode="E,E,I" maxlength="4" tooltip="連絡2" name="Form1Datas" onchange="return chkchange('CUS');">
						<layout:text styleClass="DATAS" key="連絡2" property="EHF310100T0_26"  styleId="EHF310100T0_26" size="10" mode="E,E,I" maxlength="15" tooltip="連絡2" name="Form1Datas" layout="false" onchange="return chkchange('CUS');"/>
					</layout:text>
					
					<layout:text styleClass="DATAS" key="連絡電話(住)" property="EHF310100T0_22"  styleId="EHF310100T0_22" size="10" mode="E,E,I" maxlength="15" tooltip="連絡電話(住)" name="Form1Datas" onchange="return chkchange('CUS');"/>
					
					<layout:text styleClass="DATAS" key="連絡3" property="EHF310100T0_27"  styleId="EHF310100T0_27" size="4" mode="E,E,I" maxlength="4" tooltip="連絡3" name="Form1Datas" onchange="return chkchange('CUS');">
						<layout:text styleClass="DATAS" key="連絡3" property="EHF310100T0_28"  styleId="EHF310100T0_28" size="10" mode="E,E,I" maxlength="15" tooltip="連絡3" name="Form1Datas" layout="false" onchange="return chkchange('CUS');"/>
					</layout:text>
					
					<layout:text styleClass="DATAS" key="連絡4" property="EHF310100T0_29"  styleId="EHF310100T0_29" size="4" mode="E,E,I" maxlength="4" tooltip="連絡4" name="Form1Datas" onchange="return chkchange('CUS');">
						<layout:text styleClass="DATAS" key="連絡4" property="EHF310100T0_30"  styleId="EHF310100T0_30" size="10" mode="E,E,I" maxlength="15" tooltip="連絡4" name="Form1Datas" layout="false" onchange="return chkchange('CUS');"/>
					</layout:text>	
												
				</layout:grid>
				
				<layout:grid cols="1" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
				
					<layout:text styleClass="DATAS" key="產婦地址*" property="EHF310100T0_31"  styleId="EHF310100T0_31" size="80" mode="E,E,I" maxlength="100" tooltip="產婦地址*" name="Form1Datas" onchange="return chkchange('CUS');"/>
					
					<layout:text styleClass="DATAS" key="備註1" property="EHF310100T0_32"  styleId="EHF310100T0_32" size="80" mode="E,E,I" maxlength="100" tooltip="備註1" name="Form1Datas" onchange="return chkchange('CUS');"/>
					
					<layout:text styleClass="DATAS" key="備註2" property="EHF310100T0_33"  styleId="EHF310100T0_33" size="80" mode="E,E,I" maxlength="100" tooltip="備註2" name="Form1Datas" onchange="return chkchange('CUS');"/>
				
				</layout:grid>
				
		</layout:tab>
		
		<layout:notMode value="create">
		<layout:tab key="訂餐資訊" width="15%" >
		
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:text styleClass="DATAS" key="產婦姓名" property="EHF310100T0_04"  styleId="EHF310100T0_04" size="10" mode="I,I,I" maxlength="10" tooltip="產婦姓名" name="Form1Datas"/>
				
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I"  startYear="2015" endYear="2030"  key="開始送餐日" size="10"  name="Form1Datas" property="EHF310100T0_10" styleClass="DATAS"/>
			
			</layout:grid>
			
			<layout:row>
				<layout:message styleClass="LARGE_MESSAGE" key="開始設定養生飲品明細" />
			</layout:row>
			
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="日期(起)" size="10"  name="Form1Datas" property="EHF310100T2_03" styleClass="DATAS"/>
				
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="日期(迄)" size="10"  name="Form1Datas" property="EHF310100T2_04" styleClass="DATAS"/>
				
				<layout:radios styleClass="DATAS" key="餐別" cols="4" property="EHF310100T2_05" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310100T2_05" property="item_id" labelProperty="item_value" />
				</layout:radios>
				
				<layout:checkboxes styleClass="DATAS" key="飲品" cols="6" property="EHF310100T2_06_total" styleId="EHF310100T2_06_total" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310100T2_06" property="item_id" labelProperty="item_value" />
				</layout:checkboxes>
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310100T2');" ></layout:image>
				</layout:cell>
				<layout:cell styleClass="DATAS"/>
				
			</layout:grid>
			
			<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG_DETAIL_DRINK}" />
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310100T2_List" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
									
					<logic:equal name="status" value="yes">
						<layout:collectionItem property="EHF310100T2_02" title="順序編號" style="text-align: center" />
					</logic:equal>
					<layout:collectionItem property="EHF310100T2_03" title="日期" style="text-align: center" />
					<layout:collectionItem property="EHF310100T2_06" title="送餐內容" style="text-align: center" />
					<layout:collectionItem property="EHF310100T2_05" title="餐別" style="text-align: center" />
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310100T2_02,EHF310100T2_03,EHF310100T2_05,EHF310100T2_06" 
						paramProperty="EHF310100T0_01,EHF310100T2_02,EHF310100T2_03,EHF310100T2_05,EHF310100T2_06"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310100T2" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
			<layout:row>				
				<layout:message styleClass="LARGE_MESSAGE" key="開始設定每日明細" />
			</layout:row>
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="日期(起)" size="10"  name="Form1Datas" property="EHF310100T1_02_B" styleClass="DATAS"/>
				
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="日期(迄)" size="10"  name="Form1Datas" property="EHF310100T1_02_E" styleClass="DATAS"/>
				
			</layout:grid>
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:checkboxes styleClass="DATAS" cols="3" key="餐別" property="EHF310100T1_03_total" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310100T1_03" property="item_id" labelProperty="item_value" />
				</layout:checkboxes>
				
				<layout:checkboxes styleClass="DATAS" cols="3" key="醫院的路線" property="EHF310100T1_04_total" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310100T1_03" property="item_id" labelProperty="item_value" />
						&nbsp;不選，預設住宅路線
				</layout:checkboxes>
				
				<layout:checkboxes styleClass="DATAS" cols="3" key="餐點素食" cols="4" property="EHF310100T1_05_total" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310100T1_03" property="item_id" labelProperty="item_value" />
						&nbsp;不選，預設葷
				</layout:checkboxes>
				
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增天數" mode="D,D,D" name="btnimage?text=button.addDateData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310100T1');" ></layout:image>
					<layout:image alt="儲存明細" mode="D,D,D" name="btnimage?text=button.saveDetailData&type=t" property="EHF310100M1" 
							      onclick="setAction();return confirmShowEMSWait('若修改內容是沒有訂餐的資料將不會儲存'); return false;" ></layout:image>
					<layout:image alt="列印用餐確認表" mode="D,D,D" name="btnimage?text=button.print_order&type=t" property="EHF310100M1" 
								  reqCode="print" ></layout:image>
				</layout:cell>
				
			</layout:grid>
			<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG_DETAIL}" />
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310100T1_List" styleId="EHF310100T1_List"
							   id="bean1" indexId="index" 
							   selectId="" selectProperty="" selectName="" 
							   width="100%" 
							   styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					
					<layout:collectionItem title="日期" style="text-align: center" >
						<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I"  startYear="2015" endYear="2030"  key="日期" size="10"  name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_02" styleClass="DATAS" layout="false"/>
					</layout:collectionItem>				
					
					
					<layout:collectionItem title="A(早餐)" style="text-align: center" >
					
						<%--<layout:text styleClass="DATAS" property="EHF020814T0_02" styleId="EHF020814T0_02" mode="I,I,I" name="Form1Datas" layout="false">	--%>											
									是否訂餐：
									<layout:select key="餐別" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_03_A" styleId="EHF310100T1_List[${index}].EHF310100T1_03_A" styleClass="DATAS" mode="I,I,I" maxlength="3" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_03_TF" property="item_id" labelProperty="item_value" />
									</layout:select>
									&nbsp;&nbsp;路線：
									<layout:select key="路線" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_04_A" styleId="EHF310100T1_List[${index}].EHF310100T1_04_A" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_04" property="item_id" labelProperty="item_value" />
									</layout:select>
									&nbsp;&nbsp;素食：
									<layout:select key="葷素" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_05_A" styleId="EHF310100T1_List[${index}].EHF310100T1_05_A" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_05" property="item_id" labelProperty="item_value" />
									</layout:select>
									
						<%--</layout:text>--%>
						
					</layout:collectionItem>
					
					<layout:collectionItem title="B(中餐)" style="text-align: center" >
									是否訂餐：
									<layout:select key="餐別" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_03_B" styleId="EHF310100T1_List[${index}].EHF310100T1_03_B" styleClass="DATAS" mode="I,I,I" maxlength="3" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_03_TF" property="item_id" labelProperty="item_value" />
									</layout:select>
									&nbsp;&nbsp;路線：
									<layout:select key="路線" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_04_B" styleId="EHF310100T1_List[${index}].EHF310100T1_04_B" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_04" property="item_id" labelProperty="item_value" />
									</layout:select>
									&nbsp;&nbsp;素食：
									<layout:select key="葷素" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_05_B" styleId="EHF310100T1_List[${index}].EHF310100T1_05_B" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_05" property="item_id" labelProperty="item_value" />
									</layout:select>
									
					</layout:collectionItem>
					
					<layout:collectionItem title="C(晚餐)" style="text-align: center" >
									是否訂餐：
									<layout:select key="餐別" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_03_C" styleId="EHF310100T1_List[${index}].EHF310100T1_03_C" styleClass="DATAS" mode="I,I,I" maxlength="3" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_03_TF" property="item_id" labelProperty="item_value" />
									</layout:select>
									&nbsp;&nbsp;路線：
									<layout:select key="路線" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_04_C" styleId="EHF310100T1_List[${index}].EHF310100T1_04_C" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_04" property="item_id" labelProperty="item_value" />
									</layout:select>
									&nbsp;&nbsp;素食：
									<layout:select key="葷素" name="Form1Datas" property="EHF310100T1_List[${index}].EHF310100T1_05_C" styleId="EHF310100T1_List[${index}].EHF310100T1_05_C" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('ORD');" layout="false" >
										<layout:options collection="listEHF310100T1_05" property="item_id" labelProperty="item_value" />
									</layout:select>
					
					</layout:collectionItem>
					
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310100T1_02" 
						paramProperty="EHF310100T0_01,EHF310100T1_02"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310100T1" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
		</layout:tab>
		
		<layout:tab key="訂餐特殊資訊" width="15%" >
		
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:text styleClass="DATAS" key="產婦姓名" property="EHF310100T0_04"  styleId="EHF310100T0_04" size="10" mode="I,I,I" maxlength="10" tooltip="產婦姓名" name="Form1Datas"/>
				
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I"  startYear="2015" endYear="2030"  key="開始送餐日" size="10"  name="Form1Datas" property="EHF310100T0_10" styleClass="DATAS"/>
			
				<layout:checkbox styleClass="DATAS" key="特殊習慣" property="EHF310200T0_02"  styleId="EHF310200T0_02" mode="E,E,I" maxlength="10" tooltip="特殊習慣" name="Form1Datas" onchange="return chkchange('SPE');">
				特殊不吃食物(人工處理)
				</layout:checkbox>
				
				<layout:text styleClass="DATAS" key="備註" property="EHF310200T0_03"  styleId="EHF310200T0_03" size="80" mode="E,E,I" maxlength="50" tooltip="備註" name="Form1Datas" onchange="return chkchange('SPE');"/>
				
			</layout:grid>
		
			<layout:row>				
				<layout:message styleClass="LARGE_MESSAGE" key="開始設定不吃食物明細" />
			</layout:row>
			
			<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG_DETAIL_DONTEAT}" />
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:text styleClass="DATAS" key="不吃食物" property="EHF310200T1_03" styleId="EHF310200T1_03" mode="I,I,I" name="Form1Datas" >		
				
					<%--<layout:select styleClass="DATAS" key="不吃食物" property="EHF310200T1_03_type" mode="E,E,I" name="Form1Datas" layout="false">=
							<layout:options collection="listEHF310100T1_04" property="item_id" labelProperty="item_value" />
					</layout:select>--%>
					
					<layout:number key="不吃食物代碼" size="" maxlength="" name="Form1Datas" property="EHF310200T1_03_type"  styleId="EHF310200T1_03_type" styleClass="DATAS" mode="H,H,H" tooltip="不吃食物代碼" layout="false"/>
					<layout:text styleClass="DATAS" key="不吃食物" property="EHF310200T1_03_type_TXT"  styleId="EHF310200T1_03_type_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="不吃食物名稱" name="Form1Datas" layout="false">
						<sp:lov 	form="EHF310100M0F" 
									id="EHF310200T1_03_type,EHF310200T1_03_type_TXT" 
									lovField="PSFOODT0_01,PSFOODT0_04" 
									table="FOODT0"
									fieldAlias="食物代碼,類別名稱" 
									fieldName="PSFOODT0_01,PSFOODT0_04"	
									mode="E,E,F"				
									beforerun="cls2()"					
									others="AND PSFOODT0_07 = '1' AND PSFOODT0_08 = '${compid}' "
						/>					
					</layout:text>
					
					<%--<layout:select styleClass="DATAS" key="" property="EHF310200T1_03_detail" mode="E,E,I" name="Form1Datas" layout="false">
							<layout:options collection="listEHF310100T1_04" property="item_id" labelProperty="item_value" />
					</layout:select>--%>
					
					<layout:number key="不吃食物明細代碼" size="" maxlength="" name="Form1Datas" property="EHF310200T1_03_detail"  styleId="EHF310200T1_03_detail" styleClass="DATAS" mode="H,H,H" tooltip="不吃食物明細代碼" layout="false"/>
					<layout:text styleClass="DATAS" key="不吃食物明細名稱" property="EHF310200T1_03_detail_TXT"  styleId="EHF310200T1_03_detail_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="不吃食物明細名稱" name="Form1Datas" layout="false">
						<sp:lov 	form="EHF310100M0F" 
									id="EHF310200T1_03_detail,EHF310200T1_03_detail_TXT" 
									lovField="PSFOODT1_04,PSFOODT1_05" 
									table="FOODT1"
									fieldAlias="代碼,代碼名稱" 
									fieldName="PSFOODT1_04,PSFOODT1_05"	
									parentId="PSFOODT1_01" 
									parentField="window.EHF310100M0F.EHF310200T1_03_type.value" 
									mode="E,E,F"					
									beforerun="chkApplyFoodID()"			
									others="AND PSFOODT1_08 = '1' AND PSFOODT1_09 = '${compid}' "
						/>					
					</layout:text>
						
				</layout:text>
				
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310200T1');" ></layout:image>
				</layout:cell>
				
			</layout:grid>
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310200T1_List" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
									
					<layout:collectionItem property="EHF310200T1_03" title="不吃食物" style="text-align: center" />
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310200T1_02,EHF310200T1_03" 
						paramProperty="EHF310100T0_01,EHF310200T1_02,EHF310200T1_03"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310200T1" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
			<layout:row>				
				<layout:message styleClass="LARGE_MESSAGE" key="開始設定不喝飲品明細" />
			</layout:row>

			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:select styleClass="DATAS" key="不喝飲品" property="EHF310200T2_03" mode="E,E,I" name="Form1Datas" >
					<layout:options collection="listEHF310200T2_03" property="item_id" labelProperty="item_value" />
				</layout:select>
				
				<%--<layout:text key="不喝飲品系統編號" size="" maxlength="" name="Form1Datas" property="EHF310200T2_03"  styleId="EHF310200T2_03" styleClass="DATAS" mode="H,H,H" tooltip="不喝飲品系統編號"/>
				<layout:text key="不喝飲品茶飲代號" size="" maxlength="" name="Form1Datas" property="EHF310200T2_03_SHOW"  styleId="EHF310200T2_03_SHOW" styleClass="DATAS" mode="H,H,H" tooltip="不喝飲品茶飲代號"/>
				<layout:text styleClass="DATAS" key="不喝飲品" property="EHF310200T2_03_TXT"  styleId="EHF310200T2_03_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="不喝飲品名稱" name="Form1Datas">
					<sp:lov 	form="EHF310100M0F" 
								id="EHF310200T2_03,EHF310200T2_03_SHOW,EHF310200T2_03_TXT" 
								lovField="EHF300400T0_01,EHF300400T0_03,EHF300400T0_04" 
								table="EHF300400T0"
								fieldAlias="系統編號,茶飲代號,茶飲名稱" 
								fieldName="EHF300400T0_01,EHF300400T0_03,EHF300400T0_04"	
								mode="E,E,F"				
								others="AND EHF300400T0_05 = '1' AND EHF300400T0_06 = '${compid}' GROUP BY EHF300400T0_04 "
								
					/>					
				</layout:text>--%>
				
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310200T2');" ></layout:image>
				</layout:cell>
				
			</layout:grid>
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310200T2_List" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
									
					<layout:collectionItem property="EHF310200T2_03" title="不喝飲品" style="text-align: center" />
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310200T2_02,EHF310200T2_03" 
						paramProperty="EHF310100T0_01,EHF310200T2_02,EHF310200T2_03"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310200T2" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
			<layout:row>				
				<layout:message styleClass="LARGE_MESSAGE" key="開始設定加量需求明細" />
			</layout:row>

			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:select styleClass="DATAS" key="加量需求" property="EHF310200T3_03" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310200T3_03" property="item_id" labelProperty="item_value" />
				</layout:select>
				
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310200T3');" ></layout:image>
				</layout:cell>
				
			</layout:grid>
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310200T3_List" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
									
					<layout:collectionItem property="EHF310200T3_03" title="加量需求" style="text-align: center" />
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310200T3_02,EHF310200T3_03" 
						paramProperty="EHF310100T0_01,EHF310200T3_02,EHF310200T3_03"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310200T3" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
			<layout:row>				
				<layout:message styleClass="LARGE_MESSAGE" key="開始設定特殊口味明細" />
			</layout:row>

			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:select styleClass="DATAS" key="特殊口味" property="EHF310200T4_03" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310200T4_03" property="item_id" labelProperty="item_value" />
				</layout:select>
				
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310200T4');" ></layout:image>
				</layout:cell>
				
			</layout:grid>
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310200T4_List" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
									
					<layout:collectionItem property="EHF310200T4_03" title="特殊口味" style="text-align: center" />
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310200T4_02,EHF310200T4_03" 
						paramProperty="EHF310100T0_01,EHF310200T4_02,EHF310200T4_03"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310200T4" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
			<layout:row>				
				<layout:message styleClass="LARGE_MESSAGE" key="開始設定清淡去油明細" />
			</layout:row>

			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:select styleClass="DATAS" key="清淡去油" property="EHF310200T5_03" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310200T5_03" property="item_id" labelProperty="item_value" />
				</layout:select>
				
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310200T5');" ></layout:image>
				</layout:cell>
				
			</layout:grid>
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310200T5_List" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
									
					<layout:collectionItem property="EHF310200T5_03" title="清淡去油" style="text-align: center" />
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310200T5_02,EHF310200T5_03" 
						paramProperty="EHF310100T0_01,EHF310200T5_02,EHF310200T5_03"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310200T5" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
		</layout:tab>
		
		<layout:tab key="電訪紀錄" width="15%" >
		
			

			<layout:message styleClass="MESSAGE_ERROR" key="${DATE_MSG}" />
			<layout:grid cols="3" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:text styleClass="DATAS" key="產婦姓名" property="EHF310100T0_04"  styleId="EHF310100T0_04" size="10" mode="I,I,I" maxlength="10" tooltip="產婦姓名" name="Form1Datas"/>
				
				<layout:number styleClass="DATAS" key="電話" property="EHF310100T0_21"  styleId="EHF310100T0_21" size="10" mode="I,I,I" maxlength="10" tooltip="電話" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="電訪人員系統編號" property="EHF310300T0_03"  styleId="EHF310300T0_03" size="10" mode="H,H,H" maxlength="10" tooltip="電訪人員系統編號" name="Form1Datas"/>
				<layout:text styleClass="DATAS" key="電訪人員工號" property="EHF310300T0_03_SHOW"  styleId="EHF310300T0_03_SHOW" size="10" mode="H,H,H" maxlength="10" tooltip="電訪人員工號" name="Form1Datas"/>
				<layout:text styleClass="DATAS" key="電訪人員" property="EHF310300T0_03_TXT"  styleId="EHF310300T0_03_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="電訪人員" name="Form1Datas">
					<sp:lov 	form="EHF310100M0F" 
								id="EHF310300T0_03,EHF310300T0_03_SHOW,EHF310300T0_03_TXT" 
								lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
								table="EHF010100T6"
								leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
								fieldAlias="系統代碼,工號,姓名" 
								fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"	
								mode="E,E,F"				
								others=" AND EHF010100T6.EHF010100T6_02 IN ('DEP001COM42760782','DEP002COM42760782','DEP003COM42760782') AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
								
					/>					
				</layout:text>
				
			</layout:grid>
			
			<layout:grid cols="1" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="電訪日期" size="10"  name="Form1Datas" property="EHF310300T0_04" styleClass="DATAS"/>
			
				<layout:textarea styleClass="DATAS" tooltip="電訪內容" key="電訪內容" property="EHF310300T0_05" styleId="EHF310300T0_05" rows="3" cols="50" 
					 maxlength="50" mode="E,E,I" name="Form1Datas" />
				
				<layout:textarea styleClass="DATAS" key="備註" property="EHF310300T0_06"  styleId="EHF310300T0_06" size="80" mode="E,E,I" maxlength="50" tooltip="備註" name="Form1Datas" rows="1" cols="40"/>
				
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310300T0');" ></layout:image>
					<layout:image alt="儲存明細" mode="D,D,D" name="btnimage?text=button.saveDetailData&type=t" property="EHF310100M1" 
					              onclick="setAction(); return false;" ></layout:image>
					<%--<layout:image alt="產出規定電訪日期" mode="D,D,D" name="btnimage?text=button.autoAddCallDate&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310100T2');" ></layout:image>--%>
					<layout:image alt="列印電訪表" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310100T2123');" ></layout:image>
				</layout:cell>
				
			</layout:grid>
			
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310300T0_List" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
									
					<layout:collectionItem title="電訪日期" style="text-align: center" >
						<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I"  startYear="2015" endYear="2030"  key="日期" size="10"  name="Form1Datas" property="EHF310300T0_List[${index}].EHF310300T0_04" styleClass="DATAS" layout="false"  onchange="return chkchange('CALL');"/>
					</layout:collectionItem>
					
					<layout:collectionItem title="電訪人員" style="text-align: center" >
						<layout:text styleClass="DATAS" key="電訪人員" property="EHF310300T0_List[${index}].EHF310300T0_03_TXT"  styleId="EHF310300T0_List[${index}].EHF310300T0_03_TXT" size="10" mode="I,I,I" maxlength="10" tooltip="電訪人員" name="Form1Datas" layout="false" onchange="return chkchange('CALL');"/>
						<layout:text styleClass="DATAS" key="順序編號" property="EHF310300T0_List[${index}].EHF310300T0_02"  styleId="EHF310300T0_List[${index}].EHF310300T0_02" size="10" mode="H,H,H" maxlength="10" tooltip="順序編號" name="Form1Datas" layout="false" onchange="return chkchange('CALL');"/>
					</layout:collectionItem>
					
					<layout:collectionItem title="電訪內容" style="text-align: center" >
						<layout:textarea styleClass="DATAS" tooltip="電訪內容" key="電訪內容" property="EHF310300T0_List[${index}].EHF310300T0_05" styleId="EHF310300T0_List[${index}].EHF310300T0_05" rows="3" cols="50" 
					 				 maxlength="50" mode="E,E,I" name="Form1Datas" layout="false" onchange="return chkchange('CALL');"/>
					</layout:collectionItem>
					
					<layout:collectionItem title="備註" style="text-align: center" >
						<layout:textarea styleClass="DATAS" key="備註" property="EHF310300T0_List[${index}].EHF310300T0_06"  styleId="EHF310300T0_List[${index}].EHF310300T0_06" size="80" mode="E,E,I" rows="1" cols="40" maxlength="50" tooltip="備註" name="Form1Datas" layout="false" onchange="return chkchange('CALL');"/>
					</layout:collectionItem>
					
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310300T0_02,EHF310300T0_03" 
						paramProperty="EHF310100T0_01,EHF310300T0_02,EHF310300T0_03"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310300T0" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
		</layout:tab>
		<logic:notEqual name="admin" value="yes">
		<layout:tab key="付款資訊" width="15%" >
			<layout:grid cols="3" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:text styleClass="DATAS" key="產婦姓名" property="EHF310100T0_04"  styleId="EHF310100T0_04" size="10" mode="I,I,I" maxlength="10" tooltip="產婦姓名" name="Form1Datas"/>
				
				<layout:cell styleClass="DATAS" >	
					<layout:submit alt="試算" mode="D,D,D"  property="EHF310100M1" reqCode="startCount"  >試算</layout:submit>					
				</layout:cell>
				
				<layout:cell styleClass="DATAS" />
				
				<layout:text styleClass="DATAS" key="訂餐天數" property="EHF310400T0_02"  styleId="EHF310400T0_02" size="10" mode="I,I,I" maxlength="10" tooltip="訂餐天數" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="全部數量" property="EHF310400T2_03_All"  styleId="EHF310400T2_03_All" size="10" mode="I,I,I" maxlength="10" tooltip="全部數量" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="全部金額" property="EHF310400T2_03_All_Pay"  styleId="EHF310400T2_03_All_Pay" size="10" mode="I,I,I" maxlength="10" tooltip="全部金額" name="Form1Datas"/>
				
				<layout:cell styleClass="DATAS" />
				
				<layout:text styleClass="DATAS" key="A餐數量" property="EHF310400T2_03_A"  styleId="EHF310400T2_03_A" size="10" mode="I,I,I" maxlength="10" tooltip="A餐數量" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="A餐金額" property="EHF310400T2_03_A_Pay"  styleId="EHF310400T2_03_A_Pay" size="10" mode="I,I,I" maxlength="10" tooltip="A餐金額" name="Form1Datas"/>
				
				<layout:cell styleClass="DATAS" />
				
				<layout:text styleClass="DATAS" key="B餐數量" property="EHF310400T2_03_B"  styleId="EHF310400T2_03_B" size="10" mode="I,I,I" maxlength="10" tooltip="B餐數量" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="B餐金額" property="EHF310400T2_03_B_Pay"  styleId="EHF310400T2_03_B_Pay" size="10" mode="I,I,I" maxlength="10" tooltip="B餐金額" name="Form1Datas"/>
				
				<layout:cell styleClass="DATAS" />
				
				<layout:text styleClass="DATAS" key="C餐數量" property="EHF310400T2_03_C"  styleId="EHF310400T2_03_C" size="10" mode="I,I,I" maxlength="10" tooltip="C餐數量" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="C餐金額" property="EHF310400T2_03_C_Pay"  styleId="EHF310400T2_03_C_Pay" size="10" mode="I,I,I" maxlength="10" tooltip="C餐金額" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="定價" property="EHF310400T0_03"  styleId="EHF310400T0_03" size="10" mode="I,I,I" maxlength="10" tooltip="定價" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="折扣金額" property="EHF310400T0_04"  styleId="EHF310400T0_04" size="10" mode="E,E,I" maxlength="10" tooltip="折扣金額" name="Form1Datas" onchange="return chkchange('PAY');"/>
				
				<layout:cell styleClass="DATAS" >	
					<layout:submit alt="結算" mode="D,D,D"  property="EHF310100M1" reqCode="endCount" >結算</layout:submit>					
				</layout:cell>
				
				<layout:text styleClass="DATAS" key="實際金額" property="EHF310400T2_realPay"  styleId="EHF310400T2_realPay" size="10" mode="I,I,I" maxlength="10" tooltip="實際金額" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="已付金額" property="EHF310400T0_05"  styleId="EHF310400T0_05" size="10" mode="I,I,I" maxlength="10" tooltip="已付金額" name="Form1Datas"/>
				
				<layout:text styleClass="DATAS" key="未付金額" property="EHF310400T2_unPay"  styleId="EHF310400T2_unPay" size="10" mode="I,I,I" maxlength="10" tooltip="未付金額" name="Form1Datas"/>
			</layout:grid>
			
			<layout:row>				
				<layout:message styleClass="LARGE_MESSAGE" key="開始設定付款資訊明細" />
			</layout:row>
			
			<layout:grid cols="3" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="開單日期" size="10"  name="Form1Datas" property="EHF310400T1_03" styleClass="DATAS"/>
			
				<%--<layout:text styleClass="DATAS" key="開單人員" property="EHF310400T1_04"  styleId="EHF310400T1_04" size="10" mode="E,E,I" maxlength="10" tooltip="開單人員" name="Form1Datas"/>--%>
				
				<layout:text styleClass="DATAS" key="開單人員系統編號" property="EHF310400T1_04"  styleId="EHF310400T1_04" size="10" mode="H,H,H" maxlength="10" tooltip="開單人員系統編號" name="Form1Datas"/>
				<layout:text styleClass="DATAS" key="開單人員工號" property="EHF310400T1_04_SHOW"  styleId="EHF310400T1_04_SHOW" size="10" mode="H,H,H" maxlength="10" tooltip="開單人員工號" name="Form1Datas"/>
				<layout:text styleClass="DATAS" key="開單人員" property="EHF310400T1_04_TXT"  styleId="EHF310400T1_04_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="開單人員" name="Form1Datas">
					<sp:lov 	form="EHF310100M0F" 
								id="EHF310400T1_04,EHF310400T1_04_SHOW,EHF310400T1_04_TXT" 
								lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
								table="EHF010100T6"
								leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
								fieldAlias="系統代碼,工號,姓名" 
								fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"	
								mode="E,E,F"				
								others=" AND EHF010100T6.EHF010100T6_02 IN ('DEP001COM42760782','DEP002COM42760782','DEP003COM42760782') AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
								
					/>					
				</layout:text>
				
				<layout:select styleClass="DATAS" key="付款方式" property="EHF310400T1_06" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310400T1_06" property="item_id" labelProperty="item_value" />
				</layout:select>
				
				<layout:text styleClass="DATAS" key="總應付金額" property="EHF310400T2_realPay"  styleId="EHF310400T2_realPay" size="10" mode="I,I,I" maxlength="10" tooltip="總應付金額" name="Form1Datas"/>
				
				<%--<layout:text styleClass="DATAS" key="經手人員" property="EHF310400T1_05"  styleId="EHF310400T1_05" size="10" mode="E,E,I" maxlength="10" tooltip="經手人員" name="Form1Datas"/>--%>
				
				<layout:text styleClass="DATAS" key="經手人員系統編號" property="EHF310400T1_05"  styleId="EHF310400T1_05" size="10" mode="H,H,H" maxlength="10" tooltip="經手人員系統編號" name="Form1Datas"/>
				<layout:text styleClass="DATAS" key="經手人員工號" property="EHF310400T1_05_SHOW"  styleId="EHF310400T1_05_SHOW" size="10" mode="H,H,H" maxlength="10" tooltip="經手人員工號" name="Form1Datas"/>
				<layout:text styleClass="DATAS" key="經手人員" property="EHF310400T1_05_TXT"  styleId="EHF310400T1_05_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="經手人員" name="Form1Datas">
					<sp:lov 	form="EHF310100M0F" 
								id="EHF310400T1_05,EHF310400T1_05_SHOW,EHF310400T1_05_TXT" 
								lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
								table="EHF010100T6"
								leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
								fieldAlias="系統代碼,工號,姓名" 
								fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"	
								mode="E,E,F"				
								others=" AND EHF010100T6.EHF010100T6_02 IN ('DEP001COM42760782','DEP002COM42760782','DEP003COM42760782') AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
								
					/>					
				</layout:text>
				
				<layout:select styleClass="DATAS" key="付款類別" property="EHF310400T1_07" mode="E,E,I" name="Form1Datas">
						<layout:options collection="listEHF310400T1_07" property="item_id" labelProperty="item_value" />
				</layout:select>
				
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="預計收款日" size="10"  name="Form1Datas" property="EHF310400T1_08" styleClass="DATAS"/>
				
				<layout:number styleClass="DATAS" key="預計金額" property="EHF310400T1_09"  styleId="EHF310400T1_09" size="10" mode="E,E,I" maxlength="10" tooltip="預計金額" name="Form1Datas"/>
				
				<layout:cell styleClass="DATAS" />
				
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="實際收款日" size="10"  name="Form1Datas" property="EHF310400T1_10" styleClass="DATAS"/>
				
				<layout:number styleClass="DATAS" key="實際金額" property="EHF310400T1_11"  styleId="EHF310400T1_11" size="10" mode="E,E,I" maxlength="10" tooltip="實際金額" name="Form1Datas"/>
				
				<layout:cell styleClass="DATAS" />
				
			</layout:grid>
			
			<layout:grid cols="1" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
				
				<layout:text styleClass="DATAS" key="備註" property="EHF310400T1_12"  styleId="EHF310400T1_12" size="80" mode="E,E,I" maxlength="50" tooltip="備註" name="Form1Datas"/>
				
				<layout:cell styleClass="DATAS" >	
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310400T1');" ></layout:image>
					<layout:image alt="儲存明細" mode="D,D,D" name="btnimage?text=button.saveDetailData&type=t" property="EHF310100M1" 
							      onclick="setAction(); return false;" ></layout:image>
				</layout:cell>
				
			</layout:grid>
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310400T1_List" id="bean1" indexId="index" width="100%" 
							   styleClass="COLLECTION" styleClass2="COLLECTION_2" >
							   
					<logic:equal name="status" value="yes">
						<layout:collectionItem title="是否確認" style="text-align: center">	   
							<layout:text styleClass="DATAS" key="是否確認" property="EHF310400T1_List[${index}].EHF310400T1_13"  styleId="EHF310400T1_List[${index}].EHF310400T1_13" size="10" mode="H,H,H" maxlength="10" tooltip="實際金額" name="Form1Datas" layout="false"/>
						</layout:collectionItem>	
					</logic:equal>	
						
						
					<layout:collectionItem title="開單日期" style="text-align: center" >
						<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I"  startYear="2015" endYear="2030"  key="開單日期" size="10"  name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_03" styleClass="DATAS" layout="false"/>
					</layout:collectionItem>
					
					<layout:collectionItem title="開單人員" style="text-align: center" >
						<layout:text styleClass="DATAS" key="開單人員" property="EHF310400T1_List[${index}].EHF310400T1_04_TXT"  styleId="EHF310400T1_List[${index}].EHF310400T1_04_TXT" size="10" mode="I,I,I" maxlength="10" tooltip="開單人員" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
					</layout:collectionItem>
					
					
					<layout:collectionItem title="付款方式" style="text-align: center" >
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:select key="付款方式" name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_06" styleId="EHF310400T1_List[${index}].EHF310400T1_06" styleClass="DATAS" mode="I,I,I" maxlength="3" onchange="return chkchange('PAY_DETAIL');" layout="false" >
								<layout:options collection="listEHF310400T1_06" property="item_id" labelProperty="item_value" />
							</layout:select>
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:select key="付款方式" name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_06" styleId="EHF310400T1_List[${index}].EHF310400T1_06" styleClass="DATAS" mode="E,E,I" maxlength="3" onchange="return chkchange('PAY_DETAIL');" layout="false" >
								<layout:options collection="listEHF310400T1_06" property="item_id" labelProperty="item_value" />
							</layout:select>
						</logic:notEqual>
					</layout:collectionItem>
										
					<layout:collectionItem title="付款類別" style="text-align: center" >
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:select key="付款類別" name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_07" styleId="EHF310400T1_List[${index}].EHF310400T1_07" styleClass="DATAS" mode="I,I,I" maxlength="3" onchange="return chkchange('PAY_DETAIL');" layout="false" >
								<layout:options collection="listEHF310400T1_07" property="item_id" labelProperty="item_value" />
							</layout:select>
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:select key="付款類別" name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_07" styleId="EHF310400T1_List[${index}].EHF310400T1_07" styleClass="DATAS" mode="E,E,I" maxlength="3" onchange="return chkchange('PAY_DETAIL');" layout="false" >
								<layout:options collection="listEHF310400T1_07" property="item_id" labelProperty="item_value" />
							</layout:select>
						</logic:notEqual>
					</layout:collectionItem>
					
					<layout:collectionItem title="預計日期" style="text-align: center" >
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I"  startYear="2015" endYear="2030"  key="預計日期" size="10"  name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_08" styleClass="DATAS" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="預計日期" size="10"  name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_08" styleClass="DATAS" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:notEqual>
					</layout:collectionItem>
										
					<layout:collectionItem title="預計金額" style="text-align: center" >
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:number styleClass="DATAS" key="預計金額" property="EHF310400T1_List[${index}].EHF310400T1_09"  styleId="EHF310400T1_List[${index}].EHF310400T1_09" size="10" mode="I,I,I" maxlength="10" tooltip="預計金額" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:number styleClass="DATAS" key="預計金額" property="EHF310400T1_List[${index}].EHF310400T1_09"  styleId="EHF310400T1_List[${index}].EHF310400T1_09" size="10" mode="E,E,I" maxlength="10" tooltip="預計金額" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:notEqual>
					</layout:collectionItem>
					
					<layout:collectionItem title="實際日期" style="text-align: center" >
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="I,I,I"  startYear="2015" endYear="2030"  key="實際日期" size="10"  name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_10" styleClass="DATAS" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="實際日期" size="10"  name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_10" styleClass="DATAS" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:notEqual>
					</layout:collectionItem>
					
					<layout:collectionItem property="EHF310400T1_11" title="實際金額" style="text-align: center" >
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:number styleClass="DATAS" key="實際金額" property="EHF310400T1_List[${index}].EHF310400T1_11"  styleId="EHF310400T1_List[${index}].EHF310400T1_11" size="10" mode="I,I,I" maxlength="10" tooltip="實際金額" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:number styleClass="DATAS" key="實際金額" property="EHF310400T1_List[${index}].EHF310400T1_11"  styleId="EHF310400T1_List[${index}].EHF310400T1_11" size="10" mode="E,E,I" maxlength="10" tooltip="實際金額" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:notEqual>
					</layout:collectionItem>
					
					<layout:collectionItem title="經手人" style="text-align: center" >
					
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:text styleClass="DATAS" key="經手人" property="EHF310400T1_List[${index}].EHF310400T1_05"  styleId="EHF310400T1_List[${index}].EHF310400T1_05" size="10" mode="I,I,I" maxlength="10" tooltip="經手人" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:text styleClass="DATAS" key="經手人" property="EHF310400T1_List[${index}].EHF310400T1_05"  styleId="EHF310400T1_List[${index}].EHF310400T1_05" size="10" mode="E,E,I" maxlength="10" tooltip="經手人" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:notEqual>
						
						
						<layout:text styleClass="DATAS" key="順序編號" property="EHF310400T1_List[${index}].EHF310400T1_02"  styleId="EHF310400T1_List[${index}].EHF310400T1_02" size="10" mode="H,H,H" maxlength="10" tooltip="順序編號" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
					</layout:collectionItem>
					
					<layout:collectionItem title="備註" style="text-align: center" >
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:text styleClass="DATAS" key="備註" property="EHF310400T1_List[${index}].EHF310400T1_12"  styleId="EHF310400T1_List[${index}].EHF310400T1_12" size="80" mode="I,I,I" maxlength="50" tooltip="備註" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							<layout:text styleClass="DATAS" key="備註" property="EHF310400T1_List[${index}].EHF310400T1_12"  styleId="EHF310400T1_List[${index}].EHF310400T1_12" size="80" mode="E,E,I" maxlength="50" tooltip="備註" name="Form1Datas" layout="false" onchange="return chkchange('PAY_DETAIL');"/>
						</logic:notEqual>
					</layout:collectionItem>
					<logic:equal name="boss" value="yes">
						<layout:collectionItem title="確認" style="TEXT-ALIGN: CENTER" 
							paramId="EHF310100T0_01,EHF310400T1_02,EHF310400T1_13,EHF310400T1_14" 
							paramProperty="EHF310100T0_01,EHF310400T1_02,EHF310400T1_13,EHF310400T1_14"
							url="EHF310100M1.do?reqCode=confirmRecovery" 
							onclick="return confirmShowEMSWait('您確定要 確認/回復 此筆明細嗎?');" >
							
							<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
								回復
							</logic:equal>
							<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
								確認
							</logic:notEqual>
						</layout:collectionItem>
					</logic:equal>
					
						
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310400T1_02,EHF310400T1_13,EHF310400T1_14" 
						paramProperty="EHF310100T0_01,EHF310400T1_02,EHF310400T1_13,EHF310400T1_14"
						url="EHF310100M1.do?reqCode=remove" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >
						
						<logic:equal name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							
						</logic:equal>
						<logic:notEqual name="Form1Datas" property="EHF310400T1_List[${index}].EHF310400T1_13" value="1">
							刪除
						</logic:notEqual>
						
					
					</layout:collectionItem>	
					
					
					
					
					
			</layout:collection>
			
		</layout:tab>
		</logic:notEqual>
		<layout:tab key="贈品資訊" width="15%" >
			
<%--			<layout:grid cols="3" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >--%>
			
<%--				<layout:text styleClass="DATAS" key="產婦姓名" property="EHF310100T0_04"  styleId="EHF310100T0_04" size="10" mode="I,I,I" maxlength="10" tooltip="產婦姓名" name="Form1Datas" onchange="return chkchange('GIFT');"/>--%>
				
				<%--<layout:text styleClass="DATAS" key="接待人員" property="EHF310500T0_02"  styleId="EHF310500T0_02" size="10" mode="E,E,I" maxlength="10" tooltip="接待人員" name="Form1Datas" onchange="return chkchange('GIFT');"/>--%>
				
<%--				<layout:text styleClass="DATAS" key="接待人員系統編號" property="EHF310500T0_02"  styleId="EHF310500T0_02" size="10" mode="H,H,H" maxlength="10" tooltip="接待人員系統編號" name="Form1Datas"  onchange="return chkchange('GIFT');"/>--%>
<%--				<layout:text styleClass="DATAS" key="接待人員工號" property="EHF310500T0_02_SHOW"  styleId="EHF310500T0_02_SHOW" size="10" mode="H,H,H" maxlength="10" tooltip="接待人員工號" name="Form1Datas"  onchange="return chkchange('GIFT');"/>--%>
<%--				<layout:text styleClass="DATAS" key="接待人員" property="EHF310500T0_02_TXT"  styleId="EHF310500T0_02_TXT" size="10" mode="R,R,I" maxlength="10" tooltip="接待人員" name="Form1Datas"  onchange="return chkchange('GIFT');">--%>
<%--					<sp:lov 	form="EHF310100M0F" --%>
<%--								id="EHF310500T0_02,EHF310500T0_02_SHOW,EHF310500T0_02_TXT" --%>
<%--								lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" --%>
<%--								table="EHF010100T6"--%>
<%--								leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "--%>
<%--								fieldAlias="系統代碼,工號,姓名" --%>
<%--								fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"	--%>
<%--								mode="E,E,F"				--%>
<%--								others=" AND EHF010100T6.EHF010100T6_02 IN ('DEP001COM42760782','DEP002COM42760782','DEP003COM42760782') AND EHF010100T6.HR_CompanySysNo = '${compid}' " --%>
<%--								--%>
<%--					/>					--%>
<%--				</layout:text>--%>
				
<%--				<layout:radios styleClass="DATAS" key="是否有贈品" cols="2" property="EHF310500T0_03" mode="E,E,I" name="Form1Datas" onchange="return chkchange('GIFT');">--%>
<%--						<layout:options collection="listEHF310500T0_03" property="item_id" labelProperty="item_value" />--%>
<%--				</layout:radios>--%>
				
<%--				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="領取日期" size="10"  name="Form1Datas" property="EHF310500T0_04" styleClass="DATAS" onchange="return chkchange('GIFT');"/>--%>
				
<%--				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="開單日期" size="10"  name="Form1Datas" property="EHF310500T0_05" styleClass="DATAS" onchange="return chkchange('GIFT');"/>--%>
				
<%--				<layout:cell styleClass="DATAS"/>--%>
				
				<%--<layout:text styleClass="DATAS" key="贈品類別" property="EHF310500T0_06"  styleId="EHF310500T0_06" size="10" mode="E,E,I" maxlength="10" tooltip="贈品類別" name="Form1Datas" onchange="return chkchange('GIFT');"/>--%>
				
<%--				<layout:select styleClass="DATAS" key="贈品類別" property="EHF310500T0_06" mode="E,E,I" name="Form1Datas" onchange="return chkchange('GIFT');">--%>
<%--						<layout:options collection="listEHF310500T0_06" property="item_id" labelProperty="item_value" />--%>
<%--				</layout:select>--%>
				
<%--				<layout:number styleClass="DATAS" key="單位份數" property="EHF310500T0_07"  styleId="EHF310500T0_07" size="5" mode="E,E,I" maxlength="2" tooltip="單位份數" name="Form1Datas" onchange="return chkchange('GIFT');"/>--%>
				
<%--				<layout:cell styleClass="DATAS"/>--%>
<%--			</layout:grid>--%>
			
<%--			<layout:grid cols="1" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >--%>
<%--				<layout:text styleClass="DATAS" key="備註" property="EHF310500T0_08"  styleId="EHF310500T0_08" size="80" mode="E,E,I" maxlength="50" tooltip="備註" name="Form1Datas" onchange="return chkchange('GIFT');"/>--%>
<%--			</layout:grid>--%>
			
			<layout:grid cols="1" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
				<layout:text styleClass="DATAS" key="產婦姓名" property="EHF310100T0_04"  styleId="EHF310100T0_04" size="10" mode="I,I,I" maxlength="10" tooltip="產婦姓名" name="Form1Datas"/>
			</layout:grid>
			
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="領取日期" size="10"  name="Form1Datas" property="EHF310500T0_04" styleClass="DATAS"/>
				
				<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2015" endYear="2030"  key="開單日期" size="10"  name="Form1Datas" property="EHF310500T0_05" styleClass="DATAS"/>
				
				<layout:select styleClass="DATAS" key="贈品類別" property="EHF310500T0_06" mode="E,E,I" name="Form1Datas" onchange="return chkchange('GIFT');">
						<layout:options collection="listEHF310500T0_06" property="item_id" labelProperty="item_value" />
				</layout:select>
				
				<layout:cell styleClass="DATAS"/>
				
			</layout:grid>
			
			<layout:grid cols="1" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
				<layout:text styleClass="DATAS" key="備註" property="EHF310500T0_08"  styleId="EHF310500T0_08" size="80" mode="E,E,I" maxlength="50" tooltip="備註" name="Form1Datas" onchange="return chkchange('GIFT');"/>
			</layout:grid>
			
			<layout:cell styleClass="DATAS" >
			<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF310100M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF310500T0');" ></layout:image>
			</layout:cell>
			
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF310500T0_List" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					
					<layout:collectionItem property="EHF310500T0_05" title="開單日期" style="text-align: center" />
					<layout:collectionItem property="EHF310500T0_04" title="領取日期" style="text-align: center" />				
					<layout:collectionItem property="EHF310500T0_06_TXT" title="贈品" style="text-align: center" />
					<layout:collectionItem property="EHF310500T0_08" title="備註" style="text-align: center" />
					
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="EHF310100T0_01,EHF310500T0_02" 
						paramProperty="EHF310100T0_01,EHF310500T0_02"
						url="EHF310100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF310500T0" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
		</layout:tab>
		</layout:notMode>
		
	</layout:tabs>
		
</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />