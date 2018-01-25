<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<SCRIPT>
	function select_one(elem,flag)
	{
		if(flag==false)
			document.all[elem].checked=true;
		else
		{
			if(document.all[elem].checked==true) document.all[elem].checked=false;
			else document.all[elem].checked=true;
		}
			
	}
	
	function fbutton(reqCode) {
		SC003F.elements['reqCode'].value=reqCode;
		SC003F.submit();
	}
	function chkDeptID(){
		if(document.getElementById("SC0030_07").value == "" || document.getElementById("SC0030_07").value == null ){
			alert("請先選擇單位代碼!!");
			return false;
		}else{
			return true;
		}
	}
</SCRIPT>
<layout:form action="SC003B.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="使用者管理" focus="SC0030_01">
	<layout:row>
			<layout:image alt="查詢" accesskey="q" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" mode="D,D,D" reqCode="queryForm" property="SC003B" policy="query"></layout:image>
			<layout:image alt="新增" accesskey="w" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="D,D,D" reqCode="addDataForm" property="SC003B" policy="add"></layout:image>
			<layout:image alt="儲存修改內容" accesskey="e" name="btnimage?text=button.save&type=t" disabledName="btnimage?text=button.save&type=f" mode="F,D,D" reqCode="editDataForm" property="SC003B" policy="modify"></layout:image>
			<layout:image alt="清除" accesskey="r" name="btnimage?text=button.reset&type=t" disabledName="btnimage?text=button.reset&type=f" mode="D,D,D" reqCode="init"></layout:image>
			<layout:image alt="刪除" accesskey="d" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f" mode="F,D,D" property="SC003B" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
<%--			<layout:image mode="D,D,D" name="btnimage?text=瀏覽&type=t" disabledName="btnimage?text=瀏覽&type=f" property="SC003B" onclick="return browse();">瀏覽</layout:image>--%>
<%--			<sp:browse from="SC0030 " form="SC003F" width="700" height="500" others=" AND SC0030_14='${compid}' "  />--%>
	</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:tabs  styleClass="FORM">
	<layout:tab key="使用者資料" width="100">
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<input type="hidden" name="LastKey1" value="<layout:write layout="false" name="Form1Datas" property="SC0030_01"/>">
		<layout:suggest suggestEncoding="UTF-8" size="10" maxlength="10" tooltip="◎label.user.id" suggestAction="/getSuggestions.do?reqCode=getSuggest&col=SC0030_01&from=SC0030" key="label.user.id" name="Form1Datas" property="SC0030_01"
			styleId="SC0030_01" styleClass="DATAS" suggestCount="10" mode="E,E,I" >
			<sp:lov id="SC0030_01,SC0030_03,SC0030_04,SC0030_07,SC0030_05,SC0030_08,SC0030_09,SC0030_10,SC0030_12,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					form="SC003F"
					lovField="SC0030_01,SC0030_03,SC0030_04,SC0030_07,SC0030_05,SC0030_08,SC0030_09,SC0030_10,SC0030_12,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					fieldAlias="使用者代碼,員工內碼,使用者姓名,單位代碼,啟用,登入錯誤次數,密碼重設時間,電子郵件帳號,備註,版本編號,資料建立日期,最後異動日期,資料建立人員,最後異動人員"
					fieldName="SC0030_01,SC0030_03,SC0030_04,SC0030_07,SC0030_05,SC0030_08,SC0030_09,SC0030_10,SC0030_12,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					table="SC0030"
					others="AND SC0030_14='${compid}' AND SC0030_01 NOT IN ('spadmin') "
					changescript="fbutton('queryDatas')" />
		</layout:suggest>
		<layout:space styleClass="DATAS"/>
