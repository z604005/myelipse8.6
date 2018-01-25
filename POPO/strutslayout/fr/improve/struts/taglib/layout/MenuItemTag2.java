package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.menu.MenuAction;
import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.MenuActionInterface;
import fr.improve.struts.taglib.layout.util.MenuInterface;

/**
 * Menu tag used to specify the menu to display.
 * The menu can be defined in the jsp page by nesting menuItem tag
 * or taken from the repository by giving the menu name.
 * Compatible with Scott Sayles menu framework.
 * 
 * @author: Jean-No�l Ribette
 */
public class MenuItemTag2 extends LayoutTagSupport implements MenuInterface, MenuActionInterface {
	protected String open = null;
	protected String key = null;
	protected String link = null;
	protected String onClick = null;
	protected String page = null;
	protected String action = null;
	protected String forward = null;
	protected String style = null;
	protected String styleClass;
	protected String separator;
	protected String reqCode = null;
	protected String tooltip;
	
	protected String name = null;
	protected String image = null;
	protected String target = null;
	protected MenuComponent menu = null;
	
	protected MenuAction menuAction = null;
	
	public void addItem(MenuComponent item) {
		if (menu==null) menu = new MenuComponent();
		menu.addMenuComponent(item);
	}
	
	public void addAction(MenuAction action) {
		if (menu==null) menu = new MenuComponent();
		menu.addMenuAction(action);
		
	}
	
	public int doEndLayoutTag() throws JspException {
		if (name==null) {
			if (menu==null) menu = new MenuComponent();
			menu.setTitle(key);
			menu.setToolTip(tooltip);
			menu.setLocation(link);
			menu.setForward(forward);
			menu.setPage(page);
			menu.setAction(action);
			menu.setImage(image);
			menu.setStyle(style);
			menu.setStyleClass(styleClass);
			menu.setTarget(target);
			menu.setSeparator(separator!=null ? Boolean.valueOf(separator) : null);
			menu.setOnClick(onClick);
			menu.setOpen(Boolean.valueOf(Expression.evaluate(open, pageContext)).booleanValue());
			menu.setReqCode(reqCode);
		} else
			menu = LayoutUtils.getMenu(pageContext, name);
		if (menu==null) throw new JspException("Menu " + name + " not found in repository");
		try {
			MenuInterface menuTag = (MenuInterface) findAncestorWithClass(this, MenuInterface.class);
			menuTag.addItem(menu);	
		} catch (ClassCastException e) {
			throw new JspException("menuItem tag not in a menu or a menuItem tag");
		} catch (NullPointerException e) {
			throw new JspException("menuItem tag not in a menu or a menuItem tag");
		}
		menu = null;
		return EVAL_PAGE;
	}
	public int doStartLayoutTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}
	public void release() {
		super.release();
		key = null;
		link = null;
		forward = null;
		action = null;
		page = null;
		menu = null;
		name = null;
		image = null;
		style = null;
		styleClass = null;
		target = null;
		open = null;
		separator = null;
		onClick = null;
		reqCode = null;
		tooltip = null;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Gets the target.
	 * @return Returns a String
	 */
	public String getTarget() {
		return target;
	}

	/**
	 * Sets the target.
	 * @param target The target to set
	 */
	public void setTarget(String target) {
		this.target = target;
	}

	/**
	 * Sets the forward.
	 * @param forward The forward to set
	 */
	public void setForward(String forward) {
		this.forward = forward;
	}

	/**
	 * Sets the page.
	 * @param page The page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	public void setStyle(String in_string) {
		style = in_string;
	}
	
	public void setAction(String in_action) {
		action = in_action;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}

	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getReqCode() {
		return reqCode;
	}

	public void setReqCode(String reqCode) {
		this.reqCode = reqCode;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

}
