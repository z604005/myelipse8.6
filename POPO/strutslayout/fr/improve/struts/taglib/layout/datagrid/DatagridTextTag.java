package fr.improve.struts.taglib.layout.datagrid;

import fr.improve.struts.taglib.layout.collection.CollectionItemTag;
import fr.improve.struts.taglib.layout.collection.SimpleItemContext;

/**
 * This tag creates a simple datagrid column :
 * the column does not have special input data,
 * but allows to call a Javascript method to add new data.
 * 
 * @author jribette
 */
public class DatagridTextTag extends CollectionItemTag {	
	/**
	 * Use a datagrid item context.
	 */
	protected SimpleItemContext createItemContext() {
		DatagridItemContext context = new DatagridItemContext();
		ColumnType type = ColumnType.empty();		
		context.setColumnType(type);
		return context;
	}
	
	
	
	/**
	 * Release data.
	 */
	public void release() {
		super.release();
		((DatagridItemContext)context).getColumnType().setJavascript(null);
	}

	public void setOnnewrow(String onnewline) {
		((DatagridItemContext)context).getColumnType().setJavascript(onnewline);
	}
}
