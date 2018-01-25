package com.spon.utils.util;


import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import com.spon.utils.sc.actions.SC006A;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JExcelApiExporterParameter;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterParameter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRProperties;

public class BA_REPORT  {

	Connection conn;
	String RPT_TYPE ="PDF";
	
	
	/**
	 * 產生報表檔-使用SQL語法
	 * @param appPath  網站的實體路徑
	 * @param reportPath 報表存放的實體位置
	 * @param user_id  使用者編號
	 * @param sql		SQL語法
	 * @param reportName 報表名稱
	 * @param reportCName 報表中文名稱
	 * @param parameters  從外部給報表參數
	 * @return
	 */
	public String print(String appPath,String reportPath ,String user_id,String sql, String reportName,String reportCName, Map parameters) {
		String filename="";
		try {
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			JRResultSetDataSource jrResultSetDataSource = new JRResultSetDataSource(rs);
			if (!rs.next()) {
				return filename;
			}else{
				rs.beforeFirst();
				//rs.first();
			}
			
			JRProperties.setProperty(
				JRProperties.COMPILER_CLASSPATH, 
				appPath + "/WEB-INF/lib/jasperreports-1.2.2.jar" +
				System.getProperty("path.separator") +
				appPath + "/WEB-INF/classes/"
			);

			JasperCompileManager.compileReportToFile(appPath + "/WEB-INF/rpt/" + reportName + ".jrxml");
			
			File  RptFile=new File(appPath + "/WEB-INF/rpt/" + reportName +".jasper");
			JasperReport report = (JasperReport)JRLoader.loadObject(RptFile);
			
			Check_Tmp_Path(appPath,reportPath,user_id);
			
			SC006A sc006a=new SC006A();
			int maxsize=Integer.parseInt(sc006a.getSysParam(conn, null, null, "REPORT_MEM_SIZE"));
			
			JRFileVirtualizer virtualizer = new JRFileVirtualizer(maxsize, appPath+"/WEB-INF/tmp");

			
			parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,jrResultSetDataSource);
			
			virtualizer.setReadOnly(true);

				if(!jasperPrint.getPages().isEmpty())
				{
					filename=reportPath+"/reports/"+user_id+"/"+reportName+"_"+new Date().getTime();
					
					if(RPT_TYPE.equals("PDF"))
					{
						filename=exportPDF(filename, jasperPrint);
					}
					if(RPT_TYPE.equals("XLS"))
					{
						filename=exportXSL(filename, jasperPrint);
					}
					if(RPT_TYPE.equals("RTF"))
					{
						filename=exportRTF(filename, jasperPrint);
					}
					if(RPT_TYPE.equals("HTML"))
					{
						filename=exportHTML(filename, jasperPrint);
					}
					if(RPT_TYPE.equals("CSV"))
					{
						filename=exportCSV(filename, jasperPrint);
					}
					if(RPT_TYPE.equals("XML"))
					{
						filename=exportXML(filename, jasperPrint);
					}
				}
					
			
			virtualizer.cleanup();
			
			

		} catch (Exception e) {
			System.out.println(e);
		}
		return filename;
	}
	
	/**
	 * 產生報表檔案 SubReport 專用
	 * @param appPath 網站的實體路徑
	 * @param reportPath 報表存放的實體位置
	 * @param user_id
	 * @param reportName 報表檔名(不含副檔名) 多份 用Vector裝進來
	 * @param reportCName 報表中文名稱
	 * @param parameters 從外部給報表參數
	 * @return 檔名
	 */
	
	public String prints(String appPath,String reportPath , String user_id,Vector reportName, String reportCName,Map parameters) {
		String filename="";
		
		try {
			JRProperties.setProperty(
				JRProperties.COMPILER_CLASSPATH, 
				appPath + "/WEB-INF/lib/jasperreports-1.2.2.jar" +
				System.getProperty("path.separator") +
				appPath + "/WEB-INF/classes/"
			);

			for (int i = 0; i < reportName.size(); i++) {
				JasperCompileManager.compileReportToFile(appPath + "/WEB-INF/rpt/" + reportName.get(i) + ".jrxml");
			}

			if(reportName.size()>0)
			{
				File  RptFile=new File(appPath + "/WEB-INF/rpt/" + reportName.get(0) +".jasper");
				JasperReport report = (JasperReport)JRLoader.loadObject(RptFile);
				
				Check_Tmp_Path(appPath,reportPath,user_id);
				
				SC006A sc006a=new SC006A();
				int maxsize=Integer.parseInt(sc006a.getSysParam(conn, null, null, "REPORT_MEM_SIZE"));
				
				JRFileVirtualizer virtualizer = new JRFileVirtualizer(maxsize, appPath+"/WEB-INF/tmp");

				
				parameters.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters,conn);
				
				virtualizer.setReadOnly(true);
				
				
				if(!jasperPrint.getPages().isEmpty())
					{
						filename=reportPath+"/reports/"+user_id+"/"+reportName.get(0)+"_"+new Date().getTime();
						
						if(RPT_TYPE.equals("PDF"))
						{
							filename=exportPDF(filename, jasperPrint);
						}
						if(RPT_TYPE.equals("XLS"))
						{
							filename=exportXSL(filename, jasperPrint);
						}
						if(RPT_TYPE.equals("RTF"))
						{
							filename=exportRTF(filename, jasperPrint);
						}
						if(RPT_TYPE.equals("HTML"))
						{
							filename=exportHTML(filename, jasperPrint);
						}
						if(RPT_TYPE.equals("CSV"))
						{
							filename=exportCSV(filename, jasperPrint);
						}
						if(RPT_TYPE.equals("XML"))
						{
							filename=exportXML(filename, jasperPrint);
						}
						
				}
				virtualizer.cleanup();
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		return filename;
	}
	
	
	
	
	
public void Check_Tmp_Path(String appPath,String reportPath,String user_id) {
	File file=new File(appPath+"/WEB-INF/tmp");
	if(!file.exists())
		file.mkdirs();
	file=new File(reportPath);
	if(!file.exists())
		file.mkdirs();
	file=new File(reportPath+"/reports/");
	if(!file.exists())
		file.mkdirs();
	file=new File(reportPath+"/reports/"+user_id);
	if(!file.exists())
		file.mkdirs();
		
	}

private String exportPDF(String filename,JasperPrint jasperPrint)
	{
		filename+=".pdf";		
		JRPdfExporter exporter=new JRPdfExporter();
		exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT,jasperPrint);
		//exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, OutputBytes);
		exporter.setParameter(JRPdfExporterParameter.OUTPUT_FILE_NAME, filename);
		try {
			exporter.exportReport();
		} catch (JRException e) {
			e.printStackTrace();
		}
	return filename;
	}
	
