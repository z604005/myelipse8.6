package com.spon.utils.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.spon.ems.NewDispatchAction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;


public class EMS_CategoryM0A extends NewDispatchAction
{
	private DataSourceTransactionManager transactionManager;    
	private DefaultTransactionDefinition def;
	private JdbcTemplate jdbcTemplate;
	private BA_TOOLS tool;
	
	public EMS_CategoryM0A(){
		tool = BA_TOOLS.getInstance();
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
		
		try{
			//產生下拉選單
			getSelectOption(request);
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", list);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward("success");
	}
	
	public ActionForward addDataForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("redirectADD");
	}
	
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		List list = new ArrayList();
		
		try{
			String compId = getLoginUser(request).getCompId();
			//產生下拉選單
			getSelectOption(request);
			String sql = null;
			String str = null;
			String enable = "";
			
			sql = " SELECT EMS_CategoryT0_01, EMS_CategoryT0_02, EMS_CategoryT0_03, EMS_CategoryT0_05 " +
				  " FROM EMS_CategoryT0 " +
				  " WHERE 1=1 " +
				  " AND EMS_CategoryT0_06 = '"+compId+"' ";  //公司代碼
	
			if(!"".equals(Form.getDATA01()) && Form.getDATA01() != null ){
				sql += " and EMS_CategoryT0_01 like '%"+Form.getDATA01().trim()+"%'";  //類別代碼
			}
			if(!"".equals(Form.getDATA02()) && Form.getDATA02() != null ){
				sql += " and EMS_CategoryT0_02 like '%"+Form.getDATA02().trim()+"%' ";  //類別名稱
			}
			if(!"".equals(Form.getDATA03()) && Form.getDATA03() != null ){
				sql += " and EMS_CategoryT0_05 = "+Form.getDATA03()+"";  //啟用
			}
			sql += " order by EMS_CategoryT0_01, EMS_CategoryT0_04";
			
			List data = jdbcTemplate.queryForList(sql);
			
			if(data.size() > 0){
				for(int i=0;i<data.size();i++){
					Map map = (Map)data.get(i);
					EMS_VIEWDATAF bForm = new EMS_VIEWDATAF();
					bForm.setDATA01(map.get("EMS_CategoryT0_01").toString());
					bForm.setDATA02(map.get("EMS_CategoryT0_02").toString());
//					bForm.setDATA03(map.get("EMS_CategoryT0_05").toString());
					if("true".equals(map.get("EMS_CategoryT0_05").toString())){
						enable = "是"; 
					}else{
						enable = "否";
					}
					bForm.setDATA03(enable);
					bForm.setDATA04(map.get("EMS_CategoryT0_03").toString());
					list.add(bForm);
				}
			}else{
				request.setAttribute("MSG", "查無資料!!");
			}
			
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas", list);
			
		}catch(Exception e){
			request.setAttribute("MSG", "查詢失敗!!");
			e.printStackTrace();
		}
		
		return mapping.findForward("success");
	}
	
	public ActionForward delForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
		List list = new ArrayList();
		TransactionStatus status = transactionManager.getTransaction(def);
		//String[] id = request.getParameterValues("checkId");
		
		try{
			if (!"".equals(Form.getDATA01()) && Form.getDATA01() != null ){
			
				try{
					int delete = 0;
					getSelectOption(request);
					String compId = getLoginUser(request).getCompId();
					String sql = null;
					
					//刪除表頭資料
					sql = "delete from EMS_CategoryT0 where EMS_CategoryT0_01 = ? and EMS_CategoryT0_05 = ? and EMS_CategoryT0_06 = ?";
					delete = jdbcTemplate.update(sql,new Object[]{Form.getDATA01(),false,compId});
					
					//刪除明細資料
					sql = "delete from EMS_CategoryT1 where EMS_CategoryT1_01 = ? and EMS_CategoryT1_09 = ?";
					jdbcTemplate.update(sql,new Object[]{Form.getDATA01(),compId});
					
					if(delete > 0){
						request.setAttribute("MSG", "刪除成功!!");
					}else{
						request.setAttribute("MSG", "刪除失敗，啟用條件為否才可刪除!!");
					}
					
				}catch(Exception e){
					transactionManager.rollback(status);
					request.setAttribute("MSG", "刪除失敗!!");
					e.printStackTrace();
				}
				transactionManager.commit(status);
				request.setAttribute("Form1Datas", Form);
				request.setAttribute("Form2Datas", list);
				
				Form.setDATA01("");
				return mapping.findForward("success");

			}else{
				request.setAttribute("MSG","請點選一筆要刪除的資料!!");
				Form.setDATA01("");
				return init(mapping,form,request,response);
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return init(mapping,form,request,response);
	}
	
	protected void getSelectOption(HttpServletRequest request)
	{	
		String[] priority = {"是","否"};
		boolean[] flag = {true,false};
		try{
			List datas = new ArrayList();
			for(int i=0;i<priority.length;i++){
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id(String.valueOf(flag[i]));
				bform.setItem_value(priority[i]);
				datas.add(bform);
			}
			request.setAttribute("Enable_list", datas);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//測試程式
		/*
		try{
			BA_TOOLS tools = BA_TOOLS.getInstance();
			BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
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
				request.setAttribute("listDATA04", ems_tools.getOptions(conn, true, "EMS", "SPON" ));
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
			
		}catch(Exception e){
			e.printStackTrace();
		}
		*/
		
	}
}
