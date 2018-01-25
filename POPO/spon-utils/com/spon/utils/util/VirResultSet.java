package com.spon.utils.util;

import java.util.ArrayList;
import java.util.Arrays;

public class VirResultSet {
	ArrayList list;			//結果集
	ArrayList colList;		//欄位名稱清單
	String orderBy = "";
	int orderById = 0;
	int cursor = -1;
	public VirResultSet() {
		// TODO Auto-generated constructor stub
		list = new ArrayList();
		colList = new ArrayList();
		cursor = -1;
	}

	public boolean next(){
		cursor ++;
		if (cursor > list.size() - 1){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean isEmpty(){
		if (list.size() == 0){
			return true;
		}else{
			return false;
		}
	}
	
	public void putResult(ArrayList row){
		list.add(row);
	}
	
	public void putColumnList(ArrayList colList){
		this.colList = colList;
	}
	
	public void setOrderBy(String orderBy) {
		// TODO Auto-generated method stub
		this.orderBy = orderBy;
		for (int i = 0; i < colList.size(); i ++){
			String field = (String)colList.get(i);
			field = field.toUpperCase();
			if (field.equals(orderBy.trim().toUpperCase())){
				orderById = i;
				break;
			}
		}
	}
	
	public void sort(){
		String[] sorting = new String[list.size()];
		for (int i = 0; i < list.size(); i ++){
			ArrayList ele = (ArrayList)list.get(i);
			sorting[i] = (String)ele.get(this.orderById);
		}
		if (!this.orderBy.equals("")){
			
		}
	}
	
	public String getString(String columnName){
		String value = "";
		for (int i = 0; i < colList.size(); i ++){
			String field = (String)colList.get(i);
			field = field.toUpperCase();
			if (field.equals(columnName)){
				ArrayList row = (ArrayList)list.get(cursor);
				value = (row.get(i) == null)? "":(String)row.get(i);
				break;
			}
		}
		return value;
	}
}
