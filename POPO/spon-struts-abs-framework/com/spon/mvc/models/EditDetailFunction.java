package com.spon.mvc.models;

import java.util.Map;

public interface EditDetailFunction {
	
	/**
	 * 查詢細項資料
	 * @param detail_type
	 * @param detail_data_map
	 */
	public Map queryDetailData( String detail_type, Map detail_data_map );
	
	/**
	 * 新增細項資料
	 * @param detail_data_map
	 */
	public void addDetailData( String detail_type, Map detail_data_map );
	
	/**
	 * 更新細項資料
	 * @param detail_data_map
	 */
	public void saveDetailData( String detail_type, Map detail_data_map );
	
	/**
	 * 刪除細項資料
	 * @param detail_data_map
	 */
	public void delDetailData( String detail_type, Map detail_data_map );
	
	
	
}
