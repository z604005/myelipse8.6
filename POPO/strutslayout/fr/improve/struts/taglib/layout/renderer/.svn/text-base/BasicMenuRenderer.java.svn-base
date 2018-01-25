package fr.improve.struts.taglib.layout.renderer;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import fr.improve.struts.taglib.layout.MenuTag2;
import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.util.IMenuRenderer;
import fr.improve.struts.taglib.layout.util.PanelInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * This is the default menu renderer.
 * Is is based on the historic code of the MenuTag2 class.
 * 
 * @author jnribette
 */
public class BasicMenuRenderer implements IMenuRenderer {
	/**
	 * Start the menu.
	 */
	public void doStartMenu(MenuTag2 menuTag) throws JspException {		
		StringBuffer buffer = new StringBuffer();
		
		// Start a panel.
		String align = menuTag.getAlign();
		String width = menuTag.getWidth();
		String styleClass = menuTag.getStyleClass();
		menuTag.init();
		PanelInterface panelInterface = menuTag.getPanelInterface();		
		panelInterface.init(menuTag.getPageContext(), styleClass, menuTag);
		menuTag.getPanelInterface().doStartPanel(buffer, align, width);
		menuTag.getPanelInterface().doBeforeBody(buffer, align);
		
		// Get ready to print the menu.
		buffer.append("<tr><td><table border=0 cellpadding=10><tr><td><table border=0>");
		
		// Copy the buffer to the output stream.
		TagUtils.write(menuTag.getPageContext(), buffer.toString());
	}
	
	/**
	 * End the menu.
	 */
	public void doEndMenu(MenuTag2 menuTag) throws JspException {
		StringBuffer buffer = new StringBuffer();
		
		// End printing the menu.
		buffer.append("</table></td></tr></table></td></tr>");
		
		menuTag.getPanelInterface().doAfterBody(buffer);
		menuTag.getPanelInterface().doEndPanel(buffer);
	}
	
	public void doStartMenuItem(MenuTag2 menuTag, int level, MenuComponent item) throws JspException {
		String key = item.getTitle();
		String href = menuTag.computeHref(item);
		
		TagUtils.write(menuTag.getPageContext(), "<tr valign=\"top\"><td class=\"");
		TagUtils.write(menuTag.getPageContext(), menuTag.getStyleClass());		
		if (href!=null) {
			TagUtils.write(menuTag.getPageContext(),"\" align=\"left");
		} else {
			TagUtils.write(menuTag.getPageContext(),"\" onClick=\"changeMenu('");
			TagUtils.write(menuTag.getPageContext(),key);
			TagUtils.write(menuTag.getPageContext(),"')\" style=\"cursor:hand");
		}
		TagUtils.write(menuTag.getPageContext(),"\">");
	}
	
	public void doPrintMenuContent(MenuTag2 menuTag, int level, MenuComponent item) throws JspException {
		PageContext pageContext = menuTag.getPageContext();
		String image = item.getImage();
		
		if (image!=null) {
			TagUtils.write(pageContext,"<img src=");
			TagUtils.write(pageContext,image);
			TagUtils.write(pageContext,">&nbsp;");
		}
		
		TagUtils.write(pageContext, menuTag.computeLabel(item));
	}

	public void doPrintSpaces(MenuTag2 menuTag, int level) throws JspException {
		switch (level) {
			case 2: TagUtils.write(menuTag.getPageContext(),"&nbsp;&nbsp;"); break;
			case 3: TagUtils.write(menuTag.getPageContext(),"&nbsp;&nbsp;&nbsp;>&nbsp;"); break;
		}
	}

	public void doStartLink(MenuTag2 menuTag, MenuComponent component) throws JspException {
		String href = menuTag.computeHref(component);
		String target = component.getTarget();
		String style = component.getStyle();
		
		TagUtils.write(menuTag.getPageContext(),"<a href=\"");
		TagUtils.write(menuTag.getPageContext(),href);
		if (target!=null) {
			TagUtils.write(menuTag.getPageContext(),"\" target=\"");
			TagUtils.write(menuTag.getPageContext(),target);
		}
		if (style!=null) {
			TagUtils.write(menuTag.getPageContext(),"\" style=\"");
			TagUtils.write(menuTag.getPageContext(),style);
		}
		TagUtils.write(menuTag.getPageContext(),"\">");
	}
	
	public void doEndLink(MenuTag2 menuTag) throws JspException {
		TagUtils.write(menuTag.getPageContext(),"</a>");
	}
	
	public void doEndMenuItem(MenuTag2 menuTag, int level, MenuComponent component) throws JspException {
		TagUtils.write(menuTag.getPageContext(),"</td></tr>");
	}

	public boolean doStartSubMenu(MenuTag2 menuTag, int in_level, String in_key, MenuComponent[] in_subMenus) throws JspException {
		TagUtils.write(menuTag.getPageContext(),"<tr valign=top><td><div id=\"");
		TagUtils.write(menuTag.getPageContext(),in_key);
		TagUtils.write(menuTag.getPageContext(),"b\" style=\"display:none\"></div><div id=\"");
		TagUtils.write(menuTag.getPageContext(),in_key);
		TagUtils.write(menuTag.getPageContext(),"\"><table border=0 cellspacing=0 cellpadding=0>");
		TagUtils.write(menuTag.getPageContext(),"<script language=\"JavaScript\">initMenu('" + in_key  +"');</script>\n");
		return true;
	}
	
	public void doEndSubMenu(MenuTag2 menuTag) throws JspException {
		TagUtils.write(menuTag.getPageContext(),"</table></div></td></tr>");			
	}
}
