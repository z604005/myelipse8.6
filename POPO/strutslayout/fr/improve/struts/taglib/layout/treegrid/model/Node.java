package fr.improve.struts.taglib.layout.treegrid.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * TreeGrid node<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class Node {

	private Object object;
	private Node parent;
	private int index;
	private Collection childNodes;
	private Collection cells;

	public Node() {
		this(null);
	}
	public Node(Object object) {
		this(object, null);
	}
	
	public Node(Object[] cellObjects) {
		this(null, cellObjects);
	}
	
	public Node(Object object, Object[] cellObjects) {
		this.object = object;
		cells = new ArrayList();
		childNodes = new ArrayList();
		if (cellObjects != null) {
			for (int i = 0; i < cellObjects.length; i++) {
				addCell(cellObjects[i]);
			}
		}
	}

	public void addCell(Object object) {
		this.cells.add(object);
	}
	
	public void addChildNode(Node node) {
		node.parent = this;
		node.index = childNodes.size();
		childNodes.add(node);
	}
	
	public boolean isRoot() {
		return parent == null;
	}
	public int getLevel() {
		if (isRoot()) {
			return 0;
		} else {
			return parent.getLevel()+1;
		}
	}
	public Node getParent() {
		return parent;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public Collection getCells() {
		return cells;
	}
	public void setCells(Collection cells) {
		this.cells = cells;
	}
	public Collection getChildNodes() {
		return childNodes;
	}
	public Collection getChildren() {
		Collection children = new ArrayList();
		Iterator itChildNodes = childNodes.iterator();
		while (itChildNodes.hasNext()) {
			Node node = (Node)itChildNodes.next();
			children.add(node.getObject());
		}
		return children;
	}
	public int getIndex() {
		return index;
	}
}
