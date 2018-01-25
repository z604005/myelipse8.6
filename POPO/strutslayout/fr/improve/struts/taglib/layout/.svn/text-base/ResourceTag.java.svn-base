/*
 * Created on 19 oct. 2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Generate an absolute path to a struts-layout resource.
 * 
 * @author JN Ribette
 */
public class ResourceTag extends LayoutTagSupport {

	protected static final int IMG = 1;
	protected static final int CFG = 2;
	protected static final int CSS = 3;
	protected static final int SCRIPT = 4;

	protected int type;
	protected String name;
	
	public int doStartLayoutTag() throws JspException {
		// Get skin
		Skin lc_skin = LayoutUtils.getSkin(pageContext.getSession());
		StringBuffer lc_buffer = new StringBuffer();
		String resourcePath = "";
		
		switch (type) {
			case IMG:
				// Get image directory.
				resourcePath = lc_skin.getImageDirectory(pageContext.getRequest());
				break;
			case CFG:
				//	Get config directory.
				resourcePath = lc_skin.getConfigDirectory(pageContext.getRequest());
				break;
			case CSS:
				// Get css directory.
				resourcePath = lc_skin.getCssDirectory(pageContext.getRequest());
				break;
			case SCRIPT:
				// Get css directory.
				resourcePath = lc_skin.getScriptsDirectory(pageContext.getRequest());
				break;
		}
		lc_buffer.append(resourcePath);
		if (!resourcePath.endsWith("/") && !name.startsWith("/")) {
			lc_buffer.append('/');
		}
		lc_buffer.append(name);
		
		TagUtils.write(pageContext, lc_buffer.toString());
		return SKIP_BODY;
	}
	public void release() {
		super.release();
		type = IMG;
		name = null;
	}
	
	public void setType(String in_type) throws JspException {
		if ("img".equals(in_type)) {
			type = IMG;
		} else if ("cfg".equals(in_type)) {
			type = CFG;
		} else if ("css".equals(in_type)) {
			type = CSS;
		} else if ("script".equals(in_type)) {
			type = SCRIPT;
		} else {
			throw new JspException("Type " + in_type + " is not supported.");
		}
	}
	
	public void setName(String in_name) {
		name = in_name;
	}
}
