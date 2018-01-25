package fr.improve.struts.taglib.layout.renderer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.collection.NestedTreeCollectionTag;
import fr.improve.struts.taglib.layout.util.CollectionInterface;
import fr.improve.struts.taglib.layout.util.ITreeGridRenderer;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Simple renderer for treegrids.
 * 
 * @author Jean-Noel RIBETTE
 */
public class TreeGridRenderer implements CollectionInterface, ITreeGridRenderer{
	/**
	 * Collection tag.
	 */
	private CollectionTag collectionTag;
	
	/**
	 * Tree column styleClass.
	 */
	private String treeClass;
	
	/**
	 * Global collection styleClass
	 */
	private String styleClass;
	
	/**
	 * Init renderer.
	 */
	public void init(PageContext pageContext, String in_styleClass, TagSupport in_tag) throws JspException {	
		styleClass = in_styleClass;
		collectionTag = (CollectionTag) in_tag;
	}
	
	/**
	 * Print collection title.
	 */
	public void doPrintTitle(StringBuffer buffer, String title) {
		if (title!=null) {
			buffer.append("<p>").append(title).append("</p>");
		}
	}
	
	public void doStartPanel(StringBuffer buffer, String align, String width) {
		buffer.append("<table");
		if (styleClass!=null) {
			buffer.append(" class=\"").append(styleClass).append("\"");
		}
		buffer.append(">");
	}
	
	public void doBeforeBody(StringBuffer buffer, String align) throws JspException {
		// Don't do anything.
	}
	
	public void doAfterBody(StringBuffer buffer) throws JspException {
		// Don't do anything.
	}
	
	public void doStartHeaders(StringBuffer buffer) {
		buffer.append("<tr>");
	}

	public void doPrintHeader(StringBuffer buffer, String header, String width, String sortUrl) {
		buffer.append("<th");
		if (width!=null) {
			buffer.append(" width=\"").append(width).append("\"");
		}
		if (styleClass!=null) {
			buffer.append(" class=\"").append(styleClass).append("\"");
		}
		buffer.append(">").append(header).append("</th>");
	}
	
	public void doEndHeaders(StringBuffer buffer) {
		buffer.append("</tr>");
	}
	
	public void doStartItems(StringBuffer buffer) throws JspException {
		buffer.append("<tr>");
	}
	
	public void doPrintItem(StringBuffer buffer, String item, String[] styleClass, String id) {
		buffer.append("<td");
		boolean addTreeClass = collectionTag.getColumn()==0;
		if (styleClass!=null && styleClass.length!=0 || addTreeClass) {
			buffer.append(" class=\"");			
			if (addTreeClass) {
				buffer.append(treeClass);
			}
			for (int i=0;i<styleClass.length;i++) {
				if (i>0 || addTreeClass) {
					buffer.append(' ');
				}
				buffer.append(styleClass[i]);				
			}
			buffer.append("\"");
		}
		buffer.append(">").append(item).append("</td>");
	}
	
	public void doEndItems(StringBuffer buffer) {
		buffer.append("</tr>");
	}
	
	public void doEndPanel(StringBuffer buffer) {
		buffer.append("</table>");
	}
	
	public void doPrintBlankLine(StringBuffer buffer, int cols) throws JspException {
		// do nothing.
	}
	
	public void doPrintEmptyCollection(StringBuffer out_buffer, String in_message) {
		// do nothing.		
	}
	
	public void doRenderTreeNode(CollectionTag collectionTag, NestedTreeCollectionTag nestedTreeCollectionTag, String item, int level) throws JspException {
		PageContext pageContext = nestedTreeCollectionTag.getPageContext();
		treeClass = styleClass + "_LEVEL_" + level;
		TagUtils.write(pageContext, "<tr><th colspan=\"");
		TagUtils.write(pageContext, String.valueOf(collectionTag.getHeaders().size()));
		TagUtils.write(pageContext, "\" class=\"");
		TagUtils.write(pageContext, treeClass);
		TagUtils.write(pageContext, "\" onclick=\"changeTreeGridNode(this, ");
		TagUtils.write(pageContext, String.valueOf(level));
		TagUtils.write(pageContext,");\">");
		TagUtils.write(pageContext, item);
		TagUtils.write(pageContext, "</th></tr>"); 
	}
	
	public void doPrintEmptyRows(StringBuffer out_buffer) {
		// TODO Auto-generated method stub
		
	}	
	
}
