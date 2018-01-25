package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.spon.ems.NewDispatchAction;
import com.spon.ems.abs.actions.QueryAction;

import com.spon.ems.popo.forms.EHF330800M0F;

import com.spon.ems.popo.forms.EX330800R0F;

import com.spon.ems.popo.models.EHF330800;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;
import fr.improve.struts.taglib.layout.util.FormUtils;
import java.util.Date;

/**
 * 
 * (Action)醫院用餐資訊
 */
public class EHF330800M0A extends QueryAction {
	
		private EMS_ACCESS ems_access;
		private float work_hours;
		public EHF330800M0A(){
			ems_access = EMS_ACCESS.getInstance();
		}
		@Override
		protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
			return false;
			// TODO Auto-generated method stub
		
		}

		@Override
		protected ActionForm executeInitData(Connection conn, ActionForm form,
				Map paramMap) {
			// TODO Auto-generated method stub
			
			EHF330800M0F Form = new EHF330800M0F();
			
			List list = new ArrayList();
			
			return (ActionForm) Form;
		}

		@Override
		protected Map executeQueryData(Connection conn, ActionForm form,
				Map paramMap) {
			// TODO Auto-generated method stub
			Map return_map = new HashMap();
			String comp_id = (String)paramMap.get(super.COMP_ID);	
			try{
			}catch(Exception e){		
			}
			return return_map;
		}


		@Override
		protected void generateSelectBox(Connection conn, ActionForm form,
				HttpServletRequest request) {
			// TODO Auto-generated method stub

		}
		

		/**
		 * 列印查詢結果 (全部)
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 */

		public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
			
			EHF330800M0F Form = (EHF330800M0F) form;		
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Connection conn = null;
			String comp_id = getLoginUser(request).getCompId();
			ActionErrors lc_errors = new ActionErrors();
			int DataCount = 0;
			String sql;
			
			if (Form.getEHF310100T0_11()!=null && !Form.getEHF310100T0_11().equals("") ){
			}else{
				request.setAttribute("MSG","請選擇要列印的醫院名稱!!");
				return init(mapping,form,request,response);
			}
				try {
					String Begin =Form.getEHF310100T0_07_B().toString();
					String End = Form.getEHF310100T0_07_E().toString();
						if (End != null && !End.equals("") && !"".equals(Begin)) {
							if (Begin != null && !Begin.equals("") && !"".equals(End)) {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
								Date Begindate;
								Begindate = sdf.parse(Begin);
								Date Enddate = sdf.parse(End);
								if (Begindate.after(Enddate)){
									request.setAttribute("ERR_MSG", "送餐結束日期不能大於送餐開始日期");
								}
							}
						}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
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
				
				sql = "" +
				" SELECT EHF310100T0_15, EHF310100T0_04, EHF310100T0_07, EMS_CategoryT1_05 AS EHF310100T0_05_TXT, " +
				" EHF310100T0_13, EHF310100T0_14, EHF310100T0_01 " +
				" FROM EHF310100T0 " +
				" LEFT JOIN ems_categoryt1 " +
				" ON EMS_CategoryT1_01='BirthType' " +
				" AND EMS_CategoryT1_04=EHF310100T0_05 " +
				" WHERE 1=1 ";
				
				if (Form.getEHF310100T0_11()!=null && !Form.getEHF310100T0_11().equals("") ){	//收費者
					sql += " AND EHF310100T0_11='" + Form.getEHF310100T0_11() + "'" ;
				}
				if(Form.getEHF310100T0_07_B()!=null && !Form.getEHF310100T0_07_B().equals("")&& 
						Form.getEHF310100T0_07_E()!=null && !Form.getEHF310100T0_07_E().equals("")){	//送餐日期(起、迄都有填)
					sql += " and EHF310100T0_07 between'"+Form.getEHF310100T0_07_B()+"'and'"+Form.getEHF310100T0_07_E()+"' ";
				}else if(Form.getEHF310100T0_07_B()!=null && !Form.getEHF310100T0_07_B().equals("")){	//只填送餐日期(起)
					sql += " and EHF310100T0_07 >='"+Form.getEHF310100T0_07_B()+"' ";
				}else if(Form.getEHF310100T0_07_E()!=null && !Form.getEHF310100T0_07_E().equals("")){	//只填送餐日期(迄)
					sql += " and EHF310100T0_07 <='"+Form.getEHF310100T0_07_E()+"' ";
				}
				
				sql +=" ORDER BY EHF310100T0_01";
				rs = stmt.executeQuery(sql);
				rs.last();
				int count = rs.getRow();
				rs.beforeFirst();
				
				try{//列印

					EX330800R0F ef = new EX330800R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request);

					while(rs.next()) {
						//需要有住宅用餐期間起訖
						if(rs.getString("EHF310100T0_13") != null && !rs.getString("EHF310100T0_13").equals("") &&
								   rs.getString("EHF310100T0_14") != null && !rs.getString("EHF310100T0_14").equals("")){						
							if(DataCount==0){
								ef.setHeadValue(0, 1, "A", (String)Form.getEHF310100T0_11_TXT(), false, "");
								DataCount++;
							}
							ef.next();
							ef.setDetail(1, "A", String.valueOf(DataCount), false); 		 //系統編號
							ef.setDetail(1, "B", rs.getString("EHF310100T0_15")==null?"": rs.getString("EHF310100T0_15"), false); 	//病房	
							ef.setDetail(1, "C", rs.getString("EHF310100T0_04")==null?"": rs.getString("EHF310100T0_04"), false); 		//姓名
							ef.setDetail(1, "D", rs.getString("EHF310100T0_07")==null?"":DateMMDD(rs.getString("EHF310100T0_07")), false); 		//生產日期
							ef.setDetail(1, "E", rs.getString("EHF310100T0_05_TXT")==null?"":rs.getString("EHF310100T0_05_TXT"), false);	 //產別
							ef.setDetail(1, "F", rs.getString("EHF310100T0_13")==null?"":DateMMDD(rs.getString("EHF310100T0_13")), false); 		 //吃餐啟程(起)
							ef.setDetail(1, "G", "~" ,false);	
							ef.setDetail(1, "H", rs.getString("EHF310100T0_14")==null?"":DateMMDD(rs.getString("EHF310100T0_14")), false); 		//吃餐啟程(訖)
							///////////////////////////////////////////////////////////////////////////////////////////////
							String Key=rs.getString("EHF310100T0_01");
							ef.setDetail(1, "J", NoEatFood(Key,comp_id,conn), false);		//不吃食物
							ef.setDetail(1, "K", SpecialNeeds(Key,comp_id,conn), false);		//特殊需求
							if(EucommiaPowder(Key,comp_id,conn)){
								ef.setDetail(1, "P", "✓", false);		//杜仲粉
							}
							DataCount++;
						}
					}
					rs.close() ;
					stmt.close();
					
					//傳入前端需要的檔名
					String name ="";
					String FileName="";
					if(DataCount>0){//表示有資料
						//String cur_date = tool.ymdTostring(tools.getSysDate());
						//存入檔案
						name="醫院用餐資訊.xls";
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
//					ef.write();
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
		
	
		public String DateMMDD(String InDate){ 	//(ex:2018/01/01 => 1/1)
			String OUTDate="";
			if(InDate!=null && !InDate.equals("")){
				String[] Date = InDate.split("/");
				int year = Integer.parseInt(Date[0]);
				int month = Integer.parseInt(Date[1]);
				int day = Integer.parseInt(Date[2]);
				OUTDate =Date[1]+"/"+Date[2];
			}
			 return OUTDate;
		}
		
		
		public String NoEatFood(String InKey, String compId, Connection Inconn){
			String OutNoEatFood = "";
			try {
				Connection conn =Inconn;
	    		Statement stmt1 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    		Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs1 = null;
				ResultSet rs2 = null;
				String sql1 = "";
				String sql2 = "";
				String FoodData[] = null;
				
				sql1+="" +
				" SELECT EHF310200T1_03 FROM EHF310200T1 " +
				" WHERE 1=1 " +
				" AND EHF310200T1_01='"+InKey+"' " ;
				
				rs1 = stmt1.executeQuery(sql1);
				rs1.beforeFirst();
				while(rs1.next()) {
					FoodData = rs1.getString("EHF310200T1_03").split("/");	//食物代碼分開
					if(FoodData.length==2){//代碼轉換成食物細項名稱(用陣列資料長度判斷)
						sql2="" +
						" SELECT PSFOODT1_04,PSFOODT1_05 FROM foodt1 " +
						" WHERE 1=1 " +
						" AND PSFOODT1_04='"+FoodData[1]+"' " +
						" AND PSFOODT1_09 = '"+compId+"' ";
						
						rs2 = stmt2.executeQuery(sql2);
						rs2.next();
						if(!rs1.isLast()){//!最後一筆資料
							OutNoEatFood+=rs2.getString("PSFOODT1_05")+"、";
							rs2 = null;
						}else{
							OutNoEatFood+=rs2.getString("PSFOODT1_05");
							rs2.close();
						}
					}else if(FoodData.length==1){	//代碼轉換成食物類別名稱(用陣列資料長度判斷)
						sql2="" +
						" SELECT PSFOODT0_01,PSFOODT0_04 FROM foodt0 " +
						" WHERE 1=1 " +
						" AND PSFOODT0_01='"+FoodData[0]+"' "+
						" AND PSFOODT0_08 = '"+compId+"' ";
						
						rs2 = stmt2.executeQuery(sql2);
						rs2.next();
						if(!rs1.isLast()){//!最後一筆資料
							OutNoEatFood+=rs2.getString("PSFOODT0_04")+"、";
							rs2 = null;
						}else{
							OutNoEatFood+=rs2.getString("PSFOODT0_04");
							rs2.close();
						}
					}else{
						System.out.println("代碼資料錯誤");
					}
				//	FoodData=null;
				}
				rs1.close() ;
				//rs2.close();
				stmt1.close();
				stmt2.close();				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return OutNoEatFood;
		}
		
		public String SpecialNeeds(String InKey, String compId, Connection Inconn){
			String OutSpecialNeeds = "";
			Connection conn =Inconn;
    		try {
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = null;
				String Needs[] = null;
				String sql = "";
				sql=""+
				" SELECT 1 as T, EHF310200T5_01,CONCAT(ems_categoryt1_05,'清淡去油') as Needs " +
				" FROM EHF310200T5  " +
				" LEFT JOIN ems_categoryt1  " +
				" ON ems_categoryt1_01='MENU_TYPE' " +
				" AND ems_categoryt1_04=EHF310200T5_03" +
				" WHERE 1=1 " +
				" AND EHF310200T5_01='"+InKey+"' " +
				" UNION " +
				" SELECT 2 as T, EHF310200T4_01,ems_categoryt1_05 as Needs " +
				" FROM EHF310200T4  " +
				" LEFT JOIN ems_categoryt1  " +
				" ON ems_categoryt1_01='ADDITIVES' " +
				" AND ems_categoryt1_04=EHF310200T4_03 " +
				" WHERE 1=1 " +
				" AND EHF310200T4_01='"+InKey+"' " +
				" UNION " +
				" SELECT 3 as T, EHF310200T3_01,CONCAT(ems_categoryt1_05,'加量') as Needs " +
				" FROM EHF310200T3  " +
				" LEFT JOIN ems_categoryt1  " +
				" ON ems_categoryt1_01='MENU_TYPE' " +
				" AND ems_categoryt1_04=EHF310200T3_03 " +
				" WHERE 1=1 " +
				" AND EHF310200T3_01='"+InKey+"' " +
				" ORDER BY EHF310200T5_01,T ";

				
				rs = stmt.executeQuery(sql);
				rs.beforeFirst();
				
				while(rs.next()) {
					Needs = rs.getString("Needs").split("/");
					if(!rs.isLast()){//!最後一筆資料
						OutSpecialNeeds+=Needs[0]+"、";
					}else{
						OutSpecialNeeds+=Needs[0];
					}
				}	
				rs.close() ;
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return OutSpecialNeeds;
		}

		
		public boolean EucommiaPowder(String InKey, String compId, Connection Inconn){
			boolean OUTEucommiaPowder=false;
			boolean EucommiaPowder=false;
			String OutSpecialNeeds = "";
			Connection conn =Inconn;
    		try {
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs = null;
				String sql = "";
				
				sql="" +
				" SELECT EHF310100T2_06, EHF310100T2_03 AS DrinkData_B, EHF310100T2_04 AS DrinkData_E, " +
				" EHF310100T0_04, EHF310100T0_13 AS HospitalEatData_B, EHF310100T0_14 AS HospitalEatData_E " +
				" FROM EHF310100T2 " +
				" LEFT JOIN EHF310100T0 " +
				" ON EHF310100T0_01=EHF310100T2_01 " +
				" AND EHF310100T0_34 =EHF310100T2_07 " +
				" WHERE 1=1" +
				" AND EHF310100T2_06='07' " +	//'07'是杜仲粉
				" AND EHF310100T2_01='"+InKey+"' " +
				" AND EHF310100T2_07 ='"+compId+"' ";
				rs = stmt.executeQuery(sql);
				rs.beforeFirst();
				
				while(rs.next()) {//日期都要有才判斷
					if(rs.getString("DrinkData_B") != null && !rs.getString("DrinkData_B").equals("") &&
					   rs.getString("DrinkData_E") != null && !rs.getString("DrinkData_E").equals("") &&
					   rs.getString("HospitalEatData_B") != null && !rs.getString("HospitalEatData_B").equals("") &&
					   rs.getString("HospitalEatData_E") != null && !rs.getString("HospitalEatData_E").equals("")){
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
						Date DrinkData_B = sdf.parse(rs.getString("DrinkData_B"));
						Date DrinkData_E = sdf.parse(rs.getString("DrinkData_E"));
						Date HospitalEatData_B = sdf.parse(rs.getString("HospitalEatData_B"));
						Date HospitalEatData_E = sdf.parse(rs.getString("HospitalEatData_E"));
							if( (DrinkData_B.before(HospitalEatData_E) &&  DrinkData_E.after(HospitalEatData_B))||
								(DrinkData_B.equals(HospitalEatData_E) || DrinkData_E.equals(HospitalEatData_B)) ){
								EucommiaPowder=true;
							}else{
								EucommiaPowder=false;
							}
//							System.out.println(DrinkData_B.before(HospitalEatData_E) &&  DrinkData_E.after(HospitalEatData_B));
//							System.out.println((DrinkData_B.before(HospitalEatData_B)) && DrinkData_E.after(HospitalEatData_E));
//							System.out.println("---------------------");
							OUTEucommiaPowder=EucommiaPowder||OUTEucommiaPowder;//一筆以上資料處理用
					}
				}
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return OUTEucommiaPowder;
		}
}
