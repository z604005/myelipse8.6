package fr.improve.struts.taglib.layout.collection;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;

import fr.improve.struts.taglib.layout.LayoutTagSupport;
import fr.improve.struts.taglib.layout.util.TagUtils;

/**
 * Tag allowing nested collection display
 * 
 * @author Jean-Noel RIBETTE
 */
public class NestedCollectionTag extends LayoutTagSupport {
	/**
	 * Name of the bean in the pageContext holding the collection to display.
	 * This attribute is a tag attribute set in the JSP. 
	 */
	protected String name;
	
	protected String property;
	protected String id;
	protected String indexId;
	
	protected CollectionTag collectionTag;
	protected int depth = 0;
	
	public int doStartLayoutTag() throws JspException {				
		if (collectionTag.isFirst()) try {
			CollectionsIterator lc_iterator = new CollectionsIterator(null, property, id, indexId);
			collectionTag.addIterator(lc_iterator);
		} catch (Exception e) {
			TagUtils.saveException(pageContext, e);
			throw new JspException(e.getMessage());
		}		
		
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndLayoutTag() {		
		return EVAL_PAGE;
	}
	
	protected void initDynamicValues() {
		if (collectionTag==null) {
			Tag parent = getParent();
			while (!(parent instanceof CollectionTag) && parent!=null) {
				if (parent instanceof NestedCollectionTag) {
					depth++;
				}			
				parent = parent.getParent();				
			}
			collectionTag = (CollectionTag) parent;
		}
	}
	
	protected void reset() {
		collectionTag = null;
		depth = 0;
	}
	
	public void release() {
		super.release();
		name = null;
		property = null;
		id = null;	
		indexId = null;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public CollectionTag getCollectionTag() {
		return collectionTag;
	}
	
	
	/**
	 * Returns the id.
	 * @return String
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns the property.
	 * @return String
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Sets the id.
	 * @param id The id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the property.
	 * @param property The property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * Returns the indexId.
	 * @return String
	 */
	public String getIndexId() {
		return indexId;
	}

	/**
	 * Sets the indexId.
	 * @param indexId The indexId to set
	 */
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

}
