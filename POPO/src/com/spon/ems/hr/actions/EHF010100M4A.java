package com.spon.ems.hr.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.fileimport.IMP_EHF010300;
import com.spon.ems.hr.forms.EHF010100M0F;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/25 下午2:57:58
 */
public class EHF010100M4A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF010100M0F Form = (EHF010100M0F) form;
		List list = new ArrayList();
		
		try{
			
		}catch (Exception e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
		
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
			hr_tools.close();
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
		
		return mapping.findForward("success");
	}
	
	public ActionForward redirect(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		return mapping.findForward("redirect");
	}

}
