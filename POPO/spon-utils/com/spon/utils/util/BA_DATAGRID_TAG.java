package com.spon.utils.util;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.spon.utils.struts.form.BA_Detail_Head_Attr;

public class BA_DATAGRID_TAG extends TagSupport{

	protected String action;
	protected int rsperpage;
	protected boolean pager;
	protected String keyid="",title="";
	protected int dbgridwidth=800;
	protected int dbgridheight=350;
	protected int trheight=25;
	protected int fontsize=12;
	protected String sqlstatement,sqlother="";
	protected boolean onloadinit;
	
	public String getSqlstatement() {
		return sqlstatement;
	}

	public void setSqlstatement(String sqlstatement) {
		this.sqlstatement = sqlstatement;
	}

	public int doStartTag() throws JspException {
		
		try {
			StringBuffer sb=new StringBuffer();
			JspWriter out=pageContext.getOut();
			sb.append("<script language='JavaScript' src='config/LJdatagrid.js'/>'></script>");
			sb.append("\r\n");
			sb.append("<div id='headDivAttn' style='position:absolute;display:none;filter:alpha(opacity='10');'></div>");
			sb.append("\r\n");
			sb.append("<iframe id='headDivShim' src='Blank.html' scrolling='no' frameborder='0' style='position:absolute; top:0px; left:0px; display:none;filter:alpha(opacity='10');'></iframe>");
			sb.append("\r\n");
			sb.append("<div id='detailloading' style='position:absolute;display:none;z-index:100'></div>");
			sb.append("\r\n");
			sb.append("<div id='detailDivAttn' style='position:absolute;display:none;background-color: #FFFFFF;filter:alpha(opacity='10');'></div>");
			sb.append("\r\n");
			sb.append("<iframe id='detailDivShim' src='Blank.html' scrolling='no' frameborder='0' style='position:absolute; top:0px; left:0px; display:none;background-color: #FFFFFF;filter:alpha(opacity='10');'></iframe>");
			
			sb.append("\r\n");
			sb.append("<div id='BA_DATAGRID_LOV_DIV' style='overflow:auto;position:absolute;border:solid 2px;border-top-style:none;border-color: #003DDC;display:none;background-color: #E4E4E4;' ></div>");
			sb.append("\r\n");
			if(title.equals(""))
				sb.append("<table cellspacing='0' cellpadding='0' border='0' ><tr><td valign='top'>");
			else
				sb.append("<table cellspacing='0' cellpadding='0' border='0' class='TITLE'><tr><td valign='top'>");
			if(!title.equals(""))
				sb.append("<table cellspacing='1' cellpadding='1' border='0' width='100%'><tr><th align='center' class='TITLE'>"+title+"</th></tr><tr><td width='"+dbgridwidth+"' height='"+dbgridheight+"' class='TITLE'>");
			sb.append("<div id='DBGRIDDetail' style='width:auto;height:auto;position:relative;z-index:10' ></div>");
			sb.append("\r\n");
			
			
//			if(!onloadinit)
//			{
//				sb.append("<input type=\"button\" value=\"明細\" onclick=\"");
//				String startpage="1";
//				if(!pager)
//					startpage="0";
//				sb.append("Detail_select('"+action+"','DBGRIDDetail','','"+startpage+"','"+keyid+"');");
//				sb.append("\" />");
//			}
			
			out.print(sb.toString());
			
			
			BA_Detail_Head_Attr D_H_A = new BA_Detail_Head_Attr();
			//設定SQL語法
			D_H_A.setSQL(sqlstatement);
			//設定其他語法 如 order by ,group by
			D_H_A.setSQLOTHER(" "+sqlother+" ");
			//每一頁的筆數
			D_H_A.setRS_Per_Page(rsperpage);
			
			D_H_A.setPROGRAM(action);
			
			if(pager)
			{
				D_H_A.setPAGENUM(1);
			}
			
			
			//表頭key的id
			D_H_A.setKEYID(keyid);
			//設定GRID寬度
			D_H_A.setGRID_WIDTH(dbgridwidth+"");
			//設定GRID高度
			D_H_A.setGRID_HEIGHT(dbgridheight+"");
			//設定字型大小
			D_H_A.setFONTSIZE(fontsize);
			
			
			D_H_A.setHEIGHT(trheight);
			
//			 取得程式權限
			HashMap pgms = new HashMap();
			if (pageContext.getSession().getAttribute("pgmsauth") != null) {
				pgms = (HashMap) pageContext.getSession().getAttribute("pgmsauth");
			}

			String pgmname =action.substring(0,action.indexOf(".do")); 
				
			int[] auths = (int[]) pgms.get(pgmname);

			if (auths[0] == 1)// 新增權限
				D_H_A.setINSERT_RIGHT(true);
			if (auths[1] == 1)// 刪除權限
				D_H_A.setDELETE_RIGHT(true);
			if (auths[2] == 1)// 修改權限
				D_H_A.setUPDATE_RIGHT(true);
			
			
			//定義欄位資料物件集合
			D_H_A.setBA_Detail_attr(new ArrayList());
			
			pageContext.getSession().setAttribute("D_H_A",D_H_A);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return EVAL_PAGE;
	}
	
	public int doEndTag() throws JspException {
		try {
		StringBuffer sb=new StringBuffer();
		if(onloadinit)
		{
		JspWriter out=pageContext.getOut();
		
		sb.append("<script language='JavaScript'>");
		String startpage="1";
		if(!pager)
			startpage="0";
		sb.append("Detail_select('"+action+"','DBGRIDDetail','','"+startpage+"','"+keyid+"');");
		sb.append("</script>");
		if(!title.equals(""))
			sb.append("</td><tr></table>");
		sb.append("</td><tr></table>");
		out.print(sb.toString());
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SKIP_BODY;
	}
	
	public void release() {
		super.release();
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getRsperpage() {
		return rsperpage;
	}

	public void setRsperpage(int rsperpage) {
		this.rsperpage = rsperpage;
	}

	public boolean isPager() {
		return pager;
	}

	public void setPager(boolean pager) {
		this.pager = pager;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid.replaceAll("\\,","\\{;\\}");
	}

	public int getDbgridheight() {
		return dbgridheight;
	}

	public int getDbgridwidth() {
		return dbgridwidth;
	}

	public int getTrheight() {
		return trheight;
	}

	public void setDbgridheight(int dbgridheight) {
		this.dbgridheight = dbgridheight;
	}

	public void setDbgridwidth(int dbgridwidth) {
		this.dbgridwidth = dbgridwidth;
	}

	public void setTrheight(int trheight) {
		this.trheight = trheight;
	}

	public boolean isOnloadinit() {
		return onloadinit;
	}

	public void setOnloadinit(boolean onloadinit) {
		this.onloadinit = onloadinit;
	}

	public int getFontsize() {
		return fontsize;
	}

	public void setFontsize(int fontsize) {
		this.fontsize = fontsize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSqlother() {
		return sqlother;
	}

	public void setSqlother(String sqlother) {
		this.sqlother = sqlother;
	}
	
	
}
