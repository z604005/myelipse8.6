<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.hr.forms.EHF010101M0F" %>
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
	
	if(document.getElementById("chk_type").value == "yes"){
		chkEHF010101T0_06_FLG();
		chkEHF010101T0_07();
		chkEHF010101T0_08();
		chkEHF010101T0_12();
		chkEHF010101T0_13();
		chkEHF010101T0_17();
		chkEHF010101T0_18();
	}
		
}

function chkEHF010101T0_06_FLG(){
	//是否依據一日基本新給津貼
	if(!document.getElementById("EHF010101T0_06_FLG").checked){
		document.getElementById("EHF010101T0_06").style.display = "";
		document.getElementById("flag_EHF010101T0_06_RATE").style.display = "none";
	}else{
		document.getElementById("EHF010101T0_06").style.display = "none";
		if(document.getElementById("EHF010101T0_06").value == "" || document.getElementById("EHF010101T0_06").value == null){
			document.getElementById("EHF010101T0_06").value = "0";
		} 
		document.getElementById("flag_EHF010101T0_06_RATE").style.display = "";
	}
}

function chkEHF010101T0_07(){
	//津貼條件
	if(!document.getElementById("EHF010101T0_07").checked){
		document.getElementById("flag_EHF010101T0_07").style.display = "none";
	}else{
		document.getElementById("flag_EHF010101T0_07").style.display = "";
	}
}

function chkEHF010101T0_08(){
	//津貼條件類型
	if(document.getElementById("EHF010101T0_08").value == "01"){
		document.getElementById("flag_EHF010101T0_09").style.display = "";
		document.getElementById("flag_EHF010101T0_10").style.display = "none";
		document.getElementById("flag_EHF010101T0_11").style.display = "none";
	}else if(document.getElementById("EHF010101T0_08").value == "02"){
		document.getElementById("flag_EHF010101T0_09").style.display = "none";
		document.getElementById("flag_EHF010101T0_10").style.display = "";
		document.getElementById("flag_EHF010101T0_11").style.display = "none";
	}else if(document.getElementById("EHF010101T0_08").value == "03"){
		document.getElementById("flag_EHF010101T0_09").style.display = "none";
		document.getElementById("flag_EHF010101T0_10").style.display = "none";
		document.getElementById("flag_EHF010101T0_11").style.display = "";
	}else{
		document.getElementById("flag_EHF010101T0_09").style.display = "none";
		document.getElementById("flag_EHF010101T0_10").style.display = "none";
		document.getElementById("flag_EHF010101T0_11").style.display = "none";
	}
}

function chkEHF010101T0_12(){
	//津貼加成
	if(!document.getElementById("EHF010101T0_12").checked){
		document.getElementById("flag_EHF010101T0_12").style.display = "none";
	}else{
		document.getElementById("flag_EHF010101T0_12").style.display = "";
	}
}

function chkEHF010101T0_13(){
	//津貼加成類型
	if(document.getElementById("EHF010101T0_13").value == "01"){
		document.getElementById("flag_EHF010101T0_14").style.display = "";
		document.getElementById("flag_EHF010101T0_15").style.display = "none";
		document.getElementById("flag_EHF010101T0_16").style.display = "none";
	}else if(document.getElementById("EHF010101T0_13").value == "02"){
		document.getElementById("flag_EHF010101T0_14").style.display = "none";
		document.getElementById("flag_EHF010101T0_15").style.display = "";
		document.getElementById("flag_EHF010101T0_16").style.display = "none";
	}else if(document.getElementById("EHF010101T0_13").value == "03"){
		document.getElementById("flag_EHF010101T0_14").style.display = "none";
		document.getElementById("flag_EHF010101T0_15").style.display = "none";
		document.getElementById("flag_EHF010101T0_16").style.display = "";
	}else{
		document.getElementById("flag_EHF010101T0_14").style.display = "none";
		document.getElementById("flag_EHF010101T0_15").style.display = "none";
		document.getElementById("flag_EHF010101T0_16").style.display = "none";
	}
}

