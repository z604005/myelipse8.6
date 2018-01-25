package fr.improve.struts.taglib.layout;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.struts.config.ActionConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.taglib.html.Constants;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.util.FormUtilsInterface;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Display a form tag using the panel layout.<br>
 * This tag can specify a default action for the action using the DispatchAction class.<br>
 * 
 * @author: Jean-No�l Ribette
 */
public class FormTag extends PanelTag {
	protected String acceptCharset = null;
	
	protected String action = null;
	protected String enctype = null;
	
	protected String focus = null;
	protected String editFocus = null;
	protected String inspectFocus = null;
	protected String createFocus = null;
	protected String jspFocus = null;
	
	
	protected String method = "POST";
	protected String onreset = null;
	protected String onsubmit = null;
	protected String onkeypress = null;
	protected String scope = "session";
	protected String style = null;
	protected String target = null;
	protected String type = null;
	protected String reqCode = null;
	
	protected String styleId;
	
	/**
	 * Required scheme.
	 * If not null, an absolute URL 
	 * with the specified scheme is generated.
	 */
	protected String scheme;
	
	protected org.apache.struts.taglib.html.FormTag formTag = new SchemeFormTag();

	protected int mode = FormUtilsInterface.EDIT_MODE;
	
	/**
	 * @deprecated
	 */
	public static final int CREATE_MODE = FormUtilsInterface.CREATE_MODE;
	
	/**
	 * @deprecated
	 */
	public static final int EDIT_MODE = FormUtilsInterface.EDIT_MODE;
	
	/**
	 * @deprecated
	 */
	public static final int INSPECT_MODE = FormUtilsInterface.INSPECT_MODE;
	
	/**
	 * Layout
	 */
	protected boolean layout = true;
	
	/**
	 * Modified Struts form tag to support scheme.
	 */
	private class SchemeFormTag extends org.apache.struts.taglib.html.FormTag {
		/**
		 * Render action attribute.
		 */
		protected void renderAction(StringBuffer results) {
			String schemeNow = Expression.evaluate(scheme, this.pageContext); 
			if (schemeNow==null || schemeNow.length()==0) {
				// Do nothing : use super.renderAction
				super.renderAction(results);
			} else {
				// Compute absolute URL.
		        HttpServletResponse response = (HttpServletResponse) this.pageContext.getResponse();

		        String host = this.pageContext.getRequest().getServerName();
		        String action = TagUtils.getActionMappingURL(this.action, this.pageContext);
		        
		        results.append(" action=\"");
		        results.append(
		            response.encodeURL(schemeNow + "://" + host + action));		                
		        results.append("\"");
			}
		}
	}

	public FormTag() {
		super();
	//	name = Constants.BEAN_KEY;
	}
	
	public int doEndLayoutTag() throws JspException {
		if (layout) {
			StringBuffer buffer = new StringBuffer();
			doPrintBlankLine(buffer);
			doAfterBody(buffer);
			TagUtils.write(pageContext, buffer.toString());				
			buffer.setLength(0);
			doEndPanel(buffer);
			TagUtils.write(pageContext, buffer.toString());
		}
		
		int ret = formTag.doEndTag();
		
		if (layout) {
			doEndLayout();
		}
				
		formTag.release();
		if(getOnkeypress()!=null && getOnkeypress().length()!=0){
			TagUtils.write(pageContext,"<script language=\"javaScript\">document.forms['" + formTag.getBeanName() + "'].onkeypress="+ getOnkeypress() +";</script>");
		}
		return ret;
	}
	
	public int doStartLayoutTag() throws JspException {
		
		//列印時，可以出現遮罩  Alvin
		TagUtils.write(pageContext,"<div id=\"HEADDIV\" style=\"position:relative;\">");
		//Initialize the struts form tag.
		LayoutUtils.copyProperties(formTag,this);				
		
		//Start the layout.
		StringBuffer buffer = null;
		if (layout) {
			doStartLayout();
		}
		
		// Start the struts form tag.
		int ret = formTag.doStartTag();
		
		if (layout) {
			buffer = new StringBuffer("\n");
			doStartPanel(buffer);
			TagUtils.write(pageContext,buffer.toString());
		}
		
	
		// the mode must be calculated after the struts formTag is begun.
		mode = getSkin().getFormUtils().getFormDisplayMode(pageContext);
		computeDisplayMode();
		
		if (layout) {
			buffer.setLength(0);
		}
		// if needed, add an hidden input field setting the reqCode.
		processRequestCode(buffer);
	
		// recalculate the key.
		processKey();
		
		// recalculate the focus.
		processFocus();
		
		if (layout) {
			doPrintTitle(buffer);
			doBeforeBody(buffer);
			//減少標頭行距
			//doPrintBlankLine(buffer);
			TagUtils.write(pageContext,buffer.toString());
		}
		return ret;	
	}

