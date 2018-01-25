package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.popo.forms.EHF310100M0F;
import com.spon.ems.popo.forms.EHF331300M0F;
import com.spon.ems.popo.forms.EX331300R0F;
import com.spon.ems.popo.models.EHF310100;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;

/**
 * 用餐客戶明細報表
 * (Action)
 */
public class EHF331300M0A extends NewDispatchAction {

	
	private BA_TOOLS tools ;
	private EMS_ACCESS ems_access;
	

	
	public EHF331300M0A(){
		tools = BA_TOOLS.getInstance();
		ems_access = EMS_ACCESS.getInstance();
	}
	
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		
		EHF331300M0F Form = (EHF331300M0F) form;	
		BA_TOOLS tools = BA_TOOLS.getInstance();
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
		
		try{	
			
			//取得當前登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			
			
			request.setAttribute("Form1Datas", Form);
		
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		return mapping.findForward("success");
	}
	
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		EHF331300M0F Form = (EHF331300M0F) form;	
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		
		Connection conn = null;		

		ActionErrors lc_errors = new ActionErrors();



		String Sendmeal_DATE_START = Form.getEHF331300T0_10();//送餐開始日期
		String Sendmeal_DATE_END = Form.getEHF331300T0_11();//送餐結束日期
		
		String Cabinet_Num_START = Form.getEHF331300T0_03_B();//櫃號起
		String Cabinet_Num_END = Form.getEHF331300T0_03_E();//櫃號迄
		
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
    		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			
			if ((Cabinet_Num_START!=null && !Cabinet_Num_START.equals(""))&&//櫃號(起、迄都有填)
					Cabinet_Num_END!=null && !Cabinet_Num_END.equals("")){
						if (Integer.parseInt(Cabinet_Num_START)>Integer.parseInt(Cabinet_Num_END)){
								request.setAttribute("MSG", "櫃號請由小到大填寫");
								return init(mapping, Form, request, response);

						}
						}

			
			String Begin = null;
			String End = null;
			
				
					if (End != null && !End.equals("") && !"".equals(Begin)) {
						if (Begin != null && !Begin.equals("") && !"".equals(End)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
							Date Begindate = sdf.parse(Begin);
							Date Enddate = sdf.parse(End);
							if (Begindate.after(Enddate)){	
									request.setAttribute("MSG", "送餐開始日期不能大於送餐結束日期");
									return init(mapping, Form, request, response);
							}
						}
					}
			
			
			int DataCount =0;
			
			String comp_id = getLoginUser(request).getCompId();
			
			String sql = "" +
			 "       SELECT  " +
			 "	     a.EHF310100T0_01 as EHF310100T0_01 ,  " +//系統編號
			 "		 a.EHF310100T0_02 as EHF310100T0_02" +//檔案編號
			 "        ,a.EHF310100T0_04 as EHF310100T0_04" +//姓名
			 "       ,a.EHF310100T0_03 as EHF310100T0_03," +//櫃號
			 "       b.EHF310400T0_02 as EHF310400T0_02, " +//訂餐天數
			 "       b.EHF310400T0_05 AS EHF310400T0_05,  "+//已付金額
			 "     c.EHF310500T0_06 AS EHF310500T0_06," +//贈品類別
			 "       a.EHF310100T0_08 as EHF310100T0_08" +//訂餐日期

			 
			 
			 /*
			 a 產婦基本資料
			 b 付款資訊1
			 c 贈品資訊

			 */
			 "  FROM EHF310100T0 a " +
			 " 		 LEFT JOIN ehf310400t0 b ON a.EHF310100T0_01 = b.EHF310400T0_01 " +
			 "		 LEFT JOIN ehf310500t0 c ON a.EHF310100T0_01 = c.EHF310500T0_01" +
			 "  WHERE 1 = 1  " ;


			if(!"".equals(Sendmeal_DATE_START)){	//送餐日期(起)
					sql+="	AND DATE_FORMAT(a.EHF310100T0_10, '%Y/%m/%d') >= '"+Sendmeal_DATE_START+"'" ;
				}
			if(!"".equals(Sendmeal_DATE_END)){	//送餐日期(迄)
					sql+="	AND DATE_FORMAT(a.EHF310100T0_10, '%Y/%m/%d') <= '"+Sendmeal_DATE_END+"'" ;
				}
			if(!"".equals(Cabinet_Num_START)){	//櫃號(起)
					sql += " and a.EHF310100T0_03 >= "+Integer.parseInt(Cabinet_Num_START);
				}
			if(!"".equals(Cabinet_Num_END)){	//櫃號(迄)
					sql += " and a.EHF310100T0_03 <= "+Integer.parseInt(Cabinet_Num_END);
				}

	
			sql+="		AND a.EHF310100T0_34 = '"+comp_id+"'" +//公司代碼  " +
			 "			ORDER BY a.EHF310100T0_02 ASC  ";

//			System.out.println(sql);//顯示sql
			
			rs = stmt.executeQuery(sql);
			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			
			//			產生Excel元件
			AuthorizedBean authbean = getLoginUser(request); 
			try{
				getLoginUser(request).getCompId();
				//取得公司名稱
				Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getUserId());
				hr_tools.close();
				
				EX331300R0F ef = new EX331300R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getEmployeeID(), request);
				
				
				
//				ef.setHeadValue(0,1,"A","用餐客戶明細報表",false,"");
				
				
				
				while(rs.next()) {		//列印資料

					ef.next();
					ef.setDetail(1,"A", DataCount+1+"",false);  //項次
					ef.setDetail(1,"B", rs.getString("EHF310100T0_02")==null?"":rs.getString("EHF310100T0_02"),false);//檔案編號
					ef.setDetail(1,"C", rs.getString("EHF310100T0_04")==null?"":rs.getString("EHF310100T0_04"),false);//姓名
					
					/**
					 * 開始計算訂餐數量A、B、C
					 * */
					Form.setEHF331300T0_01(rs.getString("EHF310100T0_01"));

					//取得訂餐數量
					EHF310100 ehf310100 = new EHF310100(comp_id);
					String EHF310100T0_01 = rs.getString("EHF310100T0_01");//系統代碼
					Map orderMap = ehf310100.getOrderMap(EHF310100T0_01,"",comp_id);
					
					int countA = (Integer)orderMap.get("EHF310400T2_03_A");
					int countB = (Integer)orderMap.get("EHF310400T2_03_B");
					int countC = (Integer)orderMap.get("EHF310400T2_03_C");
					int MABC = 0;
					if(countA<countB && countA<countC){
						//代表A餐數量最少
						MABC = countA;
					}else if(countB<countA && countB<countC){
						//代表B餐數量最少
						MABC = countB;
					}else if(countC<countA && countC<countB){
						//代表C餐數量最少
						MABC = countC;
					}else{
						//代表ABC餐數量皆一樣
						Random ran = new Random();
						switch (ran.nextInt(3)){
					    case 0: 
					    	MABC = countA; 
					        break; 
					    case 1: 
					    	MABC = countB; 
					        break;
					    case 2: 
					    	MABC = countC; 
					        break;    
					    default: 
					    	MABC = 0; 
						}
					}
					
					countA -= MABC; countB -= MABC; countC -= MABC;
					ef.setDetail(1,"D", String.valueOf((Integer)MABC),true);//訂餐總數
					ef.setDetail(1,"E", String.valueOf((Integer)countA),true);//訂餐天數A
					ef.setDetail(1,"F", String.valueOf((Integer)countB),true);//訂餐天數B
					ef.setDetail(1,"G", String.valueOf((Integer)countC),true);//訂餐天數C
					
					/**
					 * 開始計算付款資訊
					 * */
					int final_price=0;//實際金額
					int unpay_goad=0;//未付金額
					//建立查詢資料List
					List queryPay1Data = new ArrayList();
					//Map pay1Map = new HashMap();
					
					queryPay1Data = this.queryPay1Data(Form.getEHF331300T0_01(),comp_id,queryPay1Data);
					Iterator it = queryPay1Data.iterator(); 
					Map tempMap = null;
					tempMap = (Map) it.next();
					final_price = (Integer)tempMap.get("EHF310400T0_03")-(Integer)tempMap.get("EHF310400T0_04");//實際金額 = 定價 - 折扣金額  
					unpay_goad = (Integer)final_price-(Integer)tempMap.get("EHF310400T0_05");//未付金額 = 實際金額 - 已付金額
//					pay1Map.put("final_price", final_price);
//					pay1Map.put("unpay_goad", unpay_goad);
					ef.setDetail(1,"H", unpay_goad,true,"##,###");//未付金額
					ef.setDetail(1,"I", (Integer)tempMap.get("EHF310400T0_05"),true,"##,###");//已付金額
					
					ef.setDetail(1,"J", this.getITEM_VALUE(rs.getString("EHF310500T0_06"), "GIFT", comp_id),false);//已送贈品
					
					DataCount++;
					ehf310100.close();
			}
				
				rs.close();
				stmt.close();
				
				String name ="用餐客戶明細報表.xls";
				String FileName="";
				
				if(DataCount>0){
					//String cur_date = tool.ymdTostring(tools.getSysDate());
					//存入檔案
					FileName=ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
					return init(mapping, Form, request, response);
				}
				else{
					saveErrors(request, lc_errors);
					request.setAttribute("ERR_MSG","沒有資料可列印!!");
					return init(mapping, Form, request, response);
				}
				
			}catch(Exception E){
				E.printStackTrace();
				request.setAttribute("ERR_MSG","列印失敗!!");
			}finally{
//				ef.write();
			}
			
		}catch (Exception E) {
			E.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return init(mapping, Form, request, response);
		
		
	}
		
	protected void generateSelectBox(Connection conn, ActionForm form,HttpServletRequest request) {
		
	}
	
	/**
	* ITEM_ID轉為ITEM_VALUE
	*/
	public String getITEM_VALUE(String item_ID, String classKey, String comp_id ){
		
		
		String ItemValue = "";
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
			
		try{
				
			String sql = "" +
			" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
			" FROM EMS_CategoryT0 " +
			" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_01 = '"+classKey+"' " +
			" AND EMS_CategoryT1_04 = '"+item_ID+"' " +
			" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
			" ORDER BY EMS_CategoryT1_07 ";		
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
				
			if(rs.next()){
				ItemValue = rs.getString("ITEM_VALUE");
			}
				
			rs.close();
			stmt.close();
				
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (conn != null && !conn.isClosed()){
					conn.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
			
		return ItemValue;
	}
	
	
	public List queryPay1Data(String EHF310100T0_01,String COMP_ID,List queryPay1Data) {
		// TODO Auto-generated method stub
		
		
		
					
			//QueryEdit
			String sql = "" +
			" SELECT * " +
			" FROM EHF310400T0 " +
			" WHERE 1=1 " +
			" AND EHF310400T0_01 = '"+EHF310100T0_01+"'" + 
			" AND EHF310400T0_06 = '"+COMP_ID+"'" ;
			
			
//			System.out.println(sql);
			try {
				BaseFunction base_tools = new BaseFunction();
				PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
				queryPay1Data = base_tools.resultSetToList(pstmt.executeQuery(sql));
				base_tools.close();
				pstmt.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return queryPay1Data;
	}
	
	
	
	
	

}
