package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * 
 * @author Gilles Rossi
 *
 */
public class ScrollBoxTag extends LayoutTagSupport {
	
	private String height;
	private String width;
	
	private String styleId;
	private String styleClass;
	private String style;

	private String jspHeight;
	private String jspWidth;
	private String jspStyleId;
	private String jspStyleClass;
	private String jspStyle;

	public void setHeight(String height) {
		this.height = height;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public int doEndLayoutTag() throws JspException {
		TagUtils.write(getPageContext(), "</div>");

		return super.doEndLayoutTag();
	}

	public int doStartLayoutTag() throws JspException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<div");
		if (styleId != null & !"".equals(styleId)) {
			buffer.append(" id=\"").append(styleId).append("\"");
		}
		if (styleClass != null & !"".equals(styleClass)) {
			buffer.append(" class=\"").append(styleClass).append("\"");
		}
		
		buffer.append(" style=\"overflow:auto;");
		if (height != null && !height.equals("")){
			buffer.append("height:").append(height).append(";");
		}
		if (width != null && !width.equals("")){
			buffer.append("width:").append(width).append(";");
		}
		if (style != null) {
			buffer.append(style);
		}
		
		buffer.append("\">");
		TagUtils.write(getPageContext(), buffer.toString());

		return EVAL_BODY_INCLUDE;
	}

	protected void initDynamicValues() {
		super.initDynamicValues();
		jspHeight = height;
		height = Expression.evaluate(height, getPageContext());
		
		jspWidth = width;
		width = Expression.evaluate(width, getPageContext());
	}

	protected void reset() {
		super.reset();
		height = jspHeight;
		jspHeight = null;
		
		width = jspWidth;
		jspWidth = null;
		
		styleId = jspStyleId;
		jspStyleId = null;
		
		styleClass = jspStyleClass;
		jspStyleClass = null;
		
		style = jspStyle;
		jspStyle = null;
	}

	public void release() {
		super.release();
		height = null;
		width = null;
		
		styleId = null;
		styleClass = null;
		style = null;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	
}
