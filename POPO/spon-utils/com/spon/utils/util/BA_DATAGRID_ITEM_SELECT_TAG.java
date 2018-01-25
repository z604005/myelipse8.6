package com.spon.utils.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spon.utils.struts.form.BA_Detail_Attr;
import com.spon.utils.struts.form.BA_Detail_Head_Attr;




public class BA_DATAGRID_ITEM_SELECT_TAG extends TagSupport{
	
	protected String name,id,disp_type,value="",options;
	protected int width=100,maxlength=100;
	protected boolean headkey=false,iskey=false,isRequired=false;;
	
	public boolean isIsRequired() {
		return isRequired;
	}

	public void setIsRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public boolean isHeadkey() {
		return headkey;
	}

	public void setHeadkey(boolean headkey) {
		this.headkey = headkey;
	}

	public int doStartTag() throws JspException {
		BA_Detail_Head_Attr D_H_A=(BA_Detail_Head_Attr)pageContext.getSession().getAttribute("D_H_A");
		ArrayList D_A_C=(ArrayList)D_H_A.getBA_Detail_attr();
		BA_Detail_Attr D_A = new BA_Detail_Attr();
		D_A.setWIDTH(width);
		D_A.setTH_ID(id);
		D_A.setTH_NAME(name);
		D_A.setMAXLENGTH(maxlength);
		D_A.setVALUE(value);
		D_A.setRequired(isRequired);
		
		
		if(iskey)
		{
			D_A.setDISP_TYPE("SK"+disp_type);
		}
		else
		{
			D_A.setDISP_TYPE("SN"+disp_type);
		}
		
		
		
		if(options!=null )
			D_A.setOPTIONS((List)pageContext.getRequest().getAttribute(options));
		
		D_A_C.add(D_A);
		 
		return SKIP_BODY;
	}
	
	public void release() {
	
	}

	public String getDisp_type() {
		return disp_type;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public int getWidth() {
		return width;
	}

	public void setDisp_type(String disp_type) {
		this.disp_type = disp_type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(int maxlength) {
		this.maxlength = maxlength;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public boolean isIskey() {
		return iskey;
	}

	public void setIskey(boolean iskey) {
		this.iskey = iskey;
	}

	
}
