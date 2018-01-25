package com.spon.utils.category;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.spon.ems.NewDispatchAction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;

import fr.improve.struts.taglib.layout.util.FormUtils;

public class EMS_CategoryM1A extends NewDispatchAction
{
	private DataSourceTransactionManager transactionManager;    
	private DefaultTransactionDefinition def;
	private JdbcTemplate jdbcTemplate;
	private BA_TOOLS tool;
	private EMS_CategoryM0A m0a;
	public EMS_CategoryM1A()
	{
		tool = BA_TOOLS.getInstance();
		m0a = new EMS_CategoryM0A();
		try {
			jdbcTemplate = new JdbcTemplate(tool.getDataSource("SPOS"));
			transactionManager = new DataSourceTransactionManager(tool.getDataSource("SPOS"));
			def = new DefaultTransactionDefinition();        
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public ActionForward init(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		List list = new ArrayList();
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
			m0a.getSelectOption(request);
//			getSelectOption(request);
//			getSubSelectOption(request,"");
			this.generateSelectBox(conn, Form, request);
			
			//設定Struts-Layout顯示模式
			FormUtils.setFormDisplayMode(request, form, "create");
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", list);
		
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return mapping.findForward("success");
	}
	
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		String sql = null;
		String sql2 = null;
		List list = new ArrayList();
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		String name = getLoginUser(request).getUserName();
		TransactionStatus status = transactionManager.getTransaction(def);
		
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
			m0a.getSelectOption(request);
//			getSelectOption(request);
//			getSubSelectOption(request,"");
			this.generateSelectBox(conn, Form, request);
			String compId = getLoginUser(request).getCompId();
			
			ActionErrors lc_errors = new ActionErrors();
			//檢核欄位
			try{
				BA_Vaildate ve = BA_Vaildate.getInstance();
				ve.isEmpty(lc_errors, Form.getDATA01(), "DATA01", "不可空白");
				ve.isEmpty(lc_errors, Form.getDATA02(), "DATA02", "不可空白");
				ve.isEmpty(lc_errors, Form.getDATA04(), "DATA04", "不可空白");
				ve.isEmpty(lc_errors, Form.getDATA05(), "DATA05", "不可空白");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(lc_errors.isEmpty()){
			
				sql2 = "select EMS_CategoryT0_01 from EMS_CategoryT0 where EMS_CategoryT0_01 = ? and EMS_CategoryT0_06 = ?";
				List data = jdbcTemplate.queryForList(sql2,new Object[]{Form.getDATA01().trim(),compId});
				int insert = 0;
				if(data.size() > 0){
					request.setAttribute("MSG", "類別代碼資料不可重複，請重填!!");
				}else{
					sql = " insert into EMS_CategoryT0 (" +
	  	  	      	  	  " EMS_CategoryT0_01,EMS_CategoryT0_SuperClass,EMS_CategoryT0_SuperCode," +
	  	  	      	  	  " EMS_CategoryT0_02,EMS_CategoryT0_03,EMS_CategoryT0_04," +
	  	  	      	  	  " EMS_CategoryT0_05,EMS_CategoryT0_06," +
	  	  	      	  	  " USER_CREATE,USER_UPDATE,VERSION,DATE_UPDATE) " +
	  	  	      	  	  " values(?,?,?,?,?,?,?,?,?,?,1,NOW())";
		
					insert = jdbcTemplate.update(sql, new Object[]{
							 		 Form.getDATA01().trim(),Form.getDATA06()==null?"":Form.getDATA06().trim(),Form.getDATA07()==null?"":Form.getDATA07().trim(),
									 Form.getDATA02().trim(),Form.getDATA03().trim(),
									 Form.getDATA04().trim(),Boolean.parseBoolean(Form.getDATA05().trim()),compId,name,name
							 });
				
					if(insert > 0){
						request.setAttribute("MSG", "新增成功!!");
						transactionManager.commit(status);
						//設定Struts-Layout顯示模式
						FormUtils.setFormDisplayMode(request, form, "edit");
					}else{
						request.setAttribute("MSG", "新增失敗!!");
						//設定Struts-Layout顯示模式
						FormUtils.setFormDisplayMode(request, form, "create");
					}
				}
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
			}
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", list);
			
		}catch(Exception e){
			transactionManager.rollback(status);
			request.setAttribute("MSG", "新增失敗!!");
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return mapping.findForward("success");
	}
	
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		List list = new ArrayList();
		//BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
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
			String superClass = "";
			if(!"".equals(Form.getDATA40())){
				superClass = Form.getDATA40();
			}
			
			m0a.getSelectOption(request);
//			getSelectOption(request);
//			getSubSelectOption(request,superClass);
			this.generateSelectBox(conn, Form, request);
			
			String compId = getLoginUser(request).getCompId();
			String sql = null;
			
			sql = " select *, DATE_FORMAT(DATE_UPDATE,'%Y/%m/%d') AS DATE_UPDATE from EMS_CategoryT0 " +
				  " where 1=1 " +
				  " and EMS_CategoryT0_01 = ? " +
				  " and EMS_CategoryT0_06 = ?";
			List data = jdbcTemplate.queryForList(sql,new Object[]{Form.getDATA01(),compId});
			Map map = null;
	
			if(data.size()>0){
				map = (Map)data.get(0);
				Form.setDATA01(map.get("EMS_CategoryT0_01").toString());
				Form.setDATA02(map.get("EMS_CategoryT0_02").toString());
				Form.setDATA03(map.get("EMS_CategoryT0_03").toString());
				Form.setDATA04(map.get("EMS_CategoryT0_04").toString());
				Form.setDATA05(map.get("EMS_CategoryT0_05").toString());
				Form.setDATA06(map.get("EMS_CategoryT0_SuperClass").toString());
				Form.setDATA07(map.get("EMS_CategoryT0_SuperCode").toString());
				Form.setUSER_CREATE(map.get("USER_CREATE").toString());
				Form.setUSER_UPDATE(map.get("USER_UPDATE").toString());
				Form.setVERSION(Integer.parseInt(map.get("VERSION").toString()));
				//Form.setDATE_UPDATE(ems_tools.convertADDatetimeToStrng((Date)map.get("DATE_UPDATE")));
				Form.setDATE_UPDATE(map.get("DATE_UPDATE").toString());
			}

			sql = " select * from EMS_CategoryT1 " +
			  " where 1=1 " +
			  " and EMS_CategoryT1_01 = ? " +
			  " and EMS_CategoryT1_09 = ? " +
			  " order by EMS_CategoryT1_07 ";
			data = jdbcTemplate.queryForList(sql,new Object[]{Form.getDATA01(),compId});
			
			for(int i=0;i<data.size();i++){
				map = (Map)data.get(i);
				EMS_VIEWDATAF bForm = new EMS_VIEWDATAF();
				bForm.setDATA01(map.get("EMS_CategoryT1_01").toString());
				bForm.setDATA02(map.get("EMS_CategoryT1_04").toString());
				bForm.setDATA03(map.get("EMS_CategoryT1_05").toString());
				bForm.setDATA04(map.get("EMS_CategoryT1_07").toString());
				bForm.setDATA05(map.get("EMS_CategoryT1_06").toString());
				list.add(bForm);
				
			}
			
			//設定Struts-Layout顯示模式
			FormUtils.setFormDisplayMode(request, form, "edit");
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", list);
			
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}

		return mapping.findForward("success");
	}
	
