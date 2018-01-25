package fr.improve.struts.taglib.layout.grid.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of ColumnsModel<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public class DefaultColumnsModel implements ColumnsModel {

	private List columns;

	public DefaultColumnsModel() {
		this.columns = new ArrayList();
	}

	public void addColumn(String title) {
		columns.add(title);
	}
	public String getColumnTitle(int index) {
		return (String)columns.get(index);
	}
//	public void setColumnTitle(int index, String title) {
//		columns.set(index, title);
//	}
	public int getColumnCount() {
		return columns.size();
	}
}
