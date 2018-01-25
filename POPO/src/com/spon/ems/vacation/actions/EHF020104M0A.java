package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.vacation.forms.EHF020104M0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.work_schedule.EMS_WorkScheduleControl;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>預排換班查詢作業(預排換班設定)
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020104M0A extends NewDispatchAction {

	public EHF020104M0A() {

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
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
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
	public ActionForward editDataForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		try{
			String arrid[] = request.getParameterValues("checkId");
			if ((arrid==null?false:!arrid[0].equals(""))){
				return mapping.findForward("redirectEDIT");
			}else{
				request.setAttribute("MSG","請選取要修改的資料!!");
				return queryDatas(mapping,form,request,response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return queryDatas(mapping,form,request,response);
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
			EHF020104M0F Form = (EHF020104M0F) form;
			ArrayList list = new ArrayList();
			this.generateSelectBox(conn, form, request);
			
			Form.setEHF020104T0_01("");  //初始區間
			Form.setEHF020104T0_02("");  //初始區間
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "query");
			request.setAttribute("collection", "show");
			
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
	
	/**
	 * 查詢資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020104M0F Form = (EHF020104M0F) form;

		try {
			conn = tools.getConnection("SPOS");
			generateSelectBox(conn, Form, request);
			request.setAttribute("action", "queryDataForm");
			ActionErrors lc_errors = new ActionErrors();
			
			if (lc_errors.isEmpty()) {
				ArrayList list = new ArrayList();
				
				String sql = "" +
				" SELECT EHF020104T0_01, DATE_FORMAT(EHF020104T0_02, '%Y/%m/%d') AS EHF020104T0_02, EHF020104T0_05, " +
				" IFNULL(DATE_FORMAT(EHF020104T0_04, '%Y/%m/%d'), '') AS EHF020104T0_04, " +
				" USER_UPDATE, " +
				" CASE EHF020104T0_03 WHEN '01' THEN '填寫中' WHEN '02' THEN '已確認' WHEN '03' THEN '已執行完成' END AS EHF020104T0_03 " +
				" FROM EHF020104T0 " +
				" WHERE 1=1 " +
				" AND EHF020104T0_06 = '"+getLoginUser(request).getCompId()+"' " ;  //公司代碼
				
				if(!"".equals(Form.getEHF020104T0_01()) && Form.getEHF020104T0_01() != null 
				   && !"".equals(Form.getEHF020104T0_02()) && Form.getEHF020104T0_02() !=null ){
					sql += " AND EHF020104T0_02 BETWEEN '"+Form.getEHF020104T0_01()+"' AND '"+Form.getEHF020104T0_02()+"' " ;  //日期區間  
				}else if(!"".equals(Form.getEHF020104T0_01()) && Form.getEHF020104T0_01() != null){
					sql += " AND EHF020104T0_02 >= '"+Form.getEHF020104T0_01()+"' ";
				}else if(!"".equals(Form.getEHF020104T0_02()) && Form.getEHF020104T0_02() != null){
					sql += " AND EHF020104T0_02 <= '"+Form.getEHF020104T0_02()+"' ";
				}
				
				sql += " ORDER BY EHF020104T0_02 DESC, EHF020104T0_01 DESC ";//LIMIT 20 ";

				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				if(rs.next()){
					
					rs.beforeFirst();
					
					while(rs.next()){
					
						EHF020104M0F bean = new EHF020104M0F();
						bean.setEHF020104T0_01(rs.getString("EHF020104T0_01"));  //預排換班序號
						bean.setEHF020104T0_02(rs.getString("EHF020104T0_02"));  //換班日期
						bean.setEHF020104T0_03(rs.getString("EHF020104T0_03"));  //狀態
						bean.setEHF020104T0_04(rs.getString("EHF020104T0_04")); //執行日期
						bean.setEHF020104T0_05(rs.getString("EHF020104T0_05"));  //備註
						bean.setUSER_UPDATE(rs.getString("USER_UPDATE"));  //最後修改人員
//						bean.setDATE_UPDATE(tools.DateToChineseDate(rs.getTimestamp("DATE_UPDATE")));  //最後異動日期
						
						list.add(bean);
					}
					
					request.setAttribute("MSG",request.getAttribute("MSG")==null?"查詢成功!!":request.getAttribute("MSG")+"  查詢成功!!");
				}else{
					request.setAttribute("MSG",request.getAttribute("MSG")==null?"查無資料!!":request.getAttribute("MSG")+"  查無資料!!");
					//request.setAttribute("MSG","查無資料!!");
				}
				
				request.setAttribute("Form2Datas",list);
				
				rs.close();
				stmt.close();
				FormUtils.setFormDisplayMode(request, form, "edit");
				request.setAttribute("button", "query");
				request.setAttribute("collection", "show");
				
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

	public ActionForward delData(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		EHF020104M0F Form = (EHF020104M0F) form;
		String sql = "" ;
		String arrid[] = request.getParameterValues("checkId");
    	if ((arrid==null?false:!arrid[0].equals(""))){
    		Form.setEHF020104T0_01(arrid[0]);  //預排換班序號
		}else{
			request.setAttribute("MSG", "請先選擇一筆要刪除的資料!!");
			return queryDatas(mapping, form, request, response);
		}
		
		try {
			conn = tools.getConnection("SPOS");
			request.setAttribute("action", "delData");
			ActionErrors lc_errors = new ActionErrors();
			
			/*sql = "" +
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
				" FROM EHF020900T0 LEFT JOIN EHF020104T0 ON " +
				" EHF020900T0_M2 BETWEEN DATE_FORMAT(EHF020104T0_02, '%Y/%m') AND DATE_FORMAT(EHF020104T0_02, '%Y/%m')" +
				" AND EHF020900T0_04=EHF020104T0_06" +
				" WHERE 1=1 " +
				" AND EHF020104T0_01 = '"+Form.getEHF020104T0_01()+"' " +
			    " AND EHF020104T0_06 = '"+getLoginUser(request).getCompId()+"' " +  //公司代碼
				//" AND EHF020900T0_M2 BETWEEN '"+2013/04+"' AND '"+2013/04+"'"+	//考勤年月
				" AND EHF020900T0_04 = '"+getLoginUser(request).getCompId()+"'" +//公司代碼
				" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤
			 
			 
			Statement stmt_new = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_new = stmt_new.executeQuery(sql);
			
			while(rs_new.next()){
				if("02".equals(rs_new.getString("EHF020900T0_02"))||"03".equals(rs_new.getString("EHF020900T0_02")))
					lc_errors.add("",new ActionMessage("考勤已確認，不可換班!!"));
				request.setAttribute("MSG", "考勤已確認，不可刪除!!");
			}
			rs_new.close();
			stmt_new.close();*/
			if (lc_errors.isEmpty()) {
					
					String comp_id = getLoginUser(request).getCompId();
				
					Statement stmt = conn.createStatement();
					ResultSet rs = null;
					 sql = "" ;
					sql = " SELECT * FROM EHF020104T0 " +
						  " WHERE 1=1 " +
						  " AND EHF020104T0_01 = "+Form.getEHF020104T0_01()+" " +  //預排換班序號
						  " AND EHF020104T0_06 = '"+comp_id+"' ";				
					rs = stmt.executeQuery(sql);
					
					if(rs.next()){
						
						//判斷預排換班處理狀態
						if("01".equals(rs.getString("EHF020104T0_03"))){
							//預排換班處理狀態 = '01', 才可以刪除
							
							//取得預排換班明細資料
							List Detail_List = this.getDetails(conn, Form.getEHF020104T0_01(), comp_id);
							
							Form.setEHF020104C(Detail_List);
							
							//產生預排換班處理元件
							EMS_WorkScheduleControl ems_wsc_tools = EMS_WorkScheduleControl.getInstance();
							
							//執行刪除預排換班程式
							ems_wsc_tools.delSystem(conn, Form.getEHF020104C(), getLoginUser(request).getUserId(), getLoginUser(request).getCompId());
							
							//刪除明細資料
							sql = " DELETE FROM EHF020104T1 " +
							  	  " WHERE 1=1 " +
							  	  " AND EHF020104T1_01 = "+Form.getEHF020104T0_01()+" " +  //預排換班序號
							  	  " AND EHF020104T1_06 = '"+comp_id+"' ";  //公司別條件
							stmt.execute(sql);	
							
							//刪除表頭資料
							sql = " DELETE FROM EHF020104T0 " +
								  " WHERE 1=1 " +
								  " AND EHF020104T0_01 = "+Form.getEHF020104T0_01()+" " +  //預排換班序號
								  " AND EHF020104T0_06 = '"+comp_id+"' ";  //公司別條件
						
							stmt.execute(sql);
							
							conn.commit();
							request.setAttribute("MSG", "刪除成功");
						}else{
							//預排換班處理狀態 >= '01', 不可刪除
							//顯示不可刪除的錯誤訊息
							request.setAttribute("MSG", "處理狀態不是'填寫中', 資料不可刪除!!");
						}
						
					}else{
						request.setAttribute("MSG", "刪除失敗，選取的資料不存在!!");
					}
					
					rs.close();
					stmt.close();
								
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
	 * 取得預排換班明細資料
	 * @param conn
	 * @param form_no
	 * @param comp_id
	 * @return
	 */
	private List getDetails(Connection conn, String form_no,
			String comp_id) {
		// TODO Auto-generated method stub
		
		List EHF020104T1_DETAIL = new ArrayList();
		
		try{
			String sql = "" +
			" SELECT EHF020104T1_01, EHF020104T1_02, EHF020104T1_03, EHF020104T1_04, EHF020104T1_05, EHF020104T1_06 " +
			" FROM EHF020104T1 " +
			" WHERE 1=1 " +
			" AND EHF020104T1_01 = '"+Integer.parseInt(form_no)+"' " +
			" AND EHF020104T1_06 = '"+comp_id+"' ";
			
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				EHF020104M0F bean = new EHF020104M0F();
				
				bean.setEHF020104T1_01(rs.getString("EHF020104T1_01"));	 //預排換班序號
				bean.setEHF020104T1_02(rs.getString("EHF020104T1_02"));  //部門代號
				bean.setEHF020104T1_03(rs.getString("EHF020104T1_03"));  //員工工號
				bean.setEHF020104T1_04(String.valueOf(rs.getInt("EHF020104T1_04")));  //舊班別
				bean.setEHF020104T1_05(String.valueOf(rs.getInt("EHF020104T1_05")));  //新班別
				bean.setCHECKED(true);
				
				EHF020104T1_DETAIL.add(bean);
				
			}
			
			rs.close();
			stmt.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return EHF020104T1_DETAIL;
	}
	
}
