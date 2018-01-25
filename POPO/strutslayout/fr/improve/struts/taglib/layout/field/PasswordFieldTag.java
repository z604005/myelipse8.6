package fr.improve.struts.taglib.layout.field;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.html.BaseHandlerTag;
import org.apache.struts.taglib.html.PasswordTag;
/**
 * A simple password tag.
 * @author: Jean-Noël Ribette
 * @author: Gilles Rossi
 */
public class PasswordFieldTag extends AbstractFieldTag {

	protected boolean redisplay = false;

	public boolean getRedisplay() {
		return redisplay;
	}
/**
 * Insert the method's description here.
 * Creation date: (01/12/2001 02:12:55)
 * @return boolean
 */
public boolean isRedisplay() {
	return redisplay;
}
	public void release() {
		super.release();
		redisplay = false;
	}
/**
 * Insert the method's description here.
 * Creation date: (01/12/2001 02:12:55)
 * @param newRedisplay boolean
 */
public void setRedisplay(boolean newRedisplay) {
	redisplay = newRedisplay;
}
/**
 * Return the value(s) that will be displayed. The password should of course not been displayed, except specificaly asked.
 */
protected java.lang.Object getFieldValue() throws JspException {
	Object lc_value = super.getFieldValue();
	switch (getFieldDisplayMode()) {
		case MODE_EDIT:
			if (!redisplay) {
				lc_value = "";	
			}
			break;
		default:
			lc_value = "";
	}
	return lc_value;
}
	
	protected void copyProperties(BaseHandlerTag in_dest) throws JspException {		
		super.copyProperties(in_dest);		
		getPasswordFieldTag().setCols(getCols());
		getPasswordFieldTag().setMaxlength(getMaxlength());
		getPasswordFieldTag().setProperty(getProperty());
		getPasswordFieldTag().setRows(getRows());
		getPasswordFieldTag().setValue(getValue());
		
		getPasswordFieldTag().setAccept(getAccept());
		getPasswordFieldTag().setName(getName());
		getPasswordFieldTag().setRedisplay(getRedisplay());		
	}
	protected BaseHandlerTag createStrutsTag() {
		return new org.apache.struts.taglib.html.PasswordTag();
	}
	
	private org.apache.struts.taglib.html.PasswordTag getPasswordFieldTag() {
		return (org.apache.struts.taglib.html.PasswordTag)getStrutsTag();
	}
	
	protected boolean doBeforeValue() throws JspException {
		return true;
	}

}
