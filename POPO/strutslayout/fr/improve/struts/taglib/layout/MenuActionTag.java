package fr.improve.struts.taglib.layout;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import fr.improve.struts.taglib.layout.menu.MenuAction;
import fr.improve.struts.taglib.layout.util.MenuActionInterface;

/**
 * This tag allows to add an action to a menu item.
 * This tags must be nested into a MenuItemTag2 tag.
 * 
 * @author jnribette
 */
public class MenuActionTag extends TagSupport {
	private String target;
	private String key;
	private String link;
	
	public int doEndTag() throws JspException {
		try {
			MenuAction action = new MenuAction();
			action.setHref(link);
			action.setTarget(target);
			action.setTitle(key);
			
			MenuActionInterface menuTag = (MenuActionInterface) findAncestorWithClass(this, MenuActionInterface.class);
			menuTag.addAction(action);
		} catch (ClassCastException e) {
			throw new JspException("menuAction tag not in a menuItem tag");
		} catch (NullPointerException e) {
			throw new JspException("menuAction tag not in a menuItem tag");
		}
		return EVAL_PAGE;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setLink(String link) {
		this.link = link;
	}
	
	
}
