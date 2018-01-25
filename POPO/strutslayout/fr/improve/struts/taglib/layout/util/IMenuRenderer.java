package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.MenuTag2;
import fr.improve.struts.taglib.layout.menu.MenuComponent;

/**
 * Menu renderer.
 * 
 * @author jnribette
 */
public interface IMenuRenderer {
	/**
	 * Start to render the menu.
	 */
	public void doStartMenu(MenuTag2 menuTag) throws JspException;
	
	/**
	 * End to render the menu.
	 */
	public void doEndMenu(MenuTag2 menuTag) throws JspException;
	
	/**
	 * Start rendering of a menu item.
	 * @param menuTag			The JSP tag defining the menu to render.
	 * @param level				The depth of the menu item to render.
	 * @param item				The menu item to render.
	 * @throws JspException
	 */
	public void doStartMenuItem(MenuTag2 menuTag, int level, MenuComponent item) throws JspException;
	public void doPrintSpaces(MenuTag2 menuTag, int level) throws JspException;
	public void doStartLink(MenuTag2 menuTag, MenuComponent component) throws JspException;
	public void doPrintMenuContent(MenuTag2 menuTag, int level, MenuComponent item) throws JspException;
	public void doEndLink(MenuTag2 menuTag) throws JspException;
	public void doEndMenuItem(MenuTag2 menuTag, int level, MenuComponent component) throws JspException;
	
	/**
	 * Start rendering of the submenu for the current item.
	 * @param menuTag			The JSP tag defining the menu to render.
	 * @param in_level			The depth of the menu item to render.
	 * @param in_key		
	 * @param in_subMenus		The submenu to render
	 * @return					False if the renderer renders the submenu by itself. 
	 * 							True if the framework should call the *MenuItem methods for each submenu item. 
	 * @throws JspException
	 */
	public boolean doStartSubMenu(MenuTag2 menuTag, int in_level, String in_key, MenuComponent[] in_subMenus) throws JspException;
	public void doEndSubMenu(MenuTag2 menuTag) throws JspException;
}
