package com.spon.ems.vacation.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.spon.ems.vacation.actions.EHF020501M0A;
import com.spon.ems.vacation.forms.EX020501R1F;
import com.spon.ems.vacation.forms.EX020501R2F;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;

import fr.improve.struts.taglib.layout.util.FormUtils;
//請假月統計表
public class EHF020501M0A extends QueryAction{

	private BA_TOOLS tools ;
	private EMS_ACCESS ems_access;
	
	private float work_hours;
	private float total ;
	public EHF020501M0A(){
		tools = BA_TOOLS.getInstance();
		ems_access = EMS_ACCESS.getInstance();
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
		return (ActionForm) form;
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
		try{
			List listDATA06 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("01");
			bform.setItem_value("請假月明細表");
			listDATA06.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("02");
			bform.setItem_value("請假月統計表");
			listDATA06.add(bform);
			request.setAttribute("listDATA06", listDATA06);
		}catch(Exception e) {
			System.out.println(e);
		}
	}

	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
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
			
			//取得系統年月
			String day[]=(tools.ymdTostring(tools.getSysDate())).split("/");
			
			
			//設定日期區間初始值
			Form.setDATA05(day[0]+"/"+day[1]);
			
			

			
			
//			//設定日期區間初始值
//			if (!"".equals(Form.getDATA13())){//要修正
//				Form.setDATA13(day[0]+"/"+day[1]);
//			//Form.setDATA14(tools.ymdTostring(tools.getSysDate()));
//			}else{
//
//			}
			
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//當前登入者權限
			String HR 		= tools.getSysParam(conn, authbean.getCompId(),"HR_IDENTITY");		//預設人資		100
			String SYSTEM 	= tools.getSysParam(conn, authbean.getCompId(),"SYSTEM_IDENTITY");	//系統管理者	000
			
