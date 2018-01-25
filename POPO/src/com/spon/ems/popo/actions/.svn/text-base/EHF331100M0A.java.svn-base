package com.spon.ems.popo.actions;

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
import com.spon.ems.NewDispatchAction;
import com.spon.ems.abs.actions.QueryAction;

import com.spon.ems.popo.forms.EHF331100M0F;

import com.spon.ems.popo.forms.EX331100R0F;

import com.spon.ems.popo.models.EHF331100;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.BA_Vaildate;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;
import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * 電話電訪報表
 * (Action)
 */
public class EHF331100M0A extends QueryAction {
	
		private EMS_ACCESS ems_access;
		private float work_hours;
		public EHF331100M0A(){
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
			
			EHF331100M0F Form = new EHF331100M0F();
			
			List list = new ArrayList();
			
//			Form.setEHF331100T0_03_E(100);
			Form.setEHF330600_C(list);
			
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
			
			EHF331100M0F Form = (EHF331100M0F) form;		
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Connection conn = null;
			String comp_id = getLoginUser(request).getCompId();
			ActionErrors lc_errors = new ActionErrors();
			int DataCount = 0;
			
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
	    		
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				
				//產生Excel元件
				EX331100R0F ef = new EX331100R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request);
	    		
				List getPrintConditionSQL = new ArrayList();
				
				//先查詢
				getPrintConditionSQL = this.getPrintConditionSQL(conn,comp_id,Form.getEHF331100T0_10(),Form.getEHF331100T0_03_B(),Form.getEHF331100T0_03_E(),getPrintConditionSQL);
				
				Iterator it = getPrintConditionSQL.iterator(); 
				Map tempMap = null;
				while(it.hasNext() ) {
					
					tempMap = (Map) it.next();
					
//					ef.setHeadValue(0,1,"A","電話電訪報表",false,"");//列印公司抬頭
		
					ef.next();//下一筆
					ef.setDetail(1, "A", (String)tempMap.get("EHF310100T0_02"),false);//檔案編號
					ef.setDetail(1, "B", (String)tempMap.get("EHF310100T0_04"),false);//產婦姓名
					ef.setDetail(1, "C", (String)tempMap.get("EHF310100T0_21"),false); //連絡電話(產婦)
					
					/*
					 * 此處為判斷「連絡電話(先生)」條件，如要新增條件，請自行填寫
					 * String [] condition ={"先生"};
					 * 判斷優先順序為連絡電話１、連絡電話２、連絡電話３、連絡電話４
					 * 判斷條件優先順序為
					 * String [] condition ={"條件１","條件２","條件３"};
					 * */
					String [] condition ={"先生"};
					for (int i=0;i<condition.length;i++){
						if(condition[i].equals(tempMap.get("EHF310100T0_23"))){
							ef.setDetail(1, "D", (String)tempMap.get("EHF310100T0_24"),false);
						}else if(condition[i].equals(tempMap.get("EHF310100T0_25"))){
							ef.setDetail(1, "D", (String)tempMap.get("EHF310100T0_26"),false);
						}else if(condition[i].equals(tempMap.get("EHF310100T0_27"))){
							ef.setDetail(1, "D", (String)tempMap.get("EHF310100T0_28"),false);
						}else if(condition[i].equals(tempMap.get("EHF310100T0_29"))){
							ef.setDetail(1, "D", (String)tempMap.get("EHF310100T0_30"),false);
						}
					}
					
					ef.setDetail(1, "E", (String)tempMap.get("EHF310100T0_22"),false); //連絡電話(住家)
							
					//再要列印前，先依據"訂餐天數與開始送餐日期"判斷電訪日期
					//取得訂餐天數與開始送餐日期
					//Map dateInf = ehf331100.getOrderDays(comp_id);//這個Map沒有用到，所以這行應該不用
					int days =(Integer)tempMap.get("EHF310100T0_09"); //訂餐天數
					String date = (String)tempMap.get("EHF310100T0_10");//送餐日期
					
					String EHF310100T0_21 = "";
					String EHF310100T0_24 = "";
					String EHF310100T0_22 = "";
					//電訪次數			
					//判斷是否已填送餐日期資訊
					if(date!=null && !"".equals(date)){
						//開始送餐日期
						Calendar cal_start = tools.covertStringToCalendar(date);
						Calendar cal_first = (Calendar)cal_start.clone();
						Calendar cal_second = (Calendar)cal_start.clone();
						Calendar cal_third = (Calendar)cal_start.clone();

						//計算預計電訪日期
						if(days>=30){
							cal_first.add(Calendar.DAY_OF_MONTH, 4);
							cal_second.add(Calendar.DAY_OF_MONTH, 14);
							cal_third.add(Calendar.DAY_OF_MONTH, 24);
							
							EHF310100T0_21 = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
							EHF310100T0_24 = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");
							EHF310100T0_22 = tools.covertDateToString(cal_third.getTime(), "yyyy/MM/dd");
							
						}else if(days<30 && days>10){
							cal_first.add(Calendar.DAY_OF_MONTH, 3);
							cal_second.add(Calendar.DAY_OF_MONTH, 8);
							cal_third.add(Calendar.DAY_OF_MONTH, 14);
							
							EHF310100T0_21 = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
							EHF310100T0_24 = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");
							EHF310100T0_22 = tools.covertDateToString(cal_third.getTime(), "yyyy/MM/dd");
							
						}else if(days<=10){
							cal_first.add(Calendar.DAY_OF_MONTH, 2);
							cal_second.add(Calendar.DAY_OF_MONTH, 7);
							
							EHF310100T0_21 = tools.covertDateToString(cal_first.getTime(), "yyyy/MM/dd");
							EHF310100T0_24 = tools.covertDateToString(cal_second.getTime(), "yyyy/MM/dd");		
						}
					}	
					ef.setDetail(1, "F", this.getDateConversion(EHF310100T0_21),false); //第一次電訪日期
					ef.setDetail(1, "G", this.getDateConversion(EHF310100T0_24),false); //第二次電訪日期
					ef.setDetail(1, "H", this.getDateConversion(EHF310100T0_22),false); //第三次電訪日期
					ef.setDetail(1, "I", (String)tempMap.get("EHF310100T0_32"),false); //備註1
//					list.add(bean);
					DataCount++;
							//EHF010201R0
				}
				
