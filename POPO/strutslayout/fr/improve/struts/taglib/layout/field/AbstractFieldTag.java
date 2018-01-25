package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.Constants;
import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.HelpFieldRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * Abstract class for the usual input fields (text, textarea, password and checkbox). <br>
 *
 * The content of the tag is displayed after the input field 
 * so that buttons or other elements can be added
 *
 * @author: Jean-Noel Ribette
 * @author: Gilles Rossi
 **/
public abstract class AbstractFieldTag extends AbstractLayoutFieldTag {
	/**
	 * The fieldTag from the Struts html taglib.
	 */
	private BaseHandlerTag fieldTag;

	/**
	 * The fieldTag parameters.
	 */
	protected String value;
	protected String accept;
	protected String cols;
	protected String maxlength;
	protected String rows;
	protected String size;
	protected String accesskey;
	protected String onblur;
	protected String onclick;
	protected String ondblclick;
	protected String onfocus;
	protected String onkeydown;
	protected String onkeyup;
	protected String onkeypress;
	protected String onmouseup;
	protected String onmouseout;
	protected String onmousemove;
	protected String onmouseover;
	protected String onmousedown;
	
	protected String tabIndex;
	protected String onchange;
	protected String onselect;
	protected boolean filter = true;
	protected String onHelperClick;
	protected String keyUnit;

	/**
	 * The field value set in the jsp.	
	 */
	protected String jspValue;
	
	public AbstractFieldTag() {
		name = Constants.BEAN_KEY;
		fieldTag = createStrutsTag();
	}
	
	protected BaseHandlerTag getStrutsTag() {
		return fieldTag;
	}
	
	private boolean hasAfterValue() {
		return (keyUnit!=null && !keyUnit.equals("")) ||
		(onHelperClick!=null && !onHelperClick.equals("")) ||
		(getHelp()!=null && !getHelp().equals(""));
	}
	
