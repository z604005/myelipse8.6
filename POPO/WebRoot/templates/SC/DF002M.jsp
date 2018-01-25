<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>


<layout:form action="DF002.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="DATAGRID樣板(表頭表身)" focus="DF0020_01">
	<layout:row>
		<layout:image alt="查詢" accesskey="q" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" mode="D,D,D" reqCode="queryForm" property="DF002" policy="query"></layout:image>
		<layout:image alt="新增" accesskey="w" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="D,D,D" reqCode="addDataForm" property="DF002" policy="add"></layout:image>
		<layout:image alt="修改" accesskey="e" name="btnimage?text=修改&type=t" disabledName="btnimage?text=修改&type=f" mode="F,D,D" reqCode="editDataForm" property="DF002" policy="modify"></layout:image>
		<layout:image alt="清除" accesskey="r" name="btnimage?text=清除&type=t" disabledName="btnimage?text=清除&type=f" mode="D,D,D" reqCode="init"></layout:image>
		<layout:image alt="刪除" accesskey="d" name="btnimage?text=刪除&type=t" disabledName="btnimage?text=刪除&type=f" mode="D,D,D" property="DF002" policy="del" reqCode="delData" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
<%--		<layout:image mode="D,D,D" name="btnimage?text=瀏覽&type=t" disabledName="btnimage?text=瀏覽&type=f" property="DF002" onclick="return openpopup2(this.form, 'browse.do?reqCode=browse&from=DF0020&form=DF002F', '700', '500');">瀏覽</layout:image>--%>
		<layout:message key="${DETAIL}" />		
	</layout:row>
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<input type=hidden name="LastKey1" value="<layout:write layout="false" name="Form1Datas" property="DF0020_01"/>">
		<layout:text  size="5" maxlength="5" tooltip="◎區處別"  isRequired="${Required}" key="區處別:" name="Form1Datas" property="DF0020_01"
			styleClass="DATAS"  mode="E,E,I" onchange="CloseDetailButton();"  onkeydown="nextFiled()"/>
		<input type=hidden name="LastKey2" value="<layout:write layout="false" name="Form1Datas" property="DF0020_02"/>">
		<layout:text size="10" maxlength="10" tooltip="◎站所代碼" 
		 isRequired="${Required}" key="站所代碼:" name="Form1Datas" property="DF0020_02"
		 styleClass="DATAS"  mode="E,E,I" onchange="CloseDetailButton();" onkeydown="nextFiled()"/>
		<layout:text size="30" tooltip="備　　註" maxlength="100" key="備　　註:" name="Form1Datas" property="DF0020_03" styleClass="DATAS" mode="E,E,I" />
		<layout:text size="14" maxlength="14" tooltip="版本編號" key="版本編號:" name="Form1Datas" property="VERSION" styleClass="DATAS" mode="I,I,I"  />
		
	</layout:grid>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="資料建立日期" styleClass="LOGDATA" property="DATE_CREATE" name="Form1Datas" mode="I,I,I" />
		<layout:text key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" name="Form1Datas" mode="I,I,I" />
		<layout:text key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode="I,I,I" />
		<layout:text key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode="I,I,I" />
	</layout:grid>

</layout:form>

<sp:datagrid 	action="DF002.do"
				rsperpage="10" 
				pager="true"
				dbgridwidth="800" 
				dbgridheight="350" 
				trheight="25" 
				onloadinit="false" 
				fontsize="16" 
				sqlstatement="SELECT DF0020_01, DF0020_02, DF0021_03, DF0021_04 ,a.VERSION FROM SPOS.DF0021 a left join SPOS.DF0020 b on DF0021_01=DF0020_01 and DF0021_02=DF0020_02  WHERE 1=1 " 
				sqlother="order by DF0021_01,DF0021_02,DF0021_03"
				keyid="DF0020_01,DF0020_02"
				>
	<sp:datagridItemText name="表頭KEY1" id="DF0020_01" width="100" disp_type="H" />
	<sp:datagridItemText name="表頭KEY2" id="DF0020_02" width="100" disp_type="H"/>
	<sp:datagridItemLov name="程式代碼" id="DF0021_03"  
						sqlstatement="SELECT SC0020_01,SC0020_02,SC0020_03,SC0020_04 FROM SC0020  WHERE 1=1 and SC0020_01 like '%S%' " 
						sqlother="order by SC0020_01"
						lovfield_name="程式代碼,程式名稱,程式路徑,備註"
						lovfield_id="SC0020_01,SC0020_02,SC0020_03,SC0020_04"
						cfield_name="程式代碼"  
						sqlkeyid=""  
						disp_type="E" 
						width="200"
						isRequired="true"
						iskey="true"
						dg_keyvalue_cname="" />
	<sp:datagridItemText name="備註" id="DF0021_04" width="300" disp_type="E"  />
	<sp:datagridItemText name="版本" id="VERSION" width="100" disp_type="R"  />


</sp:datagrid>

