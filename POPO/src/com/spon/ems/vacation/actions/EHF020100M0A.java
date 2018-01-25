package com.spon.ems.vacation.actions;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import com.spon.ems.NewDispatchAction;
import com.spon.utils.sc.actions.SC005A;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.ems.vacation.forms.EHF020100M0F;
import com.spon.ems.vacation.forms.EX020100R0F;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

import com.p6spy.engine.spy.P6PreparedStatement;
import fr.improve.struts.taglib.layout.util.FormUtils;


/**
 * <Action>公司假別設定
 * @version 1.6
 * @created 29-八月-2013 上午 09:20:12 整理
 */
public class EHF020100M0A extends NewDispatchAction {
	
	private BA_TOOLS ba;
	
	public EHF020100M0A(){
		ba = BA_TOOLS.getInstance();
	}
	
	/**
	 * Action的進入點
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response  
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		//將表單的顯示模式設定為create (新增模式)
		FormUtils.setFormDisplayMode(request, form, "create");
		Connection conn = null;
		try {
			getSelectOption(request);
	        EHF020100M0F Form = new EHF020100M0F();
	        //BA_TOOLS ba = new BA_TOOLS();
	        Collection list =new ArrayList();
	        
	        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
	        Date today = new Date();

			Form.setUSER_UPDATE(getLoginUser(request).getUserName());
			//Form.setDATE_UPDATE("1-"+ba.DateToChineseDate(today).substring(0,8));
			
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
	    } catch (Exception e) {
			// TODO Auto-generated catch block
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
		
		request.setAttribute("State", "");
		request.setAttribute("button", "init");
		request.setAttribute("collection", "show");
		return mapping.findForward("success");
	}
	
	
	public ActionForward init_add(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		//將表單的顯示模式設定為create (新增模式)
		FormUtils.setFormDisplayMode(request, form, "create");
		Connection conn = null;
		try {
			getSelectOption(request);
			EHF020100M0F Form = (EHF020100M0F) form;
	        //BA_TOOLS ba = new BA_TOOLS();
	        Collection list =new ArrayList();
	        
	        SimpleDateFormat sdf =  new SimpleDateFormat("yyyy-MM-dd");
	        Date today = new Date();

			Form.setUSER_UPDATE(getLoginUser(request).getUserName());
			//Form.setDATE_UPDATE("1-"+ba.DateToChineseDate(today).substring(0,8));
			
			
			if("on".equals(Form.getEHF020100T0_06_FLG())){
				request.setAttribute("chk_type","yes");
			}
			
			if("on".equals(Form.getEHF020100T0_05_FLG())){
				request.setAttribute("chk_type","yes");
			}
			
			
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			
			
			
	    } catch (Exception e) {
			// TODO Auto-generated catch block
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
		
		request.setAttribute("State", "");
		request.setAttribute("button", "add");
		//request.setAttribute("collection", "show");
		return mapping.findForward("success");
	}
	
	
	
	public ActionForward queryForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		//inspect
		FormUtils.setFormDisplayMode(request, form, "edit");
		List list = new ArrayList();
		EHF020100M0F Form = (EHF020100M0F) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String sql = "";
		Connection conn = null;
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
	    if (lc_errors.isEmpty()) {
	    	try{
	    		conn = tools.getConnection("SPOS");
	    		AuthorizedBean authbean = getLoginUser(request);
	    		
	    		//取得一天工作時數
	    		float work_hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
	    		 
	    		getSelectOption(request);
	    		sql = " select * from EHF020100T0 " +
	    			  " where 1=1 " +
	    			  " and EHF020100T0_08 = '"+authbean.getCompId()+"' ";  //公司代碼
	    		
	    		if (!"".equals(Form.getEHF020100T0_03())) {//假別代號
	    			sql += " and EHF020100T0_03 = '"+Form.getEHF020100T0_03()+"'";
	    		}
	    		if(!"".equals(Form.getEHF020100T0_03_TYPE())){//假別薪資設定
	    			sql += " and EHF020100T0_03_TYPE = '"+Form.getEHF020100T0_03_TYPE()+"'";
	    		}
	    		if(!"".equals(Form.getEHF020100T0_04())){//假別
	    			sql += " and EHF020100T0_04 = '"+Form.getEHF020100T0_04()+"'";
	    		}
	    	
	    		if(Form.getEHF020100T0_05_hour()==null||"".equals(Form.getEHF020100T0_05_hour())){
	    			Form.setEHF020100T0_05_hour("0");
	    		}
	    		if(Form.getEHF020100T0_06_hour()==null||"".equals(Form.getEHF020100T0_06_hour())){
	    			Form.setEHF020100T0_06_hour("0");
	    		}
	    			
	    		if( Form.getEHF020100T0_05_day()!=null&&!"".equals(Form.getEHF020100T0_05_day())){
	    		
	    			float work_hourss=(Float.parseFloat(Form.getEHF020100T0_05_day()) * work_hours) + Float.parseFloat(Form.getEHF020100T0_05_hour());

	    			sql += " and EHF020100T0_05 = '"+work_hourss+"'";
	    		}
	    		if(Form.getEHF020100T0_06_day()!=null&&!"".equals(Form.getEHF020100T0_06_day())){//是否檢核單次最低請假時數
	    			float work_hourss=(Float.parseFloat(Form.getEHF020100T0_06_day()) * work_hours) + Float.parseFloat(Form.getEHF020100T0_06_hour());
	    			sql += " and EHF020100T0_06 = '"+work_hourss+"'";
	    		}
	    		if(Form.getEHF020100T0_07()!=null&&!"".equals(Form.getEHF020100T0_07())){//備註
	    			sql += " and EHF020100T0_07 = '"+Form.getEHF020100T0_07()+"'";
	    		}
	    		sql += " ORDER BY EHF020100T0_03 ";
	    		
	    		HR_TOOLS hr_tools = new HR_TOOLS();
	    		//取得公司名稱
				Map compMap = hr_tools.getCompNameMap(conn, authbean.getCompId());
				hr_tools.close();
	    		
	    		PreparedStatement pstmt = conn.prepareStatement(sql);
	    		//P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
	    		ResultSet rs = pstmt.executeQuery();
	    		EHF020100M0F Form_List = null;
	    		
	    		while(rs.next()){
	    			Form_List = new EHF020100M0F();
	    			Form_List.setEHF020100T0_01(rs.getInt("EHF020100T0_01"));
	    			Form_List.setEHF020100T0_02((String)compMap.get("COMP_NAME_C"));  //公司名稱
	    			Form_List.setEHF020100T0_03(rs.getString("EHF020100T0_03")+"/"+rs.getString("EHF020100T0_04"));
	    			if("01".equals(rs.getString("EHF020100T0_03_TYPE"))){
	    				Form_List.setEHF020100T0_03_TYPE("全薪");
	    			}else if("02".equals(rs.getString("EHF020100T0_03_TYPE"))){
	    				Form_List.setEHF020100T0_03_TYPE("半薪");
	    			}else if("03".equals(rs.getString("EHF020100T0_03_TYPE"))){
	    				Form_List.setEHF020100T0_03_TYPE("無薪");
	    			}else if("04".equals(rs.getString("EHF020100T0_03_TYPE"))){
	    				Form_List.setEHF020100T0_03_TYPE("依比例設定:"+rs.getString("EHF020100T0_03_VAL")+"倍");
	    			}
	    			Form_List.setEHF020100T0_04(rs.getString("EHF020100T0_04"));
	    			
	    			//處理剩餘時數
	    			if("0".equals(rs.getString("EHF020100T0_05"))||"".equals(rs.getString("EHF020100T0_05"))){
	    				Form_List.setEHF020100T0_05_day("0");
		    			Form_List.setEHF020100T0_05_hour("0");
	    			}
	    			else{
		    			Form_List.setEHF020100T0_05_day((Float.parseFloat(rs.getString("EHF020100T0_05")))/work_hours+"");
		    			Form_List.setEHF020100T0_05_hour((Float.parseFloat(rs.getString("EHF020100T0_05")))%work_hours+"");
	    			}
	    			//Form_List.setEHF020100T0_05(rs.getString("EHF020100T0_05"));
	    			//Form_List.setEHF020100T0_06(rs.getString("EHF020100T0_06")+"小時");
	    			Form_List.setEHF020100T0_07(rs.getString("EHF020100T0_07"));
	    			Form_List.setUSER_UPDATE(rs.getString("USER_UPDATE"));
	    			Form_List.setDATE_UPDATE(rs.getString("VERSION")+"/"+rs.getString("DATE_UPDATE"));
	    			list.add(Form_List);
	    		}
	    		rs.close();
	    		pstmt.close();
	    		//p6stmt.close();
	    		
	    		if(list.size() > 0){
	    			request.setAttribute("MSG","查詢成功!!");
	    		}else{
	    			request.setAttribute("MSG","查無資料!!");
	    		}
	    		Form.setEHF020100T0_03("");
	    		Form.setEHF020100T0_04("");
	    		Form.setEHF020100T0_03_TYPE("");
	    		
	    		request.setAttribute("Form1Datas",Form);
	    		request.setAttribute("Form2Datas",list);
	    		
	    	}catch(Exception e){
	    		e.printStackTrace();
	    		request.setAttribute("MSG","查詢失敗!!");
	    	}finally{
	    		try {
    				if (conn != null && !conn.isClosed()) {
    					conn.close();
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
	    	}
	    }else{
	    	request.setAttribute("MSG","請輸入正確的查詢條件!!");
	    }
	    
	    request.setAttribute("queryCondition", "yes");
		request.setAttribute("button", "query");
		request.setAttribute("collection", "show");
	    request.setAttribute("State", "");
		return mapping.findForward("success");
	}
	
	public ActionForward delForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		BA_TOOLS tools = BA_TOOLS.getInstance();
		String[] id = request.getParameterValues("checkId");
		
		if(id.length>=2){
			request.setAttribute("State", "");
			request.setAttribute("MSG","請勿刪除多筆資料!!");
    		return init(mapping,form,request,response);
		}
		
		if ((id==null?false:!id[0].equals(""))){
			Connection conn = null;
			try{
				conn = tools.getConnection("SPOS");
				String sql = "";
				String compid = getLoginUser(request).getCompId();
				
			int TYPE=this.chkdelId(conn,id,compid);
					
					
					switch(TYPE){
					case 0:
						PreparedStatement pstmt = null;
						conn.setAutoCommit(false);
						for(int i=0;i < id.length;i++){
							sql = "delete from EHF020100T0 where EHF020100T0_01 = ? and EHF020100T0_08 = ?";
							pstmt = conn.prepareStatement(sql);
							pstmt.setInt(1, Integer.valueOf(id[i]));
							pstmt.setString(2, compid);
				    		pstmt.execute();
						}
						conn.commit();
						pstmt.close();
						request.setAttribute("State", "");
						request.setAttribute("MSG","刪除成功");
						break;
					case 1:
						//為預設假別，不可刪除
						request.setAttribute("State", "");
						request.setAttribute("MSG","該假別為預設假別，不可刪除!!");
			    		
			    		break;
					case 2:
						//有員工正在使用，不可刪除
						request.setAttribute("State", "");
						request.setAttribute("MSG","有員工的員工年度休假資料正在使用該假別，不可刪除!!");
			    		
			    		break;
					case 3:
						//有員工正在使用，不可刪除
						request.setAttribute("State", "");
						request.setAttribute("MSG","有員工的請假單正在使用該假別，不可刪除!!");
			    		
			    		break;
					case 4:
						//有員工正在使用，不可刪除
						request.setAttribute("State", "");
						request.setAttribute("MSG","有全勤獎金規則正在使用該假別，不可刪除!!");
			    		
			    		break;
					
					}
					
				
				
				
			}catch(Exception e){
				request.setAttribute("MSG","刪除失敗");
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}finally{
				try {
    				if (conn != null && !conn.isClosed()) {
    					conn.close();
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
			}
			return init(mapping,form,request,response);
		}else{
			request.setAttribute("State", "");
			request.setAttribute("MSG","請選取要刪除的資料!!");
    		return init(mapping,form,request,response);
		}
	}
	/**
	 * 檢核刪除的資料是否有員工在使用，或是是否為預設假別
	 * @param conn
	 * @param id
	 * @param compid
	 * @return
	 */
	private int chkdelId(Connection conn, String[] id, String compid) {
		// TODO Auto-generated method stub
		int i=0;
		try{
		for(int number=0;number<id.length;number++){
			if(Integer.valueOf(id[number])<=9){
				//預設假別，不可刪除。
				return 1;
			}
			
			
		}

		//檢核員工年度休假  是否有使用
		for(int number=0;number<id.length;number++){
		String 	sql = " select    EHF010300T0.* " +
				" FROM  EHF010300T0" +
				" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF010300T0_06" +
				" AND EHF020100T0_08 = EHF010300T0_12" +
				" WHERE 1=1" +
				" AND EHF020100T0_01 = '"+id[number]+"'"+
				" AND EHF010300T0_12 = '"+compid+"' ";  //公司代碼
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);

			if(rs.next()){
				i= 2;
			}
			
			if(i!=0){
			rs.close();
			stmt.close();
			return i;
			
			}
		}
		
		//檢核請假單是否有人使用
		for(int number=0;number<id.length;number++){
			String 	sql = " select  * " +
				" FROM  EHF020200T0" +
				" LEFT JOIN EHF020100T0 ON EHF020100T0_03=EHF020200T0_14" +
				" AND EHF020100T0_08 = EHF020200T0_18" +
				" WHERE 1=1" +
				" AND EHF020100T0_01 = '"+id[number]+"'"+
				" AND EHF020200T0_18 = '"+compid+"' ";  //公司代碼
			Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_01 = stmt_01.executeQuery(sql);

			if(rs_01.next()){
				i= 3;
			}
			if(i!=0){
				rs_01.close();
				stmt_01.close();
				return i;
				
			}
		}

		//檢核全勤獎金規則是否正在使用
		for(int number=0;number<id.length;number++){
			String 	sql = " select  * " +
				" FROM  EHF030108T1" +
				" LEFT JOIN EHF020100T0 ON EHF020100T0_03 = EHF030108T1_02" +
				" AND EHF020100T0_08 = EHF030108T1_07" +
				" WHERE 1=1" +
				" AND EHF020100T0_01 = '"+id[number]+"'"+
				" AND EHF030108T1_07 = '"+compid+"' ";  //公司代碼
			Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_01 = stmt_01.executeQuery(sql);

			if(rs_01.next()){
				i= 4;
			}
			if(i!=0){
				stmt_01.close();
				rs_01.close();
				return i;
				
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
		
		return i;
	}

	public ActionForward InsertForm(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) {
		String sql = "";
		Connection conn = null;
		EHF020100M0F Form = (EHF020100M0F) form;
		//BA_TOOLS ba = new BA_TOOLS();
		ActionErrors l_actionErrors = new ActionErrors();
		BA_TOOLS tools = BA_TOOLS.getInstance();
		List list = new ArrayList();
		getSelectOption(request);
		if (Form.getEHF020100T0_03() == null || "".equals(Form.getEHF020100T0_03())) {
			l_actionErrors.add("EHF020100T0_03", new ActionMessage("不可空白"));
		}
		if (Form.getEHF020100T0_04() == null || "".equals(Form.getEHF020100T0_04())) {
			l_actionErrors.add("EHF020100T0_04", new ActionMessage("不可空白"));
		}
		if (Form.getEHF020100T0_03_TYPE() == null || "".equals(Form.getEHF020100T0_03_TYPE())) {
			l_actionErrors.add("EHF020100T0_03_TYPE", new ActionMessage("不可空白"));
		}
		
		if (Form.getEHF020100T0_03().toString().length()>=5) {
			l_actionErrors.add("EHF020100T0_03", new ActionMessage("不可超過4個字"));
		}
		
		
		try {
			
			conn = ba.getConnection("SPOS");
		} catch (Exception e) {
			request.setAttribute("MSG", "取得CONN失敗!!");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try{
			String 	chkdel_sql = " select  * " +
			" FROM  EHF020100T0" +
		
			" WHERE 1=1" +
			" AND EHF020100T0_03 = '"+Form.getEHF020100T0_03()+"'"+
			" AND EHF020100T0_08 = '"+getLoginUser(request).getCompId()+"' ";  //公司代碼
			Statement stmt_01 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_01 = stmt_01.executeQuery(chkdel_sql);

			if(rs_01.next()){
				l_actionErrors.add("EHF020100T0_03", new ActionMessage("已重複"));
			}
		}catch(Exception e){
				e.printStackTrace();
		}
		
		
		float work_hourss_05 = 0;
		float work_hourss_06 = 0;
		if (l_actionErrors.isEmpty()) {
			try {
				
				// 取得一天工作時數
				float work_hours = Float.parseFloat(tools.getSysParam(conn,	getLoginUser(request).getCompId(), "WORK_HOURS"));
				
				String compid = getLoginUser(request).getCompId();
				
				String name = getLoginUser(request).getUserName();
				
				conn.setAutoCommit(false);
				//EMS1.1新增三個參數，因此改寫此SQL。 20140530 by Hedwig
//				sql = " insert into EHF020100T0 ("
//						+ " EHF020100T0_02,EHF020100T0_03, "
//						+ " EHF020100T0_03_TYPE,EHF020100T0_03_VAL, "
//						+ " EHF020100T0_04, "
//						+ " EHF020100T0_05_FLG, EHF020100T0_05, "
//						+ " EHF020100T0_06_FLG, EHF020100T0_06, "
//						+ " EHF020100T0_07,EHF020100T0_08, "
//						+ " EHF020100T0_09, "
//						+ " USER_CREATE,USER_UPDATE,VERSION,DATE_UPDATE) "
//						+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,1,NOW())";
				sql = " insert into EHF020100T0 ("+
					 " EHF020100T0_02,EHF020100T0_03, EHF020100T0_03_TYPE,EHF020100T0_03_VAL, EHF020100T0_04, "+
					 " EHF020100T0_05_FLG, EHF020100T0_05, EHF020100T0_06_FLG, EHF020100T0_06, EHF020100T0_07, " +
					 " EHF020100T0_08, EHF020100T0_09, EHF020100T0_10, EHF020100T0_11, EHF020100T0_12,"+
					 " USER_CREATE,USER_UPDATE,VERSION,DATE_UPDATE) "+
					 " values(?,?,?,?,?," +
					 " ?,?,?,?,?," +
					 " ?,?,?,?,?," +
					 " ?,?,1,NOW())";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null,	pstmt, null, sql);
				int indexid = 1;
				p6stmt.setString(indexid, compid);
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020100T0_03());
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020100T0_03_TYPE()); // 假別薪資設定
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020100T0_03_VAL() == null ? "0" : Form.getEHF020100T0_03_VAL() == "" ? "0" : Form.getEHF020100T0_03_VAL()); // 假別薪資設定比例
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020100T0_04());
				indexid++;
				p6stmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_05_FLG())); // 設定是否檢核年度請假總時數
				indexid++;
				
				if ("".equals(Form.getEHF020100T0_05_day())) {
					Form.setEHF020100T0_05_day("0");
				}
				
				if ("".equals(Form.getEHF020100T0_05_hour())) {
					Form.setEHF020100T0_05_hour("0");
				}

				if (!"".equals(Form.getEHF020100T0_05_day())&& Form.getEHF020100T0_05_day() != null)
					work_hourss_05 = (Float.parseFloat(Form.getEHF020100T0_05_day()) * work_hours)+ Float.parseFloat(Form.getEHF020100T0_05_hour());
				
				p6stmt.setString(indexid, work_hourss_05 + ""); // 年度請假總時數
				indexid++;
				p6stmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_06_FLG())); // 設定是否檢核單次最低請假時數
				indexid++;
				
				
				if ("".equals(Form.getEHF020100T0_06_day())) {
					Form.setEHF020100T0_06_day("0");
				}
				
				
				if ("".equals(Form.getEHF020100T0_06_hour())) {
					Form.setEHF020100T0_06_hour("0");
				}

				if (!"".equals(Form.getEHF020100T0_06_day())&& Form.getEHF020100T0_06_day() != null)
					work_hourss_06 = (Float.parseFloat(Form.getEHF020100T0_06_day()) * work_hours)+ Float.parseFloat(Form.getEHF020100T0_06_hour());
				
				p6stmt.setString(indexid, work_hourss_06 + ""); // 年度請假總時數
				indexid++;
				p6stmt.setString(indexid, Form.getEHF020100T0_07());
				indexid++;
				p6stmt.setString(indexid, compid);
				indexid++;
				//不管哪一種假別都應該要檢核剩餘時數，因此預設就為TRUE，並且不讓使用者修改。 20140530 by Hedwig
