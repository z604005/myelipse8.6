<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

<layout:form action="EHF030108M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="獎金扣費規則設定">

<input type="hidden" id="dataChanged" name="dataChanged" value="" />	
<input type="hidden" id="stf_open_type" value="${stf_open_type}" />

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="儲存" mode="D,H,H" name="btnimage?text=button.save&type=t" 
						  reqCode="addDataForm" property="EHF030108M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t" 
						  reqCode="saveDataForm" property="EHF030108M1" ></layout:image>

			
			<logic:notEqual name="DisplayFileName" value="" >
				<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" 
							  onclick="opendownloadfile(); return false;" >
				</layout:image>
			</logic:notEqual>
				  
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t" 
						  reqCode="delForm" property="EHF030108M1"
						  confirm="您確定要刪除資料嗎?" ></layout:image>	
		
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t" 
					  reqCode="redirect" property="EHF030108M1" ></layout:image>
	</layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="獎金扣費規則序號" name="Form1Datas" property="EHF030108T0_01" styleId="EHF030108T0_01"
					 size="25" maxlength="25" mode="H,H,H" />
		
		<layout:text styleClass="DATAS" key="獎金扣費規則代號" name="Form1Datas" property="EHF030108T0_02" styleId="EHF030108T0_02"
					 size="25" maxlength="25" mode="E,I,I" />
		<layout:text styleClass="DATAS" key="獎金扣費規則名稱" name="Form1Datas" property="EHF030108T0_03" styleId="EHF030108T0_03"
					 size="30" maxlength="40" mode="E,E,I" />
		
		<layout:radios key="是否啟用" name="Form1Datas"  property="EHF030108T0_12" styleClass="DATAS"   mode="E,E,I" cols="2" >
			<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
		</layout:radios>
		
		<layout:space styleClass="DATAS" />
		
<%--		<layout:radios key="是否依出勤天數計算" name="Form1Datas"  property="EHF030108T0_14" styleClass="DATAS"   mode="E,E,I" cols="2" >--%>
<%--			<layout:options collection="listTF" property="item_id" labelProperty="item_value" />--%>
<%--		</layout:radios>--%>
		
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;全勤獎金扣費規則:&nbsp;&nbsp;
			<br>
			&nbsp;&nbsp;1.&nbsp;&nbsp;
			每&nbsp;
			<layout:select key="遲到早退頻率單位" name="Form1Datas" 
						   property="EHF030108T0_04" styleId="EHF030108T0_04"
						   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listFREQ" 
							    property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;
			累計遲到/早退&nbsp;&nbsp;
			<layout:number styleClass="DATAS" key="遲到早退扣費基準" 
						   property="EHF030108T0_05" styleId="EHF030108T0_05" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;
			<layout:select key="遲到早退扣費基準單位" name="Form1Datas" 
						   property="EHF030108T0_06" styleId="EHF030108T0_06" 
						   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="list_EHF030108T0_06" 
							    property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;&nbsp;
			扣獎金
			&nbsp;&nbsp;
			<layout:number styleClass="DATAS" key="遲到早退扣費金額" 
						   property="EHF030108T0_07" styleId="EHF030108T0_07" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;元
			<br>
			&nbsp;&nbsp;2.&nbsp;&nbsp;
			每&nbsp;
			<layout:select key="曠職頻率單位" name="Form1Datas" 
						   property="EHF030108T0_08" styleId="EHF030108T0_08"
						   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listFREQ" 
							    property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;
			累計曠職&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<layout:number styleClass="DATAS" key="曠職扣費基準" 
						   property="EHF030108T0_09" styleId="EHF030108T0_09" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;
			<layout:select key="曠職扣費基準單位" name="Form1Datas" 
						   property="EHF030108T0_10" styleId="EHF030108T0_10"
						   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="list_EHF030108T0_10" 
							    property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;&nbsp;
			扣獎金
			&nbsp;&nbsp;
			<layout:number styleClass="DATAS" key="曠職扣費金額" 
						   property="EHF030108T0_11" styleId="EHF030108T0_11" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;元

		</layout:cell>
		
	</layout:grid>
	
	
	<logic:equal name="SHOW" value="add">
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	<layout:cell styleClass="DATAS" >
		&nbsp;&nbsp;4.&nbsp;&nbsp;
			每&nbsp;
			<layout:select key="請假頻率單位" name="Form1Datas" 
						   property="EHF030108T1_03" styleId="EHF030108T1_03" 
						   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listFREQ"     property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;
			累計申請假別&nbsp;&nbsp;
			
			<layout:select key="請假假別" name="Form1Datas" 
						   property="EHF030108T1_02" styleId="EHF030108T1_02"
						   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="list_EHF030108T1_02" property="item_id" labelProperty="item_value" />
			</layout:select>
			
			
			<layout:number styleClass="DATAS" key="請假扣費基準" 
						   property="EHF030108T1_04" styleId="EHF030108T1_04" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;
			<layout:select key="請假扣費基準單位" name="Form1Datas" 
						   property="EHF030108T1_05" styleId="EHF030108T1_05"
						   styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="list_EHF030108T0_06"  property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;&nbsp;
			扣獎金
			&nbsp;&nbsp;
			<layout:number styleClass="DATAS" key="請假扣費金額" 
						   property="EHF030108T1_06" styleId="EHF030108T1_06" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;元
			
		
				<layout:cell styleClass="DATAS">
					<layout:image alt="新增明細" mode="D,D,D"  name="btnimage?text=button.addDetailData&type=t" property="EHF030108M1" reqCode="handleDetailDataForm" onclick="ctrlDetail('add', 'EHF030108T1');" >></layout:image>
				</layout:cell>
				<br>
		</layout:cell>
		<layout:collection name="Form1Datas" property="EHF030108T1_LIST" emptyKey="沒有資料列" indexId="index"  width="100%" height="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			
			<layout:collectionItem style="TEXT-ALIGN: CENTER" title="刪除" 
									   url="EHF030108M1.do?reqCode=handleDetailDataForm&Detail_formAction=delete&Detail_formType=EHF030108T1" 
									   paramId="EHF030108T0_01,EHF030108T1_01,EHF030108T1_02,EHF030108T1_03" 
									   paramProperty="EHF030108T0_01,EHF030108T1_01,EHF030108T1_02,EHF030108T1_03" 
									   onclick="return confirmDelData('您確定要刪除加班資料嗎?');" >刪除
				</layout:collectionItem>
			<layout:collectionItem property="EHF030108T1_03_TXT" style="TEXT-ALIGN: CENTER" title="請假頻率單位" />
			<layout:collectionItem property="EHF030108T1_02_TXT" style="TEXT-ALIGN: CENTER" title="請假假別" />
			<layout:collectionItem property="EHF030108T1_04" style="TEXT-ALIGN: CENTER" title="請假扣費基準"/>
			<layout:collectionItem property="EHF030108T1_05_TXT" style="TEXT-ALIGN: CENTER" title="請假扣費基準單位"/>
			<layout:collectionItem property="EHF030108T1_06" style="TEXT-ALIGN: CENTER" title="請假扣費金額"/>
		</layout:collection>
		
		</layout:grid>
	</logic:equal>
	
	<layout:notMode value="create" >
	<logic:equal name="collection" value="show">
	
	<layout:notMode value="inspect">
	
	</layout:notMode>
	
	</logic:equal>
	</layout:notMode>
	
	<layout:notMode value="create" >
	<logic:equal name="button" value="query">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>
	</layout:notMode>
	
</layout:form>