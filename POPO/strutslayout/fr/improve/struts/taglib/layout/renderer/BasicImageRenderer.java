package fr.improve.struts.taglib.layout.renderer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.JavascriptImageTag;
import fr.improve.struts.taglib.layout.util.IButtonImageRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Basic renderer for image button.
 * This allows to create auto-expanding round button.
 * 
 * TODO
 * - compute the name of the reqCode attribute (it may not be reqCode)
 * - don't generate a reqCode if reqCode is null (not everybody is using DispatchAction)
 * - compute the name of the HTML form elements (there may be several form in the page) 
 * 
 * @author jribette
 */
public class BasicImageRenderer implements IButtonImageRenderer {
	
	public void doEndButton(PageContext context, JavascriptImageTag tag) throws JspException {
//		 Compute onclick JS code.
		String javascript = null;
		
		if (tag.getReqCode()!= null) {
			javascript = "return getParentForm(this, '"+tag.getReqCode()+"');";
		} 
		if (tag.getHref()!= null) {
			javascript = "window.location = '"+tag.getHref()+"';";
		}
	
		// Start table.
		TagUtils.write(context, "<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" ");
		if (javascript!=null){
			TagUtils.write(context, "onclick=\"" + javascript +"\" ");
		}
		TagUtils.write(context, "onmouseover=\"this.style.cursor='hand'\"");
		TagUtils.write(context, ">\n");
		// Start row.
		TagUtils.write(context, "<tr>");
		
		// Left cell.
	    TagUtils.write(context, "<td class=\"");
	    TagUtils.write(context, tag.getStyleClass());
	    TagUtils.write(context, "_left\">");
	    TagUtils.write(context, "<img src=\"");
	    TagUtils.write(context, LayoutUtils.getSkin(context.getSession()).getImageDirectory(context.getRequest()));
	    TagUtils.write(context, "clearpixel.gif\" width=\"1\" height=\"1\"/>");
	    TagUtils.write(context, "</td>");	   
	}
	public void doPrintLabel(PageContext context, JavascriptImageTag tag, String label) throws JspException {
		// Center cell with label.
		TagUtils.write(context, "<td class=\"");
		TagUtils.write(context, tag.getStyleClass());
		TagUtils.write(context, "\">");
		TagUtils.write(context, label);
		TagUtils.write(context, "</td>");
		
	}
	public void doStartButton(PageContext context, JavascriptImageTag tag) throws JspException {
		// Right cell.
	    TagUtils.write(context, "<td class=\"");
	    TagUtils.write(context, tag.getStyleClass());
	    TagUtils.write(context, "_right\">");
	    TagUtils.write(context, "<img src=\"");
	    TagUtils.write(context, LayoutUtils.getSkin(context.getSession()).getImageDirectory(context.getRequest()));
	    TagUtils.write(context, "clearpixel.gif\" width=\"1\" height=\"1\"/>");
	    TagUtils.write(context, "</td>");
	    
	    // Close row and table.
		TagUtils.write(context, "</tr></table>");
	}
}
