<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF320100M0F" %>
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
function changeTF(){
	if($("#EHF320100T3_03 option:selected").val() == 'M'){
		$("#EHF320100T3_06").val('0');
	}else{
		$("#EHF320100T3_06").val('1');
	}
}
</script>

<layout:form action="EHF320100M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="菜單管理設定">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF320100M1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF320100M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF320100M1" ></layout:image>
    </layout:row>
    
    <layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:row>
   		 <layout:message styleClass="LARGE_MESSAGE" key="${Form_No}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
	
		<layout:text styleClass="DATAS" tooltip="系統編號" key="系統編號" property="EHF320100T0_01" styleId="EHF320100T0_01" mode="H,H,H" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="菜單編號" key="*菜單編號" property="EHF320100T0_02" styleId="EHF320100T0_02" size="12" mode="E,I,I" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="菜單名稱" key="*菜單名稱" property="EHF320100T0_03" styleId="EHF320100T0_03" size="15" mode="E,E,I" maxlength="30" name="Form1Datas" />
			
		<layout:select key="*菜單餐別" name="Form1Datas" property="EHF320100T0_04" styleId="EHF320100T0_04" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listEHF320100T0_04" property="item_id" labelProperty="item_value" />
		</layout:select>
		<layout:select key="*菜單類別" name="Form1Datas" property="EHF320100T0_05" styleId="EHF320100T0_05" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listEHF320100T0_05" property="item_id" labelProperty="item_value" />
		</layout:select>
		<layout:select key="*菜單用酒" name="Form1Datas" property="EHF320100T0_06" styleId="EHF320100T0_06" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
		</layout:select>
		<layout:select key="*菜單用油" name="Form1Datas" property="EHF320100T0_07" styleId="EHF320100T0_07" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listEHF320100T0_07" property="item_id" labelProperty="item_value" />
		</layout:select>
	
	</layout:grid>
	
	<layout:notMode value="create">
	
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" styleId="VERSION" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	
		<layout:row>
   			 <layout:message styleClass="LARGE_MESSAGE" key="遞補選項：" />
		</layout:row>
	
	<layout:grid cols="3" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
	
		<layout:select key="遞補順序" name="Form1Datas" property="EHF320100T4_02" styleId="EHF320100T4_02" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listORDER" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:text styleClass="DATAS" tooltip="遞補主食選項" key="遞補主食選項" property="EHF320100T4_03" styleId="EHF320100T4_03" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="遞補主食選項" key="遞補主食選項" property="EHF320100T4_03_SHOW" styleId="EHF320100T4_03_SHOW" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
			<sp:lov 	
				form="EHF320100M0F" 
				id="EHF320100T4_03,EHF320100T4_03_SHOW,EHF320100T4_03_TXT" 
				lovField="EHF320100T0_01,EHF320100T0_02,EHF320100T0_03" 
				table="EHF320100T0"
				fieldAlias="系統編號,菜單編號,菜單名稱" 
				fieldName="EHF320100T0_01,EHF320100T0_02,EHF320100T0_03"
				others=" AND EHF320100T0_08 = '${compid}' "
				mode="E,E,F"																
			/>
			<layout:text layout="false" property="EHF320100T4_03_TXT" styleId="EHF320100T4_03_TXT" size="12" mode="R,R,I" maxlength="30" name="Form1Datas" />
		</layout:text>
										
		<layout:cell styleClass="DATAS">
			<layout:image alt="新增明細" mode="D,D,H" name="btnimage?text=button.addDetailData&type=t" property="EHF320100M1" 
						  reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF320100T4');" ></layout:image>
		</layout:cell>
		
	</layout:grid>
	
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF320100T4_DETAIL" styleId="EHF320100T4_DETAIL" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >				
				<layout:collectionItem property="EHF320100T4_02" title="順序" style="text-align: center" />
				<layout:collectionItem property="EHF320100T4_03_TXT" title="菜單" style="text-align: center" />
				<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
					paramId="EHF320100T0_01,EHF320100T4_01,EHF320100T4_02" 
					paramProperty="EHF320100T0_01,EHF320100T4_01,EHF320100T4_02"
					url="EHF320100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF320100T4" 
					onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
	
	<layout:grid cols="3" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
	
		<layout:select key="遞補順序" name="Form1Datas" property="EHF320100T5_02" styleId="EHF320100T5_02" styleClass="DATAS" mode="E,E,I" >
			<layout:options collection="listORDER" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:text styleClass="DATAS" tooltip="遞補副食選項" key="遞補副食選項" property="EHF320100T5_03" styleId="EHF320100T5_03" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="遞補副食選項" key="遞補副食選項" property="EHF320100T5_03_SHOW" styleId="EHF320100T5_03_SHOW" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
			<sp:lov 	
				form="EHF320100M0F" 
				id="EHF320100T5_03,EHF320100T5_03_SHOW,EHF320100T5_03_SHOW" 
				lovField="EHF320100T0_01,EHF320100T0_02,EHF320100T0_03" 
				table="EHF320100T0"
				fieldAlias="系統編號,菜單編號,菜單名稱" 
				fieldName="EHF320100T0_01,EHF320100T0_02,EHF320100T0_03"
				others=" AND EHF320100T0_08 = '${compid}' "
				mode="E,E,F"																
			/>
			<layout:text layout="false" property="EHF320100T5_03_TXT" styleId="EHF320100T5_03_TXT" size="12" mode="R,R,I" maxlength="30" name="Form1Datas" />
		</layout:text>
		
		<layout:cell styleClass="DATAS">
			<layout:image alt="新增明細" mode="D,D,H" name="btnimage?text=button.addDetailData&type=t" property="EHF320100M1" 
						  reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF320100T5');" ></layout:image>
		</layout:cell>
		
	</layout:grid>
	
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF320100T5_DETAIL" styleId="EHF320100T5_DETAIL" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >				
				<layout:collectionItem property="EHF320100T5_02" title="順序" style="text-align: center" />
				<layout:collectionItem property="EHF320100T5_03_TXT" title="菜單" style="text-align: center" />
				<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
					paramId="EHF320100T0_01,EHF320100T5_01,EHF320100T5_02" 
					paramProperty="EHF320100T0_01,EHF320100T5_01,EHF320100T5_02"
					url="EHF320100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF320100T5" 
					onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
	
		<layout:row>
   		 	<layout:message styleClass="LARGE_MESSAGE" key="食材明細：" />
		</layout:row>
				
			<layout:grid cols="3" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
			
				<layout:select key="*主類別" name="Form1Datas" property="EHF320100T3_03" styleId="EHF320100T3_03" styleClass="DATAS" mode="E,E,I" onchange="return changeTF();" >
					<layout:options collection="listEHF320100T3_03" property="item_id" labelProperty="item_value" />
				</layout:select>
			
				<layout:text styleClass="DATAS" tooltip="食材分類" key="*食材分類" property="EHF320100T3_04" styleId="EHF320100T3_04" size="12" mode="E,E,I" maxlength="50" name="Form1Datas" >
					<sp:lov 	
						form="EHF320100M0F" 
						id="EHF320100T3_04,EHF320100T3_04_TXT" 
						lovField="PSFOODT0_01,PSFOODT0_04" 
						table="FOODT0"
						fieldAlias="分類代碼,分類名稱" 
						fieldName="PSFOODT0_01,PSFOODT0_04"
						others=" AND PSFOODT0_08 = '${compid}' AND PSFOODT0_07 = '1' "
						orderby=" PSFOODT0_06, PSFOODT0_01 "
						mode="E,E,F"																
					/>
					<layout:text layout="false" property="EHF320100T3_04_TXT" styleId="EHF320100T3_04_TXT" size="12" mode="R,R,I" maxlength="50" name="Form1Datas" />
				</layout:text>
				
				<layout:text styleClass="DATAS" tooltip="食材內容" key="*食材內容" property="EHF320100T3_05" styleId="EHF320100T3_05" size="12" mode="E,E,I" maxlength="50" name="Form1Datas" >
					<sp:lov 	
						form="EHF320100M0F" 
						id="EHF320100T3_05,EHF320100T3_05_TXT" 
						lovField="PSFOODT1_04,PSFOODT1_05" 
						table="FOODT1"
						fieldAlias="食材代碼,食材名稱" 
						fieldName="PSFOODT1_04,PSFOODT1_05"
						others=" AND PSFOODT1_09 = '${compid}' "
						parentId="PSFOODT1_01" 
						parentField="window.EHF320100M0F.EHF320100T3_04.value"
						orderby=" PSFOODT1_07 "
						mode="E,E,F"																
					/>
					<layout:text layout="false" property="EHF320100T3_05_TXT" styleId="EHF320100T3_05_TXT" size="12" mode="R,R,I" maxlength="50" name="Form1Datas" />
				</layout:text>
								
				<layout:select key="*遞補副食菜單" name="Form1Datas" property="EHF320100T3_06" styleId="EHF320100T3_06" styleClass="DATAS" mode="E,E,I" >
					<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
				</layout:select>	
			
				<layout:text styleClass="DATAS" tooltip="備註" key="備註" property="EHF320100T3_07" styleId="EHF320100T3_07" size="30" mode="E,E,I" maxlength="50" name="Form1Datas" />
				
				<layout:cell styleClass="DATAS">
					<layout:image alt="新增明細" mode="D,D,H" name="btnimage?text=button.addDetailData&type=t" property="EHF320100M1" 
						reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF320100T3');" ></layout:image>
				</layout:cell>
			
			</layout:grid>
			
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF320100T3_DETAIL" styleId="EHF320100T3_DETAIL" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >				
				<layout:collectionItem property="EHF320100T3_03_TXT" title="主類別" style="text-align: center" />
				<layout:collectionItem property="EHF320100T3_04_TXT" title="食材分類" style="text-align: center" />
				<layout:collectionItem property="EHF320100T3_05_TXT" title="食材內容" style="text-align: center" />
				<layout:collectionItem property="EHF320100T3_06_TXT" title="遞補副食菜單" style="text-align: center" />
				<layout:collectionItem property="EHF320100T3_07" title="備註" style="text-align: center" />
				<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
					paramId="EHF320100T0_01,EHF320100T3_01,EHF320100T3_02" 
					paramProperty="EHF320100T0_01,EHF320100T3_01,EHF320100T3_02"
					url="EHF320100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF320100T3" 
					onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
			<layout:row>
   		 		<layout:message styleClass="LARGE_MESSAGE" key="調味內容：" />
			</layout:row>
			
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
				<layout:text styleClass="DATAS" tooltip="調味" key="*調味" property="EHF320100T1_03" styleId="EHF320100T1_03" size="12" mode="E,E,I" maxlength="50" name="Form1Datas" >
					<sp:lov 	
						form="EHF320100M0F" 
						id="EHF320100T1_03,EHF320100T1_03_TXT" 
						lovField="EMS_CategoryT1_04,EMS_CategoryT1_05" 
						table="EMS_CategoryT1"
						fieldAlias="調味代碼,調味名稱" 
						fieldName="EMS_CategoryT1_04,EMS_CategoryT1_05"
						others=" AND EMS_CategoryT1_09 = '${compid}' AND EMS_CategoryT1_01 = 'ADDITIVES' "
						orderby=" EMS_CategoryT1_07 "
						mode="E,E,F"																
					/>
					<layout:text layout="false" property="EHF320100T1_03_TXT" styleId="EHF320100T1_03_TXT" size="12" mode="R,R,I" maxlength="50" name="Form1Datas" />
				</layout:text>
				<layout:cell styleClass="DATAS">
					<layout:image alt="新增明細" mode="D,D,H" name="btnimage?text=button.addDetailData&type=t" property="EHF320100M1" 
						reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF320100T1');" ></layout:image>
				</layout:cell>
			</layout:grid>
			
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF320100T1_DETAIL" styleId="EHF320100T1_DETAIL" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >				
				<layout:collectionItem property="EHF320100T1_03_TXT" title="調味" style="text-align: center" />
				<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
					paramId="EHF320100T0_01,EHF320100T1_01,EHF320100T1_02" 
					paramProperty="EHF320100T0_01,EHF320100T1_01,EHF320100T1_02"
					url="EHF320100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF320100T1" 
					onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
			
			<layout:row>
   		 		<layout:message styleClass="LARGE_MESSAGE" key="擺盤內容：" />
			</layout:row>
			
			<layout:grid cols="2" space="false" borderSpacing="0" align="left" styleClass="DATAGRID" width="100%" >
				<layout:text styleClass="DATAS" tooltip="擺盤" key="*擺盤" property="EHF320100T2_03" styleId="EHF320100T2_03" size="12" mode="E,E,I" maxlength="50" name="Form1Datas" >
					<sp:lov 	
						form="EHF320100M0F" 
						id="EHF320100T2_03,EHF320100T2_03_TXT" 
						lovField="EMS_CategoryT1_04,EMS_CategoryT1_05" 
						table="EMS_CategoryT1"
						fieldAlias="擺盤代碼,擺盤名稱" 
						fieldName="EMS_CategoryT1_04,EMS_CategoryT1_05"
						others=" AND EMS_CategoryT1_09 = '${compid}' AND EMS_CategoryT1_01 = 'PLATE' "
						orderby=" EMS_CategoryT1_07 "
						mode="E,E,F"																
					/>
					<layout:text layout="false" property="EHF320100T2_03_TXT" styleId="EHF320100T2_03_TXT" size="12" mode="R,R,I" maxlength="50" name="Form1Datas" />
				</layout:text>
				<layout:cell styleClass="DATAS">
					<layout:image alt="新增明細" mode="D,D,H" name="btnimage?text=button.addDetailData&type=t" property="EHF320100M1" 
						reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF320100T2');" ></layout:image>
				</layout:cell>
			</layout:grid>
			
			<layout:collection emptyKey="沒有資訊列" name="Form1Datas" property="EHF320100T2_DETAIL" styleId="EHF320100T2_DETAIL" id="bean1" indexId="index" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2" >				
				<layout:collectionItem property="EHF320100T2_03_TXT" title="擺盤" style="text-align: center" />
				<layout:collectionItem title="刪除" style="TEXT-ALIGN: CENTER" 
					paramId="EHF320100T0_01,EHF320100T2_01,EHF320100T2_02" 
					paramProperty="EHF320100T0_01,EHF320100T2_01,EHF320100T2_02"
					url="EHF320100M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF320100T2" 
					onclick="return confirmShowEMSWait('您確定要刪除此筆明細嗎?');" >刪除</layout:collectionItem>	
			</layout:collection>
		
		</layout:notMode>
		
	
	

</layout:form>