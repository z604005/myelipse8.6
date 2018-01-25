package com.spon.utils.sc.actions;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.spon.struts.NewDispatchAction;
import com.spon.utils.sc.forms.FU001F;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 使用者管理
 * @version 1.0
 * @created 10-四月-2006 下午 04:37:53
 */
public class FU001A extends NewDispatchAction {

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
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {

		Connection conn = null;

		FU001F Form = new FU001F();

		generateSelectBox(conn, Form, request);
		request.setAttribute("Form1Datas", Form);

		//將表單的顯示模式設定為edit (編輯模式)，因為struts-layout沒有定義查詢模式，只好用edit了
		FormUtils.setFormDisplayMode(request, form, "edit");
		return mapping.findForward("success");
	}

	public ActionForward upload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;

		FU001F Form = (FU001F) form;
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		
		if (lc_errors.isEmpty()) {
			try {
				conn = tools.getConnection();
				FormFile File1 = Form.getFILE1();

				Workbook rwb = null;
				rwb = Workbook.getWorkbook(File1.getInputStream());
				//int totalsheet=rwb.getNumberOfSheets();

				int totalrow = 0;
				StringBuffer sb = new StringBuffer();
				//處理第一個工作表表
				{
					sb.append("<table border=1>");
					Sheet rs = rwb.getSheet(0);
					//獲取Sheet表中所包含的總行數
					int rsRows = rs.getRows();
					//獲取指定單元格的對象引用
					for (int i = 0; i < rsRows; i++) {
						totalrow++;
						Cell cell[] = rs.getRow(i);
						sb.append("<tr>");
						for (int j = 0; j < cell.length; j++) {
							//每格的值
							//System.out.print(cell[j].getContents());
							sb.append("<td>");
							sb.append(cell[j].getContents());
							sb.append("</td>");
						}
						//System.out.println();
						sb.append("</tr>");
					}
					sb.append("</table>");
				}

				rwb.close();
				File1.destroy();

				request.setAttribute("MSG", "<script>alert('上傳成功，共處理 " + totalrow + " 列資料! ');</script>");
				request.setAttribute("BODY", sb.toString());
			} catch (Exception e) {
				System.out.println("FU001A upload error" + e);
				e.printStackTrace();
				request.setAttribute("MSG", "上傳失敗:" + e);
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			saveErrors(request, lc_errors);
		}

		//將表單的顯示模式設定為edit (編輯模式)，因為struts-layout沒有定義查詢模式，只好用edit了
		FormUtils.setFormDisplayMode(request, form, "edit");
		return mapping.findForward("success");
	}

	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request) {
		boolean openbyme = false;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try {
			if (conn.isClosed()) {
				try {
					conn = tools.getConnection();
					openbyme = true;
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		} catch (Exception e1) {
			try {
				DataSource ds = tools.getDataSource();
				conn = ds.getConnection();
				openbyme = true;
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		if (openbyme) {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	

}