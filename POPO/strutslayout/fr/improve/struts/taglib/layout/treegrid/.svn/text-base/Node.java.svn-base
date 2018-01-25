package fr.improve.struts.taglib.layout.treegrid;

import java.util.Collection;

import fr.improve.struts.taglib.layout.grid.BodyCell;
import fr.improve.struts.taglib.layout.grid.Line;
import fr.improve.struts.taglib.layout.treegrid.model.Path;
import fr.improve.struts.taglib.layout.util.CSSElement;
import fr.improve.struts.taglib.layout.util.Widget;

/**
 * TreeGrid widget node<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class Node extends Widget implements CSSElement {

	private String title;
	private String style;
	private String styleClass;
	private Line line;
	private Widget children;
	private Path path;
	
	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Node() {
		this(null);
	}
	
	public Node(String title) {
		this.title = title;
		this.line = new Line();
		addWidget(this.line);
		this.children = new Widget();
		addWidget(this.children);
	}

	public boolean isRoot() {
		return getParent() instanceof TreeGrid;
	}
	public int getLevel() {
		if (isRoot()) {
			return 0;
		}
		return getParentNode().getLevel()+1;
	}
	public Node getParentNode() {
		if (isRoot()) {
			return null;
		} else {
			return (Node)getParent().getParent(); // because a node is in "nodes" container, before his node parent
		}
	}
	public TreeGrid getTreeGrid() {
		if (isRoot()) {
			return ((TreeGrid)getParent());
		} else {
			return getParentNode().getTreeGrid();
		}
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public Object getModel() {
		return line.getModel();
	}
	public void setModel(Object model) {
		line.setModel(model);
	}
	public Collection getChildNodes() {
		return children.getWidgets();
	}
	public void addChildNode(Node childNode) {
		children.addWidget(childNode);
	}
	public Collection getBodyCells() {
		return line.getCells();
	}
	public void addBodyCell(BodyCell cell) {
		line.addCell(cell);
	}
}
