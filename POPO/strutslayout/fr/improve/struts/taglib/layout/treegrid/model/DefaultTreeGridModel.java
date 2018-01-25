package fr.improve.struts.taglib.layout.treegrid.model;

import java.util.Collection;
import java.util.Iterator;

import fr.improve.struts.taglib.layout.grid.model.DefaultColumnsModel;

/**
 * Simple implementation of TreeGridModel, based on DefaultColumnsModel<br>
 * This implementation used TreeGrid nodes<br>
 * <br>
 * 24 nov. 08
 * <br>
 * @author Gilles Rossi
 *
 */
public class DefaultTreeGridModel extends DefaultColumnsModel implements TreeGridModel {

	private Node rootNode;
	
	public DefaultTreeGridModel(Node rootNode) {
		this.rootNode = rootNode;
	}
	
	protected Node searchNode(Path path, int indexPath, Collection nodes) {
		Object pathElement = path.getElements().get(indexPath);
		Iterator itNodes = nodes.iterator();
		while (itNodes.hasNext()) {
			Node node = (Node)itNodes.next();
			if (((pathElement != null) && pathElement.equals(node.getObject())) ||
					((pathElement == null) && (node.getObject() == null))) {
				if (indexPath < path.getElements().size()-1) {
					return searchNode(path, indexPath+1, node.getChildNodes());
				}
				return node;
			}
		}
		return null;
	}

	protected Node searchNode(Path path) {
		if (path.isRoot()) {
			return rootNode;
		} else {
			return searchNode(path, 1, rootNode.getChildNodes());
		}
	}

	
	public final Collection getCells(Path path) {
		Node node = searchNode(path);
		if (node != null) {
			return node.getCells();
		} else {
			return null;
		}
	}
	public final Collection getChildren(Path path) {
		Node node = searchNode(path);
		if (node != null) {
			return node.getChildren();
		} else {
			return null;
		}
	}
	public final Object getRoot() {
		return rootNode.getObject();
	}
	
//	public static TreeGridModel buildModel() {
//		Node r = new Node("ROOT");
//		Node a = new Node("A");
//		r.addChildNode(a);
//		Node f1 = new Node("F1");
//		a.addChildNode(f1);
//		Node b = new Node("B");
//		r.addChildNode(b);
//		Node f2 = new Node("F2");
//		b.addChildNode(f2);
//		Node f3 = new Node("F3");
//		b.addChildNode(f3);
//		return new DefaultTreeGridModel(r);
//	}
//	public static void showNode(TreeGridModel model, Path path) {
//		System.out.println(path.getElements().size() + " " + path.getLastElement());
//		Iterator itChildren = model.getChildren(path).iterator();
//		while (itChildren.hasNext()) {
//			showNode(model, new Path(path, itChildren.next()));
//		}
//		
//	}
//	public static void main(String[] args) {
//		TreeGridModel model = buildModel();
//		Object root = model.getRoot();
//		showNode(model, new Path(root));
//		
//	}
}