<%--		<layout:text size="10" maxlength="10" tooltip="◎身分證字號" key="身分證字號:" name="Form1Datas" property="SC0030_03" styleId="SC0030_03" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" >--%>
<%--			<sp:lov id="SC0030_03,SC0030_04"--%>
<%--					form="SC003F"--%>
<%--					lovField="EHF010100T0_I,EHF010100T0_05"--%>
<%--					fieldAlias="身分證字號,姓名"--%>
<%--					fieldName="EHF010100T0_I,EHF010100T0_05"--%>
<%--					table="EHF010100T0"--%>
<%--					others="AND HR_CompanySysNo='${compid}' "/>--%>
<%--		</layout:text>--%>
		<layout:password size="20" maxlength="20" key="使用者密碼:" name="Form1Datas" property="SC0030_02" styleId="SC0030_02" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		<layout:password size="20" maxlength="20" key="密碼確認:" name="Form1Datas" property="SC0030_02_CHK" styleId="SC0030_02_CHK" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
<%--		<layout:text size="20" maxlength="50" tooltip="◎label.user.name" key="label.user.name" name="Form1Datas" property="SC0030_04" styleId="SC0030_04" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />--%>
<%--		<layout:text size="10" maxlength="10" tooltip="◎單位代碼" key="單位代碼:" name="Form1Datas" property="SC0030_07" styleId="SC0030_07" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" onkeydown="return getcodename(this);">--%>
		<layout:text size="10" maxlength="200" tooltip="◎單位代碼" key="單位代碼:" name="Form1Datas" property="SC0030_07" styleId="SC0030_07" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()">
<%--			<sp:lov form="SC003F" id="SC0030_07,SA_SALAM_S_txt" lovField="SC_UNITM_01,SC_UNITM_02" table="SC_UNITM" others="AND SC_UNITM_12='${compid}'  " fieldAlias="單位代碼,名稱" fieldName="SC_UNITM_01,SC_UNITM_02" />--%>
			<sp:lov id="SC0030_07,SA_SALAM_S_txt"
					form="SC003F"
					lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"
					fieldAlias="系統內碼,部門名稱,部門代號"
					fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"
					table="EHF000200T0"
					others="AND HR_CompanySysNo='${compid}' AND EHF000200T0_06 > '1' "/>
			<input type="text" name="SA_SALAM_S_txt" id="SA_SALAM_S_txt" value="" readonly>
		</layout:text>
		
		
		<layout:text size="10" maxlength="10" tooltip="◎身分證字號" key="label.user.name" name="Form1Datas" property="SC0030_03" styleId="SC0030_03" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" >
			<sp:lov 	
				id="SC0030_03,SC0030_04" 
				form="SC003F" 
				lovField="EHF010100T0_01,EHF010100T0_05,EHF010100T0_02" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
				fieldAlias="系統內碼,姓名,員工工號" 
				fieldName="EHF010100T0_01,EHF010100T0_05,EHF010100T0_02"
				parentId="EHF010100T6_02" 
				parentField="window.SC003F.SC0030_07.value" 
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
				beforerun="chkDeptID()"																	
			/>
			<layout:text layout="false" size="20" maxlength="50" tooltip="◎label.user.name" key="label.user.name" name="Form1Datas" property="SC0030_04" styleId="SC0030_04" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		</layout:text>
		<sp:getcodename thisfield="SC0030_07" table="EHF000200T0" cfield="EHF000200T0_03" returnfield="SA_SALAM_S_txt" tablekey="EHF000200T0_01" keyvalue="SC0030_07" others=" AND HR_CompanySysNo='${compid}'  " />
		<layout:select key="label.group.used_flag" name="Form1Datas" property="SC0030_05" styleId="SC0030_05" styleClass="DATAS" mode="E,E,I"  onkeydown="nextFiled()" >
			<layout:option key="label.flag.yes" value="Y"/>
			<layout:option key="label.flag.no" value="N"/>
		</layout:select>
		<layout:text size="3" maxlength="3" tooltip="label.userpwdchange.error_time" key="label.userpwdchange.error_time" name="Form1Datas" property="SC0030_08" styleId="SC0030_08" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" >
		<br>
		<span class="DATAS">密碼重設時間:</span>
