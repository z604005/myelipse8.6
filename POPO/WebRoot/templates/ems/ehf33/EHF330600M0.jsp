<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.popo.forms.EHF330600M0F" %>
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
//chooseOne()函式，參數為觸發該函式的元素本身
function ALL(obj) {
	//變數checkItem為checkbox的集合
	 var checkboxs = document.getElementsByName('checkId_boss'); 
    for(var i=0;i<checkboxs.length;i++){
    	checkboxs[i].checked = obj.checked;
    	} 
}
</script>

<layout:form action="EHF330600M0.do" reqCode="" width="100%" styleClass="TITLE" enctype="multipart/form-data" method="post" key="客戶名牌標籤">
	
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF330600M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="列印" mode="H,D,D" name="btnimage?text=button.print&type=t" property="EHF330600M0" reqCode="print" confirm="您確定要列印資料嗎?">
			</layout:image>
		</logic:equal>
<%--		<logic:notEqual name="DisplayFileName" value="" >--%>
<%--			<layout:image alt="下載檔案" mode="H,D,D" name="btnimage?text=下載檔案&type=t" onclick="opendownloadfile(); return false;" ></layout:image>--%>
<%--		</logic:notEqual>--%>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />

	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
	
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2015" endYear="2030"  key="送餐日期" size="10"  name="Form1Datas" property="EHF330600T0_10" styleClass="DATAS" />
			
		<layout:text styleClass="DATAS" tooltip="孕婦系統編號" key="孕婦系統編號" property="EHF310100T0_01_01" styleId="EHF310100T0_01_01" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="孕婦檔案編號" key="孕婦檔案編號" property="EHF310100T0_01_02" styleId="EHF310100T0_01_02" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="孕婦檔案編號" key="孕婦檔案編號" property="EHF310100T0_03_01" styleId="EHF310100T0_03_01" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="孕婦姓名" key="孕婦姓名" property="EHF310100T0_01_03" styleId="EHF310100T0_01_03" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" onchange="return cls1();">
			<sp:lov 	
				form="EHF330600M0F" 
				id="EHF310100T0_01_01,EHF310100T0_01_02,EHF310100T0_01_03,EHF310100T0_03_01" 
				lovField="EHF310100T0_01,EHF310100T0_02,EHF310100T0_04,EHF310100T0_03" 
				table="EHF310100T0"
				fieldAlias="孕婦系統編號,孕婦檔案編號,孕婦姓名,櫃號" 
				fieldName="EHF310100T0_01,EHF310100T0_02,EHF310100T0_04,EHF310100T0_03"									
				others=" AND EHF310100T0_34 = '${compid}' "
				mode="E,E,F"
				/>
							
			
		</layout:text>		
								 
			 
	
		
		<%--<layout:text styleClass="DATAS" key="櫃號" mode="E,E,I"  size="3"  name="Form1Datas" property="EHF330600T0_03_B" styleClass="DATAS"  >
			&nbsp;~&nbsp;
			<layout:text styleClass="DATAS" key="櫃號" mode="E,E,I" layout="false" size="3" key="" name="Form1Datas" property="EHF330600T0_03_E" styleClass="DATAS"  />
		</layout:text>--%>

	


	</layout:grid>
	
	
	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF330600M0F ehf330600m0f =(EHF330600M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf330600m0f.getEHF330600_C();
		String strTmp = "";
		
	%>
		<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
	
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="999" >
	
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF330600_C" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
						   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			<layout:collectionItem title='<input type="checkbox" name="check" onclick="ALL(this);"> 全選 '>
				<center>
				<%
			
				if (item_index < list.size()){
					int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i
					EHF330600M0F FORM=(EHF330600M0F)list.get(i);
					strTmp = FORM.getEHF330600T0_01();					
					item_index++;
				%>
						<input type="checkbox" name="checkId_boss" value="<%=strTmp%>"  >
				<%
				}
				%>
				</center>
			</layout:collectionItem>
		
			
			<layout:collectionItem property="EHF330600T0_03" style="TEXT-ALIGN: CENTER" title="櫃號" />
			<layout:collectionItem property="EHF330600T0_04" style="TEXT-ALIGN: CENTER" title="產婦姓名" />
			<layout:collectionItem property="EHF330600T0_21" style="TEXT-ALIGN: CENTER" title="行動電話" />
			<layout:collectionItem property="EHF330600T0_31" style="TEXT-ALIGN: CENTER" title="住址" />
			

		</layout:collection>
		
	</layout:pager>
	
	</logic:equal>
	
	
	
	
</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />