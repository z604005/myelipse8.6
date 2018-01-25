package com.spon.ems.popo.actions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF331000M0F;
import com.spon.ems.popo.forms.EX331000R0F;
import com.spon.ems.popo.models.EHF331000;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;

/**
 * 當日已收明細表
 * (Action)
 */
public class EHF331000M0A extends QueryAction {
	
		@Override
		protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
			return false;
			// TODO Auto-generated method stub
			
		}

		@Override
		protected ActionForm executeInitData(Connection conn, ActionForm form,
				Map paramMap) {
			// TODO Auto-generated method stub
			EHF331000M0F Form = new EHF331000M0F();
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
			

			EHF331000M0F Form = (EHF331000M0F) form;		
			BA_TOOLS tools = BA_TOOLS.getInstance();
			HR_TOOLS hr_tools = new HR_TOOLS();
			Connection conn = null;
			String comp_id = getLoginUser(request).getCompId();
			String sql="" ;
			//建立EHF331000元件
			EHF331000 ehf331000 = new EHF331000(comp_id);
			
			if (Form.getEHF310400T1_10()!=null && !Form.getEHF310400T1_10().equals("") ){
				}
			else{
				request.setAttribute("MSG","請選擇要列印的收費日期!!");
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
	    		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	    		ResultSet rs = null;
				int DataCount =0;
				boolean selectEHF310400T1_05=false;
				
				sql = "" +
				" SELECT EHF310100T0_02, EHF310100T0_03, EHF310100T0_04, " +
				" a.ems_categoryt1_05 AS EHF310400T1_06_TXT, b.ems_categoryt1_05 AS EHF310400T1_07_TXT, " +
				" EHF310400T1_10, EHF310400T1_11, EHF310400T1_12, EHF310400T1_05 " +
				" FROM EHF310400T1 " +
				" LEFT JOIN EHF310100T0 " +
				" ON EHF310400T1_01 = EHF310100T0_01 AND EHF310400T1_15 = EHF310100T0_34 " +
				" LEFT JOIN ems_categoryt1 a " +
				" ON a.ems_categoryt1_01 = 'PayType' AND EHF310400T1_07 = a.ems_categoryt1_04 AND EHF310400T1_15 = a.ems_categoryt1_09 " +
				" LEFT JOIN ems_categoryt1 b " +
				" ON b.ems_categoryt1_01 = 'PayCategor' AND EHF310400T1_07 = b.ems_categoryt1_04 AND EHF310400T1_15 = b.ems_categoryt1_09 " +
				" WHERE EHF310400T1_14= false " ;
	//			" AND EHF310400T1_13= true " ;
				if (Form.getEHF310400T1_10()!=null && !Form.getEHF310400T1_10().equals("") ){  	//實際收款日期
					sql += " AND EHF310400T1_10='" + Form.getEHF310400T1_10() + "'" ;
				}
				if (Form.getEHF310400T1_05()!=null && !Form.getEHF310400T1_05().equals("") ){	//收費者
					sql += " AND EHF310400T1_05='" + Form.getEHF310400T1_05() + "'" ;
					selectEHF310400T1_05=true;
				}
				sql +=" ORDER BY EHF310100T0_02";
				rs = stmt.executeQuery(sql);
				rs.last();
				int count = rs.getRow();
				rs.beforeFirst();
				
				try{//列印
					Map compMap = hr_tools.getCompNameMap(conn,getLoginUser(request).getUserId()); //取得公司名稱
					hr_tools.close();
					EX331000R0F ef = new EX331000R0F(conn, getServlet().getServletContext().getRealPath(""), getLoginUser(request).getEmployeeID(), request, count);

					while(rs.next()) {	
						if(DataCount==0){
							//日期格式轉換
							String[] Date = Form.getEHF310400T1_10().split("/");
							 int year = Integer.parseInt(Date[0])-1911;
							 int month = Integer.parseInt(Date[1]);
							 int day = Integer.parseInt(Date[2]);
							ef.setHeadValue(0,1,"A",String.valueOf(year)+"年"+String.valueOf(month)+"月"+String.valueOf(day)+"日",false,"");
//							System.out.println( String.valueOf(year)+"年"+String.valueOf(month)+"月"+String.valueOf(day)+"日");
							
							if(selectEHF310400T1_05)	//是否有選收費者
								ef.setHeadValue(1,1,"G", rs.getString("EHF310400T1_05"),false,"");
							selectEHF310400T1_05=false;
						}						
						ef.next();
						ef.setDetail(1,"A", rs.getString("EHF310100T0_02")==null?"":rs.getString("EHF310100T0_02"),false); 		 //檔案編號
						ef.setDetail(1,"B", rs.getString("EHF310100T0_03")==null?"": rs.getString("EHF310100T0_03"),false); 		 //櫃號
						ef.setDetail(1,"C", rs.getString("EHF310100T0_04")==null?"": rs.getString("EHF310100T0_04"),false); 		 //姓名
						ef.setDetail(1,"D", rs.getString("EHF310400T1_07_TXT")==null?"":rs.getString("EHF310400T1_07_TXT"),false); 		 //類別
						ef.setDetail(1,"E", rs.getString("EHF310400T1_06_TXT")==null?"":rs.getString("EHF310400T1_06_TXT"),false);	 //收費方式
						ef.setDetail(1,"F", rs.getInt("EHF310400T1_11"),true,"##,###"); 		 //金額
						ef.setDetail(1,"G", rs.getString("EHF310400T1_05")==null?"":rs.getString("EHF310400T1_05"),false); 		 //收費者
						ef.setDetail(1,"H", rs.getString("EHF310400T1_12")==null?"":rs.getString("EHF310400T1_12"),false); 		 //備註
						DataCount++;
					}
					
					ef.BeforeChangePage();
					ef.EndDocument();
					rs.close() ;
					stmt.close();
					
					//傳入前端需要的檔名
					String name ="";
					
					String FileName="";
					
					if(DataCount>0){//表示有資料
						//String cur_date = tool.ymdTostring(tools.getSysDate());
						//存入檔案
						name="當日已收明細表.xls";
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

}
