package com.spon.ems;

import java.io.File;
import java.io.FileInputStream;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.struts.IndirectNewDispatchAction;

import vtgroup.ems.common.vo.AuthorizedBean;

import fr.improve.struts.taglib.layout.util.FormUtils;
import fr.improve.struts.taglib.layout.util.LayoutUtils;


public class NewDispatchAction extends IndirectNewDispatchAction {
	
	public ActionForward execute(ActionMapping in_mapping, ActionForm in_form, HttpServletRequest request, HttpServletResponse in_response) throws Exception {
		
		try{
			//GET SETTING
			String lc_parameterName = in_mapping.getParameter();
			String path = in_mapping.getPath();
			String reqCode = request.getParameter("reqCode");
			
			//若要轉換語言，則直接導向LanguageSelect.do
			if(path.equals("/Locale") 
//					&&(
//					   "traditionalchinese".equals(reqCode) ||
//					   "simplechinese".equals(reqCode) ||
//					   "english".equals(reqCode) ||
//					   "japanese".equals(reqCode)
//					)
			){
				return super.execute(in_mapping, in_form, request, in_response);
			}
			
			//set UseFormDisplayMode = false
			LayoutUtils.setUseFormDisplayMode(false);
			
			//set OverwriteMode = true, edit by joe 2011/02/15
			FormUtils.setOverwriteMode(request, "OVERWRITE_FLAG", true);
			
			//判斷登入資訊是否存在
			if( !super.isLogin(request) && !this.isLogin(request) 
				&& !path.equals("/login") && !path.equals("/mobile_login") && !lc_parameterName.equals("loginScreen")){
				if("getAjaxData".equals(reqCode) && reqCode != null){
					//建立 return JSON Object
					JSONObject resultJSON = new JSONObject();
					resultJSON.accumulate("success", true);  //是否成功
					resultJSON.accumulate("message", "Session failed!!");  //顯示訊息
					resultJSON.accumulate("forward", in_mapping.findForward("getmobileout").getPath());  //程式導向路徑
					
					//設定Ajax Header
					in_response.setHeader( "Pragma", "no-cache" );
					in_response.addHeader( "Cache-Control", "must-revalidate" );
					in_response.addHeader( "Cache-Control", "no-cache" );
				    in_response.addHeader( "Cache-Control", "no-store" );
				    in_response.setDateHeader("Expires", 0);
					
					//處理Ajax前端回應的寫入
					in_response.setCharacterEncoding("utf-8");
					in_response.getWriter().print(resultJSON.toString());
					in_response.getWriter().flush();
					in_response.getWriter().close();
					
					//已用Ajax回應, 後續不需要處理
					return null;
					
				}else{
					return in_mapping.findForward("getout");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return super.execute(in_mapping, in_form, request, in_response);
	}
	
	/**
	 * 判斷使用者是否有登入，方法： 檢查session中是否有auth，如果有的話就將auth放到Member varialbes
	 * 如果沒有的話，就回傳false
	 * 
	 * @param request
	 * @return
	 */
	public boolean isLogin(HttpServletRequest request) {
		boolean check = false;
		
		try{
			 HttpSession session = (HttpSession) request.getSession();
		     if(session.getAttribute("auth") == null) {
		    	 check = false;
		     }else {
		    	 check = true;
		     }
		     
		    }catch(Exception e){
		    	// TODO: handle exception
				System.out.println("NewDispatchAction.isLogin()" + e);
				check = false;
				e.printStackTrace();
			}
		    
			return check;			
	}
	
	/**
	 *getLoginUser使用EMS-FLOW session 取得當前登入者資訊
	 *Modify  by joe  2010/08/23
	 **/
	protected AuthorizedBean getLoginUser(HttpServletRequest request)
	{
		AuthorizedBean authBean = null;
		
		try{
		 HttpSession session = (HttpSession) request.getSession();
	     authBean = (AuthorizedBean) session.getAttribute("auth");
	     
	    }catch(Exception e){
			e.printStackTrace();
		}
	    
		return authBean;
	     
	}
	
	public ActionForward exceldownload(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		//取得檔案路徑
		File file = new File(request.getParameter("filename"));
		//================舊的下載方法開始========
		//顯示的檔案名稱
//		String filename = request.getParameter("displayfilename");
//	
//		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		//================舊的下載方法結束========
		//================新的下載方法開始========
		//顯示的檔案名稱(列印時，可以出現遮罩)   Alvin
		
		try {
		
			//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
			response.setHeader("Content-Disposition", "attachment;filename="+new String(((String)request.getParameter("name")).getBytes("BIG5"), "iso8859-1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//================新的下載方法結束========
		 if (file.exists()) {
		      MimetypesFileTypeMap MIME_TYPES=new MimetypesFileTypeMap();
			//使用MimetypesFileTypeMap 自行以副檔名回覆相對應的MIME_TYPE
		      response.setContentType(MIME_TYPES.getContentType(file.getName()));
		      try {
		  		IOUtils.copy(new FileInputStream(file), response.getOutputStream());
		  		response.setStatus(response.SC_OK);
		  	    response.flushBuffer();
		  	    response=null;
		  	} catch (Exception e) {
		  		// TODO Auto-generated catch block
		  		e.printStackTrace();
		  	}
		      
		 }
		
		return null;
	}


}
