package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF330500M0F;
import com.spon.ems.popo.forms.EX330500R0F;
import com.spon.ems.popo.models.EHF330500;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;

/**
 * 今日餐點
 * (Action)
 */
public class EHF330500M0A extends QueryAction {
	
	

		@Override
		protected ActionForm executeInitData(Connection conn, ActionForm form,
				Map paramMap) {
			// TODO Auto-generated method stub
			EHF330500M0F Form = new EHF330500M0F();
			return (ActionForm) Form;
		}

		@Override
		protected Map executeQueryData(Connection conn, ActionForm form,
				Map paramMap) {
			// TODO Auto-generated method stub
			Map return_map = new HashMap();
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
			

			EHF330500M0F Form = (EHF330500M0F) form;		
			BA_TOOLS tools = BA_TOOLS.getInstance();
			HR_TOOLS hr_tools = new HR_TOOLS();
			Connection conn = null;
			String comp_id = getLoginUser(request).getCompId();
		
			String sql="",sql2="",sql3="",sql4="" ;
			//建立EHF331000元件
			EHF330500 ehf330500 = new EHF330500(comp_id);
			
			if (Form.getEHF320100T0_02()!=null && !Form.getEHF320100T0_02().equals("") ){
				}
			else{
				request.setAttribute("MSG","請選擇要列印的日期!!");
				return init(mapping,form,request,response);
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

				EX330500R0F ef = new EX330500R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request);
				
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    		ResultSet rs = null;
	    		Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    		ResultSet rs2 = null;
	    		Statement stmt3 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    		ResultSet rs3 = null;
	    		Statement stmt4 = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    		ResultSet rs4 = null;
				int DataCount =0;
				Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getUserId()); //取得公司名稱
				hr_tools.close();
				//String ABC[]={"A","B","C"};
				
				sql = "" +
					"SELECT EHF320300T0_02,EHF320300T1_03,EHF320100T0_03,EHF320300T1_04 "+
					" FROM EHF320300T1 "+
					" left join EHF320300T0 on EHF320300T0_01 = EHF320300T1_01 "+
					" left join EHF320100T0 on EHF320100T0_01 = EHF320300T1_05 "+
					" where 1=1 "+
					"	and EHF320300T1_03='"+'A'+"' "+
					"	and EHF320300T0_02 = '"+Form.getEHF320100T0_02()+"' ";
				rs = stmt.executeQuery(sql);
				rs.last();
			
				sql2 = "" +
					"SELECT EHF320300T0_02,EHF320300T1_03,EHF320100T0_03,EHF320300T1_04 "+
					" FROM EHF320300T1 "+
					" left join EHF320300T0 on EHF320300T0_01 = EHF320300T1_01 "+
					" left join EHF320100T0 on EHF320100T0_01 = EHF320300T1_05 "+
					" where 1=1 "+
					"	and EHF320300T1_03='"+'B'+"' "+
					"	and EHF320300T0_02 = '"+Form.getEHF320100T0_02()+"' ";
				rs2 = stmt2.executeQuery(sql2);
				rs2.last();
				
				sql3 = "" +
					"SELECT EHF320300T0_02,EHF320300T1_03,EHF320100T0_03,EHF320300T1_04 "+
					" FROM EHF320300T1 "+
					" left join EHF320300T0 on EHF320300T0_01 = EHF320300T1_01 "+
					" left join EHF320100T0 on EHF320100T0_01 = EHF320300T1_05 "+
					" where 1=1 "+
					"	and EHF320300T1_03='"+'C'+"' "+
					"	and EHF320300T0_02 = '"+Form.getEHF320100T0_02()+"' ";
				rs3 = stmt3.executeQuery(sql3);
				rs3.last();
				
				sql4 = "" +
					"SELECT EHF300400T0_02,EHF300400T0_04 " +
					"FROM EHF300400T0 "+
					" where 1= 1 "+
					" and EHF300400T0_02 = "+dayForWeek(Form.getEHF320100T0_02())+" ";
				rs4 = stmt4.executeQuery(sql4);
				rs4.last();
				
				int count = rs3.getRow();
				rs.beforeFirst();
				rs2.beforeFirst();
				rs3.beforeFirst();
				rs4.beforeFirst();
				
