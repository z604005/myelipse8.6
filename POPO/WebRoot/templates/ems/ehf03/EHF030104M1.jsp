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

<!-- JavaScript -->
<script type="text/javascript">

function chktotal(index){
	
//	alert(index);

	if(index != null){
//		alert("執行第一項");
		document.getElementById("EHF030104C["+index+"].EHF030104T1_06").value = parseInt(document.getElementById("EHF030104C["+index+"].EHF030104T1_04").value) + parseInt(document.getElementById("EHF030104C["+index+"].EHF030104T1_05").value);	
	}else{
//		alert("執行第二項");
		document.getElementById("EHF030104T1_06").value = parseInt(document.getElementById("EHF030104T1_04").value) + parseInt(document.getElementById("EHF030104T1_05").value);
		
	}
	
}


</script>

<layout:form action="EHF030104M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="勞保等級維護">
<input type="hidden" id="dataChanged" name="dataChanged" value="" />	
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="儲存" mode="D,H,H" name="btnimage?text=button.save&type=t"  reqCode="addDataForm" property="EHF030104M1" ></layout:image>
		</logic:notEqual>
			
		<logic:equal name="button" value="edit">
		</logic:equal>
			
		<logic:equal name="button" value="query">
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveData" property="EHF030104M1" ></layout:image>
			<layout:image alt="刪除明細" mode="H,D,H" name="btnimage?text=button.deleteDetailData&type=t"  reqCode="delData" property="EHF030104M1"  onclick="return confirm('您確定要刪除明細資料嗎?')"></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF030104M1" ></layout:image>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	
	</layout:row>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="勞保等級序號" property="EHF030104T0_01" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />

		<logic:notEqual name="button" value="edit" >
			
			<%--新增於20140116 BY賴泓錡           --%>
			<layout:text styleClass="DATAS" key="勞保等級代碼" property="EHF030104T0_02" styleId="EHF030104T0_02" size="10" mode="E,I,I" maxlength="4" name="Form1Datas" />
			<layout:text styleClass="DATAS" key="勞保等級名稱" property="EHF030104T0_02_VERSION" styleId="EHF030104T0_02_VERSION" size="30" mode="E,E,I" maxlength="20" name="Form1Datas" />

<%--			<layout:select key="年度" name="Form1Datas" property="EHF030104T0_02" styleClass="DATAS" mode="E,I,I" >--%>
<%--				<layout:options collection="EHF030104T0_02_list" property="item_id" labelProperty="item_value" />--%>
<%--			</layout:select>	--%>

		</logic:notEqual>
		
			
		<logic:equal name="button" value="edit">		
		<%--新增於20140116 BY賴泓錡           --%>
		<layout:text styleClass="DATAS" key="勞保等級代碼" property="EHF030104T0_02" styleId="EHF030104T0_02" size="10" mode="I,I,I" maxlength="4" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="勞保等級名稱" property="EHF030104T0_02_VERSION" styleId="EHF030104T0_02_VERSION" size="30" mode="I,I,I" maxlength="20" name="Form1Datas" />
		
<%--	<layout:select key="年度" name="Form1Datas" property="EHF030104T0_02" styleClass="DATAS" mode="I,I,I" >--%>
<%--		<layout:options collection="EHF030104T0_02_list" property="item_id" labelProperty="item_value" />--%>
<%--	</layout:select>--%>
		</logic:equal>
		
<%--	<layout:text styleClass="DATAS" tooltip="公司組織" key="公司組織" property="EHF030104T0_03" size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >--%>
<%--		<sp:emspopup function="dep" id="EHF030104T0_03,EHF030104T0_03_TXT"></sp:emspopup>--%>
<%--			<layout:text layout="false" property="EHF030104T0_03_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />--%>
<%--	</layout:text>--%>
	</layout:grid>
	
	
	
		<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text styleClass="DATAS" property="EHF030104T0_04" styleId="EHF030104T0_04" key="備註" size="100" maxlength="50" name="Form1Datas"  mode="E,E,I" />
		</layout:grid>
	
	
	<logic:equal name="collection" value="show">
	
		<layout:grid cols="2"  space="false" borderSpacing="0" align="left" width="100%" styleClass="DATAGRID">
			<layout:number styleClass="DATAS" property="EHF030104T1_03" styleId="EHF030104T1_03" key="投保級距" tooltip="投保級距" 
				size="10" maxlength="7" name="Form1Datas"   isRequired="true" mode="E,E,I" style="TEXT-ALIGN: RIGHT" onkeydown="nextFiled()" />
						   
			<layout:text styleClass="DATAS" property="EHF030104T1_07" styleId="EHF030104T1_07" key="備註" tooltip="備註"  size="50" maxlength="25" name="Form1Datas" mode="E,E,I" onkeydown="nextFiled()" >
				 <layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF030104M1" reqCode="addDetailDataForm" ></layout:image>	
			</layout:text>
		</layout:grid>

		<layout:collection name="Form1Datas" property="EHF030104C" emptyKey="沒有資料列" indexId="index"  width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem title="選取"	style="TEXT-ALIGN: CENTER" width="10%" >
				<layout:text name="Form1Datas" property="EHF030104C[${index}].EHF030104T1_01" styleId="EHF030104C[${index}].EHF030104T1_01" layout="false" mode="H,H,H" />

				<logic:equal name="Form1Datas" property="EHF030104C[${index}].SHOW" value="1">
					<layout:checkbox name="Form1Datas" property="EHF030104C[${index}].CHECKED" styleId="EHF030104C[${index}].CHECKED" layout="false"/><BR>
				</logic:equal>
				
