package fr.improve.struts.taglib.layout.util;

import fr.improve.struts.taglib.layout.menu.MenuAction;

/**
 * interface that should implement a menu or menuItem tag so that menuActions can be added
 * @author: JeanNoël Ribette
 */
public interface MenuActionInterface {
	public void addAction(MenuAction action);
}
