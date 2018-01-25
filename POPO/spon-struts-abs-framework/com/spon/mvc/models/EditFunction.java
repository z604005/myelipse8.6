package com.spon.mvc.models;

import java.util.Map;

public interface EditFunction {
	
	/**
	 * 新增資料
	 * @param data_map
	 */
	public void addData( Map data_map );
	
	/**
	 * 更新資料
	 * @param data_map
	 */
	public void saveData( Map data_map );
	
	/**
	 * 刪除資料
	 * @param data_map
	 */
	public void delData( Map data_map );
	
}
