<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF330700M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- JavaScript -->
<script type="text/javascript">
function ALL(obj) {
	//變數checkItem為checkbox的集合
	 var checkboxs = document.getElementsByName('checkId'); 
    for(var i=0;i<checkboxs.length;i++){
    	checkboxs[i].checked = obj.checked;
    	} 
}
</script>

<layout:form action="EHF330700M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="客戶送餐開始標籤與用餐結束標籤">
	<layout:row>
		<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t" reqCode="queryForm" property="EHF330700M0" ></layout:image>
		<logic:equal name="button" value="query">
			<layout:image alt="列印" mode="H,D,D" name="btnimage?text=button.print&type=t" reqCode="printTag" property="EHF330700M0" ></layout:image>
		</logic:equal>
	</layout:row>

	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
		<layout:text key="產婦姓名" property="EHF310100T0_04" styleId="EHF310100T0_04" mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="12" maxlength="10">
						 <sp:lov 
						 		form="EHF330700M0F" 
								id="EHF310100T0_04,EHF310100T0_03,EHF310100T0_01" 
								lovField="EHF310100T0_04,EHF310100T0_03,EHF310100T0_01" 
								table="EHF310100T0 "
								fieldAlias="產婦姓名,櫃號,系統編號" 
								fieldName="EHF310100T0_04,EHF310100T0_03,EHF310100T0_01"
								mode="E,E,F"							
								others=" AND EHF310100T0_34 = '${compid}'"
								/> 
		</layout:text>
		<layout:number key="櫃號(小~大)" property="EHF310100T0_03_B" styleId="EHF310100T0_03_B" mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="5" maxlength="5">
					&nbsp;~&nbsp;
					 <layout:number layout="false" property="EHF310100T0_03_E" styleId="EHF310100T0_03_E" mode="E,E,I" name="Form1Datas" styleClass="DATAS" size="5" maxlength="5" />
		 </layout:number>
		 
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="1960" endYear="2030"  
													 key="送餐日期(起)" name="Form1Datas" property="EHF310100T0_10_B" styleClass="DATAS"/>
													 
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="1960" endYear="2030"  
													 key="送餐日期(迄)" name="Form1Datas" property="EHF310100T0_10_E" styleClass="DATAS"/>
	</layout:grid>
	
		<layout:text key="櫃號" property="EHF310100T0_03" styleId="EHF310100T0_03" mode="H,H,H" name="Form1Datas" styleClass="DATAS" 
		 		size="20" maxlength="20"/>	
		 <layout:text key="系統編號" property="EHF310100T0_01" styleId="EHF310100T0_01" mode="H,H,H" name="Form1Datas" styleClass="DATAS" 
		 		size="20" maxlength="20"/>	
		 		
		 		
<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		EHF330700M0F EHF330700m0f =(EHF330700M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) EHF330700m0f.getEHF330700_C();
		String strTmp = "";
	%>

<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%"  maxPageItems="15">
<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF330700_C"  id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
						   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title='<input type="checkbox" name="checkId" onclick="ALL(this);"> 全選 '>
			<center>
			<%
		
			if (item_index < list.size()){
				EHF330700M0F FORM =(EHF330700M0F)list.get(item_index);
				strTmp = FORM.getEHF310100T0_01_C();
				item_index++;
			%>
				<input type="checkbox" name="checkId" value="<%=strTmp%>" >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF310100T0_04_C" style="TEXT-ALIGN: CENTER" title="產婦姓名"  />
		<layout:collectionItem property="EHF310100T0_03_C" style="TEXT-ALIGN: CENTER" title="櫃號" />
		<layout:collectionItem property="EHF310100T0_10_C" style="TEXT-ALIGN: CENTER" title="送餐日期" />
	</layout:collection>
	</layout:pager>
	</logic:equal>
	
</layout:form>

<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />