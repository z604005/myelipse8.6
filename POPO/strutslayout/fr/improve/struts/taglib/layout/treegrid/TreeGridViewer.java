package fr.improve.struts.taglib.layout.treegrid;

import fr.improve.struts.taglib.layout.grid.BodyCell;
import fr.improve.struts.taglib.layout.grid.HeaderCell;

/**
 * TreeGrid viewer.<br>
 * A widget viewer is used on a widget just before his rendering.<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public interface TreeGridViewer {
	/**
	 * Customise a TreeGrid before the rendering 
	 * @param treeGrid
	 */
	void view(TreeGrid treeGrid);
	/**
	 * Customise a TreeGrid node before the rendering 
	 * @param treeGrid
	 */
	void viewNode(Node node);
	/**
	 * Customise a TreeGrid body cell before the rendering 
	 * @param treeGrid
	 */
	void viewNodeCell(BodyCell cell);
	/**
	 * Customise a TreeGrid header cell before the rendering 
	 * @param treeGrid
	 */
	void viewHeaderCell(HeaderCell cell);

//	TreeGridModel modelize(TreeGrid treeGrid);
//	void modelizeHeaderCell(HeaderCell cell, TreeGridModel model);
}
