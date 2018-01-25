package com.spon.utils.sc.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.EXRP002F;
import com.spon.utils.struts.form.BA_REPORTF;
import com.spon.utils.util.BA_REPORT;
import com.spon.utils.util.BA_REPORT_TYPE;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.BA_XML;
import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 使用者管理
 * @version 1.0
 * @created 10-四月-2006 下午 04:37:53
 */
public class RP002A extends NewDispatchAction {

	public void finalize() throws Throwable {
		super.finalize();
	}

	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("DEPT_OTHERS", request.getAttribute("UNIT_OTHERS") + " and LENGTH(SC_UNITM_01) = 2 and SC_UNITM_01 <>'99' ");
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		try {
			conn = tools.getConnection();
			
			BA_REPORTF Form = new BA_REPORTF();
			request.setAttribute("Form1Datas", Form);

			//將表單的顯示模式設定為create (新增模式)
			FormUtils.setFormDisplayMode(request, form, "create");
			//指定執行鈕，應該要呼叫的method
			request.setAttribute("BUTTON_TYPE", "addData");
			request.setAttribute("rownum", "0");

		} catch (Exception e) {
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		request.setAttribute("action", "新增");

		String Required = "true";
		String Mode = "E,R,I";

		request.setAttribute("Required", Required);
		request.setAttribute("Mode", Mode);

		return mapping.findForward("success");
	}

	public  ActionForward printExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		
		response.addHeader("Content-Type", "text/xml;charset=utf-8");
		request.setAttribute("DEPT_OTHERS", request.getAttribute("UNIT_OTHERS") + " and LENGTH(SC_UNITM_01) = 2 and SC_UNITM_01 <>'99' ");
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		String strwhere="";
		try {
			response.addHeader("Content-Type", "text/xml;charset=utf-8");
			
			conn = tools.getConnection();
			
			strwhere = request.getParameter("other") +"";
			
			//組成XML的檔案
			BA_XML bx = new BA_XML();
			
			//	指定Excel的檔名
			bx.setExcelName("test.xls",request,conn);
			//	丟標題所需要的資料(非內容的資料)
			bx.setText("年度","");
			bx.setText("標題","");
		
			
			//丟內容
			
			
			Vector contens = new Vector();
			String sql = "SELECT * FROM SC0020 where SC0020_01 like '%" + strwhere + "%' and SC0020_08='"+userform(request).getSC0030_14()+"'";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			//第一列固定為標題，hashMap的元素 Key值一定要是字串流水號
			HashMap temptxt = new HashMap();
			temptxt.put("1","AAA");
			temptxt.put("2","BBB");
			temptxt.put("3","CCC");
			temptxt.put("4","DDD");
			contens.add(temptxt);
			
			//第二列開始才丟資料	
			while(rs.next())
			{
				temptxt = new HashMap();
				temptxt.put("1",rs.getString("SC0020_01"));
				temptxt.put("2",rs.getString("SC0020_02"));
				temptxt.put("3",rs.getString("SC0020_03"));
				temptxt.put("4",rs.getString("SC0020_04"));
				contens.add(temptxt);	
			}
			rs.close();
			stmt.close();
			bx.setBody(contens);
			try {
				byte [] in =bx.getXML().getBytes("UTF-8");
				for (int i = 0; i < in.length; i++) {
					response.getOutputStream().write(in[i]);
				} 
			} catch (IOException e) {
				e.printStackTrace();
			}
	        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
	/** 
	 * 列印EXCEL用的元件
	 * Creation date: 02-01-2006
	 * 
	 * XDoclet definition:
	 * 
	 */
	public class dataArr {
		public boolean cflag = false;
		public String Datas[][] = new String[21][3];
		
	}
	//將各項資料寫入紀錄
	private boolean setData(dataArr ef,String gsdata,String smsdata,int aindex) throws Exception{
		
		ef.Datas[aindex][0] = gsdata+"";
		ef.Datas[aindex][1] = smsdata+"";
		if (!gsdata.equals(smsdata)) 
			return true;
		else
			return false;
	}


	
}