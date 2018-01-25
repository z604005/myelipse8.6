package com.spon.ems.fileimport;

import java.sql.Connection;
import java.util.List;

import jxl.Workbook;

public interface EMS_FILEIMPORT_XLS {
	
	/**
	 * 建立 XLS DATA LIST
	 * @param wbk
	 * @return
	 */
	public List generateXLSDataList( Connection conn, Workbook wbk, String comp_id );
}
