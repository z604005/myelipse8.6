package com.spon.ems.popo.actions;

import java.sql.Connection;
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

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF330400M0F;
import com.spon.ems.popo.forms.EX330400R0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

/**
 * 送餐確認表
 * (Action)
 * @author maybe
 * @version 1.0
 * @created 下午3:54:39
 */
public class EHF330400M0A extends QueryAction {
	
	private BA_TOOLS tool;
	
	public EHF330400M0A()
	{
		tool = BA_TOOLS.getInstance();
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
		
		EHF330400M0F Form = new EHF330400M0F();
		
		return (ActionForm) Form;
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
	 * 列印查詢結果 (全部)，目前無法印到第2頁
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		EHF330400M0F Form = (EHF330400M0F) form;
		Connection conn = null;
		List OrderList = new ArrayList();
		
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
    		ActionErrors lc_errors = Form.validate(mapping, request, conn);
    		
    		try{
				//建立檢核元件
				BA_Vaildate ve = BA_Vaildate.getInstance();
				ve.isEmpty(lc_errors, Form.getEHF310100T1_02(), "EHF310100T1_02", "不可空白");
				ve.isEmpty(lc_errors, Form.getEHF310100T1_04(), "EHF310100T1_04_TXT", "不可空白");
				ve.isEmpty(lc_errors, Form.getEHF010100T0_01(), "EHF010100T0_02", "不可空白");
				
			}catch(Exception e){
				request.setAttribute("MSG", "檢核程式執行失敗!!");
				e.printStackTrace();
			}
    		
    		if (lc_errors.isEmpty()) {
    			
    			String comp_id = getLoginUser(request).getCompId();
    			
        		//取得在該日期、路線中有訂餐(住宅)
    			List R_OrderList = this.getMeals(conn, Form.getEHF310100T1_02(), Form.getEHF310100T1_04(), "R", "", comp_id);
    			
    			if(!R_OrderList.isEmpty()){
    				OrderList.addAll(R_OrderList);
				}
    			//取得在該日期、路線中有訂餐(醫院)
    			List H_OrderList = this.getMeals(conn, Form.getEHF310100T1_02(), Form.getEHF310100T1_04(), "H", "", comp_id);
    			
    			if(!H_OrderList.isEmpty()){
    				OrderList.addAll(H_OrderList);
				}
        		
        		//產生Excel元件
        		EX330400R0F ef = new EX330400R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),
        				request,OrderList.size());
        		        		
        		int DataCount = 0;
        		int i = 0;
        		int b = 0;//早餐數量
        		int l = 0;//中餐數量
        		int d = 0;//晚餐數量
        		int hospital = 0;
        		Iterator it = OrderList.iterator();
        		Map tempMap = null;
        		String form_id = "";
        		
        		while(it.hasNext()){
        			
        			tempMap = (Map) it.next();
          			
            		if(DataCount==0){
            			ef.setHeadValue(0, 1, "C", Form.getEHF310100T1_02(),false, "");//日期
        				ef.setHeadValue(1, 2, "A", Form.getEHF010100T0_05(),false, "");//外送人員
        				ef.setHeadValue(2, 2, "G", Form.getEHF010100T0_05(),false, "");//外送人員
        				ef.setHeadValue(3, 2, "L", Form.getEHF010100T0_05(),false, "");//外送人員
        				ef.setHeadValue(4, 3, "A", Form.getEHF310100T1_04_TXT(),false, "");//路線
            		}
    				ef.next();//下一筆
    				
    				/*if(OrderList.size() > 105){
						
							for (int c = 0; c <= 105; i++) {
								ef.CopyCellFormat(5 + c , "A", 4, "A");// 複製格式
								ef.CopyCellFormat(5 + i , "B", 4, "B");// 複製格式
								ef.CopyCellFormat(5 + i , "C", 4, "C");// 複製格式
								ef.CopyCellFormat(5 + i , "D", 4, "D");// 複製格式
								ef.CopyCellFormat(5 + i , "E", 4, "E");// 複製格式
								ef.CopyCellFormat(5 + i , "F", 4, "F");// 複製格式
								ef.CopyCellFormat(5 + i , "G", 4, "G");// 複製格式
								ef.CopyCellFormat(5 + i , "H", 4, "H");// 複製格式
								ef.CopyCellFormat(5 + i , "I", 4, "I");// 複製格式
								ef.CopyCellFormat(5 + i , "J", 4, "J");// 複製格式
							}
						
					}*/
    				
    				//System.out.println(i);	
    				if("R".equals((String)tempMap.get("EHF310100T1_04"))){
    					if(!form_id.equals((String)tempMap.get("EHF310100T1_01")) && DataCount!=0){
        					i++;
        				}
    					
    					if(i>34){
        					i = 0;
        					b = 0;//早餐數量
        	        		l = 0;//中餐數量
        	        		d = 0;//晚餐數量
        				}
    					
    					ef.setValue(4+i,"A",((String)tempMap.get("EHF310100T0_31")).substring(0,6),false);//地址
            			
                		if("A".equals((String)tempMap.get("EHF310100T1_03"))){
                				
                			ef.setValue(4+i,"B",(String)tempMap.get("EHF310100T0_04"),false);//產婦姓名
                			ef.setValue(4+i,"C",(String)tempMap.get("EHF310100T0_03"),false);//櫃號
                			b++;
                				
                		}else if("B".equals((String)tempMap.get("EHF310100T1_03"))){
                				
                			ef.setValue(4+i,"G",(String)tempMap.get("EHF310100T0_04"),false);//產婦姓名
                			ef.setValue(4+i,"H",(String)tempMap.get("EHF310100T0_03"),false);//櫃號
                			l++;
                				
                		}else if("C".equals((String)tempMap.get("EHF310100T1_03"))){
                				
                			ef.setValue(4+i,"L",(String)tempMap.get("EHF310100T0_04"),false);//產婦姓名
                			ef.setValue(4+i,"M",(String)tempMap.get("EHF310100T0_03"),false);//櫃號
                			d++;
                				
                		}
                			
                		form_id = (String)tempMap.get("EHF310100T1_01");
                			
                		DataCount++;
            		}else if("H".equals((String)tempMap.get("EHF310100T1_04"))){
            			if(hospital==0 || !form_id.equals((String)tempMap.get("EHF310100T1_01"))){
            				i++;//第一筆醫院要跟住宅分開
            			}
            			
            			if(i>34){
        					i = 0;
        					b = 0;//早餐數量
        	        		l = 0;//中餐數量
        	        		d = 0;//晚餐數量
        				}
            			
            			ef.setValue(4+i,"A",(String)tempMap.get("EHF300200T0_02")==null?"醫院未填":(String)tempMap.get("EHF300200T0_02")
            					+"("+(String)tempMap.get("EHF310100T0_15")+")",false);//醫院
            			
            			if("A".equals((String)tempMap.get("EHF310100T1_03"))){
            				
                			ef.setValue(4+i,"B",(String)tempMap.get("EHF310100T0_04"),false);//產婦姓名
                			ef.setValue(4+i,"C",(String)tempMap.get("EHF310100T0_03"),false);//櫃號
                			b++;
                				
                		}else if("B".equals((String)tempMap.get("EHF310100T1_03"))){
                				
                			ef.setValue(4+i,"G",(String)tempMap.get("EHF310100T0_04"),false);//產婦姓名
                			ef.setValue(4+i,"H",(String)tempMap.get("EHF310100T0_03"),false);//櫃號
                			l++;
                				
                		}else if("C".equals((String)tempMap.get("EHF310100T1_03"))){
                				
                			ef.setValue(4+i,"L",(String)tempMap.get("EHF310100T0_04"),false);//產婦姓名
                			ef.setValue(4+i,"M",(String)tempMap.get("EHF310100T0_03"),false);//櫃號
                			d++;
                				
                		}
                			
                		form_id = (String)tempMap.get("EHF310100T1_01");
                		
                		hospital++;
                		DataCount++;
            		}
    				
    				ef.BeforeChangePage(b,l,d);
        		} 
        		
