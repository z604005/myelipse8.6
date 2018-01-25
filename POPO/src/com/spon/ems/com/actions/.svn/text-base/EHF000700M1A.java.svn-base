package com.spon.ems.com.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.com.forms.EHF000700M0F;
import com.spon.ems.com.models.EHF000700;
import com.spon.ems.vacation.tools.EMS_CaculateVacationByCal;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.flow.tools.SPON_ParticularFlowForPWSUP_Tools;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.EMS_FLOW;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action>請假單申請
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午10:32:34
 */
public class EHF000700M1A extends EditAction {
	
	private EMS_ACCESS ems_access;
	private EMS_FLOW ems_flow;
	
	public EHF000700M1A(){
		ems_access = EMS_ACCESS.getInstance();
		ems_flow = EMS_FLOW.getInstance();
	}

	@Override
	protected void cleanAddField(ActionForm form) {
		// TODO Auto-generated method stub

	}

	@Override
	protected boolean executeAddData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF000700M0F Form = (EHF000700M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF000700元件
			EHF000700 ehf000700 = new EHF000700(comp_id);
			
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap.put("EHF000700T0_02", Form.getEHF000700T0_02());
			dataMap.put("EHF000700T0_03", Form.getEHF000700T0_03());
			dataMap.put("EHF000700T0_04", Form.getEHF000700T0_04());
			dataMap.put("EHF000700T0_05", "");

			//設定使用者資訊
			dataMap.putAll(paramMap);
			ehf000700.addData(dataMap);
			
			//關閉EHF000700元件
			ehf000700.close();
			
			//取出KEY_ID
			Form.setEHF000700T0_01(String.valueOf((Integer)dataMap.get("KEY_ID")));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000700T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//新增完成
			chk_flag = true;
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected Map executeQueryEditData(Connection conn, String[] key,
			ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF000700M0F Form = (EHF000700M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String Attached_File = "";
		String Attached_File_Name = "";
		
		try{
			EHF000700 ehf000700 = new EHF000700();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF000700T0_01", key[0]);  //職務系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf000700.queryEditData(queryCondMap);
			
			//關閉EHF000700元件
			ehf000700.close();
			
			if(!dataMap.isEmpty()){
				Form.setEHF000700T0_01(String.valueOf(dataMap.get("EHF000700T0_01")));  
				Form.setEHF000700T0_02((String)dataMap.get("EHF000700T0_02"));  
				Form.setEHF000700T0_03((String)dataMap.get("EHF000700T0_03")); 
				Form.setEHF000700T0_04((String)dataMap.get("EHF000700T0_04"));
				Form.setEHF000700T0_05((String)dataMap.get("EHF000700T0_05"));
				Form.setHR_CompanySysNo((String) dataMap.get("HR_CompanySysNo"));
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));
				Form.setVERSION((Integer)dataMap.get("VERSION"));
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				//Form.setDATE_CREATE((String)dataMap.get("DATE_CREATE"));		
				if(!"".equals((String)dataMap.get("EHF000700T0_05")) && (String)dataMap.get("EHF000700T0_05") != null ){
					String[] filename = ((String)dataMap.get("EHF000700T0_05")).split("/");
					request.setAttribute("Attached_File_Name", filename[1]);
					request.setAttribute("Attached_File", "yes");
					Attached_File = "yes";
					Attached_File_Name = filename[1];
				}else {
					request.setAttribute("Attached_File", "no");
					Attached_File = "no";
					Attached_File_Name = "";
				}
				chk_flag = true;
			}
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return return_map;
	}

	@Override
	protected boolean executeSaveData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF000700M0F Form = (EHF000700M0F) form;
		
		try{			
			EHF000700 ehf000700 = new EHF000700();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			dataMap.put("EHF000700T0_02", Form.getEHF000700T0_02());
			dataMap.put("EHF000700T0_03", Form.getEHF000700T0_03());
			dataMap.put("EHF000700T0_04", Form.getEHF000700T0_04());
			//dataMap.put("EHF000700T0_05", Form.getEHF000700T0_05()); 
			dataMap.putAll(paramMap);
			ehf000700.saveData(dataMap);
			
			//關閉EHF000700元件
			ehf000700.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000700T0_01());
			paramMap.put(super.KEY_ID, key_id);
			
			//儲存完成
			chk_flag = true;
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected void generateKeyId(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF000700M0F Form = (EHF000700M0F) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000700T0_01());
			paramMap.put(super.KEY_ID, key_id);
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
	}

