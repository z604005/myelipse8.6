<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.spon.utils.sc.forms.SC001F" %>
<%@ page import="com.spon.utils.sc.forms.SC0011F" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>

<script type="text/javascript">
<!--
	function chgChecked(indexnum){
		var chkbln ;
		if (document.all["SC0011[" + indexnum + "].CHECKED"].checked == true)
			chkbln = true ;
		else 
		 	chkbln = false;
		document.all["SC0011[" + indexnum + "].SC0011_03"].checked = chkbln;
		document.all["SC0011[" + indexnum + "].SC0011_04"].checked = chkbln;
		document.all["SC0011[" + indexnum + "].SC0011_05"].checked = chkbln;
		document.all["SC0011[" + indexnum + "].SC0011_06"].checked = chkbln;
		document.all["SC0011[" + indexnum + "].SC0011_07"].checked = chkbln;
	}
	function setBookMark(SC){
		document.location='#ss'+ SC;
	}
//-->
</script>
<layout:form action="SC001.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="群組管理${action}" focus="SC0010_01">
	<layout:row>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	<layout:row>
		<layout:image alt="查詢" name="btnimage?text=button.query&type=t" disabledName="btnimage?text=button.query&type=f" mode="F,F,F"></layout:image>
		<layout:image alt="新增" name="btnimage?text=button.add&type=t" disabledName="btnimage?text=button.add&type=f" mode="F,F,F"></layout:image>
		<layout:image alt="修改" name="btnimage?text=button.update&type=t" disabledName="btnimage?text=button.update&type=f" mode="F,F,F"></layout:image>
		<!-- 以JSTL的方式，由Bean內部指定呼叫FM0102的哪一個method -->
		<layout:image alt="取消" name="btnimage?text=button.cancel&type=t" disabledName="btnimage?text=button.cancel&type=f" policy="all" mode="D,D,D" reqCode="cancel"></layout:image>
		<layout:image alt="執行" name="btnimage?text=button.execute&type=t" disabledName="btnimage?text=button.execute&type=f" policy="all" mode="D,D,F" reqCode="${BUTTON_TYPE}"></layout:image>
		<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
			<layout:image alt="刪除" name="btnimage?text=button.delete&type=t" disabledName="btnimage?text=button.delete&type=f" mode="H,D,D" reqCode="delData" property="SC001" policy="del" onclick="return confirm('您確定要刪除這筆資料嗎?')"></layout:image>
		</logic:notEqual>
	</layout:row>
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text size="3" maxlength="3" tooltip="◎群組代碼:" key="群組代碼:" name="Form1Datas" property="SC0010_01" styleId="SC0010_01" styleClass="DATAS" mode="${Mode}" onkeydown="nextFiled()" />
		<layout:text size="20" maxlength="20" tooltip="◎群組名稱:" key="群組名稱:" name="Form1Datas" property="SC0010_02" styleId="SC0010_02" styleClass="DATAS" mode="E,E,I" onkeydown="nextFiled()" />
		<layout:text size="30" maxlength="50" key="備　　註:" name="Form1Datas" property="SC0010_03" styleId="SC0010_03" styleClass="DATAS" mode="E,E,I" />
		<layout:text styleClass="DATAS" key="版本編號" property="VERSION" styleId="VERSION" name="Form1Datas" tooltip="版本編號" mode="I,I,I" />
	</layout:grid>
	
	<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
		<layout:select property="SC0010_99" styleId="SC0010_99" key="" mode="E,E,E" name="Form1Datas"  tooltip="" onkeydown="nextFiled()" onchange="setBookMark(this.value);">
			<layout:options collection="DATA02_list" property="item_id" labelProperty="item_value" />
		</layout:select>
	</logic:notEqual>
	
	<logic:equal name="Form1Datas" property="SC0010_01" value="12352++2">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text key="資料建立日期" styleClass="LOGDATA" property="DATE_CREATE" styleId="DATE_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode="I,I,I" />
			<layout:text key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode="I,I,I" />
		</layout:grid>
	</logic:equal>
	<logic:notEqual name="BUTTON_TYPE" value="doQueryDatas">
		<layout:cell>
				<%
					String prgGroup="";
					String strTmp = "";
					String strTmp1 = "";
					int ixx=0;
					
					ArrayList list =(ArrayList)((SC001F)request.getAttribute("Form1Datas")).getSC0011();
			%>
					<layout:collection width="" height="" name="Form1Datas" property="SC0011" styleId="SC0011" indexId="index" styleClass="COLLECTION" styleClass2="COLLECTION_2">
						
						<layout:collectionItem title="選取">
						<%
							strTmp1 = "";
							if (ixx < list.size()-2 ){
								if (ixx > 1 ){
									SC0011F FORM=(SC0011F)list.get(ixx+2);
									strTmp = FORM.getSC0020_04() +"";
								}else{
									SC0011F FORM=(SC0011F)list.get(ixx);
							 		strTmp = FORM.getSC0020_04() +"";
								}
							}
							ixx++;
							if (!prgGroup.equals(strTmp)){
						%>		<a name="ss<%=strTmp%>"/>		<%
							}
							prgGroup = strTmp;
						%>	
							<center>
								<layout:checkbox name="Form1Datas" property="SC0011[${index}].CHECKED" styleId="SC0011[${index}].CHECKED" layout="false" onclick="chgChecked(${index});"  />
							</center>
							
						</layout:collectionItem>
						<layout:collectionItem title="程式代碼">
							<layout:text name="Form1Datas" property="SC0011[${index}].SC0011_02" styleId="SC0011[${index}].SC0011_02" layout="false" mode="I,I,I" />
						</layout:collectionItem>
						<layout:collectionItem title="程式名稱">
							<layout:text name="Form1Datas" property="SC0011[${index}].SC0020_02" styleId="SC0011[${index}].SC0020_02" layout="false" mode="I,I,I" />
						</layout:collectionItem>
						<layout:collectionItem title="新增">
							<center>
								<layout:checkbox name="Form1Datas" property="SC0011[${index}].SC0011_03" styleId="SC0011[${index}].SC0011_03" layout="false" />
							</center>
						</layout:collectionItem>
						<layout:collectionItem title="刪除">
							<center>
								<layout:checkbox name="Form1Datas" property="SC0011[${index}].SC0011_04" styleId="SC0011[${index}].SC0011_04" layout="false" />
							</center>
						</layout:collectionItem>
						<layout:collectionItem title="修改">
							<center>
								<layout:checkbox name="Form1Datas" property="SC0011[${index}].SC0011_05" styleId="SC0011[${index}].SC0011_05" layout="false" />
							</center>
						</layout:collectionItem>
						<layout:collectionItem title="查詢">
							<center>
								<layout:checkbox name="Form1Datas" property="SC0011[${index}].SC0011_06" styleId="SC0011[${index}].SC0011_06" layout="false" />
							</center>
						</layout:collectionItem>
						<layout:collectionItem title="列印">
							<center>
								<layout:checkbox name="Form1Datas" property="SC0011[${index}].SC0011_07" styleId="SC0011[${index}].SC0011_07" layout="false" />
							</center>
						</layout:collectionItem>
						<layout:collectionItem title="備註">
							<center>
								<layout:text name="Form1Datas" property="SC0011[${index}].SC0011_08" styleId="SC0011[${index}].SC0011_08" layout="false" />
							</center>
						</layout:collectionItem>
					</layout:collection>
		</layout:cell>
	</logic:notEqual>
</layout:form>
