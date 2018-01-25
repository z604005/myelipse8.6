package com.spon.ems.vacation.actions;

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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.abs.actions.EditAction;
import com.spon.ems.vacation.forms.EHF020700M0F;
import com.spon.ems.vacation.models.EHF020700;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * <Action>未打卡單申請作業
 *@author maybe
 *@version 1.0
 *@created 2015/11/19 下午5:22:38
 */
public class EHF020700M1A extends EditAction {
	
	private EMS_ACCESS ems_access;
	
	public EHF020700M1A(){
		ems_access = EMS_ACCESS.getInstance();
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
		EHF020700M0F Form = (EHF020700M0F) form;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			EHF020700 ehf020700 = new EHF020700(comp_id);
			
			String UID = tools.createEMSUID(conn, "EG", "EHF020700T0", "EHF020700T0_01", comp_id);
			
			//建立新增資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			dataMap.put("EHF020700T0_01", UID);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf020700.addData(dataMap);
			
			ehf020700.close();
			
			//取出KEY_ID
			Form.setEHF020700T0_01((String)dataMap.get("KEY_ID"));
			
			//設定 AddDataForm forward 資料
			paramMap.put(super.ADD_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020700T0_01());
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
		EHF020700M0F Form = (EHF020700M0F) form;
		Map return_map = new HashMap();
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String Attached_File = "";
		String Attached_File_Name = "";
		
		try{
			EHF020700 ehf020700 = new EHF020700();
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//建立查詢資料Map
			Map queryCondMap = new HashMap();
			queryCondMap.put("EHF020700T0_01", key[0]);  //職務系統代碼
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			Map dataMap = ehf020700.queryEditData(queryCondMap);
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			Map depMap = hr_tools.getDepNameMap(comp_id);
			
			ehf020700.close();
			hr_tools.close();
			
			if(!dataMap.isEmpty()){
				
				Form.setEHF020700T0_01((String)dataMap.get("EHF020700T0_01"));  //未打卡單單號
				Form.setEHF020700T0_02((String)dataMap.get("EHF020700T0_02"));  //流程空表單編號  -- 未打卡單
				Form.setEHF020700T0_03((String)dataMap.get("EHF020700T0_03"));  //申請人(員工工號)
				Form.setEHF020700T0_03_SHOW( (String) ((Map)empMap.get((String)dataMap.get("EHF020700T0_03"))).get("EMPLOYEE_ID") );
				Form.setEHF020700T0_03_TXT( (String) ((Map)empMap.get((String)dataMap.get("EHF020700T0_03"))).get("EMPLOYEE_NAME") );  //申請人姓名
				Form.setEHF020700T0_04((String)dataMap.get("EHF020700T0_04"));  //申請人部們組織(代號)
				Form.setEHF020700T0_04_SHOW( (String) ((Map)depMap.get((String)dataMap.get("EHF020700T0_04"))).get("SHOW_DEPT_ID") );
				Form.setEHF020700T0_04_TXT( (String) ((Map)depMap.get((String)dataMap.get("EHF020700T0_04"))).get("SHOW_DESC") );  //申請人部門名稱
				Form.setEHF020700T0_05((String)dataMap.get("EHF020700T0_05"));  //填單人(員工工號)
				Form.setEHF020700T0_05_SHOW( (String) ((Map)empMap.get((String)dataMap.get("EHF020700T0_05"))).get("EMPLOYEE_ID") );
				Form.setEHF020700T0_05_TXT( (String) ((Map)empMap.get((String)dataMap.get("EHF020700T0_05"))).get("EMPLOYEE_NAME") );  //填單人姓名
				Form.setEHF020700T0_06((String)dataMap.get("EHF020700T0_06"));  //填單人部門組織(代號)
				Form.setEHF020700T0_06_SHOW( (String) ((Map)depMap.get((String)dataMap.get("EHF020700T0_06"))).get("SHOW_DEPT_ID") );
				Form.setEHF020700T0_06_TXT( (String) ((Map)depMap.get((String)dataMap.get("EHF020700T0_06"))).get("SHOW_DESC") );  //填單人部門名稱
				Form.setEHF020700T0_07((String)dataMap.get("CREATE_DATE"));  //填單日期
				Form.setEHF020700T0_08((String)dataMap.get("NO_CARD_DATE"));    //未打卡日期
				Form.setEHF020700T0_09((String)dataMap.get("EHF020700T0_09"));    //未打卡類別
				Form.setEHF020700T0_09_DESC((String)dataMap.get("EHF020700T0_09_DESC"));  //未打卡類別名稱
				Form.setEHF020700T0_10_HH(((String)dataMap.get("EHF020700T0_10")).substring(0, 2));  //預計時間 (時/分) (起)
				Form.setEHF020700T0_10_MM(((String)dataMap.get("EHF020700T0_10")).substring(2, 4));
				Form.setEHF020700T0_11_HH(((String)dataMap.get("EHF020700T0_11")).substring(0, 2));  //預計時間 (時/分) (迄)
				Form.setEHF020700T0_11_MM(((String)dataMap.get("EHF020700T0_11")).substring(2, 4));
				Form.setEHF020700T0_12((String)dataMap.get("EHF020700T0_12"));  //未打卡原因
				if(!"".equals((String)dataMap.get("EHF020700T0_13")) && (String)dataMap.get("EHF020700T0_13") != null ){
					String[] filename = ((String)dataMap.get("EHF020700T0_13")).split("/");
					request.setAttribute("Attached_File_Name", filename[1]);
					request.setAttribute("Attached_File", "yes");
					Attached_File = "yes";
					Attached_File_Name = filename[1];
				}else {
					request.setAttribute("Attached_File", "no");
					Attached_File = "no";
					Attached_File_Name = "";
				}
				Form.setEHF020700T0_14((String)dataMap.get("EHF020700T0_14"));  //表單狀態
				Form.setEHF020700T0_14_DESC((String)dataMap.get("EHF020700T0_14_DESC"));  //表單狀態(中文名稱)
				
				Form.setUSER_CREATE((String)dataMap.get("USER_CREATE"));
				Form.setUSER_UPDATE((String)dataMap.get("USER_UPDATE"));
				Form.setVERSION((Integer)dataMap.get("VERSION"));
				Form.setDATE_UPDATE((String)dataMap.get("DATE_UPDATE"));
				
				chk_flag = true;
				
			}
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", Form.getEHF020700T0_03(), "");
			//0001 草稿儲存，0002 呈核，0004 審核中，0005 駁回，0006 完成，0008 抽單，0009 作廢 
			if(!"".equals(Form.getEHF020700T0_14()) && !"0001".equals(Form.getEHF020700T0_14()) &&
					!"0005".equals(Form.getEHF020700T0_14()) && !"0008".equals(Form.getEHF020700T0_14())){
				paramMap.put(super.EMS_STRUTSLAYOUT_MODE, super.StrutsLayoutInspectMode);
			}
			
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
		EHF020700M0F Form = (EHF020700M0F) form;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			EHF020700 ehf020700 = new EHF020700();
			
			//建立資料Map
			Map dataMap = new HashMap();
			dataMap = BeanUtils.describe(Form);
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			ehf020700.saveData(dataMap);
			
			ehf020700.close();
			
			//設定Query Key
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020700T0_01());
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
		
		EHF020700M0F Form = (EHF020700M0F) form;
		
		try{
			String[] key_id = new String[1];
			key_id[0] = String.valueOf(Form.getEHF020700T0_01());
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
		
		EHF020700M0F Form = (EHF020700M0F) form;
		String key = "";
		
		try{			
			key = String.valueOf(Form.getEHF020700T0_01());
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
		
		EHF020700M0F Form = (EHF020700M0F) form;
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String employee_id = (String)paramMap.get(super.EMPLOYEE_ID);
		
		try{
			Form.setEHF020700T0_07(tools.ymdTostring(tools.getChiSysDate()));  //填單日期
			Form.setEHF020700T0_08(tools.ymdTostring(tools.getSysDate()));  //未打卡日期
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			Form.setEHF020700T0_03( (String) ((Map)empMap.get(employee_id)).get("USER_CODE") );  //申請人員工工號
			Form.setEHF020700T0_03_SHOW( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_ID") );
			Form.setEHF020700T0_03_TXT( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME") );  //申請人姓名
			Form.setEHF020700T0_04( (String) ((Map)empMap.get(employee_id)).get("DEPT_ID") );  //申請人部們組織代號
			Form.setEHF020700T0_04_SHOW( (String) ((Map)empMap.get(employee_id)).get("SHOW_DEPT_ID") );
			Form.setEHF020700T0_04_TXT( (String) ((Map)empMap.get(employee_id)).get("SHOW_DESC") );  //申請人部們名稱
			Form.setEHF020700T0_05( (String) ((Map)empMap.get(employee_id)).get("USER_CODE") );  //填單人員工工號
			Form.setEHF020700T0_05_SHOW( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_ID") );
			Form.setEHF020700T0_05_TXT( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME") );  //填單人姓名
			Form.setEHF020700T0_06( (String) ((Map)empMap.get(employee_id)).get("DEPT_ID") );  //填單人部們組織代號
			Form.setEHF020700T0_06_SHOW( (String) ((Map)empMap.get(employee_id)).get("SHOW_DEPT_ID") );
			Form.setEHF020700T0_06_TXT( (String) ((Map)empMap.get(employee_id)).get("SHOW_DESC") );  //填單人部們名稱
			
			//設定班別時間區間
			Map empClassMap = hr_tools.getEmpClass(conn, getLoginUser(request));
			Form.setEHF020700T0_10_HH(((String)empClassMap.get("WORK_START_TIME")).substring(0, 2));
			Form.setEHF020700T0_10_MM(((String)empClassMap.get("WORK_START_TIME")).substring(2, 4));
			Form.setEHF020700T0_11_HH(((String)empClassMap.get("WORK_END_TIME")).substring(0, 2));
			Form.setEHF020700T0_11_MM(((String)empClassMap.get("WORK_END_TIME")).substring(2, 4));
			
			Form.setEHF020700T0_14("0001");
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			hr_tools.close();
		}catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
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
		
		//依據班別 產生--時-- 下拉選單
		try{								
			List listEHF020700T0_10_HH = new ArrayList();
			DecimalFormat df=new DecimalFormat("00");
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			for (int i = 0; i < 24; i++) {
				bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF020700T0_10_HH.add(bform);
			}
			request.setAttribute("listEHF020700T0_10_HH", listEHF020700T0_10_HH);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			List listEHF020700T0_10_MM = new ArrayList();
			DecimalFormat df=new DecimalFormat("00");
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			for (int i = 0; i < 60; i=i+5) {
				bform = new BA_ALLKINDForm();
				bform.setItem_id(df.format(i));
				bform.setItem_value(df.format(i));
				listEHF020700T0_10_MM.add(bform);
			}
			request.setAttribute("listEHF020700T0_10_MM", listEHF020700T0_10_MM);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		//產生--未打卡類別-- 下拉選單
		try{			
			List listEHF020700T0_09 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			listEHF020700T0_09.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("上班未打卡");
			listEHF020700T0_09.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("下班未打卡");
			listEHF020700T0_09.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("03");
			bform.setItem_value("全天未打卡");
			listEHF020700T0_09.add(bform);
			request.setAttribute("listEHF020700T0_09", listEHF020700T0_09);
			
		}catch(Exception e) {
			System.out.println(e);
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
	public ActionForward uploadEHF020700M1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020700M0F Form = (EHF020700M0F) form;
		
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
			 			 " SELECT * FROM EHF020700T0 " +
			 			 " WHERE 1=1 " +
			 			 " AND EHF020700T0_01 = '"+Form.getEHF020700T0_01()+"' " +
			 			 " AND EHF020700T0_15 = '"+getLoginUser(request).getCompId()+"' ";

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
				temp = system_root+"ems/uploadfiles/nocard/";
				
				check_Path_Exist(temp , rs.getString("EHF020700T0_03"));
				
				temp += rs.getString("EHF020700T0_03")+"/"+Form.getUPLOADFILE().getFileName();
				
				String filepath = rs.getString("EHF020700T0_03")+"/"+Form.getUPLOADFILE().getFileName();
				
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
				" UPDATE ehf020700t0 SET EHF020700T0_13=?, USER_UPDATE=?, VERSION=VERSION+1, DATE_UPDATE=NOW() " +
				" WHERE EHF020700T0_01 = '"+Form.getEHF020700T0_01()+"' " +
				" AND EHF020700T0_15 = '"+getLoginUser(request).getCompId()+"' ";
				
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
			System.out.println("FILEUPLOAD.uploadEHF020700M1() " + e);
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
		EHF020700M0F Form = (EHF020700M0F) form;

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
			" SELECT EHF020700T0.* " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_01 = '"+Form.getEHF020700T0_01()+"' " +
			" AND EHF020700T0_15 = '"+getLoginUser(request).getCompId()+"' ";	
			
			Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			if(rs.next()){
				String[] filename = rs.getString("EHF020700T0_13").split("/");				
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
				String filepath = system_root+"ems/uploadfiles/nocard/";
				filepath += rs.getString("EHF020700T0_13");				
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
		EHF020700M0F Form = (EHF020700M0F) form;

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
			" SELECT EHF020700T0.* " +
			" FROM EHF020700T0 " +
			" WHERE 1=1 " +
			" AND EHF020700T0_01 = '"+Form.getEHF020700T0_01()+"' " +
			" AND EHF020700T0_15 = '"+getLoginUser(request).getCompId()+"' ";	
			
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
				String filepath = system_root+"ems/uploadfiles/nocard/";				
				filepath += rs.getString("EHF020700T0_13");				
				java.net.URLEncoder.encode(filepath, "UTF-8");				
				File fout = new File(filepath);				
				if(fout.exists()){				
					//刪除附加檔案
					fout.delete();					
					String upsql = "" +
					" UPDATE EHF020700T0 " +
					" SET EHF020700T0_13 = '' " +
					" WHERE 1=1 " +
					" AND EHF020700T0_01 = '"+Form.getEHF020700T0_01()+"' " +
					" AND EHF020700T0_15 = '"+getLoginUser(request).getCompId()+"' ";
					
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
