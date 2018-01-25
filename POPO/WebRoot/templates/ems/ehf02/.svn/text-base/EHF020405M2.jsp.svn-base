<%@ page language="java" import="java.util.*"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.spon.utils.struts.form.EMS_VIEWDATAF" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<jsp:include page="/templates/begin.jsp"></jsp:include>

<!-- JavaScript -->
<script type="text/javascript">


function chkDeptID(){
	if(document.getElementById("DATA01").value == "" 
	   || document.getElementById("DATA01").value == null ){
		alert("請先選擇部門組織!!");
		return false;
	}else{
		return true;
	}
}

//清除員工所有欄位
function cls2(){
	document.getElementById("DATA03").value = "";
	document.getElementById("DATA04").value = "";
	document.getElementById("DATA09").value = "";

	return true;
}

/**
 * 確認考勤產生範圍
 */
function chkScope(){
	
	//alert((document.getElementById("DATA06").value).slice(0,7));
	//alert("重新產生全公司 "+  document.getElementById("DATA07").value+" 考勤資料!!");
	
	
	var c = document.getElementsByName("DATA07");
	var number="";
	for(i=0;i<c.length;i++){
  	 if(c[i].checked == true )
   		{
  		 
  		 number+= (c[i].value);
       		

   		}
	}
	
	
	if(number==01){
		//月份
		if( document.getElementById("DATA05").value=="" || document.getElementById("DATA05").value== null){
			 alert("請輸入日期條件(起)日期!!");
			 return false;
			}
	}else if(number==02){
		//指定日期
		if( document.getElementById("DATA05").value=="" || document.getElementById("DATA05").value== null){
			 alert("請輸入日期條件(起)日期!!");
			 return false;
			}
	}else if(number==03){
		//指定日期區間
		if( document.getElementById("DATA05").value=="" || document.getElementById("DATA05").value== null||document.getElementById("DATA06").value=="" || document.getElementById("DATA06").value== null){
			 alert("請輸入日期條件(起)、(迄)日期!!");
			 return false;
			}
	}

	
	if(number==01){
		//月份
		
		if(document.getElementById("DATA03").value != ""   && document.getElementById("DATA03").value != null 	// 組織單位
		 && document.getElementById("DATA01").value != ""  && document.getElementById("DATA01").value != null){	// 員工
			return confirm("重新產生員工:"+document.getElementById("DATA04").value+" "+  (document.getElementById("DATA05").value).slice(0,7) +" 考勤資料!!");

		}else if(document.getElementById("DATA01").value != "" && document.getElementById("DATA01").value != null){// 組織單位
		
			return confirm("重新產生部門:"+document.getElementById("DATA02").value+" "+ (document.getElementById("DATA05").value).slice(0,7)+" 考勤資料!!");	
		
		}else{
		
			return confirm("重新產生全公司 "+  (document.getElementById("DATA05").value).slice(0,7)+" 考勤資料!!");
		}

	}else if(number==02){
		//指定日期
		if(document.getElementById("DATA03").value != ""   && document.getElementById("DATA03").value != null 	// 組織單位
		 && document.getElementById("DATA01").value != ""  && document.getElementById("DATA01").value != null){	// 員工
			return confirm("重新產生員工:"+document.getElementById("DATA04").value+" "+  (document.getElementById("DATA05").value) +" 考勤資料!!");

		}else if(document.getElementById("DATA01").value != "" && document.getElementById("DATA01").value != null){// 組織單位
		
			return confirm("重新產生部門:"+document.getElementById("DATA02").value+" "+ (document.getElementById("DATA05").value)+" 考勤資料!!");	
		
		}else{
		
			return confirm("重新產生全公司 "+  (document.getElementById("DATA05").value)+" 考勤資料!!");
		}
		
	}else if(number==03){
		//指定日期區間
		if(document.getElementById("DATA03").value != ""   && document.getElementById("DATA03").value != null 	// 組織單位
		 && document.getElementById("DATA01").value != ""  && document.getElementById("DATA01").value != null){	// 員工
			 return confirm("重新產生員工:"+document.getElementById("DATA04").value+" "+  document.getElementById("DATA05").value+"~"+ document.getElementById("DATA06").value+" 考勤資料!!");

		}else if(document.getElementById("DATA01").value != "" && document.getElementById("DATA01").value != null){// 組織單位
		
			return confirm("重新產生部門:"+document.getElementById("DATA02").value+" "+  document.getElementById("DATA05").value+"~"+ document.getElementById("DATA06").value+" 考勤資料!!");	
		
		}else{
		
			return confirm("重新產生全公司 "+   document.getElementById("DATA05").value+"~"+ document.getElementById("DATA06").value+" 考勤資料!!");
		}
		
		
		
	}
	
	
	
	
	
	
	
	/*
	
	if(document.getElementById("DATA03").value != ""   && document.getElementById("DATA03").value != null  
			&& document.getElementById("DATA01").value != ""  && document.getElementById("DATA01").value != null
			&& document.getElementById("DATA06").value!="" && document.getElementById("DATA06").value!= null){
		
		return ("重新產生員工:"+document.getElementById("DATA04").value+" "+  document.getElementById("DATA05").value+"~"+ document.getElementById("DATA06").value+" 考勤資料!!");
		
	}
	else if(document.getElementById("DATA03").value != ""   && document.getElementById("DATA03").value != null  && document.getElementById("DATA01").value != ""   && document.getElementById("DATA01").value != null){
		
		return ("重新產生員工:"+document.getElementById("DATA04").value+" "+  document.getElementById("DATA05").value+" 考勤資料!!");

	}else if(document.getElementById("DATA01").value != ""  && document.getElementById("DATA01").value != null&& document.getElementById("DATA06").value!="" && document.getElementById("DATA06").value!= null){
		
		return ("重新產生部門:"+document.getElementById("DATA02").value+" "+  document.getElementById("DATA05").value+"~"+ document.getElementById("DATA06").value+" 考勤資料!!");	

	}else if(document.getElementById("DATA01").value != ""  && document.getElementById("DATA01").value != null){
		
		return ("重新產生部門:"+document.getElementById("DATA02").value+" "+  document.getElementById("DATA05").value+" 考勤資料!!");	
	
	}else if(document.getElementById("DATA06").value!="" && document.getElementById("DATA06").value!= null){
		
		return ("重新產生全公司 "+   document.getElementById("DATA05").value+"~"+ document.getElementById("DATA06").value+" 考勤資料!!");
	}else{
		
		return ("重新產生全公司 "+  document.getElementById("DATA05").value+" 考勤資料!!");
	}	*/
	
	
}
function setTime(){
	//document.getElementById("DATA06").value = document.getElementById("DATA05").value;
	
	$("input[name='DATA06']").val($("input[name='DATA05']").val());
	return true;
}