	/**
	 * Specify the default action parameter if Action class is subclass of DispatchAction.
	 */ 
	protected void processRequestCode(StringBuffer buffer) throws JspException {
		if (reqCode!=null) {
			try {
				String parameter = computeActionParameter(pageContext, action);
			
				// set the default reqCode if we are posting to a dispatch action
				if (parameter!=null) {
					//Add id by John 2014/07/17
					buffer.append("<input type=\"hidden\" name=\"");
					buffer.append(parameter);
					buffer.append("\" value=\"");
					buffer.append(reqCode);
					buffer.append("\" id=\"");
					buffer.append(parameter);
					buffer.append("\">\n");
					
					//Set the EMS ctrl method edit by joe 2013/04/16
					//Add id by John 2014/07/17
					buffer.append("<input type=\"hidden\" name=\"");
					buffer.append("reqCodeMethod");
					buffer.append("\" value=\"");
					buffer.append("");
					buffer.append("\" id=\"");
					buffer.append("reqCodeMethod");
					buffer.append("\">\n");
					
					//set the default Detail_formType, Detail_formAction
					//Add id by John 2014/07/17
					buffer.append("<input type=\"hidden\" name=\"");
					buffer.append("Detail_formType");
					buffer.append("\" value=\"");
					buffer.append("");
					buffer.append("\" id=\"");
					buffer.append("Detail_formType");
					buffer.append("\">\n");
					buffer.append("<input type=\"hidden\" name=\"");
					buffer.append("Detail_formAction");
					buffer.append("\" value=\"");
					buffer.append("");
					buffer.append("\" id=\"");
					buffer.append("Detail_formAction");
					buffer.append("\">\n");
					
				}
			} catch (Exception e) {				
				JspException jspe = new JspException("BaseHandlerTag - cannot acces the DispatchAction parameter: " + e.getMessage() + "(" + e.getClass().getName() + ")");
				TagUtils.saveException(pageContext, jspe);
				throw jspe;				
			}
		}
	}
	
	/**
	 * Look for the struts action parameter value defined in struts-config.	 
	 */
	public static String computeActionParameter(PageContext in_pageContext, String in_actionName) {
		String value = computeActionName(in_actionName);
		ModuleConfig lc_moduleConfig = TagUtils.getModuleConfig(in_pageContext);
		ActionConfig lc_actionConfig = lc_moduleConfig.findActionConfig(value);		
		String parameter = lc_actionConfig.getParameter();
		return parameter;
	}
	
	/**
	 * Retrurn the current form action parameter.
	 */
	public static String computeActionParameter(PageContext in_pageContext) {
		org.apache.struts.taglib.html.FormTag lc_strutsFormTag = findFormTag(in_pageContext);
		String action = lc_strutsFormTag==null ? "" : lc_strutsFormTag.getAction();
	    String parameter = FormTag.computeActionParameter(in_pageContext, action);
	    return parameter;
	}
	
	private static org.apache.struts.taglib.html.FormTag findFormTag(PageContext in_pageContext) {
		org.apache.struts.taglib.html.FormTag lc_strutsFormTag = (org.apache.struts.taglib.html.FormTag) in_pageContext.findAttribute(Constants.FORM_KEY);
		if (lc_strutsFormTag==null && !LayoutUtils.getNoErrorMode()) {
			throw new IllegalStateException("reqCode can not be set if tag is not inside a form tag");
		}
		return lc_strutsFormTag;
	}

	/**
	 * Return the current form name.
	 */
	public static String computeFormName(PageContext in_pageContext) {
		org.apache.struts.taglib.html.FormTag lc_strutsFormTag = findFormTag(in_pageContext);
		return lc_strutsFormTag.getBeanName();
	}

	/**
	 * Look for the struts action name as defined in struts-config.
	 */
	protected static String computeActionName(String in_actionName) {
		return TagUtils.getActionMappingName(in_actionName);
	}

	/**
	 * Add .edit /.create or .inspect to the end of the key if needed.
	 */
	protected void processKey() {
		if (key!=null && LayoutUtils.getUseFormDisplayMode()) {
			switch (mode) {
				case FormUtilsInterface.EDIT_MODE:
					key = key + ".edit";
					break;
				case FormUtilsInterface.CREATE_MODE:
					key = key + ".create";
					break;
				case FormUtilsInterface.INSPECT_MODE:
					key = key + ".inspect";
			}
		}
	}
	
