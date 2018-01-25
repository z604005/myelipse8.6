package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.collection.CollectionTag;
import fr.improve.struts.taglib.layout.collection.NestedTreeCollectionTag;

/**
 * Treegrid renderer interface for collection.
 * 
 * @author jnribette
 */
public interface ITreeGridRenderer {
	public void doRenderTreeNode(CollectionTag collectionTag, NestedTreeCollectionTag nestedTreeCollectionTag, String item, int level) throws JspException;
}
