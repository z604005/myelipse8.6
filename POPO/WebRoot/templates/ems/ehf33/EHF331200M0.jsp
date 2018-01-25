<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF331200M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script>


	function cls1(){
	　	var day=getToday();
		if ($("input[name='EHF310500T0_04_01']").val() == ""){
			if ($("input[name='EHF310500T0_04_02']").val() < day){
				$("input[name='EHF310500T0_04_01']").val($("input[name='EHF310500T0_04_02']").val());
			}else{
				$("input[name='EHF310500T0_04_01']").val(day);
			}
		}else if ($("input[name='EHF310500T0_04_02']").val() < $("input[name='EHF310500T0_04_01']").val()){
				$("input[name='EHF310500T0_04_01']").val($("input[name='EHF310500T0_04_02']").val());
		}
		
	return true;
	}
	
	function cls2(){
　		var day=getToday();
		if ($("input[name='EHF310500T0_05_01']").val() == ""){
			$("input[name='EHF310500T0_05_01']").val(day);
		}
		if ($("input[name='EHF310500T0_05_02']").val() < $("input[name='EHF310500T0_05_01']").val()){
			$("input[name='EHF310500T0_05_01']").val($("input[name='EHF310500T0_05_02']").val());
		}
	return true;
	}
	
	function getToday(){
		var Today=new Date();
		var day1=Today.getFullYear();
		var day2=(Today.getMonth()+1);
		var day3=Today.getDate();
		day2=padLeft(day2,"2");
		day3=padLeft(day3,"2");
　		var day=day1+ "/" + day2 + "/" + day3;
		return day;
	}
	
	function padLeft(str,lenght){
		//左邊補0
		if(str.length >= lenght)
		return str;
		else
		return padLeft("0" +str,lenght);
}
	

</script>

<layout:form action="EHF331200M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="贈品明細報表">

	<layout:row>
		<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" property="EHF331200M0"   reqCode="print" confirm="您確定要列印資料嗎?"  ></layout:image>
		<logic:notEqual name="DisplayFileName" value="" >
			<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" ></layout:image>
		</logic:notEqual>
	</layout:row>
	
	<layout:row>
	若未有任何查詢條件,將列印由今天起所有贈品明細<br/>
		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>

	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		
		
		<layout:text styleClass="DATAS" tooltip="孕婦系統編號" key="孕婦系統編號" property="EHF310100T0_01_01" styleId="EHF310100T0_01_01" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="孕婦檔案編號" key="孕婦檔案編號" property="EHF310100T0_01_02" styleId="EHF310100T0_01_02" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="孕婦姓名" key="孕婦姓名" property="EHF310100T0_01_03" styleId="EHF310100T0_01_03" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return cls1();">
			<sp:lov 	form="EHF331200M0F" 
							id="EHF310100T0_01_01,EHF310100T0_01_02,EHF310100T0_01_03" 
							lovField="EHF310100T0_01,EHF310100T0_02,EHF310100T0_04,EHF310100T0_03" 
							table="EHF310100T0"
							fieldAlias="孕婦系統編號,孕婦檔案編號,孕婦姓名,櫃號" 
							fieldName="EHF310100T0_01,EHF310100T0_02,EHF310100T0_04,EHF310100T0_03"									
							others=" AND EHF310100T0_34 = '${compid}' "
							mode="E,E,F"
							/>
							
			
		</layout:text>
		
		
		<layout:text styleClass="DATAS" key="櫃號" property="EHF310100T0_03" styleId="EHF310100T0_03" size="10" mode="I,I,I" maxlength="10" name="Form1Datas"  >
			<layout:select styleClass="DATAS" key="櫃號" name="Form1Datas" property="EHF310100T0_03_01" styleId="EHF310100T0_03_01"  mode="E,E,I"  layout="false">
				<layout:options collection="listEHF310100T0_03_01" property="item_id" labelProperty="item_value" />
			</layout:select>
			&nbsp;&nbsp;&nbsp;~
			<layout:select styleClass="DATAS" key="櫃號" name="Form1Datas" property="EHF310100T0_03_02" styleId="EHF310100T0_03_02"  mode="E,E,I"  layout="false">
				<layout:options collection="listEHF310100T0_03_02" property="item_id" labelProperty="item_value" />
			</layout:select>
		</layout:text>
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2015" endYear="2030"  key="領取日期" name="Form1Datas" property="EHF310500T0_04_01" styleClass="DATAS"  >
		~
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2015" endYear="2030"  key="領取日期" name="Form1Datas" property="EHF310500T0_04_02" styleClass="DATAS" layout="false" onchange="return cls1();" />
		</layout:date>
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2015" endYear="2030"  key="開單日期" name="Form1Datas" property="EHF310500T0_05_01" styleClass="DATAS"  >
		~
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2015" endYear="2030"  key="開單日期" name="Form1Datas" property="EHF310500T0_05_02" styleClass="DATAS" layout="false" onchange="return cls2();" />
		</layout:date>
	</layout:grid>
	
</layout:form>
<%--以下方法，在列印時，可以出現遮罩，目前未調整遮罩的CSS，因此先不用   Alvin--%>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />

