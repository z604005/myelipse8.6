package fr.improve.struts.taglib.layout.renderer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.crumb.Crumb;
import fr.improve.struts.taglib.layout.crumb.CrumbsTag;
import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.ICrumbRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.PanelInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;

public class BasicCrumbRenderer implements ICrumbRenderer {
	/**
	 * pageContext renderer key.
	 */
	private static final String PANEL_KEY = "fr.improve.struts.taglib.layout.renderer.BasicCrumbRenderer.PANEL";

	/**
	 * Start crumbs renderering.
	 */
	public void doStartCrumbs(PageContext pageContext, CrumbsTag crumbs) throws JspException {		
		String align = crumbs.getAlign();
		String width = crumbs.getWidth();
		String styleClass = crumbs.getStyleClass();
		String key = crumbs.getKey();
		String bundle = crumbs.getBundle(); 
		StringBuffer buffer = new StringBuffer();
		
		Skin skin = LayoutUtils.getSkin(pageContext.getSession());
		Class panelClass = skin.getPanelClass(null);
		PanelInterface panel = null;
		try {
			panel = (PanelInterface) panelClass.newInstance();
		} catch (Exception e) {
			TagUtils.saveException(pageContext, e);
			throw new JspException("Could not create panel renderer instance");
		}
		
		pageContext.setAttribute(PANEL_KEY, panel);
		
		panel.init(pageContext, styleClass, crumbs);
		panel.doStartPanel(buffer, align, width);
		panel.doPrintTitle(buffer, LayoutUtils.getLabel(pageContext, bundle, key, null, false));
		panel.doBeforeBody(buffer, align);		
		
		// Old call to CrumbsTag.doStartLayoutTag
		buffer.append("<tr><td>");
		TagUtils.write(pageContext, buffer.toString());
	}	
	
	/**
	 * End crumbs rendering.
	 */
	public void doEndCrumbs(PageContext in_pageContext, CrumbsTag crumbs) throws JspException {
		PanelInterface panel = (PanelInterface) in_pageContext.getAttribute(PANEL_KEY);

		// Old CrumbsTag.doEndLayoutTag code
		TagUtils.write(in_pageContext, "</td></tr>");
		
		StringBuffer buffer = new StringBuffer();
		panel.doAfterBody(buffer);
		panel.doEndPanel(buffer);
		TagUtils.write(in_pageContext, buffer.toString());
		
		in_pageContext.removeAttribute(PANEL_KEY);
	}

	/**
	 * Render a crumb.
	 */
	public void doRenderCrumb(PageContext in_pageContext, CrumbsTag in_crumbsTag, Crumb in_crumb) throws JspException {
		if (in_crumbsTag.needSeparator()) {
			TagUtils.write(in_pageContext, "&nbsp;");
			TagUtils.write(in_pageContext, in_crumbsTag.getSeparator());
			TagUtils.write(in_pageContext, "&nbsp;");
		}
		
		String lc_styleClass = in_crumbsTag.getStyleClass();
		if (lc_styleClass!=null) lc_styleClass += in_crumbsTag.getLevel();

		TagUtils.write(in_pageContext, "<a");
		String lc_link = in_crumb.getLink();
		if (lc_link!=null) {
			TagUtils.write(in_pageContext," href=\"");
			if (!lc_link.startsWith("javascript:")
					&& !lc_link.startsWith("http:") 
					&& !lc_link.startsWith("https:") 
					&& !lc_link.startsWith("mailto:")
					&& !lc_link.startsWith("ftp:")) {
				TagUtils.write(in_pageContext, ((HttpServletRequest)in_pageContext.getRequest()).getContextPath());
			}
			TagUtils.write(in_pageContext, lc_link);
			TagUtils.write(in_pageContext, "\"");
			if (in_crumb.getTarget()!=null) {
				TagUtils.write(in_pageContext, " target=\"");
				TagUtils.write(in_pageContext, in_crumb.getTarget());
				TagUtils.write(in_pageContext, "\"");
			}
		}
		if (lc_styleClass!=null) {
			TagUtils.write(in_pageContext, " class=\"");
			TagUtils.write(in_pageContext, lc_styleClass);
			TagUtils.write(in_pageContext, "\"");
		}
		TagUtils.write(in_pageContext, ">");
		
		String lc_bundle = in_crumb.getBundle();
		if (lc_bundle==null) {
			lc_bundle = in_crumbsTag.getBundle();
		}
		TagUtils.write(in_pageContext,LayoutUtils.getLabel(in_pageContext, lc_bundle, in_crumb.getKey(), null, false));
		TagUtils.write(in_pageContext,"</a>");
	}

	

}
