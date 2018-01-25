package com.spon.utils.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import fr.improve.struts.taglib.layout.util.FormUtils;


public class BA_LOV implements Tag{
	/**
	 * 查詢資料表
	 */
	private String table = "";
	/**
	 * 呼叫者的物件
	 */
	private String caller = "";
	/**
	 * 呼叫者的物件的 id
	 */
	private String id = "";
	/**
	 * LOV中的Fields
	 */
	private String lovField = "";
	/**
	 * 資料預設值
	 */
	private String value = "";
	/**
	 * 表單名稱
	 */
	private String form = "";
	/**
	 * 其他條件
	 */
	private String others = "";
	/**
	 * Left join
	 */
	private String leftjoin="";
	
	/**
	 * 查詢關鍵字
	 */
	private String keywords = "";
	/**
	 * 顯示於LOV中的中文欄位別名
	 */
	private String fieldAlias;
	/**
	 * 顯示於LOV中的中文欄位別名的對應表格欄位名稱
	 */
	private String fieldName;
	/**
	 * 呼叫者的需要的 changescript
	 */
	private String changescript = "";
	/**
	 * 呼叫者的需要的 beforerun
	 */
	private String beforerun = "";
	/**
	 * 呼叫者的需要的 orderby
	 */
	private String orderby = "";
	/**
	 * 呼叫者的需要的 return_flag
	 */
	private String return_flag = "";
	
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
	
	public String getOrderby() {
		return orderby;
	}
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
	
	public String getReturn_flag() {
		return return_flag;
	}
	public void setReturn_flag(String returnFlag) {
		return_flag = returnFlag;
	}

	/**
	 * 如果caller是陣列的話，須傳入正確的索引值
	 */
	private int index = -1;
	private int totalRowCount = 0;
	private String mode = "";
	private PageContext pageContext;
	private Tag parent;
	private String keywordsField = "";
	private String keywordsget="";
	private String connectionString = "SPOS"; //modify by joe 2010/09/01
	/**
	 * 如果為子項目，則必須有值
	 */
	private String parentId="";
	/**
	 * 如果為子項目，則必須有值
	 */
	private String parentField="";
	/**
	 * 如果為"like"，則視同"like"搜尋，否則視同"=="搜尋
	 */
	private String parentTarget="";
	
