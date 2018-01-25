package com.spon.utils.util;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.struts.action.ActionForm;

public class VirTable {
	ArrayList table;
	ArrayList colName;
	public VirTable() {
		// TODO Auto-generated constructor stub
		table = new ArrayList();
		colName = new ArrayList();
	}
	/**
	 * 將查詢結果以FormBean List儲存後，放入 VirTable 中
	 * @param list FormBean List
	 */
	public void putList(Collection list){
		ArrayList aList = (ArrayList)list;
		Iterator ite = aList.iterator();
		while (ite.hasNext()){
			ActionForm form = (ActionForm)ite.next();
			Method[] methods = form.getClass().getMethods();
			String className = form.getClass().getName();
			ArrayList rows = new ArrayList();
			for (int i = 0; i < methods.length; i ++){
				String methodName = methods[i].getName();
				if (methodName.startsWith("get")){
					colName.add(methodName.substring(3, methodName.length()));
				}
			}
			for (int i = 0; i < methods.length; i ++){
				String methodName = methods[i].getName();
				if (methodName.startsWith("get")){
					try {
						Object obj = methods[i].invoke(form, null);
						rows.add(obj);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			table.add(rows);
		}
	}
	
	public void putList(ResultSet rs){
		try {
			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 1; i <= meta.getColumnCount(); i ++){
				String columnName = meta.getColumnName(i);
				colName.add(columnName);
			}
			while (rs.next()){
				ArrayList rows = new ArrayList();
				for (int i = 0; i < colName.size(); i ++){
					String columnName = (String)colName.get(i);
					try {
						String value = (rs.getString(columnName) == null)? "": rs.getString(columnName);
						rows.add(value);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				table.add(rows);
			}		
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public VirResultSet query(String expression){
		expression = expression.trim();
		String[] sentences = null;
		String[] operation = null;
		String orderBy = "";
		//看看有沒有order by 子句
		if (expression.toUpperCase().indexOf("ORDER BY") >= 0){
			sentences = expression.toUpperCase().split("ORDER BY");
		}
		if (sentences == null){
			operation = expression.split("=");
		}else{
			operation = sentences[0].split("=");
			orderBy = sentences[1].trim();
		}
		VirResultSet rs = null;
		try {
			if (operation != null && operation.length == 2){
				for (int i = 0; i < colName.size(); i ++){
					String field = (String)colName.get(i);
					field = field.toUpperCase();
					if (field.equals(operation[0].trim().toUpperCase())){
						rs = queryResultSet(i, operation[1], orderBy);
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("VirTable.query() Error." + e.getMessage());
		}
		return rs;
	}
	
	private VirResultSet queryResultSet(int index, String target, String orderBy){
		VirResultSet rs = new VirResultSet();
		if (!orderBy.equals("")) rs.setOrderBy(orderBy);
		try {
			for (int i = 0; i < table.size(); i ++){
				ArrayList rows = (ArrayList)table.get(i);
				String value = (String)rows.get(index);
				if (value.equals(target)){
					rs.putColumnList(colName);
					rs.putResult(rows);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("VirTable.queryResultSet() Error." + e.getMessage());
		}
		return rs;
	}
}
