package com.spon.utils.struts.form;

import java.util.List;

public class BA_Detail_Head_Attr {

	private String SQL="",SQLOTHER="";
	private String PROGRAM="";
	private String KEYID="";
	private String GRID_HEIGHT="800";
	private String GRID_WIDTH="300";
	private int PAGENUM=0;
	private int RS_Per_Page=0;
	private String MODE="";
	private int HEIGHT=25;
	private int FONTSIZE=12;
	private List BA_Detail_attr;
	private boolean INSERT_RIGHT=false;
	private boolean UPDATE_RIGHT=false;
	private boolean DELETE_RIGHT=false;
	
	public List getBA_Detail_attr() {
		return BA_Detail_attr;
	}
	
	
	public String getPROGRAM() {
		return PROGRAM;
	}
	public String getSQL() {
		return SQL;
	}
	/**
	 * 設定欄位的參數 用List裝
	 * @param detail_attr
	 */
	public void setBA_Detail_attr(List detail_attr) {
		BA_Detail_attr = detail_attr;
	}
	
	

	/**
	 * 設定要呼叫的action名稱
	 * @param program
	 */
	public void setPROGRAM(String program) {
		PROGRAM = program;
	}
	/**
	 * 設定SQL語法
	 * @param sql
	 */
	public void setSQL(String sql) {
		SQL = sql;
	}
	public int getPAGENUM() {
		return PAGENUM;
	}
	/**
	 * 設定地幾頁資料 如為 0 表示不分頁  全部要
	 * @param pagenum
	 */
	public void setPAGENUM(int pagenum) {
		PAGENUM = pagenum;
	}


	public int getRS_Per_Page() {
		return RS_Per_Page;
	}

	/**
	 * 設定每頁幾筆
	 * @param per_Page
	 */
	public void setRS_Per_Page(int per_Page) {
		RS_Per_Page = per_Page;
	}


	public String getMODE() {
		return MODE;
	}

	/**
	 * 設定新增或修改模式(new or edit)
	 * @param mode
	 */

	public void setMODE(String mode) {
		MODE = mode;
	}


	public boolean isDELETE_RIGHT() {
		return DELETE_RIGHT;
	}


	public boolean isINSERT_RIGHT() {
		return INSERT_RIGHT;
	}


	public boolean isUPDATE_RIGHT() {
		return UPDATE_RIGHT;
	}


	public void setDELETE_RIGHT(boolean delete_right) {
		DELETE_RIGHT = delete_right;
	}


	public void setINSERT_RIGHT(boolean insert_right) {
		INSERT_RIGHT = insert_right;
	}


	public void setUPDATE_RIGHT(boolean update_right) {
		UPDATE_RIGHT = update_right;
	}


	public int getHEIGHT() {
		return HEIGHT;
	}

	/**
	 * 設定行的高度 預設25
	 * @param height
	 */
	public void setHEIGHT(int height) {
		HEIGHT = height;
	}


	public int getFONTSIZE() {
		return FONTSIZE;
	}

/**
 * 設定字型大小  預設為12
 * @param fontsize
 */

	public void setFONTSIZE(int fontsize) {
		FONTSIZE = fontsize;
	}


public String getKEYID() {
	return KEYID;
}


public void setKEYID(String keyid) {
	KEYID = keyid;
}


public String getGRID_HEIGHT() {
	return GRID_HEIGHT;
}


public String getGRID_WIDTH() {
	return GRID_WIDTH;
}

/**
 * 設定GRID的高度 預設300
 * @param grid_height
 */
public void setGRID_HEIGHT(String grid_height) {
	GRID_HEIGHT = grid_height;
}

/**
 * 設定GRID的寬度度 預設800
 * @param grid_width
 */
public void setGRID_WIDTH(String grid_width) {
	
	GRID_WIDTH = grid_width;
}


public String getSQLOTHER() {
	return SQLOTHER;
}


public void setSQLOTHER(String sqlother) {
	SQLOTHER = sqlother;
}
	
	
	
}