	public ActionForward addDetailDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		List list = new ArrayList();
		TransactionStatus status = transactionManager.getTransaction(def);
		
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
			m0a.getSelectOption(request);
//			getSelectOption(request);
//			getSubSelectOption(request,"");
			this.generateSelectBox(conn, Form, request);
			
			String sql = null;
			String sql2 = null;
			String compId = getLoginUser(request).getCompId();
			
			ActionErrors lc_errors = new ActionErrors();
			
			//檢核欄位
			try{
				BA_Vaildate ve = BA_Vaildate.getInstance();
				ve.isEmpty(lc_errors, Form.getDATA10(), "DATA10", "不可空白");
				ve.isEmpty(lc_errors, Form.getDATA11(), "DATA11", "不可空白");
				ve.isEmpty(lc_errors, Form.getDATA13(), "DATA13", "不可空白");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if (lc_errors.isEmpty()) {
				
				sql2 = " select EMS_CategoryT1_04 from EMS_CategoryT1 where EMS_CategoryT1_01 = ? and EMS_CategoryT1_04 = ? and EMS_CategoryT1_09 = ?";
				List t1 = jdbcTemplate.queryForList(sql2,new Object[]{Form.getDATA01(),Form.getDATA10(),compId});
				if(t1.size() > 0){
					request.setAttribute("MSG", "代碼資料不可重複，請重填!!");
					lc_errors.add("DATA10",new ActionMessage("代碼重複"));
					saveErrors(request, lc_errors);
				}else{
					int detail = 0;
					sql = " insert into EMS_CategoryT1 (" +
	  	  	      	  	  " EMS_CategoryT1_01,EMS_CategoryT1_02,EMS_CategoryT1_03,EMS_CategoryT1_04," +
	  	  	      	  	  " EMS_CategoryT1_05,EMS_CategoryT1_06,EMS_CategoryT1_07,EMS_CategoryT1_08,EMS_CategoryT1_09) " +
	  	  	      	  	  " values(?,?,?,?,?,?,?,?,?)";
					detail = jdbcTemplate.update(sql,new Object[]{
								Form.getDATA01().trim(),"","",Form.getDATA10().trim(),
								Form.getDATA11().trim(),Form.getDATA12().trim(),Form.getDATA13().trim(),true,compId
							 });
				
					if(detail > 0){
						request.setAttribute("MSG", "代碼明細新增成功!!");
						transactionManager.commit(status);
						
						//清除新增明細欄位
						Form.setDATA10("");
						Form.setDATA11("");
						Form.setDATA12("");
						Form.setDATA13("");
						
					}else{
						request.setAttribute("MSG", "代碼明細新增失敗!!");
					}
				}
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
			}
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", list);
			
		}catch(Exception e){
			transactionManager.rollback(status);
			request.setAttribute("MSG", "代碼明細新增失敗!!");
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return queryForm(mapping,form,request,response);
	}
	
