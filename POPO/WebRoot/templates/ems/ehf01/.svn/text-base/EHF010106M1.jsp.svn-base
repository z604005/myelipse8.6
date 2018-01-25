<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.hr.forms.EHF010106M0F" %>
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
window.onload = function (evt){
	chkEHF030200T0_05();	
}

function fbutton(reqCode) {
	EHF010106M0F.elements['reqCode'].value=reqCode;
	EHF010106M0F.submit();
}

function chkchange(obj){
	
	if(obj=="ATT"){		
		document.getElementById("EHF010106T0_TYPE").value = "ATT";
	}else if(obj=="EMP"){		
		document.getElementById("EHF010106T0_TYPE").value = "EMP";
	}else if(obj=="EXP"){
		document.getElementById("EHF010106T0_TYPE").value = "EXP";
	}else if(obj=="SAL"){
		document.getElementById("EHF010106T0_TYPE").value = "SAL";
	}else if(obj=="INS"){
		document.getElementById("EHF010106T0_TYPE").value = "INS";
	}
	
	if(document.getElementById("EHF030200T0_05").value == "02"){
		document.getElementById("flag_EHF030200T0_06").style.display = "";
	}else{
		document.getElementById("flag_EHF030200T0_06").style.display = "none";
	}
		
}

function setAction(){
	
	var saveType = document.getElementById("EHF010106T0_TYPE").value;
	
	if(saveType=="ATT"){	//員工考勤
		return fbutton('saveDataATT');
	}else if(saveType=="EMP"){	//員工基本資料
		return fbutton('saveDataEMP');
	}else if(saveType=="EXP"){	//員工履經歷
		return fbutton('saveDataEXP');
	}else if(saveType=="SAL"){	//員工薪資
		return fbutton('saveDataSAL');
	}else if(saveType=="INS"){	//員工保險
		return fbutton('saveDataINS');
	}else{
		return fbutton('saveDataEMP');
	}
}

function chkEHF030200T0_05(){
	//給薪方式
		if(document.getElementById("EHF030200T0_05").value == "02"){
			document.getElementById("flag_EHF030200T0_06").style.display = "";
		}else{
			document.getElementById("flag_EHF030200T0_06").style.display = "none";
		}
}

//清空勞保等級欄位  BY賴泓錡 20140120
function clear_EHF030300T0_05_HIDE(){
	document.getElementById("EHF030300T0_05_HIDE").value = "";
	return true;
}

//清空健保等級欄位 BY賴泓錡 20140120
function clear_EHF030300T0_07_HIDE(){
	document.getElementById("EHF030300T0_07_HIDE").value = "";
	return true;
}

function chkFile(){
	if(document.getElementById("UPLOADFILE").value =="")	{
		alert("請選擇檔案!");	
		return false;
	}else{
		return true;
	}
}
</script>

