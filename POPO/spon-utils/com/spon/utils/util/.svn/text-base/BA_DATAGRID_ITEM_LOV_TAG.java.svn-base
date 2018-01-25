package com.spon.utils.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.spon.utils.struts.form.BA_Detail_Attr;
import com.spon.utils.struts.form.BA_Detail_Head_Attr;




public class BA_DATAGRID_ITEM_LOV_TAG extends TagSupport{
	
	protected String name,id,disp_type,value="",sqlstatement,cfield_name,sqlkeyid,dg_keyvalue_cname,lovfield_name,lovfield_id,sqlother="";
	protected int width=100,maxlength=100;
	protected boolean headkey=false,iskey=false,isRequired=false;
	
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
			D_A.setDISP_TYPE("LK"+disp_type);
		}
		else
		{
			D_A.setDISP_TYPE("LN"+disp_type);
		}
		
		
		D_A.setSQL(sqlstatement.replaceAll("\'","\\<\\~\\>").replaceAll("%","\\<\\^\\>"));
		D_A.setSQLOTHER(" "+sqlother.replaceAll("\'","\\<\\~\\>").replaceAll("%","\\<\\^\\>"));
		
		D_A.setCFIELD_NAME(cfield_name.replaceAll(",","{;}"));
		D_A.setSQLKEYID(sqlkeyid.replaceAll(",","{;}"));
		D_A.setDG_KEYVALUE_CNAME(dg_keyvalue_cname.replaceAll(",","{;}"));
		D_A.setLOVFIELD_NAME(lovfield_name.replaceAll(",","{;}"));
		D_A.setLOVFIELD_ID(lovfield_id.replaceAll(",","{;}"));
		
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

	public String getCfield_name() {
		return cfield_name;
	}

	public String getDg_keyvalue_cname() {
		return dg_keyvalue_cname;
	}

	public String getSqlkeyid() {
		return sqlkeyid;
	}

	public String getSqlstatement() {
		return sqlstatement;
	}

	public void setCfield_name(String cfield_name) {
		this.cfield_name = cfield_name;
	}

	public void setDg_keyvalue_cname(String dg_keyvalue_cname) {
		this.dg_keyvalue_cname = dg_keyvalue_cname;
	}

	public void setSqlkeyid(String sqlkeyid) {
		this.sqlkeyid = sqlkeyid;
	}

	public void setSqlstatement(String sqlstatement) {
		this.sqlstatement = sqlstatement;
	}

	public boolean isIskey() {
		return iskey;
	}

	public void setIskey(boolean iskey) {
		this.iskey = iskey;
	}

	

	public String getLovfield_name() {
		return lovfield_name;
	}

	
	public void setLovfield_name(String lovfield_name) {
		this.lovfield_name = lovfield_name;
	}

	public String getLovfield_id() {
		return lovfield_id;
	}

	public void setLovfield_id(String lovfield_id) {
		this.lovfield_id = lovfield_id;
	}

	public String getSqlother() {
		return sqlother;
	}

	public void setSqlother(String sqlother) {
		this.sqlother = sqlother;
	}

	

	
}
