package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.spon.utils.struts.form.BA_ALLKINDForm;
import com.spon.ems.abs.actions.QueryAction;

//import com.spon.ems.popo.forms.EHF310100M0F;
import com.spon.ems.popo.forms.EHF330700M0F;
import com.spon.ems.popo.models.EHF330700;
import com.spon.ems.popo.forms.EX330700R0F;

import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 客戶送餐開始標籤與用餐結束標籤
 * (Action)
 */
public class EHF330700M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		EHF330700M0F Form = new EHF330700M0F();
		
		List list = new ArrayList();
		
		Form.setEHF330700_C(list);
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		request.getSession().setAttribute("Pageid",null);
		boolean chk_flag = false;
		EHF330700M0F Form = (EHF330700M0F) form;
		Map return_map = new HashMap();
		EHF330700M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		
		try{
			//建立空清單
			List<EHF330700M0F> querylist = new ArrayList<EHF330700M0F>();
			
			//建立EHF330700元件
			EHF330700 ehf330700 = new EHF330700(comp_id);

			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			queryCondMap.put("COMP_ID", comp_id);  //公司代碼
			
			//使用元件進行處理
			List ehf330700_queryList = ehf330700.queryData(queryCondMap);
			
			Iterator it = ehf330700_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				tempMap = (Map) it.next();
				bean = new EHF330700M0F();
				bean.setEHF310100T0_01_C((String)tempMap.get("EHF310100T0_01"));	//櫃號
				bean.setEHF310100T0_03_C((String)tempMap.get("EHF310100T0_03"));	//櫃號
				bean.setEHF310100T0_04_C((String)tempMap.get("EHF310100T0_04"));	//產婦姓名
				bean.setEHF310100T0_10_C((String)tempMap.get("EHF310100T0_10"));	//送餐日期
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF330700_C(querylist);
			
			//關閉EHF330700元件
			ehf330700.close();
//			
//			Form.setEHF330700T0_03("");	//櫃號
//			Form.setEHF330700T0_21("");	//行動電話(產婦)
//			Form.setEHF330700T0_04("");	//產婦姓名
//			Form.setEHF330700T0_31("");	//地址
//			Form.setEHF330700T0_08_B("");	//訂餐日期起
//			Form.setEHF330700T0_08_E("");	//訂餐日期迄
						
			//Return資料
			return_map.put("CHK_FLAG", chk_flag);
			return_map.put("FORM", Form);
			
		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		return return_map;
	}

	@Override
	protected void generateSelectBox(Connection conn, ActionForm form,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		
	}
	
	public ActionForward printTag(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
		
		String[] formKey = {};
		String arrid[] = request.getParameterValues("checkId");	//所選取的key(系統編號)
		String Checked ="☑";
		String NoChecked ="☐";
		
    	if ((arrid==null?false:arrid.length > 0)){
    		formKey = arrid ;
		}else{
    		request.setAttribute("MSG","請選取要列印的資料!!");
    		return queryForm(mapping,form,request,response);
    	}
    	
		BA_TOOLS tools = BA_TOOLS.getInstance();
		HR_TOOLS hr_tools = new HR_TOOLS();
		Connection conn = null;		
		EHF330700M0F Form = (EHF330700M0F) form;
		ActionErrors lc_errors = new ActionErrors();
		String sql = "";
		
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
    		Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = null;
			ResultSet rs2 = null;
			int DataCount =0;
			
			sql = "" +		//用餐開始SQL
			" SELECT EHF310100T0_03 AS '開始櫃號', EHF310100T1_02 AS '開始日期', " +  //EHF310100T0_01,
			" min(EHF310100T1_03) AS '開始ABC' " +
			" FROM EHF310100T0 " +
			" LEFT JOIN EHF310100T1 " +
			" ON EHF310100T1_01 = EHF310100T0_01 AND EHF310100T1_06 = EHF310100T0_34 " +
			" WHERE (EHF310100T0_01, EHF310100T1_02) " +
			" IN (SELECT EHF310100T1_01, min(EHF310100T1_02) FROM EHF310100T1 GROUP BY EHF310100T1_01) " +
			" AND EHF310100T0_01 IN ( ";
			for(int i=0; i<formKey.length; i++){
				if(i==0){
				sql +=" '" + formKey[i] + "'" ;
				}
				else{
				sql +=" " +	
					", '" + formKey[i] + "'" ;
				}
			}
			sql +=
			" )GROUP BY EHF310100T1_01 " ;
			
			rs = stmt.executeQuery(sql);
			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			
			sql = "" +		//用餐結束SQL
			" SELECT EHF310100T0_03 AS '結束櫃號', EHF310100T1_02 AS '結束日期', " +  //EHF310100T0_01,
			" max(EHF310100T1_03) AS '結束ABC' " +
			" FROM EHF310100T0 " +
			" LEFT JOIN EHF310100T1 " +
			" ON EHF310100T1_01 = EHF310100T0_01 AND EHF310100T1_06 = EHF310100T0_34 " +
			" WHERE (EHF310100T0_01, EHF310100T1_02) " +
			" IN (SELECT EHF310100T1_01, max(EHF310100T1_02) FROM EHF310100T1 GROUP BY EHF310100T1_01) " +
			" AND EHF310100T0_01 IN ( ";
			for(int i=0; i<formKey.length; i++){
				if(i==0){
				sql +=" '" + formKey[i] + "'" ;
				}
				else{
				sql +=" " +	
					", '" + formKey[i] + "'" ;
				}
			}
			sql +=
			" )GROUP BY EHF310100T1_01 " ;
			
			rs2 = stmt2.executeQuery(sql);
			rs2.last();
	//		int count2 = rs2.getRow();
			rs2.beforeFirst();			
			
			try{//列印
				Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getUserId()); //取得公司名稱
				hr_tools.close();
				
				EX330700R0F ef = new EX330700R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getEmployeeID(), request, count);
				
				
				while(rs.next()) {		////(開始的)下筆資料
					rs2.next();	//(結束的)下筆資料
//					System.out.println(rs.getString("開始ABC"));
//					System.out.println(rs2.getString("結束ABC"));					
					if(DataCount==0){
					ef.next();//Excell設定下筆資料要存的位子
					}
					
					if(DataCount%2==0){	//----------印左邊----------
						ef.setDetail(1,"B",rs.getString("開始櫃號"),false);
						ef.setDetail(2,"B",rs.getString("開始日期"),false);
						if(rs.getString("開始ABC").equals("A"))	{
							ef.setDetail(2,"C",Checked,false);
							ef.setDetail(2,"E",NoChecked,false);
							ef.setDetail(2,"G",NoChecked,false);						
						}
						else{
							if(rs.getString("開始ABC").equals("B")){
								ef.setDetail(2,"C",NoChecked,false);
								ef.setDetail(2,"E",Checked,false);
								ef.setDetail(2,"G",NoChecked,false);
							}
							else{
								if(rs.getString("開始ABC").equals("C")){
									ef.setDetail(2,"C",NoChecked,false);
									ef.setDetail(2,"E",NoChecked,false);
									ef.setDetail(2,"G",Checked,false);
								}
								else{
									System.out.println("列印資料非A,B,C");
								}
							}
						}
						
						ef.setDetail(4,"B",rs2.getString("結束櫃號"),false);
						ef.setDetail(5,"B",rs2.getString("結束日期"),false);
						if(rs2.getString("結束ABC").equals("A"))	{
							ef.setDetail(5,"C",Checked,false);
							ef.setDetail(5,"E",NoChecked,false);
							ef.setDetail(5,"G",NoChecked,false);						
						}
						else{
							if(rs2.getString("結束ABC").equals("B")){
								ef.setDetail(5,"C",NoChecked,false);
								ef.setDetail(5,"E",Checked,false);
								ef.setDetail(5,"G",NoChecked,false);
							}
							else{
								if(rs2.getString("結束ABC").equals("C")){
									ef.setDetail(5,"C",NoChecked,false);
									ef.setDetail(5,"E",NoChecked,false);
									ef.setDetail(5,"G",Checked,false);
								}
								else{
									System.out.println("列印資料非A,B,C");
								}
							}
						}
					}
					else{  //----------印右邊----------
						ef.setDetail(1,"J",rs.getString("開始櫃號"),false);
						ef.setDetail(2,"J",rs.getString("開始日期"),false);
						if(rs.getString("開始ABC").equals("A"))	{
							ef.setDetail(2,"K", Checked,false);
							ef.setDetail(2,"M", NoChecked,false);
							ef.setDetail(2,"O", NoChecked,false);						
						}
						else{
							if(rs.getString("開始ABC").equals("B")){
								ef.setDetail(2,"K", NoChecked,false);
								ef.setDetail(2,"M", Checked,false);
								ef.setDetail(2,"O", NoChecked,false);
							}
							else{
								if(rs.getString("開始ABC").equals("C")){
									ef.setDetail(2,"K", NoChecked,false);
									ef.setDetail(2,"M", NoChecked,false);
									ef.setDetail(2,"O", Checked,false);
								}
								else{
									System.out.println("列印資料非A,B,C");
								}
							}
						}
						
						ef.setDetail(4,"J", rs2.getString("結束櫃號"),false);
						ef.setDetail(5,"J", rs2.getString("結束日期"),false);
						if(rs2.getString("結束ABC").equals("A"))	{
							ef.setDetail(5,"K", Checked,false);
							ef.setDetail(5,"M", NoChecked,false);
							ef.setDetail(5,"O", NoChecked,false);						
						}
						else{
							if(rs2.getString("結束ABC").equals("B")){
								ef.setDetail(5,"K", NoChecked,false);
								ef.setDetail(5,"M", Checked,false);
								ef.setDetail(5,"O", NoChecked,false);
							}
							else{
								if(rs2.getString("結束ABC").equals("C")){
									ef.setDetail(5,"K", NoChecked,false);
									ef.setDetail(5,"M", NoChecked,false);
									ef.setDetail(5,"O", Checked,false);
								}
								else{
									System.out.println("列印資料非A,B,C");
								}
							}
						}
						if(rs.isLast()){	//最後一筆資料
						}
						else{
						ef.next();	
						}
					}
					DataCount++;
				}
				
				ef.BeforeChangePage();
				ef.EndDocument();
				rs.close() ;
				rs2.close() ;
				stmt.close();
				stmt2.close();
				
				//傳入前端需要的檔名
				String name ="";
				
				String FileName="";
				
				if(DataCount>0){//表示有資料
					//String cur_date = tool.ymdTostring(tools.getSysDate());
					//存入檔案
					name="客戶送餐開始標籤與用餐結束標籤.xls";
					FileName=ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
					return queryForm(mapping, Form, request, response);
				}
				else{
					request.setAttribute("MSG","沒有資料可列印!!");
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
		return this.queryForm(mapping, Form, request, response);
		
	}
	
	
}