			if(ems_access.checkEmsRoleEmp(conn, authbean, HR)){
				//人事經辦
			}else if(ems_access.checkEmsRoleEmp(conn, authbean, SYSTEM)){
				//系統管理者
			}else{
				//一般員工
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
				Form.setDATA12( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("USER_CODE") );  //申請人員工工號
				Form.setDATA15( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_ID") );
				Form.setDATA21( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_NAME") );  //申請人姓名
				Form.setDATA11( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID") );  //申請人部們組織代號
				Form.setDATA14( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DEPT_ID") );
				Form.setDATA20( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DESC") );  //申請人部們名稱
			}
			
			hr_tools.close();
			
			//判斷身分別
			ems_access.checkIdentity(request, getLoginUser(request), conn, "", "", "");
			
			this.generateSelectBox(conn, Form, request);
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
	
	/**
	 * 列印查詢結果 (全部)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	
	
	public ActionForward print_select(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF)form;
	
		try{
			if("01".equals(Form.getDATA07())){
				//列印月明細表
				return this.print_VACATION_LIST(mapping, Form, request, response);
			}else if("02".equals(Form.getDATA07())){
				//列印統計表
				return this.print_VC(mapping, Form, request, response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return mapping.findForward("success");
	}
	public ActionForward print_VACATION_LIST(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		request.setAttribute("Form1Datas",Form);
		Connection conn = null;
		ArrayList list = new ArrayList();
		ActionErrors lc_errors = new ActionErrors();
		HR_TOOLS hr_tools = new HR_TOOLS();
		int count=0;
		int number=1;
		Map empInfMap= new HashMap();
		String DEPT_ID = Form.getDATA04();
		String EMPLOYEE_ID = Form.getDATA03();
		String Month = Form.getDATA05();
		
		String type =Form.getDATA07();
		
		String day[]=(tools.ymdTostring(tools.getSysDate())).split("/");

	
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
    	
    	BA_Vaildate ve = BA_Vaildate.getInstance();
    	
    	
    	
    	if (!lc_errors.isEmpty()) {
			saveErrors(request, lc_errors);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			this.generateSelectBox(conn, Form, request);
			
			return mapping.findForward("success");
		}
    	Calendar cal = null;
        String nowtime = "";
        
        try{
        	//取得當前登入者資訊
    		AuthorizedBean authbean = getLoginUser(request);
			
    		
    		Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
    		
			String path = "";
			
			//一天工時
			work_hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
			
			path = getServlet().getServletContext().getRealPath("");
			
			//準備存放當月請假清單的MAP
			List get_Ask_For_leave= new ArrayList();
			
			
			this.generateSelectBox(conn, Form, request);

			EX020501R1F ef = new EX020501R1F(conn,path,getLoginUser(request).getEmployeeID(),request);
			//撈當月請假清單                                                                                                                                                                                                                                                                                                        
			get_Ask_For_leave = this.get_Ask_For_leave(conn,authbean.getCompId(),DEPT_ID,EMPLOYEE_ID,Month,get_Ask_For_leave);

			Iterator it = get_Ask_For_leave.iterator(); 
			Map tempMap = null;
			while (it.hasNext()) {
				
				tempMap = (Map) it.next();
				
				ef.setHeadValue(0, 1, "A", Form.getDATA13(),false, "");//年份(西元)
				ef.setHeadValue(0,1,"A",(String)compMap.get("COMP_NAME_C"),false,""); //列印公司抬頭
				ef.setHeadValue(1, 2, "A", Month.split("/")[0]+"年"+ Month.split("/")[1]+"月請假月報表",false, "");//報表名稱
				ef.setHeadValue(2,3, "A", "日期區間："+ Month.split("/")[1]+"月",false, "");//日期區間
				ef.setHeadValue(3,3,"K",(ef.getNowPageNum()+1)+"",false,"");//頁次
				ef.setHeadValue(4,4,"K", day[0] + "年" + day[1] + "月 " + day[2] + "日",false,"");//列印日期
				
				ef.next();//下一筆
				
				ef.setDetail(1, "A", tempMap.get("部門代號2")+""+tempMap.get("部門名稱"),false); //部門代號+部門名稱
				ef.setDetail(1, "B", (String)tempMap.get("職務名稱"),false); //職稱
				ef.setDetail(1, "C", (String)tempMap.get("姓名"),false); //員工姓名
				ef.setDetail(1, "D", (String)tempMap.get("員工工號"),false); //員工代號
				ef.setDetail(1, "E", (String)tempMap.get("假別"),false); //假別
				ef.setDetail(1, "F", (String)tempMap.get("起日"),false); //請假日期(起)
				ef.setDetail(1, "G", (String)tempMap.get("起時"),false); //請假時間(起)
				ef.setDetail(1, "H", (String)tempMap.get("迄日"),false); //請假日期(迄)
				ef.setDetail(1, "I", (String)tempMap.get("迄時"),false); //請假時間(迄)
				ef.setDetail(1, "J", tempMap.get("請假天數").toString().split("/")[0]+"天"+
									 tempMap.get("請假天數").toString().split("/")[1]+"小時",false); //時數
				ef.setDetail(1, "K", (String)tempMap.get("事由"),false); //備註
				
				count++;
					
			}
			
			String name = "" ;
//			System.out.println(type);
			
			 if(type.equals("01"))
					
					name=Month.split("/")[0]+"年"+ Month.split("/")[1]+"月請假明細表.xls";
				else{
					name=Month.split("/")[0]+"年"+ Month.split("/")[1]+"月請假統計表.xls";
				}
			System.out.println(name);
			 String FileName="";
			
			if(count>0){
//				String cur_date = tool.ymdTostring(tools.getSysDate());
				//存入檔案
				FileName=ef.write();
				request.setAttribute("MSG","列印完成!!");
				//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
				request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
//				System.out.println("DOWNLOADFILE:"+request.getAttribute("DOWNLOADFILE"));
				return init(mapping, form, request, response);
			
			}else{
				request.setAttribute("MSG","沒有資料可列印!!");
				return init(mapping, form, request, response);
			}
        }catch (Exception E) {
			E.printStackTrace();
//			this.getSelectOption(request);
		    request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			request.setAttribute("MSG","列印失敗!!");
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
	private List get_Ask_For_leave(Connection conn, String compId, String DEPT_ID, String EMPLOYEE_ID,String Month, List get_Ask_For_leave) {
		// TODO Auto-generated method stub
		
		String sql= "" +
					"    		SELECT a.EHF020200T0_03 AS '申請人',    " +
					" 		    e.EHF010100T0_02 AS '員工工號',        " +
					" 		    e.EHF010100T0_05 AS '姓名',              " +
					" 		    a.EHF020200T0_04 AS '部門代號1',     " +
					"           c.EHF000200T0_02 AS '部門代號2',       " +
					"           c.EHF000200T0_03 AS '部門名稱',          " +
					"           DATE_FORMAT(a.EHF020200T0_09, '%Y/%m/%d')AS '起日' ,           " +
					"           a.EHF020200T0_11 AS '起時',                  " +
					"		    DATE_FORMAT(a.EHF020200T0_10, '%Y/%m/%d')AS '迄日',             " +
					"		    a.EHF020200T0_12 AS '迄時'," +
					"		    a.EHF020200T0_14 as '假別代號',        " +
					"		    f.EHF020100T0_04  as '假別' ,                 " +
					" 		    a.EHF020200T0_15 as '事由' ,                  " +
					"		    b.EHF010100T6_06 as '職務系統代碼',                  " +
					"		    d.EHF000300T0_03 AS '職務名稱',                           " +
					"           a.EHF020200T0_13 As '請假天數'                             " +
					"	   		FROM EHF020200T0 a     " +
					" 			LEFT JOIN EHF010100T6 b ON b.EHF010100T6_01=a.EHF020200T0_03 " +
					"			 LEFT JOIN EHF000200T0 c ON c.EHF000200T0_01=a.EHF020200T0_04 " +
					"			 LEFT JOIN EHF000300T0 d ON d.EHF000300T0_01=b.EHF010100T6_06 " +
					" 			LEFT JOIN EHF010100T0 e ON e.EHF010100T0_01=a.EHF020200T0_03 " +
					" 			LEFT JOIN EHF020100T0 f ON f.EHF020100T0_03=a.EHF020200T0_14 " +
					"  		   WHERE 1 = 1" ;
		
		sql += " 		AND (DATE_FORMAT(a.EHF020200T0_09, '%Y/%m/%d') LIKE '"+Month+"%' OR DATE_FORMAT(a.EHF020200T0_10, '%Y/%m/%d') LIKE '"+Month+"%')";

		if(!"".equals(EMPLOYEE_ID)){
			sql+="		AND EHF020200T0_03 = '"+EMPLOYEE_ID+"'" ;
		}
		if(!"".equals(DEPT_ID)){
			sql+="		AND EHF020200T0_04 = '"+DEPT_ID+"'" ;
		}
		sql+=" 			AND a.EHF020200T0_18 = '"+compId+"'" +
			 " 					ORDER BY a.EHF020200T0_04,a.EHF020200T0_03,EHF020200T0_01 DESC";
//		System.out.println(sql);
		try {
			BaseFunction base_tools = new BaseFunction();
			PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
			get_Ask_For_leave = base_tools.resultSetToList(pstmt.executeQuery(sql));
			base_tools.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return get_Ask_For_leave;
	}
	public ActionForward print_VC(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
		request.setAttribute("Form1Datas",Form);
		Connection conn = null;
		ArrayList list = new ArrayList();
		ActionErrors lc_errors = new ActionErrors();
		HR_TOOLS hr_tools = new HR_TOOLS();
		int count=0;
		int number=1;
		Map empInfMap= new HashMap();
		String DEPT_ID = Form.getDATA04();
		String EMPLOYEE_ID = Form.getDATA03();
		String Month = Form.getDATA05();
		
		String type=Form.getDATA07();
		
		String day[]=(tools.ymdTostring(tools.getSysDate())).split("/");


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
		
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		
		
		if (!lc_errors.isEmpty()) {
			saveErrors(request, lc_errors);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("save_status", "error");
			request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
			this.generateSelectBox(conn, Form, request);
			
			return mapping.findForward("success");
		}
		Calendar cal = null;
	    String nowtime = "";
	    
	    try{
	    	//取得當前登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			
			//取得公司名稱
			Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
			//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			hr_tools.close();
			
			String path = "";
			
			//一天工時
			work_hours = Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "WORK_HOURS"));
		
			//total =Float.parseFloat(tools.getSysParam(conn, authbean.getCompId(), "合計"));
			path = getServlet().getServletContext().getRealPath("");
			
			//準備存放當月請假清單的MAP
			List get_Ask_For_vc= new ArrayList();
			
			
			this.generateSelectBox(conn, Form, request);

			EX020501R2F ef = new EX020501R2F(conn,path,getLoginUser(request).getEmployeeID(),request);

			//撈當月請假清單                                                                                                                                                                                                                                                                                                        
			get_Ask_For_vc = this.get_Ask_For_vc(conn,authbean.getCompId(),DEPT_ID,EMPLOYEE_ID,Month,get_Ask_For_vc);
//			System.out.println("get_Ask_For_leave:"+get_Ask_For_leave);
			Iterator it = get_Ask_For_vc.iterator(); 
			Map tempMap = null;
			while (it.hasNext()) {
				
				tempMap = (Map) it.next();
				
				ef.setHeadValue(0,1,"A",(String)compMap.get("COMP_NAME_C"),false,""); //列印公司抬頭
				ef.setHeadValue(1,2, "A", Month.split("/")[0]+"年"+ Month.split("/")[1]+"月請假月報表",false, "");//報表名稱
				ef.setHeadValue(2,3, "F", "日期區間："+ Month.split("/")[1]+"月",false, "");//日期區間
				float total=0; //合計
				
				ef.next();//下一筆
				
				ef.setDetail(1, "B", String.valueOf(tempMap.get("病假")),true); 
				ef.setDetail(1, "C", String.valueOf(tempMap.get("事假")),true); 
				ef.setDetail(1, "D", String.valueOf(tempMap.get("特休")),true);  
				ef.setDetail(1, "E", String.valueOf(tempMap.get("公假")),true); 
				ef.setDetail(1, "F", String.valueOf(tempMap.get("產假")),true); 
				ef.setDetail(1, "G", String.valueOf(tempMap.get("婚假")),true); 
				ef.setDetail(1, "H", String.valueOf(tempMap.get("喪假")),true); 
				ef.setDetail(1, "I", String.valueOf(tempMap.get("輪休")),true); 
				ef.setDetail(1, "J", String.valueOf(tempMap.get("生理假")),true); 
//				ef.setDetail(1, "k", String.valueOf(tempMap.get("合計")),true);
				
//				total = Float.parseFloat(String.valueOf(get_Ask_For_vc.get("合計")).equals("0.0")?"0" 
//						:String.valueOf(get_Ask_For_vc.get("toatalVa_hours")))
				ef.setDetail(1, "A", (String)tempMap.get("員工工號"),true);
				ef.setDetail(1,"A", (String) (empMap.containsKey(tempMap.get("員工工號"))==true?
						(String) ((Map)empMap.get(tempMap.get("員工工號"))).get("EMPLOYEE_NAME"):tempMap.get("員工工號")),false);//姓名
				
				total= total +Float.parseFloat(String.valueOf(tempMap.get("病假")))+
							  Float.parseFloat(String.valueOf(tempMap.get("事假")))+
							  Float.parseFloat(String.valueOf(tempMap.get("特休")))+
							  Float.parseFloat(String.valueOf(tempMap.get("公假")))+
						      Float.parseFloat(String.valueOf(tempMap.get("產假")))+		
						      Float.parseFloat(String.valueOf(tempMap.get("婚假")))+
						      Float.parseFloat(String.valueOf(tempMap.get("喪假")))+
						      Float.parseFloat(String.valueOf(tempMap.get("輪休")))+
						      Float.parseFloat(String.valueOf(tempMap.get("生理假")));
			  ef.setDetail(1, "K", String.valueOf(total),true);
				
				System.out.println(total);
				
				count++;
				
				

			}
			String name = "" ;
			System.out.println(type);
			
			 if(type.equals("01"))
					
					name=Month.split("/")[0]+"年"+ Month.split("/")[1]+"月請假明細表.xls";
				else{
					name=Month.split("/")[0]+"年"+ Month.split("/")[1]+"月請假統計表.xls";
				}
			
			 String FileName="";
			
			 if(count>0){
//					String cur_date = tool.ymdTostring(tools.getSysDate());
					//存入檔案
					FileName=ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
//					System.out.println("DOWNLOADFILE:"+request.getAttribute("DOWNLOADFILE"));
					return init(mapping, form, request, response);
				
				}else{
					request.setAttribute("MSG","沒有資料可列印!!");
					return init(mapping, form, request, response);
				}
	    }catch (Exception E) {
			E.printStackTrace();
//			this.getSelectOption(request);
		    request.setAttribute("Form1Datas",Form);
			request.setAttribute("Form2Datas",list);
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
	private List get_Ask_For_vc(Connection conn, String compId, String DEPT_ID, String EMPLOYEE_ID,String Month, List get_Ask_For_vc) {
		// TODO Auto-generated method stub
		
		String sql= "" +
		"select  EHF020200T0_03 as 員工工號"+
		",SUM(if(EHF020100T0_04='病假',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))  as 病假"+
		",SUM(if(EHF020100T0_04='事假',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))  as 事假"+
		",SUM(if(EHF020100T0_04='特休',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))  as 特休"+
		",SUM(if(EHF020100T0_04='公假',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))  as 公假"+
		",SUM(if(EHF020100T0_04='產假',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))  as 產假"+
		",SUM(if(EHF020100T0_04='婚假',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))  as 婚假"+
		",SUM(if(EHF020100T0_04='喪假',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))  as 喪假"+
		",SUM(if(EHF020100T0_04='輪休',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))  as 輪休"+
		",SUM(if(EHF020100T0_04='生理假',8*(SUBSTRING(EHF020200T0_13, 1, POSITION('/' IN EHF020200T0_13)-1)),0))as 生理假"+
		" FROM   EHF020200T0 c      "+
		"LEFT JOIN EHF020100T0 b ON b.EHF020100T0_03 = c.EHF020200T0_14 AND b.EHF020100T0_08 = c.EHF020200T0_18 "+
		"  		   WHERE 1 = 1" ;
		
		//float total=(Float.parseFloat(Form.get()) ) + Float.parseFloat(Form.get());
		
		sql += " 		AND (DATE_FORMAT(EHF020200T0_09, '%Y/%m/%d') LIKE '"+Month+"%' OR DATE_FORMAT(EHF020200T0_10, '%Y/%m/%d') LIKE '"+Month+"%')";

		if(!"".equals(EMPLOYEE_ID)){
			sql+="		AND EHF020200T0_03 = '"+EMPLOYEE_ID+"'" ;//員工工號
		}
		if(!"".equals(DEPT_ID)){
			sql+="		AND EHF020200T0_04 = '"+DEPT_ID+"'" ; //部門代號
		}
			sql+=" 		AND EHF020200T0_18 = '"+compId+"'" +
		 " 	GROUP BY EHF020200T0_03  " ;

		System.out.println(sql);
		try {
			BaseFunction base_tools = new BaseFunction();
			PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
			get_Ask_For_vc = base_tools.resultSetToList(pstmt.executeQuery(sql));
			base_tools.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return get_Ask_For_vc;
	}

}