<%--				<layout:checkbox name="Form1Datas" property="EHF030104C[${index}].CHECKED" layout="false"/><BR>--%>

				<div style="display: none;">
					<layout:checkbox name="Form1Datas" property="EHF030104C[${index}].CHANGED" styleId="EHF030104C[${index}].CHANGED" layout="false"/>
				</div>	
			
			</layout:collectionItem>
			<layout:collectionItem title="勞保等級" style="TEXT-ALIGN: CENTER" width="20%" >
				<layout:text name="Form1Datas" property="EHF030104C[${index}].EHF030104T1_09" styleId="EHF030104C[${index}].EHF030104T1_09" size="7" maxlength="7" 
				layout="false" mode="I,I,I" style="text-align: center;ime-modeisabled;"
							onchange="document.all.item('EHF030104C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" 
				/>
			</layout:collectionItem>
<%--			<layout:collectionItem title="勞保級距" style="text-align: CENTER" width="20%" >--%>
<%--				<layout:text name="Form1Datas" property="EHF030104C[${index}].EHF030104T1_03" size="7" maxlength="7" layout="false" mode="E,E,I" --%>
<%--				style="text-align: right;ime-modeisabled;" type="money"--%>
<%--				onchange="document.all.item('EHF030104C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" --%>
<%--				/>--%>
<%--			</layout:collectionItem>--%>
			
			
			<layout:collectionItem title="勞保級距" style="text-align: CENTER" width="20%" >
			
				<logic:equal name="Form1Datas" property="EHF030104C[${index}].SHOW" value="1">
					<layout:number name="Form1Datas" property="EHF030104C[${index}].EHF030104T1_03" styleId="EHF030104C[${index}].EHF030104T1_03" size="7" maxlength="7" 
					layout="false" mode="E,E,I" style="text-align: right;ime-modeisabled;" type="money"
								onchange="document.all.item('EHF030104C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" 
					/>
				</logic:equal>
				
				
				<logic:equal name="Form1Datas" property="EHF030104C[${index}].SHOW" value="0">
					<layout:number name="Form1Datas" property="EHF030104C[${index}].EHF030104T1_03" styleId="EHF030104C[${index}].EHF030104T1_03" size="7" maxlength="7" 
					layout="false" mode="I,I,I" style="text-align: right;ime-modeisabled;" type="money"
								onchange="document.all.item('EHF030104C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" 
					/>
				</logic:equal>
			
			</layout:collectionItem>


			<layout:collectionItem title="備註" width="50%" >
			
				<logic:equal name="Form1Datas" property="EHF030104C[${index}].SHOW" value="1">
					<layout:text name="Form1Datas" property="EHF030104C[${index}].EHF030104T1_07" styleId="EHF030104C[${index}].EHF030104T1_07" size="50" maxlength="25"	
					layout="false" mode="E,E,I" style="text-align: left;ime-modeisabled;" 
								onchange="document.all.item('EHF030104C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" 
				/>
				</logic:equal>
				
				
				<logic:equal name="Form1Datas" property="EHF030104C[${index}].SHOW" value="0">
					<layout:text name="Form1Datas" property="EHF030104C[${index}].EHF030104T1_07" styleId="EHF030104C[${index}].EHF030104T1_07" size="50" maxlength="50"
					layout="false" mode="I,I,I" style="text-align: left;ime-modeisabled;"
								onchange="document.all.item('EHF030104C[${index}].CHANGED').checked=true;document.getElementById('dataChanged').value='*';this.style.background='#DAFFDA';" 
				/>
				</logic:equal>

			</layout:collectionItem>

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

</layout:form>