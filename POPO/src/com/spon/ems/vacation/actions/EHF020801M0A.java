package com.spon.ems.vacation.actions;

import java.sql.Connection;
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
import org.apache.struts.upload.FormFile;

import vtgroup.ems.common.vo.AuthorizedBean;

import com.spon.ems.abs.actions.QueryAction;
import com.spon.ems.fileimport.IMP_EHF020800;
import com.spon.ems.vacation.forms.EHF020801M0F;
import com.spon.ems.vacation.models.EHF020801;
import com.spon.mvc.models.BaseFunction;
import com.spon.utils.util.BA_TOOLS;
import com.spon.utils.util.HR_TOOLS;
import com.sun.org.apache.commons.beanutils.BeanUtils;

import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action> 加班單申請作業(1張1人)
 *@author maybe
 *@version 1.0
 *@created 2016/2/1 上午11:06:32
 */
public class EHF020801M0A extends QueryAction {

	@Override
	protected boolean executeDelData(Connection conn, String[] key, Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		List DEL_List = new ArrayList();//紀錄考勤日期
		
		try{
			EHF020801 ehf020801 = new EHF020801(comp_id);
			
			//Ready Del Condition Map
			Map dataMap = new HashMap();
			dataMap.put("EHF020800T0_01", key[0]);  //加班申請表單編號
			
			//設定使用者資訊
			dataMap.putAll(paramMap);
			
			String sql = "" +
			" SELECT EHF020800T0_01 AS 表單編號," +
			" DATE_FORMAT(EHF020800T0_06, '%Y/%m/%d') AS 加班日期," +
			" EHF020800T1_04 AS 員工工號,EHF020800T0_09" +
			" FROM  EHF020800T0 LEFT JOIN EHF020800T1 ON" +
			" EHF020800T0_01 = EHF020800T1_01 AND EHF020800T0_10 = EHF020800T1_10" +
			" WHERE 1=1 " +
			" AND EHF020800T1_01 = '"+(String)dataMap.get("EHF020800T0_01")+"' " +  //加班表單編號
			" AND EHF020800T1_10 = '"+comp_id+"' ";  //公司代碼

			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Map msgMap = new HashMap();
				msgMap.put("EHF020800T0_06", (String) rs.getString("加班日期"));
				msgMap.put("EHF020800T1_04", (String) rs.getString("員工工號"));
				DEL_List.add(msgMap);
			}
			
			rs.close();
			stmt.close();
			
			for(int i=0;i<DEL_List.size();i++){
				
				Map msgMap =(Map)DEL_List.get(i);
				//重產考勤
				ehf020801.Again_Produce_att(conn,(String)msgMap.get("EHF020800T0_06"),(String) msgMap.get("EHF020800T1_04"),comp_id, USER_ID);
			}
			
			//執行EHF020801刪除功能
			ehf020801.delData(dataMap);
			
			if((Integer)dataMap.get("MAIN_DATA_COUNT") > 0){
				chk_flag = true;
			}
			
			//關閉EHF020801元件
			ehf020801.close();
			
			//控制刪除後的顯示畫面
			paramMap.put(super.DEL_FORWARD_CONFIG, super.FORWARD_QUERYFORM);
			
		}catch(Exception e){
			chk_flag = false;
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return chk_flag;
	}

	@Override
	protected ActionForm executeInitData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		EHF020801M0F Form = new EHF020801M0F();
		
		try{

		}catch(Exception e){
			//記錄錯誤訊息
			this.handleException(e, paramMap);
			e.printStackTrace();
		}
		