	/**
	 * @return the created Struts Tag (extending BaseHandlerTag) to use as value tag for rendering the current component
	 */
	protected abstract BaseHandlerTag createStrutsTag();
	
protected void doAfterValue() throws JspException {
		
		if(hasAfterValue()){
			TagUtils.write(pageContext, "</td>");
		}
		
		//If we've got a unit to add 
		if(keyUnit!=null && !keyUnit.equals("")){
			String unit = LayoutUtils.getLabel(pageContext, bundle, keyUnit, null, false);
			TagUtils.write(pageContext, "<td><span ");
			
			if(styleClass!=null && !styleClass.equals("")){
				TagUtils.write(pageContext, "class=\""+styleClass+"\"");
			}
			TagUtils.write(pageContext, ">"+unit+"</span></td>"); 
		}
		
		//onHelperClick
		if(onHelperClick!=null && !onHelperClick.equals("")){
			StringBuffer sb = new StringBuffer("<td>");
			
			sb.append("<img src=\"");
            sb.append(getSkin().getImageDirectory(pageContext.getRequest()));
            sb.append("/");
            sb.append(getSkin().getProperty("field.helper"));
            sb.append("\"");
            if (!isDisabledAsBoolean() && !isReadonly()) {
            	sb.append(" onclick=\"");
            	sb.append(onHelperClick);
            	sb.append("\"");
			}
//            sb.append(" style=\"cursor: pointer;\"");
            sb.append(" class=\"onHelperClick\"");
            sb.append(">");
            sb.append("</td>");
             
            TagUtils.write(pageContext,sb.toString()); 
		}	
		
		// help
		if ("AFTER_VALUE".equals(getSkin().getProperty(Skin.FIELD_HELP))) {
			StringBuffer sb = new StringBuffer("<td>");
			
			if (getSkin().getFieldInterface(getModel()) instanceof HelpFieldRenderer) {
				((HelpFieldRenderer)getSkin().getFieldInterface(getModel())).doHelp(sb, this);				
			}
            sb.append("</td>");

            TagUtils.write(pageContext,sb.toString()); 
		}
		
		if (hasAfterValue()){
			TagUtils.write(pageContext,"</tr></table>");
		}
		
	}
public void setOnHelperClick(String onHelperClick) {
	this.onHelperClick = onHelperClick;
}
/**
 * This method is called before displaying the value.
 * This is the place to write something before the value, and set the fieldTag value.
 * @return true - process the tag<br>
 *		   false - skip the tag
 */
protected boolean doBeforeValue() throws JspException {
	
	if (hasAfterValue()){
		TagUtils.write(pageContext, "<table><tr><td>");
	}
	
	return true;
}
public void setKeyUnit(String keyUnit) {
	this.keyUnit = keyUnit;
}
public int doEndEditField() throws JspException {
	return EVAL_PAGE;
}
public int doEndInspectField() throws JspException {
	return EVAL_PAGE;
}

public String getJavascriptType() {
	return "TEXT";
}

public int doStartEditField() throws JspException {	
	
	// Maybe do something before displaying the value
	if (!doBeforeValue()) return SKIP_BODY;

	// copy properties
	copyProperties(fieldTag);
	
	// add checkValue to onchange. 
	boolean check = isRequired();		
	if (check) {
		StringBuffer lc_buffer = new StringBuffer("checkValue(this, '");
		lc_buffer.append(property).append("','");
		lc_buffer.append(getJavascriptType());
		lc_buffer.append("',").append(check).append(");");
		if (fieldTag.getOnchange()!=null) {
			lc_buffer.append(fieldTag.getOnchange());
		}
		fieldTag.setOnchange(lc_buffer.toString());
	}
	
	// add autoskip to onkeyup.
	if (getSkin().isAutoskipActivated()) {
		StringBuffer lc_buffer = new StringBuffer("checkAutoSkip(this, event);");
		if (fieldTag.getOnkeyup()!=null) {
			lc_buffer.append(fieldTag.getOnkeyup());
		}
		fieldTag.setOnkeyup(lc_buffer.toString());
	}

	fieldTag.doStartTag();
	fieldTag.doEndTag();

	doAfterValue();
						
	return EVAL_BODY_INCLUDE;	
}
protected void copyProperties (BaseHandlerTag in_dest) throws JspException {
	// PENDING nice, but not that fast.
	// LayoutUtils.copyProperties(in_dest, this);

	// PENDING not nice, but fast.
	fieldTag.setAccesskey(getAccesskey());
	// alt
	// altkey
	//fieldTag.setBundle(getBundle()); only used in Struts 1.1 for altKey and titleKey, which are not use here.	
	fieldTag.setDisabled(isDisabledAsBoolean());
	fieldTag.setId(getId());
	// indexed
	// locale
	fieldTag.setOnblur(getOnblur());
	fieldTag.setOnchange(Expression.evaluate(getOnchange(), pageContext));
	fieldTag.setOnclick(getOnclick());
	fieldTag.setOndblclick(getOndblclick());
	fieldTag.setOnfocus(getOnfocus());
	fieldTag.setOnkeydown(getOnkeydown());
	fieldTag.setOnkeypress(getOnkeypress());
	fieldTag.setOnkeyup(getOnkeyup());
	fieldTag.setOnmousedown(getOnmousedown());
	fieldTag.setOnmousemove(getOnmousemove());
	fieldTag.setOnmouseout(getOnmouseout());
	fieldTag.setOnmouseover(getOnmouseover());
	fieldTag.setOnmouseup(getOnmouseup());
	fieldTag.setOnselect(getOnselect());
	fieldTag.setPageContext(getPageContext());
	fieldTag.setParent(getParent());
	fieldTag.setReadonly(isReadonly());
	fieldTag.setStyle(getStyle());
	fieldTag.setStyleClass(getStyleClass());
	fieldTag.setStyleId(getStyleId());
	fieldTag.setTabindex(getTabindex());
	fieldTag.setTitle(LayoutUtils.getLabel(getPageContext(), getBundle(), Expression.evaluate(getTooltip(), pageContext), null, false));
}
	
public int doStartInspectField() throws JspException {	
	// Maybe do something before dispaying the value.
	if (!doBeforeValue()) return SKIP_BODY;
		
	Object lc_value = getFieldValue();
	if (lc_value!=null) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<span class=\"");
		buffer.append(styleClass);
		buffer.append("\">");
		if (filter) {
			buffer.append(ResponseUtils.filter(lc_value.toString()));
		} else {
			buffer.append(lc_value.toString());
		}
		buffer.append("</span>");
		TagUtils.write(pageContext, buffer.toString());			
	}

	doAfterValue();
	
	return EVAL_BODY_INCLUDE;	
}
public String getAccept() {
	return accept;
}
public String getCols() {
	return cols;
}
	protected Object getFieldValue() throws JspException{
		if (value!=null) return value;
		return LayoutUtils.getBeanFromPageContext(pageContext, name, property);
	}
