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
	function fbutton(reqCode) {
		SC010F.elements['reqCode'].value=reqCode;
		SC010F.submit();
	}
</SCRIPT>
	<layout:form action="SC010.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="單位資料維護" >
		<layout:row>
				<layout:row>
					<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" reqCode="queryForm" property="SC010" policy="query"></layout:image>
					<layout:image alt="新增" mode="D,F,D" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" reqCode="addDataForm" property="SC010" policy="add"></layout:image>
					<layout:image alt="修改" mode="F,D,D" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f"  reqCode="editDataForm" property="SC010" policy="modify"></layout:image>
					<layout:image alt="取消" mode="D,D,D" reqCode="cancel" name="btnimage?text=button.cancel&type=t" disabledName="btnimage?text=button.cancel&type=f"></layout:image>					
					<layout:image alt="刪除" mode="F,D,D" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f"  property="SC010" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
<%--					<layout:image mode="D,D,D" name="btnimage?text=瀏覽&type=t" disabledName="btnimage?text=瀏覽&type=f" property="SC_UNITM"--%>
<%--						onclick="return browse();">瀏覽</layout:image>	--%>
				<%--		onclick="return openpopup2(this.form, 'browse.do?reqCode=browse&from=SC_UNITM&form=SC010F', '700', '500');">瀏覽</layout:image>		--%>
<%--				        <sp:browse from="SC_UNITM " form="SC010F" width="700" height="500" others=" AND SC_UNITM_12='${compid}' "  />--%>
				</layout:row>
		</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		 <input type=hidden name="LastKey1"  value="<layout:write layout="false" name="Form1Datas" property="SC_UNITM_01"/>" >
			<layout:text  key="單位代碼"  name="Form1Datas" property="SC_UNITM_01" styleId="SC_UNITM_01" tooltip="◎單位代碼"  mode= "E,E,I" styleClass="DATAS" size="10" maxlength="10">
				<sp:lov id="SC_UNITM_01,SC_UNITM_02,SC_UNITM_08,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					form="SC010F"
					lovField="SC_UNITM_01,SC_UNITM_02,SC_UNITM_08,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					fieldAlias="單位代碼,單位名稱,負責人,版本編號,資料建立日期,最後異動日期,資料建立人員,最後異動人員"
					fieldName="SC_UNITM_01,SC_UNITM_02,SC_UNITM_08,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					table="SC_UNITM"
					others="AND SC_UNITM_12='${compid}'"
					changescript="fbutton('queryDatas')" />
			</layout:text>
			<layout:text  key="單位名稱" name="Form1Datas" property="SC_UNITM_02" styleId="SC_UNITM_02" tooltip="◎單位名稱"   mode= "E,E,I" styleClass="DATAS" size="15" maxlength="15"/>
			<layout:text  key="負責人"       name="Form1Datas" property="SC_UNITM_08" styleId="SC_UNITM_08" tooltip="◎負責人"  mode= "E,E,I" styleClass="DATAS" size="11" maxlength="11"/>
			<layout:text  tooltip="版本編號" key="版本編號:" name="Form1Datas" property="VERSION" styleId="VERSION" styleClass="DATAS" mode="I,I,I" onkeydown="nextFiled()" size="14" maxlength="14"/>
		</layout:grid>
		<layout:space/>
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text key="資料建立日期" styleClass="LOGDATA" property="DATE_CREATE" styleId="DATE_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode="I,I,I" />
		</layout:grid>
		
	</layout:form>