<layout:form action="EHF010106M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" acceptCharset="UTF-8" key="員工基本資料">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF010106M1" ></layout:image>
<%--			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF010106M1" ></layout:image>--%>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" property="EHF010106M1" onclick="setAction(); return false;" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF010106M1" ></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF010106M1" ></layout:image>
    </layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<%
			String strTmp = "";			
			
			EHF010106M0F FORM=(EHF010106M0F)request.getAttribute("Form1Datas");
			
			strTmp = FORM.getEHF010106T0_23().replace("\\","//");	
			
	%>
	
	<layout:tabs styleClass="DATAS" width="100%" selectedTabKeyName="EHF010106M1_Tabs_01">
	
				<layout:text styleClass="DATAS" property="HR_EmployeeSysNo" styleId="HR_EmployeeSysNo" mode="H,H,H" name="Form1Datas" />
				<input type="hidden" id="EHF010106T0_TYPE" value="" />
	
		<layout:tab key="員工基本資料" width="15%" >
			
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
					<layout:grid cols="1" space="false" borderSpacing="0" align="left" width="100%" >
	<%--				<layout:text styleClass="DATAS" key="登入帳號" property="Login_No" styleId="Login_No" size="16" mode="I,I,I" maxlength="11" name="Form1Datas" />--%>
					<layout:text styleClass="DATAS" property="HR_EmployeeSysNo" styleId="HR_EmployeeSysNo" mode="H,H,H" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="姓名(中)" property="EHF010106T0_04" styleId="EHF010106T0_04" size="16" mode="E,E,I" maxlength="10" name="Form1Datas" onchange="return chkchange('EMP');" isRequired="true"/>
					<layout:text styleClass="DATAS" key="員工工號" property="HR_EmployeeNo" styleId="HR_EmployeeNo" size="16" mode="E,E,I" maxlength="11" name="Form1Datas" isRequired="true"/>
					
					<%--所屬部門內碼為資料庫的PK					--%>
					<layout:text styleClass="DATAS" key="所屬部門內碼" property="HR_DepartmentSysNo" styleId="HR_DepartmentSysNo" size="16" mode="E,E,I" maxlength="11" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="所屬部門" property="HR_DepartmentNo" styleId="HR_DepartmentNo" size="16" mode="E,E,I" maxlength="20" name="Form1Datas" >				
						<sp:lov 	form="EHF010106M0F" 
									id="HR_DepartmentSysNo,HR_DepartmentNo,HR_DepartmentName," 
									lovField="HR_DepartmentSysNo,HR_DepartmentNo,HR_DepartmentName,HR_DepartmentLevel" 
									table="EHF010108T0"
									fieldAlias="系統代號,部門代號,部門名稱,層級" 
									fieldName="HR_DepartmentSysNo,HR_DepartmentNo,HR_DepartmentName,HR_DepartmentLevel"									
									others=" AND HR_CompanySysNo = '${compid}' "
									changescript="chkchange('EMP')"
						/>
						<layout:text layout="false" property="HR_DepartmentName" styleId="HR_DepartmentName" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
					</layout:text>
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="部門歸屬區間" size="10"  name="Form1Datas" property="EHF010106T6_01" styleClass="DATAS"  >
						&nbsp;~&nbsp;
						<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  key="" name="Form1Datas" property="EHF010106T6_02" styleClass="DATAS"  />
					</layout:date>
					
					<%--職務名稱內碼為資料庫的PK					--%>
					<layout:text styleClass="DATAS" key="職務名稱內碼" property="HR_JobNameSysNo" styleId="HR_EmployeeNo" size="16" mode="E,E,I" maxlength="11" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="職務名稱" property="HR_JobNameNo" styleId="HR_JobNameSysNo" size="16" mode="R,R,R" maxlength="20" name="Form1Datas" >
						<sp:lov 	form="EHF010106M0F" 
									id="HR_JobNameSysNo,HR_JobName,HR_JobNameNo" 
									lovField="HR_JobNameSysNo,HR_JobName,HR_JobNameNo,EHF010109T0_01" 
									table="EHF010109T0"
									fieldAlias="系統代號,職務名稱,職務代碼,使用情況" 
									fieldName="HR_JobNameSysNo,HR_JobName,HR_JobNameNo,EHF010109T0_01"									
									others=" AND HR_CompanySysNo = '${compid}' AND EHF010109T0_01 = '1'"
									changescript="chkchange('EMP')"
						/>
						<layout:text layout="false" property="HR_JobName" styleId="HR_JobName" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
					</layout:text>
					<layout:select styleClass="DATAS" key="員工類別" property="EHF010106T0_06" styleId="EHF010106T0_06" mode="E,E,I" name="Form1Datas" isRequired="true">
						<layout:options collection="listEHF010106T0_06" property="item_id" labelProperty="item_value" />
					</layout:select>
					</layout:grid>
					
					<layout:text layout="false" property="EHF010106T0_23" styleId="EHF010106T0_23" size="12" mode="H,H,I" maxlength="16" name="Form1Datas" />
					
					
					<logic:notEqual name="button_init" value="init">
						<layout:cell styleClass="DATAS" >
							<layout:img styleClass="DATAS" alt="點選上傳圖片" styleId="data" height="200"  align="middle" srcName="<%=strTmp%>" onclick="openStrutsLayoutPopup('fileupload'); return false;" />		
						</layout:cell>
					</logic:notEqual>	
				</layout:grid>
				<layout:grid cols="1" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
					<layout:radios styleClass="DATAS" key="職務狀況" property="EHF010106T1_01" mode="E,E,I" name="Form1Datas" cols="4" onchange="return chkchange('EMP');" >
						<layout:line space="false">
							<layout:line space="false">
								<layout:option value="1" key="現職"/>
								<layout:cell>
									<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  
												 key="" name="Form1Datas" property="EHF010106T1_0201" styleClass="DATAS" onchange="return chkchange('EMP');"/>
								</layout:cell>
							</layout:line>
							<layout:line space="false">
								<layout:option value="2" key="離職" />
									<layout:cell>
										<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  
													 key="" name="Form1Datas" property="EHF010106T1_0202" styleClass="DATAS" onchange="return chkchange('EMP');"/>
									</layout:cell>
							</layout:line>
							<layout:line space="false">
								<layout:option value="3" key="復職" />
									<layout:cell>
										<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  
													 key="" name="Form1Datas" property="EHF010106T1_0203" styleClass="DATAS" onchange="return chkchange('EMP');"/>
									</layout:cell>
							</layout:line>
							<layout:line space="false">
								<layout:option value="4" key="留職停薪" />
									<layout:cell>
										<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  
													 key="" name="Form1Datas" property="EHF010106T1_0204" styleClass="DATAS" onchange="return chkchange('EMP');"/>
									</layout:cell>
							</layout:line>
						</layout:line>
					</layout:radios>
				</layout:grid>
				<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
					<layout:text styleClass="DATAS" key="身分證字號" property="EHF010106T0_01" styleId="EHF010106T0_01" size="20" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:text styleClass="DATAS" key="居留證號" property="EHF010106T0_02" styleId="EHF010106T0_02" size="20" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:text styleClass="DATAS" key="護照號碼" property="EHF010106T0_03" styleId="EHF010106T0_03" size="20" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:text styleClass="DATAS" key="姓名(英)" property="EHF010106T0_05" styleId="EHF010106T0_05" size="20" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:text styleClass="DATAS" key="籍貫" property="EHF010106T0_09" styleId="EHF010106T0_09" size="20" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:radios styleClass="DATAS" key="性別" property="EHF010106T0_07" mode="E,E,I" name="Form1Datas" cols="2" onchange="return chkchange('EMP');" >
						<layout:option value="F" key="女" />
						<layout:option value="M" key="男" />
					</layout:radios>
					<layout:radios styleClass="DATAS" key="婚姻狀況" property="EHF010106T0_08" mode="E,E,I" name="Form1Datas" cols="4" onchange="return chkchange('EMP');" >
						<layout:option value="1" key="未婚" />
						<layout:option value="2" key="已婚" />
						<layout:option value="3" key="離婚" />
						<layout:option value="4" key="鰥寡" />
					</layout:radios>
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="1900" endYear="2050"  key="生日" size="10"  name="Form1Datas" property="EHF010106T0_10" styleClass="DATAS" onchange="return chkchange('EMP');"/>
					<layout:text styleClass="DATAS" key="緊急聯絡人" property="EHF010106T0_16" styleId="EHF010106T0_16" size="10" mode="E,E,I" maxlength="10" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:text styleClass="DATAS" key="緊急聯絡人關係" property="EHF010106T0_17" styleId="EHF010106T0_17" size="5" mode="E,E,I" maxlength="5" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:number styleClass="DATAS" key="緊急連絡人電話" property="EHF010106T0_1801" styleId="EHF010106T0_1801" size="5" mode="E,E,I" maxlength="5" name="Form1Datas" onchange="return chkchange('EMP');">
					-
					<layout:number styleClass="DATAS" key="" property="EHF010106T0_1802" styleId="EHF010106T0_1802" size="10" mode="E,E,I" maxlength="10" name="Form1Datas" layout="false" onchange="return chkchange('EMP');"/>
					</layout:number>
					<layout:number styleClass="DATAS" key="手機號碼" property="EHF010106T0_15" styleId="EHF010106T0_15" size="15" mode="E,E,I" maxlength="15" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:number styleClass="DATAS" key="連絡電話" property="EHF010106T0_1301" styleId="EHF010106T0_1301" size="5" mode="E,E,I" maxlength="5" name="Form1Datas" onchange="return chkchange('EMP');">
					-
					<layout:number styleClass="DATAS" key="" property="EHF010106T0_1302" styleId="EHF010106T0_1302" size="10" mode="E,E,I" maxlength="10" name="Form1Datas" layout="false" onchange="return chkchange('EMP');"/>
					</layout:number>
					<layout:number styleClass="DATAS" key="戶籍電話" property="EHF010106T0_1101" styleId="EHF010106T0_1101" size="5" mode="E,E,I" maxlength="5" name="Form1Datas" onchange="return chkchange('EMP');">
					-
					<layout:number styleClass="DATAS" key="" property="EHF010106T0_1102" styleId="EHF010106T0_1102" size="10" mode="E,E,I" maxlength="10" name="Form1Datas" layout="false" onchange="return chkchange('EMP');"/>
					</layout:number>
				</layout:grid>
				<layout:grid cols="1" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
					<layout:text styleClass="DATAS" key="聯絡地址" property="EHF010106T0_14" styleId="EHF010106T0_14" size="80" mode="E,E,I" maxlength="50" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:text styleClass="DATAS" key="戶籍地址" property="EHF010106T0_12" styleId="EHF010106T0_12" size="80" mode="E,E,I" maxlength="50" name="Form1Datas" onchange="return chkchange('EMP');" />
					<layout:field styleClass="DATAS" property="EHF010106T0_24" key="身份證明圖檔" name="Form1Datas" access="READONLY">
						<logic:notEqual value="yes" name="Attached_File1">
		 					<layout:button value="上傳檔案" onclick="return setCondition('condition01','fileupload1');" mode="H,D,H" />
						</logic:notEqual>
						<logic:equal name="Attached_File1" value="yes">
							<layout:link layout="true" styleClass="DATAS" action="" onclick="fbutton('getAttachedFile','EHF010106T0_24'); return false;">
								<layout:message styleClass="DATAS" key="${Attached_File_Name1}" />
							</layout:link>
							<layout:image styleClass="DATAS" alt="刪除附加檔案" mode="D,D,H" onclick="fbutton('delAttachedFile','EHF010106T0_24'); return false;"  src="config/deleteicon.jpg" confirm="您確定要刪除附加檔案嗎?"/>
						</logic:equal>
						請上傳身分證明檔
					</layout:field>
				</layout:grid>
				<layout:grid cols="5" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
					<layout:text styleClass="DATAS" key="家庭成員姓名" property="EHF010106T5_02" styleId="EHF010106T5_02" size="10" mode="E,E,I" maxlength="10" name="Form1Datas"/>
					<layout:text styleClass="DATAS" key="稱謂" property="EHF010106T5_05" styleId="EHF010106T5_05" size="10" mode="E,E,I" maxlength="10" name="Form1Datas"/>
					<layout:number styleClass="DATAS" key="年齡" property="EHF010106T5_03" styleId="EHF010106T5_03" size="5" mode="E,E,I" maxlength="5" name="Form1Datas"/>
					<layout:text styleClass="DATAS" key="職業" property="EHF010106T5_04" styleId="EHF010106T5_04" size="10" mode="E,E,I" maxlength="10" name="Form1Datas"/>
					<layout:cell>
					<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF010106M1" 
						reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF010106T5');" ></layout:image>
					</layout:cell>
				</layout:grid>
				<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF010106T5_LIST" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="HR_EmployeeSysNo,EHF010106T5_01,HR_CompanySysNo" 
						paramProperty="HR_EmployeeSysNo,EHF010106T5_01,HR_CompanySysNo"
						url="EHF010106M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF010106T5" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆感應卡明細嗎?');" >刪除</layout:collectionItem>					
					<layout:collectionItem property="EHF010106T5_02" title="家庭成員姓名" style="text-align: center" />
					<layout:collectionItem property="EHF010106T5_05" title="稱謂" style="text-align: center" />
					<layout:collectionItem property="EHF010106T5_03" title="年齡" style="text-align: center" />
					<layout:collectionItem property="EHF010106T5_04" title="職業" style="text-align: center" />
				</layout:collection>