public String getMaxlength() {
	return maxlength;
}
public String getRows() {
	return rows;
}
public String getSize() {
	return cols;
}
public BaseHandlerTag getFieldTag() {
	return fieldTag;
}
public String getValue() {
	try {	
	    Object lc_value = getFieldValue();
    	if (lc_value != null) return lc_value.toString();
	} catch (JspException e) {
		// PENDING
	}
   	return "";
}
protected void reset() {
	super.reset();		
	if (fieldTag!=null) {
		fieldTag.release();
		fieldTag = createStrutsTag();
	}		
	value = jspValue;
	jspValue = null;
}
public void release() {
	super.release();
	
	name = Constants.BEAN_KEY;
	accept = null;
	accesskey = null;
	cols = null;
	filter = true;
	maxlength = null;
	onblur = null;
	onchange = null;
	onclick = null;
	ondblclick = null;
	onfocus = null;
	onkeydown = null;
	onkeyup = null;
	onmousedown = null;
	onmousemove = null;
	onmouseout = null;
	onmouseover = null;
	onmouseup = null;
	onselect = null;
	rows = null;
	size = null;
	tabIndex = null;	
	value = null;
}
public void setAccept(String accept) {
	this.accept = accept;
}
/**
 * Insert the method's description here.
 * Creation date: (19/02/01 11:29:27)
 * @param newCols int
 */
public void setCols(String newCols) {
	cols = newCols;
}
public void setMaxlength(String maxlength) {
	this.maxlength = maxlength;
}
/**
 * Insert the method's description here.
 * Creation date: (19/02/01 11:29:27)
 * @param newRows int
 */
public void setRows(String newRows) {
	rows = newRows;
}
/**
 * Insert the method's description here.
 * Creation date: (19/02/01 11:29:27)
 * @param newSize int
 */
