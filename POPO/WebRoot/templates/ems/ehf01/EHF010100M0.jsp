<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.hr.forms.EHF010100M0F" %>
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

<layout:form action="EHF010100M0.do" reqCode="" width="100%" styleClass="TITLE" enctype="multipart/form-data" method="post" key="員工資料查詢">
	
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="查詢" mode="D,D,D" name="btnimage?text=button.query&type=t"
						  reqCode="queryForm" property="EHF010100M0" ></layout:image>
			<layout:image alt="新增" mode="D,D,H" name="btnimage?text=button.add&type=t"
						  reqCode="addDataForm" property="EHF010100M0" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">

		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="修改" mode="H,D,H" name="btnimage?text=button.update&type=t"  
						  reqCode="editDataForm" property="EHF010100M0" ></layout:image>			
		</logic:equal>
<%--		<layout:image alt="員工基本資料匯入" mode="D,D,D" name="btnimage?text=button.import.emp&type=t"  	property="EHF010100M0"  onclick="openStrutsLayoutPopup('fileimport'); return false;" ></layout:image>--%>
<%--		<layout:image alt="年度休假匯入" mode="D,D,D" name="btnimage?text=button.import.empvacation&type=t" onclick="openStrutsLayoutPopup('fileupload'); return false;" ></layout:image>--%>
<%--		<layout:image alt="年度休假範例檔下載" mode="D,D,D" name="btnimage?text=button.download.vacationexample&type=t" property="EHF010100M0" reqCode="print_example" confirm="您確定要下載資料嗎?" ></layout:image>--%>
		<%--<layout:image alt="員工基本資料更新(薪資資料補正)" mode="D,D,D" name="btnimage?text=button.import0.emp&type=t"  reqCode="updateSalary"	property="EHF010100M0"></layout:image>--%>
	</layout:row>
	
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG_QUERY}" />
   		 <layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%" >
		
		<layout:text styleClass="DATAS" key="部門名稱" property="EHF000200T0_02" styleId="EHF000200T0_02" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" >				
			<sp:lov 	form="EHF010100M0F" 
						id="EHF010100T6_02,EHF000200T0_02,EHF000200T0_03" 
						lovField="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03,EHF000200T0_06" 
						table="EHF000200T0"
						fieldAlias="系統代號,部門代號,部門名稱,層級" 
						fieldName="EHF000200T0_01,EHF000200T0_02,EHF000200T0_03,EHF000200T0_06"									
						others=" AND HR_CompanySysNo = '${compid}' "
						/>
			<layout:text layout="false" property="EHF000200T0_03" styleId="EHF000200T0_03" size="12" mode="R,R,R" maxlength="16" name="Form1Datas" />
			<layout:text layout="false" property="EHF010100T6_02" styleId="EHF010100T6_02" size="12" mode="H,H,H" maxlength="16" name="Form1Datas" />
		</layout:text>
		<layout:text styleClass="DATAS" key="員工工號" property="EHF010100T0_02" styleId="EHF010100T0_02" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="員工姓名" property="EHF010100T0_05" styleId="EHF010100T0_05" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" key="身分證字號" property="EHF010100T0_I" styleId="EHF010100T0_I" size="16" mode="E,E,I" maxlength="16" name="Form1Datas" />
		<layout:select styleClass="DATAS" key="員工類別" property="EHF010100T0_07" styleId="EHF010100T0_07" mode="E,E,I" name="Form1Datas" >
				<layout:options collection="listEHF010100T0_07" property="item_id" labelProperty="item_value" />
		</layout:select>
		<layout:select styleClass="DATAS" key="職務狀況" property="EHF010100T1_02" styleId="EHF010100T1_02" mode="E,E,I" name="Form1Datas" >
				<layout:options collection="listEHF010100T1_02" property="item_id" labelProperty="item_value" />
		</layout:select>
	
	</layout:grid>
	
	<logic:equal name="collection" value="show">
	
	<%
		int item_index = 0;
		EHF010100M0F ehf010100m0f =(EHF010100M0F)request.getAttribute("Form1Datas");
		ArrayList list = (ArrayList) ehf010100m0f.getEHF010100T0_LIST();
		String strTmp = "";
		
	%>
		<%
			//建立頁次session 
			session.setAttribute("Pageid","0");
		%>
	
	<layout:pager sessionPagerId="Pageid"  linksLocation="both" width="100%" maxPageItems="15" >
	
		<layout:collection emptyKey="沒有資料列" name="Form1Datas" property="EHF010100T0_LIST" id="bean1" indexId="index" selectId="" selectProperty="" selectName="" 
						   selectType="" width="100%" styleClass="COLLECTION" styleClass2="COLLECTION_2">
		
			<layout:collectionItem name="bean1" title="選取">
				<center>
				<%
			
				if (item_index < list.size()){
					int i=Integer.valueOf((String) session.getAttribute("Pageid"))*15+item_index;//可以隨著換頁時  累加 i
					EHF010100M0F FORM=(EHF010100M0F)list.get(i);
					strTmp = FORM.getEHF010100T0_01();					
					item_index++;
				%>
						<input type="checkbox" name="checkId" value="<%=strTmp%>" onclick="chooseOne(this);" >
				<%
				}
				%>
				</center>
			</layout:collectionItem>
		
			<layout:collectionItem property="EHF000200T0_03" style="TEXT-ALIGN: CENTER" title="部門名稱" />
			<layout:collectionItem property="EHF010100T0_02" style="TEXT-ALIGN: CENTER" title="員工工號" />
			<layout:collectionItem property="EHF010100T0_05" style="TEXT-ALIGN: CENTER" title="員工姓名" />
			<layout:collectionItem property="EHF010100T0_07" style="TEXT-ALIGN: CENTER" title="員工類別" />
			<layout:collectionItem property="EHF010100T1_02" style="TEXT-ALIGN: CENTER" title="職務狀況" />

		</layout:collection>
		
	</layout:pager>
	
	</logic:equal>
	
	<layout:popup styleId="fileimport" styleClass="DATAS" key="員工基本資料匯入">
		<layout:file key="檔案路徑:" property="up_file1" fileKey="up_file1" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="impEmployeeInformation"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileimport');" />
				</layout:row>
	</layout:popup>
	
	<layout:popup styleId="fileupload" styleClass="DATAS" key="員工年度休假匯入">
		<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="uploadEHF010300M1"  ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileupload');" />
				</layout:row>
	</layout:popup>

</layout:form>

<layout:message styleClass="MESSAGE_ERROR" key="${DOWNLOADFILE}" />