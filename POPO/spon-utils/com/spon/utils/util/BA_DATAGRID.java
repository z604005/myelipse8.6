package com.spon.utils.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.BA_Detail_Attr;
import com.spon.utils.struts.form.BA_Detail_Head_Attr;

public class BA_DATAGRID {
	//存放表頭key的值
	private String [] keyvalue_Arr;
	
	private static BA_DATAGRID ba_datagrid;

	public StringBuffer getDATAGRID(Connection conn, BA_Detail_Head_Attr D_H_A) {
		
 		StringBuffer sb = new StringBuffer(); // 存放所有的資料
		StringBuffer sbSet = new StringBuffer(); // 存放型態資料
		StringBuffer sbTitle = new StringBuffer(); // 存放標題資料
		StringBuffer sbContent = new StringBuffer(); // 存放內容資料
		String DISP_TYPE = ""; // 型態
		String TH_ID = ""; // 欄位名稱
		String TH_NAME = ""; // 標題
		int WIDTH = 0; // 寬度px
		int HEIGHT = 0; // 高度px
		int ColCount = -1; // 記錄目前的欄位數
		int RowCount = 0; // 記錄筆數
		int DefRowCount = 0; // 紀錄新增時要顯示幾列
		int PageCount = 0; // 總頁數
		int RecordCount = 0; // 資料總比數
		
		String SQL=""; //Lov用
		String CFIELD_NAME=""; //Lov用
		String SQLKEYID=""; //Lov用
		String DG_KEYVALUE_CNAME=""; //Lov用
		String LOVFIELD_NAME=""; //Lov用
		String LOVFIELD_ID=""; //Lov用
		
		

		// 取得sql語法
		String sql = D_H_A.getSQL();
		// 取得欄位資料的集合
		List D_H_C = D_H_A.getBA_Detail_attr();
		HEIGHT = D_H_A.getHEIGHT();

		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			//如果有keyvalue 就把條件加上去
			if(keyvalue_Arr!=null)
			if(keyvalue_Arr.length>0)
			{
				for (int i = 0; i < keyvalue_Arr.length; i++) {
					sql+=" AND "+(D_H_A.getKEYID().split("\\{;\\}"))[i] +"='"+keyvalue_Arr[i]+"' ";
				}
				
			}
			
			sql+=D_H_A.getSQLOTHER();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData rsm = rs.getMetaData();
			BA_Detail_Attr D_A = new BA_Detail_Attr();

			// 丟型態和標題
			sbSet.append("<tr style='display:none'>\n");
			sbTitle.append("<thead>\n");
			sbTitle.append("<tr style='display:inline' style='height:" + HEIGHT + "px'>\n");

			// 設定刪除欄
			if (D_H_A.isDELETE_RIGHT() == true && !D_H_A.getMODE().equals("new")) {
				ColCount++;
				sbSet.append("<td  style='dispaly:inline;width:40px;'>" + "BBD" + "40px</td>\n");
				sbTitle.append("<td align=center style='display:inline;width:40px;background-color:#99ccff;font-size:" + D_H_A.getFONTSIZE() + "px;' >刪除</td>\n");
			}
			// 設定還原欄
			ColCount++;
			sbSet.append("<td  style='dispaly:inline;width:40px;'>" + "BBC" + "40px</td>\n");
			sbTitle.append("<td align=center style='display:inline;width:40px;background-color:#99ccff;font-size:" + D_H_A.getFONTSIZE() + "px;' >還原</td>\n");

			for (int i = 1; i <= rsm.getColumnCount(); i++) {
				// 處理欄位資料
				ColCount++;
				// 取得欄位資訊
				TH_ID = rsm.getColumnName(i);
				TH_NAME = "";
				DISP_TYPE = "";
				WIDTH = 0;
				for (int j = 0; j < D_H_C.size(); j++) {
					D_A = (BA_Detail_Attr) D_H_C.get(j);
					if (D_A.getTH_ID().equals(TH_ID)) { // 如果是這個欄位的資料
						// 設定型態
						DISP_TYPE = D_A.getDISP_TYPE();
						
						if(!D_H_A.isUPDATE_RIGHT() & DISP_TYPE.charAt(2)=='E')
						{
							DISP_TYPE=DISP_TYPE.substring(0,2)+"R";
						}
						
						// 設定標題
						TH_NAME = D_A.getTH_NAME();
						// 設定寬度
						WIDTH = D_A.getWIDTH();
						// 離開迴圈
						j = D_H_C.size();
					}
				}
				if (TH_NAME.equals(""))
					TH_NAME = TH_ID;
				if (DISP_TYPE.equals(""))
					DISP_TYPE = "TNE";
				if (WIDTH == 0)
					WIDTH = 100;
				// 設定型態列
				sbSet.append("<td  style='cursor:hand;");
				if (DISP_TYPE.charAt(2) == 'H') {
					sbSet.append("dispaly:none;");
				} else {
					sbSet.append("dispaly:inline;");
				}
				sbSet.append("width:" + WIDTH + "px;'>");
				sbSet.append(DISP_TYPE + WIDTH + "px</td>\n");

				// 設定標題列

				sbTitle.append("<td align=center ");
				sbTitle.append("onclick='setColWidth(" + ColCount + ");'");
				if (DISP_TYPE.charAt(2) == 'H') {
					sbTitle.append("style='cursor:hand;display:none;");
				} else if (DISP_TYPE.charAt(1) == 'K') {
					sbTitle.append("style='FONT-WEIGHT: bold;cursor:hand;display:inline;font-size:" + D_H_A.getFONTSIZE() + "px;");
					TH_NAME = "◎" + TH_NAME;
				} else {
					sbTitle.append("style='cursor:hand;display:inline;font-size:" + D_H_A.getFONTSIZE() + "px;");
				}
				sbTitle.append("width:" + WIDTH + ";text-overflow:clip;");
				sbTitle.append("background-color:#99ccff;'>" + TH_NAME);
				if (D_A.isRequired())
					sbTitle.append("<img src='config/ast.gif' />");
				sbTitle.append("</td>\n");

			}
			sbSet.append("</tr>\n");
			sbTitle.append("</tr>\n");
			sbTitle.append("</thead>\n");

			if (D_H_A.getMODE().equals("new")) {
				// 新增
				DefRowCount = D_H_A.getRS_Per_Page();
				if (DefRowCount == 0)
					DefRowCount = 10;
				    RowCount = 2;
				for (int intNum = 1; intNum <= DefRowCount; intNum++) {
					ColCount = -1;
					sbContent.append("<tr  style='height:" + HEIGHT + "px;");
					sbContent.append("' bgcolor='#FFFFFF'>\n");
					// 設定還原欄
					ColCount++;
					sbContent.append("<td style='display:inline;background-color:#FFFFFF'>");
					sbContent.append("<input id=column" + RowCount + "-" + ColCount + " ");
					sbContent.append(" name=column" + RowCount + "-" + ColCount + " ");
					sbContent.append(" onclick=\"clsGridRowValue(" + RowCount + ",'" + D_H_A.getPROGRAM() + "','" + D_H_A.getMODE() + "')\" ");
					sbContent.append(" type=button ");
					sbContent.append("style='width:40px;");
					sbContent.append("height:" + HEIGHT + "px;");
					sbContent.append("border:none;' value='還原'></td>\n");
					// 丟資料
					for (int i = 1; i <= rsm.getColumnCount(); i++) {
						// 處理欄位資料
						ColCount++;
						// 取得欄位資訊
						TH_ID = rsm.getColumnName(i);
						TH_NAME = "";
						DISP_TYPE = "";
						WIDTH = 0;
						SQL=""; //Lov用
						CFIELD_NAME=""; //Lov用
						SQLKEYID=""; //Lov用
						DG_KEYVALUE_CNAME=""; //Lov用
						LOVFIELD_ID=""; //Lov用
						LOVFIELD_NAME=""; //Lov用
						for (int j = 0; j < D_H_C.size(); j++) {
							D_A = (BA_Detail_Attr) D_H_C.get(j);
							if (D_A.getTH_ID().equals(TH_ID)) { // 如果是這個欄位的資料
								// 設定型態
								DISP_TYPE = D_A.getDISP_TYPE();
								// 設定標題
								TH_NAME = D_A.getTH_NAME();
								// 設定寬度
								WIDTH = D_A.getWIDTH();
								// 離開迴圈
								j = D_H_C.size();
//								取得相關資料
								SQL= D_A.getSQL(); 
								CFIELD_NAME=D_A.getCFIELD_NAME(); 
								SQLKEYID=D_A.getSQLKEYID();
								DG_KEYVALUE_CNAME=D_A.getDG_KEYVALUE_CNAME();
								LOVFIELD_ID=D_A.getLOVFIELD_ID();
								LOVFIELD_NAME=D_A.getLOVFIELD_NAME();
							}
						}
						
						// 丟資料
						
						if (DISP_TYPE.charAt(2) == 'H') { 
							// 隱藏
							sbContent.append("<td style='display:none'><input type=text");	
							sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
							sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
							sbContent.append(" style='width:" + WIDTH + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
							sbContent.append(" height:" + HEIGHT + "px;border:none;' " );
							sbContent.append(" value=\"" + D_A.getVALUE().replaceAll("\"", "&quot;") + "\" >");	
							
						} else if (DISP_TYPE.charAt(2) == 'R' ) { 
							// 唯讀
							sbContent.append("<td style='display:inline;'><input readonly type=text");
							sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
							sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
							sbContent.append(" style='width:" + WIDTH + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
							sbContent.append(" background-color:#E4E4E4;");
							sbContent.append(" height:" + HEIGHT + "px;border:none;'");
							sbContent.append(" value=\"" + D_A.getVALUE().replaceAll("\"", "&quot;") + "\" >");
						} else if (DISP_TYPE.charAt(0) == 'S') {
							//SelectBox
							sbContent.append("<td style='display:inline'><select  ");
							sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
							sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
							sbContent.append(" style='width:" + WIDTH + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
							sbContent.append(" height:" + HEIGHT + "px;border:none;'");
							sbContent.append(" >");
							// 組options
							sbContent.append(getOptios(D_A.getOPTIONS(), D_A.getVALUE()));
							sbContent.append("</select>");
						} else if (DISP_TYPE.charAt(0) == 'L') {
							//DBGRIDLOV
							sbContent.append("<td style='display:inline'><input type=text");	
							sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
							sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
							sbContent.append(" style='width:" + (WIDTH-20) + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
							sbContent.append(" height:" + HEIGHT + "px;border:none;' maxlength=\""+D_A.getMAXLENGTH()+"\"  ");
							sbContent.append(" value=\"" + D_A.getVALUE().replaceAll("\"", "&quot;") + "\" >");
							sbContent.append("<input type=button value='...' ");
							sbContent.append("onclick=\"Lov_DO_select('");
							//傳入sql語法
							sbContent.append(SQL);
							sbContent.append("','");
							//傳入sqlother語法
							sbContent.append(D_A.getSQLOTHER());
							sbContent.append("','");
							//傳入
							sbContent.append(CFIELD_NAME);
							sbContent.append("','");
//							傳入
							sbContent.append(SQLKEYID);
							sbContent.append("','");
//							傳入
							sbContent.append(DG_KEYVALUE_CNAME);
							sbContent.append("','");
//							傳入							
							sbContent.append(LOVFIELD_ID);
							sbContent.append("','");
//							傳入							
							sbContent.append(LOVFIELD_NAME);
							sbContent.append("','");
//							傳入
							sbContent.append("BA_DATAGRID_LOV_DIV");
							sbContent.append("','1'," + RowCount + ")\"  style='width:20px;height:" + HEIGHT + "px'>");
						} else {
							//InputBox
							sbContent.append("<td style='display:inline'><input type=text");	
							sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
							sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
							sbContent.append(" style='width:" + WIDTH + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
							sbContent.append(" height:" + HEIGHT + "px;border:none;' maxlength=\""+D_A.getMAXLENGTH()+"\" " );
							sbContent.append(" value=\"" + D_A.getVALUE().replaceAll("\"", "&quot;") + "\" >");	
						}
						sbContent.append("</td>\n");
						
					}
					sbContent.append("</tr>\n");
					RowCount++;
				}
			} else {
				
				
				// 修改
				if (rs.last()) {
					// 取得總比數
					RecordCount = rs.getRow();
					// 取總頁數
					PageCount = new Double((Math.ceil(RecordCount / (float) D_H_A.getRS_Per_Page()))).intValue();
					rs.beforeFirst();
				}

				if (D_H_A.getPAGENUM() > 0) { // 分頁
					if (D_H_A.getPAGENUM() > PageCount & PageCount!=0) D_H_A.setPAGENUM(PageCount);
					int start = (D_H_A.getPAGENUM() - 1) * D_H_A.getRS_Per_Page();
					if (start == 0) // 第一頁
						rs.beforeFirst();
					else
						// 不是第一頁
						rs.absolute(start);
					RecordCount = D_H_A.getRS_Per_Page();
					
				} else {

				}
				RowCount = 2;
				for (int s = 0; s < RecordCount; s++) {
					// 丟內容
					if (rs.next()) {
						ColCount = -1;
						sbContent.append("<tr  style='height:" + HEIGHT + "px;' bgcolor='#FFFFFF'>\n");
						// 設定刪除欄
						if (D_H_A.isDELETE_RIGHT() == true) {
							ColCount++;
							sbContent.append("<td style='display:inline;background-color:#FFFFFF'>");
							sbContent.append("<input id=column" + RowCount + "-" + ColCount + ";");
							sbContent.append(" name=column" + RowCount + "-" + ColCount + " ");
							sbContent.append(" onchange='chgGridRow(" + RowCount + ");' onclick='chgGridRow(" + RowCount + ");' type=checkbox ");
							sbContent.append("style='width:40px;");
							sbContent.append("height:" + HEIGHT + "px;");
							sbContent.append("border:none;' value='Y'></td>\n");
						}
						// 設定還原欄
						ColCount++;
						sbContent.append("<td style='display:inline;background-color:#FFFFFF'>");
						sbContent.append("<input id=column" + RowCount + "-" + ColCount + " ");
						sbContent.append(" name=column" + RowCount + "-" + ColCount + " ");
						sbContent.append(" onclick=\"clsGridRowValue(" + RowCount + ",'" + D_H_A.getPROGRAM() + "','" + D_H_A.getMODE() + "')\" ");
						sbContent.append(" type=button ");
						sbContent.append("style='width:40px;");
						sbContent.append("height:" + HEIGHT + "px;");
						sbContent.append("border:none;' value='還原'></td>\n");
						// 丟資料
						for (int i = 1; i <= rsm.getColumnCount(); i++) {
							// 處理欄位資料
							ColCount++;
							// 取得欄位資訊
							TH_ID = rsm.getColumnName(i);
							TH_NAME = "";
							DISP_TYPE = "";
							WIDTH = 0;
							SQL=""; //Lov用
							CFIELD_NAME=""; //Lov用
							SQLKEYID=""; //Lov用
							DG_KEYVALUE_CNAME=""; //Lov用
							LOVFIELD_NAME=""; //Lov用
							LOVFIELD_ID=""; //Lov用
							for (int j = 0; j < D_H_C.size(); j++) {
								D_A = (BA_Detail_Attr) D_H_C.get(j);
								if (D_A.getTH_ID().equals(TH_ID)) { // 如果是這個欄位的資料
									// 設定型態
									DISP_TYPE = D_A.getDISP_TYPE();
									
									if(!D_H_A.isUPDATE_RIGHT() & DISP_TYPE.charAt(2)=='E')
									{
										DISP_TYPE=DISP_TYPE.substring(0,2)+"R";
									}
									
									// 設定標題
									TH_NAME = D_A.getTH_NAME();
									// 設定寬度
									WIDTH = D_A.getWIDTH();
									// 離開迴圈
									j = D_H_C.size();
									
									//取得相關資料
									SQL= D_A.getSQL(); 
									CFIELD_NAME=D_A.getCFIELD_NAME(); 
									SQLKEYID=D_A.getSQLKEYID();
									DG_KEYVALUE_CNAME=D_A.getDG_KEYVALUE_CNAME();
									LOVFIELD_ID=D_A.getLOVFIELD_ID();
									LOVFIELD_NAME=D_A.getLOVFIELD_NAME();
									
									
								}
							}
							 
							if (DISP_TYPE.charAt(2) == 'H') { 
								// 隱藏
								sbContent.append("<td style='display:none'><input type=text");	
								sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
								sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
								sbContent.append(" style='width:" + WIDTH + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
								sbContent.append(" height:" + HEIGHT + "px;border:none;'");
								sbContent.append(" value=\"" + (rs.getString(i)==null?"":rs.getString(i)+ "").replaceAll("\"", "&quot;") + "\" >");	
								
							} else if (DISP_TYPE.charAt(2) == 'R' ) { 
								// 唯讀
								sbContent.append("<td style='display:inline;'><input readonly type=text");
								sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
								sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
								sbContent.append(" style='width:" + WIDTH + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
								sbContent.append(" background-color:#E4E4E4;");
								sbContent.append(" height:" + HEIGHT + "px;border:none;'");
								sbContent.append(" value=\"" + (rs.getString(i)==null?"":rs.getString(i) + "").replaceAll("\"", "&quot;") + "\" >");
							} else if (DISP_TYPE.charAt(0) == 'S') {
								//SelectBox
								sbContent.append("<td style='display:inline'><select  ");
								sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
								sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
								sbContent.append(" style='width:" + WIDTH + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
								sbContent.append(" height:" + HEIGHT + "px;border:none;'");
								sbContent.append(" >");
								// 組options
								sbContent.append(getOptios(D_A.getOPTIONS(), rs.getString(i)==null?"":rs.getString(i)));
								sbContent.append("</select>");
							} else if (DISP_TYPE.charAt(0) == 'L') {
								//DBGRIDLOV
								sbContent.append("<td style='display:inline'><input type=text");	
								sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
								sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
								sbContent.append(" style='width:" + (WIDTH-20) + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
								sbContent.append(" height:" + HEIGHT + "px;border:none;' maxlength=\""+D_A.getMAXLENGTH()+"\" ");
								sbContent.append(" value=\"" + (rs.getString(i)==null?"":rs.getString(i) + "").replaceAll("\"", "&quot;") + "\" >");
								sbContent.append("<input type=button value='...' ");
								sbContent.append("onclick=\"Lov_DO_select('");
								//傳入sql語法
								sbContent.append(SQL);
								sbContent.append("','");
//								傳入sqlother語法
								sbContent.append(D_A.getSQLOTHER());
								sbContent.append("','");
								//傳入
								sbContent.append(CFIELD_NAME);
								sbContent.append("','");
//								傳入
								sbContent.append(SQLKEYID);
								sbContent.append("','");
//								傳入
								sbContent.append(DG_KEYVALUE_CNAME);
								sbContent.append("','");
//								傳入
								sbContent.append(LOVFIELD_ID);
								sbContent.append("','");
//								傳入
								sbContent.append(LOVFIELD_NAME);
								sbContent.append("','");
//								傳入
								sbContent.append("BA_DATAGRID_LOV_DIV");
								sbContent.append("','1'," + RowCount + ")\"  style='width:20px;height:" + HEIGHT + "px'>");
								
							} else {
								//InputBox
								sbContent.append("<td style='display:inline'><input type=text");	
								sbContent.append(" id=column" + RowCount + "-" + ColCount + " name=column" + RowCount + "-" + ColCount + " ");
								sbContent.append(" onchange='chgGridRow(" + RowCount + ");'  ");
								sbContent.append(" style='width:" + WIDTH + "px;font-size:" + D_H_A.getFONTSIZE() + "px;");
								sbContent.append(" height:" + HEIGHT + "px;border:none;' maxlength=\""+D_A.getMAXLENGTH()+"\"  ");
								sbContent.append(" value=\"" + (rs.getString(i)==null?"":rs.getString(i) + "").replaceAll("\"", "&quot;") + "\" >");	
							}

							sbContent.append("</td>\n");
						}

						sbContent.append("</tr>\n");
						RowCount++;
					} else {
						s = RecordCount;
					}
				}
			}
			// 串上分頁資料
			sb.append("<span id='SpanPage' style='width:400px;font-size:14px'>");
			if (D_H_A.getPAGENUM() != 0 & !D_H_A.getMODE().equals("new")) {
				// 第一頁
				if (D_H_A.getPAGENUM() != 1) {
					sb.append("<a style='cursor:hand' onclick='ToBeSelect(1,0);' >第一頁</a>　&nbsp;\n");
				} else {
					sb.append("<a   >　　　</a>　&nbsp;\n");
				}
				if (D_H_A.getPAGENUM() > 1) {
					// 上一頁
					sb.append("<a style='cursor:hand' onclick='ToBeSelect(GridPerPage.value,-1);' >上一頁</a>　&nbsp;\n");
				} else {
					sb.append("<a   >　　　</a>　&nbsp;\n");
				}
				if (D_H_A.getPAGENUM() < PageCount) {
					// 下一頁
					sb.append("<a style='cursor:hand' onclick='ToBeSelect(GridPerPage.value,1)' >下一頁</a>　&nbsp;\n");
				} else {
					sb.append("<a   >　　　</a>　&nbsp;\n");
				}
				// 最後一頁
				if (D_H_A.getPAGENUM() != PageCount & PageCount!=0) {
					sb.append("<a style='cursor:hand' onclick='ToBeSelect(" + PageCount + ",0);' >最後一頁</a>\n");
				} else {
					sb.append("<a   >　　　　</a>　&nbsp;\n");
				}
			}
			if (!D_H_A.getMODE().equals("new") & D_H_A.getPAGENUM()>0 & PageCount!=0) {
				sb.append("&nbsp;頁次：" + D_H_A.getPAGENUM() + "&nbsp;/&nbsp;" + PageCount + "&nbsp;\n");
			}
			sb.append("&nbsp;</span>　　　　&nbsp;\n");

			// 丟控制按鈕
			if (D_H_A.isINSERT_RIGHT()) {
				if (D_H_A.getMODE().equals("new")) {
					sb.append("<input type=button id=GataGridAdd value='新增' style='display:none'  onclick=\"Detail_select('" + D_H_A.getPROGRAM() + "','DBGRIDDetail','new',GridPerPage.value,'" + D_H_A.getKEYID()
							+ "');\"  >\n");
					sb.append("<input type=button id=GataGridSave value='存檔' style='display:inline' onclick=\"Dbgrid_Detail_save('" + D_H_A.getPROGRAM() + "');\" >\n");
				} else {
					sb.append("<input type=button id=GataGridAdd value='新增' style='display:inline' onclick=\"Detail_select('" + D_H_A.getPROGRAM() + "','DBGRIDDetail','new',GridPerPage.value,'" + D_H_A.getKEYID()
							+ "');\"  >\n");
				}
			}
			
			if (D_H_A.isUPDATE_RIGHT() | D_H_A.isDELETE_RIGHT()) {
				if (!D_H_A.getMODE().equals("new")) 
					sb.append("<input type=button id=GataGridSave value='存檔' style='display:inline' onclick=\"Dbgrid_Detail_save('" + D_H_A.getPROGRAM() + "');\" >\n");
				
			}
			sb.append("<input type=button id=GataGridCancel value='取消' onclick=\"Detail_select('" + D_H_A.getPROGRAM() + "','DBGRIDDetail','',GridPerPage.value,'" + D_H_A.getKEYID() + "');\" >\n");
			
			if(!D_H_A.getKEYID().equals(""))
			{
				sb.append("<input type=button  value='回表頭' onclick=\"Detail_select_Head_Leave();\" >\n");
			}
			
			sb.append("<br>\n");

			sb.append("<div id='DIVLOCKHEAD' style='width:" + D_H_A.getGRID_WIDTH() + "px;height:" + D_H_A.getGRID_HEIGHT() + "px;position:relative;overflow:auto;' >");
			sb.append("<table id=DBTableDetail style='background-color:#3366cc;border:1px;font-size:12pt' CELLPADDING=0 CELLSPACING=1 align=left>");

			sb.append(sbTitle.toString());
			sb.append(sbSet.toString());
			sb.append(sbContent.toString());
			sb.append("</table>");
			sb.append("</div>\n");
			// 寫入共用資訊
			// 寫入目前是修改還是新增
			if (D_H_A.getMODE().equals("new")) {
				// 新增
				sb.append("<input id=GridEditMode type=text style='display:none' value='insert'>\n");
			} else {
				// 修改
				sb.append("<input id=GridEditMode type=text style='display:none' value='update'>\n");
			}
			// 修改用的欄位
			sb.append("<input id=GridEditNowRow type=text style='display:none' value='2'>\n");
			// 寫入目前頁次
			sb.append("<input id=GridPerPage type=text style='display:none' value='" + D_H_A.getPAGENUM() + "'>\n");
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return sb;
	}

	private String getOptios(List options, String value) {
		String remsg = "";
		if(options!=null)
		{
		if (value.equals(""))
			remsg += "<option value=''>請選擇</option>";

		for (int i = 0; i < options.size(); i++) {
			BA_ALLKINDForm baf = null;
			baf = (BA_ALLKINDForm) options.get(i);
			if (value != null) {
				remsg += "<option ";
				if (value.equals(baf.getItem_id()))
					remsg += " SELECTED ";

				remsg += "value='" + baf.getItem_id() + "'>" + baf.getItem_value() + "</option>";
			} else {
				remsg += "<option value='" + baf.getItem_id() + "'>" + baf.getItem_value() + "</option>";
			}

		}
		}
		return remsg;
	}

	public static BA_DATAGRID getInstance() {
		if (ba_datagrid == null)
			ba_datagrid = new BA_DATAGRID();
		else
			ba_datagrid.keyvalue_Arr=null;
		
		return ba_datagrid;
	}

	public BA_Detail_Attr getD_AbyID(List detail_attr, String id) {
		BA_Detail_Attr D_A = null;
		ArrayList D_H_C = (ArrayList) detail_attr;
		for (int i = 0; i < D_H_C.size(); i++) {
			BA_Detail_Attr D_A_A = (BA_Detail_Attr) D_H_C.get(i);
			if (D_A_A.getTH_ID().equals(id)) {
				D_A = (BA_Detail_Attr) D_H_C.get(i);
				break;
			}
		}
		return D_A;
	}

	public String[] getKeyvalue_Arr() {
		return keyvalue_Arr;
	}

	public void setKeyvalue_Arr(String[] keyvalue_Arr) {
		this.keyvalue_Arr = keyvalue_Arr;
	}

}
