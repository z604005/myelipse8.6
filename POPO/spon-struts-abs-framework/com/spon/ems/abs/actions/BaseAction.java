package com.spon.ems.abs.actions;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.db.EMS_DB_CONTROLLER;
import com.spon.ems.msg.EMS_MSGSYSTEM;
import com.spon.utils.util.BA_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;
import fr.improve.struts.taglib.layout.util.TabsUtil;

/**
 * <Action>思邦科技系統底層抽象類別

 */
public abstract class BaseAction extends NewDispatchAction {
	
	protected final BA_TOOLS tools = BA_TOOLS.getInstance();
	
	protected final String success_config = "success";
	protected final String redirect_config = "redirect";
	protected final String QueryButtion = "query";
	protected final String EditButtion = "edit";
	protected final String ShowCollection = "show";
	protected final String EMPTY = "";
	protected final String USER_ID = "USER_ID";
	protected final String USER_CODE = "USER_CODE";
	protected final String USER_NAME = "USER_NAME";
	protected final String EMPLOYEE_ID = "EMPLOYEE_ID";
	protected final String COMP_ID = "COMP_ID";
	protected final String LOGIN_AUTHBEAN = "LOGIN_AUTHBEAN";
	protected final String REQUEST = "REQUEST";
	//protected static String comp_id = "";
	//protected static String employee_id = "";
	//protected static String user_id = "";
	//protected static String user_code = "";
	//protected static String user_name = "";
	//protected static AuthorizedBean login_authbean;
	
	protected final String StrutsLayoutCreateMode = "create";
	protected final String StrutsLayoutEditMode = "edit";
	protected final String StrutsLayoutInspectMode = "inspect";
	//protected final String ems_strutslayout_mode = "";
	protected final String EMS_STRUTSLAYOUT_MODE = "EMS_STRUTSLAYOUT_MODE";
	
	protected final String EMSCONFIG = "EMSCONFIG";
	
	//Tab Control Map
	//protected final Map tabCtrlMap = new HashMap();
	//Msg Control Map
	//protected final Map msgCtrlMap = new HashMap();
	//Control LayoutPopup
	//protected final String ctrlOpenLayoutPopup = "";
	protected final String CTRLOPENLAYOUTPOPUP = "ctrlOpenLayoutPopup";
	
	//訊息元件用的資料庫連線
	//protected Connection ems_msg_conn = null;
	//EMS系統訊息元件
	protected final EMS_MSGSYSTEM ems_msg_tools = EMS_MSGSYSTEM.getInstance();
	
