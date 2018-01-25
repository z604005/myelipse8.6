package com.spon.utils.util;

import java.sql.Connection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import fr.improve.struts.taglib.layout.util.FormUtils;


public class BA_EMS_Tag implements Tag{
	/**
	 * 需要查詢的資料
	 */
	private String data = "";
	/**
	 * 呼叫者的物件
	 */
	private String caller = "";
	/**
	 * 呼叫者的物件的 id
	 */
	private String id = "";
	/**
	 * 呼叫者的需要的 function
	 */
	private String function = "";
	/**
	 * 呼叫者的需要的 width
	 */
	private String width = "";
	/**
	 * 呼叫者的需要的 height
	 */
	private String height = "";
	/**
	 * 呼叫者的需要的 depkey
	 */
	private String depkey = "";
	/**
	 * 呼叫者的需要的 empkey
	 */
	private String empkey = "";
	/**
	 * 呼叫者的需要的 changescript
	 */
	private String changescript = "";
	/**
	 * 呼叫者的需要的 beforerun
	 */
	private String beforerun = "";
	/**
	 * 呼叫者的需要的 clearfield
	 */
	private String clearfield = "";
	
	/**
	 * 如果caller是陣列的話，須傳入正確的索引值
	 */
	private int index = -1;
	private int totalRowCount = 0;
	private String mode = "";
	private PageContext pageContext;
	private Tag parent;
	private String connectionString = "SPOS"; //modify by joe 2010/09/01

	
	public String getDepkey() {
		return depkey;
	}
	public void setDepkey(String depkey) {
		this.depkey = depkey;
	}
	public String getEmpkey() {
		return empkey;
	}
	public void setEmpkey(String empkey) {
		this.empkey = empkey;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public String getHeight() {
		return height;
	}
	public void setHeight(String height) {
		this.height = height;
	}
	public String getChangescript() {
		return changescript;
	}
	public void setChangescript(String changescript) {
		this.changescript = changescript;
	}
	public String getBeforerun() {
		return beforerun;
	}
	public void setBeforerun(String beforerun) {
		this.beforerun = beforerun;
	}
	public String getClearfield() {
		return clearfield;
	}
	public void setClearfield(String clearfield) {
		this.clearfield = clearfield;
	}
	/**
	 * @param mode The mode to set.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}
	/**
	 * @return Returns the mode.
	 */
	public String getMode() {
		return mode;
	}
	/**
	 * @return Returns the id.
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param idKey The idKey to set.
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return Returns the index.
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index The index to set.
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	/**
	 * @return Returns the caller.
	 */
	public String getCaller() {
		return caller;
	}

	/**
	 * @param caller The caller to set.
	 */
	public void setCallerKey(String caller) {
		this.caller = caller;
	}
	
	
	


	/**
	 * @return Returns the recordsCount.
	 */
	public int getTotalRowCount() {
		return totalRowCount;
	}
	public String toCharset(String str, String fromCharset , String toCharset){
		String s = "";
			try{
			s = new String(str.getBytes(fromCharset), toCharset);
			}catch(Exception er){ 
			System.out.println(er.toString());
			return ""; 
			}
		return s;
	}	
	/**
	 * 顯示多筆資料
	 * @param curPage 目前頁碼
	 * @param rowsPerPage 每頁要顯示的筆數
	 * @return
	 * @throws Exception 
	 */
	public Vector showDatas(HttpServletRequest request, int curPage, int rowsPerPage){
		
		Connection conn = null;
		
		
		try{
		
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
	/**
	 * 顯示LOV的Caller Field
	 * @return
	 */
	public String showField(){
		String field = "";
		try{
			//CREATE_MODE = 0;
			//EDIT_MODE = 1;
			//INSPECT_MODE = 2;
			int displayMode = FormUtils.getFormDisplayMode(pageContext);
			
//			String onclick_field= "";
//			if(!"".equals(this.changescript) && this.changescript != null){
//				onclick_field = this.changescript;
//				onclick_field = onclick_field.replaceAll("\'","\"");
//			}
				String[] id = this.id.split(",");
			//如果id.length<1代表是不取得回傳值，要將選取的按鈕隱藏起來
			field += "<script language='javascript'>\n";
			field += "<!--//\n";		
			field += "function popUpEMS_" + id[0] + "(form){\n";
			
			if(!"".equals(this.beforerun) && !(this.beforerun == null )){  //主要的程式執行之前，先做檢核的javascript
				field += "if(!"+this.beforerun+"){\n" +
						 "return false;\n" +
						 "}\n";
			}		
			field += "return openpopup2(form , \"EMS.do?reqCode=init" +
				"&id=" + this.id + 
				"&function=" + this.function ;
				if(!"".equals(this.changescript) && this.changescript != null){  //代入後欲執行的javascript
					field += "&changescript=" + this.changescript;
				}
				if(!"".equals(this.clearfield) && this.clearfield != null){  //代入值後要清除的欄位
					field += "&clearfield=" + this.clearfield;
				}
				if(!"".equals(this.depkey) && !(this.depkey == null )){  //部門代號
					field += "&depkey=\"+" + this.depkey +"+\"";
				}
				if(!"".equals(this.empkey) && !(this.empkey == null )){  //員工工號
					field += "&empkey=\"+" + this.empkey +"+\"";
				}
				if(!"".equals(this.width) && !(this.width == null ) && !"".equals(this.height) && !(this.height == null )){
					field += "\", \"" + this.width + 
							 "\", \"" + this.height +
							 "\");\n" ;
				}else{
					field += "\", \"450" + 
					 		 "\", \"400" +
					 		 "\");\n" ;
				}
			field += "}";
			field += "\n";
			field += "//-->\n";
			field += "</script>\n";
			field += "<input type='button' value='...' id='emsbtn_" + id[0] + "' onclick='javascript:popUpEMS_" + id[0] + "(this.form);'" ;
			//modify by John 2012.10.15
			if(!"".equals(mode) && "F".equals(mode.split(",")[displayMode])){
				field += " disabled ";
			}
			field += "/>\n";
			//this.closeConn();
		}catch(Exception e){
			System.out.println("Exception showField()" + e);
		}
		return field;
	}
	
	
	/**
	 * 一次設定LOV屬性值
	 * @param form	表單名稱
	 * @param callerKey 回傳key給 caller field
	 * @param callerValue 回傳value給 caller field
	 * @param table 表格名稱
	 * @param leftjoin 相關檔案
	 * @param fieldAlias 中文顯示欄位別名 各欄位須以','隔開
	 * @param fieldName 原英文欄名 各欄位須以','隔開
	 */
	public void setProperties(String form, String id,
			String lovField, String value,
			String table, String leftjoin,String fieldAlias, String fieldName, String others,String hideCust,String parentId,String parentField){

	}

	public BA_EMS_Tag(){
		super();
	}
	
	public void setPageContext(PageContext arg0) {
		// TODO Auto-generated method stub
		this.pageContext = arg0;
	}
	public void setParent(Tag arg0) {
		// TODO Auto-generated method stub
		this.parent = arg0;
	}
	public Tag getParent() {
		// TODO Auto-generated method stub
		return parent;
	}
	public int doStartTag() throws JspException {
		// TODO Auto-generated method stub
		return SKIP_BODY;
	}
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		try{
			pageContext.getOut().write(showField());
		}catch(java.io.IOException e){
			throw new JspTagException("IO Error: " + e.getMessage());
		}
		return EVAL_PAGE;
	}
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
