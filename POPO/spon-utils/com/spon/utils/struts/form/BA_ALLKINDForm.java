//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_4.0.1/xslt/JavaClass.xsl

package com.spon.utils.struts.form;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * MyEclipse Struts
 * Creation date: 02-11-2006
 * 
 * XDoclet definition:
 * @struts.form name="BA_ALLKINDForm"
 */
public class BA_ALLKINDForm extends ActionForm {

	// --------------------------------------------------------- Instance Variables

	/** col_name property */
	private String col_name;

	/** table_name property */
	private String table_name;

	/** item_id property */
	private String item_id;

	/** item_value property */
	private String item_value;
	
	private List dependslist = new ArrayList();

	// --------------------------------------------------------- Methods

	/** 
	 * Method validate
	 * @param mapping
	 * @param request
	 * @return ActionErrors
	 */
	public ActionErrors validate(
		ActionMapping mapping,
		HttpServletRequest request) {

		// TODO Auto-generated method stub
		return null;
	}

	/** 
	 * Method reset
	 * @param mapping
	 * @param request
	 */
	public void reset(ActionMapping mapping, HttpServletRequest request) {

		// TODO Auto-generated method stub
	}

	/** 
	 * Returns the col_name.
	 * @return String
	 */
	public String getCol_name() {
		return col_name;
	}

	/** 
	 * Set the col_name.
	 * @param col_name The col_name to set
	 */
	public void setCol_name(String col_name) {
		this.col_name = col_name;
	}

	/** 
	 * Returns the table_name.
	 * @return String
	 */
	public String getTable_name() {
		return table_name;
	}

	/** 
	 * Set the table_name.
	 * @param table_name The table_name to set
	 */
	public void setTable_name(String table_name) {
		this.table_name = table_name;
	}

	/** 
	 * Returns the item_id.
	 * @return String
	 */
	public String getItem_id() {
		return item_id;
	}

	/** 
	 * Set the item_id.
	 * @param item_id The item_id to set
	 */
	public void setItem_id(String item_id) {
		this.item_id = item_id;
	}

	/** 
	 * Returns the item_value.
	 * @return String
	 */
	public String getItem_value() {
		return item_value;
	}

	/** 
	 * Set the item_value.
	 * @param item_value The item_value to set
	 */
	public void setItem_value(String item_value) {
		this.item_value = item_value;
	}
	
	public List getDependslist() {
		return dependslist;
	}

	public void setDependslist(List dependslist) {
		this.dependslist = dependslist;
	}

}

