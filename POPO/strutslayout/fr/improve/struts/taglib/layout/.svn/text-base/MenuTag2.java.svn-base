package fr.improve.struts.taglib.layout;

import java.util.Vector;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.menu.MenuAction;
import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.util.IMenuRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.MenuInterface;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Display a tree like menu.
 * 
 * Compatible with Scott Sayles menu framework.
 * 
 * In the past, this tag didn't have a renderer and several methods used to generate HTML code in a StringBuffer.
 * The way to customize the tag was to override one of this method.
 * 
 * The recommanded wat to customize the HTML is now to write a renderer, but care should be taken
 * to let the different methods as is to not break backward compatibility. 
 * 
 * @author: Jean-No�l Ribette
 */
public class MenuTag2 extends PanelTag implements MenuInterface {
	/**
	 * Menus to display.
	 */
	protected Vector menus = new Vector();
	
	/**
	 * Current menu renderer.
	 */
	private IMenuRenderer menuRenderer;
	
	/**
	 * Current item.
	 */
	private MenuComponent currentMenu;
	
	/**
	 * Initialize the tag.
	 */
	protected void initDynamicValues() {
		super.initDynamicValues();
		menuRenderer = getSkin().getMenuRenderer(null);
	}
		
	/**
	 * Reset the tag.
	 */
	protected void reset() {
		menuRenderer = null;
		currentMenu = null;
		super.reset();
	}

	/**
	 * Start the tag, do nothing (menu items are not defined yet).
	 */
	public int doStartLayoutTag() throws JspException {	
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * Add a menu item (method specified in MenuInterface).
	 */
	public void addItem(MenuComponent item) {
		menus.add(item);
	}

	/**
	 * End the tag, generate the HTML code for the menu items added.
	 */
	public int doEndLayoutTag() throws JspException {
		menuRenderer.doStartMenu(this);
		StringBuffer buffer = new StringBuffer();
		doPrintMenu(buffer,(MenuComponent[])menus.toArray(new MenuComponent[menus.size()]),1);
		TagUtils.write(pageContext, buffer.toString());
		menuRenderer.doEndMenu(this);			
		menus.removeAllElements();
		return EVAL_PAGE;
	}
	
	/**
	 * Iterates over the MenuComponent[].	 
	 */
	protected void doPrintMenu(StringBuffer buffer, MenuComponent[] menus, int level) throws JspException {
		for (int i=0;i<menus.length;i++) {
			// Print the menu item.
			MenuComponent item = (MenuComponent) menus[i];		
			currentMenu = item;
			doPrintMenuItem(buffer, level, item);
			flushBuffer(buffer);
			
			// Print the sub items.
			MenuComponent[] subMenus = item.getMenuComponents();
			String key = item.getTitle();	
			doPrintSubMenu(buffer, level, key, subMenus);
			flushBuffer(buffer);
			
			buffer.append("\n");
			flushBuffer(buffer);
		}
	
	}

	/**
	 * Generate the HTML code for one menu item. 
	 */
	protected void doPrintMenuItem(StringBuffer buffer, int level, MenuComponent item) throws JspException { 
		menuRenderer.doStartMenuItem(this, level, item);
		
		doPrintSpaces(buffer, level);
		flushBuffer(buffer);
		
		String link = item.getLocation();
		String forward = item.getForward();
		String page = item.getPage();
		String action = item.getAction();
		String target = item.getTarget();
		String onClick = item.getOnClick();
		String reqCode = item.getReqCode();
		boolean lc_hasLink = link!=null || forward!=null || page!=null || action!=null || onClick!=null || reqCode!=null;
		
		if (lc_hasLink) {
			doStartLink(buffer, link, forward, page, action, target, style);
			flushBuffer(buffer);
		}
		
		menuRenderer.doPrintMenuContent(this, level, item);
		if (lc_hasLink) {
			doEndLink(buffer);
			flushBuffer(buffer);
		}
		menuRenderer.doEndMenuItem(this, level, item);
	}			

	private void doPrintSpaces(StringBuffer buffer, int level) throws JspException {
		menuRenderer.doPrintSpaces(this, level);
	}
	
	/**
	 * @deprecated.
	 */
	protected void doStartLink(StringBuffer buffer, String link, String forward, String page, String target, String style) throws JspException {
		doStartLink(buffer, link, forward, page, null, target, style);
	}

	protected void doStartLink(StringBuffer buffer, String link, String forward, String page, String action, String target, String style) throws JspException {
		menuRenderer.doStartLink(this, currentMenu);
	}
	
	protected void doEndLink(StringBuffer buffer) throws JspException {
		menuRenderer.doEndLink(this);
	}

	/**
	 * Print the submenu for a menu item.	
	 */
	protected void doPrintSubMenu(StringBuffer in_buffer, int in_level, String in_key, MenuComponent[] in_subMenus) throws JspException {
		if (in_subMenus!=null && in_subMenus.length!=0) {
			if (menuRenderer.doStartSubMenu(this, in_level, in_key, in_subMenus)) {
				doPrintMenu(in_buffer, in_subMenus, in_level+1);
			}
			menuRenderer.doEndSubMenu(this);
			
		}
	}
	
	/**
	 * Helper method for the renderer.
	 * Return the link associated to the item.
	 */
	public String computeHref(MenuComponent item) throws JspException {
		String lc_link = item.getLocation();
		String lc_forward = item.getForward();
		String lc_page = item.getPage();
		String lc_action = item.getAction();
		String lc_target = item.getTarget();
		
		boolean lc_hasLink = lc_link!=null || lc_forward!=null || lc_page!=null || lc_action!=null;
		if (lc_hasLink) {
			String lc_href = LayoutUtils.computeURL(pageContext, lc_forward, lc_link, lc_page, lc_action, null,null, null, false, lc_target);
			return lc_href;
		} else {
			return null;
		}
	}
	
	protected final void flushBuffer(StringBuffer buffer) throws JspException {
		TagUtils.write(pageContext, buffer.toString());
		buffer.setLength(0);
	}
	
	/**
	 * Helper method for the renderer
	 */
	public String computeLabel(MenuComponent item) throws JspException {
		String lc_key = item.getTitle();
		String lc_label = LayoutUtils.getLabel(pageContext,bundle, lc_key, null, false);
		return lc_label;
	}
	
	/**
	 * Helper method for the renderer.
	 */
	public String computeHref(MenuAction action) throws JspException {
		String lc_href = action.getHref();
		String lc_target = action.getTarget();
		return LayoutUtils.computeURL(pageContext, null, lc_href, null, null,null, null, null, false, lc_target);
	}
	
	/**
	 * Helper method for the renderer.
	 */
	public String computeLabel(MenuAction action) throws JspException {
		String lc_key = action.getTitle();
		String lc_label = LayoutUtils.getLabel(pageContext,bundle, lc_key, null, false);
		return lc_label;
	}
}
