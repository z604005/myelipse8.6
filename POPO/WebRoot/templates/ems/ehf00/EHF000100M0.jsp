<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

<layout:form action="EHF000100M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="公司基本資料">

	<layout:row>
			<layout:image alt="儲存" mode="D,D,H" name="btnimage?text=button.save&type=t"  reqCode="saveDataForm" property="EHF000100M0"  onclick="document.getElementById('changeFormType').value='save'" ></layout:image>
			<layout:image alt="修改" mode="H,H,D" name="btnimage?text=button.update&type=t"  reqCode="changeForm" property="EHF000100M0"  onclick="document.getElementById('changeFormType').value='edit'" ></layout:image>			
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
<%--   		 <layout:message styleClass="MESSAGE_ERROR" key="${ErrMSG}" />--%>
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	
		<layout:text styleClass="DATAS" tooltip="轉換FormType" key="轉換FormType" property="changeFormType" styleId="changeFormType" size="16" 
					 maxlength="16" mode="H,H,H" name="Form1Datas"/>
		<layout:text styleClass="DATAS" tooltip="公司系統代碼" property="HR_CompanySysNo" styleId="HR_CompanySysNo" size="16" 
				 	 maxlength="20" mode="H,H,H" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="公司代號" key="label.company.no" property="EHF000100T0_01" styleId="EHF000100T0_01" size="16" 
				 	 maxlength="100" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="統一編號" key="label.company.uni" property="EHF000100T0_02" styleId="EHF000100T0_02" size="16" 
				 	 maxlength="10" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="公司名稱(中)" key="label.company.name" property="EHF000100T0_03" styleId="EHF000100T0_03" size="16" 
				 	 maxlength="20" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="公司名稱(英)" key="label.company.english_name" property="EHF000100T0_04" styleId="EHF000100T0_04" size="16" 
				 	 maxlength="20" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="公司簡稱" key="label.company.short_name" property="EHF000100T0_05" styleId="EHF000100T0_05" size="16" 
				 	 maxlength="50" mode="E,E,I" name="Form1Datas" />		
		<layout:date calendarType="datepicker" startYear="1990" endYear="2020" patternKey="yy/mm/dd" tooltip="成立日期" key="成立日期" size="10"
					 name="Form1Datas" property="EHF000100T0_06" styleClass="DATAS" mode="E,E,I" />
		<layout:text styleClass="DATAS" tooltip="負責人" key="負責人" property="EHF000100T0_07" styleId="EHF000100T0_07" size="16" 
				 	 maxlength="10" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="聯絡人" key="聯絡人" property="EHF000100T0_08" styleId="EHF000100T0_08" size="16" 
				 	 maxlength="10" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="登記地址(中)" key="登記地址(中)" property="EHF000100T0_09" styleId="EHF000100T0_09" size="16" 
				 	 maxlength="50" mode="E,E,I" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="營業地址(中)" key="營業地址(中)" property="EHF000100T0_11" styleId="EHF000100T0_11" size="16" 
				 	 maxlength="50" mode="E,E,I" name="Form1Datas" />		 	 
		<layout:text styleClass="DATAS" tooltip="登記地址(英)" key="登記地址(英)" property="EHF000100T0_10" styleId="EHF000100T0_10" size="16" 
				 	 maxlength="50" mode="E,E,I" name="Form1Datas" />		 	 		 	 
		<layout:text styleClass="DATAS" tooltip="營業地址(英)" key="營業地址(英)" property="EHF000100T0_12" styleId="EHF000100T0_12" size="16" 
				 	 maxlength="50" mode="E,E,I" name="Form1Datas" />		 	 
		<layout:number styleClass="DATAS" tooltip="電話(代表號)" key="電話(代表號)" property="EHF000100T0_13_TAC" styleId="EHF000100T0_13_TAC" size="5" 
				 	 maxlength="5" mode="E,E,I" name="Form1Datas" >
			<layout:number styleClass="DATAS" tooltip="" property="EHF000100T0_13_PN" styleId="EHF000100T0_13_PN" size="16" 
				 	 maxlength="20" mode="E,E,I" name="Form1Datas" layout="false" />		 	
		</layout:number>			 	 
		<layout:number styleClass="DATAS" tooltip="電話二" key="電話二" property="EHF000100T0_14_TAC" styleId="EHF000100T0_14_TAC" size="5" 
				 	 maxlength="5" mode="E,E,I" name="Form1Datas" >
			<layout:number styleClass="DATAS" tooltip="" property="EHF000100T0_14_PN" styleId="EHF000100T0_14_PN" size="16" 
				 	 maxlength="20" mode="E,E,I" name="Form1Datas" layout="false" />
		</layout:number>		 	 		 	 
		<layout:number styleClass="DATAS" tooltip="傳真" key="傳真" property="EHF000100T0_15_TAC" styleId="EHF000100T0_15_TAC" size="5" 
				 	 maxlength="5" mode="E,E,I" name="Form1Datas" >
			<layout:number styleClass="DATAS" tooltip="" property="EHF000100T0_15_PN" styleId="EHF000100T0_15_PN" size="16" 
				 	 maxlength="20" mode="E,E,I" name="Form1Datas" layout="false" />
		</layout:number>		 	 
		<layout:text styleClass="DATAS" tooltip="電子郵件" key="電子郵件" property="EHF000100T0_16" styleId="EHF000100T0_16" size="40" 
				 	 maxlength="50" mode="E,E,I" name="Form1Datas" />		 	 

	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	
		<layout:textarea styleClass="DATAS" tooltip="公司簡介" key="label.company.desc" property="EHF000100T0_17" styleId="EHF000100T0_17" rows="3" cols="80" 
					 maxlength="50" mode="E,E,I" name="Form1Datas" />
					 
	</layout:grid>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" name="Form1Datas" mode= "I,N,I" />
		<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" name="Form1Datas" mode= "I,N,I" />
		<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" name="Form1Datas" mode= "I,N,I" />
		<layout:text  key="最後異動日期" styleClass="LOGDATA" property="HR_LastUpdateDate" name="Form1Datas" mode= "I,N,I" />
	</layout:grid>

</layout:form>