package fr.improve.struts.taglib.layout;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.IButtonImageRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;


public class JavascriptImageTag extends BodyLayoutTagSupport {
	/**
	 * Request code.
	 */
	protected String reqCode;
	
	/**
	 * Link.
	 */
	protected String href;
	
	/**
	 * CSS style class
	 */
	protected String styleClass;
	
	/**
	 * Content.
	 */
	private String content;
	
	public int doStartLayoutTag() throws JspException {
		// Start layout tag.
		StringBuffer start = new StringBuffer();
		start.append("<td colspan=\"").append(getLayoutColspan()).append("\">");
		new StartLayoutEvent(this, start.toString()).send();
		
		// Start button tag.
		IButtonImageRenderer renderer = getRenderer();
		renderer.doEndButton(pageContext, this);
		
		return EVAL_BODY_TAG;
	}
	
	public int doAfterBody() throws JspException {
		content = bodyContent.getString();
		try {
			bodyContent.clear();
		} catch (IOException e) {
			throw new JspException("Fail to clear bodyContent");
		}
		return SKIP_BODY;
	}
	
	public int doEndLayoutTag() throws JspException {		
		// Display text.
		IButtonImageRenderer renderer = getRenderer();
		renderer.doPrintLabel(pageContext, this, content);
		content = null;
		
		// End button tag.
		renderer.doStartButton(pageContext, this);
		
		// End layout tag.
		new EndLayoutEvent(this, "</td>").send();
		
		// Continue.
		return EVAL_PAGE;
	}
	
	protected int getLayoutColspan() {
		Skin skin = LayoutUtils.getSkin(pageContext.getSession());
		int span = skin.getFieldInterface().getColumnNumber();
		return span;
	}
	
	protected IButtonImageRenderer getRenderer() {
		Skin skin = LayoutUtils.getSkin(pageContext.getSession());
		IButtonImageRenderer renderer = skin.getButtonRenderer(null);
		return renderer;
	}
	
	/**
	 * Release tag.
	 */
	public void release() {	
		super.release();
		reqCode = null;
		styleClass = null;
		href = null;
	}
	
	/**
	 * Initialize dynamic values.
	 */
	protected void initDynamicValues() throws JspException {
		super.initDynamicValues();
	}
	  
	/**
	 * Reset dynamic values.
	 */
	protected void reset() {
		  super.reset();
	}

	public String getReqCode() {
		return reqCode;
	}

	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}

	public String getStyleClass() {
		return styleClass;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
}
