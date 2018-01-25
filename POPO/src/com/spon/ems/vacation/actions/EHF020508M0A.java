package com.spon.ems.vacation.actions;

import java.sql.Connection;
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

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.vacation.forms.EHF020401M0F;
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
public class EHF020508M0A extends QueryAction {
	
	private EMS_ACCESS ems_access;
	
	public EHF020508M0A() {
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
		
		EMS_VIEWDATAF Form = new EMS_VIEWDATAF();
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		String comp_id = (String)paramMap.get(super.COMP_ID);
		String employee_id = (String)paramMap.get(super.EMPLOYEE_ID);
		
		try{
			HR_TOOLS hr_tools = new HR_TOOLS();
			
			//當前登入者權限
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
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
				Map empMap = hr_tools.getEmpNameMap(comp_id);
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
		
		try{
			List listDATA05 = new ArrayList();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id("");
			bform.setItem_value("-請選擇-");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("1");
			bform.setItem_value("上午上班");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("2");
			bform.setItem_value("下午下班");
			listDATA05.add(bform);
			/*bform = new BA_ALLKINDForm();
			bform.setItem_id("3");
			bform.setItem_value("外出");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
			bform.setItem_id("4");
			bform.setItem_value("返回");
			listDATA05.add(bform);*/
			bform = new BA_ALLKINDForm();
			bform.setItem_id("5");
			bform.setItem_value("加班上班");
			listDATA05.add(bform);
			bform = new BA_ALLKINDForm();
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
		EMS_VIEWDATAF Form = (EMS_VIEWDATAF) form;
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
			
    		HR_TOOLS hr_tools = new HR_TOOLS();

			//取得公司名稱
			Map compMap = (Map)hr_tools.getCompNameMap(conn,authbean.getCompId());
			//員工Map
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			//部門Map
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());
			//職務
			Map titleMap = hr_tools.getTitleNameMap(authbean.getCompId());
			
			hr_tools.close();
			
			//產生Excel元件
			EX020503R5F ef = new EX020503R5F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request);
			
			//是否顯示加班考勤資料flag
			boolean overtime_data_flag = true;
			
			EMS_FixAttendance fix_att_tools = EMS_FixAttendance.getInstance();
			
			EHF020401M0F Fbean = new EHF020401M0F();
			
			Fbean.setEHF020403T0_01(Form.getDATA12()==null?"":Form.getDATA12());//員工工號
			Fbean.setEHF020403T0_02(Form.getDATA11()==null?"":Form.getDATA11());//部門代號
			EMS_FixAttendance.setStart_date(Form.getDATA30()==null?"":Form.getDATA30());//考勤日期區間
			EMS_FixAttendance.setEnd_date(Form.getDATA31()==null?"":Form.getDATA31());//考勤日期區間
			String card_status = Form.getDATA05();//狀態

			//取得考勤異常資料清單
			list = fix_att_tools.getAttAbnormalList(conn, Fbean, getLoginUser(request), false, overtime_data_flag, "", card_status );
			
			int DataCount = 0;
			Iterator it = list.iterator();
			
			while(it.hasNext()) {
				
				EHF020401M0F bean = (EHF020401M0F) it.next();
				if(DataCount==0){
					ef.setHeadValue(4,4,"H","列印日期:"+tools.ymdTostring(tools.getSysDate())+"",false,"");							
					ef.setHeadValue(5,1,"A",(String)compMap.get("COMP_NAME_C"),false,""); //列印公司抬頭
					
					ef.setHeadValue(6,2,"A","員工刷卡異常記錄表",false,""); //報表名稱
				}
				ef.setHeadValue(7,3,"H","頁        次:"+(ef.getNowPageNum()+1)+"",false,"");
				
				ef.next();
				
				ef.setDetail(1,"A", bean.getEHF020403T0_03(),false);  //部門
				ef.setDetail(1,"B", bean.getEHF020403T0_02(),false);  //員工姓名
				ef.setDetail(1,"C", bean.getEHF020401T0_10(),false);  //日期
				ef.setDetail(1,"D", bean.getEHF020401T0_08_STATUS(),false);  //考勤類別
				ef.setDetail(1,"E", bean.getEHF020401T0_05_DATE(),false);  //打卡時間
				ef.setDetail(1,"F", bean.getEHF020401T0_09_DESC(),false);  //打卡狀態
				ef.setDetail(1,"G", bean.getEHF020401T0_FIX()+","+bean.getEHF020401T0_PS(),false);  //修正內容
				ef.setDetail(1,"H", "",false);  //備註	
				
				DataCount++;
			}
			
			
			//傳入前端需要的檔名
			String name ="";
					
			name="員工刷卡異常記錄表.xls";
				
			String FileName="";
			if(DataCount>0){
				//String cur_date = tool.ymdTostring(tools.getSysDate());
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

}
