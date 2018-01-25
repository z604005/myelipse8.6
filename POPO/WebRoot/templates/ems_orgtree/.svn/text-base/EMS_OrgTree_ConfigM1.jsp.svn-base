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

<!-- JavaScript -->
<script type="text/javascript" src="<%= request.getContextPath() %>/config/jscolor-1.4.2/jscolor.js"></script>
<%--組織樹參數設定作業  2014/10/03 整理  By Alvin--%>
<layout:form action="EMS_OrgTree_ConfigM1.do" reqCode="" width="100%" styleClass="TITLE" method="post" key="組織樹參數設定作業" >
	<layout:row>
		<layout:image alt="儲存" mode="D,D,D" name="btnimage?text=button.save&type=t"  reqCode="saveDataForm" property="EMS_OrgTree_ConfigM1" ></layout:image>
	</layout:row>
	<layout:row >
   		 <layout:message styleClass="MESSAGE_ERROR" key="${MSG}" />
	</layout:row>
	
	<layout:grid cols="2" space="false" borderSpacing="1" align="left" styleClass="DATAGRID" width="100%">
		<layout:cell colspan="2" styleClass="DATAS"><center><b>各階層顏色設定</b></center></layout:cell>
		<layout:text styleClass="color {hash:true}" key="第一階文字顏色" property="tree_layer_font_color_01" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第一階背景顏色" property="tree_layer_background_color_01" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第二階文字顏色" property="tree_layer_font_color_02" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第二階背景顏色" property="tree_layer_background_color_02" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第三階文字顏色" property="tree_layer_font_color_03" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第三階背景顏色" property="tree_layer_background_color_03" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第四階文字顏色" property="tree_layer_font_color_04" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第四階背景顏色" property="tree_layer_background_color_04" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第五階文字顏色" property="tree_layer_font_color_05" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第五階背景顏色" property="tree_layer_background_color_05" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第六階文字顏色" property="tree_layer_font_color_06" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第六階背景顏色" property="tree_layer_background_color_06" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第七階文字顏色" property="tree_layer_font_color_07" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第七階背景顏色" property="tree_layer_background_color_07" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第八階文字顏色" property="tree_layer_font_color_08" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第八階背景顏色" property="tree_layer_background_color_08" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第九階文字顏色" property="tree_layer_font_color_09" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:text styleClass="color {hash:true}" key="第九階背景顏色" property="tree_layer_background_color_09" size="" mode="E,E,I" maxlength="" name="Form1Datas" />
		<layout:cell colspan="2" styleClass="DATAS"><center><b>下列指定作業將套用上述設定(<font color="red">本功能未實作</font>)</b></center></layout:cell>
	</layout:grid>
	
</layout:form>