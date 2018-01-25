package com.spon.ems.vacation.actions;

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
import com.spon.ems.fileimport.IMP_EHF020200;
import com.spon.ems.vacation.forms.EHF020200M0F;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 *@author maybe
 *@version 1.0
 *@created 2015/11/19 上午10:33:39
 */
public class EHF020200M4A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020200M0F Form = new EHF020200M0F();
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
	 * 匯入請假單
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward impEHF020200(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF020200M0F Form = (EHF020200M0F) form;
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
			
			//建立請假單匯入元件
			IMP_EHF020200 imp_ehf020200 = new IMP_EHF020200( authbean.getEmployeeID(), (String)((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID"), authbean, depMap, empMap);
			
			//取得匯入檔案
			FormFile importfile = Form.getUPLOADFILE();
			
			//取得回傳訊息Map
			Map msgMap = imp_ehf020200.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(),
												  importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());
			//取得匯入的詳細資料清單
			List datalist = (List) msgMap.get("DATALIST");
			
			Iterator it = datalist.iterator();
			Map tempMap = null;
			EHF020200M0F bean = null;
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				bean = new EHF020200M0F();
				
				String yera_month_day_start=(String)tempMap.get("EHF020200T0_09_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_09_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_09_day")));
				String yera_month_day_end  =(String)tempMap.get("EHF020200T0_10_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_10_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_10_day")));
				
				bean.setEHF020200T0_03_TXT((String)tempMap.get("EHF020200T0_03")+" / "+(String)tempMap.get("EHF020200T0_03_NAME"));
				bean.setEHF020200T0_14_DESC((String)tempMap.get("EHF020200T0_14"));
				bean.setEHF020200T0_09(yera_month_day_start+" "+(String)tempMap.get("EHF020200T0_11_HH")+":" +(String)tempMap.get("EHF020200T0_11_MM") 
					   +" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF020200T0_12_HH")+":" +(String)tempMap.get("EHF020200T0_12_MM"));
				bean.setEHF020200T0_15((String)tempMap.get("EHF020200T0_15"));
				
				list.add(bean);
			}
			
			//判斷是否有重複未匯入的資料清單
			if(msgMap.containsKey("NGDATACOUNT")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("NGDATALIST");
				
				it = datalist.iterator();
				tempMap = null;
				bean = null;
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bean = new EHF020200M0F();
					String yera_month_day_start=(String)tempMap.get("EHF020200T0_09_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_09_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_09_day")));
					String yera_month_day_end  =(String)tempMap.get("EHF020200T0_10_year")+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_10_month")))+"/"+String.format("%02d",Integer.valueOf((String)tempMap.get("EHF020200T0_10_day")));
					
					bean.setEHF020200T0_03_TXT((String)tempMap.get("EHF020200T0_03")+" / "+(String)tempMap.get("EHF020200T0_03_NAME"));
					bean.setEHF020200T0_14_DESC((String)tempMap.get("EHF020200T0_14"));
					bean.setEHF020200T0_09( yera_month_day_start+" "+(String)tempMap.get("EHF020200T0_11_HH")+":" +(String)tempMap.get("EHF020200T0_11_MM") 
							+" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF020200T0_12_HH")+":" +(String)tempMap.get("EHF020200T0_12_MM"));
					bean.setEHF020200T0_15((String)tempMap.get("error"));//20131016修改   BY 賴泓錡
					
					ng_list.add(bean);
				}
				if(ng_list.size()>0){
					// 設定前端顯示訊息
					request.setAttribute("NGMSG", "請假單共有 "	+ (Integer) msgMap.get("NGDATACOUNT")+ " 筆重複資料未匯入!!");
					request.setAttribute("Form3Datas", ng_list);
					request.setAttribute("ng_collection", "show");
				}
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
					bean = new EHF020200M0F();
					
					String yera_month_day_start=(String)tempMap.get("EHF020200T0_09_year")+"/"+(String)tempMap.get("EHF020200T0_09_month")+"/"+(String)tempMap.get("EHF020200T0_09_day");
					String yera_month_day_end  =(String)tempMap.get("EHF020200T0_10_year")+"/"+(String)tempMap.get("EHF020200T0_10_month")+"/"+(String)tempMap.get("EHF020200T0_10_day");
					
					bean.setEHF020200T0_03_TXT((String)tempMap.get("EHF020200T0_03")+" / "+(String)tempMap.get("EHF020200T0_03_NAME"));
					bean.setEHF020200T0_14_DESC((String)tempMap.get("EHF020200T0_14"));
					bean.setEHF020200T0_09( yera_month_day_start+" "+(String)tempMap.get("EHF020200T0_11_HH")+":" +(String)tempMap.get("EHF020200T0_11_MM") 
							+" ~ " +yera_month_day_end+" "  +(String)tempMap.get("EHF020200T0_12_HH")+":" +(String)tempMap.get("EHF020200T0_12_MM"));
					bean.setEHF020200T0_15((String)tempMap.get("error"));//20131016修改   BY 賴泓錡
					
					error_list.add(bean);
				}
				if(error_list.size()>0){
					//設定前端顯示訊息
					request.setAttribute("ERRORMSG", "請假單共有 "+(Integer)msgMap.get("ERRORDATACOUNT")+" 筆格式不正確資料未匯入!!");
					request.setAttribute("Form4Datas",error_list);
					request.setAttribute("error_collection", "show");
				}
			}
			
			hr_tools.close();
			
			//設定前端顯示訊息
			request.setAttribute("MSG", "請假單共匯入 "+(Integer)msgMap.get("DATACOUNT")+" 筆!!");

			//設定前端顯示資訊
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("collection", "show");
			
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
