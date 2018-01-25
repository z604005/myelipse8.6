package com.spon.ems.popo.actions;

import java.io.File;
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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.popo.forms.EHF330600M0F;
import com.spon.ems.popo.forms.EX330600R0F;
import com.spon.ems.popo.models.EHF330600;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.EMS_ACCESS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

/**
 * 客戶名牌標籤
 * (Action)
 */
public class EHF330600M0A extends QueryAction {
	
		private EMS_ACCESS ems_access;
		private float work_hours;
		public EHF330600M0A(){
			ems_access = EMS_ACCESS.getInstance();
		}
		protected boolean chk_Picture(String picture_Path){
			boolean chk_flag = false;
			File file = new File(picture_Path);
			if (file.exists()) {
				chk_flag=true;
			}
			return chk_flag;
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
			
			EHF330600M0F Form = new EHF330600M0F();
			
			List list = new ArrayList();
			
			Form.setEHF330600_C(list);
			
			return (ActionForm) Form;
		}

		@Override
		protected Map executeQueryData(Connection conn, ActionForm form,
				Map paramMap) {
			// TODO Auto-generated method stub
			
			HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
			request.getSession().setAttribute("Pageid",null);
			boolean chk_flag = false;
			EHF330600M0F Form = (EHF330600M0F) form;
			Map return_map = new HashMap();
			EHF330600M0F bean = null;
			String comp_id = (String)paramMap.get(super.COMP_ID);	
			try{
				//建立空清單
				List<EHF330600M0F> querylist = new ArrayList<EHF330600M0F>();
				
				//建立EHF330600元件
				EHF330600 ehf330600 = new EHF330600(comp_id);
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				//建立EHF010109M0 查詢資料
				//建立查詢 Condition Map
				Map queryCondMap = new HashMap();
				Map empMap = null;
				Map depMap = null;
				
				//處理條件設定
				//ActionForm convert to HashMap
				queryCondMap = BeanUtils.describe(Form);
				queryCondMap.put("COMP_ID", comp_id);  //公司代碼
				
				empMap = hr_tools.getEmpNameMap(comp_id);
				depMap = hr_tools.getDepNameMap(comp_id);
				
				//使用元件進行處理
				List ehf330600_queryList =ehf330600.queryData(queryCondMap);

				Iterator it = ehf330600_queryList.iterator();
				Map tempMap = null;
				
				if(it.hasNext()){
					chk_flag = true;
				}
				
					while(it.hasNext()){
						
						tempMap = (Map) it.next();
						
						bean = new EHF330600M0F();
					
		
						
						bean.setEHF330600T0_01((String)tempMap.get("EHF310100T0_01"));	//客戶需求單編號
				
						bean.setEHF330600T0_03((String)tempMap.get("EHF310100T0_03"));	//櫃號
						bean.setEHF330600T0_04((String)tempMap.get("EHF310100T0_04"));	//產婦姓名
						bean.setEHF330600T0_21((String)tempMap.get("EHF310100T0_21"));	//行動電話
						bean.setEHF330600T0_31((String)tempMap.get("EHF310100T0_31"));	

				
					
					querylist.add(bean);
				}
				
				//設定querylist
				Form.setEHF330600_C(querylist);
				
				//關閉EHF010301元件
				ehf330600.close();
				hr_tools.close();
				
				//Return資料
				return_map.put("CHK_FLAG", chk_flag);
				return_map.put("FORM", form);
				
		
				
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
		/**
		 * 列印查詢結果 (全部)
		 * 
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 */

		public ActionForward print(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response){
			
			EHF330600M0F Form = (EHF330600M0F) form;		
			BA_TOOLS tools = BA_TOOLS.getInstance();
			Connection conn = null;
			String comp_id = getLoginUser(request).getCompId();
			ArrayList list = new ArrayList();
			String[] arrid = request.getParameterValues("checkId_boss");
			String[] formKey = {};
			
			
	    	if ((arrid==null?false:arrid.length > 0)){
	    		formKey = arrid ;
			}else{
	    		request.setAttribute("MSG","請選取要查看的資料!!");
	    		return queryForm(mapping,form,request,response);
	    		
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
	    	

	    	
	    	try{
//	    		if(this.chk_Picture(getServlet().getServletContext().getRealPath("/")+"\\config\\"+Form.getEHF330600T0__pic())){
//	    			
//				}else{
//					
					Form.setEHF330600T0__pic("pic\\餐牌圖檔.png");
//				}
	    		java.io.File imageFile = new java.io.File(getServlet().getServletContext().getRealPath("") +"\\config\\"+ Form.getEHF330600T0__pic());
				
	    		//產生Excel元件
				EX330600R0F ef = new EX330600R0F(conn,getServlet().getServletContext().getRealPath(""),getLoginUser(request).getEmployeeID(),request,imageFile);
	    		
//				Statement stmt = (Statement) conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
				ResultSet rs= null;
				int DataCount = 0;
				HR_TOOLS hr_tools = new HR_TOOLS();
				
				
				Map empMap = hr_tools.getEmpNameMap(comp_id);//員工Map
				Map depMap = hr_tools.getDepNameMap(comp_id);//部門Map
				hr_tools.close();
				
		

				String sql = "";

				
				
				
					if(arrid!=null){  //有選，印出有條件的
						for(int i=0; i<formKey.length; i++){
//						arrid[0] = Form.getEHF330600T0_01();
						sql = this.getPrintConditionSQL(conn,comp_id,formKey[i],Form.getEHF330600T0_10(),Form.getEHF330600T0_03_B(),Form.getEHF330600T0_03_E());

						//取的列印用的SQL語句
						rs = stmt.executeQuery(sql);
						EHF330600M0F bean = null;
						while(rs.next() ) {
							

							if(DataCount==0){
							
								
							}
							ef.next();
							
						
							ef.setValue(1, "A", rs.getString("EHF310100T0_03")==null?"":rs.getString("EHF310100T0_03"),false);//櫃號
							ef.setValue(1, "B", rs.getString("EHF310100T0_04")==null?"":rs.getString("EHF310100T0_04"),false);//產婦姓名
							ef.setValue(4, "B", rs.getString("EHF300200T0_02")==null?"":rs.getString("EHF300200T0_02"),false); //醫院名稱
							ef.setValue(6, "B", rs.getString("EHF310100T0_21")==null?"":rs.getString("EHF310100T0_21"),false); //電話(行)
							ef.setValue(7, "B", rs.getString("EHF310100T0_22")==null?"":rs.getString("EHF310100T0_22"),false); //電話(住)
							ef.setValue(8, "B", rs.getString("EHF310100T0_31")==null?"":rs.getString("EHF310100T0_31"),false); //住址
//							ef.setValue(10, "A", Form.getEHF330600T0__pic(),false); //圖檔
							ef.setValue(14, "A",rs.getString("EHF310100T0_04")==null?"":rs.getString("EHF310100T0_04"),false); //產婦姓名
							
							
							ef.setValue(1, "D", rs.getString("EHF310100T0_03")==null?"":rs.getString("EHF310100T0_03"),false);//櫃號
							ef.setValue(1, "E", rs.getString("EHF310100T0_04")==null?"":rs.getString("EHF310100T0_04"),false);//產婦姓名
							ef.setValue(4, "E", rs.getString("EHF300200T0_02")==null?"":rs.getString("EHF300200T0_02"),false); //醫院名稱
							ef.setValue(6, "E", rs.getString("EHF310100T0_21")==null?"":rs.getString("EHF310100T0_21"),false); //電話(行)
							ef.setValue(7, "E", rs.getString("EHF310100T0_22")==null?"":rs.getString("EHF310100T0_22"),false); //電話(住)
							ef.setValue(8, "E", rs.getString("EHF310100T0_31")==null?"":rs.getString("EHF310100T0_31"),false); //住址
							
							
							ef.setValue(1, "G", rs.getString("EHF310100T0_03")==null?"":rs.getString("EHF310100T0_03"),false);//櫃號
							ef.setValue(1, "H", rs.getString("EHF310100T0_04")==null?"":rs.getString("EHF310100T0_04"),false);//產婦姓名
							ef.setValue(4, "H", rs.getString("EHF300200T0_02")==null?"":rs.getString("EHF300200T0_02"),false); //醫院名稱
							ef.setValue(6, "H", rs.getString("EHF310100T0_21")==null?"":rs.getString("EHF310100T0_21"),false); //電話(行)
							ef.setValue(7, "H", rs.getString("EHF310100T0_22")==null?"":rs.getString("EHF310100T0_22"),false); //電話(住)
							ef.setValue(8, "H", rs.getString("EHF310100T0_31")==null?"":rs.getString("EHF310100T0_31"),false); //住址
							
							ef.print_Picture();

							list.add(bean);
							DataCount++;
							//EHF010201R0
						}
						
						
					}
				}else{//沒選
					
					request.setAttribute("MSG","請選擇要列印的資料!!");
					 
				}
				
				
				
				stmt.close();
			  	rs.close() ;

				


	    		
	    		//傳入前端需要的檔名
				String name ="";
				String FileName="";
				
				if (DataCount > 0){//表示有資料
					name="客戶名牌標籤.xls";
					//存入檔案
					FileName = ef.write();
					request.setAttribute("MSG","列印完成!!");
					//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
					request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
					
					//顯示前端 Query Collection畫面
					request.setAttribute("collection", "show");
					request.setAttribute("Form1Datas",Form);
					request.setAttribute("Form2Datas",list);
					
					return init(mapping, Form, request, response);
				}else{
					request.setAttribute("MSG","沒有資料可列印!!");
				}
				if(this.chk_Picture(getServlet().getServletContext().getRealPath("/")+"\\config\\"+Form.getEHF330600T0__pic())){
					Form.setEHF330600T0__pic(Form.getEHF330600T0__pic());
				}else{
					Form.setEHF330600T0__pic("pic\\餐牌圖檔.jpg");
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


		
		private String getPrintConditionSQL(Connection conn, String compId,String formKey, String Sendmeal_DATE,String Cabinet_Num1,String Cabinet_Num2) {
			
			String sql= "" +
			"    SELECT a.EHF310100T0_01,a.EHF310100T0_03 AS EHF310100T0_03,    " +
			"    a.EHF310100T0_04 as EHF310100T0_04,        " +
			" 		    a.EHF310100T0_10 as EHF310100T0_10,              " +
			" 		    a.EHF310100T0_31 as EHF310100T0_31,     " +
			"           a.EHF310100T0_21 as EHF310100T0_21,       " +
			"           a.EHF310100T0_22 as EHF310100T0_22,          " +
			"           b.EHF300200T0_02 as EHF300200T0_02           " +
			
			"	   		FROM ehf310100t0 a     " +
			" 			LEFT JOIN ehf300200t0 b ON a.EHF310100T0_34 = b.EHF300200T0_06 AND a.EHF310100T0_11 = b.EHF300200T0_01 " +
			
			"  		   WHERE 1 = 1" ;
			
			sql+=" AND a.EHF310100T0_01 = '"+formKey+"'";

			if(!"".equals(Sendmeal_DATE)){//送餐日期
				sql+="	AND DATE_FORMAT(a.EHF310100T0_10, '%Y/%m/%d') LIKE '"+Sendmeal_DATE+"'" ;
			}
			
//			if(!"".equals(Woman_Name)){//系統編號
//				sql+="	AND a.EHF310100T0_04 = '"+Woman_Name+"'" ;
//			}
//			if(!"".equals(Cabinet_Num1)){//櫃號
//				sql+="	AND a.EHF310100T0_03 BETWEEN '"+Cabinet_Num1+"' AND '"+Cabinet_Num2+"'";
//			}
			
			
			
			sql+=" 		AND a.EHF310100T0_34 = '"+compId+"'" +//公司代碼
				 " 		ORDER BY a.EHF310100T0_03 ASC ";//依櫃號遞增排序
			//System.out.println(sql);
		

			
		
			
			return sql;
		}

}
