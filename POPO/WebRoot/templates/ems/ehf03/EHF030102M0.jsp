<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.salary.forms.EHF030102M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<layout:form action="EHF030102M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="薪資項目金額設定">
	<input type="hidden" id="chk_type" value="${chk_type}" />
	
	<layout:row>
		<logic:equal name="button" value="init">
<%--			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=查詢&type=t"  reqCode="queryForm" property="EHF030102M0" ></layout:image>--%>
			<layout:image alt="儲存" mode="D,D,H" name="btnimage?text=button.save&type=t"  reqCode="addDataForm" property="EHF030102M0" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="init" property="EHF030102M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="edit">
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveData" property="EHF030102M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF030102M0"  
						  confirm="您確定要刪除資料嗎?" ></layout:image>
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="init" property="EHF030102M0" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF030102M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="init_add" property="EHF030102M0" ></layout:image>
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t"  reqCode="editDataForm" property="EHF030102M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF030102M0" 
						  confirm="您確定要刪除資料嗎?" ></layout:image>
<%--			<layout:image alt="回查詢畫面" mode="D,D,D" name="btnimage?text=回查詢畫面&type=t"  reqCode="init" property="EHF030102M0" ></layout:image>--%>
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="薪資計算公式序號" property="EHF030102T0_01" styleId="EHF030102T0_01" size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
		<logic:notEqual name="button" value="edit" >
			
			<layout:text styleClass="DATAS" key="組織單位內碼" property="EHF030102T0_02_ID" styleId="EHF030102T0_02_ID" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF030102T0_02" styleId="EHF030102T0_02" 
						 size="10" mode="E,E,I" maxlength="16" name="Form1Datas" >
				<sp:lov 	form="EHF030102M0F" 
							id="EHF030102T0_02_ID,EHF030102T0_02,EHF030102T0_02_TXT" 
							lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03,EHF000200T0_06" 
							table="EHF000200T0"
							fieldAlias="系統代號,部門代號,部門名稱,層級" 
							fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03,EHF000200T0_06"									
							others=" AND HR_CompanySysNo = '${compid}' "
							/>
				<layout:text layout="false" property="EHF030102T0_02_TXT" styleId="EHF030102T0_02_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			</layout:text>
			
			<layout:text key="薪資項目" name="Form1Datas" property="EHF030102T0_03" styleId="EHF030102T0_03"
					     mode="E,E,I" size="20" maxlength="20" styleClass="DATAS"  />
			
<%--			<layout:select key="薪資項目" name="Form1Datas" property="EHF030102T0_03" styleClass="DATAS" mode="E,E,I" >--%>
<%--				<layout:options collection="EHF030102T0_03_list" property="item_id" labelProperty="item_value" />--%>
<%--			</layout:select>--%>
	
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
			
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF030102T0_02" styleId="EHF030102T0_02" 
						 size="10" mode="I,I,I" maxlength="16" name="Form1Datas" >
				<layout:text layout="false" property="EHF030102T0_02_TXT" styleId="EHF030102T0_02_TXT" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />
			</layout:text>
<%--			<layout:text styleClass="DATAS" key="薪資項目" property="EHF030101T0_02" size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />--%>
			<layout:select key="薪資項目" name="Form1Datas" property="EHF030102T0_03" styleId="EHF030102T0_03" styleClass="DATAS" mode="I,I,I" >
				<layout:options collection="EHF030102T0_03_list" property="item_id" labelProperty="item_value" />
			</layout:select>
			
		</logic:equal>
		
		<logic:notEqual name="queryCondition" value="yes">
		
<%--			<layout:radios key="是否納入所得" name="Form1Datas" property="EHF030102T0_04" styleClass="DATAS" mode="E,E,I" cols="2" >--%>
<%--				<layout:options collection="EHF030102T0_04_list" property="item_id" labelProperty="item_value" />--%>
<%--			</layout:radios>--%>
		
<%--			<layout:radios key="類別" name="Form1Datas" property="EHF030102T0_05" styleClass="DATAS" mode="E,E,I" cols="3" >--%>
<%--				<layout:options collection="EHF030102T0_05_list" property="item_id" labelProperty="item_value" />--%>
<%--			</layout:radios>--%>
		
			<layout:number styleClass="DATAS" key="金額" property="EHF030102T0_06" styleId="EHF030102T0_06" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" />
			
			<layout:cell styleClass="DATAS" />
			
<%--			<layout:select key="薪資計算公式" name="Form1Datas" property="EHF030102T0_09" styleClass="DATAS" mode="E,E,I" >--%>
<%--				<layout:options collection="EHF030102T0_09_list" property="item_id" labelProperty="item_value" />--%>
<%--			</layout:select>--%>
	
		</logic:notEqual>
	
	</layout:grid>
	
	
	<logic:notEqual name="queryCondition" value="yes">
	
		<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text styleClass="DATAS" property="EHF030102T0_07" styleId="EHF030102T0_07" key="備註" size="100" maxlength="75" name="Form1Datas"  mode="E,E,I" />
		</layout:grid>
	
	</logic:notEqual>
	
	<logic:equal name="button" value="edit">
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
	
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="300" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				EHF030102M0F FORM=(EHF030102M0F)list.get(item_index);
				strTmp = FORM.getEHF030102T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF030102T0_02" style="TEXT-ALIGN: CENTER" title="部門組織" />
		<layout:collectionItem property="EHF030102T0_03" style="TEXT-ALIGN: CENTER" title="薪資項目" />
<%--		<layout:collectionItem property="EHF030102T0_05" style="TEXT-ALIGN: CENTER" title="類別" />--%>
<%--		<layout:collectionItem property="EHF030102T0_04" style="TEXT-ALIGN: CENTER" title="所得稅別" />--%>
		<layout:collectionItem property="EHF030102T0_06" style="TEXT-ALIGN: CENTER" title="金額" />
		<layout:collectionItem property="EHF030102T0_07" style="TEXT-ALIGN: CENTER" title="備註" />
	</layout:collection>
	</logic:equal>

</layout:form>