<%--				<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">--%>
<%--					<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動日期" styleClass="LOGDATA" property="HR_LastUpdateDate" name="Form1Datas" mode= "I,I,I" />--%>
<%--				</layout:grid>--%>
		
		</layout:tab>
		
<%--		<layout:notMode value="create">--%>
			
			<layout:tab key="員工履經歷資料" width="15%" >
			<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
											
					<layout:text styleClass="DATAS" key="員工姓名" property="EHF010106T0_04" styleId="EHF010106T0_04" size="16" mode="I,I,I" maxlength="10" name="Form1Datas" />
					<layout:space styleClass="DATAS"></layout:space>
					<layout:select styleClass="DATAS" key="學歷" property="EHF010106T3_02" mode="E,E,I" name="Form1Datas" >
						<layout:option value="1" key="國小" />
						<layout:option value="2" key="國中" />
						<layout:option value="3" key="高中職" />
						<layout:option value="4" key="專科" />
						<layout:option value="5" key="大學" />
						<layout:option value="6" key="碩士" />
						<layout:option value="7" key="博士" />
					</layout:select>
					<layout:text styleClass="DATAS" key="學校名稱" property="EHF010106T3_03" styleId="EHF010106T3_03" size="16" mode="E,E,I" maxlength="10" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="學校科系" property="EHF010106T3_04" styleId="EHF010106T3_04" size="16" mode="E,E,I" maxlength="10" name="Form1Datas" />
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="在校期間" size="10"  name="Form1Datas" property="EHF010106T3_05" styleClass="DATAS"  >
						~
						<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  key="" name="Form1Datas" property="EHF010106T3_06" styleClass="DATAS"  />
					</layout:date>
					<layout:radios styleClass="DATAS" key="就學狀況" property="EHF010106T3_07" mode="E,E,I" name="Form1Datas" cols="3" >
						<layout:option value="0" key="畢業" />
						<layout:option value="1" key="肄業" />
						<layout:option value="2" key="在學" />
					</layout:radios>
					<layout:cell styleClass="DATAS">
						<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF010106M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF010106T3');" ></layout:image>
					</layout:cell>
					</layout:grid>
					<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF010106T3_LIST" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="HR_EmployeeSysNo,EHF010106T3_01,HR_CompanySysNo" 
						paramProperty="HR_EmployeeSysNo,EHF010106T3_01,HR_CompanySysNo"
						url="EHF010106M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF010106T3" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆感應卡明細嗎?');" >刪除</layout:collectionItem>					
					<layout:collectionItem property="EHF010106T3_02" title="學歷" style="text-align: center" />
					<layout:collectionItem property="EHF010106T3_03" title="學校名稱" style="text-align: center" />
					<layout:collectionItem property="EHF010106T3_04" title="學校科系" style="text-align: center" />
					<layout:collectionItem property="EHF010106T3_05" title="在校期間" style="text-align: center" />
					<layout:collectionItem property="EHF010106T3_07" title="就學狀況" style="text-align: center" />
				</layout:collection>
				<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
											
					<layout:text styleClass="DATAS" key="公司名稱" property="EHF010106T2_02" styleId="EHF010106T2_02" size="26" mode="E,E,I" maxlength="20" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="產業別" property="EHF010106T2_03" styleId="EHF010106T2_03" size="26" mode="E,E,I" maxlength="20" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="公司部門" property="EHF010106T2_04" styleId="EHF010106T2_04" size="26" mode="E,E,I" maxlength="20" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="公司職稱" property="EHF010106T2_05" styleId="EHF010106T2_05" size="26" mode="E,E,I" maxlength="20" name="Form1Datas" />
					</layout:grid>
				<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
					<layout:text styleClass="DATAS" key="工作內容" property="EHF010106T2_06" styleId="EHF010106T2_06" size="60" mode="E,E,I" maxlength="50" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="離職原因" property="EHF010106T2_09" styleId="EHF010106T2_09" size="60" mode="E,E,I" maxlength="50" name="Form1Datas" />
				</layout:grid>
				<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="工作期間" size="10"  name="Form1Datas" property="EHF010106T2_07" styleClass="DATAS"  >
						~
						<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  key="" name="Form1Datas" property="EHF010106T2_08" styleClass="DATAS"  />
					</layout:date>
					<layout:cell styleClass="DATAS">
						<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF010106M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF010106T2');"></layout:image>
					</layout:cell>
				</layout:grid>
				<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF010106T2_LIST" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="HR_EmployeeSysNo,EHF010106T2_01,HR_CompanySysNo" 
						paramProperty="HR_EmployeeSysNo,EHF010106T2_01,HR_CompanySysNo"
						url="EHF010106M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF010106T2" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆感應卡明細嗎?');" >刪除</layout:collectionItem>					
					<layout:collectionItem property="EHF010106T2_02" title="公司名稱" style="text-align: center" />
					<layout:collectionItem property="EHF010106T2_05" title="公司職務" style="text-align: center" />
					<layout:collectionItem property="EHF010106T2_06" title="工作內容" style="text-align: center" />
					<layout:collectionItem property="EHF010106T2_07" title="工作期間" style="text-align: center" />
					<layout:collectionItem property="EHF010106T2_09" title="離職原因" style="text-align: center" />
				</layout:collection>
				<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
					<layout:text styleClass="DATAS" key="證照名稱" property="EHF010106T4_02" styleId="EHF010106T4_02" size="26" mode="E,E,I" maxlength="20" name="Form1Datas" />
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="使用期限" size="10"  name="Form1Datas" property="EHF010106T4_04" styleClass="DATAS"  >
						~
						<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="2010" endYear="2025"  key="" name="Form1Datas" property="EHF010106T4_05" styleClass="DATAS"  />
					</layout:date>
					<layout:text styleClass="DATAS" key="上傳檔案名稱" property="EHF010106T4_03" styleId="EHF010106T4_03" size="26" mode="E,E,I" maxlength="20" name="Form1Datas" />
					<layout:space styleClass="DATAS"/>
				</layout:grid>
				<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
					<layout:field styleClass="DATAS" property="EHF010106T4_06" key="證照檔案上傳" name="Form1Datas" access="READONLY">
						<logic:notEqual value="yes" name="Attached_File1">
		 					<layout:button value="上傳檔案" onclick="return setCondition('condition01','fileupload1');" mode="H,D,H" />
						</logic:notEqual>
						<logic:equal name="Attached_File1" value="yes">
							<layout:link layout="true" styleClass="DATAS" action="" onclick="fbutton('getAttachedFile','EHF010106T0_24'); return false;">
								<layout:message styleClass="DATAS" key="${Attached_File_Name1}" />
							</layout:link>
							<layout:image styleClass="DATAS" alt="刪除附加檔案" mode="D,D,H" onclick="fbutton('delAttachedFile','EHF010106T0_24'); return false;"  src="config/deleteicon.jpg" confirm="您確定要刪除附加檔案嗎?"/>
						</logic:equal>
					</layout:field>
					<layout:text styleClass="DATAS" key="證照備註" property="EHF010106T4_07" styleId="EHF010106T4_07" size="60" mode="E,E,I" maxlength="50" name="Form1Datas" >
						<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF010106M1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF010106T4');" ></layout:image>
					</layout:text>
				</layout:grid>
				<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF010106T4_LIST" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="HR_EmployeeSysNo,EHF010106T4_01,HR_CompanySysNo" 
						paramProperty="HR_EmployeeSysNo,EHF010106T4_01,HR_CompanySysNo"
						url="EHF010106M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF010106T4" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆感應卡明細嗎?');" >刪除</layout:collectionItem>					
					<layout:collectionItem property="EHF010106T4_02" title="證照名稱" style="text-align: center" />
					<layout:collectionItem property="EHF010106T4_04" title="使用日期(起)" style="text-align: center" />
					<layout:collectionItem property="EHF010106T4_05" title="使用日期(迄)" style="text-align: center" />
					<layout:collectionItem property="EHF010106T4_02" title="檔案下載" style="text-align: center" />
				</layout:collection>
