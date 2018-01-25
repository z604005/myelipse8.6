package fr.improve.struts.taglib.layout.treegrid.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import fr.improve.struts.taglib.layout.grid.model.DefaultColumnsModel;
import fr.improve.struts.taglib.layout.util.LayoutUtils;

/**
 * Bean implementation of TreeGridModel, based on DefaultColumnsModel.<br>
 * "Bean" means that the last path element is a bean which a property can return the child nodes.<br>
 * The cells of a path are also provided by a bean property <br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class BeanTreeGridModel extends DefaultColumnsModel implements TreeGridModel {

	private Object rootBean;
	private List columnProperties;
	private Map childrenProperties;
	private Class cellsBeanClass;

	public BeanTreeGridModel(Object rootBean) {
		this.rootBean = rootBean;
		columnProperties = new ArrayList();
		childrenProperties = new HashMap();
	}
	
	private boolean instanceOf(Object bean, Class beanClass) {
		if ((beanClass != null) && (bean != null)) {
			return beanClass.isAssignableFrom(bean.getClass());
		}
		return false;
	}
	
	private String getChildrenPropery(Class clazz) {
		String childrenProperty = (String)childrenProperties.get(clazz);
		if (childrenProperty == null) {
			Class superClass = clazz.getSuperclass();
			if (superClass != null) {
				return getChildrenPropery(superClass);
			}
		}
		return childrenProperty;
	}
	
	public Collection getChildren(Path path) {
		try {
			Object lastPathElement = path.getLastElement();
			Collection nodeBeans = null;
			String childrenProperty = getChildrenPropery(lastPathElement.getClass());
			if (childrenProperty != null) {
				nodeBeans = LayoutUtils.getCollection(LayoutUtils.getProperty(lastPathElement, childrenProperty));
			}
			return nodeBeans;
		} catch (JspException e) {
			throw new RuntimeException(e);
		}
	}

	public Collection getCells(Path path) {
		Collection cells = null;
		Object lastPathElement = path.getLastElement();
		if (instanceOf(lastPathElement, cellsBeanClass)) {
			cells = new ArrayList();
			for (int i = 0; i < getColumnCount(); i++) {
				String property = (String)columnProperties.get(i);
				try {
					Object cellObject;
					if (property != null) {
						cellObject = LayoutUtils.getProperty(lastPathElement, property);
					} else {
						cellObject = lastPathElement;
					}
					cells.add(cellObject);
				} catch (JspException e) {
					throw new RuntimeException(e);
				}
			}
		}
		return cells;
	}
	
	public Object getRoot() {
		return rootBean;
	}
	
	public void addChildrenProperty(Class clazz, String chidrenProperty) {
		childrenProperties.put(clazz, chidrenProperty);
	}
	
	public void addColumn(String title, String property) {
		addColumn(title);
		addColumnProperty(property);
	}

	public void addColumnProperty(String property) {
		columnProperties.add(property);
	}

	public void setCellsBeanClass(Class cellsBeanClass) {
		this.cellsBeanClass = cellsBeanClass;
	}
}
