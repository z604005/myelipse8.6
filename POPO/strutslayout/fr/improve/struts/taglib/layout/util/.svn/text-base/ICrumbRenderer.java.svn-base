package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.crumb.Crumb;
import fr.improve.struts.taglib.layout.crumb.CrumbsTag;

/**
 * Render crumbs elements.
 * 
 * @author jribette
 */
public interface ICrumbRenderer {
	/**
	 * Start crumbs rendering.
	 */
	public void doStartCrumbs(PageContext pageContext, CrumbsTag crumbs) throws JspException;
	
	/**
	 * End crumbs rendering.
	 */
	public void doEndCrumbs(PageContext pageContext, CrumbsTag crumbs) throws JspException;
	
	/**
	 * Render one crumb.
	 */
	public void doRenderCrumb(PageContext pageContext, CrumbsTag crumbsTag, Crumb crumb) throws JspException;
}