<%--				<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">--%>
<%--					<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動日期" styleClass="LOGDATA" property="HR_LastUpdateDate" name="Form1Datas" mode= "I,I,I" />--%>
<%--				</layout:grid>--%>
			</layout:tab>
			
			<layout:tab key="員工考勤基本資料" width="15%" >
				
				<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
														
					<layout:text styleClass="DATAS" key="員工姓名" property="EHF010106T0_04" styleId="EHF010106T0_04" size="16" mode="I,I,I" maxlength="10" name="Form1Datas" />
					<layout:space styleClass="DATAS"></layout:space>
					<layout:text styleClass="DATAS" key="班別" property="EHF010100T1_04" styleId="EHF010100T1_04" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" >
						<sp:lov 	form="EHF010106M0F" 
									id="EHF010100T1_04,EHF010100T1_04_TXT,EHF010100T1_04_HIDE,EHF010100T1_04_KEY" 
									lovField="EHF010100T0_03,EHF010100T0_04,EHF010100T0_02,EHF010100T0_01" 
									table="EHF010100T0"
									fieldAlias="班別代號,班別名稱,組織代號,班別序號" 
									fieldName="EHF010100T0_03,EHF010100T0_04,EHF010100T0_02,EHF010100T0_01"
									parentId="EHF010100T0_02" 
									parentField="window.EHF010106M0F.HR_DepartmentSysNo.value" 
									others=" AND EHF010100T0_11 = '${compid}' "
									changescript="chkchange('ATT')"																		
						 			/>
						<layout:text layout="false" property="EHF010100T1_04_TXT" styleId="EHF010100T1_04_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
						<layout:text layout="false" property="EHF010100T1_04_KEY" styleId="EHF010100T1_04_KEY" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
						<input type="hidden" size="12" name="EHF010100T1_04_HIDE" readonly class="READONLY" />
					</layout:text>
					<layout:radios styleClass="DATAS" key="是否計算考勤" property="EHF020403T0_05" mode="E,E,I" name="Form1Datas" cols="2" onchange="return chkchange('ATT');" >
						<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
					</layout:radios>
					<layout:number styleClass="DATAS" key="感應卡號" property="EHF020403T1_02" styleId="EHF020403T1_02" size="16" maxlength="20" name="Form1Datas" mode="E,E,I" style="TEXT-ALIGN: RIGHT" />
					<layout:space styleClass="DATAS"></layout:space>					
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" startYear="2012" endYear="2100"  key="使用期間(起)" size="10"  name="Form1Datas" property="EHF020403T1_04_START" styleClass="DATAS"  >
						&nbsp;
						<layout:select key="" name="Form1Datas" property="EHF020403T1_04_START_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
							<layout:options collection="listEHF020403T1_04_HH" property="item_id" labelProperty="item_value" />
						</layout:select>
						&nbsp;點&nbsp;
						<layout:select key="" name="Form1Datas" property="EHF020403T1_04_START_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
							<layout:options collection="listEHF020403T1_04_MM" property="item_id" labelProperty="item_value" />
						</layout:select>
						&nbsp;分
					</layout:date>
					<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="使用期間(迄)" size="10"  name="Form1Datas" property="EHF020403T1_04_END" styleClass="DATAS"  >
						&nbsp;
						<layout:select key="" name="Form1Datas" property="EHF020403T1_04_END_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
							<layout:options collection="listEHF020403T1_04_HH" property="item_id" labelProperty="item_value" />
						</layout:select>
						&nbsp;點&nbsp;
						<layout:select key="" name="Form1Datas" property="EHF020403T1_04_END_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
							<layout:options collection="listEHF020403T1_04_MM" property="item_id" labelProperty="item_value" />
						</layout:select>
						&nbsp;分
					</layout:date>
					
				</layout:grid>
				
				<layout:grid cols="2"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
				
					<layout:text styleClass="DATAS" key="感應卡備註" property="EHF020403T1_05" styleId="EHF020403T1_05" size="100" maxlength="78" name="Form1Datas" mode="E,E,I" onkeydown="nextFiled()" />	
					<layout:cell styleClass="DATAS">
						<layout:image alt="新增明細" mode="D,D,D" name="btnimage?text=button.addDetailData&type=t" property="EHF010106M1" 
									  reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF020403T1');" ></layout:image>
					</layout:cell>
					
				</layout:grid>
				
				<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF020403C" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >
										
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="HR_EmployeeSysNo,EHF020403T1_01,EHF020403T1_02,EHF020403T1_04_START,EHF020403T1_04_END,EHF020403T1_04_END_HH,EHF020403T1_04_END_MM,EHF020403T1_06" 
						paramProperty="HR_EmployeeSysNo,EHF020403T1_01,EHF020403T1_02,EHF020403T1_04_START,EHF020403T1_04_END,EHF020403T1_04_END_HH,EHF020403T1_04_END_MM,EHF020403T1_06"
						url="EHF010106M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF020403T1" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆感應卡明細嗎?');" >刪除</layout:collectionItem>					
					
					<layout:collectionItem title="感應卡號" style="TEXT-ALIGN: CENTER" >
						<layout:text name="Form1Datas" property="EHF020403C[${index}].EHF020403T1_02" size="7" maxlength="7" layout="false" mode="I,I,I" 
						style="text-align: center;ime-modeisabled;"
						onchange="document.all.item('EHF020403C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" 
						/>
					</layout:collectionItem>
										
					<layout:collectionItem title="使用期間(起)" style="TEXT-ALIGN: CENTER" >
						<layout:text name="Form1Datas" property="EHF020403C[${index}].EHF020403T1_04_START" size="7" maxlength="7" layout="false" mode="I,I,I" 
						style="text-align: center;ime-modeisabled;"
						onchange="document.all.item('EHF020403C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" 
						/>
					</layout:collectionItem>
			
					<layout:collectionItem title="使用期間(迄)" style="TEXT-ALIGN: CENTER" >								
						<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="使用期間(迄)" size="10"  
									 name="Form1Datas" property="EHF020403C[${index}].EHF020403T1_04_END" styleClass="DATAS" layout="false" onchange="return chkchange('ATT');" >
						&nbsp;
						<layout:select key="使用期間(迄)(時)" name="Form1Datas" property="EHF020403C[${index}].EHF020403T1_04_END_HH" styleClass="DATAS" mode="E,E,I" layout="false" 
									   onchange="return chkchange('ATT');" >
							<layout:options collection="listEHF020403T1_04_HH" property="item_id" labelProperty="item_value" />
						</layout:select>
						&nbsp;點&nbsp;
						<layout:select key="使用期間(迄)(分)" name="Form1Datas" property="EHF020403C[${index}].EHF020403T1_04_END_MM" styleClass="DATAS" mode="E,E,I" layout="false" 
									   onchange="return chkchange('ATT');" >
							<layout:options collection="listEHF020403T1_04_MM" property="item_id" labelProperty="item_value" />
						</layout:select>
						&nbsp;分
						</layout:date>
					</layout:collectionItem>

					<layout:collectionItem title="備註" style="TEXT-ALIGN: CENTER" >
						<layout:text name="Form1Datas" property="EHF020403C[${index}].EHF020403T1_05" size="30" maxlength="7" layout="false" mode="I,I,I" 
						style="text-align: left;ime-modeisabled;" 
						onchange="document.all.item('EHF020403C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" 
						/>
					</layout:collectionItem>
					
				</layout:collection>
				