function chkEHF010101T0_17(){
	//津貼加成條件
	if(document.getElementById("EHF010101T0_12").checked){
		if(!document.getElementById("EHF010101T0_17").checked){
			document.getElementById("flag_EHF010101T0_17").style.display = "none";
		}else{
			document.getElementById("flag_EHF010101T0_17").style.display = "";
		}
	}else{
		//alert("請先勾選津貼加成!!");
	}
}

function chkEHF010101T0_18(){
	//津貼加成條件類型
	if(document.getElementById("EHF010101T0_18").value == "01"){
		document.getElementById("flag_EHF010101T0_19").style.display = "";
		document.getElementById("flag_EHF010101T0_20").style.display = "none";
		document.getElementById("flag_EHF010101T0_21").style.display = "none";
	}else if(document.getElementById("EHF010101T0_18").value == "02"){
		document.getElementById("flag_EHF010101T0_19").style.display = "none";
		document.getElementById("flag_EHF010101T0_20").style.display = "";
		document.getElementById("flag_EHF010101T0_21").style.display = "none";
	}else if(document.getElementById("EHF010101T0_18").value == "03"){
		document.getElementById("flag_EHF010101T0_19").style.display = "none";
		document.getElementById("flag_EHF010101T0_20").style.display = "none";
		document.getElementById("flag_EHF010101T0_21").style.display = "";
	}else{
		document.getElementById("flag_EHF010101T0_19").style.display = "none";
		document.getElementById("flag_EHF010101T0_20").style.display = "none";
		document.getElementById("flag_EHF010101T0_21").style.display = "none";
	}
}

</script>
<layout:form action="EHF010101M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="津貼基本資料設定">
	<input type="hidden" id="chk_type" value="${chk_type}"/>
	<layout:row>
		<logic:equal name="button" value="init">
			<!--<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=查詢&type=t"  reqCode="queryForm" property="EHF010101M0" ></layout:image>-->
			<!--<layout:image alt="儲存" mode="D,D,H" name="btnimage?text=儲存&type=t"  reqCode="addDataForm" property="EHF010101M0" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=回前作業&type=t"  reqCode="init" property="EHF010101M0" ></layout:image>-->
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF010101M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="init_add" property="EHF010101M0" ></layout:image>
		</logic:equal>
		
		<logic:equal name="button" value="edit">
			<logic:equal name="edit" value="edit">
				<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveData" property="EHF010101M0" ></layout:image>
			</logic:equal>
			<logic:equal name="edit" value="add">
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"  reqCode="addData" property="EHF010101M0" ></layout:image>
			</logic:equal>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="init" property="EHF010101M0" ></layout:image>
		</logic:equal>
		
		
		<logic:equal name="button" value="query">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF010101M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="init_add" property="EHF010101M0" ></layout:image>
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t"  reqCode="editDataForm" property="EHF010101M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF010101M0"  onclick="return confirm('您確定要刪除資料嗎?')"></layout:image>
			<!--<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=回前作業&type=t"  reqCode="init" property="EHF010101M0" ></layout:image>-->
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ErrMSG}" />   		 
	</layout:row>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="津貼資料序號" property="EHF010101T0_01" styleId="EHF010101T0_01" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<logic:notEqual name="button" value="edit" >

		</logic:notEqual>
		
		<logic:equal name="button" value="edit">

		</logic:equal>
		
		<layout:text styleClass="DATAS" key="津貼名稱" property="EHF010101T0_02" styleId="EHF010101T0_02" size="16" mode="E,E,I" maxlength="20" name="Form1Datas" >
		(依天數計算每日津貼金額)
		</layout:text>
		
		<layout:radios key="是否啟用" name="Form1Datas" property="EHF010101T0_05" styleClass="DATAS" mode="E,E,I" cols="2" >
			<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
		</layout:radios>
		
		
		<logic:notEqual name="button" value="init">
		<logic:notEqual name="button" value="query">
		
<%--	所得稅類別保留位置	--%>
		<layout:select key="所得稅" name="Form1Datas" property="EHF010101T0_03" styleId="EHF010101T0_03" styleClass="DATAS" mode="E,E,I" layout="true" >
			<layout:options collection="listEHF010101T0_03" property="item_id" labelProperty="item_value" />
		</layout:select>
					
