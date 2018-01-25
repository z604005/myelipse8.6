package fr.improve.struts.taglib.layout.treegrid;

import java.util.Collection;
import java.util.Iterator;

import fr.improve.struts.taglib.layout.grid.BodyCell;
import fr.improve.struts.taglib.layout.grid.HeaderCell;
import fr.improve.struts.taglib.layout.grid.Line;
import fr.improve.struts.taglib.layout.treegrid.model.Path;
import fr.improve.struts.taglib.layout.treegrid.model.TreeGridModel;

/**
 * Simple implementation of TreeGridViewer<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class DefaultTreeGridViewer implements TreeGridViewer {

	public void view(TreeGrid treeGrid) {
//		TreeGrid treeGrid = new TreeGrid();
		TreeGridModel model = treeGrid.getModel();
		if (model != null) {
			// headers
			Line headerLine = treeGrid.getHeaderLine();
			if (headerLine.getWidgets().isEmpty()) {
				for (int i = 0; i < model.getColumnCount(); i++) {
					String title = model.getColumnTitle(i);
					HeaderCell cell = new HeaderCell(title);
					cell.setModel(title);
					headerLine.addWidget(cell);
					viewHeaderCell(cell);
				}
			}
			
			// nodes and cells
			if (treeGrid.getRootNode() == null) {
				Object root = model.getRoot();
				Node rootNode = new Node();
				rootNode.setPath(new Path(root));
				rootNode.setModel(root);
				treeGrid.setRootNode(rootNode);
				viewNode(rootNode);
			}
		}
//		return treeGrid;
	}

	public void viewNodeCell(BodyCell cell) {
		if (cell.getModel() != null) {
			cell.setText(cell.getModel().toString());
		}
	}

	public void viewNodeTitle(Node node) {
		Object nodeModel = node.getModel();
		if (nodeModel != null) {
			node.setTitle(nodeModel.toString());
		}
	}

	public void viewNodeChildren(Node node) {
		TreeGridModel model = node.getTreeGrid().getModel();
		Path path = node.getPath();

		Collection children = model.getChildren(path);
		if ((children != null) && !children.isEmpty()) {
			Iterator itChildren = children.iterator();
			while (itChildren.hasNext()) {
				Object child = itChildren.next();
				Node childNode = new Node();
				childNode.setPath(new Path(path, child));
				childNode.setModel(child);
				node.addChildNode(childNode);
				viewNode(childNode);
			}
		}
	}
	
	public void viewNodeCells(Node node) {
		TreeGridModel model = node.getTreeGrid().getModel();
		Path path = node.getPath();

		Collection cells = model.getCells(path);
		if ((cells != null) && !cells.isEmpty()) {
			// node with cells
			Iterator itCells =cells.iterator();
			while (itCells.hasNext()) {
				Object cellModel = itCells.next();
				BodyCell cell = new BodyCell();
				cell.setModel(cellModel);
				node.addBodyCell(cell);
				viewNodeCell(cell);
			}
		}
	}
	
	public void viewNode(Node node) {
		viewNodeTitle(node);
		viewNodeChildren(node);
		viewNodeCells(node);
	}

	public void viewHeaderCell(HeaderCell cell) {
		cell.setTitle(cell.getModel());
	}

//	public TreeGridModel modelize(TreeGrid treeGrid) {
//		TreeGridModel model = new DefaultTreeGridModel();
//		Iterator itCells = treeGrid.getHeaderLine().getCells().iterator();
//		while (itCells.hasNext()) {
//			HeaderCell cell = (HeaderCell)itCells.next();
//			modelizeHeaderCell(cell, model);
//		}
//		return model;
//	}
//
//	public void modelizeHeaderCell(HeaderCell cell, TreeGridModel model) {
//		model.setColumnTitle(cell.getIndex(), cell.getTitle());
//	}

}
