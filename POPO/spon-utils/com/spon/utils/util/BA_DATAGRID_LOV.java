package com.spon.utils.util;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.p6spy.engine.spy.P6ResultSet;
import com.p6spy.engine.spy.P6ResultSetMetaData;
import com.p6spy.engine.spy.P6Statement;
import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.actions.SC006A;

public class BA_DATAGRID_LOV extends NewDispatchAction{
	
	public ActionForward Lov_select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		response.addHeader("Content-Type", "text/xml;charset=utf-8");
		
		String sql=request.getParameter("sql").replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
		String sqlother=request.getParameter("sqlother").replaceAll("\\<\\~\\>","\'").replaceAll("\\<\\^\\>","%");
		String cfield_name=request.getParameter("cfield_name");
		String strkeyCmd=request.getParameter("sqlkeyCmd");
		
		String [] cfield_name_Arr=request.getParameter("cfield_name").split("\\{;\\}");
		
		String [] lovfield_id_Arr=request.getParameter("lovfield_id").split("\\{;\\}");
		String [] lovfield_name_Arr=request.getParameter("lovfield_name").split("\\{;\\}");
		
		String [] sqlkeyid=request.getParameter("sqlkeyid").split("\\{;\\}");
		String [] strOption="大於-小於-等於-大於等於-小於等於-相似於-介於".split("-");
		
		String [] sqlkeyCmd = null;
		if (strkeyCmd.trim().equals("")){
			sqlkeyCmd =(String[])sqlkeyid.clone();
			for(int i =0;i<sqlkeyCmd.length;i++){
				sqlkeyCmd[i] = "5"; //預設為相似於
			}
		}else{
			sqlkeyCmd=request.getParameter("sqlkeyCmd").split("\\{;\\}");
		}
		String [] dg_keyvalue_cname=request.getParameter("dg_keyvalue_cname").split("\\{;\\}");
		String [] dg_keyvalue_cname2 = null;
		if (request.getParameter("dg_keyvalue_cname2").trim().equals("")){
			dg_keyvalue_cname2=request.getParameter("dg_keyvalue_cname").split("\\{;\\}");
		}else{
			dg_keyvalue_cname2=request.getParameter("dg_keyvalue_cname2").split("\\{;\\}");	
		}
		
		
		
		int pagenum=Integer.parseInt(request.getParameter("pagenum"));
		String rowindex=request.getParameter("rowindex");
		int pagesize=0;
		SC006A sc006a=new SC006A();
		Statement stmt = null;
		ResultSet rs = null;
		
		StringBuffer sb = new StringBuffer();
		String strTmp = "";
		
		BA_TOOLS ba = BA_TOOLS.getInstance();
		Connection conn = null;
		
