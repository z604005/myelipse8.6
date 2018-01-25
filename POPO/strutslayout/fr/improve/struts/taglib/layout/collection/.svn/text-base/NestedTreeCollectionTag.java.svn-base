package fr.improve.struts.taglib.layout.collection;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.util.ITreeGridRenderer;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * This tag is a specialized version of the NestedCollectionTag, allowing to display treegrids.
 * 
 * @author Jean-Noel RIBETTE
 */
public class NestedTreeCollectionTag extends NestedCollectionTag {
	/**
	 * Property to display as node value.
	 */
	protected String labelProperty;
	
	public int doStartLayoutTag() throws JspException {
		super.doStartLayoutTag();
		
		if (getName()!=null) {
			if (getCollectionTag().getSpans().containsKey(getName())) {
				Object value = LayoutUtils.getBeanFromPageContext(pageContext, getName(), labelProperty);
				ITreeGridRenderer renderer = (ITreeGridRenderer) getCollectionTag().panel;
				renderer.doRenderTreeNode(getCollectionTag(), this, value==null ? null : value.toString(), getDepth());				
			}
		}
		
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndLayoutTag() {
		super.doEndLayoutTag();
		return EVAL_PAGE;
	}
	
	public void release() {
		super.release();
		labelProperty = null;
	}
	
	public void setLabelProperty(String labelProperty) {
		this.labelProperty = labelProperty;
	}
	
}
