package com.spon.ems.salary.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import vtgroup.ems.common.vo.AuthorizedBean;
import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.NewDispatchAction;
import com.spon.ems.fileimport.IMP_EHF030104;
import com.spon.ems.salary.forms.EHF030104M1F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.field.AbstractModeFieldTag;
import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>勞保等級維護
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF030104M0A extends NewDispatchAction {

	public EHF030104M0A() {

	}

	/**
	 * 儲存新增資料
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {

		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF030104M1F Form = (EHF030104M1F) form;

		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		request.setAttribute("action", "addData");
		ActionErrors lc_errors = Form.validate(mapping, request, conn);

		try {
			if (lc_errors.isEmpty()) {
					//新增表單資訊
					String sql = ""  ;

					PreparedStatement pstmt = conn.prepareStatement(sql);
					P6PreparedStatement p6stmt = new P6PreparedStatement(null,
							pstmt, null, sql);
					int indexid = 1;
				
					
					p6stmt.executeUpdate();
					pstmt.close();
					p6stmt.close();			

					conn.commit();
					generateSelectBox(conn, Form, request);
					
					request.setAttribute("MSG", "新增成功");
					request.setAttribute("button", "edit");
					request.setAttribute("collection", "");
					// 清掉畫面上的新增
			
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("MSG", request.getAttribute("ErrMSG"));
//				return init(mapping, form, request, response);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("MSG", "新增失敗!");
			System.out.println("EHF030104M0A.addData() " + e);
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}

//		return queryDatas(mapping, form, request, response);
//		return mapping.findForward("success");
		return init(mapping, form, request, response);
	}

	/**
	 * 新增
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return addData(mapping, form, request, response);
	}
	
	/**
	 * 新增畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward addForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("redirectADD");
	}
	
	/**
	 * 明細資料畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response    response
	 */
	public ActionForward editForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		return mapping.findForward("redirectEDIT");
    	}else{
    		request.setAttribute("MSG",(request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"請選取要查看的資料!!");
    		return queryDatas(mapping,form,request,response);
    	}
		
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	private void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request) {
		
		/*//產生年度
		try{
			List EHF030104T0_02_list=new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			EHF030104T0_02_list.add(bform);
			DecimalFormat df=new DecimalFormat("00");
			for (int i = 111; i > 99; i--) {
				bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				EHF030104T0_02_list.add(bform);
			}
			request.setAttribute("EHF030104T0_02_list",EHF030104T0_02_list);

			}catch(Exception e) {
				System.out.println(e);
			}*/

	}

	/**
	 * Action的進入點
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try{
			
			conn = tools.getConnection("SPOS");
			EHF030104M1F Form = new EHF030104M1F();
			ArrayList list = new ArrayList();
			generateSelectBox(conn, Form, request);
	
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			
//			FormUtils.
			
//			int data01 = -1;
//			int data02 = -1;
//			int data03 = -1;
//			
//			if(FormUtils.getFieldDisplayMode(request, form, "DATA01") != null){
//				data01 = FormUtils.getFieldDisplayMode(request, form, "DATA01");
//			}
//			if(FormUtils.getFieldDisplayMode(request, form, "DATA02") != null){
//				data02 = FormUtils.getFieldDisplayMode(request, form, "DATA02");
//			}
//			if(FormUtils.getFieldDisplayMode(request, form, "DATA03") != null){
//				data03 = FormUtils.getFieldDisplayMode(request, form, "DATA03");
//			}
//			
//			
//			System.out.println("data01 ==> display mode = "+data01);
//			System.out.println("data02 ==> display mode = "+data02);
//			System.out.println("data03 ==> display mode = "+data03);
			
//			// reset FormUtils
//			FormUtils.setFieldDisplayMode(request, form, "DATA01", 3);
//			FormUtils.setFieldDisplayMode(request, form, "DATA02", 2);
//			FormUtils.setFieldDisplayMode(request, form, "DATA03", 2);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "query");
			request.setAttribute("collection", "show");
			
//			FormUtils.setFormDisplayMode(request, form, "create");
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
				conn.close();
			}catch(Exception e){
				
			}
	    }
		
		return mapping.findForward("success");
	}

	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF030104M1F Form = (EHF030104M1F) form;

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT EHF030104T0_01 ,EHF030104T0_02,EHF030104T0_02_VERSION ,EHF030104T0_03 ,EHF030104T0_04 " +
				" FROM EHF030104T0 " +
				" WHERE 1=1 " +
				" AND EHF030104T0_05 = '"+getLoginUser(request).getCompId()+"' " ;
				
				if(!"".equals(Form.getEHF030104T0_02()) && Form.getEHF030104T0_02() != null ){
					sql += " AND EHF030104T0_02 = '"+Form.getEHF030104T0_02()+"' ";  //年度
				}
				if(!"".equals(Form.getEHF030104T0_04()) && Form.getEHF030104T0_04() != null ){
					sql += " AND EHF030104T0_04 LIKE '%"+Form.getEHF030104T0_04()+"%' ";  //備註
				}
				
				sql += " ORDER BY EHF030104T0_01 DESC ";//LIMIT 10 ";

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				//Map compMap = ems_tools.getCompNameMap(getEmscontext(), getLoginUser(request).getUserId());				
				
				while(rs.next()){
					EHF030104M1F bean = new EHF030104M1F();
					bean.setEHF030104T0_01(rs.getString("EHF030104T0_01"));  //勞保等級序號
					bean.setEHF030104T0_02(rs.getString("EHF030104T0_02"));  //年度
					bean.setEHF030104T0_02_VERSION(rs.getString("EHF030104T0_02_VERSION"));
					bean.setEHF030104T0_04(rs.getString("EHF030104T0_04"));  //備註
					//bean.setEHF030104T0_03( (String) ((Map)compMap.get(rs.getString("EHF030104T0_03"))).get("COMP_NAME_C") );  //公司組織(代號)
					
					list.add(bean);
				}
				
				request.setAttribute("Form2Datas",list);
//				Form.setEHF030102C(list);
				
				rs.close();
				stmt.close();
				hr_tools.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				if(list.isEmpty()){
					request.setAttribute("MSG",(request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"查無資料");
				}else{
					request.setAttribute("MSG",(request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"查詢成功");
				}
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Form.setEHF030104T0_02("");
		Form.setEHF030104T0_02_VERSION("");
		request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return queryDatas(mapping, form, request, response);
	}

	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF030104M1F Form = (EHF030104M1F) form;
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		String arrid[] = request.getParameterValues("checkId");
//    	if ((arrid==null?false:!arrid[0].equals(""))){
//    		Form.setEHF030102T0_01(arrid[0]);
//		}else if("".equals(Form.getEHF030102T0_01()) || Form.getEHF030102T0_01() == null){
//			request.setAttribute("MSG", "請先選擇一筆要修改的資料!!");
//			return queryDatas(mapping, form, request, response);
//		}
    	
    	try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "editDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			

			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" ;

				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				if(rs.next()){
				}
				request.setAttribute("Form2Datas",list);
//				Form.setEHF030102C(list);
				
				rs.close();
				stmt.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("Form1Datas", Form);
		return mapping.findForward("success");
//		return saveData(mapping, form, request, response);
	}

	public ActionForward saveData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030104M1F Form = (EHF030104M1F) form;
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "saveData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			BA_Vaildate ve=BA_Vaildate.getInstance();
//			if("".equals(Form.getEHF030102T0_01())){
//				request.setAttribute("MSG","薪資計算公式序號錯誤!!請重新查詢資料!!");
//				return init(mapping, form, request, response);
//				
//			}

			if (lc_errors.isEmpty()) {
				
				String sql = "" ;

				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				
				
				System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
				request.setAttribute("MSG","儲存成功!!");
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("button", "edit");
				request.setAttribute("collection", "");
				
	
				
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
		} catch (Exception e) {

			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			
			request.setAttribute("MSG","薪資計算公式儲存錯誤!!請重新操作!!");
						
			System.out.println(e);
			e.printStackTrace();
			
			return init(mapping, form, request, response);
			
			
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		return queryDatas(mapping, form, request, response);
//		return mapping.findForward("success");
		return editDataForm(mapping, form, request, response);
	}

	public ActionForward delData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF030104M1F Form = (EHF030104M1F) form;
		
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF030104T0_01(arrid[0]);
		}else{
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"請先選擇一筆要刪除的資料!!");
			return queryDatas(mapping, form, request, response);
		}
		
		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = Form.validate(mapping, request, conn);
			if (lc_errors.isEmpty()) {

					Statement stmt = conn.createStatement();
					ResultSet rs=null;
					String sql = "" ;
					sql = " SELECT * FROM EHF030104T0 " +
						  " WHERE 1=1 " +
						  " AND EHF030104T0_01 = '"+Form.getEHF030104T0_01()+"' " +
						  " AND EHF030104T0_05 = '"+getLoginUser(request).getCompId()+"' ";				
					rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						if(!"1".equals(rs.getString("EHF030104T0_06"))){
						sql = " DELETE FROM EHF030104T0 " +
						" WHERE 1=1 " +
						" AND EHF030104T0_01 = '"+Form.getEHF030104T0_01()+"' " +
						" AND EHF030104T0_05 = '"+getLoginUser(request).getCompId()+"' ";
						stmt.execute(sql);
						
						sql = " DELETE FROM EHF030104T1 " +
						" WHERE 1=1 " +
						" AND EHF030104T1_01 = '"+Form.getEHF030104T0_01()+"' " +
						" AND EHF030104T1_08 = '"+getLoginUser(request).getCompId()+"' ";
						stmt.execute(sql);
						
						conn.commit();
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"刪除成功");
						}else {
							request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"該設定檔已被使用，無法刪除。");
						}
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"刪除失敗，選取的資料不存在!!");
					}
					
					rs.close();
					stmt.close();
					conn.commit();
								
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return init(mapping, form, request, response);
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return init(mapping, form, request, response);
	}
	/**
	 * 匯入健保資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward impEHF030104(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF030104M1F Form = (EHF030104M1F) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		//Collection List
		List list = new ArrayList();
		//NG_Collection List
		List ng_list = new ArrayList();
		//ERROR_Collection List
		List error_list = new ArrayList();
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			
			//建立健保匯入元件
			IMP_EHF030104 imp_EHF030104 = new IMP_EHF030104( authbean.getEmployeeID(),authbean.getUserName() );
			
			//取得匯入檔案
			FormFile importfile = Form.getIMP_EHF030104();
			
			//取得回傳訊息Map
			Map msgMap = imp_EHF030104.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(),importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());

			List datalist = (List) msgMap.get("DATALIST");

			Iterator it = datalist.iterator();

			Map tempMap = null;
			EMS_VIEWDATAF bean = null;

			//正確匯入的資料
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				EHF030104M1F bForm = new EHF030104M1F();
				
				bForm.setEHF030104T0_02((String)tempMap.get("EHF030104T0_02"));//序號
				bForm.setEHF030104T1_07((String)tempMap.get("EHF030104T1_07"));//組織單位
				bForm.setEHF030104T0_02_VERSION((String)tempMap.get("EHF030104T0_02_VERSION"));//員工工號
				bForm.setEHF030104T1_03((String)tempMap.get("EHF030104T1_03"));//員工姓名
			
				list.add(bForm);
				
			}
			//設定前端顯示訊息
			request.setAttribute("right_MSG", "共成功匯入 "+(Integer)msgMap.get("DATACOUNT")+" 筆!!");
			request.setAttribute("Form4Datas",list);
			request.setAttribute("right_collection", "show");

			
			
			
			//判斷是否有重複未匯入的資料清單
			if(msgMap.containsKey("NGDATACOUNT")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("NGDATALIST");
				
				it = datalist.iterator();
				tempMap = null;
				bean = null;
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					EHF030104M1F bForm = new EHF030104M1F();
					
					bForm.setEHF030104T0_02((String)tempMap.get("EHF030104T0_02"));//序號
					bForm.setEHF030104T1_07((String)tempMap.get("EHF030104T1_07"));//組織單位
					bForm.setEHF030104T0_02_VERSION((String)tempMap.get("EHF030104T0_02_VERSION"));//員工工號
					bForm.setEHF030104T1_03((String)tempMap.get("EHF030104T1_03"));//員工姓名
					bForm.setERROR((String)tempMap.get("error"));
					ng_list.add(bForm);
				}
				
				//設定前端顯示訊息
				request.setAttribute("NGMSG", "共有 "+(Integer)msgMap.get("NGDATACOUNT")+" 筆資料錯誤，請檢查!!");
				request.setAttribute("Form3Datas",ng_list);
				request.setAttribute("ng_collection", "show");
			}
			//設定前端顯示資訊
			request.setAttribute("Form1Datas", 	Form);
			request.setAttribute("Form2Datas",	list);
			FormUtils.setFormDisplayMode(request, form, "create");
			//request.setAttribute("collection", "show");
			request.setAttribute("button", "Import");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if( conn!=null && !conn.isClosed() ){
					conn.close();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		return mapping.findForward("success");
	}
	/**
	 * 下載空白範例匯入檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print_example(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		BA_TOOLS tools = BA_TOOLS.getInstance();
		try {			
			// 存入檔案
			String FileName = getServlet().getServletContext().getRealPath("")+"/excelrpt/"+"//" + "EHF030104R0_example.xls"; 
			String name ="勞保等級匯入(範本)"+tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";
			response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("BIG5"), "iso8859-1"));
			ServletOutputStream ouputStream;
			try {
				InputStream in = new FileInputStream(FileName);
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
				e.printStackTrace();
			}
			return null;
		
		} catch (Exception E) {
			E.printStackTrace();
			request.setAttribute("MSG", "列印失敗!!");
		} finally {
			// ef.write();
		}
		return this.init(mapping, form, request, response);
	}
	
	/**
	 * 回上一頁
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward redirect(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return init(mapping, form, request, response);
	}
	
}