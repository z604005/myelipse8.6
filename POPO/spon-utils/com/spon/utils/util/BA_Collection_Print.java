package com.spon.utils.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.sql.Statement;

import java.util.Date;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.UnderlineStyle;
import jxl.write.WritableFont;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.actions.SC006A;
import com.spon.utils.sc.actions.Security;

public class BA_Collection_Print extends NewDispatchAction {

	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		response.addHeader("Content-Type", "text/html;charset=utf-8");
		
		SC006A sc006a = new SC006A();
		DynaActionForm dynaForm = (DynaActionForm) form;
		String id = (String) dynaForm.get("collectionid");
		String[] heads = ((String) dynaForm.get("collectionheads")).split(",");
		String[] propertys =( (String) dynaForm.get("collectionpropertys")).split(",");
		
		
		BA_TOOLS ba = BA_TOOLS.getInstance();
		Connection conn = null;
		
		Statement stmt = null;
		try {
			if (!id.equals("")) {
				conn = ba.getConnection();
				stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				String sql = new Security().decode(id);

				ResultSet rs = stmt.executeQuery(sql);
				rs.last();
				int rows = rs.getRow();
				if (rows != 0) {
					if (rows < 65535) {

						rs.beforeFirst();
						String REPORT_PATH = "";
						String osName = System.getProperty("os.name");
						if (osName.equals("Linux")) {
							REPORT_PATH = sc006a.getSysParam(conn, form, request, "REPORT_PATH_LINUX");
						} else {
							REPORT_PATH = sc006a.getSysParam(conn, form, request, "REPORT_PATH_WINDOWS");
						}
						File dir = new File(REPORT_PATH);
						if (!dir.exists())
							dir.mkdirs();
						dir = new File(REPORT_PATH + "/tmp/");
						if (!dir.exists())
							dir.mkdirs();
						String filename = REPORT_PATH + "/tmp/" + new Date().getTime() + ".xls";
						File os = new File(filename);

						jxl.write.WritableWorkbook wwb = Workbook.createWorkbook(os);
						jxl.write.WritableSheet ws = wwb.createSheet("Sheet1", 0);

						jxl.write.WritableFont wfc = new jxl.write.WritableFont(WritableFont.createFont("細明體"), 12, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLACK);
						jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(wfc);

						// wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN);
						wcfFC.setAlignment(Alignment.CENTRE);

						jxl.write.Label labelC = null;

						// 產生表頭
						// for (int i = 0; i < colNames.length; i++) {
						// String cont = colNames[i].replaceAll("◎", "");
						// labelC = new jxl.write.Label(i, 0, cont);
						// labelC.setCellFormat(wcfFC);
						// ws.setColumnView(i, 20);
						// ws.addCell(labelC);
						// }
//						 產生表頭
						for (int i = 0; i < heads.length; i++) {
							 labelC = new jxl.write.Label(i, 0, heads[i]);
							 labelC.setCellFormat(wcfFC);
							 ws.setColumnView(i, 20);
							 ws.addCell(labelC);
						}
						
						
						// 產生明細
					
						int k = 1;
						while (rs.next()) {
							for (int i = 0; i < propertys.length; i++) {
								String property="";
								String code="";
								if(propertys[i].indexOf("@")>-1)
								{
									code=propertys[i].substring(propertys[i].indexOf("@")+1,propertys[i].length());
									property=propertys[i].substring(0,propertys[i].indexOf("@"));
								}else
								{
									property=propertys[i];
								}
								property=rs.getString(property)==null?"":rs.getString(property);
								if(code.equals(""))
									labelC = new jxl.write.Label(i, k, property);
								else
									labelC = new jxl.write.Label(i, k, BA_TOOLS.getInstance().getCodeName(conn, code, property));
								
								labelC.setCellFormat(wcfFC);
								ws.addCell(labelC);
							}
							k++;
						}
						rs.close();

						// 產生明細
						// for (int i = 0; i < list.size(); i++) {
						// String[] data = (String[]) list.get(i);
						// for (int k = 0; k < data.length; k++) {
						// labelC = new jxl.write.Label(k, i + 1, data[k]);
						// labelC.setCellFormat(wcfFC);
						// ws.addCell(labelC);
						// }

						wwb.write();
						wwb.close();

						//
						//
						File f = new File(filename);
						String name = f.getName().substring(f.getName().lastIndexOf("/") + 1, f.getName().length());
						response.setHeader("Content-Disposition", "attachment; filename=\" " + name + "\"");
						ServletOutputStream ouputStream;
						try {
							InputStream in = new FileInputStream(f);
							ouputStream = response.getOutputStream();
							int bit = 0;
							byte [] bits=new byte[4096];
							while ((bit=in.read(bits)) >-1 ) {
								ouputStream.write(bits,0,bit);
								Thread.sleep(1);
							}
							ouputStream.flush();
							ouputStream.close();
							in.close();

						} catch (IOException e) {
							// e.printStackTrace();
						}

					} else {
						sendmsg("資料筆數過多，請先縮小查詢範圍!", response);
					}
				} else {
					sendmsg("無資料可列印!", response);
				}
			} else {
				sendmsg("請先查詢!", response);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("BA_Collection_Print.showList():" + e);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}

		return null;
	}

	private void sendmsg(String msg, HttpServletResponse response) {
		try {
			
			ServletOutputStream ouputStream = response.getOutputStream();
			String oo = "<script>alert('" + msg + "');window.history.back();</script>";
			ouputStream.write(oo.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
