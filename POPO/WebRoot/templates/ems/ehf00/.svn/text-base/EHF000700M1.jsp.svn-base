<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.ems.com.forms.EHF000700M0F" %>
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
function fbutton(reqCode) {
	EHF000700M0F.elements['reqCode'].value=reqCode;
	EHF000700M0F.submit();
}	

function chkdelfile(){
	
	if(confirm('您確定要刪除附加檔案嗎?')){
		//啟動讀取動畫
		showEMSWait();
		fbutton('delAttachedFile'); 
	}
	return false;
}
</script>

<layout:form action="EHF000700M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" enctype="multipart/form-data" key="表格上傳與下載區">

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="確認新增" mode="D,H,D" name="btnimage?text=button.add.ok&type=t"  reqCode="addDataForm" property="EHF000700M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="query">
			<layout:image alt="儲存" mode="H,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF000700M1" ></layout:image>
			<layout:image alt="刪除" mode="H,D,D" name="btnimage?text=button.delete&type=t" confirm="您確定要刪除資料嗎?" reqCode="delForm" property="EHF000700M1" ></layout:image>
		</logic:equal>
		<logic:equal name="button" value="edit">
		<layout:image alt="儲存" mode="D,D,D" name="btnimage?text=button.save&type=t" reqCode="saveDataForm" property="EHF000700M1" ></layout:image>
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"  reqCode="redirect" property="EHF000700M1" ></layout:image>
    </layout:row>
    
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:row >
	</layout:row>
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		<layout:text styleClass="DATAS" property="EHF000700T0_01" styleId="EHF000700T0_01" size="16" mode="H,H,H" maxlength="16" name="Form1Datas" />
		<layout:text styleClass="DATAS" property="EHF000700T0_02" styleId="EHF000700T0_02" key="表格名稱" size="100" maxlength="100" name="Form1Datas"  mode="E,E,I" />
		<layout:text styleClass="DATAS" property="EHF000700T0_03" styleId="EHF000700T0_03" key="表格簡述" size="100" maxlength="100" name="Form1Datas"  mode="E,E,I" />
		<layout:textarea styleClass="DATAS" property="EHF000700T0_04" key="表格說明" rows="3" cols="80" size="80" maxlength="80" name="Form1Datas" mode="E,E,I" />
			<logic:notEqual name="Form1Datas" property="EHF000700T0_01" value="">
				<layout:field styleClass="DATAS" property="EHF000700T0_05" key="附加檔案" name="Form1Datas" access="READONLY" >
					<logic:notEqual name="Attached_File" value="yes" >
						<layout:button value="上傳檔案" onclick="openStrutsLayoutPopup('fileupload'); return false;" mode="D,D,H" ></layout:button>
					</logic:notEqual>
					<logic:equal name="Attached_File" value="yes">
						<layout:row styleClass="DATAS" >
							<layout:link layout="true" styleClass="DATAS" action="" onclick="fbutton('getAttachedFile'); return false;" >
	   		 					 <layout:message styleClass="DATAS" key="${Attached_File_Name}" />
	   		 				</layout:link>
	   		 				<layout:link layout="true" styleClass="DATAS" action="" mode="D,D,H" onclick="chkdelfile(); return false;" >
	   		 					<layout:img styleClass="DATAS" alt="刪除附加檔案" srcName="deleteicon.jpg" />
	   		 				</layout:link>
	   		 			</layout:row>
	   		 		</logic:equal>
				</layout:field>
			</logic:notEqual>
	</layout:grid>
	<layout:row >
	</layout:row>
	
	<layout:grid cols="1"  space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
				<layout:text  key="資料建立人員" styleClass="LOGDATA" property="USER_CREATE" styleId="USER_CREATE" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="最後異動人員" styleClass="LOGDATA" property="USER_UPDATE" styleId="USER_UPDATE" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="資料版本" styleClass="LOGDATA" property="VERSION" styleId="VERSION" name="Form1Datas" mode= "I,I,I" />
				<layout:text  key="最後異動日期" styleClass="LOGDATA" property="DATE_UPDATE" styleId="DATE_UPDATE" name="Form1Datas" mode= "I,I,I" />
			</layout:grid>
	</layout:grid>
	
		<layout:popup styleId="fileupload" styleClass="DATAS" key="附加檔案上傳">
			<layout:file key="檔案路徑:" property="UPLOADFILE" fileKey="UPLOADFILE" styleClass="DATAS" ></layout:file>
				<layout:row >				
					<layout:submit value="匯入" reqCode="uploadEHF000700M1" ></layout:submit>
					<layout:button value="關閉" onclick="closeStrutsLayoutPopup('fileupload');" />
				</layout:row>
		</layout:popup>
	
</layout:form>