	/**
	 * 根據來源，判斷是否為客製化LOV
	 * 有值則為客製化
	 */
	private String sourceTarget="";
	
	
	public String getParentField() {
		return parentField;
	}
	public void setParentField(String parentField) {
		this.parentField = parentField;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getParentTarget() {
		return parentTarget;
	}
	public void setparentTarget(String parenttarget) {
		this.parentTarget = parenttarget;
	}
	public String getSourceTarget() {
		return sourceTarget;
	}
	public void setSourceTarget(String sourcetarget) {
		this.sourceTarget = sourcetarget;
	}
	
	
	
	/**
	 * @return Returns the others.
	 */
	public String getOthers() {
		return others;
	}
	/**
	 * @param others The others to set.
	 */
	public void setOthers(String others) {
		this.others = others;
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
	 * @return Returns the lovKey.
	 */
	public String getLovField() {
		return lovField;
	}
	/**
	 * @param lovKey The lovKey to set.
	 */
	public void setLovField(String lovField) {
		this.lovField = lovField;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return 
	 */
	public String getFieldAlias() {
		return fieldAlias;
	}
	/**
	 * @param 
	 */
	public void setFieldAlias(String fieldAlias) {
		try {
			this.fieldAlias = URLDecoder.decode(fieldAlias,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns 
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldsNameInLOV The fieldsNameInLOV to set.
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return Returns the find.
	 * @throws Exception 
	 */
	public String getKeywords() throws Exception {
		return keywords;
	}

	/**
	 * @param find The find to set.
	 */
	public void setKeywords(String keywords) {
			//System.out.print("");
			try {
				if (keywords.length()>new String(keywords.getBytes("ISO8859-1"),"UTF-8").length()){
				     this.keywords = toCharset(keywords, "ISO8859-1", "UTF-8");
				    }else{
				     this.keywords =keywords;
				    }
				
				//System.out.print( Integer.valueOf((keywords.getBytes("ISO8859-1")) +""));
				//this.keywords = toCharset(keywords, "ISO8859-1", "UTF-8");
				//this.keywords = URLDecoder.decode(keywords,"UTF-8");
				// this.keywords =URLDecoder.decode(keywords,"UTF-8");
				//this.keywords =new String(keywords.getBytes("UTF-8"), "ISO8859_1");
			    //this.keywords = new String(keywords.getBytes("ISO8859-1"),"UTF-8");
				//this.keywords =keywords;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
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

	public void setTable(String table){
		this.table = table;
	}

	public String getTable(){
		return this.table;
	}
	
	/**
	 * @return Returns the form.
	 */
	public String getForm() {
		return form;
	}

	/**
	 * @param form The form to set.
	 */
	public void setForm(String form) {
		this.form = form;
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
//		String HTTP_ACCEPT_CHARSET = request.getHeader("Accept-Charset");
  
		String HTTP_ACCEPT_CHARSET = request.getHeader("accept-language");
		String str = request.getParameter("keywords");
		//System.out.print( System.getProperty("file.encoding"));
		Charset utf8 = Charset.forName("utf-8");
		 
		if (HTTP_ACCEPT_CHARSET!=null && !HTTP_ACCEPT_CHARSET.equals(""))
		{
			if(HTTP_ACCEPT_CHARSET.equals("zh-tw")){
				 
				if (str!=null && !str.equals(""))
				{
					if (this.keywordsget!=null && !this.keywordsget.equals(""))
					{
						this.keywords=this.keywordsget;
						this.keywordsget="";
					}
//					 
//					try{
//						/*
//						FileWriter filewriter = new FileWriter(str);
//						String encname = filewriter.getEncoding(); 
//
//		                filewriter.close(); 
//		                
//		                System.out.println("default charset is: " + encname);
//		                */
//						
//		                System.out.println(str.getBytes("UTF-8"));
// 
//		                char[] chr=str.getBytes("UTF-8").toString().toCharArray();
//		                if (chr[0]=='[') 
//		                {	
//		                	
//			              // str = toCharset(str, "ISO8859-1", "UTF-8"); //client ->iso8859-1->gb2312->UTF-8
//							//str = toCharset(str, "UTF-8", "ISO8859-1");//utf-8->ISO8859-1
//			              // str=new String(str.getBytes("utf-8"),"utf-8");
//		                }else
//		                {
//		                	
//		                }
//						this.keywords=str; 
//					}catch( Exception E){
//						E.printStackTrace();
//					}
				}	
				
			}else if(HTTP_ACCEPT_CHARSET.startsWith("UTF-8")){
			;//DONNOT CONVERT
			} 
			
		}
		 
		//98.8.24 因為來自 jsp 的 <lj:lov> 傳入的參數之一 table 其內容有單引號前已先用<~> 符號取代,所以,這理必須再轉回來才可執行sql!
		Vector datas = new Vector();
		String sql = "";
		String query = "select ";
		String colnum="";
		Connection conn = null;
		String Select="";//
		try{
			
			
			
//			DataSource ds = (DataSource)request.getSession(true).getServletContext().getAttribute("SPOS");
//			conn = ds.getConnection();
			conn = BA_TOOLS.getInstance().getConnection(connectionString);  //modify by joe 2010/09/01
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String[] fieldsNameInLOV = fieldName.split(",");
			for (int k = 0; k < fieldsNameInLOV.length; k ++){
				query += fieldsNameInLOV[k];
				if (fieldsNameInLOV.length > 1 && k < fieldsNameInLOV.length - 1) query += ",";
				colnum+= fieldsNameInLOV[k];
				if (fieldsNameInLOV.length > 1 && k < fieldsNameInLOV.length - 1) colnum += ",";
			}
			query += " from " + table.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
			query += " "+leftjoin +" ";
			String condition = " where 1=1 ";
			if (!keywords.equals("")){
				if (keywordsField.equals("")){
					//fix by steven 加上 "( )"
					for (int k = 0; k < fieldsNameInLOV.length; k ++){
						if (k == 0){
							condition += " and ( ";
						}else{
							condition += " or ";
						}
						condition += fieldsNameInLOV[k] + " like '%" + keywords + "%'";
					}
					condition+=") ";
				}else{
					condition += " and " + keywordsField + " like '%" + keywords + "%'";
				}
			}
			//-------------------先計算資料總筆數----------------------------------
			/*String isLike = "like".equals(parentTarget)?"%":"";
			if ((" " + others).indexOf("group")>0)
			{
				sql = "select count(*) recordsCount from (select " + colnum + " from " +
				table.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%") + leftjoin + condition;;
				
				if (!parentId.equals("") && !parentField.equals("")) {
						condition += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
						sql += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
				}
				
				sql += others.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
				sql +=") as t ";
				
			}else{
				sql = "select count(*) as recordsCount from " +
					table.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%") + leftjoin + condition;
				
				if (!parentId.equals("") && !parentField.equals("")) {
						condition += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
						sql += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
				}
				
				sql += others.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
			}*/
			
			
			if("yes".equals(sourceTarget)){
				//因應前端已經將傳入的字串轉換成 UTF-8 編碼的字元   所以需將斷行與空白取代，才能進行放大鏡搜尋功能
				parentField=parentField.replaceAll("\\r\\n","").replaceAll(" ","");
			
				Select=" select tableA."+colnum 
					+ " from (" 
					+ "		select replace(replace("+ colnum.trim()+", '\\r\\n',''), ' ','') as " + colnum.trim()+"_1"+","+ colnum+" AS "+colnum
					+ " 	from "+table.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%")
					+ "		) tableA  ";


				//-------------------先計算資料總筆數----------------------------------
				String isLike = "like".equals(parentTarget)?"%":"";
				if ((" " + others).indexOf("group")>0)
				{
					sql = "select count(*) recordsCount from (" +Select +"  where 1=1 ";
				
					if (!parentId.equals("") && !parentField.equals("")) {
						condition += " and " + " tableA."+parentId.trim()+"_1" + " like '" + isLike + parentField.replaceAll(" ", "") + isLike + "'";
						sql += "  and " + " tableA."+parentId.trim()+"_1" + " like '" + isLike + parentField.replaceAll(" ", "") + isLike + "'";
					}
				
					sql += others.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
					sql +=") as t ";
				
				}else{
					sql = "select count(*) as recordsCount from " +
					table.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%") + leftjoin + condition;
			
					if (!parentId.equals("") && !parentField.equals("")) {
						condition += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
						sql += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
					}
			
					sql += others.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
				}
			}else{
				String isLike = "like".equals(parentTarget)?"%":"";
				if ((" " + others).indexOf("group")>0)
				{
					sql = "select count(*) recordsCount from (select " + colnum + " from " +
					table.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%") + leftjoin + condition;;
					
					if (!parentId.equals("") && !parentField.equals("")) {
							condition += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
							sql += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
					}
					
					sql += others.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
					sql +=") as t ";
					
				}else{
					sql = "select count(*) as recordsCount from " +
						table.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%") + leftjoin + condition;
					
					if (!parentId.equals("") && !parentField.equals("")) {
							condition += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
							sql += " and " + parentId + " like '" + isLike + parentField + isLike + "'";
					}
					
					sql += others.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
				}
			}
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if (rs.next()){
				totalRowCount = rs.getInt("recordsCount");
			}
			rs.close();
			//------------------------------------------------------------------
			
			if("yes".equals(sourceTarget)){
				sql = Select + condition + others.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");;
				//sql+=" limit " + ((curPage - 1) * rowsPerPage) + ","+ rowsPerPage;
			}else{
				sql = query + condition + others.replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");;
				
			}
			//處理 SQL語法 Order by 功能
			if(!"".equals(this.orderby) && this.orderby != null && this.orderby.length() > 0 ){
				sql +=" order by " + this.orderby;  //Order by User Enter
			}else{
				sql +=" order by " + fieldName.trim();
			}
			
			rs = stmt.executeQuery(sql);
			int i = 0;
			
			int start=((curPage-1) * rowsPerPage);
			int end=rowsPerPage*curPage ;
			
			if(start==0)
				rs.beforeFirst();	
			else
				rs.absolute(start);
			
			for (int j=start;j<end; j++) {
				if(rs.next())
				{
					HashMap data = new HashMap();
					for (int k = 0; k < fieldsNameInLOV.length; k ++){
						data.put(fieldsNameInLOV[k], this.isnull(rs.getString(fieldsNameInLOV[k])).replaceAll("\\<","&lt;").replaceAll("\\>","&gt;"));
					}
					datas.add(i, data);
					i ++;
				}
				else
					j=end;
			}
			rs.close();
			stmt.close();
		}catch(Exception e){
			System.out.println("Exception BA_LOV.showDatas()" + e + sql);
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		return datas;
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
			
			String[] lovField = this.lovField.split(",");
			String[] id = this.id.split(",");
			//如果id.length<1代表是不取得回傳值，要將選取的按鈕隱藏起來
			field += "<script language='javascript'>\n";
			
			field += "<!--//\n";
			field += "function popUpLOV_" + (this.id.equals("")?lovField[0]:id[0]) + "(){\n";
			if(!"".equals(this.beforerun) && !(this.beforerun == null )){  //主要的程式執行之前，先做檢核的javascript
				field += "if(!"+this.beforerun+"){\n" +
						 "return false;\n" +
						 "}\n";
			}	
			field += "var lovwin = window.open(\""+(this.id.equals("")?"../../":"")+"templates/BA_LOV.jsp?" +
				"form=" + form +
				"&id=" + this.id +
				"&lovField=" + this.lovField +
				//"&table=" + table +
				//98.8.24 因為來自 jsp 的 <lj:lov> 傳入的參數之一 table 其內容有單引號,暫時用<~> 符號取代
				"&table=" + table.replaceAll("\'","<~>") +
				"&leftjoin=" + this.leftjoin +
				"&others=" + others.replaceAll("\'","<~>").replaceAll("%","<^>") ;
				if (!parentId.equals("") && !parentField.equals("")) {
					field += "&parentId=" + parentId +
					"&parentField=\"+" + parentField +"+\"";
					if(!parentTarget.equals("")){
						field += "&parentTarget=" + this.parentTarget;
					}
					if(!sourceTarget.equals("")){
						field += "&sourceTarget=" + this.sourceTarget;
					}
				}
				if(!"".equals(this.changescript) && this.changescript != null){  //代入後欲執行的javascript
					field += "&changescript=" + this.changescript.replaceAll("\'","<~>");
				}
				if(!"".equals(this.orderby) && this.orderby != null){  //代入後欲排序的SQL語法
					field += "&orderby=" + this.orderby;
				}
				if(!"".equals(this.return_flag) && this.return_flag != null){  //是否顯示回傳欄位
					field += "&return_flag=" + this.return_flag.trim();
				}
				field += "&fieldAlias=" + URLEncoder.encode(fieldAlias, "UTF-8") +
				
				"&fieldName=" + fieldName + "\", \"lovwin\"," ;
//				"\"top=20,left=200,height=450,width=550,status=no,toolbar=no,menubar=no,location=no\");\n" +
				
				if(!sourceTarget.equals("")){
					field+="\"top=20,left=200,height=600,width=650,status=no,toolbar=no,menubar=no,location=no,scrollbars=1\");\n" ;
				}
				else{	//利用lovField的數量去控制popup視窗的大小(尚未找到靈活的寫法，暫時寫死的)
					if(lovField.length<4){
						field+="\"top=20,left=200,height=500,width=550,status=no,toolbar=no,menubar=no,location=no,scrollbars=1\");\n" ;
					}else if(lovField.length<7){
						field+="\"top=20,left=200,height=500,width=900,status=no,toolbar=no,menubar=no,location=no,scrollbars=1\");\n" ;
					}else if(lovField.length<8){
						field+="\"top=20,left=200,height=500,width=900,status=no,toolbar=no,menubar=no,location=no,scrollbars=1\");\n" ;
					}else{
						field+="\"top=20,left=200,height=500,width=650,status=no,toolbar=no,menubar=no,location=no,scrollbars=1\");\n" ;
					}
				}
				field+="lovwin.focus();";
			field += "}";
			field += "//-->\n";
			field += "</script>\n";
			//Add by John 2014/02/14
			if(!"".equals(mode) && "H".equals(mode.split(",")[displayMode])){
				field += " <span style=\"display:none\"> ";
			}
			
			field += "<input type='button' value='...' id='LOVBTN_" + lovField[0] + "' onclick='javascript:popUpLOV_" + (this.id.equals("")?lovField[0]:id[0]) + "();'";
			
			//modify by John 2012.10.15
			if(!"".equals(mode) && "F".equals(mode.split(",")[displayMode])){
				field += " disabled ";
			}
			field += "/>\n";
			//Add by John 2014/02/14
			if(!"".equals(mode) && "H".equals(mode.split(",")[displayMode])){
				field += " </span> ";
			}
			//this.closeConn();
		}catch(Exception e){
			System.out.println("Exception showField()" + e);
		}
		return field;
	}
	
	/**
	 * 產生分頁瀏覽器Navigator
	 * @param filePath          JSP檔名，要產生Navigator的JSP網頁
	 * @param totalRowCount     資料總筆數
	 * @param curPage           使用者點選的頁碼
	 * @param rsPerPage         每頁要顯示的資料筆數
	 * @param pageLinkPerPage   每頁顯示幾個頁面連結
	 * @param cond              傳入條件字串
	 * @return HTML 字串
	 */
	public String navigator(String filePath, int totalRowCount, int curPage,
			int rsPerPage, int pageLinkPerPage, String cond) {

		StringBuffer pageInfo = new StringBuffer();

		int totalPageCount = 0; //資料總頁數
		int prePage = 0; //
		double lastPage = 0;

		//求出最後一個頁碼
		lastPage=Math.floor(totalRowCount / rsPerPage); 
		if ((lastPage * rsPerPage) < totalRowCount){
			lastPage =lastPage+1;
		}
		
		//if (lastPage!=1){
			pageInfo.append("<table cellSpacing=0 cellPadding=0 width=100% border=0><tr><td class='navigator' height=20 align=left>");
			pageInfo.append("共&nbsp;" + totalRowCount + "&nbsp;筆資料&nbsp;&nbsp;&nbsp;&nbsp;");
		
			if (totalRowCount>0){
				pageInfo.append("目前在第&nbsp;" + curPage + "&nbsp;頁");
			}
			if (lastPage!=0){
				pageInfo.append("&nbsp;&nbsp;共&nbsp;" + (int)lastPage + "&nbsp;頁");
			}
			pageInfo.append("</td><td class='navigator' align=right>");
			
			String button_style ="style=\"border:2px dotted #808080;color:#0000FF;background-color: '#C0C0C0';cursor:hand;text-align:center;margin:0;\" ";
			
			if (curPage!=1){
			//	pageInfo.append("<a title='第一頁' id='firstpage' href=\"" + filePath + "?page=1" + cond + "\">第一頁</a>");
				pageInfo.append("<input type='button' "+ button_style +" id='nextpage' value='第一頁' onclick=\"window.location.replace('" + filePath + "?page=1"  + cond + "');\">");
			}
			if (curPage>1){
				pageInfo.append("&nbsp;|&nbsp;");
			//	pageInfo.append("<a title='上一頁' id='prevpage' href=\"" + filePath + "?page=" + (int)(curPage-1) + cond + "\"><上一頁</a>");
				pageInfo.append("<input type='button' "+ button_style +" id='nextpage' value='上一頁' onclick=\"window.location.replace('" + filePath + "?page=" + (int)(curPage-1) + cond + "');\">");
			}
			if ((curPage)< lastPage){
				pageInfo.append("&nbsp;|&nbsp;");
				//pageInfo.append("<a title='下一頁' id='nextpage' href=\"" + filePath + "?page=" + (int)(curPage+1) + cond + "\">下一頁></a>");
				
				pageInfo.append("<input type='button'  "+ button_style +" id='nextpage' value='下一頁'  onclick=\"window.location.replace('" + filePath + "?page=" + (int)(curPage+1) + cond + "');\">");
			}
			if (lastPage!=0 && lastPage!=curPage){
				pageInfo.append("&nbsp;|&nbsp;");
				//pageInfo.append("<a title='最終頁' id='lastpage' href=\"" + filePath + "?page=" + (int)lastPage + cond + "\">最終頁</a>");
				pageInfo.append("<input type='button'  "+ button_style +" id='nextpage' value='最終頁' onclick=\"window.location.replace('" + filePath + "?page=" +  (int)lastPage + cond + "');\">");
			}
			pageInfo.append("</td></tr></table>");
		//}
		//將HTML code回傳
		return pageInfo.toString();
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
		this.form = form;
		this.id = id;
		this.lovField = lovField;
		this.leftjoin= leftjoin;
		this.others = others;
		this.value = value;
		this.table = table;
		this.parentId = parentId;
		this.parentField = parentField;
		this.fieldAlias = fieldAlias;
		this.fieldName = fieldName;
	}

	public BA_LOV(){
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
	/**
	 * @return Returns the keywordsField.
	 */
	public String getKeywordsField() {
		return keywordsField;
	}
	/**
	 * @param keywordsField The keywordsField to set.
	 */
	public void setKeywordsField(String keywordsField) {
		this.keywordsField = keywordsField;
	}
	public String getLeftjoin() {
		return leftjoin;
	}
	public void setLeftjoin(String leftjoin) {
		this.leftjoin = leftjoin;
	}
	private String isnull(String str){
		return str==null?"":str;
	}
	public String getKeywordsget() {
		return keywordsget;
	}
	public void setKeywordsget(String keywordsget) {
		try{
			keywordsget=new String(keywordsget.getBytes("utf-8"),"utf-8");
		}catch(Exception E){
		}
		this.keywordsget=keywordsget;
		this.keywords = keywordsget;
	}
}
