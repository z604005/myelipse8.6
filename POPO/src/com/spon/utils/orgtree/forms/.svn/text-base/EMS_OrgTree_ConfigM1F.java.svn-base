package com.spon.utils.orgtree.forms;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.interfaces.ValidateForm;

public class EMS_OrgTree_ConfigM1F extends ActionForm implements ValidateForm {
	String tree_layer_font_color_01;	//第一階文字顏色
	String tree_layer_background_color_01;	//第一階背景顏色
	String tree_layer_font_color_02;	//第一階文字顏色
	String tree_layer_background_color_02;	//第一階背景顏色
	String tree_layer_font_color_03;	//第一階文字顏色
	String tree_layer_background_color_03;	//第一階背景顏色
	String tree_layer_font_color_04;	//第一階文字顏色
	String tree_layer_background_color_04;	//第一階背景顏色
	String tree_layer_font_color_05;	//第一階文字顏色
	String tree_layer_background_color_05;	//第一階背景顏色
	String tree_layer_font_color_06;	//第一階文字顏色
	String tree_layer_background_color_06;	//第一階背景顏色
	String tree_layer_font_color_07;	//第一階文字顏色
	String tree_layer_background_color_07;	//第一階背景顏色
	String tree_layer_font_color_08;	//第一階文字顏色
	String tree_layer_background_color_08;	//第一階背景顏色
	String tree_layer_font_color_09;	//第一階文字顏色
	String tree_layer_background_color_09;	//第一階背景顏色
	
	public ActionErrors validate(ActionMapping mapping,	HttpServletRequest request, Connection conn) {
		ActionErrors l_actionErrors = new ActionErrors();
		
		
		if (l_actionErrors.isEmpty()) {
			
			//新增時判斷條件
			if (request.getAttribute("action").equals("addData")) {
				addData_validate(l_actionErrors, request, conn);
			}
			
			//新增明細時判斷條件
			if (request.getAttribute("action").equals("addDetailData")) {
				addDetailData_validate(l_actionErrors, request, conn);
			}

			//修改時判斷條件
			if (request.getAttribute("action").equals("saveData")) {
				saveData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("delData")) {	
				delData_validate(l_actionErrors, request, conn);
			}
			
			//刪除時判斷條件
			if (request.getAttribute("action").equals("deleteOrgTree")) {	
				delDetailData_validate(l_actionErrors, request, conn);
			}
			
		}

		return l_actionErrors;
	}
	private void addData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	}

	private void addDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	}

	private void saveData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	}	
	private void delData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	}
	private void delDetailData_validate(ActionErrors errors, HttpServletRequest request, Connection conn) {
		// TODO Auto-generated method stub
	}
	public String getTree_layer_font_color_01() {
		return tree_layer_font_color_01;
	}
	public void setTree_layer_font_color_01(String Tree_layer_font_color_01) {
		tree_layer_font_color_01 = Tree_layer_font_color_01;
	}
	public String getTree_layer_background_color_01() {
		return tree_layer_background_color_01;
	}
	public void setTree_layer_background_color_01(String Tree_layer_background_color_01) {
		tree_layer_background_color_01 = Tree_layer_background_color_01;
	}
	public String getTree_layer_font_color_02() {
		return tree_layer_font_color_02;
	}
	public void setTree_layer_font_color_02(String Tree_layer_font_color_02) {
		tree_layer_font_color_02 = Tree_layer_font_color_02;
	}
	public String getTree_layer_background_color_02() {
		return tree_layer_background_color_02;
	}
	public void setTree_layer_background_color_02(String Tree_layer_background_color_02) {
		tree_layer_background_color_02 = Tree_layer_background_color_02;
	}
	public String getTree_layer_font_color_03() {
		return tree_layer_font_color_03;
	}
	public void setTree_layer_font_color_03(String Tree_layer_font_color_03) {
		tree_layer_font_color_03 = Tree_layer_font_color_03;
	}
	public String getTree_layer_background_color_03() {
		return tree_layer_background_color_03;
	}
	public void setTree_layer_background_color_03(String Tree_layer_background_color_03) {
		tree_layer_background_color_03 = Tree_layer_background_color_03;
	}
	public String getTree_layer_font_color_04() {
		return tree_layer_font_color_04;
	}
	public void setTree_layer_font_color_04(String Tree_layer_font_color_04) {
		tree_layer_font_color_04 = Tree_layer_font_color_04;
	}
	public String getTree_layer_background_color_04() {
		return tree_layer_background_color_04;
	}
	public void setTree_layer_background_color_04(String Tree_layer_background_color_04) {
		tree_layer_background_color_04 = Tree_layer_background_color_04;
	}
	public String getTree_layer_font_color_05() {
		return tree_layer_font_color_05;
	}
	public void setTree_layer_font_color_05(String Tree_layer_font_color_05) {
		tree_layer_font_color_05 = Tree_layer_font_color_05;
	}
	public String getTree_layer_background_color_05() {
		return tree_layer_background_color_05;
	}
	public void setTree_layer_background_color_05(String Tree_layer_background_color_05) {
		tree_layer_background_color_05 = Tree_layer_background_color_05;
	}
	public String getTree_layer_font_color_06() {
		return tree_layer_font_color_06;
	}
	public void setTree_layer_font_color_06(String Tree_layer_font_color_06) {
		tree_layer_font_color_06 = Tree_layer_font_color_06;
	}
	public String getTree_layer_background_color_06() {
		return tree_layer_background_color_06;
	}
	public void setTree_layer_background_color_06(String Tree_layer_background_color_06) {
		tree_layer_background_color_06 = Tree_layer_background_color_06;
	}
	public String getTree_layer_font_color_07() {
		return tree_layer_font_color_07;
	}
	public void setTree_layer_font_color_07(String Tree_layer_font_color_07) {
		tree_layer_font_color_07 = Tree_layer_font_color_07;
	}
	public String getTree_layer_background_color_07() {
		return tree_layer_background_color_07;
	}
	public void setTree_layer_background_color_07(String Tree_layer_background_color_07) {
		tree_layer_background_color_07 = Tree_layer_background_color_07;
	}
	public String getTree_layer_font_color_08() {
		return tree_layer_font_color_08;
	}
	public void setTree_layer_font_color_08(String Tree_layer_font_color_08) {
		tree_layer_font_color_08 = Tree_layer_font_color_08;
	}
	public String getTree_layer_background_color_08() {
		return tree_layer_background_color_08;
	}
	public void setTree_layer_background_color_08(String Tree_layer_background_color_08) {
		tree_layer_background_color_08 = Tree_layer_background_color_08;
	}
	public String getTree_layer_font_color_09() {
		return tree_layer_font_color_09;
	}
	public void setTree_layer_font_color_09(String Tree_layer_font_color_09) {
		tree_layer_font_color_09 = Tree_layer_font_color_09;
	}
	public String getTree_layer_background_color_09() {
		return tree_layer_background_color_09;
	}
	public void setTree_layer_background_color_09(String Tree_layer_background_color_09) {
		tree_layer_background_color_09 = Tree_layer_background_color_09;
	}
}
