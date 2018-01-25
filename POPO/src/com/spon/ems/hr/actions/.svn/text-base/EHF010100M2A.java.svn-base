package com.spon.ems.hr.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import vtgroup.ems.common.vo.AuthorizedBean;

import java.sql.Statement;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.vacation.forms.EHF020401M0F;
import com.spon.ems.hr.forms.EHF010100M0F;
import com.spon.ems.hr.forms.EX010100R2F;
import com.spon.ems.vacation.forms.EX020503R5F;
import com.spon.ems.vacation.tools.EMS_FixAttendance;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.struts.form.EMS_VIEWDATAF;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;

/**
 * (Action)戴園菌養考勤異常記錄表
 *@author maybe
 *@version 1.0
 *@created 2016/6/6 下午3:31:32
 */
public class EHF010100M2A extends NewDispatchAction {
	
	private EMS_ACCESS ems_access;
	
	private String DEP = "";//部門
	private String emp = "";//員工
	private String emp_class = "";//員工類別
	private String Duty_situation ="";
	public EHF010100M2A() {
		ems_access = EMS_ACCESS.getInstance();
	}
	
	
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{

		EHF010100M0F Form = (EHF010100M0F) form;
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
			
			String day[]=(tools.ymdTostring(tools.getSysDate())).split("/");
			
			
			//設定日期區間初始值
//			Form.setDATA13(day[0]+"/"+day[1]);
			//Form.setDATA14(tools.ymdTostring(tools.getSysDate()));
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//當前登入者權限
			String HR 		= tools.getSysParam(conn, authbean.getCompId(),"HR_IDENTITY");		//預設人資		100
			String SYSTEM 	= tools.getSysParam(conn, authbean.getCompId(),"SYSTEM_IDENTITY");	//系統管理者	000
			String AC 		= tools.getSysParam(conn, authbean.getCompId(),"AC_IDENTITY");		//會計		110
			String BOSS 	= tools.getSysParam(conn, authbean.getCompId(),"BOSS_IDENTITY");	//老闆		111
			
			if(ems_access.checkEmsRoleEmp(conn, authbean, HR) || ems_access.checkEmsRoleEmp(conn, authbean, BOSS)){
				//人事經辦
			}else if(ems_access.checkEmsRoleEmp(conn, authbean, SYSTEM)){
				//系統管理者
			}else{
				//一般員工
				Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
//				Form.setDATA12( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("USER_CODE") );  //申請人員工工號
//				Form.setDATA15( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_ID") );
//				Form.setDATA21( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("EMPLOYEE_NAME") );  //申請人姓名
//				Form.setDATA11( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("DEPT_ID") );  //申請人部們組織代號
//				Form.setDATA14( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DEPT_ID") );
//				Form.setDATA20( (String) ((Map)empMap.get(authbean.getEmployeeID())).get("SHOW_DESC") );  //申請人部們名稱
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



		


	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		try{
			List listDATA05 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("正式員工");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("2");
			bform.setItem_value("臨時員工");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("3");
			bform.setItem_value("外籍勞工");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("4");
			bform.setItem_value("外籍配偶");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("5");
			bform.setItem_value("工讀生");
			listDATA05.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("6");
			bform.setItem_value("加班下班");
			listDATA05.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("7");
			bform.setItem_value("上午下班");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("8");
			bform.setItem_value("下午上班");
			listDATA05.add(bform);*/
			request.setAttribute("listDATA05", listDATA05);
		}catch(Exception e) {
			System.out.println(e);
		}
		
		try{
			List listDATA06 = new ArrayList();
			BA_ALLKINDForm bform1 = new BA_ALLKINDForm();
			bform1.setItem_id("");
			bform1.setItem_value("-請選擇-");
			listDATA06.add(bform1);
			bform1 = new BA_ALLKINDForm();
			bform1.setItem_id("1");
			bform1.setItem_value("到職");
			listDATA06.add(bform1);
			bform1 = new BA_ALLKINDForm();
			bform1.setItem_id("2");
			bform1.setItem_value("離職");
			listDATA06.add(bform1);
			bform1 = new BA_ALLKINDForm();
			bform1.setItem_id("3");
			bform1.setItem_value("復職");
			listDATA06.add(bform1);
			bform1 = new BA_ALLKINDForm();
			bform1.setItem_id("4");
			bform1.setItem_value("留職停薪");
			listDATA06.add(bform1);
			bform1 = new BA_ALLKINDForm();
			/*bform1.setItem_id("5");
			bform1.setItem_value("工讀生");
			listDATA06.add(bform1);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("6");
			bform.setItem_value("加班下班");
			listDATA05.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("7");
			bform.setItem_value("上午下班");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("8");
			bform.setItem_value("下午上班");
			listDATA05.add(bform);*/
			request.setAttribute("listDATA06", listDATA06);
		}catch(Exception e) {
			System.out.println(e);
		}

	}
	
	/**
	 * 列印查詢結果 (全部)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
	ArrayList list = new ArrayList();
	EHF010100M0F Form = (EHF010100M0F) form;
	BA_TOOLS tools = BA_TOOLS.getInstance();
	Connection conn = null;
	
	DEP = Form.getEHF000200T0_03();//部門代號
	emp = Form.getEHF010100T0_06();//員工
	emp_class = Form.getEHF010100T0_07();//員工類別
	Duty_situation = Form.getEHF010100T1_02();//職務狀況
	
	
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
    	
		try {
    		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			int DataCount =0;
			
			String sql = ""+
			" 	  select a.EHF010100T0_01 as '員工工號',   " +
    		" 	  a.EHF010100T0_05 as '員工姓名',   " +
    		" 		 a.EHF010100T0_07 as '員工類別',  "+
    		" 	  b.EHF020403T0_05 as '是否計算考勤',   "+
    		" 	 c.EHF000200T0_03 as '所屬部門',   "+
    		" 	 c.EHF000200T0_01 as '部門代號' ,   "+
    		" 	 d.EHF000400T0_04 as '班別',          "+
    		" 	  e.EHF000300T0_03 as '職務',         "+
    		" 	 a.EHF010100T0_27 as '到職日',       "+
    		"  	 f.EHF010100T1_02 as '職務狀況',     "+
    		"  	 g.EHF020403T1_02 as '感應卡號'     "+
    		" 	 	FROM  ehf010100t0 a    "+
    		"		LEFT OUTER JOIN ehf020403t0 b ON a.EHF010100T0_01 = b.EHF020403T0_01   "+
    		"		LEFT OUTER JOIN ehf000200t0 c ON c.EHF000200T0_01 = b.EHF020403T0_02     "+
    		"		LEFT OUTER JOIN ehf000400T0 d ON b.EHF020403T0_04 = d.EHF000400T0_11     "+
    		"		LEFT OUTER JOIN EHF000300T0 e ON a.HR_CompanySysNo = e.HR_CompanySysNo     "+
    		"		LEFT OUTER JOIN EHF010100T1 f ON a.EHF010100T0_01 = f.EHF010100T1_01         "+
    		"		LEFT OUTER JOIN EHF020403T1 g ON a.EHF010100T0_01 = g.EHF020403T1_01      "+
    		" 		  WHERE 1=1    ";
    		if (DEP != null && !DEP.equals("")) {
				 sql+=
					 "			AND c.EHF000200T0_03 ='" + DEP +   "' ";//選擇部門
				}
			 
			 
			 if (emp != null && !emp.equals("")) {
				 sql+=
				 "     		AND a.EHF010100T0_05 ='"+ emp + "' "  ;//選擇員工
				}
			 
			 if (emp_class != null && !emp_class.equals("")) {
				 sql+=
				 "     		AND a.EHF010100T0_07 ='"+ emp_class + "' "  ;//選擇員工類別
				}
			
			 if (Duty_situation != null && !Duty_situation.equals("")) {
				 sql+=
				 "     		AND f.EHF010100T1_02 ='"+ Duty_situation + "' "  ;//選擇職務狀況
				}
		
    		sql+=
    		
    		"      group by a.EHF010100T0_01       ";
    
//			System.out.println(sql);
			
			rs = stmt.executeQuery(sql);
			rs.last();
			
			
			
			int count = rs.getRow();
			rs.beforeFirst();
			
			
			AuthorizedBean authbean = getLoginUser(request); 
			
			
//			Iterator it = list.iterator();
			HR_TOOLS hr_tools = new HR_TOOLS();
			
    
    		
    		//公司
    		Map compMap = hr_tools.getCompNameMap(conn, authbean.getCompId());
    		
			//職務
			Map titleMap = hr_tools.getTitleNameMap(authbean.getCompId());
			
			//班別名稱
			Map empClassMap = hr_tools.getEmp(authbean.getCompId());
			try{
				
				getLoginUser(request).getCompId();
				//取得公司名稱
	
				hr_tools.close();
//				產生Excel元件
				EX010100R2F ef = new EX010100R2F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getEmployeeID(), request);
				
				while(rs.next()) {		//列印資料
				
					
//					if(DataCount==0){
//				
//						request.setAttribute("MSG","列印完成!!");
//
//					}else{
//						request.setAttribute("ERRMSG","沒有資料可列印!!");
//					}
					
					
//					ef.setDetail(1,"A", DataCount+1+"",false);  //項次
					
			
					ef.setHeadValue(0,1,"A",(String)compMap.get("COMP_NAME_C"),false,""); //列印公司抬頭
					ef.setHeadValue(1,2, "A", "員工資料清單",false, "");//報表名稱
					ef.setHeadValue(1,4,"I",(ef.getNowPageNum()+1)+"",false,"");//頁次
					ef.setHeadValue(1,4,"J","列印日期："+ day[0] + "/" + day[1] + "/ " + day[2] ,false,"");//列印日期
					ef.next();//下一筆
					
					ef.setDetail(1,"B", rs.getString("部門代號")==null?"":rs.getString("部門代號"),false);  //部門
					ef.setDetail(1,"C", rs.getString("員工工號")==null?"":rs.getString("員工工號"),false);  //員工工號
					ef.setDetail(1,"D", rs.getString("員工姓名")==null?"":rs.getString("員工姓名"),false);  //員工姓名
					ef.setDetail(1,"E", rs.getString("員工類別")==null?"":rs.getString("員工類別"),false);  //員工類別
					ef.setDetail(1,"F", rs.getString("感應卡號")==null?"":rs.getString("感應卡號"),false);  //感應卡號
					ef.setDetail(1,"G", rs.getString("班別")==null?"":rs.getString("班別"),false);  //班別
					ef.setDetail(1,"H",rs.getString("職務")==null?"":rs.getString("職務"),false);  //職務名稱
					ef.setDetail(1,"I", rs.getString("到職日")==null?"":rs.getString("到職日"),false);  //到職日
					ef.setDetail(1,"J", rs.getString("職務狀況")==null?"":rs.getString("職務狀況"),false);//職務狀況
					ef.setDetail(1,"K", rs.getString("是否計算考勤")==null?"":rs.getString("是否計算考勤"),false);//是否計算考勤
					
					/*
					 * EHF000200T0_02
					 * EHF010100T0_02
					 * EHF010100T0_05
					 * EHF010100T0_07
					 * EHF020403T1_02
					 * EHF020403T0_05
					 * 
					 * EHF010100T0_27
					 * EHF010100T1_02
					 * EHF000300T0_03//職務名稱
					 * 
					 * EHF010100T8_04_TXT//班別
					 * */
					
					DataCount++;
			}

				rs.close() ;
				stmt.close();
	
				String name ="員工資料清單.xls";

				String FileName="";
				
				if(DataCount>0){
			
					//存入檔案
					FileName=ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
					return init(mapping, Form, request, response);
				}else{
					return init(mapping, Form, request, response);
				}
				
			}catch(Exception E){
				E.printStackTrace();
				request.setAttribute("MSG","列印失敗!!");
			}finally{
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

}
