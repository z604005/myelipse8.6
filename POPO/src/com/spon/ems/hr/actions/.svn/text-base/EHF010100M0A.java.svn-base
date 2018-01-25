package com.spon.ems.hr.actions;

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
import com.spon.ems.fileimport.IMP_EHF010100;
import com.spon.ems.fileimport.IMP_EHF010300;
import com.spon.ems.hr.forms.EHF010100M0F;
import com.spon.ems.hr.forms.EX010100R0F;
import com.spon.ems.hr.models.EHF010100;
import com.spon.ems.vacation.forms.EHF020200M0F;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * (Action)員工基本資料管理
 * @author maybe
 * @version 1.0
 * @created 2015/8/18 上午9:24:29
 */
public class EHF010100M0A extends QueryAction {
	
	private BA_TOOLS tool;

	public EHF010100M0A()
	{
		tool = BA_TOOLS.getInstance();
	}

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立EHF010106元件
			EHF010100 ehf010100 = new EHF010100(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF010100T0_01", key[0]);  //員工系統代碼
			dataMap.put("COMP_ID", comp_id);
			
			//執行EHF010106刪除功能
			ehf010100.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			
			//關閉EHF010106元件
			ehf010100.close();
			
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
		
		EHF010100M0F Form = new EHF010100M0F();
		List list = new ArrayList();
		
		Form.setEHF010100T0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF010100M0F Form = (EHF010100M0F) form;
		Map return_map = new HashMap();
		EHF010100M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF010100M0F> querylist = new ArrayList<EHF010100M0F>();
			
			//建立EHF010106元件
			EHF010100 ehf010100 = new EHF010100(comp_id);
			
			//建立EHF010109M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf010100_queryList = ehf010100.queryData(queryCondMap);
			
			Iterator it = ehf010100_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF010100M0F();
				
				bean.setEHF010100T0_01((String)tempMap.get("EHF010100T0_01"));	//員工系統代碼
				bean.setEHF000200T0_03((String)tempMap.get("EHF000200T0_03"));	//部門名稱
				bean.setEHF010100T0_02((String)tempMap.get("EHF010100T0_02"));	//員工工號
				bean.setEHF010100T0_05((String)tempMap.get("EHF010100T0_05"));	//員工姓名
				if("1".equals((String)tempMap.get("EHF010100T0_07"))){
					bean.setEHF010100T0_07("正式員工");	//員工類別
				}else if("2".equals((String)tempMap.get("EHF010100T0_07"))){
					bean.setEHF010100T0_07("臨時員工");	//員工類別
				}else if("3".equals((String)tempMap.get("EHF010100T0_07"))){
					bean.setEHF010100T0_07("外籍勞工");	//員工類別
				}else if("4".equals((String)tempMap.get("EHF010100T0_07"))){
					bean.setEHF010100T0_07("外籍配偶");	//員工類別
				}else if("5".equals((String)tempMap.get("EHF010100T0_07"))){
					bean.setEHF010100T0_07("工讀生");	//員工類別
				}
				if("1".equals((String)tempMap.get("EHF010100T1_02"))){
					bean.setEHF010100T1_02("在職");	//職務狀況
				}else if("2".equals((String)tempMap.get("EHF010100T1_02"))){
					bean.setEHF010100T1_02("離職");	//職務狀況
				}else if("3".equals((String)tempMap.get("EHF010100T1_02"))){
					bean.setEHF010100T1_02("復職");	//職務狀況
				}else if("4".equals((String)tempMap.get("EHF010100T1_02"))){
					bean.setEHF010100T1_02("留職停薪");	//職務狀況
				}else{
					bean.setEHF010100T1_02("");	//職務狀況
				}				
												
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF010100T0_LIST(querylist);
			
			//關閉EHF010106元件
			ehf010100.close();
			
			Form.setEHF000200T0_02("");	//部門代號
			Form.setEHF000200T0_03("");	//員工工號
			Form.setEHF010100T6_02("");	//員工姓名
			Form.setEHF010100T0_02("");	//身分證字號
			Form.setEHF010100T0_05("");	//員工類別
			Form.setEHF010100T0_I("");	//職務狀況
			
			
			
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", Form);
			
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
		
		request.setAttribute("listEHF010100T0_07", this.getSelectOptions(conn,true, "EMPTYPE","and FM010501_06='"+userform(request).getSC0030_14()+"'"));//員工類別
		request.setAttribute("listEHF010100T1_02", this.getSelectOptions(conn,true, "JOBTYPE","and FM010501_06='"+userform(request).getSC0030_14()+"'"));//職務狀況

	}
	
