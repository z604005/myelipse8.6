package com.spon.utils.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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

import com.spon.ems.NewDispatchAction;
import com.spon.utils.struts.form.BA_EMSForm;
import com.spon.utils.struts.form.BA_ALLKINDForm;

import fr.improve.struts.taglib.layout.menu.MenuComponent;
import fr.improve.struts.taglib.layout.menu.MenuRepository;
import fr.improve.struts.taglib.layout.util.FormUtils;

/**
 * <Action>EMS-Flow API 取得資料

 */
public class BA_EMS extends NewDispatchAction {

	public BA_EMS() {

	}


	/**
	 * 
	 * @exception Throwable
	 */
	public void finalize() throws Throwable {

	}

	/**
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	/**
	 * Action的進入點
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward init(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		Connection conn = null;
		try{
			
			request.getSession().setAttribute("Pageid",null);
			FormUtils.setFormDisplayMode(request, form, "create");
			
		} catch (Exception e2) {
	        // TODO Auto-generated catch block
	        e2.printStackTrace();
	    }finally {
	    	try {
	    		if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			}catch(Exception e){
				
			}
	    }
		
	    return queryDatas(mapping, form, request, response);

	}

	public ActionForward queryDatas(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		
		Connection conn = null;
		//BA_EMS_TOOLS ems_tools = BA_EMS_TOOLS.getInstance();
		BA_EMSForm Form = (BA_EMSForm) form;
		List emplist = null;
		List deplist = null;
		List agentlist = null;

		try {
			ActionErrors lc_errors = new ActionErrors();
					
			if (lc_errors.isEmpty()) {
				generateSelectBox(conn, form, request);	
				
				String dep_id = "";  //部門key , 用於員工資料API
				if(!"".equals(Form.getDepkey()) && !(Form.getDepkey() == null )){
					dep_id = Form.getDepkey();
				}else{
					dep_id = "";
				}
				
				String employee_id = "";  //員工key , 用於代理人API
				if(!"".equals(Form.getEmpkey()) && !(Form.getEmpkey() == null )){
					employee_id = Form.getEmpkey();
				}else{
					employee_id = "";
				}
				
				int depth = -999;  //下轄N階 , 用於取得部門下轄N階(or 下轄到底)人員資料API
				if(!"".equals(Form.getDepth()) && !(Form.getDepth() == null )){
					depth = Integer.parseInt(Form.getDepth());
				}else{	//未指定尋訪階層視同下轄到底
					depth = -999;
				}
				
				String changescript = "";
				if(!"".equals(Form.getChangescript()) && !(Form.getChangescript() == null)){
					changescript = Form.getChangescript();
				}
				
				
				if("emp".equals(Form.getFunction())){  //取得員工資料 List
					/*
					EmployeeApi empapi = (EmployeeApi ) getEmscontext().getBean("esaOrgEmployeeApi");
//					empapi.queryEmployeeList(userID, companyID, deptID, employeeNO, idNO, employeeType, searchStatus, checkDate)
					if(Form.getSearch()==null || "".equals(Form.getSearch())){
						emplist = empapi.queryEmployeeList(getLoginUser(request).getUserId(), getLoginUser(request).getCompId(), dep_id, "", "", "", "", null);
					}else{
						if("1".equals(Form.getCHKBUTTON())){
							emplist = empapi.queryEmployeeList(getLoginUser(request).getUserId(), getLoginUser(request).getCompId(), dep_id, Form.getSearch(), "", "", "", null);
						}else if ("2".equals(Form.getCHKBUTTON())){
							emplist = empapi.queryEmployeeList(getLoginUser(request).getUserId(), getLoginUser(request).getCompId(), dep_id, "", Form.getSearch(), "", "", null);
						}else if ("3".equals(Form.getCHKBUTTON())){
							emplist = empapi.queryEmployeeList(getLoginUser(request).getUserId(), getLoginUser(request).getCompId(), dep_id, "", "", "", "", null);
						}
					}
					
					Form.setEMSC(emplist);

					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", emplist);
					*/
				}else if ("dep".equals(Form.getFunction())){  //取得部門資料 List
					/*
					OrganizationApi orgapi = (OrganizationApi) getEmscontext().getBean("esaOrgOrganizationApi");
					
					deplist = orgapi.getAllDeptList(getLoginUser(request).getUserId(), null, Integer.parseInt(getLoginUser(request).getUserCode()), getLoginUser(request).getCompId());
					
					Form.setEMSC(deplist);
					
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", deplist);
					*/
				}else if ("samedepemp".equals(Form.getFunction())){  //取得同部門同仁資料 List
					/*
					OrganizationApi orgapi = (OrganizationApi) getEmscontext().getBean("esaOrgOrganizationApi");
					//if(!"".equals(dep_id) && dep_id != null){
					if(!(dep_id.equals("")) && dep_id != null){	
						
						deplist = orgapi.getDeptAllUser(getLoginUser(request).getUserId(), getLoginUser(request).getCompId(), dep_id, null, "");
					
					}else{
						deplist = new ArrayList();
					}
					
					Form.setEMSC(deplist);
					
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("changescript", changescript);
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", deplist);
					*/
				}else if(Form.getFunction().equals("queryempdetp")){
					/*
					List resultList = null;
					EmployeeApi empapi = (EmployeeApi) getEmscontext().getBean("esaOrgEmployeeApi");
					if(!dep_id.equals("") && dep_id != null){
						
						resultList = empapi.queryEmployeeDetpByEmpID(getLoginUser(request).getUserId(), 
								getLoginUser(request).getCompId(), dep_id , null);
					}else{
						resultList = new ArrayList();
					}
					Form.setEMSC(resultList);
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("changescript", changescript);
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", resultList);
					*/
				}else if(Form.getFunction().equals("dbed")){
					/*
					System.out.println("====>"+dep_id);
					List dbList = null;
					AgentApi agentapi = (AgentApi) getEmscontext().getBean("esaOrgAgentApi");
					if(!dep_id.equals("") && dep_id != null){
						dbList = agentapi.getDataByEmployeeDuty (getLoginUser(request).getUserId(), dep_id,
								getLoginUser(request).getCompId(), "1", null, null);
					}else{
						dbList = new ArrayList();
					}
					
					Form.setEMSC(dbList);
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("changescript", changescript);
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", dbList);
					*/
				}else if ("agents".equals(Form.getFunction())){  //取得代理人資料 List
					/*
					agentlist = ems_tools.getEmsAgentList(getEmscontext(), getLoginUser(request).getUserId(), employee_id, getLoginUser(request).getCompId(), "1", "", "");
					
					Form.setEMSC(agentlist);
					
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("changescript", changescript);
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", agentlist);
					*/
				}else if (("bossunderdep").equals(Form.getFunction())){  //取得主管轄下部門 List
					/*
					List bossunderdeplist = null;
					bossunderdeplist = ems_tools.getEmsBossAllUnderDep(getEmscontext(), getLoginUser(request).getUserId(), getLoginUser(request).getEmployeeID(),
										"", getLoginUser(request).getUserCode(), getLoginUser(request).getCompId() );
					
					Form.setEMSC(bossunderdeplist);
					
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("changescript", changescript);
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", bossunderdeplist);
					*/
				}else if ("empunderdep".equals(Form.getFunction())){  //取得部門下轄N階(or 下轄到底)人員資料 List; 空白階視同下轄到底
					/*
					OrganizationApi orgapi = (OrganizationApi) getEmscontext().getBean("esaOrgOrganizationApi");
					List<String> DeptIdList = new ArrayList<String>();
					String getDeptId = "";
					String[] depId = null;
					String[] depName = null;
					BA_TOOLS ba_tools = BA_TOOLS.getInstance();
					conn = ba_tools.getConnection();
					Statement stmt = conn.createStatement();
					ResultSet rs = null;
					
					//尋訪組織樹取得部門代碼(演算法: 深度優先, 不包含本階)
					ParseTree(conn, DeptIdList, dep_id, depth);
					
					//納入本階部門代碼
					getDeptId = "'" + dep_id + "'";
					for(int i=0; i<DeptIdList.size(); i++){
						getDeptId += ",'" + DeptIdList.get(i) + "'";
					}
					
					//重新排序部門代碼(廣度優先, 若本階為樹根則排除掉因為樹根不是部門不會有員工)
					String sql = "select ems_orgtree_01, ems_orgtree_02 from ems_orgtree where ems_orgtree_01 in (" + getDeptId + ") and ems_orgtree_07 > 0 group by ems_orgtree_01 order by ems_orgtree_07";
					rs = stmt.executeQuery(sql);
					rs.last();
					depId = new String[rs.getRow()];
					depName = new String[rs.getRow()];
					rs.beforeFirst();
					
					int dep_index = 0;
					while(rs.next()){
						depId[dep_index] = rs.getString("ems_orgtree_01");
						depName[dep_index] = rs.getString("ems_orgtree_02");
						
						dep_index++;
					}
					
					//取得各部門員工資料
					deplist = new ArrayList();
					List tempList = null;
					
					for(int i=0; i<depId.length; i++){
						if(!(depId[i].equals("")) && depId[i] != null){
							tempList = orgapi.getDeptAllUser(getLoginUser(request).getUserId(), getLoginUser(request).getCompId(), depId[i], null, "");
							
							for(int y=0; y<tempList.size(); y++){
								((Map)tempList.get(y)).put("DEPT_DESC", depName[i]);
								deplist.add(tempList.get(y));
							}
							
							tempList.clear();
						}else{
							deplist = new ArrayList();
						}
					}
					
					
					Form.setEMSC(deplist);
					
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("changescript", changescript);
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", deplist);
					
					rs.close();
					stmt.close();
					*/
				}else if (("dep_tree").equals(Form.getFunction())){	//取得公司組織樹
					BA_TOOLS ba_tools = BA_TOOLS.getInstance();
					conn = ba_tools.getConnection();
					String sql = "";
					Statement stmt = conn.createStatement();
					ResultSet rs = null;
					
					//建立展料樹節點資料
					//預設展開MenuComponent.setOpen.......不知道為啥, 這行沒效果QAQ
					
					//0. 建立BOM樹(樹根)
					MenuComponent BOM_tree = new MenuComponent();
					BOM_tree.setName("DEP_tree");
//					BOM_tree.setOpen(true);
					
					//1. 建立BOM0(樹頭)
					sql = "select * from EHF000200T0 where EHF000200T0_04='#'";
					rs = stmt.executeQuery(sql);
					rs.next();
					
					MenuComponent motherBOM = new MenuComponent();
					motherBOM.setTitle(rs.getString("EHF000200T0_02")+"/"+rs.getString("EHF000200T0_03")+"~"+rs.getString("EHF000200T0_01"));
					motherBOM.setName("motherBOM");
//					motherBOM.setOpen(true);
					
					//開始遞迴樹幹、樹葉
					ExpendTree(conn, motherBOM, rs.getString("EHF000200T0_01"), 0);
					
					BOM_tree.addMenuComponent(motherBOM);
//					BOM_tree.setOpen(true);
					
					MenuRepository menurepository = new MenuRepository();
					menurepository.addMenu(BOM_tree);

					request.getSession().setAttribute(MenuRepository.MENU_REPOSITORY_KEY, menurepository);
					
					FormUtils.setFormDisplayMode(request, form, "edit");
					request.setAttribute("changescript", changescript);
					request.setAttribute("Form1Datas", Form);
					request.setAttribute("Form2Datas", new ArrayList());
					
					//查詢組織樹各階層顏色設定檔
					sql = "select * from ems_orgtree_config where COMP_ID='" + getLoginUser(request).getCompId() + "'";
					rs = stmt.executeQuery(sql);
					
					String tree_layer_font_color = "";
					String tree_layer_background_color = "";
					
					if(rs.next()){
						for(int i=1; i<=18; i++){
							if("".equals(tree_layer_font_color) || "".equals(tree_layer_background_color)){
								if(i%2 == 1) tree_layer_font_color = rs.getString(i);
								if(i%2 == 0) tree_layer_background_color = rs.getString(i);
							}else{
								if(i%2 == 1) tree_layer_font_color += "," + rs.getString(i);
								if(i%2 == 0) tree_layer_background_color += "," + rs.getString(i);
							}
						}
					}
					
					request.setAttribute("tree_layer_font_color", tree_layer_font_color);
					request.setAttribute("tree_layer_background_color", tree_layer_background_color);
					
					rs.close();
					stmt.close();
				}
			} else {
				saveErrors(request, lc_errors);
				request.setAttribute("save_status", "error");
				request.setAttribute("Form1Datas", Form);
				return mapping.findForward("success");
			}
		} catch (Exception e) {
			request.setAttribute("Form2Datas",emplist);
			request.setAttribute("Form1Datas", Form);
			System.out.println(e);
			e.printStackTrace();
		} finally {
			try {
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		return mapping.findForward("success");
	}
	
	/**
	 * 建立組織樹(使用遞迴方式，可無限展開; 演算法:深度優先) add by John 2014/06/12
	 * @param conn
	 * @param MotherNode 樹頭節點
	 * @param NodeValue 部門代碼
	 * @param NodeLayer 傳入該節點階層
	 */
	public void ExpendTree(Connection conn, MenuComponent MotherNode, String NodeValue, int NodeLayer){
		int current_NodeLayer = NodeLayer+1;
		
		try{
			Statement stmt = conn.createStatement();
			
			//2. 建立BOM1(樹幹)
			String sql = "select * from EHF000200T0 where EHF000200T0_04='"+NodeValue+"' group by EHF000200T0_01";
			ResultSet rs = stmt.executeQuery(sql);
			
			int BOM1_index = 0;
			while(rs.next()){
				//BOM1暫存節點
				MenuComponent BOM1 = new MenuComponent();
				
				BOM1.setTitle(rs.getString("EHF000200T0_02")+"/"+rs.getString("EHF000200T0_03")+"~"+rs.getString("EHF000200T0_01"));
				BOM1.setName("BOM"+current_NodeLayer+"_"+BOM1_index);
//				BOM1.setOpen(true);
				MotherNode.addMenuComponent(BOM1);
				
				//遞迴
				ExpendTree(conn, BOM1, rs.getString("EHF000200T0_01"), current_NodeLayer);
				
				BOM1_index++;
			}
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			System.out.println("ExpendTree() error at current_NodeLayer = "+current_NodeLayer+", MotherNode = "+NodeValue+"!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 尋訪組織樹(使用遞迴方式，可指定向下尋訪N階; 演算法:深度優先) add by John 2014/06/12
	 * @param conn
	 * @param List<String> 傳入空的部門代碼字串List供遞迴使用input
	 * @param NodeValue 部門代碼
	 * @param depth 欲向下尋訪的階層數
	 */
	public void ParseTree(Connection conn, List<String> depID, String NodeValue, int depth){
		try{
			Statement stmt = conn.createStatement();
			
			//尋訪組織樹(演算法: 深度優先)
			String sql = "select * from ems_orgtree where ems_orgtree_03='"+NodeValue+"' group by ems_orgtree_01";
			ResultSet rs = stmt.executeQuery(sql);
			
			int temp_depth = depth;
			rs.last();
			if(rs.getRow()>0){
				temp_depth--;
			}
			rs.beforeFirst();
			
			//若無查詢結果，則表示尋訪至最底端，並結束尋訪
			while(depth != 0 && rs.next()){
				depID.add(rs.getString("ems_orgtree_01"));
				
				if(depth == -999){	//表示向下尋訪至底
					ParseTree(conn, depID, rs.getString("ems_orgtree_01"), depth);
				}else if(temp_depth > 0){	//若true, 則繼續向下尋訪
					//遞迴
					ParseTree(conn, depID, rs.getString("ems_orgtree_01"), temp_depth);
				}
			}
			
			rs.close();
			stmt.close();
		}catch(Exception e){
			System.out.println("ParseTree() error at descade_depth = "+depth+", MotherNode = "+NodeValue+"!");
			e.printStackTrace();
		}
	}
	
	/**
	 * 查詢表單畫面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 *            response
	 */
	public ActionForward queryForm(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) {
		return queryDatas(mapping, form, request, response);
	}
	
	/**
	 * 產生下拉選單資料
	 * 
	 * @param conn
	 * @param form
	 * @param request
	 */
	
	public void generateSelectBox(Connection conn, ActionForm form, HttpServletRequest request){
		// TODO Auto-generated method stub
		//產生搜尋條件類別
		
		try{
			BA_EMSForm Form = (BA_EMSForm) form;
			
			if("emp".equals(Form.getFunction())){
				List datas = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("1");
				bform.setItem_value("員工工號");
				datas.add(bform);
//				bform = new BA_ALLKINDForm();
//				bform.setItem_id("2");
//				bform.setItem_value("員工姓名");
//				datas.add(bform);
//				bform = new BA_ALLKINDForm();
//				bform.setItem_id("3");
//				bform.setItem_value("員工身份證字號");
//				datas.add(bform);
				request.setAttribute("CHKBUTTON_list", datas);
			}else if("dep".equals(Form.getFunction())){
				/*List datas = new ArrayList();
				BA_ALLKINDForm bform = new BA_ALLKINDForm();
				bform.setItem_id("1");
				bform.setItem_value("部門代號");
				datas.add(bform);
				request.setAttribute("CHKBUTTON_list", datas);*/
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}