package fr.improve.struts.taglib.layout.pager;

import java.util.Map;

/**
 * Interface implemented by java.util.List renderer that can paginate 
 * their display.
 * 
 * @author jnribette
 *
 */
public interface PagerContainer {
	public void setLength(int in_length);
	public void setOffset(int in_offset);
	public int getSize();
	public int getIndex();
	
	/**
	 * Compute the url to invoke to display another page.
     * The parameters to add to the url must be specified in the first method parameter
	 */
	public String computePageURL(Map params);
}