<%--				<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">--%>
<%--					<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動日期" styleClass="LOGDATA" property="HR_LastUpdateDate" name="Form1Datas" mode= "I,I,I" />--%>
<%--				</layout:grid>--%>
			
			</layout:tab>
			
			<layout:tab key="員工薪資基本資料" width="15%" >
			
				<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
				
					<layout:text styleClass="DATAS" key="員工姓名" property="EHF010106T0_04" styleId="EHF010106T0_04" size="16" mode="I,I,I" maxlength="10" name="Form1Datas" />
					<layout:space styleClass="DATAS"></layout:space>
					<layout:select styleClass="DATAS" key="計薪方式" property="EHF030200T0_07" styleId="EHF030200T0_07" mode="E,E,I" name="Form1Datas" onchange="return chkchange('SAL');" >
						<layout:options collection="listEHF030200T0_07" property="item_id" labelProperty="item_value" />
					</layout:select>
					<layout:select styleClass="DATAS" key="給薪方式" property="EHF030200T0_05" styleId="EHF030200T0_05" mode="E,E,I" name="Form1Datas" 
								   onchange="return chkchange('SAL');" >
						<layout:options collection="listEHF030200T0_05" property="item_id" labelProperty="item_value" />									
						<layout:cell styleClass="DATAS" >
						<span id="flag_EHF030200T0_06" style="display:none;" >
							<layout:select key="銀行代號" name="Form1Datas" property="EHF030200T0_06" styleClass="DATAS" mode="E,E,I" layout="false" onchange="return chkchange('SAL');" >
								<layout:options collection="listBank" property="item_id" labelProperty="item_value" />
							</layout:select>
						<layout:number styleClass="DATAS" key="匯款帳號"  size="16" maxlength="25" name="Form1Datas" property="EHF030200T0_06_AC" 
							 		   mode="E,E,I" layout="false" onchange="return chkchange('SAL');" />
						</span>
						</layout:cell>
					</layout:select>
					<layout:number styleClass="DATAS" key="基本薪資" property="EHF030200T0_04" styleId="EHF030200T0_04" mode="E,E,I" size="10" maxlength="10" name="Form1Datas" onchange="return chkchange('SAL');" />
					<layout:number styleClass="DATAS" key="全勤獎金" property="EHF030200T0_19" styleId="EHF030200T0_19" mode="E,E,I" size="10" maxlength="10" name="Form1Datas" onchange="return chkchange('SAL');" />
					<layout:select styleClass="DATAS" key="加班費規則" property="EHF030200T0_18" styleId="EHF030200T0_18" mode="E,E,I" name="Form1Datas" onchange="return chkchange('SAL');" >
						<layout:options collection="list_EHF030200T0_18" property="item_id" labelProperty="item_value" />
					</layout:select>
					<layout:select styleClass="DATAS" key="全勤扣費規則" property="EHF030200T0_20" styleId="EHF030200T0_20" mode="E,E,I" name="Form1Datas" onchange="return chkchange('SAL');" >
						<layout:options collection="list_EHF030200T0_20" property="item_id" labelProperty="item_value" />
					</layout:select>
					<layout:radios styleClass="DATAS" key="是否代扣福利金" property="EHF030200T0_21" mode="E,E,I" cols="2" name="Form1Datas" onchange="return chkchange('SAL');" >
						<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
					</layout:radios>
					<layout:radios styleClass="DATAS" key="是否啟用" property="EHF030200T0_08" mode="E,E,I" cols="2" name="Form1Datas" onchange="return chkchange('SAL');" >
						<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
					</layout:radios>
				
				</layout:grid>
				
				<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
					<layout:text styleClass="DATAS" key="備註" property="EHF030200T0_12" styleId="EHF030200T0_12" size="100" maxlength="75" name="Form1Datas"  mode="E,E,I" onchange="return chkchange('SAL');" />
				</layout:grid>
				
				<layout:grid cols="5"  space="false" borderSpacing="0" align="left" width="100%" styleClass="DATAGRID">
				
					<layout:text styleClass="DATAS" key="薪資項目" property="EHF030200T1_02" styleId="EHF030200T1_02" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" isRequired="true" >
						<sp:lov 	form="EHF010106M0F" 
									id="EHF030200T1_02,EHF030200T1_02_TXT,EHF030200T1_02_MONEY" 
									lovField="EHF030102T0_01,EHF030101T0_02,EHF030102T0_06" 
									table="EHF030102T0"
									leftjoin=" LEFT JOIN EHF030101T0 ON EHF030101T0_01 = EHF030102T0_03 AND EHF030101T0_05 = EHF030102T0_08 "
									fieldAlias="序號,薪資項目名稱,金額" 
									fieldName="EHF030102T0_01,EHF030101T0_02,EHF030102T0_06"
									parentId="EHF030102T0_02" 
									parentField="window.EHF010106M0F.HR_DepartmentSysNo.value" 
									others=" AND EHF030102T0_08 = '${compid}' " />
					<layout:text layout="false" property="EHF030200T1_02_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
					</layout:text>
			
					<layout:space styleClass="DATAS"  />
					<layout:number styleClass="DATAS" key="薪資項目金額" property="EHF030200T1_02_MONEY" styleId="EHF030200T1_02_MONEY" size="6" maxlength="8" name="Form1Datas"   
						   			isRequired="true" mode="R,R,I" style="TEXT-ALIGN: RIGHT" />
			
					<layout:text styleClass="DATAS" key="備註" property="EHF030200T1_03" styleId="EHF030200T1_03" size="15" maxlength="15" name="Form1Datas" mode="E,E,I" />
					
					<layout:cell styleClass="DATAS">
						<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF010106M1" 
									  reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF030102T0');" ></layout:image>
					</layout:cell>
					
					<layout:text styleClass="DATAS" key="津貼項目" property="EHF030200T1_02_01" styleId="EHF030200T1_02_01" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" isRequired="true" >
						<sp:lov 	form="EHF010106M0F" 
									id="EHF030200T1_02_01,EHF030200T1_02_TXT_01,EHF030200T1_02_MONEY_01" 
									lovField="EHF010101T0_01,EHF010101T0_02,EHF010101T0_06" 
									table="EHF010101T0"
						
									fieldAlias="序號,津貼項目名稱,金額" 
									fieldName="EHF010101T0_01,EHF010101T0_02,EHF010101T0_06"
						
									parentField="window.EHF010106M0F.EHF030200T0_02_01.value" 
									others=" AND EHF010101T0_05 = 1 AND EHF010101T0_23 = '${compid}' " />
					<layout:text layout="false" property="EHF030200T1_02_TXT_01" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
					</layout:text>
			
					<layout:text styleClass="DATAS" key="可使用班別" property="EHF030200T1_04_01" styleId="EHF030200T1_04_01" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" isRequired="true" >
				
						<sp:lov form="EHF010106M0F" 
								id="EHF030200T1_04_01,EHF030200T1_04_01_TXT" 
								lovField="EHF010100T0_03,EHF010100T0_04" 
								table="EHF010100T0"
								fieldAlias="班別代號,班別名稱" 
								fieldName="EHF010100T0_03,EHF010100T0_04"
								others=" AND EHF010100T0_11 = '${compid}' " />
					<layout:text layout="false" property="EHF030200T1_04_01_TXT" styleId="EHF030200T1_04_01_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			
					</layout:text>	
			
					<layout:number styleClass="DATAS" key="津貼項目金額" property="EHF030200T1_02_MONEY_01" styleId="EHF030200T1_02_MONEY_01" size="6" maxlength="8" name="Form1Datas"   
						   			isRequired="true" mode="R,R,I" style="TEXT-ALIGN: RIGHT" />
			
					<layout:text styleClass="DATAS" key="備註" property="EHF030200T1_03_01" styleId="EHF030200T1_03_01" size="15" maxlength="15" name="Form1Datas" mode="E,E,I" />	
			
					<layout:cell styleClass="DATAS">
						<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF010106M1" 
									  reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF010101T0');" ></layout:image>
					</layout:cell>
				
				</layout:grid>
				
				<layout:collection emptyKey="沒有資料列"  name="Form1Datas" property="EHF030200C" id="bean1" indexId="index" selectId="" selectProperty=""  selectName=""  width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