        		//ef.EndDocument(b,l,d);
        		
        		//傳入前端需要的檔名
    			String name ="";
    			String FileName="";
    			
    			if(DataCount>0){
    				String cur_date = tool.ymdTostring(tools.getSysDate());
    				name ="送餐確認表"+cur_date+".xls";
    				//存入檔案
    				FileName=ef.write();
    				request.setAttribute("MSG","列印完成!!");
    				//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
    				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
    				return init(mapping, Form, request, response);
    				
    			}else{
    				request.setAttribute("MSG","沒有資料可列印!!");
    			}
    			
    		}else{
				generateSelectBox(conn, Form, request);
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return init(mapping, form, request, response);
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

	/**
	 * 取得在該日期、路線中有訂餐
	 * @param conn
	 * @param query_date
	 * @param route_id
	 * @param route_type
	 * @param meal_type
	 * @param comp_id
	 * @return
	 */
	private List getMeals(Connection conn, String query_date, String route_id, String route_type, String meal_type, String comp_id) {
		// TODO Auto-generated method stub
		
		BaseFunction base_tools = new BaseFunction(comp_id);
		List OrderList = new ArrayList();
		String sql = "";
		
		try{
			sql = " SELECT " +
				  " EHF310100T1_01, EHF310100T1_02, EHF310100T1_03, EHF310100T1_04, " +
				  " EHF310100T0_03, EHF310100T0_04, EHF310100T0_11, EHF310100T0_15, EHF310100T0_16, EHF310100T0_17, EHF310100T0_31, " +
				  " EHF300200T0_02 " +
				  " FROM EHF310100T1 " +
				  " LEFT JOIN EHF310100T0 ON EHF310100T0_01 = EHF310100T1_01 AND EHF310100T0_34 = EHF310100T1_06 " +
				  " LEFT JOIN EHF300200T0 ON EHF300200T0_01 = EHF310100T0_11 AND EHF300200T0_06 = EHF310100T0_34 " +
				  " WHERE 1=1 " ;
				  if("R".equals(route_type)){
					  sql += " AND EHF310100T0_17 = '"+route_id+"' ";
				  }else if("H".equals(route_type)){
					  sql += " AND EHF310100T0_16 = '"+route_id+"' ";
				  }else{
					  sql += " AND (EHF310100T0_16 = '"+route_id+"' OR EHF310100T0_17 = '"+route_id+"')" ;
				  }
			sql +=
				  " AND EHF310100T1_02 = '"+query_date+"'" ;
				  if(!"".equals(route_type)){
					  sql +=  " AND EHF310100T1_04 = '"+route_type+"'" ;
				  }
				  if(!"".equals(meal_type)){
					  sql +=  " AND EHF310100T1_03 = '"+meal_type+"'" ;
				  }
			sql +=
				  " AND EHF310100T1_06 = '"+comp_id+"'" +
				  " ORDER BY EHF310100T1_01, EHF310100T1_03 ";
			
			OrderList = base_tools.queryList(sql);
			
			base_tools.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return OrderList;
	}

}
