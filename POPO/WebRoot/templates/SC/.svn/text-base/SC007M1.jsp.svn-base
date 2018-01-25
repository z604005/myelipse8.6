<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>


<layout:form action="SC007.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="訊息廣播管理${action}" focus="SC0070_02">
	<layout:row>
		<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" mode="F,F,F"></layout:image>
		<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="F,F,F"></layout:image>
		<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f" mode="F,F,F"></layout:image>
		<!-- 以JSTL的方式，由Bean內部指定呼叫FM0102的哪一個method -->
		<layout:image alt="取消" name="btnimage?text=button.cancel&type=t" disabledName="btnimage?text=button.cancel&type=f" policy="all" mode="D,D,D" reqCode="cancel"></layout:image>
		<layout:image alt="執行" name="btnimage?text=button.execute&type=t" disabledName="btnimage?text=button.execute&type=f" policy="all" mode="D,D,F" reqCode="${BUTTON_TYPE}"></layout:image>
		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
			<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f" mode="H,D,D" reqCode="delData" property="SC007" policy="del" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</logic:notEqual>
	</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:tabs styleClass="FORM" width="100%">
	<layout:tab key="設定" width="100">
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="訊息編號:" name="Form1Datas" property="SC0070_01" styleId="SC0070_01" styleClass="DATAS10" mode="H,H,H" />
		<layout:text size="50" maxlength="100" tooltip="◎訊息標題:" key="訊息標題:" name="Form1Datas" property="SC0070_02" styleId="SC0070_02" styleClass="DATAS10" mode="E,E,I" onkeydown="nextFiled()" />
		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
			<layout:textarea rows="4" size="50" maxlength="200" tooltip="◎訊息內容:" key="訊息內容:" name="Form1Datas" property="SC0070_03" styleId="SC0070_03" styleClass="DATAS10" mode="E,E,I" />
		</logic:notEqual>
		<logic:equal name="BUTTON_TYPE" value="doQueryDatas">
			<layout:text size="50" maxlength="200" tooltip="◎訊息內容:" key="訊息內容:" name="Form1Datas" property="SC0070_03" styleId="SC0070_03" styleClass="DATAS10" mode="E,E,I" />
		</logic:equal>

		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
			<layout:checkbox key="給全部人:" name="Form1Datas" property="SC0070_04" styleId="SC0070_04" styleClass="DATAS10" mode="E,E,I" onkeydown="nextFiled()" />
		</logic:notEqual>
		<layout:checkbox key="啟用:" name="Form1Datas" property="SC0070_07" styleId="SC0070_07" styleClass="DATAS10" mode="E,E,I" onkeydown="nextFiled()" />



		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
		<layout:grid cols="3" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
<%--			<layout:date size="10" startYear="2010" endYear="2100" patternKey="yyyy/MM/dd" key="開始時間:" name="Form1Datas" property="SC0070_05" styleClass="DATAS11" mode="E,E,I" onkeydown="nextFiled()" />--%>
			<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2010" endYear="2025"  key="開始時間:" size="10"  name="Form1Datas" property="SC0070_05" styleClass="DATAS11" onkeydown="nextFiled()"/>
			<layout:select key="時" name="Form1Datas" property="SC0070_05_HH" styleId="SC0070_05_HH" styleClass="DATAS11" mode="E,E,I">
				<layout:options collection="listSC0070_05_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			<layout:select key="分" name="Form1Datas" property="SC0070_05_MM" styleId="SC0070_05_MM" styleClass="DATAS11" mode="E,E,I">
				<layout:options collection="listSC0070_05_MM" property="item_id" labelProperty="item_value" />
			</layout:select>


<%--			<layout:date size="10"startYear="2010" endYear="2100" patternKey="yyyy/MM/dd"  key="結束時間:" name="Form1Datas" property="SC0070_06" styleClass="DATAS11" mode="E,E,I" onkeydown="nextFiled()" />--%>
			<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="E,E,I"  startYear="2010" endYear="2025"  key="結束時間:" size="10"  name="Form1Datas" property="SC0070_06" styleClass="DATAS11" onkeydown="nextFiled()"/>
			<layout:select key="時" name="Form1Datas" property="SC0070_06_HH" styleId="SC0070_06_HH" styleClass="DATAS11" mode="E,E,I">
				<layout:options collection="listSC0070_05_HH" property="item_id" labelProperty="item_value" />
			</layout:select>
			<layout:select key="分" name="Form1Datas" property="SC0070_06_MM" styleId="SC0070_06_MM" styleClass="DATAS11" mode="E,E,I">
				<layout:options collection="listSC0070_05_MM" property="item_id" labelProperty="item_value" />
			</layout:select>
		</layout:grid>
		</logic:notEqual>

		<layout:text size="50" maxlength="50" key="備　　註:" name="Form1Datas" property="SC0070_08" styleId="SC0070_08" styleClass="DATAS10" mode="E,E,I" />

		<logic:equal name="BUTTON_TYPE" value="saveData">
			<layout:text styleClass="DATAS10" key="版本編號" property="VERSION" styleId="VERSION" name="Form1Datas" tooltip="版本編號" mode="I,I,I" />
		</logic:equal>
	</layout:grid>

	<logic:equal name="BUTTON_TYPE" value="saveData">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text key="資料建立日期" styleClass="LOGDATA" property="DATE_CREATE" styleId="DATE_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode="I,I,I" />
		</layout:grid>
	</logic:equal>
	</layout:tab>
		<layout:tab key="群組清單" width="100">
	<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
		<layout:cell>
					<layout:collection width="100%" name="Form1Datas" property="SC0071" styleId="SC0071" indexId="index" styleClass="COLLECTION" styleClass2="COLLECTION_2" height="400">
						<layout:collectionItem title="選取">
							<center>
								<layout:checkbox name="Form1Datas" property="SC0071[${index}].CHECKED" styleId="SC0071[${index}].CHECKED" layout="false" />
							</center>
						</layout:collectionItem>
						<layout:collectionItem title="使用者編號">
							<layout:text name="Form1Datas" property="SC0071[${index}].SC0071_02" styleId="SC0071[${index}].SC0071_02" layout="false" mode="I,I,I" />
						</layout:collectionItem>
						<layout:collectionItem title="使用者名稱">
							<layout:text name="Form1Datas" property="SC0071[${index}].SC0030_04" styleId="SC0071[${index}].SC0030_04" layout="false" mode="I,I,I" />
						</layout:collectionItem>
						<layout:collectionItem title="備註">
							<layout:text name="Form1Datas" property="SC0071[${index}].SC0071_03" styleId="SC0071[${index}].SC0071_03" layout="false" />
						</layout:collectionItem>
					</layout:collection>
		</layout:cell>
	</logic:notEqual>
	</layout:tab>
	</layout:tabs>
</layout:form>
