package com.spon.utils.sc.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.RP001F;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.util.BA_REPORT;
import com.spon.utils.util.BA_REPORT_TYPE;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;


/**
 * 使用者管理
 * @version 1.0
 * @created 10-四月-2006 下午 04:37:53
 */
public class RP001A extends NewDispatchAction {

	String reportCName="薪資程式清單";
//	定義 此報表可以列印型態
	public String [][] 	report_type={BA_REPORT_TYPE.PDF,BA_REPORT_TYPE.XLS,BA_REPORT_TYPE.RTF};
	
	public void finalize() throws Throwable {
		super.finalize();
	}

		
	/**
	 * 一開始進來程式時顯示空的資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
	
		Connection conn=null;
		
		RP001F Form=new RP001F();
		
		
		Form.setDATEB(getDate());
		Form.setDATEE(getDate());
		
		generateSelectBox(conn,Form,request);
		request.setAttribute("Form1Datas", Form);
		
		//將表單的顯示模式設定為edit (編輯模式)，因為struts-layout沒有定義查詢模式，只好用edit了
		FormUtils.setFormDisplayMode(request, form, "edit");
		return mapping.findForward("success");
	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		//request.setAttribute("MSG","<SCRIPT>alert('列印完成');</SCRIPT>");
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection();
			
			RP001F Form=(RP001F) form;
		
		
			Map parameters = new HashMap();
			SC006A sc006a=new SC006A();
			parameters.put("RPT_TITLE", sc006a.getSysParam(conn,form,request,"RPT_TITLE")+reportCName);
			parameters.put("RPT_SDATE", Form.getDATEB());
			parameters.put("RPT_EDATE", Form.getDATEE());
		
//		if(!Form.getPEUNIT().equals(""))
//			parameters.put("PEUNIT", " and c.PEUNIT='"+Form.getPEUNIT()+"' ");
//		else
//			parameters.put("PEUNIT", "");
		
		parameters.put("RPT_BUILDDATE", getDate()+" "+getTime());
		
		Vector rptform=new Vector();
		rptform.add("RP001P00");
		
		BA_REPORT report=new BA_REPORT();
		report.setConn(conn);
		report.setRPT_TYPE(Form.getRPT_TYPE());
		
		SC003F sc003f=(SC003F)request.getSession().getAttribute("userform");
		
		String REPORT_PATH="";
		String osName = System.getProperty("os.name");
		
		if (osName.equals("Linux")) {
			REPORT_PATH=sc006a.getSysParam(conn,form,request,"REPORT_PATH_LINUX");
		} else {
			REPORT_PATH=sc006a.getSysParam(conn,form,request,"REPORT_PATH_WINDOWS");
		}
		
		String  filename = report.prints(getServlet().getServletContext().getRealPath(""),REPORT_PATH,sc003f.getSC0030_01(),rptform, reportCName,parameters);
		
		//System.out.println(filename);
		
		if (filename.equals("")) {
			request.setAttribute("MSG","<SCRIPT>alert('目前無資料可供列印!!');</SCRIPT>");
			request.setAttribute("Form1Datas", form);
			//將表單的顯示模式設定為edit (編輯模式)，因為struts-layout沒有定義查詢模式，只好用edit了
			FormUtils.setFormDisplayMode(request, form, "edit");
			generateSelectBox(conn,form,request);
			return mapping.findForward("success");
		}else {
			File f = new File (filename);
			String name = f.getName().substring(f.getName().lastIndexOf("/") + 1,f.getName().length());
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
				
				//e.printStackTrace();
			}
			
		}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
//		request.setAttribute("Form1Datas", form);
//		//將表單的顯示模式設定為edit (編輯模式)，因為struts-layout沒有定義查詢模式，只好用edit了
//		//FormUtils.setFormDisplayMode(request, form, "edit");
//		generateSelectBox(conn,form,request);
		return null;
	}
	
	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request){
		boolean openbyme=false;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		try {
			if(conn.isClosed()){
				try {
					conn = tools.getConnection();
			        openbyme=true;
			    } catch (SQLException e2) {
			        e2.printStackTrace();
			    }
			}
		} catch (Exception e1) {
			try {
				conn = tools.getConnection();
		        openbyme=true;
		    } catch (Exception e2) {
		        e2.printStackTrace();
		    }
		}	
		
//		String sql="select OA2PUNIT as item_id,OA2UNITN as item_value from CPAOA02M  order by 1 ";
//		request.setAttribute("listUNIT",this.getSelectOptions(conn,sql,true));
		
		request.setAttribute("listRPT_TYPE",this.getRPT_TYPE(report_type));
		
		
		if(openbyme)
		{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	
		

		
}