private String exportXSL(String filename,JasperPrint jasperPrint)
{
	filename+=".xls";		
	JExcelApiExporter exporter = new JExcelApiExporter();
	exporter.setParameter(JExcelApiExporterParameter.JASPER_PRINT, jasperPrint);
    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE); 
    // exporter.setParameter(JExcelApiExporterParameter.IS_AUTO_DETECT_CELL_TYPE, java.lang.Boolean.TRUE);
    exporter.setParameter(JExcelApiExporterParameter.IS_FONT_SIZE_FIX_ENABLED, java.lang.Boolean.TRUE);
    exporter.setParameter(JExcelApiExporterParameter.IS_WHITE_PAGE_BACKGROUND, java.lang.Boolean.FALSE);
    exporter.setParameter(JExcelApiExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, java.lang.Boolean.TRUE);
    //exporter.setParameter(JExcelApiExporterParameter.OUTPUT_STREAM, OutputBytes);
    exporter.setParameter(JExcelApiExporterParameter.OUTPUT_FILE_NAME, filename);
    try {
		exporter.exportReport();
	} catch (JRException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return filename;
}

private String exportRTF(String filename,JasperPrint jasperPrint)
{
	filename+=".rtf";		
	JRRtfExporter exporter = new JRRtfExporter();
	
	exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,"UTF8");
	exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, filename);

	
    try {
		exporter.exportReport();
	} catch (JRException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return filename;
}

private String exportXML(String filename,JasperPrint jasperPrint)
{
	filename+=".xml";		
	JRXmlExporter exporter = new JRXmlExporter();
	
	exporter.setParameter(JRXmlExporterParameter.JASPER_PRINT, jasperPrint);
	exporter.setParameter(JRXmlExporterParameter.OUTPUT_FILE_NAME, filename);

	
    try {
		exporter.exportReport();
	} catch (JRException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return filename;
}

private String exportHTML(String filename,JasperPrint jasperPrint)
{
	filename+=".html";		
	try {
		JasperExportManager.exportReportToHtmlFile(jasperPrint, filename);
	} catch (JRException e) {
 
		e.printStackTrace();
	}
	
	return filename;
}

private String exportCSV(String filename,JasperPrint jasperPrint)
{
	filename+=".csv";		
	JRCsvExporter exporter = new JRCsvExporter();
	
	exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, jasperPrint);
	exporter.setParameter(JRCsvExporterParameter.CHARACTER_ENCODING,"MS950");
	exporter.setParameter(JRCsvExporterParameter.OUTPUT_FILE_NAME, filename);

	
    try {
		exporter.exportReport();
	} catch (JRException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return filename;
}


	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	/**
	 * 
	 * @param reportType PDF ,XSL
	 */
	public void setRPT_TYPE(String rpt_type) {
		RPT_TYPE = rpt_type;
	}

}