	/**
	 * 員工人事資料作業匯入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward impEmployeeInformation(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF010100M0F Form = (EHF010100M0F) form;
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
			Map depMap = hr_tools.getDep(authbean.getCompId());

			// 取得員工資訊
			Map empMap = hr_tools.getEmp(authbean.getCompId());
			
			//取得職務系統內碼
			Map titleNameMap = hr_tools.getTitle(authbean.getCompId());
			
			hr_tools.close();
			//建立行事曆匯入元件
			IMP_EHF010100 imp_ehf010106 = new IMP_EHF010100(  authbean.getEmployeeID(), authbean, request, depMap, empMap, titleNameMap );
			
			//取得匯入檔案
			FormFile importfile = Form.getUp_file1();
			
			//取得回傳訊息Map
			Map msgMap = imp_ehf010106.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(), importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());
			
			List datalist = (List) msgMap.get("DATALIST");

			Iterator it = datalist.iterator();

			Map tempMap = null;
			EHF010100M0F bean = null;

			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				bean = new EHF010100M0F();
				
				bean.setEHF010100T0_02((String)tempMap.get("EHF010100T0_02"));
				bean.setEHF010100T0_05((String)tempMap.get("EHF010100T0_05"));
				
//				String yera_month_day_start=(String)tempMap.get("EHF020200T0_09_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_09_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_09_day")));
//				String yera_month_day_end  =(String)tempMap.get("EHF020200T0_10_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_10_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_10_day")));
//				
//				bean.setEHF020200T0_03_TXT((String)tempMap.get("EHF020200T0_03")+" / "+(String)tempMap.get("EHF020200T0_03_NAME"));
//				bean.setEHF020200T0_14_DESC((String)tempMap.get("EHF020200T0_14"));
//				bean.setEHF020200T0_09(yera_month_day_start+" "+(String)tempMap.get("EHF020200T0_11_HH")+":" +(String)tempMap.get("EHF020200T0_11_MM") 
//					   +" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF020200T0_12_HH")+":" +(String)tempMap.get("EHF020200T0_12_MM"));
//				bean.setEHF020200T0_15((String)tempMap.get("EHF020200T0_15"));
				
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
					bean = new EHF010100M0F();
					bean.setEHF010100T0_02((String)tempMap.get("EHF010100T0_02"));
					bean.setEHF010100T0_05((String)tempMap.get("EHF010100T0_05"));
					bean.setEHF010100T0_26((String)tempMap.get("error"));
//					String yera_month_day_start=(String)tempMap.get("EHF020200T0_09_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_09_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_09_day")));
//					String yera_month_day_end  =(String)tempMap.get("EHF020200T0_10_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_10_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_10_day")));
//					
//					bean.setEHF020200T0_03_TXT((String)tempMap.get("EHF020200T0_03")+" / "+(String)tempMap.get("EHF020200T0_03_NAME"));
//					bean.setEHF020200T0_14_DESC((String)tempMap.get("EHF020200T0_14"));
//					bean.setEHF020200T0_09( yera_month_day_start+" "+(String)tempMap.get("EHF020200T0_11_HH")+":" +(String)tempMap.get("EHF020200T0_11_MM") 
//							+" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF020200T0_12_HH")+":" +(String)tempMap.get("EHF020200T0_12_MM"));
//					bean.setEHF020200T0_15((String)tempMap.get("error"));//20131016修改   BY 賴泓錡
					
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
					bean = new EHF010100M0F();
					bean.setEHF010100T0_02((String)tempMap.get("EHF010100T0_02"));
					bean.setEHF010100T0_05((String)tempMap.get("EHF010100T0_05"));
					bean.setEHF010100T0_26((String)tempMap.get("error"));
//					String yera_month_day_start=(String)tempMap.get("EHF020200T0_09_year")+"/"+(String)tempMap.get("EHF020200T0_09_month")+"/"+(String)tempMap.get("EHF020200T0_09_day");
//					String yera_month_day_end  =(String)tempMap.get("EHF020200T0_10_year")+"/"+(String)tempMap.get("EHF020200T0_10_month")+"/"+(String)tempMap.get("EHF020200T0_10_day");
//					
//					bean.setEHF020200T0_03_TXT((String)tempMap.get("EHF020200T0_03")+" / "+(String)tempMap.get("EHF020200T0_03_NAME"));
//					bean.setEHF020200T0_14_DESC((String)tempMap.get("EHF020200T0_14"));
//					bean.setEHF020200T0_09( yera_month_day_start+" "+(String)tempMap.get("EHF020200T0_11_HH")+":" +(String)tempMap.get("EHF020200T0_11_MM") 
//							+" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF020200T0_12_HH")+":" +(String)tempMap.get("EHF020200T0_12_MM"));
//					bean.setEHF020200T0_15((String)tempMap.get("error"));//20131016修改   BY 賴泓錡
					
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
			request.setAttribute("MSG_Success", "共成功匯入 "+(Integer)msgMap.get("DATACOUNT")+" 筆!!");

			//設定前端顯示資訊
			//request.setAttribute("Form1Datas", 	Form);
			request.setAttribute("Form2Datas",	list);
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
		
		return mapping.findForward("redirectEMP");
//		return mapping.findForward("success");
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
		EHF010100M0F Form = (EHF010100M0F) form;
		Connection conn = null;
		
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
			/*// 存入檔案
			String FileName = getServlet().getServletContext().getRealPath("")+"/excelrpt/"+"//" + "EHF010300R0_example.xls"; 
			String name ="員工年度休假(範本)"+tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";
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
			return null;*/
			
