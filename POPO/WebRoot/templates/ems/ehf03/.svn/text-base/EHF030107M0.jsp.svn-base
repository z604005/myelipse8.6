<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.salary.forms.EHF030107M0F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<script>
 function chkCondition(){
	
	//當前端沒有勾選資料時  跳出警告視窗
	var c = document.getElementsByName("checkId"); 
	for(i=0;i<c.length;i++)
	{
  	 if(c[i].checked == true )
   		{
       		var v  = c[i].value;
        	break;
   		}
	}
	if(v=="" || v==null ){
		alert("請先選擇要刪除的資料!!");
		return false;
	}else{
			
		return true;
	}
}
</script>

<layout:form action="EHF030107M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="加班費規則設定">
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF030107M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="addDataForm" property="EHF030107M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
<%--			<layout:image alt="儲存" 		mode="H,D,H" name="btnimage?text=儲存&type=t"   		reqCode="saveDataForm" property="EHF030107M0" ></layout:image>--%>
<%--			<layout:image alt="回查詢畫面" 	mode="D,D,D" name="btnimage?text=回查詢畫面&type=t" 	reqCode="init" property="EHF030107M0" ></layout:image>--%>
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t"  	reqCode="editDataForm" property="EHF030107M0" ></layout:image>
			<layout:image alt="刪除" 	mode="H,D,H" name="btnimage?text=button.delete&type=t"		reqCode="delForm" property="EHF030107M0" 
						  onclick="var agree=chkCondition();if (agree) return confirm('您確定要刪除資料嗎?') ;if (!agree) return false;" ></layout:image>
		</logic:equal>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:text styleClass="DATAS" key="加班規則代碼" property="EHF030107T0_02" styleId="EHF030107T0_02" size="30" mode="E,E,I" maxlength="20" name="Form1Datas" />
					 
		<layout:text styleClass="DATAS" key="加班規則名稱" property="EHF030107T0_03" styleId="EHF030107T0_03" size="30" mode="E,E,I" maxlength="30" name="Form1Datas" />
		
<%--		<layout:cell styleClass="DATAS" />--%>
		
		<logic:notEqual name="button" value="edit" >
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
		</logic:equal>
		
	</layout:grid>

	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF030107M0F ehf030107m0f =(EHF030107M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf030107m0f.getEHF030107T0_LIST();
		String strTmp = "";
		
	%>
	<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
		<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	<layout:collection emptyKey="沒有資料列"  name="Form1Datas" 
					   property="EHF030107T0_LIST" 
					   id="bean1" indexId="index"
					   selectId="" selectProperty="" selectName=""  
					   selectType=""
					   width="100%"  
				       styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
		<layout:collectionItem name="bean1" title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i  新增於20131101  BY賴泓錡
				Map FORM = (Map) list.get(i);
				strTmp = ((String) FORM.get("EHF030107T0_01"))+"";
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		
		<layout:collectionItem property="EHF030107T0_02" style="TEXT-ALIGN: CENTER" title="加班規則代號" />
		<layout:collectionItem property="EHF030107T0_03" style="TEXT-ALIGN: CENTER" title="加班規則名稱" />
		<layout:collectionItem property="EHF030107T0_09" style="TEXT-ALIGN: CENTER" title="是否啟用" />

	</layout:collection>
	</layout:pager> 
	</logic:equal>

</layout:form>

