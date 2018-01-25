<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.salary.forms.EHF030103M1F" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
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
<layout:form action="EHF030103M0.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="健保等級維護">

<%--按鈕顯示  --%>
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<logic:notEqual name="button" value="Import">
				<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"  reqCode="queryForm" property="EHF030103M0" ></layout:image>
				<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"  reqCode="addForm" property="EHF030103M0" ></layout:image>
			</logic:notEqual>
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
<%--			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=儲存&type=t"  reqCode="saveData" property="EHF030103M0" ></layout:image>--%>
<%--			<layout:image alt="回查詢畫面" mode="D,D,D" name="btnimage?text=回查詢畫面&type=t"  reqCode="init" property="EHF030103M0" ></layout:image>--%>
		</logic:equal>
		
		<logic:equal name="button" value="query">
			<layout:image alt="修改明細" mode="H,D,H" name="btnimage?text=button.update&type=t"  reqCode="editForm" property="EHF030103M0" ></layout:image>
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t"  reqCode="delData" property="EHF030103M0"   
			              onclick="var agree=chkCondition();if (agree) return confirm('您確定要刪除資料嗎?') ;if (!agree) return false;" ></layout:image>
		</logic:equal>
			
		<logic:notEqual name="button" value="Import">
			<layout:image alt="健保等級匯入" mode="D,D,D" name="btnimage?text=button.import.healthIns&type=t"  property="EHF030103M0"  onclick="openStrutsLayoutPopup('fileimport'); return false;" />
			<layout:image alt="範例檔下載" mode="D,D,D" name="btnimage?text=button.download.example&type=t" property="EHF030103M0"   reqCode="print_example" confirm="您確定要下載資料嗎?" ></layout:image>
				<logic:notEqual name="DisplayFile" value="" >
					<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=button.download&type=t"   onclick="opendownloadfile(); return false;" ></layout:image>
				</logic:notEqual>
		</logic:notEqual>
		<logic:equal name="button" value="Import">
			<layout:image alt="健保等級匯入" mode="D,D,D" name="btnimage?text=button.import.healthIns&type=t"  property="EHF030103M0"  onclick="openStrutsLayoutPopup('fileimport'); return false;" />
			
			<layout:image alt="範例檔下載" mode="D,D,D" name="btnimage?text=button.download.example&type=t" property="EHF030103M0"   reqCode="print_example" confirm="您確定要下載資料嗎?" ></layout:image>
				<logic:notEqual name="DisplayFile" value="" >
					<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=button.download&type=t"   onclick="opendownloadfile(); return false;" ></layout:image>
				</logic:notEqual>
			
			<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF030103M0" ></layout:image>
		</logic:equal>
			
	</layout:row>

	<%--提示字  --%>
	<layout:row >
		<logic:notEqual name="button" value="Import">
   		 	<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
   		</logic:notEqual>
	</layout:row>
	
	<logic:notEqual name="button" value="Import">	
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
			<layout:text styleClass="DATAS" key="健保等級序號" property="EHF030103T0_01" 			styleId="EHF030103T0_01" 			size="10" mode="H,H,H" maxlength="16" name="Form1Datas" />
		
			<%--新增於20140116 BY賴泓錡           --%>
			<layout:text styleClass="DATAS" key="健保等級代碼" property="EHF030103T0_02" 			styleId="EHF030103T0_02" 			size="15" mode="E,E,I" maxlength="20" name="Form1Datas" />
			<layout:text styleClass="DATAS" key="健保等級名稱" property="EHF030103T0_02_VERSION" 	styleId="EHF030103T0_02_VERSION" 	size="15" mode="E,E,I" maxlength="20" name="Form1Datas" />
	

	<%--修改於20140116 BY賴泓錡           --%>
	
<%--		<layout:select key="年度" name="Form1Datas" property="EHF030103T0_02" styleClass="DATAS" mode="E,E,I" >--%>
<%--				<layout:options collection="EHF030103T0_02_list" property="item_id" labelProperty="item_value" />--%>
<%--		</layout:select>--%>
<%--		--%>
		<logic:notEqual name="button" value="edit" >
		</logic:notEqual>
		
		<logic:equal name="button" value="edit">
		</logic:equal>
		
<%--		<layout:cell styleClass="DATAS" ></layout:cell>--%>
		
	</layout:grid>
	</logic:notEqual>
<%--	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">--%>
<%--		<layout:text styleClass="DATAS" property="DATA03" key="備註" size="100" maxlength="75" name="Form1Datas"  mode="E,E,I" />--%>
<%--	</layout:grid>--%>

	
	<logic:equal name="collection" value="show">
	<%
		int item_index = 0;
		ArrayList list =(ArrayList)request.getAttribute("Form2Datas");
		String strTmp = "";
		
	%>
	
	<layout:collection emptyKey="沒有資料列"  name="Form2Datas" selectId="" selectProperty="" selectName=""  width="100%" height="300" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		<layout:collectionItem title="選取">
			<center>
			<%
		
			if (item_index < list.size()){
				EHF030103M1F FORM=(EHF030103M1F)list.get(item_index);
				strTmp = FORM.getEHF030103T0_01();
				item_index++;
			%>
					<input type="checkbox" name="checkId" value="<%=strTmp%>"  >
			<%
			}
			%>
			</center>
		</layout:collectionItem>
		<layout:collectionItem property="EHF030103T0_02" 			style="TEXT-ALIGN: CENTER" title="健保等級代碼" />
		<layout:collectionItem property="EHF030103T0_02_VERSION" 	style="TEXT-ALIGN: CENTER" title="健保等級名稱" />
		<layout:collectionItem property="EHF030103T0_04" 			style="TEXT-ALIGN: CENTER" title="備註" />
	</layout:collection>
	</logic:equal>

	<logic:equal name="right_collection" value="show">
		
		<layout:row>
			<layout:message styleClass="MESSAGE_ERROR" key="${right_MSG}" /> 
		</layout:row>
		
		<layout:collection emptyKey="沒有資料列"  name="Form4Datas" selectId="" selectProperty="" selectName=""   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			
			<layout:collectionItem property="EHF030103T0_02" 			title="健保規則代號"	style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF030103T0_02_VERSION" 	title="健保規則名稱"	style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF030103T1_03" 			title="投保級距"		style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF030103T1_07" 			title="備註"			style="TEXT-ALIGN: CENTER"/>	
		</layout:collection>
	
	</logic:equal>

	<logic:equal name="ng_collection" value="show">
		
			<layout:row>
				  <layout:message styleClass="MESSAGE_ERROR" key="${NGMSG}"/>
			</layout:row>
		
		<layout:collection emptyKey="沒有資料列"  name="Form3Datas" selectId="" selectProperty="" selectName=""   width="100%" height="" styleClass="COLLECTION" styleClass2="COLLECTION_2">
			
			<layout:collectionItem property="EHF030103T0_02" 			title="健保規則代號"	style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF030103T0_02_VERSION" 	title="健保規則名稱"	style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF030103T1_03" 			title="投保級距"		style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="EHF030103T1_07" 			title="備註"			style="TEXT-ALIGN: CENTER"/>
			<layout:collectionItem property="ERROR" 					title="錯誤訊息"		style="TEXT-ALIGN: CENTER" 	filter="false"/>		
		
		</layout:collection>
	</logic:equal>


	<layout:popup styleId="fileimport" styleClass="DATAS" key="健保規則匯入">
		<layout:file key="檔案路徑:" property="IMP_EHF030103" fileKey="IMP_EHF030103" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEHF030103"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>
		
		
</layout:form>