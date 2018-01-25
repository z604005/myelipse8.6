package com.spon.utils.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.spon.utils.struts.form.BA_Detail_Head_Attr;

public class BA_GETCODENAME_TAG extends TagSupport{

	protected String others="",thisfield,table,cfield,returnfield,tablekey,keyvalue,forusername;

	public String getCfield() {
		return cfield;
	}

	public String getKeyvalue() {
		return keyvalue;
	}

	public String getOthers() {
		return others;
	}

	public String getReturnfield() {
		return returnfield;
	}

	public String getTable() {
		return table;
	}

	public String getTablekey() {
		return tablekey;
	}

	public String getThisfield() {
		return thisfield;
	}

	public void setCfield(String cfield) {
		this.cfield = cfield;
	}

	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public void setReturnfield(String returnfield) {
		this.returnfield = returnfield;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public void setTablekey(String tablekey) {
		this.tablekey = tablekey;
	}

	public void setThisfield(String thisfield) {
		this.thisfield = thisfield;
	}

	public int doStartTag() throws JspException {
		try {
			StringBuffer sb=new StringBuffer();
			{
			JspWriter out=pageContext.getOut();
			
//			('SC0030_07','SC_UNITM','SC_UNITM_02','SA_SALAM_S_txt','SC_UNITM_01',SC0030_07.value,'',event)
			sb.append("<script language=\"javascript\"> \n <!--// \n");
			sb.append("function getcodename"+thisfield+"()\n{\n");
			
		//	sb.append("alert('aaa');");
			
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
		forusername=forusername==null?"":forusername;
		if ( forusername.equals("") && !forusername.equals("true"))
		{
			sb.append("getCodeName('"+thisfield+"','"+table+"','"+cfield+"','"+returnfield+"','"+tablekey+"',"+"document.all."+keyvalue+".value,'"+others.replaceAll("\'","<~>").replaceAll("%","<^>")+"',event);");
			sb.append(" \n}\n");
			sb.append("getCodeName('"+thisfield+"','"+table+"','"+cfield+"','"+returnfield+"','"+tablekey+"',"+"document.all."+keyvalue+".value,'"+others.replaceAll("\'","<~>").replaceAll("%","<^>")+"',event);\n");
		}else{
			sb.append("getUserName('"+thisfield+"','"+table+"','"+cfield+"','"+returnfield+"','"+tablekey+"',"+"document.all."+keyvalue+".value,'"+others.replaceAll("\'","<~>").replaceAll("%","<^>")+"',event);");
			sb.append(" \n}\n");
		}
		sb.append(" --> \n</script> \n");
		
		out.print(sb.toString());
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}

	public String getForusername() {
		return forusername;
	}

	public void setForusername(String forusername) {
		this.forusername = forusername;
	}

	
	

	
	
}
