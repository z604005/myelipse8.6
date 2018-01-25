<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>


<layout:form action="SC003.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="使用者管理${action}" focus="SC0030_01">

		<layout:text size="10" maxlength="10" key="使用者代碼:" property="HSC0030_01" styleClass="FORM" mode="H,H,H" onkeydown="nextFiled()" />
		<layout:text size="10" maxlength="10" key="身分證字號:" property="HSC0030_03" styleClass="FORM" mode="H,H,H" onkeydown="nextFiled()" />
		<layout:text size="20" maxlength="50" key="使用者姓名:" property="HSC0030_04" styleClass="FORM" mode="H,H,H" onkeydown="nextFiled()" />

	<layout:row>
		<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" mode="F,F,F"></layout:image>
		<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="F,F,F"></layout:image>
		<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f" mode="F,F,F"></layout:image>
		<!-- 以JSTL的方式，由Bean內部指定呼叫FM0102的哪一個method -->
		<layout:image alt="取消" name="btnimage?text=button.cancel&type=t" disabledName="btnimage?text=button.cancel&type=f" policy="all" mode="D,D,D" reqCode="cancel"></layout:image>
		<layout:image alt="執行" name="btnimage?text=button.execute&type=t" disabledName="btnimage?text=button.execute&type=f" policy="all" mode="D,D,F" reqCode="${BUTTON_TYPE}"></layout:image>
		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
			<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f" mode="H,D,D" reqCode="delData" property="SC003" policy="del" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</logic:notEqual>
	</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:row>
		<layout:text size="10" maxlength="10" isRequired="${Required}" key="使用者代碼:" name="Form1Datas" property="SC0030_01" styleId="SC0030_01" styleClass="DATAS" mode="${Mode}" onkeydown="nextFiled()" />
		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
			<layout:select key="啟用:" name="Form1Datas" property="SC0030_05" styleId="SC0030_05" styleClass="DATAS" mode="E,E,I"  onkeydown="nextFiled()" >
				<layout:option key="是" value="Y"/>
				<layout:option key="否" value="N"/>
			</layout:select>
		</logic:notEqual>
	</layout:row>
	<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
		<layout:row>
			<layout:password size="20" maxlength="20" key="使用者密碼:" name="Form1Datas" property="SC0030_02" styleId="SC0030_02" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
			<layout:password size="20" maxlength="20" key="密碼確認:" name="Form1Datas" property="SC0030_02_CHK" styleId="SC0030_02_CHK" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		</layout:row>
	</logic:notEqual>
	<layout:row>
		<layout:text size="10" maxlength="10" isRequired="${Required}" key="身分證字號:" name="Form1Datas" property="SC0030_03" styleId="SC0030_03" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
	</layout:row>
	<layout:row>
		<layout:text size="20" maxlength="50" isRequired="${Required}" key="使用者姓名:" name="Form1Datas" property="SC0030_04" styleId="SC0030_04" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
	</layout:row>

	<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
		<logic:notEqual name="BUTTON_TYPE" value="addData">
			<layout:row>
				<layout:text size="3" maxlength="3" key="登入錯誤次數:" name="Form1Datas" property="SC0030_08" styleId="SC0030_08" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
				<layout:column>
					<span class="DATAS">密碼重設時間:<span> <layout:write layout="false" name="Form1Datas" property="SC0030_09" styleClass="DATAS" />
				</layout:column>
			</layout:row>
		</logic:notEqual>
		<layout:row>
			<layout:text onchange="checkValue(this, 'SC0030_10','EMAIL',true);" size="30" maxlength="100" isRequired="${Required}" key="電子郵件帳號:" name="Form1Datas" property="SC0030_10" styleId="SC0030_10" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		</layout:row>
		<layout:row>
			<layout:text size="30" maxlength="50" key="備　　註:" name="Form1Datas" property="SC0030_12" styleId="SC0030_12" styleClass="DATAS" mode="E,E,I" />
		</layout:row>
	</logic:notEqual>

	<logic:equal name="BUTTON_TYPE" value="saveData">
		<layout:row styleClass="FORM">
			<layout:column>
				<span class="FORM">新增者</span>
				<layout:write name="Form1Datas" property="USER_CREATE" layout="false" />
				<span class="FORM">新增時間</span>
				<layout:write name="Form1Datas" property="DATE_CREATE" layout="false" />
				<span class="FORM">修改者</span>
				<layout:write name="Form1Datas" property="USER_UPDATE" layout="false" />
				<span class="FORM">修改時間</span>
				<layout:write name="Form1Datas" property="DATE_UPDATE" layout="false" />
				<span class="FORM">版本</span>
				<layout:text name="Form1Datas" property="VERSION" styleId="VERSION" layout="false" mode="I,I,I" />
			</layout:column>
		</layout:row>
	</logic:equal>
	<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
		<layout:cell>
			<DIV style="OVERFLOW: scroll;  HEIGHT: 200px; ">
				<layout:panel key="群組清單" width="95%" styleClass="FORM">
					<layout:collection width="100%" name="Form1Datas" property="SC0031" styleId="SC0031" indexId="index" styleClass="COLLECTION" styleClass2="COLLECTION_2">
						<layout:collectionItem title="選取">
							<center>
								<layout:checkbox name="Form1Datas" property="SC0031[${index}].CHECKED" styleId="SC0031[${index}].CHECKED" layout="false" />
							</center>
						</layout:collectionItem>
						<layout:collectionItem title="群組代碼">
							<layout:text name="Form1Datas" property="SC0031[${index}].SC0031_02" styleId="SC0031[${index}].SC0031_02" layout="false" mode="I,I,I" />
						</layout:collectionItem>
						<layout:collectionItem title="群組名稱">
							<layout:text name="Form1Datas" property="SC0031[${index}].SC0010_02" styleId="SC0031[${index}].SC0010_02" layout="false" mode="I,I,I" />
						</layout:collectionItem>
						<layout:collectionItem title="備註">
							<layout:text name="Form1Datas" property="SC0031[${index}].SC0031_03" styleId="SC0031[${index}].SC0031_03" layout="false" />
						</layout:collectionItem>
					</layout:collection>
				</layout:panel>
			</DIV>
		</layout:cell>
	</logic:notEqual>
</layout:form>
