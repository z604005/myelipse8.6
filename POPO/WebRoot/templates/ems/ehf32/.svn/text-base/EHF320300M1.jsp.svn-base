<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF320300M0F" %>
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

</script>

<layout:form action="EHF320300M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="餐點預排管理">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF320300M1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF320300M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?"
				 		  reqCode="delForm" property="EHF320300M1" ></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF320300M1" ></layout:image>
    </layout:row>

	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:text styleClass="DATAS" property="EHF320300T0_01" styleId="EHF320300T0_01" size="16" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,I,I"  startYear="2015" endYear="2030"  key="日期" size="10"  name="Form1Datas" property="EHF320300T0_02" styleClass="DATAS"/>
		<layout:select styleClass="DATAS" key="上菜順序(天)" name="Form1Datas" property="EHF320300T0_03" styleId="EHF320300T0_03"  mode="E,I,I">
			<layout:options collection="listEHF320300T0_03" property="item_id" labelProperty="item_value" />
		</layout:select>

		<layout:text styleClass="DATAS" key="" property="EHF320300T0_04" styleId="EHF320300T0_04" mode="H,H,H" name="Form1Datas" />
		
	</layout:grid>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
		<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
	</layout:grid>
	
<!--		<layout:notMode value="create" > -->
		
			<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
				<layout:number styleClass="DATAS" key="上菜順序(項)" property="EHF320300T1_02" styleId="EHF320300T1_02" size="5" mode="E,E,I" maxlength="2" tooltip="上菜順序(項)" name="Form1Datas" />
				<layout:select styleClass="DATAS" key="菜單餐別" name="Form1Datas" property="EHF320300T1_03" styleId="EHF320300T1_03"  mode="E,E,I">
						<layout:options collection="listEHF320300T1_03" property="item_id" labelProperty="item_value" />
				</layout:select>
				
				<layout:select styleClass="DATAS" key="菜單類別" name="Form1Datas" property="EHF320300T1_04" styleId="EHF320300T1_04"  mode="E,E,I">
						<layout:options collection="listEHF320300T1_04" property="item_id" labelProperty="item_value" />
				</layout:select>
				<layout:text styleClass="DATAS" key="菜單編號" property="EHF320300T1_05_TXT_0" styleId="EHF320300T1_05_TXT_0" size="20" mode="R,R,I" maxlength="20" name="Form1Datas" tooltip="菜單編號" >				
						<sp:lov 	form="EHF320300M0F" 
									id="EHF320300T1_05,EHF320300T1_05_TXT_0,EHF320300T1_05_TXT,EHF320300T1_03,EHF320300T1_04" 
									lovField="EHF320100T0_01,EHF320100T0_02,EHF320100T0_03,EHF320100T0_04,EHF320100T0_05" 
									table="EHF320100T0"
									fieldAlias="系統編號,菜單編號,菜單名稱,餐別代碼,菜單代碼" 
									fieldName="EHF320100T0_01,EHF320100T0_02,EHF320100T0_03,EHF320100T0_04,EHF320100T0_05"									
									others=" AND EHF320100T0_08 = '${compid}' "
									mode="E,E,F"
									parentId=""
									parentField=""
						/>
						<layout:text layout="false" property="EHF320300T1_05_TXT" styleId="EHF320300T1_05_TXT" size="20" mode="R,R,I" maxlength="30" name="Form1Datas" />
						<layout:text layout="false" property="EHF320300T1_05" styleId="EHF320300T1_05" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
						
				</layout:text>
				
			</layout:grid>

				
			<layout:cell styleClass="DATAS11" >
							<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF320300T1" 
							reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF320300T1');" ></layout:image>	
			</layout:cell>
			
						<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF320300_DETAIL" selectId="EHF320300_DETAIL" selectProperty="" selectName=""  
							   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
				<layout:collectionItem property="EHF320300T1_02" style="TEXT-ALIGN: CENTER" title="項次" />
				<layout:collectionItem property="EHF320300T1_03" style="TEXT-ALIGN: CENTER" title="餐別" />
				<layout:collectionItem property="EHF320300T1_04" style="TEXT-ALIGN: CENTER" title="類別" />
				<layout:collectionItem property="EHF320300T1_05" style="TEXT-ALIGN: CENTER" title="菜單名稱" />
				<layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除" 
									   url="EHF320300M1.do?reqCode=delDetailForm" 
									   paramId="EHF320300T1_01,EHF320300T1_02" 
									   paramProperty="EHF320300T1_01,EHF320300T1_02" 
									   onclick=" return confirmShowEMSWait('您確定要刪除資料嗎?'); " >
					刪除				
				</layout:collectionItem>
			</layout:collection>

<!--		</layout:notMode>-->

</layout:form>