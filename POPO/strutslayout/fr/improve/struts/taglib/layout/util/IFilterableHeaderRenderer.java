package fr.improve.struts.taglib.layout.util;

import fr.improve.struts.taglib.layout.collection.header.CollectionHeader;

/**
 * 
 * 25 janv. 08
 *
 * @author Gilles Rossi
 *
 */
public interface IFilterableHeaderRenderer {
	void renderInnerFilterableHeader(StringBuffer buffer, CollectionHeader header);
}
