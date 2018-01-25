package fr.improve.struts.taglib.layout.grid;

import java.util.Collection;

import fr.improve.struts.taglib.layout.treegrid.TreeGrid;
import fr.improve.struts.taglib.layout.util.Widget;

/**
 * Line of grid<br>
 * A line contains cells<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class Line extends Widget {

	private Object model;

	public Line() {
	}
	
	public void addCell(Cell cell) {
		cell.setX(getWidgets().size());
		cell.setY(getIndex());
		addWidget(cell);
	}
	public TreeGrid getTreeGrid() {
		return (TreeGrid)getParent();
	}
	public Collection getCells() {
		return getWidgets();
	}
	public Object getModel() {
		return model;
	}
	public void setModel(Object model) {
		this.model = model;
	}
}
