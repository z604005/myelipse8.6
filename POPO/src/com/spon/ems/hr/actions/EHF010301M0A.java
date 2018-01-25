package com.spon.ems.hr.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;


import com.p6spy.engine.spy.P6PreparedStatement;
import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.hr.forms.EHF010301M0F;
import com.spon.ems.hr.forms.EX010301R0F;
import com.spon.ems.hr.models.EHF010301;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * (Action)假別時數清單
 * @author maybe
 * @version 1.0
 * @created 2015/8/18 上午9:26:52
 */
public class EHF010301M0A extends QueryAction {
private EMS_ACCESS ems_access;
	
	public EHF010301M0A(){
		ems_access = EMS_ACCESS.getInstance();
	}
	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		return false;
		// TODO Auto-generated method stub
		
	
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF010301M0F Form = new EHF010301M0F();
		
		List list = new ArrayList();
		
		Form.setEHF010301T0_LIST(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF010301M0F Form = (EHF010301M0F) form;
		Map return_map = new HashMap();
		EHF010301M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);	
		try{
			//建立空清單
			List<EHF010301M0F> querylist = new ArrayList<EHF010301M0F>();
			
			//建立EHF020200元件
			EHF010301 ehf010301 = new EHF010301(comp_id);
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
			
			//使用元件進行處理
			List ehf010301_queryList =ehf010301.queryData(queryCondMap);

			Iterator it = ehf010301_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					
					bean = new EHF010301M0F();
				
					bean.setEHF010300T0_02((String)tempMap.get("年度"));	//年度
					bean.setEHF010300T0_06((String)tempMap.get("假別名稱"));	//假別
					bean.setEHF010300T0_07((String)tempMap.get("休假時數"));	//休假時數
					bean.setEHF010300T0_08((String)tempMap.get("剩餘時數"));	//剩餘時數
					bean.setEHF010300T0_09( (String.valueOf( tempMap.get("使用期限起") )+"~"+( (String.valueOf( tempMap.get("使用期間訖") ) ) ) ));  //使用期限區間
					bean.setEHF010300T0_04_TXT((String) ((Map)depMap.get((String)tempMap.get("部門代號"))).get("SHOW_DESC"));  //部門名稱
					bean.setEHF010300T0_05_TXT((String) ((Map)empMap.get((String)tempMap.get("員工工號"))).get("EMPLOYEE_NAME"));  //姓名

			
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF010301T0_LIST(querylist);
			
			//關閉EHF010301元件
			ehf010301.close();
			hr_tools.close();
			
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
	 * 列印查詢結果 (全部)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */

	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		EHF010301M0F Form = (EHF010301M0F) form;		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		String comp_id = getLoginUser(request).getCompId();
		ArrayList list = new ArrayList();
		
		
		
		
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
    	
    	try{
    		//產生Excel元件
			EX010301R0F ef = new EX010301R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request);
    		
//			Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs= null;
			int DataCount = 0;
			
	
	
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			
			Map empMap = hr_tools.getEmpNameMap(comp_id);//員工Map
			Map depMap = hr_tools.getDepNameMap(comp_id);//部門Map
			hr_tools.close();
			
			String empName="";

			
//			if(!"".equals(Form.getEHF010300T0_05())||!"".equals(Form.getEHF010300T0_04())){
//				//整理Map，如有指定部門與員工，將會被剔除，只會留下指定的部門或是員工
//				Iterator iter_emp = empMap.entrySet().iterator(); 
//				while (iter_emp.hasNext()) {
//					Map.Entry entry = (Map.Entry) iter_emp.next(); 
//				    String key = (String)entry.getKey(); 
//				    if(!"".equals(Form.getEHF010300T0_05())){
//				    	//當有指定員工，必須將其餘人員存起來，之後從Map刪除
//				    	if(!key.equals(Form.getEHF010300T0_05())){
//				    		empName+=key+",";
//				    	}	
//				    }else if(!"".equals(Form.getEHF010300T0_04())){
//				    	//指定部門時，必須將非此部門的員工剔除
//				    	String dep = (String)((Map)empMap.get(key)).get("DEPT_ID");//部門
//				    	if(!dep.equals(Form.getEHF010300T0_04())){
//				    		empName+=key+",";
//				    	}
//				    }   
//				}
//				
//				if(!"".equals(empName)){
//					String[] empNameList=empName.split(",");
//					for(int i=0; i < empNameList.length; i++){    
//						   //System.out.println(empNameList[i]); 
//						   empMap.remove(empNameList[i]);
//					}   
//				}
//			
//			}
			

			
			Iterator iter = empMap.entrySet().iterator(); 
			
			//取的列印用的SQL語句
			String sql = this.getPrintSQL(conn,Form.getEHF010300T0_04(),Form.getEHF010300T0_05(),Form.getEHF000200T0_03(),Form.getEHF010100T0_05(),Form.getEHF010100T0_05(),
					Form.getEHF010300T0_02(),Form.getEHF010300T0_06(),Form.getEHF010300T0_07(),Form.getEHF000200T0_03(),
					Form.getEHF010300T0_08());
			

		
			rs = stmt.executeQuery(sql);
			EHF010301M0F bean = null;

			Map tempMap = null;
			while(rs.next() ) {
				
//			    Map.Entry entry = (Map.Entry) iter.next(); 
				if(DataCount==0){
				ef.setHeadValue(1,1,"A","休假計數清單",false,""); //列印公司抬頭
				}
				ef.next();

				ef.setDetail(1,"A", rs.getString("單位"),true);
				ef.setDetail(1,"B", rs.getString("姓名"),true);
				ef.setDetail(1,"C", rs.getString("年度"),true);
				ef.setDetail(1,"D", rs.getString("假別名稱"),true);
				ef.setDetail(1,"E", rs.getString("休假時數"),true);
				ef.setDetail(1,"F", rs.getString("剩餘時數"),true);
				ef.setDetail(1,"G", rs.getString("使用期限"),true);
				
				
				ef.setDetail(1,"A", depMap.containsKey(rs.getString("單位"))==true?
						(String) ((Map)depMap.get(rs.getString("單位"))).get("SHOW_DESC"):rs.getString("單位"),false);//部門
				ef.setDetail(1,"B", empMap.containsKey(rs.getString("姓名"))==true?
						(String) ((Map)empMap.get(rs.getString("姓名"))).get("EMPLOYEE_NAME"):rs.getString("姓名"),false);//姓名

//				bean.setDATA01(depMap.containsKey(rs.getString("部門代號"))==true?
//				(String) ((Map)depMap.get(rs.getString("部門代號"))).get("SHOW_DEPT_ID")+"/"+
//				(String) ((Map)depMap.get(rs.getString("部門代號"))).get("SHOW_DESC"):rs.getString("部門名稱"));
//				
			System.out.println(sql);
				bean = new EHF010301M0F();

				bean.setEHF010300T0_04(depMap.containsKey(rs.getString("單位"))==true?
						(String) ((Map)depMap.get(rs.getString("單位"))).get("SHOW_DESC"):rs.getString("單位"));
				bean.setEHF010300T0_05(empMap.containsKey(rs.getString("姓名"))==true?
						(String) ((Map)empMap.get(rs.getString("姓名"))).get("EMPLOYEE_NAME"):rs.getString("姓名"));
				System.out.println("oo");
				list.add(bean);
				DataCount++;
				//EHF010201R0
			}
			ef.EndDocument();
			
		  	rs.close() ;

			stmt.close();

			
			//傳入前端需要的檔名
			String name ="";
			String FileName="";
			
			if (DataCount > 0){//表示有資料
				name="休假計數清單.xls";
				//存入檔案
				FileName = ef.write();
				request.setAttribute("MSG","列印完成!!");
				//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
				
				//顯示前端 Query Collection畫面
				request.setAttribute("collection", "show");
				request.setAttribute("Form1Datas",Form);
				request.setAttribute("Form2Datas",list);
				
				return init(mapping, Form, request, response);
			}else{
				request.setAttribute("MSG","沒有資料可列印!!");
			}
    		
			}catch (Exception E) {
			E.printStackTrace();
			request.setAttribute("MSG","列印失敗!!");
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return init(mapping, Form, request, response);
	}