				try{//列印

					ef.next();
					
					while(rs.next()) {
						if(rs.getString("EHF320300T1_04").equals("01"))
							ef.setValue(3, "B", rs.getString("EHF320100T0_03"),false);//早主食
						if(rs.getString("EHF320300T1_04").equals("02"))
							ef.setValue(4, "B", rs.getString("EHF320100T0_03"),false);
						if(rs.getString("EHF320300T1_04").equals("05"))
							ef.setValue(6, "B", rs.getString("EHF320100T0_03"),false);		
						DataCount++; //判斷是否有資料
					}
					rs.close();
					
					rs4.next();
					ef.setValue(5, "B", rs4.getString("EHF300400T0_04"),false);	
					rs4.close();
					
					while(rs2.next()){
						if(rs2.getString("EHF320300T1_04").equals("03"))
							ef.setValue(3, "D", rs2.getString("EHF320100T0_03"),false);//主食
						if(rs2.getString("EHF320300T1_04").equals("04"))
							ef.setValue(4, "D", rs2.getString("EHF320100T0_03"),false);
						if(rs2.getString("EHF320300T1_04").equals("02"))
							ef.setValue(5, "D", rs2.getString("EHF320100T0_03"),false);
						if(rs2.getString("EHF320300T1_04").equals("01"))
							ef.setValue(6, "D", rs2.getString("EHF320100T0_03"),false);
						if(rs2.getString("EHF320300T1_04").equals("05"))
							ef.setValue(7, "D", rs2.getString("EHF320100T0_03"),false);
						if(rs2.getString("EHF320300T1_04").equals("06"))
							ef.setValue(8, "D", rs2.getString("EHF320100T0_03"),false);
					}
					rs2.close();
					while(rs3.next()){
						if(rs3.getString("EHF320300T1_04").equals("03"))
							ef.setValue(3, "F", rs3.getString("EHF320100T0_03"),false);//主食
						if(rs3.getString("EHF320300T1_04").equals("04"))
							ef.setValue(4, "F", rs3.getString("EHF320100T0_03"),false);
						if(rs3.getString("EHF320300T1_04").equals("02"))
							ef.setValue(5, "F", rs3.getString("EHF320100T0_03"),false);
						if(rs3.getString("EHF320300T1_04").equals("01"))
							ef.setValue(6, "F", rs3.getString("EHF320100T0_03"),false);
						if(rs3.getString("EHF320300T1_04").equals("05"))
							ef.setValue(7, "F", rs3.getString("EHF320100T0_03"),false);
						if(rs3.getString("EHF320300T1_04").equals("06"))
							ef.setValue(8, "F", rs3.getString("EHF320100T0_03"),false);
					}
					rs3.close();
					//亂數
					 int[] random = new int[10];
					  String[] data = {
							  "養肝飲 = 桂圓紅棗能改善新血循環，安定揪神狀況，紓解壓力，降低產後感染。",
							  "黑木耳 = 豐富的植物蛋白，鐵質甚多有抗膽固醇抗血小板凝集於血管壁之功能。",
							  "豬腰 = 含有蛋白質、脂肪、碳水化合物、鈣、磷、鐵和維生素，有健腎補腰、腎理氣之功效。",
							  "麻油雞 = 麻油雞主要是讓調理進補作為健胃劑與發汗劑，內服具有驅寒殺菌提神。",
							  "枸杞 = 味甘，心脾。歸肝、腎、肺經。功效滋補肝腎、明目、潤肺。",
							  "黃耆茶 = 可促進全身血液循環、治療糖尿病消渴症、冠狀動脈硬化及心肌梗塞等疾病。",
							  "牛蒡茶 = 具有利尿、通便、防治痔瘡，排毒、清血養顏，降血壓、膽固醇，健脾胃，補腎壯陽。",
							  "黑豆茶 = 有治腎病、利水下氣、活血、解毒的功能。",
							  "甘草 = 甘草袪痰止咳，並可隨証作適宜配伍而廣泛應用如風寒咳嗽。",
							  "黑棗 = 用於補血和作為調理藥物，對貧血、血小板減少、肝炎、乏力、失眠有一定療效。"

					  };
					  for(int i=0;i<9;i++){

					   random[i]=(int) Math.floor(Math.random()*9+1);   

					   for(int j=0;j<i;j++){//檢查有無重覆

					    if(random[i]==random[j]){i--; break;}//如重覆，重抽一次     
					   					  }
					   }

					  for(int x=0;x<4;x++){
						  ef.setValue(11+x, "A", data[random[x]],false);

					  
						 
				//	  System.out.println(data[random[x]]);
					  
					//return;

					  }
					
					
					
					ef.BeforeChangePage();
			//		ef.EndDocument();
					stmt.close();
					stmt2.close();
					stmt3.close();
					stmt4.close();
					//傳入前端需要的檔名
					String name ="";		
					
					String FileName="";
					
					if(DataCount>0){//表示有資料
						//String cur_date = tool.ymdTostring(tools.getSysDate());
						//存入檔案
						name="今日餐點.xls";
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
			return this.queryForm(mapping, Form, request, response);
			
		}

		@Override
		protected boolean executeDelData(Connection conn, String[] key,
				Map paramMap) {
			// TODO Auto-generated method stub
			return false;
		}
		//養生茶
		public  static  int  dayForWeek(String pTime) throws  Exception {  
			 SimpleDateFormat format = new  SimpleDateFormat("yyyy/MM/dd" );  
			 Calendar c = Calendar.getInstance();  
			 c.setTime(format.parse(pTime));  
			 int  dayForWeek = 0 ;  
			 if (c.get(Calendar.DAY_OF_WEEK) == 1 ){  
			  dayForWeek = 7 ;  
			 }else {  
			  dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1 ;  
			 }  
			 return  dayForWeek;  
		
			 
			 
		}
		//亂數
		
//		public static void main (String[] args){ 	 
//			// 
//			  int[] random = new int[4];
//			  String[] data = {
//					  "yuhi01",
//					  "iuhi02",
//					  "hih03",
//					  "hihyi04"
//			  };
//			  for(int i=0;i<3;i++){
//
//			   random[i]=(int) Math.floor(Math.random()*3+1);   
//
//			   for(int j=0;j<i;j++){//檢查有無重覆
//
//			    if(random[i]==random[j]){i--; break;}//有重覆重抽一次     
//			   					  }
//			   }
//
//			  for(int x=0;x<4;x++){
//
//			  System.out.println(data[random[x]]);
//			  
//			//return;
//
//			  }
//
//
//		
//		
//		}
}

