<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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

function syncovertimehours(){
	
	try{
		document.getElementById("EHF030107T0_06").value = document.getElementById("EHF030107T0_04").value;
	}catch(err){
		alert(err.message);
	}
}

</script>

<layout:form action="EHF030107M1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="加班費規則設定">

<input type="hidden" id="dataChanged" name="dataChanged" value="" />	
<input type="hidden" id="stf_open_type" value="${stf_open_type}" />

	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="儲存" mode="D,H,H" name="btnimage?text=button.save&type=t"   reqCode="addDataForm" property="EHF030107M1" ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
		</logic:equal>
		<logic:equal name="button" value="query">
			<layout:image alt="儲存" mode="H,D,H" name="btnimage?text=button.save&type=t"   reqCode="saveDataForm" property="EHF030107M1" ></layout:image>
			
			<logic:notEqual name="DisplayFileName" value="" >
						<layout:image alt="下載檔案" mode="D,D,D" name="btnimage?text=下載檔案&type=t" 	  onclick="opendownloadfile(); return false;" >     </layout:image>
			</logic:notEqual>
				  
			<layout:image alt="刪除" mode="H,D,H" name="btnimage?text=button.delete&type=t" 	  reqCode="delForm" property="EHF030107M1"	  confirm="您確定要刪除資料嗎?" ></layout:image>	
		
		</logic:equal>
		<layout:image alt="回前作業" mode="D,D,D" name="btnimage?text=button.Back&type=t"   reqCode="redirect" property="EHF030107M1" ></layout:image>
	</layout:row>
	
	<layout:row >
   		<layout:message styleClass="MESSAGE_ERROR" key="${MSG_EDIT}" />
   		<layout:message styleClass="MESSAGE_ERROR" key="${ERR_MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		
		<layout:text styleClass="DATAS" key="加班規則序號" name="Form1Datas" property="EHF030107T0_01" 	styleId="EHF030107T0_01" 		 size="25" maxlength="25" mode="H,H,H" />
		<layout:text styleClass="DATAS" key="加班規則代號" name="Form1Datas" property="EHF030107T0_02" 	styleId="EHF030107T0_02" 		 size="25" maxlength="20" mode="E,I,I" />
		<layout:text styleClass="DATAS" key="加班規則名稱" name="Form1Datas" property="EHF030107T0_03" 	styleId="EHF030107T0_03" 		 size="30" maxlength="40" mode="E,E,I" />
		
	</layout:grid>
	
	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;平常日加班:&nbsp;&nbsp;
			<br>
			&nbsp;&nbsp;加班
			<layout:number styleClass="DATAS" key="加班(時數一)" 
						   property="EHF030107T0_04" styleId="EHF030107T0_04" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT"
					   	   onchange="return syncovertimehours();" />
			&nbsp;小時前,
			&nbsp;&nbsp;加班費加成率
			<layout:number styleClass="DATAS" key="加班(加成率一)" 
						   property="EHF030107T0_05" styleId="EHF030107T0_05" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;倍
			<br>
			&nbsp;&nbsp;加班
			<layout:number styleClass="DATAS" key="加班(時數二)" 
						   property="EHF030107T0_06" styleId="EHF030107T0_06" layout="false" 
						   size="4" maxlength="4" mode="R,R,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;小時後,
			&nbsp;&nbsp;加班費加成率
			<layout:number styleClass="DATAS" key="加班(加成率二)" 
						   property="EHF030107T0_07" styleId="EHF030107T0_07" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;倍
		</layout:cell>
		
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;國定假日加班:&nbsp;&nbsp;
			<br>
			&nbsp;&nbsp;全天加班費加成率
			<layout:number styleClass="DATAS" key="假日加班加成率" 
						   property="EHF030107T0_08" styleId="EHF030107T0_08" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;倍
		</layout:cell>
		
		<layout:cell styleClass="DATAS" >
			&nbsp;&nbsp;例假日加班:&nbsp;&nbsp;
			<br>
			&nbsp;&nbsp;全天加班費加成率
			<layout:number styleClass="DATAS" key="假日加班加成率" 
						   property="EHF030107T0_11" styleId="EHF030107T0_11" layout="false" 
						   size="4" maxlength="4" mode="E,E,I"
					   	   style="TEXT-ALIGN: RIGHT" />
			&nbsp;倍
		</layout:cell>
		
	</layout:grid>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
	
		<layout:radios key="是否啟用" name="Form1Datas" property="EHF030107T0_09" styleClass="DATAS" mode="E,E,I" cols="2" >
			<layout:options collection="listTF" property="item_id" labelProperty="item_value" />
		</layout:radios>
		
		<layout:cell styleClass="DATAS" />
	
	</layout:grid>
	
	<layout:notMode value="create" >
	<logic:equal name="collection" value="show">
	
	<layout:notMode value="inspect">
	
	</layout:notMode>
	
	</logic:equal>
	</layout:notMode>
	
	<layout:notMode value="create" >
	<logic:equal name="button" value="query">
		<layout:grid cols="2" space="false" borderSpacing="1" align="left" width="100%" styleClass="DATAGRID">
			<layout:text  key="資料建立人員" styleClass="LOGDATA" 	property="USER_CREATE" 	styleId="USER_CREATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動人員" styleClass="LOGDATA" 	property="USER_UPDATE" 	styleId="USER_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="資料版本" 	styleClass="LOGDATA" 	property="VERSION" 		styleId="VERSION" 		name="Form1Datas" mode= "I,I,I" />
			<layout:text  key="最後異動日期" styleClass="LOGDATA" 	property="DATE_UPDATE" 	styleId="DATE_UPDATE" 	name="Form1Datas" mode= "I,I,I" />
		</layout:grid>
	</logic:equal>
	</layout:notMode>
	
</layout:form>