package com.spon.ems.hr.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.hr.forms.EHF010112M0F;
import com.spon.ems.hr.forms.EX010112R0F;
import com.spon.ems.hr.models.EHF010112;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class EHF010112M0A extends QueryAction {
	
	private BA_TOOLS tool;
	//private BA_EMS_TOOLS ems_tool;
	public EHF010112M0A()
	{
		tool = BA_TOOLS.getInstance();
		//ems_tool = BA_EMS_TOOLS.getInstance();
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
		
		EHF010112M0F Form = new EHF010112M0F();
		List list = new ArrayList();
		
		Form.setEHF010112M0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF010112M0F Form = (EHF010112M0F) form;
		Map return_map = new HashMap();
		EHF010112M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF010112M0F> querylist = new ArrayList<EHF010112M0F>();
			
			//建立EHF010112元件
			EHF010112 ehf010112 = new EHF010112(comp_id);
			
			//建立EHF010112M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf010112_queryList = ehf010112.queryData(queryCondMap);
			
			Iterator it = ehf010112_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF010112M0F();
				
				bean.setHR_EmployeeSysNo((String)tempMap.get("HR_EmployeeSysNo"));	//員工系統代碼
				bean.setHR_DepartmentName((String)tempMap.get("HR_DepartmentName"));	//部門名稱
				bean.setHR_JobName((String)tempMap.get("HR_JobNameNo"));	//員工職稱
				bean.setHR_EmployeeNo((String)tempMap.get("HR_EmployeeNo"));	//員工工號
				bean.setEHF010106T0_04((String)tempMap.get("EHF010106T0_04"));	//員工姓名
				bean.setEHF010106T0_10((String)tempMap.get("EHF010106T0_10"));	//出生日期
												
				querylist.add(bean);
				
			}
			
			//設定querylist
			Form.setEHF010112M0_LIST(querylist);
			
			//關閉EHF010112元件
			ehf010112.close();
			
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
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * 列印查詢結果  -- 員工生日清冊
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		EHF010112M0F Form = (EHF010112M0F) form;
		Connection conn = null;
		String comp_id = getLoginUser(request).getCompId();  //公司代碼
		ActionErrors lc_errors = new ActionErrors();
		
		//建立資料庫連線
    	if (conn == null) {
    		try {
    			conn = tool.getConnection("SPOS");
    		} catch (SQLException e2) {
    			e2.printStackTrace();
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
		}
    	
    	try{
    		//產生Excel元件
			EX010112R0F ef = new EX010112R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getUserId(),request);
    		   		
			int DataCount =0;
									
			// 列印報表表頭
			ef.printHeadValue("", "", tool.ymdTostring(tool.getSysDate())+ "", "思邦科技股份有限公司", "員工生日清冊");
			
			DataCount = ef.print(conn, Form, comp_id);
			
			if (DataCount > 0){//表示有資料
				
				String cur_date = tool.ymdTostring(tools.getSysDate());
				String name ="員工生日清冊"+cur_date+".xls";
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(name.getBytes("BIG5"), "iso8859-1"));
				ServletOutputStream ouputStream;
				try {
					InputStream in = new FileInputStream(ef.write());
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
				    
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("MSG","沒有資料可列印!!");
			}
			//EMS-v2.0無法使用下列方法
			/*if (DataCount > 0){//表示有資料
				//存入檔案
				String FileName	= ef.write();
				request.setAttribute("FileName", FileName);
				//String name ="員工生日清冊"+ems_tool.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";	
				String name = "員工生日清冊.xls";
				request.setAttribute("DisplayFileName",  new String(name.getBytes("BIG5"), "iso8859-1"));
				//request.setAttribute("DisplayFileName", ef.TemplateName);
				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"'></iframe>");
				request.setAttribute("MSG","請點選下載檔案，下載報表檔案!!");
	
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("MSG","沒有資料可列印!!");
			}*/
			
			//產生下拉選單
			this.generateSelectBox(conn, Form, request);
    		
			request.setAttribute("Form1Datas", Form);
    		
    	}catch(Exception e){
			e.printStackTrace();
			request.setAttribute("MSG","列印失敗!!");
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return mapping.findForward("success");
		//return queryForm(mapping, form, request, response);
		
	}

}