	public BaseAction() {
		
		//建立登入者使用資訊
		try{
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 取得資料庫連線
	 * @return
	 */
	protected final Connection getConnectionn(){
		
		Connection local_conn = null;
		EMS_DB_CONTROLLER ems_db_c = null;
		
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
			local_conn = ems_db_c.getConnection();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
		
		return local_conn;
	}
	
	/**
	 * 例外記錄
	 * @param e
	 */
	protected final void handleException( Exception e, Map paramMap){
		
		try{
			System.out.println("JAVA例外錯誤訊息："+e+",  "+e.getMessage());
			
			//寫入錯誤記錄LOG - In DataBase
			this.writeMSGForm( "ERR_MSG_0001", "JAVA_EXCEPTION",
					 		   e.getMessage(),
					 		   (String)paramMap.get("USER_NAME"), EMS_MSGSYSTEM.Serious, EMS_MSGSYSTEM.E_SYSTEM_MANGER, 
					 		   (String)paramMap.get("COMP_ID"));

		}catch(Exception e_log ){
			System.out.println("執行例外處理記錄時，發生錯誤!!"+e_log.getMessage());
			e_log.printStackTrace();
		}
		
	}
	
	/**
	 * 寫入EMS系統訊息
	 * @param error_code
	 * @param error_type
	 * @param error_msg
	 * @param user
	 * @param severity_level
	 * @param msg_notification_level
	 * @param comp_id
	 */
	public void writeMSGForm( String error_code, String error_type, String error_msg, String user, 
				 String severity_level, String msg_notification_level, String comp_id ){
		
		EMS_DB_CONTROLLER ems_db_c = null;
		
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
		
		try{
			
			
			//寫入錯誤記錄LOG - In DataBase
			this.ems_msg_tools.writeMSGForm(ems_db_c.getConn(), 
									        error_code, error_type, error_msg, user, severity_level, msg_notification_level, comp_id);
			
		}catch(Exception e_log ){
			System.out.println("執行例外處理記錄時，發生錯誤!!"+e_log.getMessage());
			e_log.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * 設定 StrutsLayout顯示模式(Override)
	 * @param paramMap
	 * @param mode
	 */
	protected void setFormDisplayMode( Map paramMap, String mode ){
		
		try{
			//設定Struts-Layout顯示模式
			paramMap.put(this.EMS_STRUTSLAYOUT_MODE, mode);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 設定Struts-Layout顯示模式
	 * @param request
	 * @param form
	 * @param mode
	 */
	private void setFormDisplayMode( Map paramMap, HttpServletRequest request, ActionForm form, String mode ){
		
		try{
			//取得Struts-Layout顯示模式
			String ems_strutslayout_mode = (String)paramMap.get(this.EMS_STRUTSLAYOUT_MODE);
			
			//設定Struts-Layout顯示模式
			//先判斷是否已有覆蓋的設定值
			if(!"".equals(ems_strutslayout_mode) && ems_strutslayout_mode != null){
				FormUtils.setFormDisplayMode(request, form, ems_strutslayout_mode);
				//System.out.println(this.ems_strutslayout_mode);
				//清除控制的顯示模式
				ems_strutslayout_mode = "";
			}else{
				FormUtils.setFormDisplayMode(request, form, mode);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 設定create顯示模式
	 * @param request
	 * @param form
	 */
	protected final void setCreateMode( Map paramMap, HttpServletRequest request, ActionForm form ){
		
		try{
			//設定create顯示模式
			this.setFormDisplayMode( paramMap, request, form, this.StrutsLayoutCreateMode);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 設定edit顯示模式
	 * @param request
	 * @param form
	 */
	protected final void setEditMode( Map paramMap, HttpServletRequest request, ActionForm form ){
		
		try{
			//設定edit顯示模式
			this.setFormDisplayMode(paramMap, request, form, this.StrutsLayoutEditMode);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 設定inspect顯示模式
	 * @param request
	 * @param form
	 */
	protected final void setInspectMode( Map paramMap, HttpServletRequest request, ActionForm form ){
	
		try{
			//設定inspect顯示模式
			this.setFormDisplayMode(paramMap, request, form, this.StrutsLayoutInspectMode);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 設定Button Display Mode
	 * @param request
	 * @param mode
	 */
	protected final void setButtionDispalyMode( HttpServletRequest request, String mode ){
		
		try{
			//設定Button顯示模式
			request.setAttribute("button", mode);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 設定Collection Dispaly Mode
	 * @param request
	 * @param mode
	 */
	protected final void setCollectionDispalyMode( HttpServletRequest request, String mode ){
		
		try{
			//設定Button顯示模式
			request.setAttribute("collection", mode);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得檢查後的Map物件
	 * @param dataMap
	 * @param key
	 * @param class_type
	 * @return
	 */
	protected final Object getMapDataWithCHK( Map dataMap, String key, Object ori_obj ){
		
		Object obj = null;
		
		try{
			if(dataMap.containsKey(key)){
				//此Map結構有Key的資料
				obj = dataMap.get(key);
			}else{
				//回傳給定的Object
				obj = ori_obj;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return obj;
	}
	
	/**
	 * 下載檔案
	 * @param request
	 * @param response
	 * @param filename
	 * @param file
	 * @return
	 */
	protected final boolean downLoadFile( HttpServletRequest request, HttpServletResponse response, String filename, File file ){
		
		boolean chk_flag = false;
		
		try{
			//開啟檔案下載視窗
			response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(filename, "UTF-8"));
			ServletOutputStream ouputStream;
			
			//執行檔案輸出
			try {
				InputStream in = new FileInputStream(file);
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
				
				//檔案輸出執行完成
				chk_flag = true;
				
			} catch (IOException e) {
				//處理顯示訊息
				request.setAttribute("MSG", "檔案下載失敗!!");
				//記錄錯誤訊息
				this.handleException(e, this.readyEMSConfig(request));
				e.printStackTrace();
			}
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, this.readyEMSConfig(request));
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 上傳檔案
	 * @param inputfile
	 * @param ouputfile
	 * @return
	 */
	protected final boolean upLoadFile( InputStream inputfile, File outputfile ){
		
		boolean chk_flag = false;
		
		try{
			//執行檔案上傳的寫入動作
			OutputStream out = new FileOutputStream(outputfile);
			int bit = 0;
			byte [] bits=new byte[4096];
			while ((bit=inputfile.read(bits)) >-1 ) {
				out.write(bits,0,bit);
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					chk_flag = false;
					e.printStackTrace();
				}
			}
			out.flush();
			out.close();
			inputfile.close();
			
			//設定檔案上傳完成Flag
			chk_flag = true;
			
		}catch (IOException e) {
			chk_flag = false;
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
		return chk_flag;
	}
	
	/**
	 * 確認檔案路徑是否存在
	 * @param Path
	 * @param employee_id
	 */
	protected final void check_Path_Exist(String Path , String last_Folder) {
	
		try{
			File file=new File(Path);
			if(!file.exists())
				file.mkdirs();
			file=new File(Path+last_Folder+"/");
			if(!file.exists())
				file.mkdirs();
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
			
	}
	
	/**
	 * 寫入 Tabs Config
	 * @param keyname
	 * @param keyvalue
	 * @param overrite_flag
	 */
	protected final void addCurrentTabConfig( Map paramMap, String keyname, String keyvalue, boolean overrite_flag ){
		
		Map config = new HashMap();
		
		try{
			//取得 tabCtrlMap
			Map tabCtrlMap = (Map)paramMap.get("tabCtrlMap");
				
			//建立 config Map
			config.put("KEYNAME", keyname);
			config.put("KEYVALUE", keyvalue);
			config.put("OVERRIDE_FLAG", overrite_flag);
			
			//判斷是否有相同設定
			if(tabCtrlMap.containsKey(keyname)){
				if((Boolean)((Map)tabCtrlMap.get(keyname)).get("OVERRIDE_FLAG")){
					//移除已存在的Map
					tabCtrlMap.remove(keyname);
					//加入覆蓋的MAP
					tabCtrlMap.put(keyname, config);
				}
			}else{
				//若無相關的設定則直接設定config
				tabCtrlMap.put( keyname, config);
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 設定 TabsUtil
	 * @param request
	 * @param response
	 */
	protected final void setEMSCurrentTab( Map paramMap, HttpServletRequest request, HttpServletResponse response ){
		
		try{
			//取得 tabCtrlMap
			Map tabCtrlMap = (Map)paramMap.get("tabCtrlMap");
			
			String keyname = "";
			//取得 Tab 的Key Set
			Iterator it = tabCtrlMap.keySet().iterator();
			
			while(it.hasNext()){
				
				//取得 Key
				keyname = (String)it.next();
				
				//取得設定
				//設定 Struts - Layout Tabs
				TabsUtil.setCurrentTab( keyname, (String)((Map)tabCtrlMap.get(keyname)).get("KEYVALUE"), request, response);
				
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 寫入 View MSG
	 * @param key
	 * @param msg
	 * @param overrite_flag
	 */
	protected final void addViewMSG( Map paramMap, String key, String msg, boolean overrite_flag ){
		
		Map config = new HashMap();
		
		try{
			//取得 msgCtrlMap
			Map msgCtrlMap = (Map)paramMap.get("msgCtrlMap");
			
			//建立 MSG Map
			config.put("KEY", key);
			config.put("MSG", msg);
			config.put("OVERRIDE_FLAG", overrite_flag);
			
			//判斷是否有相同設定
			if(msgCtrlMap.containsKey(key)){
				if((Boolean)((Map)msgCtrlMap.get(key)).get("OVERRIDE_FLAG")){
					//移除已存在的Map
					msgCtrlMap.remove(key);
					//加入覆蓋的MAP
					msgCtrlMap.put(key, config);
				}
			}else{
				//若無相關的設定則直接設定config
				msgCtrlMap.put( key, config);
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 設定 MSG
	 * @param request
	 */
	protected final void setEMSViewMSG( Map paramMap, HttpServletRequest request ){
		
		try{
			//取得 msgCtrlMap
			Map msgCtrlMap = (Map)paramMap.get("msgCtrlMap");
			
			String key = "";
			//取得 Tab 的Key Set
			Iterator it = msgCtrlMap.keySet().iterator();
			
			while(it.hasNext()){
				
				//取得 Key
				key = (String)it.next();
				
				//設定 Attribute
				request.setAttribute(key, (String)((Map)msgCtrlMap.get(key)).get("MSG"));
				
			}
			
			//清除Msg Map
			msgCtrlMap = new HashMap();
			
			//取得 前端 LayoutPopup 是否開啟的設定
			String ctrlOpenLayoutPopup = (String)paramMap.get(this.CTRLOPENLAYOUTPOPUP);
			String local_ctrlOpenLayoutPopup = "";
			//設定前端 LayoutPopup 是否開啟的設定
			if(!"".equals(ctrlOpenLayoutPopup) && ctrlOpenLayoutPopup != null){
				local_ctrlOpenLayoutPopup = ctrlOpenLayoutPopup;
			}
			request.setAttribute(this.CTRLOPENLAYOUTPOPUP, local_ctrlOpenLayoutPopup);
			//清除 Contrl LayoutPopup 設定
			ctrlOpenLayoutPopup = "";
			
			
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Ready EMS Config
	 * @param request
	 */
	protected final Map readyEMSConfig( HttpServletRequest request ){
		
		Map paramMap = new HashMap();
		
		try{
			//建立使用者登入參數
			AuthorizedBean authbean = super.getLoginUser(request);
			if(authbean != null){
				paramMap.put("COMP_ID", authbean.getCompId());
				paramMap.put("USER_ID", authbean.getUserId());
				paramMap.put("USER_CODE", authbean.getUserCode());
				paramMap.put("EMPLOYEE_ID", authbean.getEmployeeID());
				paramMap.put("USER_NAME", authbean.getUserName());
				paramMap.put("LOGIN_AUTHBEAN", authbean);
				paramMap.put("REQUEST", request);
				
				//設定相關參數用的Map
				//設定 tabCtrlMap
				paramMap.put("tabCtrlMap", new HashMap());
				//設定 msgCtrlMap
				paramMap.put("msgCtrlMap", new HashMap());
				//設定 ctrlOpenLayoutPopup
				paramMap.put("ctrlOpenLayoutPopup", new String(""));
				
			}
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
		return paramMap;
	}
	
	/**
	 * Forward
	 * @param mapping
	 * @param forward
	 * @return
	 */
	protected ActionForward redirect( ActionMapping mapping, String forward ){
		
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward(forward);
	}
	
	/**
	 * Struts - Execute Overwrite
	 */
	public ActionForward execute(ActionMapping in_mapping, ActionForm in_form, HttpServletRequest request, HttpServletResponse in_response) throws Exception {
		
		try{
			//建立使用者登入參數
			request.setAttribute(this.EMSCONFIG, this.readyEMSConfig(request));
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
		return super.execute(in_mapping, in_form, request, in_response);
	}
	
	/**
	 * 取得當前Connection 最後輸入的 auto_increament Id
	 * @param conn
	 * @return
	 */
	public int getLastInsertId( Connection conn ){
		
		int id = -1;
		
		try{
			id = new EMS_DB_CONTROLLER().getLastInsertId(conn);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return id;
	}
	
	/**
	 * 提供Ajax呼叫取資料
	 * @param in_mapping
	 * @param in_form
	 * @param request
	 * @param in_response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAjaxData(ActionMapping in_mapping, ActionForm in_form, HttpServletRequest request, HttpServletResponse in_response) 
		   throws Exception {
		
		EMS_DB_CONTROLLER ems_db_c = null;
		
		try{
			//開啟資料庫控制器
			ems_db_c = new EMS_DB_CONTROLLER();
		}catch(Exception e_conn){
			e_conn.printStackTrace();
		}
		
		Map paramMap = null;
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(this.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			//程式執行路徑
			String path = in_mapping.getPath();
			//Action Name
			String ActionName = path.replaceAll("/", "");
			
			//取得要呼叫方法的名稱
			String method_name = request.getParameter("mobile_reqCode");
			
			//JSON處理結果
			Map resultJSON_map = new HashMap();
			String resultJSON_str = "";
			
			if(!"".equals(method_name) && method_name != null && !"getForwardPath".equals(method_name)){
				//動態呼叫子類別方法
				Method method = this.getClass().getMethod(method_name, Connection.class, ActionForm.class, Map.class);
				resultJSON_map = (Map) method.invoke(this, ems_db_c.getConn(), in_form, paramMap);
				//取得要回應的JSON字串
				resultJSON_str = (String)resultJSON_map.get("resultJSON");
				
				//增加處理EMS Ajax Session 相關資訊
				JSONObject resultJSON = JSONObject.fromObject(resultJSON_str);
				resultJSON.accumulate("session_expire", false);  //Session是否過期flag
				resultJSON.accumulate("session_expire_message", "EMS Session available!!");  //Session訊息
				
				//轉換為JSON String
				resultJSON_str = resultJSON.toString();
				
			}else if(!"".equals(method_name) && method_name != null && "getForwardPath".equals(method_name)){
				//取得Mobile頁面路徑
				//建立 return JSON Object
				JSONObject resultJSON = new JSONObject();
				resultJSON.accumulate("success", true);  //是否成功
				resultJSON.accumulate("session_expire", false);  //Session是否過期flag
				resultJSON.accumulate("session_expire_message", "EMS Session available!!");  //Session訊息
				resultJSON.accumulate("message", "getForwardPath");  //顯示訊息
				String mobile_forward = request.getParameter("mobile_forward");
				if(!"".equals(mobile_forward) && mobile_forward != null){
					resultJSON.accumulate("forward", in_mapping.findForward(mobile_forward).getPath());  //程式導向路徑
				}else{
					resultJSON.accumulate("forward", in_mapping.findForward(this.success_config).getPath());  //程式導向路徑
				}
				resultJSON_str = resultJSON.toString();
			}else{
				
			}
			
			//處理Ajax前端回應的寫入
			this.setAjaxResponse(in_response, resultJSON_str);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}finally{
			try{
				//關閉資料庫控制器
				ems_db_c.close();
			}catch(Exception e_conn){
				e_conn.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * 處理Mobile Ajax呼叫, Session過期的後續處理動作
	 * @param in_mapping
	 * @param in_form
	 * @param request
	 * @param in_response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAjaxSessionExpire(ActionMapping in_mapping, ActionForm in_form, HttpServletRequest request, HttpServletResponse in_response) 
	   throws Exception {
		
		Map paramMap = null;
		
		try{
			//準備EMS參數
			paramMap = (Map)request.getAttribute(this.EMSCONFIG);
		}catch(Exception e_param){
			e_param.printStackTrace();
		}
		
		try{
			String resultJSON_str = "";
			
			//建立Mobile Ajax Session過期資訊
			//建立 return JSON Object
			JSONObject resultJSON = new JSONObject();
			resultJSON.accumulate("success", true);  //是否成功
			resultJSON.accumulate("session_expire", true);  //Session是否過期flag
			resultJSON.accumulate("session_expire_message", "EMS Session expire!!");  //Session訊息
			resultJSON_str = resultJSON.toString();
			
			//處理Ajax前端回應的寫入
			this.setAjaxResponse(in_response, resultJSON_str);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}finally{

		}
	
		return null;
}
	
	/**
	 * 處理Ajax前端資料回應
	 * @param response
	 * @param resultJSON_str
	 */
	protected void setAjaxResponse(HttpServletResponse response, String resultJSON_str){
		
		try{
			//設定Ajax Header
			response.setHeader( "Pragma", "no-cache" );
		    response.addHeader( "Cache-Control", "must-revalidate" );
		    response.addHeader( "Cache-Control", "no-cache" );
		    response.addHeader( "Cache-Control", "no-store" );
		    response.setDateHeader("Expires", 0);
		    
			//處理Ajax前端回應的寫入
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(resultJSON_str);
			response.getWriter().flush();
			response.getWriter().close();
			
		}catch(Exception e){
			//記錄錯誤訊息
			e.printStackTrace();
		}
		
	}
	
}