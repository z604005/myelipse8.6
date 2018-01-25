package fr.improve.struts.taglib.layout.treegrid.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Tree path.<br>
 * A tree path is composed of a pathElement list :<br>
 * <br>
 * Path = pathElement/pathElement/pathElement
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class Path {
	
	private List elements;
	
	public Path(List elements) {
		this.elements = elements;
	}
	public Path(Object element) {
		this(null, element);
	}
	public Path(Path path, Object element) {
		if (path != null) {
			elements = new ArrayList(path.getElements());
		} else {
			elements = new ArrayList();
		}
		elements.add(element);
	}
	
	public List getElements() {
		return elements;
	}
	public Object getLastElement() {
		return elements.get(elements.size()-1);
	}
	public boolean isRoot() {
		return elements.size() == 1;
	}
}