public void setSize(String newSize) {
	cols = newSize;
}
public void setValue(String value) {
	this.value = value;
}
	/**
	 * Gets the accessKey.
	 * @return Returns a String
	 */
	public String getAccesskey() {
		return accesskey;
	}

	/**
	 * Sets the accessKey.
	 * @param accessKey The accessKey to set
	 */
	public void setAccesskey(String accesskey) {
		this.accesskey = accesskey;
	}

	/**
	 * Gets the onblur.
	 * @return Returns a String
	 */
	public String getOnblur() {
		return onblur;
	}

	/**
	 * Sets the onblur.
	 * @param onblur The onblur to set
	 */
	public void setOnblur(String onblur) {
		this.onblur = onblur;
	}

	/**
	 * Gets the onclick.
	 * @return Returns a String
	 */
	public String getOnclick() {
		return onclick;
	}

	/**
	 * Sets the onclick.
	 * @param onclick The onclick to set
	 */
	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * Gets the ondblclick.
	 * @return Returns a String
	 */
	public String getOndblclick() {
		return ondblclick;
	}

	/**
	 * Sets the ondblclick.
	 * @param ondblclick The ondblclick to set
	 */
	public void setOndblclick(String ondblclick) {
		this.ondblclick = ondblclick;
	}

	/**
	 * Gets the onkeyup.
	 * @return Returns a String
	 */
	public String getOnkeyup() {
		return onkeyup;
	}

	/**
	 * Sets the onkeyup.
	 * @param onkeyup The onkeyup to set
	 */
	public void setOnkeyup(String onkeyup) {
		this.onkeyup = onkeyup;
	}

	/**
	 * Gets the onkeydown.
	 * @return Returns a String
	 */
	public String getOnkeydown() {
		return onkeydown;
	}

	/**
	 * Sets the onkeydown.
	 * @param onkeydown The onkeydown to set
	 */
	public void setOnkeydown(String onkeydown) {
		this.onkeydown = onkeydown;
	}

	/**
	 * Gets the onfocus.
	 * @return Returns a String
	 */
	public String getOnfocus() {
		return onfocus;
	}

	/**
	 * Sets the onfocus.
	 * @param onfocus The onfocus to set
	 */
	public void setOnfocus(String onfocus) {
		this.onfocus = onfocus;
	}

	/**
	 * Gets the onkeypress.
	 * @return Returns a String
	 */
	public String getOnkeypress() {
		return onkeypress;
	}

	/**
	 * Sets the onkeypress.
	 * @param onkeypress The onkeypress to set
	 */
	public void setOnkeypress(String onkeypress) {
		this.onkeypress = onkeypress;
	}

	/**
	 * Gets the onmouseout.
	 * @return Returns a String
	 */
	public String getOnmouseout() {
		return onmouseout;
	}

	/**
	 * Sets the onmouseout.
	 * @param onmouseout The onmouseout to set
	 */
	public void setOnmouseout(String onmouseout) {
		this.onmouseout = onmouseout;
	}

	/**
	 * Gets the onmouseup.
	 * @return Returns a String
	 */
	public String getOnmouseup() {
		return onmouseup;
	}

	/**
	 * Sets the onmouseup.
	 * @param onmouseup The onmouseup to set
	 */
	public void setOnmouseup(String onmouseup) {
		this.onmouseup = onmouseup;
	}

	/**
	 * Gets the onmousemove.
	 * @return Returns a String
	 */
	public String getOnmousemove() {
		return onmousemove;
	}

	/**
	 * Sets the onmousemove.
	 * @param onmousemove The onmousemove to set
	 */
	public void setOnmousemove(String onmousemove) {
		this.onmousemove = onmousemove;
	}

	/**
	 * Gets the onmouseover.
	 * @return Returns a String
	 */
	public String getOnmouseover() {
		return onmouseover;
	}

	/**
	 * Sets the onmouseover.
	 * @param onmouseover The onmouseover to set
	 */
	public void setOnmouseover(String onmouseover) {
		this.onmouseover = onmouseover;
	}

	/**
	 * Gets the onmousedown.
	 * @return Returns a String
	 */
	public String getOnmousedown() {
		return onmousedown;
	}

	/**
	 * Sets the onmousedown.
	 * @param onmousedown The onmousedown to set
	 */
	public void setOnmousedown(String onmousedown) {
		this.onmousedown = onmousedown;
	}

	/**
	 * Gets the tabIndex.
	 * @return Returns a String
	 */
	public String getTabindex() {
		return tabIndex;
	}

	/**
	 * Sets the tabIndex.
	 * @param tabIndex The tabIndex to set
	 */
	public void setTabindex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	/**
	 * Gets the onselect.
	 * @return Returns a String
	 */
	public String getOnselect() {
		return onselect;
	}

	/**
	 * Sets the onselect.
	 * @param onselect The onselect to set
	 */
	public void setOnselect(String onselect) {
		this.onselect = onselect;
	}

	/**
	 * Gets the onchange.
	 * @return Returns a String
	 */
	public String getOnchange() {
		return onchange;
	}

	/**
	 * Sets the onchange.
	 * @param onchange The onchange to set
	 */
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}

	/**
	 * Sets the filter.
	 * @param filter The filter to set
	 */
	public void setFilter(boolean filter) {
		this.filter = filter;
	}

	/**
	 * Compute EL values.
	 */
	protected void initDynamicValues() {
		super.initDynamicValues();
		
		// compute the runtime value of the "value" attribute. 
		jspValue = value;
		value = Expression.evaluate(jspValue, pageContext);
	}
}