		return (ActionForm) Form;
	}

	@Override
	protected Map executeQueryData(Connection conn, ActionForm form,
			Map paramMap) {
		// TODO Auto-generated method stub
		
		boolean chk_flag = false;
		EHF020801M0F Form = (EHF020801M0F) form;
		Map return_map = new HashMap();
		EHF020801M0F bean = null;
		String comp_id = (String)paramMap.get(super.COMP_ID);
		HttpServletRequest request = (HttpServletRequest)paramMap.get(super.REQUEST);
		
		try{
			//建立空清單
			List<EHF020801M0F> querylist = new ArrayList<EHF020801M0F>();
			
			//建立EHF020800元件
			EHF020801 ehf020801 = new EHF020801(comp_id);
			
			//建立查詢 Condition Map
			Map queryCondMap = new HashMap();
			//處理條件設定
			//ActionForm convert to HashMap
			queryCondMap = BeanUtils.describe(Form);
			//設定使用者資訊
			queryCondMap.putAll(paramMap);
			
			//使用元件進行處理
			List ehf020801_queryList = ehf020801.queryData(queryCondMap);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			Map empMap = hr_tools.getEmpNameMap(comp_id);
			Map depMap = hr_tools.getDepNameMap(comp_id);
			hr_tools.close();
			
			Iterator it = ehf020801_queryList.iterator();
			Map tempMap = null;
			
			if(it.hasNext()){
				chk_flag = true;
			}
			
			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				
				bean = new EHF020801M0F();
				
				bean.setEHF020800T0_01((String)tempMap.get("EHF020800T0_01"));  //單號
				bean.setEHF020800T0_06((String)tempMap.get("EHF020800T0_06"));  //加班日期
				bean.setEHF020800T0_03((String) ((Map)empMap.get((String)tempMap.get("EHF020800T0_03"))).get("EMPLOYEE_NAME"));  //填單人
				bean.setEHF020800T0_11((String) ((Map)depMap.get((String)tempMap.get("EHF020800T0_11"))).get("SHOW_DESC"));  //加班部門
				bean.setEHF020800T1_04((String) ((Map)empMap.get((String)tempMap.get("EHF020800T1_04"))).get("EMPLOYEE_NAME"));  //申請人
				bean.setEHF020800T1_06((String)tempMap.get("EHF020800T1_06"));  //加班時間
				bean.setEHF020800T0_09_DESC((String)tempMap.get("EHF020800T0_09_DESC"));  //表單狀態
				
				querylist.add(bean);
			}
			
			//設定querylist
			Form.setEHF020800T0_LIST(querylist);
			
			//關閉EHF020800元件
			ehf020801.close();
			//清空
			Form.setEHF020800T0_06("");
			Form.setEHF020800T0_06_END("");
			Form.setEHF020800T0_11("");
			Form.setEHF020800T0_11_SHOW("");
			Form.setEHF020800T0_11_TXT("");
			Form.setEHF020800T1_04("");
			Form.setEHF020800T1_04_SHOW("");
			Form.setEHF020800T1_04_TXT("");
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
	 * 下載空白範例匯入檔案
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward print_example(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		BA_TOOLS tools = BA_TOOLS.getInstance();
		
		try {								
			//傳入前端需要的檔名
			String name ="加班單(範本)"+tools.covertDateToString(Calendar.getInstance().getTime(), "yyyy/MM/dd")+".xls";
			
			String FileName="";					
			//存入檔案
			FileName = getServlet().getServletContext().getRealPath("")+"/excelrpt/"+"//" + "EHF020800R0_example.xls";
			request.setAttribute("MSG","列印完成!!");
			//多傳入一個參數，name  可以指定報表下載時，要顯示什麼中文。
			request.setAttribute("DOWNLOADFILE","<iframe align='center' width=0 height=0 src='"+mapping.getPath().replaceAll("/", "")+".do?reqCode=exceldownload&filename="+FileName+"&name="+name+"'></iframe>");
		
	} catch (Exception E) {
		E.printStackTrace();
		request.setAttribute("MSG", "列印失敗!!");
	} finally {
		// ef.write();
	}

		return this.init(mapping, form, request, response);
	}
	
	/**
	 * 加班單匯入作業 
	 * @EHF020800M0A
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public ActionForward impEHF020800(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		EHF020801M0F Form = (EHF020801M0F) form;
		BA_TOOLS tools = BA_TOOLS.getInstance();
		Connection conn = null;
		
		try {
			conn = tools.getConnection("SPOS");
		} catch (SQLException e2) {
			e2.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try{
			//Collection List
			List list = new ArrayList();
			//NG_Collection List
			List ng_list = new ArrayList();
			//ERROR_Collection List
			List error_list = new ArrayList();
			
			//建立登入者資訊
			AuthorizedBean authbean = getLoginUser(request);
			
			HR_TOOLS hr_tools = new HR_TOOLS();
			// 取得部門資訊
			Map depMap = hr_tools.getDepNameMap(authbean.getCompId());

			// 取得員工資訊
			Map empMap = hr_tools.getEmpNameMap(authbean.getCompId());
			
			//建立行事曆匯入元件
			IMP_EHF020800 imp_ehf020800 = new IMP_EHF020800(  authbean.getEmployeeID(), authbean,request ,depMap,empMap);
			
			//取得匯入檔案
			FormFile importfile = Form.getUPLOADFILE();
			
			//取得回傳訊息Map
			Map msgMap = imp_ehf020800.fileImport(conn, importfile.getFileName(), importfile.getFileSize(), importfile.getContentType(),importfile.getInputStream(), authbean.getUserName(), authbean.getCompId());
			//取得匯入的詳細資料清單
			List datalist = (List) msgMap.get("DATALIST");
			
			Iterator it = datalist.iterator();
			Map tempMap = null;
			EHF020801M0F bean = null;

			while(it.hasNext()){
				
				tempMap = (Map) it.next();
				bean = new EHF020801M0F();

				String EHF020800T0_06=(String)tempMap.get("EHF020800T0_06_Year")+"/"+(String)tempMap.get("EHF020800T0_06_Month")+"/"+(String)tempMap.get("EHF020800T0_06_Day");
				String EHF020800T1_06=(String)tempMap.get("EHF020800T1_06_Year")+"/"+(String)tempMap.get("EHF020800T1_06_Month")+"/"+(String)tempMap.get("EHF020800T1_06_Day")+" "+(String)tempMap.get("EHF020800T1_06_HH")+":"+(String)tempMap.get("EHF020800T1_06_MM");
				String EHF020800T1_07=(String)tempMap.get("EHF020800T1_07_Year")+"/"+(String)tempMap.get("EHF020800T1_07_Month")+"/"+(String)tempMap.get("EHF020800T1_07_Day")+" "+(String)tempMap.get("EHF020800T1_07_HH")+":"+(String)tempMap.get("EHF020800T1_07_MM");

				bean.setEHF020800T0_06(EHF020800T0_06);
				bean.setEHF020800T0_03((String)tempMap.get("EHF020800T0_03"));
				bean.setEHF020800T0_11((String) ((Map)depMap.get((String)tempMap.get("EHF020800T0_11"))).get("SHOW_DESC"));
				bean.setEHF020800T1_04((String)tempMap.get("EHF020800T1_04_name"));
				bean.setEHF020800T1_11((String)tempMap.get("EHF020800T1_11"));
				bean.setEHF020800T1_06(EHF020800T1_06+"~"+EHF020800T1_07);
				
				
				list.add(bean);
			}
			//判斷是否有重複未匯入的資料清單
			if(msgMap.containsKey("ERRORDATACOUNT")){
				
				//取得重複未匯入的詳細資料清單
				datalist = (List) msgMap.get("ERRORDATALIST");
				
				it = datalist.iterator();
				tempMap = null;
				bean = null;
				
				while(it.hasNext()){
					
					tempMap = (Map) it.next();
					bean = new EHF020801M0F();
					
					String EHF020800T0_06=(String)tempMap.get("EHF020800T0_06_Year")+"/"+(String)tempMap.get("EHF020800T0_06_Month")+"/"+(String)tempMap.get("EHF020800T0_06_Day");
					String EHF020800T1_06=(String)tempMap.get("EHF020800T1_06_Year")+"/"+(String)tempMap.get("EHF020800T1_06_Month")+"/"+(String)tempMap.get("EHF020800T1_06_Day")+" "+(String)tempMap.get("EHF020800T1_06_HH")+":"+(String)tempMap.get("EHF020800T1_06_MM");
					String EHF020800T1_07=(String)tempMap.get("EHF020800T1_07_Year")+"/"+(String)tempMap.get("EHF020800T1_07_Month")+"/"+(String)tempMap.get("EHF020800T1_07_Day")+" "+(String)tempMap.get("EHF020800T1_07_HH")+":"+(String)tempMap.get("EHF020800T1_07_MM");

					bean.setEHF020800T0_06(EHF020800T0_06);
					bean.setEHF020800T0_03((String)tempMap.get("EHF020800T0_03"));
					
					
					if( ((Map)depMap.get((String)tempMap.get("EHF020800T0_11")))==null){
						bean.setEHF020800T0_11(((String)tempMap.get("EHF020800T0_11")));
					}else{
						bean.setEHF020800T0_11((String) ((Map)depMap.get((String)tempMap.get("EHF020800T0_11"))).get("SHOW_DESC"));
					}
					bean.setEHF020800T1_04((String)tempMap.get("EHF020800T1_04_name"));
					bean.setEHF020800T1_11((String)tempMap.get("EHF020800T1_11"));
					bean.setEHF020800T1_06(EHF020800T1_06+"~"+EHF020800T1_07);
					bean.setERROR((String)tempMap.get("error"));
					
					
					error_list.add(bean);
				}
				if(error_list.size()>0){
					//設定前端顯示訊息
					request.setAttribute("ERRORMSG", "加班單共有 "+(Integer)msgMap.get("ERRORDATACOUNT")+" 筆資料未匯入!!");
					request.setAttribute("Form3Datas",error_list);
					request.setAttribute("error_collection", "show");
				}
			}
			
			
			hr_tools.close();
			//設定前端顯示訊息
			request.setAttribute("MSG", "加班單共匯入 "+(Integer)msgMap.get("DATACOUNT")+" 筆!!");

			//設定前端顯示資訊
			request.setAttribute("Form1Datas", Form);
			request.setAttribute("Form2Datas",list);
			FormUtils.setFormDisplayMode(request, form, "create");
			request.setAttribute("button", "Import");
			request.setAttribute("correct_collection", "show");
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if( conn!=null && !conn.isClosed() ){
					conn.close();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
		}
		
		//return mapping.findForward("success");
		return mapping.findForward("redirectIMP");
	}

}
