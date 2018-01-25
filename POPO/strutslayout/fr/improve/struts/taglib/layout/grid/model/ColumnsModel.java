package fr.improve.struts.taglib.layout.grid.model;

/**
 * Columns model<br>
 * <br>
 * 24 nov. 08
 *
 * @author Gilles Rossi
 *
 */
public interface ColumnsModel {
	/**
	 * Returns the title of the column index
	 * @param index the column index
	 * @return the title
	 */
	String getColumnTitle(int index);
//	void setColumnTitle(int index, String title);
	/**
	 * Returns the column count
	 * @return the column count
	 */
	int getColumnCount();
}