	@Override
	protected String getDelFormKey(ActionForm form, Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF000700M0F Form = (EHF000700M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF000700T0_01());
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return key;
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF000700M0F Form = (EHF000700M0F) form;		
		return (ActionForm)Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 上傳附加檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadEHF000700M1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF000700M0F Form = (EHF000700M0F) form;
		
		//檢查上傳檔案是否存在
		try{
			if("".equals(Form.getUPLOADFILE().getFileName()) || Form.getUPLOADFILE().getFileName() == null 
			   || Form.getUPLOADFILE().getFileSize() <= 0 ){
				
				request.setAttribute("MSG", "檔案上傳發生錯誤，請重新選取附加檔案!!");
				
				return queryForm(mapping, form, request, response);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//建立資料庫連線
		try {
			conn = tools.getConnection("SPOS");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//方法二實體檔案上傳至系統實體路徑
			String sql = "" +
			 			 " SELECT * FROM EHF000700T0 " +
			 			 " WHERE 1=1 " +
			 			 " AND EHF000700T0_01 = '"+Form.getEHF000700T0_01()+"' " +
			 			 " AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				
				String system_root = "";
				String temp = "";
				//判斷作業系統
				String osName = System.getProperty("os.name");
				if ("Linux".equals(osName)) {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_LINUX");
				} else {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_WINDOWS");
				}
				temp = system_root+"ems/uploadfiles/leave/";
				
				check_Path_Exist(temp , rs.getString("EHF000700T0_02"));
				
				temp += rs.getString("EHF000700T0_02")+"/"+Form.getUPLOADFILE().getFileName();
				
				String filepath = rs.getString("EHF000700T0_02")+"/"+Form.getUPLOADFILE().getFileName();
				
				File fout = new File(temp);
				
				try{
					InputStream in = Form.getUPLOADFILE().getInputStream();
					OutputStream out = new FileOutputStream(fout);
					int bit = 0;
					byte [] bits=new byte[4096];
					while ((bit=in.read(bits)) >-1 ) {
						out.write(bits,0,bit);
						Thread.sleep(1);
					}
					out.flush();
					out.close();
					in.close();
				}catch (IOException e) {
					e.printStackTrace();
				}
				
				sql = "" +
				" UPDATE ehf000700t0 SET EHF000700T0_05=?, USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE EHF000700T0_01 = '"+Form.getEHF000700T0_01()+"' " +
				" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, filepath );  //附加檔案路徑
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName() );  //修改人員
				indexid++;
							
				p6stmt.executeUpdate();
				pstmt.close();
				p6stmt.close();
				
				conn.commit();
			}
			rs.close();
			stmt.close();
			request.setAttribute("MSG", "附加檔案上傳成功!!");
			
		}catch (Exception e) {
			request.setAttribute("MSG", "附加檔案上傳失敗!");
			System.out.println("FILEUPLOAD.uploadEHF000700M1() " + e);
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
		
		return queryForm(mapping, form, request, response);
	}
	
	/**
	 * 下載請假單附加檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getAttachedFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;		
		EHF000700M0F Form = (EHF000700M0F) form;

		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{			
			String sql = "" +
			" SELECT EHF000700T0.* , DATE_FORMAT(USER_CREATE, '%Y/%m/%d') AS CREATE_DATE " +
			" FROM EHF000700T0 " +
			" WHERE 1=1 " +
			" AND EHF000700T0_01 = '"+Form.getEHF000700T0_01()+"' " +
			" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";	
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				String[] filename = rs.getString("EHF000700T0_05").split("/");				
//				String filepath = request.getSession().getServletContext().getRealPath("/")+"ems/uploadfiles/leave/";
				String system_root = "";
//				String temp = "";
				//判斷作業系統
				String osName = System.getProperty("os.name");
				if ("Linux".equals(osName)) {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_LINUX");
				} else {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_WINDOWS");
				}
				String filepath = system_root+"ems/uploadfiles/leave/";
				filepath += rs.getString("EHF000700T0_05");				
				java.net.URLEncoder.encode(filepath, "UTF-8");				
				File fout = new File(filepath);				
				//開啟檔案下載視窗
				response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename[1], "UTF-8"));
				ServletOutputStream ouputStream;
				try {
					InputStream in = new FileInputStream(fout);
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
					request.setAttribute("MSG", "檔案下載失敗!!");
					e.printStackTrace();
				}
				
				return null;
			}
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			request.setAttribute("MSG", "檔案下載失敗!!");
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return queryForm(mapping, form, request, response);
	}
	
	/**
	 * 刪除請假單附加檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward delAttachedFile(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EHF000700M0F Form = (EHF000700M0F) form;

		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			String sql = "" +
			" SELECT EHF000700T0.* , DATE_FORMAT(USER_CREATE, '%Y/%m/%d') AS CREATE_DATE " +
			" FROM EHF000700T0 " +
			" WHERE 1=1 " +
			" AND EHF000700T0_01 = '"+Form.getEHF000700T0_01()+"' " +
			" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";	
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){				
//				String filepath = request.getSession().getServletContext().getRealPath("/")+"ems/uploadfiles/leave/";
				String system_root = "";
				//判斷作業系統
				String osName = System.getProperty("os.name");
				if ("Linux".equals(osName)) {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_LINUX");
				} else {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_WINDOWS");
				}
				String filepath = system_root+"ems/uploadfiles/leave/";				
				filepath += rs.getString("EHF000700T0_05");				
				java.net.URLEncoder.encode(filepath, "UTF-8");				
				File fout = new File(filepath);				
				if(fout.exists()){				
					//刪除附加檔案
					fout.delete();					
					String upsql = "" +
					" UPDATE EHF000700T0 " +
					" SET EHF000700T0_05 = '' " +
					" WHERE 1=1 " +
					" AND EHF000700T0_01 = '"+Form.getEHF000700T0_01()+"' " +
					" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";
					
					stmt.executeUpdate(upsql);					
					conn.commit();					
					request.setAttribute("MSG", "附加檔案刪除成功!!");
				}else{
					request.setAttribute("MSG", "附加檔案不存在!!");
				}
			}			
			rs.close();
			stmt.close();		
		}catch(Exception e){
			request.setAttribute("MSG", "附加檔案刪除失敗!!");
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
		return queryForm(mapping, form, request, response);
	}
	

}
