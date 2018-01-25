<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
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
				SC003F.elements['confirmb'].value='';
				SC003F.submit();
		}
	
</SCRIPT>
<layout:form action="ChangePassword.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="使用者密碼修改" focus="DATA03">
	<layout:row>
		<layout:row>
<%--			<layout:image alt="查詢" accesskey="q" name="btnimage?text=查詢&type=t" disabledName="btnimage?text=查詢&type=f" mode="D,D,D" reqCode="queryForm" property="SC003B" policy="query"></layout:image>--%>
<%--			<layout:image alt="新增" accesskey="w" name="btnimage?text=新增&type=t" disabledName="btnimage?text=新增&type=f" mode="D,D,D" reqCode="addDataForm" property="SC003B" policy="add"></layout:image>--%>
			<layout:image alt="修改密碼"  name="btnimage?text=button.update.password&type=t" disabledName="btnimage?text=button.update.password&type=f" mode="D,D,D"  reqCode="changePassword" property="ChangePassword"  ></layout:image>
<%--			<layout:image alt="清除" accesskey="r" name="btnimage?text=清除&type=t" disabledName="btnimage?text=清除&type=f" mode="D,D,D" reqCode="init">--%>
<%--			</layout:image>--%>
<%--			<layout:image alt="刪除" accesskey="d" name="btnimage?text=刪除&type=t" disabledName="btnimage?text=刪除&type=f" mode="F,D,D" property="SC003B" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>--%>
<%--			<layout:image mode="D,D,D" name="btnimage?text=瀏覽&type=t" disabledName="btnimage?text=瀏覽&type=f" property="SC003B" onclick="return browse();">瀏覽</layout:image>--%>
<%--			<sp:browse from="SC0030 " form="SC003F" width="700" height="500" others=" AND SC0030_14='${compid}' "  />--%>
				
		</layout:row>
	</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>

	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text size="20" maxlength="50" isRequired="${Required}" tooltip="◎使用者代碼" key="使用者代碼:" name="Form1Datas" property="DATA01" styleClass="DATAS" mode="I,I,I" onkeydown="nextFiled()" />
		<layout:text size="20" maxlength="50" isRequired="${Required}" tooltip="使用者姓名" key="使用者姓名:" name="Form1Datas" property="DATA02" styleClass="DATAS" mode="I,I,I" onkeydown="nextFiled()" />
		<layout:password size="20" maxlength="20" key="使用者密碼:" name="Form1Datas" property="DATA03" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		<layout:password size="20" maxlength="20" key="密碼確認:" name="Form1Datas" property="DATA04" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
				
	</layout:grid>

</layout:form>