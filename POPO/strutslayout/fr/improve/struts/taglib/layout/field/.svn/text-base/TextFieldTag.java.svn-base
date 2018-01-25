package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.TextTag;
import org.apache.struts.util.ResponseUtils;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.formatter.FormatException;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;
/**
 * A simple text field tag, but that can use a formatter to customized the displayed value.
 * @author: Jean-No�l Ribette
 * @author: Gilles Rossi
 */
public class TextFieldTag extends AbstractFieldTag {
	
	/**
	 * Inspect formatter.
	 */
	protected String format;
	
	/**
	 * Edit formater
	 */
	protected String editFormat;
	
	protected String jspFormat;
	protected String jspEditFormat;
	
	/**
	 * Return the value(s) that will be displayed.
	 */
	protected java.lang.Object getFieldValue() throws JspException {
		Object lc_value = super.getFieldValue();
		if (lc_value != null) {
			switch (getFieldDisplayMode()) {
				case MODE_EDIT:
					if (editFormat!=null) try {
						lc_value = getSkin().getFormatter().format(lc_value, editFormat, pageContext);
					} catch (FormatException fe) {
						TagUtils.saveException(pageContext, fe);
						throw new JspException("Format " + editFormat + "failed: " + fe.getMessage());
					}
					break;
				default:
					if (format!=null) try {
						lc_value = getSkin().getFormatter().format(lc_value, format, pageContext);
					} catch (FormatException fe) {
						TagUtils.saveException(pageContext, fe);
						throw new JspException("Format " + format + "failed: " + fe.getMessage());
					}
					break;
			}						 	
		}
		return lc_value;
	}

	public int doStartInspectField() throws JspException {
		// Maybe do something before displaying the value.
		if (!doBeforeValue())
			return SKIP_BODY;

		Object lc_value = getFieldValue();
		if (lc_value != null) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("<span class=\"");
			buffer.append(styleClass);
			buffer.append("\">");
			if (format == null) {
				if (filter) {
					buffer.append(ResponseUtils.filter(lc_value.toString()));
				} else {
					buffer.append(lc_value.toString());
				}
			} else {
				buffer.append(lc_value);
			}
			buffer.append("</span>");
			TagUtils.write(pageContext, buffer.toString());
		}

		doAfterValue();

		return EVAL_BODY_INCLUDE;
	}

	public void release() {
		super.release();
		format = null;
		editFormat = null;
	}
	public void setType(String type) {
		format = type;
	}
	public void setEditType(String type) {
		editFormat = type;
	}
	public String getType() {
		return format;
	}
	
	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {
		super.copyProperties(in_dest);
		getTextTag().setCols(getCols());
		getTextTag().setMaxlength(getMaxlength());
		getTextTag().setProperty(getProperty());
		getTextTag().setRows(getRows());
		getTextTag().setValue(getValue());
		getTextTag().setAccept(getAccept());
		getTextTag().setName(getName());
		
		//handle layout:text onkeypress event, by joe 2011/09/21
		String control_enter_event = "return nextFiled(); ";
		String old_onkeypress = this.getOnkeypress();
		if(!"".equals(old_onkeypress) && old_onkeypress != null){
			if((old_onkeypress.trim()).endsWith(";")){
				if (old_onkeypress.indexOf("nextFiled") < 0){
					this.setOnkeypress(old_onkeypress+" "+control_enter_event);
				}
				//this.setOnkeypress(old_onkeypress+" "+control_enter_event);
			}else{
				if (old_onkeypress.indexOf("nextFiled") < 0){
					this.setOnkeypress(old_onkeypress+"; "+control_enter_event);
				}
				//this.setOnkeypress(old_onkeypress+"; "+control_enter_event);
			}
		}else{
			this.setOnkeypress(" "+control_enter_event);
		}
		
		//Set onKeyPress
		getTextTag().setOnkeypress(getOnkeypress());
		
	}

	protected void initDynamicValues() {		
		super.initDynamicValues();
		
		// format is an EL.
		jspFormat = format;
		format = Expression.evaluate(format, pageContext);
		
		// editoFormat also.
		jspEditFormat = editFormat;
		editFormat = Expression.evaluate(editFormat, pageContext);
	}
	
	protected void reset() {
		// format is an EL
		format = jspFormat;
		jspFormat = null;
		
		// editoFormat also.
		editFormat = jspEditFormat;
		jspEditFormat = null;
		
		super.reset();
	}

	protected BaseHandlerTag createStrutsTag() {
		return new org.apache.struts.taglib.html.TextTag();
	}
	
	private org.apache.struts.taglib.html.BaseFieldTag getTextTag() {
		return (org.apache.struts.taglib.html.BaseFieldTag)getStrutsTag();
	}
}
