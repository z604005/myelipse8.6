package com.spon.utils.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.spon.utils.sc.actions.SC006A;


public class FileOutput extends HttpServlet{
	private static final String CONTENT_TYPE = "application/x-download";
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource dataSource = (DataSource)request.getSession().getServletContext().getAttribute("SPOS");
		Connection conn = null;
		String path = "";
		try {
			conn = dataSource.getConnection();
			SC006A sys = new SC006A();
			path = sys.getSysParam(conn, null, request, "PATH_MEDIAS");
		} catch (Exception e) {
			try {
				conn.close() ;
			}catch(Exception E) {
			}
			
			// TODO: handle exception
		}
		request.setCharacterEncoding("utf-8");
		
		String filenamedownload ="";
		String osName = System.getProperty("os.name");
		if (osName.equals("Linux")) {
			filenamedownload=request.getParameter("name");
		} else {
			filenamedownload="C:" + "/"+request.getParameter("name");
		}
		ServletOutputStream out=response.getOutputStream();
		response.setContentType(CONTENT_TYPE+";charset=iso-8859-1");
		
		
		  BufferedInputStream  in = new BufferedInputStream(new FileInputStream(filenamedownload));

//		BufferedReader br = new BufferedReader(new FileReader(filenamedownload));
		
		  int aRead = 0;
			while((aRead = in.read()) > -1 & in != null)
			{
				out.write(aRead);
			}
			out.flush();

		  out.close();	
//        br.close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			doGet(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws IOException {

	}
}