	public ActionForward delDetailForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		TransactionStatus status = transactionManager.getTransaction(def);
		
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
			if (!"".equals(Form.getDATA01()) && Form.getDATA01() != null 
				&& !"".equals(Form.getDATA10()) && Form.getDATA10() != null ){
				try{
					int delete = 0;
//					getSelectOption(request);
//					getSubSelectOption(request,"");
					this.generateSelectBox(conn, Form, request);
					
					String compId = getLoginUser(request).getCompId();
					String sql = null;
					
					//System.out.println("==>"+Form.getDATA10());
					sql = "delete from EMS_CategoryT1 where EMS_CategoryT1_01 = ? and EMS_CategoryT1_04 = ?and EMS_CategoryT1_09 = ?";
					delete = jdbcTemplate.update(sql,new Object[]{Form.getDATA01(),Form.getDATA10(),compId});
					
					if(delete > 0){
						request.setAttribute("MSG", "刪除明細成功!!");
						transactionManager.commit(status);
					}else{
						request.setAttribute("MSG", "刪除明細失敗!!");
					}
					
				}catch(Exception e){
					transactionManager.rollback(status);
					request.setAttribute("MSG", "刪除明細失敗!!");
					e.printStackTrace();
				}
				
			}else{
				request.setAttribute("MSG","請選取要刪除的資料!!");
			}
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
			}
		}
		
		return queryForm(mapping,form,request,response);
	}
	
	public ActionForward saveDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		TransactionStatus status = transactionManager.getTransaction(def);
		
		try{
			int t0 = 0;
			String sql = null;
			String compId = getLoginUser(request).getCompId();
			String name = getLoginUser(request).getUserName();
			
			ActionErrors lc_errors = new ActionErrors();
			//檢核欄位
			try{
				BA_Vaildate ve = BA_Vaildate.getInstance();
				ve.isEmpty(lc_errors, Form.getDATA02(), "DATA02", "不可空白");
				ve.isEmpty(lc_errors, Form.getDATA04(), "DATA04", "不可空白");
				ve.isEmpty(lc_errors, Form.getDATA05(), "DATA05", "不可空白");
			}catch(Exception e){
				e.printStackTrace();
			}
			
			if(lc_errors.isEmpty()){
				sql = " update EMS_CategoryT0 set " +
				  	  " EMS_CategoryT0_SuperClass=?,EMS_CategoryT0_SuperCode=?," +
				  	  " EMS_CategoryT0_02=?,EMS_CategoryT0_03=?,EMS_CategoryT0_04=?,"+
				  	  " EMS_CategoryT0_05=?,USER_UPDATE=?,VERSION=VERSION+1,DATE_UPDATE=NOW()"+
				  	  " where EMS_CategoryT0_01 = ? and EMS_CategoryT0_06 = ?";
				t0 = jdbcTemplate.update(sql,new Object[]{
						        Form.getDATA06()==null?"":Form.getDATA06().trim(),Form.getDATA07()==null?"":Form.getDATA07().trim(),
								Form.getDATA02().trim(),Form.getDATA03().trim(),Form.getDATA04().trim(),
								Boolean.parseBoolean(Form.getDATA05()),name,Form.getDATA01(),compId
					 });
			
				if(t0 == 0){
					request.setAttribute("MSG", "儲存失敗!!");
				}else{
					request.setAttribute("MSG", "儲存成功!!");
					transactionManager.commit(status);
				}
			}else{
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
			}
			
		}catch(Exception e){
			transactionManager.rollback(status);
			request.setAttribute("MSG", "儲存失敗!!");
			e.printStackTrace();
		}
		
		return queryForm(mapping,form,request,response);
	}
	
	public ActionForward redirect(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("redirect");
	}
	
	protected void generateSelectBox( Connection conn, ActionForm form, HttpServletRequest request ) {
		
		try{
			List optionlist = new ArrayList();
			List optionlist_sub = null;
			BA_ALLKINDForm bform = null;
			BA_ALLKINDForm bform_sub = null;
			
			String sql = "" +
			" SELECT EMS_CategoryT0_01 AS CLASSKEY, EMS_CategoryT0_02 AS CLASSKEY_VALUE " +
			" FROM EMS_CategoryT0 " +
			" WHERE 1=1 " +
			" AND EMS_CategoryT0_05 = true " +  //啟用
			" AND EMS_CategoryT0_06 = '"+getLoginUser(request).getCompId()+"' " +  //公司代碼
			" ORDER BY EMS_CategoryT0_04 ";		

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			
			//建立第一筆選項
			bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("一請選擇一");
			optionlist.add(bform);

			Statement stmt_sub = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs_sub = null;
			
			while(rs.next()){
				
				bform = new BA_ALLKINDForm();
				bform.setItem_id(rs.getString("CLASSKEY"));
				bform.setItem_value(rs.getString("CLASSKEY")+"/"+rs.getString("CLASSKEY_VALUE"));
				
				optionlist_sub = new ArrayList();
				
				sql = "" +
				" SELECT EMS_CategoryT1_01 AS CLASSKEY, EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
				" FROM EMS_CategoryT1 " +
				" WHERE 1=1 " +
				" AND EMS_CategoryT1_01 = '"+rs.getString("CLASSKEY")+"' " +  //類別代碼
				" AND EMS_CategoryT1_08 = true " +  //啟用
				" AND EMS_CategoryT1_09 = '"+getLoginUser(request).getCompId()+"' " +  //公司代碼
				" ORDER BY EMS_CategoryT1_07 ";	
				rs_sub = stmt_sub.executeQuery(sql);
				
				while(rs_sub.next()){
					bform_sub = new BA_ALLKINDForm();
					bform_sub.setItem_id(rs_sub.getString("ITEM_ID"));
					bform_sub.setItem_value(rs_sub.getString("ITEM_ID")+"/"+rs_sub.getString("ITEM_VALUE"));
					optionlist_sub.add(bform_sub);
				}
				
				//設定相依清單
				bform.setDependslist(optionlist_sub);
				
				optionlist.add(bform);
				rs_sub.close();
			}
			
			rs.close();
			stmt.close();
			stmt_sub.close();
			
			request.setAttribute("listDATA06", optionlist);
			
		}catch(Exception e){
			request.setAttribute("listDATA06", new ArrayList());
			e.printStackTrace();
		}
		
	}
	

	protected void getSelectOption(HttpServletRequest request)
	{	
		try{
			List datas = new ArrayList();
			String sql = null;
			String compId = getLoginUser(request).getCompId();
			sql = "select EMS_CategoryT0_01,EMS_CategoryT0_02 from EMS_CategoryT0 where EMS_CategoryT0_06 = ?";
			List data = jdbcTemplate.queryForList(sql,new Object[]{compId});
			BA_ALLKINDForm bform1 = new BA_ALLKINDForm();
			bform1.setItem_id("");
			bform1.setItem_value("");
			datas.add(bform1);
			for(int i=0;i<data.size();i++){
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				Map map = (Map)data.get(i);
				bform.setItem_id(map.get("EMS_CategoryT0_01").toString());
				bform.setItem_value(map.get("EMS_CategoryT0_01").toString()+"/"+map.get("EMS_CategoryT0_02"));
				datas.add(bform);
			}
			request.setAttribute("Super_list", datas);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected void getSubSelectOption(HttpServletRequest request,String superClass)
	{
		try{
			List datas = new ArrayList();
			String compId = getLoginUser(request).getCompId();
			if(!"".equals(superClass)){
				String sql = null;
				sql = " select EMS_CategoryT1_04,EMS_CategoryT1_05 from EMS_CategoryT0 left join EMS_CategoryT1" +
			          " on EMS_CategoryT0_01 = EMS_CategoryT1_01 and EMS_CategoryT0_06 = EMS_CategoryT1_09 " +
			          " where EMS_CategoryT0_01 = ? and EMS_CategoryT0_05 = ? and EMS_CategoryT1_08 = ?" +
			          " and EMS_CategoryT0_06 = ? order by EMS_CategoryT0_04,EMS_CategoryT1_07";
				List data = jdbcTemplate.queryForList(sql,new Object[]{
						superClass,true,true,compId
				});
				if(data.size() > 0){
					BA_ALLKINDForm bform1 = new BA_ALLKINDForm();
					bform1.setItem_id("");
					bform1.setItem_value("");
					datas.add(bform1);
					for(int i=0;i<data.size();i++){
						Map map = (Map)data.get(i);
						BA_ALLKINDForm bform = new BA_ALLKINDForm();
						bform.setItem_id(map.get("EMS_CategoryT1_04").toString());
						bform.setItem_value(map.get("EMS_CategoryT1_04")+"/"+map.get("EMS_CategoryT1_05"));
						datas.add(bform);
					}
				}
			}
			request.setAttribute("Sub_list", datas);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
