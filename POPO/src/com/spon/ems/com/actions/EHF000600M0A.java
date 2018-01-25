package com.spon.ems.com.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.com.forms.EHF000600M0F;
import com.spon.ems.com.models.EHF000300;
import com.spon.ems.com.models.EHF000600;
import com.spon.ems.fileimport.IMP_EHF020200;
import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.ems.vacation.tools.EMS_FixAttendance;
import com.spon.ems.vacation.tools.EMS_setVacationUpdateSQL;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>請假單申請
 *@author maybe
 *@version 1.0
 *@created 2015/11/13 上午10:32:15
 */
public class EHF000600M0A extends QueryAction {
	
	private EMS_ACCESS ems_access;
	
	public EHF000600M0A(){
		ems_access = EMS_ACCESS.getInstance();
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		
		
		try{
			
			//建立EHF000600元件
			EHF000600 ehf000600 = new EHF000600(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF000600T0_01", key[0]);  //職務系統代碼
			dataMap.put("COMP_ID", comp_id);
			
//			System.out.println(key[0]);
			
//			EHF000600M0F Form = (EHF000600M0F) form;

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
				" AND EHF000600T0_01 = '"+key[0]+"' " +
				" AND HR_CompanySysNo = '"+getLoginUser(request).getCompId()+"' ";	
				
				Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs=stmt.executeQuery(sql);
				
				if(rs.next()){				
//					String filepath = request.getSession().getServletContext().getRealPath("/")+"ems/uploadfiles/rules/";
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
						" AND EHF000600T0_01 = '"+key[0]+"' " +
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
//			
			
			
			
			//boolean delFlag = ehf000600.selectEHF010100T6(key[0], "", comp_id);
			

				//執行EHF000600刪除功能
				ehf000600.delData(dataMap);
				
				if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
					chk_flag = true;
				}
			
			
			//關閉EHF000600元件
			ehf000600.close();
			
			//控制刪除後的顯示畫面
			paramMap.put(super.DEL_FORWARD_CONFIG, super.FORWARD_INIT);
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF000600M0F Form = new EHF000600M0F();
		List list = new ArrayList();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String employee_id = (String)paramMap.get(super.EMPLOYEE_ID);
		
		try{
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//當前登入者權限
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			String HR 		= tools.getSysParam(conn, authbean.getCompId(),"HR_IDENTITY");		//預設人資		100
			String SYSTEM 	= tools.getSysParam(conn, authbean.getCompId(),"SYSTEM_IDENTITY");	//系統管理者	000
			String AC 		= tools.getSysParam(conn, authbean.getCompId(),"AC_IDENTITY");		//會計		110
			String BOSS 	= tools.getSysParam(conn, authbean.getCompId(),"BOSS_IDENTITY");	//老闆		111
			
			if(ems_access.checkEmsRoleEmp(conn, authbean, HR) || ems_access.checkEmsRoleEmp(conn, authbean, BOSS)){
				//人事經辦
			}else if(ems_access.checkEmsRoleEmp(conn, authbean, SYSTEM)){
				//系統管理者
			}else{
				//一般員工
				/*
				 * Map empMap = hr_tools.getEmpNameMap(comp_id);
				Form.setEHF000600T0_03( (String) ((Map)empMap.get(employee_id)).get("USER_CODE") );  //申請人員工工號
				Form.setEHF000600T0_03_SHOW( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_ID") );
				Form.setEHF000600T0_03_TXT( (String) ((Map)empMap.get(employee_id)).get("EMPLOYEE_NAME") );  //申請人姓名
				Form.setEHF000600T0_04( (String) ((Map)empMap.get(employee_id)).get("DEPT_ID") );  //申請人部們組織代號
				Form.setEHF000600T0_04_SHOW( (String) ((Map)empMap.get(employee_id)).get("SHOW_DEPT_ID") );
				Form.setEHF000600T0_04_TXT( (String) ((Map)empMap.get(employee_id)).get("SHOW_DESC") );  //申請人部們名稱
				*/
			}
			
			hr_tools.close();
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
			Form.setEHF000600C(list);
			
			
			//自動執行查詢
			paramMap.put(INIT_FORWARD_CONFIG,"queryForm");
			
			
			
/*			ActionMapping mapping = null;
			HttpServletResponse response = null;
			this.queryForm(mapping, Form, request, response);*/
			

			
		}catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF000600M0F Form = (EHF000600M0F) form;
		Map return_map = new HashMap();
		EHF000600M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF000600M0F> querylist = new ArrayList<EHF000600M0F>();
			
			//建立EHF000600元件
			EHF000600 ehf000600 = new EHF000600(comp_id);
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			Map empMap = null;
			Map depMap = null;
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			empMap = hr_tools.getEmpNameMap(comp_id);
			depMap = hr_tools.getDepNameMap(comp_id);
			
			//使用元件進行處理 //SQL
			List ehf000600_queryList = ehf000600.queryData(queryCondMap);
			
			Iterator it = ehf000600_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF000600M0F();
				
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
				
				bean.setEHF000600T0_01((String)tempMap.get("EHF000600T0_01"));  //規則編號
				bean.setEHF000600T0_02((String)tempMap.get("EHF000600T0_02"));  //規則名稱
				bean.setEHF000600T0_03((String)tempMap.get("EHF000600T0_03"));  //規則內容簡述
				String path = (String)tempMap.get("EHF000600T0_04");
				String url = "<a href=\"" + temp + path + "\" title=\""+ path + "\">" + path + "</a>";
				bean.setEHF000600T0_04(path);  //附件(附加檔案)
				/*
				bean.setEHF000600T0_01((String)tempMap.get("EHF000600T0_01"));  //請假單單號
				bean.setEHF000600T0_02((String)tempMap.get("EHF000600T0_08"));  //申請日期
				bean.setEHF000600T0_03((String) ((Map)depMap.get((String)tempMap.get("EHF000600T0_04"))).get("SHOW_DESC"));  //申請人部門名稱
				bean.setEHF000600T0_04((String) ((Map)empMap.get((String)tempMap.get("EHF000600T0_03"))).get("EMPLOYEE_NAME"));  //申請人
				if(!"".equals((String)tempMap.get("EHF000600T0_07"))){//有指定代理人
					bean.setEHF000600T0_07_TXT((String) ((Map)empMap.get((String)tempMap.get("EHF000600T0_07"))).get("EMPLOYEE_NAME"));  //代理人
				}else{
					bean.setEHF000600T0_07_TXT("");  //代理人
				}
					bean.setEHF000600T0_14_DESC((String)tempMap.get("EHF020100T0_04"));  //假別
				bean.setEHF000600T0_09((String)tempMap.get("START_DATE")+" "+((String)tempMap.get("EHF000600T0_11")).substring(0, 2)+"點 " +
						((String)tempMap.get("EHF000600T0_11")).substring(2, 4)+"分 "+ " ~ " +(String)tempMap.get("END_DATE")+" " +
						((String)tempMap.get("EHF000600T0_12")).substring(0, 2)+"點 "+((String)tempMap.get("EHF000600T0_12")).substring(2, 4)+"分 ");  //日期區間
				String[] dayhour = ((String)tempMap.get("EHF000600T0_13")).split("/");
				bean.setEHF000600T0_13(dayhour[0]+" 天 "+dayhour[1]+" 時");  //請假時數(天/小時)
				bean.setEHF000600T0_16_DESC((String)tempMap.get("EHF000600T0_16_DESC"));  //表單狀態*/
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF000600C(querylist);
			
			//關閉EHF000600元件
			ehf000600.close();
			hr_tools.close();
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", form);
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return return_map;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
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
		
		//產生系統預設類別
		try{
		List datas = new ArrayList();
		BA_ALLKINDForm bform = new BA_ALLKINDForm();
		bform.setItem_id("");
		bform.setItem_value("-請選擇-");
		datas.add(bform);
		bform = new BA_ALLKINDForm();
		bform.setItem_id("0001");
		bform.setItem_value("填寫中");
		datas.add(bform);
		/*bform = new BA_ALLKINDForm();
		bform.setItem_id("0002");
		bform.setItem_value("表單送審(草稿呈核)");
		datas.add(bform);
		bform = new BA_ALLKINDForm();
		bform.setItem_id("0004");
		bform.setItem_value("審核通過");
		datas.add(bform);
		bform = new BA_ALLKINDForm();
		bform.setItem_id("0005");
		bform.setItem_value("審核駁回");
		datas.add(bform);*/
		bform = new BA_ALLKINDForm();
		bform.setItem_id("0006");
		bform.setItem_value("完成");
		datas.add(bform);
		/*bform = new BA_ALLKINDForm();
		bform.setItem_id("0008");
		bform.setItem_value("抽單");
		datas.add(bform);*/
		bform = new BA_ALLKINDForm();
		bform.setItem_id("0009");
		bform.setItem_value("作廢");
		datas.add(bform);
		request.setAttribute("EHF000600T0_16_list", datas);
		}catch(Exception e) {
			System.out.println(e);
		}

	}
	
	/**
	 * 匯入請假單
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward impEHF000600(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF000600M0F Form = (EHF000600M0F) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//Collection List
			List list = new ArrayList();
			//NG_Collection List
			List ng_list = new ArrayList();
			//ERROR_Collection List
			List error_list = new ArrayList();
			
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			// 取得部門資訊
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

			// 取得員工資訊
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			
			//建立請假單匯入元件
			IMP_EHF020200 imp_ehf020200 = new IMP_EHF020200( authbean.getEmployeeID(), (String)((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID"), authbean, depMap, empMap);
			
			//取得匯入檔案
			FormFile importfile = Form.getUPLOADFILE();
			
			//取得回傳訊息Map
			Map msgMap = imp_ehf020200.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(),
												  importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());
			//取得匯入的詳細資料清單
			List datalist = (List) msgMap.get("DATALIST");
			
			Iterator it = datalist.iterator();
			Map tempMap = null;
			EHF000600M0F bean = null;
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				bean = new EHF000600M0F();
				
				String yera_month_day_start=(String)tempMap.get("EHF000600T0_09_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF000600T0_09_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF000600T0_09_day")));
				String yera_month_day_end  =(String)tempMap.get("EHF000600T0_10_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF000600T0_10_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF000600T0_10_day")));
				
				bean.setEHF000600T0_03_TXT((String)tempMap.get("EHF000600T0_03")+" / "+(String)tempMap.get("EHF000600T0_03_NAME"));
				bean.setEHF000600T0_14_DESC((String)tempMap.get("EHF000600T0_14"));
				bean.setEHF000600T0_09(yera_month_day_start+" "+(String)tempMap.get("EHF000600T0_11_HH")+":" +(String)tempMap.get("EHF000600T0_11_MM") 
					   +" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF000600T0_12_HH")+":" +(String)tempMap.get("EHF000600T0_12_MM"));
				bean.setEHF000600T0_15((String)tempMap.get("EHF000600T0_15"));
				
				list.add(bean);
			}
			
			//判斷是否有重複未匯入的資料清單
			if(msgMap.containsKey("NGDATACOUNT")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("NGDATALIST");
				
				it = datalist.iterator();
				tempMap = null;
				bean = null;
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bean = new EHF000600M0F();
					String yera_month_day_start=(String)tempMap.get("EHF000600T0_09_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF000600T0_09_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF000600T0_09_day")));
					String yera_month_day_end  =(String)tempMap.get("EHF000600T0_10_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF000600T0_10_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF000600T0_10_day")));
					
					bean.setEHF000600T0_03_TXT((String)tempMap.get("EHF000600T0_03")+" / "+(String)tempMap.get("EHF000600T0_03_NAME"));
					bean.setEHF000600T0_14_DESC((String)tempMap.get("EHF000600T0_14"));
					bean.setEHF000600T0_09( yera_month_day_start+" "+(String)tempMap.get("EHF000600T0_11_HH")+":" +(String)tempMap.get("EHF000600T0_11_MM") 
							+" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF000600T0_12_HH")+":" +(String)tempMap.get("EHF000600T0_12_MM"));
					bean.setEHF000600T0_15((String)tempMap.get("error"));//20131016修改   BY 賴泓錡
					
					ng_list.add(bean);
				}
				if(ng_list.size()>0){
					// 設定前端顯示訊息
					request.setAttribute("NGMSG", "請假單共有 "	+ (Integer) msgMap.get("NGDATACOUNT")+ " 筆重複資料未匯入!!");
					request.setAttribute("Form3Datas", ng_list);
					request.setAttribute("ng_collection", "show");
				}
			}
			
			//判斷是否有重複未匯入的資料清單
			if(msgMap.containsKey("ERRORDATACOUNT")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("ERRORDATALIST");
				
				it = datalist.iterator();
				tempMap = null;
				bean = null;
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bean = new EHF000600M0F();
					
					String yera_month_day_start=(String)tempMap.get("EHF000600T0_09_year")+"/"+(String)tempMap.get("EHF000600T0_09_month")+"/"+(String)tempMap.get("EHF000600T0_09_day");
					String yera_month_day_end  =(String)tempMap.get("EHF000600T0_10_year")+"/"+(String)tempMap.get("EHF000600T0_10_month")+"/"+(String)tempMap.get("EHF000600T0_10_day");
					
					bean.setEHF000600T0_03_TXT((String)tempMap.get("EHF000600T0_03")+" / "+(String)tempMap.get("EHF000600T0_03_NAME"));
					bean.setEHF000600T0_14_DESC((String)tempMap.get("EHF000600T0_14"));
					bean.setEHF000600T0_09( yera_month_day_start+" "+(String)tempMap.get("EHF000600T0_11_HH")+":" +(String)tempMap.get("EHF000600T0_11_MM") 
							+" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF000600T0_12_HH")+":" +(String)tempMap.get("EHF000600T0_12_MM"));
					bean.setEHF000600T0_15((String)tempMap.get("error"));//20131016修改   BY 賴泓錡
					
					error_list.add(bean);
				}
				if(error_list.size()>0){
					//設定前端顯示訊息
					request.setAttribute("ERRORMSG", "請假單共有 "+(Integer)msgMap.get("ERRORDATACOUNT")+" 筆格式不正確資料未匯入!!");
					request.setAttribute("Form4Datas",error_list);
					request.setAttribute("error_collection", "show");
				}
			}
			
			hr_tools.close();
			
			//設定前端顯示訊息
			request.setAttribute("MSG", "請假單共匯入 "+(Integer)msgMap.get("DATACOUNT")+" 筆!!");

			//設定前端顯示資訊
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("collection", "show");
			
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
		
		return mapping.findForward("redirectIMP");
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
			String FileName = getServlet().getServletContext().getRealPath("")+"/excelrpt/"+"//" + "EHF000600R0_example.xls"; 
			String name ="請假單匯入(範本)"+tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";
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

}