<%--    津貼類別保留位置        --%>
		
		<layout:number key="津貼金額" name="Form1Datas" property="EHF010101T0_06" styleId="EHF010101T0_06" styleClass="DATAS" mode="E,E,I" size="12" maxlength="16" >
			<logic:notEqual name="queryCondition" value="yes">
			&nbsp;是否依據一日基本薪給津貼:&nbsp;
			<layout:checkbox key="是否依據一日基本薪給津貼" name="Form1Datas" property="EHF010101T0_06_FLG" styleId="EHF010101T0_06_FLG" styleClass="DATAS" mode="E,E,I" 	 layout="false" onclick="return chkEHF010101T0_06_FLG();" >
				<span id="flag_EHF010101T0_06_RATE" style="display:none;" >
					&nbsp;&nbsp;一日基本薪加成率:
					<layout:number styleClass="DATAS" key="一日基本薪加成率" property="EHF010101T0_06_RATE" styleId="EHF010101T0_06_RATE" layout="false" size="5" maxlength="6" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;倍
				</span>
			</layout:checkbox>
			</logic:notEqual>
		</layout:number>
		</logic:notEqual>
		</logic:notEqual>
		
		
		
<%--		<layout:cell styleClass="DATAS" />--%>
		
	</layout:grid>
	
	<logic:equal name="button" value="edit">

	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		<layout:checkbox key="是否參考加班紀錄" name="Form1Datas" property="EHF010101T0_24" styleId="EHF010101T0_24" styleClass="DATAS" mode="E,E,I"> </layout:checkbox>
		
		<layout:checkbox key="是否有津貼條件" name="Form1Datas" property="EHF010101T0_07" styleId="EHF010101T0_07" styleClass="DATAS" mode="E,E,I" onclick="return chkEHF010101T0_07();" >
		
		<layout:cell styleClass="DATAS" >
		&nbsp;&nbsp;&nbsp;&nbsp;
		<span id="flag_EHF010101T0_07" style="display:none;" >
			
			<layout:select key="津貼條件類型" name="Form1Datas" property="EHF010101T0_08" styleId="EHF010101T0_08" styleClass="DATAS" mode="E,E,I" layout="false" onchange="return chkEHF010101T0_08();" >
				<layout:options collection="listEHF010101T0_08" property="item_id" labelProperty="item_value" />
			
			
			<layout:cell styleClass="DATAS" >
				
				<span id="flag_EHF010101T0_09" style="display:none;" >
					<layout:select key="津貼條件時間(時)" name="Form1Datas" property="EHF010101T0_09_HH" styleId="EHF010101T0_09_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listHOUR" property="item_id" labelProperty="item_value" />
					</layout:select>
					&nbsp;時&nbsp;
					<layout:select key="津貼條件時間(分)" name="Form1Datas" property="EHF010101T0_09_MM" styleId="EHF010101T0_09_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
						<layout:options collection="listMINUTE" property="item_id" labelProperty="item_value" />
					</layout:select>&nbsp;分
				</span>
				<span id="flag_EHF010101T0_10" style="display:none;" >
					<layout:number styleClass="DATAS" key="津貼條件時數" property="EHF010101T0_10" styleId="EHF010101T0_10" layout="false" size="4" maxlength="4" 
					   	   	style="TEXT-ALIGN: RIGHT" /> 小時
				</span>
				<span id="flag_EHF010101T0_11" style="display:none;" >
<%--				保留給津貼特殊條件Key的下拉選單		--%>
				</span>
				
			</layout:cell>
			
			</layout:select>
			
		</span>
		
		</layout:cell>
		
		</layout:checkbox>
		<layout:checkbox key="是否有津貼加成" name="Form1Datas" property="EHF010101T0_12" styleId="EHF010101T0_12" styleClass="DATAS" mode="E,E,I" onclick="return chkEHF010101T0_12();" >
		
		<layout:cell styleClass="DATAS" >
		&nbsp;&nbsp;&nbsp;&nbsp;
		<span id="flag_EHF010101T0_12" style="display:none;" >
			
			<layout:select key="津貼加成類型" name="Form1Datas" property="EHF010101T0_13" styleId="EHF010101T0_13" styleClass="DATAS" mode="E,E,I" layout="false" onchange="return chkEHF010101T0_13();" >
				<layout:options collection="listEHF010101T0_13" property="item_id" labelProperty="item_value" />
			
				<layout:cell styleClass="DATAS" >
				
				<span id="flag_EHF010101T0_14" style="display:none;" >
					<layout:number styleClass="DATAS" key="津貼加成率" property="EHF010101T0_14" styleId="EHF010101T0_14" layout="false" size="5" maxlength="6" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;倍
				</span>
				<span id="flag_EHF010101T0_15" style="display:none;" >
					<layout:number styleClass="DATAS" key="津貼加成固定金額" property="EHF010101T0_15" styleId="EHF010101T0_15" layout="false" size="8" maxlength="10" 
					   	   	style="TEXT-ALIGN: RIGHT" />&nbsp;元
				</span>
				<span id="flag_EHF010101T0_16" style="display:none;" >
