package fr.improve.struts.taglib.layout.treegrid;

/**
 * TreeGrid renderer.<br>
 * A widget renderer is used on a widget to do his HTML rendering.<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public interface ITreeGridRenderer {
	/**
	 * Renders a TreeGrid in a HTML buffer
	 * 
	 * @param buffer an HTML output buffer
	 * @param treeGrid a TreeGrid
	 */
	void render(StringBuffer buffer, TreeGrid treeGrid);
}