//				p6stmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_09())); // 設定是否檢核年度假別剩餘時數
				p6stmt.setBoolean(indexid, true); // 設定是否檢核年度假別剩餘時數
				indexid++;
				p6stmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_10())); // 是否為預設假別
				indexid++;
				p6stmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_11())); // 請假時數是否包含六日
				indexid++;
				p6stmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_12())); // 限定連續休假
				indexid++;
				p6stmt.setString(indexid, name);
				indexid++;
				p6stmt.setString(indexid, name);
				indexid++;
				//System.out.println(p6stmt.getQueryFromPreparedStatement());
				pstmt.execute();
				p6stmt.close();
				pstmt.close();
				
				conn.commit();
				
				request.setAttribute("Form1Datas", form);
				request.setAttribute("MSG", "新增成功!!");
				// request.setAttribute("button", "edit");
				request.setAttribute("State", "");
				
				return selectForm(mapping, form, request, response);
			} catch (Exception e) {
				request.setAttribute("MSG", "新增失敗!!");
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} finally {
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else {
			saveErrors(request, l_actionErrors);
			
			
			
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", list);
			request.setAttribute("button", "add");
			request.setAttribute("MSG", "請重新選擇條件!!");
			return init_add(mapping, form, request, response); 
		}
		return selectForm(mapping, form, request, response);
	}
	
	public ActionForward UpdateForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String[] id = request.getParameterValues("checkId");
		String sql = "";
		Connection conn = null;
		EHF020100M0F Form = (EHF020100M0F)form;
		//BA_TOOLS ba = new BA_TOOLS();
		List list = new ArrayList();
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		BA_TOOLS tools = BA_TOOLS.getInstance();
		if ((id==null?false:!id[0].equals(""))){
			Form.setEHF020100T0_01(Integer.parseInt(id[0]));
		}
		
	    if((id==null?false:!id[0].equals("")) || (!"".equals(Form.getEHF020100T0_01()) && (Form.getEHF020100T0_01()+"") != null )){
	    	try{
	    		
	    		conn = ba.getConnection("SPOS");
	    		//取得一天工作時數
				float work_hours = Float.parseFloat(tools.getSysParam(conn, getLoginUser(request).getCompId(), "WORK_HOURS"));

				getSelectOption(request);
				sql = " select * from EHF020100T0 " +
					  " where 1=1 " +
					  " and EHF020100T0_01 = ? " +
					  " and EHF020100T0_08 = ? ";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
				p6stmt.setString(1, Form.getEHF020100T0_01()+"");
				p6stmt.setString(2, getLoginUser(request).getCompId());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					Form.setEHF020100T0_01(rs.getInt("EHF020100T0_01"));
					//Form.setEHF020100T0_02(rs.getString("EHF020100T0_02"));
					Form.setEHF020100T0_03(rs.getString("EHF020100T0_03"));
					Form.setEHF020100T0_03_TYPE(rs.getString("EHF020100T0_03_TYPE"));
					Form.setEHF020100T0_03_VAL(rs.getString("EHF020100T0_03_VAL"));
					Form.setEHF020100T0_04(rs.getString("EHF020100T0_04"));
					Form.setEHF020100T0_05_FLG(ba.BooleanToString(rs.getBoolean("EHF020100T0_05_FLG"), "2"));
					
					if(rs.getBoolean("EHF020100T0_05_FLG"))
					{
						//處理剩餘時數
						Form.setEHF020100T0_05_day(((int)(rs.getFloat("EHF020100T0_05")/work_hours))+"");
						Form.setEHF020100T0_05_hour((rs.getFloat("EHF020100T0_05")%work_hours)+"");
					}else
					{
						//處理剩餘時數
						Form.setEHF020100T0_05_day(0+"");
						Form.setEHF020100T0_05_hour(0+"");
					}

					//Form.setEHF020100T0_05(rs.getString("EHF020100T0_05"));
					Form.setEHF020100T0_06_FLG(ba.BooleanToString(rs.getBoolean("EHF020100T0_06_FLG"), "2"));
					if(rs.getBoolean("EHF020100T0_06_FLG"))
					{
						//處理剩餘時數
						Form.setEHF020100T0_06_day(((int)(rs.getFloat("EHF020100T0_06")/work_hours))+"");
						Form.setEHF020100T0_06_hour((rs.getFloat("EHF020100T0_06")%work_hours)+"");
					}else
					{
						//處理剩餘時數
						Form.setEHF020100T0_06_day(0+"");
						Form.setEHF020100T0_06_hour(0+"");
					}
					//Form.setEHF020100T0_06(rs.getString("EHF020100T0_06"));
					Form.setEHF020100T0_07(rs.getString("EHF020100T0_07"));
					Form.setEHF020100T0_09(ba.BooleanToString(rs.getBoolean("EHF020100T0_09"), "2"));
					//EMS1.1新增三個參數。 20140530 by Hedwig
					Form.setEHF020100T0_10(ba.BooleanToString(rs.getBoolean("EHF020100T0_10"), "2"));
					Form.setEHF020100T0_11(ba.BooleanToString(rs.getBoolean("EHF020100T0_11"), "2"));
					Form.setEHF020100T0_12(ba.BooleanToString(rs.getBoolean("EHF020100T0_12"), "2"));
					Form.setUSER_UPDATE(getLoginUser(request).getUserName());
					Form.setDATE_UPDATE(rs.getString("VERSION")+"/"+rs.getString("DATE_UPDATE").substring(0,10));
				}
				rs.close();
				pstmt.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			request.setAttribute("button", "edit");
			request.setAttribute("collection", "");
			request.setAttribute("State", "1");
			request.setAttribute("chk_type", "yes");
			return mapping.findForward("success");
	    }else{
	    	request.setAttribute("MSG","請選取要修改的資料!!");
	    	return init(mapping,form,request,response);
	    }
	}
	
	public ActionForward saveForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String sql = "";
		Connection conn = null;
		EHF020100M0F Form = (EHF020100M0F) form;
		FormUtils.setFormDisplayMode(request, form, "edit");
		//BA_TOOLS ba = new BA_TOOLS();
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		if (Form.getEHF020100T0_03() == null || "".equals(Form.getEHF020100T0_03())) {
			lc_errors.add("EHF020100T0_03", new ActionMessage("不可空白"));
		}
		if (Form.getEHF020100T0_04() == null || "".equals(Form.getEHF020100T0_04())) {
			lc_errors.add("EHF020100T0_04", new ActionMessage("不可空白"));
		}
		if (Form.getEHF020100T0_03_TYPE() == null || "".equals(Form.getEHF020100T0_03_TYPE())) {
			lc_errors.add("EHF020100T0_03_TYPE", new ActionMessage("不可空白"));
		}
		
		if (Form.getEHF020100T0_03().toString().length()>=5) {
			lc_errors.add("EHF020100T0_03", new ActionMessage("不可超過4個字"));
		}
		
		if (ba.StringToBoolean(Form.getEHF020100T0_06_FLG())) {
			
			
			if (Form.getEHF020100T0_06_day() == null || "".equals(Form.getEHF020100T0_06_day())){
				
			}
			
			
			
		}
		
		
		
		float work_hourss_05=0;
		float work_hourss_06=0;
	    if(lc_errors.isEmpty()){
			try{
				String name = getLoginUser(request).getUserName();
				String compId = getLoginUser(request).getCompId();
				conn = ba.getConnection("SPOS");
				conn.setAutoCommit(false);
				
				//取得一天工作時數
				float work_hours = Float.parseFloat(tools.getSysParam(conn, getLoginUser(request).getCompId(), "WORK_HOURS"));
				
				
				sql = " update EHF020100T0 set " +
				  " EHF020100T0_02=?,EHF020100T0_03=?, " +
				  " EHF020100T0_03_TYPE=?, EHF020100T0_03_VAL=?, " +
				  " EHF020100T0_04=?, " +
				  " EHF020100T0_05_FLG=?, EHF020100T0_05=?, " +
				  " EHF020100T0_06_FLG=?, EHF020100T0_06=?, " +
				  " EHF020100T0_07=?, EHF020100T0_09=?, " +
				  //EMS1.1改版多加三個參數。 20140530 by Hedwig
				  " EHF020100T0_10=?,EHF020100T0_11=?,EHF020100T0_12=?,"+
				  " USER_CREATE=?,USER_UPDATE=?,VERSION=VERSION+1, "+
				  " DATE_UPDATE=NOW() " +
				  " where 1=1 " +
				  " and EHF020100T0_01 = ? " +
				  " and EHF020100T0_08 = ? ";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
			int indexid = 1;
			pstmt.setString(indexid,compId);
			indexid++;
			pstmt.setString(indexid,Form.getEHF020100T0_03());
			indexid++;
			pstmt.setString(indexid, Form.getEHF020100T0_03_TYPE());
			indexid++;
			pstmt.setString(indexid, Form.getEHF020100T0_03_VAL()==null?"0":Form.getEHF020100T0_03_VAL()==""?"0":Form.getEHF020100T0_03_VAL());  //假別薪資設定比例
			indexid++;
			pstmt.setString(indexid,Form.getEHF020100T0_04());
			indexid++;
			pstmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_05_FLG())); //設定是否檢核年度請假總時數
			indexid++;
			
			if (ba.StringToBoolean(Form.getEHF020100T0_05_FLG())) {
				if (Form.getEHF020100T0_05_hour() == null) {
					Form.setEHF020100T0_05_hour("0");
				}
				if (!"".equals(Form.getEHF020100T0_05_day())&& Form.getEHF020100T0_05_day() != null)
					work_hourss_05 = (Float.parseFloat(Form.getEHF020100T0_05_day()) * work_hours)	+ Float.parseFloat(Form.getEHF020100T0_05_hour());
			}
			else{
				work_hourss_05=0;
			}
  		
			pstmt.setString(indexid,work_hourss_05+"");  //年度請假總時數
			indexid++;
			pstmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_06_FLG())); //設定是否檢核單次最低請假時數
			indexid++;
			
			if (ba.StringToBoolean(Form.getEHF020100T0_06_FLG())) {
				if (Form.getEHF020100T0_06_hour() == null) {
					Form.setEHF020100T0_06_hour("0");
				}
				if (!"".equals(Form.getEHF020100T0_06_day())&& Form.getEHF020100T0_06_day() != null)
					work_hourss_06 = (Float.parseFloat(Form.getEHF020100T0_06_day()) * work_hours)	+ Float.parseFloat(Form.getEHF020100T0_06_hour());
			}
			else{
				work_hourss_06=0;
			}
  		
			pstmt.setString(indexid,work_hourss_06+"");  //年度請假總時數
			indexid++;
			
			//pstmt.setString(indexid,Form.getEHF020100T0_06()==null?"0":Form.getEHF020100T0_06()==""?"0":Form.getEHF020100T0_06());  //單次最低請假時數
			//indexid++;
			
			pstmt.setString(indexid,Form.getEHF020100T0_07());
			indexid++;
			pstmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_09()==null?"1":Form.getEHF020100T0_09())); //設定是否檢核年度假別剩餘時數
			indexid++;
			//於EMS1.1改版時新增三個參數。 20140603 by Hedwig
			pstmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_10())); //是否為預設假別
			indexid++;
			pstmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_11())); //請假時數是否包含六日
			indexid++;
			pstmt.setBoolean(indexid, ba.StringToBoolean(Form.getEHF020100T0_12())); //限定連續休假
			indexid++;
			pstmt.setString(indexid,name);
			indexid++;
			pstmt.setString(indexid,name);
			indexid++;
			String[] version = Form.getDATE_UPDATE().split("/");
			//pstmt.setInt(indexid,Integer.parseInt(version[0])+1);
			//indexid++;
			pstmt.setInt(indexid,Form.getEHF020100T0_01());
			indexid++;
			pstmt.setString(indexid, compId);
			indexid++;
			
			pstmt.executeUpdate();
			pstmt.close();
			//p6stmt.close();
			conn.commit();
			request.setAttribute("MSG", "修改成功!");
				
			}catch(Exception e){
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				request.setAttribute("MSG", "修改失敗!");
				return UpdateForm(mapping,form,request,response);
			}finally{
				try {
    				if (conn != null && !conn.isClosed()) {
    					conn.close();
    				}
    			} catch (Exception e) {
    				e.printStackTrace();
    			}
			}
			
		}
	    else{
	    	
	    	saveErrors(request, lc_errors);

		
	    	request.setAttribute("save_status", "error");
			//request.setAttribute("Form1Datas", Form);
			//request.setAttribute("Form2Datas", list);
			request.setAttribute("button", "add");
			
			 return UpdateForm(mapping,form,request,response);
	    }
	    
	    request.setAttribute("chk_type", "yes");
	    request.setAttribute("State", "1");
	    return UpdateForm(mapping,form,request,response);
	}
	
	public ActionForward goback(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		
		return init(mapping,form,request,response);
	}
	
	private void getSelectOption(HttpServletRequest request)
	{	
		try{
			List list = new ArrayList();
			BA_ALLKINDForm bform1 = new BA_ALLKINDForm();
			bform1.setItem_id("");
			bform1.setItem_value("=請選擇=");
			list.add(bform1);
			String[] id = {"01","02","03","04"};
			String[] type = {"全薪","半薪","無薪","依比例設定"};
			
			for(int j=0;j<id.length;j++){
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(id[j]);
				bform.setItem_value(type[j]);
				list.add(bform);
			}
			
			request.setAttribute("listEHF020100T0_03_TYPE", list);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * 報表匯出
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		BA_TOOLS tools = BA_TOOLS.getInstance();
		EHF020100M0F Form = (EHF020100M0F) form;
		Connection conn = null;
		ActionErrors lc_errors = new ActionErrors();

		// 建立資料庫連線
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
			AuthorizedBean authbean = getLoginUser(request);

			// 產生Excel元件
			EX020100R0F ef = new EX020100R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getUserId(), request);

			try {
				Map return_map = new HashMap();
				int DataCount = 0;
				// 取得薪資計算明細資料
				//List printlist = this.queryListDatas(conn, authbean, Form.getEHF030600T0_01());
				HR_TOOLS hr_tools = new HR_TOOLS();
				// 取得公司名稱
				Map compMap = hr_tools.getCompNameMap(conn, getLoginUser(request).getCompId());
				
				Map empMap = hr_tools.getEmpNameMap(getLoginUser(request).getCompId());
				Map depMap = hr_tools.getDepNameMap(getLoginUser(request).getCompId());
				hr_tools.close();

				//取得一天工作時數
	    		float work_hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
				
	    		
	    		// 列印報表表頭
				ef.printHeadValue((String)(compMap.get("COMP_NAME_C")),tools.ymdTostring(tools.getSysDate()));
				// 列印報表
				DataCount = ef.print(conn,  Form,authbean.getCompId(),return_map,empMap,depMap,work_hours);

				if (DataCount > 0) {// 表示有資料
					
					String cur_date = tools.ymdTostring(tools.getSysDate());
					String name ="公司假別"+cur_date+".xls";
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

				} else {
					saveErrors(request, lc_errors);
					request.setAttribute("MSG", "沒有資料可列印!!");
				}
			} catch (Exception E) {
				E.printStackTrace();
				request.setAttribute("MSG", "列印失敗!!");
			} finally {
				// ef.write();
			}

		} catch (Exception E) {
			E.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}

		return queryForm(mapping, Form, request, response);
	}
	
	
	
	/**
	 * 新增後的查詢畫面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward selectForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		String[] id = request.getParameterValues("checkId");
		String sql = "";
		Connection conn = null;
		EHF020100M0F Form = (EHF020100M0F)form;
		List list = new ArrayList();
		ActionErrors lc_errors = Form.validate(mapping, request, conn);
		BA_TOOLS tools = BA_TOOLS.getInstance();
		if ((id==null?false:!id[0].equals(""))){
			Form.setEHF020100T0_01(Integer.parseInt(id[0]));
		}
		
	    if((id==null?false:!id[0].equals("")) || (!"".equals(Form.getEHF020100T0_01()) && (Form.getEHF020100T0_01()+"") != null )){
	    	try{
	    		
	    		conn = tools.getConnection("SPOS");
	    		//取得一天工作時數
				float work_hours = Float.parseFloat(tools.getSysParam(conn, getLoginUser(request).getCompId(), "WORK_HOURS"));

				getSelectOption(request);
				sql = " select * from EHF020100T0 " +
					  " where 1=1 " +
					  " and EHF020100T0_03 = ? " +
					  " and EHF020100T0_08 = ? ";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				P6PreparedStatement p6stmt = new P6PreparedStatement(null, pstmt,null, sql);
				p6stmt.setString(1, Form.getEHF020100T0_03()+"");
				p6stmt.setString(2, getLoginUser(request).getCompId());
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()){
					Form.setEHF020100T0_01(rs.getInt("EHF020100T0_01"));
					//Form.setEHF020100T0_02(rs.getString("EHF020100T0_02"));
					Form.setEHF020100T0_03(rs.getString("EHF020100T0_03"));
					Form.setEHF020100T0_03_TYPE(rs.getString("EHF020100T0_03_TYPE"));
					Form.setEHF020100T0_03_VAL(rs.getString("EHF020100T0_03_VAL"));
					Form.setEHF020100T0_04(rs.getString("EHF020100T0_04"));
					Form.setEHF020100T0_05_FLG(tools.BooleanToString(rs.getBoolean("EHF020100T0_05_FLG"), "2"));
					
					if(rs.getBoolean("EHF020100T0_05_FLG"))
					{
						//處理剩餘時數
						Form.setEHF020100T0_05_day(((int)(rs.getFloat("EHF020100T0_05")/work_hours))+"");
						Form.setEHF020100T0_05_hour((rs.getFloat("EHF020100T0_05")%work_hours)+"");
					}else
					{
						//處理剩餘時數
						Form.setEHF020100T0_05_day(0+"");
						Form.setEHF020100T0_05_hour(0+"");
					}

					//Form.setEHF020100T0_05(rs.getString("EHF020100T0_05"));
					Form.setEHF020100T0_06_FLG(tools.BooleanToString(rs.getBoolean("EHF020100T0_06_FLG"), "2"));
					if(rs.getBoolean("EHF020100T0_06_FLG"))
					{
						//處理剩餘時數
						Form.setEHF020100T0_06_day(((int)(rs.getFloat("EHF020100T0_06")/work_hours))+"");
						Form.setEHF020100T0_06_hour((rs.getFloat("EHF020100T0_06")%work_hours)+"");
					}else
					{
						//處理剩餘時數
						Form.setEHF020100T0_06_day(0+"");
						Form.setEHF020100T0_06_hour(0+"");
					}
					//Form.setEHF020100T0_06(rs.getString("EHF020100T0_06"));
					Form.setEHF020100T0_07(rs.getString("EHF020100T0_07"));
					Form.setEHF020100T0_09(tools.BooleanToString(rs.getBoolean("EHF020100T0_09"), "2"));
					//EMS1.1新增三個參數。 20140530 by Hedwig
					Form.setEHF020100T0_10(tools.BooleanToString(rs.getBoolean("EHF020100T0_10"), "2"));
					Form.setEHF020100T0_11(tools.BooleanToString(rs.getBoolean("EHF020100T0_11"), "2"));
					Form.setEHF020100T0_12(tools.BooleanToString(rs.getBoolean("EHF020100T0_12"), "2"));
					Form.setUSER_UPDATE(getLoginUser(request).getUserName());
					Form.setDATE_UPDATE(rs.getString("VERSION")+"/"+rs.getString("DATE_UPDATE").substring(0,10));
				}
				rs.close();
				pstmt.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				try {
					if (conn != null && !conn.isClosed()) {
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}	
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			request.setAttribute("button", "edit");
			request.setAttribute("collection", "");
			request.setAttribute("State", "1");
			request.setAttribute("chk_type", "yes");
			return mapping.findForward("success");
	    }else{
	    	request.setAttribute("MSG","請選取要修改的資料!!");
	    	return init(mapping,form,request,response);
	    }
	}
	
	
}
