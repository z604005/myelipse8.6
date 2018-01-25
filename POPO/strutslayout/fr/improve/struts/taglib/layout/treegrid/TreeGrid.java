package fr.improve.struts.taglib.layout.treegrid;

import fr.improve.struts.taglib.layout.grid.Line;
import fr.improve.struts.taglib.layout.treegrid.model.TreeGridModel;
import fr.improve.struts.taglib.layout.util.Widget;

/**
 * TreeGrid widget<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class TreeGrid extends Widget {

	private TreeGridModel model;
	private TreeGridViewer viewer;
	
	private Line headerLine;
	private Node rootNode;
	
	public TreeGrid() {
		this.viewer = new DefaultTreeGridViewer();
		this.headerLine = new Line();
		addWidget(this.headerLine);
	}

	public Node getRootNode() {
		return rootNode;
	}
	public void setRootNode(Node rootNode) {
		this.rootNode = rootNode;
		addWidget(rootNode);
	}
	public TreeGridModel getModel() {
		return model;
	}
	public void setModel(TreeGridModel model) {
		this.model = model;
	}
	public TreeGridViewer getViewer() {
		return viewer;
	}
	public void setViewer(TreeGridViewer viewer) {
		this.viewer = viewer;
	}
	public Line getHeaderLine() {
		return headerLine;
	}	

	void view() {
		if (viewer != null) {
			if (model != null) {
				 viewer.view(this);
//			} else if (headerLine.getCells().isEmpty() || (rootNode == null)) {
//				model = viewer.modelize(this);
			}
		}
	}

}
