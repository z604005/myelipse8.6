package fr.improve.struts.taglib.layout.renderer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.GridLayoutTag;
import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.ILayoutRenderer;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Grid renderer.
 * 
 * The grid renderer automatically flow children in a table.
 * The table has the specified number of column.
 * 
 * Children are rendered from left to right and top to bottom in lines, 
 * with a specified number of children per line.
 * 
 * @author jnribette
 */
public class BasicGridRenderer implements ILayoutRenderer {
	
	/**
	 * Start a grid.
	 * @param layoutTag the &lt;layout:grid&gt; tag to render.
	 */
	public void doStartLayout(LayoutTag layoutTag) throws JspException {
		// Get tag attributes.
		GridLayoutTag gridLayoutTag = (GridLayoutTag) layoutTag;
		String id = gridLayoutTag.getId();
		String height = gridLayoutTag.getHeight();
		int borderSpacing = gridLayoutTag.getBorderSpacing();
		String styleClass = gridLayoutTag.getStyleClass();
		String width = gridLayoutTag.getWidth();
		String align = gridLayoutTag.getAlign();
		PageContext pageContext = gridLayoutTag.getPageContext();
		
		// If id or height is set, we need an extra HTML div element.
		// id can be used to show/hide the grid content,
		// height can be used to add a scrollbar.
		if (id!=null || height!=null) {
			TagUtils.write(pageContext, "<div style=\"");
			if (height!=null) {
				TagUtils.write(pageContext, "height:");
				TagUtils.write(pageContext, height);
				TagUtils.write(pageContext, ";");
			}
			TagUtils.write(pageContext,"overflow-y:scroll;overflow:-moz-scrollbars-vertical;");
			if (id!=null) {
				TagUtils.write(pageContext, "\" id=\"");
				TagUtils.write(pageContext, id);
			}
			TagUtils.write(pageContext, "\">");
		}
		
		// Start the grid table.
		TagUtils.write(pageContext, "<table border=\"0\" cellspacing=\"");
		TagUtils.write(pageContext, String.valueOf(borderSpacing));
		TagUtils.write(pageContext,"\" cellpadding=\"0\"");
		if (styleClass!=null) {
			TagUtils.write(pageContext, " class=\"");
			TagUtils.write(pageContext, styleClass);
			TagUtils.write(pageContext, "\"");
		}
		if (width!=null) {
			TagUtils.write(pageContext, " width=\"");
			TagUtils.write(pageContext, width);
			TagUtils.write(pageContext, "\"");
		}
		if (align!=null) {
			TagUtils.write(pageContext, " align=\"");
			TagUtils.write(pageContext, align);
			TagUtils.write(pageContext, "\"");
		}
		TagUtils.write(pageContext,">");
	}
	
	/**
	 * End a grid.
	 * @param layoutTag The &lt;layout:grid&gt; tag to render.
	 */
	public void doEndLayout(LayoutTag layoutTag) throws JspException {
		// Get tag attributes.
		GridLayoutTag gridLayoutTag = (GridLayoutTag) layoutTag;
		String id = gridLayoutTag.getId();
		String height = gridLayoutTag.getHeight();
		PageContext pageContext = gridLayoutTag.getPageContext();
		
		// Close table element.
		TagUtils.write(pageContext, "</table>");
		
		// Close potential div element.
		if (id!=null || height!=null) {
			TagUtils.write(pageContext, "</div>");
		}
	}
	
	/**
	 * Prepare for the rendering of a children.
	 */
	public Object doStartChildLayout(LayoutTag layoutTag, StartLayoutEvent startEvent) throws JspException {
		GridLayoutTag gridLayoutTag = (GridLayoutTag) layoutTag;
		PageContext pageContext = startEvent.getSource().getPageContext();
		if (gridLayoutTag.isFirst()) {
			TagUtils.write(pageContext, "<tr>");
		}
		
		return startEvent.consume(pageContext, "");
	}
	
	/**
	 * End the rendering of a children.
	 */
	public Object doEndChildLayout(LayoutTag layoutTag, EndLayoutEvent endEvent) throws JspException {
		// Get tag attributes.
		GridLayoutTag gridLayoutTag = (GridLayoutTag) layoutTag;
		PageContext pageContext = endEvent.getSource().getPageContext();
		boolean space = gridLayoutTag.isSpace();
		
		StringBuffer buffer = new StringBuffer();
		
		// Add an extra empty column to add some space between the child components.
		if (space) {
			buffer.append("<td>&nbsp;</td>");
		}
		
		if (gridLayoutTag.isLast()) {
			buffer.append("</tr>");
		}
		
		Object result = endEvent.consume(pageContext, buffer.toString());
		return result;
	}
}