		sb.append("<table style=\"width:100%;caption-side:0 \" border=\"0\" background=\"config/wintitle.gif\" ><tr><td><span  style=\"color: #FFFFFF;font-size:16px;   font-weight      : bold; \">代碼選擇</span></TD><TD align=\"right\"><img onMouseOver=\"this.style.cursor='hand';\" onclick=\"LovLeave();\" align=\"center\" src=\"config/crit_32.gif\" width=\"20\" height=\"20\"></tr></table>");
		try {
			conn = ba.getConnection();
			//取得每頁幾筆
			pagesize=Integer.parseInt(sc006a.getSysParam(conn, form, request,"BROW_ROWSPERPAGE"));
			
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
//			sql語法串上條件值
			if(!sqlkeyid[0].equals(""))
			{	
				for (int i = 0 ; i < sqlkeyid.length ; i++) {
					//0大於1小於2等於3大於等於4小於等於5相似於6介於
					switch(Integer.parseInt(sqlkeyCmd[i])){
						case 0: //大於
							sql += " and " + sqlkeyid[i] + " > '" + dg_keyvalue_cname[i].trim() + "'";
							break;
						case 1:	//小於
							sql += " and " + sqlkeyid[i] + " < '" + dg_keyvalue_cname[i].trim() + "'";
							break;
						case 2:	//等於
							sql += " and " + sqlkeyid[i] + " = '" + dg_keyvalue_cname[i].trim() + "'";
							break;
						case 3:	//大於等於
							sql += " and " + sqlkeyid[i] + " >= '" + dg_keyvalue_cname[i].trim() + "'";
							break;
						case 4:	//小於等於
							sql += " and " + sqlkeyid[i] + " <= '" + dg_keyvalue_cname[i].trim() + "'";
							break;
						case 5:	//相似於
							sql += " and " + sqlkeyid[i] + " like '%" + dg_keyvalue_cname[i].trim() + "%'";
							break;
						case 6:	//介於
							if (!dg_keyvalue_cname[i].trim().equals("") && !dg_keyvalue_cname2[i].trim().equals("")){
								sql += " and " + sqlkeyid[i] + " >= '" + dg_keyvalue_cname[i].trim() + "'";
								sql += " and " + sqlkeyid[i] + " <= '" + dg_keyvalue_cname2[i].trim() + "'";
							}
							break;
					}
					
				}
			}
			sql+=sqlother;
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsm = rs.getMetaData();
			
/////////////////////////////////////////////////////////加入查詢條件
			//加入script
			
			//加入畫面
			sb.append("<table width=100% style='background-color:#3366cc;border:1px;font-size:12pt'>");
			sb.append("<tr style='background-color:#C6CAE4'>");
			sb.append("<td style='width:150px'>查詢欄位</td>");
			sb.append("<td style='width:80px'>條件式</td>");
			sb.append("<td style='width:150px'>條件值 I</td>");
			sb.append("<td style='width:150px'>條件值 II</td>");
			sb.append("</tr>");
			for(int s = 0;s<3;s++){
				String str= "";
				String DisplayS = "";
				String DisplayN = "";
				if (s<sqlkeyid.length){
					str = "inline";
					if (rsm.getColumnClassName(1).equals("java.lang.String")){
						DisplayS = "inline";
						DisplayN = "none";
					}else{
						DisplayS = "none";
						DisplayN = "inline";
					}
				}else{
					str = "none";
					DisplayS = "none";
					DisplayN = "none";
				}
				sb.append("<tr style='background-color:#E8ECF5;'>");
				sb.append("<td><select style='width:100%;display:" + str + "' name='LOVCol" + s + "-0' id='LOVCol" + s + "-0' >");
				for (int i = 0; i < lovfield_name_Arr.length ; i++){
					String strSelect = "";
					if (s < sqlkeyid.length ){
						if (lovfield_id_Arr[i].equals(sqlkeyid[s])){
							strSelect = " selected ";
						}
					}
					if (rsm.getColumnClassName(i+1).equals("java.lang.String")){
						sb.append("<option value='S" + lovfield_id_Arr[i]+ "' " + strSelect + ">" + lovfield_name_Arr[i] + "</option>");
					}else{
						sb.append("<option value='N" + lovfield_id_Arr[i]+ "' " + strSelect + ">" + lovfield_name_Arr[i] + "</option>");
					}
				}
				sb.append("</select></td>");
				sb.append("<td>");
				sb.append("<select onchange=\"showLovOption(this," + s + ")\" style='width:100%;display:" + DisplayS + "' name='LOVSelS" + s + "' id='LOVSelS" + s + "' >");
				for (int i = 0;i <strOption.length;i++){
					String strSelect = "";
					if (s < sqlkeyCmd.length ){
						if (sqlkeyCmd[s].equals((i+""))){
							strSelect=" selected ";
						}
					}
					sb.append("<option value='" + i + "' " + strSelect + ">" + strOption[i] + "</option>");	
				}
				sb.append("</select>");
				sb.append("<select style='width:100%;display:" + DisplayN + "' name='LOVSelN" + s + "' id='LOVSelN" + s + "' >");
				for (int i = 0;i <strOption.length -2 ;i++){
					String strSelect = "";
					if (s < sqlkeyCmd.length ){
						if (sqlkeyCmd[s].equals((i+""))){
							strSelect=" selected ";
						}
					}
					sb.append("<option value='" + i + "' " + strSelect + ">" + strOption[i] + "</option>");	
				}
				sb.append("</select>");
				sb.append("</td>");
				if (s < dg_keyvalue_cname.length ){
					sb.append("<td><input type=text style='width:100%;display:" + str + "' name='LOVCol" + s + "-1' id='LOVCol" + s + "-1' value='" + dg_keyvalue_cname[s].trim() + "'></td>");
					if (s < sqlkeyCmd.length){
						if (sqlkeyCmd[s].equals("6")){
							sb.append("<td><input type=text style='width:100%;display:inline' name='LOVCol" + s + "-2' id='LOVCol" + s + "-2' value='" + dg_keyvalue_cname2[s].trim() + "'></td>");
						}else{
							sb.append("<td><input type=text style='width:100%;display:none' name='LOVCol" + s + "-2' id='LOVCol" + s + "-2' value='" + dg_keyvalue_cname2[s].trim() + "'></td>");
						}
					}else{
						sb.append("<td><input type=text style='width:100%;display:none' name='LOVCol" + s + "-2' id='LOVCol" + s + "-2' value='" + dg_keyvalue_cname2[s].trim() + "'></td>");
					}
				}else{
					sb.append("<td><input type=text style='width:100%;display:" + str + "' name='LOVCol" + s + "-1' id='LOVCol" + s + "-1' value=''></td>");
					sb.append("<td><input type=text style='width:100%;display:none' name='LOVCol" + s + "-2' id='LOVCol" + s + "-2' value=''></td>");
				}
				
				sb.append("</tr>");
			}
			sb.append("<tr  style='background-color:#E8ECF5'><td colspan=4 align=center>");
			sb.append("<input type=button value='新增條件' onclick='Dbgrid_LOV_ADD_Query()'>");
			sb.append("<input type=button value='移除條件' onclick='Dbgrid_LOV_RM_Query()'>");
			sb.append("<input type=button value='查　　詢' onclick='LovGridPerPage.value=1;LovGataGridCancel.onclick();' >");
			sb.append("<input type=button onclick=\"LovLeave();\" value='離　　開'>");
			sb.append("</td></tr>");
			sb.append("</table>");
/////////////////////////////////////////////////////////加入查詢條件			
			
			int RecordCount=0,PageCount=0;
			if (rs.last()) {
				// 取得總比數
				RecordCount = rs.getRow();
				// 取總頁數
				PageCount = new Double((Math.ceil(RecordCount / (float) pagesize))).intValue();
				rs.beforeFirst();
			}
			if (pagenum > PageCount) pagenum=PageCount;
			int start = (pagenum - 1) * pagesize;
			if (start == 0) // 第一頁
				rs.beforeFirst();
			else
				// 不是第一頁
				rs.absolute(start);
			
			RecordCount=pagesize;
			
//			 串上分頁資料
			sb.append("<span id='SpanPage' style='width:400px;font-size:14px'>");
			if (pagenum != 0 ) {
				// 第一頁
				if (pagenum != 1) {
					sb.append("<a style='cursor:hand' onclick='LovGridPerPage.value=1;LovGataGridCancel.onclick();' >第一頁</a>　&nbsp;\n");
				} else {
					sb.append("<a   >　　　</a>　&nbsp;\n");
				}
				if (pagenum > 1) {
					// 上一頁
					sb.append("<a style='cursor:hand' onclick='LovGridPerPage.value=" + (pagenum - 1) + ";LovGataGridCancel.onclick();' >上一頁</a>　&nbsp;\n");
				} else {
					sb.append("<a   >　　　</a>　&nbsp;\n");
				}
				if (pagenum < PageCount) {
					// 下一頁
					sb.append("<a style='cursor:hand' onclick='LovGridPerPage.value=" + (pagenum + 1) + ";LovGataGridCancel.onclick();' >下一頁</a>　&nbsp;\n");
				} else {
					sb.append("<a   >　　　</a>　&nbsp;\n");
				}
				// 最後一頁
				if (pagenum != PageCount) {
					sb.append("<a style='cursor:hand' onclick='LovGridPerPage.value=" + PageCount + ";LovGataGridCancel.onclick();' >最後一頁</a>\n");
				} else {
					sb.append("<a   >　　　　</a>　&nbsp;\n");
				}
			}
			sb.append("&nbsp;目前頁次：" + pagenum + "&nbsp;/&nbsp;" + PageCount + "&nbsp;\n");

			sb.append("<input type=button id=LovGataGridCancel style='display:none' value='' ");
			sb.append("onclick=\"Lov_Page_Qry('");
			//傳入sql語法
			sb.append((String)request.getParameter("sql"));
			sb.append("','");
			//傳入sql語法
			sb.append((String)request.getParameter("sqlother"));
			sb.append("','");
			//傳入
			sb.append((String)request.getParameter("cfield_name"));
			sb.append("','");
			//傳入			
			sb.append((String)request.getParameter("lovfield_id"));
			sb.append("','");
			sb.append((String)request.getParameter("lovfield_name"));
			sb.append("','");
//				傳入
			sb.append("BA_DATAGRID_LOV_DIV");
			sb.append("',LovGridPerPage.value," + rowindex + ")\">");
//				// 寫入目前頁次
			sb.append("<input id=LovGridPerPage type=text style='display:none' value='" + pagenum + "'>\n");
			
			sb.append("&nbsp;</span>　　　　&nbsp;\n");
			sb.append("<table width=\"98%\" id=LOVTableDetail style='background-color:#3366cc;border:1px;font-size:12pt' CELLPADDING=0 CELLSPACING=1 align=center>");
			sb.append("<tr>");
			sb.append("<td align=center style='width:40px;font-size:16px;text-overflow:clip;background-color:#C6CAE4;'>回傳</td>");
			for (int i = 0; i <lovfield_name_Arr.length; i++) {
				sb.append("<td align=center width=200 style='font-size:16px;text-overflow:clip;background-color:#C6CAE4;'>" + lovfield_name_Arr[i] + "</td>");
			}
			sb.append("</tr>");
			
			for (int s = 0; s < RecordCount; s++) {
				if (rs.next()){
					sb.append("<tr bgcolor='#FFFFFF' onMouseOver=\"this.style.backgroundColor='#91AAD9';this.style.cursor='hand'; \" onMouseOut=\"this.style.backgroundColor='#FFFFFF'\" >");
					sb.append("<td align=left style='font-size:16px;text-overflow:clip;background-color:#ffffff;' >");
					strTmp = "";
					for (int i=0 ;i<cfield_name_Arr.length;i++){
						int k=1;
						for (int j = 0; j < lovfield_name_Arr.length; j++) {
							if(cfield_name_Arr[i].equals(lovfield_name_Arr[j]))
								k=j+1;
						}
						strTmp += rs.getString(rsm.getColumnName(k)) + "{;}";
						
					}
					
					//選取按鈕
					sb.append("<input type=button value='選取' onclick=\"DBLOVReturn('"+ cfield_name + "','" + strTmp + "'," + rowindex + ");\">");
					sb.append("</td>");
					for (int i = 1; i <= rsm.getColumnCount(); i++) {
						sb.append("<td align=left  style='font-size:16px;text-overflow:clip;' nowrap='nowrap' onclick=\"DBLOVReturn('"+ cfield_name + "','" + strTmp + "'," + rowindex + ");\" >" + rs.getString(i) + "</td>");
					}
					sb.append("</tr>");
				}
				}
				sb.append("</table>");
			try {
				
				sendtoclient(sb.toString(),response);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private  void sendtoclient(String msg,HttpServletResponse response) throws IOException
	{
		byte [] in =msg.getBytes("UTF-8");
		for (int i = 0; i < in.length; i++) {
			response.getOutputStream().write(in[i]);
		} 
	}
	
}
