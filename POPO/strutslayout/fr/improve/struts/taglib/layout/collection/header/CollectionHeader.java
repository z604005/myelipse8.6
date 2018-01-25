package fr.improve.struts.taglib.layout.collection.header;

/**
 * 
 * @author Gilles Rossi
 *
 */
public interface CollectionHeader {
	String getTitle();
	String getArg0();
	String getArg1();
	String getWidth();
	
	String getSortProperty();
	String getSortUrl();
	void setSortUrl(String url);
	boolean isSorted();
	boolean isAscending();
	
	String getFilter();
}

