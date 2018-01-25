<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template" %>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout" %>


    <layout:form action="SC002.do" reqCode=""  width="100%" styleClass="TITLE" method="post" key="程式模組管理${action}" focus="SC0020_01">
		<layout:row>
		<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f"  mode="F,F,F"></layout:image>
		<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f"  mode="F,F,F"></layout:image>
		<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f" mode="F,F,F"></layout:image>
		<!-- 以JSTL的方式，由Bean內部指定呼叫FM0102的哪一個method -->
		<layout:image alt="取消" name="btnimage?text=button.cancel&type=t" disabledName="btnimage?text=button.cancel&type=f" policy="all" mode="D,D,D" reqCode="cancel" ></layout:image>
		<layout:image alt="執行" name="btnimage?text=button.execute&type=t" disabledName="btnimage?text=button.execute&type=f" policy="all" mode="D,D,F" reqCode="${BUTTON_TYPE}" ></layout:image>
		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
			<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f"  mode="H,D,D" reqCode="delData" property="SC002" policy="del" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</logic:notEqual>
	</layout:row>
		<layout:row>
		    <layout:message styleClass="MESSAGE_ERROR" key="${MSG}"/>
   		</layout:row>

			<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
				<layout:text size="20" maxlength="20" tooltip="◎程式代碼:" key="程式代碼:"  name="Form1Datas" property="SC0020_01" styleId="SC0020_01" styleClass="DATAS" mode="${Mode}" onkeydown="nextFiled()" />
				<layout:text size="40" maxlength="40" tooltip="◎程式名稱:" key="程式名稱:"  name="Form1Datas" property="SC0020_02" styleId="SC0020_02" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
				<layout:text tooltip="◎程式路徑:" key="程式路徑:"  name="Form1Datas" property="SC0020_03" styleId="SC0020_03" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
				<layout:text size="40" maxlength="40" tooltip="◎父節點　:" key="父節點　:"  name="Form1Datas" property="SC0020_04" styleId="SC0020_04" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
				<%--<layout:select key="子系統　:" isRequired="${Required}" name="Form1Datas" property="SC0020_04" styleId="SC0020_01" styleClass="DATAS"  mode="E,E,I" onkeydown="nextFiled()">
					<layout:options collection="listSC0020_04" property="item_id" labelProperty="item_value"/>
				</layout:select>--%>
				<layout:text size="5" maxlength="11" key="排列序號:"  	name="Form1Datas" property="SC0020_06" styleId="SC0020_06" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />				
				<layout:text size="30" maxlength="50" key="備　　註:"  	name="Form1Datas" property="SC0020_05" styleId="SC0020_05" styleClass="DATAS" mode="E,E,I"  />				
				
				<logic:equal name="BUTTON_TYPE" value="saveData">
					<layout:text styleClass="DATAS" key="版本編號" property="VERSION" styleId="SC0020_01" name="Form1Datas" tooltip="版本編號" mode="I,I,I" />
					<layout:radios key="是否使用KEY" property="SC0020_07" name="Form1Datas" tooltip="是否使用KEY"  mode= "E,E,I" styleClass="DATAS" cols="2"  onkeydown="nextFiled()">
						<layout:option key="否" value="N"/>
						<layout:option key="是" value="Y"/>
					</layout:radios>
				</logic:equal>
				<logic:equal name="BUTTON_TYPE" value="addData">
					<layout:radios key="是否使用KEY" property="SC0020_07" name="Form1Datas" tooltip="是否使用KEY"  mode= "E,E,I" styleClass="DATAS" cols="2"  onkeydown="nextFiled()">
						<layout:option key="否" value="N"/>
						<layout:option key="昰" value="Y"/>
					</layout:radios>
					<layout:space  styleClass="DATAS" />
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
   	</layout:form>
