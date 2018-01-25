package fr.improve.struts.taglib.layout.util;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.collection.CollectionGroupTag;
import fr.improve.struts.taglib.layout.collection.CollectionTag;

public interface ICollectionGroupRenderer {
	public void doRenderGroup(CollectionTag collectionTag, CollectionGroupTag collectionGroupTag, Object value) throws JspException;
}