	private String getPrintSQL(Connection conn,String EHF010300T0_04,String EHF010300T0_05,String EHF010300T0_02,String EHF010300T0_06,String EHF010300T0_07,String EHF010300T0_08,String EHF010300T0_09,String EHF010300T0_10,String EHF000200T0_03,String EHF010100T0_05) {
		
		String sql = "" ;
		
		try{
			
			
			sql += "" +
		
			" select EHF010300T0_04 as 單位 , EHF010300T0_05 as 姓名 ,EHF010300T0_02 as 年度, " +
			"  CASE EHF010300T0_06  " +
			"			 WHEN 'A01' THEN '病假'  " +
			"			 WHEN 'A02' THEN '事假'  " +
			"			 WHEN 'A03' THEN '特休'  " +
			"			 WHEN 'A04' THEN '公假'  " +
			"			 WHEN 'A05' THEN '產假'  " +
			"			 WHEN 'A06' THEN '婚假'  " +
			"			 WHEN 'A07' THEN '喪假'  " +
			"			 WHEN 'A08' THEN '輪休'  " +
			"			 WHEN 'A09' THEN '生理假'  " +
			"			 END AS 假別名稱                        " +
			"  ,EHF010300T0_07 as 休假時數 ,      " +
			"  EHF010300T0_08 as 剩餘時數,        " +
			"  CONCAT(EHF010300T0_09,'~',EHF010300T0_10) AS 使用期限    " +
			"  FROM EHF010300T0 a   " +
			"  LEFT JOIN EHF010100T0 b ON  a.EHF010300T0_05 = b.ehf010100t0_01  and  a.EHF010300T0_12=b.HR_CompanySysNo  " +
			"  LEFT JOIN EHF000200T0 c ON  a.EHF010300T0_04 = c.EHF000200T0_01  and  a.EHF010300T0_12=c.HR_CompanySysNo  " +
			"  WHERE 1=1   ";
		
			if(!"".equals(EHF010300T0_04) && EHF010300T0_04 != null){
				sql += "  and EHF010300T0_04 = ?  " ; }
		
			if(!"".equals(EHF010300T0_05) && EHF010300T0_05 != null){
				sql += "  and EHF010300T0_05 = ?  " ; }

			//執行SQL
			PreparedStatement pstmt = conn.prepareStatement(sql);
			P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt, null, sql);
			int indexid = 1;
			if(!"".equals(EHF010300T0_04) && EHF010300T0_04 != null){
				p6stmt.setString(indexid, EHF010300T0_04);
				indexid++;
			}
			if(!"".equals(EHF010300T0_05) && EHF010300T0_05 != null){
				p6stmt.setString(indexid, EHF010300T0_05);
				indexid++;
			}
//			System.out.println(p6stmt.getQueryFromPreparedStatement());
			sql=p6stmt.getQueryFromPreparedStatement();
			ResultSet rs = pstmt.executeQuery();
			
			p6stmt.close();
			pstmt.close();
	System.out.println(sql);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return sql;
	}
	
	
	

}
