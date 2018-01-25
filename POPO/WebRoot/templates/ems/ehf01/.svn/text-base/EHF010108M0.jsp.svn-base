<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.hr.forms.EHF010108M0F" %>
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
function chooseOne(cb){
	//先取得同name的chekcBox的集合物件
	var obj = document.getElementsByName("checkId");
	for (i=0; i<obj.length; i++){
		//判斷obj集合中的i元素是否為cb，若否則表示未被點選
		if (obj[i]!=cb)	obj[i].checked = false;
		//若是 但原先未被勾選 則變成勾選；反之 則變為未勾選
		else	obj[i].checked = cb.checked;
		//若要至少勾選一個的話，則把上面那行else拿掉，換用下面那行
		//else obj[i].checked = true;
	}
}
</script>

<layout:form action="EHF010108M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="部門資料查詢">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"
						  reqCode="queryForm" property="EHF010108M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"
						  reqCode="addDataForm" property="EHF010108M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">

		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t"  
						  reqCode="editDataForm" property="EHF010108M0" ></layout:image>
<%--			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"--%>
<%--						  reqCode="delForm" property="EHF010108M0" --%>
<%--						  confirm="您確定要刪除資料嗎?" ></layout:image>--%>
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:text styleClass="DATAS" key="上層部門" property="HR_UpDepartmentSysNo" styleId="HR_UpDepartmentSysNo" size="20" mode="E,E,I" maxlength="200" name="Form1Datas" >				
			<sp:lov 	form="EHF010108M0F" 
						id="HR_UpDepartmentSysNo,HR_UpDepartmentName" 
						lovField="HR_DepartmentSysNo,HR_DepartmentName,HR_DepartmentNo,HR_DepartmentLevel" 
						table="EHF010108T0"
						fieldAlias="系統代號,部門名稱,部門代號,層級" 
						fieldName="HR_DepartmentSysNo,HR_DepartmentName,HR_DepartmentNo,HR_DepartmentLevel"									
						others=" AND HR_CompanySysNo = '${compid}' "
			/>
			<layout:text layout="false" property="HR_UpDepartmentName" styleId="HR_UpDepartmentName" size="20" mode="R,R,R" maxlength="200" name="Form1Datas" />
		</layout:text>
		<layout:space styleClass="DATAS"/>
		<layout:text styleClass="DATAS" key="部門名稱" property="HR_DepartmentName" styleId="HR_DepartmentName" size="20" mode="E,E,I" maxlength="200" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="部門代號" property="HR_DepartmentNo" styleId="HR_DepartmentNo" size="20" mode="E,E,I" maxlength="200" name="Form1Datas" />
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="成立日期" size="10"  name="Form1Datas" property="EHF010108T0_02" styleClass="DATAS"/>
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I"  startYear="2010" endYear="2025"  key="結束日期" size="10"  name="Form1Datas" property="EHF010108T0_03" styleClass="DATAS"/>
	</layout:grid>
	
<%--	<logic:equal name="collection" value="show">--%>
		<%
			int item_index = 0;
			EHF010108M0F ehf010108m0f =(EHF010108M0F)request.getAttribute("Form1Datas");
			ArrayList list = (ArrayList) ehf010108m0f.getEHF010108T0_LIST();
			String strTmp = "";
		
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
		
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF010108T0_LIST" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
						   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
			<layout:collectionItem name="bean1" title="選取">
				<center>
				<%
			
				if (item_index < list.size()){
					int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i
					Map FORM=(Map)list.get(i);
					strTmp = FORM.get("HR_DepartmentSysNo").toString();					
					item_index++;
				%>
						<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
				<%
				}
				%>
				</center>
			</layout:collectionItem>
		
			<layout:collectionItem property="HR_DepartmentNo" style="TEXT-ALIGN: CENTER" title="部門代號" />
			<layout:collectionItem property="HR_DepartmentName" style="TEXT-ALIGN: CENTER" title="部門名稱" />

		</layout:collection>
		
	</layout:pager>
<%--	</logic:equal>--%>

</layout:form>