<%--					<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER">--%>
<%--						<layout:text name="Form1Datas" property="EHF030200C[${index}].EHF030200T1_01" layout="false" mode="H,H,H" />--%>
<%--						<layout:text name="Form1Datas" property="EHF030200C[${index}].EHF030200T1_02" layout="false" mode="H,H,H" />--%>
<%--						<layout:text name="Form1Datas" property="EHF030200C[${index}].type" layout="false" mode="H,H,H" />--%>
<%--						<layout:checkbox name="Form1Datas" property="EHF030200C[${index}].CHECKED" layout="false"/><BR>--%>
<%--						<div style="display: none;">--%>
<%--				--%>
<%--						<layout:checkbox name="Form1Datas" property="EHF030200C[${index}].CHANGED" layout="false"/></div>	--%>
<%--					</layout:collectionItem>--%>
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="HR_EmployeeSysNo,EHF030200T1_02,type" 
						paramProperty="HR_EmployeeSysNo,EHF030200T1_02,type"
						url="EHF010106M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF030200T1" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>
					<layout:collectionItem property="EHF030200T1_02_TXT" style="TEXT-ALIGN: CENTER" title="薪資項目" />
					<layout:collectionItem property="EHF030200T1_02_MONEY" style="TEXT-ALIGN: CENTER" title="金額" />
					<layout:collectionItem property="EHF030200T1_04_01" style="TEXT-ALIGN: CENTER" title="可用班別" />
					<layout:collectionItem property="EHF030200T1_03" style="TEXT-ALIGN: CENTER" title="備註" />
				</layout:collection>
				