<%--		<layout:write layout="false" name="Form1Datas" property="SC0030_09" styleClass="DATAS" />--%>
		<layout:text property="SC0030_09" styleId="SC0030_09" layout="false" mode="R,R,R" styleClass="DATAS"></layout:text>
		</layout:text>
		<layout:text tooltip="◎label.contact.email" onchange="checkValue(this, 'SC0030_10','EMAIL',true);" size="20" maxlength="100" key="label.contact.email" name="Form1Datas" property="SC0030_10" styleId="SC0030_10" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		<layout:text size="30" tooltip="備　　註" maxlength="50" key="備　　註:" name="Form1Datas" property="SC0030_12" styleId="SC0030_12" styleClass="DATAS" mode="E,E,I" />
		<layout:text size="14" maxlength="14" tooltip="版本編號" key="版本編號:" name="Form1Datas" property="VERSION" styleId="VERSION" styleClass="DATAS" mode="I,I,I"  />
<%--		<layout:text size="10" maxlength="10" tooltip="◎群組代碼" key="群組代碼:" name="Form1Datas" property="SC0031_02" styleId="SC0031_02" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" onkeydown="return getcodename(this);">--%>
<%--			<sp:lov form="SC0031F" id="SC0031_02,SC0010_02" lovField="SC_UNITM_01,SC_UNITM_02" table="SC_UNITM" others="AND SC_UNITM_12='${compid}'  " fieldAlias="群組代碼,名稱" fieldName="SC0010_01,SC0010_02" />--%>
<%--			<input type="text" name="SC0010_02" id="SC0010_02" value="" readonly>--%>
<%--		</layout:text>--%>
<%--		<sp:getcodename thisfield="SC0031_02" table="SC0010" cfield="SC0010_02" returnfield="SC0010_02" tablekey="SC0010_01" keyvalue="SC0031_02" others=" AND SC0010_04'${compid}'  " />--%>
		<layout:space styleClass="DATAS"/>
		
	
	</layout:grid>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="資料建立日期" styleClass="LOGDATA" property="DATE_CREATE" styleId="DATE_CREATE" name="Form1Datas" mode="I,I,I" />
		<layout:text key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode="I,I,I" />
		<layout:text key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode="I,I,I" />
		<layout:text key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode="I,I,I" />
	</layout:grid>
	</layout:tab>
	<layout:tab key="群組清單" width="100">
				<layout:collection width="700" height="400" name="Form1Datas" property="SC0031" styleId="SC0031" indexId="index" styleClass="COLLECTION" styleClass2="COLLECTION_2">
					<layout:collectionItem title="選取">
						<center>
							<layout:checkbox name="Form1Datas" property="SC0031[${index}].CHECKED" styleId="SC0031[${index}].CHECKED" mode="E,E,E" layout="false" />
						</center>
					</layout:collectionItem>

					<layout:collectionItem title="群組代碼">
						<layout:text style="bgcolor:red" name="Form1Datas" property="SC0031[${index}].SC0031_02" styleId="SC0031[${index}].SC0031_02" layout="false" mode="I,I,I" onmousedown="select_one('SC0031[${index}].CHECKED',true);" />
					</layout:collectionItem>
					<layout:collectionItem title="群組名稱">
						<layout:text name="Form1Datas" property="SC0031[${index}].SC0010_02" styleId="SC0031[${index}].SC0010_02" layout="false" mode="I,I,I" onmousedown="select_one('SC0031[${index}].CHECKED',true);" />
					</layout:collectionItem>
					<layout:collectionItem title="備註">
						<layout:text name="Form1Datas" property="SC0031[${index}].SC0031_03" styleId="SC0031[${index}].SC0031_03" layout="false" mode="I,I,I" onchange="select_one('SC0031[${index}].CHECKED',false);" />
					</layout:collectionItem>
				</layout:collection>
	</layout:tab>
	</layout:tabs>
</layout:form>