	/**
	 * Set the focus according to the form display mode.
	 */
	protected void processFocus() {		
		switch (mode) {
			case FormUtilsInterface.EDIT_MODE:
				if (editFocus!=null) {
					formTag.setFocus(editFocus);
				}
				break;
			case FormUtilsInterface.INSPECT_MODE:
				if (inspectFocus!=null) {
					formTag.setFocus(inspectFocus);				
				}
				break;
			case FormUtilsInterface.CREATE_MODE:
				if (createFocus!=null) {
					formTag.setFocus(createFocus);
				}
				break;
		}
	}

public String getAction() {
	return action;
}
	public int getDisplayMode() { return mode; }
public String getEnctype() {
	return enctype;
}
public String getFocus() {
	return focus;
}
public String getMethod() {
	return method;
}
public String getOnreset() {
	return onreset;
}
public String getOnsubmit() {
	return onsubmit;
}
public String getOnkeypress() {
	return onkeypress;
}
public String getScope() {
	return scope;
}
public String getReqCode() {
	return reqCode;
}
public String getStyle() {
	return style;
}
public String getTarget() {
	return target;
}
public String getType() {
	return type;
}
public void release() {
	super.release();
	acceptCharset = null;
	action = null;
	method = null;
	scope = null;
	//name = Constants.BEAN_KEY;
	mode = FormUtilsInterface.EDIT_MODE;
	reqCode = null;
	onkeypress = null;
	onreset = null;
	onsubmit = null;
	
	focus = null;
	editFocus = null;
	createFocus = null;
	inspectFocus = null;
	jspFocus = null;
	styleId = null;	
	scheme  = null;
	
	layout = true;
	
	formTag.release();
}
/**
 * Set the action for the form tag. <br>
 * If the init-parameter 'struts-layout-mode' is equal to 'noerror' the action is set to '/default'.
 */
public void setAction(String action) {
	this.action = action;
	if (LayoutUtils.getNoErrorMode()) this.action = "/default";

}
public void setEnctype(String enctype) {
	this.enctype = enctype;
}
public void setFocus(String focus) {
	this.focus = focus;
}
public void setMethod(String method) {
	this.method = method;
}
public void setOnreset(String onreset) {
	this.onreset = onreset;
}
public void setOnsubmit(String onsubmit) {
	this.onsubmit = onsubmit;
}
public void setOnkeypress(String onkeypress) {
	this.onkeypress = onkeypress;
}
	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}
public void setScope(String scope) {
	this.scope = scope;
}
public void setStyle(String style) {
	this.style = style;
}
public void setTarget(String target) {
	this.target = target;
}
public void setType(String type) {
	this.type = type;
}
	/**
	 * Returns the createFocus.
	 * @return String
	 */
	public String getCreateFocus() {
		return createFocus;
	}

	/**
	 * Returns the editFocus.
	 * @return String
	 */
	public String getEditFocus() {
		return editFocus;
	}

	/**
	 * Returns the inspectFocus.
	 * @return String
	 */
	public String getInspectFocus() {
		return inspectFocus;
	}

	/**
	 * Sets the createFocus.
	 * @param createFocus The createFocus to set
	 */
	public void setCreateFocus(String createFocus) {
		this.createFocus = createFocus;
	}

	/**
	 * Sets the editFocus.
	 * @param editFocus The editFocus to set
	 */
	public void setEditFocus(String editFocus) {
		this.editFocus = editFocus;
	}

	/**
	 * Sets the inspectFocus.
	 * @param inspectFocus The inspectFocus to set
	 */
	public void setInspectFocus(String inspectFocus) {
		this.inspectFocus = inspectFocus;
	}

	protected void initDynamicValues() {
		// Super init.
		super.initDynamicValues();
		
		// Compute focus value.
		jspFocus = focus;
		focus = Expression.evaluate(focus, pageContext);
	}
	protected void reset() {
		// Reset focus value.
		focus = jspFocus;
		jspFocus = null;
		
		// Super reset.
		super.reset();
	}

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getAcceptCharset() {
		return acceptCharset;
	}

	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public boolean isLayout() {
		return layout;
	}

	public void setLayout(boolean layout) {
		this.layout = layout;
	}

	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		if (layout) {
			return super.processEndLayoutEvent(in_event);
		} else {
			return Boolean.FALSE;
		}
	}

	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		if (layout) {
			return super.processStartLayoutEvent(in_event);
		} else {
			return Boolean.FALSE;
		}
	}
}