</script>

<layout:form action="EHF020405M2.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="重新抓取考勤"	 enctype="multipart/form-data" >
			 
	<layout:row>
		<logic:notEqual name="button" value="edit">
			<layout:image alt="重新抓取考勤" mode="D,D,H" name="btnimage?text=button.re.attendance&type=t"  reqCode="generateDataForm" property="EHF020405M2" 
			  confirm=""   ></layout:image>
		</logic:notEqual>
		<logic:equal name="button" value="edit">
		</logic:equal>
		<logic:equal name="button" value="query">
		</logic:equal>
	</layout:row>
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
<%--	<layout:grid cols="1" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">--%>
<%--		<layout:text styleClass="DATAS" tooltip="登入User_Id" key="登入User_Id" property="DATA10" --%>
<%--					 size="12" mode="I,I,I" maxlength="16" name="Form1Datas" />--%>
<%--	</layout:grid>--%>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		

		
		
		<layout:text styleClass="DATAS" key="部門系統代碼" property="DATA01" styleId="DATA01" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="組織單位" key="組織單位" property="DATA02" styleId="DATA02" size="10" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA01,DATA02,DATA08" 
							lovField="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02" 
							table="EHF000200T0"
							fieldAlias="系統代碼,部門名稱,部門代號" 
							fieldName="EHF000200T0_01,EHF000200T0_03,EHF000200T0_02"									
							others=" AND EHF000200T0_06>1  AND HR_CompanySysNo = '${compid}' "
								beforerun="cls2()"
							/>
				<layout:text layout="false" property="DATA08" styleId="DATA08" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>

			<layout:text styleClass="DATAS" key="員工系統代碼" property="DATA03" styleId="DATA03" mode="H,H,H" name="Form1Datas" />
			<layout:text styleClass="DATAS" tooltip="員工" key="員工" property="DATA04" styleId="DATA04" size="12" mode="E,E,I" maxlength="20" name="Form1Datas" >
				<sp:lov 	form="EMS_VIEWDATAF" 
							id="DATA03,DATA04,DATA09" 
							lovField="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05" 
							table="EHF010100T6"
							leftjoin=" LEFT JOIN EHF010100T0 ON EHF010100T6_01 = EHF010100T0_01 "
							fieldAlias="系統代碼,工號,姓名" 
							fieldName="EHF010100T0_01,EHF010100T0_02,EHF010100T0_05"
							parentId="EHF010100T6_02" 
							parentField="window.EMS_VIEWDATAF.DATA01.value" 
							others=" AND EHF010100T6.HR_CompanySysNo = '${compid}' "   
							beforerun="chkDeptID()"	/>	
									
				<layout:text layout="false" property="DATA09" styleId="DATA09" size="12" mode="R,R,I" maxlength="16" name="Form1Datas" />
			</layout:text>
		
		
		<layout:radios styleClass="DATAS" cols="3" tooltip="日期類別" key="日期類別" property="DATA07" value="02" mode="E,E,I" name="Form1Datas" >
				<layout:options collection="listDATA05" property="item_id" labelProperty="item_value" />
		</layout:radios>
		
		
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2010" endYear="2025"  key="日期條件" 		name="Form1Datas" property="DATA05" styleClass="DATAS"  layout="true" onchange="setTime()">
			&nbsp;&nbsp;~&nbsp;&nbsp;
		<layout:date calendarType="datepicker" patternKey="yy/mm/dd" mode="R,R,I" size="10" startYear="2010" endYear="2025"  key="日期條件(迄)"  name="Form1Datas" property="DATA06" styleClass="DATAS"  layout="false" />
		</layout:date>
		
		
	</layout:grid>
	
</layout:form>
