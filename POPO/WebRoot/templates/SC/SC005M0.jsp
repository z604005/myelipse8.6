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
		SC005F.elements['reqCode'].value=reqCode;
		SC005F.submit();
	}
</SCRIPT>
	<layout:form action="SC005.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="序號維護" >
		<layout:row>
				<layout:row>
					<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" reqCode="queryForm" property="SC005" policy="query"></layout:image>
					<layout:image alt="新增" mode="D,F,D" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" reqCode="addDataForm" property="SC005" policy="add"></layout:image>
					<layout:image alt="儲存修改內容" mode="F,D,D" name="btnimage?text=button.save&type=t" disabledName="btnimage?text=button.save&type=f"  reqCode="editDataForm" property="SC005" policy="modify"></layout:image>
					<layout:image alt="取消" mode="D,D,D" reqCode="cancel" name="btnimage?text=button.cancel&type=t" disabledName="btnimage?text=button.cancel&type=f"></layout:image>					
					<layout:image alt="刪除" mode="F,D,D" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f"  property="SC005" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
<%--					<layout:image mode="D,D,D" name="btnimage?text=瀏覽&type=t" disabledName="btnimage?text=瀏覽&type=f" property="SC005"--%>
<%--						onclick="return browse();">瀏覽</layout:image>	
<%--								onclick="return openpopup2(this.form, 'browse.do?reqCode=browse&from=SC0050&form=SC005F&others= and SC0050_06=${compid}', '700', '500' );">瀏覽</layout:image>	--%>
<%--						<sp:browse from="SC0050 " form="SC005F" width="700" height="500" others=" AND SC0050_06='${compid}' "  />--%>
				</layout:row>
				
		</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		 <input type=hidden name="LastKey1"  value="<layout:write layout="false" name="Form1Datas" property="SC0050_01"/>" >
			<layout:text  key="TABLE名稱"  name="Form1Datas" property="SC0050_01" styleId="SC0050_01" tooltip="◎TABLE名稱"   mode= "E,E,I" styleClass="DATAS" size="20" maxlength="15">
				<sp:lov id="SC0050_01,SC0050_02,SC0050_03,SC0050_04,SC0050_05,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					form="SC005F"
					lovField="SC0050_01,SC0050_02,SC0050_03,SC0050_04,SC0050_05,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					fieldAlias="TABLE名稱,COLUMN名稱,序號,重置時間,備註,版本編號,資料建立日期,最後異動日期,資料建立人員,最後異動人員"
					fieldName="SC0050_01,SC0050_02,SC0050_03,SC0050_04,SC0050_05,VERSION,DATE_CREATE,DATE_UPDATE,USER_CREATE,USER_UPDATE"
					table="SC0050"
					others="AND SC0050_06='${compid}'"
					changescript="fbutton('queryDatas')" />
			</layout:text>
			<layout:text  key="COLUMN名稱" name="Form1Datas" property="SC0050_02" styleId="SC0050_02" tooltip="◎COLUMN名稱"  mode= "E,E,I" styleClass="DATAS" size="20" maxlength="15"/>
			<layout:text  key="序號"       name="Form1Datas" property="SC0050_03" styleId="SC0050_03" tooltip="◎序號"       mode= "E,E,I" styleClass="DATAS" size="20" maxlength="11"/>
			<layout:text  key="重置時間"    name="Form1Datas" property="SC0050_04" styleId="SC0050_04" tooltip="重置時間"    mode= "E,E,I" styleClass="DATAS" size="8" maxlength="8"/>
			<layout:text  key="備註"       name="Form1Datas" property="SC0050_05" styleId="SC0050_05" tooltip="備註"        mode= "E,E,I" styleClass="DATAS" size="25" maxlength="25"/>
			<layout:text  tooltip="版本編號" key="版本編號:" name="Form1Datas" property="VERSION" styleId="VERSION" styleClass="DATAS" mode="I,I,I" onkeydown="nextFiled()" size="14" maxlength="14"/>
		</layout:grid>
		<layout:space/>
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text key="資料建立日期" styleClass="LOGDATA" property="DATE_CREATE" styleId="DATE_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode="I,I,I" />
		</layout:grid>
		<layout:row width=""><layout:cell>
		重置時間:<br>
		&nbsp&nbsp 0或1 代表沒有更新時間，序號一直加總<br>
		&nbsp&nbsp 2010 四碼 代表每年歸零<br>
		&nbsp&nbsp 201001 六碼 代表每月歸零<br>
		&nbsp&nbsp 20100101 八碼 代表每日歸零<br>
		</layout:cell></layout:row>
	</layout:form>

