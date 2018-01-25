package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.struts.action.ActionMessage;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.NewDispatchAction;
import com.spon.ems.popo.forms.EHF331200M0F;
import com.spon.ems.popo.forms.EX331200R0F;
import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;

/**
 * 贈品明細報表
 * (Action)
 */
public class EHF331200M0A extends NewDispatchAction{
	
	private BA_TOOLS tools ;
	private EMS_ACCESS ems_access;
	
	
	public EHF331200M0A(){
		tools = BA_TOOLS.getInstance();
		ems_access = EMS_ACCESS.getInstance();
	}

	
	public ActionForward init(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) 
	{
		
	
		EHF331200M0F Form = new EHF331200M0F();
	
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
	
		//建立資料庫連線
			if (conn == null) {
				try {
					conn = tools.getConnection("SPOS");
				} catch (SQLException e2) {
					e2.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	
			try{	
		
				//取得當前登入者資訊
				AuthorizedBean authbean = getLoginUser(request);
				//取得系統年月
				String day[]=(tools.ymdTostring(tools.getSysDate())).split("/");

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
	
	
public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		BA_TOOLS ems_tools = BA_TOOLS.getInstance();
		
		Connection conn = null;		
		EHF331200M0F Form = (EHF331200M0F) form;
		ActionErrors lc_errors = new ActionErrors();
		
		String day[]=(tools.ymdTostring(tools.getSysDate())).split("/");
		String compId = getLoginUser(request).getCompId();	//公司代號
		
		String sql = "";
		
		//檢查不可為空白
		BA_Vaildate ve = BA_Vaildate.getInstance();
		
		try {
			
			if ((Form.getEHF310100T0_03_01()!=null && !Form.getEHF310100T0_03_01().equals(""))&&//櫃號(起、迄都有填)
					Form.getEHF310100T0_03_02()!=null && !Form.getEHF310100T0_03_02().equals("")){
						if (Integer.parseInt(Form.getEHF310100T0_03_01())>Integer.parseInt(Form.getEHF310100T0_03_02())){
							//	errors.add("EHF310100T0_03_01", new ActionMessage(""));
							//	errors.add("EHF310100T0_03_02", new ActionMessage(""));
								request.setAttribute("MSG", "櫃號請依小到大選取");
								return init(mapping, Form, request, response);

						}}

			
			String Begin = null;
			String End = null;
			for (int i=0 ; i<2 ; i++){
				if (i == 1){
					Begin = Form.getEHF310500T0_05_01().toString();
					End = Form.getEHF310500T0_05_02().toString();
				}else{
					Begin = Form.getEHF310500T0_04_01().toString();
					End = Form.getEHF310500T0_04_02().toString();
				}
					if (End != null && !End.equals("") && !"".equals(Begin)) {
						if (Begin != null && !Begin.equals("") && !"".equals(End)) {
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
							Date Begindate = sdf.parse(Begin);
							Date Enddate = sdf.parse(End);
							if (Begindate.after(Enddate)){
								if (i == 1){
									request.setAttribute("MSG", "開單開始日期不能大於開單結束日期");
									return init(mapping, Form, request, response);
								}else{
									request.setAttribute("MSG", "領取開始日期不能大於領取結束日期");
									return init(mapping, Form, request, response);
								}
								
							}
						}
					}
			}
			} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
    	//建立資料庫連線
    	if (conn == null) {
    		try {
    			conn = tools.getConnection("SPOS"); //emsdb_v2_haccp
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
			
			sql = "" +" SELECT  " +
			"        EHF310100T0_01 AS '系統編號', " +
			"        EHF310100T0_03 AS '櫃號', " +
			"        EHF310100T0_04 AS '產婦姓名', " +
			"        EHF310500T0_05 AS '開單日期', " +
			"        EHF310500T0_04 AS '領取日期', " +
			"        EHF310100T0_09 AS '訂餐天數', " +
			"        EHF310500T0_06 AS '贈品類別'/*需到類別代碼反查*/, " +
			"        EHF310500T0_08 AS '贈品備註' " +
			"   FROM EHF310100T0 a " +
			"        LEFT JOIN EHF310500T0 b ON a.EHF310100T0_01 = b.EHF310500T0_01 " +
			"        WHERE 1=1  " +
			"        AND EHF310100T0_34 = '" + compId + "' /*產婦資料公司代碼*/  " +
			"        AND EHF310500T0_09 = '" + compId + "' /*贈品資訊公司代碼*/  " ;
			int Cheak = 0;
			if(!"".equals(Form.getEHF310100T0_01_01()) && Form.getEHF310100T0_01_01() != null ){
				sql +=
					"        AND EHF310100T0_01 = '" + Form.getEHF310100T0_01_01() + "' /*產婦資料系統編號*/  " ;
				Cheak++;
			}else{
//				sql +=
//					"        AND EHF310500T0_03 = '1' /*判斷是否有贈品*/  " ;
				}
			if(!"".equals(Form.getEHF310100T0_03_01()) && Form.getEHF310100T0_03_01() != null ){
				sql +=
					"        AND EHF310100T0_03 >= " + Form.getEHF310100T0_03_01() + " /*櫃號1*/  " ;
				Cheak++;
					}
			if(!"".equals(Form.getEHF310100T0_03_02()) && Form.getEHF310100T0_03_02() != null ){
				sql +=
					"        AND EHF310100T0_03 <= " + Form.getEHF310100T0_03_02() + " /*櫃號2*/  " ;
				Cheak++;
					}
			
			if(!"".equals(Form.getEHF310500T0_04_01()) && Form.getEHF310500T0_04_01() != null ){
				sql +=
					"        AND EHF310500T0_04 >= '" + Form.getEHF310500T0_04_01() + "' /*領取日期1*/  " ;
				Cheak++;
			}
			if(!"".equals(Form.getEHF310500T0_04_02()) && Form.getEHF310500T0_04_02() != null ){
				sql +=
					"        AND EHF310500T0_04 <= '" + Form.getEHF310500T0_04_02() + "' /*領取日期2*/  " ;
				Cheak++;
					}
			if(!"".equals(Form.getEHF310500T0_05_01()) && Form.getEHF310500T0_05_01() != null ){
				sql +=
					"        AND EHF310500T0_05 >= '" + Form.getEHF310500T0_05_01() + "' /*開單日期1*/  " ;
				Cheak++;
					}
			if(!"".equals(Form.getEHF310500T0_05_02()) && Form.getEHF310500T0_05_02() != null ){
				sql +=
					"        AND EHF310500T0_05 <= '" + Form.getEHF310500T0_05_02() + "' /*開單日期2*/  " ;
				Cheak++;
					}
			if (Cheak < 1){
				sql +=
					"        AND EHF310500T0_04 >= '" + ems_tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd") + "' /*領取日期1*/  " ;
			}
			sql+=
			" ORDER BY a.EHF310100T0_01 ASC ";

			//System.out.println(sql);//顯示sql
			
			rs = stmt.executeQuery(sql);
			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			

			
			
			//			產生Excel元件
			AuthorizedBean authbean = getLoginUser(request); 
			try{
				getLoginUser(request).getCompId();
				//取得公司名稱
				Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getUserId());
				hr_tools.close();
				
				EX331200R0F ef = new EX331200R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getEmployeeID(), request, count);
				
				while(rs.next()) {		//列印資料
					if(DataCount>0){
						request.setAttribute("MSG","列印完成!!");
						
							ef.setHeadValue(0,1,"A","贈品明細報表",false,"");
							ef.setHeadValue(1,2,"F","列印日期：" + day[0] + "年" + day[1] + "月" + day[2] + "日",false,"");
						
					}else{
						saveErrors(request, lc_errors);
						request.setAttribute("MSG","沒有資料可列印!!");
					}
					
					
					
					if (this.getITEM_VALUE(rs.getString("贈品類別"), "GIFT", compId)==null) {
						if(!"".equals(Form.getEHF310100T0_01_01()) && Form.getEHF310100T0_01_01() != null ){
						}else
						{
							continue;
						}
					}
					
					ef.next();
//						ef.setDetail(1,"A", DataCount+1+"",false);  //項次
						ef.setDetail(1,"A", rs.getString("櫃號"),false);
						ef.setDetail(1,"B", rs.getString("產婦姓名"),false);
						ef.setDetail(1,"C", rs.getString("開單日期")==null?"":rs.getString("開單日期"),false);
						ef.setDetail(1,"D", rs.getString("領取日期")==null?"":rs.getString("領取日期"),false);
						ef.setDetail(1,"E", rs.getString("訂餐天數")==null?"":rs.getString("訂餐天數"),false);
						ef.setDetail(1,"F", this.getITEM_VALUE(rs.getString("贈品類別"), "GIFT", compId)==null?"無贈品":this.getITEM_VALUE(rs.getString("贈品類別"), "GIFT", compId),false);
						ef.setDetail(1,"G", rs.getString("贈品備註")==null?"":rs.getString("贈品備註"),false);
					DataCount++;
			}
//				ef.BeforeChangePage();
//				ef.EndDocument();
				rs.close() ;
				stmt.close();
				
				String name ="贈品明細報表.xls";
					name ="贈品明細報表"+ems_tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";
					
				String FileName="";
				
				if(DataCount>0){
					//String cur_date = tool.ymdTostring(tools.getSysDate());
					//存入檔案
					FileName=ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
					return init(mapping, Form, request, response);
				}
				else{
					request.setAttribute("MSG","沒有資料可列印!!");
					return init(mapping, Form, request, response);
				}
				
			}catch(Exception E){
				E.printStackTrace();
				request.setAttribute("MSG","列印失敗!!");
			}finally{
//				ef.write();
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

public List getEHF310100T0_03(Connection conn, boolean emptyOption, String comp_id ){
	
	List optionlist = new ArrayList();
		
	try{
			
		String sql = "" +
		" SELECT EHF310100T0_03 FROM EHF310100T0" +
		" WHERE 1=1 " +
		" AND EHF310100T0_34 = '"+comp_id+"' " +
		" Group BY EHF310100T0_03 " +
		" ORDER BY (EHF310100T0_03+0) ASC ";		

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		
		if(emptyOption){
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("");
				bform.setItem_value("－請選擇－");
				optionlist.add(bform);
			}
			
		
			
		while(rs.next()){
				
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_id(rs.getString("EHF310100T0_03"));
			bform.setItem_value(rs.getString("EHF310100T0_03"));
			if(!"".equals(rs.getString("EHF310100T0_03")) && rs.getString("EHF310100T0_03") != null ){
			optionlist.add(bform);
			}
				
		}
		
		if (optionlist.size() < 2){
			optionlist.clear();
			BA_ALLKINDForm bform = new BA_ALLKINDForm();
			bform.setItem_value("無櫃號資料");
			optionlist.add(bform);
		}
			
		rs.close();
		stmt.close();
			
	}catch(Exception e){
		e.printStackTrace();
	}
		
	return optionlist;
}

/**
* ITEM_ID轉為ITEM_VALUE
*/
public String getITEM_VALUE(String item_ID, String classKey, String comp_id ){
	
	
	String ItemValue = null;
	BA_TOOLS tools = BA_TOOLS.getInstance();
	Connection conn = null;
	try {
		conn = tools.getConnection("SPOS");
	} catch (SQLException e2) {
		e2.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();}
		
	try{
			
		String sql = "" +
		" SELECT EMS_CategoryT1_04 AS ITEM_ID, EMS_CategoryT1_05 AS ITEM_VALUE " +
		" FROM EMS_CategoryT0 " +
		" LEFT JOIN EMS_CategoryT1 ON EMS_CategoryT1_01 = EMS_CategoryT0_01 AND EMS_CategoryT1_09 = EMS_CategoryT0_06 " +
		" WHERE 1=1 " +
		" AND EMS_CategoryT0_01 = '"+classKey+"' " +
		" AND EMS_CategoryT1_04 = '"+item_ID+"' " +
		" AND EMS_CategoryT0_06 = '"+comp_id+"' " +
		" ORDER BY EMS_CategoryT1_07 ";		
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
			
		if(rs.next()){
			ItemValue = rs.getString("ITEM_VALUE");
		}
			
		rs.close();
		stmt.close();
			
	}catch(Exception e){
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
		
	return ItemValue;
}

	protected void generateSelectBox(Connection conn, ActionForm form,
		HttpServletRequest request) {
	//產生 radio選單 --> 是,否
		try{
			String comp_id = (String) request.getAttribute("compid");
			//("欄位名稱", tools.getOptions(conn, true, "類別代碼", "公司編號" ))
			request.setAttribute("listEHF310100T0_03_01", this.getEHF310100T0_03(conn, true, comp_id ));
			request.setAttribute("listEHF310100T0_03_02", this.getEHF310100T0_03(conn, true, comp_id ));
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	

	

}
