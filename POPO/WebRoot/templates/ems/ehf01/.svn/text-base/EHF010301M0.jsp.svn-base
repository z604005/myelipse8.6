<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.hr.forms.EHF010301M0F" %>
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
function chkDeptID(){
	if(document.getElementById("EHF010300T0_04").value == "" || document.getElementById("EHF010300T0_04").value == null ){
		alert("請先選擇部門組織!!");
		return false;
	}else{
		return true;
	}
}
</script>


<layout:form action="EHF010301M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="假別時數清單">


	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"
						  reqCode="queryForm" property="EHF010301M0" ></layout:image>
			<layout:image alt="列印" mode="D,D,D" name="btnimage?text=button.print&type=t" reqCode="print" property="EHF010301M0" confirm="您確定要列印資料嗎?" ></layout:image>
			  
	</logic:notEqual>

	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>

			<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
				
	<%--	人資用的按鈕			--%>

		<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF010300T0_04" styleId="EHF010300T0_04" size="10" mode="H,H,H" maxlength="20" name="Form1Datas" />
		<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="EHF010300T0_04_SHOW" styleId="EHF010300T0_04_SHOW"  size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF010301M0F" 
				id="EHF010300T0_04,EHF010300T0_04_SHOW,EHF010300T0_04_TXT" 
				lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03" 
				table="EHF000200T0"
				fieldAlias="系統代碼,部門代號,部門名稱" 
				fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03"															
				others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
			/>
			<layout:text layout="false" property="EHF010300T0_04_TXT" styleId="EHF010300T0_04_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
		</layout:text>
		<layout:text styleClass="DATAS" tooltip="姓名" key="姓名" property="EHF010300T0_05" styleId="EHF010300T0_05" size="12" mode="H,H,H" maxlength="20" name="Form1Datas" />			
		<layout:text styleClass="DATAS" tooltip="姓名" key="姓名" property="EHF010300T0_05_SHOW" styleId="EHF010300T0_05_SHOW" size="12" mode="E,E,I" maxlength="16" name="Form1Datas" >
			<sp:lov 	
				form="EHF010301M0F" 
				id="EHF010300T0_05,EHF010300T0_05_SHOW,EHF010300T0_05_TXT" 
				lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
				table="EHF010100T6"
				leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
				fieldAlias="系統代碼,工號,姓名" 
				fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
				parentId="EHF010100T6_02" 
				parentField="window.EHF010301M0F.EHF010300T0_04.value" 
				others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' " 
				beforerun="chkDeptID()"																	
			/>
			<layout:text layout="false" property="EHF010300T0_05_TXT" styleId="EHF010300T0_05_TXT" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
		</layout:text>
	

		
			</layout:grid>


	

	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF010301M0F EHF010301m0f =(EHF010301M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) EHF010301m0f.getEHF010301T0_LIST();
		String strTmp = "";
		
	%>
		<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
	
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF010301T0_LIST" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
						   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
			<layout:collectionItem name="bean1" title="選取">
				<center>
				<%
			
				if (item_index < list.size()){
					int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i
					EHF010301M0F FORM=(EHF010301M0F)list.get(i);
					strTmp = FORM.getEHF010300T0_01();					
					item_index++;
				%>
						<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
				<%
				}
				%>
				</center>
			</layout:collectionItem>
		
			<layout:collectionItem property="EHF010300T0_04_TXT" style="TEXT-ALIGN: CENTER" title="單位" />
			<layout:collectionItem property="EHF010300T0_05_TXT" style="TEXT-ALIGN: CENTER" title="姓名" />
			<layout:collectionItem property="EHF010300T0_02" style="TEXT-ALIGN: CENTER" title="年度" />
			<layout:collectionItem property="EHF010300T0_06" style="TEXT-ALIGN: CENTER" title="假別名稱" />
		    <layout:collectionItem property="EHF010300T0_07" style="TEXT-ALIGN: CENTER" title="休假時數 "/>
			<layout:collectionItem property="EHF010300T0_08" style="TEXT-ALIGN: CENTER" title="剩餘時數" />
			<layout:collectionItem property="EHF010300T0_09" style="TEXT-ALIGN: CENTER" title="使用期限" />
			

		</layout:collection>
	
	</layout:pager>
	
	</logic:equal>
</layout:form>
<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />
