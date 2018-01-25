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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF330900M0F;
import com.spon.ems.popo.forms.EX330900R0F;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;

/**
 * 特殊飲品對照表
 * (Action)
 * @author maybe
 * @version 1.0
 * @created 下午2:59:48
 */
public class EHF330900M0A extends QueryAction {
	
	private BA_TOOLS tool;
	
	public EHF330900M0A()
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
		
		EHF330900M0F Form = new EHF330900M0F();
		
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
	 * 不考慮印到第2頁
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		EHF330900M0F Form = (EHF330900M0F) form;
		Connection conn = null;
		String comp_id = getLoginUser(request).getCompId();
		
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
    		List DrinkList = this.getAllSpecDrink(conn, "Drink", "", comp_id);
    		
    		String A01 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "A", "01", comp_id, false);//杜仲水
    		String B01 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "B", "01", comp_id, false);//杜仲水
    		String C01 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "C", "01", comp_id, false);//杜仲水
    		String A02 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "A", "02", comp_id, false);//生化湯
    		String B02 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "B", "02", comp_id, false);//生化湯
    		String C02 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "C", "02", comp_id, false);//生化湯
    		String A03 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "A", "03", comp_id, false);//通乳水
    		String B03 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "B", "03", comp_id, false);//通乳水
    		String C03 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "C", "03", comp_id, false);//通乳水
    		String A04 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "A", "04", comp_id, false);//麥芽水
    		String B04 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "B", "04", comp_id, false);//麥芽水
    		String C04 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "C", "04", comp_id, false);//麥芽水
    		String A05 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "A", "05", comp_id, true);//韭菜
    		String B05 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "B", "05", comp_id, true);//韭菜
    		String C05 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "C", "05", comp_id, true);//韭菜
    		String A06 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "A", "06", comp_id, true);//其他
    		String B06 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "B", "06", comp_id, true);//其他
    		String C06 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "C", "06", comp_id, true);//其他
    		String A08 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "A", "08", comp_id, true);//無糖紅豆水
    		String B08 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "B", "08", comp_id, true);//無糖紅豆水
    		String C08 = this.getSpecDrinkId(conn, Form.getEHF310100T2_03(), "C", "08", comp_id, true);//無糖紅豆水
    		
    		String A_Other = A05+"，"+A06+"，"+A08;
    		String B_Other = B05+"，"+B06+"，"+B08;
    		String C_Other = C05+"，"+C06+"，"+C08;
    		/*
    		List A05List = this.getSpecDrink(conn, Form.getEHF310100T2_03(), "A", "05", comp_id);//韭菜
    		
    		Iterator A05_it = A05List.iterator();
    		Map A05_tempMap = null;
    		String A_Other = "";
    		int a05 = 0;
    		while(A05_it.hasNext()){
    			A05_tempMap = (Map) A05_it.next();
    			if(a05 == 0){
    				A_Other = (String)A05_tempMap.get("EMS_CategoryT1_05")+"："+(String)A05_tempMap.get("EHF310100T0_03");
    			}else{
    				A_Other += "、"+(String)A05_tempMap.get("EHF310100T0_03");
    			}
    			a05++;
    		}
    		
    		List B05List = this.getSpecDrink(conn, Form.getEHF310100T2_03(), "B", "05", comp_id);//韭菜
    		
    		Iterator B05_it = B05List.iterator();
    		Map B05_tempMap = null;
    		String B_Other = "";
    		int b05 = 0;
    		while(B05_it.hasNext()){
    			B05_tempMap = (Map) B05_it.next();
    			if(b05 == 0){
    				B_Other = (String)B05_tempMap.get("EMS_CategoryT1_05")+"："+(String)B05_tempMap.get("EHF310100T0_03");
    			}else{
    				B_Other += "、"+(String)B05_tempMap.get("EHF310100T0_03");
    			}
    			b05++;
    		}
    		
    		List C05List = this.getSpecDrink(conn, Form.getEHF310100T2_03(), "C", "05", comp_id);//韭菜
    		
    		Iterator C05_it = C05List.iterator();
    		Map C05_tempMap = null;
    		String C_Other = "";
    		int c05 = 0;
    		while(C05_it.hasNext()){
    			C05_tempMap = (Map) C05_it.next();
    			if(c05 == 0){
    				C_Other = (String)C05_tempMap.get("EMS_CategoryT1_05")+"："+(String)C05_tempMap.get("EHF310100T0_03");
    			}else{
    				C_Other += "、"+(String)C05_tempMap.get("EHF310100T0_03");
    			}
    			c05++;
    		}
    		
    		List A06List = this.getSpecDrink(conn, Form.getEHF310100T2_03(), "A", "06", comp_id);//其他
    		
    		Iterator A06_it = A06List.iterator();
    		Map A06_tempMap = null;
    		int a06 = 0;
    		while(A06_it.hasNext()){
    			A06_tempMap = (Map) A06_it.next();
    			if(a06 == 0){
    				if(!"".equals(A_Other)){
    					A_Other = A_Other+"，"+(String)A06_tempMap.get("EMS_CategoryT1_05")+"："+(String)A06_tempMap.get("EHF310100T0_03");
    				}else{
    					A_Other = (String)A06_tempMap.get("EMS_CategoryT1_05")+"："+(String)A06_tempMap.get("EHF310100T0_03");
    				}
    			}else{
    				A_Other += "、"+(String)A06_tempMap.get("EHF310100T0_03");
    			}
    			a06++;
    		}
    		
    		List B06List = this.getSpecDrink(conn, Form.getEHF310100T2_03(), "B", "06", comp_id);//其他
    		
    		Iterator B06_it = B06List.iterator();
    		Map B06_tempMap = null;
    		int b06 = 0;
    		while(B06_it.hasNext()){
    			B06_tempMap = (Map) B06_it.next();
    			if(b06 == 0){
    				if(!"".equals(B_Other)){
    					B_Other = B_Other+"，"+(String)B06_tempMap.get("EMS_CategoryT1_05")+"："+(String)B06_tempMap.get("EHF310100T0_03");
    				}else{
    					B_Other = (String)B06_tempMap.get("EMS_CategoryT1_05")+"："+(String)B06_tempMap.get("EHF310100T0_03");
    				}
    			}else{
    				B_Other += "、"+(String)B06_tempMap.get("EHF310100T0_03");
    			}
    			b06++;
    		}
    		
    		List C06List = this.getSpecDrink(conn, Form.getEHF310100T2_03(), "C", "06", comp_id);//其他
    		
    		Iterator C06_it = C06List.iterator();
    		Map C06_tempMap = null;
    		int c06 = 0;
    		while(C06_it.hasNext()){
    			C06_tempMap = (Map) C06_it.next();
    			if(c06 == 0){
    				if(!"".equals(C_Other)){
    					C_Other = C_Other+"，"+(String)C06_tempMap.get("EMS_CategoryT1_05")+"："+(String)C06_tempMap.get("EHF310100T0_03");
    				}else{
    					C_Other = (String)C06_tempMap.get("EMS_CategoryT1_05")+"："+(String)C06_tempMap.get("EHF310100T0_03");
    				}
    			}else{
    				C_Other += "、"+(String)C06_tempMap.get("EHF310100T0_03");
    			}
    			c06++;
    		}
    		*/
    		EX330900R0F ef = new EX330900R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request);
    		
    		int DataCount = 0;
    		
    		Iterator it = DrinkList.iterator();
    		Map tempMap = null;
    		
    		while(it.hasNext()){
    			
    			tempMap = (Map) it.next();
      			
        		if(DataCount==0){
        			ef.setHeadValue(0, 2, "E", "日期："+Form.getEHF310100T2_03(),false, "");//日期
        		}
				ef.next();//下一筆
				
				switch(Integer.parseInt((String)tempMap.get("EMS_CategoryT1_04"))){
				
				case 1://杜仲水
					
					ef.setValue(4,"D",A01,false);
					ef.setValue(6,"D",B01,false);
					ef.setValue(8,"D",C01,false);
					
					break;
				
				case 2://生化湯
					
					ef.setValue(4,"C",A02,false);
					ef.setValue(6,"C",B02,false);
					ef.setValue(8,"C",C02,false);
					
					break;
					
				case 3://通乳水
					
					ef.setValue(4,"E",A03,false);
					ef.setValue(6,"E",B03,false);
					ef.setValue(8,"E",C03,false);
					
					break;
					
				case 4://麥芽水
					
					ef.setValue(4,"B",A04,false);
					ef.setValue(6,"B",B04,false);
					ef.setValue(8,"B",C04,false);
					
					break;
					
				case 6://其他
					
					ef.setValue(4,"F",A_Other,false);
					ef.setValue(6,"F",B_Other,false);
					ef.setValue(8,"F",C_Other,false);
					
					break;
				
				}
				
				DataCount++;
				
    		}
    		
    		//傳入前端需要的檔名
			String name ="";
			String FileName="";
			
			if(DataCount>0){
				String cur_date = tool.ymdTostring(tools.getSysDate());
				name ="特殊飲品對照表"+cur_date+".xls";
				//存入檔案
				FileName=ef.write();
				request.setAttribute("MSG","列印完成!!");
				//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
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
	
	/**
	 * 將櫃號串成字串
	 * @param conn
	 * @param date
	 * @param meal_type
	 * @param drink_type
	 * @param comp_id
	 * @return
	 */
	private String getSpecDrinkId(Connection conn, String date,
			String meal_type, String drink_type, String comp_id, boolean name_flag) {
		// TODO Auto-generated method stub
		
		String spec_id = "";
		
		try{
			List SpecList = this.getSpecDrink(conn, date, meal_type, drink_type, comp_id);
    		
    		Iterator it = SpecList.iterator();
    		Map tempMap = null;
    		int count = 0;
    		while(it.hasNext()){
    			tempMap = (Map) it.next();
    			if(count == 0){
    				if(name_flag){
    					spec_id = (String)tempMap.get("EMS_CategoryT1_05")+"："+(String)tempMap.get("EHF310100T0_03");
    				}else{
    					spec_id = (String)tempMap.get("EHF310100T0_03");
    				}
    			}else{
    				spec_id += "、"+(String)tempMap.get("EHF310100T0_03");
    			}
    			count++;
    		}
    		
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return spec_id;
	}

	/**
	 * 取得特殊飲品
	 * @param conn
	 * @param type
	 * @param item_id
	 * @param comp_id
	 * @return
	 */
	private List getAllSpecDrink(Connection conn, String type, String item_id,
			String comp_id) {
		// TODO Auto-generated method stub
		
		BaseFunction base_tools = new BaseFunction(comp_id);
		List DrinkList = new ArrayList();
		String sql = "";
		
		try{
			sql = " SELECT " +
			  	  " EMS_CategoryT1_01, EMS_CategoryT1_04, EMS_CategoryT1_05, " +
			  	  " EMS_CategoryT1_06, EMS_CategoryT1_07 " +			  	  
			  	  " FROM EMS_CategoryT1 " +
			  	  " WHERE 1=1 " ;
			  	  if(!"".equals(type)){
			  		sql += " AND EMS_CategoryT1_01 = '"+type+"' ";
			  	  }
			  	  if(!"".equals(item_id)){
			  		sql += " AND EMS_CategoryT1_04 = '"+item_id+"' ";
			  	  }
			sql +=
				  " AND EMS_CategoryT1_04 <> '07'" +
				  " AND EMS_CategoryT1_09 = '"+comp_id+"'" +
				  " ORDER BY EMS_CategoryT1_07 ";
			
			DrinkList = base_tools.queryList(sql);
			
			base_tools.close();			  		
			
		}catch (Exception e) {
			e.printStackTrace();
		}
				
		return DrinkList;
	}
	
	/**
	 * 取得訂餐資訊-養生飲品明細
	 * @param conn
	 * @param date
	 * @param meal_type
	 * @param drink_type
	 * @param comp_id
	 * @return
	 */
	private List getSpecDrink(Connection conn, String date,
			String meal_type, String drink_type, String comp_id) {
		// TODO Auto-generated method stub
		
		BaseFunction base_tools = new BaseFunction(comp_id);
		List DrinkList = new ArrayList();
		String sql = "";
		
		try{
			sql = " SELECT " +
				  " EHF310100T2_01, EHF310100T2_02, EHF310100T2_03, " +
				  " EHF310100T2_04, EHF310100T2_05, EHF310100T2_06, " +
				  " EHF310100T0_03, EHF310100T0_04, EMS_CategoryT1_05 " +
				  " FROM EHF310100T2 " +
				  " LEFT JOIN EHF310100T0 ON EHF310100T0_01 = EHF310100T2_01 AND EHF310100T0_34 = EHF310100T2_07 " +
				  " LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_04 = EHF310100T2_06 AND EMS_CategoryT1_09 = EHF310100T2_07 AND EMS_CategoryT1_01 = 'Drink' " +
				  " WHERE 1=1 " +
				  " AND ('"+date+"' BETWEEN EHF310100T2_03 AND EHF310100T2_04) ";
			if(!"".equals(meal_type)){
				sql += " AND EHF310100T2_05 = '"+meal_type+"' ";
			}
			if(!"".equals(drink_type)){
				sql += " AND EHF310100T2_06 = '"+drink_type+"' ";
			}
			sql +=
				 " AND EHF310100T2_07 = '"+comp_id+"'" +
				 " ORDER BY EHF310100T2_05, EHF310100T0_03, EHF310100T2_01 ";
			
			DrinkList = base_tools.queryList(sql);
			
			base_tools.close();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return DrinkList;
	}

}
