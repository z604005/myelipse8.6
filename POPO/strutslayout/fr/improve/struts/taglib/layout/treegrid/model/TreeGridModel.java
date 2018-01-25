package fr.improve.struts.taglib.layout.treegrid.model;

import java.util.Collection;

import fr.improve.struts.taglib.layout.grid.model.ColumnsModel;

/**
 * TreeGrid model<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public interface TreeGridModel extends ColumnsModel {
	/**
	 * Returns the tree root
	 * @return the tree root
	 */
	Object getRoot();
	/**
	 * Returns the children of a tree path
	 * @param path the tree path
	 * @return the children
	 */
	Collection getChildren(Path path);
	
	/**
	 * Returns the cells of a tree path
	 * @param path a tree path
	 * @return the cells
	 */
	Collection getCells(Path path);
}