				stmt.close();
				
				//傳入前端需要的檔名
				String name ="";
				String FileName="";
				
				if (DataCount > 0){//表示有資料
					name="電話電訪報表.xls";
					//存入檔案
					FileName = ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
					
					//顯示前端 Query Collection畫面
//					request.setAttribute("collection", "show");
					request.setAttribute("Form1Datas",Form);
//					request.setAttribute("Form2Datas",list);
					
					return init(mapping, Form, request, response);
				}else{
					request.setAttribute("MSG","沒有資料可列印!!");
					System.out.println("沒有資料可列印!!");
					return init(mapping, Form, request, response);
				}
	    		
				}catch (Exception e) {
					e.printStackTrace();
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


		
		private List getPrintConditionSQL(Connection conn, String compId, String Sendmeal_DATE,String  Cabinet_Num_1,String  Cabinet_Num_2,List getPrintConditionSQL) {
			
			String sql = "" +
			" SELECT * " + 
			"   FROM EHF310100T0 " +
			"   WHERE 1=1 " ;	
			
//			sql+=" AND b.EHF310100T0_01 = '"+formKey+"'";

			if(!"".equals(Sendmeal_DATE)){//送餐日期
				sql+="	AND DATE_FORMAT(EHF310100T0_10, '%Y/%m/%d') = '"+Sendmeal_DATE+"'" ;
			}
			if(!"".equals(Cabinet_Num_1)){	
				sql += " and EHF310100T0_03 >= "+Integer.parseInt(Cabinet_Num_1)+"";
			}
			if(!"".equals(Cabinet_Num_2)){	
				sql += " and EHF310100T0_03 <= "+Integer.parseInt(Cabinet_Num_2)+"";
			}
			sql+=" 		AND EHF310100T0_34 = '"+compId+"'" +//公司代碼
				 " 		ORDER BY EHF310100T0_01 ASC ";//依系統編號遞增排序
			System.out.println(sql);

			try {
				BaseFunction base_tools = new BaseFunction();
				PreparedStatement pstmt = base_tools.getConn().prepareStatement(sql);
				getPrintConditionSQL = base_tools.resultSetToList(pstmt.executeQuery(sql));
				base_tools.close();
				pstmt.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return getPrintConditionSQL;
		}
		
		private String getDateConversion(String Day){
			try{
			String Days [] = Day.split("/");
			Day = Days[0] + "年" + Days[1] + "月" + Days[2] + "日";
			return Day;
			}catch(ArrayIndexOutOfBoundsException e){
				return Day;
			}
		}
		
	

}
