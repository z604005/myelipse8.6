<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%--<%@ taglib uri="http://struts.apache.org/tags-template" prefix="template"%>--%>
<%@ taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@ taglib uri="/WEB-INF/struts-layout.tld" prefix="layout"%>
<%@ taglib uri="/WEB-INF/spon.tld" prefix="sp"%>

<sp:datagrid 	action="DF001.do"
				title="DATAGRID樣板(單檔維護)"
 				rsperpage="10" 
				pager="true"
				dbgridwidth="800" 
				dbgridheight="350" 
				trheight="25" 
				onloadinit="true" 
				fontsize="16" 
				sqlstatement="SELECT SC0060_01, SC0060_02, SC0060_03, SC0060_04, VERSION ,'' as 代碼,'' as 名稱  FROM SC0060  order by SC0060_01" >
	<sp:datagridItemText name="參數名稱" id="SC0060_01" width="100" disp_type="R" iskey="true" isRequired="true" />
	<sp:datagridItemText name="參數說明" id="SC0060_02" width="100" disp_type="E" isRequired="true" />
	<sp:datagridItemText name="內容" id="SC0060_03" width="50" disp_type="E"  isRequired="true" />
	<sp:datagridItemText name="備註" id="SC0060_04" width="50" disp_type="E"  />
	<sp:datagridItemText name="版本" id="VERSION" width="50" disp_type="R"  />
	<sp:datagridItemSelect name="" id="" disp_type="" options=""></sp:datagridItemSelect>
	<sp:datagridItemText name="程式代碼" id="代碼" width="80" disp_type="E" />
	<sp:datagridItemLov name="程式名稱" id="名稱"  
						sqlstatement="SELECT SC0020_01,SC0020_02,SC0020_03,SC0020_04 FROM SC0020  WHERE 1=1 and SC0020_01 like '%S%' " 
						sqlother="order by SC0020_01"
						lovfield_name="程式代碼,程式名稱,程式路徑,備註"
						lovfield_id="SC0020_01,SC0020_02,SC0020_03,SC0020_04"
						cfield_name="程式代碼,程式名稱"  
						sqlkeyid=""  
						disp_type="E" 
						width="200"
						dg_keyvalue_cname="" />

</sp:datagrid>








