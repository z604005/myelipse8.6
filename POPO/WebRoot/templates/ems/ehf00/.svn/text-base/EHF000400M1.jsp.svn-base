<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.com.forms.EHF000400M0F" %>
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

<layout:form action="EHF000400M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="公司班別設定">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF000400M1" ></layout:image>
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF000400M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF000400M1" ></layout:image>
    </layout:row>

	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		<layout:text styleClass="DATAS" key="班別序號" property="EHF000400T0_01" styleId="EHF000400T0_01" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="班別代號" property="EHF000400T0_03" styleId="EHF000400T0_03" size="5" mode="E,E,I" maxlength="3" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="班別" property="EHF000400T0_04" styleId="EHF000400T0_04" size="15" mode="E,E,I" maxlength="15" name="Form1Datas" />
		
		<logic:equal name="PAY_BY_HOUR" value="no">
		
		<layout:text styleClass="DATAS" key="上班時間" property="EHF000400T0_05" styleId="EHF000400T0_05" size="8" mode="N,I,I" maxlength="16" name="Form1Datas" >
			&nbsp;
			<layout:select key="上班時間(時)" name="Form1Datas" property="EHF000400T0_05_HH" styleId="EHF000400T0_05_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;:&nbsp;
			<layout:select key="上班時間(分)" name="Form1Datas" property="EHF000400T0_05_MM" styleId="EHF000400T0_05_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
		</layout:text>
		
		<layout:text styleClass="DATAS" key="下班時間" property="EHF000400T0_06" styleId="EHF000400T0_06" size="8" mode="N,I,I" maxlength="16" name="Form1Datas" >
			&nbsp;
			<layout:select key="下班時間(時)" name="Form1Datas" property="EHF000400T0_06_HH" styleId="EHF000400T0_06_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;:&nbsp;
			<layout:select key="下班時間(時)" name="Form1Datas" property="EHF000400T0_06_MM" styleId="EHF000400T0_06_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
		</layout:text>
		
		</logic:equal>
		
		<logic:equal name="PAY_BY_HOUR" value="yes">
		
		<layout:text styleClass="DATAS" key="上班時間" property="EHF000400T0_05" styleId="EHF000400T0_05" size="8" mode="N,I,I" maxlength="16" name="Form1Datas" >
			&nbsp;
			<layout:select key="上班時間(時)" name="Form1Datas" property="EHF000400T0_05_HH" styleId="EHF000400T0_05_HH" styleClass="DATAS" mode="I,I,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;:&nbsp;
			<layout:select key="上班時間(分)" name="Form1Datas" property="EHF000400T0_05_MM" styleId="EHF000400T0_05_MM" styleClass="DATAS" mode="I,I,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
		</layout:text>
		
		<layout:text styleClass="DATAS" key="下班時間" property="EHF000400T0_06" styleId="EHF000400T0_06" size="8" mode="N,I,I" maxlength="16" name="Form1Datas" >
			&nbsp;
			<layout:select key="下班時間(時)" name="Form1Datas" property="EHF000400T0_06_HH" styleId="EHF000400T0_06_HH" styleClass="DATAS" mode="I,I,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;:&nbsp;
			<layout:select key="下班時間(時)" name="Form1Datas" property="EHF000400T0_06_MM" styleId="EHF000400T0_06_MM" styleClass="DATAS" mode="I,I,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
		</layout:text>
		
		</logic:equal>
		
		<layout:text styleClass="DATAS" key="休息時間(起)" property="EHF000400T0_07" styleId="EHF000400T0_07" size="8" mode="N,I,I" maxlength="16" name="Form1Datas" >
			&nbsp;
			<layout:select key="中午休息(起)(時)" name="Form1Datas" property="EHF000400T0_07_HH" styleId="EHF000400T0_07_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;:&nbsp;
			<layout:select key="中午休息(起)(時)" name="Form1Datas" property="EHF000400T0_07_MM" styleId="EHF000400T0_07_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
		</layout:text>
		
		<layout:text styleClass="DATAS" key="休息時間(迄)" property="EHF000400T0_08" styleId="EHF000400T0_08" size="8" mode="N,I,I" maxlength="16" name="Form1Datas" >
			&nbsp;
			<layout:select key="中午休息(起)(時)" name="Form1Datas" property="EHF000400T0_08_HH" styleId="EHF000400T0_08_HH" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;:&nbsp;
			<layout:select key="中午休息(起)(時)" name="Form1Datas" property="EHF000400T0_08_MM" styleId="EHF000400T0_08_MM" styleClass="DATAS" mode="E,E,I" layout="false" >
				<layout:options collection="listEHF000400T0_05_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
		</layout:text>
		
		
<%--		<layout:select key="休假方式" name="Form1Datas" property="EHF000400T0_17" styleId="EHF000400T0_17" styleClass="DATAS" mode="E,E,I"  >--%>
<%--			<layout:options collection="EHF000400T0_17_list" property="item_id" labelProperty="item_value" />--%>
<%--		</layout:select>--%>
		
		
<%--		<layout:space styleClass="DATAS"></layout:space>--%>
		
		<layout:checkbox key="是否記錄中午打卡" name="Form1Datas" property="EHF000400T0_NFLG" styleId="EHF000400T0_NFLG" styleClass="DATAS" mode="N,E,I" />
		
		<layout:select key="組織預設" name="Form1Datas" property="EHF000400T0_09" styleId="EHF000400T0_09" styleClass="DATAS" mode="E,E,I"  >
			<layout:options collection="listEHF000400T0_09" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<layout:select key="時薪班別" name="Form1Datas" property="EHF000400T0_18" styleId="EHF000400T0_18" styleClass="DATAS" mode="E,E,I"  >
			<layout:options collection="listEHF000400T0_18" property="item_id" labelProperty="item_value" />
		</layout:select>
		
		<logic:notEqual name="PAY_BY_HOUR" value="">
			<layout:space styleClass="DATAS"/>
		</logic:notEqual>
		
	</layout:grid>
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">	
		<layout:text styleClass="DATAS" property="EHF000400T0_10" styleId="EHF000400T0_10" key="備註" size="100" maxlength="50" name="Form1Datas"  mode="N,E,I" />
	</layout:grid>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "N,I,I" />
		<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "N,I,I" />
		<layout:text  key="資料版本" 	styleClass="LOGDATA" property="VERSION" 	name="Form1Datas" mode= "N,I,I" />
		<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode= "N,I,I" />
	</layout:grid>

</layout:form>