<%--				保留給津貼加成職等條件Key的下拉選單		--%>
				</span>
				
				</layout:cell>
				
			</layout:select>
			
			<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;是否有津貼加成條件:&nbsp;&nbsp;
			<layout:checkbox key="是否有津貼加成條件" layout="false" name="Form1Datas" property="EHF010101T0_17" styleId="EHF010101T0_17" styleClass="DATAS" mode="E,E,I" onclick="return chkEHF010101T0_17();" >
			
			<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;&nbsp;&nbsp;
			<span id="flag_EHF010101T0_17" style="display:none;" >
			
				<layout:select key="津貼加成條件類型" name="Form1Datas" property="EHF010101T0_18" styleId="EHF010101T0_18" styleClass="DATAS" mode="E,E,I" layout="false" onchange="return chkEHF010101T0_18();" >
					<layout:options collection="listEHF010101T0_18" property="item_id" labelProperty="item_value" />
			
				<layout:cell styleClass="DATAS" >
				
					<span id="flag_EHF010101T0_19" style="display:none;" >
						<layout:select key="津貼加成條件時間(時)" name="Form1Datas" property="EHF010101T0_19_HH" styleId="EHF010101T0_19_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
							<layout:options collection="listHOUR" property="item_id" labelProperty="item_value" />
						</layout:select>
						&nbsp;時&nbsp;
						<layout:select key="津貼加成條件時間(分)" name="Form1Datas" property="EHF010101T0_19_MM" styleId="EHF010101T0_19_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
							<layout:options collection="listMINUTE" property="item_id" labelProperty="item_value" />
						</layout:select>&nbsp;分
					</span>
					<span id="flag_EHF010101T0_20" style="display:none;" >
						<layout:number styleClass="DATAS" key="津貼加成條件時數" property="EHF010101T0_20" styleId="EHF010101T0_20" layout="false" size="4" maxlength="4" 
					   	 		       style="TEXT-ALIGN: RIGHT" /> 小時
					</span>
					<span id="flag_EHF010101T0_21" style="display:none;" >
<%--					保留給津貼加成特殊條件Key的下拉選單		--%>
					</span>
				
				</layout:cell>
				
				</layout:select>
			
			</span>
			</layout:cell>
			</layout:checkbox>
			</layout:cell>
			
		</span>
		
		</layout:cell>
		</layout:checkbox>
	
	</layout:grid>

		<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:textarea styleClass="DATAS" property="EHF010101T0_22" styleId="EHF010101T0_22" key="備註" rows="2" cols="80" maxlength="50" name="Form1Datas"  mode="E,E,I" />
		</layout:grid>
	</logic:equal>

	
	<logic:equal name="edit" value="edit">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>
	
	
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
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				EHF010101M0F FORM=(EHF010101M0F)list.get(i);
				strTmp = FORM.getEHF010101T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF010101T0_02" style="TEXT-ALIGN: CENTER" title="津貼名稱" />
		<layout:collectionItem property="EHF010101T0_05" style="TEXT-ALIGN: CENTER" title="啟用" />
		<layout:collectionItem property="EHF010101T0_06" style="TEXT-ALIGN: CENTER" title="津貼金額" />
		<layout:collectionItem property="EHF010101T0_22" style="TEXT-ALIGN: LEFT" title="備註"/>
	</layout:collection>
	</layout:pager>
	</logic:equal>

</layout:form>