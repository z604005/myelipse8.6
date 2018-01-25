package fr.improve.struts.taglib.layout.collection;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.el.Expression;
import fr.improve.struts.taglib.layout.formatter.AbstractFormatter;
import fr.improve.struts.taglib.layout.util.CollectionInterface;
import fr.improve.struts.taglib.layout.util.ICollectionGroupRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;
import fr.improve.struts.taglib.layout.util.ParentFinder;

/**
 * This tag allows to add visual grouping of lines 
 * which share a common value for a specific property.
 * 
 * @author Jean-Noel RIBETTE
 */
public class CollectionGroupTag extends LayoutTagSupport {
	/**
	 * Name of the group property.
	 * (required and EL tag attribute) 
	 */
	private String property;
	
	/**
	 * Name of the format.
	 * (tag attribute)
	 */
	private String type;
	
	/**
	 * Action 
	 * (EL tag attribute)
	 */
	private String action;
	
	/**
	 * Action label.
	 * (tag attribute)
	 */
	private String hrefKey;
	
	/**
	 * Open flag.
	 */
	private String open;
	
	/**
	 * Save state of the property.
	 */
	private String jspProperty;
	
	/**
	 * Last group property value.
	 */
	private Object value;
	
	/**
	 * Compute dynamic values.
	 */
	protected void initDynamicValues() {
		jspProperty = property;
		property = Expression.evaluate(property, pageContext);
	}
	
	/**
	 * Start tag.
	 */
	public int doStartLayoutTag() throws JspException {
		CollectionTag collectionTag = (CollectionTag) ParentFinder.findLayoutTag(this, CollectionTag.class);
					
		if (!collectionTag.isFirst()) {
			Object bean = collectionTag.getBean();
			Object currentValue = LayoutUtils.getProperty(bean, property);
			if (currentValue!=null && value==null || currentValue==null && value!=null || currentValue!=null && !currentValue.equals(value)) {
				CollectionInterface collectionRenderer = (CollectionInterface) collectionTag.panel;
				if (!(collectionRenderer instanceof ICollectionGroupRenderer)) {
					throw new JspException("The renderer " + collectionRenderer.getClass().getName() + " does not implement ICollectionGroupRenderer");
				}
				ICollectionGroupRenderer renderer = (ICollectionGroupRenderer) collectionRenderer;
				AbstractFormatter formatter = LayoutUtils.getSkin(pageContext.getSession()).getFormatter();
				Object formattedValue = type==null ? currentValue : formatter.format(currentValue, type, pageContext);
				renderer.doRenderGroup(collectionTag, this, formattedValue);
			}
			value = currentValue;
		}
		return EVAL_BODY_INCLUDE;
	}
	
	/**
	 * Compute the action URL.
	 * This method is supposed to be used by the renderer.
	 */
	public String getActionURL() {
		String actionValue = Expression.evaluate(action, pageContext);
		return LayoutUtils.computeURL(pageContext, null, null, null, actionValue, null, null, null, false, null);
	}
	
	/**
	 * Compute the action label.
	 * This method is supposed to be used by the renderer.
	 */
	public String getActionLabel() {
		String label = LayoutUtils.getLabel(pageContext, hrefKey, null);
		return label;
	}
	
	/**
	 * Compute the open state.
	 * @return null if the group is not openable/closeable, true if open, flase if closed.
	 */
	public Boolean getOpenState() {
		String value = Expression.evaluate(open,pageContext);
		if (value==null) {
			return null;
		} else {
			return new Boolean(value);
		}
	}
	
	/**
	 * End tag.
	 */
	public int doEndLayoutTag() throws JspException {
		return EVAL_PAGE;
	}
	
	/**
	 * Reset dynamic values.
	 */
	protected void reset() {
		action = null;
		open = null;
		property = jspProperty;
		jspProperty = null;
	}
	
	public void release() {		
		property = null;
		type = null;
		hrefKey = null;
	}
	
	public void setProperty(String property) {
		this.property = property;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setAction(String action) {
		this.action = action;
	}

	public void setHrefKey(String hrefKey) {
		this.hrefKey = hrefKey;
	}
	
	public void setOpen(String open) {
		this.open = open;
	}
}
