package fr.improve.struts.taglib.layout.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Ancestor of all widgets.
 * A widget can be the ancestor of others widgets.
 * 
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class Widget {

	private Widget parent;
	private int index;
	private List widgets;
	
	public Widget() {
		this.widgets = new ArrayList();
	}

	public void addWidget(Widget widget) {
		widget.parent = this;
		widget.index = widgets.size();
		widgets.add(widget);
	}
	/**
	 * Return the index of the current widget in his parent
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	public Widget getParent() {
		return parent;
	}
	public List getWidgets() {
		return widgets;
	}
	public int getDepth() {
		if (getParent() == null) {
			return 0;
		} else {
			return getParent().getDepth()+1;
		}
	}
}
