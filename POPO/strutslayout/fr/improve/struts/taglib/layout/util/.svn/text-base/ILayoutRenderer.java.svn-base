package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTag;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;

/**
 * Default renderer interface for simple components.
 * 
 * @author jnribette
 */
public interface ILayoutRenderer {
	public void doStartLayout(LayoutTag layoutTag) throws JspException;
	public void doEndLayout(LayoutTag layoutTag) throws JspException;
	
	public Object doStartChildLayout(LayoutTag layoutTag, StartLayoutEvent startEvent) throws JspException;
	public Object doEndChildLayout(LayoutTag layoutTag, EndLayoutEvent endEvent) throws JspException;
}