<%--				<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">--%>
<%--					<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動日期" styleClass="LOGDATA" property="HR_LastUpdateDate" name="Form1Datas" mode= "I,I,I" />--%>
<%--				</layout:grid>--%>
			
			</layout:tab>
			
			<layout:tab key="員工保險基本資料" width="15%" >
			
				<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
				
					<layout:text styleClass="DATAS" key="員工姓名" property="EHF010106T0_04" styleId="EHF010106T0_04" size="16" mode="I,I,I" maxlength="10" name="Form1Datas" />
					<layout:space styleClass="DATAS"></layout:space>
					
					<layout:text styleClass="DATAS" key="勞保規則" property="EHF030300T0_05_VERSION" size="6" mode="R,R,I" maxlength="8" name="Form1Datas" style="TEXT-ALIGN: RIGHT" >
						<sp:lov 	form="EHF010106M0F" 
									id="EHF030300T0_05_NUMBER,EHF030300T0_05_VERSION,EHF030300T0_05_VERSION_NAME" 
									lovField="EHF030104T0_01,EHF030104T0_02,EHF030104T0_02_VERSION" 
									table="EHF030104T0"
									fieldAlias="編號,健保等級代碼,健保等級名稱" 
									fieldName="EHF030104T0_01,EHF030104T0_02,EHF030104T0_02_VERSION"
									orderby="EHF030104T0_01,EHF030104T0_02,EHF030104T0_02_VERSION"
									parentField="window.EHF010106M0F.HR_DepartmentSysNo.value" 
									others=" AND EHF030104T0_05 = '${compid}' "
									beforerun="clear_EHF030300T0_05_HIDE()"
									changescript="chkchange('INS')"	 />
					<layout:text layout="false" property="EHF030300T0_05_VERSION_NAME" size="15" mode="R,R,H" maxlength="15" name="Form1Datas" style="TEXT-ALIGN: RIGHT" />
					<layout:text layout="false" property="EHF030300T0_05_NUMBER" size="8" mode="H,H,H" maxlength="10" name="Form1Datas" style="TEXT-ALIGN: RIGHT" />
					</layout:text>
					
					<layout:text styleClass="DATAS" key="勞保級距" property="EHF030300T0_05_HIDE" size="6" mode="R,R,I" maxlength="8" name="Form1Datas" style="TEXT-ALIGN: RIGHT" >
						<sp:lov 	form="EHF010106M0F" 
									id="EHF030300T0_05_HIDE,EHF030300T0_05" 
									lovField="EHF030104T1_03,EHF030104T1_02" 
									table="EHF030104T1"
									leftjoin=" LEFT JOIN EHF030104T0 ON EHF030104T0_01 = EHF030104T1_01 AND EHF030104T0_05 = EHF030104T1_08 "
									fieldAlias="投保級距,勞保等級" 
									fieldName="EHF030104T1_03,EHF030104T1_02"
									parentId="EHF030104T0_02" 
									parentField="window.EHF010106M0F.EHF030300T0_05_VERSION.value" 
									others="  AND EHF030104T0_03 = '${compid}' AND EHF030104T1_08 = '${compid}' " 
									changescript="chkchange('INS')"	/>
					<layout:text layout="false" property="EHF030300T0_05" size="8" mode="H,H,H" maxlength="10" name="Form1Datas" style="TEXT-ALIGN: RIGHT" />
					</layout:text>
					
					<layout:date calendarType="datepicker" startYear="2012" endYear="2020" patternKey="yy/mm/dd" key="勞保投保日期" size="10"
					 				name="Form1Datas" property="EHF030300T0_05_START" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('INS');" />
					<layout:date calendarType="datepicker" startYear="2012" endYear="2020" patternKey="yy/mm/dd" key="勞保退保日期" size="10"
					 				name="Form1Datas" property="EHF030300T0_05_END" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('INS');" />
					
					<layout:text styleClass="DATAS" key="健保規則" property="EHF030300T0_07_VERSION" size="6" mode="R,R,I" maxlength="8" name="Form1Datas" style="TEXT-ALIGN: RIGHT" >
						<sp:lov 	form="EHF010106M0F" 
									id="EHF030300T0_07_NUMBER,EHF030300T0_07_VERSION,EHF030300T0_07_VERSION_NAME" 
									lovField="EHF030103T0_01,EHF030103T0_02,EHF030103T0_02_VERSION" 
									table="EHF030103T0"
									fieldAlias="編號,健保等級代碼,健保等級名稱" 
									fieldName="EHF030103T0_01,EHF030103T0_02,EHF030103T0_02_VERSION"
									orderby="EHF030103T0_01,EHF030103T0_02,EHF030103T0_02_VERSION"
									parentField="window.EHF010106M0F.HR_DepartmentSysNo.value" 
									others="  AND EHF030103T0_05 = '${compid}'"
									beforerun="clear_EHF030300T0_07_HIDE()" 
									changescript="chkchange('INS')"	/>
					<layout:text layout="false" property="EHF030300T0_07_VERSION_NAME" size="15" mode="R,R,H" maxlength="15" name="Form1Datas" style="TEXT-ALIGN: RIGHT" />
					<layout:text layout="false" property="EHF030300T0_07_NUMBER" size="8" mode="H,H,H" maxlength="10" name="Form1Datas" style="TEXT-ALIGN: RIGHT" />
					</layout:text>
					
					<layout:text styleClass="DATAS" key="健保級距" property="EHF030300T0_07_HIDE" size="6" mode="R,R,I" maxlength="8" name="Form1Datas" style="TEXT-ALIGN: RIGHT" >
						<sp:lov 	form="EHF010106M0F" 
									id="EHF030300T0_07_HIDE,EHF030300T0_07" 
									lovField="EHF030103T1_03,EHF030103T1_02" 
									table="EHF030103T1"
									leftjoin=" LEFT JOIN EHF030103T0 ON EHF030103T0_01 = EHF030103T1_01 AND EHF030103T0_05 = EHF030103T1_08 "
									fieldAlias="投保級距,健保等級" 
									fieldName="EHF030103T1_03,EHF030103T1_02"
									parentId="EHF030103T0_02" 
									parentField="window.EHF010106M0F.EHF030300T0_07_VERSION.value" 
									others=" AND EHF030103T0_03 = '${compid}' AND EHF030103T1_08 = '${compid}' " 
									changescript="chkchange('INS')"	/>
					<layout:text layout="false" property="EHF030300T0_07" size="8" mode="H,H,H" maxlength="10" name="Form1Datas" style="TEXT-ALIGN: RIGHT" />
					</layout:text>
					
					<layout:date calendarType="datepicker" startYear="2012" endYear="2020" patternKey="yy/mm/dd" key="健保投保日期" size="10"
					 				name="Form1Datas" property="EHF030300T0_07_START" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('INS');" />
					<layout:date calendarType="datepicker" startYear="2012" endYear="2020" patternKey="yy/mm/dd" key="健保退保日期" size="10"
					 				name="Form1Datas" property="EHF030300T0_07_END" styleClass="DATAS" mode="E,E,I" onchange="return chkchange('INS');" />
					 				
					<layout:number styleClass="DATAS" property="EHF030300T0_10" key="勞保政府補助金額" 
		               				size="8" maxlength="10" name="Form1Datas" mode="E,E,I" style="TEXT-ALIGN: RIGHT" onkeydown="nextFiled()" onchange="return chkchange('INS');" />
		            <layout:number styleClass="DATAS" property="EHF030300T0_10" key="健保政府補助金額" 
		               				size="8" maxlength="10" name="Form1Datas" mode="E,E,I" style="TEXT-ALIGN: RIGHT" onkeydown="nextFiled()" onchange="return chkchange('INS');" />
		               				
		            <layout:number styleClass="DATAS" property="EHF030300T0_15" key="勞退自提金額" tooltip="勞退自提金額" 
		               				size="8" maxlength="10" name="Form1Datas" mode="E,E,I" style="TEXT-ALIGN: RIGHT" onchange="return chkchange('INS');" />
		            <layout:number styleClass="DATAS" property="EHF030300T0_09" key="勞健保總金額" tooltip="勞健保總金額" 
		               				size="10" maxlength="10" name="Form1Datas" mode="I,I,I" style="TEXT-ALIGN: RIGHT" onkeydown="nextFiled()" >
						<layout:submit reqCode="getInsuranceMoney" property="EHF030300M1" mode="D,H,H" value="計算" ></layout:submit>
					</layout:number>
					
					<layout:number styleClass="DATAS" property="EHF030300T0_11" key="員工與眷屬加保人數" tooltip="員工與眷屬加保人數" 
					   				size="2" maxlength="2" name="Form1Datas" mode="E,E,I" style="TEXT-ALIGN: RIGHT" onkeydown="nextFiled()" onchange="return chkchange('INS');" >
					(最高以四人計算)
					</layout:number>
					<layout:space styleClass="DATAS"></layout:space>
				
				</layout:grid>
				
				<layout:grid cols="3" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
					
					<layout:text styleClass="DATAS" key="加保眷屬姓名" property="EHF030300T1_02" styleId="EHF030300T1_02" size="16" mode="E,E,I" maxlength="10" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="加保眷屬關係" property="EHF030300T1_03" styleId="EHF030300T1_03" size="16" mode="E,E,I" maxlength="10" name="Form1Datas" />
					<layout:text styleClass="DATAS" key="加保眷屬ID" property="EHF030300T1_04" styleId="EHF030300T1_04" size="16" mode="E,E,I" maxlength="10" name="Form1Datas" />
					<layout:date calendarType="datepicker" startYear="2012" endYear="2020" patternKey="yy/mm/dd" key="加保眷屬生日" size="10"
					 				name="Form1Datas" property="EHF030300T1_05" styleClass="DATAS" mode="E,E,I" />
					<layout:date calendarType="datepicker" startYear="2012" endYear="2020" patternKey="yy/mm/dd" key="眷屬加保日期" size="10"
					 				name="Form1Datas" property="EHF030300T1_06" styleClass="DATAS" mode="E,E,I" />
					<layout:cell styleClass="DATAS">
						<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF010106M1" 
									  reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF030300T1');" ></layout:image>
					</layout:cell>
					
				</layout:grid>
				
				<layout:collection emptyKey="沒有資料列"  name="Form1Datas" property="EHF030300C" id="bean1" indexId="index" selectId="" selectProperty=""  selectName=""  
								   width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">					
					<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
						paramId="HR_EmployeeSysNo,EHF030300T1_04,HR_CompanySysNo" 
						paramProperty="HR_EmployeeSysNo,EHF030300T1_04,HR_CompanySysNo"
						url="EHF010106M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF030300T1" 
						onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>
					<layout:collectionItem property="EHF030300T1_02" style="TEXT-ALIGN: CENTER" title="眷屬姓名" />
					<layout:collectionItem property="EHF030300T1_03" style="TEXT-ALIGN: CENTER" title="眷屬關係" />
					<layout:collectionItem property="EHF030300T1_04" style="TEXT-ALIGN: CENTER" title="眷屬ID" />
					<layout:collectionItem property="EHF030300T1_05" style="TEXT-ALIGN: CENTER" title="眷屬生日" />
					<layout:collectionItem property="EHF030300T1_06" style="TEXT-ALIGN: CENTER" title="加保日期" />
				</layout:collection>
				
<%--				<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">--%>
<%--					<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />--%>
<%--					<layout:text  key="最後異動日期" styleClass="LOGDATA" property="HR_LastUpdateDate" name="Form1Datas" mode= "I,I,I" />--%>
<%--				</layout:grid>--%>
			
			</layout:tab>
			
<%--		</layout:notMode>--%>
		
	</layout:tabs>
	
	<layout:popup styleId="fileupload" styleClass="DATAS" key="圖片上傳">
		<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="上傳" reqCode="archive" onclick="return chkFile();"></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileupload');" />
				</layout:row>
	</layout:popup>
	
</layout:form>