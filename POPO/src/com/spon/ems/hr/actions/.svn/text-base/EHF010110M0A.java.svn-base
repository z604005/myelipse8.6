package com.spon.ems.hr.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import com.spon.ems.hr.forms.EHF010110M0F;
import com.spon.ems.hr.forms.EX010110R0F;
import com.spon.ems.hr.models.EHF010110;
import com.spon.utils.util.BA_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

public class EHF010110M0A extends QueryAction {
	
	private BA_TOOLS tool;
	//private BA_EMS_TOOLS ems_tool;
	public EHF010110M0A()
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
		
		EHF010110M0F Form = new EHF010110M0F();
		List list = new ArrayList();
		
		Form.setEHF010110M0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF010110M0F Form = (EHF010110M0F) form;
		Map return_map = new HashMap();
		EHF010110M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF010110M0F> querylist = new ArrayList<EHF010110M0F>();
			
			//建立EHF010110元件
			EHF010110 ehf010110 = new EHF010110(comp_id);
			
			//建立EHF010110M0 查詢資料
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf010110_queryList = ehf010110.queryData(queryCondMap);
			
			Iterator it = ehf010110_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF010110M0F();
				
				bean.setHR_EmployeeSysNo((String)tempMap.get("HR_EmployeeSysNo"));	//員工系統代碼
				bean.setHR_DepartmentName((String)tempMap.get("HR_DepartmentName"));	//部門名稱				
				bean.setHR_EmployeeNo((String)tempMap.get("HR_EmployeeNo"));	//員工工號
				bean.setEHF010106T0_04((String)tempMap.get("EHF010106T0_04"));	//員工姓名
				if(!"".equals((String)tempMap.get("EHF010106T0_01"))){
					bean.setEHF010106T0_01((String)tempMap.get("EHF010106T0_01"));	//身分證字號
				}else if(!"".equals((String)tempMap.get("EHF010106T0_02"))){
					bean.setEHF010106T0_01((String)tempMap.get("EHF010106T0_02"));	//護照號碼
				}else{
					bean.setEHF010106T0_01((String)tempMap.get("EHF010106T0_03"));	//居留證號碼
				}
				bean.setEHF010106T0_10((String)tempMap.get("EHF010106T0_10"));
				if("1".equals((String)tempMap.get("EHF010106T0_07"))){
					bean.setEHF010106T0_07("女");
				}else{
					bean.setEHF010106T0_07("男");
				}
				bean.setEHF010106T0_13((String)tempMap.get("EHF010106T0_13"));
												
				querylist.add(bean);
				
			}
			
			//設定querylist
			Form.setEHF010110M0_LIST(querylist);
			
			//關閉EHF010110元件
			ehf010110.close();
			
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
	 * 列印查詢結果  -- 員工基本資料卡
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		EHF010110M0F Form = (EHF010110M0F) form;
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
    		//取得各別員工的相片路徑和檔名
    		String sql = ""+
    		" SELECT A.HR_EmployeeSysNo, A.EHF010106T0_23 FROM EHF010106T0 A " +
    		" LEFT JOIN EHF010106T6 B ON A.HR_EmployeeSysNo = B.HR_EmployeeSysNo AND A.HR_CompanySysNo = B.HR_CompanySysNo " +						
			" LEFT JOIN EHF010108T0 H ON B.HR_DepartmentSysNo = H.HR_DepartmentSysNo AND B.HR_CompanySysNo = H.HR_CompanySysNo " +
			" LEFT JOIN EHF010109T0 I ON B.HR_JobNameSysNo = I.HR_JobNameSysNo AND B.HR_CompanySysNo = I.HR_CompanySysNo " +
    		" WHERE 1=1 " ;
    		
    		if (!"".equals(Form.getHR_EmployeeSysNo()) && !"".equals(Form.getHR_DepartmentSysNo())){  //員工
				sql += " AND B.HR_DepartmentSysNo = '"+Form.getHR_DepartmentSysNo()+"' " +  //部門代號
					   " AND A.HR_EmployeeSysNo = '"+Form.getHR_EmployeeSysNo()+"' ";  //員工工號
			}else if(!"".equals(Form.getHR_DepartmentSysNo())){  //部門組織
				sql += " AND B.HR_DepartmentSysNo = '"+Form.getHR_DepartmentSysNo()+"' ";  //部門代號
			}
    		
    		sql += "" +
    		" AND A.HR_CompanySysNo like '"+comp_id+"' " ;
    		
    		Statement stmt =conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs=stmt.executeQuery(sql);
			
			String PrintImage = "";			
			List printList = new ArrayList();
			
			while(rs.next()) {
				
				Map dataMap = new HashMap();
				
				if(!"".equals(rs.getString("EHF010106T0_23")) && rs.getString("EHF010106T0_23")!=null){
	    			PrintImage = rs.getString("EHF010106T0_23");
				}else{					
					PrintImage = "pic\\No_picture.png";
				}
	    		
	    		java.io.File imageFile = new java.io.File(getServlet().getServletContext().getRealPath("") +"\\config\\"+ PrintImage);
	    		
	    		dataMap.put("HR_EmployeeSysNo",rs.getString("HR_EmployeeSysNo"));
	    		dataMap.put("imageFile",imageFile);
	    		printList.add(dataMap);
				
			}
			rs.close();
			stmt.close();
    		
    		//產生Excel元件
			EX010110R0F ef = new EX010110R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getUserId(),request);
    		   		
			int DataCount =0;
									
			// 列印報表表頭
			ef.printHeadValue("", "", tool.ymdTostring(tool.getSysDate())+ "", "思邦科技股份有限公司", "員工基本資料卡");
			
			DataCount = ef.print(conn, Form, printList, comp_id);
			
			if (DataCount > 0){//表示有資料
				
				String cur_date = tool.ymdTostring(tools.getSysDate());
				String name ="員工基本資料卡"+cur_date+".xls";
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
		
		return queryForm(mapping, form, request, response);
	}
	
	protected boolean chk_Picture(String picture_Path){
		boolean chk_flag = false;
		File file = new File(picture_Path);
		if (file.exists()) {
			chk_flag=true;
		}
		return chk_flag;
	}

}
