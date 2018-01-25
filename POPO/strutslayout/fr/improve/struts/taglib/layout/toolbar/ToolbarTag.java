package fr.improve.struts.taglib.layout.toolbar;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.event.EndLayoutEvent;
import fr.improve.struts.taglib.layout.event.LayoutEventListener;
import fr.improve.struts.taglib.layout.event.StartLayoutEvent;
import fr.improve.struts.taglib.layout.menu.MenuAction;
import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.util.FieldInterface;
import fr.improve.struts.taglib.layout.util.IToolbarRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.MenuInterface;

/**
 * A toolbar is a widget container.
 * It can contain menu items or buttons.
 * 
 * Buttons are renderered by the different buttons tag as usual.
 * The toolbar renderer can insert HTML code before and after each button.
 * 
 * Menu items are rendered by the toolbar renderer.
 * 
 * @author jnribette
 */
public class ToolbarTag extends LayoutTagSupport implements LayoutEventListener, MenuInterface {
	/**
	 * Tag parameter : automatically add separator between the elements. 
	 */
	private boolean separator;
	
	/**
	 * Tag parameter : name of the renderer
	 */
	private String model;
	
	/**
	 * The current toolbar renderer.
	 */
	protected IToolbarRenderer renderer;
	
	/**
	 * Initialize the tag before use.
	 */
	protected void initDynamicValues() {
		super.initDynamicValues();
		renderer = LayoutUtils.getSkin(pageContext.getSession()).getToolbarRenderer(model);
	}
	
	/**
	 * Clean the tag after use.
	 */
	protected void reset() {
		renderer = null;
		super.reset();
	}
	
	/**
	 * Render a menu item.
	 */
	public void addItem(MenuComponent item) {
		try {
			renderer.doRenderItem(this, item);
		} catch (JspException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Start rendering of a button component.
	 */
	public Object processStartLayoutEvent(StartLayoutEvent in_event) throws JspException {
		// Inform the renderer.
		renderer.doStartButton(this);
		return null;
	}
	
	/**
	 * End rendering of a button component.
	 */
	public Object processEndLayoutEvent(EndLayoutEvent in_event) throws JspException {
		// Inform the renderer.
		renderer.doEndButton(this);
		return null;
	}
	
	/**
	 * Start rendering of the toolbar.
	 */
	public int doStartLayoutTag() throws JspException {
		FieldInterface fieldRenderer = LayoutUtils.getSkin(pageContext.getSession()).getFieldInterface();
		new StartLayoutEvent(this, "<td valign=\"top\" colspan=\"" + fieldRenderer.getColumnNumber() + "\">").send();
		renderer.doStartToolbar(this);
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * End rendering of the toolbar.
	 */
	public int doEndLayoutTag() throws JspException {
		renderer.doEndToolbar(this);
		new EndLayoutEvent(this, "</td>").send();
		return EVAL_PAGE;
	}
	
	/**
	 * Helper method : compute the label of an item.
	 * This method can be called by the renderer.
	 */
	public String computeLabel(MenuComponent item) throws JspException {
		return LayoutUtils.getLabel(pageContext, item.getTitle(), null);
	}
	
	/**
	 * Helper method : compute the label of ac action.
	 * This method can be called by the renderer.
	 */
	public String computeLabel(MenuAction action) throws JspException {
		return LayoutUtils.getLabel(pageContext, action.getTitle(), null);
	}

	public boolean isSeparator() {
		return separator;
	}

	public void setSeparator(boolean separator) {
		this.separator = separator;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
