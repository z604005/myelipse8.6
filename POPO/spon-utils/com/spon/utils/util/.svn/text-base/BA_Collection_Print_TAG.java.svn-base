package com.spon.utils.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Statement;

import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.sql.DataSource;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.WritableFont;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.actions.SC006A;
import com.spon.utils.sc.actions.Security;

public class BA_Collection_Print_TAG extends TagSupport {

	protected String name,sql,heads,propertys,isdefault="";
	
	public int doStartTag() throws JspException {
		try {
			StringBuffer sb=new StringBuffer();
			{
			JspWriter out=pageContext.getOut();
		
			sb.append("<input type=\"hidden\" id=\""+name+"\" value=\""+sql+"\" />");
			sb.append("<input type=\"hidden\" id=\""+name+"heads\" value=\""+heads+"\" />");
			sb.append("<input type=\"hidden\" id=\""+name+"propertys\" value=\""+propertys+"\" />");
		
			
			out.print(sb.toString());
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag() throws JspException {
		try {
		StringBuffer sb=new StringBuffer();
		{
		JspWriter out=pageContext.getOut();
		
		if(!isdefault.equals(""))
		{
			sb.append("<SCRIPT>document.all.collection.value=\""+name+"\"</SCRIPT>");
		}
	
		
		out.print(sb.toString());
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	public String getHeads() {
		return heads;
	}

	public void setHeads(String heads) {
		this.heads = heads;
	}

	public String getPropertys() {
		return propertys;
	}

	public void setPropertys(String propertys) {
		this.propertys = propertys;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	

}
