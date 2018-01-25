<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF331300M0F" %>
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
	function D2(){
	if ($("input[name='EHF331300T0_10']").val() != ""){
	　	if ($("input[name='EHF331300T0_11']").val() < $("input[name='EHF331300T0_10']").val()){
			$("input[name='EHF331300T0_10']").val($("input[name='EHF331300T0_11']").val());
		}
	}
	return true;
	}
	function D1(){
	if ($("input[name='EHF331300T0_11']").val() != ""){
	　	if ($("input[name='EHF331300T0_11']").val() < $("input[name='EHF331300T0_10']").val()){
			$("input[name='EHF331300T0_11']").val($("input[name='EHF331300T0_10']").val());
		}
	}
	return true;
	}
</script>

<layout:form action="EHF331300M0.do" reqCode="" width="100%" styleClass="TITLE" enctype="multipart/form-data" method="post" key="用餐客戶明細報表">
	
	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF331300M0"   reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>
		<logic:notEqual name="DisplayFileName" value="" >
			<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" ></layout:image>
		</logic:notEqual>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />

	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
	
	
		
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="1960" endYear="2035"  key="送餐日期" size="10"  name="Form1Datas" property="EHF331300T0_10" styleClass="DATAS" onchange="return D1();" >
			&nbsp;~&nbsp;
			<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" layout="false" size="10" startYear="1960" endYear="2035"  key="" name="Form1Datas" property="EHF331300T0_11" styleClass="DATAS"  onchange="return D2();" />
		</layout:date>

		<layout:number styleClass="DATAS" key="櫃號" mode="E,E,I"  size="3"  name="Form1Datas" property="EHF331300T0_03_B" styleClass="DATAS"  >
			&nbsp;~&nbsp;
			<layout:text styleClass="DATAS" key="櫃號" mode="E,E,I" layout="false" size="3" key="" name="Form1Datas" property="EHF331300T0_03_E" styleClass="DATAS"  />
		</layout:number>


	

	</layout:grid>
	
	

	
	
	
	
</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />