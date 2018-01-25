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
import java.util.Date;
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
import com.spon.ems.com.forms.EHF000300M0F;
import com.spon.ems.com.forms.EHF000600M0F;
import com.spon.ems.com.models.EHF000300;
import com.spon.ems.com.models.EHF000600;
import com.spon.ems.vacation.tools.EMS_CaculateVacationByCal;
import com.spon.ems.vacation.tools.EMS_CheckVacation;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.flow.tools.SPON_ParticularFlowForPWSUP_Tools;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.sc.forms.SC003F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
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
public class EHF000600M1A extends EditAction {
	
	private EMS_ACCESS ems_access;
	private EMS_FLOW ems_flow;
	
	public EHF000600M1A(){
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
		EHF000600M0F Form = (EHF000600M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		
		try{
			//建立EHF000600元件
			EHF000600 ehf000600 = new EHF000600(comp_id);
			
			//規則編號
			String UID = Form.getEHF000600T0_01();
			
			
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			
			dataMap.put("EHF000600T0_01", Form.getEHF000600T0_01()); //規則編號
			dataMap.put("EHF000600T0_02", Form.getEHF000600T0_02()); //規則名稱
			dataMap.put("EHF000600T0_03", Form.getEHF000600T0_03()); //規則內容簡述
			//dataMap.put("EHF000600T0_06", Form.getEHF000600T0_06()==null?"":Form.getEHF000600T0_06());
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000600.addData(dataMap);
			
			//關閉EHF000600元件
			ehf000600.close();
			
			//取出KEY_ID
			Form.setEHF000600T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000600T0_01());
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
		EHF000600M0F Form = (EHF000600M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String Attached_File = "";
		String Attached_File_Name = "";
		
		try{
			//建立EHF010109元件
			EHF000600 ehf000600 = new EHF000600();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF000600T0_01", key[0]);  
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf000600.queryEditData(queryCondMap);
			
			//關閉EHF010109元件
			ehf000600.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setEHF000600T0_01((String)dataMap.get("EHF000600T0_01"));  //規則編號
				Form.setEHF000600T0_02((String)dataMap.get("EHF000600T0_02"));  //規則名稱
				Form.setEHF000600T0_03((String)dataMap.get("EHF000600T0_03"));  //規則內容簡述
				Form.setHR_CompanySysNo((String) dataMap.get("HR_CompanySysNo"));
				//Create Information
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));//建立人員
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));//修改人員
				Form.setVERSION((Integer)dataMap.get("VERSION"));//版本
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));//最後異動日期
				//Form.setHR_LastUpdateDate(ems_tools.convertADDatetimeToStrng((Date)dataMap.get("HR_LastUpdateDate")));//修改日期
//				Form.setHR_LastUpdateDate((String)dataMap.get("HR_LastUpdateDate"));
				if(!"".equals((String)dataMap.get("EHF000600T0_04")) && (String)dataMap.get("EHF000600T0_04") != null ){
					String filename = (String)dataMap.get("EHF000600T0_04");
					request.setAttribute("Attached_File_Name", filename);
					request.setAttribute("Attached_File", "yes");
					Attached_File = "yes";
					Attached_File_Name = filename;
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
		EHF000600M0F Form = (EHF000600M0F) form;
		
		try{
			//建立EHF010109元件
			EHF000600 ehf000600 = new EHF000600();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap.put("EHF000600T0_01", Form.getEHF000600T0_01());
			dataMap.put("EHF000600T0_02", Form.getEHF000600T0_02());
			dataMap.put("EHF000600T0_03", Form.getEHF000600T0_03());

			//dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf000600.saveData(dataMap);
			
			//關閉EHF010109元件
			ehf000600.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000600T0_01());
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
		
		EHF000600M0F Form = (EHF000600M0F) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF000600T0_01());
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
		
		EHF000600M0F Form = (EHF000600M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF000600T0_01());
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
		EHF000600M0F Form = (EHF000600M0F) form;
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
			
		
		String Rule_number = null;
		//建立資料庫連線
    	if (conn == null) {
    		try {
    			conn = tools.getConnection("SPOS");
    		} catch (SQLException e2) {
    			e2.printStackTrace();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		}
    	
    	try {

			int DataCount =0;
			String sql = "SELECT EHF000600T0_01 FROM EHF000600T0 ORDER BY EHF000600T0_01 DESC LIMIT 1" ;
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);//執行executeQuery把資料sql內的拿出來查詢
			if(rs.next()){//針對一筆來拿
				//把資料拿出來
				Rule_number=rs.getString("EHF000600T0_01");
			}
			rs.close();
			stmt.close();
		;}catch (Exception e) {
			System.out.println(e);
			} 
		
		if (Rule_number != null && !Rule_number.equals("")) {
			
			}
		
		else{
			Rule_number = "RULE00000";
		}
		
		String[] RuleNumber = Rule_number.split("E");
		
		int rn = Integer.parseInt(RuleNumber[1]);
		
		rn += 1;
		
		Rule_number = "RULE" + String.format("%05d", rn);		
		
//		System.out.println(Rule_number);
		
		Form.setEHF000600T0_01(Rule_number);
		
		
		return (ActionForm)Form;	}

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
		
		try{
			List listEHF000600T0_11_MM = new ArrayList();
			DecimalFormat df=new DecimalFormat("00");
			for (int i = 0; i <= 23; i++) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF000600T0_11_MM.add(bform);
			}
			request.setAttribute("listEHF000600T0_11_HH", listEHF000600T0_11_MM);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
		try{
			List listEHF000600T0_11_MM = new ArrayList();
			DecimalFormat df=new DecimalFormat("00");
			for (int i = 0; i < 60; i=i+5) {
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF000600T0_11_MM.add(bform);
			}
			request.setAttribute("listEHF000600T0_11_MM", listEHF000600T0_11_MM);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生假別項目
		try {
			List datas = new ArrayList();
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			String sql = " SELECT EHF020100T0_03 , EHF020100T0_04 FROM EHF020100T0 " +
					     " WHERE 1=1 " +
					     " AND EHF020100T0_08 = '"+getLoginUser(request).getCompId()+"' ";
			
			ResultSet rs=stmt.executeQuery(sql);
			
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			datas.add(bform);
			
			while(rs.next()){
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("EHF020100T0_03"));
				bform.setItem_value(rs.getString("EHF020100T0_04"));
				datas.add(bform);	
				
			}
			
			rs.close();
			stmt.close();
			
			request.setAttribute("EHF000600T0_14_list", datas);
							
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 上傳附加檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward uploadEHF000600M1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF000600M0F Form = (EHF000600M0F) form;
		
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
			 			 " SELECT * FROM EHF000600T0 " +
			 			 " WHERE 1=1 " +
			 			 " AND EHF000600T0_01 = '"+Form.getEHF000600T0_01()+"' " +
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
				temp = system_root+"ems/uploadfiles/rules/";
				
//				check_Path_Exist(temp , rs.getString("EHF000600T0_01")); //員工工號???
				
				try{
					File file=new File(temp);
					if(!file.exists())
						file.mkdirs();
					
				}catch(Exception e){
					//記錄錯誤訊息
					e.printStackTrace();
				}
				
				temp += /*rs.getString("EHF000600T0_03")+"/"+*/Form.getUPLOADFILE().getFileName();
				
				String filepath = /*rs.getString("EHF000600T0_03")+"/"+*/Form.getUPLOADFILE().getFileName();
				
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
				" UPDATE ehf000600t0 SET EHF000600T0_04=?, USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE EHF000600T0_01 = '"+Form.getEHF000600T0_01()+"' " +
				" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, filepath );  //附加檔案路徑
				indexid++;
				p6stmt.setString(indexid, getLoginUser(request).getUserName() );  //修改人員
				indexid++;
				
				//System.out.println("sql ==> "+ p6stmt.getQueryFromPreparedStatement());
				
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
			System.out.println("FILEUPLOAD.uploadEHF000600M1() " + e);
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
		EHF000600M0F Form = (EHF000600M0F) form;

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
			" SELECT EHF000600T0.* " +
			" FROM EHF000600T0 " +
			" WHERE 1=1 " +
			" AND EHF000600T0_01 = '"+Form.getEHF000600T0_01()+"' " +
			" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";	
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				String filename = rs.getString("EHF000600T0_04");				
//				String filepath = request.getSession().getServletContext().getRealPath("/")+"ems/uploadfiles/rules/";
				String system_root = "";
//				String temp = "";
				//判斷作業系統
				String osName = System.getProperty("os.name");
				if ("Linux".equals(osName)) {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_LINUX");
				} else {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_WINDOWS");
				}
				String filepath = system_root+"ems/uploadfiles/rules/";
				filepath += rs.getString("EHF000600T0_04");				
				java.net.URLEncoder.encode(filepath, "UTF-8");				
				File fout = new File(filepath);				
				//開啟檔案下載視窗
				response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));//可能是0,因直面去了一截
				ServletOutputStream ouputStream;
				try {
					InputStream in = new FileInputStream(fout);
					ouputStream = response.getOutputStream();
					int bit = 0;
					byte [] bits=new byte[4096]; //Why 4096
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
		EHF000600M0F Form = (EHF000600M0F) form;

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
			" SELECT EHF000600T0.* " +
			" FROM EHF000600T0 " +
			" WHERE 1=1 " +
			" AND EHF000600T0_01 = '"+Form.getEHF000600T0_01()+"' " +
			" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";	
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){				
//				String filepath = request.getSession().getServletContext().getRealPath("/")+"ems/uploadfiles/rules/";
				String system_root = "";
				//判斷作業系統
				String osName = System.getProperty("os.name");
				if ("Linux".equals(osName)) {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_LINUX");
				} else {
					system_root = tools.getSysParam(conn, getLoginUser(request).getCompId(), "REPORT_PATH_WINDOWS");
				}
				String filepath = system_root+"ems/uploadfiles/rules/";				
				filepath += rs.getString("EHF000600T0_04");				
				java.net.URLEncoder.encode(filepath, "UTF-8");				
				File fout = new File(filepath);				
				if(fout.exists()){				
					//刪除附加檔案
					fout.delete();					
					String upsql = "" +
					" UPDATE EHF000600T0 " +
					" SET EHF000600T0_04 = '' " +
					" WHERE 1=1 " +
					" AND EHF000600T0_01 = '"+Form.getEHF000600T0_01()+"' " +
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
	
	/**
	 * 請假單呈核動作 -- 緩衝區
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */	
	public ActionForward submitForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return submitDatas(mapping, form, request, response);
	}
	
	/**
	 * 請假單 -- 呈核動作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward submitDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		EHF000600M0F Form = (EHF000600M0F) form;
		String comp_id = getLoginUser(request).getCompId();  //公司代碼
		String user_name = getLoginUser(request).getUserName();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			ActionErrors lc_errors = new ActionErrors();
			
			/*//判斷呈核時是否檢核假別資訊 edit by joe 2012/05/16
			if("ON".equals(tools.getSysParam(conn, comp_id, "VACATION_CHECK_BY_VA_TYPE"))){
				EMS_CheckVacation checkva_tools = (EMS_CheckVacation) EMS_CheckVacation.getInstance();
				//請假單呈核進行檢核
				checkva_tools.checkVacation(lc_errors, request, conn, Form.getEHF000600T0_01(), comp_id);
			}*/
			
			try {				
				String sql = "" +
				" SELECT EHF020900T0_01, EHF020900T0_U, EHF020900T0_M2,EHF020900T0_02" +
				" FROM EHF020900T0 " +
				" WHERE 1=1 " +
				" AND EHF020900T0_M2 BETWEEN DATE_FORMAT('"+Form.getEHF000600T0_09()+"', '%Y/%m') AND DATE_FORMAT('"+Form.getEHF000600T0_10()+"', '%Y/%m')"+	//考勤年月
				" AND EHF020900T0_04 = '"+request.getAttribute("compid")+"'" +//公司代碼
				" AND (EHF020900T0_02 = '02' OR EHF020900T0_02 = '03')"; //已經確認考勤

		    	Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = stmt.executeQuery(sql);
				
				while(rs.next()){
					lc_errors.add("EHF000600T0_09",new ActionMessage(""));
					lc_errors.add("EHF000600T0_10",new ActionMessage(""));
					request.setAttribute("ErrMSG",
							(request.getAttribute("ErrMSG")==null?"":request.getAttribute("ErrMSG")+"<br>")+
							"考勤已確認，不可呈核該筆請假單!!請再次確認!!");
					
				}
				rs.close();
				stmt.close();
				
			} catch (Exception e) {
				
			}
			
			if (lc_errors.isEmpty()){
				
				//是否啟用緊急流程
				String emergency_flow = tools.getSysParam(conn, comp_id, "EMERGENCY_FLOW_FLAG");
				
				if("Y".equals(emergency_flow)){
				
					EHF000600 ehf000600 = new EHF000600();
				
					//建立查詢資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF000600T0_01", Form.getEHF000600T0_01());  // 
				
					if(ehf000600.submitDatas(queryCondMap, comp_id, user_name)){
						//EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						//setVaSql.updataVacationData(queryCondMap, comp_id);
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核失敗!!");
					}
				
					ehf000600.close();
				
				}else{
					//建立FLOW元件
					SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
					
					//取得當前使用者資訊
					SC003F userform = super.userform(request);
					
					HR_TOOLS hr_tools = new HR_TOOLS();
					
					Map empMap = hr_tools.getEmpNameMap(comp_id);
					
					hr_tools.close();
					
					flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF000600M0", Form.getEHF000600T0_03(), Form.getEHF000600T0_04(), comp_id);
					
					//組成 condMap
					Map condMap = new HashMap();
					condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
					
					//執行FLOW submit
					spon_flow_tools.submit(flow_no, 
										   Form.getEHF000600T0_01(), 
										   condMap,  //條件資料Map
										   userform.getSC0030_01(), 
										   userform.getSC0030_14());
					
					//取得當前待簽核LOG
					Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF000600T0_01(), comp_id);
					
					this.updateFormStatus(spon_flow_tools, request, Form.getEHF000600T0_01(), current_sign_map, flow_no, "submit");
					
				}
				
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核失敗!!");
				request.setAttribute("Form1Datas", Form);
				this.generateSelectBox(conn, Form, request);
				return mapping.findForward("success");
			}			
			
		}catch(Exception e){
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
	
	//從緊急流程抽單
	public ActionForward signFormEmergency(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		String emergency_flow_action = request.getParameter("emergency_flow_action");
		return signDatasEmergency(mapping, form, request, response, emergency_flow_action);
	}
	
	public ActionForward signDatasEmergency(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
			HttpServletResponse response, String flow_action){
		
		BaseFunction base_tools = new BaseFunction();
		
		EHF000600M0F Form = (EHF000600M0F) form;
		String fromKey = Form.getEHF000600T0_01()+"";
		String agent_sign_table = "EHF000600T0";	//簽核table
		String agent_sign_form_status = "EHF000600T0_16";	//簽核表單狀態
		String agent_sign_form_status_desc = "EHF000600T0_16_DESC";	//簽核表單狀態說明
		String agent_sign_form_status_code = ""; //flow_action
		String agent_sign_form_status_code_desc = ""; //flow_action說明
		String agent_sign_form_recieptNo = "EHF000600T0_01";	//簽核表單單號
		String agent_sign_compId = getLoginUser(request).getCompId();	//簽核公司代碼
		String compId = "HR_CompanySysNo";
		
		Connection conn = null;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			if("0001".equals(flow_action)){	//呈核
				agent_sign_form_status_code = "0002";
				agent_sign_form_status_code_desc = "呈核";
			}else if("0002".equals(flow_action)){	//核准
				agent_sign_form_status_code = "0006";
				agent_sign_form_status_code_desc = "完成";	//因為人資核准就算完成了，所以狀態直接設定為完成
			}else if("0003".equals(flow_action)){	//駁回
				agent_sign_form_status_code = "0005";
				agent_sign_form_status_code_desc = "駁回";
			}else if("0013".equals(flow_action)){	//抽單
				agent_sign_form_status_code = "0008";
				agent_sign_form_status_code_desc = "抽單";
			}
			
			String sql = " UPDATE " + agent_sign_table +
			" SET " + agent_sign_form_status + "='" + agent_sign_form_status_code + "'," +
			" " + agent_sign_form_status_desc + "='" + agent_sign_form_status_code_desc + "' " +
			" WHERE " + agent_sign_form_recieptNo + "='" + fromKey + "' " +
			" AND " + compId + "='" + agent_sign_compId + "'";
	
			base_tools.executeSQL(sql);
	
			base_tools.commit();
			
			//若是執行完成的動作，要回寫請假時數到年度休假資料中
			if("0006".equals(agent_sign_form_status_code)){
				
				//建立查詢資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF000600T0_01", Form.getEHF000600T0_01());  //表單編號
				
				EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
				setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());
			}	
			
		}catch (Exception e) {
			System.out.println(e);
			base_tools.close();
			base_tools.rollback();
			request.setAttribute("MSG", agent_sign_form_status_code_desc+"失敗");
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		base_tools.close();
		request.setAttribute("MSG", agent_sign_form_status_code_desc+"成功");
		
		return queryForm(mapping, form, request, response);
	}
	
	/**
	 * 簽核動作 -- 緩衝區
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward signForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		String flow_action = request.getParameter("FLOW_ACTION");
		
		try{
			switch (Integer.parseInt(flow_action)){
				
				case 4:
					return approve(mapping, form, request, response);
				
				case 5:
					return reject(mapping, form, request, response);
					
				case 8:
					return cancel(mapping, form, request, response);
			
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 核准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward approve(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		EHF000600M0F Form = (EHF000600M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
							
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF000600M0", Form.getEHF000600T0_03(), Form.getEHF000600T0_04(), comp_id);
				
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF000600T0_01(), comp_id);
				
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF000600T0_01(), current_sign_map, flow_no, "approve");
				
			//組成 condMap                                                                                                                                                                                                                                                                                                    
			Map condMap = new HashMap();
				
			//執行FLOW approve
			spon_flow_tools.approve(flow_no, 
									Form.getEHF000600T0_01(), 
									condMap,  //條件資料Map 
									Form.getSIGN_COMMENT(),  //簽核意見
								    userform.getSC0030_01(), 
								    userform.getSC0030_14());
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准失敗!!");
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
	 * 駁回
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward reject(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		EHF000600M0F Form = (EHF000600M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
			
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF000600M0", Form.getEHF000600T0_03(), Form.getEHF000600T0_04(), comp_id);
			
			//組成 condMap
			Map condMap = new HashMap();
			//condMap.put("SUPPLY_USER_ID", Form.getDATA02());
			
			//執行FLOW reject
			spon_flow_tools.reject(flow_no, 
								   Form.getEHF000600T0_01(), 
								   condMap,  //條件資料Map
								   Form.getSIGN_COMMENT(),  //簽核意見
								   userform.getSC0030_01(), 
								   userform.getSC0030_14());
			
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF000600T0_01(), comp_id);
			
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF000600T0_01(), current_sign_map, flow_no, "reject");
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
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
	 * 抽單
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward cancel(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		EHF000600M0F Form = (EHF000600M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			
			hr_tools.close();
			
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF000600M0", Form.getEHF000600T0_03(), Form.getEHF000600T0_04(), comp_id);
			
			//執行FLOW reject
			spon_flow_tools.cancel(flow_no, 
								   Form.getEHF000600T0_01(), 
								   userform.getSC0030_01(),  //抽單人
								   Form.getSIGN_COMMENT(),  //簽核意見
								   userform.getSC0030_01(), 
								   userform.getSC0030_14());
			
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF000600T0_01(), comp_id);
			
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF000600T0_01(), current_sign_map, flow_no, "cancel");
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"抽單失敗!!");
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
	 * 自動核准
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward AutoApprove(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) {
		
		Connection conn = null;
		EHF000600M0F Form = (EHF000600M0F) form;
		String comp_id = getLoginUser(request).getCompId();
		String flow_no = "";
		int auto_flow_site_sn = 0;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//建立FLOW元件
			SPON_ParticularFlowForPWSUP_Tools spon_flow_tools = new SPON_ParticularFlowForPWSUP_Tools();
			
			//取得當前使用者資訊
			SC003F userform = super.userform(request);
							
			flow_no = ems_flow.getFlowNo(conn, getLoginUser(request), "EHF000600M0", Form.getEHF000600T0_03(), Form.getEHF000600T0_04(), comp_id);
			
			auto_flow_site_sn = this.getAutoFlowSiteSN(conn, flow_no, comp_id);						
				
			//取得當前待簽核LOG
			Map current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF000600T0_01(), comp_id);
			
			if("填寫中".equals((String)current_sign_map.get("CURRENT_SIGN_FORM_STATUS_NAME"))){
				//尚未呈核過，申請人是主管流程角色
				Map condMap = new HashMap();
				condMap.put("FORM_OPEN_USER_ID", userform.getSC0030_01());
				
				//執行FLOW submit
				spon_flow_tools.submit(flow_no, 
									   Form.getEHF000600T0_01(), 
									   condMap,  //條件資料Map
									   userform.getSC0030_01(), 
									   userform.getSC0030_14());
				
				//取得當前待簽核LOG
				Map auto_current_sign_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF000600T0_01(), comp_id);
				
				this.updateFormStatus(spon_flow_tools, request, Form.getEHF000600T0_01(), auto_current_sign_map, flow_no, "submit");
			}
			
			Map auto_current_approve_map = spon_flow_tools.getCurrentFlowFormStatus(flow_no, Form.getEHF000600T0_01(), comp_id);
				
			this.updateFormStatus(spon_flow_tools, request, Form.getEHF000600T0_01(), auto_current_approve_map, flow_no, "approve");
				
			//組成 condMap                                                                                                                                                                                                                                                                                                    
			Map condMap = new HashMap();
				
			//執行FLOW approve
			spon_flow_tools.AutoApprove(flow_no, 
										Form.getEHF000600T0_01(), 
										condMap,  //條件資料Map 
										"系統自動核准",  //簽核意見
										userform.getSC0030_01(), 
										userform.getSC0030_14(),
										auto_flow_site_sn);
			
		}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"自動核准失敗!!");
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
	 * 更新表單狀態
	 * @param spon_flow_tools
	 * @param request
	 * @param form_no
	 * @param current_sign_map
	 * @param action
	 * @param action 
	 */
	private void updateFormStatus(SPON_ParticularFlowForPWSUP_Tools spon_flow_tools, HttpServletRequest request,
			String form_no, Map current_sign_map, String flow_no, String action) {
		// TODO Auto-generated method stub
		
		String comp_id = getLoginUser(request).getCompId();
		String user_name = getLoginUser(request).getUserName();
		
		try{
			if("submit".equals(action)){
				
				EHF000600 ehf000600 = new EHF000600();
				
				//建立查詢資料Map
				Map<String, String> queryCondMap = new HashMap();					
				queryCondMap.put("EHF000600T0_01", form_no);  //表單編號
				queryCondMap.put("EHF000600T0_16", (String)current_sign_map.get("BEFORE_SIGN_FORM_STATUS_NO"));	//更改前表單狀態代碼
				queryCondMap.put("EHF000600T0_16_DESC", (String)current_sign_map.get("BEFORE_SIGN_FORM_STATUS_NAME")); //更改前表單狀態名稱
				
				if(ehf000600.submitDatas(queryCondMap, comp_id, user_name)){
					//不是緊急流程就要寫入流程空表單編號
					ehf000600.updateFlowNo(form_no, flow_no, comp_id);
					
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"呈核失敗!!");
				}
				
				ehf000600.close();
				
			}else if("approve".equals(action)){
				
				boolean next_flow_station_flag = spon_flow_tools.isNextFlowStation(current_sign_map, comp_id);
				
				EHF000600 ehf000600 = new EHF000600();
				
				if(next_flow_station_flag){
					//有下一站
					//建立資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF000600T0_01", form_no);  //表單編號
					queryCondMap.put("EHF000600T0_16", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
					queryCondMap.put("EHF000600T0_16_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
					
					if(ehf000600.submitFlowDatas(queryCondMap, comp_id, user_name)){
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"核准失敗!!");
					}
					
				}else{
					//無下一站
					//建立資料Map
					Map queryCondMap = new HashMap();					
					queryCondMap.put("EHF000600T0_01", form_no);  //表單編號
					queryCondMap.put("EHF000600T0_16", (String)current_sign_map.get("FLOW_SITE_STATUS_NO"));	//FLOW狀態號碼
					queryCondMap.put("EHF000600T0_16_DESC", (String)current_sign_map.get("FLOW_SITE_STATUS_NAME")); //FLOW狀態名稱
					
					if(ehf000600.submitFlowDatas(queryCondMap, comp_id, user_name)){						
						//若是執行完成的動作，要回寫請假時數到年度休假資料中												
						EMS_setVacationUpdateSQL setVaSql = EMS_setVacationUpdateSQL.getInstance();
						setVaSql.updataVacationData(queryCondMap, getLoginUser(request).getCompId());						
						
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成成功!!");
					}else{
						request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"完成失敗!!");
					}
					
				}
				
				ehf000600.close();
				
			}else if("reject".equals(action)){
				
				EHF000600 ehf000600 = new EHF000600();
				
				//建立資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF000600T0_01", form_no);  //表單編號
				queryCondMap.put("EHF000600T0_16", "0005");	//FLOW狀態號碼
				queryCondMap.put("EHF000600T0_16_DESC", "駁回"); //FLOW狀態名稱
				
				if(ehf000600.submitFlowDatas(queryCondMap, comp_id, user_name)){
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"駁回失敗!!");
				}
				
				ehf000600.close();
				
			}else if("cancel".equals(action)){
				
				EHF000600 ehf000600 = new EHF000600();
				
				//建立資料Map
				Map queryCondMap = new HashMap();					
				queryCondMap.put("EHF000600T0_01", form_no);  //表單編號
				queryCondMap.put("EHF000600T0_16", "0008");	//FLOW狀態號碼
				queryCondMap.put("EHF000600T0_16_DESC", "抽單"); //FLOW狀態名稱
				
				if(ehf000600.submitFlowDatas(queryCondMap, comp_id, user_name)){
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"抽單成功!!");
				}else{
					request.setAttribute("MSG", (request.getAttribute("MSG")==null?"":request.getAttribute("MSG")+"<br>")+"抽單失敗!!");
				}
				
				ehf000600.close();
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * 自動核准到哪一站
	 * @param conn
	 * @param flow_no
	 * @param comp_id
	 * @return
	 */
	private int getAutoFlowSiteSN(Connection conn, String flow_no, String comp_id) {
		// TODO Auto-generated method stub
		
		int auto_flow_site_sn = 0;
		
		try{
			if("HC_VACATION_FLOW".equals(flow_no)){
				auto_flow_site_sn = 5;
			}else if("HC_M_VACATION_FLOW".equals(flow_no)){
				auto_flow_site_sn = 4;
			}else if("HC_M1_VACATION_FLOW".equals(flow_no)){
				auto_flow_site_sn = 3;
			}else if("HC_M2_VACATION_FLOW".equals(flow_no)){
				auto_flow_site_sn = 2;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
		return auto_flow_site_sn;
	}

}