			//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			
    		HR_TOOLS hr_tools = new HR_TOOLS();
    		
    		//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			
			hr_tools.close();
			
			int DataCount = 0;
			
			String sql = "" +
			 			 " SELECT EHF010100T0_01, EHF010100T0_02, EHF010100T0_05, EHF010100T6_02 FROM EHF010100T0 " +
			 			 " LEFT JOIN EHF010100T1 ON EHF010100T1_01 = EHF010100T0_01 " +
			 			 " LEFT JOIN EHF010100T6 ON EHF010100T6_01 = EHF010100T0_01 " +
			 			 " WHERE 1=1 " +
			 			 " AND EHF010100T1_02 = '1' AND EHF010100T1_04 = '0' "+	//員工在職才執行
			 			 " AND EHF010100T0.HR_CompanySysNo = '"+authbean.getCompId()+"' " +
			 			 " ORDER BY EHF010100T6_02, EHF010100T0_02 ";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			
			//產生Excel元件
			EX010100R0F ef = new EX010100R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getEmployeeID(), request, count);
			
			while(rs.next()){
				if(DataCount==0){
					
				}
				ef.next();
				
				if(count > 1){
					if(DataCount==0){
						for (int i = 0; i < count-1; i++) {
							ef.CopyCellFormat(6 + i , "A", 5, "A");// 複製格式
							ef.CopyCellFormat(6 + i , "B", 5, "B");// 複製格式
							ef.CopyCellFormat(6 + i , "C", 5, "C");// 複製格式
							ef.CopyCellFormat(6 + i , "D", 5, "D");// 複製格式
							ef.CopyCellFormat(6 + i , "E", 5, "E");// 複製格式
							ef.CopyCellFormat(6 + i , "F", 5, "F");// 複製格式
							ef.CopyCellFormat(6 + i , "G", 5, "G");// 複製格式
							ef.CopyCellFormat(6 + i , "H", 5, "H");// 複製格式
							ef.CopyCellFormat(6 + i , "I", 5, "I");// 複製格式
							ef.CopyCellFormat(6 + i , "J", 5, "J");// 複製格式
							ef.CopyCellFormat(6 + i , "K", 5, "K");// 複製格式
							ef.CopyCellFormat(6 + i , "L", 5, "L");// 複製格式
							ef.CopyCellFormat(6 + i , "M", 5, "M");// 複製格式
						}
					}
				}
				
				
				ef.setDetail(1,"B", (String) ((Map)depMap.get(rs.getString("EHF010100T6_02"))).get("SHOW_DEPT_ID"),false);  //部門代號
				ef.setDetail(1,"C", rs.getString("EHF010100T0_02"),false);//員工工號
				ef.setDetail(1,"N", rs.getString("EHF010100T0_05")==null?"":rs.getString("EHF010100T0_05"),false);
				
				DataCount++;
				
			}
			
			ef.EndDocument();
			
			rs.close();
			stmt.close();
			
			String FileName="";
			String name = "員工年度休假匯入.xls";
			
			if(DataCount>0){
				//存入檔案
				FileName = ef.write();
				request.setAttribute("MSG","列印完成!!");
				//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
				
			}else{				
				request.setAttribute("ERR_MSG","沒有資料可列印!!");	
			}
		
		} catch (Exception E) {
			E.printStackTrace();
			request.setAttribute("MSG", "列印失敗!!");
		} finally {
			// ef.write();
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return this.init(mapping, form, request, response);
	}
	
	/**
	 * 匯入員工年度休假資料
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */	
	public ActionForward uploadEHF010300M1(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF010100M0F Form = (EHF010100M0F) form;
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
			
			hr_tools.close();
			
			IMP_EHF010300 imp_ehf010300 =  new IMP_EHF010300( authbean.getEmployeeID(), (String)((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID"), authbean, depMap, empMap);
			
			//取得匯入檔案
			FormFile importfile = Form.getUPLOADFILE();
			
			//取得回傳訊息Map
			Map msgMap = imp_ehf010300.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(),
												  importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());
			//取得匯入的詳細資料清單
			List datalist = (List) msgMap.get("DATALIST");
			
			Iterator it = datalist.iterator();
			Map tempMap = null;
			EHF010100M0F bean = null;
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				bean = new EHF010100M0F();
				
				bean.setEHF010300T0_02((String)tempMap.get("EHF010300T0_02"));
				bean.setEHF010300T0_04((String)tempMap.get("EHF010300T0_04"));
				bean.setEHF010300T0_05((String)tempMap.get("EHF010300T0_05"));
				bean.setEHF010300T0_06((String)tempMap.get("EHF010300T0_06"));
				bean.setEHF010300T0_07((String)tempMap.get("EHF010300T0_07_Day")+"天"+(String)tempMap.get("EHF010300T0_07_Hour")+"小時");
				bean.setEHF010300T0_09(
				(String)tempMap.get("EHF010300T0_09_Year")+"/"+(String)tempMap.get("EHF010300T0_09_Month")+"/"+(String)tempMap.get("EHF010300T0_09_Day")
				+"~"+
				(String)tempMap.get("EHF010300T0_10_Year")+"/"+(String)tempMap.get("EHF010300T0_10_Month")+"/"+(String)tempMap.get("EHF010300T0_10_Day"));
				
				list.add(bean);
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
					bean = new EHF010100M0F();
					
					bean.setEHF010300T0_02((String)tempMap.get("EHF010300T0_02"));
					bean.setEHF010300T0_04((String)tempMap.get("EHF010300T0_04"));
					bean.setEHF010300T0_05((String)tempMap.get("EHF010300T0_05"));
					bean.setEHF010300T0_06((String)tempMap.get("EHF010300T0_06"));
					bean.setEHF010300T0_07((String)tempMap.get("EHF010300T0_07_Day")+"天"+(String)tempMap.get("EHF010300T0_07_Hour")+"小時");
					bean.setEHF010300T0_09(
					(String)tempMap.get("EHF010300T0_09_Year")+"/"+(String)tempMap.get("EHF010300T0_09_Month")+"/"+(String)tempMap.get("EHF010300T0_09_Day")
					+"~"+
					(String)tempMap.get("EHF010300T0_10_Year")+"/"+(String)tempMap.get("EHF010300T0_10_Month")+"/"+(String)tempMap.get("EHF010300T0_10_Day"));					
					bean.setEHF010300T0_11((String)tempMap.get("ERROR"));
					
					error_list.add(bean);
				}
				if(error_list.size()>0){
					//設定前端顯示訊息
					request.setAttribute("ERRORMSG", "共有 "+(Integer)msgMap.get("ERRORDATACOUNT")+" 筆資料錯誤!!");
					request.setAttribute("Form4Datas",error_list);
					request.setAttribute("error_collection", "show");
				}
			}
			
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
	/**因堯堉第一次匯入員工基本資料時漏掉薪資設定，故在此補充 	BY JIMMYWU
	 * 補員工基本資料 		薪資設定
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */	
	public ActionForward updateSalary(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		
		EHF010100 ehf010100 = new EHF010100();
		//建立新增資料Map
		Map dataMap = new HashMap();
		
		try{
			
			conn=tool.getConnection("SPOS");
			
			dataMap.put("EHF030200T0_05","");//
			dataMap.put("EHF030200T0_06","");//
			dataMap.put("EHF030200T0_06_AC","");//
			dataMap.put("EHF030200T0_07","");//
			dataMap.put("EHF030200T0_08","1");//
			dataMap.put("EHF030200T0_12","");//
			dataMap.put("EHF030200T0_18","");//
			dataMap.put("EHF030200T0_20","");//
			dataMap.put("EHF030200T0_21","1");//
			
			dataMap.put("EHF030300T0_05","");//
			dataMap.put("EHF030300T0_05_NUMBER","");//
			dataMap.put("EHF030300T0_07","");//
			dataMap.put("EHF030300T0_07_NUMBER","");//
			dataMap.put("EHF030300T0_09",0);//
			dataMap.put("EHF030300T0_10",0);//
			dataMap.put("EHF030300T0_11",1);//
			dataMap.put("EHF030300T0_15",0);//
			dataMap.put("EHF030300T0_05_START",tool.ymdTostring(tool.getSysDateYYMMDD()));//
			dataMap.put("EHF030300T0_05_END","0000-00-00");//
			dataMap.put("EHF030300T0_07_START",tool.ymdTostring(tool.getSysDateYYMMDD()));//
			dataMap.put("EHF030300T0_07_END","0000-00-00");//
			dataMap.put("COMP_ID","COM28034776");//
			dataMap.put("USER_NAME","管理者");
			
			String sql=" SELECT a.EHF010100T0_01,b.EHF010100T6_02,EHF010100T0_07" +
					   " FROM EHF010100T0 a" +
					   " LEFT JOIN EHF010100T6 b ON a.EHF010100T0_01=b.EHF010100T6_01 AND a.HR_CompanySysNo=b.HR_CompanySysNo";

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				
				dataMap.put("EHF010100T0_01",rs.getString("EHF010100T0_01"));//員工工號
				dataMap.put("EHF010100T6_02",rs.getString("EHF010100T6_02"));//部門代號
				dataMap.put("EHF010100T0_07",rs.getString("EHF010100T0_07"));//員工類別(正式員工)
				
				ehf010100.addDataSalary(dataMap);
				ehf010100.addDataInsurance(dataMap);
			}
			
			
		}catch(Exception e){
			
		}
		
		
		return queryForm(mapping,form,request,response);
	}
}
