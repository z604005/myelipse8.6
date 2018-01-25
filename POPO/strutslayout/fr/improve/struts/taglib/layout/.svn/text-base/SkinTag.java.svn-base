package fr.improve.struts.taglib.layout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.skin.Skin;
import fr.improve.struts.taglib.layout.util.HTMLUtils;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Struts-Layout skin tag (&lt;layout:skin&gt;).<br>
 * This tag generates in the JSP code to : 
 * <ul>
 *  <li>Load the current Skin CSS</li>
 *  <li>Define global Javascript variables (
 *  imgsrc : server root relative skin image directory,
 *  scriptsrc : server root relatrive config directory,
 *  langue : Struts locale,
 *  contextPath : application context path)
 *  </li>
 *  <li>Load Struts-Layout Javascript (the file named javascript.js in the skin config directory); if includeScript is set to true.</li>
 * </ul>
 * 
 * Creation date: (17/06/2001 18:01:46)
 * @author: Jean-No�l Ribette
 */
public class SkinTag extends javax.servlet.jsp.tagext.TagSupport {
	/**
	 * Skin name.
	 * Default is to use the skin set in the user session.
	 */
	private String name;
	
	/**
	 * Should we include Struts-Layout Javascript ?
	 * Default is false for background compatibility. 
	 */
	private boolean include = false;

	/**
	 * End tag callback.
	 */
	public int doEndTag() throws JspException {
		// Set the skin.
		if (name!=null) {
			LayoutUtils.setSkin(pageContext.getSession(), name);
		}
		
		Skin.appendImportedResources(include, pageContext);

		// Go on evaluating the page. 
		return EVAL_PAGE;
	}

	/**
	 * Release the tag.
	 */
	public void release() {
		include = false;	
	}
	
	/**
	 * Setter for setting the includeScript boolean variable.
	 * @param in_include include Struts-Layout Javascript loading code
	 */
	public void setIncludeScript(boolean in_include) {
		include = in_include;	
	}
	
	/**
	 * Setter for the skin name variable.	 
	 * @param name name the name of the skin to use
	 */
	public void setName(String name) {
		this.name = name;
	}
}
