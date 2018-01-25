package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.PanelTag;
import fr.improve.struts.taglib.layout.util.BasicPanel;

/**
 * A basic panel that contains a scrollable div
 * @author bbu
 */
public class ScrollablePanel extends BasicPanel {

	private String maxHeight;
	private String maxWidth;
	private PageContext pageContext;
	
	protected void doStartBodyContent(StringBuffer buffer, String align){
		buffer.append("<div style=\"overflow:auto;");
		if(maxHeight!=null && !maxHeight.equals("")){
			buffer.append("height:").append(maxHeight).append(";");
		}
		if(maxWidth!=null && !maxWidth.equals("")){
			buffer.append("width:").append(maxWidth).append(";");
		}
		buffer.append("\">");
		
		super.doStartBodyContent(buffer, align);
	}
	
	protected void doEndBodyContent(StringBuffer buffer){
		super.doEndBodyContent(buffer);
		
		buffer.append("</div>");
	}
	
	protected PageContext getPageContext() {
		return pageContext;
	}
	
	public void init(PageContext pageContext, String in_styleClass, TagSupport in_panel) throws JspException {
		super.init(pageContext, in_styleClass, in_panel);
		this.pageContext = pageContext;
		
		maxHeight = ((PanelTag)in_panel).getMaxHeight();
		maxWidth = ((PanelTag)in_panel).getMaxWidth();
	}

	protected String getMaxHeight() {
		return maxHeight;
	}
	protected String getMaxWidth() {
		return maxWidth;
	}
}
